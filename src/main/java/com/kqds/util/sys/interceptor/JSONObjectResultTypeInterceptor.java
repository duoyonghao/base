package com.kqds.util.sys.interceptor;

import com.kqds.core.util.db.DBUtil;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import net.sf.json.JSONObject;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({@org.apache.ibatis.plugin.Signature(method="handleResultSets", type=org.apache.ibatis.executor.resultset.ResultSetHandler.class, args={Statement.class})})
public class JSONObjectResultTypeInterceptor
  implements Interceptor
{
  private Logger logger = LoggerFactory.getLogger(JSONObjectResultTypeInterceptor.class);
  
  public Object intercept(Invocation invocation)
    throws Throwable
  {
    Object target = invocation.getTarget();
    if ((target instanceof DefaultResultSetHandler))
    {
      DefaultResultSetHandler resultSetHandler = (DefaultResultSetHandler)target;
      MappedStatement mappedStatement = (MappedStatement)InterceptorTool.getFieldValue(resultSetHandler, "mappedStatement");
      List<ResultMap> resultMaps = mappedStatement.getResultMaps();
      if (resultMaps.size() == 1)
      {
        ResultMap resultMap = (ResultMap)resultMaps.get(0);
        Class<?> type = resultMap.getType();
        if (type == JSONObject.class)
        {
          List<JSONObject> list = new ArrayList();
          int count = 0;
          boolean isMysql = DBUtil.isMysql();
          Statement stmt = (Statement)invocation.getArgs()[0];
          ResultSet rs = stmt.getResultSet();
          DBUtil.getJsonListByResultSet(rs, list, count, isMysql);
          


          DBUtil.close(stmt, rs, this.logger);
          return list;
        }
      }
    }
    return invocation.proceed();
  }
  
  public Object plugin(Object obj)
  {
    return Plugin.wrap(obj, this);
  }
  
  public void setProperties(Properties arg0) {}
}
