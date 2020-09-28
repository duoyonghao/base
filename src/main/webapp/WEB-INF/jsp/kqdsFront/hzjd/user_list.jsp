<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	String typechoose = request.getParameter("typechoose"); // typechoose值为 1时， 表示不需要权限过滤 ，可查所有人
	if (typechoose == null) {
		typechoose = "0";
	}

	String operFlag = request.getParameter("operFlag"); // 用于判断是否是预约中心的选择患者页面，如果是，则后台的查询用 预约可见人员，而不是可见人员
	if (operFlag == null) {
		operFlag = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8" />
    <title>查询患者</title><!-- 查询患者，以列表方式展现，供选择 -->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css?v=${version}" />
	<!-- 数据表中数据的样式 -->
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
</head>
<style type="text/css">
/*工作量表格 ，单独写*/
.kqds_table{
	width:90%;
	align:center;
	margin-left: auto;
	margin-right: auto;
}
	
.kqds_table  td { 
	color: #666;
	padding: 3px 2px 5px 2px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	font-weight: normal;
	line-height: 28px;
}
	
.kqds_table  select { 
	height: 28px;
	width:100px;
	border: solid 1px #e5e5e5;
	border-radius: 3px;
}

input[type=text],.kv .kv-v input[type=text]{padding:0 10px;width:120px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}
.searchWrap .btnBar > .aBtn {
    width: 90px;
}
.register-bottom{
	margin-top:10px;
}
</style>
<body>
	<div class="">
		<div class="register-bottom" style="float: left;">
			<table class="kqds_table">
	    		<tr>
	    			<td style="width: 85px;">
						<span>门诊</span>
		    			<select id="organization" name="organization" style="width: auto;"></select>
					</td>
	    			<td>姓名：</td>
	    			<td style="text-align:left;">
	    				<input type="text" id="username"  placeholder="姓名"/> 
	    			</td>
	    			<td>手机号：</td>
	    			<td style="text-align:left;">
	    				<input type="text" id="sjhm"  placeholder="手机号"/> 
	    			</td>
	    			<td style="width:160px;">
                  			<a href="javascript:void(0);" style="margin-left:10px;" class="kqdsSearchBtn" id="searchUser">查 询</a>
	    			</td>
	    		</tr>
	    	</table>
		</div>
	    <!--表格-->
	    <!-- <div class="tableBox" style="display:none;">
	    	<div class="tableFather" style="border:1px solid blue;">
	    		<table id="table" class="table-striped table-condensed table-bordered" data-height="170">
	        	</table>
	    	</div>
           	
	        
	        
	        <div id="notMsg" style='color:red;width:100%;text-align:center;margin:0 auto;margin-top:10px;font-size:18px;'>
	        
	        </div>
        </div> -->
		<div class="tableBox">
			<div class="tableFather">
	    		<table id="table" class="table-striped table-condensed table-bordered">
	        	</table>
		    </div>
		</div>
	</div>
</body>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>

<script type="text/javascript">
var contextPath = '<%=contextPath%>';
//为 1 表示不需要权限过滤  查所有人
var typechoose = '<%=typechoose%>';

//初始化表头，返回空的数据
var nullUrl = '<%=contextPath%>/UtilAct/intTableHeader.act';

var operFlag = '<%=operFlag%>';

var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
$(function() {
 queryUser(nullUrl);
 initHosSelectListNoCheck('organization','<%=ChainUtil.getCurrentOrganization(request)%>'); // 加载门诊列表
});

$('#searchUser').on('click',
function() {
	var username = $('#username').val();
	var sjhm =  $('#sjhm').val();
	if(username =="" && sjhm ==""){
		layer.alert('请输入精确查询条件', {
	          
	    });
		return false;
	}
	if(sjhm != "" && sjhm.length < 8){
		layer.alert('请输入精确查询条件，手机号码长度不能小于8', {
	          
	    });
		return false;
	}
	if(username != "" && username.length < 2){
		layer.alert('请输入精确查询条件，姓名长度不能小于2', {
	          
	    });
		return false;
	}
 var pageurl = '<%=contextPath%>/KQDS_UserDocumentAct/selectWithNopage.act?typechoose=' + typechoose + '&sjhm=' + sjhm+ '&username=' + username+ '&operFlag=' + operFlag+ '&organization=' + $("#organization").val();
 $('#table').bootstrapTable('refresh', {
     'url': pageurl
 });

});

/**
* 请求后台，获取数据，初始化表格
*/
function getTableHeight(){
	var tableHeight=$(window).height()-$(".register-bottom").outerHeight()-20;
	return tableHeight;
}
function queryUser(requrl) {

 /* 根据当前页面 计算出table需要的高度 */
	/*wl----首次加载时 计算table高 */
	 //var tableHeight=getTableHeight();/* 计算table需要的高度 */
	//$(".tableFather").html("<table id='table' class='table-striped table-condensed table-bordered' data-height='"+tableHeight+"'></table>");
	//$(".tableFather").html('<table id="table" class="table-striped table-condensed table-bordered" data-height="170"></table>');
	/*wl----首次加载时 计算table高度————————————结束  */
 $('#table').bootstrapTable({
     url: requrl,
     dataType: "json",
     singleSelect: false,
     onLoadSuccess: function(data) { //加载成功时执行
         $('#notMsg').html("");
         if (data.length == 0 && requrl != nullUrl) {
             $('#notMsg').html("查询结果为空");
         }
     },
     onRefresh: function(params) {
         requrl = params.url; // 重新赋值   这个方法执行后，加载数据，然后执行onLoadSuccess方法
     },
     striped: true,
     columns: [{
         title: 'seqId',
         field: 'seqId',
         align: 'center',
//          valign: 'middle',
         visible: false,
         switchable: false,
         formatter:function(value){
         	return "<span class='name'>"+value+"</span>" 
         }
     },
     {
         title: '患者编号',
         field: 'usercode',
         align: 'center',
//          valign: 'middle',
         formatter:function(value){
             return "<span style='width:90px;' class='name'>"+value+"</span>" 
         }
     },
     {
         title: '姓名',
         field: 'username',
         align: 'center',
//          valign: 'middle',
         sortable: true,
         formatter:function(value){
        	return "<span class='name'>"+value+"</span>" 
         }
     },
     {
         title: '年龄',
         field: 'age',
         align: 'center',
         /* valign: 'middle', */
         sortable: true,
         formatter:function(value){
        	return "<span>"+value+"</span>" 
         }
     },
     /* {
         title: '身份证号',
         field: 'idcardno',
         align: 'center',
         valign: 'middle',
         sortable: true,
         formatter:function(value){
        	return "<span>"+value+"</span>" ;
         }
     }, */
     {
         title: '手机号码1',
         field: 'phonenumber1',
         align: 'center',
         sortale: true,
         formatter: function(value, row, index) {
             if (canlookphone == 0) {
                 return "<span>"+value+"</span>" ;
             } else {
                 return "<span></span>";
             }
         }
     },
     {
         title: '手机号码2',
         field: 'phonenumber2',
         align: 'center',
         sortale: true,
         formatter: function(value, row, index) {
             if (canlookphone == 0) {
                 return "<span>"+value+"</span>" ;
             } else {
                 return "<span></span>";
             }
         }
     },
	 {
		 title: 'vip',
		 field: 'vip',
		 align: 'center',
		 sortale: true,
		 formatter: function(value, row, index) {
			 if (value) {
			     if(value ==1){
                     return '<span class="label label-warning">vip</span>' ;
				 }else{
                     return "<span>-</span>" ;
				 }

			 } else {
				 return "<span>-</span>";
			 }
		 }
	 },
     {
         title: '缴费金额',
         field: 'totalcost',
         align: 'center',
         sortable: true,
         formatter:function(value, row, index){
        	 if (value == null || value == "" || value == "null" || value == "undefined") {
        		 value = "";
        	 } else {
	        	return "<span>"+value+"</span>" ;
        	 }
         }
     },
     {
         title: '种植医生',
         field: 'doctorname',
         align: 'center',
         sortable: true,
         formatter:function(value, row, index){
        	 if (value == null || value == "" || value == "null" || value == "undefined") {
        		 value = "";
        	 } else {
	        	return "<span>"+value+"</span>" ;
        	 }
         }
     },
     {
         title: '修复医生',
         field: 'idcardno',
         align: 'center',
         sortable: true,
         formatter:function(value, row, index){
        	 if (value == null || value == "" || value == "null" || value == "undefined") {
        		 value = "";
        	 } else {
	        	return "<span>"+value+"</span>" ;
        	 }
         }
     },
     {
         title: '咨询',
         field: 'askpersonname',
         align: 'center',
         sortable: true,
         formatter:function(value, row, index){
        	 if (value == null || value == "" || value == "null" || value == "undefined") {
        		 value = "";
        	 } else {
	        	return "<span>"+value+"</span>" ;
        	 }
         }
     }
     ]

 }).on('click-row.bs.table',
 function(e, row, element) {
     $('.success').removeClass('success'); //去除之前选中的行的，选中样式
     $(element).addClass('success'); //添加当前选中的 success样式用于区别
     
     if(parent.setSelectUserValue){
//     	 parent.setSelectUserValue(row.usercode,row.username);
    	 parent.setSelectUserValue2(row.usercode,row.username,row.sex,row.age,row.phonenumber1);
     }else{
    	 //给父页面属性赋值 
         parent.usercodechild = row.usercode;
         parent.usernamechild = row.username;
     }
    
     //关闭layer
     parent.layer.close(frameindex);
 });
}
/* ###  此段代码留在这儿作为参考，bootstrap table 可编辑单元
editable: {
	type: 'text',
	title: 'Item Price',
	validate: function (value) {
		value = $.trim(value);
		if (!value) {
			return 'This field is required';
		}
		if (!/^\$/.test(value)) {
			return 'This field needs to start width $.';
		}
		var data = $table.bootstrapTable('getData'),
		index = $(this).parents('tr').data('index');
		return '';
	}
}, ## */

		              
</script>

</html>