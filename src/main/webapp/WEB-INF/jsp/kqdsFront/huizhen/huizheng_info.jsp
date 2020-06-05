<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>会诊信息</title>
<!-- 整个系统就一个页面 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/huizheng_info.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>

<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tabledit.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/language/zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
</head>
<style>
	#table2{ 
		
	} 
	#table2 td { 
		padding:0;
	} 
	#table2 tr.lastrow td { 
		
	} 
	#table2 tr td.lastCol { 
		
	} 
.editable-buttons {
    display: inline-block;
    vertical-align: top;
    margin-left: 0px;
    zoom: 1;
    float:left;
}
textarea.form-control {
    height: 34px;
}
.table-striped > tbody > tr {
    background: #eef9fe;
}
.table-striped > tbody > tr:hover {
   background: #F5F5F5;
}
#container{
	/* padding:0 15px; */
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
.fixed-table-body{
	overflow-x:visible;
}
html{
	overflow:hidden;
}
</style> 

<body>
<div id="container">
    <div class="infoHd">
        <ul class="tab">
            <li link="children">儿童</li>
            <li link="adult" class="current">成人</li>
        </ul>
        <span class="text" id="showtext">会诊信息</span>
    </div>
    <div class="infoBd">
        <div class="tabContent">
            <!--成人-->
            <div class="toothBox adult" style="min-width:590px;">
                <ul class="upYa" >
                	<li><input type="checkbox" id="lefttop" style="margin-top:85px;" /></li>
                    <li><span class="yaIcon le8"></span><span class="num" name="adultupYa1">8</span></li>
                    <li><span class="yaIcon le7"></span><span class="num" name="adultupYa1">7</span></li>
                    <li><span class="yaIcon le6"></span><span class="num" name="adultupYa1">6</span></li>
                    <li><span class="yaIcon le5"></span><span class="num" name="adultupYa1">5</span></li>
                    <li><span class="yaIcon le4"></span><span class="num" name="adultupYa1">4</span></li>
                    <li><span class="yaIcon le3"></span><span class="num" name="adultupYa1">3</span></li>
                    <li><span class="yaIcon le2"></span><span class="num" name="adultupYa1">2</span></li>
                    <li><span class="yaIcon le1"></span><span class="num" name="adultupYa1">1</span></li>
                    
                    <li><span class="yaIcon rg1"></span><span class="num" name="adultupYa2">1</span></li>
                    <li><span class="yaIcon rg2"></span><span class="num" name="adultupYa2">2</span></li>
                    <li><span class="yaIcon rg3"></span><span class="num" name="adultupYa2">3</span></li>
                    <li><span class="yaIcon rg4"></span><span class="num" name="adultupYa2">4</span></li>
                    <li><span class="yaIcon rg5"></span><span class="num" name="adultupYa2">5</span></li>
                    <li><span class="yaIcon rg6"></span><span class="num" name="adultupYa2">6</span></li>
                    <li><span class="yaIcon rg7"></span><span class="num" name="adultupYa2">7</span></li>
                    <li><span class="yaIcon rg8"></span><span class="num" name="adultupYa2">8</span></li>
                    <li><input type="checkbox" id="righttop" style="margin-top:85px;"/></li>
                </ul>
                <div class="line">
                    <span class="left">右</span>
                    <span class="right">左</span>
                </div>
                <ul class="downYa">
                	<li><input type="checkbox" id="leftdown" style="margin-bottom:20px;"/></li>
                    <li><span class="num" name="adultdownYa1">8</span><span class="yaIcon le8"></span></li>
                    <li><span class="num" name="adultdownYa1">7</span><span class="yaIcon le7"></span></li>
                    <li><span class="num" name="adultdownYa1">6</span><span class="yaIcon le6"></span></li>
                    <li><span class="num" name="adultdownYa1">5</span><span class="yaIcon le5"></span></li>
                    <li><span class="num" name="adultdownYa1">4</span><span class="yaIcon le4"></span></li>
                    <li><span class="num" name="adultdownYa1">3</span><span class="yaIcon le3"></span></li>
                    <li><span class="num" name="adultdownYa1">2</span><span class="yaIcon le2"></span></li>
                    <li><span class="num" name="adultdownYa1">1</span><span class="yaIcon le1"></span></li>
                                                                      
                    <li><span class="num" name="adultdownYa2">1</span><span class="yaIcon rg1"></span></li>
                    <li><span class="num" name="adultdownYa2">2</span><span class="yaIcon rg2"></span></li>
                    <li><span class="num" name="adultdownYa2">3</span><span class="yaIcon rg3"></span></li>
                    <li><span class="num" name="adultdownYa2">4</span><span class="yaIcon rg4"></span></li>
                    <li><span class="num" name="adultdownYa2">5</span><span class="yaIcon rg5"></span></li>
                    <li><span class="num" name="adultdownYa2">6</span><span class="yaIcon rg6"></span></li>
                    <li><span class="num" name="adultdownYa2">7</span><span class="yaIcon rg7"></span></li>
                    <li><span class="num" name="adultdownYa2">8</span><span class="yaIcon rg8"></span></li>
                    <li><input type="checkbox" id="rightdown" style="margin-bottom:20px;"/></li>
                </ul>
            </div>
            
            <!--儿童-->
            <div class="toothBox children hide">
                <ul class="upYa">
                	<li><input type="checkbox" id="childlefttop" style="margin-top:85px;" /></li>
                    <li><span class="yaIcon le5"></span><span class="num" name="childrenupYa1">5</span></li>
                    <li><span class="yaIcon le4"></span><span class="num" name="childrenupYa1">4</span></li>
                    <li><span class="yaIcon le3"></span><span class="num" name="childrenupYa1">3</span></li>
                    <li><span class="yaIcon le2"></span><span class="num" name="childrenupYa1">2</span></li>
                    <li><span class="yaIcon le1"></span><span class="num" name="childrenupYa1">1</span></li>
                    
                    <li><span class="yaIcon rg1"></span><span class="num" name="childrenupYa2">1</span></li>
                    <li><span class="yaIcon rg2"></span><span class="num" name="childrenupYa2">2</span></li>
                    <li><span class="yaIcon rg3"></span><span class="num" name="childrenupYa2">3</span></li>
                    <li><span class="yaIcon rg4"></span><span class="num" name="childrenupYa2">4</span></li>
                    <li><span class="yaIcon rg5"></span><span class="num" name="childrenupYa2">5</span></li>
                    <li><input type="checkbox" id="childrighttop" style="margin-top:85px;"/></li>
                </ul>
                <div class="line">
                    <span class="left">右</span>
                    <span class="right">左</span>
                </div>
                <ul class="downYa">
                	<li><input type="checkbox" id="childleftdown" style="margin-bottom:20px;"/></li>
                	<li><span class="num" name="childrendownYa1">5</span><span class="yaIcon le5"></span></li>
                    <li><span class="num" name="childrendownYa1">4</span><span class="yaIcon le4"></span></li>
                    <li><span class="num" name="childrendownYa1">3</span><span class="yaIcon le3"></span></li>
                    <li><span class="num" name="childrendownYa1">2</span><span class="yaIcon le2"></span></li>
                    <li><span class="num" name="childrendownYa1">1</span><span class="yaIcon le1"></span></li>
                    
                    <li><span class="num" name="childrendownYa2">1</span><span class="yaIcon rg1"></span></li>
                    <li><span class="num" name="childrendownYa2">2</span><span class="yaIcon rg2"></span></li>
                    <li><span class="num" name="childrendownYa2">3</span><span class="yaIcon rg3"></span></li>
                    <li><span class="num" name="childrendownYa2">4</span><span class="yaIcon rg4"></span></li>
                    <li><span class="num" name="childrendownYa2">5</span><span class="yaIcon rg5"></span></li>
                    <li><input type="checkbox" id="childrightdown" style="margin-bottom:20px;"/></li>
                </ul>
            </div>
        </div>
        <div class="menuList">
        	<!--wl添加html  添加一个请选择会诊分类的信息------start-->
        	<span class="menuListClassify">选择分类</span>
        	<!--wl添加html  添加一个请选择会诊分类的信息------end-->
            <ul name="ycflul">
