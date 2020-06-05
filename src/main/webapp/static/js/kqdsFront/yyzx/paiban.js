scheduler.attachEvent("onEventSave",
function(id, e) {
    //判断是否由排班权限
    if (canPaiban == "0") { //没有权限  只能排自己的班
        if (e.personid != loginuser) {
            layer.alert('权限不足！只能对自己进行排班！'  );
            return false;
        }
    }
    if (id.length == 36 && canPaiban == 0) { //36位为uuid，表示是编辑
        layer.alert('权限不足,不能修改排班！' );
        return false;
    }
    var timestamp = Date.parse(new Date(getNowDay()+' 00:00:00'));
    var end_date = Date.parse(e.end_date);
    if (timestamp > end_date) {
        layer.alert('排班时间已过' );
        return false;
    }
    if (e.personid == "") {
        layer.alert('请选择人员' );
        return false;
    }
    return true;

});
/**  
 *添加保存事件数据操作  
 */
scheduler.attachEvent("onEventAdded",
function(id, e, is_new) {
    var param1 = getSelecttype(e.orderstatus);
    var B = e.start_date;
    var ss = B.getFullYear() + "-" + (B.getMonth() + 1) + "-" + B.getDate();
    //判断是否排过班  排过班的 不可新建 只能修改
    var ispaiban = checkPaiban(ss, e.personid);
    if (ispaiban) {
        layer.alert('该用户当天已排过班！' );
        return false;
    }
    if (param1[0].startdate != "") {
        ss += " " + param1[0].startdate + ":00";
    } else {
        ss += " " + "00:00:00";
    }
    var ss1 = B.getFullYear() + "-" + (B.getMonth() + 1) + "-" + B.getDate();
    if (param1[0].enddate != "") {
        ss1 += " " + param1[0].enddate + ":00";
    } else {
        ss1 += " " + "00:00:00";
    }
    var param = {
        personid: e.personid,
        orderstatus: e.orderstatus,
        remark: e.remark,
        startdate: ss,
        enddate: ss1
    };
    var url = contextPath + '/KQDS_PaibanAct/insertOrUpdate.act';
    var msg;
    $.axseSubmit(url, param,
    function() {
        msg = layer.msg('加载中', {
            icon: 16
        });
    },
    function(r) {
        if (r.retState == "0") {
            layer.alert('排班成功', {
                  
                end: function() {
                    loaddata();
                }
            });

        } else {
            layer.alert('排班失败'  );
        }
    },
    function() {
        layer.alert('排班失败' );
    });
    return true;
});
//修改处理 得到填写的数据，然后利用ajax提交到后台 
scheduler.attachEvent("onEventChanged",
function(id, e) {
    var param1 = getSelecttype(e.orderstatus);
    var B = e.start_date;
    var ss = B.getFullYear() + "-" + (B.getMonth() + 1) + "-" + B.getDate();
    if (param1[0].startdate != "") {
        ss += " " + param1[0].startdate + ":00";
    } else {
        ss += " " + "00:00:00";
    }
    var ss1 = B.getFullYear() + "-" + (B.getMonth() + 1) + "-" + B.getDate();
    if (param1[0].enddate != "") {
        ss1 += " " + param1[0].enddate + ":00";
    } else {
        ss1 += " " + "00:00:00";
    }
    var param = {
        seqId: e.id,
        personid: e.personid,
        orderstatus: e.orderstatus,
        remark: e.remark,
        startdate: ss,
        enddate: ss1

    };
    var url = contextPath + '/KQDS_PaibanAct/insertOrUpdate.act';
    var msg;
    $.axseSubmit(url, param,
    function() {
        msg = layer.msg('加载中', {
            icon: 16
        });
    },
    function(r) {
        if (r.retState == "0") {
            layer.alert('保存成功', {
                  
                end: function() {
                    loaddata();
                }
            });
        } else {
            layer.alert('保存失败'  );
        }
    },
    function() {
        layer.alert('保存失败' );
    });
    return true;
});

