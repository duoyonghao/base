<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	YZPerson person = SessionUtil.getLoginPerson(request);

	String type = request.getParameter("type"); // 0 标准 1 自用
	if (type == null) {
		type = "";
	}

	String id = request.getParameter("id"); //选项分类id
	if (id == null) {
		id = "";
	}
// 	String projectName = request.getParameter("projectName"); 
// 	if (projectName == null) {
// 		projectName = "";
// 	}
	String toothNum = request.getParameter("toothNum");
	if (toothNum == null) {
		toothNum = "";
	}
	String toothMore = request.getParameter("toothMore");
	if (toothMore == null) {
		toothMore = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>种植自用病历库</title>
<style type="text/css">
	#projectItems{
/* 		text-align: center; */
		margin:0 15%;
	}
	#projectItems ul li {
	    float: left;
	    width: 25%;
	    margin-bottom: 10px;
	    font-size: 14px;
	    font-family: "Microsoft YaHei";
	    color: #3f3f3f;
	    cursor: pointer;
	    -webkit-transition: all .3s;
	    transition: all .3s;
	}
	#projectItems ul li input{
		margin-right: 2px;
	}
	#projectItems button{
	   	font-size: 16px;
	   	line-height: 34px;
	   	background-color: #00A6C0;
	   	font-weight: normal;
	   	color: white;
	   	border: 0px;
	   	border-radius: 5px;
	   	padding: 0px 20px;
	   	letter-spacing: 1px;
	   	margin-left: 40%
	}

</style>
</head>
<body>
<div id="projectItems">
	<h3></h3>
	<ul style="min-height:90px;"></ul>
</div>
<script type="text/javascript">
	 var projectId = '<%=id%>';
// 当前牙位
	 var toothNum = '<%=toothNum%>';
//	多牙位数组处理
	var toothMore = '<%=toothMore%>';
 	var toothMoreArr=[];//传入牙位数组
 	var getdata=[];
 	for(var i=0;i<toothMore.length;i+=2){
 		toothMoreArr.push(toothMore.slice(i,i+2));
 	}//字符串转数组处理
	 $(function(){
		$("h3").text('牙位:'+toothNum); //牙位标题
		gettionData();
		getData(getdata);
		checkedItems(projectId,toothNum);
	 })

	function gettionData(){
		var url = contextPath + "/YZDictAct/getDiseaseByCode.act?id=bqfl67&code=zdqk594";
	    $.axse(url, null,
	    function(data) {
	    	 if(projectId=="toothLoose"){
	    		 getdata=[{dictCode:"ysd1",dictName:"Ⅰ°",},{dictCode:"ysd2",dictName:"Ⅱ°",},{dictCode:"ysd3",dictName: "Ⅲ°",}];
	    	 }else if(projectId=="toothCondition"){
	    		 getdata=data.conditionData;
	    	 }else if(projectId=="imageExamination"){
	    		 getdata=[{dictCode:"yxx14",dictName:"牙槽嵴",},{dictCode:"yxx15",dictName:"牙槽神经管",},{dictCode:"yxx16",dictName: "种植区域",},{dictCode:"yxx17", dictName:"Ⅰ类骨",},{dictCode:"yxx18",dictName:"Ⅱ类骨"},{dictCode:"yxx19",dictName:"Ⅲ类骨"},{dictCode:"yxx20",dictName:"Ⅳ类骨"}];
	    	 }else if(projectId=="medicalCertificate"){
	    		 getdata=data.certificateData;
	    	 }
	    },
	    function() {
	        layer.alert('查询出错！' );
	    });
	}

	function getData(data){
		for(var i=0;i<data.length;i++){
			var projecthtml="";
			projecthtml +="<li>";
			projecthtml += "<label><input type='checkbox' value="+data[i].dictCode+" parentid="+projectId+" tooth="+toothNum+">"+data[i].dictName+"</label>";
			projecthtml +="</li>";
			$('#projectItems').find('ul').append(projecthtml);
		}
	}

//循环得到item选择的当前回显选中状态
function checkedItems(projectId,toothNum){
	if(projectId=="toothLoose"){//牙松动布局不同
		$(window.parent.document).find("."+projectId+"NextBox").nextAll().find("td:odd").each(function(j,el){
			var thi=this;
			if($(thi).find("div").find("span").length>0){
				var checkedtoothArr=[];
				$(thi).find("div").find("span").each(function(k,e){
						checkedtoothArr.push($(this).attr("id"));
		 				for(var i=0;i<checkedtoothArr.length;i++){
		 					if(checkedtoothArr[i]==toothNum){
		 						var itmecheckedArr=[];
		 						var itmechecked=$(thi).find("div").attr("id");
		 						itmecheckedArr.push(itmechecked);//通过牙位查归属牙问题
		 						for(var j=0;j<itmecheckedArr.length;j++){
		 	 						$("#projectItems").find("ul label").each(function(j,el){
		 	 							$("#projectItems").find("ul label").find("input[value="+itmecheckedArr[j]+"]").attr('checked',true);
		 							})
		 						}
		 					}
		 				}
				})
			}
		})
	}else{//牙松动布局相同
		 $(window.parent.document).find("#"+projectId+"Box").find("tr td:even").each(function(j,el){
			var thi=this;
			if($(thi).find("div").find("span").length>0){
					var checkedtoothArr=[];
					$(thi).find("div").find("span").each(function(k,e){
						checkedtoothArr.push($(this).attr("id"));
			 				for(var i=0;i<checkedtoothArr.length;i++){
			 					if(checkedtoothArr[i]==toothNum){
			 						var itmecheckedArr=[];
			 						var itmechecked=$(thi).find("div").attr("id");
			 						itmecheckedArr.push(itmechecked);//通过牙位查归属牙问题
			 						for(var j=0;j<itmecheckedArr.length;j++){
			 	 						$("#projectItems").find("ul label").each(function(j,el){
			 	 							$("#projectItems").find("ul label").find("input[value="+itmecheckedArr[j]+"]").attr('checked',true);
			 							})
			 						}
			 					}
			 				}
					})
			}
		})
	}
}

