package com.kqds.controller.ck;

import com.kqds.entity.base.KqdsCkHouse;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.ck.KQDS_Ck_HouseLogic;
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
@RequestMapping({"KQDS_Ck_HouseAct"})
public class KQDS_Ck_HouseAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_Ck_HouseAct.class);
  @Autowired
  private KQDS_Ck_HouseLogic logic;
  
  @RequestMapping({"/toGoodsHouse.act"})
  public ModelAndView toGoodsHouse(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String sshouse = request.getParameter("sshouse");
    String type = request.getParameter("type");
    String menuid = request.getParameter("menuid");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuid);
    mv.addObject("sshouse", sshouse);
    mv.addObject("type", type);
    mv.setViewName("/kqdsFront/ck/goodsIn/goods_house.jsp");
    return mv;
  }
  
  @RequestMapping({"/toSave.act"})
  public ModelAndView toSave(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/ck/house/save_house.jsp");
    return mv;
  }
  
  @RequestMapping({"/toCkHouse.act"})
  public ModelAndView toCkHouse(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/ck/house/ck_house.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsCkHouse dp = new KqdsCkHouse();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (!YZUtility.isNullorEmpty(seqId))
      {
        this.logic.updateSingleUUID(TableNameUtil.KQDS_CK_HOUSE, dp);
        
        BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_CK_HOUSE, dp, TableNameUtil.KQDS_CK_HOUSE, request);
      }
      else
      {
        String uuid = YZUtility.getUUID();
        dp.setSeqId(uuid);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_CK_HOUSE, dp);
        
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_HOUSE, dp, TableNameUtil.KQDS_CK_HOUSE, request);
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
      KqdsCkHouse en = (KqdsCkHouse)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_HOUSE, seqId);
      this.logic.deleteSingleUUID(TableNameUtil.KQDS_CK_HOUSE, seqId);
      
      BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_CK_HOUSE, en, TableNameUtil.KQDS_CK_HOUSE, request);
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
      KqdsCkHouse en = (KqdsCkHouse)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_HOUSE, seqId);
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
      Map<String, String> map = new HashMap();
      map.put("type", request.getParameter("type"));
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      List<JSONObject> list = this.logic.selectList(map);
      
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/emptyCK.act"})
  public String emptyCK(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      this.logic.emptyCK();
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
}
