<%@page import="com.kqds.entity.sys.YZPerson"%>
<%@page import="com.kqds.util.sys.SessionUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	String deptId = person.getDeptId();
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
 <style>
     table{
         text-align: center;
     }
     .size{
         width: 95%;
         height: 90%;
         text-align: center;
         border: 1px solid blue;
     }
     td{
         word-wrap: break-word
     }
     /* 序号 */
     .sequenceWidth{
         width: 5%;
     }
     /* 编号 */
     .numberWidth{
         width: 10%;
     }
     /* 回访计划名称 */
     .planNameWidth{
         width: 25%;
     }
     /* 说明 */
     .explainWidth{
     	width:30%;
     }
     
     /* 操作 */
     .operateWidth{
         width:15%;
     }
/*      小计划样式           	*/
       /* 跟进天数 */ 
      .daysWidth{ 
          width: 10%; 
      } 
      /* 回访类型 */ 
      .visitTypeWidth{ 
          width: 15%; 
      } 
      /* 是否强制跟进 */ 
      .followWidth{ 
          width:12%; 
      } 
      /* 回访部门 */ 
      .deptWidth{ 
          width:10%;
          display: none; 
      } 
      .deptSelectWidth{
      	width: 100%
      }
       /* 回访人员 */ 
      .hfperson_Width{ 
          width:10%; 
          display: none;
      } 
        /* 受理类型 */ 
      .sltype_Width{ 
          width:10%; 
      } 
      /* 说明 */ 
      .smallexplainWidth{ 
      	width:18%; 
      } 
       /*小计划 操作 */
     .smalloperateWidth{
         width:7%;
     }
     #planContiner{
     	width:100%;
/*      	border:1px solid red; */
     }
     /* 大计划删除 */
     .bigPlanDel{
     	margin-right: 5px;
     }
     .smallPlanDel{
     	margin-right: 5px;
     }
     /* table */
     table{
     	width:100%;
	    font-size: 13px;
	    font-weight: normal;
	    margin-bottom: 5px;
	    border-left:1px solid #ddd;
     }
     table thead{
     	width:100%;
     	border:1px solid blue;
     	background-color: #00A6C0;
	    border:1px solid #ddd;
	    color: white;
     }
     table thead th{
     	border-right:1px solid #ddd;
     	line-height: 18px;
    	padding: 8px;
     }
     table tbody td{
     	border-right:1px solid #ddd;
     	border-bottom:1px solid #ddd;
     	padding: 2px;
     }
     /* 大计划选中行背景颜色 */
     #bigPlanTbody tr.trActive{
     	background-color:#A9ECC7; 
     }
     .btn{
    	display: inline-block;
	    box-sizing: border-box;
	    height: 24px;
	    line-height: 24px;
	    background: white;
	    color: #00a6c0;
	    outline: none;
	    padding: 0 8px;
	    border: 1px solid #00a6c0;
	    border-radius: 3px;
	    margin-right: 3px;
	    text-decoration: none;
	    cursor: pointer;
	    text-align: center;
	    font-size: 12px;
     }
     .planBtn{
     	display: inline-block;
     	background-color: #00a6c0;
     	border: 1px solid #00a6c0;
     	color: white;
	    border-radius: 5px;
	    padding: 3px 10px;
	    margin: 0 auto;
	    outline: none;
     }
     .bigPlanAdd{
     	display: inline-block;
     }
     .smallPlanAdd{
     	display: inline-block;
     }
     .smallPlanSave{
      	background-color: #00A6C0;
      	border:1px solid #00A6C0;
      	color:white;
      }
      /*关键设置 tbody出现滚动条*/
     #bigPlan tbody {
         display: block;
         height: 200px;
         overflow-y: scroll;
     }
                    
     #bigPlan thead,
     tbody tr {
         display: table;
         width: 100%;
         table-layout: fixed;
     }
     /*关键设置：滚动条默认宽度是16px 将thead的宽度减16px*/
     #bigPlan thead {
         width: calc( 100% - 20px)
     }
      
        /*关键设置 tbody出现滚动条*/
     #smallPlanList tbody {
         display: block;
         height: 200px;
         overflow-y: scroll;
     }
                    
     #smallPlanList thead,
     tbody tr {
          display: table;
          width: 100%; 
          table-layout: fixed; 
     }
     /*关键设置：滚动条默认宽度是16px 将thead的宽度减16px*/
     #smallPlanList thead {
         width: calc( 100% - 20px)
      }
      /* 禁用按钮 */
      .disAbledBtn{
     	background-color: #aca5a5;
     	border: 1px solid #aca5a5;
     	cursor: no-drop;
     	color: white;
     }
     .startBtn{
     	background-color: #00a6c0;
     	border: 1px solid #00a6c0;
     	cursor: auto;
     }
     .startBtnTwo{
     	background-color: white;
     	border: 1px solid #00a6c0;
     	color:#00a6c0;
     	cursor: auto;
     }
	#smallPlanTbody tr td select {
	  width: 100%;
	  height: 100%;
	/*   border: 1px solid red; */
	 } 
	.out_div{
	position:relative;
	height: 100%;
	}
	.out_span{
	 margin-left:0px;
	 width:18px;
	 overflow:hidden;
	}
	.fu_input{
	 width:82%;
 	 height:68%; 
	 position:absolute;
	 left:0px;
	 }
    .name:empty:before{
        content: attr(placeholder); 
        /*content: 'this is content';*/
        color:#ccc;
    }
    .name:focus:before{
        content:none;
    }
    .explan:empty:before{
        content: attr(placeholder);
        color:#ccc;
    }
    .explan:focus:before{
        content:none;
    }
    .spExplain:empty:before{
        content: attr(placeholder);
        color:#ccc;
    }
    .spExplain:focus:before{
        content:none;
    }
