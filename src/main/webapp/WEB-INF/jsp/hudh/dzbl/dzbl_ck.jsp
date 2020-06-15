<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	
	YZPerson person = SessionUtil.getLoginPerson(request);
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
	String currPersonId = person.getSeqId();
	String perPriv = person.getUserPriv();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>病历查看</title><!-- 右侧个人中心  中心 病历图标 点击进入的 主页面  -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/font-awesome/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/datepicker/datepicker3.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/history_record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>

<!--引入CSS-->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/style.css">
<script type="text/javascript" src="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/upload/uploadutil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<style type="text/css">
.dzbl-context{
	margin-left:2%;
	width: 96%;
	text-align: center;
}
.bootstrap-table{
	margin-top:5px;
}
.topInfo-font{
	color:#FA6406;
}
.topInfo ul{
	width:100%;
	/* height: 30px; */
	display: flex;
    flex-direction: row;
}
.topInfo ul>li{
	width:24%;		
	margin-right:2%;
}
.topInfo ul>li select{
	/* width:60%; */
	float: left;
	max-width: 60%;
}
.topInfo ul>li font{
	float: left;
	line-height: 28px;
	margin-right:3%;
}
.kqdsSearchBtn{
	padding:0px;
	width:165%;
	text-align: center;
}
.layui-layer-setwin{
   display: none;
}

/* 搜索框select */ 
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
	.searchModule>section {
	    padding: 10px 10px;
	    border-bottom: 0px solid #ddd!important; 
	}
	.button-box {
    	height: 51px;
	}
</style>
</head>
<body>
    <div class="biliHistory">
<!--             <div class="tableHd">历史病历【双击查看详情】</div> -->
              <div class="button-box">
			    <!--查询条件  -->
				<div class="searchModule staticDivContent">
					 <section>
					    <ul class="conditions">
					    	<li>
					    		<span>时间</span>
					    		<input type="text" id="starttime" name="starttime" placeholder="开始日期" class="birthDate">
					    	</li>
					    	<li>
					    		<span>到</span>
					    		<input type="text" id="endtime" name="endtime" placeholder="结束日期" class="birthDate">
					    	</li>
					    	<li>
					    		<span>模糊查询</span>
					    		<input type="text" name="searchvalue" id="searchvalue" class="fuzzyQueryInp" placeholder="请输入种植类型/患者编号/患者姓名" style="font-size:12px;">
					    	</li>
					    	<li><a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清 空</a></li>
							<li><a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查 询</a></li>
					    </ul>
					  </section>
				  </div>
			</div>
            <table id="table" class="table-striped table-condensed table-bordered" data-height="600"></table>
    </div>
	
</body>
<script type="text/javascript">
var onclickrowOobj = "";
var perPriv = '<%=perPriv%>';//获取当前用户角色Id
var apppath = apppath();
var listbutton = window.parent.listbutton;
var lcljAdminOrAgency = "";
var currPersonId = '<%=currPersonId%>';
var pageurlhis = apppath+'/HUDH_DzblAct/findDzbl.act';
$(function() {
    onclickrowOobj = window.parent.onclickrowOobj;
   $("#detail_iframe").attr("src",apppath + "/static/js/hudh/dzbl/dzbl_detail.html");
   $("#sstype").on("change",function (){
	   initBcList();
   });
   
   nowday = getNowFormatDate();
   $("#starttime").val(nowday);
   $("#endtime").val(nowday);
   //绑定两个时间选择框的chage时间
   $("#starttime,#endtime").change(function() {
       timeCompartAndFz("starttime", "endtime");
   });

   //加载历史病历列表
   gethisbl();
 getButtonPower();//初始化按钮
});

//时间选择
$(".birthDate").datetimepicker({
    language: 'zh-CN',
    format: 'yyyy-mm-dd',
    minView: 2,
    autoclose: true,
    //选中之后自动隐藏日期选择框   
    pickerPosition: "bottom-right"
});

