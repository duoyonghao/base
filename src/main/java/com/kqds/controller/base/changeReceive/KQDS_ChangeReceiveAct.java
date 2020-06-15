package com.kqds.controller.base.changeReceive;

import com.kqds.entity.base.KqdsChangeReceive;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.changeReceive.KQDS_changeReceiveLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"KQDS_ChangeReceiveAct"})
public class KQDS_ChangeReceiveAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_ChangeReceiveAct.class);
  @Autowired
  private KQDS_changeReceiveLogic logic;
  @Autowired
  private YZDictLogic dictLogic;
  @Autowired
  private KQDS_REGLogic reglogic;
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsChangeReceive dp = new KqdsChangeReceive();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (!YZUtility.isNullorEmpty(seqId))
      {
        this.logic.updateSingleUUID(TableNameUtil.KQDS_CHANGE_RECEIVE, dp);
        
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_CHANGE_RECEIVE, dp, dp.getUsercode(), TableNameUtil.KQDS_CHANGE_RECEIVE, request);
      }
      else
      {
        dp.setSeqId(YZUtility.getUUID());
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_CHANGE_RECEIVE, dp);
        
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_CHANGE_RECEIVE, dp, dp.getUsercode(), TableNameUtil.KQDS_CHANGE_RECEIVE, request);
        
        Map<String, String> map2 = new HashMap();
        map2.put("usercode", dp.getUsercode());
        List<KqdsUserdocument> user2 = (List)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map2);
        if (user2.size() == 0) {
          throw new Exception("患者不存在，患者编号为：" + dp.getUsercode());
        }
        KqdsUserdocument u = (KqdsUserdocument)user2.get(0);
        u.setAskperson(dp.getToaskperson());
        this.logic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, u);
      }
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/zzAll.act"})
  public String zzAll(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String listdata = request.getParameter("data");
      String toaskperson = request.getParameter("toaskperson");
      String remark = request.getParameter("remark");
      JSONArray jArray = JSONArray.fromObject(listdata);
      Iterator<Object> it = jArray.iterator();
      while (it.hasNext())
      {
        JSONObject ob = (JSONObject)it.next();
        KqdsUserdocument entity = (KqdsUserdocument)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, ob.getString("seqId"));
        KqdsChangeReceive dp = new KqdsChangeReceive();
        dp.setSeqId(YZUtility.getUUID());
        dp.setOldaskperson(entity.getAskperson());
        dp.setToaskperson(toaskperson);
        dp.setRemark(remark);
        dp.setUsercode(entity.getUsercode());
        dp.setUsername(entity.getUsername());
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_CHANGE_RECEIVE, dp);
        
        entity.setAskperson(dp.getToaskperson());
        this.logic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, entity);
      }
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectNoPage.act"})
  public String selectNoPage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String queryinput = request.getParameter("queryinput");
      String olddoctor = request.getParameter("olddoctor");
      String toaskperson = request.getParameter("toaskperson");
      String RegNo = request.getParameter("RegNo");
      
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime)) {
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime)) {
        map.put("endtime", endtime);
      }
      if (!YZUtility.isNullorEmpty(queryinput)) {
        map.put("queryinput", queryinput);
      }
      if (!YZUtility.isNullorEmpty(olddoctor)) {
        map.put("olddoctor", olddoctor);
      }
      if (!YZUtility.isNullorEmpty(toaskperson)) {
        map.put("toaskperson", toaskperson);
      }
      if (!YZUtility.isNullorEmpty(RegNo)) {
        map.put("RegNo", RegNo);
      }
      List<JSONObject> list = this.logic.selectWithPage(TableNameUtil.KQDS_CHANGE_RECEIVE, map);
      if ((flag != null) && (flag.equals("exportTable")))
      {
        ExportTable.exportBootStrapTable2Excel("转诊咨询列表", fieldArr, fieldnameArr, list, response, request);
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
}
