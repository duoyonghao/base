package com.hudh.ykzz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.ykzz.entity.YkzzDrugs;
import com.hudh.ykzz.entity.YkzzDrugsInDetail;
import com.hudh.ykzz.entity.YkzzDrugsOutDetail;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsTreatitem;

import net.sf.json.JSONObject;

@Service
public class IYkzzDrugsDao {
	
	@Autowired
	private DaoSupport dao;
	
	/**I
	 * 添加药品信息
	 * @param ykzzDrugs
	 * @return
	 * @throws Exception
	 */
	public int insertDrugsInfor(YkzzDrugs ykzzDrugs) throws Exception{
		int drugsNum = (int) dao.save("HUDH_YKZZ_DRUGS.insertDrugsInfor", ykzzDrugs);
		return drugsNum;
	}
	
	/**
	 * 药品信息同步到收费项目表中
	 * @param dp
	 * @return
	 * @throws Exception
	 */
	public int addTreatItemBack(KqdsTreatitem dp) throws Exception{
		dao.save("HUDH_YKZZ_DRUGS.addTreatItemBack", dp);
		return 0;
	}
	
	/**
	 * 批量更新药品信息到收费项目表中
	 * @param list
	 * @throws Exception
	 */
	public void saveBatchInsertTreatItemBack(List<KqdsTreatitem> list) throws Exception {
		dao.batchUpdate("HUDH_YKZZ_DRUGS.saveBatchInsertTreatItemBack", list);
	}
	
	/**
	 * 同步修改收费项目表中药品的信息
	 * @param dp
	 * @throws Exception
	 */
	public void updateDeugsTreatitemInfor(KqdsTreatitem dp) throws Exception{
		dao.update("HUDH_YKZZ_DRUGS.updateDeugsTreatitemInfor", dp);
	}
	
	/**
	 * 查询药品
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JSONObject selectDrugsByPrimaryId(String id) throws Exception{
		JSONObject json = (JSONObject) dao.findForObject("HUDH_YKZZ_DRUGS.selectDrugsByPrimaryId", id);
		return json;
	}
	
	/**
	 * 查找全部药品
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectAllDrugsInfor(Map<String, String> map) throws Exception{
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_YKZZ_DRUGS.selectAllDrugsInfor", map);
		return list;
	}
	
	/**
	 * 查找最大的药品号(新增方法)
	 * @return
	 * @throws Exception
	 */
	public YkzzDrugs findNextOrderNumber() throws Exception{
		YkzzDrugs ykzzDrugs = (YkzzDrugs) dao.findForObject("HUDH_YKZZ_DRUGS.findNextOrderNumber", null);
		return ykzzDrugs;
	}
	
	/**
	 * 获得编号的数量
	 * @return
	 * @throws Exception
	 */
	public int findOrderNumberCount() throws Exception{
		int num = (int) dao.findForObject("HUDH_YKZZ_DRUGS.findOrderNumberCount", null);
		return num;
	}
	
	/**
	 * 删除药品
	 * @param id
	 * @throws Exception
	 */
	public void deleteDrugsByPrimaryId(String id) throws Exception{
		dao.delete("HUDH_YKZZ_DRUGS.deleteDrugsByPrimaryId", id);
	}
	
	/**
	 * 同步删除收费项目表中的药品
	 * @param id
	 * @throws Exception
	 */
	public void deleteTreamtDrugsByPrimaryId(String id) throws Exception {
		dao.delete("HUDH_YKZZ_DRUGS.deleteTreamtDrugsByPrimaryId", id);
	}
	
	/**
	 * 更新药品
	 * @param id
	 * @throws Exception
	 */
	public void updateDrugsByPrimaryId( @Param("ykzzDrugs") YkzzDrugs ykzzDrugs) throws Exception{
		dao.update("HUDH_YKZZ_DRUGS.updateDrugsByPrimaryId", ykzzDrugs);
	}
	
	/**
	 * 批量更新药品
	 * @param id
	 * @throws Exception
	 */
	public void batchUpdateDrugsByPrimaryId(List<YkzzDrugs> ykzzDrugs) throws Exception{
		dao.batchUpdate("HUDH_YKZZ_DRUGS.batchUpdateDrugsByPrimaryId", ykzzDrugs);
	}
	
	/**
	 * 精确查询药品
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectDrugsInforByConditionQuery(Map<String, String> map) throws Exception{
		List<JSONObject> json = (List<JSONObject>) dao.findForList("HUDH_YKZZ_DRUGS.selectDrugsInforByConditionQuery", map);
		return json;
	}
	
	/**
	 * 根据id集合获取药品
	 */
	@SuppressWarnings("unchecked")
	public List<YkzzDrugs> selectDrugsByIdStr(List<YkzzDrugsInDetail> list)  throws Exception{
		List<YkzzDrugs> json = (List<YkzzDrugs>) dao.findForList("HUDH_YKZZ_DRUGS.selectDrugsByIdStr", list);
		return json;
	}
	
	/**
	 * 根据id集合获取药品
	 */
	@SuppressWarnings("unchecked")
	public List<YkzzDrugs> selectDrugsOutByIdStr(List<YkzzDrugsOutDetail> list)  throws Exception{
		List<YkzzDrugs> json = (List<YkzzDrugs>) dao.findForList("HUDH_YKZZ_DRUGS.selectDrugsByIdStr", list);
		return json;
	}
	/**
	 * 根据编号集合获取药品
	 */
	@SuppressWarnings("unchecked")
	public List<YkzzDrugs> selectDrugsByOrderNoStr(List<String> list)  throws Exception{
		List<YkzzDrugs> json = (List<YkzzDrugs>) dao.findForList("HUDH_YKZZ_DRUGS.selectDrugsByOrderNoStr", list);
		return json;
	}
	
	/**
	 * 根据模板批量导入药品
	 * @param ykzzDrugs
	 * @throws Exception
	 */
	public void importDrugsInfro(List<YkzzDrugs> list) throws Exception{
		dao.batchUpdate("HUDH_YKZZ_DRUGS.importDrugsInfro", list);
	}
	
	/**
	 * 查询所有的药品
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<YkzzDrugs> getAllDrugsInfor(Map<String, String> map) throws Exception {
		List<YkzzDrugs> list = (List<YkzzDrugs>) dao.findForList("HUDH_YKZZ_DRUGS.getAllDrugsInfor", map);
		return list;
	}
	
	/**
	 * 根据模板导入药品
	 * @param ykzzDrugs
	 * @throws Exception
	 */
	public void insertDrugsInfro(YkzzDrugs ykzzDrugs) throws Exception{
		dao.save("HUDH_YKZZ_DRUGS.insertDrugsInfro", ykzzDrugs);
	}
	
	/**
	 * 根据药品国家编号查询药品
	 */
	@SuppressWarnings("unchecked")
	public List<YkzzDrugs> findDeugsByContryCode(Map<String, String> dataMap) throws Exception {
		List<YkzzDrugs> ykzzDrugs = (List<YkzzDrugs>) dao.findForList("HUDH_YKZZ_DRUGS.findDeugsByContryCode", dataMap);
		return ykzzDrugs;
	}
	
	/**
	 * 禁用药品
	 * @param id
	 * @throws Exception
	 */
	public void forbiddenDrugs(String id) throws Exception {
		dao.update("HUDH_YKZZ_DRUGS.forbiddenDrugs", id);
	}
	
	/**
	 * 恢复禁用
	 * @param id
	 * @throws Exception
	 */
	public void recoverDrugs(String id) throws Exception {
		dao.update("HUDH_YKZZ_DRUGS.recoverDrugs", id);
	}
}
