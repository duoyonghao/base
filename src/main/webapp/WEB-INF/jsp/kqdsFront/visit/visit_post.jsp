<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	String seqId = request.getParameter("seqId");
	//如果传值为isparent 为layer.open 弹出
	//如果传值不为isparent默认设为noparent 为parent.layer.open 弹出
	String lytype = request.getParameter("type");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8" />
    <title>回访结果</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
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
<!--回访结果弹窗-->
<div class="container">
    <input type="hidden" class="form-control" name="seqId" id="seqId" >
    <input type="hidden" class="form-control" name="status" id="status" >
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
					 <input type="text" id="visittime" name="visittime" placeholder="请选择时间" readonly>
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
				<td>
					<input type="hidden" name="visitor" id="visitor" placeholder="回访人员" title="回访人员" value=""/>
					<input class="whiteInpPointer" type="text" id="visitorDesc" name="visitorDesc" placeholder="回访人员"  onClick="javascript:single_select_user(['visitor', 'visitorDesc']);">
				</td>
				
			</tr>
			<tr class="tr_Hf"><!--tr_Hf该行的行高调整 -->
				<td>
					<span class="information">回访要点</span>
				</td>
				<td colspan="5">
					<textarea rows="3" name="hfyd" id="hfyd" placeholder="" ></textarea>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="information">满意度</span>
				</td>
				<td><!-- class="dict select2"绑定了回访分类的加载 -->
					<select class="select2 dict" tig="hfmyd" name="myd" id="myd"></select>
				</td>
				<td>
					<span class="information">提交人员</span>
				</td>
				<td><!-- class="dict select2"绑定了回访分类的加载 -->
					<input type="hidden" name="postperson" id="postperson" placeholder="提交人员" title="提交人员" value=""/>
					<input type="text" id="postpersonDesc" name="postpersonDesc" placeholder="提交人员" readonly >
				</td>
			</tr>
			<tr class="tr_Hf"><!--tr_Hf该行的行高调整 -->
				<td>
					<span class="information">回访结果</span>
				</td>
				<td colspan="5">
					<textarea rows="3" name="hfresult" id="hfresult" placeholder="" ></textarea>
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
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>

<script type="text/javascript">

var contextPath = '<%=contextPath%>';
var seqId = '<%=seqId%>';
var postname ="<%=person.getUserName()%>";
var postperson ="<%=person.getSeqId()%>";
var lytype = "<%=lytype%>";
var regno = "";
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function () {
	if (usercode == "" || usercode == null || usercode == "null" || usercode == undefined) {
        //不是从	消息推送页面跳转过来的
        onclickrowObj = window.parent.onclickrowOobj;
        //如果选中的不是挂号记录
        if (onclickrowObj == "" || onclickrowObj.ifcost == null) {
            regno = onclickrowObj.regno;
        } else {
            regno = onclickrowObj.seqId;
            isdelreg = onclickrowObj.del;
        }
        if (onclickrowObj.usercode != "" && onclickrowObj.usercode != null) {
            var tableheaderstr = "";
            //展示按钮
            tableheaderstr += onclickrowObj.username;
            tableheaderstr += "(" + onclickrowObj.usercode + ")的回访记录";
            $(".title").html(tableheaderstr);
        }
        usercode = onclickrowObj.usercode;
    }
	
	initDictSelectByClass(); // 回访分类、满意度
	
	//提交人默认为当前用户
	var detailurl = '<%=contextPath%>/KQDS_VisitAct/selectDetail.act?seqId='+seqId;
	$.axse(detailurl,null, 
        function(data){
	 		loadData(data.data);
	 		if(data.data.myd != "" && data.data.myd != null){
	 			$("#tijiao").hide();
	 		}
	 		if(data.data.postperson == "" || data.data.postperson == null){
		 		$("#postperson").val(postperson);
		 		$("#postpersonDesc").val(postname);
	 		}else{
	 			if(document.getElementById("postperson").value.trim() != ""){
	 				bindPerUserNameBySeqIdTB("postpersonDesc",document.getElementById("postperson").value);
				}
	 		}
	 		if(document.getElementById("visitor").value.trim() != ""){
	 			bindPerUserNameBySeqIdTB("visitorDesc",document.getElementById("visitor").value);
			}
	 		//验证是否是自己添加的回访 不是自己的不能修改和提交
	 		//if(!isown(data.data.visitor)){
	 			//$("#tijiao").hide();
	 		//}
		},
		function(){
	       	  layer.alert('查询出错！' );
		}
	);
	$('.searchSelect').selectpicker("refresh");//初始化刷新--2019-10-26 licc
});

