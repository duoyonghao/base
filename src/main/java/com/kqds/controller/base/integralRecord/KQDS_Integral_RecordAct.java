package com.kqds.controller.base.integralRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.entity.base.KqdsIntegralRecord;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.integralRecord.KQDS_Integral_RecordLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportBean;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.sys.DictUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "unchecked" })
@Controller
@RequestMapping("KQDS_Integral_RecordAct")
public class KQDS_Integral_RecordAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_Integral_RecordAct.class);
	@Autowired
	private KQDS_Integral_RecordLogic logic;
	@Autowired
	private YZDictLogic dictLogic;

	@RequestMapping(value = "/toJfcxCenter.act")
	public ModelAndView toJfcxCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/hzjd/jfcx_center.jsp");
		return mv;
	}

	@RequestMapping(value = "/toAddIntegral.act")
	public ModelAndView toAddIntegral(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/hzjd/add_integral.jsp");
		return mv;
	}

	@RequestMapping(value = "/toSubIntegral.act")
	public ModelAndView toSubIntegral(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/hzjd/sub_integral.jsp");
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
	@RequestMapping(value = "/insert.act")
	public String insert(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsIntegralRecord dp = new KqdsIntegralRecord();
			BeanUtils.populate(dp, request.getParameterMap());
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("usercode", dp.getUsercode());
			List<KqdsUserdocument> userlist = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map2);
			if (userlist == null) {
				throw new Exception("患者不存在！");
			}
			KqdsUserdocument u = userlist.get(0);

			// 产生的积分
			u.setIntegral(u.getIntegral().add(dp.getJfmoney()));
			logic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, u);

			String jfzj = dictLogic.getDictIdByNameAndParentCode(DictUtil.JFLX, "手动增加");
			String jfjs = dictLogic.getDictIdByNameAndParentCode(DictUtil.JFLX, "手动减少");
			if (YZUtility.isNullorEmpty(jfzj) || YZUtility.isNullorEmpty(jfjs)) {
				throw new Exception("积分类型不存在！");
			}
			if (dp.getJftype().equals(ConstUtil.JFZJ)) {
				dp.setJftype(jfzj);
			} else if (dp.getJftype().equals(ConstUtil.JFJS)) {
				dp.setJftype(jfjs);
			}
			// 保存积分记录
			dp.setSeqId(UUID.randomUUID().toString());
			dp.setCreatetime(YZUtility.getCurDateTimeStr());
			dp.setOrganization(ChainUtil.getCurrentOrganization(request));
			dp.setSyjfmoney(u.getIntegral());
			dp.setCreateuser(person.getSeqId());
			logic.saveSingleUUID(TableNameUtil.KQDS_INTEGRAL_RECORD, dp);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getRecordList.act")
	public String getRecordList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			BootStrapPage bp = new BootStrapPage();
			BeanUtils.populate(bp, request.getParameterMap());
			YZPerson person = SessionUtil.getLoginPerson(request);
			String fzsj = request.getParameter("fzsj");
			String fzsj2 = request.getParameter("fzsj2");
			String usercodeorname = request.getParameter("usercodeorname");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");

			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(fzsj)) {
				fzsj = fzsj + ConstUtil.TIME_START;
				map.put("fzsj", fzsj);
			}
			if (!YZUtility.isNullorEmpty(fzsj2)) {
				fzsj2 = fzsj2 + ConstUtil.TIME_END;
				map.put("fzsj2", fzsj2);
			}
			if (!YZUtility.isNullorEmpty(usercodeorname)) {
				map.put("usercodeorname", usercodeorname);
			}
			// 根据所选门诊进行查询
			String organization = ChainUtil.getOrganizationFromUrl(request);
			if (YZUtility.isNullorEmpty(organization)) {
				// 根据所选门诊进行查询
				map.put("organization", ChainUtil.getCurrentOrganization(request));
			} else {
				map.put("organization", organization);
			}
			// 是否分页
			String ispaging = request.getParameter("ispaging");
			// 是否分页 1 分页 0 不分页
			if (!YZUtility.isNullorEmpty(ispaging) && YZUtility.isNullorEmpty(flag)) {
				map.put("ispaging", ispaging);
			} else {
				map.put("ispaging", ConstUtil.PAGE_FLAG_0);
			}
			String visualstaff = SessionUtil.getVisualstaff(request);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				JSONObject resut1 = logic.selectWithPage(bp, TableNameUtil.KQDS_INTEGRAL_RECORD, person, map, visualstaff);
				if (resut1 != null) {
					ExportBean bean = ExportTable.initExcel4RsWrite("积分查询", fieldArr, fieldnameArr, response, request);
					bean = ExportTable.exportBootStrapTable2ExcelByResult(bean, (List<JSONObject>) resut1.get("rows"), "");
					ExportTable.writeExcel4DownLoad("积分查询", bean.getWorkbook(), response);
				}
				return null;
			}
			JSONObject data = logic.selectWithPage(bp, TableNameUtil.KQDS_INTEGRAL_RECORD, person, map, visualstaff);

			if (!YZUtility.isNullorEmpty(ispaging)) {
				YZUtility.DEAL_SUCCESS(data, null, response, logger);
			} else {
				YZUtility.RETURN_LIST((List<JSONObject>) data.getJSONArray("rows"), response, logger);
			}
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
}