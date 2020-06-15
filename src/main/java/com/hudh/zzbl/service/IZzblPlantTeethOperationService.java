package com.hudh.zzbl.service;

import com.hudh.zzbl.entity.ZzblPlantTeethOperation;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public abstract interface IZzblPlantTeethOperationService
{
  public abstract void insertZzblPlantTeethOperation(ZzblPlantTeethOperation paramZzblPlantTeethOperation, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract JSONObject findZzblPlantTeethOperationById(String paramString)
    throws Exception;
}
