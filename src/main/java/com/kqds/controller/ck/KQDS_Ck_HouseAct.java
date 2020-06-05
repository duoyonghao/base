package com.kqds.controller.ck;
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

import com.kqds.entity.base.KqdsCkHouse;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.ck.KQDS_Ck_HouseLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_Ck_HouseAct")
public class KQDS_Ck_HouseAct {
	private static Logger logger = LoggerFactory.getLogger(KQDS_Ck_HouseAct.class);
	@Autowired
	private KQDS_Ck_HouseLogic logic;

	@RequestMapping(value = "/toGoodsHouse.act")
	public ModelAndView toGoodsHouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sshouse = request.getParameter("sshouse");
		String type = request.getParameter("type");
		String menuid = request.getParameter("menuid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuid);
		mv.addObject("sshouse", sshouse);
		mv.addObject("type", type);
		mv.setViewName("/kqdsFront/ck/goodsIn/goods_house.jsp");
		return mv;
	}

	@RequestMapping(value = "/toSave.act")
	public ModelAndView toSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/ck/house/save_house.jsp");
		return mv;
	}

	@RequestMapping(value = "/toCkHouse.act")
	public ModelAndView toCkHouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/ck/house/ck_house.jsp");
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
			KqdsCkHouse dp = new KqdsCkHouse();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			if (!YZUtility.isNullorEmpty(seqId)) {
				logic.updateSingleUUID(TableNameUtil.KQDS_CK_HOUSE, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_CK_HOUSE, dp, TableNameUtil.KQDS_CK_HOUSE, request);
			} else {
				String uuid = YZUtility.getUUID();
				dp.setSeqId(uuid);
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
				logic.saveSingleUUID(TableNameUtil.KQDS_CK_HOUSE, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_HOUSE, dp, TableNameUtil.KQDS_CK_HOUSE, request);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteObj.act")
	public String deleteObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}
			KqdsCkHouse en = (KqdsCkHouse) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_HOUSE, seqId);
			logic.deleteSingleUUID(TableNameUtil.KQDS_CK_HOUSE, seqId);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_CK_HOUSE, en, TableNameUtil.KQDS_CK_HOUSE, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 详情返回
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDetail.act")
	public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			KqdsCkHouse en = (KqdsCkHouse) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_HOUSE, seqId);
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
	@RequestMapping(value = "/selectList.act")
	public String selectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("type", request.getParameter("type"));
			map.put("organization", ChainUtil.getCurrentOrganization(request));
			List<JSONObject> list = logic.selectList(map);

			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 重新初始化仓库，删除所有仓库数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/emptyCK.act")
	public String emptyCK(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			logic.emptyCK();
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}
}