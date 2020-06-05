<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	String source = request.getParameter("source");
	String menuid = request.getParameter("menuId");
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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/yyzx/netorder.css"/>
<!-- table数据表中的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css"/>
<style>
	.fixed-table-container{
		border-left: 1px solid #ddd;
		border-right: 1px solid #ddd;
		border-bottom:1px solid #ddd;
		border-radius: 6px;
		/* border-top-left-radius: 6px;
		border-top-right-radius: 6px; */
		overflow: hidden;
	}
</style>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
</head>
<body>
<div class="container" style="padding-top:10px;">
		<!-- <p class="contentTitle">网电咨询信息</p> -->	<!-- “网电咨询信息”的样式 -->
		<input type="hidden" name="seqId" id="seqId">
		<input type="hidden" name="isdelete" id="isdelete">
		<table class="tableLayout bottomBorderTable">	<!-- tableLayout的布局 基本样式  bottomBorderTable提供下边框 -->
			<tr>
				<td>	<!--.contentText 该页面 信息文本的基本样式 -->
					<span class="contentText">预约门诊</span>
				</td>
				<td>
					<select id="organization" name="organization">
               		</select>
				</td>
			</tr>
			<tr>
				<td>	<!--.contentText 该页面 信息文本的基本样式 -->
					<span class="contentText">患者编号</span>
				</td>
				<td>
					<input type="text" id="usercode" name="usercode" value="" disabled>
				</td>
				
				<td>	<!--.contentText 该页面 信息文本的基本样式 -->
					<span class="contentText">患者姓名</span>
				</td>
				<td>
					<input type="text" id="username" name="username" value="" disabled>
				</td>
			</tr>
			<tr>
				<td> 	<!--.contentText 该页面 信息文本的基本样式 -->
					<span class="contentText">咨询日期</span>
				</td>
				<td>
					<input type="text" id="acceptdate" name="acceptdate" value="" disabled >
				</td>
				<td>	<!--.contentText 该页面 信息文本的基本样式 -->
					<span class="contentText">预约日期</span>
				</td>
				<td>
					<input type="text" id="ordertime" name="ordertime" value="" readonly="readonly" >
					<input type="hidden" id="guidetime" name="guidetime" value=""  >
				</td>
			</tr>
			<tr>
				<td>
					<span class="contentText">受理类型</span>
				</td>
				<td>
					<select class="dict" tig="SLLX" name="accepttype" id="accepttype">
				</select>
				</td>
				<td>
					<span class="contentText" id="tool">受理工具</span>
				</td>
				<td>
					<select class="dict" tig="SLGJ" name="accepttool" id="accepttool">
					</select>
				</td>
			</tr>
			<tr class="textareaTr"> 	<!-- textarea所在的行加的样式   用于调整 textarea的与其它行的间距  -->
				<td>
					<span class="contentText">咨询内容</span>
				</td>
				<td colspan="3">
					<textarea  name="askcontent" id="askcontent" rows="3" ></textarea>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="contentText">咨询项目</span>
				</td>
				<td> <!--.dict本身无样式 但是有功能  -->
					<select class="dict" tig="ZXXM" name="askitem" id="askitem">
					</select>
				</td>
				<td>
					<span class="contentText">患者状态</span>
				</td>
				<td>
					<select name="doorstatus" id="doorstatus">
                	<option value="">请选择</option>
                	<option value="0">未上门</option>
                	<option value="1">已上门</option>
				</select>
				</td>
			</tr>
		</table>
		
		<div class="buttonBar" id="buttonBarDiv"> <!--buttonBar 按钮组的布局    -->
			
		</div>
		
		<table class="tableLayout">  <!-- tableLayout  本页面table布局的基本样式 -->
			<tr>
				<td>	<!--.contentText 该页面 信息文本的基本样式 -->
					<span class="contentText">安排科室</span>
				</td>
				<td>
					<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_1%>" name="regdept" id="regdept" disabled>
					</select>
				</td>
				<td>	<!--.contentText 该页面 信息文本的基本样式 -->
					<span class="contentText">安排医生</span>
				</td>
				<td>
					<input type="hidden" name="orderperson" id="orderperson" value="" title="主治医生" class="form-control" />
					<input type="text" id="orderpersonDesc" name="orderpersonDesc" value="" onClick="javascript:single_select_user(['orderperson', 'orderpersonDesc']);" disabled>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="contentText">咨询</span>
				</td>
				<td>
					<input type="hidden" name="askperson" id="askperson" value="" title="咨询人" />
					<input type="text" id="askpersonDesc" name="askpersonDesc" value="" onClick="javascript:single_select_user(['askperson', 'askpersonDesc']);" disabled>
				</td>
				<td>
					<span class="contentText">导医</span>
				</td>
				<td>
					<input type="hidden" name="daoyi" id="daoyi" value="" title="导医" />
					<input type="text" id="daoyiDesc" name="daoyiDesc" value="" onClick="javascript:single_select_user(['daoyi', 'daoyiDesc']);" disabled>
				</td>
			</tr>
			
			<tr class="textareaTr">  <!-- textarea所在的行加的样式   用于调整 textarea的与其它行的间距  -->
				<td>
					<span class="contentText">患者备注</span>
				</td>
				<td colspan="3">
					<textarea name="remark" id="remark" rows="3"></textarea>
				</td>
			</tr>
		</table>
		
		<div class="buttonBar"></div> <!--buttonBar 按钮组的布局    -->
		
		<!-- <div class="tableHd">网电咨询记录</div> -->
        <div class="tableBox">
            <table id="table" class="table-striped table-condensed table-bordered" data-height="300"></table>
        </div>
	</div>
