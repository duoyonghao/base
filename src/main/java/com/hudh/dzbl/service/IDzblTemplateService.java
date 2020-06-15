package com.hudh.dzbl.service;

import com.hudh.dzbl.entity.DzblDetail;
import com.hudh.dzbl.entity.DzblTemplate;
import com.kqds.entity.sys.BootStrapPage;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public abstract interface IDzblTemplateService
{
  public abstract void insertDzblTemplate(DzblTemplate paramDzblTemplate)
    throws Exception;
  
  public abstract JSONObject findTemplateByBlflAndBc(String paramString1, String paramString2, String paramString3, String paramString4)
    throws Exception;
  
  public abstract List<JSONObject> findBcByBlfl(String paramString1, String paramString2)
    throws Exception;
  
  public abstract void insertDzblDetail(DzblDetail paramDzblDetail, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract List<JSONObject> findDzblByBlcode(String paramString)
    throws Exception;
  
  public abstract void updateDzblById(DzblDetail paramDzblDetail, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract String dzblPrint(HttpServletRequest paramHttpServletRequest, String paramString1, String paramString2, String paramString3)
    throws Exception;
  
  public abstract JSONObject findUserDocByBlCode(String paramString)
    throws Exception;
  
  public abstract JSONObject findDzblById(String paramString)
    throws Exception;
  
  public abstract JSONObject findDzblTemplateById(String paramString)
    throws Exception;
  
  public abstract void updateDzblTempleById(DzblTemplate paramDzblTemplate)
    throws Exception;
  
  public abstract String selectParaValueByName(HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract JSONObject findDzbl(Map<String, String> paramMap, BootStrapPage paramBootStrapPage)
    throws Exception;
}
