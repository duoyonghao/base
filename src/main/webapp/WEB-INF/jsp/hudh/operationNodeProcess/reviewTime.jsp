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
	}
	table td{
		padding-left: 1%;
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
</style>
</head>
<body>
	<div>
		<table align="center">
			<tbody>
							<tr>
								<td style="width:17%;">
									<div>
										<ul>
											<li><label>1、来院时间：<input name="visit_time" type="text" id="visit_time" value="" style="width: 105px;"/></label></li>
											<li><label>2、拍X片 ：<input name="small_teeth" type="checkbox" value="曲面断层片" id="small_teeth"/>曲面断层片 
															   <input name="small_teeth" type="checkbox" value="小牙片" id="small_teeth"/>小牙片 </label></li>
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
											 <li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class="">4、牙龈情况：</span></div>
													<div><label><input name="resinSealing" type="checkbox" value="正常" />正常</label></div>
													<div><label><input name="resinSealing" type="checkbox" value="红肿" />红肿</label></div>
													<div><label><input name="resinSealing" type="checkbox" value="溃疡" />溃疡</label></div>
												</div>
											</li>
										</ul>
									</div>	
								</td>
							
								<td style="width:15%;">
									<div>
										<ul>
											
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class="">5、螺丝孔材料是否脱落：</span></div>
													<div><label><input name="makeTransitionDenture" type="radio" value="是" />是</label></div>
													<div><label><input name="makeTransitionDenture" type="radio" value="否" />否</label></div>
												</div>
											</li>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class="">6、检查咬合：</span></div>
													<div><label><input name="checkOcclusion" type="radio" value="正常" />正常</label></div>
													<div><label><input name="checkOcclusion" type="radio" value="正常" />正常</label></div>
													<!-- <div><label><input name="checkOcclusion" type="radio" value="优" />优</label></div>
													<div><label><input name="checkOcclusion" type="radio" value="良" />良</label></div>
													<div><label><input name="checkOcclusion" type="radio" value="中" />中</label></div>
													<div><label><input name="checkOcclusion" type="radio" value="差" />差</label></div> -->
												</div>
											</li>
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
													<div><span style="font-weight: bold;" class="">8、邻接是否塞牙：</span></div>
													<div><label><input name="fixation_tooth_bridge" type="radio" value="是" />是</label></div>
													<div><label><input name="fixation_tooth_bridge" type="radio" value="否" />否</label></div>
												</div>
											</li>
										</ul>
									</div>	
								</td>
								
								<td style="width:20%;">
									<div>
										<ul>
											<li style="margin-bottom: 30px;height: auto;">
												<!-- 牙位图 -->
												<div class="zl_toothMapdiv">
													<span style="font-weight: bold;">9、植体是否松动：</span>
													<ul class="tooth_map">
														<li>
															<input id="extractionleftup_l" name="extractionleftup_l" class="tooth_input" onblur="getValue(this.id)" type="text">
														</li>
														<li>
															<input id="extractionleftup_r" name="extractionleftup_r" class="tooth_input" onblur="getValue(this.id)" type="text">
														</li>
														<li>
															<input id="extractionleftdown_l" name="extractionleftdown_l" class="tooth_input" onblur="getValue(this.id)" type="text">
														</li>
														<li>
															<input id="extractionleftdown_r" name="extractionleftdown_r" class="tooth_input" onblur="getValue(this.id)" type="text">
														</li>
													</ul>
												</div>
											</li>
											<li style="height: auto;"> 
												<!-- 牙位图 -->
												<div class="zl_toothMapdiv">
													<span style="font-weight: bold;">10、牙冠是否松动：</span>
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
										</ul>
									</div>
								</td>
								
								<td style="width:18%;">
									<div>
										<ul>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class="">11、是否崩瓷：</span></div>
													<div><label><input name="decision_bite" type="radio" value="无崩瓷" />无崩瓷</label></div>
													<div><label><input name="decision_bite" type="radio" value="崩瓷" />崩瓷</label></div>
												</div>
											</li>
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class="">12、植体是否吸收：</span></div>
													<div><label><input name="upper_frame" type="radio" value="无吸收" />无吸收</label></div>
													<div><label><input name="upper_frame" type="radio" value="吸收" />吸收</label></div>
												</div>
											</li>
											<li><label><input name="announcements" type="checkbox" value="12、交代注意事项" id="announcements"/>13、交代注意事项</label></li>
											<li><label>14、预约下次来院时间：<input class="time_initialize" name="review_time" type="text" value="" id="review_time" style="width: 105px;"/></label></li>
										</ul>
									</div>
								</td>
								
								<td style="width:15%;">
									<div>
										<ul>
											<li><label><input name="complete_case_record" type="checkbox" value="15、完成病历记录并录入临床路径" id="complete_case_record"/>15、完成病历记录并录入临床路径</label></li>
											<li><label>16、种植体有无变异：<input name="accord" type="radio" value="有" />有<input name="accord" type="radio" value="无" />无</label></li>
											<li><label><input name="connectingBridge" type="checkbox" value="17、请上级医师会诊" id="connectingBridge"/>17、请上级医师会诊</label></li>
										</ul>
									</div>
								</td>
								<td style="width:10%;"> 
									<div>
										<span class="commonText" style="margin-bottom: 10px;display: block;">备注：</span>
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

/* 获取拼接牙位并校验 */
function getValue(inputObj){
	var inputBool=false;
	var toothArr=[];
	var toothString="";
	//牙位输入框
	var inputVal=$("#"+inputObj).val();
	for (var i = 0; i < inputVal.length; i++) {
		if(inputVal[i]<=8 && inputVal[i]>=1){
			if(toothArr.indexOf(inputVal[i])<0){
				toothArr.push(inputVal[i]);
			}else{
				inputBool=true;
			}
		}else{
			inputBool=true;
		}
	}
	if(inputBool){
		layer.open({
			 title: '提示',
			 content: '请输入正确牙位！(牙位值为1~8,且不能重复)'
		});  
		$("#"+inputObj).val("");
		toothString="";
		
	}
	//toothString=toothArr.join(",");
	//console.log(toothString+"------拼接字符串");//拼接字符串
	return toothString;
};

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
			//console.log(JSON.stringify(data)+"====复查");
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
			$("#extractionleftup_l").val(data.extractionleftup_l);
			$("#extractionleftup_r").val(data.extractionleftup_r);
			$("#extractionleftdown_l").val(data.extractionleftdown_l);
			$("#extractionleftdown_r").val(data.extractionleftdown_r);
			$("#extractionleftup1_l").val(data.extractionleftup1_l);
			$("#extractionleftup1_r").val(data.extractionleftup1_r);
			$("#extractionleftdown1_l").val(data.extractionleftdown1_l);
			$("#extractionrightdown1_r").val(data.extractionleftdown1_r);
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