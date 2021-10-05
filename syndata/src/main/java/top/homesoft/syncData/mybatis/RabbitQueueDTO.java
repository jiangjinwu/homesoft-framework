package top.homesoft.syncData.mybatis;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class RabbitQueueDTO implements Serializable {
    private String queueName;
    private Set<String> routeKeys;
}
