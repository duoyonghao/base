package com.hudh.zzbl.service;

import com.hudh.zzbl.entity.ZzblCheck;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public interface IZzblCheckService {
  void insertZzblCheck(ZzblCheck paramZzblCheck, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  List<JSONObject> findZzblOprationById(String paramString) throws Exception;
  
  ZzblCheck selectZzblCheckById(String paramString) throws Exception;
  
  void updateZzblOprationById(ZzblCheck paramZzblCheck, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  void deleteZzblInforById(String paramString) throws Exception;
  
  List<JSONObject> findAllZzblInfor() throws Exception;
}
