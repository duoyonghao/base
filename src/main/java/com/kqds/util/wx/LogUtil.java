package com.kqds.util.wx;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
  private static Logger objLog;
  
  private static Logger getLogger() {
    if (objLog == null)
      objLog = LoggerFactory.getLogger(LogUtil.class); 
    return objLog;
  }
  
  public static void info(String message, Exception exception) {
    try {
      log("INFO", message, exception);
    } catch (Exception exception1) {}
  }
  
  public static void info(Object message) {
    log("INFO", message);
  }
  
  public static void trace(String message) {
    try {
      log("TRACE", message);
    } catch (Exception exception) {}
  }
  
  public static void trace(String message, Exception exception) {
    try {
      log("TRACE", message, exception);
    } catch (Exception exception1) {}
  }
  
  public static void error(String message, Exception exception) {
    try {
      log("ERROR", message, exception);
    } catch (Exception exception1) {}
  }
  
  public static void error(String message) {
    try {
      log("ERROR", message);
    } catch (Exception exception) {}
  }
  
  public static void warning(String message, Exception exception) {
    try {
      log("WARN", message, exception);
    } catch (Exception exception1) {}
  }
  
  public static void warning(String message) {
    try {
      log("WARN", message);
    } catch (Exception exception) {}
  }
  
  public static void fatal(String message) {
    try {
      log("FATAL", message);
    } catch (Exception exception) {}
  }
  
  public static void debug(String message, Exception exception) {
    try {
      log("DEBUG", message, exception);
    } catch (Exception exception1) {}
  }
  
  public static void debug(String message) {
    try {
      log("DEBUG", message);
    } catch (Exception exception) {}
  }
  
  public static void log(String level, Object msg) {
    log(level, msg, null);
  }
  
  public static void log(String level, Throwable e) {
    log(level, null, e);
  }
  
  public static void log(String level, Object msg, Throwable e) {
    try {
      StringBuilder sb = new StringBuilder();
      Throwable t = new Throwable();
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      t.printStackTrace(pw);
      String input = sw.getBuffer().toString();
      StringReader sr = new StringReader(input);
      BufferedReader br = new BufferedReader(sr);
      for (int i = 0; i < 4; i++)
        br.readLine(); 
      String line = br.readLine();
      int paren = line.indexOf("at ");
      line = line.substring(paren + 3);
      paren = line.indexOf('(');
      String invokeInfo = line.substring(0, paren);
      int period = invokeInfo.lastIndexOf('.');
      sb.append('[');
      sb.append(invokeInfo.substring(0, period));
      sb.append(':');
      sb.append(invokeInfo.substring(period + 1));
      sb.append("():");
      paren = line.indexOf(':');
      period = line.lastIndexOf(')');
      sb.append(line.substring(paren + 1, period));
      sb.append(']');
      sb.append(" - ");
      sb.append(msg);
      if ("DEBUG".equals(level))
        getLogger().debug(sb.toString(), e); 
      if ("ERROR".equals(level))
        getLogger().error(sb.toString(), e); 
      if ("INFO".equals(level))
        getLogger().info(sb.toString(), e); 
      if ("TRACE".equals(level))
        getLogger().trace(sb.toString(), e); 
      if ("WARN".equals(level))
        getLogger().warn(sb.toString(), e); 
    } catch (Exception ex) {
      info(ex.getLocalizedMessage());
    } 
  }
}
