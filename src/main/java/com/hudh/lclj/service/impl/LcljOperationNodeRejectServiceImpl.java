/**  
  *
  * @Title:  LcljOperationNodeRejectServiceImpl.java   
  * @Package com.hudh.lclj.service.impl   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年6月27日 下午2:40:17   
  * @version V1.0  
  */ 
package com.hudh.lclj.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.lclj.dao.LcljOperateRejectRecordDao;
import com.hudh.lclj.entity.LcljOperateRejectRecord;
import com.hudh.lclj.service.ILcljOperationNodeRejectService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  LcljOperationNodeRejectServiceImpl   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年6月27日 下午2:40:17   
  *      
  */
@Service
public class LcljOperationNodeRejectServiceImpl implements ILcljOperationNodeRejectService {
	
	@Autowired
	private LcljOperateRejectRecordDao rejectDao;

	/**   
	  * <p>Title: insertOperationNodeReject</p>   
	  * <p>Description: </p>   
	  * @param dp   
	 * @throws Exception 
	  * @see com.hudh.lclj.service.ILcljOperationNodeRejectService#insertOperationNodeReject(com.hudh.lclj.entity.LcljOperateRejectRecord)   
	  */  
	@Override
	public void insertOperationNodeReject(LcljOperateRejectRecord dp, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		dp.setSEQ_ID(YZUtility.getUUID());
		dp.setCreatetime(YZUtility.getCurDateTimeStr());
		String organization = ChainUtil.getCurrentOrganization(request);
		YZPerson person = SessionUtil.getLoginPerson(request);
		dp.setCreateuser(person.getSeqId());
		dp.setOrganization(organization);
		rejectDao.insertOperationNodeReject(dp);
	}

	/**   
	  * <p>Title: findOperationNodeRejectByOrderNumber</p>   
	  * <p>Description: </p>   
	  * @param orderNumber
	  * @return   
	 * @throws Exception 
	  * @see com.hudh.lclj.service.ILcljOperationNodeRejectService#findOperationNodeRejectByOrderNumber(java.lang.String)   
	  */  
	@Override
	public List<JSONObject> findOperationNodeRejectByOrderNumber(String orderNumber) throws Exception {
		// TODO Auto-generated method stub
		return rejectDao.findOperationNodeRejectByOrderNumber(orderNumber);
	}

}
