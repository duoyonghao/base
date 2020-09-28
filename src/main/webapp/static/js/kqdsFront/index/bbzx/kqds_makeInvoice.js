function makeInvoice(usercodes) {
    //询问框
    layer.confirm('确定开票？', {
        btn: ['确定', '放弃'] //按钮
    },
    function() {
    	var param = {
    			usercodes : usercodes
    	}
        var url = contextPath+'/Kqds_MakeInvoiceAct/makeInvoice.act';
        $.axse(url, param,
        function(data) {
            if (data.rtState == "0") {
                 layer.alert('操作成功', {
                      
                 });
            }else{
            	 layer.alert(data.retMsrg, {
                       
                 });
            }
        },
        function() {
            layer.alert(data.retMsrg  );
            refresh();
        });
    });
}