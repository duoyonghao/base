<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>

<%
	// 只有咨询和医生，才有费用添加权限
	String static_askpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_ASK_SEQID);
	String static_docpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_DOCTOR_SEQID);
	String IS_OPEN_ZHONGZHI_BINGLI = SysParaUtil.getSysValueByName(request, SysParaUtil.IS_OPEN_ZHONGZHI_BINGLI);
	
	/**
	 * 是否开启电话录音  0 关闭 1开启
	 */
	String is_open_record_func = YZSysProps.getProp(SysParaUtil.IS_OPEN_RECORD_FUNC);
	if(is_open_record_func == null){
		is_open_record_func = "";
	}
%>

<%
	// 这里的代码主要是为了让 <%@include 的页面不报错，没有其他意义
	String baseheader = request.getContextPath();
	if (baseheader.equals("")) {
		baseheader = "/kqds";
	}
	// 这里的代码主要是为了让 <%@include 的页面不报错，没有其他意义   END   ###
	String is_Wd_oper = null;
	String reqUrl = request.getRequestURI();
	if(reqUrl.contains("KQDS_UserDocumentAct/toUserManagerJq.act")){ // user_manager_jq.jsp
		is_Wd_oper = "wd";
	}else{
		is_Wd_oper = "";
	}
%>

