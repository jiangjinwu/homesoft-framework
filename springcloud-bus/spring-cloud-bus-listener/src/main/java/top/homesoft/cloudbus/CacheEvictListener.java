package top.homesoft.cloudbus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CacheEvictListener implements ApplicationListener<CacheEvictEvent> {

    @Override
    public void onApplicationEvent(CacheEvictEvent cacheEvictEvent) {
        logger.info("Received MyCustomRemoteEvent - message: ");
    }
}
