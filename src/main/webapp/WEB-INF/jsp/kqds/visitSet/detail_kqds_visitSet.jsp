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
<title>回访设置详情</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />
</head>
<body>
<div class="outprocessingContainer">
	<form id="form1">
		<input type="hidden" class="form-control" name="seqId" id="seqId">
		<table class="tableLayout">
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">回访分类</span>
					
				</td>
				<td>
					<div class="form-group">
						<select class="select2 dict" tig="HFFL" name="hffl" id="hffl" >
						</select>
					</div>
				</td>
				<td>
					<span class="commonText">回访角色</span>
					
				</td>
				<td>
					<div class="form-group">
						<select class="select2 priv" name="userpriv" id="userpriv" >
						</select>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">回访目的</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text"  name="purpose" id="purpose" placeholder="回访目的">
					</div>
				</td>
				<td>
					<span class="commonText">回访备注</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text"  name="remark" id="remark" placeholder="回访备注">
					</div>
				</td>
			</tr>
			
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">间隔天数</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text"  name="jgday" id="jgday" placeholder="间隔天数">
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
    intPrivSelectByClassWithCommon();
    var detailurl = '<%=contextPath%>/KQDS_visitSetAct/selectDetail.act?seqId=' + seqId;
    $.axse(detailurl, null,
    function(data) {
    	initDictSelectByClassOrg(data.data.organization);
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