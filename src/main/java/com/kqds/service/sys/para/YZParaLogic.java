package com.kqds.service.sys.para;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZPara;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;

import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
@Service("paraLogic")
public class YZParaLogic extends BaseLogic {

	@Autowired
	private DaoSupport dao;

	/**
	 * 查询一些登录时需要的系统参数
	 * 
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public JSONObject getSysPara(String organization) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PARA + ".getSysPara", organization);
		JSONObject json = new JSONObject();
		for (JSONObject job : list) {
			String value = YZUtility.parseString(job.getString("para_value"));
			String name = YZUtility.parseString(job.getString("para_name"));
			json.put(name, value);
		}
		return json;
	}

	public List<JSONObject> selectList(String organization) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PARA + ".selectList", organization);
		return list;
	}

	public int countByName(YZPara dp) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.SYS_PARA + ".countByName", dp);
		return count;
	}

	public int deleteBySeqIds(String seqids, HttpServletRequest request) throws Exception {
		List<String> idList = YZUtility.ConvertString2List(seqids);
		int count = (int) dao.delete(TableNameUtil.SYS_PARA + ".deleteBySeqIds", idList);
		// 记录日志
		SysLogUtil.log(SysLogUtil.DELETE, SysLogUtil.SYS_PARA, seqids, TableNameUtil.SYS_PARA, request);
		return count;
	}

	public String getParaValueByName(Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PARA + ".getParaValueByName", map);
		if (list == null || list.size() == 0) {
			return "";
		} else {
			return list.get(0).getString("paraValue");
		}
	}

	public void initParaByOrganization(String organization) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.SYS_PARA + ".initParaByOrganization", organization);
		if (count > 0) {
			return;
		}

		String oldOrganization = getTopOrganization();
		List<YZPara> list = (List<YZPara>) dao.findForList(TableNameUtil.SYS_PARA + ".selectListBean", oldOrganization);
		for (YZPara yzPara : list) {
			yzPara.setSeqId(YZUtility.getUUID());
			yzPara.setOrganization(organization);
			dao.saveSingleUUID(TableNameUtil.SYS_PARA, yzPara);
		}

	}

	private String getTopOrganization() throws Exception {
		JSONObject json = (JSONObject) dao.findForObject(TableNameUtil.SYS_PARA + ".getTopOrganization", null);
		if (json == null) {
			throw new Exception("系统参数表中没有数据，无法初始化！");
		}

		String oldOrganization = json.getString("organization");
		return oldOrganization;
	}

}
