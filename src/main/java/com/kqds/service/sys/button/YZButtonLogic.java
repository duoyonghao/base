package com.kqds.service.sys.button;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZButton;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YZButtonLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public List<YZButton> getListBySeqIds(String seqids)
    throws Exception
  {
    List<String> idList = YZUtility.ConvertString2List(seqids);
    List<YZButton> list = (List)this.dao.findForList(TableNameUtil.SYS_BUTTON + ".getListBySeqIds", idList);
    return list;
  }
  
  public int deleteBySeqIds(String seqids, HttpServletRequest request)
    throws Exception
  {
    List<String> idList = YZUtility.ConvertString2List(seqids);
    int count = ((Integer)this.dao.delete(TableNameUtil.SYS_BUTTON + ".deleteBySeqIds", idList)).intValue();
    
    SysLogUtil.log(SysLogUtil.DELETE, SysLogUtil.SYS_BUTTON, seqids, TableNameUtil.SYS_BUTTON, request);
    return count;
  }
  
  public List<JSONObject> selectList(String parentid)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_BUTTON + ".selectList", parentid);
    return list;
  }
  
  public List<YZButton> getButtonList(String parentid)
    throws Exception
  {
    List<YZButton> list = (List)this.dao.findForList(TableNameUtil.SYS_BUTTON + ".getButtonList", parentid);
    return list;
  }
}
