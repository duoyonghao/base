<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>病历库管理</title><!-- 医疗中心 病历查询 菜单  -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>	

<style>
	.searchWrap{
	    margin-top: 10px;
   		overflow: hidden;
	}
	.tableLayout{/*  */
		margin:0 auto;
		width:98%;
	}
	.tableLayout tr{
		height:42px;
	}
	#searchvalue{/* 模糊查询搜索框 */
		width:157px;
	}
	.searchWrap .btnGroups{		/*按钮组右浮动  */
		float:right;
		overflow:hidden;
	}
	.searchWrap .btnGroups .aBtn{
		margin:0 3px;
		display:block;
		color:#0E7CC9;
		background:#fff;
		height:28px;
		padding:0 15px;
		line-height:28px;
		border-radius:16px;
		text-align:center;
		border:1px solid #0E7CC9;
		font-size:13px;
	}
	.searchWrap .btnGroups .aBtn:hover{
		cursor:pointer;
		color:#fff;
		background:#0E7CC9;
	}
	select{/*普通select inputsss */
		box-sizing:border-box;
		width:80px;
		color:#333;
		height:24px;
		border-radius:4px;
		transition:box-shadow linear 300ms;
	}
	input[type="text"]{/*普通select inputsss */
		box-sizing:border-box;
		width:86px;
		color:#333;
		height:24px;
		border-radius:4px;
		transition:box-shadow linear 300ms;
	}
	select{
		cursor:pointer;
	}
	/* input[type="text"]:focus,select:focus,textarea:focus{
	    box-shadow: 0 0 8px rgb(49, 165, 247);
	    border-color:transparent;
	} */
	.btnGroups{
		float:right;
	}
	.btnGroups a.aBtn{
		float:left;
	}
	.textContent{
		display:inline-block;
		text-align:right;
	}
	.orangeText{
		color:#FA6406;
	}
	.fixed-table-container thead th .sortable{/*覆盖样式 表头右边距导致无法居中的问题  */
		padding-right:8px;
	}
	.fixed-table-container{
		border-left: 1px solid #ddd;
		border-right: 1px solid #ddd;
		border-bottom:1px solid #ddd;
		border-radius: 6px;
		/* border-top-left-radius: 6px;
		border-top-right-radius: 6px; */
		overflow: hidden;
	}
	.searchModule{
		margin-top:10px;
	}
