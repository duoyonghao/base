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
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<title>就诊提醒</title> <!-- 只从医疗中心 > 就诊情况进入  -->
 <style>
    .searchWrap .kv input[type=text], .searchWrap .kv select{
    	width:110px;
    }
    .tableBox{
		border-radius:6px;
		border-bottom:1px solid #ddd;
	}
		/* 搜索框select */
	.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
	       width: 110px;   
	      }  
	.searchSelect>.btn { 
		    width: 110px; 
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
/* 	.searchWrap,.formBox{ */
/* 		overflow: visible; */
/* 	} */
 	.formBox .searchBox { 
 		overflow: visible; 
 	} 
 	.formBox , .searchWrap{
    	overflow: visible; 
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
				    <span class="title">复诊提醒</span>
				</div>
                <div class="columnBd">
                    <div class="tableBox">
                        <table id="table" class="table-striped table-condensed table-bordered"></table>
                    </div>
                </div>
            </div>
            <div class="searchWrap" style="padding-top:35px;">
                <div class="cornerBox">查询条件</div>
                <div class="formBox">
                    <div class="searchBox">
                    	<div class="kv">
		                    <label>门诊</label>
		                    <div class="kv-v">
		                        <select id="organization" name="organization"></select>
		                    </div>
		                </div>
                        <div class="kv">
                            <label>复诊时间</label>
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
                            <label>挂号科室</label>
                            <div class="kv-v">
                                <select class="dept" tag="<%=ConstUtil.DEPT_TYPE_1%>" name="regdept" id="regdept" >
							</select>
                            </div>
                        </div>
                        <div class="kv">
                            <label>医生</label>
                            <div class="kv-v">
                            <select id="doctor" name="doctor" tig="" class="searchSelect"  data-live-search="true" title="请选择"></select>
<!--                              <select  name="doctor" id="doctor" > -->
<!-- 	    					  </select> -->
                            </div>
                        </div>
                         <div class="kv">
                            <label style="width:70px;">模糊</label>
                            <div class="kv-v">
                                  <input type="text" name="usercodeorname" id="usercodeorname" placeholder="患者编号/姓名/手机号码"  />
                            </div>
                        </div>
                        <div class="kv">
	                        <div class="kv">
	                            <label>目的分类</label>
	                            <div class="kv-v">
	                                <select class="dict" tig="jzmd" name="reggoalSearch" id="reggoalSearch">
									</select>
	                            </div>
	                            
	                        </div>
	                        <div class="kv">
	                         	<label>提醒就诊</label>
	                            <div class="kv-v">
	                                <select class="patients-source" name="jzmdSearch" id="jzmdSearch">
									</select>
	                            </div>
	                        </div>
                        </div>
                    </div>
                    <div style="border-top:1px solid #ddd;clear:both;padding-top:10px;padding-bottom:5px;padding-left:15px;">
                     	<a id="clean" href="javascript:void(0);"  class="kqdsCommonBtn">清空</a>
                    	<a id="searchHzda" href="javascript:void(0);"  class="kqdsSearchBtn">查询</a>
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
			<%@include file="/inc/rightPartInfo.jsp" %>
		</div>
    </div>
</div>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var listbutton;
var onclickrowOobj = "";
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var pageurl = '<%=contextPath%>/KQDS_JzqkAct/selectJzTx.act';
var nowday;
$(function() {
    initHosSelectList4Front('organization'); // 连锁门诊下拉框	
    //当前日期
    nowday = getNowFormatDate();
    $("#fzsj").val(nowday);
    $("#fzsj2").val(nowday);
    initDictSelectByClass(); // 就诊目的
    var tmpOrganization = $("#organization").val();
    initDeptSelectByTypesAndClass(tmpOrganization);
    $('.searchSelect').selectpicker("refresh");//初始化刷新--2019-10-24 licc    
    $("#organization").change(function() {
        var currSelect = $(this).val();
        initDeptSelectByTypesAndClass(currSelect);
    });
    //监听 onchange事件       根据部门选择人员
//     selectChangeTwo("regdept", "doctor",1);
    selectChangeTwoSearch("regdept", "doctor",1);
    /* 左侧个人中心的按钮样式控制js已经被抽取到rightPartInfo.jsp页面中 */

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
    togglemodel.initial('bctx',pageurl);/*wl 初始化 开关模块 */
    //4、表格初始化
    initTable(1);

    //获取当前页面所有按钮
    getButtonAllCurPage("<%=menuid%>");

    $(window).resize(function() {
        setWidth();
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });
});
$('#reggoalSearch').change(function() {
	getSubDictSelectByParentCodeNoOrg(this.value,'jzmdSearch');
});
$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#reggoalSearch").val("").trigger("change");
    $("#jzmdSearch").val("").trigger("change");
    $("#remark").val("");
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
	 $(".searchSelect li.selected").empty();
	 $('.searchSelect').selectpicker("refresh");//初始化刷新--2019.10.23--licc
});
function initTable() {

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
        onLoadSuccess:function(){
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
            title: '复诊日期',
            field: 'fzdata',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span>'+value+'</span>';
            }
        },
        {
            title: '到诊时间',
            field: 'dzdata',
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
            title: '病人编号',
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
            title: '姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
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
            title: '就诊分类',
            field: 'recesort',
            align: 'center',
            
            formatter: function(value, row, index) {
                return '<span class="source">' + value + '</span>';
            }
        },
        {
            title: '挂号分类',
            field: 'regsort',
            align: 'center',
            
            formatter: function(value, row, index) {
                return '<span class="source">' + value + '</span>';
            }
        },
        {
            title: '就诊医生',
            field: 'doctor',
            align: 'center',
            
            formatter: function(value, row, index) {
                return '<span class="name">' + value + '</span>';
            }
        },
        {
            title: '目的分类',
            field: 'reggoal',
            align: 'center',
            sortable: true,
            formatter: function(value,row, index) {
                return '<span class="source">' + value + '</span>';
            }
        },
        {
            title: '就诊目的',
            field: 'jzmdname',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span style="text-align:left;" class="source">'+ value +'</span>';
            }
        },
        {
            title: '提醒就诊',
            field: 'txjzmdname',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span style="text-align:left;" class="source">'+ value +'</span>';
            }
        },
        {
            title: '咨询',
            field: 'askperson',
            align: 'center',
            
            formatter:function(value){
            	return '<span class="name">'+ value +'</span>';
            }
        },
        {
            title: '挂号时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                html = '<span title="' + value + '">' + value.substring(0) + '</span>';
                return html;
            }
        },
        {
            title: '挂号人员',
            field: 'createuser',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span class="name">'+ value +'</span>';
            }
        },
        {
            title: '备注',
            field: 'remark',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return "<span class='remark' title='" + value + "'>" + value + "</span>";
                } else {
                    return "<span class='remark'></span>";
                }
            }
        }]
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
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        isfz: 1,
        fzsj: $('#fzsj').val(),
        fzsj2: $("#fzsj2").val(),
        organization: $('#organization').val(),
        regdept: $('#regdept').val(),
        doctor: $('#doctor').val(),
        usercodeorname: $('#usercodeorname').val(),
        reggoal: $('#reggoalSearch').val(),
        jzmd: $('#jzmdSearch').val(),
        remark: $('#remark').val(),
    };
    return temp;
}
$('#searchHzda').on('click',
function() {
    var fzsj = $("#fzsj").val();
    var fzsj2 = $("#fzsj2").val();
    var regdept = $("#regdept").val();
    var doctor = $("#doctor").val();
    var usercodeorname = $("#usercodeorname").val();
    var reggoalSearch = $("#reggoalSearch").val();
    var jzmdSearch = $("#jzmdSearch").val();
    var remark = $("#remark").val();

    if (remark == "" && fzsj == "" && fzsj2 == "" && regdept == "" && doctor == "" && usercodeorname == "" && reggoalSearch == "" && jzmdSearch == "") {
        layer.alert('请选择查询条件' );
        return false;
    }
    dept = regdept;

    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
});

