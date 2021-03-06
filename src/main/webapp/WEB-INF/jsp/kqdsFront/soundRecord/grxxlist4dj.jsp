<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<meta charset="utf-8" />
<title>录音中心</title>

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

.searchWrap{
	overflow:hidden;
	padding: 35px 10px 5px 10px;
}
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
.main{
	padding:5px 10px 0;
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
<body>
	<div id="container">
		<div class="main">
			<div class="listWrap">
				<!-- <div class="listHd">
					<span class="hc-icon icon20 home-icon"></span>录音文件列表
				</div> -->
				<div class="listBd">
					<div class="tableBox">
						<table id="table"
							class="table-striped table-condensed table-bordered"></table>
					</div>
					<div id="tableBarDiv" class="buttonBar">
					</div>
				</div>
			</div>
			<!--查询条件-->
			<div class="searchWrap">
				<div class="cornerBox">查询条件</div>
				<div class="formBox">
					<table class="kqds_table">
				    		<tr>
				                <td style="text-align:right;">录音日期：</td>
				    			<td style="text-align:left;"> 
				    					<span class="unitsDate">
				    						<input type="text"  id="startdate" class="datetime"/> -
				    						<input type="text"  id="enddate" class="datetime"/> 
				                        </span>
				                </td>
			                </tr>
				    	</table>
				    	
				    	<div class="btnBar">
				    		<a href="javascript:void(0);" class="kqdsCommonBtn" id="clear">清空</a> 
							<a href="javascript:void(0);" style="" class="kqdsSearchBtn" id="query">查询</a> 
						</div>
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
var contextPath = '<%=contextPath%>';
var $table = $('#table');
var onclickrowobj = window.parent.onclickrowOobj; //父页面传值;
var usercode = null;
var pageurl = '<%=contextPath%>/KQDS_SoundRecordAct4DJ/selectList4Grxx.act';
$(function() {
	
	usercode = onclickrowobj.usercode;
	
	if(!usercode){
		layer.alert('请选择患者！' );
        return false;
	}

	var nowday = getNowFormatDate();
	$("#startdate").val(nowday);
	$("#enddate").val(nowday);
	
    inittable(pageurl);
    
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

function play(weburl) {
    var index = layer.open({
        type: 2,
        title: '播放录音',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['510px', '400px'],
        content: weburl
    });
}

//查询
$('#query').on('click',
function() {
    //加载表格
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
});
//清空
$('#clear').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
   		offset: params.offset,
        limit: params.limit,
        startdate: $("#startdate").val(),
        enddate: $("#enddate").val(),
        usercode: usercode
    };
    return temp;
}
function inittable(pageurl) {
    //加载表格
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        singleSelect: false,
        cache: false,
        striped: true,
        pagination: true,
        sidePagination: "server",
        onLoadSuccess: function(data) { //加载成功时执行
        	setHeight();
        },
        columns: [{
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                var menubutton = "";
                if(row.talklong != "0"){
                	menubutton += "<span class='common' style='width:50px;'>";
                    menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="play(\'' + row.url + '\')" title="' + row.url + '">播放</a> ';
                    menubutton += "</span>"
                }
                return menubutton;
            }
        },
        {
            title: '通道号',
            field: 'channel',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            visible: false,
        },
        {
            title: '本地号码',
            field: 'localcode',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '本地用户',
            field: 'localname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '所在部门',
            field: 'localdepartment',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '对方号码',
            field: 'remotecode',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '呼入/呼出',
            field: 'calldir',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                var valueS = "";
                if(value == "0"){
                	valueS =  "<span style='color:red;'>呼入</span>"
                }
                if(value == "1"){
                	valueS =  "呼出"
                }
                return valueS;
            }
        },
        {
            title: '通话时间',
            field: 'talktime',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true
        },
        {
            title: '通话时长(秒)',
            field: 'talklong',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                var valueS = "";
                if(value == "0"){
                	valueS =  "<span style='color:red;'>未接听</span>"
                }else{
                	valueS = value;
                }
                return valueS;
            }
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            visible: false,
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            visible: false,
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

//刷新整个页面
function refresh() {
    window.location.reload();
}

//计算左侧表格高度保证一屏展示
function setHeight() {
    $("#table").bootstrapTable("resetView",{
    	height:$(window).outerHeight()-$(".searchWrap").outerHeight()-20
    })
    /* 框架table显示的高度 */
}
</script>
</html>