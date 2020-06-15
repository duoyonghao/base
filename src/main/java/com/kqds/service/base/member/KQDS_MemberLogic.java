package com.kqds.service.base.member;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.core.util.db.DBUtil;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCostorder;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.base.KqdsMember;
import com.kqds.entity.base.KqdsMemberRecord;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.fkfs.YZFkfsLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;
import com.kqds.util.sys.sys.DictUtil;
import com.kqds.util.sys.sys.SysParaUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KQDS_MemberLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  @Autowired
  private YZDictLogic dictLogic;
  @Autowired
  private YZFkfsLogic fkfsLogic;
  
  public List<JSONObject> getMemberListByUserCode(String usercode)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_MEMBER + ".getMemberListByUserCode", usercode);
    return list;
  }
  
  public List<JSONObject> getMemberListByMember(String memberno, String seqId)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("memberno", memberno);
    map.put("seqId", seqId);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_MEMBER + ".getMemberListByMember", map);
    return list;
  }
  
  public void setCardno(String memberno1, String memberno2)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("memberno1", memberno1);
    map.put("memberno2", memberno2);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".updateCardno", map);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".updateShCardno", map);
  }
  
  public void yjjSubMoney(KqdsCostorder costlist, List<KqdsCostorderDetail> cost, KqdsMember en, HttpServletRequest request)
    throws Exception
  {
    BigDecimal money1 = BigDecimal.ZERO;
    BigDecimal money2 = BigDecimal.ZERO;
    for (KqdsCostorderDetail detail : cost) {
      if (detail.getPayyjj() != null)
      {
        money1 = KqdsBigDecimal.add(money1, detail.getPayyjj());
        money2 = KqdsBigDecimal.add(money2, detail.getPayother2());
      }
    }
    en.setMoney(KqdsBigDecimal.sub(en.getMoney(), money1));
    en.setGivemoney(KqdsBigDecimal.sub(en.getGivemoney(), money2));
    this.dao.updateSingleUUID(TableNameUtil.KQDS_MEMBER, en);
    

    BcjlUtil.LogBcjlWithUserCode(BcjlUtil.PAY, BcjlUtil.KQDS_MEMBER, en, en.getUsercode(), TableNameUtil.KQDS_MEMBER, request);
    
    KqdsMemberRecord r = new KqdsMemberRecord();
    r.setSeqId(YZUtility.getUUID());
    r.setUsercode(costlist.getUsercode());
    r.setUsername(costlist.getUsername());
    r.setCardno(en.getMemberno());
    r.setCreatetime(costlist.getSftime());
    r.setCreateuser(costlist.getSfuser());
    r.setType("缴费");
    r.setCmoney(KqdsBigDecimal.sub(BigDecimal.ZERO, money1));
    
    r.setCgivemoney(KqdsBigDecimal.sub(BigDecimal.ZERO, money2));
    BigDecimal ctotal = KqdsBigDecimal.add(money1, money2);
    r.setCtotal(KqdsBigDecimal.sub(BigDecimal.ZERO, ctotal));
    r.setYmoney(en.getMoney());
    r.setYgivemoney(en.getGivemoney());
    r.setYtotal(KqdsBigDecimal.add(en.getMoney(), en.getGivemoney()));
    r.setCostno(costlist.getCostno());
    r.setOrganization(ChainUtil.getCurrentOrganization(request));
    this.dao.saveSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, r);
  }
  
  public int checkIsMemberByUsercode(String usercode)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("usercode", usercode);
    
    List<KqdsMember> en = (List)this.dao.loadList(TableNameUtil.KQDS_MEMBER, map);
    return en.size();
  }
  
  public JSONObject selectList(String table, Map<String, String> map, String visualstaff, String organization, JSONObject json, BootStrapPage bp)
    throws Exception
  {
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    map.put("visualstaff", visualstaff);
    if (map.containsKey("queryInput"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", (String)map.get("queryInput")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", (String)map.get("queryInput")));
    }
    if (map.containsKey("pagenum"))
    {
      if (((String)map.get("pagenum")).contains("3")) {
        map.put("pagenumsql", "3");
      }
      if (((String)map.get("pagenum")).contains("1")) {
        map.put("pagenumsql", "1");
      }
    }
    if (map.get("sortName") != null)
    {
      if (((String)map.get("sortName")).equals("usercode")) {
        map.put("sortName", "m.usercode");
      }
      if (((String)map.get("sortName")).equals("username")) {
        map.put("sortName", "m.username");
      }
      if (((String)map.get("sortName")).equals("askpersonname")) {
        map.put("sortName", "p.user_name");
      }
      if (((String)map.get("sortName")).equals("createtime")) {
        map.put("sortName", "m.createtime");
      }
      if (((String)map.get("sortName")).equals("memberno")) {
        map.put("sortName", "m.memberno");
      }
      if (((String)map.get("sortName")).equals("icno")) {
        map.put("sortName", "m.icno");
      }
      if (((String)map.get("sortName")).equals("memberlevel")) {
        map.put("sortName", "kcit1.dict_name");
      }
      if (((String)map.get("sortName")).equals("discount")) {
        map.put("sortName", "m.discount");
      }
      if (((String)map.get("sortName")).equals("discountdate")) {
        map.put("sortName", "m.discountdate");
      }
      if (((String)map.get("sortName")).equals("money")) {
        map.put("sortName", "m.money");
      }
      if (((String)map.get("sortName")).equals("jdr")) {
        map.put("sortName", "p2.user_id");
      }
      if (((String)map.get("sortName")).equals("kfr")) {
        map.put("sortName", "p3.user_id");
      }
      if (((String)map.get("sortName")).equals("remark")) {
        map.put("sortName", "m.remark");
      }
      if (((String)map.get("sortName")).equals("devchannel")) {
        map.put("sortName", "kcit3.dict_name");
      }
      if (((String)map.get("sortName")).equals("totalmoney")) {
        map.put("sortName", "m.money+m.givemoney");
      }
    }
    List<JSONObject> list1 = (List)this.dao.findForList(TableNameUtil.KQDS_MEMBER + ".selectListMoney", map);
    
    String search = bp.getSearch();
    String sort = bp.getSort();
    json.put("search", search);
    json.put("sort", sort);
    
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_MEMBER + ".selectList", map);
    

    List<JSONObject> list2 = new ArrayList();
    for (JSONObject obj1 : list) {
      if ((list2 == null) || (list2.size() == 0))
      {
        list2.add(obj1);
      }
      else
      {
        boolean flag = true;
        for (JSONObject obj2 : list2) {
          if (obj1.getString("memberno").equals(obj2.getString("memberno")))
          {
            flag = false;
            break;
          }
        }
        if (flag) {
          list2.add(obj1);
        }
      }
    }
    for (JSONObject job : list2)
    {
      String memberstatus = job.getString("memberstatus");
      if (ConstUtil.MEMBER_STATUS_0.equals(memberstatus)) {
        memberstatus = ConstUtil.MEMBER_STATUS_0_DESC;
      } else if (ConstUtil.MEMBER_STATUS_1.equals(memberstatus)) {
        memberstatus = ConstUtil.MEMBER_STATUS_1_DESC;
      } else if (ConstUtil.MEMBER_STATUS_2.equals(memberstatus)) {
        memberstatus = ConstUtil.MEMBER_STATUS_2_DESC;
      } else if (ConstUtil.MEMBER_STATUS_3.equals(memberstatus)) {
        memberstatus = ConstUtil.MEMBER_STATUS_3_DESC;
      }
      job.put("memberstatus", memberstatus);
      float money = 0.0F;
      if (!YZUtility.isNullorEmpty(job.getString("money"))) {
        money = Float.parseFloat(job.getString("money"));
      }
      float givemoney = 0.0F;
      if (!YZUtility.isNullorEmpty(job.getString("givemoney"))) {
        givemoney = Float.parseFloat(job.getString("givemoney"));
      }
      job.put("totalmoney", Float.valueOf(givemoney + money));
    }
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list2);
    jobj.put("mon", list1);
    return jobj;
  }
  
  @Transactional(rollbackFor={Exception.class})
  public JSONObject saveChongzhi(String moneys, String types, String askperson, String regsort, String seqId, KqdsMember dp, YZPerson person, JSONObject czfs, String recordname, String tfremark, HttpServletRequest request)
    throws Exception
  {
    BigDecimal xjmoney = BigDecimal.ZERO;
    BigDecimal yhkmoney = BigDecimal.ZERO;
    BigDecimal qtmoney = BigDecimal.ZERO;
    BigDecimal zfbmoney = BigDecimal.ZERO;
    BigDecimal wxmoney = BigDecimal.ZERO;
    BigDecimal mmdmoney = BigDecimal.ZERO;
    BigDecimal bdfqmoney = BigDecimal.ZERO;
    BigDecimal totalmoney = BigDecimal.ZERO;
    
    String[] typesstrs = types.split(",");
    if (types.indexOf(",") != -1)
    {
      String[] moneyss = moneys.split(",");
      for (int i = 0; i < typesstrs.length; i++) {
        if (typesstrs[i].equals(this.fkfsLogic.selectSeqId4Memberfield("xjmoney")))
        {
          xjmoney = new BigDecimal(moneyss[i]);
          totalmoney = KqdsBigDecimal.add(totalmoney, xjmoney);
        }
        else if (typesstrs[i].equals(this.fkfsLogic.selectSeqId4Memberfield("yhkmoney")))
        {
          yhkmoney = new BigDecimal(moneyss[i]);
          totalmoney = KqdsBigDecimal.add(totalmoney, yhkmoney);
        }
        else if (typesstrs[i].equals(this.fkfsLogic.selectSeqId4Memberfield("zfbmoney")))
        {
          zfbmoney = new BigDecimal(moneyss[i]);
          totalmoney = KqdsBigDecimal.add(totalmoney, zfbmoney);
        }
        else if (typesstrs[i].equals(this.fkfsLogic.selectSeqId4Memberfield("wxmoney")))
        {
          wxmoney = new BigDecimal(moneyss[i]);
          totalmoney = KqdsBigDecimal.add(totalmoney, wxmoney);
        }
        else if (typesstrs[i].equals(this.fkfsLogic.selectSeqId4Memberfield("mmdmoney")))
        {
          mmdmoney = new BigDecimal(moneyss[i]);
          totalmoney = KqdsBigDecimal.add(totalmoney, mmdmoney);
        }
        else if (typesstrs[i].equals(this.fkfsLogic.selectSeqId4Memberfield("bdfqmoney")))
        {
          bdfqmoney = new BigDecimal(moneyss[i]);
          totalmoney = KqdsBigDecimal.add(totalmoney, bdfqmoney);
        }
        else if (typesstrs[i].equals(this.fkfsLogic.selectSeqId4Memberfield("qtmoney")))
        {
          qtmoney = new BigDecimal(moneyss[i]);
          totalmoney = KqdsBigDecimal.add(totalmoney, qtmoney);
        }
      }
    }
    else if (types.equals(this.fkfsLogic.selectSeqId4Memberfield("xjmoney")))
    {
      xjmoney = new BigDecimal(moneys);
      totalmoney = KqdsBigDecimal.add(totalmoney, xjmoney);
    }
    else if (types.equals(this.fkfsLogic.selectSeqId4Memberfield("yhkmoney")))
    {
      yhkmoney = new BigDecimal(moneys);
      totalmoney = KqdsBigDecimal.add(totalmoney, yhkmoney);
    }
    else if (types.equals(this.fkfsLogic.selectSeqId4Memberfield("zfbmoney")))
    {
      zfbmoney = new BigDecimal(moneys);
      totalmoney = KqdsBigDecimal.add(totalmoney, zfbmoney);
    }
    else if (types.equals(this.fkfsLogic.selectSeqId4Memberfield("wxmoney")))
    {
      wxmoney = new BigDecimal(moneys);
      totalmoney = KqdsBigDecimal.add(totalmoney, wxmoney);
    }
    else if (types.equals(this.fkfsLogic.selectSeqId4Memberfield("mmdmoney")))
    {
      mmdmoney = new BigDecimal(moneys);
      totalmoney = KqdsBigDecimal.add(totalmoney, mmdmoney);
    }
    else if (types.equals(this.fkfsLogic.selectSeqId4Memberfield("bdfqmoney")))
    {
      bdfqmoney = new BigDecimal(moneys);
      totalmoney = KqdsBigDecimal.add(totalmoney, bdfqmoney);
    }
    else if (types.equals(this.fkfsLogic.selectSeqId4Memberfield("qtmoney")))
    {
      qtmoney = new BigDecimal(moneys);
      totalmoney = KqdsBigDecimal.add(totalmoney, qtmoney);
    }
    KqdsMember en = new KqdsMember();
    if (!YZUtility.isNullorEmpty(seqId))
    {
      en = (KqdsMember)this.dao.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER, seqId);
      en.setMoney(KqdsBigDecimal.add(en.getMoney(), totalmoney));
      en.setGivemoney(KqdsBigDecimal.add(en.getGivemoney(), dp.getGivemoney()));
      this.dao.updateSingleUUID(TableNameUtil.KQDS_MEMBER, en);
    }
    else
    {
      dp.setMoney(totalmoney);
      en = dp;
      dp.setOrganization(ChainUtil.getCurrentOrganization(request));
      String HYK_BINDING = SysParaUtil.getSysValueByName(request, SysParaUtil.HYK_BINDING);
      if (HYK_BINDING.equals("0"))
      {
        dp.setIcno(dp.getMemberno());
        dp.setIsbinding(Integer.valueOf(1));
      }
      this.dao.saveSingleUUID(TableNameUtil.KQDS_MEMBER, dp);
    }
    Map<String, String> map = new HashMap();
    map.put("usercode", en.getUsercode());
    List<KqdsUserdocument> user = (List)this.dao.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);
    if ((user == null) || (user.size() == 0)) {
      throw new Exception("患者数据不存在");
    }
    KqdsMemberRecord r = new KqdsMemberRecord();
    r.setSeqId(YZUtility.getUUID());
    r.setUsercode(((KqdsUserdocument)user.get(0)).getUsercode());
    r.setUsername(((KqdsUserdocument)user.get(0)).getUsername());
    r.setCardno(en.getMemberno());
    String ctime = YZUtility.getCurDateTimeStr();
    r.setCreatetime(ctime);
    r.setCreateuser(person.getSeqId());
    r.setType(recordname);
    r.setTfremark(tfremark);
    r.setContent(dp.getRemark());
    
    r.setAskperson(askperson);
    r.setRegsort(regsort);
    r.setXjmoney(xjmoney);
    r.setYhkmoney(yhkmoney);
    r.setQtmoney(qtmoney);
    r.setZfbmoney(zfbmoney);
    r.setWxmoney(wxmoney);
    r.setMmdmoney(mmdmoney);
    r.setBdfqmoney(bdfqmoney);
    r.setCmoney(totalmoney);
    r.setCgivemoney(dp.getGivemoney());
    r.setCtotal(KqdsBigDecimal.add(totalmoney, dp.getGivemoney()));
    r.setYmoney(en.getMoney());
    r.setYgivemoney(en.getGivemoney());
    r.setYtotal(KqdsBigDecimal.add(en.getMoney(), en.getGivemoney()));
    r.setOrganization(ChainUtil.getCurrentOrganization(request));
    this.dao.saveSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, r);
    
    BcjlUtil.LogBcjl(r.getSeqId(), BcjlUtil.KQDS_MEMBER_RECORD, r, TableNameUtil.KQDS_MEMBER_RECORD, request);
    czfs = new JSONObject();
    czfs.put("xjmoney", xjmoney);
    czfs.put("yhkmoney", yhkmoney);
    czfs.put("qtmoney", qtmoney);
    czfs.put("zfbmoney", zfbmoney);
    czfs.put("wxmoney", wxmoney);
    czfs.put("mmdmoney", mmdmoney);
    czfs.put("bdfqmoney", bdfqmoney);
    czfs.put("creatTime", ctime);
    czfs.put("recordId", r.getSeqId());
    return czfs;
  }
  
  public JSONObject selectMemberTongji(String table, BootStrapPage bp, Map<String, String> map, String exportFlag, YZPerson person)
    throws Exception
  {
    String nameFlag = person.getUserId();
    String temp_member1 = "temp_member1_" + nameFlag;
    String temp_member2 = "temp_member2_" + nameFlag;
    String temp_member3 = "temp_member3_" + nameFlag;
    String temp_member4 = "temp_member4_" + nameFlag;
    String temp_member5 = "temp_member5_" + nameFlag;
    String temp_member6 = "temp_member6_" + nameFlag;
    String temp_member7 = "temp_member7_" + nameFlag;
    
    int firstIndex = bp.getOffset();
    int pageSize = bp.getLimit();
    int pageNum = firstIndex / pageSize;
    
    JSONObject result = new JSONObject();
    String starttime = "";
    String endtime = "";
    if (!map.isEmpty())
    {
      if (map.containsKey("starttime")) {
        starttime = (String)map.get("starttime");
      }
      if (map.containsKey("endtime")) {
        endtime = (String)map.get("endtime");
      }
    }
    String check1 = null;
    String check2 = null;
    String check3 = null;
    String check4 = null;
    String check5 = null;
    String check6 = null;
    String check7 = null;
    if (DBUtil.isMysql())
    {
      check1 = "DROP TEMPORARY TABLE IF EXISTS " + temp_member1 + ";";
      check2 = "DROP TEMPORARY TABLE IF EXISTS " + temp_member2 + ";";
      check3 = "DROP TEMPORARY TABLE IF EXISTS " + temp_member3 + ";";
      check4 = "DROP TEMPORARY TABLE IF EXISTS " + temp_member4 + ";";
      check5 = "DROP TEMPORARY TABLE IF EXISTS " + temp_member5 + ";";
      check6 = "DROP TEMPORARY TABLE IF EXISTS " + temp_member6 + ";";
      check7 = "DROP TEMPORARY TABLE IF EXISTS " + temp_member7 + ";";
    }
    else
    {
      check1 = "IF OBJECT_ID('tempdb..##" + temp_member1 + "') is not null drop table ##" + temp_member1;
      check2 = "IF OBJECT_ID('tempdb..##" + temp_member2 + "') is not null drop table ##" + temp_member2;
      check3 = "IF OBJECT_ID('tempdb..##" + temp_member3 + "') is not null drop table ##" + temp_member3;
      check4 = "IF OBJECT_ID('tempdb..##" + temp_member4 + "') is not null drop table ##" + temp_member4;
      check5 = "IF OBJECT_ID('tempdb..##" + temp_member5 + "') is not null drop table ##" + temp_member5;
      check6 = "IF OBJECT_ID('tempdb..##" + temp_member6 + "') is not null drop table ##" + temp_member6;
      check7 = "IF OBJECT_ID('tempdb..##" + temp_member7 + "') is not null drop table ##" + temp_member7;
    }
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check1);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check2);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check3);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check4);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check5);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check6);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check7);
    
    String create1 = null;
    String create2 = null;
    String create3 = null;
    String create4 = null;
    String create5 = null;
    String create6 = null;
    String create7 = null;
    if (DBUtil.isMysql())
    {
      create1 = 
        "CREATE temporary table " + temp_member1 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50),zzmoney VARCHAR(50),zzgivemoney VARCHAR(50))";
      create2 = "CREATE temporary table " + temp_member2 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50))";
      create3 = "CREATE temporary table " + temp_member3 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50))";
      create4 = "CREATE temporary table " + temp_member4 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50))";
      create5 = "CREATE temporary table " + temp_member5 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50))";
      create6 = "CREATE temporary table " + temp_member6 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50))";
      create7 = "CREATE temporary table " + temp_member7 + 
        "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50),zzmoney VARCHAR(50),zzgivemoney VARCHAR(50))";
    }
    else
    {
      create1 = 
        "CREATE TABLE ##" + temp_member1 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50),zzmoney VARCHAR(50),zzgivemoney VARCHAR(50))";
      create2 = "CREATE TABLE ##" + temp_member2 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50))";
      create3 = "CREATE TABLE ##" + temp_member3 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50))";
      create4 = "CREATE TABLE ##" + temp_member4 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50))";
      create5 = "CREATE TABLE ##" + temp_member5 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50))";
      create6 = "CREATE TABLE ##" + temp_member6 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50))";
      create7 = "CREATE TABLE ##" + temp_member7 + 
        "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50),zzmoney VARCHAR(50),zzgivemoney VARCHAR(50))";
    }
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", create1);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", create2);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", create3);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", create4);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", create5);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", create6);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", create7);
    
    String insert1 = "";
    if (DBUtil.isMysql()) {
      insert1 = insert1 + "INSERT INTO " + temp_member1 + "(memberno,cmoney, cgivemoney,ctotal,zzmoney,zzgivemoney)";
    } else {
      insert1 = insert1 + "INSERT INTO ##" + temp_member1 + "(memberno,cmoney, cgivemoney,ctotal,zzmoney,zzgivemoney)";
    }
    insert1 = insert1 + " SELECT ";
    insert1 = insert1 + " cardno,";
    insert1 = insert1 + " sum(" + SQLUtil.castAsFloat("cmoney") + ") as cmoney,";
    insert1 = insert1 + " sum(" + SQLUtil.castAsFloat("cgivemoney") + ") as cgivemoney,";
    insert1 = insert1 + " sum(" + SQLUtil.castAsFloat("ctotal") + ") as ctotal,";
    insert1 = insert1 + " sum(" + SQLUtil.castAsFloat("zzmoney") + ") as zzmoney,";
    insert1 = insert1 + " sum(" + SQLUtil.castAsFloat("zzgivemoney") + ") as zzgivemoney";
    insert1 = insert1 + " FROM KQDS_MEMBER_RECORD dd";
    insert1 = insert1 + " where  createtime <='" + starttime + "'";
    insert1 = insert1 + " GROUP BY dd.cardno";
    
    String insert2 = "";
    if (DBUtil.isMysql()) {
      insert2 = insert2 + "INSERT INTO " + temp_member2 + "(memberno,cmoney, cgivemoney,ctotal)";
    } else {
      insert2 = insert2 + "INSERT INTO ##" + temp_member2 + "(memberno,cmoney, cgivemoney,ctotal)";
    }
    insert2 = insert2 + " SELECT ";
    insert2 = insert2 + " cardno,";
    insert2 = insert2 + " sum(" + SQLUtil.castAsFloat("cmoney") + ") as cmoney,";
    insert2 = insert2 + " sum(" + SQLUtil.castAsFloat("cgivemoney") + ") as cgivemoney,";
    insert2 = insert2 + " sum(" + SQLUtil.castAsFloat("ctotal") + ") as ctotal";
    insert2 = insert2 + " FROM KQDS_MEMBER_RECORD dd";
    insert2 = insert2 + " where  type = '开卡' ";
    if (!YZUtility.isNullorEmpty(starttime)) {
      insert2 = insert2 + " and createtime >='" + starttime + "' ";
    }
    if (!YZUtility.isNullorEmpty(endtime)) {
      insert2 = insert2 + " and createtime <='" + endtime + "' ";
    }
    insert2 = insert2 + " GROUP BY dd.cardno";
    
    String insert3 = "";
    if (DBUtil.isMysql()) {
      insert3 = insert3 + "INSERT INTO " + temp_member3 + "(memberno,cmoney,cgivemoney,ctotal)";
    } else {
      insert3 = insert3 + "INSERT INTO ##" + temp_member3 + "(memberno,cmoney,cgivemoney,ctotal)";
    }
    insert3 = insert3 + " SELECT ";
    insert3 = insert3 + " cardno,";
    insert3 = insert3 + " sum(" + SQLUtil.castAsFloat("cmoney") + ") as cmoney,";
    insert3 = insert3 + " sum(" + SQLUtil.castAsFloat("cgivemoney") + ") as cgivemoney,";
    insert3 = insert3 + " sum(" + SQLUtil.castAsFloat("ctotal") + ") as ctotal";
    insert3 = insert3 + " FROM KQDS_MEMBER_RECORD dd";
    insert3 = insert3 + " where type = '充值' ";
    if (!YZUtility.isNullorEmpty(starttime)) {
      insert3 = insert3 + " and createtime >='" + starttime + "' ";
    }
    if (!YZUtility.isNullorEmpty(endtime)) {
      insert3 = insert3 + " and createtime <='" + endtime + "' ";
    }
    insert3 = insert3 + " GROUP BY dd.cardno";
    
    String insert4 = "";
    if (DBUtil.isMysql()) {
      insert4 = insert4 + "INSERT INTO " + temp_member4 + "(memberno,cmoney,cgivemoney,ctotal)";
    } else {
      insert4 = insert4 + "INSERT INTO ##" + temp_member4 + "(memberno,cmoney,cgivemoney,ctotal)";
    }
    insert4 = insert4 + " SELECT ";
    insert4 = insert4 + " cardno,";
    insert4 = insert4 + " sum(" + SQLUtil.castAsFloat("cmoney") + ") as cmoney,";
    insert4 = insert4 + " sum(" + SQLUtil.castAsFloat("cgivemoney") + ") as cgivemoney,";
    insert4 = insert4 + " sum(" + SQLUtil.castAsFloat("ctotal") + ") as ctotal";
    insert4 = insert4 + " FROM KQDS_MEMBER_RECORD dd";
    insert4 = insert4 + " where type = '缴费' ";
    if (!YZUtility.isNullorEmpty(starttime)) {
      insert4 = insert4 + " and createtime >='" + starttime + "' ";
    }
    if (!YZUtility.isNullorEmpty(endtime)) {
      insert4 = insert4 + " and createtime <='" + endtime + "' ";
    }
    insert4 = insert4 + " GROUP BY dd.cardno";
    
    String insert5 = "";
    if (DBUtil.isMysql()) {
      insert5 = insert5 + "INSERT INTO " + temp_member5 + "(memberno,cmoney,cgivemoney,ctotal)";
    } else {
      insert5 = insert5 + "INSERT INTO ##" + temp_member5 + "(memberno,cmoney,cgivemoney,ctotal)";
    }
    insert5 = insert5 + " SELECT ";
    insert5 = insert5 + " cardno,";
    insert5 = insert5 + " sum(" + SQLUtil.castAsFloat("cmoney") + ") as cmoney,";
    insert5 = insert5 + " sum(" + SQLUtil.castAsFloat("cgivemoney") + ") as cgivemoney,";
    insert5 = insert5 + " sum(" + SQLUtil.castAsFloat("ctotal") + ") as ctotal";
    insert5 = insert5 + " FROM KQDS_MEMBER_RECORD dd";
    insert5 = insert5 + " where type = '退费' ";
    if (!YZUtility.isNullorEmpty(starttime)) {
      insert5 = insert5 + " and createtime >='" + starttime + "' ";
    }
    if (!YZUtility.isNullorEmpty(endtime)) {
      insert5 = insert5 + " and createtime <='" + endtime + "' ";
    }
    insert5 = insert5 + " GROUP BY dd.cardno";
    
    String insert6 = "";
    if (DBUtil.isMysql()) {
      insert6 = insert6 + "INSERT INTO " + temp_member6 + "(memberno,cmoney,cgivemoney)";
    } else {
      insert6 = insert6 + "INSERT INTO ##" + temp_member6 + "(memberno,cmoney,cgivemoney)";
    }
    insert6 = insert6 + " SELECT ";
    insert6 = insert6 + " cardno,";
    insert6 = insert6 + " sum(" + SQLUtil.castAsFloat("zzmoney") + ") as cmoney,";
    insert6 = insert6 + " sum(" + SQLUtil.castAsFloat("zzgivemoney") + ") as cgivemoney";
    

    insert6 = insert6 + " FROM KQDS_MEMBER_RECORD dd";
    insert6 = insert6 + " where (type = '转赠' or type = '受赠') ";
    if (!YZUtility.isNullorEmpty(starttime)) {
      insert6 = insert6 + " and createtime >='" + starttime + "' ";
    }
    if (!YZUtility.isNullorEmpty(endtime)) {
      insert6 = insert6 + " and createtime <='" + endtime + "' ";
    }
    insert6 = insert6 + " GROUP BY dd.cardno";
    
    String insert7 = "";
    if (DBUtil.isMysql()) {
      insert7 = insert7 + "INSERT INTO " + temp_member7 + "(memberno,cmoney,cgivemoney,ctotal,zzmoney,zzgivemoney)";
    } else {
      insert7 = insert7 + "INSERT INTO ##" + temp_member7 + "(memberno,cmoney,cgivemoney,ctotal,zzmoney,zzgivemoney)";
    }
    insert7 = insert7 + " SELECT ";
    insert7 = insert7 + " cardno,";
    insert7 = insert7 + " sum(" + SQLUtil.castAsFloat("cmoney") + ") as cmoney,";
    insert7 = insert7 + " sum(" + SQLUtil.castAsFloat("cgivemoney") + ") as cgivemoney,";
    insert7 = insert7 + " sum(" + SQLUtil.castAsFloat("ctotal") + ") as ctotal,";
    insert7 = insert7 + " sum(" + SQLUtil.castAsFloat("zzmoney") + ") as zzmoney,";
    insert7 = insert7 + " sum(" + SQLUtil.castAsFloat("zzgivemoney") + ") as zzgivemoney";
    insert7 = insert7 + " FROM KQDS_MEMBER_RECORD dd";
    insert7 = insert7 + " where  createtime <='" + endtime + "'";
    insert7 = insert7 + " GROUP BY dd.cardno";
    
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", insert1);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", insert2);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", insert3);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", insert4);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", insert5);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", insert6);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", insert7);
    
    StringBuffer sb = new StringBuffer();
    if (DBUtil.isMysql())
    {
      sb.append(" LEFT JOIN " + temp_member1 + " tp1 on tp1.memberno =  m.memberno ");
      sb.append(" LEFT JOIN " + temp_member2 + " tp2 on tp2.memberno =  m.memberno ");
      sb.append(" LEFT JOIN " + temp_member3 + " tp3 on tp3.memberno =  m.memberno ");
      sb.append(" LEFT JOIN " + temp_member4 + " tp4 on tp4.memberno =  m.memberno ");
      sb.append(" LEFT JOIN " + temp_member5 + " tp5 on tp5.memberno =  m.memberno ");
      sb.append(" LEFT JOIN " + temp_member6 + " tp6 on tp6.memberno =  m.memberno ");
      sb.append(" LEFT JOIN " + temp_member7 + " tp7 on tp7.memberno =  m.memberno ");
    }
    else
    {
      sb.append(" LEFT JOIN ##" + temp_member1 + " tp1 on tp1.memberno =  m.memberno ");
      sb.append(" LEFT JOIN ##" + temp_member2 + " tp2 on tp2.memberno =  m.memberno ");
      sb.append(" LEFT JOIN ##" + temp_member3 + " tp3 on tp3.memberno =  m.memberno ");
      sb.append(" LEFT JOIN ##" + temp_member4 + " tp4 on tp4.memberno =  m.memberno ");
      sb.append(" LEFT JOIN ##" + temp_member5 + " tp5 on tp5.memberno =  m.memberno ");
      sb.append(" LEFT JOIN ##" + temp_member6 + " tp6 on tp6.memberno =  m.memberno ");
      sb.append(" LEFT JOIN ##" + temp_member7 + " tp7 on tp7.memberno =  m.memberno ");
    }
    sb.append(" where 1=1 ");
    map.put("joinsql", sb.toString());
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_MEMBER + ".selectList2", map);
    
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check1);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check2);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check3);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check4);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check5);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check6);
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check7);
    
    BigDecimal tdqcmoney = BigDecimal.ZERO;BigDecimal tdqcgivemoney = BigDecimal.ZERO;BigDecimal tdqctotal = BigDecimal.ZERO;BigDecimal tdkkmoney = BigDecimal.ZERO;BigDecimal tdkkgivemoney = BigDecimal.ZERO;
    BigDecimal tdkktotal = BigDecimal.ZERO;BigDecimal tdczmoney = BigDecimal.ZERO;BigDecimal tdczgivemoney = BigDecimal.ZERO;BigDecimal tdcztotal = BigDecimal.ZERO;BigDecimal tdjfmoney = BigDecimal.ZERO;
    BigDecimal tdjfgivemoney = BigDecimal.ZERO;BigDecimal tdjftotal = BigDecimal.ZERO;BigDecimal tdtfmoney = BigDecimal.ZERO;BigDecimal tdtfgivemoney = BigDecimal.ZERO;BigDecimal tdtftotal = BigDecimal.ZERO;
    BigDecimal tdzzmoney = BigDecimal.ZERO;BigDecimal tdzzgivemoney = BigDecimal.ZERO;BigDecimal tdzztotal = BigDecimal.ZERO;BigDecimal tdmoney = BigDecimal.ZERO;BigDecimal tdgivemoney = BigDecimal.ZERO;
    BigDecimal tdtotal = BigDecimal.ZERO;
    String memberno;
    for (int i = 0; i < list.size(); i++)
    {
      JSONObject json = (JSONObject)list.get(i);
      
      memberno = json.getString("memberno");
      json.put("memberno", memberno);
      String field = json.getString("memberstatus");
      String value = "";
      if (field.equals(ConstUtil.MEMBER_STATUS_0_DESC)) {
        value = "未激活";
      } else if (field.equals(ConstUtil.MEMBER_STATUS_1_DESC)) {
        value = "正常";
      } else if (field.equals(ConstUtil.MEMBER_STATUS_2_DESC)) {
        value = "已挂失";
      } else if (field.equals(ConstUtil.MEMBER_STATUS_3_DESC)) {
        value = "已补办";
      }
      json.put("memberstatus", value);
      




      BigDecimal cmoney1 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney1")) ? "0.00" : json.getString("cmoney1"));
      BigDecimal zzmoney1 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("zzmoney1")) ? "0.00" : json.getString("zzmoney1"));
      BigDecimal cgivemoney1 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney1")) ? "0.00" : json.getString("cgivemoney1"));
      BigDecimal zzgivemoney1 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("zzgivemoney1")) ? "0.00" : json.getString("zzgivemoney1"));
      BigDecimal ctotal1 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal1")) ? "0.00" : json.getString("ctotal1"));
      json.put("qcmoney", cmoney1.add(zzmoney1));
      json.put("qcgivemoney", cgivemoney1.add(zzgivemoney1));
      json.put("qctotal", ctotal1.add(zzmoney1).add(zzgivemoney1));
      




      BigDecimal cmoney2 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney2")) ? "0.00" : json.getString("cmoney2"));
      BigDecimal cgivemoney2 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney2")) ? "0.00" : json.getString("cgivemoney2"));
      BigDecimal ctotal2 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal2")) ? "0.00" : json.getString("ctotal2"));
      json.put("kkmoney", cmoney2);
      json.put("kkgivemoney", cgivemoney2);
      json.put("kktotal", ctotal2);
      




      BigDecimal cmoney3 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney3")) ? "0.00" : json.getString("cmoney3"));
      BigDecimal cgivemoney3 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney3")) ? "0.00" : json.getString("cgivemoney3"));
      BigDecimal ctotal3 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal3")) ? "0.00" : json.getString("ctotal3"));
      json.put("czmoney", cmoney3);
      json.put("czgivemoney", cgivemoney3);
      json.put("cztotal", ctotal3);
      





      BigDecimal cmoney4 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney4")) ? "0.00" : json.getString("cmoney4"));
      BigDecimal cgivemoney4 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney4")) ? "0.00" : json.getString("cgivemoney4"));
      BigDecimal ctotal4 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal4")) ? "0.00" : json.getString("ctotal4"));
      json.put("jfmoney", cmoney4);
      json.put("jfgivemoney", cgivemoney4);
      json.put("jftotal", ctotal4);
      




      BigDecimal cmoney5 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney5")) ? "0.00" : json.getString("cmoney5"));
      BigDecimal cgivemoney5 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney5")) ? "0.00" : json.getString("cgivemoney5"));
      BigDecimal ctotal5 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal5")) ? "0.00" : json.getString("ctotal5"));
      json.put("tfmoney", cmoney5);
      json.put("tfgivemoney", cgivemoney5);
      json.put("tftotal", ctotal5);
      




      BigDecimal ctotal6 = BigDecimal.ZERO;
      BigDecimal cmoney6 = BigDecimal.ZERO;
      BigDecimal cgivemoney6 = BigDecimal.ZERO;
      if (!YZUtility.isNullorEmpty(json.getString("cmoney6")))
      {
        cmoney6 = new BigDecimal(json.getString("cmoney6"));
        ctotal6 = ctotal6.add(new BigDecimal(json.getString("cmoney6")));
      }
      if (!YZUtility.isNullorEmpty(json.getString("cgivemoney6")))
      {
        cgivemoney6 = new BigDecimal(json.getString("cgivemoney6"));
        ctotal6 = ctotal6.add(new BigDecimal(json.getString("cgivemoney6")));
      }
      json.put("zzmoney", cmoney6);
      json.put("zzgivemoney", cgivemoney6);
      json.put("zztotal", ctotal6);
      




      BigDecimal cmoney7 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney7")) ? "0.00" : json.getString("cmoney7"));
      BigDecimal zzmoney7 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("zzmoney7")) ? "0.00" : json.getString("zzmoney7"));
      BigDecimal cgivemoney7 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney7")) ? "0.00" : json.getString("cgivemoney7"));
      BigDecimal zzgivemoney7 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("zzgivemoney7")) ? "0.00" : json.getString("zzgivemoney7"));
      BigDecimal ctotal7 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal7")) ? "0.00" : json.getString("ctotal7"));
      json.put("qmmoney", cmoney7.add(zzmoney7));
      json.put("qmgivemoney", cgivemoney7.add(zzgivemoney7));
      json.put("qmtotal", ctotal7.add(zzmoney7).add(zzgivemoney7));
      if (firstIndex == 0)
      {
        tdqcmoney = tdqcmoney.add(new BigDecimal(json.getString("qcmoney")));
        tdqcgivemoney = tdqcgivemoney.add(new BigDecimal(json.getString("qcgivemoney")));
        tdqctotal = tdqctotal.add(new BigDecimal(json.getString("qctotal")));
        
        tdkkmoney = tdkkmoney.add(new BigDecimal(json.getString("kkmoney")));
        tdkkgivemoney = tdkkgivemoney.add(new BigDecimal(json.getString("kkgivemoney")));
        tdkktotal = tdkktotal.add(new BigDecimal(json.getString("kktotal")));
        
        tdczmoney = tdczmoney.add(new BigDecimal(json.getString("czmoney")));
        tdczgivemoney = tdczgivemoney.add(new BigDecimal(json.getString("czgivemoney")));
        tdcztotal = tdcztotal.add(new BigDecimal(json.getString("cztotal")));
        
        tdjfmoney = tdjfmoney.add(new BigDecimal(json.getString("jfmoney")));
        tdjfgivemoney = tdjfgivemoney.add(new BigDecimal(json.getString("jfgivemoney")));
        tdjftotal = tdjftotal.add(new BigDecimal(json.getString("jftotal")));
        
        tdtfmoney = tdtfmoney.add(new BigDecimal(json.getString("tfmoney")));
        tdtfgivemoney = tdtfgivemoney.add(new BigDecimal(json.getString("tfgivemoney")));
        tdtftotal = tdtftotal.add(new BigDecimal(json.getString("tftotal")));
        
        tdzzmoney = tdzzmoney.add(new BigDecimal(json.getString("zzmoney")));
        tdzzgivemoney = tdzzgivemoney.add(new BigDecimal(json.getString("zzgivemoney")));
        tdzztotal = tdzztotal.add(new BigDecimal(json.getString("zztotal")));
        
        tdmoney = tdmoney.add(new BigDecimal(json.getString("qmmoney")));
        tdgivemoney = tdgivemoney.add(new BigDecimal(json.getString("qmgivemoney")));
        tdtotal = tdtotal.add(new BigDecimal(json.getString("qmtotal")));
      }
    }
    if (firstIndex == 0)
    {
      result.put("tdqcmoney", tdqcmoney);
      result.put("tdqcgivemoney", tdqcgivemoney);
      result.put("tdqctotal", tdqctotal);
      result.put("tdkkmoney", tdkkmoney);
      result.put("tdkkgivemoney", tdkkgivemoney);
      result.put("tdkktotal", tdkktotal);
      result.put("tdczmoney", tdczmoney);
      result.put("tdczgivemoney", tdczgivemoney);
      result.put("tdcztotal", tdcztotal);
      result.put("tdjfmoney", tdjfmoney);
      result.put("tdjfgivemoney", tdjfgivemoney);
      result.put("tdjftotal", tdjftotal);
      result.put("tdtfmoney", tdtfmoney);
      result.put("tdtfgivemoney", tdtfgivemoney);
      result.put("tdtftotal", tdtftotal);
      result.put("tdzzmoney", tdzzmoney);
      result.put("tdzzgivemoney", tdzzgivemoney);
      result.put("tdzztotal", tdzztotal);
      result.put("tdmoney", tdmoney);
      result.put("tdgivemoney", tdgivemoney);
      result.put("tdtotal", tdtotal);
    }
    if ((exportFlag != null) && (exportFlag.equals("exportTable")))
    {
      result.put("list", list);
    }
    else
    {
      List<JSONObject> pageList = pagingByList(list, pageSize, pageNum);
      for (JSONObject json : pageList)
      {
        firstIndex++;
        json.put("rownumber", Integer.valueOf(firstIndex));
      }
      result.put("total", Integer.valueOf(list.size()));
      
      result.put("rows", pageList);
    }
    return result;
  }
  
  public List pagingByList(List list, int pagesize, int pageNo)
  {
    int totalcount = list.size();
    int pagecount = 0;
    int m = totalcount % pagesize;
    if (m > 0) {
      pagecount = totalcount / pagesize + 1;
    } else {
      pagecount = totalcount / pagesize;
    }
    List subList = null;
    if (m == 0) {
      subList = list.subList(pageNo * pagesize, pagesize * (pageNo + 1));
    } else if (pageNo + 1 == pagecount) {
      subList = list.subList(pageNo * pagesize, totalcount);
    } else {
      subList = list.subList(pageNo * pagesize, pagesize * (pageNo + 1));
    }
    return subList;
  }
  
  public JSONObject selectAllQm(String memberno, String endtime, String key1, String key2, String key3)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("memberno", memberno);
    if (!YZUtility.isNullorEmpty(endtime)) {
      map.put("endtime", endtime);
    }
    JSONObject json = new JSONObject();
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_MEMBER + ".selectAllQm", map);
    if ((list != null) && (list.size() > 0))
    {
      json = (JSONObject)list.get(0);
      
      BigDecimal cmoney = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney")) ? "0.00" : json.getString("cmoney"));
      BigDecimal zzmoney = new BigDecimal(YZUtility.isNullorEmpty(json.getString("zzmoney")) ? "0.00" : json.getString("zzmoney"));
      BigDecimal cgivemoney = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney")) ? "0.00" : json.getString("cgivemoney"));
      BigDecimal zzgivemoney = new BigDecimal(YZUtility.isNullorEmpty(json.getString("zzgivemoney")) ? "0.00" : json.getString("zzgivemoney"));
      BigDecimal ctotal = new BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal")) ? "0.00" : json.getString("ctotal"));
      
      json.put(key1, cmoney.add(zzmoney));
      json.put(key2, cgivemoney.add(zzgivemoney));
      json.put(key3, ctotal.add(zzmoney).add(zzgivemoney));
    }
    return json;
  }
  
  public JSONObject getSymoneyByUsercode(String usercode, String sftime)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("usercode", usercode);
    if (!YZUtility.isNullorEmpty(sftime)) {
      map.put("sftime", sftime);
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_MEMBER + ".getSymoneyByUsercode", map);
    JSONObject obj = new JSONObject();
    BigDecimal money = BigDecimal.ZERO;
    BigDecimal givemoney = BigDecimal.ZERO;
    for (JSONObject rs : list)
    {
      if ((!YZUtility.isNullorEmpty(rs.getString("cmoney"))) && 
        (KqdsBigDecimal.compareTo(new BigDecimal(rs.getString("cmoney")), BigDecimal.ZERO) != 0))
      {
        money = money.add(new BigDecimal(rs.getString("cmoney"))).add(new BigDecimal(rs.getString("zzmoney")));
        obj.put(ConstUtil.MEMBER_MONEY, money);
      }
      if ((!YZUtility.isNullorEmpty(rs.getString("cgivemoney"))) && 
        (KqdsBigDecimal.compareTo(new BigDecimal(rs.getString("cgivemoney")), BigDecimal.ZERO) != 0))
      {
        givemoney = givemoney.add(new BigDecimal(rs.getString("cgivemoney"))).add(new BigDecimal(rs.getString("zzgivemoney")));
        obj.put(ConstUtil.MEMBER_GIVEMONEY, givemoney);
      }
    }
    return obj;
  }
  
  public void addMember4CreateUserDocument(KqdsUserdocument user, YZPerson person, HttpServletRequest request)
    throws Exception
  {
    KqdsMember dp = new KqdsMember();
    
    dp.setSeqId(YZUtility.getUUID());
    dp.setCreatetime(YZUtility.getCurDateTimeStr());
    dp.setCreateuser(person.getSeqId());
    



    dp.setIcno(user.getUsercode());
    dp.setIsbinding(Integer.valueOf(1));
    
    dp.setMemberno(user.getUsercode());
    
    dp.setPassword(dp.getMemberno().substring(dp.getMemberno().length() - 6, dp.getMemberno().length()));
    
    String level = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.HYKFL, "预交金卡");
    dp.setMemberlevel(level);
    dp.setMemberstatus("1");
    dp.setUsername(user.getUsername());
    dp.setUsercode(user.getUsercode());
    dp.setMoney(BigDecimal.ZERO);
    dp.setGivemoney(BigDecimal.ZERO);
    dp.setDiscount(Integer.valueOf(100));
    dp.setRemark("预交金卡");
    dp.setOrganization(ChainUtil.getCurrentOrganization(request));
    this.dao.saveSingleUUID(TableNameUtil.KQDS_MEMBER, dp);
  }
  
  public void editIcno(String table)
    throws Exception
  {
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".editIcno", null);
  }
  
  public void emptyIcno(String table)
    throws Exception
  {
    this.dao.update(TableNameUtil.KQDS_MEMBER + ".emptyIcno", null);
  }
}
