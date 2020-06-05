package com.hudh.lclj.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hudh.lclj.entity.LcljOrderImplemen;
import com.kqds.dao.DaoSupport;
/**
 * 项目实施情况表dao
 * @author XKY
 *
 */

import net.sf.json.JSONObject;
@Service
public class LcljOrderImplemenDao {
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 向项目实施情况表表插入数据
	 * @param lcljOrder
	 * @return
	 * @throws Exception
	 */
	public int saveLcljOrderImplemen(LcljOrderImplemen lcljOrderImplemen) throws Exception {
		int rsCount = (int) dao.save("HUDH_LCLJ_ORDERIMPLEMEN.insertLcljOrderImplemen", lcljOrderImplemen);
		return rsCount;
	}
	
	/**
	 * 根据orderTrackId=parentid查询项目实施情况表数据
	 * @param orderTrackId
	 * @return
	 * @throws Exception
	 */
	public LcljOrderImplemen findLcljOrderImplemenByTrackId(String orderTrackId) throws Exception {
		LcljOrderImplemen lcljOrderImplemen = (LcljOrderImplemen) dao.findForObject("HUDH_LCLJ_ORDERIMPLEMEN.findLcljOrderImplemenByTrackId", orderTrackId);
		return lcljOrderImplemen;
	}
	
	/**
	 * 更新操作的信息
	 * @throws Exception
	 */
	public LcljOrderImplemen updateOperateNoteInfo(LcljOrderImplemen lcljOrderImplemen)  throws Exception{
		LcljOrderImplemen lo = (LcljOrderImplemen) dao.findForObject("HUDH_LCLJ_ORDERIMPLEMEN.updateOperateNoteInfo", lcljOrderImplemen);
		return lo;
	}
	
	/**
	 * 更新操作环节操作的状态
	 * @throws Exception 
	 */
	public LcljOrderImplemen changeOperateStatus (LcljOrderImplemen lcljOrderImplemen) throws Exception {
		LcljOrderImplemen lo = (LcljOrderImplemen) dao.findForObject("HUDH_LCLJ_ORDERIMPLEMEN.updateOperateNoteInfo", lcljOrderImplemen);
		return lo;
	}
	
	/**
	 * 查看备注的信息
	 */
	public JSONObject selectRemakeInfor() {
		
		return null;
	}
}
