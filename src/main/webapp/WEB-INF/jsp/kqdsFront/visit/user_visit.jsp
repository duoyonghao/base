<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	// 是否可以修改别人的回访信息、和回访结果 0不可以 1可以
	String canEditOrder = UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag10_canEditOrder, request);
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>回访管理</title><!-- 左侧菜单 预约中心  回访管理 进入 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/reception_center.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/layer-v2.4/layer/skin/blueSkin/skin_style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/yuyue/visitReturn.css?v=${version}" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<!-- 跳页 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
</head>
<style type="text/css">
*{
	box-sizing:border-box;
}
/*工作量表格 ，单独写*/
.kqds_table{
	width:90%;
	align:center;
	margin-left: auto;
	margin-right: auto;
}
	
.kqds_table  td { 
	color: #666;
	padding: 3px 2px 5px 2px;
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

input[type=text],.kv .kv-v input[type=text]{padding:0 10px;width:100px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}

.fixed-table-body #listTable tr td:first-child{
	padding:0px 11px;
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
.hfflul>li a{
	color:#333 ;
}
.hfflul>li a.nowchecked{
	color:#00a6c0;
}
.searchModule>section>ul.conditions>li>span{
	width:70px;
	text-align:right;
}
.searchModule>section>ul.conditions>li{
	padding: 3px 0;
}
.fixed-table-container{
	padding-bottom: 89px!important;
	position: relative;
}
.fixed-table-pagination{
	display: block;
	width: 100%;
    background-color: white;
    position: absolute;
    left: 0px;
    bottom: 0px;
}
.fixed-table-container{
	background-color: white;
}

	/* 搜索框select */
	.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
	       width: 110px;   
	      }  
	.searchSelect>.btn { 
		    width: 110px; 
		 	height:26px; 
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
	.searchModule{
			 overflow: visible;
		}
	.searchModule>section>ul.conditions {
	    overflow: visible; 
	    height: 100%;
	    position: relative;
	    margin: 0;
	}
	.searchModule>section{
		height: 80px
	}

</style>
<body>
<div id="container">
    <div class="extraBar">
    	<div class="titleDiv" style="height:50px;margin:0;">
    		<div class="title" style="margin-top:16px;padding-left:15px;">
    			回访列表
    		</div>
    	</div>
        <!-- <div class="extraHd">回访列表</div> -->
        <!-- <div class="extraBd hc-scroll-webkit" style="overflow-y:auto;"> -->
        <div class="extraBd" style="overflow-y:auto;">
            <!-- <label><em>*</em>注：分类列表双击查看</label> -->
            <ul class="hfflul">
            </ul>
        </div>
    </div>
    <div class="main">
        <div class="listWrap">
            <div class="listHd">
            	<span class="title">回访记录列表</span>
           	</div>
            <div class="listBd">
                <div class="tableBox" style="overflow: hidden;">
                    <table id="listTable" class="table-striped table-condensed table-bordered" data-height="550"></table>
                </div>
            </div>
        </div>
        <!--查询条件-->
        <div class="searchModule">
	        <input type="hidden" id="righttype" value="">
	    	<header>
	    		<span>查询条件</span>
	    		<i>共有 记录<span id="total"></span> 条</i>
	    		<i>已回访 <span id="yihuifang"></span> 条</i>
	    	</header>
	    	<section>
	    		<ul class="conditions">
	    			<li>
	    				<span>所属门诊</span>
	    				<select id="organization" name="organization"></select>
	    			</li>
	    			<li class="unitsDate">
	    				<span>回访时间</span>
	    				<input type="text" placeholder="开始日期" id="vtime1" readonly>
	    			</li>
	    			<li class="unitsDate">
	    				<span>到</span>
	    				<input type="text"  placeholder="结束日期" id="vtime2" readonly>
	    			</li>
	    			<li>
	    				<span>姓名</span>
	    				 <input type="text" placeholder="" id="username" />
	    			</li>
	    			<li>
	    				<span>未回访</span>
	    				<select id="ishuifang">
                       		<option value="">- 请选择 -</option>
                       		<option value="0">未回访</option>
                       		<option value="1">已回访</option>
                       	</select>
	    			</li>
	    			<li>
	    				<span>回访分类</span>
	    				<select class="select2 dict searchSelect" id="hffl" name="hffl" tig="hffl" data-live-search="true" title="请选择"></select>
<!-- 	    				<select class="select2 dict" tig="hffl" name="hffl" id="hffl"></select> -->
	    			</li>
	    			<li>
	    				<span>回访部门</span>
	    				<select class="select2 searchSelect" id="visitdept" name="visitdept" data-live-search="true" title="请选择"></select>
<!-- 	    				<select class="select2" id="visitdept" name="visitdept"></select> -->
					</li>
	    			<li>
	    				<span>回访人</span>
	    				<input type="hidden" class="form-control"  name="visitor" id="visitor" placeholder="回访人员" title="回访人员" value=""/>
						<input  type="text" id="visitorDesc" name="visitorDesc" placeholder="回访人员" readonly   onClick="javascript:single_select_user(['visitor', 'visitorDesc'],'',1);"></input>
	    			</li>
	    			<li>
	    				<span>满意度</span>
	    				<select class="select2 dict" tig="HFMYD" name="myd" id="myd"></select>
	    			</li>
	    			<li>
	    				<span>模糊查询</span>
	    				<input type="text" name="phonenumber1" id="phonenumber1" placeholder="患者姓名/手机"/>
	    			</li>
	    		</ul>
	    	</section>
	    	<div class="btnBar" style="text-align:left;">
	           <a href="javascript:void(0);" id="tjhf" class="kqdsCommonBtn" onclick="goshowVisit()">查看详情</a>
               <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="tosend()">短信发送</a>
               <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="goAddVisit()">添加回访</a>
               <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="goEditVisit()">修改回访</a>
               <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="goFinishVisit(1)">回访结果</a>
               <a href="javascript:void(0);" id="tjhfjh" class="kqdsCommonBtn hide" onclick="goAddVisitPlan()">添加回访计划</a>
               <a href="javascript:void(0);" class="kqdsCommonBtn" id="clear" onclick="clearc()">清空</a> 
               <a href="javascript:void(0);" class="kqdsSearchBtn" id="query" onclick="refresh()">查询</a>
				<a href="javascript:void(0);" class="kqdsSearchBtn" id="" onclick="exportTable()">生成报表</a>
	        </div>
   		</div>
        
        <!-- <div class="searchWrap">
            <div class="cornerBox">查询条件</div>
            <input type="hidden" id="righttype" value="">
            <span class="text">共有记录<i class="total" id="total"></i>条，<i class="currentNum" id="yihuifang"></i>条已回访</span>
            <div class="formBox">
           		 <table class="kqds_table">
			    		<tr>
			    			<td style="text-align:right;">所属门诊：</td>
			    			<td style="text-align:left;">
			    				<select id="organization" name="organization"></select>
			    			</td>
			    			<td style="text-align:right;">回访时间：</td>
			    			<td style="text-align:left;"> 
		    					<span class="unitsDate">
		    						<input type="text" placeholder="开始日期" id="vtime1" readonly>
		                        </span>
			                </td>
			    			
			    			<td style="text-align:right;">到：</td>
			    			<td style="text-align:left;">
								<span class="unitsDate">
		                             <input type="text"  placeholder="结束日期" id="vtime2" readonly>
		                        </span>
							</td>
			    			<td style="text-align:right;">姓名：</td>
			    			<td style="text-align:left;">
		    					 <input type="text" placeholder="" id="username" />
			    			</td>
			    			<td style="text-align:right;">未回访：</td>
			    			<td style="text-align:left;">
		    					<select id="ishuifang">
		                       		<option value="">- 请选择 -</option>
		                       		<option value="0">未回访</option>
		                       		<option value="1">已回访</option>
		                       	</select>
			    			</td>
			    		</tr>
			    		<tr>
			    			<td style="text-align:right;">回访分类：</td>
			    			<td style="text-align:left;">
		    					 <select class="select2 dict" tig="hffl" name="hffl" id="hffl"></select>
			    			</td>
			                <td style="text-align:right;">回访人：</td>
			    			<td style="text-align:left;">
		    					    <input type="hidden" class="form-control"  name="visitor" id="visitor" placeholder="回访人员" title="回访人员" value=""/>
									<input  type="text" id="visitorDesc" name="visitorDesc" placeholder="回访人员" readonly   onClick="javascript:single_select_user(['visitor', 'visitorDesc'],'',1);"></input>	
			    			</td>
			    		
			    			<td style="text-align:right;">满意度：</td>
			    			<td style="text-align:left;">
		    					 <select class="select2 dict" tig="HFMYD" name="myd" id="myd"></select>
			    			</td>
			    			<td style="text-align:right;">模糊：</td>
			    			<td style="text-align:left;" colspan="11">
		    					 <input type="text" name="phonenumber1" id="phonenumber1" placeholder="患者姓名/手机"/>
			    			</td>
			    		</tr>
			    </table>
                <div class="btnBar">
                	<a href="javascript:void(0);" id="tjhf" class="kqdsCommonBtn" onclick="goshowVisit()">查看详情</a>
                	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="tosend()">短信发送</a>
	                <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="goAddVisit()">添加回访</a>
	                <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="goEditVisit()">修改回访</a>
	                <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="goFinishVisit(1)">回访结果</a>
	                <a href="javascript:void(0);" class="kqdsCommonBtn" id="clear" onclick="clearc()">清空</a>
	                <a href="javascript:void(0);" class="kqdsSearchBtn" id="query" onclick="queryTable()">查询</a>
            	</div>
            </div> -->
            
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js?v=${version}"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/btnfuc.js?v=${version}"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>

<script type="text/javascript">

var contextPath = '<%=contextPath%>';

var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
//初始化表头，返回空的数据
var nullUrl = '<%=contextPath%>/UtilAct/intTableHeader.act';
var pageurl = '<%=contextPath%>/KQDS_VisitAct/selectList4returnVisit.act';
var querycounturl = '<%=contextPath%>/KQDS_VisitAct/getCountByQuery.act';


var onclickrow = "";
var selectedrows = "";
var onclickrowOobj="";

var userid = "<%=person.getSeqId()%>";
var userpriv = "<%=person.getUserPriv()%>";
var nowday;
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var menuid = "<%=menuid%>";
var qxnameArr = ['hfzx_scbb'];
var func = ['exportTable'];
var $table = $("#listTable");
$(function() {
	getSelectDeptTree("visitdept"); // 部门树下拉框
	
	//initHosSelectListNoCheck('organization');// 连锁门诊下拉框
    initHosSelectListNoCheck('organization','<%=ChainUtil.getCurrentOrganization(request)%>'); // 加载门诊列表
	//获取当前页面所有按钮
    getButtonAllCurPage("<%=menuid%>");
    //当前日期
    nowday = getNowFormatDate();
    //mingday = getNowFormatDateMing();
    //$("#vtime1").val(nowday);
    //$("#vtime2").val(nowday);
    //绑定两个时间选择框的chage时间
    $("#vtime1,#vtime2").change(function() {
        timeCompartAndFz("vtime1", "vtime2");
    });
    
    /* 
    // 现在没有连锁
    if (userpriv != "1") {
        //非管理员 则隐藏门诊下拉框
        $("#menzhenorg").hide();
    } */

    initDictSelectByClass(); // 回访分类、挂号分类、满意度
    
    //加载右侧回访分类
    //inithffl();
    
	//加载表格
    initTable(pageurl);

    //查询数量
    queryTable();
    
    
    
    
    //时间选择
    $(".unitsDate input").datetimepicker({
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
    $('.searchSelect').selectpicker("refresh");//初始化刷新--2019.10.23--licc	
});
//清空
function clearc() {
    $(".conditions :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
//  清空搜索下拉框1
//     $(".searchSelect li.selected").empty();
//     $('.searchSelect').selectpicker("refresh");//初始化刷新--2019.10.23--licc	
//  清空搜索下拉框2
	$(".searchSelect li.selected").removeClass("selected");
  	$(".searchSelect button .pull-left").text("请选择");    
}
function queryParams(params) {
	//console.log(params+"-------------回访管理参数");
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	//limit: params.limit,   //页面大小
        //offset: params.offset, //页码 
        //pageIndex : params.offset/params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)
        vtime1: $('#vtime1').val(),
        vtime2: $('#vtime2').val(),
        username: $('#username').val(),
        hffl: $('#hffl').val(),
        organization: $('#organization').val(),
        visitor: $('#visitor').val(),
        visitdept: $('#visitdept').val(),
        myd: $('#myd').val(),
       // regsort: $('#regsort').val(),
        ishuifang: $('#ishuifang').val(),
        phonenumber1: $('#phonenumber1').val(),
        sortName:this.sortName,
    	sortOrder:this.sortOrder
    };
    return temp;
}

//点击查询，此时查询的是右侧树形目录的数据
function queryTable() {
	
    selectedrows = "";
    var vtime1 = $("#vtime1").val();
    var vtime2 = $("#vtime2").val();
    var username = $("#username").val();
    var hffl = $("#hffl").val();
    var organization = $("#organization").val(); 
    var visitdept = $("#visitdept").val();
    var visitor = $("#visitor").val();
    var myd = $("#myd").val();
    //var regsort = $("#regsort").val();
    var ishuifang = $("#ishuifang").val();
    var phonenumber1 = $("#phonenumber1").val();
    
    if (vtime1 == "" && vtime2 == "" && username == "" && hffl == "" && visitdept == ""  && visitor == "" && myd == "" &&  ishuifang == "" && phonenumber1 == "") {
        /* layer.alert('请选择查询条件' );
        return false; */
    }

 	// 1、清空表格数据
    //$('#listTable').bootstrapTable('removeAll'); 
    
    // 2、初始化右侧分类条数，并以列表展示
    inithffl();
    
 	// 全部，赋值
    $("#allHFFL").attr("value",hffl);
    
    //查询各类条数
    $.axseY(querycounturl, queryParams(),
    function(data) {
        if (data.retState == "0") {
            //总条数
            $("#total").html(data.total);
            $("#yihuifang").html(data.yihuifang); // 没有选择 回访分类进行查询的时候， 才查询此值 
            if (data.flcounts != null || data.flcounts.length > 0) {
                $("ul.hfflul li").each(function() {
                	var hffl_id = $(this).attr("id");
                	
                    var thisli = $(this).html();
                    for (var i = 0; i < data.flcounts.length; i++) {
                        var thisstr1 = data.flcounts[i].name;
                        var hffl_id_server = data.flcounts[i].hffl;
                        
                        if(hffl_id == hffl_id_server){ // 匹配 ID 是否一致，一致才执行下面代码
                        	$(this).html(""); // 先清空
                        	if (getStrLen(thisstr1) > 16) {
                                $(this).html("<a href='#' onclick='aquery(this)' title='" + thisstr1 + "'>" + thisstr1.substring(0, 12) + ".. " + data.flcounts[i].count + " 人</a>");
                            } else {
                                $(this).html("<a href='#' onclick='aquery(this)' title='" + thisstr1 + "'>" + thisstr1 + "  " + data.flcounts[i].count + " 人</a>");
                            }
                        }
                    }
                });
            }
        }
    },
    function() {});
    // 加载完分类后，触发 全部连接，显示回访信息
    //$("#allHFFL").find("a").trigger("click");
    
}

//点击右侧回访分类对应条数 查询数据
function aquery(obj) {
    var hffl = $(obj).parent().attr("value");
  	//被点击时获得样式
	$(".nowchecked").removeClass("nowchecked");//清除橘色字体颜色
	$(obj).addClass("nowchecked");//被点击的文本 添加橘色样式
	
    $("#righttype").val(hffl);
    //加载表格
    $('#listTable').bootstrapTable('refresh', {
        'url': pageurl + '?hffl=' + hffl
    });
} 

function refresh(){
	$("#query").attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
	$("#query").text("查询中");
	//alert("正在查询，请稍后！");
	queryTable();
	//var hffl = $("#righttype").val();
	var url = pageurl;// + "?hffl=" + hffl
    //加载表格
    $('#listTable').bootstrapTable('refresh', {
        'url': url
    });
	
};

// 查询字典表中 回访分类，并以列表展示
function inithffl() {
    var url = contextPath + "/YZDictAct/getListByParentCode.act?parentCode=HFFL";
    $.axse(url, null,
    function(data) {
        var list = data.list;
        if (list != null) {
            if (list.length > 0) {
                var ulstr = "";
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    var optionStr1 = optionStr.dictName;
                    if (getStrLen(optionStr1) > 16) {
                        ulstr += '<li id="' + optionStr.seqId + '" value="' + optionStr.seqId + '" title="' + optionStr1 + '">' + optionStr1.substring(0, 14) + '..</li>';
                    } else {
                    	ulstr += '<li id="' + optionStr.seqId + '" value="' + optionStr.seqId + '" title="' + optionStr1 + '">' + optionStr1 + '</li>';
                    }
                }
                
                ulstr += '<li id="allHFFL" value="" title="全部" style="font-weight:bold;"></li>';
                
                $("ul.hfflul").html(ulstr);
            }
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
}


/* var row = $.map($('#listTable').bootstrapTable('getSelections'),function (row) {
	return row;
}); */ 
/* function refresh() {
    $("#listTable").bootstrapTable('refresh', {
        'url': tmpUrl
    });
} */

//加载表格
function initTable(url) {

	$("#listTable").bootstrapTable({
        url: url,
        dataType: "json",
        contentType : "application/x-www-form-urlencoded",   //必须有
        queryParams: queryVisitParams,
        cache: false,
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList : [10, 15, 20, 25],//可以选择每页大小
        striped: true,
        clickToSelect: false,
        singleSelect: false,
		sidePagination: "server",//后端分页
        paginationShowPageGo: true,
        onLoadSuccess: function(data) { //加载成功时执行
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","auto").css("pointer-events","auto");
				$("#query").text("查询");
			}
             /*表格载入时，设置表头的宽度 */
             setTableHeaderWidth(".tableBox");

        },
        onDblClickCell: function(field, value, row, td) {
            //双击事件 调用提交回访结果方法 
            goToUserCenterPage(row.usercode);
        },
        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
        //服务端处理分页
       	columns: [ 
        {
            field: '',
            checkbox: true,
            formatter: stateFormatter
        },
        {
        	title : '序号',
        	align: "center",
        	width: 40,
        	formatter: function (value, row, index) {
        		/* return index + 1; */
        		var pageSize = $('#listTable').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
            	var pageNumber = $('#listTable').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
            	return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
        	}
        },
        {
            title: '所属门诊',
            field: 'organizationname',
            align: 'center',
            
            formatter: function(value, row, index) {
                if (value) {
                    return '<span>' + value + '</span>';
                }else{
                	return '<span></span>';
                }
            }
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span>' + value + '</span>';
                }else{
                	return '<span></span>';
                }
            }
        },
        {
            title: '患者姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                 return '<span class="name">' + value + '</span>';
            }
        },
        {
            title: '患者性别',
            field: 'sex',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '患者手机1',
            field: 'phonenumber1',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (canlookphone == 0) {
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '<span>-</span>';
                }
            }
        },
        {
            title: '患者手机2',
            field: 'phonenumber2',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (canlookphone == 0) {
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '<span>-</span>';
                }
            }
        },
        {
            title: '会员',
            field: 'memberno',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
            	if (value == '是') {
                    return "<span style='color:green;'>是</span>";
                } else {
                	return "<span style='color:red;'>否</span>";
                }
            }
        },
        {
            title: '回访状态',
            field: 'status',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                var htmlstr = "";
                if (row.status == "未回访") {
                    htmlstr = "<span class='label label-danger'>未回访</span>";
                } else if (row.status == "已回访") {
                    htmlstr = "<span class='label label-success'>已回访</span>";
                }	
                return htmlstr;
            }
        },
        {
            title: '回访时间',
            field: 'visittime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (row.visittime != "" && row.visittime != null) {
                    return '<span>' + value + '</span>';
                }else{
                	return '<span></span>';
                }
            }
        },
        {
            title: '满意度',
            field: 'myd',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '回访部门',
            field: 'visitdeptname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name" title="'+value+'">' + value + '</span>';
                }
            }
        },
        {
            title: '回访人员',
            field: 'visitor1',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name">' + value + '</span>';
                }
            }
        },
        {
        	title: '建档人',
        	field: 'jdr',
        	align: 'center',
        	sortable: true,
        	formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name">' + value + '</span>';
                }
            }
        },
       /*  {
            title: '挂号分类',
            field: 'regsort',
            align: 'center',
            
            sortable: true
        }, */
        {
            title: '回访分类',
            field: 'hffl1',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '回访要点',
            field: 'hfyd',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (row.hfyd != "" && row.hfyd != null) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                }
            }
        },
        {
            title: '回访结果',
            field: 'hfresult',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (row.hfresult != "" && row.hfresult != null) {
                    return '<span class="remark" title="' + value + '">' + value + '</span>';
                }
            }
        },
        {
            title: '回访结果时间',
            field: 'finishtime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span title="' + value + '">' + value + '</span>';
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
    	$('.success').removeClass('success'); // 去除之前选中的行的，选中样式
        $(element).addClass('success'); // 添加当前选中的 success样式用于区别
        var index = $('#listTable').find('tr.success').data('index'); // 获得选中的行
        onclickrowOobj = $('#listTable').bootstrapTable('getData')[index];
    });
}


