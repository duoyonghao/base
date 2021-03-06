package com.kqds.service.ck;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoodsDetail;
import com.kqds.util.sys.TableNameUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_CK_InitGoodsCodeLogic {
  @Autowired
  private DaoSupport dao;
  
  public void batchUpdateGoodsCode(List<KqdsCkGoodsDetail> list) throws Exception {
    this.dao.batchUpdate(String.valueOf(TableNameUtil.KQDS_CK_GOODS_DETAIL) + ".batchUpdateGoodsCode", list);
  }
}
