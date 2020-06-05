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
<%-- <script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script> --%>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
</head>
<style id="styleA" type="text/css">
	*{
		margin: 0px;
		padding: 0px;
		font-size: 18px;
		line-height: 30px; 
	}
	#content{
		/* font-weight: bold; */
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
	.content .colDefined .contentItem thead tr{
		height: 30px;
		text-align: center;
	}
	.content .colDefined .contentItem tbody tr{
		text-align: center;
	}
	.content .colDefined .contentItem tbody tr td{
		text-align: center;
		width:12.5%;
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
	.detailInfo td{
		height: 120px;
	}
	.infoText{
		font-weight: bold;
	}
	.consent_updateBtn{
		font-size: 16px;
	    line-height: 34px;
	    background-color: #00A6C0;
	    font-weight: normal;
	    color: white;
	    border: 0px;
	    border-radius: 5px;
	    padding: 0px 10px;
	    letter-spacing: 1px;
	    margin: 0 auto;
	}
</style>
<body style="padding: 0px 3%;">
	<div id="content">
		<h2 class="bigtitle">修复治疗记录 </h2>	
		<div class="row content">
			<div class="col-md-12 col-sm-12 colDefined">
				<table class="contentItem" border="1" width="100%">
					<tbody id="tbody">
						<tr>
							<td><span>患者编号</span></td>
							<td colspan="2" class="infoText"><font id="patient_num"></font></td>
							<td><span>患者姓名</span></td>	
							<td class="infoText"><font id="patient_name"></font></td>
							<td><span>性别</span></td>	
							<td class="infoText"><font id="patient_sex"></font></td>
							<td><span>年龄</span></td>	
							<td class="infoText"><font id="patient_age"></font></td>
						</tr>
						<tr>
							<td colspan="2"><span class="contentName">时间</span></td>
							<td colspan="4"><span class="contentName">复查记录</span></td>
							<td colspan="2"><span class="contentName">医生名称</span></td>
							<td><span class="contentName">操作</span></td>
						</tr>
						<!-- <tr class="detailInfo">
							<td colspan="2">
								<input id="" class="consent_time writetime" type="text" placeholder="请选择日期"/>
							</td>
							<td colspan="4">
								<textarea id="" class="form-control reviewrecord" style="width: 100%; height: 120px ;overflow: auto;word-break: break-all; resize: none;"></textarea>
							</td>
							<td colspan="2">
								<textarea id="" class="form-control doctorname" style="width: 100%; height: 120px ;overflow: auto;word-break: break-all; resize: none;"></textarea>
							</td>
							<td>
								<button id="consent_updateBtn" style="display: none;" class="consent_updateBtn hidden" onclick="update(this)">修改此条记录</button>
							</td>							
						</tr> --> 
					</tbody>			
				</table>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="btns">
			<button id="consent_saveBtn" onclick="save()">保存</button>
			<button id="print_Btn" onclick="myPreviewAll()">打印本页内容</button>
		</div>
	</div>
	
	
</body>
	<script language="javascript"  src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
	<script type="text/javascript">
		var contextPath = "<%=contextPath%>";	
  		var id= window.parent.consultSelectPatient.seqid;	//选中患者id
 		var order_number= window.parent.consultSelectPatient.orderNumber;//选中患者order_number
		var selectPatient= window.parent.consultSelectPatient;//选中患者 lutian
		var userseqid; //患者seqid
		var menuid=window.parent.menuid;//左侧菜单id
		$(function(){
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
			//console.log(JSON.stringify(selectPatient)+"----------selectPatient");
			/* 初始化患者基本信息 */
		    initPatientInfo(selectPatient.usercode);
			
 		    init();//页面数据初始化
			// 2019/7/24 lutian 禁止页面拖拽
			document.ondragstart = function() {
	            return false;
	        };
	        
	      	//获取当前页面所有按钮
			getButtonAllCurPage(menuid);
			$("#consent_updateBtn").css("display","inline-block");//显示修改按钮
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
		             $("#patient_num").text(r.usercode);//患者编号
		             $("#patient_name").text(r.username);//患者姓名
		             $("#patient_sex").text(r.sex);//患者性别
		             $("#patient_age").text(r.age);//患者年龄
		             userseqid=r.seq_id;
		         }
		     });
		}
		
		//追加输入
		function appendWrite(){
			var writeHtml='<tr class="detailInfo">';
			writeHtml+='<td colspan="2">';
			writeHtml+='<input id="" class="consent_time writetime" type="text" placeholder="请选择日期"/>';
			writeHtml+='</td>';
			writeHtml+='<td colspan="4">';
			writeHtml+='<textarea id="" class="form-control reviewrecord" style="width: 100%; height: 120px ;overflow: auto;word-break: break-all; resize: none;"></textarea>';
			writeHtml+='</td>';
			writeHtml+='<td colspan="2">';
			writeHtml+='<textarea id="" class="form-control doctorname" style="width: 100%; height: 120px ;overflow: auto;word-break: break-all; resize: none;"></textarea>';
			writeHtml+='</td>';						
			writeHtml+='<td>';
			writeHtml+='<button id="consent_updateBtn" style="display: none;" class="consent_updateBtn hidden" onclick="update(this)">修改此条记录</button>';
			writeHtml+='</td>';
			writeHtml+='</tr>';
			$("#tbody").append(writeHtml);
			
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
		}
		
		//修改单行数据
		function update(obj){
			var patient_num = $("#patient_num").text();//患者编号
			var patient_name = $("#patient_name").text();//患者姓名
			var patient_sex = $("#patient_sex").text();//患者性别
			var patient_age = $("#patient_age").text();//患者年龄
			var seqid = $(obj).parents(".detailInfo").attr("seqid"); //患者seqid
			var writetime=$(obj).parents(".detailInfo").find(".writetime").val();//时间
			var reviewrecord=$(obj).parents(".detailInfo").find(".reviewrecord").val();//复查记录
			var doctorname=$(obj).parents(".detailInfo").find(".doctorname").val();//医生名称
			if(writetime=="" || reviewrecord=="" || doctorname==""){
				layer.alert("请填写复查时间、复查记录、医生名称！");
				return;
			}
			var url = contextPath + '/HUDH_MedicalRecordsAct/installOrUpdate.act';
	        var param = {
	        		 lcljId :  id,
	        		 lcljNum :  order_number,
	        		 seqId : seqid,
	        		 userId : userseqid,
	        		 usernum : patient_num,
	        		 username :patient_name,
		        	 sex : patient_sex,
		        	 age : patient_age,
		        	 writetime : writetime,
		        	 reviewrecord:reviewrecord,
		        	 doctorname:doctorname
	        };
	        
	        //console.log(JSON.stringify(param)+"------------修改传参");
	        $.axseSubmit(url, param,function() {},function(r) {
	        	layer.alert("保存成功！", {
		            end: function() {
		            	//保存成功父页面选中该项
		            	//window.parent.document.getElementById("ask_Previous").prev().attr("checked","checked");
		                var frameindex = parent.layer.getFrameIndex(window.name);
		                //parent.layer.close(frameindex); //再执行关闭
		            }
		      	});
	        },function(r){
	        	layer.alert("保存失败！");
		    });
			
		}
		
		//页面数据初始化
		function init() {
			var param = {
						lcljId:id
				 };
			var url = contextPath + '/HUDH_MedicalRecordsAct/selectAcography.act';
			 $.axseSubmit(url, param,function() {},function(data) {
				 	//console.log(JSON.stringify(data)+"-----------data");
		        	if(data.length>0){
		        		var initInfoHtml="";
		        		for(var i=0;i<data.length;i++){
		        			initInfoHtml+='<tr class="detailInfo" seqid='+data[i].seq_id+'>';
		        			initInfoHtml+='<td colspan="2">';
		        			initInfoHtml+='<input class="consent_time writetime" type="text" placeholder="请选择日期" value='+data[i].writetime+' >';
		        			initInfoHtml+='</td>';
		        			initInfoHtml+='<td colspan="4">';
		        			initInfoHtml+='<textarea class="form-control reviewrecord" style="width: 100%; height: 120px ;overflow: auto;word-break: break-all; resize: none;">'+data[i].reviewrecord+'</textarea>';
		        			initInfoHtml+='</td>';
		        			initInfoHtml+='<td colspan="2">';
		        			initInfoHtml+='<textarea class="form-control doctorname" style="width: 100%; height: 120px ;overflow: auto;word-break: break-all; resize: none;">'+data[i].doctorname+'</textarea>';
		        			initInfoHtml+='</td>';						
		        			initInfoHtml+='<td>';
		        			initInfoHtml+='<button id="consent_updateBtn" style="display: block;" class="consent_updateBtn hidden" onclick="update(this)">修改此条记录</button>';
		        			initInfoHtml+='</td>';
		        			initInfoHtml+='</tr>';
		        		}
		        		$("#tbody").append(initInfoHtml);
		        	}
		        	appendWrite(); //追加输入
		        },function(r){
		        	layer.alert("查询失败！");
			    }); 
		}
		
		//保存方法
		function save(){
			var patient_num = $("#patient_num").text();//患者编号
			var patient_name = $("#patient_name").text();//患者姓名
			var patient_sex = $("#patient_sex").text();//患者性别
			var patient_age = $("#patient_age").text();//患者年龄
			var trlength=$("#tbody").find("tr").length; 
			var writetime =$("#tbody").find("tr").eq(trlength-1).find(".writetime").val();//时间
			var reviewrecord = $("#tbody").find("tr").eq(trlength-1).find(".reviewrecord").val();//复查记录
			var doctorname = $("#tbody").find("tr").eq(trlength-1).find(".doctorname").val();//医生名称
			if(writetime=="" || reviewrecord=="" || doctorname==""){
				layer.alert("请填写复查时间、复查记录、医生名称！");
				return;
			}
			var url = contextPath + '/HUDH_MedicalRecordsAct/installOrUpdate.act';
	        var param = {
	        		 lcljId :  id,
	        		 lcljNum :  order_number,
	        		 userId : userseqid,
	        		 usernum : patient_num,
	        		 username :patient_name,
		        	 sex : patient_sex,
		        	 age : patient_age,
		        	 writetime : writetime,
		        	 reviewrecord:reviewrecord,
		        	 doctorname:doctorname
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
		
		/* 打印本页面方法 */
		function myPreviewAll(){
			LODOP=getLodop();  
			LODOP.PRINT_INIT("人工种植牙知情同意书");
			LODOP.SET_PRINT_PAGESIZE(1,2100,2970,"A4");
			var htmlStyle="<style>button{display:none;}*{font-size: 12px;line-height: 24px;}</style>";
			var html="<!DOCTYPE html>"+document.getElementsByTagName("html")[0].innerHTML+htmlStyle;
			LODOP.ADD_PRINT_HTM(0,10,"100%","100%",html);
			LODOP.PREVIEW();	
		};
		
		function getButtonPower() {
		    var menubutton1 = "";
		    for (var i = 0; i < listbutton.length; i++) {
		        if (listbutton[i].qxName == "zsbs_xgbd") {
		           //$("#consent_updateBtn").removeClass("hidden");
		           $(".consent_updateBtn").each(function(i,obj){
		        	   $(this).removeClass("hidden");
		           });
		        }
		    }
		    $("#bottomBarDdiv").append(menubutton1);
		}
	</script>
</html>