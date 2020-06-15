package com.hudh.ykzz.dao;

import com.hudh.ykzz.entity.YkzzDrugsBatchnumSave;
import com.kqds.dao.DaoSupport;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YkzzDrugsBatchnumSaveDao {
  @Autowired
  private DaoSupport dao;
  
  public void insertDrugsBatchnumSave(List<YkzzDrugsBatchnumSave> list) throws Exception {
    this.dao.batchUpdate("HUDH_YKZZ_DRUGS_BATCHNUM_SAVE.insertDrugsBatchnumSave", list);
  }
}
