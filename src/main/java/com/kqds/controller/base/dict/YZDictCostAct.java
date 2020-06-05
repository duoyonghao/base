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
import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.entity.base.KqdsTreatitemTcType;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.treatItem.KQDS_TreatItemLogic;
import com.kqds.service.base.treatItemTc.KQDS_TreatItem_TcLogic;
import com.kqds.service.sys.dict.YZDictCostLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.sys.DictUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("YZDictCostAct")
public class YZDictCostAct {

	private Logger logger = LoggerFactory.getLogger(YZDictCostAct.class);
	@Autowired
	private YZDictCostLogic dictCostLogic;
	@Autowired
	private KQDS_TreatItemLogic treatItemLogic;
	@Autowired
	private KQDS_TreatItem_TcLogic tcLogic;

	/**
	 * 该方法用于后台收费项目维护
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTreatSortTree.act")
	public void getTreatSortTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String lv = request.getParameter("lv"); // 树的级
		String search = request.getParameter("search");
		String organization = ChainUtil.getOrganizationFromUrlCanNull(request); // 从页面传值
		List<JSONObject> listtree = new ArrayList<JSONObject>();
		try {
			// 收费项目一级分类
			if (YZUtility.isNullorEmpty(id)) {
				List<JSONObject> list = dictCostLogic.getLeve1SortList4Manager(organization);
				for (int i = 0; i < list.size(); i++) {
					JSONObject obj = list.get(i);
					String basecount = YZUtility.isNullorEmpty(obj.getString("basecount")) ? "0" : obj.getString("basecount");
					obj.put("id", obj.getString("dictCode")); // 用 DICT_CODE ！！
					obj.put("pId", DictUtil.COSTITEM_SORT);
					obj.put("name", obj.getString("dictName") + "[" + basecount + "]");
					obj.put("nocheck", "true");
					List<YZDict> nextlist = dictCostLogic.getLeve2SortList(obj.getString("dictCode"), search, ChainUtil.getCurrentOrganization(request));
					if (nextlist.size() > 0) {
						obj.put("isParent", "true");
					} else {
						obj.put("isParent", "false");
					}
					listtree.add(obj);
				}
			}

			// 收费项目二级分类
			if (!YZUtility.isNullorEmpty(id) && !"1".equals(lv)) {
				List<YZDict> nextlist = dictCostLogic.getLeve2SortList(id, search, ChainUtil.getCurrentOrganization(request));
				for (int j = 0; j < nextlist.size(); j++) {
					YZDict next = nextlist.get(j);
					JSONObject obj = new JSONObject();
					obj.put("id", next.getSeqId()); // 用SEQ_ID ！！，这里不用编号
					obj.put("pId", id);
					obj.put("name", next.getDictName());
					obj.put("nocheck", "true");
					obj.put("isParent", "false");
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
	 * 异步加载治疗项目树，用于套餐管理，新增套餐
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSelectTreeAsync4TcManager.act")
	public void getSelectTreeAsync4TcManager(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String lv = request.getParameter("lv"); // 树的级
		String noyjj = request.getParameter("noyjj");// 后台添加套参的树 不显示预交金项目
		String search = request.getParameter("search");
		List<JSONObject> listtree = new ArrayList<JSONObject>();
		String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
		try {

			// 收费项目一级分类
			if (YZUtility.isNullorEmpty(id)) {
				List<YZDict> list = dictCostLogic.getLeve1SortList(search, organization);
				for (int i = 0; i < list.size(); i++) {
					YZDict base = list.get(i);
					JSONObject obj = new JSONObject();
					obj.put("id", base.getDictCode()); // 用 DICT_CODE ！！
					obj.put("pId", DictUtil.COSTITEM_SORT);
					obj.put("name", base.getDictName());
					obj.put("nocheck", "true");
					List<YZDict> nextlist = dictCostLogic.getLeve2SortList(base.getDictCode(), search, organization);
					if (nextlist != null && nextlist.size() > 0) {
						boolean flag = false;
						for (int j = 0; j < nextlist.size(); j++) {
							YZDict next = nextlist.get(j);
							List<KqdsTreatitem> itemlist = treatItemLogic.getTreatItemListByNextType(next.getSeqId(), search, noyjj);
							if (itemlist != null && itemlist.size() > 0) {
								flag = true;
							}
						}
						if (flag) {
							obj.put("isParent", "true");
							listtree.add(obj);
						} else {
							obj.put("isParent", "false");
						}
					}
				}
			}

			// 收费项目二级分类
			if (!YZUtility.isNullorEmpty(id) && !"1".equals(lv)) {
				List<YZDict> nextlist = dictCostLogic.getLeve2SortList(id, search, organization);
				for (int j = 0; j < nextlist.size(); j++) {
					YZDict next = nextlist.get(j);
					int count = treatItemLogic.getCountByNextType(next.getSeqId(), null);
					JSONObject obj = new JSONObject();
					obj.put("id", next.getSeqId()); // 用SEQ_ID ！！，这里不用编号
					obj.put("pId", id);
					obj.put("name", next.getDictName());
					obj.put("nocheck", "true");
					if (count > 0) {
						// 有子类 isParent true 有子分类才显示
						obj.put("isParent", "true");
						listtree.add(obj);
					} else {
						obj.put("isParent", "false");
					}
				}
			}

			// 收费项目三级分类，收费项目列表
			if (!YZUtility.isNullorEmpty(id) && "1".equals(lv)) {
				List<KqdsTreatitem> itemlist = treatItemLogic.getTreatItemListByNextType(id, search, noyjj);
				if (itemlist != null && itemlist.size() > 0) {
					for (int k = 0; k < itemlist.size(); k++) {
						JSONObject obj = new JSONObject();
						KqdsTreatitem item = itemlist.get(k);
						obj.put("id", item.getTreatitemno());
						obj.put("pId", id);
						obj.put("name", item.getTreatitemname());
						listtree.add(obj);
					}
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
	 * 异步加载治疗项目树
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSelectTreeAsync.act")
	public void getSelectTreeAsync(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		String lv = request.getParameter("lv"); // 树的级
		String noyjj = request.getParameter("noyjj");// 后台添加套参的树 不显示预交金项目
		String search = request.getParameter("search");
		List<JSONObject> listtree = new ArrayList<JSONObject>();
		try {

			// 收费项目一级分类
			if (YZUtility.isNullorEmpty(id)) {
				List<YZDict> list = dictCostLogic.getLeve1SortList(search, ChainUtil.getCurrentOrganization(request));
				for (int i = 0; i < list.size(); i++) {
					YZDict base = list.get(i);
					JSONObject obj = new JSONObject();
					obj.put("id", base.getDictCode()); // 用 DICT_CODE ！！
					obj.put("pId", DictUtil.COSTITEM_SORT);
					obj.put("name", base.getDictName());
					obj.put("nocheck", "true");
					List<YZDict> nextlist = dictCostLogic.getLeve2SortList(base.getDictCode(), search, ChainUtil.getCurrentOrganization(request));
					if (nextlist != null && nextlist.size() > 0) {
						boolean flag = false;
						for (int j = 0; j < nextlist.size(); j++) {
							YZDict next = nextlist.get(j);
							List<KqdsTreatitem> itemlist = treatItemLogic.getTreatItemListByNextType(next.getSeqId(), search, noyjj);
							if (itemlist != null && itemlist.size() > 0) {
								flag = true;
							}
						}
						if (flag) {
							obj.put("isParent", "true");
							listtree.add(obj);
						} else {
							obj.put("isParent", "false");
						}
					}
				}
			}

			// 收费项目二级分类
			if (!YZUtility.isNullorEmpty(id) && !"1".equals(lv)) {
				List<YZDict> nextlist = dictCostLogic.getLeve2SortList(id, search, ChainUtil.getCurrentOrganization(request));
				for (int j = 0; j < nextlist.size(); j++) {
					YZDict next = nextlist.get(j);
					int count = treatItemLogic.getCountByNextType(next.getSeqId(), null);
					JSONObject obj = new JSONObject();
					obj.put("id", next.getSeqId()); // 用SEQ_ID ！！，这里不用编号
					obj.put("pId", id);
					obj.put("name", next.getDictName());
					obj.put("nocheck", "true");
					if (count > 0) {
						// 有子类 isParent true 有子分类才显示
						obj.put("isParent", "true");
						listtree.add(obj);
					} else {
						obj.put("isParent", "false");
					}
				}
			}

			// 收费项目三级分类，收费项目列表
			if (!YZUtility.isNullorEmpty(id) && "1".equals(lv)) {
				List<KqdsTreatitem> itemlist = treatItemLogic.getTreatItemListByNextType(id, search, noyjj);
				if (itemlist != null && itemlist.size() > 0) {
					for (int k = 0; k < itemlist.size(); k++) {
						JSONObject obj = new JSONObject();
						KqdsTreatitem item = itemlist.get(k);
						obj.put("id", item.getTreatitemno());
						obj.put("pId", id);
						obj.put("name", item.getTreatitemname());
						listtree.add(obj);
					}
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
	 * 费用添加页面，异步加载治疗套餐
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSelectTreeTcAsync.act")
	public void getSelectTreeTcAsync(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		List<JSONObject> listtree = new ArrayList<JSONObject>();
		YZPerson person = SessionUtil.getLoginPerson(request);
		try {

			// 套餐类别
			if (YZUtility.isNullorEmpty(id)) {
				List<KqdsTreatitemTcType> list = tcLogic.getTcTypeList(ChainUtil.getCurrentOrganization(request));
				for (int i = 0; i < list.size(); i++) {
					KqdsTreatitemTcType base = list.get(i);
					int count = tcLogic.countTcNameByTcType(base.getSeqId(), person.getSeqId(), ChainUtil.getCurrentOrganization(request));
					JSONObject obj = new JSONObject();
					obj.put("id", base.getSeqId());
					obj.put("name", base.getName());
					obj.put("pId", "0");
					obj.put("nocheck", "true");
					obj.put("isParent", count > 0 ? true : false);
					if (count > 0) {
						obj.put("isParent", true);
						listtree.add(obj);
					} else {
						obj.put("isParent", false);
					}

				}

			} else {
				// 按照父id 返回子节点
				List<KqdsTreatitemTcType> nextlist = tcLogic.getTcNameListByTcType(id, person.getSeqId(), ChainUtil.getCurrentOrganization(request));
				for (int j = 0; j < nextlist.size(); j++) {
					JSONObject obj = new JSONObject();
					KqdsTreatitemTcType next = nextlist.get(j);
					obj.put("id", next.getSeqId());
					obj.put("name", next.getName());
					obj.put("pId", id);
					obj.put("isParent", "false");
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
	 * 获取收费项目一级分类，根据门诊编号
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLeve1SortListOrg.act")
	public void getLeve1SortListOrg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<YZDict> list = dictCostLogic.getLeve1SortListOrg(null, ChainUtil.getOrganizationFromUrlCanNull(request));
			JSONObject jobj = new JSONObject();
			jobj.put("list", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/**
	 * 根据收费项目一级分类，获取二级分类列表，根据门诊编号
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLeve2SortListOrg.act")
	public void getLeve2SortListOrg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String parentCode = request.getParameter("parentCode");
			if (YZUtility.isNullorEmpty(parentCode)) {
				throw new Exception("父级CODE不能为空");
			}
			// 通过父级编号去查子列表
			List<YZDict> list = dictCostLogic.getLeve2SortListOrg(parentCode, null, ChainUtil.getOrganizationFromUrlCanNull(request));
			JSONObject jobj = new JSONObject();
			jobj.put("list", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/**
	 * 获取收费项目一级分类
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLeve1SortList.act")
	public void getLeve1SortList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<YZDict> list = dictCostLogic.getLeve1SortList(null, ChainUtil.getCurrentOrganization(request));
			JSONObject jobj = new JSONObject();
			jobj.put("list", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/**
	 * 根据收费项目一级分类，获取二级分类列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLeve2SortList.act")
	public void getLeve2SortList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String parentCode = request.getParameter("parentCode");
			if (YZUtility.isNullorEmpty(parentCode)) {
				throw new Exception("父级CODE不能为空");
			}
			// 通过父级编号去查子列表
			List<YZDict> list = dictCostLogic.getLeve2SortList(parentCode, null, ChainUtil.getCurrentOrganization(request));
			JSONObject jobj = new JSONObject();
			jobj.put("list", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/**
	 * 根据收费项目一、二级分类，获取收费项目列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSfxmSelect.act")
	public void getSfxmSelect(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String basetype = request.getParameter("basetype");
		String nexttype = request.getParameter("nexttype");
		try {
			List<JSONObject> list = null;
			if (basetype != null && basetype.equals("")) {
				list = treatItemLogic.getAllsfxmSelect(ChainUtil.getCurrentOrganization(request));
			} else {
				list = treatItemLogic.getSfxmSelectParam(basetype, nexttype, ChainUtil.getCurrentOrganization(request));
			}
			JSONObject jobj = new JSONObject();
			jobj.put("list", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}
	/****************************************
	 * 消费项目分类 END
	 ************************************************/

}
