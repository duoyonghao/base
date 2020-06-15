package com.kqds.controller.base.push;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.hudh.tjhf.service.IAddVisitService;
import com.kqds.entity.base.KqdsPush;
import com.kqds.entity.base.KqdsVisit;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.push.KQDS_Pushogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes" })
@Controller
@RequestMapping("KQDS_PushAct")
public class KQDS_PushAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_PushAct.class);
	@Autowired
	private KQDS_Pushogic logic;
	@Autowired
	private IAddVisitService visitService;
	@RequestMapping(value = "/toPushIndex.act")
	public ModelAndView toPushIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/push/pushIndex.jsp");
		return mv;
	}

	@RequestMapping(value = "/toPushList.act")
	public ModelAndView toPushList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String iscs = request.getParameter("iscs");
		ModelAndView mv = new ModelAndView();
		mv.addObject("iscs", iscs);
		mv.setViewName("/kqdsFront/push/pushList.jsp");
		return mv;
	}

	/**
	 * 【不做门诊条件过滤】
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
			YZPerson person = SessionUtil.getLoginPerson(request);
			Map<String, String> map = new HashMap<String, String>();
			String notifytype = request.getParameter("notifytype");
			if (!YZUtility.isNullorEmpty(notifytype)) {
				map.put("notifytype", notifytype);
			}
			String pcpushed = request.getParameter("pcpushed");
			if (!YZUtility.isNullorEmpty(pcpushed)) {
				if ("已推送".equals(pcpushed)) {
					map.put("pcpushed", "1");
				} else {
					map.put("pcpushed", "0");
				}

			}
			String pcpushreaded = request.getParameter("pcpushreaded");
			if (!YZUtility.isNullorEmpty(pcpushreaded)) {
				if ("已读".equals(pcpushreaded)) {
					map.put("pcpushreaded", "1");
				} else {
					map.put("pcpushreaded", "0");
				}

			}
			JSONObject json = new JSONObject();
			String iscs = request.getParameter("iscs");
			if (!YZUtility.isNullorEmpty(iscs)) {
				map.put("iscs", iscs);
			}
			map.put("reciveuser", person.getSeqId());
			JSONObject data = logic.selectWithPage(TableNameUtil.KQDS_PUSH, bp, map,json);
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询登录用户未读消息总数
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectPageNum.act")
	public String selectPageNum(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			int total = logic.selectPageNum(person.getSeqId());
			JSONObject jobj = new JSONObject();
			jobj.put("total", total);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 更新推送消息状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateReadStatus.act")
	public String updateReadStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			KqdsPush dp = new KqdsPush();
			BeanUtils.populate(dp, request.getParameterMap());
			if(YZUtility.isNullorEmpty(dp.getSeqId())){
				YZPerson person = SessionUtil.getLoginPerson(request);
				dp.setReciveuser(person.getSeqId());
			}
			logic.updateReadStatus(dp);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.UPDATE_STATUS, BcjlUtil.KQDS_PUSH, dp, TableNameUtil.KQDS_PUSH, request);

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 更新推送消息状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePushStatus.act")
	public String updatePushStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			KqdsPush dp = new KqdsPush();
			BeanUtils.populate(dp, request.getParameterMap());
			logic.updatePushStatus(dp);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.UPDATE_STATUS, BcjlUtil.KQDS_PUSH, dp, TableNameUtil.KQDS_PUSH, request);

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
	
	@RequestMapping(value = "/selectTop5ByTime.act")
	public String selectTop5ByTime(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			//获取最新的五条推送数据
			String userId=request.getParameter("subPageUserId");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			String time = df.format(new Date()); 
			//根据人员查询当前时间下的回访
			Map<String,String> map2=new HashMap<String,String>();
			YZPerson person = SessionUtil.getLoginPerson(request);
			map2.put("seqid", person.getSeqId());
			map2.put("time", time);
			map2.put("reciveuser", person.getSeqId());
			map2.put("notifyType", "回访提醒");
			map2.put("organization", ChainUtil.getCurrentOrganization(request));
			map2.put("starttime", time + ConstUtil.TIME_START);  
			map2.put("endtime", time + ConstUtil.TIME_END);  
			//查询判断是否已经推送当天数据
			String seqid=logic.selectPushSeqid(map2);
			if(YZUtility.isNullorEmpty(seqid)){
				int l=visitService.findvisitByTimeNum(map2);
				if(l>0){
					KqdsVisit visit = new KqdsVisit();
					visit.setVisitor(person.getSeqId());
					visit.setUsername(person.getUserName());
					visit.setVisittime(time);
					PushUtil.selectTx4NewVisit(visit, request);
				}
			}
			Map<String,String> map1=new HashMap<String,String>();
			map1.put("userId", userId);
			map1.put("starttime", time + ConstUtil.TIME_START);  
			map1.put("endtime", time + ConstUtil.TIME_END);
			List<JSONObject> list = (List<JSONObject>) logic.selectTop5ByTime(map1);
			// 记录日志
			BcjlUtil.LogBcjl("查询推送消息", BcjlUtil.KQDS_PUSH, userId, TableNameUtil.KQDS_PUSH, request);
			YZUtility.RETURN_LIST(list, response, logger);
			//YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
}