</style>
<body>
<div id="planContiner">
        <table id="bigPlan" border="0" cellspacing='0' style="table-layout: fixed;">
             <thead>
                 <tr>
                 	<th class="bigPlanID" hidden>ID</th>
                 	<th class="sequenceWidth"></th>
                    <th class="sequenceWidth">序号</th>
                    <th class="numberWidth">部门</th>
                    <th class="planNameWidth">回访计划名称</th>
                    <th class="explainWidth">说明</th>
                    <th class="operateWidth">操作</th>
                 </tr>
             </thead>  
             <tbody id="bigPlanTbody" style="table-layout: fixed;border: 1px solid #ddd;">
             </tbody>   
        </table>
        <div style="text-align: center;" id="bigPlan">
        <button class="planBtn bigPlanAdd hide" onclick="addBigPlan()">添加计划模板</button>
        <button class="planBtn hide" onclick="bpBatchDel();">删除计划模板</button>
        <button class="planBtn hide" onclick="updateBigPlan(this);">修改计划模板</button>
        </div>
        
        <!-- <button class="btn bigPlanSave"  onclick="save()">保存</button> -->
        <table id="smallPlanList" border="0" cellspacing='0' style="table-layout: fixed;margin-top: 10px;">
             <thead>
                 <tr>
                 	 <th class="smallPlanID" hidden>ID</th>
                 	 <th class="parentPlanID" hidden>父计划ID</th>
                 	 <th style="width:5%;"></th>
                     <th class="daysWidth">跟进天数（天）</th>
                     <th class="visitTypeWidth">回访类型</th>
                     <th class="followWidth">是否设为操作员强制跟进</th>
                     <th class="deptWidth">回访部门</th>
                     <th class="hfperson_Width">回访人员</th>
                     <th class="sltype_Width">受理类型</th>
                     <th class="smallexplainWidth">回访要点</th>
                     <th class="smalloperateWidth">操作</th>
                 </tr>
             </thead>  
             <tbody id="smallPlanTbody" style="table-layout: fixed;border: 1px solid #ddd;">
             </tbody>   
        </table>
        <div class="smallPlanBtn" style="text-align: center;">
        <button class="planBtn smallPlanAdd disAbledBtn hide"  disabled="disabled"  onclick="addSmallPlan()">添加计划</button>
        <button class="planBtn smallPlanSave hide"  onclick="saveSmallPlan()">保存计划</button>
        <button class="planBtn hide" onclick="spBatchDel();">删除计划</button>
        </div>
    </div> 
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/kqds/kqds_request.js"></script> <!-- 请求封装方法 -->
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/kqds/kqds_select.js"></script>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var sectionName;
var listbutton = window.parent.parent.listbutton;
var deptId;
    $(function () {
    	initSelectDeptByPerson(); //查询登录部门名称、ID
    	initBigPlan(); //大计划初始化
    	//initDept();//大部门初始化
    	//获取当前页面所有按钮 
     	getButtonPower();   
    	
    });

//  判断登录部门
function initSelectDeptByPerson() {	
	var url = contextPath + '/YZPersonAct/findDeptnameByPerson.act';
    $.axse(url, null,
    function(r) {
    	//console.log(JSON.stringify(r)+"---------部门信息");
    	deptId=r.seq_id;
    	sectionName = r.dept_name;
     	//console.log(sectionName+"-----------"+deptId);
    },
    function() {
        layer.alert('请求失败' );
    });
}
 
