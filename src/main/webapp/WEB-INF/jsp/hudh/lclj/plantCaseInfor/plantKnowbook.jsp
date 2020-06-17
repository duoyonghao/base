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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/kqds/kqds_request.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/kqds/kqds_system.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
</head>
<style id="styleA" type="text/css">
	*{
		margin: 0px; 
		padding: 0px;
		font-size: 16px;
		line-height: 30px; 
	}
	.textAuto_element{
		color:#00a6c0!important;
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
	   	padding-top: 5%;  
/* 		height:136px; */
	    margin-bottom:2%; 
	}
	/* 详细文字介绍 */
	#content .consent_text{
		width:100%;
		font-weight: bold;
		/* overflow: hidden; */
	}
	/* 第二条：输入输入框 */
	#content .consent_textInput{
		/* border:1px solid red; */
	}
	#content .consent_textInput>font{
		/* display:block; */
		height:50px;
		/* float:left; */
		font-weight:bold;
		line-height: 50px;
	}
	/* #content .consent_textInput>.text_input{
		display: block;
		float:left; 
		width: 120px;
    	height: 20px;
    	border: 0px;
    	border-radius: 0px;
    	border-bottom: 1px solid black; 
    	text-align: center;
    	margin: 20px 0px 10px;
	}  */
	#content .consent_textInput .textAuto_element{
		display: inline;
		/* float: left; */
		width: auto;
		font-weight:bold;
		/* min-width: 40px; */
		border-bottom: 1px solid black;
	 	padding: 0px 20px;
	}
	/* 第二条：种植牙位 */
	#content .consent_text .tooth_map{
		/* display: block;
		float:left;  */
		display:inline-block;
 		width: 180px; 
/* 		width: 17%; */
    	height: 50px;
    	/* margin-bottom:0px; */
    	margin-bottom:-5px;
    	margin-left: 5px;
     	vertical-align: middle; 
/*     	vertical-align:top; */
	}
	#content .consent_text .tooth_map>li{
		width:49%;
/* 		height:25px; */
		height:50%;
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
	/* 第二条：牙位输入框 */
	#content .consent_text .tooth_map>li>.tooth_input{
		display:block;
		width:100%;
		height:100%;
		padding:0px;
		border:0px;
		text-align: center;
		color: #00a6c0;
	}
	/* 备注 */
	#content #consent_remark{
		width:100%;
		height: 140px;
    	background-color: #ddd;
    	margin: 5px 0px;
	}
	/* 备注 */
	#content #consent_remark>span{
		display: block;
    	width: 7%;
		float: left;
		margin-left: 2%;
/*     	margin: 10px 0px 0px 30px; */
/*     	margin-top:1%; */
	}
	/* 备注输入框 */
	#content #consent_remark>textarea{
		width: 95%;
    	height: 90px;
    	float: left;
    	background-color: transparent;
/*     	border:0px; */
    	font-weight: normal;    	
    	margin-left: 2.5%;
    	margin-top: 0.5%;
	}
	/* 签名 */
	#content #consent_signature{
	 	overflow: hidden;
/* 	 	height: 120px; */
/* 	 	margin-top: 10px; */
	 	margin-bottom: 20px;
	}
	#content #consent_signature>.signature_time{
		width:40%;
		margin-left:60%;
/* 		height:100%; */
/* 		float:left; */
		position: relative;
	}
	#content #consent_signature>.signature_time>.signature_box{
		width:100%;
