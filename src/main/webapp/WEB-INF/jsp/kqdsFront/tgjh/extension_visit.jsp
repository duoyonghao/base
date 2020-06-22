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
<title>客服中心_推广计划</title> <!-- 客服中心  推广计划菜单进入  -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/reception_center.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/layer-v2.4/layer/skin/blueSkin/skin_style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/yuyue/visitReturn.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
	
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
</head>
<style type="text/css">
*{
	box-sizing:border-box;
}
/*工作量表格 ，单独写*/
.kqds_table{
	width:90%;
	align:center;
	margin-left: auto;
	margin-right: auto;
}
	
.kqds_table  td { 
	color: #666;
	padding: 3px 2px 5px 2px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	font-weight: normal;
	line-height: 28px;
}
	
.kqds_table  select { 
	height: 28px;
	width:120px;
	border: solid 1px #e5e5e5;
	border-radius: 3px;
	box-sizing:border-box;
}

input[type=text],.kv .kv-v input[type=text]{box-sizing:border-box;padding:0 10px;width:100px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}
.fixed-table-container{
	border:none;
}
.tableBox{

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
.hfflul>li a{
	color:#333 ;
}
.hfflul>li a.nowchecked{
	color:#00A6C0;
}
</style>
<body>
<div id="container">
    <div class="extraBar">
    	<div class="titleDiv" style="height:50px;margin:0;">
    		<div class="title" style="margin-top:16px;padding-left:15px;">
    			计划列表
    		</div>
    	</div>
       <!--  <div class="extraHd">计划列表</div> -->
        <!-- <div class="extraBd hc-scroll-webkit" style="overflow-y:auto;"> -->
        <div class="extraBd" style="overflow-y:auto;">
            <!-- <label><em>*</em>注：分类列表双击查看</label> -->
            <ul class="hfflul" name="hfflul" style="">
            </ul>
        </div>
    </div>
    <div class="main">
        <div class="listWrap">
            <div class="listHd">
           	  <span class="title">推广计划</span>
          	</div>
            <div class="listBd">
                <div class="tableBox">
                    <table id="listTable" class="table-striped table-condensed table-bordered" data-height="450"></table>
                </div>
            </div>
        </div>
        <!--查询条件-->
        <div class="searchModule">
	        <input type="hidden" id="rightselecttype" value="">
	    	<header>
	    		<span>查询条件</span>
	    		<i>共有 记录<span id="total">0</span> 条</i>
	    		<i>已回访 <span id="yihuifang">0</span> 条</i>
	    		<i>未回访 <span id="whuifang">0</span> 条</i>
	    		<i>最新完成率  <span id="xwcl">0%</span></i>
	    		<i>截止期末完成率  <span id="qmwcl">0%</span></i>
	    	</header>
	    	<section>
	    		<ul class="conditions">
	    			<li>
	    				<span>所属门诊</span>
	    				<select id="organization" name="organization"></select>
	    			</li>
	    			<li class="unitsDate">
	    				<span>完成时间</span>
	    				<input type="text" placeholder="开始日期" id="vtime1" readonly>
	    			</li>
	    			<li class="unitsDate">
	    				<span>到</span>
	    				<input type="text"  placeholder="结束日期" id="vtime2" readonly>
	    			</li>
	    			<li>
	    				<span>计划完成状态</span>
	    				<select name="planstatus" id="planstatus">
	                       	<option value="">请选择</option>
	                       	<option value="0">未完成</option>
	                       	<option value="1">已完成</option>
						 </select>
	    			</li>
	    			<li>
	    				<span>计划名称</span>
	    				<input type="text" name="extension" id="extension" placeholder="请输入计划名称"/>
	    			</li>
	    		</ul>
	    	</section>
	    	<div class="btnBar" style="text-align:left;">
	           <a href="javascript:void(0);" class="kqdsCommonBtn" id="finishtg" onclick="finishtg()">执行</a>
	           <a href="javascript:void(0);" class="kqdsCommonBtn" id="editTgjh" onclick="editTgjh()">编辑</a>
	           <a href="javascript:void(0);" class="kqdsCommonBtn" id="delete" onclick="deleteSingle()">删除</a>
	           <a href="javascript:void(0);" class="kqdsCommonBtn" id="detail" onclick="detail()">详情</a>
	           <a href="javascript:void(0);" class="kqdsCommonBtn" id="clear">清空</a>
	           <a href="javascript:void(0);" class="kqdsSearchBtn" id="query" onclick="querytable()">查询</a>
	        </div>
   		</div>
        <!-- <div class="searchWrap">
       		<input type="hidden" id="rightselecttype" value="">
            <div class="cornerBox">查询条件</div>
            <span class="text">
            	共有记录<i class="total" id="total"></i> 条&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            	已回访<i class="yihuifang" id="yihuifang"></i> 条&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            	未回访<i class="whuifang" id="whuifang"></i> 条&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            	最新完成率<i class="xwcl" id="xwcl"></i> %&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            	截止期末完成率<i class="qmwcl" id="qmwcl"></i> %&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </span>
            <div class="formBox">
            	<table class="kqds_table">
			    		<tr>
			    			<td style="text-align:right;">所属门诊：</td>
			    			<td style="text-align:left;">
			    				<select id="organization" name="organization"></select>
			    			</td>
			    			<td style="text-align:right;">完成时间：</td>
			    			<td style="text-align:left;"> 
		    					<span class="unitsDate">
		    						<input type="text" placeholder="开始日期" id="vtime1" readonly>
		                        </span>
			                </td>
			    			
			    			<td style="text-align:right;">到：</td>
			    			<td style="text-align:left;">
								<span class="unitsDate">
		                             <input type="text"  placeholder="结束日期" id="vtime2" readonly>
		                        </span>
							</td>
			    			<td style="text-align:right;">计划完成状态：</td>
			    			<td style="text-align:left;">
		    					 <select name="planstatus" id="planstatus">
		                        	<option value="">请选择</option>
		                        	<option value="0">未完成</option>
		                        	<option value="1">已完成</option>
								 </select>
			    			</td>
			    			<td style="text-align:right;">计划名称：</td>
			    			<td style="text-align:left;">
		    					    <input type="text" name="extension" id="extension" placeholder="请输入计划名称" style="width:60%"/><span style="color:red;padding-left:2px;">(*可模糊查询)</span>
			    			</td>
			    		</tr>
			    </table>
			     <div class="btnBar" id="btnBarDiv">
	            	<a href="javascript:void(0);" class="kqdsCommonBtn" id="finishtg" onclick="finishtg()">执行</a>
	            	<a href="javascript:void(0);" class="kqdsCommonBtn" id="editTgjh" onclick="editTgjh()">编辑</a>
	            	<a href="javascript:void(0);" class="kqdsCommonBtn" id="delete" onclick="deleteSingle()">删除</a>
	            	<a href="javascript:void(0);" class="kqdsCommonBtn" id="detail" onclick="detail()">详情</a>
	            	<a href="javascript:void(0);" class="kqdsCommonBtn" id="clear">清空</a>
	                <a href="javascript:void(0);" class="kqdsSearchBtn" id="query" onclick="querytable()">查询</a>
	            </div>
            </div> -->
        </div>
    </div>
</div>
</body>

<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var $table = $('#listTable');
var pageurl = '<%=contextPath%>/KQDS_ExtensionAct/selectList.act';
var querycounturl = '<%=contextPath%>/KQDS_ExtensionAct/getCountByQuery.act';
//初始化表头，返回空的数据
var nullUrl = '<%=contextPath%>/UtilAct/intTableHeader.act';
var pid = "<%=person.getSeqId()%>";
var nowday = null;
var onclickrowOobj = "";
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var menuid = "<%=menuid%>";
$(function() {
	initHosSelectListNoCheck('organization');// 连锁门诊下拉框
	//获取当前页面所有按钮
	getButtonAllCurPage("<%=menuid%>");
    nowday = getNowFormatDate(); //当前日期
    $("#vtime1").val(nowday);
    // $("#vtime2").val(nowday);
    //默认加载未完成的计划
    $('#planstatus').val("0");

    //加载右侧回访分类
    inithffl();

    //查询各类条数
    settypeNum();

    //加载表格
    initTable(nullUrl);

    //清空
    $('#clear').on('click',
    function() {
        $("#vtime1").val("");
        $("#vtime2").val("");
        $("#extension").val("");
        $("#status").val("");
    });

    //时间选择
    $(".unitsDate input").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "top-right"
    });

    //计算主体的宽度
    setHeight();
    $(window).resize(function() {
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });
});
//导出
function exportTable() {
    var fieldArr = [];
    var fieldnameArr = [];
    $('#listTable thead tr th').each(function() {
        var field = $(this).attr("data-field");
        if (field != "") {
            fieldArr.push(field); //获取字段
            fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
        }
    });
    var params = queryParams();
    params.tuiguang = $("#rightselecttype").val();
    var param = JsontoUrldata(params);
    location.href = pageurl + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
}

