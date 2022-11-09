package top.homesoft.framework.idgenerator.configuration;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import top.homesoft.framework.idgenerator.support.RedisMachineIdProvider;
import top.homesoft.framework.idgenerator.support.ZookeeperMachineIdProvider;
import top.homesoft.framework.idgenerator.utils.InnerIpAddressUtils;

import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Copyright:
 * Description:
 * Date: 2021/7/7 5:11 下午
 * @author snowxuyu
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(UidGeneratorProperties.class)
@AllArgsConstructor
public class UidGeneratorAutoConfigure {

    private static final long MAX_SEQUENCE = -1L ^ (-1L << 12L);
    private static final long MACHINE_MASK = 1 << 5;
    private final StringRedisTemplate  redisTemplate;

    @Bean
    @ConditionalOnProperty(prefix = "uid.generator.config", value = "strategy", havingValue = "redis")
    public RedisMachineIdProvider machineRedisIdProvider(UidGeneratorProperties uidGeneratorProperties) {
//        RedisStandaloneConfiguration standaloneConfig = getStandaloneConfig(uidGeneratorProperties.getRedis());
//        LettuceClientConfiguration clientConfiguration = getLettuceClientConfiguration(uidGeneratorProperties.getRedis());
//        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(standaloneConfig, clientConfiguration);
//        lettuceConnectionFactory.afterPropertiesSet();

       // StringRedisTemplate template = new StringRedisTemplate(lettuceConnectionFactory);

        return new RedisMachineIdProvider(redisTemplate);
    }

    @Bean
    @ConditionalOnProperty(prefix = "uid.generator.config", value = "strategy", havingValue = "zookeeper")
    public ZookeeperMachineIdProvider machineZookeeperIdProvider(UidGeneratorProperties uidGeneratorProperties) {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(uidGeneratorProperties.getZookeeper().getConnectString())
                .retryPolicy(new ExponentialBackoffRetry(uidGeneratorProperties.getZookeeper().getBaseSleepTimeMs(),
                        uidGeneratorProperties.getZookeeper().getMaxRetries()))
                .namespace(uidGeneratorProperties.getZookeeper().getNamespace())
                .build();
        return new ZookeeperMachineIdProvider(curatorFramework);
    }

    @Bean
    @ConditionalOnBean(RedisMachineIdProvider.class)
    public Snowflake idWorkerRedis(RedisMachineIdProvider machineIdProvider) throws SocketException, UnknownHostException {
        logger.info("===============初始化基于redis的id生成器===============");
        String innerIpAddress = InnerIpAddressUtils.getInnerIpAddress();
        long machineId = machineIdProvider.getMachineId(innerIpAddress);
        long workerId = machineId % MACHINE_MASK;
        long dataCenterId = machineId >> 5;
        Snowflake snowflake = IdUtil.createSnowflake(workerId, dataCenterId);
        return  snowflake;
    }

    @Bean
    @ConditionalOnBean(ZookeeperMachineIdProvider.class)
    public Snowflake idWorkerZookeeper(ZookeeperMachineIdProvider machineIdProvider) throws SocketException, UnknownHostException {
        logger.info("===============初始化基于zookeeper的id生成器===============");
        String innerIpAddress = InnerIpAddressUtils.getInnerIpAddress();
        long machineId = machineIdProvider.getMachineId(innerIpAddress);
        long workerId = machineId % MACHINE_MASK;
        long dataCenterId = machineId >> 5;
        return   IdUtil.createSnowflake(workerId, dataCenterId);
    }


    private RedisStandaloneConfiguration getStandaloneConfig(RedisProperties redisProperties) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisProperties.getHost());
        config.setPort(redisProperties.getPort());
        config.setPassword(RedisPassword.of(redisProperties.getPassword()));
        config.setDatabase(redisProperties.getDatabase());
        return config;
    }

//    private LettuceClientConfiguration getLettuceClientConfiguration(RedisProperties redisProperties) {
//        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder;
//        RedisProperties.Pool pool = redisProperties.getLettuce().getPool();
//        if ( pool == null ) {
//            builder = LettuceClientConfiguration.builder();
//        } else {
//            builder = new PoolBuilderFactory().createBuilder(pool);
//        }
//        if ( redisProperties.getTimeout() != null ) {
//            builder.commandTimeout(redisProperties.getTimeout());
//        }
//        if ( redisProperties.getLettuce() != null ) {
//            RedisProperties.Lettuce lettuce = redisProperties.getLettuce();
//            if ( lettuce.getShutdownTimeout() != null
//                    && !lettuce.getShutdownTimeout().isZero() ) {
//                builder.shutdownTimeout(redisProperties.getLettuce().getShutdownTimeout());
//            }
//        }
//        builder.clientResources(DefaultClientResources.create());
//        return builder.build();
//    }

    /**
     * Inner class to allow optional commons-pool2 dependency.
     */
//    private static class PoolBuilderFactory {
//
//        public LettuceClientConfiguration.LettuceClientConfigurationBuilder createBuilder(RedisProperties.Pool pool) {
//            return LettucePoolingClientConfiguration.builder().poolConfig(getPoolConfig(pool));
//        }
//
//        private GenericObjectPoolConfig<?> getPoolConfig(RedisProperties.Pool pool) {
//            GenericObjectPoolConfig<?> config = new GenericObjectPoolConfig<>();
//            config.setMaxTotal(pool.getMaxActive());
//            config.setMaxIdle(pool.getMaxIdle());
//            config.setMinIdle(pool.getMinIdle());
//            if ( pool.getMaxWait() != null ) {
//                config.setMaxWaitMillis(pool.getMaxWait().toMillis());
//            }
//            return config;
//        }
//    }
}
