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
		margin-top: 15px;
		width: 100%;
	}
	table li{
		height:40px;
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
	<div style="padding-left: 30px;">
		<table align="center">
			<tbody>
							<tr>
								<td style="width: 25%;">
									<div>
										<ul>
											<li><label><i style="color: red;font-style: normal;">*</i>1、复查时间：<input class="time_initialize" name="operation_time" type="text" id="operation_time" value="" readonly="readonly" placeholder="请选择日期" style="width: 105px;vertical-align: middle;"/></label></li>
											<li><label><i style="color: red;font-style: normal;">*</i><input name="is_stitches" type="checkbox" value="2、拍全景片" id="is_stitches"/>2、拍全景片</label></li>
											<li><label><i style="color: red;font-style: normal;">*</i>3、检查术区清洁情况：
													<select id="observe_wound" name="observe_wound" style="width: 80px;">
														<option>-请选择-</option>
														<option>优</option>
														<option>良</option>
														<option>中</option>
														<option>差</option>
													</select>
											    </label></li>
										</ul>
									</div>	
								</td>
							
								<td style="width: 25%;">
									<div style="margin-left: 68px;">
										<ul>
											<li><label><i style="color: red;font-style: normal;">*</i>4、检查咬合情况：
													<select id="check_wound" name="check_wound" style="width: 80px;">
														<option>-请选择-</option>
														<option>优</option>
														<option>良</option>
														<option>中</option>
														<option>差</option>
													</select>
											    </label></li>
											<li><label><i style="color: red;font-style: normal;">*</i><input name="clean_toothbridge" type="checkbox" value="5、清洁牙桥" id="clean_toothbridge"/>5、清洁牙桥 </label></li>
											<li><label><i style="color: red;font-style: normal;">*</i><input name="health_education" type="checkbox" value="6、卫生宣教" id="health_education"/>6、卫生宣教 </label></li>
										</ul>
									</div>	
								</td>
								
								<td style="width: 25%;">
									<div style="margin-left: 68px;">
										<ul>
											<li><label><i style="color: red;font-style: normal;">*</i><input name="preoperation_one_houres" type="checkbox" value="7、同期一侧外提升植骨" id="preoperation_one_houres"/>7、同期一侧外提升植骨</label></li>
											<li>
												<div class="preparation-ul">
													<span style="font-weight: bold;" class=""><i style="color: red;font-style: normal;">*</i>8、有无变异：</span>
													<label><input name="is_loose" type="radio" value="有" />有</label>
													<label><input name="is_loose" type="radio" value="无" />无</label>
												</div>
											</li>
											<li><label><input name="nostitches" type="checkbox" value="9、请上级会诊"  id="nostitches">9、请上级会诊</label></li>
										</ul>
									</div>	
								</td>
								
								<td style="width: 25%;">
									<div style="margin-left: 68px;">
										<ul>
											<li><label><i style="color: red;font-style: normal;">*</i><input name="announcements" type="checkbox" value="10、交代注意事项" id="announcements"/>10、交代注意事项</label></li>
											<li><label><i style="color: red;font-style: normal;">*</i>11、预约下次来院时间：<input class="" onfocus="popup()" name="next_hospital_time" type="text" value="" id="next_hospital_time" style="width: 108px;vertical-align: middle;"/></label></li> 
											<li><label><i style="color: red;font-style: normal;">*</i><input name="complete_case_record" type="checkbox" value="12、完成病历记录" id="complete_case_record"/>12、完成病历记录</label></li>
											<li><label><i style="color: red;font-style: normal;">*</i><input name="accord" type="checkbox" value="13、完成临床路径录入" id="accord"/>13、完成临床路径录入</label></li>
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
//alert(nodeSta);
$(function(){
	//时间选择
    $(".time_initialize").datetimepicker({
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
			var complete_case_record = $("#complete_case_record").find("input[type='checkbox']");
			select_complete_case_record = data.complete_case_record;
			if (select_complete_case_record) {
				$("#complete_case_record").attr("checked","checked");
			}
			var observe_wound = $("#observe_wound").find("input[type='checkbox']");
			select_observe_wound = data.observe_wound;
			if (select_observe_wound) {
				$("#observe_wound").attr("checked","checked");
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
			var accord = $("#accord").find("input[type='checkbox']");
			select_accord = data.accord;
			if (select_accord) {
				$("#accord").attr("checked","checked");
			}
			
			//清洁牙桥
			clean_toothbridge = data.clean_toothbridge;
			if (clean_toothbridge) {
				$("#clean_toothbridge").attr("checked","checked");
			}
			//卫生宣教
			health_education = data.health_education;
			if (health_education) {
				$("#health_education").attr("checked","checked");
			}
			/* 外提升植骨 */
			var preoperation_one_houres = $("#preoperation_one_houres").find("input[type='checkbox']");
			select_preoperation_one_houres = data.preoperation_one_houres;
			if (select_preoperation_one_houres) {
				$("#preoperation_one_houres").attr("checked","checked");
			}
			/* 有无变异赋值 */
			var is_loose = data.is_loose;
			$("input[name='is_loose']").each(function(){
				if($(this).val()==is_loose){
				   $(this).attr("checked","checked");
				}
			})
			var nostitches = $("#nostitches").find("input[type='checkbox']");
			select_nostitches = data.nostitches;
			if (select_nostitches) {
				$("#nostitches").attr("checked","checked");
			}
			
			$("#check_wound").val(data.check_wound);
			$("#next_hospital_time").val(data.next_hospital_time);
			$("#remark").val(data.remark);
		}
	});
}
</script>
</html>