function settypeNum() {
    $.axse(querycounturl, queryParams(),
    function(data) {
        if (data.retState == "0") {
            //总条数
            $("#total").html(data.total);
            $("#yihuifang").html(data.yihuifang);
            $("#whuifang").html(data.nohuifang);
            $("#xwcl").html(data.wcl);
            $("#qmwcl").html(data.qmwcl);
            if (data.flcounts != null || data.flcounts.length > 0) {
                // $("ul[name=hfflul] li").each(function() {
                //    var thisli = $(this).html();
                    for (var i = 0; i < data.flcounts.length; i++) {
                        var thisstr1 = data.flcounts[i].jhname;
                        var jhseqId =  data.flcounts[i].jhseqId;
                        // var s = thisli.split('<')[0].trim();
                        // if (s.indexOf("..") != -1) {
                            // if (thisstr1.indexOf(s.substring(0, s.length - 2)) != -1) {
                                $("#KQDS" + jhseqId).html(""); // 这个ID和 下面方法中的ID保持一致
                                if (getStrLen(thisstr1) > 16) {
                                    $("#KQDS" + jhseqId).html("<a href='#' onclick='aquery(this)' title='" + thisstr1 + "'>" + thisstr1.substring(0, 12) + ".. " + data.flcounts[i].count + " 人</a>");
                                } else {
                                    $("#KQDS" + jhseqId).html("<a href='#' onclick='aquery(this)' title='" + thisstr1 + "'>" + thisstr1 + " " + data.flcounts[i].count + " 人</a>");
                                }
                            // }
                        // } else {
                            // if (s == thisstr1) {
                            //     $(this).html("");
                            //     $(this).html(" <a href='#' style='color:blue;' onclick='aquery(this)' title='" + thisstr1 + "'>" + thisstr1 + " " + data.flcounts[i].count + " 人</a>");
                            // }
                        // }
                    }
                // });
            }
        }
    },
    function() {});
}
function queryParams() {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	organization: $('#organization').val(),
        vtime1: $('#vtime1').val(),
        vtime2: $('#vtime2').val(),
        extension: $('#extension').val(),
        planstatus: $('#planstatus').val()
    };
    return temp;
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
        };
    }
    return value;
}
//获取选中行的usercode
function getIdSelections() {
    return $.map($table.bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
}

//点击查询
function querytable() {
    onclickrowOobj = "";
    var vtime1 = $("#vtime1").val();
    var vtime2 = $("#vtime2").val();
    var extension = $("#extension").val();
    var organization = $("#organization").val();
    if (vtime1 == "" && vtime2 == "" && extension == "" && organization == "") {
        layer.alert('请选择查询条件' );
        return false;
    }
    if (vtime1 != "" && vtime2 != "") {
        if (checkdate(vtime1, vtime2) == 0) { // 0 表示 starttime 大于 endtime
            alert("结束日期不能小于开始日期");
            return false;
        }
    }
    //清空表格内容
    $("#listTable tbody").html("");
    //初始化右侧推广计划分类条数
    inithffl();
    //查询各类条数
    settypeNum();
}
function refresh() {
    querytable();
    var params = queryParams();
    params.tuiguang = $("#rightselecttype").val();
    $table.bootstrapTable('refresh', {
        'url': pageurl,
        query: params
    });
}
//点击右侧回访分类对应条数 查询数据
function aquery(obj) {
	//被点击时获得样式
	$(".nowchecked").removeClass("nowchecked");//清除橘色字体颜色
	$(obj).addClass("nowchecked");//被点击的文本 添加橘色样式
	
    onclickrowOobj = "";
    var tuiguang = $(obj).parent().attr("value");
    $("#rightselecttype").val(tuiguang);
    var params = queryParams();
    params.tuiguang = tuiguang;
    $.axse(querycounturl, params,
    function(data) {
        if (data.retState == "0") {
            //总条数
            $("#total").html(data.total);
            $("#yihuifang").html(data.yihuifang);
            $("#whuifang").html(data.nohuifang);
            $("#xwcl").html(data.wcl);
            $("#qmwcl").html(data.qmwcl);
        }
    },
    function() {});
    //加载表格
    $table.bootstrapTable('refresh', {
        'url': pageurl,
        query: params
    });
}

//查询推广计划
function inithffl() {
    $('ul[name=hfflul] li').remove();
    var url = '<%=contextPath%>/KQDS_ExtensionAct/getExtensoionTypeList.act';
    $.axse(url, queryParams(),
    function(data) {
        var list = data.data;
        if (list != null) {
            if (list.length > 0) {
                var ulstr = "";
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    var optionStrseqId = optionStr.seqId;
                    var optionStr1 = optionStr.jhname;
                    if (getStrLen(optionStr1) > 16) {
                        ulstr += '<li id="KQDS' + optionStrseqId + '" value="' + optionStrseqId + '" title="' + optionStrseqId + '">' + optionStr1.substring(0, 14) + '..</li>';
                    } else {
                        ulstr += '<li id="KQDS' + optionStrseqId + '" value="' + optionStrseqId + '">' + optionStr1 + '</li>';
                    }
                }
                $("ul[name=hfflul]").html(ulstr);
            }
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
}

//加载表格
function initTable(requrl) {
    $table.bootstrapTable({
        url: requrl,
        dataType: "json",
        queryParams: queryParams,
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	/*表格载入时，设置表头的宽度 */
        	setTableHeaderWidth(".tableBox");
        },
        onDblClickCell: function(field, value, row, td) {
            //双击事件 调用提交回访结果方法 
            goToUserCenterPage(row.usercode);
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
            title: '所属门诊',
            field: 'organizationname',
            align: 'center',
            
            formatter: function(value, row, index) {
                return '<span title="' + value + '">' + value + '</span>';
            }
        },{
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            
            formatter: function(value, row, index) {
                return '<span title="' + value + '">' + value + '</span>';
            }
        },{
            title: '推广计划创建人',
            field: 'typecreateuser',
            align: 'center',
           	visible: false
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
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '手机号码',
            field: 'phonenumber1',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (canlookphone == 0) {
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '会员',
            field: 'memberno',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == '是') {
                    return "<span style='color:green;'>是</span>";
                } else {
                    return "<span style='color:red;'>否</span>";
                }
            }
        },
        {
            title: '回访人',
            field: 'visitor',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '计划截止时间',
            field: 'visittime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '备注',
            field: 'extenremark',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span title="' + value + '" style="width:100px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;display:block;cursor:pointer;">' + value + '</span>';
            }
        },
        {
            title: '完成状态',
            field: 'status',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                var htmlstr = "";
                if (row.status == "未完成") {
                    htmlstr = "<span class='label label-danger'>未完成</span>";
                } else {
                    htmlstr = "<span class='label label-success'>已完成</span>";
                }
                return htmlstr;
            }
        },
        {
            title: '完成时间',
            field: 'finishtime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '内容详情',
            field: 'hfresult',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "" || value == null) {
                    return "";
                }
                return '<span class="remark" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '患者备注',
            field: 'remark',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="remark" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '计划名称',
            field: 'jhname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="remark" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '创建人',
            field: 'createuser',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span title="' + value + '">' + value + '</span>';
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $table.find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $table.bootstrapTable('getData')[index];
    });
}
function getButtonPower(){
	var menubutton1 = "",menubutton2 = "";
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "tgjh_scbb") {
            menubutton1 += ' <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="exportTable()">生成报表</a>';
        }else if(listbutton[i].qxName == "tgjh_mzyy"){
        	menubutton2 += '  <a href="javascript:void(0);" class="kqdsCommonBtn" id="mzyy" onclick="mzyy()">门诊预约</a> ';
        }else if(listbutton[i].qxName == "tgjh_wdyy"){
        	menubutton2 += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="wdyy" onclick="wdyy()">网电预约</a> ';
        }
        
    }
    $(".btnBar").append(menubutton2);
    $(".btnBar").append(menubutton1);
}
//门诊预约
function mzyy() {
    if (onclickrowOobj == "" || onclickrowOobj == null) {
        layer.alert('请选择一个计划' );
        return false;
    } else {
        layer.open({
            type: 2,
            title: '门诊预约',
            shadeClose: true,
            shade: 0.6,
            area: ['90%', '90%'],
            content: '<%=contextPath%>/KQDS_Net_OrderAct/toYyzx2.act?usercode=' + onclickrowOobj.usercode + '&username=' + onclickrowOobj.username
        });
    }
}
//网电预约
function wdyy() {
    if (onclickrowOobj == "" || onclickrowOobj == null) {
        layer.alert('请选择一个计划' );
        return false;
    } else {
        layer.open({
            type: 2,
            title: '网电预约',
            shadeClose: true,
            shade: 0.6,
            area: ['550px', '520px'],
            content: '<%=contextPath%>/KQDS_Net_OrderAct/toNetorderInsert.act?usercode=' + onclickrowOobj.usercode
        });
    }
}
//详情
function detail() {
    if (onclickrowOobj == "" || onclickrowOobj == null) {
        layer.alert('请选择一个计划' );
        return false;
    } else {
        layer.open({
            type: 2,
            title: '计划详情',
            shadeClose: true,
            shade: 0.6,
            area: ['550px', '520px'],
            content: '<%=contextPath%>/KQDS_ExtensionAct/toExtensionDetail.act?seqId=' + onclickrowOobj.seqId
        });
    }
}
//完成推广
function finishtg() {
    if (onclickrowOobj == "" || onclickrowOobj == null) {
        layer.alert('请选择一个计划' );
        return false;
    } else if (onclickrowOobj.status == "已完成") {
        layer.alert('您选中的计划已完成' );
        return false;
    } else if (onclickrowOobj.visitorid != pid) {
        layer.alert('请选择自己的计划任务' );
        return false;
    } else {
        layer.open({
            type: 2,
            title: '执行计划',
            shadeClose: true,
            shade: 0.6,
            area: ['40%', '35%'],
            content: '<%=contextPath%>/KQDS_ExtensionAct/toExtensionFinish.act' 
        });
    }
}

