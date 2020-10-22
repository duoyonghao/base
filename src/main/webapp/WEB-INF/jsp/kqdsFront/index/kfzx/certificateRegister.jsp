<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/10/11
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
    String contextPath = request.getContextPath();
    if (contextPath.equals("")) {
        contextPath = "/kqds";
    }
    YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8" />
    <title>患者信息补录</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/addVisting.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/hzjd/hzjd.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
    <style>
        .searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {
            width: 200px;
        }
        .searchSelect>.btn {
            width: 200px;
            height:28px;
            border: solid 1px #e5e5e5;
        }
        .sel3:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {
            width: 110px;
        }
        .sel3>.btn {
            width: 110px;
            height:28px;
            border: solid 1px #e5e5e5;
        }
        .bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
            color: #999;
            height: 28px;
        }
        .pull-left {
            float: left !important;
            color: #333;
        }
        .btn-group > .btn:first-child:hover {
            background: white;
        }
        .provinceReg i.form-control-feedback {
            right: -30px;
        }
    </style>
</head>

<body>

<div class="containerNetEdit">	<!-- .containerNetEdit网电建档容器样式 -->
    <form id="defaultForm" style="padding-bottom:30px;">
        <table class="tableContent">	<!--tableContent 布局样式  -->
            <tr>
                <td>	<!-- .impText 必填信息样式-->
                    <span class="impText">所属门诊*</span>
                </td>
                <td>
                    <select id="organization" name="organization" disabled="disabled">
                    </select>
                </td>
            </tr>
            <tr class="addVisitingDialog-content">	<!-- .addVisitingDialog-content 本身无样式 与星星功能有关 -->
                <td>	<!-- .impText 必填信息样式-->
                    <span class="impText">患者编号*</span>
                </td>
                <td>
                    <div class="form-group">	<!-- form-group与表单验证有关 -->
                        <input type="text" name="usercode" id="usercode" readonly="readonly">
                        <input type="hidden" name="seqId" id="seqId">
                    </div>
                </td>
                <td>
                    <span class="impText">姓名*</span>
                </td>
                <td>
                    <div class="form-group">	<!-- form-group与表单验证有关 -->
                        <input type="text" name="username" id="username" placeholder="请输入姓名"  readonly="readonly">
                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <span class="impText">性别*</span>
                </td>
                <td>
                    <div class="form-group sexReg">	<!--.sexReg性别需要调整的样式  -->
                        <input id="maleId" type="radio" name="sex" value="男"><label for="maleId" class="sexValue">男</label>
                        <input id="femaleId" type="radio" name="sex" value="女"><label for="femaleId" class="sexValue">女</label>
                    </div>
                </td>
                <td>
                    <span class="comText impText">年 龄*</span>
                </td>
                <td>
                    <input type="text" id="age" name="age" onclick="changeAge();" placeholder="请输入真实年龄" />
                </td>
            </tr>

            <tr>
                <td>
                    <span class="impText">电话1*</span>
                </td>
                <td>
                    <div class="form-group">	<!-- form-group与表单验证有关 -->
                        <select class="sel_short_inp_long" id="familyship" name="familyship">		<!--.sel_short_inp_long  select元素与input组合时的样式 -->
                            <option value="本人">本人</option>
                            <option value="家人">家人</option>
                        </select>			<!--.sel_short_inp_long  select元素与input组合时的样式 -->
                        <input class="sel_short_inp_long" type="text" id="phonenumber1" name="phonenumber1" maxlength="11" placeholder="请输入11位电话号码" >
                    </div>
                </td>
                <td>
                    <span class="comText">电话2</span>
                </td>
                <td>
                    <div class="form-group">
                        <select class="sel_short_inp_long" id = "familyship2">
                            <option>本人</option>
                            <option>家人</option>
                        </select>
                        <input class="sel_short_inp_long" type="text" id="phonenumber2" name="phonenumber2" maxlength="11" placeholder="请输入11位电话号码" >
                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <span class="comText">出生年月</span>
                </td>
                <td class="relative">
                    <div class="form-group">
                        <input size="12" type="text" name="birthday" id="birthday" value="" placeholder="请选择出生年月"  class="birthDate" onchange="changeAge();" />
                    </div>
                </td>
                <td>
                    <span class="comText">身份证号</span>
                </td>
                <td>
                    <div class="form-group">
                        <input type="text" name="idcardno" id="idcardno" placeholder="身份证号" maxlength="18">
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <span class="comText">紧急联系人</span>
                </td>
                <td>
                    <div class="form-group">
                        <input type="text" name="emergencyContact" id="emergencyContact" placeholder="紧急联系人">
                    </div>
                </td>
                <td>
                    <span class="comText">紧急联系人电话</span>
                </td>
                <td>
                    <div class="form-group">
                        <input class="sel_short_inp_long" type="text" name="emergencyPhone" id="emergencyPhone" placeholder="请输入11位电话号码" maxlength="11" oninput="value=value.replace(/[^\d]/g,'')">
                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <span class="impText">地址信息*</span>
                </td>
                <td colspan="3">
                    <div class="form-group provinceReg">
                        <select class="sel3 searchSelect" id="province" name="province" onchange="initCity()" data-live-search="true" title="请选择"></select>
                        <select class="sel3 searchSelect" id="city" name="city" onchange="initArea()" data-live-search="true" title="请选择"></select>
                        <select class="sel3 searchSelect" id="area" name="area" onchange="initStreet()" data-live-search="true" title="请选择"></select>
                        <select class="sel3 searchSelect" id="town" name="town" data-live-search="true" title="请选择"></select>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <span class="comText">详细地址</span>
                </td>
                <td colspan="3">
                    <input class="sel_address" type="text" placeholder="联系地址" name="address" id="address">
                </td>
            </tr>
        </table>
        <!-- =============================================================================身份证读取========================================================================================================================== -->
        <div class="test hide">
            <h1 style="text-align:center;color:#5555FF;">身份证阅读器USB接入测试页面</h1>

            <table border="0" width="50%" cellpadding="0" cellspacing="0" style="padding-left:100px;">
                <tr>
                    <td><input type="button" value="连接" onclick="connect()"></td>
                    <td><input type="button" value="状态" onclick="getStatus()"></td>
                    <td><input type="button" value="读卡" onclick="readCert()"></td>
                    <td><input type="button" value="断开" onclick="disconnect()"></td>
                    <td><input type="button" value="读IC卡序列号" onclick="readICCardSN()"></td>
                    <td><input type="button" value="读身份证物理卡号" onclick="readIDCardSN()"></td>
                </tr>
            </table>
            <table border="0" width="100%" cellpadding="0" cellspacing="10">
                <tr>
                    <td align="right">读卡时间：</td>
                    <td><input type="text" id="timeElapsed" size="49" style="width:400px;" readonly="readonly"></td>
                </tr>
                <tr>
                    <td align="right">数字签名：</td>
                    <td><input type="text" id="signature" size="49" style="width:400px;" readonly="readonly"></td>
                </tr>
                <tr>
                    <td align="right">厂家标识：</td>
                    <td><input type="text" id="venderId" size="49" style="width:400px;" readonly="readonly"></td>
                </tr>
                <td align="right">卡类型：</td>
                <td><input type="text" id="certType" size="49" style="width:400px;" readonly="readonly">(取值为“1”(表示“中国居民身份证”)或“50”(表示“外国永久居留身份证”))
                </td>
                </tr>
                <tr>
                    <td align="right">中/英姓名：</td>
                    <td><input type="text" id="partyName" size="49" style="width:400px;" readonly="readonly">(要求中间，两头都没有空格)</td>
                </tr>
                <tr>
                    <td align="right">性别：</td>
                    <td><input type="text" id="gender" size="49" style="width:400px;" readonly="readonly">(取值为“1”（表示“男”）或“0”（表示“女”）)
                    </td>
                </tr>
                <tr>
                    <td align="right">民族/国籍：</td>
                    <td><input type="text" id="nation" size="49" style="width:400px;" readonly="readonly">(汉字即可)</td>
                </tr>
                <tr>
                    <td align="right">出生日期：</td>
                    <td><input type="text" id="bornDay" size="49" style="width:400px;" readonly="readonly">(要求格式为:yyyyMMdd，长度为8)
                    </td>
                </tr>
                <tr>
                    <td align="right">住址：</td>
                    <td><input type="text" id="certAddress" size="49" style="width:400px;" readonly="readonly">(外国人永久居留身份证地址为“空”)
                    </td>
                </tr>
                <tr>
                    <td align="right">身份证号：</td>
                    <td><input type="text" id="certNumber" size="49" style="color:#FF0000;width:400px;" readonly="readonly">(居民身份号码，长度18位)
                    </td>
                </tr>
                <tr>
                    <td align="right">签发机关：</td>
                    <td><input type="text" id="certOrg" size="49" style="width:400px;" readonly="readonly">(外国永久居留身份证签发机关为“机关代码”)
                    </td>
                </tr>
                <tr>
                    <td align="right">开始期限：</td>
                    <td><input type="text" id="effDate" size="49" style="width:400px;" readonly="readonly">(要求格式为:yyyyMMdd，长度为8)
                    </td>
                </tr>
                <tr>
                    <td align="right">结束期限：</td>
                    <td><input type="text" id="expDate" size="49" style="width:400px;" readonly="readonly">(要求格式为:yyyyMMdd，长度为8，或者是“长期”)
                    </td>
                </tr>
                <tr>
                    <td align="right">结果：</td>
                    <td><textarea id="result" rows="8" cols="47" style="width:400px;" readonly="readonly"></textarea></td>
                </tr>
                <tr>
                    <td align="right">照片：</td>
                    <td><img id="headPic" style="height: 126px; width: 102px" align="top" />
                        <img id="photoDisplay" style="width:341px; height:443px;"/>
                    </td>
                </tr>
            </table>
        </div>

        <object id="CertCtl" type="application/cert-reader" width="0" height="0">
            <object classid="clsid:30516390-004F-40B9-9FC6-C9096B59262E" id="CertCtl" width="0" height="0">
                <object ID="CertCtl" classid="30516390-004F-40B9-9FC6-C9096B59262E" TYPE="application/cert-reader" width=0
                        height=0>
                    <!--<p style="color:#FF0000;">控件不可用，可能未正确安装控件及驱动，或者控件未启用。</p>-->
                </object>
            </object>
        </object>
    </form>

    <div class="fixedBottomDiv">	<!--netBtnDiv按钮组样式  -->
        <a id="bzblk" href="javascript:void(0);" class="kqdsSearchBtn" onclick="submitu()">保存</a>
        <button class="kqdsSearchBtn" onclick="readCert()" type="button">读取身份证信息</button>
    </div>

