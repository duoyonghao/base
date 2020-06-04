<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>报表-会诊记录</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/reception_center.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/yiliao/bingli_search.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
</head>
<style type="text/css">
.buttonBar .aBtn_big{display:inline-block;padding: 0 15px;height: 28px;border: solid 1px #0e7cc9;color: #0e7cc9;border-radius: 15px;line-height: 28px;width: 80px;text-align: center;}
.buttonBar  .aBtn_big:hover{color:#fff;background: #0e7cc9;}
.buttonBar .aBtn_big2{display:inline-block;padding: 0 15px;height: 28px;border: solid 1px #0e7cc9;color: #0e7cc9;border-radius: 15px;line-height: 28px;width: 88px;text-align: center;}
.buttonBar  .aBtn_big2:hover{color:#fff;background: #0e7cc9;}
/*工作量表格 ，单独写*/
#gongzuol{border:solid 1px #c0c0c0;background: #f5f5f5;}
#gongzuol{margin-bottom: 15px;}
#gongzuol .columnHd{padding:0 15px;border-bottom:solid 1px #0e7ec6;font-size:16px;color: #373737;font-family: "Microsoft YaHei";line-height:36px;}
 .kqds_table{
		width:90%;
		/* align:center; */
		margin-left: auto;
		margin-right: auto;
	}
	
	.kqds_table  td { 
		color: #666;
		padding: 3px 5px 5px 5px;
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
		font-weight: normal;
		line-height: 28px;
	}
	
	.kqds_table  select { 
		height: 28px;
		width:120px;
		border: solid 1px #e5e5e5;
		border-radius: 3px;
	}
	input[type=text],.kv .kv-v input[type=text]{padding:0 10px;width:120px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}
	.table > tbody > tr > td{
		vertical-align:middle;
	}

	.ywTable{ 
		border-collapse: collapse; 
		border: 0px solid #999; 
	} 
	.ywTable td { 
		width:80px;
		border-top: 0; 
		border-right: 1px solid #999; 
		border-bottom: 1px solid #999; 
		border-left: 0;
	} 
	.ywTable tr.lastrow td { 
		border-bottom: 0; 
		width: 80px;
		height: 16px;
	} 
	.ywTable tr td.lastCol { 
		border-right: 0; 
		width: 80px;
		height: 16px;
	} 
	#table td{
		padding:0;
	}
	#table .ywTable td{
		padding:0;
		height:17px;
	}
	#table .ywTable td span{
		display: block;
	    width: 100px;
	    height:100%;
	    margin:0;
		white-space: nowrap;
	    overflow: hidden;
	    text-overflow: ellipsis;
	}
	#table tr td.bs-checkbox {
		padding:0px 11px;
	}
	.tableBox,.fixed-table-container{
		border:none;
	}
	.searchWrap .btnBar{
		border-top:none;
	}
	.fixed-table-container thead th .sortable{
		padding-right:8px;
	}
	.bootstrap-table td.bs-checkbox input[type=checkbox] {
	    top: 12px;
	    left: 14px;
	}
	.bootstrap-table th.bs-checkbox input[type="checkbox"] {
	    top: 7px;
	    left: 14px;
	}
