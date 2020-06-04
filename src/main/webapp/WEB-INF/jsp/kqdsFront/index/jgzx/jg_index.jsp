<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);
	// sno 为加工单的主键 
	String sno = request.getParameter("sno");
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
	//从哪里点击过来的
	String type = request.getParameter("type");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>加工中心_加工单管理</title>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
</head>
<style type="text/css">
/*工作量表格 ，单独写*/
#gongzuol{border:solid 1px #c0c0c0;background: #fff;}
#gongzuol{margin-bottom: 15px;}
#gongzuol .columnHd{padding:0 15px;border-bottom:solid 1px #0e7ec6;font-size:16px;color: #373737;font-family: "Microsoft YaHei";line-height:36px;}
 .buttonBar .aBtn_big {  /* 按钮为四个字的用这个样式 */
	display: inline-block;
	padding: 0 15px;
	height: 28px;
	border: solid 1px #0e7cc9;
	color: #0e7cc9;
	border-radius: 15px;
	line-height: 28px;
	width: 88px;
	text-align: center;
}

.buttonBar  .aBtn_big:hover {
	color: #fff;
	background: #0e7cc9;
}
.kqds_table{
	width:90%;
	align:center;
	/* margin-left: auto; */
	margin-right: auto;
}
	
.kqds_table  td { 
	color: #666;
	padding: 3px 5px 5px 5px;
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
}
input[type=text],.kv .kv-v input[type=text]{padding:0 10px;width:120px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}


