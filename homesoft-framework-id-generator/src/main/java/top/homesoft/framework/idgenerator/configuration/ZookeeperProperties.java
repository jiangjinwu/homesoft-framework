package top.homesoft.framework.idgenerator.configuration;

import lombok.Data;

@Data
public class ZookeeperProperties {
    /**
     * 连接地址  ip1:port1,ip2:port2,ip3:port3
     */
    private String connectString;
    /**
     * 命名空间，默认为IdWorker
     */
    private String namespace = "IdWorker";
    /**
     * 基本休眠时间（单位：毫秒），默认为3000毫秒
     */
    private Integer baseSleepTimeMs = 3000;
    /**
     * 最大重试次数，默认为3次
     */
    private Integer maxRetries = 3;
}
