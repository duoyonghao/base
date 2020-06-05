<!-- wl整理 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/Load.js"></script>

<title>接诊查询</title>
 <!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
</head>
<style type="text/css">
/*工作量表格 ，单独写*/
    .kqds_table td {
        color: #666;
        padding: 2px 3px 3px 3px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        font-weight: normal;
        line-height: 28px;
    }

    /*查询条件中的样式  */
    .searchWrap .formBox>section>ul.conditions>li {
        padding: 3px 0;
    }

    .searchWrap .formBox>section>ul.conditions>li>span {
        width: 62px;
        text-align: right;
    }

    .searchWrap .formBox>section>ul.conditions>li>input[type=text],
    .searchWrap .formBox>section>ul.conditions>li>select {
        width: 94px;
    }

    @media screen and (max-width:1390px) {
        .searchWrap .formBox>section>ul.conditions>li>span {
            width: 55px;
            text-align: right;
            font-size: 11px;
            height: 24px;
            line-height: 24px;
        }

        .searchWrap .formBox>section>ul.conditions>li>input[type=text],
        .searchWrap .formBox>section>ul.conditions>li>select {
            width: 82px;
            font-size: 11px;
            padding: 0 3px 0 5px;
            height: 24px;
            line-height: 24px;
        }
    }

    @media screen and (max-width:1100px) {
        .searchWrap .formBox>section>ul.conditions>li>span {
            width: 51px;
            text-align: right;
            font-size: 10px;
            height: 22px;
            line-height: 22px;
        }

        .searchWrap .formBox>section>ul.conditions>li>input[type=text],
        .searchWrap .formBox>section>ul.conditions>li>select {
            width: 73px;
            font-size: 10px;
            padding: 0 3px 0 5px;
            height: 22px;
            line-height: 22px;
        }
    }

    .centerWrap .columnWrap .columnBd ul {
        overflow: visible;
    }

    .centerWrap .columnWrap .columnBd ul li {
        margin-left: 0px;
    }

    .centerWrap .columnWrap {
        margin-bottom: 0px;
    }

    .fixed-table-pagination .btn-group .dropdown-menu {
        min-width: auto;
    }

    /* 搜索框select */
    .searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {
        width: 94px;
    }

    .searchSelect>.btn {
        width: 94px;
        height: 26px;
        border: solid 1px #e5e5e5;
    }

    .bootstrap-select>.dropdown-toggle.bs-placeholder,
    .bootstrap-select>.dropdown-toggle.bs-placeholder:hover,
    .bootstrap-select>.dropdown-toggle.bs-placeholder:focus,
    .bootstrap-select>.dropdown-toggle.bs-placeholder:active {
        color: #999;
        height: 26px;
    }

    .pull-left {
        float: left !important;
        color: #333;
    }

    .btn-group>.btn:first-child:hover {
        background: white;
    }

    .searchWrap .text {
        position: static !important;
        left: 0px;
        top: 9px;
        color: rgb(31, 32, 33);
    }

    /* 	解決标签查询中下拉框悬浮 */
    .searchWrap {
        overflow: visible;
    }

    .formBox {
        overflow: visible;
    }

    .searchWrap .formBox>section {
        height: 100px;
    }

    .searchWrap .formBox>section>ul.conditions {
        overflow: visible;
        height: 100%;
        position: relative;
    }

    .searchWrap .formBox>section {
        height: 134px
    }

    .searchWrap {
        padding: 5px 0 8px !important;

    }


    a:focus {
        outline: none;
    }

    #showhide {
        background: black;
        color: white;
        width: 600px;
        display: block;
        margin: 0 auto;
        padding: 5px;
        font-size: 20px;
        height: auto;
        font-family: "微软雅黑";
    }

    .slide {
        margin: 0;
        padding: 0;
        width: 600px;
        border-top: solid 4px gray;
        margin: 0 auto;
    }

    .btn-slide {
        background: gray;
        text-align: center;
        width: 120px;
        height: 30px;
        padding: 10px 10px 0 0;
        margin: 0 auto;
        display: block;
        color: #fff;
        text-decoration: none;
    }

    #show {
        width: 100%;
        margin-left: 6px;
    }

    #show li {
        display: inline-block;

    }

    #jzcx_askperson {
        display: inline-block !important;
    }

    .searchWrap .formBox>section>ul.conditions {
        overflow: visible;
        height: 100% !important;
        position: relative;
    }

    #more {
        float: right;
        position: absolute;
        top: 98px;
        right: -3px;
    }

    a {
        text-decoration: none;
        color: #666666;
    }

    #nav {
        display: table;
        margin: 5px;
    }

    #nav li {
        margin-right: 30px;
    }

    .navdown a {
        color: #00a6c0;
        border-bottom: 3px solid #00a6c0;
    }

    .dropup .dropdown-menu,
    .navbar-fixed-bottom .dropdown .dropdown-menu {
        top: 100% !important;
    }

    .inner {
        max-height: 84px !important;
    }

    .wh {
        width: 94px;
    }
</style>
<body>
<div id="container">
    <div id="main">
      <!--左侧中心-->
      <div class="centerWrap">
        <div class="columnWrap">
          <div class="columnBd">
          
            <div id="nav">
               <ul>
			      <li class="navdown" onclick="show(this)"><a href="#"><span>接诊</span></a></li>
			      <li onclick="show(this)"><span>信息</span></li>
			      <li onclick="show(this)"><span>到诊</span></li>
			      <li onclick="show(this)"><span>费用</span></li>
			      <li onclick="show(this)"><span>明细</span></li>
			    </ul>
            </div>
            
            <div class="tableBox" > 
              <table id="table" class="table-striped table-condensed table-bordered" data-height=""></table>
            </div>
            
          </div>
        </div>
        
        <div id="gongzuol">
          <div class="columnBd">
            <ul class="dataCountUl" id="dataCount">
            </ul>
          </div>
        </div>

        <div class="searchWrap" style="height:280px;overflow-y:scroll;overflow-x:hidden;">
          <!-- <div class="cornerBox">查询条件</div> -->

          <div class="formBox">
            <section>
              <ul class="conditions">
                <li>
                  <span>所属门诊</span>
                  <select id="organization" name="organization"></select>
                </li>
                <li>
                  <span>模糊查询</span>
                  <input type="text" id="searchValue" class="searchInput" placeholder="姓名/手机号">
                </li>
                <li>
                  <span>挂号时间</span>
                  <input type="text" id="starttime" name="starttime" placeholder="开始日期" readonly class="birthDate">
                </li>
                <li>
                  <span>到</span>
                  <input type="text" id="endtime" name="endtime" placeholder="结束日期" readonly class="birthDate">
                </li>
                <li class="">
                  <span>接诊咨询</span>
                  <select id="askpersonSearch" name="askpersonSearch" class="patients-source select2 dict searchSelect"
                    data-live-search="true" title="请选择"></select>
                </li>
                <li>
                  <span>创建时间</span>
                  <input type="text" id="cjstarttime" name="cjstarttime" placeholder="开始日期" readonly class="birthDate">
                </li>
                <li>
                  <span>到</span>
                  <input type="text" id="cjendtime" name="cjendtime" placeholder="结束日期" readonly class="birthDate">
                </li>
                <li>
                  <span>初诊时间</span>
                  <input type="text" id="dystarttime" name="dystarttime" placeholder="开始日期" readonly class="birthDate">
                </li>
                <li>
                  <span>到</span>
                  <input type="text" id="dyendtime" name="dyendtime" placeholder="结束日期" readonly class="birthDate">
                </li>
                <li>
                  <span>医生</span>
                  <select id="doctorSearch" name="doctorSearch" class="patients-source select2 dict searchSelect"
                    data-live-search="true" title="请选择"></select>
                </li>
                <li>
                    <span>治疗时间</span>
                    <input type="text" id="zlstarttime" name="zlstarttime" placeholder="开始日期" readonly
                      class="birthDate">
                  </li>
                  <li>
                    <span>到</span>
                    <input type="text" id="zlendtime" name="zlendtime" placeholder="结束日期" readonly class="birthDate">
                  </li>
                <li>
                    <span>收费时间</span>
                    <input type="text" id="sfstarttime" name="sfstarttime" placeholder="开始日期" readonly
                      class="birthDate">
                  </li>
                  <li>
                    <span>到</span>
                    <input type="text" id="sfendtime" name="sfendtime" placeholder="结束日期" readonly class="birthDate">
                  </li>
                <li id="accept">
                  <!-- 添加受理工具查询分类 -->
                  <span>受理工具</span>
                  <select id="gongju" name="gongju" class="dict searchSelect" data-live-search="true" tig="SLGJ"
                    title="请选择"></select>
                </li>
                <li>
                  <span>成交状态</span>
                  <select name="cjstatus" id="cjstatus">
                    <option value="">请选择</option>
                    <option value="0">未成交</option>
                    <option value="1">已成交</option>
                  </select>
                </li>
                <li>
                  <span>病历</span>
                  <select name="ifmedrecord" id="ifmedrecord">
                    <option value="">请选择</option>
                    <option value="1">已填写</option>
                    <option value="0">未填写</option>
                  </select>
                </li>
                
                <li id="tool">
                  <span>患者来源</span>
                  <select id="devchannelSearch" name="devchannelSearch"
                    class="patients-source select2 dict searchSelect" tig="hzly" data-live-search="true"
                    onchange="getSubDictSelectSearch('devchannelSearch','nexttype1');" title="请选择"></select>
                </li>
                <li id="toolSon">
                  <span>子分类</span>
                  <select id="nexttype1" name="nexttype1" class="select2 dict searchSelect" data-live-search="true"
                    title="请选择"></select>
                </li>

                <li>
                  <span>就诊科室</span>
                  <select class="dept" tag="<%=ConstUtil.DEPT_TYPE_1 %>" name="regdept" id="regdept"></select>
                </li>
                <bottom class="btn" id="more">展开</bottom>
                <ul id="show" style="display:none;">
                
                <li>
                  <span>挂号分类</span>
                  <select class="dict wh" tig="ghfl" name="regsort" id="regsort"></select>
                </li>
                <li>
                  <span>就诊分类</span>
                  <select class="dict wh" tig="jzfl" name="recesort" id="recesort"></select>
                </li>
                <li style="margin-left: 6px;">
                    <span>消费分类</span>
                    <select id="basetype" name="basetype" class="select2 searchSelect" data-live-search="true"
                      title="请选择"></select>
                  </li>
                  <li>
                    <span title="二级消费分类">消费子类</span>
                    <select id="xfnexttype" name="xfnexttype" class="select2 searchSelect" title="请选择"></select>
                  </li>
                  <li>
                    <span>消费项目</span>
                    <input type="text" placeholder="消费项目" id="itemname" name="itemname">
                  </li>
                  <li>
                    <span>第一咨询</span>
                    <select id="askperson" name="askperson" class="patients-source select2 dict searchSelect"
                      data-live-search="true" title="请选择"></select>
                  </li>
                  <li>
                  <span>客户等级</span>
                  <select name="importantSearch" id="importantSearch" class="wh">
                    <option value="">客户等级</option>
                    <option value="1">一级</option>
                    <option value="2">二级</option>
                    <option value="3">三级</option>
                    <option value="4">四级</option>
                    <option value="5">五级</option>
                  </select>
                </li>
                <li style="margin-left: 6px;">
                  <span>年龄区间</span>
                  <select name="ageSearch" id="ageSearch" class="wh">
                    <option value="">年龄区间</option>
                    <option value="10">0~10</option>
                    <option value="20">10~20</option>
                    <option value="30">20~30</option>
                    <option value="40">30~40</option>
                    <option value="50">40~50</option>
                    <option value="51">50以上</option>
                  </select>
                </li>
                  <li style="margin: 4px 0 4px 12px;">
                    <span>建档人</span>
                    <input type="hidden" name="jdr" id="jdr" placeholder="建档人" title="建档人" class="form-control"
                      value="" />
                    <input type="text" id="jdrDesc" name="jdrDesc" placeholder="建档人" readonly
                      onClick="javascript:single_select_user(['jdr', 'jdrDesc'],'single',1);">
                  </li>
                  <li>
                    <span title="潜在开发项目">潜在开发</span>
                    <select class="dict" tig="QZKFXM" name="devItem" id="devItem" style="width: 94px;"></select>
                  </li>
                  <li>
                    <span>有无回访</span>
                    <select name="ywhf" id="ywhf" style="width: 94px;">
                      <option value="">请选择</option>
                      <option value="0">无</option>
                      <option value="1">有</option>
                    </select>
                  </li>
                  <li style="margin-left: 25px;">
                    <span>护士</span>
                    <input type="hidden" name="nurse" id="nurse" class="form-control" value="" />
                    <input type="text" id="nurseDesc" name="nurseDesc" placeholder="护士" readonly
                      onClick="javascript:single_select_user(['nurse', 'nurseDesc'],'',1);">
                  </li>
                  <li>
                    <span>收款备注</span>
                    <input type="text" placeholder="收款备注" id="remark" name="remark">
                  </li>
                  <li>
                    <span>成交情况</span>
                    <select name="cjStatus" id="cjStatus" style="width: 94px;">
                      <option value="">请选择</option>
                      <option value="1">已成交</option>
                      <option value="0">未成交</option>
                    </select>
                  </li>
                  <li>
                    <span>受理类型</span>
                    <select class="dict" tig="SLLX" id="shouli" style="width: 94px;"></select>
                  </li>
				  <li style="margin: 2px 0;">
                    <span>治疗状态</span>
                    <select id="zlstatus" name="zlstatus" style="width: 94px;">
                      <option value="" selected="selected">请选择</option>
                      <option value="未治疗">未治疗</option>
                      <option value="已治疗">已治疗</option>
                    </select>
                  </li>
                </ul>
              </ul>
            </section>
            <div class="btnBar" id="bottomBarDdiv">

            </div>
          </div>
        </div>
      </div>

      <!--右侧信息浏览-->
      <div class="lookInfoWrap">
        <%@include file="/inc/rightPartInfo.jsp" %>
      </div>
    </div>
  </div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/DataLazyLoad.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
