/**
 * 全局变量
 */
var wxObj = new Object();

/**
 * 微信网页数据初始化
 * @returns
 */
$(function() {
    if (!localStorage) {
        alert('浏览器不支持html5特性，无法访问！');
        return false;
    }

    storgeWeChatInfo();
    /** 存储微信带过来的值 **/

    var openid = localStorage.getItem("openid");
    var subscribe = localStorage.getItem("subscribe");
    var appid = localStorage.getItem("appid");
    
    if (openid) {
        wxObj.openid = openid;
        // do nothing
        initbaseinfo(openid);
    } else {
        alert('参数错误，请确认通过微信客户端访问！');
        return false;
    }
    if (subscribe) {
        wxObj.subscribe = subscribe;
    }
    if (appid) {
        wxObj.appid = appid;
    }
});

function initbaseinfo(openid) {
    if ((window.location.href).indexOf('wechat/user/bind.html') > 0 || (window.location.href).indexOf('wechat/user/register.html') > 0) {
        return false;
    }
    // 初始化基础信息
    var respData = getDataFromServer(wxUserObj.getUserDocByOpenId + "&openid=" + wxObj.openid);
    if (!respData.retData) {
        if ((window.location.href).indexOf('wechat/userCenter.html') > 0) {
            // alert("您未绑定账号！");
            // var timestamp = new Date().getTime();
            // window.location.href = contextPath + "/wechat/user/bind.html?timestamp=" + timestamp;
        }
    } else {
        wxObj.username = respData.retData.username;
        wxObj.usercode = respData.retData.usercode;
        wxObj.userobj = respData.retData;
        /** 患者对象 **/
        wxObj.phonenumber = respData.retData.phonenumber1;
    }
}

/*function getBaseInfoByOpenId(openid){
	// 初始化基础信息
	wxObj.openid = openid;
	var respData = getDataFromServer(wxUserObj.getUserDocByOpenId + "&openid=" + wxObj.openid);
    if (!respData.retData) {
    }else{
    	wxObj.username=respData.retData.username;
    	wxObj.usercode=respData.retData.usercode;
    	wxObj.userobj=respData.retData; */
/** 患者对象 **/
/*
    }
}*/

/**
 * 存储微信带过来的值
 * @returns
 */
function storgeWeChatInfo() {
    var openid = $.getUrlVar('openid');
    var subscribe = $.getUrlVar('subscribe');
    var appid = $.getUrlVar('appid');
    if (openid) {
        localStorage["openid"] = openid;
        /*getBaseInfoByOpenId(openid);*/
    }
    if (subscribe) {
        localStorage.setItem("subscribe", subscribe);
    }
    if (appid) {
        localStorage.setItem("appid", appid);
    }
}

document.write('<script src="' + contextPath + '/static/js/wechat/kqds_wechat_user.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/wechat/kqds_wechat_order.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/wechat/kqds_wechat_bingli.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/wechat/kqds_wechat_zhongzhi.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/wechat/kqds_wechat_costOrder.js"><\/script>');