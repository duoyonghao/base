package com.hudh.lclj.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZDict;

@Service
public class SysDictDao {
	@Autowired
	private DaoSupport dao;
	/**
	 * 获取所有字典项集合（用于替换seq_id对应的中文名称）
	 * @param blCode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<YZDict> findSysDictList() throws Exception {
		List<YZDict> YZDictList = (List<YZDict>) dao.findForList("SYS_DICT.selectAllBeanList", null);
		return YZDictList;
	}
	
}
