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
<title>病历查询</title><!-- 医疗中心 病历查询 菜单  -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />

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

<style>
*{
	box-sizing:border-box;
}
	.searchWrap{
	    margin-top: 10px;
   		overflow: hidden;
	}
	.tableLayout{/*  */
		margin:0 auto;
		width:98%;
	}
	.tableLayout tr{
		height:42px;
	}
	#searchvalue{/* 模糊查询搜索框 */
		/* width:157px; */
		font-size:12px;
	}
	.searchWrap .btnGroups{		/*按钮组右浮动  */
		float:right;
		overflow:hidden;
	}
	.searchWrap .btnGroups .aBtn{
		margin:0 3px;
		display:block;
		color:#0E7CC9;
		background:#fff;
		height:28px;
		padding:0 15px;
		line-height:28px;
		border-radius:16px;
		text-align:center;
		border:1px solid #0E7CC9;
		font-size:13px;
	}
	.searchWrap .btnGroups .aBtn:hover{
		cursor:pointer;
		color:#fff;
		background:#0E7CC9;
	}
	select{/*普通select inputsss */
		box-sizing:border-box;
		width:80px;
		color:#333;
		height:24px;
		border-radius:4px;
		transition:box-shadow linear 300ms;
	}
	input[type="text"]{/*普通select inputsss */
		box-sizing:border-box;
		width:86px;
		color:#333;
		height:24px;
		border-radius:4px;
		transition:box-shadow linear 300ms;
	}
	select{
		cursor:pointer;
	}
	/* input[type="text"]:focus,select:focus,textarea:focus{
	    box-shadow: 0 0 8px rgb(49, 165, 247);
	    border-color:transparent;
	} */
	.btnGroups{
		float:right;
	}
	.btnGroups a.aBtn{
		float:left;
	}
	.textContent{
		display:inline-block;
		text-align:right;
	}
	.orangeText{
		color:#FA6406;
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
.fixed-table-container thead th .sortable{/*覆盖样式 表头右边距导致无法居中的问题  */
	padding-right:8px;
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
<div id="container">
    <div class="main" style="margin-left:20px;">
        <div class="listWrap" style="margin-bottom:10px;">
            <div class="listHd">
            	<span class="title">病历查询</span>
           	</div>
            <div class="listBd">
                <div class="tableBox">
                    <table id="table" class="table-striped table-condensed table-bordered" data-height="550"></table>
                </div>
            </div>
        </div>
        <!--查询条件-->
        <div class="searchModule">
	    	<header>
	    		<span>查询条件</span>
	    		<i>共有记录 <span id="size">0</span> 条</i>
	    	</header>
	    	<section>
	    		<ul class="conditions">
	    			<li>
	    				<span>时间</span>
	    				<input type="text" id="starttime" name="starttime" placeholder="开始日期" readonly class="birthDate">
	    			</li>
	    			<li>
	    				<span>到</span>
	    				<input type="text" id="endtime" name="endtime" placeholder="结束日期" readonly class="birthDate">
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
					 	<input type="hidden"  onchange="changeAskPerson()"   id="checkpersonDesc" name="checkpersonDesc" readonly ></input>
	    			</li>
	    			<li>
	    				<span>病历分类</span>
	    				<select class="dict" tig="blfl" name="blfl" id="blfl" ></select>
	    			</li>
	    			<li>
	    				<span>病程</span>
	    				<select name="bc" id="bc">
           			 		<option value="">请选择</option>
           				</select>
	    			</li>
	    			<li>
	    				<span>病种分类</span>
	    				<select name="mtype" id="mtype">
           			 		<option value="">请选择</option>
           			 		<option value="0">初诊</option>
           			 		<option value="1">复诊</option>
           			 		<option value="2">种植一期</option>
           			 		<option value="3">种植复查</option>
           			 		<option value="4">种植二期</option>
           			 		<option value="5">种植修复</option>
           				</select>
	    			</li>
	    			<li>
	    				<span>模糊查询</span>
	    				<input type="text" name="searchvalue" id="searchvalue" class="searchInput" placeholder="请输入编号/姓名/手机/联系地址">
	    			</li>
	    			
	    		</ul>
	    	</section>
	    	<div class="btnBar" style="text-align:left;">
		        <a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
   				<a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
	        </div>
   		</div>
        
        <%-- <div class="searchWrap">
            <div class="cornerBox">查询条件</div>
            <span class="text">共有记录<i class="total" id="size">0</i>条</span>
            
            <table class="formBox tableLayout"><!--  -->
            	<tr>
            		<td >
            			<span class="textContent">时间：</span>
            			<input type="text" id="starttime" name="starttime" placeholder="开始日期" readonly class="birthDate">
            			<span>到：</span>
                        <input type="text" id="endtime" name="endtime" placeholder="结束日期" readonly class="birthDate">
            		</td>
            		<td>
	            		<span class="textContent">门诊：</span>
	            		<select id="organization" name="organization"></select>
            		</td>
            		<td>
            			<span class="textContent">科室：</span>
            			<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_1%>" name="regdept" id="regdept" >
						</select>
            		</td>
            		<td >
            			<span class="textContent">医生：</span>
            			<select  name="createuser" id="createuser"><option value="">请选择</option></select>
            			
            			<input type="hidden" name="checkperson" id="checkperson"/> 
					 	<input type="hidden"  onchange="changeAskPerson()"   id="checkpersonDesc" name="checkpersonDesc" readonly ></input>	
            		</td>
            		<td>
            			<span class="textContent">病历分类：</span>
            			<select class="dict" tig="blfl" name="blfl" id="blfl" ></select>
            		</td>
            		<td>
            			<span class="textContent">病程：</span>
            			<select name="bc" id="bc">
           			 		<option value="">请选择</option>
           				 </select>
            		</td>
            		<td>
            			<span class="textContent">病种分类：</span>
            			<select name="mtype" id="mtype">
           			 		<option value="">请选择</option>
           			 		<option value="0">初诊</option>
           			 		<option value="1">复诊</option>
           			 		<option value="2">种植一期</option>
           			 		<option value="3">种植复查</option>
           			 		<option value="4">种植二期</option>
           			 		<option value="5">种植修复</option>
           				 </select>
            		</td>
            		<td>
            			<input type="text" name="searchvalue" id="searchvalue" class="searchInput" placeholder="请输入编号/姓名/手机/联系地址">
            		</td>
            		<!-- <td colspan="6">
            			
            		</td> -->
            	</tr>
            </table>  
            <div class="btnGroups">
     				<a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
     				<a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
           </div>
        </div> --%>
        
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var pageurl = '<%=contextPath%>/KQDS_MedicalRecordAct/selectPageByUsercodeNopage.act';
var nowday;
var menuid = "<%=menuid%>";
var qxnameArr = ['blcx_scbb'];
var func = ['exportTable'];
var isClick = true;

$(function() {
    initHosSelectList4Front('organization'); // 连锁门诊下拉框	
    //获取当前页面所有按钮
    getButtonPowerByPriv(qxnameArr, func, menuid);
    var tmpOrganization = $("#organization").val();
    initDeptSelectByTypesAndClass(tmpOrganization);
    $('.createuser').selectpicker("refresh");//咨询部门初始化刷新--2019-10-24 licc
    $("#organization").change(function() {
        var currSelect = $(this).val();
        initDeptSelectByTypesAndClass(currSelect);
    });

    initDictSelectByClass(); // 病历分类
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    $('#blfl').change(function() {
    	getSubDictSelectByParentCode(this.value,'bc');
    });
    
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        contentType : "application/x-www-form-urlencoded",   //必须有
        cache: false,
        queryParams: queryParams1,
        striped: true,
        pagination: true,//是否显示分页（*）
        pageSize: 30,
        pageList : [15, 20, 25,30],//可以选择每页大小
        sidePagination: "server",//后端分页
	    paginationShowPageGo: true,
        onLoadSuccess: function(data) { //加载成功时执行
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer").css("pointer-events","auto");
				$("#query").text("查询");
			}
			var tableList = $('#table').bootstrapTable('getData');
            $("#size").html(data.total);
            setHeight();
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
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span class="name">'+value+'</span>';
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
                    return '-';
                }
            }
        },
        /* {title: '就诊门诊',field: 'org',align: 'center',sortable: true,
				formatter:function(value,row,index){
					if(row.size != "" && row.size != null){
						$("#size").html(row.size);
					}
				    return "QDKE";
			  } 
			}, */
        {
            title: '就诊科室',
            field: 'docdept',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '诊治医生',
            field: 'createuser',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '病种分类',
            field: 'mtypename',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                var html = '<span style="position:relative;top:1px;" class="label label-success">'+value+'</span>';
                return html;
            }
        },
        {
            title: '病历分类',
            field: 'blfl',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '病程',
            field: 'bc',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '填写时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '操作',
            field: ' ',
            align: 'center',
            formatter: function(value, row, index) {
            	if(row.mtype == '0' || row.mtype == '1'){
            		return '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="detail(\'' + row.seqId + '\')">详情</a> ';
            	}
            	if(row.mtype == '2' || row.mtype == '3' || row.mtype == '4' || row.mtype == '5'){
            		return '<a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:#00A6C0;" onclick="huifubingli(\'' + row.seqId + '\',\'' + row.mtype + '\',\'detail\',\'' + row.usercode + '\')">&nbsp;详情</a> ';
            	}
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
//selectChangeTwo("regdept", "createuser",1);
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
        status: 2,
        mtype: $('#mtype').val(),
        endtime: $('#endtime').val(),
        regdept: $("#regdept").val(),
        createuser: $('#createuser').val(),
        searchvalue: $('#searchvalue').val(),
        organization: $('#organization').val(),
        blfl: $('#blfl').val(),
        bc: $('#bc').val()
    };
    return temp;
}
function queryParams1(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        starttime: $('#starttime').val(),
        status: 2,
        mtype: $('#mtype').val(),
        endtime: $('#endtime').val(),
        regdept: $("#regdept").val(),
        createuser: $('#createuser').val(),
        searchvalue: $('#searchvalue').val(),
        organization: $('#organization').val(),
        blfl: $('#blfl').val(),
        bc: $('#bc').val(),
        limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        sortName:this.sortName,
        sortOrder:this.sortOrder,
        pageIndex : params.offset/params.limit + 1
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
    var blfl = $("#blfl").val();
    var bc = $("#bc").val();
    if (organization == "" && starttime == "" && endtime == "" && regdept == "" && createuser == "" && searchvalue == "" && blfl == "" && bc == "") {
        layer.alert('请选择查询条件'  );
        return false;
    }
	$(this).attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
	$(this).text("查询中");
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
});

