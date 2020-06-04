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
<title>处方查询</title><!-- 医疗中心 病历查询 菜单  -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/chufang/chufang.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<style>
	.fixed-table-container thead th .sortable{/*覆盖样式 表头右边距导致无法居中的问题  */
		padding-right:8px;
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
.fixed-table-pagination{
	border-top: 1px solid #ddd;
}

	/* 搜索框select */
	.createuser:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
	       width: 110px;   
	      }  
	.createuser>.btn { 
		    width: 110px; 
		 	height:26px; 
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
	<div class="chufangContainer">
		<div id="dynamicDiv" class="dynamicDiv">
			<!--处方查询  -->
			<div class="titleText">
				<span class="title">处方查询</span>
			</div>
			<!--数据表  -->
			<div class="tableBox">
                <table id="table" class="table-striped table-condensed table-bordered" data-height="300"></table>
            </div>
		</div>
		<!--查询条件  -->
		<div class="searchModule">
	        <input type="hidden" id="righttype" value="">
	    	<header>
	    		<span>查询条件</span>
	    		<i>共有记录 <span id="size">0</span> 条</i>
	    		
	    	</header>
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
	    				<span>门诊</span>
	    				<select id="organization" name="organization"></select>
	    			</li>
	    			<li>
	    				<span>科室</span>
	    				<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_1%>" name="regdept" id="regdept" >
						</select>
	    			</li>
	    			<li>
	    				<span>医生</span>
	    				<select id="createuser" name="createuser" tig="" class="createuser"  data-live-search="true" title="请选择"></select>
<!-- 	    				<select  name="createuser" id="createuser"><option value="">请选择</option></select> -->
						<input type="hidden" name="checkperson" id="checkperson"/> 
						<input type="hidden"  onchange="changeAskPerson()"   id="checkpersonDesc" name="checkpersonDesc" readonly />
	    			</li>
	    			<li>
	    				<span>模糊查询</span>
	    				<input type="text" name="searchvalue" id="searchvalue" class="fuzzyQueryInp" placeholder="请输入编号/姓名/手机/联系地址" style="font-size:12px;">
	    			</li>
	    		</ul>
	    	</section>
	    	<div class="btnBar" style="text-align:left;">
		        <a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清 空</a>
				<a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查 询</a>
	        </div>
   		</div>
		
		<%-- <div id="static" class="staticDiv">
			<div class="staticDivTitle">
				<span class="queryCondition">查询条件</span>
				<span class="recordNumber">
					共有记录 <i class="total" id="size">0</i> 条
				</span>
			</div>
			<div class="staticDivContent">
				<table>
					<tbody>
						<tr>
							<td>
								<span class="commonText">时间：</span>
							
								<input type="text" id="starttime" name="starttime" placeholder="开始日期" class="birthDate">
							
								<span style="padding-left:0;" class="commonText">到：</span>
							
								<input type="text" id="endtime" name="endtime" placeholder="结束日期" class="birthDate">
							</td>
							<td>
								<span class="commonText">门诊：</span>
							</td>
							<td>
								<select id="organization" name="organization"></select>
							</td>
							<td>
								<span class="commonText">科室：</span>
							</td>
							<td>
								<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_1%>" name="regdept" id="regdept" >
								</select>
							</td>
							<td>
								<span class="commonText">医生：</span>
							</td>
							<td>
								<select  name="createuser" id="createuser"><option value="">请选择</option></select>
								<input type="hidden" name="checkperson" id="checkperson"/> 
								<input type="hidden"  onchange="changeAskPerson()"   id="checkpersonDesc" name="checkpersonDesc" readonly style="width: 120px;" ></input>	
							</td>
							<td >
								<input type="text" name="searchvalue" id="searchvalue" class="fuzzyQueryInp" placeholder="请输入编号/姓名/手机/联系地址">
                        		<span class="fuzzyQueryText">*可模糊查询</span>
							</td>
						</tr>
						
					</tbody>
				</table>
				<div class="btnGroup">
					<a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清 空</a>
					<a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查 询</a>
				</div>
			</div>
		</div> --%>
		
	</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>	
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var pageurl = '<%=contextPath%>/KQDS_ChuFangAct/searchChuFang.act';
var nowday;
var menuid = "<%=menuid%>";
var qxnameArr = ['blcx_scbb'];
var func = ['exportTable'];
$(function() {
    initHosSelectList4Front('organization'); // 连锁门诊下拉框	
    //获取当前页面所有按钮
    getButtonPowerByPriv(qxnameArr, func, menuid);
    var tmpOrganization = $("#organization").val();

    initDeptSelectByTypesAndClass(tmpOrganization); // 部门类型1 为医生
    $('.createuser').selectpicker("refresh");//初始化刷新--2019-10-24 licc
    $("#organization").change(function() {
        var currSelect = $(this).val();
        initDeptSelectByTypesAndClass(currSelect);
    });

    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    
 	
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        contentType : "application/x-www-form-urlencoded",   //必须有
        cache: false,
        queryParams: queryParams,
        striped: true,
        pagination: true,//是否显示分页（*）
        pageSize: 30,
        pageList : [15, 20, 25,30],//可以选择每页大小
        paginationShowPageGo: true,
        sidePagination: "server",//后端分页 
        onLoadSuccess: function(data) { //加载成功时执行
        	//加了分页器之后高度自适应
           	$(".fixed-table-container").height($(".fixed-table-container").height()+50+"px");
            var tableList = $('#table').bootstrapTable('getData');
            $("#size").html(data.total);
            setHeight();
        },
      	//点击分页器触动方法
        onPageChange:function(){
        	//加了分页器之后高度自适应
           	$(".fixed-table-container").height($(".fixed-table-container").height()+50+"px");
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
         {
            title: '病人编号',
            field: 'usercode',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return '<span class="name">'+value+'</span>'
            }
        },
        {
            title: '项目名称',
            field: 'itemname',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return '<span style="text-align:left;width:100%;">'+value+'</span>'
            }
        },
        {
            title: '用法',
            field: 'cfusagename',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '单次用量',
            field: 'cfuselevel',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '用药途径',
            field: 'cfusemethodname',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return '<span>'+value+'</span>'
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
            title: '就诊科室',
            field: 'docdept',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '处方医生',
            field: 'createuser',
            align: 'center',
            sortable: true,
            formatter:function(value,row,index){
            	return '<span class="name">'+value+'</span>'
            }
        },
        {
            title: '填写时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            formatter:function(value,row,index){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '操作',
            field: ' ',
            align: 'center',
            formatter: function(value, row, index) {
                var x = '<span><a href="javascript:void(0);" class="highlight" mce_href="javascript:void(0);" onclick="detail(\'' + row.cfno + '\')">详情</a> </span>';
                return x;
            }
        }]
    });
    //时间选择
    $(".birthDate").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "top-right"
    });
    //计算主体的宽度
    setHeight();
    $(window).resize(function() {
        setHeight();
    });
});
// selectChangeTwo("regdept", "createuser",1);
selectChangeTwoSearch("regdept", "createuser",1);
//页面人员高级选择
function select() {
    //获取部门的所有id
    var jzks = $("#regdept option").map(function() {
        return $(this).val();
    }).get().join(",");
    jzks = jzks.replace(/^,+|,+$/g, '');
    deptid_select_user(['checkperson', 'checkpersonDesc'], jzks);
}