.searchWrap .btnBar{		/*按钮组右浮动  */
	position:absolute;
	right:10px;
	bottom:10px;
	width:auto;
}
.searchWrap .btnBar .aBtn{
	float:left;
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
.buttonBar{
	padding:15px 0 15px 10px;
	margin:0;
}

.tableBox{
	border:none;
}
/* .fixed-table-container{
	border-left: 1px solid #ddd;
	border-right: 1px solid #ddd;
	border-bottom:1px solid #ddd;
	border-radius: 6px;
	overflow: hidden;
} */
.listWrap .listBd{
	border:1px solid #ddd;
	border-radius: 6px;
}
.searchModule>section>ul.conditions>li>span{
	width:70px;
	text-align:right;
}
.searchModule>section>ul.conditions>li{
	padding: 3px 0;
}
.bootstrap-table .fixed-table-header table th{
	border:none;
}
/* .table>thead>tr>th:nth-child(4)>.fht-cell{
	border:1px solid pink;
} */
/* .machineSelect{
	border: 1px solid #ddd;
    font-size: 13px;
    height: 22px;
    line-height: 22px;
    border-radius: 5px;
    width: 135px;
    padding-left: 5px;
} */
</style>
<body>
	<div id="container">
		<div class="main" style="margin-left:20px;">
			<div class="listWrap">
				<div class="listHd">
					<span class="title">加工单列表</span>
				</div>
				<div class="listBd">
					<div class="tableBox">
						<table id="table" class="table-striped table-condensed table-bordered"></table>
					</div>
					<div id="tableBarDiv" class="buttonBar">
					</div>
				</div>
			</div>
			<!--查询条件-->
			<div class="searchModule" style="margin-top:15px;">
		        
		    	<header>
		    		<span>查询条件</span>
		    		
		    	</header>
		    	<section>
		    		<ul class="conditions">
		    			<li class="unitsDate">
		    				<span>创建时间</span>
		    				<input type="text" placeholder="开始日期" id="createtime1" class="datetime"/>
		    			
		    				<span>到</span>
		    				<input type="text" placeholder="结束日期" id="createtime2" class="datetime"/>
		    			</li>
		    			<li class="unitsDate">
		    				<span>发件时间</span>
		    				<input type="text" placeholder="开始日期" id="fjtime1" class="datetime"/>
		    			
		    				<span>到</span>
		    				<input type="text" placeholder="结束日期" id="fjtime2" class="datetime"/>
		    			</li>
		    			<li class="unitsDate">
		    				<span>回件时间</span>
		    				<input type="text" placeholder="开始日期" id="hjtime1" class="datetime"/>
		    			
		    				<span>到</span>
		    				<input type="text" placeholder="结束日期" id="hjtime2" class="datetime"/>
		    			</li>
		    			<li class="unitsDate">
		    				<span>戴走时间</span>
		    				<input type="text" placeholder="开始日期" id="dztime1" class="datetime"/>
		    			
		    				<span>到</span>
		    				<input type="text" placeholder="结束日期" id="dztime2" class="datetime"/>
		    			</li>
		    			
		    			<li class="unitsDate">
		    				<span>返工时间</span>
		    				<input type="text" placeholder="开始日期" id="fgtime1" class="datetime"/>
		    			
		    				<span>到</span>
		    				<input type="text" placeholder="结束日期" id="fgtime2" class="datetime"/>
		    			</li>
		    			
		    			
		    			<li>
		    				<span>状态</span>
		    				<select name="status" id="status">
								<option value="">请选择</option>
								<option value="0">未发件</option>
								<option value="1">已发件</option>
								<option value="2">回件</option>
								<option value="3">戴走</option>
								<option value="4">返工</option>
								<option value="5">作废</option>
							</select>
		    			</li>
		    			
		    			<li>
		    				<span>加工厂</span>
		    				<select name="unit" id="unit" ></select>
		    			</li>
		    			<li>
		    				<span>模糊查询</span>
		    				<input type="text" id="queryInput" class="searchInput" style="font-size:12px;" placeholder="请输入单号/患者姓名、编号、手机/加工单位" >
		    			</li>
		    		</ul>
		    	</section>
		    	<div class="btnBar" style="text-align:left;">
			        <a href="javascript:void(0);" class="kqdsCommonBtn" id="dayin">打印</a> 
					<a href="javascript:void(0);" class="kqdsCommonBtn" id="clear">清空</a> 
					<a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
		        </div>
	   		</div>
		</div>
	</div>
	
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/jgzx/jg/jg.js"></script>
</body>
<script type="text/javascript">
var type = '<%=type%>';
var sno = '<%=sno%>';
var contextPath = '<%=contextPath%>';
var perseqId = "<%=person.getSeqId()%>";
var personrole = "<%=person.getUserPriv()%>";
var personroleother = "<%=person.getUserPrivOther()%>";
var canHj = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag3_canHj, request)%>';
var $table = $('#table');
var pageurl = '<%=contextPath%>/KQDS_MACHININGAct/getProcessingRecords.act';
var onclickrow = ""; //选中行对象
var onclickrowOobj = ""; //选中行对象 lutian
var nowday = "";
var num = 0; //页面加载使用 第一次加载查询条件为当天或者未发件的加工单
var menuid = "<%=menuid%>";
$(function() {

    //获取当前页面所有按钮
    if (type != "pushlist") {
        //因为从消息提醒列表链接过来时 没有menuid 取按钮权限会报错
        getButtonAllCurPage(menuid);
    }
    //加工厂
    initFactorySelect('unit','','<%=ChainUtil.getCurrentOrganization(request)%>');
    var pageurlsno = pageurl;
    if (sno != "" && sno != null && sno != "undefined" && sno != "null") {
        pageurlsno = pageurlsno + "?sno=" + sno;
        //加载表格
        inittable(pageurlsno);
        //隐藏查询条件
        $('.searchWrap').hide();
        $("#tableBarDiv").hide();
    } else {
        inittable(pageurlsno + '?num=' + num); //加载表格
        num++;
    }
    //查询条件 创建时间
    $(".datetime").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "top-right"
    });
    
    $(window).resize(function() {
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });
    
});

	//加载加工单位
	<%-- function initMachineUnit(){
		var url = '<%=contextPath%>/KQDS_OutProcessing_UnitBackAct/selectPage.act';
		$.ajax({   
	        type:"GET",   
	        url:url, //从服务器拿东西，地址自己写  
	        dataType:"json",  
	        async:false,
	        //下面这个函数是在ajax请求成功后才执行的，其中的参数data，就是
	        success:function(data){
	        	var processUnitHtml="";
	        	if(data){
	        		var machineData = data.rows;
	        		for (var i = 0; i < machineData.length; i++) {
						processUnitHtml += "<option value="+machineData[i].seqId+">"+machineData[i].name+"</option>";
					}
	        		$(".machineSelect").append(processUnitHtml);
	        	}
	        } 
	    });
	} --%>

