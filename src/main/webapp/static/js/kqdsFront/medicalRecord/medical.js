/* 病历内容页掉用---------------- */
window.onload = function() {
    document.onclick = function (e) {
        if(e.target.tagName == 'TEXTAREA' || e.target.getAttribute("type") == 'text') {
        	static_input_obj = $(e.target);
        }
    }
    $('#TeShuZiFuUL li',parent.document).click(function(){
		if(static_input_obj){
			var oldVal = $(static_input_obj).val();
			oldVal += $(this).attr("value");
			$(static_input_obj).val(oldVal);
		}
  	});
}
function setctChild(ctname){
	$(static_input_obj).val($(static_input_obj).val()+ctname);
}
/* 点击行 */ 
function czclick(obj, name) {
    //获取鼠标所在 行号 
    rowno = obj.parent().parent().find("tr").index(obj.parent()[0]);
    addrowname = name;
    rownoCheck = rowno;
}
/* 获取最大的序号，这里指的不是 当前一共几行 ，而是Name的下标*/ 
function getXHByItemName() {
    var currMaxXh = 0;
    $("input[name*='" + addrowname + "']").each(function() {
        var currName = $(this).attr("name");
        var currxh = currName.split("_")[1];
        var xhnum = Number(currxh);
        if (xhnum > currMaxXh) {
            currMaxXh = xhnum;
        }
    });
    return currMaxXh + 1;
}
/* 增加行start */   
$('#addRow',parent.document).click(function(){
 	if (rowno == 0 && rownoCheck == 0) {
        return false;
    }
    if (rowno != rownoCheck) { // 连续点击，使最新的行放到前一个的底下
        rowno = rownoCheck;
    }
    //获取序号
    var xh = getXHByItemName();
    //增加初诊表格行
    var trHTML = '<tr>' + '<th rowspan="2">' + '<td width="20px" rowspan="2" onclick="openSelectTooth(this);" colspan="1"><input type="text" value="' + xh + '、"></td>' + '<td width="90px"><input type="text"  name="' + addrowname + '_' + xh + '" ></td>' + '<td width="90px"><input type="text"  name="' + addrowname + '_' + xh + '" ></td>' + '<td rowspan="2" colspan="1" onclick="czclick($(this),\'' + addrowname + '\');">' + '<textarea name="' + addrowname + '_' + xh + '"></textarea>' + '</td>' + '</tr>' + '<tr>' + '<td><input type="text"  name="' + addrowname + '_' + xh + '" ></td>' + '<td><input type="text"  name="' + addrowname + '_' + xh + '" ></td>' + '</tr>';
    $("#medical tr:eq(" + (rowno + 1) + ")").after(trHTML); // 在table的第rowno行后面添加一行
    rownoCheck = rowno + 2;
});
/* 增加行end */ 

/* 删除行start */   
$('#delRow',parent.document).click(function(){
	//获取序号
    var xh = $("#medical tr:eq(" + (rowno) + ")").find("td").eq(0).find("input").eq(0).val();
    //去除、号
    if (xh.indexOf("、") > 0) {
        xh = xh.substring(0, xh.length - 1);
        if (xh != "1") {
            $('#medical tr:eq(' + rowno + ')').remove();
            $('#medical tr:eq(' + rowno + ')').remove();
        }
    }
    rownoCheck = 0;
    rowno = 0;
});
/* 删除行end */ 

/* 获取病历内容start--保存 */ 
function getValue(name) {
    var value = "";
    //取得条数
    var length = $("input[name*='" + name + "']").length / 4;

    var count = 0;
    $("input[name*='" + name + "']").each(function() {
        var tmpName = $(this).attr("name");
        count++;

        if (count % 4 == 0) { // 这里之所以是4 因为 类型为input ，不包括textarea
            value += $(this).val() + "|| ";
            $("textarea[name='" + tmpName + "']").each(function(i) { // 该值正常只有一个
                value += $(this).val() + "|||";
            });
        } else {
            value += $(this).val() + ";";
        }

    });

    return value;
}
/* 获取病历内容end--保存 */ 

/* 病历库 使用时 调用 start*/ 
function setMedicalcontent(content){
	$("#medical").html(content);
}
/* 病历库 使用时 调用 end*/ 

/* 获取病历内容start--恢复 */ 
//子页面赋值
function getcontent2HuiFu(value, name, id) {
    var blcontent = "";
    if (value == "") {
        //子页面赋值 start
        blcontent += '<tr>' + '<th rowspan="2">' + name + '<br/></th>' + '<td width="30px" rowspan="2" colspan="1" onclick="openSelectTooth(this);"><input type="text" value="1、"></td>' + '<td width="60px"><input type="text"  name="' + id + '_1" value=""></td>' + '<td width="60px"><input type="text"  name="' + id + '_1" value=""></td>' + '<td rowspan="2" colspan="1" onclick="czclick($(this),\'' + id + '\');"><textarea name="' + id + '_1" ></textarea></td>' + '</tr>';
        blcontent += '<tr>' + '<td><input type="text"  name="' + id + '_1" value=""></td>' + '<td><input type="text"  name="' + id + '_1" value=""></td>' + '</tr>';
        //子页面赋值 end			
    } else {
        //1;2;4;3||撒东窗事发大概|||1;2;2;4||打折的v风格|||
        var arr = value.split("|||");
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].length > 0) {
                var arrone = arr[i].split("||");
                var ywarr = arrone[0].split(";");
                var content = arrone[1];
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

                if (i > 0) {
                    name = '';
                }
                //子页面赋值 start
                blcontent += '<tr>' + '<th rowspan="2">' + name + '<br/></th>' + '<td width="30px" rowspan="2" colspan="1" onclick="openSelectTooth(this);"><input type="text" value="' + (i + 1) + '、"></td>' + '<td width="60px"><input type="text"  name="' + id + '_' + (i + 1) + '" value="' + ywarr[0] + '"></td>' + '<td width="60px"><input type="text"  name="' + id + '_' + (i + 1) + '" value="' + ywarr[1] + '"></td>' + '<td rowspan="2" colspan="1" onclick="czclick($(this),\'' + id + '\');"><textarea name="' + id + '_' + (i + 1) + '" >' + content + '</textarea></td>' + '</tr>';
                blcontent += '<tr>' + '<td><input type="text"  name="' + id + '_' + (i + 1) + '" value="' + ywarr[2] + '"></td>' + '<td><input type="text"  name="' + id + '_' + (i + 1) + '" value="' + ywarr[3] + '"></td>' + '</tr>';
                //子页面赋值 end
            }
        }
    }
    return blcontent;
}