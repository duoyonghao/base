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
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/operationToggle.js"></script>
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
		margin-top: 12px;
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
	.preparation-ul label{
		margin-bottom: 10px;
		margin-right: 5px;
	}
	table li{
		margin-top: 7px;
	}
</style>
</head>
<body>
	<div>
		<table align="center">
			<tbody>
							<tr>
								<td style="width:18%;">
									<div style="z-index: 9999999 ;">
										<ul>
											<li><label style="position:absolute,z-index:9999">1、手术时间：
											<input class="time_select" name="operation_time" type="text" id="operation_time" value="" style="width: 105px;vertical-align: middle;" readonly="readonly"/></label></li>
											<li><label>2、<input name="preoperation_one_houres" type="checkbox" value="2、术前1小时，口服抗菌药物"  id="preoperation_one_houres"/>术前1小时口服抗菌药物</label></li>
											<li class="hcd">
												<label>
													<i style="color: red;font-style: normal;">*</i>
													3、<input name="preoperative_verification" type="checkbox" value="3、术前核查" id="preoperative_verification" disabled="disabled"/>
													<font class="verification" onclick="showHiddenCase(this);">术前核查</font>
												</label>
												<div class="caseContiner" style="display:none;width: 10%;top: 120px;">
													<button class="btnStyle" onclick="toggleOperationCase(this)" style="">切换旧版</button>
													<div class="zlCases" style="text-align: center;padding: 10px;">
														<font class="operation_examine_patients" style="font-size: 16px;font-weight: bolder;cursor: pointer;">术前核查单（新）</font>
														<font class="operation_examine" style="display:none;font-size: 16px;font-weight: bolder;cursor: pointer;">术前核查（旧）</font>
													</div>
												</div>
											</li>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class="">4、麻醉方式：</span></div>
													<div><label><input name="anesthesiaMethod" type="checkbox" value="(1)、局部麻醉" />(1)、局部麻醉</label></div>
													<div><label><input name="anesthesiaMethod" type="checkbox" value="(2)、必要时加镇痛镇静治疗" />(2)、必要时加镇痛镇静治疗</label></div>
												</div>
											</li>
										</ul>
									</div>	
								</td>
							
								<td style="width:23%;">
									<div style="">
										<ul>
											<li><label>5、<input name="operator" type="checkbox" value="5、手术" id="operator"/>手术</label></li>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class="">6、愈合帽放置：</span></div>
													<div><label><input name="healing_cap_station" type="checkbox" value="1、 金帽" />(1)、 金帽</label></div>
													<div><label><input name="healing_cap_station" type="checkbox" value="2、银帽" />(2)、银帽</label></div>
												</div>
											</li>
											<li>
												<div class="preparation-ul">
													<span style="font-weight: bold;" class="">7、基台放置：</span>
													<label><input name="abutment_station" type="checkbox" value="(1)、愈合基台" />(1)、愈合基台</label>
													<label><input name="abutment_station" type="checkbox" value="(2)、复合基台放置" />(2)、复合基台放置</label><br/>	
													<label><input name="abutment_station" type="checkbox" value="(3)、螺丝" />(3)、螺丝</label>
												</div>
											</li>
										</ul>
									</div>	
								</td>
								
								<td style="width:17%;">
									<div style="">
										<ul>
											<li><label>8、<input name="antimicrobial_use" type="checkbox" value="8、微创" id="antimicrobial_use"/>微创</label></li>
											<li>
												<div class="preparation-ul">
													<div style="margin-top: 7px;"><label>9、<input name="before_Modulo_bite" id="before_Modulo_bite" type="checkbox" value="术中用药" />术中用药</label></div>
													<div><label><input style="width: 120px;" name="visit_time" id="visit_time" type="text" value=""/></label></div>
												</div>
											</li>
											<li><label>10、<input name="enterLclj" type="checkbox" value="10、临床路径录入" id="enterLclj"/>临床路径录入</label></li>
											<li><label>11、<input name="collutory" type="checkbox" value="11、漱口液含漱、抗菌药物使用3-5天" id="collutory"/>漱口液含漱、抗菌药物使用3-5天</label></li>
										</ul>
									</div>	
								</td>
								<td style="width:20%;">
									<div style="">
										<ul>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class="">12、拍X片：</span></div>
													<div><label><input name="assist_operation" type="checkbox" value="曲面断层片" />曲面断层片</label></div>
													<div><label><input name="assist_operation" type="checkbox" value="小牙片" />小牙片</label></div>
												</div>
											</li>
											<!-- <li><label><input name="announcements" type="checkbox" value="13、交代注意事项" id="announcements"/>13、交代注意事项 </label></li> -->
											<li><div class="preparation-ul">
													<div><span style="font-weight: bold;margin-bottom: 5px;" class="">13、交代注意事项：</span></div>
													<div><label><input name="intraoperativeMedication" type="radio" value="1" /><font class="postoperationItem">种植牙术后注意事项</font></label></div>
<%--													<div><label><input name=intraoperativeMedication type="checkbox" value="半口、全口" /><font class="attention_wholehalf">半口、全口</font></label></div>--%>
<%--													<div><label><input name="intraoperativeMedication" type="checkbox" value="局部" /><font class="attention_topo">局部</font></label></div>--%>
											</div></li>
											<li><label>14、预约下次复查时间：<input class="time_initialize time_select" name="next_hospital_time" type="text" value="" id="next_hospital_time" style="width: 105px;vertical-align: middle;"/></label></li> 
											<li>
												<label>15、<input name="opration_record" type="checkbox" value="15、术者完成手术记录" id="opration_record"/>
