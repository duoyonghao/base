package com.kqds.util.sys.interceptor;

public class RangeContextHolder {
  private static ThreadLocal<String> tenanThreadLocal = new ThreadLocal<>();
  
  public static final void setTenant(String scheme) {
    tenanThreadLocal.set(scheme);
  }
  
  public static final String getTenant() {
    String scheme = tenanThreadLocal.get();
    remove();
    if (scheme == null)
      scheme = ""; 
    return scheme;
  }
  
  public static final void remove() {
    tenanThreadLocal.remove();
  }
}
