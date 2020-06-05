package com.hudh.lclj.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.lclj.dao.FlowConfigDao;
import com.hudh.lclj.entity.LcljFlow;
import com.hudh.lclj.service.ILcljFlowConfigService;
import com.hudh.util.HUDHStaticVar;
import com.hudh.util.HUDHUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;
@Service
public class LcljFlowConfigServiceImpl implements ILcljFlowConfigService {
	/**
	 * 临床路径流程dao
	 */
	@Autowired
	private FlowConfigDao flowConfigDao;
	@Override
	public void insertLcljFlow(LcljFlow lcljFlow) throws Exception {
		lcljFlow.setId(YZUtility.getUUID());
		lcljFlow.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
		flowConfigDao.insertLcljFlow(lcljFlow);
	}

	@Override
	public LcljFlow findLcljFlowById(String flowCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteLcljFlowById(String flowCode) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLcljFlowById(LcljFlow lcljFlow) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<JSONObject> findAllLcljFlow(Map<String,String> dataMap) throws Exception {
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = flowConfigDao.findAllLcljFlowByParentId(dataMap);
		return list;
	}

	@Override
	public List<JSONObject> findLcljFlowByDentalJaw(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		List<JSONObject> List = flowConfigDao.findLcljFlowByDentalJaw(map);
		return List;
	}

	@Override
	public List<JSONObject> findLcljFlowInforByDentalJaw(String dentalJaw) throws Exception {
		// TODO Auto-generated method stub
		List<JSONObject> list = flowConfigDao.findLcljFlowInforByDentalJaw(dentalJaw);
		return list;
	}

}
