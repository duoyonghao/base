<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	//合并测试
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuid");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>会员卡-会员卡查询</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/Load.js"></script><!-- 下拉分页 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/DataLazyLoad.js"></script>
</head>
<body>
<div id="container">
   <!--查询条件-->
     <div class="searchWrap">
            <div class="cornerBox">查询条件</div>
            <div class="btnBar">
                <a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
                <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
            </div>
            <div class="formBox">
               	<div class="kv">
               		<div class="kv" style="width:300px;">
						<label>操作时间</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" /><span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</span>
						</div>
					</div>
					<div class="kv" style="width:230px;">
						<label>咨询</label>
						<div class="kv-v">
							 <input type="hidden" name="askperson" id="askperson"  class="form-control"  value=""/>
							 <input type="text"  id="askpersonDesc" name="askpersonDesc" placeholder="咨询" readonly  onClick="javascript:single_select_user(['askperson', 'askpersonDesc'],'',1);"></input>	
						</div>
					</div>
		            <div class="kv">
	                    <label></label>
	                    <div class="kv-v">
	                        <input type="text" id="queryInput" class="searchInput" placeholder="请输入用户编号/姓名/会员卡号" style="width:200px;">
	                        <span class="orangeFont">*可模糊查询</span>
	                    </div>
                	</div>
                </div>
            </div>
        </div> 
    <div class="tableBox">
    	<table id="table" class="table-striped table-condensed table-bordered" data-height="250"></table>
    </div>
    <div class="tableBox">
    	<table style="width:98%;text-align: center;"> 
       		<tr>
       			<td style="width:10%"><span style="color:green;">期初充值余额:<lable id="tdqcmoney">0</lable></span></td>
      			<td style="width:10%"><span style="color:green;">期初赠送余额:<lable id="tdqcgivemoney">0</lable></span></td>
      			<td style="width:10%"><span style="color:green;">期初余额:<lable id="tdqctotal">0</lable></span></td>
      			<td style="width:10%"><span style="color:#00A6C0;">开卡-充值小计:<lable id="tdkkmoney">0</lable></span></td>
      			<td style="width:10%"><span style="color:#00A6C0;">开卡-赠送小计:<lable id="tdkkgivemoney">0</lable></span></td>
      			<td style="width:10%"><span style="color:#00A6C0;">开卡小计:<lable id="tdkktotal">0</lable></span></td>
      			<td style="width:10%"><span style="color:#00A6C0;">充值金额小计:<lable id="tdczmoney">0</lable></span></td>
      			<td style="width:10%"><span style="color:#00A6C0;">充值赠送小计:<lable id="tdczgivemoney">0</lable></span></td>
      			<td style="width:10%"><span style="color:#00A6C0;">充值小计:<lable id="tdcztotal">0</lable></span></td>
       		</tr>
       		<tr>
       			<td style="width:10%"><span style="color:orange;">缴费-充值小计:<lable id="tdjfmoney">0</lable></span></td>
      			<td style="width:10%"><span style="color:orange;">缴费-赠送小计:<lable id="tdjfgivemoney">0</lable></span></td>
      			<td style="width:10%"><span style="color:orange;">缴费小计:<lable id="tdjftotal">0</lable></span></td>
       			<td style="width:10%"><span style="color:red;">退费-充值小计:<lable id="tdtfmoney">0</lable></span></td>
      			<td style="width:10%"><span style="color:red;">退费-赠送小计:<lable id="tdtfgivemoney">0</lable></span></td>
      			<td style="width:10%"><span style="color:red;">退费小计:<lable id="tdtftotal">0</lable></span></td>
<!--       			<td style="width:10%"><span style="color:red;">转赠-充值小计:<lable id="tdzzmoney">0</lable></span></td> -->
<!--       			<td style="width:10%"><span style="color:red;">转赠-赠送小计:<lable id="tdzzgivemoney">0</lable></span></td> -->
<!--       			<td style="width:10%"><span style="color:red;">转赠小计:<lable id="tdzztotal">0</lable></span></td> -->
      			<td style="width:10%"><span style="color:green;">期末充值余额:<lable id="tdmoney">0</lable></span></td>
      			<td style="width:10%"><span style="color:green;">期末赠送余额:<lable id="tdgivemoney">0</lable></span></td>
      			<td style="width:10%"><span style="color:green;">期末余额:<lable id="tdtotal">0</lable></span></td>
       		</tr> 
       	</table>
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var onclickrow = "";
var onclickrowOobj = "";
var nowday;
var $table = $("#table");
var pageurl = '<%=contextPath%>/KQDS_MemberAct/selectMemberTongji.act';
//初始化表头，返回空的数据
var nullUrl = '<%=contextPath%>/UtilAct/intTableHeader.act';
var number = 1; //序号
var menuid = "<%=menuid%>";
var qxnameArr = ['zzcx_scbb'];
var func = ['exportTable'];
var isClick = true;

