<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/inc/classImport.jsp" %>
<%
    String contextPath = request.getContextPath();
    if (contextPath.equals("")) {
        contextPath = "/kqds";
    }
    String usercode = request.getParameter("usercode");
    String regno = request.getParameter("regno");
    String lytype = request.getParameter("lytype");
    YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8"/>
    <title>添加回访</title>
    <%--     <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" /> --%>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/visit/visitTableLayout.css">
    <!-- select搜索筛选 -->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css"/>
    <style>
        /* 搜索框select */
        .searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {
            width: 150px;
        }

        .searchSelect > .btn {
            width: 150px;
            height: 28px;
        }

        .bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
            color: #999;
            height: 28px;
        }

        .pull-left {
            float: left !important;
            color: #333;
        }

        .searchWrap .text {
            position: static !important;
            left: 0px;
            top: 9px;
            color: rgb(31, 32, 33);
        }

        table {
            text-align: center;
        }

        .size {
            width: 95%;
            height: 90%;
            text-align: center;
            border: 1px solid blue;
        }

        td {
            word-wrap: break-word
        }

        .cbox {
            display: none;
        }

        .spcbox {
            width: 10%;
        }

        /* 序号 */
        .sequenceWidth {
            width: 5%;
        }

        /* 编号 */
        .deptWidth {
            width: 10%;
        }

        /* 回访计划名称 */
        .planNameWidth {
            width: 25%;
        }

        /* 说明 */
        .explainWidth {
            width: 30%;
        }

        /* 操作 */
        .operateWidth {
            width: 15%;
        }

        /*      小计划样式           	*/
        /* 跟进天数 */
        .daysWidth {
            width: 10%;
        }

        /* 回访类型 */
        .visitTypeWidth {
            width: 25%;
        }

        /* 是否强制跟进 */
        .followWidth {
            width: 12%;
        }

        .self_motion {
            width: 12%;
            display: none;
        }

        /* 回访部门 */
        .deptWidth {
            width: 10%;
            /* display: none; */
        }

        /* 回访人员 */
        .hfperson_Width {
            width: 10%;
            display: none;
        }

        /* 受理类型 */
        .sltype_Width {
            width: 10%;
        }

        /* 说明 */
        .smallexplainWidth {
            width: 24%;
        }

        /*小计划 操作 */
        .smalloperateWidth {
            width: 7%;
        }

        #planContiner {
            width: 100%;
            /*      	border:1px solid red; */
        }

        /* 大计划删除 */
        .bigPlanDel {
            margin-right: 5px;
        }

        .smallPlanDel {
            margin-right: 5px;
        }

        /* table */
        #planContiner table {
            width: 100%;
            font-size: 13px;
            font-weight: normal;
            margin-bottom: 5px;
            border-left: 1px solid #ddd;
        }

        #planContiner table thead {
            width: 100%;
            border: 1px solid blue;
            background-color: #00A6C0;
            border: 1px solid #ddd;
            color: white;
        }

        #planContiner table thead th {
            border-right: 1px solid #ddd;
            line-height: 18px;
            padding: 8px;
        }

        #planContiner table tbody td {
            border-right: 1px solid #ddd;
            border-bottom: 1px solid #ddd;
            padding: 2px;
        }

        /* 大计划选中行背景颜色 */
        #bigPlanTbody tr.trActive {
            background-color: #A9ECC7;
        }

        .btn {
            display: inline-block;
            box-sizing: border-box;
            height: 24px;
            line-height: 24px;
            background: white;
            color: #00a6c0;
            outline: none;
            padding: 0 8px;
            border: 1px solid #00a6c0;
            border-radius: 3px;
            margin-right: 3px;
            text-decoration: none;
            cursor: pointer;
            text-align: center;
            font-size: 12px;
        }

        .planBtn {
            display: inline-block;
            background-color: #00a6c0;
            border: 1px solid #00a6c0;
            color: white;
            border-radius: 5px;
            /* 	    padding: 3px 10px; */
            margin: 0 auto;
            outline: none;
        }

        .bigPlanAdd {
            display: inline-block;
        }

        .smallPlanAdd {
            display: inline-block;
        }

        .smallPlanSave {
            background-color: #00A6C0;
            border: 1px solid #00A6C0;
            color: white;
        }

        /*关键设置 tbody出现滚动条*/
        #bigPlan tbody {
            display: block;
            height: 200px;
            overflow-y: scroll;
        }

        #bigPlan thead,
        tbody tr {
            display: table;
            width: 100%;
            table-layout: fixed;
        }

        /*关键设置：滚动条默认宽度是16px 将thead的宽度减16px*/
        #planContiner #bigPlan thead {
            width: calc(100% - 18px)
        }

        /*关键设置 tbody出现滚动条*/
        #smallPlanList tbody {
            display: block;
            height: 200px;
            overflow-y: scroll;
        }

        #smallPlanList thead,
        tbody tr {
            display: table;
            width: 100%;
            table-layout: fixed;
        }

        /*关键设置：滚动条默认宽度是16px 将thead的宽度减16px*/
        #planContiner #smallPlanList thead {
            width: calc(100% - 18px)
        }

        /* 禁用按钮 */
        .disAbledBtn {
            background-color: #aca5a5;
            border: 1px solid #aca5a5;
            cursor: no-drop;
            color: white;
        }

        .startBtn {
            background-color: #00a6c0;
            border: 1px solid #00a6c0;
            cursor: auto;
        }

        .startBtnTwo {
            background-color: white;
            border: 1px solid #00a6c0;
            color: #00a6c0;
            cursor: auto;
        }

        #smallPlanTbody tr td select {
            width: 100%;
            height: 100%;
            /*   border: 1px solid red; */
        }

        #planContiner input, #table select {
            width: auto;
            padding-left: 5px;
            color: #333;
        }

        #table tr > td:nth-child(3) {
            padding-left: 0px;
        }

        #bigPlan thead tr th {
            text-align: center;
        }

        #smallPlanList thead tr th {
            text-align: center;
        }

        #table .marg {
            text-indent: -70px;
        }

        #table .marg .visit {
            width: 150px;
            height: 26px;
            padding-left: 5px;
            color: #333;
        }

        .out_div {
            position: relative;
            height: 100%;
        }

        .out_span {
            margin-left: 0px;
            width: 18px;
            overflow: hidden;
        }

        .fu_input {
            width: 92%;
            height: 100%;
            position: absolute;
            left: 0px;
        }

        .spExplain:empty:before {
            content: attr(placeholder);
            color: #ccc;
        }

        .spExplain:focus:before {
            content: none;
        }
    </style>