var loadIndex='';
function download() {
	layer.msg('数据导出中，请等待');
	//loadIndex = layer.load(0, {shade: false});
	isClick = false;
}
function disload() {
	layer.close(loadIndex);
	layer.msg('数据导出完毕');
	isClick = true;
}
//点击导出
function exportTable() {
	if(isClick) {
		isClick = false;
		// console.log("生成报表")
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
		//location.href = pageurl + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
		var url = pageurl + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
		download();
		var xhr = new XMLHttpRequest();
		xhr.open('GET', url, true);    // 也可用POST方式
		xhr.responseType = "blob";
		xhr.onload = function () {
			if (this.status === 200) {
				var blob = this.response;
				// if (navigator.msSaveBlob == null) {
				var a = document.createElement('a');
				//var headerName = xhr.getResponseHeader("Content-disposition");
				//var fileName = decodeURIComponent(headerName).substring(20);
				a.download = "病历查询";
				a.href = URL.createObjectURL(blob);
				$("body").append(a);    // 修复firefox中无法触发click
				a.click();
				URL.revokeObjectURL(a.href);
				$(a).remove();
				// } else {
				//     navigator.msSaveBlob(blob, "信息查询");
				// }
			}
			disload();
		};
		xhr.send();
	}
}
$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
});
function detail(id) {
    parent.layer.open({
        type: 2,
        title: '详情',
        shadeClose: true,
        //点击遮罩关闭层
        area: ['1000px', '550px'],
        content: contextPath + '/KQDS_MedicalRecordAct/toMedicalrecordDetail.act?seqId=' + id
    });
}

