/**  
  *
  * @Title:  DzblDao.java   
  * @Package com.hudh.zzbl.dao   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年5月6日 下午3:01:53   
  * @version V1.0  
  */ 
package com.hudh.zzbl.dao;


import com.hudh.zzbl.entity.ZzblAdvice;
import com.kqds.dao.DaoSupport;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**  
  * 
  * @ClassName:  DzblDao   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年5月6日 下午3:01:53   
  *      
  */
@Service
public class AdviceDao {

	@Autowired
	  private DaoSupport dao;
	  
	/**
	  * @Title: saveCaseHistory   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param adviceNote
	  * @param: @throws Exception      
	  * @return: void
	 */
	  public void saveCaseHistory(ZzblAdvice zzblAdvice)
	    throws Exception
	  {
	    dao.save("HUDH_ZZBL_ADVICE.saveCaseHistory", zzblAdvice);
	  }
	  
	  /**
	    * @Title: findCaseHistoryById   
	    * @Description: TODO(这里用一句话描述这个方法的作用)   
	    * @param: @param id
	    * @param: @return
	    * @param: @throws Exception      
	    * @return: JSONObject
	   */
	  @SuppressWarnings("unchecked")
	public  List<JSONObject> findCaseHistoryById(String id)
	    throws Exception
	  {
	  	List<JSONObject> caseHistory= (List<JSONObject>)dao.findForList("HUDH_ZZBL_ADVICE.findCaseHistoryById", id);
	    return caseHistory;
	  }
	  
	  /**
	    * @Title: updateCaseHistoryById   
	    * @Description: TODO(这里用一句话描述这个方法的作用)   
	    * @param: @param adviceNote
	    * @param: @throws Exception      
	    * @return: void
	   */
	  public void updateCaseHistoryById(ZzblAdvice zzblAdvice)
	    throws Exception
	  {
	    dao.update("HUDH_ZZBL_ADVICE.updateCaseHistoryById", zzblAdvice);
	  }
	  
	  /**
	    * @Title: deleteCaseHistory   
	    * @Description: TODO(这里用一句话描述这个方法的作用)   
	    * @param: @param id
	    * @param: @throws Exception      
	    * @return: void
	   */
	  public void deleteCaseHistoryById(String id)
	    throws Exception
	  {
	    dao.deleteSingleUUID("HUDH_ZZBL_ADVICE.deleteCaseHistoryById", id);
	  }
}
