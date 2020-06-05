/**  
  *
  * @Title:  LcljOperationNodeRemarkDao.java   
  * @Package com.hudh.lclj.dao   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月31日 上午11:01:20   
  * @version V1.0  
  */ 
package com.hudh.lclj.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.lclj.entity.LcljOperationNodeRemark;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  LcljOperationNodeRemarkDao   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月31日 上午11:01:20   
  *      
  */
@Service
public class LcljOperationNodeRemarkDao {
	
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 保存具体节点备注信息
	  * @Title: saveNodeRemark   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dp
	  * @param: @throws Exception      
	  * @return: void
	  * @dateTime:2019年8月31日 上午11:04:01
	 */
	public void saveNodeRemark(LcljOperationNodeRemark dp) throws Exception {
		dao.save("HUDH_LCLJ_OPERATION_NODE_REMARK.saveNodeRemark", dp);
	}
	
	/**
	 * 根据节点ID和临床路径编号查询备注信息
	  * @Title: findNodeRemarkByNodeId   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dataMap
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2019年8月31日 下午2:36:08
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findNodeRemarkByNodeId(Map<String, String> dataMap) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_LCLJ_OPERATION_NODE_REMARK.findNodeRemarkByNodeId", dataMap);
		return list;
	}

}
