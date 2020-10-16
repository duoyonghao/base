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
<style>
	
	.tableBox{
		
		overflow:hidden;
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
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>   

</head>
<body>
	<div>	
		<!--查询条件-->
	    <div class="searchModule" style="margin-bottom:2px;text-align:right;">
	    	<header>
	    		<span>查询条件</span>
	    		<!-- <i>共有记录 <span id="total"></span> 条</i> -->
	    	</header>
	    	<section id="menzhen" style="border-bottom:none;">
	    		<div style="float:right;margin-top:2px;">
		            <a id="clearmenzhen" href="javascript:void(0);" class="kqdsCommonBtn">清空</a>
					<a id="query" href="javascript:void(0);" class="kqdsSearchBtn">查询</a>
		            <a id="mzyy_scbb" href="javascript:void(0);" onclick="exportTable()" class="kqdsCommonBtn hidden">生成报表</a>
		        </div>
	    		<ul class="conditions">
	    			<li>
	    				<span>预约时间</span>
						<input type="text" style="font-size:11px;" placeholder="开始时间" class="dataTimemenzhen"  id="starttime" >
					</li>
					<li>
	    				<span>到</span>
						<input type="text" style="font-size:11px;" placeholder="结束时间" class="dataTimemenzhen" id="endtime" >
					</li>
					<li>
	    				<span>门诊</span>
						<select name="organization" id="organization_mz" onchange="initPersonSelectByOrganization()"></select>
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
						<select class="dict" tig="YYXM" name="yyxm" id="yyxm" title="请选择" data-live-search="true"></select>
					</li>
					<li>
						<span>模糊查询</span>
						<input type="text" id="musername" name="musername"  placeholder="患者姓名/手机" />
					</li>
					<li>
						<span>部门</span>
						<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_0%>,<%=ConstUtil.DEPT_TYPE_1%>" name="regdept" id="regdept_mz" title="请选择" data-live-search="true"></select>
					</li>
					<li>
						<span>医生/咨询</span>
						<select class="askperson" name="askperson" id="askperson_mz" title="请选择" data-live-search="true"></select>
					</li>
					<li>
						<span>创建人</span>
						<input type="hidden" name="cjr" id="cjr" value="" class="form-control whiteInp" style="width: 70px;" />
						<input type="text" id="cjrDesc" name="cjrDesc" onClick="javascript:single_select_user(['cjr', 'cjrDesc'],'single',1);">
					</li>
					<li>
						<span>第一咨询</span>
						<select class="firstaskperson" name="firstaskperson" id="firstaskperson" title="请选择" data-live-search="true"></select>
					</li>
					<li>
						<span>建档人</span>
						<input type="hidden" name="createuser" id="createuser" value="" placeholder="" class="form-control whiteInp" style="width: 70px;" />
						<input type="text" id="createuserDesc" name="createuserDesc" placeholder="" onClick="javascript:single_select_user(['createuser', 'createuserDesc'],'single',1);">
					</li>
					<li id="zdkf" style="margin-left: 15px;">
						<!-- <span style="padding-left: 31px;"><input type="button" class="kqdsCommonBtn" onclick="setKeFu()" value="指定客服"></span> --> 
					</li>
	    		</ul>
	    	</section>
	    </div>
		
		<div>
		    <!--表格-->
	    	<div class="tableBox" id="tableDiv1">
	           	<table id="table1" class="table-striped table-condensed table-bordered"></table>
	        </div>
	        <div id="buttonBar"> 
	              <table style="width:90%;text-align: center;"> 
	          		<tr>
	         			<td style="width:12%;"><span style="color:#00a6c0;">总记录数:<lable id="zong">0</lable></span></td>
	         			<td style="width:12%"><span style="color:#00a6c0;">本次上门数:<lable id="sms">0</lable></span></td>
	           			<td style="width:12%"><span style="color:red;">本次未上门数:<lable id="wsms">0</lable></span></td>
	           			<td style="width:12%"><span style="color:#00a6c0;">成交数:<lable id="cj">0</lable></span></td>
	           			<td style="width:12%"><span style="color:red;">未成交数:<lable id="wcj">0</lable></span></td>
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
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/kqds/kqds_system.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var visitname = "<%=person.getUserName()%>";
var userpriv = "<%=person.getUserPriv()%>";
var $table1 = $('#table1');
var $table2 = $('#table2');
var pageurl1 = '<%=contextPath%>/KQDS_Hospital_OrderAct/selectNoPage.act';
var nowday;
var listbutton;
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var clickrow = "";
var static_organization = null;
var menuid = "<%=menuid%>";
var qxnameArr = ['mzyy_scbb','hfzx_scbb'];
var func = ['exportTable'];
var isClick = true;

$(function() {
	$("input[type='text']").attr("autocomplete","off");  //去掉input输入框的历史记录
    //获取当前页面所有按钮##现在此方法 ## syp 17-5-2
    getButtonAllCurPage("<%=menuid%>");
  	//获取当前页面所有按钮
    //getButtonPowerByPriv(qxnameArr,func,menuid);
  	//咨询 下拉列表
    initPersonSelectByDeptType("firstaskperson","<%=ConstUtil.DEPT_TYPE_0 %>");
    $('.firstaskperson').selectpicker("refresh");//第一咨询下拉框添加搜索功能 lutian
    /**********************默认初始化门诊查询条件****************************/
    static_organization = '<%=ChainUtil.getCurrentOrganization(request)%>';
    initHosSelectListNoCheck('organization_mz',static_organization); // 加载门诊列表
  	//加载科室
    var tmpOrganization = $("#organization_mz").val();
    getAllDeptByOrganization(tmpOrganization); 
    /* $('.dept').selectpicker("refresh");//部门下拉框添加搜索功能 lutian */
    $("#organization_mz").change(function() {
        var currSelect = $(this).val();
        //getAllDeptByOrganization(currSelect); // ys modify 1213
        getAllDeptByOrganizationSingle(currSelect,"regdept_mz"); //selectpicker插件单独下拉框加载 2019/11/19 lutian
    });
    /**********************默认初始化门诊查询条件END****************************/

    selectChangeTwo("regdept_mz", "askperson_mz",1); //监听 onchange事件       根据部门选择人员
    
    //获取当前日期
    nowday = getNowFormatDate();
    $("#starttime").val(nowday + " 00:00");
    $("#endtime").val(nowday + " 23:59");

    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {	
        timeCompartAndFz("starttime", "endtime");
    });

    initDictSelectByClass(); //预约项目分类、咨询项目
    $('.dict').selectpicker("refresh");//预约项目分类下拉框添加搜索功能 lutian
    
    inittablemenzhen();
    
    //时间选择
    $(".dataTimemenzhen").datetimepicker({
        language: 'zh-CN',
        minView: 1,
        autoclose: true,
        format: 'yyyy-mm-dd hh:ii:ss',
        pickerPosition: "bottom-right"
    });

    //清空门诊查询条件
      $('#clearmenzhen').on('click',
         function() {
	        $("#menzhen :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
	         /**********************默认初始化门诊查询条件****************************/
//	         $("#askperson_mz").val("");
            document.getElementById("regdept_mz").options.selectedIndex = 0; //回到初始状态
            $("#regdept_mz").selectpicker('refresh');//对searchPayState这个下拉框进行重置刷新
            document.getElementById("askperson_mz").options.selectedIndex = 0; //回到初始状态
            $("#askperson_mz").selectpicker('refresh');//对searchPayState这个下拉框进行重置刷新
            static_organization = '<%=ChainUtil.getCurrentOrganization(request)%>';
            initHosSelectListNoCheck('organization_mz', static_organization)
            // getAllDeptByOrganization(static_organization);
            $('#firstaskperson').selectpicker('val', '');
            $("#askperson_mz").selectpicker('val', '');
         /**********************默认初始化门诊查询条件END****************************/

      });


    $('#query').on('click',
    function() {
    	$(this).attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
		$(this).text("查询中");
        //加载内容
    	$('#table1').bootstrapTable('refresh', {
            'url': pageurl1
        });
    });
    setHeight();
    $(window).resize(function(){
    	setHeight();
    	
    	/*  table加载好，判断是否出现滚动条并进行调整，需要传入table父容器的class名字*/
        setTableHeaderWidth(".tableBox");
    });
});

/**
 * 获取当前门诊的所有部门列表 搜索单独加载selectpicker 2019/11/19 lutian
 */
function getAllDeptByOrganizationSingle(organization,obj) {
        var dict = $("#"+obj);
        var url = contextPath + "/YZDeptAct/getAllDeptByOrganization.act?1=1";
        if (organization) {
            url += '&organization=' + organization;
        }
        $.axse(url, null,
        function(data) {
            var list = data.list;
            if (list != null && list.length > 0) {
                dict.empty();
                dict.append("<option value=''>请选择</option>");
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.deptName + "</option>");
                }
                dict.selectpicker("refresh");
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });
}

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
        var url = projectName + '/YZPersonAct/getUserListByDeptId.act?deptId=' + value+'&showleave='+showleave+'&organization='+ $("#organization_mz").val();
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
    return $.map($table1.bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
}