function refresh(){
	window.parent.rerefresh();
};
//检查是否是自己的返回true 不是自己的返回false
function isown(aid){
	var isown = true;
	$.ajax({
		type: "POST",
		async: false,
     	url: contextPath +"/YZPersonAct/findIsLeaveBySeqId.act",
      	dataType: "json",
      	data:{seqId:aid},
      	success: function(data){  
      		if(data.retMsrg!='1'){
      			if(aid != postperson){
      				isown = false;
      			}
      		}
      	}
	});
	return isown; 
}

//提交
function submitu(){
	//验证
	var myd = $("#myd").val();
	var postperson = $("#postperson").val();
	var hfresult = $("#hfresult").val();
	var usercode = $("#usercode").val();
	var username = $("#username").val();
	var sex = $("#sex").val();
	var telphone = $("#telphone").val();
	var visittime = $("#visittime").val();
	var visitor = $("#visitor").val();
	var status = $("#status").val();
	if(myd == "" || myd == null){
		layer.alert('请选择满意度' );
		return false;
	}
	if(postperson == "" || postperson == null){
		layer.alert('请选择提交人' );
		return false;
	}
	if(status == "1"){
		layer.alert('您选中的回访记录已经回访过了' );
		return false;
	}
	//验证是否是自己添加的回访 不是自己的不能修改和提交
	if(!isown(visitor)){
		layer.alert('您不可以操作别人提交的回访记录' );
		return false;
	}
	var param = {
			seqId : seqId,
			myd : myd,
			usercode : usercode,
			username : username,
			sex : sex,
			telphone : telphone,
			visittime : visittime,
			postperson : postperson,
			hfresult : hfresult
	};
       var url = '<%=contextPath%>/KQDS_VisitAct/insertOrUpdate.act';
       $.axseSubmit(url,param, 
  		function(){
	  	},
       function(r){
   	        if(r.retState=="0"){
   	        	/* layer.alert('回访完成', {
				  	      
				  	    end: function () {
				  	    	try{
  				  	    		parent.$("#tabIframe")[0].contentWindow.refresh();
  				  	    	}catch(e){
  				  	    		parent.refresh();
  				  	    	}
				  	    	parent.layer.close(frameindex); //再执行关闭

				  	    }
			  	    }); */
			  	  layer.confirm('是否要添加新的回访吗？', {
			          btn: ['确定', '取消'] //按钮
			      },
			      function() {
			    	  //layer.alert("我是确认添加回访");
			    	 try{ 
			    	  parent.layer.open({
	  		  	          type: 2,
	  		  	          title: '添加回访',
	  		  	          shadeClose: false,
	  		  	          shade: 0.6,
	  		  	          area: ['550px', '480px'],
	  		  	          content: '<%=contextPath%>/KQDS_VisitAct/toVisitAdd.act?lytype='+lytype+'&usercode=' + usercode + '&regno=' + regno 
	  		  	      	 });
			    	  }catch(e){
			  	    		parent.refresh();
			  	    	}
			  	    	parent.layer.close(frameindex); //再执行关闭
			      },function(){
			    	  //layer.alert("我是不添加回访");
			    	  try{
 			  	    		parent.$("#tabIframe")[0].contentWindow.refresh();
 			  	    	}catch(e){
 			  	    		//window.parent.refresh();
 			  	    		if(window.parent.patienPageIdentifying=="returnVisit"){
     		            		window.parent.refresh();
     		            	}
 			  	    	}
 			  	    	parent.layer.close(frameindex); //再执行关闭
			      });
   	        }else{
   	        	layer.alert('操作失败：' + r.retMsrg );
   	        }     
           },
           function(){
         	    layer.alert('操作失败' );
		}
       ); 
}

</script>
</html>
