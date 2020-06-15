package com.hudh.zzbl.service;

import com.hudh.zzbl.entity.ZzblPlantTeethOperation;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public interface IZzblPlantTeethOperationService {
  void insertZzblPlantTeethOperation(ZzblPlantTeethOperation paramZzblPlantTeethOperation, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  JSONObject findZzblPlantTeethOperationById(String paramString) throws Exception;
}
