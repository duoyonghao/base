function checkUserName(inputStr) {
    var tmpValue = inputStr;
    //以下搜索字符串中的特殊字符，如果存在，则替换成""
    if (tmpValue.indexOf('|') > -1) {
        return false;
    }
    if (tmpValue.indexOf('&') > -1) {
        return false;
    }
    if (tmpValue.indexOf(';') > -1) {
        return false;
    }
    if (tmpValue.indexOf('$') > -1) {
        return false;
    }
    if (tmpValue.indexOf('%') > -1) {
        return false;
    }
    if (tmpValue.indexOf('@') > -1) {
        return false;
    }
    if (tmpValue.indexOf("'") > -1) {
        return false;
    }
    if (tmpValue.indexOf('"') > -1) {
        return false;
    }
    if (tmpValue.indexOf('(') > -1) {
        return false;
    }
    if (tmpValue.indexOf(')') > -1) {
        return false;
    }
    if (tmpValue.indexOf('+') > -1) {
        return false;
    }
    if (tmpValue.indexOf('<') > -1) {
        return false;
    }
    if (tmpValue.indexOf('>') > -1) {
        return false;
    }
    if (tmpValue.indexOf('--') > -1) {
        return false;
    }
    if (tmpValue.indexOf(",") > -1) {
        return false;
    }
    if (tmpValue.indexOf("?") > -1) {
        return false;
    }
    if (tmpValue.indexOf("=") > -1) {
        return false;
    }
    if (tmpValue.indexOf("\\") > -1) {
        return false;
    }
    if (tmpValue.indexOf("\n") > -1) {
        return false;
    }
    if (tmpValue.indexOf("\r") > -1) {
        return false;
    }
    return true;
}
function doLogin(type) {
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    if (userAgent.indexOf("Chrome") > -1) {
        var regStr_chrome = /chrome\/[\d.]+/gi;
        // 获取浏览器名字+版本字符串
        var browserInfo = userAgent.match(regStr_chrome);
        var chromeVersion = (browserInfo + "").replace(/[^0-9.]/ig, "");
        var versionNo = chromeVersion.split(".")[0];
        /* if (versionNo < 50) {
            alert("系统内核版本太低，请联系管理员");
            return;
        } */
    } else {
       // alert("请使用chrome浏览器登录本系统");
       // return;
    }

    if ($('#userName') == '') {
        alert("请输入用户名");
        $('#userName').focus();
        return;
    }
    if (!checkUserName($('#userName').val())) {
        alert("输入的用户名含有特殊字符！请修改");
        $('#userName').focus();
        return;
    }
    loginNamePass(type);
}

/**
 * 用户名密码登录
 */
function loginNamePass(type) {
    //验证系统当前人数是否达到上限值
    var urls = "YZPersonAct/getMaxuser.act";
    var rtJsons = getDataFromServer(urls);
    if (rtJsons.retState == "0") {
        if (rtJsons.data == "1") {
            alert("登录人数已达到上限！");
            return false;
        }
    } else {
        alert(rtJson.rtMsrg);
        return false;
    }
    var pars = $('#loginForm').serialize();
    var url = "YZSystemAct/doLoginIn.act";
    try {
        var json = getDataFromServer(url, pars);
        loginComplete(json, type);
    } catch(e) {
        alert('服务器连接中断');
    }
}
/**
 * 用户名密码登录--同一账号只能同时登录一次
 */
function loginNamePassRepeatlogin(type) {
    // 验证系统当前人数是否达到上限值
    var urls = "YZPersonAct/getMaxuser.act";
    var rtJsons = getDataFromServer(urls);
    if (rtJsons.retState == "0") {
        if (rtJsons.data == "1") {
            alert("登录人数已达到上限！");
            return false;
        }
    } else {
        alert(rtJson.rtMsrg);
        return false;
    }

    // 传参repeatlogin = 1 ，作用是当用户在其他地方登录时，强制挤掉该用户，然后进行登录
    var pars = $('#loginForm').serialize();
    var url = "YZSystemAct/doLoginIn.act?repeatlogin=1";

    try {
        var json = getDataFromServer(url, pars);
        loginComplete(json, type);
    } catch(e) {
        alert('服务器连接中断');
    }
}
function loginComplete(json, type) {
    if (json.code == "0") {
        // 记录上次成功登录的用户名
        setCookie('userName', $('#userName').val());

        // 缓存数据
        $.axseY(contextPath + "/YZCacheAct/loginCache.act", null,
        function(data) {},
        function() {});

        if (type == 1) {
            window.location.href = contextPath + '/YZSystemAct/login_index.act';
        } else {
            window.location.href = contextPath + '/YZSystemAct/loginIndexFront.act';
        }
    } else {
        switch (json.code) { // YZLoginErrorConst.java中有详细描述
        case 15:
            {
                alert(json.msg);
                $('#loginForm').reset();
                $('#userName').focus();
                break;
            }
        case 1:
            {
                alert("账号已被禁止登录，请您联系管理员！");
                break;
            }
        case 9:
            {
                var r = confirm("您的账号已在其他机器登录，确定登录?");
                if (r == true) {
                    loginNamePassRepeatlogin(type);
                }
                break;
            }
        case 12:
            {
                alert(json.msg);
                $('#pwd').val('');
                $('#userName').focus();
                break;
            }
        case 4:
            {
                alert(json.msg);
                break;
            }
        case 5:
            { // 密码错误
                alert(json.msg);
                $('#pwd').val('');
                $('#pwd').focus();
                break;
            }

        case 20:
            { // 已经离职
                alert("该用户已离职！");
                $('#pwd').val('');
                $('#pwd').focus();
                break;
            }
        case 14:
            {
                alert('验证码错误');
                $('#pwd').val('');
                $('#pwd').focus();
                break;
            }
        case 25:
        {
            alert('超过最大端口数');
            $('#pwd').val('');
            $('#pwd').focus();
            break;
        }
        default:
            {
                alert("登录失败!");
            }
        }
    }
}

var KEY_ENTER = 0X000D; // 回车键
/**
 * 处理键盘按键press事件
 */
function documentKeypress(e) {
    var id = document.activeElement.id;
    if (id != 'userName' && id != 'pwd') {
        return;
    }

    var currKey = 0;
    var e = e || event;
    currKey = e.keyCode || e.which || e.charCode;

    if (currKey == KEY_ENTER) {
        doLogin();
    }
}

document.onkeypress = documentKeypress;

function doInit() {
    if (getCookie('userName')) {
        $('#userName').val(getCookie('userName'));
        $('#pwd').focus();
    } else {
        $('#userName').focus();
    }

    if ("1" == is_open_chain_func) {
        initHosSelectList4Login("organization");
    }
}

/**
 * 设置cookie
 */
function setCookie(name, value) {
    var Days = 30;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";path=/;expires=" + exp.toGMTString();
}

/**
 * 读取cookie
 */
function getCookie(name) {
    var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
    if (arr != null) {
        return unescape(arr[2]);
    } else {
        return null;
    }
    return null;
}

