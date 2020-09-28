package com.kqds.controller.base.reg;

// 合并测试
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.SysexMessage;

import com.kqds.entity.sys.YZPriv;
import com.kqds.service.sys.priv.YZPrivLogic;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.core.global.YZActionKeys;
import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.jzmdType.KQDS_JzqkLogic;
import com.kqds.service.base.receiveinfo.KQDS_ReceiveInfoLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.CacheUtil;
import com.kqds.util.sys.sys.DictUtil;
import com.kqds.util.sys.sys.SysParaUtil;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
@RequestMapping("KQDS_REGAct")
public class KQDS_REGAct {
	private static Logger logger = LoggerFactory.getLogger(KQDS_REGAct.class);
	@Autowired
	private KQDS_REGLogic logic;
	@Autowired
	private KQDS_UserDocumentLogic userlogic;
	@Autowired
	private KQDS_CostOrderLogic costLogic;
	@Autowired
	private YZDictLogic dictLogic;
	@Autowired
	private KQDS_JzqkLogic jzqkLogic;
	@Autowired
	private KQDS_ReceiveInfoLogic receiveLogic;
	@Autowired
	private YZPersonLogic personLogic;
	@Autowired
	private YZPrivLogic privLogic;

	@RequestMapping(value = "/toJrhzCenter.act")
	public ModelAndView toJrhzCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/jdzx/jrhz_center.jsp");
		return mv;
	}

	@RequestMapping(value = "/toJrhzCenter1.act")
	public ModelAndView toJrhzCenter1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("isyx", 1);
		mv.setViewName("/kqdsFront/index/jdzx/jrhz_center.jsp");
		return mv;
	}

	@RequestMapping(value = "/toJrhzCenter2.act")
	public ModelAndView toJrhzCenter2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("isyx", 2);
		mv.setViewName("/kqdsFront/index/jdzx/jrhz_center.jsp");
		return mv;
	}

	@RequestMapping(value = "/toJdzxCenter.act")
	public ModelAndView toJdzxCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/jdzx/jdzx_center.jsp");
		return mv;
	}

	@RequestMapping(value = "/toZxzxCenter.act")
	public ModelAndView toZxzxCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/zxzx/zxzx_center.jsp");
		return mv;
	}

	@RequestMapping(value = "/toYlzxCenter.act")
	public ModelAndView toYlzxCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/ylzx/ylzx_center.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEditRegRecesort.act")
	public ModelAndView toEditRegRecesort(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String regSeqId = request.getParameter("regSeqId");
		String recesortvalue = request.getParameter("recesortvalue");
		ModelAndView mv = new ModelAndView();
		mv.addObject("regSeqId", regSeqId);
		mv.addObject("recesortvalue", recesortvalue);
		mv.setViewName("/kqdsFront/reg/chufuzhenModify.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEditRegReason.act")
	public ModelAndView toEditRegReason(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/reg/editReason.jsp");
		return mv;
	}

	@RequestMapping(value = "/toAddReg.act")
	public ModelAndView toAddReg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String orderno = request.getParameter("orderno");
		String netorderid = request.getParameter("netorderid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("orderno", orderno);
		mv.addObject("netorderid", netorderid);
		mv.setViewName("/kqdsFront/reg/reg_add.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEditReg.act")
	public ModelAndView toEditReg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		ModelAndView mv = new ModelAndView();
		mv.addObject("username", username);
		mv.setViewName("/kqdsFront/reg/reg_edit.jsp");
		return mv;
	}

	/**
	  * @Title: toChufuzhenModify   
	  * @Description: TODO(修改就诊分类)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: ModelAndView
	  * @dateTime:2019年7月18日 上午10:13:45
	 */
	@RequestMapping(value = "/toChufuzhenModify.act")
	public ModelAndView toChufuzhenModify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String regSeqId = request.getParameter("regSeqId");
		String recesortvalue = request.getParameter("recesortvalue");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("regSeqId", regSeqId);
		mv.addObject("recesortvalue", recesortvalue);
		mv.addObject("organization", organization);
		mv.setViewName("/kqdsFront/reg/chufuzhenModify.jsp");
		return mv;
	}
	
	/**
	  * @Title: toChufuzhenModify   
	  * @Description: TODO(修改挂号分类)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: ModelAndView
	  * @dateTime:2019年7月18日 上午10:14:01
	 */
	@RequestMapping(value = "/toUpdaeRegModify.act")
	public ModelAndView toUpdaeRegModify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String regSeqId = request.getParameter("regSeqId");
		String regsortvalue = request.getParameter("regsortvalue");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("regSeqId", regSeqId);
		mv.addObject("regsortvalue", regsortvalue);
		mv.addObject("organization", organization);
		mv.setViewName("/kqdsFront/reg/updateRegModify.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEditReason.act")
	public ModelAndView toEditReason(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/reg/editReason.jsp");
		return mv;
	}

	@RequestMapping(value = "/toJzcxCenter.act")
	public ModelAndView toJzcxCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/jdzx/jzcx_center.jsp");
		return mv;
	}
	/**
	 * 开票信息查询页面
	 * <p>Title: toKpxxcxCenter</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月10日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toKpxxcxCenter.act")
	public ModelAndView toKpxxcxCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/jdzx/kpxxcx_center.jsp");
		return mv;
	}
	
	
	
	//添加到诊查询
	@RequestMapping(value = "/toDzQuery.act")
	public ModelAndView toDzQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/jdzx/dz_query.jsp");
		return mv;
	}

	/**
	 * 修改初复诊
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/chufuzhenModify.act")
	public String chufuzhenModify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			String recesort = request.getParameter("recesort");
			String organization = request.getParameter("organization"); // 门诊条件过滤
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("seqId不能为空");
			}

			if (YZUtility.isNullorEmpty(recesort)) {
				throw new Exception("recesort不能为空");
			}

			KqdsReg reg = (KqdsReg) logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);

			if (reg == null) {
				throw new Exception("挂号记录不存在");
			}
			reg.setRecesort(recesort);
			logic.updateSingleUUID(TableNameUtil.KQDS_REG, reg);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	
	/**
	 * 修改挂号
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/updateRegModify.act")
	public String updateRegModify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			String regsort = request.getParameter("regsort");
			String organization = request.getParameter("organization"); // 门诊条件过滤
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("seqId不能为空");
			}

			if (YZUtility.isNullorEmpty(regsort)) {
				throw new Exception("recesort不能为空");
			}

			KqdsReg reg = (KqdsReg) logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);

			if (reg == null) {
				throw new Exception("挂号记录不存在");
			}
			reg.setRegsort(regsort);
			logic.updateSingleUUID(TableNameUtil.KQDS_REG, reg);
			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_CHANGE_KEFU, reg, regsort, TableNameUtil.KQDS_CHANGE_KEFU, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据患者编号，获取上一次开单信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLastestCostOrderInfo.act")
	public String getLastestCostOrderInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			if (YZUtility.isNullorEmpty(usercode)) {
				throw new Exception("患者编号不能为空");
			}
			List<JSONObject> list = costLogic.getLastestCostOrderInfo(usercode);
			JSONObject jobj = new JSONObject();
			if (list != null && list.size() > 0) {
				jobj.put("data", list.get(0)); // true 第一次挂号 false不是第一次挂号
			}
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 查询患者24小时内的初诊挂号记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getChuZhenIn24Hours.act")
	public String getChuZhenIn24Hours(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			if (YZUtility.isNullorEmpty(usercode)) {
				throw new Exception("患者编号不能为空");
			}
			List<JSONObject> list = logic.getChuZhenIn24Hours(usercode, request);

			JSONObject jobj = new JSONObject();
			if (list != null && list.size() > 0) {
				jobj.put("data", list.get(0)); // 24小时内存在初诊
			}
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 根据患者编号，获取上一次挂号信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLastestRegInfo.act")
	public String getLastestRegInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			if (YZUtility.isNullorEmpty(usercode)) {
				throw new Exception("患者编号不能为空");
			}
			List<JSONObject> list = logic.getLastestRegInfo(usercode);
			JSONObject jobj = new JSONObject();
			if (list != null && list.size() > 0) {
				jobj.put("data", list.get(0)); // true 第一次挂号 false不是第一次挂号
			}
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 验证患者是否是初诊
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/isFirstReg.act")
	public String isFirstReg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			String seqId = request.getParameter("seqId");
			boolean flag = false;
			Map<String, String> map = new HashMap<String, String>();
			map.put("usercode", usercode);
			int regNum = logic.selectCount(TableNameUtil.KQDS_REG, map);
			if (YZUtility.isNullorEmpty(seqId)) { // 新增
				if (regNum == 0) { // 0条挂号记录
					flag = true;
				}
			} else {
				KqdsReg reg = (KqdsReg) logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);
				if (reg == null) {
					throw new Exception("挂号记录不存在！");
				}
				String syspara_recesort = SysParaUtil.getSysValueByName(request, SysParaUtil.JZFL_CHUZHEN_SEQID);
				if (YZUtility.isStrInArrayEach(reg.getRecesort(), syspara_recesort)) { // 初诊编号在配置文件中配置的
					flag = true;
				}
			}

			JSONObject jobj = new JSONObject();
			jobj.put("data", flag); // true 第一次挂号 false不是第一次挂号
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 撤销挂号
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/cancelReg.act")
	public String cancelReg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String seqId = request.getParameter("seqId");
			String editreason = request.getParameter("editreason");
			KqdsReg reg = (KqdsReg) logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);
			if (reg == null) {
				throw new Exception("挂号记录不存在！");
			}
			// 保存修改前内容 没有修改的不保存 (拼json 方便前台取值)
			reg.setBeforeeditreason(JSONObject.fromObject(reg).toString());
			reg.setEditreason(editreason);
			reg.setDel(1);// 1 已删除
			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.CANCEL, BcjlUtil.KQDS_REG, reg, reg.getUsercode(), TableNameUtil.KQDS_REG, request);
			logic.cancelReg(reg, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 验证该挂号是否可以进行撤销挂号操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkIfCanDelReg.act")
	public String checkIfCanDelReg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			String regno = request.getParameter("regno");

			JSONObject jobj = new JSONObject();

			// 判断该挂号对应的就诊情况是否已经填写
			int num = jzqkLogic.countJzqkByRegNo(regno);
			if (num > 0) {
				jobj.put("data", num);
				YZUtility.DEAL_SUCCESS(jobj, "该挂号单已经填写就诊情况，不能撤销！", response, logger);
				return null;
			}
			// 判断挂号对应的接诊，是否已经接诊
			num = receiveLogic.countReceiveByRegNo(regno);
			if (num > 0) {
				jobj.put("data", num);
				YZUtility.DEAL_SUCCESS(jobj, "该挂号单已接诊，不能撤销！", response, logger);
				return null;
			}

			Map<String, String> map = new HashMap<String, String>();
			map.put("usercode", usercode);
			map.put("regno", regno);
			int count = logic.selectCount(TableNameUtil.KQDS_COSTORDER_DETAIL, map);
			if (count > 0) {
				jobj.put("data", count);
				YZUtility.DEAL_SUCCESS(jobj, "该患者挂号已开单，不能撤销！", response, logger);
				return null;
			}
			// 程序执行到此处，说明验证通过
			jobj.put("data", 0);
			YZUtility.DEAL_SUCCESS(jobj, "该挂号单可以撤销！", response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 新增挂号、挂号修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsReg dp = new KqdsReg();
			dp.setJh(1);
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId"); // 挂号seqId
			List<JSONObject> list=new ArrayList<JSONObject>();
			/**
			 * 校验相同患者重复挂号选择相同医生返回提示 （2019-4-21） shaoyp
			 */
			/*String usercode = dp.getUsercode();
			JSONObject json = logic.findKqdsRegByUserCode(usercode);
			if ((json.getString("doctor")).equals(dp.getDoctor())) {
				throw new Exception("禁止同一患者挂号选择同一医生！");
			}*/
			/**
			 * 修改结束
			 */
			
			if (YZUtility.isNullorEmpty(dp.getAskperson()) && YZUtility.isNullorEmpty(dp.getDoctor())) {
				throw new Exception("挂号时咨询和医生至少选一个！");
			}

			// 挂号后判断该患者是否是第一次咨询 如果是则保存咨询师
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("usercode", dp.getUsercode());
			List<KqdsUserdocument> userlist = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map1);
			if (userlist.size() == 0) {
				throw new Exception("患者信息不存在，患者编号为：" + dp.getUsercode());

			}
			KqdsUserdocument userdoc = userlist.get(0);
			// 防止多个页面同时操作
			dp.setUsername(userdoc.getUsername());
			// 【重要】 挂号的时候不给患者档案表的咨询赋值 ！！！
			if (!YZUtility.isNullorEmpty(seqId)) {// ### 修改挂号
				// ### 根据主键 加载 数据库中的记录
				KqdsReg regold = (KqdsReg) logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);

				if (regold == null) {
					throw new Exception("该挂号记录不存在，无法修改！");
				}

				if (1 == regold.getDel()) {
					throw new Exception("该挂号记录已撤销，无法修改！");
				} /*
					 * else { if (regold.getStatus() != 0 && regold.getStatus() != 1) { throw new
					 * Exception("该挂号记录不 是'等待治疗'和'等待结账'状态，无法修改！"); } }
					 */

				// if (regold.getStatus() == 0) { // 等待治疗状态下的挂号单修改

				// ### 保存修改前内容 没有修改的不保存 (拼json 方便前台取值)
				dp.setBeforeeditreason(JSONObject.fromObject(regold).toString());
				logic.updateReg(dp, regold, userdoc, person, request);
				Map<String, String> netmap = new HashMap<String, String>();
				netmap.put("regno", dp.getSeqId());
				// ### 对网电预约数据进行处理
				if (ConstUtil.USER_TYPE_1 == userdoc.getType()) {
					List<KqdsNetOrder> netlist = (List<KqdsNetOrder>) logic.loadList(TableNameUtil.KQDS_NET_ORDER, netmap);
					for (KqdsNetOrder netorder : netlist) { // ###
															// 只会有一条与之对应的网电预约数据
															// ！
						netorder.setRegdept(dp.getRegdept());
						netorder.setOrderperson(dp.getDoctor()); // 安排医生
						netorder.setAskperson(dp.getAskperson());
						netorder.setDaoyi(dp.getCreateuser());
						// netorder.setGuidetime(dp.getCreatetime());
						logic.updateSingleUUID(TableNameUtil.KQDS_NET_ORDER, netorder);
					}
				}
				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE, BcjlUtil.KQDS_REG, dp, dp.getUsercode(), TableNameUtil.KQDS_REG, request);

			} else {
				// ##### 重要，修复同时打开两个界面，同时进行挂初诊，都能挂上的BUG
				String czIdStrs = SysParaUtil.getSysValueByName(request, SysParaUtil.JZFL_CHUZHEN_SEQID);

				if (YZUtility.isStrInArrayEach(dp.getRecesort(), czIdStrs)) { // 如果挂的是
																				// 初诊，才做判断！！
					int count = logic.countCzReg(dp.getUsercode(), czIdStrs);
					if (count > 0) {
						JSONObject retrunObj = new JSONObject();
						retrunObj.put(YZActionKeys.JSON_RET_STATES, ConstUtil.REG_CHECK_100); // ###
																								// 特殊处理
						retrunObj.put(YZActionKeys.JSON_RET_MSRGS, "该患者已挂过初诊，请重新选择就诊分类。");
						PrintWriter pw = response.getWriter();
						pw.println(retrunObj.toString());
						pw.flush();
						return null;
					}
				}

				// ### 插入挂号记录
				dp.setSeqId(YZUtility.getUUID());
				dp.setStatus(ConstUtil.REG_STATUS_0); // 等待治疗
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setOrganization(ChainUtil.getCurrentOrganization(request));// ###【前端调用，以当前所在门诊为主】
				list=logic.updateRegInsert(dp, userdoc, person, request);
			}
			if(list!=null){
				JSONObject json=new JSONObject();
				json.put("list", list);
				YZUtility.DEAL_SUCCESS(json, null, response, logger);
			}else{
				YZUtility.DEAL_SUCCESS(null, null, response, logger);
			}
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
//			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
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
			KqdsReg en = (KqdsReg) logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);
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
	 * 根据usercode获取当天最新挂号信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectToDayNewDetail.act")
	public String selectToDayNewDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			// 门诊条件过滤
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			JSONObject jobj = logic.selectToDayNewDetail(usercode, organization);
			if (!jobj.has("askperson")) {
				// throw new Exception("患者信息挂号不存在，请先挂号");
			}
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 接待中心-等待治疗列表 【只查询当前所在门诊，Organization直接从登录用户的session中获取】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDzlNopage.act")
	public String selectDzlNopage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String status = request.getParameter("status");
			String querytype = request.getParameter("querytype");
			String searchValue = request.getParameter("searchValue");
			String sortName = request.getParameter("sortName");
			String sortOrder = request.getParameter("sortOrder");
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			YZPerson person = SessionUtil.getLoginPerson(request);
			int statusInt = 0;
			if (null != status && !"".equals(status)) {
				statusInt = Integer.parseInt(status);
			}

			// 当前所在门诊
			String organization = ChainUtil.getCurrentOrganization(request);
			// 可见人员过滤
			String visualstaff = SessionUtil.getVisualstaff(request);
			JSONObject list = logic.selectDzlmenu(TableNameUtil.KQDS_REG, person,sortName,sortOrder, statusInt, querytype, searchValue, visualstaff, organization, request,bp);
			//JSONObject jobj = new JSONObject();
