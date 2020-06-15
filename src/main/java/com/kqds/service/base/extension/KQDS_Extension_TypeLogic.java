package com.kqds.service.base.extension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;

public class KQDS_Extension_TypeLogic extends BaseLogic {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(KQDS_Extension_TypeLogic.class);
	@Autowired
	private DaoSupport dao;
}