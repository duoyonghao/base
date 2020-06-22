
/*********************** 上传代码 **************************************/

/**
 * 删除操作时，调用该方法，重新赋值
 */
function setFileNameAndIdValue() {
    var attachmentIds = "";
    var attachmentNames = "";
    for (var i = 0; i < fileArray.length; i++) {
        attachmentIds += fileArray[i].attrId;
        attachmentNames += fileArray[i].attrName;
    }
    jQuery("#attachmentid").val(attachmentIds);
    jQuery("#attachmentname").val(attachmentNames);
}

/**
 * 单个删除附件
 */
function delFj(attrId, objects) {
    fileArray = fileArray.filter(function(obj) {
        return attrId !== obj.attrId;
    });
    setFileNameAndIdValue();
    var fileitem = jQuery(objects).parent().parent();
    //移除列表
    fileitem.remove();
}

/**
 * 下载附件
 */
function downLoadFj(name, id) {
    var fileUrl = getFileUrl(id, name, fileModule);
    /*window.location.href = fileUrl;*/ /** 改为通过后台中转后，再下载，这样做是为了下载的文件名称，不带随机数前缀 **/
    window.location.href = contextPath + "/FileUploadAct/downLoadByURL.act?url=" + fileUrl;
    
}

/**
 * 上传完成后，显示附件列表
 */
function showFileList() {
    var attachmentId = $("#form1 input[name=attachmentid]").val();
    var attachmentName = $("#form1 input[name=attachmentname]").val();
    
    var fileList = jQuery("#fileList"); // 清空
    fileList.empty();
    if (null != attachmentId && "" != attachmentId) {
        //附件填充
        createFileListUL(attachmentName, attachmentId, fileModule);
    }
}

/*
 * 编辑时填充附件
 * */
function createFileListUL(attachmentName, attachmentId, module) {
    fileArray = [];
    var fileList = jQuery("#fileList"); // 清空
    fileList.empty();
    var attachmentIds = attachmentId.split(",");
    var attachmentNames = attachmentName.split("*");
    var str = "";
    for (var i = 0; i < attachmentIds.length; i++) {
        if (attachmentIds[i] != '' && attachmentIds[i].length > 5) {
            //初始化 附件 数组
            var attach = {};
            var sattachmentId = attachmentIds[i] + ",";
            var sattachmentName = attachmentNames[i] + "*";
            attach.attrId = sattachmentId;
            attach.attrName = sattachmentName;
            fileArray.push(attach);

            // 截取附件id去掉日期前缀
            var mediaType = getFileType(attachmentNames[i]);
            var imgUrl = getFileUrl(attachmentIds[i], attachmentNames[i], module);

            var showUrl = imgUrl;
            str += "<div id=\"" + attachmentIds[i] + "\" class=\"file-item thumbnail upload-state-done\" >";
            if (mediaType != "5") {
                str += "<div class=\"info\" title=\"" + attachmentNames[i] + "\"><span class=\"fileName\">" + attachmentNames[i] + "</span></div>";
            } else {
                str += "<div class=\"info\" title=\"" + attachmentNames[i] + "\"><span class=\"fileName\">";
                str += "<img class=\"imgShow\" src=\"" + showUrl + "\" height=\"100px\" width=\"100px\" onclick=\"playImgOrVideo('" + mediaType + "','" + 
                attachmentIds[i] + "','" + attachmentNames[i] + "','" + module + "')\">"+ attachmentNames[i] +"</span>";
                str += "</div>";
            }
            str += "<div class=\"error\"><span class=\"fileName\">上传成功</span><i class=\"fa fa-trash-o\" aria-hidden=\"true\" onclick=\"delFj('" + attach.attrId + "',this);\"></i>";
            if (mediaType != "5") {
            	str += "<i class=\"fa fa-download\"  aria-hidden=\"true\" onclick=\"downLoadFj('" + attachmentNames[i] + "','" + attachmentIds[i] + "');\"></i></div>";
            }else{
            	str += "<i class=\"fa \"  aria-hidden=\"true\" onclick=\"javascript:void(0);\"></i></div>";
            }
            
            str += "</div>";
        }

    }

    fileList.append(str);
}