</body>
<script type="text/javascript">

var listbutton;
var contextPath = "<%=contextPath%>";
var onclickrowOobj = "";
var nowday;
//初始化表头，返回空的数据
var nullUrl = contextPath + '/UtilAct/intTableHeader.act';
var pageurl = contextPath + '/KQDS_REGAct/selectZhcxNopage.act';
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var personrole = "<%=person.getUserPriv()%>";
var personroleother = "<%=person.getUserPrivOther()%>";
var selectedrows = ""; //推广计划使用
var jzcx_chufuzhenModify_Flag = false;
var jzcx_updateRegModify_Flag = false;
var jzcx_invoice_Flag = false;

var loadperson='<%=person.getUserPriv()%>';
var load=loadperson.split(",");//登陆这权限
var allowedperson='<%=SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK)%>';
var allowed=allowedperson.split(",");//允许权限
var show1="接诊";
$(function() {
	initHosSelectList4Front('organization'); // 连锁门诊下拉框
	initSysUserByDeptId($("#doctorSearch"),"doctor"); //初始化医生选项下拉框
    initSysUserByDeptId($("#askpersonSearch"),"consultation"); //初始化咨询选项下拉框
  //咨询 下拉列表
	initPersonSelectByDeptType("askperson","<%=ConstUtil.DEPT_TYPE_0 %>");
	var tmpOrganization = $("#organization").val();
    initDeptSelectByTypesAndClass(tmpOrganization);
    initCostSortSelect1Level('basetype');//消费分类
    $("#organization").change(function() {
        var currSelect = $(this).val();
        initDeptSelectByTypesAndClass(currSelect);
    });
    nowday = getNowFormatDate();
    initDictSelectByClass(); // 患者来源、挂号分类、就诊分类
    //获取当前页面所有按钮
    getButtonAllCurPage("<%=menuid%>");
    /* 左侧个人中心的按钮样式控制js已经被抽取到rightPartInfo.jsp页面中 */
    //时间选择
    $(".birthDate").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "top-right"
    });
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    $("#cjstarttime,#cjendtime").change(function() {
        timeCompartAndFz("cjstarttime", "cjendtime");
    });
    $("#dystarttime,#dyendtime").change(function() {
        timeCompartAndFz("dystarttime", "dyendtime");
    });
    $("#sfstarttime,#sfendtime").change(function() {
        timeCompartAndFz("sfstarttime", "sfendtime");
    });
    $("#zlstarttime,#zlendtime").change(function() {
        timeCompartAndFz("zlstarttime", "zlendtime");
    });
    
    
    togglemodel.initial("jzcx",pageurl);
    //4、表格初始化
    initTable(pageurl);
    
    $(window).resize(function() {
        setWidth();
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });

    /* 常用查询 按钮 高级查询  按钮*/
    initSearchToggleBtnGroup();
    $('.searchSelect').selectpicker("refresh");//搜索初始化刷新2019.10.19--licc
});

$('#basetype').change(function() {
	if($(this).val()){ // 如果一级有值，再请求
		//initCostSortSelect2Level('nexttype',this.value);
		initCostSortSelect2LevelSearch('xfnexttype',this.value);
		 $('.searchSelect').selectpicker("refresh");//搜索初始化刷新2019.10.21--licc
	}
});

$(function () {
    $("#nav li").click(function () {
      $("#nav li").eq($(this).index()).addClass("navdown").siblings().removeClass("navdown");
    })
  })

$('#more').click(function () {
	if ($(this).text() === '展开') {
		$("#show").slideToggle()
		$(".searchWrap .formBox>section").css("height","260px");
		$(this).text('收起');
	} else {
		$('#show').hide();
		$(".searchWrap .formBox>section").css("height","130px");
		$(this).text('展开');
	}
	});


function show(obj){
	show1=$(obj).text();
	if($(obj).text()=="接诊"){
		pageurl = contextPath + '/KQDS_REGAct/selectZhcxNopage.act';
	}else if($(obj).text()=="信息"){
		pageurl = contextPath + '/KQDS_UserDocumentAct/selectWithNopage2.act'
	}else if($(obj).text()=="到诊"){
		pageurl = contextPath + '/KQDS_REGAct/selectDzQuery.act'
	}else if($(obj).text()=="费用"){
		pageurl = contextPath +'/KQDS_CostOrderAct/getAll.act'
	}else if($(obj).text()=="明细"){
		pageurl = contextPath +'/KQDS_CostOrder_DetailAct/getAll.act'
	}
	$('#table').bootstrapTable('destroy');
	initTable(pageurl);
}

//判断当前人员权限
var total=load.concat(allowed);
function isExist(total) {
    var exist = {};
    for(var i in total) { 
        if(exist[total[i]]) {
            return true;
        }
        exist[total[i]] = true;
    }
    return false;
}

$(document).ready(function () {
    $(".box h4").toggle(function () {
      $(this).next(".text").animate({ height: 'toggle', opacity: 'toggle' }, "slow");
    }, function () {
      $(this).next(".text").animate({ height: 'toggle', opacity: 'toggle' }, "slow");
    });
  });
