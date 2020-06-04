<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String status = request.getParameter("status");
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
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<style type="text/css">
*{
	margin: 0px;
	padding: 0px;
	font-size: 16px;
	line-height: 30px;
}
.boxBig{
	padding: 20px;
}
/* 内容 */
.examine_continer{
	/* border:1px solid red; */ 
}
.examine_continer ul,ol{
	margin: 0px;
	padding: 0px;
}
.examine_continer label{
	margin-bottom: 0px;
}
/* 字体设置 */
.examine_continer span{
	font-weight: normal;
}
/*行*/
.examine_continer>.row{
	/* width:100%;
	margin: 0px;
	padding: 0px;
	overflow: hidden; */
}
.cavityExamine>.row{
	margin-bottom: 0px;
}
/* 栅格布局自定义样式 */
.examine_continer>.row>.colDefined{
	float:left;
	display:flex;
	flex-direction: row;
	margin: 0px;
	padding: 0px;
	/* margin-bottom: 5px; */
}
/* 输入框 */
.examine_continer input[type="text"]{
	height:30px;
	background-color: transparent;
	border: 1px solid #c3c3c3;
	vertical-align: middle;
	-webkit-appearance: none;
	outline: none;
	font-weight: bold;
}
/* 多选框 */
.examine_continer input[type="checkbox"]{
	background-color: transparent;
	width:15px;
	height:15px;
	vertical-align: sub;
	outline: none;
/* 	margin-left: 8px; */
}
/* 单选框 */
.examine_continer input[type="radio"]{
	background-color: transparent;
	width:15px;
	height:15px;
	vertical-align: sub;
	outline: none;
}

.examine_continer .bigtitle{
    width: 100%;
    font-size: 26px;
    font-weight: bold;
    border-bottom: 2px solid #776c6c;
    padding-bottom: 15px;
   	margin: 10px 0;
}

/* 信息输入组合框 */
.examine_continer .rpInfo_import{
	width:100%;
}

.examine_continer .rpInfo_import>input{
	width:67%;
	border:0px;
	font-weight: bold;
}
/* 多选组合框 */
.examine_continer .zl_multiple{
	width:100%;
	overflow: hidden;;
}
.examine_continer .zl_multiple>span{

	 float: left;
        width: 8%;
}
.examine_continer .zl_multiple>ul{
	  width: 100%;
        list-style: none;
        margin: 0;
        padding: 0;
}
.examine_continer .zl_multiple>ul>li{
	 width: 20%;
     float: left;
}
.zl_optiondiv>input{
	margin-left: 8px;
}

/* 选项框 */
.examine_continer .zl_optiondiv{
	/* width: 100%; */
}
.examine_continer .zl_optiondiv>span{

}
/* 身体状况评估 */
.body_assess  .zl_multiple>ul>li{
	width:20%;
}
/* 多选框 */
.examine_continer .moreSelect_div{
}
.examine_continer .moreSelect_div>li{
	float:left;
	width:14%;
	
}
.examine_continer .moreSelect_div>li>input{
	margin-left: 8px;
}
/* 填写框 */
.examine_continer .zl_fillWritediv>span{
	display: inline-block;
	line-height: 20px;
}
.examine_continer .zl_fillWritediv>input{
	width: 20%;
    vertical-align: bottom;
    text-align: center;
    border: 0;
    border-bottom: 1px solid #5b5b5b;
}
/* 牙位图 */
.examine_continer .zl_toothMapdiv{
	/* border:1px solid blue; */
}
.examine_continer .zl_toothMapdiv>span{
	margin-right: 5%;
	float: left;
	/* margin-top: 18px; */
}
.examine_continer .zl_toothMapdiv .tooth_map{
	 float: left;
    display: inline-block;
    width: 74%;
    height: 80px;
    
}
.examine_continer .zl_toothMapdiv .tooth_map>li{
	width:49%;
	height:25px;
	float:left;
}
.horizontal{
	margin: 10px 0;
	font-weight:600;
	width: 30%;
	
}
.examine_continer .zl_toothMapdiv .tooth_map>li>input{
	border:0px;
    width: 90%;
    margin-left: 5%;
    height: 25px;
    text-align: center;
}
.examine_continer .zl_toothMapdiv .tooth_map>li:FIRST-CHILD{
	border-bottom: 1px solid black;
	border-right: 1px solid black;
}
.examine_continer .zl_toothMapdiv .tooth_map>li:FIRST-CHILD+li{
	border-bottom: 1px solid black;
}
.examine_continer .zl_toothMapdiv .tooth_map>li:FIRST-CHILD+li+li{
	border-right: 1px solid black;
}

