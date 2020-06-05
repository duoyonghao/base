/**  
  *
  * @Title:  DzblServiceImpl.java   
  * @Package com.hudh.zzbl.service.impl   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年5月6日 下午2:59:52   
  * @version V1.0  
  */ 
package com.hudh.zzbl.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.zzbl.dao.DzblDao;
import com.hudh.zzbl.entity.AdviceNote;
import com.hudh.zzbl.entity.FamiliarBook;
import com.hudh.zzbl.entity.LocatorFamiliar;
import com.hudh.zzbl.service.DzblService;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  DzblServiceImpl   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年5月6日 下午2:59:52   
  *      
  */
@Service
public class DzblServiceImpl implements DzblService{

	 @Autowired
	  private DzblDao dzblDao;
	  
	  public void saveCaseHistory(AdviceNote adviceNote) throws Exception{
	    dzblDao.saveCaseHistory(adviceNote);
	  }
	  
	  /**
	    * <p>Title: findCaseHistoryById</p>   
	    * <p>Description: </p>   
	    * @param id
	    * @return
	    * @throws Exception   
	    * @see com.hudh.dzbl.service.DzblService#findCaseHistoryById(java.lang.String)
	   */
	  public  List<JSONObject> findCaseHistoryById(String id) throws Exception{
	    return dzblDao.findCaseHistoryById(id);
	  }
	  
	  /**
	    * <p>Title: updateCaseHistoryById</p>   
	    * <p>Description: </p>   
	    * @param adviceNote
	    * @throws Exception   
	    * @see com.hudh.dzbl.service.DzblService#updateCaseHistoryById(com.hudh.dzbl.entity.AdviceNote)
	   */
	  public void updateCaseHistoryById(AdviceNote adviceNote)
	    throws Exception
	  {
	    dzblDao.updateCaseHistoryById(adviceNote);
	  }
	  
	  /**
	    * <p>Title: deleteCaseHistory</p>   
	    * <p>Description: </p>   
	    * @param id
	    * @throws Exception   
	    * @see com.hudh.dzbl.service.DzblService#deleteCaseHistory(java.lang.String)
	   */
	  public void deleteCaseHistory(String id)
	    throws Exception
	  {
	    dzblDao.deleteCaseHistoryById(id);
	  }
	  
	  /**
	    * <p>Title: saveFamiliarBook</p>   
	    * <p>Description: </p>   
	    * @param familiarBook
	    * @throws Exception   
	    * @see com.hudh.dzbl.service.DzblService#saveFamiliarBook(com.hudh.dzbl.entity.FamiliarBook)
	   */
	  public void saveFamiliarBook(FamiliarBook familiarBook)
	    throws Exception
	  {
	    dzblDao.saveFamiliarBook(familiarBook);
	  }
	  
	  /**
	    * <p>Title: updateFamiliarBook</p>   
	    * <p>Description: </p>   
	    * @param familiarBook
	    * @throws Exception   
	    * @see com.hudh.dzbl.service.DzblService#updateFamiliarBook(com.hudh.dzbl.entity.FamiliarBook)
	   */
	  public void updateFamiliarBook(FamiliarBook familiarBook)
	    throws Exception
	  {
	    dzblDao.updateFamiliarBook(familiarBook);
	  }
	  
	  /**
	    * <p>Title: findFamiliarBook</p>   
	    * <p>Description: </p>   
	    * @param id
	    * @return
	    * @throws Exception   
	    * @see com.hudh.dzbl.service.DzblService#findFamiliarBook(java.lang.String)
	   */
	  public JSONObject findFamiliarBook(String id)
	    throws Exception
	  {
	    return dzblDao.findFamiliarBook(id);
	  }
	  
	  /**
	    * <p>Title: deleteFamiliarBook</p>   
	    * <p>Description: </p>   
	    * @param id
	    * @throws Exception   
	    * @see com.hudh.dzbl.service.DzblService#deleteFamiliarBook(java.lang.String)
	   */
	  public void deleteFamiliarBook(String id)
	    throws Exception
	  {
	    dzblDao.deleteFamiliarBook(id);
	  }


	/**   
	  * <p>Title: findLocatorFamiliar</p>   
	  * <p>Description: </p>   
	  * @param lcljId
	  * @return   
	 * @throws Exception 
	  * @see com.hudh.zzbl.service.DzblService#findLocatorFamiliar(java.lang.String)   
	  */  
	@Override
	public JSONObject findLocatorFamiliar(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return dzblDao.findLocatorFamiliar(map);
	}
	
	/**   
	  * <p>Title: findLocatorFamiliar</p>   
	  * <p>Description: </p>   
	  * @param lcljId
	  * @return   
	 * @throws Exception 
	  * @see com.hudh.zzbl.service.DzblService#findLocatorFamiliar(java.lang.String)   
	  */  
	@Override
	public List<JSONObject> findLocatorFamiliares(String lcljId) throws Exception {
		// TODO Auto-generated method stub
		return dzblDao.findLocatorFamiliares(lcljId);
	}

	/**   
	  * <p>Title: findFamiliarBookList</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see com.hudh.zzbl.service.DzblService#findFamiliarBookList()   
	  */  
	@Override
	public List<JSONObject> findFamiliarBookList() {
		// TODO Auto-generated method stub
		return null;
	}

	/**   
	  * <p>Title: saveLocatorFamiliar</p>   
	  * <p>Description: </p>   
	  * @param locatorFamiliar   
	 * @return 
	 * @throws Exception 
	  * @see com.hudh.zzbl.service.DzblService#saveLocatorFamiliar(com.hudh.zzbl.entity.LocatorFamiliar)   
	  */  
	@Override
	public Integer saveLocatorFamiliar(LocatorFamiliar locatorFamiliar) throws Exception {
		return (Integer)dzblDao.saveLocatorFamiliar(locatorFamiliar);
	}

	/**   
	  * <p>Title: updateLocatorFamiliar</p>   
	  * <p>Description: </p>   
	  * @param locatorFamiliar   
	 * @throws Exception 
	  * @see com.hudh.zzbl.service.DzblService#updateLocatorFamiliar(com.hudh.zzbl.entity.LocatorFamiliar)   
	  */  
	@Override
	public void updateLocatorFamiliar(LocatorFamiliar locatorFamiliar) throws Exception {
		// TODO Auto-generated method stub
		dzblDao.updateLocatorFamiliar(locatorFamiliar);
	}
}
