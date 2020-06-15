<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/inc/classImport.jsp" %>
<%
    String contextPath = request.getContextPath();
    if (contextPath.equals("")) {
        contextPath = "/kqds";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8"/>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css"/>

    <script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
    <%-- <script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script> --%>
    <script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
    <script type="text/javascript"
            src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"
            charset="utf-8"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
</head>
<style id="styleA" type="text/css">
    * {
        margin: 0px;
        padding: 0px;
        font-size: 16px;
        line-height: 30px;
    }

    #content {
        font-weight: bold;
        margin-bottom: 20px;
        padding-top: 10px;
    }

    /* 输入框 */
    #content input[type="text"] {
        font-weight: bold;
    }

    /* 标题 */
    #content .bigtitle {
        display: block;
        width: 100%;
        text-align: center;
        font-size: 26px;
        line-height: 26px;
        margin: 45px auto 35px;
        letter-spacing: 1px;
        font-weight: bold;
    }

    /* 详细文字介绍 */
    #content .consent_text {
        width：100%;
        font-weight: normal;
        overflow: hidden;
    }

    /* 第二条：输入输入框 */
    #content .consent_textInput {
        /* border:1px solid red; */
    }

    #content .consent_textInput > font {
        /* display:block; */
        height: 50px;
        /* float:left; */
        font-weight: normal;
        line-height: 50px;
    }

    /* #content .consent_textInput>.text_input{
        display: block;
        float:left;
        width: 120px;
        height: 20px;
        border: 0px;
        border-radius: 0px;
        border-bottom: 1px solid black;
        text-align: center;
        margin: 20px 0px 10px;
    }  */
    #content .consent_textInput .textAuto_element {
        display: inline;
        /* float: left; */
        width: auto;
        font-weight: bold;
        /* min-width: 40px; */
        border-bottom: 1px solid black;
        padding: 0px 20px;
    }

    /* 第二条：种植牙位 */
    #content .consent_text .tooth_map {
        /* display: block;
        float:left;  */
        display: inline-block;
        width: 210px;
        height: 50px;
        /* margin-bottom:0px; */
        margin-bottom: -5px;
        margin-left: 5px;
    }

    #content .consent_text .tooth_map > li {
        width: 49%;
        height: 25px;
        float: left;
    }

    #content .consent_text .tooth_map > li:FIRST-CHILD {
        border-bottom: 1px solid black;
        border-right: 1px solid black;
    }

    #content .consent_text .tooth_map > li:FIRST-CHILD + li {
        border-bottom: 1px solid black;
    }

    #content .consent_text .tooth_map > li:FIRST-CHILD + li + li {
        border-right: 1px solid black;
    }

    /* 第二条：牙位输入框 */
    #content .consent_text .tooth_map > li > .tooth_input {
        display: block;
        width: 100%;
        height: 100%;
        padding: 0px;
        border: 0px;
        text-align: center;
    }

    /* 备注 */
    #content #consent_remark {
        width：100%;
        height: 120px;
        background-color: #ddd;
        margin: 5px 0px;
    }

    /* 备注 */
    #content #consent_remark > span {
        display: block;
        width: 7%;
        float: left;
        margin: 10px 0px 0px 15px;
    }

    /* 备注输入框 */
    #content #consent_remark > textarea {
        width: 90%;
        height: 90px;
        float: left;
        background-color: transparent;
        border: 0px;
        font-weight: normal;
    }

    /* 签名 */
    #content #consent_signature {
        overflow: hidden;
        /* 	 	height: 120px; */
        margin-top: 10px;
        margin-bottom: 20px;
    }

    #content #consent_signature > .signature_time {
        width: 40%;
        height: 60%;
        float: left;
        position: relative;
    }

    #content #consent_signature > .signature_time > .signature_box {
        width: 100%;
        height: 40px;
    }

    #content #consent_signature > .signature_time > .signature_box > span {
        font-weight: normal;
    }

    /* 时间选择框 */
    #content #consent_signature > .signature_time > input {
        width: 50%;
        position: absolute;
        right: 0px;
        bottom: 0px;
        text-align: center;
    }

    /* 按钮 */
    #content .btns {
        width: 100%;
        text-align: center;
        margin-top: 40px;
    }

    #content .btns button {
        background-color: #00A6C0;
        font-weight: normal;
        color: white;
        border: 0px;
        border-radius: 5px;
        padding: 0px 20px;
        letter-spacing: 1px;
    }

    #content .btns #consent_saveBtn {
        margin-right: 30px;
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

    h4 {
        font-weight: bold;
        text-indent: 2rem;
        line-height: 30px;
        margin-top: 0px;
        margin-bottom: 0px;
    }
