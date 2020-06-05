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
											<li><label><i style="color: red;font-style: normal;">*</i>1、来院时间：<input name="visit_time" type="text" id="visit_time" value="" readonly="readonly" placeholder="请选择日期" style="width: 105px;"/></label></li>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class=""><i style="color: red;font-style: normal;">*</i>2、拍X片：</span></div>
													<div><label><input name="intraoperativeMedication" type="checkbox" value="曲面断层片" />曲面断层片</label></div>
													<div><label><input name="intraoperativeMedication" type="checkbox" value="小牙片" />小牙片</label></div>
												</div>
											</li>
											<li><label><i style="color: red;font-style: normal;">*</i>3、检查口腔卫生情况：
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
							
								<td style="width: 25%;">
									<div style="margin-left: 68px;">
										<ul>
											 <li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class=""><i style="color: red;font-style: normal;">*</i>4、牙龈情况：</span></div>
													<div><label><input name="assist_operation" type="checkbox" value="正常" />正常</label></div>
													<div><label><input name="assist_operation" type="checkbox" value="红肿" />红肿</label></div>
													<div><label><input name="assist_operation" type="checkbox" value="溃疡" />溃疡</label></div>
												</div>
											</li>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class=""><i style="color: red;font-style: normal;">*</i>5、螺丝孔材料是否脱落：</span></div>
													<div><label><input name="makeTransitionDenture" type="radio" value="是" />是</label></div>
													<div><label><input name="makeTransitionDenture" type="radio" value="否" />否</label></div>
												</div>
											</li>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class=""><i style="color: red;font-style: normal;">*</i>6、检查咬合：</span></div>
													<div><label><input name="checkOcclusion" type="radio" value="优" />优</label></div>
													<div><label><input name="checkOcclusion" type="radio" value="良" />良</label></div>
													<div><label><input name="checkOcclusion" type="radio" value="中" />中</label></div>
													<div><label><input name="checkOcclusion" type="radio" value="差" />差</label></div>
												</div>
											</li>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class=""><i style="color: red;font-style: normal;">*</i>7、牙桥：</span></div>
													<div><label><input name="healing_cap_station" type="checkbox" value="正常" />正常</label></div>
													<div><label><input name="healing_cap_station" type="checkbox" value="松动" />松动</label></div>
													<div><label><input name="healing_cap_station" type="checkbox" value="断裂" />断裂</label></div>
												</div>
											</li>
										</ul>
									</div>	
								</td>
								
								<td style="width: 25%;">
									<div style="margin-left: 68px;">
										<ul>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class=""><i style="color: red;font-style: normal;">*</i>8、是否崩瓷：</span></div>
													<div><label><input name="decision_bite" type="radio" value="无崩瓷" />无崩瓷</label></div>
													<div><label><input name="decision_bite" type="radio" value="崩瓷" />崩瓷</label></div>
												</div>
											</li>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class=""><i style="color: red;font-style: normal;">*</i>9、植体是否吸收：</span></div>
													<div><label><input name="upper_frame" type="radio" value="无吸收" />无吸收</label></div>
													<div><label><input name="upper_frame" type="radio" value="吸收" />吸收</label></div>
												</div>
											</li>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class=""><i style="color: red;font-style: normal;">*</i>10、有无变异：</span></div>
													<div><label><input name="is_loose" type="radio" value="有" />有</label></div>
													<div><label><input name="is_loose" type="radio" value="无" />无</label></div>
												</div>
											</li>
											<li><label><input name="connectingBridge" type="checkbox" value="11、请上级医师会诊" id="connectingBridge"/>11、请上级医师会诊</label></li>
										</ul>
									</div>
								</td>
								<td style="width: 25%;">
									<div style="margin-left: 68px;">
										<ul>
											<li><label><i style="color: red;font-style: normal;">*</i><input name="announcements" type="checkbox" value="12、交代注意事项" id="announcements"/>12、交代注意事项</label></li>
											<li><label><i style="color: red;font-style: normal;">*</i>13、预约下次来院时间：<input class="" onfocus="popup()" name="review_time" type="text" value="" id="review_time" style="width: 108px;"/></label></li>
											<li><label><i style="color: red;font-style: normal;">*</i><input name="complete_case_record" type="checkbox" value="14、完成病历记录" id="complete_case_record"/>14、完成病历记录</label></li>
											<li><label><i style="color: red;font-style: normal;">*</i><input name="enterLclj" type="checkbox" value="15、完成临床路径录入" id="enterLclj"/>15、完成临床路径录入</label></li>
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
    $("#visit_time").datetimepicker({
        language:  'zh-CN',  
   		minView:2,
        format: 'yyyy-mm-dd',
   		autoclose : true,//选中之后自动隐藏日期选择框   
   		pickerPosition: "bottom-right"
    });
	/* //时间选择
    $("#review_time").datetimepicker({
        language:  'zh-CN',  
   		minView:2,
        format: 'yyyy-mm-dd',
   		autoclose : true,//选中之后自动隐藏日期选择框   
   		pickerPosition: "bottom-right"
    }); */
    initReviewTime();
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
			var select_intraoperativeMedication = data.intraoperativemedication;
			var disease_id = select_intraoperativeMedication.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='intraoperativeMedication']").each(function(){
					if($(this).val()==disease_id[i]){
					   $(this).attr("checked","checked");
					}
				})
			}
			//临床路径录入
			var enterLclj = data.enterlclj;
			if (enterLclj) {
				$("#enterLclj").attr("checked","checked");
			}
			//检查咬合
			select_checkOcclusion = data.checkocclusion;
			$("input[name='checkOcclusion']").each(function(){
				if($(this).val()==select_checkOcclusion){
				   $(this).attr("checked","checked");
				}
			})
			//牙龈情况
			var assist_operation = data.assist_operation;
			var disease_id = assist_operation.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='assist_operation']").each(function(){
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
			var healing_cap_station = data.healing_cap_station;
			var disease_id = healing_cap_station.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='healing_cap_station']").each(function(){
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
			/* 有无变异 */
			var is_loose = data.is_loose;
			$("input[name='is_loose']").each(function(){
				if($(this).val()==is_loose){
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