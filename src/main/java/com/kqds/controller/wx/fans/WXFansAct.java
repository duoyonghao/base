package com.kqds.controller.wx.fans;

import com.kqds.controller.wx.user.WXUserDocAct;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.wx.core.WXUtil4ChatLogic;
import com.kqds.service.wx.core.WXUtilLogic;
import com.kqds.service.wx.fans.WXFansLogic;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.export.ExportTable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"WXFansAct"})
public class WXFansAct {
  private static Logger logger = LoggerFactory.getLogger(WXUserDocAct.class);
  
  @Autowired
  private WXFansLogic fansLogic;
  
  @Autowired
  private WXUtilLogic wxUtilLogic;
  
  @Autowired
  private WXUtil4ChatLogic wxUtil4ChatLogic;
  
  @RequestMapping({"/toList.act"})
  public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/wechat/fans_manage/list.jsp");
    return mv;
  }
  
  @RequestMapping({"/selectList.act"})
  public String selectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      Map<String, String> map = new HashMap<>();
      List<JSONObject> list = this.fansLogic.selectList4Chat(map);
      JSONObject json = new JSONObject();
      json.put("list", list);
      YZUtility.DEAL_SUCCESS(json, "查询成功", response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectPage4Manage.act"})
  public String selectPage4Manage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
      String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
      String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(flag))
        map.put("flag", flag); 
      JSONObject data = this.fansLogic.selectPage(bp, map);
      if (flag != null && flag.equals("exportTable")) {
        ExportTable.exportBootStrapTable2Excel("粉丝列表", fieldArr, fieldnameArr, (List)data.getJSONArray("rows"), response, request);
        return null;
      } 
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectPage.act"})
  public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      Map<String, String> map = new HashMap<>();
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      JSONObject json = this.fansLogic.selectPage(bp, map);
      YZUtility.DEAL_SUCCESS(json, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectPageObjByOpenid.act"})
  public String selectPageObjByOpenid(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String openid = request.getParameter("openid");
      JSONObject fansinfo = this.fansLogic.selectPageObjByOpenid(openid);
      fansinfo.put("talkstatus", Integer.valueOf(this.wxUtil4ChatLogic.getTalkStatusByOpenid(fansinfo.getString("openid"))));
      JSONObject jobj = new JSONObject();
      jobj.put("fansinfo", fansinfo);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateBaseInfoByOpenid.act"})
  public String updateBaseInfoByOpenid(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String openid = request.getParameter("openid");
      if (YZUtility.isNullorEmpty(openid))
        throw new Exception("openid不能为空"); 
      JSONObject json = this.wxUtilLogic.getUserBaseInfo(openid, request);
      YZUtility.DEAL_SUCCESS(json, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
}
