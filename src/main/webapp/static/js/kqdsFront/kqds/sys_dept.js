/**
 * 根据用户ID获取用户姓名，并在id标签进行显示
 * ajax 异步请求
 */
function bindDeptNameBySeqIdYB(id, param) {
    // 增加判断，如果Id为空，则不向后台发送请求
    if (param == null || param == '' || param == undefined || param == 'null') {
        $("#" + id).html('');
        return;
    }

    var detailurl = contextPath + "/YZDeptAct/getDeptNamesBySeqIds.act?seqIds=" + param;
    $.axseY(detailurl, null,
    function(data) {
        var val = data.data;
        if ($("#" + id).length <= 0) {
            //dom不存在
            setTimeout(function() {
                $("#" + id).html(val);
            },
            500);
        } else {
            $("#" + id).html(val);
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

// 列表显示
function bindDeptNameBySeqIdYB4List(id, param, num) {
    // 增加判断，如果Id为空，则不向后台发送请求
    if (param == null || param == '' || param == undefined || param == 'null') {
        $("#" + id).html('');
        return;
    }

    var detailurl = contextPath + "/YZDeptAct/getDeptNamesBySeqIds.act?seqIds=" + param;
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

/**
 * 根据用户ID获取用户姓名，并在id标签进行显示
 * ajax 同步请求
 */
function bindDeptNameBySeqIdTB(id, param) {
    if (param == null || param == "") {
        return "";
    }
    var detailurl = contextPath + "/YZDeptAct/getDeptNamesBySeqIds.act?seqIds=" + param;
    $.axse(detailurl, null,
    function(data) {
        $("#" + id).val(data.data);
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

/**
 * 根据dept表的用户ID获取用户姓名，并直接返回
 * ajax 同步请求
 */
function getDeptNameBySeqIdTB(deptIds) {
    if (deptIds == null || deptIds == "") {
        return "";
    }
    var value = "";
    var detailurl = contextPath + "/YZDeptAct/getDeptNamesBySeqIds.act?seqIds=" + deptIds;
    $.axse(detailurl, null,
    function(data) {
        value = data.data;
    },
    function() {
        layer.alert('查询出错！'  );
    });
    return value;
}

/**
 * 根据ID 异步 查询 部门name
 * 
 */
function getValueDept(id, param) {
    if (param == null || param == "") {
        return "";
    }
    var value = "";
    var urldept = contextPath + "/YZDeptAct/getDeptNameBySeqId.act?seqId=" + param;
    $.axseY(urldept, null,
    function(data) {
        value = data.data;
        if ($("#" + id).length <= 0) {
            //dom不存在
            setTimeout(function() {
                $("#" + id).html(value);
            },
            500);
        } else {
            $("#" + id).html(value);
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

/**
 * 根据ID 同步 查询 部门name
 * 
 */
function getValueDeptTB(param) {
    if (param == null || param == "") {
        return "";
    }
    var value = "";
    var urldept = contextPath + "/YZDeptAct/getDeptNameBySeqId.act?seqId=" + param;
    $.axse(urldept, null,
    function(data) {
        value = data.data;
    },
    function() {
        layer.alert('查询出错！'  );
    });
    return value;
}


/**
 * 这个方法需要注意的是 dept_parent = 0
 * 目前的目录结构是 单位下一级 ，放一个 一级菜单， 比如好大夫口腔 
 * 获取 门诊名称
 * @param conn
 * @param deptcode
 * @return
 * @throws Exception
 */
function getValueOrg(id, deptcode) {
    var value = "";
    var urldept = contextPath + "/YZDeptAct/getTopDeptName.act?deptcode=" + deptcode;
    $.axseY(urldept, null,
    function(data) {
        value = data.data;
        if ($("#" + id).length <= 0) {
            //dom不存在
            setTimeout(function() {
                $("#" + id).html(value);
            },
            500);
        } else {
            $("#" + id).html(value);
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}