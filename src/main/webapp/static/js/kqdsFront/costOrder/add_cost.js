function setHeight() {
    // 这个页面，屏幕小会有横向滚动条，这个滚动条的高度是15
    // 这里可以可以根据屏幕分辨率，计算是否会出现滚动条
	$("#container").outerHeight($(window).outerHeight());
    var baseHeight = $(window).height() 
    -$(".headerUl").outerHeight()
    -$("#jbxx").outerHeight()
    -$("#fytjDiv .costHd").outerHeight()
    -$(".footBar").outerHeight()
    -$(".formBox_center").outerHeight()-30; // 这几个值是通过chrome查看每个元素的高度得到的
    /*if ((baseHeight - 65) <= 200) {
        baseHeight = 265;
    }*/
    /*$('.costWrap').height(baseHeight);*/
    $('.costWrap .costBd ').height(baseHeight);
    $('.costWrap .costBd .tableBody,#tableContent').height(
    		$('.costWrap .costBd ').outerHeight() 
    		- $('.formBox.money').outerHeight() 
    		- $('.formBox.person').outerHeight());
    /*$('.costWrap .costBd .ztreeWrap').height($('.costWrap .costBd ').height() - 5);*/
    $('.costWrap .costBd .ztreeWrap').height(baseHeight);
    /*$('.costWrap .costBd .ztreeWrap  .ztree').height($('.costWrap .costBd .ztreeWrap').height() - 35);*/
    $('.costWrap .costBd .ztreeWrap  .ztree').height($('.costWrap .costBd .ztreeWrap').height() - $('.ztreeWrap .searchWrap').height()-10);//减去.ztree的10个像素的 上下内边距
    $(".fixed-table-container").height(baseHeight - 65);
    
}
function setFixedHeaderWidth(){//设置固定得表头每项得宽度与数据table表头的宽度一致
	$("#fixedHeadertable").outerWidth($("#table").outerWidth());
	$("#table thead th").each(function(i,elem){
		$($("#fixedHeadertable thead th").get(i)).outerWidth($(elem).outerWidth());
	});
	
}
/**
 * 获取已添加到列表的收费项目的类型，如挂号费、预交金。。 1 是预交金  2 是挂号
 */
function isExistTreatItemSort(sortValue){
	var flag = false;
	$('#table').find('tbody').each(function() {
	    $(this).find('tr').each(function() {
	        $(this).find('td').each(function() {
	            if ($(this).index() == 18) {
	                //消费项目类型，如预交金
	                var isyjjitem = $(this).text();
	                if(sortValue == isyjjitem){
	                	flag = true;
	                	return false; // 相当于break;
	                }
	            }
	        });
	    });
	});
	return flag;
}

/**
 * 是否存在过期项目
 * @returns {Boolean}
 */
function isExistExpireItem(){
	var flag = false;
	$('#table').find('tbody').each(function() {
	    $(this).find('tr').each(function() {
	    	var expireflag = $(this).attr("expireflag");
	        if('1' == expireflag){
	        	flag = true;
	        	return false; // 相当于break;
	        }
	    });
	});
	return flag;
}

/**
 * 处理这种情：
 * 存在一个欠费单，欠款50
 * 这时，新建一个还款单，换30，创建后再修改，此时，不应该把之前的欠款50的单子带出来;过期项目除外！
 */
function ifAddQFItemInList(qfbh){  // true 显示  false 不显示
	var flag = true;
	$('#table').find('tbody').each(function() {
	    $(this).find('tr').each(function() {
	    	var expireflag = $(this).attr("expireflag");
	        $(this).find('td').each(function() {
	            if ($(this).index() == 17) {
	                var qfbh2 = $(this).text();
	                if(qfbh == qfbh2){
	                	if('1' != expireflag){ /** 仅当修改还款单，且还款单未过期，不显示 **/
	                		flag = false;
	                		return false; // 相当于break;
	        	        }
	                }
	            }
	        });
	    });
	});
	return flag;
}

/**
 * 判断是否存在过期项目
 * @returns {Boolean}
 */
function judgeIfExistExpireItem()
{
	/** 判断是否存在过期项目，并给予提示 **/
    var expireflag = isExistExpireItem(); // 是否存在过期项目
    if(expireflag){
    	layer.alert('该费用单存在过期消费项目，请先删除黄色标注的过期消费项目，再继续进行操作！'  );
    	return false;
    }	
}


