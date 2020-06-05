/**  
  *
  * @Title:  KQDS_MACHININGLogic.java   
  * @Package com.kqds.service.base.machiningCenter   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年12月19日 下午4:30:39   
  * @version V1.0  
  */ 
package com.kqds.service.base.machiningCenter;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsMachining;
import com.kqds.entity.base.KqdsMachiningRecord;
import com.kqds.entity.base.KqdsMachiningRepairType;
import com.kqds.entity.base.KqdsMachiningRepairWay;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  KQDS_MACHININGLogic   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年12月19日 下午4:30:39   
  *      
  */
@Service
public class KQDS_MACHININGLogic extends BaseLogic {

	@Autowired
	private DaoSupport dao;
	
	/**   
	  * @Title: insertOrUpdate   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dp      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年12月19日 下午4:53:25
	  */  
	@Transactional
	public boolean insert(KqdsMachining dp) throws Exception {
		// TODO Auto-generated method stub
		boolean tooth = false;
		Integer seqId = (Integer)dao.save(TableNameUtil.KQDS_MACHINING+".insert", dp);
		if(!YZUtility.isNullorEmpty(String.valueOf(seqId))){
			String substring = dp.getToothMapArr();
			List<KqdsMachiningRepairWay> asList = (List<KqdsMachiningRepairWay>)com.alibaba.fastjson.JSONArray.parseArray(substring, KqdsMachiningRepairWay.class);
			tooth = insertTooth(asList,dp);
		}
		
		return tooth;
	}
	@Transactional
	public boolean insertTooth(List<KqdsMachiningRepairWay> list,KqdsMachining dp) throws Exception{
		for (int i = 0; i < list.size(); i++) {
			KqdsMachiningRepairWay bean = list.get(i);
			if(bean != null){
				bean.setSeqId(YZUtility.getUUID());
				bean.setWorksheetId(dp.getSeqId());
				bean.setCreateuser(dp.getCreateuser());
				bean.setCreatetime(dp.getCreatetime());
				bean.setOrganization(dp.getOrganization());
			}
			Integer id = (Integer)dao.save(TableNameUtil.KQDS_MACHININGREPAIRWAY+".insert", bean);
			if(!YZUtility.isNullorEmpty(String.valueOf(id))){
				String substring = bean.getRepairProjectArr();
				List<KqdsMachiningRepairType> asList = (List<KqdsMachiningRepairType>)com.alibaba.fastjson.JSONArray.parseArray(substring, KqdsMachiningRepairType.class);
				batchrepair(asList, bean);
			}
		}
		return true;
	}
	
