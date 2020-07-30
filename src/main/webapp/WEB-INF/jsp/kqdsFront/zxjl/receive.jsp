<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/inc/classImport.jsp" %>
<%
    String contextPath = request.getContextPath();
    if (contextPath.equals("")) {
        contextPath = "/kqds";
    }
    YZPerson person = (YZPerson) request.getSession().getAttribute(ConstUtil.LOGIN_USER);
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8"/>
    <title>咨询记录</title> <!-- 右侧个人中心  咨询记录 标签进入 -->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/history_record.css"/>
    <!-- 数据表中数据的样式 -->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css"/>
    <!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
    <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>

    <script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
    <script type="text/javascript"
            src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
    <script type="text/javascript"
            src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
    <script type="text/javascript"
            src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
</head>
<style type="text/css">
    .kqds_table {
        width: 98%;
        margin: auto;
        /*  align:center;
            margin-left: auto;
            margin-right: auto; */
    }

    .kqds_table td {
        color: #666;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        font-weight: normal;
        line-height: 28px;
        padding: 3px 0px 5px 0px;
    }

    .kqds_table select {
        height: 28px;
        /* width:120px; */
        width: 80%;
        border: solid 1px #e5e5e5;
        border-radius: 3px;
    }


    .kqds_table input[type=text] {
        width: 80%;
    }

    textarea {
        min-height: 120px;
        max-height: 200px;
    }

    .jzRecord {
        padding-top: 5px;
    }

    #container input[readonly], #container input[disabled] { /*只读的input*/
        background: rgba(246, 246, 246, 0.42);
        cursor: not-allowed;
    }

    /* #container input[type="text"]:focus,#container select:focus,#container textarea:focus{
        box-shadow: 0 0 8px rgb(49, 165, 247);
        border-color:transparent;
    } */
    .form-control:focus {
        box-shadow: none;
        border: 1px solid #ddd;
    }

    .bottomBar {
        padding: 0;
    }

    .titleDiv {
        box-sizing: border-box;
        color: #373737;
        font-family: "Microsoft YaHei";
        height: 30px;
        position: relative;
        margin-bottom: 5px;
        border-bottom: 1px solid #ddd;
    }

    .titleDiv .title {
        font-size: 18px;
        height: 20px;
        line-height: 20px;
        float: left;
        display: block;
        margin-top: 5px;

    }

    #container {
        padding: 0 15px;
    }

    .fixed-table-container {
        border-left: 1px solid #ddd;
        border-right: 1px solid #ddd;
        border-bottom: 1px solid #ddd;
        border-radius: 6px;
        /* border-top-left-radius: 6px;
        border-top-right-radius: 6px; */
        overflow: hidden;
    }
