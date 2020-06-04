<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
	String lcljId = request.getParameter("lcljId");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="css/base.css" />
<link type="text/css" rel="stylesheet" href="css/index.css" />
<link type="text/css" rel="stylesheet" href="../../../../plugin/layer-v2.4/layer/skin/layer.css" />

<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" /> --%>

<script type="text/javascript" src="js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="../../../../js/hudh/commont.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/btnfuc.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/kqds/kqds_system.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<style type="text/css">
#variation_table {
	font-size: 13px;
	margin: 0px 15px;
}
/* 变异节点div */
#variation_table .variation_nodeinfo {
	display: flex;
	margin-bottom: 10px;
	border-bottom: 1px solid #ddd;
}
/* 变异节点名字 */
#variation_table .node_name {
	display: block;
	width: 100%;
	font-size: 15px;
	font-weight: bold;
	margin-bottom: 10px;
	padding-bottom: 10px;
	border-bottom: 1px solid #ddd;
}
/* 变异节点次数 */
#variation_table .variation_nodeinfo>.node_time {
	width: 8%;
/* 	width: 15%; */
/* 	text-align: center; */
	font-weight: bold;
}
/* 详细变异内容 */
#variation_table .variation_nodeinfo .variation_list {
	width: 85%;
	display: flex;
	-webkit-flex-wrap: wrap;
	flex-wrap: wrap;
}

