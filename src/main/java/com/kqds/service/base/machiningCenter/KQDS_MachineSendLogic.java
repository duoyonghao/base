package com.kqds.service.base.machiningCenter;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsMachineSend;

import net.sf.json.JSONObject;

@Service
public class KQDS_MachineSendLogic {
	
	@Autowired
	private DaoSupport dao;
	
	public void saveMachineSend(KqdsMachineSend dp) throws Exception {
		dao.save("KQDS_MACHINING_SEND.saveMachineSend", dp);
	}
	
	public void updateMachineSendById(KqdsMachineSend dp) throws Exception {
		dao.update("KQDS_MACHINING_SEND.updateMachineSendById", dp);
	}
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectMachineSend(Map<String, String> dataMap) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("KQDS_MACHINING_SEND.selectMachineSend", dataMap);
		return list;
	}
}