</body>

<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var source = '<%=source%>';
//点击左侧网店预约列表传值 预约编号或者详情 
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var listbutton = parent.listbutton;
var $table = $('#table');
var pageurl = '<%=contextPath%>/KQDS_Net_OrderAct/selectNoPage.act';
var onclickrowOobj = ""; //表格点击获取到的对象
var parentonclickrowObj = window.parent.onclickrowOobj; //父页面选中行对象
// 进入页面的时间
var intoPageDateTime = new Date();
var canEditWd = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag8_canEditWd, request)%>';

var loadperson='<%=person.getUserPriv()%>';
var load=loadperson.split(",");//登陆这权限
var allowedperson='<%=SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK)%>';
var allowed=allowedperson.split(",");//允许权限

$(function() {
	
	
	/** ########################################################### 连锁相关  **/
	initHosSelectListNoCheck('organization');// 连锁门诊下拉框
	/** ########################################################### 连锁相关  end **/
	
    getButtonPower();
    initDictSelectByClass(); // 受理类型 受理工具 咨询项目
    var tmpOrganization = $("#organization").val();
    initDeptSelectByTypesAndClass(tmpOrganization);
    $("#organization").change(function() {
        var currSelect = $(this).val();
        initDeptSelectByTypesAndClass(currSelect);
    });
    if (parentonclickrowObj != null && parentonclickrowObj != "") {
        //如果父页面传值不为空 则使用父页面传值作为参数查询详情及列表
        pageurl = pageurl + '?usercode=' + parentonclickrowObj.usercode;
        //展示患者名称和咨询日期
        $("#usercode").val(parentonclickrowObj.usercode);
        $("#username").val(parentonclickrowObj.username);
        $("#acceptdate").val(parentonclickrowObj.CreateTime);
        //加载列表
        initTable();
    }

    //时间选择
    $("#acceptdate").datetimepicker({
        language: 'zh-CN',
        minView: 0,
        format: 'yyyy-mm-dd hh:ii',
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "bottom-right"
    });
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
    
    
    
    
});

//判断当前人员权限是否为前台
var total=load.concat(allowed);
function isExist(total) {
    var exist = {};
    for(var i in total) {
        if(exist[total[i]]) {
            return true;
        }
        exist[total[i]] = true;
    }
    return false;
}
var existornot=isExist(total);
if(existornot){
	
}else{
	$('#accepttool').attr('style', 'display:none');
	$('#tool').attr('style', 'display:none');
} 


function noEdit() {
    $(":input[type=text]").each(function(i) {
        var text = $(this).val();
        if (text != "") {
            $(this).attr("disabled", "disabled");
        }
    });
    $("select").each(function(i) {
    	//var selectId = $(this).attr("id");
    	
        var text = $(this).val();
        if (text != "") {
            $(this).attr("disabled", "disabled");
            
            //if(selectId == 'organization'){ // 连锁门诊下拉框，暂时不做特殊处理
        	//	$(this).removeAttr("disabled"); 
        	//}
        }
        
    });
    $("textarea").each(function(i) {
        var text = $(this).val();
        if (text != "") {
            $(this).attr("disabled", "disabled");
        }
    });
    //如果到诊时间 有值 不可选预约时间
    /* if ($("#guidetime").val() == "" && canEditWd == 1) { */
    if ($("#guidetime").val() == "") {
        $("#ordertime").attr("disabled", false);
    }
}

