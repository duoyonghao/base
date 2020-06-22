/**
 * 获取角色对应的权限按钮
 */
function getButtonListByPriv(){
	var buttonlist = null;
	var url = 'YZButtonAct/getButtonListByPriv.act';
	var data = getDataFromServer(url);
	if(data){
		buttonlist = data.retData;
	}
	return buttonlist;
}

//传入页面需要获取的权限按钮数组,及对应点击触发的方法名
function getButtonPowerByPriv(qxnameArr, func, menuid) {
    //权限按钮为空 直接return；
    if (qxnameArr == null || qxnameArr == "" || qxnameArr == "undefined") {
        return false;
    }
    if(!menuid){
    	menuid="";
    }
    //根据用户角色获取按钮权限
    var pageurl = contextPath + '/YZButtonAct/getButtonListByPriv.act?menuid=' + menuid;
    $.axse(pageurl, null,
    function(r) {
        if (r.retState == "0") {
            var listbutton = r.retData;
            var menubutton1 = "";
            //遍历按钮
            for (var j = 0; j < qxnameArr.length; j++) {
                //验证是存在权限
                for (var i = 0; i < listbutton.length; i++) {
                    if (listbutton[i].qxName == qxnameArr[j]) {
                        //判断方法数组是否为空
                        if (menubutton1.indexOf(listbutton[i].qxName) > 0) {
                            continue;
                        }
                        if (func != null && qxnameArr != "" && qxnameArr != "undefined" && qxnameArr.length > 0) {
                            menubutton1 += '<a href="javascript:void(0);" id="' + qxnameArr[j] + '" class="kqdsCommonBtn" onclick="' + func[j] + '();">' + listbutton[i].bz + '</a>';
                        } else {
                            menubutton1 += '<a href="javascript:void(0);" id="' + qxnameArr[j] + '" class="kqdsCommonBtn" >' + listbutton[i].bz + '</a>';
                        }

                    }
                }
            }
            
            $("#query").before(menubutton1);
        } else {
            layer.alert('获取按钮失败'  );
        }
    },
    function() {
        layer.alert('获取按钮失败' );
    });
}