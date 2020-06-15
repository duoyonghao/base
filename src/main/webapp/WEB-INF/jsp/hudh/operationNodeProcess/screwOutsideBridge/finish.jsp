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
		margin-top: 10px;
		width: 100%;
	}

	table li{
		height:35px;
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
	<div>
		<table align="center">
			<tbody>
							<tr>
								<td style="width:50%;">
									<div style="margin-left: 68px;">
										<ul>
											<li><label><input name="finish_norm_first" type="checkbox" value="1、X线片显示种植体位置、轴向良好，周围无透射区" id="finish_norm_first">1、X线片显示种植体位置、轴向良好，周围无透射区</label></li>	
											<li><label><input name="finish_norm_second" type="checkbox" value="2、种植体无动度" id="finish_norm_second">2、种植体无动度</label></li>
											<li><label><input name="finish_norm_third" type="checkbox" value="3、种植修复体能正常行使功能" id="finish_norm_third">3、种植修复体能正常行使功能</label></li>										
										</ul>
									</div>	
								</td>
							
								<td style="width:50%;">
									<div style="margin-left: 68px;">
										<ul>
											<li><label><input name="finish_norm_fourth" type="checkbox" value="4、伤口愈合良好" id="finish_norm_fourth">4、伤口愈合良好</label></li>
											<li><label><input name="finish_norm_fifth" type="checkbox" value="5、无持续性或不可逆的症状，没有需要临床处理的并发症和/或合并症" id="finish_norm_fifth">5、无持续性或不可逆的症状，没有需要临床处理的并发症和/或合并症</label></li>
										</ul>
									</div>	
								</td>
							</tr>
					</tbody>
			</table>
			<div style="padding-left: 68px;">
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
    initQmdyh();
});

function initQmdyh() {
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
			$("#checkPlantBoneCombine").val(data.checkplantbonecombine);


			//标准一
			finish_norm_first = data.finish_norm_first;
			if (finish_norm_first) {
				$("#finish_norm_first").attr("checked","checked");
			}
			//标准二
			finish_norm_second = data.finish_norm_second;
			if (finish_norm_second) {
				$("#finish_norm_second").attr("checked","checked");
			}
			//标准三
			finish_norm_third = data.finish_norm_third;
			if (finish_norm_third) {
				$("#finish_norm_third").attr("checked","checked");
			}
			//标准四
			finish_norm_fourth = data.finish_norm_fourth;
			if (finish_norm_fourth) {
				$("#finish_norm_fourth").attr("checked","checked");
			}
			//标准五
			finish_norm_fifth = data.finish_norm_fifth;
			if (finish_norm_fifth) {
				$("#finish_norm_fifth").attr("checked","checked");
			}
			
			$("#remark").val(data.remark);
		}
	});
}
</script>
</html>