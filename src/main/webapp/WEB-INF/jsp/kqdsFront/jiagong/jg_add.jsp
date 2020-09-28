<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- ## 该页面样式已经优化过，虽然样式代码比较乱，但是目前兼容各种显示器尺寸，尽量不要再调整样式   yangsen 17-4-22 ## -->
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String usercode = request.getParameter("usercode");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>添加加工单</title>
<!-- jd_index.jsp页面进入 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<style>
table td span{display:inline-block;vertical-align:middle;white-space: nowrap;overflow: hidden;text-overflow:ellipsis; }
table td span.time{display:inline-block;width: 140px;white-space: nowrap;overflow: hidden;text-overflow:ellipsis; }
table td span.name{display:inline-block;width: 80px;white-space: nowrap;overflow: hidden;text-overflow:ellipsis; }
input[type=number] {
    padding-left:10px;
    width: 212px;
    height: 28px;
    line-height: 28px;
    border: solid 1px #e5e5e5;
    border-radius: 3px;
    -webkit-transition: all .3s;
    transition: all .3s;
}
.listWrap .formBox{
	margin:0;
}
.costList .costListBd{
	padding-bottom:0;
}
 #container{padding: 5px;}
.listWrap{
	background:#fff;
}
table>td,table>th{
	border:none;
}
h4{
	margin:0;
}
.tableBox{
	border:1px solid #ddd;
}
.buttonBar{
	margin:0;
}

#listTable a{
	height:29px;
	line-height:29px;
}
.kv > label{
	width:auto;
}	
.searchWrap .kv > label{
	margin-bottom:0;
}
.searchWrap{
	padding-top:34px;
}
</style>
</head>
<body>
<div id="container">
    <div class="main addWrap" style="padding-right:0px;">
        <div class="costList" style="margin-right:0px;width:20%;">
        
            <div class="costListHd">
                <h4>加工项目列表</h4>
            </div>
            <div class="costListBd">
                <div class="searchBox">
                    <input type="text" placeholder="搜索" name="search" id="search" class="searchInput" style="width: 192px;">
		                <a href="javascript:void(0);" id="infosearch" class="hc-icon icon16 search-icon searchBtn" onclick="zTreeInit()"></a>
  				</div>
                <div class="ztree" id="ztree" style="overflow:auto;"></div>
            </div>
        </div>
        <div class="listWrap" style="width:79%;">
	        <div class="titleDiv">
	 			<div class="title">加工项目明细</div>       
	        </div>
            <!-- <div class="listHd"><span class="hc-icon icon20 home-icon"></span>加工项目明细</div> -->
            <div class="listBd">
				<div class="tableBox" style="width: 100%;overflow: scroll;">
                    <table id="listTable" style="width: 100%;text-align: center;" >
						<thead style="background: #00A6C0 ;color:white ; ">
							<tr>
								<th style="text-align: center; vertical-align: middle;width:50px;height:30px;">操作</th>
								<th style="text-align: center; vertical-align: middle;">项目名称</th>
								<th style="text-align: center; vertical-align: middle;">项目分类</th>
								<th style="text-align: center; vertical-align: middle;">加工厂</th>
								<th style="text-align: center; vertical-align: middle;">数量</th>
								<th style="text-align: center; vertical-align: middle;width:8%;">单位</th>
								<th style="text-align: center; vertical-align: middle;">牙位</th>
								<th style="text-align: center; vertical-align: middle;">色号</th>
								<th style="text-align: center; vertical-align: middle;">制作要求</th>
							</tr>
						</thead>
						<tbody style="background-color: #F0FFFF;text-align: center;"></tbody>
					</table>
                </div>
				
                <div style="height:30px;"> <!-- 这里30px固定值，页面设置高度时减掉  -->
                	<label><input type="radio" name="type" checked value="试戴">试戴</label>
                    <label><input type="radio" name="type" value="返工">返工</label>
                    <label><input type="radio" name="type" value="单冠">单冠</label>
                    <label><input type="radio" name="type" value="联冠">联冠</label>
                    <label><input type="radio" name="type" value="加急">加急</label>
                    <a href="javascript:void(0);" style="margin:5px 0 0 20px;float:right;" class="kqdsCommonBtn" onclick="baocun()">保存</a>
               </div>
               
               <div id="ztyqDiv" class="formBox">
	                <div class="kv">
	                    <label>设计要求：</label>
	                    <div class="kv-v">
	                        <textarea id="yaoqiu" rows="3" style="font-size:14px;height:60px;width:400px;line-height: 15px;"></textarea>
	                    </div>
	                </div>
                </div>
                <!--查询条件-->
			    <div class="searchWrap">
			        <div class="cornerBox">基本资料</div>
			        <div class="btnBar">
			        </div>
			        <div class="formBox">
			        	<div class="kv">
			                <label>姓名</label>
			                <div class="kv-v">
			                	<input type="text" id="username" readonly>
			                </div>
			            </div>
			            <div class="kv">
			                <label>患者编号</label>
			                <div class="kv-v">
			                	<input type="text" id="usercode" readonly style="width:110px;">
			                </div>
			            </div>
			            <div class="kv">
			                <label>性别</label>
			                <div class="kv-v">
			                	<input type="text" id="sex" readonly/>
			                </div>
			            </div>
			            <div class="kv">
			                <label>出生年月</label>
			                <div class="kv-v">
			                	<input type="text" id="birthday" readonly style="width:110px;">
			                </div>
			            </div>
			            <div class="kv">
			                <label>手机</label>
			                <div class="kv-v">
			                	<input type="text" id="phonenumber1" readonly style="width:110px;">
			                </div>
			            </div>
			        </div>
			    </div>
            </div>
        </div>
    </div>
    
