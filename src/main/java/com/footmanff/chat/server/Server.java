package com.footmanff.chat.server;

import com.footmanff.chat.base.SystemException;
import com.footmanff.chat.handler.MessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import java.net.InetSocketAddress;

/**
 * @author zhangli on 10/01/2018.
 */
@Slf4j
public class Server {
    
    private int port;
    private ServerBootstrap bootstrap;
    
    public Server(int port) {
        this.port = port;
        bootstrap = new ServerBootstrap();
        bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup());
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new LengthFieldBasedFrameDecoder(65 * 1024, 0, 8));
                pipeline.addLast(new MessageHandler());
                
            }
        });
    }
    
    public void start(){
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(port));
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    log.info("netty服务器启动成功");
                } else {
                    throw new SystemException("netty服务器启动失败", future.cause());
                }
            }
        });
    }
    
}
