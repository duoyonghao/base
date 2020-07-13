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
<title>预约中心</title><!-- 点击预约中心 按钮进入  以表格方式展现 ，分网电和门诊预约 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<!-- 预约中心 里 网电预约和门诊预约的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/yyzx/yyzxSearch.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<style>
	.fixed-table-container{
		border-left: 1px solid #ddd;
		border-right: 1px solid #ddd;
		border-bottom:1px solid #ddd;
		border-radius: 6px;
		/* border-top-left-radius: 6px;
		border-top-right-radius: 6px; */
		overflow: hidden;
	}
	.tableBox{
		
		overflow:hidden;
	}
/* 查询条件  中的样式 */
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
/* select */
.btn-default{
	width: 100px !important;
	height: 26px;
}
.btn {
	padding: 3px 12px;
	box-sizing:border-box;
	font-size: 12px;
	line-height: inherit;
}
.filters_wrapper span{
	padding-right: 0px;
}
input[type="text"]:focus, select:focus, textarea:focus {
    box-shadow: 0 0 8px #00A6C0; 
}
.dropdown-menu > .active > a, .dropdown-menu > .active > a:hover, .dropdown-menu > .active > a:focus {
    background-color: #00A6C0;
}
.bootstrap-select:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {
    width: auto;
}
.dropdown-menu > li > a {
    padding: 0px 20px;
    box-sizing:border-box;
    font-size: 12px;
}
.form-control {
    height: auto;
    padding: 0px 12px;
    box-sizing:border-box;
}
.bootstrap-select.btn-group .dropdown-menu li a span.text {
    font-weight: normal;
}
.pull-left {
    color: black;
}
/* 解决select被遮盖问题 */
.searchModule>section>ul.conditions {
    overflow: visible;
    height: 60px;
} 
</style>
</head>
<body>
<div class="">
	<%-- <div class="queryDiv">	<!--查询条件的父元素 -->
		<!-- .queryTitle标题行  .queryText “查询条件”字样样式-->
		<div class="queryTitle"><span class="queryText">查询条件</span></div> 
		<!-- .operateDiv条件选择 区的父元素 -->
		<div id="wangdian" class="operateDiv">
		<!--  .operateConditionDiv 左边条件选择-->
			<div class="operateConditionDiv">
				<table class="tableLayout">
					<tr>
						<td>
							<span class="commonText">预约时间：</span>
						</td>
						<td>
							<input type="text"  placeholder="开始时间" class="dataTimewangdian whiteInp" id="wstarttime">
						</td>
						<td>
							<span class="commonText">到：</span>
						</td>
						<td>
							<input type="text"  placeholder="结束时间" class="dataTimewangdian whiteInp" id="wendtime">
						</td>
						
						<td>
							<span class="commonText">门诊：</span>
						</td>
						<td>
							<select name="organization" id="organization_wd" ></select>
						</td>
						<td>
							<span class="commonText">本次上门状态：</span>
						</td>
						<td>
							<select name="worderstatus" id="worderstatus">
								<option value="">请选择</option>
			                	<option value="0">未上门</option>
			                	<option value="1">已上门</option>
							<select>
						</td>
						
						<td>
							<span class="commonText">咨询项目：</span>
						</td>
						<td>
							<select class="dict" tig="ZXXM" name="xiangmu" id="xiangmu" ></select>
						</td>
						
						
					</tr>
					
					<tr>
						
						<td>
							<span class="commonText">科室：</span>
						</td>
						<td>
							<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_0%>" name="regdept" id="regdept_wd" ></select>
						</td>
					
						<td>
							<span class="commonText">咨询：</span>
						</td>
						<td>
							<select name="askperson" id="askperson_wd" ></select>
						</td>
						<td>
							<span class="commonText">建档人：</span>
						</td>
						<td>
							<input type="hidden" name="createuser2" id="createuser2" value="" placeholder="" title="" class="form-control" style="width: 70px;" />
							<input  type="text" id="createuserDesc2" name="createuserDesc2" placeholder="" onClick="javascript:single_select_user(['createuser2', 'createuserDesc2'],'',1);">
						</td>
						<td>
							<span class="commonText">模糊查询：</span>
						</td>
						<td>
							<input type="text" id="wusername" name="wusername"  placeholder="患者姓名/手机"/>
						</td>
					</tr>
				</table>
			</div>
		<!--.operatebtnDiv 右边按钮组  -->	
			<div class="operatebtnDiv">
				<a id="clearwangdian" href="javascript:void(0);" class="kqdsCommonBtn">清空</a>
				<a id="query" href="javascript:void(0);" class="kqdsSearchBtn">查询</a>
			</div>
		</div>
	</div> --%>
	
	<!--查询条件-->
    <div class="searchModule" style="margin-bottom:2px;text-align:right;">
    	<header>
    		<span>查询条件</span>
    		<!-- <i>共有记录 <span id="total"></span> 条</i> -->
    	</header>
    	<section id="wangdian" style="border-bottom:none;">
    		<div style="float:right;margin-top:2px;">
	            <a id="clearwangdian" href="javascript:void(0);" class="kqdsCommonBtn">清空</a>
				<a id="query" href="javascript:void(0);" class="kqdsSearchBtn">查询</a>
	        </div>
    		<ul class="conditions">
				<li>
					<span>预约时间</span>
					<input type="text" style="font-size:11px;"  placeholder="开始时间" class="dataTimewangdian whiteInp" id="wstarttime">
				</li>
				<li>
					<span>到</span>
					<input type="text" style="font-size:11px;"  placeholder="结束时间" class="dataTimewangdian whiteInp" id="wendtime">
				</li>
				<li>
					<span>门诊</span>
				</li>
				<li>
					<select name="organization" id="organization_wd" ></select>
					<span>本次上门</span>
				</li>
				<li>
					<select name="worderstatus" id="worderstatus">
						<option value="">请选择</option>
	                	<option value="0">未上门</option>
	                	<option value="1">已上门</option>
					<select>
				</li>
				<li>
					<span>咨询项目</span>
					<select class="dict" tig="ZXXM" name="xiangmu" id="xiangmu" title="请选择" data-live-search="true"></select>
				</li>
				<li>
					<span>科室</span>
					<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_0%>" name="regdept" id="regdept_wd" ></select>
				</li>
				<li>
					<span>咨询</span>
					<select name="askperson" id="askperson_wd" title="请选择" data-live-search="true"></select>
				</li>
				<li>
					<span>建档人</span>
					<input type="hidden" name="createuser2" id="createuser2" value="" placeholder="" title="" class="form-control" style="width: 70px;" />
					<input  type="text" id="createuserDesc2" name="createuserDesc2" placeholder="" onClick="javascript:single_select_user(['createuser2', 'createuserDesc2'],'',1);">
				</li>
				<li>
					<span>状态</span>
					<select name="isdelete" id="isdelete" >
						<option value="">请选择</option>
	                	<option value="0">正常</option>
	                	<option value="1">已取消</option>
					</select>
				</li>
				<li>
					<span>模糊查询</span>
					<input type="text" id="wusername" name="wusername"  placeholder="患者姓名/手机"/>
				</li>
    		</ul>
    	</section>
    </div>
	    
	<div>
	    <!--表格-->
	   	<div class="tableBox" id="tableDiv2">
	       	<table id="table2" class="table-striped table-condensed table-bordered"></table>
	    </div>
	    <div id="buttonBar"> 
	          <table style="width:90%;text-align: center;"> 
	      		<tr>
	     			<td style="width:12%;"><span style="color:#00A6C0;">总记录数:<lable id="wdzong">0</lable></span></td>
	     			<td style="width:12%"><span style="color:#00A6C0;">本次上门数:<lable id="wdsms">0</lable></span></td>
	       			<td style="width:12%"><span style="color:red;">本次未上门数:<lable id="wdwsms">0</lable></span></td>
	       			<td style="width:12%"><span style="color:#00A6C0;">成交数:<lable id="wdcj">0</lable></span></td>
	       			<td style="width:12%"><span style="color:red;">未成交数:<lable id="wdwcj">0</lable></span></td>
	      		</tr> 
	      	  </table> 
	     </div>
	</div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var visitname = "<%=person.getUserName()%>";