</div>
</body>
<script type="text/javascript">
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var contextPath = '<%=contextPath%>';
var usercode = '<%=usercode%>'; //患者编号
var sheetno = ''; //加工单号
var $table = $('#listTable');
var pageurl = '<%=contextPath%>/KQDS_OUTPROCESSING_SHEET_DETAILAct/getListNoPage.act';
var savesheeturl = '<%=contextPath%>/KQDS_OutProcessingSheetAct/insertOrUpdate.act';
var onclickrow = ""; //选中行对象
var tdindex = 1000; //表格里各类标签的id 动态添加时使用 数字随便从几开始
var tdsum = 0; //表格行数
var jgcchoose = "";
$(function() {
    //查询患者详情
    var baseinfo = getBaseInfoByUserCode(usercode);
	if(baseinfo){
		$('#username').val(baseinfo.username);
        $('#usercode').val(baseinfo.usercode);
        $('#sex').val(baseinfo.sex);
        $('#birthday').val(baseinfo.birthday);
        $('#phonenumber1').val(baseinfo.phonenumber1);
	}

    zTreeInit(); //加载树
    //计算主体的宽度
    setHeight();
    $(window).resize(function() {
        setHeight();
    });

});

//计算左侧表格高度保证一屏展示
function setHeight() {
    /*计算高度通过手工调整  不要随意修改   yangsen  17-4-22*/
    var baseHeight = $(window).height();
    var serachH = $('.searchWrap').outerHeight();

    var listHd = $('.listHd').outerHeight();
    var ztyqDivHeight = $("#ztyqDiv").height();

   
    $(".tableBox").height($(window).outerHeight()-$(".titleDiv").outerHeight()-$("#ztyqDiv").outerHeight()-$(".searchWrap").outerHeight()-50);

    // 设置右侧高度
    $(".costListBd").height($(window).outerHeight()-$(".costListHd").outerHeight()-18);
   

    $("#ztree").height($('.costListBd').height() - 45);
    $("#ztree").width($(".costListBd").outerWidth() - 15);
}

//加载右侧加工项目树
function zTreeInit() {
    var search = $("#search").val();
    var url = '<%=contextPath %>/YZDictJGAct/getSelectJgTreeAsync.act?search=' + search + '&isAdd=1';
    var setting = {
        view: {
            showIcon: false // 去掉图标
        },
        async: {
            enable: true,
            url: url,
            autoParam: ["id", "name=n", "level=lv"],
            otherParam: {
                "otherParam": "zTreeAsyncTest"
            },
            dataFilter: ajaxDataFilter,
            type: "get"
        }
    };
    setting['callback'] = {
        onClick: onclick
    };
    $.fn.zTree.init($("#ztree"), setting);
}

// 获取所在树的加工厂编号
function getFactoryId(treeNode){
	if(treeNode.level == 0){
		return treeNode.id;
	}else{
		return getFactoryId(treeNode.getParentNode());
	}
}

