<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	YZPerson person = SessionUtil.getLoginPerson(request);
	String mtype = request.getParameter("mtype");
	if (mtype == null) {
		mtype = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>种植病历库</title>
<!-- 点击右侧个人中心  中间病历 图标 页面， 进入后点击  常用病历库按钮 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/fhbuttonTree.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<style>
ul.ztree{
	margin-top:5px;
	padding:0;
}
#treeDemo li{
	padding:1px 5px;
	height:20px;
	line-height:20px;
	border-bottom:1px solid #ddd;
}
#treeDemo li span{
	font-size:13px;
}
#treeDemo li:nth-child(odd){
	background:#f0ffff;
}
#treeDemo li:hover{
	background:#ddd;
}
</style>
</head>
<body>
	<div class="leftFatherDiv" style="float: left;width:24%;margin-left:1%;">
		<div style="margin-top:5px;overflow:hidden;">
			<span id="selfblk" class="kuBtn kuBtnCurrent"> 
				自用病历库
			</span>
			<span id="publicblk" class="kuBtn" >
				标准病历库
			</span>
		</div>
		<ul id="treeDemo" class="ztree" style="width:98%;height:90%;"></ul>
	</div>
	<div class="rightFatherDiv"  style="float: left;width:75%;">
		<div id="toolbar">
			<button id="parentIframe" class="confirmBtn">
				<i class="glyphicon glyphicon-plus"></i> 确认
			</button>
		</div>
		<div class="box-body" id="blcontent" style="margin-top:10px;">
			<iframe id="contentFrame" name="contentFrame" src="<%=contextPath%>/KQDS_BLKAct/toEmtp.act" width="100%" height="100%" frameborder="0" ></iframe>
		</div>
	</div>
<script>
var perseqId = "<%=person.getSeqId()%>";
var content = "";
var mtype = "<%=mtype%>";
var static_type = "";
var pageurl = 'KQDS_BLKAct/selectNoPage4Front.act?mtype=<%=mtype%>&organization=<%=ChainUtil.getCurrentOrganization(request)%>';
var seqId_blk = '';
var mtype_blk = '';
$(function () {
	var contentHeight=0;
	$("#selfblk").trigger("click");
	
	contentHeight=getContentHeight();
	$(".leftFatherDiv").outerHeight(contentHeight+8);
	$("#contentFrame").outerHeight(contentHeight-30);
});
$(".kuBtn").click(function(e){/* 按钮的点击的切换效果 */
	/*wl添加  */
	$(e.target).addClass("kuBtnCurrent").siblings().removeClass("kuBtnCurrent");
	/*wl结束*/
});

function getContentHeight(){
	var contentHeight=$(window).height()-20;
	return contentHeight;
}
$("#selfblk").click(function(){
	var pageurl2 = pageurl;
	pageurl2 += '&type=1';
	static_type = 1;
	initList(pageurl2);
});
$("#publicblk").click(function(){
	var pageurl2 = pageurl;
	pageurl2 += '&type=0';
	static_type = 0;
	initList(pageurl2);
});

function initList(url){
	var serdata = getDataFromServer(url);
	var list = serdata.rows;
	var strHtml = "";
	$.each(list,function(i){
	    var requrl = '';
	    if (list[i].mtype == '2') {
	    	requrl = contextPath+'/KQDS_BLKAct/toZhongZhiYiQi_Detail.act';
	    }
	    if (list[i].mtype == '3') {
	    	requrl = contextPath+'/KQDS_BLKAct/toZhongZhi_Suture_Removal_Detail.act';
	    }
	    if (list[i].mtype == '4') {
	    	requrl = contextPath+'/KQDS_BLKAct/toZhongZhiErQi_Detail.act';
	    }
	    if (list[i].mtype == '5') {
	    	requrl = contextPath+'/KQDS_BLKAct/toZhongZhi_XiuFu_Detail.act';
	    }
	    requrl += '?seqId=' + list[i].seqId + '&mtype=' + list[i].mtype + '&type=' + static_type;
	    requrl += '&detailFlag=detail'; // 有值，就是代表是跳转到详情页面的
		strHtml += "<li target='contentFrame' style='cursor:pointer;' src='" + requrl + "' onclick=\"clickLI('"+list[i].seqId+"','"+list[i].mtype+"',this);\"><span>"+list[i].blname+"</span></li>";
	});
	$("#treeDemo").html(strHtml);
}

function clickLI(seqId,mtype,thisobj){
	seqId_blk = seqId;
	mtype_blk = mtype;
	var src = $(thisobj).attr('src');
    $('#contentFrame').attr('src',src);
}

// 弹出一个iframe层
$('#parentIframe').on('click', function(){
	if(seqId_blk == ""){
		layer.alert('请选择病历模板！'  );
		return false;
	}
	if(parent.getblcontent4copy){
		parent.getblcontent4copy(seqId_blk,mtype_blk);
	}
	var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	if(frameindex){
		parent.layer.close(frameindex); //再执行关闭 */
	}
});
</script>
</body>
</html>
