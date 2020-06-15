package com.kqds.service.ck;

import com.kqds.dao.DaoSupport;
import com.kqds.util.sys.TableNameUtil;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_CK_Goods_Init_DetailLogic {
  @Autowired
  private DaoSupport dao;
  
  public void updateDetail(Map<String, String> map) throws Exception {
    this.dao.findForList(String.valueOf(TableNameUtil.KQDS_CK_GOODS_DETAIL) + ".updateDetail", map);
  }
  
  public void updateGoods(Map<String, String> map) throws Exception {
    this.dao.findForList(String.valueOf(TableNameUtil.KQDS_CK_GOODS) + ".updateGoods", map);
  }
}
