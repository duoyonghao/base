<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String canDelCk = UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag18_canDelCk, request);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>领料查询</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/select2.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
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
</style>
</head>
<body>
<div id="container">
   <!--查询条件-->
     <div class="searchWrap" >
            <div class="cornerBox">查询条件</div>
            <div class="btnBar">
                <a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
                <a href="javascript:void(0);" class="kqdsCommonBtn" id="query" onclick="refresh()">查询</a>
                <!-- <a href="javascript:void(0);" class="kqdsSearchBtn" onclick="openDayin();" >今日药单</a> -->
            </div>
            <div class="formBox">
               	<div class="kv">
               		<div class="kv">
						<label>申领日期</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</span>
						</div>
					</div>
					<div class="kv">
		                <label>申领人</label>
		                <div class="kv-v">
		                    <input type="text"  name="hzname" id="hzname"/>
		                </div>
	           		</div>
	           		<div class="kv">
		                <label>申领科室</label>
		                <div class="kv-v">
		                    <select id="deptpart" name="deptpart"></select>
		                </div>
	           		</div>
	           		<div class="kv">
		                <label>领用状态</label>
		                <div class="kv-v">
		                    <select  name="status" id="status">
		                    	<option value="">请选择</option>
		                    	<option value="0" selected>备货中</option>
		                    	<option value="2">已领走</option>
		                    </select>
		                </div>
		            </div>
		            <div class="kv">
		                <label>领用楼层</label>
		                <div class="kv-v">
		                    <select  name="floor" id="floor">
		                    	<option value="">请选择</option>
		                    	<option value="1" selected>负一层</option>
		                    	<option value="2">二楼</option>
		                    </select>
		                </div>
		            </div>
                </div>
            </div>
        </div> 
    <div class="tableBox">
        <table id="table" class="table-striped table-condensed table-bordered"></table>
    </div>

    <div class="tableHd">商品列表</div>
    <div class="tableBox1" id="divkdxm" >
        <table id="dykdxm" class="table-striped table-condensed table-bordered" ></table>
    </div>
    <div class="tableBox">
    	<table style="width: 100%"> 
       		<tr>
 				<td width="30%"><span style="color:#00A6C0;">共有记录<lable id="total">0</lable>条</span></td>
 				<td width="30%"><span style="color:#00A6C0;">申领数量：<lable id="nums">0</lable></span></td>
 				<td width="30%"><span style="color:#00A6C0;">实发数量：<lable id="actualnums">0</lable></span></td>
       		</tr> 
       	</table>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/ykzx/yk.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/ksll/ksll.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var apppath = apppath();
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl = apppath + '/HUDH_KSllAct/findAllKsllColor.act';
var onclickrowOobj2 = "";
var goodsDetail = "";
var nowday;
//允许删除仓库出入库记录
var canDelCk = "<%=canDelCk%>";
$(function() {
	initDept($("#deptpart"));//初始化领料部门
    //时间选择
    $(".unitsDate input").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "bottom-right"
    });
	//$("#floor").val(1);
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    getlist();
    OrderDetail();
});
function setHeight(){
	var windowHeight=$(window).outerHeight();
	var tableHeight=(windowHeight-$(".searchWrap").outerHeight()-60)/2;//本页面有两个table
	$("#table").bootstrapTable("resetView",{
		height: tableHeight
	})
	$("#dykdxm").bootstrapTable("resetView",{
		height: tableHeight
	})
}
function getlist() {
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        onLoadSuccess:function(){
        	setHeight();
        },
        columns: [{
		    title: '操作',
		    field: ' ',
		    align: 'center',
		    
		    formatter: function(value, row, index) {
		    	var html = '';
		    	if(row.status == '2'){
		    		html = '<span><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="" style="color:black;">--</a> </span>';
            	}else{
            		html = '<span><a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:red;" onclick="deleteCollor(\'' + row.id + '\');">删除</a> </span>';
            	}
		        return html;
		    }
		},
		{
            title: 'id',
            field: 'id',
            visible: false
        },
        {
            title: '申领科室',
            field: 'deptpartname',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '申领人',
            field: 'creatorname',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '申领日期',
            field: 'createtime',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '附加说明',
            field: 'remark',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '申领状态',
            field: 'status',
            align: 'center',
            formatter:function(value){
            	if(value == '2'){
            		return '<span style="color:green">已领走</span>';
            	}else if(value == '0'){
            		return '<span class="label-danger">备货中</span>';
            	}else if(value == '1'){
            		return '<span style="color:green">备货完成</span>';
            	} 
            }
        }
       ]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj2 = $('#table').bootstrapTable('getData')[index];
        OrderDetail(onclickrowOobj2.id); 
    });
}
$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        hzname: $('#hzname').val(),
        deparent : $('#deptpart').val(),
        status:$('#status').val(),
        floor:$("#floor").val()
    };
    return temp;
}
function refresh(){
	//点击查询时，置空入库明细列表
	$("#total").html("0");
	$("#nums").html("0");
	$("#actualnums").html("0");
	$('#dykdxm').find('tbody').html("");
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
function OrderDetail(parentId) {
    $("#divkdxm").html('<table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="230"></table>');
    var detailurl = apppath + '/HUDH_KSllAct/findKsllColorDetailByparentid.act?parentId='+parentId;
    $("#dykdxm").bootstrapTable({
        url: detailurl,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	//console.log("测试数据="+JSON.stringify(data));
        	$("#total").html(data.length);
        	var nums=0;
        	var actualnums=0;
	        for(var i=0;i<data.length;i++){
	        	nums += Number(data[i].cknums);
	        	if(data[i].status != "1") { //1标识库房删除的记录
	        		actualnums += Number(data[i].cknums);
	        	}
	        }
        	$("#nums").html(nums);
        	$("#actualnums").html(actualnums);
        	setHeight();
        	/*表格出现滚动条时 调整表头*/
            setTableHeaderWidth(".tableBox1");
        },
        rowStyle: function(row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (row.status == "1" || row.status == "2") {
                strclass = 'danger';
            } else {
                return {};
            }
            return {
                classes: strclass
            };
        },
        columns: [
			{
            title: '所属仓库',
            field: 'housename',
            align: 'center',
            /* sortable: true, */
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
		{
            title: '一级类别',
            field: 'firsttype',
            align: 'center',
            /* sortable: true, */
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '二级类别',
            field: 'goodstypename',
            align: 'center',
            /* sortable: true, */
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '商品编号',
            field: 'goodscode',
            align: 'center',
            /* sortable: true, */
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '商品名称',
            field: 'goodsname',
            align: 'center',
            formatter: function(value, row, index) {
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '商品规格',
            field: 'goodsnorms',
            align: 'center',
            /* sortable: true, */
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '领用数量',
            field: 'cknums',
            align: 'center',
            formatter:function(value, row, index){
            	if(row.parent_status == "2") {
            		return '<span>'+value+'</span>';
            	}else {
            		return '<span><input id="cknums'+index+'" value="'+value+'" style="text-align:center;border:0;" onchange="changeNums(\'' + row.id + '\',\'' + index + '\')"/></span>';
            	}
            }
        },
        {
            title: '单位',
            field: 'goodsunit',
            align: 'center',
            /* sortable: true, */
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '库房调整说明',
            field: 'ckchange',
            align: 'center',
            /* sortable: true, */
            formatter:function(value){
            	return '<span style="color:red;">'+value+'</span>';
            }
        }]
    });
}

function openDayin() {
    if (onclickrowOobj2 == "") {
        layer.alert('请选择需要打印的入库单！', {
            
        });
        return false;
    }
    // 先查询fyqrd页面在打印设置里 是a4还是a5，如果是a4则跳转a4界面
    var printSet = getPrintType("药品入库单");
    var costurl = "";
    // 默认 a5
    costurl = apppath + '/KQDS_Print_SetAct/toInGoodsPrintPage.act?printpage='+printSet.printurl+'&printType=' + printSet.printtype + '&incode=' + onclickrowOobj2.incode;
    // 弹出打印页面
    parent.layer.open({
        type: 2,
        title: '打印',
        shadeClose: true,
        //点击遮罩关闭层
        area: ['1000px', '550px'],
        content: costurl
    });
}
function goodsEdit(index){
	goodsDetail = $('#dykdxm').bootstrapTable('getData')[index];
	// 弹出打印页面
	layer.open({
		type: 2,
		title: '修改入库明细',
		shadeClose: true, //点击遮罩关闭层
		area : ['90%' , '90%'],
		content: apppath+'/KQDS_Ck_Goods_In_DetailAct/toCkInGoodsDetailEdit.act'
	});
}
function deleteCollor(id){
	if(!id){
		layer.alert("获取申领单失败,请重新打开");
		return;
	}
	layer.alert("确认删除？", {
		  closeBtn: 1    // 是否显示关闭按钮
		  ,anim: 1 //动画类型
		  ,icon : 5 //文字前方表情类型
		  ,btn: ['确认','关闭'] //按钮
		  ,yes:function(index){
			 $.ajax({
					url: apppath + "/HUDH_KSllAct/deleteKsllNotCK.act",
					type:"POST",
					dataType:"json",
					data : {"status" : status,"id" : id},
					success:function(result){
						layer.alert('操作成功', function(index) {
							refresh(); //刷新父页面
							layer.close(index);
						})
					}
				});
		  }
		  ,btn2:function(){}
	})
	return;
 }
 function changeNums(id,index){
	 var newNums = $("#cknums"+index).val();
	 $.ajax({
		url: apppath+'/HUDH_KSllAct/updateNumsById.act',
		type:"POST",
		data:{"id":id,"newNums":newNums},
		dataType:"json",
		success:function(result){
		}
	});
 }
</script>
</html>
