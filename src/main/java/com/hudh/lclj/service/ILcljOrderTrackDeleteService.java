package com.hudh.lclj.service;

import com.hudh.lclj.entity.LcljOrderTrackDeleteRecord;
import javax.servlet.http.HttpServletRequest;

public abstract interface ILcljOrderTrackDeleteService
{
  public abstract void save(LcljOrderTrackDeleteRecord paramLcljOrderTrackDeleteRecord, HttpServletRequest paramHttpServletRequest)
    throws Exception;
}