//查询按钮
$('#query').on('click',
function() {
	var pageurlhis = apppath+'/HUDH_DzblAct/findDzbl.act';
	$('#table').bootstrapTable('refresh', {
        'url': pageurlhis
    });
});

//清空
$('#clean').on('click',
	function() {
	    $(".staticDivContent :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
	});

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        searchValue: $('#searchvalue').val(),
        limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1
    };
    //console.log("参数="+JSON.stringify(temp));
    return temp;
}
//导出参数
function queryParamsB(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        searchValue: $('#searchvalue').val(),
    };
    return temp;
}

function gethisbl() {
    $("#table").bootstrapTable({
        url: pageurlhis,
        queryParams: queryParams,
        dataType: "json",
        singleSelect: false,
        striped: true,
        pagination: true,//是否显示分页（*）
        pageSize: 30,
        pageList : [15, 20, 25,30],//可以选择每页大小
        //paginationShowPageGo: true,
        sidePagination: "server",//后端分页 
        onLoadSuccess: function(data) { //加载成功时执行
        	var tableList = data.rows;
        },
        onDblClickCell: function(field, value, row, td) {
            detail(row.id,row.blcode);
        },
        columns: [{
    		    title : '序号',
    		    align: "center",
    		    width: 40,
    		    formatter: function (value, row, index) {
    		     /* return index + 1; */
    		     var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
    		        var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
    		        return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
    		    }
    		   },{
    	            title: '患者编号',
    	            field: 'blcode',
    	            align: 'center',
    	            valign: 'middle',
    	            sortable: true,
    	            visible: true,
    	            switchable: false,
    	            formatter: function(value, row, index) {
    	                if (value != "" && value != null) {
    	                    return '<span title="' + value + '">' + value + '</span>';
    	                }else{
    	                	return '<span></span>'
    	                }
    	            }
    	        },
        {
            title: '患者姓名',
            field: 'username',
            align: 'center',
            valign: 'middle',
            switchable: false
        },
        {
            title: '病历分类',
            field: 'blfl',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '病程',
            field: 'bc',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: 'id',
            field: 'id',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            editable: true,
            formatter:function(value,row,index){
            	return "<span>"+value+"</span>"
            }
        },
        {
            title: '创建人',
            field: 'createname',
            align: 'center',
            sortable: true,
            editable: true,
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj2 = $('#table').bootstrapTable('getData')[index];
    });
}

/**
 * 清空输入的信息
 */
function emptyInfo(){
	$("#blfl").val("");
	$("#bc").val("");
	$("#templeDetail").val("");
}

function detail(blId,blCode){
	parent.layer.open({
        type: 2,
        title: '详情',
        shadeClose: true,
        shade: 0.6,
        area: ['70%', '55%'],
        content: apppath+'/HUDH_DzblViewAct/toBlDetail.act?blId='+blId+'&blCode='+blCode
    });
}

/**
 *  设置按钮权限操作 
 */
 function getButtonPower() {
	    var menubutton1 = "";
	    for (var i = 0; i < listbutton.length; i++) {
	        if (listbutton[i].qxName == "dzbl_scbb") {
	            menubutton1 += '<li><a href="javascript:void(0);" style="margin-left:25px;" class="kqdsCommonBtn" onclick="exportTable()">导出报表</a>&nbsp;</li>';
	        }
	    }
	    $(".conditions").append(menubutton1);
	}

//导出
function exportTable() {
	loadedData = [];
	nowpage = 0;
    var fieldArr = [];
    var fieldnameArr = [];
    $('#table thead tr th').each(function() {
        var field = $(this).attr("data-field");
        if (field != "") {
            fieldArr.push(field); //获取字段
            fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
        }
    });
    var param = JsontoUrldata(queryParamsB());
    location.href = pageurlhis + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
}

</script>
</html>