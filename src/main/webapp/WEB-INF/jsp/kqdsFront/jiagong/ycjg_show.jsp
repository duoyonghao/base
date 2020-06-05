<%@page import="com.kqds.entity.sys.YZPerson"%>
<%@page import="com.kqds.util.sys.SessionUtil"%>
<%@page import="java.io.Console"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
  YZPerson person =  SessionUtil.getLoginPerson(request);
  String createuser = person.getSeqId();
  String organization = person.getOrganization();
  String loginUserName = person.getUserName();
  /*  加工单查询单号 */
  String id = request.getParameter("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>加工单展示</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/hudh/lclj/flowdetail/css/base.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/ycjg_add.css" />
<%-- <link type="text/css" rel="stylesheet" href="<%=contextPath%>/static/css/kqdsFront/jiagong/powerFloat.css" />
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/static/css/kqdsFront/jiagong/xmenu.css" /> 
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/static/css/kqdsFront/jiagong/index.css" /> --%>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script> 
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jiagong/jquery-1.7.2.min.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jiagong/jquery-powerFloat-min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jiagong/jquery-xmenu.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jiagong/index.js"></script> --%>

</head>
<style type="text/css">
	/* .consent_time{
		 width: 150px !important;
	} */
	.optionsDetail .closeIcon{
		display: block;
	    position: absolute;
	    top: 0px;
	    right: 0px;
	    width: 25px;
	    height: 25px;
	    background: url(../../base/static/image/kqdsFront/jiagong/cancel.png) no-repeat;
	    background-size: 25px 25px;
	} 
	#processUnit {
		width: 135px;
   	 	padding-left: 5px;
   	 	font-weight: bold;
	    border: 1px solid #ddd;
	    font-size: 14px;
	    line-height: 28px;
	    height: 26px;
	    border-radius: 5px;
	    outline: none;
	    font-weight: bold;
	}
	/* 聊天沟通信息 */
	#chatInfo{
		border:1px solid #c3c3c3;
		border-bottom: 1px solid #999;
		max-height: 150px;
    	overflow-y: auto;
	}
	#chatInfo span{
	    display: block;
		font-size: 14px;
		line-height: 22px;
	}
	/* 单条聊天 */
	#chatInfo .chatItem{
		overflow: hidden;
		/* padding: 10px; */
		padding: 3px 10px;
	}
	/* 聊天人 */
	#chatInfo .chatPerson{
	 	display: block;
		float: left;
		margin: 25px 5px 0px 0px;
		font-weight: bold;
	}
	/* 聊天详细信息 */
	#chatInfo .chatRight{
		float:left;
	}
	/* 聊天时间 */
	#chatInfo .chatRight>.chatTime{
		font-size: 12px;
    	margin-left: 20px;
	}
	/* 聊天内容 */
	#chatInfo .chatRight>.chatText{
		/* border: 1px solid #ddd; */
	    margin: 0px 10px;
	    border-radius: 5px;
	    box-shadow: 0px 0px 8px 1px #ddd;
	    /* padding: 5px; */
	    padding: 1px 5px 1px 5px;
	    position: relative;
	    /* background: url(../static/image/kqdsFront/jiagong/chatIcon.png) no-repeat;
	    background-size: 10px 13px;
	    background-position: left; */
	}
	#chatInfo .chatRight>.chatText>img{
		height: 13px;
	    position: absolute;
	    top: 7px;
	    left: -6px;
	}
	/* 聊天输入框 */
	#importChat{
		text-align: right;
	    border: 1px solid #c3c3c3;
	    border-top: 0px;
	    padding: 8px 10px 8px 0px;
	}
	/* 输入框 */
	#importChat .importChatText{
		border: 1px solid #ddd;
	    font-size: 14px;
	    line-height: 26px;
	    border-radius: 5px;
	    margin-right: 10px;
	    width: 70%;
	    outline: none;
	    border: 0px;
	    box-shadow: 0px 0px 6px 1px #ddd;
	    padding-left: 10px;
	}
	/* 按钮 */
	#importChat .importChatBtn{
		font-size: 12px;
    	line-height: 22px;
	    background-color: #00A6C0;
	    font-weight: normal;
	    color: white;
	    border: 0px;
	    border-radius: 5px;
	    padding: 0px 15px;
	    letter-spacing: 1px;
	}
	#chargemodelperson{
		width: 90px;
	}
	#age{
		width: 106px;
	}
	@media screen and (max-width: 720px) {
	    .toothBox .toothMap{
		    width: 225px;
		    height: 80px;
		    padding-top: 16px;
		    margin-top: 20px;
		}
		.upYa,.downYa{
			width: 100%;
		} 
		.toothBox li{
			width:6.25%;
		}
		.toothMap .verticalLine{
			top: 0px;
		}
		#logoImg{
			top: 35px;
		}
		.toothBox .toothMap{
			padding: 16px 5%;
		}
	}
	@media print{
		.upYa{
			border-bottom: 1px solid black;
		}
		.verticalLine{
			border-right: 1px solid black;
		}
		#chargemodelperson{
			width: 80px;
		}
		#age{
			width: 93px;
		}
		#chatInfo .chatRight>.chatText{
			border:1px solid #ddd;
		}
		#chatInfo{
			height: auto;
			max-height:auto;
		}
		.designRequest input[type="radio"]+span{
			width: 10px;
    		height: 10px;
    		padding: 1px;
		}
		.designRequest input[type="radio"]:checked+span>font{
			width: 6px;
    		height: 6px;
    		border: 3px solid #00A6C0;
		}
	}
	/* @page { 
		 @bottom-left {
		    content: "Our Cats";
		    font-size: 9pt;
		    color: #333;
		  } 
		 
		  @bottom-right { 
		    margin: 10pt 0 30pt 0;
		    border-top: .25pt solid #666;
		    content: counter(page);
		    font-size: 9pt;
		  }
		 
		  @top-right {
		    content:  string(doctitle);
		    margin: 30pt 0 10pt 0;
		    font-size: 9pt;
		    color: #333;
		  } 
		} */
		@page {
			 @ top-left {
			 		border:1px solid blue;
			 }
			 @ bottom-center {
				 	content: "Our Cats";
			    	font-size: 9pt;
			    	color: #333;
			    	border:1px solid red;
			 }
		}
</style>


