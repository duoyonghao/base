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
	String organization = request.getParameter("organization");
	if (organization == null) {
		organization = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>手术查询</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/yyzx.css" /><!-- 样式已经整理 -->
 <!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>   
<style type="text/css">
.refreshA1{float: right;margin-right: 6px;height: 25px;line-height: 25px;padding: 0px 15px;color: #fff;border-radius:15px;border:none;outline:none;background: #0e7cc9;}
.refreshA1:hover,.refreshA1:focus{color: #FFFFFF;text-decoration: none;background: #40a4ea;}
/*  鲍预约中心搜索样式布局修改
.register-bottom  .kv{float: left;width:230px;}
.register-bottom  .kv.w410 {float: left;width:430px;}*/
.kqds_table{
	width:100%;
	align:center;
	/* margin-left: auto; */
	margin-right: auto;
}

.kqds_table  td { 
	color: #666;
	padding: 3px 2px 5px 2px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	font-weight: normal;
	line-height: 28px;
}
input[type=text],.kv .kv-v input[type=text]{padding:0 10px;width:90px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}
select { 
	height: 28px;
	width:90px;
	border: solid 1px #e5e5e5;
	border-radius: 3px;
}
.main{
	margin-left:19px;
	padding-top:2px;
}
/* 查询条件标签样式 */
.queryText{
	height:30px;width:65px;
	line-height:30px;text-align:center;
	font-size:14px;color:#fff;
	background: #00A6C0;
	border-radius:2px;
}
.fixed-table-container{
	border-left: 1px solid #ddd;
	border-right: 1px solid #ddd;
	border-bottom:1px solid #ddd;
	border-radius: 6px;
	/* border-top-left-radius: 6px;
	border-top-right-radius: 6px; */
	overflow: hidden;
}
/* 查询条件  中的样式 */
.searchModule>header {
    height: 28px;
    padding: 5px 0 5px 15px;
    border-bottom: 1px solid #ddd;
    box-sizing: border-box;
}
.searchModule>section{
	padding:5px;
}
.searchModule>section>ul.conditions{
	margin-right:205px;
}
.searchModule>section>ul.conditions>li{
	padding:2px 0 2px;
}
.searchModule>section>ul.conditions>li>span{
	width:75px;
	text-align:right;
}
.searchModule>section>ul.conditions>li>input[type=text],
.searchModule>section>ul.conditions>li>select{
	width:100px;
	padding:0 2px;
}
.fixed-table-container{
	background-color: white;
	padding-bottom: 90px!important;
}
#organization{
	width: 145px;
}
</style>
</head>
<body>

<div class="main" style="">
	<!-- <div style="">
		<table style="width: 100%; margin-top:1px;">
			<tr>
				<td class="queryText">
					查询条件
				</td>
				<td id="tdmz" style="text-align:right;">
				</td>
			</tr>
		</table>
	</div> -->

	<div>
		<!-- 查询区域 -->
		<!-- <div class="register-bottom"> 
			  <table class="kqds_table">
		    		<tr>
		    			<td style="text-align:right;">预约时间：</td>
		    			<td style="text-align:left;width: 125px;"> 
	    					<span class="unitsDate">
	    						<input type="text" style="width:120px;" placeholder="开始时间" class="dataTimemenzhen"  id="starttime" >
	                        </span>
		                </td>
		    			<td style="text-align:right;">到：</td>
		    			<td style="text-align:left;">
							<span class="unitsDate">
	                           <input type="text" style="width:120px;" placeholder="结束时间" class="dataTimemenzhen" id="endtime" >
	                        </span>
						</td>
						<td style="text-align:right;">手术状态：</td>
		    			<td style="text-align:left;">
	    					<select name="roomstatus" id="roomstatus">
								<option value="">请选择</option>
			                	<option value="0">手术前</option>
			                	<option value="1">手术中</option>
			                	<option value="2">手术后</option>
							<select>	
		    			</td>
		    			<td style="text-align:right;">种植系统：</td>
		    			<td style="text-align:left;">
							<select class="dict" tig="ZZXT" name="zzxt" id="zzxt" ></select>
						</td>
		    			<td style="text-align:right;">医生：</td>
		    			<td style="text-align:left;">
			    			<input type="hidden" name="doctor" id="doctor" />
							<input type="text"" id="doctorDesc" name="doctorDesc" placeholder="" readonly
								onClick="javascript:single_select_user(['doctor', 'doctorDesc'],'',1);"></input>
						</td>
								
						<td style="text-align:right;">护士：</td>
						<td style="text-align:left;">
			    			<input type="hidden" name="nurse" id="nurse"/>
							<input type="text" id="nurseDesc" name="nurseDesc" placeholder="" readonly
								onClick="javascript:single_select_user(['nurse', 'nurseDesc'],'',1);"></input>			
						</td>	
		    			<td style="text-align:right;">模糊查询：</td>
		    			<td style="text-align:left;">
		    					<input type="text" id="musername" name="musername"  placeholder="患者姓名/手机" style="width: 100px;"/>
		    			</td>
		    		</tr>
			</table>
		</div> -->
		
		<!--查询条件-->
	    <div class="searchModule" style="margin-bottom:2px;text-align:right;">
	    	<header>
	    		<span>查询条件</span>
	    		<!-- <i>共有记录 <span id="total"></span> 条</i> -->
	    	</header>
	    	<section style="border-bottom:none;">
	    		<div id="tdmz" style="float:right;margin-top:2px;">
		            
		        </div>
	    		<ul class="conditions">
	    			<li class=" hide" id="roomcx_mz">
		    			<span>门诊:</span>
						<select name="organization" id="organization" ></select>
					</li>
					<li>
						<span>预约时间</span>
						<input type="text" style="font-size:11px;" placeholder="开始时间" class="dataTimemenzhen"  id="starttime" >
					</li>
					<li>
						<span>到</span>
                        <input type="text" style="font-size:11px;" placeholder="结束时间" class="dataTimemenzhen" id="endtime" >
	                </li>
					<li>
						<span>手术状态</span>
    					<select name="roomstatus" id="roomstatus">
							<option value="">请选择</option>
		                	<option value="0">手术前</option>
		                	<option value="1">手术中</option>
		                	<option value="2">手术后</option>
						</select>
		    		</li>
					<li>
						<span>种植系统</span>
						<select class="dict" tig="ZZXT" name="zzxt" id="zzxt" ></select>
					</li>
					<li>	
						<span>医生</span>
		    			<input type="hidden" name="doctor" id="doctor" />
						<input type="text" id="doctorDesc" name="doctorDesc" placeholder="" readonly
								onClick="javascript:single_select_user(['doctor', 'doctorDesc'],'',1);"></input>
					</li>
					<li>	
						<span>护士</span>
		    			<input type="hidden" name="nurse" id="nurse"/>
						<input type="text" id="nurseDesc" name="nurseDesc" placeholder="" readonly
							onClick="javascript:single_select_user(['nurse', 'nurseDesc'],'',1);"></input>			
					</li>
					<li>
						<span>客服</span>
						<input type="hidden" name="kefu" id="kefu"/>
						<input type="text" id="kefuDesc" name="kefuDesc" placeholder="" readonly
							   onClick="javascript:single_select_user(['kefu', 'kefuDesc'],'',1);"></input>
					</li>
		    		<li>	
						<span>模糊查询</span>
    					<input type="text" style="font-size:11px;" id="musername" name="musername"  placeholder="患者姓名/手机" />
		    		</li>
					<li id="zdkf" style="margin-left: 15px;">
						<!-- <span style="padding-left: 31px;"><input type="button" class="kqdsCommonBtn" onclick="setKeFu()" value="指定客服"></span> -->
					</li>
	    		</ul>
	    	</section>
	    </div>
		
	    <!--表格-->
    	<div class="tableBox" id="tableDiv1">
           	<table id="table1" class="table-striped table-condensed table-bordered"></table>
        </div>
	</div>
</div>	
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var visitname = "<%=person.getUserName()%>";
var userpriv = "<%=person.getUserPriv()%>";
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var $table1 = $('#table1');
var pageurl1 = '<%=contextPath%>/KQDS_RoomAct/selectNoPage.act';
var nowday;
var menuid = "<%=menuid%>";
var static_organization = null; //门诊
var isClick = true;

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        isdelete: 1,
        zzxt: $("#zzxt").val(),
        roomstatus: $("#roomstatus").val(),
        doctor: $("#doctor").val(),
        kefu: $("#kefu").val(),
        nurse: $("#nurse").val(),
        endtime: $("#endtime").val(),
        starttime: $("#starttime").val(),
        username: $("#musername").val(),
        organization:$("#organization").val()
    };
    return temp;
}

