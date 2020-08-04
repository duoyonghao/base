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
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/static/css/kqdsFront/plantCase/examineDiagnose.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script><!-- 引入封装ajax方法文件 -->
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<style type="text/css">
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
            width: 46%;
            position: absolute;
            right: 0px;
            bottom: 0px;
            text-align: center;
        }

        #doctortime {
            width: 46%;
            position: absolute;
            right: 0px;
            bottom: 0px;
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

        .timed {
            width: 24%;
            margin-left: 61%;
            position: absolute;
            bottom: 40px;
        }

        .timep {
            width: 24%;
            margin-left: 64%;
            position: absolute;
            margin-top: -22px;
        }

        .item {
            font-weight: 600 !important;
        }	
</style>
</head>
<body>
    <div class="boxBig">
        <div class="container-fluid examine_continer">
            <!-- 标题 -->
            <div class="row">
                <div>
                    <h2 class="bigtitle title">告知通知书</h2>
                </div>
            </div>
            <!-- 患者信息 -->
            <div class="row">
                <div class="col-md-6 col-sm-6 col-xs-6 colDefined">
                    <div class="rpInfo_import">
                        <span class="item">首诊时间：</span>
                        <input id="patient_time" type="text" disabled="disabled" class="input" />
                    </div>
                    <div class="rpInfo_import">
                        <span class="item">编号：</span>
                        <font class="alreadyInfo input" id="patient_num"></font>
                    </div>
                </div>
                <div class="col-md-9 col-sm-9 col-xs-9 colDefined">
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

                <div class="col-md-6 col-sm-6 col-xs-6 colDefined">
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

                <div class="col-md-9 col-sm-9 col-xs-9 colDefined" style="height: 31px;">
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
            <div class="zl_optiondiv" style="position: relative;">
                <textarea oninput="show1(event,50)" id="others" placeholder="特殊告知内容" type="text" /></textarea>
                <div class="signature_time timed">
                    <div class="zl_signature">
                        <span>医生签字：</span>
                        <div id="doctor_signatory"></div>
                        <input id="doctortime" type="text" class="consent_time inputheight2" readonly="readonly"
                            placeholder="请选择日期" />
                    </div>
                </div>
                <div>我已知悉医生告知的情况，仍同意种植牙方案。</div>
                <div class="signature_time timep">
                    <div class="zl_signature">
                        <span>患者签字：</span>
                        <div id="doctor_signatory"></div>
                        <input id="patienttime" type="text" class="consent_time inputheight2" readonly="readonly"
                            placeholder="请选择日期" />
                    </div>
                </div>
            </div>
            <!-- 按钮 -->
            <div class="btns" style="margin-top:68px;">
                <button id="consent_saveBtn" onclick="save()">保存</button>
                <button id="print_Btn" onclick="myPreviewAll()">打印本页内容</button>
            </div>
        </div>
</body>

<script language="javascript"  src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
<script type="text/javascript">
	var contextPath = "<%=contextPath%>";
	var status = "<%=status%>";
	var pageurl = '<%=contextPath%>/HUDH_FlowAct/findPatientInformation.act';
	var id;	//选中患者id
	var order_number;//选中患者order_number
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
	            $("#patient_time").attr("value", r.cztime);
	            $("#patient_num").text(r.usercode);
	            $("#patient_name").attr("value", r.username);
	            $("#patient_sex").attr("value", r.sex);
	            $("#patient_age").attr("value", r.age);
	            $("#patient_idNumber").attr("value", r.idcardno);
	            $("#patient_date").attr("value", r.birthday);
	            $("#patient_phone").attr("value", r.phonenumber1);
	            $("#patient_instancyName").attr("value", r.emergencycontact);
	            $("#patient_instancyPhone").attr("value", r.emergencyphone);
	            $("#patient_site").attr("value", r.provincename + r.cityname + r.townname + r.streetName);
	            if (status == '1') {
	                if (r.whether == '0') {
	                    $("#consultation_opinionA").attr("checked", true);
	                } else {
	                    $("#consultation_opinionB").attr("checked", true);
	                }
	                $("#others").val(r.content);
	                $("#doctortime").val(r.doctortime);
	                $("#patienttime").val(r.patienttime);
	                $("#consent_saveBtn").css("display", "none");//隐藏保存按钮
	                $("input").attr("disabled", "disabled");//查看信息的时候禁止在填写
	            }
	        }
	    });
	});
	
	
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
	        whether: whether
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
	
	function myPreviewAll() {
	    LODOP = getLodop();
	    LODOP.PRINT_INIT("检查及诊断");
	    var htmlStyle = "<style>#others{border-style: none;border-bottom: 1px solid #5b5b5b;}button{display:none;}span{font-size: 12px!important;}*{font-size: 12px;line-height: 16px;}.examine_continer input[type='checkbox']{width:12px !important;height:12px !important;margin-top: 10px !important;}.inputheight2{border: 1px solid transparent!important;}</style>";
	    var html = "<!DOCTYPE html>" + document.getElementsByTagName("html")[0].innerHTML + htmlStyle;
	    LODOP.ADD_PRINT_HTM(0, 0, "100%", "100%", html);
	    LODOP.PREVIEW();
	};		

</script>
</html>