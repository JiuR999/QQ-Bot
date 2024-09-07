package cn.swust.qqbot.intercept;

import cn.swust.qqbot.annotation.Action;
import cn.swust.qqbot.annotation.GroupHandler;
import cn.swust.qqbot.annotation.PrivateHandler;
import cn.swust.qqbot.constants.MessageSendApi;
import cn.swust.qqbot.handler.MessageHandler;
import cn.swust.qqbot.handler.impl.GroupMessageHandler;
import cn.swust.qqbot.models.QQMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Aspect
@Component
public class MessageInterceptor {
    @Autowired
    private MessageHandler messageHandler;

    @Around("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        QQMessage msg = (QQMessage) args[0];
        if(msg.getMessage_type().equals("group")) {
            messageHandler.setMsgCat(MessageSendApi.SEND_GROUP_MSG);
            for (Method method : messageHandler.getClass().getMethods()) {
                if (method.isAnnotationPresent(GroupHandler.class)) {
                    Action action = method.getAnnotation(Action.class);
                    String cmd = msg.getMessage().get(1).getData().getText();
                    if(cmd.contains(action.cmd())) {
                        method.invoke(messageHandler, msg);
                    }
                    System.out.println("拦截到消息:" + msg);
                }
            }
        } else {
            messageHandler.setMsgCat(MessageSendApi.SEND_PRIVATE_MSG);
        }

        return "未知消息类型";
    }
}