function queryParamsB(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	sortName:this.sortName,//排序名称
        sortOrder:this.sortOrder,//排序类型
        limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1,
        isdelete: 1,
        zzxt: $("#zzxt").val(),
        roomstatus: $("#roomstatus").val(),
        doctor: $("#doctor").val(),
        kefu: $("#kefu").val(),
        nurse: $("#nurse").val(),
        endtime: $("#endtime").val(),
        starttime: $("#starttime").val(),
        username: $("#musername").val(),
        organization:$("#organization").val()
    };
    return temp;
}
$(function() {
	$("input[type='text']").attr("autocomplete","off");  //去掉input输入框的历史记录 LUTIAN
	if('<%=organization%>' != ''){
		static_organization = '<%=organization%>';
	}else{
		static_organization = '<%=ChainUtil.getCurrentOrganization(request)%>';
	}
	
	initHosSelectListNoCheck('organization',static_organization); // 加载门诊列表
	$("#organization").change(function() {
	    var currSelect = $(this).val();
	    clearmenzhen();
	    $("#organization").val(currSelect);
	    initDictSelectByClassOrg1(currSelect);
	});
	 
	initDictSelectByClassOrg1(static_organization); //加载下拉
    //获取当前日期
    nowday = getNowFormatDate();
    $("#starttime").val(nowday + " 00:00:00");
    $("#endtime").val(nowday + " 23:59:59");

    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    //initDictSelectByClass();
    inittablemenzhen(1);
    //获取当前页面所有按钮
    getButtonAllCurPage(menuid);
    //时间选择
    $(".dataTimemenzhen").datetimepicker({
        language: 'zh-CN',
        minView: 0,
        autoclose: true,
        format: 'yyyy-mm-dd hh:ii',
        pickerPosition: "bottom-right"
    });
    setHeight();
    
    $(window).resize(function(){
    	setHeight();
    	/*table出现滚动条时 表头进行调整*/
    	setTableHeaderWidth(".tableBox");
    });
});
//清空门诊查询条件
function clearmenzhen() {
    $(".searchModule section :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
}
function query(thi) {
	$(thi).attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
	$(thi).text("查询中");
    $('#table1').bootstrapTable('refresh', {
        'url': pageurl1
    });
}
//获取选中行的usercode
function getIdSelections() {
    return $.map($table1.bootstrapTable('getSelections'),
        function(row) {
            return row;
        });
}
//指定客服
function setKeFu() {
    selectedrows = getIdSelections();
    if (selectedrows.length == 0) {
        layer.alert('请勾选复选框，选择需要指定客服的患者(可多选)' );
    } else {
        layer.open({
            type: 2,
            title: '指定客服',
            shadeClose: false,
            shade: 0.6,
            area: ['90%', '98%'],
            content: contextPath+'/KQDS_ChangeKeFuAct/setKefu.act?menuid='+"<%=menuid%>"+'&organization='+$("#organization").val()
        });
    }
}
//加载门诊表格
function inittablemenzhen(nums) {
    $table1.bootstrapTable({
        url: pageurl1,
        dataType: "json",
        cache: false,
        queryParams: queryParamsB,
        singleSelect: false,
        striped: true,
        pagination: true,//是否显示分页（*）
        pageSize: 30,
        pageList : [15, 20, 25,30],//可以选择每页大小
        sidePagination: "server",//后端分页 
        paginationShowPageGo: true,
        onLoadSuccess:function(data){
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","auto").css("pointer-events","auto");
				$("#query").text("查询");
			}
        	/*table出现滚动条时 表头进行调整*/
        	setTableHeaderWidth(".tableBox");
        },
        onDblClickCell: function(field, value, row, td) {
            //双击事件 调用提交回访结果方法 
            goToUserCenterPage(row.usercode);
        },
        rowStyle: function(row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (row.isdeletename == "已取消") {
                strclass = 'danger'; //还有一个active
            } else {
                return {};
            }
            return {
                classes: strclass
            };
        },
        //basic', 'all', 'selected'. 
         columns: [
		 {
			field: ' ',
			checkbox: true,
			formatter: stateFormatter
		 },
		 {
			title : '序号',
			align: "center",
			width: 40,
			formatter: function (value, row, index) {
			 /* return index + 1; */
			 var pageSize = $('#table1').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
				var pageNumber = $('#table1').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
				return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
			}
		 },
         {
            title: '手术室',
            field: 'roomname',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="time" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        }, {
            title: '手术状态',
            field: 'roomstatusname',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },{
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="time" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '患者姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '手机号',
            field: 'phonenumber1',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (canlookphone == 0) {
                    return '<span class="time phone" title="' + value + '">' + value + '</span>';
                } else {
                    return '-';
                }
            }
        },
        {
            title: '医生',
            field: 'doctorname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '修复医生',
            field: 'phasedoctorname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
		{
        	title: '客服',
			field: 'kefuname',
			align: 'center',

			sortable: true,
			formatter: function(value, row, index) {
				if (value) {
					html = '<span class="name" title="' + value + '">' + value + '</span>';
					return html;
				} else {
					return "";
				}
			}
		},
        {
			title: '咨询',
			field: 'askpersonname',
			align: 'center',

			sortable: true,
			formatter: function(value, row, index) {
				if (value) {
					html = '<span class="name" title="' + value + '">' + value + '</span>';
					return html;
				} else {
					return "";
				}
			}
        },
        {
			title: '护士',
			field: 'nursename',
			align: 'center',

			sortable: true,
			formatter: function(value, row, index) {
				if (value) {
					html = '<span class="name" title="' + value + '">' + value + '</span>';
					return html;
				} else {
					return "";
				}
			}
        },
        {
            title: '种植系统',
            field: 'zzxtname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="time" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '颗数',
            field: 'ks',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '开始时间',
            field: 'starttime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="time" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '结束时间',
            field: 'endtime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="time" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '备注',
            field: 'remark',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="remark" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '预约状态',
            field: 'isdeletename',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '取消人',
            field: 'delpersonname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '取消原因',
            field: 'delreason',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="remark" title="' + value + '">' + value + '</span>';
                }
            }
        },
        {
            title: '创建人',
            field: 'createusername',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '建档人',
            field: 'jdr',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '建档时间',
            field: 'jdsj',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="time" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
    });
}

