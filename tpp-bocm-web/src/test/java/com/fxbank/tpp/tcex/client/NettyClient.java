package com.fxbank.tpp.tcex.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    public void start(EchoClientHandler echoHandler) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).remoteAddress("127.0.0.1", 8080)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(echoHandler);
                        }
                    });
            ChannelFuture future = b.connect().sync();
            ChannelFuture ff = future.channel().writeAndFlush("OK");// TODO
            ff.addListener(new ChannelFutureListener() {

                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("write ok");
                    } else {

                    }
                }
            });
            System.out.println("connect success!");
            future.channel().closeFuture().sync();
            System.out.println("close future");
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        EchoClientHandler echoHandler = new EchoClientHandler();
        NettyClient nc = new NettyClient();
        nc.start(echoHandler);
    }
}