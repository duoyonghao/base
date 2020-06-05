package com.hudh.ykzz.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.lclj.StaticVar;
import com.hudh.ykzz.dao.IYkzzDrugsDao;
import com.hudh.ykzz.entity.YkzzDrugs;
import com.hudh.ykzz.entity.YkzzDrugsInDetail;
import com.hudh.ykzz.entity.YkzzDrugsOutDetail;
import com.hudh.ykzz.service.IYkzzDrugsService;
import com.hudh.ykzz.service.IYkzzManuService;
import com.hudh.ykzz.service.IYkzzTypeService;
import com.hudh.ykzz.service.IYzzManufacturersService;
import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.treatItem.KQDS_TreatItemLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.dict.YZDictCostLogic;
import com.kqds.service.sys.dict.YZDictLogic;

import java.util.ArrayList;
import java.util.HashMap;

import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.sys.DictUtil;

import net.sf.json.JSONObject;

@Service
public class YkzzDrugsServiceImpl extends BaseLogic implements IYkzzDrugsService {
	
	private static final String orderNumber_Prefix = "DRUG"; //编号前缀字母
	private static final int orderNumber_length = 6; //编号数字长度
	private static final String beginNumber = "000000"; //初始数字编号
	
	@Autowired
	private IYkzzDrugsDao iYkzzDrugsDao;
	
	@Autowired
	private YZDictCostLogic dictCostLogic;
	
	@Autowired
	private IYkzzTypeService ykzzTypeService;
	
	@Autowired
	private IYkzzManuService ykzzManuService;
	
	@Autowired
	private YZDictLogic yZDictLogic;
	
	@Autowired
	private IYzzManufacturersService iYzzManufacturersService;
	
	@Autowired
	private KQDS_TreatItemLogic logic;
	
	@Override
	@Transactional
	public int insertDrugsinfor(YkzzDrugs ykzzDrugs, HttpServletRequest request) throws Exception {
		String organization = ChainUtil.getCurrentOrganization(request);
		int drugs = iYkzzDrugsDao.insertDrugsInfor(ykzzDrugs);
		//数据同步到收费项目表当中
		KqdsTreatitem dp = new KqdsTreatitem();
		dp.setDiscount(ykzzDrugs.getDiscount());
		dp.setCreateuser(ykzzDrugs.getCreator());
		dp.setSeqId(ykzzDrugs.getId());//
		dp.setTreatitemno(ykzzDrugs.getOrder_no());
		dp.setTreatitemname(ykzzDrugs.getGood_name());
		BigDecimal retailPrice = ykzzDrugs.getDrug_retail_price();
		dp.setUnitprice(retailPrice.toString());
		dp.setUnit(ykzzDrugs.getCompany());
		dp.setXmjs(ykzzDrugs.getComments());
		dp.setUseflag(0);
		dp.setCreatetime(YZUtility.getCurDateTimeStr());
		if (organization.equals("HUDX")) {//分院添加药品在收费项目中
			
			String baseType = dictCostLogic.getBaseType("2",DictUtil.COSTITEM_SORT, ChainUtil.getCurrentOrganization(request));
			dp.setBasetype(baseType);//一级分类
			String nextType = dictCostLogic.getBaseType("2",baseType, ChainUtil.getCurrentOrganization(request));
			dp.setNexttype(nextType);//二级分类：根据字典表药品配置
		} else {
			dp.setBasetype(StaticVar.HUDH_BASE_TYPE);//一级分类
			dp.setNexttype(StaticVar.HUDH_NEXT_TYPE);//二级分类：根据字典表药品配置
		}
		dp.setStatus(StaticVar.HUDH_STATUS);//表示为药品
		dp.setOrganization(ykzzDrugs.getOrganization());
		dp.setIstsxm(0);
		dp.setIsyjjitem(0);
		dp.setIssplit(0);
		iYkzzDrugsDao.addTreatItemBack(dp);
		return drugs;
	}