//带参数刷新表格
function refreshTable(){
	$('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
function initTable(requrl) {
	$('#table').html("");
	if(show1=="接诊"){
		$('#table').bootstrapTable({
	        url: requrl,
	        queryParams: queryParamsB,
	        dataType: "json",
	        pagination: true,//是否显示分页（*）
	        pageSize: 25,
	        pageList : [10, 15, 20, 25],//可以选择每页大小
	        //clickToSelect: false,
	        singleSelect: true,
	        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
	        paginationShowPageGo: true,
	        onLoadSuccess: function(data) { //加载成功时执行
	        	
	        	//判断登录
	        	var existornot=isExist(total);
	        	if(!existornot){
	        		$('#table').bootstrapTable('hideColumn', 'devchannel');
	        		$('#table').bootstrapTable('hideColumn', 'nexttype');
	        		$('#table').bootstrapTable('hideColumn', 'devitemdesc');
	        		$('#table').bootstrapTable('hideColumn', 'slgj');
	        		$('#tool').attr('class','toole').attr('style','display:none');
	        		$('#toolSon').attr('class','toolSone').attr('style','display:none');
	        		$('#accept').attr('class','accepte').attr('style','display:none');
	        	}else{
	        		/* console.log("-----------！！登录"); */
	        	}      
	        	
	            /* if(nowpage == 0 && data.total>0){ */
	            	/* maxpage = Math.floor(data.total/pagesize)+1;  */
	                var content = '';
	                var ts = 2;
	                for (var prop in data.jzfl){
	                	ts ++;
	                }
	                content += '<li>总数:<span>'+data.total+'</span></li>';
	                content += '<li>成交数:<span>'+data.cjtotal+'</span></li>';
	                for (var prop in data.jzfl){
	                	if(data.jzfl[prop] == 0){
	                		continue;
	                	}
	                	content += '<li>'+prop+' '+data.jzfl[prop]+'</li>'
	                /* } */
	                
	                $("#dataCount").html(content);
	        	 }
	             if(data.total == 0){
	            	 $("#dataCount").html(''); 
	      		 }
	        	//分页加载
	        	/* showdata("table",data.rows); */
	        	//计算主体的宽度
	            setWidth();
	            setHeight();
	        	/*表格载入时，设置表头的宽度 */
	            setTableHeaderWidth(".tableBox");
	        	if(!jzcx_invoice_Flag){
		            $('#table').bootstrapTable('hideColumn', 'invoice');
	        	}
	        },
	        rowStyle: function(row, index) {
	            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
	            var strclass = "";
	            if (Number(row.del) > 0) {
	                strclass = 'warning'; //还有一个active
	            } else {
	                return {};
	            }
	            return {
	                classes: strclass
	            };
	        },
	        columns: [{
	            field: ' ',
	            checkbox: true,
	            formatter: stateFormatter
	        },
	        {
	            title : '序号',
	            align: "center",
	            width: 40,
	            formatter: function (value, row, index) {
	             /* return index + 1; */
	             var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
	                var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
	                return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
	            }
	        },
	        {
	            title: '挂号时间',
	            field: 'createtime',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span>' + value.substring(0) + '</span>';
	            }
	        },
	        {
	            title: '患者编号',
	            field: 'usercode',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span title="' + value + '">' + value + '</span>';
	            }
	        },
	        {
	            title: '病历号',
	            field: 'blcode',
	            align: 'center',
	            valign: 'middle',
	            sortable: true,
	            visible: false,
	            formatter: function(value, row, index) {
	                return '<span class="name" title="' + value + '">' + value + '</span>';
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
	            title: '标识',
	            field: 'iscreatelclj',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	            	//console.log("value="+value+",row="+JSON.stringify(row)+",index="+index);
	            	var html = "";
	            	
	            	if (value != "" && value != null) {
	                    html='<img class="iscreatelclj" style="height: 18px;width: 18px;margin-top: 3px;" onclick="skip(\'' + row.username + '\',\'' + row.usercode +'\')" src= ' +contextPath + '/static/image/kqdsFront/tag/clinical.jpg/>';
	                }
	            	if(jzcx_invoice_Flag){
	            		if(row.invoice!=0){
	            			html += '<img class="iscreatelclj" style="height: 17px;width: 17px;margin-top: 3px;" src= ' +contextPath + '/static/image/kqdsFront/tag/invoice.jpg/>';
	            		}
	            	 }
	            	 return html;
	            },
	            cellStyle:{
	        		css:{"display":"flex","flex-direction": "row"}
	        	}
	        },
	        {
	            title: '手机号码1',
	            field: 'phonenumber1',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value != null || value != "") {
	                    if (canlookphone == 0) {
	                        return '<span title="' + value + '">' + value + '</span>';
	                    } else {
	                        return '<span>-</span>';
	                    }
	                } else {
	                    return '<span>-</span>';
	                }
	            }
	        },
	        {
	            title: '性别',
	            field: 'sex',
	            align: 'center',
	            sortable: true,
	            formatter:function(value,row,index){
	            	return '<span>'+value+'</span>';
	            }
	        },
	        {
	            title: '年龄',
	            field: 'age',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value == "0") {
	                    return '<span></span>';
	                } else {
	                    return '<span>'+value+'</span>';
	                }
	            }
	        },
	        {
	            title: '省',
	            field: 'provincename',
	            align: 'center',
	            
	            sortable: true,
	            formatter:function(value){
	            	return "<span class='name'>"+value+"</span>";
	            }
	        },
	        {
	            title: '市',
	            field: 'cityname',
	            align: 'center',
	            
	            sortable: true,
	            formatter:function(value){
	            	return "<span class='name'>"+value+"</span>";
	            }
	        },
	        {
	            title: '区',
	            field: 'townname',
	            align: 'center',
	            
	            sortable: true,
	            formatter:function(value){
	            	return "<span class='name'>"+value+"</span>";
	            }
	        },
	        {
	            title: '街道',
	            field: 'streetName',
	            align: 'center',
	            
	            sortable: true,
	            formatter:function(value){
	            	return "<span class='name'>"+value+"</span>";
	            }
	        },
	        {
	            title: '客户等级',
	            field: 'important',
	            align: 'center',
	            sortable: true,
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
	        },
	        {
	            title: '成交状态',
	            field: 'cjstatus',
	            align: 'center',
	            sortable: true,
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
	            
	        },
	        {
	            title: '就诊分类',
	            field: 'recesort',
	            align: 'center',
	            sortable: true,
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
	        },
	        {
	            title: '挂号分类',
	            field: 'regsort',
	            align: 'center',
	            sortable: true,
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
	        },
	        {
	            title: '第一咨询',
	            field: 'faskperson',
	            align: 'center',
	            sortable: true,
	            formatter:function(value,row,index){  
			    	  if(value){
			        	    html = '<span class="name" title="'+value+'">'+value+'</span>';
			                return html;  
			    	  }else{
			    		  return "<span></span>";
			    	  }
		  		}
	        },
	        {
	            title: '接诊咨询',
	            field: 'askperson',
	            align: 'center',
	            sortable: true,
	            formatter:function(value,row,index){  
			    	  if(value){
			        	    html = '<span class="name" title="'+value+'">'+value+'</span>';
			                return html;  
			    	  }else{
			    		  return "<span></span>";
			    	  }
		  		}
	        },
	        {
	            title: '就诊科室',
	            field: 'regdept',
	            align: 'center',
	            sortable: true,
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
	        },
	        {
	            title: '医生',
	            field: 'doctor',
	            align: 'center',
	            sortable: true,
	            formatter:function(value,row,index){  
			    	  if(value){
			        	    html = '<span class="name" title="'+value+'">'+value+'</span>';
			                return html;  
			    	  }else{
			    		  return "<span class='name'></span>";
			    	  }
		  		}
	        },
	        {
	            title: '患者来源',
	            field: 'devchannel',
	            align: 'center',
	            sortable: true,
	            formatter:function(value){
	            	return "<span class='source'>"+value+"</span>";
	            }
	        },
	        {
	            title: '来源子分类',
	            field: 'nexttype',
	            align: 'center',
	            sortable: true,
	            formatter:function(value){
	            	return "<span class='source'>"+value+"</span>";
	            }
	        },
	        {
	            title: '受理工具', //添加受理工具类型
	            field: 'slgj',
	            align: 'center',
	            sortable: true,
	            formatter:function(value){
	            	return "<span class='source'>"+value+"</span>";
	            }
	        },
	        {
	            title: '缴费金额',
	            field: 'jf',
	            align: 'center',
	            sortable: true,
	            formatter:function(value,row,index){
	            	return '<span>'+value+'</span>'
	            }
	        },
	        {
	            title: '实收金额',
	            field: 'ssje',
	            align: 'center',
	            /* sortable: true, */
	            formatter:function(value,row,index){
	            	return '<span>'+value+'</span>'
	            }
	        },
	/*      {
	            title: '知情同意书',
	            field: 'cisprint',
	            align: 'center',
	            
	            sortable: true,
	            width: 85
	        }, */
	        {
	            title: '潜在开发项目',
	            field: 'devitemdesc',
	            align: 'center',
	            sortable: true,
	            formatter:function(value){
	            	return "<span class='source'>"+value+"</span>";
	            }
	        },
	        {
	            title: '病历',
	            field: 'ifmedrecord',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                var html = "";
	                if (row.ifmedrecord == "已填写") {
	                    html = '<span class="label label-success">已填写</span>';
	                } else {
	                    html = '<span class="label label-danger">未填写</span>';
	                }
	                return html;
	            }
	        },
	        {
	            title: '未成交原因',
	            field: 'failreason1',
	            align: 'center',
	            sortable: true,
	            formatter:function(value,row,index){  
			    	  if(value){
			        	    html = '<span class="time" title="'+value+'">'+value+'</span>';
			                return html;  
			    	  }else{
			    		  return "<span></span>";
			    	  }
		  		}
	        },
	        {
	            title: '咨询情况备注',
	            field: 'detaildesc',
	            align: 'center',
	            /* sortable: true, */
	            formatter:function(value,row,index){  
			    	  if(value){
			    		  var showVal = value;
			    		  if(value.length > 6){
			    			  showVal = value.substring(0, 6) + '...';
			    		  }
			        	  html = '<span class="time" title="'+value+'">'+showVal+'</span>';
			              return html;  
			    	  }else{
			    		  return "<span></span>";
			    	  }
		  		}
	        },
	        {
	            title: '建档人',
	            field: 'jdr',
	            align: 'center',
	            sortable: true,
	            formatter:function(value,row,index){  
			    	  if(value){
			        	    html = '<span class="name" title="'+value+'">'+value+'</span>';
			                return html;  
			    	  }else{
			    		  return "<span></span>";
			    	  }
		  		}
	        },
	        {
	            title: '开发人',
	            field: 'developername',
	            align: 'center',
	            sortable: true,
	            formatter:function(value,row,index){  
			    	  if(value){
			        	    html = '<span class="name" title="'+value+'">'+value+'</span>';
			                return html;  
			    	  }else{
			    		  return "<span></span>";
			    	  }
		  		}
	        },
	        {
	            title: '建档导医',
	            field: 'jddy',
	            align: 'center',
	            sortable: true,
	            formatter:function(value,row,index){  
			    	  if(value){
			        	    html = '<span class="name" title="'+value+'">'+value+'</span>';
			                return html;  
			    	  }else{
			    		  return "<span></span>";
			    	  }
		  		}
	        },
	        {
	            title: '挂号人',
	            field: 'createuser',
	            align: 'center',
	            sortable: true,
	            formatter:function(value,row,index){  
			    	  if(value){
			        	    html = '<span class="name" title="'+value+'">'+value+'</span>';
			                return html;  
			    	  }else{
			    		  return "<span></span>";
			    	  }
		  		}
	        },
	        {
	            title: '挂号导医',
	            field: 'dy',
	            align: 'center',
	            sortable: true,
	            formatter:function(value,row,index){  
			    	  if(value){
			        	    html = '<span class="name" title="'+value+'">'+value+'</span>';
			                return html;  
			    	  }else{
			    		  return "<span></span>";
			    	  }
		  		}
	        },
	        {
	            title: '修改/撤销',
	            field: ' ',
	            align: 'center',
	            /* sortable: true, */
	            formatter: function(value, row, index) {//field 没有值 这里不能用value值
	            	 var html = "";
	                 if (row.editreason != "" && row.editreason != null && row.del == "0") { //修改
	                     html = '<span class="label label-success" onclick="showEditreason(\'' + row.seqId + '\')" style="width:60px">已修改</span>';
	                 }
	                 if (row.editreason != "" && row.editreason != null && row.del == "1") { //修改
	                     html = '<span class="label label-success" onclick="showEditreason(\'' + row.seqId + '\')" style="width:60px">已撤销</span>';
	                 }
	                 return html;
	            }
	        },
	        {
	            title: '就诊分类',
	            field: ' ',
	            align: 'center',
	            /* sortable: true, */
	            formatter: function(value, row, index) {//field 没有值 这里不能用value值
	            	 var html = "";
	            	 if(jzcx_chufuzhenModify_Flag){
	            		 html = '<span class="chufuzhenclass" style="color:red;cursor:pointer;text-decoration:underline;" onclick="chufuzhenModify(\'' + row.seqId + '\',\'' + row.recesortvalue + '\')" style="width:60px">修改</span>';
	                 }
	            	 return html;
	            }
	        },
	        {
	            title: '挂号分类',
	            field: ' ',
	            align: 'center',
	            /* sortable: true, */
	            formatter: function(value, row, index) {//field 没有值 这里不能用value值
	            	 var html = "";
	            	 if(jzcx_updateRegModify_Flag){
	            		 html = '<span class="chufuzhenclass" style="color:red;cursor:pointer;text-decoration:underline;" onclick="updateRegModify(\'' + row.seqId + '\',\'' + row.regsortvalue + '\')" style="width:60px">修改</span>';
	                 }
	            	 return html;
	            }
	        }
	       ]
	    }).on('click-row.bs.table',
	    function(e, row, element) {
	        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
	        $(element).addClass('success'); //添加当前选中的 success样式用于区别
	        var index = $('#table').find('tr.success').data('index'); //获得选中的行
	        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
//	         console.log(JSON.stringify(onclickrowOobj)+"----onclickrowOobj");
	        $('#tabIframe').attr('src', $('#tabIframe').attr('src')); //个人中心
	    });
	}else if(show1=="信息"){
		//初始化表格所在div
	    $('#table').bootstrapTable({
	        url: requrl,
	        dataType: "json",
	        pagination: true,//是否显示分页（*）
	        pageSize: 25,
	        pageList : [10, 15, 20, 25],//可以选择每页大小
	        singleSelect: false,
	        queryParams: queryParamsB,
	        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
	        paginationShowPageGo: true,
	        onLoadSuccess: function(data) { //加载成功时执行
	        	console.log(data,"data------")
	        	//判断登录
	        	var existornot=isExist(total);
	        	if(!existornot){
	        		$('#table').bootstrapTable('hideColumn', 'devchannelname');
	        		$('#table').bootstrapTable('hideColumn', 'nexttypename');
//	         		$('#tool').attr('class','toole').attr('style','display:none');
//	         		$('#toolSon').attr('class','toolSone').attr('style','display:none');
	        	}else{
	        		/* console.log("------------判断登录"); */
	        	}   
	        	
	        	//if(nowpage == 0 && data.total>0){
	        	//	maxpage = Math.floor(data.total/pagesize)+1; 
	        		$("#zong").html(data.total);
	        	// }
	        	//分页加载
	        	/* showdata("table",data.rows);
	        	$("#table").bootstrapTable("resetView",{
	        		height:$(window).outerHeight()-$(".searchWrap").outerHeight()-$("#gongzuol").outerHeight()-$(".columnWrap .columnHd").outerHeight()-35
	        	}); */
	        	//计算主体的宽度
	            setWidth();
	            setHeight();
	        	/*表格载入时，设置表头的宽度 */
	            setTableHeaderWidth(".tableBox");
	        },
	        columns: [
	        {field: ' ',checkbox: true,formatter: stateFormatter},
	        {
	            title: 'seqId',
	            field: 'seqId',
	            align: 'center',
	            visible: false,
	            switchable: false
	        },
	        {
	            title : '序号',
	            align: "center",
	            width: 40,
	            formatter: function (value, row, index) {
	             /* return index + 1; */
	             var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
	                var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
	                return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
	            }
	        }, 
	        {
	            title: '建档时间',
	            field: 'createtime',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                	return '<span>' + value + '</span>';
	                }else{
	                	return "";
	                }
	            }
	        },
	        {
	            title: '初诊时间',
//	          field: 'lasttime',
	            field: 'regcreatetime',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                	return '<span>' + value + '</span>';
	                }else{
	                	return "";
	                }
	            }
	        },
	        {
	            title: '病人编号',
	            field: 'usercode',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    html = '<span>' + value + '</span>';
	                    return html;
	                }
	            }
	        },
	        {
	            title: '病历号',
	            field: 'blcode',
	            align: 'center',
	            valign: 'middle',
	            sortable: true,
	            visible: false,
	            formatter: function(value, row, index) {
	                return '<span class="name" title="' + value + '">' + value + '</span>';
	            }
	        },
	        {
	            title: '姓名',
	            field: 'username',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    html = '<span class="name">' + value + '</span>';
	                    return html;
	                }
	            }
	        },
	        {
	            title: '标识',
	            field: 'iscreatelclj',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	            	//console.log("value="+value+",row="+JSON.stringify(row)+",index="+index);
	            	 if (value != "" && value != null) {
	                    return '<img class="iscreatelclj" onclick="skip(\'' + row.username + '\')" src= ' +contextPath + '/static/image/kqdsFront/tag/clinical.jpg/>';
	                }
	            }
	        },
	        {
	            title: '性别',
	            field: 'sex',
	            align: 'center',
	            sortable: true,
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
	        },
	        {
	            title: '年龄',
	            field: 'age',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value == "0") {
	                    return "<span>-</span>";
	                } else {
	                    return "<span>"+value+"</span>";
	                }
	            }
	        },
	        {
	            title: '第一咨询',
	            field: 'askperson',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    html = '<span class="name">' + value + '</span>';
	                    return html;
	                }
	            }
	        },
	        {
	            title: '医生',
	            field: 'doctor',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    html = '<span class="name">' + value + '</span>';
	                    return html;
	                }
	            }
	        },
	        {
	            title: '手机号码1',
	            field: 'phonenumber1',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value != null || value != "") {
	                    if (canlookphone == 0) {
	                        return '<span title="' + value + '">' + value + '</span>';
	                    } else {
	                        return '<span>-</span>';
	                    }
	                } else {
	                    return '<span>-</span>';
	                }
	            }
	        },
	        {
	            title: '手机号码2',
	            field: 'phonenumber2',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value != null || value != "") {
	                    if (canlookphone == 0) {
	                        return '<span title="' + value + '">' + value + '</span>';
	                    } else {
	                        return '<span>-</span>';
	                    }
	                } else {
	                    return '<span>-</span>';
	                }
	            }
	        },
	        {
	            title: '患者来源',
	            field: 'devchannelname',
	            align: 'center',
	            
	            sortable: true,
	            formatter:function(value){
	            	return "<span class='source'>"+value+"</span>";
	            }
	        },
	        {
	            title: '来源子分类',
	            field: 'nexttypename',
	            align: 'center',
	            
	            sortable: true,
	            formatter:function(value){
	            	return '<span class="source">'+value+'</span>'
	            }
	        },
	        {
	            title: '省',
	            field: 'provincename',
	            align: 'center',
	            
	            sortable: true,
	            formatter:function(value){
	            	return "<span class='name'>"+value+"</span>";
	            }
	        },
	        {
	            title: '市',
	            field: 'cityname',
	            align: 'center',
	            
	            sortable: true,
	            formatter:function(value){
	            	return "<span class='name'>"+value+"</span>";
	            }
	        },
	        {
	            title: '区',
	            field: 'townname',
	            align: 'center',
	            
	            sortable: true,
	            formatter:function(value){
	            	return "<span class='name'>"+value+"</span>";
	            }
	        },
	        {
	            title: '街道',
	            field: 'streetName',
	            align: 'center',
	            
	            sortable: true,
	            formatter:function(value,row,index){
	            	return '<span class="name">'+value+'</span>';
	            }
	        },
	        {
	            title: '建档人',
	            field: 'createuser',
	            align: 'center',
	            sortable: true,
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
	        },
	        {
	            title: '有无回访',
	            field: 'ywhf',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value == "无回访") {
	                  	return "<span class='label label-danger'>无回访</span>";
	                }else{
	                	return "<span class='label label-success'>有回访</span>";
	                }
	            }
	        }]
	    }).on('click-row.bs.table',
	    function(e, row, element) {
	        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
	        $(element).addClass('success'); //添加当前选中的 success样式用于区别
	        var index = $('#table').find('tr.success').data('index'); //获得选中的行
	        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
	        $('#tabIframe').attr('src', $('#tabIframe').attr('src')); //个人中心
	    });
	}else if(show1=="到诊"){
		$('#table').bootstrapTable({
	        url: pageurl,
	        queryParams: queryParamsB,
	        dataType: "json",
	        pagination: true,//是否显示分页（*）
	        pageSize: 25,
	        pageList : [10, 15, 20, 25],//可以选择每页大小
	        //clickToSelect: false,
	        singleSelect: false,
	        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
	        paginationShowPageGo: true,
	        onLoadSuccess: function(data) { //加载成功时执行
	        	
	        	//判断登录
	        	var existornot=isExist(total);
	        	if(!existornot){
	        		$('#table').bootstrapTable('hideColumn', 'devchannelname');
	        		$('#table').bootstrapTable('hideColumn', 'nexttypename');
	        		$('#table').bootstrapTable('hideColumn', 'slgj');
	        		$('#tool').attr('class','toole').attr('style','display:none');
	        		$('#toolSon').attr('class','toolSone').attr('style','display:none');
	        		$('#accept').attr('class','accepte').attr('style','display:none');
	        	}else{
	        		/* console.log("------------！！登录"); */
	        	} 
	        	
	        	//console.log(data);
	            var tableList = data.nums[0];
	            $("#zong").html(data.total);
	            /* var cjs = 0,
	            wcjs = 0;
	            for (var i = 0; i < tableList.length; i++) {
	                if (tableList[i].cjstatus == "1") {
	                    cjs = cjs + 1;
	                } else {
	                    wcjs = wcjs + 1;
	                }
	            } */
	            $("#cj").html(tableList.cjstatus);
	            $("#wcj").html(Number(data.total)-Number(tableList.cjstatus));
	          	//计算主体的宽度
	            setWidth();
	            setHeight();
	            /*表格载入时，设置表头的宽度 */
	            setTableHeaderWidth(".tableBox");
	        },
	        rowStyle: function(row, index) {
	            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
	            var strclass = "";
	            if (Number(row.del) > 0) {
	                strclass = 'active'; //还有一个active
	            } else {
	                return {};
	            }
	            return {
	                classes: strclass
	            };
	        },
	        columns: [
				{
				    title : '序号',
				    align: "center",
				    width: 40,
				    formatter: function (value, row, index) {
				     /* return index + 1; */
				     var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
				        var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
				        return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
				    }
				   },
				/* {
					    title: 'seqId',
					    field: 'seqId',
					    align: 'center',
					    visible: true,
					    switchable: false,
					    formatter: function(value, row, index) {
					        if (value != "" && value != null) {
					            return '<span title="' + value + '">' + value + '</span>';
					        }else{
					        	return '<span></span>';
					        }
					    }
					},
			        
			        {title: 'del',field: 'del',align: 'center',sortable: true,
			            formatter: function(value, row, index) {
			                return '<span>' + value + '</span>';
			            }
			        }, 
			           */
			        {
			        	title: '挂号时间',
			        	field: 'createtime',
			        	align: 'center',
			        	sortable: true,
			            formatter: function(value, row, index) {
			                return '<span title="' + value + '">' + value + '</span>';
			            }
			        },
			        {
			        	title: '就诊分类',
			        	field: 'recesortname',
			        	align: 'center',
			        	sortable: true,
			            formatter: function(value, row, index) {
			                return '<span class="name" title="' + value + '">' + value + '</span>';
			            }
			        },
			        {
			        	title: '挂号分类',
			        	field: 'regsortname',
			        	align: 'center',
			        	sortable: true,
			            formatter: function(value, row, index) {
			                if (value != null || value != "") {
			                    if (canlookphone == 0) {
			                        return '<span title="' + value + '">' + value + '</span>';
			                    } else {
			                        return '<span>-</span>';
			                    }
			                } else {
			                    return '<span>-</span>';
			                }
			            }
			        },
			        {
			        	title: '患者编号',
			        	field: 'usercode',
			        	align: 'center',
			        	sortable: true,
			        	formatter:function(value,row,index){
			        		return '<span>'+value+'</span>';
			        	}
			        	
			        },
			        {
			        	title: '姓名',
			        	field: 'username',
			        	align: 'center',
			        	sortable: true,
			            formatter: function(value, row, index) {
			                if (value == "0") {
			                    return '<span>-</span>';
			                } else {
			                    return '<span>'+value+'<span>';
			                }
			            }
			        },
			        {
			            title: '标识',
			            field: 'iscreatelclj',
			            align: 'center',
			            sortable: true,
			            formatter: function(value, row, index) {
			            	//console.log("value="+value+",row="+JSON.stringify(row)+",index="+index);
			            	 if (value != "" && value != null) {
			                    return '<img class="iscreatelclj" onclick="skip(\'' + row.username + '\')" src= ' +contextPath + '/static/image/kqdsFront/tag/clinical.jpg/>';
			                }
			            }
			        },
			        {
			        	title: '性别',
			        	field: 'sex',
			        	align: 'center',
			        	sortable: true,
			        },
			        {
			        	title: '年龄',
			        	field: 'age',
			        	align: 'center',
			        	sortable: true,
			        	 formatter: function(value, row, index) {
				                return '<span class="name">' + value + '</span>';
				         }
			        },
			        {
			        	title: '成交状态',
			        	field: 'cjstatus',
			        	align: 'center',
			        	sortable: true,
			            formatter: function(value, row, index) {
			            	var html = "";
			                if (value == "0") {
			                    html = '<span class="label label-danger">未成交</span>';
			                } else if (value == "1") {
			                    html = '<span class="label label-success">已成交</span>';
			                }
			                return html;
			            }
			        },
			        {
			        	title: '咨询',
			        	field: 'askpersonname',
			        	align: 'center',
			        	sortable: true,
			            formatter: function(value, row, index) {
			            	if (value) {
			                    return "<span class='name' title='" + value + "'>" + value + "</span>";
			                }
			            }
			        },
			        {
			        	title: '医生',
			        	field: 'doctorname',
			        	align: 'center',
			        	sortable: true,
			        	formatter: function(value, row, index) {
			        		if (value) {
			                    return "<span class='name' title='" + value + "'>" + value + "</span>";
			                }
				        }
			        },
			        {
			        	title: '修复医生',
			        	field: 'repairdoctorname',
			        	align: 'center',
			        	sortable: true,
			        	 formatter: function(value, row, index) {
			        		 if (value) {
			                     return "<span class='name' title='" + value + "'>" + value + "</span>";
			                 }
				         }
			        }, 
			        {
			        	title: '情况备注',
			        	field: 'remark',
			        	align: 'center',
			        	sortable: true,
			        	 formatter: function(value, row, index) {
			        		 return '<span>' + value + '</span>';
				         }
			        },
			        {
			        	title: '患者来源',
			        	field: 'devchannelname',
			        	align: 'center',
			        	sortable: true,
			        	 formatter: function(value, row, index) {
			        		 return '<span>' + value + '</span>';
				         }
			        },
			        {
			        	title: '来源子分类',
			        	field: 'nexttypename',
			        	align: 'center',
			        	sortable: true,
			        	formatter: function(value, row, index) {
			        	     return '<span>' + value + '</span>';
				        }
			        },
			        {	
			        	title: '受理工具',
			            field: 'slgj',
			            align: 'center',
			            
			            sortable: true,
			            formatter: function(value, row, index) {
			            	return '<span>' + value + '</span>';
			         	}
			        },
			        
			        {
			        	title: '病历',
			        	field: 'ifmedrecord',
			        	align: 'center',
			        	sortable: true,
			        	formatter: function(value, row, index) {
			        		 var html = "";
			                 if (row.ifmedrecord == "1") {
			                     html = '<span class="label label-success">已填写</span>';
			                 } else {
			                     html = '<span class="label label-danger">未填写</span>';
			                 }
			                 return html;
				         }
			        },
			        {
			        	title: '缴费',
			        	field: 'ifcost',
			        	align: 'center',
			        	sortable: true,
			        	formatter: function(value, row, index) {
			        		 var html = "";
			                 if (row.ifcost == "1") {
			                     html = '<span class="label label-success">已缴费</span>';
			                 } else {
			                     html = '<span class="label label-danger">未缴费</span>';
			                 }
			                 return html;
				         }
			        },
			        {
			        	title: '挂号人员',
			        	field: 'createusername',
			        	align: 'center',
			        	sortable: true,
			            formatter: function(value, row, index) {
			            	if (value) {
			                    return "<span class='name' title='" + value + "'>" + value + "</span>";
			                }
			            }
			        },
			        {
			        	title: '挂号导医',
			        	field: 'receivenoname',
			        	align: 'center',
			        	sortable: true,
			            formatter: function(value, row, index) {
			            	if (value) {
			                    return "<span class='name' title='" + value + "'>" + value + "</span>";
			                } else {
			                    return '<span>-</span>';
			                }
			            }
			        }
	        ]
	    }).on('click-row.bs.table',
	    function(e, row, element) {
	        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
	        $(element).addClass('success'); //添加当前选中的 success样式用于区别
	        var index = $('#table').find('tr.success').data('index'); //获得选中的行
	        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
	        //showpersoninfo(onclickrowOobj);//展示右侧个人信息
	        $('#tabIframe').attr('src', $('#tabIframe').attr('src')); //个人中心
	    });
	}else if(show1=="费用"){
		$('#table').bootstrapTable({
	        url: pageurl,
	        dataType: "json",
	        pagination: true,//是否显示分页（*）
	        pageSize: 25,
	        pageList : [10, 15, 20, 25],//可以选择每页大小
	        queryParams: queryParamsB,
	        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
	        paginationShowPageGo: true,
	        onLoadSuccess: function(data) { //加载成功时执行
	        	//判断登录
	        	var existornot=isExist(total);
	        	if(!existornot){
	        		$('#table').bootstrapTable('hideColumn', 'devchannel');
	        		$('#table').bootstrapTable('hideColumn', 'nexttype');
	        		$('#tool').attr('class','toole').attr('style','display:none');
	        		$('#toolSon').attr('class','toolSone').attr('style','display:none');
	        	}else{
	        		
	        	} 
	        	
	            if(data.total>0){
	        		 //maxpage = Math.floor(data.total/pagesize)+1; 
	        		 $("#xiaoji").html(data.totalcost);
	                 $("#mian").html(data.voidmoney);
	                 $("#ying").html(data.shouldmoney);
	                 $("#xian").html(data.actualmoney);
	                 $("#qian").html(data.y2); 
	        	 }
	        	 if(data.total == 0){
	        		 $("#xiaoji").html("0");
	                 $("#mian").html("0");
	                 $("#ying").html("0");
	                 $("#xian").html("0");
	                 $("#qian").html("0"); 
	        	 } 
	        	//分页加载
	        	/* showdata("table",data.rows);
	        	$("#table").bootstrapTable("resetView",{
	        		height:$(window).outerHeight()-$(".searchWrap").outerHeight()-$("#gongzuol").outerHeight()-$(".columnWrap .columnHd").outerHeight()-55
	        	}); */
	        	//计算主体的宽度
	            setWidth();
	            setHeight();
	        	/*表格载入时，设置表头的宽度 */
	            setTableHeaderWidth(".tableBox");
	        },
	        rowStyle: function(row, index) {
	            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
	            var strclass = "";
	            if (Number(row.actualmoney) < 0 || row.actualmoney.indexOf("-") >= 0) {
	                strclass = 'danger'; //还有一个active
	            } else {
	                return {};
	            }
	            return {
	                classes: strclass
	            };
	        },
	        columns: [
			 {
	            title : '序号',
	            align: "center",
	            width: 40,
	            formatter: function (value, row, index) {
	             /* return index + 1; */
	             var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
	                var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
	                return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
	            }
	        },          
	         {
	            title: '消费门诊',
	            field: 'organizationname',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span title="' + value + '">' + value + '</span>';
	            }
	        },{
	            title: '收费时间',
	            field: 'sftime',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                 if (value.indexOf("null") > -1) {
	                    return "";
	                } else {
	                    var sftime = value.substring(0, 16);
	                    return '<span>' + sftime + '</span>';
	                } 
	            }
	        },
	        {
	            title: '患者编号',
	            field: 'usercode',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span title="' + value + '">' + value + '</span>';
	            }
	        },
	        {
	            title: '病历号',
	            field: 'blcode',
	            align: 'center',
	            valign: 'middle',
	            sortable: true,
	            visible: false,
	            formatter: function(value, row, index) {
	                return '<span class="name" title="' + value + '">' + value + '</span>';
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
	            title: '电话',
	            field: 'phonenumber1',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (canlookphone == 0) {
	                    return '<span title="' + value + '">' + value + '</span>';
	                } else {
	                    return '-';
	                }

	            }
	        },
	        {
	            title: '小计',
	            field: 'totalcost',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span class="money">' + value + '</span>';
	            }
	        },
	        {
	            title: '免除',
	            field: 'voidmoney',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span class="money">' + value + '</span>';
	            }
	        },
	        {
	            title: '应收',
	            field: 'shouldmoney',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span class="money">' + value + '</span>';
	            }
	        },
	        {
	            title: '欠费',
	            field: 'y2',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span class="money">' + value + '</span>';
	            }
	        },
	        {
	            title: '缴费',
	            field: 'actualmoney',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span class="money">' + value + '</span>';
	            }
	        },
	        {
	            title: '就诊分类',
	            field: 'recesort',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    return '<span class="name" title="' + value + '">' + value + '</span>';
	                } else {
	                    return '';
	                }
	            }
	        },
	        {
	            title: '挂号分类',
	            field: 'regsort',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    return '<span class="name" title="' + value + '">' + value + '</span>';
	                } else {
	                    return '';
	                }
	            }
	        },
	        {
	            title: '成交情况',
	            field: 'cjstatus',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value == "已成交") {
	                    return '<span style="color:green">已成交</span>';
	                } else {
	                    return '<span style="color:red">未成交</span>';
	                }
	            }
	        },
	        {
	            title: '第一咨询',
	            field: 'faskperson',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    return '<span class="name" title="' + value + '">' + value + '</span>';
	                } else {
	                    return '';
	                }
	            }
	        },
	        {
	            title: '接诊咨询',
	            field: 'askperson',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    return '<span class="name" title="' + value + '">' + value + '</span>';
	                } else {
	                    return '';
	                }
	            }
	        },
	        {
	            title: '就诊科室',
	            field: 'regdept',
	            align: 'center',
	            valign: 'middle',
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span>' + value + '</span>';
	            }
	        },
	        {
	            title: '医生',
	            field: 'doctorname',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    return '<span class="name" title="' + value + '">' + value + '</span>';
	                } else {
	                    return '';
	                }
	            }
	        },
	        {
	            title: '护士1',
	            field: 'nurse',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    return '<span class="name" title="' + value + '">' + value + '</span>';
	                } else {
	                    return '';
	                }
	            }
	        },
	        {
	            title: '护士2',
	            field: 'techperson',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    return '<span class="name" title="' + value + '">' + value + '</span>';
	                } else {
	                    return '';
	                }
	            }
	        },
	        {
	            title: '患者来源',
	            field: 'devchannel',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    return '<span class="source" title="' + value + '">' + value + '</span>';
	                } else {
	                    return '';
	                }
	            }
	        },
	        {
	            title: '来源子分类',
	            field: 'nexttype',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    return '<span class="source" title="' + value + '">' + value + '</span>';
	                } else {
	                    return '';
	                }
	            }
	        },
	        {
	            title: '开单人',
	            field: 'createuser',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    html = '<span class="name" title="' + value + '">' + value + '</span>';
	                    return html;
	                } else {
	                    return "";
	                }
	            }
	        },
	        {
	            title: '开单时间',
	            field: 'createtime',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                html = '<span>' + value.substring(0) + '</span>';
	                return html;
	            }
	        },
	        {
	            title: '介绍人',
	            field: 'introducer',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    html = '<span class="name" title="' + value + '">' + value + '</span>';
	                    return html;
	                } else {
	                    return "";
	                }
	            }
	        },
	        {
	            title: '开发人',
	            field: 'developer',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    html = '<span class="name" title="' + value + '">' + value + '</span>';
	                    return html;
	                } else {
	                    return "";
	                }
	            }
	        },
	        {
	            title: '建档人',
	            field: 'jduser',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    html = '<span class="name" title="' + value + '">' + value + '</span>';
	                    return html;
	                } else {
	                    return "";
	                }
	            }
	        },
	        {
	            title: '建档导医',
	            field: 'jddy',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    html = '<span class="name" title="' + value + '">' + value + '</span>';
	                    return html;
	                } else {
	                    return "";
	                }
	            }
	        },
	        {
	            title: '建档时间',
	            field: 'jdtime',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value != "" && value != null) {
	                    html = '<span>' + value + '</span>';
	                    return html;
	                }
	            }
	        },
	        {
	            title: '收费人',
	            field: 'sfuser',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    html = '<span class="name" title="' + value + '">' + value + '</span>';
	                    return html;
	                } else {
	                    return "";
	                }
	            }
	        },
	        {
	            title: '收款备注',
	            field: 'remark',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value != "" && value != null) {
	                    html = '<span class="remark">' + value + '</span>';
	                    return html;
	                }
	            }
	        }

	        ]
	    }).on('click-row.bs.table',
	    function(e, row, element) {
	        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
	        $(element).addClass('success'); //添加当前选中的 success样式用于区别
	        var index = $('#table').find('tr.success').data('index'); //获得选中的行
	        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
	        var tab = $("#rightmenu").children("ul").children("li").eq(4);
	        tab.attr({
	            "class": "current"
	        });
	        var src = contextPath + "/KQDS_CostOrder_DetailAct/toCostDetail2.act?costno=" + onclickrowOobj.costno;
	        tab.attr('src', src);
	        tab.trigger("click");
	    });
	}else if(show1=="明细"){
		$('#table').bootstrapTable({
	        url: pageurl,
	        dataType: "json",
	        pagination: true,//是否显示分页（*）
	        pageSize: 25,
	        pageList : [10, 15, 20, 25],//可以选择每页大小
	        queryParams: queryParamsB,
	        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
	        paginationShowPageGo: true,
	        onLoadSuccess: function(data) { //加载成功时执行
	        	
	        	//判断登录
	        	var existornot=isExist(total);
	        	if(!existornot){
	        		$('#table').bootstrapTable('hideColumn', 'devchannel');
	        		$('#table').bootstrapTable('hideColumn', 'nexttype');
	        		$('#tool').attr('class','toole').attr('style','display:none');
	        		$('#toolSon').attr('class','toolSone').attr('style','display:none');
	        		
	        	}else{
	        		/* console.log("-----------！！登录"); */
	        	}
	        	
	            if(data.total>0){
	       		    //maxpage = Math.floor(data.total/pagesize)+1; 
	       		    $("#ying").html(data.ys);
	                $("#xian").html(data.paymoney);
	                $("#zeng").html(data.payother2);
	                $("#qian").html(data.y2);
	                $("#djq").html(data.paydjq); 
	                $("#integral").html(data.payintegral); 
		      	 }
	            if(data.total == 0){
	       		 	$("#ying").html("0");
	                $("#xian").html("0");
	                $("#zeng").html("0");
	                $("#qian").html("0");
	                $("#djq").html("0"); 
	                $("#integral").html("0"); 
	      		 }
		      	//分页加载
		      	//showdata("table",data.rows);
		      	//计算主体的宽度
		        setWidth();
		        setHeight();
		      	/*表格载入时，设置表头的宽度 */
		        setTableHeaderWidth(".tableBox");
		      	//付款方式赋值
		        getFkfsField();
	        },
	        rowStyle: function(row, index) {
	            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
	            var strclass = "";
	            if (row.istk == "1") {
	                strclass = 'danger'; //还有一个active
	            } else {
	                return {};
	            }
	            return {
	                classes: strclass
	            };
	        },
	        columns: [
	        {
	            title : '序号',
	            align: "center",
	            width: 40,
	            formatter: function (value, row, index) {
	             /* return index + 1; */
	             var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
	                var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
	                return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
	            }
	        },{
	            title: '消费门诊',
	            field: 'organization',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span title="' + value + '">' + value + '</span>';
	            }
	        },{
	            title: '收费时间',
	            field: 'sftime',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value.indexOf("null") > -1) {
	                    return "<span></span>";
	                } else {
	                    var sftime = value.substring(0, 16);
	                    return '<span>' + sftime + '</span>';
	                }
	            }
	        },{
	            title: '治疗状态',
	            field: 'kaifa',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value=='已治疗') {
	                    return "<span>已治疗</span>";
	                } else {
	                	return "<span>未治疗</span>";
	                }
	            }
	        },{
	            title: '治疗时间',
	            field: 'zltime',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	            	if (value) {
	                    var sftime = value.substring(0, 16);
	                    return '<span>' + sftime + '</span>';
	                }
	                return "";
	            }
	        },
	        {
	            title: '编号',
	            field: 'usercode',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span title="' + value + '">' + value + '</span>';
	            }
	        },
	        {
	            title: '病历号',
	            field: 'blcode',
	            align: 'center',
	            valign: 'middle',
	            sortable: true,
	            visible: false,
	            formatter: function(value, row, index) {
	                return '<span class="name" title="' + value + '">' + value + '</span>';
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
	            title: '电话',
	            field: 'phonenumber1',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (canlookphone == 0) {
	                    return '<span title="' + value + '">' + value + '</span>';
	                } else {
	                    return '<span>-</span>';
	                }
	            }
	        },
	        
	        {
	            title: '消费分类',
	            field: 'classname',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    return '<span class="remark" title="' + value + '">' + value + '</span>';
	                } else {
	                    return '<span class="remark"></span>';
	                }
	            }
	        },
	        {
	            title: '二级消费分类',
	            field: 'nextname',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    return '<span class="source" title="' + value + '">' + value + '</span>';
	                } else {
	                    return '<span class="source"></span>';
	                }
	            }
	        },
	        {
	            title: '消费项目',
	            field: 'itemname',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    return '<span style="width:140px;" class="remark" title="' + value + '">' + value + '</span>';
	                } else {
	                    return '<span style="width:140px;"></span>';
	                }
	            }
	        },
	        {
	            title: '单位',
	            field: 'unit',
	            align: 'center',
	            
	            sortable: true,
			    formatter:function(value){
			    	return  '<span>'+value+'</span>';
			    }
	        },
	        {
	            title: '数量',
	            field: 'num',
	            align: 'center',
	            
	            sortable: true,
			    formatter:function(value){
			    	return  '<span>'+value+'</span>';
			    }
	        },
	        {
	            title: '折扣',
	            field: 'discount',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span class="money">'+value+'</span>';
	            }
	        },
	        {
	            title: '小计',
	            field: 'subtotal',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span class="money">' + value + '</span>';
	            }
	        },
	        {
	            title: '免除',
	            field: 'voidmoney',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span class="money">' + value + '</span>';
	            }
	        },
	        {
	            title: '应收', /** 应收=小计-免除  **/
	            field: 'ys',
	            align: 'center',
	            /* 
	            sortable: true, */
	            formatter: function(value, row, index) {
	                if (value) {
	                    return '<span class="money">' + value + '</span>';
	                } else {
	                    return '<span class="money">0.0</span>';
	                }
	            }
	        },
	        {
	            title: '欠费',
	            field: 'y2',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span class="money">' + value + '</span>';
	            }
	        },
	        {
	            title: '赠送使用',
	            field: 'payother2',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value == null) {
	                    return '<span class="money">0.0</span>';
	                } else {
	                    return '<span class="money">' + value + '</span>';
	                }
	            }
	        },
	        {title: '代金券',field: 'paydjq',align: 'center',sortable: true,
				formatter:function(value,row,index){
					if(value==null){
						return '<span class="money">0.0</span>' ;
					}else{
						return '<span class="money">'+value+'</span>' ;
					}
				}
			},
			{title: '积分使用',field: 'payintegral',align: 'center',sortable: true,
				formatter:function(value,row,index){
					if(value==null){
						return '<span class="money">0.0</span>' ;
					}else{
						return '<span class="money">'+value+'</span>' ;
					}
				}
			},
	        {
	            title: '实收',
	            field: 'paymoney',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span class="money">' + value + '</span>';
	            }
	        },
	        {
	            title: '现金',
	            field: 'payxj',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value == null) {
	                    return '<span class="money">0.0</span>';
	                } else {
	                    return '<span class="money">' + value + '</span>';
	                }
	            }
	        },
	        {
	            title: '预交金使用',
	            field: 'payyjj',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value == null) {
	                    return '<span class="money">0.0</span>';
	                } else {
	                    return '<span class="money">' + value + '</span>';
	                }
	            }
	        },
	        {
	            title: '银行卡',
	            field: 'paybank',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value == null) {
	                    return '<span class="money">0.0</span>';
	                } else {
	                    return '<span class="money">' + value + '</span>';
	                }
	            }
	        },
	        {
	            title: '医保',
	            field: 'payyb',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value == null) {
	                    return '<span class="money">0.0</span>';
	                } else {
	                    return '<span class="money">' + value + '</span>';
	                }
	            }
	        },
	        {
	            title: '微信',
	            field: 'paywx',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value == null) {
	                    return '<span class="money">0.0</span>';
	                } else {
	                    return '<span class="money">' + value + '</span>';
	                }
	            }
	        },
	        {
	            title: '支付宝',
	            field: 'payzfb',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value == null) {
	                    return '<span class="money">0.0</span>';
	                } else {
	                    return '<span class="money">' + value + '</span>';
	                }
	            }
	        },
	        {title: '么么贷',field: 'paymmd',align: 'center',sortable: true,
				formatter:function(value,row,index){
					if(value==null){
						return '<span class="money">0.0</span>' ;
					}else{
						return '<span class="money">'+value+'</span>' ;
					}
				}
			},
			{title: '百度分期',field: 'paybdfq',align: 'center',sortable: true,
				formatter:function(value,row,index){
					if(value==null){
						return '<span class="money">0.0</span>' ;
					}else{
						return '<span class="money">'+value+'</span>' ;
					}
				}
			},
	        {
	            title: '其他',
	            field: 'payother1',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value == null) {
	                    return '<span class="money">0.0</span>';
	                } else {
	                    return '<span class="money">' + value + '</span>';
	                }
	            }
	        },
	        {
	            title: '特殊项目',
	            field: 'istsxm',
	            align: 'center',
	            valign: 'middle',
	            sortable: true,
			    formatter:function(value){
			    	return  '<span>'+value+'</span>';
			    }
	        },
	        {
	            title: '就诊分类',
	            field: 'recesort',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span class="name" title="' + value + '">' + value + '</span>';
	            }
	        },
	        {
	            title: '挂号分类',
	            field: 'regsort',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span class="name" title="' + value + '">' + value + '</span>';
	            }
	        },
	        {
	            title: '成交情况',
	            field: 'cjstatus',
	            align: 'center',
	            sortable: true,
			    formatter:function(value){
			    	return  '<span>'+value+'</span>';
			    }
	        },
	        {
	            title: '第一咨询',
	            field: 'faskperson',
	            align: 'center',
	            
	            sortable: true,
	            formatter:function(value,row,index){
	            	return '<span class="name">'+value+'</span>';
	            }
	        },
	        {
	            title: '接诊咨询',
	            field: 'askperson',
	            align: 'center',
	            
	            sortable: true,
	            formatter:function(value,row,index){
	            	return '<span class="name">'+value+'</span>';
	            }
	        },
	        {
	            title: '就诊科室',
	            field: 'regdept',
	            align: 'center',
	            sortable: true,
			    formatter:function(value){
			    	return  '<span>'+value+'</span>';
			    }
	        },
	        {
	            title: '医生',
	            field: 'doctor',
	            align: 'center',
	            
	            sortable: true,
	            formatter:function(value,row,index){
	            	return '<span class="name">'+value+'</span>';
	            }
	        },
	        {
	            title: '护士1',
	            field: 'nurse',
	            align: 'center',
	            
	            sortable: true,
	            formatter:function(value,row,index){
	            	return '<span class="name">'+value+'</span>';
	            }
	        },
	        {
	            title: '护士2',
	            field: 'techperson',
	            align: 'center',
	            
	            sortable: true,
	            formatter:function(value,row,index){
	            	return '<span class="name">'+value+'</span>';
	            }
	        },
	        {
	            title: '患者来源',
	            field: 'devchannel',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    return '<span class="source" title="' + value + '">' + value + '</span>';
	                } else {
	                    return '';
	                }
	            }
	        },
	        {
	            title: '来源子分类',
	            field: 'nexttype',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    return '<span class="source" title="' + value + '">' + value + '</span>';
	                } else {
	                    return '<span class="source"></span>';
	                }
	            }
	        },
	        {
	            title: '开单人',
	            field: 'kduser',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    html = '<span class="name" title="' + value + '">' + value + '</span>';
	                    return html;
	                } else {
	                    return "<span class='name'></span>";
	                }
	            }
	        },
	        {
	            title: '开单时间',
	            field: 'kdtime',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    html = '<span>' + value + '</span>';
	                    return html;
	                }
	            }
	        },
	        {
	            title: '介绍人',
	            field: 'introducer',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    html = '<span class="name" title="' + value + '">' + value + '</span>';
	                    return html;
	                } else {
	                    return "<span class='name'></span>";
	                }
	            }
	        },
	        {
	            title: '开发人',
	            field: 'developer',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    html = '<span class="name" title="' + value + '">' + value + '</span>';
	                    return html;
	                } else {
	                    return "<span></span>";
	                }
	            }
	        },
	        {
	            title: '建档人',
	            field: 'jduser',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    html = '<span class="name" title="' + value + '">' + value + '</span>';
	                    return html;
	                } else {
	                    return "<span class='name'></span>";
	                }
	            }
	        },
	        {
	            title: '建档导医',
	            field: 'jddy',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    html = '<span class="name" title="' + value + '">' + value + '</span>';
	                    return html;
	                } else {
	                    return "<span class='name'></span>";
	                }
	            }
	        },
	        {
	            title: '建档时间',
	            field: 'jdtime',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value != "" && value != null) {
	                    html = '<span title="' + value + '">' + value + '</span>';
	                    return html;
	                } else {
	                    return "<span></span>";
	                }
	            }
	        },
	        {
	            title: '收费人',
	            field: 'sfuser',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    html = '<span class="name" title="' + value + '">' + value + '</span>';
	                    return html;
	                } else {
	                    return "<span></span>";
	                }
	            }
	        },
	        {
	            title: '收款备注',
	            field: 'remark',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value != "" && value != null) {
	                    html = '<span class="remark">' + value + '</span>';
	                    return html;
	                }
	            }
	        },
	        {
	            title: '修改缴费备注',
	            field: 'detailremark',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value != "" && value != null) {
	                    html = '<span class="remark">' + value + '</span>';
	                    return html;
	                }
	            }
	        }]
	    }).on('click-row.bs.table',
	    function(e, row, element) {
	        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
	        $(element).addClass('success'); //添加当前选中的 success样式用于区别
	        var index = $('#table').find('tr.success').data('index'); //获得选中的行
	        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
	        var tab = $("#rightmenu").children("ul").children("li").eq(4);
	        tab.attr({
	            "class": "current"
	        });
	        var src = contextPath + "/KQDS_CostOrder_DetailAct/toCostDetail2.act?costno=" + onclickrowOobj.costno;
	        tab.attr('src', src);
	        tab.trigger("click");
	    });
	}
    
}