</style>
</head>
<body>
<div id="container">
    <div class="main" style="margin-left:20px;">
        <div class="listWrap">
            <div class="listHd">
            	<span class="title">病历管理</span>
           	</div>
            <div class="listBd">
                <div class="tableBox">
                    <table id="table" class="table-striped table-condensed table-bordered" data-height="550"></table>
                </div>
            </div>
        </div>
        <div class="searchModule">
	    	<header>
	    		<span>查询条件</span>
	    		<i>共有记录 <span id="size"></span> 条</i>
	    	</header>
	    	<section>
	    		<ul class="conditions">
	    			<li>
	    				<span>病历库类别</span>
	    				<select class="dict" tig="BLKFL" name="blkfl" id="blkfl"></select>
	    			</li>
	    			<li>
	    				<span>病历库种类</span>
	   				    <select id="mtype" name="mtype">
							<option value="">请选择</option>
							<option value="0">初诊</option>
							<option value="1">复诊</option>
						</select>
	    			</li>
            		<li>
            			<span>是否自用</span>
            			<select id="type" name="type">
							<option value="">请选择</option>
							<option value="1">自用</option>
							<option value="0">公用</option>
						</select>
            		</li>
            		<li>
            			<span>病历库名称</span>
            			<input type="text" name="blname" id="blname" class="searchInput" style="width: 200px;">
            		</li>
	    		</ul>
	    	</section>
	    	<div class="btnBar" style="text-align:left;">
	        	<a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
     			<a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
	        </div>
	    </div> 
        <!--查询条件-->
        <!-- <div class="searchWrap">
            <div class="cornerBox">查询条件</div>
            <span class="text">共有记录<i class="total" id="size">0</i>条</span>
            
            <table class="formBox tableLayout">
            	<tr>
            		<td>
	            		<span class="textContent">病历库类别：</span>
	            		<select class="dict" tig="BLKFL" name="blkfl" id="blkfl"></select>
            		</td>
            		<td>
            			<span class="textContent">病历库种类：</span>
            			<select id="mtype" name="mtype">
							<option value="">请选择</option>
							<option value="0">初诊</option>
							<option value="1">复诊</option>
						</select>
            		</td>
            		<td>
            			<span class="textContent">是否自用：</span>
            			<select id="mtype" name="mtype">
							<option value="">请选择</option>
							<option value="1">自用</option>
							<option value="0">公用</option>
						</select>
            		</td>
            		<td style="width:500px;">
            			<span class="textContent">病历库名称：</span>
            			<input type="text" name="blname" id="blname" class="searchInput" style="width: 200px;">
            		</td>
            		<td style="width: 40%;">
            			&nbsp;
            		</td>
            	</tr>
            	
           </table>  
           <div class="btnGroups">
     				<a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
     				<a href="javascript:void(0);" style="background:#0E7CC9;color:#fff;" class="kqdsCommonBtn search" id="query">查询</a>
           </div>
        </div> -->
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var pageurl = '<%=contextPath%>/KQDS_BLKAct/blkManager.act';
$(function() {
	initDictSelectByClass();
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer").css("pointer-events","auto");
				$("#query").text("查询");
			}
			var tableList = $('#table').bootstrapTable('getData');
             $("#size").html(tableList.length);
             setHeight();
        },
        //服务端处理分页
        columns: [
		{
		    title : '序号',
		    align: "center",
		    width: 40,
		    formatter: function (value, row, index) {
		     /* return index + 1; */
		     var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
		        var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
		        return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
		    }
		   },     
            {
            title: '病历库名称',
            field: 'blname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter:function(value){
            	return "<span style='text-align:left;width:fit-content;float:left;'>"+value+"</span>";
            }
        },
        {
            title: '病历库类别',
            field: 'blkfl',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return value;
                }
            }
        },
        {
            title: '病历种类',
            field: 'mtype',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                var html = "";
                if (value == "1") {
                    html = '<span class="label label-success">复诊</span>';
                } else {
                    html = '<span class="label label-success">初诊</span>';
                }
                return html;
            }
        },
        {
            title: '是否自用',
            field: 'type',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                var html = "";
                if (value == "1") {
                    html = "自用";
                } else {
                    html = "公用";
                }
                return html;
            }
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
        },
        {
            title: '创建人',
            field: 'createuser',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	if (value) {
                    return value;
                }
            }
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            formatter: function(value, row, index) {
                var html = "";
               	html += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="bzblkEdit(\'' + row.seqId + '\',\'' + row.mtype + '\')"><span style="color:#00A6C0;">编辑</span></a> ';
               	html += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="copyBLK(\'' + row.seqId + '\')">拷贝</a> ';
               	html += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="del(\'' + row.seqId + '\')"><span style="color:red;">删除</span></a> ';
                html += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="detail(\'' + row.seqId + '\')">详情</a> ';
                return html;
            }
        }]
    });
    //计算主体的宽度
    setHeight();
    $(window).resize(function() {
        setHeight();
    });
});
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
   		blname: $('#blname').val(),
        blkfl: $('#blkfl').val(),
        mtype: $('#mtype').val(),
        type: $("#type").val()
    };
    return temp;
}
$('#query').on('click',
function() {
	$(this).attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
	$(this).text("查询中");
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
});
$('#clean').on('click',
function() {
    $("section :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});

//计算左侧表格高度保证一屏展示
function setHeight() {
   $("#table").bootstrapTable("resetView",{
	   height:$(window).outerHeight()-$(".searchModule").outerHeight()-$(".listWrap .listHd").outerHeight()-50
   });
}

/*******************  下面JS从list_kqds_blk.jsp中拷贝 ****************/

function copyBLK(seqId) {
    layer.open({
        type: 2,
        title: '拷贝病历库',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['500px', '400px'],
        content: contextPath + '/KQDS_BLKAct/toBlkCopy.act?seqId=' + seqId
    });
}


function detail(id) {
    var index = layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：病历库详情',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '480px'],
        content: contextPath + '/KQDS_BLKAct/toBlkDetail.act?seqId=' + id
    });
}
function del(id) {

    //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/KQDS_BLKAct/deleteObj.act?seqId=' + id;
        msg = layer.msg('加载中', {
            icon: 16
        });
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
                layer.alert('删除成功', {
                      
                });
                refresh();
            }
        },
        function() {
            layer.alert('删除失败！'  );
        });
    });
}

//编辑病历
function bzblkEdit(seqId, mtype) {
 parent.layer.open({
     type: 2,
     title: '编辑病历',
     maxmin: true,
     shadeClose: true,
     //点击遮罩关闭层
     zIndex: 211111,
     area: ['80%', '90%'],
     content: contextPath + '/KQDS_BLKAct/toMedicalrecord_Edit4blk.act?seqId=' + seqId + '&mtype=' + mtype
 });
}

function refresh() {

    $("#table").bootstrapTable('refresh', {
        'url': pageurl
    });

}

</script>
</html>
