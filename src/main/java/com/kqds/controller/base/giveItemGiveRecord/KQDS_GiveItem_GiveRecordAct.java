package com.kqds.controller.base.giveItemGiveRecord;

import com.kqds.entity.base.KqdsGiveitemGiverecord;
import com.kqds.entity.base.KqdsGiveitemUserecord;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.giveItemGiveRecord.KQDS_GiveItem_GiveRecordLogic;
import com.kqds.service.base.jzmdType.KQDS_JzqkLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"KQDS_GiveItem_GiveRecordAct"})
public class KQDS_GiveItem_GiveRecordAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_GiveItem_GiveRecordAct.class);
  @Autowired
  private KQDS_GiveItem_GiveRecordLogic logic;
  @Autowired
  private KQDS_JzqkLogic jzqkLogic;
  
  @RequestMapping({"/getGiveRecordByMemberno.act"})
  public String getGiveRecordByMemberno(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String memberno = request.getParameter("memberno");
      String queryInput = request.getParameter("queryInput");
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String usercode = request.getParameter("usercode");
      
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(memberno)) {
        map.put("memberno", memberno);
      }
      if (!YZUtility.isNullorEmpty(queryInput)) {
        map.put("queryInput", queryInput);
      }
      if (!YZUtility.isNullorEmpty(starttime)) {
        map.put("starttime", starttime + ConstUtil.TIME_START);
      }
      if (!YZUtility.isNullorEmpty(endtime)) {
        map.put("endtime", endtime + ConstUtil.TIME_END);
      }
      if (!YZUtility.isNullorEmpty(usercode)) {
        map.put("usercode", usercode);
      }
      List<JSONObject> list = this.logic.getGiveRecordByMemberno(TableNameUtil.KQDS_GIVEITEM_GIVERECORD, map);
      if ((flag != null) && (flag.equals("exportTable")))
      {
        ExportTable.exportBootStrapTable2Excel("赠送记录", fieldArr, fieldnameArr, list, response, request);
        return null;
      }
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/deleteObj.act"})
  public String deleteObj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("主键为空或者null");
      }
      KqdsGiveitemGiverecord en = (KqdsGiveitemGiverecord)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_GIVEITEM_GIVERECORD, seqId);
      this.logic.deleteSingleUUID(TableNameUtil.KQDS_GIVEITEM_GIVERECORD, seqId);
      
      BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_GIVEITEM_GIVERECORD, en, TableNameUtil.KQDS_GIVEITEM_GIVERECORD, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/confirmGiveItems.act"})
  public String confirmGiveItems(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String usercode = request.getParameter("usercode");
      String username = request.getParameter("username");
      String memberno = request.getParameter("memberno");
      String params = request.getParameter("params");
      params = URLDecoder.decode(params, "UTF-8");
      if (YZUtility.isNullorEmpty(params)) {
        throw new Exception("params为空或者null");
      }
      JSONArray jArray = JSONArray.fromObject(params);
      Collection collection = JSONArray.toCollection(jArray, KqdsGiveitemGiverecord.class);
      Iterator it = collection.iterator();
      while (it.hasNext())
      {
        KqdsGiveitemGiverecord r = (KqdsGiveitemGiverecord)it.next();
        String rid = YZUtility.getUUID();
        r.setSeqId(rid);
        r.setCreatetime(YZUtility.getCurDateTimeStr());
        r.setCreateuser(person.getSeqId());
        r.setUsercode(usercode);
        r.setUsername(username);
        r.setMemberno(memberno);
        r.setUsenum("0");
        r.setNownum(r.getZsnum());
        r.setOrganization(ChainUtil.getCurrentOrganization(request));
        
        this.logic.saveSingleUUID(TableNameUtil.KQDS_GIVEITEM_GIVERECORD, r);
        

        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_GIVEITEM_GIVERECORD, r, r.getUsercode(), TableNameUtil.KQDS_GIVEITEM_GIVERECORD, request);
      }
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/useGiveItems.act"})
  public String useGiveItems(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      String regno = request.getParameter("regno");
      String doctor = request.getParameter("doctor");
      String nurse = request.getParameter("nurse");
      String params = request.getParameter("params");
      params = URLDecoder.decode(params, "UTF-8");
      if (YZUtility.isNullorEmpty(params)) {
        throw new Exception("params为空或者null");
      }
      this.jzqkLogic.saveJzqk(doctor, regno, person, request);
      JSONArray jArray = JSONArray.fromObject(params);
      Collection collection = JSONArray.toCollection(jArray, KqdsGiveitemUserecord.class);
      Iterator it = collection.iterator();
      while (it.hasNext())
      {
        KqdsGiveitemUserecord r = (KqdsGiveitemUserecord)it.next();
        String id = r.getSeqId();
        int num = Integer.parseInt(r.getSynum());
        if (num > 0)
        {
          KqdsGiveitemGiverecord en = (KqdsGiveitemGiverecord)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_GIVEITEM_GIVERECORD, id);
          int synum = Integer.parseInt(en.getUsenum());
          int nownum = Integer.parseInt(en.getNownum());
          en.setUsenum(synum + num);
          en.setNownum(nownum - num);
          this.logic.updateSingleUUID(TableNameUtil.KQDS_GIVEITEM_GIVERECORD, en);
          
          String uuid = YZUtility.getUUID();
          r.setSeqId(uuid);
          r.setCreatetime(YZUtility.getCurDateTimeStr());
          r.setCreateuser(person.getSeqId());
          r.setUsercode(en.getUsercode());
          r.setUsername(en.getUsername());
          r.setItemno(en.getItemno());
          r.setItemname(en.getItemname());
          r.setMemberno(en.getMemberno());
          r.setUnit(en.getUnit());
          r.setUnitprice(en.getUnitprice());
          r.setZsnum(en.getZsnum());
          r.setSynum(num);
          r.setNownum(en.getNownum());
          r.setDoctor(doctor);
          r.setNurse(nurse);
          r.setStatus(Integer.valueOf(ConstUtil.GIVE_ITEM_USE_STATUS_0));
          r.setCztime("");
          r.setOrganization(ChainUtil.getCurrentOrganization(request));
          
          this.logic.saveSingleUUID(TableNameUtil.KQDS_GIVEITEM_USERECORD, r);
          

          BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_GIVEITEM_USERECORD, r, r.getUsercode(), TableNameUtil.KQDS_GIVEITEM_USERECORD, request);
        }
        KqdsReg regTmp = (KqdsReg)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, regno);
        if (regTmp != null)
        {
          regTmp.setDoctor(doctor);
          this.logic.updateSingleUUID(TableNameUtil.KQDS_REG, regTmp);
        }
      }
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
}
