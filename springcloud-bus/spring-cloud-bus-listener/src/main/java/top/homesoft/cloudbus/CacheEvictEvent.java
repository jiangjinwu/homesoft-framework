package top.homesoft.cloudbus;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

public class CacheEvictEvent extends RemoteApplicationEvent {
    public CacheEvictEvent(Object body,String source,String dest){
        super(body,source,dest);
    }
}