//指定客服
function setKeFu() {
    selectedrows = getIdSelections();
    if (selectedrows.length == 0) {
        layer.alert('请勾选复选框，选择需要指定客服的患者(可多选)' );
    } else {
        layer.open({
            type: 2,
            title: '指定客服',
            shadeClose: false,
            shade: 0.6,
            area: ['90%', '98%'],
            content: contextPath+'/KQDS_ChangeKeFuAct/setKefu.act?menuid='+"<%=menuid%>"+'&organization='+$("#organization_mz").val()
        });
    }
}

function queryParamsB(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	sortName:this.sortName,//排序名称
        sortOrder:this.sortOrder,//排序类型
        limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1,
        isdelete: 1,
        organization: $("#organization_mz").val(),
        regdept: $("#regdept_mz").val(),
        askperson: $("#askperson_mz").val(),
        firstaskperson:$("#firstaskperson").val(),
        orderstatus: $("#orderstatus").val(),
        starttime: $("#starttime").val(),
        yyxm: $("#yyxm").val(),
        endtime: $("#endtime").val(),
        username: $("#musername").val(),
        createuser: $("#createuser").val(),
        cjr: $("#cjr").val()
    };
    return temp;
}

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	sortName:this.sortName,//排序名称
        sortOrder:this.sortOrder,//排序类型
        limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1,
        isdelete: 1,
        organization: $("#organization_mz").val(),
        regdept: $("#regdept_mz").val(),
        askperson: $("#askperson_mz").val(),
        firstaskperson:$("#firstaskperson").val(),
        orderstatus: $("#orderstatus").val(),
        starttime: $("#starttime").val(),
        yyxm: $("#yyxm").val(),
        endtime: $("#endtime").val(),
        username: $("#musername").val(),
        createuser: $("#createuser").val(),
        cjr: $("#cjr").val()
    };
    return temp;
}
//加载门诊表格
function inittablemenzhen() {
    $table1.bootstrapTable({
        url: pageurl1,
        dataType: "json",
        cache: false,
        queryParams: queryParams,
        singleSelect: false,
        striped: true,
        pagination: true,//是否显示分页（*）
        pageSize: 30,
        pageList : [15, 20, 25,30],//可以选择每页大小
        sidePagination: "server",//后端分页 
        paginationShowPageGo: true,
        onDblClickCell: function(field, value, row, td) {
            //双击事件 调用提交回访结果方法 
            goToUserCenterPage(row.usercode);
        },
        //点击分页器触动方法
        onPageChange:function(){
        	//加了分页器之后高度自适应
           	$(".fixed-table-container").height($(".fixed-table-container").height()+50+"px");
        },
        onLoadSuccess: function(data) { //加载成功时执行
        	//console.log($(".fixed-table-container").height());
        
        	//console.log("门诊预约==---=="+JSON.stringify(data));
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","auto").css("pointer-events","auto");
				$("#query").text("查询");
			}
        	//加了分页器之后高度自适应
           	$(".fixed-table-container").height($(".fixed-table-container").height()+50+"px");
        
        	if_mz_table_int = "mz_table_has_init";
        	
            var tableList = data.nums[0];
            $("#zong").html(tableList.allnum);
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
                if (data.rows[i].orderstatus == "已上门") {
                    sms = sms + 1;
                } else {
                    wsms = wsms + 1;
                }
            } */
            $("#cj").html(tableList.cjstatus);
            $("#wcj").html(Number(tableList.allnum)-Number(tableList.cjstatus));
            $("#sms").html(tableList.orderstatus);
            $("#wsms").html(Number(tableList.allnum)-Number(tableList.orderstatus));
            
            /*  table加载好，判断是否出现滚动条并进行调整，需要传入table父容器的class名字*/
            setTableHeaderWidth(".tableBox");
        },
        rowStyle: function(row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (row.isdelete == "已取消") {
                strclass = 'danger'; //还有一个active
            } else {
                return {};
            }
            return {
                classes: strclass
            };
        },
        //basic', 'all', 'selected'. 
        columns: [
        	{
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
               var pageSize = $('#table1').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
               var pageNumber = $('#table1').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
               return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
              }
        },
            {
            title: '患者ID',
            field: 'uid',
            align: 'center',
            visible:false,
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="time" title="' + value + '">' + value + '</span>';
            }
        },
        	{
            title: '门诊',
            field: 'organizationname',
            align: 'center',
            /* sortable: true, */
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
            title: '项目分类',
            field: 'orderitemtype',
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
                    return '<span class="time" title="' + value + '">' + getDatetimeToMinutes(value) + '</span>';
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
                    return '<span class="time" title="' + value + '">' + getDatetimeToMinutes(value) + '</span>';
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
                    return '<span class="time" title="' + value + '">' + getDatetimeToMinutes(value) + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '预约状态',
            field: 'isdelete',
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
			title: '患者标识',
			field: 'iscreatelclj',
			align: 'center',
			sortable: true,
			formatter: function (value, row, index) {
				//console.log("网电预约=" + JSON.stringify(row));
				var iconhtml = "";
				if (value != "" && value != null) {
					iconhtml += '<img class="iscreatelclj" src= ' + contextPath + '/static/image/kqdsFront/tag/clinical.jpg/>';
				}
				return iconhtml == "" ? "-" : iconhtml;
			}
		},
        {
            title: '取消人',
            field: 'delperson',
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
            title: '取消原因',
            field: 'delreason',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="remark" title="' + value + '">' + value + '</span>';
                }
            }
        },
        {
            title: '创建人',
            field: 'cjr',
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
            title: '建档人',
            field: 'jdr',
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
            title: '建档时间',
            field: 'jdsj',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="time" title="' + value + '">' + getDatetimeToDay(value) + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '客服',
            field: 'kefu',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
            	if (value != "" && value != null) {
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $table1.find('tr.success').data('index'); //获得选中的行
        clickrow = $table1.bootstrapTable('getData')[index];
    });
}

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
//导出
function exportTable() {
	if(isClick) {
		isClick = false;
		// console.log("生成报表")
		var fieldArr = [];
		var fieldnameArr = [];
		$('#table1 thead tr th').each(function() {
			var field = $(this).attr("data-field");
			if (field != "") {
				fieldArr.push(field); //获取字段
				fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
			}
		});
		var param = JsontoUrldata(queryParamsExport());
		var url = pageurl1 + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
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
				a.download = "门诊预约查询";
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
function queryParamsExport(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        isdelete: 1,
        organization: $("#organization_mz").val(),
        regdept: $("#regdept_mz").val(),
        askperson: $("#askperson_mz").val(),
        firstaskperson:$("#firstaskperson").val(),
        orderstatus: $("#orderstatus").val(),
        starttime: $("#starttime").val(),
        yyxm: $("#yyxm").val(),
        endtime: $("#endtime").val(),
        username: $("#musername").val(),
        createuser: $("#createuser").val(),
        cjr: $("#cjr").val()
    };
    return temp;
}
//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height();
    $('#table1').bootstrapTable('resetView', {
        height: baseHeight - $(".searchModule").outerHeight()-21
    });
    //console.log($(".fixed-table-container").height()+"-------*");
    //加了分页器之后高度自适应
    $(".fixed-table-container").height($(".fixed-table-container").height()+50+"px");
}


function getButtonPower() {
    var menubutton1 = "";
    //console.log("按钮-=--=="+JSON.stringify(listbutton));
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "zdkf") {
            menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="setKeFu()">指定客服</a>&nbsp;';
        } else if(listbutton[i].qxName == "mzyy_scbb"){
        	$("#mzyy_scbb").removeClass("hidden");
        }
    }
    $("#zdkf").html(menubutton1);
}

function initPersonSelectByOrganization() {
    var url = contextPath + "/YZPersonAct/getPersonListByDeptType.act?depttype=" + "<%=ConstUtil.DEPT_TYPE_0 %>"+"&organization="+$("#organization_mz").val();
    $.axse(url, null,
    function(data) {
        var list = data.list;
        if (list != null && list.length > 0) {
        	$("#firstaskperson").empty();
            //dict.selectpicker("refresh");
            $("#firstaskperson").append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                $("#firstaskperson").append("<option value='" + optionStr.seqId + "'>" + optionStr.userName + "</option>");
            }
            $('.firstaskperson').selectpicker("refresh");//第一咨询下拉框添加搜索功能 lutian
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
}
</script>
</body>
</html>