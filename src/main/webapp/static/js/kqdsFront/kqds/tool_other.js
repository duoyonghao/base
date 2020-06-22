
function JsontoUrldata(param, key) {
    var paramStr = "";
    if (param instanceof String || param instanceof Number || param instanceof Boolean) {
        paramStr += "&" + key + "=" + encodeURIComponent(param);
    } else {
        var keyArray = objOfPropertyToArr(param);

        var valueArray = objOfValueToArr(param);

        for (var i = 0; i < keyArray.length; i++) {
            if (valueArray[i] == null || valueArray[i] == 'null') {
                valueArray[i] = '';
            }
            paramStr += '&' + keyArray[i] + '=' + valueArray[i];
        }
    }
    return paramStr.substr(1);
};

//将Object的属性输出成Array
function objOfPropertyToArr(object) {
    var arr = [];
    var i = 0;
    for (var item in object) {
        arr[i] = item;
        i++;
    }
    return arr;
}

//将Object的属性值输出成Array
function objOfValueToArr(object) {
    var arr = [];
    var i = 0;
    for (var item in object) {
        arr[i] = object[item];
        i++;
    }
    return arr;
}


/**
 * 判断参数1 是否在 参数2的数组中
 * @param str
 * @param array
 * @returns {Boolean}
 */
function isStrInArray(str, array) {
    var flag = false;

    if (array == null) {
        return false;
    }

    if (array.length == 0) {
        return false;
    }

    for (var i = 0; i < array.length; i++)

    if (str == array[i]) {
        flag = true;
        break;
    }

    return flag;
}

/**
 * 判断参数1是否在 参数2的字符串数组中
 * @param str
 * @param arrayStr
 */
function isStrInArrayString(str, arrayStr) {

    if (str == null || str == '') {
        return false;
    }

    if (arrayStr == null || arrayStr == '') {
        return false;
    }

    var filterArray = arrayStr.split(",");

    var flag = isStrInArray(str, filterArray);

    return flag;
}

/**
 * 判断参数1是否在 参数2的字符串数组中
 * @param str
 * @param arrayStr
 */
function isStrInArrayStringEach(Str1, Str2) {

    if (Str1 == null || Str1 == '') {
        return false;
    }

    if (Str2 == null || Str2 == '') {
        return false;
    }

    var Str1Array = Str1.split(",");
    
    var Str2Array = Str2.split(",");
    var flag = false;
    for(var i=0;i<Str1Array.length;i++){
    	 flag = isStrInArray(Str1Array[i], Str2Array);
    	 if(flag){
    		 break;
    	 }
    }
    return flag;
}
//日期保留到天
function getDatetimeToDay(value){
	return value.substring(0,10);
}
//日期保留到分
function getDatetimeToMinutes(value){
	return value.substring(0,16);
}


