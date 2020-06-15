package com.kqds.controller.base.register;

import com.kqds.entity.sys.YZRegister;
import com.kqds.service.sys.register.YZRegisterLogic;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"KQDS_RegisterAct"})
public class KQDS_RegisterAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_RegisterAct.class);
  @Autowired
  private YZRegisterLogic logic;
  
  @RequestMapping({"/toDetailRegister.act"})
  public ModelAndView toRoomSearch(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/admin/register/list_kqds_register.jsp");
    return mv;
  }
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      YZRegister en = (YZRegister)this.logic.loadObjSingleUUID(TableNameUtil.SYS_REGISTER, seqId);
      if (en == null) {
        throw new Exception("数据不存在");
      }
      JSONObject jobj = new JSONObject();
      jobj.put("data", en);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
