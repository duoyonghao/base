package com.kqds.service.wx.news;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.kqds.core.global.YZSysProps;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.wx.WXNews;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Service
public class WXNewsLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;
	@Autowired
	private WXNewsItemLogic itemLogic;

	/**
	 * 获取图文信息，用于发送
	 * 
	 * @param newsid
	 * @param request
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public JSONObject getNewsObject4Send(String newsid, HttpServletRequest request) throws SQLException, Exception {
		String msgtype = "news";
		String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID");
		String website_url = YZSysProps.getString("WEBSITE_URL");

		WXNews news = (WXNews) itemLogic.loadObjSingleUUID(TableNameUtil.WX_NEWS, newsid);

		if (news == null) {
			throw new Exception("图文不存在");
		}

		List<JSONObject> itemList = itemLogic.getList(newsid);

		if (itemList == null || itemList.size() == 0) {
			throw new Exception("图文列表为空");
		}

		for (JSONObject item : itemList) {
			String imagepath = item.getString("imagepath");
			String imageurl = website_url + request.getContextPath() + imagepath;
			String newsurl = website_url + request.getContextPath() + "/WXNewsItemAct/toDetail.act?seqId=" + item.getString("seqId");
			if (!YZUtility.isNullorEmpty(item.getString("url"))) {
				newsurl = item.getString("url");
			}
			item.put("imageurl", imageurl);
			item.put("newsurl", newsurl);
		}

		JSONObject newsMsg = new JSONObject();

		newsMsg.put("msgtype", msgtype);
		newsMsg.put("itemlist", itemList);
		newsMsg.put("accountid", accountid);
		newsMsg.put("newsid", newsid);
		return newsMsg;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.WX_NEWS + ".selectWithPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());

		for (JSONObject news : list) {
			String seqId = news.getString("seqId");
			List<JSONObject> itemlist = itemLogic.getList(seqId);
			news.put("itemlist", itemlist);
			news.put("itemcount", itemlist.size());
		}
		jobj.put("rows", list);
		return jobj;
	}

	@SuppressWarnings("unchecked")
	public List<WXNews> getList(String newstype) throws Exception {
		List<WXNews> list = (List<WXNews>) dao.findForList(TableNameUtil.WX_NEWS + ".getList", newstype);
		return list;
	}

}
