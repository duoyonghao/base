/**  
  *
  * @Title:  HUDH_LcljVerificationSheetServiceImpl.java   
  * @Package com.hudh.lclj.service.impl   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年12月23日 下午6:46:46   
  * @version V1.0  
  */ 
package com.hudh.lclj.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hudh.lclj.dao.LcljVerificationSheetDao;
import com.hudh.lclj.entity.LcljApprovedMemo;
import com.hudh.lclj.service.HUDH_LcljVerificationSheetService;
import com.kqds.entity.sys.YZPerson;
import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  HUDH_LcljVerificationSheetServiceImpl   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年12月23日 下午6:46:46   
  *      
  */
@Service
public class HUDH_LcljVerificationSheetServiceImpl implements HUDH_LcljVerificationSheetService{

	@Autowired
	private LcljVerificationSheetDao lSheetDao;
	/**   
	  * <p>Title: insert</p>   
	  * <p>Description: </p>   
	  * @param bean   
	 * @throws Exception 
	  * @see com.hudh.lclj.service.HUDH_LcljVerificationSheetService#insert(com.hudh.lclj.entity.LcljApprovedMemo)   
	  */  
	@Override
	@Transactional
	public void insert(LcljApprovedMemo bean) throws Exception {
		// TODO Auto-generated method stub
		lSheetDao.insert(bean);
	}

	/**   
	  * <p>Title: Update</p>   
	  * <p>Description: </p>   
	  * @param bean   
	 * @throws Exception 
	  * @see com.hudh.lclj.service.HUDH_LcljVerificationSheetService#Update(com.hudh.lclj.entity.LcljApprovedMemo)   
	  */  
	@Override
	@Transactional
	public void Update(LcljApprovedMemo bean) throws Exception {
		// TODO Auto-generated method stub
		List<LcljApprovedMemo> list = lSheetDao.findCheckRecordByOrderAndStatus(bean);
		for (LcljApprovedMemo lcljApprovedMemo : list) {
			lSheetDao.updateCheckRecordBySeqId(lcljApprovedMemo);
		}
		lSheetDao.Update(bean);
//		dao.update(TableNameUtil.HUDH_LCLJ_CHECKRECORD+".Update", bean);
	}

	/**   
	  * <p>Title: getCheckRecord</p>   
	  * <p>Description: </p>   
	  * @param lcljId
	  * @return   
	 * @throws Exception 
	  * @see com.hudh.lclj.service.HUDH_LcljVerificationSheetService#getCheckRecord(java.lang.String)   
	  */  
	@Override
	@Transactional
	public List<JSONObject> getCheckRecord(Map<String, String> map, YZPerson person) throws Exception {
		// TODO Auto-generated method stub
		if((person.getDeptId()).equals("adea811e-a70c-46b0-a950-2f5727bd2fe8")) {
			return lSheetDao.findLcljCheckrecordById(map);
		} else if((person.getDeptId()).equals("45df337f-8687-4c0b-aa29-95e8b4a59d5d")) {
			return lSheetDao.findLcljCheckrecordById(map);
		} else if((person.getDeptId()).equals("4c4d0f09-6b5d-471f-80fb-4b2b92aaa7ae")) {
			return lSheetDao.findLcljCheckrecordById(map);
		} else {	
			return lSheetDao.findLcljCheckrecord(map);
		}
//		return (List<JSONObject>)dao.findForList(TableNameUtil.HUDH_LCLJ_CHECKRECORD+".findLcljCheckrecordById", map);
	}

	/**   
	  * <p>Title: delCheckRecord</p>   
	  * <p>Description: </p>   
	  * @param id   
	 * @throws Exception 
	  * @see com.hudh.lclj.service.HUDH_LcljVerificationSheetService#delCheckRecord(java.lang.String)   
	  */  
	@Override
	@Transactional
	public void delCheckRecord(String id) throws Exception {
		// TODO Auto-generated method stub
		lSheetDao.delCheckRecord(id);
	}

	@Override
	public Integer getCheckRecordNum(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return lSheetDao.getCheckRecordNum(map);
	}

	@Override
	public Integer getAwaitVerifieNum(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return lSheetDao.getAwaitVerifieNum(map);
	}

	@Override
	public List<JSONObject> getAwaitCheckRecord(Map<String, String> map, YZPerson person) throws Exception {
		// TODO Auto-generated method stub
		return lSheetDao.getAwaitCheckRecord(map);
	}

}
