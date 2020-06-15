package com.hudh.zzbl.service;

import com.hudh.zzbl.entity.ZzblOperation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public abstract interface IZzblService
{
  public abstract void save(ZzblOperation paramZzblOperation, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract List<ZzblOperation> findZzblOprationById(String paramString)
    throws Exception;
  
  public abstract JSONObject selectZzblOperationById(String paramString)
    throws Exception;
  
  public abstract void updateZzblOprationById(ZzblOperation paramZzblOperation, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract void deleteZzblInforById(String paramString)
    throws Exception;
  
  public abstract List<JSONObject> findAllZzblInfor()
    throws Exception;
}
