/**  
  *
  * @Title:  KQDS_CK_Goods_Init_DetailLogic.java   
  * @Package com.kqds.service.ck   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2020年3月27日 下午5:13:55   
  * @version V1.0  
  */ 
package com.kqds.service.ck;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoodsDetail;
import com.kqds.util.sys.TableNameUtil;

/**  
  * 
  * @ClassName:  KQDS_CK_Goods_Init_DetailLogic   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2020年3月27日 下午5:13:55   
  *      
  */
@Service
public class KQDS_CK_Goods_Init_DetailLogic {

	@Autowired
	private DaoSupport dao;

	/**   
	  * @Title: updateDetail   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2020年3月28日 上午9:02:13
	  */  
	public void updateDetail(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL+".updateDetail", map);
	}

	/**   
	  * @Title: updateGoods   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2020年3月28日 下午2:06:05
	  */  
	public void updateGoods(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		dao.findForList(TableNameUtil.KQDS_CK_GOODS+".updateGoods", map);
	}
	
}
