<!--wl整理 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/inc/classImport.jsp" %>
<%
    String contextPath = request.getContextPath();
    if (contextPath.equals("")) {
        contextPath = "/kqds";
    }
    YZPerson person = SessionUtil.getLoginPerson(request);
    //获取从左侧菜单点击带过来的菜单id
    String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css"/>

    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css"/>
    <!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
    <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/Load.js"></script>
    <!-- select搜索筛选 -->
    <link rel="stylesheet" type="text/css"
          href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css"/>
    <title>信息查询</title>
</head>
<style type="text/css">
    .kqds_table td {
        color: #666;
        padding: 1px 2px 2px 2px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        font-weight: normal;
        line-height: 28px;
    }

    /*查询条件中的样式  */
    .searchWrap .formBox > section > ul.conditions > li {
        padding: 3px 0;
    }

    .searchWrap .formBox > section > ul.conditions > li > span {
        width: 62px;
        text-align: right;
    }

    .searchWrap .formBox > section > ul.conditions > li > input[type=text],
    .searchWrap .formBox > section > ul.conditions > li > select {
        width: 94px;
    }

    @media screen and (max-width: 1390px) {
        .searchWrap .formBox > section > ul.conditions > li > span {
            width: 55px;
            text-align: right;
            font-size: 11px;
            height: 24px;
            line-height: 24px;
        }

        .searchWrap .formBox > section > ul.conditions > li > input[type=text],
        .searchWrap .formBox > section > ul.conditions > li > select {
            width: 82px;
            font-size: 11px;
            padding: 0 3px 0 5px;
            height: 24px;
            line-height: 24px;
        }
    }

    @media screen and (max-width: 1100px) {
        .searchWrap .formBox > section > ul.conditions > li > span {
            width: 51px;
            text-align: right;
            font-size: 10px;
            height: 22px;
            line-height: 22px;
        }

        .searchWrap .formBox > section > ul.conditions > li > input[type=text],
        .searchWrap .formBox > section > ul.conditions > li > select {
            width: 73px;
            font-size: 10px;
            padding: 0 3px 0 5px;
            height: 22px;
            line-height: 22px;
        }
    }

    .centerWrap .columnWrap .columnBd ul {
        overflow: visible;
    }

    .centerWrap .columnWrap .columnBd ul li {
        margin-left: 0px;
    }

    .centerWrap .columnWrap {
        margin-bottom: 0px;
    }

    .fixed-table-pagination .btn-group .dropdown-menu {
        min-width: auto;
    }

    /* 搜索框select */
    .searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {
        width: 94px;
    }

    .searchSelect > .btn {
        width: 94px;
        height: 26px;
        border: solid 1px #e5e5e5;
    }

    .bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
        color: #999;
        height: 26px;
    }

    .pull-left {
        float: left !important;
        color: #333;
    }

    .btn-group > .btn:first-child:hover {
        background: white;
    }

    .searchWrap .text {
        position: static !important;
        left: 0px;
        top: 9px;
        color: rgb(31, 32, 33);
    }

    /* 	解決标签查询中下拉框悬浮 */
    .searchWrap {
        overflow: visible;
    }

    .formBox {
        overflow: visible;
    }

    .searchWrap .formBox > section {
        height: 100px;
    }

    .searchWrap .formBox > section > ul.conditions {
        overflow: visible;
        height: 100%;
        position: relative;
    }

    /* 	.searchWrap .formBox>section{ */
    /* 		height: 130px */
    /* 	} */

