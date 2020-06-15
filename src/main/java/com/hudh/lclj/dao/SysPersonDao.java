package com.hudh.lclj.dao;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZPerson;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysPersonDao
{
  @Autowired
  private DaoSupport dao;
  
  public List<YZPerson> findSysPersonList()
    throws Exception
  {
    List<YZPerson> YZDictList = (List)this.dao.findForList("SYS_PERSON.selectAllBeanList", null);
    return YZDictList;
  }
}
