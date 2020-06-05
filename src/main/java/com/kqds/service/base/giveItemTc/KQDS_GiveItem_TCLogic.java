package com.kqds.service.base.giveItemTc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsGiveitemTc;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes" })
@Service
public class KQDS_GiveItem_TCLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	/**
	 * 根据消费项目编号统计###########这里的编号是收费项目的主键
	 * 
	 * @param conn
	 * @param itemnos
	 * @return
	 * @throws Exception
	 */
	public int getCountByItemnos(String costitemSeqId) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.KQDS_GIVEITEM_TC + ".getCountByItemnos", costitemSeqId);
		return count;
	}

	@SuppressWarnings("unchecked")
	public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		// 分页插件后的查询，会自动进行分页
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_GIVEITEM_TC + ".selectWithPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	// 查询套餐
	@SuppressWarnings("unchecked")
	public List getSelectTc(String organization) throws Exception {
		List<KqdsGiveitemTc> list = new ArrayList<KqdsGiveitemTc>();
		List<JSONObject> listJson = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_GIVEITEM_TC + ".getSelectTc", organization);
		for (JSONObject typeRs : listJson) {
			KqdsGiveitemTc dict = new KqdsGiveitemTc();
			dict.setSeqId(typeRs.getString("seq_id"));
			dict.setName(typeRs.getString("name"));
			dict.setNum(typeRs.getString("num"));
			list.add(dict);
		}
		return list;
	}

}