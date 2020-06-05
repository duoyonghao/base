<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//服务器路径
	String realurl = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ contextPath + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>微信单图消息</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/static/css/wechat/newsItem/appmsg_edit.css" />
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/static/css/wechat/newsItem/jquery.fileupload.css" />
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/static/css/admin/index/bower_components/bootstrap/dist/bootstrap.min.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/wechat/newsItem/ckfinder.js"></script>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<!--fileupload-->
<script type="text/javascript" src="<%=contextPath%>/static/js/wechat/newsItem/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/wechat/newsItem/load-image.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/wechat/newsItem/jquery.fileupload.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/wechat/newsItem/jquery.fileupload-process.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/wechat/newsItem/jquery.fileupload-image.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/wechat/newsItem/jquery.iframe-transport.js"></script>
<!--UEditor-->
<script type="text/javascript"  charset="utf-8" src="<%=contextPath%>/third-party/ueditor/ueditor.config.js"></script>
<script type="text/javascript"  charset="utf-8" src="<%=contextPath%>/third-party/ueditor/ueditor.all.min.js"></script>
  
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="<%=contextPath%>/third-party/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
//编写自定义JS代码
/*jslint unparam: true, regexp: true */
/*global window, $ */
$(function() {
    'use strict';
    // Change this to the location of your server-side upload handler:
    var url = "<%=contextPath%>/FileUploadAct/uploadFile.act?module=wechat_news",
    uploadButton = $('<button/>').addClass('btn btn-primary').prop('disabled', true).text('上传中...').on('click',
    function() {
        var $this = $(this),
        data = $this.data();
        $this.off('click').text('正在上传...').on('click',
        function() {
            $this.remove();
            data.abort();
        });
        data.submit().always(function() {
            $this.remove();
        });
    });
    $('#fileupload').fileupload({
        url: url,
        dataType: 'json',
        autoUpload: false,
        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
        maxFileSize: 2000000,
        // 2 MB
        // Enable image resizing, except for Android and Opera,
        // which actually support image resizing, but fail to
        // send Blob objects via XHR requests:
        disableImageResize: /Android(?!.*Chrome)|Opera/.test(window.navigator.userAgent),
        previewMaxWidth: 290,
        previewMaxHeight: 160,
        previewCrop: true
    }).on('fileuploadadd',
    function(e, data) {

        $("#files").text("");
        data.context = $('<div/>').appendTo('#files');
        $.each(data.files,
        function(index, file) {
            //var node = $('<p/>').append($('<span/>').text(file.name));
            //fileupload
            var node = $('<p/>');
            if (!index) {
                node.append('<br>').append(uploadButton.clone(true).data(data));
            }
            node.appendTo(data.context);
        });
    }).on('fileuploadprocessalways',
    function(e, data) {

        var index = data.index,
        file = data.files[index],
        node = $(data.context.children()[index]);

        if (file.preview) {
            node.prepend('<br>').prepend(file.preview);
        }

        if (file.error) {
            node.append('<br>').append($('<span class="text-danger"/>').text(file.error));
        }

        if (index + 1 === data.files.length) {
            data.context.find('button').text('上传').prop('disabled', !!data.files.error);
        }
    }).on('fileuploadprogressall',
    function(e, data) {
        var progress = parseInt(data.loaded / data.total * 100, 10);
        $('#progress .progress-bar').css('width', progress + '%');
    }).on('fileuploaddone',
    function(e, data) {
        console.info(data);
        var file = data.files[0];
        //var delUrl = "<a class=\"js_removeCover\" onclick=\"return false;\" href=\"javascript:void(0);\">删除</a>";
        $("#imgName").text("").append(file.name);
        $("#progress").hide();
        var d = data.result;
        if (d.success) {
            var link = $('<a>').attr('target', '_blank').prop('href', d.attributes.viewhref);
            $(data.context.children()[0]).wrap(link);
            console.info(d.attributes.viewhref);
            $("#imagepath").val(d.attributes.url);
        } else {
            var error = $('<span class="text-danger"/>').text(d.msg);
            $(data.context.children()[0]).append('<br>').append(error);
        }
    }).on('fileuploadfail',
    function(e, data) {
        $.each(data.files,
        function(index, file) {
            var error = $('<span class="text-danger"/>').text('File upload failed.');
            $(data.context.children()[index]).append('<br>').append(error);
        });
    }).prop('disabled', !$.support.fileInput).parent().addClass($.support.fileInput ? undefined: 'disabled');

    $("#saveButton").click(function() {
        $("#formobjNew").attr("action", "weixinArticleController.do?doUpdate");
        $("#formobjNew").submit();
        //parent.refresh();
    });
});
</script>
</head>
<body>
	<div class="main_bd">
		<div class="media_preview_area">
			<div class="appmsg editing">
				<div class="appmsg_content" id="js_appmsg_preview">
					<h4 class="appmsg_title">
						<a target="_blank" href="javascript:void(0);"
							onclick="return false;">标题</a>
					</h4>
					<div class="appmsg_info">
						<em class="appmsg_date"></em>
					</div>
					<div id="files" class="files">
						<i class="appmsg_thumb default">封面图片</i>
					</div>
					<div id="progress" class="progress">
						<div class="progress-bar progress-bar-success"></div>
					</div>
					<p class="appmsg_desc"></p>
				</div>
			</div>
		</div>

		<div class="media_edit_area" id="js_appmsg_editor">
			<div class="appmsg_editor" style="margin-top: 0px;">
				<div class="inner">
					<form id="form1">
						<input id="seqId" name="seqId" type="hidden" value=""> <input
							id="newsid" name="newsid" type="hidden" value=""> <input
							id="organization" name="organization" type="hidden" value="">
						<table style="width: 100%" cellpadding="0" cellspacing="1"
							class="formtable">
							
						<tr>
							<td align="right">
								<label class="Validform_label"> 图文类型: </label>
							</td>
							<td class="value">
								<select id="itemtype" name="itemtype" style="width: 300px" class="select" onclick="newitemChange()" >
									<option value="1">文本内容</option>
									<option value="2">外部链接</option>
								</select>
								<span class="Validform_checktip"></span> 
								<label class="Validform_label" style="display: none;">图文类型</label>
							</td>
						</tr>
							
							<tr>
								<td align="right"><label class="Validform_label">
										标题: </label></td>
								<td class="value"><input id="title" name="title"
									type="text" style="width: 300px" class="inputxt" datatype="*">
									<span class="Validform_checktip"></span> <label
									class="Validform_label" style="display: none;">标题</label></td>
							</tr>
							<tr id="authorTr">
								<td align="right"><label class="Validform_label">
										作者: </label></td>
								<td class="value"><input id="author" name="author"
									type="text" style="width: 300px" class="inputxt"> <span
									class="Validform_checktip"></span> <label
									class="Validform_label" style="display: none;">作者</label></td>
							</tr>
							<tr>
								<td align="right"><label class="Validform_label">
										图片链接: </label></td>
								<td class="value"><span
									class="btn btn-success fileinput-button"> <i
										class="glyphicon glyphicon-plus"></i> <span>浏览</span> <!-- The file input field used as target for the file upload widget -->
										<input id="fileupload" type="file" name="files[]" multiple>
										<input id="imagepath" name=imagepath type="hidden"
										datatype="*" nullmsg="请添加图片" value="">
								</span> <span id="imgName"></span> <span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">图片链接</label>
								</td>
							</tr>
							<tr id="descriptionTr">
								<td align="right"><label class="Validform_label">
										摘要: </label></td>
								<td class="value"><input id="description"
									name="description" type="text" style="width: 300px"
									class="inputxt"> <span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">摘要</label>
								</td>
							</tr>
							<tr id="urlTr">
								<td align="right"><label class="Validform_label">
										外部链接: </label></td>
								<td class="value"><input id="url" name="url" type="text"
									style="width: 300px" class="inputxt"> <span
									class="Validform_checktip"></span> <label
									class="Validform_label" style="display: none;">外部链接</label></td>
							</tr>
							<tr id="contentTr">
								<td align="right"><label class="Validform_label">
										正文: </label></td>
								<td class="value"><textarea name="content" id="content"></textarea>
									<script type="text/javascript">
							        var editor = UE.getEditor('content',{initialFrameHeight:320,initialFrameWidth:400});
							    </script></td>
							</tr>
							<tr>
								<td align="right"><label class="Validform_label">
										顺序: </label></td>
								<td class="value"><input id="sortno" name="sortno"
									type="text" style="width: 150px" class="inputxt" datatype="*"
									value="0"> <span class="Validform_checktip"></span> <label
									class="Validform_label" style="display: none;">顺序</label></td>
							</tr>
						</table>
					</form>
					<div class="btnGroup" style="text-align: center;">
						<button id="save" class="kqdsCommonBtn" onclick="submit();">保存</button>
					</div>
				</div>
				<i class="arrow arrow_out" style="margin-top: 0px;"></i> 
				<i class="arrow arrow_in" style="margin-top: 0px;"></i>
			</div>
		</div>
	</div>
