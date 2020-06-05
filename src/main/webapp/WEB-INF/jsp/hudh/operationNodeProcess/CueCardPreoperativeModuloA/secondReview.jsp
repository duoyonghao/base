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
	input{
		vertical-align: text-top;
	}
	table{
		margin-top: 10px;
		width: 100%;
	}
	table li{
		height:35px;
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
	.preparation-ul label{
		margin-bottom: 10px;
		margin-right: 5px;
	}
	/* 牙位图 */
	.zl_toothMapdiv{
		overflow: hidden;
	}
	.zl_toothMapdiv>span{
		margin-right: 5%;
		float: left;
		margin-top: 18px;
	}
	.zl_toothMapdiv .tooth_map{
		float:left;
		display: inline-block;
		width: 55%;
	    height: 50px;
	}
	.zl_toothMapdiv .tooth_map>li{
		width:49%;
		height:25px;
		float:left;
	}
	.zl_toothMapdiv .tooth_map>li>input{
		border:0px;
	    width: 70%;
	    margin-left: 5%;
	    text-align: center;
	    height: 25px;
	}
	.zl_toothMapdiv .tooth_map>li:FIRST-CHILD{
		border-bottom: 1px solid black;
		border-right: 1px solid black;
		
	}
	.zl_toothMapdiv .tooth_map>li:FIRST-CHILD+li{
		border-bottom: 1px solid black;
	}
	.zl_toothMapdiv .tooth_map>li:FIRST-CHILD+li+li{
		border-right: 1px solid black;
	}
	#remark{
	    width: 96%;
	}
	.commonText{
	    display: block;
	    font-size: 14px;
	    font-weight: 600;
	    padding-bottom: 10px;
	}
</style>
</head>
<body>
	<div style="padding-left: 30px">
		<table align="center">
			<tbody>
							<tr>
								<td style="width:25%;">
									<div>
										<ul>
											<li><label><i style="color: red;font-style: normal;">*</i>1、复查时间：<input class="time_initialize" name="operation_time" type="text" id="operation_time" value="" readonly="readonly" placeholder="请选择日期" style="width: 105px;vertical-align:middle;"/></label></li>
											<li><label><i style="color: red;font-style: normal;">*</i>2、术区清洁情况：
												<select id="observe_wound" name="observe_wound" style="width: 80px;">
														<option>-请选择-</option>
														<option>Ⅰ</option>
														<option>Ⅱ</option>
														<option>Ⅲ</option>
														<option>Ⅳ</option>
												</select>
											</label>
											</li>
											<li><label><i style="color: red;font-style: normal;">*</i>3、检查伤口愈合情况：
													<select id="check_wound" name="check_wound" style="width: 80px;">
														<option>-请选择-</option>
														<option>优</option>
														<option>良</option>
														<option>中</option>
														<option>差</option>
													</select>
											    </label>
											</li>
										</ul>
									</div>	
								</td>
							
								<td style="width:25%;">
									<div>
										<ul>
											<li><label><i style="color: red;font-style: normal;">*</i>4、<input name="preoperative_verification" type="checkbox" value="4、根据患者当时病情决定检查项目" id="preoperative_verification"/>根据患者当时病情决定检查项目</label></li>
											<li style="height: auto;"> 
												<!-- 牙位图 -->
												<div class="zl_toothMapdiv">
													<span style="font-weight: bold;">5、牙冠是否松动：</span>
													<ul class="tooth_map">
														<li>
															<input id="extractionleftup1_l" name="extractionleftup1_l" class="tooth_input" onblur="getValue(this.id)" type="text">
														</li>
														<li>
															<input id="extractionleftup1_r" name="extractionleftup1_r" class="tooth_input" onblur="getValue(this.id)" type="text">
														</li>
														<li>
															<input id="extractionleftdown1_l" name="extractionleftdown1_l" class="tooth_input" onblur="getValue(this.id)" type="text">
														</li>
														<li>
															<input id="extractionrightdown1_r" name="extractionrightdown1_r" class="tooth_input" onblur="getValue(this.id)" type="text">
														</li>
													</ul>
												</div>
											</li>
											<!-- <li><label>4、<input name="is_stitches" type="checkbox" value="4、拆线" id="is_stitches"/>拆线</label></li> -->
											<li><label><i style="color: red;font-style: normal;">*</i><input name="announcements" type="checkbox" value="7、交代注意事项" id="announcements"/>6、交代注意事项</label></li> 
										</ul>
									</div>	
								</td>
								
								<td style="width:25%;">
									<div>
										<ul>
											<li><label><i style="color: red;font-style: normal;">*</i>7、预约下次来院时间：<input class="" onfocus="popup()" name="next_hospital_time" type="text" value="" id="next_hospital_time" style="width: 108px;vertical-align:middle;"/></label></li>
											<li><label><i style="color: red;font-style: normal;">*</i><input name="complete_case_record" type="checkbox" value="8、完成病历记录" id="complete_case_record"/>8、完成病历记录</label></li>
											<li><label><i style="color: red;font-style: normal;">*</i><input name="enterLclj" type="checkbox" value="9、完成临床路径录入" id="enterLclj"/>9、完成临床路径录入</label></li>
										</ul>
									</div>
								</td>
								<td style="width:25%;">
									<div>
										<ul>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class=""><i style="color: red;font-style: normal;">*</i>10、种植体有无变异：</span></div>
													<div><label><input name="is_loose" type="radio" value="有" />有</label></div>
													<div><label><input name="is_loose" type="radio" value="无" />无</label></div>
												</div>
											</li>
											<li><label><input name="tryHolderOrBasalCrowns" type="checkbox" value="11、请上级医师会诊" id="isphotograph"/>11、请上级医师会诊</label></li>
										</ul>
									</div>
								</td>
							</tr>
					</tbody>
			</table>
			<div style="">
				<span class="commonText" style="">备注：</span>
				<textarea class="a" rows="" cols="" id="remark" name="remark"></textarea>
			</div>	
			<div style="font-weight: bold;margin-top: 10px;float: right;width: 10%;">
				<font style="color: blue;font-size: 12px;">注意：带红色</font><font style="color: red;font-size: 17px">*</font><font style="color: blue;font-size: 12px;">为必填项</font>
			</div>
	</div>
