package com.kqds.core.global;

import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.service.sys.priv.YZPrivLogic;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

public class PrivilegeSynchronizer implements InitializingBean {
    Logger logger = LoggerFactory.getLogger(PrivilegeSynchronizer.class);

    @Autowired
    YZPrivLogic yzPrivLogic;

    @Autowired
    YZPersonLogic yzPersonLogic;

    private ArrayBlockingQueue<String> waitingUsers = new ArrayBlockingQueue<>(1000);
    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(() -> {
            String seqId;
            while (true) {
                try {
                    seqId = waitingUsers.take();
                    if(StringUtils.isNotEmpty(seqId)) {
                        //执行同步操作
                        logger.info("PrivilegeSynchronizer: can not find user [{}] privileges", seqId);
                    }
                } catch (InterruptedException e) {
                    logger.debug(e.getMessage());
                }

            }
        },"PrivilegeSynchronizer-main").start();

        //初始化同步操作
        new Thread(){
            @Override
            public void run() {
                try {
                    List<YZPerson> users = yzPersonLogic.getAll();
                    List<String> sources = users.stream().map(YZPerson::getSeqId).collect(Collectors.toList());
                    sources.forEach(seqId ->{
                        try {
                            int count = yzPrivLogic.countUserPrivileges(seqId);
                            if(count == 0) {
                                waitingUsers.add(seqId);
                            }
                        } catch (Exception e) {
                            logger.debug(e.getMessage());
                        }
                    });
                } catch (Exception e) {
                    logger.debug(e.getMessage());
                }
            }
        }.start();
        logger.info("PrivilegeSynchronizer initialized-----------");
    }


    public void notifyChanged(String userSeqId) {
        waitingUsers.add(userSeqId);
    }

}
