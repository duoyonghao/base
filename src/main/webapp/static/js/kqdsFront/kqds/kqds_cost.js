function getCostOrderObjBySeqId(seqId) {
    var obj = null;
    var detailurl = contextPath + '/KQDS_CostOrderAct/selectDetail.act?seqId=' + seqId;
    $.axse(detailurl, null,
    function(r) {
        if (r.retState == "0") {
            obj = r.data;
        } else {
            layer.alert('根据主键查询查询费用单信息出错！'  );
        }
    },
    function() {
        layer.alert('根据主键查询查询费用单信息出错！' );
    });

    return obj;
}