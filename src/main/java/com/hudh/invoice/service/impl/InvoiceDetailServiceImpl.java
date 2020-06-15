package com.hudh.invoice.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hudh.dzbl.dao.DzblDetailDao;
import com.hudh.invoice.dao.InvoiceDetailDao;
import com.hudh.invoice.entity.InvoiceDetail;
import com.hudh.invoice.service.InvoiceDetailService;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.chufang.KQDS_ChuFangLogic;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.jzmdType.KQDS_JzqkLogic;
import com.kqds.service.base.paycost.Kqds_PayCostLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.other.CacheUtil;

import net.sf.json.JSONObject;
@Service
public class InvoiceDetailServiceImpl implements InvoiceDetailService {
	@Autowired
	private KQDS_JzqkLogic jzqkLogic;
	@Autowired
	private Kqds_PayCostLogic payCostLogic;
	@Autowired
	private KQDS_UserDocumentLogic userLogic;
	@Autowired
	private KQDS_REGLogic regLogic;
	@Autowired
	private KQDS_CostOrderLogic costOrderLogic;
	@Autowired
	private KQDS_CostOrder_DetailLogic costOrderDetailLogic;
	@Autowired
	private KQDS_ChuFangLogic chuFangLogic;
	@Autowired
	private DzblDetailDao dzblDetailDao;
	@Autowired
	private InvoiceDetailDao invoiceDetailDao;
	@Autowired
	private DaoSupport dao;
	/**
	 * 保存数据
	 */
	@Override
	public int batchSaveInvoiceDetail(List<InvoiceDetail> list,String usercode,YZPerson person,String organization) throws Exception {
		for (InvoiceDetail invoiceDetail : list) {
			
			invoiceDetail.setCreatetime(YZUtility.getCurDateTimeStr("yyyy-MM-dd HH:mm:ss"));
			invoiceDetail.setSeqId(YZUtility.getUUID());
			invoiceDetail.setCreateuser(person.getCreateuser());
			invoiceDetail.setStatus(0);
			invoiceDetail.setUsercode(usercode);
			invoiceDetail.setDishonour(0);
			invoiceDetail.setOrganization(organization);
		}
		invoiceDetailDao.batchSaveInvoiceDetail(list);
		int i=(int) dao.update(TableNameUtil.KQDS_USERDOCUMENT+ ".updateUserdocumentInvoiceByUsercode",usercode);
		String usercodes="\'"+usercode+"\'";
		//查询患者的所有建档信息
		List<JSONObject> list1 = userLogic.findKqdsUserdocumentByUsercodes(usercodes);
		//查询患者的所有挂号信息
		List<JSONObject> list2=regLogic.findKqdsRegByUsercodes(usercodes);
		//查询患者的所有缴费信息
		List<JSONObject> list3=costOrderLogic.findCostOrderByUsercodes(usercodes);
		//查找患者的所有缴费明细信息
		List<JSONObject> list4=costOrderDetailLogic.findCostOrderDetailByUsercodes(usercodes);
		//查找患者的所有退单信息
		List<JSONObject> list5=costOrderLogic.findCostOrderTuidanByUsercodes(usercodes);
		//查找患者的所有退单明细
		List<JSONObject> list6=costOrderDetailLogic.findCostOrderDetailTuidanByUsercodes(usercodes);
		//查找患者的所有处方
		List<JSONObject> list7=chuFangLogic.findChuFangByUsercodes(usercodes);
		//查找患者的所有处方明细
		List<JSONObject> list8=chuFangLogic.findChuFangDetailByUsercodes(usercodes);
		//查找患者的所有费用信息
		List<JSONObject> list9=payCostLogic.findPayCostByUsercodes(usercodes);
		//查找患者的所有病历信息
		List<JSONObject> list10=dzblDetailDao.findDzblDetailByUsercodes(usercodes);
		//查询患者所有的发票信息
		List<JSONObject> list11=invoiceDetailDao.selectInvoiceDetailByUsercode(usercode);
		//查找患者医生的所有就诊情况
		List<JSONObject> list12=jzqkLogic.selectJzqkByUsercodes(usercodes);
		CacheUtil.openCache();
//		CacheUtil.queryVisitArticleStatistics(0);
		if(list1!=null&&list1.size()>0){
			CacheUtil.addZSet("KqdsUserdocument:key", list1.get(0).getString("usercode"),new Date().getTime());
			Map<String,List<JSONObject>> key1=new HashMap<String,List<JSONObject>>();
			key1.put(list1.get(0).getString("usercode"), list1);
			CacheUtil.setMap("KqdsUserdocument:value", key1);
		}
		
		if(list2!=null&&list2.size()>0){
			CacheUtil.addZSet("KqdsReg:key", list2.get(0).get("usercode"),new Date().getTime());
			Map<String,List<JSONObject>> key2=new HashMap<String,List<JSONObject>>();
			key2.put(list2.get(0).getString("usercode"), list2);
			CacheUtil.setMap("KqdsReg:value", key2);
			
		}
		
		if(list3!=null&&list3.size()>0){
			CacheUtil.addZSet("KqdsCostOrder:key", list3.get(0).get("usercode"),new Date().getTime());
			Map<String,List<JSONObject>> key3=new HashMap<String,List<JSONObject>>();
			key3.put(list3.get(0).getString("usercode"), list3);
			CacheUtil.setMap("KqdsCostOrder:value", key3);
		}
		
		if(list4!=null&&list4.size()>0){
			CacheUtil.addZSet("KqdsCostOrderDetail:key", list4.get(0).get("usercode"),new Date().getTime());
			Map<String,List<JSONObject>> key4=new HashMap<String,List<JSONObject>>();
			key4.put(list4.get(0).getString("usercode"), list4);
			CacheUtil.setMap("KqdsCostOrderDetail:value", key4);
		}
		
		if(list5!=null&&list5.size()>0){
			CacheUtil.addZSet("KqdsCostOrderTuidan:key", list5.get(0).get("usercode"),new Date().getTime());
			Map<String,List<JSONObject>> key5=new HashMap<String,List<JSONObject>>();
			key5.put(list5.get(0).getString("usercode"), list5);
			CacheUtil.setMap("KqdsCostOrderTuidan:value", key5);
		}
		
		if(list6!=null&&list6.size()>0){
			CacheUtil.addZSet("KqdsCostOrderDetailTuidan:key", list6.get(0).get("usercode"),new Date().getTime());
			Map<String,List<JSONObject>> key6=new HashMap<String,List<JSONObject>>();
			key6.put(list6.get(0).getString("usercode"), list6);
			CacheUtil.setMap("KqdsCostOrderDetailTuidan:value", key6);
			
		}
		if(list7!=null&&list7.size()>0){
			CacheUtil.addZSet("KqdsChuFang:key", list7.get(0).get("usercode"),new Date().getTime());
			Map<String,List<JSONObject>> key7=new HashMap<String,List<JSONObject>>();
			key7.put(list7.get(0).getString("usercode"), list7);
			CacheUtil.setMap("KqdsChuFang:value", key7);
			
		}
		
		if(list8!=null&&list8.size()>0){
			CacheUtil.addZSet("KqdsChuFangDetail:key", list8.get(0).get("usercode"),new Date().getTime());
			Map<String,List<JSONObject>> key8=new HashMap<String,List<JSONObject>>();
			key8.put(list8.get(0).getString("usercode"), list8);
			CacheUtil.setMap("KqdsChuFangDetail:value", key8);
			
		}
		
		if(list9!=null&&list9.size()>0){
			CacheUtil.addZSet("KqdsPayCost:key", list9.get(0).get("usercode"),new Date().getTime());
			Map<String,List<JSONObject>> key9=new HashMap<String,List<JSONObject>>();
			key9.put(list9.get(0).getString("usercode"), list9);
			CacheUtil.setMap("KqdsPayCost:value", key9);
		}
		
		if(list10!=null&&list10.size()>0){
			CacheUtil.addZSet("HudhDzblDetail:key", list10.get(0).get("blcode"),new Date().getTime());
			Map<String,List<JSONObject>> key10=new HashMap<String,List<JSONObject>>();
			key10.put(list10.get(0).getString("blcode"), list10);
			CacheUtil.setMap("HudhDzblDetail:value", key10);
		}
		
		if(list11!=null&&list11.size()>0){
			CacheUtil.addZSet("HudhInvoiceDetail:key", list11.get(0).get("usercode"),new Date().getTime());
			Map<String,List<JSONObject>> key11=new HashMap<String,List<JSONObject>>();
			key11.put(list11.get(0).getString("usercode"), list11);
			CacheUtil.setMap("HudhInvoiceDetail:value", key11);
		}
		if(list12!=null&&list12.size()>0){
			CacheUtil.addZSet("KqdsJzqk:key", list12.get(0).get("usercode"),new Date().getTime());
			Map<String,List<JSONObject>> key12=new HashMap<String,List<JSONObject>>();
			key12.put(list12.get(0).getString("usercode"), list12);
			CacheUtil.setMap("KqdsJzqk:value", key12);
		}
		CacheUtil.close();
		return i;
	}
	/**
	 * 修改数据
	 */
	@Override
	public void batchupdateInvoiceDetail(List<InvoiceDetail> list,String usercode,YZPerson person,String organization) throws Exception {
		List<InvoiceDetail> list1 = new ArrayList<InvoiceDetail>();
		for (InvoiceDetail invoiceDetail : list) {
			if(invoiceDetail.getSeqId()!=null&&!invoiceDetail.getSeqId().equals("")){
				invoiceDetail.setUpdatetime(YZUtility.getCurDateTimeStr("yyyy-MM-dd HH:mm:ss"));
				invoiceDetail.setUpdateuser(person.getCreateuser());
			}else{
				invoiceDetail.setCreatetime(YZUtility.getCurDateTimeStr("yyyy-MM-dd HH:mm:ss"));
				invoiceDetail.setSeqId(YZUtility.getUUID());
				invoiceDetail.setCreateuser(person.getCreateuser());
				invoiceDetail.setStatus(0);
				invoiceDetail.setUsercode(usercode);
				invoiceDetail.setDishonour(0);
				invoiceDetail.setOrganization(organization);
				list1.add(invoiceDetail);
			}
		}
		invoiceDetailDao.batchupdateInvoiceDetail(list);
		if(list1.size()>0&&list1!=null){
			invoiceDetailDao.batchSaveInvoiceDetail(list1);
		}
		//查询患者所有的发票信息
		List<JSONObject> list11=invoiceDetailDao.selectInvoiceDetailByUsercode(usercode);
		CacheUtil.openCache();
//		CacheUtil.queryVisitArticleStatistics(0);
		if(list11!=null&&list11.size()>0){
			CacheUtil.addZSet("HudhInvoiceDetail:key", list11.get(0).get("usercode"),new Date().getTime());
			Map<String,List<JSONObject>> key11=new HashMap<String,List<JSONObject>>();
			key11.put(list11.get(0).getString("usercode"), list11);
			CacheUtil.setMap("HudhInvoiceDetail:value", key11);
		}
		CacheUtil.close();

	}
	/**
	 * 修改数据状态
	 */
	@Override
	public void batchupdateInvoiceDetailStatus(List<InvoiceDetail> list,HttpServletRequest request) throws Exception {
		YZPerson person = SessionUtil.getLoginPerson(request);
		for (InvoiceDetail invoiceDetail : list) {
			invoiceDetail.setUpdatetime(YZUtility.getCurDateTimeStr("yyyy-MM-dd HH:mm:ss"));
			invoiceDetail.setUpdateuser(person.getCreateuser());
			invoiceDetail.setStatus(1);
		}
		invoiceDetailDao.batchupdateInvoiceDetail(list);

	}
	/**
	 * 根据usercode查询
	 */
	@Override
	public List<JSONObject> selectInvoiceDetailByUsercode(String usercode) throws Exception {
		List<JSONObject> list=invoiceDetailDao.selectInvoiceDetailByUsercode(usercode);
		return list;
	}
	@Override
	public int updateDishonourInvoiceDetail(InvoiceDetail invoiceDetail, String usercode, YZPerson person)
			throws Exception {
		//修改原有的数据
		//查询患者所有的发票信息
		Map<String,String> map = new HashMap<String,String>();
		map.put("usercode", usercode);
		map.put("dishonour", "0");
		List<JSONObject> InvoiceDetail = invoiceDetailDao.findInvoiceDetailByuserCodeAndstatus(map);
		//新增一条数据
		invoiceDetail.setDishonour(1);
		int i =invoiceDetailDao.updateDishonourInvoiceDetail(invoiceDetail);
		List<JSONObject> list11=invoiceDetailDao.selectInvoiceDetailByUsercode(usercode);
		List<JSONObject> costOrder = costOrderLogic.findCostByUsercode(usercode);
		BigDecimal bd = new BigDecimal(0.00);
		for(int y=0;y<costOrder.size();y++){
		 bd = bd.add(new BigDecimal(costOrder.get(y).getString("shouldmoney")));
		}
		CacheUtil.openCache();
//		CacheUtil.queryVisitArticleStatistics(0);
		if(InvoiceDetail.size()==1){	
			if(bd.compareTo(invoiceDetail.getInvoiceValue())>-1){
				CacheUtil.addZSet("Detail:key", list11.get(0).get("usercode"),new Date().getTime());
				InvoiceDetail detail = new InvoiceDetail();
				detail.setUsercode(list11.get(0).getString("usercode"));
				detail.setStatus(0);
				JSONObject json = JSONObject.fromObject(detail);
				Map<String,JSONObject> key11=new HashMap<String,JSONObject>();
				key11.put(usercode, json);
				CacheUtil.setMap("Detail:value", key11);
			}
		}
		if(list11!=null&&list11.size()>0){
		CacheUtil.addZSet("HudhInvoiceDetail:key", list11.get(0).get("usercode"),new Date().getTime());
		Map<String,List<JSONObject>> key11=new HashMap<String,List<JSONObject>>();
		key11.put(list11.get(0).getString("usercode"), list11);
		CacheUtil.setMap("HudhInvoiceDetail:value", key11);
		}
		CacheUtil.close();
		return i;
	}
	@Override
	public JSONObject selectInvoiceDetailValueByUsercode(String usercode) throws Exception {
		JSONObject json=invoiceDetailDao.selectInvoiceDetailValueByUsercode(usercode);
		return json;
	}
	@Override
	public JSONObject selectInvoiceDetailValueByUsercodeAndDishonour(String usercode) throws Exception {
		JSONObject json=invoiceDetailDao.selectInvoiceDetailValueByUsercodeAndDishonour(usercode);
		return json;
	}
	/**   
	  * <p>Title: updateDishonourInvoiceDetailAll</p>   
	  * <p>Description: </p>   
	  * @param usercode
	  * @param person   
	 * @throws Exception 
	  * @see InvoiceDetailService#updateDishonourInvoiceDetailAll(String, YZPerson)
	  */  
	@Override
	public void updateDishonourInvoiceDetailAll(String usercode, YZPerson person) throws Exception {
		// TODO Auto-generated method stub
		InvoiceDetail invoiceDetail = new InvoiceDetail();
		invoiceDetail.setUsercode(usercode);
		invoiceDetail.setUpdateuser(person.getSeqId());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String updatetime = simpleDateFormat.format(new Date());
		invoiceDetail.setUpdatetime(updatetime);
		invoiceDetailDao.updateDishonourInvoiceDetailAll(invoiceDetail);
		CacheUtil.openCache();
//		CacheUtil.queryVisitArticleStatistics(0);
		CacheUtil.addZSet("DetailAll:key", usercode,new Date().getTime());
		InvoiceDetail detail = new InvoiceDetail();
		detail.setUsercode(usercode);
		detail.setStatus(0);
		JSONObject json = JSONObject.fromObject(detail);
		Map<String,JSONObject> key11=new HashMap<String,JSONObject>();
		key11.put(usercode, json);
		CacheUtil.setMap("DetailAll:value", key11);
		CacheUtil.close();
	}
	@Override
	@Transactional
	public void saveBatchInsert(List<List<String>> list, HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request);
//      list中存的就是excel中的数据，可以根据excel中每一列的值转换成你所需要的值（从0开始），如：
		long time=new Date().getTime();
		List<InvoiceDetail> listInvoiceDetail = new ArrayList<InvoiceDetail>();
		InvoiceDetail invoiceDetail1 = null;
			for (int i = 1; i < list.size(); i++) {
				invoiceDetail1= new InvoiceDetail();
				invoiceDetail1.setSeqId(YZUtility.getUUID());
				invoiceDetail1.setCreatetime(YZUtility.getCurDateTimeStr("yyyy-MM-dd HH:mm:ss"));
				invoiceDetail1.setCreateuser(person.getCreateuser());
				invoiceDetail1.setStatus(0);
				invoiceDetail1.setDishonour(0);
				invoiceDetail1.setOrganization(organization);
				String dutyParayraph = list.get(i).get(2);//发票号码
				if (YZUtility.isNullorEmpty(dutyParayraph)) {
					throw new Exception("发票号码不能为空");
				}
				Double dou_obj = new Double(Double.parseDouble(dutyParayraph));
		        NumberFormat nf = NumberFormat.getInstance();
		        nf.setGroupingUsed(false);
		        dutyParayraph = nf.format(dou_obj);
		        invoiceDetail1.setDutyParayraph(dutyParayraph);
		        
				String usercode = list.get(i).get(3);//患者编号 必填
				if (YZUtility.isNullorEmpty(usercode)) {
					throw new Exception("患者编号不能为空");
				}
				invoiceDetail1.setUsercode(usercode);
				
				String drawer = list.get(i).get(4);//购方名称
				invoiceDetail1.setDrawer(drawer);
				
				String taxpayerNumber = list.get(i).get(5);//购方税号
				if(taxpayerNumber.equals("0.0")){
					taxpayerNumber="";
				}else if(taxpayerNumber.contains(".")){
					Double dou = new Double(Double.parseDouble(taxpayerNumber));
			        NumberFormat nf1 = NumberFormat.getInstance();
			        nf1.setGroupingUsed(false);
			        taxpayerNumber = nf1.format(dou);
				}
				invoiceDetail1.setTaxpayerNumber(taxpayerNumber);
				
				String invoiceTime = list.get(i).get(6);//开票时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = HSSFDateUtil.getJavaDate(Double.parseDouble(invoiceTime));
				invoiceTime = sdf.format(date);
				if (YZUtility.isNullorEmpty(invoiceTime)) {
					throw new Exception("开票日期不能为空");
				}
				invoiceDetail1.setInvoiceTime(invoiceTime);
				
				String invoiceValue = list.get(i).get(8);// 合计金额
				if (YZUtility.isNullorEmpty(invoiceValue)) {
					throw new Exception("合计金额不能为空");
				}
				invoiceDetail1.setInvoiceValue(new BigDecimal(invoiceValue));
				
				String invoiceDetail = list.get(i).get(9);//主要商品名称
				invoiceDetail1.setInvoiceDetail(invoiceDetail);
				listInvoiceDetail.add(invoiceDetail1);
			}
			invoiceDetailDao.batchSaveInvoiceDetail(listInvoiceDetail);
			for (InvoiceDetail invoiceDetail : listInvoiceDetail) {
				int i=(int) dao.update(TableNameUtil.KQDS_USERDOCUMENT+ ".updateUserdocumentInvoiceByUsercode",invoiceDetail.getUsercode());
				String usercodes="\'"+invoiceDetail.getUsercode()+"\'";
				//查询患者的所有建档信息
				List<JSONObject> list1 = userLogic.findKqdsUserdocumentByUsercodes(usercodes);
				//查询患者的所有挂号信息
				List<JSONObject> list2=regLogic.findKqdsRegByUsercodes(usercodes);
				//查询患者的所有缴费信息
				List<JSONObject> list3=costOrderLogic.findCostOrderByUsercodes(usercodes);
				//查找患者的所有缴费明细信息
				List<JSONObject> list4=costOrderDetailLogic.findCostOrderDetailByUsercodes(usercodes);
				//查找患者的所有退单信息
				List<JSONObject> list5=costOrderLogic.findCostOrderTuidanByUsercodes(usercodes);
				//查找患者的所有退单明细
				List<JSONObject> list6=costOrderDetailLogic.findCostOrderDetailTuidanByUsercodes(usercodes);
				//查找患者的所有处方
				List<JSONObject> list7=chuFangLogic.findChuFangByUsercodes(usercodes);
				//查找患者的所有处方明细
				List<JSONObject> list8=chuFangLogic.findChuFangDetailByUsercodes(usercodes);
				//查找患者的所有费用信息
				List<JSONObject> list9=payCostLogic.findPayCostByUsercodes(usercodes);
				//查找患者的所有病历信息
				List<JSONObject> list10=dzblDetailDao.findDzblDetailByUsercodes(usercodes);
				//查询患者所有的发票信息
				List<JSONObject> list11=invoiceDetailDao.selectInvoiceDetailByUsercode(invoiceDetail.getUsercode());
				//查找患者医生的所有就诊情况
				List<JSONObject> list12=jzqkLogic.selectJzqkByUsercodes(usercodes);
				CacheUtil.openCache();
//				CacheUtil.queryVisitArticleStatistics(0);
				if(list1!=null&&list1.size()>0){
					CacheUtil.addZSet("KqdsUserdocument:key", list1.get(0).getString("usercode"),new Date().getTime());
					Map<String,List<JSONObject>> key1=new HashMap<String,List<JSONObject>>();
					key1.put(list1.get(0).getString("usercode"), list1);
					CacheUtil.setMap("KqdsUserdocument:value", key1);
				}
				
				if(list2!=null&&list2.size()>0){
					CacheUtil.addZSet("KqdsReg:key", list2.get(0).get("usercode"),new Date().getTime());
					Map<String,List<JSONObject>> key2=new HashMap<String,List<JSONObject>>();
					key2.put(list2.get(0).getString("usercode"), list2);
					CacheUtil.setMap("KqdsReg:value", key2);
					
				}
				
				if(list3!=null&&list3.size()>0){
					CacheUtil.addZSet("KqdsCostOrder:key", list3.get(0).get("usercode"),new Date().getTime());
					Map<String,List<JSONObject>> key3=new HashMap<String,List<JSONObject>>();
					key3.put(list3.get(0).getString("usercode"), list3);
					CacheUtil.setMap("KqdsCostOrder:value", key3);
				}
				
				if(list4!=null&&list4.size()>0){
					CacheUtil.addZSet("KqdsCostOrderDetail:key", list4.get(0).get("usercode"),new Date().getTime());
					Map<String,List<JSONObject>> key4=new HashMap<String,List<JSONObject>>();
					key4.put(list4.get(0).getString("usercode"), list4);
					CacheUtil.setMap("KqdsCostOrderDetail:value", key4);
				}
				
				if(list5!=null&&list5.size()>0){
					CacheUtil.addZSet("KqdsCostOrderTuidan:key", list5.get(0).get("usercode"),new Date().getTime());
					Map<String,List<JSONObject>> key5=new HashMap<String,List<JSONObject>>();
					key5.put(list5.get(0).getString("usercode"), list5);
					CacheUtil.setMap("KqdsCostOrderTuidan:value", key5);
				}
				
				if(list6!=null&&list6.size()>0){
					CacheUtil.addZSet("KqdsCostOrderDetailTuidan:key", list6.get(0).get("usercode"),new Date().getTime());
					Map<String,List<JSONObject>> key6=new HashMap<String,List<JSONObject>>();
					key6.put(list6.get(0).getString("usercode"), list6);
					CacheUtil.setMap("KqdsCostOrderDetailTuidan:value", key6);
					
				}
				if(list7!=null&&list7.size()>0){
					CacheUtil.addZSet("KqdsChuFang:key", list7.get(0).get("usercode"),new Date().getTime());
					Map<String,List<JSONObject>> key7=new HashMap<String,List<JSONObject>>();
					key7.put(list7.get(0).getString("usercode"), list7);
					CacheUtil.setMap("KqdsChuFang:value", key7);
					
				}
				
				if(list8!=null&&list8.size()>0){
					CacheUtil.addZSet("KqdsChuFangDetail:key", list8.get(0).get("usercode"),new Date().getTime());
					Map<String,List<JSONObject>> key8=new HashMap<String,List<JSONObject>>();
					key8.put(list8.get(0).getString("usercode"), list8);
					CacheUtil.setMap("KqdsChuFangDetail:value", key8);
					
				}
				
				if(list9!=null&&list9.size()>0){
					CacheUtil.addZSet("KqdsPayCost:key", list9.get(0).get("usercode"),new Date().getTime());
					Map<String,List<JSONObject>> key9=new HashMap<String,List<JSONObject>>();
					key9.put(list9.get(0).getString("usercode"), list9);
					CacheUtil.setMap("KqdsPayCost:value", key9);
				}
				
				if(list10!=null&&list10.size()>0){
					CacheUtil.addZSet("HudhDzblDetail:key", list10.get(0).get("blcode"),new Date().getTime());
					Map<String,List<JSONObject>> key10=new HashMap<String,List<JSONObject>>();
					key10.put(list10.get(0).getString("blcode"), list10);
					CacheUtil.setMap("HudhDzblDetail:value", key10);
				}
				
				if(list11!=null&&list11.size()>0){
					CacheUtil.addZSet("HudhInvoiceDetail:key", list11.get(0).get("usercode"),new Date().getTime());
					Map<String,List<JSONObject>> key11=new HashMap<String,List<JSONObject>>();
					key11.put(list11.get(0).getString("usercode"), list11);
					CacheUtil.setMap("HudhInvoiceDetail:value", key11);
				}
				if(list12!=null&&list12.size()>0){
					CacheUtil.addZSet("KqdsJzqk:key", list12.get(0).get("usercode"),new Date().getTime());
					Map<String,List<JSONObject>> key12=new HashMap<String,List<JSONObject>>();
					key12.put(list12.get(0).getString("usercode"), list12);
					CacheUtil.setMap("KqdsJzqk:value", key12);
				}
				CacheUtil.close();
			}
			//System.err.println(new Date().getTime()-time+"结束");
	}
	
	@Override
	public JSONObject selectInvoiceDetailByTime(BootStrapPage bp,Map<String,String> map) throws Exception {
		if(map.get("sortName")!=null){
			if(map.get("sortName").equals("duty_parayraph")){
				map.put("sortName", "invoice.duty_parayraph");
			}
			if(map.get("sortName").equals("invoice_time")){
				map.put("sortName", "invoice.invoice_time");
			}
			if(map.get("sortName").equals("invoice_value")){
				map.put("sortName", "invoice.invoice_value");
			}
			if(map.get("sortName").equals("createtime")){
				map.put("sortName", "invoice.createtime");
			}
			if(map.get("sortName").equals("drawer")){
				map.put("sortName", "invoice.drawer");
			}
			if(map.get("sortName").equals("taxpayer_number")){
				map.put("sortName", "invoice.taxpayer_number");
			}
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "invoice.usercode");
			}
			if(map.get("sortName").equals("invoice_detail")){
				map.put("sortName", "invoice.invoice_detail");
			}
			if(map.get("sortName").equals("updatetime")){
				map.put("sortName", "invoice.updatetime");
			}
			if(map.get("sortName").equals("dishonour")){
				map.put("sortName", "invoice.dishonour");
			}
			if(map.get("sortName").equals("createuser")){
				map.put("sortName", "invoice.createuser");
			}
			if(map.get("sortName").equals("updateuser")){
				map.put("sortName", "invoice.updateuser");
			}
		}
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> json=invoiceDetailDao.selectInvoiceDetailByTime(map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(json);
		JSONObject jobj = new JSONObject();
		List<JSONObject> invocievalue=invoiceDetailDao.selectInvoiceValueByTime(map);
		String allinvoicevalue="0.00";
		String invoicevalue="0.00";
		String dishonourvalue="0.00";
		if(invocievalue.size()>0){
			for (JSONObject jsonObject : invocievalue) {
				if(jsonObject.getString("dishonour").equals("0")){
					invoicevalue=jsonObject.getString("invoicevalue");
				}else if(jsonObject.getString("dishonour").equals("1")){
					dishonourvalue=jsonObject.getString("invoicevalue");
				}
			}
		}	 
		allinvoicevalue=new BigDecimal(invoicevalue).add(new BigDecimal(dishonourvalue)).toString();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", json);
		jobj.put("allinvoicevalue", allinvoicevalue);
		jobj.put("invoicevalue", invoicevalue);
		jobj.put("dishonourvalue", dishonourvalue);
		return jobj;
	}
}
