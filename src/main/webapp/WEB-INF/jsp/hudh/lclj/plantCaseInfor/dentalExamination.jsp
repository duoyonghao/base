<%@page import="java.io.Console"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    String contextPath = request.getContextPath();
    if (contextPath.equals("")) {
        contextPath = "/kqds";
    }
    //当前诊断id
    String seqidFather = request.getParameter("seqidFather");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/yaweitu.css" />
    <!-- huizheng_info2.css需要重新配置路径暂时放在原文件处 -->

    <script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
    <%-- <script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script> --%>
    <script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
    <script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
</head>
<style type="text/css">
    dl{
        padding:0;
        margin:0;
    }
    body{
        font-size: 12px!important;
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
    ul, ol {
        list-style: none;
        padding:0
    }
    .container-fluid{
        width:100%;
        font-family:"微软雅黑";
    }
    .colDefined{
        padding:0px;
    }
    .row{
        margin: 0px;
    }
    ul, ol{
        margin:0px;
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
    /* 小标题 */
    .container-fluid .smalltitle{
        display: block;
        width:100%;
        font-size: 16px;
        line-height: 32px;
        letter-spacing: 1px;
        font-weight: bold;
        text-indent:1rem
    }
    .container-fluid .smalltitle>font{
        font-size:24px;
        margin-right: 10px;
        margin-left: 8px;
    }
    /* 基本信息 */
    .baseInfo{
        width:100%;
        border-bottom: 1px solid black;
        padding-left: 8px;
    }
    .baseInfo .patient{
        overflow: hidden;
    }
    /* 输入组合框 */
    .baseInfo .patient>.inputDiv{
        display:block;
        float:left;
        /*width: 235px;*/
        width: 250px;
        height: 25px;
    }
    .patient .inputDiv:nth-child(5){
        width: 100px!important;
    }
    .patient .inputDiv:nth-child(7){
        width: 310px!important;
    }

    .baseInfo .patient>.inputDiv>span{
        display: inline-block;
        font-size: 16px;
        line-height: 30px;
        height: 30px;
    }
    .baseInfo .patient>.inputDiv>font{
        font-size: 15px;
        color:#00a6c0;
        font-weight: bold;
    }
    /* 内容样式 */
    .table_width{
        width: 16.7%;
        text-align: center;
    }
    .table_width2{
        width:20%;
        text-align: center;
    }
    .table_width3{
        width:13.5%;
        text-align: center;
    }
    .content .colDefined .contentItem tr{
        height: 40px;
    }
    .content .colDefined .contentItem tr td input{
        width:20px;
        font-size: 14px;
        margin-left: 2px;
        outline:none;
    }
    .content .colDefined .contentItem tr td input[type="text"]{
        -webkit-appearance:none;
        padding: 0;
        margin: 0;
        text-align: center;
    }
    .border_bottom{
        width: 20px;
        border: none!important;
        border-bottom: 1px solid #aaa!important;
    }
    .problemitem{
        font-size:16px;
        font-weight: bold;
        text-align: center;
    }
    label {
        font-weight: normal;
        margin-bottom: 0px;
        margin-left:2%;
    }
    .btnsYa{
        float: left;
        /* 	width: 14%; */
        width:200px;
        text-align: center;
        margin-top: 2%;
    }
    .btnsYa .nocurrent_btn{
        margin-bottom:2px;
        width:100px;
        line-height: 34px;
        background-color: #00A6C0;
        font-weight: normal;
        color: white;
        border: 0px;
        border-radius: 5px;
        letter-spacing: 1px;
    }
    /* 牙位赋值框 */
    .toothNumInput {
        width: 90%!important;
        text-align: center!important;
        border: none!important;
    }
    .current_btn{
        margin-bottom:2px;
        background:#FF6100;
        width:100px;
        line-height: 34px;
        font-weight: normal;
        color: white;
        border: 0px;
        border-radius: 5px;
        letter-spacing: 1px;
    }
    /* 保存按钮 */
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
    .site{
        margin-left: 75%;
        width: 100px;
        text-align: center;
    }
    .placeholder:empty:before{
        /*     content: attr(placeholder);  */
        content: '对应牙位';
        color:#ccc;
        line-height: 35px
    }
    .placeholder:focus:before{
        content:none;
    }
    .ulstayle{
        width: 80%;
        border: 1px solid #aaa;
        /*float:left;*/
        border-radius: 5px;
        margin: 1%;
        display: flex;
        flex-direction:row;
    }
    /*签字*/
    .container-fluid .consent_signature {
        overflow: hidden;
        margin-top: 10px;
        margin-bottom: 20px;
    }
    .container-fluid .consent_signature>.signature_time {
        width: 40%;
        position: relative;
    }
    .container-fluid .consent_signature>.signature_time>.signature_box {
        width: 100%;
    }
    .container-fluid .consent_signature>.signature_time>input {
        width: 40%;
        position: absolute;
        right: 0px;
        bottom: 0px;
        text-align: center;
        border: 1px solid #ccc;
    }
    .container-fluid .consent_signature input[type="text"] {
        font-weight: bold;
        color: #00a6c0;
        line-height: 28px;
        border-radius: 3px;
        text-align: center;
    }
    @page{
        size:240mm 325mm;
        margin: 0 auto;
    }
    @media print {
        .content .colDefined .contentItem tr{
            height:20px;
        }
        .blockToothBox{
            display:block!important;
        }
        .patient .inputDiv:nth-child(4){
            width: 190px!important;
        }
        .patient .inputDiv:nth-child(9){
            width: 190px!important;
        }
        #logoImg{
            position: absolute;
            top: 15px!important;
            width: 150px!important;
        }
        .bigtitle{
            font-size: 22px;
            margin: 0px auto 10px !important;
        }
        .div_with{
            width:270px!important;
            margin: auto;
        }
        .div_with2{
            width:170px!important;
            margin: auto;
        }
        .div_with3{
            width:500px!important;
            margin: auto;
        }
        .problemitem{
            font-size:10px;
            font-weight:normal;
        }
        .tooth_map{
            width: 98%!important;
            margin-left: 5px!important;
        }
        .table_width3{
            width:12%;
        }
        #other{
            font-size:10px!important;
        }
    }
    /*textarea牙位输入*/
    textarea{
        resize:none;
        border: none;
        border-radius: 5px;
        overflow-y: hidden;
        height: 35px;
        width: 100%;
        text-align: center;
        outline:none;
    }
    /*input牙位输入*/
    .tooth_map {
        float: left;
        display: inline-block;
        width: 90%;
        margin: 3px;
    }
    .tooth_map>li {
        width: 49%;
        height: 17.5px;
        float: left;
    }
    .tooth_map>li>input {
        border: 0px;
        width: 90%;
        margin-left: 5%;
        height: 25px;
        text-align: center;
    }
    .tooth_map>li:FIRST-CHILD {
        border-bottom: 1px solid #2d2b2b;
        border-right: 1px solid #2d2b2b;
    }
    .tooth_map>li:FIRST-CHILD+li {
        border-bottom: 1px solid #2d2b2b;
    }
    .tooth_map>li:FIRST-CHILD+li+li {
        border-right: 1px solid #2d2b2b;
    }
    .tooth_map input{
        width:100%!important;
        height:98%!important;
    }
    .zl_optiondiv{
        display: inline-block;
        width: auto;
        min-width: 18%;
    }
    .zl_optiondiv>label{
        font-weight: bold;
    }
