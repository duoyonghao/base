package com.kqds.util.sys.interceptor;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kqds.core.util.db.DBUtil;

import net.sf.json.JSONObject;

@Intercepts(@Signature(method = "handleResultSets", type = ResultSetHandler.class, args = { Statement.class }))
public class JSONObjectResultTypeInterceptor implements Interceptor {

	private Logger logger = LoggerFactory.getLogger(JSONObjectResultTypeInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// 通过invocation获取代理的目标对象
		Object target = invocation.getTarget();

		if (target instanceof DefaultResultSetHandler) {
			DefaultResultSetHandler resultSetHandler = (DefaultResultSetHandler) target;
			MappedStatement mappedStatement = InterceptorTool.getFieldValue(resultSetHandler, "mappedStatement");
			List<ResultMap> resultMaps = mappedStatement.getResultMaps();
			if (resultMaps.size() == 1) {
				ResultMap resultMap = resultMaps.get(0);
				Class<?> type = resultMap.getType();
				if (type == JSONObject.class) {// 拦截到resultType为JSONObject的查询
					List<JSONObject> list = new ArrayList<JSONObject>();
					int count = 0;
					boolean isMysql = DBUtil.isMysql();
					Statement stmt = (Statement) invocation.getArgs()[0]; // 获取到当前的Statement
					ResultSet rs = stmt.getResultSet();
					DBUtil.getJsonListByResultSet(rs, list, count, isMysql); // 根据resultset
																				// 返回
																				// Json
																				// list
					DBUtil.close(stmt, rs, logger); // 关闭数据库连接
					return list;
				}
			}

			// String id = mappedStatement.getId();
			// MappedStatement ms = configuration.getMappedStatement(id);
			// ResultSetType resultSetType = ms.getResultSetType();
			// ResultHandler resultHandler = getFieldValue(resultSetHandler,
			// "resultHandler");
			// ParameterHandler parameterHandler =
			// getFieldValue(resultSetHandler, "parameterHandler");
			// Object parameterObj = parameterHandler.getParameterObject();

		}

		// 如果没有进行拦截处理，则执行默认逻辑
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object obj) {
		// TODO Auto-generated method stub
		return Plugin.wrap(obj, this);
	}

	@Override
	public void setProperties(Properties arg0) {
		// TODO Auto-generated method stub

	}

}
