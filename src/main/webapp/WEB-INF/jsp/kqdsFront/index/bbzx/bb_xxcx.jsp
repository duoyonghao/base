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
	String usercodes = request.getParameter("usercodes");
	if(usercodes == null){
		usercodes = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/reception_center.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/yiliao/bingli_search.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css"/>
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>

<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/Load.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<title>信息明细</title>
</head>
<style type="text/css">
/*工作量表格 ，单独写*/
#gongzuol{border:solid 1px #c0c0c0;background: #f5f5f5;}
#gongzuol{margin-bottom: 10px;}
#gongzuol .columnHd{padding:0 15px;border-bottom:solid 1px #0e7ec6;font-size:16px;color: #373737;font-family: "Microsoft YaHei";line-height:36px;}
#table tr td.bs-checkbox {
		padding:0px 11px;
	}
#container{
	padding-left:0;
}
#main .centerWrap{
	float:none;
}
.searchWrap .kqds_table{
	margin:0 auto;
}
@media screen and (max-width: 1251px){
	.kqds_table td {
	    font-size: 12px;
	}
	.kqds_table td select, .kqds_table td input[type=text] {
	    font-size: 12px;
	    width: 84px;
	}
}

.searchWrap .formBox>section>ul.conditions>li>span{
	width:65px;
	text-align:right;
}
.searchWrap .formBox>section>ul.conditions>li{
	padding:3px 0;
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
	height: 75px
}
</style>
<body>
<div id="container">
    <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
                <!-- <div class="columnHd"><span class="hc-icon icon20 home-icon"></span>信息明细</div> -->
                <div class="columnBd">
                    <div class="tableBox" >
                        <table id="table" class="table-striped table-condensed table-bordered"></table>
                    </div>
                </div>
            </div>
             <div id="gongzuol">
                <div class="columnBd">
                	<table style="width:90%;text-align: center;">
                		<tr>	
                			<td style="width:12%;text-align:left;"><span style="color:#00A6C0;">总记录数:<lable id="zong">0</lable></span></td>
                		</tr>
                	</table>
                </div>
            </div>
            <div class="searchWrap">
                <div class="cornerBox">查询条件</div>
                <div class="formBox">
                	<section>
			    		<ul class="conditions">
			    			<li>
			    				<span>所属门诊</span>
			    				<select id="organization" name="organization"></select>
			    			</li>
			    			<li class="birthDate">
			    				<span>创建时间</span>
	    						<input type="text" placeholder="开始日期" id="starttime" readonly>
		                   	</li>
			    			<li class="birthDate">
			    				<span>到</span>
	                            <input type="text"  placeholder="结束日期" id="endtime" readonly>
				           	</li>
				    		<li>
			    				<span>第一咨询</span>
		    					<input type="hidden" name="askperson" id="askperson" placeholder="咨询" title="咨询" class="form-control" style="width: 80%;" value=""/>
								<input  type="text"  id="askpersonDesc" name="askpersonDesc" placeholder="咨询" readonly  onClick="javascript:single_select_user(['askperson', 'askpersonDesc'],'',1);"></input>	
				    		</li>
				    		
				    		<li>
			    				<span>医生</span>
		    					<input type="hidden" name="doctor" id="doctor" placeholder="医生" title="医生" class="form-control" style="width: 80%;" value=""/>
							    <input  type="text"  id="doctorDesc" name="doctorDesc" placeholder="医生" readonly  onClick="javascript:single_select_user(['doctor', 'doctorDesc'],'',1);"></input>	
				    		</li>
				    		<li class="birthDate">
			    				<span>最近到院</span>
	    						<input type="text" placeholder="开始日期" id="dystarttime" readonly>
		                  	</li>
				    		<li class="birthDate">
			    				<span>到</span>
                             	<input type="text"  placeholder="结束日期" id="dyendtime" readonly>
		                    </li>
		                    <li>
				    			<span>建档人</span>
			    				<input type="hidden" name="jdr" id="jdr" placeholder="建档人" title="建档人" class="form-control" style="width: 80%;" value=""/>
								<input  type="text"  id="jdrDesc" name="jdrDesc" placeholder="建档人" readonly  onClick="javascript:single_select_user(['jdr', 'jdrDesc'],'single',1);"></input>	
				    		</li>
				    		<li>
				    			<span>患者来源</span>
