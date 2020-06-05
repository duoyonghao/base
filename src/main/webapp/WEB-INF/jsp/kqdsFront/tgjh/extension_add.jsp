<%@page import="com.kqds.util.sys.chain.ChainUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>添加推广计划</title><!-- 客户管理菜单  点击 添加推广 按钮 进入 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/web_info.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
</head>
<style type="text/css">
/*工作量表格 ，单独写*/
#gongzuol{border:solid 1px #c0c0c0;background: #f5f5f5;}
#gongzuol{margin-bottom: 10px;}
#gongzuol .columnHd{padding:0 15px;border-bottom:solid 1px #0e7ec6;font-size:16px;color: #373737;font-family: "Microsoft YaHei";line-height:36px;}
.kqds_table{
	width:630px;
	text-align:center;
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
}
	
.kqds_table  select { 
	height: 28px;
	width:147px;
	border: solid 1px #e5e5e5;
	border-radius: 3px;
}

input[type=text],.kv .kv-v input[type=text]{padding:0 10px;width:120px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}
	
.kqds_table{
	margin-top:10px;
}	
.kqds_table input[type="text"]{
	width:147px;
}
table.kqds_table .trF{
	height:30px;
}
table.kqds_table .trT{
	height:118px
}
.buttonPosition{
	margin-top:5px;
}
.textPostion{
	margin-top:5px;
}
#visitorDesc{
	height:170px;
}

</style>
<body>
<!--添加回访弹窗-->
<div id="container">
    <div class="infoBd">
    <form class="form-horizontal"  id="form1">
              <div class="box-body">
              <table class="kqds_table">
              			<tr class="trF">
			    			<td style="text-align:right;">所属门诊：</td>
			    			<td style="text-align:left;"> 
		    					<select id="organization" name="organization"></select>
			                </td>
			    		</tr>
			    		<tr class="trF">
			    			<td style="text-align:right;color: red;">推广计划名称：</td>
			    			<td style="text-align:left;"> 
		    					<input type="text" class="form-control" name="extensioninput" id="extensioninput" onkeydown="keydownEvent()">
			                </td>
			    			
			    		<!-- 	<td style="width:5%;text-align:right;">已创建推广计划：</td>
			    			<td style="width:20%;text-align:left;">
								 <select name="extensionsel" id="extensionsel" ></select>
							</td> -->
							<td style="text-align:right;color: red;">计划截止时间：</td>
			    			<td style="text-align:left;"> 
			    				<span class="unitsDate">
		    						<input type="text" class="form-control" name="visittime" id="visittime">
		    					</span>
			                </td>
			    		</tr>
			    		<!-- <tr>
			    			<td style="width:10%;text-align:right;">计划截止时间：</td>
			    			<td style="width:20%;text-align:left;"> 
			    				<span class="unitsDate">
		    						<input type="text" class="form-control" name="visittime" id="visittime">
		    					</span>
			                </td>
			    		</tr> -->
			    		<tr>
			    			<td style="text-align:right;color: red;">计划回访人：</td>
			    			<td style="text-align:left;padding-bottom:2px;" colspan="3">
			    				<input type="hidden" name="visitor" id="visitor" class="form-control"style="width: 70px;" /> 
			    				<textarea style="width: 90%; background:#fff;cursor:pointer;" rows="" cols="" class="form-control" id="visitorDesc"
								name="visitorDesc" readonly
								onClick="javascript:multi_select_userVisual(['visitor', 'visitorDesc']);"></textarea>
							</td>
			    		</tr>
			    		<tr>
			    			<td style="text-align:right;">备注：</td>
			    			<td style="text-align:left;" colspan="3">
			    				<textarea style="width: 90%; background:#fff;cursor:pointer;" rows="" cols="" class="form-control" id="remark"
								name="remark" ></textarea>
							</td>
			    		</tr>
			   </table>
			        <div class="kv-v buttonPosition" style="text-align:center;width:100%">
			            </br>
		                <input type="button" class="kqdsSearchBtn" onclick="submitu()" value="提交"  id="tijiao"/>
		            </div>
		            <div class="kv-v textPostion" style="text-align:center;width:100%">
			            </br>
			            <span style="color: red;">注：计划回访人如果为多个人 ，计划会随机平均分配到每个人</span>
		            </div>
              </div>
      </form>
      </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">

var contextPath = '<%=contextPath%>';
var rows = window.parent.selectedrows;

var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引

