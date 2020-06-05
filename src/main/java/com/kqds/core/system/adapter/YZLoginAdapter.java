package com.kqds.core.system.adapter;

import javax.servlet.http.HttpServletRequest;

import com.kqds.core.global.YZActionKeys;
import com.kqds.core.global.YZConst;
import com.kqds.core.system.validator.YZLoginValidator;
import com.kqds.entity.sys.YZPerson;

import net.sf.json.JSONObject;

public class YZLoginAdapter {

	private HttpServletRequest request;
	private YZPerson person;

	private JSONObject retObj;

	public JSONObject getRetObj() {
		return retObj;
	}

	public void setRetObj(JSONObject retObj) {
		this.retObj = retObj;
	}

	public YZLoginAdapter(HttpServletRequest request, YZPerson person) throws Exception {
		this.request = request;
		this.person = person;
	}

	public boolean isValid(YZLoginValidator lv) throws Exception {
		return lv.isValid(this.request, this.person);
	}

	public boolean validate(YZLoginValidator lv) throws Exception {
		if (lv.isValid(this.request, this.person)) {
			return true; // 验证通过
		} else {
			// 写系统日志
			lv.addSysLog(this.request, this.person);
			String msg = lv.getValidatorMsg();
			if (msg == null || "".equals(msg.trim())) {
				msg = "";
			}
			JSONObject job = new JSONObject();
			job.put(YZActionKeys.JSON_RET_STATES, YZConst.RETURN_OK);
			job.put("code", lv.getValidatorCode());
			job.put("msg", msg);
			this.setRetObj(job); // 赋值

			return false;
		}
	}
}
