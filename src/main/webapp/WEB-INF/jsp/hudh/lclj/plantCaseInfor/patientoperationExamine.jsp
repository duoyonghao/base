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

	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/hudh/lclj/flowdetail/css/base.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />

	<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
	<!-- 引入封装ajax方法文件,（影响元素contenteditable="true"属性） -->
	<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
	<style type="text/css">
		*{
			margin: 0px;
			padding: 0px;
			font-size: 15px;
			line-height:30px;
			/*line-height:60px;  */
		}
		label{
			margin-bottom: 0px;
			margin:0 3% 0 3%;
		}
		#consultation_opinion{
			outline:none;
			border:none;
			resize:none;
			width: 90%;
			color: #00a6c0;
			font-weight: bold;
			margin-left: 5%;
		}
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
		#content{
			font-weight: bold;
			margin-bottom: 20px;
			padding-top: 10px;
		}
		#content font{
			font-size: 15px;
			color:#00a6c0;
			font-weight: bold;
		}
		/* 标题 */
		#content .bigtitle{
			width:65%;
			display: inline-block;
			text-align: center;
			font-size: 26px;
			line-height: 26px;
			margin: 45px auto 35px;
			letter-spacing: 1px;
			font-weight: bold;
		}
		/* 	logo */
		.logoImg {
			width: 150px;
			height: 45px;
		}
		/* 详细文字介绍 */
		#content .consent_text{
			width:100%;
			font-weight: normal;
		}
		/* 第二条：输入输入框 */
		#content .consent_text .text_input{
			width:90%;
			height: 30px;
			border: 0px;
			border-radius: 0px;
			text-align: center;
			vertical-align: middle;
			font-weight: bold;
			background-color: transparent;
		}
		#content .consent_text .text_input2{
			width:50%;
			height: 30px;
			border: 0px;
			border-radius: 0px;
			text-align: center;
			vertical-align: middle;
			font-weight: bold;
			background-color: transparent;
			border-bottom: 1px solid #333;
		}
		#content .time{
			line-height:30px;
			width:20%;
			text-align: center;
			border: 1px solid #ccc;
			color:#00a6c0;
			font-weight: bold;
			margin:0 2% 0 2%;
		}
		/* 签名 */
		#content .consent_signature{
			overflow: hidden;
			margin-top: 10px;
			margin-bottom: 20px;
		}
		#content .consent_signature>.signature_time{
			width: 40%;
			position: relative;
		}
		#content .consent_signature>.signature_time>.signature_box{
			width:100%;
		}
		#content .consent_signature>.signature_time>.signature_box>span{
			font-weight: normal;
		}
		.content .colDefined .contentItem tbody tr{
			height: 35px;
		}
		.content .colDefined .contentItem tbody tr td {
			width: 12.5%;
			text-align: center;
		}
		/* 时间选择框 */
		#content .consent_signature>.signature_time>input {
			width: 40%;
			position: absolute;
			right: 0px;
			bottom: 0px;
			text-align: center;
			border: 1px solid #ccc;
		}
		/* 按钮 */
		.btns{
			width:100%;
			text-align:center;
			margin-top: 40px;
			margin-bottom: 20px;
		}
		.btns button{
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

		/* 输入框 */
		#content input[type="text"]{
			font-weight: bold;
			color: #00a6c0;
			line-height: 28px;
			border-radius: 3px;
			text-align: center;
		}
		#content input[type="text"]:disabled{
			background-color: transparent;
		}
		/* 输入牙位 */
		#content .consent_text .tooth_map{
			display:inline-block;
			width: 80%;
			height: 55px;
			margin-bottom:-5px;
			margin-left: 5px;

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
		#content .consent_text .tooth_map>li>.tooth_input{
			display:block;
			width:100%;
			height:100%;
			padding:0px;
			border:0px;
			text-align: center;
		}
		@page{
			size:205mm 280mm;
			margin: 0 auto;
		}
	</style>
