<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String static_costno = request.getParameter("costno");
	String static_usercode = request.getParameter("usercode");
	String static_regno = request.getParameter("regno");
	
	//获取是否开启免密支付
	String HYK_NO_PASSWORD = SysParaUtil.getSysValueByName(request, SysParaUtil.HYK_NO_PASSWORD);
	if (HYK_NO_PASSWORD == null) {
		HYK_NO_PASSWORD = "1"; //1需要密码  0 免密 
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>费用清单-结账</title> <!-- 接待中心 下方 结账 按钮进入 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/addVisting.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/select2.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/costOrder/cost.css"/>
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<style>
.fixedBottomDiv>.aBtn{
	width:auto;
	padding:0 15px;
}
section .operateDiv .tableLayoutLeft .aBtn{
	width:110px;
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
.fixed-table-container thead th .sortable{
	padding-right:8px;
}
</style>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script	type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/member/kqds_member_binding.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
</head>
<body>
	<form id="form1">
		<input type="hidden" id="costno" name="costno" >
		<input type="hidden" id="regno" name="regno" >
		
		<div class="checkoutContainer">
		<!-- 客户相关信息 -->
			<header>
				<table class="tableLayout">
					<tbody>
						<tr>
							<td>
								<span class="commonText">病人编号</span>
							</td>
							<td>
								<input type="text" id="usercode" name="usercode" readonly>
							</td>
						
							<td>
								<span class="commonText">姓名</span>
							</td>
							<td>
								<input type="text" id="username" name="username" readonly>
								<input type="hidden" id="age" name="age" readonly>
							</td>
							<td>
								<span class="commonText">医生</span>
							</td>
							<td>
								<input type="hidden" name="doctor" id="doctor"  />
								<input type="text" id="doctorDesc" name="doctorDesc" placeholder="医生" readonly  >
							</td>
							<td>
								<span class="commonText">护士</span>
							</td>
							<td>
								<input type="hidden" name="nurse" id="nurse" />
								<input type="text" id="nurseDesc" name="nurseDesc" placeholder="护士" readonly>
							</td>
						</tr>
						
						<tr>
							<td>
								<span class="commonText">护士</span>
							</td>
							<td>
								<input type="hidden" name="techperson" id="techperson" />
								<input type="text" id="techpersonDesc" name="techpersonDesc" placeholder="护士" readonly>
							</td>
						
							<td>
								<span class="commonText">咨询</span>
							</td>
							<td>
								<input type="hidden" name="askperson" id="askperson" />
					   			 <input type="text" id="askpersonDesc" name="askpersonDesc" placeholder="咨询" readonly>	
							</td>
							<td>
								<span class="commonText">积分余额</span>
							</td>
							<td>
					   			 <input type="text" id="integral" name="integral"  readonly>	
							</td>
						</tr>
						
					</tbody>
				</table>
			</header>
			<!-- table表与操作区 -->
			<section>
				<!-- 收款项目展示 -->
				<div class="tableBox">
					<p class="tableTitle">
						收费项目
					</p>
					<table id="table" class="table-striped table-condensed table-bordered tableLayout"  data-height="250"></table>
		 	    	<table id="tabledayin" class="table-striped table-condensed table-bordered" style="display: none;"></table>
				</div>
				
				<!-- 付款操作区域 -->
				<div class="operateDiv" style="position:relative;">
					<!--左侧会员卡选择 备注  -->
					<span class="highLightText"></span>
					<table class="tableLayout tableLayoutLeft">
						<tbody>
							<tr>
								<td>
									<span  class="commonText">会员卡号</span>
									
								</td>
								<td>
									<select name="hyxxSelect" class="select2" id="hyxxSelect">
										<option value="">请选择</option>
									</select>
								</td>
								<td colspan="2">
									<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="setyjj();" >>>预交金使用</a>
								</td>
							</tr>
							<tr>
								<td>
									<span class="commonText">卡类</span>
								</td>
								<td>
									<select class="dict" tig="HYKFL" readonly="readonly" name="memberlevel" id="memberlevel" disabled="disabled">
									</select>
								</td>
								<td>
									<span class="commonText">余额</span>
								</td>
								<td>
									<input type="text" readonly="readonly" id="ye" name="ye">
									<input type="hidden" id="hyseqId" name="hyseqId">
								</td>
							</tr>
							
							<tr>
								<td>
									<span class="commonText">备注</span>
								</td>
								<td colspan="3">
									<input class="remark" type="text" id="remark" name="remark">
								</td>
							</tr>
						</tbody>
					</table>
					<!--右侧付付款详情 -->
					<table class="tableLayout tableLayoutRight">
						<tbody id="payTable">
							<tr>
								<td>
									<span class="commonText">总金额</span>
								</td>
								<td>
									<input type="text"  class="orangeFont" id="totalcost" name="totalcost" readonly>
								</td>
								<td>
									<span class="commonText">免除金额</span>
								</td>
								<td>
									<input type="text"  class="orangeFont" id="voidmoney" name="voidmoney" readonly>
								</td>
								<td>
									<span class="commonText">应收金额</span>
								</td>
								<td>
									<input type="text"  class="orangeFont" id="shouldmoney" name="shouldmoney" readonly>
								</td>
							</tr>
							
							<tr>
								
								<td>
									<span class="commonText">缴费金额</span>
								</td>
								<td>
									<input type="text"  class="orangeFont" id="actualmoney" name="actualmoney" readonly>
								</td>
								<td>
									<span class="commonText">欠费金额</span>
								</td>
								<td>
									<input type="text"  class="orangeFont" id="arrearmoney" name="arrearmoney" readonly>
								</td>
								<td>
									<span class="commonText">打折金额</span>
								</td>
								<td>
									<input type="text"  class="orangeFont" id="discountmoney" name="discountmoney" readonly>
								</td>
							</tr>
							
							<tr id="FKFS">
								<td>
									<span class="commonText">付款方式</span>
								</td>
								<td colspan="3">
									<select onchange="YzYjj(this)" name="paytype" id="paytype1" >
									</select>		
									<input type="text" placeholder="付款金额" id="money1" name="money">
									<button type="button" class="btn btn-default btn-sm" id="addtype" onclick="addrow()">
								         <span class="glyphicon glyphicon-plus"></span>
								    </button>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</section>
			
			<footer class="fixedBottomDiv">
				<a href="javascript:void(0);" class="kqdsSearchBtn" id="qrsf">确认收费</a>
         		<a href="javascript:void(0);" class="kqdsCommonBtn" id="qx">取消</a>
			</footer>
		</div>
	</form>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var yjjId = ""; 
var costno = "<%=static_costno%>";/** 费用单主键  **/
var usercode = "<%=static_usercode%>";
var regno = "<%=static_regno%>"; /** 挂号主键 **/
var num = 1; /** 此值为 付款方式的行数  **/
var allhkxm = true; // 是否改费用单下全是还款项目
var onclickObj = "";
var cost_organization = null; /** 费用单的所属门诊，用于向打印页面传参 **/
var static_isyjjitem = 0; // 值为1时，表示预交金，值为2时表示 挂号费 /** 一个消费单只能有一个预交金，但是可以有多个挂号费 **/ 【如果是预交金项目，或者消费项目中包含 挂号费，则不需要选择护士或者医生！】
$(function() {
    $("#deltype").hide();
    initDictSelectByClass(); // 会员卡类别
    initFkfsSelect("paytype1"); //付款方式
    OrderDetail(); // 获取项目项目列表
    getAlldata();  // 获取费用单信息并显示
    getJbxx();     // 获取患者基本信息
    intMemberCardListByUserCodeSelect2("hyxxSelect", usercode); // 会员下拉框
    var canKaika = Yzkaika(usercode); // 验证患者会员卡是否存在未绑定的IC卡
	if(!canKaika){
		$(".highLightText").html('该患者存在会员卡未绑定IC卡，请先绑定');
	}
	getOrderDetailList(costno); /** 此方法要做的事情，可以通过OrderDetail()中添加代码实现，减少请求次数  **/
    getButtonPower();
});

// 判断该消费单中是否包含预交金项目或挂号费项目
function getOrderDetailList(costno) {
    var pageurl = contextPath + '/KQDS_CostOrder_DetailAct/selectNoPage.act?costno=' + costno;
    $.axse(pageurl, null,
    function(data) {
        for (var i = 0; i < data.length; i++) {
            var tabledata = data[i];
            if(tabledata.isyjjitem == 1){
            	static_isyjjitem = 1;
            	removeselect("paytype1");
            }else if(tabledata.isyjjitem == 2){
            	static_isyjjitem = 2;
            }
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}
// 如果存在预交金消费项目，则付款方式中移除预交金
function removeselect(id){
	$("#"+id+" option").each(function(){
		if($(this).html() == '<%=SysParaUtil.getFkfsNameByCostField(request, "payyb") %>' || 
		   $(this).html() == '<%=SysParaUtil.getFkfsNameByCostField(request, "payyjj") %>' || 
		   $(this).html() == '<%=SysParaUtil.getFkfsNameByCostField(request, "paydjq") %>' || 
		   $(this).html() == '<%=SysParaUtil.getFkfsNameByCostField(request, "payintegral") %>'){
				$(this).remove();
			}
	});
}
function getButtonPower() {
	var listbutton = getButtonListByPriv();
    var menubutton1 = "";
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "editPaymoney") {
            menubutton1 += '<a class="kqdsCommonBtn"  onclick="editPaymoney()">修改缴费金额</a>';
        }
    }
    $(".fixedBottomDiv").prepend(menubutton1);
}

//删除付款方式
function delrow(thisnum) {
    num = num - 1;
    $("#FKFS" + thisnum).remove();
    if ($("input[name='money']").length == 1) { /** 如果删除到只剩一个付款方式了，就显示 + 号按钮 **/ 
        $('#addtype').show();
    } else {
        //判断页面里是否还有加号 没有则在最后一个div里添加
        if ($("input[name='deltype']").length == 0) { /** 这行代码无效，因为页面中不存在input name = 'deltype' 的元素  **/
            var idnum = 0;
            $("input[name='money']").each(function() {
                var idstr = $(this).attr("id");
                idnum = Number(idstr.substring(5, idstr.length));
            });
            $('#FKFS' + idnum).find("button[name='addtype']").show();
        }
    }

    //自动计算
    automoneyj();
}

//自动计算钱数 + 
function automoneyz() {
    //实际应付
    var actmoney = $('#actualmoney').val();
    var ymoney = 0;
    var id = "";
    $("input[name='money']").each(function(index) {
        id = $(this).attr("id");
        ymoney = Number(ymoney) + Number($(this).val());
    });
    $("#" + id).val(Number(Number(actmoney) - Number(ymoney)).toFixed(2));
}
//自动计算钱数 -
function automoneyj() {
    //实际应付
    var actmoney = $('#actualmoney').val();
    var ymoney = 0;
    var ids = "";
    var len = $("input[name='money']").length;
    $("input[name='money']").each(function(index) {
        ids = ids + $(this).attr("id") + ",";
    });
    ids = ids.substring(0, ids.length - 1);
    if (len > 1) {
        var idss = ids.split(",");
        for (var i = 0; i < len; i++) {
            if (i == len - 1) {
                // $("#" + idss[i]).val(Number(actmoney) - ymoney);
                $("#" + idss[i]).val(Number(Number(actmoney) - ymoney).toFixed(2));
                break;
            } else {
                ymoney = ymoney + Number($("#" + idss[i]).val());
            }
        }
    } else {
        // $("#money1").val(actmoney);
    	$("#money1").val(Number(actmoney).toFixed(2));
    }
}

//添加付款方式
function addrow() {

    var idnum = 0;
    $("input[name='money']").each(function() {
        var idstr = $(this).attr("id");
        idnum = Number(idstr.substring(5, idstr.length));
    });
    if ($("input[name='money']").length == 1) {
        $('#addtype').hide();
        $('#deltype').show();
    } else {
        $('#addtype' + idnum).hide();
        $('#deltype' + idnum).show();
    }

    num = num + 1;
    var htmls = ' <tr id="FKFS' + (idnum + 1) + '"> ' 
    	+ ' <td><span class="commonText">付款方式</span></td> ' 
    	+ ' <td colspan="3">' 
    	+ ' <select class="dict" tig="FKFS" onchange="YzYjj(this)" name="paytype" id="' + 'paytype' + (idnum + 1) + '"></select> ' 
    	+ ' <input type="text" placeholder="付款金额" id="money' + (idnum + 1) + '" name="money"> ' 
    	+ ' <button type="button" class="btn btn-default btn-sm" class="addtype' + (idnum + 1) + '" name="addtype" id="addtype' + (idnum + 1) + '" onclick="addrow(' + (idnum + 1) + ')"> ' 
    	+ ' <span class="glyphicon glyphicon-plus"></span> '
    	+ ' </button> ' 
    	+ ' <button type="button" class="btn btn-default btn-sm" class="deltype' + (idnum + 1) + '" name="deltype" id="deltype' + (idnum + 1) + '" onclick="delrow(' + (idnum + 1) + ')"> ' 
    	+ ' <span class="glyphicon glyphicon-minus"></span> ' 
    	+ ' </button> ' 
    	+ ' </td> ' 
    	+ ' </tr> ';
    $('#payTable').append(htmls);

    //获取paytype1下拉内容 
    var selstr = $("#paytype1").html();
    $("#paytype" + (idnum + 1)).html(selstr);  /** 通过此方式，复制付款方式方式下拉框 **/

    //自动计算
    automoneyz();
}
function YzYjj(e) {
    if (yjjId == $(e).val()) { //如果选择预交金付款，先判断改患者是否有会员卡
        if (checkIsMemberByUsercode()) {
            layer.alert('请先办理会员卡'  );
            $(e).val("");
        }
    }
}
//验证患者是否已办理过会员卡
function checkIsMemberByUsercode() {
    var ff = false;
    var url = '<%=contextPath%>/KQDS_MemberAct/checkIsMemberByUsercode.act?usercode=' + usercode;
    $.axse(url, null,
    function(data) {
        if (data.data == 0) {
            ff = true;
        }
    },
    function() {});
    return ff;
}
function getAlldata() {
    var url = contextPath + '/KQDS_CostOrderAct/selectDetail.act?seqId=' + costno;
    $.axse(url, null,
    function(r) {
        if (r.retState == "0") {
            $("#costno").val(r.data.costno);
            $("#regno").val(r.data.regno);
            $("#totalcost").val(r.data.totalcost);
            $("#voidmoney").val(r.data.voidmoney);
            $("#shouldmoney").val(r.data.shouldmoney);
            $("#actualmoney").val(r.data.actualmoney);
            $("#arrearmoney").val(r.data.y2);
            $("#discountmoney").val(r.data.discountmoney);
            $("#doctor").val(r.data.doctor);
            $("#nurse").val(r.data.nurse);
            $("#techperson").val(r.data.techperson);
            if (r.data.doctor != undefined && r.data.doctor.trim() != "") { //如果开单选择医生，把医生带过来
                bindPerUserNameBySeqIdTB("doctorDesc", r.data.doctor);
            }

            if (r.data.nurse != undefined && r.data.nurse.trim() != "") {
                bindPerUserNameBySeqIdTB("nurseDesc", r.data.nurse);
            }

            if (r.data.techperson != undefined && r.data.techperson.trim() != "") {
                bindPerUserNameBySeqIdTB("techpersonDesc", r.data.techperson);
            }
        } else {
            layer.alert(r.retMsrg, {
                  
                end: function() {}
            });
        }
    },
    function() {
        layer.alert('请求失败' );
    });

}
function setOldDoctor(usercode) { /** 暂时废弃 **/
    var detailurl = '<%=contextPath%>/Kqds_PayCostAct/selectDoctor.act?usercode=' + usercode;
    $.axse(detailurl, null,
    function(data) {
        $("#doctor").val(data.data);
        if (data.data != undefined && data.data.trim() != "") {
            bindPerUserNameBySeqIdTB("doctorDesc", data.data);
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
}
function OrderDetail() {

    var detailurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/NoselectPage.act?costno=' + costno;
    $("#table").bootstrapTable({
        url: detailurl,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
            //如果全是还款 项目  默认之前的医生
            /*  if(allhkxm){
				 setOldDoctor(onclickrowOobj2.usercode);
			 } */
		    $('#askperson').val(data[0].askperson);
		    bindPerUserNameBySeqIdTB("askpersonDesc", data[0].askperson);
        },
        columns: [{
            title: '医生',
            field: 'doctorname',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        /* {title: '项目编号',field: 'itemno',align: 'center',valign: 'middle', sortable: true}, */
        {
            title: '治疗项目',
            field: 'itemname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                var html = '<span style="text-align:left;width:140px;"  title="' + value + '">';
                if (row.istk == 1) {
                    html += '<span class="label label-inverse" style="position:relative;top:2px;font-size:11px;">退款</span>';
                } else {
                    if (row.y2 < 0) {
                        html += '<span class="label label-warning" style="position:relative;color:#fff;background:#F0AD4E;font-size:11px;">还款</span>';
                    } else if (row.y2 > 0) {
                        html += '<span class="label label-danger" style="position:relative;background:tomato;color:#fff;border-radius:4px;font-size:11px;">欠款</span>';
                    } else if (Number(row.y2) == 0 && row.isqfreal == 1) {
                        html += '<span class="label label-warning" style="position:relative;color:#fff;background:#F0AD4E;font-size:11px;">还款</span>';
                    }
                }
                html += value + '</span>';
                return html;
            }
        },
        {
            title: '单位',
            field: 'unit',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '单价',
            field: 'unitprice',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">￥' + value + '</span>';
            }
        },
        {
            title: '数量',
            field: 'num',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '折扣%',
            field: 'discount',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '小计',
            field: 'subtotal',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">￥' + value + '</span>';
            }
        },
        {
            title: '免除',
            field: 'voidmoney',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                return '<span class="money">￥' + value + '</span>';
            }
        },
        {
            title: '应收金额',
            field: 'voidmoney',
            align: 'center',
            valign: 'middle',
            formatter:function(value,row,index){
				var ys = Number(row.paymoney)+Number(row.arrearmoney);
				if(Number(row.y2)<= 0  &&  row.qfbh!="" ){//还款
					ys = "0.00";
				}
				return '<span class="money">￥'+ys+'</span>' ;
			}
        },
        {
            title: '欠费金额',
            field: 'arrearmoney',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                return '<span class="money">￥' + value + '</span>';
            }
        },
        {
            title: '缴费金额',
            field: 'paymoney',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                return '<span class="money">￥' + value + '</span>';
            }
        },
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickObj = $('#table').bootstrapTable('getData')[index];
    });
    
    return; /*************** 该页面目前不提供打印功能，不执行以下方法获取打印表格  **/
    
    $("#tabledayin").bootstrapTable({
        url: detailurl,
        dataType: "json",
        cache: false,
        striped: true,
        columns: [{
            title: '治疗项目',
            field: 'itemname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span style="float:left; text-align:left;" class="time">' + value + '</span>';
            }
        },
        {
            title: '单位',
            field: 'unit',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '单价',
            field: 'unitprice',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span style="float:right; text-align:right;">￥' + value + '</span>';
            }
        },
        {
            title: '数量',
            field: 'num',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '折扣%',
            field: 'discount',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '小计',
            field: 'subtotal',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span style="float:right; text-align:right;">￥' + value + '</span>';
            }
        },
        {
            title: '欠费金额',
            field: 'arrearmoney',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span style="float:right; text-align:right;">￥' + value + '</span>';
            }
        },
        {
            title: '缴费金额',
            field: 'paymoney',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span style="float:right; text-align:right;">￥' + value + '</span>';
            }
        },
        {
            title: '免除',
            field: 'voidmoney',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                return '<span style="float:right; text-align:right;">￥' + value + '</span>';
            }
        }]
    });
}
//基本信息
function getJbxx() {
    var returnData = getOneByUsercode(usercode);
    if (returnData) {
        var data = returnData.data[0];
        $('#usercode').val(data.usercode);
        $('#username').val(data.username);
        $('#age').val(data.age);
        $('#integral').val(data.integral);
    }
}
$('#hyxxSelect').on('change',
function() {
    var seqIdHy = $('#hyxxSelect').val();
    var detailurl = '<%=contextPath%>/KQDS_MemberAct/selectDetail.act?seqId=' + seqIdHy;
    $.axse(detailurl, null,
    function(data) {
        if (data.retState == "0") {
            var obj = data.data;
            var ye = Number(obj.money) + Number(obj.givemoney);
            $('#ye').val(Number(ye).toFixed(2));
            $('#hyseqId').val(obj.seqId);
            //getjf(obj.memberno);
            $('#memberlevel').val(obj.memberlevel);
        }
    },
    function() {
        layer.alert('删除失败！' );
    });
});
$('#qx').on('click', // 取消
function() {
    var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(frameindex); //再执行关闭 */
});

