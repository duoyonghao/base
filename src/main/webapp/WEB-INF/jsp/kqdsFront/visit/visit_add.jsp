<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String usercode = request.getParameter("usercode");
	String regno = request.getParameter("regno");
	String lytype = request.getParameter("lytype");
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8" />
    <title>添加回访</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/visit/visitTableLayout.css">
    <!-- select搜索筛选 -->
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
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
</style>
	
</head>
<body>
<!--添加回访弹窗-->
<div class="containerAddEdit"> <!-- "containerAddEdit" 添加、修改回访容器的样式 -->
    <input type="hidden" class="form-control" name="seqId" id="seqId" >
    <table id="table">
		<tbody>
			<tr>
				<td>
					<span class="information">病人编号</span>
				</td>
				<td>
					<input type="text" id="usercode" name="usercode" readonly>
				</td>
				<td>
					<span class="information">患者姓名</span>
				</td>
				<td>
					<input type="text" id="username" name="username" readonly>
				</td>
			</tr>
			<tr>
				<td>
					<span class="information">患者性别</span>
				</td>
				<td>
					<input type="text" id="sex" name="sex" readonly>
				</td>
				<td>
					<span class="information">患者手机</span>
				</td>
				<td>
					<input type="text" id="telphone" name="telphone" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>
					<span class="information">回访时间</span>
				</td>
				<td>
					 <input class="whiteInpPointer" type="text" id="visittime" name="visittime" placeholder="请选择时间" autocomplete="off">
				</td>
				<td>
					<span class="information">回访分类</span>
				</td>
				<td><!-- class="dict select2"绑定了回访分类的加载 -->
					<select id="hffl" name="hffl" tig="HFFL" class="dict select2 searchSelect"  data-live-search="true" title="请选择"></select>
<!-- 					<select class="dict select2" tig="HFFL" name="hffl" id="hffl"></select> -->
				</td>
			</tr>
			<tr>
				<td>
					<span class="information">回访人员</span>
				</td>
				<td> <!-- .whiteInpPointer白底input 表示可以输入的 且鼠标上去有小手光标显示 -->
					<input type="hidden" name="visitor" id="visitor" placeholder="回访人员" title="回访人员" class="form-control" style="" value=""/>
					<input class="whiteInpPointer" type="text" id="visitorDesc" name="visitorDesc" placeholder="回访人员" onClick="javascript:single_select_user(['visitor', 'visitorDesc'],'single');">
				</td>
				
			</tr>
			<tr class="textareaTr">
				<td>
					<span class="information">回访要点</span>
				</td>
				<td colspan="5">
					<textarea class="form-control" rows="3" name="hfyd" id="hfyd" placeholder="" ></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="6" style="text-align:center;">
					<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="submitu()" id="tijiao">提交</a>
				</td>
			</tr>
		</tbody>
    </table>
</div>
<!-- <input type="hidden" id="isleave" value="1" /> -->
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>

<script type="text/javascript">

var contextPath = '<%=contextPath%>';
var usercode = '<%=usercode%>';
var regno = '<%=regno%>';

var visitseqid ="<%=person.getSeqId()%>";
var visitname ="<%=person.getUserName()%>";

var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引

