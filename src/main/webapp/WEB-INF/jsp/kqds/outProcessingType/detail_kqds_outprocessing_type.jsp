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
<script type="text/Javascript" src="<%=contextPath%>//static/js/kqds/outProcessingType/outprocessingtype.js"></script>
<title>加工项目分类详情</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />
</head>
<body>

<div class="addTypeContainer">
	<form id="form1">
		<input type="hidden" class="form-control" name="seqId" id="seqId">
		<table class="tableLayout">
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">分类名称</span>
					
				</td>
				<td>
					<div class="form-group">
						<input readonly type="text"  name="typename" id="typename" placeholder="分类名称">
					</div>
				</td>
				<td>
					<span class="commonText">分类编号</span>
					
				</td>
				<td>
					<div class="form-group">
						<input readonly type="text" name="typeno" id="typeno" placeholder="分类编号">
					</div>
				</td>
			</tr>
			
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">加工厂</span>
					
				</td>
				<td>
					<div class="form-group">
						<select readonly name="processingfactory" id="processingfactory"></select>
					</div>
				</td>
				<td>
					<span class="commonText">启用/禁用</span>
					
				</td>
				<td>
					<div class="form-group">
						<select readonly name="useflag" id="useflag">
							<option value="0" selected="selected">启用</option>
							<option value="1">禁用</option>
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
		initFactorySelect4Back("processingfactory",'','<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>');
		var detailurl = '<%=contextPath%>/KQDS_OutProcessing_TypeBackAct/selectDetail.act?seqId=' + seqId;
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