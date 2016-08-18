package kr.ec.queryfly.analyzer.config;

import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

@Component
public class AppServerBootstrapper {

  private final InetSocketAddress address;

  private final int workerThreadCount;

  private final int bossThreadCount;

  @Autowired
  public AppServerBootstrapper(@Qualifier("tcpSocketAddress") InetSocketAddress address,
      @Qualifier("workerThreadCount") int workerThreadCount,
      @Qualifier("bossThreadCount") int bossThreadCount) {
    this.address = address;
    this.workerThreadCount = workerThreadCount;
    this.bossThreadCount = bossThreadCount;
  }

  public void start() {
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workerGroup = new NioEventLoopGroup(workerThreadCount);
    ChannelFuture channelFuture = null;

    try {

      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
          .handler(new LoggingHandler(LogLevel.INFO)).childHandler(new AppServerInitializer(null));
      Channel ch = b.bind(address).sync().channel();
      channelFuture = ch.closeFuture();
      channelFuture.sync();

    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }

}
