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
	Object isyx = request.getAttribute("isyx");//营销中心进入
	if (isyx == null) {
		isyx = "0";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>预约中心</title><!-- 点击预约中心 按钮进入  以表格方式展现 ，分网电和门诊预约 -->
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

/* 查询条件标签样式 */
.queryText{height:30px;width:65px;line-height:30px;text-align:center;font-size:14px;color:#fff;background: #0e7cc9;}
</style>
</head>
<body>
<div style="padding:5px;">
	<table style="width: 100%; ">
		<tr>
			<td id="tdwd" style="text-align:right;">
				<a id="clearwangdian" href="javascript:void(0);" class="kqdsCommonBtn">清空</a>
				<a id="searchwangdian" href="javascript:void(0);" class="kqdsSearchBtn">查询</a>
			</td>
		</tr>
	</table>
</div>
	<div class="" id="wangdian">
		<div class="register-bottom">
		<table class="kqds_table">
    		<tr>
    		
    			<td style="text-align:right;">所属门诊：</td>
    			<td style="text-align:left;">
						<select id="organization" name="organization"></select>
				</td>
    		
                <td style="text-align:right;">预约时间：</td>
    			<td style="text-align:left;width: 125px;"> 
   					<span class="unitsDate">
   						<input type="text"  placeholder="开始时间" class="dataTimewangdian" id="wstarttime"  style="width: 120px;">
                    </span>
                </td>
    			<td style="text-align:right;">到：</td>
    			<td style="text-align:left;">
					<span class="unitsDate">
                          <input type="text"  placeholder="结束时间" class="dataTimewangdian" id="wendtime"  style="width: 120px;">
                    </span>
				</td>
				<td style="text-align:right;">本次上门状态：</td>
    			<td style="text-align:left;">
   					<select name="worderstatus" id="worderstatus">
						<option value="">请选择</option>
	                	<option value="0">未上门</option>
	                	<option value="1">已上门</option>
					<select>	
    			</td>
    			<td style="text-align:right;">咨询项目：</td>
    			<td style="text-align:left;">
   					 <select class="dict" tig="ZXXM" name="xiangmu" id="xiangmu" ></select>
    			</td>
    			<td style="text-align:right;">医生/咨询：</td>
    			<td style="text-align:left;">
	    			<input type="hidden" name="askperson2" id="askperson2" value=""
						placeholder="" title="" class="form-control" style="width: 70px;" />
					<input type="text" id="askpersonDesc2" name="askpersonDesc2" placeholder="" readonly
						onClick="javascript:single_select_user(['askperson2', 'askpersonDesc2'],'',1);"></input>
				</td>
								
				<td style="text-align:right;">建档人：</td>
				<td style="text-align:left;">
	    			<input type="hidden" name="createuser2" id="createuser2" value=""
						placeholder="" title="" class="form-control" style="width: 70px;" />
					<input type="text" id="createuserDesc2" name="createuserDesc2" placeholder="" readonly
						onClick="javascript:single_select_user(['createuser2', 'createuserDesc2'],'',1);"></input>
				</td>	
    			
    			
    			<td style="text-align:right;">模糊查询：</td>
    			<td style="text-align:left;"> 
   					<input type="text" id="wusername" name="wusername"  placeholder="患者姓名/手机" style="width: 100px;"/>
                </td>
				<td style="text-align:left;">
				</td>
    		</tr>
			</table>
		</div>
	    <!--表格-->
    	<div class="tableBox" id="tableDiv2" style="margin-left:20px;overflow: hidden;border-radius:8px;border-top-left-radius: 6px;border-top-right-radius: 6px;border-left: 1px solid #ddd;border-right: 1px solid #ddd;border-bottom:1px solid #ddd;">
           	<table id="table2" class="table-striped table-condensed table-bordered"></table>
        </div>
        
         <div id="gongzuol" style="margin-left: 4px">
             <div class="columnBd">
             	<ul class="dataCountUl" id="dataCount">
             		<li>总记录数:<span id="wdzong">0</span></li>
             		<li>本次上门数:<span id="wdsms">0</span></li>
             		<li>本次未上门数:<span id="wdwsms">0</span></li>
             		<li>成交数:<span id="wdcj">0</span></li>
             		<li>未成交数:<span id="wdwcj">0</span></li>
             	</ul>
             </div>
         </div>
	</div>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
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
var $table2 = $('#table2');
var pageurl2 = '<%=contextPath%>/KQDS_Net_OrderAct/selectNoPageYYZXWd.act';
var nowday;
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var clickrow = "";
var menuid = "<%=menuid%>";
var isyx = "<%=isyx%>";
function queryParams2(params) {
	var datetype = "2";
	if(isyx == "1"){ // 1 是营销  2 是网电
		datetype = "3";
	}
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的 
    	limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        sortName:this.sortName,
        sortOrder:this.sortOrder,
        pageIndex : params.offset/params.limit + 1,
        organization: $("#organization").val(),
        // 增加门诊条件过滤，挂号查询单个患者时，不进行门诊条件过滤 
        xiangmu: $("#xiangmu").val(),
        datetype:datetype,//网电
        doorstatus: $("#worderstatus").val(),
        starttime: $("#wstarttime").val(),
        endtime: $("#wendtime").val(),
        username: $("#wusername").val(),
        askperson: $("#askperson2").val(),
        createuser: $("#createuser2").val()
    };
    return temp;
}
$(function() {
    //获取当前页面所有按钮
    getButtonPowerByPrivSelf(menuid);
    
    //initHosSelectListNoCheck('organization'); // 连锁门诊下拉框	
    initHosSelectListNoCheck('organization','<%=ChainUtil.getCurrentOrganization(request)%>'); // 加载门诊列表
	
    //获取当前日期
    nowday = getNowFormatDate();
    $("#wstarttime").val(nowday);
    $("#wendtime").val(nowday);
    //绑定两个时间选择框的chage时间
    $("#wstarttime,#wendtime").change(function() {
        timeCompartAndFz("wstarttime", "wendtime");
    });

    initDictSelectByClass();
    inittablewangdian(1);
    //时间选择
    $(".dataTimewangdian").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "bottom-right"
    });
    //清空网电查询条件
    $('#clearwangdian').on('click',
    function() {
        $("#wangdian :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    });
    $('#searchwangdian').on('click',
    function() {
        $('#table2').bootstrapTable('refresh', {
            'url': pageurl2
        });
    });
    setHeight();
    $(window).resize(function(){
    	setHeight();
    	/*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    })
});
//加载网电表格
function inittablewangdian(nums) {

    if (userpriv != "1") {
        //非管理员 则隐藏门诊下拉框
        $("#wangdianorg").hide();
    }
    $table2.bootstrapTable({
        url: pageurl2,
        contentType : "application/x-www-form-urlencoded",   //必须有
        queryParams: queryParams2,
        dataType: "json",
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList : [15, 20, 25],//可以选择每页大小
        singleSelect: false,
        striped: true,
        paginationShowPageGo: true,
        sidePagination: "server",//后端分页 
        onDblClickCell: function(field, value, row, td) {
            //双击事件 调用提交回访结果方法 
            goToUserCenterPage(row.usercode);
        },
        onLoadSuccess: function(data) { //加载成功时执行
            if_wd_table_int = "wd_table_has_init";

            var tableList = data.nums[0];
            $("#wdzong").html(data.total);
            /* var cjs = 0,
            wcjs = 0,
            sms = 0,
            wsms = 0;
            for (var i = 0; i < tableList.length; i++) {
                if (tableList[i].cjstatus == "已成交") {
                    cjs = cjs + 1;
                } else {
                    wcjs = wcjs + 1;
                }
                if (tableList[i].doorstatus == "已上门") {
                    sms = sms + 1;
                } else {
                    wsms = wsms + 1;
                }
            } */
            $("#wdcj").html(tableList.cjstatus);
            $("#wdwcj").html(Number(data.total)-Number(tableList.cjstatus));
            $("#wdsms").html(tableList.doorstatus);
            $("#wdwsms").html(Number(data.total)-Number(tableList.doorstatus));
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
				var pageSize = $('#table2').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
		    	var pageNumber = $('#table2').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
		    	return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
			}
		},
		{
		    title: '预约门诊',
		    field: 'organizationname',
		    align: 'center',
		    formatter:function(value){
		    	return '<span>'+value+'</span>';
		    }
		},
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '<span></span>';
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
                    return '<span class="name"></span>';
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
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '<span>-</span>';
                }
            }
        },
        {
            title: '咨询',
            field: 'askperson',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '<span class="name">-</span>';
                }
            }
        },
        {
            title: '预约时间',
            field: 'ordertime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '<span></span>';
                }
            }
        },
        {
            title: '到诊时间',
            field: 'guidetime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '<span></span>';
                }
            }
        },
        {
            title: '上门状态',
            field: 'zdoorstatus',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "已上门") {
                    return '<span class="label label-success">已上门</span>';
                } else {
                    return '<span class="label label-danger">未上门</span>';
                }
            }
        },
        {
            title: '本次上门',
            field: 'doorstatus',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "已上门") {
                    return '<span class="label label-success">已上门</span>';
                } else {
                    return '<span class="label label-danger">未上门</span>';
                }
            }
        },
        {
            title: '本次成交',
            field: 'cjstatus',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "已成交") {
                    return '<span class="label label-success">已成交</span>';
                } else {
                    return '<span class="label label-danger">未成交</span>';
                }
            }
        },
        {
            title: '咨询项目',
            field: 'askitem',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '<span></span>';
                }
            }
        },
        {
            title: '网电咨询内容',
            field: 'askcontent',
            align: 'center',
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="remark" title="' + value + '">' + value + '</span>';
                }else{
                	return '<span class="remark"></span>';
                }
            }
        },
        {
            title: '网电备注',
            field: 'remark',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (row.remark != "" && row.remark != null) {
                    return '<span class="remark" title="' + value + '">' + value + '</span>';
                } else {
                    return '<span class="remark"></span>';
                }
            }
        },
        {
            title: '建档人',
            field: 'jdr',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '<span class="name"></span>';
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
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '<span></span>';
                }
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $table2.find('tr.success').data('index'); //获得选中的行
        clickrow = $table2.bootstrapTable('getData')[index];
    });
}
//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height();
    $('#table2').bootstrapTable('resetView', {
        height: baseHeight -$(".register-bottom").outerHeight()-$("#gongzuol").outerHeight()-40
    });
}
//传入页面需要获取的权限按钮数组,及对应点击触发的方法名
function getButtonPowerByPrivSelf(menuid) {
    //根据用户角色获取按钮权限
    var pageurl = contextPath + '/YZButtonAct/getButtonListByPriv.act?menuid=' + menuid;
    $.axse(pageurl, null,
    function(r) {
        if (r.retState == "0") {
            var listbutton = r.retData;
            var menubutton2 = "";
            //验证是存在权限
            for (var i = 0; i < listbutton.length; i++) {
                if (listbutton[i].qxName == "wdyy_scbb") {
                    menubutton2 += '<a href="javascript:void(0);" class="refreshA1" onclick="exportwd();">' + listbutton[i].bz + '</a>';
                }
            }
            $("#searchwangdian").before(menubutton2);
        } else {
            layer.alert('获取按钮失败'  );
        }
    },
    function() {
        layer.alert('获取按钮失败' );
    });
}

</script>
</body>
</html>