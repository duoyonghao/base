<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>领料统计</title><!-- 该页面只做演示使用，未进行入库代码编写，已经被注释 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.min.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/defaults-zh_CN.js"></script>
</head>
<style>
select {
    padding: 0 10px;
    width: 120px;
    height: 28px;
    line-height: 28px;
    border: solid 1px #e5e5e5;
    border-radius: 3px;
    -webkit-transition: all .3s;
    transition: all .3s;
}
.time{
    max-width: 150px;
}
.tableBody{
		overflow-y:auto;
}
#container{
	padding:2px;
	overflow:hidden;
}
#hiddentable td,#hiddentable th{
	border:none;
}
.table-bordered > thead > tr > th, .table-bordered > tbody > tr > th, .table-bordered > tfoot > tr > th, .table-bordered > thead > tr > td, .table-bordered > tbody > tr > td, .table-bordered > tfoot > tr > td{
	border:none;
}
.table-bordered{
	border:none;
	border-top:1px solid #ddd;
}
#table tbody tr.active{/* 选中行时 为绿色  */
	background:#A9ECC7;
}
</style>
<body>
<div id="container">
   <!--查询条件-->
     <div class="searchWrap">
            <div class="cornerBox">查询条件</div>
            <div class="btnBar">
                <a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
                <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
            </div>
            <div class="formBox">
               	<div class="kv">
               		<div class="kv">
						<label>收费日期</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</span>
						</div>
					</div>
					<div class="kv" >
						 <label>*模糊查询</label>
						<div class="kv-v">
							   <input type="text" placeholder="患者编号/姓名/手机号/项目名称" id="queryinput" name="queryinput" style="width:200px;">
						</div>
					</div>
                </div>
            </div>
        </div> 
    <div class="tableHd">领料明细</div>
    <div class="tableBox" id="divkdxm" style="width: 100%;position:relative;">
	    <div class="tableHeader" style="position:absolute; top:0;left:0;height:30.4px;overflow:hidden;">
	    	<table id="hiddentable" class="table-striped table-condensed table-bordered" data-height="250" style="width: 100%;text-align: center;">
				<thead style="background: #00A6C0 ;color:white; ">
					<tr>
						<!-- <th style="text-align: center; vertical-align: middle;width:10%;height:30px;">领料编号</th> -->
						<th style="text-align: center; vertical-align: middle;width:10%;height:30px;">姓名</th>
						<th style="text-align: center; vertical-align: middle;width:10%;">编号</th>
						<th style="text-align: center; vertical-align: middle;width:8%;">消费分类</th>
						<th style="text-align: center; vertical-align: middle;width:17%;">消费项目</th>
						<th style="text-align: center; vertical-align: middle;width:5%;">单位</th>
						<th style="text-align: center; vertical-align: middle;width:5%;">数量</th>
						<th style="text-align: center; vertical-align: middle;width:;">材料名称</th>
						<th style="text-align: center; vertical-align: middle;width:5%;">数量</th>
						<th style="text-align: center; vertical-align: middle;width:12%;">领料时间</th>
						<th style="text-align: center; vertical-align: middle;width:6%;">是否治疗</th>
						<th style="text-align: center; vertical-align: middle;width:8%;">操作</th>
					</tr>
				</thead>
				<tbody style="background-color: #F0FFFF;text-align: center;"></tbody>
			</table>
	    </div>
	    <div class="tableBody">
	    	<table id="table" class="table-striped table-condensed table-bordered" data-height="250" style="width: 100%;text-align: center;">
				<thead style="background: #0e7cc9;color:white; ">
					<tr>
						<!-- <th style="text-align: center; vertical-align: middle;width:10%;height:30px;">领料编号</th> -->
						<th style="text-align: center; vertical-align: middle;width:10%;height:30px;">姓名</th>
						<th style="text-align: center; vertical-align: middle;width:10%;">编号</th>
						<th style="text-align: center; vertical-align: middle;width:8%;">消费分类</th>
						<th style="text-align: center; vertical-align: middle;width:17%;">消费项目</th>
						<th style="text-align: center; vertical-align: middle;width:5%;">单位</th>
						<th style="text-align: center; vertical-align: middle;width:5%;">数量</th>
						<th style="text-align: center; vertical-align: middle;width:;">材料名称</th>
						<th style="text-align: center; vertical-align: middle;width:5%;">数量</th>
						<th style="text-align: center; vertical-align: middle;width:12%;">领料时间</th>
						<th style="text-align: center; vertical-align: middle;width:6%;">是否治疗</th>
						<th style="text-align: center; vertical-align: middle;width:8%;">操作</th>
					</tr>
				</thead>
				<tbody style="background-color: #F0FFFF;text-align: center;"></tbody>
			</table>
	    </div>
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var chaiFenUrl = '<%=contextPath%>/KQDS_LLTJAct/chaiFen.act';
var treatDetaiListUrl = '<%=contextPath%>/KQDS_LLTJAct/getAllTreatDetailList.act';
var usercodechild = ""; //接收layer子窗口传值
var usernamechild = ""; //接收layer子窗口传值
var nowday = null;
$(function() {
    //时间选择
    $(".unitsDate input").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "bottom-right"
    });
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    // 比较起始和结束时间，如果结束时间大于起始时间，结束时间重置为开始时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
	$("#table").on("click","tbody tr",function(){
		$(this).addClass("active").siblings().removeClass();
	});
    loadDataList();
});

