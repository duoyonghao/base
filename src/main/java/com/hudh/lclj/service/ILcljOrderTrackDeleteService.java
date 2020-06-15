package com.hudh.lclj.service;

import com.hudh.lclj.entity.LcljOrderTrackDeleteRecord;
import javax.servlet.http.HttpServletRequest;

public interface ILcljOrderTrackDeleteService {
  void save(LcljOrderTrackDeleteRecord paramLcljOrderTrackDeleteRecord, HttpServletRequest paramHttpServletRequest) throws Exception;
}
