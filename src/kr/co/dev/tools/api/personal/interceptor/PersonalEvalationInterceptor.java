package kr.co.dev.tools.api.personal.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class PersonalEvalationInterceptor extends HandlerInterceptorAdapter {
	protected final Logger logger = LoggerFactory.getLogger(PersonalEvalationInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException{
		boolean loginState = false;

		String reqUrl = request.getRequestURL().toString();
		
		if(request.getSession().getAttribute("employeId")!=null && 
		  !request.getSession().getAttribute("employeId").equals(null)){ 
			logger.debug("Interceptor.nowLoginId=>"+request.getSession().getAttribute("employeId"));
			loginState = true; 
		}else if(reqUrl.contains("/login") || reqUrl.contains("/common")){
			loginState = true;
		}
		
		//비로그인 상태
		if(!loginState){			
			System.out.println("Interceptor.loginState=>"+loginState);
			response.sendRedirect(request.getContextPath()+"/login");
			return loginState;
		}
		
		return loginState;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
	{		
		//json 처리
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)
	{

	}
}
