/**
 * 改写jquery的序列化方法，使checkbox在不选中的情况下也可以传值
 */
$.fn.kqds_serialize = function() {
    var a = this.serializeArray();
    var $radio = $('input[type=radio],input[type=checkbox]', this);
    var temp = {};
    $.each($radio,
    function() {
        if (!temp.hasOwnProperty(this.name)) {
            if ($("input[name='" + this.name + "']:checked").length == 0) {
                temp[this.name] = this.value;
                a.push({
                    name: this.name,
                    value: this.value
                });
            }
        }
    });
    return jQuery.param(a);
};

/**
 * 用于部门管理
 * 和getSelectDeptTree的区别是，这里不是请选择
 * @param id
 */
function getSelectDeptTree4Manage(id) {
    var dict = $("#" + id);
    var url = contextPath + "/YZDeptAct/getSelectDeptTree.act";
    $.axse(url, null,
    function(data) {
        var list = data.retData;
        if (list != null && list.length > 0) {
            dict.empty();
            dict.append("<option value='0'>无</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                dict.append("<option value='" + optionStr.value + "'>" + optionStr.text + "</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}


/**
 * @param 以逗号分隔的转换为数组
 */
function str2Array(ids) {
    var arr = new Array();
    var tmp = ids.split(',');
    for (var i = 0; i < tmp.length; i++) {
        if (tmp[i]) {
            arr.push(tmp[i]);
        }
    }
    return arr;
}

/**
 * 给selectpicker赋默认值
 * @param selectId
 * @param vals
 */
function kqds_setMultiSelectVal(selectId, vals) {
    $("#" + selectId).selectpicker('val', str2Array(vals));
}

/**
 * 下拉框菜单树
 * @param id
 */
function getSelectMenuTree4Manage(id) {
    var dict = $("#" + id);
    var url = contextPath + "/YZMenuAct/getSelectMenuTree.act";
    $.axse(url, null,
    function(data) {
        var list = data.retData;
        if (list != null && list.length > 0) {
            dict.empty();
            // dict.append("<option value='0'>无</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                dict.append("<option value='" + optionStr.value + "'>" + optionStr.text + "</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}