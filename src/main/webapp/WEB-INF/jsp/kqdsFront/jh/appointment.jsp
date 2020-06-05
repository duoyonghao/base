<%@ page language="java" pageEncoding="UTF-8" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String usercode=request.getParameter("usercode");
	String regid=request.getParameter("regid");
	String username=request.getParameter("username");
	String sex=request.getParameter("sex");
	String askperson=request.getParameter("askperson");
	String age=request.getParameter("age");
	String index=request.getParameter("index");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>叫号</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/select2.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<style>
/* 搜索框select */
	.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
	       width: 150px;   
	      }  
	.searchSelect>.btn { 
		    width: 150px; 
		 	height:28px; 
		 	border: solid 1px #e5e5e5; 
		}
	.bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
		    color: #999;
		    height: 28px;
		}
	.pull-left {
	    float: left !important;
	    color: #333;
		}
	.btn-group > .btn:first-child:hover {
	    background: white;
	}
	.searchWrap .text {
		    position: static !important; 
		    left: 0px;
		    top: 9px;
		    color: rgb(31, 32, 33);
		}
	.checkbox1 {
		width:20px !important;
	}
	.textareaTr1 {
		height: 80px !important;
	}
	input[type=text], .kv .kv-v input[type=text] {
      width: 115px;
    }
    #table td, #table1 td, #table2 td, #listTable td, #dykdxm td, #chufang td {
      width: 26%;
    }
    
	.tooth_map{
		display:inline-block;
 		width: 143px; 
    	height: 33px;
    	margin-left: 5px;
     	vertical-align: text-top; 
	}
	.tooth_map>li{
		width:34%;
		height:50%;
		float:left;
	}
	.tooth_map>li:FIRST-CHILD{
		border-bottom: 1px solid black;
		border-right: 1px solid black;
	}
	.tooth_map>li:FIRST-CHILD+li{
		border-bottom: 1px solid black;
	}
	.tooth_map>li:FIRST-CHILD+li+li{
		border-right: 1px solid black;
	}
	.tooth_map>li>.tooth_input{
		display:block;
		width:100%;
		height:100%;
		padding:0px;
		border:0px;
		text-align: center;
		color: #00a6c0;
	}
	ul, ol {
    margin-bottom: 0px !important;
	}
	select{
      width: 115px;
    }
</style>
</head>
<body>
<div class="containerAddEdit"> <!-- "containerAddEdit" 添加、修改回访容器的样式 -->
  <div style="border:2px solid #ddd;margin: 28px 10px;border-radius: 10px;padding: 30px 0;position: relative;">
  <span style="position: absolute;top: -14px;left: 19px;font-size: 18px;font-weight: 600;">患者基本信息</span>
	<table id="table1" style="width: 100%;">
		<tbody>
				<tr>
					<td>
						<span class="information" style="font-weight: 600;">姓名</span>
						<input type="text" id="username" name="username" readonly>
					</td>
					<td>
						<span class="information" style="font-weight: 600;">编号</span>
						<input type="text" id="usercode" name="usercode" readonly>
					</td>
					<td>
						<span class="information" style="font-weight: 600;">年龄</span>
						<input type="text" id="age" name="age" readonly>
					</td>
					<td>
						<span class="information" style="font-weight: 600;">性别</span>
						<input type="text" id="sex" name="sex" readonly>
					</td>
				</tr>
		</tbody>
    </table>
	</div>
	<div style="border:2px solid #ddd; margin: 28px 10px;border-radius: 10px;padding: 30px 0;position: relative;">
		 <span style="position: absolute;top: -14px;left: 19px;font-size: 18px;font-weight: 600;">预约操作内容</span>
		<table id="table" style="width: 100%;">
			<tbody>
		</tbody>
    </table>