//生成医保文件
$('#scybwj').on('click',  /** 废弃代码 **/ 
function() {
    layer.alert('暂无'  );
});
function Isreg() {  /** 该方法废弃  **/
    var costorderObj = getCostOrderObjBySeqId(costno);
    return costorderObj.isreg;
}
//修改缴费金额
function editPaymoney() {
	if(static_isyjjitem == 1){
        layer.alert('预交金项目，不能修改缴费金额！' );
        return false;
	}
    if (onclickObj == "") {
        layer.alert('请选择要修改缴费金额的项目！' );
        return false;
    }
    var msg = "确定修改【" + onclickObj.itemname.substring(0, 6) + "...】的缴费金额？ ";
    layer.confirm(msg, {
        btn: ['确认', '放弃'] //按钮
    },
    function() {
        layer.open({
            type: 2,
            title: '修改【' + onclickObj.itemname.substring(0, 6) + '...】的缴费金额',
            maxmin: true,
            shadeClose: true,
            //点击遮罩关闭层
            area: ['520px', '300px'],
            content: contextPath + '/Kqds_PayCostAct/toCost_EditPaymoney.act'
        });
    });
}

/**
 * 该方法供修改金额子页面调用
 * @paymoney 缴费金额
 * @result 修改原因
 */
function edithz(paymoney, result) {
    var arrearmoney;
    if (onclickObj.qfbh) {
        arrearmoney = Number(Number(onclickObj.paymoney) + Number(onclickObj.arrearmoney) - Number(paymoney)).toFixed(2);
    } else {
        arrearmoney = Number(Number(onclickObj.subtotal) - Number(onclickObj.voidmoney) - Number(paymoney)).toFixed(2);
    }
    //缴费费用>小计-免除
    if (Number(arrearmoney) < 0) {
        layer.alert('缴费金额过多' );

        return false;
    }
    var paramOrder = {
        costno: onclickObj.costno,
        detailid: onclickObj.seqId,
        oldpaymoney: onclickObj.paymoney,
        paymoney: paymoney,
        arrearmoney: arrearmoney,
        remark: result
    }
    var url = contextPath + '/KQDS_CostOrderAct/editPaymoney.act';
    $.axse(url, paramOrder,
    function(r) {
        if (r.retState == "0") {
            layer.alert('操作成功', {
                  
                end: function() {
                    var detailurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/NoselectPage.act?costno=' + costno;
                    $("#table").bootstrapTable('refresh', {
                        'url': detailurl
                    });
                    getAlldata();
                }
            });
        } else {
            layer.alert(r.retMsrg, {
                  
                end: function() {}
            });
        }
    },
    function() {
        layer.alert('请求失败' );
    });
}

