package com.kqds.service.base.jzmdType;

import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsJzqk;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_JzqkLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public void saveJzqk(String doctor, String regno, YZPerson person, HttpServletRequest request)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("regno", regno);
    map.put("doctor", doctor);
    this.dao.delete(TableNameUtil.KQDS_JZQK + ".deletejzqk", map);
    
    KqdsJzqk jzqk = new KqdsJzqk();
    jzqk.setSeqId(YZUtility.getUUID());
    jzqk.setRegno(regno);
    jzqk.setDoctor(doctor);
    jzqk.setCreatetime(YZUtility.getCurDateTimeStr());
    jzqk.setCreateuser(person.getSeqId());
    jzqk.setOrganization(ChainUtil.getCurrentOrganization(request));
    
    this.dao.saveSingleUUID(TableNameUtil.KQDS_JZQK, jzqk);
  }
  
  public int countJzqkByRegNo(String regseqId)
    throws Exception
  {
    int num = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_JZQK + ".countJzqkByRegNo", regseqId)).intValue();
    return num;
  }
  
  public int deleteByRegNo(String regseqId, HttpServletRequest request)
    throws Exception
  {
    return ((Integer)this.dao.delete(TableNameUtil.KQDS_JZQK + ".deletejzqkregno", regseqId)).intValue();
  }
  
  public List<JSONObject> jzFz(String usercode, String doctor)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("usercode", usercode);
    map.put("doctor", doctor);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_JZQK + ".jzFz", map);
    return list;
  }
  
  public List<JSONObject> selectJzqk(String table, YZPerson person, Map<String, String> map, String visualstaff, String organization)
    throws Exception
  {
    if (map.containsKey("usercodeorname"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", (String)map.get("usercodeorname")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", (String)map.get("usercodeorname")));
    }
    if (YZUtility.isNotNullOrEmpty(organization)) {
      map.put("organization", organization);
    }
    map.put("visualstaff", visualstaff);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_JZQK + ".selectJzqk", map);
    return list;
  }
  
  public List<JSONObject> selectJzqkByUsercodes(String usercodes)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_JZQK + ".selectJzqkByUsercodes", usercodes);
    return list;
  }
}
