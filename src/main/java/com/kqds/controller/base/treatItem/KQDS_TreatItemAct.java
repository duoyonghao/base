package com.kqds.controller.base.treatItem;

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
import org.springframework.web.servlet.ModelAndView;

import com.kqds.core.global.YZActionKeys;
import com.kqds.core.global.YZConst;
import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.service.base.treatItem.KQDS_TreatItemLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@SuppressWarnings({ "unchecked" })
@Controller
@RequestMapping("KQDS_TreatItemAct")
public class KQDS_TreatItemAct {
	private static Logger logger = LoggerFactory.getLogger(KQDS_TreatItemAct.class);
	@Autowired
	private KQDS_TreatItemLogic logic;

	@RequestMapping(value = "/toIndex.act")
	public ModelAndView toWdyySearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqds/treatItemType/index.jsp");
		return mv;
	}

	@RequestMapping(value = "/toIndexLs.act")
	public ModelAndView toIndexLs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqds/treatItem/index_ls.jsp");
		return mv;
	}

	/**
	 * 根据项目编号查询 收费项目
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOneBytreatitemno.act")
	public String getOneBytreatitemno(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String treatitemno = request.getParameter("treatitemno");
			Map<String, String> map = new HashMap<String, String>();
			map.put("treatitemno", treatitemno);
			List<KqdsTreatitem> en = (List<KqdsTreatitem>) logic.loadList(TableNameUtil.KQDS_TREATITEM, map);
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_STATES, YZConst.RETURN_OK);
			jobj.put(YZActionKeys.JSON_RET_MSRGS, "操作成功");
			if (en != null && en.size() > 0) {
				jobj.put("data", en.get(0));
			} else {
				jobj.put("data", "");
			}

			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

}