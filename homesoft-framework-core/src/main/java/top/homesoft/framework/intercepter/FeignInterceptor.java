package top.homesoft.framework.intercepter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnClass(RequestInterceptor.class)
@SuppressWarnings("unused")
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
       /* String traceId = MDC.get(TenmaoConstant.TRACE_KEY);
        logger.info("set traceId: url[{}], traceId[{}]", requestTemplate.url(), traceId);
        requestTemplate.header(TenmaoConstant.TRACE_KEY, traceId);

        if (!Strings.isNullOrEmpty(authProperties.getAppId()) && !Strings.isNullOrEmpty(authProperties.getAppKey())) {
            long timestamp = System.currentTimeMillis();
            String md5 = Md5Util.calcMD5(authProperties.getAppId() + authProperties.getAppKey() + timestamp);

            //对服务调用进行签名，当前签名方法比较简单，以后可以支持更加复杂的签名计算（比如读取参数内容，组合后再进行签名计算）
            requestTemplate.header("appId", authProperties.getAppId());
            requestTemplate.header("timestamp", Long.toString(timestamp));
            requestTemplate.header("sign", md5);

            //一些接口的调用需要实现幂等，比如消息发送，如果使用requestId就可以方便服务方实现幂等
            requestTemplate.header("requestId", UUID.randomUUID().toString().replaceAll("-", ""));
        }*/
    }
}
