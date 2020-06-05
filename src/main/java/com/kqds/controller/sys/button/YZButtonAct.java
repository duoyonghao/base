package com.kqds.controller.sys.button;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hudh.dept.service.ISysDeptPrivService;
import com.kqds.core.global.YZActionKeys;
import com.kqds.entity.sys.YZButton;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.sys.YZPriv;
import com.kqds.service.sys.button.YZButtonLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("YZButtonAct")
public class YZButtonAct {
	private static Logger logger = LoggerFactory.getLogger(YZButtonAct.class);
	@Autowired
	private YZButtonLogic buttonLogic;
	@Autowired
	private ISysDeptPrivService sysDeptPrivService;
	@RequestMapping(value = "/toIndex.act")
	public String toIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/button/index.jsp";
	}

	@RequestMapping(value = "/toTop.act")
	public String toContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/button/top.jsp";
	}

	@RequestMapping(value = "/toLeft.act")
	public String toLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/button/left.jsp";
	}

	@RequestMapping(value = "/toList.act")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/admin/button/list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEdit.act")
	public ModelAndView toEditMenu(@RequestParam("seqId") String seqId) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/admin/button/edit.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toDept.act")
	public ModelAndView toDept(@RequestParam("qxname") String qxname) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("qxname", qxname);
		mv.setViewName("/admin/dept/addDept.jsp");
		return mv;
	}
	@RequestMapping(value = "/toDeptId.act")
	public ModelAndView toDeptId(@RequestParam("deptprivid") String deptprivid) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("deptprivid", deptprivid);
		mv.setViewName("/admin/dept/updateDept.jsp");
		return mv;
	}
	@RequestMapping(value = "/toNewAdd.act")
	public ModelAndView toNewMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/admin/button/newAdd.jsp");
		return mv;
	}

	/**
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
			// 数据库的连接

			// orm映射数据库

			YZButton dp = new YZButton();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(dp.getName())) {
				throw new Exception("按钮名称不能为空");
			}
			if (YZUtility.isNullorEmpty(dp.getQxName())) {
				throw new Exception("权限标识不能为空");
			}

			if (!YZUtility.isNullorEmpty(seqId)) {

				YZButton tmp = (YZButton) buttonLogic.loadObjSingleUUID(TableNameUtil.SYS_BUTTON, seqId);
				if (tmp == null) {
					throw new Exception("按钮不存在");
				}

				// 这几个字段修改时，不更新
				dp.setParentid(tmp.getParentid());
				dp.setCreatetime(tmp.getCreatetime());
				dp.setCreateuser(tmp.getCreateuser());
				// 这几个字段修改时，不更新 end..
				buttonLogic.updateSingleUUID(TableNameUtil.SYS_BUTTON, dp);
				// 记录日志
				SysLogUtil.log(SysLogUtil.MODIFY, SysLogUtil.SYS_BUTTON, dp, TableNameUtil.SYS_BUTTON, request);
			} else {
				if (YZUtility.isNullorEmpty(dp.getParentid())) {
					throw new Exception("所属菜单为空，请联系系统管理员");
				}

				dp.setSeqId(YZUtility.getUUID());
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				buttonLogic.saveSingleUUID(TableNameUtil.SYS_BUTTON, dp);
				// 记录日志
				SysLogUtil.log(SysLogUtil.NEW, SysLogUtil.SYS_BUTTON, dp, TableNameUtil.SYS_BUTTON, request);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectList.act")
	public String selectList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String parentid = request.getParameter("menuid");
			List<JSONObject> list = buttonLogic.selectList(parentid);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
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

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键不能为空");
			}
			YZButton button = (YZButton) buttonLogic.loadObjSingleUUID(TableNameUtil.SYS_BUTTON, seqId);

			if (button == null) {
				throw new Exception("按钮不存在");
			}

			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, JSONObject.fromObject(button).toString());
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete.act")
	public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String seqId = request.getParameter("seqId");
			String deptprivid=request.getParameter("deptprivid");
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}
			sysDeptPrivService.deleteSysDeptPriv(deptprivid);
			buttonLogic.deleteBySeqIds(seqId, request);

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getButtonListByPriv.act")
	public String getButtonListByPriv(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<YZButton> btList4Rt = new ArrayList<YZButton>();
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			String menuid = request.getParameter("menuid");

			YZPriv priv = (YZPriv) buttonLogic.loadObjSingleUUID(TableNameUtil.SYS_PRIV, person.getUserPriv());
			List<YZButton> btList = buttonLogic.getListBySeqIds(priv.getFuncbutton());

			if (YZUtility.isNullorEmpty(menuid) || menuid.equals("undefined")) {
				btList4Rt = btList;
			} else {
				for (YZButton yzButton : btList) {
					if (menuid.equals(yzButton.getParentid())) {
						btList4Rt.add(yzButton);
					}
				}
			}

			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, btList4Rt);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

}
