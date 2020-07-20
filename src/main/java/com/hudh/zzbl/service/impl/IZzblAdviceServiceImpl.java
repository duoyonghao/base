package com.hudh.zzbl.service.impl;

import com.hudh.lclj.dao.LcljTrackDao;
import com.hudh.zzbl.dao.AdviceDao;
import com.hudh.zzbl.entity.ZzblAdvice;
import com.hudh.zzbl.service.IZzblAdviceService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IZzblAdviceServiceImpl implements IZzblAdviceService {
    @Autowired
    private AdviceDao adviceDao ;
    @Autowired
    private LcljTrackDao lcljTrackDao;
    @Override
    public void saveCaseHistory(ZzblAdvice zzblAdvice) throws Exception {
        adviceDao.saveCaseHistory(zzblAdvice);
        Map<String,String> map=new HashMap<String,String>();
        map.put("form","anamnesis");
        map.put("status","1");
        map.put("id",zzblAdvice.getLcljId());
        lcljTrackDao.updateFormStatus(map);
    }

    @Override
    public List<JSONObject> findCaseHistoryById(String paramString) throws Exception {
        List<JSONObject> list = adviceDao.findCaseHistoryById(paramString);
        return list;
    }

    @Override
    public void updateCaseHistoryById(ZzblAdvice zzblAdvice) throws Exception {
        adviceDao.updateCaseHistoryById(zzblAdvice);
    }

    @Override
    public void deleteCaseHistory(String paramString) throws Exception {
        adviceDao.deleteCaseHistoryById(paramString);
    }

    @Override
    public JSONObject findCaseHistoryBySeqid(String paramString) throws Exception {
        JSONObject list=adviceDao.findCaseHistoryBySeqid(paramString);
        return list;
    }

}