//跳转临床页面
function skip(username,usercode) {
	parent.Catalogue()
	 parent.secondLevelDirectory()
	 window.location.href=contextPath+'/ClinicPathControllerAct/toLcljPageFetchInfo.act?menuId=600309&&username='+username+'&&userId='+usercode;
}

function shuaxin(){
	/* $('#table').bootstrapTable('refresh', {
        'url': pageurl
    }); */
	window.location.reload();
}

//修改就诊分类页面跳转
function chufuzhenModify(regSeqId,recesortvalue){
	var organization=$('#organization').val();
	layer.open({
        type: 2,
        title: '修改就诊分类',
        shadeClose: false,
        shade: 0.6,
        area: ['450px', '350px'],
        content: contextPath+'/KQDS_REGAct/toChufuzhenModify.act?regSeqId='+regSeqId+'&recesortvalue='+recesortvalue+'&organization='+organization,
        //iframe的url
        end: function() {}
    });
}
//修改挂号分类页面跳转
function updateRegModify(regSeqId,regsortvalue){
	var organization=$('#organization').val();
	layer.open({
        type: 2,
        title: '修改挂号分类',
        shadeClose: false,
        shade: 0.6,
        area: ['450px', '350px'],
        content: contextPath+'/KQDS_REGAct/toUpdaeRegModify.act?regSeqId='+regSeqId+'&regsortvalue='+regsortvalue+'&organization='+organization,
        //iframe的url
        end: function() {}
    });
}

