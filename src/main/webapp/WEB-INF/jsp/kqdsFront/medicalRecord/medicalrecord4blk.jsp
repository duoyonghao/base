<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	//只有院长才能编辑病历
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	
	String organization = request.getParameter("organization");
	if (organization == null) {
		organization = "";
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
	border-bottom:1px solid #ddd;
	border-left:1px solid #ddd;
	border-right:1px solid #ddd;
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
</style>
</head>
<body>
<div id="container">
    <div class="infoBd" style="text-align: center;">
	<div class="databaseWrap">
	    <div class="tableBox">
			<form class="form-horizontal" id="form2">
				<input type="hidden" class="form-control" name="seqId" id="seqId"> <!--  如果seqId有值，说明是病历恢复 -->
				<input type="hidden" class="form-control" name="usertype">
				<input type="hidden" class="form-control" name="status" id="status">
				
				
				<div id="fixDiv">
					<div id="topmenu">
						<select class="dict dictBliK" tig="blkfl" name="blkfl" id="blkfl" style="width:100px;"></select>&nbsp;&nbsp;
						<a onclick="addbzk4back()" style="float:right;" href="javascript:void(0);" class="kqdsSearchBtn">添加标准病历库</a>
					</div> <!-- 存放常用病历库、添加标准病历库、添加自用病历库按钮 -->
		            <div class="tableHd">
						<ul class="tab">
						    <li class="current" id="jbxx" >新建病历模板</li>
						</ul>
						<label id="czlabel"><input target="tabIframeMedical" type="radio" name="mtype" value="0" id="czradio" checked="checked">初诊</label>
						<label id="fzlabel"><input target="tabIframeMedical" type="radio" name="mtype" value="1" id="fzradio">复诊</label>
						<a href="javascript:void(0);" class="optA clearRow" id="delRow" style="border:solid 1px #00A6C0;color:#00A6C0;">清除行</a>
						<a href="javascript:void(0);"class="optA addRow" id="addRow" style="border:solid 1px #00A6C0;color:#00A6C0;">增加行</a>
						
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
					<iframe id="tabIframeMedical" name="tabIframeMedical" src="<%=contextPath%>/KQDS_MedicalRecordAct/toMedicalrecordCz.act"
						width="100%" height="550px;" frameborder="0" class="tabIframeMedical"></iframe>
				</div>
			</form>
		</div>
	</div>
    </div>
</div></body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
//选择增加行、删除行 时定位鼠标所在位置
//鼠标所在 列号
//var cellno = 0; 
//鼠标所在 行号 
var rowno = 0;
//增加行的名称
var addrowname = "";
//用于连续点击增加行
var rownoCheck = 0;
var organization = '<%=organization%>';
$(function() {
    initDictSelectByClass(); // 病历分类、病历库分类
    //表格中tab的切换
    $('.tableBox').on('click', '.tab li',
    function() {
        $(this).addClass('current').siblings('li').removeClass('current');
    });
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
            },function() {
                //取消后不选中单选按钮
                $("#czradio").attr("checked", false);
                $("#fzradio").trigger("click");
                layer.closeAll('dialog');
            });

        }
    });
    document.addEventListener("scroll",scrollBody);/* 添加滚动事件 */

    // 子页面高度传给父页面
    adjustTable();

    $(window).resize(adjustTable);

});


//添加标准库
function addbzk4back() {
    if ($("select[name=blkfl]").val() == null || $("select[name=blkfl]").val() == "") {
        layer.alert('请选择分类名' );
        return;
    }
    addblk(0);
}

function addblk(type) {
    layer.prompt({
        title: '输入病历名称，并确认',
        formType: 0
    },
    function(pass, index) {
        layer.close(index);
        var url = contextPath+'/KQDS_BLKAct/insertOrUpdate4Back.act';
        var dataparam = getParam();
        dataparam.blname = pass;
        dataparam.blkfl = $("select[name=blkfl]").val();
        dataparam.type = type;
        dataparam.organization = organization; /** 门诊编号 **/
        msg = layer.msg('加载中', {
            icon: 16
        });
        $.axse(url, dataparam,
        function(data) {
            if (data.retState == "0") {
                layer.alert('保存成功', {
                      
                    end: function() {
                    	if(parent.refresh){
                    		parent.refresh()
                    		parent.layer.close(frameindex); //再执行关闭 
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
</html>