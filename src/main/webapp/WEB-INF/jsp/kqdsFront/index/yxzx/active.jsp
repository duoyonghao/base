<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	// 获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
	
	//图片上传地址
	String UPLOAD_URL = YZSysProps.getProp(SysParaUtil.UPLOAD_URL);
	if(UPLOAD_URL == null){
		UPLOAD_URL = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>活动记录-营销中心</title>
<!-- 系统中只有一个页面 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/font-awesome/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/yxzx/active.css"/>
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<style>
.tableBox{
	border-radius:6px;
	overflow:hidden;
	border-bottom:1px solid #ddd;
}
</style>
</head>
<body>
	<div id="container">
		<div id="main">
			<!--左侧中心-->
			<div class="centerWrap" >
				<div class="columnWrap">
					<div class="columnHd">
					    <span class="title">活动记录</span>
					</div>
					<div class="columnBd">
						<div class="tableBox">
							<table id="table"
								class="table-striped table-condensed table-bordered"
								data-height="450"></table>
						</div>
					</div>
				</div>
				<div class="searchWrap">
					<!-- <div class="cornerBox">查询条件</div> -->
	                <div class="searchToggleBtnGroup">
	                	<span class="ptcx checked" style="cursor:pointer;">
	                		通用查询
	                	</span>
	                	<!-- <span>
	                		高级查询
	                	</span> -->
	                </div>
					<div class="formBox">
						<div class="searchBox">
							<table class="kqds_table">
					    		<tr>
						    		<td style="text-align:right;">所属门诊</td>
					    			<td style="text-align:left;">
					    				<select id="organization" name="organization"></select>
					    			</td>
					    			<td style="text-align:right;">活动日期</td>
					    			<td style="text-align:left;">
											<span class="unitsDate" style="float: left;"> 
											<input type="text" id="starttime" name="starttime" placeholder="开始日期"
												style="width: 90px; cursor:pointer;"  class="birthDate2"> 
												
												<span>到</span>
												<input type="text" style="cursor:pointer;width: 90px;" id="endtime" name="endtime"
												placeholder="结束日期" class="birthDate2">
											</span>
									</td>
					    			<td style="text-align:right;">客户姓名</td>
					    			<td style="text-align:left;"> 
					    					<input type="hidden" class="whiteInp" name="username" id="username" class="form-control" value="" /> 
					    					<input type="text" style="cursor:pointer;width: 100px;" id="usernameDesc" name="usernameDesc" onclick="getuser()"></input>
					                </td>
					    			<td style="text-align:right;">活动执行</td>
					    			<td style="text-align:left;">
											<input type="hidden" class="whiteInp" name="activitycontactsSearch" id="activitycontactsSearch" class="form-control" value="" /> 
											<input type="text" style="cursor:pointer;width: 100px;" id="activitycontactsSearchDesc" name="activitycontactsSearchDesc"
										 		onclick="javascript:multi_select_user(['activitycontactsSearch', 'activitycontactsSearchDesc']);"></input>
									</td>
					    		</tr>
							</table>
						</div>

						<div class="btnBar" id="hdjldiv">
							<a href="javascript:void(0);" class="kqdsCommonBtn" id="clean" onclick="cleanLeft()">清空</a> 
							<a href="javascript:void(0);" class="kqdsSearchBtn" id="search">查询</a>
						</div>
					</div>
				</div>
			</div>
			<!--右侧信息浏览-->
			<div class="lookInfoWrap">
				<div class="columnWrap">
					<div class="columnHd"
						style="padding: 0 20px; line-height: 40px; font-size: 14px; color: #FFFFFF; background: #00a6c0;">
						信息展示</div>
					<div class="infoBd">
						<form id="form1">
							<input type="hidden" name="seqId" id="seqId">
							<table class="formBox tableLayout">
								<tbody>
									<tr>
										<td>
											<span class="commonText" style="color:red;">活动日期：</span>
										</td>
										<td>
											<input type="text" class="birthDate" placeholder="活动日期" id="activitydate" name="activitydate">
										</td>
										<td>
											<span class="commonText" style="color:red;">活动名称：</span>
										</td>
										<td>
											<input type="text" class="zxInput" placeholder="活动名称" id="activityname" name="activityname">
										</td>
									</tr>
									
									<tr>
										<td>
											<span class="commonText">地址：</span>
										</td>
										<td>
											<input type="text" class="zxInput" placeholder="地址" id="address" name="address">
										</td>
										<td>
											<span class="commonText" style="color:red;">活动投入：</span>
										</td>
										<td>
											<input type="text" class="zxInput" style="width: 180px;" placeholder="活动投入" id="outmoney" name="outmoney">
											<span style="color:#00A6C0;">单位：元</span>
										</td>
									</tr>
									
									<tr>
										<td>
											<span class="commonText">联系人1：</span>
										</td>
										<td>
											<input type="text" class="zxInput" placeholder="活动联系人1" id="lxr1" name="lxr1">
										</td>
										<td>
											<span class="commonText">联系人2：</span>
										</td>
										<td>
											<input type="text" class="zxInput" placeholder="活动联系人2" id="lxr2" name="lxr2">
										</td>
									</tr>
									
									<tr>
										<td>
											<span class="commonText">活动人数：</span>
										</td>
										<td>
											<input type="text" class="zxInput" placeholder="活动人数" id="activitynum" name="activitynum">
										</td>
										<td>
											<span class="commonText">活动执行：</span>
										</td>
										<td>
											<input type="hidden" name="activitycontacts" id="activitycontacts" class="form-control" /> 
											<input style="cursor:pointer;" type="text" id="activitycontactsDesc" name="activitycontactsDesc" placeholder="活动执行" onClick="javascript:multi_select_user(['activitycontacts', 'activitycontactsDesc']);">
										</td>
									</tr>
									<tr>	
										<td>
											<span class="commonText">内容：</span>
										</td>
										<td colspan="3">
											<textarea class="longTextarea" name="content" id="content" rows="3" placeholder=""></textarea>
										</td>
									</tr>
									
									<tr>	
										<td>
											<input type="hidden" id="attachmentid" name="attachmentid" value=""> 
											<input type="hidden" id="attachmentname" name="attachmentname" value="">
											<span class="commonText">优惠：</span>
										</td>
										<td colspan="3">
											<textarea class="longTextarea" name="discounts" id="discounts" rows="3" placeholder=""></textarea>
										</td>
									</tr>	
									<tr>	
										<td>
											<span class="commonText">客户：</span>
										</td>
										<td colspan="3">
											<input type="hidden" name="kehu" id="kehu" value="">
											<textarea class="longTextarea" name="kehudesc" id="kehudesc" rows="3" placeholder=""
											onclick="javascript:multi_select_hz(['kehu', 'kehudesc']);"></textarea>
										</td>
									</tr>
								</tbody>
							</table>
							<div class="fileUploadDiv">
								<p style="margin:0;width:100%;border-bottom:1px solid #00a6c0;height:28px;line-height:28px; color:#00a6c0;">上传明细</p>
								<div class="child-inline-block-middle">
									<div id="fileList" class="uploader-list"></div>
								</div>	
							</div>
						</form>
						<div class="fixedBottomDiv" id="saveDiv" style="text-align: center;">
							<form enctype="multipart/form-data" method="post" id="fileForm">
		                    	<input id="selectfileInput" name="selectfileInput" type="file" accept="*" value="" style="display: none;"/>
		                    </form>
							<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="selectFile4Upload()">选择文件</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/yxzx/trcc.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/yxzx/upload.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var UPLOAD_URL ="<%=UPLOAD_URL%>";
var menuid = "<%=menuid%>";
var listbutton;
var onclickrowOobj = "";
var usercodechild = ""; //接收layer子窗口传值
var usernamechild = ""; //接收layer子窗口传值
var nowday;
var pageurl = '<%=contextPath%>/KQDS_Activity_RecordAct/selectNoPage.act';
/** 上传代码 **/
var fileArray = [];
var fileModule = "active";
/** 上传代码 end... **/
$(function() {
	initHosSelectListNoCheck('organization');// 连锁门诊下拉框
    //获取当前页面所有按钮
    getButtonAllCurPage("<%=menuid%>");
    //1、接待中心的栏目切换状态
    $('.centerWrap').on('click', '.columnWrap .columnBd li',
    function() {
        $(this).addClass('current').siblings('li').removeClass('current');
        //切换对应栏目下的表格
    });
    //进入页面默认查询今日网电预约列表
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    //时间选择
    $(".birthDate").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "buttom-right"
    });
    //时间选择
    $(".birthDate2").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "top-right"
    });
    initTable();
    //计算主体的宽度
    setWidth();
    setHeight();
    $(window).resize(function() {
        setWidth();
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });
});
function initTable() {
    //初始化表格所在div
    $('#table').bootstrapTable({
        url: pageurl,
        queryParams: queryParams,
        dataType: "json",
        showExport: true,
        //是否显示导出
        exportDataType: "basic",
        //basic', 'all', 'selected'.
        onLoadSuccess:function(){
        	setHeight()
        	/*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        },
        columns: [{
            field: ' ',
            checkbox: true
        },
        {
		    title : '序号',
		    align: "center",
		    width: 40,
		    formatter: function (value, row, index) {
		     /* return index + 1; */
		     var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
		        var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
		        return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
		    }
		   },
       	{
            title: '所属门诊',
            field: 'organizationname',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span title="' + value + '">' + value + '</span>';
                }
            }
        },{
            title: '活动日期',
            field: 'activitydate',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span title="' + value + '">' + value + '</span>';
                }
            }
        },
        {
            title: '名称',
            field: 'activityname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="remark" title="' + value + '">' + value + '</span>';
                }
            }
        },
        {
            title: '地址',
            field: 'address',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="remark" title="' + value + '">' + value + '</span>';
                }
            }
        },
        {
            title: '联系人1',
            field: 'lxr1',
            align: 'center',
            
            sortable: true,
            formatter: function(value) {
            	return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '联系人2',
            field: 'lxr2',
            align: 'center',
            
            sortable: true,
            formatter: function(value) {
            	return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '活动人数',
            field: 'activitynum',
            align: 'center',
            
            sortable: true,
            formatter: function(value) {
            	return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '活动执行',
            field: 'activitycontactsname',
            align: 'center',
            
            sortable: true,
            formatter: function(value) {
            	return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '活动内容',
            field: 'content',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="remark" title="' + value + '">' + value + '</span>';
                }
            }
        },
        {
            title: '活动投入',
            field: 'outmoney',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '客户',
            field: 'kehudesc',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="remark" title="' + value + '">' + value + '</span>';
                }
            }
        },
        {
            title: '活动优惠',
            field: 'discounts',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="remark" title="' + value + '">' + value + '</span>';
                }
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        getdata(onclickrowOobj.seqId);
    });

    $(".columns").hide();
}
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        organization: $("#organization").val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        username: $('#username').val(),
        activitycontactsSearch:$('#activitycontactsSearch').val()
    };
    return temp;
}
$('#search').on('click',
function() {
    var starttime = $("#starttime").val();
    var endtime = $("#endtime").val();
    var username = $("#username").val();
    var activitycontactsSearch = $("#activitycontactsSearch").val();
    if (starttime == "" && endtime == "" && username == "" && activitycontactsSearch == "") {
        layer.alert('请选择查询条件' );
        return false;
    }

    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
});
//详情
function getdata(seqId) {
    var detailurl = '<%=contextPath%>/KQDS_Activity_RecordAct/selectDetail.act?seqId=' + seqId;
    $.axse(detailurl, null,
    function(data) {
        loadData(data.data);
        if (document.getElementById("activitycontacts").value.trim() != "") {
            bindPerUserNameBySeqIdTB("activitycontactsDesc", document.getElementById("activitycontacts").value);
        }
        //显示附件
        showFileList();
    },
    function() {
        layer.alert('查询出错！' );
    });
}
//保存
function save() {
    var activitydate = $("#activitydate").val();
    if (activitydate == "" || activitydate == "") {
        layer.alert('请选择活动日期！' );
        return false;
    }
    
    var activityname = $("#activityname").val();
    if (activityname == "" || activityname == "") {
        layer.alert('请选输入活动名称！' );
        return false;
    }
    
    var outmoney = $("#outmoney").val();
    if (outmoney == "" || outmoney == "") {
        layer.alert('请填写活动投入！' );
        return false;
    }
    if(judgeSignFloat(outmoney)==false && judgeSign(outmoney)==false){
		layer.alert('投入金额格式不正确！' );
        return false;
 	}
    var param = $('#form1').serialize();
    var editurl = '<%=contextPath%>/KQDS_Activity_RecordAct/insertOrUpdate.act?' + param;
    $.axse(editurl, null,
    function(data) {
        if (data.retState == "0") {
            layer.alert('保存成功！'  );
            refresh();
        }
    },
    function() {
        layer.alert('保存失败！' );
    });
}
function getButtonPower() {
    $("#filePicker").hide();
    var menubutton1 = "",menubutton2 = "";
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "bc") {
            menubutton1 += ' <a href="javascript:void(0);" class="kqdsSearchBtn" onclick="save()">保存</a>';
            menubutton1 += ' <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="cleanRight()">新建</a>';
            $("#filePicker").show();
        }else if(listbutton[i].qxName == "hdjl_zxqk"){
        	menubutton2 += ' <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="zxqk(\'KQDS_ACTIVITY_RECORD\');">咨询情况</a> ';
        }else if(listbutton[i].qxName == "hdjl_skmx"){
        	menubutton2 += ' <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="skmx();">收款明细</a> ';
        }else if(listbutton[i].qxName == "hdjl_user"){
        	menubutton2 += ' <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="xxcx();">患者明细</a> ';
        }else if(listbutton[i].qxName == "hdjl_scbb"){
        	menubutton2 += ' <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="exportTable();">生成报表</a> ';
        }
        
    }
    $("#saveDiv").append(menubutton1);
    $("#clean").before(menubutton2);
}

//计算主体的宽度
function setWidth() {
    var baseW = $(window).width() - 20;
    var innerHeight_1, innerHeight_2;
    $('.centerWrap').width(baseW / 2 + 75);
    $('.lookInfoWrap').width(baseW / 2 - 100);
    $('.operateModel .optBox').width(($('.centerWrap').width() - 140) / 2); (innerHeight_1 = $('.operateModel .optBox:eq(0)').height()) > (innerHeight_2 = $('.operateModel .optBox:eq(1)').height()) ? $('.operateModel .optBox:eq(1)').height(innerHeight_1) : $('.operateModel .optBox:eq(0)').height(innerHeight_2);
    $(".fixedBottomDiv").width($(".lookInfoWrap").width() - 2);
    /*设置右侧 底部按钮固定定位后的宽度  */

}

//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height() - 15,
    optAreaHeight = $('.searchWrap').outerHeight();
    $('.lookInfoWrap .columnWrap').height(baseHeight+2);
    $('#table').bootstrapTable('resetView', {
        height: baseHeight - optAreaHeight - 3
    });

}
</script>
</html>
