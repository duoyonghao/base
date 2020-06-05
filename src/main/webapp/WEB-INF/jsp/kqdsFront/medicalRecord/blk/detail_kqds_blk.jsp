<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<% 
	String seqId=request.getParameter( "seqId"); 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>病历库详情</title>
<style>
	.yaNumber{
		display:inline-block;
	}
	.content{
		padding-bottom: 20px;
	}
</style>
</head>
<body>
<div class="container-fluid">
	<section class="content">
		<div class="row">
			<div class="col-sm-12">
				<div class="box box-info">
					<form class="form-horizontal" id="form1">
						<div class="box-body" id="blcontent"></div>
					</form>
				</div>
			</div>
		</div>
	</section>
</div>
<script>
var seqId = "<%=seqId%>";
$(function() {
    var detailurl = '<%=contextPath%>/KQDS_BLKAct/selectDetail.act?seqId=' + seqId;
    $.axse(detailurl, null,
    function(data) {
        var davalul = data.data;
        getblcontent(davalul.seqId, davalul.mtype);
    },
    function() {
        layer.alert('查询出错！' );
    });
});
//获取增加行这种字段的内容
function getcontent(value, name) {
    var blcontent = "";
    if (value == "") { //体格检查
        blcontent += '<tr>' + '<th rowspan="2">' + name + '<br/></th>' + '<td width="30px" rowspan="2" colspan="1">1、</td>' + '<td width="50px"></td>' + '<td width="50px"></td>' + '<td rowspan="2" colspan="1">' + '</td>' + '</tr>';
        blcontent += '<tr>' + '<td></td>' + '<td></td>' + '</tr>';
    } else {
        //1;2;4;3||撒东窗事发大概|||1;2;2;4||打折的v风格|||
        var arr = value.split("|||");
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].length > 0) {
                var arrone = arr[i].split("||");
                var ywarr = arrone[0].split(";");
                var content = arrone[1];
                if (i > 0) {
                    name = '';
                }
                blcontent += '<tr>' + '<th rowspan="2">' + name + '<br/></th>' + '<td width="30px" rowspan="2" colspan="1">' + (i + 1) + '、</td>' + '<td width="50px"><span class="yaNumber">' + ywarr[0] + '</span></td>' + '<td width="50px"><span class="yaNumber">' + ywarr[1] + '</span></td>' + '<td rowspan="2" colspan="1">' + content + '</td>' + '</tr>';
                blcontent += '<tr>' + '<td><span class="yaNumber">' + ywarr[2] + '</span></td>' + '<td><span class="yaNumber">' + ywarr[3] + '</span></td>' + '</tr>';
            }
        }
    }
    return blcontent;
}
//取病历内容
function getblcontent(meid, mtype) {
    var detailurl = '<%=contextPath%>/KQDS_BLKAct/selectDetailContent.act?meid=' + meid + '&mtype=' + mtype;
    $.axse(detailurl, null,
    function(data) {
        var davalul = data.data;
        var blcontent = "";
        if (mtype == "0") { //初诊
            blcontent += '<table id="cz" width="100%" class=" table-condensed table-bordered blmb"  data-height="450">';
            blcontent += '<tr height="20px"><th width="70px"><p style="margin-top: 0px; margin-bottom: 0px; padding: 0px; white-space: normal;">主诉</p></th><td colspan="4">' + davalul.czzs + '</td></tr>';
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
            blcontent += '<tr><th>医嘱</th><td colspan="4">' + davalul.fzyz + '</td></tr>';
            blcontent += '<tr><th>备注</th><td colspan="4">' + davalul.fzremark + '</td></tr></table>';
        }
        $("#blcontent").html(blcontent);
    },
    function() {
        layer.alert('查询出错！'  );
    });
}
</script>
</body>

</html>
