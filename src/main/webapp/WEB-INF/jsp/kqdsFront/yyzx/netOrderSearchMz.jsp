<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	//合并测试
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	//挂号页面 查看患者预约情况 带入的参数
	String usercode = request.getParameter("usercode");
	if (usercode == null) {
		usercode = "";
	}
	String username = request.getParameter("username");
	if (username == null) {
		username = "";
	}
	String organization = request.getParameter("organization");
	if (organization == null) {
		organization = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>预约中心</title><!-- 点击预约中心 按钮进入  以表格方式展现 ，分网电和门诊预约 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/yyzx.css" /><!-- 样式已经整理 -->
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />    
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<style type="text/css">
.fixed-table-container{border:1px solid #ddd;}
.refreshA1{float: right;margin-right: 6px;height: 25px;line-height: 25px;padding: 0px 15px;color:#0e7cc9 ;border-radius:15px;border:1px solid #0e7cc9;outline:none;background:#fff ;}
.refreshA1:hover,.refreshA1:focus{color:#fff;text-decoration: none;background: #0e7cc9;border:1px solid #0e7cc9;}
/*  鲍预约中心搜索样式布局修改
.register-bottom  .kv{float: left;width:230px;}
.register-bottom  .kv.w410 {float: left;width:430px;}*/
.kqds_table{
	width:100%;
	align:center;
	/* margin-left: auto; */
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
input[type=text],.kv .kv-v input[type=text]{padding:0 10px;width:90px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}
select { 
		height: 28px;
		width:90px;
		border: solid 1px #e5e5e5;
		border-radius: 3px;
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
/*旧 查询条件标签样式 */
.queryText{
	height:30px;
	width:65px;
	line-height:30px;
	text-align:center;
	font-size:14px;
	color:#fff;
	background: #00A6C0 ;
}
/*新 查询条件  中的样式 */
.searchModule>header {
    height: 28px;
    padding: 5px 0 5px 15px;
    border-bottom: 1px solid #ddd;
    box-sizing: border-box;
}
.searchModule>section{
	padding:5px;
}
.searchModule>section>ul.conditions{
	margin-right:205px;
}
.searchModule>section>ul.conditions>li{
	padding:2px 0 2px;
}
.searchModule>section>ul.conditions>li>span{
	width:75px;
	text-align:right;
}
.searchModule>section>ul.conditions>li>input[type=text],
.searchModule>section>ul.conditions>li>select{
	width:100px;
	padding:0 2px;
}
.fixed-table-container{
	background-color: white;
	padding-bottom: 90px!important;
}
.fixed-table-body{
	border-bottom: 1px solid #ddd;
}
/* 搜索框select */
.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
       width: 100px;   
      }  
.searchSelect>.btn { 
	    width: 100px; 
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
	
  /* 	解決标签查询中下拉框悬浮  */
.searchModule>section>ul.conditions {
   overflow: visible; 
   margin: 0;
}
.searchModule>section{
	height: 50px
}
</style>
</head>
<body>
<!-- <div style="">
	<table style="width: 100%; height: 35px;">
		<tr>
			<td class="queryText">
				查询条件
			</td>
			<td id="tdmz" style="text-align:right;">
				<a id="guahao1" href="javascript:void(0);" class="kqdsCommonBtn">挂号</a>
				<a id="clearmenzhen" href="javascript:void(0);" class="kqdsCommonBtn">清空</a>
				<a id="searchmenzhen"  onclick="searchmenzhen();" href="javascript:void(0);" class="kqdsSearchBtn">查询</a>
			</td>
		</tr>
	</table>
</div> -->

	<div class="" id="menzhen">
		<%-- <!-- 查询区域 -->
		<div class="register-bottom"> 
			  <table class="kqds_table">
		    		<tr>
		    			<td style="text-align:right;">预约时间：</td>
		    			<td style="text-align:left;width: 125px;"> 
		    					<span class="unitsDate">
		    						<input type="text" style="width:120px;" placeholder="开始时间" class="dataTimemenzhen"  id="starttime" >
		                        </span>
		                </td>
		    			
		    			<td style="text-align:right;">到：</td>
		    			<td style="text-align:left;">
								<span class="unitsDate">
		                           <input type="text" style="width:120px;" placeholder="结束时间" class="dataTimemenzhen" id="endtime" >
		                        </span>
						</td>
						<td style="text-align:right;">本次上门状态：</td>
		    			<td style="text-align:left;">
		    					<select name="orderstatus" id="orderstatus">
									<option value="">请选择</option>
				                	<option value="0">未上门</option>
				                	<option value="1">已上门</option>
								<select>	
		    			</td>
		    			<td style="text-align:right;">项目分类：</td>
		    			<td style="text-align:left;">
								<select class="dict" tig="YYXM" name="yyxm" id="yyxm" ></select>
						</td>
		    			<td style="text-align:right;">科室：</td>
		    			<td style="text-align:left;">
								<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_0%>,<%=ConstUtil.DEPT_TYPE_1%>" name="regdept" id="regdept"></select>
						</td>
		    			
		    			<td style="text-align:right;">咨询：</td>
		    			<td style="text-align:left;">
		    					 <select  name="askperson" id="askperson"></select>
		    					 <input type="hidden" name="checkperson" id="checkperson" placeholder="咨询人员" title="咨询人员" class="form-control" /> 
								 <input type="hidden"  onchange="changeAskPerson()"   id="checkpersonDesc" name="checkpersonDesc" placeholder="咨询人员" readonly style="width: 120px;" ></input>	
							     <a onClick="javascript:select()">高级选择</a>
		    			</td>
		    			<td style="text-align:right;">模糊查询：</td>
		    			<td style="text-align:left;">
		    					<input type="text" id="musername" name="musername"  placeholder="患者姓名/手机" style="width: 100px;"/>
		    			</td>
		    		</tr>
			</table>
		</div> --%>
		
		<!--查询条件-->
	    <div class="searchModule" style="margin-bottom:2px;text-align:right;">
	    	<header>
	    		<span>查询条件</span>
	    		<!-- <i>共有记录 <span id="total"></span> 条</i> -->
	    	</header>
	    	<section style="border-bottom:none;">
	    		<div id="tdmz" style="float:right;margin-top:2px;">
		            <a id="guahao1" href="javascript:void(0);" class="kqdsCommonBtn">挂号</a>
					<a id="clearmenzhen" href="javascript:void(0);" class="kqdsCommonBtn">清空</a>
					<a id="searchmenzhen"  onclick="searchmenzhen();" href="javascript:void(0);" class="kqdsSearchBtn">查询</a>
		        </div>
	    		<ul class="conditions">
	    			<li >
		    			<span>门诊:</span>
						<select name="organization" id="organization" ></select>
					</li>
					<li>
						<span>预约时间</span>
						<input type="text" style="font-size:11px;" placeholder="开始时间" class="dataTimemenzhen"  id="starttime" >
					</li>
					<li>
						<span>到</span>
                        <input type="text" style="font-size:11px;" placeholder="结束时间" class="dataTimemenzhen" id="endtime" >
		            </li>
					<li>
						<span>本次上门</span>
    					<select name="orderstatus" id="orderstatus">
							<option value="">请选择</option>
		                	<option value="0">未上门</option>
		                	<option value="1">已上门</option>
						<select>	
		    		</li>
					<li>
		    			<span>项目分类</span>
		    			<select id="yyxm" name="yyxm" tig="YYXM" class="dict searchSelect"  data-live-search="true" title="请选择"></select>
<!-- 						<select class="dict" tig="YYXM" name="yyxm" id="yyxm" ></select> -->
					</li>
					<li>
		    			<span>科室</span>
		    			<select id="regdept" name="regdept" tag="<%=ConstUtil.DEPT_TYPE_0%>,<%=ConstUtil.DEPT_TYPE_1%>" class="dept searchSelect"  data-live-search="true" title="请选择"></select>
<%-- 						<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_0%>,<%=ConstUtil.DEPT_TYPE_1%>" name="regdept" id="regdept"></select> --%>
					</li>
					<li>
		    			<span>咨询</span>
		    			<select id="askperson" name="askperson" class="searchSelect"  data-live-search="true" title="请选择"></select>
<!--     					<select  name="askperson" id="askperson"></select> -->
    					<input type="hidden" name="checkperson" id="checkperson" placeholder="咨询人员" title="咨询人员" class="form-control" /> 
						<input type="hidden"  onchange="changeAskPerson()"   id="checkpersonDesc" name="checkpersonDesc" placeholder="咨询人员" readonly  ></input>	
					    <a style="position:relative;top:3px;" onClick="javascript:select()">高级选择</a>
		    		</li>
					<li>
		    			<span>模糊查询</span>
    					<input type="text" id="musername" name="musername"  placeholder="患者姓名/手机" />
		    		</li>
	    		</ul>
	    	</section>
	    </div>
		
	    <!--表格-->
    	<div class="tableBox1">
           	<table id="table1" class="table-striped table-condensed table-bordered" ></table>
        </div>
	</div>
	    <!--表格-->
    	<div class="tableBox2">
           	<table id="table2" class="table-striped table-condensed table-bordered" ></table>
        </div>
	</div>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var visitname ="<%=person.getUserName()%>";
var userpriv ="<%=person.getUserPriv()%>";
var $table1 = $('#table1');
var pageurl1 = '<%=contextPath%>/KQDS_Hospital_OrderAct/selectNoPage.act';
var nowday;
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var usercode = "<%=usercode%>";
var clickrow = "";

//页面人员高级选择
function select(){
	//获取部门的所有id
	var jzks = $("#regdept option").map(function(){return $(this).val();}).get().join(",");
	jzks=jzks.replace(/^,+|,+$/g,'');
	deptid_select_user(['checkperson', 'checkpersonDesc'], jzks);
}

//人员树选择后 改变 两级下拉选
function changeAskPerson(){
	var checkerson= $("#checkperson").val();
	if(checkerson){
		//获取用户部门id
		var url = contextPath+ "/YZPersonAct/getDeptByUserSeqId.act?seqId="+checkerson;
		$.axse(url, null, function(data) {
			if(data.retState=="0"){
				var val =data.retData;
				//就诊科室赋值
				$("#regdept").val(val);
				//手动触发 下拉选change事件
				$("#regdept").trigger("change");
				//人员下拉选赋值
				$("#askperson").val(checkerson);
			}

		}, function() {
		layer.alert('查询出错！', {
		});
		});
		
	}
}
function queryParamsB(params) {
    var temp = {  //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    		sortName:this.sortName,//排序名称
            sortOrder:this.sortOrder,//排序类型
            limit: params.limit,   //页面大小
            offset: params.offset, //页码 
            pageIndex : params.offset/params.limit + 1,
    		isdelete:0,
		    regdept:$("#regdept").val(),
   	        askperson:$("#askperson").val(),
   	     	orderstatus:$("#orderstatus").val(),
   	    	starttime:$("#starttime").val(),
   	    	yyxm:$("#yyxm").val(),
   			endtime:$("#endtime").val(),
            username:$("#musername").val()
    };
    return temp;
}	
function queryParams(params) {
    var temp = {  //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    		isdelete:0,
		    regdept:$("#regdept").val(),
   	        askperson:$("#askperson").val(),
   	     	orderstatus:$("#orderstatus").val(),
   	    	starttime:$("#starttime").val(),
   	    	yyxm:$("#yyxm").val(),
   			endtime:$("#endtime").val(),
            username:$("#musername").val()
    };
    return temp;
}
$(function(){
	//默认选中门诊
	$("#menzhneradio").attr("checked","checked");
	
	//获取当前日期
	nowday = getNowFormatDate();
	$("#starttime").val(nowday);
   	$("#endtime").val(nowday);
   	
    //绑定两个时间选择框的chage时间
	$("#starttime,#endtime").change(function(){
		timeCompartAndFz("starttime","endtime");
    });
	if('<%=organization%>' != ''){
		static_organization = '<%=organization%>';
	}else{
		static_organization = '<%=ChainUtil.getCurrentOrganization(request)%>';
	}
	
	initHosSelectListNoCheck('organization',static_organization); // 加载门诊列表
	$("#organization").change(function() {
	    var currSelect = $(this).val();
	    //clearmenzhen();
	    $("#organization").val(currSelect);
	    initDictSelectByClass(currSelect);
	});
	 
	initDictSelectByClass(static_organization); //加载下拉
	//initDictSelectByClass(); // 预约项目、咨询项目
	//监听 onchange事件       根据部门选择人员
    //selectChangeTwo("regdept","askperson",1);
    selectChangeTwoSearch("regdept", "askperson",1);
    initDeptSelectByTypesAndClass('<%=ChainUtil.getCurrentOrganization(request)%>');//加载科室
	//加载门诊预约表格
	inittablemenzhen();
	//时间选择
	$(".dataTimemenzhen").datetimepicker({
		language:  'zh-CN',  
		minView:0,
        autoclose : true,
		format: 'yyyy-mm-dd hh:ii',
		pickerPosition: "bottom-right"
	});
	
	//清空门诊查询条件
	$('#clearmenzhen').on('click', function(){
		$("#menzhen :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected");//核心
	     $(".searchSelect li.selected").empty();
	     $('.searchSelect').selectpicker("refresh");//初始化刷新--2019.10.25--licc	
    });
	function change(){
		usercode="";//重置挂号页面传入的usercode
	}
	//挂号
	$('#guahao1').on('click', function(){
		if(clickrow == "" || clickrow == null){
			layer.alert('请选择患者', {icon : 6});
			return false;
		}
		layer.open({
	        type: 2,
	        title: '就诊挂号',
	        shadeClose: true,
	        shade: 0.6,
	        area: ['950px', '525px'],
	        content:contextPath+'/KQDS_REGAct/toAddReg.act?usercode='+clickrow.usercode+'&orderno='+clickrow.seqId 
	    });
    });
	setHeight();
	
	$(window).resize(function(){
		/*页面大小重置时*/
		/*重新计算高度  */
		setHeight();
		/*产生滚动条时，对表头进行的修改 */
		setTableHeaderWidth(".tableBox1");
	});
	 $('.searchSelect').selectpicker("refresh");//咨询部门初始化刷新--2019-10-24 licc
});
function searchmenzhen(){
	 $('#table1').bootstrapTable('refresh',{'url':pageurl1}); 
}
//加载门诊表格
function inittablemenzhen(nums){
	$table1.bootstrapTable({
		url:pageurl1, 
		dataType: "json",
		queryParams:queryParamsB,
		pagination: true,//是否显示分页（*）
	    pageSize: 30,
	    pageList : [15, 20, 25,30],//可以选择每页大小
		singleSelect: false,
		striped: true,
		sidePagination: "server",//后端分页 
        paginationShowPageGo: true,
		onPageChange:function(){
			//加了分页器之后高度自适应
           	$(".fixed-table-container").height($(".fixed-table-container").height()+60+"px");
		},
		onLoadSuccess:function(){
			//加了分页器之后高度自适应
            $(".fixed-table-container").height($(".fixed-table-container").height()+60+"px");
			/*出现滚动条时，调整表头宽度*/
			setTableHeaderWidth(".tableBox1");
		},
		columns: [
			{
			    title : '序号',
			    align: "center",
			    width: 40,
			    formatter: function (value, row, index) {
			     /* return index + 1; */
			     var pageSize = $('#table1').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
			        var pageNumber = $('#table1').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
			        return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
			    }
			   },
			 {
	            title: '患者编号',
	            field: 'usercode',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value != "" && value != null) {
	                    return '<span>' + value + '</span>';
	                } else {
	                    return '';
	                }
	            }
	        },
	        {
	            title: '患者姓名',
	            field: 'username',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value != "" && value != null) {
	                    return '<span class="name" title="' + value + '">' + value + '</span>';
	                } else {
	                    return '';
	                }
	            }
	        },
	        {
	            title: '手机号',
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
	            title: '第一咨询',
	            field: 'firstaskperson',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    html = '<span class="name" title="' + value + '">' + value + '</span>';
	                    return html;
	                } else {
	                    return "";
	                }
	            }
	        },
	        {
	            title: '咨询/医生',
	            field: 'askperson',
	            align: 'center',
	            sortable: true,
	            formatter:function(value,row,index){  
			    	  if(value){
			        	    html = '<span class="name" title="'+value+'">'+value+'</span>';
			                return html;  
			    	  }else{
			    		  return "";
			    	  }
		  		}
	        },
	        {
	            title: '项目分类',
	            field: 'orderitemtype',
	            align: 'center',
	            sortable: true,
	            formatter:function(value,row,index){  
			    	  if(value){
			        	    html = '<span class="name" title="'+value+'">'+value+'</span>';
			                return html;  
			    	  }else{
			    		  return "";
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
	                    return '<span style="color:green">已上门</span>';
	                } else {
	                    return '<span style="color:red">未上门</span>';
	                }
	            }
	        },
	        {
	            title: '本次上门',
	            field: 'orderstatus',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value == "已上门") {
	                    return '<span style="color:green">已上门</span>';
	                } else {
	                    return '<span style="color:red">未上门</span>';
	                }
	            }
	        },
	        {
	            title: '本次成交',
	            field: 'cjstatus',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, s, index) {
	                if (value == "已成交") {
	                    return '<span style="color:green">已成交</span>';
	                } else {
	                    return '<span style="color:red">未成交</span>';
	                }
	            }
	        },
	        {
	            title: '开始时间',
	            field: 'starttime',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value != "" && value != null) {
	                    return '<span class="time" title="' + value + '">' + value + '</span>';
	                } else {
	                    return '';
	                }
	            }
	        },
	        {
	            title: '结束时间',
	            field: 'endtime',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value != "" && value != null) {
	                    return '<span class="time" title="' + value + '">' + value + '</span>';
	                } else {
	                    return '';
	                }
	            }
	        },
	        {
	            title: '到诊时间',
	            field: 'ordertime',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value != "" && value != null) {
	                    return '<span class="time" title="' + value + '">' + value + '</span>';
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
	            formatter:function(value,row,index){  
			    	  if(value){
			        	    html = '<span class="name" title="'+value+'">'+value+'</span>';
			                return html;  
			    	  }else{
			    		  return "";
			    	  }
		  		}
	        },
	        {
	            title: '建档时间',
	            field: 'jdsj',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value != "" && value != null) {
	                    return '<span class="time" title="' + value + '">' + value + '</span>';
	                } else {
	                    return '';
	                }
	            }
	        },
	        {
	            title: '预约状态',
	            field: 'isdelete',
	            align: 'center',
	            sortable: true
	        }
		]
	}).on('click-row.bs.table', function (e, row, element){
		$('.success').removeClass('success');//去除之前选中的行的，选中样式
   		$(element).addClass('success');//添加当前选中的 success样式用于区别
   		var index = $table1.find('tr.success').data('index');//获得选中的行
   		clickrow =  $table1.bootstrapTable('getData')[index];
	});
}
//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height();
    $('#table1').bootstrapTable('resetView', {
    	height: baseHeight - $(".searchModule").outerHeight()-9
    });
  //加了分页器之后高度自适应
    $(".fixed-table-container").height($(".fixed-table-container").height()+60+"px");
}
</script>
</body>
</html>