package top.homesoft.framework.log.aspectj;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.homesoft.framework.log.domain.systemlog.entity.SystemLogPO;
import top.homesoft.framework.log.infrastructure.config.SystemLogConfig;
import top.homesoft.framework.log.infrastructure.util.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class BaseLogAspect {
    protected  SystemLogConfig systemLogConfig;
    public void setResult(SystemLogPO msgLog, Object result) {
        String rspmsg = null;
        if (msgLog != null) {
            // 响应时间
            msgLog.setRspTime(new Date());
            // 响应内容
            if (result != null) {
                if (result instanceof String)
                    rspmsg = result.toString();
                else
                    rspmsg = JSON.toJSONString(result);
                msgLog.setRspMsg(rspmsg);
            }
            // 只要调用成功，就算成功，不管业务逻辑
            //msgLog.setRspStatus("成功");
            // 耗时
            if (msgLog.getOperateDate() != null) {
                msgLog.setTimeout(msgLog.getRspTime().getTime() - msgLog.getOperateDate().getTime());
            }
            LoggerFactory.getLogger(msgLog.getClass()).info("交易耗时" + msgLog.getTimeout() + "毫秒，响应信息：" + msgLog.getRspMsg());
        }
    }

    public void setRequest(SystemLogPO log,JoinPoint joinPoint) {
        String rspmsg = null;
        HttpServletRequest request = this.getRequest();
        if (log != null && Objects.nonNull(request)) {
            // 响应时间
            log.setTitle(getControllerMethodDescription2(joinPoint));
            //log.setType(Log.TYPE_INFO);
            log.setRemoteAddr(request.getRemoteAddr());
            log.setRequestUri(request.getRequestURI());
            log.setMethod(request.getMethod());
            log.setReqParams(this.buildRequestParams(request.getParameterMap()));
            log.setParams(JSON.toJSON(joinPoint.getArgs()).toString());
            Set filter = systemLogConfig.getHeader().getExcludes();
            Map<String,String> headerMap = RequestUtil.getHeaderMap(request,filter);

            log.setReqHeader(JSON.toJSONString(headerMap));
            LoggerFactory.getLogger(log.getClass()).info("交易耗时" + log.getTimeout() + "毫秒，响应信息：" + log.getRspMsg());
        }else if(Objects.nonNull(log)){
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            log.setRequestUri(methodSignature.getDeclaringTypeName()+"."+methodSignature.getMethod().getName());
            log.setParams(JSON.toJSON(joinPoint.getArgs()).toString());
            log.setOperateDate(new Date());
        }
    }

    public HttpServletRequest getRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();
        return request;
    }


    private String buildRequestParams(Map<String, String[]> paramMap) {
        StringBuilder params = new StringBuilder();

        for (Map.Entry<String, String[]> param : ((Map<String, String[]>)paramMap).entrySet()){
            params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            //params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 100));
            params.append(  paramValue);
        }

        return params.toString();
    }

    public static String getControllerMethodDescription2(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ApiOperation apiOperation = method
            .getAnnotation(ApiOperation.class);
        String discription = apiOperation.value();
        return discription;
    }
}
