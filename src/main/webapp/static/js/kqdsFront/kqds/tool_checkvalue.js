//验证输入项是否为正数、负数
function judgeSignNum(num) {
    var reg = /^^([1-9][0-9]*|-[1-9][0-9]*)$/;
    if (reg.test(num)) {
        return true;
    } else {
        return false;
    }
}
//验证输入项是否为正数
function judgeSign(num) {
    var reg = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
    if (reg.test(num)) {
        return true;
    } else {
        return false;
    }
}
//检验金额包括正，负，小数
function judgeSignMoney(num) {
    var reg = /^(\-)?\d+(\.\d{1,2})?$/;
    if (reg.test(num)) {
        return true;
    } else {
        return false;
    }
}
//验证输入项是否为正整数
function judgeSignZZ(num) {
    var reg = /^[0-9]*[1-9][0-9]*$/;
    if (reg.test(num)) {
        return true;
    } else {
        return false;
    }
}
//验证输入项是否为非负浮点数
function judgeSignFloat(num) {
    var reg = /^\\d+(\\.\\d+)?$/;
    if (reg.test(num)) {
        return true;
    } else {
        return false;
    }
}
//验证输入项是否为非负浮点数
function judgeSignFloatNum(num) {
    var reg = /^(-?\d+)(\.\d+)?$/;
    if (reg.test(num)) {
        return true;
    } else {
        return false;
    }
}
function validatemobile(phonestr){ 
	var mobile = phonestr.replace(/[^0-9]/ig,"");
    if(mobile.length==0) 
    { 
       document.form1.mobile.focus(); 
       return false; 
    }     
    if(mobile.length!=11) 
    { 
        document.form1.mobile.focus(); 
        return false; 
    } 
     
    var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|17[0-9]{1}|(18[0-9]{1}))+\d{8})$/; 
    if(!myreg.test(mobile)) 
    { 
        document.form1.mobile.focus(); 
        return false; 
    } 
    return true;
} 