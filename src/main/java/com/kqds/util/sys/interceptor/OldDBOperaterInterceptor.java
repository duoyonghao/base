package com.kqds.util.sys.interceptor;

import java.sql.Connection;
import java.util.Properties;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

@Intercepts({@Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class, Integer.class})})
public class OldDBOperaterInterceptor implements Interceptor {
  public Object intercept(Invocation invocation) throws Throwable {
    Object target = invocation.getTarget();
    StatementHandler routingStatementHandler = (StatementHandler)target;
    ParameterHandler parameterHandler = routingStatementHandler.getParameterHandler();
    MappedStatement mappedStatement = InterceptorTool.<MappedStatement>getFieldValue(parameterHandler, "mappedStatement");
    Object parameterObj = parameterHandler.getParameterObject();
    String id = mappedStatement.getId();
    return invocation.proceed();
  }
  
  public Object plugin(Object obj) {
    return Plugin.wrap(obj, this);
  }
  
  public void setProperties(Properties arg0) {}
}
