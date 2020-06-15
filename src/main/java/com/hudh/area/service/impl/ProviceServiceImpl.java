/**  
  *
  * @Title:  ProviceServiceImpl.java   
  * @Package com.hudh.area.service.impl   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月14日 下午6:55:04   
  * @version V1.0  
  */ 
package com.hudh.area.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.area.dao.ProvinceDao;
import com.hudh.area.service.IProviceService;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  ProviceServiceImpl   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月14日 下午6:55:04   
  *      
  */
@Service
public class ProviceServiceImpl implements IProviceService {
	
	@Autowired
	private ProvinceDao provinceDao;

	/**   
	  * <p>Title: findAll</p>   
	  * <p>Description: </p>   
	  * @return   
	 * @throws Exception 
	  * @see IProviceService#findAll()
	  */  
	@Override
	public List<JSONObject> findAll() throws Exception {
		List<JSONObject> list = provinceDao.findAll();
		return list;
	}

	/**   
	  * <p>Title: findProviceByProviceCode</p>   
	  * <p>Description: </p>   
	  * @param proviceCode
	  * @return   
	 * @throws Exception 
	  * @see IProviceService#findProviceByProviceCode(String)
	  */  
	@Override
	public JSONObject findProviceByProviceCode(String proviceCode) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = provinceDao.findProviceByProviceCode(proviceCode);
		return json;
	}

}
