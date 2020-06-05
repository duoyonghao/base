package com.hudh.ksll.util;

import java.util.List;

import com.hudh.ksll.entity.KsllCollorDetail;
/**
 * 2019.07.10 lwg
 * 利用线程科室领料保存问题
 */
public class RunnableUtil implements Runnable{ 
	private  List<KsllCollorDetail> ksllCollorDetailList;
	public void setKsllCollorDetailList(List<KsllCollorDetail> ksllCollorDetailList) {
		this.ksllCollorDetailList = ksllCollorDetailList;
	}
	@Override
	public void run() {
		
		// TODO Auto-generated method stub
		try {
			//System.err.println("走线程++++++++++++++++++++业务开始");
			//ksllCollorDetailDao.batchSaveCollorDetail(ksllCollorDetailList);
			KsllCollerDetailDaoUtil.save(ksllCollorDetailList);
			//System.err.println("走线程++++++++++++++++++++业务结束");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
