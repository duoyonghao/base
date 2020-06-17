/**  
  *
  * @Title:  CityServiceImpl.java   
  * @Package com.hudh.area.service.impl   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月14日 下午7:27:48   
  * @version V1.0  
  */ 
package com.hudh.area.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.area.dao.CityDao;
import com.hudh.area.service.ICityService;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  CityServiceImpl   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月14日 下午7:27:48   
  *      
  */
@Service
public class CityServiceImpl implements ICityService {
	
	@Autowired
	private CityDao cityDao;

	/**   
	  * <p>Title: findCityByProviceCode</p>   
	  * <p>Description: </p>   
	  * @param proviceCode
	  * @return   
	 * @throws Exception 
	  * @see com.hudh.area.service.ICityService#findCityByProviceCode(java.lang.String)   
	  */  
	@Override
	public List<JSONObject> findCityByProviceCode(String proviceCode) throws Exception {
		// TODO Auto-generated method stub
		List<JSONObject> list = cityDao.findCityByProviceCode(proviceCode);
		return list;
	}

	/**   
	  * <p>Title: findCityByCityCode</p>   
	  * <p>Description: </p>   
	  * @param cityCode
	  * @return   
	 * @throws Exception 
	  * @see com.hudh.area.service.ICityService#findCityByCityCode(java.lang.String)   
	  */  
	@Override
	public JSONObject findCityByCityCode(String cityCode) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = cityDao.findCityByCityCode(cityCode);
		return json;
	}

}
