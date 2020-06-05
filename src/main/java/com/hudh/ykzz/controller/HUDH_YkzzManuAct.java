package com.hudh.ykzz.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSON;
import com.hudh.ykzz.entity.YkzzManu;
import com.hudh.ykzz.service.IYkzzManuService;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;
@Controller
@RequestMapping("/HUDH_YkzzManuAct")
public class HUDH_YkzzManuAct {
	private Logger logger = LoggerFactory.getLogger(HUDH_YkzzManuAct.class);
	/**
	 * 药库供应商操作接口
	 */
	@Autowired
	private IYkzzManuService ykzzManuService;
	
	/**
	 * 新增供应商
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertYkzzManu.act")
	public String insertYkzzManu(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String manuName = request.getParameter("manuName");
		String orderno = request.getParameter("orderno");
		String manuCode = request.getParameter("manuCode");
		YkzzManu ykzzManu = new YkzzManu();
		if(YZUtility.isNotNullOrEmpty(orderno)) {
			ykzzManu.setOrderno(Integer.valueOf(orderno));
		}
		ykzzManu.setManuCode(manuCode);
		ykzzManu.setManuName(manuName);
		try {
			ykzzManuService.insertYkzzManu(ykzzManu, request);
			YZUtility.DEAL_SUCCESS(null,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据id查找供应商
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findYkzzManuById.act")
	public String findYkzzManuById(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		try {
			if(YZUtility.isNotNullOrEmpty(id)) {
				YkzzManu ykzzManu = ykzzManuService.findYkzzManuById(id);
				JSONObject jo = new JSONObject();
				jo.put("ykzzManu", JSON.toJSONString(ykzzManu));
				YZUtility.DEAL_SUCCESS(jo,null, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据id删除供应商
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteYkzzManuById.act")
	public String deleteYkzzManuById(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		try {
			if(YZUtility.isNotNullOrEmpty(id)) {
				ykzzManuService.deleteYkzzManuById(id);
				YZUtility.DEAL_SUCCESS(null,null, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据id更新供应商信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateYkzzManuById.act")
	public String updateYkzzManuById(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String manuName = request.getParameter("manuName");
		String orderno = request.getParameter("orderno");
		YkzzManu ykzzManu = new YkzzManu();
		ykzzManu.setId(id);
		if(YZUtility.isNotNullOrEmpty(orderno)) {
			ykzzManu.setOrderno(Integer.valueOf(orderno));
		}
		ykzzManu.setManuName(manuName);
		try {
			if(YZUtility.isNotNullOrEmpty(id) && YZUtility.isNotNullOrEmpty(manuName) && 
					YZUtility.isNotNullOrEmpty(orderno)) {
				ykzzManuService.updateYkzzManuById(ykzzManu);
				YZUtility.DEAL_SUCCESS(null,null, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 获取全部供应商；列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findAllManu.act")
	public void findAllManu(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String organization = ChainUtil.getCurrentOrganization(request);
		try {
			List<JSONObject> list = ykzzManuService.findAllManu(organization);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}
}
