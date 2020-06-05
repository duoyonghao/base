<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>领料统计</title><!-- 该页面只做演示使用，未进行入库代码编写，已经被注释 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.min.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/defaults-zh_CN.js"></script>
<!-- jquery 导出excel -->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.table2excel.js"></script>
</head>
<style>
select {
    padding: 0 10px;
    width: 120px;
    height: 28px;
    line-height: 28px;
    border: solid 1px #e5e5e5;
    border-radius: 3px;
    -webkit-transition: all .3s;
    transition: all .3s;
}
.searchWrap .kv select {
    width: 120px;
    height: 24px;
    line-height: 24px;
    box-sizing: border-box;
}
.time{
    max-width: 150px;
}
.tableBody{
		overflow-y:auto;
}
#container{
	padding:2px;
	overflow:hidden;
}
#hiddentable td,#hiddentable th{
	border:none;
}
.table-bordered > thead > tr > th, .table-bordered > tbody > tr > th, .table-bordered > tfoot > tr > th, .table-bordered > thead > tr > td, .table-bordered > tbody > tr > td, .table-bordered > tfoot > tr > td{
	border:none;
}
.table-bordered{
	border:none;
	border-top:1px solid #ddd;
}
</style>
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
		                    <label>门诊</label>
		                    <div class="kv-v">
		                        <select id="organization" name="organization"></select>
		                    </div>
		            </div>
               		<div class="kv">
						<label>收费日期</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</span>
						</div>
					</div>
					<div class="kv" >
						 <label>*模糊查询</label>
						<div class="kv-v">
							   <input type="text" placeholder="患者编号/姓名/手机号/项目名称" id="queryinput" name="queryinput" style="width:200px;">
						</div>
					</div>
                </div>
            </div>
        </div> 
    <div class="tableHd">领料明细</div>
      <!-- jquery 生成excel table-responsive table2excel -->
    <div class="table2excel tableBox" id="divkdxm" style="width: 100%;position:relative;">
    	<div class="tableHeader" style="position:absolute; top:0;left:0;height:30.4px;overflow:hidden;">
    		<table id="hiddentable" class="table-striped table-condensed table-bordered" data-height="250" style="width: 100%;text-align: center;">
				<thead style="background: #00A6C0 ;color:white; ">
					<tr>
						<!-- <th style="text-align: center; vertical-align: middle;width:10%;height:30px;">领料编号</th> -->
						<th style="text-align: center; vertical-align: middle;width:10%;height:30px;">姓名</th>
						<th style="text-align: center; vertical-align: middle;width:10%;">编号</th>
						<th style="text-align: center; vertical-align: middle;width:8%;">消费分类</th>
						<th style="text-align: center; vertical-align: middle;width:17%;">消费项目</th>
						<th style="text-align: center; vertical-align: middle;width:5%;">单位</th>
						<th style="text-align: center; vertical-align: middle;width:5%;">数量</th>
						<th style="text-align: center; vertical-align: middle;width:;">材料名称</th>
						<th style="text-align: center; vertical-align: middle;width:5%;">数量</th>
						<th style="text-align: center; vertical-align: middle;width:12%;">领料时间</th>
						<th style="text-align: center; vertical-align: middle;width:8%;">是否治疗</th>
					</tr>
				</thead>
				<tbody style="background-color: #F0FFFF;text-align: center;"></tbody>
			</table>
    	</div>
 		<div class="tableBody">
 			<table id="table" class="table-striped table-condensed table-bordered" data-height="250" style="width: 100%;text-align: center;">
				<thead style="background: #0e7cc9;color:white; ">
					<tr>
						<!-- <th style="text-align: center; vertical-align: middle;width:10%;height:30px;">领料编号</th> -->
						<th style="text-align: center; vertical-align: middle;width:10%;height:30px;">姓名</th>
						<th style="text-align: center; vertical-align: middle;width:10%;">编号</th>
						<th style="text-align: center; vertical-align: middle;width:8%;">消费分类</th>
						<th style="text-align: center; vertical-align: middle;width:17%;">消费项目</th>
						<th style="text-align: center; vertical-align: middle;width:5%;">单位</th>
						<th style="text-align: center; vertical-align: middle;width:5%;">数量</th>
						<th style="text-align: center; vertical-align: middle;width:;">材料名称</th>
						<th style="text-align: center; vertical-align: middle;width:5%;">数量</th>
						<th style="text-align: center; vertical-align: middle;width:12%;">领料时间</th>
						<th style="text-align: center; vertical-align: middle;width:8%;">是否治疗</th>
					</tr>
				</thead>
				<tbody style="background-color: #F0FFFF;text-align: center;"></tbody>
			</table>
 		</div>
    	
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl = '<%=contextPath%>/KQDS_LLTJAct/getAllTreatDetailList.act'; 
var usercodechild = "";//接收layer子窗口传值
var usernamechild = "";//接收layer子窗口传值
var menuid = "<%=menuid%>";
var qxnameArr = ['llcx_scbb'];
var func = ['exportTable'];
var nowday = null;
$(function(){
	initHosSelectList4Front('organization');// 连锁门诊下拉框	
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
	// 比较起始和结束时间，如果结束时间大于起始时间，结束时间重置为开始时间
	$("#starttime,#endtime").change(function(){
		timeCompartAndFz("starttime","endtime");
    });
	loadDataList();
});
<!-- jquery 导出excel -->
function exportTable(){
	$(".table2excel .tableBody").table2excel({
		exclude: ".noExl",//带noExlclass的行不会被输出到excel中
		name: "Excel Document Name",
		filename: "领料查询",
		exclude_img: true,
		exclude_links: true,
		exclude_inputs: true
	});
}
// 加载治疗项目清单
function loadDataList(){
	
	// 查询前先清空
	$("#table").find('tbody').html("");
	$("#hiddentable").find('tbody').html("");
	// 查询条件
	var queryData = {  
		organization: $('#organization').val(),
    	queryinput : $("#queryinput").val(),
    	starttime : $("#starttime").val(),
    	endtime : $("#endtime").val()
    };
	
	$.axseY(pageurl,queryData, 
		function(data){
			var htmlStr = "";
			if(data != null  && data.length > 0){
				for(var i = 0; i < data.length; i ++){
					var trobj = data[i];
					htmlStr += initTrHtml(trobj);
				}
			}
			$("#table").find('tbody').append(htmlStr);  // 加载完成清单后，再加载材料
			initCaiLiaoTrs();
			//设置table的高度
			$(".tableBody").outerHeight($(window).outerHeight()-$(".searchWrap").outerHeight()-$(".tableHd").outerHeight()-18);
		    //设置表头的宽度
			$("#hiddentable").outerWidth($("#table").outerWidth());
		},
		function(){
			layer.alert('操作失败！' );
		}
	);
}

