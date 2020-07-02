<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/inc/classImport.jsp" %>
<%
    String contextPath = request.getContextPath();
    if (contextPath.equals("")) {
        contextPath = "/kqds";
    }
    String type = request.getParameter("type");
    if (type == null || "".equals(type)) {
        type = "0";
    }
    YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<!-- 本页面已优化（输入框需要再次优化）鲍3-26 -->
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8"/>
    <title>患者建档</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/hzjd/hzjd.css">
    <!-- select搜索筛选 -->
    <link rel="stylesheet" type="text/css"
          href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css"/>
</head>
<style>
    .aa {
        display: inline-block;
        box-sizing: border-box;
        line-height: 20px;
        background: #00a6c0;
        color: #fff;
        outline: none;
        padding-left: 10px;
        padding-right: 10px;
        border: 1px solid #00a6c0;
        border-radius: 30px;
        text-decoration: none;
        cursor: pointer;
        text-align: center;
    }

    .otherColor {
        background: #428bca;
        border: 1px solid #428bca;
    }

    .namecoloc {
        display: inline-block;
        font-size: 14px;
        line-height: 26px;
        font-weight: bold;
        margin-left: 5px;
        display: none;
    }

    .alla {
        display: flex;
        flex-wrap: wrap;
        margin-left: 10px;
    }

    .alla > li {
        margin-bottom: 10px;
        margin-left: 5px;
    }

    .labelfatherLi {
        width: 100%;
        margin: 10px 0 10px 10px;
    }

    .plus {
        line-height: 30px;
        margin: 0 auto;
        font-size: 24px;
    }

    #lableShow {
        min-height: 90px;
    }

    .ageText {
        display: none;
        font-size: 12px;
        position: absolute;
        bottom: -43px;
        left: 0px;
        color: #a94442;
    }

    .select2:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {
        width: 200px;
    }

    .select2 > .btn {
        width: 200px;
        height: 28px;
        border: solid 1px #e5e5e5;
    }

    .sel3:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {
        width: 110px;
    }

    .sel3 > .btn {
        width: 110px;
        height: 28px;
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

    .bootstrap-select .dropdown-toggle:focus {
        outline: 0px dotted #333333 !important;
        outline: 0px auto -webkit-focus-ring-color !important;
        outline-offset: -2px;
    }
</style>
<body>

<div class="container"><!--提供内边距 让内容不顶头显示-->
    <form id="defaultForm"><!-- 本身无样式  defaultForm表单验证功能要用 -->
        <input type="hidden" name="seqId" id="seqId">
        <table class="tableContent"><!--tableContent的样式 -->
            <tbody>

            <tr class="addVisitingDialog-content">    <!--addVisitingDialog 本身无样式 绑定了星星的点击事件  -->
                <td>    <!--impText必填项样式  -->
                    <span class="impText">患者编号*</span>
                </td>
                <td>
                    <input type="text" name="usercode" id="usercode" readonly="readonly">
                    <input type="hidden" name="important" id="important" readonly="readonly">
                    <input type="hidden" name="type" id="type" value="<%=type%>">
                    <input type="hidden" name="doorstatus" id="doorstatus" value="0">

                </td>
                <td colspan="2" class="starsBox">    <!--starsBox 本身无样式 绑定了星星的点击事件  只有在该类下的星星被点击才有用-->
                    <span class="hc-icon icon20 stars-icon" value="1"></span>
                    <span class="hc-icon icon20 stars-icon" value="2"></span>
                    <span class="hc-icon icon20 stars-icon" value="3"></span>
                    <span class="hc-icon icon20 stars-icon" value="4"></span>
                    <span class="hc-icon icon20 stars-icon" value="5"></span>
                </td>
            </tr>

            <tr>
                <td>    <!--impText必填项样式  -->
                    <span class="impText">姓 名*</span>
                </td>
                <td>
                    <div class="form-group">
                        <input type="text" name="username" id="username" placeholder="请输入姓名">
                    </div>
                </td>
                <td>
                    <span class="impText">性别*</span>
                </td>
                <td>
                    <div class="form-group sexReg">    <!--form-group与表单验证有关系  sexReg性别的 提示符号 与提示文本 位置调整   -->
                        <!--id=maleId id=femaleId 均为了配合label标签点击操作 没特殊用途    -->
                        <input id="maleId" type="radio" name="sex" value="男"><label for="maleId" class="sexValue"
                                                                                    style="font-weight: bold;font-size: 13px;">男</label>
                        <input id="femaleId" type="radio" name="sex" value="女"><label for="femaleId" class="sexValue"
                                                                                      style="font-weight: bold;font-size: 13px;">女</label>
                    </div>
                </td>
            </tr>

            <tr>
                <td>    <!--impText必填项样式  -->
                    <span class="impText">电话1*</span>
                </td>
                <td>
                    <div class="form-group">
                        <select class="sel_short_inp_long" id="familyship" name="familyship">
                            <!--.sel_short_inp_long select与input组合时的样式  -->
                            <option value="本人">本人</option>
                            <option value="家人">家人</option>
                        </select>
                        <input class="sel_short_inp_long" type="text" id="phonenumber1" name="phonenumber1"
                               maxlength="11" placeholder="请输入11位电话号码"/>
                    </div>
                </td>
                <td>    <!--.comText 选填项样式-->
                    <span class="comText">电话2</span>
                </td>
                <td>
                    <div class="form-group">
                        <select class="sel_short_inp_long">    <!--.sel_short_inp_long select与input组合时的样式  -->
                            <option>本人</option>
                            <option>家人</option>
                        </select>
                        <input class="sel_short_inp_long" type="text" id="phonenumber2" name="phonenumber2"
                               maxlength="11" placeholder="请输入11位电话号码"/>
                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <span class="comText">出生年月</span>
                </td>
                <td style="position:relative;">
                    <div class="form-group">
                        <input size="12" type="text" name="birthday" id="birthday" value="" placeholder="请选择出生年月"
                               class="birthDate" onchange="changeAge();"/>
                    </div>
                </td>
                <td>
                    <span class="comText impText">年 龄*</span>
                </td>
                <td>
                    <div class="form-group" style="position: relative;">
                        <input type="text" id="age" name="age" onkeyup="this.value=this.value.replace(/\D/g,'')"
                               maxlength="3" onafterpaste="this.value=this.value.replace(/\D/g,'')"
                               onclick="changeAge();" oninput="ageHint();" placeholder="请输入真实年龄"/>
                        <span class="ageText">您填写的患者年龄小于10！</span>
                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <span class="impText">患者来源*</span>
                </td>
                <td class="relative">
                    <div class="form-group">
                        <select id="devchannel" name="devchannel" class="select2 dict searchSelect"
                                data-live-search="true" resource="hzly" title="请选择"
                                onchange="getSubDictSelectSearch('devchannel','nexttype','add');" disabled></select>
                    </div>
                </td>
                <td>
                    <span class="impText">子分类*</span>
                </td>
                <td>
                    <div class="form-group">
                        <select id="nexttype" name="nexttype" class="select2 dict searchSelect" data-live-search="true"
                                title="请选择" disabled></select>
                    </div>
                </td>
            </tr>

            <tr>
                <!-- <td>
                    <span class="comText">职业</span>
                </td>
                <td>.select2 dict本身无样式 与功能有关
                    <select id="profession" name="profession" class="select2 dict searchSelect"  data-live-search="true" tig="job" title="请选择"></select>
                </td> -->
                <td>
                    <span class="comText">身份证号</span>
                </td>
                <td>
                    <div class="form-group">
                        <input type="text" name="idcardno" id="idcardno" placeholder="身份证号" maxlength="18">
                    </div>
                </td>
                <td>
                    <span class="comText">身份证地址</span>
                </td>
                <td><!-- .select2 dict本身无样式 与功能有关 -->
                    <input type="text" name="clipAddress" id="clipAddress" placeholder="身份证地址">
                </td>
            </tr>

            <tr>
                <td>
                    <span class="impText">现居住地址*</span>
                </td>
                <td colspan="3">
                    <div class="form-group provinceReg">
                        <select id="province" name="province" class="sel3 searchSelect" data-live-search="true"
                                onchange="initCity()"></select>
                        <select id="city" name="city" class="sel3 searchSelect" data-live-search="true"
                                onchange="initArea()"></select>
                        <select id="area" name="area" class="sel3 searchSelect" data-live-search="true"
                                onchange="initStreet()"></select>
                        <select id="town" name="town" class="sel3 searchSelect" data-live-search="true"></select>
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

            <tr>
                <td>
                    <span class="comText">就诊经历</span>
                </td>
                <td>
                    <input type="text" placeholder="就诊经历" id="experience" name="experience">
                </td>
                <td>
                    <span class="comText">洁牙习惯</span>
                </td>
                <td>
                    <select id="habit" name="habit">
                        <option value="">请选择</option>
                        <option value="有">有</option>
                        <option value="无">无</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td>
                    <span class="impText">是否他人介绍*</span>
                </td>
                <td>
                    <div class="form-group">
                        <select class="dict" name="introduce" id="introduce">
                            <option value="">-请选择-</option>
                            <option value="是">是</option>
                            <option value="否">否</option>
                        </select>
                    </div>
                </td>
                <td>
                    <span class="comText">介绍人</span>
                </td>
                <td>    <!-- .whiteInp  input白底鼠标移入的效果-->
                    <input type="hidden" name="introducer" id="introducer" value=""/>
                    <input type="text" class="whiteInp" id="introducerDesc" name="introducerDesc" onclick="getuser()"
                           placeholder="介绍人">
                </td>
            </tr>

            <tr>
                <td>
                    <span class="comText">开发人</span>
                </td>
                <td>    <!-- .whiteInp  input白底鼠标移入的效果-->
                    <input type="hidden" name="developer" id="developer" class="form-control"/>
                    <input type="text" class="whiteInp" id="developerDesc" name="developerDesc" placeholder="开发人"
                           onClick="javascript:xxbbValidate();">
                </td>
                <td>
                    <span class="comText">建档导医</span>
                </td>
                <td>    <!-- .whiteInp  input白底鼠标移入的效果-->
                    <input type="hidden" name="guider" id="guider"/>
                    <input type="text" class="whiteInp" id="guiderDesc" name="guiderDesc" placeholder="建档导医"
                           onClick="javascript:single_select_user(['guider', 'guiderDesc'],'single');">
                </td>
            </tr>

            <!-- 添加社会标签 -->
            <tr>
                <td>
                    <span class="comText">参加本院活动</span>
                </td>
                <td>
                    <div class="form-group">
                        <select class="dict" tig="glhd238" name="activity" id="activity">
                        </select>
                    </div>
                </td>
                <td>
                    <span class="comText">QQ/微信</span>
                </td>
                <td>
                    <input type="text" placeholder="QQ/微信" id="qq" name="qq">
                </td>
            </tr>

            <tr class="textareaTr3" style="height:96px;"><!-- textareaTr3 调整该行的行高   -->
                <td>
                    <span class="comText">患者印象</span>
                </td>
                <td colspan="3">
                    <div class="form-group">
                        <textarea name="userimpress" id="userimpress" rows="3" placeholder=""></textarea>
                    </div>
                </td>
            </tr>

            <tr class="textareaTr1" style="height:52px;"><!-- textareaTr1 调整该行的行高   -->
                <td>
                    <span class="comText">第一句话</span>
                </td>
                <td colspan="3">
                    <textarea name="firstword" id="firstword" rows="1" placeholder=""></textarea>
                </td>
            </tr>

            <tr class="textareaTr1" style="height:58px;"><!-- textareaTr1 调整该行的行高   -->
                <td>
                    <span class="comText">备注</span>
                </td>
                <td colspan="3">
                    <textarea name="remark" id="remark" rows="1" placeholder=""></textarea>
                </td>
            </tr>
            <tr id="conceal" class="textareaTr3" style="width:527px;position: static;"><!-- textareaTr3 调整该行的行高   -->
                <td>
                    <span class="impText">患者标签*</span>
                </td>
                <td colspan="3">
                    <div style="border-radius: 4px;border: solid 1px #e5e5e5;margin-bottom: 20px;max-width: 559px;">
                        <ul id="lableShow">
                            <li class="labelfatherLi">
                                <div class="namecoloc">消费标签:</div>
                                <ul class="alla" id="expenseShow">
                                </ul>
                            </li>
                            <li class="labelfatherLi">
                                <div class="namecoloc">社会标签:</div>
                                <ul class="alla" id="societyShow">
                                </ul>
                            </li>
                            <li class="labelfatherLi">
                                <div class="namecoloc">需求标签:</div>
                                <ul class="alla" id="needShow">
                                </ul>
                            </li>
                        </ul>
                    </div>
                </td>
                <td style="padding-left: 0;">
                    <botton id="labelBtn" class="aa" onclick="openPatientTag();">
                        <span class="plus">+</span>
                    </botton>
                </td>
                </td>

            </tr>
            </tbody>
        </table>
    </form>

    <div class="fixedBottomDiv"><!--底部三个按钮所在父元素 固定在底部样式  -->
        <div class="clear2"></div>
        <a class="kqdsCommonBtn" id="clean">清 空</a>
        <a class="kqdsCommonBtn" id="close">取 消</a>
        <a class="kqdsSearchBtn" id="submit">确 定</a>
        <button class="kqdsSearchBtn" onclick="readCert()" type="button">读取身份证信息</button>
    </div>
</div>
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
            <td><img id="headPic" style="height: 126px; width: 102px" align="top"/>
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
</body>
<script type="text/javascript">
    // ### 这个变量声明在下面，hzjd.js调用不到
    var contextPath = '<%=contextPath%>';
</script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script> --%>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script> --%>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/initArea.js"></script>
<script type="text/javascript"
        src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript"
        src="<%=contextPath%>/static/js/admin/index/bower_components/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/hzjd/hzjd.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript">

    var partyName;
    var gender;
    var nation;
    var bornDay;
    var certAddress;
    var certOrg;
    var effDate;
    var expDate;
    var headPic;
    var photoDisplay;

    var usercodechild = "";//接收layer子窗口传值
    var usernamechild = "";//接收layer子窗口传值

    //信息报备权限，即是否具备修改开发人员的权限  0可以 1不可以
    var canEditKf = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag7_canEditKf, request)%>';
    var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    var frameSelfindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引


    //登录权限licc--2020-1-8
    var loadperson = '<%=person.getUserPriv()%>';
    var load = loadperson.split(",");//登陆这权限
    // console.log(load+"------load");
    var allowedperson = '<%=SysParaUtil.getSysValueByName(request, SysParaUtil.RIGHT_YCHF)%>';
    var allowed = allowedperson.split(",");//允许权限
    // console.log(allowed+"------allowed");

    //判断当前人员权限是否有查看患者来源和子分类等着资源
    var total = load.concat(allowed);

    function isExist(total) {
        var exist = {};
        for (var i in total) {
            if (exist[total[i]]) {
                return true;
            }
            exist[total[i]] = true;
        }
        return false;
    }

    var existornot = isExist(total);//是否是前台人员

    $(function () {
        prov();
        initCity();
        initArea();
        initStreet();
        //读卡前先连接机器
        connect();
        //默认选中北京市
        $("#province").val("110000").trigger("change");
        $("#city").val("110100").trigger("change");
        $("#area").val("110101").trigger("change");
//   	 	搜索下拉框点击默认值方法重写
        $('#province').on('change', function () {
            var val = $('#city option:eq(1)').val();
            $("#city option[value='" + val + "']").attr("selected", true);
            var cityName = $("#city").val();
            initArea(cityName);
            var areaName = $('#area option:eq(1)').val();
            $("#area option[value='" + areaName + "']").attr("selected", true);
            initStreet(townName);
            var townName = $('#town option:eq(1)').val();
            $('#town').selectpicker('val', townName);//官方赋值
        })
        initDictSelectByClassIscustomer(existornot);//前台患者来源资源隐藏专用
        initDictSelectByClass();
        getUserCode();//获取病人编号
        //弹窗内星级评分
        jsJb();
        //验证表单
        Yztable();
        if (canEditKf != 1) {
            $("#developerDesc").attr('readonly', 'readonly').attr('onclick', '').click(eval(function () {
                alert('无设定权限！');
            }));
        }
        $('.searchSelect').selectpicker("refresh");//初始化刷新--2019.10.11--licc
        /*********************/
        $("#devchannel").selectpicker('val', "lpyjs106"); //患者来源赋值
        $("#devchannel").trigger("change");
        $("#nexttype").selectpicker('val', "lhzjs963"); //子分类赋值
        $(".glyphicon-remove").css("display", "none");
        $(".help-block").css("display", "none");
        $(".dropdown-toggle").css("border", "1px solid #e5e5e5");
        /*********************/
    });

    function cleatBtn(obj) {
        obj.parentNode.remove();
    }

    $('#clean').on('click', function () {
        var usercode = $('input[name=usercode]').val();
        $("#defaultForm")[0].reset();// 调用dom的reset方法，把表单中的元素重置为它们的默认值，比如一个<input type='text' value='2'/>,如果通过Js将值改为3，然后手工通过页面改为4，则reset后值仍为2
        $('input[name=usercode]').val(usercode);
        $('input[name=developer]').val("");
        $('input[name=introducer]').val("");
        $('input[name=important]').val("");
        //默认选中北京
        $("#province").val("110000").trigger("change");
        //点击清除默认值改变为北京，从新调用
        prov();
        initCity();
        initArea();
        initStreet();
        $(".stars-icon").each(function (i, n) {
            $(this).attr('class', 'hc-icon icon20 stars-icon');
        });
        $("#defaultForm").data('bootstrapValidator').resetForm();
    });
    $('#submit').on('click', function () {
        $("#province").val();
        $("#city").val();
        $("#area").val();
        $("#town").val();

        var username = $("#username").val();
        if (!username) {
            layer.alert('患者姓名必须填写！');
            return false;
        }

        var val = $('input:radio[name="sex"]:checked').val();
        if (val == null) {
            layer.alert("请选择性别！");
            return false;
        }

        var phonenumber1 = $("#phonenumber1").val();
        var phonenumber2 = $("#phonenumber2").val();
        if (phonenumber1 == phonenumber2) {
            layer.alert('手机号码1和手机号码2不能填写相同的值！');
            return false;
        }

        // 2019/8/4  lutian 患者年龄校验必填
        var age = $("#age").val();
        if (!age || age <= 0) {
            layer.alert('患者年龄必须填写且大于零！');
            return false;
        }
        var devchannel = $("#devchannel").val();
        if (!devchannel) {
            layer.alert('患者来源必须选择！');
            return false;
        }
        var nexttype = $("#nexttype").val();
        if (!nexttype) {
            layer.alert('患者来源子分类必须选择！');
            return false;
        }

        var province = $("#province").val();
        if (!province) {
            layer.alert('省份必须选择！');
            return false;
        }
        var city = $("#city").val();
        if (!city) {
            layer.alert('城市必须选择！');
            return false;
        }
        var area = $("#area").val();
        if (!area) {
            layer.alert('城市区域必须选择！');
            return false;
        }
        var town = $("#town").val();
        if (!town) {
            layer.alert('街道必须选择！');
            return false;
        }
        var introduce = $("#introduce").val();
        if (introduce == "") {
            layer.alert("请选择是否他人介绍！");
            return false;
        }
        var introducer = $("#introducer").val();
        if (introduce == "是") {
            if (introducer == "" || introducer == null || introducer == "null" || introducer == "undefined") {
                layer.alert("如果有介绍人，请选择介绍人！");
                return false;
            }
        }
        var param = $('#defaultForm').serialize(); // 输出序列化表单值的结果，比如sex=1&age=10，如果是中文的话，会通过URLEncoder.encode编码，序列化的类型包括：文本框、textarea、下拉框、复选框等,单选框要选中的才被序列

        //患者标签参数
        var labelAllArr = [];
        var exploit;
        var exploit1;
        var exploitId;
        var exploitId1;
        var exploitStatus;
        var exploitStatus1;
        if ($("#expenseShow").html()) {
            $("#expenseShow").find("li").each(function (i, ele) {
                var expenseObj = {};
                expenseObj.labelThreeName = $(this).attr("value");
                expenseObj.labelThreeId = $(this).attr("id");
                expenseObj.labelTwoName = $(this).attr("slname");
                expenseObj.labelTwoId = $(this).attr("slid");
                expenseObj.labelOneName = "消费标签";
                expenseObj.labelOneId = "ad2eae81-310c-45f0-a667-8a8f2383b168";
                labelAllArr.push(expenseObj);
            });
        }
        if ($("#societyShow").html()) {
            $("#societyShow").find("li").each(function (i, ele) {
                var societyObj = {};
                societyObj.opinion = $(this).attr("title");
                societyObj.labelThreeName = $(this).attr("value");
                societyObj.labelThreeId = $(this).attr("id");
                societyObj.labelTwoName = $(this).attr("slname");
                societyObj.labelTwoId = $(this).attr("slid");
                societyObj.labelOneName = "社会标签";
                societyObj.labelOneId = "13543c4d-f81e-4251-87a1-f07984022e9f";
                labelAllArr.push(societyObj);
            });
        }
        if ($("#needShow").html()) {
            $("#needShow").find("li").each(function (i, ele) {
                var needObj = {};
                needObj.labelThreeName = $(this).attr("value");
                needObj.labelThreeId = $(this).attr("id");
                needObj.labelTwoName = $(this).attr("slname");
                //可开发项目
                if ($(this).attr("slid") == "61223947-c0da-4d0f-a1ff-429232d10a3c") {
                    exploitStatus = 1;
                    exploit = $(this).attr("privelist");
                    exploitId = $(this).attr("id");
                }
                //可开发空间
                if ($(this).attr("slid") == "3d6b7239-c964-4714-8020-4dcc15ed1f5d") {
                    exploitStatus1 = 2;
                    exploit1 = $(this).attr("privelist");
                    exploitId1 = $(this).attr("id");
                }
                needObj.labelTwoId = $(this).attr("slid");
                needObj.labelOneName = "需求标签";
                needObj.labelOneId = "a5cb2fa0-952f-4e45-b5e9-c6be12f4baf0";
                labelAllArr.push(needObj);
            });
        }
        var labelAllArr = JSON.stringify(labelAllArr);
        exploit = JSON.stringify(exploit);
        exploit1 = JSON.stringify(exploit1);
        exploit = encodeURIComponent(exploit);
        exploit1 = encodeURIComponent(exploit1);
        labelAllArr = encodeURIComponent(labelAllArr);

        //	 console.log(param,"------------");
        var url = contextPath + '/KQDS_UserDocumentAct/insert.act?' + param;
        var usercode = $("#usercode").val();
        var params = {
            labelAllArr: labelAllArr,
            exploit: exploit,
            exploit1: exploit1,
            exploitId: exploitId,
            exploitId1: exploitId1,
            exploitStatus: exploitStatus,
            exploitStatus1: exploitStatus1,
            nation: nation,
            certOrg: certOrg,
            effDate: effDate,
            expDate: expDate,
            headPic: headPic,
            photoDisplay: photoDisplay,
        };
        $.axse(url, params,
            function (r) {
                if (r.retState == "0") {
                    layer.alert('创建成功', {
                        closeBtn: 0,
                        end: function () {
                            parent.layer.close(frameindex); //执行关闭
                        }
                    });
                    return true;
                } else if (r.retState == "100") { // 患者编号重复
                    getUserCode();//获取病人编号
                    layer.alert('患者编号已存在，系统已自动获取新的病历号，请再次进行提交操作。', {});

                } else {
                    layer.alert('建档失败：' + r.retMsrg, {});
                    return false;
                }
            },
            function () {
                layer.alert('建档失败', {});
                return false;
            });
    });
    $('#close').on('click', function () {
        parent.layer.close(frameindex); //执行关闭
    });

    /**
     * 初始化患者来源 （专属客服人员）2019-11-30 syp
     */
    function initDictSelectByClassIscustomer(existornot, operFlag) {
        if ($(".dict").length > 0) {
            for (var i = 0; i < $(".dict").length; i++) {
                var dict = $(".dict").eq(i);
                // :eq() 选择器选取带有指定 index 值的元素。index值从 0 开始，所有第一个元素的 index 值是 0（不是1）。
                var type = dict.attr("resource");
//             var url = contextPath + "/YZDictAct/getListByParentCodeIscustomer.act?parentCode=" + type;
                var url = contextPath + "/YZDictAct/getListByParentCode.act?parentCode=" + type;
                $.axse(url, null,
                    function (data) {
                        var list = data.list;
                        if (list != null && list.length > 0) {
                            dict.empty();
                            dict.append("<option value=''>请选择</option>");
                            for (var j = 0; j < list.length; j++) {
                                var optionStr = list[j];
                                if (existornot) {
                                    if (optionStr.seqId == "sc569" || optionStr.seqId == "zrdz510" || optionStr.seqId == "lpyjs106" || optionStr.seqId == "qdzz13" || optionStr.seqId == "ptlz562" || optionStr.seqId == "pyjs900" || optionStr.seqId == "nbygkf734" || optionStr.seqId == "yyyg526" || optionStr.seqId == "wzqdjs66") {
                                        //隐藏种植市场--自然上门--老患者介绍--渠道转诊--陪同来诊--朋友介绍--内部员工开发--医院员工--未知渠道
                                        dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                                    } else {
                                        dict.append("<option value='" + optionStr.seqId + "' style='display:none'>" + optionStr.dictName + "</option>");
                                    }
                                } else {
                                    dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                                }
                            }
                        }

                        if ('triggerChange' == operFlag) { // 触发Onchange事件 #### add by yangsen
                            $(dict).trigger('change'); // 和 连锁代码片段的 $("#organization").change 配合使用
                        }
                    },
                    function () {
                        layer.alert('查询出错！');
                    });
            }
        }
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
                effDate = effDat.substring(0, 4) + "-" + effDat.substring(4, 6) + "-" + effDat.substring(6, effDat.length);
                var expDat = resultObj.resultContent.expDate;//证件最后失效时间
                expDate = expDat.substring(0, 4) + "-" + expDat.substring(4, 6) + "-" + expDat.substring(6, expDat.length);
                headPic = resultObj.resultContent.identityPic;
                photoDisplay = resultObj.resultContent.identityPrintPic;
                if ($("#username").val() != resultObj.resultContent.partyName) {
                    $("#username").val(resultObj.resultContent.partyName);
                }
                if (gender == 1) {
                    $('input:radio[value="男"]').prop('checked', 'checked');
                } else if (gender == 0) {
                    $("input:radio[value='女']").prop('checked', 'checked');
                }
                //读取身份证时对出生年月、身份证号、地址进行判定如若无值进行赋值
                var birth = resultObj.resultContent.bornDay;//格式化日期
                var birthNew = birth.substring(0, 4) + "-" + birth.substring(4, 6) + "-" + birth.substring(6, birth.length);
                $("#birthday").val(birthNew);//出生年月
                var age = new Date().Format("yyyy-MM-dd").substring(0, 4) - birth.substring(0, 4);
                $("#age").val(age);//年龄
                /* }  */
                if ($("#idcardno").val() == null || $("#idcardno").val() == '') {
                    $("#idcardno").val(resultObj.resultContent.certNumber);//身份证号
                }
                if ($("#clipAddress").val() == null || $("#clipAddress").val() == '') {
                    $("#clipAddress").val(resultObj.resultContent.certAddress);//住址赋值
                }
                bornDay = birthNew;//出生年月
            } else if (resultObj.resultFlag == "-1") {
                if (resultObj.errorMsg == "端口打开失败") {
                    alert("读卡器未连接");
                } else {
                    alert(resultObj.errorMsg);
                }
            } else if (resultObj.resultFlag == "-2") {
                alert(resultObj.errorMsg);
            }
        }
    }

    function getButtonPower() {
        for (var i = 0; i < listbutton.length; i++) {
            if (listbutton[i].qxName == "xgzl_blh") {
                $('#blcode').removeAttr("readonly");
                $('#blcode').removeAttr("onclick");
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
                alert("未启动读卡插件!")
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

</script>


</html>
