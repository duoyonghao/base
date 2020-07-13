<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);
	// sno 为加工单的主键 
	String sno = request.getParameter("sno");
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
	//从哪里点击过来的
	String type = request.getParameter("type");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>加工中心_加工明细</title>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
</head>
<style type="text/css">
/*工作量表格 ，单独写*/
#gongzuol{border:solid 1px #c0c0c0;background: #f5f5f5;}
#gongzuol{margin-bottom: 15px;}
#gongzuol .columnHd{padding:0 15px;border-bottom:solid 1px #0e7ec6;font-size:16px;color: #373737;font-family: "Microsoft YaHei";line-height:36px;}
 .buttonBar .aBtn_big {  /* 按钮为四个字的用这个样式 */
	display: inline-block;
	padding: 0 15px;
	height: 28px;
	border: solid 1px #0e7cc9;
	color: #0e7cc9;
	border-radius: 15px;
	line-height: 28px;
	width: 88px;
	text-align: center;
}

.buttonBar  .aBtn_big:hover {
	color: #fff;
	background: #0e7cc9;
}
.kqds_table{
	width:90%;
	align:center;
	/* margin-left: auto; */
	margin-right: auto;
}
	
.kqds_table  td { 
	color: #666;
	padding: 3px 5px 5px 5px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	font-weight: normal;
	line-height: 28px;
}
	
.kqds_table  select { 
	height: 28px;
	width:120px;
	border: solid 1px #e5e5e5;
	border-radius: 3px;
}
input[type=text],.kv .kv-v input[type=text]{padding:0 10px;width:120px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}

.searchWrap .btnBar{		/*按钮组右浮动  */
	position:absolute;
	right:10px;
	bottom:10px;
	width:auto;
}
.searchWrap .btnBar .aBtn{
	float:left;
	margin:0 3px;
	display:block;
	color:#0E7CC9;
	background:#fff;
	height:28px;
	padding:0 15px;
	line-height:28px;
	border-radius:16px;
	text-align:center;
	border:1px solid #0E7CC9;
	font-size:13px;
}
.searchWrap .btnGroups .aBtn:hover{
	cursor:pointer;
	color:#fff;
	background:#0E7CC9;
}
.buttonBar{
	margin-top:17px;
}
.searchModule>section>ul.conditions>li>span{
	width:70px;
	text-align:right;
}
.searchModule>section>ul.conditions>li{
	padding: 3px 0;
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
.bootstrap-table .fixed-table-header table th{
	border:none;
}
</style>
<body>
	<div id="container">
		<div class="main" style="margin-left:20px;">
			<div class="listWrap">
				<div class="listHd">
					<span class="title">加工明细列表</span>
				</div>
				<div class="listBd">
					<div class="tableBox">
						<table id="table"
							class="table-striped table-condensed table-bordered"></table>
					</div>
					<!-- <div id="tableBarDiv" class="buttonBar">
					</div> -->
				</div>
			</div>
			<!--查询条件-->
			<div class="searchModule" style="margin-top:15px;">
		    	<header>
		    		<span>查询条件</span>
		    	</header>
		    	<section>
		    		<ul class="conditions">
		    			<li class="unitsDate">
		    				<span>创建时间</span>
		    				<input type="text" placeholder="开始日期" id="createtime1" class="datetime"/>
		    			
		    				<span>到</span>
		    				<input type="text" placeholder="结束日期" id="createtime2" class="datetime"/>
		    			</li>
		    			<li class="unitsDate">
		    				<span>发件时间</span>
		    				<input type="text" placeholder="开始日期" id="fjtime1" class="datetime"/>
		    			
		    				<span>到</span>
		    				<input type="text" placeholder="结束日期" id="fjtime2" class="datetime"/>
		    			</li>
		    			<li class="unitsDate">
		    				<span>回件时间</span>
		    				<input type="text" placeholder="开始日期" id="hjtime1" class="datetime"/>
		    			
		    				<span>到</span>
		    				<input type="text" placeholder="结束日期" id="hjtime2" class="datetime"/>
		    			</li>
		    			<li class="unitsDate">
		    				<span>戴走时间</span>
		    				<input type="text" placeholder="开始日期" id="dztime1" class="datetime"/>
		    			
		    				<span>到</span>
		    				<input type="text" placeholder="结束日期" id="dztime2" class="datetime"/>
		    			</li>
		    			
		    			<li class="unitsDate">
		    				<span>返工时间</span>
		    				<input type="text" placeholder="开始日期" id="fgtime1" class="datetime"/>
		    			
		    				<span>到</span>
		    				<input type="text" placeholder="结束日期" id="fgtime2" class="datetime"/>
		    			</li>
		    			
		    			
		    			<li>
		    				<span>状态</span>
		    				<select name="status" id="status">
								<option value="">请选择</option>
								<option value="0">未发件</option>
								<option value="1">已发件</option>
								<option value="2">回件</option>
								<option value="3">戴走</option>
								<option value="4">返工</option>
								<option value="5">作废</option>
							</select>
		    			</li>
		    			
		    			<li>
		    				<span>加工厂</span>
		    				<select name="unit" id="unit" ></select>
		    			</li>
		    			<li>
		    				<span>模糊查询</span>
		    				<input type="text" id="queryInput" class="searchInput" style="font-size:12px;" placeholder="请输入单号/患者姓名、编号、手机/加工单位" >
		    			</li>
		    		</ul>
		    	</section>
		    	<div class="btnBar" style="text-align:left;">
					<a href="javascript:void(0);" class="kqdsCommonBtn" id="clear">清空</a> 
					<a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
		        </div>
	   		</div>
			
		</div>
	</div>
	
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
</body>
<script type="text/javascript">
var type = '<%=type%>';
var sno = '<%=sno%>';
var contextPath = '<%=contextPath%>';
var perseqId = "<%=person.getSeqId()%>";
var personrole = "<%=person.getUserPriv()%>";
var personroleother = "<%=person.getUserPrivOther()%>";
var canHj = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag3_canHj, request)%>';
var $table = $('#table');
var pageurl = '<%=contextPath%>/KQDS_OUTPROCESSING_SHEET_DETAILAct/selectList.act';
var onclickrow = ""; //选中行对象
var nowday = "";
var num = 0; //页面加载使用 第一次加载查询条件为当天或者未发件的加工单
var menuid = "<%=menuid%>";
var qxnameArr = ['jgmx_scbb'];
var func = ['exportTable'];
$(function() {

    //获取当前页面所有按钮
    getButtonPowerByPriv(qxnameArr,func,menuid);
    //加工厂
    initFactorySelect('unit','','<%=ChainUtil.getCurrentOrganization(request)%>');
    var pageurlsno = pageurl;
    if (sno != "" && sno != null && sno != "undefined" && sno != "null") {
        pageurlsno = pageurlsno + "?sno=" + sno;
        //加载表格
        inittable(pageurlsno);
        //隐藏查询条件
        $('.searchWrap').hide();
    } else {
        inittable(pageurlsno + '?num=' + num); //加载表格
        num++;
    }
    //查询条件 创建时间
    $(".datetime").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "top-right"
    });
    //计算主体的宽度
    setHeight();
    $(window).resize(function() {
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });
});

