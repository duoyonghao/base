/**
 * 获取角色下拉框
 * @param id
 */
function getSlectUserPriv(id) {
    var dict = $("#" + id);
    var url = contextPath + "/YZPrivAct/getSlectUserPriv.act";
    $.axse(url, null,
    function(data) {
        var list = data.retData;
        if (list != null && list.length > 0) {
            dict.empty();
            dict.append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.privName + "</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

// 角色下拉框 不加门诊编号过滤条件
function getSlectUserPrivNoOrg(id) {
    var dict = $("#" + id);
    var url = contextPath + "/YZPrivAct/getSlectUserPrivNoOrg.act";
    $.axse(url, null,
    function(data) {
        var list = data.retData;
        if (list != null && list.length > 0) {
            dict.empty();
            dict.append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.privName + "</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}