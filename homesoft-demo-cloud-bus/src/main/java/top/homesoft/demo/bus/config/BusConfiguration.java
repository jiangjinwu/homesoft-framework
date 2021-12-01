package top.homesoft.demo.bus.config;

import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@RemoteApplicationEventScan("top.homesoft.demo.bus")
public class BusConfiguration {
}