</head>
<body>
<!--添加回访弹窗-->
<div class="containerAddEdit"> <!-- "containerAddEdit" 添加、修改回访容器的样式 -->
    <input type="hidden" class="form-control" name="seqId" id="seqId">
    <table id="table">
        <tbody>
        <tr class="marg">
            <td>
                <span class="information">病人编号</span>
            </td>
            <td>
                <input type="text" id="usercode" name="usercode" readonly>
            </td>
            <td>
                <span class="information">患者姓名</span>
            </td>
            <td>
                <input type="text" id="username" name="username" readonly>
            </td>
        </tr>
        <tr class="marg">
            <td>
                <span class="information">患者性别</span>
            </td>
            <td>
                <input type="text" id="sex" name="sex" readonly>
            </td>
            <td>
                <span class="information">患者手机</span>
            </td>
            <td>
                <input type="text" id="telphone" name="telphone" readonly="readonly">
            </td>
        </tr>
        <tr class="marg">
            <td>
                <span class="information">回访部门</span>
            </td>
            <td>
                <select class="hfdept visit" name="hfdept" id="hfdept"
                        onchange="getVisitPersonSelect('hfdept','visitor');"></select>
            </td>
            <td>
                <span class="information">回访人员</span>
            </td>
            <td>
                <select class="dict visit" name="hfperson" id="visitor">
                    <option value="">请选择</option>
                </select>
            </td>
        </tr>
        <tr class="textareaTr">
            <td colspan="8">
                <div id="planContiner">
                    <table id="bigPlan" border="0" cellspacing='0' style="table-layout: fixed;">
                        <thead>
                        <tr>
                            <th class="bigPlanID" hidden>ID</th>
                            <th class="sequenceWidth cbox"></th>
                            <th class="sequenceWidth">序号</th>
                            <th class="deptWidth">部门</th>
                            <th class="planNameWidth">回访计划名称</th>
                            <th class="explainWidth">说明</th>
                            <!-- 				                    <th class="operateWidth">操作</th> -->
                        </tr>
                        </thead>
                        <tbody id="bigPlanTbody" style="table-layout: fixed;border: 1px solid #ddd;">
                        </tbody>
                    </table>

                    <!-- <button class="btn bigPlanSave"  onclick="save()">保存</button> -->
                    <table id="smallPlanList" border="0" cellspacing='0' style="table-layout: fixed;margin-top: 10px;">
                        <thead>
                        <tr>
                            <th class="smallPlanID" hidden>ID</th>
                            <th class="parentPlanID" hidden>父计划ID</th>
                            <th class="spcbox">当前时间</th>
                            <th class="daysWidth">跟进天数（天）</th>
                            <th class="visitTypeWidth">回访类型</th>
                            <th class="followWidth">是否设为操作员强制跟进</th>
                            <!-- <th class="self_motion">是否设为自动回访</th>
                            <th class="deptWidth">回访部门</th>
                            <th class="hfperson_Width">回访人员</th> -->
                            <th class="sltype_Width">受理类型</th>
                            <th class="smallexplainWidth">回访要点</th>
                            <th class="smalloperateWidth">操作</th>
                        </tr>
                        </thead>
                        <tbody id="smallPlanTbody" style="table-layout: fixed;border: 1px solid #ddd;">
                        </tbody>
                    </table>
                </div>
            </td>
        </tr>
        <tr>
            <td colspan="6" style="text-align:center;">
                <button class="planBtn smallPlanAdd disAbledBtn" disabled="disabled" onclick="addSmallPlan()">添加
                </button>
                <a href="javascript:void(0);" class="btn kqdsSearchBtn" onclick="submitu()" id="tijiao">提交</a>
            </td>
        </tr>
        </tbody>
    </table>