//确认缴费
$('#qrsf').on('click',
function() {
    //请求后台 验证改单的状态
    var flag = true;
    var costorderObj = getCostOrderObjBySeqId(costno);
    if (costorderObj.status == "2") {
        flag = false;
        layer.alert('该费用单已结账', {
              
            end: function() {
                var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                parent.layer.close(frameindex); //再执行关闭 
            }
        });
    }
    if (!flag) {
        return false;
    }
    var list = $('#table').bootstrapTable('getData');
    //验证该费用单是否正在重新开单
    var actmoney = $('#actualmoney').val();
    var doctor = $('#doctor').val();
    // 如果是预交金项目，或者消费项目中包含 挂号费，则不需要选择护士或者医生
    if(static_isyjjitem != 1 && static_isyjjitem !=2){
    	 // if (Isreg() == 0) {   /** 这个方法没什么用 **/
    	        if (doctor == "") {
    	            layer.alert('选择医生', {
    	                  
    	            });
    	            return false;
    	        }
    	        /* if (nurse == "") {
    	            layer.alert('请选择护士', {
    	                  
    	            });
    	            return false;
    	        } */
    	// }
	}
    var yjjmoney = 0; // 预交金金额
    var summoney = 0; // 总金额 (与实收金额做对比用)
    var types = "";   // 类型数组，存储付款方式对应sys_dict表的主键
    var moneys = "";  // 存放每个缴费方式对应的缴费金额
    var testz = /^\d+(\.\d+)?$/;
    var typestrs = ""; // 支付类型 汉字集合
    var type = $("#paytype1").val();
    var money = $("#money1").val();
    var typestr = $("#paytype1 option:selected").text();
    var membertype = $('#hyxxSelect option:selected').val(); // 对应会员卡表的主键 
    var ye = $("#ye").val(); // 选中会员卡对应的余额
    if (type == "") {
        layer.alert('请选择付款方式' );
        return false;
    } else if (type == yjjId && membertype == "") {
        layer.alert('使用预交金付款时请先选择一个会员卡' );
        return false;
    } else if (type == yjjId && Number(money) > Number(ye)) {
        layer.alert('会员卡余额不足' );
        return false;
    }
    if (money == "") {
        layer.alert('请填写金额' );
        return false;
    } else if (!testz.test(money)) {
        layer.alert('金额格式不正确' );
        return false;
    }

    if (type == yjjId) { // 如果第一个付款方式是预交金
        yjjmoney = money;
    }

    types = types + type + ",";
    typestrs = typestrs + typestr + ",";
    moneys = moneys + money + ",";
    summoney = parseFloat(money);

    var FKFSlength = $("input[name='money']").length;
    if (FKFSlength > 1) {
        var idnums2 = "";
        $("input[name='money']").each(function() {
            var idstr = $(this).attr("id");
            if (idstr == "money1") {
                return true; /**  return false时相当于break, 如果return true 就相当于continure。  **/
            }
            var idnum = Number(idstr.substring(5, idstr.length));
            idnums2 = idnums2 + idnum + ",";
        });

        //多种付款方式时循环获取
        var idnums22 = idnums2.split(",");
        for (var i = 0; i < idnums22.length; i++) {
            if (idnums22[i] == "") {
                continue;
            }
            var typei = $("#paytype" + idnums22[i]).val();
            var moneyi = $("#money" + idnums22[i]).val();
            var typestri = $("#paytype" + idnums22[i] + " option:selected").text();

            var reg = new RegExp("^.*" + typei + ".*$");
            if (typei == "") {
                layer.alert('请选择付款方式', {
                      
                });
                return false;
            } else if (validateRepeatFKFS(typei, types)) {
                layer.alert('请选择不同的付款方式', {
                      
                });
                return false;
            } else if (typei == yjjId && membertype == "") {
                layer.alert('使用预交金付款时请先选择一个会员卡', {
                      
                });
                return false;
            } else if (typei == yjjId && Number(money) > Number(ye)) {
                layer.alert('会员卡余额不足', {
                      
                });
                return false;
            }

            if (moneyi == "") {
                layer.alert('请填写金额', {
                      
                });
                return false;
            } else if (!testz.test(moneyi)) {
                layer.alert('金额格式不正确', {
                      
                });
                return false;
            }

            if (typei == yjjId) {
                yjjmoney = moneyi;
            }

            types = types + typei + ",";
            typestrs = typestrs + typestri + ",";
            moneys = moneys + moneyi + ",";
            summoney = Number(summoney) + Number(moneyi);
        }
    }

    //验证实收金额是否和付款金额相同
    if (Number(summoney).toFixed(2) != Number(actmoney).toFixed(2)) {
        layer.alert('实收金额与付款金额不相符，请重新填写付款金额' );
        return false;
    }
    //含预交金付款 输入密码
    if(types.indexOf(yjjId)>=0 && <%=HYK_NO_PASSWORD%> == '1'){
    	YzYjjPass(yjjmoney,moneys,types,typestrs);
    }else{
    	submitPay(yjjmoney,moneys,types,typestrs);
    }
});