/**
 * 种植一期、种植复查、种植二期、种植修复病历详情
 */
function huifubingli(seqId, mtype, flag,usercode) {
    var winTitle = "病历详情";
    var titleStr = "【种植一期】";
    var requrl = '<%=contextPath%>/KQDS_BLK_ZhongzhiAct/toZhongZhiYiQi_Detail.act';

    if (mtype == '3') {
    	titleStr = "【种植复查】";
        requrl = '<%=contextPath%>/KQDS_BLK_ChaiXianAct/toZhongZhi_Suture_Removal_Detail.act';
    }

    if (mtype == '4') {
    	titleStr = "【种植二期】";
        requrl = '<%=contextPath%>/KQDS_BLK_Zhongzhi2Act/toZhongZhiErQi_Detail.act';
    }

    if (mtype == '5') {
    	titleStr = "【种植修复】";
        requrl = '<%=contextPath%>/KQDS_BLK_XiuFuAct/toZhongZhi_XiuFu_Detail.act';
    }

    requrl += '?seqId=' + seqId + '&mtype=' + mtype + '&usercode=' + usercode;

    if (flag == 'detail') {
        requrl += '&detailFlag=' + flag;
        winTitle = "病历详情";
    }

    layer.open({
        type: 2,
        title: winTitle + titleStr,
        shade: 0.6,
        shadeClose: false,
        area: ['95%', '98%'],
        content: requrl
    });
}

//计算左侧表格高度保证一屏展示
function setHeight() {
   $("#table").bootstrapTable("resetView",{
	   height:$(window).outerHeight()-$(".searchModule").outerHeight()-$(".listWrap .listHd").outerHeight()-20
   });
}

</script>
</html>
