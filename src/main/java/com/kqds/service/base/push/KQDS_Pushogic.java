package com.kqds.service.base.push;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsPush;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes" })
@Service
public class KQDS_Pushogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	/**
	 * 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param table
	 * @param bp
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map,JSONObject json) throws Exception {
		
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList("KQDS_PUSH.selectWithPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	/**
	 * 查询消息数量
	 * 
	 * @param conn
	 * @param table
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int selectPageNum(String userId) throws Exception {
		int count = (int) dao.findForObject("KQDS_PUSH.selectPageNum", userId);
		return count;
	}

	/**
	 * 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param table
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectNoPageWithUserId(String table, String userId) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("KQDS_PUSH.selectNoPageWithUserId", userId);
		List<JSONObject> listNew = new ArrayList<JSONObject>();
		for (JSONObject jsonObject : list) {
			String isnowpush = jsonObject.getString("isnowpush");
			if ("1".equals(isnowpush)) {
				// 非及时推送 比较时间
				String targetPushTime = jsonObject.getString("targetpushtime");
				if (!YZUtility.isNullorEmpty(targetPushTime)) {
					int flag = YZUtility.compare_date(targetPushTime, YZUtility.getCurDateTimeStr());
					if ("1".equals(flag)) { // 即目标时间大于当前时间
						// 移除
						continue;
					}
				}
			}
			listNew.add(jsonObject);
		}

		return listNew; // 返回neWlist
	}

	// 更新推送消息状态
	public void updateReadStatus(KqdsPush dp) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pcpushreadedtime", dp.getPcpushedtime());
		if (!YZUtility.isNullorEmpty(dp.getSeqId())) {
			map.put("seqId", YZUtility.ConvertStringIds4Query(dp.getSeqId()));
		}else if(!YZUtility.isNullorEmpty(dp.getReciveuser())){
			map.put("reciveuser", dp.getReciveuser());
		}
		dao.update("KQDS_PUSH.updateReadStatus", map);
	}

	// 更新推送消息状态
	public void updatePushStatus(KqdsPush dp) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pcpushedtime", dp.getPcpushreadedtime());
		if (!YZUtility.isNullorEmpty(dp.getSeqId())) {
			map.put("seqId", YZUtility.ConvertStringIds4Query(dp.getSeqId()));
		}
		dao.update("KQDS_PUSH.updatePushStatus", map);
	}

	public void deleteVistPushInfo(String usercode, String reciveuser) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("reciveuser", reciveuser);
		map.put("usercode", usercode);
		dao.delete("KQDS_PUSH.deleteVistPushInfo", map);
	}
	/**
	 * 推送未读五条
	 * <p>Title: selectTop5ByTime</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年11月9日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> selectTop5ByTime (Map<String,String> map) throws Exception{
		List<JSONObject> list = (List<JSONObject>) dao.findForList("KQDS_PUSH.selectTop5ByTime", map);
		return list;
	}
	/**
	 * 查询是否已经推送当天回访
	 * <p>Title: selectPushStatus</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年11月9日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String selectPushSeqid (Map<String,String> map) throws Exception{
		String list = (String) dao.findForObject("KQDS_PUSH.selectPushSeqid", map);
		return list;
	}
}