</style>
<body style="padding: 0px 3%;">
<div id="content">
    <h2 class="bigtitle">种植术后注意事项 </h2>
    <h4>一、常规注意事项 </h4>
    <div class="consent_text">
        1、纱卷咬住30-60分钟后吐掉。术后不要嘬舔术区，口水咽下即可。
    </div>
    <div class="consent_text">
        2、手术当天温凉饮食，术后1周内吃流食或半流食，不吃热、硬及刺激性食物，忌烟酒。不用手术侧咬食物。
    </div>
    <div class="consent_text">
        3、术后当天不刷牙。术后第2天可以刷牙，注意保护伤口。进食后用清水漱口，再用漱口液漱口，每天3至4次，共用2周。请永久保持良好的口腔卫生。
    </div>
    <div class="consent_text">
        4、按医嘱服用抗菌药物，预防感染。
    </div>
    <div class="consent_text">
        5、术后按时复诊，需要定期拍摄X片观察种植体情况。
    </div>
    <div class="consent_text">
        6、原义齿需要在医生指导下使用。
    </div>
    <h4>二、种植术后可能会出现的情况 </h4>
    <div class="consent_text">
        1、疼痛：术后出现轻微疼痛属于正常现象，不能忍受时可按药物说明书服用止痛药：布洛芬，乐松；持续疼痛，使用止痛药物无法缓解，感到发烧请及时与医生联系。
    </div>
    <div class="consent_text">
        2、出血：24小时内会有少量渗血，48小时口内有血丝均属正常现象，可自行停止。如渗血较多，可局部使用云南白药止血粉末：上颌粉末倒在无菌纱布上，下颌将粉末倒在术区，对准伤口咬紧纱布1小时，不说话、不张口，可起到消炎止血作用。如果出血不止请及时就近就诊。
    </div>
    <div class="consent_text">
        3、肿胀：术后3至7天可能出现局部肿胀和淤青，48小时内冷敷，72小时后可热敷，症状会逐渐减轻，如肿胀加重提示感染可能，请及时与医生联系。
    </div>
    <div class="consent_text">
        4、感染：保持口腔卫生、遵医嘱使用漱口水及服用抗菌药。如局部有红、肿、热、痛请及时来院就诊。
    </div>
    <h4>三、术后特殊注意事项 </h4>
    <div class="consent_text">
        1、拔牙：术后不可吸允拔牙窝，避免患干槽症，愈合期间会有酸性分泌物的排异，无需担心，3个月左右拔牙窝会自行恢复。
    </div>
    <div class="consent_text">
        2、愈合基台可能由于咀嚼等其他原因导致松动，应及时就诊重新紧固，以防吞咽或误吸入气管。万一误吞请勿紧张，一般可随大便排出体外；误吸入气管请立即就近急诊。
    </div>
    <div class="consent_text">
        3、上颌窦提升、植骨术后： <br/>
        （1）行上颌窦提升术患者，术后注意避免手术侧受压，避免感冒，术后1个月内不可擤鼻涕、游泳，如打喷嚏应张大嘴，减少上颌窦压力。术后短期可能有鼻塞、头痛、鼻出血等症状，不必紧张，请及时与医生联系。 <br/>
        （2）行植骨术患者，不可按压植骨区域，如有白色颗粒状物体，无需担心，分泌物排异，咽下无妨可用食。
    </div>
    <h4>四、温馨提示 </h4>
    <div class="consent_text">
        1、纱布使用：双手清洗干净，无菌纱布对折，将云南白药止血粉末倒在纱布上，对准术区咬紧即可，根据空间决定纱布的使用量。
    </div>
    <div class="consent_text">
        2、冰袋使用：冰袋24h内对术区进行间断性的冰敷1分钟即可，不可以放在同一个位置过久，避免冻伤。
    </div>
    <div class="consent_text">
        3、如洗澡，泡脚，避免使用过热的热水，会加速血液循环使术区渗血，胀痛。
    </div>
    <h4>五、修复期间可能出现以下情况，为目前医学不可避免范围，但不必担心，请及时与医生联系，医生会给您妥善处理。 </h4>
    <div class="consent_text">
        1、缝线脱落。
    </div>
    <div class="consent_text">
        2、咬合咀嚼不佳、咬唇、咬舌等。
    </div>
    <div class="consent_text">
        3、基台、牙冠、愈合帽、种植体脱落。
    </div>
    <div class="consent_text">
        4、过渡牙桥断裂。
    </div>
    <!-- 备注 -->
    <!-- 		<div id="consent_remark"> -->
    <!-- 			<span>备注:</span> -->
    <!-- 			<textarea id="remarks" rows="" cols="" onblur="TextLengthCheck(this.id,200);"></textarea> -->
    <!-- 		</div> -->
    <!-- 		<div class="consent_text" style="font-weight: bold;text-align: right;"> -->
    <!-- 			以上情况我已知情并签字确认。 -->
    <!-- 		</div> -->
    <!-- 手术签名 -->
    <div id="consent_signature">
        <!-- 患者签名 -->
        <div class="signature_time">
            <div class="signature_box">
                <span id="patientSignature" style="margin-top: 8px;">患者签名:</span>
                <img id="patientimg" style="width:156px;height:auto;"/>
            </div>
            <input id="patienttime" type="text" class="consent_time" readonly="readonly" placeholder="请选择日期"/>
        </div>
        <!-- 医生签名 -->
        <div class="signature_time" style="float:right;">
            <div class="signature_box">
                <span id="doctorSignature" style="line-height: 50px;">医生签名:</span>
                <img id="img" style="width:156px;height:auto;"/>
            </div>
            <input id="doctortime" type="text" class="consent_time" readonly="readonly" placeholder="请选择日期"/>
        </div>
    </div>
    <!-- 按钮 -->
    <div class="btns">
        <button id="consent_saveBtn" onclick="save()">保存</button>
        <button id="print_Btn" onclick="myPreviewAll()">打印本页内容</button>
    </div>
