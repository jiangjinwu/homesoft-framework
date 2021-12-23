package top.homesoft.framework.idgenerator;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

/**
 * @author yuanjie
 */
@Slf4j
@Service
@Data
public class SimpleIdGenerator {
    @Value("${c3.snowflake.worker-id:1}")
    private Long workerId;

    @Value("${c3.snowflake.datacenter-id:1}")
    private Long datacenterId;

    @PostConstruct
    void initialize() {
        Assert.isTrue(workerId > 0, "Snowflake workerId must > 0");
        if (workerId == 1) {
            logger.error(">>>>>>>> 请手动指定workerId， 否则可能生成重复ID <<<<<<<<");
        }
    }

    public synchronized long nextId() {
        Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);
        return snowflake.nextId();
    }


}
