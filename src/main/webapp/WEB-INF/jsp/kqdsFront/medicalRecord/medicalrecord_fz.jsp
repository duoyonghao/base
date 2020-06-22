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
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/medicalRecord/medical.js"></script>	
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
</style>
</head>
<body>
	<!--复诊 -->
	<table id="medical" width="100%" class=" table-condensed table-bordered blmb"  data-height="450">
		<tr height="20px">
			<th width="50px">
			<p style="margin-top: 0px; margin-bottom: 0px; padding: 0px; white-space: normal;">主诉</p>
			</th>
			<td colspan="4"><input type="text"  name="fzzs"  id="fzzs" ></td>
		</tr>
		<tr>
			<th>检验<br/>结果</th>
			<td colspan="4"><textarea name="fzjyjg"  id="fzjyjg" ></textarea></td>
		</tr>
		
		<tr>
			<th rowspan="2">检查</th>
			<td width="20px" rowspan="2" colspan="1" onclick="openSelectTooth(this);"><input name="xh" type="text" value="1、" ></td>
			<td width="90px"><input type="text"  name="fzfzjc_1" ></td>
			<td width="90px"><input type="text"  name="fzfzjc_1" ></td>
			<td rowspan="2" colspan="1" onclick="czclick($(this),'fzfzjc');">
				<textarea name="fzfzjc_1"></textarea>
			</td>
		</tr>	
		<tr>
			<td><input type="text"  name="fzfzjc_1" ></td>
			<td><input type="text"  name="fzfzjc_1" ></td>	
		</tr>
		<tr>
			<th rowspan="2">诊断</th>
			<td rowspan="2" colspan="1" onclick="openSelectTooth(this);"><input name="xh" type="text" value="1、" ></td>
			<td><input type="text"  name="fzzd_1" ></td>
			<td><input type="text"  name="fzzd_1" ></td>
			<td rowspan="2" colspan="1" onclick="czclick($(this),'fzzd');">
				<textarea name="fzzd_1"></textarea>
			</td>
		</tr>	
		<tr>
			<td><input type="text"  name="fzzd_1" ></td>
			<td><input type="text"  name="fzzd_1" ></td>
		</tr>	
		<tr>
			<th rowspan="2">治疗<br/>方案</th>
			<td rowspan="2" colspan="1" onclick="openSelectTooth(this);"><input name="xh" type="text" value="1、" ></td>
			<td><input type="text"  name="fzzlfa_1" ></td>
			<td><input type="text"  name="fzzlfa_1" ></td>
			<td rowspan="2" colspan="1" onclick="czclick($(this),'fzzlfa');">
				<textarea name="fzzlfa_1"></textarea>
			</td>
		</tr>	
		<tr>
			<td><input type="text"  name="fzzlfa_1" ></td>
			<td><input type="text"  name="fzzlfa_1" ></td>
		</tr>
		<tr>
			<th rowspan="2">处理</th>
			<td rowspan="2" colspan="1" onclick="openSelectTooth(this);"><input name="xh" type="text" value="1、" ></td>
			<td><input type="text"  name="fzcl_1" ></td>
			<td><input type="text"  name="fzcl_1" ></td>
			<td rowspan="2" colspan="1" onclick="czclick($(this),'fzcl');">
				<textarea name="fzcl_1"></textarea>
			</td>
		</tr>	
		<tr>
			<td><input type="text"  name="fzcl_1" ></td>
			<td><input type="text"  name="fzcl_1" ></td>
		</tr>
		
		<tr>
			<th>医嘱</th>
			<td colspan="4"><textarea name="fzyz"  id="fzyz" ></textarea></td>
		</tr>
		
		<tr>
			<th>备注</th>
			<td colspan="4"><textarea name="fzremark"  id="fzremark" ></textarea></td>
		</tr>
	</table>
</body>
</script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var static_input_obj = null;
//选择增加行、删除行 时定位鼠标所在位置
//鼠标所在 列号
//var cellno = 0; 
//鼠标所在 行号 
var rowno = 0;
//增加行的名称
var addrowname = "";
//用于连续点击增加行
var rownoCheck = 0;
var subSeqId = null; // 病历具体内容表主键
/* 获取病历内容 */ 
function getParamMedical(param) {
	param.fzzs = $('#fzzs').val();//主诉
	param.fzjc = getValue('fzfzjc'); //检查
    param.fzzd = getValue('fzzd');//诊断
    param.fzcl = getValue('fzcl');//处理
    param.fzzlfa = getValue('fzzlfa');//治疗方案
    param.fzremark = $('#fzremark').val();//备注
    param.fzjyjg = $('#fzjyjg').val();//检验结果
    param.fzyz = $('#fzyz').val();//医嘱
    param.subSeqId = subSeqId;
    
	return param;
}
/* 获取病历内容end */ 

/* 获取病历内容start--恢复 */ 
function getblcontent4HuiFu(meid, mtype) {
    var content = "";
    var detailurl = '<%=contextPath%>/KQDS_MedicalRecordAct/selectDetailContent.act?meid=' + meid + '&mtype=' + mtype;
    $.axse(detailurl, null,
    function(data) {
        var davalul = data.data;

        subSeqId = davalul.seqId; // 病历内容主键
        var blcontent = "";
        content = "";
        //调用父页面方法才能触发父页面的change事件
        parent.setBcfl(data.blfl,data.bc);
        //子页面赋值 start
        content += '<tr height="20px"><th width="70px"><p style="margin-top: 0px; margin-bottom: 0px; padding: 0px; white-space: normal;">主诉</p></th><td colspan="4"><input type="text"  name="fzzs"  id="fzzs" value="' + davalul.fzzs + '"></td></tr>';
        content += '<tr><th>检验结果</th><td colspan="4"><textarea name="fzjyjg"  id="fzjyjg" >' + davalul.fzjyjg + '</textarea></td></tr>';

        content += getcontent2HuiFu(davalul.fzjc, "检查", "fzfzjc");
        content += getcontent2HuiFu(davalul.fzcl, "诊断", "fzzd");
        content += getcontent2HuiFu(davalul.fzzlfa, "治疗方案", "fzzlfa");
        content += getcontent2HuiFu(davalul.fzcl, "处理", "fzcl");

        content += '<tr><th>医嘱</th><td colspan="4"><textarea name="fzyz"  id="fzyz" >' + davalul.fzyz + '</textarea></td></tr>';

        content += '<tr><th>备注</th><td colspan="4"><textarea name="fzremark"  id="fzremark" >' + davalul.fzremark + '</textarea></td></tr>';

        //子页面赋值 end
        $("#medical").html(content);
    },
    function() {
        layer.alert('查询出错！'  );
    }

    );

    return content;
}
/* 获取病历内容end--恢复 */ 
</script>
</html>