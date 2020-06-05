package com.hudh.zzbl.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.zzbl.entity.ZzblCheck;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;

@Service
public class ZzblCheckDao {
	
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 保存数据
	 * @param dp
	 * @throws Exception
	 */
	public void insertZzblCheck(ZzblCheck dp) throws Exception {
		dao.save("HUDH_ZZBL_CHECK.insertZzblCheck", dp);
	}
	
	/**
	 * 根据id查询信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findZzblOprationById(String id) throws Exception {
	 List<JSONObject> jsonObject = (List<JSONObject>) dao.findForList("HUDH_ZZBL_CHECK.findZzblOprationById", id);
		return jsonObject;
	}
	
	/**
	 * 根据id查询信息，返回对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ZzblCheck selectZzblCheckById(String id) throws Exception {
		ZzblCheck zzblCheck = (ZzblCheck) dao.findForObject("HUDH_ZZBL_CHECK.selectZzblCheckById", id);
		return zzblCheck;
	}
	
	/**
	 * 根据id修改
	 * @param id
	 * @throws Exception
	 */
	public void updateZzblOprationById(ZzblCheck dp) throws Exception {
		dao.update("HUDH_ZZBL_CHECK.updateZzblOprationById", dp);
	}
	
	/**
	 * 根据id删除信息
	 * @param id
	 * @throws Exception
	 */
	public void deleteZzblInforById(String id) throws Exception {
		dao.delete("HUDH_ZZBL_CHECK.deleteZzblInforById", id);
	}
	
	/**
	 * 查询全部的信息
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findAllZzblInfor() throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_ZZBL_CHECK.findAllZzblInfor", null);
		return list;
	}
}