	@Override
	public JSONObject selectDrugsByPrimaryId(String id) throws Exception {
		JSONObject json = iYkzzDrugsDao.selectDrugsByPrimaryId(id);
		return json;
	}

	@Override
	@Transactional
	public void deleteDrugsByPrimaryId(String id) throws Exception {
		iYkzzDrugsDao.deleteDrugsByPrimaryId(id);
		iYkzzDrugsDao.deleteTreamtDrugsByPrimaryId(id);
	}

	@Override
	@Transactional
	public void updateDrugsByPrimaryId(YkzzDrugs ykzzDrugs) throws Exception {
		iYkzzDrugsDao.updateDrugsByPrimaryId(ykzzDrugs);
		KqdsTreatitem dp = new KqdsTreatitem();
		dp.setDiscount(ykzzDrugs.getDiscount());
		dp.setCreateuser(ykzzDrugs.getCreator());
		dp.setSeqId(ykzzDrugs.getId());//
		dp.setTreatitemno(ykzzDrugs.getOrder_no());
		dp.setTreatitemname(ykzzDrugs.getGood_name());
		BigDecimal retailPrice = ykzzDrugs.getDrug_retail_price();
		dp.setUnitprice(retailPrice.toString());
		dp.setUnit(ykzzDrugs.getCompany());
		dp.setXmjs(ykzzDrugs.getComments());
		dp.setUseflag(0);
		dp.setCreatetime(YZUtility.getCurDateTimeStr());
		iYkzzDrugsDao.updateDeugsTreatitemInfor(dp);
	}

	@Override
	public List<JSONObject> selectAllDrugsInfor(Map<String, String> map) throws Exception {
		List<JSONObject> list = iYkzzDrugsDao.selectAllDrugsInfor(map);
		return list;
	}

	@Override
	public List<JSONObject> selectDrugsInforByConditionQuery(Map<String, String> map) throws Exception {
		List<JSONObject> json = iYkzzDrugsDao.selectDrugsInforByConditionQuery(map);
		return json;
	}

	@Override
	public List<YkzzDrugs> selectDrugsByIdStr(List<YkzzDrugsInDetail> list) throws Exception {
		// TODO Auto-generated method stub
		List<YkzzDrugs> joList  = new ArrayList<YkzzDrugs>();
		joList = iYkzzDrugsDao.selectDrugsByIdStr(list);
		return joList;
	}

	@Override
	public List<YkzzDrugs> selectDrugsOutByIdStr(List<YkzzDrugsOutDetail> list) throws Exception {
		// TODO Auto-generated method stub
		List<YkzzDrugs> joList  = new ArrayList<YkzzDrugs>();
		joList = iYkzzDrugsDao.selectDrugsOutByIdStr(list);
		return joList;
	}
	
	@Override
	public void batchUpdateDrugsByPrimaryId(List<YkzzDrugs> ykzzDrugs) throws Exception {
		// TODO Auto-generated method stub
		iYkzzDrugsDao.batchUpdateDrugsByPrimaryId(ykzzDrugs);
	}

	@Override
	public List<YkzzDrugs> selectDrugsByOrderNoStr(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		List<YkzzDrugs> joList  = new ArrayList<YkzzDrugs>();
		joList = iYkzzDrugsDao.selectDrugsByOrderNoStr(list);
		return joList;
	}
	
	//内部调用方法,更新收费项目表的药品信息
	public void updateDeugsTreatitemInfor(YkzzDrugs ykzzDrugs){
		KqdsTreatitem dp = new KqdsTreatitem();
		dp.setDiscount(ykzzDrugs.getDiscount());
		dp.setCreateuser(ykzzDrugs.getCreator());
		dp.setSeqId(YZUtility.getUUID());
		dp.setTreatitemno(ykzzDrugs.getOrder_no());
		dp.setTreatitemname(ykzzDrugs.getGood_name());
		BigDecimal retailPrice = ykzzDrugs.getDrug_retail_price();
		dp.setUnitprice(retailPrice.toString());
		dp.setUnit(ykzzDrugs.getCompany());
		dp.setXmjs(ykzzDrugs.getComments());
		dp.setUseflag(0);
		dp.setCreatetime(YZUtility.getCurDateTimeStr());
	}

