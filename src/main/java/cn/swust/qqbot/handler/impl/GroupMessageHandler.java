package cn.swust.qqbot.handler.impl;

import cn.swust.qqbot.annotation.Action;
import cn.swust.qqbot.constants.MessageBeanType;
import cn.swust.qqbot.constants.MessageSendApi;
import cn.swust.qqbot.factory.MessageFactory;
import cn.swust.qqbot.handler.MessageHandler;
import cn.swust.qqbot.models.QQMessage;
import cn.swust.qqbot.models.RawSendMessage;

import java.io.IOException;

public class GroupMessageHandler extends MessageHandler {

    @Action(cmd = "视频")
    public void HandleVideo(QQMessage msg) {

            String url = "https://jx.iqfk.top/api/sjsp.php";
            RawSendMessage message = MessageFactory.createGroupRawMessage(msg.getGroup_id(), MessageBeanType.VIDEO, url);
            try {
                sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

}
