//获取当前页面所有按钮--非bootstraptable 调用
function getButtonAllCurPage(menuId) {
    var url = contextPath + '/YZSystemAct/getButtonList.act?menuId=' + menuId;
    $.axseY(url, null,
    function(data) {
        if (data.retState == "0") {
            listbutton = data.retData;
            // 加载该页面的所有可操作按钮，每个页面一共有多少按钮是相对固定的，在此基础上，通过获取当前登录用户的权限按钮，来进行界面展示
            getButtonPower();
        } else {
            layer.alert('查询出错！'  );
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}
//获取当前页面所有按钮--bootstraptable 调用
function getButtonAll(menuId) {
    var url = contextPath + '/YZSystemAct/getButtonList.act?menuId=' + menuId;
    $.axseY(url, null,
    function(data) {
        if (data.retState == "0") {
            listbutton = data.retData;
            // 加载该页面的所有可操作按钮，每个页面一共有多少按钮是相对固定的，在此基础上，通过获取当前登录用户的权限按钮，来进行界面展示
        } else {
            layer.alert('查询出错！'  );
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}