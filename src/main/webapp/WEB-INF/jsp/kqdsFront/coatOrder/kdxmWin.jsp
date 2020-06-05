<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String regno = request.getParameter("regno");
	String usercode = request.getParameter("usercode");
	String doctorno = request.getParameter("doctorno");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8" />
    <title>开单项目</title><!-- 加工中心 开单项目 按钮进入 -->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css" />
		<!-- 数据表中数据的样式 -->
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
	<style>
		.fixed-table-container thead th .sortable{
			padding-right:8px;
		}
	</style>
</head>
<body>
    <div class="tableBox">
       <table id="table" class="table-striped table-condensed table-bordered" data-height="480"></table>
    </div>
    <div class="" align="center">
    	<table style="width:90%;text-align: center;"> 
       		<tr>
      			<td style="width:12%"><span style="color:#00A6C0;">开单金额小计:<lable id="xiaoji">0.0</lable></span></td>
      			<td style="width:12%"><span style="color:#00A6C0;">免收小计:<lable id="mian">0.0</lable></span></td>
      			<td style="width:12%"><span style="color:#00A6C0;">应收小计:<lable id="ying">0.0</lable></span></td>
      			<td style="width:12%"><span style="color:#00A6C0;">欠费小计:<lable id="qian">0.0</lable></span></td>
      			<td style="width:12%"><span style="color:#00A6C0;">赠送小计:<lable id="zeng">0.0</lable></span></td>
      			<td style="width:12%"><span style="color:#00A6C0;">实收小计:<lable id="xian">0.0</lable></span></td>
       		</tr> 
       	</table>
    </div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/language/zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var regno= "<%=regno %>";
var usercode = "<%=usercode %>";
var doctorno = "<%=doctorno %>";
var totalmoney=0,mianshou=0,yingshou=0,qianfei=0,zengsong=0,shishou=0;
$(function(){
	//现在已改为查询患者所有开的单子 
	getOrderListByUsercode();
// 	showkdxm();
});
function setHeight(){
	$("#table").bootstrapTable('resetView',
		{
			height:$(window).outerHeight()-20
		}	
	);
}
function getOrderListByUsercode(){
	var detailurl  = '<%=contextPath%>/KQDS_CostOrder_DetailAct/getOrderListByUsercode.act?usercode='+usercode+'&doctorno='+doctorno;
	$("#table").bootstrapTable({
		url:detailurl, 
		dataType: "json",
		cache: false,
		striped: true,
		onLoadSuccess: function(data){  //加载成功时执行
			var tableList = $('#table').bootstrapTable('getData');
     	 	var xiaoji=0;
 	    	var mian = 0;
     	   	var ying = 0;
     		var xian = 0;
     		var zeng = 0;
     		var qian = 0;
 	    	for(var i=0;i<tableList.length;i++){
 	    		xiaoji +=Number(tableList[i].subtotal);
                if ((tableList[i].y2 >= 0 && tableList[i].y2 != "-0.0" && tableList[i].y2 != "-0") || tableList[i].istk == 1) {
 	    			ying +=Number(tableList[i].paymoney)+Number(tableList[i].arrearmoney);
 	    			mian +=Number(tableList[i].voidmoney);
 	    		}
 	    		xian +=Number(tableList[i].paymoney)-Number(tableList[i].payother2);
 	    		zeng +=Number(tableList[i].payother2);
 	    		qian +=Number(tableList[i].y2);
 	    	}
     	   	$("#xiaoji").html(xiaoji.toFixed(2));
 	    	$("#mian").html(mian.toFixed(2));
			$("#ying").html(ying.toFixed(2));
 	    	$("#xian").html(xian.toFixed(2));
 	    	$("#zeng").html(zeng.toFixed(2));
 	   		$("#qian").html(qian.toFixed(2));
 	   		setHeight();
	 	},
	 	rowStyle: function (row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (row.istk == "1") {
                strclass = 'danger';//还有一个active
            } else {
                return {};
            }
            return { classes: strclass };
        },
        columns: [
				{
					title: 'seqId',
					field: 'seqId',
					align: 'center',
					visible:false,
					switchable:false
				},
				{title: 'istk',field: 'istk',align: 'center',visible:false,switchable:false},
				{title: '项目编号',field: 'itemno',align: 'center', sortable: true},
				{title: '消费项目',field: 'itemname',align: 'center',sortable: true,
					 formatter:function(value,row,index){  
						 return '<span style="" class="money">'+value+'</span>' ;
				  	  } 
				}, 
				{title: '医生',field: 'doctor', align: 'center',sortable: true,
	                formatter:function(value,row,index){
	              	    if(row.doctor!="" && row.doctor!=null){
	              	 	    var pjid="table"+index+"doctor" ;
	              	 	  	bindPerUserNameBySeqIdYB(pjid,value);
		             		return "<span id='"+pjid+"' class='name'></span>";
	              	    }
         	  	    }
				},
				{title: '单位',field: 'unit',align: 'center',sortable: true}, 
				{title: '单价',field: 'unitprice',align: 'center', sortable: true,
		              formatter:function(value,row,index){  
		            	  return '<span class="money">￥'+value+'</span>' ;
		        	  } 
		 		},
				{title: '数量',field: 'num',align: 'center',sortable: true}, 
				{title: '折扣',field: 'discount',align: 'center',sortable: true,
					formatter:function(value,row,index){
						return '<span>'+value+'</span>';
					}	
				},
				{title: '小计',field: 'subtotal',align: 'center',sortable: true,
					formatter:function(value,row,index){
						totalmoney = totalmoney + Number(value);
						return '<span class="money">￥'+value+'</span>' ;
				}
				},
				{title: '免除',field: 'voidmoney',align: 'center',sortable: true,
					formatter:function(value,row,index){
						mianshou = mianshou + Number(value);
						return '<span class="money">￥'+value+'</span>' ;
					}
				},
				{title: '应收',field: 'paymoney',align: 'center',sortable: true,
					formatter:function(value,row,index){
						if((row.y2>=0 &&  row.y2!="-0.0") || row.istk==1){
							var ys =0.0;
							ys+= Number(row.paymoney)+Number(row.arrearmoney);
							return '<span class="money">'+ys+'</span>' ;
						}else{
							return '<span class="money">0.0</span>' ;
						}
					}
				},
				{title: '欠费',field: 'y2',align: 'center',sortable: true,
					formatter:function(value,row,index){
							return '<span class="money">'+value+'</span>' ;
					}
				},
				{title: '赠送',field: 'payother2',align: 'center',sortable: true,
					formatter:function(value,row,index){
						if(value==null || value==""){
							 return '<span class="money">￥0</span>' ;
						}else{
							zengsong = zengsong + Number(value);
							return '<span class="money">￥'+value+'</span>' ;
						}
					}
				},
				{title: '实收',field: 'paymoney',align: 'center',sortable: true,
					formatter:function(value,row,index){
						if(value==null){
							 return '<span class="money">￥0.0</span>' ;
						}else{
							var v = (Number(value)-Number(row.payother2)).toFixed(2);
							shishou = shishou + Number(v);
							return '<span class="money">￥'+v+'</span>' ;
						}
					}
				}
          ]
  });
}
</script>

</html>
