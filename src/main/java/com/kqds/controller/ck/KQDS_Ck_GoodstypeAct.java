package com.kqds.controller.ck;

import com.kqds.entity.base.KqdsCkGoodsType;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.ck.KQDS_Ck_GoodstypeLogic;
import com.kqds.service.ck.KQDS_Ck_HouseLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.ChineseCharToEn;
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
@RequestMapping({"KQDS_Ck_GoodstypeAct"})
public class KQDS_Ck_GoodstypeAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_Ck_GoodstypeAct.class);
  @Autowired
  private KQDS_Ck_GoodstypeLogic logic;
  @Autowired
  private KQDS_Ck_HouseLogic hlogic;
  
  @RequestMapping({"/toSave.act"})
  public ModelAndView toSave(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    String perid = request.getParameter("perid");
    String pername = request.getParameter("pername");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("perid", perid);
    mv.addObject("pername", pername);
    mv.setViewName("/kqdsFront/ck/goodsType/save_goodstype.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsCkGoodsType dp = new KqdsCkGoodsType();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (!YZUtility.isNullorEmpty(seqId))
      {
        this.logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS_TYPE, dp);
        
        BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_CK_GOODS_TYPE, dp, TableNameUtil.KQDS_CK_GOODS_TYPE, request);
      }
      else
      {
        dp.setSeqId(YZUtility.getUUID());
        
        String typeno = ChineseCharToEn.getAllFirstLetter_RandNum(dp.getGoodstype());
        dp.setTypeno(typeno);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_TYPE, dp);
        
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_GOODS_TYPE, dp, TableNameUtil.KQDS_CK_GOODS_TYPE, request);
      }
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/delete.act"})
  public String delete(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("主键为空或者null");
      }
      Map<String, String> map = new HashMap();
      map.put("seqId", seqId);
      this.logic.deleteType(map);
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
      
      KqdsCkGoodsType en = (KqdsCkGoodsType)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_TYPE, seqId);
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
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      List<JSONObject> list = this.logic.selectList(TableNameUtil.KQDS_CK_GOODS_TYPE, map);
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getBaseTypeSelect.act"})
  public String getBaseTypeSelect(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      Map<String, String> map = new HashMap();
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      map.put("perid", request.getParameter("sshouseid"));
      List<JSONObject> list = this.logic.getBaseTypeSelect(TableNameUtil.KQDS_CK_GOODS_TYPE, map);
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getNextTypeSelect.act"})
  public String getNextTypeSelect(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      Map<String, String> map = new HashMap();
      String baseid = request.getParameter("baseid");
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      if (YZUtility.isNullorEmpty(baseid)) {
        throw new Exception("父级CODE不能为空");
      }
      map.put("perid", baseid);
      
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      List<JSONObject> list = this.logic.getBNextTypeSelect(TableNameUtil.KQDS_CK_GOODS_TYPE, map);
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/YzCode.act"})
  public String YzCode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      String perid = request.getParameter("perid");
      String typename = request.getParameter("typename");
      
      boolean flag = true;
      
      Map<String, String> map = new HashMap();
      map.put("goodstype", typename);
      map.put("perid", perid);
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      List<KqdsCkGoodsType> en = (List)this.logic.loadList(TableNameUtil.KQDS_CK_GOODS_TYPE, map);
      if (!YZUtility.isNullorEmpty(seqId)) {
        for (KqdsCkGoodsType sup : en) {
          if (!sup.getSeqId().equals(seqId)) {
            flag = false;
          }
        }
      } else if ((en != null) && (en.size() > 0)) {
        flag = false;
      }
      JSONObject jobj = new JSONObject();
      jobj.put("data", Boolean.valueOf(flag));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getSelectTreeAsync.act"})
  public void getSelectTreeAsync(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    String lv = request.getParameter("lv");
    String search = request.getParameter("search");
    List<JSONObject> listtree = new ArrayList();
    
    Map<String, String> map = new HashMap();
    Map<String, String> map2 = new HashMap();
    
    String menzhen = ChainUtil.getCurrentOrganization(request);
    map.put("organization", menzhen);
    map.put("type", request.getParameter("type"));
    map2.put("organization", menzhen);
    try
    {
      if (YZUtility.isNullorEmpty(id))
      {
        List<JSONObject> list = this.hlogic.selectList(map);
        if ((list != null) && (list.size() > 0)) {
          for (int i = 0; i < list.size(); i++)
          {
            JSONObject obj = new JSONObject();
            JSONObject base = (JSONObject)list.get(i);
            obj.put("id", base.getString("seq_id"));
            obj.put("name", base.getString("housename"));
            obj.put("open", "true");
            map2.put("perid", id);
            map2.put("organization", menzhen);
            List<JSONObject> list2 = this.logic.selectList(TableNameUtil.KQDS_CK_GOODS_TYPE, map2);
            if ((list2 != null) && (list2.size() > 0)) {
              obj.put("isParent", "true");
            } else {
              obj.put("isParent", "false");
            }
            listtree.add(obj);
          }
        }
      }
      else if ("1".equals(lv))
      {
        map.put("perid", id);
        map.put("organization", menzhen);
        map.put("search", search);
        List<JSONObject> list = this.logic.selectList(TableNameUtil.KQDS_CK_GOODS_TYPE, map);
        if ((list != null) && (list.size() > 0)) {
          for (int k = 0; k < list.size(); k++)
          {
            JSONObject obj = new JSONObject();
            JSONObject item = (JSONObject)list.get(k);
            obj.put("id", item.getString("seq_id"));
            obj.put("pId", id);
            obj.put("name", item.getString("goodstype"));
            listtree.add(obj);
          }
        }
      }
      else
      {
        map.put("perid", id);
        map.put("organization", menzhen);
        List<JSONObject> nextlist = this.logic.selectList(TableNameUtil.KQDS_CK_GOODS_TYPE, map);
        if ((nextlist != null) && (nextlist.size() > 0)) {
          for (int j = 0; j < nextlist.size(); j++)
          {
            JSONObject next = (JSONObject)nextlist.get(j);
            JSONObject obj = new JSONObject();
            obj.put("id", next.getString("seq_id"));
            obj.put("pId", "0");
            obj.put("name", next.getString("goodstype"));
            obj.put("nocheck", "true");
            map.put("perid", next.getString("perid"));
            map.put("organization", menzhen);
            map.put("search", search);
            List<JSONObject> itemlist = this.logic.selectList(TableNameUtil.KQDS_CK_GOODS_TYPE, map);
            if ((itemlist != null) && (itemlist.size() > 0)) {
              obj.put("isParent", "true");
            } else {
              obj.put("isParent", "false");
            }
            listtree.add(obj);
          }
        }
      }
      JSONObject jobj = new JSONObject();
      jobj.put("retData", listtree);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
  }
  
  @RequestMapping({"/getSelectTreeNohouseAsync.act"})
  public void getSelectTreeNohouseAsync(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    List<JSONObject> listtree = new ArrayList();
    Map<String, String> map = new HashMap();
    Map<String, String> map2 = new HashMap();
    
    String menzhen = ChainUtil.getCurrentOrganization(request);
    map.put("organization", menzhen);
    map2.put("organization", menzhen);
    try
    {
      if (YZUtility.isNullorEmpty(id))
      {
        map.put("perid", id);
        List<JSONObject> list = this.logic.selectList(TableNameUtil.KQDS_CK_GOODS_TYPE, map);
        if ((list != null) && (list.size() > 0)) {
          for (int i = 0; i < list.size(); i++)
          {
            JSONObject obj = new JSONObject();
            JSONObject base = (JSONObject)list.get(i);
            obj.put("id", base.getString("seq_id"));
            obj.put("pId", "0");
            obj.put("name", base.getString("goodstype"));
            obj.put("open", "true");
            map2.put("perid", base.getString("perid"));
            List<JSONObject> list2 = this.logic.selectList(TableNameUtil.KQDS_CK_GOODS_TYPE, map2);
            if ((list2 != null) && (list2.size() > 0)) {
              obj.put("isParent", "true");
            } else {
              obj.put("isParent", "false");
            }
            listtree.add(obj);
          }
        }
      }
      else
      {
        map.put("perid", id);
        List<JSONObject> list = this.logic.selectList(TableNameUtil.KQDS_CK_GOODS_TYPE, map);
        if ((list != null) && (list.size() > 0)) {
          for (int k = 0; k < list.size(); k++)
          {
            JSONObject obj = new JSONObject();
            JSONObject item = (JSONObject)list.get(k);
            obj.put("id", item.getString("seq_id"));
            obj.put("pId", id);
            obj.put("name", item.getString("goodstype"));
            listtree.add(obj);
          }
        }
      }
      JSONObject jobj = new JSONObject();
      jobj.put("retData", listtree);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
  }
}
