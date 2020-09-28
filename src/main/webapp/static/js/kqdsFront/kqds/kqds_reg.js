function getRegObjBySeqId(seqId) {
    var obj = null;
    var detailurl = contextPath + '/KQDS_REGAct/selectDetail.act?seqId=' + seqId;
    $.axse(detailurl, null,
    function(r) {
        if (r.retState == "0") {
            obj = r.data;
        } else {
            layer.alert('根据主键查询挂号单信息出错！'  );
        }
    },
    function() {
        layer.alert('根据主键查询挂号单信息出错！' );
    });

    return obj;
}
//查询挂号信息加患者的客服
function getRegObjAndUserKefuBySeqId(seqId) {
    var obj = null;
    var detailurl = contextPath + '/KQDS_REGAct/selectUserdocumentByReg.act?seqId=' + seqId;
    $.axse(detailurl, null,
        function(r) {
           if (r.retState == "0") {
                obj = r.data;
            } else {
                layer.alert('根据主键查询挂号单信息出错！'  );
            }
        },
        function() {
            layer.alert('根据主键查询挂号单信息出错！' );
        });

    return obj;
}
/**
 * 根据患者编号获取最近一次挂号信息
 * @param usercode
 * @returns
 */
function getLastestRegInfo(usercode) {
    var serverData = getDataFromServer("KQDS_REGAct/getLastestRegInfo.act?usercode=" + usercode);
    if (serverData) {
        return serverData.data;
    } else {
        return null;
    }
}

/**
 * 获取该患者24小时内的初诊挂号记录
 * @param usercode
 * @returns
 */
function getChuZhenIn24Hours(usercode) {
    var serverData = getDataFromServer("KQDS_REGAct/getChuZhenIn24Hours.act?usercode=" + usercode);
    if (serverData) {
        return serverData.data;
    } else {
        return null;
    }
}

/**
 * 1、挂号操作：
 * 在“门诊预约”进行挂号的，
 * A：直接预约到某个医生的，在挂号界面：医生，默认为预约医生，可改；
 * B：没有预约到医生，医生，默认为最近一次挂号的接诊医生，可改；
 *    再修改一下：如果有开单医生，取值开单医生；没有开单医生，取值挂号医生；都没有的话，不赋值；
 *    根据患者编号获取最近一次开单信息
 * @param usercode
 * @returns
 */
function getLastestCostOrderInfo(usercode) {
    var serverData = getDataFromServer("KQDS_REGAct/getLastestCostOrderInfo.act?usercode=" + usercode);
    if (serverData) {
        return serverData.data;
    } else {
        return null;
    }
}
