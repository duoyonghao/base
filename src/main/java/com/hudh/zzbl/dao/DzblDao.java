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

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.zzbl.entity.AdviceNote;
import com.hudh.zzbl.entity.FamiliarBook;
import com.hudh.zzbl.entity.LocatorFamiliar;
import com.kqds.dao.DaoSupport;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  DzblDao   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年5月6日 下午3:01:53   
  *      
  */
@Service
public class DzblDao {

	@Autowired
	  private DaoSupport dao;
	  
	/**
	  * @Title: saveCaseHistory   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param adviceNote
	  * @param: @throws Exception      
	  * @return: void
	 */
	  public void saveCaseHistory(AdviceNote adviceNote)
	    throws Exception
	  {
	    dao.save("HUDH_DZBL_AdviceNote.saveCaseHistory", adviceNote);
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
	    List<JSONObject> caseHistory = (List<JSONObject>)dao.findForList("HUDH_DZBL_AdviceNote.findCaseHistoryById", id);
	    return caseHistory;
	  }
	  
	  /**
	    * @Title: updateCaseHistoryById   
	    * @Description: TODO(这里用一句话描述这个方法的作用)   
	    * @param: @param adviceNote
	    * @param: @throws Exception      
	    * @return: void
	   */
	  public void updateCaseHistoryById(AdviceNote adviceNote)
	    throws Exception
	  {
	    dao.update("HUDH_DZBL_AdviceNote.updateCaseHistoryById", adviceNote);
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
	    dao.deleteSingleUUID("HUDH_DZBL_AdviceNote.deleteCaseHistoryById", id);
	  }
	  
	  /**
	    * @Title: saveFamiliarBook   
	    * @Description: TODO(这里用一句话描述这个方法的作用)   
	    * @param: @param familiarBook
	    * @param: @throws Exception      
	    * @return: void
	   */
	  public void saveFamiliarBook(FamiliarBook familiarBook)
	    throws Exception
	  {
	    dao.save("HUDH_DZBL_FamiliarBook.saveFamiliarBook", familiarBook);
	  }
	  
	  /**
	    * @Title: updateFamiliarBook   
	    * @Description: TODO(这里用一句话描述这个方法的作用)   
	    * @param: @param familiarBook
	    * @param: @throws Exception      
	    * @return: void
	   */
	  public void updateFamiliarBook(FamiliarBook familiarBook)
	    throws Exception
	  {
	    dao.update("HUDH_DZBL_FamiliarBook.updateFamiliarBook", familiarBook);
	  }
	  
	  /**
	    * @Title: findFamiliarBook   
	    * @Description: TODO(这里用一句话描述这个方法的作用)   
	    * @param: @param id
	    * @param: @return
	    * @param: @throws Exception      
	    * @return: JSONObject
	   */
	  public JSONObject findFamiliarBook(String id)
	    throws Exception
	  {
	    return (JSONObject)dao.findForObject("HUDH_DZBL_FamiliarBook.findFamiliarBookById", id);
	  }
	  
	  /**
	    * @Title: deleteFamiliarBook   
	    * @Description: TODO(这里用一句话描述这个方法的作用)   
	    * @param: @param id
	    * @param: @throws Exception      
	    * @return: void
	   */
	  public void deleteFamiliarBook(String id)
	    throws Exception
	  {
		  dao.deleteSingleUUID("HUDH_DZBL_FamiliarBook.deleteFamiliarBook", id);
	  }

	/**   
	  * @Title: findFamiliarBookList   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param lcljId
	  * @param: @return      
	  * @return: JSONObject
	 * @throws Exception 
	  * @dateTime:2019年7月11日 下午6:04:31
	  */  
	public JSONObject findFamiliarBookList(String lcljId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**   
	  * @Title: findLocatorFamiliar   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param lcljId
	  * @param: @return      
	  * @return: JSONObject
	 * @throws Exception 
	  * @dateTime:2019年7月11日 下午6:09:25
	  */  
	public JSONObject findLocatorFamiliar(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return (JSONObject) dao.findForObject("HUDH_LCLJ_LOCATORFAMILIARBOOK.findLocatorFamiliar", map);
	}

	/**   
	  * @Title: findLocatorFamiliar   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param lcljId
	  * @param: @return      
	  * @return: JSONObject
	 * @throws Exception 
	  * @dateTime:2019年7月11日 下午6:09:25
	  */  
	@SuppressWarnings("unchecked")
	public List<JSONObject> findLocatorFamiliares(String lcljId) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>)dao.findForList("HUDH_LCLJ_LOCATORFAMILIARBOOK.findLocatorFamiliares", lcljId);
	}
	
	/**   
	  * @Title: saveLocatorFamiliar   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param locatorFamiliar      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年7月12日 上午9:16:57
	  */  
	public Object saveLocatorFamiliar(LocatorFamiliar locatorFamiliar) throws Exception {
		// TODO Auto-generated method stub
		return dao.save("HUDH_LCLJ_LOCATORFAMILIARBOOK.saveLocatorFamiliar", locatorFamiliar);
	
	}

	/**   
	  * @Title: updateLocatorFamiliar   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param locatorFamiliar      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年7月12日 下午2:54:48
	  */  
	public Object updateLocatorFamiliar(LocatorFamiliar locatorFamiliar) throws Exception {
		// TODO Auto-generated method stub
		return dao.update("HUDH_LCLJ_LOCATORFAMILIARBOOK.updateLocatorFamiliar", locatorFamiliar);
	}
}
