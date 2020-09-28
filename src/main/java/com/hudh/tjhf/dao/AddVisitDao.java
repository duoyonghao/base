/**  
  *
  * @Title:  AddVisitDao.java   
  * @Package com.hudh.tjhf.dao   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年11月2日 上午9:07:28   
  * @version V1.0  
  */ 
package com.hudh.tjhf.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.tjhf.entity.VisitPlanTemplate;
import com.hudh.tjhf.entity.VisitTemplate;
import com.kqds.dao.DaoSupport;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  AddVisitDao   
  * @Description:TODO(添加回访计划)   
  * @author: 海德堡联合口腔
  * @date:   2019年11月2日 上午9:07:28   
  *      
  */
@Service
public class AddVisitDao {
	

	@Autowired
	private DaoSupport dao;
	/**   
	  * @Title: saveVisitTemalate   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param attribute      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年11月2日 下午2:57:19
	  */  
	public void saveVisitTemalate(List<VisitTemplate> VisitTemplatelist) throws Exception {
		// TODO Auto-generated method stub
		dao.batchUpdate(TableNameUtil.KQDS_VISIT_PLAN+".saveplanTemplate", VisitTemplatelist);
	}

	/**   
	  * @Title: saveVisitPlanTemalate   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param list      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年11月2日 下午4:14:50
	  */  
	public void saveVisitPlanTemalate(List<VisitPlanTemplate> list) throws Exception{
		dao.batchUpdate(TableNameUtil.KQDS_VISIT_PLAN_TEMPLATE+".saveplanTemplate", list);
	}

	/**   
	  * @param map 
	 * @Title: findVisitTemalat   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @return      
	  * @return: List<VisitTemplate>
	 * @throws Exception 
	  * @dateTime:2019年11月2日 下午4:29:04
	  */  
	@SuppressWarnings("unchecked")
	public List<VisitTemplate> findTemalate(Map<String, String> map) throws Exception{
		return (List<VisitTemplate>) dao.findForList(TableNameUtil.KQDS_VISIT_PLAN+".findTemalate", map);
	}

	/**   
	  * @Title: findvisitPlanTemplate   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param managarId
	  * @param: @return      
	  * @return: List<VisitTemplate>
	 * @throws Exception 
	  * @dateTime:2019年11月2日 下午4:36:25
	  */  
	@SuppressWarnings("unchecked")
	public List<VisitTemplate> findvisitPlanTemplate(String managarId) throws Exception{
		return (List<VisitTemplate>) dao.findForList(TableNameUtil.KQDS_VISIT_PLAN_TEMPLATE+".findVisitPlanTemalate", managarId);
		
	}
	/**
	 * 查询回访
	 * <p>Title: findvisitByTime</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年11月6日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findvisitByTime(Map<String,String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>) dao.findForList("KQDS_VISIT.findvisitByTime", map);
	}
	/**
	 * 查询回访条数
	 * <p>Title: findvisitByTimeNum</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年11月6日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int  findvisitByTimeNum(Map<String,String> map) throws Exception {
		// TODO Auto-generated method stub
		return (int) dao.findForObject("KQDS_VISIT.findvisitByTimeNum", map);
	}
	
	@SuppressWarnings("unchecked")
	public int  deleteManagarPlan(String seqids) throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		map.put("seqids", seqids);
		return (int) dao.delete(TableNameUtil.KQDS_VISIT_PLAN_TEMPLATE+".deleteManagarPlan", map);
	}
	
	@SuppressWarnings("unchecked")
	public int  deleteManagar(String seqids) throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		map.put("seqids", seqids);
		return (int) dao.delete(TableNameUtil.KQDS_VISIT_PLAN+".deleteManagar", map);
	}
	
	@SuppressWarnings("unchecked")
	public int  deleteManagarPlanByManagarId(String seqids) throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		map.put("seqids", seqids);
		return (int) dao.delete(TableNameUtil.KQDS_VISIT_PLAN_TEMPLATE+".deleteManagarPlanByManagarId", map);
	}
	
	@SuppressWarnings("unchecked")
	public int  updateManagarStatus(VisitTemplate visit) throws Exception {
		
		return (int) dao.update(TableNameUtil.KQDS_VISIT_PLAN+".updateTemplateStatus", visit);
	}

	/**   
	  * @Title: findVisitTemalate   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map
	  * @param: @return      
	  * @return: List<VisitTemplate>
	 * @throws Exception 
	  * @dateTime:2019年11月11日 上午10:22:12
	  */  
	@SuppressWarnings("unchecked")
	public List<JSONObject> findVisitTemalate(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_VISIT_PLAN+".findVisitTemalate", map);
	}
}
