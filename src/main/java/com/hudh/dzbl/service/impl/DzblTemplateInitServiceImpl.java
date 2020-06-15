package com.hudh.dzbl.service.impl;

import com.hudh.dzbl.dao.DzblTemplateInitDao;
import com.hudh.dzbl.entity.DzblTemplate;
import com.hudh.dzbl.service.IDzblTemplateInitService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DzblTemplateInitServiceImpl implements IDzblTemplateInitService {
  @Autowired
  private DzblTemplateInitDao dInitDao;
  
  public void initDzblTemplate(List<DzblTemplate> list) throws Exception {
    this.dInitDao.initDzblTemplate(list);
  }
}
