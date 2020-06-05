package com.hudh.ksll.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.ksll.dao.KsllCollorDetailDao;
import com.hudh.ksll.entity.KsllCollorDetail;
import com.kqds.util.sys.spring.BeanUtil;
/**
 * 2019.07.10 lwg
 * 科室领料线程关联类
 */
@Service
public class KsllCollerDetailDaoUtil {
	@Autowired
	private static KsllCollorDetailDao ksllCollorDetailDao;
	static{
		ksllCollorDetailDao=(KsllCollorDetailDao) BeanUtil.getBean("ksllCollorDetailDao");
	}
	public static void save(List<KsllCollorDetail> ksllCollorDetailList){
		try {
			ksllCollorDetailDao.batchSaveCollorDetail(ksllCollorDetailList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
