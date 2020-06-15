package com.hudh.lclj.dao;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZDict;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysDictDao
{
  @Autowired
  private DaoSupport dao;
  
  public List<YZDict> findSysDictList()
    throws Exception
  {
    List<YZDict> YZDictList = (List)this.dao.findForList("SYS_DICT.selectAllBeanList", null);
    return YZDictList;
  }
}