<!--            <li class="current" value="">全部</li> -->
            </ul>
            <!--wl将下面的.tableBar 移入此处------start-->
            <div class="tableBar" id="tableBarDiv">
            
        	</div>
        	<!--wl将下面的.tableBar 移入此处------end-->
        	
        	<!-- ######添加生成临床路径按钮####### -->
        	<!-- 
        	<div class="tableBar">
        		&nbsp;&nbsp;<a class="kqdsCommonBtn" onclick="goClinicPath()">生成路径</a>
        	</div> 
        	-->
        	
        </div>
        <!-- 
        <div class="tableBar" id="tableBarDiv">
        </div> 
        -->
        <div class="tableBox">
            <table id="table" class="table-striped table-condensed table-bordered" data-height="350"></table>
        </div>
    </div>
        <div class="buttonBar" style="margin: 10px 10px;overflow: hidden;">
            <div class="recommendedBar" id="recommendedBarDiv">
            </div>
            <div class="normalBar">
            </div>
        </div>
    </div>
</body>

<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var pageurl = '<%=contextPath%>/KQDS_Tooth_DocAct/selectPageByRegno.act';
var ischild = 0; //当前选项卡是否是儿童页 0不是 1是
var crup1 = ""; //儿童左上
var crup2 = ""; //儿童右上
var crdown1 = ""; //儿童左下
var crdown2 = ""; //儿童右下
var etup1 = ""; //儿童左上
var etup2 = ""; //儿童右上
var etdown1 = ""; //儿童左下
var etdown2 = ""; //儿童右下
var yctype = ""; //牙齿分类
var onclickrowobj = window.parent.onclickrowOobj; //父页面传值
var listbutton = window.parent.listbutton; //父页面传值
var onclickrow = "";
var usercode = "";
var useranme = "";
var perseqId = "<%=person.getSeqId()%>";
var regno = "";
var isdelreg = 0;
var menuid = window.parent.menuid;
$(function() {
    //如果选中的不是挂号记录
    if (onclickrowobj == "" || onclickrowobj.ifcost == null) {
        regno = onclickrowobj.regno;
    } else {
        regno = onclickrowobj.seqId;
        isdelreg = onclickrowobj.del;
    }
    getButtonPower();
    //查询字典表中 牙齿分类
    var url = contextPath + "/YZDictAct/getListByParentCode.act?parentCode=YCFL";
    $.axse(url, null,
    function(data) {
        var list = data.list;
        if (list != null) {
            if (list.length > 0) {
                var ulstr = "";
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    if (j == 0) {
                        ulstr += '<li class="current" style="cursor: pointer;" value="">全部</li>';
                    }
                    ulstr += '<li style="cursor: pointer;" value="' + optionStr.seqId + '">' + optionStr.dictName + '</li>';
                }
                $("ul[name=ycflul]").html(ulstr);
            }
        }
    },
    function() {
        layer.alert('查询出错！' );
    });

    //切换牙齿分类
    $('.menuList ul').on('click', 'li',
    function() {
        $(this).addClass('current').siblings('li').removeClass('current');
    });

    //展示患者信息
    var user = getHzNameByusercodeTB(onclickrowobj.usercode);
    usercode = onclickrowobj.usercode;
    username = user.username;
    var sex = user.sex == null ? "": user.sex;
    $("#showtext").html("口腔检查 - " + user.username + " (" + sex + ")");
    pageurl += '?usercode=' + onclickrowobj.usercode;
    inittable(); //加载表格
    //tab的切换
    $('.infoHd .tab').on('click', 'li',
    function() {
        var link = $(this).attr('link');
        $(this).addClass('current').siblings('li').removeClass('current');
        $('.' + link).removeClass('hide').siblings('.toothBox').addClass('hide');
        if ($(this).html() == "儿童") {
            ischild = 1;
            //设置所有牙齿 不选中
            $(".num").each(function(index) {
 	            $(this).parent().removeClass('current');
 	    	});
            //设置所有 checkbox 不选中 
            $("input[type='checkbox']").each(function(index) {
 	            $(this).attr("checked",false);
 	    	});
        } else {
            ischild = 0;
            $(".num").each(function(index) {
 	            $(this).parent().removeClass('current');
 	    	 });
            //设置所有 checkbox 不选中 
            $("input[type='checkbox']").each(function(index) {
 	            $(this).attr("checked",false);
 	    	});
        }
    });

    //点击牙齿
    $('.toothBox').on('click', 'li',
    function() {
        $(this).toggleClass('current');
    });
    //成人
    //自动选择--左上
    $('#lefttop').on('click',
    function() {
    	if ($('#lefttop').is(':checked')) {
    		 $("span[name=adultupYa1]").each(function(index) {
   	        	$(this).parent().removeClass('current');
   	            $(this).parent().toggleClass('current');
    	     });
        }else{
        	 $("span[name=adultupYa1]").each(function(index) {
 	            $(this).parent().removeClass('current');
 	    	 });
        }
    });
    //自动选择--右上
    $('#righttop').on('click',
    function() {
    	if ($('#righttop').is(':checked')) {
	  		 $("span[name=adultupYa2]").each(function(index) {
	 	        	$(this).parent().removeClass('current');
	 	            $(this).parent().toggleClass('current');
	  	     });
        }else{
       	 $("span[name=adultupYa2]").each(function(index) {
	            $(this).parent().removeClass('current');
	    	 });
        }
    });
    //自动选择--左下
    $('#leftdown').on('click',
    function() {
    	if ($('#leftdown').is(':checked')) {
	  		 $("span[name=adultdownYa1]").each(function(index) {
	 	        	$(this).parent().removeClass('current');
	 	            $(this).parent().toggleClass('current');
	  	     });
        }else{
      		 $("span[name=adultdownYa1]").each(function(index) {
	            $(this).parent().removeClass('current');
	    	 });
        }
    });
    //自动选择--右下
    $('#rightdown').on('click',
    function() {
    	if ($('#rightdown').is(':checked')) {
	  		 $("span[name=adultdownYa2]").each(function(index) {
	 	        	$(this).parent().removeClass('current');
	 	            $(this).parent().toggleClass('current');
	  	     });
        }else{
     		 $("span[name=adultdownYa2]").each(function(index) {
	            $(this).parent().removeClass('current');
	    	 });
        }
    });

    //儿童
    //自动选择--左上
    $('#childlefttop').on('click',
    function() {
    	if ($('#childlefttop').is(':checked')) {
	  		 $("span[name=childrenupYa1]").each(function(index) {
	 	        	$(this).parent().removeClass('current');
	 	            $(this).parent().toggleClass('current');
	  	     });
        }else{
    		 $("span[name=childrenupYa1]").each(function(index) {
	            $(this).parent().removeClass('current');
	    	 });
        }
    });
    //自动选择--右上
    $('#childrighttop').on('click',
    function() {
    	if ($('#childrighttop').is(':checked')) {
	  		 $("span[name=childrenupYa2]").each(function(index) {
	 	        	$(this).parent().removeClass('current');
	 	            $(this).parent().toggleClass('current');
	  	     });
        }else{
   		 $("span[name=childrenupYa2]").each(function(index) {
	            $(this).parent().removeClass('current');
	    	 });
        }
    });
    //自动选择--左下
    $('#childleftdown').on('click',
    function() {
    	if ($('#childleftdown').is(':checked')) {
	  		 $("span[name=childrendownYa1]").each(function(index) {
	 	        	$(this).parent().removeClass('current');
	 	            $(this).parent().toggleClass('current');
	  	     });
       }else{
  		 $("span[name=childrendownYa1]").each(function(index) {
	            $(this).parent().removeClass('current');
	    	 });
       }
    });
    //自动选择--右下
    $('#childrightdown').on('click',
    function() {
        if ($('#childrightdown').is(':checked')) {
	  		 $("span[name=childrendownYa2]").each(function(index) {
	 	        	$(this).parent().removeClass('current');
	 	            $(this).parent().toggleClass('current');
	  	     });
        }else{
 		 $("span[name=childrendownYa2]").each(function(index) {
	            $(this).parent().removeClass('current');
	    	 });
        }
    });
    
    //点击牙齿分类
    $('.menuList').on('click', 'li',
    function() {
        yctype = $(this).attr("value");
    });

    //添加 获取所有选中的牙齿  如选中则保存到全局变量中
    $("#addbtn").click(function() {
   	
        //1先判断是否选择患者信息 无则提示用户请选择左侧菜单中某一条患者信息
        if (onclickrowobj.seqId == "" || onclickrowobj.seqId == null) {
            layer.alert('请选择一条挂号信息再添加会诊信息'  );
            return false;
        }

        //2清空所有牙齿的值
        crup1 = "";
        crup2 = "";
        crdown1 = "";
        crdown2 = "";

        if (ischild == 0) {
            //成人
            $('span[name=adultupYa1]').each(function(i) {
                if ($(this).parent().prop('className') == 'current') {
                    crup1 = crup1 + $(this).html();
                    crup1 += ',';
                }
            });
            $('span[name=adultupYa2]').each(function(i) {
                if ($(this).parent().prop('className') == 'current') {
                    crup2 = crup2 + $(this).html();
                    crup2 += ',';
                }
            });
            $('span[name=adultdownYa2]').each(function(i) {
                if ($(this).parent().prop('className') == 'current') {
                	crdown2 = crdown2 + $(this).html();
                    crdown2 += ',';
                }
            });
            $('span[name=adultdownYa1]').each(function(i) {
                if ($(this).parent().prop('className') == 'current') {
                    
                    
                    crdown1 = crdown1 + $(this).html();
                    crdown1 += ',';
                }
            });
            if (crup1 != null && crup1 != "") {
                crup1 = crup1.substring(0, crup1.length - 1);
            }
            if (crup2 != null && crup2 != "") {
                crup2 = crup2.substring(0, crup2.length - 1);
            }
            if (crdown1 != null && crdown1 != "") {
                crdown1 = crdown1.substring(0, crdown1.length - 1);
            }
            if (crdown2 != null && crdown2 != "") {
                crdown2 = crdown2.substring(0, crdown2.length - 1);
            }
            //3.1判断用户是否选择牙齿 无则提示请选择 
            if (crup1 == "" && crup2 == "" && crdown1 == "" && crdown2 == "") {
                layer.alert('请选择一个或多个牙齿模型', {
                      
                });
                return false;
            }
        } else {
            //儿童
            $('span[name=childrenupYa1]').each(function(i) {
                if ($(this).parent().prop('className') == 'current') {
                    etup1 = etup1 + $(this).html();
                    etup1 += ',';
                }
            });
            $('span[name=childrenupYa2]').each(function(i) {
                if ($(this).parent().prop('className') == 'current') {
                    etup2 = etup2 + $(this).html();
                    etup2 += ',';
                }
            });
            $('span[name=childrendownYa1]').each(function(i) {
                if ($(this).parent().prop('className') == 'current') {
                    etdown1 = etdown1 + $(this).html();
                    etdown1 += ',';
                }
            });
            $('span[name=childrendownYa2]').each(function(i) {
                if ($(this).parent().prop('className') == 'current') {
                    etdown2 = etdown2 + $(this).html();
                    etdown2 += ',';
                }
            });
            if (etup1 != null && etup1 != "") {
                etup1 = etup1.substring(0, etup1.length - 1);
            }
            if (etup2 != null && etup2 != "") {
                etup2 = etup2.substring(0, etup2.length - 1);
            }
            if (etdown1 != null && etdown1 != "") {
                etdown1 = etdown1.substring(0, etdown1.length - 1);
            }
            if (etdown2 != null && etdown2 != "") {
                etdown2 = etdown2.substring(0, etdown2.length - 1);
            }
            //3.2判断用户是否选择牙齿 无则提示请选择 
            if (etup1 == "" && etup2 == "" && etdown1 == "" && etdown2 == "") {
                layer.alert('请选择一个或多个牙齿模型', {
                      
                });
                return false;
            }
        }

        //4判断牙齿分类是否为空 空则提示用户请选择
        if (yctype == "") {
            layer.alert('请选择牙齿分类'  );
            return false;
        }

        //5 保存
        var param;
        if (ischild == 0) {
            //成人
            param = {
                regno: regno,
                usercode: onclickrowobj.usercode,
                usertype: ischild,
                toothtype: yctype,
                yw1: crup1,
                yw2: crup2,
                yw3: crdown2,
                yw4: crdown1
            };
        } else {
            param = {
                regno: regno,
                usercode: onclickrowobj.usercode,
                usertype: ischild,
                toothtype: yctype,
                yw1: etup1,
                yw2: etup2,
                yw3: etdown2,
                yw4: etdown1
            };
        }
        var url = '<%=contextPath%>/KQDS_Tooth_DocAct/tianJiaHuiZhen.act';
        $.axse(url, param,
        function(data) {
            if (data.retState == "0") {
                layer.alert('添加成功', {
                    end: function() {
                        window.location.reload();
                    }
                });
            }
        },
        function() {
            layer.alert('添加失败！'  );
        });
    });
	
    function inittable() {
    	$("#table").bootstrapTable({
            url: pageurl,
            dataType: "json",
            singleSelect: true,
            striped: true,
            onLoadSuccess: function(data) { //加载成功时执行
                $("td a").each(function() {
                    var datavalue = $(this).attr('data-value');
                    $(this).attr('title', datavalue);
                });
                $(".unitsDate").datetimepicker({
            		language:  'zh-CN',  
            		minView:0,
            	    autoclose : true,
            		format: 'yyyy-mm-dd hh:ii:ss',
            		pickerPosition: "bottom-right"
            	});
                setHeight();
                /*表格载入时，设置表头的宽度 */
                setTableHeaderWidth(".tableBox");
            },
            columns: [
            {
                title: '是否治疗',
                field: 'toothseat',
                align: 'center',
                valign: 'middle',
                sortable: true,
                formatter: function(value, row, index) {
                	if(value == '已治疗'){
                		 return value;
                	}else{
                		 return '<select id="toothseat' + index + '" style="width:60px;height:22px;" onchange="edithz(\'' + row.seqId + '\',\'' + index + '\',\'' + row.createuser + '\')">'
                         +'<option value="未治疗" selected>未治疗</option>'
                         +'<option value="已治疗">已治疗</option></select>';
                	}
                }
            },
            {
                title: '分类',
                field: 'toothtypename',
                align: 'center',
                valign: 'middle',
                sortable: true
            },
            {
                title: '牙位',
                field: ' ',
                align: 'center',
                valign: 'middle',
                sortable: true,
                formatter: function(value, row, index) {
                    var str = '<table id="table2" border="0" cellpadding="1" cellspacing="1">' 
                    + '<tr>' 
	                    + '<td ><span style="display:block;height:100%;width:100px;border-right:1px solid #999;border-bottom:1px solid #999;">' + row.yw1 + '</span></td>' 
	                    + '<td class="lastCol"><span style="display:block;height:100%;width:100px;border-bottom:1px solid #999;">' + row.yw2 + '</span></td>' 
                    + '</tr>' 
                    + '<tr class="lastrow">' 
	                    + '<td ><span style="display:block;height:100%;width:100px;border-right:1px solid #999;">' + row.yw4 + '</span></td>' 
	                    + '<td class="lastCol"><span style="display:block;height:100%;width:100px;">' + row.yw3 + '</span></td>' 
                    + '</tr>' 
                    + '</table>';
                    return str;
                }
            },
            {
                title: '检查项',
                field: 'detaildesc',
                align: 'center',
                valign: 'middle',
                sortable: true,
                formatter: function(value, row, index) {
                    if (value == null || value == "" || value == "null" || value == "undefined") {
                        value = "";
                    }
                    return '<textarea type="text"  class="form-control" rows="2" style="width:150px;padding:0px;min-height: 60px;" title="' + value + '" onBlur="edithz(\'' + row.seqId + '\',\'' + index + '\',\'' + row.createuser + '\')" name="detaildesc' + index + '" id="detaildesc' + index + '" >' + value + '</textarea>';
                }
            },
            {
                title: '情况备注',
                field: 'remark',
                align: 'center',
                valign: 'middle',
                sortable: true,
                formatter: function(value, row, index) {
                    // return '<input type="text" style="width:150px;" title="'+value+'" onBlur="edithz(\''+row.seqId+'\')" name="remark" id="remark" value='+value+'>';
                    if (value == null || value == "" || value == "null" || value == "undefined") {
                        value = "";
                    }
                    return '<textarea type="text"  class="form-control" rows="2" style="width:150px;padding:0px;min-height: 60px;" title="' + value + '" onBlur="edithz(\'' + row.seqId + '\',\'' + index + '\',\'' + row.createuser + '\')" name="remark' + index + '" id="remark' + index + '" >' + value + '</textarea>';

                }
            }
            ,
            {
                title: '提醒时间',
                field: 'diseasesort',
                align: 'center',
                valign: 'middle',
                sortable: true,
                formatter: function(value, row, index) {
                    if (value != null && value != "") {
                    	return value.substring(0,10);
                    }else{
                        return '<input type="text" style="width:135px; text-align:center;" class="unitsDate" onchange="edithz(\'' + row.seqId + '\',\'' + index + '\',\'' + row.createuser + '\')" name="diseasesort'+index+'" id="diseasesort'+index+'">';
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
                        return "<span class='name'>"+value+"</span>";
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
                    return '<span title="' + value + '">' + value.substring(0,10) + '</span>';
                }
            }
           ]
        }).on('click-row.bs.table',
        function(e, row, element) {
            $('.success').removeClass('success'); //去除之前选中的行的，选中样式
            $(element).addClass('success'); //添加当前选中的 success样式用于区别
            var index = $('#table').find('tr.success').data('index'); //获得选中的行
            onclickrow = $('#table').bootstrapTable('getData')[index];
            getdetail(onclickrow);
        });
    }
    function getdetail(onclickrow){
    	
    	 //分类
    	  $("ul[name=ycflul] li").each(function(index) {
    			if($(this).attr("value") == onclickrow.toothtype){
    				$(this).click();
    			}
		  });
    	 //牙齿
    	 //成人
    	 if(onclickrow.usertype == "0"){
    		 $(".infoHd .tab li").each(function(index) {
    			if(index==1){
    				$(this).click();
    			}
			 });
    		 $("span[name=adultupYa1]").each(function(index) {
    			 if(onclickrow.yw1.indexOf(8-index)>=0){
    				 $(this).parent().toggleClass('current');
    			 }
			 });
			 $("span[name=adultupYa2]").each(function(index) {
				 if(onclickrow.yw2.indexOf(index+1)>=0){
    				 $(this).parent().toggleClass('current');
    			 } 	  	     
			 });
 			 $("span[name=adultdownYa2]").each(function(index) {
 				 if(onclickrow.yw3.indexOf(index+1)>=0){
    				 $(this).parent().toggleClass('current');
    			 } 	  	     
 			 });
 			 $("span[name=adultdownYa1]").each(function(index) {
 				 if(onclickrow.yw4.indexOf(8-index)>=0){
    				 $(this).parent().toggleClass('current');
    			 } 	  	     
 			 });
    	 }else{
    		 $(".infoHd .tab li").each(function(index) {
     			if(index==0){
     				$(this).click();
     			}
 			 });
    		 $("span[name=childrenupYa1]").each(function(index) {
    			 if(onclickrow.yw1.indexOf(5-index)>=0){
    				 $(this).parent().toggleClass('current');
    			 }
			 });
			 $("span[name=childrenupYa2]").each(function(index) {
				 if(onclickrow.yw2.indexOf(index+1)>=0){
    				 $(this).parent().toggleClass('current');
    			 } 	  	     
			 });
 			 $("span[name=childrendownYa1]").each(function(index) {
 				 if(onclickrow.yw3.indexOf(index+1)>=0){
    				 $(this).parent().toggleClass('current');
    			 } 	  	     
 			 });
 			 $("span[name=childrendownYa2]").each(function(index) {
 				 if(onclickrow.yw4.indexOf(5-index)>=0){
    				 $(this).parent().toggleClass('current');
    			 } 	  	     
 			 });
    	 }
    }
    //获取所有被选中复选框所在行的seqid
    function getIdSelections() {
        return $.map($("#table").bootstrapTable('getSelections'),
        function(row) {
            return row.seqId;
        });
    }

    //删除
    $("#delbtn").click(function() {
        if (onclickrow == "" || onclickrow == null) {
            layer.alert('请选中要删除的会诊记录！'  );
            return false;
        } else if (onclickrow.createuser != perseqId) {
            layer.alert('不能删除他人填写的会诊记录！'  );
            return false;
        }

        layer.confirm('确定删除？', {
            btn: ['删除', '放弃'] //按钮
        },
        function() {
            var url = '<%=contextPath%>/KQDS_Tooth_DocAct/deleteObj.act?ids=' + onclickrow.seqId;
            $.axse(url, null,
            function(data) {
                if (data.retState == "0") {
                    layer.alert('删除成功', {
                          
                        end: function() {
                            window.location.reload();
                        }
                    });
                }
            },
            function() {
                layer.alert('删除失败！', {
                      
                });
            });
        });
    });

});
function refresh(){
	$('#table').bootstrapTable('refresh', {
	    'url': pageurl
	});
}

