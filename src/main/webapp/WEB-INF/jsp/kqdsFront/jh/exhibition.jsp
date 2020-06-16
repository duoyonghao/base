<%@page import="javax.sound.midi.SysexMessage"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp"%>
<%
	YZPerson person = SessionUtil.getLoginPerson(request);
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
	String floor = request.getParameter("floor");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>排队列表</title>
<!-- 接待中心  下方退款按钮进入  -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/swiper.css">

<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/swiper.js"></script>
</head>
<style>
.box {
	/* height: auto; */
	width: 54%;
	position: relative;
}

.bg {
	height: 190px;
	background: #00a6c0;
}

.head {
	margin: 0 50px;
	width: 91%;
	height: 160px;
	background: #fff;
	position: absolute;
	top: 40px;
	box-shadow: 1px 1px 50px rgba(0, 0, 0, .3);
}

.setout {
	display: inline-block;
	height: 100%;
	width: 30%;
	text-align: center;
	line-height: 170px;
	font-size: 45px;
	font-weight: 600;
	float: left;
	color: #00a6c0;
} 
.timeout {
	padding-top: 8px;
	padding-bottom: 8px;
	color: #333;
	text-align: left;
	font-size: 25px;
	font-weight: 600;
}

.gaine {
	display: inline-block;
	height: 100%;
	width: 30%;
	text-align: center;
}

.number {
	font-size: 38px;
	font-weight: 600;
	margin: 54px 0 0 0;
}

.aid {
	display: inline-block;
	width: 24%;
	text-align: center;
}

#video {
	width: 100%;
    /*height: 485px;*/
	float: right;
	padding: 10px 10px 0 10px;
}

.size tbody tr td span {
	
	font-size: 16px;
}


.th-inner {
	font-size: 17px;
}

/* @media  screen and (min-width: 1820px) { */
	 .th-inner{
	  font-size:40px;
	  font-weight: 600;
	 }
	 .size tbody tr td span {
       font-size: 30px;
       font-weight: 600;
     }
    .fixed-table-body thead th .th-inner {
	    padding-right: 8px;
	    height: 50px;
	    line-height: 30px;
	    font-weight: 900;
	}
	#table td>span, #table1 td>span, #table2 td>span, #listTable td>span, #dykdxm td>span, #chufang td>span {
	    height: 40px;
	    line-height: 40px;
	}
	.timeout {
	    padding-bottom: 5px;
	    color: #333;
	    text-align: left;
	    font-size: 40px;
	    font-weight: 600;
	}
	.setout {
	    font-size: 70px;
	    font-weight: 600;
	    line-height: 160px;
	}
	.number {
	    font-size: 60px;
	    font-weight: 600;
	    margin: 25px 0 0 0;
	}
	.stair {
	    font-size: 30px;
	    font-weight: 600;
	}
	.two {
		font-size: 29px;
		font-weight: 600;
	}
	.bg {
	    height: 180px;
	}
	.hover{
	 	height: 260px !important;
	}
    /* } */
    
    
.stair {
	font-size: 20px;
}

.two {
	font-size: 19px;
}
.hover{
  margin: 0px 30px;
  height: 279px;
  overflow: hidden;
}
.proceed{
margin: 30px 30px 0 30px;
}



.swiper-container {
      width: 100%;
      height: 480px;
      /* height: 170px; */
    }
.swiper-pagination{
     bottom: 0px;
    }

    .photo {
      float: left;
      width: 38%;
      margin-top: 25px;
    }

    .photo img {
      width: 100%;
      height: 100%;
    }

    .intro {
      float: right;
      width: 60%;
      margin: 27px 10px 0 0;
    }

    p {
      font-size: 19px;
      margin: 0;
      text-align: left;
      text-indent: 25px;
    }
    
    h4{
    text-align: center;
    font-size: 19px;
    }

    