</style>
<body>
<!--startprint-->
<div  class="container-fluid">
    <!-- 标题 -->
    <div class="row">
        <div class="col-md-12 col-sm-12" style="position: relative;padding: 0;">
            <img id="logoImg" src="<%=contextPath%>/static/image/kqdsFront/jiagong/logoName.png">
            <span class="bigtitle">口腔专科检查</span>
        </div>
    </div>
    <!-- 基本信息 -->
    <div class="row"  style="border-top: 1px solid #000;">
        <div class="col-md-12 col-sm-12 colDefined">
            <span class="smalltitle"><font>●</font>基本信息</span>
        </div>
    </div>
    <div class="row baseInfo">
        <div class="col-md-12 col-sm-12 colDefined">
            <!-- 患者基本信息 -->
            <div class="patient" style="width:90%;float:left;">
                <div class="inputDiv">
                    <span>首诊时间：</span><font type="text" id="patient_first_diagnose"></font>
                </div>
                <div class="inputDiv">
                    <span>患者编号：</span><font type="text" id="patient_num"></font>
                </div>
                <div class="inputDiv" style="clear:left;">
                    <span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span><font type="text" id="patient_name"></font>
                </div>
                <div class="inputDiv">
                    <span>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span><font type="text" id="patient_sex"></font>
                </div>
                <div class="inputDiv" style="">
                    <span>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄：</span><font type="text" id="patient_age"></font>
                </div>
                <div class="inputDiv" style="clear:left;">
                    <span>证件号码：</span><font type="text" id="patient_ID"></font>
                </div>
                <div class="inputDiv">
                    <%--                    <span>出生年月：</span><font type="text" id="patient_bone"></font>--%>
                    <span>现居住地址：</span><font type="text" id="patient_address"></font>
                </div>
                <div class="inputDiv" style="clear:left;">
                    <span>联系电话：</span><font type="text" id="patient_tel"></font>
                </div>
                <div class="inputDiv" >
                    <span>紧急联系人：</span><font type="text" id="patient_emergency_contact"></font>
                </div>
                <div class="inputDiv">
                    <span>紧急联系人电话：</span><font type="text" id="emergency_contact_tel"></font>
                </div>
            </div>
            <div class="patientHeader" style="width:10%;float:left;">
                <img src="<%=contextPath%>/static/image/kqdsFront/jiagong/headImg.jpg">
                <input style="display:none;" type="file" />
            </div>
        </div>
    </div>
    <!-- 口腔专科检查-->
    <div class="row">
        <div class="col-md-12 col-sm-12 colDefined">
            <span class="smalltitle"><font>●</font>口腔专科检查</span>
        </div>
    </div>
    <div class="row content">
        <div class="col-md-12 col-sm-12 colDefined">
            <table id="oralSpecialtyExamination" class="contentItem" border="1" width="100%">
                <!-- 主诉content -->
                <tr>
                    <td colspan="1" class="problemitem"><span class="">张口度</span></td>
                    <td colspan="2">
                        <label><input class="" type="radio" name="ismouthopening" value="0" onclick="choose(this.name);"/><span class=" ">正常</span></label>
                        <label><input class="" type="radio" name="ismouthopening" value="1" onclick="choose(this.name);"/><span class=" ">受限</span></label><input class="border_bottom ismouthopeninginput" id="mouthopening" type="text" onblur="TextLengthCheck(this.id,2);" style="cursor: no-drop;" disabled="disabled"/>mm
                    </td>
                    <td colspan="3" class="problemitem"><span class="">颞下颌关节</span></td>
                    <td colspan="5" class="chooseCheckbox" id="arthrosisCheckbox">
                        <label><input class="usual" type="checkbox" name="arthrosis" value="0" onclick="chooseUsual(this,this.name);"/><span class=" ">正常</span></label>
                        <label><input class="unusual" type="checkbox" name="arthrosis" value="1" onclick="chooseUnusual(this.name);"/><span class=" ">弹响</span></label>
                        <label><input class="unusual" type="checkbox" name="arthrosis" value="2" onclick="chooseUnusual(this.name);"/><span class=" ">疼痛</span></label>
                        <label><input class="unusual" type="checkbox" name="arthrosis" value="3" onclick="chooseUnusual(this.name);"/><span class=" ">习惯性脱位</span></label>
                        <label><input class="unusual" type="checkbox" name="arthrosis" value="4" onclick="chooseUnusual(this.name);"/><span class=" ">绞锁</span></label>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" class="problemitem"><span class="">咬合关系</span></td>
                    <td colspan="10" class="chooseCheckbox" id="occludingrelationCheckbox">
                        <label><input class="usual" type="checkbox" name="occludingrelation" value="0" onclick="chooseUsual(this,this.name);"/><span class=" ">正常颌 </span></label>
                        <label><input class="unusual" type="checkbox" name="occludingrelation" value="1" onclick="chooseUnusual(this.name);"/><span class=" ">深覆颌 </span></label>
                        <label><input class="unusual" type="checkbox" name="occludingrelation" value="2" onclick="chooseUnusual(this.name);"/><span class=" ">深覆盖 </span></label>
                        <label><input class="unusual" type="checkbox" name="occludingrelation" value="3" onclick="chooseUnusual(this.name);"/><span class=" ">对刃颌 </span></label>
                        <label><input class="unusual" type="checkbox" name="occludingrelation" value="4" onclick="chooseUnusual(this.name);"/><span class=" ">反颌 </span></label>
                        <label><input class="unusual" type="checkbox" name="occludingrelation" value="5" onclick="chooseUnusual(this.name);"/><span class=" ">开颌 </span></label>
                    </td>
                </tr>
                <tr style="display: none">
                    <td colspan="2" class="problemitem"><span class="">纵&nbsp&nbsp&nbsp&nbsp曲线</span></td>
                    <td colspan="4">
                        <label><input class="" type="radio" name="verticalcurve" value="0"/><span class=" ">正常</span></label>
                        <label><input class="" type="radio" name="verticalcurve" value="1"/><span class=" ">异常</span></label>
                    </td>
                    <td colspan="2" class="problemitem"><span class="">横&nbsp&nbsp&nbsp&nbsp曲线</span></td>
                    <td colspan="4">
                        <label><input class="" type="radio" name="horizontalcurve" value="正常"/><span class=" ">正常</span></label>
                        <label><input class="" type="radio" name="horizontalcurve" value="异常"/><span class=" ">异常</span></label>
                    </td>
                </tr>
                <tr>
                    <td  class="problemitem"><span class="">颌间距离</span></td>
                    <td  style="text-align: center;"><input class="border_bottom" id="distancebetween" type="text" onblur="TextLengthCheck(this.id,2);"/>mm</td>
                    <td colspan="3" class="problemitem"><span class="">唇线</span><span class="demand">(全口/半口/美学区填写)</span></td>
                    <td colspan="3">
                        <label><input class="" type="radio" name="labialline"  value="高"/><span class=" ">高</span></label>
                        <label><input class="" type="radio" name="labialline" value="中"/><span class=" ">中</span></label>
                        <label><input class="" type="radio" name="labialline" value="低"/><span class=" ">低</span></label>
                    </td>
                    <td class="problemitem"><span class="">其他:</span><input id="others" class="border_bottom" onblur="TextLengthCheck(this.id,15);" style="width: 80%"/></td>
                </tr>
                <tr>
                    <td class="problemitem" colspan="2"><span class="">口腔治疗经历</span></td>
                    <td colspan="10" id="undergoCheckbox">
                        <label><input class="" type="checkbox" name="undergo" value="1 "/><span class=" ">补牙 </span></label>
                        <label><input class="" type="checkbox" name="undergo" value="2"/><span class=" ">牙周治疗 </span></label>
                        <label><input class="" type="checkbox" name="undergo" value="3 "/><span class=" ">种植治疗 </span></label>
                        <label><input class="" type="checkbox" name="undergo" value="4"/><span class=" ">部分活动义齿</span></label>
                        <label><input class="" type="checkbox" name="undergo" value="5"/><span class=" ">全口义齿</span></label>
                        <label><input class="" type="checkbox" name="undergo" value="6"/><span class=" ">固定义齿 </span></label>
                    </td>
                </tr>
                <tr>
                    <td class="problemitem" colspan="2"><span class="">牙周情况</span></td>
                    <td colspan="10" class="chooseCheckbox" id="periodontalconditionCheckbox">
                        <label><input class="usual" type="checkbox" name="periodontalcondition" value="1" onclick="chooseUsual(this,this.name);"/><span class=" ">良好 </span></label>
                        <label><input class="unusual" type="checkbox" name="periodontalcondition" value="2" onclick="chooseUnusual(this.name);chooseUnusualRadio(this,this.name)"/><span class=" ">牙周病</label>（
                        <label><input class="level" type="radio" name="periodontalcondition" value="重度" disabled/><span class=" ">重度</span></label>
                        <label><input class="level" type="radio" name="periodontalcondition" value="中度" disabled/><span class=" ">中度</span></label>
                        <label><input class="level" type="radio" name="periodontalcondition" value="轻度" disabled/><span class=" ">轻度</span></label>
                        ）  </span>
                        <label><input class="unusual" type="checkbox" name="periodontalcondition" value="3" onclick="chooseUnusual(this.name);"/><span class=" ">牙龈炎</span></label>
                        <label><input class="unusual" type="checkbox" name="periodontalcondition" value="4" onclick="chooseUnusual(this.name);"/><span class=" ">植体周围粘膜炎</span></label>
                    </td>
                </tr>
                <tr>
                    <td class="problemitem" colspan="2"><span class="">粘膜情况</span></td>
                    <td colspan="10" class="chooseCheckbox" id="mucosalsituationCheckbox">
                        <label><input class="usual" type="checkbox" name="mucosalsituation" value="1" onclick="chooseUsual(this,this.name);"/><span class=" ">正常</span></label>
                        <label><input class="unusual" type="checkbox" name="mucosalsituation" value="2" onclick="chooseUnusual(this.name);"/><span class=" ">溃疡   </span></label>
                        <label><input class="unusual" type="checkbox" name="mucosalsituation" value="3" onclick="chooseUnusual(this.name);"/><span class=" ">白斑 </span></label>
                        <label><input class="unusual" type="checkbox" name="mucosalsituation" value="4" onclick="chooseUnusual(this.name);"/><span class=" ">扁平苔藓</span></label>
                        <label><input class="unusual" type="checkbox" name="mucosalsituation" value="5" onclick="chooseUnusual(this.name);"/><span class=" ">福代斯斑 </span></label>
                    </td>
                </tr>
                <tr  class="toothLooseNextBox">
                    <td class="" colspan="12"><span class="problemitem" style="margin-left:1% ">牙松动（不作为诊断）</span></td>
                </tr>
                <tr>
                    <td  colspan="" class="problemitem table_width"><span class="" id="ysd1">Ⅰ°</span></td>
                    <td  colspan="4" class="table_width">
                        <div class="div_with" sid="ysd1">
                            <textarea name="ysd1"  cols="2" placeholder="对应牙位"></textarea>
                            <ul class="tooth_map" style="margin-left: 30px;">
                                <li>
                                    <input id="ysd1leftup" class="tooth_input" type="text">
                                </li>
                                <li>
                                    <input id="ysd1rightup" class="tooth_input " type="text">
                                </li>
                                <li>
                                    <input id="ysd1leftdown" class="tooth_input" type="text">
                                </li>
                                <li>
                                    <input id="ysd1rightdown" class="tooth_input" type="text">
                                </li>
                            </ul>
                        </div>
                    </td>
                    <td  colspan="" class="problemitem table_width"><span class="" id="ysd2">Ⅱ°</span></td>
                    <td  colspan="4" class="table_width">
                        <div class="div_with" sid="ysd2">
                            <textarea name="ysd2"  cols="2" placeholder="对应牙位"></textarea>
                            <ul class="tooth_map" style="margin-left: 30px;">
                                <li>
                                    <input id="ysd2leftup" class="tooth_input" type="text">
                                </li>
                                <li>
                                    <input id="ysd2rightup" class="tooth_input" type="text">
                                </li>
                                <li>
                                    <input id="ysd2leftdown" class="tooth_input" type="text">
                                </li>
                                <li>
                                    <input id="ysd2rightdown" class="tooth_input" type="text">
                                </li>
                            </ul>
                        </div>
                    </td>

                </tr>
                <tr>
                    <td  colspan="" class="problemitem table_width"><span class="" id="ysd3">Ⅲ°</span></td>
                    <td  colspan="4" class="table_width">
                        <div class="div_with placeholder" sid="ysd3">
                            <textarea name="ysd3"  cols="2" placeholder="对应牙位"></textarea>
                            <ul class="tooth_map" style="margin-left: 30px;">
                                <li>
                                    <input id="ysd3leftup" class="tooth_input" type="text">
                                </li>
                                <li>
                                    <input id="ysd3rightup" class="tooth_input" type="text">
                                </li>
                                <li>
                                    <input id="ysd3leftdown" class="tooth_input" type="text">
                                </li>
                                <li>
                                    <input id="ysd3rightdown" class="tooth_input" type="text">
                                </li>
                            </ul>
                        </div>
                    </td>
                    <td  colspan="" class="table_width"></td>
                    <td  colspan="4" class="table_width"></td>
                </tr>
                <tr>
                    <td  class="problemitem"><span style="margin-left:1% ">牙缺失（不作为诊断）</span></td>
                    <td  colspan="8" class="table_width">
                        <div class="div_with3" sid="ysd">
                            <textarea id="toothDeficiency" name="toothDeficiency"  cols="1" placeholder="对应牙位"></textarea>
                            <ul class="tooth_map" style="margin-left: 30px;">
                                <li>
                                    <input id="ysdleftup" class="tooth_input" type="text">
                                </li>
                                <li>
                                    <input id="ysdrightup" class="tooth_input" type="text">
                                </li>
                                <li>
                                    <input id="ysdleftdown" class="tooth_input" type="text">
                                </li>
                                <li>
                                    <input id="ysdrightdown" class="tooth_input" type="text">
                                </li>
                            </ul>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div class="row toothBox blockToothBox" id="conditionToothBox" style="display:block;min-width:700px;width: 100%;overflow: hidden;margin:5px 0 0 10%;">
        <ul class="upYa" >
        </ul>
        <div class="line" style="left: -20%;width: 30%;">
            <span class="left">右</span>
            <span class="right">左</span>
        </div>
        <ul class="downYa">
        </ul>
    </div>
    <!-- 整体口腔情况明细-->
    <div class="row content">
        <div class="col-md-12 col-sm-12 colDefined">
            <span class="smalltitle"><font>●</font>整体口腔情况明细</span>
            <table id="toothConditionBox" class="contentItem" border="1" width="100%">
            </table>
        </div>
        <!-- 影像学检查 -->
        <div class="row content">
            <div class="col-md-12 col-sm-12 colDefined">
                <span class="smalltitle"><font>●</font>影像学检查</span>
                <table id="imageExaminationBox" class="contentItem contentItem2" border="1" width="100%">
                    <tr>
                        <td class="table_width2">
                            <div class='div_with2 placeholder' sid="ycjjd">
                                <textarea name='ycjjd'  cols='2' placeholder='对应牙位'></textarea>
                                <ul class="tooth_map" style="margin-left: 30px;">
                                    <li>
                                        <input id="ycjjdleftup" class="tooth_input" type="text">
                                    </li>
                                    <li>
                                        <input id="ycjjdrightup" class="tooth_input" type="text">
                                    </li>
                                    <li>
                                        <input id="ycjjdleftdown" class="tooth_input" type="text">
                                    </li>
                                    <li>
                                        <input id="ycjjdrightdown" class="tooth_input" type="text">
                                    </li>
                                </ul>
                            </div>
                        </td>
                        <td colspan="3" class="problemitem table_width3">
                            <span class="" id="ycjjd">牙槽嵴顶距上颌窦底 </span><input class="border_bottom" id="saprodontia" type="text" onblur="TextLengthCheck(this.id,2);"/>mm
                        </td>
                        <td>
                            <div class='div_with2 placeholder' sid="jbd">
                                <textarea name='jbd'  cols='2' placeholder='对应牙位'></textarea>
                                <ul class="tooth_map" style="margin-left: 30px;">
                                    <li>
                                        <input id="jbdleftup" class="tooth_input" type="text">
                                    </li>
                                    <li>
                                        <input id="jbdrightup" class="tooth_input" type="text">
                                    </li>
                                    <li>
                                        <input id="jbdleftdown" class="tooth_input" type="text">
                                    </li>
                                    <li>
                                        <input id="jbdrightdown" class="tooth_input" type="text">
                                    </li>
                                </ul>
                            </div>
                        </td>
                        <td colspan="3" class="problemitem table_width3">
                            <span class="" id="jbd">距鼻底</span><input class="border_bottom" id="basisnasi" type="text" onblur="TextLengthCheck(this.id,2);"/>mm
                        </td>
                    </tr>
                    <tr>
                        <td class="table_width2">
                                <div class='div_with2 placeholder' sid="ycjkd">
                                    <textarea name='ycjkd'  cols='2' placeholder='对应牙位'></textarea>
                                    <ul class="tooth_map" style="margin-left: 30px;">
                                        <li>
                                            <input id="ycjkdleftup" class="tooth_input" type="text">
                                        </li>
                                        <li>
                                            <input id="ycjkdrightup" class="tooth_input" type="text">
                                        </li>
                                        <li>
                                            <input id="ycjkdleftdown" class="tooth_input" type="text">
                                        </li>
                                        <li>
                                            <input id="ycjkdrightdown" class="tooth_input" type="text">
                                        </li>
                                    </ul>
                                </div>
                        </td>
                        <td colspan="3" class="problemitem table_width3">
                            <span class="" id="ycjkd">牙槽嵴宽度</span><input class="border_bottom" id="alveolarcrest" type="text" onblur="TextLengthCheck(this.id,2);"/>mm
                        </td>
                        <td class="table_width2">
                            <div class='div_with2 placeholder' sid="ycsjg">
                                <textarea name='ycsjg'  cols='2' placeholder='对应牙位'></textarea>
                                <ul class="tooth_map" style="margin-left: 30px;">
                                    <li>
                                        <input id="ycsjgleftup" class="tooth_input" type="text">
                                    </li>
                                    <li>
                                        <input id="ycsjgrightup" class="tooth_input" type="text">
                                    </li>
                                    <li>
                                        <input id="ycsjgleftdown" class="tooth_input" type="text">
                                    </li>
                                    <li>
                                        <input id="ycsjgrightdown" class="tooth_input" type="text">
                                    </li>
                                </ul>
                            </div>
                        </td>
                        <td colspan="2" class="problemitem table_width3">
                            <span class="" id="ycsjg">距下牙槽神经管 </span><input class="border_bottom" id="residualcrown" type="text" onblur="TextLengthCheck(this.id,2);"/>mm
                        </td>
                    </tr>
            </table>
        </div>
    </div>
        <!-- 诊断 -->
        <div class="row content">
    <div class="col-md-12 col-sm-12 colDefined">
        <span class="smalltitle"><font>●</font>诊断</span>
        <table id="medicalCertificateBox" class="contentItem contentItem2" border="1" width="100%">
            <tr id="">
