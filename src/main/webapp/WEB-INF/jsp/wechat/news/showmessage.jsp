<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//服务器路径
	String realurl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微信页面</title>
<meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1, maximum-scale=1">
<meta http-equiv="cache-control" content="no-cache">
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<link href="<%=contextPath%>/static/css/wechat/newsItem/index.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div style="overflow: hidden; clear: both;">
		<div style="float: left; width: 25%;">
			<div id="appmsg200159594" class="pp">
				<div class="appmsg multi" data-fileid="200159570"
					data-id="200159594">
					<div class="appmsg_content">
						<div class="appmsg_info">
							<em class="appmsg_date" id="addtime"></em>
						</div>
						<div id="newsItemHeaderDiv"></div>

						<div id="newsItemListDiv"></div>

					</div>
				</div>
			</div>
		</div>

		<div style="float: right; width: 75%;">
			<iframe scrolling="yes" id="showframe" src="" frameborder="0"
				style="width: 100%; height: 100%;"></iframe>
		</div>
	</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">
var newsid = $.getUrlVar('newsid');
var contextPath = '<%=contextPath %>';
$(function() {
    var detailurl = '<%=contextPath%>/WXNewsAct/showMessage.act?seqId=' + newsid;
    $.axse(detailurl, null,
    function(data) {
        var newsObj = data.data;
        var itemList = data.list;
        $("#addtime").html(newsObj.createtime);

        var html = '';
        for (var i = 0; i < itemList.length; i++) {
            var itemObj = itemList[i];
            if (i == 0) { // 第一个
                var ahtml = ' <div class="cover_appmsg_item" onmouseover="mouseover(\'header\')" onmouseout="mouseout(\'header\')">';
                ahtml += '<h4 class="appmsg_title">';
                ahtml += '	<a target="_blank" href="#" id="headerTitle">' + itemObj.title + '</a>';
                ahtml += '</h4>';
                ahtml += '<div class="appmsg_thumb_wrp">';
                ahtml += '	<img class="appmsg_thumb" alt="" src="' + contextPath + itemObj.imagepath + '" id="headerImg">';
                ahtml += '</div>';
                ahtml += '<div class="fe" style="display: none" id = "headerA">';
                ahtml += '<a class="edit1" href="#" onclick="goUpate(\'' + itemObj.seqId + '\')"></a>';
                ahtml += ' <a class="dete1" id="delete"  href="#" onclick="goDelete(\'' + itemObj.seqId + '\')"></a>';
                ahtml += '</div>';
                ahtml += '</div>';

                $('#newsItemHeaderDiv').html(ahtml);

            } else {

                html += '<div class="appmsg_item" onmouseover="mouseover(\'item\')" onmouseout="mouseout(\'item\')">';
                html += '<div class="fd" style="display: none">';
                html += '	<a class="edit" id="edit" href="#" onclick="goUpate(\'' + itemObj.seqId + '\')" ></a>';
                html += '	<a class="dete" id="delete"  href="#" onclick="goDelete(\'' + itemObj.seqId + '\')"></a>';
                html += '</div>';
                html += '<img class="appmsg_thumb" alt="" src="' + contextPath + itemObj.imagepath + '">';
                html += '<h4 class="appmsg_title">';
                html += '	<a target="_blank" href="#">' + itemObj.title + '</a>';
                html += '</h4>';
                html += '</div>';
            }
        }
        $('#newsItemListDiv').html(html);

    },
    function() {
        layer.alert('查询出错！' );
    });

});

function mouseover(symbol) {
    if (symbol == 'header') {
        $(".fe").removeAttr("style");
        $(".fd").attr("style", "display:none");
    } else if (symbol == 'item') {
        $(".fe").attr("style", "display:none");
        $(".fd").removeAttr("style");
    }
}

function mouseout(symbol) {
    if (symbol == 'header') {
        $(".fe").attr("style", "display:none");
    } else if (symbol == 'item') {
        $(".fd").attr("style", "display:none");
    }
}

function goUpate(newsId) {
    var url = contextPath + '/WXNewsItemAct/toEdit.act?newsid=' + newsId;
    $("#showframe").attr("src", url);
}

function goDelete(newsId) {

    //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = contextPath + '/WXNewsItemAct/deleteObj.act?seqId=' + newsId;
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
                layer.alert('删除成功', {
                      
                    end: function() {
                        location.reload();
                    }
                });
            }
        },
        function() {
            layer.alert('删除失败！'  );
        });
    });

}

function refresh() {
    location.reload();
}
</script>
</html>
