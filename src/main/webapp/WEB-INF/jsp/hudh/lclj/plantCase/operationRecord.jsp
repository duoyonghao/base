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
	/* 按钮 */
	.btns{
		width:100%;
		text-align:center;
		margin-top: 40px;
		margin-bottom: 20px;
	}
	.btns button{
	   	font-size: 16px;
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
	*{
		margin: 0px;
		padding: 0px;
	}
	#content{
		font-size: 20px;
		font-weight: bold;
		margin-bottom: 20px;
		padding-top: 10px;
	}
	/* 标题 */
	#content>h2{
		display: block;
		width:100%;
		text-align: center;
		font-size: 28px;
   		line-height:100px;
   		letter-spacing: 1px;
   		font-weight: bold;
	}
	/* 详细文字介绍 */
	#content .consent_text{
		width：100%;
		font-size: 18px;
		line-height: 30px;
		font-weight: normal;
	}
	/* 第二条：输入输入框 */
	#content .consent_text .text_input{
		width: 120px;
    	height: 30px;
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
	 	height: 120px;
	 	margin-top: 10px;
	 	margin-bottom: 20px;
	}
	#content #consent_signature>.signature_time{
		width:40%;
		height:100%;
		float:left;
		position: relative;
	}
	#content #consent_signature>.signature_time>.signature_box{
		width:100%;
		height:80px;
	}
	#content #consent_signature>.signature_time>.signature_box>span{
		font-size:18px;
		font-weight: normal;
	}
	/* 时间选择框 */
	#content #consent_signature>.signature_time>input{
		width:35%;
		position: absolute;
		right:10%;
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
</style>

