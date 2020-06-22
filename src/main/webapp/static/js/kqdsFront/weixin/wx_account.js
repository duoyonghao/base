/**
 * 获取公众号列表
 * @param selectId
 * @returns
 */
function getAccountSelect4WX(selectId) {
    var dict = $("#" + selectId);
    var url = contextPath + "/weixin/act/account/WXAccountAct/getAccountList.act";
    $.axse(url, null,
    function(data) {
        if (data.retState == "0") {
            var list = data.list;
            if (list != null && list.length > 0) {
                dict.empty();
                // dict.append("<option value=''>请选择</option>");
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.accountname + "</option>");
                }
            }
        } else {
            layer.alert('查询出错！'  );
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}


