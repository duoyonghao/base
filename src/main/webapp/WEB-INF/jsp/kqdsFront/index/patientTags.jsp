<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}

	String deptId = request.getParameter("deptId");
	if(deptId == null){
		deptId = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<title>患者标签</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/person/list.css"/>
<!-- 文件上传 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/style.css">
<meta charset="utf-8" />
<style type="text/css">
		#label{
			margin-top:10px;
			overflow: hidden;
		}
        ul,li{
            padding: 0;
            margin: 0;
            list-style: none;
        }
        #onceLabel,#twoLabel{
            width: 22%;
            height: 405px;
            float: left;
            border: 1px solid #00a6c0;
            border-right: none;
        }
        #threeLabel{
        	width:56% ;
         	height: 405px;
         	float: left;
			border: 1px solid #00a6c0;
        }
        #twoLabel ul{
            width: 100%;
            height: 405px;
            float: left;
        }
       #onceLabel li{
           width: 100%;
           height: 20%;
       }
       #onceLabel li{
        	font-size: 18px;
    		text-align: center;
    		padding-top: 28px;
    		font-weight: 600;
       }
       #twoLabelUl li{
       		font-size: 13px;
        	height:45px;
    		text-align: center;
    		padding-top: 10px;
       }
       
		#threeLabelUl>li{
		/* 小标签的样式，可随意调整 */
			display:inline-block;
			box-sizing:border-box;
			height:26px;
			line-height:26px;
			background:#00a6c0;
			color:#fff;
			outline:none;
			padding:0 18px;
			border:1px solid #00a6c0;
			border-radius:3px;
    		margin-bottom: 5px;
		    margin-left: 20px;
		    margin-top: 10px;
			text-decoration:none;
			cursor:pointer;
			text-align:center;			
		}
		.active{
			background-color: #00a6c0;
			color:white;
		}
		.thirdActive{
			background-color:#1d7584 !important;
		}
		.importText .importSaveBtn{
			display: block;
			margin: 10px auto;
			background-color: #00A6C0;
		    width: 65px;
		    color: white;
		    line-height: 22px;
		    border: 0px solid #00A6C0;
		}
		.importText .textareaText{
			display:block;
			width: 80%;
			margin: 10px auto 0px;
		}
</style>
</head>
<body>
	 <div id="label">
            <ul id="onceLabel"></ul>
            <div id="twoLabel">
                <ul id="twoLabelUl"></ul>               
            </div> 
            <div id="threeLabel">
                <ul id="threeLabelUl"></ul>
            </div>  
	  </div>
	  
	  <!-- 标签输入框 -->
		<div id="inputHtml" style="display: none;">
			<div class="importText">
				<textarea class="textareaText" rows="5" cols="5"></textarea>
				<button class="importSaveBtn" onclick="btnTextSave();">保存</button>
			</div>
		</div>
		
