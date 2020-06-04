<%@page import="java.io.Console"%>
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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<%-- <script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script> --%>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
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
.container-fluid{
	width:100%;
	font-family："微软雅黑";
/* 	border:1px solid black; */
}
.colDefined{
	padding:0px;
}
.row{
	margin: 0px;
}
ul, ol{
	margin:0px;
}
/* 大标题 */
.container-fluid .bigtitle{
	display: block;
	width:100%;
	text-align: center;
	font-size: 26px;
	line-height: 26px;
  	margin: 45px auto 35px;
  	letter-spacing: 1px;
  	font-weight: bold;
}
/* 小标题 */
.container-fluid .smalltitle{
	display: block;
	width:100%;
	font-size: 16px;
	line-height: 32px;
  	letter-spacing: 1px;
  	font-weight: bold;
/*   	border:1px solid red; */
}
.container-fluid .smalltitle>font{
	font-size:24px;
	margin-right: 10px;
	margin-left: 8px;
}
/* 基本信息 */
.baseInfo{
	width:100%;
/* 	border:1px solid blue; */
	border-bottom: 1px solid black;
	padding-left: 8px;
}
.baseInfo .patient{
	overflow: hidden;
}
/* 输入组合框 */
.baseInfo .patient>.inputDiv{
	display:block;
	float:left;
}
.baseInfo .patient>.inputDiv>span{
	display: inline-block;
	font-size: 16px;
	line-height: 22px;
	height: 22px;
}
.baseInfo .patient>.inputDiv>input{
 	border:none; 
    width: 100px;
    height: 22px;
}
/* 内容样式 */
.content .colDefined .contentItem tr{
	height: 25px;
	text-align: center;
}
 .content .colDefined .contentItem tr td input{ 
	width:20px; 
 	font-size: 14px; 
 	line-height: 20px; 
	border: none;  
 	margin-right: 5px; 
	text-align: center;
 } 
/* 签名 */
#content #consent_signature{
 	overflow: hidden;
 	margin-top: 10px;
 	margin-bottom: 20px;
}
#content #consent_signature>.signature_time>.signature_box{
/* 	width:100%; */
	height:80px;
}
#content #consent_signature>.signature_time>.signature_box>span{
	font-weight: normal;
}
/* 时间选择框 */
#content #consent_signature>.signature_time>input{
	width:20%;
	position: absolute;
	right:0px;
	bottom: 0px;
	text-align: center;
	margin-bottom: 40px;
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
.site{
	margin-left: 75%;
    width: 100px;
    text-align: center;
    border:none;
}
</style>
<body>
	<div id="content" class="container-fluid">
		<!-- 标题 -->
