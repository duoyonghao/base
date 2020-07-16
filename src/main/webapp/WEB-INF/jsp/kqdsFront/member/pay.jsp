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
    <title>会员卡-缴费</title>
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
	 
</head>
<body>
<div id="container">
   <!--查询条件-->
     <div class="searchWrap">
            <div class="cornerBox">查询条件</div>
            <div class="btnBar">
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
						<label>缴费时间</label>
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
    	<table id="table" class="table-striped table-condensed table-bordered" data-height="250"></table>
    </div>
    <div class="tableBox" align="center">
    	<table style="width:90%;text-align: center;"> 
       		<tr>
      			<td style="width:12%"><span style="color:orange;">缴费-充值小计:<lable id="tdmoney">0</lable></span></td>
      			<td style="width:12%"><span style="color:orange;">缴费-赠送小计:<lable id="tdgivemoney">0</lable></span></td>
      			<td style="width:12%"><span style="color:orange;">缴费小计:<lable id="tdtotal">0</lable></span></td>
       		</tr> 
       	</table>
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
var tdmoney=0,tdgivemoney=0,tdtotal=0;
var menuid = "<%=menuid%>";
var qxnameArr = ['jfcx_scbb'];
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
	getjiaofeilist();
});


//缴费
function getjiaofeilist(){
	$table.attr("data-height",$(window).height() - 110);
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
	 		$("#tdmoney").html(tdmoney.toFixed(2));
	 		$("#tdgivemoney").html(tdgivemoney.toFixed(2));
	 		$("#tdtotal").html(tdtotal.toFixed(2));
	 		/*表格载入时，设置表头的宽度 */
	        setTableHeaderWidth(".tableBox");
	    },
		columns: [
				{title: '序号',field: ' ',align: 'center',valign: 'middle',sortable: true,editable: true,
					formatter:function(value,row,index){  
				        return number++ ;
					}
				},
				{
	                  title: '缴费时间',
	                  field: 'createtime',
	                  align: 'center',
	                  sortable: true,
	                  editable: true,
	                  formatter:function(value,row,index){  
		                  return '<span title="'+value+'">' + value + '</span>';
              	  }
                },
				{
					title: '患者编号',
					field: 'usercode',
					align: 'center',
					
					sortable: true,
					editable: true,
					formatter:function(value,row,index){  
		                  return '<span>' + value + '</span>';
            	  }
					
				},
				{title: '姓名',field: 'username',align: 'center',sortable: true,editable: true,
					formatter:function(value,row,index){  
		                return '<span class="name" title="'+value+'">' + value + '</span>';
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
		                return '<span class="source">' + value + '</span>';
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
				{title: '卡号',field: 'cardno',align: 'center',sortable: true,editable: true,
				    formatter:function(value,row,index){  
		                return '<span title="'+value+'">' + value + '</span>';
               	    }
				},
				{
					title: '类型',
					field: 'type',
					align: 'center',
					
					sortable: true,
					editable: true,
					formatter:function(value){
						return '<span>'+value+'</span>'
					}
					},
				{title: '缴费-充值',field: 'cmoney',align: 'center',sortable: true,editable: true,
					formatter:function(value,row,index){
                	    if(value!="" && value!=null){
                	    	tdmoney = tdmoney + Number(row.cmoney);
               	    		return '<span class="money" style="color:orange">'+value+'</span>';
                	    }else{
                	    	return '<span class="money" style="color:orange">0.0</span>';
                	    }
               	    }
				},
				{title: '缴费-赠送',field: 'cgivemoney',align: 'center',sortable: true,editable: true,
					formatter:function(value,row,index){  
                	    if(value!="" && value!=null){
                	    	tdgivemoney = tdgivemoney + Number(value);
               	    		return '<span class="money" style="color:orange">'+value+'</span>';
                	    }else{
                	    	return '<span class="money" style="color:orange">0.0</span>';
                	    }
               	    }
				},
				{title: '缴费小计',field: 'ctotal',align: 'center',sortable: true,editable: true,
					formatter:function(value,row,index){  
                	    if(value!="" && value!=null){
                	    	tdtotal = tdtotal + Number(row.ctotal);
               	    		return '<span style="color:orange">'+value+'</span>';
                	    }else{
                	    	return '<span style="color:orange">0.0</span>';
                	    }
               	    }
				},
                {
                  title: '操作人',
                  field: 'createuser',
                  align: 'center',
                  sortable: true,
                  editable: true,
                  formatter:function(value){
                	  return "<span class='name'>"+value+"</span>";
                  }
              }
		 ]
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
   		type:'缴费',
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
				a.download = "缴费查询";
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
	number = 1;//序号
	tdmoney=0,tdgivemoney=0,tdtotal=0,tdymoney=0,tdygivemoney=0,tdytotal=0;//初始化底部计算的小计
	$table.bootstrapTable('refresh',{'url':pageurl});
});
</script>
</html>