<!-- 		    					<select class="patients-source select2 dict" tig="hzly" -->
<!-- 									name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype');"> -->
<!-- 								</select> -->
								<select class="patients-source select2 dict searchSelect" tig="hzly"
									name="devchannel" id="devchannel" onchange="getSubDictSelectSearch('devchannel','nexttype');" data-live-search="true" title="请选择">
								</select>
				            </li>
				    		<li>	
				    			<span>子分类</span>
<!-- 								<select class="select2 dict" name="nexttype" id="nexttype"> -->
<!-- 									<option value="">请选择</option> -->
<!-- 								</select> -->
								<select class="select2 dict searchSelect" name="nexttype" id="nexttype" data-live-search="true" title="请选择">
								</select>
							</li>
				    		<li>
				    			<span>有无回访</span>
								<select name="ywhf" id="ywhf">
									<option value="">请选择</option>
									<option value="0">无</option>
									<option value="1">有</option>
								</select>
							</li>
				    		<li>
				    			<span>模糊查询</span>
			    				<input type="text" id="username" class="username" placeholder="请输入客户姓名、手机号"> 
				    		</li>
		    			</ul>
		    		</section>
               	   	<div class="btnBar" id ="recommendedBarDiv" style="text-align:center;">
	                 	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="clean()">清空</a>
	    				<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="searchHzda()">查询</a>
	                </div>
                    <!-- <div class="searchBox">
                       <table class="kqds_table">
				    		<tr>
				    			<td style="text-align:right;">所属门诊：</td>
				    			<td style="text-align:left;">
										<select id="organization" name="organization"></select>
								</td>
				    			<td style="text-align:right;">创建时间：</td>
				    			<td style="text-align:left;"> 
				    					<span class="birthDate">
				    						<input type="text" placeholder="开始日期" id="starttime" readonly>
				                        </span>
				                </td>
				    			<td style="text-align:right;">到：</td>
				    			<td style="text-align:left;">
										<span class="birthDate">
				                             <input type="text"  placeholder="结束日期" id="endtime" readonly>
				                        </span>
								</td>
				    			
				    			<td style="text-align:right;">第一咨询：</td>
				    			<td style="text-align:left;">
				    					 <input type="hidden" name="askperson" id="askperson" placeholder="咨询" title="咨询" class="form-control" style="width: 80%;" value=""/>
										 <input  type="text"  id="askpersonDesc" name="askpersonDesc" placeholder="咨询" readonly  onClick="javascript:single_select_user(['askperson', 'askpersonDesc'],'',1);"></input>	
				    			</td>
				    		</tr>
				    		
				    		<tr>
				    			<td style="text-align:right;">医生：</td>
				    			<td style="text-align:left;">
				    					 <input type="hidden" name="doctor" id="doctor" placeholder="医生" title="医生" class="form-control" style="width: 80%;" value=""/>
									     <input  type="text"  id="doctorDesc" name="doctorDesc" placeholder="医生" readonly  onClick="javascript:single_select_user(['doctor', 'doctorDesc'],'',1);"></input>	
				    			</td>
				    			<td style="text-align:right;">最近到院时间：</td>
				    			<td style="text-align:left;"> 
				    					<span class="birthDate">
				    						<input type="text" placeholder="开始日期" id="dystarttime" readonly>
				                        </span>
				                </td>
				    			<td style="text-align:right;">到：</td>
				    			<td style="text-align:left;">
										<span class="birthDate">
				                             <input type="text"  placeholder="结束日期" id="dyendtime" readonly>
				                        </span>
								</td>
				    			<td style="text-align:right;">建档人：</td>
				    			<td style="text-align:left;">
				    				 <input type="hidden" name="jdr" id="jdr" placeholder="建档人" title="建档人" class="form-control" style="width: 80%;" value=""/>
									 <input  type="text"  id="jdrDesc" name="jdrDesc" placeholder="建档人" readonly  onClick="javascript:single_select_user(['jdr', 'jdrDesc'],'single',1);"></input>	
				    			</td>
				    		</tr>
				    		<tr>
				    			<td style="text-align:right;">患者来源：</td>
				    			<td style="text-align:left;"> 
				    					<select class="patients-source select2 dict" tig="hzly"
											name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype');">
										</select>
				                </td>
				    			
				    			<td style="text-align:right;">子分类：</td>
				    			<td style="text-align:left;">
										<select class="select2 dict" name="nexttype" id="nexttype">
											<option value="">请选择</option>
										</select>
								</td>
				    			<td style="text-align:right;">有无回访：</td>
				    			<td style="text-align:left;">
										<select name="ywhf" id="ywhf">
											<option value="">请选择</option>
											<option value="0">无</option>
											<option value="1">有</option>
										</select>
								</td>
				    			<td style="text-align:right;">模糊查询：</td>
				    			<td style="text-align:left;">
				    				<input type="text" id="username" class="username" placeholder="请输入客户姓名、手机号"> 
				    			</td>
				    		</tr>
				    	</table> 
                    </div>
                    <div id="bottomBarDdiv" class="btnBar" style="text-align: center;margin-top: 10px;">
	                    <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="clean()">清空</a>
	    				<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="searchHzda()">查询</a>
                    </div> -->
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/DataLazyLoad.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
</body>
<script type="text/javascript">
var listbutton;
var contextPath = "<%=contextPath%>";
var usercodes = "<%=usercodes%>";
var onclickrowOobj = "";
var selectedrows = "";
var nowday;
var pageurl = '<%=contextPath%>/KQDS_UserDocumentAct/selectWithNopage2.act';
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var personrole = "<%=person.getUserPriv()%>";
var personroleother = "<%=person.getUserPrivOther()%>";
var loc = new Location();
var menuid = "<%=menuid%>";
var qxnameArr = ['hdjl_user_scbb'];
var func = ['exportTable'];
$(function() {
	initHosSelectListNoCheck('organization'); // 连锁门诊下拉框
	
    initDictSelectByClass(); // 患者来源
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    //获取当前页面所有按钮
    getButtonPowerByPriv(qxnameArr,func,menuid);

    //时间选择
    $(".birthDate input").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "top-right"
    });

    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });

    //4、表格初始化
    initTable();
    //计算主体的宽度
    setWidth();
    $(window).resize(function() {
        setWidth();
        setHeight();
    });
  $('.searchSelect').selectpicker("refresh");//初始化刷新搜索--2019.11.13--licc
});
function initTable() {
	
    //初始化表格所在div
    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        singleSelect: false,
        queryParams: queryParams,
        onLoadSuccess: function(data) { //加载成功时执行
        	if(nowpage == 0 && data.total>0){
        		maxpage = Math.floor(data.total/pagesize)+1; 
        		$("#zong").html(data.total);
        	 }
        	//分页加载
        	showdata("table",data.rows);
        	setHeight();
        	/*表格出现滚动条时 调整表头*/
            setTableHeaderWidth(".tableBox");
        },
        columns: [
        {field: ' ',checkbox: true,formatter: stateFormatter},
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '序号',
            field: 'Number',
            align: 'center',
            valign: 'middle'
        },  
        {
            title: '建档时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                	return '<span>' + value + '</span>';
                }else{
                	return "";
                }
            }
        },
        {
            title: '上门状态',
            field: 'doorstatusname',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                	return '<span>' + value + '</span>';
                }else{
                	return "";
                }
            }
        },
        {
            title: '最近到院时间',
            field: 'lasttime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                	return '<span>' + value + '</span>';
                }else{
                	return "";
                }
            }
        },
        {
            title: '病人编号',
            field: 'usercode',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span>' + value + '</span>';
                    return html;
                }
            }
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name">' + value + '</span>';
                    return html;
                }
            }
        },
        {
            title: '性别',
            field: 'sex',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '年龄',
            field: 'age',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                if (value == "0") {
                    return "-";
                } else {
                    return value;
                }
            }
        },
        {
            title: '咨询',
            field: 'askperson',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name">' + value + '</span>';
                    return html;
                }
            }
        },
        {
            title: '医生',
            field: 'doctor',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name">' + value + '</span>';
                    return html;
                }
            }
        },
        {
            title: '手机号码1',
            field: 'phonenumber1',
            align: 'center',
            formatter: function(value, row, index) {
                if (value != null || value != "") {
                    if (canlookphone == 0) {
                        return '<span title="' + value + '">' + value + '</span>';
                    } else {
                        return '-';
                    }
                } else {
                    return '-';
                }
            }
        },
        {
            title: '手机号码2',
            field: 'phonenumber2',
            align: 'center',
            formatter: function(value, row, index) {
                if (value != null || value != "") {
                    if (canlookphone == 0) {
                        return '<span title="' + value + '">' + value + '</span>';
                    } else {
                        return '-';
                    }
                } else {
                    return '-';
                }
            }
        },
        {
            title: '患者来源',
            field: 'devchannelname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter:function(value){
            	return "<span class='source'>"+value+"</span>";
            }
        },
        {
            title: '来源子分类',
            field: 'nexttypename',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter:function(value){
            	return '<span class="source">'+value+'</span>'
            }
        },
        {
            title: '省',
            field: 'provincename',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter:function(value){
            	return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '市',
            field: 'cityname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter:function(value){
            	return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '区',
            field: 'townname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter:function(value){
            	return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '建档人',
            field: 'createuser',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            title: '有无回访',
            field: 'ywhf',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "无回访") {
                  	return "<span class='name' style='color:red;'>无回访</span>";
                }else{
                	return "有回访";
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
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	offset : 0,
    	limit :	 pagesize,
    	ispaging : 1,
   		organization: $('#organization').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        dystarttime: $('#dystarttime').val(),
        dyendtime: $('#dyendtime').val(),
        username: $('#username').val(),
        askperson: $('#askperson').val(),
        doctor: $('#doctor').val(),
        devchannel: $('#devchannel').val(),
        nexttype: $('#nexttype').val(),
        usercodes:usercodes,
        //province: $('#province').val(),
        //city: $('#city').val(),
        ywhf: $('#ywhf').val(),
        jdr: $('#jdr').val()
    };
    return temp;
}
function searchHzda() {
	loadedData = [];
	nowpage = 0;
    var starttime = $('#starttime').val();
    var endtime = $('#endtime').val();
    var dystarttime = $('#dystarttime').val();
    var dyendtime = $('#dyendtime').val();
    var username = $('#username').val();
    var askperson = $('#askperson').val();
    var doctor = $('#doctor').val();
    var devchannel = $('#devchannel').val();
    var nexttype = $('#nexttype').val();
    //var province = $('#province').val();
    //var city = $('#city').val();
    var ywhf = $('#ywhf').val();
    var jdr = $('#jdr').val();

    if (starttime == "" && endtime == "" && dystarttime == "" && dyendtime == "" && username == "" && askperson == "" && doctor == "" && devchannel == "" && nexttype == ""  && jdr == "" && ywhf == "") {
        layer.alert('请选择查询条件!' );
        return false;
    }
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
function clean() {
	//定位滚动条位置
    /* $(".fixed-table-body").animate({ 
    	scrollTop: 400
    	}, 1000); */
    $(".fixed-table-body").scrollTop(400); 
 	var rgvalue = $("#organization").val();
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#organization").val(rgvalue); 
    $(".searchSelect li.selected").empty();//清空
    $('.searchSelect').selectpicker("refresh"); //初始化刷新搜索--2019.11.13--licc
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
		var param  = JsontoUrldata(queryParams());
		location.href = pageurl+"?flag=exportTable&fieldArr="+JSON.stringify(fieldArr)+"&fieldnameArr="+JSON.stringify(fieldnameArr)+"&"+param;
}
//档案合并
function dahb(){
	  layer.open({
	        type: 2,
	        title: '档案合并',
	        shadeClose: false,
	        shade: 0.6,
	        area: ['90%', '90%'],
	        content: contextPath + '/KQDS_UserDocumentAct/toDahb.act'
	    });
}
//获取选中行的usercode
function getIdSelections() {
    return $.map($('#table').bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
}
//转诊咨询
function zzzx(){
	selectedrows = getIdSelections();
    if (onclickrowOobj == "" && selectedrows.length==0) {
	        layer.alert('请选择患者!' );
	        return false;
	}
	
	if (selectedrows.length == 0) {//单个患者转诊咨询
		layer.open({
		       type: 2,
		       title: '转诊咨询',
		       shadeClose: false,
		       shade: 0.6,
		       area: ['90%', '90%'],
		       content: contextPath + '/KQDS_UserDocumentAct/toZzIndex.act?usercode='+onclickrowOobj.usercode
		});
	} else {//批量转诊咨询
		layer.open({
		       type: 2,
		       title: '转诊咨询',
		       shadeClose: false,
		       shade: 0.6,
		       area: ['550px', '80%'],
		       content: contextPath + '/KQDS_UserDocumentAct/toZzWin.act'
		});
	}
}
//添加加工单
function jiagong() {
    if (onclickrowOobj == "") {
        layer.alert('请选择患者!' );
        return false;
    }
    layer.open({
        type: 2,
        title: '创建加工单',
        shadeClose: false,
        shade: 0.6,
        area: ['90%', '90%'],
        content: contextPath + '/KQDS_OutProcessingSheetAct/toAdd.act?usercode=' + onclickrowOobj.usercode
    });
}
//计算主体的宽度
function setWidth() {
    /* var baseW = $(window).width();
    $('.centerWrap,.lookInfoWrap').width(baseW); */
}

//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height() - 15,
    optAreaHeight = $('.searchWrap').outerHeight();

    var columnHdHeight = $('.columnWrap .columnHd').height();

    $('.lookInfoWrap .columnWrap').height(baseHeight);
    $("#table").bootstrapTable("resetView",{
    	height:$(window).outerHeight()-$(".columnWrap .columnHd").outerHeight()-$(".searchWrap").outerHeight()-$("#gongzuol").outerHeight()-20
    });
}
/*****#########################################系统数据清理 START#########################################****/
/**
 * 将所选的用户标识为删除状态
 * 即 isdelete状态标识为 1
 */
function deleteUser(){
	var selectRows = getIdSelections();
	var usercodesLocal = "";
	$.each(selectRows,function(index){
		usercodesLocal += selectRows[index].usercode + ",";
	});
	
	var delrequrl = contextPath + '/CleanDataAct/tmpDeleteUers.act?usercodes=' + usercodesLocal;
	var returnObj = deleteByUrlCommon(delrequrl);
	if(returnObj == null){
		return false; // 点击取消，不继续执行了
	}
	if(returnObj.retState == "0"){
		 layer.alert('伪删除成功！', {
               
         });
		searchHzda(); // 删除
	}
}

/**
 * 删除标识为删除状态用户的所有业务数据，包括档案数据
 */
function deleteUserReal(){
	// 这两个方法暂时不做！！  yangsen 6-27 10:46
}


/**
 * 设定所选用户产生的所有业务数据日志为指定日期
 */
function userDateSet(){
	// 这两个方法暂时不做！！  yangsen 6-27 10:46
}

//获取选中行的usercode
function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
}

//复选框
function stateFormatter(value, row, index) {
    if ("admin" != "<%=person.getUserId()%>") {
        return {
            disabled: true,
            checked: false
        };
    }
   
    return value;
}
/*****#########################################系统数据清理  END#########################################****/
</script>
</html>
