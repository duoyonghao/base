package com.kqds.controller.base.outProcessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.service.base.outProcessing.KQDS_OutProcessing_TypeLogic;

@SuppressWarnings("unused")
@Controller
@RequestMapping("KQDS_OutProcessing_TypeAct")
public class KQDS_OutProcessing_TypeAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_OutProcessing_TypeAct.class);
	@Autowired
	private KQDS_OutProcessing_TypeLogic logic;

}