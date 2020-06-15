<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	YZPerson subPagePerson = SessionUtil.getLoginPerson(request);
%>
<!-- 消息推送页面 -->
<link rel="stylesheet" href="/base/static/css/admin/index/bower_components/bootstrap/dist/bootstrap.min.css">
<script type="text/javascript" src="/base/static/js/kqdsFront/ckPlayer/ckplayer.js"></script>
<script type="text/javascript">
//页面弹出层 index
var layindex = "";
// 弹出标示  空为第一次进页面，  0  弹出过，  1 弹出后关闭
var alertflag = "";
// 弹出消息的id集合
var idlist = new Array(); //创建一个数组
var wtslist = new Array(); //创建一个数组  未推送
var subPageUserId = "<%=subPagePerson.getUserId() %>";
$(function() {
    var soudurl = contextPath + "/static/image/notify.mp3";
    $('<audio id="chatAudio"><source src="' + soudurl + '" type="audio/mpeg"></audio>').appendTo('body');
    getPushAllnum();
});

/************************************************** websocket 相关 */
var websocket = null;
//判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {
    websocket = new WebSocket("ws://" + localhostPahtless + projectName + "/checkUserOnlineAndPushMsg/" + subPageUserId);
} else {
    alert('当前浏览器 Not support websocket')
}

//连接发生错误的回调方法
websocket.onerror = function() {
    // console.log("WebSocket连接发生错误");
};

//连接成功建立的回调方法
websocket.onopen = function() {
    // console.log("WebSocket连接成功");
}

//接收到消息的回调方法
websocket.onmessage = function(event) {
    getPushWebSocket(event.data);
}

