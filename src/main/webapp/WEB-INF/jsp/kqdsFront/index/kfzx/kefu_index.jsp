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
<meta http-equiv="X-UA-Compatible" content="IE=7">
<meta charset="utf-8" />
<title>指定客服</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css" />
<style>
.jiuzhen_register-content{
	padding-bottom:0;
}
.position-bottom{
	padding-bottom:15px;
}
.clear2{
	margin-top:10px;
}
</style>
</head>
<body>
<div class="jiuzhen_register-content" style="width: 98%;">
	<div class="tableBox">
		<table id="table" class="table-striped table-condensed table-bordered" data-height="500"></table>
	</div>
    <div class="position-bottom" >
    	<div class="clear2"></div>
    	<a class="kqdsCommonBtn" id="zz">指定</a>
    	<a class="kqdsCommonBtn" id="zzjl">指定记录</a>
    </div>
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
var selectedrows = window.parent.selectedrows;
var pageurl = '<%=contextPath%>/KQDS_UserDocumentAct/selectByUserCodes.act';
$(function() {
	
	/*wl----首次加载时 计算table高度  */
	
	var tableHeight=0;/* 计算table需要的高度 */
	/* 根据当前页面 计算出table需要的高度 */
	tableHeight=$(window).height()-$(".position-bottom").outerHeight()-20;
	/* 框架要使用改table */
	$(".tableBox").html("<table id='table' class='table-striped table-condensed table-bordered' data-height='"+tableHeight+"'></table>");
	/*wl----首次加载时 计算table高度————————————结束  */
	
    //档案列表
    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        striped: true,
        cache: false,
        columns: [{
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '病人编号',
            field: 'usercode',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '当前客服',
            field: 'kefuname',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                if (value) {
                	 return "<span class='name'>"+value+"</span>";
                }
            }
        },
        {
            title: '性别',
            field: 'sex',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '出生日期',
            field: 'birthday',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '身份证号',
            field: 'idcardno',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '手机号码1',
            field: 'phonenumber1',
            align: 'center'
        },
        {
            title: '手机号码2',
            field: 'phonenumber2',
            align: 'center'
        },
        {
            title: '建档人',
            field: 'createusername',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return "<span class='name'>"+value+"</span>";
                }
            }
        },
        {
            title: '建档时间',
            field: 'createtime',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="time">' + value + '</span>';
                } else {
                    return "";
                }
            }
        }

        ]
    });
	
    $(window).resize(function() {
        setHeight();
    });
});
function searchHzda() {
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
function queryParams(params) {
    var usercodes = "";
    for (var i = 0; i < selectedrows.length; i++) {
        usercodes += selectedrows[i].usercode + ",";
    }
    usercodes = usercodes.substring(0, usercodes.length - 1);
    var temp;
    temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        limit: params.limit,
        offset: params.offset,
        usercodes: usercodes
    };
    return temp;
}
$('#zz').on('click',
function() {
	var count = 0;
	for (var i = 0; i < selectedrows.length; i++) {
        if(selectedrows[i].kefuname){ // 如果选中的记录里，有的记录已经指定了客服，则给予提醒
        	count ++;
        }
    }
	if(count > 0){
	    //提交病历时  提示是否已签署知情同意书
	    layer.confirm('所选记录中，存在已经指定客服的患者，确定继续操作？', {
	        btn: ['是', '否'] //按钮
	    },
	    function() {
	    	layer.closeAll('dialog'); // 关闭confirm
	    	
	    	layer.open({
	            type: 2,
	            title: '指定',
	            maxmin: true,
	            shadeClose: true,
	            //点击遮罩关闭层
	            area: ['550px', '90%'],
	            content: contextPath+'/KQDS_ChangeKeFuAct/toSetKefu.act'
	        });
	    },
	    function() {

		});
	}else{
		layer.open({
	        type: 2,
	        title: '指定',
	        maxmin: true,
	        shadeClose: true,
	        //点击遮罩关闭层
	        area: ['550px', '90%'],
	        content: contextPath+'/KQDS_ChangeKeFuAct/toSetKefu.act'
	    });
	}
});
$('#zzjl').on('click',
function() {
    layer.open({
        type: 2,
        title: '指定记录',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['90%', '90%'],
        content: contextPath+'/KQDS_ChangeKeFuAct/toHisKefu.act'
    });
});
function setHeight() {
	/* 本页面只设置 table的高度  */
    $(".fixed-table-container").outerHeight($(window).height()-$(".position-bottom").outerHeight()-20);
    	
}
</script>

</html>
