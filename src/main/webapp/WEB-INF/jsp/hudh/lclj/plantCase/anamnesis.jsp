<%@page import="java.io.Console"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//当前主诉及既往病史方案id
	String seqidFather = request.getParameter("seqidFather");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/hudh/lclj/flowdetail/css/base.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/static/css/kqdsFront/plantCase/anamnesis.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
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
	#anamnesis_continer .bgwhite{
		background-color: white;
	}
	/*多选框样式*/
	#anamnesis_continer>.row{
		overflow: visible;
	}
	.selectgroup .groupSelect{
		padding-right: 0px;
		border: 0px solid #c3c3c3;
		margin-bottom: 12px;
	}
	.bootstrap-select > .dropdown-toggle{
		width: 100%;
		padding: 0px;
		height: 24px;
		line-height: 24px;
		padding-right: 25px;
	}
	.pull-left{
		margin-top: 0px !important;
		font-size: 14px;
		padding: 0px 5px;
	}
	.bs-caret .caret{
		margin-top: 0px !important;
	}
	.glyphicon{
		margin-top: 5px !important;
		font-size: 12px;
	}
	.dropdown-menu > li > a{
		padding: 0px 15px;
	}
	.bootstrap-select.btn-group.show-tick .dropdown-menu li a span.text {
		line-height: 24px;
		font-size: 14px;
		margin-top: 0px !important;
	}
	.bootstrap-select .dropdown-toggle:focus {
		outline: 0px dotted #333333 !important;
		outline: 0px auto -webkit-focus-ring-color !important;
		outline-offset: -2px;
	}
	.selectgroup{
		display: block !important;
	}
	.medicineText{
		display: none;
		margin: 9px 0px;
		padding-left: 10px;
	}
	.btns>button:focus{
		border:0px solid red;
		outline: none;
	}
