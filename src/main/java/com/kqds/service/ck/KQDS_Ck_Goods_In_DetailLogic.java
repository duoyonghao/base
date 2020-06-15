package com.kqds.service.ck;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoods;
import com.kqds.entity.base.KqdsCkGoodsDetail;
import com.kqds.entity.base.KqdsCkGoodsInDetail;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_Ck_Goods_In_DetailLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> inSearchList(String table, Map<String, String> map)
    throws Exception
  {
    map.put("selectKcmoney", SQLUtil.convertDecimal("gind.rkmoney", 18, 3));
    map.put("selectGoodsprice", SQLUtil.convertDecimal("gind.inprice", 18, 3));
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".inSearchList", map);
    for (JSONObject job : list)
    {
      String intype = job.getString("intype");
      if (ConstUtil.CK_IN_0.equals(intype)) {
        intype = "采购入库";
      } else if (ConstUtil.CK_IN_2.equals(intype)) {
        intype = "换货入库";
      } else if (ConstUtil.CK_IN_4.equals(intype)) {
        intype = "其他入库";
      }
      job.put("intype", intype);
    }
    return list;
  }
  
  public List<JSONObject> selectAll(String goodsuuid)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".selectAll", goodsuuid);
    return list;
  }
  
  public KqdsCkGoodsInDetail selectCkGoodsDetailByInid(String goodsinid)
    throws Exception
  {
    KqdsCkGoodsInDetail list = (KqdsCkGoodsInDetail)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".selectCkGoodsDetailByInid", goodsinid);
    return list;
  }
  
  public List<KqdsCkGoodsInDetail> findCkGoodsDetailByInid(String goodsinid)
    throws Exception
  {
    List<KqdsCkGoodsInDetail> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".findCkGoodsDetailByInid", goodsinid);
    return list;
  }
  
  public List<JSONObject> inDetailSelectByIncode(String table, Map<String, String> map)
    throws Exception
  {
    map.put("selectKcmoney", SQLUtil.convertDecimal("gind.rkmoney", 18, 3));
    map.put("selectGoodsprice", SQLUtil.convertDecimal("gind.inprice", 18, 3));
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".inDetailSelectByIncode", map);
    for (JSONObject job : list)
    {
      String intype = job.getString("intype");
      if (ConstUtil.CK_IN_0.equals(intype)) {
        intype = "采购入库";
      } else if (ConstUtil.CK_IN_2.equals(intype)) {
        intype = "换货入库";
      } else if (ConstUtil.CK_IN_4.equals(intype)) {
        intype = "其他入库";
      }
      job.put("intype", intype);
    }
    return list;
  }
  
  public void updateGoodsInDetailByGoodsInSeqId(KqdsCkGoodsInDetail kqdsCkGoodsInDetail)
    throws Exception
  {
    this.dao.update(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".updateGoodsInDetailByGoodsInSeqId", kqdsCkGoodsInDetail);
  }
  
  public void deleteGoodsInDetailById(String detailId)
    throws Exception
  {
    this.dao.delete(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".deleteGoodsInDetailById", detailId);
  }
  
  public List<JSONObject> selectAllGoodPhByGoodCode(String goodsuuid, String type)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("goodsuuid", goodsuuid);
    map.put("type", type);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".selectAllGoodPhByGoodCode", map);
    
    List<JSONObject> list1 = new ArrayList();
    



    KqdsCkGoods kqdsCkGoods = (KqdsCkGoods)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS + ".findCkGoodsByDetailid", goodsuuid);
    if (list.size() > 0)
    {
      int phnum = 0;
      for (JSONObject jsonObject : list) {
        phnum += jsonObject.getInt("phnum");
      }
      if (type.equals("2"))
      {
        if (phnum < kqdsCkGoods.getNum().intValue())
        {
          JSONObject json = new JSONObject();
          json.put("ph", "请选择");
          json.put("phnum", Integer.valueOf(kqdsCkGoods.getNums().intValue() - phnum));
          json.put("goodsuuid", goodsuuid);
          list1.add(json);
          list1.addAll(list);
        }
        else
        {
          list1.addAll(list);
        }
      }
      else if (phnum < kqdsCkGoods.getNums().intValue())
      {
        JSONObject json = new JSONObject();
        json.put("ph", "请选择");
        json.put("phnum", Integer.valueOf(kqdsCkGoods.getNums().intValue() - phnum));
        json.put("goodsuuid", goodsuuid);
        list1.add(json);
        list1.addAll(list);
      }
      else
      {
        list1.addAll(list);
      }
    }
    else
    {
      JSONObject json = new JSONObject();
      json.put("ph", "请选择");
      json.put("phnum", kqdsCkGoods.getNums());
      json.put("goodsuuid", goodsuuid);
      list1.add(json);
    }
    return list1;
  }
  
  public List<JSONObject> findCkInGoodsDetailByGoodsuuid(String goodsuuid)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".findCkInGoodsDetailByGoodsuuid", goodsuuid);
    
    KqdsCkGoodsDetail kqdsCkGoodsDetail = (KqdsCkGoodsDetail)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".findCkGoodsDetailBySeqid", goodsuuid);
    if (list.size() > 0) {
      for (JSONObject jsonObject : list)
      {
        jsonObject.put("goodscode", kqdsCkGoodsDetail.getGoodscode());
        jsonObject.put("goodsname", kqdsCkGoodsDetail.getGoodsname());
        jsonObject.put("goodsnorms", kqdsCkGoodsDetail.getGoodsnorms());
        jsonObject.put("goodsunit", kqdsCkGoodsDetail.getGoodsunit());
      }
    }
    return list;
  }
  
  public JSONObject findCkInGoodsDetail(String goodsuuid, BootStrapPage bp)
    throws Exception
  {
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".findCkInGoodsDetail", goodsuuid);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public void updateCkGoodsInDetailByKsllReplaceMent(List<KqdsCkGoodsInDetail> kqdsCkGoodsInDetailList)
    throws Exception
  {
    this.dao.batchUpdate(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".updateCkGoodsInDetailByKsllReplaceMent", kqdsCkGoodsInDetailList);
  }
  
  public void updateGoodsPh(Map<String, String> map)
    throws Exception
  {
    this.dao.update(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".updateGoodsPh", map);
  }
  
  public JSONObject findGoodsDetailById(Map<String, String> map)
    throws Exception
  {
    return (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS + ".findGoodsDetailById", map);
  }
  
  public JSONObject findGoodsBycodeAndType(Map<String, String> map2)
    throws Exception
  {
    return (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS + ".findGoodsBycodeAndType", map2);
  }
}