#variation_table .variation_nodeinfo .variation_list>li {
	width: 25%;
	margin-bottom: 10px;
}
#buttom_info .blue_btn{
	display: inline-block;
	text-align: center;
}
#continer #top #ywbd .blue_btn{
	width: 80px;
    font-size: 14px;
    line-height: 26px;
    color: white;
    background-color: #00A6C0;
    border-radius: 5px;
    border: 0px;
    cursor: pointer;
}
</style>
</head>
<body>
	<div id="continer">
		<!-- 基本信息 -->
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="" href="#collapseOne" class="smallTitle">基本信息 </a>
				</h4>
			</div>
			<div id="collapseOne" class="panel-collapse collapse in">
				<div class="panel-body">
					<div class="widget-body">
						<div class="widget-main" style="overflow: hidden;">
							<div id="hzname" class="col-xs-12 col-sm-3 col-md-2 control-label clo-itsm hr2 text-left" title="事件编号">患者姓名：
							</div>
							<div id="blcode" class="col-xs-12 col-sm-3 col-md-2 control-label clo-itsm hr2 text-left" title="事件编号">病历号：
							</div>
							<div id="hzphone" class="col-xs-12 col-sm-3 col-md-2 control-label clo-itsm hr2 text-left" title="事件编号">患者电话：
							</div>
							<div id="hzsex" class="col-xs-12 col-sm-3 col-md-2 control-label clo-itsm hr2 text-left" title="事件编号">患者性别：
							</div>
							<div id="hzage" class="col-xs-12 col-sm-3 col-md-2 control-label clo-itsm hr2 text-left" title="事件编号">患者年龄：
							</div>
							<!-- <div id="counsellor" class="col-xs-12 col-sm-2 control-label clo-itsm hr2 text-left" title="事件编号">咨询师：
							</div> -->
							<div id="plant_physician" class="col-xs-12 col-sm-3 col-md-2 control-label clo-itsm hr2 text-left" title="事件编号">种植医师：
							</div>
							<div id="repair_physician" class="col-xs-12 col-sm-3 col-md-2 control-label clo-itsm hr2 text-left" title="事件编号">修复医师：
							</div>
							<!-- <div id="clinic_nurse" class="col-xs-12 col-sm-2 control-label clo-itsm hr2 text-left" title="事件编号">诊室护士：
							</div>
							<div id="customer_service" class="col-xs-12 col-sm-2 control-label clo-itsm hr2 text-left" title="事件编号">客服：
							</div> -->
							<!-- <div id="plant_system" class="col-xs-12 col-sm-3 col-md-2 control-label clo-itsm hr2 text-left" title="事件编号" style="width: auto;">种植系统：
							</div>
							<div id="crown_material" class="col-xs-12 col-sm-3 col-md-2 control-label clo-itsm hr2 text-left" title="事件编号" style="/* margin-left: 100px; */width: auto;">牙冠材质：
							</div> -->
							<div class="col-xs-12 col-sm-3 col-md-2 control-label clo-itsm hr2 text-left" style="width: auto;">
								<div id="plant_system" title="事件编号" style="display: inline-block;margin-right: 70px;">种植系统：
								</div>
								<div id="crown_material" title="事件编号" style="display: inline-block;">牙冠材质：
								</div> 
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--上部-->
		<div id="top">
			<!--患者详细信息-->
			<div id="top_left" class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
				<span class="smallTitle">手术状态</span>
				<div id="p_details">
					<span>编号：<font id="patient_id"></font></span> 
					<span>创建时间：<font id="patient_creatime"></font></span> 
					<span>流程环节：<font id="patient_flowlink"></font></span> 
					<span>手术类型：<font id="patient_type"></font></span> 
					<span style="background-color: white;color: #434343;font-weight: bold;font-size: 16px;border-bottom-left-radius: 10px;border-bottom-right-radius: 10px;">
					状态：<font id="patient_status" style="color:#3e9f31;"></font>
					</span>
				</div> 
			</div>
			<!--牙位图-->
			<!-- <div id="ToothBit" style="border:1px solid red;"> -->
			<!--种植-->
			<div id="zhongzhi" class="toothBox adult col-xs-6 col-sm-6 col-md-4 col-lg-4">
				<span class="smallTitle aa">种植牙位：</span>
				<div class="toothMap" onclick="showToothMap(0);">
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
					<span class="showToothMap" style="display:none;width: 100px;height: 28px;border-radius: 5px;/* margin-left: 41%; */background: #00a6c0;color: #fff;padding: 7px;">更改牙位</span>
				</div>
			</div>			
			<!--修复-->
			<div id="xiufu" class="toothBox adult col-xs-6 col-sm-6 col-md-4 col-lg-4" style="margin-right: 0%;/* float:right; */">
				<span class="smallTitle">修复牙位：</span>
				<div class="toothMap" onclick="showToothMap(1);">
					<ul class="upYa">
						<!-- <li><input class="ToothBit_checkbox" type="checkbox" id="lefttop" class="lefttop" disabled="disabled" /></li> -->
						<li class="leftUpTooth"><span class="yaIcon le8 ToothBit_checkbox5"></span><span class="num numle8" name="zzadultupYa1">8</span></li>
						<li class="leftUpTooth"><span class="yaIcon le7 ToothBit_checkbox5"></span><span class="num numle7" name="zzadultupYa1">7</span></li>
						<li class="leftUpTooth"><span class="yaIcon le6 ToothBit_checkbox5"></span><span class="num numle6" name="zzadultupYa1">6</span></li>
						<li class="leftUpTooth"><span class="yaIcon le5 ToothBit_checkbox5"></span><span class="num numle5" name="zzadultupYa1">5</span></li>
						<li class="leftUpTooth"><span class="yaIcon le4 ToothBit_checkbox5"></span><span class="num numle4" name="zzadultupYa1">4</span></li>
						<li class="leftUpTooth"><span class="yaIcon le3 ToothBit_checkbox5"></span><span class="num numle3" name="zzadultupYa1">3</span></li>
						<li class="leftUpTooth"><span class="yaIcon le2 ToothBit_checkbox5"></span><span class="num numle2" name="zzadultupYa1">2</span></li>
						<li class="leftUpTooth"><span class="yaIcon le1 ToothBit_checkbox5"></span><span class="num numle1" name="zzadultupYa1">1</span></li>
	
						<li class="rightUpTooth"><span class="yaIcon rg1 ToothBit_checkbox6"></span><span class="num numrg1" name="zzadultupYa2">1</span></li>
						<li class="rightUpTooth"><span class="yaIcon rg2 ToothBit_checkbox6"></span><span class="num numrg2" name="zzadultupYa2">2</span></li>
						<li class="rightUpTooth"><span class="yaIcon rg3 ToothBit_checkbox6"></span><span class="num numrg3" name="zzadultupYa2">3</span></li>
						<li class="rightUpTooth"><span class="yaIcon rg4 ToothBit_checkbox6"></span><span class="num numrg4" name="zzadultupYa2">4</span></li>
						<li class="rightUpTooth"><span class="yaIcon rg5 ToothBit_checkbox6"></span><span class="num numrg5" name="zzadultupYa2">5</span></li>
						<li class="rightUpTooth"><span class="yaIcon rg6 ToothBit_checkbox6"></span><span class="num numrg6" name="zzadultupYa2">6</span></li>
						<li class="rightUpTooth"><span class="yaIcon rg7 ToothBit_checkbox6"></span><span class="num numrg7" name="zzadultupYa2">7</span></li>
						<li class="rightUpTooth"><span class="yaIcon rg8 ToothBit_checkbox6"></span><span class="num numrg8" name="zzadultupYa2">8</span></li>
						<!-- <li><input class="ToothBit_checkbox" type="checkbox" id="righttop" disabled="disabled" /></li> -->
					</ul>
					<div class="line">
						<!-- <span class="left">右</span> <span class="right">左</span> -->
					</div>
					<div class="verticalLine"></div>
					<ul class="downYa">
						<!-- <li><input class="ToothBit_checkbox" type="checkbox" id="leftdown" disabled="disabled" /></li> -->
						<li class="leftDownTooth"><span class="num numle8 ToothBit_checkbox7" name="zzadultdownYa1">8</span><span class="yaIcon le8"></span></li>
						<li class="leftDownTooth"><span class="num numle7 ToothBit_checkbox7" name="zzadultdownYa1">7</span><span class="yaIcon le7"></span></li>
						<li class="leftDownTooth"><span class="num numle6 ToothBit_checkbox7" name="zzadultdownYa1">6</span><span class="yaIcon le6"></span></li>
						<li class="leftDownTooth"><span class="num numle5 ToothBit_checkbox7" name="zzadultdownYa1">5</span><span class="yaIcon le5"></span></li>
						<li class="leftDownTooth"><span class="num numle4 ToothBit_checkbox7" name="zzadultdownYa1">4</span><span class="yaIcon le4"></span></li>
						<li class="leftDownTooth"><span class="num numle3 ToothBit_checkbox7" name="zzadultdownYa1">3</span><span class="yaIcon le3"></span></li>
						<li class="leftDownTooth"><span class="num numle2 ToothBit_checkbox7" name="zzadultdownYa1">2</span><span class="yaIcon le2"></span></li>
						<li class="leftDownTooth"><span class="num numle1 ToothBit_checkbox7" name="zzadultdownYa1">1</span><span class="yaIcon le1"></span></li>
	
						<li class="rightDownTooth"><span class="num numrg1 ToothBit_checkbox8" name="zzadultdownYa2">1</span><span class="yaIcon rg1"></span></li>
						<li class="rightDownTooth"><span class="num numrg2 ToothBit_checkbox8" name="zzadultdownYa2">2</span><span class="yaIcon rg2"></span></li>
						<li class="rightDownTooth"><span class="num numrg3 ToothBit_checkbox8" name="zzadultdownYa2">3</span><span class="yaIcon rg3"></span></li>
						<li class="rightDownTooth"><span class="num numrg4 ToothBit_checkbox8" name="zzadultdownYa2">4</span><span class="yaIcon rg4"></span></li>
						<li class="rightDownTooth"><span class="num numrg5 ToothBit_checkbox8" name="zzadultdownYa2">5</span><span class="yaIcon rg5"></span></li>
						<li class="rightDownTooth"><span class="num numrg6 ToothBit_checkbox8" name="zzadultdownYa2">6</span><span class="yaIcon rg6"></span></li>
						<li class="rightDownTooth"><span class="num numrg7 ToothBit_checkbox8" name="zzadultdownYa2">7</span><span class="yaIcon rg7"></span></li>
						<li class="rightDownTooth"><span class="num numrg8 ToothBit_checkbox8" name="zzadultdownYa2">8</span><span class="yaIcon rg8"></span></li>
						<!-- <li><input class="ToothBit_checkbox" type="checkbox" id="rightdown" disabled="disabled" /></li> -->
					</ul>
					<span class="showToothMap" style="display:none;width: 100px;height: 28px;border-radius: 5px;/* margin-left: 41%; */background: #00a6c0;color: #fff;padding: 7px;">更改牙位</span>
				</div>
			</div>
			<!-- </div> -->
			<div style="width:50px;height:30px;/* margin-left: 55%; */display: inline-block; position: absolute;top:20px;right:30px;text-align:right;" id = "ywbd">
				<button href="javascript:void(0);" class="blue_btn hide" onclick="buttonFun.toothEditor(this);" id="lclj_ywbd">牙位变动</button>	
				<button href="javascript:void(0);" class="blue_btn hide" onclick="buttonFun.toothEditorB(this);" id="lclj_ywtj">提交</button>		
			</div>
		</div>
		<!--中间流程图-->
		<div class="center_bg panel-collapse collapse in" style="margin-top:20px;">
			<div id="center">
			<span></span>
			<ul id="flow_path"></ul>