// 验证付款方式是否重复，重复时返回ture
function validateRepeatFKFS(currFkfs, fkfsArrayStr) {
    if (!currFkfs) {
        return false;
    }

    if (!fkfsArrayStr) {
        return false;
    }

    var fkfsArray = fkfsArrayStr.split(",");

    if (fkfsArray.length == 0) {
        return false;
    }

    var flag = false;
    for (var i = 0; i < fkfsArray.length; i++) {

        if (!fkfsArray[i]) {
            continue;
        }

        if (currFkfs == fkfsArray[i]) {
            flag = true;
            break;
        }
    }
    return flag;
}

function submitPay(yjjmoney,moneys,types,typestrs){
	 layer.confirm('确定进行收费？', {
	        btn: ['确认', '放弃'] //按钮
	    },
	    function() {
	        layer.closeAll('dialog');
	        var memberid = $('#hyseqId').val();
	        var param = $('#form1').serialize();
	        var url = '<%=contextPath%>/Kqds_PayCostAct/insertOrUpdate.act?' + param + '&moneys=' + moneys + '&types=' + types + '&member=' + memberid + '&typestrs=' + typestrs+'&userode='+onclickObj.usercode+'&username='+onclickObj.username+'&access=1';
	        var msg = layer.msg('处理中', {
	            icon: 16
	        });
	        $.axse(url, null,
	        function(r) {
	            if (r.retState == "0") {
	                layer.alert('收费成功', {
	                      
	                    end: function() {
	                      /*   if (yjjmoney > 0) {
	                            yjjfk(yjjmoney);
	                        }
	                        userSsje(); //重新计算患者实收金额 */
	                        //跳转打印页面
	                        parent.layer.open({
	                            type: 2,
	                            title: '补打单据',
	                            shadeClose: false,
	                            shade: 0.6,
	                            area: ['960px', '90%'],
	                            content: contextPath + '/Kqds_PayCostAct/toCostListingPrint.act?costno=' + costno,
	                            cancel: function() {
	                                parent.window.location.reload();
	                            },
	                        });

	                    }
	                });
	                layer.close(msg);
	            } else {
	                if (r.retMsrg != null && r.retMsrg != "") {
	                    layer.alert(r.retMsrg, {
	                          
	                    });
	                } else {
	                    layer.alert('收费失败', {
	                          
	                    });
	                }

	                layer.close(msg);
	            }
	        },
	        function() {
	            layer.alert('收费失败', {
	                  
	            });
	        });
	    });
}
/**
 * 预交金支付，开启输入密码功能时，通过此方法验证输入密码是否正确
 */