<%--                <td style='text-align:center'>--%>
<%--                    <div class='div_with placeholder' sid="yqs69">--%>
<%--                        <textarea name="yqs69"  cols='2' placeholder='对应牙位'></textarea>--%>
<%--                        <ul class='tooth_map' style='margin-left: 30px;'>--%>
<%--                            <li><input id='yqs69leftup' class='tooth_input' type='text'/></li>--%>
<%--                            <li><input id='yqs69rightup' class='tooth_input' type='text'/></li>--%>
<%--                            <li><input id='yqs69leftdown' class='tooth_input' type='text'/></li>--%>
<%--                            <li><input id='yqs69rightdown' class='tooth_input' type='text'/></li>--%>
<%--                        </ul>--%>
<%--                    </div>--%>
<%--                </td>--%>
                <td class='problemitem table_width2' id="diagnosisCheckbox">
<%--                    <span id='yqs69' class='hidden'>诊断</span>--%>
                    <div class="zl_optiondiv">
                        <input name="diagnosis" id="diagnosisA" value="0" type="checkbox"/>
                        <label for="diagnosisA">牙列缺失</label>
                    </div>
                    <div class="zl_optiondiv">
                        <input name="diagnosis" id="diagnosisB" value="1" type="checkbox"/>
                        <label for="diagnosisB">牙列缺损</label>
                    </div>
                    <div class="zl_optiondiv">
                        <input name="diagnosis" id="diagnosisC" value="2" type="checkbox"/>
                        <label for="diagnosisC">牙体缺损</label>
                    </div>
                    <div class="zl_optiondiv" style="min-width:35%;">
                        <input name="diagnosis" id="diagnosisD" value="3" type="checkbox"/>
                        <label for="diagnosisD">其他</label>
                        <input style='width:60%;font-size: 16px;text-align:center;border:none;border-bottom:1px solid black;' id='other' value="" onblur='TextLengthCheck(this.id,20);'>
                    </div>
                </td>
                <%--<td style='text-align:center'>
                    <div class='div_with placeholder' sid="qt213">
                        <textarea name="qt213"  cols='2' placeholder='对应牙位'></textarea>
                        <ul class='tooth_map' style='margin-left: 30px;'>
                            <li><input id='qt213leftup' class='tooth_input' type='text'/></li>
                            <li><input id='qt213rightup' class='tooth_input' type='text'/></li>
                            <li><input id='qt213leftdown' class='tooth_input' type='text'/></li>
                            <li><input id='qt213rightdown' class='tooth_input' type='text'/></li>
                        </ul>
                    </div>
                </td>
                <td class='problemitem table_width2'>
                    <span id='qt213' class=''>其他</span>
                    <input style='width:60%;font-size: 16px;text-align:center;border:none' id='other' value="" onblur='TextLengthCheck(this.id,6);'>
                </td>--%>
            </tr>
        </table>
    </div>
