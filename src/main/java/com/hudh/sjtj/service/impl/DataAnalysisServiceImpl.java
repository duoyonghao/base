/**  
  *
  * @Title:  DataAnalysisServiceImpl.java   
  * @Package com.hudh.sjtj.service.impl   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年9月23日 下午2:33:27   
  * @version V1.0  
  */ 
package com.hudh.sjtj.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.logstash.logback.encoder.org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hudh.dept.dao.SysDeptPrivDao;
import com.hudh.sjtj.dao.DataAnalysisDao;
import com.hudh.sjtj.service.IDataAnalysisService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.memberRecord.KQDS_Member_RecordLogic;
import com.kqds.service.base.paycost.Kqds_PayCostLogic;
import com.kqds.service.base.refund.KQDS_RefundLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.sys.DictUtil;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  DataAnalysisServiceImpl   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年9月23日 下午2:33:27   
  *      
  */
@Service
public class DataAnalysisServiceImpl implements IDataAnalysisService {
	
	@Autowired
	private DataAnalysisDao analysisDao;
	@Autowired
	private SysDeptPrivDao sysDeptPrivDao;
	@Autowired
	private YZDictLogic dictLogic;
	
	@Autowired
	private Kqds_PayCostLogic kpayLogic;
	
	@Autowired
	private KQDS_RefundLogic kRefundLogic;
	
	@Autowired
	private KQDS_CostOrder_DetailLogic kDetailLogic;
	
	@Autowired
	private KQDS_REGLogic krLogic;
	
	@Autowired
	private YZPersonLogic yzPersonLogic;
	@Autowired
	private YZDeptLogic yzDeptLogic;
	
	@Autowired
	private KQDS_UserDocumentLogic kLogic;
	
	@Autowired
	private KQDS_Member_RecordLogic kRecordLogic;
	
