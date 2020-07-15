<!-- wl整理 -->
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
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
    <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/Load.js"></script>

    <title>接诊查询</title>
    <!-- 数据表中数据的样式 -->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css"/>
    <!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
    <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css"/>
</head>
<style type="text/css">
    /*工作量表格 ，单独写*/
    .kqds_table td {
        color: #666;
        padding: 2px 3px 3px 3px;
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

    .searchWrap .formBox > section {
        height: 130px
    }
</style>
<body>
<div id="container">
    <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
                <div class="columnHd">
                    <span class="title">接诊查询</span>
                </div>
                <div class="columnBd">
                    <div class="tableBox">
                        <table id="table" class="table-striped table-condensed table-bordered" data-height=""></table>
                    </div>
                </div>
            </div>
            <div id="gongzuol">
                <div class="columnBd">
                    <ul class="dataCountUl" id="dataCount">

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
                            <li>
                                <span>挂号时间</span>
                                <input type="text" id="starttime" name="starttime" placeholder="开始日期" readonly
                                       class="birthDate">
                            </li>
                            <li>
                                <span>到</span>
                                <input type="text" id="endtime" name="endtime" placeholder="结束日期" readonly
                                       class="birthDate">
                            </li>

                            <li>
                                <span>模糊查询</span>
                                <input type="text" id="searchValue" class="searchInput" placeholder="姓名/手机号">
                            </li>
                            <li class="toggleTr">
                                <span>接诊咨询</span>
                                <select id="askpersonSearch" name="askpersonSearch"
                                        class="patients-source select2 dict searchSelect" data-live-search="true"
                                        title="请选择"></select>
                                <!-- <input type="hidden" name="askpersonSearch" id="askpersonSearch"  class="form-control"  value=""/>
                                <input  type="text"  id="askpersonSearchDesc" name="askpersonSearchDesc" placeholder="咨询" readonly  onClick="javascript:single_select_user(['askpersonSearch', 'askpersonSearchDesc'],'',1);"></input> -->
                                <!-- 				    				<select class="patients-source select2 dict"  title="咨询" placeholder="咨询" name="askpersonSearch" id="askpersonSearch"> -->
                                <!-- 									</select> -->
                            </li>
                            <li class="toggleTr">
                                <span>医生</span>
                                <select id="doctorSearch" name="doctorSearch"
                                        class="patients-source select2 dict searchSelect" data-live-search="true"
                                        title="请选择"></select>
                                <!-- <input type="hidden" name="doctorSearch" id="doctorSearch"  class="form-control"  value=""/>
                                <input type="text"  id="doctorSearchDesc" name="doctorSearchDesc" placeholder="医生" readonly  onClick="javascript:single_select_user(['doctorSearch', 'doctorSearchDesc'],'',1);"> -->
                                <!-- 				    				<select class="patients-source select2 dict"  title="医生" placeholder="医生" name="doctorSearch" id="doctorSearch"> -->
                                <!-- 									</select> -->
                            </li>
                            <li class="toggleTr">
                                <span>挂号分类</span>
                                <select class="dict" tig="ghfl" name="regsort" id="regsort"></select>
                            </li>
                            <li class="toggleTr">
                                <span>就诊分类</span>
                                <select class="dict" tig="jzfl" name="recesort" id="recesort"></select>
                            </li>
                            <li class="toggleTr" id="accept"> <!-- 添加受理工具查询分类 -->
                                <span>受理工具</span>
                                <select id="gongju" name="gongju" class="dict searchSelect" data-live-search="true"
                                        tig="SLGJ" title="请选择"></select>
                                <!-- 			    					<select class="dict" tig="SLGJ" id="gongju" ></select> -->
                            </li>
                            <li class="toggleTr">
                                <span>成交状态</span>
                                <select name="cjstatus" id="cjstatus">
                                    <option value="">请选择</option>
                                    <option value="0">未成交</option>
                                    <option value="1">已成交</option>
                                </select>
                            </li>
                            <li class="toggleTr">
                                <span>病历</span>
                                <select name="ifmedrecord" id="ifmedrecord">
                                    <option value="">请选择</option>
                                    <option value="1">已填写</option>
                                    <option value="0">未填写</option>
                                </select>
                            </li>
                            <li class="toggleTr">
                                <span>客户等级</span>
                                <select name="importantSearch" id="importantSearch">
                                    <option value="">客户等级</option>
                                    <option value="1">一级</option>
                                    <option value="2">二级</option>
                                    <option value="3">三级</option>
                                    <option value="4">四级</option>
                                    <option value="5">五级</option>
                                </select>
                            </li>
                            <li class="toggleTr">
                                <span>年龄区间</span>
                                <select name="ageSearch" id="ageSearch">
                                    <option value="">年龄区间</option>
                                    <option value="10">0~10</option>
                                    <option value="20">10~20</option>
                                    <option value="30">20~30</option>
                                    <option value="40">30~40</option>
                                    <option value="50">40~50</option>
                                    <option value="51">50以上</option>
                                </select>
                            </li>
                            <li class="toggleTr" id="tool">
                                <span>患者来源</span>
                                <select id="devchannelSearch" name="devchannelSearch"
                                        class="patients-source select2 dict searchSelect" tig="hzly"
                                        data-live-search="true"
                                        onchange="getSubDictSelectSearch('devchannelSearch','nexttype1');"
                                        title="请选择"></select>
                                <!-- 				    				<select class="patients-source select2 dict" tig="hzly" name="devchannelSearch" id="devchannelSearch" onchange="getSubDictSelect('devchannelSearch','nexttype1');"> -->
                                <!-- 									</select> -->
                            </li>
                            <li class="toggleTr" id="toolSon">
                                <span>子分类</span>
                                <select id="nexttype1" name="nexttype1" class="select2 dict searchSelect"
                                        data-live-search="true" title="请选择"></select>
                                <!-- 				    				<select class="select2 dict" name="nexttype1" id="nexttype1"> -->
                                <!-- 										<option value="">请选择</option> -->
                                <!-- 									</select> -->
                            </li>
                            <li class="toggleTr">
                                <span title="潜在开发项目">潜在开发</span>
                                <select class="dict" tig="QZKFXM" name="devItem" id="devItem"></select>
                            </li>
                            <li class="toggleTr">
                                <span>就诊科室</span>
                                <select class="dept" tag="<%=ConstUtil.DEPT_TYPE_1 %>" name="regdept"
                                        id="regdept"></select>
                            </li>
                            <li class="toggleTr hide" id="jzcx_askperson">
                                <span>第一咨询</span>
                                <select class="patients-source select2 dict searchSelect" data-live-search="true"
                                        title="请选择" title="咨询" placeholder="咨询" name="firstAskperson"
                                        id="firstAskperson"></select>
                            </li>
                        </ul>
                    </section>
                    <div class="btnBar" id="bottomBarDdiv">

                    </div>
                </div>
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
<script type="text/javascript"
        src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript"
        src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript"
        src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript"
        src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
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
    var onclickrowOobj = "";
    var nowday;
    //初始化表头，返回空的数据
    var nullUrl = contextPath + '/UtilAct/intTableHeader.act';
    var pageurl = contextPath + '/KQDS_REGAct/selectZhcxNopage.act';
    var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
    var personrole = "<%=person.getUserPriv()%>";
    var personroleother = "<%=person.getUserPrivOther()%>";
    var selectedrows = ""; //推广计划使用
    var jzcx_chufuzhenModify_Flag = false;
    var jzcx_updateRegModify_Flag = false;
    var jzcx_invoice_Flag = false;
    var isClick = true;

    var loadperson = '<%=person.getUserPriv()%>';
    var load = loadperson.split(",");//登陆这权限
    var allowedperson = '<%=SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK)%>';
    var allowed = allowedperson.split(",");//允许权限

    $(function () {
        initHosSelectList4Front('organization'); // 连锁门诊下拉框
        initSysUserByDeptId($("#doctorSearch"), "doctor"); //初始化医生选项下拉框
        initSysUserByDeptId($("#askpersonSearch"), "consultation"); //初始化咨询选项下拉框
        //咨询 下拉列表
        initPersonSelectByDeptType("firstAskperson", "<%=ConstUtil.DEPT_TYPE_0 %>");
        var tmpOrganization = $("#organization").val();
        initDeptSelectByTypesAndClass(tmpOrganization);
        $("#organization").change(function () {
            var currSelect = $(this).val();
            initDeptSelectByTypesAndClass(currSelect);
        });
        nowday = getNowFormatDate();
        initDictSelectByClass(); // 患者来源、挂号分类、就诊分类
        //获取当前页面所有按钮
        getButtonAllCurPage("<%=menuid%>");
        /* 左侧个人中心的按钮样式控制js已经被抽取到rightPartInfo.jsp页面中 */
        //时间选择
        $(".birthDate").datetimepicker({
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
        togglemodel.initial("jzcx", pageurl);
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

    //带参数刷新表格
    function refreshTable() {
        $('#table').bootstrapTable('refresh', {
            'url': pageurl
        });
    }

    function initTable(requrl) {
        $('#table').bootstrapTable({
            url: requrl,
            queryParams: queryParamsB,
            dataType: "json",
            pagination: true,//是否显示分页（*）
            pageSize: 25,
            pageList: [10, 15, 20, 25],//可以选择每页大小
            //clickToSelect: false,
            singleSelect: true,
            sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
            paginationShowPageGo: true,
            onLoadSuccess: function (data) { //加载成功时执行
                    //解除查询按钮禁用 lutian
                if(data){
                    $("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","auto").css("pointer-events","auto");
                    $("#query").text("查询");
                }
                //判断登录
                var existornot = isExist(total);
                if (!existornot) {
                    $('#table').bootstrapTable('hideColumn', 'devchannel');
                    $('#table').bootstrapTable('hideColumn', 'nexttype');
                    $('#table').bootstrapTable('hideColumn', 'devitemdesc');
                    $('#table').bootstrapTable('hideColumn', 'slgj');
                    $('#tool').attr('class', 'toole').attr('style', 'display:none');
                    $('#toolSon').attr('class', 'toolSone').attr('style', 'display:none');
                    $('#accept').attr('class', 'accepte').attr('style', 'display:none');
                } else {
                    /* console.log("-----------！！登录"); */
                }

                /* if(nowpage == 0 && data.total>0){ */
                /* maxpage = Math.floor(data.total/pagesize)+1;  */
                var content = '';
                var ts = 2;
                for (var prop in data.jzfl) {
                    ts++;
                }
                content += '<li>总数:<span>' + data.total + '</span></li>';
                content += '<li>成交数:<span>' + data.cjtotal + '</span></li>';
                for (var prop in data.jzfl) {
                    if (data.jzfl[prop] == 0) {
                        continue;
                    }
                    content += '<li>' + prop + ' ' + data.jzfl[prop] + '</li>'
                    /* } */

                    $("#dataCount").html(content);
                }
                if (data.total == 0) {
                    $("#dataCount").html('');
                }
                //分页加载
                /* showdata("table",data.rows); */
                //计算主体的宽度
                setWidth();
                setHeight();
                /*表格载入时，设置表头的宽度 */
                setTableHeaderWidth(".tableBox");
                if (!jzcx_invoice_Flag) {
                    $('#table').bootstrapTable('hideColumn', 'invoice');
                }
            },
            rowStyle: function (row, index) {
                //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
                var strclass = "";
                if (Number(row.del) > 0) {
                    strclass = 'warning'; //还有一个active
                } else {
                    return {};
                }
                return {
                    classes: strclass
                };
            },
            columns: [{
                field: ' ',
                checkbox: true,
                formatter: stateFormatter
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
                    title: '挂号时间',
                    field: 'createtime',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        return '<span>' + value.substring(0) + '</span>';
                    }
                },
                {
                    title: '患者编号',
                    field: 'usercode',
                    align: 'center',

                    sortable: true,
                    formatter: function (value, row, index) {
                        return '<span title="' + value + '">' + value + '</span>';
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
                        return '<span class="name" title="' + value + '">' + value + '</span>';
                    }
                },
                {
                    title: '标识',
                    field: 'iscreatelclj',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        //console.log("value="+value+",row="+JSON.stringify(row)+",index="+index);
                        var iconhtml = "";
                        if (value != "" && value != null) {
                            iconhtml += '<img class="iscreatelclj" src= ' + contextPath + '/static/image/kqdsFront/tag/clinical.jpg/>';
                        }
                        if (row.kefu) {
                            iconhtml += '<img class="kefu" src= ' + contextPath + '/static/image/kqdsFront/tag/customerservice.jpg/>';
                        }
                        if (row.contagion != "" && row.contagion != null && row.contagion == 1) {
                            iconhtml += '<img class="contagion" src= ' + contextPath + '/static/image/kqdsFront/tag/contagion.jpg/>';
                        }
                        if (jzcx_invoice_Flag) {
                            if (row.invoice != 0) {
                                iconhtml += '<img class="iscreatelclj" style="height: 17px;width: 17px;margin-top: 3px;" src= ' + contextPath + '/static/image/kqdsFront/tag/invoice.jpg/>';
                            }
                        }
                        return iconhtml == "" ? "-" : iconhtml;
                    },
                    cellStyle: {
                        css: {"display": "flex", "flex-direction": "row"}
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
                    title: '性别',
                    field: 'sex',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
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
                            return '<span></span>';
                        } else {
                            return '<span>' + value + '</span>';
                        }
                    }
                },
                {
                    title: '身份证号',
                    field: 'idcardno',
                    align: 'center',

                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == "0") {
                            return '<span></span>';
                        } else {
                            return '<span>' + value + '</span>';
                        }
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
                    formatter: function (value) {
                        return "<span class='name'>" + value + "</span>";
                    }
                },
                {
                    title: '客户等级',
                    field: 'important',
                    align: 'center',
                    sortable: true,
                    formatter: function (value) {
                        return '<span>' + value + '</span>';
                    }
                },
                {
                    title: '成交状态',
                    field: 'cjstatus',
                    align: 'center',
                    sortable: true,
                    formatter: function (value) {
                        return '<span>' + value + '</span>';
                    }

                },
                {
                    title: '就诊分类',
                    field: 'recesort',
                    align: 'center',
                    sortable: true,
                    formatter: function (value) {
                        return '<span>' + value + '</span>';
                    }
                },
                {
                    title: '挂号分类',
                    field: 'regsort',
                    align: 'center',
                    sortable: true,
                    formatter: function (value) {
                        return '<span>' + value + '</span>';
                    }
                },
                {
                    title: '第一咨询',
                    field: 'faskperson',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            html = '<span class="name" title="' + value + '">' + value + '</span>';
                            return html;
                        } else {
                            return "<span></span>";
                        }
                    }
                },
                {
                    title: '接诊咨询',
                    field: 'askperson',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            html = '<span class="name" title="' + value + '">' + value + '</span>';
                            return html;
                        } else {
                            return "<span></span>";
                        }
                    }
                },
                {
                    title: '客服',
                    field: 'kefuname',
                    align: 'center',
                    sortable: true,
                    formatter: function (value) {
                        if (value) {
                            return '<span>' + value + '</span>';
                        } else {
                            return '<span>-</span>';
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
                            html = '<span class="name" title="' + value + '">' + value + '</span>';
                            return html;
                        } else {
                            return "<span class='name'></span>";
                        }
                    }
                },
                {
                    title: '患者来源',
                    field: 'devchannel',
                    align: 'center',
                    sortable: true,
                    formatter: function (value) {
                        return "<span class='source'>" + value + "</span>";
                    }
                },
                {
                    title: '来源子分类',
                    field: 'nexttype',
                    align: 'center',
                    sortable: true,
                    formatter: function (value) {
                        return "<span class='source'>" + value + "</span>";
                    }
                },
                {
                    title: '受理工具', //添加受理工具类型
                    field: 'slgj',
                    align: 'center',
                    sortable: true,
                    formatter: function (value) {
                        return "<span class='source'>" + value + "</span>";
                    }
                },
                {
                    title: '缴费金额',
                    field: 'jf',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        return '<span>' + value + '</span>'
                    }
                },
                {
                    title: '实收金额',
                    field: 'ssje',
                    align: 'center',
                    /* sortable: true, */
                    formatter: function (value, row, index) {
                        return '<span>' + value + '</span>'
                    }
                },
                /*      {
                            title: '知情同意书',
                            field: 'cisprint',
                            align: 'center',

                            sortable: true,
                            width: 85
                        }, */
                {
                    title: '潜在开发项目',
                    field: 'devitemdesc',
                    align: 'center',
                    sortable: true,
                    formatter: function (value) {
                        return "<span class='source'>" + value + "</span>";
                    }
                },
                {
                    title: '病历',
                    field: 'ifmedrecord',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        var html = "";
                        if (row.ifmedrecord == "已填写") {
                            html = '<span class="label label-success">已填写</span>';
                        } else {
                            html = '<span class="label label-danger">未填写</span>';
                        }
                        return html;
                    }
                },
                {
                    title: '未成交原因',
                    field: 'failreason1',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            html = '<span class="time" title="' + value + '">' + value + '</span>';
                            return html;
                        } else {
                            return "<span></span>";
                        }
                    }
                },
                {
                    title: '咨询情况备注',
                    field: 'detaildesc',
                    align: 'center',
                    /* sortable: true, */
                    formatter: function (value, row, index) {
                        if (value) {
                            var showVal = value;
                            if (value.length > 6) {
                                showVal = value.substring(0, 6) + '...';
                            }
                            html = '<span class="time" title="' + value + '">' + showVal + '</span>';
                            return html;
                        } else {
                            return "<span></span>";
                        }
                    }
                },
                {
                    title: '建档人',
                    field: 'jdr',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            html = '<span class="name" title="' + value + '">' + value + '</span>';
                            return html;
                        } else {
                            return "<span></span>";
                        }
                    }
                },
                {
                    title: '开发人',
                    field: 'developername',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            html = '<span class="name" title="' + value + '">' + value + '</span>';
                            return html;
                        } else {
                            return "<span></span>";
                        }
                    }
                },
                {
                    title: '建档导医',
                    field: 'jddy',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            html = '<span class="name" title="' + value + '">' + value + '</span>';
                            return html;
                        } else {
                            return "<span></span>";
                        }
                    }
                },
                {
                    title: '挂号人',
                    field: 'createuser',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            html = '<span class="name" title="' + value + '">' + value + '</span>';
                            return html;
                        } else {
                            return "<span></span>";
                        }
                    }
                },
                {
                    title: '挂号导医',
                    field: 'dy',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            html = '<span class="name" title="' + value + '">' + value + '</span>';
                            return html;
                        } else {
                            return "<span></span>";
                        }
                    }
                },
                {
                    title: '修改/撤销',
                    field: ' ',
                    align: 'center',
                    /* sortable: true, */
                    formatter: function (value, row, index) {//field 没有值 这里不能用value值
                        var html = "";
                        if (row.editreason != "" && row.editreason != null && row.del == "0") { //修改
                            html = '<span class="label label-success" onclick="showEditreason(\'' + row.seqId + '\')" style="width:60px">已修改</span>';
                        }
                        if (row.editreason != "" && row.editreason != null && row.del == "1") { //修改
                            html = '<span class="label label-success" onclick="showEditreason(\'' + row.seqId + '\')" style="width:60px">已撤销</span>';
                        }
                        return html;
                    }
                },
                {
                    title: '就诊分类',
                    field: ' ',
                    align: 'center',
                    /* sortable: true, */
                    formatter: function (value, row, index) {//field 没有值 这里不能用value值
                        var html = "";
                        if (jzcx_chufuzhenModify_Flag) {
                            html = '<span class="chufuzhenclass" style="color:red;cursor:pointer;text-decoration:underline;" onclick="chufuzhenModify(\'' + row.seqId + '\',\'' + row.recesortvalue + '\')" style="width:60px">修改</span>';
                        }
                        return html;
                    }
                },
                {
                    title: '挂号分类',
                    field: ' ',
                    align: 'center',
                    /* sortable: true, */
                    formatter: function (value, row, index) {//field 没有值 这里不能用value值
                        var html = "";
                        if (jzcx_updateRegModify_Flag) {
                            html = '<span class="chufuzhenclass" style="color:#ff0000;cursor:pointer;text-decoration:underline;" onclick="updateRegModify(\'' + row.seqId + '\',\'' + row.regsortvalue + '\')" style="width:60px">修改</span>';
                        }
                        return html;
                    }
                }
            ]
        }).on('click-row.bs.table',
            function (e, row, element) {
                $('.success').removeClass('success'); //去除之前选中的行的，选中样式
                $(element).addClass('success'); //添加当前选中的 success样式用于区别
                var index = $('#table').find('tr.success').data('index'); //获得选中的行
                onclickrowOobj = $('#table').bootstrapTable('getData')[index];
//         console.log(JSON.stringify(onclickrowOobj)+"----onclickrowOobj");
                $('#tabIframe').attr('src', $('#tabIframe').attr('src')); //个人中心
            });
    }

    //跳转临床页面
    function skip(username, usercode) {
        parent.Catalogue()
        parent.secondLevelDirectory()
        console.log(username, usercode);
        window.location.href = contextPath + '/ClinicPathControllerAct/toLcljPageFetchInfo.act?menuId=600309&&username=' + username + '&&userId=' + usercode;
    }

    function shuaxin() {
        /* $('#table').bootstrapTable('refresh', {
            'url': pageurl
        }); */
        window.location.reload();
    }

    //修改就诊分类页面跳转
    function chufuzhenModify(regSeqId, recesortvalue) {
        var organization = $('#organization').val();
        layer.open({
            type: 2,
            title: '修改就诊分类',
            shadeClose: false,
            shade: 0.6,
            area: ['450px', '350px'],
            content: contextPath + '/KQDS_REGAct/toChufuzhenModify.act?regSeqId=' + regSeqId + '&recesortvalue=' + recesortvalue + '&organization=' + organization,
            //iframe的url
            end: function () {
            }
        });
    }

    //修改挂号分类页面跳转
    function updateRegModify(regSeqId, regsortvalue) {
        var organization = $('#organization').val();
        layer.open({
            type: 2,
            title: '修改挂号分类',
            shadeClose: false,
            shade: 0.6,
            area: ['450px', '350px'],
            content: contextPath + '/KQDS_REGAct/toUpdaeRegModify.act?regSeqId=' + regSeqId + '&regsortvalue=' + regsortvalue + '&organization=' + organization,
            //iframe的url
            end: function () {
            }
        });
    }

    //查看修改挂号原因
    function showEditreason(seqId) {
        layer.open({
            type: 2,
            title: '挂号修改原因',
            shadeClose: true,
            shade: 0.6,
            area: ['490px', '500px'],
            content: contextPath + '/KQDS_REGAct/toEditReason.act?seqId=' + seqId //iframe的url
        });
    }

    function queryParams() {
        var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            devItem: $("#devItem").val(), // 潜在开发项目
            organization: $('#organization').val(),
            type: 1,
            //可以查询挂号表医生 咨询不是自己，收费项目种有自己的
            regdept: $('#regdept').val(),
            doctorSearch: $('#doctorSearch').val(),
            askpersonSearch: $('#askpersonSearch').val(),
            starttime: $('#starttime').val(),
            endtime: $('#endtime').val(),
            importantSearch: $('#importantSearch').val(),
            devchannelSearch: $('#devchannelSearch').val(),
            nexttype1: $('#nexttype1').val(),
            ageSearch: $('#ageSearch').val(),
            regsort: $('#regsort').val(),
            cjstatus: $('#cjstatus').val(),
            recesort: $('#recesort').val(),
            searchValue: $('#searchValue').val(),
            ifmedrecord: $('#ifmedrecord').val(),
            gongju: $('#gongju').val(),  //添加受理工具类型
            firstAskperson: $('#firstAskperson').val()
        };
        if (nowday != null) {
            temp.starttime = nowday;
            temp.endtime = nowday;
        }
        return temp;
    }

    function queryParamsB(params) {
        var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset, //页码
            pageIndex: params.offset / params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)
            devItem: $("#devItem").val(), // 潜在开发项目
            organization: $('#organization').val(),
            type: 1,
            //可以查询挂号表医生 咨询不是自己，收费项目种有自己的
            regdept: $('#regdept').val(),
            doctorSearch: $('#doctorSearch').val(),
            askpersonSearch: $('#askpersonSearch').val(),
            starttime: $('#starttime').val(),
            endtime: $('#endtime').val(),
            importantSearch: $('#importantSearch').val(),
            devchannelSearch: $('#devchannelSearch').val(),
            nexttype1: $('#nexttype1').val(),
            ageSearch: $('#ageSearch').val(),
            regsort: $('#regsort').val(),
            cjstatus: $('#cjstatus').val(),
            recesort: $('#recesort').val(),
            searchValue: $('#searchValue').val(),
            ifmedrecord: $('#ifmedrecord').val(),
            gongju: $('#gongju').val(),  //添加受理工具类型
            firstAskperson: $('#firstAskperson').val(),
            sortName: this.sortName,
            sortOrder: this.sortOrder
        };
        if (nowday != null) {
            temp.starttime = nowday;
            temp.endtime = nowday;
        }
        return temp;
        //console.log("ceshi ="+temp.gongju);
    }

    function searchHzda(thi) {
        loadedData = [];
        $("#dataCount").html('');
        nowpage = 0;
        nowday = null;
        var doctorSearch = $('#doctorSearch').val();
        var devItem = $("#devItem").val();
        var regdept = $('#regdept').val();
        var askpersonSearch = $('#askpersonSearch').val();
        var starttime = $('#starttime').val();
        var endtime = $('#endtime').val();
        var importantSearch = $('#importantSearch').val();
        var devchannelSearch = $('#devchannelSearch').val();
        var ageSearch = $('#ageSearch').val();
        var regsort = $('#regsort').val();
        var cjstatus = $('#cjstatus').val();
        var recesort = $('#recesort').val();
        var searchValue = $('#searchValue').val();
        var gongju = $('#gongju').val();
        var firstAskperson = $('#firstAskperson').val();
        var ifmedrecord = $('#ifmedrecord').val();
        if (devItem == "" && regdept == "" && doctorSearch == "" && askpersonSearch == "" && starttime == "" && searchValue == "" && endtime == "" && cjstatus == "" && importantSearch == "" && devchannelSearch == "" && ageSearch == "" && regsort == "" && cjstatus == "" && recesort == "" && gongju == "" && ifmedrecord == "" && firstAskperson == "") {
            layer.alert('请选择查询条件!');
            return false;
        }
        $(thi).attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
        $(thi).text("查询中");
        $('#table').bootstrapTable('refresh', {
            'url': pageurl
        });
    }

    function clean() {
        $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
        $("#regsort").val("").trigger("change");
        $("#cjstatus").val("").trigger("change");
        $("#devchannelSearch").val("").trigger("change");
        $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
//     清空搜索下拉框
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
        if (isClick) {
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
                    a.download = "接诊查询";
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

    //复选框
    function stateFormatter(value, row, index) {
        if (row.id === 2) {
            return {
                disabled: true,
                checked: true
            };
        }
        if (row.id === 0) {
            return {
                disabled: true,
                checked: true
            }
        }
        return value;
    }

    //获取选中行的usercode
    function getIdSelections() {
        return $.map($("#table").bootstrapTable('getSelections'),
            function (row) {
                return row;
            });
    }

    //添加任务(推广计划)
    function goAddRenwu() {
        selectedrows = getIdSelections();
        //selectedrows[0]
        if (selectedrows.length == 0) {
            layer.alert('请选择需要加入推广计划的患者(可多选)');
        } else {
            layer.open({
                type: 2,
                title: '添加推广计划',
                shadeClose: false,
                shade: 0.6,
                area: ['50%', '80%'],
                content: contextPath + '/KQDS_ExtensionAct/toExtensionAdd.act',
                end: function () {
                }
            });
        }
    }

    //生成临床路径
    function createLclj() {
        if (onclickrowOobj == "") {
            layer.alert('请选择患者!');
            return false;
        }
        layer.open({
            type: 2,
            title: '创建手术信息',
            shadeClose: false,
            shade: 0.6,
            area: ['95%', '95%'],
            content: contextPath + '/ClinicPathControllerAct/toLcljOpreation.act?usercode=' + onclickrowOobj.usercode
        });
    }

    //计算界面宽高的设置
    //setWidth() ,setHeight()函数移动到tableHeaderWidth.js


    function getButtonPower() {
        var menubutton1 = "";
        for (var i = 0; i < listbutton.length; i++) {
            if (listbutton[i].qxName == "jzcx_tjjgd") {
                menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="jiagong()">添加加工单</a>';
            } else if (listbutton[i].qxName == "jzcx_tjtg") {
                menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="goAddRenwu()">添加推广</a>';
            } else if (listbutton[i].qxName == "jzcx_kjfp") {
                menubutton1 += '<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="invoice()">开具发票</a>';
            } else if (listbutton[i].qxName == "jzcx_scbb") {
                menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="exporttable()">生成报表</a>';
            } else if (listbutton[i].qxName == "jzcx_sclclj") {
                menubutton1 += '<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="createLclj()">生成临床路径</a>';
            } else if (listbutton[i].qxName == "jzcx_chufuzhenmodify") {
                jzcx_chufuzhenModify_Flag = true;
            } else if (listbutton[i].qxName == "jzcx_updateregmodify") {
                jzcx_updateRegModify_Flag = true;
            } else if (listbutton[i].qxName == "jzcx_invoice") {
                jzcx_invoice_Flag = true;
            } else if (listbutton[i].qxName == "jzcx_askperson") {
                $("#jzcx_askperson").removeClass("hide");
            } else if (listbutton[i].qxName == "jzcx_zdkf") {
                menubutton1 += '<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="setKeFu()">指定客服</a>';
            }
        }
        menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn clean" onclick="clean()">清空</a>';
        menubutton1 += '<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="searchHzda(this)" id="query">查询</a>';
        $("#bottomBarDdiv").append(menubutton1);

        setHeight();
    }

    //开发票
    function invoice() {
        selectedrows = getIdSelections();
        var userCode = [];
        if (selectedrows.length == 0) {
            layer.alert("请选择要开发票的患者！");
        } else if (selectedrows.length > 1) {
            layer.alert("每次至多只能选择一个患者开具发票！");
        } else {
            layer.open({
                type: 2,
                title: '开具发票',
                shadeClose: false,
                shade: 0.6,
                area: ['80%', '70%'],
                content: contextPath + '/Kqds_PayCostAct/tocw_invoice.act',
                //iframe的url
                end: function () {
                }
            });
        }
    }

    //指定客服
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
</script>
</html>
