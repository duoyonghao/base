package com.kqds.service.wx.user;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.wx.WXFans;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Service
public class WXUserDocLogic extends BaseLogic {
	@Autowired
	private KQDS_UserDocumentLogic logic;
	@Autowired
	private DaoSupport dao;

	/**
	 * 判断当前微信用户填的手机号码，目前是否正在被其他用户绑定
	 * 
	 * @param dbConn
	 * @param phonenumber
	 * @param openid
	 * @return
	 * @throws Exception
	 */
	public int phoneIsExist(String phonenumber, String openid) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("phonenumber1", YZAuthenticator.encryKqdsPhonenumber(phonenumber));
		map.put("openid", openid);

		int num = (int) dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".phoneIsExist", map);
		return num;
	}

	/**
	 * 根据Openid获取用户信息
	 * 
	 * @param openid
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public KqdsUserdocument getBindUserDocByOpenId(String openid, HttpServletRequest request) throws Exception {
		KqdsUserdocument json = (KqdsUserdocument) dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".getBindUserDocByOpenId", openid);
		return json;
	}

	/**
	 * 根据手机号和姓名匹配患者
	 * 
	 * @param openid
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public JSONObject getUserDocByNameAndPhonenumber(String username, String phonenumber, HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("phonenumber1", YZAuthenticator.encryKqdsPhonenumber(phonenumber));
		map.put("username", username);

		JSONObject json = (JSONObject) dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".getUserDocByNameAndPhonenumber", map);
		return json;
	}

	/**
	 * 绑定微信号
	 * 
	 * @param seqId
	 * @param openid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int bindWxOpenId(String seqId, String openid, HttpServletRequest request) throws Exception {
		int count = 0;
		KqdsUserdocument user = (KqdsUserdocument) logic.loadObjSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, seqId);
		if (user != null) {
			user.setOpenid(openid);
			user.setBindstatus("0");
			logic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, user);
			count++;
		}

		// 绑定时，更新fans表
		setFansUserCode(openid, user.getUsercode(), request);

		return count;
	}

	@SuppressWarnings("unchecked")
	public void setFansUserCode(String openid, String usercode, HttpServletRequest request) throws Exception {
		List<WXFans> list = (List<WXFans>) dao.findForList(TableNameUtil.WX_RECEIVETEXT + ".getFanByOpenid", openid);
		if (list != null && list.size() > 1) {
			throw new Exception("数据异常：根据微信ID匹配到多条记录【wx_fans】");
		}

		if (list == null || list.size() == 0) {
		} else {
			WXFans fan = list.get(0);
			fan.setBindtime(YZUtility.getCurDateTimeStr());
			fan.setCarestatus(0); // 0关注 1取消关注
			fan.setUsercode(usercode);
			logic.updateSingleUUID(TableNameUtil.WX_FANS, fan);
		}
	}

}
