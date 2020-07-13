<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuid");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>会员卡-开卡查询</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/member/methodModify.js"></script>
</head>
<body>
<div id="container">
   <!--查询条件-->
     <div class="searchWrap">
            <div class="cornerBox">查询条件</div>
            <div class="btnBar">
            	<a href="javascript:void(0);" class="kqdsCommonBtn" id="dayin">打印</a>
                <a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
                <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
            </div>
            <div class="formBox">
               	<div class="kv">
              		<div class="kv">
	                    <label>所属门诊</label>
	                    <div class="kv-v">
	                        <select id="organization" name="organization"></select>
	                    </div>
	                </div>
               		<div class="kv" style="width:300px;">
						<label>开卡时间</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" /><span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</span>
						</div>
					</div>
		            <div class="kv">
	                    <label></label>
	                    <div class="kv-v">
	                        <input type="text" id="queryInput" class="searchInput" placeholder="请输入用户编号/姓名/会员卡号" style="width:200px;">
	                        <span class="orangeFont">*可模糊查询</span>
	                    </div>
                	</div>
                </div>
            </div>
        </div> 
    <div class="tableBox">
    	<table id="table" class="table-striped table-condensed table-bordered" data-height="250"></table>
    </div>
    <div class="" align="center">
    	<table style="width:90%;height:30px; text-align: center;"> 
       		<tr>
      			<td style="width:28%" colspan="2"><span style="color:blue;">开卡-充值小计:<lable id="tdmoney">0</lable></span></td>
      			<td style="width:28%" colspan="2"><span style="color:blue;">开卡-赠送小计:<lable id="tdgivemoney">0</lable></span></td>
      			<td style="width:44%" colspan="3"><span style="color:blue;">开卡小计:<lable id="tdtotal">0</lable></span></td>
       		</tr> 
       		<tr>
      			<td><span style="color:#00A6C0;"><%=SysParaUtil.getFkfsNameByMemberField(request, "xjmoney") %>充值小计:<lable id="tdxj">0</lable></span></td>
      			<td><span style="color:#00A6C0;"><%=SysParaUtil.getFkfsNameByMemberField(request, "zfbmoney") %>充值小计:<lable id="tdzfb">0</lable></span></td>
      			<td><span style="color:#00A6C0;"><%=SysParaUtil.getFkfsNameByMemberField(request, "wxmoney") %>充值小计:<lable id="tdwx">0</lable></span></td>
      			<td><span style="color:#00A6C0;"><%=SysParaUtil.getFkfsNameByMemberField(request, "yhkmoney") %>充值小计:<lable id="tdyhk">0</lable></span></td>
      			<td><span style="color:#00A6C0;"><%=SysParaUtil.getFkfsNameByMemberField(request, "mmdmoney") %>充值小计:<lable id="tdmmd">0</lable></span></td>
      			<td><span style="color:#00A6C0;"><%=SysParaUtil.getFkfsNameByMemberField(request, "bdfqmoney") %>充值小计:<lable id="tdbdfq">0</lable></span></td>
      			<td><span style="color:#00A6C0;"><%=SysParaUtil.getFkfsNameByMemberField(request, "qtmoney") %>充值小计:<lable id="tdqt">0</lable></span></td>
       		</tr> 
       	</table>
    </div>
</div>
</body>
<script type="text/javascript">

