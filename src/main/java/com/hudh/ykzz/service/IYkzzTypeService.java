package com.hudh.ykzz.service;

import com.hudh.ykzz.entity.YkzzType;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public abstract interface IYkzzTypeService
{
  public abstract void insertYkzzType(YkzzType paramYkzzType, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract YkzzType findYkzzTypeById(String paramString)
    throws Exception;
  
  public abstract void deleteYkzzTypeById(String paramString)
    throws Exception;
  
  public abstract void updateYkzzTypeById(YkzzType paramYkzzType)
    throws Exception;
  
  public abstract List<JSONObject> findChildTypesByParentId(Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> findAllTypes(String paramString)
    throws Exception;
}
