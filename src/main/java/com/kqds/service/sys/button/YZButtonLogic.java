package com.kqds.service.sys.button;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZButton;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;

import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
@Service
public class YZButtonLogic extends BaseLogic {

	@Autowired
	private DaoSupport dao;

	public List<YZButton> getListBySeqIds(String seqids) throws Exception {
		List<String> idList = YZUtility.ConvertString2List(seqids);
		List<YZButton> list = (List<YZButton>) dao.findForList(TableNameUtil.SYS_BUTTON + ".getListBySeqIds", idList);
		return list;
	}

	public int deleteBySeqIds(String seqids, HttpServletRequest request) throws Exception {
		List<String> idList = YZUtility.ConvertString2List(seqids);
		int count = (int) dao.delete(TableNameUtil.SYS_BUTTON + ".deleteBySeqIds", idList);
		// 记录日志
		SysLogUtil.log(SysLogUtil.DELETE, SysLogUtil.SYS_BUTTON, seqids, TableNameUtil.SYS_BUTTON, request);
		return count;
	}

	public List<JSONObject> selectList(String parentid) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_BUTTON + ".selectList", parentid);
		return list;
	}

	/**
	 * 根据菜单ID 获取权限按钮，根据排序号升序排序
	 * 
	 * @param conn
	 * @param menuid
	 * @return
	 * @throws Exception
	 */
	public List<YZButton> getButtonList(String parentid) throws Exception {
		List<YZButton> list = (List<YZButton>) dao.findForList(TableNameUtil.SYS_BUTTON + ".getButtonList", parentid);
		return list;
	}
}
