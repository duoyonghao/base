/**
 * 全局变量
 */
var wxBingLiObj = new Object();
wxBingLiObj.selectPage = 'WXBingLiAct/selectPage.act?1=1';
/**  **/
wxBingLiObj.selectDetail = 'WXBingLiAct/selectDetail.act?1=1';
/**  **/

//获取增加行这种字段的内容
function getcontent(value, name) {
    var blcontent = "";
    if (value == "") {
        blcontent += '  <div class="weui-form-preview__item">';
        blcontent += '  		<label class="weui-form-preview__label">' + name + '</label>';
        blcontent += '  </div>';
    } else {
        //1;2;4;3||撒东窗事发大概|||1;2;2;4||打折的v风格|||
        var arr = value.split("|||");
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].length > 0) {
                var arrone = arr[i].split("||");
                var ywarr = arrone[0].split(";");

                if (ywarr[0] == undefined) {
                    ywarr[0] = '';
                }
                if (ywarr[1] == undefined) {
                    ywarr[1] = '';
                }
                if (ywarr[2] == undefined) {
                    ywarr[2] = '';
                }
                if (ywarr[3] == undefined) {
                    ywarr[3] = '';
                }

                var content = arrone[1];
                if (content == undefined) {
                    content = '';
                }

                if (i > 0) {
                    name = '&nbsp;&nbsp;';
                }
                
                var html0 = $.trim(ywarr[0]);
                var html1 = $.trim(ywarr[1]);
                var html2 = $.trim(ywarr[2]);
                var html3 = $.trim(ywarr[3]);
                
                var yaweiHtml = html0 + '、' + html1 + '、' + html2 + '、' + html3;
                if('、、、' == yaweiHtml){
                	yaweiHtml = '';
                }
                
/*              if (content.replace(/\s/g, "") != '') {*/
                    blcontent += '  <div class="weui-form-preview__item">';
                    blcontent += '  		<label class="weui-form-preview__label">' + name + '</label>';
                    blcontent += '  		<span class="weui-form-preview__value">' + yaweiHtml + '</br>' + content + '</span>';
                    blcontent += '  </div>';
/*              } else {
                    blcontent += '  <div class="weui-form-preview__item">';
                    blcontent += '  		<label class="weui-form-preview__label">' + name + '</label>';
                    blcontent += '  </div>';
                }*/
            }
        }
    }
    return blcontent;
}