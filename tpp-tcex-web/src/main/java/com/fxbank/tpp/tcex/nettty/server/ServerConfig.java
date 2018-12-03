package com.fxbank.tpp.tcex.nettty.server;

import java.security.cert.CertificateException;

import javax.net.ssl.SSLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

@Configuration
@ConfigurationProperties(prefix="netty.nioserver")
public class ServerConfig {

	private static Logger logger = LoggerFactory.getLogger(ServerConfig.class);

	private boolean ssl;

	private int port;

	private int bossgroup;

	private int workgroup;

	private SslContext sslCtx;

	@Autowired
	@Qualifier(value = "serverInitializer")
	ServerInitializer serverInitializer;

	@SuppressWarnings("deprecation")
	@Autowired
	public void serve() {
		// Configure SSL.
		sslCtx = null;
		if (ssl) {
			try {
				SelfSignedCertificate ssc = new SelfSignedCertificate();
				sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
			} catch (SSLException e) {
				e.printStackTrace();
			} catch (CertificateException e) {
				e.printStackTrace();
			}
		} else {
			sslCtx = null;
		}

		Runnable nio = new Runnable() {
			public void run() {
				nio();
			}
		};

		new Thread(nio).start();
	}

	private void nio() {
		EventLoopGroup bossGroup = new NioEventLoopGroup(bossgroup);
		EventLoopGroup workerGroup = new NioEventLoopGroup(workgroup);
		try {
			serverInitializer.setSslCtx(sslCtx);
			ServerBootstrap b = new ServerBootstrap();
			b.option(ChannelOption.SO_BACKLOG, 1024);
			// 通过NoDelay禁用Nagle,使消息立即发出去，不用等待到一定的数据量才发出去
			b.option(ChannelOption.TCP_NODELAY, true);
			b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 600000);
			// 保持长连接状态
			b.childOption(ChannelOption.SO_KEEPALIVE, false);
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(serverInitializer);
			Channel ch = b.bind(port).sync().channel();

			logger.info("Server started: " + (ssl ? "https" : "http") + "://127.0.0.1:" + port + '/');

			ch.closeFuture().sync();
		} catch (InterruptedException e) {
			logger.error("", "", "", "", e.getMessage());
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public boolean isSsl() {
		return ssl;
	}

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getBossgroup() {
		return bossgroup;
	}

	public void setBossgroup(int bossgroup) {
		this.bossgroup = bossgroup;
	}

	public int getWorkgroup() {
		return workgroup;
	}

	public void setWorkgroup(int workgroup) {
		this.workgroup = workgroup;
	}
}
