package com.kqds.service.ck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoodsType;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.other.ChineseCharToEn;

import net.sf.json.JSONObject;

@Service
public class KQDS_Ck_GoodstypeLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	// 不分页
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectList(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_TYPE + ".selectList", map);
		return list;
	}

	/**
	 * 一级分类
	 * 
	 * @param conn
	 * @param diaId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getBaseTypeSelect(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_TYPE + ".getBaseTypeSelect", map);
		return list;
	}

	/**
	 * 一级分类
	 * 
	 * @param conn
	 * @param diaId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getBNextTypeSelect(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_TYPE + ".getBNextTypeSelect", map);
		return list;
	}

	/**
	 * 删除分类 以及 下级分类
	 * 
	 * @param conn
	 * @param diaId
	 * @throws Exception
	 */
	public void delete(String seqId) throws Exception {
		dao.delete(TableNameUtil.KQDS_CK_GOODS_TYPE + ".deleteSeqId", seqId);
	}

	/**
	 * 获取唯一编号
	 * 
	 * @param conn
	 * @param goodstypename
	 * @param perid
	 * @return
	 * @throws Exception
	 */
	public String getUniTypenoByName(String goodstypename, String perid) throws Exception {
		String typeno = ChineseCharToEn.getAllFirstLetter_RandNum(goodstypename);
		JSONObject jobj = new JSONObject();
		jobj.put("typeno", typeno);
		jobj.put("perid", perid);
		int count = (int) dao.findForObject(TableNameUtil.KQDS_CK_HOUSE + ".getUniCodeByName", jobj);
		if (count == 0) {
			return typeno;
		} else {
			return getUniTypenoByName(goodstypename, perid);
		}
	}

	/**   
	  * @Title: getBNextType   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2020年4月3日 下午4:42:28
	  */  
	@SuppressWarnings("unchecked")
	@Transactional
	public void deleteType(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		List<KqdsCkGoodsType> list = (List<KqdsCkGoodsType>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_TYPE + ".findNextType",  map);
		if(list.size() > 0){
			for (KqdsCkGoodsType kqdsCkGoodsType : list) {
				Map<String, String> map2 = new HashMap<String, String>();
				map2.put("seqId", kqdsCkGoodsType.getSeqId());
				dao.delete(TableNameUtil.KQDS_CK_GOODS_TYPE + ".deleteByPrimaryKey", map2);
			}
				dao.delete(TableNameUtil.KQDS_CK_GOODS_TYPE + ".deleteByPrimaryKey", map);
		}else{
			dao.delete(TableNameUtil.KQDS_CK_GOODS_TYPE + ".deleteByPrimaryKey", map);
		}
	}
}