//查询
$('#query').on('click',
function() {
    var queryInput = $("#queryInput").val();
    var status = $("#status").val(); //状态
    var fjtime1 = $("#fjtime1").val();
    var fjtime2 = $("#fjtime2").val();
    var hjtime1 = $("#hjtime1").val();
    var hjtime2 = $("#hjtime2").val();
    var dztime1 = $("#dztime1").val();
    var dztime2 = $("#dztime2").val();
    var fgtime1 = $("#fgtime1").val();
    var fgtime2 = $("#fgtime2").val();
    var createtime1 = $('#createtime1').val();
    var createtime2 = $('#createtime2').val();
    var unit = $('#unit').val();
    if (queryInput == "" && status == "" && fjtime1 == "" && fjtime2 == "" && hjtime1 == "" && hjtime2 == "" && dztime1 == "" && dztime2 == "" && fgtime1 == "" && fgtime2 == "" 
    	&& createtime1 == "" && createtime2 == ""  && unit == "") {
        layer.alert('请选择查询条件' );
        return false;
    }
    //加载表格
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
});
//清空
$('#clear').on('click',
function() {
    $(".conditions :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});
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
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        status: $("#status").val(),
        fjtime1: $('#fjtime1').val(),
        fjtime2: $('#fjtime2').val(),
        hjtime1: $('#hjtime1').val(),
        hjtime2: $('#hjtime2').val(),
        dztime1: $('#dztime1').val(),
        dztime2: $('#dztime2').val(),
        fgtime1: $('#fgtime1').val(),
        fgtime2: $('#fgtime2').val(),
        createtime1: $('#createtime1').val(),
        createtime2: $('#createtime2').val(),
        unit: $('#unit').val(),
        queryInput: $('#queryInput').val()
    };
    return temp;
}
function inittable(pageurl) {
	
    //加载表格
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        singleSelect: false,
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	//console.log(JSON.stringify(data)+"----------data");
            var tableList = $('#table').bootstrapTable('getData');
            $("#total").html(tableList.length);
          	//计算主体的宽度
            setHeight();
            /*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
            
            //initMachineUnit(); //加载加工单位下拉框
            //下拉框赋值
            /* if(data.length>0){
            	$(".table").find("tbody").find("tr").each(function(i,obj){
	            	$(this).find(".machineSelect").val(data[i].processunit);
	            }); 
            }  */
             
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
        /* {title: '门诊',field: 'organization',align: 'center',valign: 'middle',sortable: true,
					      formatter:function(value,row,index){
					    	  var pjid="table"+index+"organization";
					    	   getValueOrg(pjid,value);
		                	   return "<span id='"+pjid+"' class='name' title='"+value+"'></span>";
					      }
  				  }, */
		{
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            visible:false,
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },{
            title: '系统单号',
            field: 'systemnumber',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                return "<span>" + value + "</span>";
            }
        },
        {
           title: '项目清单',
           field: '',
           align: 'center',
           formatter: function(value, row, index) {
           	return '<span><a href="javascript:void(0);" style="color:#0d7ccd;" onclick="proSearch(\'' + row.usercode + '\')">查看详情</a></span>'
           }
        },
        {
            title: '订单编号',
            field: 'ordernumber',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '加工单位',
            field: 'processunit',
            align: 'center',
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            	//return "<select class='machineSelect'><option value=''>请选择</option></select>";
            }
        },
        {
            title: '医生',
            field: 'doctor',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '患者姓名',
            field: 'username',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '性别',
            field: 'sex',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '年龄',
            field: 'age',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '电话',
            field: 'phonenumber',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '收模人',
            field: 'chargemodelperson',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '取模日期',
            field: 'deliverytime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '收模日期',
            field: 'chargemodeltime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '出货时间',
            field: 'sendouttime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '状态',
            field: 'status',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	var t = "";
            	if (value == 0) {
                    t = '未发件';
                } else if (value == 1) {
                    t = '已发件';
                } else if (value == 2) {
                    t = '已回件';
                } else if (value == 3) {
                    t = '戴走';
                } else if (value == 4) {
                    t = '返工';
                } else if (value == 5) {
                    t = '作废';
                }else if (value == 6 ) {
                	t = '试戴中';
                }else if (value == 7) {
                    t = '已带走';
                }
            	return "<span>" + t + "</span>";
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrow = $('#table').bootstrapTable('getData')[index];
		/* if(onclickrow.processunit!="d40eb0de-61b8-4a8b-9e19-ba5d4c17e81c"){
			$("#bianji").hide();
			$("#xiangqing").hide();
			$("#daizou").hide();
			$("#zuofei").hide();
			$("#kdxm").hide();
		}else if(onclickrow.processunit=="d40eb0de-61b8-4a8b-9e19-ba5d4c17e81c"){
			$("#bianji").show();
			$("#xiangqing").show();
			$("#daizou").show();
			$("#zuofei").show();
			$("#kdxm").show();
		} */        		   
    }).on('dbl-click-cell.bs.table', function (e, row, element) {
    	var index = $('#table').find('tr.success').data('index'); //获得选中的行
    	onclickrowOobj = $('#table').bootstrapTable('getData')[index];
    	 if (onclickrowOobj == "" || onclickrowOobj == null) {
             layer.alert('请选择患者！', {
                   
             });
             return false;
         }
         layer.open({
             type: 2,
             title: '加工单详细信息',
             shadeClose: false,
             shade: 0.6,
             area: ['90%', '90%'],
             content: contextPath + '/KQDS_OutProcessingSheetAct/toYcjgShow.act?id=' + onclickrowOobj.seqId
         });
    });
}
function proSearch(usercode){
	layer.open({
        type: 2,
        title: '患者单详细信息',
        shadeClose: false,
        shade: 0.6,
        area: ['50%', '48%'],
        content: contextPath + '/KQDS_OutProcessingSheetAct/toUserCost.act?usercode=' + usercode
    });
}
//按钮权限
function getButtonPower() {
    var menubutton = '<span class="text">共有记录 <i style="font-style:normal" class="total" id="total"></i> 条</span>';
    var menubutton1 ='';
    for (var i = 0; i < listbutton.length; i++) {
    	//console.log("按钮="+listbutton[i].qxName);
        if (listbutton[i].qxName == "bianji") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="bianji"  onclick="bianji()">编辑</a>';
        } else if (listbutton[i].qxName == "fajian") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="fajian" onclick="fajian()">发件</a>';
        } else if (listbutton[i].qxName == "huijian") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="huijian" onclick="huijian()">回件</a>';
        } else if (listbutton[i].qxName == "fangong") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="fangong" onclick="fangong()">返工</a>';
        } else if (listbutton[i].qxName == "zuofei") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="zuofei" onclick="zuofei()">作废</a>';
        }/* else if (listbutton[i].qxName == "jgc_rh") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="jgc_rh" onclick="ruhuo()">入货</a>';
        } else if (listbutton[i].qxName == "jgc_xm") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="jgc_xm" onclick="xiumo()">修模</a>';
        } else if (listbutton[i].qxName == "jgc_lx") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="jgc_lx" onclick="laxing()">蜡型</a>';
        } else if (listbutton[i].qxName == "jgc_cam") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="jgc_cam" onclick="CAD()">CAD/CAM</a>';
        }else if (listbutton[i].qxName == "jgc_cj") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="jgc_cj" onclick="chenjin()">车金</a>';
        } else if (listbutton[i].qxName == "jgc_sc") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="jgc_sc" onclick="shangci()">上瓷</a>';
        } else if (listbutton[i].qxName == "jgc_cc") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="jgc_cc" onclick="checi()">车瓷</a>';
        } else if (listbutton[i].qxName == "jgc_py") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="jgc_py" onclick="paiya()">排牙</a>';
        }else if (listbutton[i].qxName == "jgc_dmcj") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="jgc_dmcj" onclick="damo()">打磨充胶</a>';
        } else if (listbutton[i].qxName == "jgc_ch") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="jgc_ch" onclick="chuhuo()">出货</a>';
        } */ else if (listbutton[i].qxName == "jgc_fghj") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="jgc_fghj" onclick="fghj()">返工回件</a>';
        }  else if (listbutton[i].qxName == "jgzx_scbb") {
            menubutton1 += ' <a href="javascript:void(0);" class="kqdsCommonBtn"  onclick="exportTable()">生成报表</a>';
        }
    }
    $(".btnBar").prepend(menubutton1);
    $("#tableBarDiv").append(menubutton);
}

//刷新整个页面
function refresh() {
    window.location.reload();
}

//计算左侧表格高度保证一屏展示
function setHeight() {
    $("#table").bootstrapTable("resetView",{
    	height:$(window).outerHeight()-$(".listWrap .listHd").outerHeight()-$(".searchModule").outerHeight()-$(".buttonBar").outerHeight()-20
    });
}
</script>
</html>
