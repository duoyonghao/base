/**  
  *
  * @Title:  StreetDao.java   
  * @Package com.hudh.area.dao   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月15日 上午11:21:20   
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
  * @ClassName:  StreetDao   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月15日 上午11:21:20   
  *      
  */
@Service
public class StreetDao {
	
	@Autowired
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> findStreetByAreaCode(String areaCode) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("bs_street.findStreetByAreaCode", areaCode);
		return list;
	}
	
	public JSONObject findStreetByTownCode(String townCode) throws Exception {
		JSONObject json = (JSONObject) dao.findForObject("bs_street.findStreetByTownCode", townCode);
		return json;
	}

}
