<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	//只有院长才能编辑病历
	String yuanzhang = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_YUANZHANG_SEQID);
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);

	String currPersonId = person.getSeqId();
	String perPriv = person.getUserPriv();
	// 此方法用于页面弹出 cameraOnline_blk.jsp时 ，上传拍照的图片后，如何返回 病历-影像资料
	String blflag = request.getParameter("blflag");
	if (blflag == null) {
		blflag = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>病历</title><!-- 右侧个人中心  中心 病历图标 点击进入的 主页面  -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/font-awesome/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/datepicker/datepicker3.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/history_record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>

<!--引入CSS-->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/style.css">
<script type="text/javascript" src="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/upload/uploadutil.js"></script>
<!-- 剪切板 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/medicalRecord/clipboard.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/medicalRecord/medicalrecord.js"></script>		
<style type="text/css">
.child-inline-block-middle > * {
	display: inline-block;
	vertical-align: middle;
}
.blmb input[type="text"] {
	width: 100%;
    height: 20px;
    line-height: 20px;
    padding: 0px 0px 0px 0px;
    border-style:none
}
.blmb textarea {
  width: 100%;
  border-style:none;
  height:45px;
  min-height: 20px;
  padding: 0px 0px 0px 0px;
} 
select {
    margin-top: 5px;
    border: solid 1px #e5e5e5;
    border-radius: 3px;
    height: 28px;
}
body{
	/* border-top:1px solid #ddd; */
	padding:0 5px 5px;
}
.tableHd{
	margin:0 auto 3px;
}
.tableHd .charDiv{ /* 罗马数字块  */
	clear:both;
	overflow:hidden;
	padding-left:15px;
	background:#fff;
	color:#333;
	border-top:1px solid #ddd;
}
.tableHd .charDiv ul{
	
}
.tableHd .charDiv ul li{/* 固定块中的文字 罗马数字 */
	padding:0;
}
.infoBd{		/* 容器container内的div */
	padding:5;
}

.fixedDivStyle{/* 固定时的样式 */
	/* width:100%; *//*  */
	position:fixed;
	top:0;
	
}
#topmenu{/* 首行按钮组 */
	margin-bottom:0;
	padding-bottom:5px;
}
#fixDiv{
	padding-top:5px;
	background:#fff;
}
.infoBd{
	border-bottom:none;
}
.fixed-table-container thead th .sortable{
	padding-right:8px;
}
.tableBox .tableHd > a{
	display:inline-block;
	box-sizing:border-box;
	height:26px;
	line-height:26px;
	background:#fff;
	color:#00A6C0;
	outline:none;
	padding:0 15px;
	border:1px solid #0054B5;
	border-radius:3px;
	margin-right:3px;
	text-decoration:none;
	cursor:pointer;
	text-align:center;
	margin-top:9px;
}
.fixed-table-container{
	border-left: 1px solid #ddd;
	border-right: 1px solid #ddd;
	border-bottom:1px solid #ddd;
	border-radius: 6px;
	/* border-top-left-radius: 6px;
	border-top-right-radius: 6px; */
	overflow: hidden;
}

