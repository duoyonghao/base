package com.kqds.core.global;

import com.kqds.entity.sys.YZOrderPriv;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.sys.YZPriv;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.service.sys.priv.YZPrivLogic;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

public class OrderPrivilegeSynchronizer implements InitializingBean {
    Logger logger = LoggerFactory.getLogger(OrderPrivilegeSynchronizer.class);
    @Autowired
    YZPrivLogic yzPrivLogic;

    @Autowired
    YZPersonLogic yzPersonLogic;

    private ArrayBlockingQueue<String> waitingOrderUsers = new ArrayBlockingQueue<>(1000);
    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(() -> {
            while (true) {
                try {
                    String seqId = waitingOrderUsers.take();
                    if (StringUtils.isNotEmpty(seqId)) {
                        //执行同步操作
                        logger.info("OrderPrivilegeSynchronizer: user [{}] orderPrivileges changed", seqId);
                        //全部删除当前用户的权限，并重新创建
                        try {
                            yzPrivLogic.removeUserOrderPriv(seqId);
                            YZPerson yzPerson = yzPersonLogic.getPersonBySeqId(seqId);
                            if (yzPerson != null) {
                                String privSeqId = yzPerson.getUserPriv();
                                YZPriv yzPriv = yzPrivLogic.getDetailBySeqId(privSeqId);
                                List<String> orderUserSeqIds = new ArrayList<String>();
                                //预约可见人
                                String orderVisualPerson = yzPriv.getOrderVisualPerson();
                                //预约可见部门
                                String orderVisualDept=yzPriv.getOrderVisualDept();
                                //使自己能看到自己
                                orderUserSeqIds.add(seqId);
                                if (StringUtils.isNotEmpty(orderVisualPerson) && StringUtils.isNotEmpty(orderVisualDept)) {
                                    orderUserSeqIds = getSeqIds(orderUserSeqIds, orderVisualPerson, orderVisualDept);
                                }else if(StringUtils.isNotEmpty(orderVisualPerson) && StringUtils.isEmpty(orderVisualDept)){
                                    orderUserSeqIds.addAll(Arrays.asList(orderVisualPerson.split(",")));
                                }else if(StringUtils.isEmpty(orderVisualPerson) && StringUtils.isNotEmpty(orderVisualDept)){
                                    List<String> deptSeqIds = Arrays.asList(orderVisualDept.split(","));
                                    orderUserSeqIds.addAll(yzPersonLogic.findPersonalByDeptList(deptSeqIds));
                                }
                                orderUserSeqIds.forEach(orderUserSeqId -> {
                                    YZOrderPriv yzOrderPriv = new YZOrderPriv();
                                    yzOrderPriv.setOrderBelongsTo(seqId);
                                    yzOrderPriv.setOrderBelongsToName(yzPerson.getUserName());
                                    yzOrderPriv.setOrderUserSeqId(orderUserSeqId);
                                    YZPerson toUser = null;
                                    try {
                                        toUser = yzPersonLogic.getPersonBySeqId(orderUserSeqId);
                                        yzOrderPriv.setOrderUserName(toUser.getUserName());
                                    } catch (Exception e) {
                                        logger.error(e.getMessage());
                                    }
                                    try {
                                        yzPrivLogic.insertOrderPriv(yzOrderPriv);
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
        }, "OrderPrivilegeSynchronizer-main").start();

        //初始化同步操作
        new Thread() {
            @Override
            public void run() {
                try {
                    List<YZPerson> users = yzPersonLogic.getAll();
                    List<String> sources = users.stream().map(YZPerson::getSeqId).collect(Collectors.toList());
                    sources.forEach(seqId -> {
                        try {
                            int count = yzPrivLogic.countUserOrderPriv(seqId);
                            if (count == 0) {
                                waitingOrderUsers.add(seqId);
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
        logger.info("OrderPrivilegeSynchronizer initialized-----------");
    }
    private List<String> getSeqIds(List<String> orderUserSeqIds, String orderVisualPerson, String orderVisualDept) throws Exception {
        List<String> orderPersonList = Arrays.asList(orderVisualPerson.split(","));
        List<String> deptSeqIds = Arrays.asList(orderVisualDept.split(","));
        //传入部门list查询对应的员工
        List<String> list = yzPersonLogic.findPersonalByDeptList(deptSeqIds);
        orderUserSeqIds.addAll(list);
        orderUserSeqIds.addAll(orderPersonList);
        orderUserSeqIds = orderUserSeqIds.stream().distinct().collect(Collectors.toList());
        return orderUserSeqIds;
    }
    public void notifyChanged(String userSeqId) {
        waitingOrderUsers.add(userSeqId);
    }
}
