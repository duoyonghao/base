<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	// 获取从左侧菜单点击带过来的菜单id
	String seqId = request.getParameter("seqId");
	//当前诊疗方案id
	String seqidFather = request.getParameter("seqidFather");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>

	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/hudh/lclj/flowdetail/css/base.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
	<link type="text/css" rel="stylesheet" href="<%=contextPath%>/static/css/kqdsFront/plantCase/diagnosisProject.css" />

	<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>

	<style type="text/css">
		label{
			font-weight: normal;
			margin-top: 12px;
		}
		#img,#patientimg{
			width: 156px;
			height: 43px;
		}
		img[src=""],img:not([src]){
			opacity:0;
		}
		/*#logoImg{
			width: 10%;
			margin: 10px 0;
		}*/
		/*分隔线 */
		.line {
			display: block;
			border-top:2px dotted #776c6c;
			padding:10px 0;
		}
		@page {
			size: auto;
			margin: 0mm auto;
		}
		#replaceBox{
			display: none;
			height: auto;
			border:1px solid rgb(221, 221, 221);
			margin:10px;
			overflow-x: hidden;
			white-space: pre-wrap;
		}
		@media print {
			.consent_remark{
				margin-top: 40px !important;
			}
			.zl_signature>span{
				margin-top: 5px;
			}
			#replaceBox{
				font-size: 12px;
			}
			#logoImg{
				position: absolute;
				top: 20px!important;
				width: 150px!important;
			}
			.bigtitle{
				font-size: 22px!important;
				margin: 0px auto 10px !important;
			}
		}


		#logoImg{
			position: absolute;
			top: 18px;
			width: 200px;
		}
		.bigtitle{
			display: block;
			text-align: center;
			margin: 7px auto 18px;
			font-size: 26px;
			line-height: 26px;
			letter-spacing: 1px;
			font-weight: bold;
			color: #434343;
			padding-top: 30px;
		}


	</style>
