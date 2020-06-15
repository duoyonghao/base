package com.kqds.controller.base.dict;

import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.entity.base.KqdsTreatitemTcType;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.treatItem.KQDS_TreatItemLogic;
import com.kqds.service.base.treatItemTc.KQDS_TreatItem_TcLogic;
import com.kqds.service.sys.dict.YZDictCostLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.sys.DictUtil;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"YZDictCostAct"})
public class YZDictCostAct
{
  private Logger logger = LoggerFactory.getLogger(YZDictCostAct.class);
  @Autowired
  private YZDictCostLogic dictCostLogic;
  @Autowired
  private KQDS_TreatItemLogic treatItemLogic;
  @Autowired
  private KQDS_TreatItem_TcLogic tcLogic;
  
  @RequestMapping({"/getTreatSortTree.act"})
  public void getTreatSortTree(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    String lv = request.getParameter("lv");
    String search = request.getParameter("search");
    String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
    List<JSONObject> listtree = new ArrayList();
    try
    {
      if (YZUtility.isNullorEmpty(id))
      {
        List<JSONObject> list = this.dictCostLogic.getLeve1SortList4Manager(organization);
        for (int i = 0; i < list.size(); i++)
        {
          JSONObject obj = (JSONObject)list.get(i);
          String basecount = YZUtility.isNullorEmpty(obj.getString("basecount")) ? "0" : obj.getString("basecount");
          obj.put("id", obj.getString("dictCode"));
          obj.put("pId", DictUtil.COSTITEM_SORT);
          obj.put("name", obj.getString("dictName") + "[" + basecount + "]");
          obj.put("nocheck", "true");
          List<YZDict> nextlist = this.dictCostLogic.getLeve2SortList(obj.getString("dictCode"), search, ChainUtil.getCurrentOrganization(request));
          if (nextlist.size() > 0) {
            obj.put("isParent", "true");
          } else {
            obj.put("isParent", "false");
          }
          listtree.add(obj);
        }
      }
      if ((!YZUtility.isNullorEmpty(id)) && (!"1".equals(lv)))
      {
        List<YZDict> nextlist = this.dictCostLogic.getLeve2SortList(id, search, ChainUtil.getCurrentOrganization(request));
        for (int j = 0; j < nextlist.size(); j++)
        {
          YZDict next = (YZDict)nextlist.get(j);
          JSONObject obj = new JSONObject();
          obj.put("id", next.getSeqId());
          obj.put("pId", id);
          obj.put("name", next.getDictName());
          obj.put("nocheck", "true");
          obj.put("isParent", "false");
          listtree.add(obj);
        }
      }
      JSONObject jobj = new JSONObject();
      jobj.put("retData", listtree);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
  }
  
  @RequestMapping({"/getSelectTreeAsync4TcManager.act"})
  public void getSelectTreeAsync4TcManager(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    String lv = request.getParameter("lv");
    String noyjj = request.getParameter("noyjj");
    String search = request.getParameter("search");
    List<JSONObject> listtree = new ArrayList();
    String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
    try
    {
      if (YZUtility.isNullorEmpty(id))
      {
        List<YZDict> list = this.dictCostLogic.getLeve1SortList(search, organization);
        for (int i = 0; i < list.size(); i++)
        {
          YZDict base = (YZDict)list.get(i);
          JSONObject obj = new JSONObject();
          obj.put("id", base.getDictCode());
          obj.put("pId", DictUtil.COSTITEM_SORT);
          obj.put("name", base.getDictName());
          obj.put("nocheck", "true");
          List<YZDict> nextlist = this.dictCostLogic.getLeve2SortList(base.getDictCode(), search, organization);
          if ((nextlist != null) && (nextlist.size() > 0))
          {
            boolean flag = false;
            for (int j = 0; j < nextlist.size(); j++)
            {
              YZDict next = (YZDict)nextlist.get(j);
              List<KqdsTreatitem> itemlist = this.treatItemLogic.getTreatItemListByNextType(next.getSeqId(), search, noyjj);
              if ((itemlist != null) && (itemlist.size() > 0)) {
                flag = true;
              }
            }
            if (flag)
            {
              obj.put("isParent", "true");
              listtree.add(obj);
            }
            else
            {
              obj.put("isParent", "false");
            }
          }
        }
      }
      if ((!YZUtility.isNullorEmpty(id)) && (!"1".equals(lv)))
      {
        List<YZDict> nextlist = this.dictCostLogic.getLeve2SortList(id, search, organization);
        for (int j = 0; j < nextlist.size(); j++)
        {
          YZDict next = (YZDict)nextlist.get(j);
          int count = this.treatItemLogic.getCountByNextType(next.getSeqId(), null);
          JSONObject obj = new JSONObject();
          obj.put("id", next.getSeqId());
          obj.put("pId", id);
          obj.put("name", next.getDictName());
          obj.put("nocheck", "true");
          if (count > 0)
          {
            obj.put("isParent", "true");
            listtree.add(obj);
          }
          else
          {
            obj.put("isParent", "false");
          }
        }
      }
      if ((!YZUtility.isNullorEmpty(id)) && ("1".equals(lv)))
      {
        List<KqdsTreatitem> itemlist = this.treatItemLogic.getTreatItemListByNextType(id, search, noyjj);
        if ((itemlist != null) && (itemlist.size() > 0)) {
          for (int k = 0; k < itemlist.size(); k++)
          {
            JSONObject obj = new JSONObject();
            KqdsTreatitem item = (KqdsTreatitem)itemlist.get(k);
            obj.put("id", item.getTreatitemno());
            obj.put("pId", id);
            obj.put("name", item.getTreatitemname());
            listtree.add(obj);
          }
        }
      }
      JSONObject jobj = new JSONObject();
      jobj.put("retData", listtree);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
  }
  
  @RequestMapping({"/getSelectTreeAsync.act"})
  public void getSelectTreeAsync(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    String lv = request.getParameter("lv");
    String noyjj = request.getParameter("noyjj");
    String search = request.getParameter("search");
    List<JSONObject> listtree = new ArrayList();
    try
    {
      if (YZUtility.isNullorEmpty(id))
      {
        List<YZDict> list = this.dictCostLogic.getLeve1SortList(search, ChainUtil.getCurrentOrganization(request));
        for (int i = 0; i < list.size(); i++)
        {
          YZDict base = (YZDict)list.get(i);
          JSONObject obj = new JSONObject();
          obj.put("id", base.getDictCode());
          obj.put("pId", DictUtil.COSTITEM_SORT);
          obj.put("name", base.getDictName());
          obj.put("nocheck", "true");
          List<YZDict> nextlist = this.dictCostLogic.getLeve2SortList(base.getDictCode(), search, ChainUtil.getCurrentOrganization(request));
          if ((nextlist != null) && (nextlist.size() > 0))
          {
            boolean flag = false;
            for (int j = 0; j < nextlist.size(); j++)
            {
              YZDict next = (YZDict)nextlist.get(j);
              List<KqdsTreatitem> itemlist = this.treatItemLogic.getTreatItemListByNextType(next.getSeqId(), search, noyjj);
              if ((itemlist != null) && (itemlist.size() > 0)) {
                flag = true;
              }
            }
            if (flag)
            {
              obj.put("isParent", "true");
              listtree.add(obj);
            }
            else
            {
              obj.put("isParent", "false");
            }
          }
        }
      }
      if ((!YZUtility.isNullorEmpty(id)) && (!"1".equals(lv)))
      {
        List<YZDict> nextlist = this.dictCostLogic.getLeve2SortList(id, search, ChainUtil.getCurrentOrganization(request));
        for (int j = 0; j < nextlist.size(); j++)
        {
          YZDict next = (YZDict)nextlist.get(j);
          int count = this.treatItemLogic.getCountByNextType(next.getSeqId(), null);
          JSONObject obj = new JSONObject();
          obj.put("id", next.getSeqId());
          obj.put("pId", id);
          obj.put("name", next.getDictName());
          obj.put("nocheck", "true");
          if (count > 0)
          {
            obj.put("isParent", "true");
            listtree.add(obj);
          }
          else
          {
            obj.put("isParent", "false");
          }
        }
      }
      if ((!YZUtility.isNullorEmpty(id)) && ("1".equals(lv)))
      {
        List<KqdsTreatitem> itemlist = this.treatItemLogic.getTreatItemListByNextType(id, search, noyjj);
        if ((itemlist != null) && (itemlist.size() > 0)) {
          for (int k = 0; k < itemlist.size(); k++)
          {
            JSONObject obj = new JSONObject();
            KqdsTreatitem item = (KqdsTreatitem)itemlist.get(k);
            obj.put("id", item.getTreatitemno());
            obj.put("pId", id);
            obj.put("name", item.getTreatitemname());
            listtree.add(obj);
          }
        }
      }
      JSONObject jobj = new JSONObject();
      jobj.put("retData", listtree);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
  }
  
  @RequestMapping({"/getSelectTreeTcAsync.act"})
  public void getSelectTreeTcAsync(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    List<JSONObject> listtree = new ArrayList();
    YZPerson person = SessionUtil.getLoginPerson(request);
    try
    {
      if (YZUtility.isNullorEmpty(id))
      {
        List<KqdsTreatitemTcType> list = this.tcLogic.getTcTypeList(ChainUtil.getCurrentOrganization(request));
        for (int i = 0; i < list.size(); i++)
        {
          KqdsTreatitemTcType base = (KqdsTreatitemTcType)list.get(i);
          int count = this.tcLogic.countTcNameByTcType(base.getSeqId(), person.getSeqId(), ChainUtil.getCurrentOrganization(request));
          JSONObject obj = new JSONObject();
          obj.put("id", base.getSeqId());
          obj.put("name", base.getName());
          obj.put("pId", "0");
          obj.put("nocheck", "true");
          obj.put("isParent", Boolean.valueOf(count > 0));
          if (count > 0)
          {
            obj.put("isParent", Boolean.valueOf(true));
            listtree.add(obj);
          }
          else
          {
            obj.put("isParent", Boolean.valueOf(false));
          }
        }
      }
      else
      {
        List<KqdsTreatitemTcType> nextlist = this.tcLogic.getTcNameListByTcType(id, person.getSeqId(), ChainUtil.getCurrentOrganization(request));
        for (int j = 0; j < nextlist.size(); j++)
        {
          JSONObject obj = new JSONObject();
          KqdsTreatitemTcType next = (KqdsTreatitemTcType)nextlist.get(j);
          obj.put("id", next.getSeqId());
          obj.put("name", next.getName());
          obj.put("pId", id);
          obj.put("isParent", "false");
          listtree.add(obj);
        }
      }
      JSONObject jobj = new JSONObject();
      jobj.put("retData", listtree);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
  }
  
  @RequestMapping({"/getLeve1SortListOrg.act"})
  public void getLeve1SortListOrg(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      List<YZDict> list = this.dictCostLogic.getLeve1SortListOrg(null, ChainUtil.getOrganizationFromUrlCanNull(request));
      JSONObject jobj = new JSONObject();
      jobj.put("list", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
  }
  
  @RequestMapping({"/getLeve2SortListOrg.act"})
  public void getLeve2SortListOrg(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String parentCode = request.getParameter("parentCode");
      if (YZUtility.isNullorEmpty(parentCode)) {
        throw new Exception("父级CODE不能为空");
      }
      List<YZDict> list = this.dictCostLogic.getLeve2SortListOrg(parentCode, null, ChainUtil.getOrganizationFromUrlCanNull(request));
      JSONObject jobj = new JSONObject();
      jobj.put("list", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
  }
  
  @RequestMapping({"/getLeve1SortList.act"})
  public void getLeve1SortList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      List<YZDict> list = this.dictCostLogic.getLeve1SortList(null, ChainUtil.getCurrentOrganization(request));
      JSONObject jobj = new JSONObject();
      jobj.put("list", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
  }
  
  @RequestMapping({"/getLeve2SortList.act"})
  public void getLeve2SortList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String parentCode = request.getParameter("parentCode");
      if (YZUtility.isNullorEmpty(parentCode)) {
        throw new Exception("父级CODE不能为空");
      }
      List<YZDict> list = this.dictCostLogic.getLeve2SortList(parentCode, null, ChainUtil.getCurrentOrganization(request));
      JSONObject jobj = new JSONObject();
      jobj.put("list", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
  }
  
  @RequestMapping({"/getSfxmSelect.act"})
  public void getSfxmSelect(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String basetype = request.getParameter("basetype");
    String nexttype = request.getParameter("nexttype");
    try
    {
      List<JSONObject> list = null;
      if ((basetype != null) && (basetype.equals(""))) {
        list = this.treatItemLogic.getAllsfxmSelect(ChainUtil.getCurrentOrganization(request));
      } else {
        list = this.treatItemLogic.getSfxmSelectParam(basetype, nexttype, ChainUtil.getCurrentOrganization(request));
      }
      JSONObject jobj = new JSONObject();
      jobj.put("list", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
  }
}
