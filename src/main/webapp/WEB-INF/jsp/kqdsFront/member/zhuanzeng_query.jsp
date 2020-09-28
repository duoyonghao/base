<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuid");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>会员卡-转赠查询</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<style>
	.fixed-table-container{
		border:none;
	}
	.tableBox{
		border:1px solid #ddd;
	}
</style>
</head>
<body>
<div id="container">
   <!--查询条件-->
     <div class="searchWrap">
            <div class="cornerBox">查询条件</div>
            <div class="btnBar">
           		<a href="javascript:void(0);" class="kqdsCommonBtn" id="dayin">打印</a>
                <a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
                <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
            </div>
            <div class="formBox">
               	<div class="kv">
               		<div class="kv">
	                    <label>所属门诊</label>
	                    <div class="kv-v">
	                        <select id="organization" name="organization"></select>
	                    </div>
	                </div>
               		<div class="kv" style="width:300px;">
						<label>转赠时间</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" /><span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</span>
						</div>
					</div>
		            <div class="kv">
	                    <label></label>
	                    <div class="kv-v">
	                        <input type="text" id="queryInput" class="searchInput" placeholder="请输入用户编号/姓名/会员卡号" style="width:200px;">
	                        <span class="orangeFont">*可模糊查询</span>
	                    </div>
                	</div>
                </div>
            </div>
        </div> 
    <div class="tableBox">
    	<table id="table" class="table-striped table-condensed table-bordered" data-height="auto"></table>
    </div>
</div>
</body>
<script type="text/javascript">

var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var onclickrow = "";
var nowday;
var $table = $("#table");
var pageurl = '<%=contextPath%>/KQDS_Member_RecordAct/selectListByType.act';
var number = 1;//序号
var onclickrowobj = "";
var menuid = "<%=menuid%>";
var qxnameArr = ['zzcx_scbb'];
var func = ['exportTable'];
var isClick = true;