//人员树选择后 改变 两级下拉选
function changeAskPerson() {
    var checkerson = $("#checkperson").val();
    if (checkerson) {
        //获取用户部门id
        var url = contextPath + "/YZPersonAct/getDeptByUserSeqId.act?seqId=" + checkerson;
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
                var val = data.retData;
                //就诊科室赋值
                $("#regdept").val(val);
                //手动触发 下拉选change事件
                $("#regdept").trigger("change");
                //人员下拉选赋值
                $("#createuser").val(checkerson);
            }

        },
        function() {
            layer.alert('查询出错！'  );
        });

    }
}
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        regdept: $("#regdept").val(),
        createuser: $('#createuser').val(),
        searchvalue: $('#searchvalue').val(),
        organization: $('#organization').val(),
        limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1,
        sortName:this.sortName,
        sortOrder:this.sortOrder
    };
    return temp;
}
$('#query').on('click',
function() {
    var organization = $("#organization").val();
    var starttime = $("#starttime").val();
    var endtime = $("#endtime").val();
    var regdept = $("#regdept").val();
    var createuser = $("#createuser").val();
    var searchvalue = $("#searchvalue").val();
    if (organization == "" && starttime == "" && endtime == "" && regdept == "" && createuser == "" && searchvalue == "") {
        layer.alert('请选择查询条件'  );
        return false;
    }
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
});
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
$('#clean').on('click',
function() {
    $(".staticDivContent :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
});
//查看详情
function detail(seqId) {
    var requrl = contextPath + '/KQDS_ChuFangAct/toDetail_ChuFang.act?1=1';
    requrl += '&seqId=' + seqId;
    parent.layer.open({
        type: 2,
        title: '处方单明细',
        shade: 0.6,
        shadeClose: false,
        area: ['39%', '70%'],//修改处方单弹出框宽度
        content: requrl
    });
}
//计算左侧表格高度保证一屏展示
function setHeight() {
	$("#table").bootstrapTable("resetView",{
		height:$(window).outerHeight()-$(".titleText").outerHeight()-$(".searchModule").outerHeight()-20
	});
	//加了分页器之后高度自适应
   	$(".fixed-table-container").height($(".fixed-table-container").height()+50+"px");
}


</script>
</html>
