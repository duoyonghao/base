package com.kqds.controller.app;

import com.kqds.service.app.KQDS_BaobiaoLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
@RequestMapping({"KQDS_BaoBiaoAct"})
public class KQDS_BaoBiaoAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_BaoBiaoAct.class);
  @Autowired
  private KQDS_BaobiaoLogic logic;
  
  @RequestMapping({"/selectCountIndex.act"})
  public String selectCountIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String visualstaff = SessionUtil.getVisualstaff(request);
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime))
      {
        if (starttime.length() == 4) {
          starttime = starttime + "-01-01" + ConstUtil.TIME_START;
        } else if (starttime.length() == 7) {
          starttime = starttime + "-01" + ConstUtil.TIME_START;
        } else {
          starttime = starttime + ConstUtil.TIME_START;
        }
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime))
      {
        if (endtime.length() == 4)
        {
          endtime = endtime + "-12-31" + ConstUtil.TIME_END;
        }
        else if (endtime.length() == 7)
        {
          endtime = endtime + "-01" + ConstUtil.TIME_START;
          Calendar calendar = new GregorianCalendar();
          calendar.setTime(YZUtility.parseDate(endtime));
          calendar.add(2, 1);
          endtime = YZUtility.getDateTimeStr(calendar.getTime());
        }
        else
        {
          endtime = endtime + ConstUtil.TIME_END;
        }
        map.put("endtime", endtime);
      }
      JSONObject yearitem = this.logic.getSkMoneyItem(map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      BigDecimal yearskitem = new BigDecimal(yearitem.getString("skze"));
      BigDecimal yearskyjj = this.logic.getSkMoneyYjj(map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      BigDecimal yearsk = yearskitem.add(yearskyjj);
      yearitem.put("skze", yearsk);
      JSONObject jobj = new JSONObject();
      jobj.put("data", yearitem);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
