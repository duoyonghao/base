package com.kqds.controller.base.outProcessingSheet;

import com.kqds.entity.base.KqdsOutprocessingSheet;
import com.kqds.entity.base.KqdsOutprocessingSheetDetail;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.outProcessingSheet.KQDS_OutProcessingSheetLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.base.code.JGCodeUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"KQDS_OutProcessingSheetAct"})
public class KQDS_OutProcessingSheetAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_OutProcessingSheetAct.class);
  @Autowired
  private KQDS_OutProcessingSheetLogic logic;
  
  @RequestMapping({"/toJgmx.act"})
  public ModelAndView toJgmx(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/jgzx/jgmx.jsp");
    return mv;
  }
  
  @RequestMapping({"/toAdd.act"})
  public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/jiagong/jg_add.jsp");
    return mv;
  }
  
  @RequestMapping({"/toAddWorksheet.act"})
  public ModelAndView toAddWorksheet(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/jiagong/ycjg_add.jsp");
    return mv;
  }
  
  @RequestMapping({"/toYcjgShow.act"})
  public ModelAndView toYcjgShow(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    ModelAndView mv = new ModelAndView();
    mv.addObject("id", id);
    mv.setViewName("/kqdsFront/jiagong/ycjg_show.jsp");
    return mv;
  }
  
  @RequestMapping({"/toUserCost.act"})
  public ModelAndView toUserCost(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/jiagong/userCost.jsp");
    return mv;
  }
  
  @RequestMapping({"/toYcjgEdit.act"})
  public ModelAndView toYcjgEdit(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    ModelAndView mv = new ModelAndView();
    mv.addObject("id", id);
    mv.setViewName("/kqdsFront/jiagong/ycjg_edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/toToothMap.act"})
  public ModelAndView toToothMap(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/jiagong/toothMap.jsp");
    return mv;
  }
  
  @RequestMapping({"/toEdit.act"})
  public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    String sheetno = request.getParameter("sheetno");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("sheetno", sheetno);
    mv.setViewName("/kqdsFront/jiagong/jg_edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDetail.act"})
  public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    String sheetno = request.getParameter("sheetno");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("sheetno", sheetno);
    mv.setViewName("/kqdsFront/jiagong/jg_detail.jsp");
    return mv;
  }
  
  @RequestMapping({"/toKdxmWin.act"})
  public ModelAndView toKdxmWin(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    String doctorno = request.getParameter("doctorno");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("doctorno", doctorno);
    mv.setViewName("/kqdsFront/coatOrder/kdxmWin.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsOutprocessingSheet dp = new KqdsOutprocessingSheet();
      BeanUtils.populate(dp, request.getParameterMap());
      JSONObject jobj = new JSONObject();
      
      String seqId = request.getParameter("seqId");
      if (!YZUtility.isNullorEmpty(seqId))
      {
        String type = request.getParameter("type");
        String yaoqiu = request.getParameter("yaoqiu");
        String processingfactory = request.getParameter("processingfactory");
        dp.setType(type);
        dp.setYaoqiu(yaoqiu);
        if (!YZUtility.isNullorEmpty(processingfactory)) {
          dp.setProcessingfactory(processingfactory);
        } else {
          dp.setProcessingfactory("");
        }
        if (!YZUtility.isNullorEmpty(yaoqiu)) {
          dp.setYaoqiu(yaoqiu);
        } else {
          dp.setYaoqiu("");
        }
        this.logic.updateSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, dp);
        
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_OUTPROCESSING_SHEET, dp, dp.getUsercode(), TableNameUtil.KQDS_OUTPROCESSING_SHEET, request);
        

        Map<String, String> mapd = new HashMap();
        mapd.put("outprocessingsheetno", dp.getSeqId());
        List<KqdsOutprocessingSheetDetail> details = (List)this.logic.loadList(TableNameUtil.KQDS_OUTPROCESSING_SHEET_DETAIL, mapd);
        if (details.size() > 0) {
          for (KqdsOutprocessingSheetDetail d : details) {
            this.logic.deleteSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET_DETAIL, d.getSeqId());
          }
        }
        String params = request.getParameter("params");
        params = URLDecoder.decode(params, "UTF-8");
        JSONArray jArray = JSONArray.fromObject(params);
        Collection collection = JSONArray.toCollection(jArray, KqdsOutprocessingSheetDetail.class);
        Iterator it = collection.iterator();
        while (it.hasNext())
        {
          KqdsOutprocessingSheetDetail detail = (KqdsOutprocessingSheetDetail)it.next();
          String detailid = YZUtility.getUUID();
          detail.setSeqId(detailid);
          detail.setOutprocessingsheetno(dp.getSeqId());
          detail.setProcessingfactory(dp.getProcessingfactory());
          detail.setCreateuser(person.getSeqId());
          detail.setCreatetime(YZUtility.getCurDateTimeStr());
          detail.setOrganization(ChainUtil.getCurrentOrganization(request));
          
          this.logic.saveSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET_DETAIL, detail);
        }
        KqdsOutprocessingSheet en = (KqdsOutprocessingSheet)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, seqId);
        
        PushUtil.saveTx4OutProcessingSheetEdit(en, request);
      }
      else
      {
        String uuid = JGCodeUtil.getSheetNo(YZUtility.SHEET_NO_LENGTH, request);
        dp.setSeqId(uuid);
        dp.setOutprocessingsheetno(uuid);
        
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setDoctorno(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        dp.setStatus("0");
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        
        this.logic.saveSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, dp);
        

        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_OUTPROCESSING_SHEET, dp, dp.getUsercode(), TableNameUtil.KQDS_OUTPROCESSING_SHEET, request);
        

        String params = request.getParameter("params");
        params = URLDecoder.decode(params, "UTF-8");
        JSONArray jArray = JSONArray.fromObject(params);
        Collection collection = JSONArray.toCollection(jArray, KqdsOutprocessingSheetDetail.class);
        Iterator it = collection.iterator();
        while (it.hasNext())
        {
          KqdsOutprocessingSheetDetail detail = (KqdsOutprocessingSheetDetail)it.next();
          String detailid = YZUtility.getUUID();
          detail.setSeqId(detailid);
          detail.setOutprocessingsheetno(dp.getSeqId());
          detail.setProcessingfactory(dp.getProcessingfactory());
          detail.setCreateuser(person.getSeqId());
          detail.setCreatetime(YZUtility.getCurDateTimeStr());
          detail.setOrganization(ChainUtil.getCurrentOrganization(request));
          this.logic.saveSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET_DETAIL, detail);
        }
        List<JSONObject> personlist = this.logic.getAllJiagongPerson(ChainUtil.getCurrentOrganization(request));
        for (int i = 0; i < personlist.size(); i++) {
          PushUtil.saveTx4OutProcessingSheetNew(dp, (JSONObject)personlist.get(i), request);
        }
        jobj.put("sheetno", uuid);
      }
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/updatesheetstatus1.act"})
  public String updatesheetstatus1(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsOutprocessingSheet dp = new KqdsOutprocessingSheet();
      
      String seqId = request.getParameter("seqId");
      String status = request.getParameter("status");
      dp.setSeqId(seqId);
      dp.setStatus(status);
      if (status.equals(ConstUtil.JG_STATUS_1))
      {
        dp.setFajianperson(person.getSeqId());
        dp.setFajiantime(YZUtility.getCurDateTimeStr());
        this.logic.updateSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, dp);
      }
      else if (status.equals(ConstUtil.JG_STATUS_2))
      {
        dp.setHuijianperson(person.getSeqId());
        dp.setHuijiantime(YZUtility.getCurDateTimeStr());
        this.logic.updateSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, dp);
      }
      else if (status.equals(ConstUtil.JG_STATUS_3))
      {
        dp.setPickupperson(person.getSeqId());
        dp.setPickuptime(YZUtility.getCurDateTimeStr());
        this.logic.updateSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, dp);
      }
      else if (status.equals(ConstUtil.JG_STATUS_4))
      {
        dp.setFangongperson(person.getSeqId());
        dp.setFangongtime(YZUtility.getCurDateTimeStr());
        this.logic.updateSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, dp);
        

        String menzhen = ChainUtil.getCurrentOrganization(request);
        
        String uuid = JGCodeUtil.getSheetNo(YZUtility.SHEET_NO_LENGTH, request);
        

        KqdsOutprocessingSheet dp1 = (KqdsOutprocessingSheet)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, seqId);
        
        dp1.setSeqId(uuid);
        dp1.setOutprocessingsheetno(uuid);
        
        dp1.setOrganization(menzhen);
        

        dp1.setFajianperson(null);
        dp1.setFajiantime(null);
        dp1.setHuijianperson(null);
        dp1.setHuijiantime(null);
        dp1.setPickupperson(null);
        dp1.setPickuptime(null);
        dp1.setFangongperson(null);
        dp1.setFangongtime(null);
        dp1.setStatus("0");
        dp1.setCreatetime(YZUtility.getCurDateTimeStr());
        dp1.setOrganization(ChainUtil.getCurrentOrganization(request));
        
        this.logic.saveSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, dp1);
        

        Map<String, String> map = new HashMap();
        map.put("outprocessingsheetno", seqId);
        List<KqdsOutprocessingSheetDetail> details = (List)this.logic.loadList(TableNameUtil.KQDS_OUTPROCESSING_SHEET_DETAIL, map);
        if (details.size() > 0) {
          for (int i = 0; i < details.size(); i++)
          {
            KqdsOutprocessingSheetDetail d = (KqdsOutprocessingSheetDetail)details.get(i);
            String uuid2 = YZUtility.getUUID();
            d.setSeqId(uuid2);
            d.setOutprocessingsheetno(dp1.getSeqId());
            d.setOrganization(ChainUtil.getCurrentOrganization(request));
            this.logic.saveSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET_DETAIL, d);
          }
        }
        PushUtil.saveTx4OutProcessingSheetFG(dp1, request);
      }
      else if (status.equals(ConstUtil.JG_STATUS_5))
      {
        dp.setZuofeiperson(person.getSeqId());
        dp.setZuofeitime(YZUtility.getCurDateTimeStr());
        this.logic.updateSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, dp);
      }
      else
      {
        this.logic.updateSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, dp);
      }
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE_STATUS, BcjlUtil.KQDS_OUTPROCESSING_SHEET, dp, dp.getUsercode(), TableNameUtil.KQDS_OUTPROCESSING_SHEET, request);
      if (status.equals(ConstUtil.JG_STATUS_1))
      {
        KqdsOutprocessingSheet jiagogndan = (KqdsOutprocessingSheet)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, seqId);
        if (jiagogndan != null) {
          PushUtil.saveTx4OutProcessingSheetSubmit(jiagogndan, request);
        }
      }
      else if (status.equals(ConstUtil.JG_STATUS_2))
      {
        KqdsOutprocessingSheet en = (KqdsOutprocessingSheet)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, seqId);
        

        PushUtil.saveTx4OutProcessingSheetBack(en, request);
      }
      else if (!status.equals(ConstUtil.JG_STATUS_3))
      {
        if (!status.equals(ConstUtil.JG_STATUS_4)) {
          status.equals(ConstUtil.JG_STATUS_5);
        }
      }
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/deleteObj.act"})
  public String deleteObj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("主键为空或者null");
      }
      KqdsOutprocessingSheet en = (KqdsOutprocessingSheet)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, seqId);
      this.logic.deleteSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, seqId);
      
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.DELETE, BcjlUtil.KQDS_OUTPROCESSING_SHEET, en, en.getUsercode(), TableNameUtil.KQDS_OUTPROCESSING_SHEET, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      
      KqdsOutprocessingSheet en = (KqdsOutprocessingSheet)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, seqId);
      if (en == null) {
        throw new Exception("数据不存在");
      }
      JSONObject jobj = new JSONObject();
      jobj.put("data", en);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectList.act"})
  public String selectList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      BootStrapPage bp = new BootStrapPage();
      String usercode = request.getParameter("usercode");
      
      BeanUtils.populate(bp, request.getParameterMap());
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(usercode))
      {
        map.put("usercode", usercode);
      }
      else
      {
        String visualstaff = SessionUtil.getVisualstaff(request);
        map.put("doctorno", visualstaff);
      }
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      
      String num = request.getParameter("num");
      String org = request.getParameter("org");
      String doctor = request.getParameter("doctor");
      String queryInput = request.getParameter("queryInput");
      String status = request.getParameter("status");
      
      String fjtime1 = request.getParameter("fjtime1");
      String fjtime2 = request.getParameter("fjtime2");
      String hjtime1 = request.getParameter("hjtime1");
      String hjtime2 = request.getParameter("hjtime2");
      String dztime1 = request.getParameter("dztime1");
      String dztime2 = request.getParameter("dztime2");
      String fgtime1 = request.getParameter("fgtime1");
      String fgtime2 = request.getParameter("fgtime2");
      
      String createtime1 = request.getParameter("createtime1");
      String createtime2 = request.getParameter("createtime2");
      if (num == null) {
        num = "1";
      }
      if ((!YZUtility.isNullorEmpty(fjtime1)) && (!fjtime1.equals("null")) && (!fjtime1.equals("undefined"))) {
        map.put("fjtime1", fjtime1 + ConstUtil.TIME_START);
      }
      if ((!YZUtility.isNullorEmpty(fjtime2)) && (!fjtime2.equals("null")) && (!fjtime2.equals("undefined"))) {
        map.put("fjtime2", fjtime2 + ConstUtil.TIME_END);
      }
      if ((!YZUtility.isNullorEmpty(hjtime1)) && (!hjtime1.equals("null")) && (!hjtime1.equals("undefined"))) {
        map.put("hjtime1", hjtime1 + ConstUtil.TIME_START);
      }
      if ((!YZUtility.isNullorEmpty(hjtime2)) && (!hjtime2.equals("null")) && (!hjtime2.equals("undefined"))) {
        map.put("hjtime2", hjtime2 + ConstUtil.TIME_END);
      }
      if ((!YZUtility.isNullorEmpty(dztime1)) && (!dztime1.equals("null")) && (!dztime1.equals("undefined"))) {
        map.put("dztime1", dztime1 + ConstUtil.TIME_START);
      }
      if ((!YZUtility.isNullorEmpty(dztime2)) && (!dztime2.equals("null")) && (!dztime2.equals("undefined"))) {
        map.put("dztime2", dztime2 + ConstUtil.TIME_END);
      }
      if ((!YZUtility.isNullorEmpty(fgtime1)) && (!fgtime1.equals("null")) && (!fgtime1.equals("undefined"))) {
        map.put("fgtime1", fgtime1 + ConstUtil.TIME_START);
      }
      if ((!YZUtility.isNullorEmpty(fgtime2)) && (!fgtime2.equals("null")) && (!fgtime2.equals("undefined"))) {
        map.put("fgtime2", fgtime2 + ConstUtil.TIME_END);
      }
      if ((!YZUtility.isNullorEmpty(createtime1)) && (!createtime1.equals("null")) && (!createtime1.equals("undefined"))) {
        map.put("createtime1", createtime1 + ConstUtil.TIME_START);
      }
      if ((!YZUtility.isNullorEmpty(createtime2)) && (!createtime2.equals("null")) && (!createtime2.equals("undefined"))) {
        map.put("createtime2", createtime2 + ConstUtil.TIME_END);
      }
      if ((!YZUtility.isNullorEmpty(org)) && (!org.equals("null")) && (!org.equals("undefined"))) {
        map.put("org", org);
      }
      if ((!YZUtility.isNullorEmpty(doctor)) && (!doctor.equals("null")) && (!doctor.equals("undefined"))) {
        map.put("doctor", doctor);
      }
      if ((!YZUtility.isNullorEmpty(queryInput)) && (!queryInput.equals("null")) && (!queryInput.equals("undefined"))) {
        map.put("queryInput", queryInput);
      }
      if ((!YZUtility.isNullorEmpty(status)) && (!status.equals("null")) && (!status.equals("undefined"))) {
        map.put("status", status);
      }
      String sno = request.getParameter("sno");
      if ((!YZUtility.isNullorEmpty(sno)) && (!sno.equals("null")) && (!sno.equals("undefined"))) {
        map.put("sno", sno);
      }
      String unit = request.getParameter("unit");
      if ((!YZUtility.isNullorEmpty(unit)) && (!unit.equals("null")) && (!unit.equals("undefined"))) {
        map.put("processingfactory", unit);
      }
      List<JSONObject> list = this.logic.selectListByQuery(TableNameUtil.KQDS_OUTPROCESSING_SHEET, map, num);
      if ((flag != null) && (flag.equals("exportTable")))
      {
        ExportTable.exportBootStrapTable2Excel("加工中心列表", fieldArr, fieldnameArr, list, response, request);
        return null;
      }
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectPageWjg.act"})
  public String selectPageWjg(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      BootStrapPage bp = new BootStrapPage();
      String usercode = request.getParameter("usercode");
      
      BeanUtils.populate(bp, request.getParameterMap());
      Map<String, String> map = new HashMap();
      map.put("usercode", usercode);
      List<JSONObject> list = this.logic.selectWithPageLzjl(TableNameUtil.KQDS_OUTPROCESSING_SHEET, map);
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
