//单个选中赋值
function setSelectVal(row) {
	// 取父页面的值
    static_select_userid_val = static_single_select_userObj.val(); // id
    static_select_username_val = static_single_select_userDescObj.val(); // name
    
    if(static_select_userid_val.length > 0){
    	 static_select_userid_val = static_select_userid_val + ","+row.usercode;
    	 static_select_username_val = static_select_username_val + ","+row.username;
    }else{
    	 static_select_userid_val = row.usercode;
    	 static_select_username_val = row.username;
    }
    
	static_single_select_userObj.val(static_select_userid_val);
	static_single_select_userDescObj.val(static_select_username_val);
	layer.alert('患者与活动关联成功'  );
}
//多个选中赋值
function setSelectAllVal(rows) {
	for(var i=0;i<rows.length;i++){
		setSelectVal(rows[i]);
	}
}
//单个取消选中赋值
function setNoSelectVal(row) {
	// 取父页面的值
    static_select_userid_val = static_single_select_userObj.val(); // id
    static_select_username_val = static_single_select_userDescObj.val(); // name
    
    if(static_select_userid_val.length > 0){
    	var idarr = static_select_userid_val.split(",");
    	var namearr = static_select_username_val.split(",");
    	for(var i=0;i<idarr.length;i++){
    		if(row.usercode == idarr[i]){
    			idarr.removeByValue(idarr[i]);
    			namearr.removeByValue(namearr[i]);
    		}
    	}
    	static_select_userid_val =  idarr.join(",");
    	static_select_username_val = namearr.join(",");
    }
    
	static_single_select_userObj.val(static_select_userid_val);
	static_single_select_userDescObj.val(static_select_username_val);
	layer.alert('患者与活动关联取消'  );
}
//多个取消选中赋值
function setNoSelectAllVal(rows) {
	for(var i=0;i<rows.length;i++){
		setNoSelectVal(rows[i]);
	}
}

Array.prototype.removeByValue = function(val) {
	for(var i=0; i<this.length; i++) {
	    if(this[i] == val) {
	      this.splice(i, 1);
	      break;
	    }
	}
}



//获取选中行的usercode
function getIdSelections() {
  return $.map($("#table").bootstrapTable('getSelections'),
  function(row) {
      return row;
  });
}
//复选框
function stateFormatter(value, row, index) {
	// 取父页面的值
  static_select_userid_val = static_single_select_userObj.val(); // id
  static_select_username_val = static_single_select_userDescObj.val(); // name
  
  if(static_select_userid_val.length > 0){
  	var idarr = static_select_userid_val.split(",");
  	var new_userid_val = "",new_username_val = "";
  	for(var i=0;i<idarr.length;i++){
  		if(row.usercode == idarr[i]){
  			return {
  	            disabled: false,
  	            checked: true
  	        };
  		}
  	}
  	static_select_userid_val = new_userid_val;
  	static_select_username_val = new_username_val;
  }
  return row;
}