pagesize = 100;
$(function() {
	$("input[type='text']").attr("autocomplete","off");  //去掉input输入框的历史记录  lutian
    //获取当前页面所有按钮
    getButtonPowerByPriv(qxnameArr, func, menuid);
    //时间选择
    $(".unitsDate input").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "bottom-right"
    });

    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);

    //判断父页面是否选中行 选中则根据选中的值查询
    onclickrow = window.parent.onclickrowOobj;
    if (onclickrow != "" && onclickrow != null && onclickrow != "null" && onclickrow != "undefined") {
        $("#queryInput").val(onclickrow.username);
        $("#starttime").val("");
        $("#endtime").val("");
    }

    //加载表格
    getlist(nullUrl);
});

//页面加载查询的表格 总的
function getlist(requrl) {
    $table.attr("data-height", $(window).height() - 140);
    $table.bootstrapTable({
        url: requrl,
        dataType: "json",
        cache: false,
        striped: true,
        queryParams: queryParams,
        /* 		pagination: true,
		sidePagination: "server", */
        onDblClickCell: function(field, value, row, td) {
            //双击事件 查看操作记录
            onclickrow = row;
            onclickrowOobj = row;
            queryMemberRecord();
        },
        onRefresh: function(params) {
            requrl = params.url; // 重新赋值   这个方法执行后，加载数据，然后执行onLoadSuccess方法
        },
        onLoadSuccess: function(data) { //加载成功时执行
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","auto").css("pointer-events","auto");
				$("#query").text("查询");
			}
            if (nowpage == 0 && data.total > 0) {
                maxpage = Math.floor(data.total / pagesize) + 1;

                $("#tdmoney").html(data.tdmoney);
                $("#tdgivemoney").html(data.tdgivemoney);
                $("#tdtotal").html(data.tdtotal);

                $("#tdkkmoney").html(data.tdkkmoney);
                $("#tdkkgivemoney").html(data.tdkkgivemoney);
                $("#tdkktotal").html(data.tdkktotal);

                $("#tdczmoney").html(data.tdczmoney);
                $("#tdczgivemoney").html(data.tdczgivemoney);
                $("#tdcztotal").html(data.tdcztotal);

                $("#tdjfmoney").html(data.tdjfmoney);
                $("#tdjfgivemoney").html(data.tdjfgivemoney);
                $("#tdjftotal").html(data.tdjftotal);

                $("#tdtfmoney").html(data.tdtfmoney);
                $("#tdtfgivemoney").html(data.tdtfgivemoney);
                $("#tdtftotal").html(data.tdtftotal);

                $("#tdzzmoney").html(data.tdzzmoney);
                $("#tdzzgivemoney").html(data.tdzzgivemoney);
                $("#tdzztotal").html(data.tdzztotal);

                $("#tdqcmoney").html(data.tdqcmoney);
                $("#tdqcgivemoney").html(data.tdqcgivemoney);
                $("#tdqctotal").html(data.tdqctotal);

                /* 滚动条出现时 调整表头的宽度 */
                setTableHeaderWidth(".tableBox");
            }
            if (data.total == 0) {
                $("#tdmoney").html("0");
                $("#tdgivemoney").html("0");
                $("#tdtotal").html("0");

                $("#tdkkmoney").html("0");
                $("#tdkkgivemoney").html("0");
                $("#tdkktotal").html("0");

                $("#tdczmoney").html("0");
                $("#tdczgivemoney").html("0");
                $("#tdcztotal").html("0");

                $("#tdjfmoney").html("0");
                $("#tdjfgivemoney").html("0");
                $("#tdjftotal").html("0");

                $("#tdtfmoney").html("0");
                $("#tdtfgivemoney").html("0");
                $("#tdtftotal").html("0");

                $("#tdzzmoney").html("0");
                $("#tdzzgivemoney").html("0");
                $("#tdzztotal").html("0");

                $("#tdqcmoney").html("0");
                $("#tdqcgivemoney").html("0");
                $("#tdqctotal").html("0");
            }

            //分页加载
            showdata("table", data.rows);

            /*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        },
        columns: [{
            title: '序号',
            field: 'Number',
            align: 'center',
            sortable: true,
            editable: true,
            formatter:function(value, row, index){
            	return index+1;
            }
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '患者姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '患者来源',
            field: 'devchannel',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '来源子分类',
            field: 'nexttype',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '建档人',
            field: 'jdr',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '开发人',
            field: 'kfr',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '第一咨询',
            field: 'askperson',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name">' + value + '</span>';
            }
        },
        {
            title: '会员卡号',
            field: 'memberno',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '会员卡类型',
            field: 'memberlevel',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '折扣',
            field: 'discount',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '会员卡状态',
            field: 'memberstatus',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "未激活") {
                    return '<span style="color:green;">未激活</span>';
                } else if (value == "正常") {
                    return '<span style="color:red;">正常</span>';
                } else if (value == "已挂失") {
                    return '<span style="color:red;">已挂失</span>';
                } else if (value == "已补办") {
                    return '<span style="color:red;">已补办</span>';
                }
            }
        },
        {
            title: '期初充值余额',
            field: 'qcmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span class='money'>" + Number(value).toFixed(2) + "</span>";
            }
        },
        {
            title: '期初赠送余额',
            field: 'qcgivemoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span class='money'>" + Number(value).toFixed(2) + "</span>";
            }
        },
        {
            title: '期初余额小计',
            field: 'qctotal',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span class='money'>" + Number(value).toFixed(2) + "</span>";
            }
        },
        {
            title: '开卡-充值',
            field: 'kkmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span class='money'>" + Number(value).toFixed(2) + "</span>";
            }
        },
        {
            title: '开卡-赠送',
            field: 'kkgivemoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span class='money'>" + Number(value).toFixed(2) + "</span>";
            }
        },
        {
            title: '开卡小计',
            field: 'kktotal',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span class='money'>" + Number(value).toFixed(2) + "</span>";
            }
        },

        {
            title: '充值金额',
            field: 'czmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span class='money'>" + Number(value).toFixed(2) + "</span>";
            }
        },
        {
            title: '充值赠送',
            field: 'czgivemoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span class='money'>" + Number(value).toFixed(2) + "</span>";
            }
        },
        {
            title: '充值小计',
            field: 'cztotal',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span class='money'>" + Number(value).toFixed(2) + "</span>";
            }
        },

        {
            title: '缴费-充值',
            field: 'jfmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money" style="color:orange">' + Number(value).toFixed(2) + '</span>';
            }
        },
        {
            title: '缴费-赠送',
            field: 'jfgivemoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money" style="color:orange">' + Number(value).toFixed(2) + '</span>';
            }
        },
        {
            title: '缴费小计',
            field: 'jftotal',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money" style="color:orange">' + Number(value).toFixed(2) + '</span>';
            }
        },

        {
            title: '退费-充值',
            field: 'tfmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money" style="color:red">' + Number(value).toFixed(2) + '</span>';
            }
        },
        {
            title: '退费-赠送',
            field: 'tfgivemoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money" style="color:red">' + Number(value).toFixed(2) + '</span>';
            }
        },
        {
            title: '退费小计',
            field: 'tftotal',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money" style="color:red">' + Number(value).toFixed(2) + '</span>';
            }
        },

        {
            title: '转赠-充值',
            field: 'zzmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money" style="color:red">' + Number(value).toFixed(2) + '</span>';
            }
        },
        {
            title: '转赠-赠送',
            field: 'zzgivemoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money" style="color:red">' + Number(value).toFixed(2) + '</span>';
            }
        },
        {
            title: '转赠小计',
            field: 'zztotal',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money" style="color:red">' + Number(value).toFixed(2) + '</span>';
            }
        },
        {
            title: '期末充值余额',
            field: 'qmmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span class='money'>" + Number(value).toFixed(2) + "</span>";
            }
        },
        {
            title: '期末赠送余额',
            field: 'qmgivemoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span class='money'>" + Number(value).toFixed(2) + "</span>";
            }
        },
        {
            title: '期末余额小计',
            field: 'qmtotal',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span class='money'>" + Number(value).toFixed(2) + "</span>";
            }
        }]
    });
}

$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        offset: 0,
        limit: pagesize,
        ispaging: 1,
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        type: '开卡',
        askperson: $('#askperson').val(),
        queryInput: $("#queryInput").val()
    };
    return temp;
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
//点击导出
function exportTable() {
	if(isClick) {
		isClick = false;
		// console.log("生成报表")
		var fieldArr = [];
		var fieldnameArr = [];
		$('#table thead tr th').each(function() {
			var field = $(this).attr("data-field");
			if (field != "") {
				fieldArr.push(field); //获取字段
				fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
			}
		});
		var param = JsontoUrldata(queryParams());
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
				a.download = "会员卡查询";
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
$('#query').on('click',
function() {
	$(this).attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
	$(this).text("查询中");
    loadedData = [];
    nowpage = 0;
    $table.bootstrapTable('refresh', {
        'url': pageurl
    });
});

//根据会员卡号 查询操作记录
function queryMemberRecord() {
    layer.open({
        type: 2,
        title: '操作记录 - 用户编号: ' + onclickrow.usercode + ' ,姓名: ' + onclickrow.username + ' ,卡号: ' + onclickrow.memberno,
        shadeClose: true,
        shade: 0.6,
        area: ['85%', '85%'],
        content: contextPath + '/KQDS_MemberAct/toMemberRecord.act',
    });
}
</script>
</html>
