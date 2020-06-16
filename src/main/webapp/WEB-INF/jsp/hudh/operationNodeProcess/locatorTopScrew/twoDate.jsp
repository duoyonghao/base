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
	table{
		margin-top: 20px;
	}
	table li{
		height:40px;
	}
	input[type="checkbox"] {
    	vertical-align: sub;
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
											<li><label>1、二期手术时间：<input class="time_initialize" name="twoDateStitchesTime" type="text" id="twoDateStitchesTime" value="" style="width: 105px;"/></label></li>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class="">2、拍X片：</span></div>
													<div><label><input name="abutment_station" type="checkbox" value="曲面断层片" />曲面断层片</label></div>
													<div><label><input name="abutment_station" type="checkbox" value="小牙片" />小牙片</label></div>
												</div>
											</li>
											<li><label>3、检查种植区清洁情况：
													<select id="check_wound" name="check_wound" style="width: 80px;">
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
											<li><label>4、检查种植体骨结合情况：
													<select id=checkPlantBoneCombine name="checkPlantBoneCombine" style="width: 80px;">
														<option>-请选择-</option>
														<option>优</option>
														<option>良</option>
														<option>中</option>
														<option>差</option>
													</select>
											    </label></li>
											<li><label><input name="completeTwoOperation" type="checkbox" value="5、完成二期手术" id="completeTwoOperation">5、完成二期手术</label></li>
											<li><label><input name="announcements" type="checkbox" value="6、交代术后注意事项" id="announcements"/>6、交代术后注意事项</label></li>
										</ul>
									</div>	
								</td>
								
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label><input name="collutory" type="checkbox" value="7、漱口液含漱" id="collutory"/>7、漱口液含漱</label></li>
											<li><label>8、预约下次来院时间：<input class="time_initialize" name="next_hospital_time" type="text" value="" id="next_hospital_time" style="width: 105px;"/></label></li> 
											<li><label><input name="opration_record" type="checkbox" value="9、术者完成手术记录" id="opration_record"/>9、术者完成手术记录</label></li>
										</ul>
									</div>	
								</td>
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label><input name="complete_case_record" type="checkbox" value="10、临床路径录入" id="complete_case_record"/>10、临床路径录入</label></li>
											<li><label>11、种植体有无变异：<input name="accord" type="radio" value="有" />有<input name="accord" type="radio" value="无" />无</label></li>
										<li><label><input name="connectingBridge" type="checkbox" value="12、请上级医师会诊" id="connectingBridge"/>12、请上级医师会诊</label></li>
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
			//console.log(JSON.stringify(data)+"---------------fa");
			if (nodeSta == 2) {
				$('input').attr("disabled",true);
				$('select').attr("disabled",true);
				//$('input').attr("readonly", true);
				$('textarea').attr("readonly","readonly");
			}
			$("#twoDateStitchesTime").val(data.twodatestitchestime);
			$("#checkPlantBoneCombine").val(data.checkplantbonecombine);
			var checkPlantBoneCombine = $("#checkPlantBoneCombine").find("input[type='checkbox']");
			select_checkPlantBoneCombine = data.checkplantbonecombine;
			if (select_checkPlantBoneCombine) {
				$("#checkPlantBoneCombine").attr("checked","checked");
			}
			var completeTwoOperation = $("#completeTwoOperation").find("input[type='checkbox']");
			select_completeTwoOperation = data.completetwooperation;
			if (select_completeTwoOperation) {
				$("#completeTwoOperation").attr("checked","checked");
			}
			var twoOperationAttention = $("#twoOperationAttention").find("input[type='checkbox']");
			select_twoOperationAttention = data.twooperationattention;
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
			var is_stitches = $("#is_stitches").find("input[type='checkbox']");
			select_is_stitches = data.is_stitches;
			if (select_is_stitches) {
				$("#is_stitches").attr("checked","checked");
			}
			var small_teeth = $("#small_teeth").find("input[type='checkbox']");
			select_small_teeth = data.small_teeth;
			if (select_small_teeth) {
				$("#small_teeth").attr("checked","checked");
			}
			var opration_record = $("#opration_record").find("input[type='checkbox']");
			select_opration_record = data.opration_record;
			if (select_opration_record) {
				$("#opration_record").attr("checked","checked");
			}
			var panoramic_view_piece = $("#panoramic_view_piece").find("input[type='checkbox']");
			select_panoramic_view_piece = data.panoramic_view_piece;
			if (select_panoramic_view_piece) {
				$("#panoramic_view_piece").attr("checked","checked");
			}
			
			//检查项目赋值
			var select_abutment_station = data.abutment_station;
			var disease_id = select_abutment_station.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='abutment_station']").each(function(){
					if($(this).val()==disease_id[i]){
					   $(this).attr("checked","checked");
					}
				})
			}
			
			//种植体有无变异
			accord = data.accord;
			$("input[name='accord']").each(function(){
				if($(this).val()==accord){
				   $(this).attr("checked","checked");
				}
			})
			
			//请上级医师会诊
			var connectingbridge = $("#connectingBridge").find("input[type='checkbox']");
			select_connectingbridge = data.connectingbridge;
			if (select_connectingbridge) {
				$("#connectingBridge").attr("checked","checked");
			}
			
			$("#check_wound").val(data.check_wound);
			$("#next_hospital_time").val(data.next_hospital_time);
			$("#remark").val(data.remark);
		}
	});
}
</script>
</html>