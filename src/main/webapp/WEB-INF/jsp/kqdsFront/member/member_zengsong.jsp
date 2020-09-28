<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	String memberno = request.getParameter("memberno");
	String usercode = request.getParameter("usercode");
	String username = request.getParameter("username");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>会员中心-赠送项目</title><!-- 该页面会查询出之前的赠送记录 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiedai/add_cost.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script	type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>

<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<style type="text/css">
li{color:#00A6C0;cursor: pointer;}
.costBd{position:relative;padding-left:0;height: 320px;border: solid 1px #0e7cc9;overflow:auto;}
.costBd .ztreeWrap{position:relative; float:left;left:0;bottom:0;top:0;min-width: 215px;border-right: solid 1px #0e7cc9;background: #FFFFFF;z-index: 10;max-width: 785px;}
.aBtn1{display: inline-block;width: 115px;height: 30px; margin-right: 10px;line-height: 30px; border: 1px solid #0e7cc9; color: #0e7cc9; border-radius: 15px;text-align: center; }
.aBtn1:hover{color: #fff;background: #117cca;text-decoration: none;}
.aBtn1.gray{border-color: #a5a195;color: #a5a195;}
input[type=number] {
	padding: 0 10px;
	width: 212px;
	height: 26px;
	line-height: 26px;
	border: solid 1px #e5e5e5;
	border-radius: 3px;
	-webkit-transition: all .3s;
	transition: all .3s;
}
#table td>input[type="number"]{
	height:16px;
}
</style>

</head>
<body>
	<div class="costWrap">
		<div class="costHd">
			<label><input type="radio" name="treetype" id="zsxm" checked="checked">赠送项目</label>
			<label><input type="radio" name="treetype" id="zstc">赠送套餐</label>
		</div>
		<div class="costBd">
			<div style="" id="ztreeWrap" class="ztreeWrap">
				 <div class="ztree" style="overflow-y:auto;">
					 <ul id="zsitems" style="height:306px;overflow:auto;">
					 </ul>
				 </div>
			</div>
			<div style="overflow:hidden;">
				<table id="table" class="table-striped table-condensed table-bordered" data-height="250" style="float:left;width:100%"></table>
			</div>
			
		</div>
	</div>
	<div style="text-align: center;width:100%;">
		<a href="javascript:void(0);" class="kqdsSearchBtn" id="qdzs" >确定赠送</a>
		<a href="javascript:void(0);" class="kqdsCommonBtn" id="close" >关闭</a>
	</div>
</body>
<script type="text/javascript">
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var contextPath = "<%=contextPath%>";

var memberno = "<%=memberno%>";
var usercode = "<%=usercode%>";
var username = "<%=username%>";
var pageurl = contextPath + '/KQDS_GiveItem_GiveRecordAct/getGiveRecordByMemberno.act?usercode=&memberno=' + memberno;
var $table = $('#table');
var tdindex = 1000; //表格里各类标签的id 动态添加时使用 数字随便从几开始
var tdsum = 0; //保存患者已赠送项目条数
var itemspageurl = contextPath + '/KQDS_GiveItem_TCAct/getItemsByTcno.act'; //查询套餐里项目
$(function() {
    inititems(); //加载左侧项目列表
    getitems(); //加载项目列表
});

$("#zsxm").click(function() {
    //加载赠送项目列表
    inititems();
});
$("#zstc").click(function() {
    //加载赠送套餐
    inititemTc();
});

//查询套餐
function inititemTc() {
    var url = '<%=contextPath%>/KQDS_GiveItem_TCAct/getSelectTreeTcAsync.act';
    var setting = {
        view: {
            showIcon: false // 去掉图标
        },
        async: {
            enable: true,
            url: url,
            autoParam: ["id", "name=n", "level=lv"],
            otherParam: {
                "otherParam": "zTreeAsyncTest"
            },
            dataFilter: ajaxDataFilter,
            type: "get"
        }
    };
    setting['callback'] = {
        onClick: onClickTc
    };
    $.fn.zTree.init($("#zsitems"), setting);
}

//点击套餐树
function onClickTc(e, treeId, treeNode) {
    if (!treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj("zsitems");
        zTree.expandNode(treeNode);
    } else {
        //加载所选套餐中所有项目 并填充到表格中
        var itemspageurl1 = itemspageurl + '?seqId=' + treeNode.id;
        $.axse(itemspageurl1, null,
        function(data) {

            var tablehtml = "";
            var tabledata = data;
            if (tabledata.length > 0) {

                tablehtml += "</tbody>";
                for (var i = 0; i < tabledata.length; i++) {

                    tdindex++;

                    tablehtml += "<tr>";

                    //删除按钮
                    tablehtml += '<td style="text-align:center; vertical-align:center;"><a style="color:red;" href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this)">删除</a></td>';
                    //项目编号
                    tablehtml += '<td style="text-align:center; vertical-align:center;display:none;">' + tabledata[i].itemno + '</td>';
                    //项目名称
                    tablehtml += '<td style="text-align:left; vertical-align:center;"><span style="float:left; text-align:left;" class="time" title="' + tabledata[i].itemname + '">' + tabledata[i].itemname + '</span></td>';
                    //单位
                    tablehtml += '<td style="text-align:center; vertical-align:center;">' + tabledata[i].unit + '</td>';
                    //单价
                    tablehtml += '<td style="display:none; text-align:center; vertical-align:center;">' + tabledata[i].unit + '</td>';
                    //数量
                    tablehtml += '<td style="text-align:center; vertical-align:center;"><input type="number" min="1" style="width:70px;text-align:center;padding:0 0 0 15px;" onfocus="this.select()" name="zsnum" id="zsnum' + tdindex + '" value="' + tabledata[i].zsnum + '"></td>';

                    tablehtml += "</tr>";
                }
                tablehtml += "</tbody>";
            }
            $("#table").append(tablehtml);
        },
        function() {
            layer.alert('查询失败！'  );
        });
    }
}

//查询赠送项目列表
function inititems() {
    var url = contextPath + '/KQDS_Give_ItemBackAct/getGiveItemList4Front.act';
    $.axse(url, null,
    function(data) {
        var list = data.data;
        if (list != null) {
            if (list.length > 0) {
                var ulstr = "";
                for (var j = 0; j < list.length; j++) {
                    var item = list[j];
                    var itemno = item.hffl;
                    var itemname = item.name;
                    ulstr += '<li style="margin-top:8px;margin-left:2px;" value="' + itemno + '" onclick="insertRow(\'' + itemno + '\')">' + itemname + '</li>';
                }
                $("#zsitems").html(ulstr);
            }
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
}

function getitems() {
    //动态加载列表
    $.axse(pageurl, null,
    function(data) {
        var tablehtml = "";
        var tabledata = data;
        tablehtml += "<thead style='text-align:center;'><tr><th style='text-align:center;' width='20%'>操作</th><th style='text-align:center;' width='40%'>项目名称</th><th style='text-align:center;' width='20%'>单位</th><th style='text-align:center;' width='20%'>数量</th></tr></thead>";
        if (tabledata.length > 0) {

            tdsum = tabledata.length;

            tablehtml += "</tbody>";
            for (var i = 0; i < tabledata.length; i++) {

                tdindex++;

                tablehtml += "<tr>";

                //删除按钮
                tablehtml += '<td style="text-align:center; vertical-align:center;"><input type="hidden" value="' + tabledata[i].seqId + '"></td>';
                //项目名称
                tablehtml += '<td style="text-align:left; vertical-align:center;"><span>' + tabledata[i].itemname + '</span></td>';
                //单位
                tablehtml += '<td style="text-align:center; vertical-align:center;"><span>' + tabledata[i].unit + '</span></td>';
                //数量
                tablehtml += '<td style="text-align:center; vertical-align:center;"><span>' + tabledata[i].zsnum + '</span></td>';

                tablehtml += "</tr>";
            }
            tablehtml += "</tbody>";
        }
        $("#table").html(tablehtml);
    },
    function() {
        layer.alert('查询失败！' );
    });
}

//动态添加行
function insertRow(seqId) {
    var url = contextPath + '/KQDS_Give_ItemBackAct/selectDetail.act?seqId=' + seqId;
    $.axse(url, null,
    function(data) {
        if (data.retState == "0") {
            var dataobj = data.data;
            var tablehtml = "";
            tdindex++;
            tablehtml += "<tr>";

            //删除按钮
            tablehtml += '<td style="text-align:center; vertical-align:center;"><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this)"><span style="color:red;">删除</span></a></td>';
            //项目编号
            tablehtml += '<td style="text-align:center; vertical-align:center;display:none;">' + dataobj.itemno + '</td>';
            //项目名称
            tablehtml += '<td style="text-align:left; vertical-align:center;"><span title="' + dataobj.itemname + '">' + dataobj.itemname + '</span></td>';
            //单位
            tablehtml += '<td style="text-align:center; vertical-align:center;">' + dataobj.unit + '</td>';
            //单价
            tablehtml += '<td style="display:none;text-align:center; vertical-align:center;">' + dataobj.unitprice + '</td>';
            //数量
            tablehtml += '<td style="text-align:center; vertical-align:center;"><input type="number" min="1" style=" width:70px;padding:0 0 0 15px; text-align:center; " onfocus="this.select()" name="zsnum" id="zsnum' + tdindex + '" value=1></td>';

            tablehtml += "</tr>";

            $("#table").append(tablehtml);
        } else {
            layer.alert('添加失败！'  );
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

//删除行
function deltr(obj) {
    var i = obj.parentNode.parentNode.rowIndex;
    document.getElementById('table').deleteRow(i);
}

function refresh() {
    window.location.reload();
}

//确定赠送
$("#qdzs").click(function() {
    var list = [];
    $('#table').find('tbody').each(function() {
        $(this).find('tr').each(function() {
            var param = {};
            if (($(this).index() + 1) <= tdsum) {
                //跳过已赠送项目
                return true;
            }
            $(this).find('td').each(function() {
                if ($(this).index() == 1) {
                    //项目编号
                    param.itemno = $(this).text();
                } else if ($(this).index() == 2) {
                    //项目名称span
                    param.itemname = $(this).find("span").html();
                } else if ($(this).index() == 3) {
                    //单位
                    param.unit = $(this).text();
                } else if ($(this).index() == 4) {
                    //单价
                    param.unitprice = $(this).text();
                } else if ($(this).index() == 5) {
                    //赠送数量input
                    param.zsnum = $(this).find("input").val();
                }
            });
            list.push(param);
        });
    });
    var data = JSON.stringify(list);
    var url = '<%=contextPath%>/KQDS_GiveItem_GiveRecordAct/confirmGiveItems.act?params=' + encodeURIComponent(data) + '&usercode=' + usercode + '&username=' + username + '&memberno=' + memberno;
    $.axse(url, null,
    function(data) {
        if (data.retState == "0") {
            layer.alert('赠送成功', {
                  
                end: function() {
                    refresh();
                }
            });
        }
    },
    function() {
        layer.alert('操作失败！' );
    });
});

//关闭
$("#close").click(function() {
    parent.layer.close(frameindex);
});

//计算字符串长度
function getStrLen(str) {
    if (str == null) return 0;
    if (typeof str != "string") {
        str += "";
    }
    return str.replace(/[^\x00-\xff]/g, "01").length;
}
</script>
</html>
