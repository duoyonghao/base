/**  
  *
  * @Title:  AreaServiceImpl.java   
  * @Package com.hudh.area.service.impl   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月15日 上午11:02:38   
  * @version V1.0  
  */ 
package com.hudh.area.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.area.dao.AreaDao;
import com.hudh.area.service.IAreaService;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  AreaServiceImpl   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月15日 上午11:02:38   
  *      
  */
@Service
public class AreaServiceImpl implements IAreaService {
	
	@Autowired
	private AreaDao areaDao;

	/**   
	  * <p>Title: findAreaByCityCode</p>   
	  * <p>Description: </p>   
	  * @param cityCode
	  * @return   
	 * @throws Exception 
	  * @see com.hudh.area.service.IAreaService#findAreaByCityCode(java.lang.String)   
	  */  
	@Override
	public List<JSONObject> findAreaByCityCode(String cityCode) throws Exception {
		List<JSONObject> list = areaDao.findAreaByCityCode(cityCode);
		return list;
	}

	/**   
	  * <p>Title: findAreaByAreaCode</p>   
	  * <p>Description: </p>   
	  * @param areaCode
	  * @return   
	 * @throws Exception 
	  * @see com.hudh.area.service.IAreaService#findAreaByAreaCode(java.lang.String)   
	  */  
	@Override
	public JSONObject findAreaByAreaCode(String areaCode) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = areaDao.findAreaByAreaCode(areaCode);
		return json;
	}

}