</head>
<body style="padding: 0% 3%;">
<!--startprint-->
<div>
	<div id="content" class="content">
		<!-- 标题 -->
		<img class="logoImg" src="<%=contextPath%>/static/image/kqdsFront/jiagong/logoName.png">
		<h2 class="bigtitle" style="margin-top: 0">种植牙术前安全核查单(护患)</h2>
		<div class="row consent_text" style="">
			<div class="col-md-12 col-sm-12 colDefined">
				<table class="contentItem" border="1" width="100%">
					<tbody>
					<tr>
						<td><span>患者编号</span></td>
						<td><font type="text" class="patient_usercode"></font></td></td>
						<td><span>患者姓名</span></td>
						<td><font type="text" class="patient_name"></font></td>
						<td><span>性别</span></td>
						<td><font type="text" class="patient_sex"></font></td>
						<td><span>年龄</span></td>
						<td><font type="text" class="patient_age"></font></td>

					</tr>
					<tr>
						<td colspan="1"><span>手术医生</span>
						</td>
						<td colspan="1">
							<font type="text" id="patient_doctor" class="patient_doctor"></font>
						</td>
						<td colspan="2"><span>已服抗菌药</span>
							<label><input class="" type="radio" name="antibacterialmedicine" value="0"/><span class=" ">是</span></label>
							<label><input class="" type="radio" name="antibacterialmedicine" value="1"/><span class=" ">否</span></label>
						</td>
						<td colspan="2"><span>碘伏过敏史</span>
							<label><input class="" type="radio" name="iodineallergy" value="0"/><span class=" ">是</span></label>
							<label><input class="" type="radio" name="iodineallergy" value="1"/><span class=" ">否</span></label>
						</td>
						<td colspan="2"><span>麻药过敏史</span>
							<label><input class="" type="radio" name="anestheticallergy" value="0"/><span class=" ">是</span></label>
							<label><input class="" type="radio" name="anestheticallergy" value="1"/><span class=" ">否</span></label>
						</td>
					</tr>
					<tr>
						<td colspan="2"><span>拔牙牙位</span></td>
						<td colspan="2"><span>种植牙位</span></td>
						<td colspan="2" rowspan="2">辅助手术<input id="assist_operation" class="text_input2 assist_operation assistoperation" placeholder="" onblur="TextLengthCheck(this.id,10);props(this.id);" type="text"/></td>
						<td colspan="2" rowspan="2">手术方式<input id="plant_system" class="text_input2 plant_system plantsystem" placeholder="" onblur="TextLengthCheck(this.id,10);props(this.id);" type="text"/></td>
					</tr>
					<tr>
						<td colspan="2" class="tooth_height" style="height: 70px">
							<ul class="tooth_map">
								<li>
									<input id="uplefttoothbitone" onblur="TextLengthCheck(this.id,10);props(this.id);" class="tooth_input uplefttoothbitone" type="text">
								</li>
								<li>
									<input id="uperrighttoothbitone" onblur="TextLengthCheck(this.id,10);props(this.id);" class="tooth_input uperrighttoothbitone " type="text">
								</li>
								<li>
									<input id="leftlowertoothbitone" onblur="TextLengthCheck(this.id,10);props(this.id);" class="tooth_input leftlowertoothbitone" type="text">
								</li>
								<li>
									<input id="lowrighttoothbitone" onblur="TextLengthCheck(this.id,10);props(this.id);" class="tooth_input lowrighttoothbitone" type="text">
								</li>
							</ul>
						</td>
						<td colspan="2" class="tooth_height" style="height: 70px">
							<ul class="tooth_map">
								<li>
									<input id="uplefttoothbittwo" onblur="TextLengthCheck(this.id,10);props(this.id);" class="tooth_input uplefttoothbittwo" type="text">
								</li>
								<li>
									<input id="uperrighttoothbittwo" onblur="TextLengthCheck(this.id,10);props(this.id);" class="tooth_input uperrighttoothbittwo" type="text">
								</li>
								<li>
									<input id="leftlowertoothbittwo" onblur="TextLengthCheck(this.id,10);props(this.id);" class="tooth_input leftlowertoothbittwo" type="text">
								</li>
								<li>
									<input id="lowrighttoothbittwo" onblur="TextLengthCheck(this.id,10);props(this.id);" class="tooth_input lowrighttoothbittwo" type="text">
								</li>
							</ul>
						</td>
					</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 手术签名 -->
		<div class="consent_signature">
			<!-- 患者签名 -->
			<div class="signature_time" style="float: left;">
				<div class="signature_box">
					<span id="patientSignature" style="margin-top: 8px;">患者签名:</span>
					<img id="patientimg" style="width:156px;height:auto;"/>
				</div>
				<input id="patienttime" type="text" class="patienttime consent_time inputhidden" readonly="readonly" placeholder="请选择日期"/>
			</div>
			<!-- 医生签名 -->
			<div class="signature_time" style="float: right;">
				<div class="signature_box">
					<span id="nurseSignature1" style="line-height: 50px;">护士签名:</span>
					<img id="nurseimg1" style="width:156px;height:auto;"/>
				</div>
				<input id="nursetime1" type="text" class="nursetimeone consent_time inputhidden" readonly="readonly" placeholder="请选择日期"/>
			</div>
		</div>
		<i style="border: 1px dashed #333;width: 100%;display: block;margin-top: 10%"></i>
		<!-- 标题 -->
		<img class="logoImg" src="<%=contextPath%>/static/image/kqdsFront/jiagong/logoName.png">
		<h2 class="bigtitle top">种植牙术前安全核查单(医护)</h2>
		<div class="row consent_text" style="">
			<div class="col-md-12 col-sm-12 colDefined">
				<table class="contentItem" border="1" width="100%">
					<tbody>
					<tr>
						<td><span>患者编号</span></td>
						<td><font type="text" class="patient_usercode"></font></td></td>
						<td><span>患者姓名</span></td>
						<td><font type="text" class="patient_name"></font></td></td>
						<td><span>性别</span></td>
						<td><font type="text" class="patient_sex"></font></td></td>
						<td><span>年龄</span></td>
						<td><font type="text" class="patient_age"></font></td></td>
					</tr>
					<tr>
						<td><span>手术医生</span></td>
						<td colspan="3"><font type="text" class="patient_doctor"></font></td>
						<td><span>手术日期</span></td>
						<td colspan="3"><input id="operationtime" type="text" class="operationtime consent_time inputhidden" style="border:none;" readonly="readonly" placeholder="请选择日期"/></td>
					</tr>
					<tr>
						<td><span>血压</span></td>
						<td><input id="blood_pressure" class="bloodpressure text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/></td>
						<td><span>脉搏</span></td>
						<td><input id="pulse" class="pulse text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/></td>
						<td><span>血糖</span></td>
						<td><input id="blood_glucose" class="bloodglucose text_input" onblur="TextLengthCheck(this.id,10);" placeholder="此框只能输10个字" type="text"/></td>
						<td><span>凝血功能</span></td>
						<td><input id="cruor_function" class="cruorfunction text_input" placeholder="此框只能输10个字" onblur="TextLengthCheck(this.id,10);" type="text"/></td>
					</tr>
					<tr>
						<td><span>白细胞数</span></td>
						<td><input id="whiteblood_cell" class="whitebloodcell text_input" placeholder="此框只能输10个字" onblur="TextLengthCheck(this.id,10);" type="text"/></td>
						<td><span>中性粒细胞</span></td>
						<td><input id="neutrophile_cell" class="neutrophilecell text_input" placeholder="此框只能输10个字" onblur="TextLengthCheck(this.id,10);" type="text"/></td>
						<td><span>红细胞数</span></td>
						<td><input id="redblood_cell" class="redbloodcell text_input" placeholder="此框只能输10个字" onblur="TextLengthCheck(this.id,10);" type="text"/></td>
						<td><span>血红蛋白</span></td>
						<td><input id="oxyphorase" class="oxyphorase text_input" placeholder="此框只能输10个字" onblur="TextLengthCheck(this.id,10);" type="text"/></td>
					</tr>
					<tr>
						<td><span>术前用药</span></td>
						<td colspan="2"><input id="premedicate" class="premedicate text_input" placeholder="此框只能输10个字" onblur="TextLengthCheck(this.id,10);" type="text"/></td>
						<td><span>用药时间</span></td>
						<td colspan="2"><span>术前<input class="takemedicineminutes time inputhidden" id="hour" type="text" onblur="checkTime(this.id);"/>时 <input class="takemedicineminutes time inputhidden" id="minute" type="text" onblur="checkTime(this.id);"/>分</span></td>
						<td><span>用药剂量</span></td>
						<td><input id="measure" class="takemedicinemeasure text_input" placeholder="此框只能输10个字" onblur="TextLengthCheck(this.id,10);" type="text"/></td>
					</tr>
					<tr class="reuseData">
						<td colspan="2"><span>拔牙牙位</span></td>
						<td colspan="2"><span>种植牙位</span></td>
						<td colspan="2" rowspan="2">辅助手术<input class="assist_operation assistoperation text_input2" placeholder="" onblur="TextLengthCheck(this.id,10);" type="text" disabled/></td>
						<td colspan="2" rowspan="2">手术方式<input class="plant_system plantsystem text_input2" placeholder="" onblur="TextLengthCheck(this.id,10);" type="text" disabled/></td>
					</tr>
					<tr class="reuseData">
						<td colspan="2" class="tooth_height" style="height: 70px">
							<ul class="tooth_map">
								<li>
									<input onblur="TextLengthCheck(this.id,10);" class="tooth_input uplefttoothbitone" type="text" disabled/>
								</li>
								<li>
									<input onblur="TextLengthCheck(this.id,10);" class="tooth_input uperrighttoothbitone" type="text" disabled/>
								</li>
								<li>
									<input onblur="TextLengthCheck(this.id,10);" class="tooth_input leftlowertoothbitone" type="text" disabled/>
								</li>
								<li>
									<input onblur="TextLengthCheck(this.id,10);" class="tooth_input lowrighttoothbitone" type="text" disabled/>
								</li>
							</ul>
						</td>
						<td colspan="2" class="tooth_height" style="height: 70px">
							<ul class="tooth_map">
								<li>
									<input onblur="TextLengthCheck(this.id,10);" class="tooth_input uplefttoothbittwo" type="text" disabled/>
								</li>
								<li>
									<input onblur="TextLengthCheck(this.id,10);" class="tooth_input uperrighttoothbittwo" type="text" disabled/>
								</li>
								<li>
									<input onblur="TextLengthCheck(this.id,10);" class="tooth_input leftlowertoothbittwo" type="text" disabled/>
								</li>
								<li>
									<input onblur="TextLengthCheck(this.id,10);" class="tooth_input lowrighttoothbittwo" type="text" disabled/>
								</li>
							</ul>
						</td>
					</tr>
					<tr>
						<td><span>会诊意见</span></td>
						<td colspan="11" style="height:100px"><textarea id="consultation_opinion" class="consultationopinion" onblur="TextLengthCheck(this.id,100);" placeholder="此框只能输100个字" type="text" rows="3" cols="50"></textarea></td>
					</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 手术签名 -->
		<div class="consent_signature">
			<!-- 患者签名 -->
			<div class="signature_time" style="float: left;">
				<div class="signature_box">
					<span id="nurseSignature2" style="line-height: 50px;">护士签名:</span>
					<img id="nurseimg2" style="width:156px;height:auto;"/>
				</div>
				<input id="nursetime2" type="text" class="nursesignaturetime consent_time inputhidden" readonly="readonly" placeholder="请选择日期"/>
			</div>
			<!-- 医生签名 -->
			<div class="signature_time" style="float: right;">
				<div class="signature_box">
					<span id="doctorSignature" style="line-height: 50px;">医生签名:</span>
					<img id="img" style="width:156px;height:auto;"/>
				</div>
				<input id="doctortime" type="text" class="doctorsignaturetime consent_time inputhidden" readonly="readonly" placeholder="请选择日期"/>
			</div>
		</div>
	</div>
