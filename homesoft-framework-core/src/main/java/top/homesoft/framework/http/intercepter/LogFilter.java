package top.homesoft.framework.http.intercepter;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {



	public void destroy() {
	}

	public void init(FilterConfig config) {
	}

	 

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		handle((HttpServletRequest) request, (HttpServletResponse) response, chain);
	}

	private void handle(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Object debug = request.getParameter("debug");
		if(logger.isDebugEnabled() ||  ObjectUtil.isNotEmpty(debug)) {
			response.setHeader("x-requestId", UUID.randomUUID().toString());
			logger.info("request info:{}",response.getHeader("x-requestId"));
		}

		chain.doFilter(request, response);
	}

	private boolean isPreflightRequest(HttpServletRequest request) {
		return "OPTIONS".equalsIgnoreCase(request.getMethod())
				&& request.getHeader("Access-Control-Request-Method") != null;
	}

}
