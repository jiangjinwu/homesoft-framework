package top.homesoft.syncData.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "canal")
public class CanalConfig {
    private String destination;

    private List<String> subscribe;

    private String username;

    private String password;

    private String subscribeChannel;

    private int fetchSize;

    private String zkAddress;

    private int queueSize;

    private int capacity;

}
