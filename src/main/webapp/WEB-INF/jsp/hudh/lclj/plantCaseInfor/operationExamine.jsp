<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/hudh/lclj/flowdetail/css/base.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script> --%><!-- 引入封装ajax方法文件,（影响元素contenteditable="true"属性） -->
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<style type="text/css">
	*{
		margin: 0px;
		padding: 0px;
		font-size: 18px;
		line-height:50px;  
 		/* font-size: 12px;
		line-height: 24px; */
	}
	#content{
		font-weight: bold;
		margin-bottom: 20px;
		padding-top: 10px;
	}
	/* 标题 */
	#content .bigtitle{
		display: block;
		width:100%;
		text-align: center;
		font-size: 26px;
		line-height: 26px;
   		margin: 45px auto 35px;
   		letter-spacing: 1px;
   		font-weight: bold;
	}
	/* 详细文字介绍 */
	#content .consent_text{
		width：100%;
		font-weight: normal;
	} 
	/* 第二条：输入输入框 */
	#content .consent_text .text_input{
		line-height:normal;
		/* width: 140px; */
    	height: 30px;
    	/* padding:0px 10px; */
    	border: 0px;
    	border-radius: 0px;
    	border-bottom: 1px solid black;
    	text-align: center;
    	vertical-align: middle;
    	font-weight: bold;
    	background-color: transparent;
	}
	/* 签名 */
	#content #consent_signature{
	 	overflow: hidden;
	 	margin:20px 0px;
	 	position: relative;
	}
	#content #consent_signature>.signature_time{
		width:100%;
		height:100%;
		overflow: hidden;
		margin-bottom: 35px;
	}
	#content #consent_signature>.signature_time>.signature_box{
		width:50%;
		height:80px;
	}
	#content #consent_signature>.signature_time>.signature_box>span{
		/* font-size:18px; */
		font-weight: normal;
	}
	/* 时间选择框 */
	#content #consent_signature>input{
		line-height:normal;
		width:25%;
		position: absolute;
		right:5%;
		bottom: 0px;
		text-align: center;
	}
	/* 患者详细信息 */
	.patient_info{
		width:100%;
		overflow: hidden;
	}
	.patient_info>li{
		float:left;
	}
	.patient_info>li .consent_time{
		height: 30px;
	    width: 150px;
	    text-align: center;
	    vertical-align: middle;
	}
	/* 按钮 */
	.btns{
		width:100%;
		text-align:center;
		margin-top: 40px;
		margin-bottom: 20px;
	}
	.btns button{
	   	line-height: 34px;
	   	background-color: #00A6C0;
	   	font-weight: normal;
	   	color: white;
	   	border: 0px;
	   	border-radius: 5px;
	   	padding: 0px 20px;
	   	letter-spacing: 1px;
	}
	.btns #consent_saveBtn{
		margin-right: 30px;
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
/* 	2019.7.29  修改拔牙位展示 */
	/* 输入框 */
	#content input[type="text"]{
		font-weight: bold;
	}
	
	#content .consent_textInput .textAuto_element{
		display: inline;
		/* float: left; */
		width: auto;
		font-weight:bold;
		/* min-width: 40px; */
		border-bottom: 1px solid black;
	 	padding: 0px 20px;
	}
	#content .consent_text .tooth_map{
		/* display: block;
		float:left;  */
		display:inline-block;
/* 		width: 210px; */
		width: 225px;
    	height: 50px;
    	/* margin-bottom:0px; */
    	margin-bottom:-5px;
    	margin-left: 5px;
/*     	margin-top: 5px; */
	}
	#content .consent_text .tooth_map>li{
		width:49%;
		height:25px;
		float:left;
	}
	#content .consent_text .tooth_map>li:FIRST-CHILD{
		border-bottom: 1px solid black;
		border-right: 1px solid black;
	}
	#content .consent_text .tooth_map>li:FIRST-CHILD+li{
		border-bottom: 1px solid black;
	}
	#content .consent_text .tooth_map>li:FIRST-CHILD+li+li{
		border-right: 1px solid black;
	}
	#content .consent_text .tooth_map>li>.tooth_input{
		line-height:normal;
		display:block;
		width:100%;
		height:100%;
		padding:0px;
		border:0px;
		text-align: center;
	}
 	#content .consent_text input:disabled{ 
  	    color:black;  
  	    opacity: 1;  
  	    -webkit-text-fill-color: black;  
 	}
	#consent_updateBtn{
		outline: none;
	}