/*  ----------------------------------------------- */
//大计划初始化
 	function initBigPlan(){
	//console.log(deptId+"--------大计划初始化部门ID");
 		 var url = contextPath+'/HUDH_AddVisitAct/findTemplate.act';
 		$.axse(url,null, 
 			function(data){
//   		 	console.log(JSON.stringify(data)+"-----大计划初始化");
// 		 		if(data!=null){
// 		 			if(data.length>0){
		 				$('#bigPlanTbody').html("");
		 		 	     for (var i = 0; i < data.length; i++) {
		 		 	    	var html='';
		 		 	         var dataPlan=data[i];
		 		 	         //console.log(JSON.stringify(dataPlan)+"-----");		 		 	        
		 		 	         html+='<tr>'+
		 		 	       			 '<td class="bigPlanID" hidden>'+dataPlan.seqId+'</td>'+
		 		 	       			 '<td class="cbox sequenceWidth"><input type="checkbox"/></td>'+
		 		 	                 '<td class="serial sequenceWidth"><span>'+(i+1)+'</span></td>'+
// 		 		 	                 '<td class="dept numberWidth" deptid='+deptId+'>'+sectionName+'</td>'+
		 		 	               	 '<td class="dept numberWidth"><select class="hfdept select2 deptSelectWidth" name="hfdept" id="hfdept'+i+'"></td>'+
		 		 	                 '<td class="name planNameWidth" >'+dataPlan.planname+'</td>'+
		 		 	                 '<td class="explan explainWidth">'+dataPlan.explanation+'</td>';
		 		 	                 if(dataPlan.status==0){
		 		 	                	html+='<td class="operateWidth"><button class="btn bigPlanDel" href="javascript:;" onclick="disableBigPlan(this,'+dataPlan.status+')">禁用</button>';
		 		 	                 }else{
		 		 	                	html+='<td class="operateWidth"><button class="btn bigPlanDel" href="javascript:;" onclick="disableBigPlan(this,'+dataPlan.status+')">启用</button>';
		 		 	                 }
		 		 	               	html+='<button class="btn bigPlanSave disAbledBtn" disabled href="javascript:;" onclick="bigPlanSave(this)">保存</button></td>'+
		 		 	                 '</tr>';    
		 		 	                 
		 		 	               $("#bigPlanTbody").append(html);
		 	 		 		 	   var hfdept=$("#bigPlanTbody").find("tr").eq(i).find("td.dept").find("select"); // 当前所在行的部门select下拉框
		 	 		 			   initDept(hfdept,dataPlan.hfdept);//大计划部门初始化            
		 		 	     }
// 		 			}
// 		 		}
           },
			function(){
         	    layer.alert('查询失败！' );
			}
     	);
 	}
 	
 	//小计划初始化
  	function initSmallPlan(parentSeqId){
  		$("#smallPlanTbody").html(""); // 先清空小计划数据
  		var url = contextPath+"/HUDH_AddVisitAct/findvisitPlanTemplate.act";
  		parms={"managarId":parentSeqId};
   		$.axse(url,parms, 
   			function(data){
     		  //console.log(JSON.stringify(data)+"-----小计划初始化数据");
	   		 if(data[0]==null){
	   			addSmallPlan(parentSeqId);
	   			 return;
	   		 }
  		 		if(data!=null){
  		 			if(data.length>0){
  		 				$('#smallPlanTbody').html("");
  		 		 	 	 //var html='';
  		 		 	  	 for (var i = 0; i < data.length; i++) {
  		 		 	  		 var html='';
	  		 		         var dataPlan=data[i];
	  		 		         html+='<tr>'+
	  		 		         		 '<td class="smallPlanID" hidden>'+dataPlan.seqId+'</td>'+
	  		 		         		 '<td class="parentPlanID" hidden>'+parentSeqId+'</td>'+
	  		 		         		 '<td class="spcbox" style="width:5%;"><input type="checkbox"/></td>'+
	  		 		                 '<td class="spDayNum daysWidth" contenteditable="true">'+dataPlan.plandays+'</td>'+		  		 		            
	  		 		                 '<td class="spType visitTypeWidth">'+
	//   		 		              	'<div class="out_div">'+
	// 	  								'<span class="out_span">'+
			  							'<select class="select2" tig="HFFL" name="hffl" id="hffl'+i+'" onchange=""></select>'+
	// 		  							'</span>'+
	// 		  				            '<input class="fu_input" value=""></input>'+
	// 		  				            '</div>'+
			  						 '</td>'+
	  		 		                 '<td class="spFollow followWidth">';
	  		 		            //初始化是否强制跟进
 								if(dataPlan.ismandatory=="true"){
 									html+='<input checked type="checkbox"/>';
 								}else{
 									html+='<input type="checkbox"/>';
 								}
  		 		              	 	
	 							html+='</td>'+
  		 		                 '<td class="spdept deptWidth"><select class="hfdept select2" name="hfdept" id="hfdept" onchange="getVisitPersonSelect(this);"></select></td>'+
  		 		                 '<td class="spPerson hfperson_Width"><select class="select2 dict" name="hfperson" id="hfperson"><option value="">请选择</option></select></td>'+
  		 		              	 '<td class="spSLType sltype_Width"><select tig="SLLX" id="shouli'+i+'"></select></td>'+
  		 		                 '<td class="spExplain smallexplainWidth" contenteditable="true">'+dataPlan.remark+'</td>'+
  		 		                 '<td class="smalloperateWidth">'+ 
//   		 		                  '<button class="btn bigPlanSave" href="javascript:;" onclick="smallPlanSave(this)">保存</button>'
  		 		                '</td>'+ 
  		 		                '</tr>' ;
								$("#smallPlanTbody").append(html);
								var hffl=$("#smallPlanTbody").find("tr").eq(i).find("td.spType").find("select"); // 当前所在行的回访类型select下拉框
								initVisitType('triggerChange',hffl,dataPlan.visittype); // 初始化回访类型
								//initVisitType('triggerChange',"hffl"+i,dataPlan.visittype); // 初始化回访类型
								var sllx=$("#smallPlanTbody").find("tr").eq(i).find("td.spSLType").find("select"); // 当前所在行的受理类型select下拉框
								initVisitType('triggerChange',sllx,dataPlan.accepttype); // 初始化受理类型
								//initVisitType('triggerChange',"shouli"+i,dataPlan.accepttype); // 初始化受理类型
  		 		             }
  		 		        //$("#smallPlanTbody").append(html);
  		 			}
  		 		}
             },
  			function(){
           	    layer.alert('查询统计失败！' );
  			}
       	);
   		//initDictSelectByClassValue('triggerChange',"68"); // 咨询项目、受理类型、受理工具
   		//initDictSelectByClassValue('triggerChange'); // 咨询项目、受理类型、受理工具
  		//initDictSelectByClass(); // 回访分类
 	}
 	
  	/**
	 * 初始化回访类型、受理类型
	 * 参数：operFlag(自动触发事件) obj(当前下拉框ID) val(当前下拉框有无赋值)
	 */
  	function initVisitType(operFlag,obj,val) {
       	 //var dict = $("#"+obj);
       	 var dict = $(obj);
         var type = dict.attr("tig");
         var url = contextPath + "/YZDictAct/getListByParentCode.act?parentCode=" + type;
         $.axse(url, null,
         function(data) {
             var list = data.list;
             if (list != null && list.length > 0) {
                 dict.empty();
                 dict.append("<option value=''>请选择</option>");
                 for (var j = 0; j < list.length; j++) {
                     var optionStr = list[j];
                     dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                 }
             }
             if(val){
            	 dict.val(val); // 初始化有值赋值
             }/* else{
            	 dict.val("");
             } */
             if ('triggerChange' == operFlag) { // 触发Onchange事件 #### add by yangsen 
                 $(dict).trigger('change'); // 和 连锁代码片段的 $("#organization").change 配合使用
             }
         },
         function() {
             layer.alert('查询出错！'  );
         });
  	}
 	
 	
 	//大计划批量删除
 	function bpBatchDel(){
 		var bpCbLength=$("#bigPlanTbody").find("tr").find("td.cbox").find("input:checked").length; //checkbox选中个数
 		//console.log(bpCbLength+"----------");
	    if(bpCbLength<=0){
	    	layer.alert("请先选中要删除的计划！");
	    	return;
	    }else{
	    	// 批量删除
	    	var bpDelArr=[]; //批量删除的大计划ID数组
	     	$("#bigPlanTbody").find("tr").each(function(i,obj){
	     		if($(this).find("td.cbox").find("input").is(":checked")){
	     			//console.log(i+"---------当前选中行下标")
	     			var parentBpId=$(this).find("td.bigPlanID").text(); //当前修改大计划的ID
	     			bpDelArr.push(parentBpId);
	     		}
	     	});
	     	//console.log(JSON.stringify(bpDelArr)+"------------批量删除ID");
	     	var params = {
	     			bpDelArr:JSON.stringify(bpDelArr)
		        };
		        var url = contextPath + "/HUDH_AddVisitAct/deleteManagar.act";
		        $.axse(url, params,
		          	     function(r) {
		          	         if (r.retState == "0") {
		          	       			layer.open( {
		          	       				content: '删除成功!',
		          	       				closeBtn: 0,
		          	       				end:function(){
		          	       					location.reload();
		          	       				}
		          	       			});
		          	       			return true;
		          	       		}else{
			          	       		layer.open( {
			      	       				content: '删除失败!',
			      	       				closeBtn: 0
			      	       			});
			      	       			return false;
		          	       	 }
		      	        });
	    }
 	}
 	
 	//小计划批量删除
 	function spBatchDel(){
 		var spCbLength=$("#smallPlanTbody").find("tr").find("td.spcbox").find("input:checked").length; //checkbox选中个数
 		//console.log(spCbLength+"----------");
	    if(spCbLength<=0){
	    	layer.alert("请先选中要删除的计划！");
	    	return;
	    }else{
	    	// 批量删除
	    	var spDelArr=[]; //批量删除的大计划ID数组
	     	$("#smallPlanTbody").find("tr").each(function(i,obj){
	     		if($(this).find("td.spcbox").find("input").is(":checked")){
	     			//console.log(i+"---------当前选中行下标")
	     			var spId=$(this).find("td.smallPlanID").text(); //当前修改大计划的ID
	     			spDelArr.push(spId);
	     		}
	     	});
	     	//console.log(JSON.stringify(spDelArr)+"------------批量删除ID");
	     	var params = {
	     			spDelArr:JSON.stringify(spDelArr)
		        };
		        var url = contextPath + "/HUDH_AddVisitAct/deleteManagarPlan.act";
		        $.axse(url, params,
		          	     function(r) {
		          	         if (r.retState == "0") {
		          	       			layer.open({
		          	       				content: '删除成功!',
		          	       				closeBtn: 0,
		          	       				end:function(){
		          	       					//location.reload();
		          	       					 //得到父级id
									 		 var parentSeqId="";
									 		 var bigPlanTr=$("#bigPlanTbody").find("tr");
										     for (var i = 0; i < bigPlanTr.length; i++) {
											      if(bigPlanTr.eq(i).hasClass("trActive")){
											    	  parentSeqId=bigPlanTr.eq(i).find("td.bigPlanID").text();
											      }
										  	}
		          	       					initSmallPlan(parentSeqId);
		          	       				}
		          	       			})
		          	       			return true;
		          	       		}else{
			          	       		layer.open({
			      	       				content: '删除失败!',
			      	       				closeBtn: 0
			      	       			})
			      	       			return false;
		          	       	 }
		      	        });
	    }
 	}
 	
 	//大计划修改
 	function updateBigPlan(thi){
 		var bpCbLength=$("#bigPlanTbody").find("tr").find("td.cbox").find("input:checked").length; //checkbox选中个数
 		//console.log(bpCbLength+"----------");
	    if(bpCbLength==0){
	    	layer.alert("请先选中一条计划！");
	    	return;
	    }else if(bpCbLength>1){
	    	layer.alert("只能修改一条计划！");
	    	return;
	    }else{
	    	//单条计划修改
	     	$("#bigPlanTbody").find("tr").each(function(i,obj){
	     		if($(this).find("td.cbox").find("input").is(":checked")){
	     			//console.log(i+"---------当前选中行下标")
	     			$(this).find("td.name").attr("contenteditable","true"); //计划名称
	     			$(this).find("td.explan").attr("contenteditable","true"); //计划说明
	     			$(this).find("td.operateWidth").find(".bigPlanSave").removeAttr("disabled").removeClass("disAbledBtn").addClass("startBtnTwo");
	     		}
	     	});
	    }
 	}
 	
 	//小计划批量添加
 	function saveSmallPlan(){
 		var smallPlanArr=[];
 		$("#smallPlanTbody").find("tr").each(function(i,obj){
 			var smallPlanObj={};
 			smallPlanObj.managarId=$(this).find("td.parentPlanID").text(); //父计划ID
 			smallPlanObj.SEQ_ID=$(this).find("td.smallPlanID").text(); //小计划ID
 			smallPlanObj.plandays=$(this).find("td.spDayNum").text(); //跟进天数
 			smallPlanObj.visittype=$(this).find("td.spType").find("option:selected").val(); //回访类型
			smallPlanObj.ismandatory=$(this).find("td.spFollow").find("input[type='checkbox']").is(':checked'); //是否设为操作员强制跟进
 			smallPlanObj.hfdept=$(this).find("td.spdept").find("option:selected").text();//回访部门
			smallPlanObj.visitstaff=$(this).find("td.spPerson").find("option:selected").text();//回访人员
 			smallPlanObj.accepttype=$(this).find("td.spSLType").find("option:selected").val(); //受理类型
 			smallPlanObj.remark=$(this).find("td.spExplain").text(); //说明
 			smallPlanArr.push(smallPlanObj);
 		});
 		//console.log(JSON.stringify(smallPlanArr)+"--------小计划批量保存");
 		//参数验证
	 		for (var i = 0; i < smallPlanArr.length; i++) {
// 	 			console.log(JSON.stringify(smallPlanArr[i])+"--------smallPlanArr");
	 			 var date=smallPlanArr[i].plandays;	
	 			 var visittype=smallPlanArr[i].visittype;
	 			 var accepttype=smallPlanArr[i].accepttype;
// 	 			 var remark=smallPlanArr[i].remark;
				 if(!date){
					 layer.alert("请填写跟进天数！");
					 return false;
				 }
				 if(isNaN(date)){
			         layer.alert("跟进天数必须为数字！");
			         return false;
				 }
 				 if(!(/(^[1-9]\d*$)/.test(date))){
 					 layer.alert("跟进天数必须为正整数！");
 					return false;
 				 } 
 				 if(visittype==""){
 					layer.alert("请填写回访类型！");
					 return false;
 				 }
 				 if(accepttype==""){
 					layer.alert("请填写受理类型！");
					 return false;
 				 }
//  				 if(remark==""){
//  					layer.alert("请填写回访要点！");
// 					 return false;
//  				 }
			}	
//  		return;
 		 	var vistplantemplate = JSON.stringify(smallPlanArr);
	        vistplantemplate = encodeURIComponent(vistplantemplate);
	        var params = {
	        	vistplantemplate:vistplantemplate
	        };
	        var url = contextPath + "/HUDH_AddVisitAct/addVisitPlanTemplate.act";
	        $.axse(url, params,
	          	     function(r) {	        	
	          	         if (r.retState == "0") {
	          	       			layer.open( {
	          	       				content: '新增小计划模板成功!',
	          	       				closeBtn: 0,
	          	       				end:function(){
	          	       					//location.reload();
	          	       					//得到父级id
								 		 var parentSeqId="";
								 		 var bigPlanTr=$("#bigPlanTbody").find("tr");
									     for (var i = 0; i < bigPlanTr.length; i++) {
										      if(bigPlanTr.eq(i).hasClass("trActive")){
										    	  parentSeqId=bigPlanTr.eq(i).find("td.bigPlanID").text();
										      }
									  	}
	          	       					initSmallPlan(parentSeqId);
	          	       				}
	          	       			})
	          	       			return true;
	          	       		}else{
		          	       		layer.open({
		      	       				content: '新增计划模板失败!',
		      	       				closeBtn: 0
		      	       			})
		      	       			return false;
	          	       	 }
	      	        });
 		
 	}
     // 大计划增加按钮事件
     function addBigPlan(){    	 
//     	console.log("大计划增加事件")
		var trLength=$("#bigPlanTbody").find("tr").length;
		var appendHtml="";
		appendHtml+="<tr id='temp' class='temp'>";
		appendHtml+="<td class='bigPlanID' hidden></td>";
		appendHtml+='<td class="cbox sequenceWidth"><input type="checkbox"/></td>';
		appendHtml+="<td class='serial sequenceWidth'>"+(trLength+1)+"</td>";
		appendHtml+="<td class='dept numberWidth'><select class='hfdept select2 deptSelectWidth' name='hfdept' id='hfdept"+trLength+"'></td>";
		appendHtml+="<td class='name planNameWidth'contenteditable='true' placeholder='请输入自定义计划名称'></td>";
		appendHtml+="<td class='explan explainWidth' contenteditable='true' placeholder='请阐述自定义计划名称说明'></td>";
		appendHtml+="<td class='operateWidth'>";
		appendHtml+='<button class="btn bigPlanDel" href="javascript:;" onclick="bigPlanDel(this)">删除</button>';
		appendHtml+='<button class="btn bigPlanSave" href="javascript:;" onclick="bigPlanSave(this)">保存</button>';
		appendHtml+="</td>";
		appendHtml+="</tr>";
		$("#bigPlanTbody").append(appendHtml);
		var hfdept=$("#bigPlanTbody").find("tr").eq(trLength).find("td.dept").find("select"); // 当前所在行的部门select下拉框
		initDept(hfdept,deptId);//大计划部门初始化    
		document.getElementById('bigPlanTbody').scrollTop = document.getElementById('bigPlanTbody').scrollHeight; //设置滚动条位置
		//console.log(document.getElementById('bigPlanTbody').scrollHeight+"-----------滚动高度");
	}
     //禁用大计划
     function disableBigPlan(thi,status){
    	 var disabledBpId=$(thi).parent().parent().find("td.bigPlanID").text();//当前禁用大计划ID
    	 var params = {
 	        	bpId:disabledBpId,
 	        	status:status
         };
         var url = contextPath + "/HUDH_AddVisitAct/updateTemplateStatus.act";
 	        $.axse(url, params,
 	          	     function(r) {	        	
 	          	         if (r.retState == "0") {
 	          	        	 	if(status==0){
	 	          	        	 	layer.open({
	 	          	       				content: '禁用成功!',
	 	          	       				closeBtn: 0,
	 	          	       				end:function(){
	 	          	       					location.reload();
	 	          	       				}
	 	          	       			});
 	          	        	 	}else{
	 	          	        	 	layer.open({
	 	          	       				content: '启用成功!',
	 	          	       				closeBtn: 0,
	 	          	       				end:function(){
	 	          	       					location.reload();
	 	          	       				}
	 	          	       			});
 	          	        	 	}
 	          	       			return true;
 	          	       		}else{
	 	          	       		if(status==0){
		 	          	       		layer.open({
		 	      	       				content: '禁用失败!',
		 	      	       				closeBtn: 0
		 	      	       			});
	 	          	       		}else{
		 	          	       		layer.open({
		 	      	       				content: '启用失败!',
		 	      	       				closeBtn: 0
		 	      	       			});
	 	          	       		}
 	      	       				return false;
 	          	       	 }
 	      	        });
         $.axse(url, params,function(r) {
      	       
         },function(){
        	 if(status==0){
        	    layer.alert('禁用失败！' ); 
        	 }else{
        		layer.alert('启用失败！' ); 
        	 }
		});
    	 
     }

     // 大计划前端删除 
	function bigPlanDel(thisbutton){
//  		$(thisbutton).parent().parent().remove();
		layer.confirm("确认删除吗？",{
			btn: ['确认', '取消']
		}, function (index) {
			//确认
			$(thisbutton).parent().parent().remove();
			layer.close(index);
			return true;
		}, function(index){
			//删除
			layer.close(index);
			return false;
		});
		
    }

    //大计划保存
    function bigPlanSave(thisbutton){
//     	console.log("大计划保存");
		var bpId=$(thisbutton).parent().parent().find(".bigPlanID").text(); //大计划id(修改)
     	var bpDept=$(thisbutton).parent().parent().find(".dept").find("select").val(); //部门
    	var bpName=$(thisbutton).parent().parent().find(".name").text(); //大计划回访名称
    	var bpExplain=$(thisbutton).parent().parent().find(".explan").text(); //大计划说明
    	//console.log(bpDept+"--------bpDept");
    	 if(bpDept==''){
             layer.open( {
    				content: '请选择部门!',
    				closeBtn: 0,
    			})
             return;
         }
         if(bpName==''){
             layer.open( {
	       				content: '请输入回访计划名称!',
	       				closeBtn: 0,
	       			})
             return;
         }
         if(bpExplain==''){
             layer.open( {
    				content: '请添加该项计划说明!',
    				closeBtn: 0,
    			})
             return;
         }
        
//     	console.log(bpNum+"--------"+bpName+"--------"+bpExplain);
	    	var planArr = [];//定义js数组用于存放自己所需的值
	   	   	var obj={};
	   	 	obj.seqId=bpId; //大计划ID(修改)
	   	 	obj.hfdept=bpDept; //部门
	       	obj.planName=bpName; //大计划名称
	       	obj.explanation=bpExplain; //大计划说明
	       	planArr.push(obj);			
//      	    console.log(JSON.stringify(planArr)+"-------大计划保存planArr");
//     	    return;
   	        var visttemplate = JSON.stringify(planArr);
   	        visttemplate = encodeURIComponent(visttemplate);
   	        var params = {
   	        		visttemplate : visttemplate
   					};
   	        var url = contextPath + "/HUDH_AddVisitAct/addVisitTemplate.act";
   	        $.axse(url, params,
       	     function(r) {
       	         if (r.retState == "0") {
       	       			layer.open( {
       	       				content: '保存成功!',
       	       				closeBtn: 0,
       	       				end:function(){
       	       					location.reload();
       	       				}
       	       			})
       	       		return true;
       	       		}else{
       	       		layer.open( {
   	       				content: '保存失败!',
   	       				closeBtn: 0
   	       			})
   	       			return false;
       	       	 }
   	        });
    	
    }
   // 大计划选中行
   $(document).on("dblclick","#bigPlanTbody tr",function(){
//      console.log("大计划双击事件");
      var serial=$(this).find("td.serial").text();
	  var dept=$(this).find("td.dept").text();
	  var name=$(this).find("td.name").text();
	  var explan=$(this).find("td.explan").text();
	  var parentSeqId=$(this).find("td.bigPlanID").text();
	  var obj={};
	  obj.serial=serial;
	  obj.dept=dept;
	  obj.name=name;
	  obj.explan=explan;
	  obj.seqId=parentSeqId;
// 	  console.log(JSON.stringify(obj)+"====");
     var bigPlanTr=$("#bigPlanTbody").find("tr");
     for (var i = 0; i < bigPlanTr.length; i++) {
//       console.log("删除其他样式循环");
      if(bigPlanTr.eq(i).hasClass("trActive")){
       bigPlanTr.eq(i).removeClass("trActive");
      }
  	}
     $(this).addClass("trActive");   
     $(".smallPlanAdd").removeAttr("disabled"); // 去掉小计划添加按钮禁用
     $(".smallPlanAdd").removeClass("disAbledBtn").addClass("startBtn");
	 initSmallPlan(parentSeqId);//小计划初始化
	 //initDictSelectByClass('triggerChange'); // 咨询项目、受理类型、受理工具
	 //initDictSelectByClass(); // 回访分类
	 initDept();//初始化回访部门（只能是回访页面使用）
	 
   })
    // 小计划前端删除 
	function smallPlanDel(thisbutton){
// 		$(thisbutton).parent().parent().remove();
		layer.confirm("确认删除吗？",{
			btn: ['确认', '取消']
		}, function (index) {
			//确认
			$(thisbutton).parent().parent().remove();
			layer.close(index);
			return true;
		}, function(index){
			//删除
			layer.close(index);
			return false;
		});
    }
    // 小计划添加
    function addSmallPlan(parentSeqId){
		//得到父级id
 		var parentSeqId="";
 		 var bigPlanTr=$("#bigPlanTbody").find("tr");
	     for (var i = 0; i < bigPlanTr.length; i++) {
		      if(bigPlanTr.eq(i).hasClass("trActive")){
		    	  parentSeqId=bigPlanTr.eq(i).find("td.bigPlanID").text();
		      }
	  	} 
	    var smallPlanTr=$("#smallPlanTbody").find("tr").length;//当前已有小计划长度
		var appendHtml="";
		appendHtml+="<tr class=''>";
		appendHtml+="<td class='smallPlanID' hidden></td>";
		appendHtml+="<td class='parentPlanID' hidden>"+parentSeqId+"</td>";
		appendHtml+='<td class="spcbox" style="width:5%;"><input type="checkbox"/></td>';
		appendHtml+="<td class='spDayNum  daysWidth' contenteditable='true'></td>";
		appendHtml+="<td class='spType visitTypeWidth' contenteditable='true'>"+
					"<select class='select2' tig='HFFL' name='hffl' id='hffl"+smallPlanTr+"' onchange=''></select>"+
					"</td>";
		appendHtml+="<td class='spFollow followWidth'><input type='checkbox'/></td>";
		appendHtml+='<td class="spdept deptWidth"><select class="hfdept select2" name="hfdept" id="hfdept" onchange="getVisitPersonSelect(this);"></select></td>';
		appendHtml+="<td class='spPerson hfperson_Width'><select class='select2 dict hfperson' name='hfperson' id='hfperson'><option value=''>请选择</option></select></td>";
		appendHtml+="<td class='spSLType sltype_Width'><select class='dict' tig='SLLX' id='shouli"+smallPlanTr+"' ></select></td>";
		appendHtml+="<td class='spExplain smallexplainWidth' contenteditable='true' placeholder='请添加回访要点'></td>";
		appendHtml+="<td class='smalloperateWidth'>";
		appendHtml+="<button class='btn smallPlanDel' onclick='smallPlanDel(this);'>删除</button>"; 
		appendHtml+="</td>"; 
		appendHtml+="</tr>";
// 		console.log(appendHtml+"------temp----");
		$("#smallPlanTbody").append(appendHtml);
		document.getElementById('smallPlanTbody').scrollTop = document.getElementById('smallPlanTbody').scrollHeight; //设置滚动条位置
		var hffl=$("#smallPlanTbody").find("tr").eq(smallPlanTr).find("td.spType").find("select"); // 当前所在行的回访类型select下拉框
		initVisitType('triggerChange',hffl); // 初始化回访类型
		var sllx=$("#smallPlanTbody").find("tr").eq(smallPlanTr).find("td.spSLType").find("select"); // 当前所在行的受理类型select下拉框
		initVisitType('triggerChange',sllx); // 初始化受理类型
		//initDictSelectByClass('triggerChange'); // 咨询项目、受理类型、受理工具
		//initDictSelectByClass(); // 回访分类
		initDept();//初始化回访部门（只能是回访页面使用）
	}

   /* -----------------------------------------------------------  */    
	// 查询部门
	function initDept(obj,val) {
	    /* if ($(".hfdept").length > 0) { */
	        /* for (var i = 0; i < $(".hfdept").length; i++) { */
	            //var dict = $(".hfdept").eq(i);
	            var dict = $(obj);
	            // :eq() 选择器选取带有指定 index 值的元素。index值从 0 开始，所有第一个元素的 index 值是 0（不是1）。
	//             var type = dict.attr("tig");
	            var url = contextPath + "/YZDeptAct/findDeptList.act";
	            $.axse(url, null,
	            function(data) {
	            	//console.log(JSON.stringify(data)+"--------部门初始化数据");
	                var list = data;
	                if (list != null && list.length > 0) {
	                    dict.empty();
	                    dict.append("<option value=''>请选择</option>");
	                    for (var j = 0; j < list.length; j++) {
	                        var optionStr = list[j];
	                        dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.deptName + "</option>");
	                    }
	                }
	                if(val){
	                	dict.val(val);
	                	if(dict.val()==null){
	                		dict.val('');
	                	}
	                }
	            },
	            function() {
	                layer.alert('查询出错！'  );
	            });
	        /* } */
	    /* } */
	}   
