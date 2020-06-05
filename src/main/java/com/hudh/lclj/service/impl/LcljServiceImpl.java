package com.hudh.lclj.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.hudh.lclj.StaticVar;
import com.hudh.lclj.dao.LcljDao;
import com.hudh.lclj.dao.LcljOrderImplemenDao;
import com.hudh.lclj.dao.LcljTrackDao;
import com.hudh.lclj.entity.LcljOperate;
import com.hudh.lclj.entity.LcljOperateDetail;
import com.hudh.lclj.entity.LcljOrder;
import com.hudh.lclj.entity.LcljOrderImplemen;
import com.hudh.lclj.entity.LcljOrderTrack;
import com.hudh.lclj.entity.PreoperativeVerification;
import com.hudh.lclj.service.ILcljService;
import com.hudh.lclj.util.OrderNumberUtil;
import com.hudh.lclj.util.ParseOperateXml;
import com.hudh.util.HUDHStaticVar;
import com.hudh.util.HUDHUtil;
import com.kqds.dao.DaoSupport;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 临床路径service
 * 
 * @author XKY
 *
 */
@Service
public class LcljServiceImpl implements ILcljService {

	@Autowired
	private DaoSupport dao;
	/**
	 * 临床路径dao
	 */
	@Autowired
	private LcljDao lcljDao;

	/**
	 * 临床路径跟踪dao
	 */
	@Autowired
	private LcljTrackDao lcljTrackDao;

	/**
	 * 项目实施情况dao
	 */
	@Autowired
	private LcljOrderImplemenDao lcljOrderImplemenDao;

	@SuppressWarnings("static-access")
	@Override
	public int saveLcljOrder(LcljOrder lcljOrder) throws Exception {
		lcljOrder.setId(YZUtility.getUUID());
		lcljOrder.setStatus(HUDHStaticVar.COMPLETE_STATE_WWC);
		lcljOrder.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
		lcljOrder.setRemainTooth(lcljOrder.getTotalTooth());// 创建是剩余颗数=种植总数
		String orderNumber = OrderNumberUtil.getInstance().getNextOrderNumber();// 获取下一个编号
		lcljOrder.setOrderNumber(orderNumber);
		return lcljDao.saveLcljOrder(lcljOrder);
	}

	@Override
	public JSONObject findLcljOrderByBlcode(String blCode) throws Exception {
		return lcljDao.findLcljOrderByBlcode(blCode);
	}