var userpriv = "<%=person.getUserPriv()%>";
var $table2 = $('#table2');
<%-- var pageurl2 = '<%=contextPath%>/KQDS_Net_OrderAct/selectNoPageYYZX.act'; --%>
var pageurl2 = '<%=contextPath%>/KQDS_Net_OrderAct/selectNoPageYyzxNet.act';
var nowday;
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var clickrow = "";
var static_organization = null;
var menuid = "<%=menuid%>";
var qxnameArr = ['wdyy_scbb'];
var func = ['exportTable'];
$(function() {
	//获取当前页面所有按钮
    getButtonPowerByPriv(qxnameArr,func,menuid);

    /**********************默认初始化门诊查询条件****************************/
    static_organization = '<%=ChainUtil.getCurrentOrganization(request)%>';
    initHosSelectListNoCheck('organization_wd',static_organization); // 加载门诊列表
    //initDeptSelectByTypesAndClass(static_organization); //加载科室
  //加载科室
   var tmpOrganization = $("#organization_wd").val();
   initDeptSelectByTypesAndClass(tmpOrganization); 
    //$('.dept').selectpicker("refresh");//部门下拉框添加搜索功能 lutian
    $("#organization_wd").change(function() {
        var currSelect = $(this).val();
        initDeptSelectByTypesAndClass(currSelect); // ys modify 1213
    });
    selectChangeTwo("regdept_wd", "askperson_wd",1);
    /**********************默认初始化门诊查询条件END****************************/

    //获取当前日期
    nowday = getNowFormatDate();
    $("#wstarttime").val(nowday);
    $("#wendtime").val(nowday);

    //绑定两个时间选择框的chage时间
    $("#wstarttime,#wendtime").change(function() {
        timeCompartAndFz("wstarttime", "wendtime");
    });

    initDictSelectByClass(); //预约项目分类、咨询项目
    $('.dict').selectpicker("refresh");//预约项目分类下拉框添加搜索功能 lutian
    inittablewangdian();
    
    //时间选择
    $(".dataTimewangdian").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "bottom-right"
    });
    //清空网电查询条件
    $('#clearwangdian').on('click',
    function() {
        $("#wangdian :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
        /**********************默认初始化门诊查询条件****************************/
        $("#askperson_mz").val("");
        static_organization = '<%=ChainUtil.getCurrentOrganization(request)%>';
        initHosSelectListNoCheck('organization_wd', static_organization); // 加载门诊列表
        getAllDeptByOrganization(static_organization); //加载科室
        /**********************默认初始化门诊查询条件END****************************/
    });
    $('#query').on('click',
    function() {
		$(this).attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
		$(this).text("查询中");
        //加载内容
        $table2.bootstrapTable('refresh', {
            'url': pageurl2
        });
    });
    setHeight();
    $(window).resize(function(){
    	setHeight();
    });
});


