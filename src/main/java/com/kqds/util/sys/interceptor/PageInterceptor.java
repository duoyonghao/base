package com.kqds.util.sys.interceptor;

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SubSelect;

public class PageInterceptor implements Interceptor {

	private Logger logger = LoggerFactory.getLogger(PageInterceptor.class);

	CCJSqlParserManager parserManager = new CCJSqlParserManager();

	@Override
	public Object intercept(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
		String tenant = RangeContextHolder.getTenant();
		try {
			if (StringUtils.isEmpty(tenant)) {
				return invocation.proceed();
			}
			tenant = tenant.replaceAll("(?i)WHERE", "");
			tenant = tenant.replaceAll("(?i)AND", "");
			StatementHandler handler = (StatementHandler) invocation.getTarget();
			MetaObject statementHandler = SystemMetaObject.forObject(handler);
			// 获取sql
			BoundSql boundSql = handler.getBoundSql();
			String sql = boundSql.getSql();
			sql = sql.replaceAll("'", "\"");
			// add by zb 2018年3月22日14:46:53 增加容错处理 排除无别名a的sql
			if (!Pattern.compile("\\s+a\\.*").matcher(sql).find()) {
				// 将值塞入，继续
				RangeContextHolder.setTenant(tenant);
				return invocation.proceed();
			}
			Statement stmt = parserManager.parse(new StringReader(sql));
			if (stmt instanceof Select) {
				// 如果是select就将sql转成SELECT对象
				Select select = (Select) parserManager.parse(new StringReader(sql));
				PlainSelect plain = (PlainSelect) select.getSelectBody();
				Expression where_expression = plain.getWhere();
				// 解决pagerhelp分页 count 语句
				if (Pattern.matches("(?i)^SELECT\\s+COUNT\\(.*\\).*", select.toString())) {
					FromItem fromItem = plain.getFromItem();
					if (fromItem != null) {
						Alias alias = fromItem.getAlias();
						if (alias != null && Pattern.matches("(?i)tmp_count", alias.getName())) {
							// pagerhelp的count语句是子查询
							/**
							 * select count(0) from (select a.id AS "id", a.project_code AS "projectCode",
							 * a.customer_code AS "customerCode", a.follow_detail AS "followDetail",
							 * a.follow_time AS "followTime", a.next_follow_time AS "nextFollowTime",
							 * cc.contact_name as "contactName", a.follow_code AS "followCode", a.create_by
							 * AS "createBy", a.update_by AS "updateBy", a.follow_type AS "followType" from
							 * crm_follow a left join crm_follow_contact_rel c on a.follow_code =
							 * c.follow_code left join crm_contact cc on cc.contact_code = c.contact_code
							 * where a.del_flag=0 order by a.create_time DESC) tmp_count
							 */

							// if (fromItem instanceof SubSelect) {
							SubSelect subSelect = (SubSelect) fromItem;
							if (subSelect.getSelectBody() != null) {
								SelectBody selectBody = subSelect.getSelectBody();
								if (selectBody instanceof PlainSelect) {
									PlainSelect plainSelect = (PlainSelect) selectBody;
									System.out.println(plainSelect.getWhere());
									String str_wheres = plainSelect.getWhere() + " and " + tenant;
									Expression wheres = (Expression) (CCJSqlParserUtil.parseCondExpression(str_wheres));
									plainSelect.setWhere(wheres);
									// 将值塞入，继续
									RangeContextHolder.setTenant(tenant);
									// 将增强后的sql放回
									statementHandler.setValue("delegate.boundSql.sql", select.toString());
									return invocation.proceed();
								}
							}
							// }
						} else {
							// 非子查询的cout
							/*
							 * SELECT COUNT(0) FROM crm_project a LEFT JOIN vip_user vm ON vm.user_code =
							 * a.create_by WHERE a.del_flag = "0"
							 */
							// 将值塞入，继续
							RangeContextHolder.setTenant(tenant);
						}
					}

				}
				// 排除 无where条件的
				if (where_expression == null) {
					RangeContextHolder.setTenant(tenant);
					return invocation.proceed();
				}
				String old_where_str = where_expression.toString();
				String str_where = old_where_str + " and " + tenant;
				PermissionUtils.build_select_where(select, str_where);
				// 将增强后的sql放回
				statementHandler.setValue("delegate.boundSql.sql", select.toString());
				return invocation.proceed();
			} else {
				RangeContextHolder.setTenant(tenant);
				return invocation.proceed();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return invocation.proceed();
		}

	}

	@Override
	public Object plugin(Object target) {
		// 生成代理对象
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

	public static void main(String[] args) {
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		String sql = "select count(0) from (select\n" + "                             a.id AS \"id\",\n" + "                             a.project_code AS \"projectCode\",\n"
				+ "                             a.customer_code AS \"customerCode\",\n" + "                             a.follow_detail AS \"followDetail\",\n"
				+ "                             a.follow_time AS \"followTime\",\n" + "                             a.next_follow_time AS \"nextFollowTime\",\n"
				+ "                             cc.contact_name as \"contactName\",\n" + "                             a.follow_code AS \"followCode\",\n"
				+ "                             a.create_by AS \"createBy\",\n" + "                             a.update_by AS \"updateBy\",\n"
				+ "                             a.follow_type AS \"followType\"\n" + "                             from crm_follow a\n"
				+ "                             left join  crm_follow_contact_rel c on a.follow_code = c.follow_code\n"
				+ "                             left join crm_contact cc on cc.contact_code = c.contact_code\n" + "                             where a.del_flag=0\n"
				+ "                             order by a.create_time DESC) tmp_count";
		// String sql = "SELECT * FROM crm_contact a where a.id =1";
		if (Pattern.compile("\\s+a\\.*").matcher(sql).find()) {
			System.out.println(1);
		}
	}

}