	@Override
	@Transactional
	public void saveBatchInsert(List<List<String>> list, HttpServletRequest request) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		JSONObject result = new JSONObject();
		String organization = ChainUtil.getCurrentOrganization(request);
		//判断开单收费项目药品二级菜单是否存在
	    List<YZDict> listYZDict = yZDictLogic.getDrugsStoreByName("药品");
	    for (int i = 0; i < listYZDict.size(); i++) {
	    	YZDict y = listYZDict.get(i);
	    	if (y == null) {
	    		throw new Exception("药品分类级别不存在，请联系管理员！");
	    	}
		}
//      list中存的就是excel中的数据，可以根据excel中每一列的值转换成你所需要的值（从0开始），如：
		List<YkzzDrugs> listYkzzDrugs = new ArrayList<YkzzDrugs>();
		YkzzDrugs ykzzDrugs = null;
			for (int i = 1; i < list.size(); i++) {
			    ykzzDrugs = new YkzzDrugs();
				String good_name = list.get(i).get(0);//药品名称
				if (YZUtility.isNullorEmpty(good_name)) {
					throw new Exception("药品名称不能为空");
				}
				String drug_retail_price = list.get(i).get(1);//药品零售价
				if (YZUtility.isNullorEmpty(drug_retail_price)) {
					throw new Exception("药品零售价不能为空");
				}
				String discount = list.get(i).get(2);//折扣
				if (discount.equals("")) {
					ykzzDrugs.setDiscount("100.0");
				} else {
					ykzzDrugs.setDiscount(discount);
				}
				String chemistry_name = list.get(i).get(3);// 药品化学名
				/*if (YZUtility.isNullorEmpty(chemistry_name)) {
					throw new Exception("药品化学名不能为空");
				}*/
				String pharm_spec = list.get(i).get(4);//药品规格
				if (YZUtility.isNullorEmpty(pharm_spec)) {
					throw new Exception("药品规格不能为空");
				}
				String company = list.get(i).get(5);// 单位
				if (YZUtility.isNullorEmpty(company)) {
					throw new Exception("单位不能为空");
				}
				
				List<JSONObject> list1 = null;
				String drugs_typeone = list.get(i).get(6);//一级分类
				String drugs_typetwo = list.get(i).get(7);//二级分类
				JSONObject json = null;
				if (!YZUtility.isNullorEmpty(drugs_typeone)) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", "root");
					map.put("organization", organization);
					list1 = ykzzTypeService.findChildTypesByParentId(map);
					for (int j = 0; j < list1.size(); j++) {
						json = list1.get(j);
						Object l = json.get("typeName");//根据键获取值
						if (drugs_typeone.equals(json.get("typeName"))) {
							ykzzDrugs.setDrugs_typeone(json.get("id").toString());
						}
					}
					if (ykzzDrugs.getDrugs_typeone() == null) {
						throw new Exception("没有一级分类，请创建一级分类！");
					}
				} else {
					throw new Exception("一级分类不能为空");
				}
				
