package com.kqds.service.base.case1;


import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.HudhLcljBase;

import java.util.List;

@Service
public class HUDH_lcljCaseLogic {
	@Autowired
	private DaoSupport dao;

	public void insertSelective(HudhLcljBase hudhLcljBase) throws Exception {
		dao.save("HUDH_LCLJ_BASE.insertSelective", hudhLcljBase);
	}
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectWithPage(String lcljid) throws Exception {

		List<JSONObject> hudhLcljBase = (List<JSONObject>) dao.findForList("HUDH_LCLJ_BASE.selectByPrimaryKey", lcljid);
		return hudhLcljBase;
	}
	
	public void update(HudhLcljBase hudhLcljBase) throws Exception {
		dao.update("HUDH_LCLJ_BASE.update", hudhLcljBase);
	}

	@SuppressWarnings("unchecked")
	public HudhLcljBase selectById(String id) throws Exception {

		HudhLcljBase hudhLcljBase = (HudhLcljBase) dao.findForObject("HUDH_LCLJ_BASE.selectById", id);
		return hudhLcljBase;
	}
}
