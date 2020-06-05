package com.hudh.ykzz.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.lclj.entity.LcljNode;
import com.hudh.util.HUDHStaticVar;
import com.hudh.util.HUDHUtil;
import com.hudh.ykzz.dao.YkzzTypeDao;
import com.hudh.ykzz.entity.YkzzType;
import com.hudh.ykzz.service.IYkzzTypeService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;
@Service
public class YkzzTypeServiceImpl implements IYkzzTypeService{
	/**
	 * 药库类型dao
	 */
	@Autowired
	private YkzzTypeDao ykzzTypeDao;
	@Override
	public void insertYkzzType(YkzzType ykzzType,HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		ykzzType.setId(YZUtility.getUUID());
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request);
		ykzzType.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
		ykzzType.setCreator(person.getSeqId());
		ykzzType.setOrganization(organization);
		ykzzTypeDao.insertYkzzType(ykzzType);
	}

	@Override
	public YkzzType findYkzzTypeById(String id) throws Exception {
		// TODO Auto-generated method stub
		return ykzzTypeDao.findYkzzTypeById(id);
	}

	@Override
	public void deleteYkzzTypeById(String id) throws Exception {
		// TODO Auto-generated method stub
		ykzzTypeDao.deleteYkzzTypeById(id);
	}

	@Override
	public void updateYkzzTypeById(YkzzType ykzzType) throws Exception {
		// TODO Auto-generated method stub
		ykzzTypeDao.updateYkzzTypeById(ykzzType);
	}

	@Override
	public List<JSONObject> findChildTypesByParentId(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = ykzzTypeDao.findChildTypesByParentId(map);
		Collections.sort(list, new Comparator<JSONObject>() {
			@Override
			public int compare(JSONObject o1, JSONObject o2) {
				int i = Integer.valueOf(o1.getString("orderno")) - Integer.valueOf(o2.getString("orderno"));
				return i;
			}
		});
		return list;
	}

	@Override
	public List<JSONObject> findAllTypes(String organization) throws Exception {
		// TODO Auto-generated method stub
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = ykzzTypeDao.findAllTypes(organization);
		Collections.sort(list, new Comparator<JSONObject>() {
			@Override
			public int compare(JSONObject o1, JSONObject o2) {
				int i = Integer.valueOf(o1.getString("orderno")) - Integer.valueOf(o2.getString("orderno"));
				return i;
			}
		});
		return list;
	}

		
}