</body>
<script type="text/javascript">
var id = parent.seqId;
var contextPath = '<%=contextPath%>';
var nodeSta = parent.nodeStas;
var nodeId = parent.nodeId1;//获得点击节点的id
var orderNumber = parent.ordNumber;
var username = parent.patientObj.username; /*获取患者姓名 2019-7-25 */
var usercode = parent.patientObj.blcode;   /*获取或者病历号	 2019-7-25 */
var nodeid = parent.patientObj.nodeid;
var orderNumber = parent.patientObj.orderNumber
$(function(){
	//时间选择
    $(".time_initialize").datetimepicker({
        language:  'zh-CN',  
   		minView:2,
        format: 'yyyy-mm-dd',
   		autoclose : true,//选中之后自动隐藏日期选择框   
   		pickerPosition: "bottom-right"
    });
    initFirstReview();
});

/* zsy2019-7-22  修改预约下次来院时间 */
var apppath = apppath();
function apppath(){//获得根目录
    var strFullPath = window.document.location.href;
    var strPath = window.document.location.pathname;
    var pos = strFullPath.indexOf(strPath);
    var prePath = strFullPath.substring(0, pos);
    var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
    return (prePath + postPath);
}
function popup(){
	window.parent.layer.open({
		type: 2,
		title: '预约',
		shadeClose: false,
		shade: 0.6,
		area: ['95%', '95%'],
		content: apppath + '/KQDS_Net_OrderAct/toYyzx2.act?username='+username + '&usercode=' + usercode + '&nodeId=' +nodeid + '&order_number=' + orderNumber,
	});
}

function initFirstReview() {
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
			if(data.operation_time){
			    $("#operation_time").val(data.operation_time);
			   }else{
			    $("#operation_time").val(data.review_time);
			   }
			$("#observe_wound").val(data.observe_wound);
			var observe_wound = $("#observe_wound").find("input[type='checkbox']");
			select_observe_wound = data.observe_wound;
			if (select_observe_wound) {
				$("#observe_wound").attr("checked","checked");
			}
			var complete_case_record = $("#complete_case_record").find("input[type='checkbox']");
			select_complete_case_record = data.complete_case_record;
			if (select_complete_case_record) {
				$("#complete_case_record").attr("checked","checked");
			}
			var preoperative_verification = $("#preoperative_verification").find("input[type='checkbox']");
			select_preoperative_verification = data.preoperative_verification;
			if (select_preoperative_verification) {
				$("#preoperative_verification").attr("checked","checked");
			}
			var announcements = $("#announcements").find("input[type='checkbox']");
			select_announcements = data.announcements;
			if (select_announcements) {
				$("#announcements").attr("checked","checked");
			}
			var is_stitches = $("#is_stitches").find("input[type='checkbox']");
			select_is_stitches = data.is_stitches;
			if (select_is_stitches) {
				$("#is_stitches").attr("checked","checked");
			}
			var nostitches = $("#nostitches").find("input[type='checkbox']");
			select_nostitches = data.nostitches;
			if (select_nostitches) {
				$("#nostitches").attr("checked","checked");
			}
			
			/* 是否松动 赋值 */
			/* var is_loose = data.is_loose;
			$("input[name='is_loose']").each(function(){
				if($(this).val()==is_loose){
				   $(this).attr("checked","checked");
				}
			}) */
			//临床路径录入
			var enterLclj = data.enterlclj;
			if (enterLclj) {
				$("#enterLclj").attr("checked","checked");
			}
			/* 有无变异赋值 */
			var is_loose = data.is_loose;
			$("input[name='is_loose']").each(function(){
				if($(this).val()==is_loose){
				   $(this).attr("checked","checked");
				}
			})
			/* 请上级医师会诊赋值 */
			var tryholderorbasalcrowns = data.tryholderorbasalcrowns;
			$("input[name='tryHolderOrBasalCrowns']").each(function(){
				if($(this).val()==tryholderorbasalcrowns){
				   $(this).attr("checked","checked");
				}
			})
			
			/* 牙位图 */
			$("#extractionleftup_l").val(data.extractionleftup_l);
			$("#extractionleftup_r").val(data.extractionleftup_r);
			$("#extractionleftdown_l").val(data.extractionleftdown_l);
			$("#extractionleftdown_r").val(data.extractionleftdown_r);
			$("#extractionleftup1_l").val(data.extractionleftup1_l);
			$("#extractionleftup1_r").val(data.extractionleftup1_r);
			$("#extractionleftdown1_l").val(data.extractionleftdown1_l);
			$("#extractionrightdown1_r").val(data.extractionleftdown1_r);
			
			$("#check_wound").val(data.check_wound);
			$("#next_hospital_time").val(data.next_hospital_time);
			$("#remark").val(data.remark);
		}
	});
}
</script>
</html>