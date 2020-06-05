package com.kqds.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author FH Q313596790 修改时间：2015、12、11
 */
@SuppressWarnings("rawtypes")
@Repository("daoSupport")
public class DaoSupport implements DAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 保存对象
	 * 
	 * @param str
	 * @param obj
	 * @return
	 * @throws ExceptionR
	 */
	public Object save(String str, Object obj) throws Exception {
		return sqlSessionTemplate.insert(str, obj);
	}

	/**
	 * 批量更新
	 * 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object batchSave(String str, List objs) throws Exception {
		return sqlSessionTemplate.insert(str, objs);
	}
	public void batchInsert(String str, List objs) throws Exception {
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
		// 批量执行器
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		try {
			if (objs != null) {
				for (int i = 0; i < objs.size(); i++) {
					sqlSession.insert(str, objs.get(i));
				}
				sqlSession.flushStatements();
				sqlSession.commit();
				sqlSession.clearCache();
			}
		} finally {
			sqlSession.close();
		}
	}
	/**
	 * 修改对象
	 * 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object update(String str, Object obj) throws Exception {
		return sqlSessionTemplate.update(str, obj);
	}

	/**
	 * 批量更新
	 * 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void batchUpdate(String str, List objs) throws Exception {
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
		// 批量执行器
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		try {
			if (objs != null) {
				for (int i = 0; i < objs.size(); i++) {
					sqlSession.update(str, objs.get(i));
				}
				sqlSession.flushStatements();
				sqlSession.commit();
				sqlSession.clearCache();
			}
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * 批量更新
	 * 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object batchDelete(String str, List objs) throws Exception {
		return sqlSessionTemplate.delete(str, objs);
	}

	/**
	 * 删除对象
	 * 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object delete(String str, Object obj) throws Exception {
		return sqlSessionTemplate.delete(str, obj);
	}

	/**
	 * 清空表
	 * 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object deleteAll(String str) throws Exception {
		return sqlSessionTemplate.delete(str);
	}

	/**
	 * 查找对象
	 * 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForObject(String str, Object obj) throws Exception {
		return sqlSessionTemplate.selectOne(str, obj);
	}

	/**
	 * 查找对象
	 * 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForList(String str, Object obj) throws Exception {
		return sqlSessionTemplate.selectList(str, obj);
	}

	public Object findForMap(String str, Object obj, String key, String value) throws Exception {
		return sqlSessionTemplate.selectMap(str, obj, key);
	}

	/** yz base add **/

	/**
	 * 实现之前的 logic.loadList方法
	 */
	public Object loadList(String tableName, Map<String, String> filter) throws Exception {
		// 由于没有找到合适的办法直接循环Map的key和value，这里先嵌套下
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		map.put("params", filter);
		return findForList(tableName + ".selectBeanListByMap", map);
	}

	/**
	 * 实现之前的 logic.loadObjSingleUUID方法
	 */
	public Object loadObjSingleUUID(String tableName, String seqId) throws Exception {
		return sqlSessionTemplate.selectOne(tableName + ".selectByPrimaryKey", seqId);
	}

	/**
	 * 实现之前的 logic.saveSingleUUID方法
	 * 
	 * @param dp
	 * @param tableName
	 * @param request
	 * @throws Exception
	 */
	public void saveSingleUUID(String tableName, Object dp) throws Exception {
		save(tableName + ".insertSelective", dp);
	}

	/**
	 * 实现之前的 logic.updateSingleUUID方法
	 * 
	 * @param dp
	 * @param tableName
	 * @param request
	 * @throws Exception
	 */
	public void updateSingleUUID(String tableName, Object dp) throws Exception {
		update(tableName + ".updateByPrimaryKeySelective", dp);
	}

	/**
	 * 实现之前的 logic.deleteSingleUUID方法
	 * 
	 * @param dp
	 * @param tableName
	 * @param request
	 * @throws Exception
	 */
	public int deleteSingleUUID(String tableName, String seqId) throws Exception {
		return (int) delete(tableName + ".deleteByPrimaryKey", seqId);
	}

	/**
	 * 目前只支持 等于 条件的查询
	 */
	public int selectCount(String tableName, Map filter) throws Exception {
		// 由于没有找到合适的办法直接循环Map的key和value，这里先嵌套下
		Map<String, Map> map = new HashMap<String, Map>();
		map.put("params", filter);
		int count = (int) findForObject(tableName + ".selectCountByMap", map);
		return count;
	}

	@SuppressWarnings("unchecked")
	public Object loadList4One(String tableName, Map<String, String> filter) throws Exception {
		List<Object> list = (List<Object>) loadList(tableName, filter);
		if (list.size() == 0) {
			return null;
		}

		if (list.size() > 1) {
		}
		return list.get(0);
	}
}
