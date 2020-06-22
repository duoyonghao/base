<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
    <title>欠费情况</title> <!-- 由 挂号页面   欠费按钮 进入  -->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css" />
	<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
</head>
<body>
    <div class="tableBox">
       <table id="table" class="table-striped table-condensed table-bordered" data-height="300"></table>
    </div>
     <div class="buttonBar"> 
              <table style="width:90%;text-align: center;"> 
          		<tr>
         			<td style="width:12%"><span style="color:#00A6C0;">应收合计:<lable id="ying">0</lable></span></td>
         			<td style="width:12%"><span style="color:#00A6C0;">缴费合计:<lable id="jiao">0</lable></span></td>
         			<td style="width:12%"><span style="color:red;">欠费合计:<lable id="qian">0</lable></span></td>
          		</tr> 
          	</table> 
     </div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/language/zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var usercode= "<%=usercode %>";
var pageurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/selectQfDetail.act';
$(function(){
	getlzjl();
});
function getlzjl(){
	//流转记录
	var url = pageurl+'?usercode='+usercode; 
	    $('#table').bootstrapTable({
	    	url:url, 
			dataType: "json",
			cache: false, 
			striped: true,
			onLoadSuccess: function(data){  //加载成功时执行
				var tableList = $('#table').bootstrapTable('getData');
	     	   	var ying = 0;
	     		var jiao = 0;
	     		var qian = 0;
     	    	for(var i=0;i<tableList.length;i++){
     	    		ying +=Number(tableList[i].shouldmoney);
     	    		jiao +=Number(tableList[i].actualmoney);
     	    		qian +=Number(tableList[i].y2);
     	    	}
 				$("#ying").html(ying.toFixed(2));
     	    	$("#jiao").html(jiao.toFixed(2));
     	   		$("#qian").html(qian.toFixed(2));
			},
			columns: [
			          {title: '开单时间',field: 'createtime',align: 'center',valign: 'middle'},
			          {title: '应收金额',field: 'shouldmoney',align: 'center',valign: 'middle'}, 
			          {title: '缴费金额',field: 'actualmoney',align: 'center',valign: 'middle'}, 
			          {title: '欠费金额',field: 'y2',align: 'center',valign: 'middle',
			        	  formatter:function(value,row,index){  
			                    return '<span style="color:red">'+value+'</span>';  
	                	  } 
			          }
			            ]
			  });
}
</script>

</html>
