<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	String deptParentPage = request.getParameter("deptParent");
	if (deptParentPage == null) {
		deptParentPage = "";
	}

	String deptCodePage = request.getParameter("deptCode"); // 部门编号
	if (deptCodePage == null) {
		deptCodePage = "";
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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" rel="stylesheet" >
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/admin.css">
</head>
<body>
	<style>

</style>
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
								<select class="select2" id="deptParent" name="deptParent" onchange="judgeIfInputDeptCode();">
								</select>
							</div>	
						</td>	
					</tr>
					
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
								      <option value="4" >护士</option><!-- 增加部门类型 也去admin/dept/addDept.jsp 增加一次，谢谢-->
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
							<div class="form-group">
							<input type="radio" name="orgflag" id="orgflag0" value="0" checked="checked">
							<label for="orgflag0">否</label>&nbsp;
							<input type="radio" name="orgflag" id="orgflag1" value="1"><label for="orgflag1">是</label>
							</div>
						</td>
						
						<td>
							 <span class="impText">是否回访部门</span>
						</td>
						<td>
							<div class="form-group">
							<input type="radio" name="isvisit" id="isvisit0" value="0" checked="checked"><label for="isvisit0">否</label>&nbsp;
					        <input type="radio" name="isvisit" id="isvisit1" value="1"><label for="isvisit1">是</label>
					        </div>
						</td>
				
					</tr>
					<tr>
						<td>
							<span class="impText">是否多出行医</span>
						</td>
						<td>
							<div class="form-group">
								<input type="radio" name="ismore" id="ismore0" value="0" checked="checked"><label for="ismore0">否</label>&nbsp;
						        <input type="radio" name="ismore" id="ismore1" value="1"><label for="ismore1">是</label>
					        </div>
						</td>

						<td>
							<span class="impText">是否为客服</span>
						</td>
						<td>
							<div class="form-group">
								<input type="radio" name="iskefu" id="iskefu0" value="0" checked="checked"><label for="iskefu0">否</label>&nbsp;
								<input type="radio" name="iskefu" id="iskefu1" value="1"><label for="iskefu1">是</label>
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
						<td>
							<span class="comText">部门管理员</span>
						</td>
						<td colspan="3">
							<div class="form-group">
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
         	<a class="kqdsCommonBtn" id="clean">清 空</a>
         	<a class="kqdsCommonBtn" id="submit">确 定</a>
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
var static_deptParent = "<%=deptParentPage%>";
var static_deptCode = "<%=deptCodePage%>";
$(function() {
    if (static_deptCode != "") {
        $("#deptCode").val(static_deptCode);
    }

    getSelectDeptTree4Manage("deptParent"); // 部门树下拉框
    jQuery('#deptTypeSelect').selectpicker({
        title: "请选择"
    });
    // 根据deptid 选中弄下拉框
    $("#deptParent").val(static_deptParent);
    
    judgeIfInputDeptCode();

    // 初始化人员树-多选
    // initPersonTreeMulti('managerTree', manager_personTree_oncheck, 'managerInput'); 
    // 初始化人员树-单选
    // initPersonTree('managerTree',manager_personTree_onclick,'managerInput');
});

/**
 * 新建门诊时可以填写门诊编号
 */
function judgeIfInputDeptCode(){
	if("0" == $("#deptParent").val()){
		$("#deptCode").removeAttr("readonly");
	}else{
		$("#deptCode").attr("readonly","readonly")
	}
}

/**
 * 部门管理员，点击赋值方法 ### 单选
 */
function manager_personTree_onclick(e, treeId, treeNode) {
    var dataObj = getClick4PersonTreeSingle(treeId, treeNode);
    if (dataObj) {
        $("#managerInput").val(dataObj.clickName);
        $("#manager").val(dataObj.clickId);
    }
}

/**
 * 部门管理员，点击赋值方法 ### 多选
 */
function manager_personTree_oncheck(e, treeId, treeNode) {
    var dataObj = getCheck4PersonTreeMulti(treeId);
    $("#managerInput").val(dataObj.selectName);
    $("#manager").val(dataObj.selectId);
}
$('#deptParent').on('change',
function() {
	var static_detailurl = 'YZDeptAct/selectDetail.act?seqId=' + $(this).val();
	var serverData = getDataFromServer(static_detailurl);
    if (serverData) {
    	$("#deptCode").val(serverData.retData.deptCode);
    }
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
    var deptName = $("#deptName").val();
    var deptCode = $("#deptCode").val();

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
                window.location.reload();
            }
        });
    }
}
</script>
</html>
