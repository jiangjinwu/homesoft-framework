package top.homesoft.framework.http.intercepter;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object debug = request.getParameter("debug");
		MDC.put("x-request-id", UUID.randomUUID().toString());
        logger.info(MDC.get("x-request-id"));
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        MDC.put("xx", UUID.randomUUID().toString() + "begin");

    }
}
