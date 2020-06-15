package com.kqds.controller.base.giveItemTc;

import com.kqds.entity.base.KqdsGiveitem;
import com.kqds.entity.base.KqdsGiveitemGiverecord;
import com.kqds.entity.base.KqdsGiveitemTc;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.giveItemTc.KQDS_GiveItem_TCLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import java.net.URLDecoder;
import java.util.ArrayList;
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
@RequestMapping({"KQDS_GiveItem_TCBackAct"})
public class KQDS_GiveItem_TCBackAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_GiveItem_TCBackAct.class);
  @Autowired
  private KQDS_GiveItem_TCLogic logic = new KQDS_GiveItem_TCLogic();
  
  @RequestMapping({"/toIndexLs.act"})
  public ModelAndView toJgIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqds/giveItemTc/index_ls.jsp");
    return mv;
  }
  
  @RequestMapping({"/toList.act"})
  public ModelAndView toList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/giveItemTc/list_kqds_giveitem_tc.jsp");
    return mv;
  }
  
  @RequestMapping({"/toEdit.act"})
  public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    String name = request.getParameter("name");
    String useflag = request.getParameter("useflag");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("name", name);
    mv.addObject("useflag", useflag);
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/giveItemTc/edit_kqds_giveitem_tc.jsp");
    return mv;
  }
  
  @RequestMapping({"/toAdd.act"})
  public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/giveItemTc/add_kqds_giveitem_tc.jsp");
    return mv;
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
      KqdsGiveitemTc en = (KqdsGiveitemTc)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_GIVEITEM_TC, seqId);
      this.logic.deleteSingleUUID(TableNameUtil.KQDS_GIVEITEM_TC, seqId);
      
      BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_GIVEITEM_TC, en, TableNameUtil.KQDS_GIVEITEM_TC, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
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
      String name = request.getParameter("name");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(name)) {
        map.put("name", name);
      }
      map.put("organization", ChainUtil.getOrganizationFromUrlCanNull(request));
      JSONObject data = this.logic.selectWithPage(TableNameUtil.KQDS_GIVEITEM_TC, bp, map);
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/saveGiveTc.act"})
  public String saveGiveTc(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      String params = request.getParameter("params");
      String name = request.getParameter("name");
      String seqId = request.getParameter("seqId");
      params = URLDecoder.decode(params, "UTF-8");
      
      String useflag = request.getParameter("useflag");
      if (YZUtility.isNullorEmpty(useflag)) {
        throw new Exception("参数useflag值为空或者null");
      }
      JSONArray jArray = JSONArray.fromObject(params);
      Collection collection = JSONArray.toCollection(jArray, KqdsGiveitemTc.class);
      Iterator it = collection.iterator();
      String itemnos = "";
      String nums = "";
      while (it.hasNext())
      {
        KqdsGiveitemTc dp = (KqdsGiveitemTc)it.next();
        itemnos = itemnos + dp.getItemno() + ",";
        nums = nums + dp.getNum() + ",";
      }
      KqdsGiveitemTc tc = new KqdsGiveitemTc();
      tc.setName(name);
      tc.setItemno(itemnos);
      tc.setNum(nums);
      tc.setUseflag(Integer.valueOf(Integer.parseInt(useflag)));
      if (YZUtility.isNullorEmpty(seqId))
      {
        String uuid = YZUtility.getUUID();
        tc.setSeqId(uuid);
        tc.setRemark(tc.getRemark());
        tc.setCreatetime(YZUtility.getCurDateTimeStr());
        tc.setCreateuser(person.getSeqId());
        tc.setOrganization(ChainUtil.getOrganizationFromUrlCanNull(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_GIVEITEM_TC, tc);
        
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_GIVEITEM_TC, tc, TableNameUtil.KQDS_GIVEITEM_TC, request);
      }
      else
      {
        tc.setSeqId(seqId);
        this.logic.updateSingleUUID(TableNameUtil.KQDS_GIVEITEM_TC, tc);
        
        BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_GIVEITEM_TC, tc, TableNameUtil.KQDS_GIVEITEM_TC, request);
      }
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getItemsByTcno4Back.act"})
  public String getItemsByTcno4Back(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      List<KqdsGiveitemGiverecord> list = new ArrayList();
      
      KqdsGiveitemTc en = (KqdsGiveitemTc)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_GIVEITEM_TC, seqId);
      String itemnos = en.getItemno();
      String nums = en.getNum();
      if (YZUtility.isNotNullOrEmpty(itemnos))
      {
        itemnos = itemnos.substring(0, itemnos.length() - 1);
        nums = nums.substring(0, nums.length() - 1);
        String[] itemnoss = itemnos.split(",");
        String[] numss = nums.split(",");
        for (int i = 0; i < itemnoss.length; i++)
        {
          KqdsGiveitem itemobj = (KqdsGiveitem)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_GIVEITEM, itemnoss[i]);
          if (itemobj != null) {
            if (itemobj.getUseflag().intValue() != 1)
            {
              KqdsGiveitemGiverecord s = new KqdsGiveitemGiverecord();
              s.setMemberno(itemobj.getSeqId());
              s.setItemno(itemobj.getItemno());
              s.setItemname(itemobj.getItemname());
              s.setUnit(itemobj.getUnit());
              s.setUnitprice(itemobj.getUnitprice());
              s.setZsnum(numss[i]);
              list.add(s);
            }
          }
        }
      }
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
