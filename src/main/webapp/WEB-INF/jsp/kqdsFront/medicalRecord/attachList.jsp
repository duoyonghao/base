<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	String usercode = request.getParameter("usercode");
	
	if(usercode == null){
		usercode = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>附件列表</title>
<!--  用于病历列表中，查看上传的附件   右侧个人中心  -->
<!--引入CSS-->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/style.css">
<script type="text/javascript" src="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/upload/uploadutil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">
var usercode = "<%=usercode%>";

var attachmentId = "";
var attachmentName = "";
var yxflIds = "";
var moduleIds = ""; // 模块名称，该参数标明图片的存储位置
jQuery(function($) {
    if (usercode == '') {
        layer.alert('请选择患者' );
        return;
    }

    initDictSelectByClass(); // 影像资料分类
    // 选择类型的时候进行过滤
    $('#imgtype').on('change',
    function() {
        showYxzlImg();
    });

    getidandname(usercode); // 同步请求，顺序执行
    showYxzlImg();

});

//查询影像资料id和name
function getidandname(usercode) {
    var ids = "";
    var names = "";
    var yxfls = "";
    var mids = ""; // 该字段涉及存储图片的路径
    var url = '<%=contextPath%>/KQDS_ImageDataAct/selectDetailByUsercode.act?usercode=' + usercode;
    var msgIndex = msg = layer.msg('加载中', {
        icon: 16,
        time: 3000 // 默认3秒自动关闭
    });
    
    $.axse(url, null,
    function(yxdata) {
        for (var i = 0; i < yxdata.data.length; i++) {
            ids = ids + yxdata.data[i].attachmentid; // 去除最后一个星号   ## 存储的数据库的每个 attachmentid 的值结尾都是 逗号，所以这里不需要再自己拼接逗号
            names = names + yxdata.data[i].attachmentname;
            yxfls += yxdata.data[i].yxfl + ','; // 存储yxfl字段的值，用于 点击分类，过滤显示
            mids += yxdata.data[i].infosort + ',';
        }
        attachmentId = ids.substring(0, ids.length - 1); // 去除最后一个逗号
        attachmentName = names.substring(0, names.length - 1);
        yxflIds = yxfls.substring(0, yxfls.length - 1);
        moduleIds = mids.substring(0, mids.length - 1); //去除最后一个逗号
        // 加载完关闭
        layer.close(msgIndex)
    },
    function() {
        layer.alert(mess + '失败！' );
    });
}

//  展示影像资料-图片列表， 影像分类作为参数
function showYxzlImg() {
    var yxfl_select = $('#imgtype').val();
    var attachmentIdnew = "",
    attachmentNamenew = "";

    var attachmentIds = attachmentId.split(",");
    var attachmentNames = attachmentName.split("*");
    var yxflIdsArr = yxflIds.split(","); // 影像分类
    var moduleIdsArr = moduleIds.split(",");

    jQuery("#fileList").html(""); // 先清空
    for (var i = 0; i < attachmentIds.length; i++) {
        if (yxfl_select == '') { // 加载全部
            fullAttachDetail(attachmentIds[i], attachmentNames[i], moduleIdsArr[i]);
        } else {
            // 根据分类进行过滤
            if (yxflIdsArr[i] == yxfl_select) {
                fullAttachDetail(attachmentIds[i], attachmentNames[i], moduleIdsArr[i]);
            }
        }
    }
}
	
</script>

<style>
.img-responsive,.thumbnail>img,.thumbnail a>img,.carousel-inner>.item>img,.carousel-inner>.item>a>img
	{
	display: block;
	max-width: 100%;
	height: 100px;
}
</style>
</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12" style="height:30px;line-height:30px;">
								<span style="font-weight:bold;">&nbsp;&nbsp;影像分类：</span>
								<select class="dict" style="width:300px;" tig="YXFL" name="imgtype" id="imgtype">
								</select>
						</div>
						<div class="col-xs-12">
							<div class="form-group">
								<!--dom结构部分-->
								<div id="uploader-demo">
									<!--用来存放item-->
									<div id="fileList" class="uploader-list"></div>

								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


</body>
</html>