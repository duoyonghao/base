<!--wl整理  -->
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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css"/>
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/weixinModule.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/Load.js"></script>
<title>微信群发消息</title>
</head>
<style type="text/css">

.kqds_table{
	align:center;
	margin-left: auto;
	margin-right: auto;
}

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
input[type=text],.kv .kv-v input[type=text]{padding:0 10px;width:90px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}
#table tr td.bs-checkbox {
	padding:0 11px;
}
/*查询条件中的样式  */
.searchWrap .formBox>section>ul.conditions>li{
	padding: 3px 0;
}
.searchWrap .formBox>section>ul.conditions>li>span{
	width:62px;
	text-align:right;
}
.searchWrap .formBox>section>ul.conditions>li>input[type=text], 
.searchWrap .formBox>section>ul.conditions>li>select{
	width:94px;
}
@media screen and (max-width:1390px){
	.searchWrap .formBox>section>ul.conditions>li>span{
		width:55px;
		text-align:right;
		font-size:11px;
		height:24px;
		line-height:24px;
	}
	.searchWrap .formBox>section>ul.conditions>li>input[type=text], 
	.searchWrap .formBox>section>ul.conditions>li>select{
		width:82px;
		font-size:11px;
		padding:0 3px 0 5px;
		height:24px;
		line-height:24px;
	}
}
@media screen and (max-width:1100px){
	.searchWrap .formBox>section>ul.conditions>li>span{
		width:51px;
		text-align:right;
		font-size:10px;
		height:22px;
		line-height:22px;
	}
	.searchWrap .formBox>section>ul.conditions>li>input[type=text], 
	.searchWrap .formBox>section>ul.conditions>li>select{
		width:73px;
		font-size:10px;
		padding:0 3px 0 5px;
		height:22px;
		line-height:22px;
	}
}
.fixed-table-body{
	border-left:1px solid #ddd;
	border-right:1px solid #ddd;
}
</style>
<body>
<div id="container">
    <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
                <div class="columnHd"><span class="title">信息查询</span></div>
                <div class="columnBd">
                    <div class="tableBox" >
                        <table id="table" class="table-striped table-condensed table-bordered"></table>
                    </div>
                    <div id="gongzuol">
		                <div class="columnBd">
		                	<ul class="dataCountUl">
		                		<li>总记录数:<span id="zong">0</span></li>
		                	</ul>
		                </div>
		            </div>
                </div>
            </div>
             
            <div class="searchWrap">
               <!-- <div class="cornerBox">查询条件</div> -->
                <div class="searchToggleBtnGroup">
                	<span class="ptcx checked">
                		通用查询
                	</span>
                	<span>
                		高级查询
                	</span> 
                </div>
                <div class="formBox">
                    	<section>
				    		<ul class="conditions">
				    			<li>
				    				<span>所属门诊</span>
				    				<select id="organization" name="organization"></select>
				    			</li>
				    			<li class="birthDate">
				    				<span>创建时间</span>
		    						<input type="text" placeholder="开始日期" id="starttime" readonly>
				                </li>      
				    			<li class="birthDate">
				    				<span>到</span>
		                            <input type="text"  placeholder="结束日期" id="endtime" readonly>
			                    </li>    
				    			<li>
				    				<span>模糊查询</span>
				    				<input type="text" id="username" class="username" placeholder="请输入客户姓名、手机号"> 
				    			</li> 
				    			<li class="toggleTr">
				    				<span>医生</span>
			    					<input type="hidden" name="doctor" id="doctor" placeholder="医生" title="医生" class="form-control" style="width: 80%;" value=""/>
								    <input type="text"  id="doctorDesc" name="doctorDesc" placeholder="医生" readonly  onClick="javascript:single_select_user(['doctor', 'doctorDesc']);">
				    			</li> 
				    			<li class="birthDate toggleTr">
				    				<span>最近到院</span>
		    						<input type="text" placeholder="开始日期" id="dystarttime" readonly>
				                </li>     
				    			<li class="birthDate toggleTr">
				    				<span>到</span>
	                             	<input type="text"  placeholder="结束日期" id="dyendtime" readonly>
			                    </li>
				    			<li class="toggleTr">
				    				<span>第一咨询</span>
			    					<input type="hidden" name="askperson" id="askperson" placeholder="咨询" title="咨询" class="form-control" style="width: 80%;" value=""/>
									<input type="text"  id="askpersonDesc" name="askpersonDesc" placeholder="咨询" readonly  onClick="javascript:single_select_user(['askperson', 'askpersonDesc']);">	
				    			</li>
				    			<li class="toggleTr">
				    				<span>患者来源</span>
			    					<select class="patients-source select2 dict" tig="hzly"
										name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype');">
									</select>
				                </li>
				    			<li class="toggleTr">
				    				<span>子分类</span>
									<select class="select2 dict" name="nexttype" id="nexttype">
										<option value="">请选择</option>
									</select>
								</li>
				    			<li class="toggleTr">
				    				<span>绑定微信</span>
									<select name="bindWX" id="bindWX">
										<option value="">请选择</option>
										<option value="1">是</option>
										<option value="0">否</option>
									</select>
								</li>
				    			<li class="toggleTr">
				    				<span>建档人</span>
				    				<input type="hidden" name="jdr" id="jdr" placeholder="建档人" title="建档人" class="form-control" style="width: 80%;" value=""/>
									<input  type="text"  id="jdrDesc" name="jdrDesc" placeholder="建档人" readonly  onClick="javascript:single_select_user(['jdr', 'jdrDesc'],'single');"></input>	
				    			</li>
				    		</ul>
				    	</section>
                       <!-- <table class="kqds_table">
				    		<tr>
				    			<td style="text-align:right;">所属门诊：</td>
				    			<td style="text-align:left;">
										<select id="organization" name="organization"></select>
								</td>
				    			<td style="text-align:right;">创建时间：</td>
				    			<td style="text-align:left;"> 
				    					<span class="birthDate">
				    						<input type="text" placeholder="开始日期" id="starttime" readonly>
				                        </span>
				                </td>
				    			<td style="text-align:right;">到：</td>
				    			<td style="text-align:left;">
										<span class="birthDate">
				                             <input type="text"  placeholder="结束日期" id="endtime" readonly>
				                        </span>
								</td>
				    			<td style="text-align:right;">模糊查询：</td>
				    			<td style="text-align:left;">
				    				<input type="text" id="username" class="username" placeholder="请输入客户姓名、手机号"> 
				    			</td>
				    			
				    		</tr>
				    		
				    		<tr class="toggleTr">
				    		
				    			<td style="text-align:right;">医生：</td>
				    			<td style="text-align:left;">
				    					 <input type="hidden" name="doctor" id="doctor" placeholder="医生" title="医生" class="form-control" style="width: 80%;" value=""/>
									     <input  type="text"  id="doctorDesc" name="doctorDesc" placeholder="医生" readonly  onClick="javascript:single_select_user(['doctor', 'doctorDesc']);"></input>	
				    			</td>
				    			<td style="text-align:right;">最近到院时间：</td>
				    			<td style="text-align:left;"> 
				    					<span class="birthDate">
				    						<input type="text" placeholder="开始日期" id="dystarttime" readonly>
				                        </span>
				                </td>
				    			<td style="text-align:right;">到：</td>
				    			<td style="text-align:left;">
										<span class="birthDate">
				                             <input type="text"  placeholder="结束日期" id="dyendtime" readonly>
				                        </span>
								</td>
				    			<td style="text-align:right;">第一咨询：</td>
				    			<td style="text-align:left;">
				    					 <input type="hidden" name="askperson" id="askperson" placeholder="咨询" title="咨询" class="form-control" style="width: 80%;" value=""/>
										 <input  type="text"  id="askpersonDesc" name="askpersonDesc" placeholder="咨询" readonly  onClick="javascript:single_select_user(['askperson', 'askpersonDesc']);"></input>	
				    			</td>
				    		</tr>
				    		<tr class="toggleTr">
				    			<td style="text-align:right;">患者来源：</td>
				    			<td style="text-align:left;"> 
				    					<select class="patients-source select2 dict" tig="hzly"
											name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype');">
										</select>
				                </td>
				    			
				    			<td style="text-align:right;">子分类：</td>
				    			<td style="text-align:left;">
										<select class="select2 dict" name="nexttype" id="nexttype">
											<option value="">请选择</option>
										</select>
								</td>
				    			<td style="text-align:right;">绑定微信：</td>
				    			<td style="text-align:left;">
										<select name="bindWX" id="bindWX">
											<option value="">请选择</option>
											<option value="1">是</option>
											<option value="0">否</option>
										</select>
								</td>
				    			<td style="text-align:right;">建档人：</td>
				    			<td style="text-align:left;">
				    				 <input type="hidden" name="jdr" id="jdr" placeholder="建档人" title="建档人" class="form-control" style="width: 80%;" value=""/>
									 <input  type="text"  id="jdrDesc" name="jdrDesc" placeholder="建档人" readonly  onClick="javascript:single_select_user(['jdr', 'jdrDesc'],'single');"></input>	
				    			</td>
				    		</tr>
				    	</table>  -->
			    	<div id="bottomBarDdiv" class="btnBar">
                   	</div>
                </div>
            </div>
        </div>
        
        <!--右侧信息浏览-->
        <div class="lookInfoWrap">
			<%@include file="/inc/rightPartInfo.jsp" %>
		</div>
		<!-- 群发消息弹框 -->
		<div id="sendMsgDiv" style="display: none;">
    	<table style="margin:20px auto 0;">
			<tbody>
				<tr>
					<td>
						<span class="information">人员列表：</span>
					</td>
					<td>
						<textarea cols="90%;" id="personList" name="" readonly></textarea>
					</td>
				</tr>
				<tr>
					<td>
						<span class="information">发送内容：</span>
					</td>
					<td>
						<textarea cols="90%;" id="sendContent" name=""></textarea>
						<input type="hidden" id="openIds" value="">
						<input type="hidden" id="userCodes" value="">
						
					</td>
				</tr>
			</tbody>
    	</table>
    		<div style="text-align:center;margin-top:20px;">
    			<input class="abtn" type="button" value="发送" onclick="sendMsg();">
    		</div>
		</div><!-- end -->
		
    </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/DataLazyLoad.js"></script>
