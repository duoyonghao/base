package com.hudh.zzbl.service.impl;

import com.hudh.zzbl.dao.AdviceDao;
import com.hudh.zzbl.entity.ZzblAdvice;
import com.hudh.zzbl.service.IZzblAdviceService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IZzblAdviceServiceImpl implements IZzblAdviceService {
    @Autowired
    private AdviceDao adviceDao ;
    @Override
    public void saveCaseHistory(ZzblAdvice zzblAdvice) throws Exception {
        adviceDao.saveCaseHistory(zzblAdvice);
    }

    @Override
    public JSONObject findCaseHistoryById(String paramString) throws Exception {
        JSONObject list = adviceDao.findCaseHistoryById(paramString);
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
}
