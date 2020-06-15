package com.hudh.ykzz.service.impl;

import com.hudh.util.HUDHUtil;
import com.hudh.ykzz.dao.YkzzTypeDao;
import com.hudh.ykzz.entity.YkzzType;
import com.hudh.ykzz.service.IYkzzTypeService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YkzzTypeServiceImpl
  implements IYkzzTypeService
{
  @Autowired
  private YkzzTypeDao ykzzTypeDao;
  
  public void insertYkzzType(YkzzType ykzzType, HttpServletRequest request)
    throws Exception
  {
    ykzzType.setId(YZUtility.getUUID());
    YZPerson person = SessionUtil.getLoginPerson(request);
    String organization = ChainUtil.getCurrentOrganization(request);
    ykzzType.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    ykzzType.setCreator(person.getSeqId());
    ykzzType.setOrganization(organization);
    this.ykzzTypeDao.insertYkzzType(ykzzType);
  }
  
  public YkzzType findYkzzTypeById(String id)
    throws Exception
  {
    return this.ykzzTypeDao.findYkzzTypeById(id);
  }
  
  public void deleteYkzzTypeById(String id)
    throws Exception
  {
    this.ykzzTypeDao.deleteYkzzTypeById(id);
  }
  
  public void updateYkzzTypeById(YkzzType ykzzType)
    throws Exception
  {
    this.ykzzTypeDao.updateYkzzTypeById(ykzzType);
  }
  
  public List<JSONObject> findChildTypesByParentId(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = new ArrayList();
    list = this.ykzzTypeDao.findChildTypesByParentId(map);
    Collections.sort(list, new Comparator()
    {
      public int compare(JSONObject o1, JSONObject o2)
      {
        int i = Integer.valueOf(o1.getString("orderno")).intValue() - Integer.valueOf(o2.getString("orderno")).intValue();
        return i;
      }
    });
    return list;
  }
  
  public List<JSONObject> findAllTypes(String organization)
    throws Exception
  {
    List<JSONObject> list = new ArrayList();
    list = this.ykzzTypeDao.findAllTypes(organization);
    Collections.sort(list, new Comparator()
    {
      public int compare(JSONObject o1, JSONObject o2)
      {
        int i = Integer.valueOf(o1.getString("orderno")).intValue() - Integer.valueOf(o2.getString("orderno")).intValue();
        return i;
      }
    });
    return list;
  }
}
