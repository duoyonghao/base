package com.hudh.ykzz.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hudh.ykzz.entity.YkzzDrugsIn;
import com.hudh.ykzz.entity.YkzzDrugsOut;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;
@Service
public class YkzzDrugsOutDao {
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 新增出库信息
	 * @param ykzzType
	 * @return
	 * @throws Exception
	 */
	public int insertDrugsOut(YkzzDrugsOut ykzzDrugsOut) throws Exception{
		return (int) dao.save("HUDH_YKZZ_DRUGS_OUT.insertDrugsOut", ykzzDrugsOut);
	}
	
	/**
	 * 获取全部出库数据或根据条件检索
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllDrugsOut(Map<String,String> dataMap) throws Exception{
		return (List<JSONObject>) dao.findForList("HUDH_YKZZ_DRUGS_OUT.findAllDrugsOut", dataMap);
	}
	
	/**
	 * 删除 更新status字段
	 * @param id
	 * @throws Exception
	 */
	public void deleteDrugsOut(String id) throws Exception{
		dao.delete("HUDH_YKZZ_DRUGS_OUT.deleteDrugsOut", id);
	}
	
}
