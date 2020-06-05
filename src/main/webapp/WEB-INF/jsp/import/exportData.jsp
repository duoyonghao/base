<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/style.css">
<script type="text/javascript" src="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/upload/uploadutil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<title>数据导入</title>
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <section class="content">
            <div class="row" >
            <div class="col-sm-12">
            <div id="toolbar">
		        <!--dom结构部分-->
				<div class="child-inline-block-middle" id="uploader-demo">
					<input type="hidden"  id="imgtype"> 
					<span style="color: red;">---指定日期更新数据时：确保收费时间跟轻松牙医 收费时间一致 <br/>
					update base_jh.dbo.KQDS_COSTORDER  
					set sftime = CONVERT(varchar(100),qm.inputdate, 120)
					from base_jh.dbo.KQDS_COSTORDER ck left join qsyy.dbo.sf_main qm on qm.id = ck.seq_id;
					</span> <br/>
				    <br/><span style="color: red;">患者相关：</span> <br/>
				    <input type="checkbox"  name="model" value="documentuser"> 患者基本信息
				    <input type="checkbox"  name="model" value="wd_documentuser"> 网电患者基本信息
				    <input type="checkbox"  name="model" value="huifang"> 回访
				    <input type="checkbox"  name="model" value="setjsr"> 介绍人设置
				    <input type="checkbox"  name="model" value="setmember">会员卡余额
				    <input type="checkbox"  name="model" value="addmemberRecoed">会员卡操作记录(无excel)
				    <br/><span style="color: red;">病历相关：先导入病历基本信息</span> <br/>
				    <input type="checkbox"  name="model" value="medical"> 病历基本信息
				    <input type="checkbox"  name="model" value="medical_cure"> 病历_治疗过程
				    <input type="checkbox"  name="model" value="medical_diagnose"> 病历_诊断
				    <input type="checkbox"  name="model" value="medical_other"> 病历_X线
				    <input type="checkbox"  name="model" value="medical_check"> 病历_检查
				    <input type="checkbox"  name="model" value="medical_plan"> 病历_治疗方案
				    <br/><span style="color: red;">费用相关导入顺序：明细 - 费用 - 折扣反推 -执行sql- 欠费还款逻辑</span> <br/>
				    <input type="checkbox"  name="model" value="costorderdetail"> 费用明细
				    <input type="checkbox"  name="model" value="costorder"> 费用单
				    <input type="checkbox"  name="model" value="zkft"> 折扣反推(无excel)
				     <input type="checkbox"  name="model" value="pym"> 拼音码
				    <br>
				    <span>
					<span style="color: blue;">update KQDS_COSTORDER_DETAIL set paymoney = paymoney+voidmoney where voidmoney<0 ;</span> <br/>
					<span style="color: blue;">update KQDS_COSTORDER_DETAIL set payxj = paymoney;</span> <br/>
					<span style="color: blue;">update KQDS_COSTORDER_DETAIL set voidmoney=0 where voidmoney<0;</span> <br/>
					<span style="color: blue;">UPDATE KQDS_COSTORDER set status =2  where status =1 and y2<0 and totalcost =0;</span> <br/>
					<span style="color: blue;">UPDATE KQDS_COSTORDER_DETAIL set istsxm = c.istsxm,unit=c.unit
					from  KQDS_COSTORDER_DETAIL d LEFT JOIN  KQDS_TREATITEM c on d.itemno = c.treatitemno;</span> <br/>
					<span style="color: blue;">UPDATE KQDS_COSTORDER_DETAIL set discount = 100;</span> <br/>
					<span style="color: blue;">update KQDS_COSTORDER set createtime = sftime WHERE createtime is null or createtime ='';</span> <br/>
					<span style="color: blue;">update KQDS_COSTORDER_DETAIL set createtime = c.createtime from KQDS_COSTORDER_DETAIL d LEFT JOIN KQDS_COSTORDER c on c.costno=d.costno where (d.createtime is null or d.createtime = '');</span> <br/>
					<span style="color: blue;">update KQDS_UserDocument set AskPerson = ''  WHERE AskPerson = ' ' or AskPerson is null;</span> <br/>
					
					
					</span>
				    <!-- <input type="checkbox"  name="model" value="qfhk"> 欠费还款逻辑 -->
				    <br/><span style="color: red;">挂号导入顺序：挂号表 - 临时添加挂号（把没有关联到的费用，添加复诊；因为导入挂号表是以收费时间为准添加复诊，会有遗漏）</span> <br/>
				    <input type="checkbox"  name="model" value="setreg"> 挂号表(初诊时间)
				    <input type="checkbox"  name="model" value="addreg">临时添加挂号(无excel)<br>
				    <span style="color: blue;">
				    update KQDS_COSTORDER_DETAIL set regno = c.regno
					from KQDS_COSTORDER_DETAIL d
					LEFT JOIN KQDS_COSTORDER c on d.costno = c.costno;
					</span><br/>
					<span style="color: blue;">
					update KQDS_COSTORDER_DETAIL set AskPerson = u.askperson
					from KQDS_COSTORDER_DETAIL d 
					LEFT JOIN KQDS_UserDocument u on u.usercode = d.usercode;
					</span><br/>
					<span style="color: blue;">
					update KQDS_PAYCOST set AskPerson = u.askperson
					from KQDS_PAYCOST d 
					LEFT JOIN KQDS_UserDocument u on u.usercode = d.usercode;
					</span><br/>
				    <input type="checkbox"  name="model" value="setdoctor"> 医生设置
				    <input type="checkbox"  name="model" value="setmedical">设置病历(无excel)
				    <input type="checkbox"  name="model" value="setcjstatus">成交状态(无excel)
				    <br>
					<a id="filePicker"  class="btn btn-success" style="color: #fff;"><i class="glyphicon glyphicon-plus"></i> 导入</a>
					<br>
					<br>
				</div>
   			 </div>
             </div>
             <div class="col-sm-12">
           			 查询哪些 有消费记录 但是没有第一咨询的<br>
					select u.AskPerson,u.usercode from KQDS_UserDocument u
					where u.askperson not in (select SEQ_ID from sys_person) 
					and u.usercode in (SELECT usercode from KQDS_COSTORDER where status=2);<br>
					
					update KQDS_UserDocument set AskPerson='<span style="color:red;">wu_zixun</span>' where (AskPerson is null or AskPerson = '');<span style="color: red;">--在咨询部门，新建一个无咨询用户</span><br>
					
					--指定有消费记录无咨询 为第一咨询<br>
					update KQDS_COSTORDER_DETAIL set AskPerson = u.askperson
					from KQDS_COSTORDER_DETAIL d 
					LEFT JOIN KQDS_COSTORDER c on c.costno=d.costno
					LEFT JOIN KQDS_UserDocument u on u.usercode = d.usercode
					where c.status=2 and (d.AskPerson is null or d.AskPerson = '');<br>
					
					
					患者来源为空的 （设为其他）<br>
					update KQDS_UserDocument set DevChannel = '<span style="color:red;">238</span>' where DevChannel is null or DevChannel ='';<br>
					update KQDS_UserDocument set nexttype = '<span style="color:red;">62cb2a3c-b832-4038-8840-92e5ed94424a</span>' where nexttype is null or nexttype ='';<span style="color: red;">--这两个值要根据数据库实际情况设定</span><br>
					
					患者表 收费表 等 人员不存在的<span style="color: red;">//以下标红的是sys_person表的用户主键，要确保数据库中存在</span><br>
					update KQDS_UserDocument set doctor = '<span style="color:red;">wu_yisheng</span>' where doctor not in (select SEQ_ID from sys_person ); <br>
					update KQDS_UserDocument set askperson = '<span style="color:red;">wu_zixun</span>' where askperson not in (select SEQ_ID from sys_person ); <br>
					update KQDS_UserDocument set CreateUser = '<span style="color:red;">wu_daoyi</span>' where CreateUser not in (select SEQ_ID from sys_person ); <br>
					
					update KQDS_COSTORDER set doctor = '<span style="color:red;">wu_yisheng</span>' where doctor not in (select SEQ_ID from sys_person ); <br>
					update KQDS_COSTORDER set sfuser = '<span style="color:red;">wu_shoufei</span>' where sfuser not in (select SEQ_ID from sys_person ); <br>
					update KQDS_COSTORDER set CreateUser = '<span style="color:red;">wu_kaidan</span>' where CreateUser not in (select SEQ_ID from sys_person ); <br>
					
					update KQDS_COSTORDER_DETAIL set doctor = '<span style="color:red;">wu_yisheng</span>' where doctor not in (select SEQ_ID from sys_person ); <br>
					update KQDS_COSTORDER_DETAIL set AskPerson = '<span style="color:red;">wu_zixun</span>' where AskPerson not in (select SEQ_ID from sys_person ); <br>
					update KQDS_COSTORDER_DETAIL set CreateUser = '<span style="color:red;">wu_kaidan</span>' where CreateUser not in (select SEQ_ID from sys_person ); <br>
					
					
					update KQDS_PAYCOST set doctor = '<span style="color:red;">wu_yisheng</span>' where doctor not in (select SEQ_ID from sys_person ); <br>
					update KQDS_PAYCOST set AskPerson = '<span style="color:red;">wu_zixun</span>' where AskPerson not in (select SEQ_ID from sys_person ); <br>
					update KQDS_PAYCOST set CreateUser = '<span style="color:red;">wu_shoufei</span>' where CreateUser not in (select SEQ_ID from sys_person ); <br>
             </div>
          </div>
        </section>
<script>
var $table = $('#table');
$(function() {
	/** 轻松牙医数据导入 **/
    uploadfile(contextPath + "/kqds/act/thirdImport/qsyy/QsyyDataImportAct/dataImport.act?module=evidence");
});

//禁止页面选择，防止数据拷贝导出
$(function() {
    document.onselectstart = function() {
        return true;
    };
});
$("input[name='model']").click(function(){
	$(this).attr('checked','checked').siblings().removeAttr('checked');
	$("input[name='model']").each(function(){
	    if ($(this).is(':checked')) { 
	    	$("#imgtype").val($(this).val());
    	}
	});
});
</script>
</body>
</html>
