<%@page import="java.io.Console"%>
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
        font-family："微软雅黑";
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
    /* 公司logo */
    #logoImg{
        width: 150px;
        height: 45px;
    }
    /* 大标题 */
    .container-fluid .bigtitle{
        display: block;
        width:100%;
        text-align: center;
        font-size: 26px;
        line-height: 26px;
        margin: 45px auto 35px;
        letter-spacing: 1px;
        font-weight: bold;
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
        width: 230px;
        height: 30px;
    }
    .patient .inputDiv:nth-child(5){
        width: 100px!important;
    }
    .patient .inputDiv:last-child{
        width: 300px!important;
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
        width:30%;
        text-align: center;
    }
    .content .colDefined .contentItem tr{
        height: 30px;
    }
    .content .colDefined .contentItem tr td input{
        width:20px;
        font-size: 14px;
        margin-left: 2px;
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
    }
    /* 牙位图 */
    .toothBitmap{
        display:none;
    }
    .toothBitmap table tr{
        height:20px;
    }
    .toothBitmap table tr>td{
        text-align: center;
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
    /* 牙位图2 */
    #toothBitmap ul{
        overflow: hidden;
    }
    #toothBitmap ul li{
        float: left;
    }
    /* 牙位赋值框 */
    .toothNumInput {
        width: 90%!important;
        text-align: center!important;
        border: none!important;
    }
    .div_with{
        width:270px;
        height:35px;
        border:0px;
        overflow: hidden;
        word-wrap: break-word;
        word-break: normal;
        margin: auto;
    }
    .div_with2{
        width:170px;
        height:35px;
        border:0px;
        overflow: hidden;
        word-wrap: break-word;
        word-break: normal;
        margin: auto;
    }
    .div_with3{
        width:300px;
        height:35px;
        border:0px;
        overflow: hidden;
        word-wrap: break-word;
        word-break: normal;
        margin: auto;
    }
    .checked_current{
        border: 1px solid red;
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
    @page{
        size:205mm 280mm;
        margin: 0 auto;
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

</style>
<body>
<!--startprint-->
<div  class="container-fluid">
    <!-- 标题 -->
    <div class="row">
        <div id="logoTitle" class="col-md-12 col-sm-12 col-xs-12">
            <img id="logoImg" src="http://www.hdbkq.cn/templets/hdb/new_header_img/hud_logo.png">
        </div>
    </div>
    <!-- 基本信息 -->
    <div class="row"  style="border-top: 1px solid #000;margin-top: 5px">
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
                    <span>出生年月：</span><font type="text" id="patient_bone"></font>
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
                <div class="inputDiv" style="clear:left;">
                    <span>现居住地址：</span><font type="text" id="patient_address"></font>
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
            <span class="smalltitle">口腔专科检查</span>
        </div>
    </div>
    <div class="row content">
        <div class="col-md-12 col-sm-12 colDefined">
            <table id="oralSpecialtyExamination" class="contentItem" border="1" width="100%">
                <!-- 主诉content -->
                <tr>
                    <td colspan="1" class="problemitem"><span class="">张口度</span></td>
                    <td colspan="4">
                        <label><input class="" type="radio" name="ismouthopening" value="0" onclick="choose(this.name);"/><span class=" ">正常</span></label>
                        <label><input class="" type="radio" name="ismouthopening" value="1" onclick="choose(this.name);"/><span class=" ">受限</span></label><input class="border_bottom ismouthopeninginput" id="mouthopening" type="text" onblur="TextLengthCheck(this.id,2);" style="cursor: no-drop;" disabled="disabled"/>mm
                    </td>
                    <td colspan="2" class="problemitem"><span class="">牙颞下颌关节</span></td>
                    <td colspan="4" class="chooseCheckbox" id="arthrosisCheckbox">
                        <label><input class="usual" type="checkbox" name="arthrosis" value="0" onclick="chooseUsual(this,this.name);"/><span class=" ">正常</span></label>
                        <label><input class="unusual" type="checkbox" name="arthrosis" value="1" onclick="chooseUnusual(this.name);"/><span class=" ">弹响</span></label>
                        <label><input class="unusual" type="checkbox" name="arthrosis" value="2" onclick="chooseUnusual(this.name);"/><span class=" ">疼痛</span></label>
                        <label><input class="unusual" type="checkbox" name="arthrosis" value="3" onclick="chooseUnusual(this.name);"/><span class=" ">习惯性拖拉</span></label>
                        <label><input class="unusual" type="checkbox" name="arthrosis" value="4" onclick="chooseUnusual(this.name);"/><span class=" ">绞索</span></label>
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
                <tr>
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
                    <td colspan="3" class="problemitem"><span class="">唇线</span></td>
                    <td colspan="1">
                        <label><input class="" type="radio" name="labialline"  value="高"/><span class=" ">高</span></label>
                        <label><input class="" type="radio" name="labialline" value="中"/><span class=" ">中</span></label>
                        <label><input class="" type="radio" name="labialline" value="低"/><span class=" ">低</span></label>
                    </td>
                    <td colspan="2" class="problemitem"><span class="">牙龈生物学类型</span></td>
                    <td colspan="3">
                        <label><input class="" type="radio" name="gumtypes" value="薄龈"/><span class=" ">薄龈</span></label>
                        <label><input class="" type="radio" name="gumtypes" value="厚龈"/><span class=" ">厚龈</span></label>
                    </td>
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
                    <td  colspan="4" class="table_width"><div class="div_with placeholder" id="ysd1"></div></td>
                    <td  colspan="" class="problemitem table_width"><span class="" id="ysd2">Ⅱ°</span></td>
                    <td  colspan="4" class="table_width"><div class="div_with placeholder" id="ysd2"></div></td>

                </tr>
                <tr>
                    <td  colspan="" class="problemitem table_width"><span class="" id="ysd3">Ⅲ°</span></td>
                    <td  colspan="4" class="table_width"><div class="div_with placeholder" id="ysd3"></div></td>
                    <!-- 						<td  colspan="" class="table_width problemitem"><span class=" " id="4">牙列缺失</span></td> -->
                    <!-- 						<td  colspan="4" class="table_width"><div class="div_with placeholder" id="4"></div></td> -->
                    <td  colspan="" class="table_width"></td>
                    <td  colspan="4" class="table_width"></td>
                </tr>
                <!-- 					<tr> -->
                <!-- 						<td  colspan="" class="table_width problemitem"><span class=" " id="5">牙列缺损</span></td> -->
                <!-- 						<td  colspan="4" class="table_width"><div class="div_with placeholder" id="5"></div></td> -->
                <!-- 						<td  colspan="" class="table_width"></td> -->
                <!-- 						<td  colspan="4" class="table_width"></td> -->
                <!-- 					</tr>															 -->
            </table>
        </div>
    </div>
    <div id="tooth" style="overflow:hidden;padding-left: 12%;">
        <!-- 牙位图操作 -->
        <div class="btnsYa">
            <input type="button" id="toothLoose" class="nocurrent_btn" value="牙松动"/>
            <input type="button" id="toothCondition" class="nocurrent_btn" value="口腔情况"/>
            <input type="button" id="imageExamination" class="nocurrent_btn" value="影像学检查"/>
            <input type="button" id="medicalCertificate" class="nocurrent_btn" value="诊断"/>
        </div>
        <!-- 牙位图 -->
        <div class="row toothBox"  id="toothLooseMap" style="min-width:700px;float:left;overflow:hidden;">
            <ul class="upYa" >
                <!-- 	               		<span style="font-size: initial;">全选:</span><li style="float: right;"><input id="alltooth" class="" value="全口" type="checkbox"/></li> -->
                <li><input type="checkbox" class="lefttop" style="margin-top:65px;" /></li>
                <li><span class="yaIcon le8"></span><span class="num" name="adultupYa1" toothname="18">18</span></li>
                <li><span class="yaIcon le7"></span><span class="num" name="adultupYa1" toothname="17">17</span></li>
                <li><span class="yaIcon le6"></span><span class="num" name="adultupYa1" toothname="16">16</span></li>
                <li><span class="yaIcon le5"></span><span class="num" name="adultupYa1" toothname="15">15</span></li>
                <li><span class="yaIcon le4"></span><span class="num" name="adultupYa1" toothname="14">14</span></li>
                <li><span class="yaIcon le3"></span><span class="num" name="adultupYa1" toothname="13">13</span></li>
                <li><span class="yaIcon le2"></span><span class="num" name="adultupYa1" toothname="12">12</span></li>
                <li><span class="yaIcon le1"></span><span class="num" name="adultupYa1" toothname="11">11</span></li>

                <li><span class="yaIcon rg1"></span><span class="num" name="adultupYa2" toothname="21">21</span></li>
                <li><span class="yaIcon rg2"></span><span class="num" name="adultupYa2" toothname="22">22</span></li>
                <li><span class="yaIcon rg3"></span><span class="num" name="adultupYa2" toothname="23">23</span></li>
                <li><span class="yaIcon rg4"></span><span class="num" name="adultupYa2" toothname="24">24</span></li>
                <li><span class="yaIcon rg5"></span><span class="num" name="adultupYa2" toothname="25">25</span></li>
                <li><span class="yaIcon rg6"></span><span class="num" name="adultupYa2" toothname="26">26</span></li>
                <li><span class="yaIcon rg7"></span><span class="num" name="adultupYa2" toothname="27">27</span></li>
                <li><span class="yaIcon rg8"></span><span class="num" name="adultupYa2" toothname="28">28</span></li>
                <li><input type="checkbox" class="righttop" style="margin-top:65px;"/></li>
            </ul>
            <div class="line">
                <span class="left">右</span>
                <span class="right">左</span>
            </div>
            <ul class="downYa">
                <li><input type="checkbox" class="leftdown" style="margin-bottom:20px;"/></li>
                <li><span class="num" name="adultdownYa1" toothname="48">48</span><span class="yaIcon le8"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="47">47</span><span class="yaIcon le7"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="46">46</span><span class="yaIcon le6"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="45">45</span><span class="yaIcon le5"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="44">44</span><span class="yaIcon le4"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="43">43</span><span class="yaIcon le3"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="42">42</span><span class="yaIcon le2"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="41">41</span><span class="yaIcon le1"></span></li>

                <li><span class="num" name="adultdownYa2" toothname="31">31</span><span class="yaIcon rg1"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="32">32</span><span class="yaIcon rg2"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="33">33</span><span class="yaIcon rg3"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="34">34</span><span class="yaIcon rg4"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="35">35</span><span class="yaIcon rg5"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="36">36</span><span class="yaIcon rg6"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="37">37</span><span class="yaIcon rg7"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="38">38</span><span class="yaIcon rg8"></span></li>
                <li><input type="checkbox" class="rightdown" style="margin-bottom:20px;"/></li>
            </ul>
        </div>
        <div class="row toothBox" id="toothConditionMap" style="min-width:700px;float:left; display: none;">
            <ul class="upYa" >
                <!-- 	                	<span style="font-size: initial;">全选:</span><li style="float: right;"><input id="alltooth" class="" value="全口" type="checkbox"/></li> -->
                <li><input type="checkbox" class="lefttop" style="margin-top:65px;" /></li>
                <li><span class="yaIcon le8"></span><span class="num" name="adultupYa1" toothname="18">18</span></li>
                <li><span class="yaIcon le7"></span><span class="num" name="adultupYa1" toothname="17">17</span></li>
                <li><span class="yaIcon le6"></span><span class="num" name="adultupYa1" toothname="16">16</span></li>
                <li><span class="yaIcon le5"></span><span class="num" name="adultupYa1" toothname="15">15</span></li>
                <li><span class="yaIcon le4"></span><span class="num" name="adultupYa1" toothname="14">14</span></li>
                <li><span class="yaIcon le3"></span><span class="num" name="adultupYa1" toothname="13">13</span></li>
                <li><span class="yaIcon le2"></span><span class="num" name="adultupYa1" toothname="12">12</span></li>
                <li><span class="yaIcon le1"></span><span class="num" name="adultupYa1" toothname="11">11</span></li>

                <li><span class="yaIcon rg1"></span><span class="num" name="adultupYa2" toothname="21">21</span></li>
                <li><span class="yaIcon rg2"></span><span class="num" name="adultupYa2" toothname="22">22</span></li>
                <li><span class="yaIcon rg3"></span><span class="num" name="adultupYa2" toothname="23">23</span></li>
                <li><span class="yaIcon rg4"></span><span class="num" name="adultupYa2" toothname="24">24</span></li>
                <li><span class="yaIcon rg5"></span><span class="num" name="adultupYa2" toothname="25">25</span></li>
                <li><span class="yaIcon rg6"></span><span class="num" name="adultupYa2" toothname="26">26</span></li>
                <li><span class="yaIcon rg7"></span><span class="num" name="adultupYa2" toothname="27">27</span></li>
                <li><span class="yaIcon rg8"></span><span class="num" name="adultupYa2" toothname="28">28</span></li>
                <li><input type="checkbox" class="righttop" style="margin-top:65px;"/></li>
            </ul>
            <div class="line">
                <span class="left">右</span>
                <span class="right">左</span>
            </div>
            <ul class="downYa">
                <li><input type="checkbox" class="leftdown" style="margin-bottom:20px;"/></li>
                <li><span class="num" name="adultdownYa1" toothname="48">48</span><span class="yaIcon le8"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="47">47</span><span class="yaIcon le7"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="46">46</span><span class="yaIcon le6"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="45">45</span><span class="yaIcon le5"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="44">44</span><span class="yaIcon le4"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="43">43</span><span class="yaIcon le3"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="42">42</span><span class="yaIcon le2"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="41">41</span><span class="yaIcon le1"></span></li>

                <li><span class="num" name="adultdownYa2" toothname="31">31</span><span class="yaIcon rg1"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="32">32</span><span class="yaIcon rg2"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="33">33</span><span class="yaIcon rg3"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="34">34</span><span class="yaIcon rg4"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="35">35</span><span class="yaIcon rg5"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="36">36</span><span class="yaIcon rg6"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="37">37</span><span class="yaIcon rg7"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="38">38</span><span class="yaIcon rg8"></span></li>
                <li><input type="checkbox" class="rightdown" style="margin-bottom:20px;"/></li>
            </ul>
        </div>
        <div class="row toothBox" id="imageExaminationMap" style="min-width:700px;float:left; display: none;">
            <ul class="upYa" >
                <!-- 	                	<span style="font-size: initial;">全选:</span><li style="float: right;"><input id="alltooth" class=""  value="全口" type="checkbox"/></li> -->
                <li><input type="checkbox" class="lefttop" style="margin-top:65px;" /></li>
                <li><span class="yaIcon le8"></span><span class="num" name="adultupYa1" toothname="18">18</span></li>
                <li><span class="yaIcon le7"></span><span class="num" name="adultupYa1" toothname="17">17</span></li>
                <li><span class="yaIcon le6"></span><span class="num" name="adultupYa1" toothname="16">16</span></li>
                <li><span class="yaIcon le5"></span><span class="num" name="adultupYa1" toothname="15">15</span></li>
                <li><span class="yaIcon le4"></span><span class="num" name="adultupYa1" toothname="14">14</span></li>
                <li><span class="yaIcon le3"></span><span class="num" name="adultupYa1" toothname="13">13</span></li>
                <li><span class="yaIcon le2"></span><span class="num" name="adultupYa1" toothname="12">12</span></li>
                <li><span class="yaIcon le1"></span><span class="num" name="adultupYa1" toothname="11">11</span></li>

                <li><span class="yaIcon rg1"></span><span class="num" name="adultupYa2" toothname="21">21</span></li>
                <li><span class="yaIcon rg2"></span><span class="num" name="adultupYa2" toothname="22">22</span></li>
                <li><span class="yaIcon rg3"></span><span class="num" name="adultupYa2" toothname="23">23</span></li>
                <li><span class="yaIcon rg4"></span><span class="num" name="adultupYa2" toothname="24">24</span></li>
                <li><span class="yaIcon rg5"></span><span class="num" name="adultupYa2" toothname="25">25</span></li>
                <li><span class="yaIcon rg6"></span><span class="num" name="adultupYa2" toothname="26">26</span></li>
                <li><span class="yaIcon rg7"></span><span class="num" name="adultupYa2" toothname="27">27</span></li>
                <li><span class="yaIcon rg8"></span><span class="num" name="adultupYa2" toothname="28">28</span></li>
                <li><input type="checkbox" class="righttop" style="margin-top:65px;"/></li>
            </ul>
            <div class="line">
                <span class="left">右</span>
                <span class="right">左</span>
            </div>
            <ul class="downYa">
                <li><input type="checkbox" class="leftdown" style="margin-bottom:20px;"/></li>
                <li><span class="num" name="adultdownYa1" toothname="48">48</span><span class="yaIcon le8"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="47">47</span><span class="yaIcon le7"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="46">46</span><span class="yaIcon le6"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="45">45</span><span class="yaIcon le5"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="44">44</span><span class="yaIcon le4"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="43">43</span><span class="yaIcon le3"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="42">42</span><span class="yaIcon le2"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="41">41</span><span class="yaIcon le1"></span></li>

                <li><span class="num" name="adultdownYa2" toothname="31">31</span><span class="yaIcon rg1"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="32">32</span><span class="yaIcon rg2"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="33">33</span><span class="yaIcon rg3"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="34">34</span><span class="yaIcon rg4"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="35">35</span><span class="yaIcon rg5"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="36">36</span><span class="yaIcon rg6"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="37">37</span><span class="yaIcon rg7"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="38">38</span><span class="yaIcon rg8"></span></li>
                <li><input type="checkbox" class="rightdown" style="margin-bottom:20px;"/></li>
            </ul>
        </div>
        <div class="row toothBox" id="medicalCertificateMap" style="min-width:700px;float:left; display: none;">
            <ul class="upYa" >
                <!-- 	                	<span style="font-size: initial;">全选:</span><li style="float: right;"><input id="alltooth" class=""  value="全口" type="checkbox"/></li> -->
                <li><input type="checkbox" class="lefttop" style="margin-top:65px;" /></li>
                <li><span class="yaIcon le8"></span><span class="num" name="adultupYa1" toothname="18">18</span></li>
                <li><span class="yaIcon le7"></span><span class="num" name="adultupYa1" toothname="17">17</span></li>
                <li><span class="yaIcon le6"></span><span class="num" name="adultupYa1" toothname="16">16</span></li>
                <li><span class="yaIcon le5"></span><span class="num" name="adultupYa1" toothname="15">15</span></li>
                <li><span class="yaIcon le4"></span><span class="num" name="adultupYa1" toothname="14">14</span></li>
                <li><span class="yaIcon le3"></span><span class="num" name="adultupYa1" toothname="13">13</span></li>
                <li><span class="yaIcon le2"></span><span class="num" name="adultupYa1" toothname="12">12</span></li>
                <li><span class="yaIcon le1"></span><span class="num" name="adultupYa1" toothname="11">11</span></li>

                <li><span class="yaIcon rg1"></span><span class="num" name="adultupYa2" toothname="21">21</span></li>
                <li><span class="yaIcon rg2"></span><span class="num" name="adultupYa2" toothname="22">22</span></li>
                <li><span class="yaIcon rg3"></span><span class="num" name="adultupYa2" toothname="23">23</span></li>
                <li><span class="yaIcon rg4"></span><span class="num" name="adultupYa2" toothname="24">24</span></li>
                <li><span class="yaIcon rg5"></span><span class="num" name="adultupYa2" toothname="25">25</span></li>
                <li><span class="yaIcon rg6"></span><span class="num" name="adultupYa2" toothname="26">26</span></li>
                <li><span class="yaIcon rg7"></span><span class="num" name="adultupYa2" toothname="27">27</span></li>
                <li><span class="yaIcon rg8"></span><span class="num" name="adultupYa2" toothname="28">28</span></li>
                <li><input type="checkbox" class="righttop" style="margin-top:65px;"/></li>
            </ul>
            <div class="line">
                <span class="left">右</span>
                <span class="right">左</span>
            </div>
            <ul class="downYa">
                <li><input type="checkbox" class="leftdown" style="margin-bottom:20px;"/></li>
                <li><span class="num" name="adultdownYa1" toothname="48">48</span><span class="yaIcon le8"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="47">47</span><span class="yaIcon le7"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="46">46</span><span class="yaIcon le6"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="45">45</span><span class="yaIcon le5"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="44">44</span><span class="yaIcon le4"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="43">43</span><span class="yaIcon le3"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="42">42</span><span class="yaIcon le2"></span></li>
                <li><span class="num" name="adultdownYa1" toothname="41">41</span><span class="yaIcon le1"></span></li>

                <li><span class="num" name="adultdownYa2" toothname="31">31</span><span class="yaIcon rg1"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="32">32</span><span class="yaIcon rg2"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="33">33</span><span class="yaIcon rg3"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="34">34</span><span class="yaIcon rg4"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="35">35</span><span class="yaIcon rg5"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="36">36</span><span class="yaIcon rg6"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="37">37</span><span class="yaIcon rg7"></span></li>
                <li><span class="num" name="adultdownYa2" toothname="38">38</span><span class="yaIcon rg8"></span></li>
                <li><input type="checkbox" class="rightdown" style="margin-bottom:20px;"/></li>
            </ul>
        </div>
    </div>
    <div class="row toothBitmap">
        <div class="col-md-12 col-sm-12 colDefined">
            <span class="smalltitle">口腔情况牙位简图</span>
        </div>
        <div class="col-md-12 col-sm-12 colDefined">
            <table id="toothConditionBoxMap" border="1" width="100%">
                <tr>
                    <td><span id="18">18</span></td>
                    <td><span id="17">17</span></td>
                    <td><span id="16">16</span></td>
                    <td><span id="15">15</span></td>
                    <td><span id="14">14</span></td>
                    <td><span id="13">13</span></td>
                    <td><span id="12">12</span></td>
                    <td><span id="11">11</span></td>
                    <td><span id="21">21</span></td>
                    <td><span id="22">22</span></td>
                    <td><span id="23">23</span></td>
                    <td><span id="24">24</span></td>
                    <td><span id="25">25</span></td>
                    <td><span id="26">26</span></td>
                    <td><span id="27">27</span></td>
                    <td><span id="28">28</span></td>
                </tr>
                <tr>
                    <td><span id="48">48</span></td>
                    <td><span id="47">47</span></td>
                    <td><span id="46">46</span></td>
                    <td><span id="45">45</span></td>
                    <td><span id="44">44</span></td>
                    <td><span id="43">43</span></td>
                    <td><span id="42">42</span></td>
                    <td><span id="41">41</span></td>
                    <td><span id="31">31</span></td>
                    <td><span id="32">32</span></td>
                    <td><span id="33">33</span></td>
                    <td><span id="34">34</span></td>
                    <td><span id="35">35</span></td>
                    <td><span id="36">36</span></td>
                    <td><span id="37">37</span></td>
                    <td><span id="38">38</span></td>
                </tr>
            </table>
        </div>
    </div>
    <!-- 整体口腔情况明细-->
    <div class="row content">
        <div class="col-md-12 col-sm-12 colDefined">
            <span class="smalltitle">整体口腔情况明细</span>
            <table id="toothConditionBox" class="contentItem" border="1" width="100%">
            </table>
        </div>
        <!-- 影像学检查 -->
        <div class="row content">
            <div class="col-md-12 col-sm-12 colDefined">
                <span class="smalltitle">影像学检查</span>
                <table id="imageExaminationBox" class="contentItem contentItem2" border="1" width="100%">
                    <tr>
                        <td class="table_width2"><div class='div_with2 placeholder' id="yxx14"></div></td>
                        <td colspan="3" class="problemitem table_width3"><span class="" id="yxx14">牙槽嵴顶距上颌窦底 </span><input class="border_bottom" id="saprodontia" type="text" onblur="TextLengthCheck(this.id,2);"/>mm</td>
                        <td class="table_width2"><div class='div_with2 placeholder' id="yxx15"></div></td>
                        <td colspan="2" class="problemitem table_width3"><span class="" id="yxx15">距下牙槽神经管 </span><input class="border_bottom" id="residualcrown" type="text" onblur="TextLengthCheck(this.id,2);"/>mm</td>
                    </tr>
                    <tr>
                        <td class="problemitem"><span class="">上颌窦情况</span></td>
                        <td colspan="8">
                            <label><input class="" name="pulpitis" type="radio" value="0" onclick="choose(this.name)"/><span class=" ">正常 </span></label>
                            <label><input class="" name="pulpitis" type="radio" value="1" onclick="choose(this.name)"/><span class=" ">异常 </span></label><input class="border_bottom pulpitisinput" id="teethtilted" disabled type="text" onblur="TextLengthCheck(this.id,30);" style="width: 50%;cursor: no-drop;"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" class="table_width2"><div class='div_with2 placeholder' id="yxx16"></div></td>
                        <td colspan="2" class="problemitem">
                            <span class="" id="yxx16">种植区域骨量</span>
                        </td>
                        <td colspan="2">
                            <label><input type="radio" name='nub' value="不足"/><span class="">不足</span></label>
                            <label><input type="radio" name='nub' value="充足"/><span class="">充分 </span></label>
                        </td>
                    </tr>
                    <tr>
                        <td rowspan="4" class="problemitem"><span class="">种植区域骨质 </span></td></td>
                        <td colspan="4" class="table_width2">
                            <div class='placeholder div_with3' id="yxx17" class="div_with3"></div>
                        </td>
                        <td colspan="" class="table_width2 problemitem">
                            <span class=""  id="yxx17">Ⅰ类骨</span>
                        </td>

                    </tr>
                    <tr>
                        <td colspan="4" class="table_width2">
                            <div class='placeholder div_with3' id="yxx18" class="div_with3"></div>
                        </td>
                        <td colspan="" class="table_width2 problemitem" style="width: 30%">
                            <span class="" id="yxx18">Ⅱ类骨</span>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4" class="table_width2">
                            <div class='placeholder div_with3' id="yxx19" class="div_with3"></div>
                        </td>
                        <td colspan="" class="table_width2 problemitem">
                            <span class="" id="yxx19">Ⅲ类骨</span>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4" class="table_width2">
                            <div class='placeholder div_with3' id="yxx20" class="div_with3"></div>
                        </td>
                        <td colspan="" class="table_width2 problemitem">
                            <span class="" id="yxx20">Ⅳ类骨 </span>
                        </td>

                    </tr>
                </table>
            </div>
        </div>
        <!-- 诊断 -->
        <div class="row content">
            <div class="col-md-12 col-sm-12 colDefined">
                <span class="smalltitle">诊断</span>
                <table id="medicalCertificateBox" class="contentItem contentItem2" border="1" width="100%">
                </table>
            </div>
        </div>
        <div class="row toothBitmap" id="toothBitmap" style="display:block;">
            <div class="col-md-12 col-sm-12 colDefined">
                <span class="smalltitle">口腔情况牙位简图</span>
            </div>
            <iframe src="<%=contextPath%>/static/css/kqdsFront/inner.svg" width="100%" height="800"></iframe>
        </div>
    </div>
    <!--endprint-->
    <!-- 按钮 -->
    <div class="btns">
        <button id="consent_saveBtn" onclick="save()">保存</button>
        <button id="consent_updateBtn" style="display: none;" class="consent_updateBtn" onclick="update()">修改表单</button>
        <button id="print_Btn" onclick="myPreviewAll()" style="/*visibility: hidden;*/">打印本页内容</button>
    </div>

</body>
	<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
	<script language="javascript"  src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
	<script type="text/javascript">
		var contextPath = "<%=contextPath%>";
		var conditionData;
		var certificateData;
		var patientInformation=window.parent.consultSelectPatient; //选中患者信息
		var usercode=patientInformation.usercode; //选中患者usercode
		var idlclj= patientInformation.seqid;	//选中患者临床路径id
		var order_number= patientInformation.orderNumber; //选中患者order_number
		var updataid="";
		$(function(){
			getUser(usercode);//获取患者信息并赋值
			gettionData();//获取各板块问题详情
			pro("toothConditionBox",conditionData);
			pro("medicalCertificateBox",certificateData);
			initData();
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
//	 	        	 console.log(JSON.stringify(r)+'------------patient_name');
		             $("#patient_first_diagnose").text(r.cztime);
		             $("#patient_num").text(r.usercode);
		             $("#patient_num").attr("seqId", r.seqId);
		             $("#patient_name").text(r.username);
		             $("#patient_sex").text(r.sex);
		             $("#patient_age").text(r.age);
		             $("#patient_ID").text(r.idcardno);
		             $("#patient_bone").text(r.birthday);
		             $("#patient_tel").text(r.phonenumber1);
//	 	             $("#patient_emergency_contact").attr("value", r.emergencyContact);
//	 	             $("#emergency_contact_tel").attr("value", r.emergencyPhone);
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
// 	获取各板块问题
	function gettionData(){
		var url = contextPath + "/YZDictAct/getDiseaseByCode.act?id=bqfl67&code=zdqk594";
	    $.axse(url, null,
	    function(data) {
	    	 conditionData=data.conditionData;
	    	 certificateData=data.certificateData;
	    },
	    function() {
	        layer.alert('查询出错！' );
	    });
	}

	// 各项目问题内容的赋值
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
			})
			$("#"+obj).find("tbody tr").find("td:even").each(function(i,obj){
				$(this).html("<div class='div_with placeholder' id="+data[i].dictCode+"></div>");
			})
		}else{
			$("#"+obj).find("tbody tr").find("td:odd").not("td:last").each(function(i,obj){
				$(this).html("<span class='' id="+data[i].dictCode+">"+data[i].dictName+"</span>");
			})
			$("#"+obj).find("tbody tr").find("td:even").not("td:last").each(function(i,obj){
				$(this).html("<div class='div_with placeholder' id="+data[i].dictCode+"></div>");
			})
		}
	}
