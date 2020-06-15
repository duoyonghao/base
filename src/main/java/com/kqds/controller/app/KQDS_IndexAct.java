package com.kqds.controller.app;

import com.kqds.service.app.KQDS_IndexLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"KQDS_IndexAct"})
public class KQDS_IndexAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_IndexAct.class);
  
  @Autowired
  private KQDS_IndexLogic logic;
  
  @RequestMapping({"/selectCountIndex.act"})
  public String selectCountIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String visualstaff = SessionUtil.getVisualstaff(request);
      JSONObject yearitem = this.logic.getSkMoneyItem(visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), "year");
      BigDecimal yearskitem = new BigDecimal(yearitem.getString("skje"));
      BigDecimal yearskyjj = this.logic.getSkMoneyYjj(visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), "year");
      BigDecimal yearsk = yearskitem.add(yearskyjj);
      yearitem.put("skje", yearsk);
      JSONObject monthitem = this.logic.getSkMoneyItem(visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), "month");
      BigDecimal monthskitem = new BigDecimal(monthitem.getString("skje"));
      BigDecimal monthskyjj = this.logic.getSkMoneyYjj(visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), "month");
      BigDecimal monthsk = monthskitem.add(monthskyjj);
      monthitem.put("skje", monthsk);
      JSONObject dayitem = this.logic.getSkMoneyItem(visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), "day");
      BigDecimal dayskitem = new BigDecimal(dayitem.getString("skje"));
      BigDecimal dayskyjj = this.logic.getSkMoneyYjj(visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), "day");
      BigDecimal daysk = dayskitem.add(dayskyjj);
      dayitem.put("skje", daysk);
      int wdataCount = this.logic.getCountWd(TableNameUtil.KQDS_NET_ORDER, 0, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      int mdataCount = this.logic.getCountMz(TableNameUtil.KQDS_HOSPITAL_ORDER, 0, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      int tdataCountj = this.logic.getCountReg(TableNameUtil.KQDS_REG, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      int vdataCount = this.logic.getCountV(TableNameUtil.KQDS_VISIT, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      int jrtotal = wdataCount + mdataCount + tdataCountj + vdataCount;
      int wdataCountNext = this.logic.getCountWd(TableNameUtil.KQDS_NET_ORDER, -1, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      int mdataCountNext = this.logic.getCountMz(TableNameUtil.KQDS_HOSPITAL_ORDER, -1, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      int mrtotal = wdataCountNext + mdataCountNext;
      BigDecimal qfmoney = this.logic.selectRealQfMoney(visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      JSONObject jobj = new JSONObject();
      jobj.put("year", yearitem);
      jobj.put("month", monthitem);
      jobj.put("day", dayitem);
      jobj.put("jrtotal", Integer.valueOf(tdataCountj));
      jobj.put("mrtotal", Integer.valueOf(mrtotal));
      jobj.put("qfmoney", qfmoney);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
}