<div class="columnWrap">
	<ul class="subSideBar nohc-scroll-webkit" style="cursor: pointer">
		<li id="blueArrow">
			<span class="icon20 lvUpIcon ArrowIcon" style="margin-top:10px;"></span>
		</li>
		<li class="item" target="tabIframe" id="yxzl"
			src="<%=baseheader%>/KQDS_UserDocumentAct/toVideo_Yxzl.act?type=index">
			<img class="middleImageIcon" src="<%=baseheader%>/static/image/kqdsFront/img/icon/videoIcon.png" />
			 影像资料
		</li>
		<li class="item" target="tabIframe"
			src="<%=baseheader%>/KQDS_UserDocumentAct/toHuizheng_Info.act">
			<img class="middleImageIcon" src="<%=baseheader%>/static/image/kqdsFront/img/icon/consultationIcon.png" />
			会诊信息</li>
		<li class="item" target="tabIframe" src="<%=baseheader%>/KQDS_UserDocumentAct/toMedicalrecord.act" id="bingli">
			<img class="middleImageIcon" src="<%=baseheader%>/static/image/kqdsFront/img/icon/medicalHistoryIcon.png" />
			病历
		</li> 
		
		<%
			if("1".equals(IS_OPEN_ZHONGZHI_BINGLI)){
				
			%>
			<li target="tabIframe" src="javascript:void(0);" onclick="openBingLi();" id="zhongzhi_bingli">
				<img class="middleImageIcon" src="<%=baseheader%>/static/image/kqdsFront/img/icon/medicalHistoryIcon.png" />
				种植病历
			</li>
			<%					
			}
		%>
		<!-- 
		<li target="tabIframe" src="javascript:void(0);" onclick="openBingLi();" id="zhongzhi_bingli">
				<span class="hc-icon icon26 medical-zhongzhi-icon"></span>种植病历
		</li> 
		-->
		
		<li class="item" onclick="openAddCost();">
		<img class="middleImageIcon" src="<%=baseheader%>/static/image/kqdsFront/img/icon/chargeIcon.png" />
		<!-- <span class="lvUpIcon icon20 charge-icon"></span> -->
		费用添加</li>
		<li class="item" onclick="openAddCostTest();" id="openAddCostTest">
		<img class="middleImageIcon" src="<%=baseheader%>/static/image/kqdsFront/img/icon/costDetailIcon.png" />
		模拟划价</li>
		<li class="item" target="tabIframe"
			src="<%=baseheader%>/KQDS_UserDocumentAct/toJg_List.act">
			<img class="middleImageIcon" src="<%=baseheader%>/static/image/kqdsFront/img/icon/workIcon.png" />
			加工记录</li>
		<%
			if("1".equals(is_open_record_func)){
			%>
			<li class="item" target="tabIframe" src="javascript:void(0);" onclick="openSoundRecord();" id="sound_record">
				
				<img class="middleImageIcon" src="<%=baseheader%>/static/image/kqdsFront/img/icon/customerServiceIcon.png" />
				电话录音
			</li>
			<%					
			}
		%>
		<li class="item" target="tabIframe"
			src="<%=baseheader%>/KQDS_UserDocumentAct/toSms_Usercode.act">
			<img class="middleImageIcon" src="<%=baseheader%>/static/image/kqdsFront/img/icon/middleweixin.png" />
			短信记录</li>	
		<li class="item" target="tabIframe"
			src="<%=baseheader%>/KQDS_UserDocumentAct/toCs.act">
			<img class="middleImageIcon" src="<%=baseheader%>/static/image/kqdsFront/img/icon/transforIcon.png" />
			流转记录
		</li>
		<!-- #####添加临床路径模块##### -->	
		<li class="item" target="tabIframe"
			src="<%=baseheader%>/HUDH_DzblViewAct/toDzblOptation.act">
			<img class="middleImageIcon" src="<%=baseheader%>/static/image/kqdsFront/img/icon/administrationIcon.png" />
			电子病历
		</li>	
	</ul>
	<div class="columnBd" id="rightmenu">
		<ul class="menu">
			<li class="current" target="tabIframe"
				src="<%=baseheader%>/KQDS_UserDocumentAct//toRightGrxx.act">
				<img class="middleImageIcon" src="<%=baseheader%>/static/image/kqdsFront/img/icon/individualIcon-.png" />
				个人信息</li>
			<%	
			
				// 客服中心—客户管理页面右侧的网电信息Url和其他页面不一样，
				// 一个是kqdsFront/jdzx/dialogFrame/netorder.jsp
				// 一个是kqdsFront/jdzx/frame/kfzx/netorder_insertOrUpdate.jsp
				// 为了能统一包含一个公用JSP文件，所以在这里设置个标识，供rightPartInfo.jsp界面判断
			
				String staticIsUserMgrPage = null;
				if(null != request.getAttribute("staticIsUserMgrPage")){
					staticIsUserMgrPage = (String)request.getAttribute("staticIsUserMgrPage");
				}
				//staticIsUserMgrPage: value为1 网电预约  2 报备预约
				if(staticIsUserMgrPage != null  && !"".equals(staticIsUserMgrPage)){
					%>
					<li target="tabIframe" src="<%=baseheader%>/KQDS_Net_OrderAct/toNetorderInsertOrupdate.act?source=<%=staticIsUserMgrPage%>">
					<img class="middleImageIcon" src="<%=baseheader%>/static/image/kqdsFront/img/icon/netMessageIcon.png" />
					网电信息
					</li>
					<%
				}else{
					%>
					<li target="tabIframe" src="<%=baseheader%>/KQDS_Net_OrderAct/toNetorder2.act">
						<img class="middleImageIcon" src="<%=baseheader%>/static/image/kqdsFront/img/icon/netMessageIcon.png" />
						网电信息
					</li>
					<%
				}
			%>
			<li target="tabIframe"
				src="<%=baseheader%>/KQDS_UserDocumentAct/toYyzxMz.act">
				<img class="middleImageIcon" src="<%=baseheader%>/static/image/kqdsFront/img/icon/receptionIcon.png" />
				门诊预约
			</li>
			<li target="tabIframe"
				src="<%=baseheader%>/KQDS_UserDocumentAct/toReceive.act">
				<img class="middleImageIcon" src="<%=baseheader%>/static/image/kqdsFront/img/icon/consultRecordIcon.png" />
				咨询记录
			</li>
			<li id="fyxq" target="tabIframe"
				src="<%=baseheader%>/KQDS_CostOrder_DetailAct/toCostDetail2.act">
				<img class="middleImageIcon" src="<%=baseheader%>/static/image/kqdsFront/img/icon/costDetailIcon.png" />
				费用详情
			</li>
			<%
			//回访查看
			//String right_ychf = SysParaUtil.getSysValueByName(request, RIGHT_YCHF);
			//YZPerson person = SessionUtil.getLoginPerson(request);
			
			if(SysParaUtil.getSysValueByName(request, SysParaUtil.RIGHT_YCHF).indexOf(SessionUtil.getLoginPerson(request).getUserPriv()) != -1) {
			%>
			
			<%}else{ %>
				<li target="tabIframe"
				src="<%=baseheader%>/KQDS_VisitAct/toVisitList2.act?is_Wd_oper=<%=is_Wd_oper%>">
				<img class="middleImageIcon" src="<%=baseheader%>/static/image/kqdsFront/img/icon/returnVisitManageIcon.png" />
				回访管理
			</li>
			<%} %>
			<li target="tabIframe"
				src="<%=baseheader%>/KQDS_UserDocumentAct/toZengsong_List.act">
				<img class="middleImageIcon" src="<%=baseheader%>/static/image/kqdsFront/img/icon/giftProjectIcon.png" />
				赠送项目
			</li>
		</ul>
		<div class="tabContainer">
			<iframe id="tabIframe" name="tabIframe"
				src="<%=baseheader%>/KQDS_UserDocumentAct/toRightGrxx.act"
				width="100%" height="100%" frameborder="0" class="tabIframe">
			</iframe>
		</div>
	</div>
