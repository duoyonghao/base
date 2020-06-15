package com.kqds.controller.sys.cache;

import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.YZUtility;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"YZCacheAct"})
public class YZCacheAct {
  private Logger logger = LoggerFactory.getLogger(YZCacheAct.class);
  
  @Autowired
  private YZPersonLogic personLogic;
  
  @RequestMapping({"/loginCache.act"})
  public String loginCache(HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(false);
    List<JSONObject> treeList = new ArrayList<>();
    try {
      long startTime = System.currentTimeMillis();
      treeList = this.personLogic.getDeptNodeList("0", treeList, null);
      long endTime = System.currentTimeMillis();
      this.logger.error("程序运行时间： " + (endTime - startTime) + "ms ### Login Cache ### SUCCESS");
      session.setAttribute("PERSON_TREE_DATA", treeList);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
}
