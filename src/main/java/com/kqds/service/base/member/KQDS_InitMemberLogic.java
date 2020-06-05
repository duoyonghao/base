package com.kqds.service.base.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsMember;

@Service
public class KQDS_InitMemberLogic {
	
	@Autowired
	private DaoSupport dao;
	
	public void batchSaveMember(List<KqdsMember> list) throws Exception {
		dao.batchUpdate("KQDS_MEMBER.batchSaveMember", list);
	}

}
