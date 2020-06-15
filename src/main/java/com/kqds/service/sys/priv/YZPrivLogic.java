package com.kqds.service.sys.priv;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPriv;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;
import com.kqds.util.sys.sys.UserPrivUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userPrivLogic")
public class YZPrivLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public JSONObject getOneByPrivNameNoOrg(String privName, String organization)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("privName", privName);
    json.put("organization", organization);
    JSONObject result = (JSONObject)this.dao.findForObject(TableNameUtil.SYS_PRIV + ".getOneByPrivNameNoOrg", json);
    return result;
  }
  
  public String getRoleNamesBySeqIds(String ids)
    throws Exception, Exception
  {
    List<String> idList = YZUtility.ConvertString2List(ids);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_PRIV + ".getRoleNamesBySeqIds", idList);
    StringBuffer namesBf = new StringBuffer();
    for (JSONObject job : list)
    {
      String deptName = job.getString("priv_name");
      if (!YZUtility.isNullorEmpty(deptName)) {
        namesBf.append(deptName).append(",");
      }
    }
    String namesStr = namesBf.toString();
    if (namesStr.endsWith(",")) {
      namesStr = namesStr.substring(0, namesStr.length() - 1);
    }
    return namesStr;
  }
  
  public YZPriv getDetailBySeqId(String seqId)
    throws Exception
  {
    YZPriv up = (YZPriv)this.dao.loadObjSingleUUID(TableNameUtil.SYS_PRIV, seqId);
    return up;
  }
  
  public int deleteBySeqIds(String seqids, HttpServletRequest request)
    throws Exception
  {
    List<String> idList = YZUtility.ConvertString2List(seqids);
    int count = ((Integer)this.dao.delete(TableNameUtil.SYS_PRIV + ".deleteBySeqIds", idList)).intValue();
    
    SysLogUtil.log(SysLogUtil.DELETE, SysLogUtil.SYS_PRIV, seqids, TableNameUtil.SYS_PRIV, request);
    return count;
  }
  
  public List<JSONObject> getSlectUserPriv(String organization)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_PRIV + ".getSlectUserPriv", organization);
    return list;
  }
  
  public List<JSONObject> getSlectUserPrivWithCommon(String organization)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_PRIV + ".getSlectUserPrivWithCommon", organization);
    return list;
  }
  
  public boolean IsHaveChild(String id)
    throws Exception
  {
    int count1 = ((Integer)this.dao.findForObject(TableNameUtil.SYS_PRIV + ".IsHaveChild", id)).intValue();
    return count1 > 0;
  }
  
  public boolean IsHaveChildOther(String id)
    throws Exception
  {
    int count = 0;
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_PRIV + ".IsHaveChildOther", null);
    for (JSONObject jsonObject : list)
    {
      String pother = jsonObject.getString("user_priv_other");
      if (!"".equals(pother.trim()))
      {
        String[] privArrr = pother.split(",");
        for (String priv : privArrr) {
          if (id.equals(priv)) {
            count++;
          }
        }
      }
    }
    return count > 0;
  }
  
  public int count(String organization)
    throws Exception
  {
    int count = ((Integer)this.dao.findForObject(TableNameUtil.SYS_PRIV + ".count", organization)).intValue();
    return count;
  }
  
  public int countByPrivName(YZPriv priv)
    throws Exception
  {
    int num = ((Integer)this.dao.findForObject(TableNameUtil.SYS_PRIV + ".countByPrivName", priv)).intValue();
    return num;
  }
  
  public JSONObject selectPage(BootStrapPage bp, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("organization", organization);
    map.put("search", bp.getSearch());
    
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_PRIV + ".selectPage", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public JSONObject selectDetail(String seqId, HttpServletRequest request)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_PRIV + ".selectDetail", seqId);
    if ((list != null) && (list.size() > 0))
    {
      JSONObject userPriv = (JSONObject)list.get(0);
      String privIdStr = userPriv.getString("privIdStr");
      if (YZUtility.isNullorEmpty(privIdStr))
      {
        for (String key : UserPrivUtil.userQxNameList) {
          userPriv.put(key, "");
        }
        userPriv.put(UserPrivUtil.qxFlag0_maxDiscount, "100");
        userPriv.put(UserPrivUtil.qxFlag19_maxVoidmoney, "0");
      }
      else
      {
        String[] valueArray = privIdStr.split(",");
        if (valueArray.length != UserPrivUtil.userQxNameList.size())
        {
          YZPriv dp = (YZPriv)this.dao.loadObjSingleUUID(TableNameUtil.SYS_PRIV, seqId);
          valueArray = preCheckInit(dp, request);
        }
        for (int i = 0; i < UserPrivUtil.userQxNameList.size(); i++)
        {
          String tmpVal = valueArray[i];
          if ((UserPrivUtil.qxFlag19_maxVoidmoney.equals(UserPrivUtil.userQxNameList.get(i))) && (YZUtility.isNullorEmpty(tmpVal))) {
            tmpVal = "0";
          }
          userPriv.put(UserPrivUtil.userQxNameList.get(i), tmpVal);
        }
      }
      return userPriv;
    }
    return null;
  }
  
  public void setPersonPrivByKey(String[] privArray, String privKey, String privValue)
  {
    int index = 0;
    for (String key : UserPrivUtil.userQxNameList)
    {
      if (key.equals(privKey)) {
        break;
      }
      index++;
    }
    privArray[index] = privValue;
  }
  
  public String[] preCheckInit(YZPriv userPriv, HttpServletRequest request)
    throws Exception
  {
    String privIdStr = userPriv.getPrivIdStr();
    if (privIdStr == null) {
      privIdStr = "";
    } else {
      privIdStr = privIdStr.trim();
    }
    if ((!"".equals(privIdStr)) && (!privIdStr.endsWith(","))) {
      privIdStr = privIdStr + ",";
    }
    int arraySize = 0;
    String[] qxArray = null;
    if ((!YZUtility.isNullorEmpty(privIdStr)) && (privIdStr.contains(",")))
    {
      qxArray = privIdStr.split(",");
      arraySize = qxArray.length;
    }
    if (arraySize > UserPrivUtil.userQxNameList.size())
    {
      StringBuffer newQx = new StringBuffer();
      for (int i = 0; i < UserPrivUtil.userQxNameList.size(); i++)
      {
        String idtmp = qxArray[i];
        if (YZUtility.isNullorEmpty(idtmp)) {
          idtmp = ConstUtil.EMPTY_SPACE;
        }
        newQx.append(idtmp).append(",");
      }
      privIdStr = newQx.toString();
    }
    if (arraySize < UserPrivUtil.userQxNameList.size())
    {
      int count = UserPrivUtil.userQxNameList.size() - arraySize;
      for (int i = 0; i < count; i++)
      {
        privIdStr = privIdStr + ConstUtil.EMPTY_SPACE;
        privIdStr = privIdStr + ",";
      }
      userPriv.setPrivIdStr(privIdStr);
      

      this.dao.updateSingleUUID(TableNameUtil.SYS_PRIV, userPriv);
    }
    String[] privArray = privIdStr.split(",");
    if (privArray.length != UserPrivUtil.userQxNameList.size()) {
      throw new Exception("权限数据异常！请联系系统管理员！");
    }
    return privArray;
  }
  
  public YZPriv findGeneral(String userPriv)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("userPriv", userPriv);
    return (YZPriv)this.dao.findForObject(TableNameUtil.SYS_PRIV + ".findGeneral", map);
  }
}
