<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
  String contextPath = request.getContextPath();
  if (contextPath.equals("")) {
      contextPath = "/kqds";
  }
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8" />
    <title>操作记录</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css" />
	<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
	<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
    </head>
<body>
		<div class="">
		    <div class="tableBox">
            	<table id="table" class="table-striped table-condensed table-bordered" data-height="600">
		        </table>
             </div>
		</div>
</body>


<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>

<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var $table = $('#table');
var parentonclickrow = window.parent.onclickrowOobj;
var pageurl = '<%=contextPath%>/KQDS_Member_RecordAct/selectListForCzjl.act?cardno=' + parentonclickrow.memberno;
var canEditAskperson = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag13_canEditAskperson, request)%>';
var number = 1;//序号
$(function(){
	$table.attr("data-height",$(window).height());
	$table.bootstrapTable({
		url:pageurl, 
		dataType: "json",
		cache: false, 
		striped: true,
		onLoadSuccess:function(){
			/*表格载入时，设置表头的宽度 */
	        setTableHeaderWidth(".tableBox");
		},
		columns: [
					{title: '序号',field: 'usercode',align: 'center',sortable: true,editable: true,
						formatter:function(value,row,index){  
					        return '<span>'+(number++)+'</span>' ;
						}
					},
					{
		                  title: '操作时间',
		                  field: 'createtime',
		                  align: 'center',
		                  sortable: true,
		                  editable: true,
		                  formatter:function(value,row,index){  
			                  return '<span title="'+value+'">' + value + '</span>';
	                	  }
	                },
					{title: '患者编号',field: 'usercode',align: 'center',sortable: true,editable: true,
						formatter:function(value,row,index){
							var showusername = value + "";
							if(row.type == "转赠"){
								showusername = row.usercode;
							}else if(row.type == "受赠"){
								showusername = row.szrusercode;
							}
			                return '<span  title="'+showusername+'">' + showusername + '</span>';
	                	}
					},
					{title: '姓名',field: 'username',align: 'center',sortable: true,editable: true,
						formatter:function(value,row,index){
							var showusername = value + "";
							if(row.type == "转赠"){
								showusername = row.username;
							}else if(row.type == "受赠"){
								showusername = row.szrusername;
							}
			                return '<span class="name" title="'+showusername+'">' + showusername + '</span>';
	                	}
					},
					{title: '第一咨询',field: 'faskperson',align: 'center',sortable: true,
						formatter:function(value,row,index){  
			                return '<span class="name">' + value + '</span>';
			        	}
					},
					{title: '接诊咨询',field: 'askperson',align: 'center',sortable: true,
						formatter:function(value,row,index){  
			                return '<span class="name">' + value + '</span>';
			        	}
					},
					{title: '患者来源',field: 'devchannel',align: 'center',sortable: true,
						formatter:function(value,row,index){  
			                return '<span class="source">' + value + '</span>';
	                	}
					},
					{title: '来源子分类',field: 'nexttype',align: 'center',sortable: true,
						formatter:function(value,row,index){  
			                return '<span class="source">' + value + '</span>';
	                	}
					},
					{title: '建档人',field: 'jdr',align: 'center',sortable: true,
			            formatter: function(value, row, index) {
			                return '<span class="name" title="' + value + '">' + value + '</span>';
			            }
			        },
			        {title: '开发人',field: 'kfr',align: 'center',sortable: true,
			            formatter: function(value, row, index) {
			                return '<span class="name" title="' + value + '">' + value + '</span>';
			            }
			        },
					{title: '卡号',field: 'cardno',align: 'center',sortable: true,editable: true,
					    formatter:function(value,row,index){  
			                return '<span title="'+value+'">' + value + '</span>';
                	    }
					},
					{
						title: '类型',
						field: 'type',
						align: 'center',
						sortable: true,
						editable: true,
						formatter:function(value,row,index){
							return '<span>'+value+'</span>';
						}
						
					},
					{title: '充值金额',field: 'cmoney',align: 'center',sortable: true,editable: true,
						formatter:function(value,row,index){
							return '<span>'+Number(value).toFixed(2)	+ "</span>";
						}
					},
					{title: '赠送金额',field: 'cgivemoney',align: 'center',sortable: true,editable: true,
						formatter:function(value,row,index){
							return '<span>'+Number(value).toFixed(2)	+ "</span>";
						}
					},
					{title: '金额小计',field: 'ctotal',align: 'center',sortable: true,editable: true,
						formatter:function(value,row,index){
							return '<span>'+Number(value).toFixed(2)	+ "</span>";
						}
					},
					{title: '转赠-充值',field: 'zzmoney',align: 'center',sortable: true,editable: true,
						formatter:function(value,row,index){
							return '<span>'+Number(value).toFixed(2)	+ "</span>";
						}
					},
					{title: '转赠-赠送',field: 'zzgivemoney',align: 'center',sortable: true,editable: true,
						formatter:function(value,row,index){
							return '<span>'+Number(value).toFixed(2)	+ "</span>";
						}
					},
					{title: '转赠-小计',field: 'zztotal',align: 'center',sortable: true,editable: true,
						formatter:function(value,row,index){
							var zztotal = (Number(row.zzmoney) + Number(row.zzgivemoney)).toFixed(2);
							return '<span>'+ zztotal + "</span>";
						}
					},
					{title: '充值余额',field: 'qmmoney',align: 'center',sortable: true,editable: true,
						formatter:function(value,row,index){
							return '<span>'+Number(value).toFixed(2)	+ "</span>";
						}
					},
					{title: '赠送余额',field: 'qmgivemoney',align: 'center',sortable: true,editable: true,
						formatter:function(value,row,index){
							return '<span>'+Number(value).toFixed(2)	+ "</span>";
						}
					},
					{title: '余额小计',field: 'qmtotal',align: 'center',sortable: true,editable: true,
						formatter:function(value,row,index){
							return '<span>'+Number(value).toFixed(2)	+ "</span>";
						}
					},
					{title: '转赠/受赠人',field: 'zzuser',align: 'center',sortable: true,editable: true,
						formatter:function(value,row,index){
							var showusername = "";
							if(row.type == "转赠"){
								showusername = row.szrusername;
							}else if(row.type == "受赠"){
								showusername = row.username ;
							}
							return '<span>'+showusername+'</span>';
						}
					},
	                {
	                  title: '操作人',
	                  field: 'czr',
	                  align: 'center',
	                  sortable: true,
	                  editable: true,
	                  formatter:function(value,row,index){  
	                	  if(value){
		                	  return '<span>'+value+'</span>';
	                	  }
                	  }
	                },
	                {
					    title: '备注',
					    field: 'tfremark',
					    align: 'center',
					    
					    sortable: true,
					    editable: true,
					    formatter:function(value,row,index){
					    	if(value == "" || value == null || value == "null" || value == undefined){
					    		return "";
					    	}else{
					    		return '<span class="remark" title="'+value+'">' + value + '</span>';
					    	}
	                	}
					}
	                ,{
					    title: '操作',
					    field: ' ',
					    align: 'center',
					    
					    formatter:function(value,row,index){
					    	if(canEditAskperson == 1){
						    	if(row.type == "开卡" || row.type == "充值" || row.type == "退费"){
						    		var x = '<span class="time">';
				                    x += '<a href="javascript:void(0);" mce_href="javascript:void(0);"  style="color:red;" onclick="setAskperson(\'' + row.seqId + '\')">修改接诊咨询</a> ';
				                    x += '<a href="javascript:void(0);" mce_href="javascript:void(0);"  style="color:green;" onclick="askpersonList(\'' + row.seqId + '\')">修改记录</a> ';
						    		x += '</span>';
				                    return x;
						    	}
					    	}
	                	}
					}
		          ]
		  });
	
	//时间选择
	$(".dataTime").datetimepicker({
		language:  'zh-CN',
		minView:2,
        autoclose : true,
		format: 'yyyy-mm-dd',
		pickerPosition: "bottom-right"
	});
	
	$('#search').on('click', function(){
	   	var itemname = $("#itemname").val();
	   	$('#table').bootstrapTable('refresh',
  			{
	   			'url':pageurl+'?name=' + encodeURI(encodeURI(itemname))
			}
	   	); 
    });
});
function refresh(){
	$('#table').bootstrapTable('refresh', {
	    'url': pageurl,
	});
}

function setAskperson(seqId){
	 layer.open({
         type: 2,
         title: '修改接诊咨询',
         shadeClose: false,
         shade: 0.6,
         area: ['400px', '250px'],
         content: contextPath + '/KQDS_MemberAct/toMemberSetAskperson.act?seqId='+seqId,
     });
}
function askpersonList(seqId){
	layer.open({
        type: 2,
        title: '接诊咨询修改记录',
        shadeClose: false,
        shade: 0.6,
        area: ['80%', '60%'],
        content: contextPath + '/KQDS_MemberAct/toMemberAskpersonList.act?seqId='+seqId,
    });
}
</script>

</html>
