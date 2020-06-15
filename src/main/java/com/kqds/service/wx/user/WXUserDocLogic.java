package com.kqds.service.wx.user;

import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.wx.WXFans;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WXUserDocLogic extends BaseLogic {
  @Autowired
  private KQDS_UserDocumentLogic logic;
  
  @Autowired
  private DaoSupport dao;
  
  public int phoneIsExist(String phonenumber, String openid) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("phonenumber1", YZAuthenticator.encryKqdsPhonenumber(phonenumber));
    map.put("openid", openid);
    int num = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_USERDOCUMENT) + ".phoneIsExist", map)).intValue();
    return num;
  }
  
  public KqdsUserdocument getBindUserDocByOpenId(String openid, HttpServletRequest request) throws Exception {
    KqdsUserdocument json = (KqdsUserdocument)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_USERDOCUMENT) + ".getBindUserDocByOpenId", openid);
    return json;
  }
  
  public JSONObject getUserDocByNameAndPhonenumber(String username, String phonenumber, HttpServletRequest request) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("phonenumber1", YZAuthenticator.encryKqdsPhonenumber(phonenumber));
    map.put("username", username);
    JSONObject json = (JSONObject)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_USERDOCUMENT) + ".getUserDocByNameAndPhonenumber", map);
    return json;
  }
  
  public int bindWxOpenId(String seqId, String openid, HttpServletRequest request) throws Exception {
    int count = 0;
    KqdsUserdocument user = (KqdsUserdocument)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, seqId);
    if (user != null) {
      user.setOpenid(openid);
      user.setBindstatus("0");
      this.logic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, user);
      count++;
    } 
    setFansUserCode(openid, user.getUsercode(), request);
    return count;
  }
  
  public void setFansUserCode(String openid, String usercode, HttpServletRequest request) throws Exception {
    List<WXFans> list = (List<WXFans>)this.dao.findForList(String.valueOf(TableNameUtil.WX_RECEIVETEXT) + ".getFanByOpenid", openid);
    if (list != null && list.size() > 1)
      throw new Exception("数据异常：根据微信ID匹配到多条记录【wx_fans】"); 
    if (list != null && list.size() != 0) {
      WXFans fan = list.get(0);
      fan.setBindtime(YZUtility.getCurDateTimeStr());
      fan.setCarestatus(Integer.valueOf(0));
      fan.setUsercode(usercode);
      this.logic.updateSingleUUID(TableNameUtil.WX_FANS, fan);
    } 
  }
}
