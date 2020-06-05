//查询加工厂
function getTypeList(name, jgc) {
    var dict = $("#" + name);
    var url = contextPath + '/KQDS_OutProcessing_TypeBackAct/getTypeList.act?jgc=' + jgc;
    $.axse(url, null,
    function(data) {
        var list = data.data;
        if (list != null) {
            if (list.length > 0) {
                dict.empty();
                dict.append("<option value=''>请选择</option>");
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    dict.append("<option value='" + optionStr.typeno + "'>" + optionStr.typename + "</option>");
                }
            } else {
                dict.empty();
                dict.append("<option value=''>-- 暂无项目分类 --</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
}