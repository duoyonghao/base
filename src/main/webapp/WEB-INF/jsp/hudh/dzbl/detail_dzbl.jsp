<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	String blCode = request.getParameter("blCode");
	
	String blId = request.getParameter("blId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>病历详情</title><!-- 医疗中心 病历查询  > 详情链接   -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/style.css">
<script type="text/javascript" src="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/upload/uploadutil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<style type="text/css">
.aBtn{display: inline-block;margin-right:0px;padding: 0 7px;height: 28px;line-height: 28px;border: solid 1px #0e7cc7;border-radius:15px;color: #0e7cc7;}
.aBtn:hover{color: #fff;background: #117cca;text-decoration: none;}
table {
    border-collapse: collapse;
    border-spacing: 0;
}

th {
	text-align: left;
	vertical-align: middle;
	border: 1px solid #bbb;
}

tr{
	height:30px;
	border: 1px solid #bbb;
}

td{
	border: 1px solid #bbb;
}

span {
	font-size: 18px;
}

label.col-sm-1{
	padding-right:0;
}
.box-body .form-control[readonly],.box-body .form-control[disabled]{
	background:#fff;
}
</style>
	
</head>
<body>
<div class="container-fluid">
	<section class="content">
		<div class="row">
			<div class="col-sm-12">
				<!-- 档案基本信息 -->
				<div class="box box-info">
					<!-- <div class="box-header with-border">
						<h4 class="box-title">患者信息</h4>
					</div> -->
					<form class="form-horizontal" id="form1">
						<div class="box-body">
							<div class="form-group">
								<label for="usercode" class="col-sm-1 control-label">姓名</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name="name"
										id="name" readonly="readonly" style="width: 100%;">
								</div>
								<label for="usercode" class="col-sm-1 control-label">性别</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name="sex"
										id="sex" readonly="readonly" style="width: 100%;">
								</div>
								<label for="usercode" class="col-sm-1 control-label">年龄</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name="age"
										id="age" readonly="readonly" style="width: 100%;">
								</div>
								<label for="usercode" class="col-sm-1 control-label">日期</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name="ssTime"
										id="ssTime" style="width: 100%;">
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-12 control-label text-center" style="text-align: center" id="title"></label>
								<div class="col-sm-12">
									<!-- <textarea class="form-control" readonly="readonly" style="height: 200px" id="detail"></textarea> -->
									<div  class="form-control" readonly="readonly" style="min-height: 200px;height: auto;" id="detail"></div>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-4 control-label"></label>
								<label class="col-sm-2 control-label">医生签名：</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name=""
										id="" readonly="readonly" style="width: 100%;">
								</div>
								<label class="col-sm-2 control-label">日期：</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name=""
										id="" readonly="readonly" style="width: 100%;">
								</div>
								
								<div class="col-sm-12" style="text-align: right;margin-top: 10px;">
									<a href="javascript:void(0);" style="margin-top:5px;" onclick="printbl()" class="kqdsSearchBtn">打印病历</a>
								</div>
							</div>
						</div>
					</form>
				</div>
				
			</div>
		</div>
	</section>
</div>
<script>
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var blId = "<%= blId%>";
var blCode = "<%= blCode%>";
apppath = apppath();
$(function() {
	//获取患者信息及病历详情
	$.ajax({
		url: apppath + '/HUDH_DzblAct/findPrintInfo.act',
		type:"POST",
		dataType:"json",
		data : {"blId":blId,"blCode":blCode},
		async: false,
		success:function(result){
			var userDoc = result.userDoc;
			var dzblInfo = result.dzblInfo;
			$("#name").val(userDoc.username);
			$("#age").val(userDoc.age);
			$("#sex").val(userDoc.sex);
			
			$("#title").append('<h3>'+dzblInfo.title+'</3>');
			
			$("#ssTime").val(dzblInfo.createtime.substring(0,10));
		/* 	$("#detail").val(decodeTextAreaString(dzblInfo.detail)); */
			$("#detail").html(dzblInfo.detail);
		}
	});
});

function printbl(){
	 var ssTime = $("#ssTime").val().substring(0,10);
	 if(!ssTime) {
		 layer.alert("请填写yyyy-mm-dd格式的手术日期");
		 return;
	 }
	 window.location.href = contextPath + '/HUDH_DzblAct/dzblPrint.act?blId='+blId+'&blCode='+blCode +'&ssTime='+ssTime;
	 return false;
}
</script>
</body>
</html>
