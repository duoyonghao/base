package com.hudh.lclj.util;

import com.hudh.lclj.dao.SysDictDao;
import com.kqds.entity.sys.YZDict;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class SysDictUtil
{
  private SysDictDao sysDictDao;
  private static volatile SysDictUtil instance;
  
  public static SysDictUtil getInstance()
  {
    if (instance == null) {
      synchronized (SysDictUtil.class)
      {
        if (instance == null) {
          instance = new SysDictUtil();
        }
      }
    }
    return instance;
  }
  
  public Map<String, YZDict> getSysDictList()
    throws Exception
  {
    if (this.sysDictDao == null)
    {
      WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
      this.sysDictDao = ((SysDictDao)wac.getBean(SysDictDao.class));
    }
    List<YZDict> list = this.sysDictDao.findSysDictList();
    

    Map<String, YZDict> dictMap = new HashMap();
    if ((list != null) && (list.size() > 0)) {
      for (YZDict yzDict : list) {
        dictMap.put(yzDict.getSeqId(), yzDict);
      }
    }
    return dictMap;
  }
}
