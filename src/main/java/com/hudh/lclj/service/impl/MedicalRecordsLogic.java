/**
 * @Title: MedicalRecordsLogic.java
 * @Package com.hudh.lclj.service.impl
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 海德堡联合空腔
 * @date: 2020年5月29日 上午9:42:53
 * @version V1.0
 */
package com.hudh.lclj.service.impl;

import com.hudh.lclj.dao.LcljTrackDao;
import com.hudh.lclj.entity.*;
import com.kqds.dao.DaoSupport;
import com.kqds.util.sys.TableNameUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: MedicalRecordsLogic
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 海德堡联合口腔
 * @date: 2020年5月29日 上午9:42:53
 */
@Service
public class MedicalRecordsLogic {

    @Autowired
    private DaoSupport dao;
    @Autowired
    private LcljTrackDao lcljTrackDao;

    /**
     * @throws Exception
     * @Title: installData
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param dp
     * @return: void
     * @dateTime:2020年5月29日 上午9:52:09
     */
    @Transactional
    public void installData(HudhSpecialitycheck dp) throws Exception {
        // TODO Auto-generated method stub
        dao.save(TableNameUtil.HUDH_SpecialityCheck + ".insertSelective", dp);
        Map<String,String> map=new HashMap<String,String>();
        map.put("form","examine");
        map.put("status","1");
        map.put("id",dp.getLcljId());
        lcljTrackDao.updateFormStatus(map);
    }

    /**
     * @throws Exception
     * @Title: updateDate
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param dp
     * @return: void
     * @dateTime:2020年5月29日 上午11:03:46
     */
    @Transactional
    public void updateDate(HudhSpecialitycheck dp) throws Exception {
        // TODO Auto-generated method stub
        dao.update(TableNameUtil.HUDH_SpecialityCheck + ".updateByseqId", dp);
    }

    /**
     * @throws Exception
     * @Title: updateRecord
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param dp
     * @return: void
     * @dateTime:2020年5月30日 上午10:01:56
     */
    @Transactional
    public void updateRecord(HudhOperationNote dp) throws Exception {
        // TODO Auto-generated method stub
        dao.update(TableNameUtil.HUDH_operationNote + ".updateRecord", dp);
    }

    /**
     * @throws Exception
     * @Title: installRecord
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param dp
     * @return: void
     * @dateTime:2020年5月30日 上午10:02:01
     */
    @Transactional
    public void insertRecord(HudhOperationNote dp) throws Exception {
        // TODO Auto-generated method stub
        dao.save(TableNameUtil.HUDH_operationNote + ".insertRecord", dp);
    }

    /**
     * @throws Exception
     * @Title: selectdata
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param map
     * @param: @return
     * @return: List<JSONObject>
     * @dateTime:2020年5月30日 下午2:09:30
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public List<JSONObject> selectdata(Map<String, String> map) throws Exception {
        // TODO Auto-generated method stub
        return (List<JSONObject>) dao.findForList(TableNameUtil.HUDH_SpecialityCheck + ".selectdata", map);
    }

    /**
     * @throws Exception
     * @Title: selectRecord
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param map
     * @param: @return
     * @return: List<JSONObject>
     * @dateTime:2020年5月30日 下午2:09:41
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public List<JSONObject> selectRecord(Map<String, String> map) throws Exception {
        // TODO Auto-generated method stub
        return (List<JSONObject>) dao.findForList(TableNameUtil.HUDH_operationNote + ".selectRecord", map);
    }

    /**
     * @throws Exception
     * @Title: selectAcography
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param map
     * @param: @return
     * @return: List<JSONObject>
     * @dateTime:2020年5月30日 下午3:11:47
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public List<JSONObject> selectAcography(Map<String, String> map) throws Exception {
        // TODO Auto-generated method stub
        return (List<JSONObject>) dao.findForList(TableNameUtil.HUDH_Acography + ".selectAcography", map);
    }

    /**
     * @throws Exception
     * @Title: update
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param dp
     * @return: void
     * @dateTime:2020年5月30日 下午3:17:27
     */
    @Transactional
    public void update(HudhAcography dp) throws Exception {
        // TODO Auto-generated method stub
        dao.update(TableNameUtil.HUDH_Acography + ".update", dp);
    }

    /**
     * @throws Exception
     * @Title: install
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param dp
     * @return: void
     * @dateTime:2020年5月30日 下午3:17:32
     */
    @Transactional
    public void insert(HudhAcography dp) throws Exception {
        // TODO Auto-generated method stub
        dao.save(TableNameUtil.HUDH_Acography + ".insert", dp);
    }

    /**
     * @Title: findVerification
     * @Description: [id](这里用一句话描述这个方法的作用)
     * @author admin
     * @param: * @param id
     * @return: void
     * @dateTime: 2020/6/12 17:18
     */
    public List<JSONObject> findVerification(String id) throws Exception {
        return (List<JSONObject>) dao.findForList(TableNameUtil.HUDH_LCLJ_Verification + ".selectVerificationByCode", id);
    }

    /**
     * @Title: updateVerification
     * @Description: [dp](这里用一句话描述这个方法的作用)
     * @author admin
     * @param: * @param dp
     * @return: void
     * @dateTime: 2020/6/12 17:24
     */
    public void updateVerification(LcljVerification dp) throws Exception {
        dao.update(TableNameUtil.HUDH_LCLJ_Verification + ".updateByPrimaryKeySelective", dp);
    }

    /**
     * @Title: SaveVerification
     * @Description: [dp](这里用一句话描述这个方法的作用)
     * @author admin
     * @param: * @param dp
     * @return: void
     * @dateTime: 2020/6/12 17:24
     */
    public void SaveVerification(LcljVerification dp) throws Exception {
        dao.save(TableNameUtil.HUDH_LCLJ_Verification + ".insert", dp);
    }

    /**
     * @Title: insertFamiliar
     * @Description: [dp](这里用一句话描述这个方法的作用)
     * @author admin
     * @param: * @param dp
     * @return: void
     * @dateTime: 2020/6/15 15:50
     */
    public void insertFamiliar(LcljFamiliar dp) throws Exception {
        dao.save(TableNameUtil.HUDH_LCLJ_Familiar + ".insertFamiliar", dp);
    }

    /**
     * @Title: findFamiliar
     * @Description: [id](这里用一句话描述这个方法的作用)
     * @author admin
     * @param: * @param id
     * @return: java.util.List<net.sf.json.JSONObject>
     * @dateTime: 2020/6/15 15:50
     */
    public JSONObject findFamiliar(String id) throws Exception {
        return (JSONObject) dao.findForObject(TableNameUtil.HUDH_LCLJ_Familiar + ".selectByPrimaryKey", id);
    }
}
