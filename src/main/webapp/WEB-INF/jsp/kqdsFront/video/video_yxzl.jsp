<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	//图片上传地址
	String UPLOAD_URL = YZSysProps.getProp(SysParaUtil.UPLOAD_URL);
	if(UPLOAD_URL == null){
		UPLOAD_URL = "";
	}
%>
<!DOCTYPE html>
<html style="height:100%">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>影像资料</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/font-awesome/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/video.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/slide.js"></script> <!--video.jsp页面专用 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
	
<style type="text/css">
<!-- 界面样式定制，最初的css文件不作改动  -->
#ctlBtn{
    width: 80px;
    height: 34px;
    position: relative;
    display: inline-block;
    right: 20px;
}
#filePicker{
    width: 80px;
    height: 34px;
    position: relative;
    display: inline-block;
    right: 20px;
}
</style>
</head>
<body style="height:100%">
<div id="container" style="padding:0 15px;">
	<div class="titleDiv" >
		<span class="title" id="topyxzl"></span>
	</div>
    <!-- <div class="tableHd" id="topyxzl"></div> -->
    <div class="infoBd" style="height:100%;width:98%;">
        <div class="photoWrap" id="tFocus" style="/* width:95%; */ width:100%;">
            <ul id="tFocus-pic" class="zoom"> <!-- 展示大图片 -->
            </ul>
            <div id="tFocusBtn" class="zoom">
                <a href="javascript:void(0);" id="tFocus-leftbtn">&lt;</a>
                <div id="tFocus-btn" style="width:85%;">
                    <ul id="upul"> <!-- 展示小图片 -->
                    </ul>
                </div>
                <a href="javascript:void(0);" id="tFocus-rightbtn">&gt;</a>
            </div>
        </div>
        <div class="bottomBar" style="/* width:95%; */ width:100%;">
        	<table width="100%">
        		<tr>
        			<td align="left">
        				<ul name="yxflul" ></ul>
						<input type="hidden" id="attachmentid" name="attachmentid">
            			<input type="hidden" id="attachmentname" name="attachmentname">
            			<input type="hidden" id="imgcreateusers" name="imgcreateusers">
            			<input type="hidden" id="yxflIds" name="yxflIds">
            			<input type="hidden" id="moduleIds" name="moduleIds"> <!-- 该字段涉及存储图片的路径 -->
        			</td>
        		</tr>
        		<tr>
        			<td align="center">
        				<div id="buttonmenu">
        					 <form enctype="multipart/form-data" method="post" id="fileForm">
		                    	<input id="selectfile" name="selectfile" type="file" accept="*" value="" style="display: none;"/>
		                     </form>
						</div>	
        			</td>
        		</tr>
        	</table>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var UPLOAD_URL ="<%=UPLOAD_URL%>";
var perId = "<%=person.getSeqId()%>";
var onclickrowobj = window.parent.onclickrowOobj; // 父页面传值，患者对象
var listbutton = window.parent.listbutton; // 父页面传值
var usercode = ""; // 患者编号
var username = ""; // 患者姓名
var yxfl_curr_value = ""; // 影像分类当前选中值
var isdelreg = 0; // 是否撤销挂号 0未撤销 1已撤销

var imgurl = "";
$(function() {
    if (onclickrowobj == null || onclickrowobj == "" || onclickrowobj == "null" || onclickrowobj == "undefined") {
        layer.alert('请选择患者' );
        return;
    }

    usercode = onclickrowobj.usercode;
    var user = getHzNameByusercodeTB(usercode); // 根据病历号获取 患者对象
    username = user.username;

    var sex = user.sex == null ? "": user.sex; // 用户档案表  sex直接存中文男、女
    var age = "";
    if (user.age != "0") {
        age = user.age;
    }
    $("#topyxzl").html("影像资料 - " + user.username + " " + sex + " " + age);

    getdata(); //查询影像资料列表，根据usercode
    showyx(); // 加载影像分类
    yxflTabFuc(); // 添加 影像分类tab 切换事件
    getButtonPower(); // 判断是否具有图片上传权限,并初始化下载组件
    setHeight();
    $(window).resize(function() {
        setHeight();
    });

});

function setHeight() {
    $('.photoWrap').height($("#container").height() - $(".bottomBar").height() - 50);
    $('#tFocus-pic').height($('.photoWrap').height() - 95);

}

/**
 * 添加 影像分类tab 切换事件
 */
