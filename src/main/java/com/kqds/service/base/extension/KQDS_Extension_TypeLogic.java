package com.kqds.service.base.extension;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class KQDS_Extension_TypeLogic extends BaseLogic {
  private Logger log = LoggerFactory.getLogger(KQDS_Extension_TypeLogic.class);
  
  @Autowired
  private DaoSupport dao;
}
