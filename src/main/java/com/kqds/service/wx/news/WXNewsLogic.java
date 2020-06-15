package com.kqds.service.wx.news;

import com.github.pagehelper.PageInfo;
import com.kqds.core.global.YZSysProps;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.wx.WXNews;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WXNewsLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  @Autowired
  private WXNewsItemLogic itemLogic;
  
  public JSONObject getNewsObject4Send(String newsid, HttpServletRequest request) throws SQLException, Exception {
    String msgtype = "news";
    String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID");
    String website_url = YZSysProps.getString("WEBSITE_URL");
    WXNews news = (WXNews)this.itemLogic.loadObjSingleUUID(TableNameUtil.WX_NEWS, newsid);
    if (news == null)
      throw new Exception("图文不存在"); 
    List<JSONObject> itemList = this.itemLogic.getList(newsid);
    if (itemList == null || itemList.size() == 0)
      throw new Exception("图文列表为空"); 
    for (JSONObject item : itemList) {
      String imagepath = item.getString("imagepath");
      String imageurl = String.valueOf(website_url) + request.getContextPath() + imagepath;
      String newsurl = String.valueOf(website_url) + request.getContextPath() + "/WXNewsItemAct/toDetail.act?seqId=" + item.getString("seqId");
      if (!YZUtility.isNullorEmpty(item.getString("url")))
        newsurl = item.getString("url"); 
      item.put("imageurl", imageurl);
      item.put("newsurl", newsurl);
    } 
    JSONObject newsMsg = new JSONObject();
    newsMsg.put("msgtype", msgtype);
    newsMsg.put("itemlist", itemList);
    newsMsg.put("accountid", accountid);
    newsMsg.put("newsid", newsid);
    return newsMsg;
  }
  
  public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.WX_NEWS) + ".selectWithPage", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    for (JSONObject news : list) {
      String seqId = news.getString("seqId");
      List<JSONObject> itemlist = this.itemLogic.getList(seqId);
      news.put("itemlist", itemlist);
      news.put("itemcount", Integer.valueOf(itemlist.size()));
    } 
    jobj.put("rows", list);
    return jobj;
  }
  
  public List<WXNews> getList(String newstype) throws Exception {
    List<WXNews> list = (List<WXNews>)this.dao.findForList(String.valueOf(TableNameUtil.WX_NEWS) + ".getList", newstype);
    return list;
  }
}