// 点击牙位赋值给父级td
	$('#projectItems').on('click', "input[type='checkbox']",function() {
		var projectIdClick=$(this).attr("parentid"); //传入4大类别问题id
		var itemIdClick=$(this).val();//当前点击的牙位
		var that =this;
		if(projectIdClick=="toothLoose"){//牙松动布局不同---赋值			
			if($(that).is(':checked')){
				var $findBox=$(window.parent.document).find(".toothLooseNextBox").nextAll().find("td:even");//ya'song'd
				if($(that).attr('staus')=='unusual'){
					$("#projectItems").find('input[staus="usual"]').prop("checked",false).attr("disabled","disabled").css("cursor","no-drop");
				}
				$findBox.each(function(j,el){
					if($(this).find("span").attr("id")==itemIdClick){
						var toothLoose_span=$(this).next().children("div").find("span");//父级选择框的值
						if(toothLoose_span.length<32){
						var toothLoose_div = $(this).next().find("div");
						var already=toothLoose_div.text().split(',');
							for(var i=0;i<toothMoreArr.length;i++){
								for(var j=0;j<already.length;j++){
									if(already[j]==toothMoreArr[i]){
										toothLoose_div.find("span[id="+already[j]+"]").remove();//删除选择已存在的重复牙位
									}
								}
								toothLoose_div.append('<span id='+toothMoreArr[i]+' itmid='+itemIdClick+'>'+toothMoreArr[i]+","+'</span>');
							}
							parent.checkedTooth(projectIdClick);
						}else{
							layer.alert("牙位数量已超出！");
						}
					}
				})
			}else{
				$("#projectItems").find('input[staus="usual"]').removeAttr("disabled").css("cursor","auto");
				$(window.parent.document).find(".toothLooseNextBox").nextAll().find("td:even").each(function(j,el){
					var th=this;
					var itemId=$(th).find("span").attr("id");
					var $td_div=$(th).next().find("div");
					var $td_span=$td_div.find("span");//父级选择框的值
					if(itemId!=undefined&&itemId==itemIdClick){
						$td_div.each(function(y,e){
							var checkedtoothArr=$td_div.text().split(',');
							for(var i=0;i<checkedtoothArr.length;i++){
								if(toothNum==checkedtoothArr[i]){
									$td_div.find("span[id="+toothNum+"]").remove();
									parent.checkedTooth(projectIdClick,"10");
								}
							}
						})
					}
				})
			}
		}else{//牙松动布局相同的	
			if($(that).is(':checked')){
				$(window.parent.document).find("#"+projectIdClick+"Box").find("tr td:odd").each(function(j,el){
					if($(this).find("span").attr("id")==itemIdClick){
						var td_span_length=$(this).prev().children("div").find("span").length;
						if(td_span_length<32){
							var toothLoose_div = $(this).prev().find("div");
							var already=toothLoose_div.text().split(',');
								for(var i=0;i<toothMoreArr.length;i++){
									for(var j=0;j<already.length;j++){
										if(already[j]==toothMoreArr[i]){
											toothLoose_div.find("span[id="+already[j]+"]").remove();//删除选择已存在的重复牙位
										}
									}
									toothLoose_div.append('<span id='+toothMoreArr[i]+' itmid='+itemIdClick+'>'+toothMoreArr[i]+","+'</span>');
								}
							parent.checkedTooth(projectIdClick);
						}else{
							layer.alert("牙位数量已超出！");
						}
					}
				})
			}else{
				$(window.parent.document).find("#"+projectIdClick+"Box").find("tr td:even").each(function(j,el){
					var th=this;
					var $td_div=$(th).find("div");
					var $td_span=$td_div.find("span");//选择的牙位元素
					var itemId=$td_span.attr("itmid");//选中牙位的itemid
					if(itemId!=undefined&&itemId==itemIdClick){
						$td_div.each(function(y,e){
							var checkedtoothArr=$td_div.text().split(',');
							for(var i=0;i<checkedtoothArr.length;i++){
								if(toothNum==checkedtoothArr[i]){
									$td_div.find("span[id="+toothNum+"]").remove();
									parent.checkedTooth(projectIdClick,"10");
								}
							}
						})
					}

				})
			}
		}

	})

</script>
</body>
</html>