</style>
<body>
<div id="container" class="" style="padding-top:15px;">
    <!-- <div class="titleDiv">
        <span class="title">咨询记录</span>
    </div> -->
    <!--  <div class="tableHd">咨询记录</div> -->
    <div class="tableBox">
        <table id="table" class="table-striped table-condensed table-bordered" data-height="200"></table>
    </div>
    <div class="titleDiv">
        <span class="title" style="font-size:14px;font-weight:bold;color:#777;">接诊记录</span>
    </div>
    <div class="jzRecord">
        <form id="form1">
            <table class="kqds_table">
                <input type="hidden" name="seqId" id="seqId">
                <input type="hidden" name="askstatus" id="askstatus">
                <input type="hidden" name="doctors" id="doctors">
                <input type="hidden" name="main_suit" id="main_suit">
                <input type="hidden" name="scheme" id="scheme">
                <input type="hidden" name="price" id="price">
                <input type="hidden" name="order_project" id="order_project">
                <input type="hidden" name="order_plan" id="order_plan">
                <input type="hidden" name="follow" id="follow">
                <input type="hidden" name="failreason_mark" id="failreason_mark">
                <input type="hidden" name="othermark" id="othermark">
                <tr>
                    <td style="width:10%;text-align:right;">客户姓名：</td>
                    <td style="width:23%;text-align:left;"><input type="text" name="username" id="username"
                                                                  disabled="disabled"></td>
                    <td style="width:10%;text-align:right;">挂号分类：</td>
                    <td style="width:23%;text-align:left;"><input type="text" name="recesort" id="recesort"
                                                                  disabled="disabled"></td>
                    <td style="width:10%;text-align:right;">咨询时间：</td>
                    <td style="width:23%;text-align:left;"><input type="text" style="width: 135px;" name="createtime"
                                                                  id="createtime" disabled="disabled"></td>
                </tr>
                <tr>
                    <td style="text-align:right;">
                        <span id="goRemarkTemplate" class="kqdsSearchBtn" onclick="goRemarkTemplate()" style="cursor: pointer;">情况备注<span>
                    </td>
                    <td style="text-align:left;" colspan="5">
                        <div>
                            <textarea class="form-control" style="height:200px;" name="detaildesc" id="detaildesc" rows="12" readonly="readonly"></textarea>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="3" style="text-align:left;">未成交原因：
                        <select style="width:150px" class="dict" tig="wcjyy" name="failreason1"
                                id="failreason1"></select>
                    </td>
                    <td colspan="3" style="text-align:left;">潜在开发项目:
                        <select style="width:150px" class="dict" tig="QZKFXM" name="devItem" id="devItem"></select>
                    </td>
                </tr>
                <tr style="display: none;">
                    <td style="text-align:right;">主诉：</td>
                    <td style="text-align:left;"><input type="text" name="zs" id="zs"></td>
                    <td style="text-align:right;">检查：</td>
                    <td style="text-align:left;"><input type="text" name="check" id="check"></td>
                </tr>
                <tr style="display: none;">
                    <td style="text-align:right;">建议：</td>
                    <td style="text-align:left;"><input type="text" name="jy" id="jy"></td>
                    <td style="text-align:right;">卡类：</td>
                    <td style="text-align:left;"><input type="text" name="member" id="member"></td>
                </tr>
                <tr style="display: none;">
                    <td style="text-align:right;">费用：</td>
                    <td style="text-align:left;"><input type="text" name="fy" id="fy"></td>
                    <td style="text-align:right;">开发：</td>
                    <td style="text-align:left;"><input type="text" name="kaifa" id="kaifa"></td>
                </tr>

            </table>
        </form>
    </div>
    <div class="bottomBar" id="bottomBarDdiv" style="text-align: left;">
    </div>
