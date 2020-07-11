package com.kqds.core.global;

import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.sys.YZPriv;
import com.kqds.entity.sys.YZPrivilege;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.service.sys.priv.YZPrivLogic;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
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
                    if (StringUtils.isNotEmpty(seqId)) {
                        //执行同步操作
                        logger.info("PrivilegeSynchronizer: user [{}] privileges changed", seqId);
                        //全部删除当前用户的权限，并重新创建
                        try {
                            yzPrivLogic.removeUserPrivileges(seqId);
                            YZPerson yzPerson = yzPersonLogic.getPersonBySeqId(seqId);
                            if (yzPerson != null) {
                                String privSeqId = yzPerson.getUserPriv();
                                YZPriv yzPriv = yzPrivLogic.getDetailBySeqId(privSeqId);
                                //可见人
                                String visualPerson = yzPriv.getVisualPerson();
                                //可见部门
                                String visualDept = yzPriv.getVisualDept();
                                List<String> userSeqIds = new ArrayList<String>();
                                //使自己能看到自己
                                userSeqIds.add(seqId);
                                if (StringUtils.isNotEmpty(visualDept) && StringUtils.isNotEmpty(visualPerson)) {
                                    List<String> personList = Arrays.asList(visualPerson.split(","));
                                    List<String> deptSeqIds = Arrays.asList(visualDept.split(","));
                                    //传入部门list查询对应的员工
                                    List<String> list = yzPersonLogic.findPersonalByDeptList(deptSeqIds);
                                    userSeqIds.addAll(list);
                                    userSeqIds.addAll(personList);
                                    userSeqIds = userSeqIds.stream().distinct().collect(Collectors.toList());
                                } else if (StringUtils.isNotEmpty(visualPerson) && StringUtils.isEmpty(visualDept) ) {
                                    userSeqIds.addAll(Arrays.asList(visualPerson.split(",")));
                                } else if (StringUtils.isNotEmpty(visualDept) && StringUtils.isEmpty(visualPerson)) {
                                    List<String> deptSeqIds = Arrays.asList(visualDept.split(","));
                                    userSeqIds.addAll(yzPersonLogic.findPersonalByDeptList(deptSeqIds));
                                }
                                userSeqIds.forEach(userSeqId -> {
                                    YZPrivilege yzPrivilege = new YZPrivilege();
                                    yzPrivilege.setBelongsTo(seqId);
                                    yzPrivilege.setBelongsToName(yzPerson.getUserName());
                                    yzPrivilege.setUserSeqId(userSeqId);
                                    YZPerson toUser = null;
                                    try {
                                        toUser = yzPersonLogic.getPersonBySeqId(userSeqId);
                                        yzPrivilege.setUserName(toUser.getUserName());
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
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }

                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }, "PrivilegeSynchronizer-main").start();

        //初始化同步操作
        new Thread() {
            @Override
            public void run() {
                try {
                    List<YZPerson> users = yzPersonLogic.getAll();
                    List<String> sources = users.stream().map(YZPerson::getSeqId).collect(Collectors.toList());
                    sources.forEach(seqId -> {
                        try {
                            int count = yzPrivLogic.countUserPrivileges(seqId);
                            if (count == 0) {
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