/* 签名框 */
.examine_continer .zl_signature{
	/* width:100%; */
}

.examine_continer .tooth_xray .zl_toothMapdiv{
	width: 74%;
}
.examine_continer .tooth_xray .zl_toothMapdiv .tooth_map{
	width:100%;
	/* margin-top: 10px; */
}
/* 填空 */
.examine_continer .tooth_xray .tooth_xrayfillWrite{
	width:70%;
	overflow: hidden;
	display: flex;
	flex-direction: row;
	justify-content:space-between;
	flex-wrap:wrap;
}
.examine_continer .tooth_xray .tooth_xrayfillWrite>li{
	width:50%;
	min-width:200px;
	float: left;
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
        label {
            font-weight: normal;
            margin-top: 18px;
        }

        input::-webkit-input-placeholder {
            font-size: 14px;
            font-weight: normal;
        }

        input:-moz-placeholder {
            font-size: 14px;
            font-weight: normal;
        }

        input::-moz-placeholder {
            font-size: 14px;
            font-weight: normal;
        }

        input:-ms-input-placeholder {
            font-size: 14px;
            font-weight: normal;
        }

        label {
            margin-top: 0px;
        }

        #others {
            position: relative;
            margin-top: 13px;
            width: 87%;
            height: 200px;
            padding: 10px;
            font-size: 17px;
            margin-left: 16px;
        }

        .examine_continer>.row>.colDefined {
            margin: 5px 18px;
        }

        #patienttime {
            text-align: center;
        }

        #doctortime {
            text-align: center;
        }

        .title {
            border-bottom: 2px solid #776c6c;
            padding-bottom: 15px;
        }

        .input {
            width: 27%;
            color: #00a6c0;
        }


        .item {
            font-weight: 600 !important;
        }	
        #logoImg{
		    width: 10%;
		    margin:10px 0 10px 0;
		}
	 /*分隔线 */
    .line {
        display: block;
        border-top:2px dotted #776c6c;
    }
