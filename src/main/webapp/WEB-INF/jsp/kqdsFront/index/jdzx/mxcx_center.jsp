<!--wl整理  -->
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
<title>明细查询</title>
 <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<!-- table数据表的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css"/>
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
</head>
<style type="text/css">
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
	.select2:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
	       width: 94px;   
	      }  
	.select2>.btn { 
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
	.searchWrap .formBox>section{
		height: 130px
	}
</style>
<body>
<div id="container">
    <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
				<div class="columnHd">
				    <span class="title">明细查询</span>
				</div>
                <div class="columnBd" style="border-bottom:1px solid #c0c0c0;">
                    <div class="tableBox" >
                        <table id="table" class="table-striped table-condensed table-bordered" data-height=""></table>
                    </div>
                </div>
                <div id="gongzuol">
	               <div class="columnBd">
		               	<ul class="dataCountUl" id="dataCount">
		               		<li>应收:<span id="ying"></span></li>
		               		<li>欠款:<span id="qian"></span></li>
		               		<li>赠送使用:<span id="zeng"></span></li>
		               		<li>实收:<span id="xian"></span></li>
		               		<li><%=SysParaUtil.getFkfsNameByCostField(request, "paydjq") %>:<span id="djq"></span></li>
		               		<li>积分:<span id="integral"></span></li>
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
	                	<span>
	                		高级查询
	                	</span>
	                </div>
	                <div class="formBox">
	                	<section>
				    		<ul class="conditions">
				    			<li>
				    				<span>所属门诊</span>
									<select id="organization" name="organization"></select>
								</li>
				    			<li>
				    				<span>收费时间</span>
		                            <input type="text" id="starttime" name="starttime" placeholder="开始日期" readonly class="birthDate">
				                </li>       
				    			<li>
				    				<span>到</span>
		                            <input type="text" id="endtime" name="endtime" placeholder="结束日期" readonly class="birthDate">
				                </li> 
								<li>
				    				<span>模糊查询</span>
				    				<input type="text" placeholder="患者编号/姓名/手机号" id="queryinput" name="queryinput">
				    			</li>
				    			</li>
				    			<li class="toggleTr">
				    				<span>治疗状态</span>
									<select id="zlstatus" name="zlstatus">
										<option value="" selected="selected">请选择</option>
										<option value="未治疗">未治疗</option>
										<option value="已治疗">已治疗</option>
									</select>
								</li>
				    			<li class="toggleTr">
				    				<span>治疗时间</span>
		                            <input type="text" id="zlstarttime" name="zlstarttime" placeholder="开始日期" readonly class="birthDate">
				                </li>       
				    			<li class="toggleTr">
				    				<span>到</span>
		                            <input type="text" id="zlendtime" name="zlendtime" placeholder="结束日期" readonly class="birthDate">
				                </li> 
				                <li class="toggleTr">
				    				<span>收款备注</span>
				    				<input type="text" placeholder="收款备注" id="remark" name="remark" >
				    			</li>
				    			<li class="toggleTr">
				    				<span>消费分类</span>
				    				<select id="basetype" name="basetype" class="select2 searchSelect"  data-live-search="true"  title="请选择"></select>
<!-- 									<select class="select2"  name="basetype" id="basetype"> -->
<!-- 	                 	  			</select> -->
								</li>
				    			<li class="toggleTr">
				    				<span title="二级消费分类">消费子类</span>
				    				<select id="nexttype" name="nexttype" class="select2 searchSelect" title="请选择"></select>
<!-- 			    					<select class="select2"  name="nexttype" id="nexttype"> -->
<!-- 		                       			<option value="">请选择</option> -->
<!-- 			                 	  	</select> -->
				    			</li>
				    			<li class="toggleTr">
				    				<span>消费项目</span>
				    				<input type="text" placeholder="消费项目" id="itemname" name="itemname">
				    			</li>
					    		<li class="toggleTr">
				    				<span>接诊咨询</span>
				    				<select id="askperson" name="askperson" class="patients-source select2 dict searchSelect"  data-live-search="true"  title="请选择"></select>
			    					<!-- <input type="hidden" name="askperson" id="askperson"  class="form-control"  value=""/>
									<input type="text"  id="askpersonDesc" name="askpersonDesc" placeholder="咨询" readonly  onClick="javascript:single_select_user(['askperson', 'askpersonDesc'],'',1);"></input> -->	
<!-- 				    				<select class="patients-source select2 dict"  title="咨询" placeholder="咨询" name="askperson" id="askperson"></select> -->
				    			</li>
				    			<li class="toggleTr">
				    				<span>就诊科室</span>
			    					<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_1 %>" name="regdept" id="regdept" ></select>
				    			</li>
				    			<li class="toggleTr">
				    				<span>医生</span>
				    				<select id="doctor" name="doctor" class="patients-source select2 dict searchSelect"  data-live-search="true"  title="请选择"></select>
			    					<!-- <input type="hidden" name="doctor" id="doctor"  class="form-control"  value=""/>
								    <input type="text"  id="doctorDesc" name="doctorDesc" placeholder="医生" readonly  onClick="javascript:single_select_user(['doctor', 'doctorDesc'],'',1);"></input> -->	
<!-- 				    				<select class="patients-source select2 dict"  title="医生" placeholder="医生" name="doctor" id="doctor"></select> -->
				    			</li>
				    			<li class="toggleTr">
				    				<span>护士</span>
				    				<input type="hidden" name="nurse" id="nurse"  class="form-control"  value=""/>
									<input  type="text"  id="nurseDesc" name="nurseDesc" placeholder="护士" readonly  onClick="javascript:single_select_user(['nurse', 'nurseDesc'],'',1);"></input>	
				    			</li>
					    		<li class="toggleTr" id="tool">
				    				<span>患者来源</span>
				    				<select id="devchannel" name="devchannel" class="patients-source select2 dict searchSelect"  data-live-search="true"  tig="hzly" title="请选择" onchange="getSubDictSelectSearch('devchannel','nexttype1');"></select>
<!-- 				    				<select class="patients-source select2 dict" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype1');"> -->
<!-- 									</select> -->
				    			</li>
				    			<li class="toggleTr" id="toolSon">
				    				<span>子分类</span>
				    				<select id="nexttype1" name="nexttype1" class="select2 dict searchSelect"  data-live-search="true"  title="请选择"></select>
<!-- 				    				<select class="select2 dict" name="nexttype1" id="nexttype1"> -->
<!-- 										<option value="">请选择</option> -->
<!-- 									</select> -->
				    			</li>
				    			<li class="toggleTr">
				    				<span>就诊分类</span>
				    				<select class=" dict" tig="jzfl" name="recesort" id="recesort"  data-bv-notempty data-bv-notempty-message="就诊分类不能为空">
			                		</select>
				    			</li>
				    			<li class="toggleTr">
				    				<span>挂号分类</span>
				    				<select class=" dict" tig="ghfl" name="regsort" id="regsort">
			                		</select>
				    			</li>
				    			<li class="toggleTr">
				    				<span>建档人</span>
			    					<input type="hidden" name="createuser" id="createuser"  class="form-control"  value=""/>
									<input  type="text"  id="createuserDesc" name="createuserDesc" placeholder="建档人" readonly  onClick="javascript:single_select_user(['createuser', 'createuserDesc'],'',1);"></input>	
				    			</li>
			    			</ul>
			    		</section>
			    		<div class="btnBar" id="bottomBarDdiv">
			               
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
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/Load.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/DataLazyLoad.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript">
var listbutton;
var contextPath = "<%=contextPath%>";
var onclickrowOobj = "";
var nowday;
var pageurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/getAll.act';
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var personrole = "<%=person.getUserPriv()%>";
var personroleother = "<%=person.getUserPrivOther()%>";

var loadperson='<%=person.getUserPriv()%>';
var load=loadperson.split(",");//登陆这权限
var allowedperson='<%=SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK)%>';
var allowed=allowedperson.split(",");//允许权限

$(function() {
    initHosSelectList4Front('organization'); // 连锁门诊下拉框
    initSysUserByDeptId($("#doctor"),"doctor"); //初始化医生选项下拉框
    initSysUserByDeptId($("#askperson"),"consultation"); //初始化咨询选项下拉框
    var tmpOrganization = $("#organization").val();
    initDeptSelectByTypesAndClass(tmpOrganization);
    $("#organization").change(function() {
        var currSelect = $(this).val();
        initDeptSelectByTypesAndClass(currSelect);
    });
    initDictSelectByClass(); // 患者来源、挂号分类、就诊分类
    initCostSortSelect1Level('basetype');//消费分类
    nowday = getNowFormatDate();
    //获取当前页面所有按钮
    getButtonAllCurPage("<%=menuid%>");
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
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    togglemodel.initial("mxcx",pageurl);
    //4、表格初始化
    OrderDetail();
    selectChangeTwoSearch("regdept", "doctor",1);
    
   
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
//计算界面宽高的设置
//setWidth() ,setHeight()函数移动到tableHeaderWidth.js

$('#basetype').change(function() {
	if($(this).val()){ // 如果一级有值，再请求
		//initCostSortSelect2Level('nexttype',this.value);
		initCostSortSelect2LevelSearch('nexttype',this.value);
		 $('.searchSelect').selectpicker("refresh");//搜索初始化刷新2019.10.21--licc
	}
});
function OrderDetail() {
	/* 根据当前页面 计算出table需要的高度 */
	
	var tableHeight=0;/* 计算table需要的高度 */
	/* 根据当前页面 计算出table需要的高度 */
	tableHeight=$(window).height()-5-50-$(".searchWrap").height()-$(".buttonBar").height()-$(".centerWrap .columnWrap .columnHd").height()-61;
	$(".tableBox").html("<table id='table' class='table-striped table-condensed table-bordered' data-height='"+tableHeight+"'></table>");
	
	/*wl----首次加载时 计算table高度————————————结束  */
    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList : [10, 15, 20, 25],//可以选择每页大小
        queryParams: queryParamsB,
        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
        paginationShowPageGo: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	
        	//判断登录
        	var existornot=isExist(total);
        	if(!existornot){
        		$('#table').bootstrapTable('hideColumn', 'devchannel');
        		$('#table').bootstrapTable('hideColumn', 'nexttype');
        		$('#tool').attr('class','toole').attr('style','display:none');
        		$('#toolSon').attr('class','toolSone').attr('style','display:none');
        		
        	}else{
        		/* console.log("-----------！！登录"); */
        	}
        	
            if(data.total>0){
       		    //maxpage = Math.floor(data.total/pagesize)+1; 
       		    $("#ying").html(data.ys);
                $("#xian").html(data.paymoney);
                $("#zeng").html(data.payother2);
                $("#qian").html(data.y2);
                $("#djq").html(data.paydjq); 
                $("#integral").html(data.payintegral); 
	      	 }
            if(data.total == 0){
       		 	$("#ying").html("0");
                $("#xian").html("0");
                $("#zeng").html("0");
                $("#qian").html("0");
                $("#djq").html("0"); 
                $("#integral").html("0"); 
      		 }
	      	//分页加载
	      	//showdata("table",data.rows);
	      	//计算主体的宽度
	        setWidth();
	        setHeight();
	      	/*表格载入时，设置表头的宽度 */
	        setTableHeaderWidth(".tableBox");
	      	//付款方式赋值
	        getFkfsField();
        },
        rowStyle: function(row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (row.istk == "1") {
                strclass = 'danger'; //还有一个active
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
        },{
            title: '消费门诊',
            field: 'organization',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span title="' + value + '">' + value + '</span>';
            }
        },{
            title: '收费时间',
            field: 'sftime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value.indexOf("null") > -1) {
                    return "<span></span>";
                } else {
                    var sftime = value.substring(0, 16);
                    return '<span>' + sftime + '</span>';
                }
            }
        },{
            title: '治疗状态',
            field: 'kaifa',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value=='已治疗') {
                    return "<span>已治疗</span>";
                } else {
                	return "<span>未治疗</span>";
                }
            }
        },{
            title: '治疗时间',
            field: 'zltime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	if (value) {
                    var sftime = value.substring(0, 16);
                    return '<span>' + sftime + '</span>';
                }
                return "";
            }
        },
        {
            title: '编号',
            field: 'usercode',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '病历号',
            field: 'blcode',
            align: 'center',
            valign: 'middle',
            sortable: true,
            visible: false,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '电话',
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
            title: '消费分类',
            field: 'classname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="remark" title="' + value + '">' + value + '</span>';
                } else {
                    return '<span class="remark"></span>';
                }
            }
        },
        {
            title: '二级消费分类',
            field: 'nextname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return '<span class="source"></span>';
                }
            }
        },
        {
            title: '消费项目',
            field: 'itemname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span style="width:140px;" class="remark" title="' + value + '">' + value + '</span>';
                } else {
                    return '<span style="width:140px;"></span>';
                }
            }
        },
        {
            title: '单位',
            field: 'unit',
            align: 'center',
            
            sortable: true,
		    formatter:function(value){
		    	return  '<span>'+value+'</span>';
		    }
        },
        {
            title: '数量',
            field: 'num',
            align: 'center',
            
            sortable: true,
		    formatter:function(value){
		    	return  '<span>'+value+'</span>';
		    }
        },
        {
            title: '折扣',
            field: 'discount',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">'+value+'</span>';
            }
        },
        {
            title: '小计',
            field: 'subtotal',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">' + value + '</span>';
            }
        },
        {
            title: '免除',
            field: 'voidmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">' + value + '</span>';
            }
        },
        {
            title: '应收', /** 应收=小计-免除  **/
            field: 'ys',
            align: 'center',
            /* 
            sortable: true, */
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="money">' + value + '</span>';
                } else {
                    return '<span class="money">0.0</span>';
                }
            }
        },
        {
            title: '欠费',
            field: 'y2',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">' + value + '</span>';
            }
        },
        {
            title: '赠送使用',
            field: 'payother2',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == null) {
                    return '<span class="money">0.0</span>';
                } else {
                    return '<span class="money">' + value + '</span>';
                }
            }
        },
        {title: '代金券',field: 'paydjq',align: 'center',sortable: true,
			formatter:function(value,row,index){
				if(value==null){
					return '<span class="money">0.0</span>' ;
				}else{
					return '<span class="money">'+value+'</span>' ;
				}
			}
		},
		{title: '积分使用',field: 'payintegral',align: 'center',sortable: true,
			formatter:function(value,row,index){
				if(value==null){
					return '<span class="money">0.0</span>' ;
				}else{
					return '<span class="money">'+value+'</span>' ;
				}
			}
		},
        {
            title: '实收',
            field: 'paymoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">' + value + '</span>';
            }
        },
        {
            title: '现金',
            field: 'payxj',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == null) {
                    return '<span class="money">0.0</span>';
                } else {
                    return '<span class="money">' + value + '</span>';
                }
            }
        },
        {
            title: '预交金使用',
            field: 'payyjj',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == null) {
                    return '<span class="money">0.0</span>';
                } else {
                    return '<span class="money">' + value + '</span>';
                }
            }
        },
        {
            title: '银行卡',
            field: 'paybank',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == null) {
                    return '<span class="money">0.0</span>';
                } else {
                    return '<span class="money">' + value + '</span>';
                }
            }
        },
        {
            title: '医保',
            field: 'payyb',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == null) {
                    return '<span class="money">0.0</span>';
                } else {
                    return '<span class="money">' + value + '</span>';
                }
            }
        },
        {
            title: '微信',
            field: 'paywx',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == null) {
                    return '<span class="money">0.0</span>';
                } else {
                    return '<span class="money">' + value + '</span>';
                }
            }
        },
        {
            title: '支付宝',
            field: 'payzfb',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == null) {
                    return '<span class="money">0.0</span>';
                } else {
                    return '<span class="money">' + value + '</span>';
                }
            }
        },
        {title: '么么贷',field: 'paymmd',align: 'center',sortable: true,
			formatter:function(value,row,index){
				if(value==null){
					return '<span class="money">0.0</span>' ;
				}else{
					return '<span class="money">'+value+'</span>' ;
				}
			}
		},
		{title: '百度分期',field: 'paybdfq',align: 'center',sortable: true,
			formatter:function(value,row,index){
				if(value==null){
					return '<span class="money">0.0</span>' ;
				}else{
					return '<span class="money">'+value+'</span>' ;
				}
			}
		},
        {
            title: '其他',
            field: 'payother1',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == null) {
                    return '<span class="money">0.0</span>';
                } else {
                    return '<span class="money">' + value + '</span>';
                }
            }
        },
        {
            title: '特殊项目',
            field: 'istsxm',
            align: 'center',
            valign: 'middle',
            sortable: true,
		    formatter:function(value){
		    	return  '<span>'+value+'</span>';
		    }
        },
        {
            title: '就诊分类',
            field: 'recesort',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '挂号分类',
            field: 'regsort',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '成交情况',
            field: 'cjstatus',
            align: 'center',
            sortable: true,
		    formatter:function(value){
		    	return  '<span>'+value+'</span>';
		    }
        },
        {
            title: '第一咨询',
            field: 'faskperson',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return '<span class="name">'+value+'</span>';
            }
        },
        {
            title: '接诊咨询',
            field: 'askperson',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return '<span class="name">'+value+'</span>';
            }
        },
        {
            title: '就诊科室',
            field: 'regdept',
            align: 'center',
            sortable: true,
		    formatter:function(value){
		    	return  '<span>'+value+'</span>';
		    }
        },
        {
            title: '医生',
            field: 'doctor',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return '<span class="name">'+value+'</span>';
            }
        },
        {
            title: '护士1',
            field: 'nurse',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return '<span class="name">'+value+'</span>';
            }
        },
        {
            title: '护士2',
            field: 'techperson',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return '<span class="name">'+value+'</span>';
            }
        },
        {
            title: '患者来源',
            field: 'devchannel',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '来源子分类',
            field: 'nexttype',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return '<span class="source"></span>';
                }
            }
        },
        {
            title: '开单人',
            field: 'kduser',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "<span class='name'></span>";
                }
            }
        },
        {
            title: '开单时间',
            field: 'kdtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span>' + value + '</span>';
                    return html;
                }
            }
        },
        {
            title: '介绍人',
            field: 'introducer',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "<span class='name'></span>";
                }
            }
        },
        {
            title: '开发人',
            field: 'developer',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "<span></span>";
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
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "<span class='name'></span>";
                }
            }
        },
        {
            title: '建档导医',
            field: 'jddy',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "<span class='name'></span>";
                }
            }
        },
        {
            title: '建档时间',
            field: 'jdtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    html = '<span title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "<span></span>";
                }
            }
        },
        {
            title: '收费人',
            field: 'sfuser',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "<span></span>";
                }
            }
        },
        {
            title: '收款备注',
            field: 'remark',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    html = '<span class="remark">' + value + '</span>';
                    return html;
                }
            }
        },
        {
            title: '修改缴费备注',
            field: 'detailremark',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    html = '<span class="remark">' + value + '</span>';
                    return html;
                }
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        var tab = $("#rightmenu").children("ul").children("li").eq(4);
        tab.attr({
            "class": "current"
        });
        var src = contextPath + "/KQDS_CostOrder_DetailAct/toCostDetail2.act?costno=" + onclickrowOobj.costno;
        tab.attr('src', src);
        tab.trigger("click");
    });
}
function queryParamsB(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)
        sortName:this.sortName,
       	sortOrder:this.sortOrder,
        organization: $('#organization').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        zlstarttime: $('#zlstarttime').val(),
        zlendtime: $('#zlendtime').val(),
        zlstatus: $('#zlstatus').val(),
        basetype: $('#basetype').val(),
        nexttype: $('#nexttype').val(),
        regdept:$('#regdept').val(),
        askperson: $('#askperson').val(),
        isyjjitem:0,
        //咨询
        doctor: $('#doctor').val(),
        //医生
        nurse: $('#nurse').val(),
        //护士1
        createuser: $('#createuser').val(),
        //建档人
        devchannel: $('#devchannel').val(),
        //患者来源
        nexttype1: $('#nexttype1').val(),
        //子分类
        recesort: $('#recesort').val(),
        //就诊分类
        regsort: $('#regsort').val(),
        //挂号分类
        remark : $('#remark').val(),
        //消费项目
        itemname : $('#itemname').val(),
        queryinput: $("#queryinput").val()
    };
    if(nowday!=null){
    	temp.starttime = nowday;
    	temp.endtime = nowday;
    }
    return temp;
}
function queryParams() {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        organization: $('#organization').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        zlstarttime: $('#zlstarttime').val(),
        zlendtime: $('#zlendtime').val(),
        zlstatus: $('#zlstatus').val(),
        basetype: $('#basetype').val(),
        nexttype: $('#nexttype').val(),
        regdept:$('#regdept').val(),
        askperson: $('#askperson').val(),
        isyjjitem:0,
        //咨询
        doctor: $('#doctor').val(),
        //医生
        nurse: $('#nurse').val(),
        //护士1
        createuser: $('#createuser').val(),
        //建档人
        devchannel: $('#devchannel').val(),
        //患者来源
        nexttype1: $('#nexttype1').val(),
        //子分类
        recesort: $('#recesort').val(),
        //就诊分类
        regsort: $('#regsort').val(),
        //挂号分类
        remark : $('#remark').val(),
        //消费项目
        itemname : $('#itemname').val(),
        queryinput: $("#queryinput").val()
    };
    if(nowday!=null){
    	temp.starttime = nowday;
    	temp.endtime = nowday;
    }
    return temp;
}
function searchHzda() {
	loadedData = [];
	nowpage = 0;
	nowday = null;
	var starttime = $('#starttime').val();
	var endtime = $('#endtime').val();
	var zlstarttime = $('#zlstarttime').val();
	var zlendtime = $('#zlendtime').val();
	var zlstatus = $('#zlstatus').val();
	var basetype = $('#basetype').val();
	var nexttype = $('#nexttype').val();
    var askperson = $('#askperson').val();
    var regdept = $('#regdept').val();
    var doctor = $('#doctor').val();
    var createuser = $('#createuser').val();
    var devchannel = $('#devchannel').val();
//     var nexttype = $('#nexttype').val();
    var nexttype1 = $('#nexttype1').val();
    var recesort = $('#recesort').val();
    var regsort = $('#regsort').val();
    var remark = $('#remark').val();
    var itemname = $('#itemname').val(); // 消费项目
    var queryinput = $('#queryinput').val();
    if (zlstarttime == "" && zlendtime == "" && zlstatus == "" && regdept == "" && starttime == "" && endtime == "" &&  
    		basetype == "" && nexttype == "" && queryinput == "" && askperson == "" && doctor == "" && createuser == "" && 
    		devchannel == "" && recesort == "" && regsort == "" && remark == "" && itemname == "") {
        layer.alert('请选择查询条件!' );
        return false;
    }
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}

$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#basetype").val("").trigger("change");
    $("#nexttype").val("").trigger("change");
    $("#nexttype1").val("").trigger("change");
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
});

function clean() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#basetype").val("").trigger("change");
    $("#nexttype").val("").trigger("change");
    $("#nexttype1").val("").trigger("change");
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
function getButtonPower() {
    var menubutton1 = "";
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "mxcx_scbb") {
            menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="exportTable();">生成报表</a>';
        }
    }
    menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn clean" onclick="clean()">清空</a>';
    menubutton1 += '<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="searchHzda()">查询</a>';
    $("#bottomBarDdiv").append(menubutton1);
    setHeight();
}
</script>
</body>
</html>
