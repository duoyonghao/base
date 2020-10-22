package com.hudh.lclj.service;

import java.util.List;

import com.hudh.lclj.entity.LcljOperateDetail;
import com.hudh.lclj.entity.LcljOrder;
import com.hudh.lclj.entity.LcljOrderTrack;
import com.hudh.lclj.entity.PreoperativeVerification;

import net.sf.json.JSONObject;
public interface ILcljService {
	
	/**
	 * 保存临床路径信息（记录主订单信息）
	 * @param lcljOrder
	 * @return
	 */
	int saveLcljOrder(LcljOrder lcljOrder) throws Exception;
	
	/**
	 * 根据病例号查询临床路径信息
	 * @param blCode
	 * @return
	 */
	JSONObject findLcljOrderByBlcode(String blCode) throws Exception;
	
	/**
	 * 根据病例号查询临床路径信息
	 * @param blCode
	 * @return
	 */
	List<JSONObject> findLcljOrderByBlcodeAndStu(String blCode,String status) throws Exception;
	
	/**
	 * 根据编号查询对应的临床路径信息
	 * @param orderNumber
	 * @return
	 * @throws Exception
	 */
	 LcljOrder findLcljOrderByOrderNumber(String orderNumber) throws Exception;
	
	/**
	 * 保存临床路径跟踪信息及操作项数据
	 * @param lcljOrderTrack
	 * @throws Exception
	 */
	 void saveLcljOrderTrackInfo(LcljOrderTrack lcljOrderTrack) throws Exception;
	
	 /**
	  * 根据编号查询手术次数
	  * @param orderNumber
	  * @return
	  * @throws Exception
	  */
	 int findLcljOrderTrackByOrderNumber(String orderNumber) throws Exception;
	 
	 /**
	  * 根据编号查询手术下面的手术集合
	  * @param orderNumber
	  * @return
	  * @throws Exception
	  */
	 List<JSONObject> findLcljOrderTrackListByOrderNumber(String orderNumber) throws Exception;
	 
	 /**
	  * 记录医生临床跟踪下每次操作完的记录
	  * @param orderTrackId 临床跟踪路径id
	  * @param flowLink 当前所处临床环节
	  * @param operateName 当前操作项目的名称
	  * @param remake 添加的备注信息
	  * @throws Exception
	  */
	 void updateOperateNoteInfo(String orderTrackId,String flowLink,String operateName,String remake)  throws Exception;
	 
	 /**
	  * 获取当前病例号下有无未完成的手术
	  * @param blCode
	  * @return
	  */
	 int findHasOrderByBlcodeAndStu(String blCode) throws Exception ;
	 
	 /**
	  * 根据orderTrack表id获取一次手术的跟踪信息及操作详情
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 JSONObject findLcljOrderTrsackById(String orderTrackId) throws Exception ;
	 
	 /**
	  * 更新操作项目的状态
	  * @throws Exception 
	  */
	 void updateOperateStatus(String order_number, String operateName) throws Exception;
	 
	 /**
	  * 更新具体操作项目的状态
	  * @param orderNumber
	  * @param oprationName
	 * @throws Exception 
	  */
	 void updateOperationFlowStatus(String oprationName, String orderTrackId, String flowLink) throws Exception;
	 
	 /**
	  * 更新项目实施具体的操作状态
	 * @throws Exception 
	  */
	 void changeOperateStatus (String operateName, String flowLink, String orderTrackId) throws Exception;
	 
	 /**
	  * 更新一次完整临床路径的状态########################
	  * @param id
	 * @throws Exception 
	  */
	 void updateOrderStatus(String orderNumber) throws Exception;

	 /**
	  * 根据临床跟踪表id和当前环节获取环节下面的所有操作项
	  * @return
	  * @throws Exception
	  */
	 String findOperateByTrackIdAndLink(String orderTrackId,String flowLink, String oprationName) throws Exception;
	 
	 /**
	  * 根据临床跟踪表id获取环节下面操作项的备注信息################remake
	  * @param orderTrackId
	  * @return
	 * @throws Exception 
	  */
	 List<LcljOperateDetail> findLcljOrderImplemenRemakeByTrackId (String orderTrackId, String flowLink, String oprationName) throws Exception;

	/**   
	  * @Title: savePreoperativeVerification   
	  * @Description: TODO(术前核查单)   
	  * @param: @param pVerification      
	  * @return: void
	  * @dateTime:2019年5月28日 上午10:06:09
	  */  
	 int savePreoperativeVerification(PreoperativeVerification pVerification) throws Exception;

	/**   
	  * @Title: findPreoperativeVerification   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param lcljId
	  * @param: @return      
	  * @return: JSONObject
	  * @dateTime:2019年5月28日 上午11:02:29
	  */  
	JSONObject findPreoperativeVerification(String lcljId) throws Exception;

	/**
	 *
	 * @param pVerification
	 * @throws Exception
	 */
	void updatePreoperativeVerification(PreoperativeVerification pVerification) throws Exception;
}