				if (!YZUtility.isNullorEmpty(drugs_typetwo)) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", json.get("id").toString());
					map.put("organization", organization);
					List<JSONObject> list2 = ykzzTypeService.findChildTypesByParentId(map);
					for (int k = 0; k < list2.size(); k++) {
						JSONObject jsonb = list2.get(k);
						Object ll = jsonb.get("type_name");//根据键获取值
						if (drugs_typetwo.equals(jsonb.get("type_name"))) {
							ykzzDrugs.setDrugs_typetwo(jsonb.get("id").toString());
						}
					}
					if (ykzzDrugs.getDrugs_typetwo() == null) {
						throw new Exception("没有二级分类，请创建二级分类！");
					}
				} else {
					throw new Exception("二级分类不能为空");
				}
				
				String packing_num = list.get(i).get(8);//包装数量
				if (YZUtility.isNullorEmpty(packing_num)) {
					throw new Exception("包装数量不能为空");
				}
				String company_min = list.get(i).get(9);//最小单位
				if (YZUtility.isNullorEmpty(company_min)) {
					throw new Exception("包装数量不能为空");
				}
				String content_coe = list.get(i).get(10);//含量系数
				if (YZUtility.isNullorEmpty(content_coe)) {
					throw new Exception("含量系数不能为空");
				}
				String content_unit = list.get(i).get(11);//含量单位
				if (YZUtility.isNullorEmpty(content_unit)) {
					throw new Exception("含量单位不能为空");
				}
				String sta_item = list.get(i).get(12); //统计大项目
				/*if (YZUtility.isNullorEmpty(sta_item)) {
					throw new Exception("统计大项目不能为空");
				}*/
				
				//根据药品国家编码验证药品是否已经存在
				String contry_code = list.get(i).get(13);// 国家编码
				if (YZUtility.isNullorEmpty(contry_code)) {
					throw new Exception("国家编码不能为空");
				}
				dataMap.put("organization", organization);
				dataMap.put("contrycode", contry_code);
				List<YkzzDrugs> listYkzz = iYkzzDrugsDao.findDeugsByContryCode(dataMap);
				for (int j = 0; j < listYkzz.size(); j++) {
					if (listYkzz != null) {
						throw new Exception("药品国家编码已存在，不能重复添加！");
					}
				}
				
				String pharm_class = list.get(i).get(14);//药理分类
				/*if (YZUtility.isNullorEmpty(pharm_class)) {
					throw new Exception("药理分类不能为空");
				}*/
				String pharm_dos = list.get(i).get(15);//药品剂型
				if (YZUtility.isNullorEmpty(pharm_dos)) {
					throw new Exception("药品剂型不能为空");
				}
				String ant_gra = list.get(i).get(16);//抗生素级别
				/*if (YZUtility.isNullorEmpty(ant_gra)) {
					throw new Exception("抗生素级别不能为空");
				}*/
				String psy_drugs = list.get(i).get(17);//精神药品
				/*if (YZUtility.isNullorEmpty(psy_drugs)) {
					throw new Exception("精神药品不能为空");
				}*/
				String minnums = list.get(i).get(18);//下限值
				/*if (YZUtility.isNullorEmpty(minnums)) {
					throw new Exception("下限值不能为空");
				}*/
				String mingj = list.get(i).get(19);//下限报警提醒
				/*if (YZUtility.isNullorEmpty(mingj)) {
					throw new Exception("下限报警提醒不能为空");
				}*/
				String maxnums = list.get(i).get(20);//上限值
				/*if (YZUtility.isNullorEmpty(maxnums)) {
					throw new Exception("上限值不能为空");
				}*/
				String maxgj = list.get(i).get(21);//上限报警提醒
				/*if (YZUtility.isNullorEmpty(maxgj)) {
					throw new Exception("上限报警提醒不能为空");
				}*/
				String drug_identify = list.get(i).get(22);//药品标识
				/*if (YZUtility.isNullorEmpty(drug_identify)) {
					throw new Exception("药品标识不能为空");
				}*/
				String comments = list.get(i).get(23);//药品介绍
				/*if (YZUtility.isNullorEmpty(comments)) {
					throw new Exception("药品介绍不能为空");
				}*/
				
				String manu_id = list.get(i).get(24);//厂家
				if (!YZUtility.isNullorEmpty(manu_id)) {
					List<JSONObject> listManu = ykzzManuService.findAllManu(organization);
					for (int j = 0; j < listManu.size(); j++) {
						JSONObject jsonM = listManu.get(j);
						Object m = jsonM.get("manu_name");
						if (manu_id.equals(m)) {
							ykzzDrugs.setManu_id(jsonM.get("id").toString());
						}
					}
					if (ykzzDrugs.getManu_id() == null) {
						throw new Exception("供应商不存在！");
					}
				} else {
					throw new Exception("厂家不能为空");
				}
				
				String packing_unit = list.get(i).get(25);//包装单位
				if (YZUtility.isNullorEmpty(packing_unit)) {
					throw new Exception("包装单位不能为空");
				}
				String drugs_type = list.get(i).get(26);//包装单位
				
				String manufac_id = list.get(i).get(27);//生产厂家
				if (!YZUtility.isNullorEmpty(manufac_id)) {
					List<JSONObject> listManufac = iYzzManufacturersService.findAllManufacturers(organization);
					for (int j = 0; j < listManufac.size(); j++) {
						JSONObject jsonM = listManufac.get(j);
						Object m = jsonM.get("manufacturers_name");
						if (manufac_id.equals(m)) {
							ykzzDrugs.setManufac_id(jsonM.get("id").toString());
						}
					}
					if (ykzzDrugs.getManufac_id() == null) {
						throw new Exception("生产商不存在！");
					}
				} else {
					throw new Exception("生产商不能为空");
				}
				ykzzDrugs.setCreatetime(YZUtility.getCurDateTimeStr());
				ykzzDrugs.setId(YZUtility.getUUID());
			    YZPerson person = SessionUtil.getLoginPerson(request);
				ykzzDrugs.setCreator(person.getSeqId());
