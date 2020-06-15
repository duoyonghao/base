package com.hudh.doctorpick.service.Impl;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoodsPickSendBackServiceImpl
  implements IGoodsPickSendBackService
{
  @Autowired
  private GoodsPickSendBackDao sendBackDao;
  @Autowired
  private GoodsDoctorPickInDetailDao detailDao;
  @Autowired
  private KsllCollorGoodsDao ksllCollorGoodsDao;
  @Autowired
  private KsllCollorDetailDao ksllCollorDetailDao;
  
  @Transactional(rollbackFor={Exception.class})
  public int insertGoodsPickSendBack(GoodsPickSendBack dp, HttpServletRequest request)
    throws Exception
  {
    dp.setSeqId(YZUtility.getUUID());
    dp.setCreatetime(YZUtility.getCurDateTimeStr());
    this.sendBackDao.insertGoodsPickSendBack(dp);
    
    GoodsDoctorPickInDetail detail = this.detailDao.findDoctorPickInDetailByGoodsuuid(dp.getDetailId());
    String quantity = detail.getQuantity();
    Integer i = Integer.valueOf(quantity);
    String nums = dp.getNums();
    Integer j = Integer.valueOf(nums);
    
    JSONObject json = this.ksllCollorGoodsDao.findKsllGoodsByPrimaryId(dp.getGoodsuuid());
    KsllGoods ksllGoods = (KsllGoods)JSONObject.toBean(json, KsllGoods.class);
    Integer num = ksllGoods.getNums();
    int k = num.intValue() + j.intValue();
    ksllGoods.setNums(Integer.valueOf(k));
    this.ksllCollorGoodsDao.updateKsllGoodsByPrimaryId(ksllGoods);
    
    int numberno = i.intValue() - j.intValue();
    if (numberno < 0) {
      throw new Exception("退还商品数量大于已领商品数量，请重新输入！");
    }
    String quantity1 = String.valueOf(numberno);
    detail.setQuantity(quantity1);
    this.detailDao.updateDoctorPickInDetailByGoodsuuid(detail);
    String phids = request.getParameter("phids");
    if ((phids != null) && (!phids.equals("")))
    {
      String[] phs = phids.split(",");
      StringBuilder str = new StringBuilder();
      for (int l = 0; l < phs.length; l++) {
        if (l == phs.length - 1) {
          str.append("'" + phs[l] + "'");
        } else {
          str.append("'" + phs[l] + "',");
        }
      }
      if (str.length() > 0)
      {
        List<KsllCollorDetailPh> list = this.ksllCollorDetailDao.findDetailPhBySeqid(str.toString());
        List<KsllCollorDetailPh> list1 = new ArrayList();
        int n = Integer.parseInt(dp.getNums());
        for (KsllCollorDetailPh ksllCollorDetailPh : list)
        {
          if (ksllCollorDetailPh.getPhnum() + n <= ksllCollorDetailPh.getNums())
          {
            ksllCollorDetailPh.setPhnum(ksllCollorDetailPh.getPhnum() + n);
            list1.add(ksllCollorDetailPh);
            break;
          }
          n = n - ksllCollorDetailPh.getNums() - ksllCollorDetailPh.getPhnum();
          ksllCollorDetailPh.setPhnum(ksllCollorDetailPh.getNums());
          list1.add(ksllCollorDetailPh);
        }
        this.ksllCollorDetailDao.updateDetailAddPhnumBySeqid(list1);
      }
    }
    return 0;
  }
  
  public List<JSONObject> findGoodsPickSendBackAll(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = this.sendBackDao.findGoodsPickSendBackAll(map);
    return list;
  }
  
  public JSONObject findGoodsPickSendBackById(String id)
    throws Exception
  {
    JSONObject json = this.sendBackDao.findGoodsPickSendBackById(id);
    return json;
  }
}
