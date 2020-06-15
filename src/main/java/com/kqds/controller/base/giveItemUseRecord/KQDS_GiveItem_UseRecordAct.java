package com.kqds.controller.base.giveItemUseRecord;

import com.kqds.entity.base.KqdsGiveitemUserecord;
import com.kqds.service.base.giveItemUseRecord.KQDS_GiveItem_UseRecordLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
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
@RequestMapping({"KQDS_GiveItem_UseRecordAct"})
public class KQDS_GiveItem_UseRecordAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_GiveItem_UseRecordAct.class);
  
  @Autowired
  private KQDS_GiveItem_UseRecordLogic logic;
  
  @RequestMapping({"/toZengSongList.act"})
  public ModelAndView toGrxx(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/index/center/zengsong_list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMember_Zengsong_UserItem.act"})
  public ModelAndView toMember_Zengsong_UserItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    String regno = request.getParameter("regno");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("regno", regno);
    mv.setViewName("/kqdsFront/member/member_zengsong_userItem.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMember_Zengsong4zxzx.act"})
  public ModelAndView toMember_Zengsong4zxzx(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/member/member_zengsong4zxzx.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      KqdsGiveitemUserecord dp = new KqdsGiveitemUserecord();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId))
        throw new Exception("主键不能为空"); 
      dp.setStatus(Integer.valueOf(ConstUtil.GIVE_ITEM_USE_STATUS_1));
      dp.setCztime(YZUtility.getCurDateTimeStr());
      this.logic.updateSingleUUID(TableNameUtil.KQDS_GIVEITEM_USERECORD, dp);
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE_STATUS, BcjlUtil.KQDS_GIVEITEM_USERECORD, dp, dp.getUsercode(), TableNameUtil.KQDS_GIVEITEM_USERECORD, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      KqdsGiveitemUserecord en = (KqdsGiveitemUserecord)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_GIVEITEM_USERECORD, seqId);
      if (en == null)
        throw new Exception("数据不存在"); 
      JSONObject jobj = new JSONObject();
      jobj.put("data", en);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getUserRecordByMemberno.act"})
  public String getUserRecordByMemberno(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String memberno = request.getParameter("memberno");
      String queryInput = request.getParameter("queryInput");
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String doctor = request.getParameter("doctor");
      String nurse = request.getParameter("nurse");
      String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
      String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
      String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(memberno))
        map.put("memberno", memberno); 
      if (!YZUtility.isNullorEmpty(queryInput))
        map.put("queryInput", queryInput); 
      if (!YZUtility.isNullorEmpty(starttime))
        map.put("starttime", String.valueOf(starttime) + ConstUtil.TIME_START); 
      if (!YZUtility.isNullorEmpty(endtime))
        map.put("endtime", String.valueOf(endtime) + ConstUtil.TIME_END); 
      if (!YZUtility.isNullorEmpty(doctor))
        map.put("doctor", doctor); 
      if (!YZUtility.isNullorEmpty(nurse))
        map.put("nurse", nurse); 
      List<JSONObject> list = this.logic.getUserRecordByMemberno(TableNameUtil.KQDS_GIVEITEM_USERECORD, map);
      if (flag != null && flag.equals("exportTable")) {
        ExportTable.exportBootStrapTable2Excel("使用赠送记录", fieldArr, fieldnameArr, list, response, request);
        return null;
      } 
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getUseRecordByUsercodeAndDoctor.act"})
  public String getUseRecordByUsercodeAndDoctor(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      String doctor = request.getParameter("doctor");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(usercode))
        map.put("usercode", usercode); 
      if (!YZUtility.isNullorEmpty(doctor))
        map.put("doctor", doctor); 
      List<JSONObject> list = this.logic.getUserRecordByMemberno(TableNameUtil.KQDS_GIVEITEM_USERECORD, map);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
}