</style>
</head>
<body>
<div id="container">
    <div class="infoBd" style="text-align: center;">
	<div class="databaseWrap">
	    <div class="tableBox">
			<form class="form-horizontal" id="form2">
				<input type="hidden" class="form-control" name="seqId" id="seqId"> <!--  如果seqId有值，说明是病历恢复 -->
				<input type="hidden" class="form-control" name="usercode">
				<input type="hidden" class="form-control" name="usertype">
				<input type="hidden" class="form-control" name="regno" id="regno">
				<input type="hidden" class="form-control" name="status" id="status">
				
				
				<div id="fixDiv">
					<div id="topmenu"></div> <!-- 存放常用病历库、添加标准病历库、添加自用病历库按钮 -->
		            <div class="tableHd">
						<ul class="tab">
						    <li class="current" id="jbxx" >病历资料</li>
						    <li id="yxzl" >影像资料</li>
						</ul>
						<label id="czlabel"><input target="tabIframeMedical" type="radio" name="mtype" value="0" id="czradio" checked="checked">初诊</label>
						<label id="fzlabel"><input target="tabIframeMedical" type="radio" name="mtype" value="1" id="fzradio">复诊</label>
						<a href="javascript:void(0);" class="optA clearRow" id="delRow">清除行</a>
						<a href="javascript:void(0);"class="optA addRow" id="addRow">增加行</a>
						<a href="javascript:void(0);"class="optA addRow" onclick="addblct()">病历词条</a>
						<div class="charDiv">
			        		<span style="float: left;font-weight: bold;">特殊字符：</span>
			        		<ul id="TeShuZiFuUL" style="text-align:center; margin: 0 atuo;">
			        			<li class="teshuzifu_li" data-clipboard-text="Ⅰ" value="Ⅰ" style="cursor: pointer;display:inline;">&nbsp;Ⅰ&nbsp;</li>
			        			<li class="teshuzifu_li" data-clipboard-text="Ⅱ" value="Ⅱ" style="cursor: pointer;display:inline;">&nbsp;Ⅱ&nbsp;</li>
			        			<li class="teshuzifu_li" data-clipboard-text="Ⅲ" value="Ⅲ" style="cursor: pointer;display:inline;">&nbsp;Ⅲ&nbsp;</li>
			        			<li class="teshuzifu_li" data-clipboard-text="Ⅳ" value="Ⅳ" style="cursor: pointer;display:inline;">&nbsp;Ⅳ&nbsp;</li>
			        			<li class="teshuzifu_li" data-clipboard-text="Ⅴ" value="Ⅴ" style="cursor: pointer;display:inline;">&nbsp;Ⅴ&nbsp;</li>
			        			<li class="teshuzifu_li" data-clipboard-text="Ⅵ" value="Ⅵ" style="cursor: pointer;display:inline;">&nbsp;Ⅵ&nbsp;</li>
			        			<li class="teshuzifu_li" data-clipboard-text="Ⅶ" value="Ⅶ" style="cursor: pointer;display:inline;">&nbsp;Ⅶ&nbsp;</li>
			        			<li class="teshuzifu_li" data-clipboard-text="Ⅷ" value="Ⅷ" style="cursor: pointer;display:inline;">&nbsp;Ⅷ&nbsp;</li>
			        			<li class="teshuzifu_li" data-clipboard-text="Ⅸ" value="Ⅸ" style="cursor: pointer;display:inline;">&nbsp;Ⅸ&nbsp;</li>
			        			<li class="teshuzifu_li" data-clipboard-text="Ⅹ" value="Ⅹ" style="cursor: pointer;display:inline;">&nbsp;Ⅹ&nbsp;</li>
			        			<li class="teshuzifu_li" data-clipboard-text="↑" value="↑" style="cursor: pointer;display:inline;">&nbsp;↑&nbsp;</li>
			        			<li class="teshuzifu_li" data-clipboard-text="↓" value="↓" style="cursor: pointer;display:inline;">&nbsp;↓&nbsp;</li>
			        			<li class="teshuzifu_li" data-clipboard-text="↖" value="↖" style="cursor: pointer;display:inline;">&nbsp;↖&nbsp;</li>
			        			<li class="teshuzifu_li" data-clipboard-text="↗" value="↗" style="cursor: pointer;display:inline;">&nbsp;↗&nbsp;</li>
			        			<li class="teshuzifu_li" data-clipboard-text="↘" value="↘" style="cursor: pointer;display:inline;">&nbsp;↘&nbsp;</li>
			        			<li class="teshuzifu_li" data-clipboard-text="↙" value="↙" style="cursor: pointer;display:inline;">&nbsp;↙&nbsp;</li>
			        		</ul>
		        		</div>
					</div>
				</div>
				<!-- 此div存放病历模板，初诊--复诊 -->
				<div class="tableBd" id="isjbxx">
					<iframe style="border: 1px solid #ddd;border-radius: 5px;" id="tabIframeMedical" name="tabIframeMedical" src="<%=contextPath%>/KQDS_MedicalRecordAct/toMedicalrecordCz.act"
						width="100%" height="550px;" frameborder="0" class="tabIframeMedical"></iframe>
				</div>
			</form>
			<div id="isyxzl" class="col-sm-12" style="display: none;">
				<iframe id="tabIframe" name="tabIframe" src="<%=contextPath%>/KQDS_ImageDataAct/toVideoBlk.act?type=bingli"
					width="100%" height="520px;" frameborder="0" class="tabIframe"></iframe>
			</div>
		</div>
	</div>
        
        <div id="formbtn" class="formBox">
        	<table class="tableContent" style="width: 100%;">
        		<tr>
        			<td style="text-align:right;"><span class="impText">* 病历分类：</span></td>
        			<td style="text-align:left;"><select class="dict" tig="blfl" name="blfl" id="blfl" style="width:100px"></select></td>
        			<td style="text-align:right;"><span class="impText">*	病程：</span></td>
        			<td style="text-align:left;">
        				<select name="bc" id="bc" style="width:100px"></select>
        				<input type="hidden"  id="doctor" name="doctor" placeholder="填写医生" value="<%=person.getSeqId() %>" />
        			</td>
        		</tr>
        	</table>
        </div>
        
        <div class="buttonBar" id="buttommenu" style="width:100%;text-align:center;">
        </div>
        
        <div class="biliHistory">
            <div class="tableHd">历史病历【双击查看详情】</div>
            <table id="table" class="table-striped table-condensed table-bordered"  data-height="350"></table>
        </div>
    </div>
