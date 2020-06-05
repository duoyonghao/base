package com.kqds.service.base.refund;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;

import net.sf.json.JSONObject;

@Service
public class KQDS_RefundLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;
	@Autowired
	private KQDS_CostOrder_DetailLogic detaillogic;

	/**
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @param person
	 * @param visualstaff
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectWithNopage(String table, Map<String, String> map, YZPerson person, String visualstaff, String organization) throws Exception {
		map.put("organization", organization);
		map.put("visualstaff", visualstaff);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REFUND + ".selectWithNopage", map);
		for (JSONObject jobj : list) {
			BigDecimal zsmoney = detaillogic.searchOrderZs(TableNameUtil.KQDS_COSTORDER_DETAIL, jobj.getString("costno"));
			jobj.put("zsmoney", zsmoney);
			jobj.put("ssmoney", KqdsBigDecimal.sub(new BigDecimal(jobj.getString("actualmoney")), zsmoney));
		}
		return list;
	}

	
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectByCostno(String kQDS_REFUND, String costno) throws Exception {
		return (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REFUND + ".selectByCostno", costno);
	}


	/**   
	  * @Title: tkQuery   
	  * @Description: TODO(退款)   
	  * @param: @param map
	  * @param: @return      
	  * @return: List<JSONObject>
	 * @throws Exception 
	  * @dateTime:2019年9月28日 上午9:30:50
	  */  
	@SuppressWarnings("unchecked")
	public List<JSONObject> tkQuery(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REFUND_DETAIL+".tkQuery", map);
	}

}