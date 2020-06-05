/**
 * 全局变量
 */
var wxOrderObj = new Object();
wxOrderObj.insertOrUpdate = 'WXOrderAct/insertOrUpdate.act?1=1';
/**  **/
wxOrderObj.cancel = 'WXOrderAct/cancel.act?1=1';
/**  **/
wxOrderObj.auditor = 'WXOrderAct/auditor.act?1=1';
/**  **/
wxOrderObj.deleteObj = 'WXOrderAct/deleteObj.act?1=1';
/**  **/
wxOrderObj.selectPage = 'WXOrderAct/selectPage.act?1=1';
/**  **/
wxOrderObj.selectDetail = 'WXOrderAct/selectDetail.act?1=1';
/**  **/
wxOrderObj.selectPageMz = 'KqdsOrderAct/selectPageMz.act?1=1';
/**  **/
wxOrderObj.selectDetailMz = 'KqdsOrderAct/selectDetailMz.act?1=1';
/**  **/
wxOrderObj.selectPageWd = 'KqdsOrderAct/selectPageWd.act?1=1';
/**  **/
wxOrderObj.selectDetailWd = 'KqdsOrderAct/selectDetailWd.act?1=1';
/**  **/

wxOrderObj.selectPage4Front = 'WXOrderAct/selectPage4Front.act?1=1';

/**
 * 获取预约状态
 */
function getOrderStatus(status) {
    if (0 == status) {
        return "<span style='color:green;'>待确认</span>";
    }
    if (1 == status) {
        return "<span style='color:blue;'>已确认</span>";
    }

    if (2 == status) {
        return "<span style='color:red;'>已拒绝</span>";
    }

    if (3 == status) {
        return "<span style='color:red;'>已取消</span>";
    }
}