</div>


</body>
<script language="javascript" src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
<script type="text/javascript">
    var signature = "";//医生签字
    var patientsignature = "";//患者签字
    var doctorstatus = true;
    var patientstatus = true;
    var contextPath = "<%=contextPath%>";
    var id = window.parent.consultSelectPatient.seqid;	//选中患者临床id
    var order_number = window.parent.consultSelectPatient.orderNumber;//选中患者order_number
    var usercode = window.parent.consultSelectPatient.usercode;//选中患者usercode
    var selectPatient = window.parent.consultSelectPatient;//选中患者
    $(function () {
        //console.log(JSON.stringify(selectPatient) + "-----------------selectPatient");
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

        initZzblInfor();
        // 2019/7/24 lutian 禁止页面拖拽
        document.ondragstart = function () {
            return false;
        };


    });
    var doctorSignature = document.getElementById("doctorSignature");
    doctorSignature.onclick = function () {
        if (doctorstatus) {
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

    function addSignature() {
        $("#img").css("display", "");
        $("#img").attr('src', signature);
    }

    var patientSignature = document.getElementById("patientSignature");
    patientSignature.onclick = function () {
        if (patientstatus) {
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

    function addPatientSignature() {
        $("#patientimg").css("display", "");
        $("#patientimg").attr('src', patientsignature);
        /* if(!doctorstatus&&patientstatus){
            updatePatientSignature();
        } */
    }

    /* 2019/7/22 lutian span输入文字长度校验方法   obj：元素id  textLength：限制文字长度 */
    function importTextLengthCheck(obj, textLength) {
        var objTextVal = $("#" + obj).text();
        if (objTextVal.length > textLength) {
            layer.open({
                title: '提示',
                content: '文字长度不能超过' + textLength + '字!',
                end: function () {
                    var inputNewVal = $("#" + obj).text();
                    $("#" + obj).text(inputNewVal.substring(0, textLength)).focus();
                }
            });
            return;
        }
    }

    /* 2019/7/22 lutian input文字长度校验方法   obj：元素id  textNum：限制文字长度 */
    function TextLengthCheck(obj, textNum) {
        var objTextVal = $("#" + obj).val();
        var checkTitleBefore = $("#" + obj).parent(".common_style").find("span").text();//根据父元素的选择器找到标题
        var checkTitle = checkTitleBefore.substring(0, checkTitleBefore.indexOf(":")); // 校验文字长长度的标题
        if (objTextVal.length > textNum) {
            $("#" + obj).attr("maxlength", textNum);
            //layer.alert(checkTitle+"文字长度不能超过"+textNum+"字!");
            layer.open({
                title: '提示',
                content: checkTitle + '文字长度不能超过' + textNum + '字!',
                end: function () {
                    var inputNewVal = $("#" + obj).val();
                    $("#" + obj).val(inputNewVal.substring(0, textNum)).focus();
                }
            });
            return;
        }
    }

    /* 页面赋值判断初始化 */
    function initZzblInfor() {
        //console.log(id+"--------------查询id");
        var url = contextPath + '/HUDH_MedicalRecordsAct/findFamiliar.act';
        $.ajax({
            url: url,
            type: "POST",
            dataType: "json",
            data: {
                id: id, //临床路径ID
            },
            success: function (result) {
                //console.log(JSON.stringify(result) + "--------------添加成功后查询数据");
                /* 判断是否已经填写过内容 */
                if (result.seqId) {
                    $("#consent_saveBtn").css("display", "none");//隐藏保存按钮
                    signature = result.doctorSignature;
                    if (signature != "") {
                        $("#img").attr('src', signature);
                        doctorstatus = false;
                    } else {
                        $("#img").attr('display', 'none');
                    }
                    $("#patienttime").val(result.patientTime);
                    patientsignature = result.patientSignature;
                    if (patientsignature != "") {
                        $("#patientimg").attr('src', patientsignature);
                        patientstatus = false;
                    } else {
                        $("#patientimg").attr('display', 'none');
                    }
                    $("#doctortime").val(result.doctorTime);
                    $("input").attr("disabled", "disabled").css("background", "transparent");//查看信息的时候禁止在填写
                }
            }
        });
    }

    /* 打印本页面方法 */
    function myPreviewAll() {
        LODOP = getLodop();
        LODOP.PRINT_INIT("人工种植牙知情同意书");
        LODOP.SET_PRINT_PAGESIZE(1, 2100, 2970, "A4");
        var htmlStyle = "<style>button{display:none;}*{font-size: 12px;line-height: 24px;}</style>";
        var html = "<!DOCTYPE html>" + document.getElementsByTagName("html")[0].innerHTML + htmlStyle;
        LODOP.ADD_PRINT_HTM(0, 10, "100%", "100%", html);
        LODOP.PREVIEW();
    };

    //获取url中的参数
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]);
        return null; //返回参数值
    }


    function save() {
        var PatientSignature = $("#PatientSignature").val();//患者签字
        var PatientTime = $("#patienttime").val();//患者签字时间
        var doctorSignature = $("#doctorSignature").val();//医生签字
        var doctorTime = $("#doctortime").val();//医生签字时间
        var createtime = new Date().Format("yyyy-MM-dd HH:mm:ss");
        var param = {
            id: id,
            orderNumber: order_number,
            userCode: usercode,
            patientSignature: patientsignature,
            patientTime: PatientTime,
            doctorSignature: signature,
            doctorTime: doctorTime
        };
        //console.log(JSON.stringify(param) + "---------保存参数");
        //return;
        var url = contextPath + '/HUDH_MedicalRecordsAct/insertFamiliar.act';
        $.ajax({
            url: url,
            type: "POST",
            dataType: "json",
            data: param,
            success: function (result) {
                console.log(JSON.stringify(result) + "----------------返回内容");
                layer.alert("保存成功！", {
                    end: function () {
                        var frameindex = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(frameindex); //再执行关闭
                    }
                });
            }
        });
    }

    /* 获取拼接牙位并校验 */
    function getValue(inputObj) {
        var inputBool = false;
        var toothArr = [];
        var toothString = "";
        //牙位输入框
        var inputVal = $("#" + inputObj).val();
        for (var i = 0; i < inputVal.length; i++) {
            if (inputVal[i] <= 8 && inputVal[i] >= 1) {
                if (toothArr.indexOf(inputVal[i]) < 0) {
                    toothArr.push(inputVal[i]);
                } else {
                    inputBool = true;
                }
            } else {
                inputBool = true;
            }
        }
        if (inputBool) {
            layer.open({
                title: '提示',
                content: '请输入正确牙位！(牙位值为1~8,且不能重复)',
                end: function () {
                    $("#" + inputObj).val("").focus();
                    toothString = "";
                }
            });
        }
        toothString = toothArr.join(",");
        //console.log(toothString+"------拼接字符串");//拼接字符串
        return toothString;
    };

    //日期格式化
    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月
            "d+": this.getDate(), //日
            "H+": this.getHours(), //时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //
            "S": this.getMilliseconds() //
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
</script>
</html>