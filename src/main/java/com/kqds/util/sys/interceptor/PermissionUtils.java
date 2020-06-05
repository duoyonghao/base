package com.kqds.util.sys.interceptor;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

/**
 * @Author zhangbin
 * @Description
 * @Date 2018/3/1 14:32
 * @Modified By
 */
@Component
public class PermissionUtils {

	private static PermissionUtils permissionUtils;

	@PostConstruct
	// 通过@PostConstruct实现初始化bean之前进行的操作
	public void init() {
		permissionUtils = this;
		// 初使化时将已静态化的testService实例化
	}

	// ********************change where
	public static Select build_select_where(Select select, String str_where) throws JSQLParserException {
		PlainSelect plain = (PlainSelect) select.getSelectBody();
		// parseCondExpression函数 不会被空格截断
		Expression where_expression = (Expression) (CCJSqlParserUtil.parseCondExpression(str_where));
		plain.setWhere(where_expression);
		return select;
	}
}
