package com.hudh.doctorpick.service.Impl;

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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoodsDoctorPickInServiceImpl
  implements IGoodsDoctorPickInService
{
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
  
  @Transactional(rollbackFor={Exception.class})
  public void insertGoogsPick(GoodsDoctorPickIn dp, String paramDetail, HttpServletRequest request, String costno1)
    throws Exception
  {
    dp.setSeqId(YZUtility.getUUID());
    YZPerson person = SessionUtil.getLoginPerson(request);
    dp.setCreator(person.getSeqId());
    dp.setCreatetime(YZUtility.getCurDateTimeStr());
    String organization = ChainUtil.getCurrentOrganization(request);
    dp.setOrganization(organization);
    dp.setPickPerson(person.getSeqId());
    if (costno1.equals("")) {
      throw new Exception("该患者还没有开单，请添加费用！");
    }
    KqdsCostorder kCostorder = this.logic.findCostOrderByCostno(costno1);
    dp.setUserdocument(kCostorder.getUsername());
    

    List<GoodsDoctorPickInDetail> list = HUDHUtil.parseJsonToObjectList(paramDetail, GoodsDoctorPickInDetail.class);
    KsllGoods ksll = null;
    for (GoodsDoctorPickInDetail detail : list)
    {
      if (detail.getBatchnum().equals("请选择")) {
        detail.setBatchnum("");
      }
      String id = detail.getGoodsuuid();
      JSONObject json = this.ksllCollorDao.findKsllGoodsByPrimaryId(id);
      
      KsllGoods ksllGoods = (KsllGoods)JSONObject.toBean(json, KsllGoods.class);
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
      Integer number = Integer.valueOf(nums.intValue() - i.intValue());
      if (number.intValue() < 0) {
        throw new Exception("输入数量大于科室库存数量，请从新输入！");
      }
      ksll.setNums(number);
      ksll.setId(id);
      this.ksllCollorDao.updateKsllGoodsByPrimaryId(ksll);
      if (!detail.getBatchnum().equals(""))
      {
        Map<String, String> map = new HashMap();
        String deptpart = request.getParameter("deptpart");
        map.put("goodscode", detail.getGoodscode());
        map.put("ph", detail.getBatchnum());
        map.put("deptpart", deptpart);
        List<KsllCollorDetailPh> phlist = this.ksllCollorDetailDao.findDetailPhPriceByGoodscode(map);
        List<KsllCollorDetailPh> updatephlist = new ArrayList();
        
        StringBuilder str = new StringBuilder();
        if (phlist.size() > 0) {
          if (phlist.size() == 1)
          {
            detail.setNuitPrice(((KsllCollorDetailPh)phlist.get(0)).getPrice());
            KsllCollorDetailPh ksll1 = new KsllCollorDetailPh();
            ksll1.setSeqId(((KsllCollorDetailPh)phlist.get(0)).getSeqId());
            ksll1.setNums(i.intValue());
            updatephlist.add(ksll1);
            str.append(((KsllCollorDetailPh)phlist.get(0)).getSeqId());
          }
          else
          {
            BigDecimal money = new BigDecimal(0);
            for (KsllCollorDetailPh ksllCollorDetailPh : phlist)
            {
              if (ksllCollorDetailPh.getPhnum() >= i.intValue())
              {
                money = money.add(ksllCollorDetailPh.getPrice().multiply(new BigDecimal(i.intValue())));
                KsllCollorDetailPh ksll1 = new KsllCollorDetailPh();
                ksll1.setSeqId(ksllCollorDetailPh.getSeqId());
                ksll1.setNums(ksllCollorDetailPh.getPhnum());
                updatephlist.add(ksll1);
                str.append(((KsllCollorDetailPh)phlist.get(0)).getSeqId());
                break;
              }
              money = money.add(ksllCollorDetailPh.getPrice().multiply(new BigDecimal(ksllCollorDetailPh.getPhnum())));
              KsllCollorDetailPh ksll1 = new KsllCollorDetailPh();
              ksll1.setSeqId(ksllCollorDetailPh.getSeqId());
              ksll1.setNums(ksllCollorDetailPh.getPhnum());
              i = Integer.valueOf(i.intValue() - ksllCollorDetailPh.getPhnum());
              updatephlist.add(ksll1);
              str.append(((KsllCollorDetailPh)phlist.get(0)).getSeqId() + ",");
            }
            detail.setNuitPrice(money.divide(new BigDecimal(i.intValue()), 3));
          }
        }
        detail.setPhids(str.toString());
        this.ksllCollorDetailDao.updateDetailPhnumBySeqid(updatephlist);
      }
      else
      {
        Map<String, String> map = new HashMap();
        String deptpart = request.getParameter("deptpart");
        map.put("goodscode", detail.getGoodscode());
        map.put("deptpart", deptpart);
        BigDecimal p = this.ksllCollorDetailDao.findCollorDetailPriceByGoodscode(map);
        detail.setNuitPrice(p);
      }
    }
    this.goodsDoctorPickInDao.insertGoogsPick(dp);
    
    this.goodsDoctorPickInDetailDao.batchSaveInDetail(list);
  }
  
  public List<JSONObject> findAllGoodsDoctorPick(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = this.goodsDoctorPickInDao.findAllGoodsDoctorPick(map);
    return list;
  }
  
  public void deleteGoodsDoctorPickByIncode(String incode)
    throws Exception
  {
    this.goodsDoctorPickInDao.deleteGoodsDoctorPickByIncode(incode);
  }
  
  public void updateGoodsDoctorPickBySeqId(String seqId)
    throws Exception
  {
    this.goodsDoctorPickInDao.updateGoodsDoctorPickBySeqId(seqId);
  }
  
  public GoodsDoctorPickIn findAllGoodsDoctorPickBySeqId(String seqId)
    throws Exception
  {
    GoodsDoctorPickIn goodsDoctorPickIn = this.goodsDoctorPickInDao.findAllGoodsDoctorPickBySeqId(seqId);
    return goodsDoctorPickIn;
  }
  
  public JSONObject findGoodsDoctorPickByIncode(String incode)
    throws Exception
  {
    JSONObject json = this.goodsDoctorPickInDao.findGoodsDoctorPickByIncode(incode);
    return json;
  }
}
