//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
var curWwwPath = window.document.location.href;
//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
var pathName = window.document.location.pathname;
var pos = curWwwPath.indexOf(pathName);
//获取主机地址，如： http://localhost:8083
var localhostPaht = curWwwPath.substring(0, pos);
//获取带"/"的项目名，如：/uimcardprj
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

var uploader;
//导入商品返回值
var data;
var picArray = [];
//页面中 的附件隐藏值得id,自适应
var attachmentId = "attachmentId";
var attachmentName = "attachmentName";
var usernames = "";
var usernameids = "";

function initParam(attachmentId, attachmentName) {
    this.attachmentId = attachmentId;
    this.attachmentName = attachmentName;
}

// 赋值 
function fz() {
    var attachmentIds = "";
    var attachmentNames = "";
    for (var i = 0; i < picArray.length; i++) {
        attachmentIds += picArray[i].attrId;
        attachmentNames += picArray[i].attrName;
    }
    jQuery("#" + attachmentId).val(attachmentIds);
    jQuery("#" + attachmentName).val(attachmentNames);
}

/*删除 附件数组  */
function delfj(attrId, objects) {
    picArray = picArray.filter(function(obj) {
        return attrId !== obj.attrId;
    });
    fz();
    var fileitem = jQuery(objects).parent().parent();
    var file = uploader.getFile(fileitem.attr("id"));
    if (typeof(file) != "undefined") {
        //添加队列中删除
        uploader.removeFile(file, true);
    }

    //移除列表
    fileitem.remove();
}
//非图片上传 ，不会显示缩略图
function uploadfileNotImg(url, afterSuccessDeal) {
    var $ = jQuery,
    $list = $('#fileList'),
    //  $btn = $('#ctlBtn'),
    state = 'pending',
    uploader;
    //初始化，实际上可直接访问Webuploader.upLoader
    uploader = WebUploader.create({
        // 自动上传。
        auto: true,
        swf: '../webuploader-0.1.5/Uploader.swf',
        // 文件接收服务端。
        server: url,
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#filePicker',
        formData: {
            imgtype: $("#imgtype").val(),
            organization:$("#organization").val()
        }
    });
    uploader.on('uploadBeforeSend',
    function(block, data) {
        // block为分块数据。  
        // file为分块对应的file对象。  
        var file = block.file;
        // 修改data可以控制发送哪些携带数据。  
        data.imgtype = $("#imgtype").val();
        data.organization = $("#organization").val();
    });
    // uploader添加事件，当文件被加入队列后触发
    uploader.on('fileQueued',
    function(file) {
        $list.hide();
    });
    // 文件上传过程中触发，携带上传进度，file表示上传的文件，percentage表示上传的进度
    uploader.on('uploadProgress',
    function(file, percentage) {
        //定义一个变量名创建进度模块
        var $li = $('#' + file.id),
        //找到$li下class为progress的，并定义为$percent------为什么先寻找在创建
        $percent = $li.find('.progress .progress-bar');
        //如果$percent没值，就创建进度条加入到对应的文件名下， 避免重复创建
        if (!$percent.length) {
            $percent = $('<div class="progress progress-striped active">' + '<div class="progress-bar" role="progressbar" style="width: 0%">' + '</div>' + '</div>').appendTo($li).find('.progress-bar');
        }
        //为进度模块添加弹出文本
        $li.find('p.state').text('上传中');
        //为进度模块创建读条的百分比
        $percent.css('width', percentage * 100 + '%');
    });
    //uploader触发事件，当上传成功事调用这个事件
    uploader.on('uploadSuccess',
    function(file, response) {
        var attach = {};
        var $li = $('#' + file.id);
        //记录后台的文件路径
        $('#' + file.id).addClass('upload-state-done');
        if (response.retState == "0") {
            attach.attrId = response.attrId;
            attach.attrName = response.attrName;
            data = response.data;
            layer.alert(data  );
            picArray.push(attach);
            
            // 回调方法   2017-09-07 add by yangsen
            if(afterSuccessDeal){
            	afterSuccessDeal();
            }
            
        } else {
            if (response.data) {
                data = response.data;
            }
            if (response.retMsrg) {
                data = response.retMsrg;
            }
            layer.alert(data  );
        }
        $error = $li.find('div.error');
        // 避免重复创建
        if (!$error.length) {
            $error = $('<div class="error"></div>').appendTo($li);
        }
        $error.html("上传成功<i class=\"fa fa-trash-o\" style=\"color:#654545;font-size:20px;float:right;margin-right:2px\" aria-hidden=\"true\" onclick=\"delfj('" + attach.attrId + "',this);\"></i>");
        fz();
    });
    //uploader触发事件，当上传失败时触发该事件
    uploader.on('uploadError',
    function(file) {
        //调用文件被加入时触发的事件，findstate，并添加文本为上传出错
        $('#' + file.id).find('p.state').text('上传出错');
    });
    //该事件表示不管上传成功还是失败都会触发该事件
    uploader.on('uploadComplete',
    function(file) {
        //调用
        $('#' + file.id).find('.progress').fadeOut();
    });
    //这是一个特殊事件，所有的触发都会响应到，type的作用是记录当前是什么事件在触发，并给state赋值
    uploader.on('all',
    function(type) {
        if (type === 'startUpload') {
            state = 'uploading';
        } else if (type === 'stopUpload') {
            state = 'paused';
        } else if (type === 'uploadFinished') {
            state = 'done';
        }
    });
    uploader.on('uploadAccept',
    function(obj, ret) {

});
    uploader.onError = function(code) {
        if (code == "Q_EXCEED_NUM_LIMIT") {
            layer.msg('添加的文件数量超出10个',
            function() {});
        } else if (code == "Q_EXCEED_SIZE_LIMIT") {
            layer.msg('文件总大小超出500M',
            function() {});
        } else if (code == "Q_TYPE_DENIED") {
            layer.msg('文件类型不满足',
            function() {});
        } else if (code == "F_DUPLICATE") {
            layer.msg('文件已存在',
            function() {});
        } else {
            layer.msg("文件添加失败，错误代码:" + code,
            function() {});
        }
    };
};
/*
* 服务器地址,成功返回,失败返回参数格式依照jquery.ajax习惯;
* 其他参数同WebUploader
*/
/* *******************图片上传 相关*start***************************** */
function uploadfile(url) {
    var $ = jQuery;
    $list = $('#fileList'),
    // 优化retina, 在retina下这个值是2
    ratio = window.devicePixelRatio || 1,
    // 缩略图大小
    thumbnailWidth = 100 * ratio;
    thumbnailHeight = 100 * ratio;
    // Web Uploader实例
    // 初始化Web Uploader
    uploader = WebUploader.create({
        // 自动上传。
        auto: true,
        // swf文件路径
        swf: '../webuploader-0.1.5/Uploader.swf',
        // 文件接收服务端。
        server: url,
        formData: {
            imgtype: $("#imgtype").val(),
            organization:$("#organization").val()
        },
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#filePicker',
        resize: false,
        duplicate: true,
        // duplicate :true, 
        // 只允许选择文件，可选。
        /*  
		accept: {
			title: 'Images',
			extensions: 'gif,jpg,jpeg,bmp,png',
			mimeTypes: 'image/*'
		}, */

        fileNumLimit: 10,
        fileSizeLimit: 500 * 1024 * 1024,
        //500M  
        fileSingleSizeLimit: 500 * 1024 * 1024 //4M   */
    });
    /*  
	uploader.addButton({
	    id: '#btnContainer',
	    innerHTML: '选择文件2'
	}); */

    uploader.on('uploadBeforeSend',
    function(block, data) {
        // block为分块数据。  
        // file为分块对应的file对象。  
        var file = block.file;
        // 修改data可以控制发送哪些携带数据。  
        data.imgtype = $("#imgtype").val();
        data.organization = $("#organization").val();
    });

    // 当有文件添加进来的时候
    uploader.on('fileQueued',
    function(file) {
        var $li = $('<div id="' + file.id + '" class="file-item thumbnail"><div class="info"><span class="fileName"><img class="imgShow">' + file.name + '</span></div></div>'),
        $img = $li.find('img');

        $list.append($li);

        // 创建缩略图
        uploader.makeThumb(file,
        function(error, src) {
            if (error) {
                //$img.replaceWith('<span>不能预览</span>');
                $img.replaceWith('');
                return;
            }

            $img.attr('src', src);
        },
        thumbnailWidth, thumbnailHeight);
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on('uploadProgress',
    function(file, percentage) {
        var $li = $('#' + file.id),
        $percent = $li.find('.progress span');

        // 避免重复创建
        if (!$percent.length) {
            $percent = $('<p class="progress"><span></span></p>').appendTo($li).find('span');
        }

        $percent.css('width', percentage * 100 + '%');
    });

    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on('uploadSuccess',
    function(file, response) {
        var attach = {};
        var $li = $('#' + file.id);
        //记录后台的文件路径
        $('#' + file.id).addClass('upload-state-done');
        if (response.retState == "0") {
            attach.attrId = response.attrId;
            attach.attrName = response.attrName;
            usernames = response.usernames;
            usernameids = response.usernameids;
            data = response.data;
            layer.alert(data  );
            // picArray=[];
            picArray.push(attach);
            // 上传成功
            // picArray+=response.imgUri+",";
        } else {
            if (response.data) {
                data = response.data;
            }
            if (response.retMsrg) {
                data = response.retMsrg;
            }
            layer.alert(data  );
        }
        $error = $li.find('div.error');
        // 避免重复创建
        if (!$error.length) {
            $error = $('<div class="error"></div>').appendTo($li);
        }
        var html = "<span class=\"fileName\">上传成功</span><i class=\"fa fa-trash-o\"  aria-hidden=\"true\" onclick=\"delfj('" + attach.attrId + "',this);\"></i>";
        html += "<i class=\"fa fa-download\" aria-hidden=\"true\" onclick=\"downLoad('" + attach.attrName + "','" + attach.attrId + "');\"></i>";
        $error.html(html);
        //$error.html("上传成功<i class=\"fa fa-trash-o\" style=\"color:#654545;font-size:20px;float:right;margin-right:2px\" aria-hidden=\"true\" onclick=\"delfj('"+attach.attrId+"',this);\"></i>");
        fz();
    });

    // 文件上传失败，现实上传出错。
    uploader.on('uploadError',
    function(file) {
        var $li = $('#' + file.id),
        $error = $li.find('div.error');
        // 避免重复创建
        if (!$error.length) {
            $error = $('<div class="error"></div>').appendTo($li);
        }

        $error.text('上传失败');
    });

    // 完成上传完了，成功或者失败，先删除进度条。
    uploader.on('uploadComplete',
    function(file) {
        $('#' + file.id).find('.progress').remove();
    });

    uploader.on('uploadAccept',
    function(obj, ret) {

});

    uploader.onError = function(code) {
        if (code == "Q_EXCEED_NUM_LIMIT") {
            layer.msg('添加的文件数量超出10个',
            function() {});
        } else if (code == "Q_EXCEED_SIZE_LIMIT") {
            layer.msg('文件总大小超出500M',
            function() {});
        } else if (code == "Q_TYPE_DENIED") {
            layer.msg('文件类型不满足',
            function() {});
        } else if (code == "F_DUPLICATE") {
            layer.msg('文件已存在',
            function() {});
        } else {
            layer.msg("文件添加失败，错误代码:" + code,
            function() {});
        }
    };

}
/* *******************图片上传 相关*** end*************************** */

/*
 * 编辑时填充附件
 * */
function fullAttach(attachmentName, attachmentId, module) {
    picArray = [];
    var fileList = jQuery("#fileList");
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
            picArray.push(attach);

            // 截取附件id去掉日期前缀
            // attachmentIds[i] = attachmentIds[i].substring(5,attachmentIds[i].length);
            var mediaType = isMedia(attachmentNames[i]);
            var imgUrl = getImgUrl(attachmentIds[i], attachmentNames[i], module);

            var showUrl = imgUrl;
            str += "<div id=\"" + attachmentIds[i] + "\" class=\"file-item thumbnail upload-state-done\" >";
            if (mediaType != "5") {
                //str+="<span>不能预览</span>";
                str += "<div class=\"info\" title=\"" + attachmentNames[i] + "\"><span class=\"fileName\">" + attachmentNames[i] + "</span></div>";
            } else {
                str += "<div class=\"info\" title=\"" + attachmentNames[i] + "\"><span class=\"fileName\">" + attachmentNames[i];
                str += "<img class=\"imgShow\" src=\"" + showUrl + "\" height=\"100px\" width=\"100px\" onclick=\"playImgVideo('" + mediaType + "','" + attachmentIds[i] + "','" + attachmentNames[i] + "','" + module + "')\"></span>";
                str += "</div>";
            }
            str += "<div class=\"error\"><span class=\"fileName\">上传成功</span><i class=\"fa fa-trash-o\" aria-hidden=\"true\" onclick=\"delfj('" + attach.attrId + "',this);\"></i>";
            str += "<i class=\"fa fa-download\"  aria-hidden=\"true\" onclick=\"downLoad('" + attach.attrName + "','" + attach.attrId + "');\"></i></div>";
            str += "</div>";
        }

    }

    fileList.append(str);
}

