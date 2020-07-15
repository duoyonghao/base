<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	// 获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
	YZPerson person = SessionUtil.getLoginPerson(request);
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
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<style>
	.tableBox{
		border-bottom:1px solid #ddd;
		border-radius:5px;
	}
	.centerWrap .columnWrap .columnBd ul{
		overflow: visible;
	}
	.centerWrap .columnWrap .columnBd ul li{
		 margin-left: 0px;
	}
</style>
<title>就诊提醒</title> <!-- 只从医疗中心 > 就诊情况进入  -->
</head>
<body>
<div id="container">
    <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
           		<div class="columnHd">
				    <span class="title" id="cxtitle">积分查询</span>
				</div>
                <div class="columnBd">
                    <div class="tableBox">
                        <table id="table" class="table-striped table-condensed table-bordered"></table>
                    </div>
                </div>
            </div>
            <div class="searchWrap">
               <div class="searchToggleBtnGroup">
                	<span class="ptcx checked">
                		积分查询
                	</span>
                	<span>
                		患者查询
                	</span>
                </div>
                <div class="formBox">
                    <div class="searchBox">
                    	<div class="kv">
		                    <label>门诊</label>
		                    <div class="kv-v">
		                        <select id="organization" name="organization"></select>
		                    </div>
		                </div>
                        <div class="kv">
                            <label id="czsj">操作时间</label>
                            <div class="kv-v">
                                <input size="16" type="text" name="fzsj" id="fzsj" value="" placeholder="请选择时间" readonly class="birthDate" > 
                            </div>
                        </div>
                         <div class="kv">
                            <label>到</label>
                            <div class="kv-v">
                                <input size="16" type="text" name="fzsj2" id="fzsj2" value="" placeholder="请选择时间" readonly class="birthDate" >
                            </div>
                        </div>
                         <div class="kv">
                            <label style="width:70px;">模糊</label>
                            <div class="kv-v">
                                  <input type="text" name="usercodeorname" id="usercodeorname" placeholder="患者编号/姓名/手机号码"  />
                            </div>
                        </div>
                    </div>
                    <div style="clear:both;float:right;" id="bottomBarDdiv">
                     	
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
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/Load.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/DataLazyLoad.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var listbutton;
var onclickrowOobj = "";
var clickobj = "0";
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var pageurl = contextPath+'/KQDS_Integral_RecordAct/getRecordList.act';
var nowday;
$(function() {
    initHosSelectList4Front('organization'); // 连锁门诊下拉框	
    //当前日期
    nowday = getNowFormatDate();
    $("#fzsj").val(nowday);
    $("#fzsj2").val(nowday);

    //时间选择
    $(".birthDate").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        format: 'yyyy-mm-dd',
        pickerPosition: "top-right"
    });
    //绑定两个时间选择框的chage时间
    $("#fzsj,#fzsj2").change(function() {
        timeCompartAndFz("fzsj", "fzsj2");
    });
    //4、表格初始化
    initTable();
    togglemodel.initial("jfzx",pageurl);/*wl 初始化 开关模块 */
    //获取当前页面所有按钮
    getButtonAllCurPage("<%=menuid%>");

    $(window).resize(function() {
        setWidth();
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });
    /* 常用查询 按钮 高级查询  按钮*/
    initSearchToggleBtnGroup();
});
function initSearchToggleBtnGroup(){
	$(".searchToggleBtnGroup").on("click","span",function(){
		//触发清空按钮 事件   清除所有查询条件
		clean();
		$("#fzsj").val(nowday);
		$("#fzsj2").val(nowday);
		layer.closeAll();
		loadedData = [];
		nowpage = 0;
		if($(this).hasClass("ptcx")){//点击的是积分查询按钮
			pageurl = contextPath+'/KQDS_Integral_RecordAct/getRecordList.act';
			clickobj = "0";
			onclickrowOobj = "";
			$("#czsj").html("操作时间");
			$("#cxtitle").html("积分查询");
			$("#scbb").show();
			initTable();
		}else{
			pageurl = contextPath + '/KQDS_UserDocumentAct/selectWithNopage2.act';
			clickobj = "1";
			onclickrowOobj = "";
			$("#czsj").html("建档时间");
			$("#cxtitle").html("患者查询");
			$("#scbb").hide();
			initTableForUser();
		}
		//重置高度
		setHeight();
		if($(this).hasClass("checked")){//按钮样式的切换
			return;
		}else{
			$(this).addClass("checked").siblings().removeClass("checked");
		}
	});	
}
function clean() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
}

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	sortName:this.sortName,
    	sortOrder:this.sortOrder,
    	limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1,
        fzsj: $('#fzsj').val(),
        fzsj2: $("#fzsj2").val(),
        organization: $('#organization').val(),
        usercodeorname: $('#usercodeorname').val()
    };
  
    //console.log(JSON.stringify(temp)+"temp2");
    
    	 return temp;  
}

