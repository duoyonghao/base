<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
	//获取是否开启免密支付
	String HYK_BINDING = SysParaUtil.getSysValueByName(request, SysParaUtil.HYK_BINDING);
	if (HYK_BINDING == null) {
		HYK_BINDING = "0"; //0 不绑 1 绑定
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>会员中心_会员查询</title>
<!--该页面已优化  鲍3-26  -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
</head>
<style type="text/css">
/* 主页图标  */
#container{padding:5px 5px 0 5px;}
.hc-icon{display: inline-block;background: url("../img/icon.png") no-repeat;}
.icon20{width:20px;height: 20px;}
.home-icon{background-position:-10px -15px;} /*主页*/
/* 表格下方按钮样式  */

.buttonBar .aBtn_big{display:inline-block;padding: 0 15px;height: 28px;border: solid 1px #0e7cc9;color: #0e7cc9;border-radius: 15px;line-height: 28px;width: 80px;text-align: center;}
.buttonBar .aBtn_big:hover{color:#fff;background: #0e7cc9;}
.buttonBar .aBtn_big2{display:inline-block;padding: 0 5px;height: 28px;border: solid 1px #0e7cc9;color: #0e7cc9;border-radius: 15px;line-height: 28px;width: 80px;text-align: center;}
.buttonBar .aBtn_big2:hover{color:#fff;background: #0e7cc9;}
.buttonBar .aBtn_big3{display:inline-block;padding: 5 15px;height: 28px;border: solid 1px #0e7cc9;color: #0e7cc9;border-radius: 15px;line-height: 28px;width: 88px;text-align: center;}
.buttonBar .aBtn_big3:hover{color:#fff;background: #0e7cc9;}
.buttonBar .aBtn_big4{display:inline-block;padding: 5 15px;height: 28px;border: solid 1px #0e7cc9;color: #0e7cc9;border-radius: 15px;line-height: 28px;width: 92px;text-align: center;}
.buttonBar .aBtn_big4:hover{color:#fff;background: #0e7cc9;}

.buttonBar .aBtn_big5{display:inline-block;padding: 5 15px;height: 28px;border: solid 1px #0e7cc9;color: #0e7cc9;border-radius: 15px;line-height: 28px;width: 108px;text-align: center;}
.buttonBar .aBtn_big5:hover{color:#fff;background: #0e7cc9;}

.buttonBar{margin: 0 0 0 10px;}
.buttonBar .aBtn{display:inline-block;padding: 0 15px;height: 28px;border: solid 1px #0e7cc9;color: #0e7cc9;border-radius: 15px;line-height: 28px;width: 60px;text-align: center;}
.buttonBar  .aBtn:hover{color:#fff;background: #0e7cc9;}

.buttonBar .text{color: #0e7cc9;margin-right: 10px;}
.addWrap .buttonBar{text-align: right;}

/* 表格下方按钮样式结束  */
/*底部搜索日历 */
.kv > label{float:left;color: #666;width: 60px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;text-align: right;font-weight: normal;line-height: 28px;}
.kv .kv-v{overflow:hidden;color: #333;min-height: 28px; line-height: 28px;display:inline;} /* display:inline; add by liujs */
input[type=text],.kv .kv-v input[type=text]{padding:0 10px;width:212px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}
.kv .kv-v input[type=text]:focus,
.kv .kv-v select:focus{border-color: #0d7bc8;}
.select,.kv .kv-v select{height: 28px;width:212px;border: solid 1px #e5e5e5;border-radius: 3px;}
.kv .kv-v input[type=radio]{vertical-align: -3px;*vertical-align: 0px; margin-right: 3px;}
/*表格*/
.tableBox{overflow: hidden;}
.fixed-table-header{margin-right:0!important;border-bottom: none; color: #FFF;font-weight:normal;background: #00a6c0;}
.table-striped > tbody > tr:nth-child(odd){background: #eef9fe;}
.table-striped > tbody > tr{background: #FFF;}
.table > thead > tr > th, .table > tbody > tr > th, .table > tfoot > tr > th, .table > thead > tr > td, .table > tbody > tr > td, .table > tfoot > tr > td{border-top: none;}
.table-condensed > thead > tr > th, .table-condensed > tbody > tr > th, .table-condensed > tfoot > tr > th, .table-condensed > thead > tr > td, .table-condensed > tbody > tr > td, .table-condensed > tfoot > tr > td{} /* padding: 0px 0px; */
/* 底部搜查框  */
.searchWrap{border:1px solid #ddd;}
.listBd{border:1px solid #ddd;border-top:none;border-radius:5px;}
.listWrap{margin-bottom: 10px;}
.listWrap .listHd{
 	box-sizing: border-box;
    color: #373737;
    font-family: "Microsoft YaHei";
    height: 46px;
    position: relative;
}
.listWrap .listHd .title{
	font-size: 20px;
    height: 20px;
    line-height: 20px;
    float: left;
    display: block;
    margin-top: 16px;
}
.listWrap .listHd .home-icon{margin-right:6px;vertical-align: -3px; *vertical-align: 0;}
.listWrap .listBd{background: #fff;overflow:hidden;}
.listWrap  .formBox {margin: 10px 0;}
.listWrap  .formBox label{float:left;font-weight: normal;margin-right: 10px;}
.listWrap  .formBox label input[type=checkbox]{vertical-align: -3px; *vertical-align: 0;margin-right: 3px;}
.listWrap  .formBox .kv{float: left;}
.listWrap  .formBox .kv textarea{height: 30px;min-height: 0;width: 230px;}
/* 按钮  */
.searchWrap{position: relative;padding: 40px 10px 8px 10px;}
.searchWrap .cornerBox{
	position: absolute; left:0;top:0;
	height:30px;width:118px;line-height:30px;
	text-align:center;font-size:14px;color:#fff;
	background: #00A6C0 ;
}
.searchWrap .text{position: absolute; left:140px;top:9px;color: #0e7ec8;}
.searchWrap .text i{margin: 0 2px;}
/*wl————————————————————————————start 修改了按钮的样式 按钮间距  */
.searchWrap .btnBar{text-align:right;}
.searchWrap .btnBar > .aBtn{
	display:inline-block;padding:0;
	height:28px;border: solid 1px #0e7cc9;
	color: #0e7cc9;border-radius:16px;width:90px;
	line-height: 28px;text-align:center; margin-left:5px;
	}
.searchWrap .btnBar > .aBtn:hover{color:#fff;background: #0e7cc9;}
/*wl————————————————————————————end  */
.formBox{overflow: hidden;}
/*wl————样式修改start 修改输入框的高度 label与控件间的间距*/
/* .searchWrap .kv{float: left; height:33px; margin-right:15px;}
.searchWrap .kv > label{margin-right: 10px;}
.searchWrap .kv input[type=text],.searchWrap .kv select{width:120px;height: 24px; line-height: 24px;box-sizing: border-box; }
.searchWrap .kv input[type=text].searchInput{width: 390px;}
.searchWrap .orangeFont{margin-left:10px;color: #ff6100;line-height: 20px;} */
.searchWrap .kv{float: left; height:33px; margin-right:20px;}
.searchWrap .kv > label{margin-right: 5px;}
.searchWrap .kv input[type=text],.searchWrap .kv select{width:120px;height: 24px; line-height: 24px;box-sizing: border-box; }
.searchWrap .kv input[type=text].searchInput{width: 390px;}
.searchWrap .orangeFont{margin-left:5px;color: #ff6100;line-height: 20px;font-size:12px;}
table td span.time{display:inline-block;width: 140px;white-space: nowrap;overflow: hidden;text-overflow:ellipsis;}
table td span.name{display:inline-block;width: 80px;white-space: nowrap;overflow: hidden;text-overflow:ellipsis;}

.totalText{  /*表格下面的三个小计*/
	font-size:14px;
	margin:3px 0;
	overflow:hidden;
}
.totalText span{
	display:block;
	float:left;
	margin-right:13px;
}
html,body{
 font-size:13px;
}
.fixed-table-container thead th .sortable{
	padding-right:16px;
}
a:hover{
	text-decoration:none;
}
.main{
	margin-left:20px;
}
/*wl————样式修改end*/
</style>


<body>

<div class="main">
    <div class="listWrap">
        <div class="listHd">
        	<span class="title">会员列表</span>
       	</div>
        <div class="listBd">
            <div class="tableBox">
                <table id="table" class="table-striped table-condensed table-bordered" data-height="450" >
                </table>
            </div>
            
            <div class="totalText" style="display:inline-block;vertical-align: middle;width:100%;padding-left:15px;">
				<span style="color:#00a6c0;">&nbsp;充值余额小计:<lable id="tdmoney">0</lable></span>
				<span style="color:#00a6c0;">&nbsp;赠送余额小计:<lable id="tdgivemoney">0</lable></span>
				<span style="color:#00a6c0;">&nbsp;余额小计:<lable id="tdtotal">0</lable></span>
			</div>
            
     		<div id="tableBarDiv" class="buttonBar" style="display:block;padding-bottom:10px;vertical-align: middle;">
           	</div>
        </div>
    </div>
    <div class="listWrap" style="margin:0;">
    <!--查询条件-->
    <div class="searchModule">
    	<header>
    		<span>查询条件</span>
    		<i>共有记录 <span id="total"></span> 条</i>
    	</header>
    	<section>
    		<ul class="conditions">
    			<li>
    				<span>所属门诊</span>
    				 <select id="organization" name="organization"></select>
    			</li>
    			<li>
    				<span>会员类型</span>
   				    <select name="memberlevel" class="patients-source select2 dict" tig="HYKFL" id="memberlevel">
	                </select>
    			</li>
    			<li class="unitsDate">
    				<span>办卡时间</span>
   				    <input type="text" placeholder="开始日期" id="starttime" >
    			</li>
    			<li class="unitsDate">
    				<span>到</span>
   				    <input type="text"  placeholder="结束日期" id="endtime">
    			</li>
    			<li>
    				<span>模糊查询</span>
   				    <input type="text" id="queryInput" class="searchInput" placeholder="请输入用户编号/姓名/会员卡号/手机号码" style="font-size:12px;">
    			</li>
    		</ul>
    	</section>
    	<div class="btnBar" style="text-align:left;">
            <a href="javascript:void(0);" class="kqdsCommonBtn" id="clear">清空</a>
            <a href="javascript:void(0);" class="kqdsSearchBtn" id="query" onclick = "query();">查询</a>
        </div>
    </div>
</div>


<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/btnfuc.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/member/kqds_member_binding.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var HYK_BINDING = '<%=HYK_BINDING%>';
var pageurl = '<%=contextPath%>/KQDS_MemberAct/selectList.act'; 
var onclickrowOobj = ""; //选中行对象
var nowday;
var xuhao = 1; //序号
var tdmoney = 0,
tdgivemoney = 0,
tdtotal = 0;
var menuid = "<%=menuid%>";
var isClick = true;

$(function() {
	initHosSelectListNoCheck('organization'); // 连锁门诊下拉框
    //获取当前页面所有按钮
    getButtonAllCurPage(menuid);
    initDictSelectByClass(); // 会员类型
    //当前日期
    nowday = getNowFormatDate();
    
    //查询条件 创建时间
    $("#starttime,#endtime").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "top-right"
    });
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });

    initTable(pageurl); // --------------------------- 加载表格
    
    //计算主体的宽度
    setHeight();
   
    $(window).resize(function() {
        setHeight();
        /*产生滚动条时，表头做的处理*/
        setTableHeaderWidth(".tableBox");
    });
});

//清空
$('#clear').on('click',function() {
    //当前日期
    nowday = getNowFormatDate();
    $("#starttime").val("");  //---默认只查当天的
    $("#endtime").val("");
    
    $("#queryInput").val("");
    $("#memberlevel").val("");
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>");
});


//查询
function query() {
    xuhao = 1; //初始化序号
    tdmoney = 0,
    tdgivemoney = 0,
    tdtotal = 0; //初始化底部计算的小计
    var queryInput = $("#queryInput").val();
    var starttime = $("#starttime").val();
    var endtime = $("#endtime").val();
    var memberlevel = $("#memberlevel").val();

   /*  if (queryInput == "" && starttime == "" && endtime == "" && memberlevel == "") {
        layer.alert("请选择查询条件" );
        return false;
    } */

    if (starttime != "" && endtime != "") {
        if (checkdate(starttime, endtime) == 0) {  // 0 表示 starttime 大于 endtime
        	layer.alert("结束日期不能小于开始日期" );
            return false;
        }
    }
	$("#query").attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
	$("#query").text("查询中");
    refresh();

}
function refresh(){
	 $('#table').bootstrapTable('refresh', {
	        'url': pageurl,
	 });
}
/* function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	sortName:this.sortName,//排序名称
        sortOrder:this.sortOrder,//排序类型
        limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1,
    	pagenum:1,
   		organization: $('#organization').val(),
   		queryInput: $('#queryInput').val(),
   		starttime: $('#starttime').val(),
   		endtime: $('#endtime').val(),
   		memberlevel: $('#memberlevel').val()
    };
    return temp;
} */

function queryParamsB(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	sortName:this.sortName,//排序名称
        sortOrder:this.sortOrder,//排序类型
        limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1,
    	pagenum:1,
   		organization: $('#organization').val(),
   		queryInput: $('#queryInput').val(),
   		starttime: $('#starttime').val(),
   		endtime: $('#endtime').val(),
   		memberlevel: $('#memberlevel').val()
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
		var fieldArr=[];
		var fieldnameArr=[];
		$('#table thead tr th').each(function () {
			var field = $(this).attr("data-field");
			if(field!=""){
				fieldArr.push(field);//获取字段
				fieldnameArr.push($(this).children()[0].innerText);//获取字段中文
			}
		});
		var queryInput = $("#queryInput").val();
		var starttime = $("#starttime").val();
		var endtime = $("#endtime").val();
		var memberlevel = $("#memberlevel").val();
		var organization = $("#organization").val();

		var url =  pageurl+"?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr)
				+ "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&queryInput=" + queryInput
				+ "&starttime=" + starttime + "&endtime=" + endtime + "&memberlevel=" + memberlevel
				+ "&organization=" + organization;


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
				a.download = "手术查询";
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

//加载表格
function initTable() {
	
    //加载表格
    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParamsB,
        cache: false,
        striped: true,
        singleSelect: false,
        pagination: true,//是否显示分页（*）
        pageSize: 30,
        pageList : [15, 20, 25,30],//可以选择每页大小
        sidePagination: "server",//后端分页 
        paginationShowPageGo: true,
        onDblClickCell: function(field, value, row, td) {
            //双击事件 调用提交回访结果方法 
            goToUserCenterPage(row.usercode);
        },
        onLoadSuccess: function(data) { //加载成功时执行
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","auto").css("pointer-events","auto");
				$("#query").text("查询");
			}
        	//console.log("会员管理"+JSON.stringify(data));
            var allTableData = $('#table').bootstrapTable('getData');
            $("#total").html(data.total);
            $("#tdmoney").html(Number(data.mon[0].money).toFixed(2));
            $("#tdgivemoney").html(Number(data.mon[0].givemoney).toFixed(2));
            $("#tdtotal").html((Number(data.mon[0].money) + Number(data.mon[0].givemoney)).toFixed(2));
            
            /*表格加载好后，产生滚动条时，表头做的处理*/
            setTableHeaderWidth(".tableBox");
        },
        columns: [
        {
            title : '序号',
            field: ' ',
            align: "center",
            editable: true,
            formatter: function (value, row, index) {
             /* return index + 1; */
             	var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
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
		{title: '第一咨询',field: 'askpersonname',align: 'center',sortable: true,
			formatter:function(value,row,index){  
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
            title: 'IC卡号',
            field: 'icno',
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
        	formatter:function(value){
        		return '<span>'+value+'</span>';
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
            title: '折扣截止日期',
            field: 'discountdate',
            align: 'center',
            sortable: true,
        	formatter:function(value){
        		return '<span>'+value+'</span>';
        	}
        },
        {
            title: '充值余额',
            field: 'money',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    //tdmoney = tdmoney + Number(value);
                    return '<span>'+Number(value).toFixed(2)+'</span>';
                } else {
                    return "<span>0.0</span>";
                }
            }
        },
        {
            title: '赠送余额',
            field: 'givemoney',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    //tdgivemoney = tdgivemoney + Number(value);
                    return '<span>'+Number(value).toFixed(2)+'</span>';
                } else {
                    return "<span>0.0</span>";
                }
            }
        },
        {
            title: '余额小计',
            field: 'totalmoney',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	
                //var totalm = Number(row.money) + Number(row.givemoney);
                //tdtotal = Number(tdtotal) + totalm;
                //console.log(value);
                return '<span>'+Number(value).toFixed(2)+'</span>';
            }
        },
        {
        	title: '患者来源',
        	field: 'devchannel',
        	align: 'center',
        	sortable: true,
			formatter:function(value,row,index){  
                return '<span class="name">' + value + '</span>';
        	}
		},
		{
			title: '来源子分类',
			field: 'nexttype',
			align: 'center',
			sortable: true,
			formatter:function(value,row,index){  
                return '<span class="source">' + value + '</span>';
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
            title: '办卡时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '备注',
            field: 'remark',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="remark" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
    });
}

//点击办卡
function kaika() {
    var requrl = contextPath + '/KQDS_MemberAct/toMemberAdd.act?usercode='+onclickrowOobj.usercode+'&username='+onclickrowOobj.username;
    static_btnfucDeal('hykbl', requrl); // 调用btnfuc.js中的方法
}

//点击充值
function chongzhi() {
    if (onclickrowOobj == null || onclickrowOobj == "") {
        layer.alert('请选择需要充值的会员卡' );
    } else {
        layer.open({
            type: 2,
            title: '充值',
            shadeClose: false,
            shade: 0.6,
            area: ['800px', '300px'],
            content: contextPath + '/KQDS_MemberAct/toMemberChongzhi.act?seqId=' + onclickrowOobj.seqId 
        });
    }
}

//点击转赠
function zhuanzeng() {
    layer.open({
        type: 2,
        title: '转赠',
        shadeClose: false,
        shade: 0.6,
        area: ['900px', '340px'],
        content: contextPath + '/KQDS_MemberAct/toMemberZhuanzeng.act' 
    });
}

//点击赠送
function zengsong() {
    if (onclickrowOobj == null || onclickrowOobj == "") {
        layer.alert('请选择会员卡' );
    } else {
        layer.open({
            type: 2,
            title: '赠送 - 患者编号: ' + onclickrowOobj.usercode + ' ,姓名: ' + getNameByusercodesTB(onclickrowOobj.usercode)[0].username + ' ,卡号: ' + onclickrowOobj.memberno,
            shadeClose: false,
            shade: 0.6,
            area: ['798px', '450px'],
            content: contextPath + '/KQDS_MemberAct/toMemberZengSong.act?memberno=' + onclickrowOobj.memberno + '&usercode=' + onclickrowOobj.usercode + '&username=' + onclickrowOobj.username 
        });
    }
}

//查看选中的会员卡 操作记录
function jilu() {
    if (onclickrowOobj == null || onclickrowOobj == "") {
        layer.alert('请选择会员卡' );
    } else {
        layer.open({
            type: 2,
            title: '操作记录 - 用户编号: ' + onclickrowOobj.usercode + ' ,姓名: ' + getNameByusercodesTB(onclickrowOobj.usercode)[0].username + ' ,卡号: ' + onclickrowOobj.memberno,
            shadeClose: false,
            shade: 0.6,
            maxmin: true,
            area: ['98%', '95%'],
            content: contextPath + '/KQDS_MemberAct/toMemberRecord.act',
        });
    }
}

//查询开卡
function query_kaika() {
    layer.open({
        type: 2,
        title: '开卡查询',
        shadeClose: false,
        shade: 0.6,
        maxmin: true,
        area: ['98%', '95%'],
        content: contextPath + '/KQDS_MemberAct/toMemberOpencard.act?menuid='+menuid
    });
}

//查询充值
function query_chongzhi() {
    layer.open({
        type: 2,
        title: '充值查询',
        shadeClose: false,
        shade: 0.6,
        maxmin: true,
        area: ['98%', '95%'],
        content: contextPath + '/KQDS_MemberAct/toMemberRecharge.act?menuid='+menuid
    });
}

//查询退费
function query_tuifei() {
    layer.open({
        type: 2,
        title: '退费查询',
        shadeClose: false,
        shade: 0.6,
        maxmin: true,
        area: ['98%', '95%'],
        content: contextPath + '/KQDS_MemberAct/toMemberTuifei.act?menuid='+menuid
    });
}

//查询缴费
function query_jiaofei() {
    layer.open({
        type: 2,
        title: '缴费查询',
        shadeClose: false,
        shade: 0.6,
        maxmin: true,
        area: ['98%', '95%'],
        content: contextPath + '/KQDS_MemberAct/toMemberPay.act?menuid='+menuid
    });
}

//会员卡查询
function query_tongji() {
    layer.open({
        type: 2,
        title: '会员卡查询',
        shadeClose: false,
        shade: 0.6,
        maxmin: true,
        area: ['98%', '95%'],
        content: contextPath + '/KQDS_MemberAct/toMemberQuery.act?menuid='+menuid
    });
}

//赠送记录
function zengsong_giverecord() {
    layer.open({
        type: 2,
        title: '赠送记录',
        shadeClose: false,
        shade: 0.6,
        maxmin: true,
        area: ['98%', '95%'],
        content: contextPath + '/KQDS_MemberAct/toZengsong_Give_Record.act?memberno=' + onclickrowOobj.memberno+'&menuid='+menuid
    });
}

//使用赠送记录
function zengsong_userecord() {
    layer.open({
        type: 2,
        title: '使用赠送记录',
        shadeClose: false,
        shade: 0.6,
        maxmin: true,
        area: ['98%', '95%'],
        content: contextPath + '/KQDS_MemberAct/toZengsong_User_Record.act?memberno=' + onclickrowOobj.memberno+'&menuid='+menuid
    });
}

//赠送项目查询
function zengsong_query() {
    layer.open({
        type: 2,
        title: '赠送项目查询',
        shadeClose: false,
        shade: 0.6,
        maxmin: true,
        area: ['98%', '95%'],
        content: contextPath + '/KQDS_MemberAct/toZengsongQuery.act?menuid='+menuid
    });
}

//转赠记录查询
function zhuanzeng_query() {
    layer.open({
        type: 2,
        title: '转赠查询',
        shadeClose: false,
        shade: 0.6,
        maxmin: true,
        area: ['98%', '95%'],
        content: contextPath + '/KQDS_MemberAct/toZhuanzengQuery.act?menuid='+menuid
    });
}
function binding(){
	if (onclickrowOobj == null || onclickrowOobj == "") {
        layer.alert('请选择会员卡' );
        return false;
    }
    layer.open({
        type: 2,
        title: onclickrowOobj.username+'-绑定IC卡',
        shadeClose: false,
        shade: 0.6,
        maxmin: true,
        area: ['320px', '280px'],
        content: contextPath + '/KQDS_MemberAct/toMemberBangding.act?seqId='+onclickrowOobj.seqId
    });
}
function xgkh(){
	if (onclickrowOobj == null || onclickrowOobj == "") {
        layer.alert('请选择会员卡' );
        return false;
    }
    layer.open({
        type: 2,
        title: onclickrowOobj.username+'-修改会员卡',
        shadeClose: false,
        shade: 0.6,
        maxmin: true,
        area: ['800px', '420px'],
        content: contextPath + '/KQDS_MemberAct/toMemberEdit.act?seqId='+onclickrowOobj.seqId
    });
}
function editpass(){
	if (onclickrowOobj == null || onclickrowOobj == "") {
        layer.alert('请选择会员卡' );
        return false;
    }
	if (onclickrowOobj.icno == null || onclickrowOobj.icno == "") {
        layer.alert('请先绑定IC卡' );
        return false;
    }
    layer.open({
        type: 2,
        title: onclickrowOobj.username+'-修改密码',
        shadeClose: false,
        shade: 0.6,
        maxmin: true,
        area: ['50%', '350px'],
        content: contextPath + '/KQDS_MemberAct/toMemberEditpassIndex.act?seqId='+onclickrowOobj.seqId
    });
}
//按钮权限
function getButtonPower() {
    var menubutton = "";
    var menubutton1 = "";
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "kaika") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="kaika" style="" onclick="kaika()">开卡</a>';
        } else if (listbutton[i].qxName == "chongzhi") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="chongzhi" onclick="chongzhi()">充值</a>';
        } else if (listbutton[i].qxName == "binding" && HYK_BINDING == "1") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="binding()">绑卡/换卡</a>';
        } else if (listbutton[i].qxName == "xgkh") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="xgkh()">修改会员卡</a>';
        } else if (listbutton[i].qxName == "editpass" && HYK_BINDING == "1") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="editpass()">修改密码</a>';
        } else if (listbutton[i].qxName == "zhuanzeng") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="zhuanzeng" onclick="zhuanzeng()">转赠</a>';
        } else if (listbutton[i].qxName == "query_kaika") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="query_kaika" onclick="query_kaika()">开卡查询</a>';
        } else if (listbutton[i].qxName == "query_chongzhi") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="query_chongzhi" onclick="query_chongzhi()">充值查询</a>';
        } else if (listbutton[i].qxName == "query_tuifei") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="query_tuifei" onclick="query_tuifei()">退费查询</a>';
        } else if (listbutton[i].qxName == "query_jiaofei") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="query_jiaofei" onclick="query_jiaofei()">缴费查询</a>';
        } else if (listbutton[i].qxName == "zhuanzeng_query") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="zhuanzeng_query" onclick="zhuanzeng_query()">转赠查询</a>';
        } else if (listbutton[i].qxName == "jilu") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="jilu" onclick="jilu()">操作记录</a>';
        } else if (listbutton[i].qxName == "query_tongji") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="query_tongji" onclick="query_tongji()">会员卡查询</a>';
        } else if (listbutton[i].qxName == "zengsong") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="zengsong" onclick="zengsong()">赠送项目</a>';
        } else if (listbutton[i].qxName == "zengsong_giverecord") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="zengsong_giverecord" onclick="zengsong_giverecord()">赠送记录</a>';
        } else if (listbutton[i].qxName == "zengsong_userecord") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="zengsong_userecord" onclick="zengsong_userecord()" style="margin-top:3px;">使用赠送记录</a>';
        } else if (listbutton[i].qxName == "zengsong_query") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="zengsong_query" onclick="zengsong_query()" style="margin-top:3px;">赠送项目查询</a>';
        } else if (listbutton[i].qxName == "hyzx_scbb") {
        	menubutton1 += ' <a href="javascript:void(0);" class="kqdsCommonBtn"  onclick="exportTable()">生成报表</a> ';        }
    }
    $("#query").before(menubutton1);
    $("#tableBarDiv").html(menubutton);
}

//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height();
    var serachH = $('.searchModule').outerHeight();
    // $('.extraBar .extraBd').height(baseHeight - 65);
    $('#table').bootstrapTable('resetView', {
        height: baseHeight - serachH - 130
    }); // 28 是totalCount的高度  -5 是因为统计div和按钮div放成两行，不减5会出现纵向滚动条
}

</script>
</body>
</html>