</style>
<body>
<div id="container">
    <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
                <div class="columnHd">
                    <span class="title">信息查询</span>
                </div>

                <div class="columnBd">
                    <div class="tableBox">
                        <table id="table" class="table-striped table-condensed table-bordered"></table>
                    </div>
                </div>
            </div>
            <div id="gongzuol">
                <div class="columnBd">
                    <ul class="dataCountUl" id="dataCount">
                        <li>总记录数:<span id="zong">0</span></li>
                    </ul>
                </div>
            </div>
            <div class="searchWrap">
                <!-- <div class="cornerBox">查询条件</div> -->
                <div class="searchToggleBtnGroup">
                	<span class="ptcx checked">
                		通用查询
                	</span>
                    <span>
                		高级查询
                	</span>
                </div>
                <div class="formBox">
                    <section>
                        <ul class="conditions">

                            <li>
                                <span>所属门诊</span>
                                <select id="organization" name="organization"></select>
                            </li>
                            <li class="birthDate">
                                <span>创建时间</span>
                                <input type="text" placeholder="开始日期" id="starttime" readonly>
                            </li>
                            <li class="birthDate">
                                <span>到</span>
                                <input type="text" placeholder="结束日期" id="endtime" readonly>
                            </li>
                            <li>
                                <span>模糊查询</span>
                                <input type="text" id="username" class="username" placeholder="姓名/手机号">
                            </li>

                            <li class="toggleTr">
                                <span>医生</span>
                                <select id="doctor" name="doctor" class="patients-source select2 dict searchSelect"
                                        data-live-search="true" title="请选择"></select>
                                <!-- <input type="hidden" name="doctor" id="doctor" placeholder="医生" title="医生" class="form-control" style="width: 80%;" value=""/>
                                <input  type="text"  id="doctorDesc" name="doctorDesc" placeholder="医生" readonly  onClick="javascript:single_select_user(['doctor', 'doctorDesc'],'',1);"> -->
                                <!-- 				    				<select class="patients-source select2 dict"  title="医生" placeholder="医生" name="doctor" id="doctor"> -->
                                <!-- 									</select> -->
                            </li>
                            <li class="toggleTr birthDate">
                                <span title="最近到院时间">初诊时间</span>
                                <input type="text" placeholder="开始日期" id="dystarttime" readonly>
                            </li>
                            <li class="toggleTr birthDate">
                                <span>到</span>
                                <input type="text" placeholder="结束日期" id="dyendtime" readonly>
                            </li>
                            <li class="toggleTr">
                                <span>建档人</span>
                                <input type="hidden" name="jdr" id="jdr" placeholder="建档人" title="建档人"
                                       class="form-control" value=""/>
                                <input type="text" id="jdrDesc" name="jdrDesc" placeholder="建档人" readonly
                                       onClick="javascript:single_select_user(['jdr', 'jdrDesc'],'single',1);">
                            </li>
                            <li class="toggleTr" id="tool">
                                <span>患者来源</span>
                                <select id="devchannel" name="devchannel"
                                        class="patients-source select2 dict searchSelect" resource="hzly"
                                        data-live-search="true"
                                        onchange="getSubDictSelectSearch('devchannel','nexttype');"
                                        title="请选择"></select>
                                <!-- 			    					<select class="patients-source select2 dict" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype');"> -->
                                <!-- 									</select> -->
                            </li>
                            <li class="toggleTr" id="toolSon">
                                <span>子分类</span>
                                <select id="nexttype" name="nexttype" class="select2 dict searchSelect"
                                        data-live-search="true" title="请选择"></select>
                                <!-- 									<select class="select2 dict" name="nexttype" id="nexttype"> -->
                                <!-- 										<option value="">请选择</option> -->
                                <!-- 									</select> -->
                            </li>
                            <li class="toggleTr">
                                <span>有无回访</span>
                                <select name="ywhf" id="ywhf">
                                    <option value="">请选择</option>
                                    <option value="0">无</option>
                                    <option value="1">有</option>
                                </select>
                            </li>
                            <li class="toggleTr">
                                <span>第一咨询</span>
                                <select id="askperson" name="askperson"
                                        class="patients-source select2 dict searchSelect" data-live-search="true"
                                        title="请选择"></select>
                                <!-- <input type="hidden" name="askperson" id="askperson" placeholder="咨询" title="咨询" class="form-control" style="width: 80%;" value=""/>
                                <input  type="text"  id="askpersonDesc" name="askpersonDesc" placeholder="第一咨询" readonly  onClick="javascript:single_select_user(['askperson', 'askpersonDesc'],'',1);"></input> -->
                                <!-- 				    				<select class="patients-source select2 dict"  title="咨询" placeholder="第一咨询" name="askperson" id="askperson"> -->
                                <!-- 									</select> -->
                            </li>
                        </ul>
                    </section>
                    <div class="btnBar" id="bottomBarDdiv">

                    </div>
                </div>
            </div>
        </div>
        <!--中间模块开关按钮  -->
        <div class="middleWrap">
            <div id="collectBtn" class="collectBtn">
                <span id="trangle"></span>
            </div>
        </div>
        <!--右侧信息浏览-->
        <div class="lookInfoWrap">
            <%@include file="/inc/rightPartInfo.jsp" %>
        </div>
    </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/Javascript"
        src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript"
        src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript"
        src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript"
        src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/DataLazyLoad.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
