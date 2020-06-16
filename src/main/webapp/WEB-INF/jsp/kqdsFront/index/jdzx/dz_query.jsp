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
	Object isyx = request.getAttribute("isyx");
	if(isyx == null){
		isyx = "0";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>到诊查询</title>
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
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>

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
.centerWrap .columnWrap .columnBd ul{
	overflow: visible;
}
.centerWrap .columnWrap .columnBd ul li{
	margin-left: 0px;
}
.fixed-table-pagination .btn-group .dropdown-menu{
	min-width: auto;
}
	/* 搜索框select */
	.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
	       width: 94px;   
	      }  
	.searchSelect>.btn { 
		    width: 94px; 
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
</style>

</head>
<body>
<div id="container">
       <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
				<div class="columnHd">
				    <span class="title">到诊查询</span>
				</div> 
                <div class="columnBd">
                    <div class="tableBox" >
                        <table id="table" class="table-striped table-condensed table-bordered" data-height="430"></table>
                    </div>
                    <div id="gongzuol" >
			             <div class="columnBd">
			             	<ul class="dataCountUl" id="dataCount">
			             		<li>总记录数:<span id="zong">0</span></li>
			             		<li>成交数:<span id="cj">0</span></li>
			             		<li>未成交数:<span id="wcj">0</span></li>
			             	</ul>
			             </div>
			        </div>
                </div>
            </div>
            
            <div class="searchWrap" >
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
				    			<li>
				    				<span>到院门诊</span>
									<select id="organization" name="organization"></select>
								</li>
								<li>
				    				<span>挂号时间</span>
		    						<input type="text" id="starttime" name="starttime" placeholder="开始日期" readonly class="birthDate">
			                    </li>
				    			<li>
				    				<span>到</span>
	                              	<input type="text" id="endtime" name="endtime" placeholder="结束日期" readonly class="birthDate">
			                    </li>
								<li>
				    				<span>模糊查询</span>
				    				<input type="text" id="searchValue" class="searchInput" placeholder="姓名/手机号" > 
				    			</li>
				    			<li class="toggleTr" id="tool">
				    				<span>患者来源</span>
				    				<select id="devchannel" name="devchannel" class="patients-source select2 dict searchSelect"  data-live-search="true"  tig="hzly" title="请选择" onchange="getSubDictSelectSearch('devchannel','nexttype');"></select>
<!-- 				    				<select class="patients-source select2 dict" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype');"> -->
<!-- 									</select> -->
				    			</li>
				    			<li class="toggleTr" id="toolSon">
				    				<span>子分类</span>
				    				<select id="nexttype" name="nexttype" class="select2 dict searchSelect"  data-live-search="true"  title="请选择"></select>
<!-- 				    				<select class="select2 dict" name="nexttype" id="nexttype"> -->
<!-- 										<option value="">请选择</option> -->
<!-- 									</select> -->
				    			</li>
				    			<li class="toggleTr">
				    				<span>挂号分类</span>
			    					<select class="dict" tig="ghfl" name="regsort" id="regsort"></select>
				    			</li>
				    			<li class="toggleTr">
				    				<span>就诊分类</span>
				    				<select class="dict" tig="jzfl" name="recesort" id="recesort" ></select>
				    			</li>
				    			<li class="toggleTr">
				    				<span>受理类型</span>				    				
			    					<select class="dict" tig="SLLX" id="shouli" ></select>
				    			</li>
				    			<li class="toggleTr" id="accept">
				    				<span>受理工具</span>
				    				<select id="gongju" name="gongju" class="dict searchSelect"  data-live-search="true" tig="SLGJ" title="请选择"></select>
<!-- 			    					<select class="dict" tig="SLGJ" id="gongju" ></select> -->
				    			</li>
				    			<li class="toggleTr">
				    				<span>成交状态</span>
				    				<select  name="cjstatus" id="cjstatus" >
				                          <option value="">请选择</option>
				                          <option value="0">未成交</option>
				                          <option value="1">已成交</option>
									</select>
				    			</li>
				    			<!-- 
				    			<li class="toggleTr">
				    				<span>有无回访</span>
			                        <select id="ywhf">
		                        		<option value="">- 请选择 -</option>
		                        		<option value="0">无回访</option>
		                        		<option value="1">有回访</option>
		                        	</select>
				                </li> -->
				    		    <li class="toggleTr">
				    				<span>咨询</span>
				    				<select id="askpersonSearch" name="askpersonSearch" class="searchSelect"  data-live-search="true"  title="请选择"></select>
<!-- 			    					<select  name="askpersonSearch" id="askpersonSearch" ></select>	 -->
				    			</li>
				    			<li class="toggleTr">
				    				<span>医生</span>
				    				<select id="doctorSearch" name="doctorSearch" class="searchSelect"  data-live-search="true"  title="请选择"></select>
<!-- 			    					<select  name="doctorSearch" id="doctorSearch" ></select>	 -->
				    			</li>
				    			<li class="toggleTr">
				    				<span>建档人</span>
			    					<input type="hidden" name="createuserSearch" id="createuserSearch"  class="form-control"  value=""/>
								    <input  type="text"  id="createuserSearchDesc" name="createuserSearchDesc" placeholder="建档人" readonly  onClick="javascript:single_select_user(['createuserSearch', 'createuserSearchDesc'],'single',1);"></input>	
				    			</li>
			    			</ul>
			    		</section>
	                
	                    <div class="btnBar" id="bottomBarDdiv" style="text-align: left;">
	                     	 <a href="javascript:void(0);" class="kqdsCommonBtn clean" onclick="clean()">清空</a>
	                     	 <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="exportTable()">生成报表</a> 
	               		     <a href="javascript:void(0);" class="kqdsSearchBtn" id="query" onclick="searchHzda()">查询</a>
			            </div>
	                </div>
	            </div>
        </div>
        <!--右侧信息浏览-->
        <div class="lookInfoWrap">
			<%@include file="/inc/rightPartInfo.jsp" %>
		</div> 
    </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
</body>
<script type="text/javascript">
var listbutton;
var contextPath = "<%=contextPath%>";
var onclickrowOobj = "";
var pageurl = contextPath + '/KQDS_REGAct/selectDzQuery.act';
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var personrole = "<%=person.getUserPriv()%>";
var personroleother = "<%=person.getUserPrivOther()%>";
var canKd = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag2_canKd, request)%>';
var menuid = "<%=menuid%>";
var isyx = "<%=isyx%>";
var qxnameArr = ['rskcx_wd','xfmx_all_wd','wd_jrhz_scbb'];
var func = ['rskcx','xfmx','exportTable'];

