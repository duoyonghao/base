<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	YZPerson loginUser = SessionUtil.getLoginPerson(request);

	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//图片上传地址
	String VIDEO_URL = YZSysProps.getProp(SysParaUtil.VIDEO_URL);
	if (VIDEO_URL == null) {
		VIDEO_URL = "";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" rel="stylesheet" >
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ckPlayer/ckplayer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<title>左侧树形目录</title>
<style>
	*{
		margin:0;
		padding:0;
		box-sizing:border-box;
	}
	html,body{
		height:100%;
		
	}
	ul{
		list-style:none;
	}
	.leftDiv{
		width:250px;
		float:left;
		height:100%;
		/* background:beige; */
		border-right:1px solid #333;
		overflow-y:auto;
	}
	.rightDiv{
		margin-left:250px;
		/* background:deepskyblue; */
		height:100%;
		padding-right:10px;
		padding-top:10px;
		overflow-y:auto;
		
	}
	.yhgtText{
		font-weight:normal;
		padding:2px 0 2px 10px;
		background:#FAFAFA;
		border-bottom:2px solid #eee;
	}
	.videoUl{
		overflow-y:auto;
		
	}
	.videoUl>li{
		float:left;
		width:33%;
		height:250px;
		padding:0 0 0 10px;
		background:#fff;
	}
	.videoUl>li>img{
		height: 175px;
	    width: 100%;
	    float: left;
	}
	.videoUl>li>p{
		height: 20px;
	    line-height: 20px;
	    padding-right: 5px;
	    text-align: center;
	    padding-bottom: 10px;
	    /* background: palevioletred; */
	    font-size:12px;
	    clear:both;
	}
	.layoutDiv{
		position:absolute;
		top:0;
		left:0;
		right:0;
		bottom:0;
		background:rgba(74,72,77,0.68);
		display:none;
	}
	.videonDivCloseBtn{
		position:absolute;
		top:0;
		right:0;
		/* color:#acacac; */
		width:20px;
		height:20px;
		line-height:20px;
		z-index:2;
		cursor:pointer;
		background:#E81123;
		color:#fff;
		font-size:22px;
		text-align:center;
	}
</style>
<script type="text/Javascript">	
var VIDEO_URL ="<%=VIDEO_URL%>";
function doInit() {
    zTreeInit();
}
function zTreeInit() {
    //异步加载
    var url = VIDEO_URL + "/YZYhgtAct?nkj=getDictTree";
    var menutreeNodes = null;
    $.axse(url, null,
    function(data) {
        var treeNodes = [];
        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: "0"
                }
            },
            callback: {
                onClick: onclick
            }
        };
        $.each(data.retData[0].children,
        function() {
            var treeNode = traverse(this);
            
            treeNodes.push(treeNode);
        });
        $.fn.zTree.init($("#treeList"), setting, treeNodes);
        zTree = $.fn.zTree.getZTreeObj("treeList");
    },
    function() {
        layer.alert('功能未开启！');
    });

}
function traverse(obj) {
    var childs = obj.children;
    if (childs && childs.length > 0) {
        for (var i = 0; i < childs.length; i++) {
            var node = traverse(childs[i]);
            /* if (node.parent) {
                node.name = node.name + " (" + node.size + ")";
                node.isParent = true;
            } else {
                node.isParent = false;
            } */
        }
    }else{
    	obj.parent = false;
    }
    
    if (obj.parent) {
    	obj.isParent = true;
    } else {
    	obj.name = obj.name + " (" + obj.size + ")";
    	obj.isParent = false;
    }
    
    return obj;
}
function onclick(e, treeId, treeNode) {
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj("treeList");
        zTree.expandNode(treeNode);
    } else {
        var url = VIDEO_URL + "/YZYhgtAct?nkj=getShiping&path=" + encodeURIComponent(encodeURIComponent(treeNode.id));
        $.axse(url, null,
        function(data) {
            if (data.retData.length > 0) {
                var content = '';
                $(".videoUl").html('');
                for (var i = 0; i < data.retData.length; i++) {
                    var spdata = data.retData[i];
                    var path = spdata.path.replace(/\\/g, "/");
                    var mvpath = VIDEO_URL + '/' + path;
                    var imgpath = mvpath.substring(0, mvpath.lastIndexOf('.')) + '.png';
                    content += '<li>' + '<div><img src="' + imgpath + '" onclick="play(\'' + mvpath + '\');" style="width:100%;height:230px;"/></div>' + '<p>' + spdata.name + '</p>' + '</li>';
                }
                $(".videoUl").append(content);
            }
        },
        null);
    }
}
function play(mvpath) {
    /* layer.open({
        type: 1,
        title: false,
        closeBtn: 1,
        area: ['1000px', '600px'],
        skin: 'layui-layer-nobg',
        shadeClose: true,
        content: $('#videon'),
        end: function() {
            //$('.videon').html('');
        }
    }); */
    $("#layoutDiv").show();//打开视频播放弹框
    var videoObject = {
        container: '#videon',
        //“#”代表容器的ID，“.”或“”代表容器的class
        variable: 'player',
        //该属性必需设置，值等于下面的new chplayer()的对象
        flashplayer: false,
        //如果强制使用flashplayer则设置成true
        autoplay: true,
        video: mvpath //视频地址
    };
    var player = new ckplayer(videoObject);
}
$(function(){
	$("#layoutDiv").on("click",function(e){
		//如果点击灰色区域或关闭按钮 则默认关掉视频
		if($(e.target).hasClass("layoutDiv")||$(e.target).hasClass("videonDivCloseBtn")){
			$(this).hide();//隐藏视频弹框
			$("#videon").html("");//停止播放
		}
	});
	
});
</script>
</head>
<body onload="doInit()" style="overflow-x:hidden;">

<div class="leftDiv">
	<h4 class="yhgtText">医患沟通</h4>
	<div id="treeList" class="ztree"></div>
</div>
<div class="rightDiv">
	<ul class="videoUl">
	</ul>
</div>
<div id="layoutDiv" class="layoutDiv">
	<div id="videonDiv" style="width: 1000px; height: 600px; position:absolute;top:50%;left:50%;margin-top:-300px;margin-left:-500px;z-index:1;">
		<b id="videonDivCloseBtn" class="videonDivCloseBtn">×</b>
		<div id="videon" style="width: 100%; height: 100%; ">
		</div>
	</div>
</div>


</body>
</html>