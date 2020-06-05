var apppath = apppath();
$(function(){
	var url = apppath + '/HUDH_LCLJAct/findLcljOrderTrsackById.act';
	var param = {orderTrackId: orderTrackId};
	$.axseSubmit(url, param,function() {},function(data) {
		if(data) {
			$('#flowlink-top').append(
					 '<div class="ssDiv">'+
						'<div class="ssDiv-track-divTop">'+
							'<span class="ssDiv-track-span hide" name="ssId">' +data.id+ '</span>'+
							'<span class="ssDiv-track-span-first" name="ssNum">'+data.order_number+'-'+data.ss_time+'</span>'+
							'<span class="ssDiv-track-span">手术颗数:'+data.tooth+'&nbsp;颗</span>'+
							'<span class="ssDiv-track-span">创建时间:'+data.createtime+' </span>'+
							'<span class="ssDiv-track-span">流程环节:'+data.flow_link+'</span>'+
							'<span class="ssDiv-track-span">' +data.type+(data.bone=="是"?"植骨":"无植骨")+ '</span>'+
							'<span class="ssDiv-track-span">'+data.ss_status+'</span>'+
							
							'<div class="ssDiv-track-line"></div>'+
						'</div> '+
						'<div class="ssDiv-track-divContext">'+
							'<ul>'+
								'<li>'+
									'<div class="box">'+
										'<div class="box-info" title="手术">手术</div>'+
										'<div class="box-stus">'+data.ss_stu+'</div>'+
									'</div>'+
								'</li>'+
								'<li>'+
									'<div class="box">'+
										'<div class="box-info" title="术后观察">术后观察</div>'+
										'<div class="box-stus">'+data.shgc_stu+'</div>'+
									'</div>'+
								'</li>'+
								'<li>'+
									'<div class="box">'+
										'<div class="box-info" title="戴牙">戴牙</div>'+
										'<div class="box-stus">'+data.dy_stu+'</div>'+
									'</div>'+
								'</li>'+
							'</ul>'+
						'</div>'+
					'</div>'
			 );
			
			/**
			 * 初始化操作列表
			 */
			var operateArray = null;
			var $operateItem;
			if(data.ss_stu == '未完成'){
				operateArray = data.ss;
				$operateItem = $('#ss');
				$('#current_flow_link').text('手术');
			}else if(data.shgc_stu == '未完成'){
				operateArray = data.shgc;
				$operateItem = $('#shgc');
				$('#current_flow_link').text('术后观察');
			}else{
				operateArray = data.dy;
				$operateItem = $('#dy');
				$('#current_flow_link').text('戴牙');
			}
			var length = operateArray.length;
			if(length >= 1) {
				$operateItem.removeClass('hide');
				for(var index in operateArray) {
					$operateItem.append(
							'<div class="col-xs-12 col-sm-4 control-label clo-itsm hr2 flowlink-operate text-center">'+
			     			'<div class="track-operate-box">'+
			     				'<span name="operate-name" title="'+operateArray[index].name+'">'+operateArray[index].name+'</span>&nbsp;&nbsp;'+
			     				'<span name="operate-info">'+operateArray[index].isComplate+'</span><br>'+
			     				'<div align="center" class="operatebuttons">'+
			    					'<a href="javascript:void(0);" class="operate-button"  onclick="buttonFun.wc(this);" name="wanchen" title="完成">完成</a>'+
			    					'<a href="javascript:void(0);" class="operate-button"  onclick="buttonFun.add(this);" name="tianjia" title="添加">添加</a>'+
			    					'<a href="javascript:void(0);" class="operate-button"  onclick="buttonFun.ck(this);" name="chakan" title="查看" id="ck">查看</a>'+
			    				'</div>'+
			     			'</div>'+
			     		'</div>');
				}
			}
		};
		
	},function(r) {
		
	});
	
	/**
	 * 改变流程环节的状态
	 */
	/*$('.box-stus').on('click', function(){
		var orderTrackId = $('span[name="ssId"]').text();//获取当前操作项目的临床路径编号
//		alert(orderTrackId);
		var operateName = $(thi).parents('.track-operate-box').find('span[name="operate-name"]').text();//获取当前操作项目的名称
		alert(operateName);
		var flowLink = $('#current_flow_link').text();
		alert(flowLink);
		var param = {operateName:operateName,orderTrackId:orderTrackId,flowLink:flowLink};
		var url = apppath + 'HUDH_LCLJAct/updateOperationFlowStatus.act';
		$.axseSubmit(url, param, function() {
			location.reload();
		}, function(r) {
			window.location.reload(); //刷新页面
		},function() {
			location.reload();
		});
	});*/
	
	
	/**
	 * 流程环节切换
	 */
	$('.box-info').on('click',function (){
		var current_flow_link = $(this).text(); //获取当前点击环节
		var ssId = $('span[name="ssId"]').text(); //获取当前手术跟踪id
		$('#ss').addClass('hide');
		$('#shgc').addClass('hide');
		$('#dy').addClass('hide');
		var $operateItem;
		if(current_flow_link == '手术') {
			$operateItem = $('#ss');
			$('#current_flow_link').text('手术');
		}else if(current_flow_link == '术后观察'){
			$operateItem = $('#shgc');
			$('#current_flow_link').text('术后观察');
		}else if(current_flow_link == '戴牙'){
			$operateItem = $('#dy');
			$('#current_flow_link').text('戴牙');
		}
		$operateItem.removeClass('hide');
		var param = {flowLink:current_flow_link,orderTrackId:ssId};
		$.axseSubmit(apppath + '/HUDH_LCLJAct/findOperateByTrackIdAndLink.act', param,function() {},function(data) {
			var operates = data.operates;
			var length = operates.length;
			if(length >= 1) {
				$operateItem.empty();//清空原有内容
				for(var index in operates) {
					$operateItem.append(
							'<div class="col-xs-12 col-sm-4 control-label clo-itsm hr2 flowlink-operate text-center">'+
			     			'<div class="track-operate-box">'+
			     				'<span name="operate-name" title="'+operates[index].name+'">'+operates[index].name+'</span>&nbsp;&nbsp;'+
			     				'<span name="operate-info">'+operates[index].isComplate+'</span><br>'+
			     				'<div align="center" class="operatebuttons">'+
			    					'<a href="javascript:void(0);" class="operate-button"  onclick="buttonFun.wc(this);" name="wanchen" title="完成">完成</a>'+
			    					'<a href="javascript:void(0);" class="operate-button"  onclick="buttonFun.add(this);" name="tianjia" title="添加" id="add">添加</a>'+
			    					'<a href="javascript:void(0);" class="operate-button"  onclick="buttonFun.ck(this);" name="chakan" title="查看">查看</a>'+
			    				'</div>'+
			     			'</div>'+
			     		'</div>');
				}
			}
		},function(r) {
		});
	});
	
})

