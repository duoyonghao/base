package com.hudh.lclj.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hudh.lclj.dao.FlowConfigDao;
import com.hudh.lclj.dao.NodeConfigDao;
import com.hudh.lclj.entity.LcljNodeConfig;
import com.hudh.lclj.service.ILcljNodeConfigService;
import com.hudh.util.HUDHStaticVar;
import com.hudh.util.HUDHUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;
@Service
public class LcljNodeConfigServiceImpl implements ILcljNodeConfigService {
	/**
	 * 临床路径节点dao
	 */
	@Autowired
	private NodeConfigDao nodeConfigDao;
	@Override
	public void insertNodeConfig(LcljNodeConfig lcljNodeConfig) throws Exception {
		lcljNodeConfig.setId(YZUtility.getUUID());
		lcljNodeConfig.setCreatrtime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
		nodeConfigDao.insertNodeConfig(lcljNodeConfig);
	}

	@Override
	public void deleteNodeConfigByCodeAndNodeId(Map<String, String> dataMap) throws Exception {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JSONObject> findAllNodeConfigByFlowCode(Map<String, String> dataMap) throws Exception {
		List<LcljNodeConfig> list = new ArrayList<LcljNodeConfig>();
		list = nodeConfigDao.findAllNodeConfigByFlowCode(dataMap);
		return HUDHUtil.parseJsonToObjectList(JSON.toJSONString(list), JSONObject.class);
	}
	
	@Override
	public List<LcljNodeConfig> findAllNodeConfigByFlowCodeObj(Map<String, String> dataMap) throws Exception {
		List<LcljNodeConfig> list = new ArrayList<LcljNodeConfig>();
		list = nodeConfigDao.findAllNodeConfigByFlowCode(dataMap);
		return list;
	}
}