//查看修改挂号原因
function showEditreason(seqId) {
    layer.open({
        type: 2,
        title: '挂号修改原因',
        shadeClose: true,
        shade: 0.6,
        area: ['490px', '500px'],
        content: contextPath + '/KQDS_REGAct/toEditReason.act?seqId=' + seqId //iframe的url
    });
}

function queryParams() {
    nowday = getNowFormatDate();
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
   		//门诊
      	organization: $('#organization').val(),
   		// 潜在开发项目
      	devItem:$("#devItem").val(), 
        //就诊科室
        regdept:$('#regdept').val(),
   		//医生
        doctorSearch: $('#doctorSearch').val(),
   		//接诊咨询
        askpersonSearch: $('#askpersonSearch').val(),
   		//挂号时间
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
   		//客户等级
        importantSearch: $('#importantSearch').val(),
   		//患者来源
        devchannelSearch: $('#devchannelSearch').val(),
   		//子来源
        nexttype1: $('#nexttype1').val(),
   		//年龄区间
        ageSearch: $('#ageSearch').val(),
   		//挂号分类
        regsort: $('#regsort').val(),
   		//成交状态
        cjstatus: $('#cjstatus').val(),
   		//就诊分类
        recesort: $('#recesort').val(),
   		//模糊查询
        searchValue: $('#searchValue').val(),
   		//病历
        ifmedrecord: $('#ifmedrecord').val(),
   		//添加受理工具类型
        gongju : $('#gongju').val(),  
   		//第一咨询
        firstAskperson:$('#askperson').val(),
   		//创建时间
        cjstarttime: $('#cjstarttime').val(),
        
        cjendtime: $('#cjendtime').val(),
   		//初诊时间
        dystarttime: $('#dystarttime').val(),
        dyendtime: $('#dyendtime').val(),
   		//模糊查询
        username: $('#searchValue').val(),
   		//第一咨询
        askperson: $('#askperson').val(),
   		//医生
        doctor: $('#doctorSearch').val(),
   		//患者来源
        devchannel: $('#devchannelSearch').val(),
   		//子分类
        nexttype: $('#nexttype1').val(),
   		//有无回访
        ywhf: $('#ywhf').val(),
   		//建档人
        jdr: $('#jdr').val(),
   		//成交情况
        cjStatus: $('#cjStatus').val(),
   		//收费时间
        sfstarttime: $('#sfstarttime').val(),//费用和明细的时间更改
        sfendtime: $('#sfendtime').val(),
   		//预约状态
        isyjjitem:0,
   		//护士1
        nurse: $('#nurse').val(),
        //建档人
        createuser: $('#jdr').val(),
   		//收款备注
        remark : $('#remark').val(),
   		//模糊查询
        queryinput: $('#searchValue').val(),
   		//建档人
        createuserSearch: $('#jdr').val(),
   		//受理类型
        shouli: $('#shouli').val(),
   		//治疗时间
        zlstarttime: $('#zlstarttime').val(),
        zlendtime: $('#zlendtime').val(),
   		//治疗状态
        zlstatus: $('#zlstatus').val(),
   		//消费分类
        basetype: $('#basetype').val(),
   		//消费子类
        nexttype: $('#nexttype').val(),//消费子类需要修改
        //消费项目
        itemname : $('#itemname').val(),
    };
    if(show1=="接诊"){
    	temp.type=1;
    }
    if(nowday != null){
    	if(temp.sfstarttime==""){
	    	temp.sfstarttime=nowday;
        }
    	if(temp.sfendtime==""){
	    	temp.sfendtime=nowday;
        }
    	if(temp.starttime==""){
	    	temp.starttime=nowday;
        }
    	if(temp.endtime==""){
	    	temp.endtime=nowday;
        }
    	if(temp.cjstarttime==""){
	    	temp.cjstarttime=nowday;
        }
    	if(temp.cjendtime==""){
	    	temp.cjendtime=nowday;
        }
    }
    return temp;
}
function queryParamsB(params) {
    nowday = getNowFormatDate();
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)
   		//门诊
      	organization: $('#organization').val(),
   		// 潜在开发项目
      	devItem:$("#devItem").val(), 
        //就诊科室
        regdept:$('#regdept').val(),
   		//医生
        doctorSearch: $('#doctorSearch').val(),
   		//接诊咨询
        askpersonSearch: $('#askpersonSearch').val(),
   		//挂号时间
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
   		//客户等级
        importantSearch: $('#importantSearch').val(),
   		//患者来源
        devchannelSearch: $('#devchannelSearch').val(),
   		//子来源
        nexttype1: $('#nexttype1').val(),
   		//年龄区间
        ageSearch: $('#ageSearch').val(),
   		//挂号分类
        regsort: $('#regsort').val(),
   		//成交状态
        cjstatus: $('#cjstatus').val(),
   		//就诊分类
        recesort: $('#recesort').val(),
   		//模糊查询
        searchValue: $('#searchValue').val(),
   		//病历
        ifmedrecord: $('#ifmedrecord').val(),
   		//添加受理工具类型
        gongju : $('#gongju').val(),  
   		//第一咨询
        firstAskperson:$('#askperson').val(),
   		//创建时间
        cjstarttime: $('#cjstarttime').val(),
        cjendtime: $('#cjendtime').val(),
   		//初诊时间
        dystarttime: $('#dystarttime').val(),
        dyendtime: $('#dyendtime').val(),
   		//模糊查询
        username: $('#searchValue').val(),
   		//第一咨询
        askperson: $('#askperson').val(),
   		//医生
        doctor: $('#doctorSearch').val(),
   		//患者来源
        devchannel: $('#devchannelSearch').val(),
   		//子分类
        nexttype: $('#nexttype1').val(),
   		//有无回访
        ywhf: $('#ywhf').val(),
   		//建档人
        jdr: $('#jdr').val(),
   		//成交情况
        cjStatus: $('#cjStatus').val(),
   		//收费时间
        sfstarttime: $('#sfstarttime').val(),//费用和明细的时间更改
        sfendtime: $('#sfendtime').val(),
   		//预约状态
        isyjjitem:0,
   		//护士1
        nurse: $('#nurse').val(),
        //建档人
        createuser: $('#jdr').val(),
   		//收款备注
        remark : $('#remark').val(),
   		//模糊查询
        queryinput: $('#searchValue').val(),
   		//建档人
        createuserSearch: $('#jdr').val(),
   		//受理类型
        shouli: $('#shouli').val(),
   		//治疗时间
        zlstarttime: $('#zlstarttime').val(),
        zlendtime: $('#zlendtime').val(),
   		//治疗状态
        zlstatus: $('#zlstatus').val(),
   		//消费分类
        basetype: $('#basetype').val(),
   		//消费子类
        xfnexttype: $('#xfnexttype').val(),//消费子类需要修改
        //消费项目
        itemname : $('#itemname').val(),
        sortName:this.sortName,
    	sortOrder:this.sortOrder
    };    
    if(show1=="接诊"){
    	temp.type=1;
    }
    if(nowday != null){
    	if(temp.sfstarttime==""){
	    	temp.sfstarttime=nowday;
        }
    	if(temp.sfendtime==""){
	    	temp.sfendtime=nowday;
        }
    	if(temp.starttime==""){
	    	temp.starttime=nowday;
        }
    	if(temp.endtime==""){
	    	temp.endtime=nowday;
        }
    	if(temp.cjstarttime==""){
	    	temp.cjstarttime=nowday;
        }
    	if(temp.cjendtime==""){
	    	temp.cjendtime=nowday;
        }
    }
    return temp;
    //console.log("ceshi ="+temp.gongju);
}

