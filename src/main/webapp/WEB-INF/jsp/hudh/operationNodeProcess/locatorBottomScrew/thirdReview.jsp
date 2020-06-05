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
	.preparation-ul input{
		vertical-align: sub;
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
											<li><label><input name="small_teeth" type="checkbox" value="2、拍X片：曲面断层片 小牙片" id="small_teeth"/>2、拍X片 ：曲面断层片 小牙片 </label></li>
											<li><label>3、检查口腔卫生情况：
													<select id="checkCavityCleanSitu" name="checkCavityCleanSitu" style="width: 80px;">
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
							
								<td>
									<div style="margin-left: 68px;">
										<ul>
											 <li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class="">4、牙龈情况：</span></div>
													<div><label><input name="resinSealing" type="checkbox" value="正常" />正常</label></div>
													<div><label><input name="resinSealing" type="checkbox" value="红肿" />红肿</label></div>
													<div><label><input name="resinSealing" type="checkbox" value="溃疡" />溃疡</label></div>
												</div>
											</li>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class="">5、螺丝孔封闭是否完整：</span></div>
													<div><label><input name="makeTransitionDenture" type="radio" value="完整" />完整</label></div>
													<div><label><input name="makeTransitionDenture" type="radio" value="不完整" />不完整</label></div>
												</div>
											</li>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class="">6、检查咬合：</span></div>
													<div><label><input name="checkOcclusion" type="radio" value="优" />优</label></div>
													<div><label><input name="checkOcclusion" type="radio" value="良" />良</label></div>
													<div><label><input name="checkOcclusion" type="radio" value="中" />中</label></div>
													<div><label><input name="checkOcclusion" type="radio" value="差" />差</label></div>
												</div>
											</li>
										</ul>
									</div>	
								</td>
								
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class="">7、牙桥：</span></div>
													<div><label><input name="fixation_tooth_bridge" type="checkbox" value="正常" />正常</label></div>
													<div><label><input name="fixation_tooth_bridge" type="checkbox" value="松动" />松动</label></div>
													<div><label><input name="fixation_tooth_bridge" type="checkbox" value="断裂" />断裂</label></div>
												</div>
											</li>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class="">8、是否崩瓷：</span></div>
													<div><label><input name="decision_bite" type="radio" value="无崩瓷" />无崩瓷</label></div>
													<div><label><input name="decision_bite" type="radio" value="崩瓷" />崩瓷</label></div>
												</div>
											</li>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class="">9、植体是否吸收：</span></div>
													<div><label><input name="upper_frame" type="radio" value="无吸收" />无吸收</label></div>
													<div><label><input name="upper_frame" type="radio" value="吸收" />吸收</label></div>
												</div>
											</li>
										</ul>
									</div>
								</td>
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label><input name="announcements" type="checkbox" value="10、交代注意事项" id="announcements"/>10、交代注意事项</label></li>
											<li><label>11、预约下次来院时间：<input class="time_initialize" name="review_time" type="text" value="" id="review_time" style="width: 105px;"/></label></li>
											<li><label><input name="complete_case_record" type="checkbox" value="12、完成病历记录并录入临床路径" id="complete_case_record"/>12、完成病历记录并录入临床路径</label></li>
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
   		pickerPosition: "bottom-right"
    });
	//时间选择
    $("#review_time").datetimepicker({
        language:  'zh-CN',  
   		minView:2,
        format: 'yyyy-mm-dd',
   		autoclose : true,//选中之后自动隐藏日期选择框   
   		pickerPosition: "bottom-right"
    });
    initReviewTime();
});

function initReviewTime() {
	$.ajax({
		url: contextPath + "/HUDH_LcljOperationNodeInforAct/selectOperationNodeInforByOrdernumberAndNodeId.act",
		type:"POST",
		dataType:"json",
		data: {
			"nodeId" : nodeId,
			"orderNumber" : orderNumber
		},
		success:function(data){
			//console.log(JSON.stringify(data)+"---------");
			if(!data.seq_id){
				return;
			}
			if (nodeSta == 2) {
				$('input').attr("disabled",true);
				$('select').attr("disabled",true);
				//$('input').attr("readonly", true);
				$('textarea').attr("readonly","readonly");
			}
			$("#visit_time").val(data.visit_time);
			$("#review_time").val(data.review_time);//预约下次来院时间
			$("#askAboutUsage").val(data.askaboutusage);
			$("#checkCavityCleanSitu").val(data.checkcavitycleansitu);//检查口腔卫生情况
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
			//交代注意事项
			var announcements = $("#announcements").find("input[type='checkbox']");
			select_announcements = data.announcements;
			if (select_announcements) {
				$("#announcements").attr("checked","checked");
			}
			var small_teeth = $("#small_teeth").find("input[type='checkbox']");
			select_small_teeth = data.small_teeth;
			if (select_small_teeth) {
				$("#small_teeth").attr("checked","checked");
			}
			var wearTooth = $("#wearTooth").find("input[type='checkbox']");
			select_wearTooth = data.wearTooth;
			if (select_wearTooth) {
				$("#wearTooth").attr("checked","checked");
			}
			var isphotograph = $("#isphotograph").find("input[type='checkbox']");
			select_isphotograph = data.isphotograph;
			if (select_isphotograph) {
				$("#isphotograph").attr("checked","checked");
			}
			var is_stitches = $("#is_stitches").find("input[type='checkbox']");
			select_is_stitches = data.is_stitches;
			if (select_is_stitches) {
				$("#is_stitches").attr("checked","checked");
			}
			
			//拍X片
			select_small_teeth = data.small_teeth;
			if (select_small_teeth) {
				$("#small_teeth").attr("checked","checked");
			}
			
			//检查咬合
			select_checkOcclusion = data.checkocclusion;
			$("input[name='checkOcclusion']").each(function(){
				if($(this).val()==select_checkOcclusion){
				   $(this).attr("checked","checked");
				}
			})
			//牙龈情况
			var resinSealing = data.resinsealing;
			var disease_id = resinSealing.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='resinSealing']").each(function(){
					if($(this).val()==disease_id[i]){
					   $(this).attr("checked","checked");
					}
				})
			}
			//螺丝孔封闭是否完整
			makeTransitionDenture = data.maketransitiondenture;
			$("input[name='makeTransitionDenture']").each(function(){
				if($(this).val()==makeTransitionDenture){
				   $(this).attr("checked","checked");
				}
			})
			
			//牙桥
			var fixation_tooth_bridge = data.fixation_tooth_bridge;
			var disease_id = fixation_tooth_bridge.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='fixation_tooth_bridge']").each(function(){
					if($(this).val()==disease_id[i]){
					   $(this).attr("checked","checked");
					}
				})
			}
			//是否崩瓷
			var decision_bite = data.decision_bite;
			$("input[name='decision_bite']").each(function(){
				if($(this).val()==decision_bite){
				   $(this).attr("checked","checked");
				}
			})
			//植体是否吸收
			upper_frame = data.upper_frame;
			$("input[name='upper_frame']").each(function(){
				if($(this).val()==upper_frame){
				   $(this).attr("checked","checked");
				}
			})
			
			
			$("#check_wound").val(data.check_wound);
			$("#next_hospital_time").val(data.next_hospital_time);
			$("#check_surgical_healing").val(data.check_surgical_healing);
			$("#remark").val(data.remark);
		}
	});
}
</script>
</html>