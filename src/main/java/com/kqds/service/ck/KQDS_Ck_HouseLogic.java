package com.kqds.service.ck;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.other.ChineseCharToEn;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_Ck_HouseLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  public void emptyCK() throws Exception {
    this.dao.deleteAll(TableNameUtil.KQDS_CK_DEPT);
    this.dao.deleteAll(TableNameUtil.KQDS_CK_GOODS);
    this.dao.deleteAll(TableNameUtil.KQDS_CK_GOODS_DETAIL);
    this.dao.deleteAll(TableNameUtil.KQDS_CK_GOODS_IN);
    this.dao.deleteAll(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL);
    this.dao.deleteAll(TableNameUtil.KQDS_CK_GOODS_OUT);
    this.dao.deleteAll(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL);
    this.dao.deleteAll(TableNameUtil.KQDS_CK_GOODS_TYPE);
    this.dao.deleteAll(TableNameUtil.KQDS_CK_HOUSE);
    this.dao.deleteAll(TableNameUtil.KQDS_CK_SUPPLIER);
  }
  
  public List<JSONObject> selectList(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_CK_HOUSE) + ".selectList", map);
    return list;
  }
  
  public String getUniCodeByName(String housename) throws Exception {
    String code = ChineseCharToEn.getAllFirstLetter_RandNum(housename);
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_CK_HOUSE) + ".getUniCodeByName", housename)).intValue();
    if (count == 0)
      return code; 
    return getUniCodeByName(housename);
  }
}
