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
											<li><label>1、复查时间：<input class="time_initialize" name="review_time" type="text" id="review_time" value="" style="width: 105px;vertical-align: middle;"/></label></li>
											<li><label><input name="is_stitches" type="checkbox" value="2、拆线" id="is_stitches"/>2、拆线</label></li>
											<!-- <li><label><input name="is_loose" type="checkbox" value="3、检查螺丝是否松动 " id="is_loose"/>3、检查螺丝是否松动 </label></li> -->
											<li>
												<div class="preparation-ul">
													<div><span style="font-weight: bold;" class="">3、检查螺丝是否松动：</span></div>
													<div><label><input name="anesthesiaMethod" type="checkbox" value="(1)、是" />(1)、是</label></div>
													<div><label><input name="anesthesiaMethod" type="checkbox" value="(2)、否" />(2)、否</label></div>
												</div>
											</li>
										</ul>
									</div>	
								</td>
							
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label><input name="accord" type="checkbox" value="4、精细调和、封洞 " id="accord"/>4、精细调和、封洞 </label></li>
											<li><label><input name="clean_toothbridge" type="checkbox" value="5、清洁牙桥" id="clean_toothbridge"/>5、清洁牙桥 </label></li>
											<li><label><input name="health_education" type="checkbox" value="6、卫生宣教" id="health_education"/>6、卫生宣教 </label></li>
										</ul>
									</div>	
								</td>
								
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label><input name="neonychium" type="checkbox" value="7、戴保护垫" id="neonychium"/>7、戴保护垫</label></li>
											<li><label><input name="announcements" type="checkbox" value="8、交代注意事项" id="announcements"/>8、交代注意事项</label></li>
										</ul>
									</div>	
								</td>
								<td>
									<div style="margin-left: 68px;">
										<ul>
											<li><label>9、预约下次来院时间：<input class="time_initialize" name="next_hospital_time" type="text" value="" id="next_hospital_time" style="width: 105px;vertical-align: middle;"/></label></li> 
											<li><label><input name="complete_case_record" type="checkbox" value="10、完成病历记录及临床路径录入" id="complete_case_record"/>10、完成病历记录及临床路径录入</label></li>
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
			console.log(JSON.stringify(data)+"-------------haha");
			if (nodeSta == 2) {
				$('input').attr("disabled",true);
				$('select').attr("disabled",true);
				//$('input').attr("readonly", true);
				$('textarea').attr("readonly","readonly");
			}
			
			
			
			$("#review_time").val(data.review_time);
			$("#observe_wound").val(data.observe_wound);
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
			var is_stitches = $("#is_stitches").find("input[type='checkbox']");
			select_is_stitches = data.is_stitches;
			if (select_is_stitches) {
				$("#is_stitches").attr("checked","checked");
			}
			
			//精细调合、封洞
			accord = data.accord;
			if (accord) {
				$("#accord").attr("checked","checked");
			}
			
			//清洁牙桥
			clean_toothbridge = data.clean_toothbridge;
			if (clean_toothbridge) {
				$("#clean_toothbridge").attr("checked","checked");
			}
			
			//戴保护垫
			neonychium = data.neonychium;
			if (neonychium) {
				$("#neonychium").attr("checked","checked");
			}
			
			//卫生宣教
			health_education = data.health_education;
			if (health_education) {
				$("#health_education").attr("checked","checked");
			}
			
			//检查螺丝是否松动
			is_loose = data.is_loose;
			if (is_loose) {
				$("#is_loose").attr("checked","checked");
			}
			
			$("#check_wound").val(data.check_wound);
			$("#next_hospital_time").val(data.next_hospital_time);
			$("#remark").val(data.remark);
		}
	});
}
</script>
</html>