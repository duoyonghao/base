/**  
  *
  * @Title:  KQDS_hz_labelLogic.java   
  * @Package com.kqds.service.base.label   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月14日 上午11:25:46   
  * @version V1.0  
  */ 
package com.kqds.service.base.label;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsLabel;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  KQDS_hz_labelLogic   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月14日 上午11:25:46   
  *      
  */
@Service
public class KQDS_hz_labelLogic {
 
	@Autowired
	private DaoSupport dao;
	/**
	  * @Title: saveLabel   
	  * @Description: TODO(保存印象标签)   
	  * @param: @param label
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年8月14日 下午2:26:15
	 */
	@Transactional(rollbackFor = Exception.class)
	public Integer saveLabel(KqdsLabel label) throws Exception{
		return (Integer)dao.save(TableNameUtil.KQDS_HZ_label+".saveLabel", label);
	}
	/**   
	  * @Title: findLabel   
	  * @Description: TODO(查询标签库)   
	  * @param: @param string      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年8月15日 上午8:39:53
	  */  
	@SuppressWarnings("unchecked")
	public List<KqdsLabel> findLabel(String parentId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("parentId", parentId);
		return (List<KqdsLabel>) dao.findForList(TableNameUtil.KQDS_HZ_label+".findLabel", map);
	}
	/**   
	  * @Title: findLabel   
	  * @Description: TODO(查询标签库)   
	  * @param: @param string      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年8月15日 上午8:39:53
	  */  
	@SuppressWarnings("unchecked")
	public List<KqdsLabel> findLabelCondition(String parentId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("parentId", parentId);
		return (List<KqdsLabel>) dao.findForList(TableNameUtil.KQDS_HZ_label+".findLabelCondition", map);
	}
	/**   
	  * @Title: deLabel   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param parentId      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年8月15日 上午10:59:09
	  */  
	@Transactional(rollbackFor = Exception.class)
	public Integer deLabel(String[] seqId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		Integer status = null;
		for (int i = 0; i < seqId.length; i++) {	
			map.put("seqId", seqId[i]);
			status = (Integer)dao.delete(TableNameUtil.KQDS_HZ_label+".deLabel", map);
		}
		return status;
	}
	/**   
	  * @Title: updateLabel   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param kLabel      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年8月17日 下午3:44:35
	  */  
	@Transactional(rollbackFor = Exception.class)
	public Integer updateLabel(KqdsLabel kLabel) throws Exception {
		// TODO Auto-generated method stub
		return (Integer)dao.delete(TableNameUtil.KQDS_HZ_label+".updateLabel", kLabel);
	}
	/**   
	  * @Title: findLabelAll   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @return      
	  * @return: List<KqdsLabel>
	 * @throws Exception 
	  * @dateTime:2019年8月20日 下午4:38:48
	  */  
	@SuppressWarnings("unchecked")
	public List<JSONObject> findLabelAll() throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_HZ_label+".findLabelAll", null);
	}
	/**   
	  * @Title: fingLabelByCondition   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年8月21日 下午2:58:29
	  */  
	@SuppressWarnings("unchecked")
	public List<JSONObject> fingLabelByCondition(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_HZ_label+".fingLabelByCondition", map);
	}
	
	
	/**
	 * 2019-08-23 lwg
	 * 新增标签表
	 * @param kqdsHzLabel
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public int insertKqdsHzLabel(KqdsLabel  kqdsHzLabel) throws Exception{
		int i = (int) dao.save("KQDS_HZ_label.insertKqdsHzLabel", kqdsHzLabel);
		return i;
	}
	/**
	 * 2019-08-23 lwg
	 * 查询标签是否存在
	 * @param leveLabel
	 * @return
	 * @throws Exception
	 */
	public String selectKqdsHzLabelByLeveLabel(Map<String,String> map) throws Exception{
		String seqid=(String) dao.findForObject("KQDS_HZ_label.selectKqdsHzLabelByLeveLabel", map);
		return seqid;
	}
	public KqdsLabel selectKqdsHzLabelByUsercode(Map<String,String> map) throws Exception{
		KqdsLabel kqdsHzLabel=(KqdsLabel) dao.findForObject("KQDS_HZ_label.selectKqdsHzLabelByUsercode", map);
		return kqdsHzLabel;
	}
	public KqdsLabel findKqdsHzLabelParentIdByParentName(String parentName) throws Exception{
		KqdsLabel parentId=(KqdsLabel) dao.findForObject("KQDS_HZ_label.findKqdsHzLabelParentIdByParentName", parentName);
		return parentId;
	}
	public String findKqdsHzLabelSeqIdByLeveLabel(String leveLabel) throws Exception{
		String seqId=(String) dao.findForObject("KQDS_HZ_label.findKqdsHzLabelSeqIdByLeveLabel", leveLabel);
		return seqId;
	}
	
	/**
	 * 根据父级id查询子分类
	  * @Title: findParentId   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param paString
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2019年8月28日 下午5:49:57
	 */
	@SuppressWarnings("unchecked")
	public JSONObject findParentId(String paString) throws Exception {
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("parentId", paString);
		JSONObject json = new JSONObject();
		List<JSONObject> list = (List<JSONObject>) dao.findForList("KQDS_HZ_label.findParentId", dataMap);
		json.put("list", list);
		return json;
	}
}