$(function(){
	$("input[type='text']").attr("autocomplete","off");  //去掉input输入框的历史记录  lutian
	initHosSelectList4Front('organization'); // 连锁门诊下拉框
	//获取当前页面所有按钮
    getButtonPowerByPriv(qxnameArr,func,menuid);
 	//时间选择
	$(".unitsDate input").datetimepicker({
		language:  'zh-CN',
		minView:2,
        autoclose : true,
		format: 'yyyy-mm-dd',
		pickerPosition: "bottom-right"
	});
 	
	nowday = getNowFormatDate();
	$("#starttime").val(nowday);
	$("#endtime").val(nowday);
	//绑定两个时间选择框的chage时间
	$("#starttime,#endtime").change(function(){
		timeCompartAndFz("starttime","endtime");
    });
	//判断父页面是否选中行 选中则根据选中的值查询
 	onclickrow = window.parent.onclickrowOobj;
 	if(onclickrow != "" && onclickrow != null && onclickrow != "null" && onclickrow != "undefined"){
 		$("#queryInput").val(onclickrow.username);
 		$("#starttime").val("");
 		$("#endtime").val("");
 	}
	
	//加载表格
	getlist();
});
//补打充值单
$('#dayin').on('click',
function() {
    if (onclickrowobj == null || onclickrowobj == "") {
        layer.alert('请选择转赠记录' );

        return;
    }

    //先查询fyqrd页面在打印设置里 是a4还是a5，如果是a4则跳转a4界面
    var printSet = getPrintType("转赠单");
    var totalmoney = Number(onclickrowobj.zzmoney) + Number(onclickrowobj.zzgivemoney);
    var ymoney = Number(onclickrowobj.ymoney) + Number(onclickrowobj.zzmoney);
    var ygivemoney = Number(onclickrowobj.ygivemoney) + Number(onclickrowobj.zzgivemoney);
    var ytotalmoney = Number(onclickrowobj.ytotal) + Number(totalmoney);

    // 默认A5打印
    var hyurl = contextPath + '/KQDS_Print_SetAct/toHyczPrintPage.act?printpage='+printSet.printurl+'&printType=' + printSet.printtype + '&cztime=' + onclickrowobj.createtime + '&memberno=' + onclickrowobj.cardno + '&username=' + onclickrowobj.username + '&szrusercode=' + onclickrowobj.szrusercode + '&szrusername=' + onclickrowobj.szrusername + '&szcardno=' + onclickrowobj.szcardno + '&money=' + onclickrowobj.zzmoney + '&givemoney=' + onclickrowobj.zzgivemoney + '&totalmoney=' + Number(totalmoney).toFixed(2) + '&usercode=' + onclickrowobj.usercode + '&ymoney=' + Number(ymoney).toFixed(2) + '&recordId=' + onclickrowobj.seqId + '&ygivemoney=' + Number(ygivemoney).toFixed(2) + '&ytotalmoney=' + Number(ytotalmoney).toFixed(2) + '&cost_organization=' + onclickrowobj.organization;

    // 弹出打印页面
    parent.layer.open({
        type: 2,
        title: '打印',
        shadeClose: true,
        //点击遮罩关闭层
        area: ['1000px', '550px'],
        content: hyurl
    });
});
//页面加载查询的表格 总的
function getlist(){
	number = 1;
	$table.attr("data-height",$(window).height() - 100);
	$table.bootstrapTable({
		url:pageurl, 
		dataType: "json",
		cache: false, 
		striped: true,
		queryParams:queryParams,
	 	onLoadSuccess: function(data){  //加载成功时执行
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","auto").css("pointer-events","auto");
				$("#query").text("查询");
			}
	 		/*表格载入时，设置表头的宽度 */
	        setTableHeaderWidth(".tableBox");
	 	},
		columns: [
				{title: '序号',field: ' ',align: 'center',sortable: true,editable: true,
					formatter:function(value,row,index){
		                return number++ ;
                	}
				},
				{
	                 title: '转赠时间',
	                 field: 'createtime',
	                 align: 'center',
	                 sortable: true,
	                 editable: true,
	                 formatter:function(value,row,index){  
		                 return '<span title="'+value+'">' + value + '</span>';
             	  	 }
             	},
				{title: '转赠人编号',field: 'usercode',align: 'center',sortable: true,editable: true,
             		formatter:function(value,row,index){
		                return '<span title="'+value+'">' + value + '</span>';
            	  	}
             	},
				{title: '转赠人',field: 'username',align: 'center',sortable: true,editable: true,
					formatter:function(value,row,index){
		                return '<span class="name" title="'+value+'">' + value + '</span>';
                	}
				},
				{title: '转赠会员卡',field: 'cardno',align: 'center',sortable: true,editable: true,
					formatter:function(value,row,index){
		                return '<span title="'+value+'">' + value + '</span>';
                	}
				},
				{title: '第一咨询',field: 'faskperson',align: 'center',sortable: true,
					formatter:function(value,row,index){  
		                return '<span class="name">' + value + '</span>';
		        	}
				},
				{title: '接诊咨询',field: 'askperson',align: 'center',sortable: true,
					formatter:function(value,row,index){  
		                return '<span class="name">' + value + '</span>';
		        	}
				},
				{title: '患者来源',field: 'devchannel',align: 'center',sortable: true,
					formatter:function(value,row,index){  
		                return '<span class="name">' + value + '</span>';
                	}
				},
				{title: '来源子分类',field: 'nexttype',align: 'center',sortable: true,
					formatter:function(value,row,index){  
		                return '<span class="name">' + value + '</span>';
                	}
				},
				{title: '建档人',field: 'jdr',align: 'center',sortable: true,
		            formatter: function(value, row, index) {
		                return '<span class="name">' + value + '</span>';
		            }
		        },
		        {title: '开发人',field: 'kfr',align: 'center',sortable: true,
		            formatter: function(value, row, index) {
		                return '<span class="name">' + value + '</span>';
		            }
		        },
				{title: '转赠-充值',field: 'zzmoney',align: 'center',sortable: true,editable: true,
              		formatter:function(value,row,index){
		                return '<span>'+ value +'</span>';
            	  	}
              	},
				{title: '转赠-赠送',field: 'zzgivemoney',align: 'center',sortable: true,editable: true,
              		formatter:function(value,row,index){
              			return '<span>'+ value +'</span>';
            	  	}
              	},
				{title: '转赠-小计',field: 'zztotal',align: 'center',sortable: true,editable: true,
              		formatter:function(value,row,index){
						var zztotal = 0.0;
						zztotal = (Number(row.zzmoney) + Number(row.zzgivemoney)).toFixed(2);
              			return '<span>'+zztotal + "</span>";
            	  	}
              	},
              	{title: '受赠人编号',field: 'szrusercode',align: 'center',sortable: true,editable: true,
             		formatter:function(value,row,index){
		                return '<span title="'+value+'">' + value + '</span>';
            	  	}
             	},
				{title: '受赠人',field: 'szrusername',align: 'center',sortable: true,editable: true,
					formatter:function(value,row,index){
		                return '<span class="name" title="'+value+'">' + value + '</span>';
                	}
				},
				{title: '受赠会员卡',field: 'szcardno',align: 'center',sortable: true,editable: true,
					formatter:function(value,row,index){
		                return '<span title="'+value+'">' + value + '</span>';
                	}
				},
				{title: '转赠备注',field: 'tfremark',align: 'center',sortable: true,editable: true,
					formatter:function(value,row,index){  
		                return '<span class="remark" title="'+value+'">' + value + '</span>';
                	}
				},
                {
                  title: '操作人',
                  field: 'createuser',
                  align: 'center',
                  sortable: true,
                  editable: true,
                  formatter:function(value,row,index){
		          	return '<span class="name" title="'+value+'">' + value + '</span>';
          	  	}
              }
		 ]
	}).on('click-row.bs.table', function (e, row, element){
		$('.success').removeClass('success');//去除之前选中的行的，选中样式
  		$(element).addClass('success');//添加当前选中的 success样式用于区别
  		var index = $('#table').find('tr.success').data('index');//获得选中的行
  		onclickrowobj =  $('#table').bootstrapTable('getData')[index];
	});
}

