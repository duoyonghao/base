package com.kqds.util.sys.interceptor;

import java.io.PrintStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

public class PageInterceptor
  implements Interceptor
{
  private Logger logger = LoggerFactory.getLogger(PageInterceptor.class);
  CCJSqlParserManager parserManager = new CCJSqlParserManager();
  
  public Object intercept(Invocation invocation)
    throws InvocationTargetException, IllegalAccessException
  {
    String tenant = RangeContextHolder.getTenant();
    try
    {
      if (StringUtils.isEmpty(tenant)) {
        return invocation.proceed();
      }
      tenant = tenant.replaceAll("(?i)WHERE", "");
      tenant = tenant.replaceAll("(?i)AND", "");
      StatementHandler handler = (StatementHandler)invocation.getTarget();
      MetaObject statementHandler = SystemMetaObject.forObject(handler);
      
      BoundSql boundSql = handler.getBoundSql();
      String sql = boundSql.getSql();
      sql = sql.replaceAll("'", "\"");
      if (!Pattern.compile("\\s+a\\.*").matcher(sql).find())
      {
        RangeContextHolder.setTenant(tenant);
        return invocation.proceed();
      }
      Statement stmt = this.parserManager.parse(new StringReader(sql));
      if ((stmt instanceof Select))
      {
        Select select = (Select)this.parserManager.parse(new StringReader(sql));
        PlainSelect plain = (PlainSelect)select.getSelectBody();
        Expression where_expression = plain.getWhere();
        if (Pattern.matches("(?i)^SELECT\\s+COUNT\\(.*\\).*", select.toString()))
        {
          FromItem fromItem = plain.getFromItem();
          if (fromItem != null)
          {
            Alias alias = fromItem.getAlias();
            if ((alias != null) && (Pattern.matches("(?i)tmp_count", alias.getName())))
            {
              SubSelect subSelect = (SubSelect)fromItem;
              if (subSelect.getSelectBody() != null)
              {
                SelectBody selectBody = subSelect.getSelectBody();
                if ((selectBody instanceof PlainSelect))
                {
                  PlainSelect plainSelect = (PlainSelect)selectBody;
                  System.out.println(plainSelect.getWhere());
                  String str_wheres = plainSelect.getWhere() + " and " + tenant;
                  Expression wheres = CCJSqlParserUtil.parseCondExpression(str_wheres);
                  plainSelect.setWhere(wheres);
                  
                  RangeContextHolder.setTenant(tenant);
                  
                  statementHandler.setValue("delegate.boundSql.sql", select.toString());
                  return invocation.proceed();
                }
              }
            }
            else
            {
              RangeContextHolder.setTenant(tenant);
            }
          }
        }
        if (where_expression == null)
        {
          RangeContextHolder.setTenant(tenant);
          return invocation.proceed();
        }
        String old_where_str = where_expression.toString();
        String str_where = old_where_str + " and " + tenant;
        PermissionUtils.build_select_where(select, str_where);
        
        statementHandler.setValue("delegate.boundSql.sql", select.toString());
        return invocation.proceed();
      }
      RangeContextHolder.setTenant(tenant);
      return invocation.proceed();
    }
    catch (Exception e)
    {
      this.logger.error(e.getMessage());
    }
    return invocation.proceed();
  }
  
  public Object plugin(Object target)
  {
    return Plugin.wrap(target, this);
  }
  
  public void setProperties(Properties properties) {}
  
  public static void main(String[] args)
  {
    CCJSqlParserManager parserManager = new CCJSqlParserManager();
    String sql = "select count(0) from (select\n                             a.id AS \"id\",\n                             a.project_code AS \"projectCode\",\n                             a.customer_code AS \"customerCode\",\n                             a.follow_detail AS \"followDetail\",\n                             a.follow_time AS \"followTime\",\n                             a.next_follow_time AS \"nextFollowTime\",\n                             cc.contact_name as \"contactName\",\n                             a.follow_code AS \"followCode\",\n                             a.create_by AS \"createBy\",\n                             a.update_by AS \"updateBy\",\n                             a.follow_type AS \"followType\"\n                             from crm_follow a\n                             left join  crm_follow_contact_rel c on a.follow_code = c.follow_code\n                             left join crm_contact cc on cc.contact_code = c.contact_code\n                             where a.del_flag=0\n                             order by a.create_time DESC) tmp_count";
    if (Pattern.compile("\\s+a\\.*").matcher(sql).find()) {
      System.out.println(1);
    }
  }
}