</style>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/video.min.js" charset="utf-8"></script>
<body>
	<div class="box" style="float: left;width:50%;">
		<div class="bg"></div>
		<div class="head">
			<div class="setout">请准备</div>
			<div class="gaine">
				<div class="number" id="number"></div>
				<span class="stair"> 性别: <span id="sex" class="two"></span>
				</span>
			</div>
			<div class="gaine">
				<div class="number" id="name"></div>
				<span class="stair"> 患者编号: <span id="usercode" class="two"></span>
				</span>
			</div>
		</div>
		<div  class="proceed">
			<p class="timeout">进行中</p>
			<table id="proceedTable"
				class="table-striped table-condensed table-bordered size"></table>
		</div>
		<div  class="hover" id="demo">
			<p class="timeout">排队中</p>
			<table id="table"
				class="table-striped table-condensed table-bordered size"></table>
		</div>

	</div>
	<div style="width:50%;float:right;">
	<div id="video">
		<video autoplay controls volume=1 width="100%" height="auto" id="myvideo"></video>
	</div>
	
	<div class="swiper-container">
    		<div class="hover" id="demo1">
			<p class="timeout">已超时</p>
			<table id="dykdxm"
				class="table-striped table-condensed table-bordered size"></table>
		</div>	
    <!-- Add Pagination -->
    <!-- 滚动圈圈 -->
    <!-- <div class="swiper-pagination"></div> -->
  </div>
	</div>
</body>
<script type="text/javascript">
var floor="<%=floor%>";  

$(function() {
	showVideo();
    getlist();
	getDelList();
	getProceedList();
});
function showVideo(){
	var url = contextPath + '/Kqds_Jh_VideoAct/select.act';
	var vList = [];
	$.ajax({
        type: "POST",
        url: url,
        async: false,
        dataType: "json",
        success: function(r){
        	if(r.length>0){
        		for (var i = 0; i < r.length; i++) {
        			vList.push('/video/<%=ChainUtil.getCurrentOrganization(request)%>/'+r[i].url);
				}
        	}
        }
	});
	var vLen = vList.length;  
    var curr = 0;  
    var myvideo = document.getElementById("myvideo");  
    	myvideo.src = vList[vList.length-1];
    	myvideo.load();
    	myvideo.play();
        myvideo.onended = function() {
        myvideo.src = vList[curr];    
        myvideo.load();    
        myvideo.play();   
        curr++;   
        if(curr >= vLen){   
            curr = 0; //重新循环播放
        }
	};
}
	
	// 表格循环开始
	function change() {
    	 var body = $("#table tbody");
         body.append(body.children().first());
    }  
	$(document).ready(function () {
         $("#demo").mouseenter(function () {
             clearInterval(MyMar1)
         });
         $("#demo").mouseleave(function () {
             MyMar1 = setInterval(change, 6000);
         });
     });
	 var MyMar1 = setInterval(change, 6000);
	 
	 function change1() {
		 var body = $("#dykdxm tbody");
		 body.append(body.children().first());
	 }  
	 $(document).ready(function () {
         $("#demo1").mouseenter(function () {
             clearInterval(MyMar2)
         });
         $("#demo1").mouseleave(function () {
             MyMar2 = setInterval(change1, 6000);
         });
	 });
	 var MyMar2 = setInterval(change1, 6000);
	// 表格循环结束
     var websocket = null;
     //判断当前浏览器是否支持WebSocket
     if ('WebSocket' in window) {
    	 if('<%=ChainUtil.getCurrentOrganization(request)%>'=='HUDH'){
    	     websocket = new WebSocket("ws://192.168.1.138:10001/base/WSwebsocket");
   		 }else{
   			 websocket = new WebSocket("ws://192.168.1.138:11111/base/WSwebsocket");
   		 }
     }
     else {
    	 alert('当前浏览器 Not support websocket')
     }
 
     //连接发生错误的回调方法
     websocket.onerror = function () {
         //setMessageInnerHTML("WebSocket连接发生错误");
     };
 
     //连接成功建立的回调方法
     websocket.onopen = function () {
         //setMessageInnerHTML("WebSocket连接成功");
     }
 
     //接收到消息的回调方法
     websocket.onmessage = function (event) {
    	 if(event.data=='<%=ChainUtil.getCurrentOrganization(request)%>'){
    		 showVideo();
    	 }else{
    		   var data = event.data.split(",");
	  		   var j=0;
	  		   for(var i = 0; i < data.length; i++){
	  		    	if(data[i]=='<%=ChainUtil.getCurrentOrganization(request)%>'){
	  		    		j+=1;
	  		    	}else if(data[i]==floor){
	  		    		j+=1;
	  		    	}
	  		   }
	  		   if(j==data.length){
	  			//刷新页面
	  			refresh();
	  		   }
    	 }  
		   
     }
 
     //连接关闭的回调方法
     websocket.onclose = function () {
         //setMessageInnerHTML("WebSocket连接关闭");
     }
 
     //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
     window.onbeforeunload = function () {
         closeWebSocket();
     }
 
