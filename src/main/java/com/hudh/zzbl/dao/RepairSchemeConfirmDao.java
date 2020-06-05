package com.hudh.zzbl.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.zzbl.entity.RepairSchemeConfirm;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;

@Service
public class RepairSchemeConfirmDao {
	
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 保存修复方案确认信息
	 * @throws Exception
	 */
	public void saveRepairSchemeConfirmInfro(RepairSchemeConfirm dp) throws Exception {
		dao.save("HUDH_ZZBL_REPAIR_DETAIL.saveRepairSchemeConfirmInfro", dp);
	}
	
	/**
	 * 根据id删除修复方案确认信息
	 * @param id
	 * @throws Exception
	 */
	public void deleteRepairInforById(String id) throws Exception {
		dao.delete("HUDH_ZZBL_REPAIR_DETAIL.deleteRepairInforById", id);
	}
	
	/**
	 * 根据id修改修复方案确认信息
	 * @param id
	 * @throws Exception
	 */
	public void updateRepairInforById(RepairSchemeConfirm dp) throws Exception {
		dao.update("HUDH_ZZBL_REPAIR_DETAIL.updateRepairInforById", dp);
	}
	
	/**
	 * 根据id查询修复方案信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findRepairInforById(String id) throws Exception {
		List<JSONObject> json = (List<JSONObject>) dao.findForList("HUDH_ZZBL_REPAIR_DETAIL.findRepairInforById", id);
		return json;
	}
	
	/**
	 * 根据id查询修复方案信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JSONObject selectRepairInforById(String id) throws Exception {
		JSONObject json = (JSONObject) dao.findForObject("HUDH_ZZBL_REPAIR_DETAIL.selectRepairInforById", id);
		return json;
	}
	
	
	/**
	 * 根据id查询修复方案信息，返回对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public RepairSchemeConfirm selectRepairSchemeConfirmById(String id) throws Exception {
		RepairSchemeConfirm repairSchemeConfirm = (RepairSchemeConfirm) dao.findForObject("HUDH_ZZBL_REPAIR_DETAIL.selectRepairSchemeConfirmById", id);
		return repairSchemeConfirm;
	}
	
	/**
	 * 查询所有的信息
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findReapirInforAll() throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_ZZBL_REPAIR_DETAIL.findReapirInforAll", null);
		return list;
	}
}
