package com.hudh.lclj.util;

import com.hudh.lclj.dao.SysPersonDao;
import com.kqds.entity.sys.YZPerson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class SysPersonUtil
{
  private SysPersonDao sysPersonDao;
  private static volatile SysPersonUtil instance;
  
  public static SysPersonUtil getInstance()
  {
    if (instance == null) {
      synchronized (SysPersonUtil.class)
      {
        if (instance == null) {
          instance = new SysPersonUtil();
        }
      }
    }
    return instance;
  }
  
  public Map<String, YZPerson> getSysPersonList()
    throws Exception
  {
    if (this.sysPersonDao == null)
    {
      WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
      this.sysPersonDao = ((SysPersonDao)wac.getBean(SysPersonDao.class));
    }
    List<YZPerson> list = this.sysPersonDao.findSysPersonList();
    

    Map<String, YZPerson> personMap = new HashMap();
    if ((list != null) && (list.size() > 0)) {
      for (YZPerson yzPerson : list) {
        personMap.put(yzPerson.getSeqId(), yzPerson);
      }
    }
    return personMap;
  }
}
