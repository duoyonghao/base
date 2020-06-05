/**  
  *
  * @Title:  CityDao.java   
  * @Package com.hudh.area.dao   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月14日 下午7:24:39   
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
  * @ClassName:  CityDao   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月14日 下午7:24:39   
  *      
  */
@Service
public class CityDao {
	
	@Autowired
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> findCityByProviceCode(String proviceCode) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("bs_city.findCityByProviceCode", proviceCode);
		return list;
	}
	
	public JSONObject findCityByCityCode(String cityCode) throws Exception {
		JSONObject jsonObject = (JSONObject) dao.findForObject("bs_city.findCityByCityCode", cityCode);
		return jsonObject;
	}

}
