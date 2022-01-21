package top.homesoft.framework.log.infrastructure.util;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class RequestUtil {

	public static  Map<String,String > getHeaderMap(HttpServletRequest request, Set filter){
		Enumeration<String> headerNames = request.getHeaderNames();
		Map<String,String > resutl = new HashMap();
		if(Objects.isNull(filter)){
			filter = new HashSet();
		}
		while(headerNames.hasMoreElements()) {//判断是否还有下一个元素
			String nextElement = headerNames.nextElement();//获取headerNames集合中的请求头
			if(!filter.contains(nextElement)) {
				String header2 = request.getHeader(nextElement);//通过请求头得到请求内容
				resutl.put(nextElement, header2);
			}
		}
		return resutl;
	}
}
