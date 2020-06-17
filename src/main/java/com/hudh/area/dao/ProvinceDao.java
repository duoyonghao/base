/**  
  *
  * @Title:  ProvinceDao.java   
  * @Package com.hudh.area.dao   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月14日 下午6:24:53   
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
  * @ClassName:  ProvinceDao   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月14日 下午6:24:53   
  *      
  */
@Service
public class ProvinceDao {
	
	@Autowired
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAll() throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("bs_province.findAll", null);
		return list;
	}
	
	public JSONObject findProviceByProviceCode(String proviceCode) throws Exception {
		JSONObject jsonObject = (JSONObject) dao.findForObject("bs_province.findProviceByProviceCode", proviceCode);
		return jsonObject;
	}

}
