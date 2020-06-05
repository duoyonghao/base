/**
 * 获取文本模板列表
 * @param selectId
 * @returns
 */
function getTextTmpSelect4WX(selectId,accountid) {
    var dict = $("#" + selectId);
    var url = contextPath + "/weixin/act/template/TextTemplateAct/getTextTemplateList.act?1=1";
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
                    dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.templatename + "</option>");
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

/**
 * 获取图文模板列表
 * @param selectId
 * @returns
 */
function getNewsTmpSelect4WX(selectId,accountid) {
    var dict = $("#" + selectId);
    var url = contextPath + "/weixin/act/template/NewsTemplateAct/getNewsTemplateList.act?1=1";
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
                    dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.tempatename + "</option>");
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

/**
 * 获取扩展模板列表
 * @param selectId
 * @returns
 */
function getExpandTmpSelect4WX(selectId,accountid) {
    var dict = $("#" + selectId);
    var url = contextPath + "/weixin/act/template/ExpandTemplateAct/getExpandTemplateList.act?1=1";
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


