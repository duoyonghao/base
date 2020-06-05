/**  
  *
  * @Title:  IAreaService.java   
  * @Package com.hudh.area.service   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月15日 上午11:02:17   
  * @version V1.0  
  */ 
package com.hudh.area.service;

import java.util.List;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  IAreaService   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月15日 上午11:02:17   
  *      
  */
public interface IAreaService {
	
	List<JSONObject> findAreaByCityCode(String cityCode) throws Exception;
	
	JSONObject findAreaByAreaCode(String areaCode) throws Exception;
}