// 添加问题牙齿时（图）赋值---选中状态（红圈）
	function checkedTooth(projectId,one){
		var tooth=$("#"+projectId+"Map").find("ul li span.num").text();
    	var toothArr=[];
		var checkedArr=[];
    	for(var i=0;i<tooth.length;i+=2){
    		toothArr.push(tooth.slice(i,i+2));
    	}
    	checkedArr.push(one);
    	for(j=0;j<toothArr.length;j++){
			for(var i=0;i<checkedArr.length;i++){
				if(checkedArr[i]!=toothArr[j]){
					$("#"+projectId+"Map").find("ul li span[toothname="+toothArr[j]+"]").removeClass("checked_current");
					$("#"+projectId+"BoxMap").find("tr td span[id="+toothArr[j]+"]").removeClass("checked_current");
				}
			}
		}
    	if(projectId=="toothLoose"){//牙松动布局不同
    		 $("."+projectId+"NextBox").nextAll().find("td:odd").each(function(j,el){
    			 var thi=this;
    			 var span=$(thi).find("div").find("span");
    			 if(span.length>0){
    					$(thi).find("div").find("span").each(function(k,e){
    						checkedArr.push($(this).attr("id"));
    							for(j=0;j<toothArr.length;j++){
    								for(var i=0;i<checkedArr.length;i++){
    									if(checkedArr[i]==toothArr[j]){
    										$("#"+projectId+"Map").find("ul li span[toothname="+toothArr[j]+"]").addClass("checked_current");
    									}
    								}
    							}
    					})
    			 }
    		 })
    	}else{//牙松动布局相同的
    		 $("#"+projectId+"Box").find("tr td:even").each(function(j,el){
    			 var thi=this;
    			 var span=$(thi).find("div").find("span");
    			 if(span.length>0){
    					$(thi).find("div").find("span").each(function(k,e){
    						checkedArr.push($(this).attr("id"));
    							for(j=0;j<toothArr.length;j++){
    								for(var i=0;i<checkedArr.length;i++){
    									if(checkedArr[i]==toothArr[j]){
    										$("#"+projectId+"Map").find("ul li span[toothname="+toothArr[j]+"]").addClass("checked_current");
    										$("#"+projectId+"BoxMap").find("tr td span[id="+toothArr[j]+"]").addClass("checked_current");
    									}
    								}
    							}
    					})
    			 }
    		 })
    	}
	}
