/**
 * status  0 直接打印病历   1暂存  2提交
 */
function submitInfo() {
    var conditionImplantsVal = getValue('conditionImplants_tmp');
    $("#conditionImplants").val(conditionImplantsVal);

    var param = $('#form1').serialize();
    var url = contextPath + '/KQDS_BLK_ChaiXianAct/insertOrUpdate.act?' + param;
    save(url);
}

//取病历内容
function getblcontent4HuiFu() {
    var content = "";
    var detailurl = contextPath + '/KQDS_BLKAct/selectDetailContent.act?meid=' + static_seqId + '&mtype=' + static_mtype;
    $.axse(detailurl, null,
    function(data) {
        var davalul = data.data;

        loadData(davalul);

        static_subSeqId = davalul.seqId; // 病历内容主键，有可能是初诊，也有可能是复诊
        $("#subSeqId").val(static_subSeqId); // 子表主键
        /** 牙列情况dentition 和 种植体植入情况details of impants 特殊处理 **/
        dealMultiLines('conditionImplants', davalul.conditionImplants, '<%=detailFlag%>');

        // 恢复病历时，自动赋值
        if (data.blname) {
            $("#blname").val(data.blname);
        }

        // 恢复病历时，自动赋值  end ...
    },
    function() {
        layer.alert('查询出错！'  );
    });
}