</div></body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var onclickrowOobj = "";
var onclickrowOobj2 = "";
var listbutton = window.parent.listbutton; //父页面传值
var perPriv = '<%=perPriv%>';
var personroleother = "<%=person.getUserPrivOther()%>"; // 辅助角色
var yuanzhang = '<%=yuanzhang%>';
var regno = "";
var isdelreg = 0;
//选择增加行、删除行 时定位鼠标所在位置
//鼠标所在 列号
//var cellno = 0; 
//鼠标所在 行号 
var rowno = 0;
//增加行的名称
var addrowname = "";
//用于连续点击增加行
var rownoCheck = 0;
$(function() {
    onclickrowOobj = window.parent.onclickrowOobj;
    //如果选中的不是挂号记录
    if (onclickrowOobj == "" || onclickrowOobj.ifcost == null) {
        regno = onclickrowOobj.regno;
    } else {
        regno = onclickrowOobj.seqId;
        isdelreg = onclickrowOobj.del;
    }

    /* if (regno == null || regno == "" || regno == "null" || regno == "undefined") {
        layer.alert('请选择挂号记录' );
        return;
    } */

    getButtonPower();
    initDictSelectByClass(); // 病历分类、病历库分类
    $('#blfl').change(function() {
    	getSubDictSelectByParentCode(this.value,'bc');
    });
    //表格中tab的切换
    $('.tableBox').on('click', '.tab li',
    function() {
        $(this).addClass('current').siblings('li').removeClass('current');
    });
    $("#form2 input[name=regno]").val(regno);
    $("#form2 input[name=usercode]").val(onclickrowOobj.usercode);
    $("input:radio[name=mtype]").change(function() {
        if ($(this).val() == 1) {
            layer.confirm('确定重新填写病历？', {
                btn: ['确认', '取消'] //按钮
            },
            function() {
            	$('#tabIframeMedical').attr('src',contextPath+'/KQDS_MedicalRecordAct/toMedicalrecordFz.act');
                layer.closeAll('dialog');
                $("#seqId").val(""); // ########## 这个必须清空
            },
            function() {
                //取消后不选中单选按钮
                $("#fzradio").attr("checked", false);
                $("#czradio").trigger("click");
                layer.closeAll('dialog');
            });
        } else {
            layer.confirm('确定重新填写病历？', {
                btn: ['确认', '取消'] //按钮
            },
            function() {
            	$('#tabIframeMedical').attr('src',contextPath+'/KQDS_MedicalRecordAct/toMedicalrecordCz.act');
                layer.closeAll('dialog');
                $("#seqId").val(""); // ########## 这个必须清空
            },
            function() {
                //取消后不选中单选按钮
                $("#czradio").attr("checked", false);
                $("#fzradio").trigger("click");
                layer.closeAll('dialog');
            });

        }
    });
    $('#jbxx').on('click',
    function() {
        $("#isyxzl").attr("style", "display: none;");
        $("#isjbxx").show();
        //展示病历分类、病程、提交、打印病历按钮
        $("#formbtn").attr("style", "height:45px;visibility: visible;");
        $("#buttommenu").show();
    });
    $('#yxzl').on('click',
    function() {
        $("#isjbxx").hide();
        $("#isyxzl").attr("style", "display: inline;");
        //隐藏病历分类、病程、提交、打印病历按钮
        $("#formbtn").attr("style", "height:0px;visibility: hidden;");
        $("#buttommenu").hide();
    });
    document.addEventListener("scroll",scrollBody);/* 添加滚动事件 */
    gethisbl();

    // 子页面高度传给父页面
    adjustTable();

    $(window).resize(adjustTable);

    // 此方法用于页面弹出 cameraOnline_blk.jsp时 ，上传拍照的图片后，如何返回 病历-影像资料
    if ('<%=blflag%>' != '') {
        $("#yxzl").click();
    }

    // 病历填写时间
    $("#tmpcreatetime").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd hh:mm:ss',
        pickerPosition: "bottom-right"
    });

});
function gethisbl() {
    var pageurlhis = contextPath+'/KQDS_MedicalRecordAct/selectPageByUsercod.act?usercode=' + onclickrowOobj.usercode + '&status=1,2&mtype=0,1';
    $("#table").bootstrapTable({
        url: pageurlhis,
        dataType: "json",
        singleSelect: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        },
        onDblClickCell: function(field, value, row, td) {
            //双击事件 调用提交回访结果方法 
            detail(row.seqId, row.mtype);
        },
        columns: [{
            title: '病人编号',
            field: 'usercode',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '病历种类',
            field: 'mtype',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                var html = "";
                if (value == "1") {
                    html = '<span class="label label-success">复诊</span>';
                } else {
                    html = '<span class="label label-success">初诊</span>';
                }
                return html;

            }
        },
        {
            title: '影像资料',
            field: 'attachmentid',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                var attachId = row.attachmentid;
                var attachmentName = row.attachmentname;
                if (attachId != "") {
                    return "<center><button onclick=\"openAttach('" + row.usercode + "','" + regno + "')\" class=\"btn btn-xs btn-primary\"><i class=\"ace-icon fa fa-eye bigger-120\"></i></button></center>";
                } else {
                    return "";
                }
            }
        },
        {
            title: 'attachmentname',
            field: 'attachmentname',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            editable: true,
            formatter:function(value,row,index){
            	return "<span>"+value+"</span>"
            }
        },
        {
            title: '创建人',
            field: 'createusername',
            align: 'center',
            sortable: true,
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            formatter: function(value, row, index) {
                var x = "";
                if (row.status == "1" && row.createuser == '<%=person.getSeqId()%>') { // 暂存的病历才可以恢复
                    x += '<a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:red;" onclick="huifubingli(\'' + row.seqId + '\',\'' + row.mtype + '\')">恢复</a> ';
                }
                // 主角色或辅助角色是院长的话，可以编辑病历
                if (isStrInArrayStringEach(perPriv, yuanzhang) || isStrInArrayStringEach(yuanzhang, personroleother)) {
                    x += '<a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:red;" onclick="bzblkEdit(\'' + row.seqId + '\',\'' + row.mtype + '\',\'' + row.createuser + '\')">&nbsp;编辑</a> ';
                }
                return x;
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj2 = $('#table').bootstrapTable('getData')[index];
    });
}
</script>
</html>