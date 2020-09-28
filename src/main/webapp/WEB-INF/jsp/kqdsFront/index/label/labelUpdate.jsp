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
							<span class="labelName">标签名称</span>
						</td>
						<td>
							<input type="text" name="labelName" id="labelName">
						</td>
					</tr>
					<tr>
						<td>
							<span class="createUser">创建人</span>
						</td>
						<td>
							<input type="text" readonly="readonly" name="createUser" id="createUser" >
						</td>
					</tr>
					<tr>
						<td>
							<span class="labelFather">父级标签</span>
						</td>
						<td>
							<input type="text" readonly="readonly" name="labelFather" id="labelFather" >
						</td>
					</tr>
					<tr>
						<td>
							<span class="remark">备注</span>
						</td>
						<td>
							<input type="text" name="remark" id="remark">
						</td>
					</tr>
				</tbody>
			</table>	
		</form>
		<div class="fixedBottomDiv"><!--底部三个按钮所在父元素 固定在底部样式  -->
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
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds_person_select.js"></script>
<script type="text/Javascript">
var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var personId = "<%=person.getSeqId()%>";
var personName = "<%=person.getUserName()%>";
var parentSelectObj=window.parent.selectObj; //父页面选中标签行对象
$(function() {
	$("#createUser").val(personName);//初始化页面的登录人
	$("#labelName").val(parentSelectObj[0].levelabel);//标签名称
	$("#remark").val(parentSelectObj[0].remark);//备注
	if(parentSelectObj[0].parentid==1){
		$("#labelFather").val("当前标签为一级标签");
	}else{
		$("#labelFather").val(parentSelectObj[0].parentid);
	}
});

$('#submit').on('click',
function() {
    submitForm();
});

function submitForm() {
    var labelName = $("#labelName").val();//标签名称
    var remark = $("#remark").val(); //备注
    if (labelName == "") {
        layer.alert('标签名称不能为空' );
        return false;
    }
    
    //修改提交
    var createTime= new Date().Format("yyyy-MM-dd hh:mm");
    var parentlabelId=parentSelectObj[0].parentid; //父级id
    var seqId=parentSelectObj[0].seqId; //当前标签ID
    var url = contextPath + '/KQDS_LabelAct/updateLabel.act';
    var param = {
    	 seqId : seqId, //当前标签ID
    	 parentId : parentlabelId, //上级ID
    	 leveLabel :  labelName, //新建标签名
    	 createUser :  personId, //创建人
    	 createTime :  createTime, //创建时间
    	 remark :  remark //备注
    };
    //console.log(JSON.stringify(param)+"------------提交参数");
    $.axseSubmit(url, param,function() {},function(r) {
    	layer.alert("修改成功！", {
            end: function() {
            	//window.parent.location.reload(); //刷新父页面
            	window.parent.refreshTable();
                var frameindex = parent.layer.getFrameIndex(window.name);
                parent.layer.close(frameindex); //再执行关闭
            }
      	});
    },function(r){
    	layer.alert("修改失败！");
    });
}
</script>
</html>
