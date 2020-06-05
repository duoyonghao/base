package com.hudh.ykzz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hudh.util.HUDHStaticVar;
import com.hudh.util.HUDHUtil;
import com.hudh.ykzz.entity.YkzzManufacturers;
import com.hudh.ykzz.service.IYzzManufacturersService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/YkzzManufacturersAct")
public class YkzzManufacturersAct {
	
	private Logger logger = LoggerFactory.getLogger(YkzzManufacturersAct.class);
	
	@Autowired
	private IYzzManufacturersService iYzzManufacturersService;
	
	/**
	 * 新增生产商
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertManufacturers.act")
	public String insertManufacturers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String manuName = request.getParameter("manuName");
		String orderno = request.getParameter("orderno");
		String manuCode = request.getParameter("manuCode");
		YkzzManufacturers ykzzManufacturers = new YkzzManufacturers();
		ykzzManufacturers.setManufacturersName(manuName);
		ykzzManufacturers.setId(YZUtility.getUUID());
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request);
		ykzzManufacturers.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
		ykzzManufacturers.setCreator(person.getUserName());
		ykzzManufacturers.setOrganization(organization);
		if(YZUtility.isNotNullOrEmpty(orderno)) {
			ykzzManufacturers.setOrderno(orderno);
		}
		ykzzManufacturers.setManufacturersCode(manuCode);
		try {
			iYzzManufacturersService.insertManufacturers(ykzzManufacturers);
			YZUtility.DEAL_SUCCESS(null,null, response, logger);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	@RequestMapping(value = "/deleteManufacturersById.act")
	public String deleteManufacturersById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		try {
			iYzzManufacturersService.deleteManufacturers(id);
			YZUtility.DEAL_SUCCESS(null,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	@RequestMapping(value= "/findAllManufacturers.act")
	public String findAllManufacturers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = ChainUtil.getCurrentOrganization(request);
		try {
			List<JSONObject> list = iYzzManufacturersService.findAllManufacturers(organization);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	@RequestMapping(value= "/updateManufacturersById.act")
	public String updateManufacturersById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String manuName = request.getParameter("manuName");
		String orderno = request.getParameter("orderno");
		YkzzManufacturers ykzzManufacturers = new YkzzManufacturers();
		ykzzManufacturers.setId(id);
		ykzzManufacturers.setOrderno(orderno);
		ykzzManufacturers.setManufacturersName(manuName);
		try {
			if(YZUtility.isNotNullOrEmpty(id) && YZUtility.isNotNullOrEmpty(manuName) && 
					YZUtility.isNotNullOrEmpty(orderno)) {
				iYzzManufacturersService.updateManufacturers(ykzzManufacturers);
				YZUtility.DEAL_SUCCESS(null,null, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	@RequestMapping(value= "/findManufacturersById.act")
	public String findManufacturersById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		try {
			JSONObject json = iYzzManufacturersService.findManufacturersById(id);
			YZUtility.DEAL_SUCCESS(json,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
}
