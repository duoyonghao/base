<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	  contextPath = "/kqds";
	}
	
	String usercode = request.getParameter("usercode");
	if(usercode == null){
		usercode="";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>咨询记录</title> <!-- 右侧个人中心  咨询记录 标签进入 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/history_record.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
</head>
<style type="text/css">
.kqds_table{
	width:98%;
	margin:auto;
}

.kqds_table  td { 
	color: #666;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	font-weight: normal;
	line-height: 28px;
	padding: 3px 0px 5px 0px;
}

.kqds_table  select { 
	height: 28px;
	width:120px;
	border: solid 1px #e5e5e5;
	border-radius: 3px;
}


.kqds_table  input[type=text] { 
	width:80%;
}
textarea {
     min-height: 120px;
     max-height: 200px;
}
</style>
<body>
<div id="container">
    <div class="tableHd">咨询记录</div>
    <div class="tableBox">
        <table id="table" class="table-striped table-condensed table-bordered" data-height="200"></table>
    </div>
    <div class="tableHd">接诊记录</div>
    <div>
    	<form  id="form1">
	     <table class="kqds_table">
	    	<input type="hidden"  name="seqId" id="seqId">
            <input type="hidden"  name="askstatus" id="askstatus">
    		<tr>
    			<td style="width:10%;text-align:right;">客户姓名：</td>
    			<td style="width:23%;text-align:left;"><input type="text" name="username" id="username" disabled="disabled"></td>
    			<td style="width:10%;text-align:right;">挂号分类：</td>
    			<td style="width:23%;text-align:left;"><input type="text" name="recesort" id="recesort" disabled="disabled"></td>
    			<td style="width:10%;text-align:right;">咨询时间：</td>
    			<td style="width:23%;text-align:left;"><input type="text" style="width: 135px;" name="createtime" id="createtime" disabled="disabled"></td>
    		</tr>
    		<tr>
    			<td style="text-align:right;">情况备注：</td>
    			<td style="text-align:left;" colspan="5"><textarea class="form-control" style="height:200px;" name="detaildesc" id="detaildesc" rows="12" disabled="disabled"></textarea></td>
    		</tr>
            <tr>
    			<td style="text-align:right;">未成交原因：</td>
    			<td style="text-align:left;"  colspan="2"><select class="dict" tig="wcjyy" name="failreason1" id="failreason1"  disabled="disabled"></select>	</td>
    		
    			<td style="text-align:right;">潜在开发项目</td>
    			<td style="text-align:left;"  colspan="2"><select class="dict" tig="QZKFXM" name="devItem" id="devItem" disabled="disabled"></select></td>
    		</tr>
    		<tr style="display: none;">
    			<td style="text-align:right;">主诉：</td>
    			<td style="text-align:left;"><input type="text"  name="zs" id="zs"></td>
    			<td style="text-align:right;">检查：</td>
    			<td style="text-align:left;"><input type="text"  name="check" id="check"></td>
    		</tr>
    		<tr style="display: none;">
    			<td style="text-align:right;">建议：</td>
    			<td style="text-align:left;"><input type="text"  name="jy" id="jy"></td>
    			<td style="text-align:right;">卡类：</td>
    			<td style="text-align:left;"><input type="text"  name="member" id="member"></td>
    		</tr>
    		<tr style="display: none;">
    			<td style="text-align:right;">费用：</td>
    			<td style="text-align:left;"><input type="text"  name="fy" id="fy"></td>
    			<td style="text-align:right;">开发：</td>
    			<td style="text-align:left;"><input type="text"  name="kaifa" id="kaifa"></td>
    		</tr>
         </table>
        </form>
	</div>
    <div class="bottomBar" id="bottomBarDdiv">
      
    </div>
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var pageurl = '<%=contextPath%>/KQDS_ReceiveInfoAct/NoselectPage.act';
var onclickrowOobj2 = ""; //表格点击获取到的对象
var usercode = "<%=usercode%>";

$(function() {
	if(usercode == ''){
		 layer.alert('请选择患者', {
	           
	     },function(){
	    	 return false;
	     });
	 }else{
		pageurl = pageurl + '?usercode=' + usercode;
		// 加载列表
		getlist();
		initDictSelectByClass(); //未成交原因
	 }
	
    //子页面高度传给父页面
    setHeight();
    $(window).resize(setHeight);

});
function getlist() {
    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        onLoadSuccess: function(data) { //加载成功时执行
            $("#table tbody tr td:eq(0)").click();
        },
        columns: [
		{
		    title: '到院门诊',
		    field: 'organizationname',
		    align: 'center',
		    valign: 'middle',
		    visible: true,
		    switchable: false,
		    formatter: function(value, row, index) {
		        if (value != "" && value != null) {
		            return '<span class="name" title="' + value + '">' + value + '</span>';
		        }
		    }
		},
        {
            title: '咨询时间',
            field: 'createtime',
            align: 'center',
            formatter: function(value, row, index) {
                return '<span>'+value.substring(5, 10)+'</span>';
            }
        },
        {
            title: '接诊状态',
            field: 'askstatus',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                if (value == 0) {
                    return '<span style="color:red">未接诊</span>';
                } else {
                    return '<span style="color:green">已接诊</span>';
                }
            }
        },
        {
            title: '成交状态',
            field: 'regno',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                var data = getStatus(value);
                var status = data.substring(0, data.indexOf("-"));
                if (status == "未成交") {
                    return '<span style="color:red">未成交</span>';
                } else {
                    return '<span style="color:green">已成交</span>';
                }
            }
        },
        {
            title: '消费状态',
            field: 'regno',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                var data = getStatus(value);
                var status = data.substring(data.indexOf("-") + 1);
                if (status == "未成交") {
                    return '<span style="color:red">未消费</span>';
                } else {
                    return '<span style="color:green">已消费</span>';
                }
            }
        },
        {
            title: '患者姓名',
            field: 'username',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '挂号分类',
            field: 'recesortname',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
            	 if (value) {
                     return value;
                 }
            }
        },
        {
            title: '接诊咨询',
            field: 'askpersonname',
            width: 80,
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                if (value) {
                    return "<span class='name'>"+value+"</span>";
                }
            }
        },
        {
            title: '情况备注',
            field: 'detaildesc',
            align: 'center',
            
            formatter: function(value, row, index) {
		        if (value != "" && value != null) {
		        	if(value.length > 5){
		        		return '<span title="' + value + '" style="width:100px;cursor:pointer;">' + value.substring(0,5) + '...</span>';
		        	}else{
		        		return '<span title="' + value + '" style="width:100px;cursor:pointer;">' + value + '</span>';
		        	}
		        }
		    }
        },
        {
            title: '未成交原因',
            field: 'failreason1name',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
            	 if (value) {
                 	return "<span class='name'>"+value+"</span>";
                 }
            }
        },
        {
            title: '潜在开发项目',
            field: 'devitemname',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
            	 if (value) {
                 	return "<span class='name'>"+value+"</span>";
                 }
            }
        }
        ]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj2 = $('#table').bootstrapTable('getData')[index];
        jzjl(onclickrowOobj2);
    });

}
function getStatus(regno) {
    var status = "未成交",
    ifcost = "未收费";
    
    var regObj = getRegObjBySeqId(regno);
    if (regObj.cjstatus == 1) {
        status = "已成交";
    }
    if (regObj.ifcost == 1) {
        ifcost = "已收费";
    }
    return status + "-" + ifcost;;
}
function jzjl(onclickrowOobj2) {
    var data = getStatus(onclickrowOobj2.regno);
    // 潜在开发项目
    $("#devItem").val(onclickrowOobj2.devItem);
    $("#cjzt").val(data.substring(0, data.indexOf("-")));
    $("#sfzt").val(data.substring(data.indexOf("-") + 1));
    $("#username").val(onclickrowOobj2.username);
    $("#askperson").val(onclickrowOobj2.askpersonname);
    var askstatus = "未接诊";
    if (onclickrowOobj2.askstatus == 1) {
        askstatus = "已接诊";
    }
    $("#askstatusInput").val(askstatus);
    $("#deptno").val(onclickrowOobj2.deptnoname);
    $("#recesort").val(onclickrowOobj2.recesortname);
    $("#createtime").val(onclickrowOobj2.createtime);
    $("#failreason1").val(onclickrowOobj2.failreason1).trigger("change");
    $("#detaildesc").val(onclickrowOobj2.detaildesc);
    $("#seqId").val(onclickrowOobj2.seqId);

    //根据接诊信息id 查询接诊内容(主诉等6个字段)
    <%-- 
    var contenturl = '<%=contextPath%>/KQDS_Receiveinfo_ContentAct/selectDetail.act?receiveinfoId=' + onclickrowOobj2.seqId;
    $.ajax({
        type: "post",
        dataType: "json",
        url: contenturl,
        success: function(data) {
            $("#zs").val(data.data.zs);
            $("#check").val(data.data.check);
            $("#jy").val(data.data.jy);
            $("#member").val(data.data.member);
            $("#fy").val(data.data.fy);
            $("#kaifa").val(data.data.kaifa);
        }
    }); --%>
}
//调整表格高度
function setHeight() {
    var height = $('body').height();
}
</script>
</html>
