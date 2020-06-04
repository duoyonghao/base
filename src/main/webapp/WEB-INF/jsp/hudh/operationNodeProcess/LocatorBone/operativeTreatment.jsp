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
	.ui-datepicker-div {
   		z-index: 9999999 ;
    }
    .preparation-ul li{
		float:left;
		list-style: none;
	}
</style>
</head>
<body>
	<div>
		<table align="center">
			<tbody>
							<tr>
								<td>
									<div style="margin-left: 68px;z-index: 9999999 ;">
										<ul>
											<li><label>1、手术时间：<input name="operation_time" type="text" id="operation_time" value="" style="width: 105px; index: 100;z-index: 9999999 ;" /></label></li>
											<li><label><input name="preoperation_one_houres" type="checkbox" value="2、术前1小时，口服抗菌药物" id="preoperation_one_houres">2、术前1小时，口服抗菌药物</label></li>
											<li><label><input name="preoperative_verification" type="checkbox" value="3、术前核查" id="preoperative_verification"/>3、术前核查</label></li>
										</ul>
									</div>	
								</td>
							
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li>
												<ul class="preparation-ul">
													<li ><span style="font-weight: bold;" class="">&nbsp;&nbsp;&nbsp;4、辅助手术：</span></li>
													<li ><label><input name="assist_operation" type="checkbox" value="(1)、内提" />(1)、内提</label></li>
													<li ><label><input name="assist_operation" type="checkbox" value="(2)、外提" />(2)、外提</label></li>
													<li ><label><input name="assist_operation" type="checkbox" value="(3)、骨劈开" />(3)、骨劈开</label></li><br>
													<li ><label><input name="assist_operation" type="checkbox" value="(4)、骨挤压" />(4)、骨挤压</label></li>
													<li ><label><input name="assist_operation" type="checkbox" value="(5)、自体骨移植" />(5)、自体骨移植</label></li>
													<li ><label><input name="assist_operation" type="checkbox" value="(6)、植骨" />(6)、植骨</label></li>
												</ul>
											</li>
											<li>
												<ul class="preparation-ul">
													<li ><span style="font-weight: bold;" class="">&nbsp;&nbsp;&nbsp;5、基台放置：</span></li>
													<li ><label><input name="abutment_station" type="checkbox" value="(1)、愈合基台" />(1)、愈合基台</label></li>
													<li ><label><input name="abutment_station" type="checkbox" value="(2)、复合基台放置" />(2)、复合基台放置</label></li>
													<li ><label><input name="abutment_station" type="checkbox" value="(3)、螺丝" />(3)、螺丝</label></li><br>
													<li ><label><input name="abutment_station" type="checkbox" value="(4)、locator基台" />(4)、locator基台</label></li>
												</ul>
											</li>
											<li>
												<ul class="preparation-ul">
													<li ><span style="font-weight: bold;" class="">&nbsp;&nbsp;&nbsp;6、愈合帽放置：</span></li>
													<li ><label><input name="healing_cap_station" type="checkbox" value="1、 金帽" />(1)、 金帽</label></li>
													<li ><label><input name="healing_cap_station" type="checkbox" value="2、银帽" />(2)、银帽</label></li>
												</ul>
											</li>
										</ul>
									</div>	
								</td>
								
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label><input name="postoperation_user_deugs" type="checkbox" value="7、术后使用抗菌药物3~5天" id="postoperation_user_deugs"/>7、术后使用抗菌药物3~5天</label></li>
											<li><label><input name="collutory" type="checkbox" value="8、漱口液含漱" id="collutory"/>8、漱口液含漱</label></li>
											<li><label><input name="small_teeth" type="checkbox" value="9、曲面断层、小牙片" id="small_teeth"/>9、曲面断层、小牙片</label></li>
										</ul>
									</div>	
								</td>
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label><input name="antimicrobial_use" type="checkbox" value="10、抗菌药物使用3-5天" id="antimicrobial_use"/>10、抗菌药物使用3-5天 </label></li>
											<li><label><input name="announcements" type="checkbox" value="11、交代注意事项" id="announcements"/>11、交代注意事项 </label></li>
											<li><label><input name="opration_record" type="checkbox" value="12、术者完成手术记录" id="opration_record"/>12、术者完成手术记录</label></li>
										</ul>
									</div>	
								</td>
								
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label><input name="postoperativeModulus" type="checkbox" value="13、术后取模" id="postoperativeModulus"/>13、术后取模 </label></li>
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
//alert(parent.seqId);
var id = parent.seqId;
var contextPath = '<%=contextPath%>';
var nodeSta = parent.nodeStas;//根据状态判断节点操作权限
var ordNumber = parent.ordNumber;
var nodeId = parent.nodeId;
var nodeId = parent.nodeId1;//获得点击节点的id
var orderNumber = parent.ordNumber;
//alert(nodeId);
$(function(){
	/* $('#operation_time').datetimepicker({ 
	    beforeShow:function(input) { 
	        $(input).css({ 
	            "z-index": 999999 
	        }); 
	    } 
	}); */
	//时间选择
    $("#operation_time").datetimepicker({
        language:  'zh-CN',  
   		minView:2,
        format: 'yyyy-mm-dd',
   		autoclose : true,//选中之后自动隐藏日期选择框   
   		pickerPosition: "bottom-right",
   		todayBtn: true
    });
    getNodeId();
    initOperativeTreatment();
});	