function YzYjjPass(yjjmoney,moneys,types,typestrs){
	var hyseqId = $('#hyseqId').val();
	layer.prompt({
        title: '输入密码，并确认',
        formType: 0
    },
    function(pass, index) {
        layer.close(index);
        var param = {
       		password: pass,
            seqId: hyseqId
        };
        var url = contextPath + '/KQDS_MemberAct/checkPassword.act';
        $.axse(url, param,
        function(r) {
        	if(r.valid){
        		submitPay(yjjmoney,moneys,types,typestrs);
        	}else{
        		layer.alert('密码错误', {
                      
                });
        	}
        },
        function() {
            layer.alert(r.retMsrg  );
        });
    });
}
<%-- function yjjfk(yjjmoney) {
    var hyseqId = $('#hyseqId').val();
    var regno1 = $("#regno").val();
    var param = 'seqId=' + hyseqId + '&money=' + yjjmoney + '&usercode=' + usercode + '&regno=' + regno1 + '&costno=' + costno;
    var url = '<%=contextPath%>/KQDS_MemberAct/yjjkk.act?' + param;
    $.axse(url, null,
    function(r) {
        if (r.retState != "0") {
            layer.alert('预交金扣款失败', {
                  
                end: function() {}
            });
        }
    },
    function() {
        layer.alert('预交金扣款失败', {
              
            end: function() {}
        });
    });
} --%>
<%-- function userSsje() {
    var detailurl = '<%=contextPath%>/KQDS_UserDocumentAct/getSsje.act?usercode=' + usercode;
    $.axseY(detailurl, null,
    function(data) {},
    function() {});
} --%>
function setyjj() {
	if(static_isyjjitem == 1){
        layer.alert('预交金项目，不能使用预交金结账！' );
        return false;
	}
    var hyseqId = $('#hyseqId').val();
    var ye = $("#ye").val();
    if (hyseqId == null || hyseqId == "") {
        layer.alert('请选择会员卡' );
        return false;
    }
    if (Number(ye) <= 0) {
        layer.alert('暂无预交金' );
        return false;
    }
    $("#paytype1").val(yjjId); //选择使用预交金付款时  默认选中下拉中的预交金选项
    var actualmoney = $("#actualmoney").val();
    if (Number(ye) > Number(actualmoney)) {
        $("#money1").val(actualmoney);
    } else {
        $("#money1").val(ye);
        $("#addtype").click();
    }
}

