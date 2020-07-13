package com.kqds.controller.base.hzjd.util;

import com.kqds.entity.sys.BootStrapPage;
import net.sf.json.JSONObject;

import java.util.Map;
import java.util.concurrent.Callable;

public class RunnableUtil implements Callable {
    private Map<String, String> map;
    private BootStrapPage bp;
    private String flag;
    public RunnableUtil(BootStrapPage bp, Map<String, String> map, String flag) {
        this.map = map;
        this.bp = bp;
        this.flag = flag;
    }

    @Override
    public Object call() throws Exception {
        JSONObject g = ExportTableUtil.save(bp, map, flag);
        return g;
    }
}
