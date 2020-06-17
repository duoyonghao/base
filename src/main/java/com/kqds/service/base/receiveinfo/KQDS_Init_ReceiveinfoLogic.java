/**  
  *
  * @Title:  KQDS_Init_ReceiveinfoLogic.java   
  * @Package com.kqds.service.base.receiveinfo   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年11月26日 上午11:06:06   
  * @version V1.0  
  */ 
package com.kqds.service.base.receiveinfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsReceiveinfo;

/**  
  * 
  * @ClassName:  KQDS_Init_ReceiveinfoLogic   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年11月26日 上午11:06:06   
  *      
  */
@Service
public class KQDS_Init_ReceiveinfoLogic {
	
	@Autowired
	private DaoSupport dao;
	
	public void batchSaveReceiveinfo(List<KqdsReceiveinfo> receList) throws Exception {
		dao.batchUpdate("KQDS_RECEIVEINFO.batchSaveReceiveinfo", receList);
	}

}