</body>
<script type="text/javascript">
var listbutton;
var contextPath = "<%=contextPath%>";
var onclickrowOobj = "";
var selectedrows = "";
var nowday;
var pageurl = '<%=contextPath%>/KQDS_UserDocumentAct/selectWithNopage2.act';
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var personrole = "<%=person.getUserPriv()%>";
var personroleother = "<%=person.getUserPrivOther()%>";
var loc = new Location();
var frameindex = null;

/** 微信图文对象 **/
var static_local_newsobj = new Object();
var static_local_templateobj = new Object();
$(function() {
    initHosSelectList4Front('organization'); // 连锁门诊下拉框
    initDictSelectByClass(); // 患者来源
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    /* 左侧个人中心的按钮样式控制js已经被抽取到rightPartInfo.jsp页面中 */

    //获取当前页面所有按钮
    getButtonAllCurPage("<%=menuid%>");
    //时间选择
    $(".birthDate input").datetimepicker({
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
    togglemodel.initial("ht_qfxx",pageurl);
    //4、表格初始化
    initTable();
    
    $(window).resize(function() {
        setWidth();
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });
    /* 常用查询 按钮 高级查询  按钮*/
    initSearchToggleBtnGroup();
});
function initTable() {

    //初始化表格所在div
    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        singleSelect: false,
        queryParams: queryParams,
        onLoadSuccess: function(data) { //加载成功时执行
            if (nowpage == 0 && data.total > 0) {
                maxpage = Math.floor(data.total / pagesize) + 1;
                $("#zong").html(data.total);
            }
            //分页加载
            showdata("table", data.rows);
            $("#table").bootstrapTable("resetView", {
                height: $(window).outerHeight() - $(".searchWrap").outerHeight() - $("#gongzuol").outerHeight() - $(".columnWrap .columnHd").outerHeight() - 35
            });
          	//计算主体的宽度
            setWidth();
            setHeight();
            /*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        },
        columns: [{
            field: ' ',
            checkbox: true,
            formatter: stateFormatter
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
        	title : '序号',
        	align: "center",
        	width: 40,
        	formatter: function (value, row, index) {
        		return index + 1;
        	}
        },
        {
            title: '建档时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span>' + value + '</span>';
                } else {
                    return "";
                }
            }
        },
        {
            title: '最近到院时间',
            field: 'lasttime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span>' + value + '</span>';
                } else {
                    return "";
                }
            }
        },
        {
            title: '病人编号',
            field: 'usercode',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span>' + value + '</span>';
                    return html;
                }
            }
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            
            formatter: function(value, row, index) {
                var chatHtml = '';
                if (row.openid && row.bindstatus == '0') {
                    chatHtml = '<div class="weixinIcon icon-weixin" style="color:#51C332;"></div>';
                }else{
                	chatHtml = '<div class="weixinIcon icon-weixin" style="color:grey;"></div>';
                }
                if (value) {
                    var html = '<span style="position:relative">' + value + chatHtml + '</span>';
                    return html;
                }
            }
        },
        {
            title: '性别',
            field: 'sex',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
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
            title: '咨询',
            field: 'askperson',
            align: 'center',
            
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name">' + value + '</span>';
                    return html;
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
                    html = '<span class="name">' + value + '</span>';
                    return html;
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
                        return '<span title="' + value + '">' + value + '</span>';
                    } else {
                        return '<span>-</span>';
                    }
                } else {
                    return '<span>-</span>';
                }
            }
        },
        {
            title: '手机号码2',
            field: 'phonenumber2',
            align: 'center',
            formatter: function(value, row, index) {
                if (value != null || value != "") {
                    if (canlookphone == 0) {
                        return '<span title="' + value + '">' + value + '</span>';
                    } else {
                        return '<span>-</span>';
                    }
                } else {
                    return '<span>-</span>';
                }
            }
        },
        {
            title: '患者来源',
            field: 'devchannelname',
            align: 'center',
            
            sortable: true,
            formatter: function(value) {
                return "<span class='source'>" + value + "</span>";
            }
        },
        {
            title: '来源子分类',
            field: 'nexttypename',
            align: 'center',
            
            sortable: true,
            formatter: function(value) {
                return '<span class="source">' + value + '</span>'
            }
        },
        {
            title: '省',
            field: 'provincename',
            align: 'center',
            
            sortable: true,
            formatter: function(value) {
                return "<span class='name'>" + value + "</span>";
            }
        },
        {
            title: '市',
            field: 'cityname',
            align: 'center',
            
            sortable: true,
            formatter: function(value) {
                return "<span class='name'>" + value + "</span>";
            }
        },
        {
            title: '区',
            field: 'townname',
            align: 'center',
            
            sortable: true,
            formatter: function(value) {
                return "<span class='name'>" + value + "</span>";
            }
        },
        {
            title: '建档人',
            field: 'createuser',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '有无回访',
            field: 'ywhf',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "无回访") {
                    return "<span class='label label-danger'>无回访</span>";
                } else {
                    return "<span class='label label-success'>有回访</span>";
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
        offset: 0,
        limit: pagesize,
        ispaging: 1,
        organization: $('#organization').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        dystarttime: $('#dystarttime').val(),
        dyendtime: $('#dyendtime').val(),
        username: $('#username').val(),
        askperson: $('#askperson').val(),
        doctor: $('#doctor').val(),
        devchannel: $('#devchannel').val(),
        nexttype: $('#nexttype').val(),
        //province: $('#province').val(),
        //city: $('#city').val(),
        bindWX: $('#bindWX').val(),
        jdr: $('#jdr').val()
    };
    return temp;
}
function searchHzda() {
    loadedData = [];
    nowpage = 0;
    var starttime = $('#starttime').val();
    var endtime = $('#endtime').val();
    var dystarttime = $('#dystarttime').val();
    var dyendtime = $('#dyendtime').val();
    var username = $('#username').val();
    var askperson = $('#askperson').val();
    var doctor = $('#doctor').val();
    var devchannel = $('#devchannel').val();
    var nexttype = $('#nexttype').val();
    //var province = $('#province').val();
    //var city = $('#city').val();
    var bindWX = $('#bindWX').val();
    var jdr = $('#jdr').val();

    if (starttime == "" && endtime == "" && dystarttime == "" && dyendtime == "" && username == "" && askperson == "" && doctor == "" && devchannel == "" && nexttype == "" && jdr == "" && bindWX == "") {
        layer.alert('请选择查询条件!' );
        return false;
    }
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
function clean() {
    $(".fixed-table-body").scrollTop(400);
    var rgvalue = $("#organization").val();
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#organization").val(rgvalue);
}
//获取选中行的usercode
function getIdSelections() {
    return $.map($('#table').bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
}

//计算界面宽高的设置
//setWidth() ,setHeight()函数移动到tableHeaderWidth.js


function getButtonPower() {
    var menubutton1 = "";
    menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn clean" onclick="clean()">清空</a>';
    menubutton1 += '<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="searchHzda()">查询</a>';
   /*  for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "wxzx_qfxx") { */
        	menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="senMsgBtn()">群发消息</a>';
      /*   }else if(listbutton[i].qxName == "wxzx_qftw"){ */
        	menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="newsSelect()">群发图文</a>';
       /*  }else if(listbutton[i].qxName == "wxzx_qfmb"){ */
        	menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="templateSelect()">群发模板</a>';
     /*    }
    } */
    
    $("#bottomBarDdiv").append(menubutton1);
    
    
    setHeight();
}

function senMsgBtn() {
    var selectList = getIdSelections();
    if (selectList.length == 0) {
        layer.alert('请勾选患者' );
        return false;
    }

    // 获取要删除的部门id，以逗号分隔
    var userCodes = "";
    var openIds = "";
    var userNames = "";
    for (var i = 0; i < selectList.length; i++) {
        if (selectList[i].openid && selectList[i].bindstatus == '0') {
            userCodes += selectList[i].usercode + ",";
            openIds += selectList[i].openid + ",";
            userNames += selectList[i].username + ",";
        }
    }
    userCodes = userCodes.substring(0, userCodes.length - 1);
    openIds = openIds.substring(0, openIds.length - 1);
    userNames = userNames.substring(0, userNames.length - 1);

    var divWidth = $("#sendMsgDiv").width() + 20;

    $("#personList").val(userNames);
    $("#openIds").val(openIds);
    /** 隐藏存值 **/
    $("#userCodes").val(userCodes);

    frameindex = layer.open({
        type: 1,
        title: "群发消息",
        shadeClose: false,
        shade: 0.6,
        area: [divWidth + 'px', '50%'],
        content: $('#sendMsgDiv')
    });
}

function sendMsg() {
    var sendContent = $("#sendContent").val();
    if ('' == sendContent) {
        layer.alert('请填写发送内容' );
        return false;
    }

    var param = {
        openIds: $("#openIds").val(),
        userCodes: $("#userCodes").val(),
        sendContent: $("#sendContent").val()
    };

    var url = 'WXReceiveTextAct/sendMsgBath.act';
    var serverData = getDataFromServer(url, param);
    if (serverData) {
        layer.alert(serverData.retMsrg, {
              
            end: function() {
                // 清空
                $("#personList").val('');
                $("#openIds").val('');
                $("#userCodes").val('');

                loadedData = [];
                /** 先归零  **/
                nowpage = 0;
                $('#table').bootstrapTable('refresh', {
                    'url': pageurl
                });
                layer.close(frameindex);
            }
        });
    }
}


// 图文发送
function newsSelect() {
    var selectList = getIdSelections();
    if (selectList.length == 0) {
        layer.alert('请勾选患者' );
        return false;
    }

    // 获取要删除的部门id，以逗号分隔
    var userCodes = "";
    var openIds = "";
    for (var i = 0; i < selectList.length; i++) {
        if (selectList[i].openid && selectList[i].bindstatus == '0') {
            userCodes += selectList[i].usercode + ",";
            openIds += selectList[i].openid + ",";
        }
    }
    userCodes = userCodes.substring(0, userCodes.length - 1);
    openIds = openIds.substring(0, openIds.length - 1);
	// 放到全局对象中
    static_local_newsobj.userCodes = userCodes;
    static_local_newsobj.openIds = openIds;
	
    frameindex = layer.open({
        type: 2,
        title: '图文信息',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['90%', '50%'],
        content: contextPath + '/WXNewsAct/toSelect4batchsend.act'
    });
}

/**
 * 批量发送图文
 */
function batchSendNewsMsg(newsid) {
    var param = {
    		openIds: static_local_newsobj.openIds,
    		userCodes: static_local_newsobj.userCodes,
    		newsid:newsid

    };
    var url = 'WXReceiveTextAct/sendNewsBath.act';
    var serverData = getDataFromServer(url, param);
    if (serverData && serverData.retState == 0) {
        layer.alert(serverData.retMsrg, {
              
            end: function() {
            	static_local_newsobj = new Object(); // 重新赋值
            	layer.close(frameindex); // 关闭窗口
            }
        });

    }
}



//模板选择
function templateSelect() {
    var selectList = getIdSelections();
    if (selectList.length == 0) {
        layer.alert('请勾选患者' );
        return false;
    }

    // 获取要删除的部门id，以逗号分隔
    var openIds = "";
    for (var i = 0; i < selectList.length; i++) {
        if (selectList[i].openid && selectList[i].bindstatus == '0') {
            openIds += selectList[i].openid + ",";
        }
    }
    openIds = openIds.substring(0, openIds.length - 1);
	// 放到全局对象中
    static_local_templateobj.openIds = openIds;
	
    frameindex = layer.open({
        type: 2,
        title: '模板信息',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['1000px', '460px'],
        content: contextPath + '/WXTemplateItemAct/toIndex.act'
    });
}

/*****#########################################系统数据清理 START#########################################****/

/**
 * 删除标识为删除状态用户的所有业务数据，包括档案数据
 */
function deleteUserReal() {
    // 这两个方法暂时不做！！  yangsen 6-27 10:46
}

/**
 * 设定所选用户产生的所有业务数据日志为指定日期
 */
function userDateSet() {
    // 这两个方法暂时不做！！  yangsen 6-27 10:46
}

//获取选中行的usercode
function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
}

//复选框
function stateFormatter(value, row, index) {
    return value;
}
/*****#########################################系统数据清理  END#########################################****/

</script>
</html>
