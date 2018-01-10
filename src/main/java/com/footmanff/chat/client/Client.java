package com.footmanff.chat.client;

import com.footmanff.chat.base.SystemException;
import com.footmanff.chat.handler.MessageHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangli on 10/01/2018.
 */
@Slf4j
public class Client {
    
    /**
     * 本地用户名
     */
    private String userName;

    /**
     * 服务端ip
     */
    private String serverIp;

    /**
     * 服务端端口
     */
    private int serverPort;

    private ChannelHandlerContext channelHandlerContext;
    private Bootstrap bootstrap;

    public Client(String serverIp, int serverPort) {
        if (StringUtils.isBlank(serverIp) || serverPort <= 0) {
            throw new IllegalArgumentException("serverIp or serverPort invalid");
        }
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                
                
            }
        });
        try {
            bootstrap.connect(serverIp, serverPort).sync();
        } catch (InterruptedException e) {
            throw new SystemException(e);
        }
    }

    /**
     * 发送消息
     *
     * @param msg 消息内容
     */
    public void send(String msg) {

    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    public static void main(String[] args) {
        log.info("info");
        log.warn("warn");
        log.error("error");
    }

}