package com.hudh.lclj.service;

import com.hudh.lclj.entity.LcljOrderTrack;
import com.hudh.lclj.entity.LcljWorklist;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public interface IFlowOperateService {
  void createFlow(LcljOrderTrack paramLcljOrderTrack, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  void submitFlow(String paramString, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  void rejectFlow(String paramString, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  List<LcljWorklist> findHadWorkList(String paramString, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  LcljWorklist findHadWorkByOrderNumberAndNodeId(Map<String, String> paramMap) throws Exception;
}
