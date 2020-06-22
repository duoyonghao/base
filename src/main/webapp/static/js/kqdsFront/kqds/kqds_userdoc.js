
function getBaseInfoByUserCode(usercode) {
    var baseInfo = null;
    var detailurl = contextPath + "/KQDS_UserDocumentAct/getBaseUserInfoByUsercode.act?usercode=" + usercode;
    $.axse(detailurl, null,
    function(data) {
    	baseInfo = data
    },
    function() {
        layer.alert('查询出错！'  );
    });
    return baseInfo;
}

function getOneByUsercode(usercode) {
    var value = "";
    var detailurl = contextPath + "/KQDS_UserDocumentAct/getOneByUsercode.act?usercode=" + usercode;
    $.axse(detailurl, null,
    function(data) {
        value = data;
    },
    function() {
        layer.alert('查询出错！'  );
    });
    return value;
}


/**
 * 根据usercode 查询患者对象
 * ajax同步请求
 */
function getHzNameByusercodeTB(usercode) {
    var value = "";
    var detailurl = contextPath + "/KQDS_UserDocumentAct/getOneByUsercode.act?usercode=" + usercode;
    $.axse(detailurl, null,
    function(data) {
        value = data.data[0];
    },
    function() {
        layer.alert('查询出错！'  );
    });
    return value;
}

/**
 * 根据usercode 查询患者对象
 * ajax同步请求
 */
/*****  注释废弃代码！！！
function getNameByusercode(cellId, usercode, attr) {
    var value = "";
    var detailurl = contextPath + "/KQDS_UserDocumentAct/getOneByUsercode.act?usercode=" + usercode;
    $.axseY(detailurl, null,
    function(data) {
        value = data.data[0];
        if (value != "" && typeof(value) != "undefined") {
            if ($("#" + cellId).length <= 0) {
                //dom不存在
                setTimeout(function() {
                    if (attr == "username") {
                        $("#" + cellId).html(value.username);
                    } else if (attr == "phonenumber1") {
                        $("#" + cellId).html(value.phonenumber1);
                    }
                },
                500);
            } else {
                if (attr == "username") {
                    $("#" + cellId).html(value.username);
                } else if (attr == "phonenumber1") {
                    $("#" + cellId).html(value.phonenumber1);
                }
            }
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}*/

// 根据usercodes 查询患者列表
function getNameByusercodesTB(usercodes) {
    var value = "";
    var detailurl = contextPath + "/KQDS_UserDocumentAct/getOneByUsercodes.act?usercodes=" + usercodes;
    $.axse(detailurl, null,
    function(data) {
        value = data.data;
    },
    function() {
        layer.alert('查询出错！'  );
    });
    return value;
}
//根据姓名、手机号查询usercode
function getUsercodeByPhoneAndNameTB(username, phone1, phone2) {
    var value = "";
    var detailurl = contextPath + "/KQDS_UserDocumentAct/getUsercodeByPhoneAndName.act?username=" + username + "&phonenumber1=" + phone1 + "&phonenumber2=" + phone2;
    $.axse(detailurl, null,
    function(data) {
        value = data.data;
    },
    function() {
        layer.alert('查询出错！'  );
    });
    return value;
}
//根据usercodes 查询患者列表
function getNameByusercodes(cellId, usercodes) {
    var value = "";
    var detailurl = contextPath + "/KQDS_UserDocumentAct/getOneByUsercodes.act?usercodes=" + usercodes;
    $.axseY(detailurl, null,
    function(data) {
        value = data.data;
        if ($("#" + cellId).length <= 0) {
            //dom不存在
            setTimeout(function() {
                $("#" + cellId).html(value);
            },
            500);
        } else {
            $("#" + cellId).html(value);
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}