//二级联动，根据前一个select 获取后一个select lutian
function selectChangeTwo(firstId, twoId,showleave) {
    var dict = $("#" + twoId);
    $(document).on("change", "#" + firstId,
    function() {
        var value = $('#' + firstId).find("option:selected").val();
        if (!value) {
            dict.empty();
            return; // 终止执行
        }
        var url = projectName + '/YZPersonAct/getUserListByDeptId.act?deptId=' + value+'&showleave='+showleave+'&organization='+ $("#organization_wd").val();
        $.axse(url, null,
        function(data) {
            if (data) {
            	dict.empty();
                dict.append("<option value=''>请选择</option>");
                var list = data;
                if (list.length > 0) {
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.userName + "</option>");
                    }
                    $('#'+twoId).selectpicker("refresh");//人员下拉框添加搜索功能 lutian
                }
            } else {
                layer.alert('查询失败', {
                      
                });
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });

    });
}

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的 
        organization: $("#organization_wd").val(),
        // 增加门诊条件过滤，挂号查询单个患者时，不进行门诊条件过滤
        regdept: $("#regdept_wd").val(),
        askperson: $("#askperson_wd").val(),
        xiangmu: $("#xiangmu").val(),
        doorstatus: $("#worderstatus").val(),
        starttime: $("#wstarttime").val(),
        endtime: $("#wendtime").val(),
        username: $("#wusername").val(),
        createuser: $("#createuser2").val(),
        isdelete: $("#isdelete").val()
    };
    return temp;
}

//导出
function exportTable() {
    var fieldArr = [];
    var fieldnameArr = [];
    $('#table2 thead tr th').each(function() {
        var field = $(this).attr("data-field");
        if (field != "") {
            fieldArr.push(field); //获取字段
            fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
        }
    });
    var param = JsontoUrldata(queryParams());
    location.href = pageurl2 + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
}

