package com.kqds.entity.sys;

import com.kqds.util.sys.YZUtility;
import java.util.Comparator;
import net.sf.json.JSONObject;

public class YZMenuCompartorJson
  implements Comparator<JSONObject>
{
  public int compare(JSONObject m1, JSONObject m2)
  {
    Integer o1 = Integer.valueOf(0);
    Integer o2 = Integer.valueOf(0);
    if (!YZUtility.isNullorEmpty(m1.get("orderno").toString())) {
      o1 = Integer.valueOf(m1.getInt("orderno"));
    }
    if (!YZUtility.isNullorEmpty(m2.get("orderno").toString())) {
      o2 = Integer.valueOf(m2.getInt("orderno"));
    }
    return o1.compareTo(o2);
  }
}
