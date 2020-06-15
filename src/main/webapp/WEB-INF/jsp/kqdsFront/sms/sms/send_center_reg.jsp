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
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/Load.js"></script>

<title>接诊查询</title>
 <!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
</head>
<style type="text/css">
/*工作量表格 ，单独写*/

#gongzuol{margin-bottom: 15px;}
#gongzuol .columnHd{padding:0 15px;border-bottom:solid 1px #0e7ec6;font-size:16px;color: #373737;font-family: "Microsoft YaHei";line-height:36px;}
 	
	.kqds_table  td { 
		color: #666;
		padding: 2px 3px 3px 3px;
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
		font-weight: normal;
		line-height: 28px;
	}
	
	.kqds_table  select { 
		height:28px;
		width:90px;
		border:solid 1px #e5e5e5;
		border-radius: 3px;
	}
	input[type=text],.kv .kv-v input[type=text]{padding:0 10px;width:90px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}
	#table tr td.bs-checkbox {
		padding:0px 11px;
	}
/*查询条件中的样式  */
.searchWrap .formBox>section>ul.conditions>li{
	padding: 3px 0;
}
.searchWrap .formBox>section>ul.conditions>li>span{
	width:62px;
	text-align:right;
}
.searchWrap .formBox>section>ul.conditions>li>input[type=text], 
.searchWrap .formBox>section>ul.conditions>li>select{
	width:94px;
}
@media screen and (max-width:1390px){
	.searchWrap .formBox>section>ul.conditions>li>span{
		width:55px;
		text-align:right;
		font-size:11px;
		height:24px;
		line-height:24px;
	}
	.searchWrap .formBox>section>ul.conditions>li>input[type=text], 
	.searchWrap .formBox>section>ul.conditions>li>select{
		width:82px;
		font-size:11px;
		padding:0 3px 0 5px;
		height:24px;
		line-height:24px;
	}
}
@media screen and (max-width:1100px){
	.searchWrap .formBox>section>ul.conditions>li>span{
		width:51px;
		text-align:right;
		font-size:10px;
		height:22px;
		line-height:22px;
	}
	.searchWrap .formBox>section>ul.conditions>li>input[type=text], 
	.searchWrap .formBox>section>ul.conditions>li>select{
		width:73px;
		font-size:10px;
		padding:0 3px 0 5px;
		height:22px;
		line-height:22px;
	}
}	
</style>
<body>
<div id="container">
    <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
                <div class="columnHd"><span class="title">短信发送</span></div>
                <div class="columnBd">
                    <div class="tableBox">
                        <table id="table" class="table-striped table-condensed table-bordered" data-height=""></table>
                    </div>
                    <div id="gongzuol">
		                <div class="columnBd">
			                	<ul class="dataCountUl" id="dataCount">
			                		
			                	</ul>
			                </div>
			            </div>
                	</div>
            	</div>
            
            <div class="searchWrap">
	                <!-- <div class="cornerBox">查询条件</div> -->
	                <div class="searchToggleBtnGroup">
	                	<span class="ptcx checked">
	                		通用查询
	                	</span>
	                	<span>
	                		高级查询
	                	</span>
	                </div>
	                <div class="formBox">
	                	<section>
				    		<ul class="conditions">
				    			<li>
				    				<span>所属门诊</span>
				    				 <select id="organization" name="organization"></select>
				    			</li>
				    			<li>
				    				<span>挂号时间</span>
		    						<input type="text" id="starttime" name="starttime" placeholder="开始日期" readonly  class="birthDate">
			                    </li>
				    			
				    			<li>
				    				<span>到</span>
		                            <input type="text" id="endtime" name="endtime" placeholder="结束日期" readonly   class="birthDate">
			                    </li>
								<li>
				    				<span>模糊查询</span>
				    				<input style="padding:0 5px;font-size:11px;" type="text" id="searchValue" class="searchInput" placeholder="客户姓名/手机号" > 
				    			</li>
				    		
				    			<li class="toggleTr">
				    				<span>接诊咨询</span>
			    					<input type="hidden" name="askpersonSearch" id="askpersonSearch"  class="form-control"  value=""/>
								    <input  type="text"  id="askpersonSearchDesc" name="askpersonSearchDesc" placeholder="咨询" readonly  onClick="javascript:single_select_user(['askpersonSearch', 'askpersonSearchDesc'],'',1);"></input>	
				    			</li>
				    			<li class="toggleTr">
				    				<span>医生</span>
			    					<input type="hidden" name="doctorSearch" id="doctorSearch"  class="form-control"  value=""/>
								    <input type="text"  id="doctorSearchDesc" name="doctorSearchDesc" placeholder="医生" readonly  onClick="javascript:single_select_user(['doctorSearch', 'doctorSearchDesc'],'',1);"></input>	
				    			</li>
				    			<li class="toggleTr">
				    				<span>挂号分类</span>
			    					<select class="dict" tig="ghfl" name="regsort" id="regsort"></select>
				    			</li>
				    			<li class="toggleTr">
				    				<span>就诊分类</span>
				    				<select class="dict" tig="jzfl" name="recesort" id="recesort" ></select>
				    			</li>
				    			<li class="toggleTr">
				    				<span>成交状态</span>
				    				<select  name="cjstatus" id="cjstatus" >
			                          <option value="">请选择</option>
			                          <option value="0">未成交</option>
			                          <option value="1">已成交</option>
									</select>
				    			</li>
				    			<li class="toggleTr">
				    				<span>病历</span>
				    				<select   name="ifmedrecord" id="ifmedrecord" >
	                               		<option value="">请选择</option>
	                                 	<option value="1">已填写</option>
	                                 	<option value="0">未填写</option>
									</select>
				    			</li>
				    			<li class="toggleTr">
				    				<span>客户等级</span>
			    					<select  name="importantSearch" id="importantSearch" >
				                          <option value="">客户等级</option>
				                          <option value="1">一级</option>
				                          <option value="2">二级</option>
				                          <option value="3">三级</option>
				                          <option value="4">四级</option>
				                          <option value="5">五级</option>
									</select>
				    			</li>
				    			<li class="toggleTr">
				    				<span>年龄区间</span>
				    				<select name="ageSearch" id="ageSearch" >
	                                 	<option value="">年龄区间</option>
	                                 	<option value="10">0~10</option>
	                                 	<option value="20">10~20</option>
	                                 	<option value="30">20~30</option>
	                                 	<option value="40">30~40</option>
	                                 	<option value="50">40~50</option>
	                                 	<option value="51">50以上</option>
			                        </select>
				    			</li>
				    			<li class="toggleTr">
				    				<span>患者来源</span>
				    				<select class="patients-source select2 dict" tig="hzly" name="devchannelSearch" id="devchannelSearch" onchange="getSubDictSelect('devchannelSearch','nexttype1');">
									</select>
				    			</li>
				    			<li class="toggleTr">
				    				<span>子分类</span>
				    				<select class="select2 dict" name="nexttype1" id="nexttype1">
										<option value="">请选择</option>
									</select>
				    			</li>
				    			<li class="toggleTr">
				    				<span>潜在开发项目</span>
				    				 <select class="dict" tig="QZKFXM" name="devItem" id="devItem" ></select>
				    			</li>
				    			<li class="toggleTr">
				    				<span>就诊科室</span>
			    					<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_1 %>" name="regdept" id="regdept" ></select>
				    			</li>
			    			</ul>
		    			</section>
	                    <div class="btnBar" id="bottomBarDdiv" style="text-align: left;">
	                   		
			            </div>
	                </div>
	            </div>
        </div>
        <!--中间模块开关按钮  -->
        <div class="middleWrap">
			<div id="collectBtn" class="collectBtn">
				<span id="trangle"></span>
			</div>	
		</div>
        <!--右侧信息浏览-->
        <div class="lookInfoWrap">
			<%@include file="/inc/rightPartInfo.jsp" %>
		</div>
    </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/DataLazyLoad.js"></script>
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
$(function() {
	initHosSelectList4Front('organization'); // 连锁门诊下拉框	
	var tmpOrganization = $("#organization").val();
    initDeptSelectByTypesAndClass(tmpOrganization);
    $("#organization").change(function() {
        var currSelect = $(this).val();
        initDeptSelectByTypesAndClass(currSelect);
    });
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
    togglemodel.initial("jzcx",pageurl);
    //4、表格初始化
    initTable(nullUrl);
    
    $(window).resize(function() {
        setWidth();
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });

    /* 常用查询 按钮 高级查询  按钮*/
    initSearchToggleBtnGroup();
});
function initTable(requrl) {
    $('#table').bootstrapTable({
        url: requrl,
        queryParams: queryParams,
        dataType: "json",
        clickToSelect: false,
        singleSelect: false,
        onLoadSuccess: function(data) { //加载成功时执行
            if(nowpage == 0 && data.total>0){
            	maxpage = Math.floor(data.total/pagesize)+1; 
                var content = '';
                var ts = 2;
                for (var prop in data.jzfl){
                	ts ++;
                }
                content +="<li>总数:<span>"+data.total+"</span></li>";
                content +="<li>成交数:<span>"+data.cjtotal+"</span></li>";
                
                for (var prop in data.jzfl){
                	if(data.jzfl[prop] == 0){
                		continue;
                	}
                	content += '<li>'+prop+'<span>'+data.jzfl[prop]+'</span></li>'
                }
                
                $("#dataCount").html(content);
        	 }
             if(data.total == 0){
            	 $("#dataCount").html(''); 
      		 }
        	//分页加载
        	showdata("table",data.rows);
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
            title: '序号',
            field: 'Number',
            align: 'center',
            formatter:function(value, row, index){
            	return index+1;
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
            title: '姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '手机号码1',
            field: 'phonenumber1',
            align: 'center',
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
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '医生',
            field: 'doctor',
            align: 'center',
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
            title: '缴费',
            field: 'jf',
            align: 'center',
            sortable: true,
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
		        	    html = '<span class="remark" title="'+value+'">'+value+'</span>';
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
            sortable: true,
            formatter:function(value,row,index){  
		    	  if(value){
		    		  var showVal = value;
		    		  if(value.length > 6){
		    			  showVal = value.substring(0, 6) + '...';
		    		  }
		        	  html = '<span class="remark" title="'+value+'">'+showVal+'</span>';
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
            sortable: true,
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
            sortable: true,
            formatter: function(value, row, index) {//field 没有值 这里不能用value值
            	 var html = "";
            	 if(jzcx_chufuzhenModify_Flag){
            		 html = '<span class="chufuzhenclass" style="color:red;cursor:pointer;text-decoration:underline;" onclick="chufuzhenModify(\'' + row.seqId + '\',\'' + row.recesortvalue + '\')" style="width:60px">修改</span>';
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
        $('#tabIframe').attr('src', $('#tabIframe').attr('src')); //个人中心
    });
}


function shuaxin(){
	/* $('#table').bootstrapTable('refresh', {
        'url': pageurl
    }); */
	window.location.reload();
}

function chufuzhenModify(regSeqId,recesortvalue){
	layer.open({
        type: 2,
        title: '修改就诊分类',
        shadeClose: false,
        shade: 0.6,
        area: ['450px', '350px'],
        content: '<%=contextPath%>/KQDS_REGAct/toEditRegRecesort.act?regSeqId='+regSeqId+'&recesortvalue='+recesortvalue,
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
        content: contextPath + '/KQDS_REGAct/toEditRegReason.act?seqId=' + seqId 
    });
}

function queryParams() {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
   		offset : 0,
       	limit :	 pagesize,
       	ispaging : 1,
   		devItem:$("#devItem").val(), // 潜在开发项目
   		organization: $('#organization').val(),
        type: 1,
        //可以查询挂号表医生 咨询不是自己，收费项目种有自己的
        regdept:$('#regdept').val(),
        doctorSearch: $('#doctorSearch').val(),
        askpersonSearch: $('#askpersonSearch').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        importantSearch: $('#importantSearch').val(),
        devchannelSearch: $('#devchannelSearch').val(),
        nexttype1: $('#nexttype1').val(),
        ageSearch: $('#ageSearch').val(),
        regsort: $('#regsort').val(),
        cjstatus: $('#cjstatus').val(),
        recesort: $('#recesort').val(),
        searchValue: $('#searchValue').val(),
        ifmedrecord: $('#ifmedrecord').val()
    };
    return temp;
}
function searchHzda() {
	loadedData = [];
	$("#showtj").html(''); 
	nowpage = 0;
    var doctorSearch = $('#doctorSearch').val();
    var regdept = $('#regdept').val();
    var askpersonSearch = $('#askpersonSearch').val();
    var starttime = $('#starttime').val();
    var endtime = $('#endtime').val();
    var importantSearch = $('#importantSearch').val();
    var devchannelSearch = $('#devchannelSearch').val();
    var ageSearch = $('#ageSearch').val();
    var regsort = $('#regsort').val();
    var cjstatus = $('#cjstatus').val();
    var recesort = $('#recesort').val();
    var searchValue = $('#searchValue').val();
    if (regdept == "" && doctorSearch == "" && askpersonSearch == "" && starttime == "" && searchValue == "" && endtime == "" && cjstatus == "" && importantSearch == "" && devchannelSearch == ""  && ageSearch == "" && regsort == "" && cjstatus == "" && recesort == "") {
        layer.alert('请选择查询条件!' );
        return false;
    }
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
function clean() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#regsort").val("").trigger("change");
    $("#cjstatus").val("").trigger("change");
    $("#devchannelSearch").val("").trigger("change");
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
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


//计算界面宽高的设置
//setWidth() ,setHeight()函数移动到tableHeaderWidth.js

function tosend(){
	selectedrows = getIdSelections();
	if (selectedrows.length == 0) {//单个患者转诊咨询
		 layer.alert('请勾选复选框，选择患者(可多选)' );
		 return false;
	}
	layer.open({
	       type: 2,
	       title: '短信发送',
	       shadeClose: false,
	       shade: 0.6,
	       area: ['750px', '450px'],
	       content: contextPath + '/KQDS_SmsAct/toSendSms.act'
	});
}
function getButtonPower() {
    var menubutton1 = "";
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "SMS_TOSEND") {
        	menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="tosend()">短信发送</a>';
        }
    }
    menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn clean" onclick="clean()">清空</a>';
    menubutton1 += '<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="searchHzda()">查询</a>';
    
    $("#bottomBarDdiv").append(menubutton1);

    setHeight();
}
</script>
</html>
