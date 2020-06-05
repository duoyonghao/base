/**  
  *
  * @Title:  SysPersonDao.java   
  * @Package com.hudh.lclj.dao   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年7月15日 下午2:01:28   
  * @version V1.0  
  */ 
package com.hudh.lclj.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZPerson;

/**  
  * 
  * @ClassName:  SysPersonDao   
  * @Description:TODO(获取所有人员项集合)   
  * @author: 海德堡联合口腔
  * @date:   2019年7月15日 下午2:01:28   
  *      
  */
@Service
public class SysPersonDao {

	@Autowired
	private DaoSupport dao;
	/**
	 * 获取所有人员项集合（用于替换seq_id对应的中文名称）
	 * @param blCode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<YZPerson> findSysPersonList() throws Exception {
		List<YZPerson> YZDictList = (List<YZPerson>) dao.findForList("SYS_PERSON.selectAllBeanList", null);
		return YZDictList;
	}
}
