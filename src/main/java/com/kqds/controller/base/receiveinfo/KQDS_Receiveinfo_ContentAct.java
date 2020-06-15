package com.kqds.controller.base.receiveinfo;

import com.kqds.entity.base.KqdsReceiveinfoContent;
import com.kqds.service.base.receiveinfo.KQDS_ReceiveInfo_ContentLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"KQDS_Receiveinfo_ContentAct"})
public class KQDS_Receiveinfo_ContentAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_Receiveinfo_ContentAct.class);
  
  @Autowired
  private KQDS_ReceiveInfo_ContentLogic logic;
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String receiveinfoId = request.getParameter("receiveinfoId");
      Map<String, String> map = new HashMap<>();
      map.put("receiveid", receiveinfoId);
      List<KqdsReceiveinfoContent> en = (List<KqdsReceiveinfoContent>)this.logic.loadList(TableNameUtil.KQDS_RECEIVEINFO_CONTENT, map);
      JSONObject jobj = new JSONObject();
      jobj.put("retState", "0");
      jobj.put("retMsrg", "操作成功");
      if (en.size() > 0) {
        jobj.put("data", en.get(0));
      } else {
        jobj.put("data", new KqdsReceiveinfoContent());
      } 
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
}
