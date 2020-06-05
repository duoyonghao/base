<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	String deptIdPage = request.getParameter("deptId");
	if (deptIdPage == null) {
		deptIdPage = "";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<!-- 本页面已优化（输入框需要再次优化）鲍3-26 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>新建部门</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/bootstrap-treeview.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" rel="stylesheet" >
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/admin.css">
</head>
<body>
	
	<div class="container"><!--提供内边距 让内容不顶头显示-->
		<form id="defaultForm"><!-- 本身无样式  defaultForm表单验证功能要用 -->
			<input type="hidden" name="seqId" id="seqId">
			<input type="hidden" name="deptType" id="deptType">
			<table class="tableContent"><!--tableContent的样式 -->
				<tbody>
					<tr>
						<td> 	<!--impText必填项样式  -->
							<span class="impText">部门名称</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="deptName" id="deptName">
							</div>
						</td>
						
						<td>
							<span class="comText">排序号</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="deptNo" id="deptNo"></div>
						</td>
					</tr>
					<tr>
						<td>
							<span class="impText">部门编号</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="deptCode" id="deptCode"></div>
						</td>
						
						<td>	
							<span class="comText">部门类型</span>
						</td>
						<td>
							<div class="form-group">
								<select multiple id="deptTypeSelect" name="deptTypeSelect">
								<option  value="">请选择</option>
								      <option value="0" >咨询</option>
								      <option value="1" >医生</option>
								      <option value="2" >网电</option>
								      <option value="3" >营销</option>
								      <option value="6" >客服</option><!-- 20180316 add -->
								      <option value="4" >护士</option>
								      <option value="5" >业绩科室</option>
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td>	<!--impText必填项样式  -->
							<span class="impText">上级部门</span>
						</td>
						<td>
							<div class="form-group">
								<select class="select2" id="deptParent" name="deptParent">
								</select>
							</div>	
						</td>
						<td>
							<span class="impText">是否门诊</span>
						</td>
						<td>
							<input type="radio" name="orgflag" id="orgflag0" value="0" checked="checked"><label for="orgflag0">否</label>&nbsp;
							<input type="radio" name="orgflag" id="orgflag1" value="1"><label for="orgflag1">是</label>
						</td>
					</tr>
					<tr>
						<td>
							<span class="comText">部门简称</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="deptByname" id="deptByname"></div>
						</td>
						<td>
							<span class="comText">电话</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="telNo" id="telNo"></div>
						</td>
					</tr>
					<tr>
						<td>
							<span class="comText">打印抬头</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="printName" id="printName"></div>
						</td>
						<td>
							<span class="comText">地址</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="deptAddress" id="deptAddress"></div>
						</td>
					</tr>
					<tr>
						<td>
							<span class="comText">部门管理员</span>
						</td>
						<td colspan="3">
							<div class="form-group">
								<input type="text" name="manager" id="manager" onclick="showPersonTree('manager','managerTree')">
							</div>
						</td>
					</tr>
				</tbody>
			</table>	
		</form>
		
		<div id="managerTree" class="nohc-scroll-webkit" style="width:200px;height:150px;display:none;position: absolute;overflow-y: auto;"/>
		
		<div class="fixedBottomDiv"><!--底部三个按钮所在父元素 固定在底部样式  -->
			<div class="clear2"></div>
         	<a class="kqdsCommonBtn" id="clean">清 空</a>
         	<a class="kqdsCommonBtn" id="submit">确 定</a>
         	<a class="kqdsCommonBtn" id="close">取 消</a>
		</div>
	</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/bootstrap-treeview.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
<script type="text/Javascript">
var contextPath = "<%=contextPath%>";
var static_deptid = "<%=deptIdPage%>";
$(function() {
    getSelectDeptTree("deptParent"); // 部门树下拉框
    getSlectUserPriv("userPriv"); // 角色下拉框
    getSlectUserPriv("userPrivOtherAdd");
    getSelectDeptTree("deptIdOther");

    jQuery('#deptTypeSelect').selectpicker({
        title: "请选择"
    });
    // 根据deptid 选中弄下拉框
    $("#deptId").val(static_deptid);

    // 初始化人员树
    initPersonTree('managerTree');
});

/**
 * 获取人员树数据
 */
function getPersonTreeData() {
    var data = [{
        text: "p1",
        nodes: [{
            text: "p1-1",
            id: '00001',
            nodeId: '00001'
        },
        {
            text: "p1-2",
            id: '00002'
        },
        {
            text: "p1-3",
            id: '00003'
        },
        {
            text: "p1-4",
            id: '00004',
            nodes: [{
                text: 'p1-1-1',
                id: '00005'
            }]
        }]
    }]
    return data;
}

/**
 * 初始化人员树
 */
function initPersonTree(id) {
    $('#' + id).treeview({
        data: getPersonTreeData(),
        // data is not optional
        levels: 5,
        multiSelect: true
    });
}

/**
 * 显示人员树
 */
function showPersonTree(selectId, treeId) {
    var selectObj = $("#" + selectId);
    var selectOffset = $("#" + selectId).offset();
    $("#" + treeId).css({
        left: selectOffset.left + "px",
        top: selectOffset.top + selectObj.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", treeId, onBodyDown);
}

function hideMenu(treeId) {
    $("#" + treeId).fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
    var treeId = event.data;
    if (! (event.target.id == treeId || $(event.target).parents("#" + treeId).length > 0)) {
        hideMenu(treeId);
    }
}

$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});

$('#submit').on('click',
function() {
    submitForm();
});

function submitForm() {
    var userId = $("#userId").val();
    var userName = $("#userName").val();
    var userPriv = $("#userPriv").val();
    var deptId = $("#deptId").val();
    var password = $("#password").val();

    if (userId == "") {
        layer.alert('用户名不能为空' );
        return false;
    }

    if (userName == "") {
        layer.alert('姓名不能为空' );
        return false;
    }

    if (userPriv == "") {
        layer.alert('主角色不能为空' );
        return false;
    }

    if (userPriv == "") {
        layer.alert('部门不能为空' );
        return false;
    }

    if (password == "") {
        layer.alert('密码不能为空' );
        return false;
    }

    // 这里这样做的目的是避免选中两个角色，比如5，6，后面调用form的serialize方法时，传值Url变成userPrivOther=5&userPrivOther=6这种情况
    $("#userPrivOther").val($("#userPrivOtherAdd").val());
    var param = $('#defaultForm').kqds_serialize();

    var url = 'YZDeptAct/insertOrUpdate.act?' + param;
    var serverData = getDataFromServer(url);
    if (serverData) {
        layer.alert('操作成功', {
              
            end: function() {
                parent.refresh();
                parent.layer.close(frameindex); //再执行关闭 */
            }
        });
    }
}
</script>
</html>