// 加载完治疗项目清单后，再加载每个项目对应的材料清单
function initCaiLiaoTrs(){
	var itemTrs = $("tr[id*='item_tr']");
	for(var i = 0 ; i< itemTrs.length; i++){
		var curTr = itemTrs[i];
		var curId = jQuery(curTr).attr("id");
		var treatdetailno = curId.replace("item_tr","");
		loadSubDataList(treatdetailno); // 加载材料列表
	}
}


function initTrHtml(trobj){
	
	var iszl = trobj.iszl;// 0 未治疗  1已治疗
	var iszlStr = "";
	
	if(iszl == '1'){
		iszlStr = "<span style='color:green;'>已治疗</span>";
	}
	
	var tablehtml = "";
	tablehtml += "<tr style='' id='item_tr"+trobj.seqId+"'>"; 
	tablehtml += '<td style="">' + trobj.username+ '</td>';
	tablehtml += '<td style="">' + trobj.usercode+ '</td>';
	tablehtml += '<td style="">' + trobj.classname+ '</td>';
	tablehtml += '<td style=""><span style="width:17%;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;">' + trobj.itemname+ '</span></td>';
	tablehtml += '<td style="">' + trobj.unit+ '</td>';
	tablehtml += '<td style="">' + trobj.num+ '</td>';
	tablehtml += '<td style="">&nbsp;</td>';
	tablehtml += '<td style="">&nbsp;</td>';
	tablehtml += '<td style="">&nbsp;</td>';
	tablehtml += '<td style="">'+iszlStr+'</td>';
	tablehtml += "</tr>";
	return tablehtml;
}

// 查询条目对应下的领料列表
function loadSubDataList(treatdetailno){
	var requrl = '<%=contextPath%>/KQDS_LLTJ_DetailAct/selectList.act?treatdetailno='+treatdetailno;
	$.axseY(requrl,null, 
		function(data){
			var htmlStr = "";
			if(data != null  && data.length > 0){
				for(var i = 0; i < data.length; i ++){
					var trobj = data[i];
					htmlStr += initSubTrHtml(trobj);
				}
			}
			$("#item_tr"+treatdetailno).after(htmlStr);
		},
		function(){
			layer.alert('操作失败！' );
		}
	);
}


// 材料的tr html
function initSubTrHtml(trobj){
	
	var iszl = trobj.iszl;// 0 未治疗  1已治疗
	var iszlStr = "";
	if(iszl == '1'){
		iszlStr = "<span style='color:green;'>已治疗</span>";
	}
	
	var tablehtml = "";
	tablehtml += "<tr style=''>";
	tablehtml += '<td style="">&nbsp;</td>';
	tablehtml += '<td style="">&nbsp;</td>';
	tablehtml += '<td style="">&nbsp;</td>';
	tablehtml += '<td style="">&nbsp;</td>';
	tablehtml += '<td style="">&nbsp;</td>';
	tablehtml += '<td style="">&nbsp;</td>';
	tablehtml += '<td style=""><span style="width:17%;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;">'+trobj.goodsname+'</span></td>';
	tablehtml += '<td style="">'+trobj.goodsnums+'</td>';
	tablehtml += '<td style="">'+trobj.createtime+'</td>';
	tablehtml += '<td style="">'+iszlStr+'</td>';
	tablehtml += "</tr>";
	return tablehtml;
}


// 查询操作
$('#query').on('click', function(){
	loadDataList(); 
});

// 清空操作
$('#clean').on('click', function(){
	$(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected");//核心
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
});


</script>
</html>
