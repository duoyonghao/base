//var menuid = window.parent.menuid;
var usercode;
$(function(){
	/**
	 * 加载临床路径信息
	 */
	var onclickrowobj = window.parent.onclickrowOobj; //父页面传值
	var url = apppath() + '/HUDH_LCLJAct/findLcljOrderByBlcode.act';
//	usercode = onclickrowobj.usercode;
	usercode = 'KQDS000001';
	var param = {blCode: usercode};
	$.axseSubmit(url, param,function() {},function(data) {
		 var lcljOrder = data.lcljOrder
		 $("#bh").text(lcljOrder.order_number);
		 $("#ycAll").text(lcljOrder.total_tooth);
		 $("#comsit").text(lcljOrder.status);
		 $("#uncomNum").text(lcljOrder.remain_tooth);
		 $("#time").text(lcljOrder.createtime);
		 
		 /**
		  * 加载临床跟踪列表
		  */
		 var lcljTrackList = data.lcljTrackList;
		 var length = lcljTrackList.length;
		 if(length >= 1){
			 $(".no-sstrack").addClass("hide");
			 for(var index in lcljTrackList) {
				 $('#context').append(
						 '<div class="ssDiv">'+
							'<div class="ssDiv-track-divTop">'+
								'<span class="ssDiv-track-span hide" name="ssId">' +lcljTrackList[index].id+ '</span>'+
								'<span class="ssDiv-track-span-first" name="ssNum">'+lcljTrackList[index].orderNumber+'-'+lcljTrackList[index].ssTime+'</span>'+
								'<span class="ssDiv-track-span">手术颗数: ' +lcljTrackList[index].tooth+ '&nbsp;颗</span>'+
								'<span class="ssDiv-track-span">创建时间: ' +lcljTrackList[index].createtime+ '</span>'+
								'<span class="ssDiv-track-span">流程环节: ' +lcljTrackList[index].flowLink+ '</span>'+
								'<span class="ssDiv-track-span">' +lcljTrackList[index].type+(lcljTrackList[index].bone=="是"?"植骨":"无植骨")+ '</span>'+
								'<span class="ssDiv-track-span">' +lcljTrackList[index].ssStatus+'</span>'+
								
								'<div class="ssDiv-track-line"></div>'+
							'</div> '+
							'<div class="ssDiv-track-divContext">'+
								'<ul>'+
									'<li>'+
										'<div class="box">'+
											'<div class="box-info">手术</div>'+
											'<div class="box-stus">'+lcljTrackList[index].ssStu+'</div>'+
										'</div>'+
									'</li>'+
									'<li>'+
										'<div class="box">'+
											'<div class="box-info">术后观察</div>'+
											'<div class="box-stus">'+lcljTrackList[index].shgcStu+'</div>'+
										'</div>'+
									'</li>'+
									'<li>'+
										'<div class="box">'+
											'<div class="box-info">戴牙</div>'+
											'<div class="box-stus">'+lcljTrackList[index].dyStu+'</div>'+
										'</div>'+
									'</li>'+
								'</ul>'+
							'</div>'+
						'</div>'
				 );
			 }
		 }
	},function(r) {
		
	});
	
	$(".box-info").bind('click',function (){
		//获取点击的临床路径id
		var orderTrackId = $(this).parents('.ssDiv').find('span[name="ssId"]').text();
		
//		//获取流程步骤
//		var flowLink = $(this).text();
			parent.layer.open({
				type: 2,
				title: '手术操作',
				shadeClose: false,
				shade: 0.6,
				area: ['50%', '60%'],
				content: contextPath+'/ClinicPathControllerAct/toFlowLinkOperate.act?orderTrackId='+orderTrackId,
				cancel: function() {
					var onclickrowobj = window.parent.onclickrowOobj; //父页面传值
					var orderNum = $('span[name="ssNum"]').text();//获取当前操作项目的临床路径编号
					var orderNumber = orderNum.substring(0,10);
//					alert(orderNumber);
//				    alert("你点击了右上角的X");
				    var param = {orderNumber:orderNumber};
				    var url = apppath() + '/HUDH_LCLJAct/updateOrderStatus.act';
				    $.axseSubmit(url, param, function() {}, function(r) {
						
					}, function() {
						
					});

				}
			});
		
	});
	
})

/**
 * 按钮方法
 */
var buttonFun = {
	/**
	 * 手术
	 */
	ss : function (){
		var uncomNum = $('#uncomNum').text();
		var orderNumber = $('#bh').text();
		if(uncomNum <= 0) {
			layer.alert("未做手术牙齿数为0");
			return;
		}else {
			layer.open({
		        type: 2,
		        title: '创建本次手术临床跟踪',
		        shadeClose: true,
		        shade: 0.6,
		        area: ['300px', '300px'],
		        content: contextPath+'/ClinicPathControllerAct/toOperation.act?orderNumber='+orderNumber+'&uncomNum='+uncomNum
		    });
		}
	},
	/**
	 * 生成路径
	 */
	sclj : function (){
		if(!usercode) {
			layer.alert('获取病历号失败，请刷新当前页面');
			return;
		}else {
			//验证当前患者是否存在未完成的临床路径
			var param = {blCode:usercode,status:'未完成'};
			$.axseSubmit(contextPath+'/HUDH_LCLJAct/findLcljOrderByBlcodeAndStu.act', param,function() {},function(data) {
				var length = (data.lcljOrderList).length;
				if(length >= 1 ){
					layer.alert('该患者存在未完成的临床路径');
				}else {
					layer.prompt({
						formType: 0,
					  	value: '',
					  	title: '<font style="font-size:16px;">输入手术牙齿总颗数</font>'
					}, function(value,index){
						if(isNaN(value)){
							layer.close(index);
							layer.alert('请输入数字');
							return;
						}else {
							param = {totalTooth:value,blcode:usercode};
							$.axseSubmit(contextPath+'/HUDH_LCLJAct/saveLcljOrder.act', param,function() {},function(data) {
								layer.alert(data.retMsrg);
							},function (r){layer.alert(r.retState);});
							window.location.reload(); //刷新父页面
						  	layer.close(index);
						}
					});
				}
			},function (r){layer.alert(r.retState);});
		}
	}
}

function apppath(){//获得根目录
    var strFullPath = window.document.location.href;
    var strPath = window.document.location.pathname;
    var pos = strFullPath.indexOf(strPath);
    var prePath = strFullPath.substring(0, pos);
    var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
    return (prePath + postPath);	
}
