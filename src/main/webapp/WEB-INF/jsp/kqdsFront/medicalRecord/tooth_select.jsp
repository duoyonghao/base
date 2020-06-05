<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	// 获取病历的 td 元素的 name属性值
	String inputTdName = request.getParameter("inputTdName");

	if (inputTdName == null) {
		inputTdName = "";
	}

	String up1 = request.getParameter("up1");
	String up2 = request.getParameter("up2");
	String down1 = request.getParameter("down1");
	String down2 = request.getParameter("down2");

	if (up1 == null) {
		up1 = "";
	}

	if (up2 == null) {
		up2 = "";
	}

	if (down1 == null) {
		down1 = "";
	}

	if (down2 == null) {
		down2 = "";
	}
%>
<!DOCTYPE html>
<html>
<!-- 拷贝 huizheng_info.jsp 页面 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>接诊信息</title>
<!-- 整个系统就一个页面 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tooth_select.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tabledit.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/language/zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
	
</head>
<style>
	#table2{ 
		border-collapse: collapse; 
		border: 0px solid #999; 
	} 
	#table2 td { 
		border-top: 0; 
		border-right: 1px solid #999; 
		border-bottom: 1px solid #999; 
		border-left: 0;
	} 
	#table2 tr.lastrow td { 
		border-bottom: 0; 
	} 
	#table2 tr td.lastCol { 
		border-right: 0; 
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
</style> 

<body>
<div id="container">
    <div class="infoHd">
        <ul class="tab">
            <li link="children">儿童</li>
            <li link="adult" class="current">成人</li>
        </ul>
        <span class="text" id="showtext">接诊信息</span>
    </div>
    <div class="infoBd">
        <div class="tabContent">
            <!--成人-->
            	
            <div class="toothBox adult" style="">
                <ul class="upYa" >
                	<li><input type="checkbox" id="lefttop" style="margin-top:60px;" /></li>
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
                    <li><input type="checkbox" id="righttop" style="margin-top:60px;margin-left: 4px;"/></li>
                </ul>
                <div class="line">
                    <span class="left">右</span>
                    <span class="right">左</span>
                </div>
                <ul class="downYa">
                	<li><input type="checkbox" id="leftdown" style="margin-top:3px"/></li>
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
                    <li><input type="checkbox" id="rightdown" style="margin-top:3px;margin-left: 4px;"/></li>
                </ul>
            </div>
            <!--儿童-->
            <div class="toothBox children hide">
                <ul class="upYa">
                	<li><input type="checkbox" id="childlefttop" style="margin-top:60px;" /></li>
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
                    <li><input type="checkbox" id="childrighttop" style="margin-top:60px;margin-left: 4px;"/></li>
                </ul>
                <div class="line">
                    <span class="left">右</span>
                    <span class="right">左</span>
                </div>
                <ul class="downYa">
                    <li><input type="checkbox" id="childleftdown" style="margin-top:3px;"/></li>
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
                    <li><input type="checkbox" id="childrightdown" style="margin-top:3px;margin-left: 4px;"/></li>
                </ul>
            </div>
        </div>
        
        <div class="tableBar" id="tableBarDiv" style="text-align:center;margin: 0 atuo;">
        	<a href="javascript:void(0);" class="kqdsSearchBtn" id="addbtn" onclick="selectTooth();">添加</a>
        </div>
    </div>
    </div>
</div>
</body>

<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var inputTdName = "<%=inputTdName%>";

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

var onclickrow = "";
var usercode = "";
var useranme = "";
var perseqId = "<%=person.getSeqId()%>";
var regno = "";
var isdelreg = 0;

var pup1 = "<%=up1%>"; // 父页面传过来的值
var pup2 = "<%=up2%>"; // 父页面传过来的值
var pdown1 = "<%=down1%>"; // 父页面传过来的值
var pdown2 = "<%=down2%>"; // 父页面传过来的值

var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {

    if (inputTdName == '') {
        layer.alert('参数错误，未获取到inputTdName值' );
        return;
    }

    //切换牙齿分类
    $('.menuList ul').on('click', 'li',
    function() {
        $(this).addClass('current').siblings('li').removeClass('current');
    });

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
        yctype = $(this).val();
    });

    selectToothByPageValue(); // 根据父页面传过来的值，初始化选中状态
});

//根据父页面传过来的值，初始化选中状态
function selectToothByPageValue() {
    /* 由于当前的存值没有区分成人、儿童，此方法暂不实现   yangsen  17-4-20 */
}

function selectTooth() {
    //2清空所有牙齿的值
    crup1 = "";
    crup2 = "";
    crdown1 = "";
    crdown2 = "";

    etup1 = "";
    etup2 = "";
    etdown1 = "";
    etdown2 = "";

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
        $('span[name=adultdownYa1]').each(function(i) {
            if ($(this).parent().prop('className') == 'current') {
                crdown1 = crdown1 + $(this).html();
                crdown1 += ',';
            }
        });
        $('span[name=adultdownYa2]').each(function(i) {
            if ($(this).parent().prop('className') == 'current') {
                crdown2 = crdown2 + $(this).html();
                crdown2 += ',';
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
            //layer.alert('请选择一个或多个牙齿模型' );
            //return false;
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
            // layer.alert('请选择一个或多个牙齿模型' );
            // return false;
        }
    }

    //5 保存
    var param;
    if (ischild == 0) { //成人
        // alert("a" + crup1 + "b" +crup2+ "c" + crdown1 + "d" + crdown2);
        parent.fillSelectTooth(inputTdName, crup1, crup2, crdown1, crdown2, 0);
    } else {
        // alert("a" + etup1 + "b" +etup2+ "c" + etdown1 + "d" + etdown2);
        parent.fillSelectTooth(inputTdName, etup1, etup2, etdown1, etdown2, 1);
    }

    //关闭layer
    parent.layer.close(frameindex);

}
</script>
</html>
