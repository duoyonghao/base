package com.hudh.doctorpick.dao;

import com.hudh.doctorpick.entity.GoodsPickSendBack;
import com.kqds.dao.DaoSupport;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsPickSendBackDao
{
  @Autowired
  private DaoSupport dao;
  
  public int insertGoodsPickSendBack(GoodsPickSendBack goodsPickSendBack)
    throws Exception
  {
    int i = ((Integer)this.dao.save("HUDH_GOODS_PICK_SEND_BACK.insertGoodsPickSendBack", goodsPickSendBack)).intValue();
    return i;
  }
  
  public List<JSONObject> findGoodsPickSendBackAll(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList("HUDH_GOODS_PICK_SEND_BACK.findGoodsPickSendBackAll", map);
    return list;
  }
  
  public JSONObject findGoodsPickSendBackById(String id)
    throws Exception
  {
    JSONObject json = (JSONObject)this.dao.findForObject("HUDH_GOODS_PICK_SEND_BACK.findGoodsPickSendBackById", id);
    return json;
  }
}
