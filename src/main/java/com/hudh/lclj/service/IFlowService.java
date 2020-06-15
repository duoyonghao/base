package com.hudh.lclj.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hudh.lclj.entity.LcljNodeConfig;
import com.hudh.lclj.entity.LcljOptRecode;
import com.hudh.lclj.entity.LcljOrderTrack;
import com.hudh.lclj.entity.OperatingRecord;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPara;
import com.kqds.entity.sys.YZPerson;

import net.sf.json.JSONObject;
public interface IFlowService {
	
	/**
	 * 保存临床路径跟踪信息及操作项数据
	 * @param lcljOrderTrack
	 * @throws Exception
	 */
	 void saveLcljOrderTrackInfo(LcljOrderTrack lcljOrderTrack, String id,HttpServletRequest request) throws Exception;
	
	 /**
	  * 点击下一步将流程步骤更新
	  * @param orderNumber
	  */
	 void gotoNextNode(String orderNumber) throws Exception;
	 
	 /**
	  * 保存备注信息
	  * @param lcljNode
	  */
	 void saveOptRecode(LcljOptRecode lcljOptRecode) throws Exception;
	 
	 /**
	  * 获取备注信息列表
	  * @return
	  */
	 List<JSONObject> findOptRecodeList(String orderNumber,String searchFlowink) throws Exception;
	 
	 /**
	  * 根据orderNumber获取手术及患者信息
	  * 并判断当前节点是否超期，如果超期修改对应节点状态
	  * @param orderNumber
	  * @return
	  * @throws Exception
	  */
	 JSONObject findOrderTrackInfo(String orderNumber) throws Exception;
	 
	 /**
	  * 根据订单编号查询患者的信息
	  * @param orderNumber
	  * @return
	 * @throws Exception 
	  */
	 JSONObject findOrderTrackInforByOrderNumber(YZPerson person, BootStrapPage bp, Map<String, String> map,String organization, JSONObject json) throws Exception;
	 
	 /**
	  * 根据配置的para_name找临床路径管理员
	  * @return
	  * @throws Exception
	  */
	 JSONObject findLcljAdmin(HttpServletRequest request) throws Exception;
	 
	 /**
	  * 根据配置的para_name找临床路径管理员或者管理员设置的代办人
	  * @return
	  * @throws Exception
	  */
	 List<JSONObject> findLcljAdminOrAgency(HttpServletRequest request) throws Exception;
	 
	 /**
	  * 更新代办人
	  */
	 void updateAgencyUser(YZPara yzPara) throws Exception ;
	 
	 /**
	  * 跟新备注信息表状态
	  */
	 void updateRemarkStus(String id,String status) throws Exception ;
	 
	 /**
	  * 查找患者下的所有挂号信息按时间倒叙排列
	  */
	 List<KqdsReg> findRegListByBlcode(String orderNumber) throws Exception;
	 
	 /**
	  * 根据条件精确查询临床路径患者信息
	  * @return
	 * @throws Exception 
	  */
	 JSONObject findOrderTrackInforByConditionQuery(YZPerson person, Map<String, String> map, String organization) throws Exception;
	 /**
	  * 点击退回将流程退回上一步
	  * @param orderNumber
	  */
	 void reject(String orderNumber) throws Exception;
	 
	 /**
	  * 更新手术信息
	  * @param dataMap
	  */
	 public void updateOrderTrack(Map<String,String> dataMap) throws Exception;
	 
	 /**
	  * 更新临床路径节点信息
	  * @param dataMap
	  * @throws Exception
	  */
	 void updateOrderTrackNodes(Map<String,String> dataMap,String flowCode,String type,String dentalJaw,LcljOrderTrack lcljOrderTrack,HttpServletRequest request) throws Exception;
	 
	 /**
	  * 根据id更新手术数据信息
	  * @param dataMap
	  * @throws Exception
     */
	 void updateOrderTrackById(Map<String,String> dataMap) throws Exception;
	 
	 /**
	  * 根据id删除手术信息
	  * @param id
	  * @throws Exception
	  */
	 void deleteOrderTrackInforById(String id) throws Exception;
	 
	 /**
	  * 根据id查询手术跟踪信息
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 LcljOrderTrack findOrderTrackInforById(String id) throws Exception;
	 
	 /**
	  * 改变临床路径流程植骨状态
	  * @param lcljOrderTrack
	  * @throws Exception
	  */
	 void changeLcljOrderTrackBoneStatus(LcljOrderTrack lcljOrderTrack, String id) throws Exception;
	 
	 /**
	  * 根据id查询手术信息
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 JSONObject findLcljOrderTrsackById(String id) throws Exception;
	 
	 /**
	  * 根据id查询手术信息
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 LcljOrderTrack findLcljOrderTrsackByseqId(String id) throws Exception;
	 
	 /**
	  * 根据id更新临床路径状态
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 void updateLcljOrderTrackIsobsolete(String id, HttpServletRequest request) throws Exception;
	 
	 /**
	  * 根据id更新手术流程数据信息
	  * @param id
	  * @throws Exception
	  */
	 void updateLcljOrderTrackById(LcljOrderTrack lcljOrderTrack) throws Exception;

	/**   
	  * @Title: editToothBit   
	  * @Description: TODO(临床路径编辑牙位图)   
	  * @param: @param lTrack
	  * @param: @return      
	  * @return: int
	 * @throws Exception 
	  * @dateTime:2019年7月25日 下午3:15:18
	  */  
	 Integer editToothBit(LcljOrderTrack lTrack) throws Exception;

	/**   
	  * @Title: saveOperatingRecord   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param oRecord      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年7月26日 下午7:18:33
	  */  
	void saveOperatingRecord(OperatingRecord oRecord) throws Exception;
	
	JSONObject findPatientInformation(String usercode,String status,String id,String order_number)throws Exception;
	
	List<LcljNodeConfig> findNodeName() throws Exception;
}
