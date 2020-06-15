package com.kqds.controller.wx.templateitem;

import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.wx.WXTemplateitem;
import com.kqds.service.wx.core.WXUtil4TemplateMsgLogic;
import com.kqds.service.wx.templateitem.WXTemplateItemLogic;
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
@RequestMapping({"WXTemplateItemAct"})
public class WXTemplateItemAct
{
  private static Logger logger = LoggerFactory.getLogger(WXTemplateItemAct.class);
  @Autowired
  private WXTemplateItemLogic logic;
  @Autowired
  private WXUtil4TemplateMsgLogic wxUtil4TemplateMsgLogic;
  
  @RequestMapping({"/toNewAdd.act"})
  public ModelAndView toNewAdd(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/wechat/templateitem/newadd/add.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBatchSend.act"})
  public ModelAndView toBatchSend(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/wechat/templateitem/batchsend/send.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBatchSendLeft.act"})
  public ModelAndView toBatchSendLeft(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/wechat/templateitem/batchsend/left.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNewAddLeft.act"})
  public ModelAndView toNewAddLeft(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/wechat/templateitem/newadd/left.jsp");
    return mv;
  }
  
  @RequestMapping({"/toSendLeft.act"})
  public ModelAndView toSendLeft(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String openid = request.getParameter("openid");
    ModelAndView mv = new ModelAndView();
    mv.addObject("openid", openid);
    mv.setViewName("/wechat/templateitem/send/left.jsp");
    return mv;
  }
  
  @RequestMapping({"/toList.act"})
  public ModelAndView toList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/wechat/templateitem/list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toIndex.act"})
  public ModelAndView toIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/wechat/templateitem/batchsend/index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toIndex2.act"})
  public ModelAndView toIndex2(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/wechat/templateitem/newadd/index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toSend.act"})
  public ModelAndView toSend(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    String templateSeqid = request.getParameter("templateSeqid");
    String templateId = request.getParameter("templateId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("templateSeqid", templateSeqid);
    mv.addObject("templateId", templateId);
    mv.setViewName("/wechat/templateitem/batchsend/send.jsp");
    return mv;
  }
  
  @RequestMapping({"/toSend2.act"})
  public ModelAndView toSend2(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    String templateSeqid = request.getParameter("templateSeqid");
    String templateId = request.getParameter("templateId");
    String openid = request.getParameter("openid");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("templateSeqid", templateSeqid);
    mv.addObject("templateId", templateId);
    mv.addObject("openid", openid);
    mv.setViewName("/wechat/templateitem/send/send.jsp");
    return mv;
  }
  
  @RequestMapping({"/toAddNew.act"})
  public ModelAndView toAddNew(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String templateSeqid = request.getParameter("templateSeqid");
    String templateId = request.getParameter("templateId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("templateSeqid", templateSeqid);
    mv.addObject("templateId", templateId);
    mv.setViewName("/wechat/templateitem/newadd/add.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNewEdit.act"})
  public ModelAndView toNewEdit(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    String templateSeqid = request.getParameter("templateSeqid");
    String templateId = request.getParameter("templateId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("templateSeqid", templateSeqid);
    mv.addObject("templateId", templateId);
    mv.setViewName("/wechat/templateitem/newadd/edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/template_send.act"})
  public String template_send(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      WXTemplateitem dp = new WXTemplateitem();
      BeanUtils.populate(dp, request.getParameterMap());
      
      String templateId = request.getParameter("templateId");
      String openid = request.getParameter("openid");
      if (YZUtility.isNullorEmpty(openid)) {
        throw new Exception("openid不能为空");
      }
      if (YZUtility.isNullorEmpty(templateId)) {
        throw new Exception("templateId不能为空");
      }
      if (YZUtility.isNullorEmpty(dp.getFirst())) {
        throw new Exception("first不能为空");
      }
      JSONObject postParam = this.logic.getTemplateItem4Send(dp, request);
      JSONObject json = this.wxUtil4TemplateMsgLogic.template_send(postParam, openid, request);
      YZUtility.DEAL_SUCCESS(json, "发送模板消息成功！", response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectList.act"})
  public String selectList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      Map<String, String> map = new HashMap();
      List<JSONObject> list = this.logic.selectList(map);
      JSONObject json = new JSONObject();
      json.put("list", list);
      YZUtility.DEAL_SUCCESS(json, "查询成功", response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      WXTemplateitem dp = new WXTemplateitem();
      BeanUtils.populate(dp, request.getParameterMap());
      if (YZUtility.isNullorEmpty(dp.getTemplateId())) {
        throw new Exception("templateId不能为空");
      }
      if (YZUtility.isNullorEmpty(dp.getTemplateSeqid())) {
        throw new Exception("templateSeqid不能为空");
      }
      if (YZUtility.isNullorEmpty(dp.getFirst())) {
        throw new Exception("first不能为空");
      }
      if (!YZUtility.isNullorEmpty(dp.getSeqId()))
      {
        this.logic.updateSingleUUID(TableNameUtil.WX_TEMPLATEITEM, dp);
        
        BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.WX_TEMPLATEITEM, dp, TableNameUtil.WX_TEMPLATEITEM, request);
      }
      else
      {
        dp.setSeqId(YZUtility.getUUID());
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(YZUtility.getCurrLoginPersonSeqId(request));
        this.logic.saveSingleUUID(TableNameUtil.WX_TEMPLATEITEM, dp);
        
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.WX_TEMPLATEITEM, dp, TableNameUtil.WX_TEMPLATEITEM, request);
      }
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
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
      
      WXTemplateitem en = (WXTemplateitem)this.logic.loadObjSingleUUID(TableNameUtil.WX_TEMPLATEITEM, seqId);
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
  
  @RequestMapping({"/selectPage4Manage.act"})
  public String selectPage4Manage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      

      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(flag)) {
        map.put("flag", flag);
      }
      JSONObject data = this.logic.selectPage(bp, map);
      if ((flag != null) && (flag.equals("exportTable")))
      {
        ExportTable.exportBootStrapTable2Excel("模板消息列表", fieldArr, fieldnameArr, data.getJSONArray("rows"), response, request);
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
      WXTemplateitem en = (WXTemplateitem)this.logic.loadObjSingleUUID(TableNameUtil.WX_TEMPLATEITEM, seqId);
      this.logic.deleteSingleUUID(TableNameUtil.WX_TEMPLATEITEM, seqId);
      
      BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.WX_TEMPLATEITEM, en, TableNameUtil.WX_TEMPLATEITEM, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
}