var loadIndex='';
function download() {
	layer.msg('数据导出中，请等待');
	//loadIndex = layer.load(0, {shade: false});
	isClick = false;
}
function disload() {
	layer.close(loadIndex);
	layer.msg('数据导出完毕');
	isClick = true;
}
//导出
function exportTable() {
	if(isClick) {
		isClick = false;
		// console.log("生成报表")
		var fieldArr = [];
		var fieldnameArr = [];
		$('#table1 thead tr th').each(function() {
			var field = $(this).attr("data-field");
			if (field != "") {
				fieldArr.push(field); //获取字段
				fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
			}
		});
		var param = JsontoUrldata(queryParams());
		var url = pageurl1 + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
		download();
		var xhr = new XMLHttpRequest();
		xhr.open('GET', url, true);    // 也可用POST方式
		xhr.responseType = "blob";
		xhr.onload = function () {
			if (this.status === 200) {
				var blob = this.response;
				// if (navigator.msSaveBlob == null) {
				var a = document.createElement('a');
				//var headerName = xhr.getResponseHeader("Content-disposition");
				//var fileName = decodeURIComponent(headerName).substring(20);
				a.download = "手术查询";
				a.href = URL.createObjectURL(blob);
				$("body").append(a);    // 修复firefox中无法触发click
				a.click();
				URL.revokeObjectURL(a.href);
				$(a).remove();
				// } else {
				//     navigator.msSaveBlob(blob, "信息查询");
				// }
			}
			disload();
		};
		xhr.send();
	}
}
//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height();
    $('#table1').bootstrapTable('resetView', {
        height: baseHeight - $(".searchModule").outerHeight()-15
    });
}
//复选框
function stateFormatter(value, row, index) {
    if (row.id === 2) {
        return {
            disabled: true,
            checked: true
        };
    }
    if (row.id === 0) {
        return {
            disabled: true,
            checked: true
        }
    }
    return value;
}
function getButtonPower() {
    var menubutton1 = "";
    for (var i = 0; i < listbutton.length; i++) {
        if(listbutton[i].qxName == "zdkf"){
            menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="setKeFu()">指定客服</a>';
        }else if (listbutton[i].qxName == "roomcx_scbb") {
            menubutton1 += '<a  href="javascript:void(0);" class="kqdsCommonBtn" onclick="exportTable();">生成报表</a>';
        }else if (listbutton[i].qxName == "roomcx_mz") {
        	$("#roomcx_mz").removeClass("hide");
        }
    }
    menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="clearmenzhen();">清空</a>';
    menubutton1 += '<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="query(this);" id="query">查询</a>';
    $("#tdmz").append(menubutton1);
    setHeight();
}
function initDictSelectByClassOrg1(organization) {
    if ($(".dict").length > 0) {
        for (var i = 0; i < $(".dict").length; i++) {
            var dict = $(".dict").eq(i);
            // :eq() 选择器选取带有指定 index 值的元素。index值从 0 开始，所有第一个元素的 index 值是 0（不是1）。
            var type = dict.attr("tig");
            var url = contextPath + "/YZDictAct/getListByParentCodeOrg.act?parentCode=" + type;
            url += "&organization=" + organization;
            $.axse(url, null,
            function(data) {
                var list = data.list;
                if (list != null && list.length > 0) {
                    dict.empty();
                    dict.append("<option value=''>请选择</option>");
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                    }
                }else{
                	dict.empty();
                    dict.append("<option value=''>请选择</option>");
                }
            },
            function() {
                layer.alert('查询出错！', {
                });
            });
        }
    }
}
</script>
</body>
</html>