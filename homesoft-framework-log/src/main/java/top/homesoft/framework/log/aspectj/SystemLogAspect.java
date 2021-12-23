package top.homesoft.framework.log.aspectj;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;
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


@Aspect
@Component
public class SystemLogAspect {
  private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect. class);
  /**用户session key 正式环境从配置文件中取,这里仅作为演示*/
  private static final String SESSION_USER_KEY = "user";
  private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<Date>("ThreadLocal beginTime");
  private static final ThreadLocal<SystemLogPO> logThreadLocal = new NamedThreadLocal<SystemLogPO>("ThreadLocal log");

  
  @Autowired(required=false)
  private HttpServletRequest request;

  @Autowired
  Snowflake snowflake;
  
  //@Autowired
  //private ThreadPoolTaskExecutor threadPoolTaskExecutor;

  @Autowired
  private SystemLogRepository logService;

  /**Controller层切点 注解拦截*/
  @Pointcut("@annotation(io.swagger.annotations.ApiOperation) && (@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping))")
  public void controllerAspect(){}

  /**
   * 前置通知 用于拦截Controller层记录用户的操作的开始时间
   * @param joinPoint 切点
   * @throws InterruptedException 
   */
  @Before("controllerAspect()")
  public void doBefore(JoinPoint joinPoint) throws InterruptedException{
    Date beginTime=new Date();
    beginTimeThreadLocal.set(beginTime);

    //debug模式下 显式打印开始时间用于调试
//    if (logger.isDebugEnabled()){
//          logger.debug("开始计时: {}  URI: {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
//            .format(beginTime), request.getRequestURI());
//    }
    //读取session中的用户 
    //currentUser.set(this.getCurrentUser(request));
  }
  
  /**
   * 后置通知 用于拦截Controller层记录用户的操作
   * @param joinPoint 切点
   */
  @After("controllerAspect()")
  public void doAfter(JoinPoint joinPoint) {
        if(getCurrentUserId() == null){
          return;
        }
    SystemLogPO log=new SystemLogPO();
    log.setLogId(snowflake.nextId());
    log.setTitle(getControllerMethodDescription2(joinPoint));
    //log.setType(Log.TYPE_INFO);
    log.setRemoteAddr(request.getRemoteAddr());
    log.setRequestUri(request.getRequestURI());
    log.setMethod(request.getMethod());
    log.setParams(this.buildRequestParams(request.getParameterMap(), joinPoint.getArgs()));
    log.setUserId(getCurrentUserId());
    //得到线程绑定的局部变量--获取前置通知设置的开始时间
    log.setOperateDate(beginTimeThreadLocal.get());
    long beginTime = beginTimeThreadLocal.get().getTime();
    long endTime = System.currentTimeMillis();
    log.setTimeout((endTime-beginTime));



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
        new Thread(new SaveLogThread(log, logService)).start();
        //3.再优化:通过线程池来执行日志保存
        //threadPoolTaskExecutor.execute(new SaveLogThread(log, logService));
      // 演示直接打印
      //System.out.println(log);
        logThreadLocal.set(log);
  }
  
  /**
   *  异常通知 
   * @param joinPoint
   * @param e
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

  /**
   * 获取注解中对方法的描述信息 用于Controller层注解
   * 
   * @param joinPoint 切点
   * @return 方法描述
   */
  public static String getControllerMethodDescription2(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    ApiOperation apiOperation = method
        .getAnnotation(ApiOperation.class);
    String discription = apiOperation.value();
    return discription;
  }
  
  /**
   * 获取session中的用户信息
   * @param
   * @return
   */
  private Long getCurrentUserId(){

    String userId  = request.getHeader("userId");
      if( StringUtils.isNotEmpty(userId)){
          return Long.parseLong(userId);
       }
    return null;
  }

  /**
   * 构建请求参数
   * <ul>
   * <li>优先获取request中的请求参数,使用于get等方法</li>
   * <li>request中无参数时,获取连接点参数,间接获取post类型请求体参数</li>
   * </ul>
   * @param paramMap request.getParameterMap(); //请求提交的参数
   * @param args joinPoint.getArgs(); //连接点获取请求参数
   * @return
   */
  private String buildRequestParams(Map<String, String[]> paramMap, Object[] args) {
    StringBuilder params = new StringBuilder();
    // post 请求体json参数
    if (CollectionUtils.isEmpty(paramMap)) {
      for (Object obj : args) {
        if(! (obj instanceof Serializable)) {
          continue;
        }
        params.append(JSONUtil.toJsonStr(obj));
      }
    // get 请求参数
    } else {
      for (Map.Entry<String, String[]> param : ((Map<String, String[]>)paramMap).entrySet()){
        params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
        String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
        //params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 100));
        params.append(  paramValue);
      }
    }
    return params.toString();
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