//回访管理新参数
function queryVisitParams(params) {
	//console.log(JSON.stringify(params)+"-------------回访管理参数");
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)
        vtime1: $('#vtime1').val(),
        vtime2: $('#vtime2').val(),
        username: $('#username').val(),
        hffl: $('#hffl').val(),
        organization: $('#organization').val(),
        visitor: $('#visitor').val(),
        visitdept: $('#visitdept').val(),
        myd: $('#myd').val(),
       // regsort: $('#regsort').val(),
        ishuifang: $('#ishuifang').val(),
        phonenumber1: $('#phonenumber1').val(),
        sortName:this.sortName,
    	sortOrder:this.sortOrder
    };
    return temp;
}

$("#listTable").on('load-success.bs.table', function (data) {//table加载成功后的监听函数
    //console.log("load success");
    $(".pull-right").css("display", "block");
});


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
	 var index = $('#listTable').find('tr.success').data('index'); // 获得选中的行
     onclickrowOobj = $('#listTable').bootstrapTable('getData')[index];
     return onclickrowOobj;
    /* return $.map($('#listTable').bootstrapTable('getSelections'),
    function(row) {
        return row;
    }); */
}
//获取选中行的usercode
function getIdSelection() {
    return $.map($table.bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
}
//添加回访
function goAddVisit() {
    selectedrows = getIdSelections();
    if (selectedrows == "" || selectedrows == null) {
        layer.alert('请选择一个患者' );
        return false;
    } else {
        layer.open({
            type: 2,
            title: '添加回访',
            shadeClose: true,
            shade: 0.6,
            area: ['550px', '480px'],
            content: '<%=contextPath%>/KQDS_VisitAct/toVisitAdd.act?lytype=isparent&usercode=' + selectedrows.usercode 
        });
    }
    <%-- if (selectedrows.length == 0) {
        layer.alert('请选择一个患者' );
    } else if (selectedrows.length > 1) {
        layer.alert('请选择一个患者' );
    } else {
        layer.open({
            type: 2,
            title: '添加回访',
            shadeClose: true,
            shade: 0.6,
            area: ['550px', '480px'],
            content: '<%=contextPath%>/KQDS_VisitAct/toVisitAdd.act?lytype=isparent&usercode=' + selectedrows[0].usercode 
        });
    } --%> 
}
//查看详情
function goshowVisit(){
	 selectedrows = getIdSelections();
	 if (selectedrows == "" || selectedrows == null) {
	        layer.alert('请选择一个患者' );
	        return false;
	  } else{
		 var index = parent.layer.open({
		       type: 2,
		       title: '回访详情',
		       maxmin: true,
		       shadeClose: true,
		       // 点击遮罩关闭层
		       area: ['550px', '520px'],
		       content: '<%=contextPath%>/KQDS_VisitAct/toVisitDetail.act?seqId=' + selectedrows.seqId
	    });
	 }
}
//修改回访
function goEditVisit() {
    selectedrows = getIdSelections();
    if (selectedrows == "" || selectedrows == null) {
        layer.alert('请选择一个患者' );
        return false;
  	} 
    /* if (selectedrows.length == 0) {
        layer.alert('请选择回访记录' );
        return false;
    } 
	if (selectedrows.length > 1) {
        layer.alert('请选择一个患者' );
        return false;
    }  */
	if (selectedrows.status == "已回访") {
        layer.alert('您选中的回访记录已经回访过了,无法修改' );
        return false;
    }
    //验证是否是自己添加的回访 不是自己的不能修改和提交
    if ('<%=canEditOrder%>' != '1' && !isown(selectedrows.visitorid)) {
        layer.alert('只能操作自己的回访记录' );
        return false;
    } 
    layer.open({
        type: 2,
        title: '修改回访',
        shadeClose: true,
        shade: 0.6,
        area: ['550px', '480px'],
        content: '<%=contextPath%>/KQDS_VisitAct/toVisitEdit.act?type=isparent&seqId=' + selectedrows.seqId 
    });
}

//检查是否是自己的返回true 不是自己的返回false
function isown(aid) {
    var isown = true;
    /* if (aid != userid && aid != "1") {
        isown = false;
    } */
    $.ajax({
		type: "POST",
		async: false,
     	url: contextPath +"/YZPersonAct/findIsLeaveBySeqId.act",
      	dataType: "json",
      	data:{seqId:aid},
      	success: function(data){  
      		if(data.retMsrg!='1'){
      			if(aid != userid&& aid != "1"){
      				isown = false;
      			}
      		}
      	}
	})
    return isown;
}

//回访结果
function goFinishVisit(type, dbcrow) {
    //单击
    selectedrows = getIdSelections();
    if (selectedrows == "" || selectedrows == null) {
        layer.alert('请选择一个患者' );
        return false;
  	} 
    /* if (selectedrows.length == 0) {
        layer.alert('请选择一条回访记录' );
        return false;
    }
    if (selectedrows.length > 1) {
        layer.alert('请选择一个患者' );
        return false;
    } */
    var nowdate = getNowFormatDate();
	//预约时间不是今天或者之后的日期  则提示不能填写回访结果
	if(checkdate(nowdate,selectedrows.visittime.substring(0,10)) == 1){ // 0是当前日期大于回访日期  1 当前日期小于回访日期  2是当前日期等于回访日期
		layer.alert('未到回访时间，不能填写回访结果', {});
		return false;
	}
	if(selectedrows.status == "已回访"){
		layer.alert('您选中的回访记录已经回访过了' );
		return false;
	}
	//验证是否是自己添加的回访 不是自己的不能修改和提交
	if('<%=canEditOrder%>' != '1' && !isown(selectedrows.visitorid)){
		layer.alert('只能操作自己的回访记录' );
		return false;
	}
    var requrl = '<%=contextPath%>/KQDS_VisitAct/toVisitPost.act?type=hfzx&seqId=' + selectedrows.seqId;
    static_btnfucDeal('visit_post', requrl);

}
//短信
function tosend(){
	selectedrows = getIdSelections();
	if (selectedrows == "" || selectedrows == null) {
        layer.alert('请选择一个患者' );
        return false;
  	} else{
  		layer.open({
 	       type: 2,
 	       title: '短信发送',
 	       shadeClose: false,
 	       shade: 0.6,
 	       area: ['650px', '550px'],
 	       content: contextPath + '/KQDS_SmsAct/toSendSms.act'
 		});
  	}
}
//导出
function exportTable() {
    var fieldArr=[];
	var fieldnameArr=[];
	$('#listTable thead tr th').each(function () {
		var field = $(this).attr("data-field");
		if(field!=""){
			fieldArr.push(field);//获取字段
			fieldnameArr.push($(this).children()[0].innerText);//获取字段中文
		}
	});
	var params = queryParams();
	params.hffl = $("#righttype").val();
	var param  = JsontoUrldata(params);
	location.href = pageurl+"?flag=exportTable&fieldArr="+JSON.stringify(fieldArr)+"&fieldnameArr="+JSON.stringify(fieldnameArr)+"&"+param;
}
//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height();
    var serachH = $('.searchModule').outerHeight();
    $('.extraBar .extraBd').height(baseHeight - $(".titleDiv").outerHeight()-35);
    $('#listTable').bootstrapTable('resetView', {
        height: baseHeight - serachH - $(".listWrap .listHd").outerHeight()+15
    });
}