<body>
<!--startprint-->
	<div id="" class="container-fluid">
		<!-- 标题 -->
		<div class="row">
			<!-- </div> -->
			<div id="logoTitle" class="col-md-12 col-sm-12 col-xs-12">
				<img id="logoImg" src="<%=contextPath%>/static/image/kqdsFront/jiagong/logo.jpg">
				<span class="bigtitle">北京海德堡联合口腔医院管理有限公司(加工单)</span>
			</div>
		</div>	
		<!-- 基本信息 -->
		<div class="row">
			<div class="col-md-12 col-sm-12 colDefined" style="position: relative;">
				<span class="smalltitle"><font>●</font>基本信息</span>
				<div class="inputDiv" style="position: absolute;right: 10px;top: 7px;">
					<span>订单编号：</span><input id="ordernumber" type="text" class="" style="border:0px;border-bottom: 1px solid black;text-align: center;"/>
				</div>
			</div>
		</div>
		<div class="row baseInfo">
			<div class="col-md-12 col-sm-12 colDefined">
				<!-- 患者基本信息 -->
				<div class="patient" style="width:80%;float:left;padding-top: 15px;">
					<div class="inputDiv">
						<span>加工单位：</span><input id="processunit" type="text" />
						<!-- <select id="processUnit">
							<option value="">请选择</option>
						</select> -->
					</div>
					<div class="inputDiv">
						<span>医<font style="display:inline-block;margin-left:32px;"></font>生：</span><input id="doctor" type="text" />
					</div>
					<div class="inputDiv">
						<span>收模人：</span><input id="chargemodelperson" type="text"/>
					</div>
					<div class="inputDiv" style="clear:left;">
						<span>患者姓名：</span><input id="userName" type="text" />
					</div>
					<div class="inputDiv">
						<span>性<font style="display:inline-block;margin-left:32px;"></font>别：</span><input id="sex" type="text" />
					</div>
					<div class="inputDiv">
						<span>年龄：</span><input id="age" type="text"/>
					</div>
					<div class="inputDiv" style="clear:left;">
						<span>患者编号：</span><input id="userCode" type="text" />
					</div>
					<div class="inputDiv">
						<span>手机号码：</span><input id="phoneNumber" type="text" />
					</div>
					<div class="inputDiv" style="clear:left;">
						<span>取模时间：</span><input id="deliverytime" class="consent_time" type="text" autocomplete="off"/>
					</div>
					<div class="inputDiv">
						<span>收模日期：</span><input id="chargemodeltime" class="consent_time" type="text" autocomplete="off"/>
					</div>
					<div class="inputDiv">
						<span>出货时间：</span><input id="sendouttime" class="consent_time" type="text" autocomplete="off"/>
					</div>
					<div class="inputDiv">
						<span>预计收货时间：</span><input id="receivingtime" class="consent_time" type="text" autocomplete="off"/>
					</div>
					<!-- <div class="inputDiv">
						<span>订单编号：</span><input type="text" />
					</div> -->
				</div>
				<div class="patientHeader" style="width:15%;float:right;">
					<%-- <img src="<%=contextPath%>/static/image/kqdsFront/jiagong/headImg.jpg">
					<input style="display:none;" type="file" /> --%>
					<input type="file" class="file" multiple value="点击上传头像">
					<img class="headerImg" src="<%=contextPath%>/static/image/kqdsFront/jiagong/headImg.jpg">
				</div>
			</div>
		</div>	
		<!-- 配件 -->
		<div class="row fittingsName">
			<div class="col-md-12 col-sm-12 colDefined">
				<span class="smalltitle"><font>●</font>配件</span>
			</div>
		</div>
		<div class="row fittings">
			<div class="col-md-12 col-sm-12 colDefined">
				<ul>
					<li class="fittingsItem">
						<span class="fittingsName">上颌模型：</span><input id="maxillarymodel" class="fittingsNum" type="text" onblur="if(this.value){if(!/^\d+$/.test(this.value)){layer.alert('只能输入数字');this.value='';}}"/>
					</li>
					<li class="fittingsItem">
						<span class="fittingsName">咬合记录：</span><input id="occlusiorecord" class="fittingsNum" type="text" onblur="if(this.value){if(!/^\d+$/.test(this.value)){layer.alert('只能输入数字');this.value='';}}"/>
					</li>
					<li class="fittingsItem">
						<span class="fittingsName">替代体：</span><input id="replacebody" class="fittingsNum" type="text" onblur="if(this.value){if(!/^\d+$/.test(this.value)){layer.alert('只能输入数字');this.value='';}}"/>
					</li>
					<li class="fittingsItem">
						<span class="fittingsName">愈合基台：</span><input id="gingivalformer" class="fittingsNum" type="text" onblur="if(this.value){if(!/^\d+$/.test(this.value)){layer.alert('只能输入数字');this.value='';}}"/>
					</li>
					<li class="fittingsItem">
						<span class="fittingsName">下颌模型：</span><input id="mandiblemodel" class="fittingsNum" type="text" onblur="if(this.value){if(!/^\d+$/.test(this.value)){layer.alert('只能输入数字');this.value='';}}"/>
					</li>
					<li class="fittingsItem">
						<span class="fittingsName">旧义齿参考模型：</span><input id="olddenturemodel" class="fittingsNum" type="text" onblur="if(this.value){if(!/^\d+$/.test(this.value)){layer.alert('只能输入数字');this.value='';}}"/>
					</li>
					<li class="fittingsItem">
						<span class="fittingsName">基台：</span><input id="drillbase" class="fittingsNum" type="text" onblur="if(this.value){if(!/^\d+$/.test(this.value)){layer.alert('只能输入数字');this.value='';}}"/>
					</li>
					<li class="fittingsItem">
						<span class="fittingsName">定位器：</span><input id="locator" class="fittingsNum" type="text" onblur="if(this.value){if(!/^\d+$/.test(this.value)){layer.alert('只能输入数字');this.value='';}}"/>
					</li>
					<li class="fittingsItem">
						<span class="fittingsName">上颌托盘：</span><input id="maxillarysalver" class="fittingsNum" type="text" onblur="if(this.value){if(!/^\d+$/.test(this.value)){layer.alert('只能输入数字');this.value='';}}"/>
					</li>
					<li class="fittingsItem">
						<span class="fittingsName">参考蜡型：</span><input id="waxpattern" class="fittingsNum" type="text" onblur="if(this.value){if(!/^\d+$/.test(this.value)){layer.alert('只能输入数字');this.value='';}}"/>
					</li>
					<li class="fittingsItem">
						<span class="fittingsName">螺丝：</span><input id="screw" class="fittingsNum" type="text" onblur="if(this.value){if(!/^\d+$/.test(this.value)){layer.alert('只能输入数字');this.value='';}}"/>
					</li>
					<li class="fittingsItem">
						<span class="fittingsName">颌架：</span><input id="jawframe" class="fittingsNum" type="text" onblur="if(this.value){if(!/^\d+$/.test(this.value)){layer.alert('只能输入数字');this.value='';}}"/>
					</li>
					<li class="fittingsItem">
						<span class="fittingsName">下颌托盘：</span><input id="mandiblesalver" class="fittingsNum" type="text" onblur="if(this.value){if(!/^\d+$/.test(this.value)){layer.alert('只能输入数字');this.value='';}}"/>
					</li>
					<li class="fittingsItem">
						<span class="fittingsName">转移杆：</span><input id="shiftlever" class="fittingsNum" type="text" onblur="if(this.value){if(!/^\d+$/.test(this.value)){layer.alert('只能输入数字');this.value='';}}"/>
					</li>
					<li class="fittingsItem">
						<span class="fittingsName">人工牙龈：</span><input id="humangingival" class="fittingsNum" type="text" onblur="if(this.value){if(!/^\d+$/.test(this.value)){layer.alert('只能输入数字');this.value='';}}"/>
					</li>
					<li class="fittingsItem">
						<span class="fittingsName">阿曼颌架底座：</span><input id="omanjawbase" class="fittingsNum" type="text" onblur="if(this.value){if(!/^\d+$/.test(this.value)){layer.alert('只能输入数字');this.value='';}}"/>
					</li>
					<li class="fittingsItem">
						<span class="fittingsName">其他：</span><input id="other" class="fittingsNum" type="text" onblur="if(this.value){if(!/^\d+$/.test(this.value)){layer.alert('只能输入数字');this.value='';}}"/>
					</li>
				</ul>
			</div>
		</div>
		<!-- 牙位 -->
		<div class="row">
			<div class="col-md-12 col-sm-12 colDefined" style="position: relative;">
				<span class="smalltitle"><font>●</font>牙位</span>
				<button class="addTooth" onclick="addToothMap();" style="display: none;">添加牙位&nbsp;+</button>
			</div>
		</div>
		<!-- 牙位图 -->
		<div id="addToothMapDiv"></div>
		
		<!-- 设计要求 -->
		<div class="row">
			<div class="col-md-12 col-sm-12 colDefined">
				<span class="smalltitle"><font>●</font>设计要求</span>
			</div>
		</div>
		<div class="row designRequest">
			<div class="col-md-12 col-sm-12 colDefined">
				<table width="100%" cellpadding="0" cellspacing="0" style="border:1px solid #c3c3c3;">
					<tr style="border-bottom:1px solid #c3c3c3;">
						<td style="border-right:1px solid #c3c3c3;">咬合关系</td>
						<td style="border-right:1px solid #c3c3c3;">
							<ul class="cbUl" style="padding: 3px 0px;">
								<li class="cbItem">
									<input class="" type="radio" name="biteJaw" id="biteJawOne" value="1"/><span><font></font></span>
									<label for="biteJawOne"><img src="<%=contextPath%>/static/image/kqdsFront/jiagong/biteA.png"></label>
								</li>
								<li class="cbItem">
									<input class="" type="radio" name="biteJaw" id="biteJawTwo" value="2"/><span><font></font></span>
									<label for="biteJawTwo"><img src="<%=contextPath%>/static/image/kqdsFront/jiagong/biteB.png"></label>
								</li>
								<li class="cbItem">
									<input class="" type="radio" name="biteJaw" id="biteJawThree" value="3"/><span><font></font></span>
									<label for="biteJawThree"><img src="<%=contextPath%>/static/image/kqdsFront/jiagong/biteC.png"></label>
								</li>
							</ul>
						</td>
						<td style="border-right:1px solid #c3c3c3;">内冠松紧</td>
						<td>
							<ul class="cbUl">
								<li class="cbItem">
									<input class="" type="radio" name="innerCrown" id="iCrownTighten" value="1"/><span><font></font></span><label class="" for="iCrownTighten">紧</label>
								</li>
								<li class="cbItem">
									<input class="" type="radio" name="innerCrown" id="iCrownNormal" value="2"/><span><font></font></span><label class="" for="iCrownNormal">正常</label>
								</li>
								<li class="cbItem">
									<input class="" type="radio" name="innerCrown" id="iCrownLoose" value="3"/><span><font></font></span><label class="" for="iCrownLoose">松</label>
								</li>
							</ul>
						</td>
					</tr>
					<tr style="border-bottom:1px solid #c3c3c3;">
						<td style="border-right:1px solid #c3c3c3;">桥体设计</td>
						<td style="border-right:1px solid #c3c3c3;">
							<ul class="cbUl"  style="padding: 3px 0px;">
								<li class="cbItem">
									<input class="" type="radio" name="bridgeDesign" id="bridgeDesignOne" value="1"/><span><font></font></span>
									<label for="bridgeDesignOne"><img src="<%=contextPath%>/static/image/kqdsFront/jiagong/ponticA.png"></label>
								</li>
								<li class="cbItem">
									<input class="" type="radio" name="bridgeDesign" id="bridgeDesignTwo" value="2"/><span><font></font></span>	
									<label for="bridgeDesignTwo"><img src="<%=contextPath%>/static/image/kqdsFront/jiagong/ponticB.png"></label>
								</li>
								<li class="cbItem">
									<input class="" type="radio" name="bridgeDesign" id="bridgeDesignThree" value="3"/><span><font></font></span>
									<label for="bridgeDesignThree"><img src="<%=contextPath%>/static/image/kqdsFront/jiagong/ponticC.png"></label>
								</li>
								<li class="cbItem">
									<input class="" type="radio" name="bridgeDesign" id="bridgeDesignFour" value="4"/><span><font></font></span>
									<label for="bridgeDesignFour"><img src="<%=contextPath%>/static/image/kqdsFront/jiagong/ponticD.png"></label>
								</li>
							</ul>
						</td>
						<td style="border-right:1px solid #c3c3c3;">临接关系</td>
						<td>
							<ul class="cbUl">
								<li class="cbItem">
									<input class="" name="syntopy" type="radio" id="syntopyOne" value="1"/><span><font></font></span><label class="" for="syntopyOne">点面接触</label>
								</li>
								<li class="cbItem">
									<input class="" name="syntopy" type="radio" id="syntopyTwo" value="2"/><span><font></font></span><label class="" for="syntopyTwo">点接触</label>
								</li>
								<li class="cbItem">
									<input class="" name="syntopy" type="radio" id="syntopyThree" value="3"/><span><font></font></span><label class="" for="syntopyThree">面接触</label>
								</li>
							</ul>
						</td>
					</tr>
					<tr style="border-bottom:1px solid #c3c3c3;">
						<td style="border-right:1px solid #c3c3c3;">颈缘设计</td>
						<td colspan="3">
							<ul class="cbUl">
								<li class="cbItem">
									<input class="" type="checkbox" name="neckFlangeDesign" id="cervicalMarginOne" value="1"/><label class="" for="cervicalMarginOne">瓷肩台</label>
								</li>
								<li class="cbItem">
									<input class="" type="checkbox" name="neckFlangeDesign" id="cervicalMarginTwo" value="2"/><label class="" for="cervicalMarginTwo">全包瓷</label>
								</li>
								<li class="cbItem">
									<input class="" type="checkbox" name="neckFlangeDesign" id="cervicalMarginThree" value="3"/><label class="" for="cervicalMarginThree">舌面金属边</label>
								</li>
								<li class="cbItem">
									<input class="" type="checkbox" name="neckFlangeDesign" id="cervicalMarginFour" value="4"/><label class="" for="cervicalMarginFour">全金属颈缝</label>
								</li>
							</ul>
						</td>
					</tr>
					<tr style="border-bottom:1px solid #c3c3c3;">
						<td style="border-right:1px solid #c3c3c3;">种植固定方式</td>
						<td colspan="3">
							<ul class="cbUl">
								<li class="cbItem">
									<input class="" type="checkbox" name="plantFixed" id="plantFixedOne" value="1"/><label class="" for="plantFixedOne">粘接固定冠不开孔（配定位器）</label>
								</li>
								<li class="cbItem">
									<input class="" type="checkbox" name="plantFixed" id="plantFixedTwo" value="2"/><label class="" for="plantFixedTwo">粘接固定冠开排溢孔（配定位器）</label>
								</li>
								<li class="cbItem">
									<input class="" type="checkbox" name="plantFixed" id="plantFixedThree" value="3"/><label class="" for="plantFixedThree">粘接固定冠开螺丝孔（配定位器）</label>
								</li>
								<li class="cbItem">
									<input class="" type="checkbox" name="plantFixed" id="plantFixedFour" value="4"/><label class="" for="plantFixedFour">螺丝固定粘接出货</label>
								</li>
							</ul>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- 医技沟通记录 -->
		<div class="row">
			<div class="col-md-12 col-sm-12 colDefined">
				<span class="smalltitle"><font>●</font>医技沟通记录</span>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 col-sm-12 colDefined">
				<!-- 聊天详细信息 -->
				<ul id="chatInfo">
					<%-- <li class="chatItem">
						<span class="chatPerson">admin</span>
						<div class="chatRight">
							<span class="chatTime">2019/12/25 15:50:32</span>
							<span class="chatText"><img src="<%=contextPath%>/static/image/kqdsFront/jiagong/chatIcon.png"/>第一次测试</span>
						</div>
					</li>
					<li class="chatItem">
						<span class="chatPerson">admin</span>
						<div class="chatRight">
							<span class="chatTime">2019/12/25 15:55:32</span>
							<span class="chatText"><img src="<%=contextPath%>/static/image/kqdsFront/jiagong/chatIcon.png"/>第二次沟通测试</span>
						</div>
					</li> --%>
				</ul>
				<!-- 聊天输入框 -->
				<div id="importChat">
					<input class="importChatText" type="text" id="chantContent"/>
					<button class="importChatBtn" onclick="send()">发送</button>
				</div>
			</div>
		</div>
		
		<!-- 牙位图详细选项 -->
		<div class="optionsDetail" style="display: none;"> 
		</div> 
		
	</div>
	
		<!-- 追加牙位图 -->
		<div id="toothMapItem" class="toothMapItem" style="display:none;">
			<div class="row toothMapAndBtn" style="border: 1px solid #c3c3c3;border-radius:9px;margin-bottom: 10px;">
				<div class="colDefined toothMapCol" style="width:33%;float:left;height:100%;">
					<div id="zhongzhi" class="toothBox adult">
						<!-- <span class="smallTitle aa">种植牙位：</span> -->
						<div class="toothMap">
							<ul class="upYa">
								<!-- <li><input class="ToothBit_checkbox" type="checkbox" id="lefttop" class="lefttop" disabled="disabled" /></li> -->
								<li class="leftUpTooth"><span class="yaIcon le8"></span><span class="num numle8 ToothBit_checkbox1" name="zzadultupYa1">8</span></li>
								<li class="leftUpTooth"><span class="yaIcon le7"></span><span class="num numle7 ToothBit_checkbox1" name="zzadultupYa1">7</span></li>
								<li class="leftUpTooth"><span class="yaIcon le6"></span><span class="num numle6 ToothBit_checkbox1" name="zzadultupYa1">6</span></li>
								<li class="leftUpTooth"><span class="yaIcon le5"></span><span class="num numle5 ToothBit_checkbox1" name="zzadultupYa1">5</span></li>
								<li class="leftUpTooth"><span class="yaIcon le4"></span><span class="num numle4 ToothBit_checkbox1" name="zzadultupYa1">4</span></li>
								<li class="leftUpTooth"><span class="yaIcon le3"></span><span class="num numle3 ToothBit_checkbox1" name="zzadultupYa1">3</span></li>
								<li class="leftUpTooth"><span class="yaIcon le2"></span><span class="num numle2 ToothBit_checkbox1" name="zzadultupYa1">2</span></li>
								<li class="leftUpTooth"><span class="yaIcon le1"></span><span class="num numle1 ToothBit_checkbox1" name="zzadultupYa1">1</span></li>
			
								<li class="rightUpTooth"><span class="yaIcon rg1"></span><span class="num numrg1 ToothBit_checkbox2" name="zzadultupYa2">1</span></li>
								<li class="rightUpTooth"><span class="yaIcon rg2"></span><span class="num numrg2 ToothBit_checkbox2" name="zzadultupYa2">2</span></li>
								<li class="rightUpTooth"><span class="yaIcon rg3"></span><span class="num numrg3 ToothBit_checkbox2" name="zzadultupYa2">3</span></li>
								<li class="rightUpTooth"><span class="yaIcon rg4"></span><span class="num numrg4 ToothBit_checkbox2" name="zzadultupYa2">4</span></li>
								<li class="rightUpTooth"><span class="yaIcon rg5"></span><span class="num numrg5 ToothBit_checkbox2" name="zzadultupYa2">5</span></li>
								<li class="rightUpTooth"><span class="yaIcon rg6"></span><span class="num numrg6 ToothBit_checkbox2" name="zzadultupYa2">6</span></li>
								<li class="rightUpTooth"><span class="yaIcon rg7"></span><span class="num numrg7 ToothBit_checkbox2" name="zzadultupYa2">7</span></li>
								<li class="rightUpTooth"><span class="yaIcon rg8"></span><span class="num numrg8 ToothBit_checkbox2" name="zzadultupYa2">8</span></li>
								<!-- <li><input class="ToothBit_checkbox" type="checkbox" id="righttop" disabled="disabled" /></li> -->
							</ul>
							<div class="line">
								<!-- <span class="left">右</span> <span class="right">左</span> -->
							</div>
							<div class="verticalLine"></div>
							<ul class="downYa">
								<!-- <li><input class="ToothBit_checkbox" type="checkbox" id="leftdown" disabled="disabled" /></li> -->
								<li class="leftDownTooth"><span class="num numle8 ToothBit_checkbox3" name="zzadultdownYa1">8</span><span class="yaIcon le8"></span></li>
								<li class="leftDownTooth"><span class="num numle7 ToothBit_checkbox3" name="zzadultdownYa1">7</span><span class="yaIcon le7"></span></li>
								<li class="leftDownTooth"><span class="num numle6 ToothBit_checkbox3" name="zzadultdownYa1">6</span><span class="yaIcon le6"></span></li>
								<li class="leftDownTooth"><span class="num numle5 ToothBit_checkbox3" name="zzadultdownYa1">5</span><span class="yaIcon le5"></span></li>
								<li class="leftDownTooth"><span class="num numle4 ToothBit_checkbox3" name="zzadultdownYa1">4</span><span class="yaIcon le4"></span></li>
								<li class="leftDownTooth"><span class="num numle3 ToothBit_checkbox3" name="zzadultdownYa1">3</span><span class="yaIcon le3"></span></li>
								<li class="leftDownTooth"><span class="num numle2 ToothBit_checkbox3" name="zzadultdownYa1">2</span><span class="yaIcon le2"></span></li>
								<li class="leftDownTooth"><span class="num numle1 ToothBit_checkbox3" name="zzadultdownYa1">1</span><span class="yaIcon le1"></span></li>
			
								<li class="rightDownTooth"><span class="num numrg1 ToothBit_checkbox4" name="zzadultdownYa2">1</span><span class="yaIcon rg1"></span></li>
								<li class="rightDownTooth"><span class="num numrg2 ToothBit_checkbox4" name="zzadultdownYa2">2</span><span class="yaIcon rg2"></span></li>
								<li class="rightDownTooth"><span class="num numrg3 ToothBit_checkbox4" name="zzadultdownYa2">3</span><span class="yaIcon rg3"></span></li>
								<li class="rightDownTooth"><span class="num numrg4 ToothBit_checkbox4" name="zzadultdownYa2">4</span><span class="yaIcon rg4"></span></li>
								<li class="rightDownTooth"><span class="num numrg5 ToothBit_checkbox4" name="zzadultdownYa2">5</span><span class="yaIcon rg5"></span></li>
								<li class="rightDownTooth"><span class="num numrg6 ToothBit_checkbox4" name="zzadultdownYa2">6</span><span class="yaIcon rg6"></span></li>
								<li class="rightDownTooth"><span class="num numrg7 ToothBit_checkbox4" name="zzadultdownYa2">7</span><span class="yaIcon rg7"></span></li>
								<li class="rightDownTooth"><span class="num numrg8 ToothBit_checkbox4" name="zzadultdownYa2">8</span><span class="yaIcon rg8"></span></li>
								<!-- <li><input class="ToothBit_checkbox" type="checkbox" id="rightdown" disabled="disabled" /></li> -->
							</ul>
							<span class="showToothMap" style="display: none;">更改牙位</span>
						</div>
					</div>
				</div>
				<div class="colDefined" style="width:58%;float:left;margin-left:33%;min-height:130px;">
					<div class="mapItemDetail">
	 					<div class="repairProjectBtns">
						</div>
 					</div>
 				</div>
				<div class="colDefined" style="width:9%;float:left;">
					<span class="deleteIcon" onclick="deleteToothMap(this);" style="display:none;"></span>
				</div>
			</div>
		</div>
		
	
	<!--endprint-->	
	<!-- 按钮 -->
	<div class="btns">
		<!-- <button id="consent_saveBtn" onclick="updateInfo()">修改</button> -->
		<button id="print_Btn" onclick="doPrint()">打印本页内容</button>
	</div>

