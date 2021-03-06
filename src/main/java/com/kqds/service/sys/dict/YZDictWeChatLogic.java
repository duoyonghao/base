package com.kqds.service.sys.dict;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZDict;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.sys.DictUtil;

import net.sf.json.JSONObject;

/**
 * 微信相关
 * 
 * @author Administrator
 * 
 */
@Service
public class YZDictWeChatLogic extends BaseLogic {

	@Autowired
	private DaoSupport dao;

	/**
	 * 获取收费项目一级分类
	 * 
	 * @param dbConn
	 * @param search
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<YZDict> getLeve1SortList(String search, String organization) throws Exception {
		JSONObject json = new JSONObject();
		json.put("parentCode", DictUtil.WECHAT_KEYWORD);
		// json.put("search", search);
		json.put("organization", organization);
		List<YZDict> list = (List<YZDict>) dao.findForList(TableNameUtil.SYS_DICT + ".getLeve1SortList", json);
		return list;
	}

}
