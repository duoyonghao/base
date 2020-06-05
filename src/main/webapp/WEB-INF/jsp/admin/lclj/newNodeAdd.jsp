<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);

	String flowCode = request.getParameter("flowCode");
	if(flowCode == null){
		flowCode = "";
	}
	String flowName = request.getParameter("flowName");
	if(flowName == null){
		flowName = "";
	}
%>
<!DOCTYPE html>
<html>
<!-- 本页面已优化（输入框需要再次优化）鲍3-26 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>新建临床路径流程</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" rel="stylesheet" >
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/dict/dictNewAdd.css"/>
</head>
<body>
	
	<div class="container"><!--提供内边距 让内容不顶头显示-->
		<form id="defaultForm"><!-- 本身无样式  defaultForm表单验证功能要用 -->
			<input type="hidden" name="seqId" id="seqId">
			<table class="tableContent"><!--tableContent的样式 -->
				<tbody>
					<tr>
						<td> 	<!--impText必填项样式  -->
							<span class="impText">所属门诊</span>
						</td>
						<td>
							 <select id="organization" name="organization"></select>
						</td>
					</tr>
					<tr>
						<td> 	<!--impText必填项样式  -->
							<span class="impText">所属流程</span>
						</td>
						<td>
							<input type="text" name="flowName" id="flowName" readonly="readonly">
							<input type="text" name="flowCode" id="flowCode" readonly="readonly" style="display: none;">
						</td>
					</tr>
					<tr>
						<td> 	<!--impText必填项样式  -->
							<span class="impText">节点编号</span>
						</td>
						<td>
							<input type="text" name="num" id="num">
							
						</td>
					</tr>
					<tr>
						<td> 	<!--impText必填项样式  -->
							<span class="impText">节点id</span>
						</td>
						<td>
							<input type="text" name="nodeId" id="nodeId">
						</td>
					</tr>
					<tr>
						<td> 	<!--impText必填项样式  -->
							<span class="impText">节点名称</span>
						</td>
						<td>
							<input type="text" name="nodename" id="nodename">
						</td>
					</tr>
					<tr>
						<td>
							<span class="impText">办理人类型</span>
						</td>
						<td>
							<select id="authorType" name="authorType">
								<option value="">=请选择=</option>
								<option value="dept">部门</option>
								<option value="createpara">从创建字段获取</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<span class="impText">办理人员/部门</span>
						</td>
						<td>
							<input type="text" name="author" id="author" >
						</td>
					</tr>
					<tr>
						<td>
							<span class="impText">操作界面</span>
						</td>
						<td>
							<input type="text" name="viewUrl" id="viewUrl" >
						</td>
					</tr>
					<tr>
						<td>
							<span class="impText">办理时限(天)</span>
						</td>
						<td>
							<input type="text" name="nodeLimit" id="nodeLimit" >
						</td>
					</tr>
					<tr>
						<td>
							<span class="impText">时限显示类型</span>
						</td>
						<td>
							<select id="limitType" name="limitType">
								<option value="">=请选择=</option>
								<option value="1">天</option>
								<option value="2">周</option>
								<option value="3">月</option>
							</select>
						</td>
					</tr>
				</tbody>
			</table>	
		</form>
		<div class="fixedBottomDiv"><!--底部三个按钮所在父元素 固定在底部样式  -->
			<div class="clear2"></div>
         	<!-- <a class="kqdsCommonBtn" id="clean">清 空</a> -->
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
<script type="text/javascript" src="<%=contextPath%>/kqdsFront/js/kqds/sys_dict.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds_person_select.js"></script>
<script type="text/Javascript">
var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var flowCode = "<%=flowCode%>";
var flowName = "<%=flowName%>";
$(function() {
	initHosSelectListNoCheckWithCommon('organization');// 连锁门诊下拉框	
	$("#flowName").val(flowName);
	$("#flowCode").val(flowCode);
});

$('#clean').on('click',
function() {
    $("#defaultForm :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
});

$('#submit').on('click',
function() {
    submitForm();
});

function submitForm() {
    var num = $("#num").val();
    var nodeId = $("#nodeId").val();
    var nodeName = $("#nodename").val();
    var authorType = $("#authorType").val();
    var author = $("#author").val();
    var viewUrl = $("#viewUrl").val();
    var nodeLimit = $("#nodeLimit").val();
    var limitType = $("#limitType").val();
    
    /* var organization = $("#organization").val();
    if (flowName == "") {
        layer.alert('流程名称不能为空' );
        return false;
    }
    if (flowCode == "") {
        layer.alert('流程编号不能为空' );
        return false;
    }
    if (num == "") {
        layer.alert('排序号不能为空' );
        return false;
    } */
    // 赋值
    var param = $('#defaultForm').kqds_serialize();

    var url = 'HUDH_FlowConfigAct/insertNodeConfig.act?' + param;
    var serverData = getDataFromServer(url);
    if (serverData) {
        layer.alert('操作成功', {
              
            end: function() {
                if (parent) {
                    parent.refresh();
                    parent.layer.close(frameindex);
                }
            }
        });
    }
}
</script>
</html>
