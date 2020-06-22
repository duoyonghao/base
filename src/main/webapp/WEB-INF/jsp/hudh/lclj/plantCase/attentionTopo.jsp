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
 		/* font-size: 12px;
		line-height: 24px;  */ 
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
		<h2 class="bigtitle">种植牙术后注意事项</h2>
		<div style="border:1px solid black;padding-top: 3%;">
			<div class="consent_text">
				1、术后不宜烟酒。
			</div>
			<div class="consent_text">
				2、手术2小时后可饮食，食物不宜过热、过硬及辛辣刺激， 14天后恢复正常饮食
			</div>
			<div class="consent_text">
				3、术后24小时内冰敷手术的面部区域，同一区域连续冰敷时间不超过1分钟，冰袋可置冰箱速冻。（术后手术面部区域肿胀，淤青属正常情况）
			</div>
			<div class="consent_text">
				4、24小时内不可刷牙漱口，不要吸吮伤口和大力吐口水，以防破坏凝血块而大出血，24小时后可正常刷牙漱口（术区力量轻柔）。
			</div>
			<div class="consent_text">
				5、术后3日内注意休息，不宜剧烈运动。有轻微疼痛，唾液中带血丝属正常现象，唾液尽量吞咽。尽量不要大笑，频繁讲话等，以防腮部过分运动出现伤口撕裂。
			</div>
			<div class="consent_text">
				6、种植术后用药遵医嘱。
			</div>
			<div class="consent_text">
				7、下列情况为目前医学不可避免的范围（1%-2%），但不必担心，请及时与医生联系，医生会给您妥善处理。
			</div>
			<div class="consent_text">
				①持续疼痛，用镇痛剂无法控制，或能感觉到发烧。
			</div>
			<div class="consent_text">
				②出血量过大或时间过长。
			</div>
			<div class="consent_text">
				③手术过去后6日以上，浮肿现象更加明显。
			</div>
			<div class="consent_text">
				④二期修复可能出现：a 咬合、咀嚼不佳、咬唇、咬舌等；
			</div>
			<div class="consent_text text_addstyle" style="margin-left: 220px;">
				b 基台、牙冠、愈合帽、人造牙、种植体脱落；
			</div>
			<div class="consent_text text_addstyle" style="margin-left: 220px;">
				c 过渡牙桥断裂；
			</div>
			<!-- 手术签名 -->
			<div id="consent_signature" style="border-top: 1px solid black;padding: 30px 3% 0%;">
				<!-- 患者签名 -->
				<div class="signature_time">
					<div class="signature_box">
						<span>患者(监护人)签字:</span>
						<div id="PatientSignature"></div>
					</div>
					<input id="patienttime" type="text" class="consent_time" style="display: none;"/>
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
	     	$(".text_addstyle").css('margin-left','203px');
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
		LODOP.PRINT_INIT("种植牙术后注意事项");
		var htmlStyle="<style>button{display:none;}.text_addstyle{margin-left: 142px!important;}*{font-size: 12px;line-height: 24px;}</style>";
		var html="<!DOCTYPE html>"+document.getElementsByTagName("html")[0].innerHTML+htmlStyle;
		LODOP.ADD_PRINT_HTM(10,10,"100%","100%",html);
		LODOP.PREVIEW();	
	};
</script>
</html>