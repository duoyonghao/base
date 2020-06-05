/**  
  *
  * @Title:  LcljOperateRejectRecordDao.java   
  * @Package com.hudh.lclj.dao   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年6月27日 下午2:30:34   
  * @version V1.0  
  */ 
package com.hudh.lclj.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.lclj.entity.LcljOperateRejectRecord;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  LcljOperateRejectRecordDao   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年6月27日 下午2:30:34   
  *      
  */
@Service
public class LcljOperateRejectRecordDao {
	
	@Autowired
	private DaoSupport dao;
	
	public void insertOperationNodeReject(LcljOperateRejectRecord dp) throws Exception {
		dao.save("HUDH_LCLJ_OPERATION_NODE_REJECT.insertOperationNodeReject", dp);
	}
	
	public List<JSONObject> findOperationNodeRejectByOrderNumber(String orderNumber) throws Exception {
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("orderNumber", orderNumber);
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_LCLJ_OPERATION_NODE_REJECT.findOperationNodeRejectByOrderNumber", dataMap);
		return list;
	}
}
