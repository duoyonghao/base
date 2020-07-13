package com.kqds.controller.base.hzjd.util;

import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;

import com.kqds.util.sys.spring.BeanUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class ExportTableUtil {
    @Autowired
    private static KQDS_UserDocumentLogic logic;
    static{
        logic=(KQDS_UserDocumentLogic) BeanUtil.getBean("KQDS_UserDocumentLogic");
    }

    public static JSONObject save(BootStrapPage bp, Map<String, String> map, String flag){
        JSONObject resut1=new JSONObject();
        try {
            //resut1 = logic.selectWithNopage2(bp,map,flag);// ###根据页面传值进行门诊条件过滤
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resut1;
    }
}
