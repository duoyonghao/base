<%@page import="java.io.Console"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//当前主诉及既往病史方案id
	String seqidFather = request.getParameter("seqidFather");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/hudh/lclj/flowdetail/css/base.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
	<link type="text/css" rel="stylesheet" href="<%=contextPath%>/static/css/kqdsFront/plantCase/anamnesis.css" />

	<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
	<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
	<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
</head>
<style type="text/css">
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
	@media print{
		*{
			font-size: 12px;
		}
		#logoImg{
			position: absolute;
			top: 20px!important;
			width: 150px!important;
		}
		#anamnesis_continer .bigtitle{
			font-size: 22px;
			margin: 0px auto 10px !important;
		}
		.smallTitle{
			font-size: 13px !important;
			padding-top: 3px;
		}
		#pressure{
			width: 80px !important;
		}
		.rpInfo_import{
			margin-bottom: 0px !important;
			line-height: 20px !important;
		}
		.rpInfo_import .alreadyInfo{
			font-size: 12px;
		}
		.rpInfo_import span{
			font-weight: normal;
		}
		.toothMapCase .toothMapItem {
			width:162px !important;
			min-width:0px !important;
			padding-top: 0px!important;
			margin-right: 2px!important;
		}
		.toothMapItem input[name='symptom']{
			vertical-align: sub !important;
		}
		.toothMap {
			margin: 5px 0px!important;
		}
		.toothMap ul {
			height: 20px!important;
		}
		.toothMap li{
			width: 10px!important;
		}
		.toothMap .line{
			margin: 0 !important;
		}
		.downYa li {
			margin-top: 0px!important;
		}
		.toothTime>input{
			width: 80px!important;
			height: 20px !important;
			line-height: 20px !important;
		}
		#anamnesis_continer input[type="radio"],input[type="checkbox"]{
			width: 12px;
			height: 12px;
			vertical-align: text-bottom !important;
		}
		#anamnesis_continer .common_style{
			height: 24px;
		}
		.common_style span,label{
			margin-top: 4px !important;
		}
		#anamnesis_continer .option_div>input {
			margin-top: 7px;
		}
		#anamnesis_continer .time_div>input{
			margin-top: 0px !important;
			height: 20px;
			line-height: 20px;
			font-size: 12px;
		}
		.selectgroup .groupSelect {
			margin-top: 0px !important;
		}
		#anamnesis_continer .fillWrite_group>input{
			margin-top: 0px !important;
			height: 20px;
			line-height: 20px;
			font-size: 12px;
		}
		button{
			display: none;
		}
		#consent_updateBtn{
			display: none !important;
		}
		/*常用药物多选*/
		.selectgroup{
			display: none !important;
		}
		.medicineText{
			display: block !important;
			margin-top: 3px!important;
		}
		.otherText{
			margin-left: 20px !important;
		}
		#onmedication{
			width: 78% !important;
		}
		#secondLogoImg{
			display: block!important;
		}
		.question_text{
			font-size: 13px !important;
		}
		/*基本信息*/
		.baseInfo .patient>.inputDiv{
			height: 22px !important;
			line-height: 22px !important;
		}
		.baseInfo .patient>.inputDiv>span,font{
			font-size: 12px !important;
			height: 22px !important;
			line-height: 22px !important;
		}
		.patient .inputDiv:nth-child(4){
			width: 190px!important;
		}
		.patient .inputDiv:nth-child(8){
			width: 160px!important;
		}
		.patient .inputDiv:nth-child(9){
			/*width: 190px!important;*/
			width: 160px!important;
		}
		.inputPrintWidth{
			width: 50px !important;
		}
	}
	#anamnesis_continer .bgwhite{
		background-color: white;
	}
	.rpInfo_import{
		margin-bottom: 10px;
		line-height: 24px;
	}
	.rpInfo_import input{
		width: 110px;
		margin-left: 5px;
	}
	.combgGray {
		background-color: transparent;
	}
	/* 小标题 */
	.smallTitle{
		font-size: 17px;
		color: #333333;
		font-weight: bold;
		border-top: 1px solid black;
	}
	.smallTitle .circle{
		display: inline-block;
		width:12px;
		height:12px;
		border-radius:50%;
		margin-right: 10px;
		border: 6px solid #333333;
	}
	/* 牙位 */
	.toothMapCase{
		display:block;
		width: 100%;
		overflow: hidden;
	}
	.toothMapCase .toothMapItem{
		display:block;
		width:25%;
		min-width:345px;
		float:left;
		text-align: center;
	}
	.toothMapCase .toothMapItem input[name="symptom"]{
		vertical-align: top;
		margin-right: 5px;
	}
	#anamnesis_continer .common_style{
		padding-left: 0px;
	}
	/* 牙位图 */
	#ToothBit{
		width:78%;
		min-width: 745px;
		overflow: hidden;
	}
	#ToothBit>div{
		float: left;
		width:50%;
	}
	.children .upYa,.children .downYa{margin-left: -200px;}
	.downYa span:first-child{display: inline-block;margin-bottom: 5px;}
	.toothMap{
		position:relative;
		margin: 10px 10px;
	}
	.toothMap .toothMap{
		overflow: hidden;
		height: 226px;
		box-shadow: 0px 0px 10px 2px #ddd;
		border-radius: 9px;
		padding-top: 86px;
		position: relative;
		background-color: white;
	}
	.toothMap .verticalLine{
		display: block;
		height: 100%;
		border-right: 1px solid black;
		position: absolute;
		top: 0px;
		left: 49.7%;
	}
	/* 更改牙位 */
	.toothMap .showToothMap{
		display: block;
		font-size: 14px;
		line-height:14px;
		color: #434343;
		text-align:center;
		margin-top: 45px;
		margin: 45px auto 0px;
	}
	.toothMap ul{overflow: hidden;height:24px;}
	.toothMap li{visibility: hidden;float: left; width: 20px;text-align: center;cursor: pointer;}
	.toothMap .line{
		color:#888;
		border-top: 1px solid black;
		margin: 0 10px;
	}
	.toothMap .line .left{
		font-size: 13px !important;
	}
	.toothMap .line .right{
		font-size: 13px !important;
	}
	.upYa,.downYa{
		width: 320px;
		margin: 0 auto!important;
	}
	.upYa li{
		margin-bottom: 5px;
	}
	.downYa li{
		margin-top: 5px;
	}
	.yaIcon{
		display: none;
	}
	.toothMap ul li span.num{
		display:inline-block;
		width:15px; height:15px;
		border-radius:50%; color:#333; background:transparent;
		line-height:15px !important;
		font-size:12px !important;
		text-align: center;
		margin-top: 3px;
	}
	.toothMap li.current{
		visibility: visible;
	}
	.rpInfo_import {
		width: auto;
		margin-right: 35px;
	}
	#anamnesis_continer .bigtitle{
		display: block;
		text-align: center;
		margin: 7px auto 18px;
	}
	#logoImg{
		position: absolute;
		top: 18px;
		width: 200px;
	}
	.patient_info{
		overflow: hidden;
	}
	.patientHeader{
	}
	.bootstrap-select:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn){
		width: 180px;
	}
	.selectTag .btn-default{
		height: 30px;
		line-height: 30px;
		font-size: 14px;
		padding: 0px 30px 0px 5px;
	}
	#anamnesis_continer>.row{
		overflow: visible;
	}
	/*多选框样式*/
	.selectgroup .groupSelect{
		padding-right: 0px;
		border: 0px solid #c3c3c3;
	}
	.bootstrap-select > .dropdown-toggle{
		width: 100%;
		padding: 0px;
		height: 24px;
		line-height: 24px;
		padding-right: 25px;
	}
	.pull-left{
		margin-top: 0px !important;
		font-size: 14px;
		padding: 0px 5px;
	}
	.bs-caret .caret{
		margin-top: 0px !important;
	}
	.glyphicon{
		margin-top: 5px !important;
		font-size: 12px;
	}
	.dropdown-menu > li > a{
		padding: 0px 15px;
	}
	.bootstrap-select.btn-group.show-tick .dropdown-menu li a span.text {
		line-height: 24px;
		font-size: 14px;
		margin-top: 0px !important;
	}
	.bootstrap-select .dropdown-toggle:focus {
		outline: 0px dotted #333333 !important;
		outline: 0px auto -webkit-focus-ring-color !important;
		outline-offset: -2px;
	}
	.medicineText{
		display: none;
		margin: 8px 0px;
	}
	@page{
		size:206mm 280mm;
		margin: 0 auto;
	}
	#anamnesis_continer span {
		font-weight: normal;
	}
	.btns button:focus{
		border:0px solid red;
		outline: none;
	}
	/* 基本信息 */
	.baseInfo{
		width:100%;
		padding-left: 8px;
	}
	.baseInfo .patient{
		overflow: hidden;
	}
	/* 输入组合框 */
	.baseInfo .patient>.inputDiv{
		display:block;
		float:left;
		width: 235px;
		height: 25px;
	}
	.patient .inputDiv:nth-child(5){
		width: 100px!important;
	}
	.patient .inputDiv:nth-child(7){
		width: 300px!important;
	}

	.baseInfo .patient>.inputDiv>span{
		display: inline-block;
		font-size: 16px;
		line-height: 30px;
		height: 30px;
	}
	.baseInfo .patient>.inputDiv>font{
		font-size: 15px;
		color:#00a6c0;
		font-weight: bold;
	}
	.signature_row .signature_time>.signature_box>span {
		font-weight: normal !important;
	}
