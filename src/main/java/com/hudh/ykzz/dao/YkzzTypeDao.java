package com.hudh.ykzz.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hudh.ykzz.entity.YkzzType;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;
@Service
public class YkzzTypeDao {
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 新增分类
	 * @param ykzzType
	 * @return
	 * @throws Exception
	 */
	public int insertYkzzType(YkzzType ykzzType) throws Exception{
		return (int) dao.save("HUDH_YKZZ_TYPE.insertYkzzType", ykzzType);
	}
	
	/**
	 * 根据id查找分类
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public YkzzType findYkzzTypeById(String id) throws Exception{
		YkzzType ykzzType = (YkzzType) dao.findForObject("HUDH_YKZZ_TYPE.findYkzzTypeById", id);
		return ykzzType;
	}
	
	/**
	 * 根据id删除分类
	 * @param id
	 * @throws Exception
	 */
	public void deleteYkzzTypeById(String id) throws Exception{
		dao.delete("HUDH_YKZZ_TYPE.deleteYkzzTypeById", id);
	}
	
	/**
	 * 根据id更新分类信息
	 * @param ykzzType
	 * @throws Exception
	 */
	public void updateYkzzTypeById(YkzzType ykzzType) throws Exception{
		dao.update("HUDH_YKZZ_TYPE.updateYkzzTypeById", ykzzType);
	}
	
	/**
	 * 根据id查找子级分类
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findChildTypesByParentId(Map<String, String> map ) throws Exception{
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_YKZZ_TYPE.findChildTypesByParentId", map);
		return list;
	}
	
	/**
	 * 获取全部分类
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllTypes(@Param("organization") String organization) throws Exception{
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("organization", organization);//直接传String会报错，装进map中解决该问题
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_YKZZ_TYPE.findAllTypes", dataMap);
		return list;
	}
}
