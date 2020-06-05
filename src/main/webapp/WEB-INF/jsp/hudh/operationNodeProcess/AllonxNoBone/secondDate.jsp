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
											<li><label>1、来院时间：<input name="visit_time" type="text" id="visit_time" value="" style="width: 105px;"/></label></li>
											<li><label>2、检查口腔清洁情况：
													<select id="checkCavityCleanSitu" name="checkCavityCleanSitu" style="width: 80px;">
														<option>-请选择-</option>
														<option>Ⅰ</option>
														<option>Ⅱ</option>
														<option>Ⅲ</option>
														<option>Ⅳ</option>
													</select>
											    </label></li>
										</ul>
									</div>	
								</td>
							
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label><input name="askUserSituation" type="checkbox" value="3、询问使用情况" id="askUserSituation">3、询问使用情况</label></li>
											<li><label><input name="checkOcclusion" type="checkbox" value="4、检查咬合情况" id="checkOcclusion">4、检查咬合情况</label></li>
										</ul>
									</div>	
								</td>
								
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label><input name="resinSealing" type="checkbox" value="5、树脂封口" id="resinSealing"/>5、树脂封口</label></li>
											<li><label><input name="announcements" type="checkbox" value="6、交代注意事项" id="announcements"/>6、交代注意事项</label></li>
											<li><label><input name="complete_case_record" type="checkbox" value="7、完成病例记录" id="complete_case_record"/>7、完成病例记录</label></li>
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
    $("#visit_time").datetimepicker({
        language:  'zh-CN',  
   		minView:2,
        format: 'yyyy-mm-dd',
   		autoclose : true,//选中之后自动隐藏日期选择框   
   		pickerPosition: "bottom-right",
   		beforeShow: function () { 
   			setTimeout(function () { 
   			$('#myiframe').css("z-index", 999); 
   			}, 100 ); 
   		} 
    });
    initSecondDate();
});

function initSecondDate() {
	$.ajax({
		url: contextPath + "/HUDH_LcljOperationNodeInforAct/selectOperationNodeInforByOrdernumberAndNodeId.act",
		type:"POST",
		dataType:"json",
		data: {
			"nodeId" : nodeId,
			"orderNumber" : orderNumber
		},
		success:function(data){
			if (nodeSta == 2) {
				$('input').attr("disabled",true);
				$('select').attr("disabled",true);
				//$('input').attr("readonly", true);
				$('textarea').attr("readonly","readonly");
			}
			$("#visit_time").val(data.visit_time);
			$("#checkCavityCleanSitu").val(data.checkcavitycleansitu);
			$("#askAboutUsage").val(data.askaboutusage);
			var completeTwoOperation = $("#completeTwoOperation").find("input[type='checkbox']");
			select_completeTwoOperation = data.completeTwoOperation;
			if (select_completeTwoOperation) {
				$("#completeTwoOperation").attr("checked","checked");
			}
			var twoOperationAttention = $("#twoOperationAttention").find("input[type='checkbox']");
			select_twoOperationAttention = data.twoOperationAttention;
			if (select_twoOperationAttention) {
				$("#twoOperationAttention").attr("checked","checked");
			}
			var complete_case_record = $("#complete_case_record").find("input[type='checkbox']");
			select_complete_case_record = data.complete_case_record;
			if (select_complete_case_record) {
				$("#complete_case_record").attr("checked","checked");
			}
			var announcements = $("#announcements").find("input[type='checkbox']");
			select_announcements = data.announcements;
			if (select_announcements) {
				$("#announcements").attr("checked","checked");
			}
			var collutory = $("#collutory").find("input[type='checkbox']");
			select_collutory = data.collutory;
			if (select_collutory) {
				$("#collutory").attr("checked","checked");
			}
			var checkOcclusion = $("#checkOcclusion").find("input[type='checkbox']");
			select_checkOcclusion = data.checkocclusion;
			if (select_checkOcclusion) {
				$("#checkOcclusion").attr("checked","checked");
			}
			var resinSealing = $("#resinSealing").find("input[type='checkbox']");
			select_resinSealing = data.resinsealing;
			if (select_resinSealing) {
				$("#resinSealing").attr("checked","checked");
			}
			var opration_record = $("#opration_record").find("input[type='checkbox']");
			select_opration_record = data.opration_record;
			if (select_opration_record) {
				$("#opration_record").attr("checked","checked");
			}
			var askUserSituation = $("#askUserSituation").find("input[type='checkbox']");
			select_askUserSituation = data.askusersituation;
			if (select_askUserSituation) {
				$("#askUserSituation").attr("checked","checked");
			}
			
			$("#check_wound").val(data.check_wound);
			$("#next_hospital_time").val(data.next_hospital_time);
			$("#remark").val(data.remark);
		}
	});
}
</script>
</html>