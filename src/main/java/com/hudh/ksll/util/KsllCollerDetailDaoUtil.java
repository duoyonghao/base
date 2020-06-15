package com.hudh.ksll.util;

import com.hudh.ksll.dao.KsllCollorDetailDao;
import com.hudh.ksll.entity.KsllCollorDetail;
import com.kqds.util.sys.spring.BeanUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KsllCollerDetailDaoUtil
{
  @Autowired
  private static KsllCollorDetailDao ksllCollorDetailDao = (KsllCollorDetailDao)BeanUtil.getBean("ksllCollorDetailDao");
  
  public static void save(List<KsllCollorDetail> ksllCollorDetailList)
  {
    try
    {
      ksllCollorDetailDao.batchSaveCollorDetail(ksllCollorDetailList);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
