package com.kqds.service.wx.fans;

import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.wx.WXFans;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.wx.core.WXUtil4ChatLogic;
import com.kqds.service.wx.core.WXUtilLogic;
import com.kqds.service.wx.user.WXUserDocLogic;
import com.kqds.util.base.WebSocketUtil;
import com.kqds.util.base.websocket.WeChat_UserBind_Monitor;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WXFansLogic extends BaseLogic {
  private static Logger logger = LoggerFactory.getLogger(WXFansLogic.class);
  
  @Autowired
  private DaoSupport dao;
  
  @Autowired
  private KQDS_UserDocumentLogic userDocLogic;
  
  @Autowired
  private WXUserDocLogic wxUserLogic;
  
  @Autowired
  private WXUtil4ChatLogic wxUtil4ChatLogic;
  
  @Autowired
  private WXUtilLogic wxUtilLogic;
  
  public List<JSONObject> selectList4Chat(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.WX_RECEIVETEXT) + ".selectList4Chat", map);
    for (JSONObject fans : list)
      fans.put("talkstatus", Integer.valueOf(this.wxUtil4ChatLogic.getTalkStatusByOpenid(fans.getString("openid")))); 
    return list;
  }
  
  public JSONObject selectPage(BootStrapPage bp, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.WX_RECEIVETEXT) + ".selectPage", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    if (map.containsKey("flag"))
      for (JSONObject fans : list) {
        String carestatus = fans.getString("carestatus");
        if ("0".equals(carestatus))
          fans.put("carestatus", "正常"); 
        if ("1".equals(carestatus))
          fans.put("carestatus", "取消关注"); 
      }  
    jobj.put("rows", list);
    return jobj;
  }
  
  public JSONObject selectPageObjByOpenid(String openid) throws Exception {
    JSONObject json = (JSONObject)this.dao.findForObject(String.valueOf(TableNameUtil.WX_RECEIVETEXT) + ".selectPageObjByOpenid", openid);
    return json;
  }
  
  public void updateLastTimeByOpenid(String openid, HttpServletRequest request) throws Exception {
    WXFans fans = getFanByOpenid(openid, request);
    if (fans != null) {
      fans.setLastmsgtime(YZUtility.getCurDateTimeStr());
      this.dao.updateSingleUUID(TableNameUtil.WX_FANS, fans);
    } 
  }
  
  public WXFans getFanByOpenid(String openid, HttpServletRequest request) throws Exception {
    WXFans fans = (WXFans)this.dao.findForObject(String.valueOf(TableNameUtil.WX_RECEIVETEXT) + ".getFanByOpenid", openid);
    return fans;
  }
  
  public void normalCare(String openid, HttpServletRequest request) throws Exception {
    List<WXFans> list = (List<WXFans>)this.dao.findForList(String.valueOf(TableNameUtil.WX_RECEIVETEXT) + ".getFanByOpenid", openid);
    if (list != null && list.size() > 1)
      throw new Exception("数据异常：根据微信ID匹配到多条记录【wx_fans】"); 
    if (list == null || list.size() == 0) {
      WXFans user = new WXFans();
      user.setSeqId(YZUtility.getUUID());
      user.setOpenid(openid);
      user.setBindtime(YZUtility.getCurDateTimeStr());
      user.setCreatetime(YZUtility.getCurDateTimeStr());
      user.setCarestatus(Integer.valueOf(0));
      this.wxUserLogic.saveSingleUUID(TableNameUtil.WX_FANS, user);
    } else {
      WXFans user = list.get(0);
      user.setBindtime(YZUtility.getCurDateTimeStr());
      user.setCreatetime(YZUtility.getCurDateTimeStr());
      user.setCarestatus(Integer.valueOf(0));
      this.wxUserLogic.updateSingleUUID(TableNameUtil.WX_FANS, user);
    } 
  }
  
  public void bindWXUser(String eventkey, String openid, HttpServletRequest request) throws Exception {
    String qrscene = eventkey.replaceAll("qrscene_", "");
    String usercode = this.wxUtilLogic.getUserCodeFromQrCodeMap(qrscene);
    KqdsUserdocument doc = this.userDocLogic.getSingleUserByUsercode(usercode);
    if (doc == null)
      throw new Exception("根据usercode匹配不到患者档案信息，usercode值：" + usercode); 
    if (!YZUtility.isNullorEmpty(doc.getOpenid()) && "0".equals(doc.getBindstatus())) {
      if (!doc.getOpenid().equals(openid)) {
        logger.error("该患者已绑定微信号，不能再次绑定，患者姓名：" + doc.getUsername() + "，患者编号：" + doc.getUsercode() + ",微信Openid：" + doc.getOpenid());
        JSONObject msg = new JSONObject();
        msg.put("bindalother", openid);
        Session session = (Session)WeChat_UserBind_Monitor.WeChat_UserBind_Monitor.get(doc.getUsercode());
        WebSocketUtil.sendMsg2Page(session, msg.toString());
      } else {
        logger.error("您已绑定该微信号，无需重复绑定，患者姓名：" + doc.getUsername() + "，患者编号：" + doc.getUsercode() + ",微信Openid：" + doc.getOpenid());
        JSONObject msg = new JSONObject();
        msg.put("bindalready", openid);
        Session session = (Session)WeChat_UserBind_Monitor.WeChat_UserBind_Monitor.get(doc.getUsercode());
        WebSocketUtil.sendMsg2Page(session, msg.toString());
      } 
      dealSubcribeByScan(openid, null, request);
    } else {
      KqdsUserdocument userA = this.wxUserLogic.getBindUserDocByOpenId(openid, request);
      if (userA != null) {
        JSONObject msg = new JSONObject();
        msg.put("usercode", userA.getUsercode());
        msg.put("username", userA.getUsername());
        msg.put("openid", openid);
        Session session = (Session)WeChat_UserBind_Monitor.WeChat_UserBind_Monitor.get(doc.getUsercode());
        WebSocketUtil.sendMsg2Page(session, msg.toString());
      } else {
        bindWXUser(openid, doc, request);
        JSONObject msg = new JSONObject();
        msg.put("bindok", openid);
        Session session = (Session)WeChat_UserBind_Monitor.WeChat_UserBind_Monitor.get(doc.getUsercode());
        WebSocketUtil.sendMsg2Page(session, msg.toString());
      } 
    } 
  }
  
  public void bindWXUser(String openid, KqdsUserdocument doc, HttpServletRequest request) throws Exception {
    dealSubcribeByScan(openid, doc, request);
    unBindWXUser(openid, request);
    this.wxUserLogic.bindWxOpenId(doc.getSeqId(), openid, request);
  }
  
  private void dealSubcribeByScan(String openid, KqdsUserdocument doc, HttpServletRequest request) throws Exception {
    List<WXFans> list = (List<WXFans>)this.dao.findForList(String.valueOf(TableNameUtil.WX_RECEIVETEXT) + ".getFanByOpenid", openid);
    if (list != null && list.size() > 1)
      throw new Exception("数据异常：根据患者编号匹配到多条记录【wx_fans】"); 
    if (list == null || list.size() == 0) {
      WXFans user = new WXFans();
      user.setSeqId(YZUtility.getUUID());
      if (doc != null)
        user.setUsercode(doc.getUsercode()); 
      user.setOpenid(openid);
      user.setBindtime(YZUtility.getCurDateTimeStr());
      user.setCreatetime(YZUtility.getCurDateTimeStr());
      user.setCarestatus(Integer.valueOf(0));
      this.wxUserLogic.saveSingleUUID(TableNameUtil.WX_FANS, user);
    } else {
      WXFans user = list.get(0);
      if (doc != null)
        user.setUsercode(doc.getUsercode()); 
      user.setBindtime(YZUtility.getCurDateTimeStr());
      user.setCarestatus(Integer.valueOf(0));
      this.wxUserLogic.updateSingleUUID(TableNameUtil.WX_FANS, user);
    } 
  }
  
  public void unBindWXUser(String openid, HttpServletRequest request) throws Exception {
    List<WXFans> list = (List<WXFans>)this.dao.findForList(String.valueOf(TableNameUtil.WX_RECEIVETEXT) + ".getFanByOpenid", openid);
    if (list != null && list.size() > 1)
      throw new Exception("数据异常：根据微信ID匹配到多条记录【wx_fans】"); 
    if (list != null && list.size() != 0) {
      WXFans wXFans = list.get(0);
      wXFans.setUnbindtime(YZUtility.getCurDateTimeStr());
      wXFans.setCarestatus(Integer.valueOf(1));
      wXFans.setUsercode("");
      this.wxUserLogic.updateSingleUUID(TableNameUtil.WX_FANS, wXFans);
    } 
    KqdsUserdocument user = this.wxUserLogic.getBindUserDocByOpenId(openid, request);
    if (user != null) {
      user.setBindstatus("");
      user.setOpenid("");
      this.wxUserLogic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, user);
    } 
  }
}
