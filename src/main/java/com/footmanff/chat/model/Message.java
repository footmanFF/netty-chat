package com.footmanff.chat.model;

import com.footmanff.chat.base.ErrorEnum;
import com.footmanff.chat.base.MessageType;
import com.footmanff.chat.util.IdUtil;
import lombok.Data;
import java.util.Date;

/**
 * @author zhangli on 10/01/2018.
 */
@Data
public class Message {

    /**
     * 消息id
     */
    private String id;
    
    /**
     * 消息发送方标识
     */
    private String from;

    /**
     * 消息到达方标识
     */
    private String to;

    /**
     * 消息类型
     */
    private MessageType type;
    
    /**
     * 消息
     */
    private String msg;

    /**
     * 发送日期
     */
    private Date sendDate;

    /**
     * 发送失败的消息
     */
    public static Message error(ErrorEnum errorEnum, String to){
        Message msg = new Message();
        msg.setId(IdUtil.id());
        msg.setFrom("");
        msg.setTo(to);
        msg.setType(MessageType.SYSTEM_MSG);
        msg.setMsg(errorEnum.getCode() + " : " + errorEnum.getMsg());
        msg.setSendDate(new Date());
        return msg;
    }
    
}