var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var onclickrow = "";
var nowday;
var pageurl = '<%=contextPath%>/KQDS_Member_RecordAct/selectListByType.act';
var number = 1;//序号
var tdmoney=0,tdgivemoney=0,tdtotal=0,tdxj=0,tdyhk=0,tdmmd=0,tdbdfq=0,tdqt=0,tdzfb=0,tdwx=0;
var menuid = "<%=menuid%>";
var qxnameArr = ['kkcx_scbb'];
var func = ['exportTable'];
var onclickrowobj = "";
$(function(){
	$("input[type='text']").attr("autocomplete","off");  //去掉input输入框的历史记录 lutian
	initHosSelectList4Front('organization'); // 连锁门诊下拉框
	//获取当前页面所有按钮
    getButtonPowerByPriv(qxnameArr,func,menuid);
 	//时间选择
	$(".unitsDate input").datetimepicker({
		language:  'zh-CN',
		minView:2,
        autoclose : true,
		format: 'yyyy-mm-dd',
		pickerPosition: "bottom-right"
	});
 	
	nowday = getNowFormatDate();
	$("#starttime").val(nowday);
	$("#endtime").val(nowday);
	//绑定两个时间选择框的chage时间
	$("#starttime,#endtime").change(function(){
		timeCompartAndFz("starttime","endtime");
    });
	//判断父页面是否选中行 选中则根据选中的值查询
 	onclickrow = window.parent.onclickrowOobj;
 	if(onclickrow != "" && onclickrow != null && onclickrow != "null" && onclickrow != "undefined"){
 		$("#queryInput").val(onclickrow.username);
 		$("#starttime").val("");
 		$("#endtime").val("");
 	}
	
	//加载表格
	getlist();
	setHeight();
});