/* 		height:80px; */
	}
	#content #consent_signature>.signature_time>.signature_box>span{
		font-weight: bold;
	}
	/* 时间选择框 */
	#content #consent_signature>.signature_time>input{
		width:40%;
		position: absolute;
		right:0px;
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
	/* 	分隔线 */
	.line{
		display:block;
		border: 1px solid #776c6c;
		margin-bottom: 2%;
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
	.inputstyle{
		vertical-align:text-bottom;
	}
	.selectTag{
		min-width: 120px;
		font-size: 14px;
		/* padding-right: 10px; */
	    color: #757575;
	    /* margin-top: 9px; */
	    text-align: left;
	    /* padding-left: 5px; */
	    height: 30px;
	    background-color: transparent; 
	    /* border: 1px solid #c3c3c3; */
	    /* vertical-align: middle; */
	    -webkit-appearance: none;
	    outline: none;
	}
	/* 种植体系select */
	.bootstrap-select:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn){
		width: 180px; 
	}
	.selectTag .btn-default{
		height: 30px;
	    line-height: 30px;
	    font-size: 14px;
	    padding: 0px 30px 0px 5px;
	}
	/* 种植体系赋值 */
	.plantingsystemselectText{
		/* display: inline-block; */
		display:none;
		border-bottom: 1px solid black;
	    min-width: 100px;
	    height: 28px;
	    line-height: 28px;
	    font-size: 15px;
	    vertical-align: middle;
	    padding: 0px 8px;
	}
	@page{
		size:205mm 285mm;
		margin: 0 auto;
	}
