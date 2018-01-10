package com.footmanff.chat.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangli on 2018/1/7.
 */
public class MessageHandler1 extends ChannelInboundHandlerAdapter {

    private static Map<String, ChannelHandlerContext> contexts = new ConcurrentHashMap<String, ChannelHandlerContext>();
    
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("contexts size: " + contexts.size());
        System.out.println("注册! " + System.currentTimeMillis());
        ctx.fireChannelRegistered();
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("读取! " + System.currentTimeMillis());
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("Server received: " + byteBuf.toString(CharsetUtil.UTF_8));
//        for (ChannelHandlerContext context : contexts) {
//            System.out.println("Server write: " + byteBuf.toString(CharsetUtil.UTF_8));
//            context.write(byteBuf.copy());
//            context.flush();
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
}