package com.kqds.service.wx.templateitem;

import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.wx.WXTemplateitem;
import com.kqds.entity.wx.WXTemplatemsg;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WXTemplateItemLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public JSONObject getTemplateItem4Send(WXTemplateitem tempItem, HttpServletRequest request)
    throws SQLException, Exception
  {
    String template_id = tempItem.getTemplateId();
    String url = request.getParameter("url");
    

    WXTemplatemsg tempmsg = (WXTemplatemsg)this.dao.loadObjSingleUUID(TableNameUtil.WX_TEMPLATEMSG, tempItem.getTemplateSeqid());
    String content = tempmsg.getContent();
    

    List<String> keyList = new ArrayList();
    String regEx = "\\{\\{.*?\\.DATA\\}\\}";
    
    Pattern pattern = Pattern.compile(regEx);
    
    Matcher matcher = pattern.matcher(content);
    while (matcher.find())
    {
      String matchStr = matcher.group();
      matchStr = matchStr.replace(".DATA}}", "");
      matchStr = matchStr.replace("{{", "");
      keyList.add(matchStr);
    }
    JSONObject postParam = new JSONObject();
    postParam.put("template_id", template_id);
    postParam.put("url", url);
    
    JSONObject data = new JSONObject();
    JSONObject jsonItem = JSONObject.fromObject(tempItem);
    for (String key : keyList)
    {
      JSONObject keyJson = new JSONObject();
      keyJson.put("value", jsonItem.get(key));
      keyJson.put("color", "#173177");
      data.put(key, keyJson);
      


      String str4replace = "{{" + key + ".DATA}}";
      content = content.replace(str4replace, jsonItem.get(key).toString());
    }
    postParam.put("data", data);
    postParam.put("local_content", content);
    postParam.put("local_title", tempmsg.getTitle());
    
    return postParam;
  }
  
  public JSONObject selectPage(BootStrapPage bp, Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.WX_TEMPLATEITEM + ".selectPage", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    if (map.containsKey("flag")) {
      for (JSONObject fans : list)
      {
        String status = fans.getString("status");
        if ("0".equals(status)) {
          fans.put("statusname", "正常");
        }
        if ("1".equals(status)) {
          fans.put("statusname", "禁用");
        }
      }
    }
    jobj.put("rows", list);
    return jobj;
  }
  
  public List<JSONObject> selectList(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.WX_TEMPLATEITEM + ".selectList", map);
    return list;
  }
}
