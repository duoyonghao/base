package com.kqds.controller.base.imageData;

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

import com.kqds.entity.base.KqdsImageData;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.imageData.KQDS_ImageDataLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "unchecked" })
@Controller
@RequestMapping("KQDS_ImageDataAct")
public class KQDS_ImageDataAct {
	@Autowired
	private YZDictLogic dictLogic;
	@Autowired
	private KQDS_ImageDataLogic logic;
	private static Logger logger = LoggerFactory.getLogger(KQDS_ImageDataAct.class);

	@RequestMapping(value = "/toPlayVideo.act")
	public ModelAndView toNetorderInsertOrupdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = request.getParameter("url");
		ModelAndView mv = new ModelAndView();
		mv.addObject("url", url);
		mv.setViewName("/js/uploadfile/playvideo.jsp");
		return mv;
	}

	@RequestMapping(value = "/toOriginalmg.act")
	public ModelAndView toOriginalmg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = request.getParameter("url");
		ModelAndView mv = new ModelAndView();
		mv.addObject("url", url);
		mv.setViewName("/kqdsFront/video/originalmg.jsp");
		return mv;
	}

	@RequestMapping(value = "/toCameraOnline_yxzl.act")
	public ModelAndView toCameraOnline_yxzl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String username = request.getParameter("username");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("username", username);
		mv.setViewName("/kqdsFront/video/cameraOnline_yxzl.jsp");
		return mv;
	}

	@RequestMapping(value = "/toVideoBlk.act")
	public ModelAndView toVideoBlk(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView();
		mv.addObject("type", type);
		mv.setViewName("/kqdsFront/video/video_blk.jsp");
		return mv;
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
			KqdsImageData en = (KqdsImageData) logic.loadObjSingleUUID(TableNameUtil.KQDS_IMAGE_DATA, seqId);

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
	 * 根据usercode/regno查询影像资料 【### 暂且先只支持本院查询】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDetailByUsercode.act")
	public String selectDetailByUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			String regno = request.getParameter("regno");
			String infosort = request.getParameter("infosort");

			Map<String, String> map = new HashMap<String, String>();

			/** 病历-影像资料的查询，通过 regno 组合 **/
			if (!YZUtility.isNullorEmpty(regno)) {
				map.put("regno", regno);
			}

			/** 首页-影像资料的查询，通过 usercode + infosort 组合 **/
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}

			// 信息分类
			if (!YZUtility.isNullorEmpty(infosort)) { // 影像资料的值为 YXZL 病历的值为 BLK
				map.put("infosort", infosort);
			}

			// 门诊条件过滤
			map.put("organization", ChainUtil.getCurrentOrganization(request));

			List<KqdsImageData> en = (List<KqdsImageData>) logic.loadList(TableNameUtil.KQDS_IMAGE_DATA, map);

			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 影像资料 上传
	 */
	@RequestMapping(value = "/uploadyxzl.act")
	public String uploadyxzl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsImageData dp = new KqdsImageData();
			BeanUtils.populate(dp, request.getParameterMap());

			// String seqId = request.getParameter("seqId");
			String regno = request.getParameter("regno");
			String usercode = request.getParameter("usercode");
			String username = request.getParameter("username");
			String attachmentid = request.getParameter("attachmentid");
			String attachmentname = request.getParameter("attachmentname");

			if (YZUtility.isNullorEmpty(regno)) { // 首页-影响资料 和 病历-影像资料共用一个方法
				regno = "";
			}

			// 影像分类
			String yxfl = request.getParameter("yxfl");
			if (YZUtility.isNullorEmpty(yxfl)) {
				yxfl = dictLogic.getDictIdByNameAndParentCode("YXFL", "其他");
			}

			// 信息分类
			String infosort = request.getParameter("infosort");

			// 单独的影像资料菜单 video.jsp
			String uuid = YZUtility.getUUID();
			dp.setSeqId(uuid);
			dp.setCreatetime(YZUtility.getCurDateTimeStr());
			dp.setCreateuser(person.getSeqId());
			dp.setUsercode(usercode);
			dp.setUsername(username);
			dp.setRegno(regno);
			dp.setAttachmentid(attachmentid);
			dp.setAttachmentname(attachmentname);
			dp.setYxfl(yxfl);
			dp.setInfosort(infosort);
			dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
			logic.saveSingleUUID(TableNameUtil.KQDS_IMAGE_DATA, dp);

			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_IMAGE_DATA, dp, usercode, TableNameUtil.KQDS_IMAGE_DATA, request);

			YZUtility.DEAL_SUCCESS(null, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 获取图片路径
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getImgUrl.act")
	public String getImgUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String imgurl = YZUtility.getImgUrl();
			JSONObject jobj = new JSONObject();
			jobj.put("data", imgurl);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
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
			String delImgId = request.getParameter("delImgId");

			if (YZUtility.isNullorEmpty(delImgId)) {
				throw new Exception("图片为空或者null");
			}

			// 删除费用单中项目
			Map<String, String> map = new HashMap<String, String>();
			map.put("attachmentid", delImgId + ",");
			List<KqdsImageData> list = (List<KqdsImageData>) logic.loadList(TableNameUtil.KQDS_IMAGE_DATA, map);
			if (list != null && list.size() > 0) {
				for (KqdsImageData imgdata : list) {
					logic.deleteSingleUUID(TableNameUtil.KQDS_IMAGE_DATA, imgdata.getSeqId());
					// 记录日志
					BcjlUtil.LogBcjlWithUserCode(BcjlUtil.DELETE, BcjlUtil.KQDS_IMAGE_DATA, imgdata, imgdata.getUsercode(), TableNameUtil.KQDS_IMAGE_DATA, request);
				}
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
}