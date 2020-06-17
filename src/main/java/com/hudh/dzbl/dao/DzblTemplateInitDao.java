package com.hudh.dzbl.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.dzbl.entity.DzblTemplate;
import com.kqds.dao.DaoSupport;

@Service
public class DzblTemplateInitDao {
	
	@Autowired
	private DaoSupport dao;
	
	public void initDzblTemplate(List<DzblTemplate> list) throws Exception {
		dao.batchUpdate("HUDH_DZBL_TEMPLATE.initDzblTemplate", list);
	}
	
}