</div>
	<div style="border:2px solid #ddd; margin: 28px 10px;border-radius: 10px;padding: 30px 0;position: relative;">
		 <span style="position: absolute;top: -14px;left: 19px;font-size: 18px;font-weight: 600;">拍片时间节点</span>
		<table id="table2" style="width: 100%;">
			<tbody>
		 	<tr >
				<td>
					<label><input type="radio" name="surgery" value="初诊" class="checkbox1"/>&nbsp;&nbsp;初诊</label>	 	
				</td>
				<td>
					<label><input type="radio" name="surgery" value="手术" class="checkbox1"/>&nbsp;&nbsp;手术</label>
				</td>
				<td>
					<label><input type="radio" name="surgery" value="术后" class="checkbox1"/>&nbsp;&nbsp;术后</label>
				</td>
				<td>
					<label><input type="radio" name="surgery" value="复查" class="checkbox1"/>&nbsp;&nbsp;复查</label>
				</td>
				<td>
					
				</td>
			</tr>
		</tbody>
    </table>
</div>
<div style="text-align:center;">
	<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="submitu()" id="tijiao">提交</a>
</div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script> 
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var usercode = '<%=usercode%>';
var regid = '<%=regid%>';
var username = '<%=username%>';
var sex = '<%=sex%>';
var age = '<%=age%>';
var askperson = '<%=askperson%>';
var index = '<%=index%>';
var rows=0;
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
	//根据门诊查询放射科项目
	selectItems();
	$("#usercode").val(usercode);
	$("#username").val(username);
	$("#sex").val(sex);
	$("#age").val(age);

});
function cut(){
	if(!$("#orientation").prop('checked')){
		$("#uplefttoothbit").val("");
		$("#uperrighttoothbit").val("");
		$("#leftlowertoothbit").val("");
		$("#lowrighttoothbit").val("");
	}
}
/* 2019/7/16 lutian input文字长度校验方法   obj：元素id  textNum：限制文字长度 */
function TextLengthCheck(obj,textNum){
	var objTextVal=$("#"+obj).val();
	if(!$("#orientation").prop('checked')&&objTextVal.length>0){
		$("#orientation").prop("checked","checked");
	}
	var checkTitleBefore=$("#"+obj).parent(".common_style").find("span").text();//根据父元素的选择器找到标题
	var checkTitle=checkTitleBefore.substring(0,checkTitleBefore.indexOf(":")); // 校验文字长长度的标题
	if(objTextVal.length>textNum){
		$("#"+obj).attr("maxlength",textNum);
		layer.open({
			 title: '提示',
			 content: checkTitle+'文字长度不能超过'+textNum+'字!',
			 end:function(){
				 var inputNewVal=$("#"+obj).val();
				 $("#"+obj).val(inputNewVal.substring(0,textNum)).focus();
			 }
		});
		return;
	}
}
function judge(){
	if($("#joint").val()!=""){
		$("#joint1").prop("checked","checked");
	}else{
		$("#joint1").removeAttr("checked");
	}
}
function selectItems(){
	
	var url = contextPath + '/YZDictAct/getListByParentCode.act';
	$.ajax({
	    type: "POST",
	    url: url,
	    data: {parentCode:'fskxm525'},
	    dataType: "json",
	    success: function(r){
	    	if(r.retState==0){
	    		if(r.list.length<=4){
	    			rows=tdindex = $("#table").find("tbody").find("tr").length;
			    	var tb = document.getElementById("table");
			        var newTr=tb.insertRow(rows);;//添加新行，trIndex就是要添加的位置
			        for(var i = 0; i < r.list.length; i++){
			        	var itemsList = r.list[i].dictName.split(",")
			        	if(itemsList.length==2){
			        		if(itemsList[0]=="小牙片(牙位)"){
			        			newTr.insertCell().innerHTML = '<td><label><input type="checkbox" onclick="cut()" id="orientation" name="items" value="'+itemsList[0]+'" floor="'+itemsList[1]+'" class="checkbox1"/>'+itemsList[0]+'</label><ul class="tooth_map"><li><input id="uplefttoothbit" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text"></li><li><input id="uperrighttoothbit" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text"></li><li><input id="leftlowertoothbit" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text"></li><li><input id="lowrighttoothbit" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text"></li></ul></td>';
			        		}else if(itemsList[0]=="关节片"){
					        	newTr.insertCell().innerHTML = '<td><label><input type="checkbox" id="joint1" name="items" value="'+itemsList[0]+'" floor="'+itemsList[1]+'" class="checkbox1"/>'+itemsList[0]+'</label><select id="joint" name="joint" onchange="judge()"><option value="">请选择</option><option value="左侧">左侧</option><option value="右侧">右侧</option><option value="两侧">两侧</option><option value="腕关节正位">腕关节正位</option></select></td>';
			        		}else{
			        			newTr.insertCell().innerHTML = '<td><label><input type="checkbox" name="items" value="'+itemsList[0]+'" floor="'+itemsList[1]+'" class="checkbox1"/>'+itemsList[0]+'</label></td>';
			        		}
			        	}
	    			} 
			        $("#table").find("tbody").find("tr").each(function(i,obj){
			    		if(i==rows-2){
			    			$(this).addClass("textareaTr textareaTr1");
			    		}
			    	});
	    		}else{
	    			var j=0;
	    			var gather=[];
			        for(var i = 0; i < r.list.length; i++){
			        	j+=1;
			        	gather.push(r.list[i]);
			        	if(j==4){
			        		if(gather.length>0){
			        			rows=tdindex = $("#table").find("tbody").find("tr").length;
					    		var tb = document.getElementById("table");
							    var newTr=tb.insertRow(rows);;//添加新行，trIndex就是要添加的位置
			        			for (var k = 0; k < gather.length; k++) {
			        				var itemsList = gather[k].dictName.split(",")
						        	if(itemsList.length==2){
						        		if(itemsList[0]=="小牙片(牙位)"){
						        			newTr.insertCell().innerHTML = '<td><label><input type="checkbox" onclick="cut()" id="orientation" name="items" value="'+itemsList[0]+'" floor="'+itemsList[1]+'" class="checkbox1"/>'+itemsList[0]+'</label><ul class="tooth_map"><li><input id="uplefttoothbit" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text"></li><li><input id="uperrighttoothbit" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text"></li><li><input id="leftlowertoothbit" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text"></li><li><input id="lowrighttoothbit" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text"></li></ul></td>';
						        		}else if(itemsList[0]=="关节片"){
								        	newTr.insertCell().innerHTML = '<td><label><input type="checkbox" id="joint1" name="items" value="'+itemsList[0]+'" floor="'+itemsList[1]+'" class="checkbox1"/>'+itemsList[0]+'</label><select id="joint" name="joint" onchange="judge()"><option value="">请选择</option><option value="左侧">左侧</option><option value="右侧">右侧</option><option value="两侧">两侧</option><option value="腕关节正位">腕关节正位</option></select></td>';
						        		}else{
						        			newTr.insertCell().innerHTML = '<td><label><input type="checkbox" name="items" value="'+itemsList[0]+'" floor="'+itemsList[1]+'" class="checkbox1"/>'+itemsList[0]+'</label></td>';
						        		}
						        	}
							        $("#table").find("tbody").find("tr").each(function(l,obj){
						    		if(l==rows-2){
						    			$(this).addClass("textareaTr textareaTr1");
						    		}
						    	});
								}
			        		}
			        		j=0;
			        		gather=[];
			        	}
			        	if(i==r.list.length-1&&j<4){
			        		if(gather.length>0){
			        			rows=tdindex = $("#table").find("tbody").find("tr").length;
					    		var tb = document.getElementById("table");
							    var newTr=tb.insertRow(rows);;//添加新行，trIndex就是要添加的位置
			        			for (var k = 0; k < gather.length; k++) {
			        				var itemsList = gather[k].dictName.split(",")
						        	if(itemsList.length==2){
						        		if(itemsList[0]=="小牙片(牙位)"){
						        			newTr.insertCell().innerHTML = '<td><label><input type="checkbox" onclick="cut()" id="orientation" name="items" value="'+itemsList[0]+'" floor="'+itemsList[1]+'" class="checkbox1"/>'+itemsList[0]+'</label><ul class="tooth_map"><li><input id="uplefttoothbit" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text"></li><li><input id="uperrighttoothbit" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text"></li><li><input id="leftlowertoothbit" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text"></li><li><input id="lowrighttoothbit" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text"></li></ul></td>';
						        		}else if(itemsList[0]=="关节片"){
								        	newTr.insertCell().innerHTML = '<td><label><input type="checkbox" id="joint1" name="items" value="'+itemsList[0]+'" floor="'+itemsList[1]+'" class="checkbox1"/>'+itemsList[0]+'</label><select id="joint" name="joint" onchange="judge()"><option value="">请选择</option><option value="左侧">左侧</option><option value="右侧">右侧</option><option value="两侧">两侧</option><option value="腕关节正位">腕关节正位</option></select></td>';
						        		}else{
						        			newTr.insertCell().innerHTML = '<td><label><input type="checkbox" name="items" value="'+itemsList[0]+'" floor="'+itemsList[1]+'" class="checkbox1"/>'+itemsList[0]+'</label></td>';
						        		}
						        	}
							        $("#table").find("tbody").find("tr").each(function(l,obj){
						    		if(l==rows-2){
						    			$(this).addClass("textareaTr textareaTr1");
						    		}
						    	});
								}
			        		}
			        		j=0;
			        		gather=[];
			        	}
	    			} 
	    		}
	    	}
	    }
	}); 
}


