package com.kqds.controller.wx.socket;

import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.base.websocket.WeChat_Talk_Window;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"WXSocketAct"})
public class WXSocketAct {
  private static Logger logger = LoggerFactory.getLogger(WXSocketAct.class);
  
  @Autowired
  private YZPersonLogic personLogic;
  
  @RequestMapping({"/check4Chat.act"})
  public String check4Chat(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String openid = request.getParameter("openid");
      String userseqid = (String)WeChat_Talk_Window.WeChat_Talk_UserSeqIdList.get(openid);
      JSONObject result = new JSONObject();
      YZPerson person = null;
      if (!YZUtility.isNullorEmpty(userseqid)) {
        person = (YZPerson)this.personLogic.loadObjSingleUUID(TableNameUtil.SYS_PERSON, userseqid);
        result.put("userid", person.getUserId());
      } 
      YZUtility.DEAL_SUCCESS(result, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
}
