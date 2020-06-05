<%@page import="com.kqds.entity.sys.YZPerson"%>
<%@page import="com.kqds.util.sys.SessionUtil"%>
<%@page import="java.io.Console"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
 	 String usercode = request.getParameter("usercode");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>加工单展示</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/hudh/lclj/flowdetail/css/base.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
</head>
<style type="text/css">
		
</style>
<body>
	<div id="" class="">
        <table id="tableList" class="table-striped table-condensed table-bordered" data-height="185"></table>
    </div>
	<div id="" class="" style="margin-top: 20px;">
		<table id="table" class="table-striped table-condensed table-bordered" data-height="185"></table>
	</div>

</body>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script> 
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript">
	var usercode='<%=usercode%>';		
	var pageurl = '<%=contextPath%>/KQDS_CostOrderAct/getByRegnoNopage.act';
	var defaultindex = 0;
	$(function(){
		getlist();
// 		OrderDetail();
	})
function getlist() {
    var url = pageurl + "?usercode=" + usercode+"&access=1";//不需要可见人员过滤，查询全部费用
    $("#tableList").bootstrapTable({
        url: url,
        dataType: "json", 
        onLoadSuccess: function(data) { //加载成功时执行
        	if(defaultindex==0){
        		defaultindex = 1;
        	}
        	$("#tableList").find("tr:eq("+defaultindex+") td:eq(0)").click();
        },
/*         rowStyle: function (row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (row.costno==costno) {
            	strclass = 'success';//欠费
            	defaultindex = index + 1;
            } 
            return { classes: strclass };
        }, */
        columns: [
      		{
   			    title: '序号',
   			    field: '',
   			    align: 'center',
	   			 formatter: function(value, row, index) {
	                 return index+1;
	             }
    		},
			{
			    title: 'costno',
			    field: 'costno',
			    align: 'center',
			    visible: false,
			},
            {
                title: '开单时间',
                field: 'createtime',
                align: 'center',
                sortable: true,
                formatter: function(value, row, index) {
                    html = '<span>' + value.substring(0) + '</span>';
                    return html;
                }
            },
            {
                title: '姓名',
                field: 'username',
                align: 'center',
                
                sortable: true,
                formatter: function(value, row, index) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                }
            },
            {
                title: '费用类型',
                field: 'type',
                align: 'center',
                
                sortable: true,
                formatter: function(value, row, index) {
                    if (row.status == "0") {
                        return '<span style="color:red">费用计划</span>';
                    } else {
                        return '<span style="color:green">确认划价</span>';
                    }
                }
            },
            {
                title: '成交情况',
                field: 'cjstatus',
                align: 'center',
                
                sortable: true,
                formatter: function(value, row, index) {
                    if (value == "1") {
                        return '<span style="color:green">已成交</span>';
                    } else {
                        return '<span style="color:red">未成交</span>';
                    }
                }
            },
            {
                title: '状态',
                field: 'status',
                align: 'center',
                
                sortable: true,
                formatter: function(value, row, index) {
                    if (value >= 2) {
                        return '<span style="color:green">已结账</span>';
                    } else if (value == 1) {
                        return '<span style="color:red">已开单</span>';
                    } else {
                        return '<span style="color:red">未成交</span>';
                    }
                }
            },
            {
    	        title: '就诊医生',
    	        field: 'doctorname',
    	        align: 'center',
    	        
    	        formatter: function(value, row, index) {
    	            if (value != "" && value != null) {
    	                return "<span class='name'>"+value+"</span>";
    	            }else{
    	            	return "<span>-</span>";
    	            }
    	        }
    	    }
      ]
        
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#tableList').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj2 = $('#tableList').bootstrapTable('getData')[index];
        $("#table").bootstrapTable('destroy');
        OrderDetail(onclickrowOobj2.costno); //  "\'"+onclickrowOobj2.costno+"\'"
        // 处方单
    });
}
		function OrderDetail(costno) {
			static_isyjjitem = 0;
			var detailurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/NoselectPage.act?costno=' + costno;
		    $("#table").bootstrapTable({
		        url: detailurl,
		        dataType: "json",
		        cache: false,
		        striped: true,
		        onLoadSuccess:function(data){
		        	/*表格载入时，设置表头的宽度 */
//  		        	console.log(JSON.stringify(data)+"-----------data");
		        	if(data.length>0){
		        		 for (var i = 0; i < data.length; i++) {
		        	            var tabledata = data[i];
		        	            if(tabledata.isyjjitem == 1){
		        	            	static_isyjjitem = 1;
		        	            }
		        	     }
		        	}
		        	 $(".unitsDate").datetimepicker({
		         		language:  'zh-CN',  
		         		minView:0,
		         	    autoclose : true,
		         		format: 'yyyy-mm-dd hh:ii:ss',
		         		pickerPosition: "bottom-right"
		         	});
		        },
		        columns: [
				{
				    title: '序号',
				    field: '',
				    align: 'center',
				    formatter:function(value, row, index){
				    	return index+1;
				    }
				},
		        {
		            title: '项目编号',
		            field: 'itemno',
		            align: 'center',
		            visible: false,
		            sortable: true,
		            formatter:function(value){
		            	return '<span>'+value+'</span>';
		            }
		        },
		        {
		            title: '治疗项目',
		            field: 'itemname',
		            align: 'center',
		            width:	350,
		            sortable: true,
		            formatter: function(value, row, index) {
		                var html = '<span  title="' + value + '">';
		                if (row.istk == 1) {
		                    html += '<span class="label label-info">退款</span>';
		                } else {
		                    if (Number(row.y2) < 0) {
		                        html += '<span class="label label-warning">还款</span>';
		                    } else if (Number(row.y2) > 0 && status == 2) {
		                        html += '<span class="label label-danger">欠款</span>';
		                    } else if (Number(row.y2) == 0 && row.isqfreal == 1) {
		                        html += '<span class="label label-warning">还款</span>';
		                    }
		                }
		                html += value + '</span>';
		                return html;
		            }
		        },
		        {
			        title: '就诊医生',
			        field: 'doctorname',
			        align: 'center',
			        width:	150,
			        formatter: function(value, row, index) {
			        	 if (value) {
				                return "<span class='name'>"+value+"</span>";
				         }else{
				        	 return "<span>-</span>";
				         }
			        }
			    },
		        {
		            title: '单位',
		            field: 'unit',
		            align: 'center',
		            
		            sortable: true,
		            formatter:function(value){
		            	return '<span>'+value+'</span>';
		            }
		        },		       
		        {
		            title: '数量',
		            field: 'num',
		            align: 'center',
		            formatter:function(value){
		            	return '<span>'+value+'</span>';
		            }
		        }
		        
		        ]
		    });
		}
</script>
</html>