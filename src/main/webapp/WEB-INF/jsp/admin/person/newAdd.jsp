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
	
	/**
	 * 是否开启电话录音  0 关闭 1开启
	 */
	String is_open_record_func = YZSysProps.getProp(SysParaUtil.IS_OPEN_RECORD_FUNC);
	if(is_open_record_func == null){
		is_open_record_func = "";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<!-- 本页面已优化（输入框需要再次优化）鲍3-26 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>新建用户</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" rel="stylesheet" >
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/admin.css">
<style>
	input[type="password"]{
		height:28px;
		border:1px solid #ddd;
	}
	label{
		margin-bottom:0;
	}
</style>
</head>
<body>
	
	<div class="container"><!--提供内边距 让内容不顶头显示-->
		<form id="defaultForm"><!-- 本身无样式  defaultForm表单验证功能要用 -->
			<input type="hidden" name="seqId" id="seqId">
			<input type="hidden" name="userPrivOther" id="userPrivOther">
			<table class="tableContent"><!--tableContent的样式 -->
				<tbody>
					<tr>
						<td> 	<!--impText必填项样式  -->
							<span class="impText">用户名</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="userId" id="userId">
							</div>
						</td>
						<td>
							<span class="impText">真实姓名</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="userName" id="userName">
							</div>
						</td>
					</tr>
					<tr>
						<td>	<!--impText必填项样式  -->
							<span class="impText">主角色</span>
						</td>
						<td>
							<div class="form-group">
								<select class="select2 priv" id="userPriv" name="userPriv">	<!--.sel_short_inp_long select与input组合时的样式  -->
								</select>
							</div>	
						</td>
						<td>	
							<span class="comText">辅助角色</span>
						</td>
						<td>
							<div class="form-group" id="supportRole">
								<select class="priv" multiple id="userPrivOtherAdd" name="userPrivOtherAdd">
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<span class="impText">部门</span>
						</td>
						<td>
						  	<div class="form-group">
								<select class="select2" id="deptId" name="deptId">
								</select>
							</div>
						</td>
						<!-- 
						<td>
							<span class="comText">所属部门</span>
						</td>
						<td>
							<select class="selectpicker show-tick form-control" multiple id="deptIdOther" name="deptIdOther">
							</select>
						</td> -->
					</tr>
					<tr>
						<td> 	<!--impText必填项样式  -->
							<span class="impText">密码</span>
						</td>
						<td colspan="3">
							<div class="form-group">
								<input type="password" name="password" id="password"  value="123456" readonly = "readonly">
								<span class="promptText" ></span>
							</div>
						</td>
					</tr>
					<tr>
						<td> 	<!--impText必填项样式  -->
							<span class="impText">排序号</span>
						</td>
						<td colspan="3">
							<div class="form-group">
								<input type="text" name="userNo" id="userNo" value="0">
								<span class="promptText">相同部门的用户，根据排序号升序排序</span>
							</div>
						</td>
					</tr>
					
					<%
						if("1".equals(is_open_record_func)){
							%>
							<tr>
								<td> 	<!--impText必填项样式  -->
									<span class="comText">录音账号</span>
								</td>
								<td>
									<div class="form-group">
										<input type="text" name="recordaccount" id="recordaccount" value="">
									</div>
								</td>
								<td> 	<!--impText必填项样式  -->
									<span class="comText">录音密码</span>
								</td>
								<td>
									<div class="form-group">
										<input type="text" name="recordpwd" id="recordpwd" value="">
									</div>
								</td>
							</tr>
							<%
						}
					%>
					
					<tr>
						<td>
							<span class="comText">权限控制</span>
						</td>
						<td colspan="3">
							<input type="checkbox" name="notLogin" id="notLogin" value="0"  onclick="this.value=(this.value==0)?1:0"><label class="powerText" for="notLogin">禁止登录本系统</label>&nbsp;
							<input type="checkbox" name="isLeave" id="isLeave" value="0"  onclick="this.value=(this.value==0)?1:0"><label class="powerText" for="isLeave">已离职</label>
						</td>
					</tr>
				</tbody>
			</table>	
		</form>
		
		<div class="fixedBottomDiv"><!--底部三个按钮所在父元素 固定在底部样式  -->
			<div class="clear2"></div>
         	<a class="kqdsCommonBtn" id="clean">清 空</a>
         	<a class="kqdsCommonBtn" id="close">取 消</a>
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
var static_deptid = "<%=deptIdPage%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var static_deptdetailurl = 'YZDeptAct/selectDetail.act';
$(function() {
    getSelectDeptTree("deptId"); // 部门树下拉框
 /* getSlectUserPrivNoOrg("userPriv");
    getSlectUserPrivNoOrg("userPrivOtherAdd"); */
    getSelectDeptTree("deptIdOther");

    // 根据deptid 选中弄下拉框
    $("#deptId").val(static_deptid);
    // 获取所选的部门，根据部门编号，下拉角色
    initSomeInfoByDetpId();
    
    var maxorderno = getPersonMaxOrderno($("#deptId").val());
	$("#userNo").val(Number(maxorderno)+1);
    
    $('#deptId').change(function() {
    	// 获取部门值
    	static_deptid = $(this).val();
    	$("#deptId").val(static_deptid);
    	$("#userPriv").empty(); // 一个清空
    	$('#userPrivOtherAdd').selectpicker('destroy'); // 一个销毁，这个不能清空
    	
    	initSomeInfoByDetpId();
	});
});


function initSomeInfoByDetpId(){
	if(static_deptid){
		var serverData = getDataFromServer(static_deptdetailurl + '?seqId=' + static_deptid);
	    if (serverData) {
	        var deptCode = serverData.retData.deptCode;
	        $("#userPrivOtherAdd").attr("org",deptCode);
	        $("#userPriv").attr("org",deptCode);
	    }
	    intPrivSelectByClassWithCommon(); // 角色下拉框
	}/* else{
		layer.alert('请先选择所属门诊/部门' );
	} */
	jQuery('#userPrivOtherAdd').selectpicker({
        title: "请选择"
    });
}

$('#clean').on('click',
function() {
    $("#defaultForm :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});
$('#close').on('click',
function() {
	 parent.layer.close(frameindex); //再执行关闭 */
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
    var userNo = $("#userNo").val();
    var moreLand = $("#moreLand").val();

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
    
    
    var testz = /^\d+(\.\d+)?$/;
	if(!testz.test(userNo)){
		layer.alert('排序号需为整数' );
		return false;
	}

    // 这里这样做的目的是避免选中两个角色，比如5，6，后面调用form的serialize方法时，传值Url变成userPrivOther=5&userPrivOther=6这种情况
    $("#userPrivOther").val($("#userPrivOtherAdd").val());
    var param = $('#defaultForm').kqds_serialize();

    var url = 'YZPersonAct/insertOrUpdate.act?' + param;
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
<%-- 
function zTreeInit() {
    //异步加载
    var url = '<%=contextPath %>/YZPersonAct/getPersonTree.act';
    var setting = {
        view: {
            showIcon: true // 去掉图标
        },
        async: {
            enable: true,
            url: url,
            autoParam: ["id", "name=n", "level=lv"],
            otherParam: {
                "otherParam": "zTreeAsyncTest"
            },
            dataFilter: ajaxDataFilter,
            type: "get"
        }
    };
    setting['callback'] = {
        onClick: onclick
    };
    $.fn.zTree.init($("#deptTree"), setting);
}

//解析树 返回数据
function ajaxDataFilter(treeId, parentNode, responseData) {
    var tree;
    if (responseData.retState == "0") {
        tree = responseData.retData;
    }
    return tree;
};

function onclick(e, treeId, treeNode) {
	var id = treeNode.id;
	console.log(treeNode.id);
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj("deptTree");
        zTree.expandNode(treeNode);
    } else {
    	// 什么都不做
    }
    hideMenu();
}

function showDeptTree() {  
    var personDeptObj = $("#personDept");  
    var personDeptOffset = $("#personDept").offset();  
    $("#deptTreeContent").css({  
        left : personDeptOffset.left + "px",  
        top : personDeptOffset.top + personDeptObj.outerHeight() + "px"  
    }).slideDown("fast");  

    $("body").bind("mousedown", onBodyDown);  
}  
function hideMenu() {  
    $("#deptTreeContent").fadeOut("fast");  
    $("body").unbind("mousedown", onBodyDown);  
}  
function onBodyDown(event) {  
    if (!(event.target.id == "deptTreeContent" || $(event.target)  
            .parents("#deptTreeContent").length > 0)) {  
        hideMenu();  
    }  
}   --%>
</script>


</html>
