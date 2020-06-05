package com.hudh.zzbl.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.zzbl.entity.ZzblOperation;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;

@Service
public class ZzblDao {
	
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 保存数据
	 * @param dp
	 * @throws Exception
	 */
	public void save(ZzblOperation dp) throws Exception {
		dao.save("HUDH_ZZBL_DETAIL.save", dp);
	}
	
	/**
	 * 根据id查询信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List <ZzblOperation> findZzblOprationById(String id) throws Exception {
		List<ZzblOperation> json = (List<ZzblOperation>) dao.findForList("HUDH_ZZBL_DETAIL.findZzblOprationById", id);
		return json;
	}
	
	/**
	 * 根据id查询信息，返回对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JSONObject selectZzblOperationById(String id) throws Exception {
		JSONObject json = (JSONObject) dao.findForObject("HUDH_ZZBL_DETAIL.selectZzblOperationById", id);
		return json;
	}
	
	/**
	 * 根据id修改
	 * @param id
	 * @throws Exception
	 */
	public void updateZzblOprationById(ZzblOperation dp) throws Exception {
		dao.update("HUDH_ZZBL_DETAIL.updateZzblOprationById", dp);
	}
	
	/**
	 * 根据临床路径编号修改信息
	 * @param dp
	 * @throws Exception
	 */
	public void updateZzblOperationByOrderNumber(ZzblOperation dp) throws Exception {
		dao.update("HUDH_ZZBL_DETAIL.updateZzblOperationByOrderNumber", dp);
	}
	
	/**
	 * 根据id删除信息
	 * @param id
	 * @throws Exception
	 */
	public void deleteZzblInforById(String id) throws Exception {
		dao.delete("HUDH_ZZBL_DETAIL.deleteZzblInforById", id);
	}
	
	/**
	 * 查询全部的信息
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findAllZzblInfor() throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_ZZBL_DETAIL.findAllZzblInfor", null);
		return list;
	}

}
