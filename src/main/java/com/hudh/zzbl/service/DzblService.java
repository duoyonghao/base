package com.hudh.zzbl.service;


import java.util.List;
import java.util.Map;

import com.hudh.zzbl.entity.AdviceNote;
import com.hudh.zzbl.entity.FamiliarBook;
import com.hudh.zzbl.entity.LocatorFamiliar;

import net.sf.json.JSONObject;
/**  
  * 
  * @ClassName:  DzblService   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年5月6日 下午2:58:32   
  *      
  */
public interface DzblService {

	/**
	  * @Title: saveCaseHistory   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param paramAdviceNote
	  * @param: @throws Exception      
	  * @return: void
	 */
	public void saveCaseHistory(AdviceNote paramAdviceNote)
		    throws Exception;
		  
	/**
	  * @Title: findCaseHistoryById   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param paramString
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: JSONObject
	 */
	public List<JSONObject> findCaseHistoryById(String paramString)
		    throws Exception;
	
	/**
	  * @Title: updateCaseHistoryById   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param paramAdviceNote
	  * @param: @throws Exception      
	  * @return: void
	 */
	public void updateCaseHistoryById(AdviceNote paramAdviceNote)
		    throws Exception;
		 
	/**
	  * @Title: deleteCaseHistory   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param paramString
	  * @param: @throws Exception      
	  * @return: void
	 */
	public void deleteCaseHistory(String paramString)
		    throws Exception;
	
	/**
	  * @Title: saveFamiliarBook   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param paramFamiliarBook
	  * @param: @throws Exception      
	  * @return: void
	 */
	public void saveFamiliarBook(FamiliarBook paramFamiliarBook)
		    throws Exception;
	
	/**
	  * @Title: updateFamiliarBook   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param paramFamiliarBook
	  * @param: @throws Exception      
	  * @return: void
	 */
	public void updateFamiliarBook(FamiliarBook paramFamiliarBook)
		    throws Exception;
		
	/**
	  * @Title: findFamiliarBook   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param paramString
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: JSONObject
	 */
	public JSONObject findFamiliarBook(String paramString)
		    throws Exception;
	
	/**
	  * @Title: deleteFamiliarBook   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param paramString
	  * @param: @throws Exception      
	  * @return: void
	 */
	public void deleteFamiliarBook(String paramString)
		    throws Exception;
	/**
	  * @Title: findFamiliarBookList   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @return      
	  * @return: List<JSONObject>
	  * @dateTime:2019年7月11日 下午6:02:38
	 */
	public List<JSONObject> findFamiliarBookList();

	/**   
	  * @Title: findLocatorFamiliar   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param lcljId      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年7月11日 下午6:01:58
	  */  
	public JSONObject findLocatorFamiliar(Map<String, Object> map) throws Exception;

	/**   
	  * @Title: saveLocatorFamiliar   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param locatorFamiliar      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年7月12日 上午9:11:06
	  */  
	public Integer saveLocatorFamiliar(LocatorFamiliar locatorFamiliar) throws Exception;

	/**   
	  * @Title: updateLocatorFamiliar   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param locatorFamiliar      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年7月12日 下午2:53:51
	  */  
	public void updateLocatorFamiliar(LocatorFamiliar locatorFamiliar) throws Exception;

	/**   
	  * @Title: findLocatorFamiliares   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param lcljId
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: JSONObject
	  * @dateTime:2019年7月15日 下午5:37:41
	  */  
	List<JSONObject> findLocatorFamiliares(String lcljId) throws Exception;
}
