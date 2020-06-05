/**  
  *
  * @Title:  AreaDao.java   
  * @Package com.hudh.area.dao   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月15日 上午10:50:27   
  * @version V1.0  
  */ 
package com.hudh.area.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  AreaDao   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月15日 上午10:50:27   
  *      
  */
@Service
public class AreaDao {
	
	@Autowired
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAreaByCityCode(String cityCode) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("bs_area.findAreaByCityCode", cityCode);
		return list;
	}
	
	public JSONObject findAreaByAreaCode(String areaCode) throws Exception {
		JSONObject json = (JSONObject) dao.findForObject("bs_area.findAreaByAreaCode", areaCode);
		return json;
	}

}