/*
 * 详情时展示附件，程序执行到这个方法时，参数后面的逗号，星号都已经去除
 * */
function fullAttachDetail(attachmentId, attachmentName, module) {
    var str = "";
    if (attachmentId != '' && attachmentName != '' && module != '') {
        var mediaType = isMedia(attachmentName);
        var imgUrl = getImgUrl(attachmentId, attachmentName, module);

        str += "<div id=\"" + attachmentId + "\" class=\"file-item thumbnail upload-state-done\" onclick=\"playImgVideo('" + mediaType + "','" + attachmentId + "','" + attachmentName + "','" + module + "')\">";
        str += "<img src=\"" + imgUrl + "\" height=\"100px\" width=\"100px\">";
        str += "<div class=\"info\" title=\"" + attachmentName + "\">" + attachmentName + "</div>";
        str += "</div>";

        jQuery("#fileList").append(str);
    }
    //  以下代码废弃不用	 yangsen 17-4-27
    //	var fileList = jQuery("#fileList");
    //	var attachmentIds = attachmentId.split(",");
    //	var attachmentNames = attachmentName.split("*");
    //	var str="";
    //	for(var i  =0;i < attachmentIds.length;i ++){
    //		if(attachmentIds[i] != '' && attachmentIds[i].length > 5){
    //			//初始化 附件 数组
    //			var attach = {};
    //			var sattachmentId = attachmentIds[i] + ",";
    //			var sattachmentName = attachmentNames[i] + "*";
    //			attach.attrId = sattachmentId;
    //			attach.attrName = sattachmentName;
    //			// picArray.push(attach);
    //			
    //			// 截取附件id去掉日期前缀
    //	 		// attachmentIds[i] = attachmentIds[i].substring(5,attachmentIds[i].length);
    //			var mediaType = isMedia(attachmentNames[i]);
    //			// var imgUrl  = projectName+'/upload/' + attachmentIds[i]+ '_' + attachmentNames[i];
    //			// var imgUrl  = imgurl + attachmentIds[i]+ '_' + attachmentNames[i];
    //			var imgUrl  = getImgUrl(attachmentIds[i],attachmentNames[i],module);
    //			
    //			var showUrl = imgUrl;
    //			if(mediaType != "5"){
    //				showUrl = projectName+'/hzhs/evidence/hs_evidence/video.png';
    //				// alert(showUrl); 
    //			}
    //			str+="<div id=\""+attachmentIds[i]+"\" class=\"file-item thumbnail upload-state-done\" onclick=\"playImgVideo('"+mediaType+"','"+attachmentIds[i]+"','"+attachmentNames[i]+"','"+module+"')\">";
    //			str+="<img src=\""+showUrl+"\" height=\"100px\" width=\"100px\">";
    //			str+="<div class=\"info\" title=\""+attachmentNames[i]+"\">"+attachmentNames[i]+"</div>";
    //			str+="</div>";
    //		}
    //	}
}

