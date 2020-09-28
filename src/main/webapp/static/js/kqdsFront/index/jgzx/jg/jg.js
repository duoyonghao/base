//获取加工单状态中文
function getStatusC(s) {
    var t = "";
    if (s == 0) {
        t = '未发件';
    } else if (s == 1) {
        t = '已发件';
    } else if (s == 2) {
        t = '已回件';
    } else if (s == 3) {
        t = '戴走';
    } else if (s == 4) {
        t = '返工';
    } else if (s == 5) {
        t = '作废';
    } else if (s == 6) {
        t = '试戴中';
    } else if (s == 7) {
        t = '已带走';
    } 
    return t;
}
//绑定两个时间选择框的chage时间
$("#starttime,#endtime").change(function() {
    timeCompartAndFz("starttime", "endtime");
});
//绑定两个时间选择框的chage时间
$("#fjtime1,#fjtime2").change(function() {
    timeCompartAndFz("fjtime1", "fjtime2");
});
//绑定两个时间选择框的chage时间
$("#hjtime1,#hjtime2").change(function() {
    timeCompartAndFz("hjtime1", "hjtime2");
});
//绑定两个时间选择框的chage时间
$("#dztime1,#dztime2").change(function() {
    timeCompartAndFz("dztime1", "dztime2");
});
//绑定两个时间选择框的chage时间
$("#fgtime1,#fgtime2").change(function() {
    timeCompartAndFz("fgtime1", "fgtime2");
});

//提交操作
function submitsheet(status) {
    var paramstr = "?status=" + status + "";
    var url = contextPath + '/KQDS_MACHININGAct/updateStatus.act' + paramstr;
    onclickrow = JSON.stringify(onclickrow);
    var param = {onclickrow : onclickrow};
    $.axse(url, param,
    function(data) {
        if (data.retState == "0") {
            layer.alert('操作成功', {
                  
                end: function() {
                    onclickrow = "";
                    refresh();
                }
            });
        }
    },
    function() {
        layer.alert('操作失败！' );
    });
}

//点击作废
function zuofei() {
    //	layer.alert('作废', {
    //});
    if (onclickrow == null || onclickrow == "") {
        layer.alert('请选择需要作废的加工单' );
        return false;
    } else if (onclickrow.status != 0) {
        layer.alert('您的操作有误，当前加工单状态为: ' + getStatusC(onclickrow.status) + '' );
        return false;
    } else {
        layer.confirm('确定作废并重新创建一条加工单吗?', {
            btn: ['确定', '放弃'] //按钮
        },
        function() {
            submitsheet(5);
        });
    }
}
//点击删除
function del() {
    if (onclickrow == null || onclickrow == "") {
        layer.alert('请选择要删除的加工单' );
        return false;
    } else if (onclickrow.status != 0 && onclickrow.status != 3 && onclickrow.status != 4 && onclickrow.status != 5) {
        layer.alert('您的操作有误，只能删除 <span style="red">未发件、作废、戴走、返工</span> 的加工单！ ' );
        return false;
    } else {
        layer.confirm('确定删除该加工单吗？', {
            btn: ['确定', '放弃'] //按钮
        },
        function() {
            layer.confirm('删除后不能恢复，确认吗?', {
                btn: ['确定', '放弃'] //按钮
            },
            function() {
                var url = contextPath + '/KQDS_OutProcessingSheetAct/deleteObj.act?seqId=' + onclickrow.seqId;
                msg = layer.msg('加载中', {
                    icon: 16
                });
                $.axse(url, null,
                function(data) {
                    if (data.retState == "0") {
                        layer.alert('删除成功', {
                              
                            end: function() {
                                //								$('#quey').trigger("click");
                                refresh();
                            }
                        });
                    }
                },
                function() {
                    layer.alert('删除失败！'  );
                });
            });
        });
    }
}

//点击编辑
function bianji() {
    if (onclickrow == null || onclickrow == "") {
        layer.alert('请选择加工单' );
        return false;
    } else if (onclickrow.status != 0) {
        layer.alert('只能修改未发件的加工单' );
        return false;
    } else {
        insertDetail(onclickrow.seqId, onclickrow.usercode);
    }
}

