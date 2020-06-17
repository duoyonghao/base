/**  
  *
  * @Title:  StreetServiceImpl.java   
  * @Package com.hudh.area.service.impl   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月15日 上午11:24:19   
  * @version V1.0  
  */ 
package com.hudh.area.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.area.dao.StreetDao;
import com.hudh.area.service.IStreetService;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  StreetServiceImpl   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月15日 上午11:24:19   
  *      
  */
@Service
public class StreetServiceImpl implements IStreetService {
	
	@Autowired
	private StreetDao streetDao;

	/**   
	  * <p>Title: findStreetByAreaCode</p>   
	  * <p>Description: </p>   
	  * @param areaCode
	  * @return   
	 * @throws Exception 
	  * @see com.hudh.area.service.IStreetService#findStreetByAreaCode(java.lang.String)   
	  */  
	@Override
	public List<JSONObject> findStreetByAreaCode(String areaCode) throws Exception {
		// TODO Auto-generated method stub
	 	List<JSONObject> list = streetDao.findStreetByAreaCode(areaCode);
		return list;
	}

	/**   
	  * <p>Title: findStreetByTownCode</p>   
	  * <p>Description: </p>   
	  * @param townCode
	  * @return   
	 * @throws Exception 
	  * @see com.hudh.area.service.IStreetService#findStreetByTownCode(java.lang.String)   
	  */  
	@Override
	public JSONObject findStreetByTownCode(String townCode) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = streetDao.findStreetByTownCode(townCode);
		return json;
	}

}