</div>

<script type="text/javascript">

//加载事件
window.onload = function() {
	/* 抽取包含rihtPartInfo.jsp页面的公共js */
	//1、接待中心的栏目切换状态
    $('.centerWrap').on('click','.columnWrap .columnBd li',function () {
        $(this).addClass('current').siblings('li').removeClass('current');
        //切换对应栏目下的表格
    });
    //2、信息栏的menu菜单的切换
    $('.lookInfoWrap .menu').on('click','li',function () {
    	
    	if(onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined"){
    		layer.alert('请选择患者' );
    		return;
    	}
    	
    	//设置当前点击li的icon为蓝色
    	if(!$(this).hasClass("current")){
    		var img=$(this).find("img");
        	var str=img.attr("src");
                str=imgaddStr(str);
            	img.attr("src",str);
    	}
    	//去除掉当前蓝色的icon
    	$(this).siblings("li").each(function(i,elem){
    		if($(elem).hasClass("current")){
    			var img2=$(elem).find("img");
           		var str2=imgremoveStr(img2.attr("src"));
           		img2.attr("src",str2);
    		} 
    	});
    	$(".lookInfoWrap .subSideBar li").each(function(i,elem){
    		if($(elem).hasClass("hc-current")){
    			var img2=$(elem).find("img");
           		var str2=imgremoveStr(img2.attr("src"));
           		img2.attr("src",str2);
           		
    		} 
    	});
    	var src = $(this).attr('src');
       	$(this).addClass("current").siblings().removeClass("current");
        //$(this).closest('.columnHd').next('.columnBd').find('.subSideBar li').removeClass('hc-current');
        $('.tabIframe').attr('src',src);
        $(".lookInfoWrap .subSideBar li.hc-current").removeClass("hc-current");//清除左侧项中的所有橙色选中样式
    });
    $('.lookInfoWrap .subSideBar').on('click','li.item',function () {
    	if(onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined"){
    		if('openAddCostTest' != $(this).attr('id')){ // 模拟划价的，不做判断
    			layer.alert('请选择患者' );
        		return;
    		}
    	}
    /* 	if($(this).attr('id')=='bingli'){
    		if(onclickrowOobj.ifcost == null && (onclickrowOobj.regno == null || onclickrowOobj.regno == "")){
        		layer.alert('请选择已挂号的患者' );
        		return;
        	}
    	} */
    	//设置当前点击li的icon为蓝色
    	if(!$(this).hasClass("hc-current")){
    		var img=$(this).find("img");
        	var str=img.attr("src");
                str=imgaddStr(str);
            	img.attr("src",str);
    	}
    	//去除掉当前蓝色的icon
    	$(this).siblings("li").each(function(i,elem){
    		if($(elem).hasClass("hc-current")){
    			var img2=$(elem).find("img");
           		var str2=imgremoveStr(img2.attr("src"));
           		img2.attr("src",str2);
    		} 
    	});
    	$(".lookInfoWrap .menu li").each(function(i,elem){
    		if($(elem).hasClass("current")){
    			var img2=$(elem).find("img");
           		var str2=imgremoveStr(img2.attr("src"));
           		img2.attr("src",str2);
    		} 
    	});
        var src = $(this).attr('src');
        $(this).addClass('hc-current').siblings('li').removeClass('hc-current');
       // $(this).closest('.columnBd').prev('.columnHd').find('.menu li').removeClass('current');
        $('.tabIframe').attr('src',src);
        $(".lookInfoWrap .menu li").removeClass("current");//清除信息选择中的橙色选中样式
    });
};
function imgaddStr(str){//设置蓝色icon的文件名
	if(!str){ // 如果没有配置图标，则不处理
		return "";
	}
	var arr=str.split(".");
	str=arr[0]+"-"+".png";
	
	return str
}
function imgremoveStr(str){//设置灰色icon的文件名
	var arr=str.split("-");
	str=arr.join("");
	
	return str
}
// 费用添加
function openAddCost(){
	// canKd 这个变量只有在  能开单的页面才会定义
	//判断是否有权限开单 没有权限提示并终止
	if(typeof(canKd) == "undefined"){
		layer.alert('请在接待中心或者医疗中心进行费用添加！' );
		return false;
	}
	if(canKd == 0){
		if(!(isStrInArrayString(personrole,"<%=static_askpriv%>") || isStrInArrayString(personrole,"<%=static_docpriv%>") || isStrInArrayStringEach("<%=static_askpriv%>",personroleother) || isStrInArrayStringEach("<%=static_docpriv%>",personroleother))){
			layer.alert('只有咨询或者医生才有权限添加费用！' );
			return false;
		} 
		if(onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined" || onclickrowOobj.ifcost == null){
			layer.alert('请选择挂号记录！' );
			return false;
		}else{
			if(onclickrowOobj.ifcost != null && onclickrowOobj.del==1){
				layer.alert('撤销的挂号无法添加费用！' );
				return false;
			}
		}
		if(!selectNopay(onclickrowOobj.usercode)){
			layer.confirm('该患者存在费用单未结账，是否继续开单？', {
			    btn: ['是','否'] //按钮
			}, function(){
				layer.closeAll('dialog');
                var valid=true;
                if((isStrInArrayString(personrole,"<%=static_askpriv%>")|| !isStrInArrayStringEach("<%=static_askpriv%>",personroleother))){
                    //查询患者的指定客服是否是当前登录人
                    $.ajax({
                        url:'<%=baseheader%>/KQDS_UserDocumentAct/comparisonKefuByUsercode.act',
                        type:"POST",
                        dataType:"json",
                        data : {"usercode":onclickrowOobj.usercode},
                        async: false,
                        success:function(result){
                            if(!result.valid){
                                valid=false;
                                layer.alert('该患者已指定客服，无权限开单！' );
                                return false;
                            }
                        }
                    });
                }
                if(valid) {
                    layer.open({
                        type: 2,
                        title: '费用添加',
                        // shadeClose: true,
                        shade: 0.6,
                        shadeClose: false,
                        area: ['95%', '98%'],
                        content: '<%=baseheader%>/KQDS_CostOrderAct/toDetail_AddCost.act?usercode=' + onclickrowOobj.usercode + '&regno=' + onclickrowOobj.seqId
                    });
                }
			},function(){
				layer.closeAll('dialog');
			});
		}else{
		    var valid=true;
            if((isStrInArrayString(personrole,"<%=static_askpriv%>")|| !isStrInArrayStringEach("<%=static_askpriv%>",personroleother))){
                //查询患者的指定客服是否是当前登录人
                $.ajax({
                url:'<%=baseheader%>/KQDS_UserDocumentAct/comparisonKefuByUsercode.act',
                type:"POST",
                dataType:"json",
                data : {"usercode":onclickrowOobj.usercode},
                async: false,
                success:function(result){
					if(!result.valid){
                        valid=false;
                        layer.alert('该患者已指定客服，无权限开单！' );
                        return false;
					}
                }
                });
            }
            if(valid){
                layer.open({
                    type: 2,
                    title: '费用添加',
                    // shadeClose: true,
                    shade: 0.6,
                    shadeClose:false,
                    area: ['95%', '98%'],
                    content: '<%=baseheader%>/KQDS_CostOrderAct/toDetail_AddCost.act?usercode='+onclickrowOobj.usercode+'&regno='+onclickrowOobj.seqId
                });
			}
		}
	}else{
		layer.alert('无权限，请联系管理员开通权限！' );
	}
}    

