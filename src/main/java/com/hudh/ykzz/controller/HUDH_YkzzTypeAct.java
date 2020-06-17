package com.hudh.ykzz.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSON;
import com.hudh.ykzz.entity.YkzzType;
import com.hudh.ykzz.service.IYkzzTypeService;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
@RequestMapping("/HUDH_YkzzTypeAct")
public class HUDH_YkzzTypeAct {
	private Logger logger = LoggerFactory.getLogger(HUDH_YkzzTypeAct.class);
	private static final String TYPEROOTID = "root";
	private static final String TYPEROOTNAME = "根节点";
	/**
	 * 药库操作接口
	 */
	@Autowired
	private IYkzzTypeService ykzzTypeService;
	
	/**
	 * 新增分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertYkzzType.act")
	public String insertYkzzType(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String typeName = request.getParameter("typeName");
		String orderno = request.getParameter("orderno");
		String parentid = request.getParameter("parentid");
		YkzzType ykzzType = new YkzzType();
		ykzzType.setOrderno(orderno);
		ykzzType.setParentid(parentid);
		ykzzType.setTypeName(typeName);
		try {
			ykzzTypeService.insertYkzzType(ykzzType,request);
			YZUtility.DEAL_SUCCESS(null,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据id查找分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findYkzzTypeById.act")
	public String findYkzzTypeById(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		try {
			if(YZUtility.isNotNullOrEmpty(id)) {
				YkzzType ykzzType = ykzzTypeService.findYkzzTypeById(id);
				JSONObject jo = new JSONObject();
				jo.put("ykzzType", JSON.toJSONString(ykzzType));
				YZUtility.DEAL_SUCCESS(jo,null, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据id删除分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteYkzzTypeById.act")
	public String deleteYkzzTypeById(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		try {
			if(YZUtility.isNotNullOrEmpty(id)) {
				ykzzTypeService.deleteYkzzTypeById(id);
				YZUtility.DEAL_SUCCESS(null,null, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}

	/**
	 * 根据id更新分类信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateYkzzTypeById.act")
	public String updateYkzzTypeById(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String typeName = request.getParameter("typeName");
		String orderno = request.getParameter("orderno");
		YkzzType ykzzType = new YkzzType();
		ykzzType.setId(id);
		ykzzType.setOrderno(orderno);
		ykzzType.setTypeName(typeName);
		try {
			if(YZUtility.isNotNullOrEmpty(id) && YZUtility.isNotNullOrEmpty(typeName) && 
					YZUtility.isNotNullOrEmpty(orderno)) {
				ykzzTypeService.updateYkzzTypeById(ykzzType);
				YZUtility.DEAL_SUCCESS(null,null, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}

	/**
	 * 根据id查找子级分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findChildTypesByParentId.act")
	public void findChildTypesByParentId(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String organization = ChainUtil.getCurrentOrganization(request);
		Map<String, String> map = new HashMap<String, String>();
		map.put("organization", organization);
		map.put("id", id);
		try {
			if(YZUtility.isNotNullOrEmpty(id)) {
				List<JSONObject> list = ykzzTypeService.findChildTypesByParentId(map);
				YZUtility.RETURN_LIST(list, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}
	
	/**
	 * 获取全部分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findAllTypesByParentId.act")
	public void findAllTypesByParentId(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String organization = ChainUtil.getCurrentOrganization(request);
		try {
			List<JSONObject> list = ykzzTypeService.findAllTypes(organization);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}
	
	/**
	 * 返回上一步获取当前id对应类型的父类型及父类型名称
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/reBack.act")
	public String reBack(HttpServletRequest request,
			HttpServletResponse response){
		String id = request.getParameter("id");
		try {
			if(YZUtility.isNotNullOrEmpty(id)) {
				//获取父级分类
				YkzzType ykzzType = ykzzTypeService.findYkzzTypeById(id);
				JSONObject jo = new JSONObject();
				String perId = TYPEROOTID; //默认根节点
				String perName = TYPEROOTNAME; //默认根节点
				if(null != ykzzType) {
					perId = ykzzType.getParentid();
				}
				jo.put("perId", perId);
				ykzzType = ykzzTypeService.findYkzzTypeById(perId);
				
				if(null != ykzzType) {
					perName = ykzzType.getTypeName();
				}
				jo.put("perName", perName);
				YZUtility.RETURN_OBJ(jo, response, logger);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * 获取类型树所需数据json
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/findzTreeList.act")
	public void findzTreeList(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String organization = ChainUtil.getCurrentOrganization(request);
		try {
			List<JSONObject> list = ykzzTypeService.findAllTypes(organization);
			JSONArray jsonArray = new JSONArray();
			if(null != list && list.size()>0) {
				JSONObject jo = null;
				for(JSONObject obj : list) {
					if(!obj.getString("id").equals(TYPEROOTID)) {
						jo = new JSONObject();
						jo.put("id", obj.getString("id"));
						jo.put("pId", obj.getString("parentid").equals(TYPEROOTID)?"0":obj.getString("parentid"));
						jo.put("name", obj.getString("type_name"));
						jsonArray.add(jo);
					}
				}
			}
			YZUtility.RETURN_LIST(jsonArray, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}
	
	/**
	 * 获取对应级别的分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findTypeByLevel.act")
	public void findTypeByLevel(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String level = request.getParameter("level");
		String organization = ChainUtil.getCurrentOrganization(request);
		try {
			List<JSONObject> list = ykzzTypeService.findAllTypes(organization);
			List<JSONObject> levelList = null;
			if(level.equals("1")){ //获取一级分类
				levelList = new ArrayList<JSONObject>();
				for(JSONObject obj : list) {
					if(TYPEROOTID.equals(obj.get("parentid"))) {
						levelList.add(obj);
					}
				}
				YZUtility.RETURN_LIST(levelList, response, logger);
			}else if(level.equals("2")){//获取二级分类
				levelList = new ArrayList<JSONObject>();
				for(JSONObject obj : list) {
					if(!TYPEROOTID.equals(obj.get("parentid")) && !"0".equals(obj.get("parentid"))) {
						levelList.add(obj);
					}
				}
				YZUtility.RETURN_LIST(levelList, response, logger);
			}else { //返回全部分类
				YZUtility.RETURN_LIST(list, response, logger);
			}
			
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}
}