</div>

<!-- <input type="hidden" id="isleave" value="1" /> -->
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript"
        src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"
        charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script> --%>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/kqds/kqds_select.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/kqds/kqds_request.js"></script> <!-- 请求封装方法 -->
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript">

    var contextPath = '<%=contextPath%>';
    var usercode = '<%=usercode%>';
    var regno = '<%=regno%>';

    var visitseqid = "<%=person.getSeqId()%>";
    var visitname = "<%=person.getUserName()%>";
    var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    var loginUserId = "<%=person.getSeqId()%>";

    var lytype = "<%=lytype%>";

    var $table = $('#tableplan');
    var clickrow = '';
    $(function () {
        initSelectDeptByPerson(); //查询登录部门名称、ID
        checkIdadmin();//权限禁用
        initBigPlan(); //大计划初始化
        initDept();//初始化部门（只能是回访页面使用）
        if (usercode == "" || usercode == "null" || usercode == null || usercode == "undefined") {
            layer.alert('请选择患者', {});
            //隐藏保存按钮
            $("#tijiao").hide();
        } else {
            getUserDetail();//加载病人信息
            initDictSelectByClass(); // 回访分类

            //回访人默认是当前登录用户
            //$("#visitor").val(visitseqid);
            //$("#visitorDesc").val(visitname);

            //Date picker
            $("#visittime").datetimepicker({
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                minView: 2,
                autoclose: true,//选中之后自动隐藏日期选择框
                pickerPosition: "bottom-right"
            });
        }
        $("#hfdept").val(deptId).trigger('change'); //当前登录人员部门赋值
        $("#visitor").val(loginUserId); //当前登录人员赋值

    });

    //判断当前人员权限
    function checkIdadmin() {
        var url = contextPath + '/HUDH_AddVisitAct/editability.act';
        $.axse(url, null,
            function (data) {
                //console.log(JSON.stringify(data) + "----");
                if (!(data.valid)) {
                    $('#hfdept').attr("disabled", true);
                    $('#visitor').attr("disabled", true);
                    $('#hfdept').css("cursor", "not-allowed");
                    $('#visitor').css("cursor", "not-allowed");
                } else {
                }
            },
            function () {
                layer.alert('请求失败');
            });
    }

    //判断登录部门
    function initSelectDeptByPerson() {
        var url = contextPath + '/YZPersonAct/findDeptnameByPerson.act';
        $.axse(url, null,
            function (r) {
                deptId = r.seq_id;
                sectionName = r.dept_name;
            },
            function () {
                layer.alert('请求失败');
            });
    }

    //大计划初始化
    function initBigPlan() {
//console.log(deptId+"--------大计划初始化部门ID");
        var url = contextPath + '/HUDH_AddVisitAct/findvisitTemplate.act';
        $.axse(url, null,
            function (data) {
                //console.log(JSON.stringify(data)+"-----大计划初始化");
//		 		if(data!=null){
//		 			if(data.length>0){
                $('#bigPlanTbody').html("");
                var html = '';
                for (var i = 0; i < data.length; i++) {
                    var dataPlan = data[i];
                    // console.log(JSON.stringify(dataPlan)+"-----");
                    html += '<tr>' +
                        '<td class="bigPlanID" hidden>' + dataPlan.seqId + '</td>' +
                        '<td class="cbox sequenceWidth"><input type="checkbox"/></td>' +
                        '<td class="serial sequenceWidth"><span>' + (i + 1) + '</span></td>' +
                        '<td class="dept deptWidth" deptid=' + dataPlan.hfdept + '>' + dataPlan.deptname + '</td>' +
                        '<td class="name planNameWidth">' + dataPlan.planname + '</td>' +
                        '<td class="explan explainWidth">' + dataPlan.explanation + '</td>';
                    '</tr>';
                }
                $("#bigPlanTbody").append(html);
//		 			}
//		 		}
            },
            function () {
                layer.alert('查询失败！');
            }
        );
    }

    //小计划初始化
    function initSmallPlan(parentSeqId) {
        $("#smallPlanTbody").html(""); // 先清空小计划数据
        var url = contextPath + "/HUDH_AddVisitAct/findvisitPlanTemplate.act";
        parms = {"managarId": parentSeqId};
        $.axse(url, parms,
            function (data) {
                // console.log(JSON.stringify(data)+"-----小计划初始化数据");
                if (data[0] == null) {
                    addSmallPlan(parentSeqId);
                    return;
                }
                if (data != null) {
                    if (data.length > 0) {
                        $('#smallPlanTbody').html("");
                        //var html='';
                        for (var i = 0; i < data.length; i++) {
                            var html = '';
                            var dataPlan = data[i];
                            // console.log(JSON.stringify(dataPlan)+"-----");
                            var now = new Date();
                            var time = now.getFullYear() + "-" + ((now.getMonth() + 1) < 10 ? "0" : "") + (now.getMonth() + 1) + "-" + (now.getDate() < 10 ? "0" : "") + now.getDate();
                            html += '<tr class="aaa">' +
                                '<td class="smallPlanID" hidden>' + dataPlan.seqId + '</td>' +
                                '<td class="parentPlanID" hidden>' + parentSeqId + '</td>' +
                                '<td class="spcbox nowtime">' + time + '</td>' +
                                '<td class="spDayNum daysWidth" contenteditable="true">' + dataPlan.plandays + '</td>' +
                                '<td class="spType visitTypeWidth">' +
                                '<div class="out_div">' +
                                '<span class="out_span">' +
                                '<select class="select2" tig="HFFL" name="hffl" id="hffl' + i + '" onchange="selectText(this)"></select>' +
                                '</span>' +
                                '<input class="fu_input newsptype" name="newsptype" id="newsptype' + i + '">' +
                                '</div>' +
                                '</td>' +
                                '<td class="spFollow followWidth">';
                            //初始化是否强制跟进
                            if (dataPlan.ismandatory == "true") {
                                html += '<input checked type="checkbox"/>';
                            } else {
                                html += '<input type="checkbox"/>';
                            }
                            html += '</td>' +
                                //   		 		             '<td class="spdept deptWidth"><select class="hfdept select2" name="hfdept" id="hfdept"></select></td>'+
                                //   		 		             '<td class="spPerson hfperson_Width"><select>'+dataPlan.visitstaff+'</select></td>'+
                                '<td class="spSLType sltype_Width"><select class="dict" tig="SLLX" id="shouli' + i + '"></select></td>' +
                                '<td class="spExplain smallexplainWidth" contenteditable="true">' + dataPlan.remark + '</td>' +
                                '<td class="smalloperateWidth">' +
                                '<button class="btn smallPlanDel" onclick="smallPlanDel(this);">删除</button>' +
                                //   		 		                 '<button class="btn bigPlanSave" href="javascript:;" onclick="smallPlanSave(this)">保存</button>'+
                                '</td>' +
                                '</tr>';
                            $("#smallPlanTbody").append(html);
                            var hffl = $("#smallPlanTbody").find("tr").eq(i).find("td.spType").find("select"); // 当前所在行的回访类型select下拉框
                            initVisitType('triggerChange', hffl, dataPlan.visittype); // 初始化回访类型
                            //initVisitType('triggerChange',"hffl"+i,dataPlan.visittype); // 初始化回访类型
                            var sllx = $("#smallPlanTbody").find("tr").eq(i).find("td.spSLType").find("select"); // 当前所在行的受理类型select下拉框
                            initVisitType('triggerChange', sllx, dataPlan.accepttype); // 初始化受理类型
                            //initVisitType('triggerChange',"hffl"+i,dataPlan.visittype); // 初始化回访类型
                            //initVisitType('triggerChange',"shouli"+i,dataPlan.accepttype); // 初始化受理类型
                        }
                        //$("#smallPlanTbody").append(html);
                    }
                }
            },
            function () {
                layer.alert('查询统计失败！');
            }
        );
    }

    /**
     * 初始化回访类型、受理类型
     * 参数：operFlag(自动触发事件) obj(当前下拉框ID) val(当前下拉框有无赋值)
     */
    function initVisitType(operFlag, obj, val) {
        //var dict = $("#"+obj);
        var dict = $(obj);
        var type = dict.attr("tig");
        var url = contextPath + "/YZDictAct/getListByParentCode.act?parentCode=" + type;
        $.axse(url, null,
            function (data) {
                var list = data.list;
                if (list != null && list.length > 0) {
                    dict.empty();
                    dict.append("<option value=''>请选择</option>");
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                    }
                }
                if (val) {
                    dict.val(val); // 初始化有值赋值
                }
                if ('triggerChange' == operFlag) { // 触发Onchange事件 #### add by yangsen
                    $(dict).trigger('change'); // 和 连锁代码片段的 $("#organization").change 配合使用
                }
            },
            function () {
                layer.alert('查询出错！');
            });
    }

    // 小计划前端删除 
    function smallPlanDel(thisbutton) {
// 		$(thisbutton).parent().parent().remove();
        layer.confirm("确认删除吗？", {
            btn: ['确认', '取消']
        }, function (index) {
            //确认
            $(thisbutton).parent().parent().remove();
            layer.close(index);
            return true;
        }, function (index) {
            //删除
            layer.close(index);
            return false;
        });
    }

    // 大计划选中行
    $(document).on("dblclick", "#bigPlanTbody tr", function () {
//       console.log("大计划双击事件");
        var serial = $(this).find("td.serial").text();
        var dept = $(this).find("td.dept").text();
        var name = $(this).find("td.name").text();
        var explan = $(this).find("td.explan").text();
        var parentSeqId = $(this).find("td.bigPlanID").text();
        var obj = {};
        obj.serial = serial;
        obj.dept = dept;
        obj.name = name;
        obj.explan = explan;
        obj.seqId = parentSeqId;
//   	  console.log(JSON.stringify(obj)+"====");
        var bigPlanTr = $("#bigPlanTbody").find("tr");
        for (var i = 0; i < bigPlanTr.length; i++) {
//        console.log("删除其他样式循环");
            if (bigPlanTr.eq(i).hasClass("trActive")) {
                bigPlanTr.eq(i).removeClass("trActive");
            }
        }
        $(this).addClass("trActive");
        $(".smallPlanAdd").removeAttr("disabled"); // 去掉小计划添加按钮禁用
        $(".smallPlanAdd").removeClass("disAbledBtn").addClass("startBtn");
//       console.log("大计划选中行-------------"+parentSeqId);
        initSmallPlan(parentSeqId);//小计划初始化
        //initDictSelectByClass('triggerChange'); // 咨询项目、受理类型、受理工具
        //initDictSelectByClass(); // 回访分类
    })

    // 小计划添加
    function addSmallPlan(parentSeqId) {
        //得到父级id
        var parentSeqId = "";
        var bigPlanTr = $("#bigPlanTbody").find("tr");
        for (var i = 0; i < bigPlanTr.length; i++) {
            if (bigPlanTr.eq(i).hasClass("trActive")) {
                parentSeqId = bigPlanTr.eq(i).find("td.bigPlanID").text();
            }
        }
        var now = new Date();
        var time = now.getFullYear() + "-" + ((now.getMonth() + 1) < 10 ? "0" : "") + (now.getMonth() + 1) + "-" + (now.getDate() < 10 ? "0" : "") + now.getDate();
        var smallPlanTr = $("#smallPlanTbody").find("tr").length;//当前已有小计划长度
        var appendHtml = "";
        appendHtml += "<tr class=''>";
        appendHtml += "<td class='smallPlanID' hidden></td>";
        appendHtml += "<td class='parentPlanID' hidden>" + parentSeqId + "</td>";
        appendHtml += "<td class='spcbox nowtime'>" + time + "</td>";
        appendHtml += "<td class='spDayNum  daysWidth' contenteditable='true'></td>";
        appendHtml += "<td class='spType visitTypeWidth'>" +
            "<div class='out_div'>" +
            "<span class='out_span'>" +
            "<select class='select2' tig='HFFL' name='hffl' id='hffl" + smallPlanTr + "' onchange='selectText(this)'></select>" +
            "</span>" +
            "<input class='fu_input newsptype' name='newsptype' id='newsptype" + i + "'>" +
            "</div>" +
            "</td>";
        appendHtml += "<td class='spFollow followWidth'><input type='checkbox'/></td>";
        appendHtml += "<td class='spSLType sltype_Width'><select class='dict' tig='SLLX' id='shouli" + smallPlanTr + "' ></select></td>";
        appendHtml += "<td class='spExplain smallexplainWidth' contenteditable='true' placeholder='请输入回访要点'></td>";
        appendHtml += "<td class='smalloperateWidth'>";
        appendHtml += "<button class='btn smallPlanDel' onclick='smallPlanDel(this);'>删除</button>";
        appendHtml += "</td>";
        appendHtml += "</tr>";
// 		console.log(appendHtml+"------temp----");
        $("#smallPlanTbody").append(appendHtml);
        document.getElementById('smallPlanTbody').scrollTop = document.getElementById('smallPlanTbody').scrollHeight; //设置滚动条位置
        var hffl = $("#smallPlanTbody").find("tr").eq(smallPlanTr).find("td.spType").find("select"); // 当前所在行的回访类型select下拉框
        initVisitType('triggerChange', hffl); // 初始化回访类型
        var sllx = $("#smallPlanTbody").find("tr").eq(smallPlanTr).find("td.spSLType").find("select"); // 当前所在行的受理类型select下拉框
        initVisitType('triggerChange', sllx); // 初始化受理类型
        //initDictSelectByClass('triggerChange'); // 咨询项目、受理类型、受理工具
        //initDictSelectByClass(); // 回访分类
    }

    //   小计划的input----select值
    function selectText(thi) {
        var text = $(thi).find("option:selected").text();//选中的文本
        $(thi).parent().siblings().val(text);
    }

    //提交
    function submitu() {
        var checkbool = true;
        if (checkbool) {
            //验证
            var usercode = $("#usercode").val();
            var username = $("#username").val();
            var sex = $("#sex").val();
            var telphone = $("#telphone").val();
            var visitor = $("#visitor").val();
// 			var hfyd = $("#hfyd").val();

            /* 	患者大计划*/
            var bigPlanTr = $("#bigPlanTbody").find("tr");
            for (var i = 0; i < bigPlanTr.length; i++) {
                if (bigPlanTr.eq(i).hasClass("trActive")) {
                    bigPlanName = bigPlanTr.eq(i).find("td.name").text();
                    bigPlanExplan = bigPlanTr.eq(i).find("td.explan").text();
//   		        console.log(bigPlanName+"----"+bigPlanExplan);
                }
            }
            /*   患者小计划           */
            var smallPlanArr = [];
            $("#smallPlanTbody").find("tr").each(function (i, obj) {
                var smallPlanObj = {};
                smallPlanObj.managarId = $(this).find("td.parentPlanID").text(); //父计划ID
                smallPlanObj.SEQ_ID = $(this).find("td.smallPlanID").text(); //小计划ID
                smallPlanObj.nowDate = $(this).find("td.nowtime").text(); //当日时间
                smallPlanObj.plandays = $(this).find("td.spDayNum").text(); //跟进天数
                smallPlanObj.visittype = $(this).find("td.spType").find("input[name='newsptype']").val();  //回访类型
                smallPlanObj.visittypeId = $(this).find("td.spType").find("option:selected").val(); //回访类型id
                smallPlanObj.ismandatory = $(this).find("td.spFollow").find("input[type='checkbox']").is(':checked');
                smallPlanObj.accepttype = $(this).find("td.spSLType").find("option:selected").text(); //受理类型
                smallPlanObj.accepttypeId = $(this).find("td.spSLType").find("option:selected").val(); //受理类型id
                smallPlanObj.remark = $(this).find("td.spExplain").text(); //说明
                smallPlanArr.push(smallPlanObj);
            });
//  			console.log(JSON.stringify(smallPlanArr)+"--------小计划批量保存");

// 提交校验***********************************************
// 			跟进天数校验
            for (var i = 0; i < smallPlanArr.length; i++) {
                var date = smallPlanArr[i].plandays;
                var visittype = smallPlanArr[i].visittype;
                var accepttypeId = smallPlanArr[i].accepttypeId;
                if (!date) {
                    layer.alert("请填写跟进天数！");
                    return false;
                }
                if (isNaN(date)) {
                    layer.alert("跟进天数必须为数字！");
                    return;
                }
                if (!(/(^[1-9]\d*$)/.test(date))) {
                    layer.alert("跟进天数必须为正整数！");
                    return false;
                }
                if (visittype == "请选择" || visittype == "" || visittype == null) {
                    layer.alert("请填写回访类型！");
                    return false;
                }
                if (accepttypeId == "" || accepttypeId == null) {
                    layer.alert("请填写受理类型！");
                    return false;
                }
            }
// 			回访人员校验
            if (visitor == "" || visitor == null) {
                layer.alert('请选择回访人员', {});
                return false;
            }
// 			选择回访大计划校验
            if (smallPlanArr[0] == null) {
                layer.alert('请选择回访计划！', {});
                return false;
            }
//         选择回访小计划校验
            for (var i = 0; i < smallPlanArr.length; i++) {
                if (!smallPlanArr[i].plandays) {
                    layer.alert("请填写新增患者详细计划！");
                    return false;
                }
            }
            var param = {
                usercode: usercode,
                username: username,
                sex: sex,
                telphone: telphone,
                visitor: visitor,
//				hfyd : hfyd,
//				regno : regno,
// 				患者计划
                bigPlanName: bigPlanName,
                bigPlanExplan: bigPlanExplan,
                smallList: JSON.stringify(smallPlanArr)
            };
// 			console.log(JSON.stringify(param)+"*--------param");
//  			return;
            var url = '<%=contextPath%>/KQDS_VisitAct/insertOrUpdate1.act';
            $.axseSubmit(url, param, function () {
                },
                function (r) {
                    if (r.retState == "0") {
                        layer.alert('添加成功', {
                            yes: function (index) {
                                try {
                                    parent.$("#tabIframe")[0].contentWindow.refresh();
                                } catch (e) {
                                    if (window.parent.patienPageIdentifying == "returnVisit") {
                                        window.parent.refresh();
                                    }
                                }
                                parent.layer.close(frameindex); //再执行关闭
                            }

                        });
                    } else {
                        layer.alert('添加失败：' + r.retMsrg);
                        contentWindow
                    }
                },
                function () {
                    layer.alert('添加失败');
                }
            );
        }
    }

    //加载病人信息
    function getUserDetail() {
        var url = '<%=contextPath%>/KQDS_VisitAct/selectCostorderDetailByUsercode.act?usercode=' + usercode;
        $.axseY(url, null,
            function (data) {
                $("#usercode").val(data.data.usercode);
                $("#username").val(data.data.username);
                $("#telphone").val(data.data.phonenumber1);
                $("#sex").val(data.data.sex);
            },
            function () {
                layer.alert('查询出错！');
            }
        );
    }

    // 查询部门
    function initDept() {
        if ($(".hfdept").length > 0) {
            for (var i = 0; i < $(".hfdept").length; i++) {
                var dict = $(".hfdept").eq(i);
                // :eq() 选择器选取带有指定 index 值的元素。index值从 0 开始，所有第一个元素的 index 值是 0（不是1）。
//             var type = dict.attr("tig");
                var url = contextPath + "/YZDeptAct/findDeptList.act";
                $.axse(url, null,
                    function (data) {
                        //console.log(JSON.stringify(data)+"--------datadaa");
                        var list = data;
                        if (list != null && list.length > 0) {
                            dict.empty();
                            dict.append("<option value=''>请选择</option>");
                            for (var j = 0; j < list.length; j++) {
                                var optionStr = list[j];
                                dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.deptName + "</option>");
                            }
                        }
                    },
                    function () {
                        layer.alert('查询出错！');
                    });
            }
        }
    }

    //联动回访人员
    function getVisitPersonSelect(deptId, selectId, isAdd) {
        var deptId = $("#" + deptId).val();
        var dict = $("#" + selectId);
        if (deptId == null || deptId == '') { // 如果切换门诊，导致重新初始化一级目录
            dict.empty();
            dict.append("<option value=''>请选择</option>");
            return false; // 终止执行
        }
        var url = contextPath + "/YZPersonAct/findVisualPersonnel.act?deptId=" + deptId;
        if (isAdd) {
            url += '&isAdd=1';
        }
        var organization = $("#organization").val(); // ### 容错处理
        if (organization) {
            url += '&organization=' + organization;
        }
        dict.empty();
        dict.append("<option value=''>请选择</option>");
        $.axse(url, null,
            function (data) {
//      	console.log(JSON.stringify(data)+"-------data----next---")
                if (data.length > 0) {
                    var list = data;
                    if (list != null && list.length > 0) {
                        for (var j = 0; j < list.length; j++) {
                            var optionStr = list[j];
                            dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.userName + "</option>");
                        }
                    }
                } else {
                    layer.alert('查询出错！');
                }
            },
            function () {
                layer.alert('查询出错！');
            });
    }

</script>
</html>
