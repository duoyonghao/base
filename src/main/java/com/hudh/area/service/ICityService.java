/**  
  *
  * @Title:  ICityService.java   
  * @Package com.hudh.area.service   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月14日 下午7:27:21   
  * @version V1.0  
  */ 
package com.hudh.area.service;

import java.util.List;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  ICityService   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月14日 下午7:27:21   
  *      
  */
public interface ICityService {
	
	List<JSONObject> findCityByProviceCode(String proviceCode) throws Exception;
	
	JSONObject findCityByCityCode(String cityCode) throws Exception;
}
