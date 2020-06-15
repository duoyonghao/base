package com.hudh.ykzz.service;

import com.hudh.ykzz.entity.YkzzManu;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public interface IYkzzManuService {
  void insertYkzzManu(YkzzManu paramYkzzManu, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  YkzzManu findYkzzManuById(String paramString) throws Exception;
  
  void deleteYkzzManuById(String paramString) throws Exception;
  
  void updateYkzzManuById(YkzzManu paramYkzzManu) throws Exception;
  
  List<JSONObject> findAllManu(String paramString) throws Exception;
  
  JSONObject findManuByCode(String paramString) throws Exception;
}
