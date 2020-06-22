//选择患者
function getuser(operFlag) {

    var requrl = contextPath + '/KQDS_UserDocumentAct/toUserList.act?1=1';

    if (operFlag) {
        requrl += '&operFlag=' + operFlag;
    }

    layer.open({
        type: 2,
        title: '选择患者',
        shadeClose: true,
        shade: 0.6,
        area: ['580px', '80%'],
        content: requrl,
        //iframe的url
        end: function(index, layero) {
            if (usercodechild != "" && usernamechild != "") {
                $("#introducer").val(usercodechild);
                $("#introducerDesc").val(usernamechild);
            }
        },
    });
}

/**
 * 跳转到患者中心界面
 * @param usercode
 */
function goToUserCenterPage(usercode) {
	var userAgent = navigator.userAgent; 
    parent.layer.open({
        type: 2,
        title: '患者档案中心',
        shadeClose: true,
        shade: 0.6,
        area: userAgent.indexOf("iPad") > -1 ? ['80%', '75%'] : ['45%', '65%'],
        content: contextPath + '/KQDS_UserDocumentAct/toUserCenter.act?usercode=' + usercode
    });
}
