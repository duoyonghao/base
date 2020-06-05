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
<title>入库查询</title>
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
<style  type="text/css">
	/* 选择按钮 */
	.aBtn{display: inline-block;margin-right:0px;padding: 0 7px;height: 28px;line-height: 28px;border: solid 1px #0e7cc7;border-radius:15px;color: #0e7cc7;}
	.aBtn:hover{color: #fff;background: #117cca;text-decoration: none;}
	/*顶部详情  */
	.biliHistory .tableHd{
	padding: 0 20px;line-height:40px;
	font-family:"Microsoft YaHei";font-size:14px;
	color: #777;
	border-bottom:1px solid #ddd;
	}
	/*整体布局  */
	.infoBd{padding:0 10px 10px;font-size: 12px;border-bottom: solid 1px #0e7cc9;}/*表格两边间距与底部横线  */
	.formBox{overflow: hidden;padding: 10px 0;margin-bottom:10px;width:800px;margin:0 auto;}/*页面搜索条件居中  */
	.kqds_table{
		width:90%;
		margin:0 auto;
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
	
	 table input[type=text],.kv .kv-v input[type=text]{
		padding:0 0px;width:120px;height: 28px;
		line-height: 28px;
		border: solid 1px #e5e5e5;border-radius: 3px;
		-webkit-transition: all .3s;transition: all .3s;
	}
	table.table-condensed input[type=text]{
		height: 20px;
		line-height: 20px;
	}
	.searchWrap .btnBar > .aBtn {
	    width: 70px;
	}
	table th{
		background:#00A6C0;
		color:#fff;
	}
	#ckafter td{
		border:1px solid #ddd;
	}
</style>
</style>
<body>
<div class="databaseWrap">
  <div class="biliHistory">
 	 <table class="kqds_table">
    		<tr>
    			<td style="text-align:right;">单据编号：</td>
    			<td style="text-align:left;">
					<input type="text" name="outcode" id="outcode" readonly="readonly">
				</td>
    			<td style="text-align:right;">出库方式：</td>
    			<td style="text-align:left;"> 
   					<select  name="outtype" id="outtype" disabled="disabled">
                    	<option value="">请选择</option>
                    	<option value="1">领用出库</option>
                    	<option value="3">换货出库</option>
                    	<option value="5">退货出库</option>
						<option value="7">其他出库</option>
                    </select>
                </td>
    			<td style="text-align:right;">供应商：</td>
    			<td style="text-align:left;">
					<select  name="supplier" id="supplier"></select>
				</td>
    			
    			<td style="text-align:right;">出库仓库：</td>
    			<td style="text-align:left;">
   					<select  name="outhouse" id="outhouse" disabled="disabled"></select>
    			</td>
    			<td style="text-align:right;">部门：</td>
    			<td style="text-align:left;">
   					<select class="dept" name="sqdeptid" id="sqdeptid"></select>
    			</td>
    			<td rowspan="2" align="center">
    			  <a href="javascript:void(0);" class="kqdsCommonBtn" id="save" onclick="save()">保存</a>
    			</td>
    		</tr>
    		<tr>
    			<td style="text-align:right;">领料人：</td>
    			<td style="text-align:left;">
   					<input type="hidden" name="llr" id="llr" class="form-control" />
					<input type="text"   id="llrDesc" name="llrDesc" style="width: 120px;" onClick="javascript:single_select_user(['llr', 'llrDesc'],'single');"></input>	
    			</td>
    			<td style="text-align:right;">领用医生：</td>
    			<td style="text-align:left;">
   					<input type="hidden" name="sqdoctor" id="sqdoctor" class="form-control" />
					<input type="text"   id="sqdoctorDesc" name="sqdoctorDesc"  readonly style="width: 120px;" ></input>	
    			</td>
    			<td style="text-align:right;">附加说明：</td>
    			<td style="text-align:left;">
					<input type="text" name="outremark" id="outremark" >
				</td>
    			
    			<td style="text-align:right;">摘要：</td>
    			<td style="text-align:left;">
   					<input type="text" name="zhaiyao" id="zhaiyao">
    			</td>
    		</tr>
      </table>
      <div class="tableHd">商品明细</div>
        <table id="table" class="table-striped table-condensed table-bordered" style="width: 100%;" >
			<thead style="background: #00A6C0;color:white;">
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
							<th style="text-align: center; vertical-align: middle;">入库备注</th>
						</tr>
			</thead>
			<tbody style="background-color: #F0FFFF;text-align: center;"></tbody>
		</table>
		<div class="tableHd">库存信息 </div>
		<table style="width: 100%">
			<!-- 出库信息 -->
			<tr>
				<td style="text-align: center; vertical-align: middle;font-size: 16px;font-family: 'SimSun';">修改前</td>
				<td style="text-align: center; vertical-align: middle;font-size: 16px;font-family: 'SimSun';">修改后</td>
			</tr>
			<!-- 库存信息 -->
			<tr>
				<td width="50%" height="62px">
					<table id="ckbefore" class="table-striped table-condensed " style=""></table>
				</td>
				<td>
					<table id="ckafter" class="fixed-table-container table-striped table-condensed " style="width: 100%;border-radius:6px;">
						<thead style="border-radius:6px;overflow:hidden;">
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
		</table>
    </div>
  </div>
</div>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script> 
<script>
var contextPath = "<%=contextPath %>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var goodsoutDetail = "";
var goodsout = "";
$(function() {
	goodsoutDetail = window.parent.goodsDetail;
	goodsout = window.parent.onclickrowOobj2;
    getSupplierSelect2("supplier");
    getHouseSelect("outhouse");
    getDeptSelect("sqdeptid");
    getgoodsout();//出库单信息回显
    addDoodsDetail();//修改的数据
    kcData(goodsout.outhouse);//库存信息
    $("#outhouse").attr("disabled","disabled");//修改：出库仓库不能改
});
$('#outhouse').on('change',
	function() {
	kcData($(this).val());//库存信息
	editPrice();//触发事件
});
$('#supplier').on('change',
		function() {
		if($("#supplier").val() != goodsout.supplier){
			var index = layer.confirm('是否修改本单据的供应商？', {
		        btn: ['是', '否'] //按钮
		    },
		    function() {
		    	layer.close(index);
		    },
		    function() {
		    	$("#supplier").val(goodsout.supplier);
		    });
		}
	});
$("#outtype").change(function(){
	  if($(this).val() == "3" || $(this).val() == "5"){
		  $(".price").removeAttr("readonly");
	  }else{
		  //设置单价不可编辑
		  $(".price").attr("readonly","readonly");
		  //重置单价
		  $(".price").each(function() {
		        var oldvalue = $(this).attr("oldvalue");
		        $(this).val(oldvalue);
		        $(this).change();
		  });
	  }
});
function getgoodsout(){
	$("#outtype").val(goodsout.outtypecode);
	$("#supplier").val(goodsout.supplier).trigger("change");
	$("#outhouse").val(goodsout.outhouse);
	$("#outcode").val(goodsout.outcode);
	$("#llr").val(goodsout.llrid);
	$("#llrDesc").val(goodsout.llr);
	$("#sqdoctor").val(goodsout.sqdoctorid);
	$("#sqdoctorDesc").val(goodsout.sqdoctor);
	$("#outremark").val(goodsout.outremark);
	$("#zhaiyao").val(goodsout.zhaiyao);
	$("#sqdeptid").val(goodsout.sqdept);
}
function addDoodsDetail(){
	 var tablehtml = "";
	 tablehtml += "<tr style='border: 1px solid #ddd;'>";
	 tablehtml += '<td style="border-right: 1px solid #ddd;color:red;" onclick="del();">删除</td>';
    //商品名称0
     tablehtml += '<td style="height:30px;border-right: 1px solid #ddd;">' + goodsoutDetail.goodsname + '</td>';
    //商品编号1
    tablehtml += '<td style="height:30px;">' + goodsoutDetail.goodscode + '</td>';
    //类别2
    tablehtml += '<td style="border-right: 1px solid #ddd;">' + goodsoutDetail.goodstypename + '</td>';
    //规格3
    tablehtml += '<td style="border-right: 1px solid #ddd;">'+goodsoutDetail.goodsnorms+'</td>';
    //单位4
    tablehtml += '<td style="border-right: 1px solid #ddd;">'+goodsoutDetail.goodsunit+'</td>';
    //单价6
    tablehtml += '<td style="border-right: 1px solid #ddd;width:80px;"><input class="price" readonly="readonly" type="text" style="width:100%; text-align:center;" onfocus="this.select()" '
    +'onchange="editPrice()" name="outprice" id="outprice" value="'+goodsoutDetail.outprice+'" oldvalue="'+goodsoutDetail.outprice+'"></td>';
    //数量6
    tablehtml += '<td style="border-right: 1px solid #ddd;width:80px;"><input type="text" style="width:100%; text-align:center;" onchange="editPrice();" onfocus="this.select()" name="outnum" id="outnum" oldvalue='+goodsoutDetail.outnum+' value='+goodsoutDetail.outnum+'></td>';
    //金额7
    tablehtml += '<td style="border-right: 1px solid #ddd;width:80px;"><span name="ckmoney" id="ckmoney"  oldvalue='+goodsoutDetail.ckmoney+'>'+goodsoutDetail.ckmoney+'</span></td>';
    //入库备注9
    tablehtml += '<td style="border-right: 1px solid #ddd;width:150px;"><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" name="sqremark" id="sqremark" value='+goodsoutDetail.sqremark+'></td>';
    //商品主键10
	tablehtml += '<td style="display:none;">' + goodsoutDetail.goodsid + '</td>';     
    tablehtml += "</tr>";
    $('#table').find('tbody').append(tablehtml);
    //判断出库方式：换货 退货 单价是可编辑的，其他的不可编辑
    var outtype = $("#outtype").val();
    if(outtype =="3" || outtype=="5"){
   	 $(".price").removeAttr("readonly");
    }
}
//库存信息
function kcData(outhouse){
	var kcurl = contextPath + '/KQDS_Ck_Goods_DetailAct/selectList.act?goodsuuid='+goodsoutDetail.goodsuuid+'&sshouse='+outhouse;
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
            tablehtml += "<tr style=''>";
            tablehtml += '<td style="border-right: 1px solid #ddd;"><span id="sshousename">' + tabledata.sshousename + '</span></td>';
            tablehtml += '<td style="border-right: 1px solid #ddd;"><span id="goodsprice" oldvalue="'+tabledata.goodsprice+'">' + tabledata.goodsprice + '</span></td>';
            tablehtml += '<td style="border-right: 1px solid #ddd;"><span id="nums"  oldvalue="'+tabledata.nums+'">' + tabledata.nums + '</span></td>';
            tablehtml += '<td style="border-right: 1px solid #ddd;"><span id="kcmoney"  oldvalue="'+tabledata.kcmoney+'">' + tabledata.kcmoney + '</span></td>';
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
	var outnum = $("#outnum").val();//出库数量
    var oldoutnum = $("#outnum").attr("oldvalue");//原出库数量
	var outprice = $("#outprice").val();//出库单价
	var oldoutprice = $("#outprice").attr("oldvalue");//原出库单价
	var oldckmoney = $("#ckmoney").attr("oldvalue");//原出库金额
	var nums = $("#nums").html();//库存数量
	if(judgeSignNum(outnum)==false){
		 layer.alert('出库数量必须为整数！', {
	              
	     });
		 $("#outnum").val(oldoutnum);
		 return false;
	}else{
		if(goodsout.outhouse != $("#outhouse").val()){//出库改变仓库
			if(Number(outnum)>Number(nums)){
				 layer.alert('库存不足！', {
			              
			     });
				 $("#outnum").val(oldoutnum);
				 return false;
			}
		}else{
			if(Number(outnum)>(Number(nums)+Number(oldoutnum))){
				 layer.alert('库存不足！', {
			              
			     });
				 $("#outnum").val(oldoutnum);
				 return false;
			}
		}
	}
	 if(judgeSignFloat(outprice)==false && judgeSign(outprice)==false){
			layer.alert('单价格式不正确！' );
			 $("#outnum").val(oldoutnum);
	        return false;
	 }
	 if(Number(outprice)<=0){//必须大于0
    		    layer.alert('单价必须大于0！', {
	           		   
	      		});
    		    $("#outnum").val(oldoutnum);
    			return false;
	}
	var ckmoney = Number(outnum)*Number(outprice);
	$("#ckmoney").html(Number(ckmoney).toFixed(3));
	////改变库存单价、数量 、金额
	//相差的数量、金额
	 var changeoutnum = 0;
	 var changeckmoney =0; 
	 //入库仓库不变
	 if(goodsout.outhouse == $("#outhouse").val()){
		 changeoutnum = Number(outnum) - Number(oldoutnum);
		 changeckmoney = Number(ckmoney) - Number(oldckmoney);
	 }else{
		 changeoutnum = Number(outnum);
		 changeckmoney = Number(ckmoney);
	 }
	
	 //结存 库存 数量、金额
	 var jcnums = $("#nums").attr("oldvalue");
	 var jckcmoney = $("#kcmoney").attr("oldvalue");
	 //改变库存单价、数量 、金额
	 var nums =Number(jcnums) - Number(changeoutnum) ;
	 var kcmoney = Number(jckcmoney) - Number(changeckmoney);
	 if(Number(nums)==0){
		 $("#goodsprice").html("0");
		 $("#nums").html("0");
		 $("#kcmoney").html(kcmoney);
	 }else{
		 var goodsprice = (kcmoney/nums).toFixed(3);
		 $("#kcmoney").html(kcmoney);
		 $("#goodsprice").html(goodsprice);
		 $("#nums").html(nums);
	 }
}


//-------------------------------保存------------------------------
function save(){
	//保存 入库单（入库方式、供应商 、入库仓库、附加说明、摘要）
	var ckdprarm = {
			seqId : goodsout.seqId,
			outtype : $("#outtype").val(),
			supplier : $("#supplier").val(),
			outhouse : $("#outhouse").val(),
			outremark : $("#outremark").val(),
			zhaiyao : $("#zhaiyao").val(),
			sqdeptid : $("#sqdeptid").val(),
			llr : $("#llr").val(),
			sqdoctor : $("#sqdoctor").val()
	};
	//出库明细
	var ckdetailparam = {
			seqId : goodsoutDetail.seqId,
			outprice :  $("#outprice").val(),
			outnum :  $("#outnum").val(),
			ckmoney :  $("#ckmoney").html(),
			sqremark :  $("#sqremark").val()
	};
    //商品结存
    var goodsparam = {
			seqId : $("#gseqId").html(),
			goodsprice :  $("#goodsprice").html(),
			nums :  $("#nums").html(),
			kcmoney :  $("#kcmoney").html()
	};
    var paramOrder = {};
    var ckddata = JSON.stringify(ckdprarm);
    var ckdetaildata = JSON.stringify(ckdetailparam);
    var goodsdata = JSON.stringify(goodsparam);
    paramOrder.ckddata = ckddata;
    paramOrder.ckdetaildata = ckdetaildata; 
    paramOrder.goodsdata = goodsdata;
    var url = contextPath + '/KQDS_Ck_Goods_OutAct/editGoodsOut.act';
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
function del(id){
	var paramOrder ={
		outseqId : goodsout.seqId,//出库单主键
		outdetailseqId : goodsoutDetail.seqId,//出库明细主键
	    goodsseqId : $("#gseqId").html()//商品结存
	}
	//询问框
	  layer.confirm('确定删除？', {
	    btn: ['删除','放弃'] //按钮
	  }, function(){
		  var url = contextPath + '/KQDS_Ck_Goods_OutAct/deleteGoodsOut.act';
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
		        layer.alert('请求失败', {
		              
		        });
		    });
	  });
 }
</script>

</body>
</html>