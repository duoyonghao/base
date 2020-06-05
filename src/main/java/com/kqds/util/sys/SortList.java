package com.kqds.util.sys;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sf.json.JSONObject;

public class SortList<E> {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void SortJSONObject(List<JSONObject> list, final String field, final String sort) {
		Collections.sort(list, new Comparator() {
			public int compare(Object a, Object b) {
				int ret = 0;
				if (sort != null && "desc".equals(sort))// 倒序
					ret = ((JSONObject) b).getString(field).compareTo(((JSONObject) a).getString(field));
				else
					// 正序
					ret = ((JSONObject) a).getString(field).compareTo(((JSONObject) b).getString(field));
				return ret;
			}
		});
	}

	// @SuppressWarnings({ "unchecked", "rawtypes" })
	// public void Sort(List<E> list, final String method, final String sort) {
	// Collections.sort(list, new Comparator() {
	// public int compare(Object a, Object b) {
	// int ret = 0;
	// try {
	// Method m1 = ((E) a).getClass().getMethod(method, null);
	// Method m2 = ((E) b).getClass().getMethod(method, null);
	// if (sort != null && "desc".equals(sort))// 倒序
	// ret = m2.invoke(((E) b), null).toString().compareTo(m1.invoke(((E) a),
	// null).toString());
	// else
	// // 正序
	// ret = m1.invoke(((E) a), null).toString().compareTo(m2.invoke(((E) b),
	// null).toString());
	// } catch (Exception ne) {
	// System.out.println(ne);
	// }
	// return ret;
	// }
	// });
	// }
}
