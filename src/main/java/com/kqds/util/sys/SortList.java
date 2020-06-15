package com.kqds.util.sys;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.sf.json.JSONObject;

public class SortList<E> {
  public void SortJSONObject(List<JSONObject> list, final String field, final String sort) {
    Collections.sort(list, new Comparator<JSONObject>() {
          public int compare(Object a, Object b) {
            int ret = 0;
            if (sort != null && "desc".equals(sort)) {
              ret = ((JSONObject)b).getString(field).compareTo(((JSONObject)a).getString(field));
            } else {
              ret = ((JSONObject)a).getString(field).compareTo(((JSONObject)b).getString(field));
            } 
            return ret;
          }
        });
  }
}
