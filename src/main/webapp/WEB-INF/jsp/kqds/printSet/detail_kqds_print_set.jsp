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
<title>打印设置详情</title>
</head>
<body>
<form class="form-horizontal" id="form1">
	<div class="box-body">
		<input type="hidden" class="form-control" name="seqId" id="seqId">
		<div class="form-group">
			<label for="sortno" class="col-sm-2 control-label"> 排序号 <span style='color:red;'> *</span></label>
		<div class="col-sm-4">
			<!-- <input type="text" class="form-control" name="sortno" id="sortno" placeholder="排序号"> -->
			<input type="text" class="form-control" name="sortno" id="sortno">
		</div>
	</div>
	<div class="form-group">
		<label for="printname" class="col-sm-2 control-label"> 打印名称<span style='color:red;'> *</span>
		</label>
		<div class="col-sm-4">
			<!-- <input type="text" class="form-control" name="printname" id="printname" placeholder="打印名称"> -->
			<input type="text" class="form-control" name="printname"
				id="printname">
		</div>
	</div>
	<div class="form-group">
		<label for="printtype" class="col-sm-2 control-label"> 打印类型<span style='color:red;'> *</span>
		</label>
		<div class="col-sm-4">
			<!-- <input type="text" class="form-control" name="printtype" id="printtype" placeholder="打印类型"> -->
			<input type="text" class="form-control" name="printtype"
				id="printtype">
		</div>
	</div>
	<div class="form-group">
		<label for="printurl" class="col-sm-2 control-label"> 打印地址<span style='color:red;'> *</span> </label>
		<div class="col-sm-4">
			<!-- <input type="text" class="form-control" name="printurl" id="printurl" placeholder="打印地址"> -->
			<input type="text" class="form-control" name="printurl" id="printurl">
		</div>
	</div>
	</div>
</form>
<script>
	var seqId = "<%=seqId%>";
	var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	$(function() {
		var detailurl = '<%=contextPath%>/KQDS_Print_SetBackAct/selectDetail.act?seqId=' + seqId;
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