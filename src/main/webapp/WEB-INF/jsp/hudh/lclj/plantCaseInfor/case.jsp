<%@page import="java.io.Console"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    String contextPath = request.getContextPath();
    if (contextPath.equals("")) {
        contextPath = "/kqds";
    }
    String seqidFather=request.getParameter("seqidFather");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link type="text/css" rel="stylesheet" href="<%=contextPath%>/static/css/kqdsFront/plantCase/diagnosisProject.css" />

    <script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
    <script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/kqds/kqds_system.js"></script>
    <script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/kqds/kqds_request.js"></script>
    <%--     <script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script> --%>
    <script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
</head>
<style type="text/css">
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


    .colDefined {
        padding: 0px;
    }

    ul,
    ol {
        margin: 0px;
    }

    .toothBitmap {
        border: 1px solid yellow;
    }

    .toothBitmap table tr {
        height: 20px;
    }

    .toothBitmap table tr>td {
        text-align: center;
    }


    /* 签名 */
    #content #consent_signature {
        overflow: hidden;
        /* 	 height: 120px; */
        margin-top: 10px;
        margin-bottom: 20px;
    }

    #content #consent_signature>.signature_time {
        width: 40%;
        height: 100%;
        float: left;
        position: relative;
    }

    #content #consent_signature>.signature_time>.signature_box {
        width: 100%;
        height: 80px;
    }

    #content #consent_signature>.signature_time>.signature_box>span {
        font-weight: normal;
    }

    /* 时间选择框 */
    #content #consent_signature>.signature_time>input {
        width: 35%;
        position: absolute;
        right: 0px;
        bottom: 0px;
        text-align: center;
    }

    .btns {
        margin-top: 134px;
    }

    .btns button {
        font-size: 16px;
        line-height: 30px;
        background-color: #00A6C0;
        font-weight: normal;
        color: white;
        border: 0px;
        border-radius: 5px;
        padding: 0px 20px;
        letter-spacing: 1px;
    }

    .btns #consent_saveBtn {
        margin-right: 30px;
        height: 30px;
    }
    .btns #print_Btn{
        height: 30px;
    }

    .site {
        margin-left: 75%;
        width: 100px;
        text-align: center;
    }

    input {
        border: none;
    }

    * {
        margin: 0px;
        padding: 0px;
        font-size: 16px;
        line-height: 30px;
        list-style: none;
    }

    #content {
        font-weight: bold;
        margin-bottom: 20px;
        padding-top: 30px;
    }

    /* 输入框 */
    #content input[type="text"] {
        font-weight: bold;
        color: #00a6c0;
    }

    /* 标题 */
    #content .bigtitle {
        display: block;
        width: 100%;
        font-size: 26px !important;
        line-height: 26px;
        letter-spacing: 1px;
        font-weight: bold;
        margin-bottom: 1%;
    }

    /* 信息输入组合框 */
    .examine_continer .rpInfo_import {
        width: 100%;
    }

    .examine_continer .rpInfo_import>span {
        font-weight: normal;
    }

    .examine_continer .rpInfo_import>input {
        background-color: transparent;
        font-weight: bold;
        border-radius: 0px;
        padding: 0px;
    }

    /* 签名 */
    #content #consent_signature {
        margin-top: 10px;
        margin-bottom: 20px;
    }

    #content #consent_signature>.signature_time {
        width: 40%;
        margin-left: 60%;
        position: relative;
    }

    #content #consent_signature>.signature_time>.signature_box {
        width: 100%;
    }

    #content #consent_signature>.signature_time>.signature_box>span {
        font-weight: normal;
    }

    /* 时间选择框 */
    #content #consent_signature>.signature_time>input {
        width: 40%;
        position: absolute;
        right: 0px;
        bottom: 0px;
        text-align: center;
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
        font-weight: normal;
    }

    input:-moz-placeholder {
        font-weight: normal;
    }

    input::-moz-placeholder {
        font-weight: normal;
    }

    input:-ms-input-placeholder {
        font-weight: normal;
    }

    .textAuto_element {
        display: block;
        width: 100%;
        color: #00a6c0;
        border-bottom: 1px solid black;
    }

    /* 	分隔线 */
    .line {
        display: block;
        border: 1px solid #776c6c;
        margin: 7px 0;
    }

    h2 {
        margin-top: 0px;
        margin-bottom: 0px;
    }

    /* 	必填项 */
    .colorRed {
        color: #d10c0c;
        font-size: 20px;
        margin-right: 5px;
    }

    .inputheight {
        border: 1px solid #e5e5e5 !important;
    }

    .inputwidth {
        width: 50% !important;
    }

    .address:empty:before {
        content: attr(placeholder);
        color: #777474;
        font-weight: normal !important;
    }

    .address:focus:before {
        content: none;
    }

    .allergy:empty:before {
        content: attr(placeholder);
        color: #777474;
        font-weight: normal !important;
    }

    .allergy:focus:before {
        content: none;
    }

    .treatmentparts:empty:before {
        content: attr(placeholder);
        color: #777474;
        font-weight: normal !important;
    }

    .treatmentparts:focus:before {
        content: none;
    }

    .diagnose:empty:before {
        content: attr(placeholder);
        color: #777474;
        font-weight: normal !important;
    }

    .diagnose:focus:before {
        content: none;
    }

    .input {
        color: #00a6c0;
    }

    ul li {
        float: left;

    }
    .fangan{
        margin: 5px;
    }
    .preoperative{
        width: 100%;
        display: inline-block;
        margin-left: 10px;
    }
    .preoperative li{
        margin-right:12px;
    }
    .smalltitle{
        margin-left: 10px;
        line-height: 37px;
    }
    .yawei{
        float: right;
        width: 50%;
        height: 100%;
        text-align: center;
    }
    .seat{
        line-height: 70px;
    }
    .titles{
        line-height: 50px;
        margin-left: 54px;
    }
    .item{
        margin-left:10px;
    }

    /*分隔线 */
    .line-logo {
        display: block;
        border-top:2px dotted #776c6c;
        padding:7px 0;
    }
    .scheme{
        border-bottom: 1px solid #b3b3b3;
        height: 134px;
    }
    .before{
        height:386px;
    }
    .before-preoperative{
        float: left;
        border-right: 1px solid #b3b3b3;
        width: 20%;
        height:100%;
    }
    @page {
        size: 206mm 280mm;
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
    .conceal{
        display: none;
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
        width: 235px;
        height: 25px;
    }
    .patient .inputDiv:nth-child(5){
        width: 100px!important;
    }
    .patient .inputDiv:nth-child(7){
        width: 470px!important;
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

    @media print {
        .patient .inputDiv:nth-child(4){
            width: 190px!important;
        }
        .patient .inputDiv:nth-child(9){
            width: 190px!important;
        }

    }
    .container-fluid{
        margin: 0!important;
        padding: 0!important;
    }
</style>

<body>
<!--startprint-->
<div style="width: 100%;padding: 10px 20px;">
    <div style="page-break-after:always">

        <!-- 标题 -->
        <div class="row">
            <div class="col-md-12 col-sm-12" style="position: relative;padding: 0;border-bottom: 2px solid #776c6c;">
                <img id="logoImg" src="<%=contextPath%>/static/image/kqdsFront/jiagong/logoName.png">
                <span class="bigtitle">诊疗方案</span>
            </div>
        </div>

        <!-- 基本信息 -->
        <div class="container-fluid examine_continer">

            <div class="row baseInfo">
                <div class="col-md-12 col-sm-12 colDefined">
                    <!-- 患者基本信息 -->
                    <div class="patient" style="width:90%;float:left;">
                        <div class="inputDiv">
                            <span>首诊时间：</span><font type="text" id="patient_time"></font>
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
                            <span>证件号码：</span><font type="text" id="patient_idNumber"></font>
                        </div>
                        <div class="inputDiv">
                            <%--                    <span>出生年月：</span><font type="text" id="patient_bone"></font>--%>
                            <span>现居住地址：</span><font type="text" id="patient_site"></font>
                        </div>
                        <div class="inputDiv" style="clear:left;">
                            <span>联系电话：</span><font type="text" id="patient_phone"></font>
                        </div>
                        <div class="inputDiv" >
                            <span>紧急联系人：</span><font type="text" id="patient_instancyName"></font>
                        </div>
                        <div class="inputDiv">
                            <span>紧急联系人电话：</span><font type="text" id="patient_instancyPhone"></font>
                        </div>
                    </div>
                    <div class="patientHeader" style="width:10%;float:left;">
                        <img src="<%=contextPath%>/static/image/kqdsFront/jiagong/headImg.jpg">
                        <input style="display:none;" type="file" />
                    </div>
                </div>
            </div>
        </br>

        <div style="border:1px solid #b3b3b3;">
            <%-- <div class="scheme">

               --<div class="col-md-12 col-sm-12 col-xs-12 colDefined fangan">
                     <div class="rpInfo_import" style="width: 100%;overflow: hidden;">
                         <span style="float:left;">方案1：</font></span>
                         <div style="float:left;width:73%;position: relative;">
                                 <span id="treatmentparts1" class="textAuto_element treatmentparts"
                                       placeholder='有医生自行议填写' style="font-size: 16px;font-weight: bold;"
                                       contenteditable="true"></span>
                         </div>

                         <input name="consultation_opinion" id="consultation_opinionA" value="0" type="radio"
                                type="radio" />
                     </div>
                 </div>
                <br>
              <div class="col-md-12 col-sm-12 col-xs-12 colDefined fangan">
                    <div class="rpInfo_import" style="width: 100%;overflow: hidden;">
                        <span style="float:left;">方案2：</span>
                        <div style="float:left;width:73%;position: relative;">
                                <span id="treatmentparts2" class="textAuto_element treatmentparts"
                                      placeholder='有医生自行议填写' style="font-size: 16px;font-weight: bold;"
                                      contenteditable="true"></span>
                        </div>
                        <input name="consultation_opinion" id="consultation_opinionA" value="1" type="radio"
                               type="radio" />
                    </div>
                </div>
                <br>
               <div class="col-md-12 col-sm-12 col-xs-12 colDefined fangan">
                    <div class="rpInfo_import" style="width: 100%;overflow: hidden;">
                        <span style="float:left;">方案3：</font></span>
                        <div style="float:left;width:73%;position: relative;">
                                <span id="treatmentparts3" class="textAuto_element treatmentparts"
                                      placeholder='有医生自行议填写' style="font-size: 16px;font-weight: bold;"
                                      contenteditable="true"></span>
                        </div>
                        <input name="consultation_opinion" id="consultation_opinionA" value="2" type="radio"
                               type="radio" />
                    </div>
                </div>
            </div>--%>
            <div class="row" style="margin-right:0;margin-left:0;">
                <div class="col-md-12 col-sm-12 colDefined" style="border-bottom: 1px solid #b3b3b3;height: 40px;">
                    <span class="smalltitle">以下详细方案确认后填写，此方案为最终双方商榷后确认方案。</span>
                    <!-- <font>●</font> -->
                </div>
                <div class="before">
                    <div class="before-preoperative">
                        <span class="" style="line-height: 356px;margin-left: 50px;">术前准备</span>
                    </div>
                    <div style="float: right;width: 80%;">

                        <div class="preoperative">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="preoperatives" id="imaging-examine" type="checkbox"
                                           value="影像学检查" /><label for="imaging-examine">影像学检查</label>
                                </li>
                                <li>
                                    <input name="preoperatives" id="blood-test" type="checkbox" value="验血" /><label
                                        for="blood-test">验血</label>
                                </li>
                                <li style="display: -webkit-inline-box;">
                                    <input onclick="hui()" name="preoperatives" id="consultation" type="checkbox"
                                           value="会诊" /><label for="consultation">会诊:</label>
                                    <ul id="huizhen">

                                        <li>(
                                            <input name="preoperatives" id="dentistry" type="checkbox"
                                                   value="综合科" /><label for="dentistry">综合科</label>
                                        </li>
                                        <li>
                                            <input name="preoperatives" id="repair" type="checkbox"
                                                   value="修复科" /><label for="repair">修复科</label>
                                            )</li>

                                    </ul>
                                </li>

                            </ul>
                        </div>
                        <div class="preoperative">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="preoperatives" id="ecg-examine" type="checkbox"
                                           value="心电图检查" /><label for="ecg-examine">心电图检查</label>
                                </li>
                                <li>
                                    <input name="preoperatives" id="speciality-examine" type="checkbox"
                                           value="专科检查" /><label for="speciality-examine">专科检查</label>
                                </li>
                                <li style="display: -webkit-inline-box;">
                                    <input onclick="paizhao()" name="preoperatives" id="picture" type="checkbox"
                                           value="拍照" /><label for="picture">拍照:</label>
                                    <ul id="pho">

                                        <li>(
                                            <input name="preoperatives" id="intraoral" type="checkbox"
                                                   value="口外" /><label for="intraoral">口外</label>
                                        </li>
                                        <li>
                                            <input name="preoperatives" id="para-oral" type="checkbox"
                                                   value="口内" /><label for="para-oral">口内</label>
                                        </li>
                                        <li>
                                            <input name="preoperatives" id="aesthetics" type="checkbox"
                                                   value="美学" /><label for="aesthetics">美学</label>
                                            )
                                        </li>
                                    </ul>
                                </li>

                            </ul>
                        </div>

                        <div class="preoperative">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="preoperatives" id="digital-face" type="checkbox"
                                           value="数字化面扫" /><label for="digital-face">数字化面扫</label>
                                </li>
                                <li>
                                    <input name="preoperatives" id="digital-mouth" type="checkbox"
                                           value="数字化口扫" /><label for="digital-mouth">数字化口扫</label>
                                </li>

                            </ul>
                        </div>
                        <div class="preoperative">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="preoperatives" id="ultrasonic-tooth" type="checkbox"
                                           value="超声波洁牙" /><label for="ultrasonic-tooth">超声波洁牙</label>
                                </li>
                                <li>
                                    <input name="deep" id="scaling" value="0" type="radio" /><label
                                        for="scaling">刮治</label>/
                                    <input name="deep" id="deep-scaling" value="1" type="radio" /><label
                                        for="deep-scaling">深度刮治</label>
                                </li>
                            </ul>
                        </div>
                        <div class="preoperative">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="preoperatives" id="periodontal" type="checkbox"
                                           value="牙周治疗" /><label for="periodontal">牙周治疗</label>
                                </li>

                                <li style="display: -webkit-inline-box;">
                                    <input onclick="shuqian()" name="preoperatives" id="preoperative"
                                           type="checkbox" value="术前取模" /><label for="preoperative">术前取模</label>
                                    <ul id="qumo">

                                        <li>(
                                            <input name="preoperatives" id="occlusio" type="checkbox"
                                                   value="定咬合" /><label for="occlusio">定咬合</label>
                                        </li>
                                        <li>
                                            <input name="preoperatives" id="maxillary" type="checkbox"
                                                   value="上颌架" /><label for="maxillary">上颌架</label>
                                            )</li>
                                    </ul>
                                </li>

                            </ul>
                        </div>
                        <div class="preoperative">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="preoperatives" id="molding" type="checkbox" value="蜡型设计" /><label
                                        for="molding">蜡型设计</label>
                                </li>
                                <li>
                                    <input name="preoperatives" id="risk-assess" type="checkbox"
                                           value="美学风险评估" /><label for="risk-assess">美学风险评估</label>
                                </li>
                            </ul>
                        </div>
                        <div class="preoperative">
                            <ul class="loseTooth_option">
                                <li style="display: -webkit-inline-box;">
                                    <input onclick="shuzi()" name="preoperatives" id="digital-guide" type="checkbox"
                                           value="数字化导板" /><label for="digital-guide">数字化导板</label>
                                    <ul id="daoban">

                                        <li>(
                                            <input name="preoperatives" id="toothType" type="checkbox"
                                                   value="牙支持式" /><label for="toothType">牙支持式</label>
                                        </li>
                                        <li>
                                            <input name="preoperatives" id="softTissue" type="checkbox"
                                                   value="软组织支持式" /><label for="softTissue">软组织支持式</label>
                                        </li>
                                        <li>
                                            <input name="preoperatives" id="boneTissue" type="checkbox"
                                                   value="骨组织支持式" /><label for="boneTissue">骨组织支持式</label>
                                        </li>
                                        <li>
                                            <input name="preoperatives" id="Positioning" type="checkbox"
                                                   value="定位导板" /><label for="Positioning">定位导板</label>
                                        </li>
                                        <li>
                                            <input name="preoperatives" id="wholeGuide" type="checkbox"
                                                   value="全程导板" /><label for="wholeGuide">全程导板</label>
                                            )</li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                        <div class="preoperative">
                            <ul class="loseTooth_option">
                                <li style="display: -webkit-inline-box;">
                                    <input onclick="gaozhi()" name="preoperatives" id="inform" type="checkbox"
                                           value="告知" /><label for="inform">告知：</label>
                                    <ul id="gaozhi">

                                        <li>(

                                            <input name="preoperatives" id="diagnosisScheme" type="checkbox"
                                                   value="诊疗方案" /><label for="diagnosisScheme">诊疗方案</label>
                                        </li>
                                        <li>
                                            <input name="preoperatives" id="treat" type="checkbox"
                                                   value="治疗计划" /><label for="treat">治疗计划</label>
                                        </li>
                                        <li>
                                            <input name="preoperatives" id="diagnosisCourse" type="checkbox"
                                                   value="诊疗过程" /><label for="diagnosisCourse">诊疗过程</label>
                                        </li>
                                        <li>
                                            <input name="preoperatives" id="risk" type="checkbox"
                                                   value="风险" /><label for="risk">风险</label>
                                            )</li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                        <div class="preoperative">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="preoperatives" id="informedConsent" type="checkbox"
                                           value="签署知情同意书 " /><label for="informedConsent">签署知情同意书 </label>
                                </li>
                                <li>
                                    <input name="preoperatives" id="quality" type="checkbox" value="质保" /><label
                                        for="quality">质保</label>
                                </li>
                            </ul>
                        </div>
                        <div class="preoperative">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="preoperatives" id="pay" type="checkbox" value="缴费" /><label
                                        for="pay">缴费</label>
                                </li>
                                <li>
                                    <input name="preoperatives" id="soperationTime" type="checkbox"
                                           value="预约手术时间" /><label for="soperationTime">预约手术时间</label>
                                    <input id="appointmenttime" type="text"
                                           class="consent_time inputheight inputheight2" readonly="readonly"
                                           placeholder="请选择日期" />
                                </li>
                            </ul>
                        </div>
                        <div class="preoperative">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="preoperatives" id="explains" type="checkbox"
                                           value="交待手术日注意事项 " /><label for="explains">交待手术日注意事项 </label>
                                </li>
                                <li>
                                    <input name="preoperatives" id="spreoperative" type="checkbox"
                                           value="术前用药" /><label for="spreoperative">术前用药</label>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>


            <div class="row" style="margin-right:0;margin-left:0;">
                <div style="border-top:1px solid #b3b3b3;height: 200px;">
                    <div style="float: left;border-right: 1px solid #b3b3b3;width: 20%;height: 100%;">
                        <span class="" style="line-height: 213px;margin-left: 54px;">种植系统</span>
                    </div>
                    <div style="width: 80%;float: right;">
                        <div style="width: 24%;display: inline-block;margin-left: 8px;">
                            <ul class="loseTooth_option" style="display: inline-grid;">
                                <li>
                                    <input name="bicon" id="Dentium" type="checkbox" value="Dentium " /><label
                                        for="Dentium">Dentium </label>
                                </li>
                                <li>
                                    <label for="spreoperative">
                                        <div id="diagnosis_continer" class="container-fluid">
                                            <div class="row">
                                                <!-- 牙位图 -->
                                                <div class="zl_toothMapdiv">
                                                    <ul class="tooth_map"
                                                        style="width: 100%;height: 45px;margin-left: 1%;">
                                                        <li>
                                                            <input id="dentiumleftup"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="dentiumrightup"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="dentiumleftdown"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="dentiumrightdown"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </label>
                                </li>
                            </ul>
                        </div>
                        <div style="width:24%;display: inline-block;">
                            <ul class="loseTooth_option" style="display: inline-grid;">
                                <li>
                                    <input name="bicon" id="Hiossen" type="checkbox" value="Hiossen " /><label
                                        for="Hiossen">Hiossen </label>
                                </li>
                                <li>
                                    <label for="spreoperative">
                                        <div id="diagnosis_continer" class="container-fluid">
                                            <div class="row">
                                                <!-- 牙位图 -->
                                                <div class="zl_toothMapdiv">
                                                    <ul class="tooth_map"
                                                        style="width: 100%;height: 45px;margin-left: 1%;">
                                                        <li>
                                                            <input id="hiossenleftup"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="hiossenrightup"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="hiossenleftdown"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="hiossenrightdown"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </label>
                                </li>
                            </ul>
                        </div>
                        <div style="width:24%;display: inline-block;">
                            <ul class="loseTooth_option" style="display: inline-grid;">
                                <li>
                                    <input name="bicon" id="ICX" type="checkbox" value="ICX " /><label for="ICX">ICX
                                </label>
                                </li>
                                <li>
                                    <label for="spreoperative">
                                        <div id="diagnosis_continer" class="container-fluid">
                                            <div class="row">
                                                <!-- 牙位图 -->
                                                <div class="zl_toothMapdiv">
                                                    <ul class="tooth_map"
                                                        style="width: 100%;height: 45px;margin-left: 1%;">
                                                        <li>
                                                            <input id="icxleftup"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="icxrightup"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="icxleftdown"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="icxrightdown"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </label>
                                </li>
                            </ul>
                        </div>
                        <div style="width:24%;display: inline-block;">
                            <ul class="loseTooth_option" style="display: inline-grid;">
                                <li>
                                    <input name="bicon" id="Camlog" type="checkbox" value="Camlog " /><label
                                        for="Camlog">Camlog </label>
                                </li>
                                <li>
                                    <label for="spreoperative">
                                        <div id="diagnosis_continer" class="container-fluid">
                                            <div class="row">
                                                <!-- 牙位图 -->
                                                <div class="zl_toothMapdiv">
                                                    <ul class="tooth_map"
                                                        style="width: 100%;height: 45px;margin-left: 1%;">
                                                        <li>
                                                            <input id="camlogleftup"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="camlogrightup"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="camlogleftdown"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="camlogrightdown"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </label>
                                </li>
                            </ul>
                        </div>
                        <div style="width: 24%;display: inline-block;margin-left: 8px;">
                            <ul class="loseTooth_option" style="display: inline-grid;">
                                <li>
                                    <input name="bicon" id="Zimmer" type="checkbox" value="Zimmer " /><label
                                        for="Zimmer">Zimmer </label>
                                </li>
                                <li>
                                    <label for="spreoperative">
                                        <div id="diagnosis_continer" class="container-fluid">
                                            <div class="row">
                                                <!-- 牙位图 -->
                                                <div class="zl_toothMapdiv">
                                                    <ul class="tooth_map"
                                                        style="width: 100%;height: 45px;margin-left: 1%;">
                                                        <li>
                                                            <input id="zimmerleftup"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="zimmerrightup"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="zimmerleftdown"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="zimmerrightdown"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </label>
                                </li>
                            </ul>
                        </div>
                        <div style="width: 24%;display: inline-block;">
                            <ul class="loseTooth_option" style="display: inline-grid;">
                                <li>
                                    <input name="bicon" id="Nobel-CC" type="checkbox" value="Nobel-CC " /><label
                                        for="Nobel-CC">Nobel-CC </label>
                                </li>
                                <li>
                                    <label for="spreoperative">
                                        <div id="diagnosis_continer" class="container-fluid">
                                            <div class="row">
                                                <!-- 牙位图 -->
                                                <div class="zl_toothMapdiv">
                                                    <ul class="tooth_map"
                                                        style="width: 100%;height: 45px;margin-left: 1%;">
                                                        <li>
                                                            <input id="nobelccleftup"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="nobelccrightup"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="nobelccleftdown"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="nobelccrightdown"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </label>
                                </li>
                            </ul>
                        </div>
                        <div style="width: 24%;display: inline-block;">
                            <ul class="loseTooth_option" style="display: inline-grid;">
                                <li>
                                    <input name="bicon" id="Nobel-Active" type="checkbox"
                                           value="Nobel-Active" /><label for="Nobel-Active">Nobel-Active </label>
                                </li>
                                <li>
                                    <label for="spreoperative">
                                        <div id="diagnosis_continer" class="container-fluid">
                                            <div class="row">
                                                <!-- 牙位图 -->
                                                <div class="zl_toothMapdiv">
                                                    <ul class="tooth_map"
                                                        style="width: 100%;height: 45px;margin-left: 1%;">
                                                        <li>
                                                            <input id="nobelactiveleftup"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="nobelactiverightup"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="nobelactiveleftdown"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="nobelactiverightdown"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </label>
                                </li>
                            </ul>
                        </div>
                        <div style="width: 24%;display: inline-block;">
                            <ul class="loseTooth_option" style="display: inline-grid;">
                                <li>
                                    <input name="bicon" id="Pmc" type="checkbox" value="Pmc" /><label for="Pmc">Pmc
                                </label>
                                </li>
                                <li>
                                    <label for="spreoperative">
                                        <div id="diagnosis_continer" class="container-fluid">
                                            <div class="row">
                                                <!-- 牙位图 -->
                                                <div class="zl_toothMapdiv">
                                                    <ul class="tooth_map"
                                                        style="width: 100%;height: 45px;margin-left: 1%;">
                                                        <li>
                                                            <input id="pmcleftup"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="pmcrightup"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="pmcleftdown"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                        <li>
                                                            <input id="pmcrightdown"
                                                                   onblur="TextLengthCheck(this.id,10);"
                                                                   class="tooth_input" type="text">
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </label>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="row" style="margin-right:0;margin-left:0;border-top: 1px solid #b3b3b3;">
                    <div style="height: 50px;">
                        <div style="float: left;border-right: 1px solid #b3b3b3;width: 20%;height: 100%;">
                            <span class="titles">拔牙</span>
                        </div>
                        <div style="width: 80%;float: right;">
                            <div class="" style="margin-left: 10px;">
                                <ul class="loseTooth_option">
                                    <li style="width: 156px;height: 50px;">
                                        <label for="spreoperative">
                                            <div id="diagnosis_continer" class="container-fluid">
                                                <div class="row">
                                                    <!-- 牙位图 -->
                                                    <div class="zl_toothMapdiv">
                                                        <ul class="tooth_map"
                                                            style="width: 100%;height: 50px;margin-left: 1%;">
                                                            <li>
                                                                <input id="extractionleftup"
                                                                       onblur="TextLengthCheck(this.id,10);"
                                                                       class="tooth_input" type="text">
                                                            </li>
                                                            <li>
                                                                <input id="extractionrightup"
                                                                       onblur="TextLengthCheck(this.id,10);"
                                                                       class="tooth_input" type="text">
                                                            </li>
                                                            <li>
                                                                <input id="extractionleftdown"
                                                                       onblur="TextLengthCheck(this.id,10);"
                                                                       class="tooth_input" type="text">
                                                            </li>
                                                            <li>
                                                                <input id="extractionrightdown"
                                                                       onblur="TextLengthCheck(this.id,10);"
                                                                       class="tooth_input" type="text">
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </label>
                                    </li>
                                </ul>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="row" style="margin-right:0;margin-left:0;border-top: 1px solid #b3b3b3">
                    <div style="height: 50px;" class="opstall">
                        <div style="float: left;border-right: 1px solid #b3b3b3;width: 20%;height: 100%;">
                            <span class="titles">手术方式</span>
                        </div>
                        <div style="width: 80%;float: right;">
                            <ul class="loseTooth_option" id="mode" style="margin-left:10px;">
                                <li style="line-height: 50px;margin-right:10px;">
                                    <input name="modus" id="immediately" type="checkbox" value="即刻种植" /><label
                                        for="immediately">即刻种植 </label>
                                </li>
                                <li style="line-height: 50px;margin-right:10px;">
                                    <input name="modus" id="early" type="checkbox" value="早期种植" /><label
                                        for="early">早期种植
                                </label>
                                </li>
                                <li style="line-height: 50px;margin-right:10px;">
                                    <input name="modus" id="postpone" type="checkbox" value="延期种植" /><label
                                        for="postpone">延期种植</label>
                                </li>
                                <li style="line-height: 50px;margin-right:10px;">
                                    <input name="modus" id="minimally" type="checkbox" value="微创种植" /><label
                                        for="minimally">微创种植</label>
                                </li>

                            </ul>
                            <div style="margin-left: 10px;">
                                <ul class="loseTooth_option">
                                    <li style="height: 50px;line-height: 50px;">
                                        <label>第一次种植</label>
                                    </li>
                                    <li style="width: 154px;height: 50px;margin-right: 10px;">
                                        <label for="spreoperative">
                                            <div id="diagnosis_continer" class="container-fluid">
                                                <div class="row">
                                                    <!-- 牙位图 -->
                                                    <div class="zl_toothMapdiv">
                                                        <ul class="tooth_map"
                                                            style="width: 100%;height: 50px;margin-left: 1%;">
                                                            <li>
                                                                <input id="onedentalimplantleftup"
                                                                       onblur="TextLengthCheck(this.id,10);"
                                                                       class="tooth_input" type="text">
                                                            </li>
                                                            <li>
                                                                <input id="onedentalimplantrightup"
                                                                       onblur="TextLengthCheck(this.id,10);"
                                                                       class="tooth_input" type="text">
                                                            </li>
                                                            <li>
                                                                <input id="onedentalimplantleftdown"
                                                                       onblur="TextLengthCheck(this.id,10);"
                                                                       class="tooth_input" type="text">
                                                            </li>
                                                            <li>
                                                                <input id="onedentalimplantrightdown"
                                                                       onblur="TextLengthCheck(this.id,10);"
                                                                       class="tooth_input" type="text">
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </label>
                                    </li>
                                </ul>
                            </div>
                            <div>
                                <ul class="loseTooth_option">
                                    <li style="height: 50px;line-height: 50px;">
                                        <label>第二次种植</label>
                                    </li>
                                    <li style="width: 154px;height: 50px;margin-right: 10px;">
                                        <label for="spreoperative">
                                            <div id="diagnosis_continer" class="container-fluid">
                                                <div class="row">
                                                    <!-- 牙位图 -->
                                                    <div class="zl_toothMapdiv">
                                                        <ul class="tooth_map" style="width: 100%;height: 50px;margin-left: 1%;">
                                                            <li>
                                                                <input id="twodentalimplantleftup"
                                                                       onblur="TextLengthCheck(this.id,10);"
                                                                       class="tooth_input" type="text">
                                                            </li>
                                                            <li>
                                                                <input id="twodentalimplantrightup"
                                                                       onblur="TextLengthCheck(this.id,10);"
                                                                       class="tooth_input" type="text">
                                                            </li>
                                                            <li>
                                                                <input id="twodentalimplantleftdown"
                                                                       onblur="TextLengthCheck(this.id,10);"
                                                                       class="tooth_input" type="text">
                                                            </li>
                                                            <li>
                                                                <input id="twodentalimplantrightdown"
                                                                       onblur="TextLengthCheck(this.id,10);"
                                                                       class="tooth_input" type="text">
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </label>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row"
                     style="margin-right:0;margin-left:0;border-bottom:1px solid #b3b3b3;border-top: 1px solid #b3b3b3;">
                    <div style="height: 50px;">
                        <div style="float: left;border-right: 1px solid #b3b3b3;width: 20%;height: 100%;">
                            <span class="titles">基台使用</span>
                        </div>
                        <div style="width: 80%;float: right;">
                            <ul class="loseTooth_option" style="margin-left: 10px;">
                                <li style="line-height: 50px;margin-right:10px;">
                                    <input name="abutment" id="standard" type="checkbox" value="标准基台" /><label
                                        for="standard">标准基台 </label>
                                </li>
                                <li style="line-height: 50px;margin-right:10px;">
                                    <input name="abutment" id="angle" type="checkbox" value="角度基台" /><label
                                        for="angle">角度基台
                                </label>
                                </li>
                                <li style="line-height: 50px;margin-right:10px;">
                                    <input name="abutment" id="selfdom" type="checkbox" value="个性化基台" /><label
                                        for="selfdom">个性化基台</label>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="row" style="margin-right:0;margin-left:0;border-bottom:1px solid #b3b3b3">
                    <div style="height:150px;">
                        <div style="float: left;border-right: 1px solid #b3b3b3;width: 20%;height: 100%;">
                            <span class="" style="line-height: 150px;margin-left: 50px;">辅助手术</span>
                        </div>
                        <div style="width: 80%;float: right;border-bottom: 1px solid #b3b3b3;">
                            <ul class="loseTooth_option"
                                style="float: left;width: 17%;height: 77px;border-right: 1px solid;margin: 0;">
                                <li style="margin-left: 10px;line-height: 81px;">
                                    <input name="paraoperative" id="phalanx" type="checkbox" value="植骨" /><label
                                        for="phalanx">植骨</label>
                                </li>
                            </ul>
                            <div style="width: 80%;float: right;">
                                <ul class="loseTooth_option">
                                    <li>
                                        <label for="bonemeal">骨粉<input name="bonemeal" id="bonemeal" value="" style="width:60px;text-align: center;" />g</label>
                                    </li>
                                    <li>(</li>
                                    <li>
                                        <input name="paraoperative" id="hiao1" type="checkbox" value="海奥1" /><label
                                            for="hiao1">海奥
                                    </label>
                                    </li>
                                    <li>
                                        <input name="paraoperative" id="bioOss1" type="checkbox"
                                               value="Bio-oss1" /><label for="bioOss1">Bio-oss</label>
                                    </li>
                                    <li>)</li>
                                    <li>
                                        <label for="ossein">骨胶原<input name="ossein" id="ossein" value=""
                                         style="width:60px;text-align: center;" />mg</label>
                                    </li>
                                </ul>
                            </div>
                            <div style="width: 80%;float: right;">
                                <ul class="loseTooth_option">
                                    <li>
                                        <label for="periost">骨膜<input name="periost" id="periost" value="" style="width:60px;text-align: center;" />张</label>
                                    </li>
                                    <li>(</li>
                                    <li>
                                        <input name="paraoperative" id="hiao2" type="checkbox" value="博纳格" /><label
                                            for="hiao2">博纳格 </label>
                                    </li>
                                    <li>
                                        <input name="paraoperative" id="bioOss2" type="checkbox"
                                               value="Bio-oss2" /><label for="bioOss2">Bio-oss</label>
                                    </li>
                                    <li>)</li>
                                    <li>
                                </ul>
                            </div>
                        </div>
                        <div style="width: 80%;float: right;">
                            <ul class="loseTooth_option" style="margin-left: 10px;">
                                <li style="margin-right: 10px;">
                                    <input name="paraoperative" id="sinus1" type="checkbox" value="上颌窦内提" /><label
                                        for="sinus1">上颌窦内提</label>
                                </li>
                                <li style="margin-right: 10px;">
                                    <input name="paraoperative" id="tissuePatch" type="checkbox"
                                           value="组织补片" /><label for="tissuePatch">组织补片
                                </label>
                                </li>
                                <li style="margin-right: 10px;">
                                    <input name="paraoperative" id="boneSplitting" type="checkbox"
                                           value="骨劈开" /><label for="boneSplitting">骨劈开</label>
                                </li>
                                <li style="margin-right: 10px;">
                                    <input name="paraoperative" id="transplantation" type="checkbox"
                                           value="游离龈移植" /><label for="transplantation">游离龈移植</label>
                                </li>
                            </ul>
                        </div>
                        <div style="width: 80%;float: right;">
                            <ul class="loseTooth_option" style="margin-left: 10px;">
                                <li style="margin-right: 10px;">
                                    <input name="paraoperative" id="sinus2" type="checkbox" value="上颌窦外提" /><label
                                        for="sinus2">上颌窦外提</label>
                                </li>
                                <li style="margin-right: 10px;">
                                    <input name="paraoperative" id="transplantation1" type="checkbox"
                                           value="结缔组织移植" /><label for="transplantation1">结缔组织移植
                                </label>
                                </li>
                                <li style="margin-right: 10px;">
                                    <input name="paraoperative" id="boneExtrusion" type="checkbox"
                                           value="骨挤压" /><label for="boneExtrusion">骨挤压 </label>
                                </li>
                                <li style="margin-right: 10px;">
                                    <input name="paraoperative" id="boneGraft" type="checkbox"
                                           value="自体骨移植" /><label for="boneGraft">自体骨移植</label>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="row" style="margin-right:0;margin-left:0;border-bottom:1px solid #b3b3b3">

                  <%--  <div class="col-md-12 col-sm-12 colDefined" style="border-bottom: 1px solid #b3b3b3;">
                        <span class="smalltitle titles">修复方案</span>
                    </div>--%>
                    <div class="row" style="margin-right:0;margin-left:0;border-bottom: 1px solid #b3b3b3">
                        <div style="height: 50px;">
                            <div style="float: left;border-right: 1px solid #b3b3b3;width: 20%;height: 100%;">
                                <span class="titles">修复方案</span>
                            </div>
                            <div style="width: 80%;float: right;">
                                <ul class="loseTooth_option" style="margin-left:10px;">
                                    <div>
                                        <ul class="loseTooth_option">
                                            <li style="height: 50px;line-height: 50px;">
                                                <label>种植牙位</label>
                                            </li>
                                            <li style="width: 154px;height: 50px;margin-right: 10px;">
                                                <label for="spreoperative">
                                                    <div id="diagnosis_continer" class="container-fluid">
                                                        <div class="row">
                                                            <!-- 牙位图 -->
                                                            <div class="zl_toothMapdiv">
                                                                <ul class="tooth_map"
                                                                    style="width: 100%;height: 50px;margin-left: 1%;">
                                                                    <li>
                                                                        <input id="dentalimplantleftup"
                                                                               onblur="TextLengthCheck(this.id,10);"
                                                                               class="tooth_input" type="text">
                                                                    </li>
                                                                    <li>
                                                                        <input id="dentalimplantrightup"
                                                                               onblur="TextLengthCheck(this.id,10);"
                                                                               class="tooth_input" type="text">
                                                                    </li>
                                                                    <li>
                                                                        <input id="dentalimplantleftdown"
                                                                               onblur="TextLengthCheck(this.id,10);"
                                                                               class="tooth_input" type="text">
                                                                    </li>
                                                                    <li>
                                                                        <input id="dentalimplantrightdown"
                                                                               onblur="TextLengthCheck(this.id,10);"
                                                                               class="tooth_input" type="text">
                                                                    </li>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </label>
                                            </li>
                                        </ul>
                                    </div>
                                    <div>
                                        <ul class="loseTooth_option">
                                            <li style="height: 50px;line-height: 50px;">
                                                <label>整体修复牙位</label>
                                            </li>
                                            <li style="width: 154px;height: 50px;margin-right: 10px;">
                                                <label for="spreoperative">
                                                    <div id="diagnosis_continer" class="container-fluid">
                                                        <div class="row">
                                                            <!-- 牙位图 -->
                                                            <div class="zl_toothMapdiv">
                                                                <ul class="tooth_map"
                                                                    style="width: 100%;height: 50px;margin-left: 1%;">
                                                                    <li>
                                                                        <input id="entiretyrepairleftup"
                                                                               onblur="TextLengthCheck(this.id,10);"
                                                                               class="tooth_input" type="text">
                                                                    </li>
                                                                    <li>
                                                                        <input id="entiretyrepairrightup"
                                                                               onblur="TextLengthCheck(this.id,10);"
                                                                               class="tooth_input" type="text">
                                                                    </li>
                                                                    <li>
                                                                        <input id="entiretyrepairleftdown"
                                                                               onblur="TextLengthCheck(this.id,10);"
                                                                               class="tooth_input" type="text">
                                                                    </li>
                                                                    <li>
                                                                        <input id="entiretyrepairrightdown"
                                                                               onblur="TextLengthCheck(this.id,10);"
                                                                               class="tooth_input" type="text">
                                                                    </li>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </label>
                                            </li>
                                        </ul>
                                    </div>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div style="height: 50px;">
                        <div style="float: left;border-right: 1px solid #b3b3b3;width: 20%;height: 100%;">
                            <span class="titles">临时义齿</span>
                        </div>
                        <div style="width: 80%;float: right;">
                            <ul class="loseTooth_option" style="margin-left:10px;">
                                <li style="line-height: 50px;margin-right:10px;">
                                    <input name="one" id="one" type="radio" value="1" /><label for="one">有</label>
                                </li>

                                <li style="line-height: 50px;margin-right:10px;">
                                    <input name="one" id="zero" type="radio" value="0" /><label for="zero">无</label>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="row" style="margin-right:0;margin-left:0;">
                    <div style="height: 77px;">
                        <div style="float: left;border-right: 1px solid #b3b3b3;width: 25%;height: 100%;">
                            <div style="float: left;width: 50%;height: 100%;border-right:1px solid #b3b3b3;">
                                <div id="diagnosis_continer" class="container-fluid">
                                    <div class="row" style="margin-top: 17px;">
                                        <!-- 牙位图 -->
                                        <div class="zl_toothMapdiv">
                                            <ul class="tooth_map" style="width: 100%;height: 45px;margin-left: 1%;">
                                                <li>
                                                    <input id="temporarytleftup"
                                                           onblur="TextLengthCheck(this.id,10);" class="tooth_input"
                                                           type="text">
                                                </li>
                                                <li>
                                                    <input id="temporarytrightup"
                                                           onblur="TextLengthCheck(this.id,10);" class="tooth_input"
                                                           type="text">
                                                </li>
                                                <li>
                                                    <input id="temporarytleftdown"
                                                           onblur="TextLengthCheck(this.id,10);" class="tooth_input"
                                                           type="text">
                                                </li>
                                                <li>
                                                    <input id="temporarytrightdown"
                                                           onblur="TextLengthCheck(this.id,10);" class="tooth_input"
                                                           type="text">
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="yawei">
                                <span class="seat">压模临时牙</span>
                            </div>
                        </div>
                        <div style="float: left;border-right: 1px solid #b3b3b3;width: 25%;height: 100%;">
                            <div style="float: left;width: 50%;height: 100%;border-right:1px solid #b3b3b3;">
                                <div id="diagnosis_continer" class="container-fluid">
                                    <div class="row" style="margin-top: 17px;">
                                        <!-- 牙位图 -->
                                        <div class="zl_toothMapdiv">
                                            <ul class="tooth_map" style="width: 100%;height: 45px;margin-left: 1%;">
                                                <li>
                                                    <input id="commonleftup" onblur="TextLengthCheck(this.id,10);"
                                                           class="tooth_input" type="text">
                                                </li>
                                                <li>
                                                    <input id="commonrightup" onblur="TextLengthCheck(this.id,10);"
                                                           class="tooth_input" type="text">
                                                </li>
                                                <li>
                                                    <input id="commonleftdown" onblur="TextLengthCheck(this.id,10);"
                                                           class="tooth_input" type="text">
                                                </li>
                                                <li>
                                                    <input id="commonrightdown"
                                                           onblur="TextLengthCheck(this.id,10);" class="tooth_input"
                                                           type="text">
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="yawei">
                                <span class="seat">普通活动牙</span>
                            </div>
                        </div>
                        <div style="float: left;border-right: 1px solid #b3b3b3;width: 25%;height: 100%;">
                            <div style="float: left;width: 50%;height: 100%;border-right:1px solid #b3b3b3;">
                                <div id="diagnosis_continer" class="container-fluid">
                                    <div class="row" style="margin-top: 17px;">
                                        <!-- 牙位图 -->
                                        <div class="zl_toothMapdiv">
                                            <ul class="tooth_map" style="width: 100%;height: 45px;margin-left: 1%;">
                                                <li>
                                                    <input id="instantlyleftup"
                                                           onblur="TextLengthCheck(this.id,10);" class="tooth_input"
                                                           type="text">
                                                </li>
                                                <li>
                                                    <input id="instantlyrightup"
                                                           onblur="TextLengthCheck(this.id,10);" class="tooth_input"
                                                           type="text">
                                                </li>
                                                <li>
                                                    <input id="instantlyleftdown"
                                                           onblur="TextLengthCheck(this.id,10);" class="tooth_input"
                                                           type="text">
                                                </li>
                                                <li>
                                                    <input id="instantlyrightdown"
                                                           onblur="TextLengthCheck(this.id,10);" class="tooth_input"
                                                           type="text">
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="yawei">
                                <span class="seat">即刻修复</span>
                            </div>
                        </div>
                        <div style="float: left;width: 25%;height: 100%;">
                            <div style="float: left;width: 50%;height: 100%;border-right:1px solid #b3b3b3;">
                                <div id="diagnosis_continer" class="container-fluid">
                                    <div class="row" style="margin-top: 17px;">
                                        <!-- 牙位图 -->
                                        <div class="zl_toothMapdiv">
                                            <ul class="tooth_map" style="width: 100%;height: 45px;margin-left: 1%;">
                                                <li>
                                                    <input id="atonceleftup" onblur="TextLengthCheck(this.id,10);"
                                                           class="tooth_input" type="text">
                                                </li>
                                                <li>
                                                    <input id="atoncerightup" onblur="TextLengthCheck(this.id,10);"
                                                           class="tooth_input" type="text">
                                                </li>
                                                <li>
                                                    <input id="atonceleftdown" onblur="TextLengthCheck(this.id,10);"
                                                           class="tooth_input" type="text">
                                                </li>
                                                <li>
                                                    <input id="atoncerightdown"
                                                           onblur="TextLengthCheck(this.id,10);" class="tooth_input"
                                                           type="text">
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="yawei">
                                <span class="seat">即刻负重</span>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
    </div>
        </br>

    <div style="width: 100%;">
        <!-- 标题 -->
        <div class="row conceal">
            <div class="col-md-12 col-sm-12" style="position: relative;padding: 0;">
                <img id="logoImg" src="<%=contextPath%>/static/image/kqdsFront/jiagong/logoName.png">
                <span class="bigtitle">诊疗方案</span>
            </div>
        </div>
        <div style="border:1px solid #b3b3b3;">
            <div class="row" style="margin-right:0;margin-left:0;border-bottom:1px solid #b3b3b3">



                <div class="row" style="margin-right:0;margin-left:0;">
                    <div style="height: 413px;">
                        <div style="float: left;border-right: 1px solid #b3b3b3;width: 20%;height:100%;">
                            <span style="line-height: 400px;margin-left: 50px;">修复方式</span>
                        </div>
                        <div style="width: 80%;float: right;">
                            <div style="margin-top: 10px;">
                                <ul class="loseTooth_option" style="margin-left: 10px;">
                                    <li>
                                        <input name="repair" id="imaging" type="checkbox" value="单冠" /><label
                                            for="imaging">单冠</label>
                                    <li style="width: 154px;">
                                        <label for="spreoperative">
                                            <div id="diagnosis_continer" class="container-fluid">
                                                <div class="row">
                                                    <!-- 牙位图 -->
                                                    <div class="zl_toothMapdiv">
                                                        <ul class="tooth_map"
                                                            style="width: 100%;height: 45px;margin-left: 1%;">
                                                            <li>
                                                                <input id="imagingleftup"
                                                                       onblur="TextLengthCheck(this.id,10);"
                                                                       class="tooth_input" type="text">
                                                            </li>
                                                            <li>
                                                                <input id="imagingrightup"
                                                                       onblur="TextLengthCheck(this.id,10);"
                                                                       class="tooth_input" type="text">
                                                            </li>
                                                            <li>
                                                                <input id="imagingleftdown"
                                                                       onblur="TextLengthCheck(this.id,10);"
                                                                       class="tooth_input" type="text">
                                                            </li>
                                                            <li>
                                                                <input id="imagingrightdown"
                                                                       onblur="TextLengthCheck(this.id,10);"
                                                                       class="tooth_input" type="text">
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </label>
                                    </li>
                                    <li>对应材质</li>
                                    </li>
                                    <li>
                                        <input name="repair" id="lianguan" type="checkbox" value="联冠" /><label
                                            for="lianguan">联冠</label>
                                    <li style="width: 154px;">
                                        <label for="spreoperative">
                                            <div id="diagnosis_continer" class="container-fluid">
                                                <div class="row">
                                                    <!-- 牙位图 -->
                                                    <div class="zl_toothMapdiv">
                                                        <ul class="tooth_map"
                                                            style="width: 100%;height: 45px;margin-left: 1%;">
                                                            <li>
                                                                <input id="lianguanleftup"
                                                                       onblur="TextLengthCheck(this.id,10);"
                                                                       class="tooth_input" type="text">
                                                            </li>
                                                            <li>
                                                                <input id="lianguanrightup"
                                                                       onblur="TextLengthCheck(this.id,10);"
                                                                       class="tooth_input" type="text">
                                                            </li>
                                                            <li>
                                                                <input id="lianguanleftdown"
                                                                       onblur="TextLengthCheck(this.id,10);"
                                                                       class="tooth_input" type="text">
                                                            </li>
                                                            <li>
                                                                <input id="lianguanrightdown"
                                                                       onblur="TextLengthCheck(this.id,10);"
                                                                       class="tooth_input" type="text">
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </label>
                                    </li>
                                    <li>对应材质</li>
                                    </li>
                                </ul>
                            </div>
                            <div class="preoperative">
                                <ul class="loseTooth_option">
                                    <li>
                                        <input name="repair" id="overdenture" type="checkbox"
                                               value="种植覆盖义齿" /><label for="overdenture">种植覆盖义齿</label>
                                    </li>
                                </ul>
                            </div>
                            <div class="next">
                                <div class="preoperative">
                                    <ul class="loseTooth_option">
                                        <li>
                                            <input name="repair" id="locator" type="checkbox"
                                                   value="Locator式" /><label for="locator">Locator式</label>
                                        </li>

                                    </ul>

                                    <ul class="loseTooth_option">

                                        <li>
                                            <input name="repair" id="chromiuma" type="checkbox" value="钴铬" /><label
                                                for="chromiuma">钴铬</label>
                                        </li>
                                        <li>
                                            <input name="repair" id="titaniuma" type="checkbox" value="纯钛" /><label
                                                for="titaniuma">纯钛</label>
                                        </li>
                                        <li>
                                            <input name="repair" id="alloya" type="checkbox" value="钛合金" /><label
                                                for="alloya">钛合金</label>
                                        </li>
                                    </ul>
                                </div>

                                <div class="preoperative">
                                    <ul class="loseTooth_option">
                                        <li>
                                            <input name="repair" id="barclip" type="checkbox" value="杆卡式" /><label
                                                for="barclip">杆卡式</label>
                                        </li>
                                    </ul>

                                    <ul class="loseTooth_option">
                                        <li>
                                            <input name="repair" id="titaniumb" type="checkbox" value="纯钛" /><label
                                                for="titaniumb">纯钛</label>
                                        </li>
                                        <li>
                                            <input name="repair" id="Peeka" type="checkbox" value="Peek" /><label
                                                for="Peeka">Peek</label>
                                        </li>
                                    </ul>
                                </div>

                                <div class="preoperative">
                                    <ul class="loseTooth_option">
                                        <li>
                                            <input name="repair" id="telescope" type="checkbox" value="套筒冠" /><label
                                                for="telescope">套筒冠</label>
                                        </li>
                                    </ul>

                                    <ul class="loseTooth_option">
                                        <li>
                                            <input name="repair" id="chromiumb" type="checkbox" value="钴铬" /><label
                                                for="chromiumb">钴铬</label>
                                        </li>
                                        <li>
                                            <input name="repair" id="titaniumc" type="checkbox" value="纯钛" /><label
                                                for="titaniumc">纯钛</label>
                                        </li>
                                        <li>
                                            <input name="repair" id="zirconia" type="checkbox"
                                                   value="氧化锆(爱尔创/威兰德)" /><label for="zirconia">氧化锆(爱尔创/威兰德)</label>
                                        </li>
                                        <li>
                                            <input name="repair" id="Peekb" type="checkbox" value="Peek" /><label
                                                for="Peekb">Peek</label>
                                        </li>
                                    </ul>
                                </div>

                                <div class="preoperative">
                                    <ul class="loseTooth_option">
                                        <li>
                                            <input name="repair" id="attachment" type="checkbox"
                                                   value="磁性附着体" /><label for="attachment">磁性附着体</label>
                                        </li>
                                    </ul>

                                    <ul class="loseTooth_option">
                                        <li>
                                            <input name="repair" id="chromiumc" type="checkbox" value="钴铬" /><label
                                                for="chromiumc">钴铬</label>
                                        </li>
                                        <li>
                                            <input name="repair" id="titaniumd" type="checkbox" value="纯钛" /><label
                                                for="titaniumd">纯钛</label>
                                        </li>
                                        <li>
                                            <input name="repair" id="alloyb" type="checkbox" value="钛合金" /><label
                                                for="alloyb">钛合金</label>
                                        </li>
                                    </ul>
                                </div>

                                <div class="preoperative">
                                    <ul class="loseTooth_option">
                                        <li>
                                            <input name="repair" id="ballCap" type="checkbox" value="球帽" /><label
                                                for="ballCap">球帽</label>
                                        </li>
                                    </ul>

                                    <ul class="loseTooth_option">
                                        <li>
                                            <input name="repair" id="chromiumd" type="checkbox" value="钴铬" /><label
                                                for="chromiumd">钴铬</label>
                                        </li>
                                        <li>
                                            <input name="repair" id="titaniume" type="checkbox" value="纯钛" /><label
                                                for="titaniume">纯钛</label>
                                        </li>
                                        <li>
                                            <input name="repair" id="alloyc" type="checkbox" value="钛合金" /><label
                                                for="alloyc">钛合金</label>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="preoperative">
                                <li style="display: -webkit-inline-box;">
                                    <input onclick="caizhi()" name="repair" id="implantMaterial" type="checkbox"
                                           value="种植覆盖义齿牙冠材质" /><label for="implantMaterial">种植覆盖义齿牙冠材质</label>
                                    <ul id="caizhi">

                                        <li>(

                                            <input name="repair" id="steelCrown" type="checkbox"
                                                   value="松风塑钢牙冠" /><label for="steelCrown">松风塑钢牙冠</label>
                                        </li>
                                        <li>
                                            <input name="repair" id="resinTooth" type="checkbox"
                                                   value="树脂牙" /><label for="resinTooth">树脂牙</label>
                                        </li>(品牌))
                                    </ul>
                                </li>
                            </div>
                            <div class="preoperative">
                                <ul class="loseTooth_option">
                                    <li>
                                        <input name="repair" id="fixedDenture1" type="checkbox"
                                               value="种植固定义齿 " /><label for="fixedDenture1">种植固定义齿 </label>
                                    </li>
                                </ul>
                            </div>
                            <div class="next">
                                <div class="preoperative">
                                    <ul class="loseTooth_option">
                                        <li>
                                            <input name="repair" id="crownBridge" type="checkbox"
                                                   value=" 氧化锆一体桥冠 " /><label for="crownBridge">氧化锆一体桥冠 </label>
                                        </li>
                                        <li>
                                            <input name="repair" id="pureTitanium" type="checkbox"
                                                   value="CAD/CAM纯钛桥架+树脂牙冠" /><label
                                                for="pureTitanium">CAD/CAM纯钛桥架+树脂牙冠</label>
                                        </li>

                                    </ul>
                                </div>
                                <div class="preoperative">
                                    <ul class="loseTooth_option">
                                        <li>
                                            <input name="repair" id="longqiao" type="checkbox"
                                                   value=" 马龙桥+氧化锆冠  " /><label for="longqiao"> 马龙桥+氧化锆冠 </label>
                                        </li>
                                        <li>
                                            <input name="repair" id="cadcam" type="checkbox"
                                                   value="CAD/CAM纯钛桥架+树脂牙冠" /><label
                                                for="cadcam">CAD/CAM纯钛桥架+树脂牙冠</label>
                                        </li>
                                    </ul>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <span class="smalltitle" style="font-weight: 800;">替代方案：</span>
            <div style="height: 50px;border-bottom: 1px solid #b3b3b3;border-top: 1px solid #b3b3b3;">
                <ul class="loseTooth_option" style="margin-left:10px;">
                    <li style="line-height: 49px; margin-right: 10px;">
                        <input name="alternative" id="denture" type="checkbox" value="活动义齿修复 " /><label
                            for="denture">活动义齿修复
                    </label>
                    </li>
                    <li style="line-height: 49px; margin-right: 10px;">
                        <input name="alternative" id="fixeddenture" type="checkbox" value="固定义齿修复" /><label
                            for="fixeddenture">固定义齿修复</label>
                    </li>
                </ul>
            </div>
            <span class="smalltitle route">临床路径：</span>
            <div style="height: 817px;border-top: 1px solid #b3b3b3;" class="route">
                <div class="article_one item">
                    <div style="width: 100%;float: right;">
                        <ul class="loseTooth_option">
                            <li>
                                <!-- <input name="choice" id="implantRepair" type="radio" value="种植局部修复" onclick="ishaveillness(this.val);"/> -->
                                <input name="pathType" class="page_input_radio" id="implantRepair" type="radio"
                                       value="1" onclick="ishaveillness(this.id);">
                                <label for="choice">种植局部修复</label>
                            </li>
                        </ul>
                    </div>
                    <div id="others" class="one" style="margin-left: 10px;">
                        <div style="width: 100%;float: right;">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="clinical" id="underjaw" type="checkbox" value="下颌（基台）" /><label
                                        for="underjaw">下颌（基台）</label>
                                </li>
                                <li>
                                    <input name="clinical" id="maxilla" type="checkbox" value="上颌（基台）" /><label
                                        for="maxilla">上颌（基台）</label>
                                </li>
                                <li>
                                    <input name="clinical" id="abutmentBone" type="checkbox"
                                           value="基台+植骨（内提、骨挤压、骨劈开）" /><label
                                        for="abutmentBone">基台+植骨（内提、骨挤压、骨劈开）</label>
                                </li>

                            </ul>
                        </div>
                        <div style="width: 100%;float: right;">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="clinical" id="drillBase" type="checkbox" value="基台+外提" /><label
                                        for="drillBase">基台+外提</label>
                                </li>
                                <li>
                                    <input name="clinical" id="screw" type="checkbox" value="螺丝+外提" /><label
                                        for="screw">螺丝+外提</label>
                                </li>
                                <li>
                                    <input name="clinical" id="fourboneGrafts" type="checkbox"
                                           value="四类骨移植" /><label for="fourboneGrafts">四类骨移植</label>
                                </li>
                            </ul>
                        </div>
                        <div style="width: 100%;float: right;">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="clinical" id="submaxill" type="checkbox" value="下颌（螺丝）" /><label
                                        for="submaxill">下颌（螺丝）</label>
                                </li>
                                <li>
                                    <input name="clinical" id="mandible" type="checkbox" value="上颌（螺丝）" /><label
                                        for="mandible">上颌（螺丝）</label>
                                </li>
                                <li>
                                    <input name="clinical" id="screwbonegraft" type="checkbox"
                                           value="螺丝+植骨（内提、骨挤压、骨劈开）" /><label
                                        for="screwbonegraft">螺丝+植骨（内提、骨挤压、骨劈开）</label>
                                </li>
                            </ul>
                        </div>
                        <div style="width: 100%;float: right;">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="clinical" id="singlePiece" type="checkbox"
                                           value="单颗美学修复（即刻修复）" /><label for="singlePiece">单颗美学修复（即刻修复）</label>
                                </li>
                                <li>
                                    <input name="clinical" id="single" type="checkbox" value="单颗美学修复（愈合基台）" /><label
                                        for="single">单颗美学修复（愈合基台）</label>
                                </li>
                            </ul>
                        </div>
                        <div style="width: 100%;float: right;">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="clinical" id="singlescrew" type="checkbox"
                                           value="单颗美学修复（覆盖螺丝）" /><label for="singlescrew">单颗美学修复（覆盖螺丝）</label>
                                </li>
                            </ul>
                        </div>
                        <div style="width: 100%;float: right;">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="clinical" id="afterMore" type="checkbox"
                                           value="后牙多颗即刻负重（复合基台）" /><label for="afterMore">后牙多颗即刻负重（复合基台）</label>
                                </li>
                            </ul>
                        </div>
                        <div style="width: 100%;float: right;">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="clinical" id="mandibles" type="checkbox"
                                           value="下颌局部多颗（覆盖螺丝、延期负重、过渡期活动牙）" /><label
                                        for="mandibles">下颌局部多颗（覆盖螺丝、延期负重、过渡期活动牙）</label>
                                </li>
                            </ul>
                        </div>
                        <div style="width: 100%;float: right;">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="clinical" id="locally" type="checkbox"
                                           value="上颌局部多颗（覆盖螺丝、延期负重、过渡期活动牙）" /><label
                                        for="locally">上颌局部多颗（覆盖螺丝、延期负重、过渡期活动牙）</label>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="article_two item">
                    <div style="width: 100%;float: right;">
                        <ul class="loseTooth_option">
                            <li>
                                <!-- <input name="choice" id="growFull" type="radio" onclick="ishaveillness(this.val);" value="种植全口/半口固定义齿修复" /> -->
                                <input name="pathType" class="page_input_radio" id="growFull" type="radio" value="2"
                                       onclick="ishaveillness(this.id);">
                                <label for="choice">种植全口/半口固定义齿修复</label>
                            </li>
                        </ul>
                    </div>
                    <div id="others" class="two" style="margin-left: 10px">

                        <div style="width: 100%;float: right;">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="clinical" id="composite" type="checkbox"
                                           value="下颌+复合基台+即刻负重" /><label for="composite">下颌+复合基台+即刻负重</label>
                                </li>
                                <li>
                                    <input name="clinical" id="immediateLoading" type="checkbox"
                                           value="上颌+复合基台+即刻负重" /><label for="immediateLoading">上颌+复合基台+即刻负重</label>
                                </li>
                            </ul>

                        </div>
                        <div style="width: 100%;float: right;">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="clinical" id="deferredLoad" type="checkbox"
                                           value="下颌+覆盖螺丝+延期负重+过渡期活动牙" /><label
                                        for="deferredLoad">下颌+覆盖螺丝+延期负重+过渡期活动牙</label>
                                </li>
                            </ul>
                        </div>
                        <div style="width: 100%;float: right;">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="clinical" id="transitionalTooth" type="checkbox"
                                           value="上颌+覆盖螺丝+延期负重+过渡期活动牙" /><label
                                        for="transitionalTooth">上颌+覆盖螺丝+延期负重+过渡期活动牙</label>
                                </li>
                            </ul>
                        </div>
                        <div style="width: 100%;float: right;">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="clinical" id="focus" type="checkbox"
                                           value="重点位大量植骨、骨劈开+同期种植+螺丝1" /><label
                                        for="focus">重点位大量植骨、骨劈开+同期种植+螺丝</label>
                                </li>
                            </ul>
                        </div>
                        <div style="width: 100%;float: right;">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="clinical" id="frontTeethArea" type="checkbox"
                                           value="前牙区All-on-X即刻负重+择期上颌窦外提升植骨+延期种植" /><label
                                        for="frontTeethArea">前牙区All-on-X即刻负重+择期上颌窦外提升植骨+延期种植</label>
                                </li>
                            </ul>
                        </div>
                        <div style="width: 100%;float: right;">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="clinical" id="All-on-X" type="checkbox"
                                           value="All-on-X即刻负重+个别值体覆盖螺丝" /><label
                                        for="All-on-X">All-on-X即刻负重+个别值体覆盖螺丝</label>
                                </li>
                            </ul>
                        </div>
                    </div>

                </div>
                <div class="article_three item">
                    <div style="width: 100%;float: right;">
                        <ul class="loseTooth_option">
                            <li>
                                <!-- <input name="choice" id="renovate" type="radio" onclick="ishaveillness(this.val);" value="种植全口/半口可摘义齿修复"/> -->
                                <input name="pathType" class="page_input_radio" id="renovate" type="radio" value="3"
                                       onclick="ishaveillness(this.id);">
                                <label for="choice">种植全口/半口可摘义齿修复</label>
                            </li>
                        </ul>
                    </div>
                    <div id="others" class="three" style="margin-left: 10px">

                        <div style="width: 100%;float: right;">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="clinical" id="rodCardRepair" type="checkbox"
                                           value="下杆卡修复（螺丝、延期负重、术后取模、活动牙过渡义齿）" /><label
                                        for="rodCardRepair">杆卡修复（螺丝、延期负重、术后取模、活动牙过渡义齿）</label>
                                </li>
                            </ul>

                        </div>
                        <div style="width: 100%;float: right;">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="clinical" id="rodCardRepairl" type="checkbox"
                                           value="杆卡修复（Locator基台即刻负重、最终杆卡修复）" /><label
                                        for="rodCardRepairl">杆卡修复（Locator基台即刻负重、最终杆卡修复））</label>
                                </li>
                            </ul>
                        </div>
                        <div style="width: 100%;float: right;">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="clinical" id="rodCardRepairAll" type="checkbox"
                                           value="杆卡修复（All-on-X即刻负重）" /><label
                                        for="rodCardRepairAll">杆卡修复（All-on-X即刻负重）</label>
                                </li>
                            </ul>
                        </div>
                        <div style="width: 100%;float: right;">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="clinical" id="largNumberBone" type="checkbox"
                                           value="重点位大量植骨、骨劈开+同期种植+螺丝" /><label
                                        for="largNumberBone">重点位大量植骨、骨劈开+同期种植+螺丝</label>
                                </li>
                            </ul>
                        </div>
                        <div style="width: 100%;float: right;">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="clinical" id="locatorHalfdown" type="checkbox"
                                           value="Locator半口（下颌螺丝）" /><label
                                        for="locatorHalfdown">Locator半口（下颌螺丝）</label>
                                </li>
                                <li>
                                    <input name="clinical" id="locatorHalfup" type="checkbox"
                                           value="Locator半口（上颌螺丝）" /><label for="locatorHalfup">Locator半口（上颌螺丝）</label>
                                </li>
                            </ul>
                        </div>
                        <div style="width: 100%;float: right;">
                            <ul class="loseTooth_option">
                                <li>
                                    <input name="clinical" id="locatorAbutmentdown" type="checkbox"
                                           value="Locator半口（下颌基台）" /><label
                                        for="locatorAbutmentdown">Locator半口（下颌基台）</label>
                                </li>
                                <li>
                                    <input name="clinical" id="locatorAbutmentup" type="checkbox"
                                           value="Locator半口（上颌基台）" /><label
                                        for="locatorAbutmentup">Locator半口（上颌基台）</label>
                                </li>
                            </ul>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <div class="row">
            <div class="consent_remark">
                <div class="overstriking" style="margin: 0 10px;">备注:</div>
                <%--<textarea id="requirerestor" rows="" cols="" onblur="TextLengthCheck(this.id,200);"
                          style="border: 1px solid #ddd;margin:0 10px 5px 10px;width: 99%;"></textarea>--%>
                <textarea id="requirerestor" rows="" cols="" autoHeight="true" style="border: 1px solid #ddd;overflow-y: hidden;width:100%;"></textarea>
            </div>
            <pre id="replaceBox"></pre>
        </div>
        <div style="width: 100%;float: right;">
            <ul class="loseTooth_option">
                <li>
                    <input name="promise" id="promise1" type="checkbox" value="我已知悉医生阐述的所有方案。" /><label
                        for="promise1">我已知悉医生阐述的所有方案。</label>
                </li>
                <li style="float: right;">
                    <div class="zl_signature" style="display: flex">
                        <span id="patientSignature" style="display: inline;">患者签名：</span>
                        <img id="patientimg" style="display: inline-block;width: 100px;height: 30px;" />
                        <input id="patienttime" type="text" class="consent_time signature_time inputheight2"
                               readonly="readonly" placeholder="请选择日期" style="width:205px;border: 1px solid #e5e5e5" />
                    </div>
                </li>
            </ul>
        </div>
        <div style="width: 100%;float: right;">
            <ul class="loseTooth_option">
                <li>
                    <input name="accord" id="accord1" type="checkbox" value="我已知悉医生阐述的所有方案。" /><label
                        for="accord1">我自愿选择</label>
                    <input type="text" id="wzy" style="border: 1px solid #e5e5e5">
                    同意医生采用上述治疗方案，医生已向我详细介绍了治疗方案、种植的程序、整个过程所需的时间和费用，我认同治疗方案，并自愿支付其相关费用。 </li>
            </ul>
        </div>
    </div>
    <div>
        <div class="col-md-4 col-sm-4 col-xs-4 colDefined">
            <div class="zl_signature">
                <span id="doctorSignature" style="display: inline;">手术医生：</span>
                <img id="img" style="display: inline-block;width: 100px;height: 30px;" />
                <input id="operationdoctortime" type="text" class="consent_time signature_time inputheight2"
                       readonly="readonly" placeholder="请选择日期" style="border: 1px solid #e5e5e5" />
            </div>
        </div>
        <div class="col-md-4 col-sm-4 col-xs-4 colDefined">
            <div class="zl_signature">
                <span id="repairDoctorSignature" style="display: inline;">修复医生：</span>
                <img id="repairImg" style="display: inline-block;width: 100px;height: 30px;" />
                <input id="doctortime" type="text" class="consent_time signature_time inputheight2"
                       readonly="readonly" placeholder="请选择日期" style="border: 1px solid #e5e5e5" />
            </div>
        </div>
        <div class="col-md-4 col-sm-4 col-xs-4 colDefined">
            <div class="zl_signature">
                <span id="patientSignature1" style="display: inline;">患者签名：</span>
                <img id="patientimg1" style="display: inline-block;width: 100px;height: 30px;" />
                <input id="patienttime1" type="text" class="consent_time signature_time inputheight2"
                       readonly="readonly" placeholder="请选择日期" style="border: 1px solid #e5e5e5" />
            </div>
        </div>
    </div>
    <div id="content" style="width: 1000px;margin: 0 auto;">
        <!--endprint-->
        <!-- 按钮 -->
        <div class="btns">
            <button id="consent_saveBtn" onclick="save()">保存</button>
            <button id="consent_updateBtn" style="display: none;" class="consent_updateBtn"
                    onclick="update()">修改表单</button>
            <button id="print_Btn" onclick="myPreviewAll()">打印本页内容</button>
        </div>

    </div>

</div>

</body>
<script language="javascript" src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
<script type="text/javascript">
    var seqidFather="<%=seqidFather%>";
    var repairSignature="";
    var repairdoctorstatus=true;
    var signature="";
    var doctorstatus=true;
    var patientsignature="";
    var patientstatus=true;
    var patientsignature1="";
    var patientstatus1=true;
    var contextPath = "<%=contextPath%>";
    var menuid=window.parent.menuid;//左侧菜单id
    var pageurl = '<%=contextPath%>/HUDH_FlowAct/findPatientInformation.act';
    var id;	//选中患者id
    var order_number;//选中患者order_number
    var usercode;//选中患者usercode
    var userAgent = navigator.userAgent;
    var signatureWidth='70%';
    var signatureHeight='65%';
    // var form=window.parent.getNewForm();
    var form;
    $(function () {
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
        if (userAgent.indexOf("iPad") > -1){
            $("#content").css("width","100%").css("padding","10px 30px");

        }
        if(window.parent.consultSelectPatient){
            id= window.parent.consultSelectPatient.seqid;
            order_number= window.parent.consultSelectPatient.orderNumber;
            usercode = window.parent.consultSelectPatient.usercode;
            form=window.parent.getNewForm();
        }else{
            id= window.parent.patientObj.id;
            order_number= window.parent.patientObj.orderNumber;
            usercode = window.parent.patientObj.blcode;
            form=window.parent.parent.getNewForm();
        }
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
        document.ondragstart = function () {
            return false;
        };
        $.ajax({
            type: "POST",
            url: pageurl,
            data: {
                usercode: usercode,//usercode
                status: 0,
                id: id,
                order_number: order_number
            },

            dataType: "json",
            success: function (r) {
                $("#patient_time").text(r.cztime);
                $("#patient_num").text(r.usercode);
                $("#patient_name").text(r.username);
                $("#patient_sex").text(r.sex);
                $("#patient_age").text(r.age);
                $("#patient_idNumber").text(r.idcardno);
                $("#patient_date").attr("value", r.birthday);
                $("#patient_phone").text(r.phonenumber1);
                $("#patient_instancyName").text(r.emergencyContact);
                $("#patient_instancyPhone").text(r.emergencyPhone);
                $("#patient_site").text(r.provincename + r.cityname + r.townname + r.streetName);
            }
        });
        if(form){
            $("#consent_saveBtn").css("display","none");//隐藏保存按钮
            $("#consent_updateBtn").css("display","inline-block");//显示修改按钮
            signature=form.operationdoctorsignature;
            if(signature!=""){
                $("#img").attr('src', signature);
                doctorstatus=false;
            }else{
                $("#img").attr('display', 'none');
            }
            repairSignature=form.repairdoctorsignature;
            if(repairSignature!=""){
                $("#repairImg").attr('src', repairSignature);
                repairdoctorstatus=false;
            }else{
                $("#repairImg").attr('display', 'none');
            }
            patientsignature=form.patientsignature;
            if(patientsignature!=""){
                $("#patientimg").attr('src', patientsignature);
                patientstatus=false;
            }else{
                $("#patientimg").attr('display', 'none');
            }
            patientsignature1=form.patientsignature1;
            if(patientsignature1!=""){
                $("#patientimg1").attr('src', patientsignature1);
                patientstatus1=false;
            }else{
                $("#patientimg1").attr('display', 'none');
            }
            $("#treatmentparts1").html(form.treatmentparts1);
            $("#treatmentparts2").html(form.treatmentparts2);
            $("#treatmentparts3").html(form.treatmentparts3);
            $("input[name='consultation_opinion'][value='"+form.consultationOpinion+"']").attr("checked",true);
            $("input[name='one'][value='"+form.one+"']").attr("checked",true);
            $("input[name='pathType'][value='"+form.pathtype+"']").attr("checked",true);
            $("input[name='deep'][value='"+form.deep+"']").attr("checked",true);
            $("input[name='promise'][value='"+form.promise+"']").attr("checked",true);
            $("input[name='accord'][value='"+form.accord+"']").attr("checked",true);
            for(var key in form){
                //console.log(key+"-------------"+result[key]);
                $("#"+key).attr("value",form[key]);// 填框赋值
                $("#requirerestor").text(form["requirerestor"]);//textarea赋值
                $("#requirerestor").trigger("keyup");
                $("#replaceBox").text(form["requirerestor"]);//textarea替换框赋值
                if(form[key].indexOf(";")>0){
                    var checkboxVal= form[key];//拼接多选框的值
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
        }
        //获取当前页面所有按钮
        getButtonAllCurPage(menuid);
        /*$.ajax({
            type: "POST",
            url: contextPath + '/HUDH_LcljCaseAct/selectById.act',
            data: {
                id: seqidFather
            },
            dataType: "json",
            success: function (result) {
                if(result!=null){
                    $("#consent_saveBtn").css("display","none");//隐藏保存按钮
                    $("#consent_updateBtn").css("display","inline-block");//显示修改按钮
                    signature=result.operationDoctorsignature;
                    if(signature!=""){
                        $("#img").attr('src', signature);
                        doctorstatus=false;
                    }else{
                        $("#img").attr('display', 'none');
                    }
                    repairSignature=result.repairDoctorsignature;
                    if(repairSignature!=""){
                        $("#repairImg").attr('src', repairSignature);
                        repairdoctorstatus=false;
                    }else{
                        $("#repairImg").attr('display', 'none');
                    }
                    patientsignature=result.patientsignature;
                    if(patientsignature!=""){
                        $("#patientimg").attr('src', patientsignature);
                        patientstatus=false;
                    }else{
                        $("#patientimg").attr('display', 'none');
                    }
                    patientsignature1=result.patientsignature1;
                    if(patientsignature1!=""){
                        $("#patientimg1").attr('src', patientsignature1);
                        patientstatus1=false;
                    }else{
                        $("#patientimg1").attr('display', 'none');
                    }
                    $("#treatmentparts1").html(result.treatmentparts1);
                    $("#treatmentparts2").html(result.treatmentparts2);
                    $("#treatmentparts3").html(result.treatmentparts3);
                    $("input[name='consultation_opinion'][value='"+result.consultationOpinion+"']").attr("checked",true);
                    $("input[name='one'][value='"+result.one+"']").attr("checked",true);
                    $("input[name='pathType'][value='"+result.pathtype+"']").attr("checked",true);
                    $("input[name='deep'][value='"+result.deep+"']").attr("checked",true);
                    $("input[name='promise'][value='"+result.promise+"']").attr("checked",true);
                    $("input[name='accord'][value='"+result.accord+"']").attr("checked",true);
                    for(var key in result){
                        //console.log(key+"-------------"+result[key]);
                        $("#"+key).attr("value",result[key]);// 填框赋值
                        $("#requirerestor").text(result["requirerestor"]);//textarea赋值
                        $("#requirerestor").trigger("keyup");
                        $("#replaceBox").text(result["requirerestor"]);//textarea替换框赋值
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
                }

                //获取当前页面所有按钮
                getButtonAllCurPage(menuid);
            }
        });*/


    });


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
        $("#img").css("display","");
        $("#img").attr('src', signature);
        if(!repairdoctorstatus&&doctorstatus){
            updateOperationDoctorsignature();
        }
    }
    var repairDoctorSignature = document.getElementById("repairDoctorSignature");
    repairDoctorSignature.onclick = function(){
        if(repairdoctorstatus){
            layer.open({
                type: 2,
                title: '签字',
                shadeClose: true,
                shade: 0.6,
                area:userAgent.indexOf("iPad")>-1?[signatureWidth,signatureHeight] : ['70%', '65%'],
                content: contextPath + '/SignatureAct/toSignature.act?category=修复'
            });
        }
    }
    function addRepairSignature(){
        $("#repairImg").css("display","");
        $("#repairImg").attr('src', repairSignature);
        if(repairdoctorstatus&&!doctorstatus){
            updateRepairSignature();
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
                area:userAgent.indexOf("iPad")>-1?[signatureWidth,signatureHeight] : ['70%', '65%'],
                content: contextPath + '/SignatureAct/toSignature.act?category=患者'
            });
        }
    }
    function addPatientSignature(){
        $("#patientimg").css("display","");
        $("#patientimg").attr('src', patientsignature);
        if(!doctorstatus&&patientstatus&&!repairdoctorstatus||!doctorstatus&&patientstatus&&repairdoctorstatus||doctorstatus&&patientstatus&&!repairdoctorstatus){
            updatePatientSignature();
        }
    }

    var patientSignature1 = document.getElementById("patientSignature1");
    patientSignature1.onclick = function(){
        if(patientstatus1){
            layer.open({
                type: 2,
                title: '签字',
                shadeClose: true,
                shade: 0.6,
                area:userAgent.indexOf("iPad")>-1?[signatureWidth,signatureHeight] : ['70%', '65%'],
                content: contextPath + '/SignatureAct/toSignature.act?category=患者1'
            });
        }
    }
    function addPatientSignature1(){
        $("#patientimg1").css("display","");
        $("#patientimg1").attr('src', patientsignature1);
        if(!doctorstatus&&patientstatus1&&!repairdoctorstatus||!doctorstatus&&patientstatus1&&repairdoctorstatus||doctorstatus&&patientstatus1&&!repairdoctorstatus){
            updatePatientSignature1();
        }
    }
    //更新
    function updatePatientSignature1(){
        var url = contextPath + '/HUDH_LcljCaseAct/update.act';
        var patienttime1 = $("#patienttime1").val();//修复医生签名时间
        var param = {
            lcljid:id,
            lcljnum:order_number, //临床路径ID
            patientsignature1 :  patientsignature1,//患者签名
            patienttime1 : patienttime1//患者签名时间

        };
        $.axseSubmit(url, param,function() {},function(r) {
            layer.alert("修改成功！", {
                end: function() {
                    window.location.reload(); //刷新父页面
                    /* var frameindex = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(frameindex); //再执行关闭 */
                }
            });
        },function(r){
            layer.alert("修改失败！");
        });
    }
    //更新
    function updatePatientSignature(){
        var url = contextPath + '/HUDH_LcljCaseAct/update.act';
        var patienttime = $("#patienttime").val();//修复医生签名时间
        var param = {
            lcljid:id,
            lcljnum:order_number, //临床路径ID
            patientsignature :  patientsignature,//患者签名
            patienttime : patienttime//患者签名时间

        };
        $.axseSubmit(url, param,function() {},function(r) {
            layer.alert("修改成功！", {
                end: function() {
                    window.location.reload(); //刷新父页面
                    /* var frameindex = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(frameindex); //再执行关闭 */
                }
            });
        },function(r){
            layer.alert("修改失败！");
        });
    }
    //更新
    function updateRepairSignature(){
        var url = contextPath + '/HUDH_LcljCaseAct/update.act';
        var doctorTime = $("#doctortime").val();//修复医生签名时间
        var param = {
            lcljid:id,
            lcljnum:order_number, //临床路径ID
            repairDoctorsignature : repairSignature,//修复医生签名
            doctorTime : doctorTime//修复医生签名时间

        };
        $.axseSubmit(url, param,function() {},function(r) {
            layer.alert("修改成功！", {
                end: function() {
                    window.location.reload(); //刷新父页面
                    /* var frameindex = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(frameindex); //再执行关闭 */
                }
            });
        },function(r){
            layer.alert("修改失败！");
        });
    }
    //更新
    function updateOperationDoctorsignature(){
        var url = contextPath + '/HUDH_LcljCaseAct/update.act';
        var operationDoctorTime = $("#operationdoctortime").val();//手术医生签名时间
        var param = {
            lcljid:id,
            lcljnum:order_number, //临床路径ID
            operationDoctorsignature : signature,//手术医生签名
            operationDoctorTime : operationDoctorTime//手术医生签名时间
        };
        $.axseSubmit(url, param,function() {},function(r) {
            layer.alert("修改成功！", {
                end: function() {
                    window.location.reload(); //刷新父页面
                    /* var frameindex = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(frameindex); //再执行关闭 */
                }
            });
        },function(r){
            layer.alert("修改失败！");
        });
    }


    if ($("#renovate:checked").val() == undefined || $("#growFull:checked").val() == undefined || $("#implantRepair:checked").val() == undefined) {
        $("#others input").attr("disabled", "disabled");
    }
    $('input:radio[name="pathType"]').click(function () {
        var checkValue = $('input:radio[name="pathType"]:checked').val();
        if (checkValue == 1) {
            $(".one input").removeAttr("disabled")
        }
        if (checkValue == 2) {
            $(".two input").removeAttr("disabled")
        }
        if (checkValue == 3) {
            $(".three input").removeAttr("disabled")
        }

    })

    function ishaveillness(objName) {
        if ($("input[name=" + objName + "]:checked").val() == undefined) {
            $("#others input").attr("disabled", "disabled");
            $("#others input").attr("checked", false)
        }

    }
    $("#huizhen input").attr("disabled", "disabled");
    function hui(){
        if(document.getElementById('consultation').checked == true){
            $("#huizhen input").removeAttr("disabled")
        }else{
            $("#huizhen input").attr("disabled", "disabled");
            $("#huizhen input").attr("checked", false)
        }
    }

    $("#pho input").attr("disabled", "disabled");
    function paizhao(){
        if(document.getElementById('picture').checked == true){
            $("#pho input").removeAttr("disabled")
        }else{
            $("#pho input").attr("disabled", "disabled");
            $("#pho input").attr("checked", false)
        }
    }

    $("#qumo input").attr("disabled", "disabled");
    function shuqian(){
        if(document.getElementById('preoperative').checked == true){
            $("#qumo input").removeAttr("disabled")
        }else{
            $("#qumo input").attr("disabled", "disabled");
            $("#qumo input").attr("checked", false)
        }
    }

    $("#daoban input").attr("disabled", "disabled");
    function shuzi(){
        if(document.getElementById('digital-guide').checked == true){
            $("#daoban input").removeAttr("disabled")
        }else{
            $("#daoban input").attr("disabled", "disabled");
            $("#daoban input").attr("checked", false)
        }
    }

    $("#gaozhi input").attr("disabled", "disabled");
    function gaozhi(){
        if(document.getElementById('inform').checked == true){
            $("#gaozhi input").removeAttr("disabled")
        }else{
            $("#gaozhi input").attr("disabled", "disabled");
            $("#gaozhi input").attr("checked", false)
        }
    }

    $("#caizhi input").attr("disabled", "disabled");
    function caizhi(){
        if(document.getElementById('implantMaterial').checked == true){
            $("#caizhi input").removeAttr("disabled")
        }else{
            $("#caizhi input").attr("disabled", "disabled");
            $("#caizhi input").attr("checked", false)
        }
    }

    //保存方法
    function save(){
        var url = contextPath + '/HUDH_LcljCaseAct/insert.act';
        var param = {
            usercode:$("#patient_num").text(),
            username:$("#patient_name").val(),
            lcljid:id,
            lcljnum:order_number,
            //treatmentparts1:$("#treatmentparts1").text(),
            //treatmentparts2:$("#treatmentparts2").text(),
            //treatmentparts3:$("#treatmentparts3").text(),
            //consultationOpinion:$("input[name='consultation_opinion']:checked").val(),
            deep:$("input[name='deep']:checked").val(),
            one:$("input[name='one']:checked").val(),
            preoperatives:preoperativesPlan(),
            bicon:biconPlan(),
            abutment:abutmentPlan(),
            paraoperative:paraoperativePlan(),
            bonemeal:$("#bonemeal").val(),
            ossein:$("#ossein").val(),
            periost:$("#periost").val(),
            modus:modusPlan(),
            repair:repairPlan(),
            alternative:alternativePlan(),
            clinical:clinicalPlan(),
            pathtype:$("input[name='pathType']:checked").val(),
            requirerestor:$("#requirerestor").val(),
            promise:promisePlan(),
            accord:accordPlan(),
            wzy:$("#wzy").val(),
            patienttime:$("#patienttime").val(),
            operationdoctortime:$("#operationdoctortime").val(),
            doctortime:$("#doctortime").val(),
            appointmenttime:$("#appointmenttime").val(),
            patienttime1:$("#patienttime1").val(),
            extractionleftup:$("#extractionleftup").val(),
            extractionrightup:$("#extractionrightup").val(),
            extractionleftdown:$("#extractionleftdown").val(),
            extractionrightdown:$("#extractionrightdown").val(),
            dentiumleftup:$("#dentiumleftup").val(),
            dentiumrightup:$("#dentiumrightup").val(),
            dentiumleftdown:$("#dentiumleftdown").val(),
            dentiumrightdown:$("#dentiumrightdown").val(),
            hiossenleftup:$("#hiossenleftup").val(),
            hiossenrightup:$("#hiossenrightup").val(),
            hiossenleftdown:$("#hiossenleftdown").val(),
            hiossenrightdown:$("#hiossenrightdown").val(),
            icxleftup:$("#icxleftup").val(),
            icxrightup:$("#icxrightup").val(),
            icxleftdown:$("#icxleftdown").val(),
            icxrightdown:$("#icxrightdown").val(),
            camlogleftup:$("#camlogleftup").val(),
            camlogrightup:$("#camlogrightup").val(),
            camlogleftdown:$("#camlogleftdown").val(),
            camlogrightdown:$("#camlogrightdown").val(),
            zimmerleftup:$("#zimmerleftup").val(),
            zimmerrightup:$("#zimmerrightup").val(),
            zimmerleftdown:$("#zimmerleftdown").val(),
            zimmerrightdown:$("#zimmerrightdown").val(),
            nobelccleftup:$("#nobelccleftup").val(),
            nobelccrightup:$("#nobelccrightup").val(),
            nobelccleftdown:$("#nobelccleftdown").val(),
            nobelccrightdown:$("#nobelccrightdown").val(),
            nobelactiveleftup:$("#nobelactiveleftup").val(),
            nobelactiverightup:$("#nobelactiverightup").val(),
            nobelactiveleftdown:$("#nobelactiveleftdown").val(),
            nobelactiverightdown:$("#nobelactiverightdown").val(),
            pmcleftup:$("#pmcleftup").val(),
            pmcrightup:$("#pmcrightup").val(),
            pmcleftdown:$("#pmcleftdown").val(),
            pmcrightdown:$("#pmcrightdown").val(),
            temporarytleftup:$("#temporarytleftup").val(),
            temporarytrightup:$("#temporarytrightup").val(),
            temporarytleftdown:$("#temporarytleftdown").val(),
            temporarytrightdown:$("#temporarytrightdown").val(),
            commonleftup:$("#commonleftup").val(),
            commonrightup:$("#commonrightup").val(),
            commonleftdown:$("#commonleftdown").val(),
            commonrightdown:$("#commonrightdown").val(),
            instantlyleftup:$("#instantlyleftup").val(),
            instantlyrightup:$("#instantlyrightup").val(),
            instantlyleftdown:$("#instantlyleftdown").val(),
            instantlyrightdown:$("#instantlyrightdown").val(),
            atonceleftup:$("#atonceleftup").val(),
            atoncerightup:$("#atoncerightup").val(),
            atonceleftdown:$("#atonceleftdown").val(),
            atoncerightdown:$("#atonceleftdown").val(),
            imagingleftup:$("#imagingleftup").val(),
            imagingrightup:$("#imagingrightup").val(),
            imagingleftdown:$("#imagingleftdown").val(),
            imagingrightdown:$("#imagingrightdown").val(),
            lianguanleftup:$("#lianguanleftup").val(),
            lianguanrightup:$("#lianguanrightup").val(),
            lianguanleftdown:$("#lianguanleftdown").val(),
            lianguanrightdown:$("#lianguanrightdown").val(),

            onedentalimplantleftup:$("#onedentalimplantleftup").val(),
            onedentalimplantrightup:$("#onedentalimplantrightup").val(),
            onedentalimplantleftdown:$("#onedentalimplantleftdown").val(),
            onedentalimplantrightdown:$("#onedentalimplantrightdown").val(),
            twodentalimplantleftup:$("#twodentalimplantleftup").val(),
            twodentalimplantrightup:$("#twodentalimplantrightup").val(),
            twodentalimplantleftdown:$("#twodentalimplantleftdown").val(),
            twodentalimplantrightdown:$("#twodentalimplantrightdown").val(),
            dentalimplantleftup:$("#dentalimplantleftup").val(),
            dentalimplantrightup:$("#dentalimplantrightup").val(),
            dentalimplantleftdown:$("#dentalimplantleftdown").val(),
            dentalimplantrightdown:$("#dentalimplantrightdown").val(),
            entiretyrepairleftup:$("#entiretyrepairleftup").val(),
            entiretyrepairrightup:$("#entiretyrepairrightup").val(),
            entiretyrepairleftdown:$("#entiretyrepairleftdown").val(),
            entiretyrepairrightdown:$("#entiretyrepairrightdown").val(),
            operationDoctorsignature :signature,//手术医生签名
            repairDoctorsignature : repairSignature,//修复医生签名
            patientsignature : patientsignature,//患者签名
            patientsignature1 : patientsignature1//患者签名
        };
        $.ajax({
            type: "POST",
            url: url,
            data: param,
            dataType: "json",
            success: function (r) {
                if(r.retState== "0"){
                    layer.alert("保存成功！", {
                        end: function() {
                            var frameindex = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(frameindex); //再执行关闭
                        }
                    });
                }else{
                    layer.alert("保存失败！");
                }
            }
        });
    }
    //获取术前准备
    function preoperativesPlan() {
        var obj = document.getElementsByName("preoperatives");
        var preoperatives = "";
        for ( k in obj ) {
            if(obj[k].checked)
                preoperatives = preoperatives + obj[k].value + ';';
        }
        return preoperatives;
    }
    //获取种植系统
    function biconPlan() {
        var obj = document.getElementsByName("bicon");
        var bicon = "";
        for ( k in obj ) {
            if(obj[k].checked)
                bicon = bicon + obj[k].value + ';';
        }
        return bicon;
    }
    //获取基台使用
    function abutmentPlan() {
        var obj = document.getElementsByName("abutment");
        var abutment = "";
        for ( k in obj ) {
            if(obj[k].checked)
                abutment = abutment + obj[k].value + ';';
        }
        return abutment;
    }
    //获取辅助手术
    function paraoperativePlan() {
        var obj = document.getElementsByName("paraoperative");
        var paraoperative = "";
        for ( k in obj ) {
            if(obj[k].checked)
                paraoperative = paraoperative + obj[k].value + ';';
        }
        return paraoperative;
    }

    //获取手术方式
    function modusPlan() {
        var obj = document.getElementsByName("modus");
        var modus = "";
        for ( k in obj ) {
            if(obj[k].checked)
                modus = modus + obj[k].value + ';';
        }
        return modus;
    }

    //获取修复方式
    function repairPlan() {
        var obj = document.getElementsByName("repair");
        var repair = "";
        for ( k in obj ) {
            if(obj[k].checked)
                repair = repair + obj[k].value + ';';
        }
        return repair;
    }

    //获取替代方案
    function alternativePlan() {
        var obj = document.getElementsByName("alternative");
        var alternative = "";
        for ( k in obj ) {
            if(obj[k].checked)
                alternative = alternative + obj[k].value + ';';
        }
        return alternative;
    }

    //获取临床路径
    function clinicalPlan() {
        var obj = document.getElementsByName("clinical");
        var clinical = "";
        for ( k in obj ) {
            if(obj[k].checked)
                clinical = clinical + obj[k].value + ';';
        }
        return clinical;
    }

    function promisePlan() {
        var obj = document.getElementsByName("promise");
        var promise = "";
        for ( k in obj ) {
            if (obj[k].checked) {
                promise = obj[k].value;
            }
        }
        return promise;
    }

    function accordPlan() {
        var obj = document.getElementsByName("accord");
        var accord = "";
        for ( k in obj ) {
            if (obj[k].checked) {
                accord = obj[k].value;
            }
        }
        return accord;
    }


    //修改
    function update(){
        var url = contextPath + '/HUDH_LcljCaseAct/update.act';
        var param = {
            id:seqidFather,
            //treatmentparts1:$("#treatmentparts1").text(),
            //treatmentparts2:$("#treatmentparts2").text(),
            //treatmentparts3:$("#treatmentparts3").text(),
            //consultationOpinion:$("input[name='consultation_opinion']:checked").val(),
            deep:$("input[name='deep']:checked").val(),
            //one:$("input[name='one']:checked").val(),
            preoperatives:preoperativesPlan(),
            bicon:biconPlan(),
            abutment:abutmentPlan(),
            paraoperative:paraoperativePlan(),
            bonemeal:$("#bonemeal").val(),
            ossein:$("#ossein").val(),
            periost:$("#periost").val(),
            modus:modusPlan(),
            repair:repairPlan(),
            alternative:alternativePlan(),
            clinical:clinicalPlan(),
            pathtype:$("input[name='pathType']:checked").val(),
            requirerestor:$("#requirerestor").val(),
            promise:promisePlan(),
            accord:accordPlan(),
            wzy:$("#wzy").val(),
            patienttime:$("#patienttime").val(),
            operationdoctortime:$("#operationdoctortime").val(),
            doctortime:$("#doctortime").val(),
            appointmenttime:$("#appointmenttime").val(),
            patienttime1:$("#patienttime1").val(),
            extractionleftup:$("#extractionleftup").val(),
            extractionrightup:$("#extractionrightup").val(),
            extractionleftdown:$("#extractionleftdown").val(),
            extractionrightdown:$("#extractionrightdown").val(),
            dentiumleftup:$("#dentiumleftup").val(),
            dentiumrightup:$("#dentiumrightup").val(),
            dentiumleftdown:$("#dentiumleftdown").val(),
            dentiumrightdown:$("#dentiumrightdown").val(),
            hiossenleftup:$("#hiossenleftup").val(),
            hiossenrightup:$("#hiossenrightup").val(),
            hiossenleftdown:$("#hiossenleftdown").val(),
            hiossenrightdown:$("#hiossenrightdown").val(),
            icxleftup:$("#icxleftup").val(),
            icxrightup:$("#icxrightup").val(),
            icxleftdown:$("#icxleftdown").val(),
            icxrightdown:$("#icxrightdown").val(),
            camlogleftup:$("#camlogleftup").val(),
            camlogrightup:$("#camlogrightup").val(),
            camlogleftdown:$("#camlogleftdown").val(),
            camlogrightdown:$("#camlogrightdown").val(),
            zimmerleftup:$("#zimmerleftup").val(),
            zimmerrightup:$("#zimmerrightup").val(),
            zimmerleftdown:$("#zimmerleftdown").val(),
            zimmerrightdown:$("#zimmerrightdown").val(),
            nobelccleftup:$("#nobelccleftup").val(),
            nobelccrightup:$("#nobelccrightup").val(),
            nobelccleftdown:$("#nobelccleftdown").val(),
            nobelccrightdown:$("#nobelccrightdown").val(),
            nobelactiveleftup:$("#nobelactiveleftup").val(),
            nobelactiverightup:$("#nobelactiverightup").val(),
            nobelactiveleftdown:$("#nobelactiveleftdown").val(),
            nobelactiverightdown:$("#nobelactiverightdown").val(),
            pmcleftup:$("#pmcleftup").val(),
            pmcrightup:$("#pmcrightup").val(),
            pmcleftdown:$("#pmcleftdown").val(),
            pmcrightdown:$("#pmcrightdown").val(),
            temporarytleftup:$("#temporarytleftup").val(),
            temporarytrightup:$("#temporarytrightup").val(),
            temporarytleftdown:$("#temporarytleftdown").val(),
            temporarytrightdown:$("#temporarytrightdown").val(),
            commonleftup:$("#commonleftup").val(),
            commonrightup:$("#commonrightup").val(),
            commonleftdown:$("#commonleftdown").val(),
            commonrightdown:$("#commonrightdown").val(),
            instantlyleftup:$("#instantlyleftup").val(),
            instantlyrightup:$("#instantlyrightup").val(),
            instantlyleftdown:$("#instantlyleftdown").val(),
            instantlyrightdown:$("#instantlyrightdown").val(),
            atonceleftup:$("#atonceleftup").val(),
            atoncerightup:$("#atoncerightup").val(),
            atonceleftdown:$("#atonceleftdown").val(),
            atoncerightdown:$("#atonceleftdown").val(),
            imagingleftup:$("#imagingleftup").val(),
            imagingrightup:$("#imagingrightup").val(),
            imagingleftdown:$("#imagingleftdown").val(),
            imagingrightdown:$("#imagingrightdown").val(),
            lianguanleftup:$("#lianguanleftup").val(),
            lianguanrightup:$("#lianguanrightup").val(),
            lianguanleftdown:$("#lianguanleftdown").val(),
            lianguanrightdown:$("#lianguanrightdown").val(),

            onedentalimplantleftup:$("#onedentalimplantleftup").val(),
            onedentalimplantrightup:$("#onedentalimplantrightup").val(),
            onedentalimplantleftdown:$("#onedentalimplantleftdown").val(),
            onedentalimplantrightdown:$("#onedentalimplantrightdown").val(),
            twodentalimplantleftup:$("#twodentalimplantleftup").val(),
            twodentalimplantrightup:$("#twodentalimplantrightup").val(),
            twodentalimplantleftdown:$("#twodentalimplantleftdown").val(),
            twodentalimplantrightdown:$("#twodentalimplantrightdown").val(),
            dentalimplantleftup:$("#dentalimplantleftup").val(),
            dentalimplantrightup:$("#dentalimplantrightup").val(),
            dentalimplantleftdown:$("#dentalimplantleftdown").val(),
            dentalimplantrightdown:$("#dentalimplantrightdown").val(),
            entiretyrepairleftup:$("#entiretyrepairleftup").val(),
            entiretyrepairrightup:$("#entiretyrepairrightup").val(),
            entiretyrepairleftdown:$("#entiretyrepairleftdown").val(),
            entiretyrepairrightdown:$("#entiretyrepairrightdown").val(),
            operationDoctorsignature :signature,//手术医生签名
            repairDoctorsignature : repairSignature,//修复医生签名
            patientsignature : patientsignature,//患者签名
            patientsignature1 : patientsignature1//患者签名
        };
        $.ajax({
            type: "POST",
            url: url,
            data: param,
            dataType: "json",
            success: function (r) {
                if(r.retState== "0"){
                    layer.alert("修改成功！", {
                        end: function() {
                            var frameindex = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(frameindex); //再执行关闭
                        }
                    });
                }else{
                    layer.alert("修改失败！");
                }
            }
        });
    }

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

    function doPrint() {
        /*判断打印时选择的临床路径，只展示选择的路径*/
        if($("#renovate:checked").val() == undefined && $("#growFull:checked").val() == undefined && $("#implantRepair:checked").val() == undefined){
            $(".route").css('display','none');
        }
        if($("#implantRepair:checked").val() != undefined){
            $(".article_three").css('display','none');
            $(".article_two").css('display','none');
            $(".route").css('height','325px');
        }else if($("#growFull:checked").val() != undefined){
            $(".article_one").css('display','none');
            $(".article_three").css('display','none');
            $(".route").css('height','325px');
        }else if($("#renovate:checked").val() != undefined){
            $(".article_one").css('display','none');
            $(".article_two").css('display','none');
            $(".route").css('height','325px');
        }
        $(".conceal").css('display','block');
        $(".btns").css('display','none');

        var imgone = $("#img")[0].src
        if( !imgone){
            $("#img").css('display', 'none');
        }
        var repairImgone = $("#repairImg")[0].src
        if(!repairImgone){
            $("#repairImg").css('display', 'none');
        }
        var patientimg1one = $("#patientimg1")[0].src
        if(!patientimg1one){
            $("#patientimg1").css('display', 'none');
        }
        var operationdoctortimeone = $("#operationdoctortime").val()
        if(!operationdoctortimeone){
            $("#operationdoctortime").css('display', 'none');
        }
        var doctortimeone = $("#doctortime").val()
        if(!doctortimeone){
            $("#doctortime").css('display', 'none');
        }
        var patienttime1one = $("#patienttime1").val()
        if(!patienttime1one){
            $("#patienttime1").css('display', 'none');
        }
        $("#mode").css('display','inline-block');
        $(".opstall").css("height","100px");
        bdhtml=window.document.body.innerHTML;
        sprnstr="<!--startprint-->";
        eprnstr="<!--endprint-->";
        prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
        prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
        var htmlStyle="<style>#logoImg{widht:20%;}.before-preoperative{height:100%;}.before{height:377px;}.scheme{height:87px;}button{display:none;}textarea{height:50px!important;}span{font-size: 14px!important;}*{font-size: 12px;line-height: 16px;}#diagnosis_continer input[type='checkbox']{width:12px !important;height:12px !important;margin-top: 15px !important;}.inputheight2{border: 1px solid transparent!important;}#consent_signature{width:100%!important;}.consent_updateBtn{display:none!important;}</style>";
        window.document.body.innerHTML=prnhtml+htmlStyle;
        document.body.innerHTML=bdhtml; //恢复页面
        window.print();
        window.location.reload()
    }

    //打印方法
    function myPreviewAll() {
        doPrint()
    };

    function getButtonPower() {
        var menubutton1 = "";
        for (var i = 0; i < listbutton.length; i++) {
            if (listbutton[i].qxName == "zsbs_xgbd"&&repairdoctorstatus&&doctorstatus&&patientstatus&&patientstatus1) {
                $("#consent_updateBtn").removeClass("hidden");
            }
        }
        $("#bottomBarDdiv").append(menubutton1);
    }

</script>

</html>