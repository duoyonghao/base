/**
 * 根据person表的用户ID获取用户姓名，并直接返回
 * ajax 同步请求
 */
function getPerUserNameBySeqIdTB(id) {
    var value = "";
    var detailurl = contextPath + "/YZPersonAct/getPersonNameStrBySeqIds.act?ids=" + id;
    $.axse(detailurl, null,
        function (data) {
            value = data.data;
        },
        function () {
            layer.alert('查询出错！');
        });
    return value;
}

//验证人员是否是当前门诊的
function yzUser(id) {
    var value = "";
    var detailurl = contextPath + "/YZPersonAct/yzUser.act?ids=" + id;
    $.axse(detailurl, null,
        function (data) {
            value = data.data;
        },
        function () {
            layer.alert('查询出错！');
        });
    return value;
}

function getPersonMaxOrderno(deptId) {
    var value = "";
    var urldict = contextPath + "/YZPersonAct/getMaxOrderno.act?deptId=" + deptId;
    $.axse(urldict, null,
        function (data) {
            value = data.data;
        },
        function () {
            layer.alert('查询出错！');
        });
    return value;
}

/**
 * 根据用户ID获取用户姓名，并在id标签进行显示
 * ajax 同步请求
 */
function bindPerUserNameBySeqIdTB(id, param) {
    // 增加判断，如果Id为空，则不向后台发送请求
    if (param == null || param == '' || param == undefined || param == 'null') {
        $("#" + id).val('');
        return;
    }

    var detailurl = contextPath + "/YZPersonAct/getPersonNameStrBySeqIds.act"; //?ids=+ param;
    $.axse(detailurl, {"ids": param},
        function (data) {
            $("#" + id).val(data.data);
        },
        function () {
            layer.alert('查询出错！');
        });
}
/**
 * 根据用户ID获取用户姓名，并在id标签进行显示
 * ajax 异步请求
 */
function bindPerUserNameBySeqIdYB(id, param) {

    // 增加判断，如果Id为空，则不向后台发送请求
    if (param == null || param == '' || param == undefined || param == 'null') {
        $("#" + id).html('');
        return;
    }

    var detailurl = contextPath + "/YZPersonAct/getPersonNameStrBySeqIds.act?ids=" + param;
    $.axseY(detailurl, null,
    function(data) {
        if ($("#" + id).length <= 0) { //dom不存在
            setTimeout(function() {
                $("#" + id).html(data.data);
            },
            500);
        } else {
            $("#" + id).html(data.data);
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

/**
 * 用于列表展示
 * @param id
 * @param param
 */
function bindPerUserNameBySeqIdYB4List(id, param, num) {

    // 增加判断，如果Id为空，则不向后台发送请求
    if (param == null || param == '' || param == undefined || param == 'null') {
        $("#" + id).html('');
        return;
    }

    var detailurl = contextPath + "/YZPersonAct/getPersonNameStrBySeqIds.act?ids=" + param;
    $.axseY(detailurl, null,
    function(data) {

        var orgData = data.data;
        var showStr = data.data;
        if (orgData != null && orgData.length > num) {
            showStr = orgData.substring(0, num) + '...';
        }

        if ($("#" + id).length <= 0) { //dom不存在
            setTimeout(function() {
                $("#" + id).html(showStr);
                $("#" + id).attr("title", orgData);
            },
            500);
        } else {
            $("#" + id).html(showStr);
            $("#" + id).attr("title", orgData);
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}


//二级联动，根据前一个select 获取后一个select
function selectChangeTwo(firstId, twoId,showleave) {
    var dict = $("#" + twoId);
    $(document).on("change", "#" + firstId,
    function() {
        var value = $('#' + firstId).find("option:selected").val();
        if (!value) {
            dict.empty();
            return; // 终止执行
        }
        var url = projectName + '/YZPersonAct/getUserListByDeptId.act?deptId=' + value+'&showleave='+showleave;
        $.axse(url, null,
        function(data) {
            if (data) {
            	dict.empty();
                dict.append("<option value=''>请选择</option>");
                var list = data;
                if (list.length > 0) {
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.userName + "</option>");
                    }
                }
            } else {
                layer.alert('查询失败', {
                      
                });
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });

    });
}

//二级联动，根据前一个select 获取后一个select  （+搜索刷新）--2019-10-18 licc
function selectChangeTwoSearch(firstId, twoId,showleave) {
    var dict = $("#" + twoId);
    $(document).on("change", "#" + firstId,
    function() {
        var value = $('#' + firstId).find("option:selected").val();
        if (!value) {
            dict.empty();
        	$('#'+twoId).selectpicker('destroy');
            dict.append("<option value=''>请选择</option>");
            return; // 终止执行
        }
        var url = projectName + '/YZPersonAct/getUserListByDeptId.act?deptId=' + value+'&showleave='+showleave;
        $.axse(url, null,
        function(data) {
            if (data) {
            	dict.empty();
                $('#'+twoId).selectpicker('destroy');
                dict.append("<option value=''>请选择</option>");
                var list = data;
                if (list.length > 0) {
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.userName + "</option>");
                    }
                    $('#'+twoId).selectpicker("refresh");//人员下拉框添加搜索功能 
                }
            } else {
                layer.alert('查询失败', {
                      
                });
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });

    });
}


function initPersonListByDeptTypeWithNull(id, deptType, defVal) {
    var defaultVal = ""; // 默认选中值
    var serverData = getDataListFromServer("YZPersonAct/getUserListByDeptType.act?deptType=" + deptType);
    if (defVal) {
        defaultVal = defVal;
    }
    if (serverData) {
        initSelectByListWithNull(id, serverData, 'seqId', 'userId', defaultVal);
    }
}



