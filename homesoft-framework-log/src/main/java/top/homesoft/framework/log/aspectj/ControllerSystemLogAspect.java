package top.homesoft.framework.log.aspectj;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSON;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import top.homesoft.framework.log.domain.systemlog.entity.SystemLogPO;
import top.homesoft.framework.log.domain.systemlog.repository.SystemLogRepository;
import top.homesoft.framework.log.infrastructure.config.SystemLogConfig;
import top.homesoft.framework.log.infrastructure.util.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 系统日志切点类
 * @author linrx ,jjw
 *
 */


@Aspect
//("pertarget(this(com.afterAdvice.AddFunctionInterface))")
@Component
// @Scope(value = "prototype") //执行插入数据库没有数据
public class ControllerSystemLogAspect extends  BaseLogAspect {
  private static final Logger logger = LoggerFactory.getLogger(ControllerSystemLogAspect. class);
  private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<Date>("ThreadLocal beginTime");
  private static final ThreadLocal<SystemLogPO> logThreadLocal = new NamedThreadLocal<SystemLogPO>("ThreadLocal log");


  public ControllerSystemLogAspect(SystemLogConfig systemLogConfig){
      super.systemLogConfig = systemLogConfig;
  }

  @Autowired
  Snowflake snowflake;



  @Autowired
  private SystemLogRepository logService;

  /** eg: com.xx.xx.user..*  **/
  @Pointcut("@within(org.springframework.web.bind.annotation.RestController) && @annotation(io.swagger.annotations.ApiOperation) && (@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping))")
  public void controllerAspect(){}

  /**
   * 前置通知 用于拦截Controller层记录用户的操作的开始时间
   * @param joinPoint 切点
   */
  @Before("controllerAspect()")
  public void doBefore(JoinPoint joinPoint){
    Date beginTime=new Date();
    beginTimeThreadLocal.set(beginTime);

  }

  /**
   * 后置通知 用于拦截Controller层记录用户的操作
   * @param joinPoint 切点
   */
  @After("controllerAspect()")
  public void doAfter(JoinPoint joinPoint) {

    HttpServletRequest request = this.getRequest();
    SystemLogPO log=new SystemLogPO();
    log.setTitle(getControllerMethodDescription2(joinPoint));
    setRequest(log,joinPoint);

    log.setUserId(getCurrentUserId());
    //得到线程绑定的局部变量--获取前置通知设置的开始时间
    log.setOperateDate(beginTimeThreadLocal.get());
    long beginTime = beginTimeThreadLocal.get().getTime();
    long endTime = System.currentTimeMillis();
    log.setTimeout((endTime-beginTime));

	log.setReqId(MDC.get(systemLogConfig.getRequestIdKey()));


      // debu模式下打印JVM信息。
    if (logger.isDebugEnabled()){
          logger.debug("计时结束：{}  URI: {}  耗时： {}   最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
              new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(endTime),
              request.getRequestURI(),
               endTime - beginTime,
          Runtime.getRuntime().maxMemory()/1024/1024,
          Runtime.getRuntime().totalMemory()/1024/1024,
          Runtime.getRuntime().freeMemory()/1024/1024,
          (Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory())/1024/1024);
    }

       //1.直接执行保存操作
        //this.logService.createLog(log);
        //2.优化:异步保存日志

        //3.再优化:通过线程池来执行日志保存
        //threadPoolTaskExecutor.execute(new SaveLogThread(log, logService));
      // 演示直接打印
      //System.out.println(log);
        logThreadLocal.set(log);
  }

  /**
   *  异常通知
   * @param joinPoint 切点
   * @param e 异常
   */
  @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
  public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
    SystemLogPO log = logThreadLocal.get();
    if(log != null){
      log.setType("error");
      log.setException(e.toString());
      new UpdateLogThread(log, logService).start();
    }
  }


	@AfterReturning(returning = "result", pointcut = "controllerAspect()")
	public Object afterReturn(Object result) {
		try {
			SystemLogPO msgLog = logThreadLocal.get();
            super.setResult(msgLog,result);
            new SaveLogThread(msgLog, logService).run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

  /**
   * 获取注解中对方法的描述信息 用于Controller层注解
   *
   * @param joinPoint 切点
   * @return 方法描述
   */


  /**
   * 获取session中的用户信息
   * @return 返回当前用户id
   */
  private Long getCurrentUserId(){
    HttpServletRequest request = this.getRequest();
    String userId  = request.getHeader(systemLogConfig.getHeader().getUserIdKey());
      if( StringUtils.isNotEmpty(userId)){
          return Long.parseLong(userId);
       }
    return null;
  }



  /**
   * 保存日志线程
   *
   * @author lin.r.x
   *
   */
  private static class SaveLogThread implements Runnable {
    private SystemLogPO log;
    private SystemLogRepository logService;

    public SaveLogThread(SystemLogPO log, SystemLogRepository logService) {
      this.log = log;
      this.logService = logService;
    }

    @Override
    public void run() {
      logService.saveLog(log);
    }
  }

  /**
   * 日志更新线程
   *
   * @author lin.r.x
   *
   */
  private static class UpdateLogThread extends Thread {
    private SystemLogPO log;
    private SystemLogRepository logService;

    public UpdateLogThread(SystemLogPO log, SystemLogRepository logService) {
      super(UpdateLogThread.class.getSimpleName());
      this.log = log;
      this.logService = logService;
    }

    @Override
    public void run() {
      this.logService.updateLog(log);
    }
  }





}
