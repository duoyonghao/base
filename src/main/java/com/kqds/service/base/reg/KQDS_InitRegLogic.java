/**  
  *
  * @Title:  KQDS_InitRegLogic.java   
  * @Package com.kqds.service.base.reg   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年11月23日 下午4:47:40   
  * @version V1.0  
  */ 
package com.kqds.service.base.reg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsReg;

/**  
  * 
  * @ClassName:  KQDS_InitRegLogic   
  * @Description:TODO(批量导入数据)   
  * @author: 海德堡联合口腔
  * @date:   2019年11月23日 下午4:47:40   
  *      
  */
@Service
public class KQDS_InitRegLogic {
	
	@Autowired
	private DaoSupport dao;
	
	public void batchSaveReg(List<KqdsReg> regList) throws Exception {
		dao.batchUpdate("KQDS_REG.batchSaveReg", regList);
	}

}
