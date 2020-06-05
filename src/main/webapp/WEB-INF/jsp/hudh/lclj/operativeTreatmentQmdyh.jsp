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
</style>
</head>
<body>
	<div>
		<table align="center"  style="margin-top: 50px;width: 1490px; overflow: hidden;">
			<tbody>
							<tr>
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label>1、来院时间：<input class="time_initialize" name="visit_time" type="text" id="visit_time" value="" style="width: 105px;vertical-align: middle;"/></label></li>
											<li><label>2、检查伤口愈合情况：
													<select id="check_wound" name="check_wound" style="width: 80px;">
														<option>-请选择-</option>
														<option>优</option>
														<option>良</option>
														<option>中</option>
														<option>差</option>
													</select>
											    </label></li>
											    <li><label>3、检查口腔清洁情况：
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
											<li><label>4、检查种植体骨结合情况：
													<select id="checkPlantBoneCombine" name="checkPlantBoneCombine" style="width: 80px;">
														<option>-请选择-</option>
														<option>优</option>
														<option>良</option>
														<option>中</option>
														<option>差</option>
													</select>
											    </label></li>
											<li><label><input name="ismodule" type="checkbox" value="5、取模" id="ismodule">5、取模</label></li>
											<li><label><input name="panoramic_view_piece" type="checkbox" value="6、定咬合" id="panoramic_view_piece"/>6、定咬合</label></li>
										</ul>
									</div>	
								</td>
								
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label><input name="transfer" type="checkbox" value="7、面弓转移，上架" id="transfer">7、面弓转移，上架</label></li>
											<li><label><input name="basalCrowns" type="checkbox" value="8、试基底冠" id="basalCrowns">8、试基底冠</label></li>
											<li><label><input name="colorimetric" type="checkbox" value="9、比色" id="colorimetric"/>9、比色</label></li>
										</ul>
									</div>	
								</td>
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label>10、预约下次来院时间：<input class="time_initialize" name="next_hospital_time" type="text" value="" id="next_hospital_time" style="width: 108px;vertical-align: middle;"/></label></li> 
											<li><label><input name="announcements" type="checkbox" value="11、交代注意事项<" id="announcements"/>11、交代注意事项</label></li>
											<li><label><input name="complete_case_record" type="checkbox" value="12、完成病历记录" id="complete_case_record"/>12、完成病历记录</label></li>
										</ul>
									</div>	
								</td>
								
								<td>
									
									<div style="margin-left: 68px;">
										<span class="commonText" style="vertical-align: top;">备注：</span>
										<textarea class="a" rows="" cols="" id="remark" name="remark"></textarea>
									</div>	
								</td>
							</tr>
							
					</tbody>
			</table>
	</div>
	<div style="margin-top: 70px;border-top:1px solid #ddd;width:1490px;padding-top:40px;padding-bottom:5px;padding-left:15px;" align="center"></div>
	<div style="clear:both;" align="center">
         <a id="searchHzlclj" href="javascript:void(0);"  class="kqdsSearchBtn" onclick = "save();">保存</a>
         <a id="searchHzlclj" href="javascript:void(0);"  class="kqdsSearchBtn" onclick = "variationSave();">变异</a>
    </div>
</body>
<script type="text/javascript">
var id = parent.seqId;
var contextPath = '<%=contextPath%>';
var nodeSta = parent.nodeStas;
var nodeId = parent.nodeId1;//获得点击节点的id
var orderNumber = getUrlParam("orderNumber");
//alert(orderNumber);
$(function(){
	//时间选择
    $(".time_initialize").datetimepicker({
        language:  'zh-CN',  
   		minView:2,
        format: 'yyyy-mm-dd',
   		autoclose : true,//选中之后自动隐藏日期选择框   
   		pickerPosition: "bottom-right"
    });
	//时间选择
    $("#next_hospital_time").datetimepicker({
        language:  'zh-CN',  
   		minView:2,
        format: 'yyyy-mm-dd',
   		autoclose : true,//选中之后自动隐藏日期选择框   
   		pickerPosition: "bottom-right"
    });
	
    initOperativeTreatmentQmdyh();
});
//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
function save() {
	var visit_time = $("#visit_time").val();
	var ismodule = $("#ismodule").val();
	var check_wound = $("#check_wound").val();
	var checkCavityCleanSitu = $("#checkCavityCleanSitu").val()
	var checkPlantBoneCombine = $("#checkPlantBoneCombine").val();
	var panoramic_view_piece = $("#panoramic_view_piece").val();
	var transfer = $("#transfer").val();
	
	var basalCrowns = $("#basalCrowns").val();
	var colorimetric = $("#colorimetric").val();
	var next_hospital_time = $("#next_hospital_time").val();
	var announcements = $("#announcements").val();
	var complete_case_record = $("#complete_case_record").val();
	var remark = $("#remark").val();
	$.ajax({
		url: contextPath + "/HUDH_LcljOperationNodeInforAct/insertOperationNodeInfor.act",
		type:"POST",
		dataType:"json",
		data: {
			"visit_time" : visit_time,
			"ismodule" : ismodule,
			"check_wound" : check_wound,
			"checkCavityCleanSitu" : checkCavityCleanSitu,
			"checkPlantBoneCombine" : checkPlantBoneCombine,
			"panoramic_view_piece" : panoramic_view_piece,
			"transfer" : transfer,
			"remark" : remark,
			"orderNumber" : orderNumber,
			"basalCrowns" : basalCrowns,
			"colorimetric" : colorimetric,
			"next_hospital_time" : next_hospital_time,
			"announcements" : announcements,
			"complete_case_record" : complete_case_record
		},
		async:false,
		success:function(data){
//			alert(JSON.stringify(data));
			layer.alert("保存成功！", {
	            end: function() {
	            	window.parent.location.reload(); //刷新父页面
	                var frameindex = parent.layer.getFrameIndex(window.name);
	                parent.layer.close(frameindex); //再执行关闭
	            }
	      	});
		}
	});
}

