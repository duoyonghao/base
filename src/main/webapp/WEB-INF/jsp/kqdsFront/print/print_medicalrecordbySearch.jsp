<!-- 该页面适合快递单大小的纸质打印，已经经过测试  yangsen  17-4-25 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String seqId = request.getParameter("seqId");
	String flowName = request.getParameter("flowName");
	
	String printType = request.getParameter("printType");
	
	// 1 a4 2 a5
	if(printType == null || "".equals(printType)){
		printType = "2"; // 默认a5
	}
	
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>病历打印</title>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.PrintArea.js"></script>
<script language="javascript" src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
<style type="text/css">
table {
    border-collapse: collapse;
    border-spacing: 0;
}

th {
	text-align: left;
	vertical-align: middle;
	border: 1px solid #bbb;
}

tr{
	height:30px;
	border: 1px solid #bbb;
}

td{
	border: 1px solid #bbb;
}

span {
	font-size: 18px;
}

</style>
</head>
<body>
 <div id="printArea">
 	<div style="text-align: center;width: 95%;margin: auto;">
		<span id="titles" style="font-size: 20px;"><%=ChainUtil.getOrgPrintName(request)%><%=flowName%></span>
	</div>
	
	<div id="hide" style="width: 95%;margin: auto;">
		<hr>
       		<div align="left">
       			<span>姓名：</span><span id="username"></span>&nbsp;&nbsp;&nbsp;&nbsp; 
			    <span>性别：</span><span id="sex"></span>&nbsp;&nbsp;&nbsp;&nbsp; 
			    <span>年龄：</span><span id="age"></span>&nbsp;&nbsp;&nbsp;&nbsp; 
			    <span>病历编号：</span><span id="seqId"></span>&nbsp;&nbsp;&nbsp;&nbsp;
       		</div> 
		<hr>
	</div>
	<div id="contents" style="width:95%;margin: auto;" >
	</div>
      
	<div id="footer" style="text-align: left;width:95%;margin: auto;">
		<hr>
		<span>填写时间：</span><span id="createtime"></span>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<span>填写医生：</span><span id="createuser"></span>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<span>签章:</span><span></span>
	</div> 
   
</div>
<div style="width:95%;margin: auto;text-align: center;">
	<%
		if("1".equals(printType)){ // a4
			%>
			<input value="打印" onclick="PrintNoBorderTableA4()" type="button">
			<%
		}else{ // a5
			%>
			<input value="打印" onclick="PrintNoBorderTable()" type="button">
			<%
		}
	%>
</div>
<script>
var seqId="<%=seqId%>";
$(function(){
	 getdata();
});

function print(){  
	$("#printArea").printArea(); 
} 

var LODOP; //声明为全局变

// a5打印
function PrintNoBorderTable(){
	LODOP = getLodop();  
	LODOP.PRINT_INIT("UNIC管理系统_LODOP打印控件");
	LODOP.SET_PRINT_PAGESIZE(1,2100,1485,"");
	
	//表格
	LODOP.ADD_PRINT_TABLE(70,"3%","95%","280",document.getElementById("contents").innerHTML);
	
	//页眉
	LODOP.ADD_PRINT_TEXT(3,250,300,20,"<%=ChainUtil.getOrgPrintName(request)%><%=flowName%>");
    LODOP.SET_PRINT_STYLEA(0,"FontSize",15);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);//固定标题,设置卫页眉页脚
	LODOP.SET_PRINT_STYLEA(0,"Horient",2);
	LODOP.SET_PRINT_STYLEA(0,"Bold",1);
	//姓名时间栏目
	LODOP.ADD_PRINT_HTM(25,"3%","100%","100%",document.getElementById("hide").innerHTML);  
	LODOP.SET_PRINT_STYLEA(0,"LinkedItem",4);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
	LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",3);	
	
	LODOP.ADD_PRINT_HTM(1,550,300,100,"页码：<font color='#0000ff' ><span tdata='pageNO'>#</span>/<span tdata='pageCount'>#</span></font>");
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",1);	
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);		
	
	// 页脚
	LODOP.ADD_PRINT_HTM("105mm","6mm","100%","100%",document.getElementById("footer").innerHTML);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1); // 设置为页眉页脚
	LODOP.SET_PRINT_STYLEA(0,"Horient",1);	
	LODOP.PREVIEW();
	
