package com.kqds.controller.base.makeInvoice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.core.global.YZSysProps;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.service.base.makeInvoice.KQDS_makeInvoiceLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.connection.DataSourceInstances;
import com.kqds.util.sys.connection.DataSourceSwitch;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
@RequestMapping("Kqds_MakeInvoiceAct")
public class Kqds_MakeInvoiceAct {

	private static Logger logger = LoggerFactory.getLogger(Kqds_MakeInvoiceAct.class);
	@Autowired
	private KQDS_makeInvoiceLogic logic;

	/**
	 * 开票
	 */
	@RequestMapping(value = "/makeInvoice.act")
	public String makeInvoice(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 开票系统
			String usercodes = request.getParameter("usercodes");
			if (usercodes.length() == 0) {
				throw new Exception("请选择开票患者！");
			}
			String[] usercodesArr = usercodes.split(",");
			// 实现方式： 每次调用此方法
			// 开票系统：
			// 1、清空改患者的表记录
			// 2、重新插入 改患者的 表记录
			// 口腔大师系统：
			// 1、更新患者表 的开票字段

			// 查询开票系统是否存在该患者
			for (String usercode : usercodesArr) {
				Map map = new HashMap<>();
				map.put("usercode", usercode);
				// 切换数据源
				DataSourceSwitch.setDataSourceType(DataSourceInstances.KQDSKP);
				List<KqdsUserdocument> listDoc = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);
				if (listDoc != null && listDoc.size() > 0) {
					// 检验开票系统中是否存在 该患者的 新增开票数据（开票系统中需要在费用添加的时候 把Costlevel 设为0）
					if (!listDoc.get(0).getCostlevel().equals("1")) {
						throw new Exception("患者编号：" + listDoc.get(0).getUsercode() + "已在开票系统中新增数据，不能同步！");
					}
					// 清空改患者的 表记录
					delete(usercode, request);
				}
				// 2、重新插入 表记录
				add(usercode, request);
			}
			// 还原数据源
			DataSourceSwitch.reset();
			JSONObject jobj = new JSONObject();
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);

		}
		return null;
	}

	public void delete(String usercode, HttpServletRequest request) throws Exception {
		// 清空改患者的 档案 挂号 咨询记录 费用明细表、费用表、结账表、会员卡表、会员卡操作记录
		String kaipiaoTable = YZSysProps.getProp("KAIPIAO_TABLE");
		String[] kaipiaoTableArr = kaipiaoTable.split(",");
		// 切换数据源
		DataSourceSwitch.setDataSourceType(DataSourceInstances.KQDSKP);
		for (String table : kaipiaoTableArr) {
			// System.out.println("-----" + table);
			logic.deleteUserDoc(usercode, table);
		}
		// 还原数据源
		DataSourceSwitch.reset();
	}

	public void add(String usercode, HttpServletRequest request) throws Exception {
		String kaipiaoTable = YZSysProps.getProp("KAIPIAO_TABLE");
		String[] kaipiaoTableArr = kaipiaoTable.split(",");
		for (String table : kaipiaoTableArr) {
			Map map = new HashMap<>();
			map.put("usercode", usercode);
			if (table.equals(TableNameUtil.KQDS_USERDOCUMENT)) {
				// 还原数据源
				DataSourceSwitch.reset();
				List<KqdsUserdocument> listUserKqds = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);
				if (listUserKqds == null) {
					throw new Exception("患者档案信息不存在");
				}
				// 更新患者表 的开票字段
				listUserKqds.get(0).setCostlevel("1");
				logic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, listUserKqds.get(0));
				// 切换数据源
				DataSourceSwitch.setDataSourceType(DataSourceInstances.KQDSKP);
				// 保存患者信息到开票系统
				logic.saveSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, listUserKqds.get(0));
			} else {
				// 还原数据源
				DataSourceSwitch.reset();
				List<Object> listKqds = (List<Object>) logic.loadList(table, map);
				if (listKqds != null && listKqds.size() > 0) {
					// 保存患者表信息到开票系统
					// 切换数据源
					DataSourceSwitch.setDataSourceType(DataSourceInstances.KQDSKP);
					for (Object reg : listKqds) {
						logic.saveSingleUUID(table, reg);
					}
				}
			}
		}
		// 还原数据源
		DataSourceSwitch.reset();
	}

}