function initOperativeTreatment() {
	$.ajax({
		url: contextPath + "/HUDH_LcljOperationNodeInforAct/selectOperationNodeInforByOrdernumberAndNodeId.act",
		type:"POST",
		dataType:"json",
		data: {
			"nodeId" : nodeId,
			"orderNumber" : orderNumber
		},
		success:function(data) {
			console.log(data);
			if (nodeSta == 2) {
				$('input').attr("disabled",true)
				//$('input').attr("readonly", true);
				$('textarea').attr("readonly","readonly");
			}
			$("#operation_time").val(data.operationTime);
			$("#remark").val(data.remark);
			var preoperation_one_houres = $("#preoperation_one_houres").find("input[type='checkbox']");
			select_preoperation_one_houres = data.preoperation_one_houres;
			if (select_preoperation_one_houres) {
				$("#preoperation_one_houres").attr("checked","checked");
			}
			var preoperative_verification = $("#preoperative_verification").find("input[type='checkbox']");
			select_preoperative_verification = data.preoperative_verification;
			if (select_preoperation_one_houres) {
				$("#preoperative_verification").attr("checked","checked");
			}
			var announcements = $("#announcements").find("input[type='checkbox']");
			select_announcements = data.announcements;
			if (select_announcements) {
				$("#announcements").attr("checked","checked");
			}
			var antimicrobial_use = $("#antimicrobial_use").find("input[type='checkbox']");
			select_antimicrobial_use = data.antimicrobial_use;
			if (select_antimicrobial_use) {
				$("#antimicrobial_use").attr("checked","checked");
			}
			var opration_record = $("#opration_record").find("input[type='checkbox']");
			select_opration_record = data.opration_record;
			if (select_opration_record) {
				$("#opration_record").attr("checked","checked");
			}
			var small_teeth = $("#small_teeth").find("input[type='checkbox']");
			select_small_teeth = data.small_teeth;
			if (select_small_teeth) {
				$("#small_teeth").attr("checked","checked");
			}
			var preoperation_one_houres = $("#collutory").find("input[type='checkbox']");
			select_collutory = data.collutory;
			if (select_collutory) {
				$("#collutory").attr("checked","checked");
			}
			var postoperation_user_deugs = $("#postoperation_user_deugs").find("input[type='checkbox']");
			select_postoperation_user_deugs = data.postoperation_user_deugs;
			if (select_postoperation_user_deugs) {
				$("#postoperation_user_deugs").attr("checked","checked");
			}
			var postoperativeModulus = $("#postoperativeModulus").find("input[type='checkbox']");
			select_postoperativeModulus = data.postoperativemodulus;
			if (select_postoperativeModulus) {
				$("#postoperativeModulus").attr("checked","checked");
			}
			
			//辅助手术信息赋值
			var select_assist_operation = data.assist_operation;
			var disease_id = select_assist_operation.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='assist_operation']").each(function(){
					if($(this).val()==disease_id[i]){
					   $(this).attr("checked","checked");
					}
				})
			}
			
			//基台放置信息赋值
			var select_abutment_station = data.abutment_station;
			var disease_id = select_abutment_station.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='abutment_station']").each(function(){
					if($(this).val()==disease_id[i]){
					   $(this).attr("checked","checked");
					}
				})
			}
			
			//愈合帽放置信息赋值
			var select_healing_cap_station = data.healing_cap_station;
			var disease_id = select_healing_cap_station.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='healing_cap_station']").each(function(){
					if($(this).val()==disease_id[i]){
					   $(this).attr("checked","checked");
					}
				})
			}
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

function getNodeId() {
	$.ajax({
		url: contextPath + "/HUDH_FlowAct/findHadWorkByOrderNumberAndNodeId.act",
		type:"POST",
		dataType:"json",
		data: {
			"ordNumber" : ordNumber,
			"nodeId" : nodeId
		},
		success:function(data){
			if (data.retState == "0") {
				id = data.selNodeWork.dataId;
	        } else {
	            /*layer.alert('未达到该节点！' + data.retMsrg, {//后台抛出的异常信息在前台展示
	            	end: function() {
	                	location.reload(); //刷新父页面
		                var frameindex = parent.layer.getFrameIndex(window.name);
		                layer.close(frameindex); //再执行关闭
	                }
	            });*/
	        }
		},
		error: function(data) {
			
		}
	});
}
</script>
</html>