</div>
        <!-- 手术签名 -->
        <div class="consent_signature">
    <!-- 患者签名 -->
    <div class="signature_time" style="float: left;display: none">
        <div class="signature_box">
            <span id="patientSignature" style="margin-top: 8px;line-height: 50px;font-size: 16px">患者签名:</span>
            <img id="patientimg" style="width:156px;height:auto;"/>
        </div>
        <input id="patienttime" type="text" class="patienttime consent_time inputhidden" readonly="readonly" placeholder="请选择日期"/>
    </div>
    <!-- 医生签名 -->
    <div class="signature_time" style="float: right;">
        <div class="signature_box">
            <span id="doctorSignature" style="line-height: 50px;font-size: 16px">种植医生签名:</span>
            <img id="doctorimg" style="width:156px;height:auto;"/>
        </div>
        <input id="doctortime" type="text" class="doctortime consent_time inputhidden" readonly="readonly" placeholder="请选择日期"/>
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
<%--</body>--%>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script language="javascript"  src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
<script type="text/javascript">
    var signature="";
    var patientsignature="";
    var doctorstatus=true;
    var patientstatus=true;
    var contextPath = "<%=contextPath%>";
    var conditionData;
    var certificateData;
    var usercode;
    var idlclj;
    var order_number;
    var updataid="";
    var menuid=window.parent.menuid;//左侧菜单id
    var seqidFather = "<%=seqidFather%>";
    var userAgent = navigator.userAgent;
    var signatureWidth='70%';
    var signatureHeight='65%';
    var form;
    $(function(){
        if (userAgent.match(/mobile/i)) {
            var mql = window.matchMedia('(orientation: portrait)');
            function onMatchMediaChange(mql) {
                if (mql.matches) {
                    //竖屏
                    signatureWidth='98%';
                    signatureHeight='50%';
                } else {
                    //横屏
                    signatureWidth='98%';
                    signatureHeight='73%';
                }
            }
            // 输出当前屏幕模式
            onMatchMediaChange(mql);

            // 监听屏幕模式变化
            mql.addListener(onMatchMediaChange);

        }
        if(window.parent.consultSelectPatient){
            idlclj= window.parent.consultSelectPatient.seqid;
            order_number= window.parent.consultSelectPatient.orderNumber;
            usercode = window.parent.consultSelectPatient.usercode;
            form=window.parent.getNewForm();
        }else{
            idlclj= window.parent.patientObj.id;
            order_number= window.parent.patientObj.orderNumber;
            usercode = window.parent.patientObj.blcode;
            form=window.parent.parent.getNewForm();
        }
        getUser(usercode);//获取患者信息并赋值
        gettionData();//获取各板块问题详情
        initBlockToothMap("conditionToothBox");
        pro("toothConditionBox",conditionData);
        //pro("medicalCertificateBox",certificateData);
       // $("textarea").addClass("hidden");
        $(".div_with .tooth_map").addClass("hidden");
        $(".div_with2 .tooth_map").addClass("hidden");
        $(".div_with3 .tooth_map").addClass("hidden");
        initData();//数据初始化
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
        document.ondragstart = function() {
            return false;
        };
    });
    function getUser(usercode){
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
                $("#patient_first_diagnose").text(r.cztime);
                $("#patient_num").text(r.usercode);
                $("#patient_num").attr("seqId", r.seqId);
                $("#patient_name").text(r.username);
                $("#patient_sex").text(r.sex);
                $("#patient_age").text(r.age);
                $("#patient_ID").text(r.idcardno);
                $("#patient_bone").text(r.birthday);
                $("#patient_tel").text(r.phonenumber1);
                $("#patient_emergency_contact").text(r.emergencycontact);
                $("#emergency_contact_tel").text(r.emergencyphone);
                $("#patient_address").text(r.provincename + r.cityname + r.townname + r.streetName);
            }
        });
    }
    // 	input多选与单选校验
    //choose start=====================================================================
    // 	表单单选填input值
    function choose(name){
        var checkVal=$("input[name="+name+"]:checked").val();
        if(checkVal==0){
            $("."+name+"input").val("").attr("disabled","disabled").css("cursor","no-drop");
        }else if(checkVal==1){
            $("."+name+"input").removeAttr("disabled").css("background-color","transparent").css("cursor","auto");
        }
    }
    // 表单正常单选
    function chooseUsual(thi,name){
        if($(thi).is(':checked')){
            $("#"+name+"Checkbox").find('.unusual').prop("checked",false).attr("disabled","disabled").css("cursor","no-drop");
        }else{
            $("#"+name+"Checkbox").find('.unusual').removeAttr("disabled").css("cursor","auto");
        }
    }
    // 表单异常多选
    function chooseUnusual(name){
        var checked = $("#"+name+"Checkbox").find(".unusual").filter(":checked").length;
        if(checked>0){
            $("#"+name+"Checkbox").find(".usual").prop("checked",false).attr("disabled","disabled").css("cursor","no-drop");;
        }else{
            $("#"+name+"Checkbox").find(".usual").removeAttr("disabled").css("cursor","auto");
        }
    }
    // 表单多选套选单选
    function chooseUnusualRadio(thi,name){
        if($(thi).is(':checked')){
            $("#"+name+"Checkbox").find(".level").removeAttr("disabled","disabled").css("cursor","auto");
        }else{
            $("#"+name+"Checkbox").find(".level").prop("checked",false).attr("disabled","disabled").css("cursor","no-drop");
        }
    }
    //choose end=======================================================================
    // 	输入框校验start
    /* 文字长度校验方法   obj：元素id  textNum：限制文字长度 */
    function TextLengthCheck(obj,textNum){
        var objTextVal=$("#"+obj).val();
        if(objTextVal.length>textNum){
            $("#"+obj).attr("maxlength",textNum);
            layer.open({
                title: '提示',
                content: '输入不能超过'+textNum+'位数！',
                end:function(){
                    var inputNewVal=$("#"+obj).val();
                    $("#"+obj).val(inputNewVal.substring(0,textNum)).focus();
                }
            });
            return;
        }
    }
    // 		end
    // 	各项目问题数据
    function gettionData(){
        var a=1;
        var url = contextPath + "/YZDictAct/getDiseaseByCode.act?id=bqfl67&code=zdqk594";
        $.axse(url, null,
            function(data) {
            //console.log(JSON.stringify(data)+"-----------各项目问题数据");
                // a=2;
                conditionData=data.conditionData;
                certificateData=data.certificateData;
            },
            function() {
                layer.alert('查询出错！' );
            });
    }
    // 初始化各项目问题内容及布局
    function pro(obj,data){
        for(var i=0;i<(data.length)/2;i++){
            var prohtml="";
            prohtml +="<tr id="+i+">";
            prohtml += "<td style='text-align:center'></td>";
            prohtml += "<td class='problemitem table_width2'></td>";
            prohtml += "<td style='text-align:center'></td>";
            prohtml += "<td class='problemitem table_width2'></td>";
            prohtml +="</tr>";
            $("#"+obj).append(prohtml);
        }

        if((data.length)%2 == 0){
            $("#"+obj).find("tbody tr").find("td:odd").each(function(i,obj){
                $(this).html("<span class='' id="+data[i].dictCode+">"+data[i].dictName+"</span>");
                if(data[i].dictCode=="qt213"){
                    $(this).html("<span id='qt213' class='hidden'>其他</span><input style='width:60%;font-size: 16px;text-align:center;border:none' id='other' value="+data[i].dictName+" onblur='TextLengthCheck(this.id,6);'>");
                }
            })
            $("#"+obj).find("tbody tr").find("td:even").each(function(i,obj){
                $(this).html("<div class='div_with placeholder' sid="+data[i].dictCode+"><textarea name="+data[i].dictCode+"  cols='2' placeholder='对应牙位'></textarea><ul class='tooth_map' style='margin-left: 30px;'><li><input id='"+data[i].dictCode+"leftup' class='tooth_input' type='text'/></li><li><input id='"+data[i].dictCode+"rightup' class='tooth_input' type='text'/></li><li><input id='"+data[i].dictCode+"leftdown' class='tooth_input' type='text'/></li><li><input id='"+data[i].dictCode+"rightdown' class='tooth_input' type='text'/></li></ul></div>");
            })
        }else{
            $("#"+obj).find("tbody tr").find("td:odd").not("td:last").each(function(i,obj){
                $(this).html("<span class='' id="+data[i].dictCode+">"+data[i].dictName+"</span>");
                if(data[i].dictCode=="qt213"){
                    $(this).html("<span id='qt213' class='hidden'>其他</span><input style='width:60%;font-size: 16px;text-align:center;border:none' id='other' value="+data[i].dictName+" onblur='TextLengthCheck(this.id,6);'>");
                }
            })
            $("#"+obj).find("tbody tr").find("td:even").not("td:last").each(function(i,obj){
                $(this).html("<div class='div_with placeholder' sid="+data[i].dictCode+"><textarea name="+data[i].dictCode+"  cols='2' placeholder='对应牙位'></textarea><ul class='tooth_map' style='margin-left: 30px;'><li><input id='"+data[i].dictCode+"leftup' class='tooth_input' type='text'/></li><li><input id='"+data[i].dictCode+"rightup' class='tooth_input' type='text'/></li><li><input id='"+data[i].dictCode+"leftdown' class='tooth_input' type='text'/></li><li><input id='"+data[i].dictCode+"rightdown' class='tooth_input' type='text'/></li></ul></div>");
            })
        }
    }
    // 初始化牙位图默认展示
    function initToothMap(obj){
        var upelhtml="<li><input type='checkbox' class='lefttop' style='margin-top:65px;'></li>";
        for (var i=18;i>10;i--){
            upelhtml+="<li><span class='yaIcon le"+(i-10)+"'></span><span class='num' name='adultupYa1' toothname="+i+">"+i+"</span></li>";
        }
        for (var j=21;j<29;j++){
            upelhtml+="<li><span class='yaIcon rg"+(j-20)+"'></span><span class='num' name='adultupYa2' toothname="+j+">"+j+"</span></li>";
        }
        upelhtml+="<li><input type='checkbox' class='righttop' style='margin-top:65px;'></li>";
        $("#"+obj).find(".upYa").html(upelhtml);
        var downelhtml="<li><input type='checkbox' class='leftdown' style='margin-bottom:20px;'></li>";
        for (var z=48;z>40;z--){
            downelhtml+="<li><span class='num' name='adultdownYa1' toothname="+z+">"+z+"</span><span class='yaIcon le"+(z-40)+"'></span></li>";
        }
        for (var y=31;y<39;y++){
            downelhtml+="<li><span class='num' name='adultdownYa2' toothname="+y+">"+y+"</span><span class='yaIcon rg"+(y-30)+"'></span></li>";
        }
        downelhtml+="<li><input type='checkbox' class='rightdown' style='margin-top:20px;'></li>";
        $("#"+obj).find(".downYa").html(downelhtml);
    }
    // 初始化牙位图打印展示
    function initBlockToothMap(obj){
        var uphtml="";
        for (var i=18;i>10;i--){
            uphtml+="<li><img src='<%=contextPath%>/static/image/kqdsFront/img/tooths/leup/leup"+(i-10)+".png' alt=''><span class='num' name='adultupYa1' toothname="+i+">"+i+"</span></li>";
        }
        for (var j=21;j<29;j++){
            uphtml+="<li><img src='<%=contextPath%>/static/image/kqdsFront/img/tooths/rgup/rgup"+(j-20)+".png' alt=''></span><span class='num' name='adultupYa2' toothname="+j+">"+j+"</span></li>";
        }
        $("#"+obj).find(".upYa").html(uphtml);
        var downhtml="";
        for (var z=48;z>40;z--){
            downhtml+="<li><span class='num' name='adultdownYa1' toothname="+z+">"+z+"</span><img src='<%=contextPath%>/static/image/kqdsFront/img/tooths/ledown/ledown"+(z-40)+".png' alt=''></li>";
        }
        for (var y=31;y<39;y++){
            downhtml+="<li><span class='num' name='adultdownYa2' toothname="+y+">"+y+"</span><img src='<%=contextPath%>/static/image/kqdsFront/img/tooths/rgdown/rgdown"+(y-30)+".png' alt=''></li>";
        }
        $("#"+obj).find(".downYa").html(downhtml);
    }

    // 复选框取值
    function inputCheckedSave(name) {
        var obj = $("#"+name+"Checkbox").find("input[name="+name+"]");
        var inputSave = "";
        for ( k in obj ) {
            if(obj[k].checked) {
                inputSave = inputSave + obj[k].value + ',';
            }
        }
        return inputSave;
    }
    //牙松动牙位取值textarea
    function saveToothLoose(){
        var toothLooseItem={};
        $(".toothLooseNextBox").nextAll().find("td:even").each(function(j,el){
            var item=$(this).find("span").attr("id");
            var itemTooth=$(this).next().find("div textarea");
            if(item!=undefined){
                var checkedtoothStr=itemTooth.val();//获取牙位
                toothLooseItem[item]=checkedtoothStr;
            }
        })
        return toothLooseItem;
    }
    function saveToothLooseInput(){
        var toothLooseItem={};
        $(".toothLooseNextBox").nextAll().find("td:odd").find("div ul li").each(function(j,el){
                var item=$(el).find("input").attr("id");
                if(item!=undefined){
                    var itemInput=$("#"+item).val();
                    toothLooseItem[item]=itemInput;
                }
        })
        return toothLooseItem;
    }
    //整体情况~诊断~影像学检查 =》牙位取值textarea
    function saveTooth(obj){
        var ToothItem={};
        $("#"+obj).find("tr td:odd").each(function(j,el){
            var item=$(this).find("span").attr("id");
            var itemTooth=$(this).prev().find("div textarea");
            if(item!=undefined){
                var checkedtoothStr=itemTooth.val(); //获取牙位
                ToothItem[item]=checkedtoothStr;
            }
        })
        return ToothItem;
    }
    //整体情况~诊断~影像学检查 =》牙位取值input
    function saveToothInput(obj){
        var ToothItem={};
        $("#"+obj).find("tr td:even").find("div ul li").each(function(j,el){
            var item=$(el).find("input").attr("id");
            if(item!=undefined){
                var itemInput=$("#"+item).val();
                ToothItem[item]=itemInput;
            }
        })
        return ToothItem;
    }
    var doctorSignature = document.getElementById("doctorSignature");
    doctorSignature.onclick = function(){
        if(doctorstatus){
            layer.open({
                type: 2,
                title: '签字',
                shadeClose: true,
                shade: 0.6,
                area:userAgent.indexOf("iPad")>-1?[signatureWidth,signatureHeight] : ['70%', '65%'],
                content: contextPath + '/SignatureAct/toSignature.act?category=种植'
            });
        }
    }
    function addSignature(){
        $("#doctorimg").css("display","");
        $("#doctorimg").attr('src', signature);
        if(doctorstatus&&!patientstatus){
            updateDoctorSignature();
        }
    }
    //更新
    function updateDoctorSignature(){
        var url = contextPath + '/HUDH_MedicalRecordsAct/installData.act';
        var doctorTime = $("#doctortime").val();//医生签字时间
        var param = {
            seqId:updataid,
            doctorSignature :  signature,
            doctorTime :  doctorTime
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
                area:userAgent.indexOf("iPad")>-1?[signatureWidth,signatureHeight] : ['70%', '65%'],
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
        var url = contextPath + '/HUDH_MedicalRecordsAct/installData.act';
        var patienttime = $("#patienttime").val();//修复医生签名时间
        var param = {
            seqId:updataid,
            patientSignature :  patientsignature,//患者签名
            patientTime : patienttime//患者签名时间

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
    //    保存方法
    function save() {
// 	   基本信息seqid
        var patient_seqid = $("#patient_num").attr("seqid");
// 	   口腔专科检查
        var ismouthopening = $("#oralSpecialtyExamination").find("input[name='ismouthopening']:checked").val();//张口度ismouthopening
        var mouthopening = $("#mouthopening").val(); // 张口度mouth_openinput
        var arthrosis=inputCheckedSave("arthrosis");//颞下颌关节
        var occludingrelation=inputCheckedSave("occludingrelation");//咬合关系
        var verticalcurve=$("#oralSpecialtyExamination").find("input[name='verticalcurve']:checked").val();//纵曲线
        var horizontalcurve=$("#oralSpecialtyExamination").find("input[name='horizontalcurve']:checked").val();//横曲线
        var distancebetween=$("#distancebetween").val();//颌间距
        var labialline=$("#oralSpecialtyExamination").find("input[name='labialline']:checked").val();//唇线
        var others=$("#others").val();//其他
        var gumtypes=$("#oralSpecialtyExamination").find("input[name='gumtypes']:checked").val();//牙龈生物学类型
        var undergo=inputCheckedSave("undergo"); //口腔治疗经历
        var periodontalcondition=inputCheckedSave("periodontalcondition");//牙周情况
        var mucosalsituation=inputCheckedSave("mucosalsituation"); //粘膜情况
// 牙松动
        var toothLoose=saveToothLoose();//牙位textarea
        var anodontism=saveToothLooseInput();//牙缺失和牙松动的input十字架取值
//牙缺失
        var toothDeficiency=$("#toothDeficiency").val();//牙位textarea
// 整体口腔情况明细
        var toothCondition=saveTooth("toothConditionBox");//牙位textarea
        var toothConditionInput=saveToothInput("toothConditionBox");//牙位input 十字架
// 影像学检查
        var imageExamination=saveTooth("imageExaminationBox");//牙位textarea
        var imageExaminationInput=saveToothInput("imageExaminationBox");//牙位input 十字架
        var saprodontia = $("#saprodontia").val();//牙槽嵴顶距上颌窦底
        var residualcrown = $("#residualcrown").val();//距下牙槽神经管
        var basisnasi = $("#basisnasi").val();//距鼻底
        var alveolarcrest=$("#alveolarcrest").val()//牙槽嵴宽度
        var pulpitis = $("#imageExaminationBox").find("input[name='pulpitis']:checked").val();//上颌窦情况是否正常
        var teethtilted=$("#teethtilted").val();//上颌窦情况异常情况
        var nub = $("#imageExaminationBox").find("input[name='nub']:checked").val();//种植区域骨量是否正常
// 诊断
        var medicalCertificate=saveTooth("medicalCertificateBox"); //牙位
        var medicalCertificateInput=saveToothInput("medicalCertificateBox");//牙位input 十字架
        var other=$("#other").val();//诊断其他---输入
        // //签字时间
        var patienttime = $("#patienttime").val();//患者签字时间
        var doctortime = $("#doctortime").val();//医生签字时间
        anodontism=JSON.stringify(anodontism);
        toothConditionInput=JSON.stringify(toothConditionInput);
        imageExaminationInput=JSON.stringify(imageExaminationInput);
        medicalCertificateInput=JSON.stringify(medicalCertificateInput);
        // 以上是十字架传值
        toothLoose = JSON.stringify(toothLoose);
        toothCondition = JSON.stringify(toothCondition);
        imageExamination = JSON.stringify(imageExamination);
        medicalCertificate = JSON.stringify(medicalCertificate);

        var diagnosis=inputCheckedSave("diagnosis"); //诊断牙齿情况
        var param = {
//	     	    基本信息
            lcljId:idlclj,
            lcljNum:order_number,
            userId : patient_seqid,
//	     	    口腔专科检查
            ismouthopening : ismouthopening,
            mouthopening : mouthopening,
            arthrosis : arthrosis,
            occludingrelation : occludingrelation,
            verticalcurve : verticalcurve,
            horizontalcurve : horizontalcurve,
            distancebetween : distancebetween,
            labialline : labialline,
            others:others,
            gumtypes : gumtypes,
            undergo : undergo,
            periodontalcondition : periodontalcondition,
            mucosalsituation : mucosalsituation,
// 牙松动
            onedu : toothLoose,//textarea
            anodontism:anodontism,//牙松动和牙缺失input
//牙缺失
            onebone:toothDeficiency,//牙缺失 后台字段onebone textarea
// 整体口腔情况明细
            dentitiondefect : toothCondition,//textarea
            twodu:toothConditionInput,//input
// 影像学检查
            defectdentition : imageExamination,//textarea
            threedu:imageExaminationInput,//input
            saprodontia : saprodontia,
            residualcrown : residualcrown,
            twobone:basisnasi,//距鼻底 后台字段twobone
            threebone:alveolarcrest,//牙槽嵴宽度 后台字段threebone
            pulpitis : pulpitis,
            teethtilted : teethtilted,
            nub : nub,
// 诊断
            defectiverepair : medicalCertificate,//诊断牙位texterea
            fourbone:medicalCertificateInput,//诊断牙位input
            other :other,
            patientSignature :  patientsignature,
            doctorSignature :  signature,
            patientTime:patienttime,
            doctorTime:doctortime,
            diagnosis:diagnosis
        };
        //console.log(JSON.stringify(param)+"------------保存参数");
        var url = contextPath + '/HUDH_MedicalRecordsAct/installData.act';
        $.axseSubmit(url, param,
            function() {},
            function(r) {
                if (r.retState == "0") {
                    layer.alert('保存成功', {
                        end: function() {
                            parent.refresh();
                            var frameindex = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(frameindex); //再执行关闭
                        }
                    });
                } else {
                    layer.alert('保存失败'  );
                }
            },
            function() {
                layer.alert('保存失败' );
            });
    }

    //  修改方法
    function update() {
// 	    基本信息seqid
        var patient_seqid = $("#patient_num").attr("seqid");
// 	    口腔专科检查
        var ismouthopening = $("#oralSpecialtyExamination").find("input[name='ismouthopening']:checked").val();//张口度ismouthopening
        var mouthopening = $("#mouthopening").val(); // 张口度mouth_openinput
        var arthrosis=inputCheckedSave("arthrosis");//牙颞下颌关节
        var occludingrelation=inputCheckedSave("occludingrelation");//咬合关系
        var verticalcurve=$("#oralSpecialtyExamination").find("input[name='verticalcurve']:checked").val();//纵曲线
        var horizontalcurve=$("#oralSpecialtyExamination").find("input[name='horizontalcurve']:checked").val();//横曲线
        var distancebetween=$("#distancebetween").val();//颌间距
        var labialline=$("#oralSpecialtyExamination").find("input[name='labialline']:checked").val();//唇线
        var others=$("#others").val();//其他
        var gumtypes=$("#oralSpecialtyExamination").find("input[name='gumtypes']:checked").val();//牙龈生物学类型
        var undergo=inputCheckedSave("undergo"); //口腔治疗经历
        var periodontalcondition=inputCheckedSave("periodontalcondition");//牙周情况
        var mucosalsituation=inputCheckedSave("mucosalsituation"); //粘膜情况
// 牙松动
        var toothLoose=saveToothLoose();//牙位textarea
        var anodontism=saveToothLooseInput();//牙缺失和牙松动的input十字架取值
//牙缺失
        var toothDeficiency=$("#toothDeficiency").val();//牙位textarea
// 整体口腔情况明细
        var toothCondition=saveTooth("toothConditionBox");//牙位textarea
        var toothConditionInput=saveToothInput("toothConditionBox");//牙位input 十字架
// 影像学检查
        var imageExamination=saveTooth("imageExaminationBox");//牙位textarea
        var imageExaminationInput=saveToothInput("imageExaminationBox");//牙位input 十字架
        var saprodontia = $("#saprodontia").val();//牙槽嵴顶距上颌窦底
        var residualcrown = $("#residualcrown").val();//距下牙槽神经管
        var basisnasi = $("#basisnasi").val();//距鼻底
        var alveolarcrest=$("#alveolarcrest").val()//牙槽嵴宽度
        var pulpitis = $("#imageExaminationBox").find("input[name='pulpitis']:checked").val();//上颌窦情况是否正常
        var teethtilted=$("#teethtilted").val();//上颌窦情况异常情况
        var nub = $("#imageExaminationBox").find("input[name='nub']:checked").val();//种植区域骨量是否正常
// 诊断
        var medicalCertificate=saveTooth("medicalCertificateBox"); //牙位
        var medicalCertificateInput=saveToothInput("medicalCertificateBox");//牙位input 十字架
        var other=$("#other").val();//诊断其他---输入
        // //签字时间
        var patienttime = $("#patienttime").val();//患者签字时间
        var doctortime = $("#doctortime").val();//医生签字时间
        anodontism=JSON.stringify(anodontism);
        toothConditionInput=JSON.stringify(toothConditionInput);
        imageExaminationInput=JSON.stringify(imageExaminationInput);
        medicalCertificateInput=JSON.stringify(medicalCertificateInput);
        // 以上是十字架牙位传值
        toothLoose = JSON.stringify(toothLoose);
        toothCondition = JSON.stringify(toothCondition);
        imageExamination = JSON.stringify(imageExamination);
        medicalCertificate = JSON.stringify(medicalCertificate);

        var diagnosis=inputCheckedSave("diagnosis"); //诊断牙齿情况
        var param = {
            seqId:updataid,
//	     	    基本信息
            lcljId:idlclj,
            lcljNum:order_number,
            userId : patient_seqid,
//	     	    口腔专科检查
            ismouthopening : ismouthopening,
            mouthopening : mouthopening,
            arthrosis : arthrosis,
            occludingrelation : occludingrelation,
            verticalcurve : verticalcurve,
            horizontalcurve : horizontalcurve,
            distancebetween : distancebetween,
            labialline : labialline,
            others:others,
            gumtypes : gumtypes,
            undergo : undergo,
            periodontalcondition : periodontalcondition,
            mucosalsituation : mucosalsituation,
// 牙松动
            onedu : toothLoose,//textarea
            anodontism:anodontism,//牙松动和牙缺失input
//牙缺失
            onebone:toothDeficiency,//牙缺失 后台字段onebone textarea
// 整体口腔情况明细
            dentitiondefect : toothCondition,//textarea
            twodu:toothConditionInput,//input
// 影像学检查
            defectdentition : imageExamination,//textarea
            threedu:imageExaminationInput,//input
            saprodontia : saprodontia,
            residualcrown : residualcrown,
            twobone:basisnasi,//距鼻底 后台字段twobone
            threebone:alveolarcrest,//牙槽嵴宽度 后台字段threebone
            pulpitis : pulpitis,
            teethtilted : teethtilted,
            nub : nub,

// 诊断
            defectiverepair : medicalCertificate,//诊断牙位texterea
            fourbone:medicalCertificateInput,//诊断牙位input
            other :other,
            patientSignature :  patientsignature,
            doctorSignature :  signature,
            patientTime:patienttime,
            doctorTime:doctortime,
            diagnosis:diagnosis
        };
        //       console.log(JSON.stringify(param)+"-----------修改param");
// 	    return;
        var url = contextPath + '/HUDH_MedicalRecordsAct/installData.act';
        $.axseSubmit(url, param,
            function() {},
            function(r) {
                if (r.retState == "0") {
                    layer.alert('修改成功', {
                        end: function() {
                            parent.refresh();
                            var frameindex = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(frameindex); //再执行关闭
                        }
                    });
                } else {
                    layer.alert('修改失败');
                }
            },
            function() {
                layer.alert('修改失败');
            });
    }
    // 时间区间
    function isDuringDate(nowDate,end){
        endTime=new Date(end);
        curentDate=new Date(nowDate);
        if(endTime<curentDate){
            return true;
        }
        //console.log(false);
        return false;
    }

    // 	返回数据赋值
    function initData(){
        if(form){
            //console.log(JSON.stringify(form)+"---------------返回form数据");
            var breakTime=form.createtime;
            var createTimes=new Date(breakTime);
            // 此时间定为2020-08-14禁止修改
            var endTime=new Date("2020-08-14");
            if(createTimes<endTime){
                $(".tooth_map").addClass("hidden");
                $("textarea").removeClass("hidden");
                $(".signature_time").css("margin-top","80px");
            }
            updataid=form.seqId;//获取更新修改id
            if(form){
                $("#consent_saveBtn").css("display","none");//隐藏保存按钮
                $("#consent_updateBtn").css("display","inline-block");//显示修改按钮
                signature=form.doctorSignature;
                patientsignature=form.patientSignature;
            }
            if(signature!=""){
                $("#doctorimg").attr('src', signature);
                doctorstatus=false;
            }else{
                $("#doctorimg").attr('display', 'none');
            }
            if(patientsignature!=""){
                $("#patientimg").attr('src', patientsignature);
                patientstatus=false;
            }else{
                $("#patientimg").attr('display', 'none');
            }
            // 其他
            //$("#others").val(form["others"]);
            $("#others").attr("value",form["others"]);
            //牙缺失
            $("#toothDeficiency").text(form["onebone"]);//textarea
            //距鼻底
            //$("#basisnasi").val(form["twobone"]);
            $("#basisnasi").attr("value",form["twobone"]);
            //牙槽嵴宽度
            //$("#alveolarcrest").val(form["threebone"]);
            $("#alveolarcrest").attr("value",form["threebone"]);
            for(var i in form){
                $("input[name="+i+"]").each(function(){
                    var that=this;
                    var split = form[i].split(",");
                    for(var j = 0; j < split.length; j++){
                        if($(that).val()==split[j]){
                            $(that).attr("checked","checked");
                        }
                    }
                })//复选框及单选框赋值
                $("#"+i+"[type='text']").attr("value",form[i]);// 填框赋值
            }
// 	 		牙位赋值start
            if(form[i]="onedu"){
                var toothLoose=form.onedu;
                var table_pro=$(".toothLooseNextBox").nextAll().find("td:even");
                table_pro.each(function(j,el){
                    var tabletooth=$(this).find("span").attr("id");	//要赋值的item
                    for(var key in toothLoose){
                        if(toothLoose[key]!=""){
                            if(tabletooth!=undefined&&tabletooth==key){
                                $("textarea[name="+key+"]").text(toothLoose[key]);
                            }
                        }
                    }
                })
            }
            if(form[i]="dentitiondefect"){
                var dentitiondefect=form.dentitiondefect;
                var table_pro=$("#toothConditionBox").find("td:odd");
                table_pro.each(function(j,el){
                    var tabletooth=$(this).find("span").attr("id");	//要赋值的item
                    for(var key in dentitiondefect){
                        if(dentitiondefect[key]!=""){
                            if(tabletooth!=undefined&&tabletooth==key){
                                $("textarea[name="+key+"]").text(dentitiondefect[key]);
                            }
                        }
                    }
                })
            }
            if(form[i]="defectdentition"){
                var defectdentition=form.defectdentition;
                var table_pro=$("#imageExaminationBox").find("td:odd");
                table_pro.each(function(j,el){
                    var tabletooth=$(this).find("span").attr("id");	//要赋值的item
                    for(var key in defectdentition){
                        if(defectdentition[key]!=""){
                            if(tabletooth!=undefined&&tabletooth==key){
                                $("textarea[name="+key+"]").text(defectdentition[key]);
                            }
                        }
                    }
                })

            }
            if(form[i]="defectiverepair"){
                var defectiverepair=form.defectiverepair;
                var table_pro=$("#medicalCertificateBox").find("td:odd");
                table_pro.each(function(j,el){
                    var tabletooth=$(this).find("span").attr("id");	//要赋值的item
                    for(var key in defectiverepair){
                        if(defectiverepair[key]!=""){
                            if(tabletooth!=undefined&&tabletooth==key){
                                $("textarea[name="+key+"]").text(defectiverepair[key]);
                            }
                        }
                    }
                })
            }
            // 十字架牙位
            if(form[i]="anodontism"){//牙松动和牙缺失
                var anodontism=form.anodontism;
                for(var key in anodontism){
                    $("#"+key).attr("value",anodontism[key]);
                }
            }
            if(form[i]="twodu"){//整体情况
                var toothConditionInput=form.twodu;
                for(var key in toothConditionInput){
                    $("#"+key).attr("value",toothConditionInput[key]);
                }
            }
            if(form[i]="threedu"){
                var imageExaminationInput=form.threedu;
                for(var key in imageExaminationInput){
                    $("#"+key).attr("value",imageExaminationInput[key]);
                }
            }
            if(form[i]="fourbone"){
                var medicalCertificateInput=form.fourbone;
                for(var key in medicalCertificateInput){
                    $("#"+key).attr("value",medicalCertificateInput[key]);
                }
            }
            // 诊断其他
            $("#other").attr("value",form["other"]);
        }
        getButtonAllCurPage(menuid);
    }
    //修改按钮权限
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
    //打印方法
    function myPreviewAll() {
        $("input[type='radio']").each(function(i,obj){
            $(this).removeAttr("disabled");
        });
        bdhtml=window.document.body.innerHTML;
        sprnstr="<!--startprint-->";
        eprnstr="<!--endprint-->";
        prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
        prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
        var htmlStyle="<style>.level{disabled:false!important}button{display:none;}input{border:none;}.content .colDefined .contentItem tr td input:not([type='text']){width:10px}.demand{font-size: 10px;font-weight: normal;}";
        htmlStyle+="#toothConditionBoxMap2{font-size:10px!important}.placeholder:empty:before{content:' ';}.inputhidden{border: 1px solid transparent!important;}::-webkit-input-placeholder{color:transparent;}</style>"
        window.document.body.innerHTML=prnhtml+htmlStyle;
        window.print();  //打印
        //window.document.body.innerHTML=bdhtml; // 恢复页面
        //window.location.reload();
    }
// 字段备注新增内容
//     onedu--牙位牙松动textarea
//     twodu--牙位整体情况input十字架
//     threedu--牙位影像学检查input十字架
//     anodontism--牙位牙松动和牙缺失input十字架
//     onebone--牙缺失textarea
//     twobone--距鼻底
//     threebone--牙槽嵴宽度
//     fourbone--牙位诊断input十字架

</script>
</html>