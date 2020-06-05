<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	String funcId = request.getParameter("funcId");
	if (funcId == null) {
		funcId = "";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<!-- 本页面已优化（输入框需要再次优化）鲍3-26 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>编辑下级菜单</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" rel="stylesheet" >
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/menu/editMenu.css">
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
								<select class="select2" id="menuParent" name="menuParent" disabled="disabled">
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
								<input type="text" name="menuCode" id="menuCode" size="2" maxlength="2" disabled="disabled"></div>
						</td>
					</tr>
					<tr>
						<td>
							<span class="impText">子菜单名称</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="funcName" id=""funcName"" ></div>
						</td>
					</tr>
					<tr>
						<td>
							<span class="impText">菜单路径</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="funcCode" id="funcCode" funcCode""></div>
						</td>
					</tr>
					<tr>
						<td>
							<span class="comText">排序号</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="orderno" id="orderno"></div>
						</td>
					</tr>
					<tr>
						<td>
							<span class="comText">图片名</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="icon" id="icon"></div>
						</td>
					</tr>
				</tbody>
			</table>	
		</form>
		<div class="fixedBottomDiv"><!--底部三个按钮所在父元素 固定在底部样式  -->
			<div class="clear2"></div>
         	<a class="kqdsCommonBtn" id="submit">确 定</a>
         	<a class="kqdsCommonBtn" id="newMenu">新建菜单</a>
         	<a class="kqdsCommonBtn" id="newSubMenu">新建下级菜单</a>
         	<a class="kqdsCommonBtn" id="delete">删除</a>
		</div>
		<div id="managerTree" class="nohc-scroll-webkit ztree" style="width:200px;height:300px; display:none;position: absolute;overflow-y: auto;"/>
		
		
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
var static_funcMenuId = "<%=funcId%>";
var static_parentId = null;
var static_detailurl = 'YZFuncAct/selectDetailByMenuId.act?menuId=' + static_funcMenuId;
var static_deleteurl = 'YZFuncAct/delete.act?1=1';
$(function() {
    getSelectMenuTree4Manage("menuParent"); // 部门树下拉框
    getDetail();
});

function getDetail() {
    var serverData = getDataFromServer(static_detailurl);
    if (serverData) {
        loadData(serverData.retData);

        $("#seqId").val(serverData.retData.seqId);
        // 根据deptid 选中弄下拉框
        static_parentId = static_funcMenuId.substring(0, static_funcMenuId.length - 2);
        var menuCode = static_funcMenuId.substring(static_funcMenuId.length - 2, static_funcMenuId.length);
        $("#menuParent").val(static_parentId);
        $("#menuCode").val(menuCode);

    }
}


$('#submit').on('click',
function() {
    submitForm();
});

function submitForm() {
    var menuCode = $("#menuCode").val();
    var funcName = $("#funcName").val();
    var funcCode = $("#funcCode").val();

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

$('#delete').on('click',
function() {
    var seqId = $("#seqId").val();
    //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var reqUrl = static_deleteurl + "&seqId=" + seqId;
        var serverData = getDataFromServer(reqUrl);
        if (serverData) {
            layer.alert('删除成功', {
                  
                end: function() {
                    window.location.href = contextPath + "/YZDeptAct/toSuccess.act";
                    if (parent.leftFrame) {
                        parent.leftFrame.refresh4Menu();
                    }
                }
            });
        }
    });
});

//新建同级
$('#newMenu').on('click',
function() {
    window.location.href = contextPath+"/YZMenuAct/toNewFunc.act?parentMenuId=" + static_parentId;
});

// 新建下一级
$('#newSubMenu').on('click',
function() {
    window.location.href = contextPath+"/YZMenuAct/toNewFunc.act?parentMenuId=" + static_funcMenuId;
});
</script>
</html>
