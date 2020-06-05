/**  
  *
  * @Title:  LcljOperationNodeRemarkServiceImpl.java   
  * @Package com.hudh.lclj.service.impl   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月31日 上午11:06:38   
  * @version V1.0  
  */ 
package com.hudh.lclj.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.lclj.dao.LcljOperationNodeRemarkDao;
import com.hudh.lclj.entity.LcljOperationNodeRemark;
import com.hudh.lclj.service.ILcljOperationNodeRemarkService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  LcljOperationNodeRemarkServiceImpl   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月31日 上午11:06:38   
  *      
  */
@Service
public class LcljOperationNodeRemarkServiceImpl implements ILcljOperationNodeRemarkService {
	
	@Autowired
	private LcljOperationNodeRemarkDao remarkDao;

	/**   
	  * <p>Title: saveNodeRemark</p>   
	  * <p>Description: </p>   
	  * @param dp   
	 * @throws Exception 
	  * @see com.hudh.lclj.service.ILcljOperationNodeRemarkService#saveNodeRemark(com.hudh.lclj.entity.LcljOperationNodeRemark)   
	  */  
	@Override
	public void saveNodeRemark(LcljOperationNodeRemark dp, HttpServletRequest request) throws Exception {
		String lcljId = request.getParameter("");
		String order_number = request.getParameter("order_number");
		String nodeName = request.getParameter("nodeName");
		String nodeId = request.getParameter("nodeId");
		String remark = request.getParameter("remark");
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request);
		dp.setOrganization(organization);
		dp.setCreateuser(person.getSeqId());
		dp.setCreatetime(YZUtility.getCurDateTimeStr());
		dp.setSeqId(YZUtility.getUUID());
		dp.setLcljId(lcljId);
		dp.setNodeName(nodeName);
		dp.setNodeId(nodeId);
		dp.setOrder_number(order_number);
		dp.setRemark(remark);
		// TODO Auto-generated method stub
		remarkDao.saveNodeRemark(dp);
	}

	/**   
	  * <p>Title: findNodeRemarkByNodeId</p>   
	  * <p>Description: </p>   
	  * @param dataMap
	  * @return   
	 * @throws Exception 
	  * @see com.hudh.lclj.service.ILcljOperationNodeRemarkService#findNodeRemarkByNodeId(java.util.Map)   
	  */  
	@Override
	public List<JSONObject> findNodeRemarkByNodeId(Map<String, String> dataMap) throws Exception {
		// TODO Auto-generated method stub
		List<JSONObject> list = remarkDao.findNodeRemarkByNodeId(dataMap);
		return list;
	}

}