//提交
function submitu() {
	var items =[]; 
	var floor=[]; 
		$('input[name="items"]:checked').each(function(){ 
		items.push($(this).val()); 
		floor.push($(this).attr("floor"));
		}); 
	if(items.length==0){
		layer.alert('请选择预约操作内容!');
		return false;
	}
	var surgery=$('input[name="surgery"]:checked').val();
	if(!surgery){
		layer.alert('请选择拍片时间节点!');
		return false;
	}
	items=JSON.stringify(items);
	floor=JSON.stringify(floor);
	
	var joint=$("#joint").val();
	var uplefttoothbit=$("#uplefttoothbit").val();
	var uperrighttoothbit=$("#uperrighttoothbit").val();
	var leftlowertoothbit=$("#leftlowertoothbit").val();
	var lowrighttoothbit=$("#lowrighttoothbit").val();
	if(uplefttoothbit==""&&uperrighttoothbit==""&&leftlowertoothbit==""&&lowrighttoothbit==""){
		$("#orientation").removeAttr("checked");
	}	
	var lock=false;//默认未锁定 
	layer.confirm("是否确定操作？",{
		   btn: ['确认', '取消'],
		   closeBtn:0
	}, function (index) {
		if(!lock){
			lock=true;
			var url = contextPath + '/Kqds_JhAct/insert.act';
			$.ajax({
		        type: "POST",
		        url: url,
		        data: {usercode:usercode,username:username,
		        	age:age,sex:sex,askperson:askperson,
		        	regid:regid,items:items,surgery:surgery,
		        	floor:floor,joint:joint,uplefttoothbit:uplefttoothbit,
		        	uperrighttoothbit:uperrighttoothbit,leftlowertoothbit:leftlowertoothbit,
		        	lowrighttoothbit:lowrighttoothbit},
		        dataType: "json",
		        success: function(r){
		        	if(r.retState == "0"){
		        		layer.alert('排队成功!');
		        		parent.initclick1(); // 调用indexTab的点击事件，使内容刷新
		        		parent.layer.close(frameindex); //再执行关闭
		        	}else{
		        		layer.alert('排队失败!');
		        	}
		        }
			});
		}
	});
}
</script>
</html>