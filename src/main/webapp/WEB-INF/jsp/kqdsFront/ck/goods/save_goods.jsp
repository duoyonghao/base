<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
   String seqId = request.getParameter("seqId");
   if(seqId==null){
	   seqId="";
   }
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>商品类别</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/kqdsFront/css/bootstrapSwitch.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrapSwitch/bootstrap-switch.css" />

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/kqdsCss/kqdsCommon.css"/>
<style>
	table.tableLayout{/* table布局内容距离页面顶端30px */
		margin-top:15px;
	}
	.commonText{	/*数据项的样式  */
		padding: 0 10px;
	}
	table.tableLayout select,table.tableLayout input[type="text"]{
		width:140px;
	}
	table.tableLayout tr{
		height:36px;
	}
	/* 仓库类别 */
	#warehouseSort{
		border:1px solid blue;
	}
	#warehouseSort .kv-v{
		float:left;
	}
	/* *号验证 */
	.verify{
		color: red;
		margin: 0px 5px 0px -5px;
	}
</style>
</head>
<body>
<!--添加回访弹窗-->
<div id="container">
    <div class="infoBd">
    	<form class="form-horizontal"  id="form1">
    		<input type="hidden" class="form-control" name="seqId" id="seqId" >
	        <table class="tableLayout">
		     	<tbody>
		     		<tr>
		     			<td>
		     				<span class="commonText"><font class="verify">*</font>所属仓库</span>
		     			</td>
		     			<td>
		     				 <select class="select2"  name="sshouse" id="sshouse"> </select>
		     			</td>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;"><font class="verify">*</font>一级类别</span>
		     			</td>
		     			<td>
		     				<select class="select2"  name="basetype" id="basetype">
		                    	<option value="">请选择</option>
		                    </select>
		     			</td>
		     		</tr>
		     		<tr>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;"><font class="verify">*</font>二级类别</span>
		     			</td>
		     			<td>
		     				<select class="select2"  name="nexttype" id="nexttype">
		                   		 <option value="">请选择</option>
		            	  	</select>
		     			</td>
		     			<td>
		     				<span class="commonText">排序号</span>
		     			</td>
		     			<td>
		     				 <input type="text" name="sortno" id="sortno" >
		     			</td>
		     		</tr>
		     		
		     		<tr>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;"><font class="verify">*</font>商品编号</span>
		     			</td>
		     			<td>
		     				<input type="text" name="goodscode" id="goodscode" readonly="readonly">
		     			</td>
		     			<td>
		     				<span class="commonText"><font class="verify">*</font>商品名称</span>
		     			</td>
		     			<td>
		     				 <input type="text" name="goodsname" id="goodsname">
		     			</td>
		     		</tr>
		     		
		     		<!-- <tr>
		     			<td>
		     				<span class="commonText">排序号</span>
		     			</td>
		     			<td>
		     				 <input type="text" name="sortno" id="sortno" >
		     			</td>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">商品类别</span>
		     			</td>
		     			<td>
		     				<input type="hidden" name="goodstype" id="goodstype" />
			                <input type="text" name="goodstypename" id="goodstypename" readonly="readonly" onclick="selectGoodsTypezTreeInit();">
		     			</td>
		     		</tr> -->
		     		
		     		<tr>
		     			<td>
		     				<span class="commonText"><font class="verify">*</font>商品规格</span>
		     			</td>
		     			<td>
		     				 <input type="text" name="goodsnorms" id="goodsnorms">
		     			</td>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;"><font class="verify">*</font>单位</span>
		     			</td>
		     			<td>
		     				<input type="text" name="goodsunit" id="goodsunit">
		     			</td>
		     		</tr>
		     		
		     		<tr>
		     			<td>
		     				<span class="commonText">下限值</span>
		     			</td>
		     			<td>
		     				<input type="text" name="minnums" id="minnums" >
		     			</td>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">报警提醒</span>
		     			</td>
		     			<td>
		     				<input id="mingj" name="mingj" type="checkbox"/>
		     			</td>
		     		</tr>
		     		
		     		<tr>
		     			<td>
		     				<span class="commonText">上限值</span>
		     			</td>
		     			<td>
		     				 <input type="text" name="maxnums" id="maxnums" >
		     			</td>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">报警提醒</span>
		     			</td>
		     			<td>
		     				<input id="maxgj" name="maxgj" type="checkbox" />
		     			</td>
		     		</tr>
		     		
		     		<tr>
		     			<!-- <td>
		     				<span class="commonText">库位</span>
		     			</td>
		     			<td>
		     				 <input type="text" name="kuwei" id="kuwei" >
		     			</td> -->
		     			<td>
		     				<span class="commonText" style="padding-left:20px; display: none;">楼层仓库</span>
		     			</td>
		     			<td>
		     				<input type="text" name="type" id="type" style="display: none;" >
		     			</td>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">备注</span>
		     			</td>
		     			<td>
		     				<input type="text" name="remark" id="remark" >
		     			</td>
		     		</tr>
		     	</tbody>
	     	</table>   
     	</form>
     	<div class="position-bottom" >
			<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="submitu()" id="tijiao">保存</a>
		</div>
    </div>
