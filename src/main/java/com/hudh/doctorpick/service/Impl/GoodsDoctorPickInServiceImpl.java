package com.hudh.doctorpick.service.Impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hudh.doctorpick.dao.GoodsDoctorPickInDao;
import com.hudh.doctorpick.dao.GoodsDoctorPickInDetailDao;
import com.hudh.doctorpick.entity.GoodsDoctorPickIn;
import com.hudh.doctorpick.entity.GoodsDoctorPickInDetail;
import com.hudh.doctorpick.service.IGoodsDoctorPickInService;
import com.hudh.ksll.dao.KsllCollorDetailDao;
import com.hudh.ksll.dao.KsllCollorGoodsDao;
import com.hudh.ksll.entity.KsllCollorDetailPh;
import com.hudh.ksll.entity.KsllGoods;
import com.hudh.util.HUDHUtil;
import com.kqds.entity.base.KqdsCostorder;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

@Service
public class GoodsDoctorPickInServiceImpl implements IGoodsDoctorPickInService {
	
	@Autowired
	private KQDS_CostOrderLogic logic;
	
	@Autowired
	private GoodsDoctorPickInDao goodsDoctorPickInDao;
	
	@Autowired
	private GoodsDoctorPickInDetailDao goodsDoctorPickInDetailDao;
	
	@Autowired
	private KsllCollorGoodsDao ksllCollorDao;
	