/*
	SET_PRINT_STYLEA 方法
	名称：(扩展型) 设置打印项风格A,继承SET_PRINT_STYLE的所有属性
	格式：
	SET_PRINT_STYLEA(varItemNameID, strStyleName,varStyleValue)
	功能：类似函数SET_PRINT_ STYLE的功能，二者的区别是本函数只对单个打印项有效。
	参数：
	varItemNameID：要设置的目标项序号或项目名，数字型或字符型。
	数字型时，表示是序号，以其增加的先后自然顺序为准，从1开始，所有打印对象都参与排序，包括超文本、纯文本、图片、图线、图表、条码等。
	 如果序号为0，代表当前（最后加入的那个）数据项；如果序号是负数，代表前面加入的数据项，该值为前移个数偏移量。 
 
	ItemType的值：数字型，0--普通项 1--页眉页脚 2--页号项 3--页数项 4--多页项 
	缺省（不调用本函数时）值0。普通项只打印一次；页眉页脚项则每页都在固定位置重复打印；页号项和页数项是特殊的页眉页脚项，其内容包含当前页号和全部页数；多页项每页都打印，
	直到把内容打印完毕，打印时在每页上的位置和区域大小固定一样（多页项只对纯文本有效）
            在页号或页数对象的文本中，有两个特殊控制字符：“#”特指“页号”，“&”特指“页数”。
            
    HOrient的值：数字型，0--左边距锁定 1--右边距锁定 2--水平方向居中 3--左边距和右边距同时锁定（中间拉伸），缺省值是0。       
*/
};		

// a4打印
function PrintNoBorderTableA4(){
	LODOP=getLodop();  
	LODOP.PRINT_INIT("UNIC管理系统_LODOP打印控件");
	LODOP.SET_PRINT_PAGESIZE(1,2100,2970,"a4");
	
	LODOP.ADD_PRINT_TABLE("25mm","10mm","90%","200mm",document.getElementById("contents").innerHTML);
	
/* 	LODOP.ADD_PRINT_HTM("250mm","10mm","90%","50mm",document.getElementById("footer").innerHTML);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",1);	
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);	 */
	
	
	//页眉
	LODOP.ADD_PRINT_TEXT(3,250,300,20,"<%=ChainUtil.getOrgPrintName(request)%><%=flowName%>");  
	 LODOP.SET_PRINT_STYLEA(0,"FontSize",20);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);//固定标题,设置卫页眉页脚
	LODOP.SET_PRINT_STYLEA(0,"Horient",2);
	LODOP.SET_PRINT_STYLEA(0,"Bold",1);
	//姓名时间栏目
	LODOP.ADD_PRINT_HTM("10mm","10mm","90%","100%",document.getElementById("hide").innerHTML);  
	LODOP.SET_PRINT_STYLEA(0,"LinkedItem",4);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
	LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",3);	
	
	
	LODOP.ADD_PRINT_HTM(1,"180mm",300,100,"页码：<font color='#0000ff' ><span tdata='pageNO'>#</span>/<span tdata='pageCount'>#</span></font>");
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",1);
	
	// 页脚
	LODOP.ADD_PRINT_HTM("250mm","10mm","100%","100%",document.getElementById("footer").innerHTML);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1); // 设置为页眉页脚
	LODOP.SET_PRINT_STYLEA(0,"Horient",1);	
	
	LODOP.PREVIEW();
};		


function getdata(){
	var detailurl = '<%=contextPath%>/KQDS_MedicalRecordAct/selectDetail.act?seqId='+seqId;
	$.axseY(detailurl,null, 
		function(data){
			var davalul = data.data;
			$('#seqId').html(davalul.seqId); // 病历编号
			usercode = davalul.usercode; // 患者病历号，对应患者档案表，每个病历号唯一
			/* 	
			regno = davalul.regno;
			$("#form2 input[name=regno]").val(davalul.regno);
			$("input[name='mtype'][value="+davalul.mtype+"]").attr("checked",true); 
			$("#form2 input[name=content]").val(davalul.content); */
      		getJbxx(usercode); 
      		getblcontent(davalul.seqId,davalul.mtype);
		},
		function(){
			layer.alert('查询出错！'  );
		}
      );
}
//获取增加行这种字段的内容
function setxx(ywarr,usertype,num){
	var crup1="";
	crup1Arr = ywarr.split(",");
	for(var k=0;k<crup1Arr.length;k++){
		if(usertype==0){
			crup1 += crup1Arr[k]+",";
		}else{
			crup1 += Number(4)+crup1Arr[k]+",";
		}
	}
	if(crup1.indexOf(",")>0){
		crup1 = crup1.substring(0,crup1.length-1);
	}
	return crup1;
}