// 加载治疗项目清单
function loadDataList() {

    // 查询前先清空
    $("#table").find('tbody').html("");
	$("#hiddentable").find('tbody').html("");
    // 查询条件
    var queryData = {
        queryinput: $("#queryinput").val(),
        starttime: $("#starttime").val(),
        endtime: $("#endtime").val()
    };

    $.axse(chaiFenUrl, queryData,
    function(data) { // data = Object {retMsrg: "操作成功", retState: "0"}
        var htmlStr = "";
        if (data.retState == "0") {
            getTreatDetailList();
        }
    },
    function() {
        layer.alert('拆分失败！' );
    });
}

// 获取治疗明细列表
function getTreatDetailList() {
    // 查询条件
    var queryData = {
        queryinput: $("#queryinput").val(),
        starttime: $("#starttime").val(),
        endtime: $("#endtime").val()
    };

    $.axse(treatDetaiListUrl, queryData,
    function(data) {
        var htmlStr = "";
        var htmlStrHidden = "";
        if (data != null && data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                var trobj = data[i];
                htmlStr += initTrHtml(trobj);
                htmlStrHidden += initTrHtml4Hidden(trobj);
                
            }
        }
        $("#table").find('tbody').append(htmlStr); // 加载完成清单后，再加载材料
        $("#hiddentable").find('tbody').append(htmlStrHidden);
        initCaiLiaoTrs();
        $(".tableBody").outerHeight($(window).outerHeight()-$(".searchWrap").outerHeight()-$(".tableHd").outerHeight()-18);
        $("#hiddentable").outerWidth($("#table").outerWidth());
    },
    function() {
        layer.alert('操作失败！' );
    });
}

// 加载完治疗项目清单后，再加载每个项目对应的材料清单
function initCaiLiaoTrs() {
    var itemTrs = $("tr[id*='item_tr']");
    for (var i = 0; i < itemTrs.length; i++) {
        var curTr = itemTrs[i];
        var curId = jQuery(curTr).attr("id");
        var treatdetailno = curId.replace("item_tr", "");
        loadSubDataList(treatdetailno); // 加载材料列表
    }
}

//治疗，更新cost_goods表的 iszl字段值为1
function updateTreatStatus(seqId) {
    var requrl = '<%=contextPath%>/KQDS_LLTJAct/updateTreatStatus.act?seqId=' + seqId;
    $.axseY(requrl, null,
    function(data) {
        layer.alert(data.retMsrg );
        loadDataList();
    },
    function() {
        layer.alert('操作失败！' );
    });
}

