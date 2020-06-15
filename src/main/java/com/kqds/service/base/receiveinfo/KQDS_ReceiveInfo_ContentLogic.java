package com.kqds.service.base.receiveinfo;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_ReceiveInfo_ContentLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
}
