package com.hudh.doctorpick.service;

import com.hudh.doctorpick.entity.GoodsPickSendBack;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public interface IGoodsPickSendBackService {
  int insertGoodsPickSendBack(GoodsPickSendBack paramGoodsPickSendBack, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  List<JSONObject> findGoodsPickSendBackAll(Map<String, String> paramMap) throws Exception;
  
  JSONObject findGoodsPickSendBackById(String paramString) throws Exception;
}
