<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>修复类型</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/kqdsCss/kqdsCommon.css"/>
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
</head>
<body>
    <div class="tableBox" style="border-top:1px solid #0054B5">
       <table id="table" class="table-striped table-condensed table-bordered" data-height="418"></table>
    </div>
	<div class="position-bottom" >
		<div class="clear2"></div>
		<a class="kqdsCommonBtn" id="add">新增</a>
		<a class="kqdsCommonBtn" id="edit">修改</a>
		<a class="kqdsCommonBtn" id="del">删除</a>
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
var pageurl = contextPath+'/KQDS_MACHININGTypeAct/getPrimaryCategories.act';
var $table = $('#table');
var onclickrow = ""; //选中行对象
$(function() {
	initTable();
	
	$table.bootstrapTable("resetView",{
		height:$(window).height()-50
	});
	
});
function initTable(){
	$table.bootstrapTable({
        url: pageurl,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess: function(data) {  // 加载成功时执行
//           	console.log("一级="+JSON.stringify(data));
        },
        columns: [
        	{
                title: 'seqId',
                field: 'seqId',
                align: 'center',
                
                visible: false,
                switchable: false
            },
	        /* {
	            title: '序号',
	            field: '',
	            align: 'center',
	            
	            sortable: true,
	            formatter:function(value){
	            	return '<span>'+ index+1 +'</span>';
	            }
	        },     */      
	        {
	            title: '修复类型',
	            field: 'typename',
	            align: 'center',
	            
	            sortable: true,
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
	        },
	        {
		        title: '创建人',
		        field: 'createuser',
		        align: 'center',
		        sortable: true,
		        formatter:function(value){
		        	return "<span>"+value+"</span>";
		        }
	        },
	        {
	        	title: '创建时间',
	            field: 'createtime',
	            align: 'center',
	            sortable: true,
	            editable: true,
	            formatter:function(value){
	            	return "<span>"+value+"</span>";
	            }  
	        },
	        {
	        	title: '操作',
	            field: 'useflag',
	            align: 'center',
	            sortable: true,
	            editable: true,
	            formatter:function(value, row, index){
	            	if (row.useflag==1) {
	            		return '<button class="kqdsCommonBtn" sqid="'+row.seqId+'" onclick="disabledOrnot(this,'+row.useflag+')">启用</button>';		               
			          } else{	        				        	
			        	return '<button class="kqdsCommonBtn" sqid="'+row.seqId+'" onclick="disabledOrnot(this,'+row.useflag+')">禁用</button>';	
			          }            
			      }
	            }  
	        ]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrow = $('#table').bootstrapTable('getData')[index];
//         console.log(JSON.stringify(onclickrow)+"**********onclickrow");
    });
}
function refresh() {

    $table.bootstrapTable('refresh', {
        'url': pageurl
    });

}
// 禁用启用
function disabledOrnot(thi,status){
	 var disabledOrnotId=$(thi).attr("sqid"); //当前ID
	 var params = {
			 seqId:disabledOrnotId,
			 useflag:status
    };
// 	return;
   var url = contextPath + "/KQDS_MACHININGTypeAct/insertOrUpdate.act";
   $.axse(url, params,
           function(data) {
   		    layer.alert("更改成功！", {
   	            end: function() {
   	            	window.location.reload(); //刷新父页面
   	            }
   	        });
           },
           function() {
               layer.alert('改变失败！', {
               });
           });
}


//弹出一个iframe层
$('#add').on('click',
function() {
    layer.open({
        type: 2,
        title: '添加修复类型',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['550px', '250px'],
        content: contextPath + '/KQDS_MACHININGTypeAct/toAddFirstclassify.act'
    });
});
//弹出一个iframe层
$('#edit').on('click',
function() {
    if (onclickrow == "" || onclickrow == null) {
        layer.alert('请选择修复类型' );
        return false;
    }
    layer.open({
        type: 2,
        title: '修改修复类型',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['550px', '250px'],
        content: contextPath + '/KQDS_MACHININGTypeAct/toAddFirstclassify.act?seqId=' + onclickrow.seqId +'&typename='+onclickrow.typename
    });
});
//弹出一个iframe层
$('#del').on('click',
function() {
	if (onclickrow == "" || onclickrow == null) {
        layer.alert('请选择修复类型' );
        return false;
    }

// 	 console.log(JSON.stringify(params)+"------params");
    var pageurl = contextPath+'/KQDS_MACHININGTypeAct/initPrimary.act?parentId=' + onclickrow.seqId;
    $.axse(pageurl, null,
    function(data) {
//     	console.log(JSON.stringify(data)+"------data");
        if (data.retData.length > 0) {
            layer.alert('该类型下存在下级！', {                  
                end: function() {
                    return false;
                }
            });
        }else {
            layer.confirm('确定删除？', {
                btn: ['确认', '取消'] 
            },
            function() {
     
                var url = contextPath+'/KQDS_MACHININGTypeAct/delPrimaryBySeqId.act?seqId=' + onclickrow.seqId;
                msg = layer.msg('加载中', {
                    icon: 16
                });
                $.axse(url, null,
                function(data) {
                    if (data.valid) {
                    	 layer.alert('删除成功', {                             
                             end: function() {
                            	 location.reload(); //刷新父页面
                                 parent.zTreeInit();//刷新树
//                                  parent.refresh();
                             }
                         });
                     
                    }else{
                    	layer.alert('删除失败！'  );
                    }
                },
                function() {
                    layer.alert('删除失败！'  );
                }
                );
            });
        }
    },
    function() {
        layer.alert('查询失败！' );
    });
});


function submitu(){
	//验证
	var recesort = $("#recesort").val();
	if(recesort == "" || recesort == null) {
		layer.alert('请选择就诊分类' );
		return false;
	}
	var a={
		seqId:regSeqId,
		recesort:recesort,
		organization:organization
	};
	
	layer.confirm('确定修改吗？', {
        btn: ['确定', '取消'] //按钮
    },
    function() {
    	var url = '<%=contextPath%>/KQDS_REGAct/chufuzhenModify.act';
        $.axseSubmit(url,a, 
   		function(){
   			 layer.msg('加载中', {icon: 16});
   	  	},
        function(r){
      	        if(r.retState=="0"){
      	        	layer.alert('修改成功', {  end:function(){
      	        		parent.shuaxin();
      	        		parent.layer.close(frameindex); //关闭当前弹层
      	        	}});
      	        }else{
      	        	layer.alert('修改失败' );
      	        }     
              },
              function(){
            	    layer.alert('修改失败' );
   		}
   		);
    });
	
	  
}
</script>
</html>
