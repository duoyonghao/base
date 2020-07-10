package com.kqds.service.sys.priv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kqds.entity.sys.YZPrivilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
@Service("userPrivLogic")
public class YZPrivLogic extends BaseLogic {

	@Autowired
	private DaoSupport dao;

	public JSONObject getOneByPrivNameNoOrg(String privName, String organization) throws Exception {
		JSONObject json = new JSONObject();
		json.put("privName", privName);
		json.put("organization", organization);
		JSONObject result = (JSONObject) dao.findForObject(TableNameUtil.SYS_PRIV + ".getOneByPrivNameNoOrg", json);
		return result;
	}

	public String getRoleNamesBySeqIds(String ids) throws Exception, Exception {
		List<String> idList = YZUtility.ConvertString2List(ids);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PRIV + ".getRoleNamesBySeqIds", idList);
		StringBuffer namesBf = new StringBuffer();
		for (JSONObject job : list) {
			String deptName = job.getString("priv_name");
			if (YZUtility.isNullorEmpty(deptName)) {
				continue;
			}
			namesBf.append(deptName).append(",");
		}

		String namesStr = namesBf.toString();
		if (namesStr.endsWith(",")) {
			namesStr = namesStr.substring(0, namesStr.length() - 1);
		}

		return namesStr;
	}

	/**
	 * 根据主键获取角色
	 * 
	 * @param conn
	 * @param seqId
	 * @return
	 * @throws Exception
	 */
	public YZPriv getDetailBySeqId(String seqId) throws Exception {

		YZPriv up = (YZPriv) dao.loadObjSingleUUID(TableNameUtil.SYS_PRIV, seqId);
		return up;
	}

	/**
	 * 根据主键进行删除
	 * 
	 * @param conn
	 * @param seqids
	 * @return
	 * @throws Exception
	 */
	public int deleteBySeqIds(String seqids, HttpServletRequest request) throws Exception {
		List<String> idList = YZUtility.ConvertString2List(seqids);
		int count = (int) dao.delete(TableNameUtil.SYS_PRIV + ".deleteBySeqIds", idList);
		// 记录日志
		SysLogUtil.log(SysLogUtil.DELETE, SysLogUtil.SYS_PRIV, seqids, TableNameUtil.SYS_PRIV, request);
		return count;
	}

	public void removeUserPrivileges(String seqId) throws Exception {
		dao.delete(TableNameUtil.SYS_PRIV+".deleteByBelongsTo", seqId);
	}

	public void insertPrivilege(YZPrivilege yzPrivilege) throws Exception {
		dao.save(TableNameUtil.SYS_PRIV+".insertPrivilege", yzPrivilege);
	}

	/**
	 * 获取角色列表
	 * 
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> getSlectUserPriv(String organization) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PRIV + ".getSlectUserPriv", organization);
		return list;
	}

	/**
	 * 指定门诊+公共角色
	 * 
	 * @param conn
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> getSlectUserPrivWithCommon(String organization) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PRIV + ".getSlectUserPrivWithCommon", organization);
		return list;
	}

	/**
	 * 统计绑定该角色的用户数【主角色】
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean IsHaveChild(String id) throws Exception {
		int count1 = (int) dao.findForObject(TableNameUtil.SYS_PRIV + ".IsHaveChild", id);
		return count1 > 0;
	}

	/**
	 * 统计绑定该角色的用户数【辅助角色】
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean IsHaveChildOther(String id) throws Exception {
		int count = 0;
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PRIV + ".IsHaveChildOther", null);
		for (JSONObject jsonObject : list) {
			String pother = jsonObject.getString("user_priv_other");
			if ("".equals(pother.trim())) {
				continue;
			}
			String[] privArrr = pother.split(",");
			for (String priv : privArrr) {
				if (id.equals(priv)) {
					count++; // 这儿，如果不需要查找出所有，那么只要count > 0 就达到目的了 yangsen
				}
			}
		}
		return count > 0;
	}

	/**
	 * 统计角色数量
	 * 
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public int count(String organization) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.SYS_PRIV + ".count", organization);
		return count;
	}

	public int countUserPrivileges(String belongs_to) throws Exception {
		return (int) dao.findForObject(TableNameUtil.SYS_PRIV+".countUserPrivileges", belongs_to);
	}

	/**
	 * 根据角色名称统计数量
	 * 
	 * @param conn
	 * @param dept
	 * @return
	 * @throws Exception
	 */
	public int countByPrivName(YZPriv priv) throws Exception {
		int num = (int) dao.findForObject(TableNameUtil.SYS_PRIV + ".countByPrivName", priv);
		return num;
	}

	/**
	 * 根据部门ID进行分页查询
	 * 
	 * @param conn
	 * @param bp
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public JSONObject selectPage(BootStrapPage bp, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("organization", organization);
		map.put("search", bp.getSearch());
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		// 分页插件后的查询，会自动进行分页
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PRIV + ".selectPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	public JSONObject selectDetail(String seqId, HttpServletRequest request) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PRIV + ".selectDetail", seqId);
		if (list != null && list.size() > 0) {
			JSONObject userPriv = list.get(0);
			String privIdStr = userPriv.getString("privIdStr");
			if (YZUtility.isNullorEmpty(privIdStr)) {
				for (String key : UserPrivUtil.userQxNameList) {
					userPriv.put(key, "");
				}
				userPriv.put(UserPrivUtil.qxFlag0_maxDiscount, "100");
				userPriv.put(UserPrivUtil.qxFlag19_maxVoidmoney, "0");
			} else {
				String[] valueArray = privIdStr.split(",");
				if (valueArray.length != UserPrivUtil.userQxNameList.size()) {
					// throw new Exception("权限数据异常！请联系系统管理员！");
					YZPriv dp = (YZPriv) dao.loadObjSingleUUID(TableNameUtil.SYS_PRIV, seqId);
					valueArray = preCheckInit(dp, request);
				}
				for (int i = 0; i < UserPrivUtil.userQxNameList.size(); i++) {
					String tmpVal = valueArray[i];
					if (UserPrivUtil.qxFlag19_maxVoidmoney.equals(UserPrivUtil.userQxNameList.get(i)) && YZUtility.isNullorEmpty(tmpVal)) {
						tmpVal = "0"; // 默认最大免除为0，即不能免除
					}
					userPriv.put(UserPrivUtil.userQxNameList.get(i), tmpVal);

				}
			}
			return userPriv;
		}
		return null;
	}

	/**
	 * 重新设置值
	 * 
	 * @param privArray
	 * @param privKey
	 * @param privValue
	 */
	public void setPersonPrivByKey(String[] privArray, String privKey, String privValue) {
		int index = 0;
		for (String key : UserPrivUtil.userQxNameList) {
			if (key.equals(privKey)) {
				break;
			}
			index++;
		}
		privArray[index] = privValue;

	}

	/**
	 * 存取与用户相关的权限之前，做检查和初始化操作
	 * 
	 * @param privSeqId
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public String[] preCheckInit(YZPriv userPriv, HttpServletRequest request) throws Exception {

		String privIdStr = userPriv.getPrivIdStr();
		if (privIdStr == null) {
			privIdStr = "";
		} else {
			privIdStr = privIdStr.trim();
		}

		if (!"".equals(privIdStr) && !privIdStr.endsWith(",")) { // 没有以逗号结尾的，就先补上逗号，容错处理
			privIdStr = privIdStr + ",";
		}

		int arraySize = 0;
		String[] qxArray = null;
		if (!YZUtility.isNullorEmpty(privIdStr) && privIdStr.contains(",")) {
			qxArray = privIdStr.split(",");
			arraySize = qxArray.length;
		}

		if (arraySize > UserPrivUtil.userQxNameList.size()) {
			// 如果权限数据异常，则去掉后面多余的
			StringBuffer newQx = new StringBuffer();
			for (int i = 0; i < UserPrivUtil.userQxNameList.size(); i++) {
				String idtmp = qxArray[i];
				if (YZUtility.isNullorEmpty(idtmp)) {
					idtmp = ConstUtil.EMPTY_SPACE; // 空格
				}
				newQx.append(idtmp).append(",");
			}
			// 重新赋值
			privIdStr = newQx.toString();
		}

		// 如果长度比list的size小，则先补齐数据
		if (arraySize < UserPrivUtil.userQxNameList.size()) {

			int count = UserPrivUtil.userQxNameList.size() - arraySize;
			for (int i = 0; i < count; i++) { // 这里要减一
				privIdStr = privIdStr + ConstUtil.EMPTY_SPACE; // 空格
				privIdStr = privIdStr + ",";
			}
			userPriv.setPrivIdStr(privIdStr);// 重新赋值,并更新

			// 获取数据库连接
			dao.updateSingleUUID(TableNameUtil.SYS_PRIV, userPriv);
		}

		// 重新获取数组
		String[] privArray = privIdStr.split(",");
		if (privArray.length != UserPrivUtil.userQxNameList.size()) {
			throw new Exception("权限数据异常！请联系系统管理员！");
		}

		return privArray;
	}

	/**   
	  * @Title: findGeneral   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param userPriv      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年8月6日 下午2:51:43
	  */  
	public YZPriv findGeneral(String userPriv) throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		map.put("userPriv", userPriv);
		return (YZPriv)dao.findForObject(TableNameUtil.SYS_PRIV+".findGeneral", map);
	}

}
