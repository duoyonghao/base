/**  
  *
  * @Title:  LcljOrderTrackDeleteServiceImpl.java   
  * @Package com.hudh.lclj.service.impl   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年9月3日 上午11:06:03   
  * @version V1.0  
  */ 
package com.hudh.lclj.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.lclj.dao.LcljOrderTrackDeleteDao;
import com.hudh.lclj.entity.LcljOrderTrackDeleteRecord;
import com.hudh.lclj.service.ILcljOrderTrackDeleteService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

/**  
  * 
  * @ClassName:  LcljOrderTrackDeleteServiceImpl   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年9月3日 上午11:06:03   
  *      
  */
@Service
public class LcljOrderTrackDeleteServiceImpl implements ILcljOrderTrackDeleteService {
	
	@Autowired
	private LcljOrderTrackDeleteDao orderTrackDeleteDao;

	/**   
	  * <p>Title: save</p>   
	  * <p>Description: </p>   
	  * @param dp   
	 * @throws Exception 
	  * @see com.hudh.lclj.service.ILcljOrderTrackDeleteService#save(com.hudh.lclj.entity.LcljOrderTrackDeleteRecord)   
	  */  
	@Override
	public void save(LcljOrderTrackDeleteRecord dp, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		String order_number = request.getParameter("order_number");
		String lcljId = request.getParameter("lcljId");
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request);
		dp.setSeqId(YZUtility.getUUID());
		dp.setCreatetime(YZUtility.getCurDateTimeStr());
		dp.setCreateuser(person.getSeqId());
		dp.setOrganization(organization);
		dp.setLcljId(lcljId);
		dp.setOrder_number(order_number);
		orderTrackDeleteDao.save(dp);
	}

}
