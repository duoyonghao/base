<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}
   String usercode = request.getParameter("usercode");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8" />
    <title>新增亲友关系</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css" />

	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css" />
</head>
<body>
	<div class="addRelation-content">
		<div class="searchWrap">
		  <form class="form-horizontal"  id="form">
					<div class="addRelation-btn">
						<a href="javscript:;"class="addRelation">保存</a>
						<a href="javscript:;"class="white-btn">重填</a>
					</div>
					<input type="hidden" class="form-control" name="usercode" id="usercode" value="<%=usercode%>" >																	
					<input type="hidden" class="form-control" name="seqId" id="seqId"   >																	
					<div class="kv relationKv">
						<label>关系人</label>
						<div class="kv-v">
							<input type="hidden" id="participantcode" name="participantcode" >
							<input type="text" id="participant" name="participant" readonly onclick="getuser()">
						</div>
					</div>
					<div class="kv relationKv">
						<label>关	系</label>
						<div class="kv-v">
							<select class="dict" tig="gxr" name="relation" id="relation">
							</select>	
							<a href="javascript:void(0);" class="hc-icon icon20 set-icon"></a>
						</div>
					</div>
					<div class="kv relationKv remarkKv">
						<label>备	注</label>
						<div class="kv-v">
							<input type="text"  name="remark" id="remark" >
						</div>
					</div>
			</form>
		</div>
		<!--表格-->
		<div class="tableBox">
			<table id="table" class="table-striped table-condensed" data-height="250"></table>
		</div>
	</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>

<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var usercode = "<%=usercode %>";
var pageurl = '<%=contextPath%>/KQDS_ParticipantAct/selectNoPage.act?usercode='+usercode;
var $table = $('#table');
 //4、表格初始化
$(function () {
	initDictSelectByClass(); // 亲友关系
	$table.bootstrapTable({
		url:pageurl, 
		dataType: "json",
		singleSelect: false,
		striped: true,
		onLoadSuccess: function(data){  //加载成功时执行
  	    }, 
		      columns: [
		                {title: '病人编号',field: 'usercode',align: 'center',valign: 'middle',visible:false,switchable:false},
						{title: '关系人',field: 'participant',align: 'center',valign: 'middle'},
						{title: '关系',field: 'relation',align: 'center',valign: 'middle',
							 formatter:function(value,row,index){  
			                	  if(row.relation!="" && row.relation!=null){
			                		  var pjid = "table"+index+"relation";
			  		 	        		getValueDict(pjid,row.relation);
			                		    return "<span id='"+pjid+"' class='name'></span>";
			                	  }
		                	  } 
						},
						{title: '备注',field: 'remark',align: 'center',valign: 'middle'},
		                {title: 'seqId', field: 'seqId',align: 'center',valign: 'middle',visible:false,switchable:false}, 
		                {title: '操作',field: 'pkcode',align: 'center',
		                  formatter:function(value,row,index){  
		                      var d = '<a href="javascript:void(0);" onclick="del(\''+ row.seqId +'\')"><span style="color:red">删除</span></a> '; 
		                      return d;  
		                } 
		              }
		          ]
		  });
		
	
});
    $('.addRelation').click(function(){
    	var participantcode = $("#participantcode").val();
    	if(participantcode == null || participantcode == ""){
    		layer.alert('请选择关系人' );
    		return false;
    	}
    	var rea = $("#relation").val();
    	if(rea == null || rea == ""){
    		layer.alert('请选择关系' );
    		return false;
    	}
		 var param = $('#form').serialize();
		 var url = '<%=contextPath%>/KQDS_ParticipantAct/insertOrUpdate.act?'+param;
         $.axseSubmit(url,null, 
				  function(){},
	              function(r){
				        if(r.retState=="0"){
				        	 layer.alert('保存成功' );
				        	  refresh();
				        }    
	              },
	              function(){
	            	  layer.alert('保存失败' );
	          	  }
	        );
    });
    $('.white-btn').click(function(){
    	$('#participant').val("");
    	$('#relation').val("");
    	$('#remark').val("");
    });

function del(id){
	  layer.confirm('确定删除？', {
	    btn: ['删除','放弃'] //按钮
	  }, function(){
		  var url = '<%=contextPath%>/KQDS_ParticipantAct/deleteObj.act?seqId='+id;
		  msg=layer.msg('加载中', {icon: 16});
		  $.axse(url,null, 
	              function(data){
					  if(data.retState=="0"){
		        		  layer.alert('删除成功' );
		        		  refresh();
		        	  }
	              },
	              function(){
	            	  layer.alert('删除失败！' );
	          	  }
	        );
	  });
 }
function refresh(){
	 $table.bootstrapTable('refresh',{'url':pageurl}); 
}
$('#table').on('click-row.bs.table', function (e, row, element){
	 $('.success').removeClass('success');//去除之前选中的行的，选中样式
	 $(element).addClass('success');//添加当前选中的 success样式用于区别
	 
	 var index = $('#table').find('tr.success').data('index');//获得选中的行
	 onclickrowOobj =  $('#table').bootstrapTable('getData')[index];
	 $('#participant').val(onclickrowOobj.participant);
 	 $('#relation').val(onclickrowOobj.relation);
 	 $('#remark').val(onclickrowOobj.remark);
 	 $('#seqId').val(onclickrowOobj.seqId);
}); 
</script>
</html>
