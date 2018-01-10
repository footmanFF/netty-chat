package com.footmanff.chat.handler;

import com.alibaba.fastjson.JSON;
import com.footmanff.chat.base.ErrorEnum;
import com.footmanff.chat.base.MessageType;
import com.footmanff.chat.model.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangli on 10/01/2018.
 */
@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private ConcurrentHashMap<String, ChannelHandlerContext> userMap = new ConcurrentHashMap<String, ChannelHandlerContext>();
    
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        msg.readerIndex(8);
        Message message = JSON.parseObject(msg.toString(CharsetUtil.UTF_8), Message.class);
        String from = message.getFrom().intern();
        if (message.getType() == MessageType.CONNECT) {
            synchronized (from) {
                ChannelHandlerContext exist = userMap.get(from);
                if (exist != null) {
                    ctx.writeAndFlush(Message.error(ErrorEnum.AUTH_FAILED, from));
                    // 不去fire下一个handler
                } else {
                    userMap.put(from, ctx);
                    ctx.fireChannelRead(message);
                }
            }
        } else if (message.getType() == MessageType.USER_MSG){
            ChannelHandlerContext exist = userMap.get(from);
            if (exist == null) {
                ctx.writeAndFlush(Message.error(ErrorEnum.NO_PERMISSION, from));
            } else {
                ctx.fireChannelRead(message);
            }
        } else {
            ctx.writeAndFlush(Message.error(ErrorEnum.ERROR_CLIENT_MSG, from));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("消息反序列化失败", cause);
        ctx.writeAndFlush("");
    }
    
    // TODO 连接断掉需要去userMap删除context
    // TODO 连接超时重连、心跳
    // TODO 客户端意外断线处理
}
