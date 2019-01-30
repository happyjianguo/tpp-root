layui.use(['laypage', 'layer'], function(){
	  var laypage = layui.laypage
	  ,layer = layui.layer;
	 //完整功能
	  laypage.render({
	    elem: 'laypages'
	    ,count: 50  //总数据条数（其他详细参数见https://www.layui.com/doc/modules/laypage.html#options）
	    ,layout: ['count', 'prev', 'page', 'next', 'limit','skip']
	    ,jump: function(obj){
	      console.log(obj)
	    }
	  }); 
	
	})

$(window).load(function(){
	
	
})