//			int delreg = 0;
//			JSONArray array = list.getJSONArray("rows");
//			List<JSONObject> list2 = JSONArray.toList(array, new JSONObject(), new JsonConfig());
//			for (JSONObject obj : list2) {
//				if (obj.getString("del").equals("1")) {// 撤销挂号的
//					delreg++;
//				}
//			}
			//jzfljobj.put("总数", list.size() - delreg);

			list.put("offset", bp.getOffset());
			//jobj.put("data", list);
			YZUtility.DEAL_SUCCESS(list, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 就诊情况
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectJzqkNopage.act")
	public String selectJzqkNopage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String ghsj = request.getParameter("ghsj");
			String ghsj2 = request.getParameter("ghsj2");
			String regdept = request.getParameter("regdept");
			String askperson = request.getParameter("askperson");
			String doctor = request.getParameter("doctor");
			String usercodeorname = request.getParameter("usercodeorname");
			String reggoal = request.getParameter("reggoal");
			String jzmd = request.getParameter("jzmd");
			String sortName = request.getParameter("sortName");
			String sortOrder = request.getParameter("sortOrder");
			// 提醒的查询条件
			String isfz = request.getParameter("isfz");
			String fzsj = request.getParameter("fzsj");
			String fzsj2 = request.getParameter("fzsj2");
			Map<String, String> map = new HashMap<String, String>();
			map.put("del", ConstUtil.REG_DELETE_0); // 未撤销
			if (!YZUtility.isNullorEmpty(ghsj)) {
				map.put("ghsj", ghsj);
			}
			if (!YZUtility.isNullorEmpty(ghsj2)) {
				map.put("ghsj2", ghsj2);
			}
			if (!YZUtility.isNullorEmpty(regdept)) {
				map.put("regdept", regdept);
			}
			if (!YZUtility.isNullorEmpty(doctor)) {
				map.put("doctor", doctor);
			}
			if (!YZUtility.isNullorEmpty(askperson)) {
				map.put("askperson", askperson);
			}
			if (!YZUtility.isNullorEmpty(usercodeorname)) {
				map.put("usercodeorname", usercodeorname);
			}
			if (!YZUtility.isNullorEmpty(reggoal)) {
				map.put("reggoal", reggoal);
			}
			if (!YZUtility.isNullorEmpty(jzmd)) {
				map.put("jzmd", jzmd);
			}
			if (!YZUtility.isNullorEmpty(isfz)) {
				map.put("isfz", isfz);
			}
			if (!YZUtility.isNullorEmpty(fzsj)) {
				map.put("fzsj", fzsj);
			}
			if (!YZUtility.isNullorEmpty(fzsj2)) {
				map.put("fzsj2", fzsj2);
			}
			if(!YZUtility.isNullorEmpty(sortName)){
				map.put("sortName", sortName);
				map.put("sortOrder", sortOrder);
			}
			YZPerson person = SessionUtil.getLoginPerson(request);
			// 可见人员过滤
			String visualstaff = SessionUtil.getVisualstaff(request);
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			JSONObject list = logic.selectJzqk(TableNameUtil.KQDS_REG, person, map, visualstaff, ChainUtil.getOrganizationFromUrl(request),bp);
			//YZUtility.RETURN_LIST(list, response, logger);
			YZUtility.DEAL_SUCCESS(list,null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 综合查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectZhcxNopage.act")
	public String selectZhcxNopage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String regdept = request.getParameter("regdept");
			String doctorSearch = request.getParameter("doctorSearch");
			String askpersonSearch = request.getParameter("askpersonSearch");

			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String searchValue = request.getParameter("searchValue");
			String ifmedrecord = request.getParameter("ifmedrecord");

			String importantSearch = request.getParameter("importantSearch");
			String devchannelSearch = request.getParameter("devchannelSearch");
			String nexttype1 = request.getParameter("nexttype1");

			String ageSearch = request.getParameter("ageSearch");
			String firstaskperson = request.getParameter("firstAskperson");
			String regsort = request.getParameter("regsort");
			String cjstatus = request.getParameter("cjstatus");
			
			String gongju = request.getParameter("gongju");//添加受理工具分类查询

			String organization = ChainUtil.getOrganizationFromUrl(request); // 门诊条件过滤
			String recesort = request.getParameter("recesort");
			// 可以查询挂号表医生 咨询不是自己，收费项目种有自己的 value 1
			String type = request.getParameter("type");
			// 潜在开发项目
			String devItem = request.getParameter("devItem");
			String sortName = request.getParameter("sortName");
			String sortOrder = request.getParameter("sortOrder");
			
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(regdept)) {
				map.put("regdept", regdept);
			}
			if (!YZUtility.isNullorEmpty(doctorSearch)) {
				map.put("doctor", doctorSearch);
			}
			if (!YZUtility.isNullorEmpty(askpersonSearch)) {
				map.put("askperson", askpersonSearch);
			}
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime + ConstUtil.TIME_END);
			}
			if (!YZUtility.isNullorEmpty(searchValue)) {
				map.put("searchValue", searchValue);
			}
			if (!YZUtility.isNullorEmpty(ifmedrecord)) {
				map.put("ifmedrecord", ifmedrecord);
			}
			if (!YZUtility.isNullorEmpty(importantSearch)) {
				map.put("important", importantSearch);
			}
			if (!YZUtility.isNullorEmpty(devchannelSearch)) {
				map.put("devchannel", devchannelSearch);
			}
			if (!YZUtility.isNullorEmpty(nexttype1)) {
				map.put("nexttype", nexttype1);
			}
			if (!YZUtility.isNullorEmpty(ageSearch)) {
				map.put("ageSearch", ageSearch);
			}
			if (!YZUtility.isNullorEmpty(regsort)) {
				map.put("regsort", regsort);
			}
			if (!YZUtility.isNullorEmpty(cjstatus)) {
				map.put("cjstatus", cjstatus);
			}
			if (!YZUtility.isNullorEmpty(gongju)) {
				map.put("gongju", gongju);
			}
			if (!YZUtility.isNullorEmpty(recesort)) {
				map.put("recesort", recesort);
			}
			if (!YZUtility.isNullorEmpty(type)) {
				map.put("type", type);
			}
			if (!YZUtility.isNullorEmpty(devItem)) {
				map.put("devItem", devItem);
			}
			if (!YZUtility.isNullorEmpty(firstaskperson)) {
				map.put("firstaskperson", firstaskperson);
			}
			if(!YZUtility.isNullorEmpty(sortName)){
				map.put("sortName", sortName);
				map.put("sortOrder", sortOrder);
			}
			
			YZPerson person = SessionUtil.getLoginPerson(request);
			// 接诊查询
			String visualstaff = SessionUtil.getVisualstaff(request);
			List<YZDict> jzflDict = dictLogic.getListByParentCodeALL(DictUtil.JZFL, ChainUtil.getCurrentOrganization(request));
			JSONObject data = logic.selectJzcx(bp, TableNameUtil.KQDS_REG, person, map, jzflDict, organization, visualstaff, request,flag);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("接诊查询", fieldArr, fieldnameArr, (List<JSONObject>) data.getJSONArray("rows"), response, request);
				return null;
			}
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
	/**
	 * 今日患者
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectToday.act")
	public String selectToday(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String doctorSearch = request.getParameter("doctorSearch");
			String askpersonSearch = request.getParameter("askpersonSearch");
			String searchValue = request.getParameter("searchValue");
			String regsort = request.getParameter("regsort");
			String cjstatus = request.getParameter("cjstatus");
			String recesort = request.getParameter("recesort");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String createuserSearch = request.getParameter("createuserSearch");
			String shouli = request.getParameter("shouli");
			String gongju = request.getParameter("gongju");
			String devchannel = request.getParameter("devchannel");
			String nexttype = request.getParameter("nexttype");
			String ywhf = request.getParameter("ywhf");
			String customer = request.getParameter("customer");
			String depttype = request.getParameter("depttype");// 2网电 3营销
			String sortName=request.getParameter("sortName");
			String sortOrder=request.getParameter("sortOrder");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			// 可以查询挂号表医生 咨询不是自己，收费项目种有自己的 value 1
			YZPerson person = SessionUtil.getLoginPerson(request);
			// 接诊查询
			Map<String, String> map = new HashMap<String, String>();

			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime);
			}else{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
				String time = df.format(new Date());
				map.put("starttime", time);
			}

			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime);
			}else{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
				String time = df.format(new Date());
				map.put("endtime", time);
			}

			if (!YZUtility.isNullorEmpty(createuserSearch)) {
				map.put("createuserSearch", createuserSearch);
			}

			if (!YZUtility.isNullorEmpty(doctorSearch)) {
				map.put("doctorSearch", doctorSearch);
			}
			if (!YZUtility.isNullorEmpty(askpersonSearch)) {
				map.put("askpersonSearch", askpersonSearch);
			}
			if (!YZUtility.isNullorEmpty(searchValue)) {
				map.put("searchValue", searchValue);
			}
			if (!YZUtility.isNullorEmpty(regsort)) {
				map.put("regsort", regsort);
			}
			if (!YZUtility.isNullorEmpty(cjstatus)) {
				map.put("cjstatus", cjstatus);
			}
			if (!YZUtility.isNullorEmpty(recesort)) {
				map.put("recesort", recesort);
			}
			if (!YZUtility.isNullorEmpty(shouli)) {
				map.put("shouli", shouli);
			}
			if (!YZUtility.isNullorEmpty(gongju)) {
				map.put("gongju", gongju);
			}
			if (!YZUtility.isNullorEmpty(devchannel)) {
				map.put("devchannel", devchannel);
			}
			if (!YZUtility.isNullorEmpty(nexttype)) {
				map.put("nexttype", nexttype);
			}
			if (!YZUtility.isNullorEmpty(ywhf)) {
				map.put("ywhf", ywhf);
			}
			if (!YZUtility.isNullorEmpty(customer)) {
				map.put("customer", customer);
			}
			if(!YZUtility.isNullorEmpty(sortName)){
				map.put("sortName", sortName);
			}
			if(!YZUtility.isNullorEmpty(sortOrder)){
				map.put("sortOrder", sortOrder);
			}
			// 根据所选门诊进行查询
//			String organization = ChainUtil.getOrganizationFromUrl(request);
			String organization = request.getParameter("organization");
			if (YZUtility.isNullorEmpty(organization)) {
				// 根据所选门诊进行查询
				map.put("organization", ChainUtil.getCurrentOrganization(request));
			} else {
				map.put("organization", organization);
			}
			// 可见人员过滤
			String visualstaff = SessionUtil.getVisualstaff(request);
			// 查询depttype 对应的人员
			String visualstaffwd = personLogic.getPerIdsByDeptTypeNoOrg(depttype);
			
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			JSONObject list = logic.selectToday("KQDS_REG", person, map, visualstaff, visualstaffwd,bp,flag);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("今日患者", fieldArr, fieldnameArr, list.getJSONArray("rows"), response, request);
				return null;
			}
			//YZUtility.RETURN_LIST(list, response, logger);
			YZUtility.DEAL_SUCCESS(list,null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
	
	/**
	 * 查询到诊患者
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDzQuery.act")
	public String selectDzQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String doctorSearch = request.getParameter("doctorSearch");
			String askpersonSearch = request.getParameter("askperson");
			String searchValue = request.getParameter("searchValue");
			String regsort = request.getParameter("regsort");
			String cjstatus = request.getParameter("cjstatus");
			String recesort = request.getParameter("recesort");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String createuserSearch = request.getParameter("createuserSearch");
			String shouli = request.getParameter("shouli");
			String gongju = request.getParameter("gongju");
			String devchannel = request.getParameter("devchannel");
			String nexttype = request.getParameter("nexttype");
			String depttype = request.getParameter("depttype");// 2网电 3营销
			String sortName=request.getParameter("sortName");
			String sortOrder = request.getParameter("sortOrder");
			String organization = request.getParameter("organization");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			// 可以查询挂号表医生 咨询不是自己，收费项目种有自己的 value 1
			YZPerson person = SessionUtil.getLoginPerson(request);
			// 接诊查询
			Map<String, String> map = new HashMap<String, String>();
			
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime);
			}
			
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime);
			}
			
			if (!YZUtility.isNullorEmpty(createuserSearch)) {
				map.put("createuserSearch", createuserSearch);
			}
			
			if (!YZUtility.isNullorEmpty(doctorSearch)) {
				map.put("doctorSearch", doctorSearch);
			}
			if (!YZUtility.isNullorEmpty(askpersonSearch)) {
				map.put("askpersonSearch", askpersonSearch);
			}
			if (!YZUtility.isNullorEmpty(searchValue)) {
				map.put("searchValue", searchValue);
			}
			if (!YZUtility.isNullorEmpty(regsort)) {
				map.put("regsort", regsort);
			}
			if (!YZUtility.isNullorEmpty(cjstatus)) {
				map.put("cjstatus", cjstatus);
			}
			if (!YZUtility.isNullorEmpty(recesort)) {
				map.put("recesort", recesort);
			}
			if (!YZUtility.isNullorEmpty(shouli)) {
				map.put("shouli", shouli);
			}
			if (!YZUtility.isNullorEmpty(gongju)) {
				map.put("gongju", gongju);
			}
			if (!YZUtility.isNullorEmpty(devchannel)) {
				map.put("devchannel", devchannel);
			}
			if (!YZUtility.isNullorEmpty(nexttype)) {
				map.put("nexttype", nexttype);
			}
			if (!YZUtility.isNullorEmpty(sortName)) {
				map.put("sortName", sortName);
				map.put("sortOrder", sortOrder);
			}
			// 根据所选门诊进行查询
			if (YZUtility.isNullorEmpty(organization)) {
				// 根据所选门诊进行查询
				map.put("organization", ChainUtil.getCurrentOrganization(request));
			} else {
				map.put("organization", organization);
			}
			// 可见人员过滤
			String visualstaff = SessionUtil.getVisualstaff(request);
			// 查询depttype 对应的人员
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String visualstaffwd = personLogic.getPerIdsByDeptTypeNoOrg(depttype);
			JSONObject list = logic.selectDzQuery("KQDS_REG", person, map, visualstaff, visualstaffwd,bp);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("到诊患者", fieldArr, fieldnameArr, list.getJSONArray("rows"), response, request);
				return null;
			}
			//YZUtility.RETURN_LIST(list, response, logger);
			YZUtility.DEAL_SUCCESS(list,null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		
		return null;
	}

	/**
	 * 不分页查询，用于挂号修改页面 reg_edit.jsp 该方法没有任何地方引用### yangsen 2018-2-4
	 * ------------------------------------------------------------------- 同一个boot
	 * strap table
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectWithNopage.act")
	public String selectWithNopage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String searchField = request.getParameter("searchField");
			String searchValue = request.getParameter("searchValue");
			// 封装参数到实体类
			List<JSONObject> listreg = new ArrayList<JSONObject>();
			List<JSONObject> list = new ArrayList<JSONObject>();
			if (null != searchField && null != searchValue && !"".equals(searchValue) && !"".equals(searchField)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("isdelete", "0");
				map.put(searchField, searchValue);
				// 可见人员过滤
				String visualstaff = SessionUtil.getVisualstaff(request);
				list = userlogic.selectWithNopage(TableNameUtil.KQDS_USERDOCUMENT, map, "0", visualstaff, person);
			}
			if (list != null && list.size() > 0) {
				StringBuffer usercodes = new StringBuffer();
				for (JSONObject user : list) {
					usercodes.append("\'");
					usercodes.append(user.getString("usercode"));
					usercodes.append("\'");
					usercodes.append(",");
				}
				String usercodeS = usercodes.toString().substring(0, usercodes.toString().length() - 1);
				listreg = logic.selectByusercode(TableNameUtil.KQDS_REG, usercodeS);
			}
			YZUtility.RETURN_LIST(listreg, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
	
	/**
	 *  2019-11-20 syp
	  * @Title: selectWithNopageReg   
	  * @Description: TODO(用于挂号修改查询患者专用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年11月20日 下午1:58:17
	 */
	@RequestMapping(value = "/selectWithNopageReg.act")
	public String selectWithNopageReg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String searchField = request.getParameter("searchField");
			String searchValue = request.getParameter("searchValue");
			// 封装参数到实体类
			List<JSONObject> listreg = new ArrayList<JSONObject>();
			List<JSONObject> list = new ArrayList<JSONObject>();
			if (null != searchField && null != searchValue && !"".equals(searchValue) && !"".equals(searchField)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("isdelete", "0");
				map.put("searchValue", searchValue);
				// 可见人员过滤
				String visualstaff = SessionUtil.getVisualstaff(request);
				list = userlogic.selectWithNopageReg(TableNameUtil.KQDS_USERDOCUMENT, map, "0", visualstaff, person);
			}
			if (list != null && list.size() > 0) {
				StringBuffer usercodes = new StringBuffer();
				for (JSONObject user : list) {
					usercodes.append("\'");
					usercodes.append(user.getString("usercode"));
					usercodes.append("\'");
					usercodes.append(",");
				}
				String usercodeS = usercodes.toString().substring(0, usercodes.toString().length() - 1);
				listreg = logic.selectByusercode(TableNameUtil.KQDS_REG, usercodeS);
			}
			YZUtility.RETURN_LIST(listreg, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 不分页查询，用于挂号修改页面 reg_edit.jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectOneByregno.act")
	public String selectOneByregno(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");

			KqdsReg en = (KqdsReg) logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);
			List<KqdsReg> list = new ArrayList<KqdsReg>();
			list.add(en);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	} // -------------------------------------------------------------------------------------------
		// 同一个boot strap table end

	/**
	 * 上门情况柱状图展示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCountBB.act")
	public String selectCountBB(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String organization = request.getParameter("organization");
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime + ConstUtil.TIME_END);
			}

			List<KqdsReg> list = logic.getCountTj(TableNameUtil.KQDS_REG, map, organization);
			JSONObject jobj = new JSONObject();
			jobj.put("rows", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 全院来人柱状图展示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCountBBQylr.act")
	public String selectCountBBQylr(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			int year = Integer.parseInt(starttime.substring(0, 4));// 年
			int month = Integer.parseInt(starttime.substring(starttime.length() - 2, starttime.length()));// 月
			int days = YZUtility.getDaysByYearMonth(year, month); // 本月的天数
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			List<Object> listxz = new ArrayList<Object>();// 新诊
			List<Object> listAll = new ArrayList<Object>();// 总来人
			// 全部
			listAll = logic.getCountQylrAll(TableNameUtil.KQDS_REG, year, month, days, ChainUtil.getOrganizationFromUrl(request));
			// 初诊
			listxz = logic.getCountQylrNew(TableNameUtil.KQDS_REG, year, month, days, ChainUtil.getOrganizationFromUrl(request), request);
			// 全部人数
			int[] all = (int[]) listAll.get(1);
			// 初诊人数
			int[] xz = (int[]) listxz.get(1);
			JSONObject jobj = new JSONObject();
			jobj.put("listAll", listAll);
			jobj.put("listxz", listxz);
			jobj.put("all", all[0]);
			jobj.put("xz", xz[0]);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 成交率图表展示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCountCjl.act")
	public String selectCountCjl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String ghfl = request.getParameter("ghfl");
			String jzfl = request.getParameter("jzfl");
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			// 获取starttime endtime之间的日期
			String[] datearray = YZUtility.getBetweenDatesArray(starttime, endtime);
			// 可见人员过滤
			String visualstaff = SessionUtil.getVisualstaff(request);
			Double[] cjlarray = new Double[datearray.length];
			for (int i = 0; i < datearray.length; i++) {
				Double cjl = logic.getcjl(datearray[i], ghfl, jzfl, visualstaff, ChainUtil.getOrganizationFromUrl(request));
				cjlarray[i] = cjl;
			}
			JSONObject jobj = new JSONObject();
			jobj.put("datearray", datearray);
			jobj.put("cjlarray", cjlarray);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 成交率列表展示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCountCjlTable.act")
	public String selectCountCjlTable(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String ghfl = request.getParameter("ghfl");
			// 就诊类型
			String jzfl = request.getParameter("jzfl");
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			List<Object> list = new ArrayList<Object>();
			// 获取starttime endtime之间的日期
			String[] datearray = YZUtility.getBetweenDatesArray(starttime, endtime);
			// 人数 数组
			int[] czarray = new int[datearray.length];
			// 成交人数 数组
			int[] czcjarray = new int[datearray.length];
			// 成交率 数组
			String[] cjlarray = new String[datearray.length];
			// 可见人员过滤
			String visualstaff = SessionUtil.getVisualstaff(request);
			// 合计
			int allnum = 0, allcjnum = 0;
			String allcjv = "0";
			for (int i = 0; i < datearray.length; i++) {
				int cznum = logic.getCountQylrNew(TableNameUtil.KQDS_REG, datearray[i], ghfl, jzfl, visualstaff, ChainUtil.getOrganizationFromUrl(request));
				int czcjnum = logic.getCountQylrAll(TableNameUtil.KQDS_REG, datearray[i], ghfl, jzfl, visualstaff, ChainUtil.getOrganizationFromUrl(request));
				if (czcjnum == 0 || cznum == 0) {
					cjlarray[i] = "0";
				} else {
					float num = ((float) czcjnum / cznum) * 100;
					DecimalFormat df = new DecimalFormat("0.00");// 格式化小数
					cjlarray[i] = df.format(num);
				}
				czarray[i] = cznum;
				czcjarray[i] = czcjnum;
				allnum += cznum;
				allcjnum += czcjnum;
			}
			list.add(datearray);
			list.add(czarray);
			list.add(czcjarray);
			list.add(cjlarray);
			list.add(allnum);
			list.add(allcjnum);
			// 合计成交率
			if (allcjnum == 0 || allnum == 0) {
				allcjv = "0";
			} else {
				float num = ((float) allcjnum / allnum) * 100;
				DecimalFormat df = new DecimalFormat("0.00");// 格式化小数
				allcjv = df.format(num);
			}
			list.add(allcjv);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
	@RequestMapping(value="/selectExistByUsercode.act")
	public String  selectExistByUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String status="未挂号";
			String usercode=request.getParameter("usercode");
			if(usercode!=null&&usercode!=""){
				Map<String,String> map= new HashMap<String,String>();
				map.put("usercode", usercode);
				String createtime=logic.selectExistByUsercode(map);
				if(createtime!=null&&!createtime.equals("")){
					String date = YZUtility.getDateStr(new Date());
					
					Calendar calendar = Calendar.getInstance(); //得到日历
					calendar.setTime(new Date());//把当前时间赋给日历
					calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
					Date dBefore = calendar.getTime();   //得到前一天的时间
					String date1=YZUtility.getDateStr(dBefore);
					if(createtime.substring(0, 10).equals(date)||createtime.substring(0, 10).equals(date1)){
				        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			           Date dateStart = format.parse(createtime);
			           int endDay=(int) (new Date().getTime()/1000);
			           int startDay = (int) (dateStart.getTime() / 1000);
			           if((endDay-startDay)<=129600){
			        	   status="已挂号";
			           }else{
			        	   status="未挂号";
			           }
					}else{ 
						status="未挂号";
			        }
				}else{
					status="未挂号";
				}
			}else{
				status="未挂号";
			}
			JSONObject json=new JSONObject();
			json.put("status", status);
			YZUtility.DEAL_SUCCESS(json, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}

	@RequestMapping(value = "/selectUserdocumentByReg.act")
	public String selectUserdocumentByReg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			JSONObject en = logic.selectUserdocumentByReg(seqId);
			if (en == null) {
				throw new Exception("数据不存在");
			}
			//查询是不是管理员
			YZPerson person = SessionUtil.getLoginPerson(request);
			YZPriv priv = privLogic.findGeneral(person.getUserPriv());
			if(priv.getPrivName().contains("系统管理员")){
				en.put("isAdministrator",true);
			}else{
				en.put("isAdministrator",false);
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
     * 根据患者编号查询接诊信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/selectJzByUsercode.act")
	public String selectJzByUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			List<JSONObject> list = logic.selectJzByUsercode(map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
}