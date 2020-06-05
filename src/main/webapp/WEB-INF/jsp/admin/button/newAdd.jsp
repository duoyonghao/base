<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	String menuId = request.getParameter("menuId");
	if (menuId == null) {
		menuId = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/button/buttonNewAdd.css">

<title>新建按钮</title>
</head>
<body>
<div class="container"><!--提供内边距 让内容不顶头显示-->
	<form id="defaultForm"><!-- 本身无样式  defaultForm表单验证功能要用 -->
		<input type="hidden" name="parentid" id="parentid">
		<table style="border:none;" class="tableContent"><!--tableContent的样式 -->
			<tbody>
				<tr>
					<td> 	<!--impText必填项样式  -->
						<span class="impText">按钮名称</span>
					</td>
					<td>
						<div class="form-group">
							<input type="text" name="name" id="name">
						</div>
					</td>
				</tr>
				<tr>
					<td> 	<!--impText必填项样式  -->
						<span class="impText">权限标识</span>
					</td>
					<td>
						<div class="form-group">
							<input type="text" name="qxName" id="qxName">
						</div>
					</td>
				</tr>
				<tr>
					<td> 	
						<span class="comText">排序号</span>
					</td>
					<td>
						<div class="form-group">
							<input type="text" name="sortno" id="sortno">
						</div>
					</td>
				</tr>
				<tr>
					<td> 	<!--impText必填项样式  -->
						<span class="comText">备注</span>
					</td>
					<td>
						<div class="form-group">
							<input type="text" name="bz" id="bz">
						</div>
					</td>
				</tr>
			</tbody>
		</table>	
	</form>
	
	<div class="fixedBottomDiv"><!--底部三个按钮所在父元素 固定在底部样式  -->
		<div class="clear2"></div>
        	<a class="kqdsCommonBtn" id="clean">清 空</a>
        	<a class="kqdsCommonBtn" id="submit">确 定</a>
	</div>
</div>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
<script>
var static_menuid = "<%=menuId %>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
    $("#parentid").val(static_menuid);
});
$('#clean').on('click',
function() {
    $("#defaultForm :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});
$('#submit').on('click',
function() {
    submitForm();
});

function submitForm() {
    var name = $("#name").val();
    var qxName = $("#qxName").val();

    if (name == "") {
        layer.alert('按钮名称不能为空' );
        return false;
    }

    if (qxName == "") {
        layer.alert('权限标识不能为空' );
        return false;
    }
    var param = $('#defaultForm').kqds_serialize();
    var url = 'YZButtonAct/insertOrUpdate.act?' + param;
    var serverData = getDataFromServer(url);
    if (serverData) {
        layer.alert('操作成功', {
              
            end: function() {
                parent.refresh();
                parent.layer.close(frameindex);

            }
        });
    }
}
</script>
</body>
</html>
