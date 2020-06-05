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
<title>系统主页</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/layer-v2.4/layer/skin/blueSkin/skin_style.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/firstcenter.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<style>
	.fixed-table-container{
		border:none;
	}
	.tableBox{
		border:1px solid #ddd;
		overflow:hidde;
		border-radius:6px;
	}
	.fixed-table-pagination{
		border-top: 1px solid #ddd;
	}
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
	.centerWrap .columnWrap .columnBd ul li.current {
    color: #00a6c0;
    border-bottom: 0px solid #00a6c0;
    }
    input[type=text],.kv .kv-v input[type=text]{padding:0px !important; height:30px !important;}
</style>
</head>
<body>
<div id="container">
    <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
            	<div class="columnHd">
					<span class="title">今日工作</span>
				</div>
                <div class="columnBd">
                    <ul id="menuul">
                       
                    </ul>
                    <div class="tableBox">
                        <table id="table" class="table-striped table-condensed table-bordered" ></table>
                    </div>
                </div> 
            </div>
            <div id="gongzuol" style="display:none;">
                <div class="columnBd">
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
			<%@include file="//inc/rightPartInfo.jsp" %>
		</div>
    </div>
</div>
</body>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/indexTab.js"></script> <!-- 在这里引用是给 pushBox.jsp使用的，会调用其initTable方法 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<script type="text/javascript">
// 获取该页面的按钮数组
var listbutton = null; // 这个变量通过getButtonAllCurPage方法进行初始化
var contextPath = "<%=contextPath%>";
var onclickrowOobj = "";

// 标识 是否为 今日工作   0 不是  1 是
var IS_TODAY_WORK = 0;
var menuid = "<%=menuid%>";
var personseqid = "<%=person.getSeqId()%>";
var personrole = "<%=person.getUserPriv()%>";
var wquerycounturl = '<%=contextPath%>/KQDS_Net_OrderAct/getCountIndex.act';
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var personroleother = "<%=person.getUserPrivOther()%>";

var loadperson='<%=person.getUserPriv()%>';
var load=loadperson.split(",");//登陆这权限
var allowedperson='<%=SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK)%>';
var allowed=allowedperson.split(",");//允许权限