</style> 
<body>
<div id="container">
    <div class="main">
        <div class="listWrap">
        	<div class="titleDiv">
        		<div class="title">
        			会诊记录
        		</div>
        	</div>
            <!-- <div class="listHd"><span class="hc-icon icon20 home-icon"></span>会诊记录</div> -->
            <div class="listBd">
                <div class="tableBox">
                    <table id="table" class="table-striped table-condensed table-bordered" >
                    </table>
                </div>
                <div id="buttonBar"> 
                     <table style="width:90%;text-align: center;"> 
                 		<tr>
                			<td style="width:12%;text-align:left;"><span style="color:#00A6C0;">总记录数:<lable id="zong">0</lable></span></td>
                 		</tr> 
                 	</table> 
                </div>
            </div>
        </div>
        <!--查询条件-->
        <div class="searchWrap">
            <div class="cornerBox">查询条件</div>
            
                <div class="formBox">
	                    <table class="kqds_table" style="width:auto; margin:0 auto;">
				    		<tr>
				    			<td style="text-align:right;">所属门诊：</td>
				    			<td style="text-align:left;">
										<select id="organization" name="organization"></select>
								</td>
				    			<td style="text-align:right;">会诊时间：</td>
				    			<td style="text-align:left;"> 
				    					<span class="unitsDate">
				                            <input type="text" id="starttime" name="starttime" placeholder="开始日期" readonly class="birthDate">
				                        </span>
				                </td>
				    			
				    			<td style="text-align:right;">到：</td>
				    			<td style="text-align:left;">
										<span class="unitsDate">
				                            <input type="text" id="endtime" name="endtime" placeholder="结束日期" readonly class="birthDate">
				                        </span>
								</td>
				    			<td style="text-align:right;">提醒时间：</td>
				    			<td style="text-align:left;"> 
				    					<span class="unitsDate">
				                            <input type="text" id="txstarttime" name="txstarttime" placeholder="开始日期" readonly class="birthDate">
				                        </span>
				                </td>
				    			
				    			<td style="text-align:right;">到：</td>
				    			<td style="text-align:left;">
										<span class="unitsDate">
				                            <input type="text" id="txendtime" name="txendtime" placeholder="结束日期" readonly class="birthDate">
				                        </span>
								</td>
							</tr>
							<tr>	
				    			<td style="text-align:right;">是否治疗：</td>
				    			<td style="text-align:left;">
				    					<select  name="toothseat" id="toothseat" >
					                          <option value="">请选择</option>
					                          <option value="已治疗">已治疗</option>
					                          <option value="未治疗">未治疗</option>
										</select>
				    			</td>
				    			<td style="text-align:right;">会诊分类：</td>
				    			<td style="text-align:left;">
				    					<select class="select2 dict" tig="YCFL" name="toothtype" id="toothtype">
		                 	  			 </select>
				    			</td>
				    			<td style="text-align:right;">创建人：</td>
				    			<td style="text-align:left;">
				    					 <input type="hidden" name="createuser" id="createuser"  class="form-control"  value=""/>
									     <input  type="text"  id="createuserDesc" name="createuserDesc" placeholder="创建人" readonly  onClick="javascript:single_select_user(['createuser', 'createuserDesc'],'',1);"></input>	
				    			</td>
				    			<td style="text-align:right;">模糊查询：</td>
				    			<td style="text-align:left;">
				    				<input type="text" placeholder="患者编号/姓名/手机号" id="queryinput" name="queryinput" style="width:120px;">
				    			</td>
				    		</tr>
				    	</table>
	                    <div class="btnBar" style="text-align: center;">
	                      	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="goAddRenwu()">添加推广</a>
	               			<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="goAddVisit()">添加回访</a>
	               			<a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
			                <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
			            </div>
	                </div>
        </div>
    </div>
</div>

</body>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var $table = $('#table');
var pageurl = '<%=contextPath%>/KQDS_Tooth_DocAct/selectHzjl.act';
var nowday;
var selectedrows = "";
var onclickrowOobj = "";
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var personrole = "<%=person.getUserPriv()%>";
var personroleother = "<%=person.getUserPrivOther()%>";
var menuid = "<%=menuid%>";
var qxnameArr = ['hzxx_scbb'];
var func = ['exportTable'];
$(function() {
    initHosSelectList4Front('organization'); // 连锁门诊下拉框	
    //当前日期
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    initDictSelectByClass();// 会诊分类
    //获取当前页面所有按钮
    getButtonPowerByPriv(qxnameArr,func,menuid);
    //时间选择
    $(".birthDate").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "top-right"
    });
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    //绑定两个时间选择框的chage时间
    $("#txstarttime,#txendtime").change(function() {
        timeCompartAndFz("txstarttime", "txendtime");
    });
    //4、表格初始化
    getHuizhenlist(pageurl);
    //计算主体的宽度
    setWidth();
    setHeight();
    $(window).resize(function() {
        setWidth();
        setHeight();
    });
});

//计算主体的宽度
function setWidth() {
    var baseW = $(window).width() - 20;
    var innerHeight_1, innerHeight_2;
    $('.centerWrap,.lookInfoWrap').width(baseW / 2);
    $('.operateModel .optBox').width(($('.centerWrap').width() - 140) / 2); (innerHeight_1 = $('.operateModel .optBox:eq(0)').height()) > (innerHeight_2 = $('.operateModel .optBox:eq(1)').height()) ? $('.operateModel .optBox:eq(1)').height(innerHeight_1) : $('.operateModel .optBox:eq(0)').height(innerHeight_2);
}

