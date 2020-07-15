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
/* 查询条件标签样式 */
.queryText{
	height:30px;
	width:65px;
	line-height:30px;
	text-align:center;
	font-size:14px;
	color:#fff;
	background: #00A6C0;
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
	
	/* 解決标签查询中下拉框悬浮   */
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
			<td id="tdwd" style="text-align:right;">
				<a id="guahao2" href="javascript:void(0);" class="kqdsCommonBtn">挂号</a>
				<a id="clearwangdian" href="javascript:void(0);" class="kqdsCommonBtn">清空</a>
				<a id="searchwangdian" onclick="searchwangdian();"  href="javascript:void(0);" class="kqdsSearchBtn">查询</a>
			</td>
		</tr>
	</table>
</div> -->
	<div class="" id="wangdian">
		<%-- <div class="register-bottom">
		<table class="kqds_table">
		    		<tr>
		                <td style="text-align:right;">预约时间：</td>
		    			<td style="text-align:left;width: 125px;"> 
		    					<span class="unitsDate">
		    						<input type="text"  placeholder="开始时间" class="dataTimewangdian" id="wstarttime"  style="width: 120px;">
		                        </span>
		                </td>
		    			
		    			<td style="text-align:right;">到：</td>
		    			<td style="text-align:left;">
								<span class="unitsDate">
		                           <input type="text"  placeholder="结束时间" class="dataTimewangdian" id="wendtime"  style="width: 120px;">
		                        </span>
						</td>
						<td style="text-align:right;">本次上门状态：</td>
		    			<td style="text-align:left;">
		    					<select name="worderstatus" id="worderstatus">
									<option value="">请选择</option>
				                	<option value="0">未上门</option>
				                	<option value="1">已上门</option>
								<select>	
		    			</td>
		    			<td style="text-align:right;">咨询项目：</td>
		    			<td style="text-align:left;">
		    					 <select class="dict" tig="ZXXM" name="xiangmu" id="xiangmu" ></select>
		    			</td>
		    			<td style="text-align:right;">科室：</td>
		    			<td style="text-align:left;">
								<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_0%>" name="wregdept" id="wregdept"></select>
						</td>
		    			
		    			<td style="text-align:right;">咨询：</td>
		    			<td style="text-align:left;">
		    					 <select name="waskperson" id="waskperson"></select>
		    					 <input type="hidden" name="checkperson2" id="checkperson2" placeholder="咨询人员" title="咨询人员" class="form-control" /> 
								 <input type="hidden"  onchange="changeAskPerson2()"   id="checkpersonDesc2" name="checkpersonDesc2" placeholder="咨询人员" readonly style="width: 120px;" ></input>	
							     <a onClick="javascript:select2()">高级选择</a>
		    			</td>
		    			<td style="text-align:right;">模糊查询：</td>
		    			<td style="text-align:left;"> 
		    					<input type="text" id="wusername" name="wusername" onchange="change();" value="<%=username%>" placeholder="患者姓名/手机" style="width: 100px;"/>
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
		            <a id="guahao2" href="javascript:void(0);" class="kqdsCommonBtn">挂号</a>
					<a id="clearwangdian" href="javascript:void(0);" class="kqdsCommonBtn">清空</a>
					<a id="searchwangdian" onclick="searchwangdian();"  href="javascript:void(0);" class="kqdsSearchBtn">查询</a>
		        </div>
	    		<ul class="conditions">
					<li>
						<span>预约时间</span>
   						<input type="text" style="font-size:11px;" placeholder="开始时间" class="dataTimewangdian" id="wstarttime">
		            </li>
		    		<li>
						<span>到</span>
                        <input type="text" style="font-size:11px;" placeholder="结束时间" class="dataTimewangdian" id="wendtime">
		           	</li>
		           	<li>
						<span>本次上门</span>
    					<select name="worderstatus" id="worderstatus">
							<option value="">请选择</option>
		                	<option value="0">未上门</option>
		                	<option value="1">已上门</option>
						<select>	
		    		</li>
		    		<li>
		    			<span>咨询项目</span>
		    			<select id="xiangmu" name="xiangmu" tig="ZXXM" class="dict searchSelect"  data-live-search="true" title="请选择"></select>
<!--    					 	<select class="dict" tig="ZXXM" name="xiangmu" id="xiangmu" ></select> -->
		    		</li>
		    		<li>
		    			<span>科室</span>
		    			<select id="wregdept" name="wregdept" tig="ZXXM" class="dept searchSelect"  tag="<%=ConstUtil.DEPT_TYPE_0%>"  data-live-search="true" title="请选择"></select>
<%-- 						<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_0%>" name="wregdept" id="wregdept"></select> --%>
					</li>
		    		<li>
		    			<span>咨询</span>
		    			<select id="waskperson" name="waskperson" class="searchSelect"  data-live-search="true" title="请选择"></select>
<!-- 	   					<select name="waskperson" id="waskperson"></select> -->
	   					<input type="hidden" name="checkperson2" id="checkperson2" placeholder="咨询人员" title="咨询人员" class="form-control" /> 
						<input type="hidden"  onchange="changeAskPerson2()"   id="checkpersonDesc2" name="checkpersonDesc2" placeholder="咨询人员" readonly>	
					    <a style="position:relative;top:3px;" onClick="javascript:select2()">高级选择</a>
		    		</li>
		    		<li>
		    			<span>模糊查询</span>
    					<input type="text" id="wusername" name="wusername" onchange="change();" value="<%=username%>" placeholder="患者姓名/手机" />
		            </li>
	    		</ul>
	    	</section>
	    </div>
		
	    <!--表格-->
    	<div class="tableBox">
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
var $table2 = $('#table2');
var pageurl2 = '<%=contextPath%>/KQDS_Net_OrderAct/selectNoPageYYZX.act';
var nowday;
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var usercode = "<%=usercode%>";
var clickrow = "";

function select2(){
	//获取部门的所有id
	var jzks = $("#wregdept option").map(function(){return $(this).val();}).get().join(",");
	jzks=jzks.replace(/^,+|,+$/g,'');
	deptid_select_user(['checkperson2', 'checkpersonDesc2'], jzks);
}

//(网电)人员树选择后 改变 两级下拉选
function changeAskPerson2(){
	var checkerson= $("#checkperson2").val();
	if(checkerson){
		//获取用户部门id
		var url = contextPath+ "/YZPersonAct/getDeptByUserSeqId.act?seqId="+checkerson;
		$.axse(url, null, function(data) {
			if(data.retState=="0"){
				var val =data.retData;
				//就诊科室赋值
				$("#wregdept").val(val);
				//手动触发 下拉选change事件
				$("#wregdept").trigger("change");
				//人员下拉选赋值
				$("#waskperson").val(checkerson);
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
            regdept:$("#wregdept").val(),
		    usercode:usercode,
		    xiangmu:$("#xiangmu").val(),
   	        askperson:$("#waskperson").val(),
   	        doorstatus:$("#worderstatus").val(),
   	    	starttime:$("#wstarttime").val(),
   			endtime:$("#wendtime").val(),
            username:$("#wusername").val()
    };
    return temp;
}

function queryParams2(params) {
    var temp = {  //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		    regdept:$("#wregdept").val(),
		    usercode:usercode,
		    xiangmu:$("#xiangmu").val(),
   	        askperson:$("#waskperson").val(),
   	        doorstatus:$("#worderstatus").val(),
   	    	starttime:$("#wstarttime").val(),
   			endtime:$("#wendtime").val(),
            username:$("#wusername").val()
    };
    return temp;
}	
$(function(){
	$("input[type='text']").attr("autocomplete","off");  //去掉input输入框的历史记录  lutian
	//获取当前日期
	if(usercode == ""){
		nowday = getNowFormatDate();
	 	$("#wstarttime").val(nowday);
	   	$("#wendtime").val(nowday);
	}
  
	//绑定两个时间选择框的chage时间
	$("#wstarttime,#wendtime").change(function(){
		timeCompartAndFz("wstarttime","wendtime");
    });
   	
	initDictSelectByClass(); // 预约项目、咨询项目
	//监听 onchange事件       根据部门选择人员
//     selectChangeTwo("wregdept","waskperson",1);
    selectChangeTwoSearch("wregdept", "waskperson",1);
    initDeptSelectByTypesAndClass('<%=ChainUtil.getCurrentOrganization(request)%>');
	//加载门诊预约表格
	inittablewangdian();
	//时间选择
	$(".dataTimewangdian").datetimepicker({
		language:  'zh-CN',  
		minView:2,
        autoclose : true,
		format: 'yyyy-mm-dd',
		pickerPosition: "bottom-right"
	});
	function change(){
		usercode="";//重置挂号页面传入的usercode
	}
	//清空网电查询条件
	$('#clearwangdian').on('click', function(){
		$("#wangdian :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected");//核心
		usercode="";//重置挂号页面传入的usercode
		$(".searchSelect li.selected").empty();
	    $('.searchSelect').selectpicker("refresh");//初始化刷新--2019.10.25--licc	
    });
	//挂号
	$('#guahao2').on('click', function(){
		if(clickrow == "" || clickrow == null){
			layer.alert('请选择患者预约记录', {icon : 6});
			return false;
		}
		layer.open({
	        type: 2,
	        title: '就诊挂号',
	        shadeClose: true,
	        shade: 0.6,
	        area: ['950px', '530px'],
	        content:contextPath+'/KQDS_REGAct/toAddReg.act?usercode='+clickrow.usercode+'&netorderid='+clickrow.seqId 
	    });
    });
	setHeight();
	
	$(window).resize(function(){
		/*页面大小重置时*/
		/*重新计算高度  */
		setHeight();
		/*产生滚动条时，对表头进行的修改 */
		setTableHeaderWidth(".tableBox");
	});
	 $('.searchSelect').selectpicker("refresh");//咨询部门初始化刷新--2019-10-25 licc
});
function searchwangdian(){
	 $("#searchwangdian").attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
	 $("#searchwangdian").text("查询中");
	 $('#table2').bootstrapTable('refresh',{'url':pageurl2}); 
}
//加载网电表格
function inittablewangdian(nums){
	$table2.bootstrapTable({
		url:pageurl2, 
		contentType : "application/x-www-form-urlencoded",   //必须有
		queryParams:queryParamsB,
		dataType: "json",
		singleSelect: false,
		striped: true,
		pagination: true,//是否显示分页（*）
		pageSize: 30,
		pageList : [15,25,50],//可以选择每页大小
		sidePagination: "server",//后端分页 
        paginationShowPageGo: true,
		onLoadSuccess:function(data){
			//解除查询按钮禁用 lutian
			if(data){
				$("#searchwangdian").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","auto").css("pointer-events","auto");
				$("#searchwangdian").text("查询");
			}
			/*出现滚动条时，调整表头宽度*/
			setTableHeaderWidth(".tableBox");
			//console.log("----"+JSON.stringify(data));
		},
		      columns: [
						{
						    title : '序号',
						    align: "center",
						    width: 40,
						    formatter: function (value, row, index) {
						     /* return index + 1; */
						     var pageSize = $('#table2').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
						        var pageNumber = $('#table2').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
						        return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
						    }
						   },
						{title: '患者编号',field: 'usercode',align: 'center',sortable: true,
							formatter:function(value,row,index){  
			                	  if(value!="" && value!=null){
				                      return '<span title="'+value+'">' + value + '</span>';
			                	  }else{
			                		  return '';
			                	  }
		                	}	
						},
						{title: '患者姓名',field: 'username',align: 'center',sortable: true,
							formatter:function(value,row,index){  
			                	  if(value!="" && value!=null){
				                      return '<span class="name" title="'+value+'">' + value + '</span>';
			                	  }else{
			                		  return '';
			                	  }
		                	}
						},
						{title: '手机号',field: 'phonenumber1',align: 'center',sortable: true,
			                formatter:function(value,row,index){
			              	    if(canlookphone == 0){
						            return '<span title="'+value+'">'+value+'</span>';
				            	}else{
				            		return '<span>-</span>';
				            	}
				     	 	}
						},
						{title: '咨询',field: 'askperson',align: 'center',sortable: true,
							formatter:function(value,row,index){  
			                	  if(value!="" && value!=null){
				                      return '<span class="name" title="'+value+'">' + value + '</span>';
			                	  }else{
			                		  return '';
			                	  }
		                	}
						},
						{title: '预约时间',field: 'ordertime',align: 'center',sortable: true,
			                  formatter:function(value,row,index){  
			                	  if(value!="" && value!=null){
				                      return '<span title="'+value+'">' + value + '</span>';
			                	  }else{
			                		  return '';
			                	  }
		                	  }
            			},
            			{title: '到诊时间',field: 'guidetime',align: 'center',sortable: true,
			                  formatter:function(value,row,index){  
			                	  if(value!="" && value!=null){
				                      return '<span title="'+value+'">' + value + '</span>';
			                	  }else{
			                		  return '';
			                	  }
		                	  }
        			    },
        			    {title: '上门状态',field: 'zdoorstatus',align: 'center',sortable: true,
			                  formatter:function(value,row,index){  
			                	  if(value=="已上门"){
									   return '<span style="color:green">已上门</span>';
								  }else{
									   return '<span style="color:red">未上门</span>';
								  }
			         	      } 
		                },
						{title: '本次上门',field: 'doorstatus',align: 'center',sortable: true,
			                  formatter:function(value,row,index){  
			                	  if(value=="已上门"){
									   return '<span style="color:green">已上门</span>';
								  }else{
									   return '<span style="color:red">未上门</span>';
								  }
			         	      } 
		                },
		                {title: '本次成交',field: 'cjstatus',align: 'center',sortable: true,
			                  formatter:function(value,row,index){  
			                		 if(value=="已成交"){
										 return '<span style="color:green">已成交</span>';
									 }else{
										 return '<span style="color:red">未成交</span>';
									 }
			         	      } 
		                },
		                {title: '咨询项目',field: 'askitem',align: 'center',sortable: true,
		                	formatter:function(value,row,index){  
			                	  if(value!="" && value!=null){
				                      return '<span class="name" title="'+value+'">' + value + '</span>';
			                	  }else{
			                		  return '';
			                	  }
		                	}
              			},
						{title: '网电咨询内容',field: 'askcontent',align: 'center',
  	  	  		              formatter:function(value,row,index){  
  		  	                  	  if(value!="" && value!=null){
  		  	                  		  return '<span class="remark" title="'+value+'">'+value+'</span>';
  		  	                  	  }else{
  		  	                  		  return '';
  		  	                  	  }
  		  	              	  }
                    	},
               			{title: '网电备注',field: 'remark',align: 'center',sortable: true,
 	  		                  formatter:function(value,row,index){  
 		                	  if(row.remark!="" && row.remark!=null){
 		                		  return '<span class="remark" title="'+value+'">' + value + '</span>';
 		                	  }else{
 		                		  return "";
 		                	  }
 	                	  }
               			},
               			{title: '建档人',field: 'jdr',align: 'center',sortable: true,
               				formatter:function(value,row,index){  
			                	  if(value!="" && value!=null){
				                      return '<span class="name" title="'+value+'">' + value + '</span>';
			                	  }else{
			                		  return '';
			                	  }
		                	}
            			},
            			{title: '建档时间',field: 'jdsj',align: 'center',sortable: true,
            				formatter:function(value,row,index){  
                          	    if(value!="" && value!=null){
            	                    return '<span class="time" title="'+value+'">' + value + '</span>';
                          	    }else{
                          		    return '';
                          	    }
                      	    }
            			}
		          ]
		  }).on('click-row.bs.table', function (e, row, element){
				$('.success').removeClass('success');//去除之前选中的行的，选中样式
		   		$(element).addClass('success');//添加当前选中的 success样式用于区别
		   		var index = $table2.find('tr.success').data('index');//获得选中的行
		   		clickrow =  $table2.bootstrapTable('getData')[index];
		  });
}
//计算左侧表格高度保证一屏展示
function setHeight() {
	var baseHeight = $(window).height();
    $('#table2').bootstrapTable('resetView', {
    	height: baseHeight - $(".searchModule").outerHeight()-9
    });
}
</script>
</body>
</html>