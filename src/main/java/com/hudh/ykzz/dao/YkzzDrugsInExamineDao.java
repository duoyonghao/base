package com.hudh.ykzz.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.ykzz.entity.YkzzDrugsInExamine;
import com.hudh.ykzz.entity.YkzzType;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
@Service
public class YkzzDrugsInExamineDao {
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 新增入库审批数据
	 * @param ykzzType
	 * @return
	 * @throws Exception
	 */
	public int insertDrugsInExamine(YkzzDrugsInExamine ykzzDrugsInExamine) throws Exception{
		return (int) dao.save("HUDH_YKZZ_DRUGS_IN_EXAMINE.insertDrugsInExamine", ykzzDrugsInExamine);
	}
	
	/**
	 * 根据入库表id查询对应的审批数据
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findDrugsInExamineByInId(String drugsInId) throws Exception{
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_YKZZ_DRUGS_IN_EXAMINE.findDrugsInExamineByInId", drugsInId);
		return list;
	}
	
	/**
	 * 根据id删除数据
	 * @param id
	 * @throws Exception
	 */
	public void deleteDrugsInExamineById(String id) throws Exception{
		dao.delete("HUDH_YKZZ_DRUGS_IN_EXAMINE.deleteDrugsInExamineById", id);
	}
	
	/**
	 * 根据入库表id删除信息
	 * @param ykzzType
	 * @throws Exception
	 */
	public void deleteDrugsInExamineByInId(String drugsInId) throws Exception{
		dao.delete("HUDH_YKZZ_DRUGS_IN_EXAMINE.deleteDrugsInExamineByInId", drugsInId);
	}
	
	/**
	 * 查询发要明细数据
	 * @param ykzzType
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findDugsExamineDetail(Map<String,String> dataMap) throws Exception{
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_YKZZ_DRUGS_IN_EXAMINE.findDugsExamineDetail", dataMap);
		return list;
	}
	/**
	 * 查询退药明细数据
	 * @param ykzzType
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findDugsReturnDetail(Map<String,String> dataMap) throws Exception{
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_YKZZ_DRUGS_IN_EXAMINE.findDugsReturnDetail", dataMap);
		return list;
	}
	
}
