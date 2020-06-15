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
								<li><label>1、<input name="Consultation" type="checkbox" value="1、询问既往史"/>询问既往史及体格检查</label></li>
								<li><label>2、<input name="Consultation" type="checkbox" value="2、必需的检查项目:血常规、血糖、凝血功能、感染性疾病筛查"/>必需的检查项目:血常规、血糖、凝血功能、感染性疾病筛查</label></li>
								<li>
									<label>3、X线片：
										<input name="xrayfilms" type="checkbox" value="1、曲面断层片">1、曲面断层片
										<input name="xrayfilms" type="checkbox" value="2、牙片">2、牙片
										<input name="xrayfilms" type="checkbox" value="3、CT">3、CT
									</label>
								</li>
								<li>
									<label>4、选择检查项目：
										<input name="selectExamineItem" type="checkbox" value="1、心电图">1、心电图
										<input name="selectExamineItem" type="checkbox" value="2、骨密度">2、骨密度
										<input name="selectExamineItem" type="checkbox" value="3、心脏彩超">3、心脏彩超
										<input name="selectExamineItem" type="checkbox" value="4、B超">4、B超
									</label>
								</li>
								<li>
									<label>5、口腔专科检查：
										<input name="mouthExamine" type="checkbox" value="1、全口牙及牙周状况检查">1、全口牙及牙周状况检查
										<input name="mouthExamine" type="checkbox" value="2、双侧颞下颌关节及咬合关系">2、双侧颞下颌关节及咬合关系
									</label>
								</li>
							</ul>
						</div>	
					</td>
					<td style="vertical-align: top;">
						<div style="vertical-align: top;">
							<ul>
								<li><label>6、<input name="Consultation" type="checkbox" value="6、术前讨论（疑难患者）"/>术前讨论（疑难患者）</label></li>
								<li><label>7、<input name="Consultation" type="checkbox" value="7、制定手术方案和治疗计划"/>制定手术方案和治疗计划</label></li>
								<li><label>8、<input name="Consultation" type="checkbox" value="8、详细告知患者手术方案、治疗计划及诊疗流程"/>详细告知患者手术方案、治疗计划及诊疗流程</label></li>
								<li><label>9、<input name="Consultation" type="checkbox" value="9、办理病历，签署知情同意书"/>办理病历，签署知情同意书</label></li>
							</ul>
						</div>	
					</td>
					<td style="vertical-align: top;">
						<div>
							<ul>
								<li><label>10、<input name="Consultation" type="checkbox" value="10、拍照"/>拍照</label></li>
								<li><label>11、<input name="Consultation" type="checkbox" value="11、必要时取研究模型，行模型分析"/>必要时取研究模型，行模型分析</label></li>
								<li><label>12、<input name="Consultation" type="checkbox" value="12、口腔清洁（必要时牙周治疗）"/>口腔清洁（必要时牙周治疗）</label></li>
								<li>
									<label>13、<input name="Consultation" type="checkbox" value="13、安排预约手术时间"/>安排预约手术时间
										<input name="review_time" type="text" id="operation_time" value="" style="width: 105px;"/>
									</label>
								</li>
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
			//console.log(JSON.stringify(result)+"------------hahhah");
			patientObj = eval(result);
			
			//专家会诊赋值
			var select_Consultation = patientObj.consultation;
			console.log(select_Consultation); 
			var disease_id = select_Consultation.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='Consultation']").each(function(){
					if($(this).val()==disease_id[i]){
					   $(this).attr("checked","checked");
					}
				})
			}
			/* //影像学检查赋值
			var select_imagelogic = patientObj.imageologicalExamination;
			console.log(select_imagelogic);
			var disease_id = select_imagelogic.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='imagelogic']").each(function(){
					if($(this).val()==disease_id[i]){
					   $(this).attr("checked","checked");
					}
				})
			} */
			
			//X线片赋值
			var xrayfilms = patientObj.xrayfilms;
			console.log(xrayfilms+"------------------X线片赋值");
			var xrayfilms_id = xrayfilms.split(";");
			for(var i = 0; i < xrayfilms_id.length; i++){
				$("input[name='xrayfilms']").each(function(){
					if($(this).val()==xrayfilms_id[i]){
					   $(this).attr("checked","checked");
					}
				})
			}
			//选择检查项目赋值
			var selectExamineItem = patientObj.selectExamineItem;
			console.log(selectExamineItem+"------------------选择检查项目赋值");
			var selectExamineItem_id = selectExamineItem.split(";");
			for(var i = 0; i < selectExamineItem_id.length; i++){
				$("input[name='selectExamineItem']").each(function(){
					if($(this).val()==selectExamineItem_id[i]){
					   $(this).attr("checked","checked");
					}
				})
			}
			//口腔专科检查赋值
			var mouthExamine = patientObj.mouthExamine;
			console.log(mouthExamine+"------------------口腔专科检查赋值");
			var mouthExamine_id = mouthExamine.split(";");
			for(var i = 0; i < mouthExamine_id.length; i++){
				$("input[name='mouthExamine']").each(function(){
					if($(this).val()==mouthExamine_id[i]){
					   $(this).attr("checked","checked");
					}
				})
			}
			//手术时间赋值
			console.log(patientObj.reviewtime+"------------------手术时间赋值");
			$("#operation_time").val(patientObj.reviewtime);
			
		}
	});
}
</script>
</html>