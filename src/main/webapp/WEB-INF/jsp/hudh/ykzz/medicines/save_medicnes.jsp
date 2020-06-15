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
</style>
</head>
<body>
<!--添加回访弹窗-->
<div id="container">
    <div class="infoBd">
    	<form class="form-horizontal"  id="form1">
    		<input type="hidden" class="form-control" name="seqId" id="seqId" >
	        <table class="tableLayout" id="initialize">
		     	<tbody>
		     		<tr>
		     			<td>
		     				<span class="commonText">药品名称<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				 <input type="text" name="good_name" id="good_name" >
		     			</td>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">药品零售价<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				<input type="text" name="drug_retail_price" id="drug_retail_price">
		     			</td>
		     		</tr>
		     		
		     		<tr>
		     			<td>
		     				<span class="commonText">折&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;扣</span>
		     			</td>
		     			<td>
		     				 <input type="text" name="discount" id="discount" >
		     			</td>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">药品化学名</span>
		     			</td>
		     			<td>
		     				<!-- <input type="hidden" name="goodstype" id="goodstype" /> -->
			                <input type="text" name="chemistry_name" id="chemistry_name">
		     			</td>
		     		</tr>
		     		
		     		<tr>
		     			<td>
		     				<span class="commonText">药品规格<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				 <input type="text" name="pharm_spec" id="pharm_spec">
		     			</td>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				<input type="text" name="company" id="company">
		     			</td>
		     		</tr>
		     		
		     		<tr>
		     			<td>
		     				<span class="commonText">一级分类<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				 <select name="drugs_typeone" id="drugs_typeone">
		     				 </select>
		     			</td>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">二级分类<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				<select name="drugs_typetwo" id="drugs_typetwo">
		     					<option>请选择</option>
		     				</select>
		     			</td>
		     		</tr>
		     		
		     		<tr>
		     			<td>
		     				<span class="commonText">包装数量<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				 <input type="text" name="packing_num" id="packing_num">
		     			</td>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">最小单位<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				<input type="text" name="company_min" id="company_min">
		     			</td>
		     		</tr>
		     		
		     		<tr>
		     			<td>
		     				<span class="commonText">含量系数<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				 <input type="text" name="content_coe" id="content_coe">
		     			</td>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">含量单位<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				<input type="text" name="content_unit" id="content_unit">
		     			</td>
		     		</tr>
		     		
		     		<tr>
		     			<td>
		     				<span class="commonText">统计大项目</span>
		     			</td>
		     			<td>
		     				 <input type="text" name="sta_item" id="sta_item">
		     			</td>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">国家编码<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				<input type="text" name="contry_code" id="contry_code">
		     			</td>
		     		</tr>
		     		
		     		<tr>
		     			<td>
		     				<span class="commonText">药理分类</span>
		     			</td>
		     			<td>
		     				 <input type="text" name="pharm_class" id="pharm_class">
		     			</td>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">药品剂型<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				<input type="text" name="pharm_dos" id="pharm_dos">
		     			</td>
		     		</tr>
		     		
		     		<tr>
		     			<td>
		     				<span class="commonText">抗生素级别</span>
		     			</td>
		     			<td>
		     				 <input type="text" name="ant_gra" id="ant_gra">
		     			</td>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">精神药品</span>
		     			</td>
		     			<td>
		     				<select name="psy_drugs" id="psy_drugs">
		     					<option>请选择</option>
		     					<option>精神药物</option>
		     					<option>非精神药物</option>
		     				</select>
		     				<!-- <input type="text" name="psy_drugs" id="psy_drugs"> -->
		     			</td>
		     		</tr>
		     		
		     		<tr>
		     			<td>
		     				<span class="commonText">下&nbsp;&nbsp;限&nbsp;&nbsp;值</span>
		     			</td>
		     			<td>
		     				<input type="text" name="minnums" id="minnums" >
		     			</td>
		     			
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">报警提醒</span>
		     			</td>
		     			<td>
		     				<select name="mingj" id="mingj">
		     					<option>请选择</option>
		     					<option>是</option>
		     					<option>否</option>
		     				</select>
		     			</td>
		     		</tr>
		     		
		     		<tr>
		     			<td>
		     				<span class="commonText">上&nbsp;&nbsp;限&nbsp;&nbsp;值</span>
		     			</td>
		     			<td>
		     				 <input type="text" name="maxnums" id="maxnums" >
		     			</td>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">报警提醒</span>
		     			</td>
		     			<td>
		     				<select name="maxgj" id="maxgj">
		     					<option>请选择</option>
		     					<option>是</option>
		     					<option>否</option>
		     				</select>
		     			</td>
		     		</tr>
		     		
		     		<tr>
		     			<td>
		     				<span class="commonText">药品标识</span>
		     			</td>
		     			<td>
		     				 <input type="text" name="drug_identify" id="drug_identify" >
		     			</td>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">保存条件</span>
		     			</td>
		     			<td>
		     				<input type="text" name="comments" id="comments" >
		     			</td>
		     		</tr>
		     		
		     		<tr>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">供应商<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     			 	<select name="manu_id" id="manu_id">
		     			 		<option value="manu">请选择</option>
		     			 	</select>
		     			</td>
		     			
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">包装单位<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				<input type="text" name="packing_unit" id="packing_unit" >
		     			</td>
		     		</tr>
		     		
		     		<tr>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">药品分类</span>
		     			</td>
		     			<td>
		     				<input type="text" name="drugs_type" id="drugs_type" >
		     			</td>
		     			
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">生产商<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     			 	<select name="manufac_id" id="manufac_id">
		     			 		<option value="manufac">请选择</option>
		     			 	</select>
		     			</td>
		     		</tr>
		     		
		     		<tr>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">分类<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     			 	<select name="classify" id="classify">
		     			 		<option>请选择</option>
		     			 		<option value="1">高危药品</option>
		     			 		<option value="2">抗菌素</option>
		     			 		<option value="3">其他</option>
		     			 	</select>
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
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/ykzx/yk.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/selectGoodsType.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript">
var apppath = apppath();
var seqId = "<%=seqId%>";
var contextPath = '<%=contextPath%>';
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
	addFact();//动态获取供应商
	initSelectBaseType($("#drugs_typeone"));
	$("#drugs_typeone").on("change",function (){
		initSelectNextType($("#drugs_typetwo"),$("#drugs_typeone").val());
	});
	addManuFac();//动态获取生产商
	addDrugsInfor();//初始化
});

