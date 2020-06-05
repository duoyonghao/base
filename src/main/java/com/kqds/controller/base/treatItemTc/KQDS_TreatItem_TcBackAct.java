package com.kqds.controller.base.treatItemTc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.entity.base.KqdsTreatitemTc;
import com.kqds.entity.base.KqdsTreatitemTcType;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.treatItem.KQDS_TreatItemLogic;
import com.kqds.service.base.treatItemTc.KQDS_TreatItem_TcLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
@RequestMapping("KQDS_TreatItem_TcBackAct")
public class KQDS_TreatItem_TcBackAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_TreatItem_TcBackAct.class);
	@Autowired
	private KQDS_TreatItem_TcLogic logic;
	@Autowired
	private KQDS_TreatItemLogic treatlogic;

	@RequestMapping(value = "/toList.act")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/treatItemTc/list_kqds_treatitem_tc.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEdit.act")
	public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String tcnameid = request.getParameter("tcnameid");
		String organization = request.getParameter("organization");
		String tctype = request.getParameter("tctype");
		String tcname = request.getParameter("tcname");
		ModelAndView mv = new ModelAndView();
		mv.addObject("tcnameid", tcnameid);
		mv.addObject("organization", organization);
		mv.addObject("tctype", tctype);
		mv.addObject("tcname", tcname);
		mv.setViewName("/kqds/treatItemTc/edit_kqds_treatitem_tc.jsp");
		return mv;
	}

	@RequestMapping(value = "/toDetail.act")
	public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String tcnameid = request.getParameter("tcnameid");
		String organization = request.getParameter("organization");
		String tctype = request.getParameter("tctype");
		String tcname = request.getParameter("tcname");
		ModelAndView mv = new ModelAndView();
		mv.addObject("tcnameid", tcnameid);
		mv.addObject("organization", organization);
		mv.addObject("tctype", tctype);
		mv.addObject("tcname", tcname);
		mv.setViewName("/kqds/treatItemTc/detail_kqds_treatitem_tc.jsp");
		return mv;
	}

	@RequestMapping(value = "/toNewAdd.act")
	public ModelAndView toNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/treatItemTc/add_kqds_treatitem_tc.jsp");
		return mv;
	}

	@RequestMapping(value = "/toTypeIndex.act")
	public ModelAndView toTypeIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/treatItemType/subindex.jsp");
		return mv;
	}

	@RequestMapping(value = "/toTypeList.act")
	public ModelAndView toTypeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentCode = request.getParameter("parentCode");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("parentCode", parentCode);
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/treatItemType/list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toTypeEdit.act")
	public ModelAndView toTypeEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqds/treatItemType/edit.jsp");
		return mv;
	}

	@RequestMapping(value = "/toTypeNewAdd.act")
	public ModelAndView toTypeNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentCode = request.getParameter("parentCode");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("parentCode", parentCode);
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/treatItemType/newAdd.jsp");
		return mv;
	}

	@RequestMapping(value = "/toTypeLeft.act")
	public ModelAndView toTypeLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/treatItemType/left.jsp");
		return mv;
	}

	/**
	 * 新建收费套餐，后端调用
	 * 
	 * 
	 */
	@RequestMapping(value = "/insertList4Back.act")
	public String insertList4Back(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String tctype = request.getParameter("tctype");
			String tcname = request.getParameter("tcname");
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			// 判断套餐类型是否存在
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", tctype);
			map.put("parentid", "0");
			map.put("organization", organization);
			logic.newAddTc(tctype, tcname, map, request);
			// 记录日志
			JSONObject json = new JSONObject();
			map.put("tctype", tctype);
			map.put("tcname", tcname);
			BcjlUtil.LogBcjl(BcjlUtil.SAVE_TC, BcjlUtil.KQDS_TREATITEM_TC_TYPE, json, TableNameUtil.KQDS_TREATITEM_TC_TYPE, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 新建收费套餐，前台调用
	 * 
	 * 
	 */
	@RequestMapping(value = "/insertList.act")
	public String insertList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String tctype = request.getParameter("tctype");
			String tcname = request.getParameter("tcname");
			String organization = ChainUtil.getCurrentOrganization(request);

			// 判断套餐类型是否存在
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", tctype);
			map.put("parentid", "0");
			map.put("organization", organization);
			logic.newAddTc(tctype, tcname, map, request);
			// 记录日志
			JSONObject json = new JSONObject();
			map.put("tctype", tctype);
			map.put("tcname", tcname);
			BcjlUtil.LogBcjl(BcjlUtil.SAVE_TC, BcjlUtil.KQDS_TREATITEM_TC_TYPE, json, TableNameUtil.KQDS_TREATITEM_TC_TYPE, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 修改套餐 【后端调用】
	 * 
	 * 
	 */
	@RequestMapping(value = "/editList.act")
	public String editList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String tcnameid = request.getParameter("tcnameid");
			String tcname = request.getParameter("tcname");
			if (YZUtility.isNullorEmpty(tcnameid)) {
				throw new Exception("tcnameid参数值为空");
			}
			KqdsTreatitemTcType tctypeNameObj = (KqdsTreatitemTcType) logic.loadObjSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, tcnameid);
			if (tctypeNameObj == null) {
				throw new Exception("根据tcnameid查询不到对应套餐记录");
			}
			if (!tctypeNameObj.getName().equals(tcname)) {
				int count = logic.checkTc(tctypeNameObj.getParentid(), tcname, tctypeNameObj.getSeqId());
				if (count > 0) {
					throw new Exception("该套餐类型下已存在该套餐名称！");
				}
				tctypeNameObj.setName(tcname);
				logic.updateSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, tctypeNameObj); // 更新套餐名字
			}

			// 删除原套餐项目
			Map<String, String> map = new HashMap<String, String>();
			map.put("tcnameid", tcnameid); // 这里不增加门诊编号过滤条件
			List<KqdsTreatitemTc> en = (List<KqdsTreatitemTc>) logic.loadList(TableNameUtil.KQDS_TREATITEM_TC, map);
			if (en != null && en.size() > 0) {
				for (KqdsTreatitemTc tc : en) {
					logic.deleteSingleUUID(TableNameUtil.KQDS_TREATITEM_TC, tc.getSeqId());
				}
			}
			// 获取的对象集合 json格式
			String listdata = request.getParameter("params");
			JSONArray jArray = JSONArray.fromObject(listdata);
			Collection collection = JSONArray.toCollection(jArray, KqdsTreatitemTc.class);
			Iterator it = collection.iterator();
			// 保存套餐明细
			KqdsTreatitemTc detail = new KqdsTreatitemTc();
			while (it.hasNext()) {
				detail = (KqdsTreatitemTc) it.next();
				detail.setTcnameid(tcnameid);
				detail.setSeqId(YZUtility.getUUID());
				detail.setArrearmoney("0");
				detail.setVoidmoney("0");
				detail.setPaymoney(detail.getSubtotal()); // 套餐的缴费金额等于小计
				detail.setCreatetime(YZUtility.getCurDateTimeStr());
				detail.setCreateuser(person.getSeqId());
				detail.setOrganization(tctypeNameObj.getOrganization()); // 门诊编号，以套餐的编号为准

				KqdsTreatitem treatitem = treatlogic.getByTreatItemno(detail.getItemno());
				if (treatitem == null) {
					throw new Exception("收费编号对应的收费项目不存在");
				}

				if (1 == treatitem.getIsyjjitem()) { // 0 请选择 1预交金 2挂号 3治疗费 4
														// 生成回访
					throw new Exception("预交金不能作为收费套餐项目");
				}

				logic.saveSingleUUID(TableNameUtil.KQDS_TREATITEM_TC, detail);
			}
			// 记录日志
			JSONObject json = new JSONObject();
			json.put("tcnameid", tcnameid);
			BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_TREATITEM_TC_TYPE, json, TableNameUtil.KQDS_TREATITEM_TC, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 公开或者关闭套餐
	 */
	@RequestMapping(value = "/openOrcloseTc.act")
	public String openOrcloseTc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String isopen = request.getParameter("isopen");
			String tcnameid = request.getParameter("tcnameid");
			KqdsTreatitemTcType en = (KqdsTreatitemTcType) logic.loadObjSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, tcnameid);
			en.setIsopen(Integer.parseInt(isopen));
			logic.updateSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, en);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectPage.act")
	public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String tctype = request.getParameter("tctype");
			String tcname = request.getParameter("tcname");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(tctype)) {
				map.put("tctype", tctype);
			}
			if (!YZUtility.isNullorEmpty(tcname)) {
				map.put("tcname", tcname);
			}

			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			map.put("organization", organization);
			JSONObject data = logic.selectWithPage(TableNameUtil.KQDS_TREATITEM_TC, bp, map);
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据套餐类型和名称查询套餐内收费项目 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectPageByTctypeAndTcname.act")
	public String selectPageByTctypeAndTcname(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String tcnameid = request.getParameter("tcnameid");
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			JSONObject jobj = logic.selectWithPageBytctypeAndname(TableNameUtil.KQDS_TREATITEM_TC, bp, tcnameid);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据套餐类型和名称查询套餐内收费项目 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectNoPageByTctypeAndTcname.act")
	public String selectNoPageByTctypeAndTcname(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String tcnameid = request.getParameter("tcnameid");
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			int total = logic.selectTcxmCount(TableNameUtil.KQDS_TREATITEM_TC, tcnameid);
			List<JSONObject> list = logic.selectNoPageBytctypeAndname(TableNameUtil.KQDS_TREATITEM_TC, bp, tcnameid);
			JSONObject jobj = new JSONObject();
			jobj.put("total", total);
			jobj.put("rows", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

}