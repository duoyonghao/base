package com.kqds.controller.base.mediaRecord;

import com.kqds.entity.base.KqdsMediaRecord;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.mediaRecord.Kqds_MediaRecordLogic;
import com.kqds.service.base.scbb.KQDS_ScbbLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;
import java.math.BigDecimal;
import java.util.ArrayList;
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
@RequestMapping({"Kqds_MediaRecordAct"})
public class Kqds_MediaRecordAct {
  private static Logger logger = LoggerFactory.getLogger(Kqds_MediaRecordAct.class);
  
  @Autowired
  private Kqds_MediaRecordLogic logic;
  
  @Autowired
  private KQDS_ScbbLogic scbblogic;
  
  @RequestMapping({"/toMedia.act"})
  public ModelAndView toMedia(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/yxzx/media.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsMediaRecord dp = new KqdsMediaRecord();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (!YZUtility.isNullorEmpty(seqId)) {
        this.logic.updateSingleUUID(TableNameUtil.KQDS_MEDIA_RECORD, dp);
        BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_MEDIA_RECORD, dp, TableNameUtil.KQDS_MEDIA_RECORD, request);
      } else {
        String uuid = YZUtility.getUUID();
        dp.setSeqId(uuid);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_MEDIA_RECORD, dp);
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_MEDIA_RECORD, dp, TableNameUtil.KQDS_MEDIA_RECORD, request);
      } 
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
      KqdsMediaRecord en = (KqdsMediaRecord)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDIA_RECORD, seqId);
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
  
  @RequestMapping({"/selectNoPage.act"})
  public String selectNoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String username = request.getParameter("username");
      String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
      String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
      String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(username))
        map.put("mtname", username); 
      if (!YZUtility.isNullorEmpty(starttime))
        map.put("starttime", starttime); 
      if (!YZUtility.isNullorEmpty(endtime))
        map.put("endtime", endtime); 
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
        map.put("organization", organization);
      } 
      List<JSONObject> list = this.logic.selectNoPage(TableNameUtil.KQDS_MEDIA_RECORD, map, organization);
      if (flag != null && flag.equals("exportTable")) {
        ExportTable.exportBootStrapTable2Excel("媒体记录", fieldArr, fieldnameArr, list, response, request);
        return null;
      } 
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectTrscColumn.act"})
  public String selectTrscColumn(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String seqIds = request.getParameter("seqIds");
      Map<String, String> map = new HashMap<>();
      Map<String, String> map2 = new HashMap<>();
      if (!YZUtility.isNullorEmpty(starttime)) {
        map.put("starttime", starttime);
        map2.put("starttime", String.valueOf(starttime) + ConstUtil.TIME_START);
      } 
      if (!YZUtility.isNullorEmpty(endtime)) {
        map.put("endtime", endtime);
        map2.put("endtime", String.valueOf(endtime) + ConstUtil.TIME_END);
      } 
      if (!YZUtility.isNullorEmpty(seqIds))
        map.put("seqIds", seqIds); 
      List<JSONObject> list = new ArrayList<>();
      list = this.logic.selectTrscColumn(TableNameUtil.KQDS_MEDIA_RECORD, map);
      if (list != null && list.size() > 0)
        for (JSONObject jSONObject : list) {
          map2.put("usercodes", jSONObject.getString("kehu"));
          String skje = this.scbblogic.getYysr(map2);
          String skjeYjj = this.scbblogic.getYysrYjj(map2);
          BigDecimal sk = (new BigDecimal(skje)).add(new BigDecimal(skjeYjj));
          jSONObject.put("skje", sk);
        }  
      JSONObject jobj = new JSONObject();
      jobj.put("rows", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getWdOrdertj.act"})
  public String getWdOrdertj(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String seqIds = request.getParameter("seqIds");
      String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
      String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
      String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(starttime)) {
        starttime = String.valueOf(starttime) + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      } 
      if (!YZUtility.isNullorEmpty(endtime)) {
        endtime = String.valueOf(endtime) + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      } 
      if (YZUtility.isNullorEmpty(seqIds))
        throw new Exception("请选择活动记录"); 
      String[] seqIdArr = seqIds.split(",");
      List<JSONObject> list = new ArrayList<>();
      for (int i = 0; i < seqIdArr.length; i++) {
        KqdsMediaRecord actity = (KqdsMediaRecord)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDIA_RECORD, seqIdArr[i]);
        JSONObject obj = new JSONObject();
        obj.put("xh", Integer.valueOf(i + 1));
        obj.put("username", actity.getMtname());
        obj.put("createtime", actity.getCreatetime());
        obj.put("outmoney", actity.getOutmoney());
        map.put("usercodes", actity.getKehu());
        int ldnums = this.scbblogic.getCountJd(map);
        obj.put("ldnums", Integer.valueOf(ldnums));
        int yynums = this.scbblogic.getCountYy(map);
        obj.put("yynums", Integer.valueOf(yynums));
        Float yyl = Float.valueOf(0.0F);
        if (ldnums != 0)
          yyl = Float.valueOf(yynums * 100.0F / ldnums); 
        obj.put("yyl", String.valueOf(YZUtility.FloatToFixed2(yyl)) + "%");
        int yysmnums = this.scbblogic.getCountYysm(map);
        obj.put("yysmnums", Integer.valueOf(yysmnums));
        Float dzl = Float.valueOf(0.0F);
        if (ldnums != 0)
          dzl = Float.valueOf(yysmnums * 100.0F / ldnums); 
        obj.put("dzl", String.valueOf(YZUtility.FloatToFixed2(dzl)) + "%");
        String skje = this.scbblogic.getYysr(map);
        String skjeYjj = this.scbblogic.getYysrYjj(map);
        String sk = YZUtility.FloatToFixed2(Float.valueOf(Float.parseFloat(skje) + Float.parseFloat(skjeYjj)));
        obj.put("skje", sk);
        BigDecimal trccb = BigDecimal.ZERO;
        if (KqdsBigDecimal.compareTo(actity.getOutmoney(), BigDecimal.ZERO) > 0)
          trccb = (new BigDecimal(sk)).divide(actity.getOutmoney(), 2); 
        obj.put("trccb", "1:" + trccb);
        int cjnums = this.scbblogic.getCountCj(map);
        obj.put("cjnums", Integer.valueOf(cjnums));
        Float cjl = Float.valueOf(0.0F);
        if (yysmnums != 0)
          cjl = Float.valueOf(cjnums * 100.0F / yysmnums); 
        obj.put("cjl", String.valueOf(YZUtility.FloatToFixed2(cjl)) + "%");
        Float rjxf = Float.valueOf(0.0F);
        if (yysmnums != 0)
          rjxf = Float.valueOf(Float.parseFloat(sk) / yysmnums); 
        obj.put("rjxf", YZUtility.FloatToFixed2(rjxf));
        list.add(obj);
      } 
      if (flag != null && flag.equals("exportTable")) {
        ExportTable.exportBootStrapTable2Excel("网电预约统计", fieldArr, fieldnameArr, list, response, request);
        return null;
      } 
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
}