function addDrugsInfor(){
	var id = seqId;
	//alert(id);
	var url = contextPath + "/HUDH_YkzzAct/selectDrugsByPrimaryId.act";
	var param = { id : id };
	$.axseSubmit(url, param, function() {}, function(r) {
	  //alert(JSON.stringify(r));
	  //console.log(r);
	  $("#good_name").val(r.good_name);
	  $("#drug_retail_price").val(r.drug_retail_price);
	  $("#discount").val(r.discount);
	  $("#chemistry_name").val(r.chemistry_name);
	  $("#pharm_spec").val(r.pharm_spec);
	  
	  //$("#drugs_typeone option:selected").text(r.basetype);//动态获取数据的选择赋值
	  //$("#drugs_typetwo option:selected").text(r.nexttype);//子类型
	  $("#drugs_typeone").val(r.type_name);
	  $("#drugs_typetwo").val(r.type_name);
	  
	  $("#packing_num").val(r.packing_num);
	  $("#company_min").val(r.company_min);
	  $("#company").val(r.company);
	  $("#content_coe").val(r.content_coe);
	  $("#content_unit").val(r.content_unit);
	  $("#sta_item").val(r.sta_item);
	  $("#contry_code").val(r.contry_code);
	  $("#content_unit").val(r.content_unit);
	  $("#sta_item").val(r.sta_item);
	  $("#contry_code").val(r.contry_code);
	  $("#pharm_class").val(r.pharm_class);
	  $("#pharm_dos").val(r.pharm_dos);
	  $("#ant_gra").val(r.ant_gra);
	  $("#psy_drugs").val(r.psy_drugs);
	  $("#minnums").val(r.minnums);
	  $("#mingj").val(r.mingj);
	  $("#maxnums").val(r.maxnums);
	  $("#maxgj").val(r.maxgj);
	  $("#drug_identify").val(r.drug_identify);
	  $("#comments").val(r.comments);//药品介绍
	  $("#drug_identify").val(r.drug_identify);
	  $("#comments").val(r.comments);
	  $("#drug_total_num").val(r.drug_total_num);//药品总数量
	  $("#packing_unit").val(r.packing_unit);
	  //$("#manu_id option:selected").text(r.manu_name);//厂商
	  $("#manu_id").val(r.manu_id);
	  $("#drugs_type").val(r.drugs_type);
	  $("#manufac_id").val(r.manufac_id);
	  $("#classify").val(r.classify);
	}, function() {
		layer.alert("获取失败");
	});
	if(id) {
		var url = contextPath + "/HUDH_YkzzAct/selectDrugsByPrimaryId.act";
		var param = { id : id };
		$.axseSubmit(url, param, function() {}, function(r) {
		  //alert(JSON.stringify(r));
		  //console.log(r);
		  $("#good_name").val(r.good_name);
		  $("#drug_retail_price").val(r.drug_retail_price);
		  $("#discount").val(r.discount);
		  $("#chemistry_name").val(r.chemistry_name);
		  $("#pharm_spec").val(r.pharm_spec);
		  
		  $("#drugs_typeone").val(r.drugs_typeone);//动态获取数据的选择赋值
		  initSelectNextType($("#drugs_typetwo"),r.drugs_typeone);
		  $("#drugs_typetwo").val(r.drugs_typetwo);//子类型 
		  //$("#drugs_typeone").val(r.type_name);
		  //$("#drugs_typetwo").val(r.type_name);
		  
		  $("#packing_num").val(r.packing_num);
		  $("#company_min").val(r.company_min);
		  $("#company").val(r.company);
		  $("#content_coe").val(r.content_coe);
		  $("#content_unit").val(r.content_unit);
		  $("#sta_item").val(r.sta_item);
		  $("#contry_code").val(r.contry_code);
		  $("#content_unit").val(r.content_unit);
		  $("#sta_item").val(r.sta_item);
		  $("#contry_code").val(r.contry_code);
		  $("#pharm_class").val(r.pharm_class);
		  $("#pharm_dos").val(r.pharm_dos);
		  $("#ant_gra").val(r.ant_gra);
		  $("#psy_drugs").val(r.psy_drugs);
		  $("#minnums").val(r.minnums);
		  $("#mingj").val(r.mingj);
		  $("#maxnums").val(r.maxnums);
		  $("#maxgj").val(r.maxgj);
		  $("#drug_identify").val(r.drug_identify);
		  $("#comments").val(r.comments);//药品介绍
		  $("#drug_identify").val(r.drug_identify);
		  $("#comments").val(r.comments);
		  $("#drug_total_num").val(r.drug_total_num);//药品总数量
		  $("#packing_unit").val(r.packing_unit);
		  /* $("#manu_id option:selected").text(r.manu_name);//厂商 */
		  $("#manu_id").val(r.manu_id);
		  $("#manufac_id").val(r.manufac_id);
		  $("#drugs_type").val(r.drugs_type);
		  $("#classify").val(r.classify);
		}, function() {
			layer.alert("获取失败");
		});
	}
}  

