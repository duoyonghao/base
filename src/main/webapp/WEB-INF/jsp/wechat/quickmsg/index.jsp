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
<title>快捷回复</title><!-- 点击预约中心 按钮进入  以表格方式展现 ，分网电和门诊预约 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<!-- 预约中心 里 网电预约和门诊预约的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/yyzx/yyzxSearch.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
 <!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/weixinModule.css" />
<style>
	.bootstrap-table{
	
	}
	.fixed-table-container{
		border:none;
	}
	.tableBox{
		overflow:hidden;
	}
	.clearfix{
		
    	margin-top: 2px;
	}
	.queryText{
		height:22px;
		line-height:22px;
	}
		.fixed-table-header{
		border-top-left-radius:6px;
		border-top-right-radius:6px;
	}
	.fixed-table-body{
		border-bottom-left-radius:6px;
		border-bottom-right-radius:6px;
		border-bottom:1px solid #ddd;
		border-left: 1px solid #ddd;
		border-right: 1px solid #ddd;
	}
</style>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>   

</head>
<body>
	<div class=" main">	
		<div class=" listWrap">	
			<div class="listHd">
				<span class="title">快捷回复</span>
			</div>
		</div>
		
		<div class="queryDiv">	<!--查询条件的父元素 -->
			<!-- .queryTitle标题行  .queryText “查询条件”字样样式-->
			<div class="queryTitle">查询条件</div> 
			<!-- .operateDiv条件选择 区的父元素 -->
			<div id="menzhen" class="operateDiv">
			<!--  .operateConditionDiv 左边条件选择-->
				<div class="operateConditionDiv">
					<table class="tableLayout">
						<tr>
							<td>
								<span class="commonText">门诊：</span>
							</td>
							<td>
								<select name="organization" id="organization_mz" ></select>
							</td>
							<td>
								<span class="commonText">回复分类：</span>
							</td>
							<td>
								<select class="dict" tig="WECHAT_KEYWORD" name="msgtype" id="msgtype" >
								</select>
							</td>
						</tr>
					</table>
				</div>
			<!--.operatebtnDiv 右边按钮组  -->	
				<div class="operatebtnDiv" id="operatebtnDiv">
					<a id="clearmenzhen" href="javascript:void(0);" class="kqdsCommonBtn">清空</a>
					<a onclick="refresh();" href="javascript:void(0);" class="kqdsSearchBtn">查询</a>
				</div>
			</div>
		</div>
		<div>
		    <!--表格-->
	    	<div class="tableBox" id="tableDiv1">
	           	<table id="table1" class="table-striped table-condensed table-bordered"></table>
	        </div>
		</div>
	</div>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var pageurl1 = '<%=contextPath%>/WXQuickMsgAct/selectPage.act';
