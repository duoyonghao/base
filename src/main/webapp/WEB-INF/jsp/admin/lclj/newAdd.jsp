<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);

	String parentCode = request.getParameter("parentCode");
	if (parentCode == null) {
		parentCode = "";
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
							<span class="impText">流程名称</span>
						</td>
						<td>
							<input type="text" name="flowName" id="flowName">
							
						</td>
					</tr>
					<tr>
						<td>
							<span class="impText">流程编号</span>
						</td>
						<td>
							<input type="text" name="flowCode" id="flowCode" >
						</td>
					</tr>
					<tr>
						<td>
							<span class="impText">排序号</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="num" id="num" ></div>
						</td>
					</tr>
					<tr>
						<td>
							<span class="comText" id="remark">备注</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="remark" id="remark" value="" ></div> <span id="remarkSpan"></span>
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
var static_parentCode = "<%=parentCode%>";
$(function() {
	initHosSelectListNoCheckWithCommon('organization');// 连锁门诊下拉框	
    $("#parentCode").val(static_parentCode);
	//排序号自增 赋值
	var maxorderno = getDictMaxOrderno(static_parentCode);
	$("#orderno").val(Number(maxorderno)+1);
	
	if("YYXM" == static_parentCode){ // 门诊预约的预约项目
		$("#remarkTd").html("默认预约时间");
		$("#remarkSpan").html("单位：分钟，且必须填15的倍数");
	}
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
    var flowName = $("#flowName").val();
    var flowCode = $("#flowCode").val();
    var remark = $("#remark").val();
    var num = $("#num").val();
    var organization = $("#organization").val();
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
    }
    // 赋值
    var param = $('#defaultForm').kqds_serialize();

    var url = 'HUDH_FlowConfigAct/insertLcljFlow.act?' + param;
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
