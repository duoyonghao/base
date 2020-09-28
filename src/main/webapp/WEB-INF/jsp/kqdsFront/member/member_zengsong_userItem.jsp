<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	String usercode = request.getParameter("usercode");
	String regno = request.getParameter("regno");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>咨询中心-使用已赠送项目</title>
<!-- jd_index.jsp页面进入 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>

<!-- 公用的css 按钮太小 字体会换行并撑出去 单独写个大点的 -->
<style type="text/css">
.aBtnBig{display:inline-block;padding: 0 15px;height: 28px;border: solid 1px #0e7cc9;color: #0e7cc9;border-radius: 15px;line-height: 28px;text-align: center;}
.aBtnBig:hover{color:#fff;background: #0e7cc9;}
.listWrap{margin-bottom:0;}
.listBd input{
	cursor:pointer;
}
input[type="number"]{
	padding-left:15px;
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
.fixed-table-container thead th .sortable{
	padding-right:8px;
}
.fixed-table-header table tr th div.th-inner{
	color:#333 !important;	
	/* font-size:13px !important; */
	background:#fff !important;
	font-family: "SimSun";
	border-right:1px solid #ddd;
	border-bottom:1px solid #ddd;
}
table tr th div.th-inner{
	background:#fff !important;
	color:#333 !important;
}
.fixed-table-header{
	background:none;
}
.bootstrap-table .fixed-table-body thead{
	background:transparent !important;
}
</style>
</head>
<body>
	<div class="listWrap">
		<!-- <div class="titleDiv">
       		<div class="title">
       			已赠送项目列表
       		</div>
       	</div> -->
        <!-- <div class="listHd"><span class="hc-icon icon20 home-icon"></span>已赠送项目列表</div> -->
        <div class="listBd">
            <div class="tableBox">
                <table id="table" class="table-striped table-condensed table-bordered" data-height="332"></table>
            </div>
           <div class="kv" style="float: left;padding-top:5px">
				<label>医生：</label>
				<div class="kv-v">
					<input type="hidden" name="doctor" id="doctor"  class="form-control" />
					<input type="text"   id="doctorDesc" name="doctorDesc" placeholder="医生" readonly style="width: 80px;" onClick="javascript:single_select_user(['doctor', 'doctorDesc']);"  ></input>	
				</div>
			</div>
			<div class="kv" style="padding-top:5px">
				<label>护士 ：</label>
				<div class="kv-v">
					<input type="hidden" name="nurse" id="nurse"  class="form-control" />
					<input type="text"   id="nurseDesc" name="nurseDesc" placeholder="护士" readonly style="width: 80px;"onClick="javascript:single_select_user(['nurse', 'nurseDesc']);"  ></input>	
				</div>
			</div>
        </div>
        <div class="buttonBar" style="margin-bottom:6px;text-align: center;">
        	
			<a href="javascript:void(0);" class="kqdsSearchBtn" id="qdsy">确定使用</a>
     		<a href="javascript:void(0);" class="kqdsCommonBtn" id="close">关闭</a>
        </div>
    </div>
	<!-- 查询条件 -->
<!--     <div class="searchWrap"> -->
<!--         <div class="btnBar"> -->
        	
<!--         </div> -->
<!--     </div> -->
</body>
<script type="text/javascript">
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var contextPath = '<%=contextPath%>';
var $table = $('#table');
var pageurl = contextPath + '/KQDS_GiveItem_GiveRecordAct/getGiveRecordByMemberno.act';
var usercode = '<%=usercode%>';
var regno = '<%=regno%>';
$(function(){
	pageurl = pageurl + '?usercode=' + usercode + '&memberno=';
	getitems();//加载已赠送项目列表
});

//刷新表格
function refresh(){
	$("#table").bootstrapTable('refresh',{'url':pageurl}); 
}

//加载表格
function getitems(){
	$("#table").bootstrapTable({
		url:pageurl, 
		dataType: "json",
		singleSelect: false,
		striped: true,
		onLoadSuccess: function(data){ //加载成功时执行
			setHeight();
		},
		columns: [
			{
				title: 'seqId',
				field: 'seqId',
				align: 'center',
				valign: 'middle',
				visible: false,
				switchable: false
			}, 
			{
				title: '项目名称',
				field: 'itemname',
				align: 'center',
				valign: 'middle',
				sortable: true,
			    formatter: function(value, row, index) {
			        return '<span style="text-align:left;display:block;" title="'+value+'">'+value+'</span>';
			    }
			},
			{
				title: '单位',
				field: 'unit',
				align: 'center',
				valign: 'middle',
				sortable: true
			}, 
			{
				title: '赠送数量',
				field: 'zsnum',
				align: 'center',
				valign: 'middle',
				sortable: true,
			    formatter: function(value, row, index) {
			    	return '<span id="zsnum'+index+'">'+ value +'</span>';
			    }
			},
			{
				title: '已使用数量',
				field: 'usenum',
				align: 'center',
				valign: 'middle',
				sortable: true,
			    formatter: function(value, row, index) {
			        return '<span id="synum'+index+'">'+ value +'</span>';
			    }
			},
			{
				title: '本次使用',
				field: 'num',
				align: 'center',
				valign: 'middle',
				sortable: true,
			    formatter: function(value, row, index) {
			        return '<input type="number" min="0" style="width:70px; text-align:center;" onfocus="this.select()" onchange="checknum(\'' + index + '\',\'' + row.zsnum + '\',\'' + row.usenum + '\')" id="num' + index + '" value=0>';
			    }
			},
			{
				title: '操作人',
				field: 'createuser',
				align: 'center',
				valign: 'middle',
				sortable: true,
				formatter: function(value, row, index) {
	                if (value != "" && value != null) {
	                    return '<span class="name" >' + value + '</span>';
	                }
	            }
   			},
   			{title: '赠送时间',field: 'createtime',align: 'center',valign: 'middle',sortable: true,
                formatter:function(value,row,index){  
					return '<span class="time" title="'+value+'">'+value+'</span>';
          	  	}
   			},
		]
	});
}
function setHeight(){
	$("#table").bootstrapTable('resetView',
		{
			height:$(window).outerHeight()-$(".titleDiv").outerHeight()-$(".buttonBar").outerHeight()-60
		}	
	);
}
//检查本次使用的次数加上使用次数 是否大于总赠送次数
function checknum(index,zsnum,synum){
	var testz = /^\d+(\.\d+)?$/;
	var num = $("#num" + index).val();
	if(!testz.test(num)){
		layer.alert('格式不正确,请重新填写', {  end:function(){
			$("#num" + index).focus();
		}});
		return false;
	}
	if((Number(num) + Number(synum)) > Number(zsnum)){
		layer.alert('使用次数不足,请重新填写', {  end:function(){
			$("#num" + index).val(0);
			$("#num" + index).focus();
		}});
		return false;
	}
}

//确定使用
$("#qdsy").click(function(){
	var doctor = $("#doctor").val();
	var nurse = $("#nurse").val();
	if(doctor == "" && nurse == ""){
		layer.alert('请选择医生或者护士' );
		return false;
	}
	var allTableData = $("#table").bootstrapTable('getData');//获取表格的所有内容行 
	if(allTableData.length>0){
		//保存
		var list = [];
		for(var i=0;i<allTableData.length;i++){
			var param ={};
			param.seqId=allTableData[i].seqId;
			param.synum=$("#num"+i).val();
			list.push(param);
		}
		var data = JSON.stringify(list);
		var url = contextPath+'/KQDS_GiveItem_GiveRecordAct/useGiveItems.act?params='+encodeURIComponent(data) +'&doctor=' + doctor + '&regno=' + regno+'&nurse='+nurse;
        $.axse(url,null, 
             function(data){
				  if(data.retState=="0"){
	        		  layer.alert('使用成功', {
						    
						  end: function () {
							  refresh();
// 							  layer.closeAll();
 							  parent.layer.close(frameindex); //再执行关闭 
						  }
				      });
	        	  }
             },
             function(){
            	  layer.alert('操作失败！' );
          	 }
        );
	}else{
		
	}
});

//关闭
$("#close").click(function(){
	parent.layer.close(frameindex);
});

//刷新表格
function refresh(){
	 $table.bootstrapTable('refresh',{'url':pageurl}); 
}

</script>
</html>
