<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>设备详情</title>
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
		<label for="sortno" class="col-xs-2 control-label" style="float: left;"> 设备ID：</label>
		<div class="col-xs-10">
			<span id="deviceid"></span>
		</div>
	</div>
	<div class="form-group">
		<label for="sortno" class="col-xs-2 control-label" style="float: left;"> 设备名称：</label>
		<div class="col-xs-10">
			<span id="devicename"></span>
		</div>
	</div>
	<div class="form-group">
		<label for="printname" class="col-xs-2 control-label" style="float: left;"> 设备IP：
		</label>
		<div class="col-xs-10" >
			<span id="deviceip"></span>
		</div>
	</div>
	<div class="form-group">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 磁盘空间：
		</label>
		<div class="col-xs-10" >
			<span id="diskspace"></span>
		</div>
	</div>
	<div class="form-group">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 剩余磁盘空间：
		</label>
		<div class="col-xs-10">
			<span id="freespace"></span>
		</div>
	</div>
	<div class="form-group">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 设备开机时间：
		</label>
		<div class="col-xs-10" >
			<span id="starttime"></span>
		</div>
	</div>
	<!-- <div class="form-group">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 连接到主机状态：
		</label>
		<div class="col-xs-10" >
			<span id="connecttoserver"></span> 0-表示未连接1-已连接
		</div>
	</div>
	<div class="form-group">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 主机IP：
		</label>
		<div class="col-xs-10" >
			<span id="serverip"></span> 
		</div>
	</div> -->
	<div class="form-group">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 最后录音时间：
		</label>
		<div class="col-xs-10" >
			<span id="lastrecordtime"></span> 
		</div>
	</div>
	<div class="form-group">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 录音格式：
		</label>
		<div class="col-xs-10" >
			<span id="recordfmt"></span> 
		</div>
	</div>
	<div class="form-group">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 设备当前时间：
		</label>
		<div class="col-xs-10" >
			<span id="devicetime"></span> 
		</div>
	</div>
	<div class="form-group">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 通道数：
		</label>
		<div class="col-xs-10" >
			<span id="channelcount"></span> 
		</div>
	</div>
	<div class="form-group">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 超级用户用户名：
		</label>
		<div class="col-xs-10" >
			<span id="adminuser"></span> 
		</div>
	</div>
	<!-- <div class="form-group">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 超级用户密码：
		</label>
		<div class="col-xs-10" >
			<span id="adminpwd"></span> 
		</div>
	</div> -->
	
</div>
</form>
<script>
	$(function() {
		var detailurl = '<%=contextPath%>/KQDS_SoundRecordAct4DJ/deviceDetail.act';
		$.axse(detailurl, null,
		function(data) {
			loadData(data);
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