	/**
	  * @Title: batchrepair   
	  * @Description: TODO(插入修复项目)   
	  * @param: @param asList
	  * @param: @param bean
	  * @param: @throws Exception      
	  * @return: void
	  * @dateTime:2019年12月23日 下午2:42:42
	 */
	public void batchrepair(List<KqdsMachiningRepairType> asList, KqdsMachiningRepairWay bean) throws Exception{
		for (int i = 0; i < asList.size(); i++) {
			KqdsMachiningRepairType kqdsMachiningRepair = asList.get(i);
			if(kqdsMachiningRepair != null){
				kqdsMachiningRepair.setSeqId(YZUtility.getUUID());
				kqdsMachiningRepair.setToothId(bean.getSeqId());
				kqdsMachiningRepair.setCreateuser(bean.getCreateuser());
				kqdsMachiningRepair.setCreatetime(bean.getCreatetime());
				kqdsMachiningRepair.setOrganization(bean.getOrganization());
				dao.save(TableNameUtil.KQDS_MACHINING_REPAIRTYPE+".insert", kqdsMachiningRepair);
			}
		}
	}
	/**   
	  * @Title: getRecords   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年12月21日 上午10:42:05
	  */  
	@SuppressWarnings("unchecked")
	public List<JSONObject> getRecords(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MACHINING+".getRecordList", map);
	}
	/**   
	  * @Title: getParticularsBySeqId   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map
	  * @param: @return      
	  * @return: JSONObject
	 * @throws Exception 
	  * @dateTime:2019年12月21日 下午2:27:00
	  */ 
	@Transactional
	public JSONObject getParticularsBySeqId(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = (JSONObject)dao.findForObject(TableNameUtil.KQDS_MACHINING+".selectByPrimaryKey", map);
		JSONObject object = getToothByPid(json.getString("seqId"),json);
		return object;
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public JSONObject getToothByPid(String id,JSONObject json) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		List<JSONObject> KqdsMachining = new ArrayList<JSONObject>();
		map.put("seqId", id);
		List<JSONObject> findForList = (List<JSONObject>)dao.findForList(TableNameUtil.KQDS_MACHININGREPAIRWAY+".selectByParentKey", map);
		for (JSONObject jsonObject : findForList) {
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("seqId", jsonObject.getString("seqId"));
			List<JSONObject> list = (List<JSONObject>)dao.findForList(TableNameUtil.KQDS_MACHINING_REPAIRTYPE+".selectByParentKey", map2);
			jsonObject.put("repairProjectArr", list);
			KqdsMachining.add(jsonObject);
		}
		json.put("toothMapArr", KqdsMachining);
		return json;
	}
	/**   
	  * @Title: getGenre   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param id      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年12月29日 上午10:24:06
	  */  
	@SuppressWarnings("unchecked")
	public List<JSONObject> getGenre(String id) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("worksheetId", id);
		List<JSONObject> typeList = new ArrayList<JSONObject>();
		List <JSONObject> list = (List<JSONObject>)dao.findForList(TableNameUtil.KQDS_MACHININGREPAIRWAY+"selectByParentKey", map);
		for (JSONObject json : list) {
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("lcljId", json.getString("seqId"));
			typeList = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MACHINING_REPAIRTYPE+"selectByParentKey", map2);
		}
		return typeList;
	}
	/**   
	  * @Title: Update   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dp      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年12月29日 下午1:51:29
	  */  
	@Transactional
	public void updateStatus(KqdsMachining dp, HttpServletRequest request) throws Exception{
		// TODO Auto-generated method stub
		YZPerson person = SessionUtil.getLoginPerson(request);
		dao.update(TableNameUtil.KQDS_MACHINING+".updateByPrimaryKeySelective", dp);
		if(Integer.valueOf(dp.getStatus()) == 5){
			Map<String, String> map = new HashMap<String, String>();
			map.put("seqId", dp.getSeqId());
			JSONObject object = getParticularsBySeqId(map);
			KqdsMachining kMachining = new KqdsMachining();
			kMachining.setSeqId(YZUtility.getUUID());
			kMachining.setSystemNumber(YZUtility.getSystemID());
			kMachining.setOrderNumber(object.getString("ordernumber"));
			kMachining.setProcessUnit(object.getString("processunit"));
			kMachining.setDoctor(object.getString("doctor"));
			kMachining.setUserCode(object.getString("usercode"));
			kMachining.setUserName(object.getString("username"));
			kMachining.setSex(object.getString("sex"));
			kMachining.setAge(object.getString("age"));
			kMachining.setPhoneNumber(object.getString("phonenumber"));
			kMachining.setDeliverytime(object.getString("deliverytime"));
			kMachining.setChargeModelPerson(object.getString("chargemodelperson"));
			kMachining.setChargeModeltime(object.getString("chargemodeltime"));
			kMachining.setMaxillaryModel(object.getString("maxillarymodel"));
			kMachining.setOcclusioRecord(object.getString("occlusiorecord"));
			kMachining.setReplaceBody(object.getString("replacebody"));
			kMachining.setGingivalFormer(object.getString("gingivalformer"));
			kMachining.setMandibleModel(object.getString("mandiblemodel"));
			kMachining.setOldDentureModel(object.getString("olddenturemodel"));
			kMachining.setDrillBase(object.getString("drillbase"));
			kMachining.setLocator(object.getString("locator"));
			kMachining.setMaxillarySalver(object.getString("maxillarysalver"));
			kMachining.setWaxPattern(object.getString("waxpattern"));
			kMachining.setScrew(object.getString("screw"));
			kMachining.setJawFrame(object.getString("jawframe"));
			kMachining.setMandibleSalver(object.getString("mandiblesalver"));
			kMachining.setShiftLever(object.getString("shiftlever"));
			kMachining.setHumanGingival(object.getString("humangingival"));
			kMachining.setOmanJawBase(object.getString("omanjawbase"));
			kMachining.setOther(object.getString("other"));
			kMachining.setInnerCrown(object.getString("innercrown"));
			kMachining.setBiteJaw(object.getString("bitejaw"));
			kMachining.setSyntopy(object.getString("syntopy"));
			kMachining.setBridgeDesign(object.getString("bridgedesign"));
			kMachining.setNeckFlangeDesign(object.getString("neckflangedesign"));
			kMachining.setPlantFixed(object.getString("plantfixed"));
			kMachining.setStatus(object.getString("status"));
			kMachining.setOrganization(object.getString("organization"));
			kMachining.setCreateuser(person.getSeqId());
			kMachining.setCreatetime(object.getString("createtime"));
			dao.save(TableNameUtil.KQDS_MACHINING+".insertdata", kMachining);
			String jsonArray = object.getString("toothMapArr");
			List<JSONObject> list = com.alibaba.fastjson.JSONObject.parseArray(jsonArray, JSONObject.class);
			for (JSONObject json : list) {
				String string = json.getString("repairProjectArr");
				List<KqdsMachiningRepairType> repair = com.alibaba.fastjson.JSONObject.parseArray(string, KqdsMachiningRepairType.class);
				KqdsMachiningRepairWay kqdsMachiningRepairWay = new KqdsMachiningRepairWay();
				kqdsMachiningRepairWay.setSeqId(YZUtility.getUUID());
				kqdsMachiningRepairWay.setWorksheetId(kMachining.getSeqId());
				kqdsMachiningRepairWay.setUpleftTooth(json.getString("uplefttooth"));
				kqdsMachiningRepairWay.setUpperRightTooth(json.getString("upperrighttooth"));
				kqdsMachiningRepairWay.setLeftLowerTooth("leftlowertooth");
				kqdsMachiningRepairWay.setOrganization(json.getString("organization"));
				kqdsMachiningRepairWay.setLowRightTooth(json.getString("lowrighttooth"));
				kqdsMachiningRepairWay.setCreateuser(person.getSeqId());
				kqdsMachiningRepairWay.setCreatetime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
				dao.save(TableNameUtil.KQDS_MACHININGREPAIRWAY+".insertSelective", kqdsMachiningRepairWay);
				for (KqdsMachiningRepairType kqdsMachiningRepairType : repair) {
					kqdsMachiningRepairType.setSeqId(YZUtility.getUUID());
					kqdsMachiningRepairType.setToothId(kqdsMachiningRepairWay.getSeqId());
					dao.save(TableNameUtil.KQDS_MACHINING_REPAIRTYPE+".insertSelective", kqdsMachiningRepairType);
				}
			}
		}
		KqdsMachiningRecord kRecord = new KqdsMachiningRecord();
		kRecord.setSeqId(YZUtility.getUUID());
		kRecord.setWorksheetId(dp.getSeqId());
		kRecord.setSystemNumber(dp.getSystemNumber());
		kRecord.setUserCode(dp.getUserCode());
		kRecord.setUserName(dp.getUserName());
		kRecord.setStatus(dp.getStatus());
		kRecord.setCreateuser(person.getUserName());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		kRecord.setCreatetime(df.format(new Date()));
		insertRecord(kRecord);
	}
	
	@Transactional
	public Integer insertRecord(KqdsMachiningRecord kRecord) throws Exception{
		dao.save(TableNameUtil.KQDS_MACHININGRECORD+".insert", kRecord);
		return 1;
	}	
	/**   
	  * @Title: update   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param kqdsMachining
	  * @param: @param dp      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2020年1月1日 上午11:07:49
	  */
	@Transactional
	public void update(KqdsMachining dp) throws Exception {
		// TODO Auto-generated method stub
		Integer status = (Integer)dao.update(TableNameUtil.KQDS_MACHINING+".updateByPrimaryKeySelective", dp);
		System.out.println(dp.toString());
		if(YZUtility.isNotNullOrEmpty(String.valueOf(status))){
			String substring = dp.getToothMapArr();
			System.out.println(substring);
			List<KqdsMachiningRepairWay> asList = (List<KqdsMachiningRepairWay>)com.alibaba.fastjson.JSONArray.parseArray(substring, KqdsMachiningRepairWay.class);
			for (int i = 0; i < asList.size(); i++) {
				KqdsMachiningRepairWay bean = asList.get(i);
				if(bean != null){
					Map<String, String> map = new HashMap<String, String>();
					map.put("toothId", bean.getSeqId());
					Integer id = (Integer)dao.delete(TableNameUtil.KQDS_MACHINING_REPAIRTYPE+".deleteByPrimaryKey", map);
					if(!YZUtility.isNullorEmpty(String.valueOf(id))){
						String string = bean.getRepairProjectArr();
						System.out.println(string);
						List<KqdsMachiningRepairType> list = (List<KqdsMachiningRepairType>)com.alibaba.fastjson.JSONArray.parseArray(string, KqdsMachiningRepairType.class);
						batchrepair(list, bean);
					}
				}
			}
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("worksheetId", dp.getSeqId());
			dao.delete(TableNameUtil.KQDS_MACHININGREPAIRWAY+".deleteByPrimaryKey", map1);
			/*for (int i = 0; i < asList.size(); i++) {*/
				insertTooth(asList,dp);
			/*}*/
		}
	}
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectMachineByUsercode(String usercode) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("KQDS_MACHINING.selectMachineByUsercode", usercode);
		return list;
	}
	
	/**
	 * 试戴改变状态
	 * @param seqId
	 * @param tryInTime
	 * @throws Exception
	 */
	public void updateMachineBySeqId(String seqId, String tryInTime) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("seqId", seqId);
		dataMap.put("tryInTime", tryInTime);
		dataMap.put("status", "");
		dao.update("KQDS_MACHINING.updateMachineBySeqId", dataMap);
	}
	
	/**
	 * 戴走改变状态
	 * @param seqId
	 * @param takeAwayTime
	 * @throws Exception
	 */
	public void updateMachineOutBySeqId(String seqId, String takeAwayTime) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("seqId", seqId);
		dataMap.put("takeAwayTime", takeAwayTime);
		dataMap.put("status", "");
		dao.update("KQDS_MACHINING.updateMachineOutBySeqId", dataMap);
	}
	/**   
	  * @Title: delRecord   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2020年1月12日 上午10:02:30
	  */  
	public void delRecord(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		dao.delete(TableNameUtil.KQDS_MACHINING+".delRecord", map);
	}
	/**   
	  * @Title: getFlow   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param usercode
	  * @param: @return      
	  * @return: List<JSONObject>
	 * @throws Exception 
	  * @dateTime:2020年1月16日 下午3:44:04
	  */  
	@SuppressWarnings("unchecked")
	public List<JSONObject> getFlow(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>)dao.findForList(TableNameUtil.KQDS_MACHININGRECORD+".selectByPrimaryKey", map);

	}
}