<!-- 		<div class="row"> -->
<!-- 			<div class="col-md-12 col-sm-12 colDefined"> -->
<!-- 				<span class="bigtitle">种植病历</span> -->
<!-- 			</div> -->
<!-- 		</div>	 -->
		<!-- 基本信息 -->
		<div class="row">
			<div class="col-md-12 col-sm-12 colDefined">
				<span class="smalltitle"><font>●</font>基本信息</span>
			</div>
		</div>
		<div class="row baseInfo">
			<div class="col-md-12 col-sm-12 colDefined">
				<!-- 患者基本信息 -->
				<div class="patient" style="width:85%;float:left;">
					<div class="inputDiv">
						<span>首诊时间：</span><input type="text" />
					</div>
					<div class="inputDiv">
						<span>患者编号：</span><input type="text" />
					</div>
					<div class="inputDiv" style="clear:left;">
						<span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span><input type="text" />
					</div>
					<div class="inputDiv">
						<span>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span><input type="text" />
					</div>
					<div class="inputDiv">
						<span>&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄：</span><input type="text" />
					</div>
					<div class="inputDiv" style="clear:left;">
						<span>证件号码：</span><input type="text" />
					</div>
					<div class="inputDiv">
						<span>出生年月：</span><input type="text" />
					</div>
					<div class="inputDiv" style="clear:left;">
						<span>联系电话：</span><input type="text" />
					</div>
					<div class="inputDiv" >
						<span>紧急联系人：</span><input type="text" />
					</div>
					<div class="inputDiv">
						<span>紧急联系人电话：</span><input type="text" />
					</div>
					<div class="inputDiv" style="clear:left;">
						<span>现居住地址：</span><input type="text" />
					</div>
				</div>
				<div class="patientHeader" style="width:15%;float:left;">
					<img src="<%=contextPath%>/static/image/kqdsFront/jiagong/headImg.jpg">
					<input style="display:none;" type="file" />
				</div>
			</div>
			<!-- 头像 -->
			<%-- <div class="col-md-6 col-sm-6 colDefined patientHeader">
				<img src="<%=contextPath%>/static/image/image/headImg.jpg">
				<input style="display:none;" type="file" />
			</div> --%>
		</div>	
		<!-- 主诉content -->
		<div class="row">
			<div class="col-md-12 col-sm-12 colDefined">
				<span class="smalltitle"><font>●</font>主诉</span>
			</div>
		</div>
		<div class="row content">
			<div class="col-md-12 col-sm-12 colDefined">
				<table class="contentItem" border="1" width="100%">
					<!-- 主诉content -->
					<tr>
						<td><input class="" type="checkbox"/><span class="">牙缺失</span></td>
						<td><input class="" type="checkbox"/><span class="">牙松动</span></td>
						<td><input class="" type="checkbox"/><span class="">龋坏</span></td>
						<td><input class="" type="checkbox"/><span class="">牙折断</span></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td><span class="duration">时长</span><input class="yearNum" type="text"/>年<input class="dateNum" type="text"/>月</td>
						<td><span class="duration">时长</span><input class="yearNum" type="text"/>年<input class="dateNum" type="text"/>月</td>
						<td><span class="duration">时长</span><input class="yearNum" type="text"/>年<input class="dateNum" type="text"/>月</td>
						<td><span class="duration">时长</span><input class="yearNum" type="text"/>年<input class="dateNum" type="text"/>月</td>
					</tr>
					<tr>
						<td>是否要求种植修复</td>
						<td colspan="3"><input class="" type="checkbox"/><span class="">是</span>
						<input class="" type="checkbox"/><span class="">否</span></td>
					</tr>					
				</table>
			</div>
		</div>
		<!--既往史-->
		<div class="row">
			<div class="col-md-12 col-sm-12 colDefined">
				<span class="smalltitle"><font>●</font>既往史</span>
			</div>
		</div>
		<div class="row content">
			<div class="col-md-12 col-sm-12 colDefined">
				<table class="contentItem" border="1" width="100%">
					<!--既往史-->
					<tr>
						<td><span class="">高血压</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><input class="" type="text"/><span class="">年</span></td>
						<td><span class="">是否服药</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><span class="">目前血压</span></td>
						<td><input class="" type="text"/></td>
					</tr>
					<tr>
						<td><span class="">心脏疾病</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td>
						<input class="" type="text"/><span class="">病</span>
						</td>
						<td colspan="2"><span class="">是否常备药在身边</span></td>
						<td colspan="2">
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>						
					</tr>
					<tr>
						<td><span class="">糖尿病</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><input class="" type="text"/><span class="">年</span></td>
						<td><span class="">控制方式</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">饮食</span>
						</td>
						<td colspan="2">
							<input class="" type="checkbox"/><span class="">口服药</span>
							<input class="" type="checkbox"/><span class="">针剂</span>
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<span class="">六个月内做过心脏手术</span>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td colspan="4">
							<span class="">六个月内发生心梗</span>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
					</tr>
					<tr>
						<td colspan="5"><span class="">凝血功能不足性疾病（血友病、再障、血小板减少症、急性白血病）</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><input class="" type="text"/><span class="">年</span></td>
					</tr>	
					<tr>
						<td colspan="5"><span class="">服用抗凝药物或其他可以导致凝血功能障碍的药物：</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><input class="" type="text"/><span class="">年</span></td>
					</tr>
					<tr>
						<td><span class="">乙肝</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><input class="" type="text"/><span class="">年</span></td>
						<td colspan="2"><span class="">丙肝</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><input class="" type="text"/><span class="">年</span></td>
					</tr>	
					<tr>
						<td><span class="">HIV</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><input class="" type="text"/><span class="">年</span></td>
						<td colspan="2"><span class="">梅毒</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><input class="" type="text"/><span class="">年</span></td>
					</tr>	
					<tr>
						<td><span class="">恶性肿瘤/癌症</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><input class="" type="text"/><span class="">年</span></td>
						<td colspan="2"><span class="">颌面部放疗术后</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><input class="" type="text"/><span class="">年</span></td>
					</tr>		
					<tr>
						<td rowspan="2"><span class="">任何感染的急 性炎症期</span></td>
						<td rowspan="2">
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td rowspan="2"><input class="" type="text"/><span class="">年</span></td>
						<td rowspan="2" colspan="2"><span class="">骨质疏松症用药情况（双膦酸盐类）</span></td>
						<td rowspan="2">
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><span class="">注射</span><input class="" type="text"/><span class="">年</span></td>
					</tr>		
					<tr>
						<td><span class="">口服</span><input class="" type="text"/><span class="">年</span></td>
					</tr>
					<tr>
						<td><span class="">吸毒史</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><input class="" type="text"/><span class="">年</span></td>
						<td colspan="2"><span class="">心理、精神障碍</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><input class="" type="text"/><span class="">年</span></td>
					</tr>	
					<tr>
						<td><span class="">皮肤粘膜病变</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><input class="" type="text"/><span class="">年</span></td>
						<td colspan="2"><span class="">长期应用糖皮质激素</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><input class="" type="text"/><span class="">年</span></td>
					</tr>	
					<tr>
						<td><span class="">其它系统疾病：</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td colspan="4"><input class="" type="text"/></td>
						<td><input class="" type="text"/><span class="">年</span></td>
					</tr>			
					<tr>
						<td><span class="">药物过敏</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><input class="" type="text"/><span class="">年</span></td>
						<td colspan="2"><span class="">怀孕</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><input class="" type="text"/><span class="">月</span></td>
					</tr>		
					<tr>
						<td colspan="6">
							<span class="">正在服用的药物 </span>
							<input class="" type="text"/><span class=""></span>							
						</td>						
						<td><input class="" type="text"/><span class="">月</span></td>
					</tr>							
				</table>
			</div>
		</div>
		<!-- 生活习惯 -->
		<div class="row">
			<div class="col-md-12 col-sm-12 colDefined">
				<span class="smalltitle"><font>●</font>生活习惯</span>
			</div>
		</div>
		<div class="row content">
			<div class="col-md-12 col-sm-12 colDefined">
				<table class="contentItem" border="1" width="100%">
					<!-- 生活习惯 -->
					<tr>
						<td><span class="">吸烟</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><input class="" type="text"/><span class="">年</span></td>
						<td><span class="">抽烟支数/日</span></td>
						<td><input class="" type="text"/></td>
					</tr>		
					<tr>
						<td><span class="">饮酒</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><input class="" type="text"/><span class="">年</span></td>
						<td colspan="2">
							<input class="" type="checkbox"/><span class="">少量</span>
							<input class="" type="checkbox"/><span class="">中量</span>
							<input class="" type="checkbox"/><span class="">大量</span>
						</td>
					</tr>		
					<tr>
						<td><span class="">磨牙</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>
						</td>
						<td><input class="" type="text"/><span class="">年</span></td>
						<td><span class="">频次</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">频繁</span>
							<input class="" type="checkbox"/><span class="">偶尔</span>
						</td>
					</tr>		
					<tr>
						<td><span class="">咀嚼习惯</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">左侧</span>
							<input class="" type="checkbox"/><span class="">右侧</span>
							<input class="" type="checkbox"/><span class="">双侧</span>
						</td>
						<td><input class="" type="text"/><span class="">年</span></td>
						<td><span class="">其他习惯</span></td>
						<td>
							<input class="" type="text"/><span class="">频繁</span>
						</td>
					</tr>			
					<tr>
						<td><span class="">洁牙习惯</span></td>
						<td>
							<input class="" type="checkbox"/><span class="">无</span>
							<input class="" type="checkbox"/><span class="">有</span>						
						</td>
						<td><span class="">频次</span><input class="" type="text"/><span class="">次/年</span></td>
						<td><span class="">最近一次洁牙</span></td>
						<td>
							<input class="" type="text"/><span class="">年</span><input class="" type="text"/><span class="">月</span>
						</td>
					</tr>													
				</table>
			</div>
		</div>
		<!-- 结语 -->
		<div class="row">
			<div class="col-md-12 col-sm-12 colDefined">
				<span>以上所述本人健康状况属实，对隐瞒病情所造成的不良后果责任自负。 </span>
				<div id="consent_signature">
					<div class="signature_time">
						<div class="signature_box site">
							<span id="patientSignature" style="display: inline;">患者签名：</span>
							<img id="patientimg" style="width:156px;height: 43px;"/>
						</div>
						<input id="patienttime" class="site consent_time" type="text" readonly="readonly" placeholder="请选择日期"/>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 按钮 -->
	<div class="btns">
		<button id="consent_saveBtn" onclick="save()">保存</button>
		<button id="print_Btn" onclick="myPreviewAll()">打印本页内容</button>
	</div>
	
</body>
	<script language="javascript"  src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
	<script type="text/javascript">
		var patientsignature="";//患者签名字段
		var patientstatus=true;
		var contextPath = "<%=contextPath%>";
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
			document.ondragstart = function() {
	            return false;
	        };
		    
			
		});
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
		}
		//保存方法
		function save(){
			console.log("保存方法");
		}
		
		//打印方法
		function myPreviewAll(){
			LODOP=getLodop();  
			LODOP.PRINT_INIT("主诉及既往病史！");
			LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");
			var htmlStyle="<style>button{display:none;}</style>";
			var html="<!DOCTYPE html>"+document.getElementsByTagName("html")[0].innerHTML+htmlStyle;
			LODOP.ADD_PRINT_HTM(10,10,"100%","100%",html);
			LODOP.PREVIEW();	
		};

	</script>

</html>