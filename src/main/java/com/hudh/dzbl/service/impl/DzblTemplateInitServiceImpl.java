package com.hudh.dzbl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.dzbl.dao.DzblTemplateInitDao;
import com.hudh.dzbl.entity.DzblTemplate;
import com.hudh.dzbl.service.IDzblTemplateInitService;

@Service
public class DzblTemplateInitServiceImpl implements IDzblTemplateInitService {
	
	@Autowired
	private DzblTemplateInitDao dInitDao;

	@Override
	public void initDzblTemplate(List<DzblTemplate> list) throws Exception {
		// TODO Auto-generated method stub
		dInitDao.initDzblTemplate(list);
	}

}
