package com.kqds.util.sys.spring;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class MyMultipartResolver extends CommonsMultipartResolver {
  private String excludeUrls;
  
  private String[] excludeUrlArray;
  
  public String getExcludeUrls() {
    return this.excludeUrls;
  }
  
  public void setExcludeUrls(String excludeUrls) {
    this.excludeUrls = excludeUrls;
    this.excludeUrlArray = excludeUrls.split(",");
  }
  
  public boolean isMultipart(HttpServletRequest request) {
    byte b;
    int i;
    String[] arrayOfString;
    for (i = (arrayOfString = this.excludeUrlArray).length, b = 0; b < i; ) {
      String url = arrayOfString[b];
      if (request.getRequestURI().contains(url))
        return false; 
      b++;
    } 
    return super.isMultipart(request);
  }
}