//计算字符串长度
function getStrLen(str) {
    if (str == null) return 0;
    if (typeof str != "string") {
        str += "";
    }
    return str.replace(/[^\x00-\xff]/g, "01").length;
}

//添加回访计划
function goAddVisitPlan() {
	selectedrows = getIdSelection();
    /* if (isdelreg == "1") {
        layer.alert('撤销的挂号无法添加回访！' );
        return false;
    }*/
    if (selectedrows.length=='0') {
        layer.alert('请选择患者'  );
        return false;
    } 
    var usercodes;
    for (var i = 0; i < selectedrows.length; i++) {
    	if(i==0){
			usercodes=selectedrows[i].usercode+",";
    	}else if(i==selectedrows.length-1){
    		usercodes+=selectedrows[i].usercode;
    	}else{
    		usercodes+=selectedrows[i].usercode+",";
    	}
	}
    layer.open({
        type: 2,
        title: '添加回访计划',
        shadeClose: false,
        shade: 0.6,
        area: ['850px', '680px'],
        content: '<%=contextPath%>/KQDS_VisitAct/toVisitPlansAdd.act?usercodes='+usercodes
    });
}
/**
 *  设置按钮权限操作 
 */
 function getButtonPower() {
	    for (var i = 0; i < listbutton.length; i++) {
	        if(listbutton[i].qxName == "tjhfjh"){
	        	$("#tjhfjh").removeClass("hide");
	        }
	    }
	}
</script>
</html>