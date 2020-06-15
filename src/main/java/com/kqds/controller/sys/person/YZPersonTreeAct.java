package com.kqds.controller.sys.person;

import com.kqds.entity.sys.YZDept;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"YZPersonTreeAct"})
public class YZPersonTreeAct
{
  private Logger logger = LoggerFactory.getLogger(YZPersonAct.class);
  @Autowired
  private YZPersonLogic personLogic;
  @Autowired
  private YZDeptLogic deptLogic;
  
  private List<String> getDeptIdList(String deptId, List<String> list)
    throws Exception
  {
    YZDept dept = (YZDept)this.personLogic.loadObjSingleUUID(TableNameUtil.SYS_DEPT, deptId);
    if (dept != null)
    {
      String did = dept.getSeqId();
      String pid = dept.getDeptParent();
      
      boolean flag = true;
      for (String tmpId : list) {
        if (tmpId.equals(did)) {
          flag = false;
        }
      }
      if (flag) {
        list.add(did);
      }
      if (!"0".equals(pid)) {
        getDeptIdList(pid, list);
      }
    }
    return list;
  }
  
  @RequestMapping({"/getPersonTree4All.act"})
  public String getPersonTree4All(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String personIds = request.getParameter("personIds");
    String isFilterByVisualStaff = request.getParameter("isFilterByVisualStaff");
    
    HttpSession session = request.getSession(false);
    List<JSONObject> treeList = new ArrayList();
    try
    {
      List<JSONObject> personList;
      if (!YZUtility.isNullorEmpty(isFilterByVisualStaff))
      {
        String visualstaff = SessionUtil.getVisualstaff(request);
        if (!visualstaff.startsWith(",")) {
          visualstaff = "," + visualstaff;
        }
        if (!visualstaff.endsWith(",")) {
          visualstaff = visualstaff + ",";
        }
        personList = this.personLogic.getListByVisualstaff(visualstaff);
        
        List<String> deptIdList = new ArrayList();
        String seq_id;
        String user_id;
        for (int i = 0; i < personList.size(); i++)
        {
          JSONObject pjson = (JSONObject)personList.get(i);
          seq_id = pjson.getString("seq_id");
          user_id = pjson.getString("user_id");
          String dept_id = pjson.getString("dept_id");
          
          getDeptIdList(dept_id, deptIdList);
          
          JSONObject nodeP = new JSONObject();
          nodeP.put("id", "person" + seq_id);
          nodeP.put("pId", dept_id);
          nodeP.put("name", user_id);
          

          nodeP.put("isParent", Boolean.valueOf(false));
          nodeP.put("iconSkin", "person");
          nodeP.put("nocheck", Boolean.valueOf(false));
          nodeP.put("personseqid", seq_id);
          boolean isCheck = YZUtility.isStrInArrayEach(seq_id, personIds);
          nodeP.put("checked", Boolean.valueOf(isCheck));
          treeList.add(nodeP);
        }
        StringBuffer deptBf = new StringBuffer();
        for (String deptId : deptIdList) {
          deptBf.append(deptId).append(",");
        }
        List<YZDept> deptList = this.deptLogic.getDeptListBySeqIds(deptBf.toString());
        for (YZDept dept : deptList)
        {
          JSONObject node = new JSONObject();
          node.put("id", dept.getSeqId());
          if ("0".equals(dept.getDeptParent())) {
            node.put("pId", "orgId");
          } else {
            node.put("pId", dept.getDeptParent());
          }
          node.put("name", dept.getDeptName());
          node.put("isParent", Boolean.valueOf(true));
          node.put("nocheck", Boolean.valueOf(false));
          treeList.add(node);
        }
      }
      else
      {
        List<JSONObject> list = (List)session.getAttribute("PERSON_TREE_DATA");
        if (list == null) {
          treeList = this.personLogic.getDeptNodeList("0", treeList, personIds);
        } else {
          treeList = list;
        }
      }
      for (JSONObject job : treeList)
      {
        String id = job.getString("id");
        if (id.startsWith("person"))
        {
          String tmpId = id.replace("person", "");
          boolean check = YZUtility.isStrInArrayEach(tmpId, personIds);
          job.put("checked", Boolean.valueOf(check));
        }
      }
      JSONObject jobj = new JSONObject();
      jobj.put("retData", treeList);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
}
