package top.homesoft.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author yuanjie
 */
@Slf4j
public class UriKeyResolver implements KeyResolver {
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        if (logger.isDebugEnabled()) {
            logger.debug("Uri key resolver :" + exchange.getRequest().getURI().getPath());
        }
        return Mono.just(exchange.getRequest().getURI().getPath());
    }
}