// 联动回访人员
function getVisitPersonSelect(thi,isAdd) {
    var deptId = $(thi).val();
    var dict=$(thi).parent().parent().find("td.spPerson").find("select");
    if (deptId == null || deptId == '') { // 如果切换门诊，导致重新初始化一级目录
        dict.empty();
        dict.append("<option value=''>请选择</option>");
        return false; // 终止执行
    }
    var url = contextPath + "/YZPersonAct/findVisualPersonnel.act?deptId=" + deptId;
    if (isAdd) {
        url += '&isAdd=1';
    }
    var organization = $("#organization").val(); // ### 容错处理
    if (organization) {
        url += '&organization=' + organization;
    }
    dict.empty();
    dict.append("<option value=''>请选择</option>");
    $.axse(url, null,
    function(data) {
        if (data.length>0) {
            var list = data;
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                dict.append("<option value='" + optionStr.seq_id + "'>" + optionStr.user_name + "</option>");
            }
        } else {
            layer.alert('查询出错！'  );
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

/**
 *  设置按钮权限操作 
 */
function getButtonPower(){
	var menubutton1 = "";
	// 创建一个数组，存放listbutton的qxName 
	var listbuttonArray = new Array();
	for ( var i = 0; i < listbutton.length; i++) {
		//console.log("获取到的按钮="+listbutton[i].qxName);
		listbuttonArray[i] = listbutton[i].qxName;
	}
	/* 按钮 */
	var btnList =  '[';
		btnList	+= '{"qx":"addtemp","name":"添加计划模板"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"deltemp","name":"删除计划模板"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"updatetemp","name":"修改计划模板"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"addPlan","name":"添加计划"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"delPlan","name":"保存计划"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"updatePlan","name":"删除计划"}'; // 最后一个不要逗号
	    btnList	+= ']';
	    var jsonbtnList = jQuery.parseJSON( btnList );
		for( var i = 0; i < jsonbtnList.length; i++){
			// 判断当前用户具备的按钮权限
			var index = jQuery.inArray(jsonbtnList[i].qx, listbuttonArray);
			// index = -1 时，表示当前用户没有此按钮的操作权限
			if( index == -1 ){//无权限的展示
			} else {//有权限的展示
				$("#"+jsonbtnList[i].qx).removeClass("hide");
			}
		}
}
    
</script>
</body>
</html>