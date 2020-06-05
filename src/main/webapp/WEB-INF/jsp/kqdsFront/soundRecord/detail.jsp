<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	String seqId = request.getParameter("seqId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>录音详情</title>
<style>
	.form-horizontal .control-label{
		padding:0;
	}
	.box-body{
		    margin: 30px 0 0 10px;
	}
	.form-group label{
		text-align:right;
	}
</style>
</head>
<body>
<form class="form-horizontal" id="form1">
<div class="box-body">
	<div class="form-group">
		<label for="sortno" class="col-xs-2 control-label" style="float: left;"> 患者姓名：</label>
		<div class="col-xs-10">
			<span id="username"></span>
		</div>
	</div>
	<div class="form-group">
		<label for="sortno" class="col-xs-2 control-label" style="float: left;"> 患者编号：</label>
		<div class="col-xs-10">
			<span id="usercode"></span>
		</div>
	</div>
	<div class="form-group">
		<label for="printname" class="col-xs-2 control-label" style="float: left;"> 电话线路：
		</label>
		<div class="col-xs-10" >
			<span id="linenum"></span>
		</div>
	</div>
	<div class="form-group">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 电话号码：
		</label>
		<div class="col-xs-10" >
			<span id="phonenumber"></span>
		</div>
	</div>
	<div class="form-group">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 录音文件：
		</label>
		<div class="col-xs-10">
			<span id="filename"></span>
		</div>
	</div>
	<div class="form-group">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 录音时间：
		</label>
		<div class="col-xs-10" >
			<span id="recordtime"></span>
		</div>
	</div>
</div>
</form>
<script>
	var seqId = "<%=seqId%>";
	var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	$(function() {
		var detailurl = '<%=contextPath%>/KQDS_SoundRecordAct/selectDetail.act?seqId=' + seqId;
		$.axse(detailurl, null,
		function(data) {
			loadData(data.data);
		},
		function() {
			layer.alert('查询出错！', {
				  
			});
		});
		readonly();
	});
</script>
</body>

</html>