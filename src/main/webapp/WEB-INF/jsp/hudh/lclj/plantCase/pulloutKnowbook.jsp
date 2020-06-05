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
		font-size: 16px;
		line-height: 28px; 
	}
	#content{
		font-weight: bold;
		margin-bottom: 20px;
		padding-top: 10px;
	}
	/* 输入框 */
	#content input[type="text"]{
		font-weight: bold;
		color: #00a6c0;
	}
	/* 标题 */
	#content .bigtitle{
		display: block;
		width:100%;
		font-size: 26px !important;
		line-height: 26px;
		letter-spacing: 1px;
		font-weight: bold;
	   	padding-top: 10px;  
	    margin-bottom:10px;
	    border-top: 2px dotted #776c6c;
	}
	/* 详细文字介绍 */
	#content .consent_text{
/* 		width：100%; */
		font-weight: normal;
		overflow: hidden;
/* 		text-indent: 2em; */
		margin-left: 2rem;
	}
	/* 信息输入组合框 */
	.examine_continer .rpInfo_import{
		width:100%;
	}
	.examine_continer .rpInfo_import>span{
		font-weight: normal;
	}
	.examine_continer .rpInfo_import>input{
		width:59%;
		background-color: transparent; 
		border:0px;
		font-weight: bold;
		border-radius:0px;
		padding: 0px;
	}
	/* 签名 */
	#content #consent_signature{
	 	overflow: hidden;

	}
	#content #consent_signature>.signature_time{
		width:50%;
		margin-left:50%;
		position: relative;
	}
	#content #consent_signature>.signature_time>.signature_box{
		width:100%;
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
		margin-top: 40px;
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
		width:100%; 
		color: #00a6c0;	
/* 	 	border-bottom: 1px solid black; */
	}
	/* 	分隔线 */
	.line{
		display:block;
		border: 1px solid #776c6c;
		margin-bottom: 1%;
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
    	color: #00A6C0;
 	    opacity: 1; 
	    -webkit-text-fill-color: #00A6C0;     	
    }
	#logoImg{
	    width: 12%;
	    margin-bottom: 10px;
	}
