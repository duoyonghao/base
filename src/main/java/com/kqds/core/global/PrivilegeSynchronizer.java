package com.kqds.core.global;

//import com.kqds.entity.sys.YZOrderPriv;
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
                                String visualPerson = yzPriv.getVisualPerson();
                                String visualDept = yzPriv.getVisualDept();
                                List<String> userSeqIds = new ArrayList<>();
                                //使自己能看到自己
                                userSeqIds.add(seqId);
                                if (StringUtils.isNotEmpty(visualDept) && StringUtils.isNotEmpty(visualPerson)) {
                                    List<String> visualPersonList = Arrays.asList(visualPerson.split(","));
                                    List<String> deptSeqIds = Arrays.asList(visualDept.split(","));
                                    List<String> list = yzPersonLogic.findPersonalByDeptList(deptSeqIds);
//                                    Map<String, Integer> map = new HashMap<String, Integer>(userSeqIds.size());
//                                    //List<Resource> differentList = new ArrayList<Resource>();
//                                    for (String userSId : userSeqIds) {
//                                        map.put(userSId, 1);
//                                    }
//                                    for (String resource1 : list) {
//                                        if (map.get(resource1) == null) {
//                                            userSeqIds.add(resource1);
//                                        }
//                                    }
                                    userSeqIds.addAll(list);
                                    userSeqIds.addAll(visualPersonList);
                                    userSeqIds = userSeqIds.stream().distinct().collect(Collectors.toList());
                                } else if (StringUtils.isNotEmpty(visualPerson)) {
                                    userSeqIds.addAll(Arrays.asList(visualPerson.split(",")));
                                } else if (StringUtils.isNotEmpty(visualDept)) {
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
//                                String orderVisualPerson = yzPriv.getOrderVisualPerson();
//                                if (StringUtils.isNotEmpty(orderVisualPerson)) {
//                                    List<String> oderUserSeqIds = Arrays.asList(orderVisualPerson.split(","));
//                                    oderUserSeqIds.forEach(orderUserSeqId -> {
//                                        YZOrderPriv yzOrderPriv = new YZOrderPriv();
//                                        yzOrderPriv.setOrderBelongsTo(seqId);
//                                        yzOrderPriv.setOrderBelongsToName(yzPerson.getUserName());
//                                        yzOrderPriv.setOrderUserSeqId(orderUserSeqId);
//                                        YZPerson toUser = null;
//                                        try {
//                                            toUser = yzPersonLogic.getPersonBySeqId(orderUserSeqId);
//                                            yzOrderPriv.setOrderUserName(toUser.getUserName());
//                                        } catch (Exception e) {
//                                            logger.error(e.getMessage());
//                                        }
//                                        try {
//                                            yzPrivLogic.insertOrderPriv(yzOrderPriv);
//                                        } catch (Exception e) {
//                                            logger.error(e.getMessage());
//                                        }
//                                    });
//                                }
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