</div>
</body>

<script type="text/javascript">
    //### 这个变量声明在下面，hzjd.js调用不到
    var contextPath = '<%=contextPath%>';
</script>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/initArea.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/index/bower_components/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ui.datepicker-zh-CN.js"  type="text/javascript"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/hzjd/hzjd.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>

<script type="text/javascript">
    //当前登陆部门
    var intoPageDateTime = new Date();
    var selectedrows = window.parent.selectedrows;
    var usercode;
    var seqId;
    //信息报备权限，即是否具备修改开发人员的权限  0可以 1不可以
    var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    var static_userobj = null;
    var lastnexttype;
    $(function(){
        prov();
        initCity();
        initArea();
        initStreet();
        for (var i = 0; i < selectedrows.length; i++) {
           usercode = selectedrows[i].usercode;
           seqId = selectedrows[i].uid;
        }
        /** ########################################################### 连锁相关  **/
        initHosSelectListNoCheck('organization');// 连锁门诊下拉框

        $("#ordertime").datetimepicker({
            language:  'zh-CN',
            minView:0,
            format: 'yyyy-mm-dd hh:ii',
            autoclose : true,//选中之后自动隐藏日期选择框
            pickerPosition: "bottom-right"
        });
        static_userobj = getBaseInfoByUserCode(usercode);


        //默认选中北京市
        $("#province").val("110000").trigger("change");
        $("#city").val("110100").trigger("change");
        $("#area").val("110101").trigger("change");

        $('#province').on('click', function(){
            var val = $('#city option:eq(1)').val()
            $("#city option[value='" + val + "']").attr("selected", true);
            var cityName = $("#city").val()
            initArea(cityName)
            var areaName = $('#area option:eq(1)').val()
            $("#area option[value='" + areaName + "']").attr("selected", true);
            initStreet(townName)
            var townName = $('#town option:eq(1)').val()
            $("#town option[value='" + townName + "']").attr("selected", true);
        })

        //加载患者数据
        getDetail();
        //弹窗内星级评分
        jsJb();
        $("#province").val(static_userobj.province);
        $("#city").val(static_userobj.city);
        $("#area").val(static_userobj.area);
        $("#town").val(static_userobj.town);

        noEdit();
        submit(); //submit()校验方法
        $('.searchSelect').selectpicker("refresh");//初始化刷新搜索--2019.11.14--licc
    });

    function noEdit(){

            if($("#phonenumber1").val()!=""){
                $("#familyship").attr("disabled","disabled");
                $("#phonenumber1").attr("readonly","readonly");
            }
            if($("#phonenumber2").val()!=""){
                $("#familyship2").attr("disabled","disabled");
                $("#phonenumber2").attr("readonly","readonly");
            }
            if($("#birthday").val()!=""){
                $("#birthday").attr("readonly","readonly");
            }
            if($("#idcardno").val()!=""){
                $("#idcardno").attr("readonly","readonly");
            }
            if($("#province").val()){
                if($("#province").val()!=""){
                    $("#province").attr("disabled",true);
                }
            }
           if($("#city").val()){
                if($("#city").val()!=""){
                    $("#city").prop('disabled', true);
                    //$("#city").selectpicker('refresh');
                }
            }
            if($("#area").val()!=""){
                $("#area").attr("disabled","disabled");
            }
            if($("#town").val()){
                if($("#town").val()!=""){
                    $("#town").attr("disabled","disabled");
                }
            }
            if($("#emergencyContact").val()!=""){
                $("#emergencyContact").attr("readonly","readonly");
            }
            if($("#emergencyPhone").val()!=""){
                $("#emergencyPhone").attr("readonly","readonly");
            }
    }
    function submitu(){
        var username = $("#username").val();
        if(!username){
            layer.alert('患者姓名必须填写！' );
            return false;
        }
        var val = $('input:radio[name="sex"]:checked').val();
        if(val==null){
            layer.alert("请选择性别！");
            return false;
        }
        var phonenumber1 = $("#phonenumber1").val();
        var phonenumber2 = $("#phonenumber2").val();
        if(phonenumber1 == phonenumber2){
            layer.alert('手机号码1和手机号码2不能填写相同的值！' );
            return false;
        }
        var age=$("#age").val();
        if(!age || age<=0){
            layer.alert('患者年龄必须填写且大于零！' );
            return false;
        }else if(age>0 && age<=10){
            //console.log("------年龄年龄");
            layer.alert('提示：需填写患者真实年龄，您填写的患者年龄小于10！' );
        }
        var province=$("#province").val();
        if(!province){
            layer.alert('省份必须选择！' );
            return false;
        }
        var city=$("#city").val();
        if(!city){
            layer.alert('所在城市必须选择！' );
            return false;
        }
        var area=$("#area").val();
        if(!area){
            layer.alert('患者所在城市区域必须选择！' );
            return false;
        }
        var town=$("#town").val();
        if(!town){
            layer.alert('患者所在城市街道必须选择！' );
            return false;
        }
        var param = $('#defaultForm').serialize();
        var url = '<%=contextPath%>/KQDS_UserDocumentAct/update.act?'+param;
        $.axse(url,null,
            function(r){
                if(r.retState=="0"){
                    layer.alert('补录证件信息成功！', {
                        end: function() {
                            window.parent.refresh();
                            parent.layer.close(frameindex); //再执行关闭
                        }
                    });
                }else{
                    layer.alert(r.retMsrg );
                }
            },
            function(){
                layer.alert(r.retMsrg );
            }
        );
    }
    /* -----------------------------------------------------------------------------------------身份证读取---------------------------------------------------------------------- */

    //创建读卡控件
    var CertCtl = new IDCertCtl();

    //身份证读卡控件创建
    function IDCertCtl() {
        //创建用于与服务交换数据的对象
        this.xhr = createXmlHttp();
        this.type = "CertCtl";
        this.height = 0;
        this.width = 0;
        //连接
        this.connect = CertCtl_connect;
        //断开
        this.disconnect = CertCtl_disconnect;
        //获取状态
        this.getStatus = CertCtl_getStatus;
        //读卡
        this.readCert = CertCtl_readCert;
        //读IC卡序列号
        this.readICCardSN = CertCtl_readICCardSN;
        //读身份证物理卡号
        this.readIDCardSN = CertCtl_readIDCardSN;
    }

    //创建XMLHttpRequest 对象，用于在后台与服务器交换数据
    function createXmlHttp() {
        var xmlHttp = null;
        //根据window.XMLHttpRequest对象是否存在使用不同的创建方式
        if (window.XMLHttpRequest) {
            xmlHttp = new XMLHttpRequest();                  //FireFox、Opera等浏览器支持的创建方式
        } else {
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");//IE浏览器支持的创建方式
        }
        return xmlHttp;
    }

    //连接方法
    function CertCtl_connect() {
        var result = "";
        //创建请求 第一个参数是代表以post方式发送；第二个是请求端口和地址；第三个表示是否异步
        CertCtl.xhr.open("POST", "http://127.0.0.1:18889/api/connect", false);
        //发送请求
        try {
            CertCtl.xhr.send();
        } catch (e) {
        }
        //返回值readyState   0: 请求未初始化
        //				    1: 服务器连接已建立
        //				    2：请求已接收
        //				    3: 请求处理中
        //				    4: 请求已完成，且响应已就绪
        //返回值status      200: "OK"
        //					404: 未找到页面
        //当返回值readyState为4且status为200时,为查询成功
        if (CertCtl.xhr.readyState == 4 && CertCtl.xhr.status == 200) {
            result = CertCtl.xhr.responseText;
            CertCtl.xhr.readyState = 1;
        }
        return result;
    }

    //断开方法
    function CertCtl_disconnect() {
        var result = "";
        //创建请求 第一个参数是代表以post方式发送；第二个是请求端口和地址；第三个表示是否异步
        CertCtl.xhr.open("POST", "http://127.0.0.1:18889/api/disconnect", false);
        //发送请求
        try {
            CertCtl.xhr.send();
        } catch (e) {
        }
        if (CertCtl.xhr.readyState == 4 && CertCtl.xhr.status == 200) {
            result = CertCtl.xhr.responseText;
            CertCtl.xhr.readyState = 1;
        }
        return result;
    }
    //获取状态方法
    function CertCtl_getStatus() {
        var result = "";
        //创建请求 第一个参数是代表以post方式发送；第二个是请求端口和地址；第三个表示是否异步
        CertCtl.xhr.open("POST", "http://127.0.0.1:18889/api/getStatus", false);
        //发送请求
        try {
            CertCtl.xhr.send();
        } catch (e) {
        }
        if (CertCtl.xhr.readyState == 4 && CertCtl.xhr.status == 200) {
            result = CertCtl.xhr.responseText;
            CertCtl.xhr.readyState = 1;
        }
        return result;
    }

    //执行读卡操作
    function CertCtl_readCert() {
        var result = "";
        try {
            //创建请求 第一个参数是代表以post方式发送；第二个是请求端口和地址；第三个表示是否异步
            CertCtl.xhr.open("POST", "http://127.0.0.1:18889/api/readCert", false);
            //发送请求
            CertCtl.xhr.send();
            if (CertCtl.xhr.readyState == 4 && CertCtl.xhr.status == 200) {
                result = CertCtl.xhr.responseText;
                CertCtl.xhr.readyState = 1;
            }
        } catch (e) {

        }
        return result;
    }

    //获取IC卡序列号
    function CertCtl_readICCardSN() {
        var result = "";
        //创建请求 第一个参数是代表以post方式发送；第二个是请求端口和地址；第三个表示是否异步
        CertCtl.xhr.open("POST", "http://127.0.0.1:18889/api/readICCardSN", false);
        //发送请求
        try {
            CertCtl.xhr.send();
        } catch (e) {
        }
        if (CertCtl.xhr.readyState == 4 && CertCtl.xhr.status == 200) {
            result = CertCtl.xhr.responseText;
            CertCtl.xhr.readyState = 1;
        }
        return result;
    }

    //获取身份证物理卡号
    function CertCtl_readIDCardSN() {
        var result = "";
        //创建请求 第一个参数是代表以post方式发送；第二个是请求端口和地址；第三个表示是否异步
        CertCtl.xhr.open("POST", "http://127.0.0.1:18889/api/readIDCardSN", false);
        //发送请求
        try {
            CertCtl.xhr.send();
        } catch (e) {
        }
        if (CertCtl.xhr.readyState == 4 && CertCtl.xhr.status == 200) {
            result = CertCtl.xhr.responseText;
            CertCtl.xhr.readyState = 1;
        }
        return result;
    }

    //转为Json格式
    function toJson(str) {
        //var obj = JSON.parse(str);
        //return obj;
        return eval('(' + str + ')');
    }

    //清空页面上显示的内容
    function clearForm() {
        //对应控件的值全部清空
        document.getElementById("timeElapsed").value = "";
        document.getElementById("certType").value = "";
        document.getElementById("timeElapsed").value = "";
        document.getElementById("venderId").value = "";
        document.getElementById("signature").value = "";
        document.getElementById("partyName").value = "";
        document.getElementById("gender").value = "";
        document.getElementById("nation").value = "";
        document.getElementById("bornDay").value = "";
        document.getElementById("certAddress").value = "";
        document.getElementById("certNumber").value = "";
        document.getElementById("certOrg").value = "";
        document.getElementById("effDate").value = "";
        document.getElementById("expDate").value = "";
        document.getElementById("result").value = "";
        document.getElementById("headPic").src = "";
        document.getElementById("photoDisplay").src = "";
    }

    //连接方法
    function connect() {
        //清空页面
        clearForm();

        try {
            //调用对应的连接方法,并赋值给result
            var result = CertCtl.connect();
            //如果result为空,代表读卡插件未启动
            if (result == "") {
                //layer.alert("未启动读卡插件!")
            } else {
                //result页面回显
                document.getElementById("result").value = result;
            }
        } catch (e) {
        }
    }

    //断开连接方法
    function disconnect() {
        //清空页面
        clearForm();

        try {
            //调用对应的断开连接方法,并赋值给result
            var result = CertCtl.disconnect();
            //如果result为空,代表读卡插件未启动
            if (result == "") {
                layer.alert("未启动读卡插件!")
            } else {
                //result页面回显
                document.getElementById("result").value = result;
            }
        } catch (e) {
        }
    }

    //获取状态方法
    function getStatus() {
        //清空页面
        clearForm();

        try {
            //调用对应的获取状态方法,并赋值给result
            var result = CertCtl.getStatus();
            //如果result为空,代表读卡插件未启动
            if (result == "") {
                layer.alert("未启动读卡插件!")
            } else {
                //result页面回显
                document.getElementById("result").value = result;
            }
        } catch (e) {
        }
    }

    //读卡方法
    function readCert() {
        //清空页面
        clearForm();
        //开始时间
        var startDt = new Date();
        //调用对应的读卡方法
        var result = CertCtl.readCert();
        //如果result为空,代表读卡插件未启动
        if (result == "") {
            layer.alert("未启动读卡插件!")
        } else {
            //结束时间
            var endDt = new Date();
            //读卡时间回显
            document.getElementById("timeElapsed").value = (endDt.getTime() - startDt.getTime()) + "毫秒";
            document.getElementById("result").value = result;
            //格式化result
            var resultObj = $.parseJSON(result);
            //resultFlag为0代表读卡成功
            if (resultObj.resultFlag == "0") {
                partyName = resultObj.resultContent.partyName;//姓名
                gender = resultObj.resultContent.gender;//性别
                nation = resultObj.resultContent.nation;//民族
                certAddress = resultObj.resultContent.certAddress;//住址
                certNumber = resultObj.resultContent.certNumber;//身份证号
                certOrg = resultObj.resultContent.certOrg;//发证机关
                var effDat = resultObj.resultContent.effDate;//发证日期
                effDate = effDat.substring(0,4)+"-"+effDat.substring(4,6)+"-"+effDat.substring(6,effDat.length);
                var expDat = resultObj.resultContent.expDate;//证件最后失效时间
                expDate = expDat.substring(0,4)+"-"+expDat.substring(4,6)+"-"+expDat.substring(6,expDat.length);
                headPic = resultObj.resultContent.identityPic;
                photoDisplay = resultObj.resultContent.identityPrintPic;
                if($("#username").val() != resultObj.resultContent.partyName){
                    $("#username").val(resultObj.resultContent.partyName);
                }
                if(gender == 1){
                    $('input:radio[value="男"]').prop('checked','checked');
                }else if (gender == 0) {
                    $("input:radio[value='女']").prop('checked','checked');
                }
                //读取身份证时对出生年月、身份证号、地址进行判定如若无值进行赋值
                var birth = resultObj.resultContent.bornDay;//格式化日期
                var birthNew=birth.substring(0,4)+"-"+birth.substring(4,6)+"-"+birth.substring(6,birth.length);
                $("#birthday").val(birthNew);//出生年月
                var age = new Date().Format("yyyy-MM-dd").substring(0,4)-birth.substring(0,4);
                $("#age").val(age);//年龄
                /* }  */
                if($("#idcardno").val()==null || $("#idcardno").val() == ''){
                    $("#idcardno").val(resultObj.resultContent.certNumber);//身份证号
                }
                if($("#clipAddress").val()==null || $("#clipAddress").val() == ''){
                    $("#clipAddress").val(resultObj.resultContent.certAddress);//住址赋值
                }
                bornDay = birthNew;//出生年月
            } else if (resultObj.resultFlag == "-1") {
                if (resultObj.errorMsg == "端口打开失败") {
                    layer.alert("读卡器未连接");
                } else {
                    layer.alert(resultObj.errorMsg);
                }
            } else if (resultObj.resultFlag == "-2") {
                layer.alert(resultObj.errorMsg);
            }
        }
    }
    function readICCardSN() {
        //清空页面
        clearForm();

        try {
            //调用对应的获取状态方法,并赋值给result
            var result = CertCtl.readICCardSN();
            //如果result为空,代表读卡插件未启动
            if (result == "") {
                layer.alert("未启动读卡插件!")
            } else {
                //result页面回显
                document.getElementById("result").value = result;
            }
        } catch (e) {
        }
    }

    function readIDCardSN() {
        //清空页面
        clearForm();

        try {
            //调用对应的获取状态方法,并赋值给result
            var result = CertCtl.readIDCardSN();
            //如果result为空,代表读卡插件未启动
            if (result == "") {
                layer.alert("未启动读卡插件!")
            } else {
                //result页面回显
                document.getElementById("result").value = result;
            }
        } catch (e) {
        }
    }
/**---------------------------------------------------------END------------------------------------------------------------------------------------------------------------*/
</script>
<style type="text/css">
    /* 这里写样式是因为出生年月，后面增加了日期图标，如果不写下面的样式，出生年月会占两行 */
    button{
        font: normal;
        line-height:normal;
    }
</style>
</html>