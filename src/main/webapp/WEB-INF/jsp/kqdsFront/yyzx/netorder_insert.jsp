<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>	
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	String usercode = request.getParameter("usercode");
	if (usercode == null) {
		usercode = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>网电信息</title>
<!-- 从客户管理进入的 右侧个人中心   网电信息 Tab -->
<!-- 客服中心—客户管理页面右侧的网电信息Url和其他页面不一样 该页面可以进行保存操作  其他网电信息页面不可以 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/web_info.css" />
<style>
	input[type=text]:disabled{
		background:#f5f5f5;
		cursor:not-allowed;
	}
</style>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/core/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
</head>
<body>
<div id="container">
    <form id="form1"  class="form-horizontal">
    <div class="infoBd">
    	<div class="kv clearB form-group" style="width:100%;">
            <label class="impColor">预约门诊*</label>
            <div class="kv-v">
                <select id="organization" name="organization">
                </select>
            </div>
        </div>
    	<div class="kv">
            <label>患者编号</label>
            <div class="kv-v">
                <input type="text" id="usercode" name="usercode" value="" disabled>
            </div>
        </div>
    	<div class="kv">
            <label>患者姓名</label>
            <div class="kv-v">
                <input type="text" id="username" name="username" value="" disabled>
            </div>
        </div>
        <div class="kv">
            <label>咨询日期</label>
            <div class="kv-v">
                <input type="text" id="acceptdate" name="acceptdate" value="" disabled >
            </div>
        </div>
        <div class="kv">
            <label>预约日期</label>
            <div class="kv-v">
                <input type="text" id="ordertime" name="ordertime" value=""  >
            </div>
        </div>
        <div class="kv">
            <label>受理类型</label>
            <div class="kv-v">
                <select class="dict" tig="SLLX" name="accepttype" id="accepttype" >
				</select>
            </div>	
        </div>
        <div class="kv">
            <label>受理工具</label>
            <div class="kv-v">
                <select class="dict" tig="SLGJ" name="accepttool" id="accepttool" >
				</select>
            </div>
        </div>
        <div class="kv singleKv">
            <label>咨询内容</label>
            <div class="kv-v">
                <textarea class="form-control" name="askcontent" id="askcontent" rows="3" style="min-width:390px; width: 79%;"></textarea>
            </div>
        </div>
        <div class="kv">
            <label>咨询项目</label>
            <div class="kv-v">
                <select class="dict" tig="ZXXM" name="askitem" id="askitem" >
				</select>
            </div>
        </div>
        <div class="kv">
            <label>上门状态</label>
            <div class="kv-v">
                <select name="doorstatus" id="doorstatus">
                	<option value="">请选择</option>
                	<option value="0">未上门</option>
                	<option value="1">已上门</option>
				</select>
            </div>
        </div>
        
        <div class="buttonBar">
            <hr/>
        </div>
        <div class="kv singleKv">
            <label>患者备注</label>
            <div class="kv-v">
                <textarea class="form-control" name="remark" id="remark" rows="3" style="min-width:390px; width: 79%;"></textarea>
            </div>
        </div>
        
        <div class="buttonBar" id="buttonBarDiv">
        	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="baocun()">保存</a>
        </div>
    </div>
    </form>
</div>
</body>

<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var usercode = '<%=usercode%>';
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var $table = $('#table');
// 进入页面的时间
var intoPageDateTime = new Date();
$(function() {
	/** ########################################################### 连锁相关  **/
	initHosSelectListNoCheck('organization');// 连锁门诊下拉框
	/** ########################################################### 连锁相关  end **/
	
    initDictSelectByClass(); // 受理类型 受理工具 咨询项目
    if (usercode != null && usercode != "") {
        var url = contextPath + '/KQDS_Net_OrderAct/selectFirstByUsercode.act?usercode=' + usercode;
        $.axse(url, null,
        function(data) {
        	loadData(data.wdinfo);
        },
        function() {
		});
    }

    $("#ordertime").datetimepicker({
        language: 'zh-CN',
        minView: 0,
        format: 'yyyy-mm-dd hh:ii',
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "bottom-right"
    });

    //加载页面 上门状态默认选中未上门 并且disabled 不能选中
    $("#doorstatus").val(0).trigger("change");
    $("#doorstatus").attr("disabled", "disabled");
    noEdit();
    clean();
});
function noEdit() {
    $(":input[type=text]").each(function(i) {
        var text = $(this).val();
        if (text != "") {
            $(this).attr("disabled", "disabled");
        }
    });
    $("select").each(function(i) {
        var text = $(this).val();
        if (text != "") {
            $(this).attr("disabled", "disabled");
        }
        
    });
    $("textarea").each(function(i) {
        var text = $(this).val();
        if (text != "") {
            $(this).attr("disabled", "disabled");
        }
    });
}

//清空并把文本框切换为可编辑状态
function clean() {
    $("#ordertime").val("");
    $("#ordertime").attr("disabled", false);
    $("#askcontent").val("");
    $("#askcontent").attr("disabled", false);
    $("#askitem").val("").trigger("change");
    $("#askitem").attr("disabled", false);
    $("#remark").val("");
    $("#remark").attr("disabled", false);
} 
//保存预约信息
function baocun() {
    //验证
    var usercode = $("#usercode").val();
    var username = $("#username").val();
    var acceptdate = $("#acceptdate").val();
    var ordertime = $("#ordertime").val();
    var accepttype = $("#accepttype").val();
    var accepttool = $("#accepttool").val();
    var askcontent = $("#askcontent").val();
    var askitem = $("#askitem").val();
    var doorstatus = 0;
  	if (ordertime == "" || ordertime == null) {
  		 layer.alert('请选择预约日期', {
  	              
  	        });
  	        return false;
  	}
    // 预约时间对象
    var ordertimeObj = new Date(ordertime + ':00'); // 2017-05-04 17:10
    // 咨询日期对象
    var acceptdateObj = new Date(acceptdate);

    // 不得早于 咨询日期
    if (acceptdateObj.getTime() >= ordertimeObj.getTime()) {
        layer.alert('预约日期不得早于咨询日期' );
        return false;
    }

    if (intoPageDateTime.getTime() >= ordertimeObj.getTime()) {
        layer.alert('预约日期不得早于操作时间' );
        return false;
    }
 	/* var orderCount = true;
    var url = contextPath + '/KQDS_Net_OrderAct/checkNetOrderCount.act?usercode=' + usercode + '&ordertime=' + ordertime+'&seqId=';
    $.axse(url, null,
    function(data) {
    	if(data.retState != '0'){
    		orderCount = false; 
    		layer.alert(data.retMsrg, { // 【同一个患者的编辑操作，不算做重复预约】
                  
            });
    	}
    },
    function() {
    	orderCount = false; 
    	layer.alert(data.retMsrg, { // 【同一个患者的编辑操作，不算做重复预约】
              
        });
 	});
    if(!orderCount){
   		 return false; 
   	} */
    // 验证门诊
    var organization = $('#organization').val();
    if(!organization){
    	layer.alert('请选择上门门诊' );
        return false;
    }
    if (username == "" || username == null || usercode == "" || usercode == null) {
        layer.alert('请选择患者' );
        return false;
    } else if (accepttype == "" || accepttype == null) {
        layer.alert('请选择受理类型' );
        return false;
    } else if (accepttool == "" || accepttool == null) {
        layer.alert('请选择受理工具' );
        return false;
    } else if (askitem == "" || askitem == null) {
        layer.alert('请选择咨询项目' );
        return false;
    }
    var remark = $("#remark").val();

    var param = {
    	organization: organization,
        usercode: usercode,
        username: username,
        acceptdate: acceptdate,
        ordertime: ordertime,
        accepttype: accepttype,
        accepttool: accepttool,
        askcontent: askcontent,
        askitem: askitem,
        doorstatus: doorstatus,
        remark: remark
    };
    var submiturl = '<%=contextPath%>/KQDS_Net_OrderAct/insertOrUpdate.act';
	save(submiturl, param);
}
function save(submiturl, param) {
	var msg;
	$.axseSubmit(submiturl, param, function() {
		msg = layer.msg('加载中', {
			icon : 16
		});
	}, function(r) {
		if (r.retState == "0") {
			layer.alert('预约成功', {
				end : function() {
					parent.layer.close(frameindex); //再执行关闭
				}
			});
		} else {
			layer.alert('预约失败', {
			});
		}
	}, function() {
		layer.alert('预约失败', {
		});
	});
}
</script>
</html>
