<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	String seqId = request.getParameter("seqId");
	String usercode = request.getParameter("usercode");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>病历详情</title><!-- 医疗中心 病历查询  > 详情链接   -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/style.css">
<script type="text/javascript" src="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/upload/uploadutil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<style type="text/css">
.aBtn{display: inline-block;margin-right:0px;padding: 0 7px;height: 28px;line-height: 28px;border: solid 1px #0e7cc7;border-radius:15px;color: #0e7cc7;}
.aBtn:hover{color: #fff;background: #117cca;text-decoration: none;}
table {
    border-collapse: collapse;
    border-spacing: 0;
}

th {
	text-align: left;
	vertical-align: middle;
	border: 1px solid #bbb;
}

tr{
	height:30px;
	border: 1px solid #bbb;
}

td{
	border: 1px solid #bbb;
}

span {
	font-size: 18px;
}

label.col-sm-1{
	padding-right:0;
}
.box-body .form-control[readonly],.box-body .form-control[disabled]{
	background:#fff;
}
</style>
	
</head>
<body>
<div class="container-fluid">
	<section class="content">
		<div class="row">
			<div class="col-sm-12">
				<!-- 档案基本信息 -->
				<div class="box box-info">
					<div class="box-header with-border">
						<h4 class="box-title">患者信息</h4>
					</div>
					<form class="form-horizontal" id="form1">
						<div class="box-body">
							<div class="form-group">
								<label for="usercode" class="col-sm-1 control-label">病人编号</label>
								<div class="col-sm-1">
									<input type="text" class="form-control" name="usercode"
										id="usercode" readonly="readonly" style="width: 120px;">
								</div>
								<label for="username" class="col-sm-1 control-label">姓名</label>
								<div class="col-sm-1">
									<input type="text" class="form-control" name="username"
										id="username"   readonly="readonly" style="width: 120px;">
								</div>
								 <label for="sex" class="col-sm-1 control-label">性别</label>
								<div class="col-sm-1">
									<select style="height:28px;padding:0 5px;" class="form-control" name="sex" id="sex" disabled="disabled" style="width:120px;">
										<option value=""></option>
										<option value="男">男</option>
										<option value="女">女</option>
									</select>
								</div>
								<label for="phonenumber1" class="col-sm-1 control-label">年龄</label>
								<div class="col-sm-1">
									<input type="text" class="form-control" name="age"
										id="age"  readonly="readonly" style="width: 120px;">

								</div>
								<label for="phonenumber1" class="col-sm-1 control-label">手机</label>
								<div class="col-sm-1">
									<input type="text" class="form-control" name="phonenumber1"
										id="phonenumber1"  readonly="readonly" style="width: 120px;">

								</div>
							</div>
						</div>
					</form>
				</div>
				
			    <!-- 病历基本信息 -->
				
				<div class="box-title" style="width:100px;float:left;"><a href="javascript:void(0);" id="jbxx" class="kqdsSearchBtn" role="button">基本信息</a></div>
				<div class="box-title" style="width:100px;float:left;"><a href="javascript:void(0);" id="yxzl" class="kqdsSearchBtn" role="button">影像资料</a></div>
				
				<select class="dict dictBliK" tig="blkfl" name="blkfl" id="blkfl" style="width:100px;"></select>
				<a onclick="addbzk()" href="javascript:void(0);" class="kqdsCommonBtn">添加标准病历库</a>
				<a onclick="addzyk()" href="javascript:void(0);" class="kqdsCommonBtn">添加自用病历库</a>
				
				<form class="form-horizontal" id="form2">
					<input type="hidden" class="form-control" name="seqId" id="seqId">
					<input type="hidden" class="form-control" name="usercode" id="usercode">
					<input type="hidden" class="form-control" name="regno" id="regno">
					<div id="isjbxx" style="padding-top:5px;clear:both;">
				       <div class="">
							<input type="radio" name="mtype" value="0" disabled="disabled">初诊
							<input type="radio" name="mtype" value="1" disabled="disabled">复诊
							&nbsp;&nbsp;&nbsp;&nbsp;病程记录：
							<select class="select dict" tig="blfl" name="blfl" id="blfl" style="width:127px;" disabled="disabled"></select>
							<select class="select"  name="bc" id="bc" style="width:127px;" disabled="disabled"></select>
							<!-- 
							&nbsp;&nbsp;&nbsp;&nbsp;医生：<span style="font-size:12px;" id="doctorDesc"></span>
							&nbsp;&nbsp;&nbsp;&nbsp;护士：<span style="font-size:12px;" id="nurseDesc"></span> -->
							&nbsp;&nbsp;&nbsp;&nbsp;填写时间：<span style="font-size:12px;" id="tmpcreatetime"></span>
						</div>
						<div class="box-body" id="blcontent">
					    </div>
				     </div>		   
					 <div id="isyxzl" style="padding-top:5px;clear:both;">
					  	<label for="imgtype" class="control-label">附件分类：&nbsp;&nbsp;</label>
						<select class="select dict" tig="YXFL" name="imgtype" id="imgtype" style="width:212px;">
						</select>
					 	<input type="hidden" id="attachmentid" name="attachmentid" value="">
						<input type="hidden" id="attachmentname" name="attachmentname" value="">
						<input type="hidden" id="yxflIds" name="yxflIds">
           				<input type="hidden" id="moduleIds" name="moduleIds"> <!-- 该字段涉及存储图片的路径 -->
           				
						<div class="form-group" id="uploader-demo">
						    <!--用来存放item-->
						    <div id="fileList" class="uploader-list"></div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</section>
</div>
<div style="text-align: center;padding-bottom:10px;">
	<a href="javascript:void(0);" style="margin-top:5px;" onclick="printbl()" class="kqdsSearchBtn">打印病历</a>
	
</div>
<script>
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var seqId = "<%=seqId%>";
var usercode = "<%=usercode%>";
var regno = "";
$(function() {
    initDictSelectByClass(); // 病历分类、影像分类
    $('#blfl').change(function() {
    	getSubDictSelect('blfl','bc');
    });

    getdata();

    $("#isyxzl").hide();
    $("#isjbxx").show();
    $('#jbxx').on('click',
    function() {
        $("#isyxzl").hide();
        $("#isjbxx").show();
    });
    $('#yxzl').on('click',
    function() {
        $("#isyxzl").show();
        $("#isjbxx").hide();
    });
});

function printbl() {
    /* 	parent.layer.open({
        type: 2,
        title: '打印',
        shadeClose: true,
        //点击遮罩关闭层
        area: ['95%', '95%'],
        content: contextPath + '/PrintAct/bingLiPdf4Print.act?seqId='+seqId
    });
	 */
    window.location.href = contextPath + '/PrintAct/bingLiPdf4Print.act?seqId=' + seqId;
    return false;
}
function getdata() {
    var detailurl = '<%=contextPath%>/KQDS_MedicalRecordAct/selectDetail.act?seqId=' + seqId;
    $.axse(detailurl, null,
    function(data) {
        var davalul = data.data;

        $("#tmpcreatetime").html(davalul.createtime);
        getblcontent(davalul.seqId, davalul.mtype);
        $("#form2 input[name=seqId]").val(davalul.seqId);
        usercode = davalul.usercode;
        $("#form2 input[name=usercode]").val(davalul.usercode);
        regno = davalul.regno;
        $("#form2 input[name=regno]").val(davalul.regno);
        $("input[name='mtype'][value=" + davalul.mtype + "]").attr("checked", true);
        $("#form2 input[name=content]").val(davalul.content);
        //ue.setContent(davalul.content);
        $("#blfl").val(davalul.blfl).trigger("change");
        $("#bc").val(davalul.bc).trigger("change");
        getJbxx();

        //查询该挂号下的影像资料
        var yxurl = '<%=contextPath%>/KQDS_ImageDataAct/selectDetailByUsercode.act?usercode=' + usercode;
        $.axse(yxurl, null,
        function(yxdata) {
            var ids = "";
            var names = "";
            var yxflIds = ""; // 存储yxfl字段的值，用于 点击分类，过滤显示
            var moduleIds = ""; // 该字段涉及存储图片的路径
            for (var i = 0; i < yxdata.data.length; i++) {
                if (yxdata.data[i].regno == "" || yxdata.data[i].regno == null) {
                    //获取不是挂号添加的那条影像资料的id 之后保存使用本id
                    seqId = yxdata.data[i].seqId;
                }
                ids = ids + yxdata.data[i].attachmentid;
                names = names + yxdata.data[i].attachmentname;
                yxflIds += yxdata.data[i].yxfl + ','; // 存储yxfl字段的值，用于 点击分类，过滤显示
                moduleIds += yxdata.data[i].infosort + ',';

            }
            ids = ids.substring(0, ids.length - 1); //去除最后一个星号
            names = names.substring(0, names.length - 1);
            yxflIds = yxflIds.substring(0, yxflIds.length - 1); //去除最后一个逗号
            moduleIds = moduleIds.substring(0, moduleIds.length - 1); //去除最后一个逗号
            // 赋值
            $("#form2 input[name=attachmentid]").val(ids);
            $("#form2 input[name=attachmentname]").val(names);
            $("#form2 input[name=yxflIds]").val(yxflIds);
            $("#form2 input[name=moduleIds]").val(moduleIds);

        },
        function() {
            layer.alert('查询出错！'  );
        });

        yxzl(); // 加载影像资料
    },
    function() {
        layer.alert('查询出错！'  );
    });
}
//获取增加行这种字段的内容
function getcontent(value, name) {
    var blcontent = "";
    if (value == "") {
        blcontent += '<tr>' + '<th rowspan="2">' + name + '<br/></th>' + '<td width="30px" rowspan="2" colspan="1">1、</td>' + '<td width="50px" style="height: 28px;"></td>' + '<td width="50px"></td>' + '<td rowspan="2" colspan="1">' + '</td>' + '</tr>';
        blcontent += '<tr>' + '<td style="height: 28px;"></td>' + '<td></td>' + '</tr>';
    } else {
        //1;2;4;3||撒东窗事发大概|||1;2;2;4||打折的v风格|||
        var arr = value.split("|||");
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].length > 0) {
                var arrone = arr[i].split("||");
                var ywarr = arrone[0].split(";");

                if (ywarr[0] == undefined) {
                    ywarr[0] = '';
                }
                if (ywarr[1] == undefined) {
                    ywarr[1] = '';
                }
                if (ywarr[2] == undefined) {
                    ywarr[2] = '';
                }
                if (ywarr[3] == undefined) {
                    ywarr[3] = '';
                }

                var content = arrone[1];
                if (content == undefined) {
                    content = '';
                }

                if (i > 0) {
                    name = '';
                }
                blcontent += '<tr>' + '<th rowspan="2">' + name + '<br/></th>' + '<td width="30px" rowspan="2" colspan="1">' + (i + 1) + '、</td>' + '<td width="50px" style="height: 28px;">' + ywarr[0] + '</td>' + '<td width="50px">' + ywarr[1] + '</td>' + '<td rowspan="2" colspan="1" style="word-wrap:break-word;word-break:break-all;">' + content + '</td>' + '</tr>';
                blcontent += '<tr>' + '<td>' + ywarr[2] + '</td>' + '<td style="height: 28px;">' + ywarr[3] + '</td>' + '</tr>';
            }
        }
    }
    return blcontent;
}
//取病历内容
function getblcontent(meid, mtype) {
    var detailurl = '<%=contextPath%>/KQDS_MedicalRecordAct/selectDetailContent.act?meid=' + meid + '&mtype=' + mtype;
    $.axse(detailurl, null,
    function(data) {
        var davalul = data.data;
        var blcontent = "";
        if (mtype == "0") { //初诊
            blcontent += '<table id="cz" width="100%">';
            blcontent += '<tr height="20px"><th width="70"><p style="margin-top: 0px; margin-bottom: 0px; padding: 0px; white-space: normal;">主诉</p></th><td colspan="4">' + davalul.czzs + '</td></tr>';
            blcontent += '<tr><th>现病史</th><td colspan="4">' + davalul.czxbs + '</td></tr>';
            blcontent += '<tr><th>既往史</th><td colspan="4">' + davalul.czjws + '</td></tr>';
            blcontent += '<tr><th>过敏史</th><td colspan="4">' + davalul.czgms + '</td></tr>';
            blcontent += '<tr><th>家族史</th><td colspan="4">' + davalul.czjzs + '</td></tr>';
            blcontent += '<tr><th>检验结果</th><td colspan="4">' + davalul.czjyjg + '</td></tr>';
            
            blcontent += getcontent(davalul.cztgjc, "体格检查");
            blcontent += getcontent(davalul.czfzjc, "辅助检查");
            
            blcontent += getcontent(davalul.czzd, "诊断");
            blcontent += getcontent(davalul.czzlfa, "治疗方案");
            blcontent += getcontent(davalul.czcl, "处理");
            blcontent += '<tr><th>医嘱</th><td colspan="4">' + davalul.czyz + '</td></tr>';
            blcontent += '<tr><th>备注</th><td colspan="4">' + davalul.czremark + '</td></tr></table>';
        } else { //复诊
            blcontent += '<table id="fz"  width="100%" class=" table-condensed table-bordered blmb"  data-height="450">';
            blcontent += '<tr height="20px"><th width="70px"><p style="margin-top: 0px; margin-bottom: 0px; padding: 0px; white-space: normal;">主诉</p></th><td colspan="4">' + davalul.fzzs + '</td></tr>';
            blcontent += '<tr><th>检验结果</th><td colspan="4">' + davalul.fzjyjg + '</td></tr>';

            blcontent += getcontent(davalul.fzjc, "检查");
            blcontent += getcontent(davalul.fzzd, "诊断");
            blcontent += getcontent(davalul.fzzlfa, "治疗方案");
            blcontent += getcontent(davalul.fzcl, "处理");
            blcontent += '<tr><th>备注</th><td colspan="4">' + davalul.fzremark + '</td></tr>';

            blcontent += '<tr><th>医嘱</th><td colspan="4">' + davalul.fzyz + '</td></tr>';
            blcontent += '</table>';
        }
        $("#blcontent").html(blcontent);
    },
    function() {
        layer.alert('查询出错！'  );
    });
}
function getJbxx() {
	var baseinfo = getBaseInfoByUserCode(usercode);
	if(baseinfo){
		$("#form1 input[name=usercode]").val(baseinfo.usercode);
        $('#form1 input[name=seqId]').val(baseinfo.seqId);
        $('#username').val(baseinfo.username);
        $('#phonenumber1').val(baseinfo.phonenumber1);
        $('#sex').val(baseinfo.sex);
        $('#age').val(baseinfo.age);
        $('#medicalhistory').val(baseinfo.medicalhistory);
	}
}
function yxzl() {
    seqId = $("#form2 input[name=seqId]").val();
    if ("" != seqId && null != seqId) {
        var attachmentId = $("#form2 input[name=attachmentid]").val();
        var attachmentName = $("#form2 input[name=attachmentname]").val();

        var yxflIds = $("#form2 input[name=yxflIds]").val();
        var moduleIds = $("#form2 input[name=moduleIds]").val();

        var attachmentIdArray = attachmentId.split(",");
        var attachmentNameArray = attachmentName.split("*");
        var yxflIdsArray = yxflIds.split(",");
        var moduleIdsArray = moduleIds.split(",");

        for (var i = 0; i < attachmentIdArray.length; i++) {

            if (attachmentIdArray[i] == '') {
                continue;
            }

            fullAttachDetail(attachmentIdArray[i], attachmentNameArray[i], moduleIdsArray[i]);

        }
    }
}
//弹出一个iframe层
$('#imgtype').on('change',
function() {

    jQuery("#fileList").html("");

    var value = $('#imgtype').val();

    var attachmentId = $("#form2 input[name=attachmentid]").val();
    var attachmentName = $("#form2 input[name=attachmentname]").val();
    var yxflIds = $("#form2 input[name=yxflIds]").val();
    var moduleIds = $("#form2 input[name=moduleIds]").val();

    var attachmentIdArray = attachmentId.split(",");
    var attachmentNameArray = attachmentName.split("*");
    var yxflIdsArray = yxflIds.split(",");
    var moduleIdsArray = moduleIds.split(",");

    for (var i = 0; i < attachmentIdArray.length; i++) {

        if (attachmentIdArray[i] == '') {
            continue;
        }

        if (value == '') {
            fullAttachDetail(attachmentIdArray[i], attachmentNameArray[i], moduleIdsArray[i]);
        } else {
            if (yxflIdsArray[i] == value) {
                fullAttachDetail(attachmentIdArray[i], attachmentNameArray[i], moduleIdsArray[i]);
            }
        }

    }
});


/************************** 添加病历库 ************************/
function addbzk() {
    if ($("select[name=blkfl]").val() == null || $("select[name=blkfl]").val() == "") {
        layer.alert('请选择分类名' );
        return;
    }
    addblk(0);
}
//添加字用库
function addzyk() {
    if ($("select[name=blkfl]").val() == null || $("select[name=blkfl]").val() == "") {
        layer.alert('请选择分类名' );
        return;
    }
    addblk(1);
}

function addblk(type) {
    layer.prompt({
        title: '输入病历名称，并确认',
        formType: 0
    },
    function(pass, index) {
        layer.close(index);
        var url = contextPath+'/KQDS_BLKAct/addBlk.act';
        var dataparam = {};
        dataparam.blname = pass;
        dataparam.blkfl = $("select[name=blkfl]").val();
        dataparam.type = type;
        dataparam.seqId = seqId;
        msg = layer.msg('加载中', {
            icon: 16
        });
        $.axse(url, dataparam,
        function(data) {
            if (data.retState == "0") {
                layer.alert('保存成功', {
                      
                    end: function() {
                    	if(parent.refresh){
                    	}
                    }
                });
            }
        },
        function() {
            layer.alert('保存失败！'  );
        });
    });
}
</script>



</body>
</html>
