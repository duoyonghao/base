<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>新增赠送套餐</title> <!-- 由费用添加页面  新增套餐， 后台提供编辑 查询 功能 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/tableData.css"/>
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
  	.aBtn{display:inline-block;padding: 0 15px;height: 28px;border: solid 1px #00A6C0;color: #00A6C0;border-radius: 15px;line-height: 28px;width: 60px;text-align: center;}
	.aBtn:hover{color:#fff;background: #00A6C0;}
  	.costBd{position:relative;height: 320px;border: solid 1px #ddd;overflow:auto;}
	.costBd .ztreeWrap{float:left;width:30%;height:100%;min-width: 215px;border-right: solid 1px #ddd;background: #FFFFFF;z-index: 10;max-width: 785px;}
  	#table{float:left;width:70%;}
  	#table th{text-align:center;}
  	#table td a{display:block;text-align:center;}
  	a:hover{text-decoration:none;}
  	#zsitems{
  		overflow:auto;
  	}
  	#zsitems li{/*赠送项目选项的样式  */
  		margin:0;
  		padding:5px 5px;
  		text-overflow:ellipsis;
  		color:#333;
  		overflow:hidden;
  	}
  	#zsitems li:hover{/*鼠标移入的效果  */
  		background:rgba(51, 51, 51, 0.13);
  	}
  	.ztree{/*赠送项目树  */
  		padding:0;
  	}
  	.commonText{/*底部文本样式  */
  		display:inline-block;
  		height:20px;
  		font-size:12px;
  		padding-left:10px;
  	}
  	input:focus,select:focus,textarea:focus{
	   box-shadow: 0 0 8px rgb(49, 165, 247);
	   border-color:transparent;
	}
	.zsxmText{/*“选择赠送项目”的字样 */
		margin:0;
		padding:0 0 0 5px;;
		color:#fff;
		background:#00A6C0;
		height:28px;
		line-height:28px;
		font-size:13px;
	}
  </style>
</head>
<body>
<div class="costWrap">
	<div class="costBd">
		<div class="ztreeWrap">
			<p class="zsxmText">添加赠送项目</p>	
			 <div class="ztree" style="overflow-y:auto;">
				 <ul id="zsitems" style="overflow:auto;">
				 </ul>
			 </div>
		</div>
		<table id="table" class="table-striped table-condensed table-bordered" data-height="250">
		</table>
	</div>
</div>

<div style="text-align: left;width:50%;margin-top: 10px;float: left;">
	<span class="commonText" >套餐名称<i style="color: red;">*</i> : </span>
	<input style="padding-left:10px;height:20px;line-height:20px;border-radius:3px;color:#333; border:1px solid #ddd;"  type="input" id="name" />
	<span class="commonText">是否禁用 : </span>
	<select name="useflag" style="width:40px;height:20px;line-height:20px; border-radius:3px;color:#333; border:1px solid #ddd;" id="useflag"  style="width: 50px;">
		<option value="0">否</option>
		<option value="1">是</option>
	</select>
</div>

<div style="text-align: center;width:50%;margin-top: 5px;float: left;">
	<a href="javascript:void(0);" class="kqdsSearchBtn" id="baocun" style="margin-left:5px;" onclick="baocun()">保存</a>
	<a href="javascript:void(0);" class="kqdsCommonBtn" id="guanbi" style="margin-left:5px;" onclick="guanbi()">关闭</a>
</div>
    
<script>
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var contextPath = "<%=contextPath%>";
var $table = $('#table');
var tdindex = 1000; //表格里各类标签的id 动态添加时使用 数字随便从几开始
$(function() {
    inititems(); //加载左侧项目列表
    getitems(); //加载项目列表
});

//查询赠送项目列表 
function inititems() {
    var url = contextPath + '/KQDS_Give_ItemBackAct/getGiveItemList4Back.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>';
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
                    ulstr += '<li style="" value="' + itemno + '" onclick="insertRow(\'' + itemno + '\')">' + itemname + '</li>';
                }
                $("#zsitems").html(ulstr);
            }
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
}

//初始化表头
function getitems() {
    var tablehtml = "";
    tablehtml += "<thead style='text-align:center;'><tr><th width='10%'>操作</th><th width='55%'>项目名称</th><th width='15%'>单位</th><th width='20%'>数量</th></tr></thead>";
    $("#table").html(tablehtml);
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
            tablehtml += '<td><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this)"><span style="color:red;">删除</span></a></td>';
            //seqId
            tablehtml += '<td style="display:none">' + seqId + '</td>';
            //项目编号
            tablehtml += '<td style="display:none;">' + dataobj.itemno + '</td>';
            //项目名称
            tablehtml += '<td><span style="text-align:left;width:25em;" title="' + dataobj.itemname + '">' + dataobj.itemname + '</span></td>';
            //单位
            tablehtml += '<td style="text-align:center;"><span class="name">' + dataobj.unit + '<span></td>';
            //单价
            tablehtml += '<td style="display:none;">' + dataobj.unitprice + '</td>';
            //数量
            tablehtml += '<td style="text-align:center;"><input type="number" min="1" style="width:70px;text-align:center;padding-left:10px;border:1px solid #ddd;" onfocus="this.select()" name="zsnum" id="zsnum' + tdindex + '" value=1></td>';
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

//保存套餐
function baocun() {
	var useflag = $("#useflag").val();
	
    var name = $("#name").val();
    if (name == "") {
        layer.alert('请填写套餐名！', {
              
            end: function() {
                //选中套餐名称input
                $("#name").focus();
            }
        });
        return false;
    }
    var list = [];
    $('#table').find('tbody').each(function() {
        $(this).find('tr').each(function() {
            var param = {};
            $(this).find('td').each(function() {
                if ($(this).index() == 1) {
                    //项目编号
                    param.itemno = $(this).text();
                } else if ($(this).index() == 6) {
                    //赠送数量input
                    param.num = $(this).find("input").val();
                }
            });
            list.push(param);
        });
    });
    var data = JSON.stringify(list);
    var url = '<%=contextPath%>/KQDS_GiveItem_TCBackAct/saveGiveTc.act?params=' + encodeURIComponent(data) + '&name=' + name + '&type=add&useflag=' + useflag + '&organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>';
    $.axse(url, null,
    function(data) {
        if (data.retState == "0") {
            layer.alert('保存成功'  ,function(){
            	parent.layer.close(frameindex);
            });
        }else{
        	layer.alert('保存失败'  );
        }
    },
    function() {
        layer.alert('操作失败！' );
    });
}

//关闭
function guanbi() {
    parent.layer.close(frameindex);
}

function refresh() {
    $table.bootstrapTable('refresh', {
        'url': pageurl
    });
}
</script>
</body>
</html>
