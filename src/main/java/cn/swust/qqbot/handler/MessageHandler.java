package cn.swust.qqbot.handler;

import cn.swust.qqbot.annotation.Action;
import cn.swust.qqbot.annotation.GroupHandler;
import cn.swust.qqbot.annotation.PrivateHandler;
import cn.swust.qqbot.constants.MessageBeanType;
import cn.swust.qqbot.constants.MessageSendApi;
import cn.swust.qqbot.factory.MessageFactory;
import cn.swust.qqbot.models.QQMessage;
import cn.swust.qqbot.models.RawSendMessage;
import cn.swust.qqbot.utils.AiwanUtils;
import cn.swust.qqbot.utils.OkHttpUtils;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MessageHandler {
    private String msgCat;

    @Action(cmd = "疯狂星期四")
    @GroupHandler
    public void CrazyThursday(QQMessage msg) {
        try {
            Response response = OkHttpUtils.DoGet("https://api.pearktrue.cn/api/kfc/");
            JSONObject jsonObject = JSON.parseObject(response.body().string());
            RawSendMessage message = MessageFactory.createGroupTextMessage(msg.getGroup_id(),
                    jsonObject.getString("text"));
            sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Action(cmd = "视频")
    @GroupHandler
    public void HandleVideo(QQMessage msg) {
        String url = "https://jx.iqfk.top/api/sjsp.php";
        RawSendMessage message = MessageFactory.createGroupRawMessage(msg.getGroup_id(), MessageBeanType.VIDEO, url);
        try {
            sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Action(cmd = "撒娇")
    @GroupHandler
    public void Sajiao(QQMessage msg) throws IOException {
        String baseUrl = "https://api.pearktrue.cn/api/yujie/";

        Document document = Jsoup.connect(baseUrl).get();
        String src = document.
                getElementsByTag("source").
                get(0).
                attr("src");
        String url = baseUrl + src.substring(2);
        RawSendMessage record = MessageFactory.createGroupRawMessage(msg.getGroup_id(), "record", url);
        sendMessage(record);
    }

    @Action(cmd = "q")
    @GroupHandler
    public void GetStartCode(QQMessage msg) throws IOException {
        String url = AiwanUtils.GetVerImgByHtmlUnit();
        Response response = OkHttpUtils.DoGet(url + "?t=0.6871670234037845");
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        // 获取响应体的输入流
        InputStream inputStream = response.body().byteStream();
        String filePath = "D:\\LiteloaderQQNT\\data\\LLOneBot\\temp\\temp.png";
        // 创建文件输出流
        FileOutputStream outputStream = new FileOutputStream(filePath);
        byte[] buffer = new byte[2048]; // 缓冲区
        int bytesRead;
        // 读取输入流并写入文件
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        // 关闭流
        outputStream.flush();
        System.out.println("图片下载成功，保存到: " + filePath);
        RawSendMessage image = MessageFactory.createGroupRawMessage(msg.getGroup_id(), "image", filePath);
        sendMessage(image);
    }

    @Action(cmd = "小红书")
    @GroupHandler
    public void AnalyXiaoHongShu(QQMessage msg) throws IOException {
        String url = msg.getMessage().get(1).getData().getText().split("-")[1];
        Pattern pattern = Pattern.compile("http://[\\w./]+");
        Matcher matcher = pattern.matcher(url);
        while (matcher.find()) {
            String link = matcher.group();
            Response response = null;
            try {
                response = OkHttpUtils.DoGet("https://api.pearktrue.cn/api/xhhimg/?url=" + link);
                if(response.isSuccessful()){
                    RawSendMessage message = MessageFactory.createGroupTextMessage(msg.getGroup_id(),
                            response.body().string());
                    response.close();
                    sendMessage(message);
                }
            } catch (IOException e) {
                RawSendMessage message = MessageFactory.createGroupTextMessage(msg.getGroup_id(),
                        "连接服务器超时宝贝！");
                sendMessage(message);
            }

        }
    }

    @Action(cmd = "一言")
    @GroupHandler
    public void HandleHitokoto(QQMessage msg) {
        Response response = null;
        try {
            response = OkHttpUtils.DoGet("https://api.pearktrue.cn/api/hitokoto/");
            String text = response.body().string();
            RawSendMessage message = MessageFactory.createGroupTextMessage(msg.getGroup_id(),
                    text);
            sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    
    public void sendMessage(RawSendMessage message) throws IOException {
        String sendMsg = JSON.toJSONString(message);
        okhttp3.MediaType JSON = okhttp3.MediaType.get("application/json; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(sendMsg, JSON);
        System.out.println("发送消息:" + sendMsg);
        OkHttpUtils.DoPost("http://localhost:3000/" + msgCat,
                body);
    }

    public void setMsgCat(String msgCat) {
        this.msgCat = msgCat;
    }
}