</body>
<script type="text/javascript">
    var listbutton;
    var contextPath = "<%=contextPath%>";
    var deptId = '<%=person.getDeptId()%>';
    // console.log(deptId+"-----deptId");
    var onclickrowOobj = "";
    var selectedrows = "";
    var nowday;
    //初始化表头，返回空的数据
    var nullUrl = contextPath + '/UtilAct/intTableHeader.act';
    var pageurl = contextPath + '/KQDS_UserDocumentAct/selectWithNopage2.act';
    var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
    var personrole = "<%=person.getUserPriv()%>";
    var personroleother = "<%=person.getUserPrivOther()%>";

    var loadperson = '<%=person.getUserPriv()%>';
    var load = loadperson.split(",");//登陆这权限
    var allowedperson = '<%=SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK)%>';
    var allowed = allowedperson.split(",");//允许权限
    // console.log(allowed+"----allowed");
    var allowedBtn = ["afa8bf41-af02-4504-99b9-b8b69de40aca", "34379b48-ac6f-4b61-9a99-1b427183ef25", "eea5c430-1d44-40bb-ba16-0ffad8b09697", "190e9256-2aeb-453a-9e8a-e35c0c7a48dc", "794d8599-9b96-4461-b2c1-05b5c373d486", "5d80d52d-9ca4-4389-be27-ae4a50d7e8c6", "46721f65-18ec-463f-b18c-e165fa589fbe", "388fcdf9-4969-46c6-b767-a7c3b7905f2f", "005e6673-15ab-46d7-b0f4-bb1ed99556a4", "713a8067-6890-4157-b7ee-b7d981401c8a", "6ff236ec-da8f-40d8-ba0b-e9105bd89f5d", "c17de419-b6d4-4eea-b2f6-4f7422b94914", "2589c8f7-f4ec-4247-8866-067550cc5ddb", "4b32630c-13ac-444e-8dcc-0f3844a2d2bb", "b586afb5-6b6e-442a-9387-246c5dfa2db9", "a0c50df9-3ba1-47ac-a739-6b2d17e3bc91", "de063632-8dc5-45f4-a616-b27ada252406", "5bcd3e00-a179-4265-8a9d-7fc966541016", "3ee5815c-f2b1-40c7-844a-489d0ff3f256", "fc47af55-26cc-4617-863d-6ac5be7b4d34", "0dfa1271-72b7-4593-b5d7-328b2d9567d5", "1f7fd635-241d-4969-93ae-4ffeb3b5b78f", "45a11365-4add-4a2e-81c4-ad12349a6592", "0d82d20e-404a-43d0-a977-99c208b1690a"];
    var loc = new Location();
    var static_organization = '<%=ChainUtil.getCurrentOrganization(request)%>';
    var isClick = true;

    $(function () {
        $("input[type='text']").attr("autocomplete","off");  //去掉input输入框的历史记录  lutian
        // 连锁门诊下拉框
        initHosSelectListNoCheckWithNull('organization', static_organization);
        //initHosSelectList4Front('organization'); // 连锁门诊下拉框

        initDictSelectByClass(); // 患者来源
        initDictSelectByClassIsBtnshow(existornotBtn); //患者来源根据部门显示

        initSysUserByDeptId($("#doctor"), "doctor"); //初始化医生选项下拉框
        initSysUserByDeptId($("#askperson"), "consultation"); //初始化咨询选项下拉框
        //获取当前页面所有按钮
        getButtonAllCurPage("<%=menuid%>");
        /* 左侧个人中心的按钮样式控制js已经被抽取到rightPartInfo.jsp页面中 */

        //时间选择
        $(".birthDate input").datetimepicker({
            language: 'zh-CN',
            format: 'yyyy-mm-dd',
            minView: 2,
            autoclose: true,
            //选中之后自动隐藏日期选择框
            pickerPosition: "top-right"
        });

        //绑定两个时间选择框的chage时间
        $("#starttime,#endtime").change(function () {
            timeCompartAndFz("starttime", "endtime");
        });
        togglemodel.initial("xxcx", pageurl);
        //4、表格初始化
        initTable(pageurl);

        $(window).resize(function () {
            setWidth();
            setHeight();
            /*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        });

        /* 常用查询 按钮 高级查询  按钮*/
        initSearchToggleBtnGroup();
        $('.searchSelect').selectpicker("refresh");//搜索初始化刷新2019.10.19--licc
    });

    //判断当前人员权限
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

    //判断当前人员权限是否有查看患者来源和子分类等着资源的按钮
    var totalbtn = load.concat(allowedBtn);

    function isExistBtn(totalbtn) {
        var exist = {};
        for (var i in totalbtn) {
            if (exist[totalbtn[i]]) {
                return true;
            }
            exist[totalbtn[i]] = true;
        }
        return false;
    }

    var existornotBtn = isExistBtn(totalbtn);//资源隐藏按钮
    if (!existornotBtn) {
        $('#tool').attr('class', 'toole').attr('style', 'display:none');
        $('#toolSon').attr('class', 'toolSone').attr('style', 'display:none');
    } else {
    }

    // console.log(existornotBtn+"------existornotBtn");
    /**
     * 初始化患者来源 （专属客户部人员）licc 2020.1.15
     */
    function initDictSelectByClassIsBtnshow(existornotBtn, operFlag) {
        if ($(".dict").length > 0) {
            for (var i = 0; i < $(".dict").length; i++) {
                var dict = $(".dict").eq(i);
                // :eq() 选择器选取带有指定 index 值的元素。index值从 0 开始，所有第一个元素的 index 值是 0（不是1）。
                var type = dict.attr("resource");
                var url = contextPath + "/YZDictAct/getListByParentCode.act?parentCode=" + type;
                $.axse(url, null,
                    function (data) {
                        var list = data.list;
                        if (list != null && list.length > 0) {
                            dict.empty();
                            dict.append("<option value=''>请选择</option>");
                            for (var j = 0; j < list.length; j++) {
                                var optionStr = list[j];
                                if (loadperson == "c17de419-b6d4-4eea-b2f6-4f7422b94914" || loadperson == "afa8bf41-af02-4504-99b9-b8b69de40aca") {
                                    //根据登录人员是前台
                                    if (optionStr.seqId == "sc569" || optionStr.seqId == "zrdz510" || optionStr.seqId == "lpyjs106" || optionStr.seqId == "qdzz13" || optionStr.seqId == "ptlz562" || optionStr.seqId == "pyjs900" || optionStr.seqId == "nbygkf734" || optionStr.seqId == "yyyg526" || optionStr.seqId == "wzqdjs66") {
                                        dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                                    } else {
                                        dict.append("<option value='" + optionStr.seqId + "' style='display:none;'>" + optionStr.dictName + "</option>");
                                    }
                                } else if (loadperson == "005e6673-15ab-46d7-b0f4-bb1ed99556a4" || loadperson == "388fcdf9-4969-46c6-b767-a7c3b7905f2f") {
                                    //根据登录人员是客户部
                                    if (optionStr.seqId == "lpyjs106") {
                                        dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                                    } else {
                                        dict.append("<option value='" + optionStr.seqId + "' style='display:none;'>" + optionStr.dictName + "</option>");
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


    function initTable(requrl) {
        //初始化表格所在div
        $('#table').bootstrapTable({
            url: requrl,
            dataType: "json",
            pagination: true,//是否显示分页（*）
            pageSize: 25,
            pageList: [10, 15, 20, 25, 100],//可以选择每页大小
            singleSelect: false,
            queryParams: queryParamsB,
            sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
            paginationShowPageGo: true,
            onLoadSuccess: function (data) { //加载成功时执行
                //解除查询按钮禁用 lutian
                if(data){
                    $("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer").css("pointer-events","auto");
                    $("#query").text("查询");
                }

                //判断登录
                var existornot = isExist(total);
                if (!existornot) {
                    $('#table').bootstrapTable('hideColumn', 'devchannelname');
                    $('#table').bootstrapTable('hideColumn', 'nexttypename');
//         		$('#tool').attr('class','toole').attr('style','display:none');
//         		$('#toolSon').attr('class','toolSone').attr('style','display:none');
                } else {
                    /* console.log("------------判断登录"); */
                }

                //if(nowpage == 0 && data.total>0){
                //	maxpage = Math.floor(data.total/pagesize)+1;
                $("#zong").html(data.total);
                // }
                //分页加载
                /* showdata("table",data.rows);
                $("#table").bootstrapTable("resetView",{
                    height:$(window).outerHeight()-$(".searchWrap").outerHeight()-$("#gongzuol").outerHeight()-$(".columnWrap .columnHd").outerHeight()-35
                }); */
                //计算主体的宽度
                setWidth();
                setHeight();
                /*表格载入时，设置表头的宽度 */
                setTableHeaderWidth(".tableBox");
            },
            columns: [
                {field: ' ', checkbox: true, formatter: stateFormatter},
                {
                    title: 'seqId',
                    field: 'seqId',
                    align: 'center',
                    visible: false,
                    switchable: false
                },
                {
                    title: '序号',
                    align: "center",
                    width: 40,
                    formatter: function (value, row, index) {
                        /* return index + 1; */
                        var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                        var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                        return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
                    }
                },
                {
                    title: '建档时间',
                    field: 'createtime',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            return '<span>' + value + '</span>';
                        } else {
                            return "";
                        }
                    }
                },
                {
                    title: '初诊时间',
//          field: 'lasttime',
                    field: 'regcreatetime',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            return '<span>' + value + '</span>';
                        } else {
                            return "";
                        }
                    }
                },
                {
                    title: '病人编号',
                    field: 'usercode',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            html = '<span>' + value + '</span>';
                            return html;
                        }
                    }
                },
                {
                    title: '病历号',
                    field: 'blcode',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    visible: false,
                    formatter: function (value, row, index) {
                        return '<span class="name" title="' + value + '">' + value + '</span>';
                    }
                },
                {
                    title: '姓名',
                    field: 'username',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            html = '<span class="name">' + value + '</span>';
                            return html;
                        }
                    }
                },
                {
                    title: '标识',
                    field: 'iscreatelclj',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        //console.log("value="+value+",row="+JSON.stringify(row)+",index="+index);
                        var img = '';
                        if (value != "" && value != null) {
                            img += '<img class="iscreatelclj" onclick="skip(\'' + row.username + '\')" src= ' + contextPath + '/static/image/kqdsFront/tag/clinical.jpg/>';
                        }
                        if (row.kefu != "" && row.kefu != null) {
                            img += '<img class="iscreatelclj" src= ' + contextPath + '/static/image/kqdsFront/tag/customerservice.jpg/>';
                        }

                        return img == "" ? "-" : img;
                    },
                    cellStyle: {
                        css: {"display": "flex", "flex-direction": "row"}
                    }
                },
                {
                    title: '性别',
                    field: 'sex',
                    align: 'center',
                    sortable: true,
                    formatter: function (value) {
                        return '<span>' + value + '</span>';
                    }
                },
                {
                    title: '年龄',
                    field: 'age',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == "0") {
                            return "<span>-</span>";
                        } else {
                            return "<span>" + value + "</span>";
                        }
                    }
                },
                {
                    title: '第一咨询',
                    field: 'askperson',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            html = '<span class="name">' + value + '</span>';
                            return html;
                        }
                    }
                },
                {
                    title: '医生',
                    field: 'doctor',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            html = '<span class="name">' + value + '</span>';
                            return html;
                        }
                    }
                },
                {
                    title: '手机号码1',
                    field: 'phonenumber1',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value != null || value != "") {
                            if (canlookphone == 0) {
                                return '<span title="' + value + '">' + value + '</span>';
                            } else {
                                return '<span>-</span>';
                            }
                        } else {
                            return '<span>-</span>';
                        }
                    }
                },
                {
                    title: '手机号码2',
                    field: 'phonenumber2',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value != null || value != "") {
                            if (canlookphone == 0) {
                                return '<span title="' + value + '">' + value + '</span>';
                            } else {
                                return '<span>-</span>';
                            }
                        } else {
                            return '<span>-</span>';
                        }
                    }
                },
                {
                    title: '患者来源',
                    field: 'devchannelname',
                    align: 'center',

                    sortable: true,
                    formatter: function (value) {
                        return "<span class='source'>" + value + "</span>";
                    }
                },
                {
                    title: '来源子分类',
                    field: 'nexttypename',
                    align: 'center',

                    sortable: true,
                    formatter: function (value) {
                        return '<span class="source">' + value + '</span>'
                    }
                },
                {
                    title: '省',
                    field: 'provincename',
                    align: 'center',

                    sortable: true,
                    formatter: function (value) {
                        return "<span class='name'>" + value + "</span>";
                    }
                },
                {
                    title: '市',
                    field: 'cityname',
                    align: 'center',

                    sortable: true,
                    formatter: function (value) {
                        return "<span class='name'>" + value + "</span>";
                    }
                },
                {
                    title: '区',
                    field: 'townname',
                    align: 'center',

                    sortable: true,
                    formatter: function (value) {
                        return "<span class='name'>" + value + "</span>";
                    }
                },
                {
                    title: '街道',
                    field: 'streetName',
                    align: 'center',

                    sortable: true,
                    formatter: function (value, row, index) {
                        return '<span class="name">' + value + '</span>';
                    }
                },
                {
                    title: '建档人',
                    field: 'createuser',
                    align: 'center',
                    sortable: true,
                    formatter: function (value) {
                        return '<span>' + value + '</span>';
                    }
                },
                {
                    title: '有无回访',
                    field: 'ywhf',
                    align: 'center',

                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == "无回访") {
                            return "<span class='label label-danger'>无回访</span>";
                        } else {
                            return "<span class='label label-success'>有回访</span>";
                        }
                    }
                }, {
                    title: '客服',
                    field: 'kefuname',
                    align: 'center',

                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == "") {
                            return "<span>-</span>";
                        } else {
                            return "<span>" + value + "</span>";
                        }
                    }
                }]
        }).on('click-row.bs.table',
            function (e, row, element) {
                $('.success').removeClass('success'); //去除之前选中的行的，选中样式
                $(element).addClass('success'); //添加当前选中的 success样式用于区别
                var index = $('#table').find('tr.success').data('index'); //获得选中的行
                onclickrowOobj = $('#table').bootstrapTable('getData')[index];
                $('#tabIframe').attr('src', $('#tabIframe').attr('src')); //个人中心
            });
    }

    function queryParams(params) {
        var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            organization: $('#organization').val(),
            starttime: $('#starttime').val(),
            endtime: $('#endtime').val(),
            dystarttime: $('#dystarttime').val(),
            dyendtime: $('#dyendtime').val(),
            username: $('#username').val(),
            askperson: $('#askperson').val(),
            doctor: $('#doctor').val(),
            devchannel: $('#devchannel').val(),
            nexttype: $('#nexttype').val(),
            //province: $('#province').val(),
            //city: $('#city').val(),
            ywhf: $('#ywhf').val(),
            jdr: $('#jdr').val()
        };
        return temp;
    }

    function queryParamsB(params) {
        var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset, //页码
            pageIndex: params.offset / params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)
            sortName: this.sortName,
            sortOrder: this.sortOrder,
            organization: $('#organization').val(),
            starttime: $('#starttime').val(),
            endtime: $('#endtime').val(),
            dystarttime: $('#dystarttime').val(),
            dyendtime: $('#dyendtime').val(),
            username: $('#username').val(),
            askperson: $('#askperson').val(),
            doctor: $('#doctor').val(),
            devchannel: $('#devchannel').val(),
            nexttype: $('#nexttype').val(),
            //province: $('#province').val(),
            //city: $('#city').val(),
            ywhf: $('#ywhf').val(),
            jdr: $('#jdr').val()
        };
        return temp;
    }

    function searchHzda() {
        loadedData = [];
        nowpage = 0;
        var starttime = $('#starttime').val();
        var endtime = $('#endtime').val();
        var dystarttime = $('#dystarttime').val();
        var dyendtime = $('#dyendtime').val();
        var username = $('#username').val();
        var askperson = $('#askperson').val();
        var doctor = $('#doctor').val();
        var devchannel = $('#devchannel').val();
        var nexttype = $('#nexttype').val();
        //var province = $('#province').val();
        //var city = $('#city').val();
        var ywhf = $('#ywhf').val();
        var jdr = $('#jdr').val();

        if (starttime == "" && endtime == "" && dystarttime == "" && dyendtime == "" && username == "" && askperson == "" && doctor == "" && devchannel == "" && nexttype == "" && jdr == "" && ywhf == "") {
            layer.alert('请选择查询条件!');
            return false;
        }
        //查询中，禁止查询按钮点击 lutian
        $("#query").attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none");
        $("#query").text("查询中");
        $('#table').bootstrapTable('refresh', {
            'url': pageurl
        });
    }

    function clean() {
        //定位滚动条位置
        /* $(".fixed-table-body").animate({
            scrollTop: 400
            }, 1000); */
        $(".fixed-table-body").scrollTop(400);
        var rgvalue = $("#organization").val();
        $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
        $("#organization").val(rgvalue);

//  清空搜索下拉框
        $(".searchSelect li.selected").removeClass("selected");
        $(".searchSelect button .pull-left").text("请选择");
    }

    var loadIndex='';
    function download() {
        layer.msg('数据导出中，请等待');
        //loadIndex = layer.load(0, {shade: false});
        isClick = false;
    }
    function disload() {
        layer.close(loadIndex);
        layer.msg('数据导出完毕');
        isClick = true;
    }

    //导出
    function exporttable() {
        if(isClick) {
            isClick = false;
        var fieldArr = [];
        var fieldnameArr = [];
        $('#table thead tr th').each(function () {
            var field = $(this).attr("data-field");
            if (field != "") {
                fieldArr.push(field);//获取字段
                fieldnameArr.push($(this).children()[0].innerText);//获取字段中文
            }
        });
        var param = JsontoUrldata(queryParams());
        //location.href = pageurl+"?flag=exportTable&fieldArr="+JSON.stringify(fieldArr)+"&fieldnameArr="+JSON.stringify(fieldnameArr)+"&"+param;
        var url = pageurl + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
        download();
        var xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);    // 也可用POST方式
        xhr.responseType = "blob";
        xhr.onload = function () {
            if (this.status === 200) {
                var blob = this.response;
                // if (navigator.msSaveBlob == null) {
                var a = document.createElement('a');
                //var headerName = xhr.getResponseHeader("Content-disposition");
                //var fileName = decodeURIComponent(headerName).substring(20);
                a.download = "信息查询";
                a.href = URL.createObjectURL(blob);
                $("body").append(a);    // 修复firefox中无法触发click
                a.click();
                URL.revokeObjectURL(a.href);
                $(a).remove();
                // } else {
                //     navigator.msSaveBlob(blob, "信息查询");
                // }
            }
            disload();
        };
        xhr.send();
        }
    }

    //档案合并
    function dahb() {
        layer.open({
            type: 2,
            title: '档案合并',
            shadeClose: false,
            shade: 0.6,
            area: ['90%', '90%'],
            content: contextPath + '/KQDS_UserDocumentAct/toDahb.act'
        });
    }

    //跳转临床页面
    function skip(username) {
        parent.Catalogue()
        parent.secondLevelDirectory()
        window.location.href = contextPath + '/ClinicPathControllerAct/toLcljPageFetchInfo.act?menuId=600309&&username=' + username;
    }

    //获取选中行的usercode
    function getIdSelections() {
        return $.map($('#table').bootstrapTable('getSelections'),
            function (row) {
                return row;
            });
    }

    //转诊咨询
    function zzzx() {
        selectedrows = getIdSelections();
        if (onclickrowOobj == "" && selectedrows.length == 0) {
            layer.alert('请选择患者!');
            return false;
        }

        if (selectedrows.length == 0) {//单个患者转诊咨询
            layer.open({
                type: 2,
                title: '转诊咨询',
                shadeClose: false,
                shade: 0.6,
                area: ['90%', '90%'],
                content: contextPath + '/KQDS_UserDocumentAct/toZzAddWin.act?usercode=' + onclickrowOobj.usercode
            });
        } else {//批量转诊咨询
            layer.open({
                type: 2,
                title: '转诊咨询',
                shadeClose: false,
                shade: 0.6,
                area: ['550px', '80%'],
                content: contextPath + '/KQDS_UserDocumentAct/toZzWin.act'
            });
        }
    }

    //添加加工单
    function jiagong() {
        if (onclickrowOobj == "") {
            layer.alert('请选择患者!');
            return false;
        }
        layer.open({
            type: 2,
            title: '创建加工单',
            shadeClose: false,
            shade: 0.6,
            area: ['90%', '90%'],
            content: contextPath + '/KQDS_OutProcessingSheetAct/toAdd.act?usercode=' + onclickrowOobj.usercode
        });
    }

    //计算界面宽高的设置
    //setWidth() ,setHeight()函数移动到tableHeaderWidth.js

    function getButtonPower() {
        var menubutton1 = "";
        for (var i = 0; i < listbutton.length; i++) {
            if (listbutton[i].qxName == "xxcx_tjjgd") {
                menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="jiagong()">添加加工单</a>';
            } else if (listbutton[i].qxName == "xxcx_scbb") {
                menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="exporttable()">生成报表</a>';
            } else if (listbutton[i].qxName == "xxcx_zdkf") {
                menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="setKeFu()">指定客服</a>';
            } else if (listbutton[i].qxName == "xxcx_dahb") {
                menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="dahb()">档案合并</a>';
            } else if (listbutton[i].qxName == "xxcx_zzzx") {
                menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="zzzx()">转诊咨询</a>';
            } else if (listbutton[i].qxName == "xxcx_deleteuser") {
                if ("admin" == "<%=person.getUserId()%>") {
                    // 暂不放开以下功能  ############### yangsen 6-27 10:46
                    menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="deleteUser()">删除</a>'; <!--标识删除只修改状态位置-->
                    //menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="deleteUserReal()">删除</a>';
                    // menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="userDateSet()">日期设定</a>';
                }
            } else if (listbutton[i].qxName == "xxcx_xgjd") {
                menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="xgjd()">修改档案</a>';
            }
        }
        menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn clean" onclick="clean()">清空</a>';
        menubutton1 += '<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="searchHzda()" id="query">查询</a>';
        $("#bottomBarDdiv").append(menubutton1);
        setHeight();
    }

    /*****#########################################系统数据清理 START#########################################****/
    /**
     * 将所选的用户标识为删除状态
     * 即 isdelete状态标识为 1
     */
    function deleteUser() {
        var selectRows = getIdSelections();
        var usercodesLocal = "";
        $.each(selectRows, function (index) {
            usercodesLocal += selectRows[index].usercode + ",";
        });
        if (!usercodesLocal) {
            layer.alert("请勾选需要删除的患者");
            return;
        }
        var delrequrl = contextPath + '/CleanDataAct/tmpDeleteUers.act?usercodes=' + usercodesLocal;
        var returnObj = deleteByUrlCommon(delrequrl);
        if (returnObj == null) {
            return false; // 点击取消，不继续执行了
        }
        if (returnObj.retState == "0") {
            layer.alert('删除成功！', {});
            searchHzda(); // 删除
        }
    }

    /**
     * 删除标识为删除状态用户的所有业务数据，包括档案数据
     */
    function deleteUserReal() {
        var selectRows = getIdSelections();
        var usercodesLocal = "";
        $.each(selectRows, function (index) {
            usercodesLocal += selectRows[index].usercode + ",";
        });

        if (!usercodesLocal) {
            layer.alert('请勾选要删除的患者');
            return false;
        }

        layer.confirm('确定删除选定患者的所有数据吗？<br><span style="color:red;">数据删除后不可恢复！</span>', {
                btn: ['确定', '放弃'] //按钮
            },
            function () {
                var radomNum = sixRandom();
                layer.prompt({
                        title: '请输入操作确认码：' + radomNum,
                        formType: 0
                    },
                    function (inputNumber, index) {
                        if (inputNumber != radomNum) {
                            layer.alert('确认码输入错误！');
                            return false;
                        }

                        var delrequrl = 'CleanDataAct/deleteUserAllData.act?usercodes=' + usercodesLocal;
                        var returnObj = getDataFromServer(delrequrl);
                        if (returnObj.retState == "0") {
                            layer.alert('删除成功！', {});
                            searchHzda(); // 删除
                        } else {
                            layer.alert('删除失败：' + returnObj.retMsrg + '！', {});
                        }
                    });
            });


    }

    function querySC() {
        searchHzda();
    }

    function xgjd() {
        if (onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined") {
            layer.alert('请选择需要修改的患者');
        } else {
            //修改资料
            layer.open({
                type: 2,
                title: '修改患者资料',
                shadeClose: false,
                shade: 0.6,
                area: ['740px', '90%'],
                content: contextPath + '/KQDS_UserDocumentAct/toHzjd_Net_Edit.act?usercode=' + onclickrowOobj.usercode
            });
        }
    }


    /**
     * 设定所选用户产生的所有业务数据日志为指定日期
     */
    function userDateSet() {
        // 这两个方法暂时不做！！  yangsen 6-27 10:46
    }

    //获取选中行的usercode
    function getIdSelections() {
        return $.map($("#table").bootstrapTable('getSelections'),
            function (row) {
                return row;
            });
    }

    function setKeFu() {
        selectedrows = getIdSelections();
        if (selectedrows.length == 0) {
            layer.alert('请勾选复选框，选择需要指定客服的患者(可多选)');
        } else {
            layer.open({
                type: 2,
                title: '指定客服',
                shadeClose: false,
                shade: 0.6,
                area: ['90%', '98%'],
                content: contextPath + '/KQDS_ChangeKeFuAct/setKefu.act?menuid=' + "<%=menuid%>" + '&organization=' + $("#organization").val()
            });
        }
    }

    //复选框
    function stateFormatter(value, row, index) {
        <%-- if ("admin" != "<%=person.getUserId()%>") {
            return {
                disabled: true,
                checked: false
            };
        } --%>

        return value;
    }

    /*****#########################################系统数据清理  END#########################################****/
</script>
</html>
