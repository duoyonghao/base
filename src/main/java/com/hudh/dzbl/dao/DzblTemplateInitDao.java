package com.hudh.dzbl.dao;

import com.hudh.dzbl.entity.DzblTemplate;
import com.kqds.dao.DaoSupport;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DzblTemplateInitDao
{
  @Autowired
  private DaoSupport dao;
  
  public void initDzblTemplate(List<DzblTemplate> list)
    throws Exception
  {
    this.dao.batchUpdate("HUDH_DZBL_TEMPLATE.initDzblTemplate", list);
  }
}
