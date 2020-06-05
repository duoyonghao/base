package com.hudh.lclj.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZPara;
import com.kqds.entity.sys.YZPerson;

import net.sf.json.JSONObject;
@Service
public class SysParaDao {
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 根据参数配置名称获取值的集合
	 * @param blCode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getParaValueListByName(List<String> list, HttpServletRequest request, String organization) throws Exception {
		List<JSONObject> sysParaList = (List<JSONObject>) dao.findForList("SYS_PARA.getParaValueListByName", list);
		return sysParaList;
	}
	
	/**
	  * @Title: getParaValueByName   
	  * @Description: TODO(根据参数配置名称获取值)   duoyh
	  * @param: @param paraValue
	  * @param: @param request
	  * @param: @param organization
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: JSONObject
	  * @dateTime:2019年12月9日 下午5:05:55
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getParaValueByName(String paraValue, HttpServletRequest request, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("paraValue", paraValue);
		map.put("organization", organization);
		return (JSONObject) dao.findForObject("SYS_PARA.getParaByName", map);
	}
	/**
	 * 更新代办人
	 * @param blCode
	 * @return
	 * @throws Exception
	 */
	public void updateAgencyUser(YZPara yzPara) throws Exception {
		dao.update("SYS_PARA.updateByPrimaryKeySelective", yzPara);
	}
	
	public List<YZPerson> selectUserBySeqId(Map<String,Map<String,String>>dataMap) throws Exception{
		List<YZPerson> sysPersonList = (List<YZPerson>) dao.findForList("SYS_PERSON.selectBeanListByMap", dataMap);
		return sysPersonList;
	};
	
	/**
	 * 根据参数集合获取查询结果
	 * @return
	 * @throws Exception
	 */
	public List<YZPara> selectParaValueByName(Map<String,Map<String,String>> dataMap) throws Exception{
		List<YZPara> sysParaList = (List<YZPara>) dao.findForList("SYS_PARA.selectBeanListByMap", dataMap);
		return sysParaList;
	}
}
