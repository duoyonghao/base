<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
       
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//当前检查及诊断方案id
	String seqidFather = request.getParameter("seqidFather");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/hudh/lclj/flowdetail/css/base.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/static/css/kqdsFront/plantCase/examineDiagnose.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script><!-- 引入封装ajax方法文件 -->
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<style type="text/css">
	label{
		font-weight: normal;
		margin-top: 18px;
	}
	input::-webkit-input-placeholder{
		font-size: 14px;
		font-weight: normal;
	}
	input:-moz-placeholder{
		font-size: 14px;
		font-weight: normal;
	}
	input::-moz-placeholder{
		font-size: 14px;
		font-weight: normal;
	}
	input:-ms-input-placeholder{
		font-size: 14px;
		font-weight: normal;
	}
	label{
	    margin-top: 0px;
	    }
	.container-fluid {
	    padding-right: 0px;
	    padding-left: 0px;
	    margin-right: auto;
	    margin-left: auto;
	}
	img[src=""],img:not([src]){
      opacity:0;
 	}
 	#logoImg{
	    width: 10%;
	    padding: 10px 0;
	}
	 /*分隔线 */
    .line {
        display: block;
        border-top:2px dotted #776c6c;
        padding:10px 0;
    }
</style>
</head>
<body>
<!--startprint-->
   <div class="boxBig">
   	<div class="container-fluid examine_continer">
		<!-- 标题 -->
		<div class="row">
			<div>
				<img id="logoImg" src="<%=contextPath%>/static/image/kqdsFront/jiagong/logoName.png">
				<i class="line"></i>
				<h2 class="bigtitle" style="margin-top: 0!important;">检查及诊断</h2>
			</div>
			
		</div>	
		<!-- 患者信息 -->
		<div class="row">
			<div class="col-md-3 col-sm-3 col-xs-3 colDefined" style="width:21%;">
				<!-- 信息输入组合框 -->
				<div class="rpInfo_import">
					<span style="font-weight: 600;">患者姓名：</span>
					<input id="patient_name" type="text" disabled="disabled" style=" width:27%;color: #00a6c0;"/>
				</div>
			</div>
			<div class="col-md-3 col-sm-3 col-xs-3 colDefined" style="width:20%;">
				<!-- 信息输入组合框 -->
				<div class="rpInfo_import">
					<span style="font-weight: 600;">编号：</span>
					<input id="patient_usercode" type="text" disabled="disabled" style="    width: 100px;color: #00a6c0;"/>
				</div>	
			</div>
			<div class="col-md-3 col-sm-3 col-xs-3 colDefined" style="width:20%">
				<!-- 信息输入组合框 -->
				<div class="rpInfo_import">
					<span style="font-weight: 600;">性别：</span>
					<input id="patient_sex" type="text" disabled="disabled" style="    width: 22px;color: #00a6c0;"/>
				</div>
			</div>
			<div class="col-md-3 col-sm-3 col-xs-3 colDefined" style="width:20%;">
				<!-- 信息输入组合框 -->
				<div class="rpInfo_import">
					<span style="font-weight: 600;">年龄：</span>
					<input id="patient_age" type="text" disabled="disabled" style="    width: 22px;color: #00a6c0;"/>
				</div>	
			</div>
			<div class="col-md-1 col-sm-1 col-xs-1 colDefined"></div>
		</div>
		
		
	</div>
	<!-- 身体状况评估 -->
	<div class="container-fluid examine_continer">
		<!-- 身体状况评估 -->
		<div class="row">
			<div>
				<!-- 多选框 -->
				<div  class="zl_multiple">
					<!-- 选项 -->
					<ul>
					<li>
							<!-- 选项框 -->
							<div class="zl_optiondiv">
								<label for="physical_assessmentA" style="font-weight: 600;line-height: 30px;">会诊意见：</label>
							</div>
						</li>
						<li>
							<!-- 选项框 -->
							<div class="zl_optiondiv">
							<input name="physical_assessment" id="physical_assessmentA" value="可手术" type="checkbox"/>
								<label for="physical_assessmentA">可手术</label>
							</div>
						</li>
						<li>
							<!-- 选项框 -->
							<div class="zl_optiondiv">
							<input name="physical_assessment" id="physical_assessmentB" value="心电监护下手术" type="checkbox"/>
								<label for="physical_assessmentB">心电监护下手术</label>
							</div>
						</li>
						<li>
							<!-- 选项框 -->
							<div class="zl_optiondiv">
							<input name="physical_assessment" id="physical_assessmentC" value="医生监护下手术" type="checkbox"/>
								<label for="physical_assessmentC">医生监护下手术</label>
							</div>
						</li>
						<li>
							<!-- 选项框 -->
							<div class="zl_optiondiv">
							<input name="physical_assessment" id="physical_assessmentD" value="暂缓手术" type="checkbox"/>
								<label for="physical_assessmentD">暂缓手术</label>
							</div>
						</li>	
					</ul>
				</div>
			</div>
			<div >
				<!-- 多选框 -->
				<div  class="zl_multiple" >
				
					<!-- 选项 -->
					<ul style=" border-bottom: 2px solid #776c6c;padding-bottom: 30px;">
					
					<li>
							<!-- 选项框 -->
							<div class="zl_optiondiv">
								<label for="consultation_opinionA" style="font-weight: 600;">身体状况评估：</label>
							</div>
						</li>
						<li>
							<!-- 选项框 -->
							<div class="zl_optiondiv">
							<input name="consultation_opinion" id="consultation_opinionA" value="优" type="radio"/>
								<label for="consultation_opinionA">优</label>
							</div>
						</li>
						<li>
							<!-- 选项框 -->
							<div class="zl_optiondiv">
							<input name="consultation_opinion" id="consultation_opinionB" value="良" type="radio"/>
								<label for="consultation_opinionB">良</label>
							</div>
						</li>
						<li>
							<!-- 选项框 -->
							<div class="zl_optiondiv">
							<input name="consultation_opinion" id="consultation_opinionC" value="中" type="radio"/>
								<label for="consultation_opinionC">中</label>
							</div>
						</li>
						<li>
							<!-- 选项框 -->
							<div class="zl_optiondiv">
							<input name="consultation_opinion" id="consultation_opinionD" value="差" type="radio"/>
								<label for="consultation_opinionD">差</label>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 口腔专科检查 -->
	<div class="container-fluid examine_continer cavityExamine">
		
			<div>
				<span style="font-weight: 600;">口腔专科检查</span>
			</div>

    <div style="margin: 0 10px;">
    		
		<!-- 咬颌情况 -->
		<div class="row" style="background-color: #ddd;">
			<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
				<!-- 多选框 -->
				<ul class="moreSelect_div" style="height: 30px;">
				    <li><div style="line-height: 25px;margin-top: 0px;font-weight:600;">咬颌情况：</div></li>
					<li style="width:16%"><input name="occlusion_situation" id="occlusion_situationA" value="正常颌" type="checkbox"/><label for="occlusion_situationA" style="margin-top: 0px;   ">正常颌</label></li>
					<li><input name="occlusion_situation" id="occlusion_situationB" value="深覆颌" type="checkbox"/><label for="occlusion_situationB" style="margin-top: 0px;">深覆颌</label></li>
					<li><input name="occlusion_situation" id="occlusion_situationC" value="对刃颌" type="checkbox"/><label for="occlusion_situationC" style="margin-top: 0px;">对刃颌</label></li>
					<li><input name="occlusion_situation" id="occlusion_situationD" value="反颌" type="checkbox"/><label for="occlusion_situationD" style="margin-top: 0px;">反颌</label></li>
					<li><input name="occlusion_situation" id="occlusion_situationE" value="开颌" type="checkbox"/><label for="occlusion_situationE" style="margin-top: 0px;">开颌</label></li>
					<li><input name="occlusion_situation" id="occlusion_situationF" value="深覆盖" type="checkbox"/><label for="occlusion_situationF" style="margin-top: 0px;">深覆盖</label></li>
				</ul>
			</div>
		</div>
		<!-- 颌间距离 -->
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
				<!-- 填写框 -->
				<div class="zl_fillWritediv" style="style="height: 30px;"">
					<span style="line-height: 25px;font-weight:600;margin-right: 9%;">颌间距离:&nbsp;</span>
					<input id="interarch_distance" onblur="TextLengthCheck(this.id,10);" type="text" style="width: 7%;"/>
					<span>&nbsp;mm</span>
				</div>
			</div>
		</div>
		
		
		<!-- 曾修复的种类 -->
		<div class="row" style="background-color: #ddd;">
			<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
				<!-- 多选框 -->
				<div  class="zl_multiple">
					<!-- 选项 -->
					<ul style="height: 30px;">
					<li style="width:14%;">
							<!-- 选项框 -->
							<div class="zl_optiondiv">
								<div style="line-height: 25px;font-weight:600;">曾修复的种类：</div>
							</div>
						</li>
						<li style="width:16%;">
							<!-- 选项框 -->
							<div class="zl_optiondiv" style="">
							<input name="repaire_type" id="repaire_typeA" value="部分活动义齿" type="checkbox"/>
								<label for="repaire_typeA">部分活动义齿</label>
							</div>
						</li>
						<li style="width:14%;">
							<!-- 选项框 -->
							<div class="zl_optiondiv" style="">
							<input name="repaire_type" id="repaire_typeB" value="固定义齿" type="checkbox"/>
								<label for="repaire_typeB">固定义齿</label>
							</div>
						</li>
						<li style="width:14%;">
							<!-- 选项框 -->
							<div class="zl_optiondiv" style="">
							<input name="repaire_type" id="repaire_typeC" value="全口义齿" type="checkbox"/>
								<label for="repaire_typeC">全口义齿</label>
							</div>
						</li>
						<li style="width:14%;">
							<!-- 选项框 -->
							<div class="zl_optiondiv" style=""> 
							<input name="repaire_type" id="repaire_typeD" value="未修复" type="checkbox"/>
								<label for="repaire_typeD">未修复</label>
							</div>
						</li>	
					</ul>
				</div>
			</div>
		</div>
		<!-- 牙周情况 -->
		<div class="row" >
			<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
				<!-- 多选框 -->
				<div  class="zl_multiple">
					<!-- 选项 -->
					<ul style="height: 30px;">
					<li style="width:14%;">
							<!-- 选项框 -->
							<div class="zl_optiondiv">
								<div style="line-height: 25px;font-weight:600;">牙周情况：</div>
							</div>
						</li>
						<li style="width:16%;">
							<!-- 选项框 -->
							<div class="zl_optiondiv">
							<input name="periodontal_condition" id="periodontal_conditionA" value="正常" type="checkbox"/>
								<label for="periodontal_conditionA">正常</label>
							</div>
						</li>
						<li style="width:14%;">
							<!-- 选项框 -->
							<div class="zl_optiondiv" >
							<input name="periodontal_condition" id="periodontal_conditionB" value="牙周病" type="checkbox"/>
								<label for="periodontal_conditionB">牙周病</label>
							</div>
						</li>
						<li style="width:14%;">
							<!-- 选项框 -->
							<div class="zl_optiondiv" >
							<input name="periodontal_condition" id="periodontal_conditionC" value="牙龈炎" type="checkbox"/>
								<label for="periodontal_conditionC">牙龈炎</label>
							</div>
						</li>
						<li style="width:14%;">
							<!-- 选项框 -->
							<div class="zl_optiondiv">
							<input name="periodontal_condition" id="periodontal_conditionD" value="牙周炎" type="checkbox"/>
								<label for="periodontal_conditionD">牙周炎</label>
							</div>
						</li>	
					</ul>
				</div>
			</div>
		</div>
		<!-- 牙缺失、龋齿、残根 -->
		<div class="row" style="background-color: #ddd;">
			<div class="col-md-4 col-sm-4 col-xs-4 colDefined">
				<!-- 牙位图 -->
				<div class="horizontal" style="width: 30%;">牙缺失：</div>
				<div class="zl_toothMapdiv">
					<ul class="tooth_map" style="margin-left: 30px;">
						<li>
							<input id="teethmissleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="teethmissrightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="teethmissleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="teethmissrightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
					</ul>
				</div>
			</div>
			<div class="col-md-4 col-sm-4 col-xs-4 colDefined">
				<!-- 牙位图 -->
				<div class="horizontal">龋齿：</div>
				<div class="zl_toothMapdiv">
					<ul class="tooth_map" style="margin-left: 30px;">
						<li>
							<input id="dentalcaryleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="dentalcaryrightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="dentalcaryleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="dentalcaryrightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
					</ul>
				</div>
			</div>
			<div class="col-md-4 col-sm-4 col-xs-4 colDefined">
				<!-- 牙位图 -->
				<div class="horizontal">残根：</div>
				<div class="zl_toothMapdiv">
					<ul class="tooth_map" style="margin-left: 30px;">
						<li>
							<input id="residualrootleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="residualrootrightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="residualrootleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="residualrootrightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- 牙松动 -->
		<div class="row" style="background-color: #ddd;">
			<div class="col-md-4 col-sm-4 col-xs-4 colDefined">
				<div class="horizontal" style="width: 40%;">牙松动：
			<span style="margin-right: 2%;font-weight:600;">Ⅰ°</span>
			</div>
				<!-- 牙位图 -->
				<div class="zl_toothMapdiv">
					
					<ul class="tooth_map" style="margin-left: 30px;">
						<li>
							<input id="teethmoveoneleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="teethmoveonerightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="teethmoveoneleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="teethmoveonerightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
					</ul>
				</div>
			</div>
			<div class="col-md-4 col-sm-4 col-xs-4 colDefined">
				<!-- 牙位图 -->
				<div class="horizontal">Ⅱ°</div>
				<div class="zl_toothMapdiv">
					<ul class="tooth_map" style="margin-left: 30px;">
						<li>
							<input id="teethmovetwoleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="teethmovetworightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="teethmovetwoleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="teethmovetworightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
					</ul>
				</div>
			</div>
			<div class="col-md-4 col-sm-4 col-xs-4 colDefined">
				<!-- 牙位图 -->
				<div class="horizontal">Ⅲ°</div>
				<div class="zl_toothMapdiv">
					<ul class="tooth_map" style="margin-left: 30px;">
						<li>
							<input id="teethmovethreeleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="teethmovethreerightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="teethmovethreeleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li> 
							<input id="teethmovethreerightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- 牙齿缺失原因 -->
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12 colDefined tooth_deficiency">
				<!-- 多选框 -->
				<div  class="zl_multiple">
					<!-- 选项 -->
					<ul style="height: 30px;">
					<li style="width:14%">
							<!-- 选项框 -->
							<div class="zl_optiondiv">
								<div style="line-height: 25px;font-weight: 600;">牙齿缺失原因：</div>
							</div>
						</li>
						<li style="width:16%">
							<!-- 选项框 -->
							<div class="zl_optiondiv" >
							<input name="reasonmissteeth" id="reasonmissteethA" value="先天缺失" type="checkbox"/>
								<label for="reasonmissteethA">先天缺失</label>
							</div>
						</li>
						<li style="width:14%">
							<!-- 选项框 -->
							<div class="zl_optiondiv" >
							<input name="reasonmissteeth" id="reasonmissteethB" value="龋病" type="checkbox"/>
								<label for="reasonmissteethB">龋病</label>
							</div>
						</li>
						<li style="width:14%">
							<!-- 选项框 -->
							<div class="zl_optiondiv" > 
							<input name="reasonmissteeth" id="reasonmissteethC" value="牙周病" type="checkbox"/>
								<label for="reasonmissteethC">牙周病</label>
							</div>
						</li>
						<li style="width:14%">
							<!-- 选项框 -->
							<div class="zl_optiondiv" >
							<input name="reasonmissteeth" id="reasonmissteethD" value="外伤" type="checkbox"/>
								<label for="reasonmissteethD">外伤</label>
							</div>
						</li>
						<li style="width:14%"> 
							<!-- 选项框 -->
							<div class="zl_optiondiv" >
							<input name="reasonmissteeth" id="reasonmissteethE" value="其他" type="checkbox"/>
								<label for="reasonmissteethE">其他</label>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- 牙缺失的时间-->
		<div class="row" style="background-color: #ddd;">
			<div class="col-md-4 col-sm-4 col-xs-4 colDefined">
				<div class="rpInfo_import" style="height: 30px;">
					<span style="font-weight: 600;">牙缺失的时间：</span>
					<input id="timemissteeth" type="text" class="consent_time inputheight2" readonly="readonly" placeholder="请选择日期" style="border:1px solid #b0adad;text-align: center;width:35%;"/>
				</div>
			</div>
			<div class="col-md-6 col-sm-6 col-xs-6 colDefined"></div>
		</div>
		<!-- 粘膜情况 -->
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
				<!-- 多选框 -->
				<div  class="zl_multiple">
					<!-- 选项 -->
					<ul style="height: 30px;">
					<li style="width:14%">
							<!-- 选项框 -->
							<div class="zl_optiondiv">
								<div style="line-height: 25px;font-weight: 600;">粘膜情况：</div>
							</div>
						</li>
						<li style="width:16%">
							<!-- 选项框 -->
							<div class="zl_optiondiv">
							<input name="mucosacondition" id="mucosaconditionA" value="正常" type="checkbox"/>
								<label for="mucosaconditionA">正常</label>
							</div>
						</li>
						<li style="width:14%">
							<!-- 选项框 -->
							<div class="zl_optiondiv">
							<input name="mucosacondition" id="mucosaconditionB" value="溃疡" type="checkbox"/>
								<label for="mucosaconditionB">溃疡</label>
							</div>
						</li>
						<li style="width:14%">
							<!-- 选项框 -->
							<div class="zl_optiondiv" >
							<input name="mucosacondition" id="mucosaconditionC" value="白斑" type="checkbox"/>
								<label for="mucosaconditionC">白斑</label>
							</div>
						</li>
						<li style="width:14%">
							<!-- 选项框 -->
							<div class="zl_optiondiv"> 
							<input name="mucosacondition" id="mucosaconditionD" value="扁平苔癣" type="checkbox"/>
								<label for="mucosaconditionD">扁平苔癣</label>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- X线所示 -->
		<div class="row" style="background-color: #ddd;">
			<div class="col-md-12 col-sm-12 col-xs-12 colDefined" style="margin-top:10px;">
				<div style="font-weight:600;">X线所示：</div>
				<ul class="tooth_xrayfillWrite" style="height:40px;display: flex;">
					<li style="width:30%;">
						<!-- 填写框 -->
						<div class="zl_fillWritediv">
							<span>牙槽嵴顶矩上颌窦底:</span>
							<input id="frommaxillarysinusfloor" onblur="TextLengthCheck(this.id,10);" type="text"/>
							<span>mm</span>
						</div>
					</li>
					<li style="width:22%;">
						<!-- 填写框 -->
						<div class="zl_fillWritediv">
							<span>距鼻底:</span>
							<input id="fromnasalfloor" onblur="TextLengthCheck(this.id,10);" type="text"/>
							<span>mm</span>
						</div>
					</li>
					<li style="width:25%;">
						<!-- 填写框 -->
						<div class="zl_fillWritediv">
							<span>距下牙槽神经管:</span>
							<input id="frominferiordentalnerve" onblur="TextLengthCheck(this.id,10);" type="text"/>
							<span>mm</span>
						</div>
					</li>
					<li style="width:22%;">
						<!-- 填写框 -->
						<div class="zl_fillWritediv">
							<span>牙槽嵴宽度:</span>
							<input id="alveloarcrestwidths" onblur="TextLengthCheck(this.id,10);" type="text"/>
							<span>mm</span>
						</div>
					</li>
				</ul>
			</div>
			<div class="col-md-4 col-sm-4 col-xs-4 colDefined tooth_xray">
				<!-- 牙位图 -->
				<div class="zl_optiondiv" style="margin: 10px 0;">X线所示：</div>
				<div class="zl_toothMapdiv">
					<ul class="tooth_map" style="margin-left: 30px;">
						<li>
							<input id="xrayshowsleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="xrayshowsrightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="xrayshowsleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="xrayshowsrightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
					</ul>
					
				</div>
				<!-- 牙位图对应填空 -->
				
			</div>
			<div class="col-md-4 col-sm-4 col-xs-4 colDefined">
				<!-- 牙位图 -->
				<div class="zl_optiondiv" style="margin: 10px 0;">牙槽骨吸收：</div>
				<div class="zl_toothMapdiv">
					<ul class="tooth_map" style="margin-left: 30px;">
						<li>
							<input id="alveloarboneresorptionleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="alveloarboneresorptionrightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="alveloarboneresorptionleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="alveloarboneresorptionrightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
					</ul>
					
				</div>
			</div>
			<div class="col-md-4 col-sm-4 col-xs-4 colDefined">
				<!-- 牙位图 -->
				<div class="zl_optiondiv" style="margin: 10px 0;">牙根尖阴影：</div>
				<div class="zl_toothMapdiv">
					<ul class="tooth_map"  style="margin-left: 30px;">
						<li>
							<input id="periodontal_lesionleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="periodontal_lesionrightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="periodontal_lesionleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="periodontal_lesionrightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
					</ul>
					
				</div>
			</div>
		</div>
	</div>
	
			<!-- 粘膜情况 -->
			<div class="container-fluid examine_continer" style="margin: 10px -5px;">
		<div class="row" style="background: #ddd;height: 66px;margin: 0px;">
			<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
				<!-- 多选框 -->
				<div  class="zl_multiple">
					<!-- <span>粘膜情况：</span> -->
					<!-- 选项 -->
					<ul>
					<li style="width:15%">
							<!-- 选项框 -->
							<div class="zl_optiondiv">
								<label style="line-height:25px;margin-left:20px;">诊断：</label>
							</div>
						</li>
						<li style="width:14%">
							<div class="zl_optiondiv">
							<input name="diagnosis" id="diagnosisA" value="牙缺失" type="checkbox"/>
								<label for="diagnosisA">牙缺失</label>
							</div>
						</li>
						<li style="width:14%">
							
								<!-- 选项框 -->
							<div class="zl_optiondiv">
							<input name="diagnosis" id="diagnosisB" value="牙松动" type="checkbox"/>
								<label for="diagnosisB">牙松动</label>
							</div>
						</li>
						<li style="width:14%">
							<!-- 选项框 -->
							<div class="zl_optiondiv">
							<input name="diagnosis" id="diagnosisC" value="残根" type="checkbox"/>
								<label for="diagnosisC">残根</label>
							</div>
						</li>
						<li style="width:14%">
							<!-- 选项框 -->
							<!-- 选项框 -->
							<div class="zl_optiondiv">
							<input name="diagnosis" id="diagnosisD" value="牙周病" type="checkbox"/>
								<label for="diagnosisD">牙周病</label>
							</div>
						</li>
						<!-- <li style="width:28%;">
							选项框
							<div class="zl_optiondiv">
								<span>其他</span><input id="others" placeholder="此框只能输10个字" onblur="TextLengthCheck(this.id,10);" style="width: 60%;" type="text"/>
							</div>
						</li> -->
					</ul>
					
				</div>
				
			</div>
			<div class="zl_optiondiv" style="margin-left: 20px;">
				<span>其他</span><input id="others" placeholder="" onblur="TextLengthCheck(this.id,30);" style="width: 60%;" type="text"/>
			</div>
		</div>
			<div class="row" style="margin-top: 27px;"> 
			<div class="col-md-6 col-sm-6 col-xs-6 colDefined">
			</div>
			<div class="signature_time" style="width: 45%;margin-left: 55%;position: relative;">
				<div class="zl_signature">
					<span id="doctorSignature">医生签字：</span>
					<img id="img" style="width:156px;height: 43px;"/>
				</div>
				<input style="width: 35%;position: absolute;right: 0px;bottom: 0px;text-align: center;" id="doctortime" type="text" class="consent_time inputheight2" readonly="readonly" placeholder="请选择日期"/>
			</div>
			<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
			</div>
		</div>
    </div> 
    <!--endprint-->
	<!-- 按钮 -->
	<div class="btns">
		<button id="consent_saveBtn" onclick="save()">保存</button>
		<button id="consent_updateBtn" style="display: none;" class="consent_updateBtn hidden" onclick="update()">修改表单</button>
		<button id="print_Btn" onclick="myPreviewAll()">打印本页内容</button>
	</div>
   </div>
	
