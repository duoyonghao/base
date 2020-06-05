package com.kqds.controller.sys.cache;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("YZCacheAct")
public class YZCacheAct {
	private Logger logger = LoggerFactory.getLogger(YZCacheAct.class);
	@Autowired
	private YZPersonLogic personLogic;

	/**
	 * 登录成功后缓存【异步】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loginCache.act")
	public String loginCache(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false); // 不存在就报错
		List<JSONObject> treeList = new ArrayList<JSONObject>();
		try {
			long startTime = System.currentTimeMillis(); // 获取开始时间
			treeList = personLogic.getDeptNodeList("0", treeList, null);
			long endTime = System.currentTimeMillis(); // 获取结束时间
			// System.out.println("程序运行时间： "+(endTime-startTime)+"ms ### Login
			// Cache ###
			// SUCCESS");
			logger.error("程序运行时间： " + (endTime - startTime) + "ms ### Login Cache ### SUCCESS");
			// 缓存
			session.setAttribute("PERSON_TREE_DATA", treeList);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

}