function searchHzda() {
	loadedData = [];
	$("#dataCount").html(''); 
	nowpage = 0;
	nowday = null;
	// 潜在开发项目
  	var devItem=$("#devItem").val();
    //就诊科室
    var regdept=$('#regdept').val();
	//医生
    var doctorSearch=$('#doctorSearch').val();
	//接诊咨询
    var askpersonSearch=$('#askpersonSearch').val();
	//挂号时间
    var starttime=$('#starttime').val();
    var endtime=$('#endtime').val();
	//客户等级
    var importantSearch= $('#importantSearch').val();
	//患者来源
    var devchannelSearch=$('#devchannelSearch').val();
	//子来源
    var nexttype1=$('#nexttype1').val();
	//年龄区间
    var ageSearch=$('#ageSearch').val();
	//挂号分类
    var regsort=$('#regsort').val();
	//成交状态
    var cjstatus=$('#cjstatus').val();
	//就诊分类
    var recesort=$('#recesort').val();
	//模糊查询
    var searchValue=$('#searchValue').val();
	//病历
    var ifmedrecord=$('#ifmedrecord').val();
	//添加受理工具类型
    var gongju=$('#gongju').val();  
	//第一咨询
    var firstAskperson=$('#askperson').val();
	//创建时间
    var cjstarttime=$('#cjstarttime').val();
    var cjendtime=$('#cjendtime').val();
	//初诊时间
    var dystarttime=$('#dystarttime').val();
    var dyendtime=$('#dyendtime').val();
	//模糊查询
    var username=$('#searchValue').val();
	//第一咨询
    var askperson=$('#askperson').val();
	//医生
    var doctor=$('#doctorSearch').val();
	//患者来源
    var devchannel=$('#devchannelSearch').val();
	//子分类
    var nexttype=$('#nexttype1').val();
	//有无回访
    var ywhf=$('#ywhf').val();
	//建档人
    var jdr=$('#jdr').val();
	//成交情况
    var cjStatus=$('#cjStatus').val();
	//收费时间
    var sfstarttime=$('#sfstarttime').val();//费用和明细的时间更改
    var sfendtime=$('#sfendtime').val();
	//护士1
    var nurse=$('#nurse').val();
    //建档人
    var createuser=$('#jdr').val();
	//收款备注
    var remark= $('#remark').val();
	//模糊查询
    var queryinput=$('#searchValue').val();
	//建档人
    var createuserSearch=$('#jdr').val();
	//受理类型
    var shouli=$('#shouli').val();
	//治疗时间
    var zlstarttime=$('#zlstarttime').val();
    var zlendtime=$('#zlendtime').val();
	//治疗状态
    var zlstatus=$('#zlstatus').val();
	//消费分类
    var basetype=$('#basetype').val();
	//消费子类
    var xfnexttype=$('#xfnexttype').val();//消费子类需要修改
    //消费项目
    var itemname=$('#itemname').val();
    if (devItem == "" && regdept == "" && doctorSearch == "" && askpersonSearch == "" && starttime == "" && endtime == "" && importantSearch == ""
        && devchannelSearch == "" && nexttype1 == "" && ageSearch == "" && regsort == "" && cjstatus == "" && recesort == "" && searchValue == ""
        && ifmedrecord == "" && gongju == "" && firstAskperson == "" && cjstarttime == "" && cjendtime == "" && dystarttime == "" && dyendtime == ""
        && username == "" && askperson == "" && doctor == "" && devchannel == "" && nexttype == "" && ywhf == "" && jdr == "" && cjStatus == "" && sfstarttime == ""
        && sfendtime == "" && nurse == "" && createuser == "" && remark == "" && queryinput == "" && createuserSearch == "" && shouli == "" && zlstarttime == ""
        && zlstatus == "" && basetype == "" && xfnexttype == "" && itemname == ""
    ) {
        layer.alert('请选择查询条件!' );
        return false;
    }
    if(show1=="接诊"){
	    if (devItem != "" || regdept != "" || doctorSearch != "" || askpersonSearch != "" || starttime != "" || endtime != "" || importantSearch != "" ||
	    	    devchannelSearch != "" || nexttype1 != "" || ageSearch != "" || regsort != "" || cjstatus != "" || recesort != "" || searchValue != "" || ifmedrecord != "" ||
	    	    gongju != "" || firstAskperson != "") {
	    	$('#table').bootstrapTable('refresh', {
	            'url': pageurl
	        });
	    }
    }else if(show1=="信息"){
    	if (cjstarttime != "" || cjendtime != "" || dystarttime != "" || dyendtime != "" || username != "" || askperson != "" || doctor != "" || devchannel != "" ||
    		    nexttype != "" || ywhf != "" || jdr != "") {
    		 $('#table').bootstrapTable('refresh', {
    		        'url': pageurl
    		    });
        }
    }else if(show1=="明细"){
    	if (sfstarttime != "" || sfendtime != "" || zlstarttime != "" || zlendtime != "" || zlstatus != "" || basetype != "" || xfnexttype != "" || regdept != "" || askpersonSearch != "" ||
    		    doctor != "" || nurse != "" || createuser != "" || devchannel != "" || nexttype1 != "" || recesort != "" || regsort != "" || remark != "" || itemname != "" || queryinput != "") {
    		$('#table').bootstrapTable('refresh', {
                'url': pageurl
            });
    	}
    }else if(show1=="费用"){
        	if (cjStatus != "" || sfstarttime != "" || sfendtime != "" || askpersonSearch != "" || regdept != "" || doctor != "" || nurse != "" || createuser != "" || devchannel != "" || nexttype != "" ||
        		    recesort != "" || regsort != "" || remark != "" || queryinput != "") {
        		 $('#table').bootstrapTable('refresh', {
                     'url': pageurl
                 });
            }
           
        }else if(show1=="到诊"){
        	if(ywhf != "" || doctorSearch != "" || askperson != "" || regsort != "" || cjstatus != "" || recesort != "" || searchValue != "" || createuserSearch != "" || starttime != "" ||
        		    shouli != "" || gongju != "" || devchannel != "" || nexttype != "" || endtime != ""){
        		$('#table').bootstrapTable('refresh', {
                    'url': pageurl
                });
        	}
        	
        }
    $("#show").hide();
    $(".searchWrap .formBox>section").css("height","132px");
    $('#more').text('展开')
}
function clean() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#regsort").val("").trigger("change");
    $("#cjstatus").val("").trigger("change");
    $("#devchannelSearch").val("").trigger("change");
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
//     清空搜索下拉框
	$(".searchSelect li.selected").removeClass("selected");
  	$(".searchSelect button .pull-left").text("请选择");    
}
//导出
function exporttable() {
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
//添加加工单
function jiagong() {
    if (onclickrowOobj == "") {
        layer.alert('请选择患者!' );
        return false;
    }
    layer.open({
        type: 2,
        title: '创建加工单',
        shadeClose: false,
        shade: 0.6,
        area: ['90%', '90%'],
        content: contextPath + '/KQDS_OutProcessingSheetAct/toAdd.act?usercode=' + onclickrowOobj.usercode
    });
}
//复选框
function stateFormatter(value, row, index) {
    if (row.id === 2) {
        return {
            disabled: true,
            checked: true
        };
    }
    if (row.id === 0) {
        return {
            disabled: true,
            checked: true
        }
    }
    return value;
}
//获取选中行的usercode
function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
}
//添加任务(推广计划)
function goAddRenwu() {
    selectedrows = getIdSelections();
    //selectedrows[0]
    if (selectedrows.length == 0) {
        layer.alert('请选择需要加入推广计划的患者(可多选)' );
    } else {
        layer.open({
            type: 2,
            title: '添加推广计划',
            shadeClose: false,
            shade: 0.6,
            area: ['50%', '80%'],
            content: contextPath+'/KQDS_ExtensionAct/toExtensionAdd.act',
            end: function() {}
        });
    }
}

