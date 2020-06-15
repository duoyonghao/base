/**  
  *
  * @Title:  MedicalRecordsLogic.java   
  * @Package com.hudh.lclj.service.impl   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2020年5月29日 上午9:42:53   
  * @version V1.0  
  */ 
package com.hudh.lclj.service.impl;

import java.util.List;
import java.util.Map;

import com.hudh.lclj.entity.LcljVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hudh.lclj.entity.HudhAcography;
import com.hudh.lclj.entity.HudhOperationNote;
import com.hudh.lclj.entity.HudhSpecialitycheck;
import com.kqds.dao.DAO;
import com.kqds.dao.DaoSupport;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  MedicalRecordsLogic   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2020年5月29日 上午9:42:53   
  *      
  */
@Service
public class MedicalRecordsLogic {

	@Autowired
	private DaoSupport dao;
	
	/**   
	  * @Title: installData   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dp      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2020年5月29日 上午9:52:09
	  */  
	@Transactional
	public void installData(HudhSpecialitycheck dp) throws Exception {
		// TODO Auto-generated method stub
		dao.save(TableNameUtil.HUDH_SpecialityCheck+".insertSelective", dp);
	}

	/**   
	  * @Title: updateDate   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dp      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2020年5月29日 上午11:03:46
	  */
	@Transactional
	public void updateDate(HudhSpecialitycheck dp) throws Exception {
		// TODO Auto-generated method stub
		dao.update(TableNameUtil.HUDH_SpecialityCheck+".updateByseqId", dp);
	}

	/**   
	  * @Title: updateRecord   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dp      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2020年5月30日 上午10:01:56
	  */  
	@Transactional
	public void updateRecord(HudhOperationNote dp) throws Exception {
		// TODO Auto-generated method stub
		dao.update(TableNameUtil.HUDH_operationNote+".updateRecord", dp);
	}

	/**   
	  * @Title: installRecord   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dp      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2020年5月30日 上午10:02:01
	  */  
	@Transactional
	public void insertRecord(HudhOperationNote dp) throws Exception {
		// TODO Auto-generated method stub
		dao.save(TableNameUtil.HUDH_operationNote + ".insertRecord", dp);
	}

	/**   
	  * @Title: selectdata   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map
	  * @param: @return      
	  * @return: List<JSONObject>
	 * @throws Exception 
	  * @dateTime:2020年5月30日 下午2:09:30
	  */  
	@SuppressWarnings("unchecked")
	@Transactional
	public List<JSONObject> selectdata(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>) dao.findForList(TableNameUtil.HUDH_SpecialityCheck+".selectdata", map);
	}

	/**   
	  * @Title: selectRecord   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map
	  * @param: @return      
	  * @return: List<JSONObject>
	 * @throws Exception 
	  * @dateTime:2020年5月30日 下午2:09:41
	  */  
	@SuppressWarnings("unchecked")
	@Transactional
	public List<JSONObject> selectRecord(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>) dao.findForList(TableNameUtil.HUDH_operationNote+".selectRecord", map);
	}

	/**   
	  * @Title: selectAcography   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map
	  * @param: @return      
	  * @return: List<JSONObject>
	 * @throws Exception 
	  * @dateTime:2020年5月30日 下午3:11:47
	  */  
	@SuppressWarnings("unchecked")
	@Transactional
	public List<JSONObject> selectAcography(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>) dao.findForList(TableNameUtil.HUDH_Acography+".selectAcography", map);
	}

	/**   
	  * @Title: update   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dp      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2020年5月30日 下午3:17:27
	  */  
	@Transactional
	public void update(HudhAcography dp) throws Exception {
		// TODO Auto-generated method stub
		dao.update(TableNameUtil.HUDH_Acography+".update", dp);
	}

	/**   
	  * @Title: install   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dp      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2020年5月30日 下午3:17:32
	  */  
	@Transactional
	public void insert(HudhAcography dp) throws Exception {
		// TODO Auto-generated method stub
		dao.save(TableNameUtil.HUDH_Acography+".insert", dp);
	}

	/**
	  * @Title: findVerification
	  * @Description: [id](这里用一句话描述这个方法的作用)
	  * @author admin
	  * @param:  * @param id
	  * @return: void
	  * @dateTime: 2020/6/12 17:18
	  */
    public List<JSONObject> findVerification(String id) throws Exception {
    	return (List<JSONObject>) dao.findForList(TableNameUtil.HUDH_LCLJ_Verification+".selectVerificationByCode", id);
    }

    /**
      * @Title: updateVerification
      * @Description: [dp](这里用一句话描述这个方法的作用)
      * @author admin
      * @param:  * @param dp
      * @return: void
      * @dateTime: 2020/6/12 17:24
      */
	public void updateVerification(LcljVerification dp) throws Exception {
		dao.update(TableNameUtil.HUDH_LCLJ_Verification+".updateByPrimaryKey", dp);
	}

	/**
	  * @Title: SaveVerification
	  * @Description: [dp](这里用一句话描述这个方法的作用)
	  * @author admin
	  * @param:  * @param dp
	  * @return: void
	  * @dateTime: 2020/6/12 17:24
	  */
	public void SaveVerification(LcljVerification dp) throws Exception {
		dao.save(TableNameUtil.HUDH_LCLJ_Verification+".insert",dp);
	}
}
