/**  
  *
  * @Title:  LcljOrderTrackDeleteDao.java   
  * @Package com.hudh.lclj.dao   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年9月3日 上午11:00:18   
  * @version V1.0  
  */ 
package com.hudh.lclj.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.lclj.entity.LcljOrderTrackDeleteRecord;
import com.kqds.dao.DaoSupport;

/**  
  * 
  * @ClassName:  LcljOrderTrackDeleteDao   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年9月3日 上午11:00:18   
  *      
  */
@Service
public class LcljOrderTrackDeleteDao {
	
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 保存信息
	  * @Title: save   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dp
	  * @param: @throws Exception      
	  * @return: void
	  * @dateTime:2019年9月3日 上午11:04:42
	 */
	public void save(LcljOrderTrackDeleteRecord dp) throws Exception {
		dao.save("HUDH_LCLJ_ORDERTRACK_DELETE_RECORD.save", dp);
	}

}
