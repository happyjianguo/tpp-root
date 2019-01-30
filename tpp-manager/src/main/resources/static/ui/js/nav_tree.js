$(function() {
	var content;
    $.ajax({
        async:false,
        type:"post",
        dataType:"json",
        url:"getMenu",
        success:function(msg){
            content=msg;
        }
    });
//添加链接    显示地方
	var data = {
		ele: 'Ui_treeNav',
		showBox:'contentBody',
		itemColor:'#fff',
		childColor:'#fff',
		fontColor:'#7F8FA4',
		logo:'ui/images/logo.png',
		title:'渠道代理平台',
		bodyArr: content
	}
	//	data说明:暂时只支持二级菜单
	//  ele:页面元素的ID属性。导航将添加到该元素下，同时规定导航的位置，宽度和高度等页面信息(必选，样式需在css文件中进行设置)
	//	itemColor:一级菜单的背景颜色(非必选)
	//  childColor: 二级菜单的背景颜色(非必选)
	//	fontColor:字体颜色(非必选)
	//	bodyArr:是一个对象数组，包括一级菜单和二级菜单的相关内容，每一个对象包括：(必选)
	//  text：一级菜单的标题(必选)
	//  childArr : 二级菜单数组，每个对象包括二级菜单的标题(必选)
	//  imgUrl: 二级菜单的图标地址(非必选，但当没有图标时，隐藏菜单列表后不会创建对应得图标菜单)
	var nav = new UInav();

	nav.tree_nav(data, function(res) {
		console.log(res)
		if(!res.nav_show){
			$('aside').attr('style','width: 45px !important;');
			$('.layui-tab-title,.layui-tab-content').attr('style','margin-left: 46px;')
		}else{
			$('aside').attr('style','width: 230px !important;');
			$('.layui-tab-title,.layui-tab-content').attr('style','margin-left: 230px;')
		}
	})
})