//模拟划价
function openAddCostTest(){
	if(!(isStrInArrayString(personrole,"<%=static_askpriv%>") || isStrInArrayString(personrole,"<%=static_docpriv%>") || isStrInArrayStringEach("<%=static_askpriv%>",personroleother) || isStrInArrayStringEach("<%=static_docpriv%>",personroleother))){
		layer.alert('只有咨询或者医生才有权限添加费用！' );
		return false;
	} 
	layer.open({
		type: 2,
		title: '模拟划价',
		// shadeClose: true,
		shade: 0.6,
		shadeClose:false,
		area: ['95%', '98%'],
		content: '<%=baseheader%>/KQDS_CostOrderAct/toDetail_AddCostTest.act?usercode='+onclickrowOobj.usercode+'&regno='+onclickrowOobj.seqId
    });
}


//删除费用单和项目清单
function delCostItem(id){
	var url = '<%=baseheader%>/KQDS_CostOrderAct/deleteObj.act?seqId='+ id;
	$.axse(url, null, function(data) {
		if (data.retState == "0") {
		}
	}, function() {
		
	});
}
//删除费用单和项目清单
function selectNopay(usercode){
	var flag=true;
	var url = '<%=baseheader%>/KQDS_CostOrderAct/selectNopay.act?usercode='+ usercode;
	$.axse(url, null, function(data) {
		if (data.retState == "0") {
			if(data.data.length>0){
				flag = false;
			}
		}
	}, function() {
		
	});
	return flag;
}