</head>
<body>
	<div id="content" style="padding: 0% 5%;">
		<h2>种植牙手术记录</h2>
		<div class="consent_text" style="line-height: 55px;">
			<ul class="patient_info">
				<li>
					患者姓名:<input id="patient_name" class="text_input" type="text" disabled="disabled"/>
				</li>
				<li>
					性别:<input id="patient_sex" style="width:50px;" class="text_input" type="text" disabled="disabled"/>
				</li>
				<li>
					年龄:<input id="patient_age" style="width:60px;" class="text_input" type="text" disabled="disabled"/>
				</li>
				<li>
					手术日期:<input id="operation_date" class="consent_time" readonly="readonly" placeholder="请选择日期" type="text"/>
				</li>
			</ul>
		</div>
		<div class="consent_text" style="line-height: 55px;background-color: #ddd;" contenteditable="true">
			常规消毒铺巾，在4%必兰麻<input id="blm_milliliter" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>ml局麻下，显效后拔除<input id="remove" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>，彻底清创；沿牙槽嵴顶切开，翻瓣（牙槽嵴顶粘膜环切），
			牙龈厚度<input id="thicknessGum" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>mm,牙槽嵴宽度<input id="alveolarRidgeThickness" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>mm,（骨缺损大小），修平牙槽嵴顶，（行上颌窦内提、外提，植骨粉Bio-oss<input id="plant_bonemeal" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>g,
			盖生物膜、骨挤压、骨劈开），球钻定点，逐级备洞，于<input id="locator" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>处植入植体，其中<input id="kindBone" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>类骨，植入（系统）<input id="plantSystem" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>，
			（型号）<input id="modelNumber" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>，扭力：<input id="twistingForce" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>NCM,(平齐骨面，骨下)，置<input class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>
			mm(愈合基台、复合基台、覆盖螺丝)，（植体与牙槽骨之间共植入骨粉<input id="boneMeal" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>g,覆盖骨膜<input id="coveringPeriosteum" class="text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/>张），稳定性良好，修整并复位软组织，冲洗，
			严密缝合。咬纱卷，手术结束，常规口头及书面医嘱。（重要交待事项：如二期手术期间，过渡期临时牙方式）
		</div>
		<!-- 手术签名 -->
		<div id="consent_signature">
			<!-- 医生签名 -->
			<div class="signature_time" style="float:right;">
				<div class="signature_box">
					<span>手术医生签名:</span>
					<div id="doctorSignature"></div>
				</div>
				<input id="signatureTime" style="line-height: normal;" readonly="readonly" placeholder="请选择日期" type="text"/>
			</div>
		</div>
	</div>
	
	<!-- 按钮 -->
	<div class="btns">
		<button id="consent_saveBtn" onclick="save()">保存</button>
		<button id="print_Btn" onclick="myPreviewAll()">打印本页内容</button>
	</div>
	
	<script language="javascript"  src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
	<script type="text/javascript">
		var contextPath = "<%=contextPath%>";
		var id= window.parent.patientObj.id;	//选中患者id
		var order_number= window.parent.patientObj.orderNumber;//选中患者order_number
		var menuid=window.parent.menuid;//左侧菜单id
		$(function(){
			/* 针对ipad调整样式 */
			var userAgent = navigator.userAgent; 
			if (userAgent.indexOf("iPad") > -1){
				$("#print_Btn").css("display","none");  //隐藏打印按钮
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
			
		    $("#doctorTime").datetimepicker({
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
			$("#patient_name").attr("value",window.parent.patientObj.username);
			$("#patient_sex").attr("value",window.parent.patientObj.sex);
			$("#patient_age").attr("value",window.parent.patientObj.age);
			// 2019/7/24 lutian 禁止页面拖拽
			document.ondragstart = function() {
	            return false;
	        };
			
			
		});
		
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
		
		function save() {
			var patient_name = $("#patient_name").val();//患者姓名
			var patient_sex = $("#patient_sex").val();//患者性别
			var patient_age = $("#patient_age").val();//患者年龄
			var operation_date = $("#operation_date").val();//手术日期
			var blm_milliliter= $("#blm_milliliter").val();//必兰麻毫升数
			var remove = $("#remove").val(); //拔除
			var thicknessGum = $("#thicknessGum").val(); //牙龈厚度
			var alveolarRidgeThickness = $("#alveolarRidgeThickness").val(); //牙槽嵴厚度
			var plant_bonemeal = $("#plant_bonemeal").val();//植骨粉g
			var locator = $("#locator").val();//于。。。处植入植体
			var kindBone = $("#kindBone").val();//。。。类骨
			var plantSystem = $("#plantSystem").val();//系统
			var modelNumber = $("#modelNumber").val();//型号
			var twistingForce = $("#twistingForce").val();//扭力
			var boneMeal = $("#boneMeal").val();// 骨粉
			var coveringPeriosteum = $("#coveringPeriosteum").val();//覆盖骨膜
			var doctorSignature = $("#doctorSignature").val();//医生签名
			var signatureTime = $("#signatureTime").val();//签名时间
			var url = contextPath + '/HUDH_ZzblPlantTeethOperationAct/save.act';
			$.ajax({
				url: url,
				type:"POST",
				dataType:"json",
				data : {
					 id :  id, //临床路径ID
	        		 order_number :  order_number, //节点编号
	        		 username :patient_name,
		        	 sex : patient_sex,
		        	 age : patient_age,
	        		 operation_date :  operation_date, //手术日期
	        		 blm_milliliter : blm_milliliter,//必兰麻毫升数
	        		 remove :  remove, //拔除
	        		 thicknessGum :  thicknessGum, //牙龈厚度
	        		 alveolarRidgeThickness :  alveolarRidgeThickness, //牙槽嵴厚度
	        		 plant_bonemeal : plant_bonemeal,//植骨粉g
	        		 locator :  locator, //于。。。处植入植体
	        		 kindBone :  kindBone, //。。。类骨
	        		 plantSystem :  plantSystem, //系统
	        		 modelNumber :  modelNumber, //型号
	        		 twistingForce :  twistingForce, //扭力
	        		 boneMeal :  boneMeal, // 骨粉
	        		 coveringPeriosteum :  coveringPeriosteum, //覆盖骨膜
	        		 doctorSignature :  doctorSignature, //医生签名
	        		 signatureTime :  signatureTime //签名时间
				},
				success:function(result){
					layer.alert('提交成功', function(index) {
						layer.close(index);
						location.reload();
					})
					/* if (result.retState == "0") {
						layer.alert('提交成功', function(index) {
							layer.close(index);
							location.reload();
						})
			        } else {
				        layer.alert('提交失败：' + result.retMsrg, {//后台抛出的异常信息在前台展示
				            end: function() {
				            	layer.close(index);
				            	location.reload();
				            }
				        });
			        } */
				}
		  });
	        /* var param = {
	        		 id :  LcljId, //临床路径ID
	        		 order_number :  JdId, //节点编号
	        		 operation_date :  operation_date, //手术日期
	        		 blm_milliliter : blm_milliliter,//必兰麻毫升数
	        		 remove :  remove, //拔除
	        		 thicknessGum :  thicknessGum, //牙龈厚度
	        		 alveolarRidgeThickness :  alveolarRidgeThickness, //牙槽嵴厚度
	        		 plant_bonemeal : plant_bonemeal,//植骨粉g
	        		 locator :  locator, //于。。。处植入植体
	        		 kindBone :  kindBone, //。。。类骨
	        		 plantSystem :  plantSystem, //系统
	        		 modelNumber :  modelNumber, //型号
	        		 twistingForce :  twistingForce, //扭力
	        		 boneMeal :  boneMeal, // 骨粉
	        		 coveringPeriosteum :  coveringPeriosteum, //覆盖骨膜
	        		 doctorSignature :  doctorSignature, //医生签名
	        		 signatureTime :  signatureTime //签名时间
	        };
	        $.axseSubmit(url, param,function() {},function() {},function(r){
	        	layer.alert("保存成功！", {
		            end: function() {
		            	window.parent.location.reload(); //刷新父页面
		                var frameindex = parent.layer.getFrameIndex(window.name);
		                parent.layer.close(frameindex); //再执行关闭
		            }
		      	});
		    }); */
		}
	
		/* 打印页面方法 */
		function myPreviewAll(){
			LODOP=getLodop();  
			LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_完整全页");
			var htmlStyle="<style>button{display:none;}</style>";
			var html="<!DOCTYPE html>"+document.getElementsByTagName("html")[0].innerHTML+htmlStyle;
			LODOP.ADD_PRINT_HTM(10,10,"100%","100%",html);
			LODOP.PREVIEW();	
		};
	</script>
</body>
</html>