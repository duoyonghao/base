package com.kqds.controller.base.dict;

import com.kqds.entity.sys.YZDict;
import com.kqds.service.sys.dict.YZDictWeChatLogic;
import com.kqds.service.wx.quickmsg.WXQuickMsgLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.sys.DictUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"YZDictWeChatAct"})
public class YZDictWeChatAct
{
  private Logger logger = LoggerFactory.getLogger(YZDictWeChatAct.class);
  @Autowired
  private YZDictWeChatLogic dictCostLogic;
  @Autowired
  private WXQuickMsgLogic quickLogic;
  
  @RequestMapping({"/getSelectTreeAsync.act"})
  public void getSelectTreeAsync(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    String lv = request.getParameter("lv");
    String search = request.getParameter("search");
    List<JSONObject> listtree = new ArrayList();
    try
    {
      if (YZUtility.isNullorEmpty(id))
      {
        List<YZDict> list = this.dictCostLogic.getLeve1SortList(search, ChainUtil.getCurrentOrganization(request));
        for (int i = 0; i < list.size(); i++)
        {
          YZDict base = (YZDict)list.get(i);
          JSONObject obj = new JSONObject();
          obj.put("id", base.getSeqId());
          obj.put("pId", DictUtil.WECHAT_KEYWORD);
          obj.put("name", base.getDictName());
          obj.put("nocheck", "true");
          
          Map<String, String> map = new HashMap();
          map.put("msgtype", base.getSeqId());
          int count = this.quickLogic.getCount(request, TableNameUtil.WX_QUICKMSG, map);
          if (count > 0) {
            obj.put("isParent", "true");
          } else {
            obj.put("isParent", "false");
          }
          listtree.add(obj);
        }
      }
      if ((!YZUtility.isNullorEmpty(id)) && (!"1".equals(lv)))
      {
        Map<String, String> map = new HashMap();
        map.put("msgtype", id);
        
        List<JSONObject> nextlist = this.quickLogic.noSelectWithPage(TableNameUtil.WX_QUICKMSG, map);
        for (int j = 0; j < nextlist.size(); j++)
        {
          JSONObject next = (JSONObject)nextlist.get(j);
          JSONObject obj = new JSONObject();
          obj.put("id", next.getString("seqId"));
          obj.put("pId", id);
          obj.put("name", next.getString("msgcontent"));
          obj.put("nocheck", "true");
          obj.put("isParent", "false");
          listtree.add(obj);
        }
      }
      JSONObject jobj = new JSONObject();
      jobj.put("retData", listtree);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
  }
}