</head>
<body>
<!--startprint-->
<div style="margin: 17px;">

	<!-- 标题 -->
	<div id="diagnosis_continer" class="container-fluid twopage">
		<div class="row">
			<div class="col-md-12 col-sm-12" style="position: relative;padding: 0;">
				<img id="logoImg" src="<%=contextPath%>/static/image/kqdsFront/jiagong/logoName.png">
				<span class="bigtitle">诊疗方案</span>
			</div>
		</div>


		<div id="diagnosis_continer" class="container-fluid">
			<!-- 患者信息 -->
			<div class="row" style="border-top: 2px solid #776c6c;">
				<div class="col-md-3 col-sm-3 col-xs-3 colDefined">
					<!-- 信息输入组合框 -->
					<div class="rpInfo_import">
						<span>患者姓名：</span>
						<input id="patient_name" type="text" disabled="disabled"/>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-3 colDefined">
					<!-- 信息输入组合框 -->
					<div class="rpInfo_import">
						<span>患者编号：</span>
						<input id="patient_usercode" type="text" disabled="disabled"/>
					</div>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
					<!-- 信息输入组合框 -->
					<div class="rpInfo_import">
						<span>性别：</span>
						<input id="patient_sex" type="text" disabled="disabled"/>
					</div>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
					<!-- 信息输入组合框 -->
					<div class="rpInfo_import">
						<span>年龄：</span>
						<input id="patient_age" type="text" disabled="disabled"/>
					</div>
				</div>
				<div class="col-md-1 col-sm-1 col-xs-1 colDefined"></div>
			</div>
			<!-- 第一行 -->
			<div class="row teethClean">
				<div class="col-md-1 col-sm-1 col-xs-1 colDefined">
					<!-- 选项框 -->
					<div class="zl_optiondiv" style="line-height: 40px;">
						<input type="checkbox" value="洁牙" name="treamentplan" id="treamentplanA"  style="vertical-align: text-bottom;"/>
						<label for="treamentplanA">洁牙</label>
					</div>
				</div>
				<div class="col-md-1 col-sm-1 col-xs-1 colDefined">
					<!-- 选项框 -->
					<div class="zl_optiondiv" style="line-height: 40px;">
						<input type="checkbox" value="刮治" name="treamentplan" id="treamentplanB" style="vertical-align: text-bottom;"/>
						<label for="treamentplanB">刮治</label>
					</div>
				</div>
				<div class="col-md-1 col-sm-1 col-xs-1 colDefined"></div>
			</div>
			<div class="backGauge">
				<!-- 种植 -->
				<div class="row">
					<div class="col-md-4 col-sm-4 col-xs-4 colDefined">
						<!-- 牙位图 -->
						<div class="zl_toothMapdiv">
							<div class="toothExtraction" style="text-align: center;width: 97%;">拔牙</div>
							<ul class="tooth_map" style="width:80%;height:80px;margin-left: 10%;">
								<li>
									<input id="extractionleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
								</li>
								<li>
									<input id="extractionrightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
								</li>
								<li>
									<input id="extractionleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
								</li>
								<li>
									<input id="extractionrightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
								</li>
							</ul>
						</div>
					</div>
					<div class="col-md-4 col-sm-4 col-xs-4 colDefined">
						<!-- 牙位图 -->
						<div class="zl_toothMapdiv">
							<div class="toothExtraction">种植：
								<span class="one">第一次</span>
							</div>

							<ul class="tooth_map" style="width:80%;margin-left: 10%;">
								<li>
									<input id="implantfirsttimelu" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
								</li>
								<li>
									<input id="implantfirsttimeru" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
								</li>
								<li>
									<input id="implantfirsttimeld" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
								</li>
								<li>
									<input id="implantfirsttimerd" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
								</li>
							</ul>
						</div>
					</div>
					<div class="col-md-4 col-sm-4 col-xs-4 colDefined">
						<!-- 牙位图 -->
						<div class="zl_toothMapdiv">
							<div style="margin: 5px 0;text-align: center;width: 98%;">第二次</div>
							<ul class="tooth_map" style="width:80%;margin-left: 10%;">
								<li>
									<input id="implantsecondtimelu" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
								</li>
								<li>
									<input id="implantsecondtimeru" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
								</li>
								<li>
									<input id="implantsecondtimeld" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
								</li>
								<li>
									<input id="implantsecondtimerd" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
								</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- 骨粉 -->
				<div class="row bone">
					<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
						<!-- 填写组合框 -->
						<div class="writein_group">
							<!-- <span>骨粉：</span> -->
							<ul>
								<li style="width: 16%;">
									<!-- 填写框 -->
									<div class="zl_fillWritediv">
										<span style="font-weight:600;">骨粉：</span>

									</div>
								</li>
								<li style="margin-right: 18px;">
									<!-- 填写框 -->
									<div class="zl_fillWritediv">
										<span>Bioss(大颗粒)</span>
										<input type="text" onblur="TextLengthCheck(this.id,10);" id="largeparticle"/>
										<span>g</span>
									</div>
								</li>
								<li style="margin-right: 18px;">
									<div class="zl_fillWritediv">
										<span>(小颗粒)</span>
										<input type="text" onblur="TextLengthCheck(this.id,10);" id="smallparticle"/>
										<span>g</span>
									</div>
								</li>
								<li style="margin-right: 18px;">
									<div class="zl_fillWritediv">
										<span>骨胶原</span>
										<input type="text" onblur="TextLengthCheck(this.id,10);" id="bonecollagen"/>
										<span>mg</span>
									</div>
								</li>
								<li>
									<div class="zl_fillWritediv">
										<span>天博</span>
										<input type="text" onblur="TextLengthCheck(this.id,10);" id="tianbo"/>
										<span>g</span>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- 骨膜 -->
				<div class="row periost">
					<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
						<!-- 填写组合框 -->
						<div class="writein_group periosteum">
							<ul>
								<li style="width: 16%;">
									<!-- 填写框 -->
									<div class="zl_fillWritediv">
										<span style="font-weight:600;">骨膜：</span>
									</div>
								</li>
								<li style="margin-right: 45px;">
									<!-- 填写框 -->
									<div class="zl_fillWritediv">
										<span>胶原膜大</span>
										<input type="text" onblur="TextLengthCheck(this.id,10);" id="collagenmembbig"/>
										<span>张</span>
									</div>
								</li>
								<li style="margin-right: 50px;">
									<div class="zl_fillWritediv">
										<span>小</span>
										<input type="text" onblur="TextLengthCheck(this.id,10);" id="collagenmembsmall"/>
										<span>张</span>
									</div>
								</li>
								<li style="width: 22%;">
									<div class="zl_fillWritediv">
										<span>钛网</span>
										<input type="text" onblur="TextLengthCheck(this.id,10);" id="titaniummesh"/>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- 种植辅助手术 -->
				<div class="row bone">
					<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
						<!-- 多选框 -->
						<div  class="zl_multiple auxiliary_operation">
							<!-- 选项 -->
							<ul>
								<li style="width: 16%;">
									<!-- 选项框 -->
									<div class="zl_optiondiv">
										<div style="line-height: 30px;font-weight:600;">种植辅助手术：</div>
									</div>
								</li>
								<li style="width: 15%;">
									<!-- 选项框 -->
									<div class="zl_optiondiv">
										<input type="checkbox" value="上颌窦提升" name="implantassistantsurgery" id="implantassistantsurgeryA" style="vertical-align: text-bottom;"/>
										<label for="implantassistantsurgeryA">上颌窦提升</label>
									</div>
								</li>
								<!-- <li>
                                    <select style="background-color: transparent;width: 70%;height: 30px;margin-top: 10px;outline: none;">
                                        <option>请选择<option>
                                        <option>内提<option>
                                        <option>外提<option>
                                    </select>
                                </li> -->
								<li style="width: 13%;">
									<div class="zl_optiondiv">
										<input type="checkbox" value="内提" name="implantassistantsurgery" id="implantassistantsurgeryB" style="vertical-align: text-bottom;"/>
										<label for="implantassistantsurgeryB"><!-- ( -->内提</label>
									</div>
								</li>
								<li style="width: 13%;">
									<div class="zl_optiondiv">
										<input type="checkbox" value="外提" name="implantassistantsurgery" id="implantassistantsurgeryC" style="vertical-align: text-bottom;"/><!-- <span>)</span> -->
										<label for="implantassistantsurgeryC">外提</label>
									</div>
								</li>
								<li>
									<!-- 选项框 -->
									<div class="zl_optiondiv">
										<input type="checkbox" value="骨劈开" name="implantassistantsurgery" id="implantassistantsurgeryD" style="vertical-align: text-bottom;"/>
										<label for="implantassistantsurgeryD">骨劈开</label>
									</div>
								</li>
								<li>
									<!-- 选项框 -->
									<div class="zl_optiondiv">
										<input type="checkbox" value="骨挤压" name="implantassistantsurgery" id="implantassistantsurgeryE" style="vertical-align: text-bottom;"/>
										<label for="implantassistantsurgeryE">骨挤压</label>
									</div>
								</li>
								<li style="width: 15%;">
									<!-- 选项框 -->
									<div class="zl_optiondiv">
										<input type="checkbox" value="自体骨移植" name="implantassistantsurgery" id="implantassistantsurgeryF" style="vertical-align: text-bottom;"/>
										<label for="implantassistantsurgeryF">自体骨移植</label>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- 修复方式 -->
				<div class="row periost">
					<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
						<!-- 多选组合框 -->
						<div  class="zl_multiple">
							<!-- <span>修复方式：</span> -->
							<!-- 选项 -->
							<ul>
								<li style="width: 15%;">
									<!-- 选项框 -->
									<div class="zl_optiondiv">
										<div style="font-weight:600;line-height: 30px;">修复方式：</div>
									</div>
								</li>
								<li style="width: 15%;">
									<!-- 选项框 -->
									<div class="zl_optiondiv">
										<input type="checkbox" value="局部" name="repairemethod" id="repairemethodA" style="vertical-align: text-bottom;"/>
										<label for="repairemethodA">局部</label>
									</div>
								</li>
								<li style="width: 13%;">
									<!-- 选项框 -->
									<div class="zl_optiondiv">
										<input type="checkbox" value="连桥" name="repairemethod" id="repairemethodB" style="vertical-align: text-bottom;"/>
										<label for="repairemethodB">连桥</label>
									</div>
								</li>
								<li style="width: 13%;">
									<!-- 选项框 -->
									<div class="zl_optiondiv">
										<input type="checkbox" value="杆卡" name="repairemethod" id="repairemethodC" style="vertical-align: text-bottom;"/>
										<label for="repairemethodC">杆卡</label>
									</div>
								</li>
								<li style="width: 14%;">
									<!-- 选项框 -->
									<div class="zl_optiondiv">
										<input type="checkbox" value="套筒" name="repairemethod" id="repairemethodD" style="vertical-align: text-bottom;"/>
										<label for="repairemethodD">套筒</label>
									</div>
								</li>
								<li style="width: 14%;">
									<!-- 选项框 -->
									<div class="zl_optiondiv">
										<input type="checkbox" value="球帽" name="repairemethod" id="repairemethodE" style="vertical-align: text-bottom;"/>
										<label for="repairemethodE">球帽</label>
									</div>
								</li>
								<li style="width: 15%;">
									<!-- 选项框 -->
									<div class="zl_optiondiv">
										<input type="checkbox" value="磁性修复" name="repairemethod" id="repairemethodF" style="vertical-align: text-bottom;"/>
										<label for="repairemethodF">磁性修复</label>
									</div>
								</li>
								<li style="width: 15%;visibility: hidden;">
									<!-- 选项框 -->
									<div >
										<label></label>
									</div>
								</li>
								<li style="width: 15%;">
									<!-- 选项框 -->
									<div class="zl_optiondiv">
										<input type="checkbox" value="Locator" name="repairemethod" id="repairemethodG" style="vertical-align: text-bottom;"/>
										<label for="repairemethodG">Locator</label>
									</div>
								</li>
								<li style="width: 13%;">
									<!-- 选项框 -->
									<div class="zl_optiondiv">
										<input type="checkbox" value="All-on-x" name="repairemethod" id="repairemethodH" style="vertical-align: text-bottom;"/>
										<label for="repairemethodH">All-on-x</label>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>

				<div  class="row bone">
					<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
						<div class="zl_multiple">
							<!-- 选项 -->
							<ul>
								<li style="width: 15%;">
									<div class="zl_optiondiv">
										<input type="checkbox" value="术前取模" name="modulodesign" id="modulodesignA" style="vertical-align: text-bottom;"/>
										<label for="modulodesignA" style="font-weight:600;">术前取模</label>
									</div>
								</li>
								<li style="width: 17%;">
									<div class="zl_optiondiv">
										<input type="checkbox" value="手术模板设计" name="modulodesign" id="modulodesignB" style="vertical-align: text-bottom;"/>
										<label for="modulodesignB" style="font-weight:600;">手术模板设计</label>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>

				<!-- 过渡义齿情况 -->
				<div class="row periost">
					<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
						<!-- 多选框 -->
						<div  class="zl_multiple">
							<!-- 选项 -->
							<ul style="width:100%;">
								<li style="width:16%;">
									<!-- 选项框 -->
									<div class="zl_optiondiv" style="width:90%;">
										<div style="font-weight:600;line-height: 30px;">过渡义齿情况：</div>
									</div>
								</li>
								<li style="width:14%;">
									<!-- 选项框 -->
									<div class="zl_optiondiv">
										<input type="checkbox" value="无" name="tempordentcondition" id="tempordentconditionA" style="vertical-align: text-bottom;"/>
										<label for="tempordentconditionA">无</label>
									</div>
								</li>
								<li style="width:16%;">
									<!-- 选项框 -->
									<div class="zl_optiondiv">
										<input type="checkbox" value="压膜临时牙" name="tempordentcondition" id="tempordentconditionB" style="vertical-align: text-bottom;"/>
										<label for="tempordentconditionB">压膜临时牙</label>
									</div>
								</li>
								<li style="width:14%;">
									<!-- 选项框 -->
									<div class="zl_optiondiv">
										<input type="checkbox" value="活动牙" name="tempordentcondition" id="tempordentconditionC" style="vertical-align: text-bottom;"/>
										<label for="tempordentconditionC">活动牙</label>
									</div>
								</li>
								<li style="width:14%;">
									<!-- 选项框 -->
									<div class="zl_optiondiv">
										<input type="checkbox" value="旧义齿" name="tempordentcondition" id="tempordentconditionD" style="vertical-align: text-bottom;"/>
										<label for="tempordentconditionD">旧义齿</label>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- 口内其他病处理 -->
				<div class="row bone">
					<div class="col-md-7 col-sm-7 col-xs-7 colDefined">
						<!-- 牙位图 -->
						<div class="zl_toothMapdiv" style="    display: flex;">
							<div style="padding: 22px 0;font-weight:600;">口内其他病牙处理：</div>
							<ul class="tooth_map" style="width: 50%;height: 60px; margin-top: 13px;">
								<li>
									<input id="treatmentotherorallu" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
								</li>
								<li>
									<input id="treatmentotheroralru" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
								</li>
								<li>
									<input id="treatmentotheroralld" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
								</li>
								<li>
									<input id="treatmentotheroralrd" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
								</li>
							</ul>
							<span style="margin-left: 2%;">治疗</span>
						</div>
					</div>
					<div class="col-md-5 col-sm-5 col-xs-5 colDefined"></div>
				</div>


				<div id="diagnosis_continer" class="container-fluid">
					<!-- 种植系统 -->
					<div class="row plantSystem">
						<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
							<!-- 多选框 -->
							<div  class="zl_multiple">
								<!-- <span>种植系统：</span> -->
								<!-- 选项 -->
								<ul>
									<li style="width:12%;">
										<!-- 选项框 -->
										<div class="zl_optiondiv">
											<div style="font-weight:600;line-height: 30px;">种植系统：</div>
										</div>
									</li>
									<li style="width:16%;">
										<!-- 选项框 -->
										<div class="zl_optiondiv">
											<input type="checkbox" value="NobelActive" name="implantsystem" id="implantsystemA" style="vertical-align: text-bottom;"/>
											<label for="implantsystemA">NobelActive</label>
										</div>
									</li>
									<li style="width:14%;">
										<!-- 选项框 -->
										<div class="zl_optiondiv">
											<input type="checkbox" value="ICX" name="implantsystem" id="implantsystemB" style="vertical-align: text-bottom;"/>
											<label for="implantsystemB">ICX</label>
										</div>
									</li>
									<li style="width:14%;">
										<!-- 选项框 -->
										<div class="zl_optiondiv">
											<input type="checkbox" value="camlog" name="implantsystem" id="implantsystemC" style="vertical-align: text-bottom;"/>
											<label for="implantsystemC">camlog</label>
										</div>
									</li>
									<li style="width:12%;">
										<!-- 选项框 -->
										<div class="zl_optiondiv">
											<input type="checkbox" value="DT" name="implantsystem" id="implantsystemD" style="vertical-align: text-bottom;"/>
											<label for="implantsystemD">DT</label>
										</div>
									</li>
									<li style="width:14%;">
										<!-- 选项框 -->
										<div class="zl_optiondiv">
											<input type="checkbox" value="Hiossem" name="implantsystem" id="implantsystemE" style="vertical-align: text-bottom;"/>
											<label for="implantsystemE">Hiossen</label>
										</div>
									</li>

									<li style="width:16%;">
										<!-- 选项框 -->
										<div class="zl_optiondiv">
											<input type="checkbox" value="Nobel-PMC" name="implantsystem" id="implantsystemF" style="vertical-align: text-bottom;"/>
											<label for="implantsystemF">Nobel</label>
										</div>
									</li>
									<li style="width:14%;">
										<!-- 选项框 -->
										<div class="zl_optiondiv">
											<input type="checkbox" value="Zimmer" name="implantsystem" id="implantsystemG" style="vertical-align: text-bottom;"/>
											<label for="implantsystemG">Zimmer</label>
										</div>
									</li>
								</ul>
							</div>
						</div>

					</div>
				</div>
			</div>

		</div>
	</div>
	<div class="twoitem">
		<!-- 备注 -->
		<div style="font-size: 15px;font-weight: bold;">备注:</div>
		<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
			<div class="consent_remark">
				<div id="consent_remark" class="overstriking" style="margin: 0 10px;"></div>
				<textarea id="remark" rows="" cols="" autoHeight="true" style="border: 1px solid #ddd;margin:15px 10px 5px 10px;overflow-y: hidden;width:100%;"></textarea>
			</div>
			<pre id="replaceBox"></pre>
		</div>

		<!-- 手术签名 -->
		<div id="consent_signature" style="width: 100%;">
			<div style="margin-bottom: 16px;">
				<span style="font-size: 15px;font-weight: bold;"><i style="color:red;">*</i> 以上情况我已知情并签字确认。</span>
			</div>
			<!-- 患者签名 -->
			<div class="signature_time" style="width:42%;float:left;margin-right: 7%;">
				<span id="patientSignature">患者签名:</span>
				<img id="patientimg"/>
				<input id="patienttime" type="text" class="consent_time inputheight2" readonly="readonly" placeholder="请选择日期"/>
			</div>
			<!-- 医生签名 -->
			<div class="signature_time" style="width:42%;">
				<span id="doctorSignature">医生签名:</span>
				<img id="img"/>
				<input id="doctortime" type="text" class="consent_time inputheight2" readonly="readonly" placeholder="请选择日期"/>
			</div>
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
	var signature="";
	var patientsignature="";
	var toothString;
	var doctorstatus=true;
	var patientstatus=true;
	var contextPath = "<%=contextPath%>";
	var id= window.parent.consultSelectPatient.seqid;	//选中患者id
	var order_number= window.parent.consultSelectPatient.orderNumber;//选中患者order_number
	var caseId=""; //已存在的病历id
	var menuid=window.parent.menuid;//左侧菜单id
	var seqId="<%=seqId%>";   /* 左侧菜单id */
	var seqidFather = "<%=seqidFather%>";
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


		//患者姓名、年龄、性别赋值
		$("#patient_name").attr("value",window.parent.consultSelectPatient.username);
		$("#patient_sex").attr("value",window.parent.consultSelectPatient.sex);
		$("#patient_age").attr("value",window.parent.consultSelectPatient.age);
		$("#patient_usercode").attr("value",window.parent.consultSelectPatient.usercode);
		initZzblInfor(seqId);
		// 2019/7/24 lutian 禁止页面拖拽
		document.ondragstart = function() {
			return false;
		};

		//textarea高度自适应
		$.fn.autoHeight = function(){
			function autoHeight(elem){
				elem.style.height = 'auto';
				elem.scrollTop = 0; //防抖动
				elem.style.height = elem.scrollHeight + 'px';
				textareaHeight = elem.style.height.split("px")[0]
			}

			this.each(function(){
				autoHeight(this);
				$(this).on('keyup', function(){
					autoHeight(this);
				});
			});
		}
		$('textarea[autoHeight]').autoHeight();
	});
	var doctorSignature = document.getElementById("doctorSignature");
	doctorSignature.onclick = function(){
		if(doctorstatus){
			layer.open({
				type: 2,
				title: '签字',
				shadeClose: true,
				shade: 0.6,
				area: ['800px', '400px'],
				content: contextPath + '/SignatureAct/toSignature.act?category=种植'
			});
		}
	}
	function addSignature(){
		$("#img").css("display","");
		$("#img").attr('src', signature);
        if(doctorstatus&&!patientstatus){
            updateDoctorSignature();
        }
	}
    //更新
    function updateDoctorSignature(){
        var url = contextPath + '/HUDH_ZzblAct/updateZzblOprationById.act';
        var doctorTime = $("#doctortime").val();//医生签名时间
        var param = {
            id:  caseId, //临床路径ID
            doctorSignature:signature,//医生签名
            doctorTime : doctorTime//医生签名时间
        };
        $.axseSubmit(url, param,function() {},function(r) {
            layer.alert("修改成功！", {
                end: function() {
                    //window.parent.location.reload(); //刷新父页面
                    var frameindex = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(frameindex); //再执行关闭
                }
            });
        },function(r){
            layer.alert("修改失败！");
        });
    }

	var patientSignature = document.getElementById("patientSignature");
	patientSignature.onclick = function(){
		if(patientstatus){
			layer.open({
				type: 2,
				title: '签字',
				shadeClose: true,
				shade: 0.6,
				area: ['800px', '400px'],
				content: contextPath + '/SignatureAct/toSignature.act?category=患者'
			});
		}
	}
	function addPatientSignature(){
		$("#patientimg").css("display","");
		$("#patientimg").attr('src', patientsignature);
		if(!doctorstatus&&patientstatus){
			updatePatientSignature();
		}
	}
	//更新
	function updatePatientSignature(){
		var url = contextPath + '/HUDH_ZzblAct/updateZzblOprationById.act';
		var patienttime = $("#patienttime").val();//修复医生签名时间
		var param = {
			id:  caseId, //临床路径ID
			patientsignature :  patientsignature,//患者签名
			PatientTime : patienttime//患者签名时间

		};
		$.axseSubmit(url, param,function() {},function(r) {
			layer.alert("修改成功！", {
				end: function() {
					//window.parent.location.reload(); //刷新父页面
					var frameindex = parent.layer.getFrameIndex(window.name);
					parent.layer.close(frameindex); //再执行关闭
				}
			});
		},function(r){
			layer.alert("修改失败！");
		});
	}
	/* 2019/7/16 lutian input文字长度校验方法   obj：元素id  textNum：限制文字长度 */
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

	function initZzblInfor(seqId){
		//console.log(seqId+"-----------初始化病历seqId");
		var url = contextPath + '/HUDH_ZzblAct/findZzblOprationById.act';
		$.ajax({
			url: url,
			type:"POST",
			dataType:"json",
			data : {
				id :  id, //临床路径ID
				order_number : order_number
			},
			success:function(result){
				//console.log(result)
				//console.log(JSON.stringify(result)+"--------------添加成功后查询数据");
				var result;
				if(seqidFather){
					for (var i=0;i<result.length;i++) {
						if(seqidFather==result[i].seqId){
							result=result[i];
						}
					}
				}
				//caseId=result.seqId;  //病历id
				caseId=seqidFather;  //修改病历id
				/* 判断是否已经填写过内容 */
				if(result.id){
					$("#consent_saveBtn").css("display","none");//隐藏保存按钮
					$("#consent_updateBtn").css("display","inline-block");//显示修改按钮
					//赋值
					for(var key in result){
						//console.log(key+"-------------"+result[key]);
						$("#"+key).attr("value",result[key]);// 填框赋值
						$("#remark").text(result["remark"]);//textarea赋值
						$("#remark").trigger("keyup");
						$("#replaceBox").text(result["remark"]);//textarea替换框赋值
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
					//$("input").attr("disabled","disabled");//查看信息的时候禁止在填写
					if(result.doctorsignature!=""&&result.doctorsignature!=null){
						signature=result.doctorsignature;
						$("#img").attr('src', signature);
						doctorstatus=false;
					}else{
						$("#img").attr('display', 'none');
					}
					if(result.patientsignature!=""&&result.patientsignature!=null){
						patientsignature=result.patientsignature;
						$("#patientimg").attr('src', patientsignature);
						patientstatus=false;
					}else{
						$("#patientimg").attr('display', 'none');
					}
				}
				//获取当前页面所有按钮
				getButtonAllCurPage(menuid);
			}
		});
	}

	//获取url中的参数
	function getUrlParam(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg);  //匹配目标参数
		if (r != null) return unescape(r[2]);
		return null; //返回参数值
	}

	//获取治疗方案
	function showTreamentPlan() {
		var obj = document.getElementsByName("treamentplan");
		var treamentplan = "";
		for ( k in obj ) {
			if(obj[k].checked)
				treamentplan = treamentplan + obj[k].value + ';';
		}
		return treamentplan;
	}

	//获取上颌窦提升
	function showImplantAssistantSurgery() {
		var obj = document.getElementsByName("implantassistantsurgery");
		var implantAssistantSurgery = "";
		for ( k in obj ) {
			if(obj[k].checked)
				implantAssistantSurgery = implantAssistantSurgery + obj[k].value + ';';
		}
		return implantAssistantSurgery;
	}

	//获取修复方式
	function showRepaireMethod(){
		var obj = document.getElementsByName("repairemethod");
		var repaireMethod = "";
		for ( k in obj ) {
			if(obj[k].checked)
				repaireMethod = repaireMethod + obj[k].value + ';';
		}
		return repaireMethod;
	}

	//获取过度义齿情况
	function showTemporDentCondition(){
		var obj = document.getElementsByName("tempordentcondition");
		var temporDentCondition = "";
		for ( k in obj ) {
			if(obj[k].checked)
				temporDentCondition = temporDentCondition + obj[k].value + ';';
		}
		return temporDentCondition;
	}

	//获取种植系统
	function showImplantSystem(){
		var obj = document.getElementsByName("implantsystem");
		var implantSystem = "";
		for ( k in obj ) {
			if(obj[k].checked)
				implantSystem = implantSystem + obj[k].value + ';';
		}
		return implantSystem;
	}

	//获取术前取模，手术模板设计
	function showmoduloDesign(){
		var obj = document.getElementsByName("modulodesign");
		var moduloDesign = "";
		for ( k in obj ) {
			if(obj[k].checked)
				moduloDesign = moduloDesign + obj[k].value + ';';
		}
		return moduloDesign;
	}

	//修改
	function update(){
		var patient_name = $("#patient_name").val();//患者姓名
		var patient_sex = $("#patient_sex").val();//患者性别
		var patient_age = $("#patient_age").val();//患者年龄
		var treamentPlan = showTreamentPlan();
		var extractionLeftUp = $("#extractionleftup").val();
		var extractionRightUp = $("#extractionrightup").val();
		var extractionLeftDown = $("#extractionleftdown").val();
		var extractionRightDown = $("#extractionrightdown").val();
		var repaireMethod = showRepaireMethod();
		var temporDentCondition = showTemporDentCondition();
		var implantFirstTimeLU = $("#implantfirsttimelu").val();
		var implantFirstTimeRU = $("#implantfirsttimeru").val();
		var implantFirstTimeLD = $("#implantfirsttimeld").val();
		var implantFirstTimeRD = $("#implantfirsttimerd").val();
		var implantSystem = showImplantSystem();
		var implantSecondTimeLU = $("#implantsecondtimelu").val();
		var implantSecondTimeRU = $("#implantsecondtimeru").val();
		var implantSecondTimeLD = $("#implantsecondtimeld").val();
		var implantSecondTimeRD = $("#implantsecondtimerd").val();
		var implantAssistantSurgery = showImplantAssistantSurgery();
		var treatmentOtherOralLU = $("#treatmentotherorallu").val();
		var treatmentOtherOralRU = $("#treatmentotheroralru").val();
		var treatmentOtherOralLD = $("#treatmentotheroralld").val();
		var treatmentOtherOralRD = $("#treatmentotheroralrd").val();
		var largeParticle = $("#largeparticle").val();
		var smallParticle = $("#smallparticle").val();
		var boneCollagen = $("#bonecollagen").val();
		var tianBo = $("#tianbo").val();
		var collagenMembBig = $("#collagenmembbig").val();
		var collagenMembSmall = $("#collagenmembsmall").val();
		var titaniumMesh = $("#titaniummesh").val();

		/* 新增参数 */
		var moduloDesign = showmoduloDesign();//术前取模，手术模板设计
		var remarks = $("#remark").val();//备注
		/* var patientsignature = $("#patientsignature").val();//患者签名 */
		var PatientTime = $("#patienttime").val();//患者签名时间
		var doctorTime = $("#doctortime").val();//医生签名时间

		var url = contextPath + '/HUDH_ZzblAct/updateZzblOprationById.act';
		var param = {
			id : caseId,
			order_number : order_number,
			username :patient_name,
			sex : patient_sex,
			age : patient_age,
			treamentPlan : treamentPlan,
			extractionLeftUp : extractionLeftUp,
			extractionRightUp : extractionRightUp,
			extractionLeftDown : extractionLeftDown,
			extractionRightDown : extractionRightDown,
			implantAssistantSurgery : implantAssistantSurgery,
			repaireMethod : repaireMethod,
			temporDentCondition : temporDentCondition,
			implantSystem : implantSystem,
			implantFirstTimeLU : implantFirstTimeLU,
			implantFirstTimeRU : implantFirstTimeRU,
			implantFirstTimeLD : implantFirstTimeLD,
			implantFirstTimeRD : implantFirstTimeRD,
			implantSecondTimeLU : implantSecondTimeLU,
			implantSecondTimeRU : implantSecondTimeRU,
			implantSecondTimeLD : implantSecondTimeLD,
			implantSecondTimeRD : implantSecondTimeRD,
			treatmentOtherOralLU : treatmentOtherOralLU,
			treatmentOtherOralRU : treatmentOtherOralRU,
			treatmentOtherOralLD : treatmentOtherOralLD,
			treatmentOtherOralRD : treatmentOtherOralRD,
			largeParticle : largeParticle,
			smallParticle : smallParticle,
			boneCollagen : boneCollagen,
			tianBo : tianBo,
			collagenMembBig : collagenMembBig,
			collagenMembSmall : collagenMembSmall,
			titaniumMesh : titaniumMesh,
			/* 新增参数 */
			moduloDesign : moduloDesign,//术前取模，手术模板设计
			remarks : remarks,//备注
			patientsignature : patientsignature,//患者签名
			doctorSignature:signature,//医生签名
			PatientTime : PatientTime,//患者签名时间
			doctorTime : doctorTime//医生签名时间

		};
		//console.log(JSON.stringify(param)+"-------hahahahah");
		$.axseSubmit(url, param,function() {},function(r) {
//	        	console.log(JSON.stringify(r)+"----------保存成功返回值！");
			layer.alert("修改成功！", {
				end: function() {
					//window.parent.location.reload(); //刷新父页面
					var frameindex = parent.layer.getFrameIndex(window.name);
					parent.layer.close(frameindex); //再执行关闭
				}
			});
		},function(r){
			layer.alert("修改失败！");
		});
	}

	//保存
	function save() {
		var patient_name = $("#patient_name").val();//患者姓名
		var patient_sex = $("#patient_sex").val();//患者性别
		var patient_age = $("#patient_age").val();//患者年龄
		var treamentPlan = showTreamentPlan();
		var extractionLeftUp = $("#extractionleftup").val();
		var extractionRightUp = $("#extractionrightup").val();
		var extractionLeftDown = $("#extractionleftdown").val();
		var extractionRightDown = $("#extractionrightdown").val();
		var repaireMethod = showRepaireMethod();
		var temporDentCondition = showTemporDentCondition();
		var implantFirstTimeLU = $("#implantfirsttimelu").val();
		var implantFirstTimeRU = $("#implantfirsttimeru").val();
		var implantFirstTimeLD = $("#implantfirsttimeld").val();
		var implantFirstTimeRD = $("#implantfirsttimerd").val();
		var implantSystem = showImplantSystem();
		var implantSecondTimeLU = $("#implantsecondtimelu").val();
		var implantSecondTimeRU = $("#implantsecondtimeru").val();
		var implantSecondTimeLD = $("#implantsecondtimeld").val();
		var implantSecondTimeRD = $("#implantsecondtimerd").val();
		var implantAssistantSurgery = showImplantAssistantSurgery();
		var treatmentOtherOralLU = $("#treatmentotherorallu").val();
		var treatmentOtherOralRU = $("#treatmentotheroralru").val();
		var treatmentOtherOralLD = $("#treatmentotheroralld").val();
		var treatmentOtherOralRD = $("#treatmentotheroralrd").val();
		var largeParticle = $("#largeparticle").val();
		var smallParticle = $("#smallparticle").val();
		var boneCollagen = $("#bonecollagen").val();
		var tianBo = $("#tianbo").val();
		var collagenMembBig = $("#collagenmembbig").val();
		var collagenMembSmall = $("#collagenmembsmall").val();
		var titaniumMesh = $("#titaniummesh").val();

		/* 新增参数 */
		var moduloDesign = showmoduloDesign();//术前取模，手术模板设计
		var remarks = $("#remark").val();//备注
		/* var patientsignature = $("#patientsignature").val();//患者签名 */
		var PatientTime = $("#patienttime").val();//患者签名时间
		var doctorTime = $("#doctortime").val();//医生签名时间
		var url = contextPath + '/HUDH_ZzblAct/save.act';
		var param = {
			id : id,
			order_number : order_number,
			username :patient_name,
			sex : patient_sex,
			age : patient_age,
			treamentPlan : treamentPlan,
			extractionLeftUp : extractionLeftUp,
			extractionRightUp : extractionRightUp,
			extractionLeftDown : extractionLeftDown,
			extractionRightDown : extractionRightDown,
			implantAssistantSurgery : implantAssistantSurgery,
			repaireMethod : repaireMethod,
			temporDentCondition : temporDentCondition,
			implantSystem : implantSystem,
			implantFirstTimeLU : implantFirstTimeLU,
			implantFirstTimeRU : implantFirstTimeRU,
			implantFirstTimeLD : implantFirstTimeLD,
			implantFirstTimeRD : implantFirstTimeRD,
			implantSecondTimeLU : implantSecondTimeLU,
			implantSecondTimeRU : implantSecondTimeRU,
			implantSecondTimeLD : implantSecondTimeLD,
			implantSecondTimeRD : implantSecondTimeRD,
			treatmentOtherOralLU : treatmentOtherOralLU,
			treatmentOtherOralRU : treatmentOtherOralRU,
			treatmentOtherOralLD : treatmentOtherOralLD,
			treatmentOtherOralRD : treatmentOtherOralRD,
			largeParticle : largeParticle,
			smallParticle : smallParticle,
			boneCollagen : boneCollagen,
			tianBo : tianBo,
			collagenMembBig : collagenMembBig,
			collagenMembSmall : collagenMembSmall,
			titaniumMesh : titaniumMesh,
			/* 新增参数 */
			moduloDesign : moduloDesign,//术前取模，手术模板设计
			remarks : remarks,//备注
			patientsignature : patientsignature,//患者签名
			PatientTime : PatientTime,//患者签名时间
			doctorTime : doctorTime,//医生签名时间
			doctorSignature:signature//医生签名
		};
		//console.log(JSON.stringify(param)+"-------hahahahah");
		$.axseSubmit(url, param,function() {},function(r) {
			layer.alert("保存成功！", {
				end: function() {
					//window.parent.location.reload(); //刷新父页面
					var frameindex = parent.layer.getFrameIndex(window.name);
					parent.layer.close(frameindex); //再执行关闭
				}
			});
		},function(r){
			layer.alert("保存失败！");
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
			if(inputVal[i] <=8 && inputVal[i]>=1){
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
			if (listbutton[i].qxName == "zsbs_xgbd"&&doctorstatus&&patientstatus) {
				$("#consent_updateBtn").removeClass("hidden");
			}else if(listbutton[i].qxName =="lclj_ban_signature"){
                doctorstatus=false;
                patientstatus=false;
            }
		}
		$("#bottomBarDdiv").append(menubutton1);
	}
	function doPrint() {
		if(textareaHeight>120) {
			$(".twopage").css("page-break-after", "always");
		};
		$("#remark").css("display","none");
		$("#replaceBox").css("display","block");

		var sicktime = $("#patienttime").val();
		if(!sicktime){
			$("#patienttime").css('display','none')
		}

		var doctorTime = $("#doctortime").val();
		if(!doctorTime){
			$("#doctortime").css('display','none')
		}


		bdhtml=window.document.body.innerHTML;
		sprnstr="<!--startprint-->";
		eprnstr="<!--endprint-->";
		prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
		prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
		var htmlStyle="<style>button{display:none;}span{font-size: 12px!important;}*{font-size: 12px;line-height: 16px;}#diagnosis_continer input[type='checkbox']{width:12px !important;height:12px !important;margin-top: 15px !important;}.one{margin-left: 42px!important;}.inputheight2{border: 1px solid transparent!important;}#consent_signature{width:100%!important;}	.consent_updateBtn{display:none!important;}.btns{display:none!important;}#logoImg{text-align:left!important;width:20%!important;left:0%!important;top:17px!important;}#remark{font-size: 14px!important;line-height: 18px!important;}</style>";
		window.document.body.innerHTML=prnhtml+htmlStyle;
		window.print();  //打印
		document.body.innerHTML=bdhtml; //恢复页面
		window.location.reload()
	}

	function myPreviewAll(){
		doPrint();
		/* if(doctorstatus&&signature==""){
               $("#img").css("display","none");
        }
        if(patientstatus&&patientsignature==""){
               $("#patientimg").css("display","none");
        }
        LODOP=getLodop();
        LODOP.PRINT_INIT("诊疗方案");
        var htmlStyle="<style>button{display:none;}span{font-size: 12px!important;}*{font-size: 12px;line-height: 16px;}#diagnosis_continer input[type='checkbox']{width:12px !important;height:12px !important;margin-top: 15px !important;}.one{margin-left: 42px!important;}.inputheight2{border: 1px solid transparent!important;}#consent_signature{width:300px !important;}#consent_signature{width:40%;}.consent_updateBtn{display:none!important;}.btns{display:none;}#logoImg{text-align:left!important;width:20%!important;left:0%!important;top:17px!important;}</style>";
        var html="<!DOCTYPE html>"+document.getElementsByTagName("html")[0].innerHTML+htmlStyle;
        LODOP.ADD_PRINT_HTM(0,0,"100%","100%",html);
        LODOP.PREVIEW(); */
	};

</script>
</html>