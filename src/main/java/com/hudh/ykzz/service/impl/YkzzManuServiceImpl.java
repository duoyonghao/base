package com.hudh.ykzz.service.impl;

import com.hudh.util.HUDHUtil;
import com.hudh.ykzz.dao.YkzzManuDao;
import com.hudh.ykzz.entity.YkzzManu;
import com.hudh.ykzz.service.IYkzzManuService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YkzzManuServiceImpl
  implements IYkzzManuService
{
  @Autowired
  private YkzzManuDao ykzzManuDao;
  
  public void insertYkzzManu(YkzzManu ykzzManu, HttpServletRequest request)
    throws Exception
  {
    ykzzManu.setId(YZUtility.getUUID());
    YZPerson person = SessionUtil.getLoginPerson(request);
    String organization = ChainUtil.getCurrentOrganization(request);
    ykzzManu.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    ykzzManu.setCreator(person.getSeqId());
    ykzzManu.setOrganization(organization);
    this.ykzzManuDao.insertYkzzManu(ykzzManu);
  }
  
  public YkzzManu findYkzzManuById(String id)
    throws Exception
  {
    return this.ykzzManuDao.findYkzzManuById(id);
  }
  
  public void deleteYkzzManuById(String id)
    throws Exception
  {
    this.ykzzManuDao.deleteYkzzManuById(id);
  }
  
  public void updateYkzzManuById(YkzzManu ykzzManu)
    throws Exception
  {
    this.ykzzManuDao.updateYkzzManuById(ykzzManu);
  }
  
  public List<JSONObject> findAllManu(String organization)
    throws Exception
  {
    List<JSONObject> list = new ArrayList();
    list = this.ykzzManuDao.findAllManu(organization);
    return list;
  }
  
  public JSONObject findManuByCode(String manuCode)
    throws Exception
  {
    JSONObject jo = new JSONObject();
    jo = this.ykzzManuDao.findManuByCode(manuCode);
    return jo;
  }
}
