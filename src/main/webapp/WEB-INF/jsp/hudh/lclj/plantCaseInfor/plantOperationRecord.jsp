<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//种植医生
	String plantPhysician = request.getParameter("plantPhysician");
	//诊室护士
	String clinicNurse = request.getParameter("clinicNurse");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
</head>
<style id="styleA" type="text/css">
	*{
		margin: 0px;
		padding: 0px;
		font-size: 18px;
		line-height: 26px;
	}
	#content{
		margin-bottom: 20px;
		padding-top: 10px;
	}
	/* 输入框 */
	#content input[type="text"]{
		font-weight: bold;
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
	.content .colDefined .contentItem input[type='text']{
		width:80px; 
	 	font-size: 14px; 
	 	line-height: 20px; 
	 	border: 0px;
	 	border-bottom:1px solid #000; 
	 	margin-right: 5px; 
	}
	.content .colDefined .contentItem tbody tr td{
		width:12.5%;
		text-align: center;
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
		height:60%;
		float:left;
		position: relative;
	}
	#content #consent_signature>.signature_time>.signature_box{
		width:100%;
		height:40px;
	}
	#content #consent_signature>.signature_time>.signature_box>span{
		font-weight: normal;
	}
	/* 时间选择框 */
	#content #consent_signature>.signature_time>input{
		width:50%;
		position: absolute;
		right:0px;
		bottom: 0px;
		text-align: center;
	}
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
	h4{
		font-weight: bold;
		text-indent: 2rem;
		line-height: 30px;
		margin-top: 0px; 
        margin-bottom: 0px;
	}
	.consent_time{
		width: 135px !important;
		height: 24px;
	    background-color: transparent;
	    border: 1px solid #c3c3c3 !important;
	    vertical-align: middle;
	    -webkit-appearance: none;
	    outline: none;
	    text-align: left !important;
	    font-weight: bold !important;
	    text-align: left;
	    padding-left: 5px;
	    font-size: 15px !important;
	}
	.infoText{
		font-weight: bold;
	}
	label {
		font-weight: normal;
	}
	textarea{
		border: 1px solid red;
	    display: block;
	    width: 100%;
	    height: 100%;
	}
	#logoImg{
		position: absolute;
		top: 35px;
		width: 150px;
		height: 45px;
	}
	@page{
		size: auto;
		margin: 0mm auto;
	}
