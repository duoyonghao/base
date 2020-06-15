package com.hudh.lclj.service;

import com.hudh.lclj.entity.LcljOrderTrack;
import com.hudh.lclj.entity.LcljWorklist;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface IFlowOperateService
{
  public abstract void createFlow(LcljOrderTrack paramLcljOrderTrack, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract void submitFlow(String paramString, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract void rejectFlow(String paramString, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract List<LcljWorklist> findHadWorkList(String paramString, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract LcljWorklist findHadWorkByOrderNumberAndNodeId(Map<String, String> paramMap)
    throws Exception;
}