	public List<JSONObject> findLcljOrderByBlcodeAndStu(String blCode, String status) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("blCode", blCode);
		map.put("status", status);
		return lcljDao.findLcljOrderByBlcodeAndStu(map);
	}

	@Override
	public LcljOrder findLcljOrderByOrderNumber(String orderNumber) throws Exception {
		// TODO Auto-generated method stub
		return lcljDao.findLcljOrderByOrderNumber(orderNumber);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(rollbackFor = { Exception.class })
	@Override
	public void saveLcljOrderTrackInfo(LcljOrderTrack lcljOrderTrack) throws Exception {
		String id = YZUtility.getUUID();
		// 填充跟踪表数据
		lcljOrderTrack.setId(id);
		lcljOrderTrack.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
		lcljOrderTrack.setFlowLink(StaticVar.HUDH_LCLJ_FLOW_SS);
		lcljOrderTrack.setSsStatus(HUDHStaticVar.COMPLETE_STATE_WWC);
		lcljOrderTrack.setSsStu(HUDHStaticVar.COMPLETE_STATE_WWC);
		lcljOrderTrack.setShgcStu(HUDHStaticVar.COMPLETE_STATE_WWC);
		lcljOrderTrack.setDyStu(HUDHStaticVar.COMPLETE_STATE_WWC);
		// 构建项目实施情况表数据
		LcljOrderImplemen lcljOrderImplemen = new LcljOrderImplemen();
		lcljOrderImplemen.setId(YZUtility.getUUID());
		lcljOrderImplemen.setParentid(id);
		// 创建原始的操作项数据
		createOperateInfo(lcljOrderTrack.getType(), lcljOrderTrack.getBone(), lcljOrderImplemen);

		// 保存数据路径表和项目操作表数据
		lcljTrackDao.saveLcljOrderTrack(lcljOrderTrack);
		lcljOrderImplemenDao.saveLcljOrderImplemen(lcljOrderImplemen);

		// 更新order表剩余牙齿数
		Map map = new HashMap<String, Object>();
		map.put("ssTooth", Integer.parseInt(lcljOrderTrack.getTooth()));
		map.put("orderNumber", lcljOrderTrack.getOrderNumber());
		lcljDao.updateRemaintooth(map);
	}

	/**
	 * 根据xml配置获取每步下面的操作项构建初始json赋值给lcljOrderImplemen对象的不同字段
	 * 
	 * @param type
	 *            跟踪类型
	 * @param bone
	 *            是否植骨
	 * @param lcljOrderImplemen
	 */
	private void createOperateInfo(String type, String bone, LcljOrderImplemen lcljOrderImplemen) {
		// TODO Auto-generated method stub
		Map<String, String> operateMap = new HashMap<String, String>();
		if (type.equals(StaticVar.HUDH_LCLJ_DDK) && bone.equals(StaticVar.HUDH_LCLJ_NO)) { // 单颗多颗
																							// ---无植骨
			operateMap = ParseOperateXml.getOperateMap(StaticVar.HUDH_LCLJ_SINGLEORMULTINOBONE);
		} else if (type.equals(StaticVar.HUDH_LCLJ_DDK) && bone.equals(StaticVar.HUDH_LCLJ_YES)) { // 单颗多颗
																									// ---植骨
			operateMap = ParseOperateXml.getOperateMap(StaticVar.HUDH_LCLJ_SINGLEORMULTIBONE);
		} else if (type.equals(StaticVar.HUDH_LCLJ_LOCATOR) && bone.equals(StaticVar.HUDH_LCLJ_NO)) { // Locator---无植骨
			operateMap = ParseOperateXml.getOperateMap(StaticVar.HUDH_LCLJ_LOCATORNOBONE);
		} else if (type.equals(StaticVar.HUDH_LCLJ_LOCATOR) && bone.equals(StaticVar.HUDH_LCLJ_YES)) { // Locator---植骨
			operateMap = ParseOperateXml.getOperateMap(StaticVar.HUDH_LCLJ_LOCATORBONE);
		} else if (type.equals(StaticVar.HUDH_LCLJ_ALLONX) && bone.equals(StaticVar.HUDH_LCLJ_NO)) { // All-ob-x---无植骨
			operateMap = ParseOperateXml.getOperateMap(StaticVar.HUDH_LCLJ_ALLONXNOBONE);
		} else if (type.equals(StaticVar.HUDH_LCLJ_ALLONX) && bone.equals(StaticVar.HUDH_LCLJ_YES)) { // All-ob-x---植骨
			operateMap = ParseOperateXml.getOperateMap(StaticVar.HUDH_LCLJ_ALLONXBONE);
		}

		if (null != operateMap) {
			String ss = null;// 手术
			String shgc = null;// 术后观察
			String dy = null;// 戴牙

			LcljOperate lcljOperate = null;
			List<LcljOperate> lcljOperateList = null;

			for (Map.Entry<String, String> entry : operateMap.entrySet()) {
				lcljOperateList = new ArrayList<LcljOperate>();
				String key = entry.getKey();
				String value = entry.getValue();
				String[] optArray = value.split(";");
				int length = optArray.length;

				for (int i = 0; i < length; i++) {
					lcljOperate = new LcljOperate();
					lcljOperate.setName(optArray[i]);
					lcljOperate.setIsComplate(HUDHStaticVar.COMPLETE_STATE_WWC);
					lcljOperateList.add(lcljOperate);
				}
				// xml中配置的key值-手术,术后恢复，戴牙赋值给不同的字段
				if (key.equals("operation")) {
					ss = JSON.toJSONString(lcljOperateList);
				} else if (key.equals("recovery")) {
					shgc = JSON.toJSONString(lcljOperateList);
				} else if (key.equals("wearTeeth")) {
					dy = JSON.toJSONString(lcljOperateList);
				}
			}
			lcljOrderImplemen.setSs(ss);
			lcljOrderImplemen.setShgc(shgc);
			lcljOrderImplemen.setDy(dy);
		}
	}

	@Override
	public int findLcljOrderTrackByOrderNumber(String orderNumber) throws Exception {
		// TODO Auto-generated method stub
		List<JSONObject> list = lcljTrackDao.findLcljOrderTrackByOrderNumber(orderNumber);
		int ssTime = 0;
		if (null != list) {
			ssTime = list.size();
		}
		return ssTime;
	}

	public List<JSONObject> findLcljOrderTrackListByOrderNumber(String orderNumber) throws Exception {
		List<JSONObject> list = lcljTrackDao.findLcljOrderTrackByOrderNumber(orderNumber);
		if (null != list) {
			return list;
		}
		return null;
	}

	@Override
	public void updateOperateNoteInfo(String orderTrackId, String flowLink, String operateName, String remake)
			throws Exception {
		// 获取项目操作表信息
		LcljOrderImplemen lcljOrderImplemen = lcljOrderImplemenDao.findLcljOrderImplemenByTrackId(orderTrackId);
		// 获取需要修改的JSON字符串
		String jsonStr = null;
		if (StaticVar.HUDH_LCLJ_FLOW_SS.equals(flowLink)) { // 手术
			jsonStr = lcljOrderImplemen.getSs();
			jsonStr = setRemakeToJsonStr(jsonStr, operateName, remake);
			lcljOrderImplemen.setSs(jsonStr);
		} else if (StaticVar.HUDH_LCLJ_FLOW_SHGC.equals(flowLink)) { // 术后恢复
			jsonStr = lcljOrderImplemen.getShgc();
			jsonStr = setRemakeToJsonStr(jsonStr, operateName, remake);
			lcljOrderImplemen.setShgc(jsonStr);
		} else if (StaticVar.HUDH_LCLJ_FLOW_DY.equals(flowLink)) { // 戴牙
			jsonStr = lcljOrderImplemen.getDy();
			jsonStr = setRemakeToJsonStr(jsonStr, operateName, remake);
			lcljOrderImplemen.setDy(jsonStr);
		}
		// 将更新后的数据更新到数据库中
		lcljOrderImplemenDao.updateOperateNoteInfo(lcljOrderImplemen);
	}

	/**
	 * 将remake更新到对应字段的json字符串中
	 * 
	 * @param jsonStr
	 * @param operateName
	 * @param remake
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String setRemakeToJsonStr(String jsonStr, String operateName, String remake) {
		Map childClazzMap = new HashMap();
		childClazzMap.put("detail", LcljOperateDetail.class);
		List<LcljOperate> list = HUDHUtil.parseJsonToObjectList(jsonStr, LcljOperate.class, childClazzMap);
		for (LcljOperate lop : list) {
			List<LcljOperateDetail> tempList = null;
			if (lop.getName().equals(operateName)) {
				tempList = lop.getDetail();
				if (null == tempList) {
					tempList = new ArrayList<LcljOperateDetail>();
				}
				LcljOperateDetail ld = new LcljOperateDetail();
				ld.setTime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
				ld.setRemake(remake);
				tempList.add(ld);
				lop.setDetail(tempList);
				break;
			}
		}
		return JSON.toJSONString(list);
	}

	@Override
	public int findHasOrderByBlcodeAndStu(String blCode) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("blcode", blCode);
		paraMap.put("status", HUDHStaticVar.COMPLETE_STATE_WWC);
		return lcljDao.findHasOrder(paraMap);
	}

	@Override
	public JSONObject findLcljOrderTrsackById(String orderTrackId) throws Exception {
		// TODO Auto-generated method stub
		JSONObject jsonOb = lcljTrackDao.findLcljOrderTrsackById(orderTrackId);
		if (null != jsonOb) {
			return jsonOb;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateOperateStatus(String ordernumber, String operateName) throws Exception {
		// LcljOrderTrack lcljo = (LcljOrderTrack) dao.findForObject("HUDH_LCLJ_ORDERTRACK.findLcljOrderTrsackByOrderNumber",ordernumber);
		List<JSONObject> list = (List<JSONObject>) lcljTrackDao.findLcljOrderTrackByOrderNumber(ordernumber);
		if (list != null && list.size() > 0) {
			String jsonStr = JSON.toJSONString(list);//
			List<LcljOrderTrack> list1 = HUDHUtil.parseJsonToObjectList(jsonStr, LcljOrderTrack.class);
			LcljOrderTrack lcljob = list1.get(0);// 获取LcljOrderTrack对象
			if (lcljob.getDyStu().equals(HUDHStaticVar.COMPLETE_STATE_YWC)) {
				lcljob.setSsStatus(HUDHStaticVar.COMPLETE_STATE_YWC);
				lcljob.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
//				lcljTrackDao.updateOperateStatus(orderTrackId);
			}
		}
		// lcljo.setSsStatus(HUDHStaticVar.COMPLETE_STATE_YWC);
		// lcljo.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public void changeOperateStatus(String operateName, String flowLink, String orderTrackId) throws Exception {
		// TODO Auto-generated method stub
		LcljOrderImplemen lcljOrderImplemen = lcljOrderImplemenDao.findLcljOrderImplemenByTrackId(orderTrackId);
		Map childClazzMap = new HashMap();
		childClazzMap.put("detail", LcljOperateDetail.class);
		// 获取需要修改的JSON字符串
		String jsonStr = null;
		if (StaticVar.HUDH_LCLJ_FLOW_SS.equals(flowLink)) { // 手术
			jsonStr = lcljOrderImplemen.getSs();
			List<LcljOperate> list = HUDHUtil.parseJsonToObjectList(jsonStr, LcljOperate.class, childClazzMap);
			for (int i = 0; i < list.size(); i++) {
				LcljOperate l = list.get(i);
				String name = l.getName();
				if (operateName.equals(name)) {
					l.setIsComplate(HUDHStaticVar.COMPLETE_STATE_YWC);
					break;
				}
			}
			String jsonStri = JSON.toJSONString(list);
			lcljOrderImplemen.setSs(jsonStri);
		} else if (StaticVar.HUDH_LCLJ_FLOW_SHGC.equals(flowLink)) { // 术后恢复
			jsonStr = lcljOrderImplemen.getShgc();
			List<LcljOperate> list = HUDHUtil.parseJsonToObjectList(jsonStr, LcljOperate.class, childClazzMap);
			for (int i = 0; i < list.size(); i++) {
				LcljOperate l = list.get(i);
				String name = l.getName();
				if (operateName.equals(name)) {
					l.setIsComplate(HUDHStaticVar.COMPLETE_STATE_YWC);
					break;
				}
			}
			String jsonStri = JSON.toJSONString(list);
			lcljOrderImplemen.setShgc(jsonStri);
		} else if (StaticVar.HUDH_LCLJ_FLOW_DY.equals(flowLink)) { // 戴牙
			jsonStr = lcljOrderImplemen.getDy();
			List<LcljOperate> list = HUDHUtil.parseJsonToObjectList(jsonStr, LcljOperate.class, childClazzMap);
			for (int i = 0; i < list.size(); i++) {
				LcljOperate l = list.get(i);
				String name = l.getName();
				if (operateName.equals(name)) {
					l.setIsComplate(HUDHStaticVar.COMPLETE_STATE_YWC);
					break;
				}
			}
			String jsonStri = JSON.toJSONString(list);
			lcljOrderImplemen.setDy(jsonStri);
		}
		// 将更新后的数据更新到数据库中
		lcljOrderImplemenDao.updateOperateNoteInfo(lcljOrderImplemen);
	}
	
	@Override
	public String findOperateByTrackIdAndLink(String orderTrackId, String flowLink,String oprationName) throws Exception {
		// TODO Auto-generated method stub
		LcljOrderImplemen lcljOrderImplemen = lcljOrderImplemenDao.findLcljOrderImplemenByTrackId(orderTrackId);
		String operates = null;
		if (null != lcljOrderImplemen) {
			if (StaticVar.HUDH_LCLJ_FLOW_SS.equals(flowLink)) {
				operates = lcljOrderImplemen.getSs();
			} else if (StaticVar.HUDH_LCLJ_FLOW_SHGC.equals(flowLink)) {
				operates = lcljOrderImplemen.getShgc();
			} else if (StaticVar.HUDH_LCLJ_FLOW_DY.equals(flowLink)) {
				operates = lcljOrderImplemen.getDy();
			}
		}
		return operates;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LcljOperateDetail> findLcljOrderImplemenRemakeByTrackId(String orderTrackId,String flowLink,String oprationName) throws Exception {
		// TODO Auto-generated method stub
		LcljOrderImplemen lcljOrderImplemen = lcljOrderImplemenDao.findLcljOrderImplemenByTrackId(orderTrackId);
		Map childClazzMap = new HashMap();
		childClazzMap.put("detail", LcljOperateDetail.class);
		JSONObject jsonObj = new JSONObject();
		String operates = null;
		if (null != lcljOrderImplemen) {
			if (StaticVar.HUDH_LCLJ_FLOW_SS.equals(flowLink)) {
				operates = lcljOrderImplemen.getSs();
				List<LcljOperate> list = HUDHUtil.parseJsonToObjectList(operates, LcljOperate.class, childClazzMap);
				if (list != null && list.size() > 0) {
				    for (int i = 0; i < list.size(); i++) {
				    	String name = list.get(i).getName();
				    	if (oprationName.equals(name)) {
				    		List<LcljOperateDetail> remak = list.get(i).getDetail();
				    		return remak;
				    	}
					}
				}
			} else if (StaticVar.HUDH_LCLJ_FLOW_SHGC.equals(flowLink)) {
				operates = lcljOrderImplemen.getShgc();
				List<LcljOperate> list = HUDHUtil.parseJsonToObjectList(operates, LcljOperate.class, childClazzMap);
			    for (int i = 0; i < list.size(); i++) {
			    	String name = list.get(i).getName();
			    	if (oprationName.equals(name)) {
			    		List<LcljOperateDetail> remak = list.get(i).getDetail();
			    		return remak;
			    	}
				}
			} else if (StaticVar.HUDH_LCLJ_FLOW_DY.equals(flowLink)) {
				operates = lcljOrderImplemen.getDy();
				List<LcljOperate> list = HUDHUtil.parseJsonToObjectList(operates, LcljOperate.class, childClazzMap);
			    for (int i = 0; i < list.size(); i++) {
			    	String name = list.get(i).getName();
			    	if (oprationName.equals(name)) {
			    		List<LcljOperateDetail> remak = list.get(i).getDetail();
			    		return remak;
			    	}
				}
			}
		}
		return null;
	}
	
	@Override
	public void updateOrderStatus(String orderNumber) throws Exception {
		// TODO Auto-generated method stub
		/*
		JSONObject jsonO = lcljTrackDao.findLcljOrderTrsackById(id);
		LcljOrderTrack l = (LcljOrderTrack) jsonO.toBean(jsonO);
		if (HUDHStaticVar.COMPLETE_STATE_YWC.equals(l.getSsStatus())) {
		}
		*/
		int total_tooth = 0;
		int tooth = 0;
		List<JSONObject> jsono = lcljTrackDao.findToohthNum(orderNumber);
		for (int i = 0; i < jsono.size(); i++) {
		    Object j = jsono.get(i).get("total_tooth");
		    Object t = jsono.get(i).get("tooth");
		    String j1 = j.toString();
		    int total_tooth1 = Integer.valueOf(j1);
		    total_tooth = total_tooth1;
		    String t1 = t.toString();
		    int tooth1 = Integer.valueOf(t1);
		    tooth += tooth1;
			System.out.println(total_tooth);
			System.out.println(tooth1);
		}
				
		if (total_tooth == tooth) {
			lcljDao.updateOrderStatus(orderNumber);
		}
	}
	
		
	@SuppressWarnings("unchecked")
	@Override
	public void updateOperationFlowStatus(String operateName, String orderTrackId, String flowLink)
			throws Exception {
		LcljOrderTrack lclj = new LcljOrderTrack();
		LcljOrderImplemen lcljOrderImplemen = lcljOrderImplemenDao.findLcljOrderImplemenByTrackId(orderTrackId);
		Map childClazzMap = new HashMap();
		childClazzMap.put("detail", LcljOperateDetail.class);
		// 获取需要修改的JSON字符串
		String jsonStr = null;
		boolean flagStu = true;
		String flag = null;
		if (StaticVar.HUDH_LCLJ_FLOW_SS.equals(flowLink)) { // 手术
			flag = "ss";
			jsonStr = lcljOrderImplemen.getSs();
			List<LcljOperate> list = HUDHUtil.parseJsonToObjectList(jsonStr, LcljOperate.class, childClazzMap);
			for (LcljOperate lcljOperate : list) {
				if(lcljOperate.getName().equals(operateName)) {
					lcljOperate.setIsComplate(HUDHStaticVar.COMPLETE_STATE_YWC);//当前点击操作
				} else {
					if(lcljOperate.getIsComplate().equals(HUDHStaticVar.COMPLETE_STATE_WWC)) {
						flagStu = false;//标识是否需要更新
					}
				}
			}
			String jsonStri = JSON.toJSONString(list);
			lcljOrderImplemen.setSs(jsonStri);
			// 将更新后的数据更新到数据库中
			lcljOrderImplemenDao.updateOperateNoteInfo(lcljOrderImplemen);
		} else if (StaticVar.HUDH_LCLJ_FLOW_SHGC.equals(flowLink)) { // 术后恢复
			flag = "shgc";
			jsonStr = lcljOrderImplemen.getShgc();
			List<LcljOperate> list = HUDHUtil.parseJsonToObjectList(jsonStr, LcljOperate.class, childClazzMap);
			for (LcljOperate lcljOperate : list) {
				if(lcljOperate.getName().equals(operateName)) {
					lcljOperate.setIsComplate(HUDHStaticVar.COMPLETE_STATE_YWC);
				} else {
					if(lcljOperate.getIsComplate().equals(HUDHStaticVar.COMPLETE_STATE_WWC)) {//判断其他操作还有未完成，不更新
						flagStu = false;//标识是否需要更新
					}
				}
			}
			String jsonStri = JSON.toJSONString(list);
			lcljOrderImplemen.setShgc(jsonStri);
			// 将更新后的数据更新到数据库中
			lcljOrderImplemenDao.updateOperateNoteInfo(lcljOrderImplemen);
		} else if (StaticVar.HUDH_LCLJ_FLOW_DY.equals(flowLink)) { // 戴牙
			flag = "dy";
			jsonStr = lcljOrderImplemen.getDy();
			List<LcljOperate> list = HUDHUtil.parseJsonToObjectList(jsonStr, LcljOperate.class, childClazzMap);
			for (LcljOperate lcljOperate : list) {
				if(lcljOperate.getName().equals(operateName)) {
					lcljOperate.setIsComplate(HUDHStaticVar.COMPLETE_STATE_YWC);
				} else {
					if(lcljOperate.getIsComplate().equals(HUDHStaticVar.COMPLETE_STATE_WWC)) {
						flagStu = false;//标识是否需要更新
					}
				}
			}
			String jsonStri = JSON.toJSONString(list);
			lcljOrderImplemen.setDy(jsonStri);
			// 将更新后的数据更新到数据库中
			lcljOrderImplemenDao.updateOperateNoteInfo(lcljOrderImplemen);
		}
		
		if( flagStu ) {
			//跟新第二张表
			lcljTrackDao.updateOperationFlowStatus(flag, orderTrackId);
		}
		
		if(flag.equals("dy") && flagStu  ){
			lcljTrackDao.updateOperateStatus(orderTrackId);
		}
		
	}

	/**   
	  * <p>Title: savePreoperativeVerification</p>   
	  * <p>Description: </p>   
	  * @param pVerification
	  * @return   
	 * @throws Exception 
	  * @see com.hudh.lclj.service.ILcljService#savePreoperativeVerification(com.hudh.lclj.entity.PreoperativeVerification)   
	  */  
	@Override
	public JSONObject savePreoperativeVerification(PreoperativeVerification pVerification) throws Exception {
		// TODO Auto-generated method stub
		 return lcljTrackDao.savePreoperativeVerification(pVerification);
	}

	/**   
	  * <p>Title: findPreoperativeVerification</p>   
	  * <p>Description: </p>   
	  * @param lcljId
	  * @return
	  * @throws Exception   
	  * @see com.hudh.lclj.service.ILcljService#findPreoperativeVerification(java.lang.String)   
	  */  
	@Override
	public JSONObject findPreoperativeVerification(String lcljId) throws Exception {
		// TODO Auto-generated method stub
		return lcljTrackDao.findPreoperativeVerification(lcljId);
	}
	
}
