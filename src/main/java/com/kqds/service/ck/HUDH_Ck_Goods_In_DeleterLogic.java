package com.kqds.service.ck;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoodsDeleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HUDH_Ck_Goods_In_DeleterLogic
{
  @Autowired
  private DaoSupport dao;
  
  public void saveDeletePersonInfor(KqdsCkGoodsDeleter dp)
    throws Exception
  {
    this.dao.save("KQDS_CK_GOODS_IN_DELETERE.saveDeletePersonInfor", dp);
  }
}
