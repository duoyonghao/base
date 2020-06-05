/**
 * @param addrowname
 * @param selectObj
 * @param detailFlag 有值，就表示是详情页面
 */
function addRow(addrowname, selectObj, detailFlag) {

    var trHTML = '<tr>';
    /*可增加行you can add lines*/
    trHTML += '<td rowspan="2" style="width:30%;"></td>';
    trHTML += '<td><input type="text"  name="' + addrowname + '"></td>';
    trHTML += '<td><input type="text"  name="' + addrowname + '"></td>';
    trHTML += '<td rowspan="2" colspan="4" >';
    trHTML += '<textarea rows="3"  name="' + addrowname + '"></textarea>';
    trHTML += '</td>';
    trHTML += '<td rowspan="2">';
    if (!detailFlag) { // 如果不存在此参数，则表示该页面是编辑或新增页面
        trHTML += '<input type="button" value="增加行" onclick="addRow(\'' + addrowname + '\',this);"> <!-- 种植1期病历 -->';
        trHTML += '<input type="button" value="删除行" onclick="delRow(this);"> <!-- 种植1期病历 -->';
    }
    trHTML += '</td>';
    trHTML += '</tr>';
    trHTML += '<tr>';
    trHTML += '<td><input type="text"  name="' + addrowname + '"></td>';
    trHTML += '<td><input type="text"  name="' + addrowname + '"></td>';
    trHTML += '</tr>';

    $(selectObj).parent().parent().next().after(trHTML);

}
function delRow(selectObj) {
    $(selectObj).parent().parent().next().remove();
    $(selectObj).parent().parent().remove();
}
function initBaseInfo() {
    $("#username").html(static_userobj.username);
    $("#sex").html(static_userobj.sex);
    $("#age").html(static_userobj.age);
    $("#address").html(static_userobj.address);
    $("#telephone").html(static_userobj.phonenumber1);
    $("#email").html(static_userobj.email);
    // $("#allergy").html(static_userobj.drugllergy);
    $("#meid").html(static_userobj.usercode);
    
}

function getValue(name) {
    var value = "";
    //取得条数
    var length = $("input[name*='" + name + "']").length / 4;

    var count = 0;
    var count2 = 0;
    $("input[name*='" + name + "']").each(function() {
        var tmpName = $(this).attr("name");
        count++;

        if (count % 4 == 0) { // 这里之所以是4 因为 类型为input ，不包括textarea
            value += $(this).val() + "|| ";
            count2++;
            $("textarea[name='" + tmpName + "']").each(function(i) { // 该值正常只有一个
                if ((i + 1) == count2) {
                    value += $(this).val() + "|||";
                }
            });
        } else {
            value += $(this).val() + ";";
        }
    });

    return value;
}

/**
 * 恢复病历#############################################################
 */

/**
 * detailFlag 有值就是代表详情页面
 */
function dealMultiLines(fieldname, fieldvalue, detailFlag) {

    var firstFieldObj = $("input[name*='" + fieldname + "']")[0];

    var firstTrObj = $(firstFieldObj).parent().parent();

    var valArray = fieldvalue.split("|||");

    // 获取有效行数
    var realcount = 0;
    for (var i = 0; i < valArray.length; i++) {
        if (valArray[i].length == 0) {
            continue;
        }
        realcount++;
    }

    for (var i = 0; i < realcount - 1; i++) { // 第一行已经存在了，所以减掉
        addRow(fieldname + '_tmp', firstFieldObj, detailFlag);
    }

    var valArrayTmp = new Array();
    for (var i = 0; i < valArray.length; i++) {
        if (valArray[i].length == 0) {
            continue;
        }
        var arrone = valArray[i].split("||"); // 1;2;4;5|| 3|||6;7;8;9|| 10|||
        var ywarr = arrone[0].split(";");
        var content = arrone[1]; // textarea内容
        if (content == undefined) {
            content = "";
        }
        if (ywarr[0] == undefined) {
            ywarr[0] = "";
        }
        if (ywarr[1] == undefined) {
            ywarr[1] = "";
        }
        if (ywarr[2] == undefined) {
            ywarr[2] = "";
        }
        if (ywarr[3] == undefined) {
            ywarr[3] = "";
        }

        valArrayTmp.push(ywarr[0], ywarr[1], content, ywarr[2], ywarr[3]);
    }

    // 赋值
    $("[name*='" + fieldname + "']").each(function(index) {
        $(this).val(valArrayTmp[index]);

    });
}

/**#####################################################################**/

function save(url) {
    var msg;
    $.axseSubmit(url, null,
    function() {
        msg = layer.msg('加载中', {
            icon: 16
        });
    },
    function(r) {
        layer.close(msg);
        if (r.retState == "0") {
            layer.alert('保存成功'  ,
            function(index) {
            	try{
            		parent.refresh();
                    parent.layer.close(frameindex); //再执行关闭 */
	  	    	}catch(e){
	  	    		refresh();
	  	    		layer.closeAll();
	  	    	}
               
            });
        } else {
            layer.alert('保存成功'  );
        }
    },
    function() {
        layer.alert('保存成功' );
    });
}

// 详情页，关闭
function closeWin() {
    parent.refresh();
    parent.layer.close(frameindex); //再执行关闭 */
}

/*********************************病历库选取********************************************/
function blk_select(mtype) {
	var url = contextPath + '/KQDS_BLKAct/toBlkSelect.act?1=1';
	if(mtype){
		url += '&mtype='+mtype;
	}
	var titleStr = "";
	if(mtype == 2){
		titleStr = "【种植一期】";
	}
	if(mtype == 3){
		titleStr = "【种植复查】";
	}
	if(mtype == 4){
		titleStr = "【种植二期】";
	}
	if(mtype == 5){
		titleStr = "【种植修复】";
	}
	
	titleStr += "病历库";
	
    layer.open({
        type: 2,
        title: titleStr,
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        zIndex: 200,
        area: ['90%', '90%'],
        content: url
    });
}

/*********************************添加病历库********************************************/
function addblk(meid,mtype,type) {
    layer.prompt({
        title: '输入病历名称，并确认',
        formType: 0
    },
    function(pass, index) {
        layer.close(index);
        var param = 'type='+type+'&meid='+meid+'&blname='+pass;
        var url = "";
        if(mtype == 2){
        	url = contextPath + '/KQDS_BLK_ZhongzhiAct/insertOrUpdate.act?' + param;
    	}
    	if(mtype == 3){
    		url = contextPath + '/KQDS_BLK_ChaiXianAct/insertOrUpdate.act?' + param;
    	}
    	if(mtype == 4){
    		url = contextPath + '/KQDS_BLK_Zhongzhi2Act/insertOrUpdate.act?' + param;
    	}
    	if(mtype == 5){
    		url = contextPath + '/KQDS_BLK_XiuFuAct/insertOrUpdate.act?' + param;
    	}
        save(url);
    });
}