var clickrow = "";
var static_organization = null;
var menuid = "<%=menuid%>";
var qxnameArr = ['mzyy_scbb'];
var func = ['exportTable'];
$(function() {
	//获取当前页面所有按钮
    getButtonAllCurPage("<%=menuid%>");
    /**********************默认初始化门诊查询条件****************************/
    static_organization = '<%=ChainUtil.getCurrentOrganization(request)%>';
    initHosSelectListNoCheck('organization_mz',static_organization); // 加载门诊列表
    $("#organization_mz").change(function() {
        var currSelect = $(this).val();
        getAllDeptByOrganization(currSelect);
    });
    /**********************默认初始化门诊查询条件END****************************/

    initDictSelectByClass(); //预约项目分类、咨询项目

    inittablemenzhen();
    
    //清空门诊查询条件
    $('#clearmenzhen').on('click',
    function() {
        $("#menzhen :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
        /**********************默认初始化门诊查询条件****************************/
        static_organization = '<%=ChainUtil.getCurrentOrganization(request)%>';
        initHosSelectListNoCheck('organization_mz', static_organization); // 加载门诊列表
        /**********************默认初始化门诊查询条件END****************************/
    });

    
    $(window).resize(function(){
    	setHeight();
    	/*  table加载好，判断是否出现滚动条并进行调整，需要传入table父容器的class名字*/
        setTableHeaderWidth(".tableBox");
    });
});
function refresh(){
	$('#table1').bootstrapTable('refresh', {
        'url': pageurl1
    });
}
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
   		offset : params.offset,
        limit :	 params.limit,
        organization: $("#organization_mz").val(),
        msgtype: $("#msgtype").val()
    };
    return temp;
}
//加载门诊表格
function inittablemenzhen() {
	$('#table1').bootstrapTable({
        url: pageurl1,
        dataType: "json",
        pagination: true,
        queryParams: queryParams,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        striped: true,
        sidePagination: "server",
        onLoadSuccess: function(data) { //加载成功时执行
        	setHeight();
            /*  table加载好，判断是否出现滚动条并进行调整，需要传入table父容器的class名字*/
            setTableHeaderWidth(".tableBox");
        },
        columns: [{
            title: '门诊',
            field: 'organizationname',
            align: 'center',
           
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="time" title="' + value + '">' + value + '</span>';
            }
        },{
            title: '回复分类',
            field: 'msgtypename',
            align: 'center',
           
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="time" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '回复内容',
            field: 'msgcontent',
            align: 'center',
           
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="time" style="width:fit-content;float:left;text-align:left;position:relative;top:-1px;" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table1').find('tr.success').data('index'); //获得选中的行
        clickrow = $('#table1').bootstrapTable('getData')[index];
    });
}
function add() {
    layer.open({
        type: 2,
        title: '新增回复',
        maxmin: true,
        shadeClose: false,
        area: ['600px', '350px'],
        content: contextPath+'/WXQuickMsgAct/toAddNew.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>'
    });
}
function edit() {
	if(clickrow == ""){
		layer.alert('请选择需要修改的信息！'  );
		return false;
	}
    layer.open({
        type: 2,
        title: '修改回复',
        maxmin: true,
        shadeClose: false,
        area: ['600px', '350px'],
        content: contextPath+'/WXQuickMsgAct/toEdit.act?seqId='+clickrow.seqId
    });
}
function detail() {
	if(clickrow == ""){
		layer.alert('请选择需要查看的信息！'  );
		return false;
	}
    layer.open({
        type: 2,
        title: '回复详情',
        maxmin: true,
        shadeClose: false,
        area: ['600px', '350px'],
        content: contextPath+'/WXQuickMsgAct/toDetail.act?seqId='+clickrow.seqId
    });
}
function del() {
	if(clickrow == ""){
		layer.alert('请选择需要删除的信息！'  );
		return false;
	}
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] 
    },
    function() {
        var url = 'WXQuickMsgAct/deleteObj.act';
        var pageParam = {
        		seqId: clickrow.seqId
            };
        var returnData = getDataFromServer(url, pageParam);
        if (returnData.retState == 0) {
        	layer.alert('删除成功！', {
                   end: function() {
                	refresh();
                }
            });
        } else {
            layer.alert('删除失败！'  );
        }
    });
}
//导出
function exportTable() {
    var fieldArr = [];
    var fieldnameArr = [];
    $('#table1 thead tr th').each(function() {
        var field = $(this).attr("data-field");
        if (field != "") {
            fieldArr.push(field); //获取字段
            fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
        }
    });
    var param = JsontoUrldata(queryParams());
    location.href = pageurl1 + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
}
//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height();
    $('#table1').bootstrapTable('resetView', {
        height: baseHeight - $(".listHd").outerHeight()-$(".queryDiv").outerHeight()-$(".clearfix").outerHeight()+40
    });
}
function getButtonPower() {
    var menubutton1 = "";
    for (var i = 0; i < listbutton.length; i++) {
    	 if (listbutton[i].qxName == "kjhf_add") {
         	menubutton1 += '<a onclick="add();" href="javascript:void(0);" class="kqdsCommonBtn">新增</a>';
         }else if (listbutton[i].qxName == "kjhf_edit") {
         	menubutton1 += '<a onclick="edit();" href="javascript:void(0);" class="kqdsCommonBtn">修改</a>';
         }else if (listbutton[i].qxName == "kjhf_del") {
         	menubutton1 += '<a onclick="del();" href="javascript:void(0);" class="kqdsCommonBtn">删除</a>';
         }
    }
    
    menubutton1 += '<a onclick="detail();" href="javascript:void(0);" class="kqdsCommonBtn">详情</a>';
    $("#operatebtnDiv").append(menubutton1);
    
}
</script>
</body>
</html>