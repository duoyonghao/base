package com.kqds.service.ck;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Service
public class KQDS_Ck_Goods_Out_DetailLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	/**
	 * 根据商品主键，获取其出库数量
	 * 
	 * @param conn
	 * @param goodsuuid
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public int getOutNumByGoodsuuid(String goodsuuid) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL + ".getOutNumByGoodsuuid", goodsuuid);
		int count = 0;
		for (JSONObject json : list) {
			int outnum = json.getInt("outnum");
			count += outnum;
		}
		return count;
	}

	// 出库明细
	@SuppressWarnings("unchecked")
	public List<JSONObject> inSearchList(String table, Map<String, String> map) throws Exception {
		map.put("selectKcmoney", SQLUtil.convertDecimal("goutd.ckmoney", 18, 3));
		map.put("selectGoodsprice", SQLUtil.convertDecimal("goutd.outprice", 18, 3));
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL + ".inSearchList", map);
		for (JSONObject job : list) {
			String outtype = job.getString("outtype");
			if (ConstUtil.CK_OUT_1.equals(outtype)) {
				outtype = "领用出库";
			} else if (ConstUtil.CK_OUT_3.equals(outtype)) {
				outtype = "换货出库";
			} else if (ConstUtil.CK_OUT_5.equals(outtype)) {
				outtype = "退货出库";
			} else if (ConstUtil.CK_OUT_7.equals(outtype)) {
				outtype = "其他出库";
			}
			job.put("outtype", outtype);
		}

		return list;
	}
	/**
	 * 查询当天所有商品的出库数量
	 * <p>Title: findOutNumsByAll</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年12月17日 
	 * @return
	 * @throws Exception
	 */
	public int findOutNumsByAll() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String date = YZUtility.getDateStr(new Date());
		map.put("starttime", date + ConstUtil.TIME_START);
		map.put("endtime",date +  ConstUtil.TIME_END);
		int list = (int) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL + ".findOutNumsByAll", map);
		return list;
	}
	/**
	 * 查询该商品的当天出库详情，批号和出库数量
	 * <p>Title: findPhOutNumByGoodsuuid</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年12月17日 
	 * @param goodsuuid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findPhOutNumByGoodsuuid(String goodsuuid) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String date = YZUtility.getDateStr(new Date());
		map.put("starttime", date + ConstUtil.TIME_START);
		map.put("endtime",date +  ConstUtil.TIME_END);
		map.put("goodsuuid", goodsuuid);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL + ".findPhOutNumByGoodsuuid", map);
		return list;
	}
}