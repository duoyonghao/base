/**  
  *
  * @Title:  KQDS_Init_Net_OrderLogic.java   
  * @Package com.kqds.service.base.netOrder   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年11月25日 下午6:32:38   
  * @version V1.0  
  */ 
package com.kqds.service.base.netOrder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsNetOrder;

/**  
  * 
  * @ClassName:  KQDS_Init_Net_OrderLogic   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年11月25日 下午6:32:38   
  *      
  */
@Service
public class KQDS_Init_Net_OrderLogic {
	
	@Autowired
	private DaoSupport dao;
	
	public void batchSaveNetOrder(List<KqdsNetOrder> list) throws Exception {
		dao.batchUpdate("KQDS_NET_ORDER.batchSaveNetOrder", list);
	}
}
