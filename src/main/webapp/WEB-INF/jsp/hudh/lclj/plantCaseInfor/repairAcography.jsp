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
			<h2 class="bigtitle">修复治疗记录 </h2>
		</div>
		<div class="row content">
			<div class="col-md-12 col-sm-12 colDefined">
				<table class="contentItem" border="1" width="100%">
					<tbody id="tbody">
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
							<td><span class="contentName">时间</span></td>
                            <td><span class="contentName">病程</span></td>
							<td colspan="4"><span class="contentName">复查记录</span></td>
							<td><span class="contentName">创建人</span></td>
							<td><span class="contentName">操作</span></td>
						</tr>
					</tbody>			
				</table>
			</div>
		</div>
		<!--endprint-->
		<!-- 按钮 -->
		<div class="btns">
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
		

		//页面数据初始化
		function init() {
			//console.log(selectPatient.usercode+"----------患者编号");
			var param = {
					blCode:selectPatient.usercode
				 };
			var url = contextPath + '/HUDH_DzblAct/findDzblByBlcode.act';
			 $.axseSubmit(url, param,function() {},function(data) {
				 	//console.log(JSON.stringify(data)+"-----------data");
				 var initInfoHtml="";
				 if(data.length>0){
				     for(var i=0;i<data.length;i++){
                         initInfoHtml+='<tr class="detailInfo" seqid='+data[i].seq_id+'>';
                         initInfoHtml+='<td>'+data[i].createtime+'</td>';
                         initInfoHtml+='<td>'+data[i].bc+'</td>';
                         initInfoHtml+='<td colspan="4" style="text-align: left;padding: 10px;">'+data[i].detail+'</td>';
                         initInfoHtml+='<td>'+data[i].username+'</td>';
                         initInfoHtml+='<td><button id="consent_updateBtn" class="consent_updateBtn hidden" onclick="deleteInfo(this);">删除</button></td>';
                         initInfoHtml+='</tr>';
                     }
                     $("#tbody").append(initInfoHtml);
                 }
		        },function(r){
		        	layer.alert("查询失败！");
			    }); 
		}

		//删除行
		function deleteInfo(obj){
			$(obj).parents("tr").remove();
		}
		
		/* 打印本页面方法 */
		function myPreviewAll(){
			$(".consent_updateBtn").css("display","none");
			$("#tbody").find("tr:last").remove();
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
		
		function getButtonPower() {
		    var menubutton1 = "";
		    for (var i = 0; i < listbutton.length; i++) {
		        if (listbutton[i].qxName == "zsbs_xgbd") {
					$(".consent_updateBtn").each(function(i,obj){
						$(this).removeClass("hidden");
					});
		        }
		    }
		    $("#bottomBarDdiv").append(menubutton1);
		}
	</script>
</html>