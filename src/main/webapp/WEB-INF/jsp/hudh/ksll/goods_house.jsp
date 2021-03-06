<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String sshouse = request.getParameter("sshouse");
	if(sshouse == null ){
		sshouse = "";
	}
	String type = request.getParameter("type");//0 出库（隐藏添加商品按钮） 1 入库
	if(type == null ){
		type = "1";
	}
	String type1 = request.getParameter("type1");//0 出库（隐藏添加商品按钮） 1 入库
	if(type1 == null ){
		type1 = "1";
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>仓库</title>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
</head>
<style type="text/css">
.buttonBar .aBtn_big{display:inline-block;padding: 0 15px;height: 28px;border: solid 1px #0e7cc9;color: #0e7cc9;border-radius: 15px;line-height: 28px;width: 80px;text-align: center;}
.buttonBar  .aBtn_big:hover{color:#fff;background: #0e7cc9;}
.buttonBar .aBtn_big2{display:inline-block;padding: 0 15px;height: 28px;border: solid 1px #0e7cc9;color: #0e7cc9;border-radius: 15px;line-height: 28px;width: 88px;text-align: center;}
.buttonBar  .aBtn_big2:hover{color:#fff;background: #0e7cc9;}
.searchWrap{
	padding-top:35px;
}
</style>
<body>
<div id="container">
    <div class="main">
            <div class="listBd">
                <div class="tableBox">
                    <table id="table" class="table-striped table-condensed table-bordered" data-height="450" >
                    </table>
                </div>
            </div>
        <!--查询条件-->
        <div class="searchWrap">
            <div class="cornerBox">查询条件</div>
            <table style="width: 100%;">
           		<tr>
           		   	<td style="text-align:right;"> 
	           			仓库：
	           		</td>
           			<td style="text-align:left;"> 
	           			<select name="sshouse" id="sshouse" style="width: 120px"></select>
	           		</td>
           			<td style="text-align:right;"> 
	           			一级类别：
	           		</td>
           			<td style="text-align:left;"> 
	           			<select class="select2"  name="basetype" id="basetype" style="width: 120px">
	           			<option value="">请选择</option>
	           		    </select>
	           		</td>
	           		<td style="text-align:right;"> 
	           			二级类别：
	           		</td>
	           		<td style="text-align:left;"> 
	           			<select class="select2"  name="nexttype" id="nexttype" style="width: 120px">
                       		 <option value="">请选择</option>
                	  	 </select>
	           		</td>
	           		<td style="text-align:right;"> 
	           			模糊查询：
	           		</td>
	           		<td style="text-align:left;"> 
	           			<input type="text" id="queryInput" class="searchInput" placeholder="请输入商品名称/规格  等">
	           		</td>
	           		<td style="text-align:right;"> 
	           			 <div class="btnBar">
	           			  	<a href="javascript:void(0);" class="kqdsCommonBtn"  id="insert" onclick="insertTodetail();">添加到科室领料明细</a>
	           			  	<a href="javascript:void(0);" class="kqdsCommonBtn" id="add" onclick="add();">添加商品</a>
			                <a href="javascript:void(0);" class="kqdsCommonBtn" id="clear">清空</a>
			                <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
			            </div>
	           		</td>
           		</tr> 
            </table>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script> 
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var sshouse = '<%=sshouse%>';  /* 所选仓库 */
var type = '<%=type%>';
var $table = $('#table');
var pageurl = contextPath + '/KQDS_Ck_Goods_DetailAct/selectList.act';
var onclickrow = ""; //选中行对象
var number = 1;
var type1 = '<%=type1%>';
var pageurl2;
$(function() {
// 2020/04/06 licc
// 	初始化仓库，取消仓库传参，实现仓库--类别三级联动选择方式
	getHouseSelect("sshouse");
	
 	if (sshouse) { // 如果仓库有值，再请求    2020/04/02 lutian
        getBaseTypeSelect('basetype', sshouse);
    }
    $('#sshouse').change(function() {
        if ($(this).val()) { // 如果仓库有值，联动一级类别
         getBaseTypeSelect('basetype', this.value);
        }
    });
    
// 	按钮隐藏
	if(type=="0"){
		$("#add").hide();
	}
	pageurl2 = pageurl + "?type="+type1;
    initTable(); //2020/04/06 licc 加载表格所有仓库下的数据
    //查询
    $('#query').on('click',
    function() {
        var queryInput = $("#queryInput").val();
        var sshouse=$("#sshouse").val();
        pageurl1 = pageurl+ '?type='+ type1 +'&&sshouse=' + sshouse + '&queryInput=' + queryInput+"&basetype="+$("#basetype").val()+"&nexttype="+$("#nexttype").val();
        $('#table').bootstrapTable('refresh', {
            'url': pageurl1
        });
    });
    //计算主体的宽度
    setHeight();
    $(window).resize(function() {
        setHeight();
    });
});
$('#basetype').change(function() {
	if($(this).val()){ // 如果一级有值，再请求
		getNextTypeSelect('nexttype',this.value);
	}
});
//清空
$('#clear').on('click',
function() {
	 $("#sshouse").val("");
	 $("#basetype").html("<option value=''>请选择</option>");
	 $("#nexttype").html("<option value=''>请选择</option>");
    $("#queryInput").val("");
});
function refresh() {
    $table.bootstrapTable('refresh', {
        'url': pageurl
    });
}
//加载表格
function initTable() {
    //加载表格
    $table.bootstrapTable({   
        url: pageurl2,
        dataType: "json",
        contentType : "application/x-www-form-urlencoded",   //必须有
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList : [10, 15, 20, 25],//可以选择每页大小
      //在表格底部显示分页工具栏 
        singleSelect: false,
        paginationShowPageGo: true,
        cache: false,
        striped: true,
        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
        onLoadSuccess: function(data) {
        },
        //服务端处理分页
        columns: [
		{
		    field: ' ',
		    checkbox: true,
		    formatter:function(value){
            	return value;
            }
		},
        {
            title : '序号',
            align: "center",
            width: 40,
            formatter: function (value, row, index) {
            	/* return index + 1; */
            	var pageSize = $table.bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                var pageNumber = $table.bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
            }
           },
//         {
//              title: '',
//              align: "center",
//              field: 'seqId',
// 		     visible: false,
//              formatter: function (value, row, index) {
//             	 return '<span>'+value+'</span>';
//              }
//          },
       {
             title: '所属仓库',
             field: 'sshousename',
             align: 'center',
             /* sortable: true, */
             formatter:function(value, row, index){
//             	 console.log("row..."+JSON.stringify(row));
                return '<span id = '+row.sshouse+'>'+value+'</span>';
              }
           },
       {
            title: '一级类别',
            field: 'firsttype',
            align: 'center',
            /* sortable: true, */
            formatter:function(value, row, index){
            	return '<span id = '+row.firsttypeid+'>'+value+'</span>';
            }
        },
        {
            title: '二级类别',
            field: 'goodstypename',
            align: 'center',
            /* sortable: true, */
            formatter:function(value, row, index){
            	return '<span id = '+row.goodstype+'>'+value+'</span>';
            }
        },
        {
            title: '商品编号',
            field: 'goodscode',
            align: 'center',
            /* sortable: true, */
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '商品名称',
            field: 'goodsname',
            align: 'center',
            formatter: function(value, row, index) {
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '商品规格',
            field: 'goodsnorms',
            align: 'center',
            /* sortable: true, */
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '库存数量',
            field: 'nums',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '单位',
            field: 'goodsunit',
            align: 'center',
            /* sortable: true, */
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '商品单价',
            field: 'goodsprice',
            align: 'center',
            visible : false,//隐藏该列 
            /* sortable: true, */
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        }
        ]
    }).on('dbl-click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrow = $('#table').bootstrapTable('getData')[index];
//         insertTodetail2();
    });
}

//多选添加商品明细
function insertTodetail() {
	var detail=$('#table').bootstrapTable('getAllSelections');	
// 	console.log(JSON.stringify(detail)+"---------detail");
	if(detail.length<=0){
		layer.alert("请选择商品");return;
	}
	var codeArr=[];//添加商品的code
	var storedcode=[];//已经存在的商品的code
	var flag = true;
// 	//添加商品的code
	for(var i=0;i<detail.length;i++){	
		codeArr.push(detail[i].goodscode);				
	};
	//已经存在的商品的code
	parent.$('#table').find('tbody').find('tr').each(function() {			 
           var goodCode=$(this).find("td").eq(5).find("span").text();
           storedcode.push(goodCode);
    });	
	if(storedcode.length>0){		
		for (var i=0; i<detail.length; i++) { 
		     for (var j=0; j<storedcode.length; j++) {  
		    		 if (detail[i].goodscode == storedcode[j]) {  
				         	detail.splice(i, 1);              		
				      }	 	                 
		     }  
		 } 
		flag=true;
	}

	var irowArr=Object.keys(detail);
	var newIrowArr=[];
// 	console.log(irowArr+"----------detailindex");
	for(var i=0;i<irowArr.length;i++){
		newIrow=Number(irowArr[i])+1+storedcode.length;
		newIrowArr.push(newIrow);	
// 		for(var j=0;j<detail.length;j++){
// 			detail[j].irow=newIrowArr[i];
// 		}		
	}
// 	console.log(newIrowArr+"------newIrowArr");
	if(flag) {		
		var params=[];
		for(var i=0;i<detail.length;i++){
			for(var j=0;j<newIrowArr.length;j++){
				detail[i].irow=newIrowArr[i];
			}			
			var param = {
					goodsid: detail[i].seqId,
					goodsprice: detail[i].goodsprice,
					kcnums: detail[i].nums,
					firsttypeid : detail[i].firsttypeid,
					firsttype : detail[i].firsttype,
					goodsname: detail[i].goodsname,
					goodscode: detail[i].goodscode,
					goodstype: detail[i].goodstype,
					goodstypename: detail[i].goodstypename,
					sshouse: detail[i].sshouse,
					sshousename: detail[i].sshousename,
					goodsnorms: detail[i].goodsnorms,
					supplyinformation: detail[i].supplyinformation,
					goodsunit: detail[i].goodsunit,
		        	irows:detail[i].irow
		    };
			params.push(param);			 
		}		
// 		console.log(JSON.stringify(params)+"------------------rrrr");
// 		    调用父页面 增加商品明细
		    parent.addDoodsDetail(params);
		    layer.alert('已添加商品！'  );
	}else {
		layer.alert("请勿重复添加");return;
	}
}

//双击获取对象 增加商品明细
function insertTodetail2() {
	var flag = true;
	//验证当前商品是否已经添加
	parent.$('#table').find('tbody').each(function() {
        $(this).find('tr').each(function() {
            $(this).find('td').each(function() {
                if ($(this).index() == 4) {  //对应ksll_goods.jsp列表中商品编号的下标
                    if(onclickrow.goodscode == $(this).find("span").html()) {
                    	flag = false; return false; //该商品已经添加
                    }
                }
            });
        });
    });
	if(flag) {
		var param = {
		        goodsid: onclickrow.seqId,
		        goodsprice: onclickrow.goodsprice,
		        kcnums: onclickrow.nums,
		        firsttype : onclickrow.firsttype,
		        goodsname: onclickrow.goodsname,
		        goodscode: onclickrow.goodscode,
		        goodstype: onclickrow.goodstype,
		        goodstypename: onclickrow.goodstypename,
		        sshouse: onclickrow.sshouse,
		        sshousename: onclickrow.sshousename,
		        goodsnorms: onclickrow.goodsnorms,
		        supplyinformation: onclickrow.supplyinformation,
		        goodsunit: onclickrow.goodsunit,
		        goodsprice: onclickrow.goodsprice
		    };
		    //调用父页面 增加商品明细
		    parent.addDoodsDetail(param);
		    layer.alert('已添加商品！'  );
	}else {
		layer.alert("请勿重复添加");return;
	}
    
}
//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height();
    var serachH = $('.searchWrap').outerHeight();
    $('.extraBar .extraBd').height(baseHeight - 65);
    $('#table').bootstrapTable('resetView', {
        height: baseHeight - serachH - 5
    });

}
function add(){
	layer.open({
        type: 2,
        title: '添加商品',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['520px', '360px'],
        content: contextPath + '/KQDS_Ck_GoodsAct/toSave.act'
    });
}
</script>
</html>
