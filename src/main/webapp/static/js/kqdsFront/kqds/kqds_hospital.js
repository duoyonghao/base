/**
 * 获取当天的门诊预约记录
 * @param usercode
 * @returns
 */
function getCurrDatetHosOrderInfo(usercode) {
    var serverData = getDataFromServer("KQDS_Hospital_OrderAct/getCurrDatetHosOrderInfo.act?usercode=" + usercode);
    if (serverData) {
        return serverData.data;
    } else {
        return null;
    }
}

/**
 * 根据主键获取门诊预约记录
 * @param usercode
 * @returns
 */
function getHosOrderInfoBySeqId(seqId) {
    var serverData = getDataFromServer("KQDS_Hospital_OrderAct/selectDetail.act?seqId=" + seqId);
    if (serverData) {
        return serverData.data;
    } else {
        return null;
    }
}
