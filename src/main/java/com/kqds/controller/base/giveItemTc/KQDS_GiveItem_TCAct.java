package com.kqds.controller.base.giveItemTc;

import com.kqds.entity.base.KqdsGiveitem;
import com.kqds.entity.base.KqdsGiveitemGiverecord;
import com.kqds.entity.base.KqdsGiveitemTc;
import com.kqds.service.base.giveItemTc.KQDS_GiveItem_TCLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"KQDS_GiveItem_TCAct"})
public class KQDS_GiveItem_TCAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_GiveItem_TCAct.class);
  
  @Autowired
  private KQDS_GiveItem_TCLogic logic;
  
  @RequestMapping({"/getItemsByTcno.act"})
  public String getItemsByTcno(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      List<KqdsGiveitemGiverecord> list = new ArrayList<>();
      KqdsGiveitemTc en = (KqdsGiveitemTc)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_GIVEITEM_TC, seqId);
      String itemnos = en.getItemno();
      String nums = (new StringBuilder(String.valueOf(en.getNum()))).toString();
      if (YZUtility.isNotNullOrEmpty(itemnos)) {
        itemnos = itemnos.substring(0, itemnos.length() - 1);
        nums = nums.substring(0, nums.length() - 1);
        String[] itemnoss = itemnos.split(",");
        String[] numss = nums.split(",");
        for (int i = 0; i < itemnoss.length; i++) {
          KqdsGiveitem itemobj = (KqdsGiveitem)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_GIVEITEM, itemnoss[i]);
          if (itemobj.getUseflag().intValue() != 1) {
            KqdsGiveitemGiverecord s = new KqdsGiveitemGiverecord();
            s.setMemberno(itemobj.getSeqId());
            s.setItemno(itemobj.getItemno());
            s.setItemname(itemobj.getItemname());
            s.setUnit(itemobj.getUnit());
            s.setUnitprice(itemobj.getUnitprice());
            s.setZsnum(numss[i]);
            list.add(s);
          } 
        } 
      } 
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getSelectTreeTcAsync.act"})
  public String getSelectTreeTcAsync(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    List<JSONObject> listtree = new ArrayList<>();
    String organization = ChainUtil.getCurrentOrganization(request);
    try {
      if (YZUtility.isNullorEmpty(id)) {
        List<KqdsGiveitemTc> list = this.logic.getSelectTc(organization);
        if (list != null && list.size() > 0)
          for (int i = 0; i < list.size(); i++) {
            JSONObject obj = new JSONObject();
            KqdsGiveitemTc base = list.get(i);
            obj.put("id", base.getSeqId());
            obj.put("pId", "0");
            obj.put("name", base.getName());
            obj.put("nocheck", "true");
            String num = (new StringBuilder(String.valueOf(base.getNum()))).toString();
            if (num.indexOf(",") != -1) {
              obj.put("isParent", "true");
            } else {
              obj.put("isParent", "false");
            } 
            listtree.add(obj);
          }  
      } else {
        List<KqdsGiveitemGiverecord> list = new ArrayList<>();
        KqdsGiveitemTc en = (KqdsGiveitemTc)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_GIVEITEM_TC, id);
        String itemnos = en.getItemno();
        String nums = (new StringBuilder(String.valueOf(en.getNum()))).toString();
        if (YZUtility.isNotNullOrEmpty(itemnos)) {
          itemnos = itemnos.substring(0, itemnos.length() - 1);
          nums = nums.substring(0, nums.length() - 1);
          String[] itemnoss = itemnos.split(",");
          String[] numss = nums.split(",");
          for (int i = 0; i < itemnoss.length; i++) {
            KqdsGiveitem itemobj = (KqdsGiveitem)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_GIVEITEM, itemnoss[i]);
            if (itemobj != null) {
              KqdsGiveitemGiverecord s = new KqdsGiveitemGiverecord();
              s.setMemberno(itemobj.getSeqId());
              s.setItemno(itemobj.getItemno());
              s.setItemname(itemobj.getItemname());
              s.setUnit(itemobj.getUnit());
              s.setZsnum(numss[i]);
              list.add(s);
            } 
          } 
        } 
        if (list != null && list.size() > 0)
          for (int j = 0; j < list.size(); j++) {
            JSONObject obj = new JSONObject();
            KqdsGiveitemGiverecord next = list.get(j);
            obj.put("id", next.getItemno());
            obj.put("pId", Integer.valueOf(0));
            obj.put("name", next.getItemname());
            listtree.add(obj);
          }  
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", listtree);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteObj.act"})
  public String deleteObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId))
        throw new Exception("seqId为空"); 
      KqdsGiveitemTc tc = (KqdsGiveitemTc)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_GIVEITEM_TC, seqId);
      if (tc == null)
        throw new Exception("赠送套餐记录不存在"); 
      this.logic.deleteSingleUUID(TableNameUtil.KQDS_GIVEITEM_TC, seqId);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
}