</div>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.min.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<!-- 文件上传 -->
<script type="text/javascript" src="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/upload/uploadutil.js"></script>
<!-- 文件上传 END-->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/md5.js"></script>
<script type="text/javascript">
	var contextPath = "<%=contextPath %>";
	var lastLevelId;
	var lastFirstIndex=0;//记录上一个有样式的选项index
	var lastSecondIndex=0;//记录上一个有样式的选项index
	var labelArr=[];
	var usercode=window.parent.frames["tabIframe"].usercode;
	var labelThreeId1=window.parent.frames["tabIframe"].labelThreeId1;
	var labelThreeId2=window.parent.frames["tabIframe"].labelThreeId2;
	var labelLayerIndex= parent.layer.getFrameIndex(window.name); // 先得到当前自身标签iframe层的索引
	$(function(){
		getlabel("1","onceLabel");
		getlabel(lastLevelId,"twoLabelUl");
		getlabel(lastLevelId, "threeLabelUl");
	});
	
	/* 标签输入框点击事件 */
	function btnTextSave(){
		var inputText=$(".textareaText").val(); // 当前输入框输入的值
		var fatherSecondId,fatherSecondVal;//当前标签的二级标签id以及val
		//当前标签的二级标签
		$("#twoLabelUl").find("li").each(function(i,obj){
			if($(this).hasClass("active")){
				fatherSecondId=$(this).attr("id");
				fatherSecondVal=$(this).text();
			}
		});
		var discontentAlreadyHtml=$(window.parent.frames["tabIframe"].document).find("#discontentShow").html();//得到不满意标签之前内容
		//循环患者建档页面不满意标签
		var labelAlreadyIndex=-1;//存储已存在标签下标
		$(window.parent.frames["tabIframe"].document).find("#discontentShow").find("li").each(function(i,ele){
		 	//对价格不满意内容(判断是否已经添加过此标签，添加过之后修改)
			if($(ele).attr("slid") == fatherSecondId){
				labelAlreadyIndex=i; //如果存在记录已经存在标签的index
				priceText=$("#threeLabelUl .textareaText").val();//当前新输入内容
				$(ele).attr("title",fatherSecondVal+':'+priceText); // 修改已存在标签title
				$(ele).html(priceText+'<span onclick="cleatBtn(this)" id="cleatBtn" class="cleatBtn">X</span>'); // 修改已存在标签文本内容
			}
		});
		//如果没存在此标签，则新添加
		if(labelAlreadyIndex == -1){
			discontentHtml=discontentAlreadyHtml+'<li class="textHidden aa" id="006f0da0-afa1-4001-833f-bc3106b531c6" slid='+fatherSecondId+' slname='+fatherSecondVal+' title='+fatherSecondVal+':'+inputText+'>'+inputText+'<span onclick="cleatBtn(this)" id="cleatBtn" class="cleatBtn">X</span></li>';
			$(window.parent.frames["tabIframe"].document).find("#discontentShow").html(discontentHtml);
		}
	}
	
	function getlabel(parentId,obj) {
		if(obj=="twoLabelUl"){
			$("#twoLabelUl").html("");
			$("#threeLabelUl").html("");
		}else{
			$("#threeLabelUl").html("");
		}
		var url = contextPath + '/KQDS_LabelAct/findLabel.act';
	    var param = {
	    		parentId : parentId //要删除ID
	    };
	    $.axseSubmit(url,param,function() {},function(data) {
	    	//console.log(JSON.stringify(data)+"--------返回数据");    
	      	for(var i=0;i<data.length;i++){               
	      		oneClass=data[i].leveLabel;   
				var thirdhasActive;
				
				if(obj=="threeLabelUl"){ //三级自身标签初始化
					$("#"+obj).append('<li id='+data[i].seqId+' onclick="thirdLevel(this.id);">'+ oneClass +'</li>');
				}else if(obj=="onceLabel"){ //一级自身标签初始化
					 $("#"+obj).append('<li id='+data[i].seqId+' onclick="firstAndsecond(this.id,\'onceLabel\');">'+ oneClass +'</li>');
					 lastLevelId=data[0].seqId;
					 $("#"+obj).find("li").eq(0).addClass("active");
				}else{ //二级自身标签初始化
					 if(data[i].seqId=="bd7c762b-9465-457c-b9b1-b085fc2a27a5" || data[i].seqId=="c8a144a5-84fe-44ee-baf2-59373a6674bf"){
					 }else{
						 $("#"+obj).append('<li id='+data[i].seqId+' status='+data[i].status+' onclick="firstAndsecond(this.id,\'twoLabelUl\');">'+ oneClass +'</li>');
						 lastLevelId=data[0].seqId;
						 $("#"+obj).find("li").eq(0).addClass("active");
					 }
				}
	     	}
	      	for(var j=0;j<data.length;j++){
	      		for (var z = 0; z < labelArr.length; z++) {
				 	if(labelArr[z].selfId==data[j].seqId){
				 		$("#threeLabelUl").find("li").eq(j).addClass("thirdActive");
					}
	      		}
			}
	    },function(data){
	    	layer.alert("删除失败！");
	    });
	}
	
	//第一级，第二级点击事件
	function firstAndsecond(objId,fatherObj){
		var objVal=$("#"+objId).text();
		//可开发项目
		if (objId == "61223947-c0da-4d0f-a1ff-429232d10a3c") {
				$('#threeLabelUl').html('<li id="dpAddbtn" class="dpAddbtn" onclick="thirdLevel(this.id);">添加 +</li>');
				$(window.parent.frames["tabIframe"].document).find("#needShow").find("li").each(function(i,ele){
					if($(ele).attr("slid")=="61223947-c0da-4d0f-a1ff-429232d10a3c"){
						var selfId=$(ele).attr("id");//自身id
						var selfVal=$(ele).attr("value"); //自身value
						var selfSecId=$(ele).attr("slid"); //二级id
						var selfSecName=$(ele).attr("slname");//二级name
						$('#threeLabelUl').append('<li id='+selfId+' slid='+selfSecId+' slname='+selfSecName+' onclick="thirdLevel(this.id);">'+selfVal+'</li>');
					}
				});  
				
            }	
		
		//可开发空间
		if(objId=="3d6b7239-c964-4714-8020-4dcc15ed1f5d"){
			$('#threeLabelUl').html('<li id="dsAddbtn" class="dsAddbtn" onclick="thirdLevel(this.id);">添加 +</li>');
			$(window.parent.frames["tabIframe"].document).find("#needShow").find("li").each(function(i,ele){
				if($(ele).attr("slid")=="3d6b7239-c964-4714-8020-4dcc15ed1f5d"){
					var selfId=$(ele).attr("id");//自身id
					var selfVal=$(ele).attr("value"); //自身value
					var selfSecId=$(ele).attr("slid"); //二级id
					var selfSecName=$(ele).attr("slname");//二级name
					$('#threeLabelUl').append('<li id='+selfId+' slid='+selfSecId+' slname='+selfSecName+' onclick="thirdLevel(this.id);">'+selfVal+'</li>');
				}
			}); 
		}
		
		// 不满意标签添加输入框
		//当前点击标签的一级标签
		var fatherFirstId;
		$("#onceLabel").find("li").each(function(i,obj){
			if($(this).hasClass("active")){
				fatherFirstId=$(this).attr("id");			
			}
		});	
		// 追加输入框代码
		 if(fatherObj=="twoLabelUl" && fatherFirstId=="d5b4a158-5324-4674-ae19-bf1b157dd341"){
			 $(".textareaText").text(""); // 追加之前标签内容前需要先清空
			 var satisfactionHtml=$("#inputHtml").html();
			 $('#threeLabelUl').html(satisfactionHtml);
			 // 判断是否已经添加过内容
			 var priceText;
			 $(window.parent.frames["tabIframe"].document).find("#discontentShow").find("li").each(function(i,ele){
				 	//对价格不满意内容
					if($(ele).attr("slid") == objId){
						priceText=$(ele).text();//自身id
						 $("#threeLabelUl .textareaText").val(priceText.substring(0,priceText.length-1));
					}
			}); 
		 }
		
		if(fatherObj=="onceLabel"){
			getlabel(objId,"twoLabelUl");
			var twoLabelUlfirstId=$("#twoLabelUl").find("li").eq(0).attr("id");//二级标签第一个id
			getlabel(twoLabelUlfirstId,"threeLabelUl");
			$("#onceLabel").find("li").eq(lastFirstIndex).removeClass("active");
		}else{
			if(objId=="61223947-c0da-4d0f-a1ff-429232d10a3c" || objId=="3d6b7239-c964-4714-8020-4dcc15ed1f5d" || fatherFirstId=="d5b4a158-5324-4674-ae19-bf1b157dd341"){
			} else{
				getlabel(objId,"threeLabelUl");
			}
			$("#twoLabelUl").find("li").eq(lastSecondIndex).removeClass("active");
		}
		$("#"+objId).addClass("active");
		$("#onceLabel").find("li").each(function(i,obj){
			if($(this).hasClass("active")){
				lastFirstIndex=$(this).index();
			}
			
		});
		$("#twoLabelUl").find("li").each(function(i,obj){
			if($(this).hasClass("active")){
				lastSecondIndex=$(this).index();
			}
			
		});
		
	}
		
	//第三级点击事件
	function thirdLevel(objId){
		var expenseHtml=""; //消费标签
		var societyHtml="";	//社会标签
		var needHtml=""; //需求标签
		var Slstatus; //得到二级标签状态
		var labelObj={};
		//当前标签
		labelObj.selfVal=$("#"+objId).text();
		labelObj.selfId=objId;
		// 当前标签的一级标签
		$("#onceLabel").find("li").each(function(i,obj){
			if($(this).hasClass("active")){
				labelObj.fatherId=$(this).attr("id");			
				labelObj.fatherVal=$(this).text();
			}
		});	
		//当前标签的二级标签
		$("#twoLabelUl").find("li").each(function(i,obj){
			if($(this).hasClass("active")){
				labelObj.fatherSecondId=$(this).attr("id");
				labelObj.fatherSecondVal=$(this).text();
				Slstatus=$(this).attr("status");
			}
		});
		$("#"+objId).toggleClass("thirdActive");		
		//可开发项目，添加按钮点击事件
		if(labelObj.fatherSecondId=="61223947-c0da-4d0f-a1ff-429232d10a3c"){
			if(objId=="dpAddbtn"){
				var labeId=$("#threeLabelUl").find("li").eq("1").attr("id");
				$("#dpAddbtn").removeClass("thirdActive");
				var url = contextPath+'/KQDS_CostOrderAct/selectPriceListByLabeIdTwo.act?usercode='+usercode+'&parentId='+labelObj.fatherSecondId+'&parentName='+labelObj.fatherSecondVal+'&labeId='+labeId+'&labelLayerIndex=' + labelLayerIndex + '&status=1'; 
			   	parent.layer.open({
				  type: 2,
				  title: '患者标签',
				  shadeClose: true,
				  shade: 0.6,
				  area: ['1180px', '550px'],
				  scrollbar: true,
			      content: url					                	               
				});
			}
		}
		//可开发空间，添加按钮点击事件
		if(labelObj.fatherSecondId=="3d6b7239-c964-4714-8020-4dcc15ed1f5d"){
			if(objId=="dsAddbtn"){
				var labeId=$("#threeLabelUl").find("li").eq("1").attr("id");
				$("#dsAddbtn").removeClass("thirdActive");
				var url = contextPath+'/KQDS_CostOrderAct/selectPriceListByLabeIdTwo.act?usercode='+usercode+'&parentId='+labelObj.fatherSecondId+'&parentName='+labelObj.fatherSecondVal+'&labeId='+labeId+'&labelLayerIndex=' + labelLayerIndex +'&status=2';
				 parent.layer.open({
				        type: 2,
				        title: '患者标签',
				        shadeClose: true,
				        shade: 0.6,
				        area: ['1180px', '550px'],
				        scrollbar:true,
				        content: url
				 });

			}
		}
		
		//判断三级标签单选
		//console.log(Slstatus+"----------二级标签状态");
		if(Slstatus==1){
			//删除数组中当前二级分类下除了自身以外的所有标签数据
			for (var si = 0; si < labelArr.length; si++) {
				if(labelArr[si].fatherSecondId==labelObj.fatherSecondId){
					labelArr.splice(si,1);
				}
			}
			//标签样式判断
			$("#threeLabelUl").find("li").each(function(i,ele){
				$(ele).removeClass("thirdActive");
				if($(ele).attr("id")==objId){
					$("#threeLabelUl").find("li").eq(i).addClass("thirdActive");
				}
			});
			//消费标签判断
			if(labelObj.fatherId=='ad2eae81-310c-45f0-a667-8a8f2383b168'){
				$(window.parent.frames["tabIframe"].document).find("#expenseShow").find("li").each(function(i,ele){
					if(labelObj.fatherSecondId==$(ele).attr("slid")){
						$(ele).remove();
					}
				}); 
			}
			//需求标签判断
			if(labelObj.fatherId=='a5cb2fa0-952f-4e45-b5e9-c6be12f4baf0'){
				$(window.parent.frames["tabIframe"].document).find("#needShow").find("li").each(function(i,ele){
					if(labelObj.fatherSecondId==$(ele).attr("slid")){
						$(ele).remove();
					}
				}); 
			}
			//社会标签判断
			if(labelObj.fatherId=='13543c4d-f81e-4251-87a1-f07984022e9f'){
				$(window.parent.frames["tabIframe"].document).find("#societyShow").find("li").each(function(i,ele){
					if(labelObj.fatherSecondId==$(ele).attr("slid")){
						$(ele).remove();
					}
				}); 
			}
			//满意星级判断（更换ID）
			if(labelObj.fatherId=='61686ff1-2c0b-49d5-8df9-249c03457fb4'){
				$(window.parent.frames["tabIframe"].document).find("#satisfactionShow").find("li").each(function(i,ele){
					if(labelObj.fatherSecondId==$(ele).attr("slid")){
						$(ele).remove();
					}
				}); 
			}
		}
		
		if($("#"+objId).hasClass("thirdActive")){
			labelArr.push(labelObj);
			//消费标签向父页面传HTML拼接字符串
			if(labelObj.fatherId=="ad2eae81-310c-45f0-a667-8a8f2383b168"){
				var expenseRepeatIndex;
				var expenseAlreadyHtml=$(window.parent.frames["tabIframe"].document).find("#expenseShow").html();
				if(expenseAlreadyHtml.indexOf("li")!=-1){
					$(window.parent.frames["tabIframe"].document).find("#expenseShow").find("li").each(function(i,ele){
						if(labelObj.selfId==$(ele).attr("id")){
							layer.alert("此标签已经添加过！");
							expenseRepeatIndex=i;
							return false;
							/* $(window.parent.frames["tabIframe"].document).find("#expenseShow").find("li").eq(i).remove(); */
						}
					}); 
				}
				if(expenseRepeatIndex>=0){
				}else{
					expenseHtml=$(window.parent.frames["tabIframe"].document).find("#expenseShow").html()+'<li id='+labelObj.selfId+' slid='+labelObj.fatherSecondId+' slname='+labelObj.fatherSecondVal+' class="aa" value='+labelObj.selfVal+'>'+labelObj.selfVal+'<span onclick="cleatBtn(this)" id="cleatBtn" class="cleatBtn">X</span></li>';
					$(window.parent.frames["tabIframe"].document).find("#expenseShow").html(expenseHtml);
				}
			}
			//满意星级标签向父页面传HTML拼接字符串（更换ID）
			if(labelObj.fatherId=="61686ff1-2c0b-49d5-8df9-249c03457fb4"){
				var satisfactionRepeatIndex;
				var satisfactionAlreadyHtml=$(window.parent.frames["tabIframe"].document).find("#satisfactionShow").html();
				if(satisfactionAlreadyHtml.indexOf("li")!=-1){
					$(window.parent.frames["tabIframe"].document).find("#satisfactionShow").find("li").each(function(i,ele){
						if(labelObj.selfId==$(ele).attr("id")){
							layer.alert("此标签已经添加过！");
							satisfactionRepeatIndex=i;
							return false;
							/* $(window.parent.frames["tabIframe"].document).find("#expenseShow").find("li").eq(i).remove(); */
						}
					}); 
				}
				if(satisfactionRepeatIndex>=0){
				}else{
					expenseHtml=$(window.parent.frames["tabIframe"].document).find("#satisfactionShow").html()+'<li id='+labelObj.selfId+' slid='+labelObj.fatherSecondId+' slname='+labelObj.fatherSecondVal+' class="aa" value='+labelObj.selfVal+'>'+labelObj.selfVal+'<span onclick="cleatBtn(this)" id="cleatBtn" class="cleatBtn">X</span></li>';
					$(window.parent.frames["tabIframe"].document).find("#satisfactionShow").html(expenseHtml);
				}
			}
			//社会标签向父页面传HTML拼接字符串
			if(labelObj.fatherId=="13543c4d-f81e-4251-87a1-f07984022e9f"){
				var societyRepeatIndex;
				var societyAlreadyHtml=$(window.parent.frames["tabIframe"].document).find("#societyShow").html();
				if(societyAlreadyHtml.indexOf("li")!=-1){
					$(window.parent.frames["tabIframe"].document).find("#societyShow").find("li").each(function(i,ele){
						if(labelObj.selfId==$(ele).attr("id")){
							layer.alert("此标签已经添加过！");
							societyRepeatIndex=i;
							return false;
							/* $(window.parent.frames["tabIframe"].document).find("#societyShow").find("li").eq(i).remove(); */
						}
					}); 
				}
				if(societyRepeatIndex>=0){
				}else{
					societyHtml=$(window.parent.frames["tabIframe"].document).find("#societyShow").html()+'<li id='+labelObj.selfId+' slid='+labelObj.fatherSecondId+' slname='+labelObj.fatherSecondVal+' class="aa" value='+labelObj.selfVal+'>'+labelObj.selfVal+'<span onclick="cleatBtn(this)" id="cleatBtn" class="cleatBtn">X</span></li>';
					$(window.parent.frames["tabIframe"].document).find("#societyShow").html(societyHtml);
				
					//职业添加四级详情
					if(labelObj.fatherSecondId=="62aa427d-b590-4077-8b7b-4195201ec758"){
//	 					四级标签详情编辑
						  // var labelDetailObj={};//存储每一次输入的三级标签对象详情
						  //console.log(JSON.stringify(labelArr)+"----------初次判断标签详情数组");
						  var labelDetail;//存储已经添加过的标签详情
						//循环详情数组判断是否已经添加过
						  for (var j = 0; j < labelArr.length; j++) {
							  //console.log("当前标签id-------"+objId);
							  //console.log("循环标签id-------"+labelDetailArr[j].tid);
							  if(objId==labelArr[j].tid){
								  if($("#"+objId).hasClass("thirdActive")){
									  //console.log("存在选中样式");
									  labelDetail=labelArr[j].tDetail;//已经存储的详情
									  //console.log("已经存在此标签，详情为：---------"+labelDetail);
								  }else{
									  labelArr.splice(j,1);
									  //console.log("没有选中样式，删除后数组");
									  return;
								  }
								  
							  }else{
								  labelDetail="";
								  //console.log("不存在此标签-------------"+labelDetail);
							  } 
						}
						 layer.prompt({title: '请输入详情', formType: 2,value:labelDetail}, function(text, index){
							if(text){
								//console.log("进入标签详情数组存储");
								//如果此标签为单选，则清空之前存储的标签详情
								/* if(Slstatus==1){
									labelDetailArr=[];
								} */
								// labelDetailObj.tid=objId; //当前标签的id
								// labelDetailObj.tDetail=text; //当前标签添加的详情
								labelObj.selfDetailTitle=text;//当前标签添加的详情
								//labelDetailArr.push(labelDetailObj);
								societyHtml=societyAlreadyHtml+'<li id='+labelObj.selfId+' slid='+labelObj.fatherSecondId+' slname='+labelObj.fatherSecondVal+' class="aa" value='+labelObj.selfVal+' title='+text+'>'+labelObj.selfVal+'<span onclick="cleatBtn(this)" id="cleatBtn" class="cleatBtn">X</span></li>';
								$(window.parent.frames["tabIframe"].document).find("#societyShow").html(societyHtml);
							}
							//console.log(JSON.stringify(labelDetailArr)+"----------已存储标签详情2");
						    layer.close(index);
						    layer.msg('添加详情：'+text);

						  }); 
//	 					  标签详情划过展示
						  var tip_index = 0;
						  $(document).on('mouseenter', '#threeLabelUl .thirdActive', function(){
							  var mouseLabelId=$(this).attr("id");//当前划过的标签id
							  //循环详情数组判断是否已经添加过
							 for (var i = 0; i < labelArr.length; i++) {
								if(mouseLabelId==labelArr[i].selfId){
									var labelDetail=labelArr[i].selfDetailTitle;//已经存储的详情
									if(labelDetail){
										tip_index=layer.tips(labelDetail, this,{
									          tips: [2, 'pink'],
									          area: ['auto', 'auto'],
									          time: 30000
									    });
									}
								}
								
							}
						  }).on('mouseleave', '#threeLabelUl .thirdActive', function(){
						      layer.close(tip_index);
						  });
					}
					/********************************/
				
				}
			}
			//需求标签向父页面传HTML拼接字符串
			if(labelObj.fatherId=="a5cb2fa0-952f-4e45-b5e9-c6be12f4baf0"){
				var needRepeatIndex;
				var needAlreadyHtml=$(window.parent.frames["tabIframe"].document).find("#needShow").html();
				if(needAlreadyHtml.indexOf("li")!=-1){
					$(window.parent.frames["tabIframe"].document).find("#needShow").find("li").each(function(i,ele){
						if(labelObj.selfId==$(ele).attr("id")){
							layer.alert("此标签已经添加过！");
							needRepeatIndex=i;
							return false;
							/* $(window.parent.frames["tabIframe"].document).find("#needShow").find("li").eq(i).remove(); */
						}
					}); 
				}
				if(needRepeatIndex>=0){
				}else{
					if(labelObj.fatherSecondId=="5dd4d319-63f5-41af-8d4c-2e2e34a1954c"){ //可开发大项目按钮改变颜色
						needHtml=$(window.parent.frames["tabIframe"].document).find("#needShow").html()+'<li id='+labelObj.selfId+' slid='+labelObj.fatherSecondId+' slname='+labelObj.fatherSecondVal+' class="aa otherColor" value='+labelObj.selfVal+'>'+labelObj.selfVal+'<span onclick="cleatBtn(this)" id="cleatBtn" class="cleatBtn">X</span></li>';
					}else{
						needHtml=$(window.parent.frames["tabIframe"].document).find("#needShow").html()+'<li id='+labelObj.selfId+' slid='+labelObj.fatherSecondId+' slname='+labelObj.fatherSecondVal+' class="aa" value='+labelObj.selfVal+'>'+labelObj.selfVal+'<span onclick="cleatBtn(this)" id="cleatBtn" class="cleatBtn">X</span></li>';
					}
					// needHtml=$(window.parent.frames["tabIframe"].document).find("#needShow").html()+'<li id='+labelObj.selfId+' slid='+labelObj.fatherSecondId+' slname='+labelObj.fatherSecondVal+' class="aa" value='+labelObj.selfVal+'>'+labelObj.selfVal+'<span onclick="cleatBtn(this)" id="cleatBtn" class="cleatBtn">X</span></li>';
					$(window.parent.frames["tabIframe"].document).find("#needShow").html(needHtml);
				}
			}
		}else{
			for (var i = 0; i < labelArr.length; i++) {
				if(objId==labelArr[i].selfId){
					labelArr.splice(i,1);;
				}
			}
			//对比父页面是否有此标签，有则删除
			//消费标签
			if(labelObj.fatherId=="ad2eae81-310c-45f0-a667-8a8f2383b168"){
				$(window.parent.frames["tabIframe"].document).find("#expenseShow").find("li").each(function(i,obj){
					if(obj.id==objId){
						$(window.parent.frames["tabIframe"].document).find("#expenseShow").find("li").eq(i).remove();
					}
				});
			}
			//社会标签
			if(labelObj.fatherId=="13543c4d-f81e-4251-87a1-f07984022e9f"){
				$(window.parent.frames["tabIframe"].document).find("#societyShow").find("li").each(function(i,obj){
					if(obj.id==objId){
						$(window.parent.frames["tabIframe"].document).find("#societyShow").find("li").eq(i).remove();
					}
				});
			}
			//需求标签
			if(labelObj.fatherId=="a5cb2fa0-952f-4e45-b5e9-c6be12f4baf0"){
				$(window.parent.frames["tabIframe"].document).find("#needShow").find("li").each(function(i,obj){
					if(obj.id==objId){
						$(window.parent.frames["tabIframe"].document).find("#needShow").find("li").eq(i).remove();
						//判断当前点击的是可开发项目的三级标签，自身删除
						if($(obj).attr("slid")=="61223947-c0da-4d0f-a1ff-429232d10a3c"){
							$("#"+objId).remove();
						}
						if($(obj).attr("slid")=="3d6b7239-c964-4714-8020-4dcc15ed1f5d"){
							$("#"+objId).remove();
						}
					}
				});
			}
			
			
		}
	
	}
	
	

</script>
</body>
</html>
