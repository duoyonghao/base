package com.kqds.util.base.code;

import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.util.List;
import javax.annotation.PostConstruct;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
@Controller
public class UserCodeUtil
{
  @Autowired
  private KQDS_UserDocumentLogic userDocLogic;
  private static UserCodeUtil userCodeUtil;
  
  public void setUserDocLogic(KQDS_UserDocumentLogic userDocLogic)
  {
    this.userDocLogic = userDocLogic;
  }
  
  @PostConstruct
  public void init()
  {
    userCodeUtil = this;
    userCodeUtil.userDocLogic = this.userDocLogic;
  }
  
  public static String getUserCode4Insert(String organization, String userCode)
    throws Exception
  {
    int count = userCodeUtil.userDocLogic.countByUserCode(userCode);
    if (count == 0) {
      return userCode;
    }
    userCode = getUserCode(organization);
    return getUserCode4Insert(organization, userCode);
  }
  
  public static String getUserCode(String organization)
    throws Exception
  {
    int num = 0;
    String numstr = "";
    String maxusercode = getMaxUserCode(TableNameUtil.KQDS_USERDOCUMENT, organization);
    if (!YZUtility.isNullorEmpty(maxusercode))
    {
      String codeNumStr = maxusercode.substring(maxusercode.length() - ConstUtil.USER_CODE_NUM_LEN);
      num = Integer.parseInt(codeNumStr);
    }
    num++;
    if (num < 10) {
      numstr = organization + "00000" + num;
    } else if ((num > 9) && (num < 100)) {
      numstr = organization + "0000" + num;
    } else if ((num > 99) && (num < 1000)) {
      numstr = organization + "000" + num;
    } else if ((num > 999) && (num < 10000)) {
      numstr = organization + "00" + num;
    } else if ((num > 9999) && (num < 100000)) {
      numstr = organization + "0" + num;
    } else if (num > 99999) {
      numstr = organization + num;
    }
    return numstr;
  }
  
  private static String getMaxUserCode(String table, String organization)
    throws Exception
  {
    String maxusercode = null;
    List<JSONObject> list = userCodeUtil.userDocLogic.getMaxUserCode(table, organization);
    if ((list != null) && (list.size() > 0)) {
      maxusercode = ((JSONObject)list.get(0)).getString("maxusercode");
    }
    return maxusercode;
  }
}