</body>
<script type="text/Javascript" src="<%=contextPath%>/static/js/wechat/newsItem/newsItem.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script>
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var seqId = $.getUrlVar('newsid');
$(function() {
    var detailurl = '<%=contextPath%>/WXNewsItemAct/selectDetail.act?seqId=' + seqId;
    $.axse(detailurl, null,
    function(data) {
        var newsItem = data.data;
        loadData(newsItem); 
        var imgHtml = '<i style="height:160px;overflow:hidden;display:inline-block;width:100%;"><img src="<%=contextPath%>' + newsItem.imagepath + '" width="100%" height="160"></i>';
        $("#files").text("").html(imgHtml);;
        
        newitemChange();
    },
    function() {
        layer.alert('查询出错！' );
    });

    $("#seqId").val(seqId);
});

function newitemChange(){
	var val = $("#itemtype").val();
	if("1" == val){ // 图文
		$("#urlTr").hide();
		$("#authorTr").show();
		$("#description").show();
		$("#content").show();
	}else{ // 外部链接
		$("#urlTr").show();
		$("#authorTr").hide();
		$("#descriptionTr").hide();
		$("#contentTr").hide();
	}
}


function submit() {
    var seqId = $("#seqId").val();
    if ('' == seqId) {
        alert('seqId参数不能为空');
        return false;
    }

    var newsid = $("#newsid").val();
    if ('' == newsid) {
        alert('newsid参数不能为空');
        return false;
    }

    var title = $("#title").val();
    if ('' == title) {
        alert('请填写标题');
        return false;
    }

    var imagepath = $("#imagepath").val();
    if ('' == imagepath) {
        alert('请上传图片');
        return false;
    }

    var sortno = $("#sortno").val();
    if ('' == sortno) {
        alert('请填写序号');
        return false;
    }

    var param = $('#form1').serialize();
    var url = '<%=contextPath%>/WXNewsItemAct/insertOrUpdate.act?' + param;
    $.axseSubmit(url, null,
    function() {},
    function(r) {
        if (r.retState == "0") {
            layer.alert('保存成功'  ,
            function(index) {
                parent.refresh();
                parent.layer.close(frameindex); //再执行关闭 */
            });
        } else {
            layer.alert('保存失败'  );
        }
    },
    function() {
        layer.alert('保存失败' );
    });
}
</script>	