$(function () {
	initHosSelectListNoCheck('organization','<%=ChainUtil.getCurrentOrganization(request)%>');
	//时间选择
	$(".unitsDate input").datetimepicker({
		language:  'zh-CN',  
		minView:2,
        autoclose : true,
		format: 'yyyy-mm-dd',
		pickerPosition: "buttom-right"
	});
});

//禁用回车事件
function keydownEvent() {
    var e = window.event || arguments.callee.caller.arguments[0];
    if (e && e.keyCode == 13 ) {
    	e.preventDefault ? e.preventDefault() : e.returnValue = false;
//     	//提交
//     	$("#tijiao").trigger("click");
    }
}

//查询推广计划
function inithffl(extensioninput){
	var flag = false;
	var url =  '<%=contextPath%>/KQDS_ExtensionAct/getExtensoionTypeList.act';
	$.axse(url,null, 
	     function(data){
	 	 	  var list  = data.data;
	 	 	  if(list!=null){
	 	 		  if(list.length>0){
		 			 for(var j = 0 ;j < list.length ;j ++){
		 		         var optionStr = list[j];
		 		         if(optionStr.jhname==extensioninput){
		 		        	flag = true;
		 		        	break;
		 		         }
		 		         
		 		     }	 
		 		  }
	 	 	  }
	     },
	     function(){
	   	    layer.alert('查询出错！' );
	 	 }
	);
	return flag;
}
//提交
function submitu(){
	//验证
	var extensioninput = $("#extensioninput").val();
	//var extensionsel = $("#extensionsel").val();
	var visittime = $("#visittime").val();
	var visitor = $("#visitor").val();
	var remark = $("#remark").val();
	
	/* if(extensioninput != "" && extensioninput != null && extensionsel != "" && extensionsel != null){
		layer.alert('只能选择一种添加推广计划的方式', {  end:function(){
		}});
		return false;
	} */
	if(extensioninput == "" || extensioninput == null) {
		layer.alert('请输入推广计划名称' );
		return false;
	}
	/* if(inithffl(extensioninput)){
		layer.alert('推广计划名称已存在，请重新输入！' );
		return false;
	} */
	if(visittime == "" || visittime == null ){
		layer.alert('请选择计划截止时间', {  end:function(){
		}});
		return false;
	}
	var nowdate = getNowFormatDate();
	var checkdatez = checkdate(visittime.substring(0,10), nowdate); 
	if (checkdatez == 1) {
		layer.alert('请选择当天或当天之后的时间！', {
		});
		return false;
	} 
	if(visitor == "" || visitor == null ){
		layer.alert('请选择计划回访人', {  end:function(){
		}});
		return false;
	}
	var jhname = "",jhseqId="";
	if(extensioninput!=""){
		jhname = extensioninput;
	}
	/* if(extensionsel!=""){
		jhseqId = extensionsel;
	} */
	//循环获取表格中项目
	var list = [];
	for(var i = 0 ; i < rows.length; i++){
		var usercode = rows[i].usercode;
		var username = rows[i].username;
		//循环保存
		var param = {
			usercode : usercode,
			username : username,
			status : 0,
			remark : remark, // 备注
			visittime : visittime
		};
		list.push(param);
	}
	var data = JSON.stringify(list); 
	var a={
		data:data,
		visitor:visitor,
		rowsnum:rows.length,
		jhname : jhname,
		visittime : visittime,
		organization : $("#organization").val(),
		jhseqId : jhseqId
	};
	 var url = '<%=contextPath%>/KQDS_ExtensionAct/insertOrUpdate.act';
     $.axseSubmit(url,a, 
		function(){
			 layer.msg('加载中', {icon: 16});
	  	},
       function(r){
   	        if(r.retState=="0"){
   	        	layer.alert('添加成功', {  end:function(){
   	        		var index = parent.layer.index; //获取当前弹层的索引号
   	        		parent.layer.close(index); //关闭当前弹层
   	        	}});
   	        }else{
   	        	layer.alert('添加失败' );
   	        }     
           },
           function(){
         	    layer.alert('添加失败' );
		}
	); 
}
//加载病人信息
function getUserDetail(){
	var url = '<%=contextPath%>/KQDS_VisitAct/selectCostorderDetailByUsercode.act?usercode='+usercode;
	$.axse(url,null, 
        function(data){
	 		$("#usercode").val(data.data.usercode);
	 		$("#username").val(data.data.username);
	 		$("#telphone").val(data.data.phonenumber1);
	 		$("#sex").val(data.data.sex );
        },
		function(){
			layer.alert('查询出错！' );
		}
	);
}
</script>

</html>