</style>
</head>
<body>
<!--startprint-->
    <div class="boxBig">
    <img id="logoImg" src="<%=contextPath%>/static/image/kqdsFront/jiagong/logoName.png">
    <i class="line"></i>
        <div class="container-fluid examine_continer">
            <!-- 标题 -->
            
            <div class="row">
                <div>
                    <h2 class="bigtitle title">告知通知书</h2>
                </div>
            </div>
            <!-- 患者信息 -->
            <div class="row">
                <div class="col-md-8 col-sm-8 col-xs-8 colDefined">
                    <div class="rpInfo_import">
                        <span class="item">首诊时间：</span>
                        <input id="patient_time" type="text" disabled="disabled" class="input" />
                    </div>
                    <div class="rpInfo_import">
                        <span class="item">编号：</span>
                        <font class="alreadyInfo input" id="patient_num"></font>
                    </div>
                </div>
                <div class="col-md-12 col-sm-12 col-xs-12 colDefined">
                    <div class="rpInfo_import">
                        <span class="item">患者姓名：</span>
                        <input id="patient_name" type="text" disabled="disabled" class="input"" />
                    </div>
                    <div class=" rpInfo_import">
                        <span class="item">性别：</span>
                        <input id="patient_sex" type="text" disabled="disabled" class="input" />
                    </div>
                    <div class="rpInfo_import">
                        <span class="item">年龄：</span>
                        <input id="patient_age" type="text" disabled="disabled" class="input" />
                    </div>
                </div>

                <div class="col-md-8 col-sm-8 col-xs-8 colDefined">
                    <!-- 信息输入组合框 -->
                    <div class="rpInfo_import">
                        <span class="item">证件号码：</span>
                        <input id="patient_idNumber" type="text" disabled="disabled" class="input" />
                    </div>
                    <div class="rpInfo_import">
                        <span class="item">出生年月：</span>
                        <input id="patient_date" type="text" disabled="disabled" style="width: 100px;color: #00a6c0;" />
                    </div>
                </div>

                <div class="col-md-12 col-sm-12 col-xs-12 colDefined" style="height: 31px;">
                    <!-- 信息输入组合框 -->
                    <div class="rpInfo_import">
                        <span class="item">联系电话：</span>
                        <input id="patient_phone" type="text" disabled="disabled" class="input" />
                    </div>
                    <div class="rpInfo_import">
                        <span class="item">紧急联系人：</span>
                        <input id="patient_instancyName" type="text" disabled="disabled" class="input" />
                    </div>
                    <div class="rpInfo_import">
                        <span class="item">紧急联系人电话：</span>
                        <input id="patient_instancyPhone" type="text" disabled="disabled" class="input" />
                    </div>
                </div>

                <div class="col-md-12 col-sm-12 col-xs-12 colDefined">
                    <!-- 信息输入组合框 -->
                    <div class="rpInfo_import">
                        <span class="item">现居地址：</span>
                        <input id="patient_site" type="text" disabled="disabled" class="input" />
                    </div>
                </div>
                <div class="col-md-1 col-sm-1 col-xs-1 colDefined"></div>
            </div>
        </div>
        <!-- 身体状况评估 -->
        <div class="container-fluid examine_continer">
            <div class="row">
                <div>
                    <div class="zl_multiple" style="margin-left:20px;">
                        <ul style=" border-bottom: 2px solid #776c6c;padding-bottom: 30px;">
                            <li>
                                <div class="zl_optiondiv">
                                    <label for="consultation_opinionA" style="font-weight: 600;">特殊告知：</label>
                                </div>
                            </li>
                            <li>
                                <div class="zl_optiondiv">
                                    <input name="consultation_opinion" id="consultation_opinionA" value="0" type="radio"
                                        onclick="ishaveillness(this.name);" type="radio" />
                                    <label for="consultation_opinionA">有</label>
                                </div>
                            </li>
                            <li>
                                <div class="zl_optiondiv">
                                    <input name="consultation_opinion" id="consultation_opinionB" value="1" type="radio"
                                        onclick="ishaveillness(this.name);" type="radio" />
                                    <label for="consultation_opinionB">无</label>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid examine_continer cavityExamine">
            
                <textarea oninput="show1(event,50)" id="others" placeholder="特殊告知内容" type="text" /></textarea>
                <div>我已知悉医生告知的情况，仍同意种植牙方案。</div>
                <div class="signature_time timed">
                    <div class="zl_signature">
                        <span id="doctorSignature" style="line-height: 50px;">医生签名:</span>
                        <img id="img" style="width:156px;height:auto;"/>
                        <input id="doctortime" type="text" class="consent_time inputheight2" readonly="readonly"
                            placeholder="请选择日期" />
                    </div>
                </div>
                <div class="signature_time timep">
                    <div class="zl_signature">
                        <span id="patientSignature" style="margin-top: 8px;">患者签名:</span>
					 <img id="patientimg" style="width:156px;height:auto;"/>
                        <input id="patienttime" type="text" class="consent_time inputheight2" readonly="readonly"
                            placeholder="请选择日期" />
                    </div>
                </div>      
            <!--endprint-->
        </div>
        <!-- 按钮 -->
            <div class="btns" style="margin-top:68px;">
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
	var status = "<%=status%>";
	var pageurl = '<%=contextPath%>/HUDH_FlowAct/findPatientInformation.act';
	var id;	//选中患者id
	var order_number;//选中患者order_number
	var menuid=window.parent.menuid;//左侧菜单id
	$(function () {
	    if (window.parent.consultSelectPatient) {
	        formParentObj = window.parent.consultSelectPatient;
	        id = formParentObj.seqid;	//选中患者id
	        order_number = formParentObj.orderNumber;//选中患者order_number
	    } else {
	        formParentObj = window.parent.patientObj;
	        id = formParentObj.id;	//选中患者id
	        order_number = formParentObj.orderNumber;//选中患者order_number
	    }
	    //时间选择
	    $(".consent_time").datetimepicker({
	        language: 'zh-CN',
	        minView: 2,
	        format: 'yyyy-mm-dd',
	        autoclose: true,//选中之后自动隐藏日期选择框   
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
	
	    $.ajax({
	        type: "POST",
	        url: pageurl,
	        data: {
	        	usercode: window.parent.consultSelectPatient.usercode,
	        	status: status,
	        	id: id,
	        	order_number: order_number
	        	},
	        dataType: "json",
	        success: function (r) {
	        	for(var key in r){
					//console.log(key+"-------------"+r[key]);
					$("#"+key).attr("value",r[key]);// 填框赋值
					$("#others").text(r["remark"]);//textarea赋值
		
	        	}
					
	            $("#patient_time").attr("value", r.cztime);
	            $("#patient_num").text(r.usercode);
	            $("#patient_name").attr("value", r.username);
	            $("#patient_sex").attr("value", r.sex);
	            $("#patient_age").attr("value", r.age);
	            $("#patient_idNumber").attr("value", r.idcardno);
	            $("#patient_date").attr("value", r.birthday);
	            $("#patient_phone").attr("value", r.phonenumber1);
	            $("#patient_instancyName").attr("value", r.emergencyContact);
	            $("#patient_instancyPhone").attr("value", r.emergencyPhone);
	            $("#patient_site").attr("value", r.provincename + r.cityname + r.townname + r.streetName);
	            if (status == '1') {
	                if (r.whether == '0') {
	                    $("#consultation_opinionA").attr("checked", true);
	                } else {
	                    $("#consultation_opinionB").attr("checked", true);
	                }
	                $("#others").html(r.content);
	                $("#doctortime").val(r.doctortime);
	                $("#patienttime").val(r.patienttime);
	                signature=r.doctorsignature;
					if(signature!=""){
						$("#img").attr('src', signature);
						doctorstatus=false;
					}else{
						$("#img").attr('display', 'none');
					}
					patientsignature=r.patientsignature;
					if(patientsignature!=""){
						$("#patientimg").attr('src', patientsignature);
						patientstatus=false;
					}else{
						$("#patientimg").attr('display', 'none');
					}
	                $("#consent_saveBtn").css("display", "none");//隐藏保存按钮
					$("#consent_updateBtn").css("display","inline-block");//显示修改按钮
	                $("input").attr("disabled", "disabled");//查看信息的时候禁止在填写
	            }
	            getButtonAllCurPage(menuid);
	        }
	    });
	});
	
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
		if(!doctorstatus&&patientstatus){
			updatePatientSignature();
		}
	}
	//更新
	function updatePatientSignature(){
		var url = contextPath + '/HUDH_NotificationAct/updateNotification.act';
		var patienttime = $("#patienttime").val();//修复医生签名时间
        var param = {
        		LcljId: id, //临床路径ID
    	        LcljNum: order_number, //节点编号
        		patientsignature :  patientsignature,//患者签名
        		patienttime : patienttime//患者签名时间

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
	if ($("input[name=consultation_opinion]:checked").val() == undefined) {
	    $("#others").attr("disabled", "disabled");
	}
	
	/* 特殊告知有无，有填写内容*/
	function ishaveillness(objName) {
	    $("#others").attr("disabled", "disabled");
	    if ($("input[name=" + objName + "]:checked").val() == 0) {
	        $("#others").removeAttr("disabled");
	
	    } else if ($("input[name=" + objName + "]:checked").val() == 1) {
	        $("#others").attr("disabled", "disabled");
	        $("#others").val('');
	    }
	
	}
	
	/*判断文本框的长度  */
	function show1(event, num) {
	    var size = event.target.value
	    if (size.length > num) {
	        layer.open({
	            title: '提示',
	            content: '此输入框文字长度不能超过' + num + '字!',
	            end: function () {
	            }
	        });
	        return;
	
	    }
	}
	
	function save() {
	    var patient_name = $("#patient_name").val();//患者姓名
	    var patient_num = $("#patient_num").text();//患者编号
	    var doctortime = $("#doctortime").val();//医生签字时间
	    var patienttime = $("#patienttime").val();//患者签字时间
	    var whether = $('input[name="consultation_opinion"]:checked').val();//有无特殊告知
	    var content = $("#others").val();//内容
	    var url = contextPath + '/HUDH_NotificationAct/saveNotification.act';
	    var param = {
	    	LcljId: id, //临床路径ID
	    	LcljNum: order_number, //节点编号
	        username: patient_name,
	        usercode: patient_num,
	        doctortime: doctortime, //医生签字时间
	        patienttime: patienttime, //医生签字时间
	        content: content,
	        whether: whether,
	        patientsignature:patientsignature,
	        doctorsignature:signature
	    };
	    $.axseSubmit(url, param, function () { }, function (r) {
	        layer.alert("保存成功！", {
	            end: function () {
	                //window.parent.location.reload(); //刷新父页面
	                var frameindex = parent.layer.getFrameIndex(window.name);
	                parent.layer.close(frameindex); //再执行关闭
	            }
	        });
	    }, function (r) {
	        layer.alert("保存失败！");
	    });
	}
	
	function update() {
	    var doctortime = $("#doctortime").val();//医生签字时间
	    var patienttime = $("#patienttime").val();//患者签字时间
	    var whether = $('input[name="consultation_opinion"]:checked').val();//有无特殊告知
	    var content = $("#others").val();//内容
	    var url = contextPath + '/HUDH_NotificationAct/updateNotification.act';
	    var param = {
	        LcljId: id, //临床路径ID
	        LcljNum: order_number, //节点编号
	        doctortime: doctortime, //医生签字时间
	        patienttime: patienttime, //医生签字时间
	        content: content,
	        whether: whether,
	        patientsignature:patientsignature,
	        doctorsignature:signature
	    };
	    $.axseSubmit(url, param, function () { }, function (r) {
	        layer.alert("修改成功！", {
	            end: function () {
	                //window.parent.location.reload(); //刷新父页面
	                var frameindex = parent.layer.getFrameIndex(window.name);
	                parent.layer.close(frameindex); //再执行关闭
	            }
	        });
	    }, function (r) {
	        layer.alert("修改失败！");
	    });
	}
	
	function myPreviewAll() {    
	    if(doctorstatus&&signature==""){
			$("#img").css("display","none");
		}
		if(patientstatus&&patientsignature==""){
			$("#patientimg").css("display","none");
		}

		    bdhtml=window.document.body.innerHTML;   
		    sprnstr="<!--startprint-->";   
		    eprnstr="<!--endprint-->";   
		    prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);   
		    prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));   
		    var htmlStyle="<style>#repairDoctorSignature{display: inline-block;}#repairImg{width:80px !important;}img{width:80px !important;}#operationdoctortime{display: inline-block;}button{display:none;}.distance{margin-top: 10px !important;}#repair_continer .rp_toothGroup>ul>li{margin-left: 3%;}*{font-size: 12px;line-height: 16px;}#repair_continer input[type='checkbox']{width:12px !important;height:12px !important;margin-top: 15px !important;}.lodopPrintborder{border-right: 2px solid black !important;}.patient{padding:0!important;margin:0!important;}.inputheight2{border: 1px solid transparent!important;}.consent_updateBtn{display:none!important;}#logoImg{text-align:left!important;width:27%!important;left:0%!important;top:17px!important;}#requirerestor{height:40px!important;}</style>";
		    window.document.body.innerHTML=prnhtml+htmlStyle;  
		    window.print();  //打印
		    document.body.innerHTML=bdhtml; //恢复页面
	};		
	

	function getButtonPower() {
	    var menubutton1 = "";
	    for (var i = 0; i < listbutton.length; i++) {
	        if (listbutton[i].qxName == "zsbs_xgbd"&&doctorstatus&&patientstatus) {
	           $("#consent_updateBtn").removeClass("hidden");
	        }
	    }
	    $("#bottomBarDdiv").append(menubutton1);
}
</script>
</html>