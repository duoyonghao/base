package com.kqds.controller.base.dict;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.core.global.YZActionKeys;
import com.kqds.entity.base.KqdsOutprocessing;
import com.kqds.entity.base.KqdsOutprocessingType;
import com.kqds.entity.base.KqdsOutprocessingUnit;
import com.kqds.service.base.outProcessing.KQDS_OutProcessingLogic;
import com.kqds.service.base.outProcessing.KQDS_OutProcessing_TypeLogic;
import com.kqds.service.base.outProcessingUnit.KQDS_outProcessing_UnitLogic;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("YZDictJGAct")
public class YZDictJGAct {

	private Logger logger = LoggerFactory.getLogger(YZDictJGAct.class);
	@Autowired
	private KQDS_outProcessing_UnitLogic unitLogic;
	@Autowired
	private KQDS_OutProcessing_TypeLogic typeLogic;
	@Autowired
	private KQDS_OutProcessingLogic itemLogic;

	/**
	 * 用于加工项目后台管理
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getJgTreeAsync.act")
	public void getJgTreeAsync(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String lv = request.getParameter("lv"); // 树的级
		String search = request.getParameter("search");
		String mrjgc = request.getParameter("mrjgc");
		String isAdd = null; // 如果isAdd有值，说明是新增页面；目前后台的患者来源有启用、禁用功能，新增页面，查询的列表不包含被禁用的信息
		List<JSONObject> listtree = new ArrayList<JSONObject>();
		String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
		try {
			// 加工厂列表
			if (YZUtility.isNullorEmpty(id)) {
				List<KqdsOutprocessingUnit> list = unitLogic.getJgcDictList4Back(search, mrjgc, isAdd, organization);
				for (int i = 0; i < list.size(); i++) {
					KqdsOutprocessingUnit base = list.get(i);
					int count = typeLogic.countJgTypeList(base.getCode(), search, isAdd, base.getOrganization());
					JSONObject obj = new JSONObject();
					obj.put("id", base.getCode());
					obj.put("pId", "0");
					obj.put("name", base.getName());
					obj.put("nocheck", "true");
					obj.put("isParent", count > 0 ? true : false);
					listtree.add(obj);
				}
			}

			// 加工类别列表
			if (!YZUtility.isNullorEmpty(id) && !"1".equals(lv)) {
				List<KqdsOutprocessingType> nextlist = typeLogic.getjgTypeList(id, search, isAdd, organization);
				for (int j = 0; j < nextlist.size(); j++) {
					KqdsOutprocessingType next = nextlist.get(j);
					JSONObject obj = new JSONObject();
					obj.put("id", next.getTypeno());
					obj.put("pId", id);
					obj.put("name", next.getTypename());
					obj.put("nocheck", "true");
					obj.put("isParent", false);
					listtree.add(obj);
				}
			}

			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, listtree);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/**
	 * 加工单 异步加载治疗项目树
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSelectJgTreeAsync.act")
	public void getSelectJgTreeAsync(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String lv = request.getParameter("lv"); // 树的级
		String search = request.getParameter("search");
		String mrjgc = request.getParameter("mrjgc");
		String isAdd = request.getParameter("isAdd"); // 如果isAdd有值，说明是新增页面；目前后台的患者来源有启用、禁用功能，新增页面，查询的列表不包含被禁用的信息
		List<JSONObject> listtree = new ArrayList<JSONObject>();
		String organization = ChainUtil.getCurrentOrganization(request);
		try {

			// 加工厂列表
			if (YZUtility.isNullorEmpty(id)) {
				List<KqdsOutprocessingUnit> list = unitLogic.getJgcDictList(search, mrjgc, isAdd, organization);
				for (int i = 0; i < list.size(); i++) {
					KqdsOutprocessingUnit base = list.get(i);
					int count = typeLogic.countJgTypeList(base.getCode(), search, isAdd, base.getOrganization());
					JSONObject obj = new JSONObject();
					obj.put("id", base.getCode());
					obj.put("pId", "0");
					obj.put("name", base.getName());
					obj.put("nocheck", "true");
					obj.put("isParent", count > 0 ? true : false);
					listtree.add(obj);
				}
			}

			// 加工项目列表
			if (!YZUtility.isNullorEmpty(id) && "1".equals(lv)) {
				List<KqdsOutprocessing> itemlist = itemLogic.getjgItemListByType(id, search, isAdd, null); // 这里不传organization，只用加工厂分类编号作为过滤条件
				for (int k = 0; k < itemlist.size(); k++) {
					KqdsOutprocessing item = itemlist.get(k);
					JSONObject obj = new JSONObject();
					obj.put("id", item.getWjgxmbh());
					obj.put("pId", id);
					obj.put("name", item.getWjgmc());
					listtree.add(obj);
				}
			}

			// 加工类别列表
			if (!YZUtility.isNullorEmpty(id) && !"1".equals(lv)) {
				List<KqdsOutprocessingType> nextlist = typeLogic.getjgTypeList(id, search, isAdd, null); // 这里不传organization，只用加工厂长编号作为过滤条件
				for (int j = 0; j < nextlist.size(); j++) {
					KqdsOutprocessingType next = nextlist.get(j);
					int count = itemLogic.countjgItemListByType(next.getTypeno(), search, isAdd, next.getOrganization()); // 使用加工分类所属的organization进行查询
					JSONObject obj = new JSONObject();
					obj.put("id", next.getTypeno());
					obj.put("pId", id);
					obj.put("name", next.getTypename());
					obj.put("nocheck", "true");
					obj.put("isParent", count > 0 ? true : false);
					listtree.add(obj);
				}
			}

			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, listtree);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

}
