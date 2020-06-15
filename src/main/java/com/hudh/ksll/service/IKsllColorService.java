package com.hudh.ksll.service;

import com.hudh.ksll.entity.KsllCollor;
import com.kqds.entity.base.KqdsCkHouse;
import com.kqds.entity.sys.BootStrapPage;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

public interface IKsllColorService {
  List<JSONObject> findAllCKDept(Map<String, String> paramMap) throws Exception;
  
  void saveKsllData(KsllCollor paramKsllCollor, String paramString, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  List<JSONObject> findAllKsllColor(Map<String, String> paramMap) throws Exception;
  
  JSONObject findKsllColorAndDetails(String paramString) throws Exception;
  
  JSONObject findKsllColorAndDetail(String paramString) throws Exception;
  
  List<KqdsCkHouse> findAllCKHouse(Map<String, String> paramMap) throws Exception;
  
  void ksllOutGoods(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception;
  
  List<JSONObject> findKsllColorDetailByparentid(String paramString) throws Exception;
  
  void updateNumsById(Map<String, String> paramMap) throws Exception;
  
  void deleteKsllNotCK(String paramString) throws Exception;
  
  Map<String, JSONObject> findAllCKGood() throws Exception;
  
  String findCKGoodSshouse(String paramString1, String paramString2) throws Exception;
  
  List<JSONObject> findAllKsllColorGoods(Map<String, String> paramMap) throws Exception;
  
  JSONObject findAllKsllColorGoods(Map<String, String> paramMap, BootStrapPage paramBootStrapPage) throws Exception;
  
  Map<String, JSONObject> findAllCkGoodsByGoodscode(String paramString1, String paramString2) throws Exception;
  
  List<JSONObject> selectAllGoodPhByGoodCode(Map<String, String> paramMap) throws Exception;
  
  Map<String, JSONObject> findAllKsllColorGoodsByGoodscode(Map<String, String> paramMap) throws Exception;
}
