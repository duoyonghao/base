package com.kqds.controller.base.integralRecord;

import com.kqds.entity.base.KqdsIntegralRecord;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.integralRecord.KQDS_Integral_RecordLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportBean;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.sys.DictUtil;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
@RequestMapping({"KQDS_Integral_RecordAct"})
public class KQDS_Integral_RecordAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_Integral_RecordAct.class);
  @Autowired
  private KQDS_Integral_RecordLogic logic;
  @Autowired
  private YZDictLogic dictLogic;
  
  @RequestMapping({"/toJfcxCenter.act"})
  public ModelAndView toJfcxCenter(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/hzjd/jfcx_center.jsp");
    return mv;
  }
  
  @RequestMapping({"/toAddIntegral.act"})
  public ModelAndView toAddIntegral(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/hzjd/add_integral.jsp");
    return mv;
  }
  
  @RequestMapping({"/toSubIntegral.act"})
  public ModelAndView toSubIntegral(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/hzjd/sub_integral.jsp");
    return mv;
  }
  
  @RequestMapping({"/insert.act"})
  public String insert(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsIntegralRecord dp = new KqdsIntegralRecord();
      BeanUtils.populate(dp, request.getParameterMap());
      Map<String, String> map2 = new HashMap();
      map2.put("usercode", dp.getUsercode());
      List<KqdsUserdocument> userlist = (List)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map2);
      if (userlist == null) {
        throw new Exception("患者不存在！");
      }
      KqdsUserdocument u = (KqdsUserdocument)userlist.get(0);
      

      u.setIntegral(u.getIntegral().add(dp.getJfmoney()));
      this.logic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, u);
      
      String jfzj = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JFLX, "手动增加");
      String jfjs = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JFLX, "手动减少");
      if ((YZUtility.isNullorEmpty(jfzj)) || (YZUtility.isNullorEmpty(jfjs))) {
        throw new Exception("积分类型不存在！");
      }
      if (dp.getJftype().equals(ConstUtil.JFZJ)) {
        dp.setJftype(jfzj);
      } else if (dp.getJftype().equals(ConstUtil.JFJS)) {
        dp.setJftype(jfjs);
      }
      dp.setSeqId(UUID.randomUUID().toString());
      dp.setCreatetime(YZUtility.getCurDateTimeStr());
      dp.setOrganization(ChainUtil.getCurrentOrganization(request));
      dp.setSyjfmoney(u.getIntegral());
      dp.setCreateuser(person.getSeqId());
      this.logic.saveSingleUUID(TableNameUtil.KQDS_INTEGRAL_RECORD, dp);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getRecordList.act"})
  public String getRecordList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      YZPerson person = SessionUtil.getLoginPerson(request);
      String fzsj = request.getParameter("fzsj");
      String fzsj2 = request.getParameter("fzsj2");
      String usercodeorname = request.getParameter("usercodeorname");
      
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(fzsj))
      {
        fzsj = fzsj + ConstUtil.TIME_START;
        map.put("fzsj", fzsj);
      }
      if (!YZUtility.isNullorEmpty(fzsj2))
      {
        fzsj2 = fzsj2 + ConstUtil.TIME_END;
        map.put("fzsj2", fzsj2);
      }
      if (!YZUtility.isNullorEmpty(usercodeorname)) {
        map.put("usercodeorname", usercodeorname);
      }
      String organization = ChainUtil.getOrganizationFromUrl(request);
      if (YZUtility.isNullorEmpty(organization)) {
        map.put("organization", ChainUtil.getCurrentOrganization(request));
      } else {
        map.put("organization", organization);
      }
      String ispaging = request.getParameter("ispaging");
      if ((!YZUtility.isNullorEmpty(ispaging)) && (YZUtility.isNullorEmpty(flag))) {
        map.put("ispaging", ispaging);
      } else {
        map.put("ispaging", ConstUtil.PAGE_FLAG_0);
      }
      String visualstaff = SessionUtil.getVisualstaff(request);
      if ((flag != null) && (flag.equals("exportTable")))
      {
        JSONObject resut1 = this.logic.selectWithPage(bp, TableNameUtil.KQDS_INTEGRAL_RECORD, person, map, visualstaff);
        if (resut1 != null)
        {
          ExportBean bean = ExportTable.initExcel4RsWrite("积分查询", fieldArr, fieldnameArr, response, request);
          bean = ExportTable.exportBootStrapTable2ExcelByResult(bean, (List)resut1.get("rows"), "");
          ExportTable.writeExcel4DownLoad("积分查询", bean.getWorkbook(), response);
        }
        return null;
      }
      JSONObject data = this.logic.selectWithPage(bp, TableNameUtil.KQDS_INTEGRAL_RECORD, person, map, visualstaff);
      if (!YZUtility.isNullorEmpty(ispaging)) {
        YZUtility.DEAL_SUCCESS(data, null, response, logger);
      } else {
        YZUtility.RETURN_LIST(data.getJSONArray("rows"), response, logger);
      }
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
