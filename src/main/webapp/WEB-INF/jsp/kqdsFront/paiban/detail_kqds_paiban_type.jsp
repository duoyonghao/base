<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<% String seqId=request.getParameter( "seqId"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>排班类型详情</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />	
</head>
<body>
<div class="paibanContainer"><!--排班的容器  -->
	<form id="form1">
		<input type="hidden" class="form-control" name="seqId" id="seqId">
		<table class="tableLayout">
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">上班时间<span style="color: red;">*</span></span>
					
				</td>
				<td>
					<div class="form-group">
						<!-- .birthDate与弹出时间选择框功能有关系           .whiteInp 白底鼠标移入有小手的效果  -->
						<input type="text" class="birthDate whiteInp" name="startdate" id="startdate" readonly placeholder="">
					</div>
				</td>
				<td>
					<span class="commonText">下班时间<span style="color: red;">*</span></span>
					
				</td>
				<td>
					<div class="form-group">
						<!-- .birthDate与弹出时间选择框功能有关系   .whiteInp 白底鼠标移入有小手的效果 -->
						<input class="birthDate whiteInp" type="text" class="birthDate" name="enddate" id="enddate" readonly placeholder="">
					</div>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="commonText">排班类型<span style="color: red;">*</span></span>
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="typename" id="typename" readonly placeholder="">
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
    var detailurl = '<%=contextPath%>/KQDS_Paiban_TypeBackAct/selectDetail.act?seqId=' + seqId;
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