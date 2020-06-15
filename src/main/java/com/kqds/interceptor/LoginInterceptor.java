package com.kqds.interceptor;

import com.kqds.util.sys.SessionUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor
  extends HandlerInterceptorAdapter
{
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    throws Exception
  {
    super.afterCompletion(request, response, handler, ex);
  }
  
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
    throws Exception
  {
    super.postHandle(request, response, handler, modelAndView);
  }
  
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception
  {
    request.setCharacterEncoding("UTF-8");
    String url = request.getServletPath();
    if (!url.equals(""))
    {
      HttpSession session = request.getSession();
      boolean isValidSession = SessionUtil.isValidSession(session, "LOGIN_USER");
      if (!isValidSession)
      {
        response.sendRedirect(request.getContextPath() + "/inc/sessionerror.jsp");
        return false;
      }
    }
    return true;
  }
}
