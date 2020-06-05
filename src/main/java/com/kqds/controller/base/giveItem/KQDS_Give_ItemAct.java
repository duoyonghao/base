package com.kqds.controller.base.giveItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.service.base.giveItem.KQDS_Give_ItemLogic;

@SuppressWarnings("unused")
@Controller
@RequestMapping("KQDS_Give_ItemAct")
public class KQDS_Give_ItemAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_Give_ItemAct.class);
	@Autowired
	private KQDS_Give_ItemLogic logic;

}