</style>
<body style="padding: 0px 3% 0px;">
<!--startprint-->
	<div id="content">
	<img id="logoImg" src="<%=contextPath%>/static/image/kqdsFront/jiagong/logoName.png">
		<h2 class="bigtitle">拔牙手术知情同意书</h2>
		<i class="line"></i>
		<div class="container-fluid examine_continer">
			<!-- 患者信息 -->
			<div class="row" style="position: relative;">
			<div class="col-md-6 col-sm-6 col-xs-6 colDefined">
				<div class="rpInfo_import">
                        <span class="item">首诊时间：</span>
                        <input id="patient_time" type="text" disabled="disabled" class="input" />
                    </div>
				</div>
				<div class="col-md-6 col-sm-6 col-xs-6 colDefined">
					 <div class="rpInfo_import">
                        <span class="item">编号：</span>
                        <font class="alreadyInfo input" id="patient_num"  class="input" style="color: #00A6C0;"></font>
                    </div>
				</div>
			</div>
			
			
			<div class="row">
			<div class="col-md-4 col-sm-4 col-xs-4 colDefined">
					<div class="rpInfo_import">
						<span>姓名：</span>
						<input id="patient_name" type="text" disabled="disabled"/>
					</div>
				</div>
				
				<div class="col-md-4 col-sm-4 col-xs-4 colDefined">
					<div class="rpInfo_import">
						<span>性别：</span>
						<input id="patient_sex" type="text" disabled="disabled"/>
					</div>
				</div>
				
				<div class="col-md-4 col-sm-4 col-xs-4 colDefined">
					<div class="rpInfo_import">
						<span>年龄：</span>
						<input id="patient_age" type="text" disabled="disabled"/>
					</div>
				</div>
				
				</div>

			<div class="row">
			 <div class="col-md-4 col-sm-4 col-xs-4 colDefined">
                    <!-- 信息输入组合框 -->
                   <div class="rpInfo_import">
						<span>联系电话：</span>
						<input id="patient_phone" style="" type="text" disabled="disabled"/>
					</div>
					
			</div>
					<div class="col-md-4 col-sm-4 col-xs-4 colDefined" style="height:30px;">
                    <div class="rpInfo_import">
                        <span class="item">紧急联系人：</span>
                        <input id="patient_instancyName" type="text" disabled="disabled" class="input" />
                    </div>
                  </div>
                    <div class="col-md-4 col-sm-4 col-xs-4 colDefined" style="height:30px;">
                    <div class="rpInfo_import">
                        <span class="item">紧急联系人电话：</span>
                        <input id="patient_instancyPhone" type="text" disabled="disabled" class="input" />
                    </div>
                    </div> 
                    
               </div>
               	<div class="row">
				<div class="col-md-6 col-sm-6 col-xs-6 colDefined">
					<div class="rpInfo_import">
                        <span class="item">证件号码：</span>
                        <input id="patient_idNumber" type="text" disabled="disabled" class="input" />
                    </div>
				</div>
				
				<div class="col-md-6 col-sm-6 col-xs-6 colDefined">
					<div class="rpInfo_import">
                        <span class="item">出生年月：</span>
                        <input id="patient_date" type="text" disabled="disabled" style="width: 100px;color: #00a6c0;" />
                    </div>
				 </div>
				</div>
               <div class="row">
                <div class="col-md-12 col-sm-12 col-xs-12 colDefined">
					<!-- 信息输入组合框 -->
					<!-- <div class="rpInfo_import" style="width: 100%;overflow: hidden;">
						<span style="float:left;">现居住地址：</font></span>
						<div style="float:left;width:82.5%;position: relative;">
							<span id="address" class="textAuto_element address" placeholder='请输入地址' onblur="importTextLengthCheck(this.id,60);" onkeyup="diagnoseTextBorder(this.id);" style="font-size: 16px;font-weight: bold;" contenteditable="true"></span>
						</div>
					</div> -->
					<div class="rpInfo_import">
                        <span class="item">现居地址：</span>
                        <input id="patient_site" type="text" disabled="disabled" class="input" />
                    </div>
				</div>
				</div>
			
				<!-- <div class="col-md-3 col-sm-3 col-xs-3 colDefined" style="padding:0px;margin-left: -3%;">
					<div class="rpInfo_import">
						<span>职业：</font></span>
						<input placeholder="此输入框最多展示12个字" onblur="TextLengthCheck(this.id,30);" style="width:60.5%;" id="profession" type="text"/>
					</div>
				</div> -->
		
			
			<div class="row">
				<div class="col-md-6 col-sm-6 col-xs-6 colDefined">
					<!-- 信息输入组合框 -->
					<div class="rpInfo_import margintop1" style="width: 100%;overflow: hidden;">
						<span style="float:left;">药物过敏：</span>
						<div style="float:left;width:73%;position: relative;">
							<span id="allergy" class="textAuto_element allergy" placeholder='请输入过敏药物' onkeyup="diagnoseTextBorder(this.id);" style="font-size: 16px;font-weight: bold;" contenteditable="true"></span>
						</div>
					</div>
				</div>
				<div class="col-md-6 col-sm-6 col-xs-6 colDefined">
					<!-- 信息输入组合框 -->
					<div class="rpInfo_import">
						<span>治疗日期：</span>
						<input readonly="readonly" placeholder="请选择日期" id="treatmenttime" class="treat_time inputwidth" style="" type="text"/>
					</div>
				</div>
			</div>
			
			
			<div class="row">
				<div class="col-md-6 col-sm-6 col-xs-6 colDefined">
					<!-- 信息输入组合框 -->
					<div class="rpInfo_import" style="width: 100%;overflow: hidden;">
						<span style="float:left;">诊<font style="display:inline-block;margin-left: 2em;">断：</font></span>
						<div style="float:left;width:73%;position: relative;">
							<span id="diagnose" class="textAuto_element diagnose" placeholder='请输入诊断明细' onkeyup="diagnoseTextBorder(this.id);" style="font-size: 16px;font-weight: bold;" contenteditable="true"></span>
						</div>
					</div>
				</div>
				
			<div class="col-md-6 col-sm-6 col-xs-6 colDefined">
					<div class="rpInfo_import" style="width: 100%;overflow: hidden;">
						<span style="float:left;">治疗部位：</font></span>
						<div style="float:left;width:73%;position: relative;">
							<span id="treatmentparts" class="textAuto_element treatmentparts" placeholder='请输入治疗部位' onblur="importTextLengthCheck(this.id,120);" onkeyup="diagnoseTextBorder(this.id);" style="font-size: 16px;font-weight: bold;" contenteditable="true"></span>
						</div>
					</div>
		</div>
			</div>
		</div>
		<i class="line"></i>
		<div class="consent_text" style="text-indent: 2rem;">
			牙齿拔除是口腔科常见的手术。阻生齿、劈裂牙、死髓牙等牙齿的拔除是潜在风险较大、创伤较大、需时较长、术后局部反应及并发症较重的口腔科手术。一方面，医护人员力争做到避免这些并发症的出现；另一方面，患者应在术前充分了解术中、术后可能出现的问题，与医护人员密切配合，共避风险。
		</div>
		<div class="consent_text" style="font-weight: 600;">
			下面是拔牙术中、术后可能出现的风险和并发症：
		</div>
		<div class="consent_text">
			1、麻醉意外：任何所用药物都可能产生副作用，包括轻度的恶心、呕吐、皮疹等症状到严重的过敏性休克，甚至危及生命，需及时处理。患者术前应如实告知身体状况，特别是药物过敏史。
		</div>
		<div class="consent_text">
			2、牙或牙根折断，可即刻手术取出，或根据具体情况保留观察。
		</div>
		<div class="consent_text">
			3、口角拉伤、邻牙或对颌牙损伤或折断：视具体情况可能需要治疗、结扎固定或拔除，口角拉伤或邻近牙龈撕裂可上药或缝合。
		</div>
		<div class="consent_text">
			4、术后数天面部可能肿痛、皮下淤血、皮下气肿、低热、开口受限、吞咽疼痛等，须来院复查，必要时给与抗生素和止痛药。
		</div>
		<div class="consent_text">
			5、出血：一般情况下，术后24小时内伤口有少许渗血。必要时必须使用止血药物或手术缝合、填塞；服用抗凝药物者须于术前、术后在医生指导下停药数天。
		</div>
		<div class="consent_text">
			6、术后有时同侧下唇麻木或舌麻木等，持续时间因人而异，或可能长期存在。给予神经营养药物、理疗或按摩，可加快症状消失。
		</div>
		<div class="consent_text">
			7、牙槽骨、上颌结节或颌骨骨折，需将骨折片取出或手术复位固定。
		</div>
		<div class="consent_text">
			8、出现口腔上颌窦瘘或牙根进入上颌窦，可根据牙根在上颌窦的位置，保守观察、手术取出、上颌窦瘘修补。
		</div>
		<div class="consent_text">
			9、颞下颌关节脱位、疼痛或张口受限：需关节复位、热敷或理疗等对症处理。
		</div>
		<div class="consent_text">
			10、术后感染、干槽症：需及时就诊进行处理。
		</div>
		<div class="consent_text" style="margin-bottom: 1%;">
			11、其他不常见的风险、手术意外或并发症，按具体情况对症处理。
		</div>
		<i class="line"></i>
		<div class="consent_text" style="font-weight: bold;text-indent: 0em;">
			<p style="margin-left: 60%;font-weight:600;" class="font"><i class="colorRed">*</i>以上情况已知悉并确认签字。</p>			
		</div>
		<!-- 手术签名 -->
		<div id="consent_signature">
			<!-- 患者签名 -->
			<div class="signature_time" style="margin-top:5px;">
				<div class="signature_box">
					<span id="patientSignature">患者签名:</span>
					<img id="patientimg" style="width:156px;height:auto;"/>
				</div>
				<input id="patienttime" type="text" class="consent_time inputheight inputheight2" readonly="readonly" placeholder="请选择日期"/>
			</div>	
			<!-- 医生签名 -->
			<div class="signature_time" style="float:right;margin-top:5px;">
				<div class="signature_box">
					<span id="doctorSignature">医生签名:</span>
					<img id="img" style="width:156px;height:auto;"/>
				</div>
				<input id="doctortime"  type="text" class="consent_time inputheight inputheight2" readonly="readonly" placeholder="请选择日期"/>
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
		var pageurl = '<%=contextPath%>/HUDH_FlowAct/findPatientInformation.act';
		var id;	//选中患者id
		var order_number;//选中患者order_number
		var formParentObj;// 父页面的患者信息对象
		var caseId=""; //已存在的病历id
		var menuid=window.parent.menuid;//左侧菜单id
		$(function(){
		     var userAgent = navigator.userAgent; 
		     if (userAgent.indexOf("iPad") > -1){
		     	$("#print_Btn").hide();//移动端打印按钮隐藏
		     }

			
			if(window.parent.consultSelectPatient){
				formParentObj=window.parent.consultSelectPatient;
				id= formParentObj.seqid;	//选中患者id
				order_number= formParentObj.orderNumber;//选中患者order_number
			}else{
				formParentObj=window.parent.patientObj;
				id= formParentObj.id;	//选中患者id
				order_number= formParentObj.orderNumber;//选中患者order_number
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

		    $.ajax({
		        type: "POST",
		        url: pageurl,
		        data: { 
		        	usercode: window.parent.consultSelectPatient.usercode, 
		        	status: status,
		        	id: id,
		        	order_number: order_number 
		        	},
		        dataType: "json",
		        success: function (r) {
		            $("#patient_time").attr("value", r.cztime);
		            $("#patient_num").text(r.usercode);
		            $("#patient_name").attr("value", r.username);
		            $("#patient_sex").attr("value", r.sex);
		            $("#patient_age").attr("value", r.age);
		            $("#patient_idNumber").attr("value", r.idcardno);
		            $("#patient_date").attr("value", r.birthday);
		            $("#patient_phone").attr("value", r.phonenumber1);
		            $("#patient_instancyName").attr("value", r.emergencyContact);
		            $("#patient_instancyPhone").attr("value", r.emergencyPhone);
		            $("#patient_site").attr("value", r.provincename + r.cityname + r.townname + r.streetName);
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
					 classify:1
				},
				success:function(result){
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
		
		function doPrint() {   
		    bdhtml=window.document.body.innerHTML;   
		    sprnstr="<!--startprint-->";   
		    eprnstr="<!--endprint-->";   
		    prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);   
		    prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
		    var htmlStyle="<style>button{display:none;}*{font-size: 14px!important;line-height: 27px;}.margin{width: 95%;position: absolute;margin-top: -29px;}.margintop1{margin-top:-2px;}.margintop{margin-top: 5px;}.font{font-size: 16px!important;font-weight: bolder;}.inputheight2{border: 1px solid transparent!important;}.consent_updateBtn{display:none!important;}</style>";
		    window.document.body.innerHTML=prnhtml+htmlStyle;  
		    window.print();  //打印
		    document.body.innerHTML=bdhtml; //恢复页面
		}
		
		/* 打印本页面方法 */
		function myPreviewAll(){
			doPrint()
			if(doctorstatus&&signature==""){
				$("#img").css("display","none");
			}
			if(patientstatus&&patientsignature==""){
				$("#patientimg").css("display","none");
			}
			/* LODOP=getLodop();  
			LODOP.PRINT_INIT("拔牙手术知情同意书");
			//LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");
			//LODOP.SET_PRINT_MODE("FULL_WIDTH_FOR_OVERFLOW",true);//宽度溢出缩放
			var htmlStyle="<style>button{display:none;}*{font-size: 14px!important;line-height: 28px;}.margin{width: 95%;position: absolute;margin-top: -29px;}.margintop1{margin-top:-2px;}.margintop{margin-top: 5px;}.font{font-size: 16px!important;font-weight: bolder;}.inputheight2{border: 1px solid transparent!important;}.consent_updateBtn{display:none!important;}</style>";
			var html="<!DOCTYPE html>"+document.getElementsByTagName("html")[0].innerHTML+htmlStyle;
// 			LODOP.ADD_PRINT_HTM(10,10,"100%","100%",html);
			LODOP.ADD_PRINT_HTM(0,0,"100%","100%",html);
			LODOP.PREVIEW();	 */
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
			
			var param = {
					 id : caseId,
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
	        		 classify:1
			};
			//console.log(JSON.stringify(param)+"----------更新参数");
			var url = contextPath + '/HUDH_ZzblAskAct/updateLocatorFamiliar.act';
			 $.ajax({
					url: url,
					type:"POST",
					dataType:"json",
					data : param,
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
	        		 classify:1
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
	        /* $.axseSubmit(url, param,function() {},function(r) {
	        	layer.alert("保存成功！", {
		            end: function() {
		                var frameindex = parent.layer.getFrameIndex(window.name);
		                parent.layer.close(frameindex); //再执行关闭
		            }
		      	});
	        },function(r){
	        	layer.alert("保存失败！");
		    }); */ 
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
		
		function getButtonPower() {
		    var menubutton1 = "";
		    for (var i = 0; i < listbutton.length; i++) {
		        if (listbutton[i].qxName == "zsbs_xgbd"&&doctorstatus&&patientstatus) {
		           $("#consent_updateBtn").removeClass("hidden");
		        }
		    }
		    $("#bottomBarDdiv").append(menubutton1);
		}

		
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