//计算左侧表格高度保证一屏展示
function setHeight() {
    // 这个页面，屏幕小会有横向滚动条，这个滚动条的高度是15
    // 这里可以可以根据屏幕分辨率，计算是否会出现滚动条
    var scrollHeight = 0;

    var baseHeight = $(window).height() - 15,
    optAreaHeight = $('.searchWrap').outerHeight();
    $(".fixed-table-container").height(baseHeight - optAreaHeight - 60 - scrollHeight);
    $('.tabIframe').height(baseHeight - 50);
    $("#table").bootstrapTable("resetView",{
    	height:$(window).outerHeight()-$(".searchWrap").outerHeight()-$(".titleDiv").outerHeight()-$("#buttonBar").outerHeight()-30
    });

}
//已结账
function getHuizhenlist(url) {
    //初始化表格所在div
    $(".tableBox").html('<table id="table" class="table-striped table-condensed table-bordered" data-height="340"></table>');
    $('#table').bootstrapTable({
        url: url,
        dataType: "json",
        queryParams: queryParams,
        cache: false,
        striped: true,
        clickToSelect: false,
        singleSelect: false,
        onLoadSuccess: function(data) { //加载成功时执行
            $("#zong").html(data.length); 
        },
        columns: [{
            field: ' ',
            checkbox: true,
            align: 'center',
            valign: 'middle'
        },{
            title: '会诊门诊',
            field: 'organizationname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="time" title="' + value + '">' + value + '</span>';
            }
        },{
            title: '患者姓名',
            field: 'username',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="time" title="' + value + '">' + value + '</span>';
            }
        },
      /*   {
            title: '患者来源',
            field: 'devchannel',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '来源子分类',
            field: 'nexttype',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        }, */
        {
            title: '是否治疗',
            field: 'toothseat',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
            	if(value == '已治疗'){
            		 return value;
            	}else{
            		 return '未治疗';
            	}
            }
        },{
            title: '分类',
            field: 'toothtypename',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return value;
                }
            }
        },{
            title: '牙位',
            field: ' ',
            align: 'center',
            valign: 'middle',
            width:100,
            sortable: true,
            formatter: function(value, row, index) {
                var str = '<table class="ywTable" id="table2" border="0" cellpadding="1" cellspacing="1">' 
                + '<tr>' 
                    + '<td><span style="border-bottom:1px solid #999;border-right:1px solid #999;">' + row.yw1 + '</span></td>' 
                    + '<td class="lastCol"><span style="border-bottom:1px solid #999;">' + row.yw2 + '</span></td>' 
                + '</tr>' 
                + '<tr class="lastrow">' 
                    + '<td><span style="border-right:1px solid #999;">' + row.yw4 + '</span></td>' 
                    + '<td class="lastCol"><span>' + row.yw3 + '</span></td>' 
                + '</tr>' 
                + '</table>';
                return str;
            }
        },{
            title: '检查项',
            field: 'detaildesc',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
            	return '<span class="time" title="' + value + '">' + value + '</span>';
            }
        },{
            title: '情况备注',
            field: 'remark',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
            	return '<span class="time" title="' + value + '">' + value + '</span>';
            }
        },{
            title: '创建人',
            field: 'createusername',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } 
            }
        },{
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
            	return '<span class="time" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '提醒时间',
            field: 'diseasesort',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="time" title="' + value + '">' + value + '</span>';
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
    });
}
function queryParams() {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        organization: $('#organization').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        txstarttime: $("#txstarttime").val(),
        txendtime: $('#txendtime').val(),
        toothseat: $('#toothseat').val(),
        createuser: $('#createuser').val(),
        toothtype: $('#toothtype').val(),
        queryinput: $("#queryinput").val()
    };
    return temp;
}
$('#query').on('click',
function() {
    var organization = $('#organization').val();
    var starttime = $('#starttime').val();
    var endtime = $('#endtime').val();
    var txstarttime = $('#txstarttime').val();
    var txendtime = $('#txendtime').val();
    var toothseat = $('#toothseat').val();
    var createuser = $('#createuser').val();
    var toothtype = $('#toothtype').val();
    var queryinput = $('#queryinput').val();
    if (starttime == "" && endtime == "" && txstarttime == "" && txendtime == "" && toothseat == "" && createuser == "" 
    		&& toothtype == "" && queryinput == "" ) {
        layer.alert('请选择查询条件!' );
        return false;
    }
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
});
$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
});
function refresh(){
	$('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
//导出
function exportTable() {
    var fieldArr = [];
    var fieldnameArr = [];
    $('#table thead tr th').each(function() {
        var field = $(this).attr("data-field");
        if (field != "") {
            fieldArr.push(field); //获取字段
            fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
        }else{
        	fieldArr.push("yw1"); //获取字段
       	    fieldnameArr.push("牙位(左上)"); //获取字段中文
       	    fieldArr.push("yw2"); //获取字段
       	    fieldnameArr.push("牙位(右上)"); //获取字段中文
       	    fieldArr.push("yw3"); //获取字段
       	    fieldnameArr.push("牙位(右下)"); //获取字段中文
       	    fieldArr.push("yw4"); //获取字段
       	    fieldnameArr.push("牙位(左下)"); //获取字段中文
        }
    });
    var param = JsontoUrldata(queryParams());
    location.href = pageurl + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
}
//获取选中行的usercode
function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
}
//添加任务(推广计划)
function goAddRenwu() {
    selectedrows = getIdSelections();
    if (selectedrows.length == 0) {
        layer.alert('请勾选复选框，选择需要加入推广计划的患者(可多选)' );
    } else {
        layer.open({
            type: 2,
            title: '添加推广计划',
            shadeClose: false,
            shade: 0.6,
            area: ['50%', '490px'],
            content: contextPath+'/KQDS_ExtensionAct/toExtensionAdd.act'
        });
    }
}

//添加回访
function goAddVisit() {
    if (onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined") {
        layer.alert('请选择需要回访的患者' );
    } else {
        layer.open({
            type: 2,
            title: '添加回访',
            shadeClose: false,
            shade: 0.6,
            area: ['550px', '480px'],
            content: contextPath+'/KQDS_VisitAct/toVisitAdd.act?lytype=usermanager&type=khgl&usercode=' + onclickrowOobj.usercode 
        });
    }
}
</script>
</html>