function initTable() {
	//初始化表格所在div
    var tableObj = $('#table').bootstrapTable('getOptions');
    if (tableObj.length == undefined) { // 如果length 存在，则说明是第一次加载，bootstrap table还没初始化
        if (pageurl == tableObj.url) { // 重复点击
            $('#table').bootstrapTable('refresh', {
                'url': pageurl
            });
            return;
        } else { // 切换Tab
            $('#table').bootstrapTable('destroy'); // 销毁bootstrap,tab切换无效
        }
    }
    var tableHeight = 0;
    /* 计算table需要的高度 */
    /* 根据当前页面 计算出table需要的高度 */
    tableHeight = $(window).outerHeight() - $(".searchWrap").outerHeight() - $(".centerWrap .columnWrap .columnHd").outerHeight() - 70;
    /* 框架要使用改table */
    $(".tableBox").html("<table id='table' class='table-striped table-condensed table-bordered' data-height='" + tableHeight + "'></table>");

    /*wl----首次加载时 计算table高度————————————结束  */

    //初始化表格所在div
    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        onLoadSuccess:function(data){
            //解除查询按钮禁用 lutian
            if(data){
                $("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","auto").css("pointer-events","auto");
                $("#query").text("查询");
            }
            if(nowpage == 0 && data.total>0){
       		    maxpage = Math.floor(data.total/pagesize)+1; 
	      	}
	      	//分页加载
	      	showdata("table",data.rows);
	      	setHeight();
        	/*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
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
            title: '生成时间',
            field: 'createtime',
            sortable: true,
            align: 'center',
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span title="' + value + '">' + value.substring(0) + '</span>';
                    return html;
                }else{
                	return "<span>-</span>";
                }

            }
        },
        {
            title: '操作人',
            field: 'createusername',
            sortable: true,
            align: 'center'
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return "<span title='" + value + "'>" + value + "</span>";
                } else {
                    return "<span></span>";
                }
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
            title: '积分方式',
            field: 'jftype',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '获得积分',
            field: 'jfmoney',
            align: 'center',
            sortable: true
        },
        {
            title: '剩余积分',
            field: 'syjfmoney',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	if(Number(value) < 0){
            		return '<span  style="color:red">' + value + '</span>';
            	}else{
            		return '<span>' + value + '</span>';
            	}
                
            }
        },
        {
            title: '备注',
            field: 'remark',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span title="' + value + '" style="width:100px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;display:block;cursor:pointer;">' + value + '</span>';
            }
        },
]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        $('#tabIframe').attr('src', $('#tabIframe').attr('src')); //个人中心
    });
}


function queryParamsB(params) {
	 var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    		sortName:this.sortName,
    		sortOrder:this.sortOrder,
    		limit: params.limit,   //页面大小
            offset: params.offset, //页码 
            pageIndex : params.offset/params.limit + 1,
       		organization: $('#organization').val(),
            starttime: $('#fzsj').val(),
            endtime: $('#fzsj2').val(),
            username: $('#usercodeorname').val()
        };
	 return temp;
}

function initTableForUser() {
    //初始化表格所在div
    var tableObj = $('#table').bootstrapTable('getOptions');
    if (tableObj.length == undefined) { // 如果length 存在，则说明是第一次加载，bootstrap table还没初始化
        if (pageurl == tableObj.url) { // 重复点击
            $('#table').bootstrapTable('refresh', {
                'url': pageurl
            });
            return;
        } else { // 切换Tab
            $('#table').bootstrapTable('destroy'); // 销毁bootstrap,tab切换无效
        }
    }
    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParamsB,
        cache: true,
        pagination: true,//是否显示分页（*）
	    pageSize: 30,
	    pageList : [15,25,35,50],//可以选择每页大小
        striped: true,
        clickToSelect: false,
        singleSelect: false,
        paginationShowPageGo: true,
        sidePagination: "server",//后端分页 
        onLoadSuccess: function(data) { //加载成功时执行
        	//console.log("=-=-=-"+JSON.stringify(data));
        	/* if(nowpage == 0 && data.total>0){
        		    //maxpage = Math.floor(data.total/pagesize)+1; 
 	      	}
 	      	//分页加载
 	      	showdata("table",data.rows); */
 	      	setHeight();
         	/*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        },
        columns: [
        {
            title: '序号',
            field: 'Number',
            align: 'center',
            formatter:function(value, row, index){
		    	 return index+1;
		    }
        },  
        {
            title: '建档时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                	return '<span>' + value + '</span>';
                }else{
                	return "";
                }
            }
        },
        {
            title: '最近到院时间',
            field: 'lasttime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                	return '<span>' + value + '</span>';
                }else{
                	return "";
                }
            }
        },
        {
            title: '病人编号',
            field: 'usercode',
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
            title: '姓名',
            field: 'username',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name">' + value + '</span>';
                    return html;
                }
            }
        },
        {
            title: '性别',
            field: 'sex',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '年龄',
            field: 'age',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "0") {
                    return "<span>-</span>";
                } else {
                    return "<span>"+value+"</span>";
                }
            }
        },
        {
            title: '第一咨询',
            field: 'askperson',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name">' + value + '</span>';
                    return html;
                }
            }
        },
        {
            title: '医生',
            field: 'doctor',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name">' + value + '</span>';
                    return html;
                }
            }
        },
        {
            title: '手机号码1',
            field: 'phonenumber1',
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
            title: '手机号码2',
            field: 'phonenumber2',
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
            title: '患者来源',
            field: 'devchannelname',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return "<span class='source'>"+value+"</span>";
            }
        },
        {
            title: '来源子分类',
            field: 'nexttypename',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span class="source">'+value+'</span>'
            }
        },
        {
            title: '省',
            field: 'provincename',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '市',
            field: 'cityname',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '区',
            field: 'townname',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '建档人',
            field: 'createuser',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        }, {
            title: '有无回访',
            field: 'ywhf',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "无回访") {
                  	return "<span class='label label-danger'>无回访</span>";
                }else{
                	return "<span class='label label-success'>有回访</span>";
                }
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

function searchHzda(thi) {
	layer.closeAll();
	loadedData = [];
	nowpage = 0;
    var fzsj = $("#fzsj").val();
    var fzsj2 = $("#fzsj2").val();
    var usercodeorname = $("#usercodeorname").val();
    if (fzsj == "" && fzsj2 == "" && usercodeorname == "") {
        layer.alert('请选择查询条件' );
        return false;
    }
    $(thi).attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
    $(thi).text("查询中");
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
//手动增加积分
function add() {
	if(onclickrowOobj == '' || onclickrowOobj.usercode == ''){
		layer.alert('请选择患者！', {
	              
	    });
		return false;
	}
    var msg = "确定给【" + onclickrowOobj.username + "】手动增加积分？ ";
    layer.confirm(msg, {
        btn: ['确认', '放弃'] 
    },
    function() {
        layer.open({
            type: 2,
            title: '手动增加积分',
            maxmin: true,
            shadeClose: false,
            area: ['520px', '300px'],
            content: contextPath + '/KQDS_Integral_RecordAct/toAddIntegral.act?usercode='+onclickrowOobj.usercode
        });
    });
}
//手动减少积分
function sub() {
	if(onclickrowOobj == '' || onclickrowOobj.usercode == ''){
		layer.alert('请选择患者！', {
	              
	    });
		return false;
	}
    var msg = "确定给【" + onclickrowOobj.username + "】手动减少积分？ ";
    layer.confirm(msg, {
        btn: ['确认', '放弃'] 
    },
    function() {
        layer.open({
            type: 2,
            title: '手动减少积分',
            maxmin: true,
            shadeClose: false,
            area: ['520px', '300px'],
            content: contextPath + '/KQDS_Integral_RecordAct/toSubIntegral.act?usercode='+onclickrowOobj.usercode
        });
    });
}
// 该方法执行完毕后，再计算高度、宽度，否则计算不准确
function getButtonPower() {
    var menubutton1 = '<a href="javascript:void(0);"  onclick="clean()" class="kqdsCommonBtn">清空</a>';
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "jfcx_scbb") {
            menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" id="scbb" onclick="exportTable();">生成报表</a>';
        }else if(listbutton[i].qxName == "jfcx_add"){
        	menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="add();">手动增加</a>';
        }else if(listbutton[i].qxName == "jfcx_sub"){
        	menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="sub();">手动减少</a>';
        }
    }
    menubutton1 += '<a href="javascript:void(0);" onclick="searchHzda(this)" class="kqdsSearchBtn" id="query">查询</a>';
    $("#bottomBarDdiv").append(menubutton1);

    //计算主体的宽度
    setWidth();
    setHeight();
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
</script>
</body>
</html>