var loadperson='<%=person.getUserPriv()%>';
var load=loadperson.split(",");//登陆这权限
var allowedperson='<%=SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK)%>';
var allowed=allowedperson.split(",");//允许权限

$(function() {
	initHosSelectListNoCheck('organization'); // 连锁门诊下拉框		
	//咨询 下拉列表
	initPersonSelectByDeptType("askpersonSearch","<%=ConstUtil.DEPT_TYPE_0 %>");
	//医生 下拉列表
	initPersonSelectByDeptType("doctorSearch","<%=ConstUtil.DEPT_TYPE_1 %>");
    getButtonPowerByPriv(qxnameArr,func,menuid);
	nowday = getNowFormatDate();
	$("#starttime").val(nowday);
	$("#endtime").val(nowday);
	
    initDictSelectByClass(); // 就诊分类、挂号分类
    //获取当前页面所有按钮
    getButtonAll("<%=menuid%>");
    /* 左侧个人中心的按钮样式控制js已经被抽取到rightPartInfo.jsp页面中 */
    //时间选择
    $(".birthDate").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "top-right"
    });
    togglemodel.initial('jrhz',pageurl);/*wl 初始化 开关模块 */
    //4、表格初始化
    initTable();
    
    $(window).resize(function() {
        setWidth();
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });
    /* 常用查询 按钮 高级查询  按钮*/
    initSearchToggleBtnGroup();
    $('.searchSelect').selectpicker("refresh");//搜索初始化刷新2019.10.21--licc
});

