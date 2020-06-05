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
		<table align="center">
			<tbody>
							<tr>
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label>1、来院时间：<input name="visit_time" type="text" id="visit_time" value="" style="width: 105px;"/></label></li>
											<li><label>2、检查种植区愈合情况：
												<select id="checkPlantSituation" name="checkPlantSituation" style="width: 80px;">
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
							
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label>3、检查种植体骨结合情况：
													<select id="check_wound" name="check_wound" style="width: 80px;">
														<option>-请选择-</option>
														<option>优</option>
														<option>良</option>
														<option>中</option>
														<option>差</option>
													</select>
											    </label></li>
											<li><label><input name="tryBaseStationInnerCrown" type="checkbox" value="4、基底冠于口内再连接" id="tryBaseStationInnerCrown"/>4、基底冠于口内再连接</label></li>
										</ul>
									</div>	
								</td>
								
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label><input name="secondGetModulus" type="checkbox" value="5、二次取模" id="secondGetModulus"/>5、二次取模</label></li>
											<li><label><input name="ectolophUse" type="checkbox" value="6、试支架或基底冠" id="ectolophUse"/>6、试支架或基底冠</label></li>
											<li><label><input name="colorimetric" type="checkbox" value="7、比色" id="colorimetric"/>7、比色</label></li>
										</ul>
									</div>
								</td>
								
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label><input name="announcements" type="checkbox" value="8、交代注意事项" id="announcements"/>8、交代注意事项</label></li>
											<li><label><input name="complete_case_record" type="checkbox" value="9、完成病例记录" id="complete_case_record"/>9、完成病例记录</label></li>
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
    $("#next_hospital_time").datetimepicker({
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
			if (nodeSta == 2) {
				$('input').attr("disabled",true);
				$('select').attr("disabled",true);
				//$('input').attr("readonly", true);
				$('textarea').attr("readonly","readonly");
			}
			$("#visit_time").val(data.visit_time);
			$("#checkPlantSituation").val(data.checkplantsituation);
			var tryBaseStationInnerCrown = $("#tryBaseStationInnerCrown").find("input[type='checkbox']");
			select_tryBaseStationInnerCrown = data.trybasestationinnercrown;
			if (select_tryBaseStationInnerCrown) {
				$("#tryBaseStationInnerCrown").attr("checked","checked");
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
			var ectolophUse = $("#ectolophUse").find("input[type='checkbox']");
			select_ectolophUse = data.ectolophuse;
			if (select_ectolophUse) {
				$("#ectolophUse").attr("checked","checked");
			}
			var secondGetModulus = $("#secondGetModulus").find("input[type='checkbox']");
			select_secondGetModulus = data.secondgetmodulus;
			if (select_secondGetModulus) {
				$("#secondGetModulus").attr("checked","checked");
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
			var colorimetric = $("#colorimetric").find("input[type='checkbox']");
			select_colorimetric = data.colorimetric;
			if (select_colorimetric) {
				$("#colorimetric").attr("checked","checked");
			}
			
			$("#check_wound").val(data.check_wound);
			$("#next_hospital_time").val(data.next_hospital_time);
			$("#check_surgical_healing").val(data.check_surgical_healing);
			$("#remark").val(data.remark);
		}
	});
}
</script>
</html>