//生成临床路径
function createLclj() {
    if (onclickrowOobj == "") {
        layer.alert('请选择患者!' );
        return false;
    }
    layer.open({
    	type: 2,
		title: '创建手术信息',
		shadeClose: false,
		shade: 0.6,
		area: ['95%', '95%'],
		content: contextPath+'/ClinicPathControllerAct/toLcljOpreation.act?usercode=' + onclickrowOobj.usercode
    });
}

//计算界面宽高的设置
//setWidth() ,setHeight()函数移动到tableHeaderWidth.js


function getButtonPower() {
    var menubutton1 = "";
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "jzcx_tjjgd") {
            menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="jiagong()">添加加工单</a>';
        } else if (listbutton[i].qxName == "jzcx_tjtg") {
            menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="goAddRenwu()">添加推广</a>';
        }  else if (listbutton[i].qxName == "jzcx_kjfp") {
            menubutton1 += '<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="invoice()">开具发票</a>';
        } else if (listbutton[i].qxName == "jzcx_scbb") {
            menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="exporttable()">生成报表</a>';
        } else if (listbutton[i].qxName == "jzcx_sclclj") {
            menubutton1 += '<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="createLclj()">生成临床路径</a>';
        } else if (listbutton[i].qxName == "jzcx_chufuzhenmodify") {
        	jzcx_chufuzhenModify_Flag = true;
        }else if(listbutton[i].qxName == "jzcx_updateregmodify"){
        	jzcx_updateRegModify_Flag = true;
        }else if(listbutton[i].qxName == "jzcx_invoice"){
        	jzcx_invoice_Flag = true;
        }else if(listbutton[i].qxName == "jzcx_askperson"){
        	$("#jzcx_askperson").removeClass("hide");
        }
    }
    menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn clean" onclick="clean()">清空</a>';
    menubutton1 += '<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="searchHzda()">查询</a>';
    $("#bottomBarDdiv").append(menubutton1);

    setHeight();
}