//查询
$('#query').on('click',
function() {
    var queryInput = $("#queryInput").val();
    var status = $("#status").val(); //状态
    var fjtime1 = $("#fjtime1").val();
    var fjtime2 = $("#fjtime2").val();
    var hjtime1 = $("#hjtime1").val();
    var hjtime2 = $("#hjtime2").val();
    var dztime1 = $("#dztime1").val();
    var dztime2 = $("#dztime2").val();
    var fgtime1 = $("#fgtime1").val();
    var fgtime2 = $("#fgtime2").val();
    var createtime1 = $('#createtime1').val();
    var createtime2 = $('#createtime2').val();
    var unit = $('#unit').val();
    if (queryInput == "" && status == "" && fjtime1 == "" && fjtime2 == "" && hjtime1 == ""
    		&& hjtime2 == "" && dztime1 == "" && dztime2 == "" && fgtime1 == "" && fgtime2 == "" && createtime1 == "" && createtime2 == ""  && unit == "") {
        layer.alert('请选择查询条件' );
        return false;
    }
	$(this).attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
	$(this).text("查询中");
    //加载表格
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
});
//清空
$('#clear').on('click',
function() {
    $(".conditions :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});
//导出
function exportTable() {
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
    location.href = pageurl + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
}
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        status: $("#status").val(),
        fjtime1: $('#fjtime1').val(),
        fjtime2: $('#fjtime2').val(),
        hjtime1: $('#hjtime1').val(),
        hjtime2: $('#hjtime2').val(),
        dztime1: $('#dztime1').val(),
        dztime2: $('#dztime2').val(),
        fgtime1: $('#fgtime1').val(),
        fgtime2: $('#fgtime2').val(),
        createtime1: $('#createtime1').val(),
        createtime2: $('#createtime2').val(),
        unit: $('#unit').val(),
        queryInput: $('#queryInput').val()
    };
    return temp;
}
function inittable(pageurl) {
	
	/*wl----首次加载时 计算table高度  */
	
	var tableHeight=0;/* 计算table需要的高度 */
	/* 根据当前页面 计算出table需要的高度 */
	tableHeight=$(window).height()-$(".searchWrap").outerHeight()-$(".listHd").outerHeight()-40;
	/* 框架要使用改table */
	$(".tableBox").html("<table id='table' class='table-striped table-condensed table-bordered' data-height='"+tableHeight+"'></table>");
	
	/*wl----首次加载时 计算table高度————————————结束  */
	
    //加载表格
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        singleSelect: false,
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer").css("pointer-events","auto");
				$("#query").text("查询");
			}
            var tableList = $('#table').bootstrapTable('getData');
            $("#total").html(tableList.length);
            setHeight();
            /*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        },
        columns: [
      /*   {title: '门诊',field: 'organization',align: 'center',valign: 'middle',sortable: true,
        	 formatter: function(value, row, index) {
                 return "<span class='time'>" + value + "</span>";
             }
  		},  */
  		{
		    title : '序号',
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
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                var t = '<span>' + value + '</span>';
                return t;
            }
        },
        {
            title: '加工单号',
            field: 'outprocessingsheetno',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span>" + value + "</span>";
            }
        },
        {
            title: '加工厂',
            field: 'processingfactory',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span>" + value + "</span>";
            }
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            
            formatter: function(value, row, index) {
                return "<span title='" + value + "'>" + value + "</span>";
            }
        },
        {
            title: '患者姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span  class='name' title='" + value + "'>" + value + "</span>";
            }
        },
        {
            title: '加工项目',
            field: 'outprocessingname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span style='text-align:left;width:100%;'>" + value + "</span>";
            }
        },
        {
            title: '单位',
            field: 'utils',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span>" + value + "</span>";
            }
        },
        {
            title: '数量',
            field: 'nums',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                return "<span>" + value + "</span>";
            }
        },
        {
            title: '单价',
            field: 'unitprice',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                return "<span>" + value + "</span>";
            }
        },
        {
            title: '金额',
            field: 'subtotal',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                return "<span>" + value + "</span>";
            }
        },
        {
            title: '牙位',
            field: 'toothbit',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span>" + value + "</span>";
            }
        },
        {
            title: '色号',
            field: 'colorsize',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span>" + value + "</span>";
            }
        },
        {
            title: '制作要求',
            field: 'zzyq',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                var t = '<span class="remark">' + value + '</span>';
                return t;
            }
        },
        {
            title: '医生',
            field: 'doctorno',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span  class='name' >" + value + "</span>";
            }
        },
        {
            title: '类型',
            field: 'type',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span>" + value + "</span>";
            }
        },
        {
            title: '要求',
            field: 'yaoqiu',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                var str = '<span title="' + value + '" class="remark" >' + value + '</span>';
                return str;
            }
        },
        {
            title: '加工单状态',
            field: 'statusname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                var t = "";
                if (value == "未发件") {
                    t = '<span style="color:red;">' + value + '</span>';
                } else if (value == "戴走") {
                    t = '<span style="color:green;">' + value + '</span>';
                } else {
                    t = '<span>' + value + '</span>';
                }
                return t;
            }
        }
       ]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrow = $('#table').bootstrapTable('getData')[index];
    });
}
//按钮权限
function getButtonPower() {
    var menubutton = '<span class="text">共有记录<i class="total" id="total"></i>条</span>';
    var menubutton1 ='';
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "bianji") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="bianji" style="margin-left:5px;" onclick="bianji()">编辑</a>';
        } else if (listbutton[i].qxName == "xiangqing") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="xiangqing" onclick="xiangqing()">详情</a>';
        } else if (listbutton[i].qxName == "fajian") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="fajian" onclick="fajian()">发件</a>';
        } else if (listbutton[i].qxName == "huijian") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="huijian" onclick="huijian()">回件</a>';
        } else if (listbutton[i].qxName == "daizou") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="daizou" onclick="daizou()">戴走</a>';
        } else if (listbutton[i].qxName == "fangong") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="fangong" onclick="fangong()">返工</a>';
        } else if (listbutton[i].qxName == "zuofei") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="zuofei" onclick="zuofei()">作废</a>';
        } else if (listbutton[i].qxName == "kdxm") {
            menubutton += ' <a href="javascript:void(0);" class="aBtn_big" id="kdxm" onclick="kdxm()">开单项目</a>';
        } else if (listbutton[i].qxName == "jgzx_scbb") {
            menubutton1 += ' <a href="javascript:void(0);" class="kqdsCommonBtn"  onclick="exportTable()">生成报表</a>';
        }
    }
    $(".btnBar").prepend(menubutton1);
    $("#tableBarDiv").append(menubutton);
}

//刷新整个页面
function refresh() {
    window.location.reload();
}

//计算左侧表格高度保证一屏展示
function setHeight() {
	$("#table").bootstrapTable("resetView",{
		height:$(window).outerHeight()-$(".searchModule").outerHeight()-$(".listWrap .listHd").outerHeight()-20
	});
    /* var baseHeight = $(window).height() - 15,
    serachH = $('.searchWrap').outerHeight();
    $('.listWrap').height(baseHeight - serachH - 10); */
    /* $('.listWrap .listBd').height($('.listWrap').height() - $('.listWrap .listHd').height() - 20); */
    /* $(".fixed-table-container").height($('.listWrap .listBd').height() - $('.listWrap .columnBd .buttonBar').height() - 50); */
	/* $('.listWrap .listBd').height($('.listWrap').height() - $('.listWrap .listHd').height()); *//* 加工管理页面 这里没有按钮组 所以高度要重写 */
    
}
</script>
</html>