</style>
<body>
<!--startprint-->
<div id="anamnesis_continer" class="container-fluid" style="height:100%;">
	<!-- 标题 -->
	<div class="row">
		<div class="col-md-12 col-sm-12" style="position: relative;padding: 0;">
			<!-- <div class="big_title"><span class="bigtitle">主诉及既往病史</span></div> -->
			<img id="logoImg" src="<%=contextPath%>/static/image/kqdsFront/jiagong/logoName.png">
			<span class="bigtitle">主诉及既往病史</span>
		</div>
	</div>
	<!-- 患者信息 -->
	<div class="row patientInfo">
		<div class="col-md-12 col-sm-12 col-xs-12 smallTitle" style="padding-left:0px;"><span class="circle"></span>基本信息</div>
		<div class="row baseInfo">
			<div class="col-md-12 col-sm-12 colDefined">
				<!-- 患者基本信息 -->
				<div class="patient" style="width:90%;float:left;">
					<div class="inputDiv">
						<span>首诊时间：</span><font type="text" id="patient_first_diagnose"></font>
					</div>
					<div class="inputDiv">
						<span>患者编号：</span><font type="text" id="patient_num"></font>
					</div>
					<div class="inputDiv" style="clear:left;">
						<span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span><font type="text" id="patient_name"></font>
					</div>
					<div class="inputDiv">
						<span>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span><font type="text" id="patient_sex"></font>
					</div>
					<div class="inputDiv" style="">
						<span>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄：</span><font type="text" id="patient_age"></font>
					</div>
					<div class="inputDiv" style="clear:left;">
						<span>证件号码：</span><font type="text" id="patient_ID"></font>
					</div>
					<div class="inputDiv">
						<%--                    <span>出生年月：</span><font type="text" id="patient_bone"></font>--%>
						<span>现居住地址：</span><font type="text" id="patient_address"></font>
					</div>
					<div class="inputDiv urgencyDiv" style="clear:left;">
						<span>联系电话：</span><font type="text" id="patient_tel"></font>
					</div>
					<div class="inputDiv urgencyDiv" >
						<span>紧急联系人：</span><font type="text" id="patient_emergency_contact"></font>
					</div>
					<div class="inputDiv">
						<span>紧急联系人电话：</span><font type="text" id="emergency_contact_tel"></font>
					</div>
				</div>
				<div class="patientHeader" style="width:10%;float:left;">
					<img src="<%=contextPath%>/static/image/kqdsFront/jiagong/headImg.jpg">
					<input style="display:none;" type="file" />
				</div>
			</div>
		</div>
		<%--<div class="col-md-12 col-sm-12 col-xs-12" style="padding:0px;display:flex;flex-direction:row;">
			<div class="patient_info">
				<!-- 首诊时间 -->
				<div class="rpInfo_import">
					<span>首诊时间:</span>&lt;%&ndash;<input id="first_time" class="consent_time" type="text" readonly="readonly" placeholder="请选择日期"/>&ndash;%&gt;
					<font class="alreadyInfo" id="first_time"></font>
				</div>
				<!-- 信息输入组合框 -->
				<div class="rpInfo_import">
					<span>编号：</span>
					<font class="alreadyInfo" id="patient_num"></font>
				</div>
				<!-- 信息输入组合框 -->
				<div class="rpInfo_import">
					<span style="display:block;" id="username">患者姓名：</span>
					<font class="alreadyInfo" id="patient_name"></font>
				</div>
				<!-- 信息输入组合框 -->
				<div class="rpInfo_import sexInfo">
					<span>性别：</span>
					<font class="alreadyInfo" id="patient_sex"></font>
				</div>
				<!-- 信息输入组合框 -->
				<div class="rpInfo_import">
					<span>年龄：</span>
					<font class="alreadyInfo" id="patient_age"></font>
				</div>
				<!-- 信息输入组合框 -->
				<div class="rpInfo_import">
					<span>证件号码：</span>
					<font class="alreadyInfo" id="patient_idnum"></font>
				</div>
				<!-- 信息输入组合框 -->
				<div class="rpInfo_import">
					<span>出生年月：</span>
					<font class="alreadyInfo" id="patient_birthday"></font>
				</div>
				<!-- 信息输入组合框 -->
				<div class="rpInfo_import">
					<span>联系电话：</span>
					<font class="alreadyInfo" id="patient_phone"></font>
				</div>
				<!-- 信息输入组合框 -->
				<div class="rpInfo_import">
					<span>紧急联系人：</span>
					<font class="alreadyInfo" id="urgency_people"></font>
				</div>
				<!-- 信息输入组合框 -->
				<div class="rpInfo_import">
					<span>紧急联系人电话：</span>
					<font class="alreadyInfo" id="urgency_phone"></font>
				</div>
				<!-- 信息输入组合框 -->
				<div class="rpInfo_import" style="width: auto;">
					<span>现居住地址：</span>
					<font class="alreadyInfo" id="patient_address"></font>
				</div>
			</div>
			<div class="patientHeader" style="width: 135px;">
				<img style="min-width:45px;" src="<%=contextPath%>/static/image/kqdsFront/jiagong/headImg.jpg">
				<input style="display:none;" type="file" />
			</div>
		</div>--%>
	</div>
	<!-- 主诉 -->
	<div class="row" style="">
		<div class="col-md-12 col-sm-12 col-xs-12 smallTitle" style="padding-left:0px;"><span class="circle"></span>主诉</div>
		<div class="col-md-12 col-sm-12 col-xs-12" style="padding-left:0px;">
			<ul class="toothMapCase">
				<li class="toothMapItem">
					<input name="symptom" id="tooth_lose" type="checkbox" value="0" onclick="isToothChecked(this.id);"/><label for="tooth_lose">牙缺失</label>
					<!-- 牙位图 -->
					<div class="toothMap toothloseMap">
						<ul class="upYa">
							<li class="leftUpTooth"><span class="yaIcon le8"></span><span class="num numle8 ToothBit_checkbox1" name="zzadultupYa1">8</span></li>
							<li class="leftUpTooth"><span class="yaIcon le7"></span><span class="num numle7 ToothBit_checkbox1" name="zzadultupYa1">7</span></li>
							<li class="leftUpTooth"><span class="yaIcon le6"></span><span class="num numle6 ToothBit_checkbox1" name="zzadultupYa1">6</span></li>
							<li class="leftUpTooth"><span class="yaIcon le5"></span><span class="num numle5 ToothBit_checkbox1" name="zzadultupYa1">5</span></li>
							<li class="leftUpTooth"><span class="yaIcon le4"></span><span class="num numle4 ToothBit_checkbox1" name="zzadultupYa1">4</span></li>
							<li class="leftUpTooth"><span class="yaIcon le3"></span><span class="num numle3 ToothBit_checkbox1" name="zzadultupYa1">3</span></li>
							<li class="leftUpTooth"><span class="yaIcon le2"></span><span class="num numle2 ToothBit_checkbox1" name="zzadultupYa1">2</span></li>
							<li class="leftUpTooth"><span class="yaIcon le1"></span><span class="num numle1 ToothBit_checkbox1" name="zzadultupYa1">1</span></li>

							<li class="rightUpTooth"><span class="yaIcon rg1"></span><span class="num numrg1 ToothBit_checkbox2" name="zzadultupYa2">1</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg2"></span><span class="num numrg2 ToothBit_checkbox2" name="zzadultupYa2">2</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg3"></span><span class="num numrg3 ToothBit_checkbox2" name="zzadultupYa2">3</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg4"></span><span class="num numrg4 ToothBit_checkbox2" name="zzadultupYa2">4</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg5"></span><span class="num numrg5 ToothBit_checkbox2" name="zzadultupYa2">5</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg6"></span><span class="num numrg6 ToothBit_checkbox2" name="zzadultupYa2">6</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg7"></span><span class="num numrg7 ToothBit_checkbox2" name="zzadultupYa2">7</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg8"></span><span class="num numrg8 ToothBit_checkbox2" name="zzadultupYa2">8</span></li>
						</ul>
						<div class="line">
						</div>
						<div class="verticalLine"></div>
						<ul class="downYa">
							<li class="leftDownTooth"><span class="num numle8 ToothBit_checkbox3" name="zzadultdownYa1">8</span><span class="yaIcon le8"></span></li>
							<li class="leftDownTooth"><span class="num numle7 ToothBit_checkbox3" name="zzadultdownYa1">7</span><span class="yaIcon le7"></span></li>
							<li class="leftDownTooth"><span class="num numle6 ToothBit_checkbox3" name="zzadultdownYa1">6</span><span class="yaIcon le6"></span></li>
							<li class="leftDownTooth"><span class="num numle5 ToothBit_checkbox3" name="zzadultdownYa1">5</span><span class="yaIcon le5"></span></li>
							<li class="leftDownTooth"><span class="num numle4 ToothBit_checkbox3" name="zzadultdownYa1">4</span><span class="yaIcon le4"></span></li>
							<li class="leftDownTooth"><span class="num numle3 ToothBit_checkbox3" name="zzadultdownYa1">3</span><span class="yaIcon le3"></span></li>
							<li class="leftDownTooth"><span class="num numle2 ToothBit_checkbox3" name="zzadultdownYa1">2</span><span class="yaIcon le2"></span></li>
							<li class="leftDownTooth"><span class="num numle1 ToothBit_checkbox3" name="zzadultdownYa1">1</span><span class="yaIcon le1"></span></li>

							<li class="rightDownTooth"><span class="num numrg1 ToothBit_checkbox4" name="zzadultdownYa2">1</span><span class="yaIcon rg1"></span></li>
							<li class="rightDownTooth"><span class="num numrg2 ToothBit_checkbox4" name="zzadultdownYa2">2</span><span class="yaIcon rg2"></span></li>
							<li class="rightDownTooth"><span class="num numrg3 ToothBit_checkbox4" name="zzadultdownYa2">3</span><span class="yaIcon rg3"></span></li>
							<li class="rightDownTooth"><span class="num numrg4 ToothBit_checkbox4" name="zzadultdownYa2">4</span><span class="yaIcon rg4"></span></li>
							<li class="rightDownTooth"><span class="num numrg5 ToothBit_checkbox4" name="zzadultdownYa2">5</span><span class="yaIcon rg5"></span></li>
							<li class="rightDownTooth"><span class="num numrg6 ToothBit_checkbox4" name="zzadultdownYa2">6</span><span class="yaIcon rg6"></span></li>
							<li class="rightDownTooth"><span class="num numrg7 ToothBit_checkbox4" name="zzadultdownYa2">7</span><span class="yaIcon rg7"></span></li>
							<li class="rightDownTooth"><span class="num numrg8 ToothBit_checkbox4" name="zzadultdownYa2">8</span><span class="yaIcon rg8"></span></li>
						</ul>
						<%--<span class="showToothMap" style="display:none;width: 100px;height: 28px;border-radius: 5px;/* margin-left: 41%; */background: #00a6c0;color: #fff;padding: 7px;">更改牙位</span>--%>
					</div>
					<div class="toothTime">
						<span>时长：</span><input id="toothlosetime" type="text" disabled style="cursor:not-allowed;" placeholder="请输入时长"/>
					</div>
				</li>
				<li class="toothMapItem">
					<input name="symptom" id="tooth_less" type="checkbox" value="1" onclick="isToothChecked(this.id);"/><label for="tooth_less">牙松动</label>
					<!-- 牙位图 -->
					<div class="toothMap toothlessMap">
						<ul class="upYa">
							<li class="leftUpTooth"><span class="yaIcon le8"></span><span class="num numle8 ToothBit_checkbox1" name="zzadultupYa1">8</span></li>
							<li class="leftUpTooth"><span class="yaIcon le7"></span><span class="num numle7 ToothBit_checkbox1" name="zzadultupYa1">7</span></li>
							<li class="leftUpTooth"><span class="yaIcon le6"></span><span class="num numle6 ToothBit_checkbox1" name="zzadultupYa1">6</span></li>
							<li class="leftUpTooth"><span class="yaIcon le5"></span><span class="num numle5 ToothBit_checkbox1" name="zzadultupYa1">5</span></li>
							<li class="leftUpTooth"><span class="yaIcon le4"></span><span class="num numle4 ToothBit_checkbox1" name="zzadultupYa1">4</span></li>
							<li class="leftUpTooth"><span class="yaIcon le3"></span><span class="num numle3 ToothBit_checkbox1" name="zzadultupYa1">3</span></li>
							<li class="leftUpTooth"><span class="yaIcon le2"></span><span class="num numle2 ToothBit_checkbox1" name="zzadultupYa1">2</span></li>
							<li class="leftUpTooth"><span class="yaIcon le1"></span><span class="num numle1 ToothBit_checkbox1" name="zzadultupYa1">1</span></li>

							<li class="rightUpTooth"><span class="yaIcon rg1"></span><span class="num numrg1 ToothBit_checkbox2" name="zzadultupYa2">1</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg2"></span><span class="num numrg2 ToothBit_checkbox2" name="zzadultupYa2">2</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg3"></span><span class="num numrg3 ToothBit_checkbox2" name="zzadultupYa2">3</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg4"></span><span class="num numrg4 ToothBit_checkbox2" name="zzadultupYa2">4</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg5"></span><span class="num numrg5 ToothBit_checkbox2" name="zzadultupYa2">5</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg6"></span><span class="num numrg6 ToothBit_checkbox2" name="zzadultupYa2">6</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg7"></span><span class="num numrg7 ToothBit_checkbox2" name="zzadultupYa2">7</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg8"></span><span class="num numrg8 ToothBit_checkbox2" name="zzadultupYa2">8</span></li>
						</ul>
						<div class="line">
						</div>
						<div class="verticalLine"></div>
						<ul class="downYa">
							<li class="leftDownTooth"><span class="num numle8 ToothBit_checkbox3" name="zzadultdownYa1">8</span><span class="yaIcon le8"></span></li>
							<li class="leftDownTooth"><span class="num numle7 ToothBit_checkbox3" name="zzadultdownYa1">7</span><span class="yaIcon le7"></span></li>
							<li class="leftDownTooth"><span class="num numle6 ToothBit_checkbox3" name="zzadultdownYa1">6</span><span class="yaIcon le6"></span></li>
							<li class="leftDownTooth"><span class="num numle5 ToothBit_checkbox3" name="zzadultdownYa1">5</span><span class="yaIcon le5"></span></li>
							<li class="leftDownTooth"><span class="num numle4 ToothBit_checkbox3" name="zzadultdownYa1">4</span><span class="yaIcon le4"></span></li>
							<li class="leftDownTooth"><span class="num numle3 ToothBit_checkbox3" name="zzadultdownYa1">3</span><span class="yaIcon le3"></span></li>
							<li class="leftDownTooth"><span class="num numle2 ToothBit_checkbox3" name="zzadultdownYa1">2</span><span class="yaIcon le2"></span></li>
							<li class="leftDownTooth"><span class="num numle1 ToothBit_checkbox3" name="zzadultdownYa1">1</span><span class="yaIcon le1"></span></li>

							<li class="rightDownTooth"><span class="num numrg1 ToothBit_checkbox4" name="zzadultdownYa2">1</span><span class="yaIcon rg1"></span></li>
							<li class="rightDownTooth"><span class="num numrg2 ToothBit_checkbox4" name="zzadultdownYa2">2</span><span class="yaIcon rg2"></span></li>
							<li class="rightDownTooth"><span class="num numrg3 ToothBit_checkbox4" name="zzadultdownYa2">3</span><span class="yaIcon rg3"></span></li>
							<li class="rightDownTooth"><span class="num numrg4 ToothBit_checkbox4" name="zzadultdownYa2">4</span><span class="yaIcon rg4"></span></li>
							<li class="rightDownTooth"><span class="num numrg5 ToothBit_checkbox4" name="zzadultdownYa2">5</span><span class="yaIcon rg5"></span></li>
							<li class="rightDownTooth"><span class="num numrg6 ToothBit_checkbox4" name="zzadultdownYa2">6</span><span class="yaIcon rg6"></span></li>
							<li class="rightDownTooth"><span class="num numrg7 ToothBit_checkbox4" name="zzadultdownYa2">7</span><span class="yaIcon rg7"></span></li>
							<li class="rightDownTooth"><span class="num numrg8 ToothBit_checkbox4" name="zzadultdownYa2">8</span><span class="yaIcon rg8"></span></li>
						</ul>
						<%--<span class="showToothMap" style="display:none;width: 100px;height: 28px;border-radius: 5px;/* margin-left: 41%; */background: #00a6c0;color: #fff;padding: 7px;">更改牙位</span>--%>
					</div>
					<div class="toothTime">
						<span>时长：</span><input id="toothlesstime" type="text" disabled style="cursor:not-allowed;" placeholder="请输入时长"/>
					</div>
				</li>
				<li class="toothMapItem">
					<input name="symptom" id="tooth_decayed" type="checkbox" value="2" onclick="isToothChecked(this.id);"/><label for="tooth_decayed">龋齿</label>
					<!-- 牙位图 -->
					<div class="toothMap toothdecayedMap">
						<ul class="upYa">
							<li class="leftUpTooth"><span class="yaIcon le8"></span><span class="num numle8 ToothBit_checkbox1" name="zzadultupYa1">8</span></li>
							<li class="leftUpTooth"><span class="yaIcon le7"></span><span class="num numle7 ToothBit_checkbox1" name="zzadultupYa1">7</span></li>
							<li class="leftUpTooth"><span class="yaIcon le6"></span><span class="num numle6 ToothBit_checkbox1" name="zzadultupYa1">6</span></li>
							<li class="leftUpTooth"><span class="yaIcon le5"></span><span class="num numle5 ToothBit_checkbox1" name="zzadultupYa1">5</span></li>
							<li class="leftUpTooth"><span class="yaIcon le4"></span><span class="num numle4 ToothBit_checkbox1" name="zzadultupYa1">4</span></li>
							<li class="leftUpTooth"><span class="yaIcon le3"></span><span class="num numle3 ToothBit_checkbox1" name="zzadultupYa1">3</span></li>
							<li class="leftUpTooth"><span class="yaIcon le2"></span><span class="num numle2 ToothBit_checkbox1" name="zzadultupYa1">2</span></li>
							<li class="leftUpTooth"><span class="yaIcon le1"></span><span class="num numle1 ToothBit_checkbox1" name="zzadultupYa1">1</span></li>

							<li class="rightUpTooth"><span class="yaIcon rg1"></span><span class="num numrg1 ToothBit_checkbox2" name="zzadultupYa2">1</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg2"></span><span class="num numrg2 ToothBit_checkbox2" name="zzadultupYa2">2</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg3"></span><span class="num numrg3 ToothBit_checkbox2" name="zzadultupYa2">3</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg4"></span><span class="num numrg4 ToothBit_checkbox2" name="zzadultupYa2">4</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg5"></span><span class="num numrg5 ToothBit_checkbox2" name="zzadultupYa2">5</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg6"></span><span class="num numrg6 ToothBit_checkbox2" name="zzadultupYa2">6</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg7"></span><span class="num numrg7 ToothBit_checkbox2" name="zzadultupYa2">7</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg8"></span><span class="num numrg8 ToothBit_checkbox2" name="zzadultupYa2">8</span></li>
						</ul>
						<div class="line">
						</div>
						<div class="verticalLine"></div>
						<ul class="downYa">
							<li class="leftDownTooth"><span class="num numle8 ToothBit_checkbox3" name="zzadultdownYa1">8</span><span class="yaIcon le8"></span></li>
							<li class="leftDownTooth"><span class="num numle7 ToothBit_checkbox3" name="zzadultdownYa1">7</span><span class="yaIcon le7"></span></li>
							<li class="leftDownTooth"><span class="num numle6 ToothBit_checkbox3" name="zzadultdownYa1">6</span><span class="yaIcon le6"></span></li>
							<li class="leftDownTooth"><span class="num numle5 ToothBit_checkbox3" name="zzadultdownYa1">5</span><span class="yaIcon le5"></span></li>
							<li class="leftDownTooth"><span class="num numle4 ToothBit_checkbox3" name="zzadultdownYa1">4</span><span class="yaIcon le4"></span></li>
							<li class="leftDownTooth"><span class="num numle3 ToothBit_checkbox3" name="zzadultdownYa1">3</span><span class="yaIcon le3"></span></li>
							<li class="leftDownTooth"><span class="num numle2 ToothBit_checkbox3" name="zzadultdownYa1">2</span><span class="yaIcon le2"></span></li>
							<li class="leftDownTooth"><span class="num numle1 ToothBit_checkbox3" name="zzadultdownYa1">1</span><span class="yaIcon le1"></span></li>

							<li class="rightDownTooth"><span class="num numrg1 ToothBit_checkbox4" name="zzadultdownYa2">1</span><span class="yaIcon rg1"></span></li>
							<li class="rightDownTooth"><span class="num numrg2 ToothBit_checkbox4" name="zzadultdownYa2">2</span><span class="yaIcon rg2"></span></li>
							<li class="rightDownTooth"><span class="num numrg3 ToothBit_checkbox4" name="zzadultdownYa2">3</span><span class="yaIcon rg3"></span></li>
							<li class="rightDownTooth"><span class="num numrg4 ToothBit_checkbox4" name="zzadultdownYa2">4</span><span class="yaIcon rg4"></span></li>
							<li class="rightDownTooth"><span class="num numrg5 ToothBit_checkbox4" name="zzadultdownYa2">5</span><span class="yaIcon rg5"></span></li>
							<li class="rightDownTooth"><span class="num numrg6 ToothBit_checkbox4" name="zzadultdownYa2">6</span><span class="yaIcon rg6"></span></li>
							<li class="rightDownTooth"><span class="num numrg7 ToothBit_checkbox4" name="zzadultdownYa2">7</span><span class="yaIcon rg7"></span></li>
							<li class="rightDownTooth"><span class="num numrg8 ToothBit_checkbox4" name="zzadultdownYa2">8</span><span class="yaIcon rg8"></span></li>
						</ul>
						<%--<span class="showToothMap" style="display:none;width: 100px;height: 28px;border-radius: 5px;/* margin-left: 41%; */background: #00a6c0;color: #fff;padding: 7px;">更改牙位</span>--%>
					</div>
					<div class="toothTime">
						<span>时长：</span><input id="toothdecayedtime" type="text" disabled style="cursor:not-allowed;" placeholder="请输入时长"/>
					</div>
				</li>
				<li class="toothMapItem">
					<input name="symptom" id="tooth_snap" type="checkbox" value="3" onclick="isToothChecked(this.id);"/><label for="tooth_snap">牙折断</label>
					<!-- 牙位图 -->
					<div class="toothMap toothsnapMap">
						<ul class="upYa">
							<li class="leftUpTooth"><span class="yaIcon le8"></span><span class="num numle8 ToothBit_checkbox1" name="zzadultupYa1">8</span></li>
							<li class="leftUpTooth"><span class="yaIcon le7"></span><span class="num numle7 ToothBit_checkbox1" name="zzadultupYa1">7</span></li>
							<li class="leftUpTooth"><span class="yaIcon le6"></span><span class="num numle6 ToothBit_checkbox1" name="zzadultupYa1">6</span></li>
							<li class="leftUpTooth"><span class="yaIcon le5"></span><span class="num numle5 ToothBit_checkbox1" name="zzadultupYa1">5</span></li>
							<li class="leftUpTooth"><span class="yaIcon le4"></span><span class="num numle4 ToothBit_checkbox1" name="zzadultupYa1">4</span></li>
							<li class="leftUpTooth"><span class="yaIcon le3"></span><span class="num numle3 ToothBit_checkbox1" name="zzadultupYa1">3</span></li>
							<li class="leftUpTooth"><span class="yaIcon le2"></span><span class="num numle2 ToothBit_checkbox1" name="zzadultupYa1">2</span></li>
							<li class="leftUpTooth"><span class="yaIcon le1"></span><span class="num numle1 ToothBit_checkbox1" name="zzadultupYa1">1</span></li>

							<li class="rightUpTooth"><span class="yaIcon rg1"></span><span class="num numrg1 ToothBit_checkbox2" name="zzadultupYa2">1</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg2"></span><span class="num numrg2 ToothBit_checkbox2" name="zzadultupYa2">2</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg3"></span><span class="num numrg3 ToothBit_checkbox2" name="zzadultupYa2">3</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg4"></span><span class="num numrg4 ToothBit_checkbox2" name="zzadultupYa2">4</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg5"></span><span class="num numrg5 ToothBit_checkbox2" name="zzadultupYa2">5</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg6"></span><span class="num numrg6 ToothBit_checkbox2" name="zzadultupYa2">6</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg7"></span><span class="num numrg7 ToothBit_checkbox2" name="zzadultupYa2">7</span></li>
							<li class="rightUpTooth"><span class="yaIcon rg8"></span><span class="num numrg8 ToothBit_checkbox2" name="zzadultupYa2">8</span></li>
						</ul>
						<div class="line">
						</div>
						<div class="verticalLine"></div>
						<ul class="downYa">
							<li class="leftDownTooth"><span class="num numle8 ToothBit_checkbox3" name="zzadultdownYa1">8</span><span class="yaIcon le8"></span></li>
							<li class="leftDownTooth"><span class="num numle7 ToothBit_checkbox3" name="zzadultdownYa1">7</span><span class="yaIcon le7"></span></li>
							<li class="leftDownTooth"><span class="num numle6 ToothBit_checkbox3" name="zzadultdownYa1">6</span><span class="yaIcon le6"></span></li>
							<li class="leftDownTooth"><span class="num numle5 ToothBit_checkbox3" name="zzadultdownYa1">5</span><span class="yaIcon le5"></span></li>
							<li class="leftDownTooth"><span class="num numle4 ToothBit_checkbox3" name="zzadultdownYa1">4</span><span class="yaIcon le4"></span></li>
							<li class="leftDownTooth"><span class="num numle3 ToothBit_checkbox3" name="zzadultdownYa1">3</span><span class="yaIcon le3"></span></li>
							<li class="leftDownTooth"><span class="num numle2 ToothBit_checkbox3" name="zzadultdownYa1">2</span><span class="yaIcon le2"></span></li>
							<li class="leftDownTooth"><span class="num numle1 ToothBit_checkbox3" name="zzadultdownYa1">1</span><span class="yaIcon le1"></span></li>

							<li class="rightDownTooth"><span class="num numrg1 ToothBit_checkbox4" name="zzadultdownYa2">1</span><span class="yaIcon rg1"></span></li>
							<li class="rightDownTooth"><span class="num numrg2 ToothBit_checkbox4" name="zzadultdownYa2">2</span><span class="yaIcon rg2"></span></li>
							<li class="rightDownTooth"><span class="num numrg3 ToothBit_checkbox4" name="zzadultdownYa2">3</span><span class="yaIcon rg3"></span></li>
							<li class="rightDownTooth"><span class="num numrg4 ToothBit_checkbox4" name="zzadultdownYa2">4</span><span class="yaIcon rg4"></span></li>
							<li class="rightDownTooth"><span class="num numrg5 ToothBit_checkbox4" name="zzadultdownYa2">5</span><span class="yaIcon rg5"></span></li>
							<li class="rightDownTooth"><span class="num numrg6 ToothBit_checkbox4" name="zzadultdownYa2">6</span><span class="yaIcon rg6"></span></li>
							<li class="rightDownTooth"><span class="num numrg7 ToothBit_checkbox4" name="zzadultdownYa2">7</span><span class="yaIcon rg7"></span></li>
							<li class="rightDownTooth"><span class="num numrg8 ToothBit_checkbox4" name="zzadultdownYa2">8</span><span class="yaIcon rg8"></span></li>
						</ul>
						<%--<span class="showToothMap" style="display:none;width: 100px;height: 28px;border-radius: 5px;/* margin-left: 41%; */background: #00a6c0;color: #fff;padding: 7px;">更改牙位</span>--%>
					</div>
					<div class="toothTime">
						<span>时长：</span><input id="toothsnaptime" disabled style="cursor:not-allowed;"  type="text" placeholder="请输入时长"/>
					</div>
				</li>
			</ul>
			<!-- 选择组合框 -->
			<div class="select_group common_style">
				<!-- 选项名称 -->
				<span class="select_name">要求种植修复</span>
			</div>
		</div>
	</div>
	<!-- 既往史 -->
	<!-- 高血压 -->
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12 smallTitle" style="padding-left:0px;"><span class="circle"></span>既往史</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>高血压:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<div class="select_group common_style">
				<!-- 选项div -->
				<div class="option_div">
					<input name="ishypertension" id="isHypertension_n" value="0" type="radio" onclick="ishaveillness(this.name);ishavahypertension(this.name);"/><label for="isHypertension_n">无</label>
					<input name="ishypertension" id="isHypertension_y" value="1" type="radio" onclick="ishaveillness(this.name);ishavahypertension(this.name);"/><label for="isHypertension_y">有</label>
				</div>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="hypertension" disabled type="text" style="cursor:not-allowed;" placeholder="时间"/><span>年</span>
			</div>
		</div>
		<div class="col-md-3 col-sm-3 col-xs-3 colDefined combgGray backColor">
			<!-- 选择组合框 -->
			<div class="select_group common_style">
				<!-- 选项名称 -->
				<span class="select_name">是否服药:</span>
				<!-- 选项 -->
				<!-- 选项div -->
				<div class="option_div">
					<input name="istakemedicie" disabled id="isTakeMedicie_y" value="1" type="radio"/><label for="isTakeMedicie_y">是</label>
					<input name="istakemedicie" disabled id="isTakeMedicie_n" value="0" type="radio"/><label for="isTakeMedicie_n">否</label>
				</div>
			</div>
		</div>
		<div class="col-md-3 col-sm-3 col-xs-3 colDefined combgGray">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写名称 -->
				<span class="fillWrite_name" style="font-weight: normal;">目前血压:</span>
				<!-- 填写框-->
				<input id="pressure" disabled style="width:120px;text-align:center;padding:0px;cursor:not-allowed;" class="fillWrite_input" placeholder="125/69" onblur="TextLengthCheck(this.id,10);" type="text"/>
			</div>
		</div>
		<%--<div class="col-md-5 col-sm-6 col-xs-6 colDefined combgGray">
			<!-- 填写组合框 -->
			<div class="selectgroup common_style hypertensionmedicineDiv">
				<span class="groupName" style="font-weight: normal;">常用药物：</span>
				<select id="hypertensionmedicine" class="groupSelect selectpicker" multiple title="请选择" style="pointer-events: none;">
					&lt;%&ndash;<option value="">请选择</option>&ndash;%&gt;
					<option value="倍他洛克">倍他洛克</option>
					<option value="比索洛尔">比索洛尔</option>
					<option value="硝苯地平">硝苯地平</option>
					<option value="氨氯地平">氨氯地平</option>
					<option value="非洛地平">非洛地平</option>
					<option value="卡托普利">卡托普利</option>
					<option value="贝那普利">贝那普利</option>
					<option value="替米沙坦">替米沙坦</option>
					<option value="厄贝沙坦">厄贝沙坦</option>
					<option value="缬沙坦">缬沙坦</option>
					<option value="络活喜">络活喜</option>
				</select>
			</div>
			&lt;%&ndash;打印展示&ndash;%&gt;
			<div class="medicineText">常用药物：<span class="hypertensionmedicine"></span></div>
		</div>--%>
	</div>
	<!-- 心脏病 -->
	<div class="row">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 名称div -->
			<div class="name_div common_style bgwhite">
				<span>心脏疾病:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<div class="select_group common_style bgwhite">
				<!-- 选项div -->
				<div class="option_div">
					<input name="isheardiease" id="IsHearDiease_n" value="0" type="radio" onclick="ishaveillness(this.name);ishavaheardiease(this.name);"/><label for="IsHearDiease_n">无</label>
					<input name="isheardiease" id="IsHearDiease_y" value="1" type="radio" onclick="ishaveillness(this.name);ishavaheardiease(this.name);"/><label for="IsHearDiease_y">有</label>
				</div>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 时间div -->
			<div class="time_div common_style bgwhite">
				<input id="heardiease" disabled style="cursor:not-allowed;" type="text" placeholder="时间"/><span>年</span>
			</div>
		</div>
		<div class="col-md-3 col-sm-5 col-xs-5 colDefined backcolorGray">
			<!-- 选择组合框 -->
			<div class="select_group common_style">
				<!-- 选项名称 -->
				<span class="select_name">是否常备药在身边:</span>
				<!-- 选项 -->
				<div class="option_div">
					<input name="ispreparemedication" id="isPrepareMedication_y" disabled style="cursor:not-allowed;" value="1" type="radio"/><label for="isPrepareMedication_y">是</label>
					<input name="ispreparemedication" id="isPrepareMedication_n" disabled style="cursor:not-allowed;" value="0" type="radio"/><label for="isPrepareMedication_n">否</label>
				</div>
			</div>
		</div>
		<%--<div class="col-md-4 col-sm-6 col-xs-6 colDefined backcolorGray">
			<!-- 填写组合框 -->
			<div class="selectgroup common_style">
				<span class="groupName" style="font-weight: normal;">常用药物：</span>
				<select id="heardieasemedicine" class="groupSelect selectpicker" multiple title="请选择" style="pointer-events: none;">
					&lt;%&ndash;<option value="">请选择</option>&ndash;%&gt;
					<option value="速效救心丸">速效救心丸</option>
					<option value="复方丹参滴丸">复方丹参滴丸</option>
					<option value="复方丹参片">复方丹参片</option>
					<option value="心脑康胶囊">心脑康胶囊</option>
					<option value="地奥心血康">地奥心血康</option>
					<option value="心痛定片(硝苯地平片)">心痛定片(硝苯地平片)</option>
				</select>
			</div>
			&lt;%&ndash;打印展示&ndash;%&gt;
			<div class="medicineText">常用药物：<span class="heardieasemedicine"></span></div>
		</div>--%>
	</div>
	<!-- 糖尿病 -->
	<div class="row">
		<div class="col-md-1 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>糖尿病:</span>
			</div>
		</div>
		<div class="col-md-1 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<div class="select_group common_style">
				<!-- 选项div -->
				<div class="option_div">
					<input name="isdiabetes" id="isDiabetes_n" value="0" type="radio" onclick="ishaveillness(this.name);ishavadiabetes(this.name);"/><label for="isDiabetes_n">无</label>
					<input name="isdiabetes" id="isDiabetes_y" value="1" type="radio" onclick="ishaveillness(this.name);ishavadiabetes(this.name);"/><label for="isDiabetes_y">有</label>
				</div>
			</div>
		</div>
		<div class="col-md-1 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="diabetes" type="text" disabled style="cursor:not-allowed;" placeholder="时间"/><span>年</span>
			</div>
		</div>
		<div class="col-md-3 col-sm-6 col-xs-6 colDefined combgGray backColor">
			<!-- 选择组合框 -->
			<div class="select_group common_style">
				<!-- 选项名称 -->
				<span class="select_name">控制方式:</span>
				<!-- 选项 -->
				<div class="select_item" style="width:auto;">
					<input name="dietcontrol" id="dietControl_eat" value="饮食" type="checkbox" style="vertical-align: text-bottom;margin-left: 10px;" disabled="disabled"/><label for="dietControl_eat" style="margin-right: ">饮食</label>
					<input name="dietcontrol" id="dietControl_medicine" value="口服药" type="checkbox" style="vertical-align: text-bottom;margin-left: 10px;" disabled="disabled"/><label for="dietControl_medicine">口服药</label>
					<input name="dietcontrol" id="dietControl_injection" value="针剂" type="checkbox" style="vertical-align: text-bottom;margin-left: 10px;" disabled="disabled"/><label for="dietControl_injection">针剂</label>
				</div>
			</div>
		</div>
		<%--<div class="col-md-3 col-sm-6 col-xs-6 colDefined combgGray">
			<!-- 填写组合框 -->
			<div class="selectgroup common_style">
				<span class="groupName" style="font-weight: normal;">口服常用药：</span>
				<select id="diabetesoralmedicine" class="groupSelect selectpicker" multiple title="请选择" style="pointer-events: none;">
					&lt;%&ndash;<option value="">请选择</option>&ndash;%&gt;
					<option value="二甲双胍">二甲双胍</option>
					<option value="格列美脲">格列美脲</option>
					<option value="格列本脲">格列本脲</option>
					<option value="格列齐特">格列齐特</option>
					<option value="格列吡嗪">格列吡嗪</option>
					<option value="格列喹酮">格列喹酮</option>
					<option value="α-葡萄糖苷阿卡波糖">α-葡萄糖苷阿卡波糖</option>
					<option value="伏格列波糖">伏格列波糖</option>
					<option value="那格列奈">那格列奈</option>
					<option value="瑞格列奈">瑞格列奈</option>
					<option value="罗格列酮">罗格列酮</option>
					<option value="吡格列酮">吡格列酮</option>
					<option value="磷酸西格列汀片">磷酸西格列汀片</option>
					<option value="维格列汀">维格列汀</option>
					<option value="利格列汀">利格列汀</option>
					<option value="拜糖平">拜糖平</option>
				</select>
			</div>
			&lt;%&ndash;打印展示&ndash;%&gt;
			<div class="medicineText">口服常用药：<span class="diabetesoralmedicine"></span></div>
		</div>
		<div class="col-md-3 col-sm-6 col-xs-6 colDefined combgGray">
			<!-- 填写组合框 -->
			<div class="selectgroup common_style">
				<span class="groupName" style="font-weight: normal;">注射常用药：</span>
				<select id="diabetesinjectionmedicine" class="groupSelect selectpicker" multiple title="请选择" style="pointer-events: none;">
					&lt;%&ndash;<option value="">请选择</option>&ndash;%&gt;
					<option value="利拉鲁肽">利拉鲁肽</option>
					<option value="艾塞那肽">艾塞那肽</option>
					<option value="胰岛素">胰岛素</option>
				</select>
			</div>
			&lt;%&ndash;打印展示&ndash;%&gt;
			<div class="medicineText">注射常用药：<span class="diabetesinjectionmedicine"></span></div>
		</div>--%>
	</div>
	<!-- 六个月内做过心瓣膜手术 -->
	<div class="row bgWhite">
		<div class="col-md-4 col-sm-4 col-xs-4 colDefined">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>六个月内做过心脏手术：</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="isheartoperation" id="isheartoperation_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="isheartoperation_n">无</label>
				<input name="isheartoperation" id="isheartoperation_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="isheartoperation_y">有</label>
			</div>
		</div>
		<div class="col-md-4 col-sm-4 col-xs-4 colDefined">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>六个月内发生心梗：</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="isheartinfarction" id="isheartinfarction_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="isheartinfarction_n">无</label>
				<input name="isheartinfarction" id="isheartinfarction_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="isheartinfarction_y">有</label>
			</div>
		</div>
	</div>
	<!-- 凝血功能不足性疾病 -->
	<div class="row">
		<div class="col-md-8 col-sm-8 col-xs-8 colDefined combgGray">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span class="print_textStyle">凝血功能不足性疾病(血友病、再障、血小板减少症、急性白血病):</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="isbloodcoagulation" id="IsBloodCoagulation_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="IsBloodCoagulation_n">无</label>
				<input name="isbloodcoagulation" id="IsBloodCoagulation_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="IsBloodCoagulation_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="bloodcoagulation" disabled style="cursor:not-allowed;" type="text" placeholder="时间"/><span>年</span>
			</div>
		</div>
	</div>
	<!-- 服用抗凝药物 -->
	<div class="row bgWhite">
		<div class="col-md-8 col-sm-8 col-xs-8 colDefined">
			<!-- 名称div -->
			<div class="name_div common_style fillWrite_group">
				<span class="print_textStyle ipadText">服用抗凝药物或其他可以导致凝血功能障碍的药物:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="isantifreezing" id="isAntiFreezing_n" value="0" type="radio" onclick="ishaveantifreezing(this.name)"/><label for="isAntiFreezing_n">无</label>
				<input name="isantifreezing" id="isAntiFreezing_y" value="1" type="radio" onclick="ishaveantifreezing(this.name)"/><label for="isAntiFreezing_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined backcolorGray">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="antifreezingtime" disabled style="cursor:not-allowed;" type="text" placeholder="时间"/><span>年</span>
			</div>
		</div>
		<%--<div class="col-md-3 col-sm-4 col-xs-4 colDefined backcolorGray" style="padding-left: 10px;">
			<!-- 填写组合框 -->
			<div class="selectgroup common_style">
				<span class="groupName ipadText" style="font-weight: normal;">常用药物：</span>
				<select id="antifreezingmedicine" class="groupSelect selectpicker" multiple title="请选择" style="pointer-events: none;">
					&lt;%&ndash;<option value="">请选择</option>&ndash;%&gt;
					<option value="阿司匹林">阿司匹林</option>
					<option value="波立维">波立维</option>
					<option value="华法林">华法林</option>
					<option value="肝素类">肝素类</option>
					<option value="氯吡格雷">氯吡格雷</option>
					<option value="达比加群">达比加群</option>
					<option value="利伐沙班">利伐沙班</option>
				</select>
			</div>
			&lt;%&ndash;打印展示&ndash;%&gt;
			<div class="medicineText">常用药物：<span class="antifreezingmedicine"></span></div>
		</div>--%>
	</div>
	<!-- 乙肝丙肝 -->
	<div class="row">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>乙肝:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="ishepatitisb" id="isHepatitisB_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="isHepatitisB_n">无</label>
				<input name="ishepatitisb" id="isHepatitisB_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="isHepatitisB_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="hepatitisb" disabled style="cursor:not-allowed;" type="text" placeholder="时间"/><span>年</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>丙肝:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="ishepatitisc" id="IsHepatitisC_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="IsHepatitisC_n">无</label>
				<input name="ishepatitisc" id="IsHepatitisC_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="IsHepatitisC_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="hepatitisc" disabled style="cursor:not-allowed;" type="text" placeholder="时间"/><span>年</span>
			</div>
		</div>
	</div>
	<!-- HIV 恶性肿瘤、 -->
	<div class="row bgWhite">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined backcolorGray">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>HIV:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined backcolorGray">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="ishiv" id="IsHIV_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="IsHIV_n">无</label>
				<input name="ishiv" id="IsHIV_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="IsHIV_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined backcolorGray">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="hiv" type="text" disabled style="cursor:not-allowed;" placeholder="时间"/><span>年</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined backcolorGray">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>恶性肿瘤（癌症）:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined backcolorGray">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="iscancer" id="IsCancer_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="IsCancer_n">无</label>
				<input name="iscancer" id="IsCancer_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="IsCancer_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined backcolorGray">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="cancer" type="text" disabled style="cursor:not-allowed;" placeholder="时间"/><span>年</span>
			</div>
		</div>
	</div>
	<!-- 梅毒、颌面部放疗术后 -->
	<div class="row">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>梅毒:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="isyphilis" id="isYphilis_n" value="0" type="radio" onclick="ishaveisyphilis(this.name)"/><label for="isYphilis_n">无</label>
				<input name="isyphilis" id="isYphilis_y" value="1" type="radio" onclick="ishaveisyphilis(this.name)"/><label for="isYphilis_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="syphilis" type="text" disabled style="cursor:not-allowed;" placeholder="时间"/><span>年</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>颌面部放疗术后:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="ismaxillofacial" id="IsMaxillofacial_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="IsMaxillofacial_n">无</label>
				<input name="ismaxillofacial" id="IsMaxillofacial_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="IsMaxillofacial_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="maxillofacial" type="text" disabled style="cursor:not-allowed;" placeholder="时间"/><span>年</span>
			</div>
		</div>
	</div>
	<!-- 任何感染的急性炎症期、吸毒 -->
	<div class="row bgWhite">
		<div class="col-md-2 col-sm-3 col-xs-3 colDefined backcolorGray">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>任何感染的急性炎症期:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined backcolorGray">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="isinflammation" id="isInflammation_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="isInflammation_n">无</label>
				<input name="isinflammation" id="isInflammation_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="isInflammation_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined backcolorGray">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="inflammation" type="text" disabled style="cursor:not-allowed;" placeholder="时间"/><span>年</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-1 col-xs-1 colDefined backcolorGray">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>吸毒:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined backcolorGray">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="isdrugabuse" id="isDrugAbuse_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="isDrugAbuse_n">无</label>
				<input name="isdrugabuse" id="isDrugAbuse_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="isDrugAbuse_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined backcolorGray">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="drugabuse" type="text" disabled style="cursor:not-allowed;" placeholder="时间"/><span>年</span>
			</div>
		</div>
	</div>
	<!-- 心理、精神障碍.皮肤黏膜病变、 -->
	<div class="row">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>心理、精神障碍:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="ispsychosis" id="isPsychosis_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="isPsychosis_n">无</label>
				<input name="ispsychosis" id="isPsychosis_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="isPsychosis_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="psychosis" type="text" disabled style="cursor:not-allowed;" placeholder="时间"/><span>年</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>皮肤黏膜病变:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="ismucousmembrane" id="IsMucousMembrane_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="IsMucousMembrane_n">无</label>
				<input name="ismucousmembrane" id="IsMucousMembrane_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="IsMucousMembrane_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="mucousmembrane" type="text" disabled style="cursor:not-allowed;" placeholder="时间"/><span>年</span>
			</div>
		</div>
	</div>
	<!-- 长期应用糖皮质激素 -->
	<div class="row">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>长期应用糖皮质激素:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="isglucocorticoids" id="IsGlucocorticoids_n" value="0" type="radio" onclick="ishaveillness(this.name);ishavaGlucocorticoids(this.name);"/><label for="IsGlucocorticoids_n">无</label>
				<input name="isglucocorticoids" id="IsGlucocorticoids_y" value="1" type="radio" onclick="ishaveillness(this.name);ishavaGlucocorticoids(this.name);"/><label for="IsGlucocorticoids_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="glucocorticoids" type="text" disabled style="cursor:not-allowed;" placeholder="时间"/><span>年</span>
			</div>
		</div>
		<%--怀孕--%>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>怀孕:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<div class="select_group common_style">
				<!-- 选项div -->
				<div class="option_div">
					<input name="ispregnancy" id="isPregnancy_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="isPregnancy_n">无</label>
					<input name="ispregnancy" id="isPregnancy_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="isPregnancy_y">有</label>
				</div>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="pregnancy" type="text" disabled style="cursor:not-allowed;" placeholder="时间"/><span>年</span>
			</div>
		</div>
		<%--<div class="col-md-6 col-sm-5 col-xs-5 colDefined combgGray">
			<!-- 填写组合框 -->
			<div class="selectgroup common_style">
				<span class="groupName" style="font-weight: normal;">常用药物：</span>
				<select id="glucocorticoidsmedicine" class="groupSelect selectpicker" multiple title="请选择" style="pointer-events: none;">
					&lt;%&ndash;<option value="">请选择</option>&ndash;%&gt;
					<option value="泼尼松">泼尼松</option>
					<option value="甲泼尼松">甲泼尼松</option>
					<option value="倍他米松">倍他米松</option>
					<option value="得宝松">得宝松</option>
					<option value="泼尼松龙">泼尼松龙</option>
				</select>
			</div>
			&lt;%&ndash;打印展示&ndash;%&gt;
			<div class="medicineText">常用药物：<span class="glucocorticoidsmedicine"></span></div>
		</div>--%>
	</div>
	<!-- 骨质疏松症用药情况： -->
	<div class="row bgWhite">
		<div class="col-md-3 col-sm-4 col-xs-4 colDefined backcolorGray">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>骨质疏松症用药情况（双磷酸盐类）:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined backcolorGray">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="ispharmacy" id="IsPharmacy_n" value="0" type="radio" onclick="ishaveillness(this.name);ishavapharmacy(this.name);"/><label for="IsPharmacy_n">无</label>
				<input name="ispharmacy" id="IsPharmacy_y" value="1" type="radio" onclick="ishaveillness(this.name);ishavapharmacy(this.name);"/><label for="IsPharmacy_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-3 col-xs-3 colDefined">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写名称 -->
				<span class="fillWrite_name" style="font-weight: normal;">注射:</span>
				<!-- 填写框-->
				<input id="pharmacy" class="fillWrite_input" type="text" disabled style="cursor:not-allowed;" placeholder="时间"/><span>年</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-3 col-xs-3 colDefined">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写名称 -->
				<span class="fillWrite_name" style="font-weight: normal;">口服:</span>
				<!-- 填写框-->
				<input id="takeorallytime" class="fillWrite_input" type="text" disabled style="cursor:not-allowed;" placeholder="时间"/><span>年</span>
			</div>
		</div>
		<%--<div class="col-md-3 col-sm-12 col-xs-12 colDefined">
			<!-- 填写组合框 -->
			<div class="selectgroup common_style">
				<span class="groupName" style="font-weight: normal;">常用药物：</span>
				<select id="pharmacymedicine" class="groupSelect selectpicker" multiple title="请选择" style="pointer-events: none;">
					&lt;%&ndash;<option value="">请选择</option>&ndash;%&gt;
					<option value="钙剂">钙剂</option>
					<option value="阿法骨化醇骨化三醇">阿法骨化醇骨化三醇</option>
					<option value="活性维生素D">活性维生素D</option>
					<option value="骨疏康">骨疏康</option>
					<option value="金天格">金天格</option>
					<option value="筋骨通">筋骨通</option>
					<option value="双磷酸盐类">双磷酸盐类</option>
					<option value="降钙素">降钙素</option>
					<option value="雌激素">雌激素</option>
				</select>
			</div>
			&lt;%&ndash;打印展示&ndash;%&gt;
			<div class="medicineText">常用药物：<span class="pharmacymedicine"></span></div>
		</div>--%>
	</div>
	<%--口干--%>
	<div class="row">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>口干:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="isdry" id="isdry_n" value="0" type="radio" onclick="ishaveillness(this.name);ishavaGlucocorticoids(this.name);"/><label for="isdry_n">无</label>
				<input name="isdry" id="isdry_y" value="1" type="radio" onclick="ishaveillness(this.name);ishavaGlucocorticoids(this.name);"/><label for="isdry_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 时间div -->
			<%--<div class="time_div common_style">
				<input id="dry" type="text" disabled style="cursor:not-allowed;" placeholder="时间"/>
			</div>--%>
		</div>
		<%--怀孕--%>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>最近一次拔牙:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<div class="select_group common_style">
				<!-- 选项div -->
				<div class="option_div">
					<input name="ispullouttooth" id="ispullouttooth_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="ispullouttooth_n">无</label>
					<input name="ispullouttooth" id="ispullouttooth_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="ispullouttooth_y">有</label>
				</div>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写框-->
				<input id="pullouttooth" class="fillWrite_input consent_time" type="text" disabled style="width:150px;cursor:not-allowed;" readonly="readonly" placeholder="请选择日期"/>
			</div>
		</div>
	</div>
	<!-- 其他系统疾病 -->
	<div class="row bgWhite">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>其他系统疾病:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="isotherdiseases" id="isOtherDiseasesString_n" value="0" type="radio" onclick="ishaveillness(this.name);ishaveOther(this.name);"/><label for="isOtherDiseasesString_n">无</label>
				<input name="isotherdiseases" id="isOtherDiseasesString_y" value="1" type="radio" onclick="ishaveillness(this.name);ishaveOther(this.name);"/><label for="isOtherDiseasesString_y">有</label>
			</div>
		</div>

		<div class="col-md-6 col-sm-7 col-xs-7 colDefined">
			<!-- 其他疾病输入框 -->
			<div class="time_div common_style">
				<input id="otherdiseasestext" style="width:100%;border:0px;border-bottom: 1px solid #c3c3c3;cursor:not-allowed;" type="text" disabled placeholder="其他系统疾病"/>
			</div>
		</div>
		<div class="col-md-2 col-sm-1 col-xs-1 colDefined">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input class="inputPrintWidth" id="otherdiseases" type="text" disabled style="cursor:not-allowed;" placeholder="时间"/><span>年</span>
			</div>
		</div>
	</div>
	<!-- 药物过敏 -->
	<div class="row">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>药物过敏:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<div class="select_group common_style">
				<!-- 选项div -->
				<div class="option_div">
					<input name="isdrugallergy" id="isDrugAllergy_n" value="0" type="radio" onclick="ishaveillness(this.name);ishavaDrugAllergy(this.name);"/><label for="isDrugAllergy_n">无</label>
					<input name="isdrugallergy" id="isDrugAllergy_y" value="1" type="radio" onclick="ishaveillness(this.name);ishavaDrugAllergy(this.name);"/><label for="isDrugAllergy_y">有</label>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-sm-7 col-xs-7 colDefined combgGray">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写名称 -->
				<!-- 填写框-->
				<input id="drugallergy" placeholder="请输入过敏药物" disabled onblur="TextLengthCheck(this.id,28);" class="fillWrite_input" style="width:100%;border:0px;border-bottom: 1px solid #c3c3c3;cursor:not-allowed;" type="text"/>
			</div>
		</div>
		<div class="col-md-2 col-sm-1 col-xs-1 colDefined combgGray">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input class="inputPrintWidth" id="allergiclength" type="text" disabled style="cursor:not-allowed;" placeholder="时间"/><span>年</span>
			</div>
		</div>
	</div>
	<!-- 正在服用的药物 -->
	<div class="row">
		<div class="col-md-10 col-sm-11 col-xs-11 colDefined combgGray">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写名称 -->
				<span class="fillWrite_name">正在服用的主要药物:</span>
				<!-- 填写框-->
				<input id="onmedication" placeholder="华法林，降压药，降糖药..." onblur="TextLengthCheck(this.id,40);" style="width: 87%;border:0px;border-bottom: 1px solid #c3c3c3;" class="fillWrite_input" type="text"/>
			</div>
		</div>
		<div class="col-md-2 col-sm-1 col-xs-1 colDefined">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input class="inputPrintWidth" id="onmedicationtime" type="text" placeholder="时间"/><span>年</span>
			</div>
		</div>
	</div>
	<div class="col-md-12 col-sm-12 col-xs-12 smallTitle" style="padding-left:0px;"><span class="circle"></span>生活习惯</div>
	<%--<div style="height: 100px;border:1px solid red;"></div>--%>
	<!-- 抽烟 -->
	<div class="row smoking">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写名称 -->
				<span class="fillWrite_name">吸烟:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<div class="select_group common_style">
				<!-- 选项div -->
				<div class="option_div">
					<input name="issmoke" id="isSmoke_n" value="0" type="radio" onclick="ishaveillness(this.name);isSmokeing(this.name);"/><label for="isSmoke_n">无</label>
					<input name="issmoke" id="isSmoke_y" value="1" type="radio" onclick="ishaveillness(this.name);isSmokeing(this.name);"/><label for="isSmoke_y">有</label>
				</div>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写框-->
				<input id="smoketime" class="fillWrite_input" disabled style="cursor:not-allowed;" type="text" placeholder="时间"/><span>年</span>
			</div>
		</div>
		<div class="col-md-6 col-sm-6 col-xs-6 colDefined">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写名称 -->
				<span class="fillWrite_name" style="font-weight: normal;">抽烟支数/日:</span>
				<!-- 填写框-->
				<input id="smokenum" class="fillWrite_input" type="text" disabled style="cursor:not-allowed;"/>
			</div>
		</div>
	</div>
	<!-- 饮酒 -->
	<div class="row bgWhite">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写名称 -->
				<span class="fillWrite_name">饮酒:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<div class="select_group common_style">
				<!-- 选项div -->
				<div class="option_div">
					<input name="isdrink" id="isDrink_n" value="0" type="radio" onclick="ishaveillness(this.name);isdrinking(this.name);"/><label for="isDrink_n">无</label>
					<input name="isdrink" id="isDrink_y" value="1" type="radio" onclick="ishaveillness(this.name);isdrinking(this.name);"/><label for="isDrink_y">有</label>
				</div>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写框-->
				<input id="drinktime" class="fillWrite_input" type="text" disabled style="cursor:not-allowed;" placeholder="时间"/><span>年</span>
			</div>
		</div>
		<div class="col-md-6 col-sm-6 col-xs-6 colDefined combgGray">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="drinkscale" id="drinkScale_little" disabled style="cursor:not-allowed;" value="少量" type="radio"/><label for="drinkScale_little">少量</label>
				<input name="drinkscale" id="drinkScale_center" disabled style="cursor:not-allowed;" value="中量" type="radio"/><label for="drinkScale_center">中量</label>
				<input name="drinkscale" id="drinkScale_big" disabled style="cursor:not-allowed;" value="大量" type="radio"/><label for="drinkScale_big">大量</label>
			</div>
		</div>
	</div>
	<!-- 磨牙、咀嚼习惯 -->
	<div class="row">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写名称 -->
				<span class="fillWrite_name">夜磨牙:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<div class="select_group common_style">
				<!-- 选项div -->
				<div class="option_div">
					<input name="isodontoprisis" id="isOdontoprisis_n" value="0" type="radio" onclick="ishaveillness(this.name);isOdontoprisis(this.name);"/><label for="isOdontoprisis_n">无</label>
					<input name="isodontoprisis" id="isOdontoprisis_y" value="1" type="radio" onclick="ishaveillness(this.name);isOdontoprisis(this.name);"/><label for="isOdontoprisis_y">有</label>
				</div>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写框-->
				<input id="odontoprisis" class="fillWrite_input" type="text" disabled style="cursor:not-allowed;" placeholder="时间"/><span>年</span>
			</div>
		</div>
		<div class="col-md-6 col-sm-6 col-xs-6 colDefined">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="odontoprisisdegree" id="odontoprisisDegree_more" disabled style="cursor:not-allowed;" value="1" type="radio"/><label for="odontoprisisDegree_more">频繁</label>
				<input name="odontoprisisdegree" id="odontoprisisDegree_few" disabled style="cursor:not-allowed;" value="2" type="radio"/><label for="odontoprisisDegree_few">偶尔</label>
				<input name="odontoprisisdegree" id="odontoprisisDegree_dimness" disabled style="cursor:not-allowed;" value="3" type="radio"/><label for="odontoprisisDegree_dimness">不清楚</label>
			</div>
		</div>
	</div>
	<%--咀嚼习惯--%>
	<div class="row bgWhite">
		<div class="col-md-1 col-sm-1 col-xs-1 colDefined combgGray">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span class="ipadText">咀嚼习惯:</span>
			</div>
		</div>
		<div class="col-md-3 col-sm-3 col-xs-3 colDefined combgGray">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="chewinghabits" id="chewingHabits_l" value="左" type="radio"/><label for="chewingHabits_l">左侧</label>
				<input name="chewinghabits" id="chewingHabits_r" value="右" type="radio"/><label for="chewingHabits_r">右侧</label>
				<input name="chewinghabits" id="chewingHabits_d" value="双侧" type="radio"/><label for="chewingHabits_d">双侧</label>
			</div>
		</div>
		<%--<div class="col-md-2 col-sm-1 col-xs-1 colDefined">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写框-->
				<input id="chewinghabitstime" class="fillWrite_input" type="text" placeholder="时间"/>
			</div>
		</div>--%>
		<div class="col-md-6 col-sm-7 col-xs-7 colDefined">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写名称 -->
				<span class="fillWrite_name otherText">其他习惯:</span>
				<!-- 填写框-->
				<input id="others" placeholder="在此输入..." onblur="TextLengthCheck(this.id,48);" style="width: 77%;border:0px;border-bottom: 1px solid #c3c3c3;" class="fillWrite_input" type="text" placeholder="请输入其他习惯"/>
			</div>
		</div>
	</div>
	<%--洁牙习惯--%>
	<div class="row">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写名称 -->
				<span class="fillWrite_name">洁牙习惯:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<div class="select_group common_style">
				<!-- 选项div -->
				<div class="option_div">
					<input name="isteethclean" id="isTeethclean_n" value="0" type="radio" onclick="ishaveillness(this.name);isteethclean(this.name);"/><label for="isTeethclean_n">无</label>
					<input name="isteethclean" id="isTeethclean_y" value="1" type="radio" onclick="ishaveillness(this.name);isteethclean(this.name);"/><label for="isTeethclean_y">有</label>
				</div>
			</div>
		</div>
		<div class="col-md-3 col-sm-3 col-xs-3 colDefined">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写框-->
				<span>频次</span>
				<input id="teethcleannum" class="fillWrite_input" type="text" disabled style="cursor:not-allowed;" placeholder="时间"/>
				<span>次/年</span>
			</div>
		</div>
		<div class="col-md-5 col-sm-5 col-xs-5 colDefined">
			<!-- 最近一次洁牙 -->
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写框-->
				<span>最近一次洁牙:</span>
				<input id="planttime" class="fillWrite_input consent_time" type="text" disabled style="width:150px;cursor:not-allowed;" readonly="readonly" placeholder="请选择日期"/>
			</div>
		</div>
	</div>
    <!-- 标题 -->
    <%--<div class="row">
        <div class="col-md-12 col-sm-12" style="position: relative;display: none;" id="secondLogoImg">
            <!-- <div class="big_title"><span class="bigtitle">主诉及既往病史</span></div> -->
            <img id="logoImg" src="<%=contextPath%>/static/image/kqdsFront/jiagong/logoName.png">
            <span class="bigtitle" style="visibility:hidden;">主诉及既往病史</span>
        </div>
    </div>--%>
	<!-- 其他 -->
	<%--<div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12 colDefined">
            <!-- 填写组合框 -->
            <div class="fillWrite_group common_style">
                <!-- 填写名称 -->
                <span class="fillWrite_name">其他习惯:</span>

                <!-- 填写框-->
                <input id="others" placeholder="在此输入..." onblur="TextLengthCheck(this.id,48);" style="width: 89%;border:0px;" class="fillWrite_input" type="text" placeholder="请输入其他习惯"/>
            </div>
        </div>
    </div>--%>
	<div class="row question_row" style="text-align: right;border-top: 1px solid black;">
		<p class="question_text" style="margin-top: 40px;font-size: 16px;"><font style="color:red;font-size: 18px;margin-right: 10px;">*</font>以上所述本人健康状况属实，对隐瞒病情所造成的不良后果责任自负。</p>
	</div>
	<!-- 手术签名 -->
	<div class="row signature_row">
		<!-- <div class="col-md-6 col-sm-6 col-xs-6">
        </div>
        <div class="col-md-6 col-sm-6 col-xs-6 colDefined">
            患者签名
            <div class="signature_time">
                <div class="signature_box">
                    <span>患者签名:</span>
                    <div id="PatientSignature"></div>
                </div>
                <input id="patienttime" type="text" class="consent_time" readonly="readonly" placeholder="请选择日期"/>
            </div>
        </div> -->
		<div class="col-md-6 col-sm-6 col-xs-6">
		</div>
		<div class="col-md-6 col-sm-6 col-xs-6">
			<!-- 患者签名 -->
			<div class="signature_time">
				<div class="signature_box">
					<span id="patientSignature" style="margin-top: 8px;">患者签名:</span>
					<img id="patientimg" style="width:156px;height:auto;"/>
				</div>
				<input id="patienttime" type="text" class="signatureTime" readonly="readonly" placeholder="请选择日期"/>
			</div>
		</div>
		<%--<div class="col-md-6 col-sm-6 col-xs-6">
		</div>
		<div class="col-md-6 col-sm-6 col-xs-6">
			<!-- 医生签名  -->
			<div class="signature_time">
				<span id="doctorSignature" style="line-height: 50px;">医生签名:</span>
				<img id="img" style="width:156px;height:auto;"/>
				<input id="doctortime" type="text" class="signatureTime" readonly="readonly" placeholder="请选择日期"/>
			</div>
		</div>--%>
	</div>

