<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//服务器路径
	String realurl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title id="pageTitle">
</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<script type="text/javascript">
   // document.domain = "qq.com";
   var _wxao = window._wxao || {};
   _wxao.begin = ( + new Date());
</script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/wechat/newsItem/wx-article/client-page1e4a15.css">
<!--[if lt IE 9]>
    <link rel="stylesheet" type="text/css" href="http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/style/pc-page1ea1b6.css"
    />
<![endif]-->
<link media="screen and (min-width:1000px)" rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/wechat/newsItem/wx-article/pc-page1ea1b6.css">
<style>
   body{ -webkit-touch-callout: none; -webkit-text-size-adjust: none; }
   #activity-detail .page-content .media{
   	width:240px;
   	height:240px;
   	margin:  0 auto;
   }
   .page-bizinfo, #activity-detail .page-content{
   		width:400px;
   }
   #activity-detail .page-content .media img{
   	max-width:100%;
   }
</style>
<style>
#nickname{overflow:hidden;white-space:nowrap;text-overflow:ellipsis;max-width:90%;}
.page-toolarea a.random_empha{color:#607fa6;} ol,ul{list-style-position:inside;}
#activity-detail .page-content .text{font-size:16px;}
</style>
</head>
<body id="activity-detail">
	<img width="12px" style="position: absolute; top: -1000px;" src="<%=contextPath%>/static/css/wechat/newsItem/wx-article/ico_loading1984f1.gif">
	<div class="wrp_page">
		<div class="page-bizinfo">
			<div class="header">
				<h1 id="activity-name"></h1>
				<p class="activity-info">
					<span id="post-date" class="activity-meta no-extra"> </span> <span
						class="activity-meta"> <!-- jeecg社区 -->
					</span> <a href="javascript:viewProfile();" id="post-user"
						class="activity-meta"> <span class="text-ellipsis" id="author">
					</span> <i class="icon_link_arrow"> </i>
					</a>
				</p>
			</div>
		</div>
		<div id="page-content" class="page-content" lang="en">
			<div id="img-content">
				<div class="media" id="media">
					<img src="" id="imagepath">
				</div>
				<div class="text" id="js_content"></div>
				<p class="page-toolbar" id="js_toobar">
					<a href="http://china-kqds.com" class="page-imform"> KQDS团队 </a>
				</p>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.min.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/bootstrap-datepicker.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script>
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var seqId = $.getUrlVar('seqId');
$(function() {
    var detailurl = '<%=contextPath%>/WXNewsItemAct/selectDetail.act?seqId=' + seqId;
    $.axse(detailurl, null,
    function(data) {
        var newsItem = data.data;
        $("#pageTitle").html(newsItem.title);
        $("h1").html(newsItem.title);
        $("#post-date").html(newsItem.createdate);
        $("#author").html(newsItem.author);
        $("#js_content").html(newsItem.content);
        $("#imagepath").attr("src", '<%=contextPath%>' + newsItem.imagepath);
    },
    function() {
        layer.alert('查询出错！' );
    });
    readonly();
});
</script>

</html>