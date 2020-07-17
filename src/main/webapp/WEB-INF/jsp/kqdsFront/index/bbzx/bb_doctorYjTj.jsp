<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
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
<title>医生业绩统计</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />

<script src="http://cdn.hcharts.cn/highstock/highstock.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts.js"></script> --%>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-3d.js"></script> --%>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/exporting.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/nodata.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<!-- jquery 导出excel -->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.table2excel.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>

<style>
	#tableHeader tr:hover{
		background-color: #a9ecc7;
	}
	#table tr:hover{
		background-color: #a9ecc7;
	}
	#chart{
	overflow: hidden;
	}
	#containerAnalysis,#containerRightPlus,#containerRightMinus{
		float: left;
	}
	/* 搜索框select */
	.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
	       width: 110px;   
	      }  
	.searchSelect>.btn { 
		    width: 110px; 
		 	height:24px; 
		 	border: solid 1px #e5e5e5; 
		}
	.bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
		    color: #999;
		    height: 24px;
		}
	.pull-left {
	    float: left !important;
	    color: #666;
		}
	.bootstrap-select.btn-group .dropdown-toggle .filter-option {
	    margin-top: -3px;
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
		
	/* 	解決标签查询中下拉框悬浮 */
	.searchWrap , .formBox{
     overflow: visible; 
	}
	.searchWrap{
		height: 78px;
	}
</style>
</head>
<body>
<div id="container">
	<!--查询条件-->
	<div class="searchWrap">
		<div class="cornerBox">查询条件</div>
            <div class="formBox">
            	<div class="kv">
	            	<div class="kv">
							<label>所属门诊</label>
							<div class="kv-v">
								<select id="organization" name="organization"></select>
							</div>
					</div>
				</div>
				<div class="kv">
		            	<div class="kv hide" id='ysqk_bmlb'>
								<label>部门类别</label>
								<div class="kv-v " >
									<select id="deptCategory" name="deptCategory" onchange="findDeptPerson(this.value)"></select>
								</div>
						</div>
				</div>
				<div class="kv">		
						<div class="kv hide" style="width:200px;" id='ysqk_doctor'>
					        <label style="width:76px;">医生：</label>
					        <select class="dict searchSelect" tig="jzfl" name="recesort" id="recesort"  data-live-search="true" title=""></select>	
<!-- 					        <select class="dict" tig="jzfl" name="recesort" id="recesort"></select>	 -->
				    	</div>
				</div>
               	<div class="kv">
               		<div class="kv">
						<label>统计时间</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始时间" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束时间" id="endtime" />
							</span>
						</div>
					</div>
                </div>
			     <div class="kv" style="width:250px; margin:0 auto;text-align:center; ">
				        <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
			    </div>
			    
            </div>
        </div> 
    <div class="tableHd">医生业绩统计</div>
    <div id="chart">
     <div id="containerAnalysis" style="width:40%" ></div>
     <div id="containerRightPlus" style="width:30%" ></div>
     <div id="containerRightMinus" style="width:30%" ></div>
    </div>    
      <!-- jquery 生成excel table-responsive table2excel -->
    <div class=" table2excel tableBox"  style="position:relative;">
    	<div class="tableHeader" style="position:absolute; top:0;left:0;height:55px;overflow:hidden;">
    		<table id="tableHeader" class="table-striped table-condensed table-bordered" style="visibility:hidden;">
     	 		<tr>
	     	 		<td  style="text-align: center">医生</td>
			 		<td  style="text-align: center">消费一级分类</td>
			 		<td  style="text-align: center">消费二级分类</td>
			 		<!-- <td  style="text-align: center">应收金额</td> -->
			 		<td  style="text-align: center">实收金额</td>
		 		</tr>
		 		<tbody id="hiddenTable" style="visibility:hidden;" >
		 		
		 		</tbody>
        	</table>
    	</div>
    	
    	<div class="tableBody" style="overflow-y:auto;padding-bottom:2px; box-sizing:content-box;">
	     	 <table id="tableBody" class="table-striped table-condensed table-bordered" style="width: 100%">
	     	 		<tr>
		     	 		<td  style="text-align: center">医生</td>
				 		<td  style="text-align: center">消费一级分类</td>
				 		<td  style="text-align: center">消费二级分类</td>
				 		<!-- <td  style="text-align: center">应收金额</td> -->
				 		<td  style="text-align: center">实收金额</td>
			 		</tr>
			 		<tbody  id="table" >
			 		
			 		</tbody>
	         </table>
         </div>
<!--          <div id="containerhh" style="width:100%" ></div> -->
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var pageurl = contextPath+'/KQDS_ScbbAct/selectCountDoctorYj.act';
var nowday;
var qxname="";
var menuid = "<%=menuid%>";
var qxnameArr = ['doctor_scbb'];
var func = ['exportTable'];
var status;
$(function(){
	$("input[type='text']").attr("autocomplete","off");  //去掉input输入框的历史记录  lutian
	initHosSelectList4Front('organization');// 连锁门诊下拉框	
	//获取当前页面所有按钮
    getButtonPowerByPriv(qxnameArr,func,menuid);
 	//时间选择
	$(".unitsDate input").datetimepicker({
		language:  'zh-CN',  
		minView:2,
        autoclose : true,
		format: 'yyyy-mm-dd',
		pickerPosition: "bottom-right"
	});
	nowday = getNowFormatDate();
	$("#starttime").val(nowday);
	$("#endtime").val(nowday);
	//绑定两个时间选择框的chage时间
	$("#starttime,#endtime").change(function(){
		timeCompartAndFz("starttime","endtime");
    });
	//获取当前页面所有按钮
	status=0;
    findDept(menuid);
//	gettabledata();//表格
// 	getdata();//图表1
//	getdataRightPlus();//图表2正数消费
//	getdataRightMinus();//图表3负数数消费
 $('.searchSelect').selectpicker("refresh");//初始化刷新--2019-11.12 licc
});
$("#query").click(function(){
	$(this).attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
	$(this).text("查询中");
	gettabledata();//表格
// 	getdata();//图表1
//	getdataRightPlus();//图表2正数消费
//	getdataRightMinus();//图表3负数数消费
});
//  jquery 导出excel 
function exportTable(){
	$(".table2excel .tableBody").table2excel({
		exclude: ".noExl",//带noExlclass的行不会被输出到excel中
		name: "Excel Document Name",
		filename: "医生业绩统计表",
		exclude_img: true,
		exclude_links: true,
		exclude_inputs: true
	});
}
function gettabledata(){
	$('#table').html("");
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	var deptCategory=$("#deptCategory").val();
	var recesort=$("#recesort").val();
	var url = pageurl+"?starttime="+starttime+"&endtime="+endtime+"&depttype=1&organization="+$("#organization").val()+"&deptCategory="+deptCategory+"&recesort="+recesort+"&qxname="+qxname;
	 $.axseY(url,null,
             function(data){
		// console.log(JSON.stringify(data)+"---------------");
				 //解除查询按钮禁用 lutian
				 if(data){
					 $("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer").css("pointer-events","auto");
					 $("#query").text("查询");
				 }
		        getdata(data);
		        getdataRightPlus(data);
		        getdataRightMinus(data);
 			    data = data.rows;
		 		if(data!=null){
		 			if(data.length>0){
		 				$('#table').html("");
		 				var content = "";
		 				for(var i=0;i<data.length;i++){
		 					if(data[i].basename){
		 						content+='<tr>';
			 					content+='<td style="text-align: center">'+data[i].username+'</td>';
			 					content+='<td style="text-align: center">'+data[i].basename+'</td>';
			 					content+='<td style="text-align: center">'+data[i].nextname+'</td>';
			 					/* content+='<td style="text-align: center">'+data[i].ysje+'</td>'; */
			 					content+='<td style="text-align: center">'+data[i].ssje+'</td>';
			 					content+='</tr>';
		 					}else if(i == (data.length-1)){
		 						content+='<tr>';
		 						content+='<td style="text-align: center" colspan="3">总计</td>';	
		 						/* content+='<td style="text-align: center">'+data[i].ysje+'</td>'; */
			 					content+='<td style="text-align: center">'+data[i].ssje+'</td>';
			 					content+='</tr>';
		 					}else{
		 						content+='<tr>';
		 						content+='<td style="text-align: center" colspan="3">合计</td>';	
		 						/* content+='<td style="text-align: center">'+data[i].ysje+'</td>'; */
			 					content+='<td style="text-align: center">'+data[i].ssje+'</td>';
			 					content+='</tr>';
		 					}
		 					
		 				}
	 					$('#table').append(content);
	 					$("#hiddenTable").append(content);
	 					
		 			}
		 		}
		 		$(".tableBody").outerHeight($(window).outerHeight()-$(".searchWrap").outerHeight()-$(".cornerBox").outerHeight()-15);
				setWidth();//设置tableheade的表头和内容一样宽
             },
			function(){
           	    layer.alert('查询统计失败！' );
			}
       );
}
function setWidth(){
	$("#tableHeader").outerWidth($("#tableBody").outerWidth());
	$("#tableHeader").css("visibility","visible");		
}

// 数组去重
function unique(arr) {
    var obj = {};
    return arr.filter(function(item, index, arr){
        return obj.hasOwnProperty(typeof item + item) ? false : (obj[typeof item + item] = true);
    })
}

/* 基础数据成交占比(月) */
function getdata(data){
	var jsonXPerson=[];//横坐标医生
	var jsonPerformance=[];//实收
        	 var chartDataColumnar = data.rows;
			 for (var i = 0; i < chartDataColumnar.length; i++) {
				 var flag=chartDataColumnar[i].hasOwnProperty('username');
				 if(flag===false){
					 var doctorName=chartDataColumnar[i-1].username;
					 var ysje=Number(chartDataColumnar[i].ssje);
					 if(doctorName!==undefined){
						 jsonXPerson.push(doctorName);//就诊医生
						 jsonPerformance.push(ysje);//就诊医生
					 }
				 }

			 }
			 
			 var maxNum;
        	 if(jsonXPerson.length<6){
        		 maxNum=jsonXPerson.length-1;
        	 }else{
        		 maxNum=5;
        	 }
        	                 	         	         	   
           	 var chart = new Highcharts.Chart('containerAnalysis', {
           		 chart: {
           			 type: 'column',
           			},
           			title: {
           				text: '实收数据统计',
           				useHTML: true,
                        style: {
//                          color: '#FF00FF',      //字体颜色
                         "fontSize": "25px", 
                         fontWeight: 'bold'
                       }      
           			},
           			xAxis: {
           				categories: jsonXPerson,
           				max:maxNum,	
           			},
           			scrollbar: {
           				enabled: true
           			},
           			credits: {
                        enabled: false
                    },
           			yAxis: {
           				title: {
           					text: '金额'
           				}
           			},
           			tooltip: {
           				pointFormatter: function () {
           						return '<span>'+this.series.name+'</span>: <b>'+this.y+'</b>' 
//            						+
//             						'('+this.percentage.toFixed(2)+'%)<br/>';
           				},
           				shared: true
           			},
           			plotOptions: {
           				column: {
           					stacking: 'normal'
           				},
           			},
           			series: [{
           				name: "实收金额",
           				data: jsonPerformance,
           				stack: 'male',
           				color:"rgb(124, 181, 236)",
           			}
           			
           		],
           		lang: {
                    noData: "暂无数据。。。"
                },
                noData: {
                    style: {
//                         fontWeight: 'bold',
                        fontSize: '20px',
                        color: '#00a6c0'
                    }
                }
           	 });

}



function getdataRightPlus(data){
        	var plus=data.plus;
            var classifyArr=[];
            for(var i=0;i<plus.length;i++){
            	var classifyObj={};
            	classifyObj.name=plus[i].basename;
            	classifyObj.y=Number(plus[i].ssje);
            	classifyArr.push(classifyObj);
            	
            }
          var chart = new Highcharts.Chart('containerRightPlus', {
          	  chart: {
          				plotBackgroundColor: null,
          				plotBorderWidth: null,
          				plotShadow: false,
          				type: 'pie'
          			},
          			title: {
          				text: '各消费分类实收占比',
          				useHTML: true,
                        style: {
//                          color: '#FF00FF',      //字体颜色
                         "fontSize": "25px", 
                         fontWeight: 'bold'
                       }      
          			},
          			credits: {
                        enabled: false
                    },
          			tooltip: {
          				pointFormatter: function () {
          						return '<span>'+this.series.name+'</span>: <b>'+this.y+'</b>' +
           						'('+this.percentage.toFixed(2)+'%)<br/>';

          				},
          				shared: true,
//           				pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
          			},
	           	   plotOptions: {
	           			pie: {
	           				allowPointSelect: true,
	           				cursor: 'pointer',
	           				size: 235,
	           				dataLabels: {
	           					enabled: true,
	           					format: '<b>{point.name}</b>: {point.percentage:.2f} %',
	           					style: {
	           						color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	           					}
	           				}
	           			}
	           		},
		           	 series: [{
		 	    		name: ' ',
		 	   			colorByPoint: true,
		 	   			data:classifyArr
		           	 }],
		 	   		lang: {
		                noData: "暂无数据。。。"
		            },
		            noData: {
		                style: {
		                    fontSize: '20px',
		                    color: '#00a6c0'
		                }
		            }
		          });

}

function getdataRightMinus(data){
        	 var minus=data.minus;
             var classifyArr=[];
             for(var i=0;i<minus.length;i++){
             	var classifyObj={};
             	classifyObj.name=minus[i].basename;
             	classifyObj.y=Math.abs(minus[i].ssje);
             	classifyArr.push(classifyObj);
             }
          var chart = new Highcharts.Chart('containerRightMinus', {
          	  chart: {
          				plotBackgroundColor: null,
          				plotBorderWidth: null,
          				plotShadow: false,
          				type: 'pie'
          			},
          			title: {
          				text: '减免消费实收占比',
          				useHTML: true,
                        style: {
//                          color: '#FF00FF',      //字体颜色
                         "fontSize": "25px", 
                         fontWeight: 'bold'
                       }      
          			},
          			credits: {
                        enabled: false
                    },
          			tooltip: {
          				pointFormatter: function () {
      						return '<span>'+this.series.name+'</span>: <b>'+this.y+'</b>' +
       						'('+this.percentage.toFixed(2)+'%)<br/>';

      				    },
      				   shared: true,
//       				pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
          			},
	           	   plotOptions: {
	           			pie: {
	           				allowPointSelect: true,
	           				cursor: 'pointer',
	           				size:235,
	           				dataLabels: {
	           					enabled: true,
	           					format: '<b>{point.name}</b>: {point.percentage:.2f} %',
	           					style: {
	           						color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	           					}
	           				}
	           			}
	           		},
		           	 series: [{
		 	    		name: '业绩',
		 	   			colorByPoint: true,
		 	   			data:classifyArr		 	   		
		 	   			}],
		 	   		lang: {
		                noData: "暂无数据。。。"
		            },
		            noData: {
		                style: {
		//                     fontWeight: 'bold',
		                    fontSize: '20px',
		                    color: '#00a6c0'
		                }
		            }
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
		listbuttonArray[i] = listbutton[i].qxName;
	}
	/* 按钮 */
	var btnList =  '[';
		btnList	+= '{"qx":"ysqk_bmlb","name":"部门类别"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"ysqk_doctor","name":"医生"}';
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

//查询部门
function findDept(menuId){
	//console.log("执行部门");
    var url = contextPath + '/YZSystemAct/getButtonList.act?menuId=' + menuId;
    $.axseY(url, null,
    function(data) {
    	//console.log(data);
        if (data.retState == "0") {
            listbutton = data.retData;
            // 加载该页面的所有可操作按钮，每个页面一共有多少按钮是相对固定的，在此基础上，通过获取当前登录用户的权限按钮，来进行界面展示
            getButtonPower();
            for ( var i = 0; i < listbutton.length; i++) {
        		if(listbutton[i].qxName.search("dept")==0){
        			qxname=listbutton[i].qxName;
        		}
        	}
            if(qxname!=("")){
        		$.ajax({
        		    url:contextPath+"/SysDeptPrivAct/findDeptNameByButtonName.act",    //请求的url地址
        		    data:{qxname:qxname},
        		    dataType:"json",   //返回格式为json
        		    type:"post",   //请求方式
        		    success:function(data){
        		    	//console.log("权限名称"+JSON.stringify(data));
        		        //请求成功时处理
        		        $("#deptCategory").html("<option value='all'>所有部门</option>");
        		        for (var i = 0; i < data.length; i++) {
        		        $("#deptCategory").append("<option value ="+data[i].id+">"+data[i].deptname+"</option>")
        				}
        		        findDeptPerson("all");
        		    }
        		});
        	}else{
        		$("#deptCategory").html("<option value='personage'>请选择</option>");
        		$("#recesort").html("<option value='personage'>个人</option>");
        		if(status==0){
        			status=1;
        			gettabledata(); //表格数据初始化
        		}
        		
        	}
        } else {
            layer.alert('查询出错！'  );
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}
//查询部门人员
function findDeptPerson(id){
	var deptid;
	if(id=="all"){
		deptid="all";
	}else{
		deptid=id;
	}
	$.ajax({
	    url:contextPath+"/SysDeptPrivAct/findPersonByDeptId.act",    //请求的url地址
	    data:{deptid:deptid,qxname:qxname},
	    dataType:"json",   //返回格式为json
	    type:"post",   //请求方式
	    success:function(data){
	        //请求成功时处理
		    $("#recesort").html("");
		    $("#recesort").html("<option value='all'>全部员工</option>");
		    for (var i = 0; i < data.length; i++) {
		       $("#recesort").append("<option value ="+data[i].seqid+">"+data[i].username+"</option>")
			}
		    $('.searchSelect').selectpicker("refresh");//初始化刷新--2019-11.12 licc
		    if(status==0){
    			status=1;
    			gettabledata(); //表格数据初始化
    		}
	    }
	});
}
</script>
</html>
