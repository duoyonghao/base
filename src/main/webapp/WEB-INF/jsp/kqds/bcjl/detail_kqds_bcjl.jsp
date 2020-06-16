<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<% 
	String seqId=request.getParameter( "seqId"); 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>日志详情</title>
</head>
<body>
<form class="form-horizontal" id="form1">
	<div class="box-body">
		<input type="hidden" class="form-control" name="seqId" id="seqId">
		<div class="form-group">
			<label for="content" class="col-sm-2 control-label"> 内容 </label>
			<div class="col-sm-10">
				<textarea class="form-control" name="content" id="content"
					rows="10" placeholder=""></textarea>
			</div>
		</div>
	</div>
</form>
<script>
var seqId = "<%=seqId%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
    var detailurl = '<%=contextPath%>/KQDS_BCJLBackAct/selectDetail.act?seqId=' + seqId;
    $.axse(detailurl, null,
    function(data) {
        var content = data.data.content;
        content = content.replace(/;|\;/g, '\n');
        $("#content").val(content);
    },
    function() {
        layer.alert('查询出错！' );
    });
    readonly();
});
</script>
</body>

</html>