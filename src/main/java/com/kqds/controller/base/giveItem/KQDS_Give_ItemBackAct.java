package com.kqds.controller.base.giveItem;

import com.kqds.entity.base.KqdsGiveitem;
import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.giveItem.KQDS_Give_ItemLogic;
import com.kqds.service.base.treatItem.KQDS_TreatItemLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
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
@RequestMapping({"KQDS_Give_ItemBackAct"})
public class KQDS_Give_ItemBackAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_Give_ItemBackAct.class);
  @Autowired
  private KQDS_Give_ItemLogic logic = new KQDS_Give_ItemLogic();
  @Autowired
  private KQDS_TreatItemLogic treatlogic = new KQDS_TreatItemLogic();
  
  @RequestMapping({"/toIndexLs.act"})
  public ModelAndView toJgIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqds/giveItem/index_ls.jsp");
    return mv;
  }
  
  @RequestMapping({"/toGetsfitem.act"})
  public ModelAndView toGetsfitem(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/giveItem/getsfitem.jsp");
    return mv;
  }
  
  @RequestMapping({"/toList.act"})
  public ModelAndView toList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/giveItem/list_kqds_give_item.jsp");
    return mv;
  }
  
  @RequestMapping({"/toEdit.act"})
  public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqds/giveItem/edit_kqds_give_item.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDentaLaboratory.act"})
  public ModelAndView toDentaLaboratory(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqds/giveItem/dentaLaboratory.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDetail.act"})
  public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqds/giveItem/detail_kqds_give_item.jsp");
    return mv;
  }
  
  @RequestMapping({"/toAdd.act"})
  public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/giveItem/add_kqds_give_item.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsGiveitem dp = new KqdsGiveitem();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(dp.getItemno())) {
        throw new Exception("收费项目编号不能为空");
      }
      KqdsTreatitem treatitem = this.treatlogic.getByTreatItemno(dp.getItemno());
      if (treatitem == null) {
        throw new Exception("收费编号对应的收费项目不存在");
      }
      if (1 == treatitem.getIsyjjitem().intValue()) {
        throw new Exception("预交金不能作为赠送项目");
      }
      if (!YZUtility.isNullorEmpty(seqId))
      {
        this.logic.updateSingleUUID(TableNameUtil.KQDS_GIVEITEM, dp);
        
        BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_GIVEITEM, dp, TableNameUtil.KQDS_GIVEITEM, request);
      }
      else
      {
        int count = this.logic.getCountByItemnos(dp.getItemno());
        if (count > 0) {
          throw new Exception("该赠送项目已存在，编号为：" + dp.getItemno());
        }
        dp.setSeqId(YZUtility.getUUID());
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getOrganizationFromUrlCanNull(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_GIVEITEM, dp);
        
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_GIVEITEM, dp, TableNameUtil.KQDS_GIVEITEM, request);
      }
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
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
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      KqdsGiveitem en = (KqdsGiveitem)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_GIVEITEM, seqId);
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
      String itemno = request.getParameter("itemno");
      String itemname = request.getParameter("itemname");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(itemno)) {
        map.put("itemno", itemno);
      }
      if (!YZUtility.isNullorEmpty(itemname)) {
        map.put("itemname", itemname);
      }
      map.put("organization", ChainUtil.getOrganizationFromUrlCanNull(request));
      JSONObject data = this.logic.selectWithPage(TableNameUtil.KQDS_BCJL, bp, map);
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getGiveItemList4Back.act"})
  public String getGiveItemList4Back(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
    try
    {
      List<KqdsGiveitem> en = this.logic.getGiveItemList(organization);
      JSONObject jobj = new JSONObject();
      jobj.put("retState", "0");
      jobj.put("retMsrg", "操作成功");
      jobj.put("data", en);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getGiveItemList4Front.act"})
  public String getGiveItemList4Front(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = ChainUtil.getCurrentOrganization(request);
    try
    {
      List<KqdsGiveitem> en = this.logic.getGiveItemList(organization);
      JSONObject jobj = new JSONObject();
      jobj.put("retState", "0");
      jobj.put("retMsrg", "操作成功");
      jobj.put("data", en);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