function addFact(){
	var url = contextPath + "/HUDH_YkzzManuAct/findAllManu.act";
	var param = { id : null };
	$.axseSubmit(url, param, function() {}, function(r) {
	  //alert(JSON.stringify(r));
	  //alert(r.length);
	  for (var i = 0; i < r.length; i++) {
			  $("#manu_id").append(
					'<option value='+ r[i].id + '>' + r[i].manuName + '</option>' 
			  );
	  }
	}, function() {
		
	});
}

function addManuFac(){
	var url = contextPath + "/YkzzManufacturersAct/findAllManufacturers.act";
	var param = { id : null };
	$.axseSubmit(url, param, function() {}, function(r) {
		  //alert(JSON.stringify(r));
		  //alert(r.length);
		  for (var i = 0; i < r.length; i++) {
				  $("#manufac_id").append(
						'<option value='+ r[i].id + '>' + r[i].manufacturersName + '</option>' 
				  );
		  }
		}, function() {
			
	});
}

//提交
function submitu() {
    //验证
    var id = seqId;
    var good_name = $("#good_name").val();
    var drug_retail_price = $("#drug_retail_price").val();
    var discount = $("#discount").val();
    var chemistry_name = $("#chemistry_name").val();
    var pharm_spec = $("#pharm_spec").val();
    var company = $("#company").val();
    //var drugs_typeone = $("#drugs_typeone option:selected").text();//动态赋值获取数据
    //var drugs_typetwo = $("#drugs_typetwo option:selected").text();
    var drugs_typeone = $("#drugs_typeone").val();
    var drugs_typetwo = $("#drugs_typetwo").val();
    var packing_num = $("#packing_num").val();
    var company_min = $("#company_min").val();
    var content_coe = $("#content_coe").val();
    var content_unit = $("#content_unit").val();
    var sta_item = $("#sta_item").val();
    var contry_code = $("#contry_code").val();
    var pharm_class = $("#pharm_class").val();
    var pharm_dos = $("#pharm_dos").val();
    var ant_gra = $("#ant_gra").val();
    var psy_drugs = $("#psy_drugs").val();
    var minnums = $("#minnums").val();
    var mingj = $("#mingj").val();
    var maxnums = $("#maxnums").val();
    var maxgj = $("#maxgj").val();
    var drug_identify = $("#drug_identify").val();
    var comments = $("#comments").val();
    var drug_total_num = $("#drug_total_num").val();
    var packing_unit = $("#packing_unit").val();
    var manu_id = $("#manu_id").val();
    var manufac_id = $("#manufac_id").val();
    var drugs_type = $("#drugs_type").val();
    var classify = $("#classify").val();
    //alert(manu_id);
    if (good_name == "" || good_name == null) {
        layer.alert('请填写药品名称');
        return false;
    } 
    if (manufac_id == "" || manufac_id == null) {
        layer.alert('请选择生产商');
        return false;
    } 
    
    if (drug_retail_price == "" || drug_retail_price == null) {
        layer.alert('请填写药品零单价' );
        return false;
    }
    /* var seqId = $("#seqId").val();
    if (!YzCode(seqId, goodscode)) {
        layer.alert('商品编号已存在！' );
        return false;
    } */
	/* if (discount == "" || discount == null) {
        layer.alert('请填写折扣' );
        return false;
    } 
	if (chemistry_name == "" || chemistry_name == null) {
        layer.alert('请填写药品化学名称' );
        return false;
    }  */
	if (pharm_spec == "" || pharm_spec == null) {
        layer.alert('请填写药品规格' );
        return false;
    } 
	if (company == "" || company == null) {
        layer.alert('请填写单位' );
        return false;
    } 
	if (drugs_typeone == "" || drugs_typeone == null) {
        layer.alert('请选择一级分类' );
        return false;
    } 
	if (drugs_typetwo == "" || drugs_typetwo == null) {
        layer.alert('请选择二级分类' );
        return false;
    } 
	if (packing_num == "" || packing_num == null) {
        layer.alert('请填写包装数量' );
        return false;
    } 
	if (company_min == "" || company_min == null) {
        layer.alert('请填写最小单位' );
        return false;
    } 
	if (content_coe == "" || content_coe == null) {
        layer.alert('请填写含量系数' );
        return false;
    } 
	if (content_unit == "" || content_unit == null) {
        layer.alert('请填写含量单位' );
        return false;
    } 
	/* if (sta_item == "" || sta_item == null) {
        layer.alert('请填写统计大项目' );
        return false;
    }  */
	if (contry_code == "" || contry_code == null) {
        layer.alert('请填写国家编码' );
        return false;
    } 
	/* if (pharm_class == "" || pharm_class == null) {
        layer.alert('请填写药理分类' );
        return false;
    }  */
	if (pharm_dos == "" || pharm_dos == null) {
        layer.alert('请填写药品剂型' );
        return false;
    } 
	/* if (ant_gra == "" || ant_gra == null) {
        layer.alert('请填写抗生素级别' );
        return false;
    } 
	if (psy_drugs == "" || psy_drugs == null) {
        layer.alert('请选择是否是精神药物' );
        return false;
    } 
	if (minnums == "" || minnums == null) {
        layer.alert('请填写下限值' );
        return false;
    }  */
	/* if (mingj == "" || mingj == null) {
        layer.alert('请选择下限提醒' );
        return false;
    }  */
	/* if (maxnums == "" || maxnums == null) {
        layer.alert('请填写上限值' );
        return false;
    }  */
	/* if (maxgj == "" || maxgj == null) {
        layer.alert('请选择上限提醒' );
        return false;
    }  */
	/* if (drug_identify == "" || drug_identify == null) {
        layer.alert('请填写药品标识' );
        return false;
    } 
	if (comments == "" || comments == null) {
        layer.alert('请填写药品介绍' );
        return false;
    }  */
	if (packing_unit == "" || packing_unit == null) {
        layer.alert('请填写包装单位' );
        return false;
    } 
	if (manu_id == "" || manu_id == null) {
        layer.alert('请选择供应商' );
        return false;
    } 
	if (classify == "" || classify == null) {
        layer.alert('请选择分类' );
        return false;
    } 
 	
    var param = {
    	id : id,
    	good_name: good_name,
    	drug_retail_price: drug_retail_price,
        discount: discount,
        chemistry_name: chemistry_name,
        pharm_spec: pharm_spec,
        company: company,
        drugs_typeone : drugs_typeone,
        drugs_typetwo : drugs_typetwo,
        packing_num : packing_num,
        company_min : company_min,
        content_coe : content_coe,
        content_unit : content_unit,
        sta_item : sta_item,
        contry_code : contry_code,
        pharm_class : pharm_class,
        pharm_dos : pharm_dos,
        ant_gra : ant_gra,
        psy_drugs : psy_drugs,
        drug_identify : drug_identify,
        comments : comments,
        minnums : minnums,
        maxnums : maxnums,
        mingj : mingj,
        maxgj : maxgj,
        packing_unit : packing_unit,
        manu_id : manu_id,
        manufac_id : manufac_id,
        drugs_type : drugs_type,
        classify : classify
    };
    var url = contextPath + '/HUDH_YkzzAct/insertDrugsInfor.act?';
    $.axseSubmit(url, param,
    function() {},
    function(r) {
    	//alert(JSON.stringify(r));
        if (r.retState == "0") {
            layer.alert('保存成功', {
                end: function() {
                	parent.location.reload(); //刷新父页面
	                var frameindex = parent.layer.getFrameIndex(window.name);
	                parent.layer.close(frameindex); //再执行关闭
                }
            });
        } else {
            layer.alert('添加失败：' + r.retMsrg, {//后台抛出的异常信息在前台展示
            	end: function() {
                	parent.location.reload(); //刷新父页面
	                var frameindex = parent.layer.getFrameIndex(window.name);
	                parent.layer.close(frameindex); //再执行关闭
                }
            });
        }
    },
    function() {
        layer.alert('保存失败' );
    });
}
</script>
</html>