function queryParamsB(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的 
    	sortName:this.sortName,//排序名称
    	sortOrder:this.sortOrder,//排序类型
    	limit: params.limit,   //页面大小
    	offset: params.offset, //页码 
    	pageIndex : params.offset/params.limit + 1,
        organization: $("#organization_wd").val(),
        // 增加门诊条件过滤，挂号查询单个患者时，不进行门诊条件过滤
        regdept: $("#regdept_wd").val(),
        askperson: $("#askperson_wd").val(),
        xiangmu: $("#xiangmu").val(),
        doorstatus: $("#worderstatus").val(),
        starttime: $("#wstarttime").val(),
        endtime: $("#wendtime").val(),
        username: $("#wusername").val(),
        createuser: $("#createuser2").val(),
        isdelete: $("#isdelete").val()
    };
    //console.log("0-=0--="+temp.sortName);
    return temp;
}
//加载网电表格
function inittablewangdian() {
    $table2.bootstrapTable({
        url: pageurl2,
        dataType: "json",
        striped: true,
        queryParams: queryParamsB,
        cache: false,
        pagination: true,//是否显示分页（*）
	    pageSize: 30,
	    pageList : [15,25,35,50],//可以选择每页大小
        clickToSelect: false,
        singleSelect: false,
        paginationShowPageGo: true,
        sidePagination: "server",//后端分页 
        onDblClickCell: function(field, value, row, td) {
            //双击事件 调用提交回访结果方法 
            goToUserCenterPage(row.usercode);
        },
        onLoadSuccess: function(data) { //加载成功时执行
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","auto").css("pointer-events","auto");
				$("#query").text("查询");
			}
        	//console.log("网电预约=0===0==-0=0=="+JSON.stringify(data));
         	$(".fixed-table-container").height($(".fixed-table-container").height()+50+"px");
            if_wd_table_int = "wd_table_has_init";

            var tableList = data.nums[0];
            $("#wdzong").html(tableList.numall);
            /* var cjs = 0,
            wcjs = 0,
            sms = 0,
            wsms = 0;
            for (var i = 0; i < data.rows.length; i++) {
                if (data.rows[i].cjstatus == "已成交") {
                    cjs = cjs + 1;
                } else {
                    wcjs = wcjs + 1;
                }
                if (data.rows[i].doorstatus == "已上门") {
                    sms = sms + 1;
                } else {
                    wsms = wsms + 1;
                }
            } */
            $("#wdcj").html(tableList.cjstatus);
            $("#wdwcj").html(Number(tableList.numall)-Number(tableList.cjstatus));
            $("#wdsms").html(tableList.doorstatus);
            $("#wdwsms").html(Number(tableList.numall)-Number(tableList.doorstatus));
            /*  table加载好，判断是否出现滚动条并进行调整，需要传入table父容器的class名字*/
            setTableHeaderWidth(".tableBox");
        },
        rowStyle: function(row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (row.isdelete == "1") {
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
			     var pageSize = $('#table2').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
			        var pageNumber = $('#table2').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
			        return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
			    }
		},
          {
            title: '门诊',
            field: 'organizationname',
            align: 'center',
            formatter: function(value, row, index) {
                return '<span class="time" title="' + value + '">' + value + '</span>';
            }
        },{
            title: '患者编号',
            field: 'usercode',
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
                    return '<span class="time phone" title="' + value + '">' + value + '</span>';
                } else {
                    return '-';
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
                    return '';
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
                    return '<span class="time" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
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
                    return '<span class="time" title="' + value + '">' + getDatetimeToMinutes(value) + '</span>';
                } else {
                    return '';
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
            field: 'doorstatus',
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
            formatter: function(value, row, index) {
                if (value == "已成交") {
                    return '<span style="color:green">已成交</span>';
                } else {
                    return '<span style="color:red">未成交</span>';
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
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '网电咨询内容',
            field: 'askcontent',
            align: 'center',
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="remark" title="' + value + '">' + value + '</span>';
                }
            }
        },
        {
            title: '网电备注',
            field: 'remark',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (row.remark != "" && row.remark != null) {
                    return '<span class="remark" title="' + value + '">' + value + '</span>';
                } else {
                    return "";
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
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
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
                    return '<span title="' + value + '">' + getDatetimeToDay(value) + '</span>';
                } else {
                    return '';
                }
            }
        },{
            title: '预约状态',
            field: 'isdelete',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value == 1) {
                    html = '<span class="name" style="color:red;">已取消</span>';
                    return html;
                } else {
                    return "<span class='name'>正常</span>";
                }
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $table2.find('tr.success').data('index'); //获得选中的行
        clickrow = $table2.bootstrapTable('getData')[index];
    });
}
//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height();
    $('#table2').bootstrapTable('resetView', {
    	height: baseHeight - $(".searchModule").outerHeight()-24
    });
}
</script>
</body>
</html>