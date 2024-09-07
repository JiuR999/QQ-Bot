package cn.swust.qqbot.factory;

import cn.swust.qqbot.models.RawSendMessage;

public class MessageFactory {

    public static RawSendMessage createGroupTextMessage(int groupId, String text) {
            RawSendMessage message = new RawSendMessage();
            message.setGroup_id(groupId);
        RawSendMessage.MessageBean messageBean = new RawSendMessage.MessageBean();
        messageBean.setType("text");
        messageBean.setData(new RawSendMessage.MessageBean.TextDataBean(text));
        message.setMessage(messageBean);
            return message;
    }

    public static RawSendMessage createGroupRawMessage(int groupId, String type,String file) {
        RawSendMessage message = new RawSendMessage();
        message.setGroup_id(groupId);
        RawSendMessage.MessageBean messageBean = new RawSendMessage.MessageBean();
        messageBean.setType(type);
        messageBean.setData(new RawSendMessage.MessageBean.FileDataBean(file));
        message.setMessage(messageBean);
        return message;
    }

    public static RawSendMessage createPrivateTextMessage(long userId, String text) {
        RawSendMessage message = new RawSendMessage();
        message.setUser_id(userId);
        RawSendMessage.MessageBean messageBean = new RawSendMessage.MessageBean();
        messageBean.setType("text");
        messageBean.setData(new RawSendMessage.MessageBean.TextDataBean(text));
        message.setMessage(messageBean);
        return message;
    }
}
