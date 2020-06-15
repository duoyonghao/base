package com.hudh.zzbl.service;

import com.hudh.zzbl.entity.ZzblOperation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public interface IZzblService {
  void save(ZzblOperation paramZzblOperation, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  List<ZzblOperation> findZzblOprationById(String paramString) throws Exception;
  
  JSONObject selectZzblOperationById(String paramString) throws Exception;
  
  void updateZzblOprationById(ZzblOperation paramZzblOperation, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  void deleteZzblInforById(String paramString) throws Exception;
  
  List<JSONObject> findAllZzblInfor() throws Exception;
}
