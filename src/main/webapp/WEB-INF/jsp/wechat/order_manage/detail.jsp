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
<title>预约详情</title>
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
		<label for="printname" class="col-xs-2 control-label" style="float: left;"> 预约时间：
		</label>
		<div class="col-xs-10" >
			<span id="orderdate"></span>  <span id="ordertime"></span>
		</div>
	</div>
	<div class="form-group">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 确认时间：
		</label>
		<div class="col-xs-10" >
			<span id="confirmtime"></span>
		</div>
	</div>
	<div class="form-group">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 预约门诊：
		</label>
		<div class="col-xs-10">
			<span id="organizationname"></span>
		</div>
	</div>
	<div class="form-group">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 项目分类：
		</label>
		<div class="col-xs-10" >
			<span id="askitemname"></span>
		</div>
	</div>
	<div class="form-group">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 咨询内容：
		</label>
		<div class="col-xs-10" >
			<div id="askcontent"></div>
		</div>
	</div>
	<div class="form-group">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 预约状态：
		</label>
		<div class="col-xs-10">
			<span id="orderstatusname"></span>
		</div>
	</div>
	<div class="form-group">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 提交时间：
		</label>
		<div class="col-xs-10">
			<span id="createtime"></span>
		</div>
	</div>
	<div class="form-group" style="display: none;" id="cancel1">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 取消原因：
		</label>
		<div class="col-xs-10">
			<span id="cancelreason"></span>
		</div>
	</div>
	<div class="form-group" style="display: none;" id="cancel2">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 取消时间：
		</label>
		<div class="col-xs-10">
			<span id="canceltime"></span>
		</div>
	</div>
	<div class="form-group" style="display: none;" id="jujue1">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 拒绝原因：
		</label>
		<div class="col-xs-10">
			<span id="auditorremark"></span>
		</div>
	</div>
	<div class="form-group" style="display: none;" id="jujue2">
		<label for="printtype" class="col-xs-2 control-label" style="float: left;"> 拒绝时间：
		</label>
		<div class="col-xs-10">
			<span id="auditortime"></span>
		</div>
	</div>
</div>
</form>
<script type="text/Javascript" src="<%=contextPath%>/static/js/wechat/kqds_wechat_order.js"></script>
<script>
	var seqId = "<%=seqId%>";
	var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	$(function() {
		var detailurl = '<%=contextPath%>/WXOrderAct/selectDetail.act?seqId=' + seqId;
		$.axse(detailurl, null,
		function(data) {
			loadData(data.data);
			
			$("#orderstatusname").html(getOrderStatus(data.data.orderstatus));
			
			if(data.data.orderstatus == 2){ // 拒绝
				$("#jujue1").show();
				$("#jujue2").show();
			}
			
			if(data.data.orderstatus == 3){ // 取消
				$("#cancel1").show();
				$("#cancel2").show();
			}
			
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