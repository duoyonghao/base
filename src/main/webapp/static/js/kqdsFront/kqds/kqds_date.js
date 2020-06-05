
/*#####################################日期、时间相关代码 start  // yangsen 17-5-4 ########################################*/

/*	对Date的扩展，将 Date 转化为指定格式的String
 *	月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
 *	年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
 *	例子： 
 *	(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
 *	(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
 **/
Date.prototype.Format = function(fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1,
        //月份 
        "d+": this.getDate(),
        //日 
        "h+": this.getHours(),
        //小时 
        "m+": this.getMinutes(),
        //分 
        "s+": this.getSeconds(),
        //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3),
        //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

//获取当前时间yyyy-MM-dd
function getNowFormatDate(dateParam) {
    var date = null;
    if(dateParam){
    	date = dateParam;
    }else{
    	date = new Date();
    }
    var seperator1 = "-";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
    return currentdate;
}

function getNowFormatDateHours() {
    var date = new Date();
    var seperator1 = "-";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var strHours = date.getHours();
    var strMinutes = date.getMinutes();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (strHours >= 0 && strHours <= 9) {
        strHours = "0" + strHours;
    }
    if (strMinutes >= 0 && strMinutes <= 9) {
        strMinutes = "0" + strMinutes;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate + " " + strHours + ":" + strMinutes;
    return currentdate;
}

function getNowFormatTime() {
    var date = new Date();
    var seperator1 = "-";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var strHours = date.getHours();
    var strMinutes = date.getMinutes();
    var second = date.getSeconds();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (strHours >= 0 && strHours <= 9) {
        strHours = "0" + strHours;
    }
    if (strMinutes >= 0 && strMinutes <= 9) {
        strMinutes = "0" + strMinutes;
    }
    if (second >= 0 && second <= 9) {
    	second = "0" + second;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate + " " + strHours + ":" + strMinutes+ ":" + second;
    return currentdate;
}




//当前日期yyyy-MM
function getNowMonth() {
    var date = new Date();
    var seperator1 = "-";
    var month = date.getMonth() + 1;
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    var currentdate = date.getFullYear() + seperator1 + month;
    return currentdate;
}

/**
 * 当前日期yyyy-MM-DD
 */
function getNowDay(time) {
    var myDate;
    if (time) {
        myDate = new Date(time);
    } else {
        myDate = new Date();
    }
    // 获取当前年
    var year = myDate.getFullYear();
    // 获取当前月
    var month = myDate.getMonth() + 1;
    // 获取当前日
    var date = myDate.getDate();
    return year + '-' + format10(month) + "-" + format10(date);
}

/**
 * 小于10 补零
 * @param s
 * @returns
 */
function format10(s) {
    return s < 10 ? '0' + s: s;
}



//绑定两个时间选择框的chage时间
$("#starttime,#endtime").change(function() {
    timeCompartAndFz("starttime", "endtime");
});

//比较起始和结束时间，如果结束时间大于起始时间，结束时间重置为开始时间
function timeCompartAndFz(startTimeId, endTimeId) {
    var startTimeOBJ = $("#" + startTimeId);
    var endTimeOBJ = $("#" + endTimeId);
    var startTime = new Date(startTimeOBJ.val());
    var endTime = new Date(endTimeOBJ.val());
    if (startTimeOBJ.val() && endTimeOBJ.val()) {
        if (startTime.getTime() > endTime.getTime()) {
            layer.msg('您选择的结束时间小于起始时间,结束时间将重置为起始时间', {
                  
                time: 2000 //2秒关闭（如果不配置，默认是3秒）
            },
            function() {
                endTimeOBJ.val(startTimeOBJ.val());
            });
        }
    }
}


// 比较两个日期大小
function checkdate(date1, date2) {
    var num = 0;
    var oDate1 = new Date(date1);
    var oDate2 = new Date(date2);
    if (oDate1.getTime() > oDate2.getTime()) { // 时间1 大于 时间 2
        num = 0;
    } else if (oDate1.getTime() == oDate2.getTime()) { // 时间1 等于 时间2
        num = 2;
    } else {
        num = 1; // 时间1 小于 时间2
    }
    return num;
}


//目的是跟一个字符串比如 2014-7-8 2014-07-08做比较是否是同一天
function isToday(str){
	if(str!=null && str !=""){
		str = str.substring(0,10);
	}
  var d = new Date(str);
  if(d.toLocaleDateString() == new Date().toLocaleDateString()){
      return true;
  } else {
      return false;
  }
}

/*#####################################日期、时间相关代码 end  // yangsen 17-5-4 ########################################*/
