package com.kqds.service.sys.organization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZOrganization;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;

@SuppressWarnings("unchecked")
@Service
public class YZOrganizationLogic extends BaseLogic {

	@Autowired
	private DaoSupport dao;

	public List<YZOrganization> getOrganizationList() throws Exception {
		List<YZOrganization> list = (List<YZOrganization>) dao.findForList(TableNameUtil.SYS_ORGANIZATION + ".getOrganizationList", null);
		return list;
	}

}