function fyxqClick(){
	$("#fyxq").click();
}


function yxzlClick(){
	$("#yxzl").click();
}

// 此方法专门供cameraOnline_blk.jsp页面调用
function bingliClick(){
	$("#tabIframe").attr("src","<%=baseheader%>/KQDS_UserDocumentAct/toMedicalrecord.act?blflag=1");
}

function openBingLi(){
	
    //如果选中的不是挂号记录
    if (onclickrowOobj == "" || onclickrowOobj.ifcost == null) {
        regno = onclickrowOobj.regno;
    } else {
        regno = onclickrowOobj.seqId;
        isdelreg = onclickrowOobj.del;
    }
    
    if (onclickrowOobj.usercode == null || onclickrowOobj.usercode == "" || onclickrowOobj.usercode == "null" || onclickrowOobj.usercode == "undefined") {
        layer.alert('请选择患者' );
        return;
    }

    var regno4request = "";
    if (regno == null || regno == undefined || regno == "" || regno == "null" || regno == "undefined") {
    	// 点击的不是挂号记录，什么都不做
    }else{
    	regno4request = regno;
    }
    
    $('#zhongzhi_bingli').attr('src','<%=baseheader%>/KQDS_UserDocumentAct/toGrxx.act');
    
    var openUrl = '<%=baseheader%>/KQDS_MedicalRecordAct/toBingliIndex.act?usercode='+onclickrowOobj.usercode+'&regno='+regno4request;
	layer.open({
		type: 2,
		title: '种植病历管理【患者编号：'+onclickrowOobj.usercode+'】',
		// shadeClose: true,
		shade: 0.6,
		shadeClose:false,
		area: ['95%', '98%'],
		content: openUrl
       });
}

// 电话录音
function openSoundRecord(){
	$("#sound_record").attr("src","<%=baseheader%>/KQDS_UserDocumentAct/toGrxxList4dj.act");
}



</script>