/**
     * 查询是否欠费
     * 
     */
function getIsQf(cellId, id) {
    var value = "";
    var urldept = contextPath + "/KQDS_CostOrder_DetailAct/selectNoPage.act?regno=" + id;
    $.axseY(urldept, null,
    function(data) {
        if (data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                var qf = data[i].qfbh;
                if (qf != null && qf != "null" && qf != undefined && qf != "undefined" && qf != "") {
                    value = value + qf;
                }
            }
        }
        if ($("#" + cellId).length <= 0) {
            //dom不存在
            setTimeout(function() {
                if (value) {
                    $("#" + cellId).html("欠费");
                    $("#" + cellId).attr("class", "label label-danger");
                } else {
                    $("#" + cellId).html("未欠费");
                    $("#" + cellId).attr("class", "label label-success");
                }
            },
            500);
        } else {
            if (value) {
                $("#" + cellId).html("欠费");
                $("#" + cellId).attr("class", "label label-danger");
            } else {
                $("#" + cellId).html("未欠费");
                $("#" + cellId).attr("class", "label label-success");
            }
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

// 该方法执行完毕后，再计算高度、宽度，否则计算不准确
function getButtonPower() {
    var menubutton1 = "";
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "bc") {
            menubutton1 += ' <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="save();">保存</a>';
        }
    }
    $("#saveDiv").append(menubutton1);

    //计算主体的宽度
    setWidth();
    setHeight();

}

//计算界面宽高的设置
//setWidth() ,setHeight()函数移动到tableHeaderWidth.js


</script>
</body>
</html>
