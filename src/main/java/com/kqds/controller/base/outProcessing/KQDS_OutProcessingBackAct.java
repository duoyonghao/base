package com.kqds.controller.base.outProcessing;

import com.kqds.entity.base.KqdsOutprocessing;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.outProcessing.KQDS_OutProcessingLogic;
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
import java.io.OutputStream;
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
@RequestMapping({"KQDS_OutProcessingBackAct"})
public class KQDS_OutProcessingBackAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_OutProcessingBackAct.class);
  
  @Autowired
  private KQDS_OutProcessingLogic logic;
  
  @RequestMapping({"/toIndexLs.act"})
  public ModelAndView toJgIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqds/outProcessing/index_ls.jsp");
    return mv;
  }
  
  @RequestMapping({"/toList.act"})
  public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/outProcessing/list_kqds_outprocessing.jsp");
    return mv;
  }
  
  @RequestMapping({"/toEdit.act"})
  public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/outProcessing/edit_kqds_outprocessing.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDetail.act"})
  public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/outProcessing/detail_kqds_outprocessing.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNewAdd.act"})
  public ModelAndView toNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/outProcessing/add_kqds_outprocessing.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsOutprocessing dp = new KqdsOutprocessing();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (!YZUtility.isNullorEmpty(seqId)) {
        this.logic.updateSingleUUID(TableNameUtil.KQDS_OUTPROCESSING, dp);
        BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_OUTPROCESSING, dp, TableNameUtil.KQDS_OUTPROCESSING, request);
      } else {
        String uuid = YZUtility.getUUID();
        dp.setSeqId(uuid);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getOrganizationFromUrlCanNull(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_OUTPROCESSING, dp);
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_OUTPROCESSING, dp, TableNameUtil.KQDS_OUTPROCESSING, request);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/yzBywjgxmbh.act"})
  public String yzBywjgxmbh(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String wjgxmbh = request.getParameter("wjgxmbh");
      Map<String, String> map = new HashMap<>();
      map.put("wjgxmbh", wjgxmbh);
      boolean result = true;
      List<KqdsOutprocessing> en = (List<KqdsOutprocessing>)this.logic.loadList(TableNameUtil.KQDS_OUTPROCESSING, map);
      if (en != null && en.size() > 0)
        result = false; 
      YZUtility.DEAL_SUCCESS_VALID(result, response);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteObj.act"})
  public String deleteObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId))
        throw new Exception("主键为空或者null"); 
      int count = this.logic.delelteBySeqIds(seqId, request);
      YZUtility.DEAL_SUCCESS(null, "删除" + count + "条记录", response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteAll.act"})
  public String deleteAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      int count = this.logic.delelteAll(ChainUtil.getOrganizationFromUrlCanNull(request), request);
      YZUtility.DEAL_SUCCESS(null, "清空" + count + "条记录", response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      KqdsOutprocessing en = (KqdsOutprocessing)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_OUTPROCESSING, seqId);
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
  
  @RequestMapping({"/selectPage.act"})
  public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      String mrjgc = request.getParameter("mrjgc");
      String wjgfl = request.getParameter("wjgfl");
      String wjgmc = request.getParameter("wjgmc");
      String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
      String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
      String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(mrjgc))
        map.put("mrjgc", mrjgc); 
      if (!YZUtility.isNullorEmpty(wjgfl))
        map.put("wjgfl", wjgfl); 
      if (!YZUtility.isNullorEmpty(wjgmc))
        map.put("wjgmc", wjgmc); 
      if (!YZUtility.isNullorEmpty(flag))
        map.put("flag", flag); 
      map.put("organization", ChainUtil.getOrganizationFromUrlCanNull(request));
      JSONObject data = this.logic.selectWithPage(TableNameUtil.KQDS_OUTPROCESSING, bp, map);
      if (flag != null && flag.equals("exportTable")) {
        ExportTable.exportBootStrapTable2Excel("加工项目", fieldArr, fieldnameArr, (List)data.getJSONArray("rows"), response, request);
        return null;
      } 
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/excelStandardTemplateOut.act"})
  public void excelStandardTemplateOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
    File f = new File(String.valueOf(ConstUtil.ROOT_DIR) + "\\model\\加工项目模板.xls");
    response.reset();
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    try {
      response.setHeader("Content-Disposition", "attachment;filename=" + new String("加工项目模板.xls".getBytes(), "iso-8859-1"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } 
    ServletOutputStream out = response.getOutputStream();
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    try {
      bis = new BufferedInputStream(new FileInputStream(f));
      bos = new BufferedOutputStream((OutputStream)out);
      byte[] buff = new byte[2048];
      int bytesRead;
      while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
        bos.write(buff, 0, bytesRead); 
    } catch (IOException e) {
      throw e;
    } finally {
      if (bis != null)
        bis.close(); 
      if (bos != null)
        bos.close(); 
    } 
  }
}