/* function setxx(ywarr,usertype,num){
	var crup1="";
	crup1Arr = ywarr.split(",");
	for(var k=0;k<crup1Arr.length;k++){
		if(usertype==0){
			crup1 += num+crup1Arr[k]+",";
		}else{
			crup1 += Number(Number(num)+4)+crup1Arr[k]+",";
		}
	}
	if(crup1.indexOf(",")>0){
		crup1 = crup1.substring(0,crup1.length-1);
	}
	return crup1;
} */


function getcontent(value,name,usertype){
	var blcontent="";
	if(value == ""){ // 体格检查
		blcontent += '<tr>';
		blcontent += '<th rowspan="2" style="border: 1px solid #bbb;">' + name + '<br/></th>';
		blcontent += '<td width="30px" rowspan="2" colspan="1" style="border: 1px solid #bbb;">1、</td>';
		blcontent += '<td width="50px" style="height: 28px;border: 1px solid #bbb;"></td>';
		blcontent += '<td width="50px" style="border: 1px solid #bbb;"> </td>';
		blcontent += '<td rowspan="2" colspan="1" style="border: 1px solid #bbb;"></td>';
		blcontent += '</tr>';
	}else{
		// 1;2;4;3||撒东窗事发大概|||1;2;2;4||打折的v风格|||
		var arr = value.split("|||");
		for(var i = 0;i < arr.length;i ++){
			if(arr[i].length > 0){
				var	arrone = arr[i].split("||");
				var ywarr = arrone[0].split(";");
				
				if(ywarr[0] == undefined){
					ywarr[0] = '';
				}
				if(ywarr[1] == undefined){
					ywarr[1] = '';
				}
				if(ywarr[2] == undefined){
					ywarr[2] = '';
				}
				if(ywarr[3] == undefined){
					ywarr[3] = '';
				}
				
				var content = arrone[1];
				if(content == undefined){
					content = '';
				}
				
				
				if(i > 0){
					name='';
				}
				//根据 usertype 0 成人 1 儿童 给牙位加象限值
				var crup1 ="",crup2 = "",crdown1 = "",crdown2 = "";
				if(ywarr[0]!=""){
					crup1 = setxx(ywarr[0],usertype,"1");
				}
				if(ywarr[1]!=""){
					crup2 = setxx(ywarr[1],usertype,"2");
				}
				if(ywarr[2]!=""){
					crdown1 = setxx(ywarr[2],usertype,"4");
				}
				if(ywarr[3]!=""){
					crdown2 = setxx(ywarr[3],usertype,"3");
				}
				
				blcontent += '<tr>';
				blcontent += '<th style="border: 1px solid #bbb;" rowspan="2">' + name + '<br/></th>';
				blcontent += '<td style="border: 1px solid #bbb;" width="30px" rowspan="2" colspan="1">' + (i + 1) + '、</td>';
				blcontent += '<td style="border: 1px solid #bbb;height: 28px;" width="50px">' + crup1 + '</td>';
				blcontent += '<td style="border: 1px solid #bbb;" width="50px">' + crup2 + '</td>';
				blcontent += '<td style="border: 1px solid #bbb;" rowspan="2" colspan="1">' + content + '</td>';
				blcontent += '</tr>';
				
				blcontent += '<tr>';
				blcontent += '<td style="border: 1px solid #bbb;">' + crdown1 + '</td>';
				blcontent += '<td style="border: 1px solid #bbb;height: 28px;">' + crdown2 + '</td>';
				blcontent += '</tr>';			
			}
		}
	}
	return blcontent;
}
//取病历内容
function getblcontent(meid,mtype){
	 var detailurl = '<%=contextPath%>/KQDS_MedicalRecordAct/selectDetailContent.act?meid='+meid+'&mtype='+mtype;
	  $.axse(detailurl,null, 
            function(data){
		  		    var davalul=data.data;
		  		    var usertype=data.usertype;
		  		    var blcontent="";
		  		    if(mtype=="0"){//初诊
		  		    	blcontent +='<table id="cz" width="100%" style="border: 1px solid #bbb;border-collapse: collapse;border-spacing: 0;" >';
		  		    	blcontent +='<tr height="20px">';
		  		    	blcontent +='<th width="70px" style="border: 1px solid #bbb;">主诉</th>';
	  		    		blcontent +='<td colspan="4" style="border: 1px solid #bbb;">' + davalul.czzs + '</td>';
	  		    		blcontent +='</tr>';
	  		    		
		  		    	blcontent +='<tr>';
	  		    		blcontent +='<th style="border: 1px solid #bbb;">现病史</th>';
  		    			blcontent +='<td colspan="4" style="border: 1px solid #bbb;">' + davalul.czxbs + '</td>';
	  		    		blcontent +='</tr>';
	  		    		
		  		    	blcontent +='<tr>';
	  		    		blcontent +='<th style="border: 1px solid #bbb;">既往史</p></th>';
  		    			blcontent +='<td colspan="4" style="border: 1px solid #bbb;">' + davalul.czjws + '</td>';
  		    			blcontent +='</tr>';
  		    			
  		    			blcontent +='<tr>';
	  		    		blcontent +='<th style="border: 1px solid #bbb;">过敏史</th>';
  		    			blcontent +='<td colspan="4" style="border: 1px solid #bbb;">' + davalul.czgms + '</td>';
  		    			blcontent +='</tr>';
  		    			
  		    			blcontent +='<tr>';
	  		    		blcontent +='<th style="border: 1px solid #bbb;">家族史</th>';
  		    			blcontent +='<td colspan="4" style="border: 1px solid #bbb;">' + davalul.czjzs + '</td>';
  		    			blcontent +='</tr>';
  		    			
  		    			blcontent +='<tr>';
						blcontent +='<th style="border: 2px solid #bbb;">检验结果</th>';
						blcontent +='<td colspan="4" style="border: 2px solid #bbb;">'+davalul.czjyjg+'</td>';
						blcontent +='</tr>';
						
						blcontent +=getcontent(davalul.cztgjc,"体格检查",usertype);
						blcontent +=getcontent(davalul.czfzjc,"辅助检查",usertype);
						blcontent +=getcontent(davalul.czzd,"诊断",usertype);
						blcontent +=getcontent(davalul.czzlfa,"治疗方案",usertype);
						blcontent +=getcontent(davalul.czcl,"处理",usertype);
						
						blcontent +='<tr>';
						blcontent +='<th style="border: 2px solid #bbb;">医嘱</th>';
						blcontent +='<td colspan="4" style="border: 2px solid #bbb;">'+davalul.czyz+'</td>';
						blcontent +='</tr>';
						
						blcontent +='<tr>';
						blcontent +='<th style="border: 2px solid #bbb;">备注</th>';
						blcontent +='<td colspan="4" style="border: 2px solid #bbb;">'+davalul.czremark+'</td>';
						blcontent +='</tr>';
						
						blcontent +='</table>';
		  		    }else{//复诊
		  		    	blcontent +='<table id="fz"  width="100%" style="border: 1px solid #bbb;border-collapse: collapse;border-spacing: 0;">';
		  		    	blcontent +='<tr><th width="70px" style="border: 1px solid #bbb;"><p>主诉</p></th><td colspan="4" style="border: 1px solid #bbb;">'+davalul.fzzs+'</td></tr>';
			  		  	blcontent +='<tr>';
						blcontent +='<th style="border: 2px solid #bbb;">检验结果</th>';
						blcontent +='<td colspan="4" style="border: 2px solid #bbb;">'+davalul.fzjyjg+'</td>';
						blcontent +='</tr>';
						
						
		  		    	blcontent +=getcontent(davalul.fzjc,"检查",usertype);
		  		    	blcontent +=getcontent(davalul.fzzd,"诊断",usertype);
		  		    	blcontent +=getcontent(davalul.fzcl,"处理",usertype);
		  		    	blcontent +=getcontent(davalul.fzzlfa,"治疗方案",usertype);
		  		    	
		  		    	
		  		    			  		    	blcontent +='<tr>';
						blcontent +='<th style="border: 2px solid #bbb;">医嘱</th>';
						blcontent +='<td colspan="4" style="border: 2px solid #bbb;">'+davalul.fzyz+'</td>';
						blcontent +='</tr>';
		  		    	
		  		    	blcontent +='<tr><th style="border: 1px solid #bbb;">备注</th><td colspan="4" style="border: 1px solid #bbb;">'+davalul.fzremark+'</td></tr>';
		  		    	

						
						blcontent +='</table>';
		  		    	
		  		    	
		  		    }
		  		    $('#contents').html(blcontent);
            },
			function(){
				layer.alert('查询出错！'  );
			}
      );
}

/**
 * 根据患者病历号查询基本信息
 */
function getJbxx(usercode){
	var baseinfo = getBaseInfoByUserCode(usercode);
	if(baseinfo){
		$('#username').html(baseinfo.username);
		$('#sex').html(baseinfo.sex);
		$('#age').html(baseinfo.age == "0" ? "" : baseinfo.age);
	}
}
</script>
</body>
</html>