/**
 * 点击图片选择事件
 */
function selectFile4Upload() {
    $('#selectfileInput').trigger('click');
}

/**
 * 选择图片后，触发提交表单
 */
$("#selectfileInput").change(function() {
    var filePath = $("#selectfileInput").val();
    if ('' != filePath) {
        uploadSelectFile();
    }
});

/**
 * 上传附件后，回调
 */
function uploadSelectFile() {
    var form = $('#fileForm');
    var options = {
        url: UPLOAD_URL + "/FileUploadAct?module=" + fileModule + "&nkj=0",
        type: 'post',
        success: function(data) {
            if (data.retState == 0) {
                var attachmentid = $("#attachmentid").val();
                var attachmentname = $("#attachmentname").val();
                if (attachmentid == null || attachmentid == '') {
                    $("#attachmentid").val(data.attrId);
                    $("#attachmentname").val(data.attrName);
                } else {
                    if (endWidth(attachmentid, ',')) { // 如果以逗号结尾
                        $("#attachmentid").val(attachmentid + data.attrId);
                        $("#attachmentname").val(attachmentname + data.attrName);
                    } else {
                        $("#attachmentid").val(attachmentid + ',' + data.attrId);
                        $("#attachmentname").val(attachmentname + ',' + data.attrName);
                    }
                }
                // 上传后，显示图片
                showFileList();
            }
        }
    };
    form.ajaxSubmit(options);
}

/**
 * 弹框显示图片或视频
 */
function playImgOrVideo(mediaType, id, name, module) {
    var type = 2;
    var content = "";
    var url = getFileUrl(id, name, module);
    if (mediaType == "5") { // 图片
        type = 1;
        content = "<img width=\"100%\" height=\"100%\" src=\"" + url + "\"></img>";
    } else {
        // content = projectName + "/js/uploadfile/playvideo.jsp?url=" + url; // playvideo.jsp 这个页面不存在 ys 20181006
    }
    layer.open({
        type: type,
        title: false,
        area: ['80%', '80%'],
        shadeClose: true,
        content: content
    });
}

function getFileType(fileName) {
    var MEDIA_REAL_TYPE = "rm,rmvb,ram,ra,mpa,mpv,mps,m2v,m1v,mpe,mov,smi";
    var MEDIA_MS_TYPE = "wmv,asf,mp3,mpg,mpeg,mp4,avi,wmv,wma,wav,dat";
    var MEDIA_FLASH_TYPE = "flv,fla";

    var PIC_VIEW_TYPE = "jpg,jpeg,bmp,gif,png";
    var DIRECT_VIEW_TYPE = "xml,xhtml,html,htm,mid,mht,pdf,swf";
    var index = fileName.lastIndexOf(".");
    var extName = "";
    if (index >= 0) {
        extName = fileName.substring(index + 1).toLowerCase().trim()
    }
    if (MEDIA_REAL_TYPE.indexOf(extName) != -1) {
        return 1
    }
    if (MEDIA_MS_TYPE.indexOf(extName) != -1) {
        return 2
    }
    if (MEDIA_FLASH_TYPE.indexOf(extName) != -1) {
        return 4
    }
    if (DIRECT_VIEW_TYPE.indexOf(extName) != -1) {
        return 3
    }

    if (PIC_VIEW_TYPE.indexOf(extName) != -1) {
        return 5
    }
    return 0
}

//跟ID和Name获取图片上传的路径 
function getFileUrl(id, name, module) {
    // 截取附件id去掉日期前缀  1704_24e20f8e282a93628c91e8677ae8d259,
    var yearMonth = id.split('_')[0];
    var filename = id.split('_')[1] + '_' + name;
    var imgUrl = UPLOAD_URL + '/upload/' + module + '/' + yearMonth + '/' + filename;
    return imgUrl;
}