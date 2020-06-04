<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%
	String contextPath = request.getContextPath();

	YZPerson person = SessionUtil.getLoginPerson(request);
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title></title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<link href="<%=contextPath%>/static/css/kqdsFront/iconfont.css" rel="stylesheet" type="text/css"/>
<link href="<%=contextPath%>/static/css/kqdsFront/fileUpload.css" rel="stylesheet" type="text/css">
<style>
	.video1 i{
    color: red;
    cursor: pointer;
    font-size: 20px;
    }
    .video1{
    width: 48%;
    float: left;
    margin:10px;
    }
    .list{
    width: 48%;
    float: right;
    margin:10px;
    }
    .bootstrap-table .fixed-table-body thead {
    background: #fff !important;
	}
	.fixed-table-container{
		height: auto !important;
	}
	.btn{
	    background: #00a6c0;
	    color: #fff;
	    float: right;
	    border: none;
	    margin: 10px 10px 0 10px;
	}
	.ll{
	    float: left;
	    border: none;
	    margin: 17px 10px 0 10px;
	    font-size: 18px;
	    font-weight: 600;
	}
	#table td, #table1 td, #table2 td, #listTable td, #dykdxm td, #chufang td {
        text-align: left !important;
    }
    .fixed-table-body {
    border-radius: 5px !important;
	}
	.progress {
    margin-bottom: 0 !important ;
    }
</style>
</head>
<body> 
    <div style="width:100%">
	    <!--创建一个文件上传的容器-->
	    <div id="fileUploadContent" class="fileUploadContent"></div>
	    <!-- <button onclick="success()">模拟上传成功</button><button onclick="fail()">模拟上传失败</button> -->
	    <br/>
	    <br/>
	    <div style="width:100%;">
		    <div class="video1">
		    	<video autoplay controls volume=1 width="100%" height="auto" id="myvideo"></video>
		    </div>
	    	<div class="list">
	    		<span class="ll">视频列表</span>
	    	    <button class="btn" onclick="del()">删除</button>
		    	<div class="tableBox">
					<table id="table" class="table-striped table-condensed table-bordered"></table>
		    	</div>
	    	</div>
	    </div>
    </div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/video/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/video/fileUpload.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>

<script type="text/javascript">

	var contextPath = "<%=contextPath%>";
	var pageurl = contextPath + '/Kqds_Jh_VideoAct/FileUploadAct';
	var onclickrow = ""; //选中行对象
	$(function() {
		getlist();
	});
	//文件上传
	let wfu = new WpFileUpload("fileUploadContent");
	wfu.initUpload({
	    "uploadUrl":pageurl,//上传文件信息地址
	    "progressUrl":"#",//获取进度信息地址，可选，注意需要返回的data格式如下（{bytesRead: 102516060, contentLength: 102516060, items: 1, percent: 100, startTime: 1489223136317, useTime: 2767}）
	    "scheduleStandard":false,
	});
	
	//显示文件，设置删除事件
	<%-- wfu.showFileResult("<%=contextPath%>/static/image/a2.png","1",null,true,true,deleteFileByMySelf,downloadByMySelf);
	//如果不需要删除
	wfu.showFileResult("<%=contextPath%>/static/image/a3.png","2",null,false,false,null,null); --%>
	//多文件需要自己进行循环
	function deleteFileByMySelf(fileId){
	    alert("要删除文件了："+fileId);
	    wfu.removeShowFileItem(fileId);
	}
	function success(){
		 wfu.uploadSuccess();
	}
	function fail(){
		 wfu.uploadError();
	}
	function downloadByMySelf(fileId, url) {
        alert(url)
    }
	//文件上传 end

	
<%-- 	function getlist(){
		var rows=tdindex = $("#table").find("tbody").find("tr").length;
		var url = contextPath + '/Kqds_Jh_VideoAct/select.act';
		$.ajax({
		    type: "POST",
		    url: url,
		    dataType: "json",
		    success: function(r){
	    			var num=Math.ceil(Number(r.length)/7);
	    			for (var j = 0; j < num; j++) {
	   					var tb = document.getElementById("table");
				        var newTr=tb.insertRow(rows+j);// 添加新行，trIndex就是要添加的位置
				        var l=0;
		    			for (var i = 7*j; i < r.length; i++) {
		    				if(l==7){
		    					break;
		    				}
		    				//var suffix = r[i].url.split(".");
						    newTr.insertCell().innerHTML = '<td><div style="width: 200px;"><video src="/video/<%=ChainUtil.getCurrentOrganization(request)%>/'+r[i].url+'" controls volume=0.5 width="100%" height="auto"></video></div><div ><span style="margin-left: 25%;">'+r[i].filename+'</span><i onclick="del(\''+r[i].url+'\')" class="iconfont icon-shanchu"></i></div></td>';
						    l+=1;
		    		}
	    		} 
		    }
		}); 
	} --%>
var url = contextPath + '/Kqds_Jh_VideoAct/select.act';
function getlist(){
	$('#table').bootstrapTable({
        url: url,
        dataType: "json",
        onLoadSuccess: function(data) { //加载成功时执行
         	//计算主体的宽度
            setWidth();
            setHeight();
        },
        columns: [
        {
            field: 'filename',
            align: 'center',
			formatter: function (value, row, index) {
				var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
		        var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
		       	var s= pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
				return '<img src= '+contextPath + '/static/image/kqdsFront/tag/video.png/>&nbsp;<span>'+s+'.&nbsp;'+value+'&nbsp;&nbsp;'+row.burningTime+'</span>';
			}
		}]
    }).on('click-row.bs.table',function(e, row, element) {
    	$('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrow = $('#table').bootstrapTable('getData')[index];
    }).on('dbl-click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrow = $('#table').bootstrapTable('getData')[index];
        play();
    });;
}
var myvideo = document.getElementById("myvideo");  
function play(){
    	myvideo.src = '/video/<%=ChainUtil.getCurrentOrganization(request)%>/'+onclickrow.url;
    	myvideo.load();
    	myvideo.play();
}



 function del(){
		layer.confirm("是否确定删除？",{
			   btn: ['确认', '取消'],
			   closeBtn:0
		}, function (index) {
				layer.msg('玩命加载中&nbsp;&nbsp;<img src='+contextPath+'/static/image/kqdsFront/tag/await.gif/>',{time:60*1000},function() {
				})
				var url1 = contextPath + '/Kqds_Jh_VideoAct/del.act';
				$.ajax({
				    type: "POST",
				    url: url1,
				    data:{url:onclickrow.url},
				    dataType: "json",
				    success: function(r){
				    	if(r.retState == "0"){
			        		layer.alert('操作成功!');
			        	    refresh();
			        	}else{
			        		layer.alert('操作失败!');
			        	}
				    }
				}); 
			  }, function(index){
		});
		
	}
	function refresh(){
		$('table tbody').html('');
		$('#table').bootstrapTable('refresh', {
	        'url': url
	    });
		myvideo.src = '';
	};
</script>
</html>