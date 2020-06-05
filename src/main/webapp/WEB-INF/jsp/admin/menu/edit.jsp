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

	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<!-- 本页面已优化（输入框需要再次优化）鲍3-26 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>编辑主菜单</title>
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
			<table class="tableContent"><!--tableContent的样式 -->
				<tbody>
					<tr>
						<td> 	<!--impText必填项样式  -->
							<span class="impText">主菜单分类代码</span>
						</td>
						<td>
							<input style="width:60%"  type="text" name="menuId" id="menuId" size="2" maxlength="2">
							<span style="width:40%"  class="tomatoText">代码请尽量间隔开，2位数字</span>
						</td>
					</tr>
					<tr>
						<td>
							<span class="impText">菜单名称</span>
						</td>
						<td>
							<input style="width:60%"  type="text" name="menuName" id="menuName" >
						</td>
					</tr>
				</tbody>
			</table>	
		</form>
		<div class="fixedBottomDiv"><!--底部三个按钮所在父元素 固定在底部样式  -->
			<div class="clear2"></div>
         	<a class="kqdsCommonBtn" id="clean">清 空</a>
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
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds_person_select.js"></script>
<script type="text/Javascript">
var contextPath = "<%=contextPath%>";
var static_menuId = "<%=menuId%>";
var static_detailurl = 'YZMenuAct/selectDetailByMenuId.act?menuId=' + static_menuId;
var static_deleteurl = 'YZMenuAct/delete.act?1=1';
$(function() {
    getDetail();
});

function getDetail() {
    var serverData = getDataFromServer(static_detailurl);
    if (serverData) {
        loadData(serverData.retData);

        $("#seqId").val(serverData.retData.seqId);

    }
}

$('#clean').on('click',
function() {
    $("#defaultForm :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});

$('#submit').on('click',
function() {
    submitForm();
});

function submitForm() {
    var menuId = $("#menuId").val();
    var menuName = $("#menuName").val();

    if (menuId == "") {
        layer.alert('主菜单分类代码不能为空' );
        return false;
    }

    if (!/[0-9]{2}/.test(menuId)) {
        layer.alert('主菜单分类代码必须为2位数字！' );
        return false;
    }

    if (menuId == "") {
        layer.alert('菜单名称不能为空' );
        return false;
    }

    // 赋值
    var param = $('#defaultForm').kqds_serialize();

    var url = 'YZMenuAct/insertOrUpdateMenu.act?' + param;
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

//新建同级
$('#newMenu').on('click',
function() {
    window.location.href = "<%=contextPath%>/YZMenuAct/toNewMenu.act";
});

// 新建下一级
$('#newSubMenu').on('click',
function() {
    var menuId = $("#menuId").val();
    var menuName = $("#menuName").val();
    window.location.href = "<%=contextPath%>/YZMenuAct/toNewFunc.act?parentMenuId=" + menuId + "&parentMenuName=" + menuName;
});

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
                    window.location.href = contextPath + "/YZMenuAct/toNewMenu.act";
                    if (parent.leftFrame) {
                        parent.leftFrame.refresh4Menu();
                    }
                }
            });
        }
    });
});
</script>
</html>
