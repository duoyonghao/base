package com.kqds.controller.base.treatItem;

import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.treatItem.KQDS_TreatItemLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
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
@RequestMapping({"KQDS_TreatItemBackAct"})
public class KQDS_TreatItemBackAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_TreatItemBackAct.class);
  @Autowired
  private KQDS_TreatItemLogic logic;
  
  @RequestMapping({"/toList.act"})
  public ModelAndView toList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/treatItem/list_kqds_treatitem.jsp");
    return mv;
  }
  
  @RequestMapping({"/toEdit.act"})
  public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/treatItem/edit_kqds_treatitem.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDetail.act"})
  public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/treatItem/detail_kqds_treatitem.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNewAdd.act"})
  public ModelAndView toNewAdd(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/treatItem/add_kqds_treatitem.jsp");
    return mv;
  }
  
  @RequestMapping({"/getAutoCode4Add.act"})
  public String getAutoCode4Add(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String autoCode = this.logic.getAutoCode4Add(ChainUtil.getCurrentOrganization(request));
      JSONObject retrunObj = new JSONObject();
      retrunObj.put("autoCode", autoCode);
      YZUtility.DEAL_SUCCESS(retrunObj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsTreatitem dp = new KqdsTreatitem();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (!YZUtility.isNullorEmpty(seqId))
      {
        this.logic.updateSingleUUID(TableNameUtil.KQDS_TREATITEM, dp);
        
        BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_TREATITEM, dp, TableNameUtil.KQDS_TREATITEM, request);
      }
      else
      {
        String uuid = YZUtility.getUUID();
        dp.setSeqId(uuid);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        
        this.logic.saveSingleUUID(TableNameUtil.KQDS_TREATITEM, dp);
        
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_TREATITEM, dp, TableNameUtil.KQDS_TREATITEM, request);
      }
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
      KqdsTreatitem en = (KqdsTreatitem)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_TREATITEM, seqId);
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
  
  @RequestMapping({"/getOneBytreatitemno4Back.act"})
  public String getOneBytreatitemno4Back(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String treatitemno = request.getParameter("treatitemno");
      Map<String, String> map = new HashMap();
      map.put("treatitemno", treatitemno);
      List<KqdsTreatitem> en = (List)this.logic.loadList(TableNameUtil.KQDS_TREATITEM, map);
      JSONObject jobj = new JSONObject();
      jobj.put("retState", "0");
      jobj.put("retMsrg", "操作成功");
      if ((en != null) && (en.size() > 0)) {
        jobj.put("data", en.get(0));
      } else {
        jobj.put("data", "");
      }
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/yzByTreatItemNo.act"})
  public String yzByTreatItemNo(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String treatitemno = request.getParameter("treatitemno");
      Map<String, String> map = new HashMap();
      map.put("treatitemno", treatitemno);
      boolean result = true;
      List<KqdsTreatitem> en = (List)this.logic.loadList(TableNameUtil.KQDS_TREATITEM, map);
      if ((en != null) && (en.size() > 0)) {
        result = false;
      }
      YZUtility.DEAL_SUCCESS_VALID(result, response);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectPage.act"})
  public String selectPage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String basetype = request.getParameter("basetype");
      String nexttype = request.getParameter("nexttype");
      String treatitemname = request.getParameter("treatitemname");
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(basetype)) {
        map.put("basetype", basetype);
      }
      if (!YZUtility.isNullorEmpty(nexttype)) {
        map.put("nexttype", nexttype);
      }
      if (!YZUtility.isNullorEmpty(treatitemname)) {
        map.put("treatitemname", treatitemname);
      }
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      map.put("organization", organization);
      if (!YZUtility.isNullorEmpty(flag)) {
        map.put("flag", flag);
      }
      JSONObject data = this.logic.selectWithPage(TableNameUtil.KQDS_TREATITEM, bp, map);
      if ((flag != null) && (flag.equals("exportTable")))
      {
        ExportTable.exportBootStrapTable2Excel("收费项目", fieldArr, fieldnameArr, data.getJSONArray("rows"), response, request);
        return null;
      }
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getItemList.act"})
  public void getItemList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String querydata = request.getParameter("querydata");
      List<JSONObject> list = this.logic.getItemList(querydata, ChainUtil.getOrganizationFromUrlCanNull(request));
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
  }
  
  @RequestMapping({"/deleteObject.act"})
  public String deleteObject(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("seqId为空");
      }
      int count = this.logic.delelteBySeqIds(seqId, request);
      YZUtility.DEAL_SUCCESS(null, "删除" + count + "条记录", response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/deleteAll.act"})
  public String deleteAll(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      int count = this.logic.delelteAll(ChainUtil.getOrganizationFromUrlCanNull(request), request);
      YZUtility.DEAL_SUCCESS(null, "清空" + count + "条记录", response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/excelStandardTemplateOut.act"})
  public void excelStandardTemplateOut(HttpServletRequest request, HttpServletResponse response)
    throws IOException
  {
    File f = new File(ConstUtil.ROOT_DIR + "\\model\\收费项目模板.xls");
    
    response.reset();
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    try
    {
      response.setHeader("Content-Disposition", "attachment;filename=" + new String("收费项目模板.xls".getBytes(), "iso-8859-1"));
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    ServletOutputStream out = response.getOutputStream();
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    try
    {
      bis = new BufferedInputStream(new FileInputStream(f));
      bos = new BufferedOutputStream(out);
      byte[] buff = new byte[2048];
      int bytesRead;
      while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
      {
        int bytesRead;
        bos.write(buff, 0, bytesRead);
      }
    }
    catch (IOException e)
    {
      throw e;
    }
    finally
    {
      if (bis != null) {
        bis.close();
      }
      if (bos != null) {
        bos.close();
      }
    }
  }
}
