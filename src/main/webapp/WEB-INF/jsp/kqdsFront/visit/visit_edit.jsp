<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
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
    <title>修改回访</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
    <!--修改回访和添加回访用一个样式表 -->
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
<div class="containerAddEdit"> 	<!-- "containerAddEdit" 添加、修改回访容器的样式 -->
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
					 <input class="whiteInpPointer" type="text" id="visittime" name="visittime" placeholder="请选择时间">
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
				<td colspan="6"  style="text-align:center;">
					<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="submitu()" id="tijiao">提交</a>
				</td>
			</tr>
		</tbody>
    </table>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript">

var contextPath = '<%=contextPath%>';
var seqId = '<%=seqId%>';
var lytype = "<%=lytype%>";
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function () {
// 	getUserDetail();//加载病人信息
	
	initDictSelectByClass(); // 回访分类
	
	var detailurl = '<%=contextPath%>/KQDS_VisitAct/selectDetail.act?seqId='+seqId;
	$.axse(detailurl,null, 
        function(data){
	 		loadData(data.data);
	 		if(document.getElementById("visitor").value.trim() != ""){
	 			bindPerUserNameBySeqIdTB("visitorDesc",document.getElementById("visitor").value);
			}
		},
		function(){
	       	  layer.alert('查询出错！' );
		}
	);
	
	//Date picker
    $("#visittime").datetimepicker({
    	language:  'zh-CN',  
   	    format: 'yyyy-mm-dd',
        minView:2,
        autoclose : true,//选中之后自动隐藏日期选择框   
        pickerPosition: "bottom-right"
    });
    $('.searchSelect').selectpicker("refresh");//初始化刷新--2019-10-26 licc
});

//提交
function submitu(){
	// 	var checkbool = checktimeandname();//验证患者在选择的那天里是否存在回访
	var checkbool = true;
	if(checkbool){
		//验证
		var organization = $("#organization").val();
		var usercode = $("#usercode").val();
		var username = $("#username").val();
		var sex = $("#sex").val();
		var telphone = $("#telphone").val();
		var visittime = $("#visittime").val();
		var hffl = $("#hffl").val();
		var finishtime = $("#finishtime").val();
		var visitor = $("#visitor").val();
		var hfyd = $("#hfyd").val();
		if(visittime == "" || visittime == null){
			layer.alert('请选择回访时间' );
			return false;
		} 
		//添加回访：可以添加在当天的任何时间，也可以选择当天之后，不能往当天之前的时间添加。
		/* var nowdate = getNowFormatDate();
		var checkdatez = checkdate(visittime.substring(0,10), nowdate); // 0 回访时间大于当前时间  1回访时间小于当前时间 2 回访时间等于当前时间
		if (checkdatez == 1) {
			layer.alert('请选择当天或当天之后的时间！', {
			});
			return false;
		} */
		if(hffl == "" || hffl == null){
			layer.alert('请选择回访分类' );
			return false;
		}
		if(visitor == "" || visitor == null){
			layer.alert('请选择回访人员' );
			return false;
		} 
		
		var param = {
				seqId : seqId,
				organization : organization,
				usercode : usercode,
				username : username,
				sex : sex,
				telphone : telphone,
				visittime : visittime,
				hffl : hffl,
				finishtime : finishtime,
				visitor : visitor,
				hfyd : hfyd
		};
        var url = '<%=contextPath%>/KQDS_VisitAct/insertOrUpdate.act';
        $.axseSubmit(url,param, 
	  	function(){
 	  	},
        function(r){
    	        if(r.retState=="0"){
    	        	layer.alert('修改成功', {
  				  	      
    	        		end: function () {
 	  				  	    try{
 				  	    		windows.parent.$("#tabIframe")[0].contentWindow.refresh();
 				  	    	}catch(e){
 				  	    		window.parent.refresh();
 				  	    	}
 				  	    	parent.layer.close(frameindex); //再执行关闭
   				  	    }
  			  	    });
    	        }else{
    	        	layer.alert('修改失败：' + r.retMsrg );
    	        }     
            },
            function(){
          	    layer.alert('修改失败' );
			}
        );
	}
}

</script>
</html>
