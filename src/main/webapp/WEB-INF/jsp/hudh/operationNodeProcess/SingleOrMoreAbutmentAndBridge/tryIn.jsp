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
		width:100%;
 		margin-top: 20px; 
	}
	table li{
		height:40px;
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
		#remark{
	    width: 96%;
	}
	.commonText{
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
									<div style="">
										<ul>
											<li><label><i style="color: red;font-style: normal;">*</i>1、来院时间：<input class="time_initialize" name="visit_time" readonly="readonly" placeholder="请选择日期" type="text" id="visit_time" value="" style="width: 105px;"/></label></li>
											<li><label><i style="color: red;font-style: normal;">*</i>2、检查种植体骨结合情况：
													<select id="checkCavityCleanSitu" name="checkCavityCleanSitu" style="width: 80px;">
														<option>-请选择-</option>
														<option>优</option>
														<option>良</option>
														<option>中</option>
														<option>差</option>
													</select>
											    </label></li>
											    <li><label><i style="color: red;font-style: normal;">*</i>3、检查口腔清洁情况：
													<select id="satisfactionPatients" name="satisfactionPatients" style="width: 80px;">
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
							
								<td style="width:25%;">
									<div style="margin-left: 68px;">
										<ul>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class=""><span style="color: red;">*</span>4、试戴是否正常：</span></div>
													<div><label><input name="health_education" type="radio" value="正常"/>正常</label></div>
													<div><label><input name="health_education" type="radio" value="非正常"/>非正常</label></div>
												</div>
											</li>
											<!-- <li><label>5、局部连桥：<input name="localBridge" type="text" value="" id="localBridge" style="width: 105px;"/></label></li> -->
											<li><label><i style="color: red;font-style: normal;">*</i><input name="announcements" type="checkbox" value="5、交代注意事项" id="announcements"/>5、交代注意事项</label></li>
											<!-- <li><label>4、预约下次来院时间：<input class="time_initialize" name="next_hospital_time" type="text" value="" id="next_hospital_time" style="width: 105px;"/></label></li>  -->
										</ul>
									</div>	
								</td>
								
								<td style="width:25%;">
									<div style="margin-left: 68px;">
										<ul>
											<!-- <li><label><input name="announcements" type="checkbox" value="5、交代注意事项" id="announcements"/>5、交代注意事项</label></li> -->
											<li><label><i style="color: red;font-style: normal;">*</i>6、预约下次来院时间：<input class="" name="next_hospital_time" onfocus="popup()" type="text" value="" id="next_hospital_time" style="width: 108px;"/></label></li> 
											<li><label><i style="color: red;font-style: normal;">*</i><input name="complete_case_record" type="checkbox" value="7、完成病历记录" id="complete_case_record"/>7、完成病历记录</label></li>
										</ul>
									</div>	
								</td>
								
								<td style="width:25%;">
									<div style="margin-left: 68px;">
										<ul>
											<!-- <li><label><input name="announcements" type="checkbox" value="5、交代注意事项" id="announcements"/>5、交代注意事项</label></li> -->
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class=""><span style="color: red;">*</span>8、有无变异：</span></div>
													<div><label><input name="is_loose" type="radio" value="有" />有</label></div>
													<div><label><input name="is_loose" type="radio" value="无" />无</label></div>
												</div>
											</li>
											<li><label><input name="isphotograph" type="checkbox" value="9、请上级医师会诊" id="isphotograph"/>9、请上级医师会诊</label></li>
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
var username = parent.patientObj.username; /*获取患者姓名 2019-7-24 */
var usercode = parent.patientObj.blcode;   /*获取或者病历号	 2019-7-24 */
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
	
    initTryIn();
});	

/* zsy2019-7-20  修改预约下次来院时间 */
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
	console.log("jashjkah");
	window.parent.layer.open({
		type: 2,
		title: '预约',
		shadeClose: false,
		shade: 0.6,
		area: ['95%', '95%'],
		content: apppath + '/KQDS_Net_OrderAct/toYyzx2.act?username='+username + '&usercode=' + usercode + '&nodeId=' +nodeid + '&order_number=' + orderNumber,
	});
}


function initTryIn() {
	$.ajax({
		url: contextPath + "/HUDH_LcljOperationNodeInforAct/selectOperationNodeInforByOrdernumberAndNodeId.act",
		type:"POST",
		dataType:"json",
		data: {
			"nodeId" : nodeId,
			"orderNumber" : orderNumber
		},
		success:function(data){
			//console.log(JSON.stringify(data)+"---------试戴");
			if (nodeSta == 2) {
				$('input').attr("disabled",true);
				$('select').attr("disabled",true);
				//$('input').attr("readonly", true);
				$('textarea').attr("readonly","readonly");
			}
			$("#visit_time").val(data.visit_time);
			$("#checkCavityCleanSitu").val(data.checkcavitycleansitu);
			$("#satisfactionPatients").val(data.satisfactionpatients);
			$("#checkPlantSituation").val(data.checkplantsituation);
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
			var isphotograph = $("#isphotograph").find("input[type='checkbox']");
			isphotograph = data.isphotograph;
			if (isphotograph) {
				$("#isphotograph").attr("checked","checked");
			}
			var is_stitches = $("#is_stitches").find("input[type='checkbox']");
			select_is_stitches = data.is_stitches;
			if (select_is_stitches) {
				$("#is_stitches").attr("checked","checked");
			}
			//var try_in = $("#try_in").find("input[type='checkbox']");
			/* select_try_in = data.try_in;
			if (select_try_in) {
				$("#try_in").attr("checked","checked");
			} */
			//试戴是否正常
			var health_education = data.health_education;
			$("input[name='health_education']").each(function(){
				if($(this).val()==health_education){
				   $(this).attr("checked","checked");
				}
			})
			/* 有无变异 */
			var is_loose = data.is_loose;
			$("input[name='is_loose']").each(function(){
				if($(this).val()==is_loose){
				   $(this).attr("checked","checked");
				}
			})
			
			$("#check_wound").val(data.check_wound);
			$("#localBridge").val(data.localbridge);
			$("#check_surgical_healing").val(data.check_surgical_healing);
			$("#next_hospital_time").val(data.next_hospital_time);
			//有无变异
			var upper_frame = data.upper_frame;
			$("input[name='upper_frame']").each(function(){
				if($(this).val()==upper_frame){
				   $(this).attr("checked","checked");
				}
			})
			$("#remark").val(data.remark);
		}
	});
}
</script>
</html>