package com.kqds.service.base.receiveinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;

@Service
public class KQDS_ReceiveInfo_ContentLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;
}