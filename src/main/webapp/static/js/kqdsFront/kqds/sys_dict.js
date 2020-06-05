
/**
 * 根据ID 查询 字典表name
 * 
 */
function getValueDict(id, param) {
    var value = "";
    var urldict = contextPath + "/YZDictAct/getDictNameById.act?dicId=" + param;
    $.axseY(urldict, null,
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
 * 查询字典详情
 * @param seqId
 * @returns {Object}
 */
function getDictByDictSeqId(seqId) {
    var result = "";
    var url = contextPath + "/YZDictAct/selectDetail.act?seqId=" + seqId;
    $.axse(url, null,
    function(data) {
        if (data.retState == "0") {
            result = data.retData;
        } else {
            result = "记录不存在";
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
    return result;
}

/** 
 * 根据ID 查询 字典表name--同步  页面赋值
 * 
 */
function getValueDictTB(param) {
    var value = "";
    var urldict = contextPath + "/YZDictAct/getDictNameById.act?dicId=" + param;
    $.axse(urldict, null,
    function(data) {
        value = data.data;
    },
    function() {
        layer.alert('查询出错！'  );
    });
    return value;
}
/** 
 * 根据parentCode 查询 最大排序号
 * 
 */
function getDictMaxOrderno(parentCode) {
    var value = "";
    var urldict = contextPath + "/YZDictAct/getMaxOrderno.act?parentCode=" + parentCode;
    $.axse(urldict, null,
    function(data) {
        value = data.data;
    },
    function() {
        layer.alert('查询出错！'  );
    });
    return value;
}
/**
 * 查询字典子分类列表【如患者来源子分类】
 * @param parentSelectId
 * @param selectId
 * @param isAdd  如果isAdd有值，说明是新增页面； 目前后台的数据字典有启用、禁用功能，新增页面，查询的列表不包含被禁用的信息
 */
function getSubDictSelect(parentSelectId, selectId, isAdd) {
    var parentSeqId = $("#" + parentSelectId).val();
    var dict = $("#" + selectId);
    if (parentSeqId == null || parentSeqId == '') { // 如果切换门诊，导致重新初始化一级目录
        dict.empty();
        dict.append("<option value=''>请选择</option>");
        return false; // 终止执行
    }
    var url = contextPath + "/YZDictAct/getSubListByParentSeqId.act?parentSeqId=" + parentSeqId;
    if (isAdd) {
        url += '&isAdd=1';
    }
    var organization = $("#organization").val(); // ### 容错处理
    if (organization) {
        url += '&organization=' + organization;
    }
    dict.empty();
    dict.append("<option value=''>请选择</option>");
    $.axse(url, null,
    function(data) {
        if (data.retState == "0") {
            var list = data.list;
            if (list != null && list.length > 0) {
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                }
            }
        } else {
            layer.alert('查询出错！'  );
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

/**
 * 查询字典子分类列表【如患者来源子分类】 搜索select框（+刷新） 2019.10.11--licc 
 * @param parentSelectId
 * @param selectId
 * @param isAdd  如果isAdd有值，说明是新增页面； 目前后台的数据字典有启用、禁用功能，新增页面，查询的列表不包含被禁用的信息
 */
function getSubDictSelectSearch(parentSelectId, selectId, isAdd) {
    var parentSeqId = $("#" + parentSelectId).val();
    var dict = $("#" + selectId);
    if (parentSeqId == null || parentSeqId == '') { // 如果切换门诊，导致重新初始化一级目录
        dict.empty();
        $('#'+selectId).selectpicker('refresh');
        dict.append("<option value=''>请选择</option>");
        return false; // 终止执行
    }
    var url = contextPath + "/YZDictAct/getSubListByParentSeqId.act?parentSeqId=" + parentSeqId;
    if (isAdd) {
        url += '&isAdd=1';
    }
    var organization = $("#organization").val(); // ### 容错处理
    if (organization) {
        url += '&organization=' + organization;
    }
    dict.empty();
    $('#'+selectId).selectpicker('refresh');
    dict.append("<option value=''>请选择</option>");
    $.axse(url, null,
    function(data) {
        if (data.retState == "0") {
            var list = data.list;
            if (list != null && list.length > 0) {
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                }
                $('#'+selectId).selectpicker("refresh");//患者来源子分类下拉框添加搜索功能 
            }
        } else {
            layer.alert('查询出错！'  );
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}



/**
 * 
 * @param parentSelectId
 * @param selectId
 * @param isAdd
 * @returns {Boolean}
 */
function getSubDictSelectByParentCode(parentSeqId, selectId, isAdd) {
    var dict = $("#" + selectId);
    if (parentSeqId == null || parentSeqId == '') { // 如果切换门诊，导致重新初始化一级目录
        dict.empty();
        dict.append("<option value=''>请选择</option>");
        return false; // 终止执行
    }
    var url = contextPath + "/YZDictAct/getSubListByParentSeqId.act?parentSeqId=" + parentSeqId;
    if (isAdd) {
        url += '&isAdd=1';
    }
    var organization = $("#organization").val(); // ### 容错处理
    if (organization) {
        url += '&organization=' + organization;
    }
    $.axse(url, null,
    function(data) {
        if (data.retState == "0") {
            var list = data.list;
            if (list != null && list.length > 0) {
                dict.empty();
                dict.append("<option value=''>请选择</option>");
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                }
            }
        } else {
            layer.alert('查询出错！'  );
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

/**
 * 不做门诊条件过滤
 * @param parentSeqId
 * @param selectId
 * @param isAdd
 * @returns {Boolean}
 */
function getSubDictSelectByParentCodeNoOrg(parentSeqId, selectId, isAdd) {
    var dict = $("#" + selectId);
    if (parentSeqId == null || parentSeqId == '') { // 如果切换门诊，导致重新初始化一级目录
        dict.empty();
        dict.append("<option value=''>请选择</option>");
        return false; // 终止执行
    }
    var url = contextPath + "/YZDictAct/getSubListByParentSeqIdNoOrg.act?parentSeqId=" + parentSeqId;
    if (isAdd) {
        url += '&isAdd=1';
    }
    $.axse(url, null,
    function(data) {
        if (data.retState == "0") {
            var list = data.list;
            if (list != null && list.length > 0) {
                dict.empty();
                dict.append("<option value=''>请选择</option>");
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                }
            }
        } else {
            layer.alert('查询出错！'  );
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

/**
 * 查询字典详情
 * @param seqId
 * @returns {String}
 */
function getDictNameByDictSeqId(seqId) {
    var result = "";
    var url = contextPath + "/YZDictAct/selectDetail.act?seqId=" + seqId;
    $.axse(url, null,
    function(data) {
        if (data.retState == "0") {
            result = data.retData.dictName;
        } else {
            result = "记录不存在";
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
    return result;
}
function bindDictNameByCodesTB(id, param) {
    if (param == null || param == "") {
        return "";
    }
    var detailurl = contextPath + "/YZDictAct/getDictNamesByDictCodes.act?dictCodes=" + param;
    $.axse(detailurl, null,
    function(data) {
        $("#" + id).val(data.data);
    },
    function() {
        layer.alert('查询出错！'  );
    });
}



