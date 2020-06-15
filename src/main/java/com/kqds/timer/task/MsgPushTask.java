package com.kqds.timer.task;

import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.push.KQDS_Pushogic;
import com.kqds.service.sys.online.YZOnlineLogic;
import com.kqds.util.base.WebSocketUtil;
import com.kqds.util.base.websocket.Online_User_List;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import net.sf.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
@Controller
public class MsgPushTask
  implements Job
{
  private static Logger logger = LoggerFactory.getLogger(MsgPushTask.class);
  @Autowired
  private KQDS_Pushogic logic;
  @Autowired
  private YZOnlineLogic onlineLogic;
  
  public void execute(JobExecutionContext arg0)
    throws JobExecutionException
  {
    try
    {
      doTask();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void doTask()
    throws Exception
  {
    int count = this.onlineLogic.queryUserCount();
    StringBuffer userIdBf = new StringBuffer();
    for (Entry<String, Session> entry : Online_User_List.Websockt_Session_MAP.entrySet())
    {
      String userId = (String)entry.getKey();
      
      HttpSession session = (HttpSession)SessionUtil.Session_MAP.get(userId);
      if (session != null)
      {
        YZPerson person = (YZPerson)session.getAttribute("LOGIN_USER");
        if (person != null)
        {
          List<JSONObject> list = null;
          int pushnum = this.logic.selectPageNum(person.getSeqId());
          if (pushnum == 0) {
            list = new ArrayList();
          } else {
            list = this.logic.selectNoPageWithUserId(TableNameUtil.KQDS_PUSH, person.getSeqId());
          }
          JSONObject jobj = new JSONObject();
          jobj.put("retData", list);
          jobj.put("retState", "0");
          jobj.put("retDataPushNum", Integer.valueOf(pushnum));
          jobj.put("retDataOnline", Integer.valueOf(count));
          String pushText = jobj.toString();
          Session sessionSocket = (Session)entry.getValue();
          WebSocketUtil.sendMsg2Page(sessionSocket, pushText);
          logger.debug(userId + "推送成功，推送内容" + pushText);
          
          userIdBf.append(userId).append(",");
        }
      }
    }
  }
}