</style>
<body>
<!--startprint-->
<div id="anamnesis_continer" class="container-fluid" style="height:100%;">
	<!-- 标题 -->
	<div class="row">
		<div class="col-md-12 col-sm-12">
			<!-- <div class="big_title"><span class="bigtitle">主诉及既往病史</span></div> -->
			<%--<img id="logoImg" src="<%=contextPath%>/static/image/kqdsFront/jiagong/logoName.png">--%>
			<span class="bigtitle">主诉及既往病史</span>
		</div>
	</div>
	<!-- 患者信息 -->
	<div class="row patientInfo" style="border-top:2px solid #776c6c;padding: 40px 0px 0px 15px;">
		<!-- 信息输入组合框 -->
		<div class="rpInfo_import">
			<span style="display:block;" id="username">患者姓名：</span>
			<font class="alreadyInfo" id="patient_name"></font>
			<!-- <input id="patient_name" type="text" style="width:60%;" disabled="disabled"/> -->
		</div>
		<!-- 信息输入组合框 -->
		<div class="rpInfo_import">
			<span>编号：</span>
			<font class="alreadyInfo" id="patient_num"></font>
			<!-- <input id="patient_num" type="text" style="width:60%;" disabled="disabled"/> -->
		</div>
		<!-- 信息输入组合框 -->
		<div class="rpInfo_import sexInfo">
			<span>性别：</span>
			<font class="alreadyInfo" id="patient_sex"></font>
			<!-- <input id="patient_sex" type="text" style="width:60%;" disabled="disabled"/> -->
		</div>
		<!-- 信息输入组合框 -->
		<div class="rpInfo_import">
			<span>年龄：</span>
			<font class="alreadyInfo" id="patient_age"></font>
			<!-- <input id="patient_age" type="text" style="width:60%;" disabled="disabled"/> -->
		</div>
	</div>
	<!-- 缺牙时间以及情况 -->
	<div class="row toothInfo" style="margin-bottom: 20px;">
		<div class="col-md-12 col-sm-12 mainSuitInfo">
			<!-- 主诉内容 -->
			<div class="chiefComplaint">
				<!-- 缺牙时间 -->
				<div class="loseTooth_time">
					<span>主诉(缺牙时间):</span><input id="agomphostime" class="consent_time" type="text" readonly="readonly" placeholder="请选择日期"/>
				</div>
				<!-- 要求种植修复 -->
				<div class="loseTooth_time">
					<span>要求种植修复时间:</span><input id="planttime" class="consent_time" type="text" readonly="readonly" placeholder="请选择日期"/>
				</div>
				<!-- 缺牙选项 -->
				<ul class="loseTooth_option">
					<li>
						<input name="symptom" id="tooth_lack" type="checkbox" value="牙缺失"/><label for="tooth_lack">牙缺失</label>
					</li>
					<li>
						<input name="symptom" id="tooth_loose" type="checkbox" value="牙松动"/><label for="tooth_loose">牙松动</label>
					</li>
					<li>
						<input name="symptom" id="tooth_rot" type="checkbox" value="牙腐烂"/><label for="tooth_rot">牙腐烂</label>
					</li>
					<li>
						<input name="symptom" id="tooth_break" type="checkbox" value="牙折断"/><label for="tooth_break">牙折断</label>
					</li>
					<!-- <li style="width:70%;">
                        <input id="tooth_year" type="text"/><label for="tooth_year">年</label>
                        <input id="tooth_month" type="text"/><label for="tooth_month">月</label>
                        <input id="planttime" class="consent_time" type="text" readonly="readonly" placeholder="请选择日期"/>
                        <span style="margin-top: 5px;">要求种植修复</span>
                    </li> -->
				</ul>
			</div>
		</div>
	</div>
	<!-- 高血压 -->
	<div class="row">
		<div class="col-md-1 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>高血压:</span>
			</div>
		</div>
		<div class="col-md-1 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<div class="select_group common_style">
				<!-- 选项div -->
				<div class="option_div">
					<input name="ishypertension" id="isHypertension_n" value="0" type="radio" onclick="ishaveillness(this.name);ishavahypertension(this.name);"/><label for="isHypertension_n">无</label>
					<input name="ishypertension" id="isHypertension_y" value="1" type="radio" onclick="ishaveillness(this.name);ishavahypertension(this.name);"/><label for="isHypertension_y">有</label>
				</div>
			</div>
		</div>
		<div class="col-md-1 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="hypertension" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>年</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-3 col-xs-3 colDefined combgGray backColor">
			<!-- 选择组合框 -->
			<div class="select_group common_style">
				<!-- 选项名称 -->
				<span class="select_name">是否控制:</span>
				<!-- 选项 -->
				<div class="option_div">
					<input name="iscontrol" id="isControl_y" value="1" type="radio" disabled="disabled"/><label for="isControl_y">是</label>
					<input name="iscontrol" id="isControl_n" value="0" type="radio" disabled="disabled"/><label for="isControl_n">否</label>
				</div>
			</div>
		</div>
		<div class="col-md-2 col-sm-3 col-xs-3 colDefined combgGray backColor">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写名称 -->
				<span class="fillWrite_name" style="font-weight: normal;">目前血压:</span>
				<!-- 填写框-->
				<input id="pressure" style="width: 120px;text-align: center;padding: 0px;" class="fillWrite_input" placeholder="125/69" onblur="TextLengthCheck(this.id,10);" type="text"/>
			</div>
		</div>
		<div class="col-md-2 col-sm-6 col-xs-6 colDefined combgGray">
			<!-- 选择组合框 -->
			<div class="select_group common_style">
				<!-- 选项名称 -->
				<span class="select_name">是否服药:</span>
				<!-- 选项 -->
				<!-- 选项div -->
				<div class="option_div">
					<input name="istakemedicie" id="isTakeMedicie_y" value="1" type="radio" disabled="disabled"/><label for="isTakeMedicie_y">是</label>
					<input name="istakemedicie" id="isTakeMedicie_n" value="0" type="radio" disabled="disabled"/><label for="isTakeMedicie_n">否</label>
				</div>
			</div>
		</div>
		<div class="col-md-3 col-sm-6 col-xs-6 colDefined combgGray">
			<!-- 填写组合框 -->
			<div class="selectgroup common_style">
				<span class="groupName" style="font-weight: normal;">常用药物：</span>
				<select id="hypertensionmedicine" class="groupSelect" multiple title="请选择" style="pointer-events: none;">
					<%--<option value="">请选择</option>--%>
					<option value="倍他洛克">倍他洛克</option>
					<option value="比索洛尔">比索洛尔</option>
					<option value="硝苯地平">硝苯地平</option>
					<option value="氨氯地平">氨氯地平</option>
					<option value="非洛地平">非洛地平</option>
					<option value="卡托普利">卡托普利</option>
					<option value="贝那普利">贝那普利</option>
					<option value="替米沙坦">替米沙坦</option>
					<option value="厄贝沙坦">厄贝沙坦</option>
					<option value="缬沙坦">缬沙坦</option>
					<option value="络活喜">络活喜</option>
				</select>
			</div>
			<%--打印展示--%>
			<div class="medicineText">常用药物：<span class="hypertensionmedicine"></span></div>
		</div>
	</div>
	<!-- 心脏病 -->
	<div class="row">
		<div class="col-md-2 col-sm-4 col-xs-4 colDefined">
			<!-- 名称div -->
			<div class="name_div common_style bgwhite">
				<span>心脏病 心绞痛、心衰:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-4 col-xs-4 colDefined">
			<div class="select_group common_style bgwhite">
				<!-- 选项div -->
				<div class="option_div">
					<input name="isheardiease" id="IsHearDiease_n" value="0" type="radio" onclick="ishaveillness(this.name);ishavaheardiease(this.name);"/><label for="IsHearDiease_n">无</label>
					<input name="isheardiease" id="IsHearDiease_y" value="1" type="radio" onclick="ishaveillness(this.name);ishavaheardiease(this.name);"/><label for="IsHearDiease_y">有</label>
				</div>
			</div>
		</div>
		<div class="col-md-1 col-sm-4 col-xs-4 colDefined">
			<!-- 时间div -->
			<div class="time_div common_style bgwhite">
				<input id="heardiease" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>年</span>
			</div>
		</div>
		<div class="col-md-3 col-sm-6 col-xs-6 colDefined backcolorGray">
			<!-- 选择组合框 -->
			<div class="select_group common_style">
				<!-- 选项名称 -->
				<span class="select_name">是否常备药在身边:</span>
				<!-- 选项 -->
				<div class="option_div">
					<input name="ispreparemedication" id="isPrepareMedication_y" value="1" type="radio" disabled="disabled"/><label for="isPrepareMedication_y">是</label>
					<input name="ispreparemedication" id="isPrepareMedication_n" value="0" type="radio" disabled="disabled"/><label for="isPrepareMedication_n">否</label>
				</div>
			</div>
		</div>
		<div class="col-md-4 col-sm-6 col-xs-6 colDefined backcolorGray">
			<!-- 填写组合框 -->
			<div class="selectgroup common_style">
				<span class="groupName" style="font-weight: normal;">常用药物：</span>
				<select id="heardieasemedicine" class="groupSelect" multiple title="请选择" style="pointer-events: none;">
					<%--<option value="">请选择</option>--%>
					<option value="速效救心丸">速效救心丸</option>
					<option value="复方丹参滴丸">复方丹参滴丸</option>
					<option value="复方丹参片">复方丹参片</option>
					<option value="心脑康胶囊">心脑康胶囊</option>
					<option value="地奥心血康">地奥心血康</option>
					<option value="心痛定片(硝苯地平片)">心痛定片(硝苯地平片)</option>
				</select>
			</div>
			<%--打印展示--%>
			<div class="medicineText">常用药物：<span class="heardieasemedicine"></span></div>
		</div>
	</div>
	<!-- 糖尿病 -->
	<div class="row">
		<div class="col-md-1 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>糖尿病:</span>
			</div>
		</div>
		<div class="col-md-1 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<div class="select_group common_style">
				<!-- 选项div -->
				<div class="option_div">
					<input name="isdiabetes" id="isDiabetes_n" value="0" type="radio" onclick="ishaveillness(this.name);ishavadiabetes(this.name);"/><label for="isDiabetes_n">无</label>
					<input name="isdiabetes" id="isDiabetes_y" value="1" type="radio" onclick="ishaveillness(this.name);ishavadiabetes(this.name);"/><label for="isDiabetes_y">有</label>
				</div>
			</div>
		</div>
		<div class="col-md-1 col-sm-2 col-xs-2 colDefined combgGray backColor">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="diabetes" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>年</span>
			</div>
		</div>
		<div class="col-md-3 col-sm-6 col-xs-6 colDefined combgGray backColor">
			<!-- 选择组合框 -->
			<div class="select_group common_style">
				<!-- 选项名称 -->
				<span class="select_name">是怎么控制:</span>
				<!-- 选项 -->
				<div class="select_item" style="width:auto;">
					<input name="dietcontrol" id="dietControl_eat" value="饮食" type="checkbox" style="vertical-align: text-bottom;margin-left: 10px;" disabled="disabled"/><label for="dietControl_eat" style="margin-right: ">饮食</label>
					<input name="dietcontrol" id="dietControl_medicine" value="口服药" type="checkbox" style="vertical-align: text-bottom;margin-left: 10px;" disabled="disabled"/><label for="dietControl_medicine">口服药</label>
					<input name="dietcontrol" id="dietControl_injection" value="针剂" type="checkbox" style="vertical-align: text-bottom;margin-left: 10px;" disabled="disabled"/><label for="dietControl_injection">针剂</label>
				</div>
			</div>
		</div>
		<%--<div class="col-md-2 col-sm-4 col-xs-4 colDefined combgGray">
            <!-- 选择组合框 -->
            <div class="select_group common_style">
                <!-- 选项名称 -->
                <span class="select_name">是否控制:</span>
                <!-- 选项 -->
                <div class="option_div">
                    <input name="isdietcontrol" id="isDietControl_y" value="1" type="radio" disabled="disabled"/><label for="isDietControl_y">是</label>
                    <input name="isdietcontrol" id="isDietControl_n" value="0" type="radio" disabled="disabled"/><label for="isDietControl_n">否</label>
                </div>
            </div>
        </div>--%>
		<div class="col-md-3 col-sm-6 col-xs-6 colDefined combgGray">
			<!-- 填写组合框 -->
			<div class="selectgroup common_style">
				<span class="groupName" style="font-weight: normal;">口服常用药：</span>
				<select id="diabetesoralmedicine" class="groupSelect" multiple title="请选择" style="pointer-events: none;">
					<%--<option value="">请选择</option>--%>
					<option value="二甲双胍">二甲双胍</option>
					<option value="格列美脲">格列美脲</option>
					<option value="格列本脲">格列本脲</option>
					<option value="格列齐特">格列齐特</option>
					<option value="格列吡嗪">格列吡嗪</option>
					<option value="格列喹酮">格列喹酮</option>
					<option value="α-葡萄糖苷阿卡波糖">α-葡萄糖苷阿卡波糖</option>
					<option value="伏格列波糖">伏格列波糖</option>
					<option value="那格列奈">那格列奈</option>
					<option value="瑞格列奈">瑞格列奈</option>
					<option value="罗格列酮">罗格列酮</option>
					<option value="吡格列酮">吡格列酮</option>
					<option value="磷酸西格列汀片">磷酸西格列汀片</option>
					<option value="维格列汀">维格列汀</option>
					<option value="利格列汀">利格列汀</option>
					<option value="拜糖平">拜糖平</option>
				</select>
			</div>
			<%--打印展示--%>
			<div class="medicineText">口服常用药：<span class="diabetesoralmedicine"></span></div>
		</div>
		<div class="col-md-3 col-sm-6 col-xs-6 colDefined combgGray">
			<!-- 填写组合框 -->
			<div class="selectgroup common_style">
				<span class="groupName" style="font-weight: normal;">注射常用药：</span>
				<select id="diabetesinjectionmedicine" class="groupSelect" multiple title="请选择" style="pointer-events: none;">
					<%--<option value="">请选择</option>--%>
					<option value="利拉鲁肽">利拉鲁肽</option>
					<option value="艾塞那肽">艾塞那肽</option>
					<option value="胰岛素">胰岛素</option>
				</select>
			</div>
			<%--打印展示--%>
			<div class="medicineText">注射常用药：<span class="diabetesinjectionmedicine"></span></div>
		</div>
	</div>
	<!-- 六个月内做过心瓣膜手术 -->
	<div class="row bgWhite">
		<div class="col-md-8 col-sm-8 col-xs-8 colDefined">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>六个月内做过心瓣膜置换术或发生心梗：</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="isinfarction" id="isInfarction_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="isInfarction_n">无</label>
				<input name="isinfarction" id="isInfarction_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="isInfarction_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="infarction" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>月</span>
			</div>
		</div>
	</div>
	<!-- 凝血功能不足性疾病 -->
	<div class="row">
		<div class="col-md-8 col-sm-8 col-xs-8 colDefined combgGray">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span class="print_textStyle ipadText">凝血功能不足性疾病(血友病、再障、血小板减少症、急性白血病、粒细胞缺乏症):</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="isbloodcoagulation" id="IsBloodCoagulation_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="IsBloodCoagulation_n">无</label>
				<input name="isbloodcoagulation" id="IsBloodCoagulation_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="IsBloodCoagulation_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="bloodcoagulation" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>年</span>
			</div>
		</div>
	</div>
	<!-- 服用抗凝药物 -->
	<div class="row bgWhite">
		<div class="col-md-5 col-sm-5 col-xs-5 colDefined">
			<!-- 名称div -->
			<div class="name_div common_style fillWrite_group">
				<span class="print_textStyle ipadText">服用抗凝药物或其他可以导致凝血功能障碍的药物(药名):</span>
				<!-- 填写框-->
				<!-- <input id="antifreezing" placeholder="此框只能输9个字" onblur="TextLengthCheck(this.id,9);" style="margin-top: 8px;width: 25%;margin-left: 2%;border: 0px;" class="fillWrite_input" type="text"/> -->
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="isantifreezing" id="isAntiFreezing_n" value="0" type="radio" onclick="ishaveantifreezing(this.name)"/><label for="isAntiFreezing_n">无</label>
				<input name="isantifreezing" id="isAntiFreezing_y" value="1" type="radio" onclick="ishaveantifreezing(this.name)"/><label for="isAntiFreezing_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-1 col-xs-1 colDefined">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="antifreezingtime" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>年</span>
			</div>
		</div>
		<div class="col-md-3 col-sm-4 col-xs-4 colDefined">
			<!-- 填写组合框 -->
			<div class="selectgroup common_style">
				<span class="groupName" style="font-weight: normal;">常用药物：</span>
				<select id="antifreezingmedicine" class="groupSelect" multiple title="请选择" style="pointer-events: none;">
					<%--<option value="">请选择</option>--%>
					<option value="阿司匹林">阿司匹林</option>
					<option value="波立维">波立维</option>
					<option value="华法林">华法林</option>
					<option value="肝素类">肝素类</option>
					<option value="氯吡格雷">氯吡格雷</option>
					<option value="达比加群">达比加群</option>
					<option value="利伐沙班">利伐沙班</option>
				</select>
			</div>
			<%--打印展示--%>
			<div class="medicineText">常用药物：<span class="antifreezingmedicine"></span></div>
		</div>
	</div>
	<!-- 乙肝丙肝 -->
	<div class="row">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>乙肝:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="ishepatitisb" id="isHepatitisB_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="isHepatitisB_n">无</label>
				<input name="ishepatitisb" id="isHepatitisB_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="isHepatitisB_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="hepatitisb" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>年</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>丙肝:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="ishepatitisc" id="IsHepatitisC_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="IsHepatitisC_n">无</label>
				<input name="ishepatitisc" id="IsHepatitisC_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="IsHepatitisC_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="hepatitisc" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>年</span>
			</div>
		</div>
	</div>
	<!-- HIV 恶性肿瘤、 -->
	<div class="row bgWhite">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>HIV:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="ishiv" id="IsHIV_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="IsHIV_n">无</label>
				<input name="ishiv" id="IsHIV_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="IsHIV_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="hiv" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>年</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span class="ipadText">恶性肿瘤（癌症）:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="iscancer" id="IsCancer_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="IsCancer_n">无</label>
				<input name="iscancer" id="IsCancer_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="IsCancer_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="cancer" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>年</span>
			</div>
		</div>
	</div>
	<!-- 梅毒、颌面部放疗术后 -->
	<div class="row">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>梅毒:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="isyphilis" id="isYphilis_n" value="0" type="radio" onclick="ishaveisyphilis(this.name)"/><label for="isYphilis_n">无</label>
				<input name="isyphilis" id="isYphilis_y" value="1" type="radio" onclick="ishaveisyphilis(this.name)"/><label for="isYphilis_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="syphilis" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>年</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>颌面部放疗术后:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="ismaxillofacial" id="IsMaxillofacial_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="IsMaxillofacial_n">无</label>
				<input name="ismaxillofacial" id="IsMaxillofacial_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="IsMaxillofacial_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="maxillofacial" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>年</span>
			</div>
		</div>
	</div>
	<!-- 任何感染的急性炎症期、吸毒 -->
	<div class="row bgWhite">
		<div class="col-md-2 col-sm-3 col-xs-3 colDefined">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>任何感染的急性炎症期:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="isinflammation" id="isInflammation_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="isInflammation_n">无</label>
				<input name="isinflammation" id="isInflammation_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="isInflammation_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="inflammation" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>年</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-1 col-xs-1 colDefined">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>吸毒:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="isdrugabuse" id="isDrugAbuse_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="isDrugAbuse_n">无</label>
				<input name="isdrugabuse" id="isDrugAbuse_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="isDrugAbuse_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="drugabuse" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>年</span>
			</div>
		</div>
	</div>
	<!-- 心理、精神障碍.皮肤黏膜病变、 -->
	<div class="row">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>心理、精神障碍:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="ispsychosis" id="isPsychosis_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="isPsychosis_n">无</label>
				<input name="ispsychosis" id="isPsychosis_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="isPsychosis_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="psychosis" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>年</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>皮肤黏膜病变:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="ismucousmembrane" id="IsMucousMembrane_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="IsMucousMembrane_n">无</label>
				<input name="ismucousmembrane" id="IsMucousMembrane_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="IsMucousMembrane_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="mucousmembrane" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>年</span>
			</div>
		</div>
	</div>
	<!-- 骨质疏松症用药情况： -->
	<div class="row bgWhite">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>骨质疏松症用药情况:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="ispharmacy" id="IsPharmacy_n" value="0" type="radio" onclick="ishaveillness(this.name);ishavapharmacy(this.name);"/><label for="IsPharmacy_n">无</label>
				<input name="ispharmacy" id="IsPharmacy_y" value="1" type="radio" onclick="ishaveillness(this.name);ishavapharmacy(this.name);"/><label for="IsPharmacy_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="pharmacy" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>年</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<div class="option_div common_style">
				<input name="treatment" id="Treatment_vein" value="0" type="radio" disabled="disabled"/><label for="Treatment_vein">静脉</label>
				<input name="treatment" id="Treatment_take" value="1" type="radio" disabled="disabled"/><label for="Treatment_take">口服</label>
			</div>
		</div>
		<div class="col-md-4 col-sm-4 col-xs-4 colDefined">
			<!-- 填写组合框 -->
			<div class="selectgroup common_style">
				<span class="groupName" style="font-weight: normal;">常用药物：</span>
				<select id="pharmacymedicine" class="groupSelect" multiple title="请选择" style="pointer-events: none;">
					<%--<option value="">请选择</option>--%>
					<option value="钙剂">钙剂</option>
					<option value="阿法骨化醇骨化三醇">阿法骨化醇骨化三醇</option>
					<option value="活性维生素D">活性维生素D</option>
					<option value="骨疏康">骨疏康</option>
					<option value="金天格">金天格</option>
					<option value="筋骨通">筋骨通</option>
					<option value="双磷酸盐类">双磷酸盐类</option>
					<option value="降钙素">降钙素</option>
					<option value="雌激素">雌激素</option>
				</select>
			</div>
			<%--打印展示--%>
			<div class="medicineText">常用药物：<span class="pharmacymedicine"></span></div>
		</div>
	</div>
	<!-- 长期应用糖皮质激素 -->
	<div class="row">
		<div class="col-md-2 col-sm-3 col-xs-3 colDefined combgGray">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>长期应用糖皮质激素:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="isglucocorticoids" id="IsGlucocorticoids_n" value="0" type="radio" onclick="ishaveillness(this.name);ishavaGlucocorticoids(this.name);"/><label for="IsGlucocorticoids_n">无</label>
				<input name="isglucocorticoids" id="IsGlucocorticoids_y" value="1" type="radio" onclick="ishaveillness(this.name);ishavaGlucocorticoids(this.name);"/><label for="IsGlucocorticoids_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="glucocorticoids" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>年</span>
			</div>
		</div>
		<div class="col-md-6 col-sm-5 col-xs-5 colDefined combgGray">
			<!-- 填写组合框 -->
			<div class="selectgroup common_style">
				<span class="groupName" style="font-weight: normal;">常用药物：</span>
				<select id="glucocorticoidsmedicine" class="groupSelect" multiple title="请选择" style="pointer-events: none;">
					<%--<option value="">请选择</option>--%>
					<option value="泼尼松">泼尼松</option>
					<option value="甲泼尼松">甲泼尼松</option>
					<option value="倍他米松">倍他米松</option>
					<option value="得宝松">得宝松</option>
					<option value="泼尼松龙">泼尼松龙</option>
				</select>
			</div>
			<%--打印展示--%>
			<div class="medicineText">常用药物：<span class="glucocorticoidsmedicine"></span></div>
		</div>
	</div>
	<!-- 其他系统疾病 -->
	<div class="row bgWhite">
		<div class="col-md-8 col-sm-8 col-xs-8 colDefined">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>其他系统疾病:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="isotherdiseases" id="isOtherDiseasesString_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="isOtherDiseasesString_n">无</label>
				<input name="isotherdiseases" id="isOtherDiseasesString_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="isOtherDiseasesString_y">有</label>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="otherdiseases" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>年</span>
			</div>
		</div>
	</div>
	<!-- 药物过敏 -->
	<div class="row">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>药物过敏:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<div class="select_group common_style">
				<!-- 选项div -->
				<div class="option_div">
					<input name="isdrugallergy" id="isDrugAllergy_n" value="0" type="radio" onclick="ishaveillness(this.name);ishavaDrugAllergy(this.name);"/><label for="isDrugAllergy_n">无</label>
					<input name="isdrugallergy" id="isDrugAllergy_y" value="1" type="radio" onclick="ishaveillness(this.name);ishavaDrugAllergy(this.name);"/><label for="isDrugAllergy_y">有</label>
				</div>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="allergiclength" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>年</span>
			</div>
		</div>
		<div class="col-md-6 col-sm-6 col-xs-6 colDefined combgGray">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写名称 -->
				<span class="fillWrite_name">过敏药物:</span>
				<!-- 填写框-->
				<input id="drugallergy" placeholder="在此输入..." onblur="TextLengthCheck(this.id,28);" class="fillWrite_input" style="width: 79%;border:0px;" type="text" disabled="disabled"/>
			</div>
		</div>
	</div>
	<!-- 怀孕 -->
	<div class="row bgWhite">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>怀孕:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<div class="select_group common_style">
				<!-- 选项div -->
				<div class="option_div">
					<input name="ispregnancy" id="isPregnancy_n" value="0" type="radio" onclick="ishaveillness(this.name)"/><label for="isPregnancy_n">无</label>
					<input name="ispregnancy" id="isPregnancy_y" value="1" type="radio" onclick="ishaveillness(this.name)"/><label for="isPregnancy_y">有</label>
				</div>
			</div>
		</div>
		<div class="col-md-8 col-sm-8 col-xs-8 colDefined">
			<!-- 时间div -->
			<div class="time_div common_style">
				<input id="pregnancy" oninput="if(value>100||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
				<span>月</span>
			</div>
		</div>
	</div>
	<!-- 正在服用的药物 -->
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12 colDefined combgGray">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写名称 -->
				<span class="fillWrite_name">正在服用的药物(请写明):</span>
				<!-- 填写框-->
				<input id="onmedication" placeholder="华法林，降压药，降糖药..." onblur="TextLengthCheck(this.id,40);" style="width: 75%;border:0px;" class="fillWrite_input" type="text"/>
			</div>
		</div>
	</div>
	<!-- 习惯 -->
	<!-- <div class="row bgWhite">
        <div class="col-md-12 col-sm-12 col-xs-12 colDefined">
            填写组合框
            <div class="fillWrite_group common_style">
                填写名称
                <span class="fillWrite_name">习惯:</span>
                填写框
                <input id="habit" placeholder="在此输入...(此框只能输48个字)" onblur="TextLengthCheck(this.id,48);" style="width: 89%;border:0px;" class="fillWrite_input" type="text"/>
            </div>
        </div>
    </div> -->
	<!-- 抽烟 -->
	<div class="row smoking">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写名称 -->
				<span class="fillWrite_name">吸烟:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写框-->
				<input id="smoketime" class="fillWrite_input" oninput="if(value>100||value<0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/><label>年</label>
			</div>
		</div>
		<div class="col-md-8 col-sm-8 col-xs-8 colDefined">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写名称 -->
				<span class="fillWrite_name" style="font-weight: normal;">抽烟支数/日:</span>
				<!-- 填写框-->
				<input id="smokenum" class="fillWrite_input" oninput="if(value>100||value<0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/>
			</div>
		</div>
	</div>
	<!-- 饮酒 -->
	<div class="row bgWhite">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写名称 -->
				<span class="fillWrite_name">饮酒:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写框-->
				<input id="drinktime" class="fillWrite_input" oninput="if(value>100||value<0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/><label>年</label>
			</div>
		</div>
		<div class="col-md-8 col-sm-8 col-xs-8 colDefined combgGray">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="drinkscale" id="drinkScale_little" value="少量" type="radio"/><label for="drinkScale_little">少量</label>
				<input name="drinkscale" id="drinkScale_center" value="中量" type="radio"/><label for="drinkScale_center">中量</label>
				<input name="drinkscale" id="drinkScale_big" value="大量" type="radio"/><label for="drinkScale_big">大量</label>
			</div>
		</div>
	</div>
	<!-- 磨牙、咀嚼习惯 -->
	<div class="row">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写名称 -->
				<span class="fillWrite_name">磨牙:</span>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写框-->
				<input id="odontoprisis" class="fillWrite_input" oninput="if(value>100||value<0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于100！');value='';}if(value.length>3)value=value.slice(0,3);" type="text"/><label>年</label>
			</div>
		</div>
		<div class="col-md-8 col-sm-8 col-xs-8 colDefined">
			<!-- 选项div -->
			<div class="option_div common_style">
				<input name="odontoprisisdegree" id="odontoprisisDegree_more" value="1" type="radio"/><label for="odontoprisisDegree_more">频繁</label>
				<input name="odontoprisisdegree" id="odontoprisisDegree_few" value="2" type="radio"/><label for="odontoprisisDegree_few">偶尔</label>
				<input name="odontoprisisdegree" id="odontoprisisDegree_dimness" value="3" type="radio"/><label for="odontoprisisDegree_dimness">不清楚</label>
			</div>
		</div>
	</div>
	<div class="row bgWhite">
		<div class="col-md-2 col-sm-2 col-xs-2 colDefined combgGray">
			<!-- 名称div -->
			<div class="name_div common_style">
				<span>咀嚼习惯:</span>
			</div>
		</div>
		<div class="col-md-10 col-sm-10 col-xs-10 colDefined combgGray">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写名称 -->
				<span class="fillWrite_name" style="font-weight: normal;">单侧（右左）:</span>
				<!-- 填写框-->
				<!-- <input style="width:34%;" class="fillWrite_input" type="text"/> -->
				<input style="width:25px;margin-top:15px;margin-right:0px;" name="chewinghabits" id="chewingHabits_l" value="左" type="radio"/><label for="chewingHabits_l" style="margin-right: 5px;">左</label>
				<input style="width:25px;margin-top:15px;margin-right:0px;" name="chewinghabits" id="chewingHabits_r" value="右" type="radio"/><label for="chewingHabits_r" style="margin-right: 5px;">右</label>
				<input style="width:25px;margin-top:15px;margin-right:0px;" name="chewinghabits" id="chewingHabits_d" value="双侧" type="radio"/><label for="chewingHabits_d" style="margin-right: 5px;">双侧</label>
			</div>
		</div>
	</div>
	<!-- 其他 -->
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
			<!-- 填写组合框 -->
			<div class="fillWrite_group common_style">
				<!-- 填写名称 -->
				<span class="fillWrite_name">其他:</span>
				<!-- 填写框-->
				<input id="others" placeholder="在此输入..." onblur="TextLengthCheck(this.id,48);" style="width: 89%;border:0px;" class="fillWrite_input" type="text"/>
			</div>
		</div>
	</div>
	<!-- 问题2 -->
	<div class="row question_row" style="margin-top: 70px;margin-bottom: 20px;">
		<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
			<!-- 问题 -->
			<p id="question_textTwo" class="question_text question_textTwo">2、&nbsp;什么原因导致牙缺失？&nbsp;&nbsp;&nbsp;&nbsp;龋齿/牙周病/外伤...</p>
		</div>
		<div class="col-md-6 col-sm-6 col-xs-6 question_twoInfo">
			<div class="">最近一次拔牙距今<input id="lasttoothextractiontime" oninput="if(value>10000||value<=0||value!=value.replace(/[^\d]/g,'')){layer.alert('此框只能输入数字并且值大于等于0小于10000！');value='';}if(value.length>4)value=value.slice(0,4);" style="border-radius:0px;border:0px;border-bottom: 1px solid #757575;width: 70px;" type="text"/>个月</div>
		</div>
		<div class="col-md-6 col-sm-6 col-xs-6 question_twoInfo">
			<!-- 选择组合框 -->
			<div id="denture" class="select_group common_style" style="background-color:transparent;line-height: 20px;height:24px;">
				<!-- 选项名称 -->
				<span class="select_name" style="margin-top: 0px;">是否做义齿修复:</span>
				<!-- 选项 -->
				<div class="select_item">
					<input name="haveyouhaddenture" id="HaveYouHadDenture_y" value="1" type="radio"/><label for="HaveYouHadDenture_y" style="margin-top: 0px;">是</label>
					<input name="haveyouhaddenture" id="HaveYouHadDenture_n" value="0" type="radio"/><label for="HaveYouHadDenture_n" style="margin-top: 0px;">否</label>
				</div>
			</div>
		</div>
	</div>
	<!-- 问题3 -->
	<div id="question_three" class="row question_row">
		<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
			<!-- 问题 -->
			<p class="question_text">3、&nbsp;选择种植义齿是为了（请您在同意的项目后面画勾）</p>
		</div>
		<ul class="plantTooth_reason">
			<li>
				<label for="reasonsimplantdenturesA" style="font-weight: normal;">希望把义齿作为固定的</label><input name="reasonsimplantdentures" id="reasonsimplantdenturesA" value="希望把义齿作为固定的" type="checkbox"/>
			</li>
			<li>
				<label for="reasonsimplantdenturesB" style="font-weight: normal;">改善义齿固位稳定</label><input name="reasonsimplantdentures" id="reasonsimplantdenturesB" value="改善义齿固位稳定" type="checkbox"/>
			</li>
			<li>
				<label for="reasonsimplantdenturesC" style="font-weight: normal;">改善义齿咀嚼效率</label><input name="reasonsimplantdentures" id="reasonsimplantdenturesC" value="改善义齿咀嚼效率" type="checkbox"/>
			</li>
			<li>
				<label for="reasonsimplantdenturesD" style="font-weight: normal;">改善义齿美观效果</label><input name="reasonsimplantdentures" id="reasonsimplantdenturesD" value="改善义齿美观效果" type="checkbox"/>
			</li>
			<li>
				<label for="reasonsimplantdenturesE" style="font-weight: normal;">改善发音</label><input name="reasonsimplantdentures" id="reasonsimplantdenturesE" value="改善发音" type="checkbox"/>
			</li>
		</ul>
	</div>
	<div class="row question_row" style="text-align: right;">
		<p class="question_text" style="margin-top: 40px;font-size: 16px;"><font style="color:red;font-size: 18px;margin-right: 10px;">*</font>以上所述个人健康状况属实，对隐瞒病情所造成的不良后果，责任自负。</p>
	</div>
	<!-- 手术签名 -->
	<div class="row signature_row">
		<!-- <div class="col-md-6 col-sm-6 col-xs-6">
        </div>
        <div class="col-md-6 col-sm-6 col-xs-6 colDefined">
            患者签名
            <div class="signature_time">
                <div class="signature_box">
                    <span>患者签名:</span>
                    <div id="PatientSignature"></div>
                </div>
                <input id="patienttime" type="text" class="consent_time" readonly="readonly" placeholder="请选择日期"/>
            </div>
        </div> -->
		<div class="col-md-6 col-sm-6 col-xs-6">
		</div>
		<div class="col-md-6 col-sm-6 col-xs-6">
			<!-- 患者签名 -->
			<div class="signature_time">
				<div class="signature_box">
					<span id="patientSignature" style="margin-top: 8px;">患者签名:</span>
					<img id="patientimg" style="width:156px;height:auto;"/>
				</div>
				<input id="patienttime" type="text" class="signatureTime" readonly="readonly" placeholder="请选择日期"/>
			</div>
		</div>
		<div class="col-md-6 col-sm-6 col-xs-6">
		</div>
		<div class="col-md-6 col-sm-6 col-xs-6">
			<!-- 医生签名  -->
			<div class="signature_time">
				<span id="doctorSignature" style="line-height: 50px;">医生签名:</span>
				<img id="img" style="width:156px;height:auto;"/>
				<input id="doctortime" type="text" class="signatureTime" readonly="readonly" placeholder="请选择日期"/>
			</div>
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
</body>
	<script language="javascript"  src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
	<script type="text/javascript">
		var signature="";
		var patientsignature="";
		var doctorstatus=true;
		var patientstatus=true;
		var contextPath = "<%=contextPath%>";
		var id= window.parent.patientObj.id;	//选中患者id
		var order_number= window.parent.patientObj.orderNumber;//选中患者order_number
		var caseId=""; //已存在的病历id
		var menuid=window.parent.menuid;//左侧菜单id
		var seqidFather = "<%=seqidFather%>";
		$(function(){
			
			/* 针对ipad自适应 */
			var userAgent = navigator.userAgent; 
			if (userAgent.indexOf("iPad") > -1){
				$(".sexInfo").css("width","150px"); //年龄
				$("input[name='symptom']").css("width","20px"); //牙齿选项
				$("#HaveYouHadDenture_y").css("margin-left","5px"); //是否做义齿修复
				$(".loseTooth_option label").css("margin-left","2px").css("margin-right","10px"); //牙齿选项
				$(".ipadText").css("font-size","14px"); 
				$("#pressure").css("width","70px");  //目前血压
				$(".backColor").css("background-color","transparent"); //高血压
				$(".backcolorGray").css("background-color","#f5f5f5"); //心脏病心绞痛
				$("#drugallergy").css("width","77%");  //过敏药物
				$("#print_Btn").css("display","none");  //隐藏打印按钮
			}
			/* ******************** */
			
			$("input[type='text']").attr("disabled","disabled").css("background-color","#c3c3c3").css("cursor","no-drop");//禁用输入框
			$(".consent_time").removeAttr("disabled").css("background-color","transparent").css("cursor","auto"); //时间选择框不禁用
			$(".signatureTime").removeAttr("disabled").css("background-color","transparent").css("cursor","auto"); //签名时间不禁用
			$("#antifreezing").removeAttr("disabled").css("background-color","transparent").css("cursor","auto"); //服用抗凝药物不禁用
			//$("#drugallergy").removeAttr("disabled").css("background-color","transparent").css("cursor","auto"); //过敏药物不禁用
			$("#onmedication").removeAttr("disabled").css("background-color","transparent").css("cursor","auto"); //正在服用药物不禁用
			$("#habit").removeAttr("disabled").css("background-color","transparent").css("cursor","auto"); //习惯不禁用
			$("#others").removeAttr("disabled").css("background-color","transparent").css("cursor","auto"); //其他不禁用
			$("#lasttoothextractiontime").removeAttr("disabled").css("background-color","transparent").css("cursor","auto"); //最近一次拔牙时间距今不禁用
			$("#smoketime").removeAttr("disabled").css("background-color","transparent").css("cursor","auto"); //抽烟不禁用
			$("#smokenum").removeAttr("disabled").css("background-color","transparent").css("cursor","auto"); //抽烟数量不禁用
			$("#drinktime").removeAttr("disabled").css("background-color","transparent").css("cursor","auto"); //饮酒不禁用
			$("#odontoprisis").removeAttr("disabled").css("background-color","transparent").css("cursor","auto"); //磨牙不禁用
			//获取当前页面所有按钮
			 getButtonAllCurPage(menuid);
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
		    $(".signatureTime").datetimepicker({
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
		    //多选下拉框初始化
			$(".groupSelect").selectpicker({});
		    //患者姓名、年龄、性别赋值
		    
			$("#patient_name").text(window.parent.patientObj.username);
			$("#patient_num").text(window.parent.patientObj.blcode);  //编号
			$("#patient_sex").text(window.parent.patientObj.sex);
			$("#patient_age").text(window.parent.patientObj.age);
			 
			initZzblInfor();/* 页面赋值判断初始化 */
			// 2019/7/24 lutian 禁止页面拖拽
			document.ondragstart = function() {
	            return false;
	        };
	        
		});
		
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
		
		/* 页面赋值判断初始化 */
		function initZzblInfor(){
			//console.log(id+"--------------查询id");
			var url = contextPath + '/HUDH_ZzblAskAct/findCaseHistoryById.act';
			$.ajax({
				url: url,
				type:"POST",
				dataType:"json",
				async:true,
				data : {
					 id :  id, //临床路径ID
					 order_number : order_number
				},
				success:function(result){
					//console.log(JSON.stringify(result)+"--------------添加成功后查询数据");
					//caseId=result.seqId;  //病历id
					var result;
					if(seqidFather){
						for (var i=0;i<result.length;i++) {
							if(seqidFather==result[i].seqId){
								result=result[i];
							}
						}
					}
					caseId=seqidFather;  //病历id
					//console.log(JSON.stringify(result)+"---------------result");
					/* 判断是否已经填写过内容 */
					if(result.seqId){
						$("#consent_saveBtn").css("display","none");//隐藏保存按钮
						$("#consent_updateBtn").css("display","inline-block");//显示修改按钮
						//赋值 
						for(var key in result){
							//console.log(key+"-------------"+result[key]);
							$("#"+key+"[type='text']").attr("value",result[key]);// 填框赋值
							//常用药物select赋值
							if($("#"+key).find("option").length>0 && result[key]!=null){
								$("#"+key).selectpicker('val', result[key]);
								$("."+key).text(result[key]);
								/*$("#"+key).find("option").each(function(i,obj){
									$(this).removeAttr("selected");
									if($(this).val()==result[key]){
										$(this).prop("selected", true);//针对ipad赋值
										$(this).attr("selected", true);
									}
								});*/
							}
							//单选按钮赋值
							$("input[name="+key+"][type='radio']").each(function(){
								if($(this).val()==result[key]){
								   $(this).attr("checked","checked");
								}
							})
							if(result[key]){
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
							}
							if(key.indexOf("is")>=0 && result[key]==1){
								$("input[name="+key+"]").each(function(i,obj){
									if($(this).val()==1){
										$(this).trigger("click");
									}
								});
							}
						}
						signature=result.doctorsignature;
						if(signature!=""){
							$("#img").attr('src', signature);
							doctorstatus=false;
						}else{
							$("#img").attr('display', 'none');
						}
						patientsignature=result.patientsignature;
						if(patientsignature!=""){
							$("#patientimg").attr('src', patientsignature);
							patientstatus=false;
						}else{
							$("#patientimg").attr('display', 'none');
						}
					}  
					
				}
		  });
		}
		
		/* 牙齿症状选中 */
		function showSymptom() {
		    var obj = document.getElementsByName("symptom");
		    var symptom = "";
		    for ( k in obj ) {
		        if(obj[k].checked)
		        	symptom = symptom + obj[k].value + ';';
		    }
		    return symptom;
		}
		/* 糖尿病控制方式选中 */
		function showdietControl() {
		    var obj = document.getElementsByName("dietcontrol");
		    var dietControl = "";
		    for ( k in obj ) {
		        if(obj[k].checked)
		        	dietControl = dietControl + obj[k].value + ';';
		    }
		    return dietControl;
		}
		/* 选择做种植义齿是为了什么 */
		function showReasonsImplantDentures() {
		    var obj = document.getElementsByName("reasonsimplantdentures");
		    var ReasonsImplantDentures = "";
		    for ( k in obj ) {
		        if(obj[k].checked)
		        	ReasonsImplantDentures = ReasonsImplantDentures + obj[k].value + ';';
		    }
		    return ReasonsImplantDentures;
		}
		
		//修改
		function update(){
			var patient_name = $("#patient_name").val();//患者姓名
			var patient_sex = $("#patient_sex").val();//患者性别
			var patient_age = $("#patient_age").val();//患者年龄
			var agomphosTime = $("#agomphostime").val();//缺牙时间
			var symptom=showSymptom();//牙齿症状选中
			var plantTime = $("#planttime").val();//要求种植修复时间
			var isHypertension = $('input[name="ishypertension"]:checked').val();//是否有高血压
			var hypertension = $("#hypertension").val();//患高血压年限
			var isTakeMedicie = $('input[name="istakemedicie"]:checked').val();//是否有在服药
			var isControl = $('input[name="iscontrol"]:checked').val();//是否控制
			var pressure = $("#pressure").val();//当前血压
			var IsHearDiease = $('input[name="isheardiease"]:checked').val();//是否患有心脏病（心绞痛、心衰）
			var hearDiease = $("#heardiease").val();//患心脏病年限
			var isPrepareMedication = $('input[name="ispreparemedication"]:checked').val();//是否有长期备药在身边
			var isDiabetes = $('input[name="isdiabetes"]:checked').val();//是否患有糖尿病
			var diabetes = $("#diabetes").val();//患糖尿病年限
			var dietControl=showdietControl();//控制饮食的方式
			var isDietControl = $('input[name="isdietcontrol"]:checked').val();//是否控制
			var isInfarction = $('input[name="isinfarction"]:checked').val();//近6个月是否有做过心瓣膜置换术或发生过心梗
			var infarction = $("#infarction").val();//发病或手术时限
			var IsBloodCoagulation = $('input[name="isbloodcoagulation"]:checked').val();//凝血功能不足性疾病
			var bloodCoagulation = $("#bloodcoagulation").val();//患病年限
			var isAntiFreezing = $('input[name="isantifreezing"]:checked').val();//服用抗凝药物或其他可以导致凝血功能障碍的药物
			var antiFreezing = $("#antifreezing").val();//所服药名
			var antiFreezingTime = $("#antifreezingtime").val();//服药年限
			var isHepatitisB = $('input[name="ishepatitisb"]:checked').val();//是否患有乙肝
			var hepatitisB = $("#hepatitisb").val();//患病年限
			var IsHepatitisC = $('input[name="ishepatitisc"]:checked').val();//是否患有丙肝
			var hepatitisC = $("#hepatitisc").val();//患病年限
			var IsHIV = $('input[name="ishiv"]:checked').val();//是否患有HIV
			var Hiv = $("#hiv").val();//患病年限
			var isYphilis = $('input[name="isyphilis"]:checked').val();//是否患有梅毒
			var syphilis = $("#syphilis").val();//患病年限
			var IsCancer = $('input[name="iscancer"]:checked').val();//是否患癌症
			var cancer = $("#cancer").val();//患病年限
			var IsMaxillofacial = $('input[name="ismaxillofacial"]:checked').val();//是否有过颌面部放疗术
			var maxillofacial = $("#maxillofacial").val();//间隔年限
			var isInflammation = $('input[name="isinflammation"]:checked').val();//是否患有感染急性炎症
			var inflammation = $("#inflammation").val();//患病年限
			var IsPharmacy = $('input[name="ispharmacy"]:checked').val();// 是否有骨质酥松用药
			var Treatment = $('input[name="treatment"]:checked').val();// 用药方式
			var pharmacy = $("#pharmacy").val();//用药年限
			var isDrugAbuse = $('input[name="isdrugabuse"]:checked').val();// 是否吸毒
			var drugAbuse = $("#drugabuse").val();//吸毒年限
			var isPsychosis = $('input[name="ispsychosis"]:checked').val();// 是否患有心理、精神障碍
			var psychosis = $("#psychosis").val();//患病年限
			var IsMucousMembrane = $('input[name="ismucousmembrane"]:checked').val();// 是否患有皮肤黏膜病变
			var mucousMembrane = $("#mucousmembrane").val();//患病年限
			var IsGlucocorticoids = $('input[name="isglucocorticoids"]:checked').val();// 长期应用糖皮质激素
			var glucocorticoids = $("#glucocorticoids").val();//使用激素年限
			var isOtherDiseases = $('input[name="isotherdiseases"]:checked').val();//是否患有其他疾病
			var otherDiseases = $("#otherdiseases").val();//患病年限
			var isDrugAllergy = $('input[name="isdrugallergy"]:checked').val();//是否药物过敏
			var drugAllergy = $("#drugallergy").val();//过敏药物
			var allergicLength = $("#allergiclength").val();//过敏时间
			var isPregnancy = $('input[name="ispregnancy"]:checked').val();//是否怀孕
			var pregnancy = $("#pregnancy").val();//受孕时间 单位：月
			var onMedication = $("#onmedication").val();//正在服用药物
			var habit = $("#habit").val();//生活习惯
			var smokeTime = $("#smoketime").val();//吸烟年限
			var smokeNum = $("#smokenum").val();//抽烟数量    支/日
			var drinkTime = $("#drinktime").val();//饮酒年限
			var drinkScale = $('input[name="drinkscale"]:checked').val();//饮酒量    1.少量 2.中量 3.大量
			var odontoprisis = $("#odontoprisis").val();//磨牙年数
			var odontoprisisDegree = $('input[name="odontoprisisdegree"]:checked').val();//磨牙频率 1.频繁 2.偶尔
			var chewingHabits = $('input[name="chewinghabits"]:checked').val();//咀嚼习惯 
			var Others = $("#others").val();//其他
			var LastToothExtractionTime = $("#lasttoothextractiontime").val();//最近一次拔牙距今时间
			var HaveYouHadDenture = $('input[name="haveyouhaddenture"]:checked').val();//是否做义齿修复
			var ReasonsImplantDentures=showReasonsImplantDentures();//选择做义齿是为了什么
			var PatientSignature = $("#PatientSignature").val();//患者签字
			var PatientTime = $("#patienttime").val();//患者签字时间
			var doctorSignature = $("#doctorSignature").val();//医生签字
			var doctorTime = $("#doctortime").val();//医生签字时间     72
			//新增参数
			var hypertensionmedicine = JSON.stringify($("#hypertensionmedicine").val());  //高血压常用药物
			var heardieasemedicine = JSON.stringify($("#heardieasemedicine").val());  //心脏病常用药
			var diabetesoralmedicine = JSON.stringify($("#diabetesoralmedicine").val());  //糖尿病口服常用药
			var diabetesinjectionmedicine = JSON.stringify($("#diabetesinjectionmedicine").val()); //糖尿病注射常用药
			var antifreezingmedicine = JSON.stringify($("#antifreezingmedicine").val());  //服用抗凝药物常用药物
			var pharmacymedicine = JSON.stringify($("#pharmacymedicine").val()); //骨质疏松常用药物
			var glucocorticoidsmedicine = JSON.stringify($("#glucocorticoidsmedicine").val()); //长期应用糖皮质激素常用药
			
			var url = contextPath + '/HUDH_ZzblAskAct/updateCaseHistoryById.act';
	        var param = {
	        		 id:caseId,
	        		 LcljId :  id,
	        		 LcljNum :  order_number,
	        		 username :patient_name,
		        	 sex : patient_sex,
		        	 age : patient_age,
	        		 agomphosTime :  agomphosTime,
	        		 symptom :  symptom,
	        		 plantTime :  plantTime,
	        		 isHypertension :  isHypertension,
	        		 hypertension :  hypertension,
	        		 isTakeMedicie :  isTakeMedicie,
	        		 isControl :  isControl,
	        		 pressure :  pressure,
	        		 IsHearDiease :  IsHearDiease,
	        		 hearDiease :  hearDiease,
	        		 isPrepareMedication :  isPrepareMedication,
	        		 isDiabetes :  isDiabetes,
	        		 diabetes :  diabetes,
	        		 dietControl :  dietControl,
	        		 isDietControl :  isDietControl,
	        		 isInfarction :  isInfarction,
	        		 infarction :  infarction,
	        		 IsBloodCoagulation :  IsBloodCoagulation,
	        		 bloodCoagulation :  bloodCoagulation,
	        		 isAntiFreezing :  isAntiFreezing,
	        		 antiFreezing :  antiFreezing,
	        		 antiFreezingTime :  antiFreezingTime,
	        		 isHepatitisB :  isHepatitisB,
	        		 hepatitisB :  hepatitisB,
	        		 IsHepatitisC :  IsHepatitisC,
	        		 hepatitisC :  hepatitisC,
	        		 IsHIV :  IsHIV,
	        		 Hiv :  Hiv,
	        		 isYphilis :  isYphilis,
	        		 syphilis :  syphilis,
	        		 IsCancer :  IsCancer,
	        		 cancer :  cancer,
	        		 IsMaxillofacial :  IsMaxillofacial,
	        		 maxillofacial :  maxillofacial,
	        		 isInflammation :  isInflammation,
	        		 inflammation :  inflammation,
	        		 IsPharmacy :  IsPharmacy,
	        		 Treatment :  Treatment,
	        		 pharmacy :  pharmacy,
	        		 isDrugAbuse :  isDrugAbuse,
	        		 drugAbuse :  drugAbuse,
	        		 allergicLength :allergicLength,
	        		 isPsychosis :  isPsychosis,
	        		 psychosis :  psychosis,
	        		 IsMucousMembrane :  IsMucousMembrane,
	        		 mucousMembrane :  mucousMembrane,
	        		 IsGlucocorticoids :  IsGlucocorticoids,
	        		 glucocorticoids :  glucocorticoids,
	        		 isOtherDiseases :  isOtherDiseases,
	        		 otherDiseases :  otherDiseases,
	        		 isDrugAllergy :  isDrugAllergy,
	        		 drugAllergy :  drugAllergy,
	        		 isPregnancy :  isPregnancy,
	        		 pregnancy :  pregnancy,
	        		 onMedication :  onMedication,
	        		 habit :  habit,
	        		 smokeTime :  smokeTime,
	        		 smokeNum :  smokeNum,
	        		 drinkTime :  drinkTime,
	        		 drinkScale :  drinkScale,
	        		 odontoprisis :  odontoprisis,
	        		 odontoprisisDegree :  odontoprisisDegree,
	        		 chewingHabits :  chewingHabits,
	        		 Others :  Others,
	        		 LastToothExtractionTime :  LastToothExtractionTime,
	        		 HaveYouHadDenture :  HaveYouHadDenture,
	        		 ReasonsImplantDentures :  ReasonsImplantDentures,
	        		 PatientSignature :  PatientSignature,
	        		 PatientTime :  PatientTime,
	        		 doctorSignature :  doctorSignature,
	        		 doctorTime :  doctorTime,
					//新增参数
					hypertensionmedicine : hypertensionmedicine, //高血压常用药物
					heardieasemedicine : heardieasemedicine, //心脏病常用药
					diabetesoralmedicine : diabetesoralmedicine, //糖尿病口服常用药
					diabetesinjectionmedicine : diabetesinjectionmedicine,//糖尿病注射常用药
					antifreezingmedicine : antifreezingmedicine, //服用抗凝药物常用药物
					pharmacymedicine : pharmacymedicine, //骨质疏松常用药物
					glucocorticoidsmedicine : glucocorticoidsmedicine //长期应用糖皮质激素常用药
	        };
	        //console.log(JSON.stringify(param)+"---------修改参数");
	        $.axseSubmit(url, param,function() {},function(r) {
	        	layer.alert("修改成功！", {
		            end: function() {
		            	//window.parent.location.reload(); //刷新父页面
		            	//保存成功父页面选中该项
		            	//window.parent.document.getElementById("ask_Previous").prev().attr("checked","checked");
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
			var agomphosTime = $("#agomphostime").val();//缺牙时间
			var symptom=showSymptom();//牙齿症状选中
			var plantTime = $("#planttime").val();//要求种植修复时间
			var isHypertension = $('input[name="ishypertension"]:checked').val();//是否有高血压
			var hypertension = $("#hypertension").val();//患高血压年限
			var isTakeMedicie = $('input[name="istakemedicie"]:checked').val();//是否有在服药
			var isControl = $('input[name="iscontrol"]:checked').val();//是否控制
			var pressure = $("#pressure").val();//当前血压
			var IsHearDiease = $('input[name="isheardiease"]:checked').val();//是否患有心脏病（心绞痛、心衰）
			var hearDiease = $("#heardiease").val();//患心脏病年限
			var isPrepareMedication = $('input[name="ispreparemedication"]:checked').val();//是否有长期备药在身边
			var isDiabetes = $('input[name="isdiabetes"]:checked').val();//是否患有糖尿病
			var diabetes = $("#diabetes").val();//患糖尿病年限
			var dietControl=showdietControl();//控制饮食的方式
			var isDietControl = $('input[name="isdietcontrol"]:checked').val();//是否控制
			var isInfarction = $('input[name="isinfarction"]:checked').val();//近6个月是否有做过心瓣膜置换术或发生过心梗
			var infarction = $("#infarction").val();//发病或手术时限
			var IsBloodCoagulation = $('input[name="isbloodcoagulation"]:checked').val();//凝血功能不足性疾病
			var bloodCoagulation = $("#bloodcoagulation").val();//患病年限
			var isAntiFreezing = $('input[name="isantifreezing"]:checked').val();//服用抗凝药物或其他可以导致凝血功能障碍的药物
			var antiFreezing = $("#antifreezing").val();//所服药名
			var antiFreezingTime = $("#antifreezingtime").val();//服药年限
			var isHepatitisB = $('input[name="ishepatitisb"]:checked').val();//是否患有乙肝
			var hepatitisB = $("#hepatitisb").val();//患病年限
			var IsHepatitisC = $('input[name="ishepatitisc"]:checked').val();//是否患有丙肝
			var hepatitisC = $("#hepatitisc").val();//患病年限
			var IsHIV = $('input[name="ishiv"]:checked').val();//是否患有HIV
			var Hiv = $("#hiv").val();//患病年限
			var isYphilis = $('input[name="isyphilis"]:checked').val();//是否患有梅毒
			var syphilis = $("#syphilis").val();//患病年限
			var IsCancer = $('input[name="iscancer"]:checked').val();//是否患癌症
			var cancer = $("#cancer").val();//患病年限
			var IsMaxillofacial = $('input[name="ismaxillofacial"]:checked').val();//是否有过颌面部放疗术
			var maxillofacial = $("#maxillofacial").val();//间隔年限
			var isInflammation = $('input[name="isinflammation"]:checked').val();//是否患有感染急性炎症
			var inflammation = $("#inflammation").val();//患病年限
			var IsPharmacy = $('input[name="ispharmacy"]:checked').val();// 是否有骨质酥松用药
			var Treatment = $('input[name="treatment"]:checked').val();// 用药方式
			var pharmacy = $("#pharmacy").val();//用药年限
			var isDrugAbuse = $('input[name="isdrugabuse"]:checked').val();// 是否吸毒
			var drugAbuse = $("#drugabuse").val();//吸毒年限
			var isPsychosis = $('input[name="ispsychosis"]:checked').val();// 是否患有心理、精神障碍
			var psychosis = $("#psychosis").val();//患病年限
			var IsMucousMembrane = $('input[name="ismucousmembrane"]:checked').val();// 是否患有皮肤黏膜病变
			var mucousMembrane = $("#mucousmembrane").val();//患病年限
			var IsGlucocorticoids = $('input[name="isglucocorticoids"]:checked').val();// 长期应用糖皮质激素
			var glucocorticoids = $("#glucocorticoids").val();//使用激素年限
			var isOtherDiseases = $('input[name="isotherdiseases"]:checked').val();//是否患有其他疾病
			var otherDiseases = $("#otherdiseases").val();//患病年限
			var isDrugAllergy = $('input[name="isdrugallergy"]:checked').val();//是否药物过敏
			var drugAllergy = $("#drugallergy").val();//过敏药物
			var allergicLength = $("#allergiclength").val();//过敏时间
			var isPregnancy = $('input[name="ispregnancy"]:checked').val();//是否怀孕
			var pregnancy = $("#pregnancy").val();//受孕时间 单位：月
			var onMedication = $("#onmedication").val();//正在服用药物
			var habit = $("#habit").val();//生活习惯
			var smokeTime = $("#smoketime").val();//吸烟年限
			var smokeNum = $("#smokenum").val();//抽烟数量    支/日
			var drinkTime = $("#drinktime").val();//饮酒年限
			var drinkScale = $('input[name="drinkscale"]:checked').val();//饮酒量    1.少量 2.中量 3.大量
			var odontoprisis = $("#odontoprisis").val();//磨牙年数
			var odontoprisisDegree = $('input[name="odontoprisisdegree"]:checked').val();//磨牙频率 1.频繁 2.偶尔
			var chewingHabits = $('input[name="chewinghabits"]:checked').val();//咀嚼习惯 
			var Others = $("#others").val();//其他
			var LastToothExtractionTime = $("#lasttoothextractiontime").val();//最近一次拔牙距今时间
			var HaveYouHadDenture = $('input[name="haveyouhaddenture"]:checked').val();//是否做义齿修复
			var ReasonsImplantDentures=showReasonsImplantDentures();//选择做义齿是为了什么
			var PatientSignature = $("#PatientSignature").val();//患者签字
			var PatientTime = $("#patienttime").val();//患者签字时间
			var doctorSignature = $("#doctorSignature").val();//医生签字
			var doctorTime = $("#doctortime").val();//医生签字时间     72
			//新增参数
			var hypertensionmedicine = JSON.stringify($("#hypertensionmedicine").val());  //高血压常用药物
			var heardieasemedicine = JSON.stringify($("#heardieasemedicine").val());  //心脏病常用药
			var diabetesoralmedicine = JSON.stringify($("#diabetesoralmedicine").val());  //糖尿病口服常用药
			var diabetesinjectionmedicine = JSON.stringify($("#diabetesinjectionmedicine").val()); //糖尿病注射常用药
			var antifreezingmedicine = JSON.stringify($("#antifreezingmedicine").val());  //服用抗凝药物常用药物
			var pharmacymedicine = JSON.stringify($("#pharmacymedicine").val()); //骨质疏松常用药物
			var glucocorticoidsmedicine = JSON.stringify($("#glucocorticoidsmedicine").val()); //长期应用糖皮质激素常用药

			var url = contextPath + '/HUDH_ZzblAskAct/saveCaseHistory.act';
	        var param = {
	        		 LcljId :  id,
	        		 LcljNum :  order_number,
	        		 username :patient_name,
		        	 sex : patient_sex,
		        	 age : patient_age,
	        		 agomphosTime :  agomphosTime,
	        		 symptom :  symptom,
	        		 plantTime :  plantTime,
	        		 isHypertension :  isHypertension,
	        		 hypertension :  hypertension,
	        		 isTakeMedicie :  isTakeMedicie,
	        		 isControl :  isControl,
	        		 pressure :  pressure,
	        		 IsHearDiease :  IsHearDiease,
	        		 hearDiease :  hearDiease,
	        		 isPrepareMedication :  isPrepareMedication,
	        		 isDiabetes :  isDiabetes,
	        		 diabetes :  diabetes,
	        		 dietControl :  dietControl,
	        		 isDietControl :  isDietControl,
	        		 isInfarction :  isInfarction,
	        		 infarction :  infarction,
	        		 IsBloodCoagulation :  IsBloodCoagulation,
	        		 bloodCoagulation :  bloodCoagulation,
	        		 isAntiFreezing :  isAntiFreezing,
	        		 antiFreezing :  antiFreezing,
	        		 antiFreezingTime :  antiFreezingTime,
	        		 isHepatitisB :  isHepatitisB,
	        		 hepatitisB :  hepatitisB,
	        		 IsHepatitisC :  IsHepatitisC,
	        		 hepatitisC :  hepatitisC,
	        		 IsHIV :  IsHIV,
	        		 Hiv :  Hiv,
	        		 isYphilis :  isYphilis,
	        		 syphilis :  syphilis,
	        		 IsCancer :  IsCancer,
	        		 cancer :  cancer,
	        		 IsMaxillofacial :  IsMaxillofacial,
	        		 maxillofacial :  maxillofacial,
	        		 isInflammation :  isInflammation,
	        		 inflammation :  inflammation,
	        		 IsPharmacy :  IsPharmacy,
	        		 Treatment :  Treatment,
	        		 pharmacy :  pharmacy,
	        		 isDrugAbuse :  isDrugAbuse,
	        		 drugAbuse :  drugAbuse,
	        		 allergicLength :allergicLength,
	        		 isPsychosis :  isPsychosis,
	        		 psychosis :  psychosis,
	        		 IsMucousMembrane :  IsMucousMembrane,
	        		 mucousMembrane :  mucousMembrane,
	        		 IsGlucocorticoids :  IsGlucocorticoids,
	        		 glucocorticoids :  glucocorticoids,
	        		 isOtherDiseases :  isOtherDiseases,
	        		 otherDiseases :  otherDiseases,
	        		 isDrugAllergy :  isDrugAllergy,
	        		 drugAllergy :  drugAllergy,
	        		 isPregnancy :  isPregnancy,
	        		 pregnancy :  pregnancy,
	        		 onMedication :  onMedication,
	        		 habit :  habit,
	        		 smokeTime :  smokeTime,
	        		 smokeNum :  smokeNum,
	        		 drinkTime :  drinkTime,
	        		 drinkScale :  drinkScale,
	        		 odontoprisis :  odontoprisis,
	        		 odontoprisisDegree :  odontoprisisDegree,
	        		 chewingHabits :  chewingHabits,
	        		 Others :  Others,
	        		 LastToothExtractionTime :  LastToothExtractionTime,
	        		 HaveYouHadDenture :  HaveYouHadDenture,
	        		 ReasonsImplantDentures :  ReasonsImplantDentures,
	        		 PatientSignature :  PatientSignature,
	        		 PatientTime :  PatientTime,
	        		 doctorSignature :  doctorSignature,
	        		 doctorTime :  doctorTime,
					//新增参数
					hypertensionmedicine : hypertensionmedicine, //高血压常用药物
					heardieasemedicine : heardieasemedicine, //心脏病常用药
					diabetesoralmedicine : diabetesoralmedicine, //糖尿病口服常用药
					diabetesinjectionmedicine : diabetesinjectionmedicine,//糖尿病注射常用药
					antifreezingmedicine : antifreezingmedicine, //服用抗凝药物常用药物
					pharmacymedicine : pharmacymedicine, //骨质疏松常用药物
					glucocorticoidsmedicine : glucocorticoidsmedicine //长期应用糖皮质激素常用药
	        };
	        
	        //console.log(JSON.stringify(param)+"-------------提交传参");
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
		
		/* 疾病判断并验证:根据选项框name截取得到时间id并禁用 例:选项框-ishypertension 输入框-hypertension */
		function ishaveillness(objName){
			var inputTextid=objName.substring(2);
			if($("input[name="+objName+"]:checked").val()==0){
				$("#"+inputTextid).val("").attr("disabled","disabled").css("background-color","#c3c3c3");
			}else if($("input[name="+objName+"]:checked").val()==1){
				$("#"+inputTextid).removeAttr("disabled").css("background-color","transparent").css("cursor","auto");
			}
		}
		
		/* 服用抗凝药物单独验证   名字不对应 */
		function ishaveantifreezing(objName){
			var inputTextid=objName.substring(2);
			if($("input[name="+objName+"]:checked").val()==0){
				//$("#"+inputTextid).val("").attr("disabled","disabled").css("background-color","#c3c3c3");  //原输入框
				$("#"+inputTextid+"time").val("").attr("disabled","disabled").css("background-color","#c3c3c3"); //患病年限
				$("#antifreezingmedicine").val("").attr("disabled","disabled").css("background-color","#c3c3c3").css("pointer-events","none"); //常用药物
			}else if($("input[name="+objName+"]:checked").val()==1){
				//$("#"+inputTextid).removeAttr("disabled").css("background-color","transparent").css("cursor","auto"); //原输入框
				$("#"+inputTextid+"time").removeAttr("disabled").css("background-color","transparent").css("cursor","auto"); //患病年限
				$("#antifreezingmedicine").removeAttr("disabled").css("background-color","transparent").css("cursor","auto").css("pointer-events","auto"); //常用药物
			}
		}
		/* 梅毒单独验证   名字不对应 */
		function ishaveisyphilis(objName){
			var inputTextid=objName.substring(1);
			if($("input[name="+objName+"]:checked").val()==0){
				$("#"+inputTextid).val("").attr("disabled","disabled").css("background-color","#c3c3c3");
			}else if($("input[name="+objName+"]:checked").val()==1){
				$("#"+inputTextid).removeAttr("disabled").css("background-color","transparent").css("cursor","auto");
			}
		}
		/* 骨质疏松单独验证，因为多了一个用药方式 */
		function ishavapharmacy(objName){
			if($("input[name="+objName+"]:checked").val()==0){
				$("input[name='treatment']").removeAttr("checked").attr("disabled","disabled"); //静脉或者口服
				$("#pharmacymedicine").val("").attr("disabled","disabled").css("pointer-events","none"); //常用药物
			}else if($("input[name="+objName+"]:checked").val()==1){
				$("input[name='treatment']").removeAttr("disabled").css("cursor","auto"); //静脉或者口服
				$("#pharmacymedicine").removeAttr("disabled").css("cursor","auto").css("pointer-events","auto"); //常用药物
			}
		}
		/* 高血压单独验证，因为多了是否服药是否控制目前血压 */
		function ishavahypertension(objName){
			if($("input[name="+objName+"]:checked").val()==0){
				$("#hypertension").removeAttr("checked").attr("disabled","disabled");  //患病年限
				$("input[name='istakemedicie']").removeAttr("checked").attr("disabled","disabled");  //是否服药
				$("input[name='iscontrol']").removeAttr("checked").attr("disabled","disabled");      //是否控制
				$("#pressure").val("").attr("disabled","disabled").css("background-color","#c3c3c3");  //目前血压
				$("#hypertensionmedicine").val("").attr("disabled","disabled").css("background-color","#c3c3c3").css("pointer-events","none");  //常用药物
			}else if($("input[name="+objName+"]:checked").val()==1){
				$("#hypertension").removeAttr("disabled").css("cursor","auto");
				$("input[name='istakemedicie']").removeAttr("disabled").css("cursor","auto");
				$("input[name='iscontrol']").removeAttr("disabled").css("cursor","auto");
				$("#pressure").removeAttr("disabled").css("background-color","transparent").css("cursor","auto");
				$("#hypertensionmedicine").removeAttr("disabled").css("background-color","transparent").css("cursor","auto").css("pointer-events","auto");
			}
		}
		/* 心脏病 心绞痛、心衰，因为多了一个是否常备药 */
		function ishavaheardiease(objName){
			if($("input[name="+objName+"]:checked").val()==0){
				$("#heardiease").removeAttr("checked").attr("disabled","disabled"); //患病年限
				$("input[name='ispreparemedication']").removeAttr("checked").attr("disabled","disabled"); //是否有常备药
				$("#heardieasemedicine").val("").attr("disabled","disabled").css("pointer-events","none"); //常用药物
			}else if($("input[name="+objName+"]:checked").val()==1){
				$("#heardiease").removeAttr("disabled").css("cursor","auto");
				$("input[name='ispreparemedication']").removeAttr("disabled").css("cursor","auto");
				$("#heardieasemedicine").removeAttr("disabled").css("cursor","auto").css("pointer-events","auto");
			}
		}
		/* 糖尿病，因为多了一个是否控制怎么控制 */
		function ishavadiabetes(objName){
			if($("input[name="+objName+"]:checked").val()==0){
				$("#diabetes").removeAttr("checked").attr("disabled","disabled");   //患病年限
				$("input[name='dietcontrol']").removeAttr("checked").attr("disabled","disabled");   //怎么控制
				$("input[name='isdietcontrol']").removeAttr("checked").attr("disabled","disabled");  //是否控制
				$("#diabetesoralmedicine").val("").attr("disabled","disabled").css("pointer-events","none");  //口服常用药
				$("#diabetesinjectionmedicine").val("").attr("disabled","disabled").css("pointer-events","none");  //注射常用药
			}else if($("input[name="+objName+"]:checked").val()==1){
				$("#diabetes").removeAttr("disabled").css("cursor","auto");
				$("input[name='dietcontrol']").removeAttr("disabled").css("cursor","auto");
				$("input[name='isdietcontrol']").removeAttr("disabled").css("cursor","auto");
				$("#diabetesoralmedicine").removeAttr("disabled").css("cursor","auto").css("pointer-events","auto");
				$("#diabetesinjectionmedicine").removeAttr("disabled").css("cursor","auto").css("pointer-events","auto");
			}
		}
		/* 长期应用糖皮质激素验证*/
		function ishavaGlucocorticoids(objName){
			if($("input[name="+objName+"]:checked").val()==0){
				$("#glucocorticoidsmedicine").val("").attr("disabled","disabled").css("background-color","#c3c3c3").css("pointer-events","none"); //常用药物
			}else if($("input[name="+objName+"]:checked").val()==1){
				$("#glucocorticoidsmedicine").removeAttr("disabled").css("background-color","transparent").css("cursor","auto").css("pointer-events","auto"); //常用药物
			}
		}
		/* 药物过敏单独验证，因为多了过敏时间 */
		function ishavaDrugAllergy(objName){
			if($("input[name="+objName+"]:checked").val()==0){
				$("#allergiclength").removeAttr("checked").attr("disabled","disabled").css("background-color","#c3c3c3");
			}else if($("input[name="+objName+"]:checked").val()==1){
				$("#allergiclength").removeAttr("disabled").css("background-color","transparent").css("cursor","auto");
			}
		}
		
		
		/* 获取拼接牙位并校验 */
		function getValue(inputObj){
			var inputBool=false;
			var toothArr=[];
			var toothString="";
			//牙位输入框
			var inputVal=$("#"+inputObj).val();
			for (var i = 0; i < inputVal.length; i++) {
				if(inputVal[i]<=8 && inputVal[i]>=1){
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
		
		/* function myPreviewAll(){
			LODOP=getLodop();  
			LODOP.PRINT_INIT("主诉及既往病史！");
			var htmlStyle="<style>button{display:none;} .print_addStyle{margin-top:30px!important;} .print_textStyle{margin-top: 8px !important;}.big_titleText{line-height: 70px!important;margin-bottom:20px!important;}.question_textTwo{margin-top: 20px !important;}</style>";
			var html="<!DOCTYPE html>"+document.getElementsByTagName("html")[0].innerHTML+htmlStyle;
			LODOP.ADD_PRINT_HTM(10,10,"100%","100%",html);
			LODOP.PREVIEW();	
		}; */
		
		//打印样式
		function myPreviewAll(){
			if(doctorstatus&&signature==""){
				   $("#img").css("display","none");
			   }
			   if(patientstatus&&patientsignature==""){
				   $("#patientimg").css("display","none");
			   }
			LODOP=getLodop();  
			LODOP.PRINT_INIT("主诉及既往病史！");
			LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");
			var htmlStyle="<style>*{font-size: 10px;}button{display:none;}.time_div>input{display:block;margin-top:3px!important;width:40px!important;height:18px!important;}.fillWrite_group>input{display:block;width:40px!important;height:18px!important;margin-top:3px!important;}#pressure{display:block;width:50px!important;height:18px!important;margin-top:3px!important;}.alreadyInfo{font-size:12px !important;}.rpInfo_import{margin-right:80px;width:auto;}input[type='radio'],input[type='checkbox']{width:10px!important;}.option_div>input{margin-top:5px!important;}.option_div>label{margin-top:15px!important;}";
			htmlStyle+="p{margin:0px!important;padding:0px!important;line-height:14px!important;}.consent_time{width:110px!important;height:18px!important;margin-top:3px!important;}.row>.colDefined{height:24px!important;}.bigtitle{font-size:22px;line-height:22px;margin:45px auto 20px!important;padding-top:0px!important;}.toothInfo{margin-bottom:5px!important;}.patientInfo{padding:10px 0px 0px 15px!important;}.loseTooth_time{width:170px!important;}.loseTooth_option>li{margin-right:5px!important;}.loseTooth_time>span{font-size:12px!important;}";
			htmlStyle+=".mainSuitInfo{padding:0px!important;}.chiefComplaint{padding:0px 0px 0px 0px!important;margin:0px!important;}.common_style span, #anamnesis_continer .common_style label{margin-top:5px!important;}.smoking input{margin-top:2px!important;}.patientInfo{padding-left:0px!important;}.mainSuitInfo{padding-left:0px!important;height:40px!important;padding-bottom:0px!important;}.question_row{margin-top:10px!important;}.question_row .colDefined{height:18px!important;}.question_text{height:18px!important;font-size:12px!important;margin:0px!important;}.plantTooth_reason li{height:22px!important;margin-bottom:0px!important;}";
			htmlStyle+=".plantTooth_reason input{vertical-align:middle!important;}.question_row .colDefined input{display:inline-block!important;height:20px!important;}.question_twoInfo{height:30px!important;}.question_twoInfo .select_item input{vertical-align:middle!important;margin-top:10px!important;}#question_three{margin-top:0px!important;}.loseTooth_option>li label{margin-top:5px!important;}.signature_box span{font-size:12px!important;}#denture{background-color:white!important;}.loseTooth_time>span,input{display:inline!important;}.loseTooth_time{width:auto!important;}.common_style{background-color: transparent!important;}";
			htmlStyle+="input[type='text']{border:0px!important;padding:0px!important;text-align:center!important;font-weight:bold!important;}#agomphostime,#planttime{padding-left:10px!important;text-align:left!important;vertical-align:top;}#drugallergy{width:270px!important;border-bottom:1px solid #adaaaa!important;text-align:left!important;padding-left:10px!important;}#onmedication{width:570px!important;border-bottom:1px solid #adaaaa!important;text-align:left!important;padding-left:10px!important;}#habit{width:660px!important;border-bottom:1px solid #adaaaa!important;text-align:left!important;padding-left:10px!important;}#others{width:660px!important;border-bottom:1px solid #adaaaa!important;text-align:left!important;padding-left:10px!important;}";
			htmlStyle+="#antifreezing{width:170px!important;border-bottom:1px solid #adaaaa!important;text-align:left!important;padding-left:10px!important}#lasttoothextractiontime{border-bottom:1px solid #adaaaa!important;}.medicineText{display:block;margin-top: 6px!important;}.selectgroup{display:none !important;}";
			htmlStyle+=".backColor{background-color:transparent!important;}.backcolorGray{background-color:#f5f5f5!important;}.selectgroup .groupSelect{margin-top:4px!important;font-size:10px;height:18px;appearance:none;-webkit-appearance:none;-moz-appearance:none;}select::-ms-expand{display:none;}#consent_updateBtn{display:none!important;}.question_two{margin-bottom:5px!important;}</style>";
			var html="<!DOCTYPE html>"+document.getElementsByTagName("html")[0].innerHTML+htmlStyle;
			LODOP.ADD_PRINT_HTM(0,0,"100%","100%",html);
			LODOP.PREVIEW();	
		}; 
		
		function getButtonPower() {
		    var menubutton1 = "";
		    for (var i = 0; i < listbutton.length; i++) {
		        if (listbutton[i].qxName == "zsbs_xgbd") {
		           $("#consent_updateBtn").removeClass("hidden");
		        }
		    }
		    $("#bottomBarDdiv").append(menubutton1);
		}

	</script>

</html>