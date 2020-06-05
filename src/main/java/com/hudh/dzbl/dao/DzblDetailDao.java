package com.hudh.dzbl.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hudh.dzbl.entity.DzblDetail;
import com.kqds.dao.DaoSupport;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;
@Service
public class DzblDetailDao {
	@Autowired
	private DaoSupport dao;
	/**
	 * 向电子病历模板表插入数据
	 * @param dzblTemplate
	 * @return
	 * @throws Exception
	 */
	public int insertDzblDetail(DzblDetail dzblDetail) throws Exception {
		int rsCount = (int) dao.save("HUDH_DZBL_DETAIL.insertDzblDetail", dzblDetail);
		return rsCount;
	}
	
	/**
	 * 根据病历号查询患者病历列表
	 * @param dzblTemplate
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findDzblByBlcode(String blCode) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_DZBL_DETAIL.findDzblByBlcode", blCode);
		return list;
	}
	
	/**
	 * 根据id更新病历信息（暂存之后在提交）
	 * @param dzblTemplate
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateDzblById(DzblDetail dzblDetail) throws Exception {
		dao.update("HUDH_DZBL_DETAIL.updateDzblById", dzblDetail);
	}
	
	/**
	 * 根据病例号查询患者信息
	 * @param blCode
	 * @return
	 * @throws Exception 
	 */
	public JSONObject getBaseUserInfoByUsercode(String blCode) throws Exception{
		return (JSONObject) dao.findForObject("KQDS_USERDOCUMENT.getBaseUserInfoByUsercode", blCode);
	}
	
	/**
	 * 根据id查询病历信息
	 * @param blCode
	 * @return
	 * @throws Exception 
	 */
	public JSONObject findDzblById(String id) throws Exception{
		return (JSONObject) dao.findForObject("HUDH_DZBL_DETAIL.findDzblById", id);
	}
	
	/**
	  * @Title: findDzblDetailByUsercodes   
	  * @Description: TODO(查询病历)   
	  * @param: @param usercodes
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2019年9月2日 上午10:24:33
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findDzblDetailByUsercodes(String usercodes) throws Exception {
		List<JSONObject> list=(List<JSONObject>) dao.findForList("HUDH_DZBL_DETAIL.findDzblDetailByUsercodes", usercodes);
		return list;
	}
}