function initTrHtml(trobj) {

    var llbtnHtml = '<a style="color:red;cursor:pointer;" href="javascript:void(0);" onclick="getGoods(\'' + trobj.seqId + '\',\'' + trobj.username + '\',\'' + trobj.usercode + '\',\'' + trobj.itemno + '\',\'' + trobj.unit + '\')" style="cursor:pointer;">领料</a>';

    var iszl = trobj.iszl; // 0 未治疗  1已治疗
    var iszlStr = "";
    var iszlHtml = '<a href="javascript:void(0);" onclick="updateTreatStatus(\'' + trobj.seqId + '\')" style="color:red;cursor:pointer;">治疗</a>';
    if (iszl == '1') {
        iszlStr = "<span style='color:green;'>已治疗</span>";
        iszlHtml = "<span style='color:green;'>已治疗</span>";
    }

    if (trobj.num < 1) {
        llbtnHtml = "";
        iszlHtml = "";
    }

    var btnStr = '';
    if (iszlHtml != '' && llbtnHtml != '') {
        btnStr = llbtnHtml + '&nbsp;|&nbsp;' + iszlHtml;
    }

    var tablehtml = "";
    tablehtml += "<tr style='' id='item_tr" + trobj.seqId + "'>";
    tablehtml += '<td style="">' + trobj.username + '</td>';
    tablehtml += '<td style="">' + trobj.usercode + '</td>';
    tablehtml += '<td style="">' + trobj.classname + '</td>';
    tablehtml += '<td style="">' + trobj.itemname + '</td>';
    tablehtml += '<td style="">' + trobj.unit + '</td>';
    tablehtml += '<td style="">' + trobj.num + '</td>';
    tablehtml += '<td style="">&nbsp;</td>';
    tablehtml += '<td style="">&nbsp;</td>';
    tablehtml += '<td style="">&nbsp;</td>';
    tablehtml += '<td style="">' + iszlStr + '</td>';
    tablehtml += '<td style="">' + btnStr + '</td>';
    tablehtml += "</tr>";
    return tablehtml;
}


function initTrHtml4Hidden(trobj) {

    var llbtnHtml = '<a style="color:red;cursor:pointer;" href="javascript:void(0);" onclick="getGoods(\'' + trobj.seqId + '\',\'' + trobj.username + '\',\'' + trobj.usercode + '\',\'' + trobj.itemno + '\',\'' + trobj.unit + '\')" style="cursor:pointer;">领料</a>';

    var iszl = trobj.iszl; // 0 未治疗  1已治疗
    var iszlStr = "";
    var iszlHtml = '<a href="javascript:void(0);" onclick="updateTreatStatus(\'' + trobj.seqId + '\')" style="color:red;cursor:pointer;">治疗</a>';
    if (iszl == '1') {
        iszlStr = "<span style='color:green;'>已治疗</span>";
        iszlHtml = "<span style='color:green;'>已治疗</span>";
    }

    if (trobj.num < 1) {
        llbtnHtml = "";
        iszlHtml = "";
    }

    var btnStr = '';
    if (iszlHtml != '' && llbtnHtml != '') {
        btnStr = llbtnHtml + '&nbsp;|&nbsp;' + iszlHtml;
    }

    var tablehtml = "";
    tablehtml += "<tr style='' id='hidden_item_tr" + trobj.seqId + "'>";
    tablehtml += '<td style="">' + trobj.username + '</td>';
    tablehtml += '<td style="">' + trobj.usercode + '</td>';
    tablehtml += '<td style="">' + trobj.classname + '</td>';
    tablehtml += '<td style="">' + trobj.itemname + '</td>';
    tablehtml += '<td style="">' + trobj.unit + '</td>';
    tablehtml += '<td style="">' + trobj.num + '</td>';
    tablehtml += '<td style="">&nbsp;</td>';
    tablehtml += '<td style="">&nbsp;</td>';
    tablehtml += '<td style="">&nbsp;</td>';
    tablehtml += '<td style="">' + iszlStr + '</td>';
    tablehtml += '<td style="">' + btnStr + '</td>';
    tablehtml += "</tr>";
    return tablehtml;
}

