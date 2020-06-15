<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<% 
	String seqId=request.getParameter( "seqId"); 
%>

<!-- 配置文件 -->
<script type="text/javascript" src="<%=contextPath%>/third-party/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="<%=contextPath%>/third-party/ueditor/ueditor.all.js"></script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="<%=contextPath%>/third-party/ueditor/lang/zh-cn/zh-cn.js"></script>
<!-- 实例化编辑器 -->

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>UNIC管理系统</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />	
<style>
	#content{
		margin:0 5px;
		border:1px solid #ddd;
		font-size:14px;
		padding:5px;
		overflow-y:auto;
	}
	#content>p{
		font-size:13px;
	}
</style>
</head>
<body>
<form class="form-horizontal" id="form1">
	<div class="box-body">
		<input type="hidden" class="form-control" name="seqId" id="seqId">
		<table class="tableLayout">
			<tr>	
				<td>
					<span class="commonText">标题<span style="color: red;">*</span></span>
				</td>
				<td>
					<div class="form-group">
						<input type="text"  name="title" id="title" placeholder="标题">
					</div>
				</td>
				<td>
					<span class="commonText">信息分类<span style="color: red;">*</span></span>
				</td>
				<td>
					<select class="select2 dict" tig="XXKFL" name="type" id="type">
					</select>
				</td>
			</tr>
			
		</table>
		<div name="content" id="content" style="">
					
		</div>
</form>
<script>
var ue = "";
var seqId = "<%=seqId%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
    // ue = UE.getEditor('content',{initialFrameHeight:ueHeight,initialFrameWidth:650});
    
    setHeight();
    var detailurl = '<%=contextPath%>/KQDS_InformationBackAct/selectDetail.act?seqId=' + seqId;
    $.axse(detailurl, null,
    function(data) {
    	initDictSelectByClassOrg(data.data.organization); // 传入门诊编号
    	
        loadData(data.data);
        // ue.ready(function() {//编辑器初始化完成再赋值  
        //   ue.setContent(data.data.content);  //赋值给UEditor  
        // });
        //$("#content").append(data.data.content);
    },
    function() {
        layer.alert('查询出错！' );
    });
    readonly();
});
function setHeight() {
    $("#content").outerHeight($(window).outerHeight() - 50);
}
</script>
</body>

</html>