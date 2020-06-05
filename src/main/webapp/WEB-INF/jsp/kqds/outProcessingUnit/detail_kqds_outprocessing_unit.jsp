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
<title>加工厂详情</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />
</head>
<body>
<div class="outprocessingContainer"><!--加工厂的容器  -->
	<form id="form1">
		<input type="hidden" class="form-control" name="seqId" id="seqId">
		<table class="tableLayout">
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">加工厂编码</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="code" id="code" readonly placeholder="">
					</div>
				</td>
				<td>
					<span class="commonText">名称</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="name" id="name" readonly placeholder="">
					</div>
				</td>
			</tr>
			
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">地址</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="address" id="address" readonly placeholder="">
					</div>
				</td>
				<td>
					<span class="commonText">联系电话</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="phone" id="phone" readonly placeholder="">
					</div>
				</td>
			</tr>
			
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">备注</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="remark" id="remark" readonly placeholder="">
					</div>
				</td>
				<td>
					<span class="commonText">是否禁用</span>
					
				</td>
				<td>
					<div class="form-group">
						<select name="useflag" id="useflag" readonly>
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>
<script>
var seqId = "<%=seqId%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
    var detailurl = '<%=contextPath%>/KQDS_OutProcessing_UnitBackAct/selectDetail.act?seqId=' + seqId;
    $.axse(detailurl, null,
    function(data) {
        loadData(data.data);
    },
    function() {
        layer.alert('查询出错！' );
    });
    readonly();
});
</script>
</body>

</html>