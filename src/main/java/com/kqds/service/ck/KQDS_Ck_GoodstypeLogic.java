package com.kqds.service.ck;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoodsType;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.other.ChineseCharToEn;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KQDS_Ck_GoodstypeLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> selectList(String table, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_CK_GOODS_TYPE) + ".selectList", map);
    return list;
  }
  
  public List<JSONObject> getBaseTypeSelect(String table, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_CK_GOODS_TYPE) + ".getBaseTypeSelect", map);
    return list;
  }
  
  public List<JSONObject> getBNextTypeSelect(String table, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_CK_GOODS_TYPE) + ".getBNextTypeSelect", map);
    return list;
  }
  
  public void delete(String seqId) throws Exception {
    this.dao.delete(String.valueOf(TableNameUtil.KQDS_CK_GOODS_TYPE) + ".deleteSeqId", seqId);
  }
  
  public String getUniTypenoByName(String goodstypename, String perid) throws Exception {
    String typeno = ChineseCharToEn.getAllFirstLetter_RandNum(goodstypename);
    JSONObject jobj = new JSONObject();
    jobj.put("typeno", typeno);
    jobj.put("perid", perid);
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_CK_HOUSE) + ".getUniCodeByName", jobj)).intValue();
    if (count == 0)
      return typeno; 
    return getUniTypenoByName(goodstypename, perid);
  }
  
  @Transactional
  public void deleteType(Map<String, String> map) throws Exception {
    List<KqdsCkGoodsType> list = (List<KqdsCkGoodsType>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_CK_GOODS_TYPE) + ".findNextType", map);
    if (list.size() > 0) {
      for (KqdsCkGoodsType kqdsCkGoodsType : list) {
        Map<String, String> map2 = new HashMap<>();
        map2.put("seqId", kqdsCkGoodsType.getSeqId());
        this.dao.delete(String.valueOf(TableNameUtil.KQDS_CK_GOODS_TYPE) + ".deleteByPrimaryKey", map2);
      } 
      this.dao.delete(String.valueOf(TableNameUtil.KQDS_CK_GOODS_TYPE) + ".deleteByPrimaryKey", map);
    } else {
      this.dao.delete(String.valueOf(TableNameUtil.KQDS_CK_GOODS_TYPE) + ".deleteByPrimaryKey", map);
    } 
  }
}
