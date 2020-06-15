package com.kqds.service.wx.receivetext;

import com.github.pagehelper.PageInfo;
import com.kqds.core.global.YZSysProps;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.wx.WXReceivetext;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.wx.face.QQFaceUtil;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WXReceiveTextLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public String getUrl4Send(String content)
    throws Exception
  {
    String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID");
    
    String sendMsgUrl = YZSysProps.getString("SEND_MSG_URL");
    if (YZUtility.isNullorEmpty(accountid)) {
      throw new Exception("accountid不能为空");
    }
    String msgtype = "text";
    
    content = content.replaceAll("<div><br></div>", "\n");
    content = content.replaceAll("</div><div>", "\n");
    content = content.replaceAll("<div>", "\n");
    content = content.replaceAll("</div>", "\n");
    
    content = qqFaceDeal(content);
    
    String contentCode = URLEncoder.encode(content, "utf-8");
    sendMsgUrl = sendMsgUrl + "&content=" + contentCode + "&wxaccountid=" + accountid + "&msgtype=" + msgtype;
    return sendMsgUrl;
  }
  
  private static String qqFaceDeal(String text)
    throws Exception
  {
    String regxpForTag = "<\\s*img\\s+([^>]*)\\s*";
    String regxp4Class = "class=\\s*\"([^\"]+)\"";
    String regxp4Title = "title=\\s*\"([^\"]+)\"";
    String regxp4Src = "src=\\s*\"([^\"]+)\"";
    Pattern patternForTag = Pattern.compile(regxpForTag, 2);
    Pattern pattern4Class = Pattern.compile(regxp4Class, 2);
    Pattern pattern4Title = Pattern.compile(regxp4Title, 2);
    Pattern pattern4Src = Pattern.compile(regxp4Src, 2);
    Matcher matcherForTag = patternForTag.matcher(text);
    boolean result = matcherForTag.find();
    while (result)
    {
      String imgStr = matcherForTag.group(1);
      
      Matcher matcher4Class = pattern4Class.matcher(imgStr);
      Matcher matcher4Title = pattern4Title.matcher(imgStr);
      Matcher matcher4Src = pattern4Src.matcher(imgStr);
      
      String title = null;
      String classN = null;
      String src = null;
      if (matcher4Class.find()) {
        classN = matcher4Class.group(1);
      }
      if (matcher4Title.find()) {
        title = matcher4Title.group(1);
      }
      if (matcher4Src.find()) {
        src = matcher4Src.group(1);
      }
      StringBuffer imgBf1 = new StringBuffer("<img class=\"" + classN + "\" title=\"" + title + "\" src=\"" + src + "\"></img>");
      StringBuffer imgBf2 = new StringBuffer("<img class=\"" + classN + "\" title=\"" + title + "\" src=\"" + src + "\">");
      
      String titleBQ = "[" + title + "]";
      text = text.replaceAll(imgBf1.toString(), titleBQ);
      text = text.replaceAll(imgBf2.toString(), titleBQ);
      
      result = matcherForTag.find();
    }
    return text;
  }
  
  public List<JSONObject> selectList(String seqId, String openid)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("openid", openid);
    if (!YZUtility.isNullorEmpty(seqId))
    {
      WXReceivetext text = (WXReceivetext)this.dao.loadObjSingleUUID(TableNameUtil.WX_RECEIVETEXT, seqId);
      if (text != null) {
        map.put("createtime", text.getCreatetime());
      }
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.WX_RECEIVETEXT + ".selectList", map);
    for (JSONObject jsonObject : list)
    {
      String content = jsonObject.getString("content");
      
      content = QQFaceUtil.dealQQFace(content);
      jsonObject.put("content", content);
    }
    return list;
  }
  
  public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.WX_RECEIVETEXT + ".selectWithPage", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public JSONObject getMsg4Talk(String openid)
    throws Exception
  {
    JSONObject json = (JSONObject)this.dao.findForObject(TableNameUtil.WX_RECEIVETEXT + ".getMsg4Talk", openid);
    return json;
  }
  
  public int getNotReadMsgCountByOpenid(String openid, HttpServletRequest request)
    throws SQLException, Exception
  {
    Map<String, String> map = new HashMap();
    if (!YZUtility.isNullorEmpty(openid))
    {
      map.put("openid", openid);
    }
    else
    {
      String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID");
      map.put("accountid", accountid);
    }
    int count = ((Integer)this.dao.findForObject(TableNameUtil.WX_RECEIVETEXT + ".getNotReadMsgCountByOpenid", map)).intValue();
    return count;
  }
}