</style>
<body style="padding: 0px 3%;">
<!--startprint-->
	<div id="content">
		<div class="col-md-12 col-sm-12 colDefined" style="position: relative;">
			<img id="logoImg" src="http://www.hdbkq.cn/templets/hdb/new_header_img/hud_logo.png">
			<h2 class="bigtitle">种植牙手术记录</h2>
		</div>
		<div class="row content">
	<!-- 患者详情 -->
			<div class="col-md-12 col-sm-12 colDefined">
				<table class="contentItem" border="1" width="100%">					
					<tbody>
						<tr>
							<td><span>患者编号</span></td>
							<td class="infoText"><font id="patient_num"></font></td>
							<td><span>患者姓名</span></td>	
							<td class="infoText"><font id="patient_name"></font></td>
							<td><span>性别</span></td>	
							<td class="infoText"><font id="patient_sex"></font></td>
							<td><span>年龄</span></td>	
							<td class="infoText"><font id="patient_age"></font></td>
						</tr>
						<tr>
							<td><span>手术医生</span></td>
							<td class="infoText"><font id="patient_plantdoctor"></font></td>
							<td><span>配台护士</span></td>
							<td class="infoText"><font id="patient_nurse"></font></td>
							<td colspan="2"><span>手术日期</span></td>	
							<td colspan="2"><input id="operationdate" class="consent_time" type="text" placeholder="请选择日期"/></td>
						</tr>
						<tr>
							<td><span>局麻药物</span></td>
							<td colspan="11">
								<input id="anesthesiarticaine" name="localanesthesia" class="" type="radio" value="0"/><label for="anesthesiarticaine" class="contentName">4%阿替卡因 </label>&nbsp&nbsp
								<input id="anesthesialidocaine" name="localanesthesia" class="" type="radio" value="1"/><label for="anesthesialidocaine" class="contentName">利多卡因</label>
							</td>							
						</tr>
						<tr>
							<td><span>操<br/>作<br/>记<br/>录</span></td>
							<td colspan="11" class="infoText">
								<textarea id="operatingrecord" class="form-control" style="width: 100%; height: 510px;overflow: auto;word-break: break-all; resize: none;"></textarea>
							</td>							
						</tr>
						<tr>
							<td><span>种<br/>植<br/>体<br/>条<br/>码 </span></td>
							<td colspan="11" class="infoText">
								<textarea id="implantbarcode" class="form-control" style="width: 100%; height: 130px ;overflow: auto;word-break: break-all; resize: none;"></textarea>
							</td>							
						</tr>
						<tr>
							<td><span>骨<br/>粉<br/>骨<br/>膜<br/>条<br/>码</span></td>
							<td colspan="11" class="infoText">
								<textarea id="periostbarcode" class="form-control" style="width: 100%; height: 155px ;overflow: auto;word-break: break-all; resize: none;"></textarea>
							</td>								
						</tr>	
					</tbody>			
				</table>
			</div>
			<!-- 签名 -->
		<div class="signature_time" style="float:right;">
				<div class="signature_box">
					<span id="doctorSignature">手术医生签字：</span>
					<img id="img" style="width:156px;height: 43px;"/>
				</div>
				<input id="doctortime" type="text" class="consent_time" readonly="readonly" placeholder="请选择日期"/>
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
		var signature="";//医生签字字段
		var doctorstatus=true;
		var contextPath = "<%=contextPath%>";	
  		var id= window.parent.consultSelectPatient.seqid;	//临床路径id
 		var order_number= window.parent.consultSelectPatient.orderNumber;//临床路径编号
		var selectPatient= window.parent.consultSelectPatient;//选中患者 lutian
		var plantPhysician="<%=plantPhysician%>"; //种植医生 lutian
		var clinicNurse="<%=clinicNurse%>"; //诊室护士 lutian
		var userseqid; //患者seqid
		var menuid=window.parent.menuid;//左侧菜单id
		var alreadyCaseId; //已创建后的病历id
		$(function(){
			//时间选择
		    $(".consent_time").datetimepicker({
		        language:  'zh-CN',  
		   		minView: 2,
		        format: 'yyyy-mm-dd',
		   		autoclose : true,//选中之后自动隐藏日期选择框   
		   		todayBtn: true,
			   	beforeShow: function () {
			         setTimeout(
				         function () {
				             $('#ui-datepicker-div').css("z-index", 21);
				         }, 100
			         );
			    }
		    });
			//console.log(JSON.stringify(selectPatient)+"---------选中患者信息");
			
			 // 2019/7/24 lutian 禁止页面拖拽
			document.ondragstart = function() {
	            return false;
	        };
		    /* 初始化患者基本信息 */
		    initPatientInfo(selectPatient.usercode);
			$("#patient_plantdoctor").text(plantPhysician);  //手术医生
			$("#patient_nurse").text(clinicNurse); //配台护士
			init();//页面数据初始化
			//获取当前页面所有按钮
			getButtonAllCurPage(menuid);
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
		             $("#patient_num").text(r.usercode);//患者编号
		             $("#patient_name").text(r.username);//患者姓名
		             $("#patient_sex").text(r.sex);//患者性别
		             $("#patient_age").text(r.age);//患者年龄
		             userseqid=r.seq_id;
		         }
		     });
		}
		// 页面信息初始化
		function init() {
			 var param = {
					lcljId:id
			 };
			 var url = contextPath + '/HUDH_MedicalRecordsAct/selectRecord.act';
			$.axseSubmit(url, param,function() {},function(data) {
	        	//console.log(JSON.stringify(data)+"------------页面返回数据");
	        	if(data.length>0){
	        		alreadyCaseId=data[0]["seq_id"]; //已存在病历id
	        		$("#consent_saveBtn").css("display","none");  //隐藏保存按钮
	        		$("#consent_updateBtn").css("display","inline-block");  //显示修改按钮
	        		signature=data[0].doctorname;
					 if(signature!=""){
						 $("#img").attr('src', signature);
						 doctorstatus=false;
					 }else{
						 $("#img").attr('display', 'none');
					 }
	        		for(var key in data[0]){
	        			$("#"+key+"[type='text']").attr("value",data[0][key]);
	        			$("#"+key).text(data[0][key]);
	        			var anesthesia=data[0]["localanesthesia"];
	        			if(anesthesia){
	        				$("input[name='localanesthesia'][value="+anesthesia+"]").attr("checked",true);
	        			}
	        		} 
	        		
	        	}
	        },function(r){
	        	layer.alert("查询失败！");
		    }); 
		}
		
		/* 保存 lutian 2020/05/29 */
		function save(){
			var patient_num = $("#patient_num").text();//患者编号
			var patient_name = $("#patient_name").text();//患者姓名
			var patient_sex = $("#patient_sex").text();//患者性别
			var patient_age = $("#patient_age").text();//患者年龄
			var patient_plantdoctor = $("#patient_plantdoctor").text();//手术医生
			var patient_nurse = $("#patient_nurse").text();//配台护士
			var operationdate = $("#operationdate").val();//手术日期
			var localanesthesia = $("input[name='localanesthesia']:checked").val();//局部麻物
			var operatingrecord = $("#operatingrecord").val();//操作记录
			var implantbarcode = $("#implantbarcode").val();//种植体条码
			var periostbarcode = $("#periostbarcode").val();//骨粉骨膜条码
			var doctortime = $("#doctortime").val();//医生签名
			
			var url = contextPath + '/HUDH_MedicalRecordsAct/insertRecord.act';
	        var param = {
	        		 lcljId :  id,
	        		 lcljNum :  order_number,
	        		 userid : userseqid,
	        		 usernum : patient_num,
	        		 username :patient_name,
		        	 sex : patient_sex,
		        	 age : patient_age,
		        	 plantdoctor : patient_plantdoctor,
		        	 nurse : patient_nurse,
		        	 operationdate : operationdate,
		        	 localanesthesia : localanesthesia,
		        	 operatingrecord : operatingrecord,
		        	 implantbarcode : implantbarcode,
		        	 periostbarcode : periostbarcode,
		        	 doctorname :  signature, //医生签字
		        	 doctortime : doctortime
	        };
	        
	        //console.log(JSON.stringify(param)+"------------保存传参");
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
		
		//修改
		function update(){
			var patient_num = $("#patient_num").text();//患者编号
			var patient_name = $("#patient_name").text();//患者姓名
			var patient_sex = $("#patient_sex").text();//患者性别
			var patient_age = $("#patient_age").text();//患者年龄
			var patient_plantdoctor = $("#patient_plantdoctor").text();//手术医生
			var patient_nurse = $("#patient_nurse").text();//配台护士
			var operationdate = $("#operationdate").val();//手术日期
			var localanesthesia = $("input[name='localanesthesia']:checked").val();//局部麻物
			var operatingrecord = $("#operatingrecord").val();//操作记录
			var implantbarcode = $("#implantbarcode").val();//种植体条码
			var periostbarcode = $("#periostbarcode").val();//骨粉骨膜条码
			var doctortime = $("#doctortime").val();//医生签名
			
			var url = contextPath + '/HUDH_MedicalRecordsAct/insertRecord.act';
	        var param = {
	        		 lcljId :  id,
	        		 lcljNum :  order_number,
	        		 seqId : alreadyCaseId,
	        		 userid : userseqid,
	        		 usernum : patient_num,
	        		 username :patient_name,
		        	 sex : patient_sex,
		        	 age : patient_age,
		        	 plantdoctor : patient_plantdoctor,
		        	 nurse : patient_nurse,
		        	 operationdate : operationdate,
		        	 localanesthesia : localanesthesia,
		        	 operatingrecord : operatingrecord,
		        	 implantbarcode : implantbarcode,
		        	 periostbarcode : periostbarcode,
		        	 doctorname :  signature, //医生签字
		        	 doctortime : doctortime
	        };
	        
	        //console.log(JSON.stringify(param)+"------------修改传参");
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
		
		/* *********************************** */
		function getButtonPower() {
		    var menubutton1 = "";
		    for (var i = 0; i < listbutton.length; i++) {
		        if (listbutton[i].qxName == "zsbs_xgbd" && doctorstatus) {
		           $("#consent_updateBtn").removeClass("hidden");
		        }
		    }
		    $("#bottomBarDdiv").append(menubutton1);
		}				
		/* 打印本页面方法 */
		function myPreviewAll(){
			bdhtml=window.document.body.innerHTML;
			sprnstr="<!--startprint-->";
			eprnstr="<!--endprint-->";
			prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
			prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
			var htmlStyle="<style>button{display:none;}*{font-size: 12px;line-height: 24px;}#logoImg{top: -5px;}</style>";
			window.document.body.innerHTML=prnhtml+htmlStyle;
			window.print();  //打印
			window.document.body.innerHTML=bdhtml; // 恢复页面
		};
		

	</script>
</html>