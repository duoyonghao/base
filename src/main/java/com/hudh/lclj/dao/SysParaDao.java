package com.hudh.lclj.dao;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZPara;
import com.kqds.entity.sys.YZPerson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysParaDao
{
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> getParaValueListByName(List<String> list, HttpServletRequest request, String organization)
    throws Exception
  {
    List<JSONObject> sysParaList = (List)this.dao.findForList("SYS_PARA.getParaValueListByName", list);
    return sysParaList;
  }
  
  public JSONObject getParaValueByName(String paraValue, HttpServletRequest request, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("paraValue", paraValue);
    map.put("organization", organization);
    return (JSONObject)this.dao.findForObject("SYS_PARA.getParaByName", map);
  }
  
  public void updateAgencyUser(YZPara yzPara)
    throws Exception
  {
    this.dao.update("SYS_PARA.updateByPrimaryKeySelective", yzPara);
  }
  
  public List<YZPerson> selectUserBySeqId(Map<String, Map<String, String>> dataMap)
    throws Exception
  {
    List<YZPerson> sysPersonList = (List)this.dao.findForList("SYS_PERSON.selectBeanListByMap", dataMap);
    return sysPersonList;
  }
  
  public List<YZPara> selectParaValueByName(Map<String, Map<String, String>> dataMap)
    throws Exception
  {
    List<YZPara> sysParaList = (List)this.dao.findForList("SYS_PARA.selectBeanListByMap", dataMap);
    return sysParaList;
  }
}