function variationSave() {
	var visit_time = $("#visit_time").val();
	var ismodule = $("#ismodule").val();
	var check_wound = $("#check_wound").val();
	var checkCavityCleanSitu = $("#checkCavityCleanSitu").val();
	var checkPlantBoneCombine = $("#checkPlantBoneCombine").val();
	var panoramic_view_piece = $("#panoramic_view_piece").val();
	var transfer = $("#transfer").val();
	
	var basalCrowns = $("#basalCrowns").val();
	var colorimetric = $("#colorimetric").val();
	var next_hospital_time = $("#next_hospital_time").val();
	var announcements = $("#announcements").val();
	var complete_case_record = $("#complete_case_record").val();
	var remark = $("#remark").val();
	$.ajax({
		url: contextPath + "/HUDH_LcljOperationNodeInforAberranceAct/insertOperationNodeInforAberrance.act",
		type:"POST",
		dataType:"json",
		data: {
			"visit_time" : visit_time,
			"ismodule" : ismodule,
			"check_wound" : check_wound,
			"checkCavityCleanSitu" : "checkCavityCleanSitu",
			"checkPlantBoneCombine" : checkPlantBoneCombine,
			"panoramic_view_piece" : panoramic_view_piece,
			"transfer" : transfer,
			"remark" : remark,
			"orderNumber" : orderNumber,
			"basalCrowns" : basalCrowns,
			"colorimetric" : colorimetric,
			"next_hospital_time" : next_hospital_time,
			"announcements" : announcements,
			"complete_case_record" : complete_case_record
		},
		async:false,
		success:function(data){
//			alert(JSON.stringify(data));
			layer.alert("保存成功！", {
	            end: function() {
	            	window.parent.location.reload(); //刷新父页面
	                var frameindex = parent.layer.getFrameIndex(window.name);
	                parent.layer.close(frameindex); //再执行关闭
	            }
	      	});
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

function initOperativeTreatmentQmdyh() {
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
				$('input').attr("disabled",true)
				//$('input').attr("readonly", true);
				$('textarea').attr("readonly","readonly");
			}
			$("#operation_time").val(data.operation_time);
			var ismodule = $("#ismodule").find("input[type='checkbox']");
			select_ismodule = data.ismodule;
			if (select_ismodule) {
				$("#ismodule").attr("checked","checked");
			}
			var preoperative_verification = $("#preoperative_verification").find("input[type='checkbox']");
			select_preoperative_verification = data.preoperative_verification;
			if (select_preoperative_verification) {
				$("#preoperative_verification").attr("checked","checked");
			}
			var confirmOcclusalRelationship = $("#confirmOcclusalRelationship").find("input[type='checkbox']");
			select_confirmOcclusalRelationship = data.confirmocclusalrelationship;
			if (select_confirmOcclusalRelationship) {
				$("#confirmOcclusalRelationship").attr("checked","checked");
			}
			var makeTransitionDenture = $("#makeTransitionDenture").find("input[type='checkbox']");
			select_makeTransitionDenture = data.maketransitiondenture;
			if (select_makeTransitionDenture) {
				$("#makeTransitionDenture").attr("checked","checked");
			}
			var tryInTransitionDenture = $("#tryInTransitionDenture").find("input[type='checkbox']");
			select_tryInTransitionDenture = data.tryintransitiondenture;
			if (select_tryInTransitionDenture) {
				$("#tryInTransitionDenture").attr("checked","checked");
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
			var panoramic_view_piece = $("#panoramic_view_piece").find("input[type='checkbox']");
			select_panoramic_view_piece = data.panoramic_view_piece;
			if (select_panoramic_view_piece) {
				$("#panoramic_view_piece").attr("checked","checked");
			}
			
			$("#check_wound").val(data.check_wound);
			$("#checkCavityCleanSitu").val(data.checkcavitycleansitu)
			$("#next_hospital_time").val(data.next_hospital_time);
			$("#check_surgical_healing").val(data.check_surgical_healing);
			$("#remark").val(data.remark);
		}
	});
}
</script>
</html>