// 牙位图start========================================================================
// 		选择项目projectId
		var projectId='';
		var projectName='';
 		$('.btnsYa').on('click', 'input',function() {
			$(this).siblings().removeClass('current_btn');
			$(this).siblings().addClass('nocurrent_btn');
			$(this).removeClass('nocurrent_btn');
 			$(this).addClass('current_btn');
 			if($(this).hasClass("current_btn")){
 				projectId=$(this).attr("id");
 				projectName=$(this).val();
//  				lastIndex=$(this).index();
            if(projectId=='toothLoose'){//1
                $("#toothLooseMap").css("display","block");
                $("#toothLooseMap").siblings(".toothBox").css("display","none");
                checkedTooth(projectId);
            }else if(projectId=='toothCondition'){//2
                $("#toothConditionMap").css("display","block");
                $("#toothConditionMap").siblings(".toothBox").css("display","none");
                checkedTooth(projectId);
            }else if(projectId=='imageExamination'){//3
                $("#imageExaminationMap").css("display","block");
                $("#imageExaminationMap").siblings(".toothBox").css("display","none");
                checkedTooth(projectId);
            }else if(projectId=='medicalCertificate'){//4
                $("#medicalCertificateMap").css("display","block");
                $("#medicalCertificateMap").siblings(".toothBox").css("display","none");
                checkedTooth(projectId);
            }
        }else{}
    })
    //点击牙齿操作
    var toothNum;
    var toothMore;
    $('.toothBox').on('click', 'li',
        function() {
            if(!projectId){
                layer.alert("请选择要添加的牙位项目！");
                return false;
            }
            toothNumid=$(this).find("input").attr("class");
            if(toothNumid=="lefttop"){//上下左右四个方位
                toothNum="11-18";
                toothMore=$(this).nextAll().find('span[name="adultupYa1"]').text();
            }else if(toothNumid=="righttop"){
                toothNum="21-28";
                toothMore=$(this).prevAll().find('span[name="adultupYa2"]').text();
            }else if(toothNumid=="leftdown"){
                toothNum="41-48";
                toothMore=$(this).nextAll().find('span[name="adultdownYa1"]').text();
            }else if(toothNumid=="rightdown"){
                toothNum="31-38";
                toothMore=$(this).prevAll().find('span[name="adultdownYa2"]').text();
            }else if(toothNumid=="alltooth"){//全口
                toothNum=$(this).find("input").val();
                toothMore=$(this).parents("#"+projectId+"Map").find('ul li').find('span[class="num"]').text();
            }else{//普通单个牙位
                toothNum=$(this).find(".num").text();
                toothMore=$(this).find(".num").text();
            }
            layer.open({
                type: 2,
                title: projectName,
                area: ['55%', '30%'],
                content: contextPath+'/ZzblViewAct/toProject.act?id='+projectId+'&projectName='+projectName+'&toothNum='+toothNum+'&toothMore='+toothMore
            })
        })
    // 牙位图end========================================================================

    // 	口腔治疗经历---多选拼接在一起以","分隔
    function oralTreatmentExperience() {
        var obj = $("#oralSpecialtyExamination").find("input[name='oral_treatment_experience']");
        var oral_treatment_experience = "";
        for ( k in obj ) {
            if(obj[k].checked) {
                oral_treatment_experience = oral_treatment_experience + obj[k].value + ',';
            }
        }
        return oral_treatment_experience;
    }
    // 复选框存值
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
    //牙松动牙位取值
    function saveToothLoose(){
        var toothLooseItem={};
        $(".toothLooseNextBox").nextAll().find("td:even").each(function(j,el){
            var item=$(this).find("span").attr("id");
            var itemTooth=$(this).next().find("div");
            if(item!=undefined){
                var checkedtoothStr=itemTooth.text();//获取牙位
                toothLooseItem[item]=checkedtoothStr;
            }
        })
        return toothLooseItem;
    }
    //整体情况~诊断~影像学检查=牙位取值
    function saveTooth(obj){
        var ToothItem={};
        $("#"+obj).find("tr td:odd").each(function(j,el){
            var item=$(this).find("span").attr("id");
            var itemTooth=$(this).prev().find("div");
            if(item!=undefined){
                var checkedtoothStr=itemTooth.text(); //获取牙位
                ToothItem[item]=checkedtoothStr;
            }
        })
        return ToothItem;
    }
    //    保存方法
    function save() {
// 	   基本信息seqid
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
        var gumtypes=$("#oralSpecialtyExamination").find("input[name='gumtypes']:checked").val();//牙龈生物学类型
        var undergo=inputCheckedSave("undergo"); //口腔治疗经历
        var periodontalcondition=inputCheckedSave("periodontalcondition");//牙周情况
        var mucosalsituation=inputCheckedSave("mucosalsituation"); //粘膜情况
// 牙松动
        var toothLoose=saveToothLoose();//牙位
// 整体口腔情况明细
        var toothCondition=saveTooth("toothConditionBox");//牙位

// 影像学检查
        var imageExamination=saveTooth("imageExaminationBox");//牙位
        var saprodontia = $("#saprodontia").val();//牙槽嵴顶距上颌窦底
        var residualcrown = $("#residualcrown").val();//距下牙槽神经管
        var pulpitis = $("#imageExaminationBox").find("input[name='pulpitis']:checked").val();//上颌窦情况是否正常
        var teethtilted=$("#teethtilted").val();//上颌窦情况异常情况
        var nub = $("#imageExaminationBox").find("input[name='nub']:checked").val();//种植区域骨量是否正常
// 诊断
        var medicalCertificate=saveTooth("medicalCertificateBox"); //牙位
        toothLoose = JSON.stringify(toothLoose);
        toothCondition = JSON.stringify(toothCondition);
        imageExamination = JSON.stringify(imageExamination);
        medicalCertificate = JSON.stringify(medicalCertificate);
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
            gumtypes : gumtypes,
            undergo : undergo,
            periodontalcondition : periodontalcondition,
            mucosalsituation : mucosalsituation,
// 牙松动
            onedu : toothLoose,
// 整体口腔情况明细
            dentitiondefect : toothCondition,
// 影像学检查
            defectdentition : imageExamination,
            saprodontia : saprodontia,
            residualcrown : residualcrown,
            pulpitis : pulpitis,
            teethtilted : teethtilted,
            nub : nub,
// 诊断
            defectiverepair : medicalCertificate
        };
