<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
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
<title>领料编辑</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/select2.css" />

</head>
<style>
iframe{float: left;height: 450px;}

	/* 选择按钮 */
	.aBtn{display: inline-block;margin-right:0px;padding: 0 7px;height: 28px;line-height: 28px;border: solid 1px #0e7cc7;border-radius:15px;color: #0e7cc7;}
	.aBtn:hover{color: #fff;background: #117cca;text-decoration: none;}
	/*顶部详情  */
	.biliHistory .tableHd{
	padding: 0 20px;
	line-height:30px;font-family:"Microsoft YaHei";
	font-size:14px;color: #333 ;
	border-bottom:1px solid #ddd;
	margin-bottom:2px;
	background: #fff;
	}
	/*整体布局  */
	.infoBd{padding:0 10px 10px;font-size: 12px;border-bottom: solid 1px #0e7cc9;}/*表格两边间距与底部横线  */
	.formBox{overflow: hidden;padding: 10px 0;margin-bottom:10px;width:800px;margin:0 auto;}/*页面搜索条件居中  */
	.kqds_table{
		width:90%;
		align:center;
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
		
	.kqds_table  select { 
		height: 28px;
		width:120px;
		border: solid 1px #e5e5e5;
		border-radius: 3px;
	}
	
	input[type=text],.kv .kv-v input[type=text]{padding:0 0px;width:120px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}
	.searchWrap .btnBar > .aBtn {
	    width: 70px;
	}
	#table td,#table th,#ckbefore td,#ckbefore th,#ckafter th,#ckafter td{
		border:none;
	}
	#table thead,#ckbefore thead,#ckafter thead{
		background:#00A6C0 ;
		color:#fff;
		border-top-left-radius:5px;
		border-top-right-radius:5px;
	}
</style>
<body>
<div class="databaseWrap">
  <div class="biliHistory">
  <table class="kqds_table">
    		<tr>
    			<td style="text-align:right;">入库方式：</td>
    			<td style="text-align:left;"> 
   					 <select  name="intype" id="intype" disabled="disabled">
                    	<option value="">请选择</option>
                    	<option value="0">采购入库</option>
                    	<option value="2">换货入库</option>
                    	<option value="4">其他入库</option>
                     </select>
                </td>
    			<td style="text-align:right;">供应商：</td>
    			<td style="text-align:left;">
					<select  name="supplier" id="supplier"></select>
				</td>
    			
    			<!-- 
    			<td style="text-align:right;">入库仓库：</td>
    			<td style="text-align:left;">
   					<select  name="inhouse" id="inhouse" disabled="disabled"></select>
    			</td> -->
    			<td rowspan="2">
    			  <a href="javascript:void(0);" class="kqdsSearchBtn" id="save" onclick="save()">保存</a>
    			</td>
    		</tr>
    		<tr>
    			<td style="text-align:right;">单据编号：</td>
    			<td style="text-align:left;"> 
   					 <input type="text" name="incode" id="incode" readonly="readonly">
                </td>
    			<td style="text-align:right;">附加说明：</td>
    			<td style="text-align:left;">
					<input type="text" name="inremark" id="inremark" >
				</td>
    			
    			<td style="text-align:right;">摘要：</td>
    			<td style="text-align:left;">
   					<input type="text" name="zhaiyao" id="zhaiyao">
    			</td>
    		</tr>
    </table>
      <div class="tableHd">商品明细</div>
        <table id="table" class="table-striped table-condensed table-bordered" style="width: 100%">
			<thead >
				<tr>
					<th style="text-align: center; vertical-align: middle;height:30px;">操作</th>
					<th style="text-align: center; vertical-align: middle;height:30px;">商品名称</th>
					<th style="text-align: center; vertical-align: middle;">商品编号</th>
					<th style="text-align: center; vertical-align: middle;">类别</th>
					<th style="text-align: center; vertical-align: middle;">规格</th>
					<th style="text-align: center; vertical-align: middle;">单位</th>
					<th style="text-align: center; vertical-align: middle;">单价</th>
					<th style="text-align: center; vertical-align: middle;">数量</th>
					<th style="text-align: center; vertical-align: middle;">金额</th>
					<th style="text-align: center; vertical-align: middle;">有效期</th>
					<th style="text-align: center; vertical-align: middle;">入库备注</th>
				</tr>
			</thead>
			<tbody style="background-color: #F0FFFF;text-align: center;"></tbody>
		</table>
		<!-- 
		<div class="tableHd">商品库存信息</div>
		<table style="width: 100%">
			出库信息
			<tr>
				<td style="text-align: center; vertical-align: middle;font-size: 16px;font-family: 'SimSun';">修改前</td>
				<td style="text-align: center; vertical-align: middle;font-size: 16px;font-family: 'SimSun';">修改后</td>
			</tr>
			库存信息
			<tr>
				<td width="50%" height="62px">
					<table id="ckbefore" class="table-striped table-condensed table-bordered"></table>
				</td>
				<td>
					<table id="ckafter" class="table-striped table-condensed table-bordered" style="width: 100%">
						<thead style="">
						<tr>
							<th style="text-align: center; vertical-align: middle;height:30px;">所属仓库</th>
							<th style="text-align: center; vertical-align: middle;">结存均价</th>
							<th style="text-align: center; vertical-align: middle;">库存数量</th>
							<th style="text-align: center; vertical-align: middle;">结存金额</th>
							<th style="display: none;">商品库存主键</th>
						</tr>
						</thead>
						<tbody style="background-color: #F0FFFF;text-align: center;"></tbody>
					</table>
				</td>
			</tr>
		</table> -->
    </div>
  </div>
</div>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script> 
<script>
var contextPath = "<%=contextPath %>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var goodsinDetail = "";
var goodsin = "";
$(function() {
	goodsinDetail = window.parent.goodsDetail;
	goodsin = window.parent.onclickrowOobj2;
	getSupplierSelect2("supplier");
    getHouseSelect("inhouse");
    getgoodsin();//入库单信息回显
    addDoodsDetail();//修改的数据
    kcData(goodsin.inhouse);//库存信息
    $("#inhouse").attr("disabled","disabled");//修改：出库仓库不能改
});
/* $('#inhouse').on('change',
	function() {
	kcData($(this).val());//库存信息
	editPrice();//触发事件
}); */
$('#supplier').on('change',
	function() {
	if($("#supplier").val() != goodsin.supplier){
		var index = layer.confirm('是否修改本单据的供应商？', {
	        btn: ['是', '否'] //按钮
	    },
	    function() {
	    	layer.close(index);
	    },
	    function() {
	    	$("#supplier").val(goodsin.supplier);
	    });
	}
});

function getgoodsin(){
	$("#intype").val(goodsin.intypecode);
	$("#supplier").val(goodsin.supplier).trigger("change");
	$("#inhouse").val(goodsin.inhouse);
	$("#incode").val(goodsin.incode);
	$("#inremark").val(goodsin.inremark);
	$("#zhaiyao").val(goodsin.zhaiyao);
}
function addDoodsDetail(){
		var tablehtml = "";
		tablehtml += "<tr style=''>";
		tablehtml += '<td style=";color:red;cursor:pointer;" onclick="del();">删除</td>';
		//商品名称0
		tablehtml += '<td style="height:30px;;">' + goodsinDetail.goodsname + '</td>';
		//商品编号1
		tablehtml += '<td style="height:30px;">' + goodsinDetail.goodscode + '</td>';
		//科室2
		tablehtml += '<td style=";">' + goodsinDetail.housename + '</td>';
		//规格3
		tablehtml += '<td style=";">'+goodsinDetail.goodsnorms+'</td>';
		//单位4
		tablehtml += '<td style=";">'+goodsinDetail.goodsunit+'</td>';
		//单价5
		tablehtml += '<td style=";width:80px;"><span id="inprice">'+goodsinDetail.nuitprice+'</span></td>';
		//数量6
		tablehtml += '<td style=";width:80px;"><input type="text" style="width:100%; text-align:center;" onchange="editPrice();" onfocus="this.select()" name="innum" id="innum" oldvalue='+goodsinDetail.quantity+' value='+goodsinDetail.quantity+'></td>';
		//金额7
		tablehtml += '<td style=";width:80px;"><input type="text" style="width:100%;text-align:center;" onchange="editPrice();" onfocus="this.select()" name="rkmoney" id="rkmoney" oldvalue='+goodsinDetail.amount+' value='+goodsinDetail.amount+'></td>';
		//有效期8
		tablehtml += '<td style=";width:150px;"><input type="text" style="width:100%; text-align:center;" class="unitsDate" name="yxdate" id="yxdate" value='+goodsinDetail.createdate+'></td>';
		//入库备注9
		tablehtml += '<td style=";width:150px;"><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" name="sqremark" id="sqremark" value='+goodsinDetail.remark+'></td>';
		//商品主键10
		tablehtml += '<td style="display:none;">' + goodsinDetail.goodsid + '</td>';     
		tablehtml += "</tr>";
		$('#table').find('tbody').append(tablehtml);
		$(".unitsDate").datetimepicker({
			language:  'zh-CN',  
			minView:2,
		    autoclose : true,
			format: 'yyyy-mm-dd',
			pickerPosition: "bottom-right"
		}); 
}
//库存信息
function kcData(inhouse){
	var kcurl = contextPath + '/KQDS_Ck_Goods_DetailAct/selectList.act?goodsuuid='+goodsinDetail.goodsuuid+'&sshouse='+inhouse;
	var tableObj = $('#ckbefore').bootstrapTable('getOptions');
    if (tableObj.length == undefined) { // 如果length 存在，则说明是第一次加载，bootstrap table还没初始化
        if (kcurl == tableObj.url) { // 重复点击
            $('#ckbefore').bootstrapTable('refresh', {
                'url': kcurl
            });
            return;
        } else { // 切换Tab
            $('#ckbefore').bootstrapTable('destroy'); // 销毁bootstrap,tab切换无效
        }
    }
	//原库存信息
	$("#ckbefore").bootstrapTable({
	        url: kcurl,
	        dataType: "json",
	        cache: false,
	        striped: true,
	        onLoadSuccess: function(data) { //加载成功时执行
	        },
	        columns: [
	                    {title: '所属仓库',field: 'sshousename',align: 'center',valign: 'middle',sortable: true},
				        {title: '结存均价',field: 'goodsprice',align: 'center',valign: 'middle'},
				        {title: '库存数量',field: 'nums',align: 'center',valign: 'middle'},
				        {title: '结存金额',field: 'kcmoney',align: 'center',valign: 'middle'},
	      	 ]
	});
	//现库存信息
    $.axse(kcurl, null,
    function(data) {
    	$("#ckafter").find('tbody').html("");
        for (var i = 0; i < data.length; i++) {
            var tablehtml = "";
            var tabledata = data[i];
            tablehtml += "<tr style='border: 1px solid #ddd;'>";
            tablehtml += '<td style=";"><span id="sshousename">' + tabledata.sshousename + '</span></td>';
            tablehtml += '<td style=";"><span id="goodsprice" oldvalue="'+tabledata.goodsprice+'">' + tabledata.goodsprice + '</span></td>';
            tablehtml += '<td style=";"><span id="nums"  oldvalue="'+tabledata.nums+'">' + tabledata.nums + '</span></td>';
            tablehtml += '<td style=";"><span id="kcmoney"  oldvalue="'+tabledata.kcmoney+'">' + tabledata.kcmoney + '</span></td>';
            tablehtml += '<td style="display:none;"><span id="gseqId">' + tabledata.gseqId + '</span></td>';
            tablehtml += "</tr>";
            $("#ckafter").find('tbody').append(tablehtml);
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}
function editPrice(){
	 var innum = $("#innum").val();
	 var rkmoney = $("#rkmoney").val();
	 var oldinnum = $("#innum").attr("oldvalue");
	 var oldrkmoney = $("#rkmoney").attr("oldvalue");
	 if(judgeSignNum(innum)==false){
			 layer.alert('数量格式不正确！' );
			$("#innum").val(oldinnum);
			return false;
	 }
	 if(judgeSignFloatNum(rkmoney)==false){
			layer.alert('金额格式不正确！' );
			$("#rkmoney").val(oldrkmoney);
	        return false;
	 }
	 if(innum!="" && rkmoney!=""){
		 if(Number(innum)==0){
			 $("#inprice").html("0");
		 }else{
			 var inprice = (rkmoney/innum).toFixed(2);
			 $("#inprice").html(inprice);
		 }
		 //相差的数量、金额
		 var changeinnum = 0;
		 var changerkmoney =0; 
		 //入库仓库不变
		 if(goodsin.inhouse == $("#inhouse").val()){
			 changeinnum = Number(innum) - Number(oldinnum);
			 changerkmoney = Number(rkmoney) - Number(oldrkmoney);
		 }else{
			 changeinnum = Number(innum);
			 changerkmoney = Number(rkmoney);
		 }
		
		 //结存 库存 数量、金额
		 var jcnums = $("#nums").attr("oldvalue");
		 var jckcmoney = $("#kcmoney").attr("oldvalue");
		 //改变库存单价、数量 、金额
		 var nums = Number(changeinnum)+Number(jcnums);
		 var kcmoney = Number(jckcmoney)+Number(changerkmoney);
		 var goodsprice = (kcmoney/nums).toFixed(2);
		 $("#goodsprice").html(goodsprice);
		 $("#nums").html(nums);
		 $("#kcmoney").html(kcmoney);
	 }
}


//-------------------------------保存------------------------------
function save(){
	
	//保存 入库单（入库方式、供应商 、入库仓库、附加说明、摘要）
	var rkdprarm = {
		seqId : goodsin.seqId,
		intype : $("#intype").val(),
		supplier : $("#supplier").val(),
		inhouse : $("#inhouse").val(),
		inremark : $("#inremark").val(),
		zhaiyao : $("#zhaiyao").val(),
	};
	//入库明细
	var rkdetailparam = {
		seqId : goodsinDetail.seqId,
		inprice :  $("#inprice").html(),
		innum :  $("#innum").val(),
		rkmoney :  $("#rkmoney").val(),
		yxdate :  $("#yxdate").val(),
		sqremark :  $("#sqremark").val()
	};
    //商品结存
    /* var goodsparam = {
		seqId : $("#gseqId").html(),
		goodsprice :  $("#goodsprice").html(),
		nums :  $("#nums").html(),
		kcmoney :  $("#kcmoney").html()
	}; */
    var paramOrder = {};
    var rkddata = JSON.stringify(rkdprarm);
    var rkdetaildata = JSON.stringify(rkdetailparam);
    //var goodsdata = JSON.stringify(goodsparam);
    paramOrder.rkddata = rkddata;
    paramOrder.rkdetaildata = rkdetaildata; 
    //paramOrder.goodsdata = goodsdata;
    var url = contextPath + '/HUDH_Goods_DoctorPickInAct/updateGoodsDoctorPickBySeqId.act';
    $.axse(url, paramOrder,
    function(r) {
        if (r.retState == "0") {
            layer.alert('操作成功', {
                end: function() {
                	parent.refresh();
                    parent.layer.close(frameindex); //再执行关闭 */
                }
            });
        } 
    },
    function() {
        layer.alert('请求失败' );
    });
}
/**
 * 删除
 */
function del(id){
	  //alert(goodsin.seqId);
	  var paramOrder ={
		  inseqId : goodsin.seqId,//入库单主键
		  indetailseqId : goodsinDetail.seqId,//入库明细主键
	      goodsseqId : $("#gseqId").html()//商品结存
	  }
	  //询问框
	  layer.confirm('确定删除？', {
	    btn: ['删除','放弃'] //按钮
	  }, function(){
		    var url = contextPath + '/GoodsDoctorPickInDetailAct/deleteDoctorPickInDetailBySeqId.act';
		    $.axse(url, paramOrder,
		    function(r) {
		        if (r.retState == "0") {
		            layer.alert('操作成功', {
		                end: function() {
		                	parent.refresh();
		                    parent.layer.close(frameindex); //再执行关闭 */
		                }
		            });
		        } else{
		        	layer.alert(r.retMsrg, {
			              
			        });
		        }
		    },
		    function() {
		        layer.alert('请求失败', {
		              
		        });
		    });
	  });
 }
</script>

</body>
</html>