var lytype = "<%=lytype%>";
$(function () {
	if(usercode == "" || usercode == "null" || usercode== null || usercode == "undefined"){
		layer.alert('请选择患者', {
		});
		//隐藏保存按钮
		$("#tijiao").hide();
	}else{
		getUserDetail();//加载病人信息
		initDictSelectByClass(); // 回访分类
		
		//回访人默认是当前登录用户
		$("#visitor").val(visitseqid);
		$("#visitorDesc").val(visitname);
		
		//Date picker
	    $("#visittime").datetimepicker({
	    	language:  'zh-CN',  
	        format: 'yyyy-mm-dd',
	        minView:2,
	        autoclose : true,//选中之后自动隐藏日期选择框   
	        pickerPosition: "bottom-right"
	    });
	}
	 $('.searchSelect').selectpicker("refresh");//初始化刷新--2019-10-26 licc	
});

	//提交
	function submitu() {
		var checkbool = true;
		if (checkbool) {
			//验证
			var usercode = $("#usercode").val();
			var username = $("#username").val();
			var sex = $("#sex").val();
			var telphone = $("#telphone").val();
			var visittime = $("#visittime").val();
			var hffl = $("#hffl").val();
			var finishtime = $("#finishtime").val();
			var visitor = $("#visitor").val();
			var hfyd = $("#hfyd").val();
			if (visittime == "" || visittime == null) {
				layer.alert('请选择回访时间', {
				});
				return false;
			} 
			//添加回访：可以添加在当天的任何时间，也可以选择当天之后，不能往当天之前的时间添加。
			var nowdate = getNowFormatDate();
			var checkdatez = checkdate(visittime.substring(0,10), nowdate); // 0 回访时间大于当前时间  1回访时间小于当前时间 2 回访时间等于当前时间
			if (checkdatez == 1) {
				layer.alert('请选择当天或当天之后的时间！', {
				});
				return false;
			} 
			if (hffl == "" || hffl == null) {
				layer.alert('请选择回访分类', {
				});
				return false;
			}
			if (visitor == "" || visitor == null) {
				layer.alert('请选择回访人员', {
				});
				return false;
			}

			var param = {
				usercode : usercode,
				username : username,
				sex : sex,
				telphone : telphone,
				visittime : visittime,
				hffl : hffl,
				finishtime : finishtime,
				visitor : visitor,
				hfyd : hfyd,
				regno : regno
			};
			var url = '<%=contextPath%>/KQDS_VisitAct/insertOrUpdate.act';
        	$.axseSubmit(url,param, function(){},
        	function(r){
    	        if(r.retState=="0"){
    	        	layer.alert('添加成功', {
    	        		yes:function(index){
    	        			//end: function () {
    	     		           try{
    	     		             parent.$("#tabIframe")[0].contentWindow.refresh();
    	     		            }catch(e){
    	     		            	if(window.parent.patienPageIdentifying=="returnVisit"){
    	     		            		window.parent.refresh();
    	     		            	}
    	     		             // parent.location.reload();
    	     		            }
    	     		            parent.layer.close(frameindex); //再执行关闭
    	     		             }
    	        		//}
   				  	/* end: function () {
   		              try{
   		             parent.$("#tabIframe")[0].contentWindow.refresh();
   		            }catch(e){
   		            //parent.location.refresh();
   		             // parent.location.reload();
   		            }
   		            parent.layer.close(frameindex); //再执行关闭
   		             } */
  			  	    });
    	        }else{
    	        	layer.alert('添加失败：' + r.retMsrg );
    	        }
            },
            function(){
          	    layer.alert('添加失败' );
			}
        ); 
	}
}

 //判断当前患者今日是否已添加过回访记录
function checktimeandname(){
	var checkbool = true;
	var time = $("#visittime").val();
	var username = $("#username").val();
	if(username != "" && time != ""){
<%-- 		var url = '<%=contextPath%>/KQDS_VisitAct/checkTodayIsHf.act?name=' + username + '&time=' + time; --%>
		var url = "";
		$.axse(url,null, function(data){
			if(data.retState=="0"){
				checkbool = true;
	        }else if(data.retState=="1"){
	        	layer.alert('该患者今日已添加回访！'  );
	        	checkbool = false;
	        }
        },
		function(){
			layer.alert('查询出错！'  );
			checkbool = false;
		});
	}
	return checkbool;
}

//加载病人信息
function getUserDetail(){
	var url = '<%=contextPath%>/KQDS_VisitAct/selectCostorderDetailByUsercode.act?usercode='+usercode;
	$.axseY(url,null, 
        function(data){
	 		$("#usercode").val(data.data.usercode);
	 		$("#username").val(data.data.username);
	 		$("#telphone").val(data.data.phonenumber1);
	 		$("#sex").val(data.data.sex);
        },
		function(){
			layer.alert('查询出错！'  );
		}
	);
}
</script>
</html>
