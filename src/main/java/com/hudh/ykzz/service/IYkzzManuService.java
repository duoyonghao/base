package com.hudh.ykzz.service;

import com.hudh.ykzz.entity.YkzzManu;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public abstract interface IYkzzManuService
{
  public abstract void insertYkzzManu(YkzzManu paramYkzzManu, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract YkzzManu findYkzzManuById(String paramString)
    throws Exception;
  
  public abstract void deleteYkzzManuById(String paramString)
    throws Exception;
  
  public abstract void updateYkzzManuById(YkzzManu paramYkzzManu)
    throws Exception;
  
  public abstract List<JSONObject> findAllManu(String paramString)
    throws Exception;
  
  public abstract JSONObject findManuByCode(String paramString)
    throws Exception;
}
