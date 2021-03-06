package top.homesoft.framework.log.aspectj;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import feign.Request;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.homesoft.framework.log.domain.systemlog.entity.SystemLogPO;
import top.homesoft.framework.log.domain.systemlog.repository.SystemLogRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 系统日志切点类
 * @author linrx
 *
 */



public class FeignSystemLogAspect {
  private static final Logger logger = LoggerFactory.getLogger(FeignSystemLogAspect. class);
  /**用户session key 正式环境从配置文件中取,这里仅作为演示*/
  private static final String SESSION_USER_KEY = "user";
  private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<Date>("ThreadLocal beginTime");
  private static final ThreadLocal<SystemLogPO> logThreadLocal = new NamedThreadLocal<SystemLogPO>("ThreadLocal log");




  SystemLogPO msgLog;

  @Autowired
  private SystemLogRepository logService;

	// 切点一：Feign中的ribbon.LoadBalancerFeignClient调用入口，为了获取请求Request
	private final String pointcutRibbon = "execution(* org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient.execute(..))";
	// 切点二：FeignClient接口，为了获取响应字符串
	private final String pointcutFeign = "execution(* com.jubaozan.feign..*(..))";


	@Pointcut("within(org.springframework.cloud.openfeign.FeignClient+) &&  within(com.jubaozan.service.user..*)")
	public void beanAnnotatedWithFeignClient() {}

	@Pointcut(value = pointcutRibbon)
	public void logRibbon() {
	}

	//Feign接口切点
	@Pointcut(value = pointcutFeign)
	public void logFeign() {
	}

	//@Before(value = "logFeign()")
	@Before("within(feign.Client+)")
	public void beforeFeign(JoinPoint joinPoint) {
		try {
			  msgLog=new SystemLogPO();

			// 请求时间
			msgLog.setOperateDate(new Date());
			// 请求方 本系统
//			msgLog.setReqFrom(SystemEnum.SYSTEM_SELF.getCode());
//			// 被请求方 外系统
//			msgLog.setReqTo(SystemEnum.SYSTEM_OTHER.getCode());
//			// 调用类
//			MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//			msgLog.setClassName(methodSignature.getDeclaringTypeName());
//			// 调用方法
//			msgLog.setClassFunction(methodSignature.getMethod().getName());
			// 请求数据
			String reqmsg = JSON.toJSONString(joinPoint.getArgs());
			msgLog.setParams(reqmsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 在调用LoadBalancerFeignClient中的execute方法前，获取请求信息
	 */
	 @Before(value = "logRibbon()")
	public void beforeRibbon(JoinPoint joinPoint) {
		try {
			// 请求数据头
			Request request = (Request) joinPoint.getArgs()[0];
			// 请求类型
			msgLog.setMethod(request.httpMethod().name());
			// 请求URL
			msgLog.setRequestUri(request.url());
			// 请求模块
			String[] split = request.url().split("/");
//			if(split.length > 2) {
//				// 请求模块
//				msgLog.setModel(split[split.length - 2]);
//				// 请求方法
//				String[] methods = split[split.length - 1].split("[?]");
//				msgLog.setFunction(methods[0]);
//			}
			MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
			// 请求数据头信息
			msgLog.setReqHeader(JSON.toJSONString(request.headers()));
			// 请求IP
			msgLog.setRemoteAddr("0.0.0.0");
			// 打印文件日志
			LoggerFactory.getLogger(methodSignature.getDeclaringTypeName()).info("调用接口平台" + msgLog.getMethod() + "请求 " + msgLog.getRequestUri() + "，请求信息：" + msgLog.getParams());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 在FeignClient接口响应完成后，获取响应内容
	 * @param result
	 * @return
	 */
	@AfterReturning(returning = "result", pointcut = "logFeign()")
	public Object afterReturn(Object result) {
		try {
			String rspmsg = null;
			if(msgLog != null) {
				// 响应时间
				msgLog.setRspTime(new Date());
				// 响应内容
				if(result != null) {
					if(result instanceof String)
						rspmsg = result.toString();
					else
						rspmsg = JSON.toJSONString(result);
					msgLog.setRspMsg(rspmsg);
				}
				// 只要调用成功，就算成功，不管业务逻辑
				//msgLog.setRspStatus("成功");
				// 耗时
				if(msgLog.getOperateDate() != null)
					msgLog.setTimeout(msgLog.getRspTime().getTime() - msgLog.getOperateDate().getTime());
				// 保存到数据库
				logService.saveLog(msgLog);
				// 打印文件日志
				LoggerFactory.getLogger(msgLog.getClass()).info("交易耗时" + msgLog.getTimeout() + "毫秒，响应信息：" + rspmsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 在LoadBalancerFeignClient中的execute方法中，如果报错，则记录调用失败
	 * @param joinPoint
	 * @param exception
	 */
	 @AfterThrowing(pointcut = "logRibbon()", throwing = "exception")
	public void afterThrowing(JoinPoint joinPoint, Throwable exception){
		try {
			if(msgLog != null) {
				// 响应时间
				msgLog.setRspTime(new Date());
				msgLog.setErrMsg(exception.getMessage());
				//msgLog.setRspStatus("失败");
				// 耗时
				if(msgLog.getOperateDate() != null)
					msgLog.setTimeout(msgLog.getRspTime().getTime() - msgLog.getOperateDate().getTime());
				// 记录到数据库
				try {
					logService.saveLog(msgLog);
				} catch (Exception e) {}
				// 打印文件日志
				//LoggerFactory.getLogger(msgLog.getClassName()).info("处理异常，交易耗时" + msgLog.getUseTime() + "毫秒，异常信息：" + exception.getMessage());
			}
		} catch (Exception e) {}
	}
}
