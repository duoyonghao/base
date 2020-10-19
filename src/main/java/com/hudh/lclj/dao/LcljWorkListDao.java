package com.hudh.lclj.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hudh.lclj.entity.LcljWorklist;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;
@Service
public class LcljWorkListDao {
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 创建流程
	 * @param LcljWorklist
	 * @return
	 * @throws Exception
	 */
	public int insertWorkList(LcljWorklist LcljWorklist) throws Exception{
		return (int) dao.save("HUDH_LCLJ_WORKLIST.insertWorkList", LcljWorklist);
	}
	
	/**
	 * 根据临床路径编号查找当前实例下的待办
	 * @param LcljWorklist
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LcljWorklist> findWorkByOrderNumber(LcljWorklist LcljWorklist) throws Exception{
		List<LcljWorklist> list = (List<LcljWorklist>) dao.findForList("HUDH_LCLJ_WORKLIST.findWorkByOrderNumber", LcljWorklist);
		return list;
	}
	
	/**
	 * 根据编号更新流程信息（提交下一步时待办更新成已办）
	 * @param LcljWorklist
	 * @return
	 * @throws Exception
	 */
	public void updateWorkByOrderNumber(LcljWorklist LcljWorklist) throws Exception{
		dao.update("HUDH_LCLJ_WORKLIST.updateWorkByOrderNumber", LcljWorklist);
	}
	
	/**
	 * 根据临床路径编号查找当前实例所有记录
	 * @param orderNumber
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LcljWorklist> findHadWorkList(String orderNumber) throws Exception{
		List<LcljWorklist> list = (List<LcljWorklist>) dao.findForList("HUDH_LCLJ_WORKLIST.findHadWorkList", orderNumber);
		return list;
	}
	
	/**
	 * 根据临床路径编号查找当前实例所有记录
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LcljWorklist> findHadWorkByOrderNumberAndNodeId(Map<String,String> dataMap) throws Exception{
		List<LcljWorklist> list = (List<LcljWorklist>) dao.findForList("HUDH_LCLJ_WORKLIST.findHadWorkByOrderNumberAndNodeId", dataMap);
		return list;
	}
	
	/**
	 * 获取所有的流程实例记录
	 * @param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LcljWorklist> findAllWorkList() throws Exception{
		List<LcljWorklist> list = (List<LcljWorklist>) dao.findForList("HUDH_LCLJ_WORKLIST.findAllWorkList", null);
		return list;
	}
	
	/**
	 * 改变流程记录的超期状态
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	public void updateOverdueStatus(Map<String,String> dataMap) throws Exception{
		dao.update("HUDH_LCLJ_WORKLIST.updateOverdueStatus", dataMap);
	}
	
	/**
	 * 退回时获取以前办理时保存的业务数据
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public LcljWorklist selectHadWorkData(Map<String,String> dataMap) throws Exception{
		LcljWorklist lcljWorklist = (LcljWorklist) dao.findForObject("HUDH_LCLJ_WORKLIST.selectHadWorkData", dataMap);
		return lcljWorklist;
	}

	public String findCreatetimeWorkListByNode(Map<String,String> dataMap) throws Exception{
		String createtime = (String) dao.findForObject("HUDH_LCLJ_WORKLIST.findCreatetimeWorkListByNode", dataMap);
		return createtime;
	}
}