	/**   
	  * <p>Title: findBaseDataAskperson</p>   
	  * <p>Description: </p>   
	  * @param request
	  * @param dataMap
	  * @return   
	 * @throws Exception 
	  * @see com.hudh.sjtj.service.IDataAnalysisService#findBaseDataAskperson(javax.servlet.http.HttpServletRequest, java.util.Map)   
	  */  
	@Override
	public List<JSONObject> findBaseDataAskperson(HttpServletRequest request, Map<String, String> dataMap) throws Exception {
		// TODO Auto-generated method stub
		return analysisDao.findBaseDataAskperson();
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<JSONObject> findCJStatisticsByYear(HttpServletRequest request, Map<String, String> map) throws Exception {
		//// 获取初诊编码
		YZPerson person = SessionUtil.getLoginPerson(request);
		//有按钮标识和all 查询所有部门人员id
		String name="";
		if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("all")){
			List<JSONObject> personSeqIds=sysDeptPrivDao.findPersonSeqIdByButtonName(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < personSeqIds.size(); i++) {
				if(i==personSeqIds.size()-1){
					str.append("\'"+personSeqIds.get(i).getString("seqid")+"\'");
				}else{
					str.append("\'"+personSeqIds.get(i).getString("seqid")+"\',");
				}
			}
			map.put("visualstaff", str.toString());
			name="所有部门";
			//传入部门id 查询部门内人员id
		}else if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&!map.get("deptCategory").equals("all")&&!map.get("deptCategory").equals("personage")){
			//seq_id,user_name,user_id
			map.put("dept_id", map.get("deptCategory"));
			List<JSONObject> yzpersonSeqids = yzPersonLogic.selectPersonPaiban(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < yzpersonSeqids.size(); i++) {
				if(i==yzpersonSeqids.size()-1){
					str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\'");
				}else{
					str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\',");
				}
			}
			map.put("visualstaff", str.toString());
			String deptname = yzDeptLogic.getDeptNamesBySeqIds(map.get("deptCategory"));
			name=deptname;
			//传入人员id 查询个人数据
		}else if(YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("personage")){
			map.put("visualstaff", "\'"+person.getSeqId()+"\'");
			name=person.getUserName();
		}
		String czseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
		map.put("recesort", "\'"+czseqId+"\'");
		//查询初诊人数
		JSONObject czAllNums=analysisDao.findQuantityByAskpersonAndYear(map);
		//查询初诊成交人数
		JSONObject czCjNum=analysisDao.findCJQuantityByAskpersonAndYear(map);
		//查询初诊业绩
		List<JSONObject> czPaymoney=analysisDao.findPaymoneyByYear(map);
		// 获取复诊编码
		String fzseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
		map.put("recesort", "\'"+fzseqId+"\'");
		//查询复诊人数
		JSONObject fzAllNums=analysisDao.findQuantityByAskpersonAndYear(map);
		//查询复诊成交人数
		JSONObject fzCjNum=analysisDao.findCJQuantityByAskpersonAndYear(map);
		//查询复诊业绩
		List<JSONObject> fzPaymoney=analysisDao.findPaymoneyByYear(map);
		// 获取再消费编码
		String zxfseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
		// 获取复查编码
		String fcseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);			
		map.put("recesort", "\'"+zxfseqId+"\',\'"+fcseqId+"\'");
		//查询再消费人数
		JSONObject zxfAllNums=analysisDao.findQuantityByAskpersonAndYear(map);
		//查询再消费成交人数
		map.put("recesort", "\'"+zxfseqId+"\'");
		JSONObject zxfCjNum=analysisDao.findCJQuantityByAskpersonAndYear(map);
		//查询再消费业绩
		List<JSONObject> zxfPaymoney=analysisDao.findPaymoneyByYear(map);
		//查询预交金业绩
		List<JSONObject> Cmoney=analysisDao.findCmoneyByYear(map);
		//药费
		List<JSONObject> Drugsmoney=analysisDao.findDrugsmoneyByYear(map);
		
		String startyear=map.get("startyear");
		String endyear=map.get("endyear");
		//业绩循环标识
		int paymoneyMark1=0;
		int paymoneyMark2=0;
		int paymoneyMark3=0;
		int cmoneyMark4=0;
		int drugsmoneyMark5=0;
		List<JSONObject> list= new ArrayList<JSONObject>();
		for (int i = Integer.parseInt(startyear); i <= Integer.parseInt(endyear); i++) {
			JSONObject json= new JSONObject();
			json.put("month", i+"年");
			json.put("name", name);
			if(czAllNums!=null){
				String nums=czAllNums.getString(i+"年");
				if(nums!=null&&!nums.equals("")){
					json.put("czperson", nums);
				}
			}else{
				json.put("czperson", "0");
			}
			if(czCjNum!=null){
				String nums=czCjNum.getString(i+"年");
				if(nums!=null&&!nums.equals("")){
					json.put("czcjperson", nums);
				}
			}else{
				json.put("czcjperson", "0");
			}
			if(Integer.parseInt(json.getString("czperson"))>0){
				json.put("czcjratio", Double.parseDouble(json.getString("czcjperson"))/Double.parseDouble(json.getString("czperson"))+"");
			}else{
				json.put("czcjratio", "0");
			}
			if(czPaymoney!=null&&czPaymoney.size()>paymoneyMark1){
				//存在数据
				JSONObject nums=czPaymoney.get(paymoneyMark1);
				//存在这一年
				if(nums.getString("年").equals(i+"")){
					String paymoney=nums.getString("paymoney");
					if(paymoney!=null&&!paymoney.equals("")){
						json.put("czpaymoney", paymoney);
					}
					String jcfmoney=nums.getString("jcfmoney");
					if(jcfmoney!=null&&!jcfmoney.equals("")){
						json.put("czjcfmoney", jcfmoney);
					}
					String yjjmoney=nums.getString("yjjmoney");
					if(yjjmoney!=null&&!yjjmoney.equals("")){
						json.put("czyjjmoney", yjjmoney);
					}
					paymoneyMark1+=1;
				}else{
					json.put("czpaymoney", "0.00");
					json.put("czjcfmoney", "0.00");
					json.put("czyjjmoney", "0.00");
				}
			}else{
				json.put("czpaymoney", "0.00");
				json.put("czjcfmoney", "0.00");
				json.put("czyjjmoney", "0.00");
			}
			if(fzAllNums!=null){
				String nums=fzAllNums.getString(i+"年");
				if(nums!=null&&!nums.equals("")){
					json.put("fzperson", nums);
				}
			}else{
				json.put("fzperson", "0");
			}
			if(fzCjNum!=null){
				String nums=fzCjNum.getString(i+"年");
				if(nums!=null&&!nums.equals("")){
					json.put("fzcjperson", nums);
				}
			}else{
				json.put("fzcjperson", "0");
			}
			if(Integer.parseInt(json.getString("fzperson"))>0){
				json.put("fzcjratio", Double.parseDouble(json.getString("fzcjperson"))/Double.parseDouble(json.getString("fzperson"))+"");
			}else{
				json.put("fzcjratio", "0");
			}
			
			if(fzPaymoney!=null&&fzPaymoney.size()>paymoneyMark2){
				//存在数据
				JSONObject nums=fzPaymoney.get(paymoneyMark2);
				if(nums.getString("年").equals(i+"")){
					String paymoney=nums.getString("paymoney");
					if(paymoney!=null&&!paymoney.equals("")){
						json.put("fzpaymoney", paymoney);
					}
					String jcfmoney=nums.getString("jcfmoney");
					if(jcfmoney!=null&&!jcfmoney.equals("")){
						json.put("fzjcfmoney", jcfmoney);
					}
					String yjjmoney=nums.getString("yjjmoney");
					if(yjjmoney!=null&&!yjjmoney.equals("")){
						json.put("fzyjjmoney", paymoney);
					}
					paymoneyMark2+=1;
				}else{
					json.put("fzpaymoney", "0.00");
					json.put("fzjcfmoney", "0.00");
					json.put("fzyjjmoney", "0.00");
				}
			}else{
				json.put("fzpaymoney", "0.00");
				json.put("fzjcfmoney", "0.00");
				json.put("fzyjjmoney", "0.00");
			}
			if(zxfAllNums!=null){
				String nums=zxfAllNums.getString(i+"年");
				if(nums!=null&&!nums.equals("")){
					json.put("zxfperson", nums);
				}
			}else{
				json.put("zxfperson", "0");
			}
			if(zxfCjNum!=null){
				String nums=zxfCjNum.getString(i+"年");
				if(nums!=null&&!nums.equals("")){
					json.put("zxfcjperson", nums);
				}
			}else{
				json.put("zxfcjperson", "0");
			}
			if(Integer.parseInt(json.getString("zxfperson"))>0){
				json.put("zxfcjratio", Double.parseDouble(json.getString("zxfcjperson"))/Double.parseDouble(json.getString("zxfperson"))+"");
			}else{
				json.put("zxfcjratio", "0");
			}
			if(zxfPaymoney!=null&&zxfPaymoney.size()>paymoneyMark3){
				//存在数据
				JSONObject nums=zxfPaymoney.get(paymoneyMark3);
				if(nums.getString("年").equals(i+"")){
					String paymoney=nums.getString("paymoney");
					if(paymoney!=null&&!paymoney.equals("")){
						json.put("zxfpaymoney", paymoney);
					}
					String jcfmoney=nums.getString("jcfmoney");
					if(jcfmoney!=null&&!jcfmoney.equals("")){
						json.put("zxfjcfmoney", jcfmoney);
					}
					String yjjmoney=nums.getString("yjjmoney");
					if(yjjmoney!=null&&!yjjmoney.equals("")){
						json.put("zxfyjjmoney", yjjmoney);
					}
					paymoneyMark3+=1;
				}else{
					json.put("zxfpaymoney", "0.00");
					json.put("zxfjcfmoney", "0.00");
					json.put("zxfyjjmoney", "0.00");
				}
				
			}else{
				json.put("zxfpaymoney", "0.00");
				json.put("zxfjcfmoney", "0.00");
				json.put("zxfyjjmoney", "0.00");
			}
			if(Cmoney!=null&&Cmoney.size()>cmoneyMark4){
				//存在数据
				JSONObject nums=Cmoney.get(cmoneyMark4);
				if(nums.getString("年").equals(i+"")&&nums.getString("type").equals("充值")){
					String cmoney=nums.getString("cmoney");
					if(cmoney!=null&&!cmoney.equals("")){
						json.put("cmoney", cmoney);
					}
					cmoneyMark4+=1;
				}else{
					json.put("cmoney", "0.00");
				}
				if(cmoneyMark4<Cmoney.size()){
					//存在数据
					JSONObject nums1=Cmoney.get(cmoneyMark4);
					if(nums1.getString("年").equals(i+"")&&nums1.getString("type").equals("退费")){
						String cmoney=nums1.getString("cmoney");
						if(cmoney!=null&&!cmoney.equals("")){
							json.put("tcmoney", cmoney);
						}
						cmoneyMark4+=1;
					}else{
						json.put("tcmoney", "0.00");
					}
				}else{
					json.put("tcmoney", "0.00");
				}
			}else{
				json.put("cmoney", "0.00");
				json.put("tcmoney", "0.00");
			}
			if(Drugsmoney!=null&&Drugsmoney.size()>drugsmoneyMark5){
				//存在数据
				JSONObject nums=Drugsmoney.get(drugsmoneyMark5);
				if(nums.getString("年").equals(i+"")){
					String drugsmoney=nums.getString("drugsmoney");
					if(drugsmoney!=null&&!drugsmoney.equals("")){
						json.put("drugsmoney", drugsmoney);
					}
					drugsmoneyMark5+=1;
				}else{
					json.put("drugsmoney", "0.00");
				}
			}else{
				json.put("drugsmoney", "0.00");
			}
			json.put("xmpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))));
			//json.put("zjpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))+Double.parseDouble(json.getString("cmoney"))));
			list.add(json);
		}
		return list;
	}
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<JSONObject> findCJStatisticsByMonth(HttpServletRequest request, Map<String, String> map) throws Exception {
		
		YZPerson person = SessionUtil.getLoginPerson(request);
		//有按钮标识和all 查询所有部门人员id
		String name="";
		if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("all")){
			List<JSONObject> personSeqIds=sysDeptPrivDao.findPersonSeqIdByButtonName(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < personSeqIds.size(); i++) {
				if(i==personSeqIds.size()-1){
					str.append("\'"+personSeqIds.get(i).getString("seqid")+"\'");
				}else{
					str.append("\'"+personSeqIds.get(i).getString("seqid")+"\',");
				}
			}
			map.put("visualstaff", str.toString());
			name="所有部门";
			//传入部门id 查询部门内人员id
		}else if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&!map.get("deptCategory").equals("all")&&!map.get("deptCategory").equals("personage")){
			//seq_id,user_name,user_id
			map.put("dept_id", map.get("deptCategory"));
			List<JSONObject> yzpersonSeqids = yzPersonLogic.selectPersonPaiban(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < yzpersonSeqids.size(); i++) {
				if(i==yzpersonSeqids.size()-1){
					str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\'");
				}else{
					str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\',");
				}
			}
			map.put("visualstaff", str.toString());
			String deptname = yzDeptLogic.getDeptNamesBySeqIds(map.get("deptCategory"));
			name=deptname;
			//传入人员id 查询个人数据
		}else if(YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("personage")){
			map.put("visualstaff", "\'"+person.getSeqId()+"\'");
			name=person.getUserName();
		}
		// 获取初诊编码
		String czseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
		map.put("recesort", "\'"+czseqId+"\'");
		//查询初诊人数
		JSONObject czAllNums=analysisDao.findQuantityByAskpersonAndMonth(map);
		//查询初诊成交人数
		JSONObject czCjNum=analysisDao.findCJQuantityByAskpersonAndMonth(map);
		//查询初诊业绩
		List<JSONObject> czPaymoney=analysisDao.findPaymoneyByMonth(map);
		// 获取复诊编码
		String fzseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
		map.put("recesort", "\'"+fzseqId+"\'");
		//查询复诊人数
		JSONObject fzAllNums=analysisDao.findQuantityByAskpersonAndMonth(map);
		//查询复诊成交人数
		JSONObject fzCjNum=analysisDao.findCJQuantityByAskpersonAndMonth(map);
		//查询复诊业绩
		List<JSONObject> fzPaymoney=analysisDao.findPaymoneyByMonth(map);
		// 获取再消费编码
		String zxfseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
		// 获取复查编码
		String fcseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);			
		map.put("recesort", "\'"+zxfseqId+"\',\'"+fcseqId+"\'");
		//查询再消费人数
		JSONObject zxfAllNums=analysisDao.findQuantityByAskpersonAndMonth(map);
		//查询再消费成交人数
		map.put("recesort", "\'"+zxfseqId+"\'");
		JSONObject zxfCjNum=analysisDao.findCJQuantityByAskpersonAndMonth(map);
		//查询再消费业绩
		List<JSONObject> zxfPaymoney=analysisDao.findPaymoneyByMonth(map);
		//查询预交金业绩
		List<JSONObject> Cmoney= analysisDao.findCmoneyByMonth(map);
		//药费
		List<JSONObject> Drugsmoney=analysisDao.findDrugsmoneyByMonth(map);
		String startmonth=map.get("startmonth");
		String endmonth=map.get("endmonth");
		String year = map.get("year");

		List<JSONObject> list= new ArrayList<JSONObject>();
		//合计
		int hjczAllNums=0;
		int hjczCjNum=0;
		double hjczPaymoney=0;
		double hjczJcfmoney=0;
		double hjczYjjmoney=0;
		int hjfzAllNums=0;
		int hjfzCjNum=0;
		double hjfzPaymoney=0;
		double hjfzJcfmoney=0;
		double hjfzYjjmoney=0;
		int hjzxfAllNums=0;
		int hjzxfCjNum=0;
		double hjzxfPaymoney=0;
		double hjCmoney=0;
		double hjzxfJcfmoney=0;
		double hjzxfYjjmoney=0;
		double hjTcmoney=0;
		double hjDrugsmoney=0;

		//业绩循环标识
		int paymoneyMark1=0;
		int paymoneyMark2=0;
		int paymoneyMark3=0;
		int cmoneyMark4=0;
		int drugsmoneyMark5=0;
		for (int i = Integer.parseInt(startmonth); i <= Integer.parseInt(endmonth); i++) {
			JSONObject json= new JSONObject();
			json.put("month", year+"年"+i+"月");
			json.put("name", name);
			if(czAllNums!=null){
				String nums=czAllNums.getString(i+"月");
				if(nums!=null&&!nums.equals("")){
					json.put("czperson", nums);
				}
			}else{
				json.put("czperson", "0");
			}
			if(czCjNum!=null){
				String nums=czCjNum.getString(i+"月");
				if(nums!=null&&!nums.equals("")){
					json.put("czcjperson", nums);
				}
			}else{
				json.put("czcjperson", "0");
			}
			if(Integer.parseInt(json.getString("czperson"))>0){
				json.put("czcjratio", Double.parseDouble(json.getString("czcjperson"))/Double.parseDouble(json.getString("czperson"))+"");
			}else{
				json.put("czcjratio", "0");
			}
			if(czPaymoney!=null&&czPaymoney.size()>paymoneyMark1){
				//存在数据
				JSONObject nums=czPaymoney.get(paymoneyMark1);
				if(nums.getString("年").equals(year)){
					if(nums.getString("月").equals(i+"")){
						String paymoney=nums.getString("paymoney");
						if(paymoney!=null&&!paymoney.equals("")){
							json.put("czpaymoney", paymoney);
						}
						String jcfmoney=nums.getString("jcfmoney");
						if(jcfmoney!=null&&!jcfmoney.equals("")){
							json.put("czjcfmoney", jcfmoney);
						}
						String yjjmoney=nums.getString("yjjmoney");
						if(yjjmoney!=null&&!yjjmoney.equals("")){
							json.put("czyjjmoney", yjjmoney);
						}
						paymoneyMark1+=1;
					}else{
						json.put("czpaymoney", "0.00");
						json.put("czjcfmoney", "0.00");
						json.put("czyjjmoney", "0.00");
					}
				}else{
					json.put("czpaymoney", "0.00");
					json.put("czjcfmoney", "0.00");
					json.put("czyjjmoney", "0.00");
				}
			}else{
				json.put("czpaymoney", "0.00");
				json.put("czjcfmoney", "0.00");
				json.put("czyjjmoney", "0.00");
			}
			if(fzAllNums!=null){
				String nums=fzAllNums.getString(i+"月");
				if(nums!=null&&!nums.equals("")){
					json.put("fzperson", nums);
				}
			}else{
				json.put("fzperson", "0");
			}
			if(fzCjNum!=null){
				String nums=fzCjNum.getString(i+"月");
				if(nums!=null&&!nums.equals("")){
					json.put("fzcjperson", nums);
				}
			}else{
				json.put("fzcjperson", "0");
			}
			if(Integer.parseInt(json.getString("fzperson"))>0){
				json.put("fzcjratio", Double.parseDouble(json.getString("fzcjperson"))/Double.parseDouble(json.getString("fzperson"))+"");
			}else{
				json.put("fzcjratio", "0");
			}
			
			if(fzPaymoney!=null&&fzPaymoney.size()>paymoneyMark2){
					//存在数据
					JSONObject nums=fzPaymoney.get(paymoneyMark2);
					if(nums.getString("年").equals(year)){
						if(nums.getString("月").equals(i+"")){
							String paymoney=nums.getString("paymoney");
							if(paymoney!=null&&!paymoney.equals("")){
								json.put("fzpaymoney", paymoney);
							}
							String jcfmoney=nums.getString("jcfmoney");
							if(jcfmoney!=null&&!jcfmoney.equals("")){
								json.put("fzjcfmoney", jcfmoney);
							}
							String yjjmoney=nums.getString("yjjmoney");
							if(yjjmoney!=null&&!yjjmoney.equals("")){
								json.put("fzyjjmoney", yjjmoney);
							}
							paymoneyMark2+=1;
						}else{
							json.put("fzpaymoney", "0.00");
							json.put("fzjcfmoney", "0.00");
							json.put("fzyjjmoney", "0.00");
						}
					}else{
						json.put("fzpaymoney", "0.00");
						json.put("fzjcfmoney", "0.00");
						json.put("fzyjjmoney", "0.00");
					}
			}else{
				json.put("fzpaymoney", "0.00");
				json.put("fzjcfmoney", "0.00");
				json.put("fzyjjmoney", "0.00");
			}
			if(zxfAllNums!=null){
				String nums=zxfAllNums.getString(i+"月");
				if(nums!=null&&!nums.equals("")){
					json.put("zxfperson", nums);
				}
			}else{
				json.put("zxfperson", "0");
			}
			if(zxfCjNum!=null){
				String nums=zxfCjNum.getString(i+"月");
				if(nums!=null&&!nums.equals("")){
					json.put("zxfcjperson", nums);
				}
			}else{
				json.put("zxfcjperson", "0");
			}
			if(Integer.parseInt(json.getString("zxfperson"))>0){
				json.put("zxfcjratio", Double.parseDouble(json.getString("zxfcjperson"))/Double.parseDouble(json.getString("zxfperson"))+"");
			}else{
				json.put("zxfcjratio", "0");
			}
			if(zxfPaymoney!=null&&zxfPaymoney.size()>paymoneyMark3){
				
					//存在数据
					JSONObject nums=zxfPaymoney.get(paymoneyMark3);
					if(nums.getString("年").equals(year)){
						if(nums.getString("月").equals(i+"")){
							String paymoney=nums.getString("paymoney");
							if(paymoney!=null&&!paymoney.equals("")){
								json.put("zxfpaymoney", paymoney);
							}
							String jcfmoney=nums.getString("jcfmoney");
							if(jcfmoney!=null&&!jcfmoney.equals("")){
								json.put("zxfjcfmoney", jcfmoney);
							}
							String yjjmoney=nums.getString("yjjmoney");
							if(yjjmoney!=null&&!yjjmoney.equals("")){
								json.put("zxfyjjmoney", yjjmoney);
							}
							paymoneyMark3+=1;
						}else{
							json.put("zxfpaymoney", "0.00");
							json.put("zxfjcfmoney", "0.00");
							json.put("zxfyjjmoney", "0.00");
						}
					}else{
						json.put("zxfpaymoney", "0.00");
						json.put("zxfjcfmoney", "0.00");
						json.put("zxfyjjmoney", "0.00");
					}
			}else{
				json.put("zxfpaymoney", "0.00");
				json.put("zxfjcfmoney", "0.00");
				json.put("zxfyjjmoney", "0.00");
			}
			if(Cmoney!=null&&Cmoney.size()>cmoneyMark4){
				//存在数据
				JSONObject nums=Cmoney.get(cmoneyMark4);
				if(nums.getString("年").equals(year)){
					if(nums.getString("月").equals(i+"")&&nums.getString("type").equals("充值")){
						String cmoney=nums.getString("cmoney");
						if(cmoney!=null&&!cmoney.equals("")){
							json.put("cmoney", cmoney);
						}
						cmoneyMark4+=1;
					}else{
						json.put("cmoney", "0.00");
					}
					if(cmoneyMark4<Cmoney.size()){
						//存在数据
						JSONObject nums1=Cmoney.get(cmoneyMark4);
						if(nums1.getString("月").equals(i+"")&&nums1.getString("type").equals("退费")){
							String cmoney=nums1.getString("cmoney");
							if(cmoney!=null&&!cmoney.equals("")){
								json.put("tcmoney", cmoney);
							}
							cmoneyMark4+=1;
						}else{
							json.put("tcmoney", "0.00");
						}
					}else{
						json.put("tcmoney", "0.00");
					}
				}else{
					json.put("cmoney", "0.00");
					json.put("tcmoney", "0.00");
				}
			}else{
				json.put("cmoney", "0.00");
				json.put("tcmoney", "0.00");
			}
			if(Drugsmoney!=null&&Drugsmoney.size()>drugsmoneyMark5){
				//存在数据
				JSONObject nums=Drugsmoney.get(drugsmoneyMark5);
				if(nums.getString("年").equals(year)){
					if(nums.getString("月").equals(i+"")){
						String drugsmoney=nums.getString("drugsmoney");
						if(drugsmoney!=null&&!drugsmoney.equals("")){
							json.put("drugsmoney", drugsmoney);
						}
						drugsmoneyMark5+=1;
					}else{
						json.put("drugsmoney", "0.00");
					}
				}else{
					json.put("drugsmoney", "0.00");
				}
			}else{
				json.put("drugsmoney", "0.00");
			}
			json.put("xmpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))));
			//json.put("zjpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))+Double.parseDouble(json.getString("cmoney"))));
			hjczAllNums+=Integer.parseInt(json.getString("czperson"));
			hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
			hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
			hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
			hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
			hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
			hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
			hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
			hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
			hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
			hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
			hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
			hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
			hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
			hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
			hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
			hjCmoney+=Double.parseDouble(json.getString("cmoney"));
			hjTcmoney+=Double.parseDouble(json.getString("tcmoney"));
			list.add(json);
		}
		JSONObject jsonobject=new JSONObject();
		jsonobject.put("month", year+"年");
		jsonobject.put("name", "合计");
		jsonobject.put("czperson", hjczAllNums+"");
		jsonobject.put("czcjperson", hjczCjNum+"");
		NumberFormat nf1 = NumberFormat.getInstance();
		nf1.setGroupingUsed(false);
		if(hjczPaymoney>0){
			jsonobject.put("czpaymoney",nf1.format(hjczPaymoney));
		}else{
			jsonobject.put("czpaymoney", "0.00");
		}
		if(hjczJcfmoney>0){
			jsonobject.put("czjcfmoney",nf1.format(hjczJcfmoney));
		}else{
			jsonobject.put("czjcfmoney", "0.00");
		}
		if(hjczYjjmoney>0){
			jsonobject.put("czyjjmoney",nf1.format(hjczYjjmoney));
		}else{
			jsonobject.put("czyjjmoney", "0.00");
		}
		jsonobject.put("fzperson", hjfzAllNums+"");
		jsonobject.put("fzcjperson", hjfzCjNum+"");
		if(hjfzPaymoney>0){
			jsonobject.put("fzpaymoney", nf1.format(hjfzPaymoney));
		}else{
			jsonobject.put("fzpaymoney", "0.00");
		}
		if(hjfzJcfmoney>0){
			jsonobject.put("fzjcfmoney", nf1.format(hjfzJcfmoney));
		}else{
			jsonobject.put("fzjcfmoney", "0.00");
		}
		if(hjfzYjjmoney>0){
			jsonobject.put("fzyjjmoney", nf1.format(hjfzYjjmoney));
		}else{
			jsonobject.put("fzyjjmoney", "0.00");
		}
		jsonobject.put("zxfperson", hjzxfAllNums+"");
		jsonobject.put("zxfcjperson", hjzxfCjNum+"");
		if(hjzxfPaymoney>0){
			jsonobject.put("zxfpaymoney", nf1.format(hjzxfPaymoney));
			
		}else{
			jsonobject.put("zxfpaymoney", "0.00");
		}
		if(hjzxfJcfmoney>0){
			jsonobject.put("zxfjcfmoney", nf1.format(hjzxfJcfmoney));
			
		}else{
			jsonobject.put("zxfjcfmoney", "0.00");
		}
		if(hjzxfYjjmoney>0){
			jsonobject.put("zxfyjjmoney", nf1.format(hjzxfYjjmoney));
			
		}else{
			jsonobject.put("zxfyjjmoney", "0.00");
		}
		if(hjCmoney>0){
			jsonobject.put("cmoney", nf1.format(hjCmoney));
			
		}else{
			jsonobject.put("cmoney", "0.00");
		}
		if(hjTcmoney<0){
			jsonobject.put("tcmoney", nf1.format(hjTcmoney));
			
		}else{
			jsonobject.put("tcmoney", "0.00");
		}
		if(hjDrugsmoney>0){
			jsonobject.put("drugsmoney", nf1.format(hjDrugsmoney));
			
		}else{
			jsonobject.put("drugsmoney", "0.00");
		}
		if(hjczAllNums>0){
			jsonobject.put("czcjratio", (double)hjczCjNum/hjczAllNums+"");
		}else{
			jsonobject.put("czcjratio", "0");
		}
		if(hjfzAllNums>0){
			jsonobject.put("fzcjratio", (double)hjfzCjNum/hjfzAllNums+"");
		}else{
			jsonobject.put("fzcjratio", "0");
		}
		if(hjzxfAllNums>0){
			jsonobject.put("zxfcjratio", (double)hjzxfCjNum/hjzxfAllNums+"");
		}else{
			jsonobject.put("zxfcjratio", "0");
		}
		jsonobject.put("xmpaymoney", ""+(Double.parseDouble(jsonobject.getString("czpaymoney"))+Double.parseDouble(jsonobject.getString("fzpaymoney"))+Double.parseDouble(jsonobject.getString("zxfpaymoney"))));
		//jsonobject.put("zjpaymoney", ""+(Double.parseDouble(jsonobject.getString("czpaymoney"))+Double.parseDouble(jsonobject.getString("fzpaymoney"))+Double.parseDouble(jsonobject.getString("zxfpaymoney"))+Double.parseDouble(jsonobject.getString("cmoney"))));
		list.add(jsonobject);
		return list;
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<JSONObject> findCJStatisticsByDay(HttpServletRequest request, Map<String, String> map) throws Exception {
		//获取初诊编码
		YZPerson person = SessionUtil.getLoginPerson(request);
		//有按钮标识和all 查询所有部门人员id
		String name="";
		if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("all")){
			List<JSONObject> personSeqIds=sysDeptPrivDao.findPersonSeqIdByButtonName(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < personSeqIds.size(); i++) {
				if(i==personSeqIds.size()-1){
					str.append("\'"+personSeqIds.get(i).getString("seqid")+"\'");
				}else{
					str.append("\'"+personSeqIds.get(i).getString("seqid")+"\',");
				}
			}
			map.put("visualstaff", str.toString());
			name="所有部门";
			//传入部门id 查询部门内人员id
		}else if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&!map.get("deptCategory").equals("all")&&!map.get("deptCategory").equals("personage")){
			//seq_id,user_name,user_id
			map.put("dept_id", map.get("deptCategory"));
			List<JSONObject> yzpersonSeqids = yzPersonLogic.selectPersonPaiban(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < yzpersonSeqids.size(); i++) {
				if(i==yzpersonSeqids.size()-1){
					str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\'");
				}else{
					str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\',");
				}
			}
			map.put("visualstaff", str.toString());
			String deptname = yzDeptLogic.getDeptNamesBySeqIds(map.get("deptCategory"));
			name=deptname;
			//传入人员id 查询个人数据
		}else if(YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("personage")){
			map.put("visualstaff", "\'"+person.getSeqId()+"\'");
			name=person.getUserName();
		}
		String czseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
		map.put("recesort", "\'"+czseqId+"\'");
		//查询初诊人数
		JSONObject czAllNums=analysisDao.findQuantityByAskpersonAndDay(map);
		//查询初诊成交人数
		JSONObject czCjNum=analysisDao.findCJQuantityByAskpersonAndDay(map);
		//查询初诊业绩
		List<JSONObject> czPaymoney=analysisDao.findPaymoneyByDay(map);
		// 获取复诊编码
		String fzseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
		map.put("recesort", "\'"+fzseqId+"\'");
		//查询复诊人数
		JSONObject fzAllNums=analysisDao.findQuantityByAskpersonAndDay(map);
		//查询复诊成交人数
		JSONObject fzCjNum=analysisDao.findCJQuantityByAskpersonAndDay(map);
		//查询复诊业绩
		List<JSONObject> fzPaymoney=analysisDao.findPaymoneyByDay(map);
		// 获取再消费编码
		String zxfseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
		// 获取复查编码
		String fcseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);			
		map.put("recesort", "\'"+zxfseqId+"\',\'"+fcseqId+"\'");
		//查询再消费人数
		JSONObject zxfAllNums=analysisDao.findQuantityByAskpersonAndDay(map);
		//查询再消费成交人数
		map.put("recesort", "\'"+zxfseqId+"\'");
		JSONObject zxfCjNum=analysisDao.findCJQuantityByAskpersonAndDay(map);
		//查询再消费业绩
		List<JSONObject> zxfPaymoney=analysisDao.findPaymoneyByDay(map);
		//查询预交金业绩
		List<JSONObject> Cmoney=analysisDao.findCmoneyByDay(map);
		//药费
		List<JSONObject> Drugsmoney=analysisDao.findDrugsmoneyByDay(map);
		List<JSONObject> list= new ArrayList<JSONObject>();
		//业绩循环标识
		int paymoneyMark1=0;
		int paymoneyMark2=0;
		int paymoneyMark3=0;
		int cmoneyMark4=0;
		int drugsmoneyMark5=0;
		if(map.get("startTime").contains("年")&&map.get("startTime").length()==7||map.get("startTime").contains("年")&&map.get("startTime").length()==8){
			String endday=map.get("endday");
			for (int i = 1; i <= Integer.parseInt(endday); i++) {
				JSONObject json= new JSONObject();
				json.put("day", i+"日");
				if(czAllNums!=null){
					String nums=czAllNums.getString(i+"日");
					if(nums!=null&&!nums.equals("")){
						json.put("czperson", nums);
					}
				}else{
					json.put("czperson", "0");
				}
				if(czCjNum!=null){
					String nums=czCjNum.getString(i+"日");
					if(nums!=null&&!nums.equals("")){
						json.put("czcjperson", nums);
					}
				}else{
					json.put("czcjperson", "0");
				}
				if(czPaymoney!=null&&czPaymoney.size()>paymoneyMark1){
					//存在数据
					JSONObject nums=czPaymoney.get(paymoneyMark1);
					if(nums.getString("日").equals(i+"")){
						String paymoney=nums.getString("paymoney");
						if(paymoney!=null&&!paymoney.equals("")){
							json.put("czpaymoney", paymoney);
						}
						String jcfmoney=nums.getString("jcfmoney");
						if(jcfmoney!=null&&!jcfmoney.equals("")){
							json.put("czjcfmoney", jcfmoney);
						}
						String yjjmoney=nums.getString("yjjmoney");
						if(yjjmoney!=null&&!yjjmoney.equals("")){
							json.put("czyjjmoney", yjjmoney);
						}
						paymoneyMark1+=1;
					}else{
						json.put("czpaymoney", "0.00");
						json.put("czjcfmoney", "0.00");
						json.put("czyjjmoney", "0.00");
					}
						
				}else{
					json.put("czpaymoney", "0.00");
					json.put("czjcfmoney", "0.00");
					json.put("czyjjmoney", "0.00");
				}
				if(fzAllNums!=null){
					String nums=fzAllNums.getString(i+"日");
					if(nums!=null&&!nums.equals("")){
						json.put("fzperson", nums);
					}
				}else{
					json.put("fzperson", "0");
				}
				if(fzCjNum!=null){
					String nums=fzCjNum.getString(i+"日");
					if(nums!=null&&!nums.equals("")){
						json.put("fzcjperson", nums);
					}
				}else{
					json.put("fzcjperson", "0");
				}
				
				if(fzPaymoney!=null&&fzPaymoney.size()>paymoneyMark2){
						//存在数据
						JSONObject nums=fzPaymoney.get(paymoneyMark2);
						if(nums.getString("日").equals(i+"")){
							String paymoney=nums.getString("paymoney");
							if(paymoney!=null&&!paymoney.equals("")){
								json.put("fzpaymoney", paymoney);
							}
							String jcfmoney=nums.getString("jcfmoney");
							if(jcfmoney!=null&&!jcfmoney.equals("")){
								json.put("fzjcfmoney", jcfmoney);
							}
							String yjjmoney=nums.getString("yjjmoney");
							if(yjjmoney!=null&&!yjjmoney.equals("")){
								json.put("fzyjjmoney", yjjmoney);
							}
							paymoneyMark2+=1;
						}else{
							json.put("fzpaymoney", "0.00");
							json.put("fzjcfmoney", "0.00");
							json.put("fzyjjmoney", "0.00");
						}
				}else{
					json.put("fzpaymoney", "0.00");
					json.put("fzjcfmoney", "0.00");
					json.put("fzyjjmoney", "0.00");
				}
				if(zxfAllNums!=null){
					String nums=zxfAllNums.getString(i+"日");
					if(nums!=null&&!nums.equals("")){
						json.put("zxfperson", nums);
					}
				}else{
					json.put("zxfperson", "0");
				}
				if(zxfCjNum!=null){
					String nums=zxfCjNum.getString(i+"日");
					if(nums!=null&&!nums.equals("")){
						json.put("zxfcjperson", nums);
					}
				}else{
					json.put("zxfcjperson", "0");
				}
				if(zxfPaymoney!=null&&zxfPaymoney.size()>paymoneyMark3){
					//存在数据
					JSONObject nums=zxfPaymoney.get(paymoneyMark3);
					
					if(nums.getString("日").equals(i+"")){
						String paymoney=nums.getString("paymoney");
						if(paymoney!=null&&!paymoney.equals("")){
							json.put("zxfpaymoney", paymoney);
						}
						String jcfmoney=nums.getString("jcfmoney");
						if(jcfmoney!=null&&!jcfmoney.equals("")){
							json.put("zxfjcfmoney", jcfmoney);
						}
						String yjjmoney=nums.getString("yjjmoney");
						if(yjjmoney!=null&&!yjjmoney.equals("")){
							json.put("zxfyjjmoney", yjjmoney);
						}
						paymoneyMark3+=1;
					}else{
						json.put("zxfpaymoney", "0.00");
						json.put("zxfjcfmoney", "0.00");
						json.put("zxfyjjmoney", "0.00");
					}
				}else{
					json.put("zxfpaymoney", "0.00");
					json.put("zxfjcfmoney", "0.00");
					json.put("zxfyjjmoney", "0.00");
				}
				if(Cmoney!=null&&Cmoney.size()>cmoneyMark4){
					//存在数据
					JSONObject nums=Cmoney.get(cmoneyMark4);
					
					if(nums.getString("日").equals(i+"")&&nums.getString("type").equals("充值")){
						String cmoney=nums.getString("cmoney");
						if(cmoney!=null&&!cmoney.equals("")){
							json.put("cmoney", cmoney);
						}
						cmoneyMark4+=1;
					}else{
						json.put("cmoney", "0.00");
					}
					if(cmoneyMark4<Cmoney.size()){
						//存在数据
						JSONObject nums1=Cmoney.get(cmoneyMark4);
						if(nums1.getString("日").equals(i+"")&&nums.getString("type").equals("退费")){
							String cmoney=nums.getString("cmoney");
							if(cmoney!=null&&!cmoney.equals("")){
								json.put("tcmoney", cmoney);
							}
							cmoneyMark4+=1;
						}else{
							json.put("tcmoney", "0.00");
						}
					}else{
						json.put("tcmoney", "0.00");
					}
				}else{
					json.put("cmoney", "0.00");
					json.put("tcmoney", "0.00");
				}
				if(Drugsmoney!=null&&Drugsmoney.size()>drugsmoneyMark5){
					//存在数据
					JSONObject nums=fzPaymoney.get(drugsmoneyMark5);
					if(nums.getString("日").equals(i+"")){
						String drugsmoney=nums.getString("drugsmoney");
						if(drugsmoney!=null&&!drugsmoney.equals("")){
							json.put("drugsmoney", drugsmoney);
						}
						drugsmoneyMark5+=1;
					}else{
						json.put("drugsmoney", "0.00");
					}
				}else{
					json.put("drugsmoney", "0.00");
				}
				
				list.add(json);
			}
			//查询月
		}else{
			//合计
			int hjczAllNums=0;
			int hjczCjNum=0;
			double hjczPaymoney=0;
			double hjczJcfmoney=0;
			double hjczYjjmoney=0;
			int hjfzAllNums=0;
			int hjfzCjNum=0;
			double hjfzPaymoney=0;
			double hjfzJcfmoney=0;
			double hjfzYjjmoney=0;
			int hjzxfAllNums=0;
			int hjzxfCjNum=0;
			double hjzxfPaymoney=0;
			double hjCmoney=0;
			double hjzxfJcfmoney=0;
			double hjzxfYjjmoney=0;
			double hjTcmoney=0;
			double hjDrugsmoney=0;
			//同一天数据
			if(map.get("startTime").equals(map.get("endTime"))){
				JSONObject json= new JSONObject();
				json.put("month", map.get("startTime").substring(0,4)+"年"+map.get("startTime").substring(5,7)+"月"+map.get("startTime").substring(8,10)+"日");
				json.put("name",name);
				if(czAllNums!=null){
					String nums=czAllNums.getString(map.get("startTime"));
					if(nums!=null&&!nums.equals("")){
						json.put("czperson", nums);
					}
				}else{
					json.put("czperson", "0");
				}
				if(czCjNum!=null){
					String nums=czCjNum.getString(map.get("startTime"));
					if(nums!=null&&!nums.equals("")){
						json.put("czcjperson", nums);
					}
				}else{
					json.put("czcjperson", "0");
				}
				if(Integer.parseInt(json.getString("czperson"))>0){
					json.put("czcjratio", Double.parseDouble(json.getString("czcjperson"))/Double.parseDouble(json.getString("czperson"))+"");
				}else{
					json.put("czcjratio", "0");
				}
				if(czPaymoney!=null&&czPaymoney.size()>paymoneyMark1){
					//存在数据
					JSONObject nums=czPaymoney.get(paymoneyMark1);
					if(nums.getString("日").equals(Integer.parseInt(map.get("startTime").substring(8,10))+"")){
						String paymoney=nums.getString("paymoney");
						if(paymoney!=null&&!paymoney.equals("")){
							json.put("czpaymoney", paymoney);
						}
						String jcfmoney=nums.getString("jcfmoney");
						if(jcfmoney!=null&&!jcfmoney.equals("")){
							json.put("czjcfmoney", jcfmoney);
						}
						String yjjmoney=nums.getString("yjjmoney");
						if(yjjmoney!=null&&!yjjmoney.equals("")){
							json.put("czyjjmoney", yjjmoney);
						}
						paymoneyMark1+=1;
					}else{
						json.put("czpaymoney", "0.00");
						json.put("czjcfmoney", "0.00");
						json.put("czyjjmoney", "0.00");
					}
				}else{
					json.put("czpaymoney", "0.00");
					json.put("czjcfmoney", "0.00");
					json.put("czyjjmoney", "0.00");
				}
				if(fzAllNums!=null){
					String nums=fzAllNums.getString(map.get("startTime"));
					if(nums!=null&&!nums.equals("")){
						json.put("fzperson", nums);
					}
				}else{
					json.put("fzperson", "0");
				}
				if(fzCjNum!=null){
					String nums=fzCjNum.getString(map.get("startTime"));
					if(nums!=null&&!nums.equals("")){
						json.put("fzcjperson", nums);
					}
				}else{
					json.put("fzcjperson", "0");
				}
				if(Integer.parseInt(json.getString("fzperson"))>0){
					json.put("fzcjratio", Double.parseDouble(json.getString("fzcjperson"))/Double.parseDouble(json.getString("fzperson"))+"");
				}else{
					json.put("fzcjratio", "0");
				}
				
				if(fzPaymoney!=null&&fzPaymoney.size()>paymoneyMark2){
					//存在数据
					JSONObject nums=fzPaymoney.get(paymoneyMark2);
					if(nums.getString("日").equals(Integer.parseInt(map.get("startTime").substring(8,10))+"")){
						String paymoney=nums.getString("paymoney");
						if(paymoney!=null&&!paymoney.equals("")){
							json.put("fzpaymoney", paymoney);
						}
						String jcfmoney=nums.getString("jcfmoney");
						if(jcfmoney!=null&&!jcfmoney.equals("")){
							json.put("fzjcfmoney", jcfmoney);
						}
						String yjjmoney=nums.getString("yjjmoney");
						if(yjjmoney!=null&&!yjjmoney.equals("")){
							json.put("fzyjjmoney", yjjmoney);
						}
						paymoneyMark2+=1;
					}else{
						json.put("fzpaymoney", "0.00");
						json.put("fzjcfmoney", "0.00");
						json.put("fzyjjmoney", "0.00");
					}
				}else{
					json.put("fzpaymoney", "0.00");
					json.put("fzjcfmoney", "0.00");
					json.put("fzyjjmoney", "0.00");
				}
				if(zxfAllNums!=null){
					String nums=zxfAllNums.getString(map.get("startTime"));
					if(nums!=null&&!nums.equals("")){
						json.put("zxfperson", nums);
					}
				}else{
					json.put("zxfperson", "0");
				}
				if(zxfCjNum!=null){
					String nums=zxfCjNum.getString(map.get("startTime"));
					if(nums!=null&&!nums.equals("")){
						json.put("zxfcjperson", nums);
					}
				}else{
					json.put("zxfcjperson", "0");
				}
				if(Integer.parseInt(json.getString("zxfperson"))>0){
					json.put("zxfcjratio", Double.parseDouble(json.getString("zxfcjperson"))/Double.parseDouble(json.getString("zxfperson"))+"");
				}else{
					json.put("zxfcjratio", "0");
				}
				if(zxfPaymoney!=null&&zxfPaymoney.size()>paymoneyMark3){
					//存在数据
					JSONObject nums=zxfPaymoney.get(paymoneyMark3);
					if(nums.getString("日").equals(Integer.parseInt(map.get("startTime").substring(8,10))+"")){
						String paymoney=nums.getString("paymoney");
						if(paymoney!=null&&!paymoney.equals("")){
							json.put("zxfpaymoney", paymoney);
						}
						String jcfmoney=nums.getString("jcfmoney");
						if(jcfmoney!=null&&!jcfmoney.equals("")){
							json.put("zxfjcfmoney", jcfmoney);
						}
						String yjjmoney=nums.getString("yjjmoney");
						if(yjjmoney!=null&&!yjjmoney.equals("")){
							json.put("zxfyjjmoney", yjjmoney);
						}
						paymoneyMark3+=1;
					}else{
						json.put("zxfpaymoney", "0.00");
						json.put("zxfjcfmoney", "0.00");
						json.put("zxfyjjmoney", "0.00");
					}
				}else{
					json.put("zxfpaymoney", "0.00");
					json.put("zxfjcfmoney", "0.00");
					json.put("zxfyjjmoney", "0.00");
				}
				if(Cmoney!=null&&Cmoney.size()>cmoneyMark4){
					//存在数据
					JSONObject nums=Cmoney.get(cmoneyMark4);
					if(nums.getString("日").equals(Integer.parseInt(map.get("startTime").substring(8,10))+"")&&nums.getString("type").equals("充值")){
						String cmoney=nums.getString("cmoney");
						if(cmoney!=null&&!cmoney.equals("")){
							json.put("cmoney", cmoney);
						}
						cmoneyMark4+=1;
					}else{
						json.put("cmoney", "0.00");
					}
					if(cmoneyMark4<Cmoney.size()){
						//存在数据
						JSONObject nums1=Cmoney.get(cmoneyMark4);
						if(nums1.getString("日").equals(Integer.parseInt(map.get("startTime").substring(8,10))+"")&&nums1.getString("type").equals("退费")){
							String cmoney=nums1.getString("cmoney");
							if(cmoney!=null&&!cmoney.equals("")){
								json.put("tcmoney", cmoney);
							}
							cmoneyMark4+=1;
						}else{
							json.put("tcmoney", "0.00");
						}
					}else{
						json.put("tcmoney", "0.00");
					}
				}else{
					json.put("cmoney", "0.00");
					json.put("tcmoney", "0.00");
				}
				if(Drugsmoney!=null&&Drugsmoney.size()>drugsmoneyMark5){
					//存在数据
					JSONObject nums=Drugsmoney.get(drugsmoneyMark5);
					if(nums.getString("日").equals(Integer.parseInt(map.get("startTime").substring(8,10))+"")){
						String drugsmoney=nums.getString("drugsmoney");
						if(drugsmoney!=null&&!drugsmoney.equals("")){
							json.put("drugsmoney", drugsmoney);
						}
						drugsmoneyMark5+=1;
					}else{
						json.put("drugsmoney", "0.00");
					}
				}else{
					json.put("drugsmoney", "0.00");
				}
				json.put("xmpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))));
				//json.put("zjpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))+Double.parseDouble(json.getString("cmoney"))));
				hjczAllNums+=Integer.parseInt(json.getString("czperson"));
				hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
				hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
				hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
				hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
				hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
				hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
				hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
				hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
				hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
				hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
				hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
				hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
				hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
				hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
				hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
				hjCmoney+=Double.parseDouble(json.getString("cmoney"));
				hjTcmoney+=Double.parseDouble(json.getString("tcmoney"));
				list.add(json);
				
				//合计
				JSONObject jsonobject=new JSONObject();
				jsonobject.put("month", map.get("startTime").substring(0,4)+"年"+map.get("startTime").substring(5,7)+"月"+map.get("startTime").substring(8,10)+"日");
				jsonobject.put("name", "合计");
				jsonobject.put("czperson", hjczAllNums+"");
				jsonobject.put("czcjperson", hjczCjNum+"");
				NumberFormat nf1 = NumberFormat.getInstance();
				nf1.setGroupingUsed(false);
				if(hjczPaymoney>0){
					jsonobject.put("czpaymoney",nf1.format(hjczPaymoney));
				}else{
					jsonobject.put("czpaymoney", "0.00");
				}
				if(hjczJcfmoney>0){
					jsonobject.put("czjcfmoney",nf1.format(hjczJcfmoney));
				}else{
					jsonobject.put("czjcfmoney", "0.00");
				}
				if(hjczYjjmoney>0){
					jsonobject.put("czyjjmoney",nf1.format(hjczYjjmoney));
				}else{
					jsonobject.put("czyjjmoney", "0.00");
				}
				jsonobject.put("fzperson", hjfzAllNums+"");
				jsonobject.put("fzcjperson", hjfzCjNum+"");
				if(hjfzPaymoney>0){
					jsonobject.put("fzpaymoney", nf1.format(hjfzPaymoney));
				}else{
					jsonobject.put("fzpaymoney", "0.00");
				}
				if(hjfzJcfmoney>0){
					jsonobject.put("fzjcfmoney", nf1.format(hjfzJcfmoney));
				}else{
					jsonobject.put("fzjcfmoney", "0.00");
				}
				if(hjfzYjjmoney>0){
					jsonobject.put("fzyjjmoney", nf1.format(hjfzYjjmoney));
				}else{
					jsonobject.put("fzyjjmoney", "0.00");
				}
				jsonobject.put("zxfperson", hjzxfAllNums+"");
				jsonobject.put("zxfcjperson", hjzxfCjNum+"");
				if(hjzxfPaymoney>0){
					jsonobject.put("zxfpaymoney", nf1.format(hjzxfPaymoney));
					
				}else{
					jsonobject.put("zxfpaymoney", "0.00");
				}
				if(hjzxfYjjmoney>0){
					jsonobject.put("zxfyjjmoney", nf1.format(hjzxfYjjmoney));
					
				}else{
					jsonobject.put("zxfyjjmoney", "0.00");
				}
				if(hjzxfJcfmoney>0){
					jsonobject.put("zxfjcfmoney", nf1.format(hjzxfJcfmoney));
					
				}else{
					jsonobject.put("zxfjcfmoney", "0.00");
				}
				if(hjCmoney>0){
					jsonobject.put("cmoney", nf1.format(hjCmoney));
					
				}else{
					jsonobject.put("cmoney", "0.00");
				}
				if(hjTcmoney<0){
					jsonobject.put("tcmoney", nf1.format(hjTcmoney));
					
				}else{
					jsonobject.put("tcmoney", "0.00");
				}
				if(hjDrugsmoney>0){
					jsonobject.put("drugsmoney", nf1.format(hjDrugsmoney));
					
				}else{
					jsonobject.put("drugsmoney", "0.00");
				}
				if(hjczAllNums>0){
					jsonobject.put("czcjratio", (double)hjczCjNum/hjczAllNums+"");
				}else{
					jsonobject.put("czcjratio", "0");
				}
				if(hjfzAllNums>0){
					jsonobject.put("fzcjratio", (double)hjfzCjNum/hjfzAllNums+"");
				}else{
					jsonobject.put("fzcjratio", "0");
				}
				if(hjzxfAllNums>0){
					jsonobject.put("zxfcjratio", (double)hjzxfCjNum/hjzxfAllNums+"");
				}else{
					jsonobject.put("zxfcjratio", "0");
				}
				jsonobject.put("xmpaymoney", ""+(Double.parseDouble(jsonobject.getString("czpaymoney"))+Double.parseDouble(jsonobject.getString("fzpaymoney"))+Double.parseDouble(jsonobject.getString("zxfpaymoney"))));
				//jsonobject.put("zjpaymoney", ""+(Double.parseDouble(jsonobject.getString("czpaymoney"))+Double.parseDouble(jsonobject.getString("fzpaymoney"))+Double.parseDouble(jsonobject.getString("zxfpaymoney"))+Double.parseDouble(jsonobject.getString("cmoney"))));
				list.add(jsonobject);
				//不是同一天的判断
			}else{
				//循环每一天 第一天
				for (int i =Integer.parseInt(map.get("startday")); i <= Integer.parseInt(map.get("endday")); i++) {
					JSONObject json= new JSONObject();
					String r="";
					if(i<=9){
						r=0+""+i;
					}else{
						r=i+"";
					}
					json.put("month", map.get("year")+"年"+map.get("month")+"月"+r+"日");
					json.put("name",name);
					if(czAllNums!=null){
						String nums=czAllNums.getString(i+"日");
						if(nums!=null&&!nums.equals("")){
							json.put("czperson", nums);
						}
					}else{
						json.put("czperson", "0");
					}
					if(czCjNum!=null){
						String nums=czCjNum.getString(i+"日");
						if(nums!=null&&!nums.equals("")){
							json.put("czcjperson", nums);
						}
					}else{
						json.put("czcjperson", "0");
					}
					if(Integer.parseInt(json.getString("czperson"))>0){
						json.put("czcjratio", Double.parseDouble(json.getString("czcjperson"))/Double.parseDouble(json.getString("czperson"))+"");
					}else{
						json.put("czcjratio", "0");
					}
					if(czPaymoney!=null&&czPaymoney.size()>paymoneyMark1){
						//存在数据
						JSONObject nums=czPaymoney.get(paymoneyMark1);
						if(nums.getString("月").equals(Integer.parseInt(map.get("month"))+"")){
							if(nums.getString("日").equals(i+"")){
								String paymoney=nums.getString("paymoney");
								if(paymoney!=null&&!paymoney.equals("")){
									json.put("czpaymoney", paymoney);
								}
								String jcfmoney=nums.getString("jcfmoney");
								if(jcfmoney!=null&&!jcfmoney.equals("")){
									json.put("czjcfmoney", jcfmoney);
								}
								String yjjmoney=nums.getString("yjjmoney");
								if(yjjmoney!=null&&!yjjmoney.equals("")){
									json.put("czyjjmoney", yjjmoney);
								}
								paymoneyMark1+=1;
							}else{
								json.put("czpaymoney", "0.00");
								json.put("czjcfmoney", "0.00");
								json.put("czyjjmoney", "0.00");
							}
						}else{
							json.put("czpaymoney", "0.00");
							json.put("czjcfmoney", "0.00");
							json.put("czyjjmoney", "0.00");
						}
						
					}else{
						json.put("czpaymoney", "0.00");
						json.put("czjcfmoney", "0.00");
						json.put("czyjjmoney", "0.00");
					}
					if(fzAllNums!=null){
						String nums=fzAllNums.getString(i+"日");
						if(nums!=null&&!nums.equals("")){
							json.put("fzperson", nums);
						}
					}else{
						json.put("fzperson", "0");
					}
					if(fzCjNum!=null){
						String nums=fzCjNum.getString(i+"日");
						if(nums!=null&&!nums.equals("")){
							json.put("fzcjperson", nums);
						}
					}else{
						json.put("fzcjperson", "0");
					}
					if(Integer.parseInt(json.getString("fzperson"))>0){
						json.put("fzcjratio", Double.parseDouble(json.getString("fzcjperson"))/Double.parseDouble(json.getString("fzperson"))+"");
					}else{
						json.put("fzcjratio", "0");
					}
					if(fzPaymoney!=null&&fzPaymoney.size()>paymoneyMark2){
						//存在数据
						JSONObject nums=fzPaymoney.get(paymoneyMark2);
						if(nums.getString("月").equals(Integer.parseInt(map.get("month"))+"")){
							if(nums.getString("日").equals(i+"")){
								String paymoney=nums.getString("paymoney");
								if(paymoney!=null&&!paymoney.equals("")){
									json.put("fzpaymoney", paymoney);
								}
								String jcfmoney=nums.getString("jcfmoney");
								if(jcfmoney!=null&&!jcfmoney.equals("")){
									json.put("fzjcfmoney", jcfmoney);
								}
								String yjjmoney=nums.getString("yjjmoney");
								if(yjjmoney!=null&&!yjjmoney.equals("")){
									json.put("fzyjjmoney", yjjmoney);
								}
								paymoneyMark2+=1;
							}else{
								json.put("fzpaymoney", "0.00");
								json.put("fzjcfmoney", "0.00");
								json.put("fzyjjmoney", "0.00");
							}
						}else{
							json.put("fzpaymoney", "0.00");
							json.put("fzjcfmoney", "0.00");
							json.put("fzyjjmoney", "0.00");
						}
					}else{ 
						json.put("fzpaymoney", "0.00");
						json.put("fzjcfmoney", "0.00");
						json.put("fzyjjmoney", "0.00");
					}
					if(zxfAllNums!=null){
						String nums=zxfAllNums.getString(i+"日");
						if(nums!=null&&!nums.equals("")){
							json.put("zxfperson", nums);
						}
					}else{
						json.put("zxfperson", "0");
					}
					if(zxfCjNum!=null){
						String nums=zxfCjNum.getString(i+"日");
						if(nums!=null&&!nums.equals("")){
							json.put("zxfcjperson", nums);
						}
					}else{
						json.put("zxfcjperson", "0");
					}
					if(Integer.parseInt(json.getString("zxfperson"))>0){
						json.put("zxfcjratio", Double.parseDouble(json.getString("zxfcjperson"))/Double.parseDouble(json.getString("zxfperson"))+"");
					}else{
						json.put("zxfcjratio", "0");
					}
					if(zxfPaymoney!=null&&zxfPaymoney.size()>paymoneyMark3){
						//存在数据
						JSONObject nums=zxfPaymoney.get(paymoneyMark3);
						if(nums.getString("月").equals(Integer.parseInt(map.get("month"))+"")){
							if(nums.getString("日").equals(i+"")){
								String paymoney=nums.getString("paymoney");
								if(paymoney!=null&&!paymoney.equals("")){
									json.put("zxfpaymoney", paymoney);
								}
								String jcfmoney=nums.getString("jcfmoney");
								if(jcfmoney!=null&&!jcfmoney.equals("")){
									json.put("zxfjcfmoney", jcfmoney);
								}
								String yjjmoney=nums.getString("yjjmoney");
								if(yjjmoney!=null&&!yjjmoney.equals("")){
									json.put("zxfyjjmoney", yjjmoney);
								}
								paymoneyMark3+=1;
							}else{
								json.put("zxfpaymoney", "0.00");
								json.put("zxfjcfmoney", "0.00");
								json.put("zxfyjjmoney", "0.00");
							}
						}else{
							json.put("zxfpaymoney", "0.00");
							json.put("zxfjcfmoney", "0.00");
							json.put("zxfyjjmoney", "0.00");
						}
					}else{
						json.put("zxfpaymoney", "0.00");
						json.put("zxfjcfmoney", "0.00");
						json.put("zxfyjjmoney", "0.00");
					}
					if(Cmoney!=null&&Cmoney.size()>cmoneyMark4){
						//存在数据
						JSONObject nums=Cmoney.get(cmoneyMark4);
						if(nums.getString("月").equals(Integer.parseInt(map.get("month"))+"")){
							if(nums.getString("日").equals(i+"")&&nums.getString("type").equals("充值")){
								String cmoney=nums.getString("cmoney");
								if(cmoney!=null&&!cmoney.equals("")){
									json.put("cmoney", cmoney);
								}
								cmoneyMark4+=1;
							}else{
								json.put("cmoney", "0.00");
							}
							if(cmoneyMark4<Cmoney.size()){
								//存在数据
								JSONObject nums1=Cmoney.get(cmoneyMark4);
								if(nums1.getString("日").equals(i+"")&&nums1.getString("type").equals("退费")){
									String cmoney=nums1.getString("cmoney");
									if(cmoney!=null&&!cmoney.equals("")){
										json.put("tcmoney", cmoney);
									}
									cmoneyMark4+=1;
								}else{
									json.put("tcmoney", "0.00");
								}
							}else{
								json.put("tcmoney", "0.00");
							}
						}else{
							json.put("cmoney", "0.00");
							json.put("tcmoney", "0.00");
						}
						
					}else{
						json.put("cmoney", "0.00");
						json.put("tcmoney", "0.00");
					}
					if(Drugsmoney!=null&&Drugsmoney.size()>drugsmoneyMark5){
						//存在数据
						JSONObject nums=Drugsmoney.get(drugsmoneyMark5);
						if(nums.getString("月").equals(Integer.parseInt(map.get("month"))+"")){
							if(nums.getString("日").equals(i+"")){
								String drugsmoney=nums.getString("drugsmoney");
								if(drugsmoney!=null&&!drugsmoney.equals("")){
									json.put("drugsmoney", drugsmoney);
								}
								drugsmoneyMark5+=1;
							}else{
								json.put("drugsmoney", "0.00");
							}
						}else{
							json.put("drugsmoney", "0.00");
						}
					}else{
						json.put("drugsmoney", "0.00");
					}
					json.put("xmpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))));
					//json.put("zjpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))+Double.parseDouble(json.getString("cmoney"))));
					hjczAllNums+=Integer.parseInt(json.getString("czperson"));
					hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
					hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
					hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
					hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
					hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
					hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
					hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
					hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
					hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
					hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
					hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
					hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
					hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
					hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
					hjCmoney+=Double.parseDouble(json.getString("cmoney"));
					hjTcmoney+=Double.parseDouble(json.getString("tcmoney"));
					hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
					list.add(json);
				}
				//合计
				JSONObject jsonobject=new JSONObject();
				jsonobject.put("month", map.get("year")+"年"+map.get("month")+"月");
				jsonobject.put("name", "合计");
				jsonobject.put("czperson", hjczAllNums+"");
				jsonobject.put("czcjperson", hjczCjNum+"");
				NumberFormat nf1 = NumberFormat.getInstance();
				nf1.setGroupingUsed(false);
				if(hjczPaymoney>0){
					jsonobject.put("czpaymoney",nf1.format(hjczPaymoney));
				}else{
					jsonobject.put("czpaymoney", "0.00");
				}
				if(hjczJcfmoney>0){
					jsonobject.put("czjcfmoney",nf1.format(hjczJcfmoney));
				}else{
					jsonobject.put("czjcfmoney", "0.00");
				}
				if(hjczYjjmoney>0){
					jsonobject.put("czyjjmoney",nf1.format(hjczYjjmoney));
				}else{
					jsonobject.put("czyjjmoney", "0.00");
				}
				jsonobject.put("fzperson", hjfzAllNums+"");
				jsonobject.put("fzcjperson", hjfzCjNum+"");
				if(hjfzPaymoney>0){
					jsonobject.put("fzpaymoney", nf1.format(hjfzPaymoney));
				}else{
					jsonobject.put("fzpaymoney", "0.00");
				}
				if(hjfzJcfmoney>0){
					jsonobject.put("fzjcfmoney", nf1.format(hjfzJcfmoney));
				}else{
					jsonobject.put("fzjcfmoney", "0.00");
				}
				if(hjfzYjjmoney>0){
					jsonobject.put("fzyjjmoney", nf1.format(hjfzYjjmoney));
				}else{
					jsonobject.put("fzyjjmoney", "0.00");
				}
				jsonobject.put("zxfperson", hjzxfAllNums+"");
				jsonobject.put("zxfcjperson", hjzxfCjNum+"");
				if(hjzxfPaymoney>0){
					jsonobject.put("zxfpaymoney", nf1.format(hjzxfPaymoney));
					
				}else{
					jsonobject.put("zxfpaymoney", "0.00");
				}
				if(hjzxfJcfmoney>0){
					jsonobject.put("zxfjcfmoney", nf1.format(hjzxfJcfmoney));
					
				}else{
					jsonobject.put("zxfjcfmoney", "0.00");
				}
				if(hjzxfYjjmoney>0){
					jsonobject.put("zxfyjjmoney", nf1.format(hjzxfYjjmoney));
					
				}else{
					jsonobject.put("zxfyjjmoney", "0.00");
				}
				if(hjCmoney>0){
					jsonobject.put("cmoney", nf1.format(hjCmoney));
					
				}else{
					jsonobject.put("cmoney", "0.00");
				}
				if(hjTcmoney<0){
					jsonobject.put("tcmoney", nf1.format(hjTcmoney));
					
				}else{
					jsonobject.put("tcmoney", "0.00");
				}
				if(hjDrugsmoney>0){
					jsonobject.put("drugsmoney", nf1.format(hjDrugsmoney));
					
				}else{
					jsonobject.put("drugsmoney", "0.00");
				}
				if(hjczAllNums>0){
					jsonobject.put("czcjratio", (double)hjczCjNum/hjczAllNums+"");
				}else{
					jsonobject.put("czcjratio", "0");
				}
				if(hjfzAllNums>0){
					jsonobject.put("fzcjratio", (double)hjfzCjNum/hjfzAllNums+"");
				}else{
					jsonobject.put("fzcjratio", "0");
				}
				if(hjzxfAllNums>0){
					jsonobject.put("zxfcjratio", (double)hjzxfCjNum/hjzxfAllNums+"");
				}else{
					jsonobject.put("zxfcjratio", "0");
				}
				jsonobject.put("xmpaymoney", ""+(Double.parseDouble(jsonobject.getString("czpaymoney"))+Double.parseDouble(jsonobject.getString("fzpaymoney"))+Double.parseDouble(jsonobject.getString("zxfpaymoney"))));
				//jsonobject.put("zjpaymoney", ""+(Double.parseDouble(jsonobject.getString("czpaymoney"))+Double.parseDouble(jsonobject.getString("fzpaymoney"))+Double.parseDouble(jsonobject.getString("zxfpaymoney"))+Double.parseDouble(jsonobject.getString("cmoney"))));
				list.add(jsonobject);
			}
			
		}
		
		return list;
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<JSONObject> findAllCJStatisticsByYear(HttpServletRequest request, Map<String, String> map) throws Exception {
		//有按钮标识和all 查询所有部门人员id
		List<JSONObject> personlist = new ArrayList<JSONObject>();
		String name="";
		String askpersonId="";
		if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("all")&&map.get("personId").equals("all")){
			personlist=sysDeptPrivDao.findPersonSeqIdByButtonName(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < personlist.size(); i++) {
				if(i==personlist.size()-1){
					str.append("r.askperson=\'"+personlist.get(i).getString("seqid")+"\'");
				}else{
					str.append("r.askperson=\'"+personlist.get(i).getString("seqid")+"\' or ");
				}
			}
			map.put("askperson", str.toString());
			//传入部门id 查询部门内所有人员
		}else if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&!map.get("deptCategory").equals("all")&&map.get("personId").equals("all")){
			//根据部门id查询所有人员数据
			map.put("deptId", "\'"+map.get("deptCategory")+"\'");
			personlist = sysDeptPrivDao.findPersonByDeptId(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < personlist.size(); i++) {
				if(i==personlist.size()-1){
					str.append("r.askperson=\'"+personlist.get(i).getString("seqid")+"\'");
				}else{
					str.append("r.askperson=\'"+personlist.get(i).getString("seqid")+"\' or ");
				}
			}
			map.put("askperson", str.toString());
			//传入人员id 查询个人数据
		}else if(!map.get("personId").equals("all")){
			//查询部门内个人数据
			Map<String, String> map1=new HashMap<String,String>();
			map1.put("seqId", map.get("personId"));
			YZPerson yzPerson = sysDeptPrivDao.findPersonBySeqId(map1);
			name=yzPerson.getUserName();
			askpersonId=yzPerson.getSeqId();
			map.put("askperson", "r.askperson=\'"+map.get("personId")+"\'");
		}
		String czseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
		map.put("recesort", "\'"+czseqId+"\'");
		//查询初诊人数
		List<JSONObject> czAllNums=analysisDao.findAllQuantityByAskpersonAndYear(map);
		//查询初诊成交人数
		List<JSONObject> czCjNum=analysisDao.findAllCJQuantityByAskpersonAndYear(map);
		//查询初诊业绩
		List<JSONObject> czPaymoney=analysisDao.findAllPaymoneyByYear(map);
		// 获取复诊编码
		String fzseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
		map.put("recesort", "\'"+fzseqId+"\'");
		//查询复诊人数
		List<JSONObject> fzAllNums=analysisDao.findAllQuantityByAskpersonAndYear(map);
		//查询复诊成交人数
		List<JSONObject> fzCjNum=analysisDao.findAllCJQuantityByAskpersonAndYear(map);
		//查询复诊业绩 项目业绩 检查费和不含预交金项目业绩
		List<JSONObject> fzPaymoney=analysisDao.findAllPaymoneyByYear(map);
		//查询药费
		List<JSONObject> Drugsmoney=analysisDao.findAllDrugsmoneyByYear(map);
		// 获取再消费编码
		String zxfseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
		// 获取复查编码
		String fcseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);			
		map.put("recesort", "\'"+zxfseqId+"\',\'"+fcseqId+"\'");
		//查询再消费人数
		List<JSONObject> zxfAllNums=analysisDao.findAllQuantityByAskpersonAndYear(map);
		//查询再消费成交人数
		map.put("recesort", "\'"+zxfseqId+"\'");
		List<JSONObject> zxfCjNum=analysisDao.findAllCJQuantityByAskpersonAndYear(map);
		//查询再消费业绩
		List<JSONObject> zxfPaymoney=analysisDao.findAllPaymoneyByYear(map);
		//查询预交金
		List<JSONObject> Cmoney=analysisDao.findAllCmoneyByYear(map);
		String startyear=map.get("startyear");
		String endyear=map.get("endyear");
		List<JSONObject> list= new ArrayList<JSONObject>();
		//合计
		int hjczAllNums=0;
		int hjczCjNum=0;
		double hjczPaymoney=0;
		double hjczJcfmoney=0;
		double hjczYjjmoney=0;
		int hjfzAllNums=0;
		int hjfzCjNum=0;
		double hjfzPaymoney=0;
		double hjfzJcfmoney=0;
		double hjfzYjjmoney=0;
		int hjzxfAllNums=0;
		int hjzxfCjNum=0;
		double hjzxfPaymoney=0;
		double hjzxfJcfmoney=0;
		double hjzxfYjjmoney=0;
		double hjDrugsmoney=0;
		double hjcmoney=0;
		double hjtcmoney=0;
		if(name.equals("")){
			for (JSONObject jsonObject : personlist) {
				//人数和业绩判断标识
				//初诊
				int a=0;
				int b=0;
				int c=0;
				//复诊
				int d=0;
				int e=0;
				int f=0;
				//再消费
				int g=0;
				int h=0;
				int j=0;
				//预交金
				int k1=0;
				int k2=0;
				//药费
				int l=0;
				JSONObject json = new JSONObject();
				if(!startyear.equals(endyear)){
					json.put("month", "-");
				}else{
					json.put("month", startyear+"年");
				}
				json.put("name", jsonObject.getString("username"));
				//初诊
				if(czAllNums.size()>0){
					for (JSONObject jsonObject2 : czAllNums) {
						if(jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))){
							a=1;
							json.put("czperson", jsonObject2.getString("nums"));
						}
					}
				}
				if(a==0){
					json.put("czperson", "0");
				}
				if(czCjNum.size()>0){
					for (JSONObject jsonObject2 : czCjNum) {
						if(jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))){
							b=1;
							json.put("czcjperson", jsonObject2.getString("nums"));
						}
					}
				}
				if(b==0){
					json.put("czcjperson", "0");
				}
				
				if(Integer.parseInt(json.getString("czperson"))>0){
					json.put("czcjratio", Double.parseDouble(json.getString("czcjperson"))/Double.parseDouble(json.getString("czperson"))+"");
				}else{
					json.put("czcjratio", "0");
				}
				
				if(czPaymoney.size()>0){
					for (JSONObject jsonObject2 : czPaymoney) {
						if(jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))){
							c=1;
							json.put("czpaymoney", jsonObject2.getString("paymoney"));
							json.put("czjcfmoney", jsonObject2.getString("jcfmoney"));
							json.put("czyjjmoney", jsonObject2.getString("yjjmoney"));
						}
					}
				}
				if(c==0){
					json.put("czpaymoney", "0.00");
					json.put("czjcfmoney", "0.00");
					json.put("czyjjmoney", "0.00");
				}
				//复诊
				if(fzAllNums.size()>0){
					for (JSONObject jsonObject2 : fzAllNums) {
						if(jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))){
							d=1;
							json.put("fzperson", jsonObject2.getString("nums"));
						}
					}
				}
				if(d==0){
					json.put("fzperson", "0");
				}
				if(fzCjNum.size()>0){
					for (JSONObject jsonObject2 : fzCjNum) {
						if(jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))){
							e=1;
							json.put("fzcjperson", jsonObject2.getString("nums"));
						}
					}
				}
				if(e==0){
					json.put("fzcjperson", "0");
				}
				if(Integer.parseInt(json.getString("fzperson"))>0){
					json.put("fzcjratio", Double.parseDouble(json.getString("fzcjperson"))/Double.parseDouble(json.getString("fzperson"))+"");
				}else{
					json.put("fzcjratio", "0");
				}
				if(fzPaymoney.size()>0){
					for (JSONObject jsonObject2 : fzPaymoney) {
						if(jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))){
							f=1;
							json.put("fzpaymoney", jsonObject2.getString("paymoney"));
							json.put("fzjcfmoney", jsonObject2.getString("jcfmoney"));
							json.put("fzyjjmoney", jsonObject2.getString("yjjmoney"));
						}
					}
				}
				if(f==0){
					json.put("fzpaymoney", "0.00");
					json.put("fzjcfmoney", "0.00");
					json.put("fzyjjmoney", "0.00");
				}
				//再消费
				if(zxfAllNums.size()>0){
					for (JSONObject jsonObject2 : zxfAllNums) {
						if(jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))){
							g=1;
							json.put("zxfperson", jsonObject2.getString("nums"));
						}
					}
				}
				if(g==0){
					json.put("zxfperson", "0");
				}
				if(zxfCjNum.size()>0){
					for (JSONObject jsonObject2 : zxfCjNum) {
						if(jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))){
							h=1;
							json.put("zxfcjperson", jsonObject2.getString("nums"));
						}
					}
				}
				if(h==0){
					json.put("zxfcjperson", "0");
				}
				if(Integer.parseInt(json.getString("zxfperson"))>0){
					json.put("zxfcjratio", Double.parseDouble(json.getString("zxfcjperson"))/Double.parseDouble(json.getString("zxfperson"))+"");
				}else{
					json.put("zxfcjratio", "0");
				}
				if(zxfPaymoney.size()>0){
					for (JSONObject jsonObject2 : zxfPaymoney) {
						if(jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))){
							j=1;
							json.put("zxfpaymoney", jsonObject2.getString("paymoney"));
							json.put("zxfjcfmoney", jsonObject2.getString("jcfmoney"));
							json.put("zxfyjjmoney", jsonObject2.getString("yjjmoney"));
						}
					}
				}
				if(j==0){
					json.put("zxfpaymoney", "0.00");
					json.put("zxfjcfmoney", "0.00");
					json.put("zxfyjjmoney", "0.00");
				}
				if(Cmoney.size()>0){
					for (JSONObject jsonObject2 : Cmoney) {
						if(jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))){
							if(jsonObject2.getString("type").equals("充值")){
								k1=1;
								json.put("cmoney", jsonObject2.getString("cmoney"));	
							}else{
								k2=1;
								json.put("tcmoney", jsonObject2.getString("cmoney"));	
							}
						}
					}
				}
				if(k1==0){
					json.put("cmoney", "0.00");
				}
				if(k2==0){
					json.put("tcmoney", "0.00");
					
				}
				if(Drugsmoney.size()>0){
					for (JSONObject jsonObject2 : Drugsmoney) {
						if(jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))){
							l=1;
							json.put("drugsmoney", jsonObject2.getString("drugsmoney"));
						}
					}
				}
				if(l==0){
					json.put("drugsmoney", "0.00");
				}
				json.put("xmpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))));
				//json.put("zjpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))+Double.parseDouble(json.getString("cmoney"))));
				hjczAllNums+=Integer.parseInt(json.getString("czperson"));
				hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
				hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
				hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
				hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
				hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
				hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
				hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
				hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
				hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
				hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
				hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
				hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
				hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
				hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
				hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
				hjcmoney+=Double.parseDouble(json.getString("cmoney"));
				hjtcmoney+=Double.parseDouble(json.getString("tcmoney"));
				list.add(json);
			}
		}else{
				JSONObject json = new JSONObject();
				if(startyear.equals(endyear)){
					json.put("month", "-");
				}else{
					json.put("month", startyear+"年");
				}
				json.put("name", name);
				//初诊
				if(czAllNums.size()>0){
					for (JSONObject jsonObject2 : czAllNums) {
						if(askpersonId.equals(jsonObject2.getString("seqid"))){
							json.put("czperson", jsonObject2.getString("nums"));
						}else{
							json.put("czperson", "0");
						}
					}
				}else{
					json.put("czperson", "0");
				}
				if(czCjNum.size()>0){
					for (JSONObject jsonObject2 : czCjNum) {
						if(askpersonId.equals(jsonObject2.getString("seqid"))){
							json.put("czcjperson", jsonObject2.getString("nums"));
						}else{
							json.put("czcjperson", "0");
						}
					}
				}else{
					json.put("czcjperson", "0");
				}
				
				if(Integer.parseInt(json.getString("czperson"))>0){
					json.put("czcjratio", Double.parseDouble(json.getString("czcjperson"))/Double.parseDouble(json.getString("czperson"))+"");
				}else{
					json.put("czcjratio", "0");
				}
				
				if(czPaymoney.size()>0){
					for (JSONObject jsonObject2 : czPaymoney) {
						if(askpersonId.equals(jsonObject2.getString("askperson"))){
							json.put("czpaymoney", jsonObject2.getString("paymoney"));
							json.put("czjcfmoney", jsonObject2.getString("jcfmoney"));
							json.put("czyjjmoney", jsonObject2.getString("yjjmoney"));
						}else{
							json.put("czpaymoney", "0.00");
							json.put("czjcfmoney", "0.00");
							json.put("czyjjmoney", "0.00");
						}
					}
				}else{
					json.put("czpaymoney", "0.00");
					json.put("czjcfmoney", "0.00");
					json.put("czyjjmoney", "0.00");
				}
				
				//复诊
				if(fzAllNums.size()>0){
					for (JSONObject jsonObject2 : fzAllNums) {
						if(askpersonId.equals(jsonObject2.getString("seqid"))){
							json.put("fzperson", jsonObject2.getString("nums"));
						}else{
							json.put("fzperson", "0");
						}
					}
				}else{
					json.put("fzperson", "0");
				}
				if(fzCjNum.size()>0){
					for (JSONObject jsonObject2 : fzCjNum) {
						if(askpersonId.equals(jsonObject2.getString("seqid"))){
							json.put("fzcjperson", jsonObject2.getString("nums"));
						}else{
							json.put("fzcjperson", "0");
						}
					}
				}else{
					json.put("fzcjperson", "0");
				}
				if(Integer.parseInt(json.getString("fzperson"))>0){
					json.put("fzcjratio", Double.parseDouble(json.getString("fzcjperson"))/Double.parseDouble(json.getString("fzperson"))+"");
				}else{
					json.put("fzcjratio", "0");
				}
				if(fzPaymoney.size()>0){
					for (JSONObject jsonObject2 : fzPaymoney) {
						if(askpersonId.equals(jsonObject2.getString("askperson"))){
							json.put("fzpaymoney", jsonObject2.getString("paymoney"));
							json.put("fzjcfmoney", jsonObject2.getString("jcfmoney"));
							json.put("fzyjjmoney", jsonObject2.getString("yjjmoney"));
						}else{
							json.put("fzpaymoney", "0.00");
							json.put("fzjcfmoney", "0.00");
							json.put("fzyjjmoney", "0.00");
						}
					}
				}else{
					json.put("fzpaymoney", "0.00");
					json.put("fzjcfmoney", "0.00");
					json.put("fzyjjmoney", "0.00");
				}
				//再消费
				if(zxfAllNums.size()>0){
					for (JSONObject jsonObject2 : zxfAllNums) {
						if(askpersonId.equals(jsonObject2.getString("seqid"))){
							json.put("zxfperson", jsonObject2.getString("nums"));
						}else{
							json.put("zxfperson", "0");
						}
					}
				}else{
					json.put("zxfperson", "0");
				}
				if(zxfCjNum.size()>0){
					for (JSONObject jsonObject2 : zxfCjNum) {
						if(askpersonId.equals(jsonObject2.getString("seqid"))){
							json.put("zxfcjperson", jsonObject2.getString("nums"));
						}else{
							json.put("zxfcjperson", "0");
						}
					}
				}else{
					json.put("zxfcjperson", "0");
				}
				if(Integer.parseInt(json.getString("zxfperson"))>0){
					json.put("zxfcjratio", Double.parseDouble(json.getString("zxfcjperson"))/Double.parseDouble(json.getString("zxfperson"))+"");
				}else{
					json.put("zxfcjratio", "0");
				}
				if(zxfPaymoney.size()>0){
					for (JSONObject jsonObject2 : zxfPaymoney) {
						if(askpersonId.equals(jsonObject2.getString("askperson"))){
							json.put("zxfpaymoney", jsonObject2.getString("paymoney"));
							json.put("zxfjcfmoney", jsonObject2.getString("jcfmoney"));
							json.put("zxfyjjmoney", jsonObject2.getString("yjjmoney"));
						}else{
							json.put("zxfpaymoney", "0.00");
							json.put("zxfjcfmoney", "0.00");
							json.put("zxfyjjmoney", "0.00");
						}
					}
				}else{
					json.put("zxfpaymoney", "0.00");
					json.put("zxfjcfmoney", "0.00");
					json.put("zxfyjjmoney", "0.00");
				}
				if(Cmoney.size()>0){
					for (JSONObject jsonObject2 : Cmoney) {
						if(askpersonId.equals(jsonObject2.getString("askperson"))){
							if(jsonObject2.getString("type").equals("充值")){
								json.put("cmoney", jsonObject2.getString("cmoney"));
								
							}
						}else{
							json.put("cmoney", "0.00");
						}
					}
					for (JSONObject jsonObject2 : Cmoney) {
						if(askpersonId.equals(jsonObject2.getString("askperson"))){
							if(jsonObject2.getString("type").equals("退费")){
								json.put("tcmoney", jsonObject2.getString("cmoney"));
								
							}
						}else{
							json.put("tcmoney", "0.00");
						}
					}
				}else{
					json.put("cmoney", "0.00");
					json.put("tcmoney", "0.00");
				}
				if(Drugsmoney.size()>0){
					for (JSONObject jsonObject2 : Drugsmoney) {
						if(askpersonId.equals(jsonObject2.getString("askperson"))){
							json.put("drugsmoney", jsonObject2.getString("drugsmoney"));
								
						}else{
							json.put("drugsmoney", "0.00");
						}
					}
				}else{
					json.put("drugsmoney", "0.00");
				}
				json.put("xmpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))));
				//json.put("zjpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))+Double.parseDouble(json.getString("cmoney"))));
				hjczAllNums+=Integer.parseInt(json.getString("czperson"));
				hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
				hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
				hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
				hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
				hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
				hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
				hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
				hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
				hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
				hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
				hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
				hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
				hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
				hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
				hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
				hjcmoney+=Double.parseDouble(json.getString("cmoney"));
				hjtcmoney+=Double.parseDouble(json.getString("tcmoney"));
				list.add(json);
			}
			JSONObject jsonobject=new JSONObject();
			if(startyear.equals(endyear)){
				jsonobject.put("month", "-");
			}else{
				jsonobject.put("month", startyear+"年");
			}
			jsonobject.put("name", "合计");
			jsonobject.put("czperson", hjczAllNums+"");
			jsonobject.put("czcjperson", hjczCjNum+"");
			NumberFormat nf1 = NumberFormat.getInstance();
			nf1.setGroupingUsed(false);
			if(hjczPaymoney>0){
				jsonobject.put("czpaymoney",nf1.format(hjczPaymoney));
			}else{
				jsonobject.put("czpaymoney", "0.00");
			}
			if(hjczJcfmoney>0){
				jsonobject.put("czjcfmoney",nf1.format(hjczJcfmoney));
			}else{
				jsonobject.put("czjcfmoney", "0.00");
			}
			if(hjczYjjmoney>0){
				jsonobject.put("czyjjmoney",nf1.format(hjczYjjmoney));
			}else{
				jsonobject.put("czyjjmoney", "0.00");
			}
			jsonobject.put("fzperson", hjfzAllNums+"");
			jsonobject.put("fzcjperson", hjfzCjNum+"");
			if(hjfzPaymoney>0){
				jsonobject.put("fzpaymoney", nf1.format(hjfzPaymoney));
			}else{
				jsonobject.put("fzpaymoney", "0.00");
			}
			if(hjfzJcfmoney>0){
				jsonobject.put("fzjcfmoney", nf1.format(hjfzJcfmoney));
			}else{
				jsonobject.put("fzjcfmoney", "0.00");
			}
			if(hjfzYjjmoney>0){
				jsonobject.put("fzyjjmoney", nf1.format(hjfzYjjmoney));
			}else{
				jsonobject.put("fzyjjmoney", "0.00");
			}
			jsonobject.put("zxfperson", hjzxfAllNums+"");
			jsonobject.put("zxfcjperson", hjzxfCjNum+"");
			if(hjzxfPaymoney>0){
				jsonobject.put("zxfpaymoney", nf1.format(hjzxfPaymoney));
				
			}else{
				jsonobject.put("zxfpaymoney", "0.00");
			}
			if(hjzxfJcfmoney>0){
				jsonobject.put("zxfjcfmoney", nf1.format(hjzxfJcfmoney));
				
			}else{
				jsonobject.put("zxfjcfmoney", "0.00");
			}
			if(hjzxfYjjmoney>0){
				jsonobject.put("zxfyjjmoney", nf1.format(hjzxfYjjmoney));
				
			}else{
				jsonobject.put("zxfyjjmoney", "0.00");
			}
			if(hjcmoney>0){
				jsonobject.put("cmoney", nf1.format(hjcmoney));
				
			}else{
				jsonobject.put("cmoney", "0.00");
			}
			if(hjtcmoney<0){
				jsonobject.put("tcmoney", nf1.format(hjtcmoney));
				
			}else{
				jsonobject.put("tcmoney", "0.00");
			}
			if(hjDrugsmoney>0){
				jsonobject.put("drugsmoney", nf1.format(hjDrugsmoney));
				
			}else{
				jsonobject.put("drugsmoney", "0.00");
			}
			if(hjczAllNums>0){
				jsonobject.put("czcjratio", (double)hjczCjNum/hjczAllNums+"");
			}else{
				jsonobject.put("czcjratio", "0");
			}
			if(hjfzAllNums>0){
				jsonobject.put("fzcjratio", (double)hjfzCjNum/hjfzAllNums+"");
			}else{
				jsonobject.put("fzcjratio", "0");
			}
			if(hjzxfAllNums>0){
				jsonobject.put("zxfcjratio", (double)hjzxfCjNum/hjzxfAllNums+"");
			}else{
				jsonobject.put("zxfcjratio", "0");
			}
			jsonobject.put("xmpaymoney", ""+(Double.parseDouble(jsonobject.getString("czpaymoney"))+Double.parseDouble(jsonobject.getString("fzpaymoney"))+Double.parseDouble(jsonobject.getString("zxfpaymoney"))));
			//jsonobject.put("zjpaymoney", ""+(Double.parseDouble(jsonobject.getString("czpaymoney"))+Double.parseDouble(jsonobject.getString("fzpaymoney"))+Double.parseDouble(jsonobject.getString("zxfpaymoney"))+Double.parseDouble(jsonobject.getString("cmoney"))));
			list.add(jsonobject);
		return list;
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<JSONObject> findAllCJStatisticsByMonth(HttpServletRequest request, Map<String, String> map)
			throws Exception {
		//有按钮标识和all 查询所有部门人员id
		List<JSONObject> personlist = new ArrayList<JSONObject>();
		String name="";
		String askpersonId="";
		if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("all")&&map.get("personId").equals("all")){
			personlist=sysDeptPrivDao.findPersonSeqIdByButtonName(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < personlist.size(); i++) {
				if(i==personlist.size()-1){
					str.append("r.askperson=\'"+personlist.get(i).getString("seqid")+"\'");
				}else{
					str.append("r.askperson=\'"+personlist.get(i).getString("seqid")+"\' or ");
				}
			}
			map.put("askperson", str.toString());
			//传入部门id 查询部门内所有人员
		}else if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&!map.get("deptCategory").equals("all")&&map.get("personId").equals("all")){
			//根据部门id查询所有人员数据
			map.put("deptId", "\'"+map.get("deptCategory")+"\'");
			personlist = sysDeptPrivDao.findPersonByDeptId(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < personlist.size(); i++) {
				if(i==personlist.size()-1){
					str.append("r.askperson=\'"+personlist.get(i).getString("seqid")+"\'");
				}else{
					str.append("r.askperson=\'"+personlist.get(i).getString("seqid")+"\' or ");
				}
			}
			map.put("askperson", str.toString());
			//传入人员id 查询个人数据
		}else if(!map.get("personId").equals("all")){
			//查询部门内个人数据
			Map<String, String> map1=new HashMap<String,String>();
			map1.put("seqId", map.get("personId"));
			YZPerson yzPerson = sysDeptPrivDao.findPersonBySeqId(map1);
			name=yzPerson.getUserName();
			askpersonId=yzPerson.getSeqId();
			map.put("askperson", "r.askperson=\'"+map.get("personId")+"\'");
		}
		String czseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
		map.put("recesort", "\'"+czseqId+"\'");
		//查询初诊人数
		List<JSONObject> czAllNums=analysisDao.findAllQuantityByAskpersonAndMonth(map);
		//查询初诊成交人数
		List<JSONObject> czCjNum=analysisDao.findAllCJQuantityByAskpersonAndMonth(map);
		//查询初诊业绩
		List<JSONObject> czPaymoney=analysisDao.findAllPaymoneyByMonth(map);
		// 获取复诊编码
		String fzseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
		map.put("recesort", "\'"+fzseqId+"\'");
		//查询复诊人数
		List<JSONObject> fzAllNums=analysisDao.findAllQuantityByAskpersonAndMonth(map);
		//查询复诊成交人数
		List<JSONObject> fzCjNum=analysisDao.findAllCJQuantityByAskpersonAndMonth(map);
		//查询复诊业绩
		List<JSONObject> fzPaymoney=analysisDao.findAllPaymoneyByMonth(map);
		// 获取再消费编码
		String zxfseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
		// 获取复查编码
		String fcseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);			
		map.put("recesort", "\'"+zxfseqId+"\',\'"+fcseqId+"\'");
		//查询再消费人数
		List<JSONObject> zxfAllNums=analysisDao.findAllQuantityByAskpersonAndMonth(map);
		//查询再消费成交人数
		map.put("recesort", "\'"+zxfseqId+"\'");
		//查询再消费成交人数
		List<JSONObject> zxfCjNum=analysisDao.findAllCJQuantityByAskpersonAndMonth(map);
		//查询再消费业绩
		List<JSONObject> zxfPaymoney=analysisDao.findAllPaymoneyByMonth(map);
		//查询预交金
		List<JSONObject> Cmoney=analysisDao.findAllCmoneyByMonth(map);
		//查询药费
		List<JSONObject> Drugsmoney=analysisDao.findAllDrugsmoneyByMonth(map);
		List<JSONObject> list=new ArrayList<JSONObject>();
			//合计
			int hjczAllNums=0;
			int hjczCjNum=0;
			double hjczPaymoney=0;
			double hjczJcfmoney=0;
			double hjczYjjmoney=0;
			int hjfzAllNums=0;
			int hjfzCjNum=0;
			double hjfzPaymoney=0;
			double hjfzJcfmoney=0;
			double hjfzYjjmoney=0;
			int hjzxfAllNums=0;
			int hjzxfCjNum=0;
			double hjzxfPaymoney=0;
			double hjzxfJcfmoney=0;
			double hjzxfYjjmoney=0;
			double hjcmoney=0;
			double hjtcmoney=0;
			double hjDrugsmoney=0;
			if(name.equals("")){
				for (JSONObject jsonObject : personlist) {
					//人数和业绩判断标识
					//初诊
					int a=0;
					int b=0;
					int c=0;
					//复诊
					int d=0;
					int e=0;
					int f=0;
					//再消费
					int g=0;
					int h=0;
					int j=0;
					//预交金
					int k1=0;
					int k2=0;
					//药费
					int l=0;
					JSONObject json = new JSONObject();
					if(!map.get("startmonth").equals(map.get("endmonth"))){
						json.put("month", "-");
					}else{
						json.put("month", map.get("year")+"年"+map.get("startmonth")+"月");
					}
					json.put("name", jsonObject.getString("username"));
					//初诊
					if(czAllNums.size()>0){
						for (JSONObject jsonObject2 : czAllNums) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))){
								a=1;
								json.put("czperson", jsonObject2.getString("nums"));
							}
						}
					}
					if(a==0){
						json.put("czperson", "0");
					}
					if(czCjNum.size()>0){
						for (JSONObject jsonObject2 : czCjNum) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))){
								b=1;
								json.put("czcjperson", jsonObject2.getString("nums"));
							}
						}
					}
					if(b==0){
						json.put("czcjperson", "0");
					}
					
					if(Integer.parseInt(json.getString("czperson"))>0){
						json.put("czcjratio", Double.parseDouble(json.getString("czcjperson"))/Double.parseDouble(json.getString("czperson"))+"");
					}else{
						json.put("czcjratio", "0");
					}
					
					if(czPaymoney.size()>0){
						for (JSONObject jsonObject2 : czPaymoney) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))){
								c=1;
								json.put("czpaymoney", jsonObject2.getString("paymoney"));
								json.put("czjcfmoney", jsonObject2.getString("jcfmoney"));
								json.put("czyjjmoney", jsonObject2.getString("yjjmoney"));
							}
						}
					}
					if(c==0){
						json.put("czpaymoney", "0.00");
						json.put("czjcfmoney", "0.00");
						json.put("czyjjmoney", "0.00");
					}
					//复诊
					if(fzAllNums.size()>0){
						for (JSONObject jsonObject2 : fzAllNums) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))){
								d=1;
								json.put("fzperson", jsonObject2.getString("nums"));
							}
						}
					}
					if(d==0){
						json.put("fzperson", "0");
					}
					if(fzCjNum.size()>0){
						for (JSONObject jsonObject2 : fzCjNum) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))){
								e=1;
								json.put("fzcjperson", jsonObject2.getString("nums"));
							}
						}
					}
					if(e==0){
						json.put("fzcjperson", "0");
					}
					if(Integer.parseInt(json.getString("fzperson"))>0){
						json.put("fzcjratio", Double.parseDouble(json.getString("fzcjperson"))/Double.parseDouble(json.getString("fzperson"))+"");
					}else{
						json.put("fzcjratio", "0");
					}
					if(fzPaymoney.size()>0){
						for (JSONObject jsonObject2 : fzPaymoney) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))){
								f=1;
								json.put("fzpaymoney", jsonObject2.getString("paymoney"));
								json.put("fzjcfmoney", jsonObject2.getString("jcfmoney"));
								json.put("fzyjjmoney", jsonObject2.getString("yjjmoney"));
							}
						}
					}
					if(f==0){
						json.put("fzpaymoney", "0.00");
						json.put("fzjcfmoney", "0.00");
						json.put("fzyjjmoney", "0.00");
					}
					//再消费
					if(zxfAllNums.size()>0){
						for (JSONObject jsonObject2 : zxfAllNums) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))){
								g=1;
								json.put("zxfperson", jsonObject2.getString("nums"));
							}
						}
					}
					if(g==0){
						json.put("zxfperson", "0");
					}
					if(zxfCjNum.size()>0){
						for (JSONObject jsonObject2 : zxfCjNum) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))){
								h=1;
								json.put("zxfcjperson", jsonObject2.getString("nums"));
							}
						}
					}
					if(h==0){
						json.put("zxfcjperson", "0");
					}
					if(Integer.parseInt(json.getString("zxfperson"))>0){
						json.put("zxfcjratio", Double.parseDouble(json.getString("zxfcjperson"))/Double.parseDouble(json.getString("zxfperson"))+"");
					}else{
						json.put("zxfcjratio", "0");
					}
					if(zxfPaymoney.size()>0){
						for (JSONObject jsonObject2 : zxfPaymoney) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))){
								j=1;
								json.put("zxfpaymoney", jsonObject2.getString("paymoney"));
								json.put("zxfjcfmoney", jsonObject2.getString("jcfmoney"));
								json.put("zxfyjjmoney", jsonObject2.getString("yjjmoney"));
							}
						}
					}
					if(j==0){
						json.put("zxfpaymoney", "0.00");
						json.put("zxfjcfmoney", "0.00");
						json.put("zxfyjjmoney", "0.00");
					}
					if(Cmoney.size()>0){
						for (JSONObject jsonObject2 : Cmoney) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))){
								if(jsonObject2.getString("type").equals("充值")){
									k1=1;
									json.put("cmoney", jsonObject2.getString("cmoney"));
								}else{
									k2=1;
									json.put("tcmoney", jsonObject2.getString("cmoney"));
								}
							}
						}
					}
					if(k1==0){
						json.put("cmoney", "0.00");
					}
					if(k2==0){
						json.put("tcmoney","0.00");
					}
					if(Drugsmoney.size()>0){
						for (JSONObject jsonObject2 : Drugsmoney) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))){
								l=1;
								json.put("drugsmoney", jsonObject2.getString("drugsmoney"));
								
							}
						}
					}
					if(l==0){
						json.put("drugsmoney", "0.00");
					}
					json.put("xmpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))));
					//json.put("zjpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))+Double.parseDouble(json.getString("cmoney"))));
					hjczAllNums+=Integer.parseInt(json.getString("czperson"));
					hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
					hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
					hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
					hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
					hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
					hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
					hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
					hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
					hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
					hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
					hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
					hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
					hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
					hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
					hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
					hjcmoney+=Double.parseDouble(json.getString("cmoney"));
					hjtcmoney+=Double.parseDouble(json.getString("tcmoney"));
					list.add(json);
				}
			}else{
				JSONObject json = new JSONObject();
				if(!map.get("startmonth").equals(map.get("endmonth"))){
					json.put("month", "-");
				}else{
					json.put("month", map.get("year")+"年"+map.get("startmonth")+"月");
				}
				json.put("name", name);
				//初诊
				if(czAllNums.size()>0){
					for (JSONObject jsonObject2 : czAllNums) {
						if(askpersonId.equals(jsonObject2.getString("seqid"))){
							json.put("czperson", jsonObject2.getString("nums"));
						}else{
							json.put("czperson", "0");
						}
					}
				}else{
					json.put("czperson", "0");
				}
				if(czCjNum.size()>0){
					for (JSONObject jsonObject2 : czCjNum) {
						if(askpersonId.equals(jsonObject2.getString("seqid"))){
							json.put("czcjperson", jsonObject2.getString("nums"));
						}else{
							json.put("czcjperson", "0");
						}
					}
				}else{
					json.put("czcjperson", "0");
				}
				
				if(Integer.parseInt(json.getString("czperson"))>0){
					json.put("czcjratio", Double.parseDouble(json.getString("czcjperson"))/Double.parseDouble(json.getString("czperson"))+"");
				}else{
					json.put("czcjratio", "0");
				}
				
				if(czPaymoney.size()>0){
					for (JSONObject jsonObject2 : czPaymoney) {
						if(askpersonId.equals(jsonObject2.getString("askperson"))){
							json.put("czpaymoney", jsonObject2.getString("paymoney"));
							json.put("czjcfmoney", jsonObject2.getString("jcfmoney"));
							json.put("czyjjmoney", jsonObject2.getString("yjjmoney"));
						}else{
							json.put("czpaymoney", "0.00");
							json.put("czjcfmoney", "0.00");
							json.put("czyjjmoney", "0.00");
						}
					}
				}else{
					json.put("czpaymoney", "0.00");
					json.put("czjcfmoney", "0.00");
					json.put("czyjjmoney", "0.00");
				}
				
				//复诊
				if(fzAllNums.size()>0){
					for (JSONObject jsonObject2 : fzAllNums) {
						if(askpersonId.equals(jsonObject2.getString("seqid"))){
							json.put("fzperson", jsonObject2.getString("nums"));
						}else{
							json.put("fzperson", "0");
						}
					}
				}else{
					json.put("fzperson", "0");
				}
				if(fzCjNum.size()>0){
					for (JSONObject jsonObject2 : fzCjNum) {
						if(askpersonId.equals(jsonObject2.getString("seqid"))){
							json.put("fzcjperson", jsonObject2.getString("nums"));
						}else{
							json.put("fzcjperson", "0");
						}
					}
				}else{
					json.put("fzcjperson", "0");
				}
				if(Integer.parseInt(json.getString("fzperson"))>0){
					json.put("fzcjratio", Double.parseDouble(json.getString("fzcjperson"))/Double.parseDouble(json.getString("fzperson"))+"");
				}else{
					json.put("fzcjratio", "0");
				}
				if(fzPaymoney.size()>0){
					for (JSONObject jsonObject2 : fzPaymoney) {
						if(askpersonId.equals(jsonObject2.getString("askperson"))){
							json.put("fzpaymoney", jsonObject2.getString("paymoney"));
							json.put("fzjcfmoney", jsonObject2.getString("jcfmoney"));
							json.put("fzyjjmoney", jsonObject2.getString("yjjmoney"));
						}else{
							json.put("fzpaymoney", "0.00");
							json.put("fzjcfmoney", "0.00");
							json.put("fzyjjmoney", "0.00");
						}
					}
				}else{
					json.put("fzpaymoney", "0.00");
					json.put("fzjcfmoney", "0.00");
					json.put("fzyjjmoney", "0.00");
				}
				//再消费
				if(zxfAllNums.size()>0){
					for (JSONObject jsonObject2 : zxfAllNums) {
						if(askpersonId.equals(jsonObject2.getString("seqid"))){
							json.put("zxfperson", jsonObject2.getString("nums"));
						}else{
							json.put("zxfperson", "0");
						}
					}
				}else{
					json.put("zxfperson", "0");
				}
				if(zxfCjNum.size()>0){
					for (JSONObject jsonObject2 : zxfCjNum) {
						if(askpersonId.equals(jsonObject2.getString("seqid"))){
							json.put("zxfcjperson", jsonObject2.getString("nums"));
						}else{
							json.put("zxfcjperson", "0");
						}
					}
				}else{
					json.put("zxfcjperson", "0");
				}
				if(Integer.parseInt(json.getString("zxfperson"))>0){
					json.put("zxfcjratio", Double.parseDouble(json.getString("zxfcjperson"))/Double.parseDouble(json.getString("zxfperson"))+"");
				}else{
					json.put("zxfcjratio", "0");
				}
				if(zxfPaymoney.size()>0){
					for (JSONObject jsonObject2 : zxfPaymoney) {
						if(askpersonId.equals(jsonObject2.getString("askperson"))){
							json.put("zxfpaymoney", jsonObject2.getString("paymoney"));
							json.put("zxfjcfmoney", jsonObject2.getString("jcfmoney"));
							json.put("zxfyjjmoney", jsonObject2.getString("yjjmoney"));
						}else{
							json.put("zxfpaymoney", "0.00");
							json.put("zxfjcfmoney", "0.00");
							json.put("zxfyjjmoney", "0.00");
						}
					}
				}else{
					json.put("zxfpaymoney", "0.00");
					json.put("zxfjcfmoney", "0.00");
					json.put("zxfyjjmoney", "0.00");
				}
				if(Cmoney.size()>0){
					for (JSONObject jsonObject2 : Cmoney) {
						if(askpersonId.equals(jsonObject2.getString("askperson"))){
							if(jsonObject2.getString("type").equals("充值")){
								json.put("cmoney", jsonObject2.getString("cmoney"));
							}
						}else{
							json.put("cmoney", "0.00");
						}
					}
					for (JSONObject jsonObject2 : Cmoney) {
						if(askpersonId.equals(jsonObject2.getString("askperson"))){
							if(jsonObject2.getString("type").equals("退费")){
								json.put("tcmoney", jsonObject2.getString("cmoney"));
							}
						}else{
							json.put("tcmoney", "0.00");
						}
					}
				}else{
					json.put("cmoney", "0.00");
					json.put("tcmoney", "0.00");
				}
				if(Drugsmoney.size()>0){
					for (JSONObject jsonObject2 : Drugsmoney) {
						if(askpersonId.equals(jsonObject2.getString("askperson"))){
							json.put("drugsmoney", jsonObject2.getString("drugsmoney"));
							
						}else{
							json.put("drugsmoney", "0.00");
						}
					}
				}else{
					json.put("drugsmoney", "0.00");
				}
				json.put("xmpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))));
				//json.put("zjpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))+Double.parseDouble(json.getString("cmoney"))));
				hjczAllNums+=Integer.parseInt(json.getString("czperson"));
				hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
				hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
				hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
				hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
				hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
				hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
				hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
				hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
				hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
				hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
				hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
				hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
				hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
				hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
				hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
				hjcmoney+=Double.parseDouble(json.getString("cmoney"));
				hjtcmoney+=Double.parseDouble(json.getString("tcmoney"));
				list.add(json);
			}
			JSONObject jsonobject=new JSONObject();
			if(!map.get("startmonth").equals(map.get("endmonth"))){
				jsonobject.put("month", "-");
			}else{
				jsonobject.put("month", map.get("year")+"年"+map.get("startmonth")+"月");
			}
			jsonobject.put("name", "合计");
			jsonobject.put("czperson", hjczAllNums+"");
			jsonobject.put("czcjperson", hjczCjNum+"");
			NumberFormat nf1 = NumberFormat.getInstance();
			nf1.setGroupingUsed(false);
			if(hjczPaymoney>0){
				jsonobject.put("czpaymoney",nf1.format(hjczPaymoney));
			}else{
				jsonobject.put("czpaymoney", "0.00");
			}
			if(hjczJcfmoney>0){
				jsonobject.put("czjcfmoney",nf1.format(hjczJcfmoney));
			}else{
				jsonobject.put("czjcfmoney", "0.00");
			}
			if(hjczYjjmoney>0){
				jsonobject.put("czyjjmoney",nf1.format(hjczYjjmoney));
			}else{
				jsonobject.put("czyjjmoney", "0.00");
			}
			jsonobject.put("fzperson", hjfzAllNums+"");
			jsonobject.put("fzcjperson", hjfzCjNum+"");
			if(hjfzPaymoney>0){
				jsonobject.put("fzpaymoney", nf1.format(hjfzPaymoney));
			}else{
				jsonobject.put("fzpaymoney", "0.00");
			}
			if(hjfzJcfmoney>0){
				jsonobject.put("fzjcfmoney", nf1.format(hjfzJcfmoney));
			}else{
				jsonobject.put("fzjcfmoney", "0.00");
			}
			if(hjfzYjjmoney>0){
				jsonobject.put("fzyjjmoney", nf1.format(hjfzYjjmoney));
			}else{
				jsonobject.put("fzyjjmoney", "0.00");
			}
			jsonobject.put("zxfperson", hjzxfAllNums+"");
			jsonobject.put("zxfcjperson", hjzxfCjNum+"");
			if(hjzxfPaymoney>0){
				jsonobject.put("zxfpaymoney", nf1.format(hjzxfPaymoney));
				
			}else{
				jsonobject.put("zxfpaymoney", "0.00");
			}
			if(hjzxfJcfmoney>0){
				jsonobject.put("zxfjcfmoney", nf1.format(hjzxfJcfmoney));
				
			}else{
				jsonobject.put("zxfjcfmoney", "0.00");
			}
			if(hjzxfYjjmoney>0){
				jsonobject.put("zxfyjjmoney", nf1.format(hjzxfYjjmoney));
				
			}else{
				jsonobject.put("zxfyjjmoney", "0.00");
			}
			if(hjcmoney>0){
				jsonobject.put("cmoney", nf1.format(hjcmoney));
			}else{
				jsonobject.put("cmoney", "0.00");
			}
			if(hjtcmoney<0){
				jsonobject.put("tcmoney", nf1.format(hjtcmoney));
			}else{
				jsonobject.put("tcmoney", "0.00");
			}
			if(hjDrugsmoney>0){
				jsonobject.put("drugsmoney", nf1.format(hjDrugsmoney));
			}else{
				jsonobject.put("drugsmoney", "0.00");
			}
			if(hjczAllNums>0){
				jsonobject.put("czcjratio", (double)hjczCjNum/hjczAllNums+"");
			}else{
				jsonobject.put("czcjratio", "0");
			}
			if(hjfzAllNums>0){
				jsonobject.put("fzcjratio", (double)hjfzCjNum/hjfzAllNums+"");
			}else{
				jsonobject.put("fzcjratio", "0");
			}
			if(hjzxfAllNums>0){
				jsonobject.put("zxfcjratio", (double)hjzxfCjNum/hjzxfAllNums+"");
			}else{
				jsonobject.put("zxfcjratio", "0");
			}
			jsonobject.put("xmpaymoney", ""+(Double.parseDouble(jsonobject.getString("czpaymoney"))+Double.parseDouble(jsonobject.getString("fzpaymoney"))+Double.parseDouble(jsonobject.getString("zxfpaymoney"))));
			//jsonobject.put("zjpaymoney", ""+(Double.parseDouble(jsonobject.getString("czpaymoney"))+Double.parseDouble(jsonobject.getString("fzpaymoney"))+Double.parseDouble(jsonobject.getString("zxfpaymoney"))+Double.parseDouble(jsonobject.getString("cmoney"))));
			list.add(jsonobject);	
		return list;
	}
	@Override
	public List<JSONObject> findAllCJStatisticsByDay(HttpServletRequest request, Map<String, String> map)
			throws Exception {
		//有按钮标识和all 查询所有部门人员id
		List<JSONObject> personlist = new ArrayList<JSONObject>();
		String name="";
		String askpersonId="";
		if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("all")&&map.get("personId").equals("all")){
			personlist=sysDeptPrivDao.findPersonSeqIdByButtonName(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < personlist.size(); i++) {
				if(i==personlist.size()-1){
					str.append("r.askperson=\'"+personlist.get(i).getString("seqid")+"\'");
				}else{
					str.append("r.askperson=\'"+personlist.get(i).getString("seqid")+"\' or ");
				}
			}
			map.put("askperson", str.toString());
			//传入部门id 查询部门内所有人员
		}else if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&!map.get("deptCategory").equals("all")&&map.get("personId").equals("all")){
			//根据部门id查询所有人员数据
			map.put("deptId", "\'"+map.get("deptCategory")+"\'");
			personlist = sysDeptPrivDao.findPersonByDeptId(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < personlist.size(); i++) {
				if(i==personlist.size()-1){
					str.append("r.askperson=\'"+personlist.get(i).getString("seqid")+"\'");
				}else{
					str.append("r.askperson=\'"+personlist.get(i).getString("seqid")+"\' or ");
				}
			}
			map.put("askperson", str.toString());
			//传入人员id 查询个人数据
		}else if(!map.get("personId").equals("all")){
			//查询部门内个人数据
			Map<String, String> map1=new HashMap<String,String>();
			map1.put("seqId", map.get("personId"));
			YZPerson yzPerson = sysDeptPrivDao.findPersonBySeqId(map1);
			name=yzPerson.getUserName();
			askpersonId=yzPerson.getSeqId();
			map.put("askperson", "r.askperson=\'"+map.get("personId")+"\'");
		}
		String czseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
		map.put("recesort", "\'"+czseqId+"\'");
		//查询初诊人数
		List<JSONObject> czAllNums=analysisDao.findAllQuantityByAskpersonAndDay(map);
		//查询初诊成交人数
		List<JSONObject> czCjNum=analysisDao.findAllCJQuantityByAskpersonAndDay(map);
		//查询初诊业绩
		List<JSONObject> czPaymoney=analysisDao.findAllPaymoneyByDay(map);
		// 获取复诊编码
		String fzseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
		map.put("recesort", "\'"+fzseqId+"\'");
		//查询复诊人数
		List<JSONObject> fzAllNums=analysisDao.findAllQuantityByAskpersonAndDay(map);
		//查询复诊成交人数
		List<JSONObject> fzCjNum=analysisDao.findAllCJQuantityByAskpersonAndDay(map);
		//查询复诊业绩
		List<JSONObject> fzPaymoney=analysisDao.findAllPaymoneyByDay(map);
		// 获取再消费编码
		String zxfseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
		// 获取复查编码
		String fcseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);			
		map.put("recesort", "\'"+zxfseqId+"\',\'"+fcseqId+"\'");
		//查询再消费人数
		List<JSONObject> zxfAllNums=analysisDao.findAllQuantityByAskpersonAndDay(map);
		//查询再消费成交人数
		map.put("recesort", "\'"+zxfseqId+"\'");
		//查询再消费成交人数
		List<JSONObject> zxfCjNum=analysisDao.findAllCJQuantityByAskpersonAndDay(map);
		//查询再消费业绩
		List<JSONObject> zxfPaymoney=analysisDao.findAllPaymoneyByDay(map);
		//预交金
		List<JSONObject> Cmoney=analysisDao.findAllCmoneyByDay(map);
		//查询药费
		List<JSONObject> Drugsmoney=analysisDao.findAllDrugsmoneyByDay(map);
		List<JSONObject> list=new ArrayList<JSONObject>();
			//合计
			int hjczAllNums=0;
			int hjczCjNum=0;
			double hjczPaymoney=0;
			double hjczJcfmoney=0;
			double hjczYjjmoney=0;
			int hjfzAllNums=0;
			int hjfzCjNum=0;
			double hjfzPaymoney=0;
			double hjfzJcfmoney=0;
			double hjfzYjjmoney=0;
			int hjzxfAllNums=0;
			int hjzxfCjNum=0;
			double hjzxfPaymoney=0;
			double hjzxfJcfmoney=0;
			double hjzxfYjjmoney=0;
			double hjDrugsmoney=0;
			double hjcmoney=0;
			double hjtcmoney=0;
			if(name.equals("")){
				for (JSONObject jsonObject : personlist) {
					//人数和业绩判断标识
					//初诊
					int a=0;
					int b=0;
					int c=0;
					//复诊
					int d=0;
					int e=0;
					int f=0;
					//再消费
					int g=0;
					int h=0;
					int j=0;
					//预交金
					int k1=0;
					int k2=0;
					//药费
					int l=0;
					JSONObject json = new JSONObject();
					if(map.get("startday").equals(map.get("endday"))){
						json.put("month", map.get("year")+"年"+map.get("month")+"月"+map.get("startday")+"日");
						
					}else{
						json.put("month", "-");
					}
					json.put("name", jsonObject.getString("username"));
					//初诊
					if(czAllNums.size()>0){
						for (JSONObject jsonObject2 : czAllNums) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))){
								a=1;
								json.put("czperson", jsonObject2.getString("nums"));
							}
						}
					}
					if(a==0){
						json.put("czperson", "0");
					}
					if(czCjNum.size()>0){
						for (JSONObject jsonObject2 : czCjNum) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))){
								b=1;
								json.put("czcjperson", jsonObject2.getString("nums"));
							}
						}
					}
					if(b==0){
						json.put("czcjperson", "0");
					}
					
					if(Integer.parseInt(json.getString("czperson"))>0){
						json.put("czcjratio", Double.parseDouble(json.getString("czcjperson"))/Double.parseDouble(json.getString("czperson"))+"");
					}else{
						json.put("czcjratio", "0");
					}
					
					if(czPaymoney.size()>0){
						for (JSONObject jsonObject2 : czPaymoney) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))){
								c=1;
								json.put("czpaymoney", jsonObject2.getString("paymoney"));
								json.put("czjcfmoney", jsonObject2.getString("jcfmoney"));
								json.put("czyjjmoney", jsonObject2.getString("yjjmoney"));
							}
						}
					}
					if(c==0){
						json.put("czpaymoney", "0.00");
						json.put("czjcfmoney", "0.00");
						json.put("czyjjmoney", "0.00");
					}
					//复诊
					if(fzAllNums.size()>0){
						for (JSONObject jsonObject2 : fzAllNums) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))){
								d=1;
								json.put("fzperson", jsonObject2.getString("nums"));
							}
						}
					}
					if(d==0){
						json.put("fzperson", "0");
					}
					if(fzCjNum.size()>0){
						for (JSONObject jsonObject2 : fzCjNum) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))){
								e=1;
								json.put("fzcjperson", jsonObject2.getString("nums"));
							}
						}
					}
					if(e==0){
						json.put("fzcjperson", "0");
					}
					if(Integer.parseInt(json.getString("fzperson"))>0){
						json.put("fzcjratio", Double.parseDouble(json.getString("fzcjperson"))/Double.parseDouble(json.getString("fzperson"))+"");
					}else{
						json.put("fzcjratio", "0");
					}
					if(fzPaymoney.size()>0){
						for (JSONObject jsonObject2 : fzPaymoney) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))){
								f=1;
								json.put("fzpaymoney", jsonObject2.getString("paymoney"));
								json.put("fzjcfmoney", jsonObject2.getString("jcfmoney"));
								json.put("fzyjjmoney", jsonObject2.getString("yjjmoney"));
							}
						}
					}
					if(f==0){
						json.put("fzpaymoney", "0.00");
						json.put("fzjcfmoney", "0.00");
						json.put("fzyjjmoney", "0.00");
					}
					//再消费
					if(zxfAllNums.size()>0){
						for (JSONObject jsonObject2 : zxfAllNums) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))){
								g=1;
								json.put("zxfperson", jsonObject2.getString("nums"));
							}
						}
					}
					if(g==0){
						json.put("zxfperson", "0");
					}
					if(zxfCjNum.size()>0){
						for (JSONObject jsonObject2 : zxfCjNum) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))){
								h=1;
								json.put("zxfcjperson", jsonObject2.getString("nums"));
							}
						}
					}
					if(h==0){
						json.put("zxfcjperson", "0");
					}
					if(Integer.parseInt(json.getString("zxfperson"))>0){
						json.put("zxfcjratio", Double.parseDouble(json.getString("zxfcjperson"))/Double.parseDouble(json.getString("zxfperson"))+"");
					}else{
						json.put("zxfcjratio", "0");
					}
					if(zxfPaymoney.size()>0){
						for (JSONObject jsonObject2 : zxfPaymoney) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))){
								j=1;
								json.put("zxfpaymoney", jsonObject2.getString("paymoney"));
								json.put("zxfjcfmoney", jsonObject2.getString("jcfmoney"));
								json.put("zxfyjjmoney", jsonObject2.getString("yjjmoney"));
							}
						}
					}
					if(j==0){
						json.put("zxfpaymoney", "0.00");
						json.put("zxfjcfmoney", "0.00");
						json.put("zxfyjjmoney", "0.00");
					}
					if(Cmoney.size()>0){
						for (JSONObject jsonObject2 : Cmoney) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))){
								if(jsonObject2.getString("type").equals("充值")){
									k1=1;
									json.put("cmoney", jsonObject2.getString("cmoney"));
								}else{
									k2=1;
									json.put("tcmoney", jsonObject2.getString("cmoney"));
								}
							}
						}
					}
					if(k1==0){
						json.put("cmoney", "0.00");
					}
					if(k2==0){
						json.put("tcmoney","0.00");
					}
					if(Drugsmoney.size()>0){
						for (JSONObject jsonObject2 : Drugsmoney) {
							if(jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))){
								l=1;
								json.put("drugsmoney", jsonObject2.getString("drugsmoney"));
								
							}
						}
					}
					if(l==0){
						json.put("drugsmoney", "0.00");
					}
					json.put("xmpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))));
					//json.put("zjpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))+Double.parseDouble(json.getString("cmoney"))));
					hjczAllNums+=Integer.parseInt(json.getString("czperson"));
					hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
					hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
					hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
					hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
					hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
					hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
					hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
					hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
					hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
					hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
					hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
					hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
					hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
					hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
					hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
					hjcmoney+=Double.parseDouble(json.getString("cmoney"));
					hjtcmoney+=Double.parseDouble(json.getString("tcmoney"));
					list.add(json);
				}
			}else{
				JSONObject json = new JSONObject();
				if(map.get("startday").equals(map.get("endday"))){
					json.put("month", map.get("year")+"年"+map.get("month")+"月"+map.get("startday")+"日");
					
				}else{
					json.put("month", "-");
				}
				json.put("name", name);
				//初诊
				if(czAllNums.size()>0){
					for (JSONObject jsonObject2 : czAllNums) {
						if(askpersonId.equals(jsonObject2.getString("seqid"))){
							json.put("czperson", jsonObject2.getString("nums"));
						}else{
							json.put("czperson", "0");
						}
					}
				}else{
					json.put("czperson", "0");
				}
				if(czCjNum.size()>0){
					for (JSONObject jsonObject2 : czCjNum) {
						if(askpersonId.equals(jsonObject2.getString("seqid"))){
							json.put("czcjperson", jsonObject2.getString("nums"));
						}else{
							json.put("czcjperson", "0");
						}
					}
				}else{
					json.put("czcjperson", "0");
				}
				
				if(Integer.parseInt(json.getString("czperson"))>0){
					json.put("czcjratio", Double.parseDouble(json.getString("czcjperson"))/Double.parseDouble(json.getString("czperson"))+"");
				}else{
					json.put("czcjratio", "0");
				}
				
				if(czPaymoney.size()>0){
					for (JSONObject jsonObject2 : czPaymoney) {
						if(askpersonId.equals(jsonObject2.getString("askperson"))){
							json.put("czpaymoney", jsonObject2.getString("paymoney"));
							json.put("czjcfmoney", jsonObject2.getString("jcfmoney"));
							json.put("czyjjmoney", jsonObject2.getString("yjjmoney"));
						}else{
							json.put("czpaymoney", "0.00");
							json.put("czjcfmoney", "0.00");
							json.put("czyjjmoney", "0.00");
						}
					}
				}else{
					json.put("czpaymoney", "0.00");
					json.put("czjcfmoney", "0.00");
					json.put("czyjjmoney", "0.00");
				}
				
				//复诊
				if(fzAllNums.size()>0){
					for (JSONObject jsonObject2 : fzAllNums) {
						if(askpersonId.equals(jsonObject2.getString("seqid"))){
							json.put("fzperson", jsonObject2.getString("nums"));
						}else{
							json.put("fzperson", "0");
						}
					}
				}else{
					json.put("fzperson", "0");
				}
				if(fzCjNum.size()>0){
					for (JSONObject jsonObject2 : fzCjNum) {
						if(askpersonId.equals(jsonObject2.getString("seqid"))){
							json.put("fzcjperson", jsonObject2.getString("nums"));
						}else{
							json.put("fzcjperson", "0");
						}
					}
				}else{
					json.put("fzcjperson", "0");
				}
				if(Integer.parseInt(json.getString("fzperson"))>0){
					json.put("fzcjratio", Double.parseDouble(json.getString("fzcjperson"))/Double.parseDouble(json.getString("fzperson"))+"");
				}else{
					json.put("fzcjratio", "0");
				}
				if(fzPaymoney.size()>0){
					for (JSONObject jsonObject2 : fzPaymoney) {
						if(askpersonId.equals(jsonObject2.getString("askperson"))){
							json.put("fzpaymoney", jsonObject2.getString("paymoney"));
							json.put("fzjcfmoney", jsonObject2.getString("jcfmoney"));
							json.put("fzyjjmoney", jsonObject2.getString("yjjmoney"));
						}else{
							json.put("fzpaymoney", "0.00");
							json.put("fzjcfmoney", "0.00");
							json.put("fzyjjmoney", "0.00");
						}
					}
				}else{
					json.put("fzpaymoney", "0.00");
					json.put("fzjcfmoney", "0.00");
					json.put("fzyjjmoney", "0.00");
				}
				//再消费
				if(zxfAllNums.size()>0){
					for (JSONObject jsonObject2 : zxfAllNums) {
						if(askpersonId.equals(jsonObject2.getString("seqid"))){
							json.put("zxfperson", jsonObject2.getString("nums"));
						}else{
							json.put("zxfperson", "0");
						}
					}
				}else{
					json.put("zxfperson", "0");
				}
				if(zxfCjNum.size()>0){
					for (JSONObject jsonObject2 : zxfCjNum) {
						if(askpersonId.equals(jsonObject2.getString("seqid"))){
							json.put("zxfcjperson", jsonObject2.getString("nums"));
						}else{
							json.put("zxfcjperson", "0");
						}
					}
				}else{
					json.put("zxfcjperson", "0");
				}
				if(Integer.parseInt(json.getString("zxfperson"))>0){
					json.put("zxfcjratio", Double.parseDouble(json.getString("zxfcjperson"))/Double.parseDouble(json.getString("zxfperson"))+"");
				}else{
					json.put("zxfcjratio", "0");
				}
				if(zxfPaymoney.size()>0){
					for (JSONObject jsonObject2 : zxfPaymoney) {
						if(askpersonId.equals(jsonObject2.getString("askperson"))){
							json.put("zxfpaymoney", jsonObject2.getString("paymoney"));
							json.put("zxfjcfmoney", jsonObject2.getString("jcfmoney"));
							json.put("zxfyjjmoney", jsonObject2.getString("yjjmoney"));
						}else{
							json.put("zxfpaymoney", "0.00");
							json.put("zxfjcfmoney", "0.00");
							json.put("zxfyjjmoney", "0.00");
						}
					}
				}else{
					json.put("zxfpaymoney", "0.00");
					json.put("zxfjcfmoney", "0.00");
					json.put("zxfyjjmoney", "0.00");
				}
				if(Cmoney.size()>0){
					for (JSONObject jsonObject2 : Cmoney) {
						if(askpersonId.equals(jsonObject2.getString("askperson"))){
							if(jsonObject2.getString("type").equals("充值")){
								json.put("cmoney", jsonObject2.getString("cmoney"));
							}
						}else{
							json.put("cmoney", "0.00");
						}
					}
					for (JSONObject jsonObject2 : Cmoney) {
						if(askpersonId.equals(jsonObject2.getString("askperson"))){
							if(jsonObject2.getString("type").equals("退费")){
								json.put("tcmoney", jsonObject2.getString("cmoney"));
							}
						}else{
							json.put("tcmoney", "0.00");
						}
					}
				}else{
					json.put("cmoney", "0.00");
					json.put("tcmoney", "0.00");
				}
				if(Drugsmoney.size()>0){
					for (JSONObject jsonObject2 : Drugsmoney) {
						if(askpersonId.equals(jsonObject2.getString("askperson"))){
							json.put("drugsmoney", jsonObject2.getString("drugsmoney"));
							
						}else{
							json.put("drugsmoney", "0.00");
						}
					}
				}else{
					json.put("drugsmoney", "0.00");
				}
				json.put("xmpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))));
				//json.put("zjpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))+Double.parseDouble(json.getString("cmoney"))));
				hjczAllNums+=Integer.parseInt(json.getString("czperson"));
				hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
				hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
				hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
				hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
				hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
				hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
				hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
				hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
				hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
				hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
				hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
				hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
				hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
				hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
				hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
				hjcmoney+=Double.parseDouble(json.getString("cmoney"));
				hjtcmoney+=Double.parseDouble(json.getString("tcmoney"));
				list.add(json);
			}
			JSONObject jsonobject=new JSONObject();
			if(map.get("startday").equals(map.get("endday"))){
				jsonobject.put("month", map.get("year")+"年"+map.get("month")+"月"+map.get("startday")+"日");
			}else{
				jsonobject.put("month", "-");
			}
			jsonobject.put("name", "合计");
			jsonobject.put("czperson", hjczAllNums+"");
			jsonobject.put("czcjperson", hjczCjNum+"");
			NumberFormat nf1 = NumberFormat.getInstance();
			nf1.setGroupingUsed(false);
			if(hjczPaymoney>0){
				jsonobject.put("czpaymoney",nf1.format(hjczPaymoney));
			}else{
				jsonobject.put("czpaymoney", "0.00");
			}
			if(hjczJcfmoney>0){
				jsonobject.put("czjcfmoney",nf1.format(hjczJcfmoney));
			}else{
				jsonobject.put("czjcfmoney", "0.00");
			}
			if(hjczYjjmoney>0){
				jsonobject.put("czyjjmoney",nf1.format(hjczYjjmoney));
			}else{
				jsonobject.put("czyjjmoney", "0.00");
			}
			jsonobject.put("fzperson", hjfzAllNums+"");
			jsonobject.put("fzcjperson", hjfzCjNum+"");
			if(hjfzPaymoney>0){
				jsonobject.put("fzpaymoney", nf1.format(hjfzPaymoney));
			}else{
				jsonobject.put("fzpaymoney", "0.00");
			}
			if(hjfzJcfmoney>0){
				jsonobject.put("fzjcfmoney", nf1.format(hjfzJcfmoney));
			}else{
				jsonobject.put("fzjcfmoney", "0.00");
			}
			if(hjfzYjjmoney>0){
				jsonobject.put("fzyjjmoney", nf1.format(hjfzYjjmoney));
			}else{
				jsonobject.put("fzyjjmoney", "0.00");
			}
			jsonobject.put("zxfperson", hjzxfAllNums+"");
			jsonobject.put("zxfcjperson", hjzxfCjNum+"");
			if(hjzxfPaymoney>0){
				jsonobject.put("zxfpaymoney", nf1.format(hjzxfPaymoney));
				
			}else{
				jsonobject.put("zxfpaymoney", "0.00");
			}
			if(hjzxfJcfmoney>0){
				jsonobject.put("zxfjcfmoney", nf1.format(hjzxfJcfmoney));
				
			}else{
				jsonobject.put("zxfjcfmoney", "0.00");
			}
			if(hjzxfYjjmoney>0){
				jsonobject.put("zxfyjjmoney", nf1.format(hjzxfYjjmoney));
				
			}else{
				jsonobject.put("zxfyjjmoney", "0.00");
			}
			if(hjcmoney>0){
				jsonobject.put("cmoney", nf1.format(hjcmoney));
			}else{
				jsonobject.put("cmoney", "0.00");
			}
			if(hjtcmoney<0){
				jsonobject.put("tcmoney", nf1.format(hjtcmoney));
			}else{
				jsonobject.put("tcmoney", "0.00");
			}
			if(hjDrugsmoney>0){
				jsonobject.put("drugsmoney", nf1.format(hjDrugsmoney));
			}else{
				jsonobject.put("drugsmoney", "0.00");
			}
			if(hjczAllNums>0){
				jsonobject.put("czcjratio", (double)hjczCjNum/hjczAllNums+"");
			}else{
				jsonobject.put("czcjratio", "0");
			}
			if(hjfzAllNums>0){
				jsonobject.put("fzcjratio", (double)hjfzCjNum/hjfzAllNums+"");
			}else{
				jsonobject.put("fzcjratio", "0");
			}
			if(hjzxfAllNums>0){
				jsonobject.put("zxfcjratio", (double)hjzxfCjNum/hjzxfAllNums+"");
			}else{
				jsonobject.put("zxfcjratio", "0");
			}
			jsonobject.put("xmpaymoney", ""+(Double.parseDouble(jsonobject.getString("czpaymoney"))+Double.parseDouble(jsonobject.getString("fzpaymoney"))+Double.parseDouble(jsonobject.getString("zxfpaymoney"))));
			//jsonobject.put("zjpaymoney", ""+(Double.parseDouble(jsonobject.getString("czpaymoney"))+Double.parseDouble(jsonobject.getString("fzpaymoney"))+Double.parseDouble(jsonobject.getString("zxfpaymoney"))+Double.parseDouble(jsonobject.getString("cmoney"))));
			
			list.add(jsonobject);
		return list;
	}
	/**   
	  * <p>Title: findTotalMoneyByMonth</p>   
	  * <p>Description: </p>   
	  * @param dataMap
	  * @param request
	  * @return
	  * @throws Exception   
	  * @see com.hudh.sjtj.service.IDataAnalysisService#findTotalMoneyByMonth(java.util.Map, javax.servlet.http.HttpServletRequest)   
	  */  
	@Override
	public List<JSONObject> findTotalMoneyByMonth(Map<String, String> dataMap, HttpServletRequest request)
			throws Exception {
		YZPerson person = SessionUtil.getLoginPerson(request);
		if(!YZUtility.isNullorEmpty(dataMap.get("buttonName"))&&dataMap.get("deptCategory").equals("all")){
			List<JSONObject> personSeqIds=sysDeptPrivDao.findPersonSeqIdByButtonName(dataMap);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < personSeqIds.size(); i++) {
				if(i==personSeqIds.size()-1){
					str.append("\'"+personSeqIds.get(i).getString("seqid")+"\'");
				}else{
					str.append("\'"+personSeqIds.get(i).getString("seqid")+"\',");
				}
			}
			dataMap.put("askperson", str.toString());
			//传入部门id 查询部门内人员id
		}else if(!YZUtility.isNullorEmpty(dataMap.get("buttonName"))&&!dataMap.get("deptCategory").equals("all")&&!dataMap.get("deptCategory").equals("personage")){
			//seq_id,user_name,user_id
			dataMap.put("dept_id", dataMap.get("deptCategory"));
			List<JSONObject> yzpersonSeqids = yzPersonLogic.selectPersonPaiban(dataMap);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < yzpersonSeqids.size(); i++) {
				if(i==yzpersonSeqids.size()-1){
					str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\'");
				}else{
					str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\',");
				}
			}
			dataMap.put("askperson", str.toString());
			//传入人员id 查询个人数据
		}else if(YZUtility.isNullorEmpty(dataMap.get("buttonName"))&&dataMap.get("deptCategory").equals("personage")){
			dataMap.put("askperson", "\'"+person.getSeqId()+"\'");
		}
		List<JSONObject> list = new ArrayList<JSONObject>();
		String startYear = dataMap.get("startTime");
		String endYear = dataMap.get("endTime");
		for (int i = Integer.parseInt(startYear); i <= Integer.parseInt(endYear); i++) {
			JSONObject json= new JSONObject();
			//查询总的业绩
			List<JSONObject> listTotalMoney = null;
			//查询总的预交金
			List<JSONObject> listTotalPrepay = null;
			//查询总的退费金额
			List<JSONObject> listTotalRefund = null;
			//查询总的减免及折扣额
			List<JSONObject> listTotalDiscount = null;
			//查询使用的总预交金
			List<JSONObject> listTotalUsePrepay = null;
			//查询退费的总预交金
			List<JSONObject> listTotalRefundPrepay = null;
			//查询总的药品费用
			List<JSONObject> listTotalDrugs = null;
			//总的检查项目费用
			List<JSONObject> listTotalCheckItems = null;
			json.put("year", i + "年");
			json.put("name", person.getUserName());
			if("zymoney".equals(dataMap.get("selectLabel"))) {//查询总的业绩
				dataMap.put("year", Integer.parseInt((dataMap.get("endTime")).substring(0, 4)) + "");
				dataMap.put("startmonth", "1");
				dataMap.put("endmonth", "12");
				dataMap.put("years", "(year(detail.createtime)=" + i + ")");
				listTotalMoney = analysisDao.findTotalMoneyByMonth(dataMap);
				if(listTotalMoney != null && listTotalMoney.size() == 12){
					json.put("selectType", "总业绩");
					double totalYearMoney = 0;
					for (int j = 0; j < listTotalMoney.size(); j++) {
						JSONObject o = listTotalMoney.get(j);
//						json.put("totalMoney" + j, o.getString("totalmoney"));
						json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
						totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
					}
					NumberFormat nf = NumberFormat.getInstance();
					nf.setGroupingUsed(false);
					if(totalYearMoney > 0){
						json.put("totalYearMoney", nf.format(totalYearMoney));
					}else{
						json.put("totalYearMoney", "0.00");
					}
//					json.put("totalYearMoney", totalYearMoney);
				} else if (listTotalMoney.size() < 12 && listTotalMoney.size() > 0) {
					json.put("selectType", "总业绩");
					double totalYearMoney = 0;
					int a=0;
					for (int j = 1; j < 13; j++) {
						if(a>=listTotalMoney.size()){
							json.put("totalMoney" + j, "0.00");
						}else{
							for (int k = a; k < listTotalMoney.size(); k++) {
								if (listTotalMoney.get(k).get("月").equals(j + "")) {
									JSONObject o = listTotalMoney.get(k);
//								json.put("totalMoney" + j, o.getString("totalmoney"));
									json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
									totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
									a+=1;
									break;
								}else{
//								json.put("totalMoney" + j, o.getString("totalmoney"));
									json.put("totalMoney" + j, "0.00");
									break;
								}
							}
						}
					}
					NumberFormat nf = NumberFormat.getInstance();
					nf.setGroupingUsed(false);
					if(totalYearMoney > 0){
						json.put("totalYearMoney", nf.format(totalYearMoney));
					}else{
						json.put("totalYearMoney", "0.00");
					}
//					json.put("totalYearMoney", totalYearMoney);
				} else {	
					json.put("selectType", "总业绩");
					json.put("totalMoney1", "0.00");
					json.put("totalMoney2", "0.00");
					json.put("totalMoney3", "0.00");
					json.put("totalMoney4", "0.00");
					json.put("totalMoney5", "0.00");
					json.put("totalMoney6", "0.00");
					json.put("totalMoney7", "0.00");
					json.put("totalMoney8", "0.00");
					json.put("totalMoney9", "0.00");
					json.put("totalMoney10", "0.00");
					json.put("totalMoney11", "0.00");
					json.put("totalMoney12", "0.00");
					json.put("totalYearMoney", "0.00");
				}
			} else if ("zmoney".equals(dataMap.get("selectLabel"))) {//查询总的预交金
				dataMap.put("year", Integer.parseInt((dataMap.get("endTime")).substring(0, 4)) + "");
				dataMap.put("code", "ISNULL(sum(case when datepart(month,createtime)=" + (Integer.parseInt(dataMap.get("endTime"))+"")+" then 1 else 0 end),0) as '"+(Integer.parseInt(dataMap.get("endTime"))+"")+"月'");
				dataMap.put("startmonth", "1");
				dataMap.put("endmonth", "12");
				dataMap.put("years", "(year(detail.createtime)=" + i + ")");
				listTotalPrepay = analysisDao.findTotalPrepayByMonth(dataMap);
				if(listTotalPrepay != null && listTotalPrepay.size() == 12){
					json.put("selectType", "总预交金");
					double totalYearMoney = 0;
					for (int j = 0; j < listTotalPrepay.size(); j++) {
						JSONObject o = listTotalPrepay.get(j);
//						json.put("totalMoney" + j, o.getString("totalmoney"));
						json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
						totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
					}
					NumberFormat nf = NumberFormat.getInstance();
					nf.setGroupingUsed(false);
					if(totalYearMoney > 0){
						json.put("totalYearMoney", nf.format(totalYearMoney));
					}else{
						json.put("totalYearMoney", "0.00");
					}
//					json.put("totalYearMoney", totalYearMoney);
				} else if (listTotalPrepay.size() < 12 && listTotalPrepay.size() > 0) {
					json.put("selectType", "总预交金");
					double totalYearMoney = 0;
					int a=0;
					for (int j = 1; j < 13; j++) {
						if(a >= listTotalPrepay.size()){
							json.put("totalMoney" + j, "0.00");
						}else{
							for (int k = a; k < listTotalPrepay.size(); k++) {
								if (listTotalPrepay.get(k).get("月").equals(j + "")) {
									JSONObject o = listTotalPrepay.get(k);
//								json.put("totalMoney" + j, o.getString("totalmoney"));
									json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
									totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
									a+=1;
									break;
								}else{
//								json.put("totalMoney" + j, o.getString("totalmoney"));
									json.put("totalMoney" + j, "0.00");
									break;
								}
							}
						}
					}
					NumberFormat nf = NumberFormat.getInstance();
					nf.setGroupingUsed(false);
					if(totalYearMoney > 0){
						json.put("totalYearMoney", nf.format(totalYearMoney));
					}else{
						json.put("totalYearMoney", "0.00");
					}
//					json.put("totalYearMoney", totalYearMoney);
				} else {	
					json.put("selectType", "总预交金");
					json.put("totalMoney1", "0.00");
					json.put("totalMoney2", "0.00");
					json.put("totalMoney3", "0.00");
					json.put("totalMoney4", "0.00");
					json.put("totalMoney5", "0.00");
					json.put("totalMoney6", "0.00");
					json.put("totalMoney7", "0.00");
					json.put("totalMoney8", "0.00");
					json.put("totalMoney9", "0.00");
					json.put("totalMoney10", "0.00");
					json.put("totalMoney11", "0.00");
					json.put("totalMoney12", "0.00");
					json.put("totalYearMoney", "0.00");
				}
			} else if ("tkmoney".equals(dataMap.get("selectLabel"))) {//查询总的退费金额
				dataMap.put("year", Integer.parseInt((dataMap.get("endTime")).substring(0, 4)) + "");
				dataMap.put("code", "ISNULL(sum(case when datepart(month,createtime)=" + (Integer.parseInt(dataMap.get("endTime"))+"")+" then 1 else 0 end),0) as '"+(Integer.parseInt(dataMap.get("endTime"))+"")+"月'");
				dataMap.put("startmonth", "1");
				dataMap.put("endmonth", "12");
				dataMap.put("years", "(year(createtime)=" + i + ")");
				listTotalRefund = analysisDao.findTotalRefundByMonth(dataMap);
				if(listTotalRefund != null && listTotalRefund.size() == 12){
					json.put("selectType", "退费");
					double totalYearMoney = 0;
					for (int j = 0; j < listTotalRefund.size(); j++) {
						JSONObject o = listTotalRefund.get(j);
//						json.put("totalMoney" + j, o.getString("totalmoney"));
						json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
						totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
					}
					NumberFormat nf = NumberFormat.getInstance();
					nf.setGroupingUsed(false);
					if(totalYearMoney > 0){
						json.put("totalYearMoney", nf.format(totalYearMoney));
					}else{
						json.put("totalYearMoney", "0.00");
					}
//					json.put("totalYearMoney", totalYearMoney);
				} else if (listTotalRefund.size() < 12 && listTotalRefund.size() > 0) {
					json.put("selectType", "退费");
					double totalYearMoney = 0;
					int a=0;
					for (int j = 1; j < 13; j++) {
						if(a >= listTotalRefund.size()){
							json.put("totalMoney" + j, "0.00");
						}else{
							for (int k = a; k < listTotalRefund.size(); k++) {
								if (listTotalRefund.get(k).get("月").equals(j+"")) {
									JSONObject o = listTotalRefund.get(k);
//								json.put("totalMoney" + j, o.getString("totalmoney"));
									json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
									totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
									a+=1;
									break;
								}else{
//								json.put("totalMoney" + j, o.getString("totalmoney"));
									json.put("totalMoney" + j, "0.00");
									break;
								}
							}
						}
					}
					NumberFormat nf = NumberFormat.getInstance();
					nf.setGroupingUsed(false);
					if(totalYearMoney > 0){
						json.put("totalYearMoney", nf.format(totalYearMoney));
					}else{
						json.put("totalYearMoney", "0.00");
					}
//					json.put("totalYearMoney", totalYearMoney);
				} else {	
					json.put("selectType", "退费");
					json.put("totalMoney1", "0.00");
					json.put("totalMoney2", "0.00");
					json.put("totalMoney3", "0.00");
					json.put("totalMoney4", "0.00");
					json.put("totalMoney5", "0.00");
					json.put("totalMoney6", "0.00");
					json.put("totalMoney7", "0.00");
					json.put("totalMoney8", "0.00");
					json.put("totalMoney9", "0.00");
					json.put("totalMoney10", "0.00");
					json.put("totalMoney11", "0.00");
					json.put("totalMoney12", "0.00");
					json.put("totalYearMoney", "0.00");
				}
			} else if ("jmdzmoney".equals(dataMap.get("selectLabel"))) {//查询总的减免及折扣额
				dataMap.put("year", Integer.parseInt((dataMap.get("endTime")).substring(0, 4)) + "");
				dataMap.put("code", "ISNULL(sum(case when datepart(month,createtime)=" + (Integer.parseInt(dataMap.get("endTime"))+"")+" then 1 else 0 end),0) as '"+(Integer.parseInt(dataMap.get("endTime"))+"")+"月'");
				dataMap.put("startmonth", "1");
				dataMap.put("endmonth", "12");
				dataMap.put("years", "(year(createtime)=" + i + ")");
				listTotalDiscount = analysisDao.findTotalDiscountByMonth(dataMap);
				if(listTotalDiscount != null && listTotalDiscount.size() == 12){
					json.put("selectType", "减免及折扣额");
					double totalYearMoney = 0;
					for (int j = 0; j < listTotalDiscount.size(); j++) {
						JSONObject o = listTotalDiscount.get(j);
//						json.put("totalMoney" + j, o.getString("totalmoney"));
						json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
						totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
					}
					NumberFormat nf = NumberFormat.getInstance();
					nf.setGroupingUsed(false);
					if(totalYearMoney > 0){
						json.put("totalYearMoney", nf.format(totalYearMoney));
					}else{
						json.put("totalYearMoney", "0.00");
					}
//					json.put("totalYearMoney", totalYearMoney);
				} else if (listTotalDiscount.size() < 12 && listTotalDiscount.size() > 0) {
					json.put("selectType", "减免及折扣额");
					double totalYearMoney = 0;
					int a=0;
					for (int j = 1; j < 13; j++) {
						if(a >= listTotalDiscount.size()){
							json.put("totalMoney" + j, "0.00");
						}else{
							for (int k = a; k < listTotalDiscount.size(); k++) {
								if (listTotalDiscount.get(k).get("月").equals(j+"")) {
									JSONObject o = listTotalDiscount.get(k);
//								json.put("totalMoney" + j, o.getString("totalmoney"));
									json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
									totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
									a+=1;
									break;
								}else{
//								json.put("totalMoney" + j, o.getString("totalmoney"));
									json.put("totalMoney" + j, "0.00");
									break;
								}
							}
						}
					}
					NumberFormat nf = NumberFormat.getInstance();
					nf.setGroupingUsed(false);
					if(totalYearMoney > 0){
						json.put("totalYearMoney", nf.format(totalYearMoney));
					}else{
						json.put("totalYearMoney", "0.00");
					}
//					json.put("totalYearMoney", totalYearMoney);
				} else {	
					json.put("selectType", "减免及折扣额");
					json.put("totalMoney1", "0.00");
					json.put("totalMoney2", "0.00");
					json.put("totalMoney3", "0.00");
					json.put("totalMoney4", "0.00");
					json.put("totalMoney5", "0.00");
					json.put("totalMoney6", "0.00");
					json.put("totalMoney7", "0.00");
					json.put("totalMoney8", "0.00");
					json.put("totalMoney9", "0.00");
					json.put("totalMoney10", "0.00");
					json.put("totalMoney11", "0.00");
					json.put("totalMoney12", "0.00");
					json.put("totalYearMoney", "0.00");
				}
			} else if ("symoney".equals(dataMap.get("selectLabel"))) {//使用的总预交金
				//查询使用的总预交金
				dataMap.put("year", Integer.parseInt((dataMap.get("endTime")).substring(0, 4)) + "");
				dataMap.put("code", "ISNULL(sum(case when datepart(month,createtime)=" + (Integer.parseInt(dataMap.get("endTime"))+"")+" then 1 else 0 end),0) as '"+(Integer.parseInt(dataMap.get("endTime"))+"")+"月'");
				dataMap.put("startmonth", "1");
				dataMap.put("endmonth", "12");
				dataMap.put("years", "(year(detail.createtime)=" + i + ")");
				listTotalUsePrepay = analysisDao.findTotalUsePrepayByMonth(dataMap);
				if(listTotalUsePrepay != null && listTotalUsePrepay.size() == 12){
					json.put("selectType", "使用预交金");
					double totalYearMoney = 0;
					for (int j = 0; j < listTotalUsePrepay.size(); j++) {
						JSONObject o = listTotalUsePrepay.get(j);
						json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
						totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
					}
					NumberFormat nf = NumberFormat.getInstance();
					nf.setGroupingUsed(false);
					if(totalYearMoney > 0){
						json.put("totalYearMoney", nf.format(totalYearMoney));
					}else{
						json.put("totalYearMoney", "0.00");
					}
				} else if (listTotalUsePrepay.size() < 12 && listTotalUsePrepay.size() > 0) {
					json.put("selectType", "使用预交金");
					double totalYearMoney = 0;
					int a = 0;
					for (int j = 1; j < 13; j++) {
						if(a >= listTotalUsePrepay.size()){
							json.put("totalMoney" + j, "0.00");
						}else{
							for (int k = a; k < listTotalUsePrepay.size(); k++) {
								if (listTotalUsePrepay.get(k).get("月").equals(j + "")) {
									JSONObject o = listTotalUsePrepay.get(k);
									json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
									totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
									a += 1;
									break;
								}else{
									json.put("totalMoney" + j, "0.00");
									break;
								}
							}
						}
					}
					NumberFormat nf = NumberFormat.getInstance();
					nf.setGroupingUsed(false);
					if(totalYearMoney > 0){
						json.put("totalYearMoney", nf.format(totalYearMoney));
					} else {
						json.put("totalYearMoney", "0.00");
					}
				} else {	
					json.put("selectType", "使用预交金");
					json.put("totalMoney1", "0.00");
					json.put("totalMoney2", "0.00");
					json.put("totalMoney3", "0.00");
					json.put("totalMoney4", "0.00");
					json.put("totalMoney5", "0.00");
					json.put("totalMoney6", "0.00");
					json.put("totalMoney7", "0.00");
					json.put("totalMoney8", "0.00");
					json.put("totalMoney9", "0.00");
					json.put("totalMoney10", "0.00");
					json.put("totalMoney11", "0.00");
					json.put("totalMoney12", "0.00");
					json.put("totalYearMoney", "0.00");
				}
			} else if ("tfmoney".equals(dataMap.get("selectLabel"))) {//退费的总预交金
				//查询退费的总预交金
				dataMap.put("year", Integer.parseInt((dataMap.get("endTime")).substring(0, 4)) + "");
				dataMap.put("code", "ISNULL(sum(case when datepart(month,createtime)=" + (Integer.parseInt(dataMap.get("endTime"))+"")+" then 1 else 0 end),0) as '"+(Integer.parseInt(dataMap.get("endTime"))+"")+"月'");
				dataMap.put("startmonth", "1");
				dataMap.put("endmonth", "12");
				dataMap.put("years", "(year(createtime)=" + i + ")");
				listTotalRefundPrepay = analysisDao.findTotalRefundPrepayByMonth(dataMap);
				if(listTotalRefundPrepay != null && listTotalRefundPrepay.size() == 12){
					json.put("selectType", "退费预交金");
					double totalYearMoney = 0;
					for (int j = 0; j < listTotalRefundPrepay.size(); j++) {
						JSONObject o = listTotalRefundPrepay.get(j);
						json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
						totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
					}
					NumberFormat nf = NumberFormat.getInstance();
					nf.setGroupingUsed(false);
					if(totalYearMoney < 0){
						json.put("totalYearMoney", nf.format(totalYearMoney));
					}else{
						json.put("totalYearMoney", "0.00");
					}
				} else if (listTotalRefundPrepay.size() < 12 && listTotalRefundPrepay.size() > 0) {
					json.put("selectType", "退费预交金");
					double totalYearMoney = 0;
					int a = 0;
					for (int j = 1; j < 13; j++) {
						if(a >= listTotalRefundPrepay.size()){
							json.put("totalMoney" + j, "0.00");
						}else{
							for (int k = a; k < listTotalRefundPrepay.size(); k++) {
								if (listTotalRefundPrepay.get(k).get("月").equals(j + "")) {
									JSONObject o = listTotalRefundPrepay.get(k);
									json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
									totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
									a += 1;
									break;
								}else{
									json.put("totalMoney" + j, "0.00");
									break;
								}
							}
						}
					}
					NumberFormat nf = NumberFormat.getInstance();
					nf.setGroupingUsed(false);
					if(totalYearMoney < 0){
						json.put("totalYearMoney", nf.format(totalYearMoney));
					} else {
						json.put("totalYearMoney", "0.00");
					}
				} else {	
					json.put("selectType", "退费预交金");
					json.put("totalMoney1", "0.00");
					json.put("totalMoney2", "0.00");
					json.put("totalMoney3", "0.00");
					json.put("totalMoney4", "0.00");
					json.put("totalMoney5", "0.00");
					json.put("totalMoney6", "0.00");
					json.put("totalMoney7", "0.00");
					json.put("totalMoney8", "0.00");
					json.put("totalMoney9", "0.00");
					json.put("totalMoney10", "0.00");
					json.put("totalMoney11", "0.00");
					json.put("totalMoney12", "0.00");
					json.put("totalYearMoney", "0.00");
				}
			} else if ("ypmoney".equals(dataMap.get("selectLabel"))) {//总的药品费用
				//查询总的药品费用
				dataMap.put("year", Integer.parseInt((dataMap.get("endTime")).substring(0, 4)) + "");
				dataMap.put("code", "ISNULL(sum(case when datepart(month,createtime)=" + (Integer.parseInt(dataMap.get("endTime"))+"")+" then 1 else 0 end),0) as '"+(Integer.parseInt(dataMap.get("endTime"))+"")+"月'");
				dataMap.put("startmonth", "1");
				dataMap.put("endmonth", "12");
				dataMap.put("years", "(year(detail.createtime)=" + i + ")");
				listTotalDrugs = analysisDao.findTotalDrugsByMonth(dataMap);
				if(listTotalDrugs != null && listTotalDrugs.size() == 12){
					json.put("selectType", "药品");
					double totalYearMoney = 0;
					for (int j = 0; j < listTotalDrugs.size(); j++) {
						JSONObject o = listTotalDrugs.get(j);
						json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
						totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
					}
					NumberFormat nf = NumberFormat.getInstance();
					nf.setGroupingUsed(false);
					if(totalYearMoney > 0){
						json.put("totalYearMoney", nf.format(totalYearMoney));
					}else{
						json.put("totalYearMoney", "0.00");
					}
				} else if (listTotalDrugs.size() < 12 && listTotalDrugs.size() > 0) {
					json.put("selectType", "药品");
					double totalYearMoney = 0;
					int a = 0;
					for (int j = 1; j < 13; j++) {
						if(a >= listTotalDrugs.size()){
							json.put("totalMoney" + j, "0.00");
						}else{
							for (int k = a; k < listTotalDrugs.size(); k++) {
								if (listTotalDrugs.get(k).get("月").equals(j + "")) {
									JSONObject o = listTotalDrugs.get(k);
									json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
									totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
									a += 1;
									break;
								}else{
									json.put("totalMoney" + j, "0.00");
									break;
								}
							}
						}
					}
					NumberFormat nf = NumberFormat.getInstance();
					nf.setGroupingUsed(false);
					if(totalYearMoney > 0){
						json.put("totalYearMoney", nf.format(totalYearMoney));
					} else {
						json.put("totalYearMoney", "0.00");
					}
				} else {	
					json.put("selectType", "药品");
					json.put("totalMoney1", "0.00");
					json.put("totalMoney2", "0.00");
					json.put("totalMoney3", "0.00");
					json.put("totalMoney4", "0.00");
					json.put("totalMoney5", "0.00");
					json.put("totalMoney6", "0.00");
					json.put("totalMoney7", "0.00");
					json.put("totalMoney8", "0.00");
					json.put("totalMoney9", "0.00");
					json.put("totalMoney10", "0.00");
					json.put("totalMoney11", "0.00");
					json.put("totalMoney12", "0.00");
					json.put("totalYearMoney", "0.00");
				}
			} else if ("jcxmmoney".equals(dataMap.get("selectLabel"))) {//总的检查项目费用  
				//查询总的检查项目费用
				dataMap.put("year", Integer.parseInt((dataMap.get("endTime")).substring(0, 4)) + "");
				dataMap.put("code", "ISNULL(sum(case when datepart(month,createtime)=" + (Integer.parseInt(dataMap.get("endTime"))+"")+" then 1 else 0 end),0) as '"+(Integer.parseInt(dataMap.get("endTime"))+"")+"月'");
				dataMap.put("startmonth", "1");
				dataMap.put("endmonth", "12");
				dataMap.put("years", "(year(detail.createtime)=" + i + ")");
				listTotalCheckItems = analysisDao.findTotalCheckItemsByMonth(dataMap);
				if(listTotalCheckItems != null && listTotalCheckItems.size() == 12){
					json.put("selectType", "检查项目");
					double totalYearMoney = 0;
					for (int j = 0; j < listTotalCheckItems.size(); j++) {
						JSONObject o = listTotalCheckItems.get(j);
						json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
						totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
					}
					NumberFormat nf = NumberFormat.getInstance();
					nf.setGroupingUsed(false);
					if(totalYearMoney > 0){
						json.put("totalYearMoney", nf.format(totalYearMoney));
					}else{
						json.put("totalYearMoney", "0.00");
					}
				} else if (listTotalCheckItems.size() < 12 && listTotalCheckItems.size() > 0) {
					json.put("selectType", "检查项目");
					double totalYearMoney = 0;
					int a = 0;
					for (int j = 1; j < 13; j++) {
						if(a >= listTotalCheckItems.size()){
							json.put("totalMoney" + j, "0.00");
						}else{
							for (int k = a; k < listTotalCheckItems.size(); k++) {
								if (listTotalCheckItems.get(k).get("月").equals(j + "")) {
									JSONObject o = listTotalCheckItems.get(k);
									json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
									totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
									a += 1;
									break;
								}else{
									json.put("totalMoney" + j, "0.00");
									break;
								}
							}
						}
					}
					NumberFormat nf = NumberFormat.getInstance();
					nf.setGroupingUsed(false);
					if(totalYearMoney > 0){
						json.put("totalYearMoney", nf.format(totalYearMoney));
					} else {
						json.put("totalYearMoney", "0.00");
					}
				} else {	
					json.put("selectType", "检查项目");
					json.put("totalMoney1", "0.00");
					json.put("totalMoney2", "0.00");
					json.put("totalMoney3", "0.00");
					json.put("totalMoney4", "0.00");
					json.put("totalMoney5", "0.00");
					json.put("totalMoney6", "0.00");
					json.put("totalMoney7", "0.00");
					json.put("totalMoney8", "0.00");
					json.put("totalMoney9", "0.00");
					json.put("totalMoney10", "0.00");
					json.put("totalMoney11", "0.00");
					json.put("totalMoney12", "0.00");
					json.put("totalYearMoney", "0.00");
				}
			}
			list.add(json);
		}
		return list;
	}
	/**   
	  * <p>Title: findCjsCale</p>   
	  * <p>Description: </p>   
	  * @param map
	  * @return
	  * @throws Exception   
	  * @see com.hudh.sjtj.service.IDataAnalysisService#(java.util.Map)
	  */  
	@Override
	public List<JSONObject> findCjsCale(HttpServletRequest request,Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		String startmonth=map.get("startmonth");
		String endmonth=map.get("endmonth");
		String year = map.get("year");
		//获取权限可见人员
		YZPerson person = SessionUtil.getLoginPerson(request);
		//有按钮标识和all 查询所有部门人员id
		if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("all")){
			List<JSONObject> personSeqIds=sysDeptPrivDao.findPersonSeqIdByButtonName(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < personSeqIds.size(); i++) {
				if(i==personSeqIds.size()-1){
					str.append("\'"+personSeqIds.get(i).getString("seqid")+"\'");
				}else{
					str.append("\'"+personSeqIds.get(i).getString("seqid")+"\',");
				}
			}
			map.put("askperson", str.toString());
			//传入部门id 查询部门内人员id
		}else if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&!map.get("deptCategory").equals("all")&&!map.get("deptCategory").equals("personage")){
			//seq_id,user_name,user_id
			map.put("dept_id", map.get("deptCategory"));
			List<JSONObject> yzpersonSeqids = yzPersonLogic.selectPersonPaiban(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < yzpersonSeqids.size(); i++) {
				if(i==yzpersonSeqids.size()-1){
					str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\'");
				}else{
					str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\',");
				}
			}
			map.put("askperson", str.toString());
			//传入人员id 查询个人数据
		}else if(YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("personage")){
			map.put("askperson", "\'"+person.getSeqId()+"\'");
		}
		//获取总业绩
		List<JSONObject> jsonPerformance = kDetailLogic.findZperformance(map);
		//获取减免及免除
		List<JSONObject> jsonDiscount = kpayLogic.discount(map);
		//获取预交金
		List<JSONObject> jsonAccepting = kRecordLogic.findAcceptingGold(map);
		//获取退费
		List<JSONObject> jsTk =  kRefundLogic.tkQuery(map);
		
		List<JSONObject> list =new ArrayList<JSONObject>();
		for (int i = Integer.parseInt(startmonth); i <= Integer.parseInt(endmonth); i++) {
			int a=0;
			JSONObject obj= new JSONObject();
			for (JSONObject object : jsonPerformance){
				if(object != null){
					if(Integer.valueOf(object.getString("month")) == i && object.getString("performance") != null && !object.getString("performance").equals("")){
						a=1;
						obj.put("month", object.getString("year")+"年"+object.getString("month")+"月");
						obj.put("performance", object.getString("performance"));
					}
					if(a==0){
						obj.put("month", year+"年"+i+"月");
						obj.put("performance", "0.00");
					}
				}
			}
			for (JSONObject object1 : jsonDiscount) {
				if(object1 != null){
					if(Integer.valueOf(object1.getString("month")) == i && object1.getString("discount") != null && !object1.getString("discount").equals("")){
						a=1;
						obj.put("month", object1.getString("year")+"年"+object1.getString("month")+"月");
						obj.put("discount", object1.getString("discount"));
					}
					if(a==0){
						obj.put("month", year+"年"+i+"月");
						obj.put("discount", "0.00");
					}
				}
			}
			for(JSONObject object3:jsonAccepting){
				if(object3 != null){
					if(Integer.valueOf(object3.getString("monthes")) == i && object3.getString("money") != null && !object3.getString("money").equals("")){
						a=1;
						obj.put("month", object3.getString("yeares")+"年"+object3.getString("monthes")+"月");
						obj.put("money", object3.getString("money"));
					}
					if(a==0){
						obj.put("month", year+"年"+i+"月");
						obj.put("money", "0.00");
					}
				}
			}
			
			for (JSONObject object2 : jsTk) {
				if(object2 != null){
					if(jsonDiscount.size()<=0){
						obj.put("month", year+"年"+i+"月");
						obj.put("discount", "0.00");
					}
					if(jsonPerformance.size()<=0){
						obj.put("month", year+"年"+i+"月");
						obj.put("performance", "0.00");
					}
					if(Integer.valueOf(object2.getString("month")) == i && object2.getString("tkmoney") != null && !object2.getString("tkmoney").equals("")){
						a=1;
						obj.put("month", object2.getString("year")+"年"+object2.getString("month")+"月");
						obj.put("tkMoney", object2.getString("tkmoney"));
					}
					if(a==0){
						obj.put("month", year+"年"+i+"月");
						obj.put("tkMoney", "0.00");
					}
					obj.put("yjMoney", "0.00");
				}
			}
			list.add(obj);
		}
		return list;
	}
	/**   
	  * <p>Title: findDepartment</p>   
	  * <p>Description: </p>   
	  * @param request
	  * @param map
	  * @return
	  * @throws Exception   
	  * @see com.hudh.sjtj.service.IDataAnalysisService#findDepartment(javax.servlet.http.HttpServletRequest, java.util.Map)   
	  */  
	@Override
	public List<JSONObject> findDepartment(HttpServletRequest request, Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		//获取权限可见人员
		YZPerson person = SessionUtil.getLoginPerson(request);
		//有按钮标识和all 查询所有部门人员id
		if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("all")){
			List<JSONObject> personSeqIds=sysDeptPrivDao.findPersonSeqIdByButtonName(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < personSeqIds.size(); i++) {
				if(i==personSeqIds.size()-1){
					str.append("\'"+personSeqIds.get(i).getString("seqid")+"\'");
				}else{
					str.append("\'"+personSeqIds.get(i).getString("seqid")+"\',");
				}
			}
			map.put("askperson", str.toString());
			//传入部门id 查询部门内人员id
		}else if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&!map.get("deptCategory").equals("all")&&!map.get("deptCategory").equals("personage")){
			//seq_id,user_name,user_id
			map.put("dept_id", map.get("deptCategory"));
			List<JSONObject> yzpersonSeqids = yzPersonLogic.selectPersonPaiban(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < yzpersonSeqids.size(); i++) {
				if(i==yzpersonSeqids.size()-1){
					str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\'");
				}else{
					str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\',");
				}
			}
			map.put("askperson", str.toString());
			//传入人员id 查询个人数据
		}else if(YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("personage")){
			map.put("askperson", "\'"+person.getSeqId()+"\'");
		}
		//获取部门业绩
		List<JSONObject> jsonDepartment = krLogic.findDepartment(map);
		//获取总业绩
		JSONObject jsonZperformance = krLogic.findZperformance(map);
		
		List<JSONObject> list =new ArrayList<JSONObject>();
			NumberFormat percent = NumberFormat.getPercentInstance();
			percent.setMinimumFractionDigits(3);
			for (JSONObject jsonObject : jsonDepartment) {
				JSONObject obj= new JSONObject();
				if(jsonObject.getString("performance") != null && !jsonObject.getString("performance").equals("")){
					obj.put("name",jsonObject.getString("deptname"));
					obj.put("department", jsonObject.getString("performance"));
					obj.put("proportion", percent.format(Double.valueOf(jsonObject.getString("performance"))/Double.valueOf(jsonZperformance.getString("zperformance"))));
				}else{
					obj.put("name",jsonObject.getString("deptname"));
					obj.put("department", "0.00");
					obj.put("proportion", "0.00%");
				}
				list.add(obj);
		}
		return list;
	}
	/**   
	  * <p>Title: findImplant</p>   
	  * <p>Description: </p>   
	  * @param request
	  * @param map
	  * @return
	  * @throws Exception   
	  * @see com.hudh.sjtj.service.IDataAnalysisService#findImplant(javax.servlet.http.HttpServletRequest, java.util.Map)   
	  */  
	@Override
	public List<JSONObject> findImplant(HttpServletRequest request, Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		//获取权限可见人员
		YZPerson person = SessionUtil.getLoginPerson(request);
		//有按钮标识和all 查询所有部门人员id
		if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("all")){
			List<JSONObject> personSeqIds=sysDeptPrivDao.findPersonSeqIdByButtonName(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < personSeqIds.size(); i++) {
				if(i==personSeqIds.size()-1){
					str.append("\'"+personSeqIds.get(i).getString("seqid")+"\'");
				}else{
					str.append("\'"+personSeqIds.get(i).getString("seqid")+"\',");
				}
			}
			map.put("askperson", str.toString());
			//传入部门id 查询部门内人员id
		}else if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&!map.get("deptCategory").equals("all")&&!map.get("deptCategory").equals("personage")){
			//seq_id,user_name,user_id
			map.put("dept_id", map.get("deptCategory"));
			List<JSONObject> yzpersonSeqids = yzPersonLogic.selectPersonPaiban(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < yzpersonSeqids.size(); i++) {
				if(i==yzpersonSeqids.size()-1){
					str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\'");
				}else{
					str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\',");
				}
			}
			map.put("askperson", str.toString());
			//传入人员id 查询个人数据
		}else if(YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("personage")){
			map.put("askperson", "\'"+person.getSeqId()+"\'");
		}
		//获取种植体数量
		List<JSONObject> ImplantNum = krLogic.findImplantNum(map);
		//获取种植体总数量
		JSONObject ImplantNumall = krLogic.findImplantNumAll(map);
		int nums = 0;
		if(ImplantNumall.getString("nums") != null && !ImplantNumall.getString("nums").equals("")){	
			nums = Integer.valueOf(ImplantNumall.getString("nums"));
		}
		NumberFormat percent = NumberFormat.getPercentInstance();
		percent.setMinimumFractionDigits(3);
		List<JSONObject> list =new ArrayList<JSONObject>();
		for (JSONObject jsonObject : ImplantNum) {
			JSONObject json = new JSONObject();
			if(jsonObject != null){
				json.put("ImplantName", jsonObject.getString("implantname"));
				json.put("ImplantNum", jsonObject.getString("nums"));
				int num = Integer.valueOf(jsonObject.getString("nums"));
				if(nums>0){	
					json.put("proportion",percent.format((double)num/nums));
				}else{
					json.put("proportion","0.00");
				}
			}
			list.add(json);
		}
		return list;
	}
	/**   
	  * <p>Title: finddoctor</p>   
	  * <p>Description: </p>   
	  * @param request
	  * @param map
	  * @return
	  * @throws Exception   
	  * @see com.hudh.sjtj.service.IDataAnalysisService#finddoctor(javax.servlet.http.HttpServletRequest, java.util.Map)   
	  */  
	@Override
	public List<JSONObject> finddoctor(HttpServletRequest request, Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		//获取权限可见人员
				YZPerson person = SessionUtil.getLoginPerson(request);
				//有按钮标识和all 查询所有部门人员id
				if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("all")){
					List<JSONObject> personSeqIds=sysDeptPrivDao.findPersonSeqIdByButtonName(map);
					StringBuffer str = new StringBuffer();
					for (int i = 0; i < personSeqIds.size(); i++) {
						if(i==personSeqIds.size()-1){
							str.append("\'"+personSeqIds.get(i).getString("seqid")+"\'");
						}else{
							str.append("\'"+personSeqIds.get(i).getString("seqid")+"\',");
						}
					}
					map.put("askperson", str.toString());
					//传入部门id 查询部门内人员id
				}else if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&!map.get("deptCategory").equals("all")&&!map.get("deptCategory").equals("personage")){
					//seq_id,user_name,user_id
					map.put("dept_id", map.get("deptCategory"));
					List<JSONObject> yzpersonSeqids = yzPersonLogic.selectPersonPaiban(map);
					StringBuffer str = new StringBuffer();
					for (int i = 0; i < yzpersonSeqids.size(); i++) {
						if(i==yzpersonSeqids.size()-1){
							str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\'");
						}else{
							str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\',");
						}
					}
					map.put("askperson", str.toString());
					//传入人员id 查询个人数据
				}else if(YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("personage")){
					map.put("askperson", "\'"+person.getSeqId()+"\'");
				}
				//获取医生业绩
				List<JSONObject> jsonDepartment = krLogic.findDoctor(map);
				//获取医生总业绩
				JSONObject jsonZperformance = krLogic.findZdoctor(map);
				NumberFormat percent = NumberFormat.getPercentInstance();
				percent.setMinimumFractionDigits(3);
				List<JSONObject> list =new ArrayList<JSONObject>();
					for (JSONObject jsonObject : jsonDepartment) {
						JSONObject obj= new JSONObject();
						if(jsonObject.getString("paymoney") != null && !jsonObject.getString("paymoney").equals("")){
							obj.put("name",jsonObject.getString("doctor") );
							obj.put("doctor", jsonObject.getString("paymoney"));
							obj.put("zdoctor",percent.format(Double.valueOf(jsonObject.getString("paymoney"))/Double.valueOf(jsonZperformance.getString("paymoney"))));
						}else{
							obj.put("name",jsonObject.getString("doctor") );
							obj.put("doctor", "0");
							obj.put("zdoctor","0.00%");
						}
						list.add(obj);	
				}
				return list;
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<JSONObject> findCJQuantityByAskpersonAndMonthInYear(HttpServletRequest request, Map<String, String> map) throws Exception {
		
		YZPerson person = SessionUtil.getLoginPerson(request);
		//有按钮标识和all 查询所有部门人员id
		String name="";
		if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("all")){
			List<JSONObject> personSeqIds=sysDeptPrivDao.findPersonSeqIdByButtonName(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < personSeqIds.size(); i++) {
				if(i==personSeqIds.size()-1){
					str.append("\'"+personSeqIds.get(i).getString("seqid")+"\'");
				}else{
					str.append("\'"+personSeqIds.get(i).getString("seqid")+"\',");
				}
			}
			map.put("visualstaff", str.toString());
			name="所有部门";
			//传入部门id 查询部门内人员id
		}else if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&!map.get("deptCategory").equals("all")&&!map.get("deptCategory").equals("personage")){
			//seq_id,user_name,user_id
			map.put("dept_id", map.get("deptCategory"));
			List<JSONObject> yzpersonSeqids = yzPersonLogic.selectPersonPaiban(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < yzpersonSeqids.size(); i++) {
				if(i==yzpersonSeqids.size()-1){
					str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\'");
				}else{
					str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\',");
				}
			}
			map.put("visualstaff", str.toString());
			String deptname = yzDeptLogic.getDeptNamesBySeqIds(map.get("deptCategory"));
			name=deptname;
			//传入人员id 查询个人数据
		}else if(YZUtility.isNullorEmpty(map.get("buttonName"))&&!map.get("deptCategory").equals("personage")){
			map.put("visualstaff", "\'"+person.getSeqId()+"\'");
			name=person.getUserName();
		}
		//获取初诊编码
		String czseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
		map.put("recesort", czseqId);
		//查询初诊人数
		JSONObject czAllNums=analysisDao.findQuantityByAskpersonAndMonthInYear(map);
		//查询初诊成交人数
		JSONObject czCjNum=analysisDao.findCJQuantityByAskpersonAndMonthInYear(map);
		//查询初诊业绩
		List<JSONObject> czPaymoney=analysisDao.findPaymoneyByMonth(map);
		// 获取复诊编码
		String fzseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
		map.put("recesort", fzseqId);
		//查询复诊人数
		JSONObject fzAllNums=analysisDao.findQuantityByAskpersonAndMonthInYear(map);
		//查询复诊成交人数
		JSONObject fzCjNum=analysisDao.findCJQuantityByAskpersonAndMonthInYear(map);
		//查询复诊业绩
		List<JSONObject> fzPaymoney=analysisDao.findPaymoneyByMonth(map);
		// 获取再消费编码
		String zxfseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
		// 获取复查编码
		String fcseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);			
		map.put("recesort", "\'"+zxfseqId+"\',\'"+fcseqId+"\'");
		//查询再消费人数
		JSONObject zxfAllNums=analysisDao.findQuantityByAskpersonAndMonthInYear(map);
		//查询再消费成交人数
		map.put("recesort", "\'"+zxfseqId+"\'");
		JSONObject zxfCjNum=analysisDao.findCJQuantityByAskpersonAndMonthInYear(map);
		//查询再消费业绩
		List<JSONObject> zxfPaymoney=analysisDao.findPaymoneyByMonth(map);
		List<JSONObject> list= new ArrayList<JSONObject>();
		//业绩循环标识
		int paymoneyMark1=0;
		int paymoneyMark2=0;
		int paymoneyMark3=0;

		for (int i = Integer.parseInt(map.get("startmonth")); i <= Integer.parseInt(map.get("endmonth")); i++) {
			JSONObject json= new JSONObject();
			json.put("day", i+"月");
			if(czAllNums!=null){
				String nums=czAllNums.getString(i+"月");
				if(nums!=null&&!nums.equals("")){
					json.put("czperson", nums);
				}
			}else{
				json.put("czperson", "0");
			}
			if(czCjNum!=null){
				String nums=czCjNum.getString(i+"月");
				if(nums!=null&&!nums.equals("")){
					json.put("czcjperson", nums);
				}
			}else{
				json.put("czcjperson", "0");
			}
//					if(Integer.parseInt(json.getString("czperson"))>0){
//						json.put("czcjratio", Double.parseDouble(json.getString("czcjperson"))/Double.parseDouble(json.getString("czperson"))+"");
//					}else{
//						json.put("czcjratio", "0");
//					}
			if(czPaymoney!=null&&czPaymoney.size()>paymoneyMark1){
				//存在数据
				JSONObject nums=czPaymoney.get(paymoneyMark1);
				if(nums.getString("年").equals(map.get("year"))){
					if(nums.getString("月").equals(i+"")){
						String paymoney=nums.getString("paymoney");
						if(paymoney!=null&&!paymoney.equals("")){
							json.put("czpaymoney", paymoney);
						}
						paymoneyMark1+=1;
					}else{
						json.put("czpaymoney", "0.00");
					}
				}else{
					json.put("czpaymoney", "0.00");
				}
			}else{
				json.put("czpaymoney", "0.00");
			}
			if(fzAllNums!=null){
				String nums=fzAllNums.getString(i+"月");
				if(nums!=null&&!nums.equals("")){
					json.put("fzperson", nums);
				}
			}else{
				json.put("fzperson", "0");
			}
			if(fzCjNum!=null){
				String nums=fzCjNum.getString(i+"月");
				if(nums!=null&&!nums.equals("")){
					json.put("fzcjperson", nums);
				}
			}else{
				json.put("fzcjperson", "0");
			}
//					if(Integer.parseInt(json.getString("fzperson"))>0){
//						json.put("fzcjratio", Double.parseDouble(json.getString("fzcjperson"))/Double.parseDouble(json.getString("fzperson"))+"");
//					}else{
//						json.put("fzcjratio", "0");
//					}
			
			if(fzPaymoney!=null&&fzPaymoney.size()>paymoneyMark2){
					//存在数据
					JSONObject nums=fzPaymoney.get(paymoneyMark2);
					if(nums.getString("年").equals(map.get("year"))){
						if(nums.getString("月").equals(i+"")){
							String paymoney=nums.getString("paymoney");
							if(paymoney!=null&&!paymoney.equals("")){
								json.put("fzpaymoney", paymoney);
							}
							paymoneyMark2+=1;
						}else{
							json.put("fzpaymoney", "0.00");
						}
					}else{
						json.put("fzpaymoney", "0.00");
					}
			}else{
				json.put("fzpaymoney", "0.00");
			}
			if(zxfAllNums!=null){
				String nums=zxfAllNums.getString(i+"月");
				if(nums!=null&&!nums.equals("")){
					json.put("zxfperson", nums);
				}
			}else{
				json.put("zxfperson", "0");
			}
			if(zxfCjNum!=null){
				String nums=zxfCjNum.getString(i+"月");
				if(nums!=null&&!nums.equals("")){
					json.put("zxfcjperson", nums);
				}
			}else{
				json.put("zxfcjperson", "0");
			}
//					if(Integer.parseInt(json.getString("zxfperson"))>0){
//						json.put("zxfcjratio", Double.parseDouble(json.getString("zxfcjperson"))/Double.parseDouble(json.getString("zxfperson"))+"");
//					}else{
//						json.put("zxfcjratio", "0");
//					}
			if(zxfPaymoney!=null&&zxfPaymoney.size()>paymoneyMark3){
				
					//存在数据
					JSONObject nums=zxfPaymoney.get(paymoneyMark3);
					if(nums.getString("年").equals(map.get("year"))){
						if(nums.getString("月").equals(i+"")){
							String paymoney=nums.getString("paymoney");
							if(paymoney!=null&&!paymoney.equals("")){
								json.put("zxfpaymoney", paymoney);
							}
							paymoneyMark3+=1;
						}else{
							json.put("zxfpaymoney", "0.00");
						}
					}else{
						json.put("zxfpaymoney", "0.00");
					}
			}else{
				json.put("zxfpaymoney", "0.00");
			}
			json.put("zjpaymoney", ""+(Double.parseDouble(json.getString("czpaymoney"))+Double.parseDouble(json.getString("fzpaymoney"))+Double.parseDouble(json.getString("zxfpaymoney"))));
			list.add(json);
		}
		return list;
	}
	/**   
	  * <p>Title: Devchannel</p>   
	  * <p>Description: </p>   
	  * @param request
	  * @param map
	  * @return
	  * @throws Exception   
	  * @see com.hudh.sjtj.service.IDataAnalysisService#Devchannel(javax.servlet.http.HttpServletRequest, java.util.Map)   
	  */  
	@SuppressWarnings("unchecked")
	@Override
	public List<JSONObject> Devchannel(HttpServletRequest request, Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		YZPerson person = SessionUtil.getLoginPerson(request);
		//有按钮标识和all 查询所有部门人员id
		if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("all")){
			List<JSONObject> personSeqIds=sysDeptPrivDao.findPersonSeqIdByButtonName(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < personSeqIds.size(); i++) {
				if(i==personSeqIds.size()-1){
					str.append("\'"+personSeqIds.get(i).getString("seqid")+"\'");
				}else{
					str.append("\'"+personSeqIds.get(i).getString("seqid")+"\',");
				}
			}
			map.put("askperson", str.toString());
			//传入部门id 查询部门内人员id
		}else if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&!map.get("deptCategory").equals("all")&&!map.get("deptCategory").equals("personage")){
			//seq_id,user_name,user_id
			map.put("dept_id", map.get("deptCategory"));
			List<JSONObject> yzpersonSeqids = yzPersonLogic.selectPersonPaiban(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < yzpersonSeqids.size(); i++) {
				if(i==yzpersonSeqids.size()-1){
					str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\'");
				}else{
					str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\',");
				}
			}
			map.put("askperson", str.toString());
			//传入人员id 查询个人数据
		}else if(YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("personage")){
			map.put("askperson", "\'"+person.getSeqId()+"\'");
		}
		//渠道业绩
		List<JSONObject> findDevchannel = kLogic.findDevchannel(map);
		//总
		JSONObject findDevchannelAll = kLogic.findDevchannelAll(map);
		List<JSONObject> list =new ArrayList<JSONObject>();
		NumberFormat percent = NumberFormat.getPercentInstance();
		percent.setMinimumFractionDigits(3);
		for (JSONObject object : findDevchannel) {
			JSONObject obj= new JSONObject();
			if(object.get("paymoney") != null && !object.get("paymoney").equals("")){
				obj.put("DevChannelName", object.getString("devchannelname"));
				obj.put("performance", object.getString("paymoney"));
				obj.put("proportion",percent.format(Double.valueOf(object.getString("paymoney"))/Double.valueOf(findDevchannelAll.getString("paymoney"))));
			}else{
				obj.put("DevChannelName",object.getString("devchannelname") );
				obj.put("performance", "0");
				obj.put("proportion","0.00%");
			}
			list.add(obj);
		}
		return list;
	}
	/**   
	  * <p>Title: consumptionInterval</p>   
	  * <p>Description: </p>   
	  * @param request
	  * @param map
	  * @return   
	 * @throws Exception 
	  * @see com.hudh.sjtj.service.IDataAnalysisService#consumptionInterval(javax.servlet.http.HttpServletRequest, java.util.Map)   
	  */  
	@Override
	public List<JSONObject> consumptionInterval(HttpServletRequest request, Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		//获取权限可见人员
		YZPerson person = SessionUtil.getLoginPerson(request);
		//有按钮标识和all 查询所有部门人员id
		if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("all")){
			List<JSONObject> personSeqIds=sysDeptPrivDao.findPersonSeqIdByButtonName(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < personSeqIds.size(); i++) {
				if(i==personSeqIds.size()-1){
					str.append("\'"+personSeqIds.get(i).getString("seqid")+"\'");
				}else{
					str.append("\'"+personSeqIds.get(i).getString("seqid")+"\',");
				}
			}
			map.put("askperson", str.toString());
			//传入部门id 查询部门内人员id
		}else if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&!map.get("deptCategory").equals("all")&&!map.get("deptCategory").equals("personage")){
			//seq_id,user_name,user_id
			map.put("dept_id", map.get("deptCategory"));
			List<JSONObject> yzpersonSeqids = yzPersonLogic.selectPersonPaiban(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < yzpersonSeqids.size(); i++) {
				if(i==yzpersonSeqids.size()-1){
					str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\'");
				}else{
					str.append("\'"+yzpersonSeqids.get(i).getString("seq_id")+"\',");
				}
			}
			map.put("askperson", str.toString());
			//传入人员id 查询个人数据
		}else if(YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("personage")){
			map.put("askperson", "\'"+person.getSeqId()+"\'");
		}
				List<JSONObject> list = new ArrayList<JSONObject>();
				for (int i = 0; i < 6; i++) {
					if(i==0){
						map.put("start","0");
						map.put("end", "5000");
						JSONObject json =  kLogic.findConsumptionInterval(map);
						json.put("name", "0~5000");
						list.add(json);
					}
					if(i==1){
						map.put("start", "5000");
						map.put("end", "20000");
						JSONObject json1 =  kLogic.findConsumptionInterval(map);
						json1.put("name", "5000~20000");
						list.add(json1);
					}
					if(i==2){
						map.put("start", "20000");
						map.put("end", "50000");
						JSONObject json2 =  kLogic.findConsumptionInterval(map);
						json2.put("name", "20000~50000");
						list.add(json2);
					}
					if(i==3){
						map.put("start", "50000");
						map.put("end", "100000");
						JSONObject json3 =  kLogic.findConsumptionInterval(map);
						json3.put("name", "50000~100000");
						list.add(json3);
					}
					if(i==4){
						map.put("start", "100000");
						map.put("end", "200000");
						JSONObject json4 =  kLogic.findConsumptionInterval(map);
						json4.put("name", "100000~200000");
						list.add(json4);
					}
					if(i==5){
						map.put("start", "200000");
						JSONObject json5 =  kLogic.findConsumptionInterval(map);
						json5.put("name", "200000以上");
						list.add(json5);
					}
				}	
		return list;
	}

	@Override
	public JSONObject bargainPerformance( Map<String, String> map) throws Exception {

		 //当月新诊初诊成交业绩
		JSONObject json1 =analysisDao.monthlyNewDiagnosisInitial(map);
		 //当月新诊复诊成交业绩
		JSONObject json2 =analysisDao.monthlyNewDiagnosisTurnover(map);
		 //当月新诊再消费
		JSONObject json3 = analysisDao.monthlyNewDiagnosisConsumption(map);
		 //非当月复诊再消费
		JSONObject json4 = analysisDao.notInMonthConsumptionByTurnover(map);
		 //非当月新诊复诊
		JSONObject json5=analysisDao.notInMonthNewDiagnosisByTurnover(map);
		 //非当月再消费
		JSONObject json6=analysisDao.notInMonthConsumption(map);
        //含三项的未成交资源总数
        int a=analysisDao.containsThreeUntradedResources(map);
        //不含三项的未成交资源总数
        int b=analysisDao.noContainsThreeUntradedResources(map) ;
         //含三项的复诊成交数
        int c=analysisDao.containsThreeUntradedSubsequent( map);
        //不含三项的复诊成交数
        int d=analysisDao.noContainsThreeUntradedSubsequent(map);
		JSONObject json=new JSONObject();
        json.put("dyxzcz",json1.getString("actualmoney"));
        json.put("dyxzfz",json2.getString("actualmoney"));
        json.put("dyxzzxf",json3.getString("actualmoney"));
        json.put("fdyfzzxf",json4.getString("actualmoney"));
        json.put("fdyxzfz",json5.getString("actualmoney"));
        json.put("fdyzxf",json6.getString("actualmoney"));
        json.put("fzzyj",new BigDecimal(json2.getString("actualmoney")).add(new BigDecimal(json5.getString("actualmoney"))).toString());
        json.put("dyczyj",new BigDecimal(json1.getString("actualmoney")).add(new BigDecimal(json2.getString("actualmoney"))).add(new BigDecimal(json3.getString("actualmoney"))).toString());
        json.put("fdyfzyj",new BigDecimal(json4.getString("actualmoney")).add(new BigDecimal(json5.getString("actualmoney"))).toString());
        json.put("hsxwcjzyzs",a+"");
        json.put("bhsxwcjzyzs",b+"");
        json.put("hsxfzcj",c+"");
        json.put("bhsxfzcj",d+"");
        return json;
	}
}
