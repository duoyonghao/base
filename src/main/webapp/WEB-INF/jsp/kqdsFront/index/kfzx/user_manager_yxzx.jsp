<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();

	// 客服中心—客户管理页面右侧的网电信息Url和其他页面不一样，
	// 一个是kqdsFront/jdzx/dialogFrame/netorder.jsp
	// 一个是kqdsFront/jdzx/frame/kfzx/netorder_insertOrUpdate.jsp
	// 为了能统一包含一个公用JSP文件，所以在这里设置个标识，供rightPartInfo.jsp界面判断
	//staticIsUserMgrPage: value为1 网电预约  2 报备预约
	request.setAttribute("staticIsUserMgrPage", "1");
	YZPerson person = SessionUtil.getLoginPerson(request);
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>营销中心—客户管理</title>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/reception_center.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/yiliao/bingli_search.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<style>
/*查询条件中的样式  */
.searchWrap .formBox>section>ul.conditions>li{
	padding: 3px 0;
}
.searchWrap .formBox>section>ul.conditions>li>span{
	width:62px;
	text-align:right;
}
.searchWrap .formBox>section>ul.conditions>li>input[type=text], 
.searchWrap .formBox>section>ul.conditions>li>select{
	width:94px;
}
@media screen and (max-width:1390px){
	.searchWrap .formBox>section>ul.conditions>li>span{
		width:55px;
		text-align:right;
		font-size:11px;
		height:24px;
		line-height:24px;
	}
	.searchWrap .formBox>section>ul.conditions>li>input[type=text], 
	.searchWrap .formBox>section>ul.conditions>li>select{
		width:82px;
		font-size:11px;
		padding:0 3px 0 5px;
		height:24px;
		line-height:24px;
	}
}
@media screen and (max-width:1100px){
	.searchWrap .formBox>section>ul.conditions>li>span{
		width:51px;
		text-align:right;
		font-size:10px;
		height:22px;
		line-height:22px;
	}
	.searchWrap .formBox>section>ul.conditions>li>input[type=text], 
	.searchWrap .formBox>section>ul.conditions>li>select{
		width:73px;
		font-size:10px;
		padding:0 3px 0 5px;
		height:22px;
		line-height:22px;
	}
}
	.pagination li{
		margin-left: 0px!important;
		height:auto!important;
	}
	.tableBox{
		border-bottom: 1px solid #ddd;
	}
	.dropdown-menu{
		min-width: auto!important;
	    padding: 0px 0!important;
	}
	.dropdown-menu li{
		margin-left: 0px!important;
	}
	.centerWrap .columnWrap .columnBd ul li.current {
    color: #00a6c0;
    border-bottom: 0px solid #00a6c0;
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
       color: #666;
	}
	.bootstrap-select.btn-group .dropdown-toggle .filter-option {
	   margin-top: -3px;
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
	.searchWrap{
			 overflow: visible;
		}
	.formBox{
			overflow: visible;
		}
	.searchWrap .formBox>section {
			 height: 100px;
		}
	.searchWrap .formBox>section>ul.conditions {
		    overflow: visible;
		    height: 100%;
		    position: relative;
		}
	.searchWrap .formBox>section{
		height: 100px
	}
</style>
</head>

<body>
<div id="container">
    <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
	            <div class="columnHd">
				    <span class="title">客户接待列表</span>
				</div>
                <div class="columnBd">
                    <div class="tableBox">
                        <table id="table" class="table-striped table-condensed table-bordered" data-height="430"></table>
                    </div>
                    <div id="gongzuol">
		                <div class="columnBd">
		           			<ul class="dataCountUl" id="dataCount">
			               		<li>总记录数:<span id="zong">0</span></li>
			               		<li>上门数:<span id="sm">0</span></li>
			               		<li>成交数:<span id="cj">0</span></li>
			               		<li>未上门数:<span id="wsm">0</span></li>
			               		<li>未成交数:<span id="wcj">0</span></li>
			               	</ul>
		                </div>
		            </div>
                </div>
            </div>
             
            <div class="searchWrap">
                <!-- <div class="cornerBox">查询条件</div> -->
                <div class="searchToggleBtnGroup">
                	<span class="ptcx checked">
                		通用查询
                	</span>
                	<span class="gjcx">
                		高级查询
                	</span>
                </div>
                <div class="formBox">
                	<section>
			    		<ul class="conditions">
			    			<li class="unitsDate">
			    				<span>建档门诊</span>
			    				<select id="organization" name="organization"></select>
			    			</li>
			    			<li class="unitsDate">
			    				<span>建档日期</span>
	    						<input type="text" placeholder="开始日期" id="jdtime1" readonly>
		                   	</li>
			    			<li class="unitsDate">
			    				<span>到</span>
	                            <input type="text"  placeholder="结束日期" id="jdtime2" readonly>
		                    </li>
			    			<li>
			    				<span>模糊查询</span>
			    				<input type="text" id="queryInput" class="searchInput" placeholder="姓名/手机号" > 
			    			</li>
			    			<li class="toggleTr">
			    				<span>咨询项目</span>
<!-- 		    					<select class="dict" tig="ZXXM" name="xiangmu" id="xiangmu" ></select> -->
		    					<select class="dict searchSelect" tig="ZXXM" name="xiangmu" id="xiangmu"  data-live-search="true" title="请选择"></select>
			    			</li>
			    			<li class="toggleTr unitsDate">
			    				<span>预约日期</span>
	    						<input type="text" placeholder="开始日期" id="yytime1" readonly>
		                    </li>
			    			<li class="toggleTr unitsDate">
			    				<span>到</span>
	                            <input type="text"  placeholder="结束日期" id="yytime2" readonly>
		                    </li>
							<li class="toggleTr">
			    				<span>客户星级</span>
			    				<select id="important">
	                        		<option value="">- 请选择 -</option>
	                        		<option value="1">一级</option>
	                        		<option value="2">二级</option>
	                        		<option value="3">三级</option>
	                        		<option value="4">四级</option>
	                        		<option value="5">五级</option>
	                        	</select>
			    			</li>
			    			<li class="toggleTr">
			    				<span>患者来源</span>
<!-- 			    				<select class="patients-source select2 dict" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype');"> -->
<!-- 								</select> -->
								<select class="patients-source select2 dict searchSelect" resource="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelectSearch('devchannel','nexttype');" data-live-search="true" title="请选择">
								</select>
			    			</li>
			    			<li class="toggleTr">
			    				<span>子分类</span>
<!-- 			    				<select class="select2 dict" name="nexttype" id="nexttype"> -->
<!-- 									<option value="">请选择</option> -->
<!-- 								</select> -->
			    				<select class="select2 dict searchSelect" name="nexttype" id="nexttype" data-live-search="true" title="请选择">
								</select>
			    			</li>
			    			<li class="toggleTr">
			    				<span>上门</span>
		                        <select id="doorstatus">
	                        		<option value="">- 请选择 -</option>
	                        		<option value="0">未上门</option>
	                        		<option value="1">已上门</option>
	                        	</select>
			                </li>
			                <li class="toggleTr">
			    				<span>成交状态</span>
		                        <select id="cjstatus">
	                        		<option value="">- 请选择 -</option>
	                        		<option value="0">未成交</option>
	                        		<option value="1">已成交</option>
	                        	</select>
			                </li>
			    		
			    			<li class="toggleTr">
			    				<span>受理类型</span>
		    					<select class="dict" tig="SLLX" id="shouli" ></select>
			    			</li>
			    			<li class="toggleTr">
			    				<span>受理工具</span>
<!-- 		    					<select class="dict" tig="SLGJ" id="gongju" ></select> -->
		    					<select class="dict searchSelect" tig="SLGJ" id="gongju" data-live-search="true" title="请选择"></select>
			    			</li>
			    			<li class="toggleTr">
			    				<span>建档人</span>
		    					 <input type="hidden" name="yewu" id="yewu" value="" title="建档人" class="form-control"/>
								 <input type="text" id="yewuDesc" name="yewuDesc" value="" onClick="javascript:single_select_user(['yewu', 'yewuDesc'],'',1);"  readonly></input>
			    			</li>
		    			</ul>
		    		</section>
               	   	<div class="btnBar" id ="recommendedBarDiv" style="text-align:left;">
	                 	<a href="javascript:void(0);" class="kqdsCommonBtn" id="hzjd" onclick="hzjd()">新建档案</a> 
	                 	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="hzjdedit()">修改档案</a> 
		                <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="goAddRenwu()">添加推广</a>
		                <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="goAddVisit()">添加回访</a>
	                    <a href="javascript:void(0);" class="kqdsCommonBtn clean" id="clear">清空</a>
	                    <a href="javascript:void(0);" class="kqdsSearchBtn" onclick="querySC();" id="query">查询</a>
	                </div>
                    <!-- <table class="kqds_table">
			    		<tr>
			    			<td style="text-align:right;">建档门诊：</td>
			    			<td style="text-align:left;">
			    				<select id="organization" name="organization"></select>
			    			</td>
			    			<td style="text-align:right;">建档日期：</td>
			    			<td style="text-align:left;"> 
		    					<span class="unitsDate">
		    						<input type="text" placeholder="开始日期" id="jdtime1" readonly>
		                        </span>
			                </td>
			    			
			    			<td style="text-align:right;">到：</td>
			    			<td style="text-align:left;">
								<span class="unitsDate">
		                             <input type="text"  placeholder="结束日期" id="jdtime2" readonly>
		                        </span>
							</td>
			    			<td style="text-align:right;">模糊查询：</td>
			    			<td style="text-align:left;" colspan="3">
			    				<input type="text" id="queryInput" class="searchInput" placeholder="姓名/手机号" > 
			    			</td>
			    			
			    		</tr>
				    		
			    		<tr class="toggleTr">
			    			<td style="text-align:right;">咨询项目：</td>
			    			<td style="text-align:left;">
		    					 <select class="dict" tig="ZXXM" name="xiangmu" id="xiangmu" ></select>
			    			</td>
			    			<td style="text-align:right;">预约日期：</td>
			    			<td style="text-align:left;"> 
		    					<span class="unitsDate">
		    						<input type="text" placeholder="开始日期" id="yytime1" readonly>
		                        </span>
			                </td>
			    			<td style="text-align:right;">到：</td>
			    			<td style="text-align:left;">
								<span class="unitsDate">
		                             <input type="text"  placeholder="结束日期" id="yytime2" readonly>
		                        </span>
							</td>
							<td style="text-align:right;">客户星级：</td>
			    			<td style="text-align:left;">
			    				<select id="important">
	                        		<option value="">- 请选择 -</option>
	                        		<option value="1">一级</option>
	                        		<option value="2">二级</option>
	                        		<option value="3">三级</option>
	                        		<option value="4">四级</option>
	                        		<option value="5">五级</option>
	                        	</select>
			    			</td>
			    		</tr>
			    		<tr class="toggleTr">
			    			<td style="text-align:right;">患者来源：</td>
			    			<td style="text-align:left;">
			    				<select class="patients-source select2 dict" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype');">
								</select>
			    			</td>
			    			
			    			<td style="text-align:right;">子分类：</td>
			    			<td style="text-align:left;">
			    				<select class="select2 dict" name="nexttype" id="nexttype">
									<option value="">请选择</option>
								</select>
			    			</td>
			    			<td style="text-align:right;">上门：</td>
			    			<td style="text-align:left;"> 
		                        <select id="doorstatus">
	                        		<option value="">- 请选择 -</option>
	                        		<option value="0">未上门</option>
	                        		<option value="1">已上门</option>
	                        	</select>
			                </td>
			                <td style="text-align:right;">成交状态：</td>
			    			<td style="text-align:left;"> 
		                         <select id="cjstatus">
	                        		<option value="">- 请选择 -</option>
	                        		<option value="0">未成交</option>
	                        		<option value="1">已成交</option>
	                        	</select>
			                </td>
			    		</tr>
			    		
			    		<tr class="toggleTr">
			    			<td style="text-align:right;">受理类型：</td>
			    			<td style="text-align:left;">
		    					 <select class="dict" tig="SLLX" id="shouli" ></select>
			    			</td>
			    			<td style="text-align:right;">受理工具：</td>
			    			<td style="text-align:left;">
		    					<select class="dict" tig="SLGJ" id="gongju" ></select>
			    			</td>
			    			<td style="text-align:right;">建档人：</td>
			    			<td style="text-align:left;">
		    					 <input type="hidden" name="yewu" id="yewu" value="" title="建档人" class="form-control"/>
								 <input type="text" id="yewuDesc" name="yewuDesc" value="" onClick="javascript:single_select_user(['yewu', 'yewuDesc'],'',1);"  readonly></input>
			    			</td>
			    		</tr>
			    	</table> -->
                </div>
             
                 
            </div>
        </div>
        <!--右侧信息浏览-->
        <div class="lookInfoWrap">
			<%@include file="/inc/rightPartInfo.jsp" %>
		</div>
    </div>
</div>
</body>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript">
var deptId = '<%=person.getDeptId()%>'; 
var contextPath = "<%=contextPath%>";
var pageurl = '<%=contextPath%>/KQDS_UserDocumentAct/userManger4Yxzx.act';
var listbutton;
var onclickrowOobj = "";
var nowday;
var selectedrows = "";
var personrole = "";
var $table = $("#table");
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var canKd = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag2_canKd, request)%>';
var loc = new Location();
var isyx = '1'; // 1 代表营销  2代表网电
$(function() {

    //进入页面默认查询今日网电预约列表
    nowday = getNowFormatDate();
    //绑定两个时间选择框的chage时间
    $("#jdtime1,#jdtime2").change(function() {
        timeCompartAndFz("jdtime1", "jdtime2");
    });
    $("#yytime1,#yytime2").change(function() {
        timeCompartAndFz("yytime1", "yytime2");
    });
    
    
    /** ########################################################### 连锁相关  **/
	//initHosSelectListNoCheck('organization');// 连锁门诊下拉框
	initHosSelectListNoCheck('organization','<%=ChainUtil.getCurrentOrganization(request)%>'); // 加载门诊列表
    
	initDictSelectByClassIsSCDept(deptId,'triggerChange');//患者来源根据部门
    initDictSelectByClass('triggerChange'); // 咨询项目、受理类型、受理工具
    /* 左侧个人中心的按钮样式控制js已经被抽取到rightPartInfo.jsp页面中 */
	togglemodel.initial('yxzx',pageurl);/*wl 初始化 开关模块 */
    //3、加载表格
    initTable(1);

    //清空
    $('#clear').on('click',
    function() {
        $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
        $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
        $(".searchSelect li.selected").empty();//清空
        $('.searchSelect').selectpicker("refresh");//初始化刷新--2019.11.13--licc
    });

    //时间选择
    $(".unitsDate input").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "top-right"
    });

    //获取当前页面所有按钮##现在没用到此方法 ## yangsen 17-5-2
    getButtonAllCurPage("<%=menuid%>");

    //计算主体的宽度
    
    $(window).resize(function() {
        setWidth();
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });
    /* 常用查询 按钮 高级查询  按钮*/
    initSearchToggleBtnGroup();
  $('.searchSelect').selectpicker("refresh");//初始化刷新搜索--2019.11.14--licc
});

