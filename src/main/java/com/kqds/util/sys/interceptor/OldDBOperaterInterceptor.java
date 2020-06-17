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

@Intercepts({ @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class, Integer.class }) })
public class OldDBOperaterInterceptor implements Interceptor {
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// 通过invocation获取代理的目标对象
		Object target = invocation.getTarget();

		StatementHandler routingStatementHandler = (StatementHandler) target;

		ParameterHandler parameterHandler = routingStatementHandler.getParameterHandler();

		MappedStatement mappedStatement = InterceptorTool.getFieldValue(parameterHandler, "mappedStatement");

		// prepare 方法的参数

		Object parameterObj = parameterHandler.getParameterObject();
		String id = mappedStatement.getId();

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
