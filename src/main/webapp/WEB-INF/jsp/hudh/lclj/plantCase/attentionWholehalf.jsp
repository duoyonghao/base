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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<style id="styleA" type="text/css">
	*{
		margin: 0px;
		padding: 0px;
		font-size: 18px;
		line-height: 30px;  
/* 		font-size: 12px;
		line-height: 24px; */
	}
	#content{
		font-weight: bold;
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
	/* 详细文字介绍 */
	#content .consent_text{
		width：100%;
		font-weight: normal;
		margin:0% 3%;
	}
	/* 第二条：输入输入框 */
	#content .consent_text>.text_input{
		width: 120px;
    	height: 20px;
    	border: 0px;
    	border-radius: 0px;
    	border-bottom: 1px solid black;
    	text-align: center;
	}
	/* 第二条：种植牙位 */
	#content .consent_text .tooth_map{
		display: inline-block;
		width: 140px;
    	height: 50px;
    	margin-bottom: -20px;
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
	/* 第二条：牙位输入框 */
	#content .consent_text .tooth_map>li>.tooth_input{
		display:block;
		width:100%;
		height:100%;
		padding:0px;
		border:0px;
		text-align: center;
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
		font-weight: normal;
	}
	/* 时间选择框 */
	#content #consent_signature>.signature_time>input{
		width:35%;
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
	
</style>
</head>
<body style="padding: 0px 3%;">
	<div id="content">
		<h2  class="bigtitle">种植术后医嘱</h2>
		<div style="border:1px solid black;padding-top: 3%;">
			<div class="consent_text">
				一、术后可能会出现的情况
			</div>
			<div class="consent_text">
				1、 <font style="font-weight: bold;">疼痛</font>。术后可能出现疼痛，一般不会超过24小时，如果出现难以忍受的疼痛可能用止痛药物，如：布洛芬、乐松等，服用药物前与医生联系。
			</div>
			<div class="consent_text">
				2、 <font style="font-weight: bold;">渗血</font>。24小时内手术区域可能出现渗血，轻微渗血会自行停止，无需特殊处理，如果出血量多，可用棉球压迫出血点，30分钟后即可止血。如果出现无法处理情况必须来院进行处理，口含冰水有助预防出血。
			</div>
			<div class="consent_text">
				3、 <font style="font-weight: bold;">肿胀</font>。术后可能还会伴有下脸部淤青，冷敷有助降低肿胀发生。淤青一般出现在三天后，如果出现淤青，用毛巾热敷可促进淤青消退。
			</div>
			<div class="consent_text">
				4、<font style="font-weight: bold;">感染</font>。严重的感染可能会导致种植体松动。为了预防感染，请注意术后休息，保持良好的口腔卫生，按时服药，避免咀嚼过硬食物，忌烟酒，密切观察植体恢复情况，有异常及时与医生联系。
			</div>
			<div class="consent_text" style="margin-top: 20px;">
				二、注意事项（减少术后并发症促进伤口愈合）
			</div>
			<div class="consent_text">
				1、 24小时以内不刷牙漱口，避免术区出血。
			</div>
			<div class="consent_text">
				2、 术区未完全愈合，切忌抽烟喝酒、辛辣刺激等
			</div>
			<div class="consent_text">
				3、 在植体未完全骨结合前（一般三个月）尽量避免负荷过重。
			</div>
			<div class="consent_text">
				4、保持口腔卫生，按时服用消炎药，避免炎症发生。
			</div>
			<div class="consent_text">
				5、 按医嘱，定期来院复查。
			</div>
			<div class="consent_text" style="margin-top: 20px;">
				三、饮食注意事项
			</div>
			<div class="consent_text">
				1、 <font style="font-weight: bold;">术后24小时</font><br/>饮食建议： 小米粥  稀粥  糕点  鸡蛋羹（忌太热太硬 温凉后食用）
			</div>
			<div class="consent_text">
				2、 <font style="font-weight: bold;">术后2-7天</font><br/>饮食建议：面条 八宝粥 五谷粥 鱼汤 面包 馒头 番茄汁 红枣枸杞水（忌过硬食物 多喝水）
			</div>
			<div class="consent_text" style="margin-bottom: 25px;">
				3、 <font style="font-weight: bold;">术后4个月内</font><br/>饮食建议：为顺利完成骨结合，须谨慎用餐。临时修复义齿设计应以流质、半流质(如汤、粥类)食物为主，固态食物则以是否可用餐叉分割为取舍标准。需要撕或前牙啃的食物均不可食用。可以食用米饭、骨头汤、鱼汤、果汁、粥、面条、意粉、土豆、南瓜、炖熟/炖烂的肉类、菜类(菜梗除外)，热乎松软或泡化的馒头。禁食口香糖、牛肉干、果脯、烤得较硬的面包、披萨、三明治、牛皮糖、蚕豆等。禁咬整个水果(苹果，梨等)、坚果、芭乐(番石榴)、骨头、蟹壳、壳、菜梗等需要大力咀嚼及撕咬的食物。
			</div>
			<!-- 手术签名 -->
			<div id="consent_signature" style="border-top: 1px solid black;padding: 30px 3% 0%;">
				<!-- 患者签名 -->
				<div class="signature_time">
					<div class="signature_box">
						<span>患者(监护人)签字:</span>
						<div id="PatientSignature"></div>
					</div>
					<input id="patienttime" type="text" class="consent_time" disabled="disabled"style="display: none;"/>
				</div>	
				<!-- 医生签名 -->
				<div class="signature_time" style="float:right;">
					<div class="signature_box">
						<span>医生签名:</span>
						<div id="doctorSignature"></div>
					</div>
					<input id="doctortime" type="text" class="consent_time" style="display: none;"/>
				</div>
			</div>
		</div>
		
		<!-- 按钮 -->
		<div class="btns">
			<button id="consent_saveBtn" onclick="save()" style="display: none;">保存</button>
			<button id="print_Btn" onclick="myPreviewAll()">打印本页内容</button>
		</div>
	</div>
</body>

<script language="javascript"  src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
<script type="text/javascript">
	var contextPath = "<%=contextPath%>";	
	var menuid=window.parent.menuid;//左侧菜单id
	$(function(){
		var userAgent = navigator.userAgent; 
	    if (userAgent.indexOf("iPad") > -1){
	     	$("#print_Btn").hide();//移动端打印按钮隐藏
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
	 	// 2019/7/24 lutian 禁止页面拖拽
		document.ondragstart = function() {
            return false;
        };

	});
	/* 打印本页面方法 */
	function myPreviewAll(){
		LODOP=getLodop();  
		LODOP.PRINT_INIT("种植术后医嘱");
		var htmlStyle="<style>button{display:none;}*{font-size: 12px;line-height: 24px;}</style>";
		var html="<!DOCTYPE html>"+document.getElementsByTagName("html")[0].innerHTML+htmlStyle;
		LODOP.ADD_PRINT_HTM(10,10,"100%","100%",html);
		LODOP.PREVIEW();	
	};
</script>
</html>