</body>
	<script language="javascript"  src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
	<script type="text/javascript">
		var contextPath = "<%=contextPath%>";
		var createuser = "<%=createuser%>";
		var organization = "<%=organization%>";
		var loginUserName = "<%=loginUserName%>"; 
		var id = "<%=id%>";  /* 加工单查询单号 */
		var onclickrowOobj=null;
		var toothMapIndex=0;
		var machineData = null;//定义全局变量以便取值 syp 
		$(function(){
			$("#processUnit").attr("disabled","disabled");
			$("input").attr("disabled","disabled").css("background-color","white").css("cursor","no-drop"); //禁用所有input
			$(".importChatText").removeAttr("disabled").css("cursor","auto"); //留言不禁用
			$(".file").removeAttr("disabled").css("cursor","pointer");; //头像暂不禁用
			onclickrowOobj = window.parent.onclickrowOobj;
			var usercode = window.parent.onclickrowOobj.usercode;
			//查询出患者信息
			var patient=null;
			var url = contextPath + '/KQDS_UserDocumentAct/findByUsercode.act';
			$.ajax({   
		        type:"GET",   
		        url:url, //从服务器拿东西，地址自己写  
		        dataType:"json",  
		        async:false,
		        data: {usercode:usercode},
		        //下面这个函数是在ajax请求成功后才执行的，其中的参数data，就是
		        success:function(data){
		        	patient=data;
		        } 
		    }); 
			if(patient){
				$("#userName").attr("value",patient[0].username).attr("readonly","readonly"); // 患者姓名赋值
				$("#sex").attr("value",patient[0].sex).attr("readonly","readonly"); // 患者性别赋值
				$("#age").attr("value",patient[0].age).attr("readonly","readonly"); // 患者年龄赋值
				$("#userCode").attr("value",usercode).attr("readonly","readonly"); // 患者编号赋值
				$("#phoneNumber").attr("value",patient[0].phonenumber1).attr("readonly","readonly"); // 患者手机号赋值
			}  
			
			//时间选择
		    $(".consent_time").datetimepicker({
		        language:  'zh-CN',  
		   		minView: 2,
		        format: 'yyyy-mm-dd hh:ii:ss',
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
			// 2019/7/24 lutian 禁止页面拖拽
			document.ondragstart = function() {
	            return false;
	        };
	        
	        initRepairProject();//初始化修复项目
	        initMachineUnit(); //加载加工单位
	        initInfo(); //初始化页面信息
	        toothMapIndex=$("#addToothMapDiv").find(".toothMapAndBtn").length; //记录已经添加过的牙位图
	        initMachineSend();//初始化发送信息 
		});
		
		//加载加工单位
		function initMachineUnit(){
			var url = '<%=contextPath%>/KQDS_OutProcessing_UnitBackAct/selectPage.act';
			$.ajax({   
		        type:"GET",   
		        url:url, //从服务器拿东西，地址自己写  
		        dataType:"json",  
		        async:false,
		        //下面这个函数是在ajax请求成功后才执行的，其中的参数data，就是
		        success:function(data){
		        	var processUnitHtml="";
		        	if(data){
		        		var machineData = data.rows;
		        		for (var i = 0; i < machineData.length; i++) {
							//processUnitHtml += "<option value="+machineData[i].seqId+">"+machineData[i].name+"</option>";
		        			processUnitHtml += "<option value="+machineData[i].name+">"+machineData[i].name+"</option>";
		        		}
		        		$("#processUnit").append(processUnitHtml);
		        	}
		        } 
		    });
		}
		
		//初始化页面信息
		function initInfo(){
			//加载一级
			var url = contextPath + '/KQDS_MACHININGAct/getParticularsBySeqId.act?id=' + id;      
		    $.ajax({   
		        type:"GET",   
		        url:url, //从服务器拿东西，地址自己写  
		        dataType:"json",
		        async:false,
		        //下面这个函数是在ajax请求成功后才执行的，其中的参数data，就是
		        success:function(data){
		        	//console.log(JSON.stringify(data)+"--------data");
		        	machineData = data;
		        	if(data){
		        		/* 加工单位赋值 */
		        		//$("#processUnit").val(data.processunit);
		        		/*input框赋值 */
		        		for (var obj in data) {
							if($("#"+obj)){
								//无返回值的加noValue标识，控制打印不显示
								if(data[obj]==""){
									$("#"+obj).parents(".fittingsItem").addClass("noValue");
								}
								$("#"+obj).attr("value",data[obj]);
							}
						}
		        		/* 内冠松紧赋值 */
		        		$("input[name='innerCrown']").each(function(i,obj){
		        			if($(this).val()==data.innercrown){
		        				$(this).attr("checked","checked");
		        			}
		        		});
		        		/* 咬合关系赋值 */
		        		$("input[name='biteJaw']").each(function(i,obj){
		        			if($(this).val()==data.bitejaw){
		        				$(this).attr("checked","checked");
		        			}
		        		});
		        		/* 临接关系赋值 */
		        		$("input[name='syntopy']").each(function(i,obj){
		        			if($(this).val()==data.syntopy){
		        				$(this).attr("checked","checked");
		        			}
		        		});
		        		/* 桥体设计赋值 */
		        		$("input[name='bridgeDesign']").each(function(i,obj){
		        			if($(this).val()==data.bridgedesign){
		        				$(this).attr("checked","checked");
		        			}
		        		});
		        		//颈缘设计赋值
		    			var neckflangedesign = data.neckflangedesign;
		    			if(neckflangedesign){
			    			var neckflangedesign_id = neckflangedesign.split(";");
			    			for(var i = 0; i < neckflangedesign_id.length; i++){
			    				$("input[name='neckFlangeDesign']").each(function(){
			    					if($(this).val()==neckflangedesign_id[i]){
			    					   $(this).attr("checked","checked");
			    					}
			    				})
			    			}
		    			}
		    			//种植固定方式赋值
		    			var plantfixed = data.plantfixed;
		    			if(plantfixed){
			    			var plantfixed_id = plantfixed.split(";");
			    			for(var i = 0; i < plantfixed_id.length; i++){
			    				$("input[name='plantFixed']").each(function(){
			    					if($(this).val()==plantfixed_id[i]){
			    					   $(this).attr("checked","checked");
			    					}
			    				})
			    			}
		    			}
		        		initToothMapRepairProject(data.toothMapArr); // 初始化牙位+修复项目
		        	}
		        } 
		    });
		}
		
		// 初始化牙位+修复项目
		function initToothMapRepairProject(toothArr){
			//console.log(JSON.stringify(toothArr)+"-------------toothArr");
			if(toothArr){
				for (var i = 0; i < toothArr.length; i++) {
					var toothMapHtml=$("#toothMapItem").html();
					$("#addToothMapDiv").append(toothMapHtml);
					var showToothMaplength= $("#addToothMapDiv").find(".showToothMap").length; //已有牙位图
					$("#addToothMapDiv").find(".toothMap").eq(showToothMaplength-1).attr("id",toothArr[i].seqId);
					// $("#addToothMapDiv").find(".showToothMap").eq(showToothMaplength-1).attr("onclick","showToothMap("+i+")");
					$("#addToothMapDiv").find(".showToothMap").eq(showToothMaplength-1).addClass("toothMapIndex"+i);
					var toothMapAndBtn = $("#addToothMapDiv").find(".toothMapAndBtn").eq(showToothMaplength-1);
					
					var uplefttoothArr = toothArr[i].uplefttooth.split(";"); //左上牙位
					for (var j = 0; j < uplefttoothArr.length; j++) {
						var uplefttooth= uplefttoothArr[j];
						toothMapAndBtn.find(".toothMap").find(".leftUpTooth").each(function(i,obj){
							var numText= $(this).find(".num").text();
							if(Number(uplefttooth) == Number(numText)){
								$(this).addClass("current");
							}
						});
					}
					var leftlowertoothArr = toothArr[i].leftlowertooth.split(";"); //左下牙位
					for (var k = 0; k < leftlowertoothArr.length; k++) {
						var leftlowertooth= leftlowertoothArr[k];
						toothMapAndBtn.find(".toothMap").find(".leftDownTooth").each(function(i,obj){
							var numText= $(this).find(".num").text();
							if(Number(leftlowertooth) == Number(numText)){
								$(this).addClass("current");
							}
						});
					}
					var upperrighttoothArr = toothArr[i].upperrighttooth.split(";"); //右上牙位
					for (var g = 0; g < upperrighttoothArr.length; g++) {
						var upperrighttooth= upperrighttoothArr[g];
						toothMapAndBtn.find(".toothMap").find(".rightUpTooth").each(function(i,obj){
							var numText= $(this).find(".num").text();
							if(Number(upperrighttooth) == Number(numText)){
								$(this).addClass("current");
							}
						});
					}
					var lowrighttoothArr = toothArr[i].lowrighttooth.split(";"); //右下牙位
					for (var f = 0; f < lowrighttoothArr.length; f++) {
						var lowrighttooth= lowrighttoothArr[f];
						toothMapAndBtn.find(".toothMap").find(".rightDownTooth").each(function(i,obj){
							var numText= $(this).find(".num").text();
							if(Number(lowrighttooth) == Number(numText)){
								$(this).addClass("current");
							}
						});
					}
					//修复项目赋值
					var repairProjectArr = toothArr[i].repairProjectArr;
					for (var q = 0; q < repairProjectArr.length; q++) {
						var typeone = repairProjectArr[q].typeone; //一级id
						toothMapAndBtn.find(".repairClickBtn").each(function(i,obj){
							var oneSeqid = $(this).attr("seqid");
							if(oneSeqid==typeone){
								var typesecondArr = repairProjectArr[q].typesecond.split(";"); //三级id
								var typesecondnameArr = repairProjectArr[q].typesecondname.split(";"); //三级name
								var thisHtml="";
								if(typesecondArr.length>1){
									$(this).addClass("repairAlready");
									for (var a = 0; a < typesecondArr.length; a++) {
										if(typesecondArr[a]){
											thisHtml+='<span id='+typesecondArr[a]+'>'+typesecondnameArr[a]+'</span>';
										}
									}
									$(this).html(thisHtml); 
								}
							}
						});
					}
				}
			}
		}
		
		//修改信息
		/* function updateInfo(){
			$(".addTooth").css("display","block");
		} */
		
		function initRepairProject(){
			//加载一级
			var url = contextPath + '/KQDS_MACHININGTypeAct/initPrimary.act';      
		    $.ajax({   
		        type:"GET",   
		        url:url, //从服务器拿东西，地址自己写  
		        dataType:"json",  
		        async:false,
		        //下面这个函数是在ajax请求成功后才执行的，其中的参数data，就是
		        success:function(data){
		        	var dat=data.retData;
		        	//console.log(JSON.stringify(dat)+"----------dat");
		        	var firstRepairHtml='';
		        	for (var i = 0; i < dat.length; i++) {
		        		//firstRepairHtml+='<div class="repairClickBtn" onclick="selectOptions(this);" seqId='+dat[i].id+'>'+dat[i].name+'<span style="display:none;">'+dat[i].id+'</span></div>';
		        		firstRepairHtml+='<div class="repairClickBtn" onclick="selectOptions(this);" seqId='+dat[i].id+' selfname='+dat[i].name+'>'+dat[i].name+'</div>';
		        	}
		        	$(".repairProjectBtns").html(firstRepairHtml);
		        } 
		    });
		}

		//更改牙位：弹出牙位图
		function showToothMap(i){
			layer.open({
				type: 2,
				title: "选择牙位",
				shadeClose: false,
				shade: 0.6,
				area: ['900px', '450px'],
				content: contextPath +'/KQDS_OutProcessingSheetAct/toToothMap.act?index='+i
			});
		}
		
		//上传头像
		$('.file').change(function(){
			var file;
        	/* $filePath=URL.createObjectURL(this.files[0]);
        	$('.headerImg').attr('src',$filePath);  */
			file=this.files[0];
			var reader=new FileReader();
			var AllowImgFileSize=2100000;
			var imgUrlBase64;
			if(file){
				imgUrlBase64=reader.readAsDataURL(file);
				reader.onload=function(e){
					var ImgFileSize = reader.result.substring(0,reader.result.indexOf(",") + 1);//截取base64码部分(可选可不选，需要与后台沟通)
					//console.log(ImgFileSize+"------------截取base64码部分");
					if(AllowImgFileSize!=0 && AllowImgFileSize < reader.result.length){
						layer.alert("上传失败，请上传不大于2M的图片！");
						return;
					}else{
						$('.headerImg').attr('src',reader.result);
						//console.log(reader.result+"--------reader.result");
					}
				}
			}
        })

		//关闭修复项目
		function closeRepairProject(thi){
			$(thi).parents(".optionsDetail").css("display","none");
			//清楚按钮选中样式
			var spanBtns = $("#addToothMapDiv").find(".repairClickBtn");
			for (var i = 0; i < spanBtns.length; i++) {
				 spanBtns.eq(i).removeClass("optionsOpen");
			}
		}
		
		/* 点击按钮选择修复项目 */
		function selectOptions(thi){
			//得到已经选中的项目id
			var alreadyDate=[];
			$(thi).find("span").each(function(i,obj){
				alreadyDate.push($(this).prop("id"));
			});
			// console.log(alreadyDate+"-----------alreadyDate已经选中数据");
			$(".optionsDetail").css("display","none");
			/* 清除其他按钮样式 */
			var spanBtns = $("#addToothMapDiv").find(".repairClickBtn");
			for (var i = 0; i < spanBtns.length; i++) {
				 spanBtns.eq(i).removeClass("optionsOpen");
			}
			$(thi).addClass("optionsOpen");
			//var orepairNameId=$(thi).find("span").text(); //一级修复项目ID
			var orepairNameId=$(thi).attr("seqid"); //一级修复项目ID
			var orepairName=$(thi).attr("selfname"); //一级修复项目名称
			//console.log(orepairNameId+"-----------修复Id");
			var twoRepairArr; //二级修复项目数组
			var url = contextPath + '/KQDS_MACHININGTypeAct/findPrimaryByParentId.act?parentId='+orepairNameId;
			$.ajax({  
               url : url,  
               type : 'get',//请求方式：get  
               async: false,
               data: {parentId:orepairNameId},
               dataType : 'json',//数据传输格式：json  
               error : function() {  
                   $.messager.alert("警告",'亲，请求失败！');  
                 },  
                 success : function(data) { 
                 	//console.log(JSON.stringify(data)+"=======data");
                     if(data!=null && data!=""){
                       //console.log(JSON.stringify(data)+"----------二级返回");
                       twoRepairArr=data;
                     }
                 }   
           });
			
			//console.log(twoRepairArr+"---------二级返回数据");
			var noThreeRepairHtml='<dl oneName='+orepairName+' oneId='+orepairNameId+'>'; // 无三级修复项目
			noThreeRepairHtml+='<dt class="twoOptionName"></dt>'; 
			noThreeRepairHtml+='<dd>';
			noThreeRepairHtml+='<div style="display:none;">';
			noThreeRepairHtml+='<span class="repairTwoName"></span>';
			noThreeRepairHtml+='<font class="repairTwoId"></font>';
			noThreeRepairHtml+='</div>';
			noThreeRepairHtml+='<ul id="" class="detailItem">';
			var twoRepairHtml = '<dl oneName='+orepairName+' oneId='+orepairNameId+'>'; // 有三级修复项目
			var havaThreerRepair=true; //判断是否有三级修复项目
			for (var i = 0; i < twoRepairArr.length; i++) {
				twoRepairHtml+='<dt class="twoOptionName">'+twoRepairArr[i].typename+'</dt>';
				twoRepairHtml+='<dd>';
				twoRepairHtml+='<div style="display:none;">';
				twoRepairHtml+='<span class="repairTwoName">'+twoRepairArr[i].typename+'</span>';
				twoRepairHtml+='<font class="repairTwoId">'+twoRepairArr[i].seqId+'</font>';
				twoRepairHtml+='</div>';
				twoRepairHtml+='<ul id="" class="detailItem">';
				var thirdRepairData=selectRepairProject(twoRepairArr[i].seqId); //三级返回数据
				//console.log(thirdRepairData+"--------二级返回数据");
				if(thirdRepairData){ 
					for (var j = 0; j < thirdRepairData.length; j++) {
						 twoRepairHtml+='<li onclick="thirdBtnClick(this);">';
						 twoRepairHtml+='<span class="repairThirdName">'+thirdRepairData[j].typename+'</span>';
						 twoRepairHtml+='<font class="repairThirdId">'+thirdRepairData[j].seqId+'</font>';
						 twoRepairHtml+='</li>';
					}
					twoRepairHtml+='</ul>';
					twoRepairHtml+='</dd>';
					
				}else{
					havaThreerRepair=false;
					noThreeRepairHtml+='<li onclick="thirdBtnClick(this);">';
					noThreeRepairHtml+='<span class="repairThirdName">'+twoRepairArr[i].typename+'</span>';
					noThreeRepairHtml+='<font class="repairThirdId">'+twoRepairArr[i].seqId+'</font>';
					noThreeRepairHtml+='</li>';
				}
				
			}
			//判断是否有三级修复项目
			if(havaThreerRepair){
				twoRepairHtml+='</dl>';
				twoRepairHtml+='<span class="closeIcon" onclick="closeRepairProject(this);"></span>';
				//twoRepairHtml+='<button class="optionsBtn" onclick="thirdBtnSave(this);">确认</button>';
				$(".optionsDetail").html(twoRepairHtml);
			}else{
				noThreeRepairHtml+='</ul>';
				noThreeRepairHtml+='</dd>';
				noThreeRepairHtml+='</dl>';
				noThreeRepairHtml+='<span class="closeIcon" onclick="closeRepairProject(this);"></span>';
				//noThreeRepairHtml+='<button class="optionsBtn" onclick="thirdBtnSave(this);">确认</button>';
				$(".optionsDetail").html(noThreeRepairHtml);
			}
			//判断已选修复项目	
			// console.log(JSON.stringify(alreadyDate)+"------alreadyDate");
			$(".optionsDetail").find("li").each(function(i,obj){
				var threeRepairId=$(this).find("font").text();
				for (var g = 0; g < alreadyDate.length; g++) {
					if(threeRepairId==alreadyDate[g]){
						$(this).addClass("thirdBtnCurrent");
					}
				}
			});

			//监听鼠标点击显示修复详情
			/* var y=$(thi).offset().top;
			var x=$(thi).offset().left;
			console.log(x+"--------------x");
			console.log(y+"--------------y"); */
			$(".optionsDetail").css("display","block");
			var left = event.clientX+$(this).scrollLeft();
			var windowWidth = $(window).width(); //当前窗口的宽度
			if(left + 515 > windowWidth){
				var exceedWidth = left + 515 - windowWidth;
				var exceedX=left - exceedWidth;
				$(".optionsDetail").css("top",event.clientY+$(this).scrollTop()).css("left",exceedX);
			}else{
				$(".optionsDetail").css("top",event.clientY+$(this).scrollTop()).css("left",event.clientX+$(this).scrollLeft());
			}
		}
		
		//修复项目查询
		function selectRepairProject(repairId){
			var selectRepairDate;
			var url = contextPath + '/KQDS_MACHININGTypeAct/findPrimaryByParentId.act?parentId='+repairId;
			$.ajax({  
               url : url,  
               type : 'get',//请求方式：get  
               async: false,
               dataType : 'json',//数据传输格式：json  
               error : function() {  
                   $.messager.alert("警告",'亲，请求失败！');  
                 },  
                 success : function(data) { 
                     if(data!=null && data!=""){
                    	 selectRepairDate=data;
                    	//console.log(JSON.stringify(data)+"=======data");
                     }
                 }   
           });
			return selectRepairDate;
		}
		
		// 三级标签点击事件
		function thirdBtnClick(obj){
			//console.log("三级点击事件");
			/* $(obj).toggleClass("thirdBtnCurrent"); */
		}
		
		//三级确认按钮
		function thirdBtnSave(obj){
			var optionsDetail=$(obj).parents(".optionsDetail");
			var ddBtns = optionsDetail.find("dd");
			/* var selectDate=[]; */
			$("#addToothMapDiv").find(".optionsOpen").html(""); 
			for (var i = 0; i < ddBtns.length; i++) {
				var thirdLis = ddBtns.eq(i).find("li");
				for (var j = 0; j < thirdLis.length; j++) {
					if(thirdLis.eq(j).hasClass("thirdBtnCurrent")){
						/* var repairObj={};  */
						var threeId = thirdLis.eq(j).find(".repairThirdId").text();//选中项目三级ID
						var threeName = thirdLis.eq(j).find(".repairThirdName").text();//选中项目三级名字
						$("#addToothMapDiv").find(".optionsOpen").append("<span id="+threeId+">"+threeName+"</span>");
						$("#addToothMapDiv").find(".optionsOpen").addClass("repairAlready"); //为选中的按钮添加选中样式
						/* repairObj.threeId=threeId;
						repairObj.threeName=threeName;
						selectDate.push(repairObj); */
					}/* else{
						$("#addToothMapDiv").find(".optionsOpen").html("");
					} */
				}
			}
			
			var alreadyThirdBtn=$(".optionsDetail").find("li.thirdBtnCurrent");
			//console.log(alreadyThirdBtn.length+"------------选中btn长度");
			if(alreadyThirdBtn.length==0){
				//console.log("没有选中的三级btn");
				var oneRepairId=optionsDetail.find("dl").attr("oneid"); //一级修复项目ID
				var oneRepairName=optionsDetail.find("dl").attr("onename"); //一级修复项目名字
				//console.log(oneRepairId+"-----------"+oneRepairName);
				$("#addToothMapDiv").find(".optionsOpen").attr("seqid",oneRepairId);
				$("#addToothMapDiv").find(".optionsOpen").text(oneRepairName);
			}
			
			// 隐藏选项div以及清除点击按钮样式
			$(".optionsDetail").css("display","none");
			var spanBtns = $("#addToothMapDiv").find(".repairClickBtn");
			for (var i = 0; i < spanBtns.length; i++) {
				 spanBtns.eq(i).removeClass("optionsOpen");
			}
		}
		
		//var toothMapIndex=0;
		 //var toothMapIndex=$("#addToothMapDiv").find(".toothMapAndBtn").length;
		//追加牙位图
		function addToothMap(){
			toothMapIndex++;
			$(".optionsDetail").css("display","none");//隐藏选项
			var spanBtns = $("#addToothMapDiv").find(".repairClickBtn");
			for (var i = 0; i < spanBtns.length; i++) {
				 spanBtns.eq(i).removeClass("optionsOpen");
			}
			var toothMapHtml=$("#toothMapItem").html();
			$("#addToothMapDiv").append(toothMapHtml);
			var showToothMaplength= $("#addToothMapDiv").find(".showToothMap").length;
			//$("#addToothMapDiv").find(".showToothMap").eq(showToothMaplength-1).attr("onclick","showToothMap("+toothMapIndex+")");
			$("#addToothMapDiv").find(".showToothMap").eq(showToothMaplength-1).addClass("toothMapIndex"+toothMapIndex);
		}
		
		//删除当前牙位图
		function deleteToothMap(obj){
			$(obj).parents(".toothMapAndBtn").remove();
		}
		
		/* 颈缘设计 */
		function showNeckFlangeDesign() {
		    var obj = document.getElementsByName("neckFlangeDesign");
		    var neckFlangeDesign = "";
		    for ( k in obj ) {
		        if(obj[k].checked)
		        	neckFlangeDesign = neckFlangeDesign + obj[k].value + ';';
		    }
		    return neckFlangeDesign;
		}
		/* 种植固定方式 */
		function showPlantFixed() {
		    var obj = document.getElementsByName("plantFixed");
		    var plantFixed = "";
		    for ( k in obj ) {
		        if(obj[k].checked)
		        	plantFixed = plantFixed + obj[k].value + ';';
		    }
		    return plantFixed;
		}
		
		//发送方法
		function send(){
			var url = contextPath + '/KQDS_MachineSendAct/saveMachineSend.act';
			$.ajax({   
		        type:"GET",   
		        url:url, //从服务器拿东西，地址自己写  
		        dataType:"json",  
		        async:false,
		        data: {
		        	username : loginUserName,
		        	usercode : $("#userCode").val(),
		        	phoneNumber : $("#phoneNumber").val(),
		        	sendcontent : $("#chantContent").val(),
		        	orderNumber : machineData.ordernumber,
		        	machineId : machineData.seqId,
		        	systemNumber : machineData.systemnumber
		        },
		        //下面这个函数是在ajax请求成功后才执行的，其中的参数data，就是
		        success:function(data){
		        	layer.alert("保存成功！");
		        } 
		    }); 
			initMachineSend(); //初始化聊天信息
			$("#chantContent").val("");
		}
		
		function initMachineSend() {
			var url = contextPath + '/KQDS_MachineSendAct/selectMachineSend.act';
			$.ajax({   
		        type:"GET",   
		        url:url, //从服务器拿东西，地址自己写  
		        dataType:"json",  
		        async:false,
		        data: {
// 		        	username : $("#userName").val(),
// 		        	usercode : $("#userCode").val(),
// 		        	phoneNumber : $("#phoneNumber").val(),
// 		        	sendcontent : $("#chantContent").val(),
// 		        	orderNumber : machineData.ordernumber,
// 		        	systemNumber : machineData.systemnumber,
		        	machineId : machineData.seqId
		        },
		        //下面这个函数是在ajax请求成功后才执行的，其中的参数data，就是
		        success:function(data){
// 		        	layer.alert("保存成功！");
					//console.log(JSON.stringify(data)+"-----------------555");
					if(data){
						var charItemHtml="";
						for (var i = 0; i < data.length; i++) {
							data[i];
							charItemHtml += '<li class="chatItem">';
							charItemHtml += '<span class="chatPerson">'+data[i].username+'</span>';
							charItemHtml += '<div class="chatRight">';
							charItemHtml += '<span class="chatTime">'+data[i].sendtime+'</span>';
							charItemHtml += '<span class="chatText"><img src="<%=contextPath%>/static/image/kqdsFront/jiagong/chatIcon.png"/>'+data[i].sendcontent+'</span>';
							charItemHtml += '</div>';
							charItemHtml += '</li>';
						}
						$("#chatInfo").html(charItemHtml);
					}
		        } 
		    }); 
		}
		
	  function doPrint() {   
		  	// 判断是否有输入配件，没有则全部隐藏
			var fittingsInput=$(".fittings").find("input[value='']").length;
			if(fittingsInput==17){
				$(".fittings").addClass("fittingsHidden");
				$(".fittingsName").addClass("fittingsNameHidden");
			}
		    bdhtml=window.document.body.innerHTML;   
		    sprnstr="<!--startprint-->";   
		    eprnstr="<!--endprint-->";   
		    prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);   
		    prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));   
		    var htmlStyle="<style>*{font-size:12px!important;}button,.addTooth{display:none;}.showToothMap{display:none!important;}.line{margin-top:57px;}.toothLine{top:0px!important;}#logoImg{text-align:left!important;width:80px!important;left:0%!important;top:17px!important;}";
			htmlStyle += ".toothMap{padding: 33px 0%!important;}.bigtitle{font-size:24px!important;}.repairClickBtn{display:none!important;}.repairAlready{display:inline-block!important;}.baseInfo{padding-bottom:5px!important;}.noValue{display:none;}";
			htmlStyle += "#importChat{display:none;border:1px solid red!important;}.fittingsHidden,.fittingsNameHidden{display: none;}</style>";
		    window.document.body.innerHTML=prnhtml+htmlStyle;  
		    window.print();  //打印
		    document.body.innerHTML=bdhtml; //恢复页面
		} 
		
		//打印方法
		function myPreviewAll(){
			//console.log("打印***");
			LODOP=getLodop();  
			LODOP.PRINT_INIT("");
			LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");
			// LODOP.ADD_PRINT_SETUP_BKIMG(strHtmFile);
			var htmlStyle="<style>button,.addTooth{display:none;}.showToothMap{display:none!important;}.line{margin-top:57px;}.toothLine{top:0px!important;}.toothBox{margin-left:20px;width:195px!important;margin-top:35%!important;border:1px solid red;}#logoImg{text-align:left!important;width:80px!important;left:0%!important;top:17px!important;}";
			htmlStyle += ".toothMap{height:55px!important;padding-top:0px!important;}.verticalLine{top:0px!important;height:50px!important;}.bigtitle{font-size:24px!important;}.repairClickBtn{display:none!important;}.repairAlready{display:inline-block!important;}.toothBox li{width:12px;}.baseInfo{padding-bottom:5px!important;}.noValue{display:none;}";
			htmlStyle += "#importChat{display:none;border:1px solid red!important;}</style>";
			var html="<!DOCTYPE html>"+document.getElementsByTagName("html")[0].innerHTML+htmlStyle;
			LODOP.ADD_PRINT_HTM(0,0,"100%","100%",html);
			LODOP.SET_PRINT_STYLEA(0,"ItemType",2);//设置上面的为页眉页脚，每页固定位置输出
			
			var hkey_root,hkey_path,hkey_key; 
			hkey_root="HKEY_CURRENT_USER"; hkey_path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
			var RegWsh = new ActiveXObject("WScript.Shell");
			//设置页脚
			hkey_key="footer"; 
			RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"&b第 &p 页/共 &P 页"); 
			// LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW",1);//打印包含背景图 
			LODOP.PREVIEW();	
		};
	
	</script>

</html>