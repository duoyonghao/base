package com.kqds.service.base.medicalRecordDetail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;

@Service
public class KQDS_MedicalRecord_DetailLogic extends BaseLogic {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(KQDS_MedicalRecord_DetailLogic.class);
	@Autowired
	private DaoSupport dao;
}