//点击发件
function fajian() {
    if (onclickrow == null || onclickrow == "") {
        layer.alert('请选择需要发件的加工单' );
        return false;
    } else if (onclickrow.processunit == null || onclickrow.processunit == "") {
        layer.alert('当前加工单没有选择加工单位' );
        return false;
    }else if (onclickrow.status > 0) {
        layer.alert('您的操作有误，当前加工单状态为: ' + getStatusC(onclickrow.status) + '' );
        return false;
    } else {
        layer.confirm('确定发件吗？', {
            btn: ['确定', '放弃'] //按钮
        },
        function() {
            submitsheet(1);
        });
    }
}

//点击回件
function huijian() {
    //判断是否有权限开单 没有权限提示并终止
    if (canHj == 0) {
        if (onclickrow == null || onclickrow == "") {
            layer.alert('请选择需要回件的加工单'  );
            return false;
        } else if (onclickrow.status < 1 || onclickrow.status > 1) {
            layer.alert('您的操作有误，当前加工单状态为: ' + getStatusC(onclickrow.status) + ''  );
            return false;
        } else {
            layer.confirm('确定回件吗？', {
                btn: ['确定', '放弃'] //按钮
            },
            function() {
                submitsheet(2);
            });
        }
    } else {
        layer.alert('无权限，请联系管理员开通权限！' );
    }
}

//返工回件
function fghj() {
    //判断是否有权限开单 没有权限提示并终止
    if (canHj == 0) {
        if (onclickrow == null || onclickrow == "") {
            layer.alert('请选择需要回件的加工单'  );
            return false;
        } else if (onclickrow.status < 4 || onclickrow.status > 4) {
            layer.alert('您的操作有误，当前加工单状态为: ' + getStatusC(onclickrow.status) + ''  );
            return false;
        } else {
            layer.confirm('确定回件吗？', {
                btn: ['确定', '放弃'] //按钮
            },
            function() {
                submitsheet(2);
            });
        }
    } else {
        layer.alert('无权限，请联系管理员开通权限！' );
    }
}

//点击返工
function fangong() {
    if (onclickrow == null || onclickrow == "") {
        layer.alert('请选择需要返工的加工单' );
        return false;
    } else if (onclickrow.status < 3 || onclickrow.status > 3) {
        layer.alert('您的操作有误，当前加工单状态为: ' + getStatusC(onclickrow.status) + '' );
        return false;
    } else {
        layer.confirm('确定返工并重新创建一条加工单吗？', {
            btn: ['确定', '放弃'] //按钮
        },
        function() {
            submitsheet(4);
        });
    }
}

//打印
$('#dayin').on('click',
function() {
    if (onclickrow == null || onclickrow == "") {
        layer.alert('请选择需要打印的加工单' );
        return false;
    } else {
        var cindex = layer.confirm('确定打印加工单吗？', {
            btn: ['确认', '放弃'] //按钮
        },
        function() {
            // 关闭confirm确认框
            layer.close(cindex);
            // 先查询fyqrd页面在打印设置里 是a4还是a5，如果是a4则跳转a4界面
            var printSet = getPrintType("加工单");
            // 默认A5打印
            var jgurl = contextPath + '/KQDS_Print_SetAct/toJgdPrintPage.act?printpage='+printSet.printurl+'&printType=' + printSet.printtype + '&outprocessingsheetno=' + onclickrow.seqId + '&doctorno=' + onclickrow.doctorno + '&fajiantime=' + onclickrow.fajiantime + '&processingfactory=' + onclickrow.processingfactory + '&type=' + onclickrow.type + '&yaoqiu=' + onclickrow.yaoqiu + '&usercode=' + onclickrow.usercode;
            jgurl += '&cost_organization=' + onclickrow.organization;
            // 弹出打印页面
            layer.open({
                type: 2,
                title: '加工单明细',
                shadeClose: true,
                shade: 0.6,
                area: ['80%', '80%'],
                content: jgurl
            });

        });
    }
});

//新增明细
function insertDetail(id, uc) {
	layer.open({
        type: 2,
        title: '编辑加工单',
        shadeClose: false,
        shade: 0.6,
        area: ['90%', '90%'],
        content: contextPath + '/KQDS_OutProcessingSheetAct/toYcjgEdit.act?id=' + onclickrow.seqId
    });
}
//查看明细
function showDetail(id, uc) {
    layer.open({
        type: 2,
        title: '加工单详情 ' + id,
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['85%', '85%'],
        content: contextPath + '/KQDS_OutProcessingSheetAct/toDetail.act?usercode=' + uc + '&sheetno=' + id
    });
}