function edithz(seqId, index, createuser) {
	if(createuser != '<%=person.getSeqId()%>'){ // 不是自己创建的，不能编辑
		return false;
	}
	var detaildesc = $("#detaildesc" + index).val();
    var remark = $("#remark" + index).val();
    var toothseat = $("#toothseat" + index).val();
    var diseasesort = $("#diseasesort" + index).val();
    var param = {
	   	        seqId: seqId,
	   	        detaildesc: detaildesc,
	   	        toothseat:toothseat,
	   	        diseasesort:diseasesort,
	   	        schf:1,
	   	        remark: remark
	   	};
    //提醒时间
    if(diseasesort){
    	save("是否保存并生成回访",param);
    }else{
	   	 var url = '<%=contextPath%>/KQDS_Tooth_DocAct/insertOrUpdate.act';
	     $.axseSubmit(url, param,function() {},function(r) {},function() {});
    }
}
function save(msg,param){
	layer.confirm(msg, {
        btn: ['是', '否'] //按钮
    },
    function() {
   	     layer.closeAll('dialog');
	   	 var url = '<%=contextPath%>/KQDS_Tooth_DocAct/insertOrUpdate.act';
	     $.axseSubmit(url, param,function() {},function(r) {refresh();},function() {});
    },
    function() {
    	 layer.closeAll('dialog');
    	 param.schf=0;
	   	 var url = '<%=contextPath%>/KQDS_Tooth_DocAct/insertOrUpdate.act';
	     $.axseSubmit(url, param,function() {},function(r) {refresh();},function() {});
    });
	setHeight();
}
//门诊预约
function yuyue() {
    var url = contextPath + '/KQDS_Net_OrderAct/toYyzx2.act';
    if (usercode != null && usercode != "") {
        url = url + '?usercode=' + usercode + '&username=' + username;
    }
    //预约中心
    parent.layer.open({
        type: 2,
        title: '预约中心',
        shadeClose: true,
        shade: 0.6,
        area: ['990px', '90%'],
        content: url //iframe的url
    });
}
//添加回访
function goAddVisit() {
    if (onclickrowobj.usercode == null || onclickrowobj.usercode == "") {
        layer.alert('请选择患者' );
    } else {
        parent.layer.open({
            type: 2,
            title: '添加回访',
            shadeClose: true,
            shade: 0.6,
            // 	        offset:["40px" , '30px'],
            area: ['550px', '480px'],
            content: contextPath+'/KQDS_VisitAct/toVisitAdd.act?lytype=huizhen&usercode=' + onclickrowobj.usercode //iframe的url
        });
    }
}
//会诊查询
function goHZSearch() {
    parent.layer.open({
        type: 2,
        title: '会诊查询',
        shadeClose: true,
        shade: 0.6,
        area: ['90%', '90%'],
        content: contextPath+'/KQDS_Tooth_DocAct/toBb_Hzjl.act?menuId='+menuid
    });
}

