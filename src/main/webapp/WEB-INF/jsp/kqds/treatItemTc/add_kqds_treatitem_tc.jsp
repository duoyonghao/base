<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>新增收费套餐</title>
<!-- 由费用添加页面  新增套餐， 后台提供编辑 查询 功能 -->
<link rel="stylesheet" href="<%=contextPath%>/static/plugin/zTreeStyle/fhbuttonTree.css" type="text/css">
<link rel="stylesheet" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItemTc/treatitemTc.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<style>
	
</style>
</head>
<body>
	<div class="containerDiv">
		<div class="leftLayoutDiv">
			<span class="spanTitle">收费项目</span>
			<ul id="treeDemo" class="ztree"></ul>
		</div>
		
		<div class="rightLayoutDiv">
		<!-- 首行输入区 header就是div -->
			<header>
				<span class="commonText">套餐类型<i class="orange">*</i></span>
				<input type="text" name="tctype" id="tctype" placeholder="">
				
				<span class="commonText">套餐名称<i class="orange">*</i></span>
				<input type="text"  name="tcname" id="tcname" placeholder="">
				
				<button onclick="lctc()" class="kqdsCommonBtn" >
					<i class="glyphicon glyphicon-plus"></i> 保存
				</button>
			</header>
			
		<!-- 中间表格 -->
			<section>
				<div class="tableDiv">
					<div class="fixedTableHeader">
						<table class="table-striped table-condensed table-bordered">
							<thead>
								<tr>
									<th style="text-align: center; vertical-align: middle;width:5%;height:30px;">操作</th>
									<th style="text-align: center; vertical-align: middle;width:10%;">项目编号</th>
									<th style="text-align: center; vertical-align: middle;width:6%;">特殊项目</th>
									<th style="text-align: center; vertical-align: middle;width:20%;">治疗项目</th>
									<th style="text-align: center; vertical-align: middle;width:5%;">单位</th>
									<th style="text-align: center; vertical-align: middle;width:10%;">单价</th>
									<th style="text-align: center; vertical-align: middle;width:9%;">数量</th>
									<th style="text-align: center; vertical-align: middle;width:10%;">折扣%</th>
									<th style="text-align: center; vertical-align: middle;width:10%;">小计</th>
								</tr>
							</thead>
						</table>
					</div>
					<div class="fixedTableBody">
						<table id="table" class="table-striped table-condensed table-bordered">
							<thead>
								<tr>
									<th style="text-align: center; vertical-align: middle;width:5%;height:30px;">操作</th>
									<th style="text-align: center; vertical-align: middle;width:10%;">项目编号</th>
									<th style="text-align: center; vertical-align: middle;width:6%;">特殊项目</th>
									<th style="text-align: center; vertical-align: middle;width:20%;">治疗项目</th>
									<th style="text-align: center; vertical-align: middle;width:5%;">单位</th>
									<th style="text-align: center; vertical-align: middle;width:10%;">单价</th>
									<th style="text-align: center; vertical-align: middle;width:9%;">数量</th>
									<th style="text-align: center; vertical-align: middle;width:10%;">折扣%</th>
									<th style="text-align: center; vertical-align: middle;width:10%;">小计</th>
								</tr>
							</thead>
							<tbody style="background-color: #F0FFFF;text-align: center;"></tbody>
						</table>
					</div>
				</div>
			</section>
			
			<footer>
				<span class="commonText">原价：<span id="totalcost"></span>
				
				<span class="commonText">折扣额：<span id="discountmoney"></span>
				
				<span class="commonText">小计：<span id="xiaojimoney"></span>
				
			</footer>
		</div>	
	</div>

