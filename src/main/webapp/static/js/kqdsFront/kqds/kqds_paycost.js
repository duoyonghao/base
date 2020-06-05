
function getPayCostBycostId(costno) {
    var result = "";
    var url = contextPath + "/Kqds_PayCostAct/selectDetailByCostNo.act?costno=" + costno;
    $.axse(url, null,
    function(data) {
        result = data.data;
    },
    function() {
        layer.alert('查询出错！' );
    });
    return result;
}
