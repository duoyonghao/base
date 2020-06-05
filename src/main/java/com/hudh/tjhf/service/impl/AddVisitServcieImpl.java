/**  
  *
  * @Title:  AddVisitServcieImpl.java   
  * @Package com.hudh.tjhf.service.impl   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年11月2日 上午9:06:43   
  * @version V1.0  
  */ 
package com.hudh.tjhf.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hudh.tjhf.dao.AddVisitDao;
import com.hudh.tjhf.entity.VisitPlanTemplate;
import com.hudh.tjhf.entity.VisitTemplate;
import com.hudh.tjhf.service.IAddVisitService;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZPara;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  AddVisitServcieImpl   
  * @Description:TODO(添加回访计划)   
  * @author: 海德堡联合口腔
  * @date:   2019年11月2日 上午9:06:43   
  *      
  */
@Service
public class AddVisitServcieImpl implements IAddVisitService {
	
	@Autowired
	private AddVisitDao visitDao;

	@Autowired
	private DaoSupport dao;

	/**   
	  * <p>Title: saveVisitTemalate</p>   
	  * <p>Description: </p>   
	  * @param attribute   
	 * @throws Exception 
	  * @see com.hudh.tjhf.service.IAddVisitService#saveVisitTemalate(java.util.List)   
	  */  
	@Override
	public void saveVisitTemalate(List<VisitTemplate> attribute) throws Exception {
		// TODO Auto-generated method stub
		visitDao.saveVisitTemalate(attribute);
	}

	/**   
	  * <p>Title: saveVisitPlanTemalate</p>   
	  * <p>Description: </p>   
	  * @param list   
	 * @throws Exception 
	  * @see com.hudh.tjhf.service.IAddVisitService#saveVisitPlanTemalate(java.util.List)   
	  */  
	@Override
	public void saveVisitPlanTemalate(List<VisitPlanTemplate> list) throws Exception {
		// TODO Auto-generated method stub
		visitDao.saveVisitPlanTemalate(list);
	}

	/**   
	  * <p>Title: findTemplate</p>   
	  * <p>Description: </p>   
	  * @return   
	 * @throws Exception 
	  * @see com.hudh.tjhf.service.IAddVisitService#findTemplate()   
	  */  
	@Override
	public List<VisitTemplate> findTemplate(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return visitDao.findTemalate(map);
	}

	/**   
	  * <p>Title: findvisitPlanTemplate</p>   
	  * <p>Description: </p>   
	  * @param managarId
	  * @return   
	 * @throws Exception 
	  * @see com.hudh.tjhf.service.IAddVisitService#findvisitPlanTemplate(java.lang.String)   
	  */  
	@Override
	public List<VisitTemplate> findvisitPlanTemplate(String managarId) throws Exception {
		// TODO Auto-generated method stub
		return visitDao.findvisitPlanTemplate(managarId);
	}

	@Override
	public List<JSONObject> findvisitByTime(Map<String,String> map) throws Exception {
		// TODO Auto-generated method stub
		return visitDao.findvisitByTime(map);
	}
	
	@Override
	public int findvisitByTimeNum(Map<String,String> map) throws Exception {
		// TODO Auto-generated method stub
		return visitDao.findvisitByTimeNum(map);
	}

	@Override
	public int deleteManagarPlan(String managarId) throws Exception {
		int i=visitDao.deleteManagarPlan(managarId);
		return i;
	}
	
	
	@Override
	public int deleteManagar(String managarId) throws Exception {
		int i=visitDao.deleteManagar(managarId);
		int y=visitDao.deleteManagarPlanByManagarId(managarId);
		return i;
	}
	
	@Override
	public int updateManagarStatus(VisitTemplate visit) throws Exception {
		int i=visitDao.updateManagarStatus(visit);
		return i;
	}

	/**   
	  * <p>Title: findoperator</p>   
	  * <p>Description: </p>   
	  * @param sysPosition
	  * @return
	  * @throws Exception   
	  * @see com.hudh.tjhf.service.IAddVisitService#findoperator(java.lang.String)   
	  */  
	@SuppressWarnings("unchecked")
	@Override
	public List<JSONObject> findoperator(String sysPosition) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>) dao.findForObject(TableNameUtil.SYS_PARA+".findoperator", sysPosition);
	}

	/**   
	  * <p>Title: findvisitTemplate</p>   
	  * <p>Description: </p>   
	  * @param map
	  * @return   
	 * @throws Exception 
	  * @see com.hudh.tjhf.service.IAddVisitService#findvisitTemplate(java.util.Map)   
	  */  
	@Override
	public List<JSONObject> findvisitTemplate(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return visitDao.findVisitTemalate(map);
	}
}