//编辑推广
function editTgjh() {
    if (onclickrowOobj == "" || onclickrowOobj == null) {
        layer.alert('请选择一个计划' );
        return false;
    }
    
    if (onclickrowOobj.status == "已完成") {
        layer.alert('您选中的计划已完成' );
        return false;
    } 
    
    if (onclickrowOobj.typecreateuser != pid) {
        layer.alert('只有计划创建人可以执行编辑操作！' );
        return false;
    } 
    
    layer.open({
        type: 2,
        title: '编辑计划',
        shadeClose: true,
        shade: 0.6,
        area: ['550px', '560px'],
        content: '<%=contextPath%>/KQDS_ExtensionAct/toExtensionEdit.act?seqId=' + onclickrowOobj.seqId
    });
}

// 删除推广计划
function deleteSingle() {
    if (onclickrowOobj == "" || onclickrowOobj == null) {
        layer.alert('请选择一个计划' );
        return false;
    } 
    if (onclickrowOobj.status == "已完成") {
        layer.alert('您选中的计划已完成' );
        return false;
    } 
    
    if (onclickrowOobj.typecreateuser != pid) {
        layer.alert('只有计划创建人可以执行删除操作！' );
        return false;
    } 
    
  	//询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/KQDS_ExtensionAct/deleteObj.act?seqId=' + onclickrowOobj.seqId;
        msg = layer.msg('加载中', {
            icon: 16
        });
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
            	var msg = '删除成功';
            	if(data.retMsrg){
            		msg = data.retMsrg;
            	}
                layer.alert(msg, {
                      
                });
                refresh();
            }else{
            	layer.alert('删除失败：' + data.retMsrg  );
            }
        },
        function() {
            layer.alert('删除失败！'  );
        });
    });
    
    
   
}

//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height();
    var serachH = $('.searchModule').outerHeight();
    $('.extraBar .extraBd').height(baseHeight - $(".titleDiv").outerHeight()-35);
    $('#listTable').bootstrapTable('resetView', {
        height: baseHeight - serachH - 72
    });
}

//计算字符串长度
function getStrLen(str) {
    if (str == null) return 0;
    if (typeof str != "string") {
        str += "";
    }
    return str.replace(/[^\x00-\xff]/g, "01").length;
}
</script>

</html>