//开发票
function invoice(){
	 selectedrows = getIdSelections();
	 var userCode  = [];
	 if (selectedrows.length == 0) {
	        layer.alert("请选择要开发票的患者！");
	    } else if(selectedrows.length > 1){
	    	layer.alert("每次至多只能选择一个患者开具发票！");
	    }else {
	    	layer.open({
	            type: 2,
	            title: '开具发票',
	            shadeClose: false,
	            shade: 0.6,
	            area: ['80%', '70%'],
	            content: contextPath+'/Kqds_PayCostAct/tocw_invoice.act',
	            //iframe的url
	            end: function() {}
	        });
	}
}
/* function show(){
	var today=new Date().getTime();
	//获取当前时间戳  getTime() 获取时间戳 
	var nowday1 = getNowFormatDate();
    //时间选择
    $(".birthDate").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "top-right"
    });
	var date0=new Date(nowday1+'12:00:00').getTime();
	//获取定时时间戳
	
	if(date0==today){
		var url = contextPath + '/KQDS_REGAct/selectZhcxNopage1.act';
		var param = { 
				organization: $('#organization').val(),
		        starttime: nowday1,
		        endtime: nowday1,
		};
		$.axseSubmit(url, param, function() {}, function(r) {
			layer.alert(r.retMsrg, {
	            end: function() {
	            	parent.window.location.reload(); //刷新父页面
	                var frameindex = parent.layer.getFrameIndex(window.name);
	                parent.layer.close(frameindex); //再执行关闭 
	            }
	      });
		}, function() {
		}); 
	}
}
	setInterval(show,500); */
</script>
</html>
