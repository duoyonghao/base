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
        if (maxDiscount > discount) {
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
    if (judgeSign($("#paymoney" + index).val()) == true) { // 缴费金额合法性校验
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
        
        if (voidmoney > Number(num * unitprice * discount / 100)) {
            layer.alert('免除金额不能大于缴费金额'  );
            oldValue = $("#voidmoney" + index).attr("oldvalue");
            $("#voidmoney" + index).val(oldValue);
            return false;
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
                if ($(this).index() == 10) {
                    //小计
                    totalcost += Number($(this).text());
                } else if ($(this).index() == 13) {
                    //免除
                    voidmoney += Number($(this).find("input").val());
                } else if ($(this).index() == 11) {
                    //欠费金额
                    arrearmoney += Number($(this).find("span").html());
                    if ($(this).find("span").attr("isqfreal") != "1") {
                        arrearhtml += Number($(this).find("span").html());
                    }
                } else if ($(this).index() == 12) {
                    //缴费金额
                    paymoney += Number($(this).find("input").val());
                    if ($(this).find("input").attr("isqfreal") != "1") {
                        payhtml += Number($(this).find("input").val());
                    }
                } else if ($(this).index() == 15) {
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
	
    var i = tr.parentNode.parentNode.rowIndex;
    document.getElementById('table').deleteRow(i);
    if(id != "" && id != null){
    	delete_item += id +",";
    }
    //刷新金额
    getAlljs();
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
            dataFilter: projectDataFilter,
            type: "get"
        },
    };
    setting['callback'] = {
        onClick: onclick
    };
    $.fn.zTree.init($("#treeDemo"), setting);

}
/*可开发项目返回数据过滤器*/
function projectDataFilter(treeId, parentNode, responseData){
	var dataFilterAlready=responseData.retData;
	dataFilterAlready.splice(4, 5);
	dataFilterAlready.splice(5, 2);
	return dataFilterAlready;
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
            dataFilter: spaceDataFilter,
            type: "get"
        }
    };
    setting['callback'] = {
        onClick: onClickTc
    };
    $.fn.zTree.init($("#treeDemo"), setting);
}
/*可开发空间返回数据过滤器*/
function spaceDataFilter(treeId, parentNode, responseData){
	var dataFilterAlready=responseData.retData;
	dataFilterAlready.splice(1, 1);
	dataFilterAlready.splice(2, 3);
	return dataFilterAlready;
}
//新增项目
function onclick(e, treeId, treeNode) {
	//如果已经添加此套餐，则数量加1 2019/8/29 lutian
	var projectAlreadyIndex;
	$("#costitemlistBody").find("tr").each(function(i,ele){
		if(treeNode.id==$(ele).attr("tcnameid")){
			var currentCount=$(ele).find("td").eq("8").find("input").val();//当前数量
			$(ele).find("td").eq("8").find("input").val(parseInt(currentCount)+1);//数量
			var addAlreadyCount=$(ele).find("td").eq("8").find("input").val();//添加后数量
			var currentPrice=$(ele).find("td").eq("7").find("input").val();//单价
			var discount=$(ele).find("td").eq("9").find("input").val();//折扣
			$(ele).find("td").eq("10").find("span").text(parseInt(currentPrice)*parseInt(addAlreadyCount)*parseInt(discount*0.01));//小计
			var addAlreadySum=$(ele).find("td").eq("10").find("span").text();//添加数量后的小计
			$(ele).find("td").eq("12").find("input").val(addAlreadySum);//缴费金额
			projectAlreadyIndex=i;
		}
	});
	if(projectAlreadyIndex>=0){
		return;
	}
	
	var nodename=treeNode.getParentNode().name;
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
                var tabledata = data.data;
                if ($("#table tr").length > 1 && tabledata.isyjjitem == 1) {
                	layer.alert('存在其他收费项目，不允许添加预交金项目！', {
                          
                    });
                	return false;
            	}
                // 如果明细中 包括预交金 则不能 开其他收费项目
                if(tabledata.isyjjitem == 1){
                	// static_yjjiem = 1;
                	$("#doctor").val("");
                    $("#nurse").val("");
                    $("#techperson").val("");
                    $("#doctorDesc").val("");
                }
                if(tabledata.isyjjitem != 2){
                	// static_ghiem = 0;
                }
                $("#xmjs").val(tabledata.xmjs);
                $("#yhxx").val(tabledata.yhxx);
                var subtotal = tabledata.unitprice * tabledata.discount / 100;
                tablehtml += "<tr style='' onclick='getItemInfo(\""+treeNode.id+"\")' tcnameid="+ tabledata.treatitemno +">";
                //删除按钮0
                tablehtml += '<td style=""><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this,0,\'\')"><span style="color:red;">删除</span></a></td>';
                //项目编号1
                tablehtml += '<td style="display:none;" >' + treeNode.id + '</td>';
                //开发/正常2
                tablehtml += '<td style=""><select id="kaifasel' + tdindex + '" style="width:60px;" onchange="changekaifa(\'' + tdindex + '\',\'kaifasel\')"><option value="正常" selected>正常</option></select></td>';
                //特殊项目3
                var tsxm = tabledata.istsxm == 0 ? "否": "是";
                tablehtml += '<td style="">' + tsxm + '</td>';
            	//治疗项目4
    	        tablehtml += '<td style="display:none;"><span>treatitemname</span></td>';
                //治疗项目5
                tablehtml += '<td style=""><span style="float:left; text-align:left;" class="time" title=\''+tabledata.treatitemname+'\'>' + tabledata.treatitemname + '</span></td>';
                //单位6
                tablehtml += '<td style="">' + tabledata.unit + '</td>';
                //单价7
                if (tabledata.isyjjitem == 3) {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  onchange="editunitprice(\'' + tdindex + '\',\'unitprice\')"  id="unitprice' + tdindex + '" value="' + tabledata.unitprice + '" oldvalue="' + tabledata.unitprice + '"></td>';
                } else {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  readonly="readonly" onchange="edithz(\'' + tdindex + '\',\'unitprice\')"  id="unitprice' + tdindex + '" value="' + tabledata.unitprice + '" oldvalue="' + tabledata.unitprice + '"></td>';
                }

                //数量8
                if (tabledata.isyjjitem == 3 || tabledata.isyjjitem == 1) {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:60px;text-align:center;"   readonly="readonly"  id="num' + tdindex + '" value="1" oldvalue="1"></td>';
                } else {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:60px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'num\')"  id="num' + tdindex + '" value="1" oldvalue="1"></td>';

                }
                //折扣9
                if (tabledata.isyjjitem == 3 || tabledata.isyjjitem == 1) {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:75px; text-align:center;"  readonly="readonly" id="discount' + tdindex + '" value="' + tabledata.discount + '"></td>';
                } else {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:75px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'discount\')"  id="discount' + tdindex + '" value="' + tabledata.discount + '" oldvalue="' + tabledata.discount + '"></td>';

                }
                //小计10
                tablehtml += '<td style=""><span  id="subtotal' + tdindex + '">' + subtotal + '</span></td>';
                //欠费金额11
                tablehtml += '<td style=""><span  id="arrearmoney' + tdindex + '">0</span></td>';
                //缴费金额12
                if (tabledata.isyjjitem == 3) {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" readonly="readonly"  id="paymoney' + tdindex + '" value="' + tabledata.unitprice + '" oldvalue="' + tabledata.unitprice + '"></td>';
                } else if (tabledata.isyjjitem == 1) {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:90px;text-align:center;" onfocus="this.select()"  onchange="yjjcz(\'' + tdindex + '\',\'paymoney\')"  id="paymoney' + tdindex + '" value="0" oldvalue="0"></td>';
                } else {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:90px;text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'paymoney\')"  id="paymoney' + tdindex + '" value="' + subtotal + '" oldvalue="' + subtotal + '"></td>';
                }
                //免除13
                if (tabledata.isyjjitem == 3 || tabledata.isyjjitem == 1) {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" readonly="readonly"  id="voidmoney' + tdindex + '" value="0" oldvalue="0"></td>';
                } else {
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'voidmoney\')"  id="voidmoney' + tdindex + '" value="0" oldvalue="0"></td>';
                }
                //节点名称14
    	        tablehtml += '<td style="display:none;"><span  id="nodename' + tdindex + '">' + nodename + '</span></td>';
                //折扣额15
                tablehtml += '<td style="display:none;"><span  id="zke' + tdindex + '">0</span></td>';
                //ID16
                tablehtml += '<td style="display:none;"></td>';
                //欠费ID17
                tablehtml += '<td style="display:none;"></td>';
                //setIsqfreal 18
                tablehtml += '<td style="display:none;"></td>';
                //欠费编号19
                tablehtml += '<td style="display:none;"></td>';
                //预交金开单
                tablehtml += '<td style="display:none;">' + tabledata.isyjjitem + '</td>';
                tablehtml += "</tr>";

                $("#table").find('tbody').append(tablehtml);
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
//新增套餐项目
function onClickTc(e, treeId, treeNode) {
	//如果已经添加此套餐，则数量加1 2019/8/29 lutian
	var spaceAlreadyIndex;
	$("#costitemlistBody").find("tr").each(function(i,ele){
		if(treeNode.id==$(ele).attr("tcnameid")){
			var currentCount=$(ele).find("td").eq("8").find("input").val();//当前数量
			$(ele).find("td").eq("8").find("input").val(parseInt(currentCount)+1);//数量
			var addAlreadyCount=$(ele).find("td").eq("8").find("input").val();//添加后数量
			var currentPrice=$(ele).find("td").eq("7").find("input").val();//单价
			var discount=$(ele).find("td").eq("9").find("input").val();//折扣
			$(ele).find("td").eq("10").find("span").text(parseInt(currentPrice)*parseInt(addAlreadyCount)*parseInt(discount*0.01));//小计
			var addAlreadySum=$(ele).find("td").eq("10").find("span").text();//添加数量后的小计
			$(ele).find("td").eq("12").find("input").val(addAlreadySum);//缴费金额
			spaceAlreadyIndex=i;
		}
	});
	if(spaceAlreadyIndex>=0){
		return;
	}

	var nodename=treeNode.getParentNode().name;
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
                    var tabledata = allTableData[i];
                    if(tabledata.isyjjitem != 2){
                    	// static_ghiem = 0;
                    }
                    tablehtml += "<tr style='' onclick='getItemInfo(\""+tabledata.itemno+"\")' tcnameid="+ tabledata.tcnameid +">";
                    //删除按钮0
                    tablehtml += '<td style=""><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this,0,\'\')"><span style="color:red;">删除</span></a></td>';
                    //项目编号1
                    tablehtml += '<td style="display:none;" >' + tabledata.itemno + '</td>';
                    //开发/正常2
                    tablehtml += '<td style=""><select id="kaifasel' + tdindex + '" style="width:60px;" onchange="changekaifa(\'' + tdindex + '\',\'kaifasel\')"><option value="正常" selected>正常</option></select></td>';
                    //特殊项目3
                    var tsxm = tabledata.istsxm == 0 ? "否": "是";
                    tablehtml += '<td style="">' + tsxm + '</td>';
                    //治疗项目4
        	        tablehtml += '<td style="display:none;"><span >itemname</span></td>';
                    //治疗项目5
                    tablehtml += '<td style="text-align:left;"><span style="float:left; text-align:left;" class="time" title=\''+tabledata.itemname+'\'>' + tabledata.itemname + '</span></td>';
                    //单位6
                    tablehtml += '<td style="">' + tabledata.unit + '</td>';
                    //单价7
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  readonly="readonly"  id="unitprice' + tdindex + '" value="' + tabledata.unitprice + '" oldvalue="' + tabledata.unitprice + '"></td>';
                    //数量8
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:60px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'num\')"  id="num' + tdindex + '" value="' + tabledata.num + '" oldvalue="' + tabledata.num + '"></td>';
                    //折扣9
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:75px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'discount\')"  id="discount' + tdindex + '" value="' + tabledata.discount + '" oldvalue="' + tabledata.discount + '"></td>';
                    //小计10
                    tablehtml += '<td style=""><span  id="subtotal' + tdindex + '">' + tabledata.subtotal + '</span></td>';
                    //欠费金额11
                    tablehtml += '<td style=""><span  id="arrearmoney' + tdindex + '">' + tabledata.arrearmoney + '</span></td>';
                    //缴费金额12
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'paymoney\')"  id="paymoney' + tdindex + '" value="' + tabledata.paymoney + '" oldvalue="' + tabledata.paymoney + '"></td>';
                    //免除13
                    tablehtml += '<td style=""><input  type="text" min="1" style="width:90px;text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'voidmoney\')"  id="voidmoney' + tdindex + '" value="' + tabledata.voidmoney + '" oldvalue="' + tabledata.voidmoney + '"></td>';
                    //节点名称14
        	        tablehtml += '<td style="display:none;"><span  id="nodename' + tdindex + '">' +nodename + '</span></td>';
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
                    tablehtml += '<td style="display:none;">' + tabledata.isyjjitem + '</td>';
                    tablehtml += "</tr>";

                    $("#table").find('tbody').append(tablehtml);
                   
                   
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
function changekaifa(index, field) { /** 开发时，将数量、缴费金额、小计置0 **/
    var kaifa = $("#" + field + index + " option:selected").val();
    if (kaifa == "开发") {
        $("#num" + index).val("0");
        $("#paymoney" + index).val("0");
        $("#subtotal" + index).html("0");
    }
    //刷新金额
    getAlljs();
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