//判断当前人员权限
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

function initTable(status, type) {
    $(".tableBox").html('<table id="table" class="table-striped table-condensed table-bordered" data-height="200"></table>');
    $('#table').bootstrapTable({
        url: pageurl,
        queryParams: queryParamsB,
        dataType: "json",
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList : [10, 15, 20, 25],//可以选择每页大小
        //clickToSelect: false,
        singleSelect: false,
        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
        paginationShowPageGo: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	
        	//判断登录
        	var existornot=isExist(total);
        	if(!existornot){
        		$('#table').bootstrapTable('hideColumn', 'devchannelname');
        		$('#table').bootstrapTable('hideColumn', 'nexttypename');
        		$('#table').bootstrapTable('hideColumn', 'slgj');
        		$('#tool').attr('class','toole').attr('style','display:none');
        		$('#toolSon').attr('class','toolSone').attr('style','display:none');
        		$('#accept').attr('class','accepte').attr('style','display:none');
        	}else{
        		/* console.log("------------！！登录"); */
        	} 
        	
        	//console.log(data);
            var tableList = data.nums[0];
            $("#zong").html(data.total);
            /* var cjs = 0,
            wcjs = 0;
            for (var i = 0; i < tableList.length; i++) {
                if (tableList[i].cjstatus == "1") {
                    cjs = cjs + 1;
                } else {
                    wcjs = wcjs + 1;
                }
            } */
            $("#cj").html(tableList.cjstatus);
            $("#wcj").html(Number(data.total)-Number(tableList.cjstatus));
          	//计算主体的宽度
            setWidth();
            setHeight();
            /*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        },
        rowStyle: function(row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (Number(row.del) > 0) {
                strclass = 'active'; //还有一个active
            } else {
                return {};
            }
            return {
                classes: strclass
            };
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
			/* {
				    title: 'seqId',
				    field: 'seqId',
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
		        
		        {title: 'del',field: 'del',align: 'center',sortable: true,
		            formatter: function(value, row, index) {
		                return '<span>' + value + '</span>';
		            }
		        }, 
		           */
		        {
		        	title: '挂号时间',
		        	field: 'createtime',
		        	align: 'center',
		        	sortable: true,
		            formatter: function(value, row, index) {
		                return '<span title="' + value + '">' + value + '</span>';
		            }
		        },
		        {
		        	title: '就诊分类',
		        	field: 'recesortname',
		        	align: 'center',
		        	sortable: true,
		            formatter: function(value, row, index) {
		                return '<span class="name" title="' + value + '">' + value + '</span>';
		            }
		        },
		        {
		        	title: '挂号分类',
		        	field: 'regsortname',
		        	align: 'center',
		        	sortable: true,
		            formatter: function(value, row, index) {
		                if (value != null || value != "") {
		                    if (canlookphone == 0) {
		                        return '<span title="' + value + '">' + value + '</span>';
		                    } else {
		                        return '<span>-</span>';
		                    }
		                } else {
		                    return '<span>-</span>';
		                }
		            }
		        },
		        {
		        	title: '患者编号',
		        	field: 'usercode',
		        	align: 'center',
		        	sortable: true,
		        	formatter:function(value,row,index){
		        		return '<span>'+value+'</span>';
		        	}
		        	
		        },
		        {
		        	title: '姓名',
		        	field: 'username',
		        	align: 'center',
		        	sortable: true,
		            formatter: function(value, row, index) {
		                if (value == "0") {
		                    return '<span>-</span>';
		                } else {
		                    return '<span>'+value+'<span>';
		                }
		            }
		        },
		        {
		            title: '标识',
		            field: 'iscreatelclj',
		            align: 'center',
		            sortable: true,
		            formatter: function(value, row, index) {
		            	//console.log("value="+value+",row="+JSON.stringify(row)+",index="+index);
		            	 if (value != "" && value != null) {
		                    return '<img class="iscreatelclj" onclick="skip(\'' + row.username + '\')" src= ' +contextPath + '/static/image/kqdsFront/tag/clinical.jpg/>';
		                }
		            }
		        },
		        {
		        	title: '性别',
		        	field: 'sex',
		        	align: 'center',
		        	sortable: true,
		        },
		        {
		        	title: '年龄',
		        	field: 'age',
		        	align: 'center',
		        	sortable: true,
		        	 formatter: function(value, row, index) {
			                return '<span class="name">' + value + '</span>';
			         }
		        },
		        {
		        	title: '成交状态',
		        	field: 'cjstatus',
		        	align: 'center',
		        	sortable: true,
		            formatter: function(value, row, index) {
		            	var html = "";
		                if (value == "0") {
		                    html = '<span class="label label-danger">未成交</span>';
		                } else if (value == "1") {
		                    html = '<span class="label label-success">已成交</span>';
		                }
		                return html;
		            }
		        },
		        {
		        	title: '咨询',
		        	field: 'askpersonname',
		        	align: 'center',
		        	sortable: true,
		            formatter: function(value, row, index) {
		            	if (value) {
		                    return "<span class='name' title='" + value + "'>" + value + "</span>";
		                }
		            }
		        },
		        {
		        	title: '医生',
		        	field: 'doctorname',
		        	align: 'center',
		        	sortable: true,
		        	formatter: function(value, row, index) {
		        		if (value) {
		                    return "<span class='name' title='" + value + "'>" + value + "</span>";
		                }
			        }
		        },
		        {
		        	title: '修复医生',
		        	field: 'repairdoctorname',
		        	align: 'center',
		        	sortable: true,
		        	 formatter: function(value, row, index) {
		        		 if (value) {
		                     return "<span class='name' title='" + value + "'>" + value + "</span>";
		                 }
			         }
		        }, 
		        {
		        	title: '建档人',
		        	field: 'jduser',
		        	align: 'center',
		        	sortable: true,
		        	 formatter: function(value, row, index) {
		        		 if (value) {
		                     return "<span class='name' title='" + value + "'>" + value + "</span>";
		                 }
			         }
		        },
		        {
		        	title: '情况备注',
		        	field: 'remark',
		        	align: 'center',
		        	sortable: true,
		        	 formatter: function(value, row, index) {
		        		 return '<span>' + value + '</span>';
			         }
		        },
		        {
		        	title: '患者来源',
		        	field: 'devchannelname',
		        	align: 'center',
		        	sortable: true,
		        	 formatter: function(value, row, index) {
		        		 return '<span>' + value + '</span>';
			         }
		        },
		        {
		        	title: '来源子分类',
		        	field: 'nexttypename',
		        	align: 'center',
		        	sortable: true,
		        	formatter: function(value, row, index) {
		        	     return '<span>' + value + '</span>';
			        }
		        },
		        {	
		        	title: '受理工具',
		            field: 'slgj',
		            align: 'center',
		            
		            sortable: true,
		            formatter: function(value, row, index) {
		            	return '<span>' + value + '</span>';
		         	}
		        },
		        
		        {
		        	title: '病历',
		        	field: 'ifmedrecord',
		        	align: 'center',
		        	sortable: true,
		        	formatter: function(value, row, index) {
		        		 var html = "";
		                 if (row.ifmedrecord == "1") {
		                     html = '<span class="label label-success">已填写</span>';
		                 } else {
		                     html = '<span class="label label-danger">未填写</span>';
		                 }
		                 return html;
			         }
		        },
		        {
		        	title: '缴费',
		        	field: 'ifcost',
		        	align: 'center',
		        	sortable: true,
		        	formatter: function(value, row, index) {
		        		 var html = "";
		                 if (row.ifcost == "1") {
		                     html = '<span class="label label-success">已缴费</span>';
		                 } else {
		                     html = '<span class="label label-danger">未缴费</span>';
		                 }
		                 return html;
			         }
		        },
		        {
		        	title: '挂号人员',
		        	field: 'createusername',
		        	align: 'center',
		        	sortable: true,
		            formatter: function(value, row, index) {
		            	if (value) {
		                    return "<span class='name' title='" + value + "'>" + value + "</span>";
		                }
		            }
		        },
		        {
		        	title: '挂号导医',
		        	field: 'receivenoname',
		        	align: 'center',
		        	sortable: true,
		            formatter: function(value, row, index) {
		            	if (value) {
		                    return "<span class='name' title='" + value + "'>" + value + "</span>";
		                } else {
		                    return '<span>-</span>';
		                }
		            }
		        }
        ]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        //showpersoninfo(onclickrowOobj);//展示右侧个人信息
        $('#tabIframe').attr('src', $('#tabIframe').attr('src')); //个人中心
    });
}
function queryParamsB(params) {
	/* var depttype = '';
	if(isyx == 1){ // 1 是营销
		depttype = 3;
	}
	if(isyx == 2){ // 2 是网电
		depttype = 2;
	} */
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        //可以查询挂号表医生 咨询不是自己，收费项目种有自己的
        //depttype:depttype,//2 网电 3 营销  空 是客服
        limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)
        sortName:this.sortName,
    	sortOrder:this.sortOrder,
        ywhf: $('#ywhf').val(),
        organization: $('#organization').val(),
        doctorSearch: $('#doctorSearch').val(),
        askpersonSearch: $('#askpersonSearch').val(),
        regsort: $('#regsort').val(),
        cjstatus: $('#cjstatus').val(),
        recesort: $('#recesort').val(),
        searchValue: $('#searchValue').val(),
        createuserSearch: $('#createuserSearch').val(),
        starttime: $('#starttime').val(),
        shouli: $('#shouli').val(),
        gongju: $('#gongju').val(),
        devchannel: $('#devchannel').val(),
        nexttype: $('#nexttype').val(),
        endtime: $('#endtime').val()
    };
    return temp;
}