//连接关闭的回调方法
websocket.onclose = function() {
    // console.log("WebSocket连接关闭");
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function() {
    closeWebSocket();
}

//强制关闭浏览器  调用websocket.close（）,进行正常关闭
window.onunload = function() {
    closeWebSocket();
}

//关闭WebSocket连接
function closeWebSocket() {
    websocket.close();
}
/************************************************** websocket 相关 end... */
//获取消息总数量
function getPushAllnum() {
    var url = contextPath + '/KQDS_PushAct/selectPageNum.act';
    $.axseSubmit(url, null, null,
    function(data) {
        setPushNum(data.total);
    },
    function() {});
}
function setPushNum(noread) {
    $("#PusgCount").html(noread);
}
// 获取推送列表---websocket
function getPushWebSocket(data) {
    if (!isJSON(data)) {
        return false;
    }
    data = JSON.parse(data);
    if (data.retState != "0") {
        return;
    }

    // 更新在线人数
    var retDataOnline = data.retDataOnline;
    $("#userCountInput").html(retDataOnline); // 更新首页右上方的 在线人数 数值 
    // 更新总的未推送消息数
    setPushNum(data.retDataPushNum);

    var resdata = data.retData; // 获取最新的5条，不会超过5条
    if (resdata.length == 0) {
    	//查询今日回访并推送
    	 var url = contextPath+'/KQDS_PushAct/selectTop5ByTime.act';
    	 $.ajax({
             type: "GET",
             url: url,
             data: {subPageUserId:subPageUserId},
             dataType: "json",
             success: function(data){
                	resdata=data;
			    	if(resdata.length == 0){
				        return;	 
			    	}else{
			    		idlist = new Array(); // 创建一个数组，存放消息主键
			    	    wtslist = new Array(); // 创建一个数组，存放未推送消息主键，用于判断是否弹出message box 
			    	    var html = "";
			    	    var seqIdBf = "";
			    	    for (var i = 0; i < resdata.length; i++) {
			    	        idlist.push(resdata[i].seqId);
			    	        if (resdata[i].pcpushed != '1') { // 如果该消息还没有推送
			    	            seqIdBf = seqIdBf + (resdata[i].seqId + ",");
			    	            wtslist.push(resdata[i].seqId);
			    	        }

			    	        /* 推送的内容为没有读的，没有读的 范围 比 没有推送的范围大
			    	  	            比如，没有读的消息为10条，没有推送的消息肯定小于10条 
			    	   	            这里的意思是，只要消息没有被点击读取，就算推送过后还是会推送，即左下角弹框显示  */
			    	        var url = contextPath + resdata[i].remindurl;
			    	        html += "<div  class=\"row\" style=\"margin-top: 10px;\"><div style=\"padding-right:0;padding-left:0;\" class=\"col-md-2\"><span class=\"badge\" style=\"display:block;width:80%;margin:0 auto;background-color:#00A6C0;\">" + resdata[i].notifytype + "</span></div><div class=\"col-md-5\" style=\"padding-left:0;padding-right:0;text-align:right;overflow:hidden;text-overflow:ellipsis;white-space: nowrap;\"><a onclick=\"yd('" + resdata[i].seqId + "','" + resdata[i].remindurl + "','" + resdata[i].notifytype + "')\" href=\"javascript:void(0);\" title=\"" + resdata[i].content + "\">" + resdata[i].content + "<a/></div><div class=\"col-md-5\">" + resdata[i].createtime + "</div></div>";
			    	    }

			    	    /** 减少更新数据库次数，将 yts(resdata[i].seqId)方法移到这里 **/
			    	    if (wtslist.length > 0) {
			    	        yts(seqIdBf); // 更改状态 为 已推送 
			    	    }

			    	    // 程序执行到这儿，说明一定有消息要弹框提醒了
			    	    if (alertflag == "") { //第一次进入页面，弹框提示
			    	        openBox(html);
			    	    } else { // 不是第一次进入页面，也就是弹框后，后台websocket再次推送进入该js方法时
			    	        if (wtslist.length > 0) { // 如果有未推送的信息，注意：这里是未推送的，不是未读的
			    	            openBox(html);
			    	        }
			    	    }

			    	    if (wtslist.length > 0) {
			    	        $('#chatAudio')[0].play(); //消息提示音
			    	    }

			    	    $("#boxContent").html(html); // 这行代码有问题，暂时不处理 ## 
			    	}
             }
         });
    }else{
    	idlist = new Array(); // 创建一个数组，存放消息主键
	    wtslist = new Array(); // 创建一个数组，存放未推送消息主键，用于判断是否弹出message box 
	    var html = "";
	    var seqIdBf = "";
	    for (var i = 0; i < resdata.length; i++) {
	        idlist.push(resdata[i].seqId);
	        if (resdata[i].pcpushed != '1') { // 如果该消息还没有推送
	            seqIdBf = seqIdBf + (resdata[i].seqId + ",");
	            wtslist.push(resdata[i].seqId);
	        }

	        /* 推送的内容为没有读的，没有读的 范围 比 没有推送的范围大
	  	            比如，没有读的消息为10条，没有推送的消息肯定小于10条 
	   	            这里的意思是，只要消息没有被点击读取，就算推送过后还是会推送，即左下角弹框显示  */
	        var url = contextPath + resdata[i].remindurl;
	        html += "<div  class=\"row\" style=\"margin-top: 10px;\"><div style=\"padding-right:0;padding-left:0;\" class=\"col-md-2\"><span class=\"badge\" style=\"display:block;width:80%;margin:0 auto;background-color:#00A6C0;\">" + resdata[i].notifytype + "</span></div><div class=\"col-md-5\" style=\"padding-left:0;padding-right:0;text-align:right;overflow:hidden;text-overflow:ellipsis;white-space: nowrap;\"><a onclick=\"yd('" + resdata[i].seqId + "','" + resdata[i].remindurl + "','" + resdata[i].notifytype + "')\" href=\"javascript:void(0);\" title=\"" + resdata[i].content + "\">" + resdata[i].content + "<a/></div><div class=\"col-md-5\">" + resdata[i].createtime + "</div></div>";
	    }

	    /** 减少更新数据库次数，将 yts(resdata[i].seqId)方法移到这里 **/
	    if (wtslist.length > 0) {
	        yts(seqIdBf); // 更改状态 为 已推送 
	    }

	    // 程序执行到这儿，说明一定有消息要弹框提醒了
	    if (alertflag == "") { //第一次进入页面，弹框提示
	        openBox(html);
	    } else { // 不是第一次进入页面，也就是弹框后，后台websocket再次推送进入该js方法时
	        if (wtslist.length > 0) { // 如果有未推送的信息，注意：这里是未推送的，不是未读的
	            openBox(html);
	        }
	    }

	    if (wtslist.length > 0) {
	        $('#chatAudio')[0].play(); //消息提示音
	    }

	    $("#boxContent").html(html); // 这行代码有问题，暂时不处理 ## 
    }
}

// 已读操作
function yd(seqId, url, type) {
    //设置为 已推送，已读
    var senddata = {
        seqId: seqId,
        pcpushreaded: "1",
        pcpushreadedtime: "<%=YZUtility.getCurDateTimeStr() %>"
    };
    updateReadStatus(senddata); // 更新状态为 已推送
    getPushAllnum(); // 更新 首页右上角的 消息未读数值
    // 打开页面
    var url = contextPath + url;
    if (type == "挂号") {
        // 等待治疗列表
        // parent.$("#tabIframe")[0].contentWindow.refresh();
        // document.getElementById("iframe").contentWindow.initTable(0);
    } else if (type == "开单") {
        // 等待治疗列表
        // document.getElementById("iframe").contentWindow.getOrderlist(1);
    } else if (type == "缴费") {
        // 今日患者
        // document.getElementById("iframe").contentWindow.initTable(5);
    } else if (type == "门诊") {
        parent.layer.open({
            type: 2,
            title: type,
            shadeClose: true,
            shade: 0.6,
            area: ['90%', '90%'],
            content: url //iframe的url
        });
    } else if (type == "回访" || type == "回访超时") {
        parent.layer.open({
            type: 2,
            title: '回访记录',
            maxmin: true,
            shadeClose: true,
            //点击遮罩关闭层
            area: ['900px', '600px'],
            content: url + '&type=isparent&noadd=1'
        });
    } else if (type == "推广" || type == "推广超时") {

} else if (type == "加工") {
        parent.layer.open({
            type: 2,
            title: '加工单',
            maxmin: true,
            shadeClose: true,
            //点击遮罩关闭层
            area: ['90%', '90%'],
            content: url
        });
    } else if (type == "退费") {
        parent.layer.open({
            type: 2,
            title: '退款申请',
            maxmin: true,
            shadeClose: true,
            //点击遮罩关闭层
            area: ['90%', '90%'],
            content: url
        });
    } else if (type == "就诊提醒") {
        var jztx = $(parent.document.getElementById("iframe"));
        jztx.attr({
            "src": contextPath + '/KQDS_JzqkAct/toTx.act?menuId=600311'
        });
        // parent.layer.close(frameindex); //再执行关闭 */
    } else if (type == "库存报警") {
        parent.layer.open({
            type: 2,
            title: '库存报警',
            maxmin: true,
            shadeClose: true,
            //点击遮罩关闭层
            area: ['90%', '90%'],
            content: contextPath + '/KQDS_Ck_Goods_DetailAct/toGoodsgJ.act'
        });
    } else if(type == "回访提醒"){
    	parent.layer.open({
            type: 2,
            title: type,
            shadeClose: true,
            shade: 0.6,
            area: ['1000px', '90%'],
            content: url // iframe的url
        });
    }else {
        parent.layer.open({
            type: 2,
            title: type,
            shadeClose: true,
            shade: 0.6,
            area: ['740px', '90%'],
            content: url // iframe的url
        });
    }
}

//更新推送消息的状态 为  已推送
function updatePushStatus(sendata) {
    getDataFromServerY('KQDS_PushAct/updatePushStatus.act', sendata);
}
//更新推送消息的状态 为  已读
function updateReadStatus(sendata) {
    getDataFromServerY('KQDS_PushAct/updateReadStatus.act', sendata);
}
//已推送操作
function openPushList() {
    var url = contextPath + "/KQDS_PushAct/toPushIndex.act";
    layer.open({
        type: 2,
        title: "消息推送列表",
        shadeClose: false,
        shade: 0.6,
        area: ['90%', '90%'],
        content: url,
    });
}
//已推送操作
function yts(seqId) {
    var senddata = {
        seqId: seqId,
        pcpushed: "1",
        pcpushedtime: "<%=YZUtility.getCurDateTimeStr() %>"
    };
    updatePushStatus(senddata); //设置为 已推送，已读
}
//已读 所有操作
function ydAll() {
	if(idlist.length == 0){
    	return ;
    }
	
	var seqIdBf = "";
    for (var i = 0; i < idlist.length; i++) {
        var seqId = idlist[i]
        seqIdBf = seqIdBf + (seqId + ",");
    }
    
    var senddata = {
        seqId: seqIdBf,
        pcpushreaded: "1",
        pcpushreadedtime: "<%=YZUtility.getCurDateTimeStr() %>"
    };
    
    updateReadStatus(senddata); // 设置为 已推送，已读
    getPushAllnum(); // 更新 首页右上角的 消息未读数值
    layer.close(layindex); // 关闭窗口
}
function openBox(html) {
    // 边缘弹出
    layindex = layer.open({
        type: 1,
        offset: 'rb',
        id: 'box',
        content: '<div class="container-fluid" id="boxContent">' + html + '</div>',
        btn: ['全部已读'],
        anim: 6,
        time: 90000,
        area: ['450px', '300px'],
        btnAlign: 'l',
        // 按钮居中
        closeBtn:0,
        shade: 0,
        // 不显示遮罩
        success: function(layero, index) {
            layindex = index;
            alertflag = "1";
        },
        yes: function() {
            // 全部已读操作
            ydAll();
        },
        end: function() {
            // 清除标示，
            layindex = "";
            alertflag = "2";
        }
    });
}
</script>