function yxflTabFuc() {
    //切换影像分类 选中
    $('.bottomBar ul').on('click', 'li',
    function() {
        $(this).addClass('current').siblings('li').removeClass('current');

        /* yxfl_curr_value = $(this).val(); */  // 42af08ac-64fc-4892-ab15-79db754e58a3 ，这个值时，.val()取到的值为42
        yxfl_curr_value = $(this).attr("value");
        
        // 每次切换类型时 先清空两个ul里的内容
        $("#tFocus-pic").html("");
        $("#upul").html("");

        var ids = $("#attachmentid").val();
        var names = $("#attachmentname").val();
        var yxflIds = $("#yxflIds").val();
        var moduleIds = $("#moduleIds").val();
        var imgcreateusers = $("#imgcreateusers").val();

        var attachmentIds = ids.split(",");
        var attachmentNames = names.split("*");
        var yxflIdsArray = yxflIds.split(",");
        var moduleIdsArr = moduleIds.split(",");
        var imgcreateusersArr = imgcreateusers.split(",");

        var index_flag = 0; // 用于分类过滤时，判断哪个图片是第一个时使用
        for (var i = 0; i < attachmentIds.length; i++) {
            if (yxfl_curr_value == "") { // 加载全部
                if (attachmentIds[i] != '') {
                    var imgurl = getImgUrl(attachmentIds[i], attachmentNames[i], moduleIdsArr[i]); // 影像资料
                    if (i == 0) {
                        $("#tFocus-pic").append('<li class="active" imgcreateuser="'+imgcreateusersArr[i]+'" imgId="'+attachmentIds[i]+'" onclick="Focus();" ondblclick="openImge('+i+');"  style="height:100%;width:100%;"><img id="imgl'+i+'"  src="' + imgurl + '" style="height:100%;width:100%;"></li>');
                        $("#upul").append('<li class="active" onclick="Focus();" ><img   src="' + imgurl + '" width="125" height="88"/></li>');
                    } else {
                        $("#tFocus-pic").append('<li imgcreateuser="'+imgcreateusersArr[i]+'" imgId="'+attachmentIds[i]+'" onclick="Focus();"  ondblclick="openImge('+i+');" style="height:100%;width:100%;"><img id="imgl'+i+'"  src="' + imgurl + '" style="height:100%;width:100%;"></li>');
                        $("#upul").append('<li onclick="Focus();"><img  src="' + imgurl + '" width="125" height="88"/></li>');
                    }
                }
            } else {
                // 根据分类进行设计 
                if (attachmentIds[i] != '') {
                    // 更严谨的取值					
                    if (yxflIdsArray[i] == yxfl_curr_value) {
                        index_flag++; // 用于分类过滤时，判断哪个图片是第一个时使用
                        var imgurl = getImgUrl(attachmentIds[i], attachmentNames[i], moduleIdsArr[i]); // 影像资料
                        if (index_flag == 1) {
                            $("#tFocus-pic").append('<li class="active" imgcreateuser="'+imgcreateusersArr[i]+'" imgId="'+attachmentIds[i]+'" onclick="Focus();" ondblclick="openImge('+i+');" style="height:100%;width:100%;"><img id="imgl'+i+'"  src="' + imgurl + '" style="height:100%;width:100%;"></li>');
                            $("#upul").append('<li class="active" onclick="Focus();"><img  src="' + imgurl + '" width="125" height="88"/></li>');
                        } else {
                            $("#tFocus-pic").append('<li imgcreateuser="'+imgcreateusersArr[i]+'" imgId="'+attachmentIds[i]+'" onclick="Focus();" ondblclick="openImge('+i+');" style="height:100%;width:100%;"><img id="imgl'+i+'"  src="' + imgurl + '" style="height:100%;width:100%;"></li>');
                            $("#upul").append('<li onclick="Focus();"><img   src="' + imgurl + '" width="125" height="88"/></li>');
                        }
                    }
                }
            }
        }
    });
}
function openImge(i){
	 var naturalWidth = $("#imgl"+i)[0].naturalWidth;
     var naturalHeight = $("#imgl"+i)[0].naturalHeight;
     //+10 去掉滚动条
     naturalWidth=naturalWidth+10+"px";
     naturalHeight=naturalHeight+10+"px";
     var url = $("#imgl"+i)[0].src;
     var index= top.layer.open({
	 	    type: 2,
	 	    title:'原始图片',
	 	    area: ['80%', '80%'],
	 	    shade: [0.8, '#393D49'],
	 	    content:contextPath+'/KQDS_ImageDataAct/toOriginalmg.act?url='+url,
	 	    success: function(layero, index) {
	 	    	// parent.layer.iframeAuto(index);
	 	    }
   	 });
}

