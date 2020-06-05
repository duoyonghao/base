var clock = '';
var nums = 60;
var btn;
function Yztable_net() {
    $('#defaultForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	dwmc: {
            	message: '单位名称验证失败',
                validators: {
                    notEmpty: {
                        message: '单位名称不能为空'
                    }
                }
            },
            lxr: {
                validators: {
                    notEmpty: {
                        message: '联系人不能为空'
                    }
                }
            },
            sjhm: {
                validators: {
                    notEmpty: {
                        message: '手机号码不能为空'
                    },
                    regexp: {
                        regexp: /^1[3|5|8]{1}[0-9]{9}$/,
                        message: '请输入正确的手机号码'
                    },
                    remote: {
                        message: '该手机号码已注册',
                        url: contextPath + '/YZRegisterAct/checkphonenumber.act',
                        type: 'POST',
                        delay :  2000,
                        //请求方式
                        data: function(validator) {
                            return {
                            	sjhm: $('[name="sjhm"]').val()
                            };
                        }
                    }
                }
            },
            receivecode: {
                validators: {
                }
            },
            code: {
                validators: {
                    notEmpty: {
                        message: '验证码不能为空'
                    },
                    identical: {
                    	field: 'receivecode',
                    	message: '验证码错误!'
                    }
                }
            }
        }
    });
}
function submit() {
    var returnbool = true;
    var data = $('#defaultForm').data('bootstrapValidator');
    if (data) {
        // 修复记忆的组件不验
        data.validate(); // 手动对表单进行校验，经过测试，不写此方法，直接执行isValid()方法也可行 
        if (!data.isValid()) {
            returnbool = false;
        }
    }
    return returnbool;
}
function YZsjhm(){
	var sjhm = $("#sjhm").val();
	var data = $('#defaultForm').data('bootstrapValidator');
	data.validateField("sjhm");
	var flag = true;
	if(sjhm == null || sjhm == ""){
		flag = false;
	}else{
		flag = (/^1[3|5|8]{1}[0-9]{9}$/.test(sjhm));
	}
	return flag;
}
$('#getcode').on('click', function(){
	if(!YZsjhm()){
		return false;
	}
	getcode();
	btn = $(this)[0];
	btn.disabled = true; //将按钮置为不可点击
	btn.innerHTML = nums+'秒后可重新获取';
	clock = setInterval(doLoop, 1000); //一秒执行一次
	
});
function doLoop(){
	nums--;
	if(nums > 0){
		btn.innerHTML = nums+'秒后可重新获取';
	}else{
		 clearInterval(clock); //清除js定时器
		 btn.disabled = false;
		 btn.innerHTML = '获取验证码';
		 nums = 60; //重置时间
	}
}
function getcode(){
	var url = "YZRegisterAct/getSenderCode.act?sjhm=" + $("#sjhm").val();
	var rtData = getDataFromServer(url);
	if(rtData.retState == 0){
		$("#receivecode").val(rtData.data);
	}
}