$(function() {
    //获取当前页面所有按钮
    getButtonAllCurPage(menuid);
    /* 左侧个人中心的按钮样式控制js已经被抽取到rightPartInfo.jsp页面中 */
	
    togglemodel.initial("jrgz",null,0);/*wl 初始化 开关模块 */
    

    //4、表格初始化
    layer.config({
        skin: 'self-blue-skin' //自定义样式demo-class
    });

    //计算主体的宽度，计算高度的方法放到bootstrap表格加载完成后的 onsuccess方法中了
    setWidth();

    $(window).resize(function() {
        setWidth();
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });
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

// 系统主页面，显示 今日回访   今日预约人数
function initcount() {
    //网电
    $.axseY(wquerycounturl, null,
    function(data) {
        //今日回访
        $("#vdataCount").html(data.vdataCount);
        //今日预约
        $("#mdataCount").html(data.mdataCount);
        //工作量
        $("#gdataCount").html(data.gdataCount);
        //今日手术
        $("#roomdataCount").html(data.roomdataCount);
        //推广计划
        $("#tgjhCount").html(data.tgjhCount);
        //推广计划
        $("#ckbjCount").html(data.ckbjCount);
    },
    function() {});
}

// 工作量
function todaypayTable() {

    $("#gongzuol").show();
    IS_TODAY_WORK = 1;

    var todaydate = getNowFormatDate();
    var paypageurl = '<%=contextPath%>/KQDS_CostOrderAct/getAll4IndexGzl.act?isSelectCurrentOrg=1&starttime=' + todaydate + "&endtime=" + todaydate;

    // 初始化表格所在div
    $(".tableBox").html('<table id="table" class="table-striped table-condensed table-bordered" data-height="300"></table>');

    $('#table').bootstrapTable('refresh', {
        'url': paypageurl
    });

    $('#table').bootstrapTable({
        url: paypageurl,
        dataType: "json",
        pagination: true,//是否显示分页（*）
	    pageSize: 30,
	    pageList : [15, 20, 25,30],//可以选择每页大小
        onLoadSuccess: function(data) { //加载成功时执行
        	
        	//判断登录
        	var existornot=isExist(total);
        	if(!existornot){
        		$('#table').bootstrapTable('hideColumn', 'devchannel');
        	}else{
        		
        	} 
        	
        	
            var tableList = $('#table').bootstrapTable('getData');
            var xiaoji = 0;
            var mian = 0;
            var ying = 0;
            var xian = 0;
            var qian = 0;
            for (var i = 0; i < tableList.length; i++) {
                xiaoji += Number(tableList[i].totalcost);
                mian += Number(tableList[i].voidmoney);
                ying += Number(tableList[i].shouldmoney);
                xian += Number(tableList[i].actualmoney);
                qian += Number(tableList[i].y2);
            }
            $("#xiaoji").html(xiaoji.toFixed(2));
            $("#mian").html(mian.toFixed(2));
            $("#ying").html(ying.toFixed(2));
            $("#xian").html(xian.toFixed(2));
            $("#qian").html(qian.toFixed(2));
            
            $("#table").bootstrapTable("resetView",{
          		height:$(window).outerHeight()-$(".columnWrap .columnHd").outerHeight()-$("#gongzuol").outerHeight()-$("#menuul").outerHeight()-53
          	});
            /*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
            
        },
      	//点击分页器触动方法
        onPageChange:function(){
        	//加了分页器之后高度自适应
           	//$(".fixed-table-container").height($(".fixed-table-container").height()+23+"px");
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
            title: '收费时间',
            field: 'sftime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value.indexOf("null") > -1) {
                    return "";
                } else {
                    var sftime = value.substring(0, 16);
                    return '<span class="time">' + sftime + '</span>';
                }
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
            title: '编号',
            field: 'usercode',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '电话',
            field: 'phonenumber1',
            align: 'center',
            formatter: function(value, row, index) {
                if (canlookphone == 0) {
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '<span>-</span>';
                }

            }
        },
        {
            title: '成交情况',
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
            title: '咨询',
            field: 'askperson',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return '<span class="name">'+value+'</span>'
            }
        },
        {
            title: '医生',
            field: 'doctorname',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return '<span class="name">'+value+'</span>'
            }
        },
        {
            title: '挂号医生',
            field: 'regdoctorname',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return '<span class="name">'+value+'</span>'
            }
        },

        {
            title: '患者来源',
            field: 'devchannel',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                var devchannel = "-";
                if (row.devchannel != null && row.devchannel != "") {
                    devchannel = row.devchannel;
                }
                if (row.nexttype != null && row.nexttype != "") {
                    devchannel = devchannel + "-" + row.nexttype;
                }
                return '<span class="source" title="' + devchannel + '">' + devchannel + '</span>';
            }
        },
        {
            title: '小计',
            field: 'totalcost',
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
            title: '应收',
            field: 'shouldmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">' + value + '</span>';
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
            title: '缴费',
            field: 'actualmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">' + value + '</span>';
            }
        },
        {
            title: '开单时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                html = '<span>' + value.substring(5) + '</span>';
                return html;
            }
        },
        {
            title: '开单人',
            field: 'createuser',
            align: 'center',
            formatter: function(value, row, index) {
                return "<span class='name'>" + value + "</span>";
            }
        },
        {
            title: '收费人',
            field: 'sfuser',
            align: 'center',
            formatter:function(value){
            	return '<span class="name">'+value+'</span>';
            }
        },
        {
            title: '建档人',
            field: 'jduser',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span class="name">'+value+'</span>';
            }
        },
        {
            title: '建档时间',
            field: 'jdtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    html = '<span>' + value + '</span>';
                    return html;
                }else{
                	return '<span></span>'
                }
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        //去除之前选中的行的，选中样式
        $('.success').removeClass('success');
        //添加当前选中的 success样式用于区别
        $(element).addClass('success');
        //获得选中的行
        var index = $('#table').find('tr.success').data('index');
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        $('#tabIframe').attr('src', $('#tabIframe').attr('src'));
        //togglemodel.switchDiv();
    });
}
function queryParams(params) {
    var nowday = getNowFormatDate();
    var starttime = nowday + " 00:00:00";
    var endtime = nowday + " 23:59:59";
    var temp = {
        jrroom: 1,
        endtime: endtime,
        starttime: starttime,
        sortName:this.sortName,//排序名称
        sortOrder:this.sortOrder,//排序类型
    	limit: params.limit,   //页面大小
        offset: params.offset, //页码 
      	pageIndex : params.offset/params.limit + 1
    };
    return temp;
}
//今日手术tab
function todayRoomTable() {
    IS_TODAY_WORK = 0;
    $("#gongzuol").hide();

    // 加载表格前 初始化表格 不初始化的话切换表格时加载的表头不会变
    $(".tableBox").html('<table id="table" class="table-striped table-condensed table-bordered" data-height="200"></table>');
    var pageurl = contextPath + '/KQDS_RoomAct/selectNoPage.act';
    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        pagination: true,//是否显示分页（*）
	    pageSize: 30,
	    pageList : [15, 20, 25,30],//可以选择每页大小
        cache: false,
        striped: true,
        clickToSelect: false,
        singleSelect: false,
        paginationShowPageGo: true,
        sidePagination: "server",//后端分页 
        onLoadSuccess: function(data) {
            setHeight();
            //$(".fixed-table-container").outerHeight($(".centerWrap .columnWrap").outerHeight()-$("#menuul").outerHeight()-20);
          	//加了分页器之后高度自适应
           	//$(".fixed-table-container").height($(".fixed-table-container").height()+30+"px");
        },
      	//点击分页器触动方法
        onPageChange:function(){
        	//加了分页器之后高度自适应
           	$(".fixed-table-container").height($(".fixed-table-container").height()+33+"px");
        },
        rowStyle: function(row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (row.isdeletename == "已取消") {
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
		   },  
         {
            title: '手术室',
            field: 'roomname',
            align: 'center',
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '<span></span>';
                }
            }
        },
        {
            title: '手术状态',
            field: 'roomstatusname',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
            
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '<span></span>';
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
                    return '<span class="name"></span>';
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
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '<span>-</span>';
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
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "<span class='name'></span>";
                }
            }
        },
        {
            title: '护士',
            field: 'nursename',
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
            title: '种植系统',
            field: 'zzxtname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "<span></span>";
                }
            }
        },
        {
            title: '颗数',
            field: 'ks',
            align: 'center',
            
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '开始时间',
            field: 'starttime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '<span></span>';
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
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '<span></span>';
                }
            }
        },
        {
            title: '备注',
            field: 'remark',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="remark" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "<span class='remark'></span>";
                }
            }
        },
        {
            title: '预约状态',
            field: 'isdeletename',
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
            title: '取消人',
            field: 'delpersonname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return '<span class="name"></span>';
                }
            }
        },
        {
            title: '取消原因',
            field: 'delreason',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '创建人',
            field: 'createusername',
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
            title: '建档人',
            field: 'jdr',
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
            field: 'jdsj',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '<span></span>';
                }
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        // 去除之前选中的行的，选中样式
        $('.success').removeClass('success');
        // 添加当前选中的 success样式用于区别
        $(element).addClass('success');
        // 获得选中的行
        var index = $('#table').find('tr.success').data('index');
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        // 个人中心
        $('#tabIframe').attr('src', $('#tabIframe').attr('src'));
        //togglemodel.switchDiv();
    });
}



// 今日回访tab
function visitTable() {
	//onclickrowOobj="";
    // 设置标识为 非  工作量tab
    IS_TODAY_WORK = 0;
    // 隐藏工作量的统计表格
    $("#gongzuol").hide();
	
    
    
    var url = contextPath + '/KQDS_VisitAct/selectListNotByPersonIdToday.act';

    // 由于是多个tab切换，所以需要先清除样式
    $(".tableBox").html('<table id="table" class="table-striped table-condensed table-bordered" data-height="200"></table>');
	
    $('#table').bootstrapTable({
        url: url,
        dataType: "json",
        contentType : "application/x-www-form-urlencoded",   //必须有
        queryParams:queryParams,
        cache: true,
        pagination: true,//是否显示分页（*）
	    pageSize: 30,
	    pageList : [15,25,35,50],//可以选择每页大小
        striped: true,
        clickToSelect: false,
        singleSelect: false,
        paginationShowPageGo: true,
        sidePagination: "server",//后端分页 
        onLoadSuccess: function(data) { // 加载成功时执行
            setHeight();
        	//由于高度不同，不在setHeight()中统一重新设置table的高度了
            //$(".fixed-table-container").outerHeight($(".centerWrap .columnWrap").outerHeight()-$("#menuul").outerHeight()-20);
        },
      	//点击分页器触动方法
        onPageChange:function(){
        	//加了分页器之后高度自适应
           	$(".fixed-table-container").height($(".fixed-table-container").height()+33+"px");
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
					title : '门诊',
					field : 'organization',
					align : 'center',
					valign : 'middle',
					sortable : true,
					formatter : function(value, row, index) {
						 var pjid="table"+index+"organization";
				    	   getValueOrg(pjid,value);
	                	   return "<span id='"+pjid+"'></span>";
					}
				}, */
        {
            title: '回访状态',
            field: 'status',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                var htmlstr = "";
                if (row.status == "0") {
                    htmlstr = "<span style='color:red'>未回访</span>";
                } else if (row.status == "1") {
                    htmlstr = "<span style='color:green'>已回访</span>";
                }
                return htmlstr;
            }
        },
        {
            title: '回访时间',
            field: 'visittime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '回访人员',
            field: 'visitorname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return "<span class='name'>" + value + "</span>";
                }
            }
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            
            visible: false,
            switchable: false,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '患者手机',
            field: 'telphone',
            align: 'center',
            
            visible: false,
            switchable: false,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '患者性别',
            field: 'sex',
            align: 'center',
            
            visible: false,
            switchable: false
        },
        {
            title: '患者姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '回访分类',
            field: 'hfflname',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '回访要点',
            field: 'hfyd',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '" class="remark">' + value + '</span>';
                }
            }
        },
        {
            title: '提交人员',
            field: 'postpersonname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return "<span class='name'>" + value + "</span>";
                }
            }
        },
        {
            title: '回访结果',
            field: 'hfresult',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '" class="remark">' + value + '</span>';
                }
            }
        },
        {
            title: '满意度',
            field: 'mydname',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        // 去除之前选中的行的，选中样式
        $('.success').removeClass('success');
        // 添加当前选中的 success样式用于区别
        $(element).addClass('success');
        // 获得选中的行
        var index = $('#table').find('tr.success').data('index');
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        // 重新请求个人中心，根据 onclickrowOobj 这个值重载页面数据
        $('#tabIframe').attr('src', $('#tabIframe').attr('src'));
        //togglemodel.switchDiv();
    });
}
//今日预约
function hospitalOrderTable() {
    IS_TODAY_WORK = 0;
    $("#gongzuol").hide();

    // 加载表格前 初始化表格 不初始化的话切换表格时加载的表头不会变
    $(".tableBox").html('<table id="table" class="table-striped table-condensed table-bordered" data-height="200"></table>');
    var pageurl = contextPath + '/KQDS_Hospital_OrderAct/selectHospitalOrderList.act';

    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        /* contentType : "application/x-www-form-urlencoded",   //必须有 */
        queryParams: queryParams,
        cache: false,
        pagination: true,//是否显示分页（*）
	    pageSize: 30,
	    pageList : [15, 20, 25,30],//可以选择每页大小
        striped: true,
        paginationShowPageGo: true,
        sidePagination: "server",//后端分页  
        onLoadSuccess: function(data) {
            setHeight();
           // $(".fixed-table-container").outerHeight($(".centerWrap .columnWrap").outerHeight()-$("#menuul").outerHeight()-20);
          //加了分页器之后高度自适应
           	//$(".fixed-table-container").height($(".fixed-table-container").height()+23+"px");
        },
      	//点击分页器触动方法
        onPageChange:function(){
        	//加了分页器之后高度自适应
           	$(".fixed-table-container").height($(".fixed-table-container").height()+33+"px");
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
		        //return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
		        return index + 1;
		    }
		   },   
         {
            title: '上门状态',
            field: 'orderstatus',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == 1) {
                    return '<span style="color:green">已上门</span>';
                } else {
                    return '<span style="color:red">未上门</span>';
                }
            }
        },
        {
            title: '起始时间',
            field: 'starttime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    var html = value.substring(5);
                    return '<span>'+html+'</span>';
                }else{
                	return "<span></span>"
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
                    var html = value.substring(5);
                    return '<span>'+html+'</span>';
                }else{
                	return '<span></span>'
                }
            }
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '患者姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '预约分类',
            field: 'ordertypename',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '预约咨询/医生',
            field: 'askpersonname',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '预约项目分类',
            field: 'orderitemtypename',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return "<span>"+value+"</span>"
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
   		//console.log(row);
        // 去除之前选中的行的，选中样式
        $('.success').removeClass('success');
        // 添加当前选中的 success样式用于区别
        $(element).addClass('success');
        // 获得选中的行
        var index = $('#table').find('tr.success').data('index');
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        // 个人中心
        $('#tabIframe').attr('src', $('#tabIframe').attr('src'));
       	//togglemodel.switchDiv();
    });
}
//推广计划
function tgjhTable() {
    IS_TODAY_WORK = 0;
    $("#gongzuol").hide();

    // 加载表格前 初始化表格 不初始化的话切换表格时加载的表头不会变
    $(".tableBox").html('<table id="table" class="table-striped table-condensed table-bordered" data-height="200"></table>');
    var pageurl = contextPath + '/KQDS_ExtensionAct/getTodayList.act';

    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        cache: false,
        pagination: true,//是否显示分页（*）
	    pageSize: 30,
	    pageList : [15, 20, 25,30],//可以选择每页大小
        striped: true,
        onLoadSuccess: function(data) {
            setHeight();
            $(".fixed-table-container").outerHeight($(".centerWrap .columnWrap").outerHeight()-$("#menuul").outerHeight()-20);
        },
      	//点击分页器触动方法
        onPageChange:function(){
        	//加了分页器之后高度自适应
           	$(".fixed-table-container").height($(".fixed-table-container").height()+33+"px");
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
            title: '计划名称',
            field: 'jhname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '完成状态',
            field: 'status',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == 1) {
                    return '<span style="color:green">已完成</span>';
                } else {
                    return '<span style="color:red">未完成</span>';
                }
            }
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    var html = value.substring(5);
                    return '<span>'+html+'</span>';
                }else{
                	return '<span></span>'
                }
            }
        },
        {
            title: '截止时间',
            field: 'vtime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    var html = value.substring(5);
                    return '<span>'+html+'</span>';
                }else{
                	return '<span></span>'
                }
            }
        },
        {
            title: '创建人',
            field: 'cjr',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        // 去除之前选中的行的，选中样式
        $('.success').removeClass('success');
        // 添加当前选中的 success样式用于区别
        $(element).addClass('success');
        // 获得选中的行
        var index = $('#table').find('tr.success').data('index');
        //onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        // 个人中心
        //$('#tabIframe').attr('src', $('#tabIframe').attr('src'));
        //togglemodel.switchDiv();
    });
}
//库存报警
function ckbjTable() {
    IS_TODAY_WORK = 0;
    $("#gongzuol").hide();

    // 加载表格前 初始化表格 不初始化的话切换表格时加载的表头不会变
    $(".tableBox").html('<table id="table" class="table-striped table-condensed table-bordered" data-height="200"></table>');
    var pageurl = contextPath + '/KQDS_Ck_Goods_DetailAct/selectGoodsGjList.act';

    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        cache: false,
        pagination: true,//是否显示分页（*）
	    pageSize: 30,
	    pageList : [15, 20, 25,30],//可以选择每页大小
        striped: true,
        onLoadSuccess: function(data) {
            setHeight();
            $(".fixed-table-container").outerHeight($(".centerWrap .columnWrap").outerHeight()-$("#menuul").outerHeight()-20);
        },
      	//点击分页器触动方法
        onPageChange:function(){
        	//加了分页器之后高度自适应
           	$(".fixed-table-container").height($(".fixed-table-container").height()+33+"px");
        },
        rowStyle: function(row, index) {
            if (Number(row.nums) < Number(row.minnums)) { //低于下限
                return {
                    css: {
                        "color": "red"
                    }
                };
            }

            if (Number(row.nums) > Number(row.minnums)) { //高于上限
            	return {
                    css: {
                        "color": "black"
                    }
                };
            }

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
            title: '商品编号',
            field: 'goodscode',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span style="text-align:left;width:100%;">'+value+'</span>'
            }
        },
        {
            title: '商品名称',
            field: 'goodsname',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span style="text-align:left;width:100%;">'+value+'</span>'
            }
        },
        {
            title: '商品规格',
            field: 'goodsnorms',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '单位',
            field: 'goodsunit',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '当前库存',
            field: 'nums',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '下限',
            field: 'minnums',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                var html = "";
                if (Number(value) < 0) {
                    html = '';
                } else {
                    html = value;
                }
                return '<span>'+html+'</span>';
            }
        },
        {
            title: '上限',
            field: 'maxnums',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                var html = "";
                if (Number(value) == 0) {
                    html = '';
                } else {
                    html = value;
                }
                return '<span>'+html+'</span>';
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        // 去除之前选中的行的，选中样式
        $('.success').removeClass('success');
        // 添加当前选中的 success样式用于区别
        $(element).addClass('success');
        // 获得选中的行
        var index = $('#table').find('tr.success').data('index');
        //onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        // 个人中心
        //$('#tabIframe').attr('src', $('#tabIframe').attr('src'));
        //togglemodel.switchDiv();
    });
}
function getButtonPower() {
    var menubutton1 = "",menubutton2 = "",menubutton3 = "",menubutton4 = "",menubutton5 = "",menubutton6 = "";
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "jryy") {
            menubutton1 += '<li onclick="hospitalOrderTable();">今日预约&nbsp;&nbsp;<span id="mdataCount" class="notRead">0</span></li>';
        } else if (listbutton[i].qxName == "jrhf") {
            menubutton2 += '<li onclick="visitTable();">今日回访&nbsp;&nbsp;<span id="vdataCount" class="notRead">0</span></li>';
        } else if (listbutton[i].qxName == "jrroom") {
            menubutton3 += '<li onclick="todayRoomTable();">今日手术&nbsp;&nbsp;<span id="roomdataCount" class="notRead">0</span></li>';
        } else if (listbutton[i].qxName == "gzl") {
            menubutton4 += '<li onclick="todaypayTable();">工作量&nbsp;&nbsp;<span id="gdataCount" class="notRead">0</span></li>';
        } else if (listbutton[i].qxName == "tgjh") {
            menubutton5 += '<li onclick="tgjhTable();">推广计划&nbsp;&nbsp;<span id="tgjhCount" class="notRead">0</span></li>';
        } else if (listbutton[i].qxName == "ckbj") {
            menubutton6 += '<li onclick="ckbjTable();">库存报警&nbsp;&nbsp;<span id="ckbjCount" class="notRead">0</span></li>';
        }
    }
    menubutton1 +=menubutton2+menubutton3+menubutton4+menubutton5+menubutton6;
    $("#menuul").append(menubutton1);
    // 默认打开第一个tab
    var li = $(".columnBd").children("ul").children("li").eq(0);
    li.attr({
        "class": "current"
    });
    li.trigger("click");
    
 	// 系统主页面，显示 今日回访   今日预约人数
    initcount();
}
function setHeight() {

    // 这个页面，屏幕小会有横向滚动条，这个滚动条的高度是15
    // 这里可以可以根据屏幕分辨率，计算是否会出现滚动条
    var scrollHeight = 15;
    var windowHeight=$(window).height();
    var baseHeight  = $(window).height() - 15;
    optAreaHeight = $('.operateModel').outerHeight();
    $('.lookInfoWrap .columnWrap').height(baseHeight);//-scrollHeight
    $('.lookInfoWrap .columnWrap .columnBd').outerHeight(baseHeight);
    //console.log($(window).outerHeight()+","+$(".columnWrap .columnHd").outerHeight()+","+$("#gongzuol").outerHeight()+","+$(".operateModel").outerHeight()+","+$("#menuul").outerHeight()+","+33);
    //重置table的高度
    $("#table").bootstrapTable("resetView",{
  		height:$(window).outerHeight()-$(".columnWrap .columnHd").outerHeight()-$("#gongzuol").outerHeight()-$("#menuul").outerHeight()-43
  	});
    $('.tabIframe').height(baseHeight - 60);
    
  	//加了分页器之后高度自适应
   	$(".fixed-table-container").height($(".fixed-table-container").height()+31+"px");
  
}    
    

</script>
</html>