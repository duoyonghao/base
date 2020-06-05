package com.hudh.doctorpick.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hudh.doctorpick.dao.GoodsDoctorPickInDetailDao;
import com.hudh.doctorpick.dao.GoodsPickSendBackDao;
import com.hudh.doctorpick.entity.GoodsDoctorPickInDetail;
import com.hudh.doctorpick.entity.GoodsPickSendBack;
import com.hudh.doctorpick.service.IGoodsPickSendBackService;
import com.hudh.ksll.dao.KsllCollorDetailDao;
import com.hudh.ksll.dao.KsllCollorGoodsDao;
import com.hudh.ksll.entity.KsllCollorDetailPh;
import com.hudh.ksll.entity.KsllGoods;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Service
public class GoodsPickSendBackServiceImpl implements IGoodsPickSendBackService {
	
	@Autowired
	private GoodsPickSendBackDao sendBackDao;
	
	@Autowired
	private GoodsDoctorPickInDetailDao detailDao;
	
	@Autowired
	private KsllCollorGoodsDao ksllCollorGoodsDao;
	
	@Autowired
	private KsllCollorDetailDao ksllCollorDetailDao;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int insertGoodsPickSendBack(GoodsPickSendBack dp, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		dp.setSeqId(YZUtility.getUUID());//关联
		dp.setCreatetime(YZUtility.getCurDateTimeStr());
		sendBackDao.insertGoodsPickSendBack(dp);
		
		GoodsDoctorPickInDetail detail = detailDao.findDoctorPickInDetailByGoodsuuid(dp.getDetailId());
		String quantity = detail.getQuantity();
		Integer i = Integer.valueOf(quantity);
		String nums = dp.getNums();
		Integer j = Integer.valueOf(nums);
		
		JSONObject json = ksllCollorGoodsDao.findKsllGoodsByPrimaryId(dp.getGoodsuuid());
		KsllGoods ksllGoods = (KsllGoods) JSONObject.toBean(json, KsllGoods.class);
		Integer num = ksllGoods.getNums();
		int k = num + j;
		ksllGoods.setNums(k);
		ksllCollorGoodsDao.updateKsllGoodsByPrimaryId(ksllGoods);
		
		int numberno = i - j;
		if (numberno < 0) {
			throw new Exception("退还商品数量大于已领商品数量，请重新输入！");
		}
		String quantity1 = String.valueOf(numberno);
		detail.setQuantity(quantity1);
		detailDao.updateDoctorPickInDetailByGoodsuuid(detail);
		String phids=request.getParameter("phids");
		if(phids!=null&&!phids.equals("")){
			String[] phs = phids.split(",");
			StringBuilder str = new StringBuilder();
			for (int l = 0; l < phs.length; l++) {
				if(l==phs.length-1){
					str.append("\'"+phs[l]+"\'");	
				}else{
					str.append("\'"+phs[l]+"\',");	
				}
			}
			if(str.length()>0){
				List<KsllCollorDetailPh> list = ksllCollorDetailDao.findDetailPhBySeqid(str.toString());
				List<KsllCollorDetailPh> list1=new ArrayList<KsllCollorDetailPh>();
				int n=Integer.parseInt(dp.getNums());
				for (KsllCollorDetailPh ksllCollorDetailPh : list) {
					if((ksllCollorDetailPh.getPhnum()+n)<=ksllCollorDetailPh.getNums()){
						ksllCollorDetailPh.setPhnum(ksllCollorDetailPh.getPhnum()+n);
						list1.add(ksllCollorDetailPh);
						break;
					}else{
						n=n-ksllCollorDetailPh.getNums()-ksllCollorDetailPh.getPhnum();
						ksllCollorDetailPh.setPhnum(ksllCollorDetailPh.getNums());;
						list1.add(ksllCollorDetailPh);
					}
				}
				ksllCollorDetailDao.updateDetailAddPhnumBySeqid(list1);
			}
		}
		return 0;
	}

	@Override
	public List<JSONObject> findGoodsPickSendBackAll(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		List<JSONObject> list = sendBackDao.findGoodsPickSendBackAll(map);
		return list;
	}

	@Override
	public JSONObject findGoodsPickSendBackById(String id) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = sendBackDao.findGoodsPickSendBackById(id);
		return json;
	}

}