// 	    console.log(JSON.stringify(param)+"---------param");
// 	    return;
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
// 	   基本信息seqid
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
        var gumtypes=$("#oralSpecialtyExamination").find("input[name='gumtypes']:checked").val();//牙龈生物学类型
        var undergo=inputCheckedSave("undergo"); //口腔治疗经历
        var periodontalcondition=inputCheckedSave("periodontalcondition");//牙周情况
        var mucosalsituation=inputCheckedSave("mucosalsituation"); //粘膜情况
// 牙松动
        var toothLoose=saveToothLoose();//牙位
// 整体口腔情况明细
        var toothCondition=saveTooth("toothConditionBox");//牙位

// 影像学检查
        var imageExamination=saveTooth("imageExaminationBox");//牙位
        var saprodontia = $("#saprodontia").val();//牙槽嵴顶距上颌窦底
        var residualcrown = $("#residualcrown").val();//距下牙槽神经管
        var pulpitis = $("#imageExaminationBox").find("input[name='pulpitis']:checked").val();//上颌窦情况是否正常
        var teethtilted=$("#teethtilted").val();//上颌窦情况异常情况
        var nub = $("#imageExaminationBox").find("input[name='nub']:checked").val();//种植区域骨量是否正常
// 诊断
        var medicalCertificate=saveTooth("medicalCertificateBox"); //牙位
        toothLoose = JSON.stringify(toothLoose);
        toothCondition = JSON.stringify(toothCondition);
        imageExamination = JSON.stringify(imageExamination);
        medicalCertificate = JSON.stringify(medicalCertificate);
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
            gumtypes : gumtypes,
            undergo : undergo,
            periodontalcondition : periodontalcondition,
            mucosalsituation : mucosalsituation,
