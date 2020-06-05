/**  
  *
  * @Title:  IProviceService.java   
  * @Package com.hudh.area.service   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月14日 下午6:54:18   
  * @version V1.0  
  */ 
package com.hudh.area.service;

import java.util.List;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  IProviceService   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月14日 下午6:54:18   
  *      
  */
public interface IProviceService {
	
	List<JSONObject> findAll() throws Exception;
	
	JSONObject findProviceByProviceCode(String proviceCode) throws Exception;
}
