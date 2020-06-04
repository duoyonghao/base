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
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css"/>
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/Load.js"></script>
<title>短信发送</title>
</head>
<style type="text/css">

</style>
<body>
<div id="container">
    <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
                <div class="columnHd"><span class="title">发送日志</span></div>
                <div class="columnBd">
                    <div class="tableBox" >
                        <table id="table" class="table-striped table-condensed table-bordered"></table>
                    </div>
                </div>
                <div id="gongzuol">
	                <div class="columnBd">
	               		<ul class="dataCountUl">
	                		<li>总记录数:<span id="zong">0</span></li>
	                		<li>短信余额:<span id="dxye">0</span></li>
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
                    <div class="searchBox">
                       <table class="kqds_table">
				    		<tr>
				    			<td style="text-align:right;">所属门诊</td>
				    			<td style="text-align:left;">
										<select id="organization" name="organization"></select>
								</td>
				    			<td style="text-align:right;">发送时间</td>
				    			<td style="text-align:left;"> 
				    					<span class="birthDate">
				    						<input type="text" placeholder="开始日期" id="starttime" readonly>
				                        </span>
				                </td>
				    			<td style="text-align:right;">到</td>
				    			<td style="text-align:left;">
										<span class="birthDate">
				                             <input type="text"  placeholder="结束日期" id="endtime" readonly>
				                        </span>
								</td>
				    			
				    			<td style="text-align:right;">发送状态</td>
				    			<td style="text-align:left;">
				    					<select class="select2 dict" name="sendstate" id="sendstate">
											<option value="">请选择</option>
											<option value="1">已发送</option>
											<option value="0">未发送</option>
										</select>
				    			</td>
				    		</tr>
				    		
				    		<tr>
				    			<td style="text-align:right;">短信类别</td>
				    			<td style="text-align:left;">
				    					 <select class="dict" tig="dxfl" name="smstype" id="smstype" ></select>
				    			</td>
				    			<td style="text-align:right;">二级类别</td>
				    			<td style="text-align:left;"> 
				    					<select name="smsnexttype" id="smsnexttype" ></select>
				                </td>
				    			<td style="text-align:right;">发送方式</td>
				    			<td style="text-align:left;">
										<select class="select2 dict" name="smsstate" id="smsstate">
											<option value="">请选择</option>
											<option value="0">实时发送</option>
											<option value="1">定时发送</option>
										</select>
								</td>
				    			<td style="text-align:right;">模糊查询</td>
				    			<td style="text-align:left;">
				    				<input type="text" id="username" class="username" placeholder="请输入客户姓名、手机号"> 
				    			</td>
				    		</tr>
				    	</table> 
                    </div>
                    <div id="bottomBarDdiv" class="btnBar">
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
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/DataLazyLoad.js"></script>
</body>
<script type="text/javascript">
var listbutton;
var contextPath = "<%=contextPath%>";
var selectedrows = "";
var nowday;
var onclickrowOobj ="";
var pageurl = '<%=contextPath%>/KQDS_SmsAct/selectPage.act';
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var personrole = "<%=person.getUserPriv()%>";
var personroleother = "<%=person.getUserPrivOther()%>";
var loc = new Location();
$(function() {
	initHosSelectList4Front('organization'); // 连锁门诊下拉框
	
    initDictSelectByClass(); // 患者来源
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    //获取当前页面所有按钮
    getButtonAllCurPage("<%=menuid%>");
    /* 左侧个人中心的按钮样式控制js已经被抽取到rightPartInfo.jsp页面中 */

    //时间选择
    $(".birthDate input").datetimepicker({
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
    togglemodel.initial("xxcx",pageurl);
    //4、表格初始化
    initTable();
    
    $(window).resize(function() {
        setWidth();
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });
});
$('#smstype').change(function() {
	getSubDictSelectByParentCode(this.value,'smsnexttype');
});
function initTable() {
    //初始化表格所在div
    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        singleSelect: false,
        queryParams: queryParams,
        onLoadSuccess: function(data) { //加载成功时执行
        	if(nowpage == 0 && data.total>0){
        		maxpage = Math.floor(data.total/pagesize)+1; 
        		$("#zong").html(data.total);
        	}
        	$("#dxye").html(data.dxye+" 元");
        	//分页加载
        	showdata("table",data.rows);
        	$("#table").bootstrapTable("resetView",{
        		height:$(window).outerHeight()-$(".searchWrap").outerHeight()-$("#gongzuol").outerHeight()-$(".columnWrap .columnHd").outerHeight()-35
        	});
        	//计算主体的宽度
            setWidth();
            setHeight();
        	/*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        },
        columns: [
        {
            title: '门诊',
            field: 'organizationname',
            align: 'center',
            
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '患者姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            editable: true,
            formatter:function(value,row,index){
            	return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '短信类别',
            field: 'smstypename',
            align: 'center',
            
            sortable: true,
            editable: true,
            formatter:function(value,row,index){
            	return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '二级类别',
            field: 'smsnexttypename',
            align: 'center',
            
            sortable: true,
            editable: true,
            formatter:function(value,row,index){
            	return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '发送状态',
            field: 'sendstatename',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '发送时间',
            field: 'sendtime',
            align: 'center',
            
            sortable: true,
            editable: true,
            formatter:function(value,row,index){
            	return "<span class='time'>"+value+"</span>";
            }
        },
        {
            title: '发送方式',
            field: 'smsstatename',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        $('#tabIframe').attr('src', $('#tabIframe').attr('src')); //个人中心
    });
}
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	offset : 0,
    	limit :	 pagesize,
    	ispaging : 1,
   		organization: $('#organization').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        smstype: $('#smstype').val(),
        smsnexttype: $('#smsnexttype').val(),
        smsstate: $('#smsstate').val(),
        sendstate: $('#sendstate').val(),
        username: $('#username').val(),
        sendphone: $('#sendphone').val()
    };
    return temp;
}
function searchHzda() {
	loadedData = [];
	nowpage = 0;
    var organization = $('#organization').val();
    var starttime = $('#starttime').val();
    var endtime = $('#endtime').val();
    var smstype = $('#smstype').val();
    var smsnexttype = $('#smsnexttype').val();
    var smsststus = $('#smsststus').val();
    var sendstatus = $('#sendstatus').val();
    var username= $('#username').val();
    var sendphone = $('#sendphone').val();

    if (starttime == "" && endtime == "" && username == "" && smstype == "" && smsnexttype == "" && smsststus == "" && sendstatus == "" && sendphone == "") {
        layer.alert('请选择查询条件!' );
        return false;
    }
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
function goshowVisit(){
	 if (onclickrowOobj.seqId == null || onclickrowOobj.seqId == "") {
	        layer.alert('请选择一条短信记录', {
	              
	        });
	        return false;
	 }
	 parent.layer.open({
	       type: 2,
	       title: '短信内容',
	       maxmin: true,
	       shadeClose: true,
	       // 点击遮罩关闭层
	       area: ['550px', '520px'],
	       content: '<%=contextPath%>/KQDS_SmsAct/toSendSmsDetail.act?seqId=' + onclickrowOobj.seqId
	    });
}
function clean() {
	//定位滚动条位置
    /* $(".fixed-table-body").animate({ 
    	scrollTop: 400
    	}, 1000); */
    $(".fixed-table-body").scrollTop(400); 
 	var rgvalue = $("#organization").val();
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#organization").val(rgvalue); 
}

//计算界面宽高的设置
//setWidth() ,setHeight()函数移动到tableHeaderWidth.js

function getButtonPower() {
    var menubutton1 = "";
    menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="clean()">清空</a>';
    menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="goshowVisit()">详情</a>';
    menubutton1 += '<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="searchHzda()">查询</a>';
    
    $("#bottomBarDdiv").append(menubutton1);

    setHeight();
}
</script>
</html>
