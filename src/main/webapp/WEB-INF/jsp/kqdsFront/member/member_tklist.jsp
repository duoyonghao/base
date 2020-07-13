<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}
  %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>退款列表</title><!-- 接待中心  下方退款按钮进入  -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css" /> --%>
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/member/member.css"/>
<style>
	.clear2{
		background:none;
		margin:0;
	}
	.position-bottom{
		border-top:1px solid #117CCA;	
		margin-top:5px;
		padding-top:10px;
		position:static;
		padding-bottom: 0;
	}
	body{
		padding:5px;
	}
	.jiuzhen_register-content{
		/* padding-bottom:0; */
		margin:0;
		width:100%;
		padding:0;
		
	}
	.filterWrap{
		position:relative;
		overflow:hidden;
	}
	.filterWrap{
		padding-bottom:8px;
		border-bottom:1px solid rgb(14, 124, 199);
	}
	.tableLayout{
		margin:0;
		float:left;
	}
	.tableLayout input[type=text],.tableLayout select{
		width:110px;
	}
	.tableLayout tr{
		height:30px;
	}
	.table-title{
		margin:0;
		font-size:15px;
		color:#00A6C0;
		height:26px;
		line-height:26px;
		
	}
	.position-bottom{
		text-align:center;
	}
	.btnGroup{
		float:right;
	}
	 .aBtn{
		padding:0 15px;
		margin:0 5px;
	}
	.fixed-table-container thead th .sortable{
		padding-right:8px;
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
</style>
</head>
<body>
	<div class="jiuzhen_register-content">
		<div class="filterWrap">
			<table class="tableLayout">
				<tr>
					<td>
						<span class="commonText" style="margin-left:10px;">所属门诊</span>
					</td>
					<td>
						<select id="organization" name="organization"></select>
					</td>
					<td>
						<span class="commonText" style="margin-left:10px;">模糊查询</span>
					</td>
					<td>
						<input type="text" id="queryinput" name="queryinput" value>
					</td>
					<td>
						<span class="commonText">退款状态</span>
					</td>
					<td>
						<select id="sqstatus">
							<option value="">请选择</option>
							<option value="1">申请中</option>
							<option value="2">已同意</option>
							<option value="3">未同意</option>
							<option value="4">已退款</option>
						</select>
					</td>
					<td>
						<span class="commonText">申请时间</span>
					</td>
					<td class="unitsDate">
						<input style="cursor:pointer;" type="text"  placeholder="开始日期" id="starttime">
					</td>
					<td>
						<span style="text-align:center;" class="commonText">到</span>
					</td>
					<td class="unitsDate">
						<input style="cursor:pointer;" type="text"  placeholder="结束日期" id="endtime">
					</td>
				</tr>
				
				
			</table>
			<div class="btnGroup">
				<a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
				<a href="javascript:void(0);" id="exportTable" class="kqdsCommonBtn" onclick="exportTable();">生成报表</a>
				<a id="searchHzda" href="javascript:void(0);" class="kqdsSearchBtn">查 询</a>
			</div>
		</div>
		 <h1	class="table-title">退款清单</h1>
		 <div class="tableBox">
		    <table id="table" class="table-striped table-condensed table-bordered" data-height="300"></table>
         </div>
         <div class="position-bottom" id="bottomBarDdiv">
         	<div class="clear2"></div>
         </div>
	</div> 
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>

<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var pageurl = '<%=contextPath%>/KQDS_Member_Record_ShAct/selectListByType.act';
var onclickrowOobj = "";
$(function() {
	initHosSelectList4Front('organization'); // 连锁门诊下拉框
    //当前日期
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    getButtonPower();
    getlist();
    //时间选择
    $(".unitsDate input").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "buttom-right"
    });
});
$('#searchHzda').on('click',
function() {
	$(this).attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
	$(this).text("查询中");
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
});
$('#clean').on('click',
function() {
    $(".filterWrap :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        sqstatus:$('#sqstatus').val(),
        organization: $('#organization').val(),
        queryinput: $("#queryInput").val()
    };
    return temp;
}
function getlist() {
    $("#table").bootstrapTable({
    	 url: pageurl,
         dataType: "json",
         cache: false,
         striped: true,
         queryParams: queryParams,
         onLoadSuccess:function(data){
			 //解除查询按钮禁用 lutian
			 if(data){
				 $("#searchHzda").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","auto").css("pointer-events","auto");
				 $("#searchHzda").text("查询");
			 }
        	 setHeight();
        	 //付款方式赋值
 	         getFkfsField();
         },
         columns: [
         {
             title: '患者编号',
             field: 'usercode',
             align: 'center',
             sortable: true,
             editable: true,
             formatter: function(value, row, index) {
                 return '<span title="' + value + '">' + value + '</span>';
             }
         },
         {
             title: '姓名',
             field: 'username',
             align: 'center',
             sortable: true,
             formatter: function(value, row, index) {
                 return '<span class="name" title="' + value + '">' + value + '</span>';
             }
         },
         {
             title: '退款状态',
             field: 'sqstatus',
             align: 'center',
             sortable: true,
             formatter: function(value, row, index) {
                 var html = "";
                 if (value == 1) {
                     html = '<span style="color:red">申请中</span>';
                 } else if (value == 2) {
                     html = '<span style="color:red">已同意</span>';
                 } else if (value == 3) {
                     html = '<span style="color:red">未同意</span>';
                 } else if (value == 4) {
                     html = '<span style="color:green">已退款</span>';
                 }
                 return html;
             }
         },
         {
             title: '审核备注',
             field: 'sqremark',
             align: 'center',
             sortable: true,
             formatter: function(value, row, index) {
                 return '<span class="remark" title="' + value + '">' + value + '</span>';
             }
         },
         {
             title: '咨询',
             field: 'askperson',
             align: 'center',
             sortable: true,
             formatter: function(value, row, index) {
                 return '<span class="name">' + value + '</span>';
             }
         },
         {
        	 title: '患者来源',
        	 field: 'devchannel',
        	 align: 'center',
        	 sortable: true,
 			formatter:function(value,row,index){  
                 return '<span class="source">' + value + '</span>';
         	}
 		},
 		{title: '来源子分类',field: 'nexttype',align: 'center',sortable: true,
 			formatter:function(value,row,index){  
                 return '<span class="source">' + value + '</span>';
         	}
 		},
         {
             title: '卡号',
             field: 'cardno',
             align: 'center',
             
             sortable: true,
             formatter: function(value, row, index) {
                 return '<span title="' + value + '">' + value + '</span>';
             }
         },
         {
             title: '建档人',
             field: 'jdrname',
             align: 'center',
             
             sortable: true,
             formatter: function(value, row, index) {
                 return '<span title="' + value + '">' + value + '</span>';
             }
         },
         {
             title: '退费-充值',
             field: 'cmoney',
             align: 'center',
             
             sortable: true,
             formatter: function(value, row, index) {
                 return '<span class="money" style="color:red">' + value + '</span>';
             }
         },
         {
             title: '退费-赠送',
             field: 'cgivemoney',
             align: 'center',
             
             sortable: true,
             formatter: function(value, row, index) {
                 return '<span class="money" style="color:red">' + value + '</span>';
             }
         },
         {
             title: '退费小计',
             field: 'ctotal',
             align: 'center',
             
             sortable: true,
             formatter: function(value, row, index) {
                 return '<span class="money" style="color:red">' + value + '</span>';
             }
         },
         {
             title: '现金退费',
             field: 'xjmoney',
             align: 'center',
             
             sortable: true,
             formatter: function(value, row, index) {
                 return '<span class="money">'+value+'</span>';
             }
         },
         {
             title: '支付宝退费',
             field: 'zfbmoney',
             align: 'center',
             
             sortable: true,
             formatter: function(value, row, index) {
                 return '<span class="money">'+value+'</span>';
             }
         },
         {
             title: '微信退费',
             field: 'wxmoney',
             align: 'center',
             
             sortable: true,
             formatter: function(value, row, index) {
                 return '<span class="money">'+value+'</span>';
             }
         },
         {
             title: '银行卡退费',
             field: 'yhkmoney',
             align: 'center',
             
             sortable: true,
             formatter: function(value, row, index) {
                 return '<span class="money">'+value+'</span>';
             }
         },
         {
        	 title: '么么贷退费',
        	 field: 'mmdmoney',
        	 align: 'center',
        	 
        	 sortable: true,
             formatter: function(value, row, index) {
                 return '<span class="money">'+value+'</span>';
             }
       	},
       	{
       		title: '百度分期退费',
       		field: 'bdfqmoney',
       		align: 'center',
       		
       		sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">'+value+'</span>';
            }
       	},
         {
             title: '其他退费',
             field: 'qtmoney',
             align: 'center',
             
             sortable: true,
             formatter: function(value, row, index) {
                 return '<span class="money">'+value+'</span>';
             }
         },
         {
             title: '申请时间',
             field: 'createtime',
             align: 'center',
             sortable: true,
             formatter: function(value, row, index) {
                 return '<span title="' + value + '">' + value + '</span>';
             }
         },
         {
             title: '退费时间',
             field: 'type',
             align: 'center',
             
             sortable: true,
             formatter: function(value, row, index) {
                 return '<span title="' + value + '">' + value + '</span>';
             }
         },
         {
             title: '退费原因',
             field: 'tfremark',
             align: 'center',
             sortable: true,
             formatter: function(value, row, index) {
                 return '<span class="remark" title="' + value + '">' + value + '</span>';
             }
         },
         {
             title: '申请人',
             field: 'createuser',
             align: 'center',
             sortable: true,
             formatter: function(value, row, index) {
                 return '<span class="name">'+value+'</span>';
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
function refresh() {
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
function sqtk() {
    layer.open({
        type: 2,
        title: '申请退款',
        shadeClose: false,
        //点击遮罩关闭层
        area: ['90%', '90%'],
        content: '<%=contextPath%>/KQDS_MemberAct/toMemberTkSq.act'
    });
}
function Yztuikuan(memberno,tkgivemoney,tkmoney){
	var cantuikuan = false;
    var url = '<%=contextPath%>/KQDS_Member_Record_ShAct/Yztuikuan.act?memberno=' +memberno+'&tkgivemoney='+tkgivemoney+'&tkmoney='+tkmoney;
    $.axse(url, null,
    function(data) {
   	 if(data.retState=="0"){
   		cantuikuan = data.data;
   	 }else{
   		 layer.alert(data.retMsrg  );
   	 }
    },
    function() {
       layer.alert(data.retMsrg, {
             
       });
    });
    return cantuikuan;
}
function sqsp(status) {
    if (onclickrowOobj.sqstatus != 1) {
        layer.alert('请选择申请中的退款记录！' );
        return false;
    }
    var remark = "";
    if (status == 3) { //不同意
        layer.prompt({
            title: '不同意原因',
            formType: 0
        },
        function(pass, index) {
            layer.close(index);
            remark = pass;
            sh(status, remark);
        });
    } else {
    	//同意退款申请先验证金额
    	var canTuikuan = Yztuikuan(onclickrowOobj.cardno,onclickrowOobj.cgivemoney,onclickrowOobj.cmoney);
   	    if(!canTuikuan){
   	    	 return false;
   	    }
        sh(status, remark);
    }

}
function sh(status, remark) {
    var param = {
        seqId: onclickrowOobj.seqId,
        sqstatus: status,
        sqremark: remark
    };
    var pageurl = '<%=contextPath%>/KQDS_Member_Record_ShAct/editState.act';
    $.axse(pageurl, param,
    function(r) {
        if (r.retState == "0") {
            layer.alert('审核成功'  );
            refresh();
        } else {
            layer.alert('审核失败'  );
        }
    },
    function() {
        layer.alert('审核失败' );
    });
}
function qrtk(status) {
    if (onclickrowOobj.sqstatus != 2) {
        layer.alert('请选择已同意的退款记录！' );
        return false;
    }
    var url = '<%=contextPath%>/KQDS_Member_Record_ShAct/confirmRefund.act?seqId='+onclickrowOobj.seqId;
    var msg;
    $.axseSubmit(url,null, 
 	function(){
		 msg=layer.msg('加载中', {icon: 16});
  		  },
          function(r){
  			  if(r.retState=="0"){
			       	layer.alert('退费成功', {  end:function(){
			       			refresh();
			       	}});
		       }else{
		       	    layer.alert(r.retMsrg );
		       }     
          },
          function(){
        	  layer.alert('退费失败' );
      	  }
    );
}
function getButtonPower() {
	var listbutton = getButtonListByPriv();
    var menubutton1 = "";
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "tksq") {
            menubutton1 += '<a class="kqdsCommonBtn"  onclick="sqtk()">申请退款</a>';
        } else if (listbutton[i].qxName == "tytk") {
            menubutton1 += '<a class="kqdsCommonBtn"  onclick="sqsp(2)">同意申请</a>';
        } else if (listbutton[i].qxName == "jjtk") {
            menubutton1 += '<a class="kqdsCommonBtn"  onclick="sqsp(3)">拒绝申请</a>';
        } else if (listbutton[i].qxName == "qrtk") {
            menubutton1 += '<a class="kqdsSearchBtn"  onclick="qrtk(4)">确认退款</a>';
        }
    }
    
    menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" id="dayin" onclick="localPrint()">打印</a>';
    $("#bottomBarDdiv").append(menubutton1);
}


//补打充值单
function localPrint() {
    if (onclickrowOobj == null || onclickrowOobj == "") {
        layer.alert('请选择退费记录', {
            
        });
        return;
    }

    //先查询fyqrd页面在打印设置里 是a4还是a5，如果是a4则跳转a4界面
    var printSet = getPrintType("会员卡退费确认单");

    // 默认A5打印
    var hyurl = contextPath + '/KQDS_Print_SetAct/toHyczPrintPage.act?printpage='+printSet.printurl+'&printType=' + printSet.printtype + '&cztime=' + onclickrowOobj.type + '&memberno=' + onclickrowOobj.cardno + '&username=' + onclickrowOobj.username + '&money=' + onclickrowOobj.cmoney + '&givemoney=' + onclickrowOobj.cgivemoney + '&totalmoney=' + onclickrowOobj.ctotal + '&ymoney=' + onclickrowOobj.ymoney + '&recordId=' + onclickrowOobj.seqId + '&usercode=' + onclickrowOobj.usercode + '&tksh=1' + '&ygivemoney=' + onclickrowOobj.ygivemoney + '&ytotalmoney=' + onclickrowOobj.ytotal + '&cost_organization=' + onclickrowOobj.organization;

    // 弹出打印页面
    parent.layer.open({
        type: 2,
        title: '打印',
        shadeClose: true,
        //点击遮罩关闭层
        area: ['1000px', '550px'],
        content: hyurl
    });
}
function setHeight(){
	$("#table").bootstrapTable("resetView",{
		height:$(window).outerHeight()-$(".filterWrap").outerHeight()-$("#bottomBarDdiv").outerHeight()-50
	});
}

//点击导出
function exportTable() {
    var fieldArr = [];
    var fieldnameArr = [];
    $('#table thead tr th').each(function() {
        var field = $(this).attr("data-field");
        if (field != "") {
            fieldArr.push(field); //获取字段
            fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
        }
    });
    var param = JsontoUrldata(queryParams());
    location.href = pageurl + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
}
</script>
</html>
