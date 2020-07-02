<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <meta charset="utf-8"/>
    <title>预交金</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css"/>
    <!-- 数据表中数据的样式 -->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css"/>

    <style>
        .fixed-table-container {
            min-height: 240px;
        }

        .fixed-table-container thead th .sortable {
            padding-right: 8px;
        }
    </style>
</head>
<body>
<div class="">
    <div class="register-bottom" style="margin-left: 5px;">
        <a id="clear" href="javascript:void(0);" class="kqdsCommonBtn">清 空</a>
        <a id="searchHfzx" href="javascript:void(0);" class="kqdsSearchBtn">查 询</a>
        <div class="kv">
            <label>患者姓名：</label>
            <input type="text" placeholder="患者姓名" id="queryusercode" style="width: 120px;">
        </div>
        <div class="kv">
            <label>会员卡号：</label>
            <input type="text" placeholder="会员卡号" id="querymemberno" style="width: 120px;">
        </div>
    </div>
    <!--表格-->
    <div class="tableBox">
        <table id="table" class="table-striped table-condensed table-bordered" data-height="250">
        </table>
    </div>
</div>
</body>


<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript"
        src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript"
        src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"
        charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>


<script type="text/javascript">

    var contextPath = '<%=contextPath%>';

    var $table = $('#table');
    var pageurl = '<%=contextPath%>/KQDS_MemberAct/selectList.act';
    var onclickrowOobj = "";

    var num = 0;
    $(function () {
        var nowday = getNowFormatDate();
        var pageurl1 = pageurl + "?1=1";
        //判断父页面是否选中行 选中则根据选中的值查询
        onclickrowOobj = window.parent.onclickrowOobj;
        if (onclickrowOobj != "" && onclickrowOobj != null && onclickrowOobj != "null" && onclickrowOobj != "undefined") {
            $("#queryusercode").val(onclickrowOobj.username);
            pageurl1 += '&username=' + onclickrowOobj.username; // 这个只根据名字查，名字出现重复就不行了，会出现两条记录
            if (onclickrowOobj.usercode) {
                pageurl1 += '&usercode=' + onclickrowOobj.usercode;
            }
        } else {
            pageurl1 = pageurl1 + '&createtime=' + nowday;
        }

        $table.bootstrapTable({
            url: pageurl1,
            dataType: "json",
            contentType: "application/x-www-form-urlencoded",   //必须有
            cache: false,
            pagination: true,//是否显示分页（*）
            pageSize: 25,
            pageList: [15, 20, 25, 50, 100],//可以选择每页大小
            striped: true,
            clickToSelect: false,
            singleSelect: false,
            paginationShowPageGo: true,
            sidePagination: "server",//后端分页
            onLoadSuccess: function (data) { //加载成功时执行
            },
            columns: [{
                title: '操作',
                field: 'pkcode',
                align: 'center',
                formatter: function (value, row, index) {
                    var yujiao = '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="chongzhi(\'' + row.seqId + '\',\'' + row.usercode + '\',\'' + row.memberno + '\',\'' + row.money + '\',\'' + row.givemoney + '\',\'' + row.totalmoney + '\')">充值</a> ';
                    return yujiao;
                }
            },
                {
                    title: '患者编号',
                    field: 'usercode',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    formatter: function (value, row, index) {
                        return '<span title="' + value + '">' + value + '</span>';
                    }
                },
                {
                    title: '姓名',
                    field: 'username',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    editable: true,
                    formatter: function (value, row, index) {
                        return '<span class="name" title="' + value + '">' + value + '</span>';
                    }
                },
                {
                    title: '会员卡号',
                    field: 'memberno',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    editable: true,
                    formatter: function (value, row, index) {
                        return '<span title="' + value + '">' + value + '</span>';
                    }
                },
                {
                    title: '会员卡类型',
                    field: 'memberlevel',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    editable: true,
                    formatter: function (value, row, index) {
                        return '<span class="time" title="' + value + '">' + value + '</span>';
                    }
                },
                {
                    title: '充值余额',
                    field: 'money',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    editable: true,
                },
                {
                    title: '赠送余额',
                    field: 'givemoney',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    editable: true,
                    formatter: function (value, row, index) {
                        var tmp = Number(row.givemoney);
                        return '<span class="name" title="' + tmp + '">' + tmp.toFixed(2) + '</span>';
                    }
                },
                {
                    title: '余额小计',
                    field: 'totalmoney',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    formatter: function (value, row, index) {
                        var totalm = Number(row.money) + Number(row.givemoney);
                        return '<span class="name" title="' + totalm + '">' + totalm.toFixed(2) + '</span>';
                    }
                },
                {
                    title: 'seqId',
                    field: 'seqId',
                    align: 'center',
                    valign: 'middle',
                    visible: false,
                    switchable: false
                }]
        });

        //时间选择
        $(".dataTime").datetimepicker({
            language: 'zh-CN',
            minView: 2,
            autoclose: true,
            format: 'yyyy-mm-dd',
            pickerPosition: "bottom-right"
        });

        //查询
        $('#searchHfzx').on('click',
            function () {
                var querymemberno = $("#querymemberno").val();
                var queryusercode = $("#queryusercode").val();
                if (querymemberno == "" && queryusercode == "") {
                    layer.alert('请选择查询条件');
                    return false;
                }

                var localUrl = pageurl + '?memberno=' + querymemberno + '&username=' + queryusercode;
                $('#table').bootstrapTable('refresh', {
                    'url': localUrl
                });
            });
        //清空
        $('#clear').on('click',
            function () {
                $("#querymemberno").val("");
                $("#queryusercode").val("");
            });
    });

    //充值
    function chongzhi(id, usercode, memberno, money, zmoney, totalmoney) {
        layer.open({
            type: 2,
            title: '充值',
            shadeClose: true,
            shade: 0.6,
            area: ['810px', '300px'],
            content: contextPath + '/KQDS_MemberAct/toMemberChongzhi.act?seqId=' + id + '&usercode=' + usercode + '&memberno=' + memberno + '&money=' + money + '&zmoney=' + zmoney + '&total=' + totalmoney //iframe的url
        });
    }
</script>

</html>
