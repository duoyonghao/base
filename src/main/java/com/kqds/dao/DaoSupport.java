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

@Repository("daoSupport")
public class DaoSupport implements DAO {
  @Autowired
  private SqlSessionTemplate sqlSessionTemplate;
  
  public Object save(String str, Object obj) throws Exception {
    return Integer.valueOf(this.sqlSessionTemplate.insert(str, obj));
  }
  
  public Object batchSave(String str, List objs) throws Exception {
    return Integer.valueOf(this.sqlSessionTemplate.insert(str, objs));
  }
  
  public void batchInsert(String str, List objs) throws Exception {
    SqlSessionFactory sqlSessionFactory = this.sqlSessionTemplate.getSqlSessionFactory();
    SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
    try {
      if (objs != null) {
        for (int i = 0; i < objs.size(); i++)
          sqlSession.insert(str, objs.get(i)); 
        sqlSession.flushStatements();
        sqlSession.commit();
        sqlSession.clearCache();
      } 
    } finally {
      sqlSession.close();
    } 
  }
  
  public Object update(String str, Object obj) throws Exception {
    return Integer.valueOf(this.sqlSessionTemplate.update(str, obj));
  }
  
  public void batchUpdate(String str, List objs) throws Exception {
    SqlSessionFactory sqlSessionFactory = this.sqlSessionTemplate.getSqlSessionFactory();
    SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
    try {
      if (objs != null) {
        for (int i = 0; i < objs.size(); i++)
          sqlSession.update(str, objs.get(i)); 
        sqlSession.flushStatements();
        sqlSession.commit();
        sqlSession.clearCache();
      } 
    } finally {
      sqlSession.close();
    } 
  }
  
  public Object batchDelete(String str, List objs) throws Exception {
    return Integer.valueOf(this.sqlSessionTemplate.delete(str, objs));
  }
  
  public Object delete(String str, Object obj) throws Exception {
    return Integer.valueOf(this.sqlSessionTemplate.delete(str, obj));
  }
  
  public Object deleteAll(String str) throws Exception {
    return Integer.valueOf(this.sqlSessionTemplate.delete(str));
  }
  
  public Object findForObject(String str, Object obj) throws Exception {
    return this.sqlSessionTemplate.selectOne(str, obj);
  }
  
  public Object findForList(String str, Object obj) throws Exception {
    return this.sqlSessionTemplate.selectList(str, obj);
  }
  
  public Object findForMap(String str, Object obj, String key, String value) throws Exception {
    return this.sqlSessionTemplate.selectMap(str, obj, key);
  }
  
  public Object loadList(String tableName, Map<String, String> filter) throws Exception {
    Map<String, Map<String, String>> map = new HashMap<>();
    map.put("params", filter);
    return findForList(String.valueOf(tableName) + ".selectBeanListByMap", map);
  }
  
  public Object loadObjSingleUUID(String tableName, String seqId) throws Exception {
    return this.sqlSessionTemplate.selectOne(String.valueOf(tableName) + ".selectByPrimaryKey", seqId);
  }
  
  public void saveSingleUUID(String tableName, Object dp) throws Exception {
    save(String.valueOf(tableName) + ".insertSelective", dp);
  }
  
  public void updateSingleUUID(String tableName, Object dp) throws Exception {
    update(String.valueOf(tableName) + ".updateByPrimaryKeySelective", dp);
  }
  
  public int deleteSingleUUID(String tableName, String seqId) throws Exception {
    return ((Integer)delete(String.valueOf(tableName) + ".deleteByPrimaryKey", seqId)).intValue();
  }
  
  public int selectCount(String tableName, Map filter) throws Exception {
    Map<String, Map> map = new HashMap<>();
    map.put("params", filter);
    int count = ((Integer)findForObject(String.valueOf(tableName) + ".selectCountByMap", map)).intValue();
    return count;
  }
  
  public Object loadList4One(String tableName, Map<String, String> filter) throws Exception {
    List<Object> list = (List<Object>)loadList(tableName, filter);
    if (list.size() == 0)
      return null; 
    list.size();
    return list.get(0);
  }
}
