package com.kqds.controller.base.information;

import com.kqds.entity.base.KqdsInformation;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.information.KQDS_InformationLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
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
@RequestMapping({"KQDS_InformationBackAct"})
public class KQDS_InformationBackAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_InformationBackAct.class);
  @Autowired
  private KQDS_InformationLogic logic = new KQDS_InformationLogic();
  
  @RequestMapping({"/toIndex.act"})
  public ModelAndView toIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/information/list_kqds_information.jsp");
    return mv;
  }
  
  @RequestMapping({"/toEdit.act"})
  public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqds/information/edit_kqds_information.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDetail.act"})
  public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqds/information/detail_kqds_information.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNewAdd.act"})
  public ModelAndView toNewAdd(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/information/add_kqds_information.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      KqdsInformation dp = new KqdsInformation();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (!YZUtility.isNullorEmpty(seqId))
      {
        this.logic.updateSingleUUID(TableNameUtil.KQDS_INFORMATION, dp);
        
        BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_INFORMATION, dp, TableNameUtil.KQDS_INFORMATION, request);
      }
      else
      {
        String uuid = YZUtility.getUUID();
        dp.setSeqId(uuid);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getOrganizationFromUrlCanNull(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_INFORMATION, dp);
        
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_INFORMATION, dp, TableNameUtil.KQDS_INFORMATION, request);
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
      KqdsInformation en = (KqdsInformation)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_INFORMATION, seqId);
      this.logic.deleteSingleUUID(TableNameUtil.KQDS_INFORMATION, seqId);
      
      BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_INFORMATION, en, TableNameUtil.KQDS_INFORMATION, request);
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
      KqdsInformation en = (KqdsInformation)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_INFORMATION, seqId);
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
  
  @RequestMapping({"/selectPage.act"})
  public String selectPage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      String type = request.getParameter("type");
      String title = request.getParameter("title");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(type)) {
        map.put("type", type);
      }
      if (!YZUtility.isNullorEmpty(title)) {
        map.put("title", title);
      }
      map.put("organization", ChainUtil.getOrganizationFromUrlCanNull(request));
      JSONObject data = this.logic.selectWithPage(TableNameUtil.KQDS_INFORMATION, bp, map);
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/excelStandardTemplateOut.act"})
  public void excelStandardTemplateOut(HttpServletRequest request, HttpServletResponse response)
    throws IOException
  {
    File f = new File(ConstUtil.ROOT_DIR + "\\model\\信息库导入模板.xls");
    
    response.reset();
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    try
    {
      response.setHeader("Content-Disposition", "attachment;filename=" + new String("信息库导入模板.xls".getBytes(), "iso-8859-1"));
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
