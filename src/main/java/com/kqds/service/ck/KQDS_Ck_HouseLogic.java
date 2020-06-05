package com.kqds.service.ck;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.other.ChineseCharToEn;

import net.sf.json.JSONObject;

@Service
public class KQDS_Ck_HouseLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	/**
	 * 删除仓库所有数据
	 * 
	 * @param conn
	 * @param request
	 * @throws Exception
	 */
	public void emptyCK() throws Exception {
		dao.deleteAll(TableNameUtil.KQDS_CK_DEPT);
		dao.deleteAll(TableNameUtil.KQDS_CK_GOODS);
		dao.deleteAll(TableNameUtil.KQDS_CK_GOODS_DETAIL);
		dao.deleteAll(TableNameUtil.KQDS_CK_GOODS_IN);
		dao.deleteAll(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL);
		dao.deleteAll(TableNameUtil.KQDS_CK_GOODS_OUT);
		dao.deleteAll(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL);
		dao.deleteAll(TableNameUtil.KQDS_CK_GOODS_TYPE);
		dao.deleteAll(TableNameUtil.KQDS_CK_HOUSE);
		dao.deleteAll(TableNameUtil.KQDS_CK_SUPPLIER);
	}

	@SuppressWarnings("unchecked")
	public List<JSONObject> selectList(Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_HOUSE + ".selectList", map);
		return list;
	}

	/**
	 * 获取唯一编号
	 * 
	 * @param conn
	 * @param housename
	 * @return
	 * @throws Exception
	 */
	public String getUniCodeByName(String housename) throws Exception {
		String code = ChineseCharToEn.getAllFirstLetter_RandNum(housename);
		int count = (int) dao.findForObject(TableNameUtil.KQDS_CK_HOUSE + ".getUniCodeByName", housename);
		if (count == 0) {
			return code;
		} else {
			return getUniCodeByName(housename);
		}
	}
}