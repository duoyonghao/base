<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8" />
    <title>备注信息查看</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/visit/visitTableLayout.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/layer-v2.4/layer/skin/layer.css">
    <style>
   	 	.remakeContain{
   	 		width:98%;
   	 		border-top:1px solid #FFFFFF;
    	}
    	.remake_item{
    		height:auto;
    		min-height:50px;
    		margin-left: 10px;
    	}
    	.remake_item_context{
    		padding-top:10px;
    	}
    	textarea{
    		width:100%;
    	}
    	.tips{
    		margin-left:10px;
    		margin-top:10px;
    	}
    </style>
</head>
<body>
	<div class="remakeContain" id="remakeContain">
	</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>

<!-- 获取modelandview传递过来参数 -->
<script type="text/javascript">
	//设置textarea自适应高度
    jQuery.fn.extend({
        autoHeight: function(){
            return this.each(function(){
                var $this = jQuery(this);
                if( !$this.attr('_initAdjustHeight') ){
                    $this.attr('_initAdjustHeight', $this.outerHeight());
                }
                _adjustH(this).on('input', function(){
                    _adjustH(this);
                });
            });
            /**
             * 重置高度 
             * @param {Object} elem
             */
            function _adjustH(elem){
                var $obj = jQuery(elem);
                return $obj.css({height: $obj.attr('_initAdjustHeight'), 'overflow-y': 'hidden'})
                        .height( elem.scrollHeight );
            }
        }
    });
	//获取参数
    var orderTrackId = '<%=request.getAttribute("orderTrackId")%>';
    var flowLink = '<%=request.getAttribute("flowLink")%>';
    var oprationName = '<%=request.getAttribute("oprationName")%>';
    //初始化备注信息
    $(function(){
        var url = contextPath + '/HUDH_LCLJAct/findLcljOrderImplemenRemakeByTrackId.act';
        var param = {orderTrackId: orderTrackId,flowLink:flowLink,oprationName:oprationName};
        $.axseSubmit(url, param,function() {},function(data) { 
        //console.log(JSON.stringify(data));
        var remake = data.remake;
        if(remake) {
        	var length = remake.length;
        	for(var index=length-1;index >= 0;index--) {
        		$('#remakeContain').append(
                	'<div class="remake_item">'+
                	'<div class="remake_item_context">'+
                	'<div class="remake_item_time"><strong>'+remake[index].time+'<strong></div>'+
                	'<textarea id="remake" readonly="readonly">'+remake[index].remake+'</textarea>'+
                	'</div>'+
                	'</div>');
        	}
        }else { //如果没有备注信息则显示提示内容
        	$('#remakeContain').append('<div class="tips">暂无备注信息</div>');
        }
     },function(r){
    	 layer.alert('获取备注错误，请重新打开');
     });
        
     $('textarea').autoHeight();
})
</script>
</html>
