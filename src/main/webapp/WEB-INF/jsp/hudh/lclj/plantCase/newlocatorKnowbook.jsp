<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />

	<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/kqds/kqds_request.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/kqds/kqds_system.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
	<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
</head>
<style id="styleA" type="text/css">
	*{
		margin: 0px;
		padding: 0px;
		font-size: 15px;
		line-height: 30px;
	}
	#content{
		margin-bottom: 20px;
	}
	/* 输入框 */
	#content input[type="text"]{
		font-weight: bold;
		color:#00a6c0;
		padding: 0;
		margin: 0;
	}
	/* 标题 */
	#content .bigtitle{
		display: inline-block;
		width:60%;
		font-size: 26px !important;
		line-height: 26px;
		letter-spacing: 1px;
		font-weight: bold;
		padding-top: 5%;
		margin-bottom: 2%;
		text-align: center;
	}
	.container-fluid .smalltitle {
		display: block;
		width: 100%;
		font-size: 18px;
		line-height: 32px;
		letter-spacing: 1px;
		font-weight: bold;
		text-indent: 1rem;
	}
	.container-fluid .smalltitle>font{
		font-size:24px;
		margin-right: 10px;
		margin-left: 8px;
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
		width: 250px;
		height: 30px;
	}
	.patient .inputDiv:nth-child(12){
		width: 320px!important;
	}
	.baseInfo .patient>.inputDiv>span{
		display: inline-block;
		font-size: 16px;
		line-height: 30px;
		height: 30px;
	}
	.baseInfo .patient>.inputDiv>font{
		color:#00a6c0;
		font-weight: bold;
	}
	.baseInfo .patient>.inputDiv>input{
		border:none;
		/* 	 	border: 2px solid white;  */
		width: 100px;
		height: 30px;
	}
	/* 详细文字介绍 */
	#content .consent_text{
		/* 		width:100%; */
		font-weight: normal;
		overflow: hidden;
		/* 		text-indent: 2em; */
		margin-left: 2rem;
		margin-bottom: 9px;
	}
	/* 信息输入组合框 */
	.examine_continer .rpInfo_import{
		width:100%;
	}
	.examine_continer .rpInfo_import>span{
		font-weight: normal;
	}
	.examine_continer .rpInfo_import>input{
		width:70%;
		background-color: transparent;
		border:0px;
		font-weight: bold;
		/* 		border-bottom: 1px solid black; */
		border-radius:0px;
		/* 		text-align: center; */
		padding: 0px;
	}
	/* 签名 */
	#content #consent_signature{
		overflow: hidden;
		/* 	 	height: 120px; */
		margin-top: 10px;
		margin-bottom: 20px;
	}
	#content #consent_signature>.signature_time{
		width:40%;
		position: relative;
	}
	#content #consent_signature>.signature_time>.signature_box{
		width:100%;
		/* 		height:80px; */
	}
	#content #consent_signature>.signature_time>.signature_box>span{
		font-weight: normal;
	}
	/* 时间选择框 */
	#content #consent_signature>.signature_time>input{
		width:40%;
		position: absolute;
		right: 0px;
		bottom: 0px;
		text-align: center;
	}
	/* 按钮 */
	#content .btns{
		width:100%;
		text-align:center;
		margin-top: 20px;
	}
	#content .btns button{
		background-color: #00A6C0;
		font-weight: normal;
		color: white;
		border: 0px;
		border-radius: 5px;
		padding: 0px 20px;
		letter-spacing: 1px;
	}
	#content .btns #consent_saveBtn{
		margin-right: 30px;
	}
	input::-webkit-input-placeholder{
		font-weight: normal;
	}
	input:-moz-placeholder{
		font-weight: normal;
	}
	input::-moz-placeholder{
		font-weight: normal;
	}
	input:-ms-input-placeholder{
		font-weight: normal;
	}
	.textAuto_element{
		display: block;
		width:65%;
		color: #00a6c0;
	}
	/* 	logo */
	.logoImg {
		width: 150px;
		height: 45px;
	}
	h2{
		margin-top: 0px;
		margin-bottom: 0px;
	}
	/* 	必填项 */
	.colorRed {
		color: #d10c0c;
		font-size: 20px;
		margin-right: 5px;
	}
	.inputheight{
		height: 100%!important;
		border: 1px solid #e5e5e5!important;
	}
	.inputwidth{
		width: 50%!important;
	}
	.address:empty:before{
		content: attr(placeholder);
		color:#777474;
		font-weight: normal!important;
	}
	.address:focus:before{
		content:none;
	}
	.allergy:empty:before{
		content: attr(placeholder);
		color:#777474;
		font-weight: normal!important;
	}
	.allergy:focus:before{
		content:none;
	}
	.treatmentparts:empty:before{
		content: attr(placeholder);
		color:#777474;
		font-weight: normal!important;
	}
	.treatmentparts:focus:before{
		content:none;
	}
	.diagnose:empty:before{
		content: attr(placeholder);
		color:#777474;
		font-weight: normal!important;
	}
	.diagnose:focus:before{
		content:none;
	}
	#content input[type="text"]:disabled{
		color:#00A6C0;
		opacity: 1;
		-webkit-text-fill-color: #00A6C0;
	}
	@page{
		size:215mm 290mm;
		margin: 0 auto;
	}