function queryParams() {
	/* var depttype = '';
	if(isyx == 1){ // 1 是营销
		depttype = 3;
	}
	if(isyx == 2){ // 2 是网电
		depttype = 2;
	} */
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        //可以查询挂号表医生 咨询不是自己，收费项目种有自己的
        //depttype:depttype,//2 网电 3 营销  空 是客服
        ywhf: $('#ywhf').val(),
        organization: $('#organization').val(),
        doctorSearch: $('#doctorSearch').val(),
        askpersonSearch: $('#askpersonSearch').val(),
        regsort: $('#regsort').val(),
        cjstatus: $('#cjstatus').val(),
        recesort: $('#recesort').val(),
        searchValue: $('#searchValue').val(),
        createuserSearch: $('#createuserSearch').val(),
        starttime: $('#starttime').val(),
        shouli: $('#shouli').val(),
        gongju: $('#gongju').val(),
        devchannel: $('#devchannel').val(),
        nexttype: $('#nexttype').val(),
        endtime: $('#endtime').val()
    };
    return temp;
}
function searchHzda() {
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}

function clean() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#regsort").val("").trigger("change");
    $("#cjstatus").val("").trigger("change");
    $("#devchannelSearch").val("").trigger("change");
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
    
//  清空搜索下拉框
	$(".searchSelect li.selected").removeClass("selected");
  	$(".searchSelect button .pull-left").text("请选择");    
}

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

//跳转临床页面
function skip(username) {
	parent.Catalogue()
	 parent.secondLevelDirectory()
	 window.location.href=contextPath+'/ClinicPathControllerAct/toLcljPageFetchInfo.act?menuId=600309&&username='+username;
}

//计算界面宽高的设置
//setWidth() ,setHeight()函数移动到tableHeaderWidth.js

</script>
</html>
