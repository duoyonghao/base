/**
 * status  0 直接打印病历   1暂存  2提交
 */
function submitInfo(status) {
    var conditionImplantsVal = getValue('conditionImplants_tmp');
    $("#conditionImplants").val(conditionImplantsVal);
    $("#status").val(status);

    var url = contextPath + '/KQDS_MedicalRecord_ChaiXianAct/insertOrUpdate.act?';

    if (status == '2') {
        //提交病历时  提示是否已签署知情同意书
        layer.confirm('是否已签署知情同意书？', {
            btn: ['是', '否', '取消'] //按钮
        },
        function() {
            $("#isprint").val(1); // 签署同意书
            var param = $('#form1').serialize();
            url += param;
            save(url);
        },
        function() {
            $("#isprint").val(0); // 没签署同意书
            var param = $('#form1').serialize();
            url += param;
            save(url);
        });
    } else {
        $("#isprint").val(0); // 没签署同意书
        var param = $('#form1').serialize();
        url += param;
        save(url);
    }
}

/*******************************************************/
// 从病历库 点击新建病历
function getblcontent4copy(seqId_blk,mtype_blk) {
    if ('' != seqId_blk && '' != mtype_blk) {
        var content = "";
        var detailurl = contextPath + '/KQDS_BLKAct/selectDetailContent.act?meid='+seqId_blk+'&mtype=' + mtype_blk;
        $.axse(detailurl, null,
        function(data) {
            var davalul = data.data;
            delete davalul.seqId;
            delete davalul.organization;
            delete davalul.meid;
            delete davalul.usercode;

            loadData(davalul);
            dealMultiLines('conditionImplants', davalul.conditionImplants);
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }
}

//取病历内容
function getblcontent4HuiFu() {
    var content = "";
    var detailurl = contextPath + '/KQDS_MedicalRecordAct/selectDetailContent.act?meid=' + static_seqId + '&mtype=' + static_mtype;
    $.axse(detailurl, null,
    function(data) {
        var davalul = data.data;

        loadData(davalul);

        static_subSeqId = davalul.seqId; // 病历内容主键，有可能是初诊，也有可能是复诊
        // 恢复病历时，自动赋值
        if (data.doctor) {
            $("#doctor").val(data.doctor); // ### 青岛特定需求，给医生护士赋值
            bindPerUserNameBySeqIdTB("doctorDesc", data.doctor);
        }
        if (data.nurse) {
            $("#nurse").val(data.nurse);
            bindPerUserNameBySeqIdTB("nurseDesc", data.nurse);
        }

        if (data.assistant) {
            $("#assistant").val(data.assistant);
            bindPerUserNameBySeqIdTB("assistantDesc", data.assistant);
        }

        $("#subSeqId").val(static_subSeqId); // 子表主键
        /** 特殊处理 **/
        dealMultiLines('conditionImplants', davalul.conditionImplants, '<%=detailFlag%>');
        // 恢复病历时，自动赋值  end ...
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