<%--													<font class="operation_record">术者完成手术记录</font>--%>
													<font class="operation_record">1.术者完成手术记录</font>
													<font class="operationRecord">2.种植牙手术记录</font>
												</label>
											</li>
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
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">
var id = parent.seqId;
var nodeSta = parent.nodeStas;
var contextPath = '<%=contextPath%>';
var ordNumber = parent.ordNumber;
var nodeId = parent.nodeId1;//获得点击节点的id
var orderNumber = parent.ordNumber;
var lcljInfoObj=parent.lcljInfo;  //整个临床路径信息
$(function(){
	/* $('#operation_time').datetimepicker({ 
	    beforeShow:function(input) { 
	        $(input).css({ 
	            "z-index": 999999 
	        }); 
	    } 
	}); */
	//时间选择
    $(".time_select").datetimepicker({
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
    checkOptions();//判断要填写的选项是否已填写并选中
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
			//console.log(JSON.stringify(data)+"-------------------hhahhha");
			if(!data.seq_id){
				return;
			}
			if (nodeSta == 2) {
				$('input').attr("disabled",true)
				//$('input').attr("readonly", true);
				$('textarea').attr("readonly","readonly");
			}
			$("#operation_time").val(data.operationTime);
			$("#remark").val(data.remark);
			$("#next_hospital_time").val(data.next_hospital_time);//下次来院时间
			$("#visit_time").val(data.visit_time);//术中用药
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
			
			select_announcements = data.announcements;
			$("input[name='announcements']").each(function(){
				if($(this).val()==select_announcements){
				   $(this).attr("checked","checked");
				}
			})
			
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
			
			
			//拍X片
			var select_assist_operation = data.assist_operation;
			var disease_id = select_assist_operation.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='assist_operation']").each(function(){
					if($(this).val()==disease_id[i]){
					   $(this).attr("checked","checked");
					}
				})
			}
			
			//术中用药
			var before_Modulo_bite = data.before_modulo_bite;
			if (before_Modulo_bite) {
				$("#before_Modulo_bite").attr("checked","checked");
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
			
			//交代注意事项赋值
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

//注意事项 全口，半口
$(".attention_wholehalf").click(function(){
	parent.layer.open({
		title:"种植术后医嘱",
		type:2,
		closeBtn:1,
		content:contextPath + "/ZzblViewAct/toZzllAttentionWholeHalf.act",
		area:['80%','80%'],
		cancel: function(){ 
			/* console.log("点击了右上角关闭按钮！"); */     
		}
	});
	
}); 

//注意事项 局部
$(".attention_topo").click(function(){
	parent.layer.open({
		title:"种植牙术后注意事项",
		type:2,
		closeBtn:1,
		content:contextPath + "/ZzblViewAct/toZzllAttentionTopo.act",
		area:['80%','80%'],
		cancel: function(){ 
			/* console.log("点击了右上角关闭按钮！"); */     
		}
	}); 
});

//术前核查    
$(".operation_examine").click(function(){
	parent.layer.open({
		title:"种植牙术前安全核查单",
		type:2,
		closeBtn:1,
		content:contextPath + "/ZzblViewAct/toOperationExamineInfor.act",
		area:['80%','80%'],
		cancel: function(){ 
			/* console.log("点击了右上角关闭按钮！"); */     
		}
	}); 
}); 

//种植牙手术记录     
$(".operation_record").click(function(){
	parent.layer.open({
		title:"种植牙手术记录",
		type:2,
		closeBtn:1,
		content:contextPath + "/ZzblViewAct/toOperationRecordInfor.act",
		area:['80%','80%'],
		cancel: function(){ 
			/* console.log("点击了右上角关闭按钮！"); */     
		}
	}); 
}); 
//判断要填写的选项是否已填写并选中
function checkOptions(){
	var selectId = parent.patientObj.id;
	var order_number= window.parent.patientObj.orderNumber;//选中患者order_number
	/* 种植牙手术记录 */
	var operationRecordurl = contextPath + '/HUDH_ZzblPlantTeethOperationAct/findZzblOprationById.act';
	$.ajax({
		url: operationRecordurl,
		type:"POST",
		dataType:"json",
		data : {
			 id :  selectId, //临床路径ID
			 order_number : order_number
		},
		success:function(result){
			if(result.seqId){
				$(".operation_record").prev().attr("checked","checked").attr("disabled","disabled");
			}  
			
		}
  });
	/* 术前核查*/
	var operationExamineurl = contextPath + '/HUDH_LCLJAct/findPreoperativeVerification.act';
	$.ajax({
		url: operationExamineurl,
		type:"POST",
		dataType:"json",
		data : {
			 id :  selectId, //临床路径ID
			 order_number : order_number
		},
		success:function(result){
			if(result.seqId){
				$(".verification").prev().attr("checked","checked").attr("disabled","disabled");
			}  
			
		}
  });
	/* 术前核查（新）*/
	var patientoperationExamineurl = contextPath + '/HUDH_MedicalRecordsAct/findVerification.act';
	$.ajax({
		url: patientoperationExamineurl,
		type:"POST",
		dataType:"json",
		data : {
			id :  selectId, //临床路径ID
			order_number : order_number
		},
		success:function(result){
			if(result.length>0){
				$(".verification").prev().attr("checked","checked").attr("disabled","disabled");
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