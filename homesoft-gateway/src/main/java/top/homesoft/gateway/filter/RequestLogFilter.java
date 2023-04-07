package top.homesoft.gateway.filter;

import cn.hutool.core.exceptions.ExceptionUtil;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author zengchuiyu
 * @Date 2022/9/21 16:10
 * @Version 1.0
 **/
@Slf4j
@Component
public class RequestLogFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            ServerHttpRequest request = exchange.getRequest();

            // **构建成一条长 日志，避免并发下日志错乱**
            StringBuilder reqLog = new StringBuilder(200);
            List<Object> reqArgs = new ArrayList<>();

            reqLog.append("================ Gateway Request Data  ================\n");
            reqLog.append("Method: {}\n");
            reqLog.append("Url: {}\n");
            reqLog.append("ContentType: {}\n");
            reqArgs.add(request.getMethodValue());
            reqArgs.add(request.getURI());
            MediaType mediaType = request.getHeaders().getContentType();
            reqArgs.add(mediaType);

            // 打印请求头
            HttpHeaders headers = request.getHeaders();
            reqLog.append("Headers======>");
            headers.forEach((headerName, headerValue) -> {
                if(headerName.startsWith("x-c3-")
                   // && !KeyConstants.KEY_X_C3_TOKEN.equals(headerName)
                ) {
                    reqLog.append("\n         {} = {}");
                    reqArgs.add(headerName);
                    reqArgs.add(headerValue);
                }
            });

            // 打印Body
            reqLog.append("\nRequest Body======>");
            if (MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(mediaType) || MediaType.APPLICATION_JSON.isCompatibleWith(mediaType)) {
                return writeBodyLog(exchange, chain, reqLog, reqArgs);
            } else {
                return writeBasicLog(exchange, chain, reqLog, reqArgs);
            }

        } catch (Exception e) {
            logger.error("Print Request Log error: {}", ExceptionUtil.stacktraceToString(e));
        }
        return chain.filter(exchange);
    }

    private Mono<Void> writeBasicLog(ServerWebExchange exchange, GatewayFilterChain chain, StringBuilder reqLog, List<Object> reqArgs) {
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
            reqLog.append("\n         ").append(entry.getKey()).append(" = ").append(entry.getValue());
        }
        logger.info(reqLog.toString(), reqArgs.toArray());
        return chain.filter(exchange);
    }

    /**
     * 解决 request body 只能读取一次问题，
     * 参考: org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory
     * @param exchange
     * @param chain
     * @param reqLog
     * @return
     */
    @SuppressWarnings("unchecked")
    private Mono<Void> writeBodyLog(ServerWebExchange exchange, GatewayFilterChain chain, StringBuilder reqLog, List<Object> reqArgs) {
        ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
        Mono<String> modifiedBody = serverRequest.bodyToMono(String.class)
                .flatMap(body ->{
                    reqLog.append("\n        ").append(body);
                    return Mono.just(body);
                });

        // 通过 BodyInserter 插入 body(支持修改body), 避免 request body 只能获取一次
        BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());
        // the new content type will be computed by bodyInserter
        // and then set in the request decorator
        headers.remove(HttpHeaders.CONTENT_LENGTH);

        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);

        return bodyInserter.insert(outputMessage,new BodyInserterContext())
                .then(Mono.defer(() -> {
                    // 重新封装请求
                    ServerHttpRequest decoratedRequest = requestDecorate(exchange, headers, outputMessage);

                    // 记录响应日志
//                    ServerHttpResponseDecorator decoratedResponse = recordResponseLog(exchange, gatewayLog);
                    logger.info(reqLog.toString(), reqArgs.toArray());

                    // 记录普通的
                    return chain.filter(exchange.mutate().request(decoratedRequest).response(exchange.getResponse()).build())
                            .then(Mono.fromRunnable(() -> {
                            }));
                }));

    }

    /**
     * 请求装饰器，重新计算 headers
     * @param exchange
     * @param headers
     * @param outputMessage
     * @return
     */
    private ServerHttpRequestDecorator requestDecorate(ServerWebExchange exchange, HttpHeaders headers,
                                                       CachedBodyOutputMessage outputMessage) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                if (contentLength > 0) {
                    httpHeaders.setContentLength(contentLength);
                } else {
                    // TODO: this causes a 'HTTP/1.1 411 Length Required' // on
                    // httpbin.org
                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                }
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }

    @Override
    public int getOrder() {
        return -100;
    }
}