//				String organization = ChainUtil.getCurrentOrganization(request); // 获取当前所在门诊
				ykzzDrugs.setOrganization(organization);
				ykzzDrugs.setGood_name(good_name);// 每一行的第一个单元格
				ykzzDrugs.setComments(comments);
				ykzzDrugs.setPacking_unit(packing_unit);
				ykzzDrugs.setMaxgj(maxgj);
				ykzzDrugs.setMingj(mingj);
				ykzzDrugs.setMinnums(minnums);
				ykzzDrugs.setMaxnums(maxnums);
				ykzzDrugs.setGood_name(good_name);
				ykzzDrugs.setCompany(company);
//				ykzzDrugs.setDiscount(discount);
				ykzzDrugs.setPacking_num(packing_num);
				ykzzDrugs.setCompany_min(company_min);
				ykzzDrugs.setContent_coe(content_coe);
				ykzzDrugs.setContent_unit(content_unit);
				ykzzDrugs.setContry_code(contry_code);
				ykzzDrugs.setSta_item(sta_item);
				ykzzDrugs.setPharm_class(pharm_class);
				ykzzDrugs.setPharm_dos(pharm_dos);
				ykzzDrugs.setPharm_spec(pharm_spec);
				ykzzDrugs.setAnt_gra(ant_gra);
				ykzzDrugs.setPsy_drugs(psy_drugs);
				ykzzDrugs.setDrug_identify(drug_identify);
				ykzzDrugs.setChemistry_name(chemistry_name);
				ykzzDrugs.setDrug_total_num(0);
				ykzzDrugs.setDrugs_type(drugs_type);
				
				String drgs_total_money = "0.000";
				BigDecimal bd1 = new BigDecimal(drgs_total_money);
				ykzzDrugs.setDrgs_total_money(bd1);
				ykzzDrugs.setNuit_price(bd1);
				
				BigDecimal bd = new BigDecimal(drug_retail_price);
				bd = bd.setScale(3, BigDecimal.ROUND_HALF_UP);
				ykzzDrugs.setDrug_retail_price(bd);
				
				String orderNo = getCreateOederNo(i);	
				ykzzDrugs.setOrder_no(orderNo);
				listYkzzDrugs.add(ykzzDrugs);
			}
		iYkzzDrugsDao.importDrugsInfro(listYkzzDrugs);
		List<KqdsTreatitem> kqdsTreatList = dxzh(listYkzzDrugs);
		iYkzzDrugsDao.saveBatchInsertTreatItemBack(kqdsTreatList);