</style>
<body style="padding: 0px 2%;">
<!--startprint-->
	<div id="content">
		<h2 class="bigtitle">人工种植牙知情同意书</h2>
		<i class="line"></i>
		<div class="consent_text">
			1、我已完全了解关于人工种植牙的所有内容，医生向我提供了多种进行牙列缺失的其他修复方案，在经过选择和比较后，我自愿选择种植术。我也同意按照口腔种植科医生的决定，对我施以局部麻醉，或使用镇静剂和止痛药。
		</div>
		<div class="consent_text consent_textInput margin">
			<font>2、我同意医生为我选择的种植体系为</font><span class="plantingsystemselectText"></span>
			<select id="plantingsystemselect" class="selectTag plantingsystemselect selectpicker" title="请选择" multiple>
				<option value="韩国Osstem种植体">韩国Osstem种植体</option>
				<option value="韩国Dentium种植体">韩国Dentium种植体</option>
				<option value="美国HIOSSEN种植体">美国HIOSSEN种植体</option>
				<option value="德国ICX种植体">德国ICX种植体</option>
				<option value="瑞典Nobel-Replace种植体">瑞典Nobel-Replace种植体</option>
				<option value="瑞典Nobel-Active种植体">瑞典Nobel-Active种植体</option>
				<option value="德国Camlog种植体">德国Camlog种植体</option>
				<option value="瑞典Nobel-PMC">瑞典Nobel</option>
				<option value="美国Zimmer">美国Zimmer</option>
			</select>
			<!-- <span onblur="importTextLengthCheck(this.id,50);" id="plantingsystem" class="textAuto_element span" contenteditable="true"></span> --><font class="top">、<!-- 型号</font><span onblur="importTextLengthCheck(this.id,50);" id="modelnumber" class="textAuto_element span" contenteditable="true"> --></span><font class="top">首次种植牙位为</font>
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
			<font>、二次种植牙位为</font>
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
			<font>、种植辅助手术为</font><span id="assistoperation" onblur="importTextLengthCheck(this.id,50);" class="textAuto_element span" contenteditable="true"></span><font>。</font>
		</div>
		<div><font>替代方案:</font>&nbsp;&nbsp;<input id="removabledenture" type="checkbox" value="活动义齿修复" class="inputstyle"/>&nbsp;&nbsp;<label for="removabledenture">活动义齿修复</label>&nbsp;&nbsp;<input id="fixeddenture" type="checkbox" class="inputstyle" value="固定义齿修复"/>&nbsp;&nbsp;<label for="fixeddenture">固定义齿修复</label></div>
				<div class="consent_text">
			医生已向我详细介绍了治疗方案、种植的程序、整个过程所需的时间和费用，我认同治疗方案，接受医院临床路径种植修复的程序和时间，并自愿支付其相关费用。
		</div>
		<div class="consent_text">
			3、我同意为完成这些计划的手术，接受医生认为确实必须的其它过程或建议，并同意医生在术中由于新发现问题而改变原来的种植计划。
		</div>
		<div class="consent_text">
			4、医生已向我介绍了可能偶然存在的手术并发症，术后反应，药物和麻醉意外。包括疼痛、感染、水肿、出血等情况，这些有可能是很严重的或长时间存在，同时也有可能出现局部变色，麻木和静脉炎症;颈部和面部肌肉的损伤和僵硬;咬合改变或者颞颌关节的损伤和活动困难;牙齿的损伤和牙髓失活;邻近的软组织损伤;耳、颈、和头部的牵涉痛；此外还有可能发生恶心、呕吐、过敏反应、骨折、淤血、愈合时间过长、上颌窦炎症及口腔瘘、面形及鼻腔改变、骨的损失、骨高度的损失和吸收、口腔黏膜溃疡和种植材料的脱落或种植体暴露等情况。我理解这些治疗过程中的一系列问题，并在此基础上同意医生实施种植治疗。
		</div>
		<div class="consent_text">
			5、我完全理解种植义齿完成后，保持口腔卫生对种植义齿长期成功使用的重要性，若不能保持口腔卫生会造成种植体周围炎症及失败。
		</div>
		<div class="consent_text">
			6、种植义齿修复完成后须定期复查，全口半口(包括分段式)及局部联桥义齿修复，戴牙后分别于1、3、6个月来院复查，以后每6个月来院复查一次;局部义齿修复戴牙后分别于1、3个月来院复查，以后每年来院复查一次。本人如未按规定时间来院复查，出现种植体松动、脱落等问题由本人承担责任。戴牙后连续两年末来院复查，即自动解除医患关系。
		</div>
		<div class="consent_text">
			7、医生已向我说明全身性疾病，如骨质疏松症、糖尿病等内分泌性疾病对种植义齿的成功有较大不良影响，同时患者的行为习惯，如吸烟、酗酒、夜磨牙等均会对种植义齿的寿命产生不良影响。
		</div>
		<div class="consent_text">
			8、我同意遵守所有的医嘱，保证在服用镇静剂、麻醉药物和处方药时，不操纵交通工具或驾驶汽车，不操作有危险的机器及进行工作，或者至少在术后24小时内，当镇静剂和/或药物作用未完全消失之前，不进行上述活动，同时保证术后不饮用酒精饮料和服用非处方药物，要控制吸烟，注意饮食，坚持正确刷牙，保持口腔卫生，并避免进行剧烈的活动和接触已知的患有传染病的个体。
		</div>
		<div class="consent_text">
			9、我同意在治疗过程中，医生可以照相、录像，以及收集各种有关资料，进行存档及学术研究，不能公开我的患者身份。
		</div>
		<div class="consent_text">
			10、我同意在治疗的过程中与医生完全合作，我已负责地向医生报告了我过去的病史和健康状况，包括了所有的严重问题或损伤情况。并且明白如果不能合作或隐瞒病情，引起的不良后果，由我自己承担。
		</div>
		<div class="consent_text">
			11、我理解口腔医学"种植失败"之定义:在正常行使口腔功能的情况下，所出现的种植体松动、脱离、折断、而需从骨内取出种植体(不包括外伤所致的种植体损害)。在种植失败时，我同意医生可根据情况决定取出种植体及采取必要的治疗措施。
		</div>
		<div class="consent_text">
			12、种植手术完成后，在等待愈合的过程中，当医生发现植体周围出现骨吸收影响到植体的正常愈合，可能会导致种植失败时，我同意医生采取植骨治疗并愿意承担相应的骨粉骨膜费用。同时知悉随之种植后的愈合时间和修复时间会相应延长。
		</div>
		<div class="consent_text">
			13、缺牙区骨缺损严重时，需先行植骨治疗，必要时需取本人自体少量骨（事先征得本人的同意），植骨后有少数人存在失败的风险。在植骨后6个月进行种植时，大多数人仍需要再次少量植骨，我知悉并同意医生采取植骨治疗且愿意承担相应的骨粉骨膜费用。
		</div>
		<div class="consent_text">
			14、戴牙后在保质期内，如因严重骨吸收导致的种植体松动、脱落，医生可根据具体情况决定是否再种植，行再次种植时患者需要承担相应费用。
		</div>
		<!-- 备注 -->
		<div id="consent_remark" style="margin-top:0%;">
			<span>备注:</span><br/>
			<textarea id="remarks" class="height" rows="" cols="" onblur="" style="border: 1px solid #7e7b7b;border-radius: 5px"></textarea>
