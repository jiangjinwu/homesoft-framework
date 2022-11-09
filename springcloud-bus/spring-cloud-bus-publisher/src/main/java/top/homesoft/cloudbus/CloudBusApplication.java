package top.homesoft.cloudbus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;


import java.net.SocketException;
import java.net.UnknownHostException;

@SpringBootApplication
@Configuration
@RemoteApplicationEventScan(basePackageClasses = CacheEvictEvent.class)
@EnableDiscoveryClient
public class CloudBusApplication {

//    @Autowired
//    MachineIdProvider machineIdProvider;
    public static void main(String[] args) throws SocketException, UnknownHostException {

        SpringApplication.run(CloudBusApplication.class, args);
        //machineIdProvider.getMachineId(InnerIpAddressUtils.getInnerIpAddress());
     /*   new SpringApplicationBuilder(CloudBusApplication.class)
                .properties("server.port=0") // Random server port
                .properties("management.endpoints.web.exposure.include=*") // exposure
                // includes
                // all
                .properties("spring.cloud.bus.trace.enabled=true") // Enable trace
                .run(args);*/
    }
}
