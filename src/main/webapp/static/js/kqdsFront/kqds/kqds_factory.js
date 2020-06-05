
// 根据code查询加工厂名称
function getValueFactoryTB(id) {
    var value = "";
    var url = contextPath + "/KQDS_OutProcessing_UnitAct/selectDetailByCode.act?code=" + id;
    $.axse(url, null,
    function(data) {
        value = data.data.name;
    },
    function() {
        layer.alert('查询出错！'  );
    });
    return value;
}

//根据code查询加工厂名称
function getValueFactory(id, param) {
    var value = "";
    var url = contextPath + "/KQDS_OutProcessing_UnitAct/selectDetailByCode.act?code=" + param;
    $.axseY(url, null,
    function(data) {
        value = data.data.name;
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