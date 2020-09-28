<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	// 客服中心—客户管理页面右侧的网电信息Url和其他页面不一样，
	// 一个是kqdsFront/jdzx/dialogFrame/netorder.jsp
	// 一个是kqdsFront/jdzx/frame/kfzx/netorder_insertOrUpdate.jsp
	// 为了能统一包含一个公用JSP文件，所以在这里设置个标识，供rightPartInfo.jsp界面判断
	//staticIsUserMgrPage: value为1 网电预约  2 报备预约
	request.setAttribute("staticIsUserMgrPage", "2");
	YZPerson person = SessionUtil.getLoginPerson(request);
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<!-- 拷贝 xxcx_center.jsp 页面
	yangsen  17-0418
 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/style.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/reception_center.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/yiliao/bingli_search.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<title>信息报备</title>
<style>
/*按钮样式调整*/
.abtn {
    margin-left: 5px;
	padding:0 15px;
    height: 28px;
    border: solid 1px #0e7cc9;
    color: #0e7cc9;
    border-radius: 15px;
    line-height: 28px;
    text-align: center;
    font-size: 12px;
}
.aBtn:hover{color:#fff;background: #00A6C0 ;}
.webuploader-pick{
	color: #00a6c0 ;
}
.btn-default:hover, .btn-default:focus, .btn-default.focus, .btn-default:active, .btn-default.active, .open > .dropdown-toggle.btn-default {
    color: #0e7cc9;
    background-color: #e6e6e6;
    border-color: #adadad;
}
/*工作量表格 ，单独写*/
#gongzuol{border:solid 1px #c0c0c0;background: #f5f5f5;}
#gongzuol{margin-bottom: 10px;}
#gongzuol .columnHd{padding:0 15px;border-bottom:solid 1px #0e7ec6;font-size:16px;color: #373737;font-family: "Microsoft YaHei";line-height:36px;}
.kqds_table  td { 
	color: #666;
	padding: 1px 2px 2px 2px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	font-weight: normal;
	line-height: 28px;
}

.kqds_table  select { 
	height: 28px;
	width:90px;
	border: solid 1px #e5e5e5;
	border-radius: 3px;
}
.tableBox{
	border-right:none;
	border-radius: 6px;
}
.fixed-table-container{
	
	border-right: 1px solid #ddd;
	
	border-radius: 6px;
	/* border-top-left-radius: 6px;
	border-top-right-radius: 6px; */
	overflow: hidden;
}
input[type=text],.kv .kv-v input[type=text]{padding:0 10px;width:90px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}
	.fixed-table-container{
		padding-bottom: 89px!important;
		position: relative;
	}
	.fixed-table-pagination{
		display: block;
		width: 100%;
	    background-color: white;
	    position: absolute;
	    left: 0px;
	    bottom: 0px;
	}
	.fixed-table-container{
		background-color: white;
	}
	.fixed-table-pagination{
		border-bottom: 1px solid #ddd;
	}
	.pagination li{
		margin-left: 0px!important;
		height:auto!important;
	}
	.dropdown-menu{
		min-width: auto!important;
	    padding: 0px 0!important;
	}
	.dropdown-menu li{
		margin-left: 0px!important;
	}
</style>
</head>
<body>
<div id="container">
    <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
            	<div class="columnHd">
				    <span class="title">报备查询</span>
				</div>
                <div class="columnBd">
                    <div class="tableBox">
                        <table id="table" class="table-striped table-condensed table-bordered" data-height="467"></table>
                    </div>
                </div>
            </div>
            <div class="searchWrap">
                <div class="cornerBox">查询条件</div>
                <div class="formBox">
                    <div class="searchBox">
                    	<table class="kqds_table">
				    		<tr>
				    			<td style="text-align:right;">建档门诊：</td>
				    			<td style="text-align:left;">
										<select id="organization" name="organization"></select>
								</td>
				    		
				    			<td style="text-align:right;">创建时间：</td>
				    			<td style="text-align:left;"> 
				    				 <input type="text" class="birthDate" placeholder="开始日期" id="starttime" readonly>
				                </td>
				    			<td style="text-align:right;">到：</td>
				    			<td style="text-align:left;">
				                     <input type="text" class="birthDate" placeholder="结束日期" id="endtime" readonly>
								</td>
								<td style="text-align:right;">开发人：</td>
				    			<td style="text-align:left;">
				    					 <input type="hidden" name="kfr" id="kfr" placeholder="开发人" title="开发人" class="form-control" style="width: 80%;" value=""/>
									     <input  type="text"  id="kfrDesc" name="kfrDesc" placeholder="开发人" readonly  onClick="javascript:single_select_user(['kfr', 'kfrDesc'],'',1);"></input>	
				    			</td>
				    		</tr>
				    		<tr>	
				    			<td style="text-align:right;">建档人：</td>
				    			<td style="text-align:left;">
				    					 <input type="hidden" name="jdr" id="jdr" placeholder="建档人" title="建档人" class="form-control" style="width: 80%;" value=""/>
									     <input  type="text"  id="jdrDesc" name="jdrDesc" placeholder="建档人" readonly  onClick="javascript:single_select_user(['jdr', 'jdrDesc'],'',1);"></input>	
				    			</td>
				    			<td style="text-align:right;">咨询：</td>
				    			<td style="text-align:left;">
				    					 <input type="hidden" name="askperson" id="askperson" placeholder="咨询" title="咨询" class="form-control" style="width: 80%;" value=""/>
										 <input  type="text"  id="askpersonDesc" name="askpersonDesc" placeholder="咨询" readonly  onClick="javascript:single_select_user(['askperson', 'askpersonDesc'],'',1);"></input>	
				    			</td>
				    		
				    			<td style="text-align:right;">模糊查询：</td>
				    			<td style="text-align:left;" colspan="3">
				    				<input type="text" id="username" class="username" placeholder="请输入客户姓名、手机号" style="width:249px;"> 
				    			</td>
				    			
				    		</tr>
				    	</table> 
                    </div>
                    <input type="hidden" placeholder="" id="imgtype" name="imgtype" value="documentuser">
                    <div style="text-align: center;margin-top: 10px;" id="bottomBarDdiv">
                    	
                    </div>
                </div>
            </div>
        </div>
        <!--中间模块开关按钮  -->
        <div class="middleWrap">
			<div id="collectBtn" class="collectBtn">
				<span id="trangle"></span>
			</div>	
		</div>
        <!--右侧信息浏览-->
        <div class="lookInfoWrap">
			<%@include file="/inc/rightPartInfo.jsp" %>
		</div>
    </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/upload/uploadutil.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>

</body>
<script type="text/javascript">
var listbutton;
var contextPath = "<%=contextPath%>";
var onclickrowOobj = "";
var nowday;
var pageurl = '<%=contextPath%>/KQDS_UserDocumentAct/selectWithNopage2.act';
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var personrole = "<%=person.getUserPriv()%>";
var personroleother = "<%=person.getUserPrivOther()%>";
$(function() {
	initHosSelectList4Front('organization'); // 连锁门诊下拉框
	
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    //获取当前页面所有按钮
    getButtonAllCurPage("<%=menuid%>");
    /* 左侧个人中心的按钮样式控制js已经被抽取到rightPartInfo.jsp页面中 */

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
    togglemodel.initial("khbb",pageurl);/*wl 初始化 开关模块 */
    //4、表格初始化
    initTable();
    //计算主体的宽度
    $(window).resize(function() {
        setWidth();
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });

});
function initTable() {
	
	
    //初始化表格所在div
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList : [10, 15, 20, 25],//可以选择每页大小
        singleSelect: false,
        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
        paginationShowPageGo: true,
        onLoadSuccess:function(data){
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer").css("pointer-events","auto");
				$("#query").text("查询");
			}
        	//console.log("信息宝贝==="+JSON.stringify(data));
            setWidth();
            setHeight();
        	/*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        },
        
        columns: [
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
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            
            visible: false,
            switchable: false,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '病人编号',
            field: 'usercode',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return "<span>"+value+"</span>";
            }
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            
            formatter: function(value, row, index) {
                if (value) {
                	return '<span class="name">' + value + '</span>';
                }else{
                	return "<span>-</span>";
                }
            }
        },
        {
            title: '手机号码1',
            field: 'phonenumber1',
            align: 'center',
            formatter: function(value, row, index) {
                if (value != null || value != "") {
                    if (canlookphone == 0) {
                        return '<span class="time phone" title="' + value + '">' + value + '</span>';
                    } else {
                        return "<span>-</span>";
                    }
                } else {
                    return "<span>-</span>";
                }
            }
        },
        {
            title: '性别',
            field: 'sex',
            align: 'center',
            formatter:function(value,row,index){
            	return "<span>"+value+"</span>";
            }
        },
        {
            title: '年龄',
            field: 'age',
            align: 'center',
            
            formatter: function(value, row, index) {
                if (value == "0") {
                    return "<span>-</span>";
                } else {
                    return '<span>'+value+'</span>';
                }
            }
        },
        {
            title: '患者来源',
            field: 'devchannelname',
            align: 'center',
            formatter:function(value,row,index){
            	return "<span>"+value+"</span>";
            }
        },
        {
            title: '子分类',
            field: 'nexttypename',
            align: 'center',
            
            formatter: function(value, row, index) {
               return '<span class="source">' + value + '</span>';
            }
        },
        //     			                {title: '身份证号',field: 'idcardno',align: 'center',valign: 'middle'}, 
        {
            title: '咨询',
            field: 'askperson',
            align: 'center',
            
            formatter: function(value, row, index) {
                if (value) {
                	return '<span class="name">' + value + '</span>';
                }else{
                	return "<span>-</span>";
                }
            }
        },
        {
            title: '医生',
            field: 'doctor',
            align: 'center',
            
            formatter: function(value, row, index) {
                if (value) {
                	return '<span class="name">' + value + '</span>';
                }else{
                	return "<span>-</span>";
                }
            }
        },
        {
            title: '建档人',
            field: 'createuser',
            align: 'center',
            
            formatter: function(value, row, index) {
                if (value) {
                	return '<span class="name">' + value + '</span>';
                }else{
                	return "<span>-</span>";
                }
            }
        },
        {
            title: '开发人',
            field: 'developer',
            align: 'center',
            
            formatter: function(value, row, index) {
                if (value) {
                	return '<span class="name">' + value + '</span>';
                }else{
                	return "<span>-</span>";
                }
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        $('#tabIframe').attr('src', $('#tabIframe').attr('src')); //个人中心
    });
}
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	organization: $('#organization').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        username: $('#username').val(),
        askperson: $('#askperson').val(),
        kfr: $('#kfr').val(),
        jdr: $('#jdr').val()
    };
    console.log(temp.username)
    return temp;
}
function searchHzda() {
    var starttime = $('#starttime').val();
    var endtime = $('#endtime').val();
    var username = $('#username').val();
    var askperson = $('#askperson').val();
    var jdr = $('#jdr').val();
    var kfr = $('#kfr').val();
    if (starttime == "" && endtime == "" && username == "" && askperson == "" && jdr == "" && kfr == "") {
        layer.alert('请选择查询条件!' );
        return false;
    }
	$("#query").attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
	$("#query").text("查询中");
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
function clean() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
}
//导出
$('#export').on('click',
function() {
    var arr = [];
    $(".phone").each(function(index) {
        arr.push($(this).html());
        $(this).html("");
    });
    var li = $(".dropdown-menu").children("li").last();
    li.trigger("click");
    $(".phone").each(function(index) {
        $(this).html(arr[index]);
    });
});

//建档报备
function jdbbBtn() {
    //患者建档
    layer.open({
        type: 2,
        title: '患者建档',
        shadeClose: false,
        shade: 0.6,
        area: ['740px', '98%'],
        content: contextPath + '/KQDS_UserDocumentAct/toHzjd_Xxbb.act'      
    });
}
//信息报备
function xxbbBtn() {

    if (onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined") {
        layer.alert('请选择患者' );
        return false;
    }
    //信息报备--可修改设定开发人员
    layer.open({
        type: 2,
        title: '患者信息报备',
        shadeClose: false,
        shade: 0.6,
        area: ['750px', '90%'],
        content: contextPath + '/KQDS_UserDocumentAct/toHzjd_Xxbb.act?usercode=' + onclickrowOobj.usercode 
    });
}
//模板下载
function mbxz() {
    var downUrl = "<%=contextPath%>/KQDS_USERDOCUMENT";
    location.href = downUrl + "/downTemplet.act";
}
// 商品导入，附件上传初始化
function yxzl() {
    uploadfile(contextPath + "/FileUploadAct/uploadFile.act?module=evidence");
}
// 重写uploadutil.js中的fz()方法，实现该页面的自有业务
// 该方法在文件上传成功后执行
function fz() {
    jQuery(".file-item").hide();
    searchHzda();
}

//计算界面宽高的设置
//setWidth() ,setHeight()函数移动到tableHeaderWidth.js


function getButtonPower() {
    var menubutton1 = '<div class="col-sm-12" style="text-align:center;"><div class="child-inline-block-middle" id="uploader-demo"><div id="fileList" class="uploader-list"></div>';
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "khbb_mbxz") {
            menubutton1 += '<a id="mbxz" onclick="mbxz();" class="kqdsCommonBtn">模板下载</a>';
        } else if (listbutton[i].qxName == "khbb_plbb") {
            menubutton1 += '<a id="filePicker" style="position:relative;top:8px;" class="kqdsCommonBtn">批量报备</a>';
        } else if (listbutton[i].qxName == "khbb_jdbb") {
            menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="jdbbBtn();">建档报备</a>';
        } else if (listbutton[i].qxName == "khbb_bbxg") {
            menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="xxbbBtn();">报备修改</a>';
        }
    }
    menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="clean();">清空</a>';
    menubutton1 += '<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="searchHzda();" id="query">查询</a>';
    menubutton1 += '</div></div>';
    $("#bottomBarDdiv").append(menubutton1);
    yxzl();
    setHeight();
}    
</script>
</html>
