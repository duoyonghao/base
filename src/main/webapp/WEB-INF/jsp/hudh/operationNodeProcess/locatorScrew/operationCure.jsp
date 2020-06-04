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
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.min.js"></script>
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
	.b{
		z-index:100000;
	}
	table{
		width:80%;
		/* border:1px solid red; */
		margin:0 auto;
	}
	table tr td{
		/* border:1px solid blue; */
	}
	table tr td>div{
		margin-left: 5%;
	}
</style>
</head>
<body>
	<div style="margin-top: 0px;">
		<table>
			<tbody>
				<tr>			
					<td style="vertical-align: top;">
						<div style="vertical-align: top;">
							<ul>
								<li><label>1、<input name="Consultation" type="checkbox" value="1、术前1小时口服抗菌药物"/>术前1小时口服抗菌药物</label></li>
								<li><label>2、<input name="Consultation" type="checkbox" value="2、术前核查"/>术前核查</label></li>
								<li><label>3、<input name="Consultation" type="checkbox" value="3、麻醉方式：局部麻醉，必要时加镇痛镇静治疗"/>麻醉方式：局部麻醉，必要时加镇痛镇静治疗</label></li>
								<li><label>4、<input name="Consultation" type="checkbox" value="4、术中用药：局麻药物。必要时心血管临时用药"/>术中用药：局麻药物。必要时心血管临时用药</label></li>
								<li><label>5、<input name="Consultation" type="checkbox" value="5、手术"/>手术</label></li>
							</ul>
						</div>	
					</td>
					<td style="vertical-align: top;">
						<div style="vertical-align: top;">
							<ul>
								<li><label>6、<input name="Consultation" type="checkbox" value="6、临床路径录入"/>临床路径录入</label></li>
								<li><label>7、<input name="Consultation" type="checkbox" value="7、漱口液含漱、抗菌药物使用3-5天"/>漱口液含漱、抗菌药物使用3-5天</label></li>
								<li><label>8、<input name="Consultation" type="checkbox" value="8、拍X片:曲面断层,必要时小牙片"/>拍X片:曲面断层,必要时小牙片</label></li>
								<li><label>9、<input name="Consultation" type="checkbox" value="9、交代注意事项、预约复查时间"/>交代注意事项、预约复查时间</label></li>
								<li><label>10、<input name="Consultation" type="checkbox" value="10、术者完成手术记录"/>术者完成手术记录</label></li>
							</ul>
						</div>	
					</td>
					<td style="vertical-align: top;">
						<div style="vertical-align: top;">
							<ul>
								<li><label>11、<input name="Consultation" type="checkbox" value="11、术后当天取模、定咬合关系"/>术后当天取模、定咬合关系</label></li>
							</ul>
						</div>	
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
<script type="text/javascript">
var orderNumber = parent.orderNumber;
var contextPath = '<%=contextPath%>';
$(function(){
	//时间选择
    $("#operation_time").datetimepicker({
        language:  'zh-CN',  
   		minView:0,
        format: 'yyyy-mm-dd hh:ii',
   		autoclose : true,//选中之后自动隐藏日期选择框   
   		pickerPosition: "bottom-right"
    });
	
    initFlow();
});

//初始化流程
function initFlow() {
	$.ajax({
		url: contextPath + "/HUDH_FlowAct/findOrderTrackInfo.act?orderNumber=" + orderNumber,
		type:"POST",
		dataType:"json",
		async:false,
		success:function(result){
			$('input').attr("disabled",true);
			$('select').attr("disabled",true);
			$('textarea').attr("readonly","readonly");
//			alert(JSON.stringify(result));
			console.log(JSON.stringify(result)+"------------hahhah");
			patientObj = eval(result);
			
			//专家会诊赋值
			var select_Consultation = patientObj.consultation;
			/* console.log(select_Consultation); */
			var disease_id = select_Consultation.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='Consultation']").each(function(){
					if($(this).val()==disease_id[i]){
					   $(this).attr("checked","checked");
					}
				})
			}
			
			
		}
	});
}
</script>
</html>