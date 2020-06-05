<!--wl整理-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String goodsuuid=(String)request.getAttribute("goodsuuid");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>当前库存</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!-- 跳页 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/select2.css" />
<style type="text/css">
.ph{
	border: 1px solid #ddd !important;
    font-size: 13px !important;
    line-height: 24px !important;
    border-radius: 5px !important;
    padding-left: 5px !important;
    width: 120px !important;
    box-shadow: inset 0 0 0 1000px #f7f7f7!important;
}
.phBtn{
	outline: none;
    font-size: 12px;
    line-height: 22px;
    padding: 0px 10px;
    background-color: #00A6C0;
    color: white;
    border: 0px;
    border-radius: 5px;
}
</style>
</head>
<body>
	<div id="container">
		<div class="tableBox" id="divkdxm">
			<table id="table" class="table-striped table-condensed table-bordered" style="table-layout: fixed;border:1px solid blue;"></table>
		</div>
	</div>	
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/selectGoodsType.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<!-- 跳页 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl = contextPath + '/KQDS_Ck_Goods_In_DetailAct/selectAllGoodDetail.act';
var goodsuuid="<%=goodsuuid%>";
$(function() {
	/* 初始化表格 */
    OrderDetail();
	/* 页面重置时 计算表格高度 */
	$(window).resize(function(){
		setHeight();
	});
});
/* 计算表格高度*/
function setHeight(){
	$(".fixed-table-container").outerHeight($(window).height()-120-$("#totalTable").outerHeight());
}

function queryParams(params) {
    var temp = { 
    	limit: params.limit,
        offset:params.offset,
        pageIndex : params.offset/params.limit + 1,
		goodsuuid:goodsuuid
    };
    return temp;
}


