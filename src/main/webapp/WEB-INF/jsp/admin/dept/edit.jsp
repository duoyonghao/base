<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	String deptId = request.getParameter("deptId");
	if (deptId == null) {
		deptId = "";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<!-- 本页面已优化（输入框需要再次优化）鲍3-26 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>编辑部门</title>
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
						
						<!-- <td>
							<span class="comText">排序号</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="deptNo" id="deptNo" value="0"></div>
						</td> -->
						<td>	<!--impText必填项样式  -->
							<span class="impText">上级部门</span>
						</td>
						<td>
							<div class="form-group">
								<select class="select2" id="deptParent" name="deptParent" disabled="disabled">
								</select>
							</div>	
						</td>
					</tr>
					<tr>
						<td>
							<span class="impText">部门编号</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="deptCode" id="deptCode" readonly="readonly" value=""></div>
						</td>
						
						<td>	
							<span class="comText">部门类型</span>
						</td>
						<td>
							<!-- supportRole 框架生成 与辅助角色 一样的下拉菜单样式 使用同样式 -->
							<div class="form-group"  id="supportRole">
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
						<td>
							<span class="impText">是否门诊</span>
						</td>
						<td>
							<input type="radio" name="orgflag" id="orgflag0" value="0" checked="checked"><label for="orgflag0">否</label>&nbsp;
							<input type="radio" name="orgflag" id="orgflag1" value="1"><label for="orgflag1">是</label>
						</td>
						<td>
							 <span class="impText">是否回访部门</span>
						</td>
						<td>
							<div class="form-group">
							<input type="radio" name="isvisit" id="isvisit" value="0" checked="checked"><label for="isvisit">否</label>&nbsp;
					        <input type="radio" name="isvisit" id="isvisit" value="1"><label for="isvisit">是</label>
					        </div>
						</td>
					</tr>
					
					<tr>
						<td>
							<span class="impText">是否多出行医</span>
						</td>
						<td>
							<div class="form-group">
								<input type="radio" name="ismore" id="ismore" value="0" checked="checked"><label for="ismore">否</label>&nbsp;
						        <input type="radio" name="ismore" id="ismore" value="1"><label for="ismore">是</label>
					        </div>
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
								<!-- <input type="text" name="managerInput" id="managerInput" readonly="readonly" onclick="showPersonTree('managerInput','managerTree','manager')" >
								<input type="hidden" name="manager" id="manager" value=""> -->
								<input type="hidden" name="manager" id="manager" value=""/>
								<textarea rows=""  style="" name="managerDesc" id="managerDesc" readonly 
									onClick="javascript:multi_select_user(['manager', 'managerDesc']);"></textarea>
							</div>
						</td>
					</tr>
				</tbody>
			</table>	
		</form>
		<div class="fixedBottomDiv"><!--底部三个按钮所在父元素 固定在底部样式  -->
			<div class="clear2"></div>
         	<a class="kqdsCommonBtn" id="submit">确 定</a>
         	<a class="kqdsCommonBtn" id="newDept">新建部门</a>
         	<a class="kqdsCommonBtn" id="newSubDept">新建下级部门</a>
         	<a class="kqdsCommonBtn" id="delete">删除</a>
		</div>
		<div id="managerTree" class="nohc-scroll-webkit ztree" style="width:200px;height:300px;display:none;position: absolute;overflow-y: auto;"></div>
		
		
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
var static_deptId = "<%=deptId%>";
var static_deptParent = null;
var static_detailurl = 'YZDeptAct/selectDetail.act?seqId=' + static_deptId;
var static_deleteurl = 'YZDeptAct/delete.act?1=1';
$(function() {
    getSelectDeptTree4Manage("deptParent"); // 部门树下拉框
    jQuery('#deptTypeSelect').selectpicker({
        title: "请选择"
    });

    // 初始化人员树-多选
    // initPersonTreeMulti('managerTree', manager_personTree_oncheck, 'managerInput');

    getDetail();

    $("#seqId").val(static_deptId); // 主键
});

/**
 * 部门管理员，点击赋值方法 ### 多选
 */
function manager_personTree_oncheck(e, treeId, treeNode) {
    var dataObj = getCheck4PersonTreeMulti(treeId);
    $("#managerInput").val(dataObj.selectName);
    $("#manager").val(dataObj.selectId);
}

function getDetail() {
    var serverData = getDataFromServer(static_detailurl);
    if (serverData) {
        loadData(serverData.retData);

        // 辅助角色赋值
        kqds_setMultiSelectVal("deptTypeSelect", serverData.retData.deptType);
        // 上级部门seqId
        static_deptParent = serverData.retData.deptParent;

        if ($("#manager").val() != "") {
            bindPerUserNameBySeqIdTB("managerDesc", $("#manager").val());
        }
       /*  if (static_deptParent == "0") { // 一级部门，可以修改部门编号
            $("#deptCode").removeAttr("readonly");
        } else {
            $("#deptCode").attr("readonly", "readonly");
        } */

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
                        parent.leftFrame.refreshNode(static_deptParent);
                    }
                }
            });
        }
    });
});

// 新建同级部门
$('#newDept').on('click',
function() {
	var seqId = $("#deptParent").val();
    var deptCode = $("#deptCode").val();
    var url = contextPath +"/YZDeptAct/toNewAdd.act?deptParent=" + seqId;
    if(seqId != '' && seqId != '0'){
    	url += "&deptCode=" + deptCode;
    }
    window.location.href = url;
});

// 新建下一级部门
$('#newSubDept').on('click',
function() {
    var seqId = $("#seqId").val();
    var deptCode = $("#deptCode").val();
    window.location.href = contextPath +"/YZDeptAct/toNewAdd.act?deptParent=" + seqId + "&deptCode=" + deptCode;
});

$('#submit').on('click',
function() {
    submitForm();
});

function submitForm() {

    var seqId = $("#seqId").val();
    var deptName = $("#deptName").val();
    var deptCode = $("#deptCode").val();

    if (seqId == "") {
        layer.alert('主键为空，请联系系统管理员' );
        return false;
    }

    if (deptName == "") {
        layer.alert('部门名称不能为空' );
        return false;
    }

    if (deptCode == "") {
        layer.alert('部门编号不能为空' );
        return false;
    }

    // 赋值
    $("#deptType").val($("#deptTypeSelect").val());

    var param = $('#defaultForm').kqds_serialize();

    var url = 'YZDeptAct/insertOrUpdate.act?' + param;
    var serverData = getDataFromServer(url);
    if (serverData) {
        layer.alert('操作成功', {
              
            end: function() {
                if (parent.leftFrame) {
                    parent.leftFrame.refreshNode(static_deptParent);
                }
            }
        });
    }
}
</script>
</html>
