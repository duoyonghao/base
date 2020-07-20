<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
		String contextPath = request.getContextPath();

		// 客服中心—客户管理页面右侧的网电信息Url和其他页面不一样，
		// 一个是kqdsFront/jdzx/dialogFrame/netorder.jsp
		// 一个是kqdsFront/jdzx/frame/kfzx/netorder_insertOrUpdate.jsp
		// 为了能统一包含一个公用JSP文件，所以在这里设置个标识，供rightPartInfo.jsp界面判断
		//staticIsUserMgrPage: value为1 网电预约  2 报备预约
		request.setAttribute("staticIsUserMgrPage", "1");
		YZPerson person = SessionUtil.getLoginPerson(request);
		if (contextPath.equals("")) {
			contextPath = "/kqds";
		}
		//获取从左侧菜单点击带过来的菜单id
		String menuid = request.getParameter("menuId");
		String isyx = request.getParameter("isyx");//营销中心进入
		if (isyx == null) {
			isyx = "0";
		}
	%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>客服中心—精确查询</title>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/reception_center.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/yiliao/bingli_search.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
</head>
<style type="text/css">

.kqds_table{
	width:auto;
	text-align:center;
	margin-left:20px;
	float:left;
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
	width:100px;
	border: solid 1px #e5e5e5;
	border-radius: 3px;
}

