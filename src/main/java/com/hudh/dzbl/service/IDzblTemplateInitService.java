package com.hudh.dzbl.service;

import com.hudh.dzbl.entity.DzblTemplate;
import java.util.List;

public interface IDzblTemplateInitService {
  void initDzblTemplate(List<DzblTemplate> paramList) throws Exception;
}