</style>
<body style="padding: 0px 3% 0px;">
<!--startprint-->
<div id="content" class="container-fluid">
	<img class="logoImg" src="<%=contextPath%>/static/image/kqdsFront/jiagong/logoName.png">
	<h2 class="bigtitle">种植覆盖义齿知情同意书</h2>
	<!-- 基本信息 -->
	<div class="row">
		<div class="col-md-12 col-sm-12 colDefined">
			<span class="smalltitle"><font>●</font>基本信息</span>
		</div>
	</div>
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
				<div class="inputDiv" style="clear:left;">
					<span>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄：</span><font type="text" id="patient_age"></font>
				</div>
				<div class="inputDiv">
					<span>职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业：</span><input placeholder="最多展示10个字" onblur="TextLengthCheck(this.id,10);" style="width:60%;" id="profession" type="text"/>
				</div>
				<div class="inputDiv" style="clear:left;">
					<span>证件号码：</span><font type="text" id="patient_ID"></font>
				</div>
				<div class="inputDiv">
					<span>出生年月：</span><font type="text" id="patient_bone"></font>
				</div>
				<div class="inputDiv" style="clear:left;">
					<span>联系电话：</span><font type="text" id="patient_phone"></font>
				</div>
				<div class="inputDiv" >
					<span>紧急联系人：</span><font type="text" id="patient_emergency_contact"></font>
				</div>
				<div class="inputDiv">
					<span>紧急联系人电话：</span><font type="text" id="emergency_contact_tel"></font>
				</div>
				<div class="inputDiv" style="clear:left;">
					<span>现居住地址：</span><font type="text" id="address"></font>
				</div>
				<div class="inputDiv" style="clear:left;">
					<span>药物过敏：</span>
					<!-- 						<input placeholder="此输入框最多展示12个字" onblur="TextLengthCheck(this.id,30);" style="width:60.5%;" id="allergy" type="text"/> -->
					<span id="allergy" class="textAuto_element allergy" placeholder='请输入过敏药物' onkeyup="diagnoseTextBorder(this.id);" onblur="TextLengthCheck(this.id,10);"  style="font-size: 16px;font-weight: bold;" contenteditable="true"></span>
				</div>
				<div class="inputDiv">
					<span>治疗日期：</span>
					<input readonly="readonly" placeholder="请选择日期" id="treatmenttime" class="treat_time" style="width: 60% " type="text"/>
				</div>
				<div class="inputDiv" style="clear:left;">
					<span>诊&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;断：</span>
					<span id="diagnose" class="textAuto_element diagnose" placeholder='请输入诊断明细' onkeyup="diagnoseTextBorder(this.id);" onblur="TextLengthCheck(this.id,10);" style="font-size: 16px;font-weight: bold;" contenteditable="true"></span>
					<!-- 						<input placeholder="此输入框最多展示12个字" onblur="TextLengthCheck(this.id,30);" style="width:60.5%;" id="diagnose" type="text"/> -->
				</div>
				<div class="inputDiv">
					<span>治疗部位：</span>
					<span id="treatmentparts" class="textAuto_element treatmentparts" placeholder='请输入治疗部位'  onblur="TextLengthCheck(this.id,10);" onkeyup="diagnoseTextBorder(this.id);" style="font-size: 16px;font-weight: bold;" contenteditable="true"></span>
					<!-- 						<input placeholder="此输入框最多展示12个字" onblur="TextLengthCheck(this.id,30);" style="width:60.5%;" id="treatmentparts" type="text"/> -->
				</div>
			</div>
			<div class="patientHeader" style="width:10%;float:left;">
				<img src="<%=contextPath%>/static/image/kqdsFront/jiagong/headImg.jpg">
				<input style="display:none;" type="file" />
			</div>
		</div>
	</div>
	<i class="line"></i>
	<div class="consent_text">
		1、因患者骨质条件的个体差异性,术后有可能不能即刻负重。
	</div>
	<div class="consent_text">
		2、术后初期戴过渡牙的患者,中途会有复查调牙的过程,待植体骨结合好牙龈相对稳定后还需重新取模定咬合制作最终牙,术后须来院的次数因人而异。
	</div>
	<div class="consent_text">
		3、种植覆盖义齿是靠植体及粘膜共同支持的,植体起到固位作用,不易脱落,根据植体数量及牙位、反颌程度的不同基托的大小不一。
	</div>
	<div class="consent_text">
		4、因植体部位不同,种植覆盖义齿会有轻微翘动，不影响正常功能。
	</div>
	<div class="consent_text">
		5、种植覆盖义齿会有食物嵌塞现象，患者每晚取出清洁，以保持口腔清洁度和健康。
	</div>
	<div class="consent_text" style="margin-bottom: 1%;">
		6、个别种植覆盖义齿有固位不良、义齿脱落、牙桥裂开的可能,如有发生,请来院维修。
	</div>
	<!-- 		<i class="line"></i> -->
	<div class="consent_text" style="font-weight: bold;text-indent: 0em; margin-top:7%;">
		<p style="" class="font"><i class="colorRed">*</i>以上情况已知悉并确认签字。</p>
	</div>
	<!-- 手术签名 -->
	<div id="consent_signature">
		<!-- 患者签名 -->
		<div class="signature_time" style="margin-top:2%;float: left;">
			<div class="signature_box">
				<span id="patientSignature">患者签名:</span>
				<img id="patientimg" style="width:156px;height:auto;"/>
			</div>
			<input id="patienttime" type="text" class="consent_time inputheight inputheight2" readonly="readonly" placeholder="请选择日期"/>
		</div>
		<!-- 医生签名 -->
		<div class="signature_time" style="margin-top:2%;float: right;">
			<div class="signature_box">
				<span id="doctorSignature">医生签名:</span>
				<img id="img" style="width:156px;height:auto;"/>
			</div>
			<input id="doctortime" type="text" class="consent_time inputheight inputheight2" readonly="readonly" placeholder="请选择日期"/>
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
	var patientsignature="";
	var doctorstatus=true;
	var patientstatus=true;
	var contextPath = "<%=contextPath%>";
	var id;	//选中患者id
	var order_number;//选中患者order_number
	var formParentObj;// 父页面的患者信息对象
	var caseId=""; //已存在的病历id
	var menuid=window.parent.menuid;//左侧菜单id
	var patient_usercode;
	$(function(){
		var userAgent = navigator.userAgent;
		if (userAgent.indexOf("iPad") > -1){
			$("#print_Btn").hide();//移动端打印按钮隐藏
		}
		if(window.parent.consultSelectPatient){
			formParentObj=window.parent.consultSelectPatient;
			id= formParentObj.seqid;	//选中患者id
			order_number= formParentObj.orderNumber;//选中患者order_number
			patient_usercode= formParentObj.usercode; //选中患者usercode
		}else{
			formParentObj=window.parent.patientObj;
			id= formParentObj.id;	//选中患者id
			order_number= formParentObj.orderNumber;//选中患者order_number
			// console.log(JSON.stringify(formParentObj)+'------formParentObj');
		}

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
		//治疗日期
		$(".treat_time").datetimepicker({
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
		getUserInformation(patient_usercode);
		initZzblInfor();
		document.ondragstart = function() {
			return false;
		};
	});
	/* 初始化患者信息 */
	function getUserInformation(usercode){
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
				//患者姓名、年龄、性别赋值
				$("#patient_first_diagnose").text(r.cztime);
				$("#patient_num").text(r.usercode);
				$("#patient_name").text(r.username);
				$("#patient_sex").text(r.sex);
				$("#patient_age").text(r.age);
				$("#patient_ID").text(r.idcardno);
				$("#patient_bone").text(r.birthday);
				$("#patient_phone").text(r.phonenumber1);
//	 	             $("#patient_emergency_contact").attr("value", r.emergencyContact);
//	 	             $("#emergency_contact_tel").attr("value", r.emergencyPhone);
				$("#address").text(r.provincename + r.cityname + r.townname + r.streetName);
				patient_seqid=r.seq_id;
			}
		});
	}
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
		var url = contextPath + '/HUDH_ZzblAskAct/updateLocatorFamiliar.act';
		var patienttime = $("#patienttime").val();//修复医生签名时间
		var param = {
			id : caseId,
			patientSignature :  patientsignature,//患者签名
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
	//去掉自适应span元素的下边框
	function diagnoseTextBorder(obj){
		if($("#"+obj).text()){
			$("#"+obj).css("border-bottom","0px solid black");
		}else{
			$("#"+obj).css("border-bottom","1px solid black");
		}
	};

	/* 2019/7/22 lutian span输入文字长度校验方法   obj：元素id  textLength：限制文字长度 */
	function importTextLengthCheck(obj,textLength){
		var objTextVal=$("#"+obj).text();
		if(objTextVal.length>textLength){
			layer.open({
				title: '提示',
				content:'文字长度不能超过'+textLength+'字!',
				end:function(){
					var inputNewVal=$("#"+obj).text();
					$("#"+obj).text(inputNewVal.substring(0,textLength)).focus();
				}
			});
			return;
		}
	}
	/* 2019/7/16 lutian 文字长度校验方法   obj：元素id  textNum：限制文字长度 */
	function TextLengthCheck(obj,textNum){
		var objTextVal=$("#"+obj).val();
		var checkTitleBefore=$("#"+obj).parent(".rpInfo_import").find("span").text();
		var checkTitle=checkTitleBefore.substring(0,checkTitleBefore.indexOf("：")); // 校验文字长长度的标题
		if(objTextVal.length>textNum){
			$("#"+obj).attr("maxlength",textNum);
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

	/* 页面赋值判断初始化 */
	function initZzblInfor(){
		//console.log(id+"--------------查询id");
		var url = contextPath + '/HUDH_ZzblAskAct/findLocatorFamiliar.act';
		$.ajax({
			url: url,
			type:"POST",
			dataType:"json",
			data : {
				id :  id, //临床路径ID
				order_number : order_number,
				classify:0
			},
			success:function(result){
				// console.log(JSON.stringify(result)+"-----result");
				caseId=result.seqId;  //病历id
				/* 判断是否已经填写过内容 */
				if(result.seqId){
					$("#consent_saveBtn").css("display","none");//隐藏保存按钮
					$("#consent_updateBtn").css("display","inline-block");//显示修改按钮
					//赋值
					for(var key in result){
						//console.log(key+"-------------"+result[key]);
						$("#"+key).attr("value",result[key]);// 填框赋值
						//$("#"+key).text(result[key]).attr("contenteditable",false).css("height","30px");// 可编辑span填框赋值
						$("#"+key).text(result[key]).css("height","30px");// 可编辑span填框赋值
						//自适应span元素下划线判断
						if($("#"+key).text()){
							if(key=='profession'){
								continue;
							}
							if(key=='treatmenttime'){
								continue;
							}
							$("#"+key).css("border","0px solid black");
						}
					}
					//$("input").attr("disabled","disabled").css("background","transparent");//查看信息的时候禁止在填写
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
	//打印方法
	function myPreviewAll() {
		if(doctorstatus&&signature==""){
			$("#img").css("display","none");
		}
		if(patientstatus&&patientsignature==""){
			$("#patientimg").css("display","none");
		}
		bdhtml=window.document.body.innerHTML;
		sprnstr="<!--startprint-->";
		eprnstr="<!--endprint-->";
		prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
		prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
		var htmlStyle="<style>button{display:none;}*{font-size: 14px!important;line-height: 28px;}.margin{width: 95%;position: absolute;margin-top: -29px;}.marginleft1{margin-left: -3px;}.marginleft{margin-left: -5px;}.font{font-size: 18px!important;font-weight: bolder;}.inputheight2{border: 1px solid transparent!important;}.consent_updateBtn{display:none!important;}.baseInfo .patient>.inputDiv{width:250px}::-webkit-input-placeholder{color:transparent;}.diagnose:empty:before{color:transparent!important}.treatmentparts:empty:before{color:transparent!important}.allergy:empty:before{color:transparent!important}</style>";
		window.document.body.innerHTML=prnhtml+htmlStyle;
		window.print();  //打印
		window.document.body.innerHTML=bdhtml; // 恢复页面
		window.location.reload();
	}
	/* 打印本页面方法 */
	function myPreviewAll2(){
		if(doctorstatus&&signature==""){
			$("#img").css("display","none");
		}
		if(patientstatus&&patientsignature==""){
			$("#patientimg").css("display","none");
		}
		LODOP=getLodop();
		LODOP.PRINT_INIT("LOCATOR覆盖义齿<br/>即刻负重(即刻用)知情同意书");
		LODOP.SET_PRINT_MODE("FULL_WIDTH_FOR_OVERFLOW",true);//宽度溢出缩放
		//LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");
		var htmlStyle="<style>button{display:none;}*{font-size: 14px!important;line-height: 28px;}.margin{width: 95%;position: absolute;margin-top: -29px;}.marginleft1{margin-left: -3px;}.marginleft{margin-left: -5px;}.font{font-size: 18px!important;font-weight: bolder;}.inputheight2{border: 1px solid transparent!important;}.consent_updateBtn{display:none!important;}.baseInfo .patient>.inputDiv{width:100px!important;}</style>";
		var html="<!DOCTYPE html>"+document.getElementsByTagName("html")[0].innerHTML+htmlStyle;
// 			LODOP.ADD_PRINT_HTM(10,10,"100%","100%",html);
		LODOP.ADD_PRINT_HTM(0,0,"100%","100%",html);
		LODOP.PREVIEW();
	};

	//获取url中的参数
	function getUrlParam(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg);  //匹配目标参数
		if (r != null) return unescape(r[2]);
		return null; //返回参数值
	}

	//更新
	function update(){
		var patient_name = $("#patient_name").val();//患者姓名
		var patient_sex = $("#patient_sex").val();//患者性别
		var patient_age = $("#patient_age").val();//患者年龄
		var address = $("#address").text();//地址
		var phone = $("#patient_phone").val();//电话
		var allergy = $("#allergy").text();//药物过敏
		var diagnose = $("#diagnose").text();//诊断
		var treatmentParts = $("#treatmentparts").text();//治疗部位
		var treatmentTime = $("#treatmenttime").val();//治疗日期
		var profession = $("#profession").val();//职业
		//var PatientSignature = $("#PatientSignature").val();//患者签字
		var PatientTime = $("#patienttime").val();//患者签字时间
		//var doctorSignature = $("#doctorSignature").val();//医生签字
		var doctorTime = $("#doctortime").val();//医生签字时间
		var createtime= new Date().Format("yyyy-MM-dd HH:mm:ss");

		var param={
			id:caseId,
			LcljId :  id,
			LcljNum :  order_number,
			name :patient_name,
			sex : patient_sex,
			age : patient_age,
			address :  address,
			phone :  phone,
			allergy :  allergy,
			diagnose :  diagnose,
			treatmentParts :  treatmentParts,
			treatmentTime :  treatmentTime,
			profession :  profession,
			patientSignature :  patientsignature,
			patientTime :  PatientTime,
			doctorSignature :  signature,
			doctorTime :  doctorTime,
			createtime :  createtime,
			classify:0
		};
		//console.log(JSON.stringify(param)+"------------参数");
		var url = contextPath + '/HUDH_ZzblAskAct/updateLocatorFamiliar.act';
		$.ajax({
			url: url,
			type:"POST",
			dataType:"json",
			data : param,
			success:function(result){
				layer.alert("修改成功！", {
					end: function() {
						var frameindex = parent.layer.getFrameIndex(window.name);
						parent.layer.close(frameindex); //再执行关闭
					}
				});
			}
		});
	}

	//保存
	function save() {
		var patient_name = $("#patient_name").val();//患者姓名
		var patient_sex = $("#patient_sex").val();//患者性别
		var patient_age = $("#patient_age").val();//患者年龄
		var address = $("#address").text();//地址
		var phone = $("#patient_phone").val();//电话
		var allergy = $("#allergy").text();//药物过敏
		var diagnose = $("#diagnose").text();//诊断
		var treatmentParts = $("#treatmentparts").text();//治疗部位
		var treatmentTime = $("#treatmenttime").val();//治疗日期
		var profession = $("#profession").val();//职业
		//var PatientSignature = $("#PatientSignature").val();//患者签字
		var PatientTime = $("#patienttime").val();//患者签字时间
		//var doctorSignature = $("#doctorSignature").val();//医生签字
		var doctorTime = $("#doctortime").val();//医生签字时间
		var createtime= new Date().Format("yyyy-MM-dd HH:mm:ss");

		var url = contextPath + '/HUDH_ZzblAskAct/saveLocatorFamiliar.act';
		$.ajax({
			url: url,
			type:"POST",
			dataType:"json",
			data : {
				LcljId :  id,
				LcljNum :  order_number,
				name :patient_name,
				sex : patient_sex,
				age : patient_age,
				address :  address,
				phone :  phone,
				allergy :  allergy,
				diagnose :  diagnose,
				treatmentParts :  treatmentParts,
				treatmentTime :  treatmentTime,
				profession :  profession,
				patientSignature :  patientsignature,
				patientTime :  PatientTime,
				doctorSignature :  signature,
				doctorTime :  doctorTime,
				createtime :  createtime,
				classify:0
			},
			success:function(result){
				layer.alert("保存成功！", {
					end: function() {
						var frameindex = parent.layer.getFrameIndex(window.name);
						parent.layer.close(frameindex); //再执行关闭
					}
				});
			}
		});
	}

	function getButtonPower() {
		var menubutton1 = "";
		for (var i = 0; i < listbutton.length; i++) {
			if (listbutton[i].qxName == "zsbs_xgbd"&&doctorstatus&&patientstatus) {
				$("#consent_updateBtn").removeClass("hidden");
			}
		}
		$("#bottomBarDdiv").append(menubutton1);
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

	//日期格式化
	Date.prototype.Format = function (fmt) { //author: meizz
		var o = {
			"M+": this.getMonth() + 1, //月
			"d+": this.getDate(), //日
			"H+": this.getHours(), //时
			"m+": this.getMinutes(), //分
			"s+": this.getSeconds(), //秒
			"q+": Math.floor((this.getMonth() + 3) / 3), //
			"S": this.getMilliseconds() //
		};
		if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		for (var k in o)
			if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	}
</script>
</html>