//清空并把文本框切换为可编辑状态
function clean() {
    $("#seqId").val(""); //清空id 保存时即为新增操作
    $("#ordertime").val("");
    $("#ordertime").attr("disabled", false);
    $("#askcontent").val("");
    $("#askcontent").attr("disabled", false);
    $("#askitem").val("").trigger("change");
    $("#askitem").attr("disabled", false);
    $("#remark").val("");
    $("#remark").attr("disabled", false);
    // 清空连锁
    // $("#organization").val("");
} 
//保存预约信息
function baocun() {
    //验证
    var seqId = $("#seqId").val();
    var usercode = $("#usercode").val();
    var username = $("#username").val();
    var acceptdate = $("#acceptdate").val();
    var ordertime = $("#ordertime").val();
    var accepttype = $("#accepttype").val();
    var accepttool = $("#accepttool").val();
    var askcontent = $("#askcontent").val();
    var askitem = $("#askitem").val();
    var doorstatus = 0;

    if (ordertime != '') {

        // 预约时间对象
        var ordertimeObj = new Date(ordertime + ':00'); // 2017-05-04 17:10
        // 咨询日期对象
        var acceptdateObj = new Date(acceptdate);

        // 不得早于 咨询日期
        if (acceptdateObj.getTime() >= ordertimeObj.getTime()) {
            layer.alert('预约日期不得早于咨询日期'  );
            return false;
        }

        if (intoPageDateTime.getTime() >= ordertimeObj.getTime()) {
            layer.alert('预约日期不得早于操作时间'  );
            return false;
        }
    }
    
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
        seqId: seqId,
        source: source,
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
    if (ordertime == "" || ordertime == null) {
        layer.confirm('是否选择预约日期？', {
            btn: ['是', '否'] //按钮
        },
        function() {
            //选择填写预约日期，则不执行保存
            layer.closeAll('dialog');
            return false;
        },
        function() {
            save(submiturl, param);
        });
    } else {
        save(submiturl, param);
        /* 
		var d=new Date(Date.parse(ordertime+" 23:59:59"));
		var curDate=new Date();
		if(d < curDate){
			   layer.alert('预约日期不能小于当天！' );
			   return false;
		}
		var flag = checkYy(usercode,ordertime);
		if(!flag){
			return false;
		}else{
			save(submiturl,param);
		} */
    }
}
function save(submiturl, param) {
    var msg;
    $.axseSubmit(submiturl, param,
    function() {
        msg = layer.msg('加载中', {
            icon: 16
        });
    },
    function(r) {
        if (r.retState == "0") {
            layer.alert('操作成功', {
                  
                end: function() {
                    window.location.reload();
                    parent.layer.close(frameindex); //再执行关闭
                }
            });
        } else {
            layer.alert('预约失败'  );
        }
    },
    function() {
        layer.alert('预约失败' );
    });
}
//加载表格
function initTable() {
    $table.bootstrapTable({
        url: pageurl,
        dataType: "json",
        singleSelect: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
            var allTableData = $("#table").bootstrapTable('getData'); //获取表格的所有内容行
            if (allTableData != null && allTableData.length > 0) {
                showorderdetail(allTableData[0]); //表格加载成功后加载详情
            }
        },
        rowStyle: function(row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (row.isdelete == "1") {
                strclass = 'danger'; //还有一个active
            } else {
                return {};
            }
            return {
                classes: strclass
            };
        },
        columns: [
		{
		    title: '上门门诊',
		    field: 'organizationname',
		    align: 'center',
		    valign: 'middle',
		    visible: true,
		    switchable: false,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                }
            }
		},
        {
            title: '患者姓名',
            field: 'username',
            align: 'center',
            valign: 'middle',
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
            title: '预约时间',
            field: 'ordertime',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '到诊时间',
            field: 'guidetime',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '本次上门状态',
            field: 'doorstatus',
            align: 'center',
            valign: 'middle',
            sortable: true,
            width: '10%',
            formatter: function(value, row, index) {
                if (value == 1) {
                    return '<span style="color:green">已上门</span>';
                } else {
                    return '<span style="color:red">未上门</span>';
                }
            }
        },
        {
            title: '本次成交状态',
            field: 'cjstatus',
            align: 'center',
            valign: 'middle',
            sortable: true,
            width: '10%',
            formatter: function(value, row, index) {
                if (value == 1) {
                    return '<span style="color:green">已成交</span>';
                } else {
                    return '<span style="color:red">未成交</span>';
                }
            }
        },
        {
            title: '咨询项目',
            field: 'askitemname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                	return "<span  class='name'>"+value+"</span>";
                }
            }
        },
        {
            title: '是否取消',
            field: 'isdelete',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
              	  str = (value=="0"?"正常":"已取消");
                  return '<span class="source" >'+str+'</span>';
                }
            }
        },
        {
            title: '网电咨询内容',
            field: 'askcontent',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span  class="remark" title="' + value + '">' + value + '</span>';
                }
            }
        },
        {
            title: '网电备注',
            field: 'remark',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (row.remark != "" && row.remark != null) {
                    return '<span class="remark" title="' + value + '">' + value + '</span>';
                } else {
                    return "";
                }
            }
        },
        {
            title: '创建人',
            field: 'createusername',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return "<span  class='name'>"+value+"</span>";
                }
            }
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        showorderdetail(onclickrowOobj); //展示上方预约详情
    });
}
//根据选中行的seqId  查询详情并填充上方预约详情
function showorderdetail(obj) {
    if (obj == "" || obj == null) {
        layer.alert('请选择一条预约记录' );
        return;
    }
    var detailurl = '<%=contextPath%>/KQDS_Net_OrderAct/selectDetail.act?seqId=' + obj.seqId;
    $.axse(detailurl, null,
    function(data) {

        //清空原有内容
        clean();

        //填充
        loadData(data.data);

        //详情页面展示选择的用户
        if (document.getElementById("askperson").value.trim() != "") {
            bindPerUserNameBySeqIdTB("askpersonDesc", document.getElementById("askperson").value);
        }
        if (document.getElementById("orderperson").value.trim() != "") {
            bindPerUserNameBySeqIdTB("orderpersonDesc", document.getElementById("orderperson").value);
        }
        if (document.getElementById("daoyi").value.trim() != "") {
            bindPerUserNameBySeqIdTB("daoyiDesc", document.getElementById("daoyi").value);
        }
        
        
        noEdit();

        $("#remark").attr("disabled", false);
    },
    function() {
        layer.alert('查询出错！' );
    });
}

