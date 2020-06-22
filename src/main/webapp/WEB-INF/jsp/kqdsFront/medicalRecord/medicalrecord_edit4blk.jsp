<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);

	String seqId = request.getParameter("seqId");
	if (seqId == null) {
		seqId = "";
	}

	String mtype = request.getParameter("mtype");
	if (mtype == null) {
		mtype = "";
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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/medicalRecord/medicalrecord.css"/>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script	type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
</head>
<body>
<div id="container">
    <div class="infoBd" style="margin-top:10px;text-align: center;">
	<div id="topmenu"></div> <!-- 存放常用病历库、添加标准病历库、添加自用病历库按钮 -->
	<div class="databaseWrap">
	    <div class="tableBox">
			<form class="form-horizontal" id="form2">
				<input type="hidden" class="form-control" name="seqId" value="<%=seqId%>"> <!-- 患者病历表主键 -->
				<input type="hidden" class="form-control" name="mtype" value="<%=mtype%>">
	            <div class="tableHd">
					<ul class="tab">
					    <li class="current" id="jbxx" >病历资料</li>
					</ul>
					<label id="czlabel"><input type="radio" name="mtype" value="0" id="czradio" checked="checked" disabled="disabled">初诊</label>
					<label id="fzlabel"><input type="radio" name="mtype" value="1" id="fzradio" disabled="disabled">复诊</label>
					<a href="javascript:void(0);" class="optA clearRow"  onclick="delRow();" style="border:solid 1px #00A6C0;color:#00A6C0;">清除行</a>
					<a href="javascript:void(0);" class="optA addRow"    onclick="addRow();" style="border:solid 1px #00A6C0;color:#00A6C0;">增加行</a>
				</div>
			    <!--初诊 -->
			    <table id="cz" class="table-condensed table-bordered">
				</table>
				<!--复诊 -->
				<table id="fz" class=" table-condensed table-bordered">
				</table>
			</form>
		</div>
	</div>
        
	<div class="buttonBar" id="buttommenu" style="width:100%;text-align:center;">
		<a href="javascript:void(0);" onclick="saveform2(2)" class="kqdsCommonBtn" id="tjbl">提交病历</a>
	</div>
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var seqId = '<%=seqId%>'; // 病历表主键
var mtype = '<%=mtype%>'; // 病历表的病历分类字段 
var blflvalue = ""; // 病历表的病程分类字段
var bcvalue = ""; // 病历表的病程字段
var subSeqId = null; // 病历具体内容表主键
//鼠标所在 行号 （选择增加行、删除行 时定位鼠标所在位置）
var rowno = 0;
//增加行的字段名称
var addrowname = "";
//用于连续点击增加行
var rownoCheck = 0;

var frameindex = parent.layer.getFrameIndex(window.name);

$(function() {

    getdata();

    $("input[name='mtype'][value=" + mtype + "]").attr("checked", true);

});

//根据病历表主键，获取主表内容
function getdata() {
    var detailurl = '<%=contextPath%>/KQDS_BLKAct/selectDetail.act?seqId=' + seqId;
    $.axse(detailurl, null,
    function(data) {
        var davalul = data.data;
        blflvalue = davalul.blfl;
        // 根据病历表主键，获取病历具体内容
        getblcontent(davalul.seqId, davalul.mtype);

        $("#form2 input[name=seqId]").val(davalul.seqId);
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

//根据病历表主键，获取病历具体内容
function getblcontent(meid, mtype) {
    var detailurl = '<%=contextPath%>/KQDS_BLKAct/selectDetailContent.act?meid=' + meid + '&mtype=' + mtype;
    $.axse(detailurl, null,
    function(data) {
        var davalul = data.data;

        subSeqId = davalul.seqId; // 病历内容主键，有可能是初诊，也有可能是复诊
        content = "";
        if (mtype == "0") { //初诊
            //子页面赋值 start
            content += '<tr height="20px"><th style="width:10%;">主诉</th><td colspan="4"><input style="width:100%;" type="text" name="czzs"  id="czzs" value="' + davalul.czzs + '"></td></tr>';
            content += '<tr><th>现病史</th><td colspan="4"><input type="text" style="width:100%;" name="czxbs"  id="czxbs" value="' + davalul.czxbs + '"></td></tr>';
            content += '<tr><th>既往史</th><td colspan="4"><input type="text" style="width:100%;" name="czjws"  id="czjws" value="' + davalul.czjws + '"></td></tr>';
            content += '<tr><th>过敏史</th><td colspan="4"><input type="text" style="width:100%;" name="czgms"  id="czgms" value="' + davalul.czgms + '"></td></tr>';
            content += '<tr><th>家族史</th><td colspan="4"><input type="text" style="width:100%;" name="czjzs"  id="czjzs" value="' + davalul.czjzs + '"></td></tr>';
            content += '<tr><th>检验结果</th><td colspan="4"><textarea style="width:100%;" name="czjyjg"  id="czjyjg" >' + davalul.czjyjg + '</textarea></td></tr>';
            
            content += getcontent2(davalul.cztgjc, "体格检查", "cztgjc");
            content += getcontent2(davalul.czfzjc, "辅助检查", "czfzjc");
            
            content += getcontent2(davalul.czzd, "诊断", "czzd");
            content += getcontent2(davalul.czzlfa, "治疗方案", "czzlfa");
            content += getcontent2(davalul.czcl, "处理", "czcl");
            content += '<tr><th>医嘱</th><td colspan="4"><textarea style="width:100%;" name="czyz"  id="czyz" >' + davalul.czyz + '</textarea></td></tr>';
            content += '<tr><th>备注</th><td colspan="4"><textarea style="width:100%;" name="czremark"  id="czremark" >' + davalul.czremark + '</textarea></td></tr>';
            //子页面赋值 end
            $("#cz").html(content);

            $("#fz").attr("style", "display:none;");
            $("#cz").attr("style", "display:inline-block;");

        } else { //复诊
            content += '<tr height="20px" width="100%"><th width="70px">主诉</th><td colspan="4"><input style="width:100%;" type="text"  name="fzzs"  id="fzzs" value="' + davalul.fzzs + '"></td></tr>';
            content += '<tr><th>检验结果</th><td colspan="4"><textarea name="fzjyjg" style="width:100%;" id="fzjyjg" >' + davalul.fzjyjg + '</textarea></td></tr>';

            content += getcontent2(davalul.fzjc, "检查", "fzfzjc");
            content += getcontent2(davalul.fzzd, "诊断", "fzzd");
            content += getcontent2(davalul.fzzlfa, "治疗方案", "fzzlfa");
            content += getcontent2(davalul.fzcl, "处理", "fzcl");

            content += '<tr><th>医嘱</th><td colspan="4"><textarea style="width:100%;" name="fzyz"  id="fzyz" >' + davalul.fzyz + '</textarea></td></tr>';

            content += '<tr><th>备注</th><td colspan="4"><textarea style="width:100%;" name="fzremark"  id="fzremark" >' + davalul.fzremark + '</textarea></td></tr>';
            $("#fz").html(content);

            $("#cz").attr("style", "display:none;");
            $("#fz").attr("style", "display:inline-block;");

        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

//子页面赋值
function getcontent2(value, name, id) {
    var blcontent = "";
    if (value == "") {
        //子页面赋值 start
        blcontent += '<tr>' + '<th rowspan="2">' + name + '<br/></th>' + '<td rowspan="2" colspan="1"><input type="text" class="orderNumber" value="1、"></td>' + '<td><input type="text" class="toothPosition"  name="' + id + '_1" value=""></td>' + '<td><input type="text" class="toothPosition"  name="' + id + '_1" value=""></td>' + '<td rowspan="2" colspan="1" onclick="czclick($(this),\'' + id + '\');"><textarea class="textContent" name="' + id + '_1" ></textarea></td>' + '</tr>';
        blcontent += '<tr>' + '<td><input type="text" class="toothPosition" name="' + id + '_1" value=""></td>' + '<td><input type="text" class="toothPosition" name="' + id + '_1" value=""></td>' + '</tr>';
        //子页面赋值 end			
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
                //子页面赋值 start
                blcontent += '<tr>' + '<th rowspan="2">' + name + '<br/></th>' + '<td width="30px" rowspan="2" colspan="1"><input type="text" class="orderNumber" value="' + (i + 1) + '、"></td>' + '<td><input type="text" class="toothPosition" name="' + id + '_' + (i + 1) + '" value="' + ywarr[0] + '"></td>' + '<td><input type="text" class="toothPosition" name="' + id + '_' + (i + 1) + '" value="' + ywarr[1] + '"></td>' + '<td rowspan="2" colspan="1" onclick="czclick($(this),\'' + id + '\');"><textarea class="textContent" name="' + id + '_' + (i + 1) + '" >' + content + '</textarea></td>' + '</tr>';
                blcontent += '<tr>' + '<td><input type="text" class="toothPosition" name="' + id + '_' + (i + 1) + '" value="' + ywarr[2] + '"></td>' + '<td><input type="text" class="toothPosition" name="' + id + '_' + (i + 1) + '" value="' + ywarr[3] + '"></td>' + '</tr>';
                //子页面赋值 end
            }
        }
    }
    return blcontent;
}

function saveform2(status) {
    if ($("#blfl").val() == "") {
        layer.alert('病历分类不能为空' );
        return false;
    }
    if ($("#bc").val() == "") {
        layer.alert('病程不能为空' );
        return false;
    }
    //提交病历时  提示是否已签署知情同意书
    layer.confirm('确定要保存病历吗？', {
        btn: ['是', '否'] //按钮
    },
    function() {
        baocun();
    },
    function() {

});
}
function getParam(mtype) {
    var urldata = null;
    if (mtype == "0") {
        urldata = {
            seqId: $("#form2 input[name=seqId]").val(),
            subSeqId: subSeqId,
            mtype: $('#form2  input[name="mtype"]:checked ').val(),
            blfl: $("#blfl").val(),
            bc: $("#bc").val(),
            czzs: $('#czzs').val(),
            //主诉
            czxbs: $('#czxbs').val(),
            //现病史
            czjws: $('#czjws').val(),
            //既往史
            czgms: $('#czgms').val(),
            //过敏史
            czjzs: $('#czjzs').val(),
            //家族史
            cztgjc: getValue('cztgjc'),
            //体格检查
            czfzjc: getValue('czfzjc'),
            //辅助检查
            czzd: getValue('czzd'),
            //诊断
            czzlfa: getValue('czzlfa'),
            //治疗方案
            czcl: getValue('czcl'),
            //处理
            czremark: $('#czremark').val(),
            //检验结果
            czjyjg: $('#czjyjg').val(),
            //医嘱
            czyz: $('#czyz').val()
        };
    } else {
        urldata = {
            seqId: $("#form2 input[name=seqId]").val(),
            subSeqId: subSeqId,
            mtype: $('#form2  input[name="mtype"]:checked ').val(),
            blfl: $("#blfl").val(),
            bc: $("#bc").val(),
            fzzs: $('#fzzs').val(),
            //主诉
            fzjc: getValue('fzfzjc'),
            //检查
            fzzd: getValue('fzzd'),
            //处理
            fzcl: getValue('fzcl'),
          	//治疗方案
            fzzlfa: getValue('fzzlfa'),
            //处理
            fzremark: $('#fzremark').val(),
            //检验结果
            fzjyjg: $('#fzjyjg').val(),
            //医嘱
            fzyz: $('#fzyz').val()
        };

    }
    return urldata;
}

function getValue(name) {
    var value = "";
    //取得条数
    var length = $("input[name*='" + name + "']").length / 4;

    var count = 0;
    $("input[name*='" + name + "']").each(function() {
        var tmpName = $(this).attr("name");
        count++;

        if (count % 4 == 0) { // 这里之所以是4 因为 类型为input ，不包括textarea
            value += $(this).val() + "|| ";
            $("textarea[name='" + tmpName + "']").each(function(i) { // 该值正常只有一个
                value += $(this).val() + "|||";
            });
        } else {
            value += $(this).val() + ";";
        }

    });

    return value;
}
function baocun() {
    var url = '<%=contextPath%>/KQDS_BLKAct/updateBl.act';

    var urldata = getParam(mtype);
    $.axseSubmit(url, urldata,
    function() {},
    function(r) {
        if (r.retState == "0") {
            layer.alert('编辑成功', {
                  
                end: function() {
                    parent.layer.close(frameindex); //再执行关闭 */
                }
            });
        } else {
            layer.alert('编辑失败'  );
        }
    },
    function() {
        layer.alert('编辑失败' );
    });
}

// 点击某一个行时，获取改行的number
function czclick(obj, name) {
    //获取鼠标所在 列号
    //cellno = obj.parent().find("td").index(obj[0]); 
    //获取鼠标所在 行号 
    rowno = obj.parent().parent().find("tr").index(obj.parent()[0]);
    addrowname = name;

    rownoCheck = rowno;
}

function addRow() {
    if (rowno != rownoCheck) { // 连续点击，使最新的行放到前一个的底下
        rowno = rownoCheck;
    }

    if ($("#cz").is(":hidden")) {
        //获取序号
        var xh = getXHByItemName();
        //增加复诊表格行
        var trHTML = '<tr>' + '<th rowspan="2"</th>' + '<td width="30px" rowspan="2" onclick="openSelectTooth(this);" colspan="1"><input type="text" class="orderNumber" value="' + xh + '、"></td>' + '<td width="60px"><input type="text" class="toothPosition" name="' + addrowname + '_' + xh + '" ></td>' + '<td width="60px"><input type="text" class="toothPosition" name="' + addrowname + '_' + xh + '" ></td>' + '<td rowspan="2" colspan="1" onclick="czclick($(this),\'' + addrowname + '\');">' + '<textarea class="textContent" name="' + addrowname + '_' + xh + '"></textarea>' + '</td>' + '</tr>' + '<tr>' + '<td><input type="text" class="toothPosition"  name="' + addrowname + '_' + xh + '" ></td>' + '<td><input type="text" class="toothPosition" name="' + addrowname + '_' + xh + '" ></td>' + '</tr>';
        $("#fz tr:eq(" + (rowno + 1) + ")").after(trHTML); // 在table的第rowno行后面添加一行
    } else {
        //获取序号
        var xh = getXHByItemName();

        //增加初诊表格行
        var trHTML = '<tr>' + '<th rowspan="2"</th>' + '<td width="30px" rowspan="2" onclick="openSelectTooth(this);" colspan="1"><input type="text" class="orderNumber" value="' + xh + '、"></td>' + '<td width="60px"><input type="text" class="toothPosition" name="' + addrowname + '_' + xh + '" ></td>' + '<td width="60px"><input type="text" class="toothPosition" name="' + addrowname + '_' + xh + '" ></td>' + '<td rowspan="2" colspan="1" onclick="czclick($(this),\'' + addrowname + '\');">' + '<textarea class="textContent" name="' + addrowname + '_' + xh + '"></textarea>' + '</td>' + '</tr>' + '<tr>' + '<td><input type="text" class="toothPosition" name="' + addrowname + '_' + xh + '" ></td>' + '<td><input type="text" class="toothPosition" name="' + addrowname + '_' + xh + '" ></td>' + '</tr>';
        $("#cz tr:eq(" + (rowno + 1) + ")").after(trHTML); // 在table的第rowno行后面添加一行
    }

    rownoCheck = rowno + 2;

}

// 获取最大的序号，这里指的不是 当前一共几行 ，而是Name的下标
function getXHByItemName() {

    var currMaxXh = 0;

    $("input[name*='" + addrowname + "']").each(function() {

        var currName = $(this).attr("name");

        var currxh = currName.split("_")[1];

        var xhnum = Number(currxh);

        if (xhnum > currMaxXh) {
            currMaxXh = xhnum;
        }
    });

    return currMaxXh + 1;
}

function delRow() {
    if ($("#cz").is(":hidden")) { //删除复诊表格行
        //获取序号
        var xh = $("#fz tr:eq(" + (rowno) + ")").find("td").eq(0).find("input").eq(0).val();
        //去除、号
        if (xh.indexOf("、") > 0) {
            xh = xh.substring(0, xh.length - 1);
            if (xh != "1") {
                $('#fz tr:eq(' + rowno + ')').remove();
                $('#fz tr:eq(' + rowno + ')').remove();
            }
        }
    } else { //删除初诊表格行
        //获取序号
        var xh = $("#cz tr:eq(" + (rowno) + ")").find("td").eq(0).find("input").eq(0).val();
        //去除、号
        if (xh.indexOf("、") > 0) {
            xh = xh.substring(0, xh.length - 1);
            if (xh != "1") {
                $('#cz tr:eq(' + rowno + ')').remove();
                $('#cz tr:eq(' + rowno + ')').remove();
            }
        }
    }
}

</script>
</html>