</div>
</div>
</body>
<script type="text/javascript">
    var contextPath = "<%=contextPath%>";
    var perid = "<%=person.getSeqId()%>";
    var tag = '';
    var pageurl = '<%=contextPath%>/KQDS_ReceiveInfoAct/NoselectPage.act';
    var $table = $('#table');
    var onclickrowOobj = "";
    var onclickrowOobj2 = "";
    var indexEdit = "0";
    var listbutton = window.parent.listbutton; //父页面传值
    var regno = "";
    var isdelreg = 0;
    $(function () {
        onclickrowOobj = window.parent.onclickrowOobj;
        //如果选中的不是挂号记录
        if (onclickrowOobj == "" || onclickrowOobj.ifcost == null) {
            regno = onclickrowOobj.regno;
        } else {
            regno = onclickrowOobj.seqId;
            isdelreg = onclickrowOobj.del;
        }
        getButtonPower();
        //传染病是否标识
        getrecord();
        initDictSelectByClass();
        getlist(onclickrowOobj);
        //子页面高度传给父页面

        $(window).resize(function () {
            setHeight();
        });
    });
    //模板
    function goRemarkTemplate(){
        var dateNow=getNowDay(new Date());
        if(!onclickrowOobj2.seqId){
            parent.layer.alert('请选择咨询记录!');
            return false;
        }
        var createtime=onclickrowOobj2.createtime.substr(0,10);
        if(dateNow!=createtime){
            parent.layer.alert('咨询时间已过!');
            return false;
        }
        parent.layer.open({
            title:"添加情况备注模板",
            type:2,
            closeBtn:1,
            content:contextPath + "/KQDS_UserDocumentAct/toTemplate.act",
            area:['45%','70%'],
            cancel: function(){
            }
        });
    }
    function getlist(onclickrowOobj) {
        pageurl = pageurl + "?usercode=" + onclickrowOobj.usercode;

        $('#table').bootstrapTable({
            url: pageurl,
            dataType: "json",
            onLoadSuccess: function (data) { //加载成功时执行
                $("#table tbody tr td:eq(0)").click();
                setHeight();
                /*表格载入时，设置表头的宽度 */
                setTableHeaderWidth(".tableBox");
            },
            columns: [
                {
                    title: '到院门诊',
                    field: 'organizationname',
                    align: 'center',

                    visible: true,
                    switchable: false,
                    formatter: function (value, row, index) {
                        if (value != "" && value != null) {
                            return '<span title="' + value + '">' + value + '</span>';
                        }
                    }
                },
                {
                    title: '咨询时间',
                    field: 'createtime',
                    align: 'center',
                    formatter: function (value, row, index) {
                        return '<span>' + value.substring(0, 10) + '</span>';
                    }
                },
                {
                    title: '接诊状态',
                    field: 'askstatus',
                    align: 'center',

                    formatter: function (value, row, index) {
                        if (value == 0) {
                            return '<span style="color:red">未接诊</span>';
                        } else {
                            return '<span style="color:green">已接诊</span>';
                        }
                    }
                },
                {
                    title: '成交状态',
                    field: 'regno',
                    align: 'center',

                    formatter: function (value, row, index) {
                        var data = getStatus(value);
                        var status = data.substring(0, data.indexOf("-"));
                        if (status == "未成交") {
                            return '<span style="color:red">未成交</span>';
                        } else {
                            return '<span style="color:green">已成交</span>';
                        }
                    }
                },
                {
                    title: '消费状态',
                    field: 'regno',
                    align: 'center',

                    formatter: function (value, row, index) {
                        var data = getStatus(value);
                        var status = data.substring(data.indexOf("-") + 1);
                        if (status == "未收费") {
                            return '<span style="color:red">未消费</span>';
                        } else {
                            return '<span style="color:green">已消费</span>';
                        }
                    }
                },
                {
                    title: '患者姓名',
                    field: 'username',
                    align: 'center',

                    formatter: function (value, row, index) {
                        return '<span class="name" title="' + value + '">' + value + '</span>';
                    }
                },
                {
                    title: '挂号分类',
                    field: 'recesortname',
                    align: 'center',

                    formatter: function (value, row, index) {
                        if (value) {
                            return '<span>' + value + '</span>';
                        }
                    }
                },
                {
                    title: '接诊咨询',
                    field: 'askpersonname',
                    width: 80,
                    align: 'center',

                    formatter: function (value, row, index) {
                        if (value) {
                            return '<span class="name">' + value + '</span>';
                        }
                    }
                },
                {
                    title: '情况备注',
                    field: 'detaildesc',
                    align: 'center',

                    formatter: function (value, row, index) {
                        if (value != "" && value != null) {
                            if (value.length > 20) {
                                return '<span title="' + value + '" style="width:100px;cursor:pointer;">' + value.substring(0, 20) + '...</span>';
                            } else {
                                return '<span title="' + value + '" style="width:100px;cursor:pointer;">' + value + '</span>';
                            }
                        }
                    }
                },
                {
                    title: '未成交原因',
                    field: 'failreason1name',
                    align: 'center',

                    formatter: function (value, row, index) {
                        if (value) {
                            return '<span>' + value + '</span>';
                        }
                    }
                },
                {
                    title: '潜在开发项目',
                    field: 'devitemname',
                    align: 'center',

                    formatter: function (value, row, index) {
                        if (value) {
                            return '<span>' + value + '</span>';
                        }
                    }
                }
            ]
        }).on('click-row.bs.table',
            function (e, row, element) {
                $('.success').removeClass('success'); //去除之前选中的行的，选中样式
                $(element).addClass('success'); //添加当前选中的 success样式用于区别
                var index = $('#table').find('tr.success').data('index'); //获得选中的行
                onclickrowOobj2 = $('#table').bootstrapTable('getData')[index];
                jzjl(onclickrowOobj2);
            });

    }
    function getStatus(regno) {

        var regObj = getRegObjBySeqId(regno);

        var status = "未成交",
            ifcost = "未收费";

        if (regObj.cjstatus == 1) {
            status = "已成交";
        }
        if (regObj.ifcost == 1) {
            ifcost = "已收费";
        }

        return status + "-" + ifcost;
        ;
    }

    // 失去焦点时自动保存
    $("#detaildesc").blur(function () {
        if ($("#failreason1").val() == "" && $("#detaildesc").val() == "") {
            $("#askstatus").val(0);
        } else {
            $("#askstatus").val(1);
        }
        if (perid != onclickrowOobj2.askperson) {
            return false;
        }
        var param = $('#form1').serialize();
        param += "&isauto=1"
        var url = '<%=contextPath%>/KQDS_ReceiveInfoAct/insertOrUpdate.act?' + param;
        $.axseSubmit(url, null,
            function () {
            },
            function (r) {
            },
            function () {
            });
    });


    function save() {

        if (perid != onclickrowOobj2.askperson) {
            layer.alert('不能编辑其他咨询的咨询记录');
            return false;
        }
        if ($("#failreason1").val() == "" && $("#detaildesc").val() == "") {
            $("#askstatus").val(0);
        } else {
            $("#askstatus").val(1);
        }
        var param = $('#form1').serialize();
        var url = '<%=contextPath%>/KQDS_ReceiveInfoAct/insertOrUpdate.act?' + param;
        $.axseSubmit(url, null,
            function () {
            },
            function (r) {
                if (r.retState == "0") {
                    layer.alert('保存成功', {

                        end: function () {
                            refresh();
                        }
                    });
                } else {
                    layer.alert('保存失败');
                }
            },
            function () {
                layer.alert('保存失败');
            });
    }

    function jzjl(onclickrowOobj2) {
        var data = getStatus(onclickrowOobj2.regno);
        // 潜在开发项目
        $("#devItem").val(onclickrowOobj2.devItem);

        $("#cjzt").val(data.substring(0, data.indexOf("-")));
        $("#sfzt").val(data.substring(data.indexOf("-") + 1));
        $("#username").val(onclickrowOobj2.username);
        $("#askperson").val(onclickrowOobj2.askpersonname);
        var askstatus = "未接诊";
        if (onclickrowOobj2.askstatus == 1) {
            askstatus = "已接诊";
        }
        $("#askstatusInput").val(askstatus);
        $("#deptno").val(onclickrowOobj2.deptnoname);
        $("#recesort").val(onclickrowOobj2.recesortname);
        $("#createtime").val(onclickrowOobj2.createtime);
        $("#failreason1").val(onclickrowOobj2.failreason1).trigger("change");
        $("#detaildesc").val(onclickrowOobj2.detaildesc);
        $("#seqId").val(onclickrowOobj2.seqId);
    }

    function refresh() {
        $table.bootstrapTable('refresh', {
            'url': pageurl
        });
    }

    function getrecord() {
        $.ajax({
            type: "POST",
            url: '<%=contextPath%>/KQDS_UserDocumentAct/getrecord.act',
            data: {usercode: onclickrowOobj.usercode},
            dataType: "json",
            async: false,
            success: function (data) {
                //console.log(JSON.stringify(data));
                if (data.contagion == "1") {
                    //console.log("11111");
                    $("#contagion").css("display", "none");
                }
            }, error: function (data) {
                layer.alert(data.msg);
            }
        });
    }

    function updaterecord(status) {
        layer.confirm('是否取消传染性疾病登记？', {
            btn: ['是', '否'] //按钮
        }, function () {
            $.ajax({
                type: "POST",
                url: '<%=contextPath%>/KQDS_UserDocumentAct/updaterecord.act',
                data: {usercode: onclickrowOobj.usercode, contagion: status},
                dataType: "json",
                success: function (data) {
                    layer.alert("操作成功!");
                }, error: function (data) {
                    layer.alert("操作失败!");
                }
            });
        }, function () {

        });
    }

    function getButtonPower() {
        var menubutton1 = "";
        for (var i = 0; i < listbutton.length; i++) {
            //console.log("获取到的按钮=" + listbutton[i].qxName);
            if (listbutton[i].qxName == "contagion") {
                menubutton1 += '<a href="javascript:void(0);" class="kqdsSearchBtn" id="contagion" onclick="updaterecord(1)">患有传染性疾病</a>';
            } else if (listbutton[i].qxName == "rectified") {
                menubutton1 += '<a href="javascript:void(0);" class="kqdsSearchBtn" id="rectified" onclick="updaterecord(0)">更正患病情况</a>';
            } else if (listbutton[i].qxName == "zxjl_bc" && isdelreg == 0) {
                menubutton1 += '<a href="javascript:void(0);" class="kqdsSearchBtn" id="issave" onclick="save()" style = "margin-left: 20%;">保存</a>';
            }
        }
        $("#bottomBarDdiv").append(menubutton1);
    }

    //调整表格高度
    function setHeight() {
        $("#table").bootstrapTable("resetView", {
            height: $(window).outerHeight() - $(".titleDiv").outerHeight() * 2 - $(".jzRecord").outerHeight() - $("#bottomBarDdiv").outerHeight() - 20
        });
    }
</script>
</html>
