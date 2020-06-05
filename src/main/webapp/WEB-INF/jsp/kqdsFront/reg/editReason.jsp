<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String type = request.getParameter("type");
	if(type == null || "".equals(type)){
		type="0";
	}
	String seqId = request.getParameter("seqId");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8" />
    <title>挂号修改/撤销原因展示页面</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/reg/reg.css"/>
</head>

<body>
	<div class="regReason">
		<table class="tableLayout">
			<tr>
				<td>
					<span class="textMessage">修改/撤销原因：</span>
				</td>
				<td colspan="2">
					<textarea name="editreason" id="editreason" rows="1" readonly="readonly" placeholder="">
					</textarea>
				</td>
			</tr>
			<tr>
				<td>
					
				</td>
				<td>
					<span class="modifyText">修改前</span>
				</td>
				<td>
					<span class="modifyText">修改后</span>
				</td>
			</tr>
			<tr>
				<td>
					<span class="textMessage">就诊分类：</span>
				</td>
				<td>
					<input type="text" name="regsort1" id="regsort1" readonly>
				</td>
				<td>
					<input type="text" name="regsort" id="regsort" readonly>
				</td>
			</tr>
			<tr>
				<td>
					<span class="textMessage">挂号方式：</span>
				</td>
				<td>
					<input type="text" name="regway1" id="regway1" readonly>
				</td>
				<td>
					<input type="text" name="regway" id="regway" readonly>
				</td>
			</tr>
			<tr>
				<td>
					<span class="textMessage">挂号金额：</span>
				</td>
				<td>
					<input type="text" name="regmoney1" id="regmoney1" readonly>
				</td>
				<td>
					<input type="text" name="regmoney" id="regmoney" readonly>
				</td>
			</tr>
			<tr>
				<td>
					<span class="textMessage">咨&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;询：</span>
				</td>
				<td>
					<input type="text"  name="askperson1Desc" id="askperson1" readonly>
				</td>
				<td>
					<input type="text" name="askperson" id="askperson" readonly>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="textMessage">就诊科室：</span>
				</td>
				<td>
					<input type="text"  name="regdept1" id="regdept1" readonly>
				</td>
				<td>
					<input type="text" name="regdept" id="regdept" readonly>
				</td>
			</tr>
			<tr>
				<td>
					<span class="textMessage">医生：</span>
				</td>
				<td>
					<input type="text"  name="doctor1" id="doctor1" readonly>
				</td>
				<td>
					<input type="text" name="doctor" id="doctor" readonly>
				</td>
			</tr>
			<tr>
				<td>
					<span class="textMessage">创建人：</span>
				</td>
				<td>
					<input type="text"  name="createuser1Desc" id="createuser1" readonly>
				</td>
				<td>
					<input type="text" name="createuser" id="createuser" readonly>
				</td>
			</tr>
		</table>
	</div>
</body>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>

<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var seqId = '<%=seqId%>';

$(function() {
	var regObj = getRegObjBySeqId(seqId);

	//修改原因
    $("#editreason").val(regObj.editreason);

    //修改前
    var beforeeditreason = regObj.beforeeditreason;
    var before = eval('(' + beforeeditreason + ')');
    if (before.recesort) {
        $("#recesort1").val(getValueDictTB(before.recesort));
    }
    if (before.regsort) {
        $("#regsort1").val(getValueDictTB(before.regsort));
    }
    if (before.regway) {
        $("#regway1").val(getValueDictTB(before.regway));
    }
    if (before.regmoney) {
        $("#regmoney1").val(before.regmoney);
    }
    if (before.regdept) {
        $("#regdept1").val(getDeptNameBySeqIdTB(before.regdept));
    }
    if (before.askperson) {
        bindPerUserNameBySeqIdTB("askperson1", before.askperson);
    }
    
    if (before.doctor) {
        bindPerUserNameBySeqIdTB("doctor1", before.doctor);
    }
    
    if (before.createuser) {
        bindPerUserNameBySeqIdTB("createuser1", regObj.createuser);
    }
    //修改后
    if (regObj.recesort) {
        $("#recesort").val(getValueDictTB(regObj.recesort));
    }
    if (regObj.regsort) {
        $("#regsort").val(getValueDictTB(regObj.regsort));
    }
    if (regObj.regway) {
        $("#regway").val(getValueDictTB(regObj.regway));
    }
    if (regObj.regmoney) {
        $("#regmoney").val(regObj.regmoney);
    }
    if (regObj.regdept) {
        $("#regdept").val(getDeptNameBySeqIdTB(regObj.regdept));
    }
    if (regObj.askperson) {
        bindPerUserNameBySeqIdTB("askperson", regObj.askperson);
    }
    
    if (regObj.doctor) {
        bindPerUserNameBySeqIdTB("doctor", regObj.doctor);
    }
    
    if (regObj.createuser) {
        bindPerUserNameBySeqIdTB("createuser", regObj.createuser);
    }

});
</script>
</html>