/*      //将消息显示在网页上
     function setMessageInnerHTML(innerHTML) {
    	 //console.log("参数"+innerHTML);
    	if(innerHTML.contains("HUDH")&&innerHTML.contains("1lou")){
    		 
    	 }else if(innerHTML.contains("HUDH")&&innerHTML.contains("2lou")){
    		 
    	 }else if(innerHTML.contains("HUDX")){
    		 
    	 } 
          //document.getElementById('message').innerHTML += innerHTML + '<br/>';
          getlist();
     } */
 
     //关闭WebSocket连接
     function closeWebSocket() {
         websocket.close();
     }
 
     //发送消息
     function send() {
         var message = document.getElementById('text').value;
         websocket.send(message);
     }     
    var contextPath = "<%=contextPath%>";
	var pageurl = contextPath + '/Kqds_JhAct/select.act';
	var pageurl1 = contextPath + '/Kqds_JhAct/selectdel.act';
	var id = "";
	var l = 0;
	function getlist() {
		$("#table").bootstrapTable(
				{
					url : pageurl,
					dataType : "json",
					queryParams : queryParams,
					onLoadSuccess : function(data) {
						l = data.length;
						if (data.length > 0) {
							id = data[0].id;
							if (Number(data[0].number) <= 9) {
								$("#number").html(
										data[0].floor + '0' + data[0].number);
							} else {
								$("#number").html(
										data[0].floor + data[0].number);
							}
							$("#sex").html(data[0].sex);
							$("#usercode").html(data[0].usercode);
							$("#askperson").html(data[0].askpersonname);
							$("#name").html(data[0].patient_name);
						} else {
							$("#number").html("---");
							$("#sex").html("-");
							$("#usercode").html("---");
							$("#askperson").html("---");
							$("#name").html("---");
						}

					},
					columns : [
							{
								title : '序号',
								field : 'floor',
								align : 'center',
								formatter : function(value, row, index) {
									if (Number(row.number) <= 9) {
										return '<span >' + value + '0'
												+ row.number + '</span>';
									} else {
										return '<span >' + value + row.number
												+ '</span>';
									}
								}
							}, {
								title : '患者编号',
								field : 'usercode',
								align : 'center',
								formatter : function(value) {
									return '<span>' + value + '</span>';
								}
							}, {
								title : '患者姓名',
								field : 'patient_name',
								align : 'center',
								formatter : function(value) {
									return '<span>' + value + '</span>';
								}
							}, {
								title : '性别',
								field : 'sex',
								align : 'center',
								formatter : function(value) {
									return '<span>' + value + '</span>';
								}
							} /* ,
					             {
					                 title: '助理',
					                 field: 'askpersonname',
					                 align: 'center',
					                 formatter:function(value){
					                 	return '<span>'+value+'</span>';
					                 }
					             } */
					]
				}).on('click-row.bs.table', function(e, row, element) {
		});
	}

	function queryParams(params) {
		var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			status : 1,
			floor : floor
		};
		return temp;
	}
	function queryParams1(params) {
		var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			floor : floor
		};
		return temp;
	}
	function queryParams2(params) {
		var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			status : 2,
			floor : floor
		};
		return temp;
	}
	function getDelList() {
		$("#dykdxm").bootstrapTable({
			url : pageurl1,
			dataType : "json",
			queryParams : queryParams1,
			onLoadSuccess : function(data) {

			},
			columns : [ {
				title : '序号',
				field : 'floor',
				align : 'center',
				formatter : function(value, row, index) {
					if (Number(row.number) <= 9) {
						return '<span style="color:red;">' + value + '0' + row.number + '</span>';
					} else {
						return '<span style="color:red;">' + value + row.number + '</span>';
					}
				}
			}, {
				title : '患者编号',
				field : 'usercode',
				align : 'center',
				formatter : function(value) {
					return '<span>' + value + '</span>';
				}
			}, {
				title : '患者姓名',
				field : 'patient_name',
				align : 'center',
				formatter : function(value) {
					return '<span>' + value + '</span>';
				}
			}, {
				title : '性别',
				field : 'sex',
				align : 'center',
				formatter : function(value) {
					return '<span>' + value + '</span>';
				}
			} /* ,
			             {
			                 title: '助理',
			                 field: 'askpersonname',
			                 align: 'center',
			                 formatter:function(value){
			                 	return '<span>'+value+'</span>';
			                 }
			             } */
			]
		}).on('click-row.bs.table', function(e, row, element) {
		});
	}
	function getProceedList() {
		$("#proceedTable").bootstrapTable({
			url : pageurl,
			dataType : "json",
			queryParams : queryParams2,
			onLoadSuccess : function(data) {

			},
			columns : [ {
				title : '序号',
				field : 'floor',
				align : 'center',
				formatter : function(value, row, index) {
					if (Number(row.number) <= 9) {
						return '<span>' + value + '0' + row.number + '</span>';
					} else {
						return '<span>' + value + row.number + '</span>';
					}
				}
			}, {
				title : '患者编号',
				field : 'usercode',
				align : 'center',
				formatter : function(value) {
					return '<span>' + value + '</span>';
				}
			}, {
				title : '患者姓名',
				field : 'patient_name',
				align : 'center',
				formatter : function(value) {
					return '<span>' + value + '</span>';
				}
			}, {
				title : '性别',
				field : 'sex',
				align : 'center',
				formatter : function(value) {
					return '<span>' + value + '</span>';
				}
			} /* ,
			             {
			                 title: '助理',
			                 field: 'askpersonname',
			                 align: 'center',
			                 formatter:function(value){
			                 	return '<span>'+value+'</span>';
			                 }
			             } */
			]
		}).on('click-row.bs.table', function(e, row, element) {
		});
	}
	function refresh() {
		//加载表格
		$('#table').bootstrapTable('refresh', {
			'url' : pageurl
		});
		$('#dykdxm').bootstrapTable('refresh', {
			'url' : pageurl1
		});
		$('#proceedTable').bootstrapTable('refresh', {
			'url' : pageurl
		});
	};
	
	
	//鼠标移入移除停止动画
 $('.swiper-slide').mouseenter(function () {
      swiper.autoplay.stop();
    })
    $('.swiper-slide').mouseleave(function () {
      swiper.autoplay.start();
    }) 
    var swiper = new Swiper('.swiper-container', {
   /*  direction: 'vertical',
    pagination: {
       el: '.swiper-pagination',
      clickable: true,
    }, */
     loop: true,
      slidesPerView: "auto",
      //改变轮播方向
      // direction: 'vertical',
      // loopedSlides: _this.bannerList.length,
      speed: 300,
      autoplay: {
        disableOnInteraction: false, //手动滑动之后不打断播放
        delay: 60000
      },
      observer: true, //监听，当改变swiper的样式（例如隐藏/显示）或者修改swiper的子元素时，自动初始化swiper。
      pagination: {
        el: ".swiper-pagination"
      }
    });
</script>
</html>