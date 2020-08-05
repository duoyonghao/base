package com.kqds.service.ck;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.core.global.YZActionKeys;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoods;
import com.kqds.entity.base.KqdsCkGoodsInDetail;
import com.kqds.entity.base.KqdsCkGoodsOut;
import com.kqds.entity.base.KqdsCkGoodsOutDetail;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KQDS_Ck_Goods_OutLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;
	/**
	 * 根据供应商统计数量
	 * 
	 * @param conn
	 * @param supplier
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public int countBySupplier(String supplier, String organization) throws Exception {
		JSONObject jobj = new JSONObject();
		jobj.put("supplier", supplier);
		jobj.put("organization", organization);
		int count = (int) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_OUT + ".countBySupplier", jobj);
		return count;
	}

	// 出库明细查询
	@SuppressWarnings("unchecked")
	public List<JSONObject> inSearchList(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_OUT + ".inSearchList", map);
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
			}else if (ConstUtil.CK_OUT_10.equals(outtype)) {
				outtype = "调拨出库";
			}
			job.put("outtype", outtype);
		}
		return list;
	}
	
	/**
	 * 添加科室领料出库禁止多人出库同一单据校验  syp 2019-8-31 start
	  * @Title: findGoodsOut   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param outcode
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: JSONObject
	  * @dateTime:2019年8月31日 下午4:12:35
	 */
	public JSONObject findGoodsOutByOutcode(String ksllCollorId) throws Exception {
		JSONObject json = (JSONObject) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_OUT + ".findGoodsOutByOutcode", ksllCollorId);
		return json;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor= Exception.class)
	public void updateDetailByOutnum(YZPerson person, String menzhen, KqdsCkGoodsOut dp,
			List<KqdsCkGoodsOutDetail> jList, List<JSONObject> numList) throws Exception {
		List<KqdsCkGoodsOutDetail> kList=new ArrayList<KqdsCkGoodsOutDetail>();
		List<KqdsCkGoodsOutDetail> cList=new ArrayList<KqdsCkGoodsOutDetail>();
		List<KqdsCkGoodsInDetail> iList=new ArrayList<KqdsCkGoodsInDetail>();
		for (KqdsCkGoodsOutDetail detail : jList) {
			if(detail.getPh().equals("请选择")){
				kList.add(detail);
			}else{
				cList.add(detail);
			}
		}
		if(kList.size()>0){
			for (KqdsCkGoodsOutDetail detail : kList) {
				//根据商品id和数量获取入库明细
				String goodsuuid=detail.getGoodsuuid();
				//要出库的数量
				int outNum=detail.getOutnum();
				int out=outNum;
				//时间查询商品所有入库信息
				List<JSONObject> list1 = new ArrayList<JSONObject>();
				int innum = 0;
				int unnum = 0;
				Map<String, String> map2 = new HashMap<String, String>();
				if(dp.getType().equals("2")){
					map2.put("goodsuuid", goodsuuid);
					map2.put("type", dp.getType());
					list1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN + ".timeOrder", map2);	
					//已出库数量
					//int outnum = glogic.getOutNumByGoodsuuid(goodsuuid);
					//入库的数量
					innum = (int) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL +".selectNum", map2);
					//未出库数量查询仓库余量
					unnum = (int) dao.findForObject(TableNameUtil.KQDS_CK_GOODS + ".cknum", goodsuuid);
				}else{
					map2.put("goodsuuid", goodsuuid);
					map2.put("type", dp.getType());
					list1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN + ".timeOrder", map2);
					//已出库数量
					//int outnum = glogic.getOutNumByGoodsuuid(goodsuuid);
					//入库的数量
					innum = (int) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL +".selectNum", map2);
					//未出库数量查询仓库余量
					unnum = (int) dao.findForObject(TableNameUtil.KQDS_CK_GOODS + ".cknums", goodsuuid);
				}
				int outnum=innum-unnum;
				BigDecimal money=new BigDecimal(0);
				if(outNum==unnum){
					// 添加剩余所有的商品
					// 首个入库的剩余数量
					int num=0;
					int n=0;
					for (int i = 0; i < list1.size(); i++) {
						//单件商品的数量
						int in1=list1.get(i).getInt("innum");
						if(in1+num<outnum){
							num+=in1;
						}else{
							n=i;
							break;
						}
					}
					//
					int dan=outnum-num;
					//存入第一个出库表 同一个编号
					//数量 
					int i1=list1.get(n).getInt("innum")-dan;
					if(i1==0){
						money = addOutDetail(person, menzhen, dp, detail, list1, n+1, outNum);
						for (int i = n+2; i < list1.size(); i++) {
							if(list1.get(i).getInt("innum")<=(outNum-i1)){
								//存入剩余所有
								money = addOut(person, menzhen, dp, detail,list1.get(i).getInt("innum") , list1, money, i);
								i1=i1+list1.get(i).getInt("innum");
							}
						}
					}else if(i1>0){
						money = addOutDetail(person, menzhen, dp, detail, list1, n, i1);
						for (int i = n+1; i < list1.size(); i++) {
							if(list1.get(i).getInt("innum")<=(outNum-i1)){
								//存入剩余所有
								money = addOut(person, menzhen, dp, detail,list1.get(i).getInt("innum") , list1, money, i);
								i1=i1+list1.get(i).getInt("innum");
							}
						}
					}
					
				}else if(outNum<unnum){
					//添加相关商品
					int num=0;
					int n=0;
					for (int i = 0; i < list1.size(); i++) {
						//单件商品的数量
						int in1=list1.get(i).getInt("innum");
						if(in1+num<=outnum){
							num+=in1;
						}else{
							n=i;
							break;
						}
					}
					//商品多出来的数量
					int dan = 0;
					if(num==0){
						dan=0;
					}else if(num>0){
						dan=outnum - num;
					}
					//存入第一个出库表 同一个SEQ_ID
					if(list1.get(n).getInt("innum")<outNum&&dan==0){
						//存入第一个商品全部，其他进行循环添加
						if(list1.get(n).getInt("innum")==0){
							break;
						}
						money = addOutDetail(person, menzhen, dp, detail, list1, n, list1.get(n).getInt("innum"));
						outNum=outNum-list1.get(n).getInt("innum");
						for (int i = n+1; i < list1.size(); i++) {
							if(list1.get(i).getInt("innum")>=outNum){
								//存入outNum数量
								money = addOut(person, menzhen, dp, detail, outNum, list1, money, i);
								break;
							}else{
								//存入全部
								money = addOut(person, menzhen, dp, detail, list1.get(i).getInt("innum"), list1, money, i);
								outNum=outNum-list1.get(i).getInt("innum");
							}
						}
					}else if(list1.get(n).getInt("innum")>=outNum&&dan==0){
						if(list1.get(n).getInt("innum")==0){
							break;
						}
						money = addOutDetail(person, menzhen, dp, detail, list1, n, outNum);
					}else if((list1.get(n).getInt("innum")-dan)>=outNum&&dan>0){
						if(list1.get(n).getInt("innum")==0){
							break;
						}
						money = addOutDetail(person, menzhen, dp, detail, list1, n, outNum);
					}else if(list1.get(n).getInt("innum")-dan<outNum&&dan>0 ){
						if(list1.get(n).getInt("innum")==0){
							break;
						}
						//存入第一个商品-dan的数量，其他进行循环添加
						money = addOutDetail(person, menzhen, dp, detail, list1, n, list1.get(n).getInt("innum")-dan);
						outNum=outNum-(list1.get(n).getInt("innum")-dan);
						for (int i = n+1; i < list1.size(); i++) {
							if(list1.get(i).getInt("innum")>=outNum){
								money = addOut(person, menzhen, dp, detail, outNum, list1, money, i);
								break;
							}else{
								//存入全部
								money = addOut(person, menzhen, dp, detail, list1.get(i).getInt("innum"), list1, money, i);
								outNum=outNum-list1.get(i).getInt("innum");
							}
						}
					}
				}
				//商品出库后剩余数量
				int syNum=innum-out-outnum;
				if(syNum<0){
					syNum=0;
				}
				// 商品表 更新商品库存 ；等待出库数量 等待出库数量 因没有审核功能 暂不维护
				// 根据商品主键 查询商品
				Map<String, String> map = new HashMap<String, String>();
				//map.put("sshouse", dp.getOuthouse());
				map.put("goodsdetailid", detail.getGoodsuuid());
				List<KqdsCkGoods> list = (List<KqdsCkGoods>) dao.loadList(TableNameUtil.KQDS_CK_GOODS, map);
				if (list == null) {
					throw new Exception("不存在商品");
				}
				KqdsCkGoods goods = list.get(0);
				if (goods.getNums() < detail.getOutnum()) {
					throw new Exception("库存不足");
				}
				goods.setNums(syNum);
				//goods.setNums(goods.getNums() - detail.getOutnum());
				// 更新 商品表 单价 及 金额
				// 金额 = 原金额 - 出库金额
				// 单价 = 商品表剩余金额/剩余库存
				BigDecimal kcmoney = goods.getKcmoney().subtract(money);
				//BigDecimal kcmoney = goods.getKcmoney().subtract(detail.getCkmoney());
				goods.setKcmoney(kcmoney);
				BigDecimal goodsprice = null;
				if (goods.getNums() == 0) {// 出库后，库存为0
					goodsprice = BigDecimal.ZERO;
					goods.setKcmoney(BigDecimal.ZERO);
				} else {
					goodsprice = kcmoney.divide(new BigDecimal(goods.getNums()), 3, RoundingMode.HALF_EVEN).setScale(3, BigDecimal.ROUND_HALF_DOWN);
				}
				goods.setGoodsprice(goodsprice);
				dao.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
			}
		}
		if(cList.size()>0){
			List<KqdsCkGoodsOutDetail> bList=new ArrayList<KqdsCkGoodsOutDetail>();
			for (JSONObject jsonObject : numList) {
				KqdsCkGoodsInDetail in =new KqdsCkGoodsInDetail();
				in.setSeqId(jsonObject.getString("seqid"));
				in.setPhnum(jsonObject.getInt("cknum"));
				iList.add(in);
				KqdsCkGoodsOutDetail dd= new KqdsCkGoodsOutDetail();
				dd.setSeqId(YZUtility.getUUID());
				for (KqdsCkGoodsOutDetail detail : cList) {
					if(jsonObject.getString("goodsuuid").equals(detail.getGoodsuuid())&&jsonObject.getString("ph").equals(detail.getPh())){
						if(!detail.getSqremark().equals("")){
							dd.setSqremark(detail.getSqremark());
						}
						dd.setPh(detail.getPh());
						dd.setPhnum(detail.getPhnum());
						dd.setGoodsuuid(detail.getGoodsuuid());
						dd.setOutnum(jsonObject.getInt("cknum"));
						dd.setOutprice(new BigDecimal(jsonObject.getString("inprice")));
						dd.setCkmoney((new BigDecimal(jsonObject.getString("inprice"))).multiply(new BigDecimal(jsonObject.getInt("cknum"))));
						dd.setOutcode(dp.getOutcode());
						dd.setOrganization(menzhen);
						dd.setCreateuser(person.getSeqId());
						dd.setCreatetime(dp.getCreatetime());
						dd.setYxdate(jsonObject.getString("yxdate"));
						//入库编号和入库时间
						dd.setAddTime(jsonObject.getString("createtime"));
						dd.setAddNumber(jsonObject.getString("incode"));
						dd.setType(dp.getType());
						bList.add(dd);
						break;
					}
				}
			}
			//修改入库明细表
			List<KqdsCkGoods> gList=new ArrayList<KqdsCkGoods>();
			if(cList.size()==1){
				KqdsCkGoods goods=new KqdsCkGoods();
				goods.setGoodsdetailid(cList.get(0).getGoodsuuid());
				goods.setKcmoney(cList.get(0).getCkmoney());
				goods.setNums(cList.get(0).getOutnum());
				goods.setNumsexport(cList.get(0).getPhnum());
				int nums=goods.getNums();
				int phnum=cList.get(0).getPhnum();
				if(nums==phnum){
					goods.setGoodsprice(new BigDecimal(0));
				}else{
					goods.setGoodsprice(new BigDecimal(1));
				}
				gList.add(goods);
			}else{
				for (int i = 0; i < cList.size(); i++) {
					BigDecimal kcmoney= cList.get(i).getCkmoney();
					int nums=cList.get(i).getOutnum();
					int numsexport=cList.get(i).getPhnum();
					for (int j = 0; j < cList.size(); j++) {
						if(i!=j&&cList.get(i).getGoodsuuid().equals(cList.get(j).getGoodsuuid())){
							kcmoney=kcmoney.add(cList.get(j).getCkmoney());
							nums=nums+cList.get(j).getOutnum();
							numsexport=numsexport+cList.get(j).getPhnum();
						}
					}
					KqdsCkGoods goods=new KqdsCkGoods();
					goods.setGoodsdetailid(cList.get(i).getGoodsuuid());
					goods.setKcmoney(kcmoney);
					goods.setNums(nums);
					goods.setNumsexport(numsexport);
					if(nums==numsexport){
						goods.setGoodsprice(new BigDecimal(0));
					}else{
						goods.setGoodsprice(new BigDecimal(1));
					}
					gList.add(goods);
				}
			}
			if(bList.size()>0){
				//添加出库明细
				dao.batchInsert(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL+".insertSelective", bList);
			}
			if(iList.size()>0){
				//修改入库明细表数据
				dao.batchUpdate(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL+".updateGoodsInDetailByNumList", iList);	
				
			}
			if(gList.size()==1){
				dao.batchUpdate(TableNameUtil.KQDS_CK_GOODS+".updateGoodsByNumList", gList);
			}else if(gList.size()>1){
				//批量修改仓库表数据
				List<KqdsCkGoods> hList=new ArrayList<KqdsCkGoods>();
				for (KqdsCkGoods kqdsCkGoods : gList) {
			      boolean b = hList.stream().anyMatch(k -> k.getGoodsdetailid().equals(kqdsCkGoods.getGoodsdetailid()));
			      if (!b) {
			    	  hList.add(kqdsCkGoods);
			      }
				}
				
				dao.batchUpdate(TableNameUtil.KQDS_CK_GOODS+".updateGoodsByNumList", hList);
			}
		}
	}
	private BigDecimal addOut(YZPerson person, String menzhen, KqdsCkGoodsOut dp, KqdsCkGoodsOutDetail detail,
			int outNum, List<JSONObject> list1, BigDecimal money, int i) throws Exception {
		detail.setSeqId(YZUtility.getUUID());
		detail.setOutnum(outNum);
		String inprice1 = (String) list1.get(i).get("inprice");
		detail.setOutprice(new BigDecimal(inprice1));
		detail.setCkmoney((new BigDecimal(inprice1)).multiply(new BigDecimal(outNum)));
		money=money.add(detail.getCkmoney());
		detail.setOutcode(dp.getOutcode());
		detail.setOrganization(menzhen);
		detail.setCreateuser(person.getSeqId());
		detail.setCreatetime(dp.getCreatetime());
		
		//入库编号和入库时间
		detail.setAddTime(list1.get(i).getString("createtime"));
		detail.setAddNumber(list1.get(i).getString("incode"));
		
		dao.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, detail);
		return money;
	}
	private BigDecimal addOutDetail(YZPerson person, String menzhen, KqdsCkGoodsOut dp, KqdsCkGoodsOutDetail detail,
			List<JSONObject> list1, int n, int i12) throws Exception {
		BigDecimal money;
		String detailid = YZUtility.getUUID();
		detail.setSeqId(detailid);
		detail.setOutnum(i12);
		String inprice = (String) list1.get(n).get("inprice");
		detail.setOutprice(new BigDecimal(inprice) );
		detail.setCkmoney((new BigDecimal(inprice)).multiply(new BigDecimal(i12)));
		money=detail.getCkmoney();
		detail.setOutcode(dp.getOutcode());
		detail.setOrganization(menzhen);
		detail.setCreateuser(person.getSeqId());
		detail.setCreatetime(dp.getCreatetime());
		//入库编号和入库时间
		detail.setAddTime(list1.get(n).getString("createtime"));
		detail.setAddNumber(list1.get(n).getString("incode"));
		detail.setPh("");
		
		dao.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, detail);
		return money;
	}
	@SuppressWarnings("unchecked")
	public String insertOut(HttpServletRequest request, HttpServletResponse response, YZPerson person, String menzhen,
			KqdsCkGoodsOut dp) throws Exception, IOException {
		// 验证出库单号是否重复
		Map<String, String> mapin = new HashMap<String, String>();
		mapin.put("outcode", dp.getOutcode());
		List<KqdsCkGoodsOut> outList = (List<KqdsCkGoodsOut>) dao.loadList(TableNameUtil.KQDS_CK_GOODS_OUT, mapin);
		if (outList.size() > 0) {
			JSONObject retrunObj = new JSONObject();
			retrunObj.put(YZActionKeys.JSON_RET_STATES, "100"); // ###
																// 特殊处理
			retrunObj.put(YZActionKeys.JSON_RET_MSRGS, "出库单号已存在，系统已自动重新获取");
			PrintWriter pw = response.getWriter();
			pw.println(retrunObj.toString());
			pw.flush();
			return null;
		}
		//添加出库记录
		dp.setSeqId(YZUtility.getUUID());
		dp.setOrganization(menzhen);
		// 现在出库不需要审核功能，暂把审核、出库、申请 都一致保存申请的人及时间；状态设为3（已出库）
		dp.setCreatetime(YZUtility.getCurDateTimeStr());
		dp.setCreateuser(person.getSeqId());
		dp.setShtime(dp.getCreatetime());
		dp.setAuditor(person.getSeqId());
		// dp.setCktime(dp.getCreatetime());
		dp.setCkr(person.getSeqId());
		dp.setOutstatus(3);
		dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
		dao.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT, dp);
		return "添加成功";
	}
	/**
	 * 仓库余量
	 * @return
	 * @throws Exception
	 */
	public int ckNums1(String goodsuuid) throws Exception {
		int i = (int) dao.findForObject(TableNameUtil.KQDS_CK_GOODS + ".cknums", goodsuuid);
		return i;
	}
	
	/**
	 * 仓库余量
	 * @return
	 * @throws Exception
	 */
	public int ckNums2(String goodsuuid) throws Exception {
		int i = (int) dao.findForObject(TableNameUtil.KQDS_CK_GOODS + ".cknum", goodsuuid);
		return i;
	}
}