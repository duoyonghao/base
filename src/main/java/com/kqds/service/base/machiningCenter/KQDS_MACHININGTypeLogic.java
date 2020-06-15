/**  
  *
  * @Title:  KQDS_MACHININGTypeLogic.java   
  * @Package com.kqds.service.base.machiningCenter   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年12月14日 上午10:33:45   
  * @version V1.0  
  */ 
package com.kqds.service.base.machiningCenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  KQDS_MACHININGTypeLogic   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年12月14日 上午10:33:45   
  *      
  */
@Service
public class KQDS_MACHININGTypeLogic extends BaseLogic{

	private static Logger logger = LoggerFactory.getLogger(KQDS_MACHININGTypeLogic.class);
	@Autowired
	private DaoSupport dao;
	/**   
	  * @Title: getListAll   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param:       
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年12月5日 下午5:26:38
	  */  
	@SuppressWarnings("unchecked")
	public List<JSONObject> getListAll(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>)dao.findForList(TableNameUtil.KQDS_MACHINING_TYPE+".getListAll", map);
	}
	
	/**
	  * @Title: getCategory   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2019年12月16日 下午2:41:24
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getCategory(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>)dao.findForList(TableNameUtil.KQDS_MACHINING_TYPE+".getCategory", map);
	}

	/**   
	  * @Title: delPrimaryBySeqId   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param seqId      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年12月19日 上午10:51:22
	  */  
	public void delPrimaryBySeqId(String seqId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("seqId", seqId);
		dao.delete(TableNameUtil.KQDS_MACHINING_TYPE+".delByPrimaryBySeqId", dataMap);
	}

	/**   
	  * @Title: findnextType   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map
	  * @param: @return      
	  * @return: List<JSONObject>
	 * @throws Exception 
	  * @dateTime:2020年1月18日 下午3:03:29
	  */  
	@SuppressWarnings("unchecked")
	public List<JSONObject> findnextType(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>)dao.findForList(TableNameUtil.KQDS_MACHINING_TYPE+".findnextType", map);
	}
}
