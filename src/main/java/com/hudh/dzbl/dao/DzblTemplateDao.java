package com.hudh.dzbl.dao;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hudh.dzbl.entity.DzblTemplate;
import com.kqds.dao.DaoSupport;
import net.sf.json.JSONObject;
@Service
public class DzblTemplateDao {
	@Autowired
	private DaoSupport dao;
	/**
	 * 向电子病历模板表插入数据
	 * @param dzblTemplate
	 * @return
	 * @throws Exception
	 */
	public int insertDzblTemplate(DzblTemplate dzblTemplate) throws Exception {
		int rsCount = (int) dao.save("HUDH_DZBL_TEMPLATE.insertDzblTemplate", dzblTemplate);
		return rsCount;
	}
	/**
	 * 根据病历分类及病程查询对应的模板
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findDzblTemplate(Map<String,String> dataMap) throws Exception {
		List<JSONObject> dzblTemplate = (List<JSONObject>) dao.findForList("HUDH_DZBL_TEMPLATE.findDzblTemplate", dataMap);
		return dzblTemplate;
	}
	
	/**
	 * 根据病例分类查所有的病程分类
	 * @param blfl
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findBcByBlfl(Map<String,String> dataMap) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_DZBL_TEMPLATE.findBcByBlfl", dataMap);
		return list;
	}
	
	/**
	 * 根据id查找模板
	 * @param blfl
	 * @return
	 */
	public JSONObject findDzblTemplateById(String id) throws Exception {
		JSONObject jo = (JSONObject) dao.findForObject("HUDH_DZBL_TEMPLATE.findDzblTemplateById", id);
		return jo;
	}
	
	/**
	 * 根据id更新模板信息
	 * @param dzblTemplate
	 * @throws Exception
	 */
	public void updateDzblTempleById(DzblTemplate dzblTemplate)  throws Exception{
		dao.update("HUDH_DZBL_TEMPLATE.updateDzblTempleById", dzblTemplate);
	}
}
