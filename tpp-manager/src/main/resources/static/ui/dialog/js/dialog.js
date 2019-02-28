

$.extend({
	dialog:function(params){
		if(!params){
			return
		}
//		背景
		var bg = $('<div>').addClass('alertBox').appendTo($('body'));
		var box = $('<div>').addClass('alert').addClass(params.position?params.position:'center').appendTo(bg);
		var title = $('<div>').html(params.title?params.title:'提示信息').addClass(params.type?params.type:'success').addClass('alert_title iconFont').appendTo(box);
		$('<span>').addClass('cancelBtn').appendTo(title);
		var msg = $('<p>').addClass('alert_msg').html(params.msg?params.msg:'提示信息').appendTo(box);
		var btnBox = $('<div>').addClass('btsBox').appendTo(box);
		$('<div>').addClass('btns').html(params.buttons?params.buttons[0]:'取消').appendTo(btnBox);
		$('<div>').addClass('btns').html(params.buttons?params.buttons[1]:'确定').appendTo(btnBox);
		
//		点击'×',关闭弹窗,不进行操作
		$('.cancelBtn').click(function(){
			$('.alertBox').remove();
		})
		
//		点击按钮,关闭弹窗,执行回调函数
		var btn1 = $('.btns')[0];
		var btn2 = $('.btns')[1];
		$(btn1).click(function(){
			$('.alertBox').remove();
			if(params.btn1Cb) params.btn1Cb();
		})
		
		$(btn2).click(function(){
			$('.alertBox').remove();
			if(params.btn2Cb) params.btn2Cb();
		})
		
	},
	
	
});
