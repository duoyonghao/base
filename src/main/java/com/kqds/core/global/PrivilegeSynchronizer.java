package com.kqds.core.global;

import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.sys.YZPriv;
import com.kqds.entity.sys.YZPrivilege;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.service.sys.priv.YZPrivLogic;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
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
            while (true) {
                try {
                    String seqId = waitingUsers.take();
                    if(StringUtils.isNotEmpty(seqId)) {
                        //执行同步操作
                        logger.info("PrivilegeSynchronizer: user [{}] privileges changed", seqId);
                        //全部删除当前用户的权限，并重新创建
                        try {
                            yzPrivLogic.removeUserPrivileges(seqId);
                            YZPerson yzPerson = yzPersonLogic.getPersonBySeqId(seqId);
                            if(yzPerson != null) {
                                String privSeqId = yzPerson.getUserPriv();
                                YZPriv yzPriv = yzPrivLogic.getDetailBySeqId(privSeqId);
                                String visualPerson = yzPriv.getVisualPerson();
                                if(StringUtils.isNotEmpty(visualPerson)) {
                                    List<String> userSeqIds = Arrays.asList(visualPerson.split(","));
                                    userSeqIds.forEach(userSeqId->{
                                        YZPrivilege yzPrivilege = new YZPrivilege();
                                        yzPrivilege.setBelongs_to(seqId);
                                        yzPrivilege.setBelongs_to_name(yzPerson.getUserName());
                                        yzPrivilege.setUser_seq_id(userSeqId);
                                        YZPerson toUser = null;
                                        try {
                                            toUser = yzPersonLogic.getPersonBySeqId(userSeqId);
                                            yzPrivilege.setUser_name(toUser.getUserName());
                                        } catch (Exception e) {
                                            logger.error(e.getMessage());
                                        }
                                        try {
                                            yzPrivLogic.insertPrivilege(yzPrivilege);
                                        } catch (Exception e) {
                                            logger.error(e.getMessage());
                                        }
                                    });
                                }
                            }
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }

                    }
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
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
                            logger.error(e.getMessage());
                        }
                    });
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }.start();
        logger.info("PrivilegeSynchronizer initialized-----------");
    }


    public void notifyChanged(String userSeqId) {
        waitingUsers.add(userSeqId);
    }

}
