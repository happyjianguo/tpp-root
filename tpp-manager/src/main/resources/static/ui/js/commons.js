$(function(){
		//	pc时搜索框的高度需要动态设置
//		$('.searchBox').css('height',$('.form_box').height()+10+'px')
	//	pc端点击显示和隐藏，处理奇偶次点击
		var searchBoxIndex = 0;
		$('.glyphicon_pc').click(function(){
			if(searchBoxIndex % 2 === 0){
				$('.searchBox').css('height','auto').css('overflow','auto');
				$('.glyphicon_pc').addClass('searc_pc');
				searchBoxIndex = 1;
			}else{
				$('.searchBox').css('height',$('.form_box').height()+10+'px').css('overflow','hidden');
				$('.glyphicon_pc').removeClass('searc_pc');
				searchBoxIndex = 0;
			}
		})
		
//		计算按钮组合搜索框的高度,动态设置表格的margin-top
		$('#table_box').css('margin-top',$('.searchBox').outerHeight()+$('.buttonBox').outerHeight()+3+'px');
		
	//	移动端搜索框点击 动画
		var mob_searchIndex = 1;
		$('.glyphiconSearch_mob').click(function(){
			if(mob_searchIndex){
				$('.searchBox').addClass('showSearchBoxAni').removeClass('hideSearchBoxAni');
				mob_searchIndex = 0;
			}else{
				$('.searchBox').addClass('hideSearchBoxAni').removeClass('showSearchBoxAni');
				mob_searchIndex = 1;
			}
	
		})
//	菜单显示和隐藏
	$('.asideBtn').click(function(){
			$('aside').addClass('showAsideAni').removeClass('hideAsideAni');
			$('.bg').css('display','block');
	})
	$('.bg').click(function(){
		$('aside').addClass('hideAsideAni').removeClass('showAsideAni');
		$('.bg').css('display','none');
	})
	
	window.onload = function() {
		$('dd.nav_child').click(function(){
			if($('body').width()<=768){
				$('aside').addClass('hideAsideAni').removeClass('showAsideAni');
				$('.bg').css('display','none');
			}
		})
		$('.hide_li img').click(function(){
			if($('body').width()<=768){
				$('aside').addClass('hideAsideAni').removeClass('showAsideAni');
				$('.bg').css('display','none');
			}
		})
	};
	
})