//删除 利用ajax提交到后台 
scheduler.attachEvent("onBeforeEventDelete",
function(id, e) {
    //判断是否由排班权限
    if (canPaiban == "0") { //没有权限  只能排自己的班
        /*if(e.personid != loginuser){*/
        layer.alert('权限不足！不能删除该排班！' );
        return false;
        /*}*/
    }
    var timestamp = Date.parse(new Date(getNowDay()+' 00:00:00'));
    var starttime = Date.parse(e.start_date);
    if (canPaiban == "1" && timestamp <= starttime) {
        var url = contextPath + '/KQDS_PaibanAct/deleteObj.act?seqId=' + id;
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
                layer.alert('删除成功', {
                      
                    end: function() {
                        loaddata();
                    }
                });
            }
        },
        function() {
            layer.alert('删除出错！'  );
        });
    } else {
        layer.alert('无法删除！' );
    }

});
function show_minical() {
    if (scheduler.isCalendarVisible()) scheduler.destroyCalendar();
    else scheduler.renderCalendar({
        position: "dhx_minical_icon",
        date: scheduler._date,
        navigation: true,
        handler: function(date, calendar) {
            scheduler.setCurrentView(date);
            scheduler.destroyCalendar();
        }
    });
}

//查询排班类型下拉选项
function getSelect() {
    var sections = [];
    var url = contextPath + "/KQDS_Paiban_TypeAct/selectDetailBytpename.act";
    $.axse(url, null,
    function(data) {
        var list = data.data;
        if (list != null) {
            if (list.length > 0) {
                for (var j = 0; j < list.length; j++) {
                    var attach = {};
                    attach.key = list[j].typename;
                    attach.label = list[j].typename;
                    sections.push(attach);
                }

            }
        }
    },
    function() {});
    return sections;
}
//查询部门下人员
function getSelectper(deptId, perId) {
    var sections = [];
    var url = contextPath + "/KQDS_PaibanAct/getPersoPaiban.act?deptId=" + deptId + "&seqId=" + perId;
    $.axse(url, null,
    function(data) {
        var list = data.list;
        if (list != null) {
            if (list.length > 0) {
                for (var j = 0; j < list.length; j++) {
                    var attach = {};
                    attach.key = list[j].seqId;
                    attach.label = list[j].userName;
                    sections.push(attach);
                }

            }
        }
    },
    function() {});
    return sections;
}
//判断当前时间是否排过班  排过班的 不可新建 只能修改
function checkPaiban(starttime, personid) {
    var flag = false;
    if (starttime != "") {
        var url = contextPath + '/KQDS_PaibanAct/checkPaiban.act?starttime=' + starttime + '&personid=' + personid;
        $.axse(url, null,
        function(data) {
            if (data.data != "0") { // 0 代表查到0条记录
                flag = true;
            }
        },
        function() {});
    }
    return flag;
}



/**  
 *获取排班时间段
 */
function getSelecttype(type) {
    var sections = [];
    var url = contextPath + "/KQDS_Paiban_TypeAct/selectDetailBytpename.act?typename=" + type;
    $.axse(url, null,
    function(data) {
        var list = data.data;
        if (list != null) {
            if (list.length > 0) {
                for (var j = 0; j < list.length; j++) {
                    var attach = {};
                    attach.startdate = list[j].startdate;
                    attach.enddate = list[j].enddate;
                    attach.typename = list[j].typename;
                    sections.push(attach);
                }

            }
        }
    },
    function() {});
    return sections;
}

//查询患者
/*
function getuser(type) {
    if (type == 0) {
        //选择患者
        layer.open({
            type: 2,
            title: '选择患者',
            shadeClose: true,
            shade: 0.6,
            area: ['550px', '80%'],
            content: contextPath + '/kqdsFront/hzjd/user_list.jsp',
            //iframe的url
            end: function(index, layero) {
                $("#usercode").val(usercodechild);
                $("#usercodeDesc").val(usernamechild);
            },
        });
    } else {
        //选择患者
        layer.open({
            type: 2,
            title: '选择患者',
            shadeClose: true,
            shade: 0.6,
            area: ['550px', '80%'],
            content: contextPath + '/kqdsFront/hzjd/user_list.jsp',
            //iframe的url
            end: function(index, layero) {
                $("#usercode1").val(usercodechild);
                $("#usercode1Desc").val(usernamechild);
            },
        });
    }
}*/