<%--			<textarea id="remarks" class="height" rows="3" cols="60" onblur="TextLengthCheck(this.id,200);" style="border: 1px solid #7e7b7b;"></textarea>--%>
		</div>
		<div  id="consent_remark_other" style="margin-top:0%;display: none">
			<span style="vertical-align: top;">备注:</span>
			<pre class="remarks" style="border: 1px solid #7e7b7b;width:94%;display: inline-block;"></pre>
		</div>
		<div class="consent_text" style="font-weight: bold;margin-top:2%;">
			<p style="margin-left: 60%;" class="font"><i class="colorRed">*</i>以上情况已知悉并确认签字。</p>
		</div>
		<!-- 手术签名 -->
		<div id="consent_signature" style="border: 1px solid red">
			<!-- 患者签名 -->
			<div class="signature_time" style="margin-top: 1%;">
				<div class="signature_box">
					<span>患者签名:</span>
					<div id="PatientSignature"></div>
				</div>
				<input id="patienttime" type="text" class="consent_time inputheight inputheight2" readonly="readonly" placeholder="请选择日期"/>
			</div>	
			<!-- 医生签名 -->
			<div class="signature_time" style="margin-top: 2%;float:right;">
				<div class="signature_box">
					<span>医生签名:</span>
					<div id="doctorSignature"></div>
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
		var contextPath = "<%=contextPath%>";	
		var id= window.parent.consultSelectPatient.seqid;	//选中患者id
		var order_number= window.parent.consultSelectPatient.orderNumber;//选中患者order_number
		var caseId=""; //已存在的病历id
		var formParentObj;// 父页面的患者信息对象
		var menuid=window.parent.menuid;//左侧菜单id
		$(function(){
			var userAgent = navigator.userAgent; 
		    if (userAgent.indexOf("iPad") > -1){
		     	$("#print_Btn").hide();//移动端打印按钮隐藏
		    }
			//获取当前页面所有按钮
			getButtonAllCurPage(menuid);
			
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
			
		    initZzblInfor();
			 // 2019/7/24 lutian 禁止页面拖拽
			document.ondragstart = function() {
	            return false;
	        };
		    
	 	   $('.selectpicker').selectpicker({});//初始化种植体系下拉框
		});
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
		
		/* 2019/7/22 lutian input文字长度校验方法   obj：元素id  textNum：限制文字长度 */
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
		
		/* 页面赋值判断初始化 */
		function initZzblInfor(){
			//console.log(id+"--------------查询id");
			var url = contextPath + '/HUDH_ZzblAskAct/findFamiliarBookById.act';
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
					caseId=result.seqId;  //病历id
					/* 判断是否已经填写过内容 */
					if(result.seqId){
						$("#consent_saveBtn").css("display","none");//隐藏保存按钮
						$("#consent_updateBtn").css("display","inline-block");//显示修改按钮
						//赋值 
						for(var key in result){
							//console.log(key+"-------------"+result[key]);
							if(result[key]){
								if($("#"+key).attr("type")=="checkbox" && $("#"+key).val()==result[key]){
										$("#"+key).attr("checked","checked");
								}else{
									$("#"+key).attr("value",result[key]);// 填框赋值
								}
							}
							//$("#"+key).attr("value",result[key]);// 填框赋值
							if($("#"+key).attr("contenteditable")=='true'){
								$("#"+key).text(result[key]);// 可编辑span填框赋值
							}
							$("#remarks").text(result["remarks"]);//textarea赋值
							$(".remarks").text(result["remarks"]);//pre赋值
							//常用药物select赋值
							if($("#"+key).find("option")){
								$("#"+key).selectpicker('val', result[key]);
								$("."+key+"Text").text(result[key]+result["plantingsystem"]);
								/* $("#"+key).find("option").each(function(i,obj){
									if($(this).val()==result[key]){
										$(this).prop("selected", true);//针对ipad赋值
										$(this).attr("selected", true);
									}
								}); */
							}
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
						//$("input").attr("disabled","disabled").css("background","transparent");//查看信息的时候禁止在填写
					} 
				}
		  });
		}
		// 备注信息
		function wrappOtherPage(){
			var height=$("#remarks")[0].scrollHeight;
			// console.log(height+'-----height');
			if(height>112){//滚动条高度判断展示区域
				$("#consent_remark").css("display","none");
				$("#consent_remark_other").css("display","block").css("margin-top","155px").css("padding-top","50px");
			}
		}
		/* 打印本页面方法 */
		function myPreviewAll() {
			bdhtml=window.document.body.innerHTML;
			sprnstr="<!--startprint-->";
			eprnstr="<!--endprint-->";
			prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
			prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
			var htmlStyle="<style>button{display:none;}*{font-size: 12px;line-height: 18.5px;}.span{vertical-align:bottom;margin-bottom: 10px;}.margin{margin-top:-10px}#logoImg{text-align:left!important;width:27%!important;left:0%!important;top:17px!important;}.height{height:70%!important;}textarea{overflow-x:hidden;overflow-y:hidden;padding-left:50px!important;}.font{font-size: 16px!important;font-weight: bolder;}.inputheight2{border: 1px solid transparent!important;}.consent_updateBtn{display:none!important;}";
			htmlStyle+="#plantingsystemselect{font-size:12px;height:24px;vertical-align:bottom;}.selectTag{border:none;vertical-align:middle;font-size:12px!important;line-height:12px;appearance:none;-webkit-appearance:none;-moz-appearance:none;}select::-ms-expand{display:none;}";
			htmlStyle+=".plantingsystemselectText{font-size:12px;height:24px;line-height:24px;vertical-align:bottom;margin-bottom:5px;}.selectTag{display:none!important;}.plantingsystemselectText{display:inline-block;}.repairselectText{font-size:12px;height:24px;line-height:24px;vertical-align:bottom;margin-bottom:5px;}.repairselectText{display:inline-block!important;}::-webkit-input-placeholder{color:transparent;}</style>";
			window.document.body.innerHTML=prnhtml+htmlStyle;
			wrappOtherPage();//打印另一个备注
			window.print();  //打印
			document.body.innerHTML=bdhtml; //恢复页面
			window.location.reload();
		};

		function myPreviewAll2(){
			LODOP=getLodop();  
			LODOP.PRINT_INIT("人工种植牙知情同意书");
			//LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");
			var htmlStyle="<style>button{display:none;}*{font-size: 12px;line-height: 18.5px;}.span{vertical-align:bottom;margin-bottom: 10px;}.margin{margin-top:-10px}.height{height:70%!important;}textarea{overflow-x:hidden;overflow-y:hidden;padding-left:50px!important;}.font{font-size: 16px!important;font-weight: bolder;}.inputheight2{border: 1px solid transparent!important;}.consent_updateBtn{display:none!important;}";
			htmlStyle+="#plantingsystemselect{font-size:12px;height:24px;vertical-align:bottom;}.selectTag{border:none;vertical-align:middle;font-size:12px!important;line-height:12px;appearance:none;-webkit-appearance:none;-moz-appearance:none;}select::-ms-expand{display:none;}";
			htmlStyle+=".plantingsystemselectText{font-size:12px;height:24px;line-height:24px;vertical-align:bottom;margin-bottom:5px;}.selectTag{display:none!important;}.plantingsystemselectText{display:inline-block;}</style>";
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
		
		//修改
		function update(){
			//新增参数
			var plantingsystemselect = JSON.stringify($("#plantingsystemselect").val()); //种植体系
			if(!plantingsystemselect || plantingsystemselect=="null"){
				layer.alert("请选择种植系统！");
				return;
			}
			var patient_name = formParentObj.username;//患者姓名
			var patient_sex = formParentObj.sex;//患者性别
			var patient_age = formParentObj.age;//患者年龄
			//var plantingSystem = $("#plantingsystem").text();//种植体系       -----去掉参数
			//var modelNumber = $("#modelnumber").text();//型号	-----去掉参数
			var upleftToothBitOne = $("#uplefttoothbitone").val();
			var uperRightToothBitOne = $("#uperrighttoothbitone").val();
			var leftLowerToothBitOne = $("#leftlowertoothbitone").val();
			var lowRightToothBitOne = $("#lowrighttoothbitone").val();
			var upleftToothBitTwo = $("#uplefttoothbittwo").val();
			var uperRightToothBitTwo = $("#uperrighttoothbittwo").val();
			var leftLowerToothBitTwo = $("#leftlowertoothbittwo").val();
			var lowRightToothBitTwo = $("#lowrighttoothbittwo").val();
			var assistOperation = $("#assistoperation").text();//种植辅助手术
			var remarks = $("#remarks").val();//备注
			var PatientSignature = $("#PatientSignature").val();//患者签字
			var PatientTime = $("#patienttime").val();//患者签字时间
			var doctorSignature = $("#doctorSignature").val();//医生签字
			var doctorTime = $("#doctortime").val();//医生签字时间
			var createtime= new Date().Format("yyyy-MM-dd HH:mm:ss");
			var fixedDenture = "";
			if($("#fixeddenture:checked").val()){
				fixedDenture = $("#fixeddenture:checked").val();
			}
			var removableDenture = "";
			if($("#removabledenture:checked").val()){
				removableDenture = $("#removabledenture:checked").val();
			}

			var url = contextPath + '/HUDH_ZzblAskAct/updateFamiliarBookById.act';
			var param = {
					 seqId : caseId,
					 LcljId :  id,
	        		 LcljNum :  order_number,
	        		 username :patient_name,
		        	 sex : patient_sex,
		        	 age : patient_age,
	        		 plantingSystem :  '',
	        		 modelNumber :  '',
	        		 upleftToothBitOne :  upleftToothBitOne,
	        		 uperRightToothBitOne :  uperRightToothBitOne,
	        		 leftLowerToothBitOne :  leftLowerToothBitOne,
	        		 lowRightToothBitOne :  lowRightToothBitOne,
	        		 upleftToothBitTwo :  upleftToothBitTwo,
	        		 uperRightToothBitTwo :  uperRightToothBitTwo,
	        		 leftLowerToothBitTwo :  leftLowerToothBitTwo,
	        		 lowRightToothBitTwo :  lowRightToothBitTwo,
	        		 assistOperation :  assistOperation,
	        		 remarks :  remarks,
	        		 PatientSignature :  PatientSignature,
	        		 patientTime :  PatientTime,
	        		 doctorSignature :  doctorSignature,
	        		 doctorTime :  doctorTime,
	        		 createtime :  createtime,
	        		 fixedDenture : fixedDenture,
	        		 removableDenture : removableDenture,
	        		 //新增参数
	     			 plantingsystemselect : plantingsystemselect //种植体系
			};
	        
 			//console.log(JSON.stringify(param)+"------------修改参数aa");
	        $.ajax({
				url: url,
				type:"POST",
				dataType:"json",
				data : param,
				success:function(result){
					//console.log(JSON.stringify(result)+"----------------返回内容");
					layer.alert("修改成功！", {
			            end: function() {
			            	//window.parent.location.reload(); //刷新父页面
			                var frameindex = parent.layer.getFrameIndex(window.name);
			                parent.layer.close(frameindex); //再执行关闭
			            }
			      	});
				}
		  });
	        
		}
		
		//保存
		function save() {
			//新增参数
			var plantingsystemselect = JSON.stringify($("#plantingsystemselect").val()); //种植体系
			if(!plantingsystemselect || plantingsystemselect=="null"){
				layer.alert("请选择种植系统！");
				return;
			}
			var patient_name = formParentObj.username;//患者姓名
			var patient_sex = formParentObj.sex;//患者性别
			var patient_age = formParentObj.age;//患者年龄
			//var plantingSystem = $("#plantingsystem").text();//种植体系       -----去掉参数
			//var modelNumber = $("#modelnumber").text();//型号	-----去掉参数
			var upleftToothBitOne = $("#uplefttoothbitone").val();
			var uperRightToothBitOne = $("#uperrighttoothbitone").val();
			var leftLowerToothBitOne = $("#leftlowertoothbitone").val();
			var lowRightToothBitOne = $("#lowrighttoothbitone").val();
			var upleftToothBitTwo = $("#uplefttoothbittwo").val();
			var uperRightToothBitTwo = $("#uperrighttoothbittwo").val();
			var leftLowerToothBitTwo = $("#leftlowertoothbittwo").val();
			var lowRightToothBitTwo = $("#lowrighttoothbittwo").val();
			var assistOperation = $("#assistoperation").text();//种植辅助手术
			var remarks = $("#remarks").val();//备注
			var PatientSignature = $("#PatientSignature").val();//患者签字
			var PatientTime = $("#patienttime").val();//患者签字时间
			var doctorSignature = $("#doctorSignature").val();//医生签字
			var doctorTime = $("#doctortime").val();//医生签字时间
			var createtime= new Date().Format("yyyy-MM-dd HH:mm:ss");
			var fixedDenture = "";
			if($("#fixeddenture:checked").val()){
				fixedDenture = $("#fixeddenture:checked").val();
			}
			var removableDenture = "";
			if($("#removabledenture:checked").val()){
				removableDenture = $("#removabledenture:checked").val();
			}

			var url = contextPath + '/HUDH_ZzblAskAct/saveFamiliarBook.act';
	        var param = {
					 LcljId :  id,
	        		 LcljNum :  order_number,
	        		 username :patient_name,
		        	 sex : patient_sex,
		        	 age : patient_age,
	        		 //plantingSystem :  plantingSystem,
	        		 //modelNumber :  modelNumber,
	        		 upleftToothBitOne :  upleftToothBitOne,
	        		 uperRightToothBitOne :  uperRightToothBitOne,
	        		 leftLowerToothBitOne :  leftLowerToothBitOne,
	        		 lowRightToothBitOne :  lowRightToothBitOne,
	        		 upleftToothBitTwo :  upleftToothBitTwo,
	        		 uperRightToothBitTwo :  uperRightToothBitTwo,
	        		 leftLowerToothBitTwo :  leftLowerToothBitTwo,
	        		 lowRightToothBitTwo :  lowRightToothBitTwo,
	        		 assistOperation :  assistOperation,
	        		 remarks :  remarks,
	        		 PatientSignature :  PatientSignature,
	        		 patientTime :  PatientTime,
	        		 doctorSignature :  doctorSignature,
	        		 doctorTime :  doctorTime,
	        		 createtime :  createtime,
	        		 fixedDenture : fixedDenture,
	        		 removableDenture : removableDenture,
	        		 //新增参数
	     			 plantingsystemselect : plantingsystemselect //种植体系
			};
	        //console.log(JSON.stringify(param)+"---------更新参数");
	        $.ajax({
				url: url,
				type:"POST",
				dataType:"json",
				data : param,
				success:function(result){
					//console.log(JSON.stringify(result)+"----------------返回内容");
					layer.alert("保存成功！", {
			            end: function() {
			                var frameindex = parent.layer.getFrameIndex(window.name);
			                parent.layer.close(frameindex); //再执行关闭
			            }
			      	});
				}
		  });
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
			//console.log(toothString+"------拼接字符串");//拼接字符串
			return toothString;
		};
		
		function getButtonPower() {
		    var menubutton1 = "";
		    for (var i = 0; i < listbutton.length; i++) {
		        if (listbutton[i].qxName == "zsbs_xgbd") {
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