//页面加载查询的表格 总的
function getlist(){
	number = 1;
	$("#table").attr("data-height",$(window).height() - 100);
	$("#table").bootstrapTable({
		url:pageurl, 
		dataType: "json",
		cache: false, 
		striped: true,
		queryParams:queryParams,
	 	onLoadSuccess: function(data){  //加载成功时执行
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","auto").css("pointer-events","auto");
				$("#query").text("查询");
			}
	 		$("#tdmoney").html(tdmoney.toFixed(2));
	 		$("#tdgivemoney").html(tdgivemoney.toFixed(2));
	 		$("#tdtotal").html(tdtotal.toFixed(2));
	 		$("#tdxj").html(tdxj.toFixed(2));
	 		$("#tdzfb").html(tdzfb.toFixed(2));
	 		$("#tdwx").html(tdwx.toFixed(2));
	 		$("#tdyhk").html(tdyhk.toFixed(2));
	 		$("#tdqt").html(tdqt.toFixed(2));
	 		$("#tdmmd").html(tdmmd.toFixed(2));
	 		$("#tdbdfq").html(tdbdfq.toFixed(2));
	 		
	 		/*表格载入时，设置表头的宽度 */
	        setTableHeaderWidth(".tableBox");
	        //付款方式赋值
	        getFkfsField();
	 	},
		columns: [
				{title: '序号',field: 'xh',align: 'center',sortable: true,
					formatter:function(value,row,index){  
		                return '<span>'+(number++)+'</span>' ;
                	}
				},
				{
	                 title: '开卡时间',
	                 field: 'createtime',
	                 align: 'center',
	                 sortable: true,
	                 formatter:function(value,row,index){  
		                 return '<span  title="'+value+'">' + value + '</span>';
             	  	 }
             	},
				{
             		title: '患者编号',
             		field: 'usercode',
             		align: 'center',
             		
             		sortable: true,
             		formatter:function(value,row,index){
             			return '<span>'+value+'</span>'
             		}
				},
				{title: '姓名',field: 'username',align: 'center',sortable: true,
					formatter:function(value,row,index){  
		                return '<span class="name" title="'+value+'">' + value + '</span>';
                	}
				},
				{title: '第一咨询',field: 'faskperson',align: 'center',sortable: true,
					formatter:function(value,row,index){  
		                return '<span class="name">' + value + '</span>';
                	}
				},
				{title: '接诊咨询',field: 'askperson',align: 'center',sortable: true,
					formatter:function(value,row,index){  
		                return '<span class="name">' + value + '</span>';
                	}
				},
				{title: '患者来源',field: 'devchannel',align: 'center',sortable: true,
					formatter:function(value,row,index){  
		                return '<span class="source">' + value + '</span>';
                	}
				},
				{title: '来源子分类',field: 'nexttype',align: 'center',sortable: true,
					formatter:function(value,row,index){  
		                return '<span class="source">' + value + '</span>';
                	}
				},
				{title: '建档人',field: 'jdr',align: 'center',sortable: true,
		            formatter: function(value, row, index) {
		                return '<span class="name" title="' + value + '">' + value + '</span>';
		            }
		        },
		        {title: '开发人',field: 'kfr',align: 'center',sortable: true,
		            formatter: function(value, row, index) {
		                return '<span class="name" title="' + value + '">' + value + '</span>';
		            }
		        },
				{title: '卡号',field: 'cardno',align: 'center',sortable: true,
				    formatter:function(value,row,index){  
		                return '<span title="'+value+'">' + value + '</span>';
               	    }
				},
				{
					title: '类型',
					field: 'type',
					align: 'center',
					
					sortable: true,
					editable: true,
					formatter:function(value,row,index){
						return '<span>'+value+'</span>';
					}
				},
				{title: '开卡-充值',field: 'cmoney',align: 'center',sortable: true,
              		formatter:function(value,row,index){
              			tdmoney = tdmoney + Number(row.cmoney);
		                return "<span class='money'>"+value+"</span>";
            	  	}
              	},
				{title: '开卡-赠送',field: 'cgivemoney',align: 'center',sortable: true,
              		formatter:function(value,row,index){
              			tdgivemoney = tdgivemoney + Number(row.cgivemoney);
              			return "<span class='money'>"+value+"</span>";
            	  	}
              	},
				{title: '开卡-小计',field: 'ctotal',align: 'center',sortable: true,
              		formatter:function(value,row,index){
              			tdtotal = tdtotal + Number(row.ctotal);
              			return "<span class='money'>"+value+"</span>";
            	  	}
              	},
              	{title: '现金充值',field: 'xjmoney',align: 'center',sortable: true,
              		formatter:function(value,row,index){
              			tdxj = tdxj + Number(value);
		                return "<span class='money'>"+value+"</span>";
            	  	}
              	},
              	{title: '支付宝充值',field: 'zfbmoney',align: 'center',sortable: true,
              		formatter:function(value,row,index){
              			tdzfb = tdzfb + Number(value);
		                return "<span class='money'>"+value+"</span>";
            	  	}
              	},
              	{title: '微信充值',field: 'wxmoney',align: 'center',sortable: true,
              		formatter:function(value,row,index){
              			tdwx = tdwx + Number(value);
		                return "<span class='money'>"+value+"</span>";
            	  	}
              	},
                {title: '银行卡充值',field: 'yhkmoney',align: 'center',sortable: true,
              		formatter:function(value,row,index){
              			tdyhk = tdyhk + Number(value);
		                return "<span class='money'>"+value+"</span>";
            	  	}
              	},
              	{title: '么么贷充值',field: 'mmdmoney',align: 'center',sortable: true,
              		formatter:function(value,row,index){
              			tdmmd = tdmmd + Number(value);
		                return "<span class='money'>"+value+"</span>";
            	  	}
              	},
              	{title: '百度分期充值',field: 'bdfqmoney',align: 'center',sortable: true,
              		formatter:function(value,row,index){
              			tdbdfq = tdbdfq + Number(value);
		                return "<span class='money'>"+value+"</span>";
            	  	}
              	},
                {title: '其他充值',field: 'qtmoney',align: 'center',sortable: true,
              		formatter:function(value,row,index){
              			tdqt = tdqt + Number(value);
		                return "<span class='money'>"+value+"</span>";
            	  	}
              	},
                {
                  title: '操作人',
                  field: 'createuser',
                  align: 'center',
                  sortable: true,
                  formatter:function(value,row,index){
		          	return '<span class="name" title="'+value+'">' + value + '</span>';
          	  	}
              },
              {
                  title: '操作',
                  field: ' ',
                  align: 'center',
                  sortable: true,
                  formatter: function(value, row, index) {//field 没有值 这里不能用value值
                  	 var html = "<span >";
                       html += '<a  onclick="methodModify(\'' + row.seqId + '\')" style="width:60px;color:red;cursor:pointer;">修改</a>';
                       html += '&nbsp;<a  onclick="methodModifyList(\'' + row.seqId + '\')" style="width:60px;color:#00A6C0;cursor:pointer;">记录</a>';
                       html += "</span>";
                       return html;
                  }
              }
		 ]
	}).on('click-row.bs.table', function (e, row, element){
		$('.success').removeClass('success');//去除之前选中的行的，选中样式
  		$(element).addClass('success');//添加当前选中的 success样式用于区别
  		var index = $('#table').find('tr.success').data('index');//获得选中的行
  		onclickrowobj =  $('#table').bootstrapTable('getData')[index];
	});
}