</body>

<script language="javascript"  src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
<script type="text/javascript">
		var signature="";
		var doctorstatus=true;
		var contextPath = "<%=contextPath%>";
		var id= window.parent.consultSelectPatient.seqid;	//选中患者id
		var order_number= window.parent.consultSelectPatient.orderNumber;//选中患者order_number
		var caseId=""; //已存在的病历id
		var menuid=window.parent.menuid;//左侧菜单id
		var seqidFather = "<%=seqidFather%>";
		$(function(){
			
			//时间选择
		    $(".consent_time").datetimepicker({
		        language:  'zh-CN',  
		   		minView: 2,
		        format: 'yyyy-mm-dd',
		   		autoclose : true,//选中之后自动隐藏日期选择框   
		   		pickerPosition: "top-right",
		   		todayBtn: true,
			   	beforeShow: function () {
			         setTimeout(
				         function () {
				             $('#ui-datepicker-div').css("z-index", 21);
				         }, 100
			         );
			    }
		    });
			
		    //患者姓名、年龄、性别赋值
			$("#patient_name").attr("value",window.parent.consultSelectPatient.username);
			$("#patient_sex").attr("value",window.parent.consultSelectPatient.sex);
			$("#patient_age").attr("value",window.parent.consultSelectPatient.age);
			$("#patient_usercode").attr("value",window.parent.consultSelectPatient.usercode);
			initZzblInfor();
			// 2019/7/24 lutian 禁止页面拖拽
			document.ondragstart = function() {
	            return false;
	        };
		});
		var doctorSignature = document.getElementById("doctorSignature");    
		doctorSignature.onclick = function(){ 
			if(doctorstatus){
				layer.open({
			        type: 2,
			        title: '签字',
			        shadeClose: true,
			        shade: 0.6,
			        area: ['70%', '65%'],
			        content: contextPath + '/SignatureAct/toSignature.act?category=种植'
			    });
			}
	   }
		function addSignature(){
			$("#img").css("display","");
			$("#img").attr('src', signature);
		}
		/* 2019/7/22 lutian input文字长度校验方法   obj：元素id  textNum：限制文字长度 */
		function TextLengthCheck(obj,textNum){
			var objTextVal=$("#"+obj).val();
			//var checkTitleBefore=$("#"+obj).parents(".zl_toothMapdiv").find("span").text();//根据父元素的选择器找到标题
			//var checkTitle=checkTitleBefore.substring(0,checkTitleBefore.indexOf("：")); // 校验文字长长度的标题
			if(objTextVal.length>textNum){
				$("#"+obj).attr("maxlength",textNum);
				//layer.alert(checkTitle+"文字长度不能超过"+textNum+"字!");
				layer.open({
					 title: '提示',
					 content: '此输入框文字长度不能超过'+textNum+'字!',
					 end:function(){
						 var inputNewVal=$("#"+obj).val();
						 $("#"+obj).val(inputNewVal.substring(0,textNum)).focus();
					 }
				});
				return;
			}
		}
		
		function initZzblInfor(){
			var url = contextPath + '/HUDH_ZzblCheckAct/findZzblOprationById.act';
			$.ajax({
				url: url,
				type:"POST",
				dataType:"json",
				data : {
					 id :  id, //临床路径ID
					 order_number : order_number
				},
				success:function(result){
					//console.log(JSON.stringify(result)+"--------初始化返回值");
					//caseId=result.seqId;  //病历id
					var result;
					if(seqidFather){
						for (var i=0;i<result.length;i++) {
							if(seqidFather==result[i].seqId){
								result=result[i];
							}
						}
					}
					caseId=seqidFather;  //修改病历id
					/* 判断是否已经填写过内容 */
					if(result.id){
						$("#consent_saveBtn").css("display","none");//隐藏保存按钮
						$("#consent_updateBtn").css("display","inline-block");//显示修改按钮
						//赋值 
						for(var key in result){
							//console.log(key+"-------------"+result[key]);
							$("#"+key).attr("value",result[key]);// 填框赋值
							if(result[key].indexOf(";")>0){
								var checkboxVal= result[key];//拼接多选框的值
								var checkboxValArr=checkboxVal.split(";");//将字符串转为数组
								for(var i=0;i<checkboxValArr.length;i++){
									$("input[name="+key+"]").each(function(){
										if($(this).val()==checkboxValArr[i]){
										   $(this).attr("checked","checked");
										}
									})
								}
							} 
							//牙位图赋值
							if(result[key].indexOf(",")>0){
								var toothPlaceVal= result[key];//拼接多选框的值
								var toothPlaceValArr=toothPlaceVal.split(",");//将字符串转为数组
								var newtoothPlaceVal=toothPlaceValArr.join("");
								//console.log(newtoothPlaceVal+"---------去掉牙位图逗号"); 
								$("#"+key).attr("value",newtoothPlaceVal);// 填框赋值
							}
							
						}
						//$("input").attr("disabled","disabled");//查看信息的时候禁止在填写
						
						signature=result.doctor_signatory;
						if(signature!=""){
							$("#img").attr('src', signature);
							doctorstatus=false;
						}else{
							$("#img").attr('display', 'none');
						}
					}
						//获取当前页面所有按钮
						getButtonAllCurPage(menuid);
				}
		  });
		}
		
		//获取url中的参数
		function getUrlParam(name) {
		    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
		    if (r != null) return unescape(r[2]); 
		    return null; //返回参数值
		}
	
		/* 身体状况评估 */
		function showphysicalAssessment() {
		    var obj = document.getElementsByName("physical_assessment");
		    var physicalAssessment = "";
		    for ( k in obj ) {
		        if(obj[k].checked)
		        	physicalAssessment = physicalAssessment + obj[k].value + ';';
		    }
		    return physicalAssessment;
		}
		//会诊意见
		function showconsultationOpinion() {
		    var obj = document.getElementsByName("consultation_opinion");
		    var showconsultationOpinion = "";
		    for ( k in obj ) {
		        if(obj[k].checked)
		        	showconsultationOpinion = showconsultationOpinion + obj[k].value + ';';
		    }
		    return showconsultationOpinion;
		}
		//咬合情况
		function showocclusionSituation() {
		    var obj = document.getElementsByName("occlusion_situation");
		    var occlusionSituation = "";
		    for ( k in obj ) {
		        if(obj[k].checked)
		        	occlusionSituation = occlusionSituation + obj[k].value + ';';
		    }
		    return occlusionSituation;
		}
		//曾修复的种类
		function showrepaireType() {
		    var obj = document.getElementsByName("repaire_type");
		    var repaireType = "";
		    for ( k in obj ) {
		        if(obj[k].checked)
		        	repaireType = repaireType + obj[k].value + ';';
		    }
		    return repaireType;
		}
		//牙周情况
		function showperiodontalCondition() {
		    var obj = document.getElementsByName("periodontal_condition");
		    var periodontalCondition = "";
		    for ( k in obj ) {
		        if(obj[k].checked)
		        	periodontalCondition = periodontalCondition + obj[k].value + ';';
		    }
		    return periodontalCondition;
		}
		//牙缺失原因
		function showreasonMissTeeth() {
		    var obj = document.getElementsByName("reasonmissteeth");
		    var reasonMissTeeth = "";
		    for ( k in obj ) {
		        if(obj[k].checked)
		        	reasonMissTeeth = reasonMissTeeth + obj[k].value + ';';
		    }
		    return reasonMissTeeth;
		}
		//粘膜情况
		function showmucosaCondition() {
		    var obj = document.getElementsByName("mucosacondition");
		    var mucosaCondition = "";
		    for ( k in obj ) {
		        if(obj[k].checked)
		        	mucosaCondition = mucosaCondition + obj[k].value + ';';
		    }
		    return mucosaCondition;
		}
		//诊断
		function showdiagnosis() {
		    var obj = document.getElementsByName("diagnosis");
		    var diagnosis = "";
		    for ( k in obj ) {
		        if(obj[k].checked)
		        	diagnosis = diagnosis + obj[k].value + ';';
		    }
		    return diagnosis;
		}
		

		//修改
		function update(){
			var patient_name = $("#patient_name").val();//患者姓名
			var patient_sex = $("#patient_sex").val();//患者性别
			var patient_age = $("#patient_age").val();//患者年龄
			var physical_assessment = showphysicalAssessment();//身体状况评估
			var consultation_opinion = showconsultationOpinion();//会诊意见
			var occlusion_situation = showocclusionSituation();//咬合情况
			var interarch_distance = $("#interarch_distance").val();//颌间距离
			var repaire_type = showrepaireType();//曾修复的种类
			var periodontal_condition = showperiodontalCondition();//牙周情况
			var teethMissLeftUp = $("#teethmissleftup").val();//牙缺失左上
			var teethMissLeftDown = $("#teethmissleftdown").val();//牙缺失左下
			var teethMissRightUp = $("#teethmissrightup").val();//牙缺失右上
			var teethMissRightDown = $("#teethmissrightdown").val();//牙缺失右下
			var dentalCaryLeftUp = $("#dentalcaryleftup").val();//龋齿左上
			var dentalCaryLeftDown = $("#dentalcaryleftdown").val();//龋齿左下
			var dentalCaryRightUp = $("#dentalcaryrightup").val();//龋齿右上
			var dentalCaryRightDown = $("#dentalcaryrightdown").val();//龋齿右下
			var residualRootLeftUp = $("#residualrootleftup").val();//残根左上
			var residualRootLeftDown = $("#residualrootleftdown").val();//残根左下
			var residualRootRightUp = $("#residualrootrightup").val();//残根右上
			var residualRootRightDown = $("#residualrootrightdown").val();//残根右下
			var teethMoveOneLeftUp = $("#teethmoveoneleftup").val();//牙松动一级左上
			var teethMoveOneLeftDown = $("#teethmoveoneleftdown").val();//牙松动一级左下
			var teethMoveOneRightUp = $("#teethmoveonerightup").val();//牙松动一级右上
			var teethMoveOneRightDown = $("#teethmoveonerightdown").val();//牙松动一级右下
			var teethMoveTwoLeftUp = $("#teethmovetwoleftup").val();//牙松动二级左上
			var teethMoveTwoLeftDown = $("#teethmovetwoleftdown").val();//牙松动二级左下
			var teethMoveTwoRightUp = $("#teethmovetworightup").val();//牙松动二级右上
			var teethMoveTwoRightDown = $("#teethmovetworightdown").val();//牙松动二级右下
			var teethMoveThreeLeftUp = $("#teethmovethreeleftup").val();//牙松动三级左上
			var teethMoveThreeLeftDown = $("#teethmovethreeleftdown").val();//牙松动三级左下
			var teethMoveThreeRightUp = $("#teethmovethreerightup").val();//牙松动三级右上
			var teethMoveThreeRightDown = $("#teethmovethreerightdown").val();//牙松动三级右下
			var reasonMissTeeth = showreasonMissTeeth();//牙缺失原因
			var timeMissTeeth = $("#timemissteeth").val();//牙缺失时间
			var mucosaCondition = showmucosaCondition();//粘膜情况
			var xrayShowsLeftUp = $("#xrayshowsleftup").val();//X射线左上
			var xrayShowsLeftDown = $("#xrayshowsleftdown").val();//X射线左下
			var xrayShowsRightUp = $("#xrayshowsrightup").val();//X射线右上
			var xrayShowsRightDown = $("#xrayshowsrightdown").val();//X射线右下
			var fromMaxillarySinusFloor = $("#frommaxillarysinusfloor").val();//牙槽嵴顶矩上颌窦底
			var fromNasalFloor = $("#fromnasalfloor").val();//矩鼻底
			var fromInferiorDentalNerve = $("#frominferiordentalnerve").val();//距下牙槽神经管
			var alveloarCrestWidths = $("#alveloarcrestwidths").val();//牙槽嵴宽度
			var alveloarBoneResorptionLeftUp = $("#alveloarboneresorptionleftup").val();//牙槽骨吸收左上
			var alveloarBoneResorptionRightUp = $("#alveloarboneresorptionrightup").val();//牙槽骨吸收右上
			var alveloarBoneResorptionLeftDown = $("#alveloarboneresorptionleftdown").val();//牙槽骨吸收左下
			var alveloarBoneResorptionRightDown = $("#alveloarboneresorptionrightdown").val();//牙槽骨吸收右下
			var periodontal_lesionLeftUp = $("#periodontal_lesionleftup").val();//牙根尖阴影左上
			var periodontal_lesionRightUp = $("#periodontal_lesionrightup").val();//牙根尖阴影右上
			var periodontal_lesionLeftDown = $("#periodontal_lesionleftdown").val();//牙根尖阴影左下
			var periodontal_lesionRightDown = $("#periodontal_lesionrightdown").val();//牙根尖阴影右下
			var diagnosis = showdiagnosis();//诊断
			var others = $("#others").val();//其他
			var doctor_signatoryTime = $("#doctortime").val();//医生签字时间
			

			var url = contextPath + '/HUDH_ZzblCheckAct/updateZzblOprationById.act';
	        var param = {
	        		 seqId : caseId,
	        		 id :  id, //临床路径ID
	        		 order_number :  order_number, //节点编号
	        		 username :patient_name,
		        	 sex : patient_sex,
		        	 age : patient_age,
	        		 physical_assessment :  physical_assessment, //身体状况评估
	        		 consultation_opinion :  consultation_opinion, //会诊意见
	        		 occlusion_situation :  occlusion_situation, //咬合情况
	        		 interarch_distance :  interarch_distance, //颌间距离
	        		 repaire_type :  repaire_type, //曾修复的种类
	        		 periodontal_condition :  periodontal_condition, //牙周情况
	        		 teethMissLeftUp :  teethMissLeftUp, //牙缺失左上
	        		 teethMissLeftDown :  teethMissLeftDown, //牙缺失左下
	        		 teethMissRightUp :  teethMissRightUp, //牙缺失右上
	        		 teethMissRightDown :  teethMissRightDown, //牙缺失右下
	        		 dentalCaryLeftUp :  dentalCaryLeftUp, //龋齿左上
	        		 dentalCaryLeftDown :  dentalCaryLeftDown, //龋齿左下
	        		 dentalCaryRightUp :  dentalCaryRightUp, //龋齿右上
	        		 dentalCaryRightDown :  dentalCaryRightDown, //龋齿右下
	        		 residualRootLeftUp :  residualRootLeftUp, //残根左上
	        		 residualRootLeftDown :  residualRootLeftDown, //残根左下
	        		 residualRootRightUp :  residualRootRightUp, //残根右上
	        		 residualRootRightDown :  residualRootRightDown,  //残根右下
	        		 teethMoveOneLeftUp :  teethMoveOneLeftUp, //牙松动一级左上
	        		 teethMoveOneLeftDown :  teethMoveOneLeftDown, //牙松动一级左下
	        		 teethMoveOneRightUp :  teethMoveOneRightUp, //牙松动一级右上
	        		 teethMoveOneRightDown :  teethMoveOneRightDown, //牙松动一级右下
	        		 teethMoveTwoLeftUp :  teethMoveTwoLeftUp, //牙松动二级左上
	        		 teethMoveTwoLeftDown :  teethMoveTwoLeftDown, //牙松动二级左下
	        		 teethMoveTwoRightUp :  teethMoveTwoRightUp, //牙松动二级右上
	        		 teethMoveTwoRightDown :  teethMoveTwoRightDown, //牙松动二级右下
	        		 teethMoveThreeLeftUp :  teethMoveThreeLeftUp, //牙松动三级左上
	        		 teethMoveThreeLeftDown :  teethMoveThreeLeftDown, //牙松动三级左下
	        		 teethMoveThreeRightUp :  teethMoveThreeRightUp, //牙松动三级右上
	        		 teethMoveThreeRightDown :  teethMoveThreeRightDown, //牙松动三级右下
	        		 reasonMissTeeth :  reasonMissTeeth, //牙缺失原因
	        		 timeMissTeeth :  timeMissTeeth, //牙缺失时间
	        		 mucosaCondition :  mucosaCondition, //粘膜情况
	        		 xrayShowsLeftUp :  xrayShowsLeftUp, //X射线左上
	        		 xrayShowsLeftDown :  xrayShowsLeftDown, //X射线左下
	        		 xrayShowsRightUp :  xrayShowsRightUp, //X射线右上
	        		 xrayShowsRightDown :  xrayShowsRightDown, //X射线右下
	        		 fromMaxillarySinusFloor :  fromMaxillarySinusFloor, //牙槽嵴顶矩上颌窦底
	        		 fromNasalFloor :  fromNasalFloor, //矩鼻底
	        		 fromInferiorDentalNerve :  fromInferiorDentalNerve, //距下牙槽神经管
	        		 alveloarCrestWidths :  alveloarCrestWidths, //牙槽嵴宽度
	        		 alveloarBoneResorptionLeftUp :  alveloarBoneResorptionLeftUp, //牙槽骨吸收左上
	        		 alveloarBoneResorptionRightUp :  alveloarBoneResorptionRightUp, //牙槽骨吸收右上
	        		 alveloarBoneResorptionLeftDown :  alveloarBoneResorptionLeftDown, //牙槽骨吸收左下
	        		 alveloarBoneResorptionRightDown :  alveloarBoneResorptionRightDown, //牙槽骨吸收右下
	        		 periodontal_lesionLeftUp :  periodontal_lesionLeftUp, //牙根尖阴影左上
	        		 periodontal_lesionRightUp :  periodontal_lesionRightUp, //牙根尖阴影右上
	        		 periodontal_lesionLeftDown :  periodontal_lesionLeftDown, //牙根尖阴影左下
	        		 periodontal_lesionRightDown :  periodontal_lesionRightDown, //牙根尖阴影右下
	        		 diagnosis :  diagnosis, //诊断
	        		 others :  others, //其他
	        		 doctor_signatory :  signature, //医生签字
	        		 doctor_signatoryTime :  doctor_signatoryTime //医生签字时间
	        };
	        //console.log(JSON.stringify(param)+"------------检查及诊断参数");
	       
	        $.axseSubmit(url, param,function() {},function(r) {
	        	layer.alert("修改成功！", {
		            end: function() {
		            	//window.parent.location.reload(); //刷新父页面
		                var frameindex = parent.layer.getFrameIndex(window.name);
		                parent.layer.close(frameindex); //再执行关闭
		            }
		      	});
	        },function(r){
	        	layer.alert("修改失败！");
		    }); 
		}
		
		//保存		
		function save() {
			var patient_name = $("#patient_name").val();//患者姓名
			var patient_sex = $("#patient_sex").val();//患者性别
			var patient_age = $("#patient_age").val();//患者年龄
			var physical_assessment = showphysicalAssessment();//身体状况评估
			var consultation_opinion = showconsultationOpinion();//会诊意见
			var occlusion_situation = showocclusionSituation();//咬合情况
			var interarch_distance = $("#interarch_distance").val();//颌间距离
			var repaire_type = showrepaireType();//曾修复的种类
			var periodontal_condition = showperiodontalCondition();//牙周情况
			var teethMissLeftUp = $("#teethmissleftup").val();//牙缺失左上
			var teethMissLeftDown = $("#teethmissleftdown").val();//牙缺失左下
			var teethMissRightUp = $("#teethmissrightup").val();//牙缺失右上
			var teethMissRightDown = $("#teethmissrightdown").val();//牙缺失右下
			var dentalCaryLeftUp = $("#dentalcaryleftup").val();//龋齿左上
			var dentalCaryLeftDown = $("#dentalcaryleftdown").val();//龋齿左下
			var dentalCaryRightUp = $("#dentalcaryrightup").val();//龋齿右上
			var dentalCaryRightDown = $("#dentalcaryrightdown").val();//龋齿右下
			var residualRootLeftUp = $("#residualrootleftup").val();//残根左上
			var residualRootLeftDown = $("#residualrootleftdown").val();//残根左下
			var residualRootRightUp = $("#residualrootrightup").val();//残根右上
			var residualRootRightDown = $("#residualrootrightdown").val();//残根右下
			var teethMoveOneLeftUp = $("#teethmoveoneleftup").val();//牙松动一级左上
			var teethMoveOneLeftDown = $("#teethmoveoneleftdown").val();//牙松动一级左下
			var teethMoveOneRightUp = $("#teethmoveonerightup").val();//牙松动一级右上
			var teethMoveOneRightDown = $("#teethmoveonerightdown").val();//牙松动一级右下
			var teethMoveTwoLeftUp = $("#teethmovetwoleftup").val();//牙松动二级左上
			var teethMoveTwoLeftDown = $("#teethmovetwoleftdown").val();//牙松动二级左下
			var teethMoveTwoRightUp = $("#teethmovetworightup").val();//牙松动二级右上
			var teethMoveTwoRightDown = $("#teethmovetworightdown").val();//牙松动二级右下
			var teethMoveThreeLeftUp = $("#teethmovethreeleftup").val();//牙松动三级左上
			var teethMoveThreeLeftDown = $("#teethmovethreeleftdown").val();//牙松动三级左下
			var teethMoveThreeRightUp = $("#teethmovethreerightup").val();//牙松动三级右上
			var teethMoveThreeRightDown = $("#teethmovethreerightdown").val();//牙松动三级右下
			var reasonMissTeeth = showreasonMissTeeth();//牙缺失原因
			var timeMissTeeth = $("#timemissteeth").val();//牙缺失时间
			var mucosaCondition = showmucosaCondition();//粘膜情况
			var xrayShowsLeftUp = $("#xrayshowsleftup").val();//X射线左上
			var xrayShowsLeftDown = $("#xrayshowsleftdown").val();//X射线左下
			var xrayShowsRightUp = $("#xrayshowsrightup").val();//X射线右上
			var xrayShowsRightDown = $("#xrayshowsrightdown").val();//X射线右下
			var fromMaxillarySinusFloor = $("#frommaxillarysinusfloor").val();//牙槽嵴顶矩上颌窦底
			var fromNasalFloor = $("#fromnasalfloor").val();//矩鼻底
			var fromInferiorDentalNerve = $("#frominferiordentalnerve").val();//距下牙槽神经管
			var alveloarCrestWidths = $("#alveloarcrestwidths").val();//牙槽嵴宽度
			var alveloarBoneResorptionLeftUp = $("#alveloarboneresorptionleftup").val();//牙槽骨吸收左上
			var alveloarBoneResorptionRightUp = $("#alveloarboneresorptionrightup").val();//牙槽骨吸收右上
			var alveloarBoneResorptionLeftDown = $("#alveloarboneresorptionleftdown").val();//牙槽骨吸收左下
			var alveloarBoneResorptionRightDown = $("#alveloarboneresorptionrightdown").val();//牙槽骨吸收右下
			var periodontal_lesionLeftUp = $("#periodontal_lesionleftup").val();//牙根尖阴影左上
			var periodontal_lesionRightUp = $("#periodontal_lesionrightup").val();//牙根尖阴影右上
			var periodontal_lesionLeftDown = $("#periodontal_lesionleftdown").val();//牙根尖阴影左下
			var periodontal_lesionRightDown = $("#periodontal_lesionrightdown").val();//牙根尖阴影右下
			var diagnosis = showdiagnosis();//诊断
			var others = $("#others").val();//其他
			var doctor_signatoryTime = $("#doctortime").val();//医生签字时间
			

			var url = contextPath + '/HUDH_ZzblCheckAct/save.act';
	        var param = {
	        		 id :  id, //临床路径ID
	        		 order_number :  order_number, //节点编号
	        		 username :patient_name,
		        	 sex : patient_sex,
		        	 age : patient_age,
	        		 physical_assessment :  physical_assessment, //身体状况评估
	        		 consultation_opinion :  consultation_opinion, //会诊意见
	        		 occlusion_situation :  occlusion_situation, //咬合情况
	        		 interarch_distance :  interarch_distance, //颌间距离
	        		 repaire_type :  repaire_type, //曾修复的种类
	        		 periodontal_condition :  periodontal_condition, //牙周情况
	        		 teethMissLeftUp :  teethMissLeftUp, //牙缺失左上
	        		 teethMissLeftDown :  teethMissLeftDown, //牙缺失左下
	        		 teethMissRightUp :  teethMissRightUp, //牙缺失右上
	        		 teethMissRightDown :  teethMissRightDown, //牙缺失右下
	        		 dentalCaryLeftUp :  dentalCaryLeftUp, //龋齿左上
	        		 dentalCaryLeftDown :  dentalCaryLeftDown, //龋齿左下
	        		 dentalCaryRightUp :  dentalCaryRightUp, //龋齿右上
	        		 dentalCaryRightDown :  dentalCaryRightDown, //龋齿右下
	        		 residualRootLeftUp :  residualRootLeftUp, //残根左上
	        		 residualRootLeftDown :  residualRootLeftDown, //残根左下
	        		 residualRootRightUp :  residualRootRightUp, //残根右上
	        		 residualRootRightDown :  residualRootRightDown,  //残根右下
	        		 teethMoveOneLeftUp :  teethMoveOneLeftUp, //牙松动一级左上
	        		 teethMoveOneLeftDown :  teethMoveOneLeftDown, //牙松动一级左下
	        		 teethMoveOneRightUp :  teethMoveOneRightUp, //牙松动一级右上
	        		 teethMoveOneRightDown :  teethMoveOneRightDown, //牙松动一级右下
	        		 teethMoveTwoLeftUp :  teethMoveTwoLeftUp, //牙松动二级左上
	        		 teethMoveTwoLeftDown :  teethMoveTwoLeftDown, //牙松动二级左下
	        		 teethMoveTwoRightUp :  teethMoveTwoRightUp, //牙松动二级右上
	        		 teethMoveTwoRightDown :  teethMoveTwoRightDown, //牙松动二级右下
	        		 teethMoveThreeLeftUp :  teethMoveThreeLeftUp, //牙松动三级左上
	        		 teethMoveThreeLeftDown :  teethMoveThreeLeftDown, //牙松动三级左下
	        		 teethMoveThreeRightUp :  teethMoveThreeRightUp, //牙松动三级右上
	        		 teethMoveThreeRightDown :  teethMoveThreeRightDown, //牙松动三级右下
	        		 reasonMissTeeth :  reasonMissTeeth, //牙缺失原因
	        		 timeMissTeeth :  timeMissTeeth, //牙缺失时间
	        		 mucosaCondition :  mucosaCondition, //粘膜情况
	        		 xrayShowsLeftUp :  xrayShowsLeftUp, //X射线左上
	        		 xrayShowsLeftDown :  xrayShowsLeftDown, //X射线左下
	        		 xrayShowsRightUp :  xrayShowsRightUp, //X射线右上
	        		 xrayShowsRightDown :  xrayShowsRightDown, //X射线右下
	        		 fromMaxillarySinusFloor :  fromMaxillarySinusFloor, //牙槽嵴顶矩上颌窦底
	        		 fromNasalFloor :  fromNasalFloor, //矩鼻底
	        		 fromInferiorDentalNerve :  fromInferiorDentalNerve, //距下牙槽神经管
	        		 alveloarCrestWidths :  alveloarCrestWidths, //牙槽嵴宽度
	        		 alveloarBoneResorptionLeftUp :  alveloarBoneResorptionLeftUp, //牙槽骨吸收左上
	        		 alveloarBoneResorptionRightUp :  alveloarBoneResorptionRightUp, //牙槽骨吸收右上
	        		 alveloarBoneResorptionLeftDown :  alveloarBoneResorptionLeftDown, //牙槽骨吸收左下
	        		 alveloarBoneResorptionRightDown :  alveloarBoneResorptionRightDown, //牙槽骨吸收右下
	        		 periodontal_lesionLeftUp :  periodontal_lesionLeftUp, //牙根尖阴影左上
	        		 periodontal_lesionRightUp :  periodontal_lesionRightUp, //牙根尖阴影右上
	        		 periodontal_lesionLeftDown :  periodontal_lesionLeftDown, //牙根尖阴影左下
	        		 periodontal_lesionRightDown :  periodontal_lesionRightDown, //牙根尖阴影右下
	        		 diagnosis :  diagnosis, //诊断
	        		 others :  others, //其他
	        		 doctor_signatory :  signature, //医生签字
	        		 doctor_signatoryTime :  doctor_signatoryTime //医生签字时间
	        };
	        //console.log(JSON.stringify(param)+"------------检查及诊断参数");
	       
	        $.axseSubmit(url, param,function() {},function(r) {
	        	layer.alert("保存成功！", {
		            end: function() {
		            	//window.parent.location.reload(); //刷新父页面
		                var frameindex = parent.layer.getFrameIndex(window.name);
		                parent.layer.close(frameindex); //再执行关闭
		            }
		      	});
	        },function(r){
	        	layer.alert("保存失败！");
		    }); 
		}
		
		
		/* 获取拼接牙位并校验,参数为当前input的id */
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
					 content: '请输入正确牙位！(牙位值为1~8,且不能重复)',
					 end:function(){
						 $("#"+inputObj).val("").focus();
						 toothString="";
					 }
				});  
			}
			toothString=toothArr.join(",");
			//console.log(toothString+"------拼接字符串");//拼接字符串
			return toothString;
		};
		
		function getButtonPower() {
		    var menubutton1 = "";
		    for (var i = 0; i < listbutton.length; i++) {
		        if (listbutton[i].qxName == "zsbs_xgbd"&&doctorstatus) {
		           $("#consent_updateBtn").removeClass("hidden");
		        }
		    }
		    $("#bottomBarDdiv").append(menubutton1);
		}
		
		function doPrint() {   
		    bdhtml=window.document.body.innerHTML;   
		    sprnstr="<!--startprint-->";   
		    eprnstr="<!--endprint-->";   
		    prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);   
		    prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));   
		    var htmlStyle="<style>#others{border-style: none;border-bottom: 1px solid #5b5b5b;}button{display:none;}span{font-size: 12px!important;}*{font-size: 12px;line-height: 16px;}.examine_continer input[type='checkbox']{width:12px !important;height:12px !important;margin-top: 10px !important;}.inputheight2{border: 1px solid transparent!important;}.consent_updateBtn{display:none!important;}.btns{display:none!important;}#logoImg{text-align:left!important;width:20%!important;left:0%!important;}</style>";
		    window.document.body.innerHTML=prnhtml+htmlStyle;  
		    window.print();  //打印
		    document.body.innerHTML=bdhtml; //恢复页面
		} 
		
		function myPreviewAll(){
			if(doctorstatus&&signature==""){
				   $("#img").css("display","none");
			   }
			doPrint();
		};

</script>
</html>