/**
 * 初始化患者来源 （专属市场人员）licc 2020.1.15
 */
function initDictSelectByClassIsSCDept(deptId,operFlag) {
    if ($(".dict").length > 0) {
        for (var i = 0; i < $(".dict").length; i++) {
            var dict = $(".dict").eq(i);
            // :eq() 选择器选取带有指定 index 值的元素。index值从 0 开始，所有第一个元素的 index 值是 0（不是1）。
            var type = dict.attr("resource");
            var url = contextPath + "/YZDictAct/getListByParentCode.act?parentCode=" + type;
            $.axse(url, null,
            function(data) {
                var list = data.list;
                if (list != null && list.length > 0) {
                    dict.empty();
                    dict.append("<option value=''>请选择</option>");
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                    	 if(deptId=="41a669d3-fe87-4732-822b-dae6b168f4bf"){
                    		 //根据登录部门是市场
                         	if(optionStr.seqId=="sc569"||optionStr.seqId=="zj-sc85"){
                         		dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                         	}else{
                         		dict.append("<option value='" + optionStr.seqId + "' style='display:none;'>" + optionStr.dictName + "</option>");
                         	}
                    	 }else{
                        	dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                    	 }
                    }
                }
                if ('triggerChange' == operFlag) { // 触发Onchange事件 #### add by yangsen 
                    $(dict).trigger('change'); // 和 连锁代码片段的 $("#organization").change 配合使用
                }
            },
            function() {
                layer.alert('查询出错！'  );
            });
        }
    }
}
//导出
function exportTable() {
	var fieldArr=[];
	var fieldnameArr=[];
	$('#table thead tr th').each(function () {
		var field = $(this).attr("data-field");
		if(field!=""){
			fieldArr.push(field);//获取字段
			fieldnameArr.push($(this).children()[0].innerText);//获取字段中文
		}
	});
	var param  = JsontoUrldata(queryParamsB());
	location.href = pageurl+"?flag=exportTable&fieldArr="+JSON.stringify(fieldArr)+"&fieldnameArr="+JSON.stringify(fieldnameArr)+"&"+param;
}
function refresh(){
	$('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        sortName:this.sortName,
        sortOrder:this.sortOrder,
        pageIndex : params.offset/params.limit + 1,
    	organization:$("#organization").val(),
        type: 1,
        jdtime1: $('#jdtime1').val(),
        jdtime2: $('#jdtime2').val(),
        shouli: $('#shouli').val(),
        gongju: $('#gongju').val(),
        devchannel: $('#devchannel').val(),
        nexttype: $('#nexttype').val(),
        yytime1: $('#yytime1').val(),
        yytime2: $('#yytime2').val(),
        xiangmu: $('#xiangmu').val(),
        important: $('#important').val(),
        yewu: $('#yewu').val(),
        doorstatus: $('#doorstatus').val(),
        cjstatus: $('#cjstatus').val(),
        queryinput: $('#queryInput').val()
    };
    return temp;
}
function queryParamsB(params) {
	var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		organization:$("#organization").val(),
		type: 1,
		jdtime1: $('#jdtime1').val(),
		jdtime2: $('#jdtime2').val(),
		shouli: $('#shouli').val(),
		gongju: $('#gongju').val(),
		devchannel: $('#devchannel').val(),
		nexttype: $('#nexttype').val(),
		yytime1: $('#yytime1').val(),
		yytime2: $('#yytime2').val(),
		xiangmu: $('#xiangmu').val(),
		important: $('#important').val(),
		yewu: $('#yewu').val(),
		doorstatus: $('#doorstatus').val(),
		cjstatus: $('#cjstatus').val(),
		queryinput: $('#queryInput').val()
	};
	return temp;
}
//点击查询
function querySC() {
   /*  var jdtime1 = $("#jdtime1").val();
    var jdtime2 = $("#jdtime2").val();
    var shouli = $("#shouli").val();
    var gongju = $("#gongju").val();
    var yytime1 = $("#yytime1").val();
    var yytime2 = $("#yytime2").val();
    var xiangmu = $("#xiangmu").val();
    var important = $("#important").val();
    var yewu = $("#yewu").val(); //业务
    var doorstatus = $('#doorstatus').val();
    var cjstatus = $('#cjstatus').val();
    var queryInput = $("#queryInput").val();

    if (jdtime1 == "" && jdtime2 == "" && shouli == "" && gongju == "" && yytime1 == "" && yytime2 == "" && xiangmu == ""  && important == "" && yewu == "" && queryInput == "" && doorstatus == "" && cjstatus == "") {
        layer.alert('请选择查询条件' );
        return false;
    } */
	//查询中，禁止查询按钮点击 lutian
	$("#query").attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none");
	$("#query").text("查询中");
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
//加载表格
//网电预约
function initTable(nums) {

    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        contentType : "application/x-www-form-urlencoded",   //必须有
        queryParams: queryParams,
        cache: false,
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList : [10, 15, 20, 25],//可以选择每页大小
        striped: true,
        clickToSelect: false,
        singleSelect: false,
        paginationShowPageGo: true,
        sidePagination: "server",//后端分页 
        onLoadSuccess: function(data) { //加载成功时执行
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer").css("pointer-events","auto");
				$("#query").text("查询");
			}
            var tableList = data.nums[0];
            $("#zong").html(data.total);
            /* var sms = 0,
            cjs = 0,
            wsms = 0,
            wcjs = 0;
            for (var i = 0; i < tableList.length; i++) {
                if (tableList[i].doorstatus == "已上门") {
                    sms = sms + 1;
                } else {
                    wsms = wsms + 1;
                }
                if (tableList[i].cjstatus == "已成交") {
                    cjs = cjs + 1;
                } else {
                    wcjs = wcjs + 1;
                }
            } */
            $("#sm").html(tableList.doorstatus);
            $("#cj").html(tableList.cjstatus);
            $("#wsm").html(Number(data.total)-Number(tableList.doorstatus));
            $("#wcj").html(Number(data.total)-Number(tableList.cjstatus));
            
            setWidth();
            setHeight();
            /*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
            
        },
        columns: [{
            field: ' ',
            checkbox: true,
            formatter: stateFormatter
        },
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
            title: '建档门诊',
            field: 'organizationname',
            align: 'center',
            
            visible: true,
            switchable: false,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '">' + value + '</span>';
                }else{
                	return '<span></span>';
                }
            }
        },
        {
            title: '上门状态',
            field: 'zdoorstatus',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
            	if (value == "已上门") {
                    return '<span class="label label-success">已上门</span>';
                } else {
                    return '<span class="label label-danger">未上门</span>';
                }
            }
        },
        {
            title: '本次上门',
            field: 'doorstatus',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "已上门") {
                    return '<span class="label label-success">已上门</span>';
                } else {
                    return '<span class="label label-danger">未上门</span>';
                }
            }
        },
        {
            title: '本次成交',
            field: 'cjstatus',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "已成交") {
                    return '<span class="label label-success">已成交</span>';
                } else {
                    return '<span class="label label-danger">未成交</span>';
                }
            }
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '">' + value + '</span>';
                }else{
                	return '<span></span>';
                }
            }
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                }else{
                	return '<span class="name"></span>';
                }
            }
        },
        {
            title: '手机号2',
            field: 'phonenumber2',
            align: 'center',
            
            visible: false, // 不展示
            switchable: false
        },
        {
            title: '手机号',
            field: 'phonenumber1',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (canlookphone == 0) {
                    if (row.phonenumber1 != "" && row.phonenumber1 != null) {
                        return '<span title="' + row.phonenumber1 + '">' + row.phonenumber1 + '</span>';
                    } else if (row.phonenumber2 != "" && row.phonenumber2 != null && (row.phonenumber1 == "" || row.phonenumber1 == null)) {
                        return '<span title="' + row.phonenumber2 + '">' + row.phonenumber2 + '</span>';
                    } else {
                        return '<span></span>';
                    }
                } else {
                    return '<span></span>';
                }
            }
        },
        {
            title: '建档人',
            field: 'createuser',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '<span class="name"></span>';
                }
            }
        },
        {
            title: '咨询项目',
            field: 'askitem',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return '<span class="source"></span>';
                }
            }
        },
        {
            title: '建档时间',
            field: 'createtime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span>' + value + '</span>';
                } else {
                    return "<span></span>";
                }
            }
        },
        {
            title: '预约时间',
            field: 'ordertime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span>' + value + '</span>';
                } else {
                    return "<span></span>";
                }
            }
        },
        {
            title: '到诊时间',
            field: 'guidetime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span>' + value + '</span>';
                }else{
                	return '<span></span>';
                }
            }
        },
        {
            title: '患者来源',
            field: 'devchannel',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return '<span class="source"></span>';
                }
            }
        },
        {
            title: '来源子分类',
            field: 'nexttype',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return '<span class="source"></span>';
                }
            }
        },
        {
            title: '省',
            field: 'provincename',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span class="name">'+value+'</span>';
            }
        },
        {
            title: '市',
            field: 'cityname',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span class="name">'+value+'</span>';
            }
        },
        {
            title: '区',
            field: 'townname',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span class="name">'+value+'</span>';
            }
        },
        {
            title: '街道',
            field: 'streetName',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span class="name">'+value+'</span>';
            }
        },
        {
            title: '年龄',
            field: 'age',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "0") {
                    return '<span></span>';
                } else {
                    return '<span>'+value+'</span>';
                }
            }
        },
        {
            title: '星级',
            field: 'important',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '商务通身份证',
            field: 'xueli',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return "<span></span>";
                }
            }
        },
        {
            title: '咨询',
            field: 'askperson',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return "<span></span>";
                }
            }
        },
        {
            title: '受理类型',
            field: 'accepttype',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return "<span></span>";
                }
            }
        },
        {
            title: '受理工具',
            field: 'accepttool',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return "<span class='source'></span>";
                }
            }
        },
        {
            title: '咨询内容',
            field: 'askcontent',
            align: 'center',
            
            /* sortable: true, */
            formatter:function(value,row,index){  
		    	  if(value){
		    		  var showVal = value;
		    		  if(value.length > 6){
		    			  showVal = value.substring(0, 6) + '...';
		    		  }
		        	  html = '<span class="remark" title="'+value+'">'+showVal+'</span>';
		              return html;  
		    	  }else{
		    		  return "<span></span>";
		    	  }
	  		}
        },
        {
            title: '患者备注',
            field: 'remark',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="remark">' + value + '</span>';
                }else{
                	return '<span class="remark"></span>'
                }
            }
        },
        {
            title: '关联人',
            field: 'glr',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return "<span class='name'></span>";
                }
            }
        },
        {
            title: '修改原因',
            field: 'glrremark',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="remark">' + value + '</span>';
                }else{
                	return '<span class="remark"></span>';
                }
            }
        }]
    }).on('click-row.bs.table',function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        $('#tabIframe').attr('src', $('#tabIframe').attr('src')); //个人中心
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
    return $.map($table.bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
}
//添加任务(推广计划)
function goAddRenwu() {
    selectedrows = getIdSelections();
    //selectedrows[0]
    if (selectedrows.length == 0) {
        layer.alert('请勾选复选框，选择需要加入推广计划的患者(可多选)' );
    } else {
        layer.open({
            type: 2,
            title: '添加推广计划',
            shadeClose: false,
            shade: 0.6,
            area: ['50%', '490px'],
            content: contextPath + '/KQDS_ExtensionAct/toExtensionAdd.act'
        });
    }
}

//添加回访
function goAddVisit() {
    if (onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined") {
        layer.alert('请选择需要回访的患者' );
    } else {
        layer.open({
            type: 2,
            title: '添加回访',
            shadeClose: false,
            shade: 0.6,
            area: ['550px', '480px'],
            content: contextPath+'/KQDS_VisitAct/toVisitAdd.act?lytype=usermanager&type=khgl&usercode=' + onclickrowOobj.usercode 
        });
    }
}
//修改关联人
function goeditGlr() {
    selectedrows = getIdSelections();
    if (selectedrows.length == 0) {
        layer.alert('请勾选复选框，选择需要修改关联人的患者(可多选)' );
    } else {
        layer.open({
            type: 2,
            title: '修改关联人',
            shadeClose: false,
            shade: 0.6,
            area: ['90%', '98%'],
            content: contextPath+'/KQDS_UserDocumentAct/toWdIndex.act'
        });
    }
}
//新建档案
function hzjd() {
    layer.open({
        type: 2,
        title: '患者建档',
        shadeClose: false,
        shade: 0.6,
        area: ['740px', '90%'],
        content: contextPath + '/KqdsUserdocumentMergeRecordAct/toHzjd_NetPlantDept.act'     
    });
}
function hzjdedit() {

    if (onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined") {
        layer.alert('请选择需要修改的患者' );
    } else {
        //修改资料
        layer.open({
            type: 2,
            title: '修改患者资料',
            shadeClose: false,
            shade: 0.6,
            area: ['740px', '90%'],
            content: contextPath + '/KQDS_UserDocumentAct/toHzjd_Net_Edit.act?usercode=' + onclickrowOobj.usercode
        });
    }
}

//计算界面宽高的设置
//setWidth() ,setHeight()函数移动到tableHeaderWidth.js

function getButtonPower() {
    var menubutton1 = "";
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "xgglr") {
            menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="goeditGlr()">修改关联人</a>&nbsp;';
        } else if (listbutton[i].qxName == "scbb") {
            menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="exportTable();">生成报表</a>&nbsp;';
        }
    }
    $("#hzjd").before(menubutton1);
}
</script>
</html>