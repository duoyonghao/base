package com.kqds.util.sys.interceptor;

import javax.annotation.PostConstruct;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.springframework.stereotype.Component;

@Component
public class PermissionUtils
{
  private static PermissionUtils permissionUtils;
  
  @PostConstruct
  public void init()
  {
    permissionUtils = this;
  }
  
  public static Select build_select_where(Select select, String str_where)
    throws JSQLParserException
  {
    PlainSelect plain = (PlainSelect)select.getSelectBody();
    
    Expression where_expression = CCJSqlParserUtil.parseCondExpression(str_where);
    plain.setWhere(where_expression);
    return select;
  }
}