//		return result;
	}

	@Override
	public List<YkzzDrugs> getAllDrugsInfor(Map<String, String> map) throws Exception {
		List<YkzzDrugs> list = iYkzzDrugsDao.getAllDrugsInfor(map);
		return list;
	}
	
	/**
	 * 将药品对象转成收费项目对象
	 * @param listYkzzDrugs
	 * @return
	 */
	private List<KqdsTreatitem> dxzh(List<YkzzDrugs> listYkzzDrugs){
		List<KqdsTreatitem>  kqdsTreatList = new ArrayList<>();
		KqdsTreatitem kqdsTreatitem = null;
		for(YkzzDrugs ykzzDrugs : listYkzzDrugs) {
			kqdsTreatitem = new KqdsTreatitem();
			kqdsTreatitem.setDiscount(ykzzDrugs.getDiscount());
			kqdsTreatitem.setCreateuser(ykzzDrugs.getCreator());
			kqdsTreatitem.setSeqId(ykzzDrugs.getId());//
			kqdsTreatitem.setTreatitemno(ykzzDrugs.getOrder_no());
			kqdsTreatitem.setTreatitemname(ykzzDrugs.getGood_name());
			BigDecimal retailPrice = ykzzDrugs.getDrug_retail_price();
			kqdsTreatitem.setUnitprice(retailPrice.toString());
			kqdsTreatitem.setUnit(ykzzDrugs.getCompany());
			kqdsTreatitem.setXmjs(ykzzDrugs.getComments());
			kqdsTreatitem.setUseflag(0);
			kqdsTreatitem.setCreatetime(ykzzDrugs.getCreatetime());
			kqdsTreatitem.setBasetype(StaticVar.HUDH_BASE_TYPE);//一级分类
			kqdsTreatitem.setNexttype(StaticVar.HUDH_NEXT_TYPE);//二级分类
			kqdsTreatitem.setStatus(StaticVar.HUDH_STATUS);//1：标识为药品
			kqdsTreatitem.setOrganization(ykzzDrugs.getOrganization());
			kqdsTreatitem.setIstsxm(0);
			kqdsTreatitem.setIsyjjitem(0);
			kqdsTreatitem.setIssplit(0);
			kqdsTreatList.add(kqdsTreatitem);
		}
		return kqdsTreatList;
	}
	
	/**
	 * 生成药品号
	 * @param i
	 * @return
	 * @throws Exception
	 */
	private String getCreateOederNo(int i) throws Exception {
		int num = iYkzzDrugsDao.findOrderNumberCount();
		if (num == 0) {
			return orderNumber_Prefix + String.format("%0"+orderNumber_length+"d", Integer.parseInt(beginNumber) + i);
		} else {
			return orderNumber_Prefix + String.format("%0"+orderNumber_length+"d", Integer.parseInt(beginNumber) + num + i);
		}
	}

	@Override
	public List<YkzzDrugs> findDeugsByContryCode(String contrycode, String organization) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("contrycode", contrycode);
		dataMap.put("organization", organization);
		List<YkzzDrugs> list = iYkzzDrugsDao.findDeugsByContryCode(dataMap);
		return list;
	}

	@Override
	@Transactional
	public void forbiddenDrugs(String id) throws Exception {
		// TODO Auto-generated method stub
		iYkzzDrugsDao.forbiddenDrugs(id);
		logic.changeDrugsUserflag(id);
	}

	@Override
	@Transactional
	public void recoverDrugs(String id) throws Exception {
		// TODO Auto-generated method stub
		iYkzzDrugsDao.recoverDrugs(id);
		logic.recoverDrugsUserflag(id);
	}

}