<!-- 			<i class="circle"></i> -->
			<ul class="example">
				<li><span><font style="background-color: #b5b5b5;"></font>未完成</span></li>
				<li><span><font style="background-color: #58bb35;"></font>进行中</span></li>
				<li><span><font style="background-color: #00a6c0;"></font>已完成</span></li>
				<li><span><font style="background-color: #d10c0c;"></font>超期</span></li>
				<li><span><font style="background-color: #f0e91e;"></font>已退回</span></li>
			</ul>
		</div>
		</div>
		<!-- 操作内容 -->
		<div class="panel-group" id="accordion">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a class="smallTitle" data-toggle="collapse" data-parent="" href="#collapseTwo">操作内容 </a>
					</h4>
				</div>

				<div id="collapseTwo" class="panel-collapse collapse in">
					<div class="panel-body" id="operation">
						<div class="widget-body operate" style="index: 100;">
							<iframe name="myiframe" id="myiframe" class="myiframe" src="" frameborder="0" width="1660" height="320" scrolling="yes" style="index: 100;">
							</iframe>
						</div>
					</div>
				</div>
			</div>
			<!-- 底部内容 -->
			<div class="detailsBottom">
			<!-- 挂号记录 -->
			<div class="panel panel-default registrationPanel col-xs-12 col-sm-12 col-md-4 col-lg-4">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a class="smallTitle" data-toggle="collapse" data-parent="" href="#collapseThree">挂号记录 </a>
					</h4>
				</div>
				<div id="collapseThree" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="widget-body">
							<div class="widget-main">
								<div class="col-xs-12 col-sm-12 control-label clo-itsm hr2 text-left" title="事件编号">
									<table id="register_table" cellspacing="0" cellpadding="0">
										<thead>
											<tr>
												<th style="border-top-left-radius: 9px;overflow: hidden;width:10%;"><span>序号</span></th>
												<th style="width:25%"><span>挂号时间</span></th>
												<th style="width:15%"><span>就诊分类</span></th>
												<th style="width:15%"><span>挂号分类</span></th>
												<!-- <th>缴费</th> -->
												<th style="border-top-right-radius: 9px;overflow: hidden;width:20%"><span>种植医生</span></th>
												<!-- <th>修复医生</th> -->
											</tr>
										</thead>
										<tbody id="register_tbody"></tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="remarkVariation col-xs-12 col-sm-12 col-md-8 col-lg-8">
			<!-- 备注记录 -->
			<div class="panel panel-default remarkPanel">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a class="smallTitle" data-toggle="collapse" data-parent="" href="#collapseFour">备注记录 </a>
					</h4>
				</div>
				<div id="collapseFour" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="widget-body">
							<div class="widget-main">
								<div class="col-xs-12 col-sm-12 control-label clo-itsm hr2 text-left" title="事件编号">
									<table id="register_table" cellspacing="0" cellpadding="0">
										<thead>
											<tr>
												<th style="border-top-left-radius: 9px;overflow: hidden;width:5%;"><span>序号</span></th>
												<th style="width:20%"><span>备注时间</span></th>
												<th style="width:15%"><span>备注人</span></th>
												<th style="width:35%"><span>备注内容</span></th>
												<th style="border-top-right-radius: 9px;overflow: hidden;width:15%"><span>备注节点名称</span></th>
											</tr>
										</thead>
										<tbody id="remark_tbody" style="height:95px;"></tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<!-- 变异情况 -->
			<div class="panel panel-default variationPanel">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="" href="#collapsevariation">变异记录</a>
					</h4>
				</div>

				<div id="collapsevariation" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="widget-body" style="index: 100;height:153px;overflow: scroll;">
							<div id="variation_table"></div>
						</div>
					</div>
				</div>
			</div>
			</div>
			</div>
		</div>
	</div>
	<div id="buttom_info" style="background-color: #f5f5f5;">
		<a href="javascript:void(0);"  class="blue_btn hide" onclick = "buttonFun.goAssessor(this);" id="lclj_check" >审核</a>
		<a href="javascript:void(0);" class="blue_btn hide" onclick="buttonFun.electiveOperation(this);" id="lclj_yy">预约</a> 
		<button href="javascript:void(0);" class="blue_btn hide" onclick="buttonFun.gotoNext(this);" id="lclj_tj">提交</button> 
		<a href="javascript:void(0);" class="blue_btn hide" onclick="buttonFun.reject(this);" id="lclj_th">退回</a> 
		<button href="javascript:void(0);" class="blue_btn hide" onclick="buttonFun.variationSave(this);" id="lclj_by">变异</button>
		<a href="javascript:void(0);" class="blue_btn hide" onclick="buttonFun.rejectRecord(this);" id="lclj_thjl">退回记录</a>
		<a href="javascript:void(0);" class="blue_btn hide" onclick="buttonFun.saveRemark(this);" id="lclj_bcbz">保存备注</a>
	</div>
