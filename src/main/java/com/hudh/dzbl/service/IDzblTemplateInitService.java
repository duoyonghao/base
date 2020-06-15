package com.hudh.dzbl.service;

import com.hudh.dzbl.entity.DzblTemplate;
import java.util.List;

public abstract interface IDzblTemplateInitService
{
  public abstract void initDzblTemplate(List<DzblTemplate> paramList)
    throws Exception;
}