</div>
<!--endprint-->
<!-- 按钮 -->
<div class="btns">
	<button id="consent_saveBtn" onclick="save()" style="height: 33px;" readonly="readonly">保存</button>
	<button id="consent_updateBtn" style="display: none;" class="consent_updateBtn hidden" onclick="update()">修改表单</button>
	<button id="print_Btn" onclick="myPreviewAll()" style="height: 33px;" readonly="readonly">打印本页内容</button>
</div>
</div>
<script language="javascript"  src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
<script type="text/javascript">
    var signature="";
    var patientsignature="";
    var doctorstatus=true;
    var patientstatus=true;
    var nursesignature1="";
    var nursesignature2="";
    var nursestatus1=true;
    var nursestatus2=true;
    var contextPath = "<%=contextPath%>";
    var patientInformation=window.parent.patientObj;//患者信息
    var id=patientInformation.id;	//选中患者id
    var order_number= patientInformation.orderNumber; //选中患者order_number
    var patient_usercode= patientInformation.blcode;//选中患者usercode
    var patient_seqid;
    var menuid=window.parent.menuid;//左侧菜单id
    var updataid;
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
        getUserInformation(patient_usercode);
        initZzblInfor();/* 页面赋值判断初始化 */
        // 禁止页面拖拽
        document.ondragstart = function() {
            return false;
        };
        getButtonAllCurPage(menuid);
    });
    /* 初始化患者信息 */
    function getUserInformation(usercode){
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
                //患者姓名、年龄、性别赋值
// 		        	 console.log(JSON.stringify(r)+'----rr');
                $(".patient_name").text(r.username);
                $(".patient_sex").text(r.sex);
                $(".patient_age").text(r.age);
                $(".patient_doctor").text(patientInformation.plantPhysician);
                $(".patient_usercode").text(r.usercode);
                patient_seqid=r.seq_id;
            }
        });
    }
    // 		复用内容牙位及辅助手术手术方式
    function props(thi){
        var changetooth=$("."+thi).val();
        $(".reuseData").find("."+thi).val(changetooth);
    }
    // 校验术前用药时间（限定24小时，60分钟）
    function checkTime(thi){
        var time = $("#"+thi).val();
        if(isNaN(time)){//限定数字
            layer.alert("请输入正确的时间！");
            $("#"+thi).val("");
            return;
        }
        if(time.length>2){//限定时间长度
            layer.alert("请输入正确的时间！");
            $("#"+thi).val("");
            return;
        }
        if(!(/(^[1-9]\d*$)/.test(time))){//限定正整数
            layer.alert("请输入正确的时间！");
            $("#"+thi).val("");
            return;
        }
        if(thi="minute"){//限定分钟
            if(time>60){
                layer.alert("请输入正确的时间！");
                $("#"+thi).val("");
                return;
            }
        }
        if(thi="hour"){//限定小时
            if(time>24){
                layer.alert("请输入正确的时间！");
                $("#"+thi).val("");
                return;
            }
        }
    }
    /* 文字长度校验方法   obj：元素id  textNum：限制文字长度 */
    function TextLengthCheck(obj,textNum){
        var objTextVal=$("#"+obj).val();
        var checkTitleBefore=$("#"+obj).parent(".rpInfo_import").find("span").text();
        var checkTitle=checkTitleBefore.substring(0,checkTitleBefore.indexOf("：")); // 校验文字长长度的标题
        if(objTextVal.length>textNum){
            $("#"+obj).attr("maxlength",textNum);
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
        var url = contextPath + '/HUDH_MedicalRecordsAct/findVerification.act';
        $.ajax({
            url: url,
            type:"POST",
            dataType:"json",
            data : {
                id :  id, //临床路径ID
                order_number : order_number
            },
            success:function(result){
                var res=result[0];
                // updataid=res.seqId;
                // console.log(JSON.stringify(res)+'----result');
                /* 判断是否已经填写过内容 */
                if(result.length>0) {
                    updataid=res.seqId;
                    $("#consent_saveBtn").css("display", "none");//隐藏保存按钮
                    $("#consent_updateBtn").css("display", "inline-block");//显示修改按钮
                    signature=res.doctorsignature;
                    if(signature!=""){
                        $("#img").attr('src', signature);
                        doctorstatus=false;
                    }else{
                        $("#img").attr('display', 'none');
                    }
                    patientsignature=res.patientsignature;
                    if(patientsignature!=""){
                        $("#patientimg").attr('src', patientsignature);
                        patientstatus=false;
                    }else{
                        $("#patientimg").attr('display', 'none');
                    }

                    nursesignature1=res.nursesignature1;
                    if(nursesignature1!=""){
                        $("#nurseimg1").attr('src', nursesignature1);
                        nursestatus1=false;
                    }else{
                        $("#nurseimg1").attr('display', 'none');
                    }
                    nursesignature2=res.nursesignature2;
                    if(nursesignature2!=""){
                        $("#nurseimg2").attr('src', nursesignature2);
                        nursestatus2=false;
                    }else{
                        $("#nurseimg2").attr('display', 'none');
                    }
                    //赋值
                    for (var i in res) {
                        $("input[name=" + i + "][type='radio']").each(function (k, e) {
                            var that=this;
                            var split = res[i].split(",");
                            for (var j = 0; j < split.length; j++) {
                                if ($(that).val() == split[j]) {
                                    $(that).attr("checked", "checked");
                                }
                            }
                        })//单多选框赋值
                        $("." + i + "[type='text']").attr("value", res[i]);// 填框赋值
                        if(i="consultationopinion"){
                            $(".consultationopinion").text(res[i]);// textarea赋值
                        }
                    }
                }else{

                }

            }
        });
    }
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
        if(doctorstatus&&!nursestatus2){
            updateSignature("doctor");
        }
    }

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
        if(patientstatus&&!nursestatus1){
            updateSignature("patient");
        }
    }
    var nurseSignature1 = document.getElementById("nurseSignature1");
    nurseSignature1.onclick = function(){
        if(nursestatus1){
            layer.open({
                type: 2,
                title: '签字',
                shadeClose: true,
                shade: 0.6,
                area: ['70%', '65%'],
                content: contextPath + '/SignatureAct/toSignature.act?category=护士1'
            });
        }
    }
    function addNurseSignature1(){
        $("#nurseimg1").css("display","");
        $("#nurseimg1").attr('src', nursesignature1);
        if(!patientstatus&&nursestatus1){
            updateSignature("nurse1");
        }
    }
    var nurseSignature2 = document.getElementById("nurseSignature2");
    nurseSignature2.onclick = function(){
        if(nursestatus2){
            layer.open({
                type: 2,
                title: '签字',
                shadeClose: true,
                shade: 0.6,
                area: ['70%', '65%'],
                content: contextPath + '/SignatureAct/toSignature.act?category=护士2'
            });
        }
    }
    function addNurseSignature2(){
        $("#nurseimg2").css("display","");
        $("#nurseimg2").attr('src', nursesignature2);
        if(!doctorstatus&&nursestatus2){
            updateSignature("nurse2");
        }
    }
    function updateSignature(status){
        var param;
        if(status=="doctor"){
            var doctortime = $("#doctortime").val();
            param = {
                seqId:updataid,
                doctorSignature :  signature,//医生签名
                doctorsignaturetime : doctortime//医生签名时间
            };
        }else if(status=="nurse2"){
            var nursetime2 = $("#nursetime2").val();//手术护士签名时间
            param = {
                seqId:updataid,
                nurseSignature2 :  nursesignature2,//护士签名
                nursesignaturetime : nursetime2//护士签名时间
            };
        }else if(status=="nurse1"){
            var nursetime1 = $("#nursetime1").val();//护士签字时间
            param = {
                seqId:updataid,
                nurseSignature1 :  nursesignature1,//护士签名
                nursetimeone : nursetime1//护士签名时间
            };
        }else if(status=="patient"){
            var patientTime = $("#patienttime").val();//患者签字时间
            param = {
                seqId:updataid,
                patientSignature :  patientsignature,//医生签名
                patienttime : patientTime//医生签名时间
            };
        }
        //更新
        var url = contextPath + '/HUDH_MedicalRecordsAct/SaveVerification.act';
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
    function save() {
// 		护患（核查单）
//手术医生
        var patient_doctor=$("#patient_doctor").text(); //（复用）
//既往史
        var antibacterial_medicine = $('input[name="antibacterialmedicine"]:checked').val();//是否已服抗菌药  0是1否
        var iodine_allergy = $('input[name="iodineallergy"]:checked').val();//是否有碘伏过敏史   0是1否
        var anesthetic_allergy = $('input[name="anestheticallergy"]:checked').val(); //是否有麻药过敏史   0是1否
//  更改拔牙位、种植牙位展示start--8//（复用）
        var upleftToothBitOne = $("#uplefttoothbitone").val();
        var uperRightToothBitOne = $("#uperrighttoothbitone").val();
        var leftLowerToothBitOne = $("#leftlowertoothbitone").val();
        var lowRightToothBitOne = $("#lowrighttoothbitone").val();
        var upleftToothBitTwo = $("#uplefttoothbittwo").val();
        var uperRightToothBitTwo = $("#uperrighttoothbittwo").val();
        var leftLowerToothBitTwo = $("#leftlowertoothbittwo").val();
        var lowRightToothBitTwo = $("#lowrighttoothbittwo").val();
// 	end
        var assist_operation = $("#assist_operation").val();//辅助手术
        var plant_system = $("#plant_system").val();//种植系统
        var patientTime = $("#patienttime").val();//患者签字时间
        var nursetime1 = $("#nursetime1").val();//护士签字时间
// 		医护（核查单）
//手术时间
        var operationtime=$("#operationtime").val();//手术时间
//既往史
        var blood_pressure= $("#blood_pressure").val();//血压
        var pulse = $("#pulse").val(); //脉搏
        var blood_glucose = $("#blood_glucose").val(); //血糖
        var cruor_function = $("#cruor_function").val(); //凝血功能
        var whiteblood_cell= $("#whiteblood_cell").val();//白细胞数
        var neutrophile_cell = $("#neutrophile_cell").val(); //中性粒细胞
        var redblood_cell = $("#redblood_cell").val(); //红细胞数
        var oxyphorase = $("#oxyphorase").val();  //血红蛋白
        var premedicate=$("#premedicate").val(); //术前用药
        var takemedicine_hour=$("#hour").val(); //用药时间小时
        var takemedicine_minutes=$("#minute").val();//用药时间分钟
        var takemedicine_measure=$("#measure").val();//用药剂量
//会诊意见
        var consultation_opinion = $("#consultation_opinion").val();//会诊意见
// 签名时间
        var nursetime2 = $("#nursetime2").val();//手术护士签名时间
        var doctortime = $("#doctortime").val();// 手术医生签名时间

        var parms={
            userId:patient_seqid,//患者的信息id（复用）
            lcljId :  id, //临床路径ID（复用）
            orderNumber :  order_number, //节点编号（复用）
//  	护士-患者
            //手术医生1
            patientDoctor:patient_doctor, //（复用）
            //患者既往史是否3
            antibacterialMedicine:antibacterial_medicine,
            iodineAllergy:iodine_allergy,
            anestheticAllergy:anesthetic_allergy,
            // 更改拔牙位、种植牙位以及辅助手术种植系统展示start（复用）10
            uplefttoothbitone : upleftToothBitOne,
            uperrighttoothbitone : uperRightToothBitOne,
            leftlowertoothbitone : leftLowerToothBitOne,
            lowrighttoothbitone : lowRightToothBitOne,
            uplefttoothbittwo : upleftToothBitTwo,
            uperrighttoothbittwo : uperRightToothBitTwo,
            leftlowertoothbittwo : leftLowerToothBitTwo,
            lowrighttoothbittwo : lowRightToothBitTwo,
            assistOperation : assist_operation,//辅助手术
            plantSystem :plant_system,//种植系统
            //	end
            patienttime :  patientTime,
            nursetimeone :  nursetime1,
// 	      医生-护士
            //手术医生时间
            operationtime:operationtime,
            // 患者既往史12
            bloodPressure :blood_pressure,//血压
            pulse : pulse,//脉搏
            bloodGlucose : blood_glucose,//血糖
            cruorFunction :cruor_function,//凝血功能
            whitebloodCell:whiteblood_cell,//白细胞数
            neutrophileCell:neutrophile_cell,  //中性粒细胞
            redbloodCell:redblood_cell, //红细胞数
            oxyphorase:oxyphorase,//血红蛋白
            premedicate:premedicate,//术前用药
            takemedicineHour:takemedicine_hour,//用药时间小时
            takemedicineMinutes:takemedicine_minutes,//用药时间分钟
            takemedicineMeasure:takemedicine_measure, //用药剂量
            consultationOpinion:consultation_opinion, //会诊意见
            //签名时间
            nursesignaturetime : nursetime2, //手术护士签名
            doctorsignaturetime : doctortime, //手术医生签名
            doctorSignature:signature,
            patientSignature:patientsignature,
            nurseSignature1:nursesignature1,
            nurseSignature2:nursesignature2
        };
        // console.log(JSON.stringify(parms)+"-----parms2222");
        //return;
        var url = contextPath + '/HUDH_MedicalRecordsAct/SaveVerification.act';
        $.ajax({
            url: url,
            type:"POST",
            dataType:"json",
            data :parms,
            success:function(result){
                // console.log(JSON.stringify(result)+"----result");
                layer.alert("保存成功！", {
                    end: function() {
                        window.parent.location.reload(); //刷新父页面
                        var frameindex = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(frameindex); //再执行关闭
                    }
                });
            }
        });
    }
    //修改
    function update(){
// 		护患（核查单）
//手术医生
        var patient_doctor=$("#patient_doctor").text(); //（复用）
//既往史
        var antibacterial_medicine = $('input[name="antibacterialmedicine"]:checked').val();//是否已服抗菌药  0是1否
        var iodine_allergy = $('input[name="iodineallergy"]:checked').val();//是否有碘伏过敏史   0是1否
        var anesthetic_allergy = $('input[name="anestheticallergy"]:checked').val(); //是否有麻药过敏史   0是1否
//  更改拔牙位、种植牙位展示start--8//（复用）
        var upleftToothBitOne = $("#uplefttoothbitone").val();
        var uperRightToothBitOne = $("#uperrighttoothbitone").val();
        var leftLowerToothBitOne = $("#leftlowertoothbitone").val();
        var lowRightToothBitOne = $("#lowrighttoothbitone").val();
        var upleftToothBitTwo = $("#uplefttoothbittwo").val();
        var uperRightToothBitTwo = $("#uperrighttoothbittwo").val();
        var leftLowerToothBitTwo = $("#leftlowertoothbittwo").val();
        var lowRightToothBitTwo = $("#lowrighttoothbittwo").val();
// 	end
        var assist_operation = $("#assist_operation").val();//辅助手术
        var plant_system = $("#plant_system").val();//种植系统
        var patientTime = $("#patienttime").val();//患者签字时间
        var nursetime1 = $("#nursetime1").val();//护士签字时间
// 		医护（核查单）
//手术时间
        var operationtime=$("#operationtime").val();//手术时间
//既往史
        var blood_pressure= $("#blood_pressure").val();//血压
        var pulse = $("#pulse").val(); //脉搏
        var blood_glucose = $("#blood_glucose").val(); //血糖
        var cruor_function = $("#cruor_function").val(); //凝血功能
        var whiteblood_cell= $("#whiteblood_cell").val();//白细胞数
        var neutrophile_cell = $("#neutrophile_cell").val(); //中性粒细胞
        var redblood_cell = $("#redblood_cell").val(); //红细胞数
        var oxyphorase = $("#oxyphorase").val();  //血红蛋白
        var premedicate=$("#premedicate").val(); //术前用药
        var takemedicine_hour=$("#hour").val(); //用药时间小时
        var takemedicine_minutes=$("#minute").val();//用药时间分钟
        var takemedicine_measure=$("#measure").val();//用药剂量
//会诊意见
        var consultation_opinion = $("#consultation_opinion").val();//会诊意见
// 签名时间
        var nursetime2 = $("#nursetime2").val();//手术护士签名时间
        var doctortime = $("#doctortime").val();// 手术医生签名时间
        var param={
            seqId:updataid,
            userId:patient_seqid,//患者的信息id（复用）
            lcljId :  id, //临床路径ID（复用）
            orderNumber :  order_number, //节点编号（复用）
//  	护士-患者
            //手术医生1
            patientDoctor:patient_doctor, //（复用）
            //患者既往史是否3
            antibacterialMedicine:antibacterial_medicine,
            iodineAllergy:iodine_allergy,
            anestheticAllergy:anesthetic_allergy,
            // 更改拔牙位、种植牙位以及辅助手术种植系统展示start（复用）10
            uplefttoothbitone : upleftToothBitOne,
            uperrighttoothbitone : uperRightToothBitOne,
            leftlowertoothbitone : leftLowerToothBitOne,
            lowrighttoothbitone : lowRightToothBitOne,
            uplefttoothbittwo : upleftToothBitTwo,
            uperrighttoothbittwo : uperRightToothBitTwo,
            leftlowertoothbittwo : leftLowerToothBitTwo,
            lowrighttoothbittwo : lowRightToothBitTwo,
            assistOperation : assist_operation,//辅助手术
            plantSystem :plant_system,//种植系统
            //	end
            patienttime :  patientTime,
            nursetimeone :  nursetime1,
// 	      医生-护士
            //手术医生时间
            operationtime:operationtime,
            // 患者既往史12
            bloodPressure :blood_pressure,//血压
            pulse : pulse,//脉搏
            bloodGlucose : blood_glucose,//血糖
            cruorFunction :cruor_function,//凝血功能
            whitebloodCell:whiteblood_cell,//白细胞数
            neutrophileCell:neutrophile_cell,  //中性粒细胞
            redbloodCell:redblood_cell, //红细胞数
            oxyphorase:oxyphorase,//血红蛋白
            premedicate:premedicate,//术前用药
            takemedicineHour:takemedicine_hour,//用药时间小时
            takemedicineMinutes:takemedicine_minutes,//用药时间分钟
            takemedicineMeasure:takemedicine_measure, //用药剂量
            consultationOpinion:consultation_opinion, //会诊意见
            //签名时间
            nursesignaturetime : nursetime2, //手术护士签名
            doctorsignaturetime : doctortime, //手术医生签名
            doctorSignature:signature,
            patientSignature:patientsignature,
            nurseSignature1:nursesignature1,
            nurseSignature2:nursesignature2
        };
        //console.log(JSON.stringify(param)+"---------修改参数");
        var url = contextPath + '/HUDH_MedicalRecordsAct/SaveVerification.act';
        $.axseSubmit(url, param,function() {},function(r) {
            layer.alert("修改成功！", {
                end: function() {
                    var frameindex = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(frameindex); //再执行关闭
                }
            });
        },function(r){
            layer.alert("修改失败！");
        });
    }
    function getButtonPower() {
        var menubutton1 = "";
        for (var i = 0; i < listbutton.length; i++) {
            if (listbutton[i].qxName == "zsbs_xgbd") {
                $("#consent_updateBtn").removeClass("hidden");
            }
        }
        $("#bottomBarDdiv").append(menubutton1);
    }
    //打印方法
    function myPreviewAll() {
        bdhtml=window.document.body.innerHTML;
        sprnstr="<!--startprint-->";
        eprnstr="<!--endprint-->";
        prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
        prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
        var htmlStyle="<style>#content font{font-size: 12px;}.tooth_height{line-height:10px}.top{margin-top:100px}button{display:none;}*{font-size: 12px;line-height: 24px;}.inputhidden{border: 1px solid transparent!important;}::-webkit-input-placeholder{color:transparent;}</style>";
        window.document.body.innerHTML=prnhtml+htmlStyle;
        window.print();  //打印
        window.document.body.innerHTML=bdhtml; // 恢复页面
        window.location.reload();
    }
    /* 打印页面方法 */
    function myPreviewAll2(){
        LODOP=getLodop();
        LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_完整全页");
        var htmlStyle="<style>#content font{font-size: 12px;}.tooth_height{line-height:10px}button{display:none;}*{font-size: 12px;line-height: 24px;}.inputhidden{border: 1px solid transparent!important;}</style>";
        var html="<!DOCTYPE html>"+document.getElementsByTagName("html")[0].innerHTML+htmlStyle;
        LODOP.ADD_PRINT_HTM(10,10,"100%","100%",html);
        LODOP.PREVIEW();
    };
</script>
</body>
</html>