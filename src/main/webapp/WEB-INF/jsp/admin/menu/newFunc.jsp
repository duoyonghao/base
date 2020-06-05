<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	String parentMenuId = request.getParameter("parentMenuId");
	if (parentMenuId == null) {
		parentMenuId = "";
	}

	String parentMenuName = request.getParameter("parentMenuName");
	if (parentMenuName == null) {
		parentMenuName = "";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<!-- 本页面已优化（输入框需要再次优化）鲍3-26 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>新建下级菜单</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" rel="stylesheet" >
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/admin.css">
</head>
<body>
	
	<div class="container"><!--提供内边距 让内容不顶头显示-->
		<form id="defaultForm"><!-- 本身无样式  defaultForm表单验证功能要用 -->
			<input type="hidden" name="seqId" id="seqId">
			<input type="hidden" name="parentMenuId" id="parentMenuId">
			<table class="tableContent"><!--tableContent的样式 -->
				<tbody>
					<tr>
						<td> 	<!--impText必填项样式  -->
							<span class="impText">上级菜单</span>
						</td>
						<td>
							<div class="form-group">
								<select class="select2" id="menuParent" name="menuParent" >
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<span class="impText">子菜单代码项</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="menuCode" id="menuCode"  value="" size="2" maxlength="2"></div>
						</td>
					</tr>
					<tr>
						<td>
							<span class="impText">子菜单名称</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="funcName" id=""funcName""  value=""></div>
						</td>
					</tr>
					<tr>
						<td>
							<span class="impText">菜单路径</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="funcCode" id=""funcCode""  value=""></div>
						</td>
					</tr>
					<tr>
						<td>
							<span class="comText">排序号</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="orderno" id="orderno"  value=""></div>
						</td>
					</tr>
					<tr>
						<td>
							<span class="comText">图片名</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="icon" id="icon"  value=""></div>
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
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
<script type="text/Javascript">
var contextPath = "<%=contextPath%>";
var static_parentMenuId = "<%=parentMenuId%>";
var static_parentMenuName = "<%=parentMenuName%>";
$(function() {
    getSelectMenuTree4Manage("menuParent"); // 部门树下拉框
    // 根据deptid 选中弄下拉框
    $("#menuParent").val(static_parentMenuId);

});

$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});

$('#submit').on('click',
function() {
    submitForm();
});

function submitForm() {
    var menuCode = $("#menuCode").val();
    var funcName = $("#funcName").val();
    var funcCode = $("#funcCode").val();

    if (menuCode == "") {
        layer.alert('子菜单代码不能为空' );
        return false;
    }

    if (!/[0-9]{2}/.test(menuCode)) {
        layer.alert('子菜单代码必须为2位数字！' );
        return false;
    }

    if (funcName == "") {
        layer.alert('菜单名称不能为空' );
        return false;
    }

    if (funcCode == "") {
        layer.alert('菜单路径不能为空' );
        return false;
    }

    // 赋值
    var param = $('#defaultForm').kqds_serialize();

    var url = 'YZFuncAct/insertOrUpdate.act?' + param;
    var serverData = getDataFromServer(url);
    if (serverData) {
        layer.alert('操作成功', {
              
            end: function() {
                if (parent.leftFrame) {
                    parent.leftFrame.refresh4Menu();
                }
                window.location.reload();
            }
        });
    }
}
</script>
</html>
