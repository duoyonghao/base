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
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/Load.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/DataLazyLoad.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<title>欠费查询</title>
<style type="text/css">
	.kqds_table  td { 
		color: #666;
		padding: 2px 3px 3px 3px;
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
		font-weight: normal;
		line-height: 28px;
	}
	
	.kqds_table  select { 
		height: 28px;
		width:90px;
		border: solid 1px #e5e5e5;
		border-radius: 3px;
	}
	input[type=text],.kv .kv-v input[type=text]{padding:0 10px;width:90px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}
	.pagination li{
		margin-left: 0px!important;
		height:auto!important;
	}
	.dropdown-menu{
		min-width: auto!important;
	    padding: 0px 0!important;
	}
	.dropdown-menu li{
		margin-left: 0px!important;
	}
	
	
	/* 搜索框select */
	.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
	       width: 110px;   
	      }  
	.searchSelect>.btn { 
		    width: 110px; 
		 	height:28px; 
		 	border: solid 1px #e5e5e5;
		} 
	.bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
		    color: #999;
		    height: 28px;
		}
	.pull-left {
	    float: left !important;
	    color: #666;
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
	.searchWrap , .formBox{
     overflow: visible; 
	}
	.kqds_table td{
		overflow:visible;
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
				    <span class="title">欠费查询</span>
				</div>
                
                <div class="columnBd" style="padding-bottom:0;border-bottom:1px solid #c0c0c0;">
                    <div class="tableBox">
                        <table id="table" class="table-striped table-condensed table-bordered" data-height="370"></table>
                    </div>
                </div>
                <div id="gongzuol">
	                <div class="columnBd">
	                	<ul class="dataCountUl" id="dataCount">
		               		<li>小计:<span id="xiaoji">0</span></li>
		               		<li>免除:<span id="mian">0</span></li>
		               		<li>应收:<span id="ying">0</span></li>
		               		<li>欠款:<span id="qian">0</span></li>
		               		<li>缴费:<span id="xian">0</span></li>
		               	</ul>
	                </div>
	            </div>
            </div>
            <div class="searchWrap">
	                <!-- <div class="cornerBox">查询条件</div> -->
	                <div class="searchToggleBtnGroup">
	                	<span class="ptcx checked">
	                		通用查询
	                	</span>
	                	<!-- <span>
	                		高级查询
	                	</span> -->
	                </div>
	                <div class="formBox">
	                   <table class="kqds_table">
				    		<tr>
				    			<td style="text-align:right;">所属门诊</td>
				    			<td style="text-align:left;">
										<select id="organization" name="organization"></select>
								</td>
				    			<td style="text-align:right;">截止时间</td>
				    			<td style="text-align:left;">
										<span class="unitsDate">
				                            <input type="text" id="endtime" name="endtime" placeholder="结束日期" readonly class="birthDate">
				                        </span>
								</td>
				    			<td style="text-align:right;">咨询</td>
				    			<td style="text-align:left;">
				    					 <input type="hidden" name="askperson" id="askperson"  class="form-control"  value=""/>
										 <input type="text"  id="askpersonDesc" name="askpersonDesc" placeholder="咨询" readonly  onClick="javascript:single_select_user(['askperson', 'askpersonDesc'],'',1);"></input>	
				    			</td>
				    			<td style="text-align:right;">建档人</td>
				    			<td style="text-align:left;">
				    					 <input type="hidden" name="createuser" id="createuser"  class="form-control"  value=""/>
									     <input  type="text"  id="createuserDesc" name="createuserDesc" placeholder="建档人" readonly  onClick="javascript:single_select_user(['createuser', 'createuserDesc'],'',1);"></input>	
				    			</td>
				    		</tr>
				    		<tr>
				    			<%
								if(SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK).indexOf(SessionUtil.getLoginPerson(request).getUserPriv()) == -1) {
								%>
								
								<%}else{ %>
				    			<td style="text-align:right;">患者来源</td>
				    			<td style="text-align:left;">
				    				<select id="devchannel" name="devchannel" tig="hzly" class="patients-source select2 dict searchSelect"  data-live-search="true" title="请选择" onchange="getSubDictSelectSearch('devchannel','nexttype');"></select>
<!-- 				    				<select class="patients-source select2 dict" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype');"> -->
<!-- 									</select> -->
				    			</td>
				    			
				    			<td style="text-align:right;">子分类</td>
				    			<td style="text-align:left;">
				    				<select id="nexttype" name="nexttype" tig="" class="select2 dict searchSelect"  data-live-search="true" title="请选择"></select>
<!-- 				    				<select class="select2 dict" name="nexttype" id="nexttype"> -->
<!-- 										<option value="">请选择</option> -->
<!-- 									</select> -->
				    			</td>
				    			<%} %> 
				    			<td style="text-align:right;">模糊查询</td>
				    			<td style="text-align:left;">
				    				<input type="text" placeholder="患者编号/姓名/手机号" id="queryinput" name="queryinput" >
				    			</td>
				    		</tr>
				    	</table>
				    	 <div class="btnBar" id="bottomBarDdiv" style="text-align: left;">
			                 <a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
			                 <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
			            </div>
	                </div>
	            </div>
        </div>
        <!--中间模块开关按钮  -->
        <div class="middleWrap">
			<div id="collectBtn" class="collectBtn">
				<span id="trangle"></span>
			</div>	
		</div>
        <!--右侧信息浏览-->
        <div class="lookInfoWrap">
			<%@include file="/inc/rightPartInfo.jsp" %>
		</div>
    </div>
</div>
<script type="text/javascript">
var listbutton;
var contextPath = "<%=contextPath%>";
var onclickrowOobj="";
var nowday;
var pageurl = '<%=contextPath%>/KQDS_CostOrderAct/qfSearch.act'; 
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var personrole  = "<%=person.getUserPriv()%>";
var personroleother  = "<%=person.getUserPrivOther()%>";
var menuid = "<%=menuid%>";
var qxnameArr = ['bbzx_qfcx_scbb'];
var func = ['exportTable'];

//登录权限licc--2020-1-8
var loadperson='<%=person.getUserPriv()%>';
var load=loadperson.split(",");//登陆这权限
var allowedperson='<%=SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK)%>';
var allowed=allowedperson.split(","); //允许权限

//判断当前人员权限是否有查看患者来源和子分类等着资源
var total=load.concat(allowed);
function isExist(total) {
    var exist = {};
    for(var i in total) {
        if(exist[total[i]]) {
            return true;
        }
        exist[total[i]] = true;
    }
    return false;
}


$(function(){
	initHosSelectList4Front('organization');// 连锁门诊下拉框	
	initDictSelectByClass(); // 患者来源
	nowday = getNowFormatDate();
	$("#endtime").val(nowday);
    //获取当前页面所有按钮
    getButtonAll(menuid);
    getButtonPowerByPriv(qxnameArr,func,menuid);
	/* 左侧个人中心的按钮样式控制js已经被抽取到rightPartInfo.jsp页面中 */

    //时间选择
    $(".birthDate").datetimepicker({
    	language:  'zh-CN',  
        format: 'yyyy-mm-dd',
        minView:2,
        autoclose : true,//选中之后自动隐藏日期选择框   
        pickerPosition: "top-right"
    });
    togglemodel.initial("qfcx",pageurl);/*wl 初始化 开关模块 */
     //4、表格初始化
	initTable();
    $(window).resize(function () {
        setWidth();
        setHeight();
        
        /*滚动条出现时，调整表头  */
        setTableHeaderWidth(".tableBox");
    });
    $('.searchSelect').selectpicker("refresh");//初始化刷新--2019-10-30 licc
});

function initTable(status,type){
	/*wl----首次加载时 计算table高度  */
	
	var tableHeight=0;/* 计算table需要的高度 */
	/* 根据当前页面 计算出table需要的高度 */
	tableHeight=$(window).height()-5-20-$(".searchWrap").height()-$("#gongzuol").height()-$(".centerWrap .columnWrap .columnHd").height()-58;
	$(".tableBox").html("<table id='table' class='table-striped table-condensed table-bordered' data-height='"+tableHeight+"'></table>");
	
	/*wl----首次加载时 计算table高度————————结束  */
	
	$('#table').bootstrapTable({
    		url:pageurl, 
    		dataType: "json",
    		queryParams:queryParams,
    		pagination: true,//是否显示分页（*）
            pageSize: 25,
            pageList : [10, 15, 20, 25],//可以选择每页大小
            singleSelect: false,
	        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
	        paginationShowPageGo: true,
    		onLoadSuccess: function(data){  //加载成功时执行
//             	隐藏患者来源子分类
				var existornot=isExist(total);//资源隐藏判断条件ZY_LYCK
            	if(!existornot){
            		$('#table').bootstrapTable('hideColumn', 'devchannel');
            		$('#table').bootstrapTable('hideColumn', 'nexttype');
            	}else{
            	}  
	     	   	 /* if(nowpage == 0 && data.total>0){
	        		 maxpage = Math.floor(data.total/pagesize)+1; 
	        		 $("#xiaoji").html(data.totalcost);
	                 $("#mian").html(data.voidmoney);
	                 $("#ying").html(data.shouldmoney);
	                 $("#xian").html(data.actualmoney);
	                 $("#qian").html(data.y2); 
	        	 } */
	     	   	 if(data.total>0){
	        		 //maxpage = Math.floor(data.total/pagesize)+1; 
	        		 $("#xiaoji").html(data.totalcost);
	                 $("#mian").html(data.voidmoney);
	                 $("#ying").html(data.shouldmoney);
	                 $("#xian").html(data.actualmoney);
	                 $("#qian").html(data.y2); 
	        	 }
	        	 if(data.total == 0){
	        		 $("#xiaoji").html("0");
	                 $("#mian").html("0");
	                 $("#ying").html("0");
	                 $("#xian").html("0");
	                 $("#qian").html("0"); 
	        	 }
	        	 //分页加载
	        	 //showdata("table",data.rows);
	        	 var tableList = data.rows;
	        	 setHeight();
	        	/*滚动条出现时，调整表头  */
	 	        setTableHeaderWidth(".tableBox");
	 	     	//加了分页器之后高度自适应
	           	$(".fixed-table-container").height($(".fixed-table-container").height()-70+"px");
    	    },
    	  	//点击分页器触动方法
            onPageChange:function(){
            	//加了分页器之后高度自适应
            	$(".fixed-table-container").height($(".fixed-table-container").height()-70+"px");
            },
    	    rowStyle: function (row, index) {
                //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
                var strclass = "";
                if (Number(row.actualmoney) <0 || row.actualmoney.indexOf("-")>=0) {
                    strclass = 'danger';//还有一个active
                } else {
                    return {};
                }
                return { classes: strclass };
            },
    		      columns: [
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
							{title: '编号',field: 'usercode',align: 'center',sortable: true,
								   formatter:function(value,row,index){
							            return '<span class="time" title="'+value+'">'+value+'</span>';
									}
							},
							{title: '姓名',field: 'username',align: 'center',sortable: true,
								   formatter:function(value,row,index){
		   					            return '<span class="name" title="'+value+'">'+value+'</span>';
	      	 						}	  
							},
							{title: '电话',field: 'phonenumber1',align: 'center',
	   					            formatter:function(value,row,index){
	   					            	if(canlookphone == 0){
		   					            	return '<span title="'+value+'">'+value+'</span>';
	   					            	}else{
	   					            		return '<span>-</span>';
	   					            	}
	   					            
	      	 						}
		           			},
		           		    {title: '第一咨询',field: 'askperson',align: 'center',sortable: true,
	 							 formatter:function(value,row,index){
									 if(value){
										 return '<span class="name" title="'+value+'">'+value+'</span>';
									 }else{
										 return '';
									 }
			                	 }  
	 					   }, 
			     		   {title: '小计',field: 'totalcost',align: 'center',sortable: true,
	   		     				formatter:function(value,row,index){
	   		     					return '<span class="money">'+value+'</span>' ;
		                	    	}
   		     			   },
	   		     		   {title: '免除',field: 'voidmoney',align: 'center',sortable: true,
	   		     				formatter:function(value,row,index){
	   		     					return '<span class="money">'+value+'</span>' ;
		                	    }
	   		     			},
    		     		   {title: '应收',field: 'shouldmoney',align: 'center',sortable: true,
    		     				formatter:function(value,row,index){
    		     					return '<span class="money">'+value+'</span>' ;
	                	    	}
    		     			},
    		     			{title: '欠费',field: 'y2',align: 'center',sortable: true,
    		     				formatter:function(value,row,index){
    		     					return '<span class="money">'+value+'</span>' ;
	                	    	}
    		     			},
    		     			{title: '缴费',field: 'actualmoney',align: 'center',sortable: true,
    		     				formatter:function(value,row,index){
    		     					return '<span class="money">'+value+'</span>' ;
	                	    	}
    		     			},
    		     			{title: '患者来源',field: 'devchannel',align: 'center',sortable: true,
		 						  formatter:function(value,row,index){
										 if(value){
											 return '<span class="name" title="'+value+'">'+value+'</span>';
										 }else{
											 return '';
										 }
				                	 }  
			     		   }, 
			     		  {title: '来源子分类',field: 'nexttype',align: 'center',sortable: true,
			     			  formatter:function(value,row,index){
									 if(value){
										 return '<span class="name" title="'+value+'">'+value+'</span>';
									 }else{
										 return '';
									 }
			                	 }  
			     		   },
    	         			{title: '介绍人',field: 'introducer',align: 'center',
    	 						formatter:function(value,row,index){  
  	         					  if(value){
							        	    html = '<span class="name" title="'+value+'">'+value+'</span>';
							                return html;  
							    	  }else{
							    		  return "";
							    	  }
	 							 }
    	         			},
    	         			{title: '开发人',field: 'developer',align: 'center',
    	         				formatter:function(value,row,index){  
  	         					  if(value){
							        	    html = '<span class="name" title="'+value+'">'+value+'</span>';
							                return html;  
							    	  }else{
							    		  return "";
							    	  }
	 							 }
    	         			},
		           			{title: '建档人',field: 'jduser',align: 'center',sortable: true,
    	         				formatter:function(value,row,index){  
  	         					  if(value){
							        	    html = '<span class="name" title="'+value+'">'+value+'</span>';
							                return html;  
							    	  }else{
							    		  return "";
							    	  }
	 							 }
    	         			}
    		          ]
    		  }).on('click-row.bs.table', function (e, row, element){
		    		$('.success').removeClass('success');//去除之前选中的行的，选中样式
		       		$(element).addClass('success');//添加当前选中的 success样式用于区别
		       		var index = $('#table').find('tr.success').data('index');//获得选中的行
		       		onclickrowOobj =  $('#table').bootstrapTable('getData')[index];
		       		var tab = $("#rightmenu").children("ul").children("li").eq(4);
		       		tab.attr({
		       		    "class" : "current"
		       		});
		       		var src =contextPath+"/KQDS_CostOrder_DetailAct/toCostDetail2.act?costno="+onclickrowOobj.costno;
		       		tab.attr('src',src); 
		       		tab.trigger("click");
   			  });
    }
function queryParams() {
	 var temp = {  //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			  offset : 0,
		      limit :pagesize,
		      ispaging : 1,
		      starttime:$('#starttime').val(),
			  endtime:$('#endtime').val(),
			  askperson:$('#askperson').val(),//咨询
			  createuser:$('#createuser').val(),//建档人
			  devchannel:$('#devchannel').val(),//患者来源
			  nexttype:$('#nexttype').val(),//子分类
			  queryinput : $('#queryinput').val(),
			  organization : $("#organization").val()
	    };
	  return temp;
}
	$('#query').on('click', function(){
		// 点击查询的时候，先清空
		$("#xiaoji").html("0");
        $("#mian").html("0");
        $("#ying").html("0");
        $("#xian").html("0");
        $("#qian").html("0"); 
		
		loadedData = [];
		nowpage = 0;
		var askperson = $('#askperson').val();
		var createuser = $('#createuser').val();
		var devchannel = $('#devchannel').val();
		var nexttype = $('#nexttype').val();
		var starttime = $('#starttime').val();
		 var endtime = $('#endtime').val();
		 var queryinput = $('#queryinput').val();
		 if(
				 starttime == "" && endtime == "" && queryinput == "" && 
			 askperson == "" && createuser == "" && devchannel == "" && nexttype == "" 
		 ){
			 layer.alert('请选择查询条件!' );
			 return false;
		 }
		 $('#table').bootstrapTable('refresh',{'url':pageurl}); 
	});
	
	//清空
	$('#clean').on('click', function(){
		$(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected");//核心
		$("#nexttype").val("").trigger("change");
	    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
	    $(".searchSelect li.selected").empty();//清空
	    $('.searchSelect').selectpicker("refresh");//初始化刷新--2019.10.30--licc
	});
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
	
    //计算主体的宽度
    function setWidth() {
      var baseW = $(window).width() - 50;
      var innerHeight_1, innerHeight_2;
      var rightModelDisplay=$(".lookInfoWrap").css("display");/*获得当前右侧模块的状态 */
      if(rightModelDisplay=="none"){/*如果右侧模块没有显示*/
      	$(".centerWrap").width(baseW);/*如果左侧模块占满*/
      }else{
      	$('.centerWrap,.lookInfoWrap').width(Math.floor(baseW / 2));/*如果左右模块各一半*/
      }
      $('.operateModel .optBox').width(($('.centerWrap').width()-140)/2);
      (innerHeight_1=$('.operateModel .optBox:eq(0)').height()) > (innerHeight_2 = $('.operateModel .optBox:eq(1)').height()) ? $('.operateModel .optBox:eq(1)').height(innerHeight_1)  :   $('.operateModel .optBox:eq(0)').height(innerHeight_2);
    }
    
    //计算左侧表格高度保证一屏展示
    function setHeight() {
        var baseHeight = $(window).height() - 15, 
    	serachH = $('.searchWrap').outerHeight();
    	
    	$('.lookInfoWrap .columnWrap').height(baseHeight);
    	$('.lookInfoWrap .columnWrap .columnBd').height(baseHeight - 45);
    	$('.lookInfoWrap .columnWrap .columnBd .tabContainer').height(baseHeight - 45);
    	
    	
    	$('.centerWrap .columnWrap').height(baseHeight  - serachH -10 );
    	/* $('.centerWrap .columnWrap .columnBd').height($('.centerWrap .columnWrap').height() - $('.centerWrap .columnWrap .columnHd').height() - 20); */
    	$(".fixed-table-container").height($('.centerWrap .columnWrap').outerHeight()-$(".columnWrap .columnHd").outerHeight()-63); 
    	 $('.tabIframe').height(baseHeight - 40);
    	//wl 设置 关闭右侧模块按钮"collectBtn"
        $(".middleWrap").outerHeight(baseHeight-2);//"collectBtn"按钮的容器
        $("#collectBtn").css("marginTop",baseHeight/2-50);//wl 垂直位置
    }
    function getButtonPower() {
        var menubutton1 = "";
        for (var i = 0; i < listbutton.length; i++) {
            if (listbutton[i].qxName == "qfcx_scbb") {
                menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="exportTable();">生成报表</a>';
            } 
        }
        $("#clean").before(menubutton1);
        setHeight();
    }
    
</script>
</body>
</html>
