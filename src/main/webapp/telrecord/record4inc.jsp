<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=contextPath%>/telrecord/js/pulgin/swfobject.js"></script>
<script type="text/javascript">
// 高版本chrome浏览器下，必须设置高宽，否则无法使用swf
swfobject.embedSWF("<%=contextPath%>/telrecord/eVRSControl.swf", "swfDiv", "0", "0", "9.0.0", "expressInstall.swf",{ 
	// flashvars，用于向flash传值
}, {
	// 参数
}, {
	name: "eVRSCon" // 属性
});

</script>
<!-- <div id="div2">
	<ul>
		设备IP
		<input type="text" id="ip" value="192.168.0.200" id="ipaddress" />
		用户名
		<input type="text" id="user" value="admin" id="loginname" /> 
	密码
		<input type="text" id="pwd" value="123456" id="loginpwd" /> 
		端口
		<input type="text" id="port" value="80" id="port" /> 
		<button id='start' onclick="connect();">连接</button>
	</ul>
</div>-->
<div style="width: 0px;height: 0px;">
<div id="swfDiv" >
	<p><a href="http://www.adobe.com/go/getflashplayer">点击允许运行flash</a></p>
</div>
</div>
<script type="text/javascript">
var ch_count = 256; //显示通道数量
var chindex = new Array();
var gdsttime = new Array();
var chstart = new Array();
var rowindex = new Array();
var isMonistor = 0; //1表示支持监听，2，不支持
var isremote_code = 0; //1表示有对方名字
var is_show_remotecode = 0;
var cur_ch = -1; //当前监听通道，-1表示未有通道监听
var cur_status = 0; //当前监听通道状态，1正在监听，0未监听
//var ip="192.168.0.113"
var ip = '192.168.0.200';
var username = '<%=recordaccount %>';
var loginpwd = '<%=recordpwd %>';
var port = 23456;
var isconnect = false;
var re_connect_time = 5000;
var heart_time = 5000;
//初始化控件
var eVRSCon = null;

/**
 * 口腔大师业务逻辑，同一个号码不重复弹出
 */
var kqds_phonenumber = null;

$(function(){
	
});

function connect() {
    // ip = $("#ip").val();
    eVRSCon.Connect(ip, port);
    
    // $("#div2").html("正在连接到设备：" + ip);
}
//flash加载完成后回自动回调Init方法
function Init() {
    // alert(userch);
    eVRSCon = getSWF("eVRSCon"); //获取eVrsCon控件对象
    //注册事件  
    //注册方式：addEventListen("注册的事件","回调方法");
    eVRSCon.addEventListen("Connect", "connect_event"); //连接 设备回调事件
    eVRSCon.addEventListen("LogAckEvent", "logack_event"); //登录结果回调事件
    eVRSCon.addEventListen("DevInfoEvent", "devinfo_event"); //设备信息
    eVRSCon.addEventListen("VoltageChange", "voltageChange_event"); //电压改变事件
    eVRSCon.addEventListen("StateChange", "stateChange_event"); //通道状态改变事件
    eVRSCon.addEventListen("CodeEvent", "codeEvent_event"); //号码改变事件
 	eVRSCon.addEventListen("TalkAction", "talkAction_event"); //录音事件
    eVRSCon.addEventListen("Disconnect", "disconnect_event"); //失去与 设备的连接
    for (i = 0; i < 256; i++) {
        chindex[i] = -1;
        rowindex[i] = -1;
        gdsttime[i] = 0;
    }
    
    // 初始化完成后直接调用
    connect();
}

function settime() {
	
}

//检查是否连接设备
function isConnectServer() {
    if (!isconnect) {
        //重连设备
        location.reload();
    }
}

//录音事件
function talkAction_event(d) {
    var d = eval('(' + d + ')');
    var ch = d.ch; //通道数
    var len = d.len; //通话时长，如果为０，表示没有通话，是一个未接电话
    var lc = d.lc; //本地号码;
    var rc = d.rc; //对方号码;
    var begin = d.begin; //本次话单开始通话时间，如果是未接电话，表示是第一声响铃时间。
    var di = d.di; //方向　0为呼入 1为呼出
    var file = d.file; //录音文件路径和名称	
}

//电压改变事件
function voltageChange_event(d) {
    var d = eval('(' + d + ')');
    var ch = d.ch; //通道
    var dvt = d.vt; //电压 当为-1时，说明不是模拟录音通道
    if (dvt == "-1") {
        set_item_val(ch, 'voltage', '');
        return;
    } else set_item_val(ch, 'voltage', dvt);
}

function set_item_val(row, key, val) {
}

//区分火狐、IE浏览器	  
function getSWF(movieName) {
    if (navigator.appName.indexOf("Microsoft") != -1) {
        return document.getElementById(movieName);
    } else {
        return document[movieName];
    }
}

