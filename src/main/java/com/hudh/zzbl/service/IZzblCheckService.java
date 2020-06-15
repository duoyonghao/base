package com.hudh.zzbl.service;

import com.hudh.zzbl.entity.ZzblCheck;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public abstract interface IZzblCheckService
{
  public abstract void insertZzblCheck(ZzblCheck paramZzblCheck, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract JSONObject findZzblOprationById(String paramString)
    throws Exception;
  
  public abstract ZzblCheck selectZzblCheckById(String paramString)
    throws Exception;
  
  public abstract void updateZzblOprationById(ZzblCheck paramZzblCheck, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract void deleteZzblInforById(String paramString)
    throws Exception;
  
  public abstract List<JSONObject> findAllZzblInfor()
    throws Exception;
}
