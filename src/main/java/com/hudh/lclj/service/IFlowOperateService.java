package com.hudh.lclj.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
/**
 * 流程操作接口
 */
import com.hudh.lclj.entity.LcljOrderTrack;
import com.hudh.lclj.entity.LcljWorklist;
public interface IFlowOperateService {
	/**
	 * 创建流程
	 * @param lcljOrderTrack
	 * @throws Exception
	 */
	void createFlow(LcljOrderTrack lcljOrderTrack,HttpServletRequest request) throws Exception;
	
	/**
	 * 提交流程
	 * @param lcljOrderTrack
	 * @throws Exception
	 */
	void submitFlow(String orderNumber,HttpServletRequest request) throws Exception;
	
	/**
	 * 退回流程
	 * @param lcljOrderTrack
	 * @throws Exception
	 */
	void rejectFlow(String orderNumber,HttpServletRequest request) throws Exception;
	
	/**
	 * 获取对应临床路径下的流转记录
	 * @param orderNumber
	 * @param request
	 * @return
	 * @throws Exception
	 */
	List<LcljWorklist> findHadWorkList(String orderNumber,HttpServletRequest request) throws Exception;
	
	/**
	 * 根据ordernumber和nodeid查找对应的最新的操作记录获取dataid
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	LcljWorklist findHadWorkByOrderNumberAndNodeId(Map<String,String> dataMap) throws Exception;
}