</div>

<div id="menuContent" class="menuContent" style="background:#DDDDDD;display:none; position: absolute;">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:160px;"></ul>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapSwitch/bootstrap-switch.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/selectGoodsType.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script> 
<script type="text/javascript">
var seqId = "<%=seqId%>";
var menuid= parent.menuid;
var contextPath = '<%=contextPath%>';
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var onclickrow=window.parent.onclickrow;//父页面选中对象
$(function() {
	 if(menuid==603100){
	    	getHouseSelect("sshouse","2");
	    	$("#type").val("2");
	    }else{
	    	getHouseSelect("sshouse","1");
	    	$("#type").val("1");
	    } //初始化仓库
	
	$('#sshouse').change(function() {
        if ($(this).val()) { // 如果一级有值，再请求
         getBaseTypeSelect('basetype', this.value);
        }
    });
	$('[name="mingj"]').bootstrapSwitch({  
	    onText:"开启",  
	    offText:"关闭",  
	    onColor:"success",  
	    offColor:"info",  
	    size:"normal",  
	    onSwitchChange:function(event,state){  
	        if(state==true){  
	            $(this).val("1");  
	        }else{  
	            $(this).val("0");  
	        }  
	    }  
	})
	$('[name="maxgj"]').bootstrapSwitch({  
	    onText:"开启",  
	    offText:"关闭",  
	    onColor:"success",  
	    offColor:"info",  
	    size:"normal",  
	    onSwitchChange:function(event,state){  
	        if(state==true){  
	            $(this).val("1");  
	        }else{  
	            $(this).val("0");  
	        }  
	    }  
	})
    if (seqId != null && seqId != "") {
        var detailurl = contextPath + '/KQDS_Ck_Goods_DetailAct/selectDetail.act?seqId=' + seqId;
        $.axse(detailurl, null,
        function(data) {
            loadData(data.data);
            var goodstypeObj = getGoodsType(data.data.goodstype);
            $("#goodstypename").val(goodstypeObj.goodstype);
            if(data.data.mingj == "1"){
            	$('[name="mingj"]').bootstrapSwitch('state', true);
            }
            if(data.data.maxgj == "1"){
            	$('[name="maxgj"]').bootstrapSwitch('state', true);
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });
    } else {
        $("#goodstype").val(parent.goodstype).trigger("change");;
        $("#goodstypename").val(parent.goodstypename);
    }
});
/* 所属仓库 */
$("#sshouse").change(function(){
	$("#goodscode").val(""); //清空追加
	$("#basetype").html("<option value=''>请选择</option>");
	$("#nexttype").html("<option value=''>请选择</option>");
});
/* 一级类别 */
$("#basetype").change(function(){
	$("#goodscode").val(""); //清空追加
});
//初始化二级类别
$('#basetype').change(function() {
    if ($(this).val()) { // 如果一级有值，再请求
        getNextTypeSelect('nexttype', this.value);
    }
});
//是否选择类别
$("#nexttype").change(function(){
	var sshouse=$("#sshouse").val();
	var goodNum=getNumber(sshouse); //自动生成的商品编号
	$("#goodscode").val(goodNum);
});
//提交
function submitu() {
    //验证
    var sshouse = $("#sshouse").val(); //仓库
    var basetype = $("#basetype").val(); //一级类别
    var goodstype = $("#nexttype").val();//二级类别
    var goodsname = $("#goodsname").val(); //商品名称
    var goodscode = $("#goodscode").val(); //商品编号
    var goodsnorms = $("#goodsnorms").val(); //商品规格
    var goodsunit = $("#goodsunit").val(); //商品单位
    var sortno = $("#sortno").val();
    var minnums = $("#minnums").val();
    var maxnums = $("#maxnums").val();
    var status = '0';
    if (sshouse == "" || sshouse == null) {
        layer.alert('获取仓库失败,请重新选择仓库' );
        return false;
    }
    if (basetype == "" || basetype == null) {
        layer.alert('请选择商品一级类别' );
        return false;
    } 
    if (goodstype == "" || goodstype == null) {
        layer.alert('请选择商品二级类别' );
        return false;
    } 
    if (goodscode == "" || goodscode == null) {
        layer.alert('请填写商品编号' );
        return false;
    }
    if (goodsname == "" || goodsname == null) {
        layer.alert('请填写商品名称' );
        return false;
    } 
    if (goodsnorms == "" || goodsnorms == null) {
        layer.alert('请填写商品规格' );
        return false;
    } 
    if (goodsunit == "" || goodsunit == null) {
        layer.alert('请填写商品单位' );
        return false;
    } 
    /* var seqId = $("#seqId").val();
    if (!YzCode(seqId, goodscode)) {
        layer.alert('商品编号已存在！' );
        return false;
    } */
 	if (minnums != null && minnums != "") {
    	if(judgeSign(minnums)==false){
    		 layer.alert('下限值必须为正整数', {
    	              
    	        });
    	        return false;
    	}
    }
 	if (maxnums != null && maxnums != "") {
    	if(judgeSign(maxnums)==false){
    		 layer.alert('上限值必须为正整数', {
    	              
    	        });
    	        return false;
    	}
    }
    var param = {
        seqId: seqId,
        goodsname: goodsname,
        goodscode: goodscode,
        goodstype: goodstype,
        type: $("#type").val(),
        minnums: minnums,
        maxnums: maxnums,
        mingj:$('input[name="mingj"]').val(),
        maxgj:$('input[name="maxgj"]').val(),
        kuwei: $("#kuwei").val(),
        remark: $("#remark").val(),
        goodsnorms: $("#goodsnorms").val(),
        goodsunit: $("#goodsunit").val(),
        sortno: sortno,
        sshouse : sshouse,
        status: status
    };
    var url = contextPath + '/KQDS_Ck_Goods_DetailAct/insertOrUpdate.act';
    $.axseSubmit(url, param,
    function() {},
    function(r) {
        if (r.retState == "0") {
            layer.alert('保存成功', {
                  
                end: function() {
                    parent.refresh();
                    parent.layer.close(frameindex); //再执行关闭
                }
            });
        } else {
            layer.alert('保存失败'  );
        }
    },
    function() {
        layer.alert('保存失败' );
    });
}
function YzCode(seqId, goodscode) {
    var flag = true;
    var url = contextPath + '/KQDS_Ck_Goods_DetailAct/YzCode.act?seqId=' + seqId + '&goodscode=' + goodscode;
    $.axse(url, null,
    function(r) {
        flag = r.data;
    },
    function() {
        layer.alert('验证失败' );
    });
    return flag;
}
$('#goodstype').on('change',
function() {
    var goodstypename = $("#goodstype").find("option:selected").text();
    $("#goodstypename").val(goodstypename);
});
//获取自动生成的商品编号
function getNumber(id){
	var autoGoodNum="";
	$.ajax({
	    url:contextPath + '/KQDS_Ck_GoodsAct/getNumber.act',    //请求的url地址
	    dataType:"json",   //返回格式为json
	    //async:true,//请求是否异步，默认为异步，这也是ajax重要特性
	    data:{"id":id},    //参数值
	    type:"GET",   //请求方式
	    async:false,
	    success:function(data){
	        //请求成功时处理
	    	if(data.goodscode){
	    		autoGoodNum=data.goodscode;
	    	}
	    }
	});
	return autoGoodNum;
}
</script>
</html>
