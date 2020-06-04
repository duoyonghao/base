/**
 * 查询就诊目的子分类
 * @param parentSelectId
 * @param selectId
 * @param isAdd 如果isAdd有值，说明是新增页面； 目前后台有启用、禁用功能，新增页面，查询的列表不包含被禁用的信息
 */
function getJzmdNextTypeSelect(parentSelectId, selectId, isAdd) {
    var jzmd = $("#" + parentSelectId).val();
    var dict = $("#" + selectId);
    if (jzmd == null || jzmd == '') { // 如果切换门诊，导致重新初始化一级目录
        dict.empty();
        dict.append("<option value=''>请选择</option>");
        return false; // 终止执行
    }

    var url = contextPath + "/KQDS_Jzmd_TypeAct/getJzmdChildTypeSelect.act?jzmd=" + jzmd;
    if (isAdd) {
        url += '&isAdd=1';
    }
    var organization = $("#organization").val(); // ### 容错处理
    if (organization) {
        url += '&organization=' + organization;
    }

    $.axse(url, null,
    function(data) {
        var list = data.list;
        if (list != null && list.length > 0) {
            dict.empty();
            dict.append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.jzmdchildname + "</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}