	@Autowired
	private KsllCollorDetailDao ksllCollorDetailDao;

	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })
	@Override
	public void insertGoogsPick(GoodsDoctorPickIn dp, String paramDetail, HttpServletRequest request, String costno1) throws Exception {
		dp.setSeqId(YZUtility.getUUID());
		YZPerson person = SessionUtil.getLoginPerson(request);
		dp.setCreator(person.getSeqId());
		dp.setCreatetime(YZUtility.getCurDateTimeStr());
		String organization = ChainUtil.getCurrentOrganization(request); // 获取当前所在门诊
		dp.setOrganization(organization);
		dp.setPickPerson(person.getSeqId());
		if (costno1.equals("")) {
			throw new Exception("该患者还没有开单，请添加费用！");
		}
	    KqdsCostorder kCostorder = logic.findCostOrderByCostno(costno1);
	    dp.setUserdocument(kCostorder.getUsername());
//		JSONArray jArray = JSONArray.fromObject(paramsGoodsDetail);
//		List<GoodsDoctorPickInDetail> list = (List<GoodsDoctorPickInDetail>) JSONArray.toCollection(jArray, GoodsDoctorPickInDetail.class);
	    List<GoodsDoctorPickInDetail> list = HUDHUtil.parseJsonToObjectList(paramDetail, GoodsDoctorPickInDetail.class);
	    KsllGoods ksll = null;
		for (GoodsDoctorPickInDetail detail : list) {
			if(detail.getBatchnum().equals("请选择")){
				detail.setBatchnum("");
			}
			String id = detail.getGoodsuuid();
			JSONObject json = ksllCollorDao.findKsllGoodsByPrimaryId(id);
//			Object nums = json.get("nums");
			KsllGoods ksllGoods = (KsllGoods) JSONObject.toBean(json, KsllGoods.class);
			Integer nums = ksllGoods.getNums();
			ksll = new KsllGoods();
			detail.setCreatetime(YZUtility.getCurDateTimeStr());
			detail.setSeqId(YZUtility.getUUID());
			detail.setOrganization(organization);
			detail.setCreateuser(person.getSeqId());
			detail.setIncode(dp.getIncode());
			detail.setUserdocument(dp.getUserdocument());
			String num = detail.getQuantity();
			Integer i = Integer.valueOf(num);
			Integer number = nums - i;
			if (number < 0) {
				throw new Exception("输入数量大于科室库存数量，请从新输入！");
			}
			ksll.setNums(number);
			ksll.setId(id);
			ksllCollorDao.updateKsllGoodsByPrimaryId(ksll);
			if(!detail.getBatchnum().equals("")){
				Map<String,String> map = new HashMap<String,String>();
				String deptpart=request.getParameter("deptpart");
				map.put("goodscode", detail.getGoodscode());
				map.put("ph", detail.getBatchnum());
				map.put("deptpart", deptpart);
				List<KsllCollorDetailPh> phlist = ksllCollorDetailDao.findDetailPhPriceByGoodscode(map);
				List<KsllCollorDetailPh> updatephlist=new ArrayList<KsllCollorDetailPh>();
				//查询部门商品单价 减去批号数量
				StringBuilder str= new StringBuilder();
				if(phlist.size()>0){
					if(phlist.size()==1){
						detail.setNuitPrice(phlist.get(0).getPrice());
						KsllCollorDetailPh ksll1 = new KsllCollorDetailPh();
						ksll1.setSeqId(phlist.get(0).getSeqId());
						ksll1.setNums(i);
						updatephlist.add(ksll1);
						str.append(phlist.get(0).getSeqId());
					}else{
						BigDecimal money = new BigDecimal(0);
						for (KsllCollorDetailPh ksllCollorDetailPh : phlist) {
							if(ksllCollorDetailPh.getPhnum()>=i){
								//批号数量比出库数量多,确定单价
								money=money.add(ksllCollorDetailPh.getPrice().multiply(new BigDecimal(i)));
								KsllCollorDetailPh ksll1 = new KsllCollorDetailPh();
								ksll1.setSeqId(ksllCollorDetailPh.getSeqId());
								ksll1.setNums(ksllCollorDetailPh.getPhnum());
								updatephlist.add(ksll1);
								str.append(phlist.get(0).getSeqId());
								break;
							}else{
								money=money.add(ksllCollorDetailPh.getPrice().multiply(new BigDecimal(ksllCollorDetailPh.getPhnum())));
								KsllCollorDetailPh ksll1 = new KsllCollorDetailPh();
								ksll1.setSeqId(ksllCollorDetailPh.getSeqId());
								ksll1.setNums(ksllCollorDetailPh.getPhnum());
								i=i-ksllCollorDetailPh.getPhnum();
								updatephlist.add(ksll1);
								str.append(phlist.get(0).getSeqId()+",");
							}
						}
						detail.setNuitPrice(money.divide(new BigDecimal(i), 3));
					}
				}
				detail.setPhids(str.toString());
				ksllCollorDetailDao.updateDetailPhnumBySeqid(updatephlist);
			}else{
				//查询无批号商品单价
				Map<String,String> map = new HashMap<String,String>();
				String deptpart=request.getParameter("deptpart");
				map.put("goodscode", detail.getGoodscode());
				map.put("deptpart", deptpart);
				BigDecimal p = ksllCollorDetailDao.findCollorDetailPriceByGoodscode(map);
				detail.setNuitPrice(p);
			}
		}
		goodsDoctorPickInDao.insertGoogsPick(dp);
		
		goodsDoctorPickInDetailDao.batchSaveInDetail(list);
	}

	@Override
	public List<JSONObject> findAllGoodsDoctorPick(Map<String, String> map) throws Exception {
		List<JSONObject> list = goodsDoctorPickInDao.findAllGoodsDoctorPick(map);
		return list;
	}

	@Override
	public void deleteGoodsDoctorPickByIncode(String incode) throws Exception {
		goodsDoctorPickInDao.deleteGoodsDoctorPickByIncode(incode);
	}

	@Override
	public void updateGoodsDoctorPickBySeqId(String seqId) throws Exception {
		// TODO Auto-generated method stub
		goodsDoctorPickInDao.updateGoodsDoctorPickBySeqId(seqId);
	}

	@Override
	public GoodsDoctorPickIn findAllGoodsDoctorPickBySeqId(String seqId) throws Exception {
		// TODO Auto-generated method stub
		GoodsDoctorPickIn goodsDoctorPickIn = goodsDoctorPickInDao.findAllGoodsDoctorPickBySeqId(seqId);
		return goodsDoctorPickIn;
	}

	@Override
	public JSONObject findGoodsDoctorPickByIncode(String incode) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = goodsDoctorPickInDao.findGoodsDoctorPickByIncode(incode);
		return json;
	}
	
}
