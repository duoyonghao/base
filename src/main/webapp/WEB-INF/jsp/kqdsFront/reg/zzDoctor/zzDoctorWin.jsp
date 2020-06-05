<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String contextPath = request.getContextPath();
  if (contextPath.equals("")) {
    contextPath = "/kqds";
  }
  String usercode = request.getParameter("usercode");
  if(usercode==null){
	  usercode="";
  }
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>转诊记录列表</title><!-- 从咨询中心 > 转诊按钮 进入  -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.min.css" />

<style type="text/css">
/*去掉position: fixed;使转诊按钮不置底 */
.position-bottom{position:inherit;right:15px;left: 15px;background: #fff;padding-bottom: 20px;text-align: center;}
.bootstrap-table .table thead{
	background:#00A6C0 ;
}
.bootstrap-table .table thead th{
	color:#fff;
}
.fixed-table-body thead th .th-inner{
	padding-right:8px;
}
</style>
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
						<label>转诊日期</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</span>
						</div>
					</div>
					<div class="kv" >
						 <label>转诊患者</label>
						<div class="kv-v">
							   <input type="text" placeholder="患者编号/姓名" id="queryinput" name="queryinput">
						</div>
					</div>
					<div class="kv ">
			            <label>原医生</label>
			            <div class="kv-v">
							 <input type="hidden" name="olddoctor" id="olddoctor"  class="form-control" />
							 <input type="text"  class="form-control" id="olddoctorDesc" name="olddoctorDesc" placeholder="原医生"  onClick="javascript:single_select_user(['olddoctor', 'olddoctorDesc'],'',1);"  ></input>	
			        	</div>
      	 			 </div>
      	 			 <div class="kv ">
			            <label>转诊医生</label>
			            <div class="kv-v">
							 <input type="hidden" name="todoctor" id="todoctor"  class="form-control" />
							 <input type="text"  class="form-control" id="todoctorDesc" name="todoctorDesc" placeholder="转诊医生"  onClick="javascript:single_select_user(['todoctor', 'todoctorDesc'],'',1);"  ></input>	
			       		 </div>
			       	</div>
                </div>
            </div>
        </div> 
    <div class="tableBox">
       <table id="table" class="table-striped table-condensed table-bordered" ></table>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/language/zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var usercode = "<%=usercode %>";
var pageurl = '<%=contextPath%>/KQDS_ChangeDoctorAct/selectNoPage.act';
var qxnameArr = ['zzzx_scbb'];
var func = ['exportTable'];
$(function(){
	//获取当前页面所有按钮
    getButtonPowerByPriv(qxnameArr,func);
	if(usercode!=""){
		$('#queryinput').val(getNameByusercodesTB(usercode)[0].username);
	}
	//时间选择
	$(".unitsDate input").datetimepicker({
		language:  'zh-CN',  
		minView:2,
        autoclose : true,
		format: 'yyyy-mm-dd',
		pickerPosition: "buttom-right"
	});
	//绑定两个时间选择框的chage时间
	$("#starttime,#endtime").change(function(){
		timeCompartAndFz("starttime","endtime");
    });
	getlzjl();
});
function queryParams(params) {
    var temp = {  //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    		starttime:$('#starttime').val(),
    		endtime:$('#endtime').val(),
    		queryinput:$('#queryinput').val(),
    		olddoctor:$('#olddoctor').val(),
    		todoctor:$('#todoctor').val()
    };
    return temp;
}
function getlzjl(){
	//流转记录
	$('#table').attr("data-height",$(window).height() - 100);
	
	$('#table').bootstrapTable({
	url:pageurl, 
	dataType: "json",
	queryParams:queryParams,
	cache: false, 
	striped: true,
	columns: [
		{title: '患者姓名',field: 'username',align: 'center',valign: 'middle',sortable: true},
		{title: '患者编号',field: 'usercode',align: 'center',valign: 'middle',sortable: true},
		{title: '原医生',field: 'olddoctor',align: 'center',valign: 'middle',sortable: true,
			formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
		},
		{title: '转诊医生', field: 'todoctor',align: 'center', valign: 'middle',sortable: true,
			formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
		{title: '转诊原因',field: 'remark',align: 'center',valign: 'middle',sortable: true},
		{title: 'seqId',field: 'seqId',align: 'center',valign: 'middle',visible:false,switchable:false}, 
		{title: '转诊时间',field: 'createtime',align: 'center',sortable: true,editable: true,},
		{title: '操作人',field: 'createuser',align: 'center',sortable: true,
			formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
          }
     	]
	});
}
$('#query').on('click', function(){
	 $('#table').bootstrapTable('refresh',{'url':pageurl}); 
});
$('#clean').on('click', function(){
	$(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected");//核心
});
//导出
function exportTable(){
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
	location.href = pageurl+"?flag=exportTable&fieldArr="+JSON.stringify(fieldArr)+"&fieldnameArr="+JSON.stringify(fieldnameArr)+"&"+param;
}
function openLayerDialogResize(actionUrl, width, height) {
	layer.open({
		type : 2,
		title : '人员选择',
		maxmin : true,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '500px', '90%' ],
		content : actionUrl
	});

}
function refresh(){
	if(parent.initTable){ 
		parent.getPayOrderlist(); // 重新加载已结账列表
	}
	$('#table').bootstrapTable('refresh',{'url':pageurl}); 
}
</script>

</html>
