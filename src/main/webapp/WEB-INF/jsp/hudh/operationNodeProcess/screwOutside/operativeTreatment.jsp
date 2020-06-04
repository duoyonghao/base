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
    .ui-datepicker {
	    width: 17em;
	    padding: .2em .2em 0;
	    display: none;
	    z-index:21 !important   /*解决日期选择控件被挡住的问题*/ 
	}
	.preparation-ul{
		display: flex;
		flex-direction: row;
		flex-wrap:wrap;
	}
	.preparation-ul li{
		float:left;
		list-style: none;
	}
	.preparation-ul span{
		display:inline-block;
		margin-top: 3px;
	}
	table{
		width:93%;
		margin-top: 20px;
	}
	table tr{
		width:100%;
	}
	table td{
		padding-left: 2%;
	}
	table td>div{
		margin-left: 0px;
	}
	input{
		vertical-align: text-top;
	}
</style>
</head>
<body>
	<div>
		<table align="center">
			<tbody>
							<tr>
								<td style="width:26%;">
									<div style="z-index: 9999999 ;">
										<ul>
											<li><label style="position:absolute,z-index:9999">1、手术时间：
											<input name="operation_time" type="text" id="operation_time" value="" style="width: 105px;vertical-align: middle;" readonly="readonly"/></label></li>
											<li><label>2、<input name="preoperation_one_houres" type="checkbox" value="2、术前1小时，口服抗菌药物"  id="preoperation_one_houres"/>术前1小时，口服抗菌药物</label></li>
											<li><label>3、<input name="preoperative_verification" type="checkbox" value="3、术前核查" id="preoperative_verification"/>术前核查</label></li>
											<li>
												<ul class="preparation-ul">
													<li ><span style="font-weight: bold;" class="">4、麻醉方式：</span></li>
													<li ><label><input name="anesthesiaMethod" type="checkbox" value="(1)、局部麻醉" />(1)、局部麻醉</label></li>
													<li ><label><input name="anesthesiaMethod" type="checkbox" value="(2)、必要时加镇痛镇静治疗" />(2)、必要时加镇痛镇静治疗</label></li>
												</ul>
											</li>
										</ul>
									</div>	
								</td>
							
								<td style="width:26%;">
									<div style="">
										<ul>
											<!-- <li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class="">5、基台放置：</span></div>
													<div><label><input name="abutment_station" type="checkbox" value="(1)、愈合基台" />(1)、愈合基台</label></div>
													<div><label><input name="abutment_station" type="checkbox" value="(2)、复合基台放置" />(2)、复合基台放置</label></div>
													<div><label><input name="abutment_station" type="checkbox" value="(3)、螺丝" />(3)、螺丝</label></div>
													<div><label><input name="abutment_station" type="checkbox" value="(4)、locator基台" />(4)、locator基台</label></div>
												</div>
											</li> -->
											<li><label><input name="operator" type="checkbox" value="5、手术" id="operator"/>5、手术</label></li>
											<li>
												<div class="preparation-ul">
													<div ><span style="font-weight: bold;" class="">6、愈合帽放置：</span></div>
													<div ><label><input name="healing_cap_station" type="checkbox" value="1、 金帽" />(1)、 金帽</label></div>
													<div ><label><input name="healing_cap_station" type="checkbox" value="2、银帽" />(2)、银帽</label></div>
												</div>
											</li>
											<li>
												<ul class="preparation-ul">
													<li ><span style="font-weight: bold;" class="">7、术中用药：</span></li>
													<li ><label><input name="intraoperativeMedication" type="checkbox" value="(1)、局麻药物" />(1)、局麻药物</label></li>
													<li ><label><input name="intraoperativeMedication" type="checkbox" value="(2)、必要时心血管临时用药" />(2)、必要时心血管临时用药</label></li>
												</ul>
											</li>
										</ul>
									</div>	
								</td>
								
								<td style="width:18%;">
									<div style="">
										<ul>
											<li><label><input name="enterLclj" type="checkbox" value="8、临床路径录入" id="enterLclj"/>8、临床路径录入</label></li>
											<li><label><input name="collutory" type="checkbox" value="9、漱口液含漱、抗菌药物使用3-5天" id="collutory"/>9、漱口液含漱、抗菌药物使用3-5天</label></li>
											<li><label><input name="small_teeth" type="checkbox" value="10、拍X片：曲面断层,必要时小牙片" id="small_teeth"/>10、拍X片：曲面断层,必要时小牙片</label></li>
										</ul>
									</div>	
								</td>
								<td style="width:15%;">
									<div style="">
										<ul>
											<li><label><input name="antimicrobial_use" type="checkbox" value="11、抗菌药物使用3-5天" id="antimicrobial_use"/>11、抗菌药物使用3-5天</label></li>
											<li><label><input name="announcements" type="checkbox" value="12、交代注意事项" id="announcements"/>12、交代注意事项 </label></li>
											<li><label><input name="opration_record" type="checkbox" value="13、术者完成手术记录" id="opration_record"/>13、术者完成手术记录</label></li>
										</ul>
									</div>	
								</td>
								<td style="width:15%;">
									<div style="">
										<span class="commonText" style="">备注：</span>
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
var nodeSta = parent.nodeStas;
var contextPath = '<%=contextPath%>';
var ordNumber = parent.ordNumber;
var nodeId = parent.nodeId1;//获得点击节点的id
var orderNumber = parent.ordNumber;
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
   		minView: 2,
        format: 'yyyy-mm-dd',
   		autoclose : true,//选中之后自动隐藏日期选择框   
   		pickerPosition: "bottom-right",
   		todayBtn: true,
	   	beforeShow: function () {
	         setTimeout(
		         function () {
		             $('#ui-datepicker-div').css("z-index", 21);
		         }, 100
	         );
	    }
    });
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
			if (nodeSta == 2) {
				$('input').attr("disabled",true)
				//$('input').attr("readonly", true);
				$('textarea').attr("readonly","readonly");
			}
			$("#operation_time").val(data.operationTime);
			$("#remark").val(data.remark);
			var connectingBridge = $("#connectingBridge").find("input[type='checkbox']");
			select_connectingBridge = data.connectingbridge;
			if (select_connectingBridge) {
				$("#connectingBridge").attr("checked","checked");
			}
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
			var enterLclj = $("#enterLclj").find("input[type='checkbox']");
			select_enterLclj = data.enterlclj;
			if (select_enterLclj) {
				$("#enterLclj").attr("checked","checked");
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
			var antimicrobial_use = $("#antimicrobial_use").find("input[type='checkbox']");
			select_antimicrobial_use = data.antimicrobial_use;
			if (select_antimicrobial_use) {
				$("#antimicrobial_use").attr("checked","checked");
			}
			
			//新增接收选中
			//手术
			operator = data.operator;
			if (operator) {
				$("#operator").attr("checked","checked");
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
			
			//麻醉方式信息赋值
			var select_anesthesiaMethod = data.anesthesiamethod;
			var disease_id = select_anesthesiaMethod.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='anesthesiaMethod']").each(function(){
					if($(this).val()==disease_id[i]){
					   $(this).attr("checked","checked");
					}
				})
			}
			
			//术中用药信息赋值
			var select_intraoperativeMedication = data.intraoperativemedication;
			var disease_id = select_intraoperativeMedication.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='intraoperativeMedication']").each(function(){
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

/**
 * 确定每个节点对应下手术节点信息表的id
 */
function getNodeSeqId() {
	$.ajax({
		url: apppath + "/HUDH_FlowAct/findHadWorkByOrderNumberAndNodeId.act",
		type:"POST",
		dataType:"json",
		data: {
			"ordNumber" : ordNumber,
			"nodeId" : nodeId
		},
		success:function(data){
				seqId = data.selNodeWork.dataId;
		},
		error: function(data) {
			
		}
	});
}
</script>
</html>