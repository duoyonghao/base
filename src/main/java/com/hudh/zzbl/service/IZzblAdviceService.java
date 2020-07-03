package com.hudh.zzbl.service;



import com.hudh.zzbl.entity.ZzblAdvice;
import net.sf.json.JSONObject;

import java.util.List;


/**
  * 
  * @ClassName:  DzblService   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年5月6日 下午2:58:32   
  *      
  */
public interface IZzblAdviceService {

	/**
	  * @Title: saveCaseHistory   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param paramAdviceNote
	  * @param: @throws Exception      
	  * @return: void
	 */
	public void saveCaseHistory(ZzblAdvice zzblAdvice)
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
	public void updateCaseHistoryById(ZzblAdvice zzblAdvice)
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
}