</div>

<!-- 按钮 -->
<div class="btns">
	<button id="consent_saveBtn" onclick="save()">保存</button>
	<button id="consent_updateBtn" style="display: none;" class="consent_updateBtn hidden" onclick="update()">修改表单</button>
	<button id="print_Btn" onclick="doPrint()">打印本页内容</button>
</div>
<!--endprint-->
</body>
<script language="javascript"  src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
<script type="text/javascript">
	var signature="";
	var patientsignature="";
	var doctorstatus=true;
	var patientstatus=true;
	var contextPath = "<%=contextPath%>";
	//var id= window.parent.consultSelectPatient.seqid;	//选中患者临床id
	//var order_number= window.parent.consultSelectPatient.orderNumber;//选中患者order_number
	var id;	//选中患者临床id
	var order_number;//选中患者order_number
	var caseId=""; //已存在的病历id
	var menuid=window.parent.menuid;//左侧菜单id
	var seqidFather = "<%=seqidFather%>";
	var usercode;
	$(function(){
		if(window.parent.consultSelectPatient){
			id= window.parent.consultSelectPatient.seqid;
			order_number= window.parent.consultSelectPatient.orderNumber;
			usercode = window.parent.consultSelectPatient.usercode;
		}else{
			id= window.parent.patientObj.id;;
			order_number= window.parent.patientObj.orderNumber;
			usercode = window.parent.patientObj.usercode;
		}
		//针对ipad样式
		var userAgent = navigator.userAgent;
		if (userAgent.indexOf("iPad") > -1){
			$("#print_Btn").css("display","none");
			$(".ipadText").css("font-size","14px");
			$("#onmedication").css("width","80%");
			$(".toothMapItem").css("margin-top","15px");
			$(".patientHeader").find("img").css("min-width","84px");
		}

		//时间选择
		$(".consent_time").datetimepicker({
			language:  'zh-CN',
			minView: 2,
			format: 'yyyy-mm-dd',
			autoclose : true,//选中之后自动隐藏日期选择框
			pickerPosition: "bottom-right",
			todayBtn: true,
			beforeShow: function () {
				setTimeout(
						function () {
							$('#ui-datepicker-div').css("z-index", 21);
						}, 100
				);
			}
		});
		$(".signatureTime").datetimepicker({
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
		$('.selectpicker').selectpicker({});//初始化种植体系下拉框

		/* 初始化患者基本信息 */
		initPatientInfo(usercode);
		initZzblInfor();/* 页面赋值判断初始化 */

		// 2019/7/24 lutian 禁止页面拖拽
		document.ondragstart = function() {
			return false;
		};


	});
	/* 初始化患者信息 */
	function initPatientInfo(usercode){
		var pageurl = '<%=contextPath%>/HUDH_FlowAct/findPatientInformation.act';
		$.ajax({
			type: "POST",
			url: pageurl,
			data: {
				usercode: usercode,
				status: 0
			},
			dataType: "json",
			success: function (r) {
				//console.log(JSON.stringify(r)+"----------查询患者信息");
				//$("#first_time").attr("value", r.cztime); //首诊时间
				$("#first_time").text(r.cztime); //首诊时间
				$("#patient_num").text(r.usercode);//患者编号
				$("#patient_name").text( r.username);//患者姓名
				$("#patient_sex").text( r.sex);//患者性别
				$("#patient_age").text( r.age);//患者年龄
				$("#patient_idnum").text( r.idcardno); //身份证号码
				$("#patient_birthday").text( r.birthday); //生日
				$("#patient_phone").text( r.phonenumber1); //电话
				$("#urgency_people").text( r.emergencyContact);  //紧急联系人
				$("#urgency_phone").text( r.emergencyPhone);  //紧急联系人电话
				$("#patient_address").text( r.provincename + r.cityname + r.townname + r.streetName); //地址
				userseqid=r.seq_id;
			}
		});
	}
	//更改牙位：弹出牙位图
	function showToothMap(i){
		layer.open({
			type: 2,
			title: "牙位图",
			shadeClose: false,
			shade: 0.6,
			area: ['800px', '450px'],
			content: contextPath +'/ZzblViewAct/toAnamnesisToothMap.act?index='+i
		});
	}

	/*var doctorSignature = document.getElementById("doctorSignature");
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
	}*/
	function addSignature(){
		$("#img").css("display","");
		$("#img").attr('src', signature);
	}
	var patientSignature = document.getElementById("patientSignature");
	patientSignature.onclick = function(){
		if(patientstatus){
			layer.open({
				type: 2,
				title: '签字',
				shadeClose: true,
				shade: 0.6,
				area: ['70%', '65%'],
				content: contextPath + '/SignatureAct/toSignature.act?category=患者'
			});
		}
	}
	function addPatientSignature(){
		$("#patientimg").css("display","");
		$("#patientimg").attr('src', patientsignature);
		if(!doctorstatus&&patientstatus){
			updatePatientSignature();
		}
	}
	//更新
	function updatePatientSignature(){
		var url = contextPath + '/HUDH_ZzblAdviceAct/updateCaseHistoryById.act';
		var patienttime = $("#patienttime").val();//患者签名时间
		var param = {
			SEQ_ID:  caseId, //临床路径ID
			patientsignature :  patientsignature,//患者签名
			patientTime : patienttime//患者签名时间

		};
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
	/* 2019/7/16 lutian input文字长度校验方法   obj：元素id  textNum：限制文字长度 */
	function TextLengthCheck(obj,textNum){
		var objTextVal=$("#"+obj).val();
		var checkTitleBefore=$("#"+obj).parent(".common_style").find("span").text();//根据父元素的选择器找到标题
		var checkTitle=checkTitleBefore.substring(0,checkTitleBefore.indexOf(":")); // 校验文字长长度的标题
		if(objTextVal.length>textNum){
			$("#"+obj).attr("maxlength",textNum);
			//layer.alert(checkTitle+"文字长度不能超过"+textNum+"字!");
			layer.open({
				title: '提示',
				content: checkTitle+'文字长度不能超过'+textNum+'字!',
				end:function(){
					var inputNewVal=$("#"+obj).val();
					$("#"+obj).val(inputNewVal.substring(0,textNum)).focus();
				}
			});
			return;
		}
	}

	/* 页面赋值初始化 */
	function initZzblInfor(){
		//console.log(id+"--------------查询id");
		var url = contextPath + '/HUDH_ZzblAdviceAct/findCaseHistoryBySeqid.act';
		$.ajax({
			url: url,
			type:"POST",
			dataType:"json",
			data : {
				seq_id :  seqidFather
			},
			success:function(result){
				console.log(JSON.stringify(result)+"--------------患者查询信息");
				if(result){
					if(result.seq_id){
						caseId=result.seq_id; //已存在的seqid
						$("#consent_saveBtn").css("display","none");//隐藏保存按钮
						$("#consent_updateBtn").css("display","inline-block");//显示修改按钮
					}
					for(var key in result){
						$("#"+key+"[type='text']").attr("value",result[key]);// 填框赋值
						//常用药物select赋值
						if($("#"+key).find("option")){
							$("#"+key).selectpicker('val', result[key]);
							$("."+key).text(result[key]);
							/*$("#"+key).find("option").each(function(i,obj){
                                $(this).removeAttr("selected");
                                if($(this).val()==result[key]){
                                    $(this).prop("selected", true);//针对ipad赋值
                                    $(this).attr("selected", true);
                                }
                            });*/
						}
						//单选按钮赋值
						$("input[name="+key+"][type='radio']").each(function(){
							if($(this).val()==result[key]){
								$(this).attr("checked","checked");
							}
						})
						//多选框赋值
						if(result[key]){
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
						}
					}
					//牙位图赋值
					toothMapInit("toothloseMap","leftUpTooth",result.toothlosemapupleft);  //牙缺失
					toothMapInit("toothloseMap","rightUpTooth",result.toothlosemapupright);  //牙缺失
					toothMapInit("toothloseMap","leftDownTooth",result.toothlosemapdownleft);  //牙缺失
					toothMapInit("toothloseMap","rightDownTooth",result.toothlosemapdownright);  //牙缺失
					toothMapInit("toothlessMap","leftUpTooth",result.toothlessmapupleft);  //牙松动
					toothMapInit("toothlessMap","rightUpTooth",result.toothlessmapupright);  //牙松动
					toothMapInit("toothlessMap","leftDownTooth",result.toothlessmapdownleft);  //牙松动
					toothMapInit("toothlessMap","rightDownTooth",result.toothlessmapdownright);  //牙松动
					toothMapInit("toothdecayedMap","leftUpTooth",result.toothdecayedmapupleft);  //龋齿
					toothMapInit("toothdecayedMap","rightUpTooth",result.toothdecayedmapupright);  //龋齿
					toothMapInit("toothdecayedMap","leftDownTooth",result.toothdecayedmapdownleft);  //龋齿
					toothMapInit("toothdecayedMap","rightDownTooth",result.toothdecayedmapdownright);  //龋齿
					toothMapInit("toothsnapMap","leftUpTooth",result.toothsnapmapupleft);  //牙折断
					toothMapInit("toothsnapMap","rightUpTooth",result.toothsnapmapupright);  //牙折断
					toothMapInit("toothsnapMap","leftDownTooth",result.toothsnapmapdownleft);  //牙折断
					toothMapInit("toothsnapMap","rightDownTooth",result.toothsnapmapdownright);  //牙折断
					signature=result.doctorsignature;
					if(signature!=""){
						$("#img").attr('src', signature);
						doctorstatus=false;
					}else{
						$("#img").attr('display', 'none');
					}
					patientsignature=result.patientsignature;
					if(patientsignature!=""){
						$("#patientimg").attr('src', patientsignature);
						patientstatus=false;
					}else{
						$("#patientimg").attr('display', 'none');
					}
				}
				//获取当前页面所有按钮
				getButtonAllCurPage(menuid);
			}
		});
	}
	//牙位图赋值
	function toothMapInit(fObj,sObj,toothStr){
		if(toothStr){
			var toothArr=toothStr.split(";");
			$("."+fObj).find("."+sObj).each(function(i,obj){
				for (var i = 0; i < toothArr.length; i++) {
					if($(this).find(".num").text()==toothArr[i]){
						$(this).addClass("current");
					}
				}
			});
		}
	}

	/* 牙齿症状选中 */
	function showSymptom() {
		var obj = document.getElementsByName("symptom");
		var symptom = "";
		for ( k in obj ) {
			if(obj[k].checked)
				symptom = symptom + obj[k].value + ';';
		}
		return symptom;
	}
	/* 糖尿病控制方式选中 */
	function showdietControl() {
		var obj = document.getElementsByName("dietcontrol");
		var dietControl = "";
		for ( k in obj ) {
			if(obj[k].checked)
				dietControl = dietControl + obj[k].value + ';';
		}
		return dietControl;
	}
	/* 选择做种植义齿是为了什么 */
	function showReasonsImplantDentures() {
		var obj = document.getElementsByName("reasonsimplantdentures");
		var ReasonsImplantDentures = "";
		for ( k in obj ) {
			if(obj[k].checked)
				ReasonsImplantDentures = ReasonsImplantDentures + obj[k].value + ';';
		}
		return ReasonsImplantDentures;
	}


	//修改
	function update(){
		var patient_num= $("#patient_num").text(); //患者编号
		//主诉
		var symptom=showSymptom();//牙齿症状选中
		var toothloseMapUpLeft = getToothNum("toothloseMap","leftUpTooth") //牙缺失左上牙位图
		var toothloseMapDownLeft = getToothNum("toothloseMap","leftDownTooth") //牙缺失左下牙位图
		var toothloseMapUpRight = getToothNum("toothloseMap","rightUpTooth") //牙缺失右上牙位图
		var toothloseMapDownRight = getToothNum("toothloseMap","rightDownTooth") //牙缺失右下牙位图
		var toothlessMapUpLeft = getToothNum("toothlessMap","leftUpTooth") //牙松动左上牙位图
		var toothlessMapDownLeft = getToothNum("toothlessMap","leftDownTooth") //牙松动左下牙位图
		var toothlessMapUpRight = getToothNum("toothlessMap","rightUpTooth") //牙松动右上牙位图
		var toothlessMapDownRight = getToothNum("toothlessMap","rightDownTooth") //牙松动右下牙位图
		var toothdecayedMapUpLeft = getToothNum("toothdecayedMap","leftUpTooth") //龋齿左上牙位图
		var toothdecayedMapDownLeft = getToothNum("toothdecayedMap","leftDownTooth") //龋齿左下牙位图
		var toothdecayedMapUpRight = getToothNum("toothdecayedMap","rightUpTooth") //龋齿右上牙位图
		var toothdecayedMapDownRight = getToothNum("toothdecayedMap","rightDownTooth") //龋齿右下牙位图
		var toothsnapMapUpLeft = getToothNum("toothsnapMap","leftUpTooth") //牙折断左上牙位图
		var toothsnapMapDownLeft = getToothNum("toothsnapMap","leftDownTooth") //牙折断左下牙位图
		var toothsnapMapUpRight = getToothNum("toothsnapMap","rightUpTooth") //牙折断右上牙位图
		var toothsnapMapDownRight = getToothNum("toothsnapMap","rightDownTooth") //牙折断右下牙位图
		var toothlosetime=$("#toothlosetime").val(); //牙缺失时间
		var toothlesstime=$("#toothlesstime").val(); //牙松动时间
		var toothdecayedtime=$("#toothdecayedtime").val(); //龋齿时间
		var toothsnaptime=$("#toothsnaptime").val(); //牙折断时间
		var implantRestoration=$("input[name='implantrestoration']:checked").val(); //是否要求种植修复
		//既往史
		var isHypertension = $('input[name="ishypertension"]:checked').val();//是否有高血压
		var hypertension = $("#hypertension").val();//患高血压年限
		var isTakeMedicie = $('input[name="istakemedicie"]:checked').val();//是否有在服药
		var pressure = $("#pressure").val();//当前血压
		//var hypertensionmedicine = $("#hypertensionmedicine").val();  //高血压常用药物
		var hypertensionmedicine = JSON.stringify($("#hypertensionmedicine").val());  //高血压常用药物
		var IsHearDiease = $('input[name="isheardiease"]:checked').val();//是否患有心脏病（心绞痛、心衰）
		var hearDiease = $("#heardiease").val();//患心脏病年限
		var isPrepareMedication = $('input[name="ispreparemedication"]:checked').val();//是否有长期备药在身边
		var heardieasemedicine = $("#heardieasemedicine").val();  //心脏病常用药
		var isDiabetes = $('input[name="isdiabetes"]:checked').val();//是否患有糖尿病
		var diabetes = $("#diabetes").val();//患糖尿病年限
		var dietControl=showdietControl();//控制饮食的方式
		var diabetesoralmedicine = $("#diabetesoralmedicine").val();  //糖尿病口服常用药
		var diabetesinjectionmedicine = $("#diabetesinjectionmedicine").val(); //糖尿病注射常用药
		var isheartoperation = $('input[name="isheartoperation"]:checked').val();//六个月内做过心脏手术
		var isheartinfarction = $('input[name="isheartinfarction"]:checked').val();//六个月内发生心梗
		var IsBloodCoagulation = $('input[name="isbloodcoagulation"]:checked').val();//凝血功能不足性疾病
		var bloodCoagulation = $("#bloodcoagulation").val();//患病年限
		var isAntiFreezing = $('input[name="isantifreezing"]:checked').val();//服用抗凝药物或其他可以导致凝血功能障碍的药物
		var antifreezingmedicine = $("#antifreezingmedicine").val();  //服用抗凝药物常用药物
		var antiFreezingTime = $("#antifreezingtime").val();//服药年限
		var isHepatitisB = $('input[name="ishepatitisb"]:checked').val();//是否患有乙肝
		var hepatitisB = $("#hepatitisb").val();//患病年限
		var IsHepatitisC = $('input[name="ishepatitisc"]:checked').val();//是否患有丙肝
		var hepatitisC = $("#hepatitisc").val();//患病年限
		var IsHIV = $('input[name="ishiv"]:checked').val();//是否患有HIV
		var Hiv = $("#hiv").val();//患病年限
		var IsCancer = $('input[name="iscancer"]:checked').val();//是否恶性肿瘤（癌症）
		var cancer = $("#cancer").val();//患病年限
		var isYphilis = $('input[name="isyphilis"]:checked').val();//是否患有梅毒
		var syphilis = $("#syphilis").val();//患病年限
		var IsMaxillofacial = $('input[name="ismaxillofacial"]:checked').val();//是否有过颌面部放疗术
		var maxillofacial = $("#maxillofacial").val();//间隔年限
		var isInflammation = $('input[name="isinflammation"]:checked').val();//是否患有感染急性炎症
		var inflammation = $("#inflammation").val();//患病年限
		var isDrugAbuse = $('input[name="isdrugabuse"]:checked').val();// 是否吸毒
		var drugAbuse = $("#drugabuse").val();//吸毒年限
		var isPsychosis = $('input[name="ispsychosis"]:checked').val();// 是否患有心理、精神障碍
		var psychosis = $("#psychosis").val();//患病年限
		var IsMucousMembrane = $('input[name="ismucousmembrane"]:checked').val();// 是否患有皮肤黏膜病变
		var mucousMembrane = $("#mucousmembrane").val();//患病年限
		var IsPharmacy = $('input[name="ispharmacy"]:checked').val();// 是否有骨质酥松用药
		var pharmacy = $("#pharmacy").val();// 注射用药时间
		var takeorallytime = $("#takeorallytime").val();// 口服用药时间
		var pharmacymedicine = $("#pharmacymedicine").val(); //骨质疏松常用药物
		var IsGlucocorticoids = $('input[name="isglucocorticoids"]:checked').val();// 长期应用糖皮质激素
		var glucocorticoids = $("#glucocorticoids").val();//使用激素年限
		var glucocorticoidsmedicine = $("#glucocorticoidsmedicine").val(); //长期应用糖皮质激素常用药
		var isOtherDiseases = $('input[name="isotherdiseases"]:checked').val();//是否患有其他疾病
		var otherdiseasestext = $("#otherdiseasestext").val();//其他疾病输入
		var otherDiseases = $("#otherdiseases").val();//患病年限
		var isDrugAllergy = $('input[name="isdrugallergy"]:checked').val();//是否药物过敏
		var allergicLength = $("#allergiclength").val();//过敏时间
		var drugallergy = $("#drugallergy").val();//输入过敏药物
		var isPregnancy = $('input[name="ispregnancy"]:checked').val();//是否怀孕
		var pregnancy = $("#pregnancy").val();//受孕时间 单位：月
		var onMedication = $("#onmedication").val();//正在服用药物
		var onmedicationtime = $("#onmedicationtime").val();//正在服用药物的时间
		//生活习惯
		var issmoke = $('input[name="issmoke"]:checked').val();//是否吸烟
		var smoketime = $("#smoketime").val();//抽烟时间
		var smokeNum = $("#smokenum").val();//抽烟数量    支/日
		var isdrink = $('input[name="isdrink"]:checked').val();//是否饮酒
		var drinkTime = $("#drinktime").val();//饮酒年限
		var drinkScale = $('input[name="drinkscale"]:checked').val();//饮酒量    1.少量 2.中量 3.大量
		var isodontoprisis = $('input[name="isodontoprisis"]:checked').val();//是否磨牙
		var odontoprisis = $("#odontoprisis").val();//磨牙时间
		var odontoprisisDegree = $('input[name="odontoprisisdegree"]:checked').val();//磨牙频率 1.频繁 2.偶尔
		var chewingHabits = $('input[name="chewinghabits"]:checked').val();//咀嚼习惯
		var chewinghabitstime = $("#chewinghabitstime").val();//咀嚼习惯时间
		var Others = $("#others").val();//其他习惯输入
		var isteethclean = $('input[name="isteethclean"]:checked').val();//洁牙习惯
		var teethcleannum = $("#teethcleannum").val();//频次
		var planttime = $("#planttime").val();//最近一次洁牙
		//签字
		/* var PatientSignature = $("#PatientSignature").val();//患者签字 */
		var PatientTime = $("#patienttime").val();//患者签字时间
		/* var doctorSignature = $("#doctorSignature").val(); *///医生签字
		var doctorTime = $("#doctortime").val();//医生签字时间     72
		//新增参数
		var isdry = $('input[name="isdry"]:checked').val();//口干
		var ispullouttooth = $('input[name="ispullouttooth"]:checked').val();//最近一次拔牙
		var pullouttooth = $("#pullouttooth").val();//最近一次拔牙时间

		var url = contextPath + '/HUDH_ZzblAdviceAct/updateCaseHistoryById.act';
		var param = {
			lcljId :  id,
			lcljNum :  order_number,
			SEQ_ID : caseId,
			patient_num :  patient_num,
			symptom :  symptom,
			toothloseMapUpLeft :  toothloseMapUpLeft,
			toothloseMapDownLeft :  toothloseMapDownLeft,
			toothloseMapUpRight :  toothloseMapUpRight,
			toothloseMapDownRight :  toothloseMapDownRight,
			toothlessMapUpLeft :  toothlessMapUpLeft,
			toothlessMapDownLeft :  toothlessMapDownLeft,
			toothlessMapUpRight :  toothlessMapUpRight,
			toothlessMapDownRight :  toothlessMapDownRight,
			toothdecayedMapUpLeft :  toothdecayedMapUpLeft,
			toothdecayedMapDownLeft :  toothdecayedMapDownLeft,
			toothdecayedMapUpRight :  toothdecayedMapUpRight,
			toothdecayedMapDownRight :  toothdecayedMapDownRight,
			toothsnapMapUpLeft :  toothsnapMapUpLeft,
			toothsnapMapDownLeft :  toothsnapMapDownLeft,
			toothsnapMapUpRight :  toothsnapMapUpRight,
			toothsnapMapDownRight :  toothsnapMapDownRight,
			toothlosetime :  toothlosetime,
			toothlesstime :  toothlesstime,
			toothdecayedtime :  toothdecayedtime,
			toothsnaptime :  toothsnaptime,
			implantRestoration : implantRestoration,
			isHypertension : isHypertension,
			hypertension : hypertension,
			isTakeMedicie : isTakeMedicie,
			pressure : pressure,
			hypertensionmedicine : hypertensionmedicine,
			isHearDiease : IsHearDiease,
			hearDiease : hearDiease,
			isPrepareMedication : isPrepareMedication,
			heardieasemedicine : heardieasemedicine,
			isDiabetes : isDiabetes,
			diabetes : diabetes,
			dietControl : dietControl,
			diabetesoralmedicine : diabetesoralmedicine,
			diabetesinjectionmedicine : diabetesinjectionmedicine,
			isheartoperation : isheartoperation,
			isheartinfarction : isheartinfarction,
			isBloodCoagulation : IsBloodCoagulation,
			bloodCoagulation : bloodCoagulation,
			isAntiFreezing : isAntiFreezing,
			antifreezingmedicine : antifreezingmedicine,
			antiFreezingTime : antiFreezingTime,
			isHepatitisB : isHepatitisB,
			hepatitisB : hepatitisB,
			isHepatitisC : IsHepatitisC,
			hepatitisC : hepatitisC,
			isHIV : IsHIV,
			hiv : Hiv,
			isCancer : IsCancer,
			cancer : cancer,
			isYphilis : isYphilis,
			syphilis : syphilis,
			isMaxillofacial : IsMaxillofacial,
			maxillofacial : maxillofacial,
			isInflammation : isInflammation,
			inflammation : inflammation,
			isDrugAbuse : isDrugAbuse,
			drugAbuse : drugAbuse,
			isPsychosis : isPsychosis,
			psychosis : psychosis,
			isMucousMembrane : IsMucousMembrane,
			mucousMembrane : mucousMembrane,
			isPharmacy : IsPharmacy,
			pharmacy : pharmacy,
			takeorallytime : takeorallytime,
			pharmacymedicine : pharmacymedicine,
			isGlucocorticoids : IsGlucocorticoids,
			glucocorticoids : glucocorticoids,
			glucocorticoidsmedicine : glucocorticoidsmedicine,
			isOtherDiseases : isOtherDiseases,
			otherdiseasestext : otherdiseasestext,
			otherDiseases : otherDiseases,
			isDrugAllergy : isDrugAllergy,
			allergicLength : allergicLength,
			drugallergy : drugallergy,
			isPregnancy : isPregnancy,
			pregnancy : pregnancy,
			onMedication : onMedication,
			onmedicationtime : onmedicationtime,
			issmoke : issmoke,
			smoketime : smoketime,
			smokeNum : smokeNum,
			isdrink : isdrink,
			drinkTime : drinkTime,
			drinkScale : drinkScale,
			isodontoprisis : isodontoprisis,
			odontoprisis : odontoprisis,
			odontoprisisDegree : odontoprisisDegree,
			chewingHabits : chewingHabits,
			chewinghabitstime : chewinghabitstime,
			others : Others,
			isteethclean : isteethclean,
			teethcleannum : teethcleannum,
			planttime : planttime,
			patientTime : PatientTime,
			doctorTime : doctorTime,
			patientsignature : patientsignature,
			doctorsignature : signature,
			//新增参数
			isdry : isdry,
			ispullouttooth : ispullouttooth,
			pullouttooth : pullouttooth
		};
		//console.log(JSON.stringify(param)+"------------修改参数");
		$.axseSubmit(url, param,function() {},function(r) {
			layer.alert("修改成功！", {
				end: function() {
					//window.parent.location.reload(); //刷新父页面
					//保存成功父页面选中该项
					//window.parent.document.getElementById("ask_Previous").prev().attr("checked","checked");
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

		var patient_num= $("#patient_num").text(); //患者编号
		//主诉
		var symptom=showSymptom();//牙齿症状选中
		var toothloseMapUpLeft = getToothNum("toothloseMap","leftUpTooth") //牙缺失左上牙位图
		var toothloseMapDownLeft = getToothNum("toothloseMap","leftDownTooth") //牙缺失左下牙位图
		var toothloseMapUpRight = getToothNum("toothloseMap","rightUpTooth") //牙缺失右上牙位图
		var toothloseMapDownRight = getToothNum("toothloseMap","rightDownTooth") //牙缺失右下牙位图
		var toothlessMapUpLeft = getToothNum("toothlessMap","leftUpTooth") //牙松动左上牙位图
		var toothlessMapDownLeft = getToothNum("toothlessMap","leftDownTooth") //牙松动左下牙位图
		var toothlessMapUpRight = getToothNum("toothlessMap","rightUpTooth") //牙松动右上牙位图
		var toothlessMapDownRight = getToothNum("toothlessMap","rightDownTooth") //牙松动右下牙位图
		var toothdecayedMapUpLeft = getToothNum("toothdecayedMap","leftUpTooth") //龋齿左上牙位图
		var toothdecayedMapDownLeft = getToothNum("toothdecayedMap","leftDownTooth") //龋齿左下牙位图
		var toothdecayedMapUpRight = getToothNum("toothdecayedMap","rightUpTooth") //龋齿右上牙位图
		var toothdecayedMapDownRight = getToothNum("toothdecayedMap","rightDownTooth") //龋齿右下牙位图
		var toothsnapMapUpLeft = getToothNum("toothsnapMap","leftUpTooth") //牙折断左上牙位图
		var toothsnapMapDownLeft = getToothNum("toothsnapMap","leftDownTooth") //牙折断左下牙位图
		var toothsnapMapUpRight = getToothNum("toothsnapMap","rightUpTooth") //牙折断右上牙位图
		var toothsnapMapDownRight = getToothNum("toothsnapMap","rightDownTooth") //牙折断右下牙位图
		var toothlosetime=$("#toothlosetime").val(); //牙缺失时间
		var toothlesstime=$("#toothlesstime").val(); //牙松动时间
		var toothdecayedtime=$("#toothdecayedtime").val(); //龋齿时间
		var toothsnaptime=$("#toothsnaptime").val(); //牙折断时间
		var implantRestoration=$("input[name='implantrestoration']:checked").val(); //是否要求种植修复
		//既往史
		var isHypertension = $('input[name="ishypertension"]:checked').val();//是否有高血压
		var hypertension = $("#hypertension").val();//患高血压年限
		var isTakeMedicie = $('input[name="istakemedicie"]:checked').val();//是否有在服药
		var pressure = $("#pressure").val();//当前血压
		var hypertensionmedicine = $("#hypertensionmedicine").val();  //高血压常用药物
		var IsHearDiease = $('input[name="isheardiease"]:checked').val();//是否患有心脏病（心绞痛、心衰）
		var hearDiease = $("#heardiease").val();//患心脏病年限
		var isPrepareMedication = $('input[name="ispreparemedication"]:checked').val();//是否有长期备药在身边
		var heardieasemedicine = $("#heardieasemedicine").val();  //心脏病常用药
		var isDiabetes = $('input[name="isdiabetes"]:checked').val();//是否患有糖尿病
		var diabetes = $("#diabetes").val();//患糖尿病年限
		var dietControl=showdietControl();//控制饮食的方式
		var diabetesoralmedicine = $("#diabetesoralmedicine").val();  //糖尿病口服常用药
		var diabetesinjectionmedicine = $("#diabetesinjectionmedicine").val(); //糖尿病注射常用药
		var isheartoperation = $('input[name="isheartoperation"]:checked').val();//六个月内做过心脏手术
		var isheartinfarction = $('input[name="isheartinfarction"]:checked').val();//六个月内发生心梗
		var IsBloodCoagulation = $('input[name="isbloodcoagulation"]:checked').val();//凝血功能不足性疾病
		var bloodCoagulation = $("#bloodcoagulation").val();//患病年限
		var isAntiFreezing = $('input[name="isantifreezing"]:checked').val();//服用抗凝药物或其他可以导致凝血功能障碍的药物
		var antifreezingmedicine = $("#antifreezingmedicine").val();  //服用抗凝药物常用药物
		var antiFreezingTime = $("#antifreezingtime").val();//服药年限
		var isHepatitisB = $('input[name="ishepatitisb"]:checked').val();//是否患有乙肝
		var hepatitisB = $("#hepatitisb").val();//患病年限
		var IsHepatitisC = $('input[name="ishepatitisc"]:checked').val();//是否患有丙肝
		var hepatitisC = $("#hepatitisc").val();//患病年限
		var IsHIV = $('input[name="ishiv"]:checked').val();//是否患有HIV
		var Hiv = $("#hiv").val();//患病年限
		var IsCancer = $('input[name="iscancer"]:checked').val();//是否恶性肿瘤（癌症）
		var cancer = $("#cancer").val();//患病年限
		var isYphilis = $('input[name="isyphilis"]:checked').val();//是否患有梅毒
		var syphilis = $("#syphilis").val();//患病年限
		var IsMaxillofacial = $('input[name="ismaxillofacial"]:checked').val();//是否有过颌面部放疗术
		var maxillofacial = $("#maxillofacial").val();//间隔年限
		var isInflammation = $('input[name="isinflammation"]:checked').val();//是否患有感染急性炎症
		var inflammation = $("#inflammation").val();//患病年限
		var isDrugAbuse = $('input[name="isdrugabuse"]:checked').val();// 是否吸毒
		var drugAbuse = $("#drugabuse").val();//吸毒年限
		var isPsychosis = $('input[name="ispsychosis"]:checked').val();// 是否患有心理、精神障碍
		var psychosis = $("#psychosis").val();//患病年限
		var IsMucousMembrane = $('input[name="ismucousmembrane"]:checked').val();// 是否患有皮肤黏膜病变
		var mucousMembrane = $("#mucousmembrane").val();//患病年限
		var IsPharmacy = $('input[name="ispharmacy"]:checked').val();// 是否有骨质酥松用药
		var pharmacy = $("#pharmacy").val();// 注射用药时间
		var takeorallytime = $("#takeorallytime").val();// 口服用药时间
		var pharmacymedicine = $("#pharmacymedicine").val(); //骨质疏松常用药物
		var IsGlucocorticoids = $('input[name="isglucocorticoids"]:checked').val();// 长期应用糖皮质激素
		var glucocorticoids = $("#glucocorticoids").val();//使用激素年限
		var glucocorticoidsmedicine = $("#glucocorticoidsmedicine").val(); //长期应用糖皮质激素常用药
		var isOtherDiseases = $('input[name="isotherdiseases"]:checked').val();//是否患有其他疾病
		var otherdiseasestext = $("#otherdiseasestext").val();//其他疾病输入
		var otherDiseases = $("#otherdiseases").val();//患病年限
		var isDrugAllergy = $('input[name="isdrugallergy"]:checked').val();//是否药物过敏
		var allergicLength = $("#allergiclength").val();//过敏时间
		var drugallergy = $("#drugallergy").val();//输入过敏药物
		var isPregnancy = $('input[name="ispregnancy"]:checked').val();//是否怀孕
		var pregnancy = $("#pregnancy").val();//受孕时间 单位：月
		var onMedication = $("#onmedication").val();//正在服用药物
		var onmedicationtime = $("#onmedicationtime").val();//正在服用药物的时间
		//生活习惯
		var issmoke = $('input[name="issmoke"]:checked').val();//是否吸烟
		var smoketime = $("#smoketime").val();//抽烟时间
		var smokeNum = $("#smokenum").val();//抽烟数量    支/日
		var isdrink = $('input[name="isdrink"]:checked').val();//是否饮酒
		var drinkTime = $("#drinktime").val();//饮酒年限
		var drinkScale = $('input[name="drinkscale"]:checked').val();//饮酒量    1.少量 2.中量 3.大量
		var isodontoprisis = $('input[name="isodontoprisis"]:checked').val();//是否磨牙
		var odontoprisis = $("#odontoprisis").val();//磨牙时间
		var odontoprisisDegree = $('input[name="odontoprisisdegree"]:checked').val();//磨牙频率 1.频繁 2.偶尔
		var chewingHabits = $('input[name="chewinghabits"]:checked').val();//咀嚼习惯
		var chewinghabitstime = $("#chewinghabitstime").val();//咀嚼习惯时间
		var Others = $("#others").val();//其他习惯输入
		var isteethclean = $('input[name="isteethclean"]:checked').val();//洁牙习惯
		var teethcleannum = $("#teethcleannum").val();//频次
		var planttime = $("#planttime").val();//最近一次洁牙
		//签字
		/* var PatientSignature = $("#PatientSignature").val();//患者签字 */
		var PatientTime = $("#patienttime").val();//患者签字时间
		/* var doctorSignature = $("#doctorSignature").val(); *///医生签字
		var doctorTime = $("#doctortime").val();//医生签字时间     72

		//新增参数
		var isdry = $('input[name="isdry"]:checked').val();//口干
		var ispullouttooth = $('input[name="ispullouttooth"]:checked').val();//最近一次拔牙
		var pullouttooth = $("#pullouttooth").val();//最近一次拔牙时间

		var url = contextPath + '/HUDH_ZzblAdviceAct/saveCaseHistory.act';
		var param = {
			lcljId :  id,
			lcljNum :  order_number,
			patient_num :  patient_num,
			symptom :  symptom,
			toothloseMapUpLeft :  toothloseMapUpLeft,
			toothloseMapDownLeft :  toothloseMapDownLeft,
			toothloseMapUpRight :  toothloseMapUpRight,
			toothloseMapDownRight :  toothloseMapDownRight,
			toothlessMapUpLeft :  toothlessMapUpLeft,
			toothlessMapDownLeft :  toothlessMapDownLeft,
			toothlessMapUpRight :  toothlessMapUpRight,
			toothlessMapDownRight :  toothlessMapDownRight,
			toothdecayedMapUpLeft :  toothdecayedMapUpLeft,
			toothdecayedMapDownLeft :  toothdecayedMapDownLeft,
			toothdecayedMapUpRight :  toothdecayedMapUpRight,
			toothdecayedMapDownRight :  toothdecayedMapDownRight,
			toothsnapMapUpLeft :  toothsnapMapUpLeft,
			toothsnapMapDownLeft :  toothsnapMapDownLeft,
			toothsnapMapUpRight :  toothsnapMapUpRight,
			toothsnapMapDownRight :  toothsnapMapDownRight,
			toothlosetime :  toothlosetime,
			toothlesstime :  toothlesstime,
			toothdecayedtime :  toothdecayedtime,
			toothsnaptime :  toothsnaptime,
			implantRestoration : implantRestoration,
			isHypertension : isHypertension,
			hypertension : hypertension,
			isTakeMedicie : isTakeMedicie,
			pressure : pressure,
			hypertensionmedicine : hypertensionmedicine,
			isHearDiease : IsHearDiease,
			hearDiease : hearDiease,
			isPrepareMedication : isPrepareMedication,
			heardieasemedicine : heardieasemedicine,
			isDiabetes : isDiabetes,
			diabetes : diabetes,
			dietControl : dietControl,
			diabetesoralmedicine : diabetesoralmedicine,
			diabetesinjectionmedicine : diabetesinjectionmedicine,
			isheartoperation : isheartoperation,
			isheartinfarction : isheartinfarction,
			isBloodCoagulation : IsBloodCoagulation,
			bloodCoagulation : bloodCoagulation,
			isAntiFreezing : isAntiFreezing,
			antifreezingmedicine : antifreezingmedicine,
			antiFreezingTime : antiFreezingTime,
			isHepatitisB : isHepatitisB,
			hepatitisB : hepatitisB,
			isHepatitisC : IsHepatitisC,
			hepatitisC : hepatitisC,
			isHIV : IsHIV,
			hiv : Hiv,
			isCancer : IsCancer,
			cancer : cancer,
			isYphilis : isYphilis,
			syphilis : syphilis,
			isMaxillofacial : IsMaxillofacial,
			maxillofacial : maxillofacial,
			isInflammation : isInflammation,
			inflammation : inflammation,
			isDrugAbuse : isDrugAbuse,
			drugAbuse : drugAbuse,
			isPsychosis : isPsychosis,
			psychosis : psychosis,
			isMucousMembrane : IsMucousMembrane,
			mucousMembrane : mucousMembrane,
			isPharmacy : IsPharmacy,
			pharmacy : pharmacy,
			takeorallytime : takeorallytime,
			pharmacymedicine : pharmacymedicine,
			isGlucocorticoids : IsGlucocorticoids,
			glucocorticoids : glucocorticoids,
			glucocorticoidsmedicine : glucocorticoidsmedicine,
			isOtherDiseases : isOtherDiseases,
			otherdiseasestext : otherdiseasestext,
			otherDiseases : otherDiseases,
			isDrugAllergy : isDrugAllergy,
			allergicLength : allergicLength,
			drugallergy : drugallergy,
			isPregnancy : isPregnancy,
			pregnancy : pregnancy,
			onMedication : onMedication,
			onmedicationtime : onmedicationtime,
			issmoke : issmoke,
			smoketime : smoketime,
			smokeNum : smokeNum,
			isdrink : isdrink,
			drinkTime : drinkTime,
			drinkScale : drinkScale,
			isodontoprisis : isodontoprisis,
			odontoprisis : odontoprisis,
			odontoprisisDegree : odontoprisisDegree,
			chewingHabits : chewingHabits,
			chewinghabitstime : chewinghabitstime,
			others : Others,
			isteethclean : isteethclean,
			teethcleannum : teethcleannum,
			planttime : planttime,
			patientTime : PatientTime,
			doctorTime : doctorTime,
			patientsignature : patientsignature,
			doctorsignature : signature,
			//新增参数
			isdry : isdry,
			ispullouttooth : ispullouttooth,
			pullouttooth : pullouttooth
		};
		//console.log(JSON.stringify(param)+"---------保存参数");
		$.axseSubmit(url, param,function() {},function(r) {
			layer.alert("保存成功！", {
				end: function() {
					//window.parent.location.reload(); //刷新父页面
					//保存成功父页面选中该项
					//window.parent.document.getElementById("ask_Previous").prev().attr("checked","checked");
					var frameindex = parent.layer.getFrameIndex(window.name);
					parent.layer.close(frameindex); //再执行关闭
				}
			});
		},function(r){
			layer.alert("保存失败！");
		});
	}

	/*获得左上牙位图 obj:牙位图div类名 toothClass:牙位父级类名*/
	function getToothNum(obj,toothClass){
		var ToothArr=[];
		$("."+obj).find("."+toothClass).each(function(i,obj){
			if($(this).hasClass("current")){
				ToothArr.push($(this).find(".num").text());
			}
		});
		var TootharrStr=ToothArr.join(";");
		return TootharrStr;
	}

	/* 疾病判断并验证:根据选项框name截取得到时间id并禁用 例:选项框-ishypertension 输入框-hypertension */
	function ishaveillness(objName){
		var inputTextid=objName.substring(2);
		if($("input[name="+objName+"]:checked").val()==0){
			$("#"+inputTextid).val("").attr("disabled","disabled").css("background-color","#c3c3c3");
		}else if($("input[name="+objName+"]:checked").val()==1){
			$("#"+inputTextid).removeAttr("disabled").css("background-color","transparent").css("cursor","auto");
		}
	}

	/* 服用抗凝药物单独验证   名字不对应 */
	function ishaveantifreezing(objName){
		var inputTextid=objName.substring(2);
		if($("input[name="+objName+"]:checked").val()==0){
			//$("#"+inputTextid).val("").attr("disabled","disabled").css("background-color","#c3c3c3");  //原输入框
			$("#"+inputTextid+"time").val("").attr("disabled","disabled").css("background-color","#c3c3c3"); //患病年限
			//$("#antifreezingmedicine").val("").attr("disabled","disabled").css("background-color","#c3c3c3").css("pointer-events","none"); //常用药物
		}else if($("input[name="+objName+"]:checked").val()==1){
			//$("#"+inputTextid).removeAttr("disabled").css("background-color","transparent").css("cursor","auto"); //原输入框
			$("#"+inputTextid+"time").removeAttr("disabled").css("background-color","transparent").css("cursor","auto"); //患病年限
			//$("#antifreezingmedicine").removeAttr("disabled").css("background-color","transparent").css("cursor","auto").css("pointer-events","auto"); //常用药物
		}
	}
	/* 梅毒单独验证   名字不对应 */
	function ishaveisyphilis(objName){
		var inputTextid=objName.substring(1);
		if($("input[name="+objName+"]:checked").val()==0){
			$("#"+inputTextid).val("").attr("disabled","disabled").css("background-color","#c3c3c3");
		}else if($("input[name="+objName+"]:checked").val()==1){
			$("#"+inputTextid).removeAttr("disabled").css("background-color","transparent").css("cursor","auto");
		}
	}
	/* 骨质疏松单独验证，因为多了一个用药方式 */
	function ishavapharmacy(objName){
		if($("input[name="+objName+"]:checked").val()==0){
			$("#takeorallytime").val("").attr("disabled","disabled").css("background-color","#c3c3c3"); //静脉或者口服
			//$("#pharmacymedicine").val("").attr("disabled","disabled").css("pointer-events","none"); //常用药物
		}else if($("input[name="+objName+"]:checked").val()==1){
			$("#takeorallytime").removeAttr("disabled").css("cursor","auto").css("background-color","transparent"); //静脉或者口服
			//$("#pharmacymedicine").removeAttr("disabled").css("cursor","auto").css("pointer-events","auto"); //常用药物
		}
	}
	/* 高血压单独验证，因为多了是否服药是否控制目前血压 */
	function ishavahypertension(objName){
		if($("input[name="+objName+"]:checked").val()==0){
			$("#hypertension").removeAttr("checked").attr("disabled","disabled");  //患病年限
			$("input[name='istakemedicie']").removeAttr("checked").attr("disabled","disabled");  //是否服药
			$("input[name='iscontrol']").removeAttr("checked").attr("disabled","disabled");      //是否控制
			$("#pressure").val("").attr("disabled","disabled").css("background-color","#c3c3c3");  //目前血压
			$("hypertensionmedicineDiv").css("display","none");
			$(".hypertensionmedicine").find(".groupSelect").css("margin-top","-3px");
			//$("#hypertensionmedicine").val("").attr("disabled","disabled").css("background-color","#c3c3c3").css("pointer-events","none");  //常用药物
		}else if($("input[name="+objName+"]:checked").val()==1){
			$("#hypertension").removeAttr("disabled").css("cursor","auto");
			$("input[name='istakemedicie']").removeAttr("disabled").css("cursor","auto");
			$("input[name='iscontrol']").removeAttr("disabled").css("cursor","auto");
			$("#pressure").removeAttr("disabled").css("background-color","transparent").css("cursor","auto");
			$("hypertensionmedicineDiv").css("display","block");
			//$("#hypertensionmedicine").removeAttr("disabled").css("background-color","transparent").css("cursor","auto").css("pointer-events","auto");
		}
	}
	/* 心脏病 心绞痛、心衰，因为多了一个是否常备药 */
	function ishavaheardiease(objName){
		if($("input[name="+objName+"]:checked").val()==0){
			$("#heardiease").removeAttr("checked").attr("disabled","disabled"); //患病年限
			$("input[name='ispreparemedication']").removeAttr("checked").attr("disabled","disabled"); //是否有常备药
			//$("#heardieasemedicine").val("").attr("disabled","disabled").css("pointer-events","none"); //常用药物
		}else if($("input[name="+objName+"]:checked").val()==1){
			$("#heardiease").removeAttr("disabled").css("cursor","auto");
			$("input[name='ispreparemedication']").removeAttr("disabled").css("cursor","auto");
			//$("#heardieasemedicine").removeAttr("disabled").css("cursor","auto").css("pointer-events","auto");
		}
	}
	/* 糖尿病，因为多了一个是否控制怎么控制 */
	function ishavadiabetes(objName){
		if($("input[name="+objName+"]:checked").val()==0){
			$("#diabetes").removeAttr("checked").attr("disabled","disabled");   //患病年限
			$("input[name='dietcontrol']").removeAttr("checked").attr("disabled","disabled");   //怎么控制
			$("input[name='isdietcontrol']").removeAttr("checked").attr("disabled","disabled");  //是否控制
			//$("#diabetesoralmedicine").val("").attr("disabled","disabled").css("pointer-events","none");  //口服常用药
			//$("#diabetesinjectionmedicine").val("").attr("disabled","disabled").css("pointer-events","none");  //注射常用药
		}else if($("input[name="+objName+"]:checked").val()==1){
			$("#diabetes").removeAttr("disabled").css("cursor","auto");
			$("input[name='dietcontrol']").removeAttr("disabled").css("cursor","auto");
			$("input[name='isdietcontrol']").removeAttr("disabled").css("cursor","auto");
			//$("#diabetesoralmedicine").removeAttr("disabled").css("cursor","auto").css("pointer-events","auto");
			//$("#diabetesinjectionmedicine").removeAttr("disabled").css("cursor","auto").css("pointer-events","auto");
		}
	}
	/* 长期应用糖皮质激素验证*/
	function ishavaGlucocorticoids(objName){
		if($("input[name="+objName+"]:checked").val()==0){
			//$("#glucocorticoidsmedicine").val("").attr("disabled","disabled").css("background-color","#c3c3c3").css("pointer-events","none"); //常用药物
		}else if($("input[name="+objName+"]:checked").val()==1){
			//$("#glucocorticoidsmedicine").removeAttr("disabled").css("background-color","transparent").css("cursor","auto").css("pointer-events","auto"); //常用药物
		}
	}
	/* 药物过敏单独验证，因为多了过敏时间 */
	function ishavaDrugAllergy(objName){
		if($("input[name="+objName+"]:checked").val()==0){
			$("#allergiclength").val("").removeAttr("checked").attr("disabled","disabled").css("background-color","#c3c3c3");
		}else if($("input[name="+objName+"]:checked").val()==1){
			$("#allergiclength").removeAttr("disabled").css("background-color","transparent").css("cursor","auto");
		}
	}
	/*其他系统疾病单独验证：疾病输入框*/
	function ishaveOther(objName){
		if($("input[name="+objName+"]:checked").val()==0){
			$("#otherdiseasestext").val("").attr("disabled","disabled").css("background-color","#c3c3c3").css("pointer-events","none");
		}else if($("input[name="+objName+"]:checked").val()==1){
			$("#otherdiseasestext").removeAttr("disabled").css("background-color","transparent").css("cursor","auto").css("pointer-events","auto");
		}
	}
	/*吸烟单独验证*/
	function isSmokeing(objName){
		if($("input[name="+objName+"]:checked").val()==0){
			$("#smoketime").val("").attr("disabled","disabled").css("background-color","#c3c3c3").css("pointer-events","none"); //抽烟时间
			$("#smokenum").val("").attr("disabled","disabled").css("background-color","#c3c3c3").css("pointer-events","none"); //抽烟支数
		}else if($("input[name="+objName+"]:checked").val()==1){
			$("#smoketime").removeAttr("disabled").css("background-color","transparent").css("cursor","auto").css("pointer-events","auto"); //抽烟时间
			$("#smokenum").removeAttr("disabled").css("background-color","transparent").css("cursor","auto").css("pointer-events","auto"); //抽烟支数
		}
	}
	/*饮酒单独验证*/
	function isdrinking(objName){
		if($("input[name="+objName+"]:checked").val()==0){
			$("input[name='drinkscale']").removeAttr("checked").attr("disabled","disabled");
			$("#drinktime").val("").attr("disabled","disabled").css("background-color","#c3c3c3").css("pointer-events","none");
		}else if($("input[name="+objName+"]:checked").val()==1){
			$("input[name='drinkscale']").removeAttr("disabled").css("cursor","auto");
			$("#drinktime").removeAttr("disabled").css("background-color","transparent").css("cursor","auto").css("pointer-events","auto");
		}
	}
	/*磨牙单独验证*/
	function isOdontoprisis(objName){
		if($("input[name="+objName+"]:checked").val()==0){
			$("input[name='odontoprisisdegree']").removeAttr("checked").attr("disabled","disabled");
		}else if($("input[name="+objName+"]:checked").val()==1){
			$("input[name='odontoprisisdegree']").removeAttr("disabled").css("cursor","auto");
		}
	}
	/*洁牙习惯单独验证*/
	function isteethclean(objName){
		if($("input[name="+objName+"]:checked").val()==0){
			$("#teethcleannum").val("").attr("disabled","disabled").css("background-color","#c3c3c3").css("pointer-events","none");
			$("#planttime").val("").attr("disabled","disabled").css("background-color","#c3c3c3").css("pointer-events","none");
		}else if($("input[name="+objName+"]:checked").val()==1){
			$("#teethcleannum").removeAttr("disabled").css("background-color","transparent").css("cursor","auto").css("pointer-events","auto");
			$("#planttime").removeAttr("disabled").css("background-color","transparent").css("cursor","auto").css("pointer-events","auto");
		}
	}
	/*主诉牙齿情况验证*/
	function isToothChecked(id){
		var checkVal=$("#"+id).val();
		if($("#"+id).is(':checked')){
			$("#"+id).parents(".toothMapItem").find(".toothMap").attr("onclick","showToothMap("+checkVal+")");
			$("#"+id).parents(".toothMapItem").find(".toothTime").find("input").removeAttr("disabled").css("background-color","transparent").css("cursor","auto").css("pointer-events","auto");
		}else{
			$("#"+id).parents(".toothMapItem").find(".toothMap").removeAttr("onclick");
			$("#"+id).parents(".toothMapItem").find(".toothMap").find(".num").each(function(i,obj){
				$(this).text("");
			});
			$("#"+id).parents(".toothMapItem").find(".toothTime").find("input").val("").attr("disabled","disabled").css("background-color","#c3c3c3").css("pointer-events","none");
		}
	}


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
				content: '请输入正确牙位！(牙位值为1~8,且不能重复)',
				end:function(){
					$("#"+inputObj).val("").focus();
					toothString="";
				}
			});
		}
		toothString=toothArr.join(",");
		return toothString;
	};

	function doPrint() {
		$("input").removeAttr("placeholder");
		//有值显示边框，无值隐藏
		$("input[type='text']").each(function(i,obj){
			if($(this).attr("value")){
				$(this).css("border","0px solid red").css("border-bottom","1px solid #c3c3c3");
			}else{
				$(this).css("border","0px solid red");
			}
		});
		//删掉禁用属性，因为禁用打印为黑
		// 色
		$("input[type='radio']").each(function(i,obj){
			$(this).removeAttr("disabled");
		});
		$("input[type='checkbox']").each(function(i,obj){
			$(this).removeAttr("disabled");
		});
		bdhtml=window.document.body.innerHTML;
		sprnstr="<!--startprint-->";
		eprnstr="<!--endprint-->";
		prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
		prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
		window.document.body.innerHTML=prnhtml;
		window.print();  //打印
		document.body.innerHTML=bdhtml; //恢复页面
	}
	//打印样式
	function myPreviewAll(){
		if(doctorstatus&&signature==""){
			$("#img").css("display","none");
		}
		if(patientstatus&&patientsignature==""){
			$("#patientimg").css("display","none");
		}
	};

	function getButtonPower() {
		var menubutton1 = "";
		for (var i = 0; i < listbutton.length; i++) {
			if (listbutton[i].qxName == "zsbs_xgbd"&&doctorstatus&&patientstatus) {
				$("#consent_updateBtn").removeClass("hidden");
			}
		}
		$("#bottomBarDdiv").append(menubutton1);
	}
</script>

</html>