// 预交金缴费金额合法性判断，当添加的项目为预交金时，数量为1、折扣为100，免除金额为0，且不能修改
function yjjcz(index, field){
	var paymoney;
	if (judgeSign($("#paymoney" + index).val()) == true) {
        paymoney = Number($("#paymoney" + index).val());
    } else {
        layer.alert('输入项必须为正数' );
        oldValue = $("#paymoney" + index).attr("oldvalue");
        $("#paymoney" + index).val(oldValue);
        return false;
    }
	$("#unitprice" + index).val(paymoney.toFixed(2));
	$("#subtotal" + index).html(paymoney.toFixed(2));
	 //刷新金额
    getAlljs();
}
/** 1、欠费记录，只能输入缴费金额，其他不能修改 **/
/** 2、开单时，只有折扣、数量、免除、缴费金额可输入 **/
// 该方法用于验证文本框输入值是否合法，包括：折扣值
function edithz(index, field, isqfreal) {
    //判断该用户最大折扣权限
    if (field == "discount") {
        var discount = Number($("#discount" + index).val());
        if(discount.toString().indexOf('.')>0){
        	if(discount.toString().split(".")[1].length>4){
        		 layer.alert('折扣最多填写4位小数！', {
                       
                 });
                 oldValue = $("#discount" + index).attr("oldvalue");
                 $("#discount" + index).val(oldValue);
                 return false;
        	}
        }
        if (maxDiscount > discount && hykdiscount > discount) {
            layer.alert('对不起，您输入的折扣已超出最大折扣权限！'  );
            oldValue = $("#discount" + index).attr("oldvalue");
            $("#discount" + index).val(oldValue);
            return false;
        }
    }
    var oldValue = "";
    var num, discount, voidmoney, paymoney, unitprice, subtotal, arrearmoney;
    if (judgeSign($("#num" + index).val()) == true) {  // 验证数量是否为正数
        num = Number($("#num" + index).val());
    } else {
        layer.alert('输入项必须为正数' );
        oldValue = $("#num" + index).attr("oldvalue");
        $("#num" + index).val(oldValue);
        return false;
    }
    if (judgeSign($("#discount" + index).val()) == true) {
        discount = Number($("#discount" + index).val());
        if (discount > 100) {
            layer.alert('折扣必须小于等于100！'  );
            oldValue = $("#discount" + index).attr("oldvalue");
            $("#discount" + index).val(oldValue);
            return false;
        }
    } else {
        layer.alert('输入项必须为正数' );
        oldValue = $("#discount" + index).attr("oldvalue");
        $("#discount" + index).val(oldValue);
        return false;
    }
    if (judgeSignMoney($("#paymoney" + index).val()) == true) { // 缴费金额合法性校验
        paymoney = Number($("#paymoney" + index).val());
    } else {
        layer.alert('输入项必须为正数' );
        oldValue = $("#paymoney" + index).attr("oldvalue");
        $("#paymoney" + index).val(oldValue);
        return false;
    }
    unitprice = Number($("#unitprice" + index).val());
    subtotal = Number($("#subtotal" + index).html()); /** 这行代码好像没用 **/
    arrearmoney = Number($("#arrearmoney" + index).html());
    if (field == "discount") { // 修改折扣的时候，更改小计和付款金额，将免除金额设置为0；能修改折扣的时候，不存在欠费情况
        subtotal = Number(num * unitprice * discount / 100).toFixed(2);
        paymoney = Number(num * unitprice * discount / 100).toFixed(2);
        arrearmoney = 0.0;
        voidmoney = 0.0;
        $("#voidmoney" + index).val(voidmoney);
    }
    if (judgeSign($("#voidmoney" + index).val()) == true) { // 免除金额合法性校验
        voidmoney = Number($("#voidmoney" + index).val());
        
        if(voidmoney > maxVoidmoney){
        	layer.alert('免除金额不能大于' + maxVoidmoney  );
        	 oldValue = $("#voidmoney" + index).attr("oldvalue");
             $("#voidmoney" + index).val(oldValue);
        	return false;
        }
        //当单价为负数时不用判断缴费金额与免除金额
        if(unitprice >= 0) {
        	if (voidmoney > Number(num * unitprice * discount / 100)) {
                layer.alert('免除金额不能大于缴费金额'  );
                oldValue = $("#voidmoney" + index).attr("oldvalue");
                $("#voidmoney" + index).val(oldValue);
                return false;
            }
        }
        
    } else {
        layer.alert('输入项必须为正数' );
        oldValue = $("#voidmoney" + index).attr("oldvalue");
        $("#voidmoney" + index).val(oldValue);
        return false;
    }
    if (field == "num") {  // 修改数量的时候，更改小计和付款金额，将免除金额设置为0；能修改数量的时候，不存在欠费情况
        subtotal = Number(num * unitprice * discount / 100).toFixed(2);
        paymoney = Number(num * unitprice * discount / 100).toFixed(2);
        arrearmoney = 0;
        voidmoney = 0.0;
    }
   
    if (field == "voidmoney") { // 修改免除的时候，更改付款金额；能修改免除的时候，不存在欠费情况
        paymoney = Number(num * unitprice * discount / 100 - voidmoney).toFixed(2);
        arrearmoney = 0.0;
    }
    if (field == "paymoney") {
        if (isqfreal == 1) {
            oldValue = $("#paymoney" + index).attr("oldvalue"); /** 这儿的情况为：一个项目欠费60，这时划价一个付款为10的单子，然后再修改这个单子，就需要取oldValue 为10的值，因为此时欠费金额为50，而不是60 **/
            arrearmoney = Number(Number(oldValue) + Number(arrearmoney) - Number(paymoney)).toFixed(2);
        } else {
            arrearmoney = Number(Number(subtotal) - Number(voidmoney) - Number(paymoney)).toFixed(2);
        }
    }
    //缴费费用>小计-免除
    if (Number(arrearmoney) < 0) {
        var oldpayvalue = $("#paymoney" + index).attr("oldvalue"); /** 代码重复？ **/
        $("#paymoney" + index).val(oldpayvalue);
        layer.alert('缴费金额过多' );
        oldValue = $("#paymoney" + index).attr("oldvalue"); /** 代码重复？ **/
        $("#paymoney" + index).val(oldValue);
        return false;
    }
    $("#subtotal" + index).html(subtotal);
    $("#voidmoney" + index).val(voidmoney);
    $("#arrearmoney" + index).html(arrearmoney);
    $("#paymoney" + index).val(paymoney);
    //折扣额
    var zke = Number(unitprice * num * (100 - discount) / 100).toFixed(2);
    $("#zke" + index).html(Number(zke));

    $("#num" + index).attr("oldvalue", num);
    $("#discount" + index).attr("oldvalue", discount);
    $("#voidmoney" + index).attr("oldvalue", voidmoney);
    $("#paymoney" + index).attr("oldvalue", paymoney);
    //刷新金额
    getAlljs();
}

