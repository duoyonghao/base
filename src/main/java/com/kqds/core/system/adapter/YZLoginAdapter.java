package com.kqds.core.system.adapter;

import com.kqds.core.system.validator.YZLoginValidator;
import com.kqds.entity.sys.YZPerson;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public class YZLoginAdapter
{
  private HttpServletRequest request;
  private YZPerson person;
  private JSONObject retObj;
  
  public JSONObject getRetObj()
  {
    return this.retObj;
  }
  
  public void setRetObj(JSONObject retObj)
  {
    this.retObj = retObj;
  }
  
  public YZLoginAdapter(HttpServletRequest request, YZPerson person)
    throws Exception
  {
    this.request = request;
    this.person = person;
  }
  
  public boolean isValid(YZLoginValidator lv)
    throws Exception
  {
    return lv.isValid(this.request, this.person);
  }
  
  public boolean validate(YZLoginValidator lv)
    throws Exception
  {
    if (lv.isValid(this.request, this.person)) {
      return true;
    }
    lv.addSysLog(this.request, this.person);
    String msg = lv.getValidatorMsg();
    if ((msg == null) || ("".equals(msg.trim()))) {
      msg = "";
    }
    JSONObject job = new JSONObject();
    job.put("retState", "0");
    job.put("code", Integer.valueOf(lv.getValidatorCode()));
    job.put("msg", msg);
    setRetObj(job);
    
    return false;
  }
}