$('#clean').on('click', function(){
	$(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected");//核心
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
});

function queryParams(params) {
    var temp = {  //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
   		starttime:$('#starttime').val(),
   		endtime:$('#endtime').val(),
   		organization : $('#organization').val(), 
   		type:'转赠',
   		queryinput :$("#queryInput").val()
    };
    return temp;
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
//点击导出
function exportTable(){
	if(isClick) {
		isClick = false;
		// console.log("生成报表")
		var fieldArr=[];
		var fieldnameArr=[];
		$('#table thead tr th').each(function () {
			var field = $(this).attr("data-field");
			if(field!=""){
				fieldArr.push(field);//获取字段
				fieldnameArr.push($(this).children()[0].innerText);//获取字段中文
			}
		});
		var param  = JsontoUrldata(queryParams());
		var url = pageurl+"?flag=exportTable&fieldArr="+JSON.stringify(fieldArr)+"&fieldnameArr="+JSON.stringify(fieldnameArr)+"&"+param;
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
				a.download = "转赠查询";
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
$('#query').on('click', function(){
	$(this).attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
	$(this).text("查询中");
	onclickrowobj = "";//初始化之前选中的行对象
	number = 1;//初始化序号
// 	tdmoney=0,tdgivemoney=0,tdtotal=0,tdxj=0,tdyhk=0,tdqt=0,tdzfb=0,tdwx=0;//初始化底部计算的小计
	$table.bootstrapTable('refresh',{'url':pageurl});
});
</script>
</html>
