<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}
	String usercode = request.getParameter("usercode");
	if(usercode == null ){
	 usercode = "";  // 配合js 的非空判断，这里不这样写，则js 需进行  usercode != "null"的判断
	}
	//网电预约Id 挂号时选择的时网电预约记录，挂号后把该预约设为已上门
	String netorderid = request.getParameter("netorderid");
	if(netorderid == null ){
	 netorderid = "";  
	}
	String orderno = request.getParameter("orderno");
	if(orderno == null ){
		orderno = "";  
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<style type="text/css">
	.a{
		border-radius: 6px;
	}
</style>
</head>
<body>
	<div>
		<table align="center">
			<tbody>
							<tr>
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label>1、时间：<input name="operation_time" type="text" id="operation_time" value="" style="width: 105px;" readonly/></label></li>
											<li><label><input name="ismodule" type="checkbox" value="2、取模" id="ismodule">2、取模</label></li>
										</ul>
									</div>	
								</td>
							
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label><input name="preoperative_verification" type="checkbox" value="3、术前核查" id="preoperative_verification"/>3、术前核查</label></li>
											<li><label><input name="confirmOcclusalRelationship" type="checkbox" value="4、确定咬合关系、咬合记录" id="confirmOcclusalRelationship">4、确定咬合关系、咬合记录</label></li>
										</ul>
									</div>	
								</td>
								
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label><input name="makeTransitionDenture" type="checkbox" value="5、制作过渡义齿" id="makeTransitionDenture"/>5、制作过渡义齿</label></li>
											<li><label><input name="tryInTransitionDenture" type="checkbox" value="6、试戴过渡义齿" id="tryInTransitionDenture"/>6、试戴过渡义齿</label></li>
										</ul>
									</div>	
								</td>
								
								<td>
									<span class="commonText" style="margin-left: 68px;">备注：</span>
								</td>
								<td>
									<div style="margin-left: 50px;">
										<textarea class="a" rows="" cols="" id="remark" name="remark"></textarea>
									</div>	
								</td>
							</tr>
							
					</tbody>
			</table>
	</div>
</body>
<script type="text/javascript">
var id = parent.seqId;
var contextPath = '<%=contextPath%>';
var nodeSta = parent.nodeStas;
var nodeId = parent.nodeId1;//获得点击节点的id
var orderNumber = parent.ordNumber;
$(function(){
	//时间选择
    $("#operation_time").datetimepicker({
        language:  'zh-CN',  
   		minView:2,
        format: 'yyyy-mm-dd',
   		autoclose : true,//选中之后自动隐藏日期选择框   
   		pickerPosition: "bottom-right"
    });
	initPostOperativeTreatmentQmdyh();
});	

function initPostOperativeTreatmentQmdyh() {
	$.ajax({
		url: contextPath + "/HUDH_LcljOperationNodeInforAct/selectOperationNodeInforByOrdernumberAndNodeId.act",
		type:"POST",
		dataType:"json",
		data: {
			"nodeId" : nodeId,
			"orderNumber" : orderNumber
		},
		success:function(data){
			console.log(data);
			if (nodeSta == 2) {
				$('input').attr("disabled",true)
				//$('input').attr("readonly", true);
				$('textarea').attr("readonly","readonly");
			}
			$("#operation_time").val(data.operation_time);
			$("#observe_wound").val(data.observe_wound);
			
			/* var observe_wound = $("#observe_wound").find("input[type='checkbox']");
			select_observe_wound = data.observe_wound;
			if (select_observe_wound) {
				$("#observe_wound").attr("checked","checked");
			} */
			var preoperative_verification = $("#preoperative_verification").find("input[type='checkbox']");
			select_preoperative_verification = data.preoperative_verification;
			if (select_preoperative_verification) {
				$("#preoperative_verification").attr("checked","checked");
			}
			var confirmOcclusalRelationship = $("#confirmOcclusalRelationship").find("input[type='checkbox']");
			select_confirmOcclusalRelationship = data.confirmocclusalrelationship;
			if (select_confirmOcclusalRelationship) {
				$("#confirmOcclusalRelationship").attr("checked","checked");
			}
			var tryInTransitionDenture = $("#tryInTransitionDenture").find("input[type='checkbox']");
			select_tryInTransitionDenture = data.tryintransitiondenture;
			if (select_tryInTransitionDenture) {
				$("#tryInTransitionDenture").attr("checked","checked");
			}
			var makeTransitionDenture = $("#makeTransitionDenture").find("input[type='checkbox']");
			select_makeTransitionDenture = data.maketransitiondenture;
			if (select_makeTransitionDenture) {
				$("#makeTransitionDenture").attr("checked","checked");
			}
			var ismodule = $("#ismodule").find("input[type='checkbox']");
			select_ismodule = data.ismodule;
			if (select_ismodule) {
				$("#ismodule").attr("checked","checked");
			}
			$("#check_wound").val(data.check_wound);
			$("#next_hospital_time").val(data.next_hospital_time);
			$("#remark").val(data.remark);
		}
	});
}

//获取辅助手术
function getAssistOperation(){
    var obj = document.getElementsByName("assist_operation");
    var assist_operation = "";
    for ( k in obj ) {
        if(obj[k].checked)
        	//consultation.push(obj[k].value);
        	assist_operation = consultation + obj[k].value + ';';
    }
    return assist_operation;
} 
</script>
</html>