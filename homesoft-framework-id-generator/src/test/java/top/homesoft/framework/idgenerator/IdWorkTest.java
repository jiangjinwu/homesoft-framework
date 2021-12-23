package top.homesoft.framework.idgenerator;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IDGenServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IdWorkTest {
    @Autowired
    Snowflake snowflake;
    @Test
    public void test(){
   //Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);

        logger.info("{}",  snowflake.nextId());
        logger.info("{}",  snowflake.nextId());
        logger.info("{}",  snowflake.nextId());
    }
}
