package com.kqds.controller.sys.register;

import com.kqds.service.sys.register.YZRegisterLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"YZRegisterAct"})
public class YZRegisterAct
{
  private Logger logger = LoggerFactory.getLogger(YZRegisterAct.class);
  private YZRegisterLogic logic = new YZRegisterLogic();
}
