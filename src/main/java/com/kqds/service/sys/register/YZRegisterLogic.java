package com.kqds.service.sys.register;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YZRegisterLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
}
