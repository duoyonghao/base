package com.kqds.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kqds.util.sys.SessionUtil;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.setCharacterEncoding("UTF-8");
		String url = request.getServletPath();
		// System.out.println("post URL：" + url);
		if (!url.equals("")) {
			// 判斷是否已登录
			HttpSession session = ((HttpServletRequest) request).getSession();
			boolean isValidSession = SessionUtil.isValidSession(session, "LOGIN_USER");

			if (!isValidSession) {
				// 無session則是未登录狀態
				response.sendRedirect(request.getContextPath() + "/inc/sessionerror.jsp");
				return false;
			}
		}
		return true;
	}

}