// 查看附件
function playImgVideo(mediaType, id, name, module) {
    var type = 2;
    var content = "";
    var url = getImgUrl(id, name, module);
    if (mediaType == "5") {
        type = 1;
        content = "<img width=\"100%\" height=\"100%\" src=\"" + url + "\"></img>";
    } else {
        content = projectName + "/KQDS_ImageDataAct/toPlayVideo.act?url=" + url;
    }
    layer.open({
        type: type,
        title: false,
        area: ['630px', '360px'],
        shade: [0.1, '#fff'],
        //0.1透明度的白色背景
        skin: 'layui-layer-nobg',
        //没有背景色
        closeBtn: 1,
        shadeClose: true,
        maxmin: true,
        content: content
    });
}

function isMedia(fileName) {
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
function isVideo(fileName) {
    var videoType = ["mp4", "mpg", "mpeg", "avi", "wmv", "asf", "dat"];
    var index = fileName.lastIndexOf(".");
    var extName = "";
    if (index >= 0) {
        extName = fileName.substring(index + 1).toLowerCase().trim()
    }
    if (videoType.contains(extName)) {
        return 1
    } else {
        return 0
    }
}

//跟ID和Name获取图片上传的路径 
function getImgUrl(id, name, module) {
	 // 截取附件id去掉日期前缀  1704_24e20f8e282a93628c91e8677ae8d259,
    var yearMonth = id.split('_')[0];
    var filename = id.split('_')[1] + '_' + name;
    var imgUrl = UPLOAD_URL + '/upload/' + module + '/' + yearMonth + '/' + filename;
    return imgUrl;
}