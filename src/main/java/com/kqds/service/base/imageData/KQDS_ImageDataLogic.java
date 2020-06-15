package com.kqds.service.base.imageData;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_ImageDataLogic extends BaseLogic {
  private Logger log = LoggerFactory.getLogger(KQDS_ImageDataLogic.class);
  
  @Autowired
  private DaoSupport dao;
}
