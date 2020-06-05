<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String contextPath = request.getContextPath();
  if (contextPath.equals("")) {
    contextPath = "/kqds";
  }
  String usercode = request.getParameter("usercode");
  String username = request.getParameter("username");
  String regno = request.getParameter("regno");
  
%>
<!DOCTYPE html>
<html>
 <head>
 <title>内窥镜</title>
 <style type="text/css">
</style>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>拍照</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
</head>
<body>
	<div class="container" style="text-align:center;">
		<div id="webcam" > <!-- <img src="http://www.xarg.org/image/antenna.png" alt=""> --></div>
		<div id="btnDiv">
			<button type="button" class="btn btn-primary btn-lg" onclick=" webcam.capture();" >拍照</button>
			<button type="button" class="btn btn-success btn-lg" onclick=" upload();" >上传</button>
		</div> 
		<div style="padding:5px 0 5px;"><img id="showimg"></img></div>      
	</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/jQuery-webcam/jquery.webcam.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name);
    $(function() {
    	var pos = 0, ctx = null, saveCB, image = [];
    	var canvas = document.createElement("canvas");
    	canvas.setAttribute('width', 320);
    	canvas.setAttribute('height', 240);
    	if (canvas.toDataURL) {
    		ctx = canvas.getContext("2d");
    		image = ctx.getImageData(0, 0, 320, 240);
    		saveCB = function(data) {
	   			var col = data.split(";");
	   			var img = image;
	
	   			for(var i = 0;i < 320; i ++) {
	   				var tmp = parseInt(col[i]);
	   				img.data[pos + 0] = (tmp >> 16) & 0xff;
	   				img.data[pos + 1] = (tmp >> 8) & 0xff;
	   				img.data[pos + 2] = tmp & 0xff;
	   				img.data[pos + 3] = 0xff;
	   				pos += 4;
	   			}
	
	   			if (pos >= 4 * 320 * 240) {
	   				ctx.putImageData(img, 0, 0);
	   				//页面显示
	   				$("#showimg").attr("src",canvas.toDataURL("image/png"));
	   				pos = 0;
	   			}
    		};
    	} else {
    		saveCB = function(data) {
    			image.push(data);
    			pos+= 4 * 320;
    			if (pos >= 4 * 320 * 240) {
    				//$.post("/upload.php", {type: "pixel", image: image.join('|')});
    				pos = 0;
    			}
    		};
    	}

    	$("#webcam").webcam({
    		width: 320,
    		height: 240,
    		mode: "callback",
    		swffile: "<%=contextPath%>/kqdsFront/js/jQuery-webcam/jscam_canvas_only.swf",
    		onSave: saveCB,
    		onCapture: function () {
    			webcam.save();
    		},
    		debug: function (type, string) {
    		}
    	});

    });
    
    function upload(){
    	if(!$("#showimg").attr("src")){
    		 layer.alert('请先拍照！' );
    		 return;
    	}
    	var url="<%=contextPath%>/FileUploadAct/picUploadBASE64.act?module=blk";
		var requestData ={type: "data", image:$("#showimg").attr("src"),imgtype:173};//内窥镜
		$.axse(url,requestData, 
			function(data){
				//上传成功
				if(data.retState=="0"){
					saveDB(data.attrId,data.attrName);
				}
       		}, function(){
				layer.alert('上传失败！' );
			}
		);
    }
    function saveDB(attachmentId,attachmentName){
        var url = '<%=contextPath%>/KQDS_ImageDataAct/uploadyxzl.act';
        var urldata = {
        		usercode:"<%=usercode%>",
        		username:"<%=username%>",
        		regno:"<%=regno%>",
        		yxfl:"173",
        		infosort:"blk",
        		attachmentid:attachmentId,
        		attachmentname:attachmentName
        };
    	$.axse(url,urldata, 
   	         function(data){
	    		if(data.retState == "0"){
		        	layer.alert('上传成功', {
						  
						end: function () {
							parent.bingliClick();
							parent.layer.close(index);
						}
					});
	        	}
   	         },
   	         function(){
   	       	    layer.alert('上传失败！' );
   	     	 }
   	    );
    }
    

</script>
</html>