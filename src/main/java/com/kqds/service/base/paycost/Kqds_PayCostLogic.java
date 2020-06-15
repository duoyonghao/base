package com.kqds.service.base.paycost;

import com.kqds.controller.base.paycost.Kqds_PayCostAct;
import com.kqds.core.adapter.DealAdapter;
import com.kqds.core.adapter.impl.ItemDealAdapter;
import com.kqds.core.global.YZSysProps;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCostorder;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.base.KqdsHospitalOrder;
import com.kqds.entity.base.KqdsHzLabelAssociated;
import com.kqds.entity.base.KqdsIntegralRecord;
import com.kqds.entity.base.KqdsJzqk;
import com.kqds.entity.base.KqdsLabel;
import com.kqds.entity.base.KqdsMember;
import com.kqds.entity.base.KqdsMemberRecord;
import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.entity.base.KqdsPaycost;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.base.KqdsVisit;
import com.kqds.entity.base.kqdsHzLabellabeAndPatient;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.base.hzLabel.KQDS_HzLabelAssociatedLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.label.KQDS_hz_labelLogic;
import com.kqds.service.base.member.KQDS_MemberLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.base.treatItem.KQDS_TreatItemLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.fkfs.YZFkfsLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;
import com.kqds.util.sys.sys.DictUtil;
import com.kqds.util.sys.sys.SysParaUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Kqds_PayCostLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  @Autowired
  private YZDictLogic dictLogic;
  @Autowired
  private YZFkfsLogic fkfsLogic;
  @Autowired
  private KQDS_MemberLogic memberlogic;
  @Autowired
  private KQDS_CostOrder_DetailLogic dlogic;
  @Autowired
  private KQDS_UserDocumentLogic userLogic;
  @Autowired
  private KQDS_REGLogic reglogic;
  @Autowired
  private KQDS_hz_labelLogic hzLabelLogic;
  @Autowired
  private KQDS_HzLabelAssociatedLogic hzLabelAssociatedLogic;
  @Autowired
  private KQDS_CostOrderLogic logic;
  @Autowired
  private KQDS_CostOrder_DetailLogic logicd;
  @Autowired
  private KQDS_UserDocumentLogic userDocumentlogic;
  @Autowired
  private KQDS_TreatItemLogic treatItemLogic;
  private static Logger logger = LoggerFactory.getLogger(Kqds_PayCostAct.class);
  
  public int getCountIndex(String table, YZPerson person, String querytype, String searchValue, String visualstaff, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    if (((YZUtility.isNullorEmpty(querytype)) || (querytype.equals("null")) || (querytype.equals("undefined"))) && 
      (!visualstaff.equals(""))) {
      map.put("querytype", visualstaff);
    }
    if (!YZUtility.isNullorEmpty(searchValue))
    {
      map.put("searchValue", searchValue);
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", searchValue));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", searchValue));
    }
    map.put("organization", organization);
    
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_PAYCOST + ".getCountIndex", map)).intValue();
    return count;
  }
  
  public void editJzqk(String regno, String doctor, String olddoctor)
    throws Exception
  {
    Map<String, String> map1 = new HashMap();
    map1.put("regno", regno);
    
    Map<String, String> map2 = new HashMap();
    map2.put("regno", regno);
    map2.put("doctor", doctor);
    this.dao.delete(TableNameUtil.KQDS_PAYCOST + ".deletebyregno2", map2);
    if (doctor.equals(olddoctor)) {
      this.dao.delete(TableNameUtil.KQDS_PAYCOST + ".deletebyregno1old", map1);
    } else {
      this.dao.delete(TableNameUtil.KQDS_PAYCOST + ".deletebyregno1", map1);
    }
  }
  
  public KqdsTreatitem getItem(String table, String itemno)
    throws Exception
  {
    List<KqdsTreatitem> list = (List)this.dao.findForList(TableNameUtil.KQDS_PAYCOST + ".getItem", itemno);
    if ((list != null) && (list.size() > 0)) {
      return (KqdsTreatitem)list.get(0);
    }
    return null;
  }
  
  public Object getQfItem(KqdsCostorderDetail detail)
    throws Exception
  {
    Object list = this.dao.findForList(TableNameUtil.KQDS_PAYCOST + ".getQfItem", detail);
    return list;
  }
  
  @Transactional(rollbackFor={Exception.class})
  public void payMoney(KqdsPaycost dp, HttpServletRequest request)
    throws Exception
  {
    YZPerson person = SessionUtil.getLoginPerson(request);
    
    String seqId = request.getParameter("seqId");
    String userSeqId = request.getParameter("userSeqId");
    String remark = request.getParameter("remark");
    String types = request.getParameter("types");
    String moneys = request.getParameter("moneys");
    String member = request.getParameter("member");
    String typestrs = request.getParameter("typestrs");
    String usercode = request.getParameter("usercode");
    String username = request.getParameter("username");
    String access = request.getParameter("access");
    BigDecimal money11 = BigDecimal.ZERO;
    BigDecimal money22 = BigDecimal.ZERO;
    
    String[] typess = types.split(",");
    String[] moneyss = moneys.split(",");
    


    BigDecimal qmmoney = BigDecimal.ZERO;
    BigDecimal qmye = BigDecimal.ZERO;
    KqdsReg reg = (KqdsReg)this.dao.loadObjSingleUUID(TableNameUtil.KQDS_REG, dp.getRegno());
    if (reg == null) {
      throw new Exception("该费用单对应的挂号记录不存在！");
    }
    KqdsCostorder cost = (KqdsCostorder)this.dao.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, dp.getCostno());
    if (cost.getStatus().equals(ConstUtil.COST_ORDER_STATUS_2)) {
      throw new Exception("该费用单已结账，操作无效！");
    }
    BigDecimal costmoney = cost.getActualmoney();
    BigDecimal paymoney = dp.getActualmoney();
    if (KqdsBigDecimal.compareTo(paymoney, costmoney) != 0) {
      throw new Exception("该费用单已出现变化，操作无效！");
    }
    Map<String, String> map1 = new HashMap();
    map1.put("usercode", dp.getUsercode());
    List<KqdsUserdocument> userList = (List)this.dao.loadList(TableNameUtil.KQDS_USERDOCUMENT, map1);
    KqdsUserdocument u = new KqdsUserdocument();
    if (userList.size() > 0) {
      u = (KqdsUserdocument)userList.get(0);
    } else {
      throw new Exception("患者信息不存在！");
    }
    Map<String, String> map2 = new HashMap();
    map2.put("costno", dp.getCostno());
    List<KqdsCostorderDetail> Detaillist = (List)this.dao.loadList(TableNameUtil.KQDS_COSTORDER_DETAIL, map2);
    for (KqdsCostorderDetail detail : Detaillist)
    {
      String qfbh = detail.getQfbh();
      String createtime = detail.getCreatetime();
      int expireflag = this.dlogic.judgeIFExpire(createtime, qfbh);
      if (expireflag == 1) {
        throw new Exception("该费用单已过期，无法结账！");
      }
    }
    List<KqdsCostorderDetail> list;
    if (!YZUtility.isNullorEmpty(seqId))
    {
      this.dao.updateSingleUUID(TableNameUtil.KQDS_PAYCOST, dp);
      

      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_PAYCOST, dp, dp.getUsercode(), TableNameUtil.KQDS_PAYCOST, request);
    }
    else
    {
      KqdsMember en = null;
      String uuid = YZUtility.getUUID();
      dp.setSeqId(uuid);
      if (cost.getIsback().intValue() == 1)
      {
        dp.setCreatetime(cost.getSftime());
      }
      else
      {
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        cost.setSftime(dp.getCreatetime());
      }
      dp.setCreateuser(person.getSeqId());
      dp.setPaytype1(types);
      dp.setPaytype2(typestrs);
      dp.setMoney1(BigDecimal.ZERO);
      dp.setMoney2(BigDecimal.ZERO);
      BigDecimal money1;
      for (int i = 0; i < typess.length; i++)
      {
        if (typess[i].equals(this.fkfsLogic.selectSeqId4costfield("payyjj")))
        {
          en = (KqdsMember)this.dao.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER, member);
          
          BigDecimal xfmoney = new BigDecimal(moneyss[i]);
          
          money1 = en.getMoney();
          BigDecimal money2 = en.getGivemoney();
          if ((KqdsBigDecimal.compareTo(money1, BigDecimal.ZERO) == 0) && (KqdsBigDecimal.compareTo(money2, BigDecimal.ZERO) == 0)) {
            throw new Exception("会员卡余额不足！");
          }
          JSONObject jobjnow = this.memberlogic.selectAllQm(en.getMemberno(), YZUtility.getCurDateTimeStr(), "qmmoney", "qmgivemoney", "qmtotal");
          
          boolean norecord = KqdsBigDecimal.compareTo(money1.add(money2), new BigDecimal(jobjnow.getString("qmtotal"))) != 0;
          if (norecord)
          {
            qmmoney = money1;
            qmye = money1.add(money2);
          }
          else
          {
            JSONObject jobj = this.memberlogic.selectAllQm(en.getMemberno(), dp.getCreatetime(), "qmmoney", "qmgivemoney", "qmtotal");
            qmmoney = new BigDecimal(jobj.getString("qmmoney"));
            qmye = new BigDecimal(jobj.getString("qmtotal"));
          }
          if (KqdsBigDecimal.compareTo(qmye, xfmoney) < 0)
          {
            if (cost.getIsback().intValue() == 1) {
              throw new Exception("原收费时间节点，会员卡余额不足！");
            }
            throw new Exception("会员卡余额不足！");
          }
          money11 = xfmoney.multiply(qmmoney).divide(qmye, 2, 4);
          
          money22 = KqdsBigDecimal.sub(xfmoney, money11);
          
          dp.setMoney1(money11);
          dp.setMoney2(money22);
        }
        if (typess[i].equals(this.fkfsLogic.selectSeqId4costfield("payintegral")))
        {
          BigDecimal xfmoney = new BigDecimal(moneyss[i]);
          if (KqdsBigDecimal.compareTo(u.getIntegral(), xfmoney) < 0) {
            throw new Exception("积分不足！");
          }
        }
      }
      editJzqk(dp.getUsercode(), dp.getDoctor(), reg.getDoctor(), dp.getRegno(), person, request);
      
      setTypeMoney(typess, moneyss, Detaillist, qmmoney, qmye, money11, money22, person, request);
      
      dp.setOrganization(ChainUtil.getCurrentOrganization(request));
      this.dao.saveSingleUUID(TableNameUtil.KQDS_PAYCOST, dp);
      



      list = this.dlogic.selectCostorderDetail(dp.getCostno());
      for (KqdsCostorderDetail kqdsCostorderDetail : list)
      {
        kqdsCostorderDetail.setKaifa("已治疗");
        kqdsCostorderDetail.setZltime(YZUtility.getThirtyMinutesLater());
        this.dlogic.updateCostorderDetailBySeqId(kqdsCostorderDetail);
      }
      if (en != null) {
        this.memberlogic.yjjSubMoney(cost, Detaillist, en, request);
      }
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_PAYCOST, dp, dp.getUsercode(), TableNameUtil.KQDS_PAYCOST, request);
      
      PushUtil.saveTx4PayCost(dp, request);
    }
    setUserAllmoney(u, dp.getDoctor(), request);
    
    addVisit(Detaillist, dp, u, request);
    

    boolean isyjj = false;
    for (KqdsCostorderDetail detail : Detaillist) {
      if (1 == detail.getIsyjjitem().intValue())
      {
        addChongzhi(detail, reg, dp.getCreatetime(), person, request);
        
        isyjj = true;
      }
    }
    Object dealAdapter = new ItemDealAdapter();
    


    boolean dealStatus = ((DealAdapter)dealAdapter).isDeal(reg, cost, Detaillist, request, this, this.dictLogic, this.userLogic);
    
    int cjStatus = 0;
    if (dealStatus)
    {
      cjStatus = 1;
      boolean isOpenBlCode = SysParaUtil.isOpenBLCodeFunc(request);
      if ((isOpenBlCode) && (YZUtility.isNullorEmpty(u.getBlcode())))
      {
        String blcode_start = SysParaUtil.getSysValueByName(request, "BLCODE_START");
        String blcode = this.userLogic.getBlcode(blcode_start);
        u.setBlcode(blcode);
        this.dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, u);
      }
    }
    List<KqdsTreatitem> listTreatitem = this.treatItemLogic.getTreatItemInfroList(Detaillist);
    String regsort = reg.getRegsort();
    if (regsort.equals("234")) {
      if (cost.getIsdrugs().intValue() == 1) {
        cjStatus = 0;
      } else if (((KqdsCostorderDetail)Detaillist.get(0)).getIsyjjitem().intValue() == 1) {
        cjStatus = 0;
      } else if ((Detaillist.size() == 8) && (((KqdsTreatitem)listTreatitem.get(0)).getBasetype().equals("jyk671"))) {
        cjStatus = 0;
      }
    }
    Integer istsxm = ((KqdsCostorderDetail)Detaillist.get(0)).getIstsxm();
    String baseType = ((KqdsTreatitem)listTreatitem.get(0)).getBasetype();
    if ((istsxm.intValue() == 1) && (baseType.equals("jyk671"))) {
      cjStatus = 0;
    }
    BigDecimal arrearmoney = cost.getArrearmoney();
    String arrearMoney = arrearmoney.toString();
    BigDecimal voidmoney = cost.getVoidmoney();
    String voidMoney = voidmoney.toString();
    BigDecimal y2 = cost.getY2();
    if (!arrearMoney.equals("0.000")) {
      if ((cost.getIsdrugs().intValue() == 1) || (((KqdsCostorderDetail)Detaillist.get(0)).getIsyjjitem().intValue() == 1) || (Detaillist.size() == 8)) {
        cjStatus = 0;
      } else {
        cjStatus = 1;
      }
    }
    if ((regsort.equals("234")) && 
      (!arrearMoney.equals("0.000"))) {
      if ((cost.getIsdrugs().intValue() == 1) || (((KqdsCostorderDetail)Detaillist.get(0)).getIsyjjitem().intValue() == 1) || (Detaillist.size() == 8)) {
        cjStatus = 0;
      } else {
        cjStatus = 1;
      }
    }
    if (!voidMoney.equals("0.000")) {
      if ((cost.getIsdrugs().intValue() == 1) || (((KqdsCostorderDetail)Detaillist.get(0)).getIsyjjitem().intValue() == 1) || (Detaillist.size() == 8)) {
        cjStatus = 0;
      } else {
        cjStatus = 1;
      }
    }
    if ((regsort.equals("234")) && 
      (!voidMoney.equals("0.000"))) {
      if ((cost.getIsdrugs().intValue() == 1) || (((KqdsCostorderDetail)Detaillist.get(0)).getIsyjjitem().intValue() == 1) || (Detaillist.size() == 8)) {
        cjStatus = 0;
      } else {
        cjStatus = 1;
      }
    }
    if (y2.compareTo(new BigDecimal(0)) == -1) {
      if ((cost.getIsdrugs().intValue() == 1) || (((KqdsCostorderDetail)Detaillist.get(0)).getIsyjjitem().intValue() == 1) || (Detaillist.size() == 8)) {
        cjStatus = 0;
      } else {
        cjStatus = 1;
      }
    }
    if ((regsort.equals("234")) && 
      (y2.compareTo(new BigDecimal(0)) == -1)) {
      if ((cost.getIsdrugs().intValue() == 1) || (((KqdsCostorderDetail)Detaillist.get(0)).getIsyjjitem().intValue() == 1) || (Detaillist.size() == 8)) {
        cjStatus = 0;
      } else {
        cjStatus = 1;
      }
    }
    this.reglogic.editCjstatus(dp.getDoctor(), dp.getRegno(), cjStatus, request);
    


    JSONObject object = this.reglogic.findRegByregNo(TableNameUtil.KQDS_REG, dp.getRegno());
    if ((object.get("recesort").equals("22")) && (object.get("cjstatus").equals("1")))
    {
      List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_PAYCOST + ".isArrearage", dp.getUsercode());
      if (list.size() == 0) {
        this.dao.update(TableNameUtil.KQDS_REG + ".updateRecesort", dp.getRegno());
      }
    }
    editCjStatusHospital(dp.getRegno(), cjStatus, request);
    
    editCjStatusNetOrder(dp, cjStatus, request);
    
    editCjStatusOrder(cost, dp, cjStatus, remark, request);
    


    BigDecimal discountMoney = cost.getDiscountmoney();
    String disMoney = discountMoney.toString();
    if ((cost.getIsdrugs().intValue() != 1) && 
      (!disMoney.equals("0.000")) && (!baseType.equals("jyk671")))
    {
      this.logic.updateCostOrderCjstatus(cost.getSeqId());
      this.reglogic.updateRegCjstatus(reg.getSeqId());
    }
    if ((regsort.equals("234")) && 
      (cost.getIsdrugs().intValue() != 1) && 
      (!disMoney.equals("0.000")) && (!baseType.equals("jyk671")))
    {
      this.logic.updateCostOrderCjstatus(cost.getSeqId());
      this.reglogic.updateRegCjstatus(reg.getSeqId());
    }
    BigDecimal costIntegral = new BigDecimal(SysParaUtil.getSysValueByName(request, "COST_INTEGRAL"));
    if ((KqdsBigDecimal.compareTo(costIntegral, BigDecimal.ZERO) > 0) && 
      (!isyjj)) {
      setIntegralMoney(typess, moneyss, dp, u, person.getSeqId(), request);
    }
    String ssje = this.userLogic.getSsje(dp.getUsercode());
    u.setTotalpay(new BigDecimal(ssje));
    this.userLogic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, u);
    
    editOrderDetail(Detaillist, request);
    
    this.dlogic.deleteDetail(dp.getUsercode(), request);
    






    Map<String, String> map = new HashMap();
    if (!YZUtility.isNullorEmpty(usercode)) {
      map.put("usercode", usercode);
    }
    if ((!YZUtility.isNullorEmpty(access)) && (!access.equals("1"))) {
      map.put("access", access);
    }
    String visualstaff = SessionUtil.getVisualstaff(request);
    
    List<JSONObject> list = this.logic.selectWithPageNopageForLabel(TableNameUtil.KQDS_COSTORDER, map, visualstaff);
    String itemname = "";
    int nums = 0;
    BigDecimal ys = new BigDecimal("0");
    List<JSONObject> itemlist1 = new ArrayList();
    List<JSONObject> itemlist2 = new ArrayList();
    Iterator localIterator3;
    JSONObject jsonObject2;
    for (Iterator localIterator2 = list.iterator(); localIterator2.hasNext(); localIterator3.hasNext())
    {
      JSONObject jsonObject = (JSONObject)localIterator2.next();
      if ((jsonObject.getInt("status") == 2) && (jsonObject.getInt("isyjjitem") == 0)) {
        ys = ys.add(new BigDecimal(jsonObject.getString("shouldmoney")));
      }
      String costno = jsonObject.getString("costno");
      map.put("costno", costno);
      List<JSONObject> list1 = this.logicd.selectWithPageLzjlForLabel(TableNameUtil.KQDS_COSTORDER_DETAIL, map);
      localIterator3 = list1.iterator(); continue;jsonObject2 = (JSONObject)localIterator3.next();
      if ((jsonObject2.getString("itemname").endsWith("种植体")) && (jsonObject2.getString("unit").equals("颗"))) {
        if (jsonObject2.getInt("num") > 0) {
          itemlist1.add(jsonObject2);
        } else if (jsonObject2.getInt("num") < 0) {
          itemlist2.add(jsonObject2);
        }
      }
    }
    List<JSONObject> itemlist3 = new ArrayList();
    Object itemlist4 = new ArrayList();
    if (itemlist1.size() > 0)
    {
      StringBuffer str = new StringBuffer();
      if (itemlist1.size() == 1) {
        itemlist3.addAll(itemlist1);
      } else {
        for (JSONObject jsonObject2 : itemlist1) {
          if (!str.toString().contains(jsonObject2.getString("itemname")))
          {
            int m = 0;
            for (int i = 0; i < itemlist1.size(); i++) {
              if ((!jsonObject2.getString("seqid").equals(((JSONObject)itemlist1.get(i)).getString("seqid"))) && (jsonObject2.getString("itemname").equals(((JSONObject)itemlist1.get(i)).getString("itemname"))))
              {
                jsonObject2.put("num", jsonObject2.getInt("num") + ((JSONObject)itemlist1.get(i)).getInt("num"));
                m = 1;
              }
            }
            if (m == 1)
            {
              itemlist3.add(jsonObject2);
              str.append(jsonObject2.getString("itemname") + ",");
            }
          }
        }
      }
      if (str.length() > 0) {
        for (JSONObject jsonObject2 : itemlist1) {
          if (!str.toString().contains(jsonObject2.getString("itemname"))) {
            itemlist3.add(jsonObject2);
          }
        }
      } else if ((str.length() == 0) && (itemlist1.size() > 1)) {
        itemlist3.addAll(itemlist1);
      }
    }
    int i;
    if (itemlist2.size() > 0)
    {
      StringBuffer str = new StringBuffer();
      if (itemlist2.size() == 1) {
        ((List)itemlist4).addAll(itemlist2);
      } else {
        for (JSONObject jsonObject2 : itemlist2) {
          if (!str.toString().contains(jsonObject2.getString("itemname")))
          {
            int m = 0;
            for (i = 0; i < itemlist2.size(); i++) {
              if ((!jsonObject2.getString("seqid").equals(((JSONObject)itemlist2.get(i)).getString("seqid"))) && (jsonObject2.getString("itemname").equals(((JSONObject)itemlist2.get(i)).getString("itemname"))))
              {
                jsonObject2.put("num", jsonObject2.getInt("num") + ((JSONObject)itemlist2.get(i)).getInt("num"));
                m = 1;
              }
            }
            if (m == 1)
            {
              ((List)itemlist4).add(jsonObject2);
              str.append(jsonObject2.getString("itemname") + ",");
            }
          }
        }
      }
      if (str.length() > 0) {
        for (JSONObject jsonObject2 : itemlist2) {
          if (!str.toString().contains(jsonObject2.getString("itemname"))) {
            ((List)itemlist4).add(jsonObject2);
          }
        }
      } else if ((str.length() == 0) && (itemlist2.size() > 1)) {
        ((List)itemlist4).addAll(itemlist2);
      }
    }
    JSONObject jsonObject3;
    if ((itemlist3.size() > 0) && (((List)itemlist4).size() > 0))
    {
      StringBuffer str = new StringBuffer();
      for (jsonObject2 = itemlist3.iterator(); jsonObject2.hasNext(); i.hasNext())
      {
        JSONObject jsonObject3 = (JSONObject)jsonObject2.next();
        i = ((List)itemlist4).iterator(); continue;JSONObject jsonObject4 = (JSONObject)i.next();
        if (jsonObject3.getString("itemname").equals(jsonObject4.getString("itemname")))
        {
          nums += jsonObject3.getInt("num") + jsonObject4.getInt("num");
          if (jsonObject3.getInt("num") + jsonObject4.getInt("num") > 0)
          {
            str.append(jsonObject3.getString("itemname") + ",");
            if (itemname.equals("")) {
              itemname = jsonObject3.getString("itemname");
            } else if (!itemname.equals(jsonObject3.getString("itemname"))) {
              itemname = itemname + "+" + jsonObject3.getString("itemname");
            }
          }
          else if (jsonObject3.getInt("num") + jsonObject4.getInt("num") == 0)
          {
            jsonObject3.put("num", "0");
          }
        }
      }
      if (str.length() > 0) {
        for (JSONObject jsonObject3 : itemlist3) {
          if ((!str.toString().contains(jsonObject3.getString("itemname"))) && (!jsonObject3.getString("num").equals("0")))
          {
            nums += jsonObject3.getInt("num");
            if (itemname.equals("")) {
              itemname = jsonObject3.getString("itemname");
            } else if (!itemname.equals(jsonObject3.getString("itemname"))) {
              itemname = itemname + "+" + jsonObject3.getString("itemname");
            }
          }
        }
      } else if ((str.length() == 0) && (itemlist3.size() > 1)) {
        for (jsonObject2 = itemlist3.iterator(); jsonObject2.hasNext();)
        {
          jsonObject3 = (JSONObject)jsonObject2.next();
          if (jsonObject3.getInt("num") > 0)
          {
            nums += jsonObject3.getInt("num");
            if (itemname.equals("")) {
              itemname = jsonObject3.getString("itemname");
            } else if (!itemname.equals(jsonObject3.getString("itemname"))) {
              itemname = itemname + "+" + jsonObject3.getString("itemname");
            }
          }
        }
      }
    }
    else if ((itemlist3.size() > 0) && (((List)itemlist4).size() == 0))
    {
      for (JSONObject jsonObject3 : itemlist3)
      {
        nums += jsonObject3.getInt("num");
        if (itemname.equals("")) {
          itemname = jsonObject3.getString("itemname");
        } else if (!itemname.equals(jsonObject3.getString("itemname"))) {
          itemname = itemname + "+" + jsonObject3.getString("itemname");
        }
      }
    }
    if (ys.compareTo(new BigDecimal("0")) == 1)
    {
      map.put("status", "4");
      int status = 4;
      
      String hzLabelAssciatedSeqId = this.hzLabelAssociatedLogic.selectKqdsHzLabelAssociatedByUserId(map);
      
      String labelname = "";
      
      String parentName = "消费区间";
      KqdsLabel kqdsLabel = this.hzLabelLogic.findKqdsHzLabelParentIdByParentName(parentName);
      if (!YZUtility.isNullorEmpty(hzLabelAssciatedSeqId))
      {
        labelname = this.hzLabelAssociatedLogic.selectKqdsHzLabelBySeqId(hzLabelAssciatedSeqId);
        String start = "";
        String end = "";
        if (!YZUtility.isNullorEmpty(labelname)) {
          if ((labelname.endsWith("以下")) && (labelname.length() == 6))
          {
            start = labelname.substring(0, 4);
          }
          else if (labelname.length() == 11)
          {
            start = labelname.substring(0, 4);
            end = labelname.substring(5, 10);
          }
          else if (labelname.length() == 12)
          {
            start = labelname.substring(0, 5);
            end = labelname.substring(6, 11);
          }
          else if (labelname.length() == 6)
          {
            start = labelname.substring(0, 2);
            end = labelname.substring(3, 5);
          }
          else
          {
            end = "200000.9";
          }
        }
        if ((!start.equals("")) && (!end.equals("")))
        {
          if ((ys.compareTo(new BigDecimal(start)) == -1) || (ys.compareTo(new BigDecimal(end)) == 1))
          {
            String ys1 = ys.toString();
            labelname = cloudOfTags(ys1, null, hzLabelAssciatedSeqId, person, usercode, username, status, kqdsLabel.getSeqId(), parentName, kqdsLabel.getParentId(), kqdsLabel.getParentName(), request);
          }
        }
        else if ((!start.equals("")) && (end.equals("")))
        {
          if (ys.compareTo(new BigDecimal(start)) == 1)
          {
            String ys1 = ys.toString();
            labelname = cloudOfTags(ys1, null, hzLabelAssciatedSeqId, person, usercode, username, status, kqdsLabel.getSeqId(), parentName, kqdsLabel.getParentId(), kqdsLabel.getParentName(), request);
          }
        }
        else if ((start.equals("")) && (!end.equals("")))
        {
          String ys1 = ys.toString();
          labelname = cloudOfTags(ys1, null, hzLabelAssciatedSeqId, person, usercode, username, status, kqdsLabel.getSeqId(), parentName, kqdsLabel.getParentId(), kqdsLabel.getParentName(), request);
        }
        else
        {
          String ys1 = ys.toString();
          labelname = cloudOfTags(ys1, null, hzLabelAssciatedSeqId, person, usercode, username, status, kqdsLabel.getSeqId(), parentName, kqdsLabel.getParentId(), kqdsLabel.getParentName(), request);
        }
      }
      else
      {
        String ys1 = ys.toString();
        labelname = cloudOfTags(ys1, null, null, person, usercode, username, status, kqdsLabel.getSeqId(), parentName, kqdsLabel.getParentId(), kqdsLabel.getParentName(), request);
      }
    }
    String xfxmLabelname;
    if ((!YZUtility.isNullorEmpty(itemname)) && (nums > 0))
    {
      map.put("status", "3");
      int status = 3;
      xfxmLabelname = "";
      
      String hzLabelAssciatedSeqId = this.hzLabelAssociatedLogic.selectKqdsHzLabelAssociatedByUserId(map);
      
      String parentName = "消费项目(种植体品牌)";
      KqdsLabel kqdsLabel = this.hzLabelLogic.findKqdsHzLabelParentIdByParentName(parentName);
      if (!YZUtility.isNullorEmpty(hzLabelAssciatedSeqId))
      {
        xfxmLabelname = this.hzLabelAssociatedLogic.selectKqdsHzLabelBySeqId(hzLabelAssciatedSeqId);
        if ((xfxmLabelname != null) && (!xfxmLabelname.equals(itemname))) {
          xfxmLabelname = cloudOfTags(null, itemname, hzLabelAssciatedSeqId, person, usercode, username, status, kqdsLabel.getSeqId(), parentName, kqdsLabel.getParentId(), kqdsLabel.getParentName(), request);
        }
      }
      else
      {
        xfxmLabelname = cloudOfTags(null, itemname, null, person, usercode, username, status, kqdsLabel.getSeqId(), parentName, kqdsLabel.getParentId(), kqdsLabel.getParentName(), request);
      }
    }
    else
    {
      KqdsHzLabelAssociated kqdsHzLabelAssociated = new KqdsHzLabelAssociated();
      kqdsHzLabelAssociated.setModifier(person.getSeqId());
      kqdsHzLabelAssociated.setUpdateTime(YZUtility.getCurDateTimeStr());
      kqdsHzLabelAssociated.setUsercode(usercode);
      kqdsHzLabelAssociated.setStatus(3);
      xfxmLabelname = this.hzLabelAssociatedLogic.updateKqdsHzLabelAssociatedByStatus(kqdsHzLabelAssociated);
    }
  }
  
  private String cloudOfTags(String ys, String itemname, String hzLabelAssciatedSeqId, YZPerson person, String usercode, String username, int status, String parentId, String parentName, String labelOneId, String labelOneName, HttpServletRequest request)
    throws Exception
  {
    if (!YZUtility.isNullorEmpty(hzLabelAssciatedSeqId))
    {
      KqdsHzLabelAssociated kqdsHzLabelAssociated = new KqdsHzLabelAssociated();
      kqdsHzLabelAssociated.setSeqId(hzLabelAssciatedSeqId);
      kqdsHzLabelAssociated.setModifier(person.getSeqId());
      kqdsHzLabelAssociated.setUpdateTime(YZUtility.getCurDateTimeStr());
      int i = this.hzLabelAssociatedLogic.updateKqdsHzLabelAssociated(kqdsHzLabelAssociated);
    }
    String labelname = "";
    if (!YZUtility.isNullorEmpty(ys))
    {
      double d = Double.valueOf(ys).doubleValue();
      if ((d >= 0.0D) && (d <= 5000.8999999999996D)) {
        labelname = "5000以下";
      } else if ((d >= 5001.0D) && (d <= 20000.900000000001D)) {
        labelname = "5001-20000元";
      } else if ((d >= 20001.0D) && (d <= 40000.900000000001D)) {
        labelname = "20001-40000元";
      } else if ((d >= 40001.0D) && (d <= 60000.900000000001D)) {
        labelname = "40001-60000元";
      } else if ((d >= 60001.0D) && (d <= 80000.899999999994D)) {
        labelname = "60001-80000元";
      } else if ((d >= 80001.0D) && (d <= 100000.89999999999D)) {
        labelname = "80001-100000元";
      } else if ((d >= 100001.0D) && (d <= 150000.89999999999D)) {
        labelname = "10-15万";
      } else if ((d >= 150001.0D) && (d <= 200000.89999999999D)) {
        labelname = "15-20万";
      } else if (d >= 200001.0D) {
        labelname = "20万以上";
      }
    }
    else if (!YZUtility.isNullorEmpty(itemname))
    {
      labelname = itemname;
    }
    Object map1 = new HashMap();
    if ((labelname != null) && (!labelname.equals(""))) {
      ((Map)map1).put("leveLabel", labelname);
    }
    if ((!parentId.equals("")) && (parentId != null)) {
      ((Map)map1).put("parentId", parentId);
    }
    String labelId = this.hzLabelLogic.selectKqdsHzLabelByLeveLabel((Map)map1);
    String seqid;
    if (YZUtility.isNullorEmpty(labelId))
    {
      KqdsLabel kqdsHzLabel = new KqdsLabel();
      seqid = YZUtility.getUUID();
      labelId = seqid;
      kqdsHzLabel.setSeqId(seqid);
      kqdsHzLabel.setCreateTime(YZUtility.getCurDateTimeStr());
      kqdsHzLabel.setCreateUser(person.getSeqId());
      kqdsHzLabel.setLeveLabel(labelname);
      kqdsHzLabel.setParentId(parentId);
      kqdsHzLabel.setParentName(parentName);
      kqdsHzLabel.setStatus("0");
      kqdsHzLabel.setRemark("三级");
      int j = this.hzLabelLogic.insertKqdsHzLabel(kqdsHzLabel);
      KqdsHzLabelAssociated kqdsHzLabelAssociated1 = new KqdsHzLabelAssociated();
      hzLabelAssciatedSeqId = YZUtility.getUUID();
      kqdsHzLabelAssociated1.setSeqId(hzLabelAssciatedSeqId);
      kqdsHzLabelAssociated1.setLabeId(seqid);
      kqdsHzLabelAssociated1.setUsercode(usercode);
      kqdsHzLabelAssociated1.setUserName(username);
      kqdsHzLabelAssociated1.setCreateTime(YZUtility.getCurDateTimeStr());
      kqdsHzLabelAssociated1.setCreateUser(person.getSeqId());
      kqdsHzLabelAssociated1.setRemark("");
      kqdsHzLabelAssociated1.setStatus(status);
      kqdsHzLabelAssociated1.setIsdelete(0);
      int j = this.hzLabelAssociatedLogic.insertKqdsHzLabelAssociated(kqdsHzLabelAssociated1);
    }
    else
    {
      KqdsHzLabelAssociated kqdsHzLabelAssociated1 = new KqdsHzLabelAssociated();
      hzLabelAssciatedSeqId = YZUtility.getUUID();
      kqdsHzLabelAssociated1.setSeqId(hzLabelAssciatedSeqId);
      kqdsHzLabelAssociated1.setLabeId(labelId);
      kqdsHzLabelAssociated1.setUsercode(usercode);
      kqdsHzLabelAssociated1.setUserName(username);
      kqdsHzLabelAssociated1.setCreateTime(YZUtility.getCurDateTimeStr());
      kqdsHzLabelAssociated1.setCreateUser(person.getSeqId());
      kqdsHzLabelAssociated1.setRemark("");
      kqdsHzLabelAssociated1.setStatus(status);
      kqdsHzLabelAssociated1.setIsdelete(0);
      seqid = this.hzLabelAssociatedLogic.insertKqdsHzLabelAssociated(kqdsHzLabelAssociated1);
    }
    Map<String, String> map = new HashMap();
    map.put("userCode", usercode);
    map.put("labelTwoId", parentId);
    String threeid = this.userDocumentlogic.selectLabelByUsercode(map);
    this.userDocumentlogic.deleteLabelByUsercode(map);
    kqdsHzLabellabeAndPatient kPatient = new kqdsHzLabellabeAndPatient();
    String id = YZUtility.getUUID();
    kPatient.setSeqId(id);
    kPatient.setUserSeqId("");
    kPatient.setUserId(usercode);
    kPatient.setUserName(username);
    kPatient.setLabelOneId(labelOneId);
    kPatient.setLabelOneName(labelOneName);
    kPatient.setLabelTwoId(parentId);
    kPatient.setLabelTwoName(parentName);
    kPatient.setLabelThreeId(labelId);
    kPatient.setLabelThreeName(labelname);
    kPatient.setCreateUser(person.getSeqId());
    kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
    kPatient.setOrganization(ChainUtil.getCurrentOrganization(request));
    this.userDocumentlogic.saveKpatient(kPatient);
    



































































































    BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_Hz_LabellabeAndPatient, kPatient, usercode, TableNameUtil.KQDS_Hz_LabellabeAndPatient, request);
    return labelname;
  }
  
  @Transactional(rollbackFor={Exception.class})
  public void addVisit(List<KqdsCostorderDetail> list, KqdsPaycost dp, KqdsUserdocument u, HttpServletRequest request)
    throws Exception
  {
    boolean flag = false;
    for (KqdsCostorderDetail detail : list) {
      if (4 == detail.getIsyjjitem().intValue())
      {
        flag = true;
        break;
      }
    }
    String hffl = this.dictLogic.getDictIdByNameAndParentCode("HFFL", "回访提醒");
    if ((flag) && (!YZUtility.isNullorEmpty(hffl)))
    {
      KqdsVisit visit = new KqdsVisit();
      visit.setSeqId(YZUtility.getUUID());
      visit.setCreatetime(YZUtility.getCurDateTimeStr());
      visit.setCreateuser(dp.getDoctor());
      visit.setOrganization(dp.getOrganization());
      visit.setUsercode(dp.getUsercode());
      visit.setUsername(dp.getUsername());
      visit.setSex(u.getSex());
      visit.setTelphone(u.getPhonenumber1());
      visit.setVisittime(dp.getCreatetime().substring(0, dp.getCreatetime().length() - 3));
      visit.setHffl(hffl);
      visit.setVisitor(dp.getDoctor());
      visit.setHfyd("提醒设置回访！");
      this.dao.saveSingleUUID(TableNameUtil.KQDS_VISIT, visit);
      
      PushUtil.saveTx4NewVisit(visit, request);
    }
  }
  
  public void setUserAllmoney(KqdsUserdocument u, String doctor, HttpServletRequest request)
    throws Exception
  {
    u.setDoctor(doctor);
    String iskaipiao = YZSysProps.getProp("IS_KAIPIAO");
    if ("IS_KAIPIAO_TRUE".equals(iskaipiao)) {
      u.setCostlevel("0");
    }
    this.dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, u);
  }
  
  public void editJzqk(String usercode, String doctor, String olddoctor, String regno, YZPerson person, HttpServletRequest request)
    throws Exception
  {
    editJzqk(regno, doctor, olddoctor);
    
    KqdsJzqk jzqk = new KqdsJzqk();
    jzqk.setSeqId(UUID.randomUUID().toString());
    jzqk.setRegno(regno);
    jzqk.setDoctor(doctor);
    jzqk.setCreatetime(YZUtility.getCurDateTimeStr());
    jzqk.setCreateuser(person.getSeqId());
    jzqk.setOrganization(ChainUtil.getCurrentOrganization(request));
    this.dao.saveSingleUUID(TableNameUtil.KQDS_JZQK, jzqk);
  }
  
  private void setTypeMoney(String[] typess, String[] moneyss, List<KqdsCostorderDetail> listDetail, BigDecimal qmmoney, BigDecimal qmye, BigDecimal ssall, BigDecimal zsall, YZPerson person, HttpServletRequest request)
    throws Exception
  {
    BigDecimal zss = BigDecimal.ZERO;
    BigDecimal sss = BigDecimal.ZERO;
    for (int i = 0; i < typess.length; i++)
    {
      BigDecimal xfmoney = new BigDecimal(moneyss[i]);
      for (KqdsCostorderDetail detail : listDetail)
      {
        BigDecimal paymoney = detail.getPaymoney();
        BigDecimal paymoneyed = BigDecimal.ZERO;
        paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPaybank());
        
        paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPayyjj());
        paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPayyb());
        paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPayxj());
        paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPaywx());
        paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPayzfb());
        paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPaymmd());
        paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPaybdfq());
        paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPaydjq());
        paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPayintegral());
        paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPayother1());
        paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPayother2());
        
        paymoney = paymoney.subtract(paymoneyed);
        if (KqdsBigDecimal.compareTo(paymoney, BigDecimal.ZERO) != 0) {
          if (KqdsBigDecimal.compareTo(xfmoney, BigDecimal.ZERO) == 1)
          {
            if (typess[i].equals(this.fkfsLogic.selectSeqId4costfield("payyjj"))) {
              if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0)
              {
                xfmoney = xfmoney.subtract(paymoney);
                

                BigDecimal pay = paymoney.multiply(qmmoney).divide(qmye, 2, 4);
                BigDecimal zs = paymoney.subtract(pay);
                detail.setPayyjj(pay);
                detail.setPayother2(zs);
                sss = sss.add(pay);
                zss = zss.add(zs);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
              else
              {
                detail.setPayother2(zsall.subtract(zss));
                detail.setPayyjj(ssall.subtract(sss));
                xfmoney = xfmoney.subtract(xfmoney);
                
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
            }
            if (typess[i].equals(this.fkfsLogic.selectSeqId4costfield("payyb"))) {
              if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0)
              {
                xfmoney = xfmoney.subtract(paymoney);
                detail.setPayyb(paymoney);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
              else
              {
                detail.setPayyb(xfmoney);
                xfmoney = xfmoney.subtract(xfmoney);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
            }
            if (typess[i].equals(this.fkfsLogic.selectSeqId4costfield("payxj"))) {
              if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0)
              {
                xfmoney = xfmoney.subtract(paymoney);
                detail.setPayxj(paymoney);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
              else
              {
                detail.setPayxj(xfmoney);
                xfmoney = xfmoney.subtract(xfmoney);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
            }
            if (typess[i].equals(this.fkfsLogic.selectSeqId4costfield("paybank"))) {
              if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0)
              {
                xfmoney = xfmoney.subtract(paymoney);
                detail.setPaybank(paymoney);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
              else
              {
                detail.setPaybank(xfmoney);
                xfmoney = xfmoney.subtract(xfmoney);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
            }
            if (typess[i].equals(this.fkfsLogic.selectSeqId4costfield("paywx"))) {
              if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0)
              {
                xfmoney = xfmoney.subtract(paymoney);
                detail.setPaywx(paymoney);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
              else
              {
                detail.setPaywx(xfmoney);
                xfmoney = xfmoney.subtract(xfmoney);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
            }
            if (typess[i].equals(this.fkfsLogic.selectSeqId4costfield("payzfb"))) {
              if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0)
              {
                xfmoney = xfmoney.subtract(paymoney);
                detail.setPayzfb(paymoney);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
              else
              {
                detail.setPayzfb(xfmoney);
                xfmoney = xfmoney.subtract(xfmoney);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
            }
            if (typess[i].equals(this.fkfsLogic.selectSeqId4costfield("paymmd"))) {
              if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0)
              {
                xfmoney = xfmoney.subtract(paymoney);
                detail.setPaymmd(paymoney);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
              else
              {
                detail.setPaymmd(xfmoney);
                xfmoney = xfmoney.subtract(xfmoney);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
            }
            if (typess[i].equals(this.fkfsLogic.selectSeqId4costfield("paybdfq"))) {
              if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0)
              {
                xfmoney = xfmoney.subtract(paymoney);
                detail.setPaybdfq(paymoney);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
              else
              {
                detail.setPaybdfq(xfmoney);
                xfmoney = xfmoney.subtract(xfmoney);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
            }
            if (typess[i].equals(this.fkfsLogic.selectSeqId4costfield("payother1"))) {
              if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0)
              {
                xfmoney = xfmoney.subtract(paymoney);
                detail.setPayother1(paymoney);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
              else
              {
                detail.setPayother1(xfmoney);
                xfmoney = xfmoney.subtract(xfmoney);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
            }
            if (typess[i].equals(this.fkfsLogic.selectSeqId4costfield("paydjq"))) {
              if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0)
              {
                xfmoney = xfmoney.subtract(paymoney);
                detail.setPaydjq(paymoney);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
              else
              {
                detail.setPaydjq(xfmoney);
                xfmoney = xfmoney.subtract(xfmoney);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
            }
            if (typess[i].equals(this.fkfsLogic.selectSeqId4costfield("payintegral"))) {
              if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0)
              {
                xfmoney = xfmoney.subtract(paymoney);
                detail.setPayintegral(paymoney);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
              else
              {
                detail.setPayintegral(xfmoney);
                xfmoney = xfmoney.subtract(xfmoney);
                this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
              }
            }
          }
        }
      }
    }
  }
  
  private void addChongzhi(KqdsCostorderDetail detail, KqdsReg reg, String createtime, YZPerson person, HttpServletRequest request)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("memberno", detail.getUsercode());
    
    KqdsMemberRecord r = new KqdsMemberRecord();
    try
    {
      List<KqdsMember> list = (List)this.dao.loadList(TableNameUtil.KQDS_MEMBER, map);
      
      KqdsMember en = (KqdsMember)list.get(0);
      en.setMoney(KqdsBigDecimal.add(en.getMoney(), detail.getPaymoney()));
      this.dao.updateSingleUUID(TableNameUtil.KQDS_MEMBER, en);
      
      r.setSeqId(YZUtility.getUUID());
      r.setUsercode(detail.getUsercode());
      r.setUsername(en.getUsername());
      r.setCardno(en.getMemberno());
      r.setCreatetime(createtime);
      r.setCreateuser(person.getSeqId());
      r.setType(ConstUtil.MEMBER_RECORD_TYPE_CZ);
      r.setAskperson(detail.getAskperson());
      r.setRegsort(reg.getRegsort());
      r.setXjmoney(detail.getPayxj());
      r.setYhkmoney(detail.getPaybank());
      r.setQtmoney(detail.getPayother1());
      r.setZfbmoney(detail.getPayzfb());
      r.setWxmoney(detail.getPaywx());
      r.setMmdmoney(detail.getPaymmd());
      r.setBdfqmoney(detail.getPaybdfq());
      r.setCmoney(detail.getPaymoney());
      r.setCtotal(detail.getPaymoney());
      r.setYmoney(en.getMoney());
      r.setYgivemoney(en.getGivemoney());
      r.setYtotal(KqdsBigDecimal.add(en.getMoney(), en.getGivemoney()));
      r.setOrganization(ChainUtil.getCurrentOrganization(request));
      r.setCostno(detail.getCostno());
      this.dao.saveSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, r);
    }
    catch (Exception e)
    {
      logger.error("此患者预交金卡不存在，请先进行开户操作! 谢谢合作");
    }
    BcjlUtil.LogBcjl(r.getSeqId(), BcjlUtil.KQDS_MEMBER_RECORD, r, TableNameUtil.KQDS_MEMBER_RECORD, request);
  }
  
  private void editCjStatusHospital(String regno, int cjstatus, HttpServletRequest request)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("regno", regno);
    List<KqdsHospitalOrder> hos = (List)this.dao.loadList(TableNameUtil.KQDS_HOSPITAL_ORDER, map);
    if ((hos != null) && (hos.size() > 0))
    {
      KqdsHospitalOrder net = (KqdsHospitalOrder)hos.get(0);
      net.setCjstatus(Integer.valueOf(cjstatus));
      this.dao.updateSingleUUID(TableNameUtil.KQDS_HOSPITAL_ORDER, net);
    }
  }
  
  private void editCjStatusOrder(KqdsCostorder en, KqdsPaycost dp, int cjstatus, String remark, HttpServletRequest request)
    throws Exception
  {
    en.setDoctor(dp.getDoctor());
    en.setNurse(dp.getNurse());
    en.setTechperson(dp.getTechperson());
    if (en.getIsback().intValue() != 1) {
      en.setSftime(dp.getCreatetime());
    }
    en.setSfuser(dp.getCreateuser());
    en.setCjstatus(Integer.valueOf(cjstatus));
    en.setRemark(remark);
    en.setStatus(ConstUtil.COST_ORDER_STATUS_2);
    
    this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER, en);
  }
  
  private void editOrderDetail(List<KqdsCostorderDetail> listdetail, HttpServletRequest request)
    throws Exception
  {
    for (KqdsCostorderDetail detail : listdetail)
    {
      BigDecimal zss = detail.getArrearmoney();
      if (KqdsBigDecimal.compareTo(zss, BigDecimal.ZERO) == 0)
      {
        detail.setStatus(ConstUtil.COST_DETAIL_STATUS_0);
        detail.setIsqfreal(Integer.valueOf(ConstUtil.QF_STATUS_0));
        this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
      }
      else
      {
        detail.setIsqfreal(Integer.valueOf(ConstUtil.QF_STATUS_1));
        if (YZUtility.isNullorEmpty(detail.getQfbh())) {
          detail.setQfbh(YZUtility.getUUID());
        }
        this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
      }
      if (YZUtility.isNotNullOrEmpty(detail.getQfbh()))
      {
        List<KqdsCostorderDetail> oldQfList = (List)getQfItem(detail);
        for (KqdsCostorderDetail qfDetail : oldQfList)
        {
          qfDetail.setIsqfreal(Integer.valueOf(0));
          this.dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, qfDetail);
        }
      }
    }
  }
  
  private void editCjStatusNetOrder(KqdsPaycost dp, int cjstatus, HttpServletRequest request)
    throws Exception
  {
    Map<String, String> mapjz = new HashMap();
    mapjz.put("regno", dp.getRegno());
    List<KqdsNetOrder> en = (List)this.dao.loadList(TableNameUtil.KQDS_NET_ORDER, mapjz);
    if ((en != null) && (en.size() > 0))
    {
      KqdsNetOrder net = (KqdsNetOrder)en.get(0);
      net.setCjstatus(Integer.valueOf(cjstatus));
      net.setOrderperson(dp.getDoctor());
      this.dao.updateSingleUUID(TableNameUtil.KQDS_NET_ORDER, net);
    }
  }
  
  private void setIntegralMoney(String[] typess, String[] moneyss, KqdsPaycost dp, KqdsUserdocument u, String perId, HttpServletRequest request)
    throws Exception
  {
    BigDecimal ssmoney = BigDecimal.ZERO;
    
    BigDecimal zsmoney = BigDecimal.ZERO;
    BigDecimal jfmoney = BigDecimal.ZERO;
    String jfzj = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JFLX, "消费增加");
    String jfjs = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JFLX, "消费减少");
    if ((YZUtility.isNullorEmpty(jfzj)) || (YZUtility.isNullorEmpty(jfjs))) {
      throw new Exception("积分类型不存在！");
    }
    for (int i = 0; i < typess.length; i++)
    {
      if (typess[i].equals(this.fkfsLogic.selectSeqId4costfield("payintegral")))
      {
        zsmoney = zsmoney.add(new BigDecimal(moneyss[i]));
        jfmoney = new BigDecimal(moneyss[i]);
        
        KqdsIntegralRecord record = new KqdsIntegralRecord();
        record.setSeqId(UUID.randomUUID().toString());
        record.setCreatetime(YZUtility.getCurDateTimeStr());
        record.setOrganization(ChainUtil.getCurrentOrganization(request));
        record.setUsercode(u.getUsercode());
        record.setJfmoney(BigDecimal.ZERO.subtract(jfmoney));
        record.setJftype(jfjs);
        record.setSyjfmoney(u.getIntegral().subtract(jfmoney));
        record.setCreateuser(perId);
        this.dao.saveSingleUUID(TableNameUtil.KQDS_INTEGRAL_RECORD, record);
        
        u.setIntegral(u.getIntegral().subtract(jfmoney));
      }
      if (typess[i].equals(this.fkfsLogic.selectSeqId4costfield("paydjq"))) {
        zsmoney = zsmoney.add(new BigDecimal(moneyss[i]));
      }
    }
    if ((dp.getMoney2() != null) && (KqdsBigDecimal.compareTo(dp.getMoney2(), BigDecimal.ZERO) > 0)) {
      zsmoney = zsmoney.add(dp.getMoney2());
    }
    ssmoney = dp.getActualmoney().subtract(zsmoney);
    BigDecimal costIntegral = new BigDecimal(SysParaUtil.getSysValueByName(request, "COST_INTEGRAL"));
    BigDecimal integral = ssmoney.divide(costIntegral, 0, RoundingMode.DOWN);
    if (u.getIntegral() == null) {
      u.setIntegral(BigDecimal.ZERO);
    }
    u.setIntegral(u.getIntegral().add(integral));
    this.dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, u);
    
    KqdsIntegralRecord record = new KqdsIntegralRecord();
    record.setSeqId(UUID.randomUUID().toString());
    record.setCreatetime(YZUtility.getCurDateTimeStr());
    record.setOrganization(ChainUtil.getCurrentOrganization(request));
    record.setUsercode(u.getUsercode());
    record.setJfmoney(integral);
    record.setJftype(jfzj);
    record.setSyjfmoney(u.getIntegral());
    record.setCreateuser(perId);
    this.dao.saveSingleUUID(TableNameUtil.KQDS_INTEGRAL_RECORD, record);
  }
  
  public List<JSONObject> findPayCostByUsercodes(String usercodes)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_PAYCOST + ".findPayCostByUsercodes", usercodes);
    return list;
  }
  
  public List<JSONObject> discount(Map<String, String> map)
    throws Exception
  {
    return (List)this.dao.findForList(TableNameUtil.KQDS_COSTORDER + ".discount", map);
  }
}
