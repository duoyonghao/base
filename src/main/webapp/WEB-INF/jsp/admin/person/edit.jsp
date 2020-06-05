<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	String personId = request.getParameter("personId");
	if (personId == null) {
		personId = "";
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
<title>编辑用户</title>
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
			<input type="hidden" name="userPrivOther" id="userPrivOther">
			<table class="tableContent"><!--tableContent的样式 -->
				<tbody>
					<tr>
						<td> 	<!--impText必填项样式  -->
							<span class="impText">用户名</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="userId" id="userId" readonly="readonly">
							</div>
						</td>
						<td>
							<span class="impText">真实姓名</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="userName" id="userName"></div>
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
								<select class="priv" multiple id="userPrivOtherEdit" name="userPrivOtherEdit">
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
						<td>
							<span class="comText">共用部门</span> <!-- 添加人员到东西院共用部门 -->
						</td>
						<td>
							<select class="selectpicker show-tick form-control"  id="xy_dept" name="xy_dept">
							</select>
						</td>
						<!-- <td>
							<span class="comText">所属部门</span>
						</td>
						<td>
							<select class="selectpicker show-tick form-control" multiple id="deptIdOther" name="deptIdOther">
							</select>
						</td> -->
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
					<tr>
						
					</tr>
				</tbody>
			</table>	
		</form>
		
		<div class="fixedBottomDiv"><!--底部三个按钮所在父元素 固定在底部样式  -->
			<div class="clear2"></div>
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
var static_personId = "<%=personId%>";
var static_deptid = null;
var static_detailurl = 'YZPersonAct/selectDetail.act?seqId=' + static_personId;
var static_deptdetailurl = 'YZDeptAct/selectDetail.act';
var static_userPriv = null;
var static_userPrivOther = null;
$(function() {
	//getSelectDeptTree("deptIdOther");
    getSelectDeptTree("deptId"); // 部门树下拉框
 /* getSlectUserPrivNoOrg("userPriv");
    getSlectUserPrivNoOrg("userPrivOtherAdd"); */
    getSelectDeptTree("xy_dept");

    // jQuery('#userPrivOtherEdit').selectpicker({
    //     title: "请选择"
    // });

    getDetail();

    $("#seqId").val(static_personId); // 主键
    
	// 根据deptid 选中弄下拉框
    $("#deptId").val(static_deptid);
    // 获取所选的部门，根据部门编号，下拉角色
    initSomeInfoByDetpId();
    
    $('#deptId').change(function() {
    	// 获取部门值
    	static_deptid = $(this).val();
    	$("#deptId").val(static_deptid);
    	$("#userPriv").empty(); // 一个清空
    	$('#userPrivOtherEdit').selectpicker('destroy'); // 一个销毁，这个不能清空
    	
    	initSomeInfoByDetpId();
	});
});

function initSomeInfoByDetpId(){
	if(static_deptid){
		var serverData = getDataFromServer(static_deptdetailurl + '?seqId=' + static_deptid);
	    if (serverData) {
	        var deptCode = serverData.retData.deptCode;
	        $("#userPrivOtherEdit").attr("org",deptCode);
	        $("#userPriv").attr("org",deptCode);
	    }
	    intPrivSelectByClassWithCommon(); // 角色下拉框
	    
	    if(static_userPriv){
	    	$("#userPriv").val(static_userPriv);
	    }
		if(static_userPrivOther){
			kqds_setMultiSelectVal("userPrivOtherEdit", static_userPrivOther);
	    }
	    
	}else{
		layer.alert('请先选择所属门诊/部门' );
	}
	jQuery('#userPrivOtherEdit').selectpicker({
        title: "请选择"
    });
}


function getDetail() {
    var serverData = getDataFromServer(static_detailurl);
    if (serverData) {
        loadData(serverData.retData);

        if ("1" == serverData.retData.canLookPhone) {
            $("#canLookPhone").attr("checked", "checked")
        } else {
            $("#canLookPhone").removeAttr("checked");
        }
        $("#canLookPhone").attr("value", serverData.retData.canLookPhone);

        if ("1" == serverData.retData.canKd) {
            $("#canKd").attr("checked", "checked")
        } else {
            $("#canKd").removeAttr("checked");
        }

        $("#canKd").attr("value", serverData.retData.canKd);

        if ("1" == serverData.retData.canHj) {
            $("#canHj").attr("checked", "checked")
        } else {
            $("#canHj").removeAttr("checked");
        }
        $("#canHj").attr("value", serverData.retData.canHj);

        if ("1" == serverData.retData.canEditKf) {
            $("#canEditKf").attr("checked", "checked")
        } else {
            $("#canEditKf").removeAttr("checked");
        }
        $("#canEditKf").attr("value", serverData.retData.canEditKf);

        if ("1" == serverData.retData.canOrderOther) {
            $("#canOrderOther").attr("checked", "checked")
        } else {
            $("#canOrderOther").removeAttr("checked");
        }
        $("#canOrderOther").attr("value", serverData.retData.canOrderOther);

        if ("1" == serverData.retData.canRoom) {
            $("#canRoom").attr("checked", "checked")
        } else {
            $("#canRoom").removeAttr("checked");
        }
        $("#canRoom").attr("value", serverData.retData.canRoom);

        if ("1" == serverData.retData.notLogin) {
            $("#notLogin").attr("checked", "checked")
        } else {
            $("#notLogin").removeAttr("checked");
        }
        $("#notLogin").attr("value", serverData.retData.notLogin);

        if ("1" == serverData.retData.iszj) {
            $("#iszj").attr("checked", "checked")
        } else {
            $("#iszj").removeAttr("checked");
        }
        $("#iszj").attr("value", serverData.retData.iszj);

        if ("1" == serverData.retData.canPaiban) {
            $("#canPaiban").attr("checked", "checked")
        } else {
            $("#canPaiban").removeAttr("checked");
        }
        $("#canPaiban").attr("value", serverData.retData.canPaiban);

        if ("1" == serverData.retData.isLeave) {
            $("#isLeave").attr("checked", "checked")
        } else {
            $("#isLeave").removeAttr("checked");
        }
        $("#isLeave").attr("value", serverData.retData.isLeave);

        if ("1" == serverData.retData.canEditWd) {
            $("#canEditWd").attr("checked", "checked")
        } else {
            $("#canEditWd").removeAttr("checked");
        }
        $("#canEditWd").attr("value", serverData.retData.canEditWd);

        if ("1" == serverData.retData.canEditCost) {
            $("#canEditCost").attr("checked", "checked")
        } else {
            $("#canEditCost").removeAttr("checked");
        }
        $("#canEditCost").attr("value", serverData.retData.canEditCost);

        if ("1" == serverData.retData.canEditOrder) {
            $("#canEditOrder").attr("checked", "checked")
        } else {
            $("#canEditOrder").removeAttr("checked");
        }
        $("#canEditOrder").attr("value", serverData.retData.canEditOrder);

        // 辅助角色赋值
        // kqds_setMultiSelectVal("userPrivOtherEdit", serverData.retData.userPrivOther);
        // 所属部门ID
        static_deptid = serverData.retData.deptId;
        //console.log(serverData,+"33");
        static_userPriv = serverData.retData.userPriv;
        static_userPrivOther = serverData.retData.userPrivOther;

        /* $('input:checkbox').each(function(i){
    		if("1" == $(this).val()){
    			$(this).attr("checked","checked")
    		}else{
    			$(this).removeAttr("checked");
    		}
    		
		}); */
    }
}

$('#submit').on('click',
function() {
    submitForm();
});

function submitForm() {
    var userId = $("#userId").val();
    var userName = $("#userName").val();
    var userPriv = $("#userPriv").val();
    var deptId = $("#deptId").val();
    var userNo = $("#userNo").val();
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
    
    var testz = /^\d+(\.\d+)?$/;
	if(!testz.test(userNo)){
		layer.alert('排序号需为整数' );
		return false;
	}

    // 这里这样做的目的是避免选中两个角色，比如5，6，后面调用form的serialize方法时，传值Url变成userPrivOther=5&userPrivOther=6这种情况
    $("#userPrivOther").val($("#userPrivOtherEdit").val());
    var param = $('#defaultForm').kqds_serialize();
    var url = 'YZPersonAct/insertOrUpdate.act?' + param;
    var serverData = getDataFromServer(url);
    if (serverData) {
        layer.alert('操作成功', {
              
            end: function() {
                if (parent.leftFrame) {
                    parent.leftFrame.refreshNode(static_deptid);
                }
            }
        });
    }
}
</script>
</html>
