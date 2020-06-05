package com.hudh.ykzz.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.ykzz.entity.YkzzDrugsBatchnumSave;
import com.kqds.dao.DaoSupport;

@Service
public class YkzzDrugsBatchnumSaveDao {
	
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 保存患者领药
	 * @param list
	 * @throws Exception
	 */
	public void insertDrugsBatchnumSave(List<YkzzDrugsBatchnumSave> list) throws Exception {
		dao.batchUpdate("HUDH_YKZZ_DRUGS_BATCHNUM_SAVE.insertDrugsBatchnumSave", list);
	}
}