// 消费项目为治疗费的时候，只可以修改单价!
function editunitprice(index, field) {
    if (judgeSign($("#unitprice" + index).val()) == true) {
        num = Number($("#unitprice" + index).val());
    } else {
        layer.alert('输入项必须为正数' );
        oldValue = $("#unitprice" + index).attr("oldvalue");
        $("#unitprice" + index).val(oldValue);
        return false;
    }
    $("#subtotal" + index).html($("#unitprice" + index).val());
    $("#paymoney" + index).val($("#unitprice" + index).val());
    //刷新金额
    getAlljs();
}
function editOrder(status) { // 签署同意书，status值为3；没签署，status值为1；费用计划，status值为0；
	static_userObj = getHzNameByusercodeTB(static_usercode); // 根据患者编号查询患者对象(防止出现改名字)
    var isprint = 0;
    if (status == "3") {
        isprint = 1; /** isprint为0时，未签署同意书，为1时，签署同意书 **/
        status = 1; /** status为0时，是费用计划，为1时，是费用单 **/
    }
    //费用单 数据
    var paramOrder = {
        regno: static_regno,
        username: static_userObj.username, /** cost order表不建议存用户名 **/
        usercode: static_usercode,
        deleteitem:delete_item,  // 存储修改费用单时删除的 收费项目
        totalcost: $("#totalcost").val(),
        voidmoney: $("#voidmoney").val(),
        shouldmoney: $("#shouldmoney").val(),
        discountmoney: $("#discountmoney").val(),
        arrearmoney: $("#arrearmoney").val(),
        // totalarrmoney: $("#actualmoney").val(), /** totalarrmoney字段废弃无用 **/
        actualmoney: $("#actualmoney").val(),
        status: status,
        techperson: $("#techperson").val(),
        nurse: $("#nurse").val(),
        doctor: $("#doctor").val(),
        repair:$('#repair').val(),
        isprint: isprint,
        seqId: static_costno // 有值时，是修改操作

    };
    //费用详情 数据
    //循环获取表格中项目
    var list = [];
    var qfId = "";
    var isExistQFZerson = false; // 判断是否存在欠费且缴费金额为0的项目，如果存在，则不允许操作
    $('#table').find('tbody').each(function() {
        $(this).find('tr').each(function() {
            var param = {};
            param.isqfreal = 0; // 默认不欠费
            var createtime = $(this).attr("createtime"); // 创建时间
            param.createtime = createtime;
            $(this).find('td').each(function() {
                param.usercode = static_usercode;
                param.costno = static_costno;
                if ($(this).index() == 1) {
                    //项目编号
                    param.itemno = $(this).text();
                } else if ($(this).index() == 2) {
                    //医生
                    param.doctor = $(this).find("select").val();
                } else if ($(this).index() == 3) {
                    //特殊项目
                    if ($(this).text() == "否") {
                        param.istsxm = 0;
                    } else {
                        param.istsxm = 1;
                    }
                } else if ($(this).index() == 4) {
                    //治疗项目
                    param.itemname = $(this).find("span").html();
                } else if ($(this).index() == 5) {
                    //单位
                    param.unit = $(this).text();
                } else if ($(this).index() == 6) {
                    //单价
                    param.unitprice = $(this).find("input").val();
                } else if ($(this).index() == 7) {
                    //数量
                    param.num = $(this).find("input").val();
                } else if ($(this).index() == 8) {
                    //折扣
                    param.discount = $(this).find("input").val();
                } else if ($(this).index() == 9) {
                    //小计
                    param.subtotal = $(this).find("span").html();
                } else if ($(this).index() == 10) {
                    //欠费金额
                    param.arrearmoney = $(this).find("span").html();
                    //实际欠费 标识
                    if ($(this).find("span").attr("isqfreal") == "1") {
                        param.isqfreal = 1;
                        param.status = 1;
                    }
                } else if ($(this).index() == 11) {
                    //缴费金额
                    param.paymoney = $(this).find("input").val();
                } else if ($(this).index() == 12) {
                    //免除
                    param.voidmoney = $(this).find("input").val();
                } else if ($(this).index() == 14) { /*********** 这里要注意的是：欠费项目此列存空值，因为不删除旧的欠费明细 *****/
                    //Id，费用明细表的主键
                    param.seqId = $(this).text();
                } else if ($(this).index() == 15) {
                    //欠费Id
                    qfId += $(this).text() + ",";
                } else if ($(this).index() == 17) {
                    //欠费编号 qfbh，uuid无规则随机数
                    param.qfbh = $(this).text();
                } else if ($(this).index() == 18) {
                    //消费项目类型，如预交金
                    param.isyjjitem = $(this).text();
                }
                
                /** 欠费项目的缴费金额不允许为0 **/
                if(param.qfbh && param.paymoney == 0){
                	isExistQFZerson = true;
                }
            });
            list.push(param);
        });
    });
    
    if(isExistQFZerson){ // 判断是否存在欠费且缴费金额为0的项目，如果存在，则不允许操作
    	layer.alert('欠费项目的缴费金额需大于0，如不还款，请删除该欠费项目再继续操作！'  );
    	return false;
    }
    
    
    var data = JSON.stringify(list);
    paramOrder.listDetail = data;
    paramOrder.type = costorder_type; // 新增收费项目0 修改 收费项目1  /** 这个type的作用，可以用costOrder的seqId是否有值来替换  **/
    //保存时把原欠款项目的标识改为0，欠款带入的新欠款标识改为1
    if (qfId.length > 0) {
        qfId = qfId.substring(0, qfId.length - 1); /** 这里的欠费id就是原消费明细记录的主键  **/
    }
    paramOrder.qfId = qfId;
    paramOrder.askperson = $("#askperson").val();
    paramOrder.repair = $("#repair").val();
    var url = contextPath + '/KQDS_CostOrderAct/insertOrUpdate.act';
    $.axse(url, paramOrder,
    function(r) {
        if (r.retState == "0") {
            layer.alert('操作成功', {
                  
                end: function() {
                    parent.fyxqClick();
                    if (typeof(parent.initclick) != "undefined") {
                        parent.initclick();
                    }
                    parent.layer.close(frameindex); //再执行关闭 */
                }
            });
        } else {
            layer.alert(r.retMsrg, {
                  
                end: function() {
                    // parent.fyxqClick();
                    // if (typeof(parent.initclick) != "undefined") {
                    //     parent.initclick();
                    // }
                    // parent.layer.close(frameindex); //再执行关闭 */
                	return false;
                }
            });
        }
    },
    function() {
        layer.alert('请求失败' );
    });
}
function getAlljs() {
    //循环获取表格中项目
    var totalcost = 0.0,
    voidmoney = 0.0,
    shouldmoney = 0.0,
    discountmoney = 0.0,
    arrearmoney = 0.0,
    paymoney = 0.0;
    var payhtml = 0.0,
    arrearhtml = 0.0;
    $('#table').find('tbody').each(function() {
        $(this).find('tr').each(function() {
            $(this).find('td').each(function() {
                if ($(this).index() == 9) {
                    //小计
                    totalcost += Number($(this).text());
                } else if ($(this).index() == 12) {
                    //免除
                    voidmoney += Number($(this).find("input").val());
                } else if ($(this).index() == 10) {
                    //欠费金额
                    arrearmoney += Number($(this).find("span").html());
                    if ($(this).find("span").attr("isqfreal") != "1") {
                        arrearhtml += Number($(this).find("span").html());
                    }
                } else if ($(this).index() == 11) {
                    //缴费金额
                    paymoney += Number($(this).find("input").val());
                    if ($(this).find("input").attr("isqfreal") != "1") {
                        payhtml += Number($(this).find("input").val());
                    }
                } else if ($(this).index() == 13) {
                    //折扣额
                    // if ($(this).find("input").attr("isqfreal") != "1") {
                        discountmoney += Number($(this).find("span").html());
                    // }
                }
            });
        });
    });
    shouldmoney += Number(payhtml) + Number(arrearhtml); /** 这里的应收=缴费+欠费（不包括欠费单的！） **/
    $("#totalcost").val(totalcost.toFixed(2));
    $("#voidmoney").val(voidmoney.toFixed(2));
    $("#shouldmoney").val(shouldmoney.toFixed(2));
    $("#discountmoney").val(discountmoney.toFixed(2));
    $("#arrearmoney").val(arrearmoney.toFixed(2));
    // $("#totalarrmoney").val("0.0");
    $("#actualmoney").val(paymoney.toFixed(2));
}
//删除行
function deltr(tr, isqfreal, id) {
	// static_yjjiem = 0;
	var expireflag = $(tr.parentNode.parentNode).attr("expireflag"); // 判断当前删除的是不是过期项目  1 是
	if('1' == expireflag){
		// $("#costitemlistBody").find('tr').empty();
		// return false;
	}
	
	if(static_zhekou == "1"){
		layer.alert('只允许进行折扣操作！', {
               
        });
		return false;
	}
	
	/*if (isqfreal == 1) {
        layer.alert('实际欠费项目不能删除！', {
               
        });
        return false;
    }*/
    var i = tr.parentNode.parentNode.rowIndex;
    document.getElementById('table').deleteRow(i);
    if(id != "" && id != null){
    	delete_item += id +",";
    }
    //刷新金额
    getAlljs();
}
//基本信息
function getJbxx() {
    var r = getOneByUsercode(static_usercode);
    if(r){
    	var data = r.data[0];
        $('#form1 input[name=seqId]').val(data.seqId);
        $('#username').val(data.username);
        $('#age').val(data.age);

        if (canlookphone == 0) {
            $('#phonenumber1').val(data.phonenumber1);
        }

        $('#sex').val(data.sex);
        var introducerstr = data.introducer.replace(/(^\s*)|(\s*$)/g, ""); /** 这个正则是什么作用？ **/
        if (introducerstr != "" && introducerstr != "null" && introducerstr != null) {
            $('#introducer').val(data.introducer);
            $('#introducerDesc').val(getHzNameByusercodeTB(data.introducer).username);
        }
        /*** 注释掉多余代码 **/
        // $('#devchannel').val(data.devchannel);
        // getSubDictSelect('devchannel', 'nexttype');
        // $('#nexttype').val(data.nexttype);*/
        // $('#platenumber').val(data.platenumber); // 车牌号
        $('#firstword').val(data.firstword); // 第一句话
    }
    
}
function saveform1() {
    var param = $('#form1').serialize();
    var url = contextPath + '/KQDS_UserDocumentAct/editSub.act?' + param;
    $.axseSubmit(url, null,
    function() {},
    function(r) {
        if (r.retState == "0") {
            layer.alert('保存成功', {
                  
                end: function() {}
            });
        } else {
            layer.alert('保存失败'  );
        }
    },
    function() {
        layer.alert('保存失败' );
    });
}
//修改
function getOrderDetailList(costno) {
    var pageurl = contextPath + '/KQDS_CostOrder_DetailAct/selectNoPage4Cost.act?costno=' + costno;
    $.axse(pageurl, null,
    function(data) {
        for (var i = 0; i < data.length; i++) {
            tdindex++;
            var tablehtml = "";
            var tabledata = data[i];
            // 如果明细中 包括预交金 则不能 开其他收费项目
            if(tabledata.isyjjitem == 1){
            	// static_yjjiem = 1;
            }
            if(tabledata.isyjjitem != 2){ // isyjjitem的值  1是预交金，2 是挂号费
            	// static_ghiem = 0; // 这里表示，不是挂号费
            }
            
            var trbgcolor = "";
            if('1' == tabledata.expireflag){ // 已过期项目，加颜色标识
            	trbgcolor = "background: #FFB90F";
            }
            
            var isqfrealFlag = "0";
            if(tabledata.qfbh){
            	isqfrealFlag = "1";
            }
            
            tablehtml += "<tr style='" + trbgcolor + "' onclick='getItemInfo(\""+tabledata.itemno+"\")' expireflag='" + tabledata.expireflag + "' createtime='" + tabledata.createtime + "' >"; // 这里新增加了两个属性
            //删除按钮0
            tablehtml += '<td style=""><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this,\'' + isqfrealFlag + '\',\'' + tabledata.seqId + '\')"><span style="color:red;">删除</span></a></td>';
            //项目编号1
            tablehtml += '<td style="display:none;">' + tabledata.itemno + '</td>';
            //医生
            tablehtml += '<td style="display:none;"><select id="doctor' + tdindex + '" style="width:70px;height:22px;"></select></td>';
            //特殊项目3
            var tsxm = tabledata.istsxm == 0 ? "否": "是";
            tablehtml += '<td style="">' + tsxm + '</td>';
            //治疗项目4
            var itemnameBZ = ''; // 治疗项目标识
            if (tabledata.istk == 1) {
            	itemnameBZ = '<div class="label label-inverse" style="position:relative;top:-5px;">退款</div>'; // 这里使用div，而不使用span，是为了让div不入库
            } else {
                if (Number(tabledata.y2) < 0) {
                	itemnameBZ = '<div class="label label-warning" style="position:relative;top:-5px;">还款</div>';
                } else if (Number(tabledata.y2) > 0) {
                	itemnameBZ = '<div class="label label-danger" style="position:relative;top:-5px;">欠款</div>';
                } else if (Number(tabledata.y2) == 0 && tabledata.isqfreal == 1) {
                	itemnameBZ = '<div class="label label-warning" style="position:relative;top:-5px;">还款</div>';
                }
            }
            tablehtml += '<td style="text-align:left;">'+itemnameBZ+'<span style=" text-align:left;" class="time" title=\''+tabledata.itemname+'\'>'+ tabledata.itemname + '</span></td>';
            //单位5
            tablehtml += '<td style="">' + tabledata.unit + '</td>';
            //单价6
            if (tabledata.isyjjitem == 3) {
                tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  onchange="editunitprice(\'' + tdindex + '\',\'unitprice\')"  id="unitprice' + tdindex + '" value="' + tabledata.unitprice + '" oldvalue="' + tabledata.unitprice + '"></td>';
            } else {
                tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  readonly="readonly"  id="unitprice' + tdindex + '" value="' + tabledata.unitprice + '" oldvalue="' + tabledata.unitprice + '"></td>';
            }
            //数量7
            if (tabledata.qfbh || tabledata.isyjjitem == 3 || tabledata.isyjjitem == 1) { //实际欠费项目
                tablehtml += '<td style=""><input  type="text" min="1" style="width:60px; text-align:center;" onfocus="this.select()" readonly="readonly" onchange="edithz(\'' + tdindex + '\',\'num\')"  id="num' + tdindex + '" value="' + tabledata.num + '" oldvalue="' + tabledata.num + '"></td>';
            } else {
                tablehtml += '<td style=""><input  type="text" min="1" style="width:60px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'num\')"  id="num' + tdindex + '" value="' + tabledata.num + '" oldvalue="' + tabledata.num + '"></td>';
            }
            //折扣8
            if (tabledata.qfbh || tabledata.isyjjitem == 3 || tabledata.isyjjitem == 1) { //实际欠费项目
                tablehtml += '<td style=""><input  type="text" min="1" style="width:75px; text-align:center;" onfocus="this.select()" readonly="readonly" onchange="edithz(\'' + tdindex + '\',\'discount\')"  id="discount' + tdindex + '" value="' + tabledata.discount + '" oldvalue="' + tabledata.discount + '"></td>';
            } else {
                tablehtml += '<td style=""><input  type="text" min="1" style="width:75px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'discount\')"  id="discount' + tdindex + '" value="' + tabledata.discount + '" oldvalue="' + tabledata.discount + '"></td>';
            }
            //小计9
            tablehtml += '<td style=""><span  id="subtotal' + tdindex + '">' + tabledata.subtotal + '</span></td>';
            //欠费金额10
            if (tabledata.qfbh) {
            	tablehtml += '<td style=""><span  id="arrearmoney' + tdindex + '" isqfreal="1">' + tabledata.arrearmoney + '</span></td>';
            }else{
            	tablehtml += '<td style=""><span  id="arrearmoney' + tdindex + '" isqfreal="0">' + tabledata.arrearmoney + '</span></td>';
            }
            //缴费金额11
            if (tabledata.qfbh) { /** 不通过isqfreal进行判断,通过qfbh进行判断 **/
                tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'paymoney\',\'1\')"  id="paymoney' + tdindex + '" value="' + tabledata.paymoney + '" oldvalue="' + tabledata.paymoney + '" isqfreal="1"></td>';
            } else if (tabledata.isyjjitem == 3) {
                tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  readonly="readonly"  id="paymoney' + tdindex + '" value="' + tabledata.paymoney + '" oldvalue="' + tabledata.paymoney + '"></td>';
            } else if (tabledata.isyjjitem == 1) {
                tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  onchange="yjjcz(\'' + tdindex + '\',\'paymoney\')"  id="paymoney' + tdindex + '" value="' + tabledata.paymoney + '" oldvalue="' + tabledata.paymoney + '"></td>';
            } else {
                tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'paymoney\')"  id="paymoney' + tdindex + '" value="' + tabledata.paymoney + '" oldvalue="' + tabledata.paymoney + '"></td>';
            }
            //免除12
            if (tabledata.qfbh) { //实际欠费项目
                tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()" readonly="readonly" onchange="edithz(\'' + tdindex + '\',\'voidmoney\')"  id="voidmoney' + tdindex + '" value="' + tabledata.voidmoney + '" oldvalue="' + tabledata.voidmoney + '"></td>';
            } else if (tabledata.isyjjitem == 3|| tabledata.isyjjitem == 1) {
                tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  readonly="readonly" id="voidmoney' + tdindex + '" value="' + tabledata.voidmoney + '" oldvalue="' + tabledata.voidmoney + '"></td>';
            } else {
                tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'voidmoney\')"  id="voidmoney' + tdindex + '" value="' + tabledata.voidmoney + '" oldvalue="' + tabledata.voidmoney + '"></td>';
            }
            //折扣额13
            var zke = Number(tabledata.unitprice * tabledata.num * (100 - tabledata.discount) / 100);
            tablehtml += '<td style="display:none;"><span  id="zke' + tdindex + '">' + zke + '</span></td>';
            //ID14
            tablehtml += '<td style="display:none;">' + tabledata.seqId + '</td>';
            //欠费ID15
            tablehtml += '<td style="display:none;"></td>';
            //setIsqfreal 16
            tablehtml += '<td style="display:none;"></td>';
            //欠费编号17
            tablehtml += '<td style="display:none;">' + tabledata.qfbh + '</td>';
            //预交金开单
            tablehtml += '<td style="display:none;">' + tabledata.isyjjitem + '</td>';
            //分类13################添加药品分类##################
//          alert(tabledata.classify);
            if (tabledata.classify == 1) {
            	tablehtml += '<td style=""><span  id="classify' + tdindex + '">' + "高危药品" + '</span></td>';
            } else if (tabledata.classify == 2) {
            	tablehtml += '<td style=""><span  id="classify' + tdindex + '">' + "抗菌素" + '</span></td>';
            } else if (tabledata.classify == 3) {
            	tablehtml += '<td style=""><span  id="classify' + tdindex + '">' + "其他" + '</span></td>';
            }
            tablehtml += "</tr>";

            $("#table").find('tbody').append(tablehtml);
            //每次table中有数据变动都要重新计算固定表头的宽度 防止错位。
            setFixedHeaderWidth();
            //医生 下拉列表
            initPersonSelectByDeptType("doctor"+tdindex,doctype);
            $("#doctor"+tdindex).val(tabledata.doctor);
        }
        //刷新金额
        getAlljs();
        static_askperson = tabledata.askperson; /** 这个赋值好像有问题！ **/
    },
    function() {
        layer.alert('查询出错！'  );
    });
}
/**
 * 欠费项目列表，这里要注意的是：欠费项目不存在编辑，只会新增！！！
 */
function getQfDetailList(usercode) {
    var pageurl = contextPath + '/KQDS_CostOrderAct/getQk.act?usercode=' + usercode;
    $.axse(pageurl, null,
    function(data) {
        for (var i = 0; i < data.length; i++) {
            tdindex++;
            var tablehtml = "";
            var tabledata = data[i];
            
            var ifAddQfItemInList = ifAddQFItemInList(tabledata.qfbh); // 判断是否需要把这个欠费项目添加到列表中 true 显示 false不显示
            if(!ifAddQfItemInList){
            	continue;
            }
            
            if(tabledata.isyjjitem != 2){
            	// static_ghiem = 0;
            }
            tablehtml += "<tr style='' onclick='getItemInfo(\""+tabledata.itemno+"\")' createtime='" + tabledata.createtime + "' >"; // 这里新增加了1个属性
            //删除按钮0
            tablehtml += '<td style=""><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this,1,\'' + tabledata.seqId + '\')"><span style="color:red;">删除</span></a></td>';
            //项目编号1
            tablehtml += '<td style="display:none;">' + tabledata.itemno + '</td>';
            //医生
            tablehtml += '<td style="display:none;"><select id="doctor' + tdindex + '" style="width:70px;height:22px;"></select></td>';
            //特殊项目3
            var tsxm = tabledata.istsxm == 0 ? "否": "是";
            tablehtml += '<td style="">' + tsxm + '</td>';
            //治疗项目4
            var itemnameBZ = ''; // 治疗项目标识
            if (tabledata.istk == 1) {
            	itemnameBZ = '<div class="label label-inverse" style="position:relative;top:-5px;">退款</div>';
            } else {
                if (Number(tabledata.y2) < 0) {
                	itemnameBZ = '<div class="label label-warning" style="position:relative;top:-5px;">还款</div>';
                } else if (Number(tabledata.y2) > 0) {
                	itemnameBZ = '<div class="label label-danger" style="position:relative;top:-5px;">欠款</div>';
                } else if (Number(tabledata.y2) == 0 && tabledata.isqfreal == 1) {
                	itemnameBZ = '<div class="label label-warning" style="position:relative;top:-5px;">还款</div>';
                }
            }
            tablehtml += '<td style="text-align:left;">'+ itemnameBZ + '<span style=" text-align:left;" class="time" title=\''+tabledata.itemname+'\'>' + tabledata.itemname + '</span></td>';
            //单位5
            tablehtml += '<td style="">' + tabledata.unit + '</td>';
            //单价6
            tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  readonly="readonly"  id="unitprice' + tdindex + '" value="' + tabledata.unitprice + '" oldvalue="' + tabledata.unitprice + '"></td>';
            //数量7
            tablehtml += '<td style=""><input  type="text" min="1" style="width:60px; text-align:center;" onfocus="this.select()" readonly="readonly" id="num' + tdindex + '" value="' + tabledata.num + '"></td>';
            //折扣8
            tablehtml += '<td style=""><input  type="text" min="1" style="width:75px; text-align:center;" onfocus="this.select()" readonly="readonly" id="discount' + tdindex + '" value="' + tabledata.discount + '"></td>';
            //小计9
            tablehtml += '<td style=""><span  id="subtotal' + tdindex + '">0</span></td>';
            //欠费金额10
            tablehtml += '<td style=""><span  id="arrearmoney' + tdindex + '" isqfreal="1">' + tabledata.arrearmoney + '</span></td>';
            //缴费金额11
            tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'paymoney\',\'1\')"  id="paymoney' + tdindex + '" value="0" oldvalue="0" isqfreal="1"></td>';
            //免除12
            tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()" readonly="readonly" id="voidmoney' + tdindex + '" value="0"></td>';
            //折扣额13
            tablehtml += '<td style="display:none;"><span  id="zke' + tdindex + '" isqfreal="1">0</span></td>';
            //ID14
            tablehtml += '<td style="display:none;"></td>';
            //欠费ID15
            tablehtml += '<td style="display:none;">' + tabledata.seqId + '</td>';
            //setIsqfreal 16
            tablehtml += '<td style="display:none;">1</td>';
            //欠费编号17
            tablehtml += '<td style="display:none;">' + tabledata.qfbh + '</td>';
            //预交金开单
            tablehtml += '<td style="display:none;">' + tabledata.isyjjitem + '</td>';
            tablehtml += "</tr>";

            $("#table").find('tbody').append(tablehtml);
          //每次table中有数据变动都要重新计算固定表头的宽度 防止错位。
            setFixedHeaderWidth();
            //医生 下拉列表
            initPersonSelectByDeptType("doctor"+tdindex,doctype);
            $("#doctor"+tdindex).val(tabledata.doctor);
        }
        // 刷新金额
        getAlljs();
        if (data != null && data.length > 0) {
            // 默认带入还款的所属医生，如果多个欠费单时，取第一个
            if (data[0].doctor != null && data[0].doctor != "") {
            	static_Hk_doctor = data[0].doctor;
                $('#doctor').val(data[0].doctor);
                bindPerUserNameBySeqIdTB("doctorDesc", data[0].doctor);
            }
            //默认带入护士
            /**
            var costorderObj = getCostOrderObjBySeqId(data[0].costno);
            if(costorderObj != null && costorderObj.nurse != null && costorderObj.nurse != ""){
            	$("#nurse").val(costorderObj.nurse);
                bindPerUserNameBySeqIdTB("nurseDesc", costorderObj.nurse);
            }*/
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

/** 这里优化下，如果开单页面的状态复选框没有勾选，则不向后台请求 **/
function getItemInfo(itemno){
	var ifchecked = $("#yhstatus").prop("checked");
	if(ifchecked == false){
		return false;
	}
	var detailurl = contextPath + '/KQDS_TreatItemAct/getOneBytreatitemno.act?treatitemno=' +itemno;
    $.axse(detailurl, null,
    function(data) {
        if (data.retState == "0") {
            var tabledata = data.data;
            $("#xmjs").val(tabledata.xmjs);
            $("#yhxx").val(tabledata.yhxx);
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}
function zTreeInit() {
    //异步加载
    var search = $("#search").val();
    var url = contextPath + '/YZDictCostAct/getSelectTreeAsync.act?search=' + search;

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
    $.fn.zTree.init($("#treeDemo"), setting);

}
function zTreeInitTc() {
    var url = contextPath + '/YZDictCostAct/getSelectTreeTcAsync.act';
    var setting = {
        view: {
            showIcon: false // 去掉图标
        },
        check: {
            enable: true,
            chkStyle: "radio",
            radioType: "all"
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
        onClick: onClickTc
    };
    $.fn.zTree.init($("#treeDemo"), setting);
}
//新增项目
function onclick(e, treeId, treeNode) {
	/** 判断是否存在过期项目，并给予提示 **/
    judgeIfExistExpireItem();
    
	if(static_zhekou == "1"){           /** 只有修改的时候，才会有zhekou这个参数的传入 **/
		layer.alert('只允许进行折扣操作！', {
               
        });
		return false;
	}
	if(static_isback == "1"){
		layer.alert('退单不允许添加新的收费项目！', {
               
        });
		return false;
	}
	 if(treeNode.id=="ysfa396"||treeNode.pId=="ysfa396"||treeNode.pId=="scfa832"){
		 
	 }else{
		 var isExistYYJItem = isExistTreatItemSort('1');
		 if(isExistYYJItem == true){
			 layer.alert('开单项包括预交金，不允许添加其他收费项目！', {
	                
	         });
			 return false;
		 }
	 }
	
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.expandNode(treeNode);
    } else {
        treeNode.check_Focus = true;
        treeNode.checked = true;
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        treeObj.updateNode(treeNode);
        var detailurl = contextPath + '/KQDS_TreatItemAct/getOneBytreatitemno.act?treatitemno=' + treeNode.id;
        $.axse(detailurl, null,
        function(data) {
            if (data.retState == "0") {
                tdindex++;
                var tablehtml = "";
                var itemsTabledata = data.data;
                var basetypeoftable=$("#costitemlistBody").find("tr").find("td.zlxm").find("span").attr("basetype"); 
            	 if ($("#table tr").length > 1&&basetypeoftable=="ysfa396") {        		             		 	
           		 	
                 }else if($("#table tr").length > 1 && itemsTabledata.isyjjitem == 1){
	                	 layer.alert('存在其他收费项目，不允许添加预交金项目！', {
	                       
	                   });
                	 return false;
                 }
               
                /**
                 * 校验开单为检查项目时，不容许添加其他项目，只能添加检验项目 syp 2019-11-13 start
                 */
                /*var itemType = "jyk671";
                var tcitemseq_id = "cdbce74f-c329-4359-9358-6a5b170f7c10";
                if(tcTabledata != null) {
                	console.log(tcTabledata.tcnameid+"55");
                	if(tcTabledata.tcnameid == tcitemseq_id) {
                		if(itemsTabledata.basetype != itemType) {
                			layer.alert('检验套餐只能添加检验项目，不允许添加其他项目！', {
                				
                			});
                			return false;
                		}
                	}
                } */
                /**
                 * end
                 */
                // 如果明细中 包括预交金 则不能 开其他收费项目
                if(itemsTabledata.isyjjitem == 1){
                	// static_yjjiem = 1;
                	$("#doctor").val("");
                    $("#nurse").val("");
                    $("#techperson").val("");
                    $("#doctorDesc").val("");
                }
                if(itemsTabledata.isyjjitem != 2){
                	// static_ghiem = 0;
                }
                $("#xmjs").val(itemsTabledata.xmjs);
                $("#yhxx").val(itemsTabledata.yhxx);
                var subtotal = itemsTabledata.unitprice * itemsTabledata.discount / 100;
                tablehtml += "<tr style='' onclick='getItemInfo(\""+treeNode.id+"\")'>";
                //删除按钮0
                tablehtml += '<td style=""><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this,0,\'\')"><span style="color:red;">删除</span></a></td>';
                //项目编号1
                tablehtml += '<td style="display:none;">' + treeNode.id + '</td>';
                //医生
                tablehtml += '<td style="display:none;"><select id="doctor' + tdindex + '" style="width:70px;height:22px;"></select></td>';
                //特殊项目3
                var tsxm = itemsTabledata.istsxm == 0 ? "否": "是";
                tablehtml += '<td style="">' + tsxm + '</td>';
                //治疗项目4
                tablehtml += '<td style="" class="zlxm"><span style="float:left; text-align:left;" class="time" basetype=\''+itemsTabledata.basetype+'\'  title=\''+itemsTabledata.treatitemname+'\'>' + itemsTabledata.treatitemname + '</span></td>';
                //单位5
                tablehtml += '<td style="">' + itemsTabledata.unit + '</td>';
                //单价6
                if (itemsTabledata.isyjjitem == 3) {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  onchange="editunitprice(\'' + tdindex + '\',\'unitprice\')"  id="unitprice' + tdindex + '" value="' + itemsTabledata.unitprice + '" oldvalue="' + itemsTabledata.unitprice + '"></td>';
                } else {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  readonly="readonly" onchange="edithz(\'' + tdindex + '\',\'unitprice\')"  id="unitprice' + tdindex + '" value="' + itemsTabledata.unitprice + '" oldvalue="' + itemsTabledata.unitprice + '"></td>';
                }

                //数量7
                if (itemsTabledata.isyjjitem == 3 || itemsTabledata.isyjjitem == 1) {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:60px;text-align:center;"   readonly="readonly"  id="num' + tdindex + '" value="1" oldvalue="1"></td>';
                } else {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:60px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'num\')"  id="num' + tdindex + '" value="1" oldvalue="1"></td>';

                }
                //折扣8
                if (itemsTabledata.isyjjitem == 3 || itemsTabledata.isyjjitem == 1) {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:75px; text-align:center;"  readonly="readonly" id="discount' + tdindex + '" value="' + itemsTabledata.discount + '"></td>';
                } else {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:75px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'discount\')"  id="discount' + tdindex + '" value="' + itemsTabledata.discount + '" oldvalue="' + itemsTabledata.discount + '"></td>';

                }
                //小计9
                tablehtml += '<td style=""><span  id="subtotal' + tdindex + '">' + subtotal + '</span></td>';
                //欠费金额10
                tablehtml += '<td style=""><span  id="arrearmoney' + tdindex + '">0</span></td>';
                //缴费金额11
                if (itemsTabledata.isyjjitem == 3) {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" readonly="readonly"  id="paymoney' + tdindex + '" value="' + itemsTabledata.unitprice + '" oldvalue="' + itemsTabledata.unitprice + '"></td>';
                } else if (itemsTabledata.isyjjitem == 1) {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:90px;text-align:center;" onfocus="this.select()"  onchange="yjjcz(\'' + tdindex + '\',\'paymoney\')"  id="paymoney' + tdindex + '" value="0" oldvalue="0"></td>';
                } else {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:90px;text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'paymoney\')"  id="paymoney' + tdindex + '" value="' + subtotal + '" oldvalue="' + subtotal + '"></td>';
                }
                //免除12
                if (itemsTabledata.isyjjitem == 3 || itemsTabledata.isyjjitem == 1) {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" readonly="readonly"  id="voidmoney' + tdindex + '" value="0" oldvalue="0"></td>';
                } else {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'voidmoney\')"  id="voidmoney' + tdindex + '" value="0" oldvalue="0"></td>';
                }
                //折扣额13
                tablehtml += '<td style="display:none;"><span  id="zke' + tdindex + '">0</span></td>';
                //ID14
                tablehtml += '<td style="display:none;"></td>';
                //欠费ID15
                tablehtml += '<td style="display:none;"></td>';
                //setIsqfreal 16
                tablehtml += '<td style="display:none;"></td>';
                //欠费编号17
                tablehtml += '<td style="display:none;"></td>';
                //预交金开单
                tablehtml += '<td style="display:none;">' + itemsTabledata.isyjjitem + '</td>';
                //分类13################添加药品分类##################
//              alert(tabledata.classify);
                if (itemsTabledata.classify == 1) {
                	tablehtml += '<td style=""><span  id="classify' + tdindex + '">' + "高危药品" + '</span></td>';
                } else if (itemsTabledata.classify == 2) {
                	tablehtml += '<td style=""><span  id="classify' + tdindex + '">' + "抗菌素" + '</span></td>';
                } else if (itemsTabledata.classify == 3) {
                	tablehtml += '<td style=""><span  id="classify' + tdindex + '">' + "其他" + '</span></td>';
                }
                tablehtml += "</tr>";

                $("#table").find('tbody').append(tablehtml);
              //每次table中有数据变动都要重新计算固定表头的宽度 防止错位。
                setFixedHeaderWidth();
                //刷新金额
                getAlljs();
                //医生 下拉列表
                /*initPersonSelectByDeptType("doctor"+tdindex,doctype);*/
                initSysUserByDeptId($("#doctor"+tdindex),"doctor","seqId");
                $("#doctor"+tdindex).val($("#doctor").val());
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }
}
//新增套餐项目
function onClickTc(e, treeId, treeNode) {
	console.log("新增套餐");
	/**
	 * 项目套餐不能含预交金校验  syp 2019-10-07
	 */
	var isExistYYJItem = isExistTreatItemSort('1');
	if(isExistYYJItem == true){
		layer.alert('开单项包括预交金，不允许添加套餐！', {
               
        });
		return false;
	}
	/**
	 * end
	 */
	
	/** 判断是否存在过期项目，并给予提示 **/
    judgeIfExistExpireItem();
	
	if(static_zhekou == "1"){
		layer.alert('只允许进行折扣操作！', {
               
        });
		return false;
	}
	if(static_isback == "1"){
		 layer.alert('退单不允许添加新的收费项目！', {
               
         });
		return false;
	}
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.expandNode(treeNode);
    } else {
        var param = {
            tcnameid: treeNode.id
        };
        var detailurl = contextPath + '/KQDS_TreatItem_TcAct/getList.act';
        $.axseY(detailurl, param,
        function(data) {
            if (data.retState == "0") {
                var allTableData = data.data; //获取表格的所有内容行 
                for (var i = 0; i < allTableData.length; i++) {
                    tdindex++;
                    var tablehtml = "";
                    var tcTabledata = allTableData[i];
//                    console.log(tcTabledata+"99");
                    if(tcTabledata.isyjjitem != 2){
                    	// static_ghiem = 0;
                    }
                    /**
                     * 校验开单为检查项目时，不容许添加其他项目，只能添加检验项目 syp 2019-11-13 start
                     */
                    /*var itemType = "jyk671";
                    var tcitemseq_id = "cdbce74f-c329-4359-9358-6a5b170f7c10";
                    if(itemsTabledata != null) {
                    	console.log(itemsTabledata+"6");
                    	if(itemsTabledata.basetype == itemType) {
                    		if(tcTabledata != null) {
                    			if(tcTabledata.tcnameid != tcitemseq_id) {
                    				layer.alert('检验项目只能添加检验套餐，不允许添加其他套餐！', {
                    					
                    				});
                    				return false;
                    			}
                    		}
                    	}
                    }*/
                    /**
                     * end
                     */
                    tablehtml += "<tr style='' onclick='getItemInfo(\""+tcTabledata.itemno+"\")'>";
                    //删除按钮
                    tablehtml += '<td style=""><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this,0,\'\')"><span style="color:red;">删除</span></a></td>';
                    //项目编号
                    tablehtml += '<td style="display:none;">' + tcTabledata.itemno + '</td>';
                    //医生
                    tablehtml += '<td style="display:none;"><select id="doctor' + tdindex + '" style="width:70px;height:22px;"></select></td>';
                    //特殊项目
                    var tsxm = tcTabledata.istsxm == 0 ? "否": "是";
                    tablehtml += '<td style="">' + tsxm + '</td>';
                    //治疗项目
                    tablehtml += '<td style="text-align:left;"><span style="float:left; text-align:left;" class="time" title=\''+tcTabledata.itemname+'\'>' + tcTabledata.itemname + '</span></td>';
                    //单位
                    tablehtml += '<td style="">' + tcTabledata.unit + '</td>';
                    //单价
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  readonly="readonly"  id="unitprice' + tdindex + '" value="' + tcTabledata.unitprice + '" oldvalue="' + tcTabledata.unitprice + '"></td>';
                    //数量
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:60px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'num\')"  id="num' + tdindex + '" value="' + tcTabledata.num + '" oldvalue="' + tcTabledata.num + '"></td>';
                    //折扣
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:75px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'discount\')"  id="discount' + tdindex + '" value="' + tcTabledata.discount + '" oldvalue="' + tcTabledata.discount + '"></td>';
                    //小计
                    tablehtml += '<td style=""><span  id="subtotal' + tdindex + '">' + tcTabledata.subtotal + '</span></td>';
                    //欠费金额
                    tablehtml += '<td style=""><span  id="arrearmoney' + tdindex + '">' + tcTabledata.arrearmoney + '</span></td>';
                    //缴费金额
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'paymoney\')"  id="paymoney' + tdindex + '" value="' + tcTabledata.paymoney + '" oldvalue="' + tcTabledata.paymoney + '"></td>';
                    //免除
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:90px;text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'voidmoney\')"  id="voidmoney' + tdindex + '" value="' + tcTabledata.voidmoney + '" oldvalue="' + tcTabledata.voidmoney + '"></td>';
                    //折扣额
                    tablehtml += '<td style="display:none;"><span  id="zke' + tdindex + '">0</span></td>';
                    //ID14
                    tablehtml += '<td style="display:none;"></td>';
                    //欠费ID15
                    tablehtml += '<td style="display:none;"></td>';
                    //setIsqfreal 16
                    tablehtml += '<td style="display:none;"></td>';
                    //欠费编号17
                    tablehtml += '<td style="display:none;"></td>';
                    //预交金开单
                    tablehtml += '<td style="display:none;">' + tcTabledata.isyjjitem + '</td>';
                    //分类13################添加药品分类##################
//                  alert(tabledata.classify);
                    if (tcTabledata.classify == 1) {
                    	tablehtml += '<td style=""><span  id="classify' + tdindex + '">' + "高危药品" + '</span></td>';
                    } else if (tcTabledata.classify == 2) {
                    	tablehtml += '<td style=""><span  id="classify' + tdindex + '">' + "抗菌素" + '</span></td>';
                    } else if (tcTabledata.classify == 3) {
                    	tablehtml += '<td style=""><span  id="classify' + tdindex + '">' + "其他" + '</span></td>';
                    }
                    tablehtml += "</tr>";

                    $("#table").find('tbody').append(tablehtml);
                    //医生 下拉列表
                    initPersonSelectByDeptType("doctor"+tdindex,doctype);
                    $("#doctor"+tdindex).val($("#doctor").val());
                   
                }
                //每次table中有数据变动都要重新计算固定表头的宽度 防止错位。
                setFixedHeaderWidth();
                //刷新金额
                getAlljs();
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }
}
function getjf(memberno) { /** 获取当前会员卡的积分，暂且注释不用 **/
    var url = contextPath + '/kqds/act/memberScore/KQDS_Member_ScoreAct/selectDetail.act?memberno=' + memberno;
    $.axse(url, null,
    function(data2) {
        if (data2.retState == "0") {
            $('#jf').val(data2.data.number);
        }
    },
    function() {});
}

function checkUser(tcnameid) { /** 不是本人创建的套餐，不允许删除 **/
    var flag = true;
    var detailurl = contextPath + '/KQDS_TreatItem_TcAct/selectDetailBytctypeAndtcname.act?type=1&tcnameid=' + tcnameid;
    $.axse(detailurl, null,
    function(obj) {
        if (obj.data[0].createuser != perseqId) {
            flag = false;
        }
    },
    function() {
        layer.alert('查询出错！' );
        flag = false;
    });
    return flag;
}

/* ----------------------------------------费用详情-------------------------------------- */
function getlist() {
	var pageurlfyxq = contextPath + '/KQDS_CostOrderAct/getByRegnoNopage.act';
    var url = pageurlfyxq + "?usercode=" + static_usercode;
	
	var tableObj = $('#tablefyxq').bootstrapTable('getOptions');
    if (tableObj.length == undefined) { // 如果length不存在，则说明bootstrap table已经初始化
    	 $('#tablefyxq').bootstrapTable('refresh', {
             'url': url
         });
    	 return;
    }
	
    $("#tablefyxq").bootstrapTable({
        url: url,
        dataType: "json",
        onLoadSuccess:function(){
        	setTableHeight();
        },
        columns: [{
            title: '病人编号',
            field: 'usercode',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '开单时间',
            field: 'createtime',
            align: 'center',
            sortable: true
        },
        {
            title: '成交情况',
            field: 'cjstatus',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value == 1) {
                    return '<span style="color:green">已成交</span>';
                } else {
                    return '<span style="color:red">未成交</span>';
                }
            }
        },
        {
            title: '合计',
            field: 'totalcost',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '免除金额',
            field: 'voidmoney',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '应收金额',
            field: 'shouldmoney',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '欠费金额',
            field: 'y2',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '缴费金额',
            field: 'actualmoney',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '开单人',
            field: 'createusername',
            align: 'center'
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#tablefyxq').find('tr.success').data('index'); //获得选中的行
        onclickrowOobjfyxq = $('#tablefyxq').bootstrapTable('getData')[index];
        // $("#xqxmqd").show(); 
        OrderDetail();
    });
}

function OrderDetail() {
	var costno = "\'" + onclickrowOobjfyxq.costno + "\'";
    var detailurlorder = contextPath + '/KQDS_CostOrder_DetailAct/NoselectPage.act?costno=' + costno;
	
	var tableObj = $('#dykdxm').bootstrapTable('getOptions');
    if (tableObj.length == undefined) { // 如果length不存在，则说明bootstrap table已经初始化
    	 $('#dykdxm').bootstrapTable('refresh', {
             'url': detailurlorder
         });
    	 return;
    }
    // $("#divkdxm").html('<table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="150"></table>');
    $("#dykdxm").bootstrapTable({
        url: detailurlorder,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess:function(){
        	setTableHeight();
        },
        columns: [{
            title: '项目编号',
            field: 'itemno',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '治疗项目',
            field: 'itemname',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '医生',
            field: 'doctorname',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '单位',
            field: 'unit',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '单价',
            field: 'unitprice',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return "￥" + row.unitprice;
            }
        },
        {
            title: '数量',
            field: 'num',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '折扣%',
            field: 'discount',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '小计',
            field: 'subtotal',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return "￥" + row.subtotal;
            }
        },
        {
            title: '免除',
            field: 'voidmoney',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                return "￥" + row.voidmoney;
            }
        },
        {
            title: '应收金额',
            field: ' ',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                return "￥" + (Number(row.subtotal) - Number(row.voidmoney));
            }
        },
        {
            title: '欠费金额',
            field: 'y2',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return "￥" + row.y2;
            }
        },
        {
            title: '缴费金额',
            field: 'paymoney',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return "￥" + (row.paymoney);
            }
        },
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        }]
    });
}

function setTableHeight(){//设置历史费用中两个table的高度
	var tableHeight=$(window).outerHeight()-$(".costHd2").outerHeight()-170;
	$("#tablefyxq").bootstrapTable("resetView",{
		height:tableHeight/2
	});
	$("#dykdxm").bootstrapTable("resetView",{
		height:tableHeight/2
	});
}