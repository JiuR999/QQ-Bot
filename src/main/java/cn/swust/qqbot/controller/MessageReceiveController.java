package cn.swust.qqbot.controller;

import cn.swust.qqbot.annotation.PrivateHandler;
import cn.swust.qqbot.constants.MessageBeanType;
import cn.swust.qqbot.constants.MessageSendApi;
import cn.swust.qqbot.factory.MessageFactory;
import cn.swust.qqbot.models.QQMessage;
import cn.swust.qqbot.models.RawSendMessage;
import cn.swust.qqbot.utils.AiwanUtils;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class MessageReceiveController {
    final OkHttpClient client = new OkHttpClient();
    private String cmd;

    @PostMapping("/")
    public void msgHandle(@RequestBody QQMessage msg) throws IOException {
        displayMessageInfo(msg);
        cmd = msg.getMessage().get(1).getData().getText();
        String sendMsg = "";
        Response response = null;
        Request request = null;
        //群聊消息
        if(msg.getMessage_type().equals("group")) {
            if (msg.getMessage().get(0).getType().equals("at")) {
                if (cmd.contains("小红书")) {
                    String url = msg.getMessage().get(1).getData().getText().split("-")[1];
                    Pattern pattern = Pattern.compile("http://[\\w./]+");
                    Matcher matcher = pattern.matcher(url);
                    while (matcher.find()) {
                        String link = matcher.group();
                        request = new Request.Builder()
                                .url("https://api.pearktrue.cn/api/xhhimg/?url=" + link)
                                .build();
                        response = client.newCall(request).execute();
                        if(response.isSuccessful()){
                            RawSendMessage message = MessageFactory.createGroupTextMessage(msg.getGroup_id(),
                                    response.body().string());
                            response.close();
                            sendMessage(message, MessageSendApi.SEND_GROUP_MSG);
                        } else {
                            RawSendMessage message = MessageFactory.createGroupTextMessage(msg.getGroup_id(),
                                    "连接服务器超时宝贝！");
                            sendMessage(message, MessageSendApi.SEND_GROUP_MSG);
                        }
                    }
                }

                if (cmd.contains("点赞")) {

                    sendMsg = "{" +
                            "\"user_id\":\"" + msg.getSender().getUser_id() + "\"," +
                            "\"times\":10" +
                            "}";
                    okhttp3.MediaType JSON = okhttp3.MediaType.get("application/json; charset=utf-8");
                    okhttp3.RequestBody body = okhttp3.RequestBody.create(sendMsg, JSON);
                    request = new Request.Builder()
                            .post(body)
                            .url("http://localhost:3000/" + MessageSendApi.SEND_LIKE)
                            .build();
                    response = client.newCall(request).execute();
                    System.out.println("发送消息:" + sendMsg);
                    assert response.body() != null;
                    System.out.println(response.body().string());
                    response.close();

                }

                if (cmd.contains("一言")) {
                    request = new Request.Builder()
                            .url("https://api.pearktrue.cn/api/hitokoto/")
                            .build();
                    response = client.newCall(request).execute();

                    String text = response.body().string();
                    RawSendMessage message = MessageFactory.createGroupTextMessage(msg.getGroup_id(),
                            text);
                    sendMessage(message, MessageSendApi.SEND_GROUP_MSG);
                }


                if (cmd.contains("图")) {
                    request = new Request.Builder()
                            .url("http://api.suxun.site/api/tao?type=json")
                            .build();
                    response = client.newCall(request).execute();
                    JSONObject jsonObject = JSON.parseObject(response.body().string());
                    response.close();
                    String url = jsonObject.getString("url");
                    System.out.println(url);
                    RawSendMessage image = MessageFactory.createGroupRawMessage(msg.getGroup_id(), "image", url);
                    sendMessage(image, MessageSendApi.SEND_GROUP_MSG);
                }

                //抖音解析 https://api.aag.moe/api/dywsyjx?url=
                if (cmd.contains("获取歌曲")) {

                    String name = msg.getMessage().get(1).getData().getText().split(" ")[2];
                    System.out.println("歌曲名称:" + name);
                    request = new Request.Builder()
                            .url("https://www.hhlqilongzhu.cn/api/dg_qqmusic_SQ.php?msg=" + name + "&n=1&type=text")
                            .build();
                    response = client.newCall(request).execute();
                    String res = response.body().string();
                    response.close();
                    RawSendMessage musicInfo = MessageFactory.createGroupTextMessage(msg.getGroup_id(), res);
                    sendMessage(musicInfo, MessageSendApi.SEND_GROUP_MSG);
                }
                if (cmd.contains("热搜")) {
                    Map<String, String> nameMap = new HashMap<>();
                    nameMap.put("百度", "baidu");
                    nameMap.put("微博", "weibo");
                    String name = msg.getMessage().get(1).getData().getText().split(" ")[2];

                    String url = "https://api.pearktrue.cn/api/60s/image/hot/?type=" + nameMap.get(name);
                    System.out.println(url);
                    RawSendMessage hotNews = MessageFactory.createGroupRawMessage(msg.getGroup_id(), "image", url);
                    sendMessage(hotNews, MessageSendApi.SEND_GROUP_MSG);
                }
                //大模型对话
                if (cmd.contains("chat")) {
                    String ques = msg.getMessage().get(1).getData().getText().split(" ")[2];
                    System.out.println("问题:" + ques);
                    request = new Request.Builder()
                            .url("https://api.pearktrue.cn/api/xfai/?message=" + ques)
                            .build();
                    response = client.newCall(request).execute();
                    if(response.isSuccessful()){
                        JSONObject jsonObject = JSON.parseObject(response.body().string());
                        response.close();
                        RawSendMessage message = MessageFactory.createGroupTextMessage(msg.getGroup_id(),
                                jsonObject.getString("answer"));
                        sendMessage(message, MessageSendApi.SEND_GROUP_MSG);
                    } else {
                        RawSendMessage message = MessageFactory.createGroupTextMessage(msg.getGroup_id(),
                                "连接服务器超时宝贝！");
                        sendMessage(message, MessageSendApi.SEND_GROUP_MSG);
                    }

                }
            }
        } else {
            String ques = msg.getMessage().get(0).getData().getText();
            System.out.println("问题:" + ques);
            request = new Request.Builder()
                    .url("https://api.pearktrue.cn/api/xfai/?message=" + ques)
                    .build();
            response = client.newCall(request).execute();
            if(response.isSuccessful()){
                JSONObject jsonObject = JSON.parseObject(response.body().string());
                response.close();
                RawSendMessage message = MessageFactory.createPrivateTextMessage(msg.getSender().getUser_id(),
                        jsonObject.getString("answer"));
                sendMessage(message, MessageSendApi.SEND_PRIVATE_MSG);
            }
        }

    }

    private static void displayMessageInfo(QQMessage msg) {
        System.out.println("收到消息：" + msg.toString());
        System.out.println("收到" + msg.getMessage_type() + "消息");
        System.out.println("发送人QQ:" + msg.getSender().getUser_id());
        System.out.println("昵称:" + msg.getSender().getNickname());
        System.out.println("群名片:" + msg.getSender().getCard());
        System.out.println("消息类型:" + msg.getMessage().get(0).getType());
        System.out.println("Raw:"+ msg.getRaw_message());
    }

    private void sendMessage(RawSendMessage message, String msgCat) throws IOException {
        Response response;
        Request request;
        String sendMsg = JSON.toJSONString(message);
        okhttp3.MediaType JSON = okhttp3.MediaType.get("application/json; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(sendMsg, JSON);
        request = new Request.Builder()
                .post(body)
                .url("http://localhost:3000/" + msgCat)
                .build();
        response = client.newCall(request).execute();
        System.out.println("发送消息:" + sendMsg);
        assert response.body() != null;
        System.out.println(response.body().string());
        response.close();
    }

    private String HandleVido(QQMessage msg, String sendMsg) throws IOException {

        if(msg.getMessage().get(1).getData().getText().contains("视频")){
            String url = "https://jx.iqfk.top/api/sjsp.php";
            RawSendMessage message = MessageFactory.createGroupRawMessage(msg.getGroup_id(), MessageBeanType.VIDEO, url);
/*            sendMsg = "{" +
                    "\"group_id\":\"" + msg.getGroup_id() + "\"," +
                    "\"message\":" + "{" +
                    "\"type\":\"video\"," +
                    "\"data\": {" +
                    "        \"file\": \""+url+"\"" +
                    "    }"+
                    "}" +
                    "}";*/

            sendMessage(message,MessageSendApi.SEND_GROUP_MSG);

        }
        return sendMsg;
    }
}