</body>
<script type="text/javascript">
	var contextPath = "<%=contextPath%>";
	var lcljId = "<%=lcljId%>";
//	var menuid = getUrlParam(menuid);
	var menuid = parent.menuid;
	var listbutton;
	var person = '<%=person.getUserName()%>';
	var personId = '<%=person.getSeqId()%>';
	var reloadVal="0";//用于子页面刷新标识
	var menuid=window.parent.menuid;//左侧菜单id
	$(function() {
		
		/* 针对ipad自适应  2020/05/04 */
		var userAgent = navigator.userAgent; 
		if (userAgent.indexOf("iPad") > -1){
			$("#top_left").css("padding-right","0px");
			$("#zhongzhi").css("padding-left","0px");
			$("#xiufu").css("padding-right","0px");
		}
		/* ******************** */
		
		//变异内容展示
		var variation_patient_id = $("#patient_id").text();
		var variation_node_id;
		var liList = $("#flow_path").find("li");
		for (var i = 0; i < liList.length; i++) {
			var nodestatus = liList.eq(i).find("span").attr("nodestatus");
			if (nodestatus == 1) {
				variation_node_id = liList.eq(i).find("span").attr("id");
			}
		};

		$.ajax({
			url : apppath + "/HUDH_LcljOperationNodeInforAberranceAct/findOperationNodeInforAberranceByOrderNumberAndNodeId.act",
			type : "POST",
			dataType : "json",
			data : {
				"order_number" : variation_patient_id,
				"nodeId" : variation_node_id
			},
				success : function(data) {
				showVariationInfo(data); 
			}
		});
		
		//全局监听
        /* document.addEventListener("click",function(event){
        	event=event||window.event;
            var eve=event.target||eve.elementSrc;
        	if(eve.className=='ask_Previous' || eve.className=='examine_diagnose' || eve.className=='diagnosis_case' || eve.className=='xiufu_test'){
        	}else{
        		$("#myiframe").contents().find(".caseContiner").each(function(i,obj){
        			$(this).css("display","none");
        		});
        	}
        });//所有组件添加点击事件  */
        
	});
	//更改牙位：弹出牙位图
	function showToothMap(i){
		var mapTitle="";
		if(i==0){
			mapTitle="种植牙位";
		}else{
			mapTitle="修复牙位";
		}
		layer.open({
			type: 2,
			title: mapTitle,
			shadeClose: false,
			shade: 0.6,
			area: ['800px', '450px'],
			content: contextPath +'/static/css/hudh/lclj/flowdetail/toothMap.jsp?index='+i
		});
	}
	
	/**
	 *  设置按钮权限操作 
	 */
	function getButtonPower() {
		var menubutton1 = "";
		// 创建一个数组，存放listbutton的qxName 
		var listbuttonArray = new Array();
		for (var i = 0; i < listbutton.length; i++) {
			listbuttonArray[i] = listbutton[i].qxName;
			//console.log("权限按钮===="+listbuttonArray[i]);
		}
		/* 按钮 */
		var btnList = '[';
		btnList	+= '{"qx":"lclj_check","name":"审核"},'; // 最后一个不要逗号
		btnList += '{"qx":"lclj_yy","name":"预约"},';
		btnList += '{"qx":"lclj_tj","name":"提交"},';
		btnList += '{"qx":"lclj_th","name":"退回"},'; // 最后一个不要逗号
		btnList += '{"qx":"lclj_by","name":"变异"},'; // 最后一个不要逗号
		btnList += '{"qx":"lclj_ywbd","name":"牙位变动"},'; // 最后一个不要逗号
		btnList += '{"qx":"lclj_ywtj","name":"牙位提交"},'; // 最后一个不要逗号
		btnList += '{"qx":"lclj_thjl","name":"退回记录"},'; // 最后一个不要逗号
		btnList += '{"qx":"lclj_bcbz","name":"保存备注"}'; // 最后一个不要逗号
		btnList += ']';
		var jsonbtnList = jQuery.parseJSON(btnList);
		for (var i = 0; i < jsonbtnList.length; i++) {
			//console.log("权限按钮===="+JSON.stringify(jsonbtnList[i].qx));
			// 判断当前用户具备的按钮权限
			var index = jQuery.inArray(jsonbtnList[i].qx, listbuttonArray);
			// index = -1 时，表示当前用户没有此按钮的操作权限
			if (index == -1) {//无权限的展示
			} else {//有权限的展示
				$("#" + jsonbtnList[i].qx).removeClass("hide");
			}
		}
	}
	
</script>
</html>
