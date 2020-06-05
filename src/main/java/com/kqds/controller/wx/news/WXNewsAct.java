package com.kqds.controller.wx.news;

import java.util.ArrayList;
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

import com.kqds.controller.base.printSet.KQDS_Print_SetBackAct;
import com.kqds.core.global.YZActionKeys;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.wx.WXNews;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.wx.news.WXNewsItemLogic;
import com.kqds.service.wx.news.WXNewsLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.sys.DictUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("WXNewsAct")
public class WXNewsAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_Print_SetBackAct.class);
	@Autowired
	private WXNewsLogic logic;
	@Autowired
	private WXNewsItemLogic itemLogic;
	@Autowired
	private YZDictLogic dictLogic;

	@RequestMapping(value = "/toSelect4chat.act")
	public ModelAndView toSelect4chat(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String openid = request.getParameter("openid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("openid", openid);
		mv.setViewName("/wechat/news/select4chat.jsp");
		return mv;
	}

	@RequestMapping(value = "/toListNew.act")
	public ModelAndView toListNew(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/wechat/news/list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toAddNew.act")
	public ModelAndView toAddNew(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/wechat/news/add.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEditNew.act")
	public ModelAndView toEditNew(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/wechat/news/edit.jsp");
		return mv;
	}

	@RequestMapping(value = "/toSelect4batchsend.act")
	public ModelAndView toSelect4batchsend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/wechat/news/select4batchsend.jsp");
		return mv;
	}

	/**
	 * 新建、编辑
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
			WXNews dp = new WXNews();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			if (!YZUtility.isNullorEmpty(seqId)) {
				logic.updateSingleUUID(TableNameUtil.WX_NEWS, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.WX_NEWS, dp, TableNameUtil.WX_NEWS, request);
			} else {
				String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
				if (YZUtility.isNullorEmpty(organization)) {
					organization = ChainUtil.getCurrentOrganization(request);
				}

				String uuid = YZUtility.getUUID();
				dp.setSeqId(uuid);
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setOrganization(organization); // 【后台数据维护，以页面传入的门诊编号为主】
				logic.saveSingleUUID(TableNameUtil.WX_NEWS, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.WX_NEWS, dp, TableNameUtil.WX_NEWS, request);
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

			WXNews en = (WXNews) logic.loadObjSingleUUID(TableNameUtil.WX_NEWS, seqId);
			logic.deleteSingleUUID(TableNameUtil.WX_NEWS, seqId);
			// 删除图文
			itemLogic.deleteByParentId(seqId, request);

			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.WX_NEWS, en, TableNameUtil.WX_NEWS, request);
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

			WXNews en = (WXNews) logic.loadObjSingleUUID(TableNameUtil.WX_NEWS, seqId);
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

	/*
	 * 展示
	 */
	@RequestMapping(value = "/showMessage.act")
	public String showMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");

			WXNews en = (WXNews) logic.loadObjSingleUUID(TableNameUtil.WX_NEWS, seqId);

			if (en == null) {
				throw new Exception("数据不存在");
			}

			List<JSONObject> itemList = itemLogic.getList(seqId);
			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			jobj.put("list", itemList);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/*
	 * 分页查询
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/selectPage.act")
	public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());

			Map<String, String> map = new HashMap<String, String>();

			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			// ### 门诊条件过滤
			map.put("organization", organization);

			JSONObject data = logic.selectWithPage(TableNameUtil.WX_NEWS, bp, map);
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 聊天选择使用
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/select4chat.act")
	public void select4chat(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String lv = request.getParameter("lv"); // 树的级
		List<JSONObject> listtree = new ArrayList<JSONObject>();
		try {
			// 收费项目一级分类
			if (YZUtility.isNullorEmpty(id)) {
				List<YZDict> list = dictLogic.getListByParentCode(DictUtil.WECHAT_NEWSSORT, ChainUtil.getCurrentOrganization(request));
				for (int i = 0; i < list.size(); i++) {
					YZDict base = list.get(i);
					JSONObject obj = new JSONObject();
					obj.put("id", base.getDictCode()); // 用 DICT_CODE ！！
					obj.put("pId", DictUtil.WECHAT_KEYWORD);
					obj.put("name", base.getDictName());
					obj.put("nocheck", "true");
					List<WXNews> nextlist = logic.getList(base.getSeqId());
					if (nextlist != null && nextlist.size() > 0) {
						obj.put("isParent", "true");

					} else {
						obj.put("isParent", "false");
					}
					listtree.add(obj);
				}
			}

			// 收费项目二级分类
			if (!YZUtility.isNullorEmpty(id) && !"1".equals(lv)) {
				List<WXNews> nextlist = logic.getList(id);
				for (int j = 0; j < nextlist.size(); j++) {
					WXNews next = nextlist.get(j);

					int count = itemLogic.getCountByNewsId(next.getSeqId());

					if (count > 0) {
						JSONObject obj = new JSONObject();
						obj.put("id", next.getSeqId()); // 用SEQ_ID ！！，这里不用编号
						obj.put("pId", id);
						obj.put("name", next.getNewsname());
						obj.put("nocheck", "true");
						obj.put("isParent", "false");
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

}
