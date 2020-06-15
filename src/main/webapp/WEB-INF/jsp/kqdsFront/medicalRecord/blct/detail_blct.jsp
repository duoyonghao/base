<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	String seqId = request.getParameter("seqId");
	if (seqId == null) {
		seqId = "";
	}
	String organization = request.getParameter("organization");
	if(organization == null){
		organization = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>新增词条</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />	
</head>

<body>
<form class="container" class="form-horizontal" id="form1">
	<input type="hidden" class="form-control" name="seqId" id="seqId" value="<%=seqId%>">
	<table class="tableLayout">
		<tr>
			<td>
				<span class="commonText">一级分类<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div class="form-group">
					<select name="cttype" id="cttype" class="dict" tig="blct124">
					</select>
				</div>
			</td>
			<td>
				<span class="commonText">二级分类<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div class="form-group">
					<select name="ctnexttype" id="ctnexttype" >
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<span class="impText">排序号</span>
			</td>
			<td>
				<div class="form-group">
					<input type="text" name="orderno" id="orderno" value="0"></div>
			</td>
			<td>
				<span class="impText">是否禁用</span>
			</td>
			<td>
				<div class="form-group">
					<select name="flag" id="flag" style="width: 100%;">
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</div>
			</td>
		</tr>
		<tr class="textareaTr">
			<td>
				<span class="commonText longInp">词条内容<span style="color: red;">*</span></span>
				
			</td>
			<td colspan="3">
				<div class="form-group">
					<textarea rows="2" class="longTextarea" name="ctname" id="ctname" ></textarea>
				</div>
			</td>
		</tr>
	</table>
</form>

<script>
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var seqId = "<%=seqId%>";
var organization= "<%=organization%>";
$(function() {
	initDictSelectByClassOrg(organization);
    var detailurl = '<%=contextPath%>/YZBlctAct/selectDetail.act?seqId=' + seqId;
    $.axse(detailurl, null,
    function(data) {
        var cttype = data.data.cttype;
        var ctnexttype = data.data.ctnexttype;
        $('#cttype').val(cttype).trigger('change');
        $('#ctnexttype').val(ctnexttype);
        loadData(data.data);
    },
    function() {
        layer.alert('查询出错！');
    });
    readonly();
});
$('#cttype').change(function() {
    if ($(this).val()) { // 如果一级有值，再请求
        getSubDictSelectByParentCode(this.value,'ctnexttype');
    }
});
</script>



</body>
</html>