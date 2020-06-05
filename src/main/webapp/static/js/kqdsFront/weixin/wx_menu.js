/**
 * 获取微信主菜单下拉框
 * @param selectId
 * @returns
 */
function getLevel1MenuSelect4WX(selectId,accountid) {
    var dict = $("#" + selectId);
    var url = contextPath + "/weixin/act/menu/WXMenuAct/getLevel1MenuSelect4WX.act?1=1";
    if(accountid){
    	url += '&accountid=' + accountid;
    }
    $.axse(url, null,
    function(data) {
        if (data.retState == "0") {
        	dict.empty();
            var list = data.list;
            if (list != null && list.length > 0) {
                dict.append("<option value=''>请选择</option>");
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.name + "</option>");
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

function menuTypeChangeFunc(obj){
	var selectVal = $(obj).val();
	if(selectVal == 'click'){
		$(".clickTr").show();
		$(".viewTr").hide();
	}
	
	if(selectVal == 'view'){
		$(".clickTr").hide();
		$(".viewTr").show();
	}
}
