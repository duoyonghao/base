/**  
  *
  * @Title:  KQDS_InitUserdocumentLogic.java   
  * @Package com.kqds.service.base.hzjd   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年11月23日 下午2:42:29   
  * @version V1.0  
  */ 
package com.kqds.service.base.hzjd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsUserdocument;

/**  
  * 
  * @ClassName:  KQDS_InitUserdocumentLogic   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年11月23日 下午2:42:29   
  *      
  */
@Service
public class KQDS_InitUserdocumentLogic {
	
	@Autowired
	private DaoSupport dao;
	
	public void batchSaveUserDocument(List<KqdsUserdocument> list) throws Exception{
		dao.batchUpdate("KQDS_USERDOCUMENT.batchSaveUserDocument", list);
	}

}
