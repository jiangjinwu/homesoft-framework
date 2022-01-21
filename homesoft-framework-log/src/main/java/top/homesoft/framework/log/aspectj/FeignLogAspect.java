package top.homesoft.framework.log.aspectj;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.homesoft.framework.log.domain.systemlog.entity.SystemLogPO;
import top.homesoft.framework.log.domain.systemlog.repository.SystemLogRepository;
import top.homesoft.framework.log.infrastructure.config.SystemLogConfig;

import java.util.Date;

/**
 * @author
 */
@Aspect
@Slf4j
@Component
public class FeignLogAspect extends  BaseLogAspect{
    SystemLogPO msgLog= null;
    @Autowired
    SystemLogConfig systemLogConfig;

    @Autowired
    private SystemLogRepository logService;

    @Before("within(feign.Client+)")
    public void before(JoinPoint pjp) {
        logger.info("within(feign.Client+) pjp {}, args:{}", pjp, pjp.getArgs());
    }

    @Pointcut("@annotation(io.swagger.annotations.ApiOperation) && (@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping))")
    public void controllerAspect(){}

    /**
     * 前置通知 用于拦截Controller层记录用户的操作的开始时间
     * @param joinPoint 切点
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint){
        Date beginTime=new Date();


    }

    @Before("@within(org.springframework.cloud.openfeign.FeignClient)")
    public void beforeOpenfeign(JoinPoint joinPoint) {
        msgLog=new SystemLogPO();
        setRequest(msgLog,joinPoint);
        logger.info("  beforeOpenfeign {} ", msgLog.getRequestUri());
    }


    @AfterReturning(returning = "result", pointcut = "@within(org.springframework.cloud.openfeign.FeignClient)")
    public void result(Object result) {
        super.setResult(msgLog,result);
        logService.saveLog(msgLog);
    }

}
