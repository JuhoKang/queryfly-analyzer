package kr.re.ec.queryfly.analyzer.config;

import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan("kr.re.ec.queryfly.analyzer.config, kr.re.ec.queryfly.analyzer.service, kr.re.ec.queryfly.analyzer.core")
@PropertySource("classpath:appserver.properties")
public class AppServerContextConfig {

  @Value("${boss.thread.count}")
  private int bossThreadCount;

  @Value("${worker.thread.count}")
  private int workerThreadCount;

  @Value("${tcp.port}")
  private int tcpPort;

  @Bean(name = "bossThreadCount")
  public int getBossThreadCount() {
    return bossThreadCount;
  }

  @Bean(name = "workerThreadCount")
  public int getWorkerThreadCount() {
    return workerThreadCount;
  }

  public int getTcpPort() {
    return tcpPort;
  }

  @Bean(name = "tcpSocketAddress")
  public InetSocketAddress tcpPort() {
    return new InetSocketAddress(tcpPort);
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

}
