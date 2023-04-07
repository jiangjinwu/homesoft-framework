package top.homesoft.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.security.Principal;

@RestController
@SpringCloudApplication
@Slf4j
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }

    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> exchange.getPrincipal()
            .map(Principal::getName)
            .defaultIfEmpty("Default User")
            .map(userName -> {
                //adds header to proxied request
                exchange.getRequest().mutate().header("CUSTOM-REQUEST-HEADER", userName).build();
                return exchange;
            })
            .flatMap(chain::filter);
    }

    @Bean
    public GlobalFilter customGlobalPostFilter() {
        return (exchange, chain) -> chain.filter(exchange)
            .then(Mono.just(exchange))
            .map(serverWebExchange -> {
                //adds header to response
                serverWebExchange.getResponse().getHeaders().set("CUSTOM-RESPONSE-HEADER",
                    HttpStatus.OK.equals(serverWebExchange.getResponse().getStatusCode()) ? "It worked": "It did not work");
                return serverWebExchange;
            })
            .then();
    }


    @Value("${remote.home}")
    private URI home;

//    @GetMapping("/test")
//    public Mono<ResponseEntity<byte[]>> proxy(ProxyExchange<byte[]> proxy) throws Exception {
//        return proxy.uri(home.toString() + "/image/png").get();
//    }


    @Bean
    @Primary
    public KeyResolver ipKeyResolver() {
        return new IpKeyResolver();
    }

    @Bean
    public KeyResolver uriKeyResolver() {
        return new UriKeyResolver();
    }

}
