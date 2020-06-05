/**  
  *
  * @Title:  KqdsUserdocumentMergeRecordLogic.java   
  * @Package com.kqds.service.base.hzjd   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年7月29日 下午3:14:12   
  * @version V1.0  
  */ 
package com.kqds.service.base.hzjd;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsUserdocumentMergeRecord;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

/**  
  * 
  * @ClassName:  KqdsUserdocumentMergeRecordLogic   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年7月29日 下午3:14:12   
  *      
  */
@Service
public class UserdocumentMergeRecordLogic extends BaseLogic {
	
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 保存信息
	  * @Title: saveMergeRecord   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dp
	  * @param: @throws Exception      
	  * @return: void
	  * @dateTime:2019年7月29日 下午3:16:36
	 */
	public void saveMergeRecord(KqdsUserdocumentMergeRecord dp, HttpServletRequest request) throws Exception {
		String organization = ChainUtil.getCurrentOrganization(request);
		YZPerson person = SessionUtil.getLoginPerson(request);
		dp.setCrateuser(person.getSeqId());
		dp.setSeqId(YZUtility.getUUID());
		dp.setCratetime(YZUtility.getCurDateTimeStr());
		dp.setOrganization(organization);
		dao.save("KQDS_UserDocument_merge_record.saveMergeRecord", dp);
	}
}
