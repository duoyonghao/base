/**  
  *
  * @Title:  DataAnalysisDao.java   
  * @Package com.hudh.sjtj.dao   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年9月23日 下午2:31:42   
  * @version V1.0  
  */ 
package com.hudh.sjtj.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  DataAnalysisDao   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年9月23日 下午2:31:42   
  *      
  */
@Service
public class DataAnalysisDao {
	
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 
	  * @Title: findBaseDataAskperson   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2019年9月23日 下午3:03:14
	 */
	public List<JSONObject> findBaseDataAskperson() throws Exception {
		dao.findForList("", null);
		return null;
	}
	/**
	 * 查询年
	 * <p>Title: findQuantityByAskpersonAndYear</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月3日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public JSONObject findQuantityByAskpersonAndYear(Map<String,String> map) throws Exception{
		JSONObject list=(JSONObject) dao.findForObject(TableNameUtil.KQDS_REG + ".findQuantityByAskpersonAndYear", map);
		return list;
	}
	/**
	 * 查询年
	 * <p>Title: findCJQuantityByAskpersonAndYear</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月3日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public JSONObject findCJQuantityByAskpersonAndYear(Map<String,String> map) throws Exception{
		JSONObject list=(JSONObject) dao.findForObject(TableNameUtil.KQDS_REG + ".findCJQuantityByAskpersonAndYear", map);
		return list;
	}
	/**
	 * 查询年
	 * <p>Title: findPaymoneyByYear</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月3日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findPaymoneyByYear(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL+ ".findPaymoneyByYear", map);
		return list;
	}
	
	/**
	 * 查询月的初诊、复诊、再消费等人数
	 * <p>Title: findQuantityByAskpersonAndMonth</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年9月25日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public JSONObject findQuantityByAskpersonAndMonth(Map<String,String> map) throws Exception{
		JSONObject list=(JSONObject) dao.findForObject(TableNameUtil.KQDS_REG + ".findQuantityByAskpersonAndMonth", map);
		return list;
	}
	/**
	 * 查询月的初诊、复诊、再消费等成交人数
	 * <p>Title: findCJQuantityByAskpersonAndMonth</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年9月25日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public JSONObject findCJQuantityByAskpersonAndMonth(Map<String,String> map) throws Exception{
		JSONObject list=(JSONObject) dao.findForObject(TableNameUtil.KQDS_REG + ".findCJQuantityByAskpersonAndMonth", map);
		return list;
	}
	/**
	 * 查询月的初诊、复诊、再消费等业绩
	 * <p>Title: findPaymoneyByMonth</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年9月25日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findPaymoneyByMonth(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL+ ".findPaymoneyByMonth", map);
		return list;
	}
	/**
	 * 查询日的初诊、复诊、再消费等人数
	 * <p>Title: findQuantityByAskpersonAndDay</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年9月25日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public JSONObject findQuantityByAskpersonAndDay(Map<String,String> map) throws Exception{
		JSONObject list=(JSONObject) dao.findForObject(TableNameUtil.KQDS_REG + ".findQuantityByAskpersonAndDay", map);
		return list;
	}
	/**
	 * 查询日的初诊、复诊、再消费等成交人数
	 * <p>Title: findCJQuantityByAskpersonAndDay</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年9月25日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public JSONObject findCJQuantityByAskpersonAndDay(Map<String,String> map) throws Exception{
		JSONObject list=(JSONObject) dao.findForObject(TableNameUtil.KQDS_REG + ".findCJQuantityByAskpersonAndDay", map);
		return list;
	}
	/**
	 * 查询日的初诊、复诊、再消费等业绩
	 * <p>Title: findPaymoneyByDay</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年9月25日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findPaymoneyByDay(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL+ ".findPaymoneyByDay", map);
		return list;
	}
	/**
	 * 
	 * <p>Title: findQuantityByAskpersonAndMonthInYear</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月4日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public JSONObject findQuantityByAskpersonAndMonthInYear(Map<String,String> map) throws Exception{
		JSONObject list=(JSONObject) dao.findForObject(TableNameUtil.KQDS_REG + ".findQuantityByAskpersonAndMonthInYear", map);
		return list;
	}
	/**
	 * 
	 * <p>Title: findCJQuantityByAskpersonAndMonthInYear</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月4日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public JSONObject findCJQuantityByAskpersonAndMonthInYear(Map<String,String> map) throws Exception{
		JSONObject list=(JSONObject) dao.findForObject(TableNameUtil.KQDS_REG + ".findCJQuantityByAskpersonAndMonthInYear", map);
		return list;
	}
	/**
	 * 查询所有员工年
	 * <p>Title: findAllQuantityByAskpersonAndYear</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月5日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findAllQuantityByAskpersonAndYear(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".findAllQuantityByAskpersonAndYear", map);
		return list;
	}
	/**
	 * 查询所有员工年
	 * <p>Title: findAllCJQuantityByAskpersonAndYear</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月5日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findAllCJQuantityByAskpersonAndYear(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".findAllCJQuantityByAskpersonAndYear", map);
		return list;
	}
	/**
	 * 查询所有员工年
	 * <p>Title: findAllPaymoneyByYear</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月5日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllPaymoneyByYear(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL+ ".findAllPaymoneyByYear", map);
		return list;
	}
	
	/**
	 * 查询所有员工的月的初诊、复诊、再消费等人数
	 * <p>Title: findQuantityByAskpersonAndMonth</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年9月25日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findAllQuantityByAskpersonAndMonth(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".findAllQuantityByAskpersonAndMonth", map);
		return list;
	}
	/**
	 * 查询月的初诊、复诊、再消费等成交人数
	 * <p>Title: findCJQuantityByAskpersonAndMonth</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年9月25日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findAllCJQuantityByAskpersonAndMonth(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".findAllCjQuantityByAskpersonAndMonth", map);
		return list;
	}
	/**
	 * 查询所有员工月的业绩
	 * <p>Title: findAllPaymoneyByMonth</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月5日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllPaymoneyByMonth(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL+ ".findAllPaymoneyByMonth", map);
		return list;
	}
	/**
	 * 查询员工数据的天数据
	 * <p>Title: findAllQuantityByAskpersonAndDay</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月5日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findAllQuantityByAskpersonAndDay(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".findAllQuantityByAskpersonAndDay", map);
		return list;
	}
	/**
	 * 查询员工数据的天成交数据
	 * <p>Title: findAllCJQuantityByAskpersonAndDay</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月5日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findAllCJQuantityByAskpersonAndDay(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".findAllCjQuantityByAskpersonAndDay", map);
		return list;
	}
	/**
	 * 查询员工数据的天业绩数据
	 * <p>Title: findAllPaymoneyByDay</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月5日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllPaymoneyByDay(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL+ ".findAllPaymoneyByDay", map);
		return list;
	}
	/**
	 * 根据年份查询总的业绩 syp
	  * @Title: findTotalMoneyByMonth   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dataMap
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2019年9月26日 上午11:24:24
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findTotalMoneyByMonth(Map<String, String> dataMap) throws Exception {
		List<JSONObject> json = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".findTotalMoneyByMonth", dataMap);
		return json;
	}
	
	/**
	 * 根据年份查询总的退费金额  syp
	  * @Title: findTotalRefundByMonth   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dataMap
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2019年9月26日 上午11:24:53
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findTotalRefundByMonth(Map<String, String> dataMap) throws Exception {
		List<JSONObject> json = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REFUND + ".findTotalRefundByMonth", dataMap);
		return json;
	}
	
	/**
	 * 根据年份查询总的预交金  syp
	  * @Title: findTotalPrepayByMonth   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dataMap
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2019年9月26日 上午11:25:20
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findTotalPrepayByMonth(Map<String, String> dataMap) throws Exception {
		List<JSONObject> json = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".findTotalPrepayByMonth", dataMap);
		return json;
	}
	
	/**
	 * 查询使用的总预交金 syp 2019-10-27
	  * @Title: findTotalUsePrepayByMonth   
	  * @Description: TODO(查询使用的总预交金)   
	  * @param: @param dataMap
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2019年10月27日 下午3:10:58
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findTotalUsePrepayByMonth(Map<String, String> dataMap) throws Exception {
		List<JSONObject> json = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".findTotalUsePrepayByMonth", dataMap);
		return json;
	}
	
	/**
	 * 查询退费的总预交金 syp 2019-10-27
	  * @Title: findTotalRefundPrepayByMonth   
	  * @Description: TODO(查询退费的总预交金)   
	  * @param: @param dataMap
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2019年10月27日 下午3:21:21
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findTotalRefundPrepayByMonth(Map<String, String> dataMap) throws Exception {
		List<JSONObject> json = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".findTotalRefundPrepayByMonth", dataMap);
		return json;
	}
	
	/**
	 * 根据年份查询总的减免及打折金额    syp
	  * @Title: findTotalDiscountByMonth   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dataMap
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2019年9月26日 上午11:26:34
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findTotalDiscountByMonth(Map<String, String> dataMap) throws Exception {
		List<JSONObject> json = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_PAYCOST+ ".findTotalDiscountByMonth", dataMap);
		return json;
	}
	
	/**
	 * 查询总的药品费用 syp 2019-10-27
	  * @Title: findTotalDrugsByMonth   
	  * @Description: TODO(查询总的药品费用)   
	  * @param: @param dataMap
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2019年10月27日 下午3:27:50
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findTotalDrugsByMonth(Map<String, String> dataMap) throws Exception {
		List<JSONObject> json = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".findTotalDrugsByMonth", dataMap);
		return json;
	}
	
	/**
	 * 查询总的检查项目费用 syp 2019-10-27
	  * @Title: findTotalCheckItemsByMonth   
	  * @Description: TODO(查询总的检查项目费用)   
	  * @param: @param dataMap
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2019年10月27日 下午3:34:16
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findTotalCheckItemsByMonth(Map<String, String> dataMap) throws Exception {
		List<JSONObject> json = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".findTotalCheckItemsByMonth", dataMap);
		return json;
	}
	
	/**
	 * 查询年预交金
	 * <p>Title: findAllCmoneyByYear</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月23日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllCmoneyByYear(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD+ ".findAllCmoneyByYear", map);
		return list;
	}
	/**
	 * 查询月预交金
	 * <p>Title: findAllCmoneyByMonth</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月23日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllCmoneyByMonth(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD+ ".findAllCmoneyByMonth", map);
		return list;
	}
	/**
	 * 查询天预交金
	 * <p>Title: findAllCmoneyByDay</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月23日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllCmoneyByDay(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD+ ".findAllCmoneyByDay", map);
		return list;
	}
	/**
	 * 查询基础数据预交金年
	 * <p>Title: findCmoneyByYear</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月25日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findCmoneyByYear(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD+ ".findCmoneyByYear", map);
		return list;
	}
	/**
	 * 查询基础数据预交金月
	 * <p>Title: findCmoneyByMonth</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月25日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findCmoneyByMonth(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD+ ".findCmoneyByMonth", map);
		return list;
	}
	/**
	 * 查询基础数据预交金日
	 * <p>Title: findCmoneyByDay</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月25日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findCmoneyByDay(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD+ ".findCmoneyByDay", map);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllDrugsmoneyByDay(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL+ ".findAllDrugsmoneyByDay", map);
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllDrugsmoneyByMonth(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL+ ".findAllDrugsmoneyByMonth", map);
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllDrugsmoneyByYear(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL+ ".findAllDrugsmoneyByYear", map);
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<JSONObject> findDrugsmoneyByDay(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL+ ".findDrugsmoneyByDay", map);
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<JSONObject> findDrugsmoneyByMonth(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL+ ".findDrugsmoneyByMonth", map);
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<JSONObject> findDrugsmoneyByYear(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL+ ".findDrugsmoneyByYear", map);
		return list;
	}
}
