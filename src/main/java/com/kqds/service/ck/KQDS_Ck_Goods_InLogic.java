package com.kqds.service.ck;

import com.hudh.lclj.dao.SysParaDao;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoods;
import com.kqds.entity.base.KqdsCkGoodsIn;
import com.kqds.entity.base.KqdsCkGoodsInDetail;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_Ck_Goods_InLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  @Autowired
  private SysParaDao sysParaDao;
  @Autowired
  private KQDS_Ck_Goods_In_DetailLogic logic;
  
  public int countBySupplier(String supplier, String organization)
    throws Exception
  {
    JSONObject jobj = new JSONObject();
    jobj.put("supplier", supplier);
    jobj.put("organization", organization);
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS_IN + ".countBySupplier", jobj)).intValue();
    return count;
  }
  
  public List<JSONObject> inSearchList(String table, Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN + ".inSearchList", map);
    for (JSONObject job : list)
    {
      String intype = job.getString("intype");
      if (ConstUtil.CK_IN_0.equals(intype)) {
        intype = "采购入库";
      } else if (ConstUtil.CK_IN_2.equals(intype)) {
        intype = "换货入库";
      } else if (ConstUtil.CK_IN_4.equals(intype)) {
        intype = "其他入库";
      } else if (ConstUtil.CK_IN_8.equals(intype)) {
        intype = "调拨入库";
      } else if (ConstUtil.CK_IN_9.equals(intype)) {
        intype = "二次入库";
      }
      job.put("intype", intype);
    }
    return list;
  }
  
  public String selectZczh(String goodsid, String organization)
    throws Exception
  {
    JSONObject jobj = new JSONObject();
    jobj.put("goodsuuid", goodsid);
    jobj.put("organization", organization);
    String zczh = (String)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".selectZczh", jobj);
    return zczh;
  }
  
  public String selectInprice(String goodsid, String organization)
    throws Exception
  {
    JSONObject jobj = new JSONObject();
    jobj.put("goodsuuid", goodsid);
    jobj.put("organization", organization);
    String inprice = (String)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".selectInprice", jobj);
    return inprice;
  }
  
  public String selectCd(String goodsid, String organization)
    throws Exception
  {
    JSONObject jobj = new JSONObject();
    jobj.put("goodsuuid", goodsid);
    jobj.put("organization", organization);
    String cd = (String)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".selectCd", jobj);
    return cd;
  }
  
  public List<JSONObject> inList(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN + ".timeOrder", map);
    return list;
  }
  
  public List<JSONObject> inListByPh(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN + ".timeOrderByPh", map);
    return list;
  }
  
  public JSONObject selectPriceByPh(String goodsuuid, String ph, int outNum, String type)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("goodsuuid", goodsuuid);
    map.put("ph", ph);
    map.put("type", type);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".selectPriceByPh", map);
    JSONObject json = new JSONObject();
    if (list.size() > 0)
    {
      int m = 0;
      int j = 0;
      for (int i = 0; i < list.size(); i++)
      {
        m += ((JSONObject)list.get(i)).getInt("phnum");
        if (m >= outNum)
        {
          j = i;
          break;
        }
      }
      List<JSONObject> numlist = new ArrayList();
      if (j > 0)
      {
        BigDecimal money = new BigDecimal("0");
        BigDecimal moneyAll = new BigDecimal("0");
        int out = outNum;
        for (int i = 0; i < list.size(); i++)
        {
          JSONObject obj = new JSONObject();
          if (((JSONObject)list.get(i)).getInt("phnum") >= outNum)
          {
            money = new BigDecimal(((JSONObject)list.get(i)).getString("inprice")).multiply(new BigDecimal(outNum));
            moneyAll = moneyAll.add(money);
            obj.put("cknum", Integer.valueOf(outNum));
            obj.put("inprice", ((JSONObject)list.get(i)).getString("inprice"));
            obj.put("seqid", ((JSONObject)list.get(i)).getString("seqid"));
            obj.put("goodsuuid", ((JSONObject)list.get(i)).getString("goodsuuid"));
            obj.put("createtime", ((JSONObject)list.get(i)).getString("createtime"));
            obj.put("incode", ((JSONObject)list.get(i)).getString("incode"));
            obj.put("yxdate", ((JSONObject)list.get(i)).getString("yxdate"));
            obj.put("ph", ph);
            numlist.add(obj);
            break;
          }
          money = new BigDecimal(((JSONObject)list.get(i)).getString("inprice")).multiply(new BigDecimal(((JSONObject)list.get(i)).getInt("phnum")));
          outNum -= ((JSONObject)list.get(i)).getInt("phnum");
          moneyAll = moneyAll.add(money);
          obj.put("cknum", Integer.valueOf(((JSONObject)list.get(i)).getInt("phnum")));
          obj.put("inprice", ((JSONObject)list.get(i)).getString("inprice"));
          obj.put("seqid", ((JSONObject)list.get(i)).getString("seqid"));
          obj.put("goodsuuid", ((JSONObject)list.get(i)).getString("goodsuuid"));
          obj.put("createtime", ((JSONObject)list.get(i)).getString("createtime"));
          obj.put("incode", ((JSONObject)list.get(i)).getString("incode"));
          obj.put("yxdate", ((JSONObject)list.get(i)).getString("yxdate"));
          obj.put("ph", ph);
          numlist.add(obj);
        }
        json.put("goodsprice", moneyAll.divide(new BigDecimal(out), 3, RoundingMode.HALF_UP));
        json.put("ckmoney", moneyAll);
        json.put("numlist", numlist);
        return json;
      }
      if ((((JSONObject)list.get(0)).getString("inprice") != null) && (!((JSONObject)list.get(0)).getString("inprice").equals("")))
      {
        json.put("goodsprice", ((JSONObject)list.get(0)).getString("inprice"));
        json.put("ckmoney", new BigDecimal(((JSONObject)list.get(0)).getString("inprice")).multiply(new BigDecimal(outNum)));
      }
      else
      {
        json.put("goodsprice", new BigDecimal("0.00"));
        json.put("ckmoney", new BigDecimal("0.00"));
      }
      ((JSONObject)list.get(0)).put("cknum", Integer.valueOf(outNum));
      numlist.add((JSONObject)list.get(0));
      json.put("numlist", numlist);
      return json;
    }
    return null;
  }
  
  public int selectNum(Map<String, String> map)
    throws Exception
  {
    int i = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".selectNum", map)).intValue();
    return i;
  }
  
  public List<JSONObject> getParaValueListByName(HttpServletRequest request, String organization)
    throws Exception
  {
    List<String> list = new ArrayList();
    if (organization.equals("HUDH")) {
      list.add("GOODS_IN_ADMIN");
    } else if (organization.equals("HUDX")) {
      list.add("GOODS_IN_ADMIN_XZM");
    }
    List<JSONObject> json = this.sysParaDao.getParaValueListByName(list, request, organization);
    return json;
  }
  
  public JSONObject getParaValueByName(HttpServletRequest request, String organization)
    throws Exception
  {
    String paraValue = null;
    if (organization.equals("HUDH")) {
      paraValue = "GOODS_IN_ADMIN";
    } else if (organization.equals("HUDX")) {
      paraValue = "GOODS_IN_ADMIN_XZM";
    }
    return this.sysParaDao.getParaValueByName(paraValue, request, organization);
  }
  
  public List<JSONObject> goodsInSelectList(String table, Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN + ".goodsInSelectList", map);
    for (JSONObject job : list)
    {
      String intype = job.getString("intype");
      if (ConstUtil.CK_IN_0.equals(intype)) {
        intype = "采购入库";
      } else if (ConstUtil.CK_IN_2.equals(intype)) {
        intype = "换货入库";
      } else if (ConstUtil.CK_IN_4.equals(intype)) {
        intype = "其他入库";
      } else if (ConstUtil.CK_IN_8.equals(intype)) {
        intype = "调拨入库";
      } else if (ConstUtil.CK_IN_9.equals(intype)) {
        intype = "二次入库";
      }
      job.put("intype", intype);
    }
    return list;
  }
  
  public void updateCheckStatus(String goodsinid)
    throws Exception
  {
    this.dao.update(TableNameUtil.KQDS_CK_GOODS_IN + ".updateCheckStatus", goodsinid);
  }
  
  public void updateAuditStatus(String incode)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("incode", incode);
    this.dao.update(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".updateAuditStatus", map);
  }
  
  public void goodsAddInStock(String goodsinid, String menzhen)
    throws Exception
  {
    KqdsCkGoodsIn dp = findCkGoodsInById(goodsinid);
    List<KqdsCkGoodsInDetail> jList = this.logic.findCkGoodsDetailByInid(goodsinid);
    for (KqdsCkGoodsInDetail detail : jList)
    {
      Map<String, String> map = new HashMap();
      map.put("goodsdetailid", detail.getGoodsuuid());
      List<KqdsCkGoods> list = null;
      if (detail.getType().equals("2"))
      {
        JSONObject json = this.logic.findGoodsDetailById(map);
        Map<String, String> map2 = new HashMap();
        map2.put("goodscode", json.getString("goodscode"));
        map2.put("organization", detail.getOrganization());
        JSONObject object = this.logic.findGoodsBycodeAndType(map2);
        Map<String, String> map3 = new HashMap();
        map3.put("goodsdetailid", object.getString("seqId"));
        list = (List)this.logic.loadList(TableNameUtil.KQDS_CK_GOODS, map3);
      }
      else
      {
        list = (List)this.logic.loadList(TableNameUtil.KQDS_CK_GOODS, map);
      }
      if (detail.getType().equals("2"))
      {
        if ((list != null) && (list.size() > 0))
        {
          KqdsCkGoods goods = (KqdsCkGoods)list.get(0);
          
          BigDecimal zong = new BigDecimal("0.00");
          if (detail.getRkmoney() != null) {
            if ((goods.getKcmoneys() != null) && (!goods.getKcmoneys().equals(""))) {
              zong = detail.getRkmoney().add(goods.getKcmoneys());
            } else {
              zong = detail.getRkmoney().add(new BigDecimal("0.00"));
            }
          }
          int nums = 0;
          if ((goods.getNum() != null) && (!goods.getNum().equals(""))) {
            nums = detail.getInnum().intValue() + goods.getNum().intValue();
          } else {
            nums = detail.getInnum().intValue() + 0;
          }
          BigDecimal goodsprice = new BigDecimal("0.00");
          if (nums == 0) {
            goodsprice = BigDecimal.ZERO;
          } else if (zong != null) {
            goodsprice = zong.divide(new BigDecimal(nums), 3, RoundingMode.HALF_EVEN).setScale(3, 5);
          }
          goods.setGoodsprices(goodsprice);
          goods.setKcmoneys(zong);
          if ((goods.getNum() != null) && (!goods.getNum().equals(""))) {
            goods.setNum(Integer.valueOf(goods.getNum().intValue() + detail.getInnum().intValue()));
          } else {
            goods.setNum(Integer.valueOf(0 + detail.getInnum().intValue()));
          }
          this.logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
        }
        else
        {
          KqdsCkGoods goods = new KqdsCkGoods();
          
          goods.setSeqId(YZUtility.getUUID());
          goods.setGoodsdetailid(detail.getGoodsuuid());
          goods.setSshouse(dp.getInhouse());
          goods.setGoodsprices(detail.getInprice());
          goods.setKcmoneys(detail.getRkmoney());
          goods.setNum(detail.getInnum());
          goods.setOrganization(menzhen);
          this.logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
        }
      }
      else if ((list != null) && (list.size() > 0))
      {
        KqdsCkGoods goods = (KqdsCkGoods)list.get(0);
        
        BigDecimal zong = detail.getRkmoney().add(goods.getKcmoney());
        int nums = detail.getInnum().intValue() + goods.getNums().intValue();
        
        BigDecimal goodsprice = null;
        if (nums == 0) {
          goodsprice = BigDecimal.ZERO;
        } else {
          goodsprice = zong.divide(new BigDecimal(nums), 3, RoundingMode.HALF_EVEN).setScale(3, 5);
        }
        goods.setGoodsprice(goodsprice);
        goods.setKcmoney(zong);
        goods.setNums(Integer.valueOf(goods.getNums().intValue() + detail.getInnum().intValue()));
        this.logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
      }
      else
      {
        KqdsCkGoods goods = new KqdsCkGoods();
        
        goods.setSeqId(YZUtility.getUUID());
        goods.setGoodsdetailid(detail.getGoodsuuid());
        goods.setSshouse(dp.getInhouse());
        goods.setGoodsprice(detail.getInprice());
        goods.setKcmoney(detail.getRkmoney());
        goods.setNums(detail.getInnum());
        goods.setOrganization(menzhen);
        this.logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
      }
    }
  }
  
  public KqdsCkGoodsIn findCkGoodsInById(String id)
    throws Exception
  {
    KqdsCkGoodsIn kaCkGoodsIn = (KqdsCkGoodsIn)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS_IN + ".findCkGoodsInById", id);
    return kaCkGoodsIn;
  }
  
  public void deleteGoodsInById(String seq_id)
    throws Exception
  {
    this.dao.update(TableNameUtil.KQDS_CK_GOODS_IN + ".deleteGoodsInById", seq_id);
  }
}
