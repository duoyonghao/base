package com.hudh.ykzz.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hudh.util.HUDHStaticVar;
import com.hudh.util.HUDHUtil;
import com.hudh.ykzz.dao.YkzzManuDao;
import com.hudh.ykzz.entity.YkzzManu;
import com.hudh.ykzz.service.IYkzzManuService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;
@Service
public class YkzzManuServiceImpl implements IYkzzManuService{
	/**
	 * 药库供应商dao
	 */
	@Autowired
	private YkzzManuDao ykzzManuDao;
	@Override
	public void insertYkzzManu(YkzzManu ykzzManu, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		ykzzManu.setId(YZUtility.getUUID());
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request);
		ykzzManu.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
		ykzzManu.setCreator(person.getSeqId());
		ykzzManu.setOrganization(organization);
		ykzzManuDao.insertYkzzManu(ykzzManu);
	}

	@Override
	public YkzzManu findYkzzManuById(String id) throws Exception {
		// TODO Auto-generated method stub
		return ykzzManuDao.findYkzzManuById(id);
	}

	@Override
	public void deleteYkzzManuById(String id) throws Exception {
		// TODO Auto-generated method stub
		ykzzManuDao.deleteYkzzManuById(id);
	}

	@Override
	public void updateYkzzManuById(YkzzManu ykzzManu) throws Exception {
		// TODO Auto-generated method stub
		ykzzManuDao.updateYkzzManuById(ykzzManu);
	}

	@Override
	public List<JSONObject> findAllManu(String organization) throws Exception {
		// TODO Auto-generated method stub
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = ykzzManuDao.findAllManu(organization);
		return list;
	}

	@Override
	public JSONObject findManuByCode(String manuCode) throws Exception {
		// TODO Auto-generated method stub
		JSONObject jo = new JSONObject();
		jo = ykzzManuDao.findManuByCode(manuCode);
		return jo;
	}

}