//生成路径#######################################################
function goClinicPath() {
    parent.layer.open({
    	title: '请您选择',
        shadeClose: true,
        content: '是否生成？',
        btn: ['确定','关闭'],
        btn1: function(index, layero){
	   	   	 var url = '<%=contextPath%>/HUDH_LCLJAct/saveLcljOrder.act';
	   	  	 var usercode = onclickrowobj.usercode;
	   	  /* var yaNum = $(".current").length-2; */
	   	     var yaNum = $('li[class="current"]').length-2;
	   	   	 var param = {
	   	   		blcode: usercode,
	   	   		totalTooth: yaNum
	   	   	 }
	   	     $.axseSubmit(url, param,function() {},function(r) {},function(r) {parent.layer.closeAll('dialog');});
        },
        btn2: function(index, layero){
        	 layer.closeAll('dialog');         
        },
    });
}

function getButtonPower() {
    var menubutton1 = "",
    menubutton2 = "";
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "hzxx_sc" && isdelreg == 0) {
            menubutton1 += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="delbtn" >删除</a>';
        } else if (listbutton[i].qxName == "hzxx_tj" && isdelreg == 0) {
            menubutton1 += ' <a href="javascript:void(0);" style="margin-left:10px;" class="kqdsSearchBtn" id="addbtn">添加</a>';
        } else if (listbutton[i].qxName == "hzxx_yy" && isdelreg == 0) {
            menubutton2 += ' <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="yuyue()">预约</a>';
        } else if (listbutton[i].qxName == "hzxx_hf" && isdelreg == 0) {
            menubutton2 += ' <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="goAddVisit()">回访</a>';
        }
    }
    menubutton2 += ' <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="goHZSearch()">会诊查询</a>';
    $("#tableBarDiv").append(menubutton1);
    $("#recommendedBarDiv").append(menubutton2);
}
function setHeight(){
	$("#table").bootstrapTable("resetView",{
		height:$(window).outerHeight()-$(".tabContent").outerHeight()-$(".menuList").outerHeight()-92
	});
}
</script>
</html>
