package com.fxbank.cap.paf.nettty.client;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fxbank.cap.paf.nettty.SocketLenghtDecoder;
import com.fxbank.cap.paf.nettty.SocketLengthEncoder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
 
@Component
public class ClientConfig {
 
	@Autowired
	private SocketLengthEncoder socketLengthEncoder;

	@Autowired
	private ClientHandler clientHandler;

	@Autowired
	private LogPool logPool;
	
	@Resource
	private MyJedis myJedis;
    
    public SocketChannel connect(String host,int port){
    	SocketChannel socketChannel = null;
    	ChannelFuture future = null;
    	try {
    		EventLoopGroup eventLoopGroup=new NioEventLoopGroup(2);
    		Bootstrap bootstrap=new Bootstrap();
    		bootstrap.channel(NioSocketChannel.class);
    		bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
    		bootstrap.group(eventLoopGroup);
    		bootstrap.remoteAddress(host,port);
    		bootstrap.handler(new ChannelInitializer<SocketChannel>(){
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					p.addLast(new SocketLenghtDecoder(logPool,myJedis));
					p.addLast(socketLengthEncoder);
					p.addLast(clientHandler);
				}
    		});
    		future =bootstrap.connect(host,port).sync();
    		if (future.isSuccess()) {
    			socketChannel = (SocketChannel)future.channel();
    			System.out.println("connect server  成功---------");
    		}else{
    			System.out.println("连接失败！");
    			System.out.println("准备重连！");
    		}
    	} catch (Exception e) {
    		System.out.println("连接异常"+e);
    	}finally{
//    		if(null != future){
//    			if(null != future.channel() && future.channel().isOpen()){
//    				future.channel().close();
//    			}
//    		}
//    		System.out.println("准备重连！");
//    		start();
    	}
		return socketChannel;
    }
}