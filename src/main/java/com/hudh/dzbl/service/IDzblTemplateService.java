package com.hudh.dzbl.service;

import com.hudh.dzbl.entity.DzblDetail;
import com.hudh.dzbl.entity.DzblTemplate;
import com.kqds.entity.sys.BootStrapPage;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public interface IDzblTemplateService {
  void insertDzblTemplate(DzblTemplate paramDzblTemplate) throws Exception;
  
  JSONObject findTemplateByBlflAndBc(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  List<JSONObject> findBcByBlfl(String paramString1, String paramString2) throws Exception;
  
  void insertDzblDetail(DzblDetail paramDzblDetail, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  List<JSONObject> findDzblByBlcode(String paramString) throws Exception;
  
  void updateDzblById(DzblDetail paramDzblDetail, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  String dzblPrint(HttpServletRequest paramHttpServletRequest, String paramString1, String paramString2, String paramString3) throws Exception;
  
  JSONObject findUserDocByBlCode(String paramString) throws Exception;
  
  JSONObject findDzblById(String paramString) throws Exception;
  
  JSONObject findDzblTemplateById(String paramString) throws Exception;
  
  void updateDzblTempleById(DzblTemplate paramDzblTemplate) throws Exception;
  
  String selectParaValueByName(HttpServletRequest paramHttpServletRequest) throws Exception;
  
  JSONObject findDzbl(Map<String, String> paramMap, BootStrapPage paramBootStrapPage) throws Exception;
}
