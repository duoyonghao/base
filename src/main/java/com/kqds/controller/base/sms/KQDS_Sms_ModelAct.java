package com.kqds.controller.base.sms;

import java.util.HashMap;
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

import com.kqds.entity.base.KqdsSmsModel;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.sms.KQDS_Sms_ModelLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_Sms_ModelAct")
public class KQDS_Sms_ModelAct {
	private static Logger logger = LoggerFactory.getLogger(KQDS_SmsAct.class);
	@Autowired
	private KQDS_Sms_ModelLogic logic;
	@Autowired
	private YZDictLogic dictLogic;

	@RequestMapping(value = "/toSmsModelTree.act")
	public ModelAndView toSmsModelTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/sms/model/list_model_tree.jsp");
		return mv;
	}

	@RequestMapping(value = "/toSmsModel.act")
	public ModelAndView toWdyySearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/sms/model/sms_model.jsp");
		return mv;
	}

	@RequestMapping(value = "/toAddModel.act")
	public ModelAndView toAddModel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqdsFront/sms/model/add_model.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEditModel.act")
	public ModelAndView toEditModel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/medicalRecord/medicalrecord_cz.jsp");
		return mv;
	}

	@RequestMapping(value = "/toDetailModel.act")
	public ModelAndView toDetailModel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/medicalRecord/medicalrecord_cz.jsp");
		return mv;
	}

	/**
	 * 入库，修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsSmsModel dp = new KqdsSmsModel();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			if (!YZUtility.isNullorEmpty(seqId)) {
				logic.updateSingleUUID(TableNameUtil.KQDS_SMS_MODEL, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_SMS_MODEL, dp, TableNameUtil.KQDS_SMS_MODEL, request);
			} else {
				String uuid = YZUtility.getUUID();
				dp.setSeqId(uuid);
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【后台数据维护，以页面传入的门诊编号为主】
				logic.saveSingleUUID(TableNameUtil.KQDS_SMS_MODEL, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_SMS_MODEL, dp, TableNameUtil.KQDS_SMS_MODEL, request);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/deleteObj.act")
	public String deleteObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}

			KqdsSmsModel en = (KqdsSmsModel) logic.loadObjSingleUUID(TableNameUtil.KQDS_SMS_MODEL, seqId);
			logic.deleteSingleUUID(TableNameUtil.KQDS_SMS_MODEL, seqId);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_SMS_MODEL, en, TableNameUtil.KQDS_SMS_MODEL, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/*
	 * 详情返回
	 */
	@RequestMapping(value = "/selectDetail.act")
	public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			KqdsSmsModel en = (KqdsSmsModel) logic.loadObjSingleUUID(TableNameUtil.KQDS_SMS_MODEL, seqId);

			if (en == null) {
				throw new Exception("数据不存在");
			}

			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 不分页查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/NoselectPage.act")
	public String NoselectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String organization = request.getParameter("organization");
			String smstype = request.getParameter("smstype");
			String smsnexttype = request.getParameter("smsnexttype");

			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(organization)) {
				map.put("organization", organization);
			}
			if (!YZUtility.isNullorEmpty(smstype)) {
				map.put("smstype", smstype);
			}
			if (!YZUtility.isNullorEmpty(smsnexttype)) {
				map.put("smsnexttype", smsnexttype);
			}

			List<JSONObject> list = (List<JSONObject>) logic.noSelectWithPage(TableNameUtil.KQDS_SMS_MODEL, map);

			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
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
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/selectPage.act")
	public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String organization = request.getParameter("organization");
			String smstype = request.getParameter("smstype");
			String smsnexttype = request.getParameter("smsnexttype");

			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(organization)) {
				map.put("organization", organization);
			}
			if (!YZUtility.isNullorEmpty(smstype)) {
				map.put("smstype", smstype);
			}
			if (!YZUtility.isNullorEmpty(smsnexttype)) {
				map.put("smsnexttype", smsnexttype);
			}

			JSONObject data = logic.selectWithPage(TableNameUtil.KQDS_SMS_MODEL, bp, map);
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSelectModelTree.act")
	public void getSelectModelTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = ChainUtil.getCurrentOrganization(request);
		try {
			StringBuffer sb = new StringBuffer();
			List<YZDict> list = dictLogic.getListByParentCode("DXFL", organization);
			if (list != null && list.size() > 0) {
				sb.append("[");
				for (int i = 0; i < list.size(); i++) {
					YZDict base = list.get(i);
					sb.append("{ id:\"" + base.getSeqId() + "\", pId:\"0\", name:\"" + base.getDictName() + "\", nocheck:true},");
					List<YZDict> listnext = dictLogic.getListByParentCode(base.getDictCode(), organization);
					if (listnext != null && listnext.size() > 0) {
						for (int k = 0; k < listnext.size(); k++) {
							YZDict nexttype = listnext.get(k);
							sb.append("{ id:\"" + nexttype.getSeqId() + "\", pId:\"" + base.getSeqId() + "\", name:\"" + nexttype.getDictName() + "\"},");
							Map<String, String> map2 = new HashMap<String, String>();
							map2.put("smstype", base.getSeqId());
							map2.put("smsnexttype", nexttype.getSeqId());
							List<KqdsSmsModel> smslist = (List<KqdsSmsModel>) logic.loadList(TableNameUtil.KQDS_SMS_MODEL, map2);
							if (smslist != null && smslist.size() > 0) {
								for (int ik = 0; ik < smslist.size(); ik++) {
									KqdsSmsModel sms = smslist.get(ik);
									sb.append("{ id:\"" + sms.getSeqId() + "\", pId:\"" + nexttype.getSeqId() + "\", name:\"" + sms.getSmsname() + "\"},");
								}
							}
						}
					}
				}
				if (sb.length() > 1) {
					sb.deleteCharAt(sb.length() - 1);
				}
				sb.append("]");
			}
			JSONObject jobj = new JSONObject();
			jobj.put("tree", sb.toString());
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}
}