function onclick(e, treeId, treeNode) {
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj("ztree");
        zTree.expandNode(treeNode);
    } else {
        //防止当前 树的父节点下面为空 也去请求后台，此时会报错  ----特殊处理
        var level = 0;
        treeNode.check_Focus = true;
        treeNode.checked = true;
        var treeObj = $.fn.zTree.getZTreeObj("ztree");
        var sNodes = treeObj.getSelectedNodes();
        var parentNode2 = treeNode.getParentNode();
        var parentNode1 = parentNode2.getParentNode();
        if (parentNode1 == null) { //分类
            return false;
        }
        if (sNodes.length > 0) {
            level = sNodes[0].level;
        }
        if (level == 0) {
            return;
        }
        treeObj.updateNode(treeNode);
        
     	// 获取所在树的加工厂编号
        var factoryId = getFactoryId(treeNode);
     	var jgxmList = $('#listTable tbody').find('tr');
     	
        if (jgcchoose == "" || jgxmList.length == 0) {
            jgcchoose = factoryId;
        } else {
            if (jgcchoose != factoryId) {
                layer.alert('请选择同一加工厂的项目！'  );
                return false;
            }
        }
        
        var detailurl = '<%=contextPath%>/KQDS_OutProcessingAct/getOneByBh.act?factoryId='+factoryId+'&wjgxmbh=' + treeNode.id;
        $.axse(detailurl, null,
        function(data) {
            if (data.retState == "0") {
                tdindex++;
                var tablehtml = "";
                var tabledata = data.data;
                tablehtml += "<tr style='border: 1px solid #ddd;'>";
                //删除按钮0
                tablehtml += '<td style="height:30px;display: inline-block;width: 50px;"><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this)"><span style="color:red;">删除</span></a></td>';
                //项目编号1
                tablehtml += '<td style="display:none;">' + tabledata.wjgxmbh + '</td>';
                //项目名称2
                tablehtml += '<td style=""><span style=" text-align:center;" class="time" title="' + tabledata.wjgmc + '">' + tabledata.wjgmc + '</span></td>';
                //项目分类3
                tablehtml += '<td style=""><span style=" text-align:center;" class="time" title="' + tabledata.typename + '">' + tabledata.typename + '</span></td>';
                //加工厂4
                tablehtml += '<td style=""><span style=" text-align:center;" class="time" title="' + tabledata.factoryname + '">' + tabledata.factoryname + '</span></td>';
                //数量5
                tablehtml += '<td style=""><input type="number" min="1" style="width:70px; text-align:center;" onfocus="this.select()" name="nums" id="nums' + tdindex + '" value=1></td>';
                //单位6
                tablehtml += '<td style="display: inline-block;width: 50px;">' + tabledata.dw + '</td>';
                //牙位7
                tablehtml += '<td style=""><input type="text" style="width:150px; text-align:center;" onfocus="this.select()" name="toothbit" id="toothbit' + tdindex + '" value=""></td>';
                //色号8
                tablehtml += '<td style=""><input type="text" style="width:150px; text-align:center;" onfocus="this.select()" name="colorsize" id="colorsize' + tdindex + '" value=""></td>';
                //制作要求9
                tablehtml += '<td style=""><input type="text" style="width:150px; text-align:center;" onfocus="this.select()" name="zzyq" id="zzyq' + tdindex + '" value=""></td>';
                //单价
                tablehtml += '<td style="display:none;">' + tabledata.dj + '</td>';
                tablehtml += "</tr>";

                $table.find('tbody').append(tablehtml);
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }
}

//删除行
function deltr(obj) {
    var i = obj.parentNode.parentNode.rowIndex;
    document.getElementById('listTable').deleteRow(i);
}

//点击保存
function baocun() {
	var jgxmList = $('#listTable tbody').find('tr');
	if(jgxmList.length == 0){
		layer.alert('请选择加工项目' );
        return false;
	}
	
    var totalmoney = $("#totalcost").val();
    var type = $("input[name=type]:checked").val();
    var yaoqiu = $("#yaoqiu").val();
    if (type == "" || type == null) {
        layer.alert('请选择加工单种类' );
        return false;
    }

    //循环获取表格中项目
    var list = [];
    $('#listTable').find('tbody').each(function() {
        $(this).find('tr').each(function() {
            var param = {};
            $(this).find('td').each(function() {

                if ($(this).index() == 1) {
                    //项目编号
                    param.wjgxmbh = $(this).text();
                } else if ($(this).index() == 2) {
                    //项目名称span
                    param.outprocessingname = $(this).find("span").html();
                } else if ($(this).index() == 4) {

                }else if ($(this).index() == 5) {
                    //数量 input
                    if(judgeSignNum($(this).find("input").val())){
                    	 param.nums = $(this).find("input").val();
                    }
                } else if ($(this).index() == 6) {
                    //单位 
                    param.utils = $(this).text();
                } else if ($(this).index() == 7) {
                    //牙位 input
                    param.toothbit = $(this).find("input").val();
                } else if ($(this).index() == 8) {
                    //色号 input
                    param.colorsize = $(this).find("input").val();
                } else if ($(this).index() == 9) {
                    //制作要求 input
                    param.zzyq = $(this).find("input").val();
                }else if ($(this).index() == 10) {
                    //单价
                    if(judgeSignFloatNum($(this).text())){
                    	param.unitprice = $(this).text();
                    }
                    if(judgeSignFloatNum(param.unitprice) && judgeSignNum(param.nums)){
                    	param.subtotal = param.unitprice * param.nums;
                    }
                }
            });
            list.push(param);
        });
    });
    
    var data = JSON.stringify(list);
    data = encodeURIComponent(data); //编码 参数里有特殊符号时需要编码
    //保存 
    var param = {
        usercode: usercode,
        type: type,
        yaoqiu: yaoqiu,
        totalmoney: totalmoney,
        processingfactory: jgcchoose,
        params: data
    };
    var msg;
    $.axseSubmit(savesheeturl, param,
    function() {
        msg = layer.msg('加载中', {
            icon: 16
        });
    },
    function(r) {
        layer.close(msg);
        if (r.retState == "0") {
            layer.alert('保存成功', {
                  
                end: function() {
                    parent.layer.close(frameindex);
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


</script>
</html>