// 查询条目对应下的领料列表
function loadSubDataList(treatdetailno) {
    var requrl = '<%=contextPath%>/KQDS_LLTJ_DetailAct/selectList.act?treatdetailno=' + treatdetailno;
    $.axseY(requrl, null,
    function(data) {
        var htmlStr = "";
        if (data != null && data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                var trobj = data[i];
                htmlStr += initSubTrHtml(trobj);
            }
        }
        $("#item_tr" + treatdetailno).after(htmlStr);
    },
    function() {
        layer.alert('操作失败！' );
    });
}

// 材料的tr html
function initSubTrHtml(trobj) {
	
	var deleteHtml = '<a style="color:red;cursor:pointer;" href="javascript:void(0);" onclick="deleteTreatDetail(\'' + trobj.seqId + '\')" style="cursor:pointer;">删除</a>';


    var tablehtml = "";
    tablehtml += "<tr style=''>";
    tablehtml += '<td style="">&nbsp;</td>';
    tablehtml += '<td style="">&nbsp;</td>';
    tablehtml += '<td style="">&nbsp;</td>';
    tablehtml += '<td style="">&nbsp;</td>';
    tablehtml += '<td style="">&nbsp;</td>';
    tablehtml += '<td style="">&nbsp;</td>';
    tablehtml += '<td style="">' + trobj.goodsname + '</td>';
    tablehtml += '<td style="">' + trobj.goodsnums + '</td>';
    tablehtml += '<td style="">' + trobj.createtime + '</td>';
    tablehtml += '<td style=""></td>';
    tablehtml += '<td style="">' + deleteHtml + '</td>';
    tablehtml += "</tr>";
    return tablehtml;
}

function deleteTreatDetail(seqId){
	  layer.confirm('确定删除？', {
	    btn: ['删除','放弃'] //按钮
	  }, function(){
		  var requrl = '<%=contextPath%>/KQDS_LLTJ_DetailAct/deleteTreatDetail.act?seqId=' + seqId;
		  msg=layer.msg('加载中', {icon: 16});
		  $.axse(requrl,null, 
	              function(data){
					  if(data.retState=="0"){
		        		  layer.alert('删除成功' );
		        		  loadDataList();
		        	  }
	              },
	              function(){
	            	  layer.alert('删除失败！' );
	          	  }
	        );
	  });
}


// 治疗，更新cost_goods表的 iszl字段值为1
function dealFuc(seqId) {
    var requrl = '<%=contextPath%>/KQDS_LLTJ_DetailAct/zlStatusUpdate.act?seqId=' + seqId;
    $.axseY(requrl, null,
    function(data) {
        layer.alert(data.retMsrg );
        loadDataList();
    },
    function() {
        layer.alert('操作失败！' );
    });
}

// 调用此方法，弹框展示材料列表，供选择
function getGoods(treatdetailno, username, usercode, itemno, unit) {

    var paramUrl = "?treatdetailno=" + treatdetailno + "&username=" + username + "&usercode=" + usercode + "&itemno=" + itemno + "&unit=" + unit;
    layer.open({
        type: 2,
        title: '材料列表',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['90%', '90%'],
        content: contextPath+'/KQDS_LLTJAct/toLingliao.act' + paramUrl,
        end: function(index, layero) { // 页面关闭后执行
            if (usercodechild != "" && usernamechild != "") {

			}
        }
    });
}

// 查询操作
$('#query').on('click',
function() {
    loadDataList();
});

// 清空操作
$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});

</script>
</html>
