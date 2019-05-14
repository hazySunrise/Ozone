package com.jimi.ozone_server.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

import javax.servlet.http.HttpServletResponse;


/**
 * 跨域许可拦截器
 * <br>
 * <b>2018年5月29日</b>
 * @author 沫熊工作室 <a href="http://www.darhao.cc">www.darhao.cc</a>
 */
public class CORSInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation invocation) {
		 HttpServletResponse response = invocation.getController().getResponse();
		 response.addHeader("Access-Control-Allow-Origin", "*");
		 invocation.invoke();
	}

}