//连接 设备器事件
function connect_event(d) {
    //d 	1:成功 ，0失败
    if (d == 1) {
        eVRSCon.Login(username, loginpwd, false);
    } else {
        alert("无法连接到电话录音设备");
        setInterval(isConnectServer, re_connect_time); //检查连接状态
        isconnect = false;
    }
}

//登出
function logOut() {
    eVRSCon.LogOut("");
}

//登录事件
/* function logack_event(d) {
    var d = eval('(' + d + ')');
    var res = d.res;
    var memo = d.memo;
    // res  1:成功 ，其他失败
    if (res == 1) {
        setInterval(SendSocketHeart, heart_time);
        alert('成功登录电话录音系统');
        setInterval(settime, 1000); //更新通话时间
        isconnect = true;
    } else {
        alert('登录到设备失败，请检查用户名或密码');
    	setInterval(isConnectServer, re_connect_time); //检查连接状态
        isconnect = false;
    }
} */
function logack_event(d) {
    var d = eval('(' + d + ')');
    var res = d.res;
    var memo = d.memo;
    // res  1:成功 ，其他失败
    if (res == 1) {
        setInterval(SendSocketHeart, heart_time);
        setInterval(settime, 1000);
        isconnect = true;
    } else {
        setInterval(isConnectServer, re_connect_time);
        isconnect = false;
    }
}

function SendSocketHeart() {
    if (isconnect) eVRSCon.Monitor(10000, 0);
}

//设备信息事
function devinfo_event(d) {
    var d = eval('(' + d + ')');
    var runstart = d.runstart; //设备本次运行开始时间
    var ch = d.ch; //通道数
    var choft = d.choft; //通道开始偏移量
    var devid = d.devid; //设备id
    var ser = d.ser; //序列号
    var mktime = d.mktime; //出厂日期
}

/**
 * 判断是否为电话号码
 */
function checkTel(value) {
    var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
    var isMob = /^((\+?86)|(\(\+86\)))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
    if (isMob.test(value) || isPhone.test(value)) {
        return true;
    } else {
        return false;
    }
}

/**
 * 通道改变事件
 * 补充说明，这里是循环所有通道
 */
function stateChange_event(d) {
    var d = eval('(' + d + ')');
    var dch = d.ch; //通道
    var dlc = d.lc; //本地号码
    var drc = d.rc; //对方号码
    var dus = d.us; //用户
    var dvt = d.vt; //电压 当-1时，说明不是模拟录音通道
    if (dvt == "-1") dvt = "";
    var ddi = d.di; //方向 0为呼入 1为呼出
    var dstv = d.st; //状态  //0空闲，2 录音，3 通话，4 振铃，9断线
    var dsttime = d.sttime;
    var contactname = "";
    
    var isTel = checkTel(drc);
    if(isTel){
    	if(kqds_phonenumber){ // 不是第一个电话，则判断两个电话号码是否一样
    		if(drc != kqds_phonenumber){
    			kqds_phonenumber = drc;
    			// alert("根据号码弹框：" + drc);
    			openUserCenterByPhonenumber(drc);
    		}
    	}else{
    		// 初始页面后的第一个电话
    		kqds_phonenumber = drc;
    		openUserCenterByPhonenumber(drc);
    		// alert("根据号码弹框：" + drc);
        	// console.log('ss###对方号码：' + drc + ",本地号码：" + dlc + ",通道：" + dch + "，状态：" + dstv + ",方向：" + ddi + "，用户：" + dus);
    	}
    }
}

//号码改变事件
function codeEvent_event(d) {
    var d = eval('(' + d + ')');
    var ch = d.ch; //通道数
    var st = d.st; //0空闲，2 录音，3 通话，4 振铃，9断线
    var lc = d.lc; //本地号码
    var rc = d.rc; //对方号码
    
    var isTel = checkTel(drc);
    if(isTel){
    	// console.log('11###对方号码：' + d.rc + ",本地号码：" + d.lc + ",通道数：" + d.ch + "，状态：" + st);
    }
   
}

function disconnect_event() {
    $("#div2").html("与设备的连接断开！");
    setInterval(isConnectServer, re_connect_time); //检查连接状态
    isconnect = false;
}

function openUserCenterByPhonenumber(phonenumber){
	var detailurl = "UserDocAct/getUsercodeByPhonenumber.act?phonenumber=" + phonenumber;
    var rtData = getDataFromServer(detailurl);
    if(rtData.retState == "0"){
    	if(rtData.usercode){
    		layer.open({
    	        type: 2,
    	        title: '患者档案中心',
    	        shadeClose: true,
    	        shade: 0.6,
    	        area: ['900px', '650px'],
    	        content: contextPath + '/KQDS_UserDocumentAct/toUserCenter.act?usercode=' + rtData.usercode
    	    });
    	}else{
    		layer.alert("根据电话号码查询不到患者信息！", {
        		  
        	});
    	}
    }else{
    	layer.alert("根据电话号码查询患者编号失败！", {
    		  
    	});
    }
}

</script>