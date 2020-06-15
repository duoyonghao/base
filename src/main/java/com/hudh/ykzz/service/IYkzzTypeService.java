package com.hudh.ykzz.service;

import com.hudh.ykzz.entity.YkzzType;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public interface IYkzzTypeService {
  void insertYkzzType(YkzzType paramYkzzType, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  YkzzType findYkzzTypeById(String paramString) throws Exception;
  
  void deleteYkzzTypeById(String paramString) throws Exception;
  
  void updateYkzzTypeById(YkzzType paramYkzzType) throws Exception;
  
  List<JSONObject> findChildTypesByParentId(Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> findAllTypes(String paramString) throws Exception;
}