function getImgNaturalDimensions(img, callback) {
    var nWidth, nHeight;
    if (img.naturalWidth) { // 现代浏览器
        nWidth = img.naturalWidth;
        nHeight = img.naturalHeight;
    } else { // IE6/7/8
        var imgae = new Image();
        image.src = img.src;
        image.onload = function() {
            callback(image.width, image.height);
        }
    }
    return [nWidth, nHeight]
}
/**
 * 查询字典表中 影像分类，并显示
 */
function showyx() {
    var url = contextPath + "/YZDictAct/getListByParentCode.act?parentCode=YXFL";
    $.axse(url, null,
    function(data) {
        var list = data.list;
        if (list != null) {
            if (list.length > 0) {
                var ulstr = ""; // 拼接 ul 和 select 
                ulstr += '<li class="current" value="">全部</li>';
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    // 拼接 ul 和 select 
                    ulstr += '<li value="' + optionStr.seqId + '" tag="' + optionStr.seqId + '" style="cursor: pointer;">' + optionStr.dictName + '</li>';
                }
                $("ul[name=yxflul]").html(ulstr); // 初始化并显示 ul 和 select 
            }
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
}

/**
 * 根据usercode查询影像资料列表，并展示，目前用到这个页面的不止一处
 */
function getdata() {
    // 根据患者病历号查询所有，不区分病历
    var detailurl = '<%=contextPath%>/KQDS_ImageDataAct/selectDetailByUsercode.act?usercode=' + usercode;
    $.axse(detailurl, null,
    function(data) {
        var davalul = data.data;
        if (davalul != null && davalul.length > 0) { // 奇葩的判断
            /** ## 该段处理主要用于 图片分类显示 ## */
            var ids = "";
            var names = "";
            var yxflIds = ""; // 存储yxfl字段的值，用于 点击分类，过滤显示
            var moduleIds = ""; // 该字段涉及存储图片的路径
            var imgcreateusers = "";//存储图片上传人
            for (var i = 0; i < davalul.length; i++) {
                ids = ids + davalul[i].attachmentid;
                names = names + davalul[i].attachmentname;
                yxflIds += davalul[i].yxfl + ','; // 存储yxfl字段的值，用于 点击分类，过滤显示
                moduleIds += davalul[i].infosort + ',';
                imgcreateusers += davalul[i].createuser + ',';
            }
            ids = ids.substring(0, ids.length - 1); //去除最后一个逗号
            names = names.substring(0, names.length - 1); //去除最后一个星号
            yxflIds = yxflIds.substring(0, yxflIds.length - 1); //去除最后一个逗号
            moduleIds = moduleIds.substring(0, moduleIds.length - 1); //去除最后一个逗号
            imgcreateusers = imgcreateusers.substring(0, imgcreateusers.length - 1); //去除最后一个逗号
            $("#attachmentid").val(ids);
            $("#attachmentname").val(names);
            $("#yxflIds").val(yxflIds);
            $("#moduleIds").val(moduleIds);
            $("#imgcreateusers").val(imgcreateusers);

            /** ## 该段处理主要用于 图片分类显示 END ## */

            // 展示图片
            $("#tFocus-pic").html("");
            $("#upul").html("");

            /** ## 拼接后，再拆分 ## **/
            var attachmentIds = ids.split(",");
            var attachmentNames = names.split("*");
            var moduleIdsArr = moduleIds.split(",");
            var imgcreateusersArr = imgcreateusers.split(",");
            /** ## 拼接后，再拆分 END ## **/

            for (var i = 0; i < attachmentIds.length; i++) {
                var imgurl = getImgUrl(attachmentIds[i], attachmentNames[i], moduleIdsArr[i]);
                if (i == 0) {
                    $("#tFocus-pic").append('<li class="active" imgcreateuser="'+imgcreateusersArr[i]+'" imgId="'+attachmentIds[i]+'" style="height:100%;width:100%;" ondblclick="openImge('+i+');" ><img id="imgl'+i+'"  src="' + imgurl + '"  style="height:100%;width:100%;"></li>');
                    $("#upul").append('<li class="active" onclick="Focus();"><img src="' + imgurl + '" width="125" height="88"/></li>');
                } else {
                    $("#tFocus-pic").append('<li imgcreateuser="'+imgcreateusersArr[i]+'" imgId="'+attachmentIds[i]+'" style="height:100%;width:100%;" ondblclick="openImge('+i+');" ><img id="imgl'+i+'"  src="' + imgurl + '"  style="height:100%;width:100%;"></li>');
                    $("#upul").append('<li onclick="Focus();"><img src="' + imgurl + '" width="125" height="88"/></li>');
                }
            }
            $("#upul li").eq(0).click();
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
}
//跟ID和Name获取图片上传的路径 
function getImgUrl(id, name, module) {
    // 截取附件id去掉日期前缀  1704_24e20f8e282a93628c91e8677ae8d259,
    var yearMonth = id.split('_')[0];
    var filename = id.split('_')[1] + '_' + name;
    var imgUrl = UPLOAD_URL + '/upload/' + module + '/' + yearMonth + '/' + filename;
    return imgUrl;
}
//------------------------------------------------------------------图片上传-------------------------------------------------------
/**
 * 点击图片选择事件
 */
function selectFile() {
    $('#selectfile').trigger('click');
}

/**
 * 选择图片后，触发提交表单
 */
$("#selectfile").change(function() {
    var filePath = $("#selectfile").val();
    if ('' != filePath) {
        subimtFile();
    }
});

/**
 * 上传附件后，回调
 */
function subimtFile() {
    var form = $('#fileForm');
    var options = {
        url: UPLOAD_URL+"/FileUploadAct?module=yxzl&nkj=0",
        type: 'post',
        success: function(data) {
            if (data.retState == 0) {
            	$("#attachmentid").val(data.attrId);
                $("#attachmentname").val(data.attrName);
                var urldata = {
                        usercode: usercode,
                        username: username,
                        regno: "",
                        yxfl: yxfl_curr_value,
                        infosort: "yxzl",
                        attachmentid: data.attrId,
                        attachmentname: data.attrName
                };
                var retrunObj = getDataFromServer("KQDS_ImageDataAct/uploadyxzl.act", urldata);
                if (retrunObj.retState == 0) {
                	layer.alert('上传成功', {
                        end: function() {
                            window.location.reload();
                        }
                    });
                }else{
                	layer.alert('上传失败', {
                          
                        end: function() {
                            window.location.reload();
                        }
                    });
                }
            }
        }
    };
    form.ajaxSubmit(options);
}

//按钮权限
function getButtonPower() {
    var menubutton1 = "";
    for (var i = 0; i < listbutton.length; i++) {

        if (listbutton[i].qxName == "yxzl_xztp") { // 不是撤销挂号
            menubutton1 += ' <button onclick="selectFile();" class="kqdsCommonBtn">选择图片</button>';
            menubutton1 += ' <button id="delBtn" class="kqdsCommonBtn">删除图片</button>';
        }
        // 内窥镜
        if (listbutton[i].qxName == "yxzl_nkj") {
            menubutton1 += ' <button id="ctlBtn" class="kqdsCommonBtn"> 内  窥  镜 </button>';
        }
    }
    // 选择图片

    $("#buttonmenu").append(menubutton1);
}
// 删除图片
$(document).on("click", "#delBtn",
function() {
	var delImgId = "",imgcreateuser="";
	var activeObj = $("#tFocus-pic li");
	activeObj.each(function(){
	  	if($(this).css("opacity") == 1){
	  		delImgId = $(this).attr("imgId");
	  		imgcreateuser = $(this).attr("imgcreateuser");
	  	}
	});
	if (delImgId == "") {
        layer.alert('请选择需要删除的图片' );
        return false;
	}
	if (perId != imgcreateuser) {
        layer.alert('不能删除他人上传的图片' );
        return false;
	}
	
	layer.confirm('确定删除？', {
	       btn: ['确认', '放弃'] //按钮
	},
	function() {
		var serverData = getDataFromServer("KQDS_ImageDataAct/deleteObj.act?delImgId=" + delImgId);
		if(serverData.retState == "0"){
			 layer.alert('删除成功', {
	            end: function() {
                       window.location.reload();
                   }
	         });
		}else{
			 layer.alert('删除失败', {
		              
		     });
		}
	});
	
});
//内窥镜
$(document).on("click", "#ctlBtn",
function() {
    //弹出调用摄像头界面
    parent.layer.open({
        type: 2,
        title: '拍照',
        shadeClose: true,
        shade: 0.6,
        area: ['750px', '90%'],
        content: contextPath + '/KQDS_ImageDataAct/toCameraOnline_yxzl.act?usercode=' + usercode + '&username=' + username
    });
});
</script>



</html>