function intoreal() {
    var url = '<%=contextPath%>/KQDS_CostOrderAct/intoreal.act';
    $.axse(url, null,
    function(r) {
        if (r.retState == "0") {

} else {
            layer.alert('积分失败'  );
        }
    },
    function() {
        layer.alert('修改失败' );
    });
}

//费用明细单
function printfymxd() {
    var ymoney = Number($("#shouldmoney").val());
    //弹出打印页面
    openDayin('费用明细单', ymoney);
}
//费用确认单
function printfyqrd() {
    var ymoney = Number($("#shouldmoney").val());
    //弹出打印页面
    openDayin('费用确认单', ymoney);
}

function openDayin(titles, ymoney) {
    //根据costno查询 开单时间
    var costorderObj = getCostOrderObjBySeqId(costno);
    var kdtime = costorderObj.createtime;
    var usercodeLocal = costorderObj.usercode;
    cost_organization = costorderObj.organization; // 开单门诊
    // 先查询fyqrd页面在打印设置里 是a4还是a5，如果是a4则跳转a4界面
    var printSet = getPrintType("费用确认单");
    var costurl = contextPath + '/KQDS_Print_SetAct/toFyqrdPrintPage.act?printpage='+printSet.printurl+'&printType=' + printSet.printtype + '&titles=' + titles + '&actualmoney=' + $('#actualmoney').val() + '&arrearmoney=' + $('#arrearmoney').val() + '&ymoney=' + ymoney + '&costno=' + costno + '&usercode=' + usercode + '&kdtime=' + kdtime + '&usercode=' + usercodeLocal;
    if (cost_organization) {
        costurl += '&cost_organization=' + cost_organization;
    }
    // 弹出打印页面
    parent.layer.open({
        type: 2,
        title: '打印',
        shadeClose: true,
        //点击遮罩关闭层
        area: ['1000px', '550px'],
        content: costurl
    });
}
</script>
</html>