// 新增操作
function newAdd(){
	clean();
	noEdit();
	$("#organization").removeAttr("disabled");
}
//取消预约
function quxiao(){
	var seqId = $("#seqId").val();
	var isdelete = $("#isdelete").val();
	if(!seqId) {
		layer.alert("请选择预约信息");
	}
	if(isdelete == '1') {
		layer.alert("该预约已经取消");
		return;
	}
	if ($("#guidetime").val() != "") {
		layer.alert("已到诊预约无法取消");
		return;
    }
	if ($("#ordertime").val() == "") {
		layer.alert("该条预约信息未设置预约时间");
		return;
    }
	
	layer.alert("确认取消本次预约？", {
		  closeBtn: 1    // 是否显示关闭按钮
		  ,anim: 1 //动画类型
		  ,btn: ['确认','关闭'] //按钮
		  ,yes:function(index){
			  $.ajax({
					url: '<%=contextPath%>/KQDS_Net_OrderAct/updateIsDeleteById.act?',
					type:"POST",
					dataType:"json",
					data : {"seqId":seqId},
					success:function(result){
						layer.alert(result.retMsrg, function(index) {
							$table.bootstrapTable('refresh', {
						        'url': pageurl
						    });
							layer.close(index);
						})
					}
				});
		  }
		  ,btn2:function(){}
	})
}

//按钮权限
function getButtonPower() {
	if (parentonclickrowObj != null && parentonclickrowObj != "") {
		 if(parentonclickrowObj.type != 1){
		     	return false;
	     }
	} 
    var menubutton = "";
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "kf_wd_xz") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="newAdd()">新增</a>';
        }
        if (listbutton[i].qxName == "kf_wd_qx") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsSearchBtn" onclick="quxiao()">取消</a>';
        }
        if (listbutton[i].qxName == "kf_wd_bc") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="baocun()">保存</a>';
        } 
    }
    $("#buttonBarDiv").append(menubutton);
}
</script>
</html>