function OrderDetail() {

    $("#divkdxm").attr("data-height", $(window).height() - 200);
    
	/*wl----首次加载时 计算table高度  */
	var tableHeight=0;/* 计算table需要的高度 */
	/* 根据当前页面 计算出table需要的高度 */
	tableHeight=$(window).height()-120-$("#totalTable").outerHeight();
	/* 框架要使用改table */
	$("#divkdxm").html("<table id='table' class='table-striped table-condensed table-bordered' data-height='"+tableHeight+"'></table>");
	/*wl----首次加载时 计算table高度————————————结束  */
	
    $("#table").bootstrapTable({
        url: pageurl,
        /* queryParams: queryParams, */
        dataType: "json",
        contentType : "application/x-www-form-urlencoded",   //必须有
        pagination: true,//是否显示分页（*）
        pageSize: 15,
        pageList : [10, 15, 20, 25,50],//可以选择每页大小
      //在表格底部显示分页工具栏 
        singleSelect: true,
        paginationShowPageGo: true,
        queryParamsType:'limit',
        queryParams:queryParams,
        cache: false,
        striped: true,
        sidePagination: "server",
        onLoadSuccess: function(data) { //加载成功时执行
        },
        columns: [
        	{
            	title : '序号',
            	align: "center",
            	width: 40,
            	formatter: function (value, row, index) {
            		var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                	var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                	return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
            	}
            },
	       {
	          title: '入库编号',
	          field: 'incode',
	          align: 'center',
	          formatter: function(value, row, index) {
	                html = '<span id="incode'+index+'">' + value + '</span>';
	                return html;
	            }
	      },
	      {
	          title: '入库时间',
	          field: 'rktime',
	          align: 'center',
	          formatter: function(value, row, index) {
	                html = '<span>' + value + '</span>';
	                return html;
	          }
		 },
		 {
	          title: '进货时间',
	          field: 'stocktime',
	          align: 'center',
	          formatter: function(value, row, index) {
	                html = '<span>' + value + '</span>';
	                return html;
	          }
		},
		{
	          title: '入库数量',
	          field: 'innum',
	          align: 'center',
	          formatter: function(value, row, index) {
	                html = '<span id="innum'+index+'">' + value + '</span>';
	                return html;
	          }
		},
		{
	          title: '单价',
	          field: 'inprice',
	          align: 'center',
	          formatter: function(value, row, index) {
	                html = '<span>' + value + '</span>';
	                return html;
	          }
		},
		{
	          title: '有效期',
	          field: 'yxdate',
	          align: 'center',
	          formatter: function(value, row, index) {
	                html = '<input type="text" value="'+value+'" class="unitsDate ph" id="yxdate'+index+'" disabled="disabled"/>';
	                return html;
	          }
		},
		{
            title: '批号',
            field: 'ph',
            align: 'center',
            
            formatter: function(value, row, index) {
           		html ='<input type="text" class="ph" id="ph'+index+'"  value="'+value+'" disabled="disabled"/>';
                   return html;
            }
        },
		{
	          title: '批号数量',
	          field: 'phnum',
	          align: 'center',
	          formatter: function(value, row, index) {
	        		//html = '<span class="remark" title="' + value + '">' + value + '</span>';
          			html ='<input type="text" class="ph" id="phnum'+index+'"  value="'+value+'" disabled="disabled"/>';
          			html+='<button id="phnumUpdate'+index+'" class="phBtn" onclick="phnumUpdateSave(0,'+index+');">修改</button>';
          			html+='<button id="phnumSave'+index+'" class="phBtn" onclick="phnumUpdateSave(1,'+index+');" style="display:none;">保存</button>';
                  	return html;
	          }
		},
		{
	          title: '',
	          field: 'goodsuuid',
	          align: 'center',
	          visible: false,
	          formatter: function(value, row, index) {
	                html = '<span id="goodsuuid'+index+'">' + value + '</span>';
	                return html;
	          }
		},
	   ]
    });
}
function phnumUpdateSave(i,index){
	if(i=="0"){ //修改
		$("#phnum"+index).removeAttr("disabled");
		$("#ph"+index).removeAttr("disabled");
		$("#yxdate"+index).removeAttr("disabled");
	    $(".unitsDate").datetimepicker({
			language:  'zh-CN',  
			minView:2,
		    autoclose : true,
			format: 'yyyy-mm-dd',
			pickerPosition: "bottom-right"
		});
		$("#phnumUpdate"+index).css("display","none");
		$("#phnumSave"+index).css("display","inline-block");
		ph1=$("#ph"+index).val();
		phnum1=$("#phnum"+index).val();
		yxdate1=$("#yxdate"+index).val();
	}else{ //保存
		var ph=$("#ph"+index).val();
		var phnum=$("#phnum"+index).val();
		var innum=$("#innum"+index).val();
		var yxdate=$("#yxdate"+index).val();
		if(ph==""){
			layer.alert('批号不能为空！');
			$("#ph"+index).val("");
			return false;
		}
		if(yxdate==""){
			layer.alert('请选择有效期！');
			$("#yxdate"+index).val("");
			return false;
		}
		if(phnum==""){
			layer.alert('请填写批号数量！');
			$("#phnum"+index).val("");
			return false;
		}
		if(Number(phnum)==0){
			layer.alert('数量格式不正确！');
			$("#phnum"+index).val("");
			return false;
		}
		if(judgeSign(phnum)==false){
			layer.alert('数量格式不正确！' );
			$("#phnum"+index).val("");
			return false;
		}
		if(Number(phnum)>Number(innum)){
			layer.alert('批号数量多于入库数量，请查看后填写！' );
			$("#phnum"+index).val("");
			return false;
		}
		var url = contextPath + '/KQDS_Ck_Goods_In_DetailAct/updateGoodsPh.act';  
		var params = { 
    		goodsuuid : $("#goodsuuid"+index).html(),
    		phnum:$("#phnum"+index).val(),
    		ph:$("#ph"+index).val(),
    		incode:$("#incode"+index).html(),
    		yxdate:$("#yxdate"+index).val()
		};
		$.axseSubmit(url, params, function() {}, function(r) {
			layer.alert(r.retMsrg, {
				 end: function() {
				 
				 }
			});
			}, function(r) {
			layer.alert(r.retMsrg, {
			     end: function() {
			            
			         }
			     });
			});
		} 

}
</script>
</html>