</style>
</head>
<body style="padding: 0% 3%;">
	<div id="content">
		<h2 class="bigtitle">种植牙术前安全核查单</h2>
		<div class="consent_text" style="">
			<ul class="patient_info">
				<li>
					患者姓名:<input id="patient_name" class="text_input" type="text" disabled="disabled"/>
				</li>
				<li>
					性别:<input id="patient_sex" class="text_input" type="text" disabled="disabled"/>
				</li>
				<li>
					年龄:<input id="patient_age" class="text_input" type="text" disabled="disabled"/>
				</li>
			</ul>
		</div>
		<span style="display: block;margin-bottom: 15px;">核查内容：</span>
		<div id="operation_alltext" class="consent_text" style="background-color: #ddd;padding: 10px;" contenteditable="true">
			血压<input id="blood_pressure" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>脉搏<input id="pulse" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>
			血糖<input id="blood_glucose" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>凝血功能<input id="cruor_function" class="text_input" placeholder="此框只能输10个字" onblur="TextLengthCheck(this.id,10);" type="text"/>
			感染性疾病<input id="infectious_diseases" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>会诊意见<input id="consultation_opinion" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>
<!-- 			拔牙位<input id="pullout_tooth" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>种植牙位<input id="plant_tooth" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/> -->
<!-- 2019.7.29更改拔牙位、种植牙位展示	 -->
			<div style="display: inline-block;" contenteditable="false">
				<font>拔牙位</font>
				<ul class="tooth_map">
					<li>
						<input id="uplefttoothbitone" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
					</li>
					<li>
						<input id="uperrighttoothbitone" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
					</li>
					<li>
						<input id="leftlowertoothbitone" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
					</li>
					<li>
						<input id="lowrighttoothbitone" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
					</li>
				</ul>
			</div>
			<div style="display: inline-block;" contenteditable="false">
				<font>种植牙位</font>
				<ul class="tooth_map">
					<li>
						<input id="uplefttoothbittwo" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
					</li>
					<li>
						<input id="uperrighttoothbittwo" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
					</li>
					<li>
						<input id="leftlowertoothbittwo" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
					</li>
					<li>
						<input id="lowrighttoothbittwo" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
					</li>
				</ul>
			</div>
			辅助手术<input id="assist_operation" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>种植系统<input id="plant_system" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>
		</div>
		<!-- 手术签名 -->
		<div id="consent_signature">
			<!-- 医生签名 -->
			<div class="signature_time">
				<div class="signature_box" style="float:left;">
					<span>手术护士签名:</span>
					<div id="nurseSignature"></div>
				</div>
				<div class="signature_box" style="float:right;">
					<span id="doctorSignature">手术医生签名:</span>
					<img id="img" style="width:156px;height:auto;"/>
				</div>
			</div>
			<input id="signaturetime" type="text" style="height: 30px;" readonly="readonly" placeholder="请选择日期"/>
		</div>
	</div>
	
	<!-- 按钮 -->
	<div class="btns">
		<button id="consent_saveBtn" onclick="save()">保存</button>
		<button id="consent_updateBtn" style="display: none;" class="consent_updateBtn" onclick="update()">修改表单</button>
		<button id="print_Btn" onclick="myPreviewAll()">打印本页内容</button>
	</div>
	
	<script language="javascript"  src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
	<script type="text/javascript">
		var signature="";
		var doctorstatus=true;
		var contextPath = "<%=contextPath%>";
		var id;
		var order_number;
		var username;
		var sex;
		var age;
		//var id= window.parent.patientObj.id;	//选中患者id
		//var order_number= window.parent.patientObj.orderNumber;//选中患者order_number
		var menuid=window.parent.menuid;//左侧菜单id
		var updataid;
		$(function(){
			var userAgent = navigator.userAgent; 
		    if (userAgent.indexOf("iPad") > -1){
		     	$("#print_Btn").hide();//移动端打印按钮隐藏
		    }

			if(window.parent.onclickrowOobj){
				//console.log("临床对象3+-"+JSON.stringify(window.parent.onclickrowOobj));
				id= window.parent.onclickrowOobj.seqid;
				order_number= window.parent.onclickrowOobj.orderNumber;
				username= window.parent.onclickrowOobj.username;
				sex= window.parent.onclickrowOobj.sex;
				age= window.parent.onclickrowOobj.age;
			}else{
				id= window.parent.patientObj.id;
				order_number= window.parent.patientObj.order_number;
				username= window.parent.patientObj.username;
				sex= window.parent.patientObj.sex;
				age= window.parent.patientObj.age;
			}

			//时间选择
		    $("#signaturetime").datetimepicker({
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
			$("#patient_name").attr("value",username);
			$("#patient_sex").attr("value",sex);
			$("#patient_age").attr("value",age);
			
			initZzblInfor();/* 页面赋值判断初始化 */
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
		/* 2019/7/18 lutian 文字长度校验方法   obj：元素id  textNum：限制文字长度 */
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
		
		/* 页面赋值判断初始化 */
		function initZzblInfor(){
			//console.log(id+"--------------查询id");
			var url = contextPath + '/HUDH_LCLJAct/findPreoperativeVerification.act';
			$.ajax({
				url: url,
				type:"POST",
				dataType:"json",
				data : {
					 id :  id, //临床路径ID
					 order_number : order_number
				},
				success:function(result){
					//console.log(JSON.stringify(result)+"--------------添加成功后查询数据");
					/* 判断是否已经填写过内容 */
					if(result.seqId){
						updataid=result.seqId;
						$("#consent_saveBtn").css("display","none");//隐藏保存按钮
						$("#consent_updateBtn").css("display", "inline-block");//显示修改按钮
						$("#operation_alltext").html(result.operationalltext);//改变后内容赋值
						//$("input").attr("disabled","disabled");//查看信息的时候禁止在填写
						$("input").attr("onchange","changeValue(this)");
						//$("#operation_alltext").removeAttr("contenteditable");
						//赋值 
						for(var key in result){
							if(key=="operation_alltext"){
								continue;
							}
							$("#"+key).attr("value",result[key]);// 填框赋值
						}
						signature=result.doctorsignature;
						if(signature!=""){
							$("#img").attr('src', signature);
							doctorstatus=false;
						}else{
							$("#img").attr('display', 'none');
						}
					} 
					
				}
		  });
		}
		
		function save() {
			var patient_name = $("#patient_name").val();//患者姓名
			var patient_sex = $("#patient_sex").val();//患者性别
			var patient_age = $("#patient_age").val();//患者年龄
			var blood_pressure= $("#blood_pressure").val();//血压
			var pulse = $("#pulse").val(); //脉搏
			var blood_glucose = $("#blood_glucose").val(); //血糖
			var cruor_function = $("#cruor_function").val(); //凝血功能
			var infectious_diseases = $("#infectious_diseases").val();//感染性疾病
			var consultation_opinion = $("#consultation_opinion").val();//会诊意见
			var pullout_tooth = $("#pullout_tooth").val();//拔牙位
			var plant_tooth = $("#plant_tooth").val();//种植牙位
// 	2019.7.29   更改拔牙位、种植牙位展示start
			var upleftToothBitOne = $("#uplefttoothbitone").val();
			var uperRightToothBitOne = $("#uperrighttoothbitone").val();
			var leftLowerToothBitOne = $("#leftlowertoothbitone").val();
			var lowRightToothBitOne = $("#lowrighttoothbitone").val();
			var upleftToothBitTwo = $("#uplefttoothbittwo").val();
			var uperRightToothBitTwo = $("#uperrighttoothbittwo").val();
			var leftLowerToothBitTwo = $("#leftlowertoothbittwo").val();
			var lowRightToothBitTwo = $("#lowrighttoothbittwo").val();
// 	end
			var assist_operation = $("#assist_operation").val();//辅助手术
			var plant_system = $("#plant_system").val();//种植系统
			var nurseSignature = $("#nurseSignature").val();//手术护士签名
			//var doctorSignature = $("#doctorSignature").val();// 手术医生签名
			var operation_alltext=$("#operation_alltext").html();//修改后全部文字
			var signaturetime=$("#signaturetime").val();//签名日期
			//console.log("签名时间===="+signaturetime);
			var url = contextPath + '/HUDH_LCLJAct/savePreoperativeVerification.act';

			$.ajax({
				url: url,
				type:"POST",
				dataType:"json",
				data : {
					 id :  id, //临床路径ID
	        		 order_number :  order_number, //节点编号
	        		 username :patient_name,//患者姓名
		        	 sex : patient_sex,//患者性别
		        	 age : patient_age,//患者年龄
		        	 blood_pressure :blood_pressure,//血压
		        	 pulse : pulse,//脉搏
		        	 blood_glucose : blood_glucose,//血糖
		        	 cruor_function :cruor_function,//凝血功能
		        	 infectious_diseases : infectious_diseases,//感染性疾病
		        	 consultation_opinion : consultation_opinion,//会诊意见
		        	 pullout_tooth :pullout_tooth,//拔牙位
		        	 plant_tooth : plant_tooth,//种植牙位
// 2019.7.29   更改拔牙位、种植牙位展示start
		        	 upleftToothBitOne : upleftToothBitOne,
		        	 uperRightToothBitOne : uperRightToothBitOne,
		        	 leftLowerToothBitOne : leftLowerToothBitOne,
		        	 lowRightToothBitOne : lowRightToothBitOne,
		        	 upleftToothBitTwo : upleftToothBitTwo,
		        	 uperRightToothBitTwo : uperRightToothBitTwo,
		        	 leftLowerToothBitTwo : leftLowerToothBitTwo,
		        	 lowRightToothBitTwo : lowRightToothBitTwo,
// 		       end 	 
		        	 assist_operation : assist_operation,//辅助手术
		        	 plant_system :plant_system,//种植系统
		        	 nurseSignature : nurseSignature,//手术护士签名
		        	 doctorSignature : signature,//手术医生签名
		        	 signaturetime : signaturetime,//签名日期
		        	 operation_alltext : operation_alltext //修改后全部文字
				},
				success:function(result){
					/* layer.alert('提交成功', function(index) {
						window.parent.location.reload(); //刷新父页面
						var frameindex = parent.layer.getFrameIndex(window.name);
		                parent.layer.close(frameindex); // 再执行关闭
					}) */
					if(result.retMsrg>0){
                        layer.alert("保存成功！", {
                            end: function() {
                                window.parent.location.reload(); //刷新父页面
                                var frameindex = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(frameindex); //再执行关闭
                            }
                        });
					}else{
                        layer.alert("保存失败！");
					}

				}
		  });
		}
		// 修改方法
		function update(){
			var patient_name = $("#patient_name").val();//患者姓名
			var patient_sex = $("#patient_sex").val();//患者性别
			var patient_age = $("#patient_age").val();//患者年龄
			var blood_pressure= $("#blood_pressure").val();//血压
			var pulse = $("#pulse").val(); //脉搏
			var blood_glucose = $("#blood_glucose").val(); //血糖
			var cruor_function = $("#cruor_function").val(); //凝血功能
			var infectious_diseases = $("#infectious_diseases").val();//感染性疾病
			var consultation_opinion = $("#consultation_opinion").val();//会诊意见
			var pullout_tooth = $("#pullout_tooth").val();//拔牙位
			var plant_tooth = $("#plant_tooth").val();//种植牙位
// 	2019.7.29   更改拔牙位、种植牙位展示start
			var upleftToothBitOne = $("#uplefttoothbitone").val();
			var uperRightToothBitOne = $("#uperrighttoothbitone").val();
			var leftLowerToothBitOne = $("#leftlowertoothbitone").val();
			var lowRightToothBitOne = $("#lowrighttoothbitone").val();
			var upleftToothBitTwo = $("#uplefttoothbittwo").val();
			var uperRightToothBitTwo = $("#uperrighttoothbittwo").val();
			var leftLowerToothBitTwo = $("#leftlowertoothbittwo").val();
			var lowRightToothBitTwo = $("#lowrighttoothbittwo").val();
// 	end
			var assist_operation = $("#assist_operation").val();//辅助手术
			var plant_system = $("#plant_system").val();//种植系统
			var nurseSignature = $("#nurseSignature").val();//手术护士签名
			//var doctorSignature = $("#doctorSignature").val();// 手术医生签名
			var operation_alltext=$("#operation_alltext").html();//修改后全部文字
			var signaturetime=$("#signaturetime").val();//签名日期
			//console.log("签名时间===="+signaturetime);
			var url = contextPath + '';

			var parms={
				seqId:updataid,
				id :  id, //临床路径ID
				order_number :  order_number, //节点编号
				username :patient_name,//患者姓名
				sex : patient_sex,//患者性别
				age : patient_age,//患者年龄
				blood_pressure :blood_pressure,//血压
				pulse : pulse,//脉搏
				blood_glucose : blood_glucose,//血糖
				cruor_function :cruor_function,//凝血功能
				infectious_diseases : infectious_diseases,//感染性疾病
				consultation_opinion : consultation_opinion,//会诊意见
				pullout_tooth :pullout_tooth,//拔牙位
				plant_tooth : plant_tooth,//种植牙位
// 2019.7.29   更改拔牙位、种植牙位展示start
				upleftToothBitOne : upleftToothBitOne,
				uperRightToothBitOne : uperRightToothBitOne,
				leftLowerToothBitOne : leftLowerToothBitOne,
				lowRightToothBitOne : lowRightToothBitOne,
				upleftToothBitTwo : upleftToothBitTwo,
				uperRightToothBitTwo : uperRightToothBitTwo,
				leftLowerToothBitTwo : leftLowerToothBitTwo,
				lowRightToothBitTwo : lowRightToothBitTwo,
// 		       end
				assist_operation : assist_operation,//辅助手术
				plant_system :plant_system,//种植系统
				nurseSignature : nurseSignature,//手术护士签名
				doctorSignature : signature,//手术医生签名
				signaturetime : signaturetime,//签名日期
				operation_alltext : operation_alltext //修改后全部文字
			};
			console.log(JSON.stringify(parms)+"-------------parms");
			return;
			$.ajax({
				url: url,
				type:"POST",
				dataType:"json",
				data : parms,
				success:function(result){
					if(result.retMsrg>0){
						layer.alert("修改成功！", {
							end: function() {
								window.parent.location.reload(); //刷新父页面
								var frameindex = parent.layer.getFrameIndex(window.name);
								parent.layer.close(frameindex); //再执行关闭
							}
						});
					}else{
						layer.alert("修改失败！");
					}

				}
			});
		}
		// input随着输入改变value属性值
		function changeValue(obj){
			$(obj).attr("value",$(obj).val());
		}

		/* 打印页面方法 */
		function myPreviewAll(){
			if(doctorstatus&&signature==""){
				$("#img").css("display","none");
			}
			LODOP=getLodop();  
			LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_完整全页");
			var htmlStyle="<style>button{display:none;}*{font-size: 12px;line-height: 24px;}</style>";
			var html="<!DOCTYPE html>"+document.getElementsByTagName("html")[0].innerHTML+htmlStyle;
			LODOP.ADD_PRINT_HTM(10,10,"100%","100%",html);
			LODOP.PREVIEW();	
		};
	</script>
</body>
</html>