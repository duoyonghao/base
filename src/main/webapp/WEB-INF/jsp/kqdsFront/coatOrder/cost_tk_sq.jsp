<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);
	String ispush = request.getParameter("ispush");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>退款申请</title><!-- 接待中心  下方退款按钮进入 退款列表， 点击 退款 进入申请页面  -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/costOrder/refundRequest.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css"/>
<style>
.fixed-table-container thead th .sortable{
	padding-right:8px;
}
.fixed-table-container{
	border-left: 1px solid #ddd;
	border-right: 1px solid #ddd;
	border-bottom:1px solid #ddd;
	border-radius: 6px;
	/* border-top-left-radius: 6px;
	border-top-right-radius: 6px; */
	overflow: hidden;
}
</style>
</head>
<body>
	<div class="refundRequestContanier"> <!-- refundRequestContanier 退款申请样式 -->
		<div class="headerDiv">	
			<span class="commonSpan">收费时间</span>
			<!--.unitsDate本身无样式 时间验证有关系  -->
			<input class="unitsDate whiteInp"  type="text" placeholder="开始日期" id="starttime">
            <span class="commonSpan">到</span>
            <!--.unitsDate本身无样式 时间验证有关系  -->
            <input class="unitsDate whiteInp" type="text"  placeholder="结束日期" id="endtime">
            <!--commonSpan通用文本样式     .fuzzyQuerySpan提供间距的样式-->
			<span class="commonSpan fuzzyQuerySpan">模糊查询</span>
			<input class="fuzzyQuery" type="text" id="searchValue" name="searchValue" value>
			
			<a onclick="clean()" href="javascript:void(0);" class="kqdsCommonBtn">清 空</a>
			<a id="searchHzda" href="javascript:void(0);" class="kqdsSearchBtn">查 询</a>
			
		</div>
		
		<div class="sectionDiv">
			<span class="titleText">查询清单</span>
			<div class="tableBox">
			    <table id="table" class="table-striped table-condensed table-bordered" data-height="150"></table>
        	</div>
        	<span class="titleText">详细项目清单</span>
        	<div class="tableBox" id="divkdxm">
			    <table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="150"></table>
        	 </div>
        	<div id="buttonBar"> 
                     <table style="width:90%;text-align: center;"> 
                 		<tr>
                			<td style="width:12%" align="right"><span id="tkezSpan" style="color:#00A6C0;">退款总额:<lable id="tkze">0</lable></span></td>
                 		</tr> 
                 	</table> 
         	</div> 
		</div>
		<footer class="fixedBottomDiv">
			<div class="clear2"></div>
         	<a class="kqdsCommonBtn" id="sqtk">申请退款</a>
		</footer>
	</div>
	
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript">
var ispush = "<%=ispush%>";
var contextPath = "<%=contextPath%>";
var personrole = "<%=person.getUserPriv()%>";
var pageurl = '<%=contextPath%>/KQDS_CostOrderAct/getByRegnoNopage.act';
var onclickrowOobj = "";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
    //1、表格初始化
    $("#tkezSpan").hide();
    //当前日期
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    //时间选择
    $("input.unitsDate").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "buttom-right"
    });
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    getlist();
    OrderDetail();
    /* 详细项目清单初始化  目的:显示出表头 */
});
$('#searchHzda').on('click',
function() {
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
});
function clean() {
    $(".headerDiv :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
}
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        limit: params.limit,
        offset: params.offset,
        istk: 0,
        isyjjitem: 0,
        searchValue: $('#searchValue').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        status: 2
    };
    return temp;
}
function getlist() {
    $("#table").bootstrapTable({
        url: pageurl,
        queryParams: queryParams,
        dataType: "json",
        onLoadSuccess:function(){
        	setHeight();
        },
        columns: [{
            title: '病人编号',
            field: 'usercode',
            align: 'center',
            visible: false,
            switchable: false,
            formatter: function(value) {
                return '<span>' + value + '</span>';
            }
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '成交情况',
            field: 'cjstatus',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "1") {
                    return '<span style="color:green">已成交</span>';
                } else {
                    return '<span style="color:red">未成交</span>';
                }
            }
        },
        {
            title: '合计',
            field: 'totalcost',
            align: 'center',
            sortable: true,
            formatter: function(value) {
                return '<span>' + value + '</span>';
            }
        },
        {
            title: '免除金额',
            field: 'voidmoney',
            align: 'center',
            sortable: true,
            formatter: function(value) {
                return '<span>' + value + '</span>';
            }
        },
        {
            title: '应收金额',
            field: 'shouldmoney',
            align: 'center',
            sortable: true,
            formatter: function(value) {
                return '<span>' + value + '</span>';
            }
        },
        {
            title: '欠费金额',
            field: 'y2',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (Number(value) != 0) {
                    return '<span style="color:red">' + value + '</span>';
                } else {
                    return value;
                }
            }
        },
        {
            title: '缴费金额',
            field: 'actualmoney',
            align: 'center',
            sortable: true,
            formatter: function(value) {
                return '<span>' + value + '</span>';
            }
        },
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            visible: false,
            switchable: false
        },
        {
            title: '开单时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                html = '<span class="time" >' + value.substring(5) + '</span>';
                return html;
            }
        },
        {
            title: '开单人',
            field: 'createusername',
            align: 'center',
            formatter: function(value) {
                return '<span>' + value + '</span>';
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $("#tkezSpan").show();
        if (onclickrowOobj != null && onclickrowOobj != "" && onclickrowOobj != "null" && onclickrowOobj != "undefined") {
            var newindex = $(element)[0].rowIndex - 1;
            var newobj = $('#table').bootstrapTable('getData')[newindex];
            if (onclickrowOobj.seqId != newobj.seqId) {
                layer.confirm('是否放弃本单退款？', {
                    btn: ['是', '否'] //按钮
                },
                function() {
                	$("#tkze").html(0);
                    layer.closeAll('dialog');
                    $('.success').removeClass('success'); //去除之前选中的行的，选中样式
                    $(element).addClass('success'); //添加当前选中的 success样式用于区别
                    var index = $('#table').find('tr.success').data('index'); //获得选中的行
                    onclickrowOobj = $('#table').bootstrapTable('getData')[index];
                    //deletetkDetaildel(onclickrowOobj.costno);
                    OrderDetail(onclickrowOobj.costno); //
                });
            }
        } else {
            $('.success').removeClass('success'); //去除之前选中的行的，选中样式
            $(element).addClass('success'); //添加当前选中的 success样式用于区别
            var index = $('#table').find('tr.success').data('index'); //获得选中的行
            onclickrowOobj = $('#table').bootstrapTable('getData')[index];
            OrderDetail(onclickrowOobj.costno); // 
        }
    });
}
function OrderDetail(costno) {
    $("#divkdxm").html('<table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="200"></table>');
    var detailurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/selectNoPage.act?costno=' + costno;

    $("#dykdxm").bootstrapTable({
        url: detailurl,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess:function(){
        	setHeight();
        },
        columns: [{
            title: '治疗项目',
            field: 'itemname',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                var html = '<span class="time" style="vertical-align:middle; text-align:left;" title="' + value + '">';
                if (row.y2 < 0) {
                    html += '<span class="label label-warning">还款</span>';
                } else if (row.y2 > 0) {
                    html += '<span class="label label-danger" style="color:#fff;background:tomato;border-radius:3px;font-size:10px;">欠款</span>';
                } else if (Number(row.y2) == 0 && row.isqfreal == 1) {
                    html += '<span class="label label-warning">还款</span>';
                }
                html += '<span>' + value + '</span></span>';
                return html;
            }
        },
        {
            title: '单位',
            field: 'unit',
            align: 'center',
            sortable: true,
            formatter: function(value) {
                return '<span>' + value + '</span>';
            }
        },
        {
            title: '单价',
            field: 'unitprice',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span>' + "￥" + row.unitprice + '</span>';
            }
        },
        {
            title: '数量',
            field: 'num',
            align: 'center',
            formatter: function(value) {
                return '<span>' + value + '</span>';
            }
        },
        {
            title: '折扣',
            field: 'discount',
            align: 'center',
            formatter: function(value) {
                return '<span>' + value + '</span>';
            }
        },
        {
            title: '应收',
            field: 'subtotal',
            align: 'center',
            formatter: function(value, row, index) {
                return '<span>' + "￥" + (Number(row.subtotal) - Number(row.voidmoney).toFixed(2)) + '</span>';
            }
        },
        {
            title: '欠费金额',
            field: 'y2',
            align: 'center',
            formatter: function(value, row, index) {
            	if((row.y2)!="undefined" ){
            		return '<span>' + "￥0.00" + '</span>';	
            	}else{
                	return '<span>' + "￥" + row.y2 + '</span>';	
            	}
            }
        },
        {
            title: '缴费金额',
            field: 'paymoney',
            align: 'center',
            formatter: function(value, row, index) {
                return '<span>' + "￥" + (row.paymoney) + '</span>';
            }
        },
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            visible: false,
            switchable: false
        },
        {
            title: '退款数量',
            field: 'tknum',
            align: 'center',
            formatter: function(value, row, index) {
                return '<input  type="number" min="1" style="width:100%;float:left; height:24px;text-align:center;border: solid 1px #e5e5e5;"  onfocus="this.select()"   id="tknum' + index + '">';
            }
        },
        {
            title: '退款金额',
            field: 'tkmoney',
            align: 'center',
            formatter: function(value, row, index) {
                return '<input  type="number" style="width:100%;float:left; height:24px;text-align:center;border: solid 1px #e5e5e5;"  value="0" onfocus="this.select()" onchange="editqkze(\'' + index + '\',\'tkmoney\');"  id="tkmoney' + index + '">';
            }
        },
        {
            title: '退款原因',
            field: 'remark',
            align: 'center',
            formatter: function(value, row, index) {
                return '<input  type="text" min="1" style="width:100%;float:left; text-align:center; height:24px;"  onfocus="this.select()"   id="tkremark' + index + '">';
            }
        }]
    });
}
$('#sqtk').on('click',
function() {
    //var tkze = $("#tkze").html();
  	//遍历 计算 收费单的 退款总金额
    var list = $('#dykdxm').bootstrapTable('getData');
    var newtkze = 0;
    for (var i = 0; i < list.length; i++) {
        newtkze += Number($("#tkmoney" + i).val());
    }
    var tkze=newtkze.toFixed(2);
    var tkyy = false; //退款原因： 只要由一项退款原因填写了 则通过验证
    var tsqk = false; //全部免除的项目也可以提款（防止开错项目的）
    var list = $('#dykdxm').bootstrapTable('getData');
    for (var i = 0; i < list.length; i++) {
        var tkmoney = $("#tkmoney" + i).val();
        var tkremark = $("#tkremark" + i).val();
        if ((Number(list[i].paymoney) == 0 || Number(tkmoney) == 0) && tkremark != "") {
            tsqk = true;
            tkyy = true;
            continue;
        }
        if (tkmoney != "") {
            if (tkremark != "") {
                tkyy = true;
            }
        } else {
            continue;
        }
    }
    if (!tkyy) {
        tkyy = true;
        layer.alert('请填写退款原因' );
        return false;
    }
    if (!tsqk) {
        if (tkze != "") {
            if (Number(tkze) == 0) {
                layer.alert('没有选择退款项', {
                      
                });
                return false;
            }
        } else {
            layer.alert('没有选择退款项'  );
            return false;
        }
    }
    //创建退款单
    var paramOrder = {
        costno: onclickrowOobj.costno,
        tkze: tkze
    };
    //循环获取表格中项目
    var list = [];
    var listData = $('#dykdxm').bootstrapTable('getData');
    for (var i = 0; i < listData.length; i++) {
        var tknum = $("#tknum" + i).val();
        var tkmoney = $("#tkmoney" + i).val();
        var tkremark = $("#tkremark" + i).val();
        var flag = (Number(listData[i].paymoney) == 0 || Number(tkmoney) == 0) && tkremark != "";
        if (!Number(tkmoney) > 0 && !flag) {
            continue;
        }
        var param = {
            orderdetailno: listData[i].seqId,
            tknum: tknum,
            tkmoney: tkmoney,
            remark: tkremark
        };
        list.push(param);
    }
    var data = JSON.stringify(list);
    paramOrder.listDetail = data;
    var detailurl = '<%=contextPath%>/KQDS_RefundAct/insertOrUpdate.act';
    $.axse(detailurl, paramOrder,
    function(data) {
        if (data.retState == 0) {
            layer.alert('申请成功', {
                  
                end: function() {
                    parent.refresh();
                    parent.layer.close(frameindex); //再执行关闭  
                }
            });
        }
    },
    function() {
        layer.alert('申请失败' );
    });
});
function editqkze(index, name) {
    var newobj = $('#dykdxm').bootstrapTable('getData')[index];
    var param = {
        usercode: newobj.usercode,
        itemno: newobj.itemno,
        qfbh: newobj.qfbh,
        detailId: newobj.seqId
    };

    var sszje = 0; //实收总金额  
    var detailurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/checkTf.act';
    $.axse(detailurl, param,
    function(data) {
        sszje = Number(data.symoney);
    },
    function() {
        layer.alert('查询出错！' );
    });
    //判断 修改的收费项目的退费总金额是否超过实收总金额
    //收费项目的退费总金额
    var tkmoney = $("#tkmoney" + index).val();
    if(sszje>0){
    	if (Number(tkmoney).toFixed(2) < 0) {
            $("#" + name + index).val(0);
            layer.alert('退款金额不能小于0！' );
            traverseTkze();
            return false;
        }
    }else{
    	if (Number(tkmoney).toFixed(2) != sszje) {
    		$("#" + name + index).val(sszje);
            layer.alert('退款金额保持一致！' );
            traverseTkze();
            return false;
        }
    }
    if ((Number(tkmoney).toFixed(2) - Number(sszje).toFixed(2)) > 0) {
        $("#" + name + index).val(0);
        $("#tkmoney" + index).val(0);
        layer.alert('该收费项目的退款金额大于缴费金额！' );
        traverseTkze();
        return false;
    }
    traverseTkze();
}
function traverseTkze(){
	//遍历 计算 收费单的 退款总金额
    var list = $('#dykdxm').bootstrapTable('getData');
    var newtkze = 0;
    for (var i = 0; i < list.length; i++) {
        newtkze += Number($("#tkmoney" + i).val());
    }
    $("#tkze").html(newtkze.toFixed(2));
}
function setHeight(){
	var height=($(window).outerHeight()-177)/2;
	$("#table").bootstrapTable("resetView",{
		height:height
	})
	$("#dykdxm").bootstrapTable("resetView",{
		height:height
	})
}
</script>
</html>