/**
 * 按钮方法
 */
var buttonFun = {
	add : function (thi){
		var orderTrackId = $('span[name="ssId"]').text();//获取点击的临床路径id
		var flowLink = $('#current_flow_link').text();//获取当前所处临床环节
		var operateName = $(thi).parents('.track-operate-box').find('span[name="operate-name"]').text();//获取当前操作项目的名称
//		alert(operateName);
		layer.open({
			type: 2,
			title: '添加备注',
			shadeClose: true,
			shade: 0.6,
			area: ['50%', '60%'],
			content: contextPath+'/ClinicPathControllerAct/toAddRemakeInfor.act?orderTrackId='+orderTrackId+'&flowLink='+flowLink+'&operateName='+operateName 
		});
	},
	wc : function (thi){
		var orderTrackId = $('span[name="ssId"]').text();//获取点击的临床路径id
		var flowLink = $('#current_flow_link').text();//获取当前所处临床环节
		var operateName = $(thi).parents('.track-operate-box').find('span[name="operate-name"]').text();//获取当前操作项目的名称
//		var orderNumber = $('span[name="ssNum"]').text();
		var param = {orderTrackId:orderTrackId,flowLink:flowLink,operateName:operateName};
		var url = apppath + '/HUDH_LCLJAct/updateOperationFlowStatus.act';
		$.axseSubmit(url, param, function() {
		}, function(r) {
			location.reload(); //刷新页面
		},function(r) {
			layer.alert('请联系管理员！'); 
		});
	},
	ck : function (thi){
		var orderTrackId = $('span[name="ssId"]').text();//获取点击的临床路径id
		var flowLink = $('#current_flow_link').text();//获取当前所处临床环节
		var oprationName = $(thi).parents('.track-operate-box').find('span[name="operate-name"]').text();//获取当前操作项目的名称
		//var param = {orderTrackId:orderTrackId,flowLink:flowLink,oprationName:oprationName};
		layer.open({
			type: 2,
			title: '查看备注',
			shadeClose: true,
			shade: 0.6,
			area: ['100%', '100%'],
			content: contextPath +'/ClinicPathControllerAct/toCkaRemakeInfor.act?orderTrackId='+orderTrackId+'&flowLink='+flowLink+'&operateName='+oprationName
		});
		/**layer.open({
			type: 2,
			title: '备注信息查看',
			shadeClose: true,
			shade: 0.6,
			area: ['80%', '60%'],
			content: contextPath + 
				'/ClinicPathControllerAct/openRemakeInfo.act?flowLink=' + encodeURI(encodeURI(flowLink)) + '&orderTrackId=' + orderTrackId + '&oprationName=' + encodeURI(encodeURI(oprationName))
		});**/
//		layer.open({
//			type: 2,
//			title: '查看备注',
//			shadeClose: true,
//			shade: 0.6,
//			area: ['100%', '100%'],
//			content: contextPath+'/ClinicPathControllerAct/toCkaRemakeInfor.act',
//			success: function(data){
//				alert(data);
//			}
//		});

	}
}





