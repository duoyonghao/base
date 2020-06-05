/* #####################################数据绑定 start  // yangsen 17-5-4 ########################################*/
/**
 * 包括给 span 这类标签赋值
 * @param jsonStr
 */
function loadData(jsonStr) {
	//console.log(jsonStr);
    var obj = jsonStr;
    var key, value, tagName, type, arr, styles;
    for (x in obj) {
        key = x;
        value = obj[x];
        $("[name='" + key + "'],[name='" + key + "[]']").each(function() {
            tagName = $(this)[0].tagName;
            styles = $(this).attr('class');
            type = $(this).attr('type');
            if (tagName == 'INPUT') {
                if (type == 'radio') {
                    $(this).prop('checked', $(this).val() == value);
                } else if (type == 'checkbox') {
                    if (value) {
                        arr = value.toString().split(',');
                        for (var i = 0; i < arr.length; i++) {
                            if ($(this).val() == arr[i]) {
                                $(this).attr('checked', true);
                                break;
                            }
                        }
                    }
                } else if (0) {
                    // 判断是否是选人自动填充
                } else {
                    $(this).val(value);
                }
            } else if (tagName == 'SELECT' || tagName == 'TEXTAREA') {
                if (new RegExp("select2").test(styles)) {
                    $(this).val(value).trigger("change");
                } else {
                    $(this).val(value);
                }
            }
        });
        // 2017-9-6 增加 html的赋值  yangsen
        if($("#" + key).attr("id")){
        	var tagName = $("#" + key)[0].tagName;
        	if(tagName == 'SPAN' || tagName == 'TD' || tagName == 'DIV' || tagName == 'I' ){
        		$("#" + key).html(value);
        	}
        }
    }
}

/* #####################################数据绑定 end  // yangsen 17-5-4 ########################################*/