<script>
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl = '<%=contextPath %>/KQDS_TreatItem_TcBackAct/selectPageByTctypeAndTcname.act';
var maxDiscount = "<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag0_maxDiscount, request) %>";
var $table = $('#table');
var tdindex = 0; 
$(function() {
    zTreeInit();
    getAlljs();
    /*wl--ztreeInit初始化的 宽度为228px 这里设置为100% */
   	$("#treeDemo").css("width","100%");
    
    /*wl--结束 */
});
function refresh() {
    $table.bootstrapTable('refresh', {
        'url': pageurl
    });
    getAlljs();
}
function getAlljs() {
    //循环获取表格中项目
    var totalcost = 0.0,
    discountmoney = 0.0,
    xiaojimoney = 0.0;
    $("#table tbody tr").each(function() {
        var xiaoji = 0;
        var num = 0;
        var unitprice = 0;
        var discount = 0;
        $(this).find('td').each(function() {
            if ($(this).index() == 5) {
                unitprice = $(this).find("span").html();
            }
            if ($(this).index() == 6) {
                num = $(this).find("input").val();
            }
            if ($(this).index() == 7) {
                discount = $(this).find("input").val();
            }
            if ($(this).index() == 8) {
                xiaoji = $(this).find("span").html();
            }
        });
        // 合计
        if (xiaoji) {
            totalcost += Number(xiaoji);
            // 折扣额
            discountmoney += num * unitprice / 100 * (100 - discount);
        }
    });
    $("#xiaojimoney").html(Number(totalcost).toFixed(2));
    $("#discountmoney").html(Number(discountmoney).toFixed(2));
    $("#totalcost").html(Number(Number(totalcost) + Number(discountmoney)).toFixed(2));
    
}
//新增治疗项目
function edithz(index, field) {
    //判断该用户最大折扣权限
    if (field == "discount") {
        var discount = Number($("#discount" + index).val());
        if (maxDiscount > discount) {
            layer.alert('对不起，您输入的折扣已超出最大折扣权限！'  );
            oldValue = $("#discount" + index).attr("oldvalue");
            $("#discount" + index).val(oldValue);
            return false;
        }
    }
    var oldValue = "";
    var num, discount, unitprice, subtotal;
    if (judgeSign($("#num" + index).val()) == true) {
        num = Number($("#num" + index).val());
    } else {
        layer.alert('输入项必须为正数' );
        oldValue = $("#num" + index).attr("oldvalue");
        $("#num" + index).val(oldValue);
        return false;
    }
    if (judgeSign($("#discount" + index).val()) == true) {
        discount = Number($("#discount" + index).val());
        if (discount > 100) {
            layer.alert('折扣必须小于100！'  );
            oldValue = $("#discount" + index).attr("oldvalue");
            $("#discount" + index).val(oldValue);
            return false;
        }
    } else {
        layer.alert('输入项必须为正数' );
        oldValue = $("#discount" + index).attr("oldvalue");
        $("#discount" + index).val(oldValue);
        return false;
    }
    unitprice = Number($("#unitprice" + index).html());
    subtotal = Number($("#subtotal" + index).html());
    if (field == "num") {
        subtotal = Number(num * unitprice * discount / 100).toFixed(2);
    }
    if (field == "discount") {
        subtotal = Number(num * unitprice * discount / 100).toFixed(2);
    }
    $("#subtotal" + index).html(subtotal);
    //折扣额
    $("#num" + index).attr("oldvalue", num);
    $("#discount" + index).attr("oldvalue", discount);
    //刷新金额
    getAlljs();
}
function zTreeInit() {
    //异步加载
    var url = '<%=contextPath%>/YZDictCostAct/getSelectTreeAsync4TcManager.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>&noyjj=1';
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
        onClick: onclick
    };
    $.fn.zTree.init($("#treeDemo"), setting);

}
function onclick(e, treeId, treeNode) {
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.expandNode(treeNode);
    } else {
        var detailurl = '<%=contextPath%>/KQDS_TreatItemBackAct/getOneBytreatitemno4Back.act?treatitemno=' + treeNode.id;
        $.axseY(detailurl, null,
        function(data) {
            if (data.retState == "0") {
            	tdindex++;
                var tablehtml = "";
                var tabledata = data.data;
                var subtotal = tabledata.unitprice * tabledata.discount / 100;
                tablehtml += "<tr style='border: 1px solid #ddd;'>";
                //删除按钮0
                tablehtml += '<td style="height:30px;border-right: 1px solid #ddd;"><a class="display:inline-block;vertical-align:middle;" href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this)"><span style="display:block">删除</span></a></td>';
                //项目编号1
                tablehtml += '<td style="border-right: 1px solid #ddd;"><span style="vertical-align:middle;">' + treeNode.id + '<span></td>';
                //特殊项目2
                var tsxm = tabledata.istsxm == 0 ? "否": "是";
                tablehtml += '<td style="border-right: 1px solid #ddd;"><span style="vertical-align:middle;">' + tsxm + '<span></td>';
                //治疗项目3
                tablehtml += '<td style="border-right: 1px solid #ddd;"><span style="vertical-align:middle;text-align:center; width:130px;"  title='+tabledata.treatitemname+'>' + tabledata.treatitemname + '</span></td>';
                //单位4
                tablehtml += '<td style="border-right: 1px solid #ddd;"><span style="vertical-align:middle;">' + tabledata.unit + '<span></td>';
                //单价5
                tablehtml += '<td style="border-right: 1px solid #ddd;"><span style="vertical-align:middle;" id="unitprice' + tdindex + '">' + tabledata.unitprice + '</span></td>';
                //数量6
                tablehtml += '<td style="border-right: 1px solid #ddd;"><input  type="number" min="1" style="width:100%; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'num\')"  id="num' + tdindex + '" value="1" oldvalue="1"></td>';
                //折扣7
                tablehtml += '<td style="border-right: 1px solid #ddd;"><input  type="number" min="1" style="width:100%; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'discount\')"  id="discount' + tdindex + '" value="' + tabledata.discount + '" oldvalue="' + tabledata.discount + '"></td>';
                //小计8
                tablehtml += '<td style="border-right: 1px solid #ddd;"><span style="vertical-align:middle;" id="subtotal' + tdindex + '">' + subtotal + '</span></td>';
                tablehtml += "</tr>";
                $("#table").find('tbody').append(tablehtml);
                //刷新金额
                getAlljs();
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }
}
//删除行
function deltr(tr) {
        var i = tr.parentNode.parentNode.rowIndex;
        document.getElementById('table').deleteRow(i);
        //刷新金额
        getAlljs();
        return false;
}
function lctc(){
	var tctype = $("#tctype").val();
	var tcname = $("#tcname").val();
	if (tctype == "") {
        layer.alert('套餐类型不能为空！' );
        return false;
    }
	if (tcname == "") {
        layer.alert('套餐名称不能为空！' );
        return false;
    }
/* 	var regex = /^[a-zA-Z0-9\u4E00-\u9FA5]+$/;
    if (!regex.test(tctype)) {
        layer.alert('套餐类型或者套餐名称只包含汉字英文数字！' );
        return false;
    }
    if (!regex.test(tcname)) {
        layer.alert('套餐类型或者套餐名称只包含汉字英文数字！' );
        return false;
    } */
    if (tctype == tcname) {
        layer.alert('套餐类型不能和套餐名称相同！' );
        return false;
    }
    //判断该套餐类型下是否存在 该套餐
    if (!YzTcname(tctype, tcname)) {
        layer.alert('该套餐类型下已存在该套餐名称！' );
        return false;
    }
    var list = [];
    $('#table').find('tbody').each(function() {
        $(this).find('tr').each(function() {
        	var params = {};
            $(this).find('td').each(function() {
                if ($(this).index() == 1) {
                    params.itemno = $(this).text();
                } else if ($(this).index() == 2) {
                    params.istsxm = $(this).text();
                } else if ($(this).index() == 3) {
                    params.itemname = $(this).find("span").html();
                } else if ($(this).index() == 4) {
                    params.unit = $(this).text();
                } else if ($(this).index() == 5) {
                    params.unitprice = $(this).find("span").html();
                } else if ($(this).index() == 6) {
                    params.num = Number($(this).find("input").val());
                } else if ($(this).index() == 7) {
                    params.discount = Number($(this).find("input").val());
                } else if ($(this).index() == 8) {
                    params.subtotal = Number($(this).find("span").html());
                } 
            });
            list.push(params);
        });
    });
    var data = JSON.stringify(list);
    //保存 
    var param = {
   		tctype: tctype,
   		tcname: tcname,
   		organization : '<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>',
        params: data
    };
    var url = contextPath + '/KQDS_TreatItem_TcBackAct/insertList4Back.act';
    $.axse(url, param,
    function(data) {
        if (data.retState == "0") {
            layer.alert('添加成功', {
                  
                end: function () {
					parent.refresh();
		        	parent.layer.close(frameindex); //再执行关闭 
				}
            });
        }
    },
    function() {
        layer.alert('添加失败！' );
    });
}
function YzTcname(tctype,tcname){
	 var param = {
			 tctype : tctype,
			 tcname : tcname,
			 organization : '<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>'
	 }
	 var flag = true;
	 var url = contextPath + '/KQDS_TreatItem_TcAct/YzCode.act';
    $.axse(url, param,
    function(r) {
   	 flag = r.data;
    },
    function() {
        
    });
    return flag;
}
</script>
</body>
</html>
