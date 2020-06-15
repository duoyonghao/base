package com.hudh.ykzz.controller;

import com.alibaba.fastjson.JSON;
import com.hudh.ykzz.entity.YkzzType;
import com.hudh.ykzz.service.IYkzzTypeService;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/HUDH_YkzzTypeAct"})
public class HUDH_YkzzTypeAct
{
  private Logger logger = LoggerFactory.getLogger(HUDH_YkzzTypeAct.class);
  private static final String TYPEROOTID = "root";
  private static final String TYPEROOTNAME = "根节点";
  @Autowired
  private IYkzzTypeService ykzzTypeService;
  
  @RequestMapping({"/insertYkzzType.act"})
  public String insertYkzzType(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String typeName = request.getParameter("typeName");
    String orderno = request.getParameter("orderno");
    String parentid = request.getParameter("parentid");
    YkzzType ykzzType = new YkzzType();
    ykzzType.setOrderno(orderno);
    ykzzType.setParentid(parentid);
    ykzzType.setTypeName(typeName);
    try
    {
      this.ykzzTypeService.insertYkzzType(ykzzType, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/findYkzzTypeById.act"})
  public String findYkzzTypeById(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    try
    {
      if (YZUtility.isNotNullOrEmpty(id))
      {
        YkzzType ykzzType = this.ykzzTypeService.findYkzzTypeById(id);
        JSONObject jo = new JSONObject();
        jo.put("ykzzType", JSON.toJSONString(ykzzType));
        YZUtility.DEAL_SUCCESS(jo, null, response, this.logger);
      }
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/deleteYkzzTypeById.act"})
  public String deleteYkzzTypeById(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    try
    {
      if (YZUtility.isNotNullOrEmpty(id))
      {
        this.ykzzTypeService.deleteYkzzTypeById(id);
        YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
      }
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/updateYkzzTypeById.act"})
  public String updateYkzzTypeById(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    String typeName = request.getParameter("typeName");
    String orderno = request.getParameter("orderno");
    YkzzType ykzzType = new YkzzType();
    ykzzType.setId(id);
    ykzzType.setOrderno(orderno);
    ykzzType.setTypeName(typeName);
    try
    {
      if ((YZUtility.isNotNullOrEmpty(id)) && (YZUtility.isNotNullOrEmpty(typeName)) && 
        (YZUtility.isNotNullOrEmpty(orderno)))
      {
        this.ykzzTypeService.updateYkzzTypeById(ykzzType);
        YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
      }
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/findChildTypesByParentId.act"})
  public void findChildTypesByParentId(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    String organization = ChainUtil.getCurrentOrganization(request);
    Map<String, String> map = new HashMap();
    map.put("organization", organization);
    map.put("id", id);
    try
    {
      if (YZUtility.isNotNullOrEmpty(id))
      {
        List<JSONObject> list = this.ykzzTypeService.findChildTypesByParentId(map);
        YZUtility.RETURN_LIST(list, response, this.logger);
      }
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
  }
  
  @RequestMapping({"/findAllTypesByParentId.act"})
  public void findAllTypesByParentId(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = ChainUtil.getCurrentOrganization(request);
    try
    {
      List<JSONObject> list = this.ykzzTypeService.findAllTypes(organization);
      YZUtility.RETURN_LIST(list, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
  }
  
  @RequestMapping({"/reBack.act"})
  public String reBack(HttpServletRequest request, HttpServletResponse response)
  {
    String id = request.getParameter("id");
    try
    {
      if (YZUtility.isNotNullOrEmpty(id))
      {
        YkzzType ykzzType = this.ykzzTypeService.findYkzzTypeById(id);
        JSONObject jo = new JSONObject();
        String perId = "root";
        String perName = "根节点";
        if (ykzzType != null) {
          perId = ykzzType.getParentid();
        }
        jo.put("perId", perId);
        ykzzType = this.ykzzTypeService.findYkzzTypeById(perId);
        if (ykzzType != null) {
          perName = ykzzType.getTypeName();
        }
        jo.put("perName", perName);
        YZUtility.RETURN_OBJ(jo, response, this.logger);
      }
    }
    catch (Exception localException) {}
    return null;
  }
  
  @RequestMapping({"/findzTreeList.act"})
  public void findzTreeList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = ChainUtil.getCurrentOrganization(request);
    try
    {
      List<JSONObject> list = this.ykzzTypeService.findAllTypes(organization);
      JSONArray jsonArray = new JSONArray();
      if ((list != null) && (list.size() > 0))
      {
        JSONObject jo = null;
        for (JSONObject obj : list) {
          if (!obj.getString("id").equals("root"))
          {
            jo = new JSONObject();
            jo.put("id", obj.getString("id"));
            jo.put("pId", obj.getString("parentid").equals("root") ? "0" : obj.getString("parentid"));
            jo.put("name", obj.getString("type_name"));
            jsonArray.add(jo);
          }
        }
      }
      YZUtility.RETURN_LIST(jsonArray, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
  }
  
  @RequestMapping({"/findTypeByLevel.act"})
  public void findTypeByLevel(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String level = request.getParameter("level");
    String organization = ChainUtil.getCurrentOrganization(request);
    try
    {
      List<JSONObject> list = this.ykzzTypeService.findAllTypes(organization);
      List<JSONObject> levelList = null;
      if (level.equals("1"))
      {
        levelList = new ArrayList();
        for (JSONObject obj : list) {
          if ("root".equals(obj.get("parentid"))) {
            levelList.add(obj);
          }
        }
        YZUtility.RETURN_LIST(levelList, response, this.logger);
      }
      else if (level.equals("2"))
      {
        levelList = new ArrayList();
        for (JSONObject obj : list) {
          if ((!"root".equals(obj.get("parentid"))) && (!"0".equals(obj.get("parentid")))) {
            levelList.add(obj);
          }
        }
        YZUtility.RETURN_LIST(levelList, response, this.logger);
      }
      else
      {
        YZUtility.RETURN_LIST(list, response, this.logger);
      }
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
  }
}