input[type=text],.kv .kv-v input[type=text]{padding:0 10px;width:120px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}
.searchWrap .btnBar > .aBtn {
    width: 90px;
}
a:hover{
	text-decoration:none;
}
.btnGroup{
    float: left;
    margin: 2px 0 0 30px;
}
</style>
<body>
<div id="container">
    <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
                <div class="columnHd">
				    <span class="title">精确查询</span>
				</div>
                <div class="columnBd">
                    <div class="tableBox">
                        <table id="table" class="table-striped table-condensed table-bordered" data-height="430"></table>
                    </div>
                </div>
            </div>
            <div class="searchWrap">
                <!-- <div class="cornerBox">查询条件</div> -->
                <div class="searchToggleBtnGroup">
                	<span class="ptcx checked">
                		通用查询
                	</span>
                	<!-- <span class="gjcx">
                		高级查询
                	</span> -->
                </div>
                <div class="formBox">
                    <table class="kqds_table">
			    		<tr>
			    			<td style="padding-left:10px;">姓名：</td>
			    			<td style="text-align:left;">
			    				<input type="text" id="queryInputName"  placeholder="姓名"/> 
			    			</td>
			    			<td style="padding-left:20px;">手机号：</td>
			    			<td style="text-align:left;">
			    				<input type="text" id="queryInput"  placeholder="手机号"/> 
			    			</td>
			    			<!-- <td style="width:160px;">
			    				
			    			</td> -->
			    		</tr>
			    	</table>
			    	<div class="btnGroup">
			    		<a href="javascript:void(0);" class="kqdsCommonBtn" id="clear">清空</a>
                		<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="querySC();" id="query">查询</a>
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
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var nullUrl = '<%=contextPath%>/UtilAct/intTableHeader.act';
var pageurl = '<%=contextPath%>/KQDS_UserDocumentAct/jingQueChaXun4Net.act';
var listbutton;
var onclickrowOobj = "";
var nowday;
var selectedrows = "";
var personrole = "";
var personroleother= "'<%=person.getUserPrivOther()%>";
var $table = $("#table");
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var canKd = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag2_canKd, request)%>';
var isyx = "<%=isyx%>";
var loc = new Location();
$(function() {
	togglemodel.initial('jqcx',pageurl);/*wl 初始化 开关模块 */
    //3、加载表格
    initTable(nullUrl);

    //清空
    $('#clear').on('click',
    function() {
        $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    });

    //获取当前页面所有按钮##现在没用到此方法 ## yangsen 17-5-2
    getButtonAllCurPage("<%=menuid%>");
    
    //计算主体的宽度
    setWidth();
    setHeight();
    $(window).resize(function() {
        setWidth();
        setHeight();
    });
});

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        queryinput: $('#queryInput').val(),
        queryInputName: $('#queryInputName').val()
    };
    return temp;
}
//点击查询
function querySC() {
	var tmp = $('#queryInput').val();
	var queryInputName =  $('#queryInputName').val();
	if(tmp =="" && queryInputName ==""){
		layer.alert('请输入精确查询条件', {
	          
	    });
		return false;
	}
	if(tmp != "" && tmp.length < 8){
		layer.alert('请输入精确查询条件，手机号码长度不能小于8', {
	          
	    });
		return false;
	}
	if(queryInputName != "" && queryInputName.length < 2){
		layer.alert('请输入精确查询条件，姓名长度不能小于2', {
	          
	    });
		return false;
	}
	//查询中，禁止查询按钮点击 lutian
	$("#query").attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none");
	$("#query").text("查询中");
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
//加载表格
//网电预约
function initTable(reqUrl) {

    $('#table').bootstrapTable({
        url: reqUrl,
        dataType: "json",
        queryParams: queryParams,
        cache: false,
        striped: true,
        clickToSelect: false,
        singleSelect: false,
        onLoadSuccess: function(data) { //加载成功时执行
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer").css("pointer-events","auto");
				$("#query").text("查询");
			}
			setWidth();
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
            title: '建档门诊',
            field: 'organizationname',
            align: 'center',
            visible: true,
            switchable: false,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
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
                    return '<span class="label label-success">已上门</span>';
                } else {
                    return '<span class="label label-danger">未上门</span>';
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
                    return '<span class="label label-success">已上门</span>';
                } else {
                    return '<span class="label label-danger">未上门</span>';
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
                    return '<span class="label label-success">已成交</span>';
                } else {
                    return '<span class="label label-danger">未成交</span>';
                }
            }
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '">' + value + '</span>';
                }
            }
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                }
            }
        },
        {
            title: '手机号2',
            field: 'phonenumber2',
            align: 'center',
            
            visible: false, // 不展示
            switchable: false
        },
        {
            title: '手机号',
            field: 'phonenumber1',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (canlookphone == 0) {
                    if (row.phonenumber1 != "" && row.phonenumber1 != null) {
                        return '<span title="' + row.phonenumber1 + '">' + row.phonenumber1 + '</span>';
                    } else if (row.phonenumber2 != "" && row.phonenumber2 != null && (row.phonenumber1 == "" || row.phonenumber1 == null)) {
                        return '<span title="' + row.phonenumber2 + '">' + row.phonenumber2 + '</span>';
                    } else {
                        return '';
                    }
                } else {
                    return '';
                }
            }
        },
        {
            title: '建档人',
            field: 'createuser',
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
            title: '建档时间',
            field: 'createtime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span>' + value + '</span>';
                } else {
                    return "";
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
                    return '<span>' + value + '</span>';
                } else {
                    return "";
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
                    return '<span>' + value + '</span>';
                }
            }
        },
        {
            title: '患者来源',
            field: 'devchannel',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '来源子分类',
            field: 'nexttype',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '省',
            field: 'provincename',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span class="name">'+value+'</span>';
            }
        },
        {
            title: '市',
            field: 'cityname',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span class="name">'+value+'</span>';
            }
        },
        {
            title: '区',
            field: 'townname',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span class="name">'+value+'</span>';
            }
        },
        {
            title: '年龄',
            field: 'age',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "0") {
                    return '';
                } else {
                    return '<span>'+value+'</span>';
                }
            }
        },
        {
            title: '星级',
            field: 'important',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '患者分类',
            field: 'usersort',
            align: 'center',
            sortable: true
        },
        {
            title: '商务通身份证',
            field: 'xueli',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return "";
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
                    return "";
                }
            }
        },
        {
            title: '受理类型',
            field: 'accepttype',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return "";
                }
            }
        },
        {
            title: '受理工具',
            field: 'accepttool',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return "";
                }
            }
        },
        {
            title: '患者备注',
            field: 'remark',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="remark">' + value + '</span>';
                }
            }
        },
        {
            title: '关联人',
            field: 'glr',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return "";
                }
            }
        },
        {
            title: '修改原因',
            field: 'glrremark',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="remark">' + value + '</span>';
                }
            }
        }]
    }).on('click-row.bs.table',function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        $('#tabIframe').attr('src', $('#tabIframe').attr('src')); //个人中心
    });
}

//计算界面宽高的设置
//setWidth() ,setHeight()函数移动到tableHeaderWidth.js

</script>
</html>