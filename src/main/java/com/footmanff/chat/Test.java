package com.footmanff.chat;

import java.net.InetSocketAddress;
import com.footmanff.chat.handler.EchoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * @author zhangli on 2018/1/7.
 */
public class Test {

    private final String host = "127.0.0.1";
    private final int port = 8080;

    private Bootstrap client;
    
    public Test() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new EchoClientHandler());
            }
        });
        client = bootstrap;
    }

    public void bootstrap() throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup());
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ServerChannelInitializer());
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(host, port));
        future.sync();
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Server bound");
                } else {
                    System.err.println("Bound attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });
    }
    
    public void shutdown(){
        client.group().shutdownGracefully();
    }

    public void connect(int localPort, String message) throws Exception {
        // ChannelFuture future = client.connect(new InetSocketAddress(host, port));
        // ChannelFuture future = client.connect();
        ChannelFuture future = client.connect(new InetSocketAddress(host, port), new InetSocketAddress(host, localPort));
        future.sync();
        System.out.println("conected");
        Channel channel = future.channel();
        
        channel.writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
        
        System.out.println("writed");
    }
    
    public void testLengthFieldHandler() throws Exception {
        ChannelFuture future = client.connect(new InetSocketAddress(host, port));
        future.sync();
        Channel channel = future.channel();
        String str = "你好咯";
        byte[] data = str.getBytes();
        ByteBuf byteBuf = Unpooled.buffer(data.length + 8);
        byteBuf.writeLong(Long.valueOf(data.length));
        byteBuf.writeBytes(data);
        channel.writeAndFlush(byteBuf);
    }

    public static void main(String[] args) throws Exception {
        Test test = new Test();
        test.bootstrap();
    }

}
