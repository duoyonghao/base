<!-- 领料页面，在lltj.jsp页面中，点击领料按钮，跳转到此页面进行领料操作 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	
	String treatdetailno = request.getParameter("treatdetailno");
	String username = request.getParameter("username");
	String usercode = request.getParameter("usercode");
	String itemno = request.getParameter("itemno");
	String unit = request.getParameter("unit");
	// 领料编号
	if(treatdetailno == null){
		treatdetailno = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>领料选取</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/selectGoodsType.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script> 
<style>
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
#table input[type="text"]{
	height:18px;
}
#table tbody tr.active{/* 选中行时 为绿色  */
	background:#A9ECC7;
}
.formBox input[type="text"],.formBox select{
	cursor:pointer;
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
						<label>仓库</label>
						<div class="kv-v">
							<select  name="house" id="house"></select>
						</div>
					</div>
				     <div class="kv">
				            <label>商品类别</label>
				            <div class="kv-v">
				                <input type="hidden" name="goodstype" id="goodstype" />
				           	    <input type="text" name="goodstypename" id="goodstypename" readonly="readonly" onclick="selectGoodsTypezTreeInit();">
				            </div>
				     </div>
					<div class="kv" >
						<label>商品名称</label>
						<div class="kv-v">
							  <input type="text" name="goodsname" id="goodsname"  class="form-control" />
						</div>
					</div>
                </div>
            </div>
        </div> 
    <div class="tableHd">货品清单</div>
    <div class="tableBox" id="divkdxm" style="width: 100%;position:relative;">
    	<div class="tableHeader" style="position:absolute; top:0;left:0;height:30.4px;overflow:hidden;">
	        <table  id="hiddentable" class="table-striped table-condensed table-bordered" data-height="250" style="width: 100%;text-align: center;">
				<thead style="background: #00A6C0;color:white; ">
					<tr>
						<th style="text-align: center; vertical-align: middle;width:10%;">所属仓库</th>
						<th style="text-align: center; vertical-align: middle;width:10%;">类别</th>
						<th style="text-align: center; vertical-align: middle;width:10%;">商品编号</th>
						<th style="text-align: center; vertical-align: middle;width:15%;height:30px;">商品名称</th>
						<th style="text-align: center; vertical-align: middle;width:10%;">商品规格</th>
						<th style="text-align: center; vertical-align: middle;width:5%;">单位</th>
						<th style="text-align: center; vertical-align: middle;width:5%;">数量</th>
						<th style="text-align: center; vertical-align: middle;width:5%;">领料</th>
					</tr>
				</thead>
				<tbody style="background-color: #F0FFFF;text-align: center;"></tbody>
			</table>
		</div>
		
		<div class="tableBody">
			<table id="table" class="table-striped table-condensed table-bordered" data-height="250" style="width: 100%;text-align: center;">
				<thead style="background: #00A6C0;color:white; ">
					<tr>
						<th style="text-align: center; vertical-align: middle;width:10%;">所属仓库</th>
						<th style="text-align: center; vertical-align: middle;width:10%;">类别</th>
						<th style="text-align: center; vertical-align: middle;width:10%;">商品编号</th>
						<th style="text-align: center; vertical-align: middle;width:15%;height:30px;">商品名称</th>
						<th style="text-align: center; vertical-align: middle;width:10%;">商品规格</th>
						<th style="text-align: center; vertical-align: middle;width:5%;">单位</th>
						<th style="text-align: center; vertical-align: middle;width:5%;">数量</th>
						<th style="text-align: center; vertical-align: middle;width:5%;">领料</th>
					</tr>
				</thead>
				<tbody style="background-color: #F0FFFF;text-align: center;"></tbody>
			</table>
		</div>
		
    </div>
</div>
<div id="menuContent" class="menuContent" style="background:#DDDDDD;display:none; position: absolute;">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:160px;"></ul>
</div>	
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl = contextPath + '/KQDS_Ck_Goods_DetailAct/selectList.act';

var ptreatdetailno = '<%=treatdetailno%>'; // 获取从父页面传过来的值
var pusername = '<%=username%>';
var pusercode = '<%=usercode%>';
var pitemno = '<%=itemno%>';
var punit = '<%=unit%>';

$(function(){
	if(ptreatdetailno == ''){
		layer.alert('参数错误，未获取到ptreatdetailno的值' );
		return;
	}
	getHouseSelect("house");
	loadDataList();
	$("#table").on("click","tbody tr",function(){
		$(this).addClass("active").siblings().removeClass();
	});
});
$('#query').on('click', function(){
	loadDataList(); 
});
$('#clean').on('click', function(){
	$(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected");//核心
});

function loadDataList(){
	// 先清空，再查询
	$("#table").find('tbody').html("");
	$("#hiddentable").find('tbody').html("");
	// 查询条件
    var querydata = {  
   		goodstype:$('#goodstype').val(),
   		sshouse:$('#house').val(),
   		goodsname:$('#goodsname').val()
    };
	
	$.axseY(pageurl,querydata, 
		function(data){
			var htmlStr = "";
			if(data != null  && data.length > 0){
				for(var i = 0; i < data.length; i ++){
					var trobj = data[i];
					htmlStr += initTrHtml(trobj);
				}
			}
			$("#table").find('tbody').append(htmlStr);
			$("#hiddentable").find('tbody').append(htmlStr);
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

function initTrHtml(trobj){
	var tablehtml = "";
	var housename = "-";
	if(trobj.sshousename !=null){
		housename = trobj.sshousename;
	}
	tablehtml += "<tr style=''>";
	tablehtml += '<td style="">' + housename+ '</td>';
	tablehtml += '<td style="">' + trobj.goodstypename+ '</td>';
	tablehtml += '<td style="text-align:left;">' + trobj.goodscode+ '</td>';
	tablehtml += '<td style="width:50px;text-align:left;">' + trobj.goodsname+ '</td>';
	tablehtml += '<td style="text-align:left;">' + trobj.goodsnorms+ '</td>';
	tablehtml += '<td style="">' + trobj.goodsunit+ '</td>';
	tablehtml += '<td style=""><input type="text" style="width:100px;text-align:center;" id=\''+trobj.seqId+'\' value="1" /></td>';
	tablehtml += '<td style=""><a href="javascript:void(0);" onclick="selectGood(\''+trobj.seqId+'\',\''+trobj.goodsunit+'\')">确定</a></td>';
	tablehtml += "</tr>";
	return tablehtml;
}


function selectGood(id,goodsunit){
	var num = $("#"+id).val();
	
	var list=[];
	var param ={};
	param.lltjid=ptreatdetailno;
	param.usercode=pusercode;
	param.username=pusername;
	param.itemno=pitemno;
	param.unit=punit;
	param.nums=1; // 开单项中单项数量超过1的都拆分成1，所以这个值是固定值
	param.goods=id; // 商品表的主键
	param.goodsnums=num;
	param.goodsunit=goodsunit;
	param.iszl=0; // 默认未治疗   1已治疗
	list.push(param);
	var data = JSON.stringify(list); 
	var a={
		data:data
	};
	var url = '<%=contextPath%>/KQDS_LLTJ_DetailAct/insertOrUpdate.act';
    $.axseSubmit(url,a,  // 异步调用
  		function(){
    	
	  	},
    	function(r){
	        if(r.retState=="0"){
	        	// 重新加载数据，刷新父页面列表
	        	parent.loadDataList();
	        	
	        	layer.alert('领料成功', {
			  	      
		  	    },function(){
		  	    	parent.layer.close(frameindex); //再执行关闭 */
		  	    });
	        	
	        	
	        }else{
	        	layer.alert('领料失败' );
	        }
        },
        function(){
      	    layer.alert('领料失败' );
		}
    ); 
}

</script>
</html>