// 牙松动
            onedu : toothLoose,
// 整体口腔情况明细
            dentitiondefect : toothCondition,
// 影像学检查
            defectdentition : imageExamination,
            saprodontia : saprodontia,
            residualcrown : residualcrown,
            pulpitis : pulpitis,
            teethtilted : teethtilted,
            nub : nub,
// 诊断
            defectiverepair : medicalCertificate
        };
// 	    console.log(JSON.stringify(param)+"---------param");
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
                    layer.alert('修改失败'  );
                }
            },
            function() {
                layer.alert('修改失败' );
            });
    }
    // 	返回数据赋值
    function initData(){
        var url =  contextPath + '/HUDH_MedicalRecordsAct/selectdata.act';
        $.ajax({
            url: url,
// 				type:"POST",
            dataType:"json",
            data : {
                lcljId:idlclj
            },
            success:function(result){
                var res=result[0];
                updataid=res.seqId;
// 					console.log(JSON.stringify(res)+"-----rr");
                if(result.length>0){
                    $("#consent_saveBtn").css("display","none");//隐藏保存按钮
                    $("#consent_updateBtn").css("display","inline-block");//显示修改按钮
                    for(var i in res){
                        $("input[name="+i+"]").each(function(){
                            var that=this;
                            var split = res[i].split(",");
                            for(var j = 0; j < split.length; j++){
                                if($(that).val()==split[j]){
                                    $(that).attr("checked","checked");
                                }
                            }
                        })//复选框及单选框赋值
                        $("#"+i+"[type='text']").attr("value",res[i]);// 填框赋值
                    }
// 	 					牙位赋值start
                    if(res[i]="onedu"){
                        var toothLoose=res.onedu;
                        var table_pro=$(".toothLooseNextBox").nextAll().find("td:even");
                        table_pro.each(function(j,el){
                            var $table_span=$(this).next().find("div");//赋值的div框
                            var tabletooth=$(this).find("span").attr("id");	//要赋值的item
                            for(var key in toothLoose){
                                var toothLoosestr=toothLoose[key];
                                if(toothLoose[key]!=""){
                                    //去掉字符串最后一个逗号
                                    toothLoosestr = (toothLoosestr.substring(toothLoosestr.length - 1) == ',') ? toothLoosestr.substring(0, toothLoosestr.length - 1) : toothLoosestr;
                                    var toothLooseArr = toothLoosestr.split(",");
//	 									console.log(toothLooseArr+"-----toothLooseArr");
                                    if(tabletooth!=undefined&&tabletooth==key){
                                        for(var j=0;j<toothLooseArr.length;j++){
                                            $table_span.append('<span id='+toothLooseArr[j]+' itmid='+key+'>'+toothLooseArr[j]+","+'</span>');
                                        }
                                    }
                                }
                            }
                        })
                    }
                    if(res[i]="dentitiondefect"){
                        var dentitiondefect=res.dentitiondefect;
                        var table_pro=$("#toothConditionBox").find("td:odd");
                        table_pro.each(function(j,el){
                            var $table_span=$(this).prev().find("div");//赋值的div框
                            var tabletooth=$(this).find("span").attr("id");	//要赋值的item
                            for(var key in dentitiondefect){
                                var dentitiondefectstr=dentitiondefect[key];
                                if(dentitiondefect[key]!=""){
                                    //去掉字符串最后一个逗号
                                    dentitiondefectstr = (dentitiondefectstr.substring(dentitiondefectstr.length - 1) == ',') ? dentitiondefectstr.substring(0, dentitiondefectstr.length - 1) : dentitiondefectstr;
                                    var dentitiondefectArr = dentitiondefectstr.split(",");
                                    if(tabletooth!=undefined&&tabletooth==key){
                                        for(var j=0;j<dentitiondefectArr.length;j++){
                                            $table_span.append('<span id='+dentitiondefectArr[j]+' itmid='+key+'>'+dentitiondefectArr[j]+","+'</span>');
                                        }
                                    }
                                }
                            }
                        })
                    }
                    if(res[i]="defectdentition"){
                        var defectdentition=res.defectdentition;
                        var table_pro=$("#imageExaminationBox").find("td:odd");
                        table_pro.each(function(j,el){
                            var $table_span=$(this).prev().find("div");//赋值的div框
                            var tabletooth=$(this).find("span").attr("id");	//要赋值的item
                            for(var key in defectdentition){
                                var defectdentitionstr=defectdentition[key];
                                if(defectdentition[key]!=""){
                                    //去掉字符串最后一个逗号
                                    defectdentitionstr = (defectdentitionstr.substring(defectdentitionstr.length - 1) == ',') ? defectdentitionstr.substring(0, defectdentitionstr.length - 1) : defectdentitionstr;
                                    var defectdentitionArr = defectdentitionstr.split(",");
                                    if(tabletooth!=undefined&&tabletooth==key){
                                        for(var j=0;j<defectdentitionArr.length;j++){
                                            $table_span.append('<span id='+defectdentitionArr[j]+' itmid='+key+'>'+defectdentitionArr[j]+","+'</span>');
                                        }
                                    }
                                }
                            }
                        })

                    }
                    if(res[i]="defectiverepair"){
                        var defectiverepair=res.defectiverepair;
                        var table_pro=$("#medicalCertificateBox").find("td:odd");
                        table_pro.each(function(j,el){
                            var $table_span=$(this).prev().find("div");//赋值的div框
                            var tabletooth=$(this).find("span").attr("id");	//要赋值的item
                            for(var key in defectiverepair){
                                var defectiverepairstr=defectiverepair[key];
                                if(defectiverepair[key]!=""){
                                    //去掉字符串最后一个逗号
                                    defectiverepairstr = (defectiverepairstr.substring(defectiverepairstr.length - 1) == ',') ? defectiverepairstr.substring(0, defectiverepairstr.length - 1) : defectiverepairstr;
                                    var defectiverepairArr = defectiverepairstr.split(",");
                                    if(tabletooth!=undefined&&tabletooth==key){
                                        for(var j=0;j<defectiverepairArr.length;j++){
                                            $table_span.append('<span id='+defectiverepairArr[j]+' itmid='+key+'>'+defectiverepairArr[j]+","+'</span>');
                                        }
                                    }
                                }
                            }
                        })
                    }
// 	 					牙位赋值end
                }

            }
        });
    }

    //打印方法
    function myPreviewAll() {
        bdhtml=window.document.body.innerHTML;
        sprnstr="<!--startprint-->";
        eprnstr="<!--endprint-->";
        prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
        prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
        var htmlStyle="<style>button{display:none;}input{border:none;}.toothBitmap{display:none}#tooth{display:none}.content .colDefined .contentItem tr td input:not([type='text']){width:10px}";
        htmlStyle+=".content .colDefined .contentItem tr{height:20px}.problemitem{font-size:10px;font-weight:normal;}#toothConditionBoxMap2{font-size:10px!important}.placeholder:empty:before{content:' ';}</style>"
        window.document.body.innerHTML=prnhtml+htmlStyle;
        window.print();  //打印
        window.document.body.innerHTML=bdhtml; // 恢复页面
// 			    window.location.reload();
    }

</script>

</html>