$('#clean').on('click', function(){
	$(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected");//核心
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
});

//补打充值单
$('#dayin').on('click',
function() {
    if (onclickrowobj == null || onclickrowobj == "") {
        layer.alert('请选择开卡记录' );
        return;
    }

    //先查询fyqrd页面在打印设置里 是a4还是a5，如果是a4则跳转a4界面
    var printSet = getPrintType("会员卡充值单");

    // 默认A5打印
    var hyurl = contextPath + '/KQDS_Print_SetAct/toHyczPrintPage.act?printpage='+printSet.printurl+'&printType=' + printSet.printtype + '&cztime=' + onclickrowobj.createtime + '&memberno=' + onclickrowobj.cardno + '&username=' + onclickrowobj.username + '&money=' + Number(onclickrowobj.cmoney).toFixed(2) + '&givemoney=' + Number(onclickrowobj.cgivemoney).toFixed(2) + '&totalmoney=' + Number(onclickrowobj.ctotal).toFixed(2) + '&usercode=' + onclickrowobj.usercode + '&ymoney=' + Number(onclickrowobj.ymoney).toFixed(2) + '&recordId=' + onclickrowobj.seqId + '&ygivemoney=' + Number(onclickrowobj.ygivemoney).toFixed(2) + '&ytotalmoney=' + Number(onclickrowobj.ytotal).toFixed(2) + '&cost_organization=' + onclickrowobj.organization;
    // 弹出打印页面
    parent.layer.open({
        type: 2,
        title: '打印',
        shadeClose: true,
        //点击遮罩关闭层
        area: ['1000px', '550px'],
        content: hyurl
    });
});

function queryParams(params) {
    var temp = {  //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
   		starttime:$('#starttime').val(),
   		endtime:$('#endtime').val(),
   		organization : $('#organization').val(), 
   		type:'开卡',
   		queryinput :$("#queryInput").val()
    };
    return temp;
}
//点击导出
function exportTable(){
	var fieldArr=[];
	var fieldnameArr=[];
	$('#table thead tr th').each(function () {
		var field = $(this).attr("data-field");
		if(field!=""){
			fieldArr.push(field);//获取字段
			fieldnameArr.push($(this).children()[0].innerText);//获取字段中文
		}
	});
	var param  = JsontoUrldata(queryParams());
	location.href = pageurl+"?flag=exportTable&fieldArr="+JSON.stringify(fieldArr)+"&fieldnameArr="+JSON.stringify(fieldnameArr)+"&"+param;
}
$('#query').on('click', function(){
	$(this).attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
	$(this).text("查询中");
	onclickrowobj = "";//初始化之前选中的行对象
	number = 1;//初始化序号
	tdmoney=0,tdgivemoney=0,tdtotal=0,tdxj=0,tdyhk=0,tdqt=0,tdzfb=0,tdwx=0;//初始化底部计算的小计
	$("#table").bootstrapTable('refresh',{'url':pageurl});
});
//计算左侧表格高度保证一屏展示
function setHeight() {
  var baseHeight = $(window).height();
  var serachH = $('.searchWrap').outerHeight();
  var formH = $('.formBox').outerHeight();
  $('#table').bootstrapTable('resetView', {
      height: baseHeight - serachH - formH - 20
  });
}
</script>
</html>