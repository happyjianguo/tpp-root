var tabSet=new Set();
(function(window, undefined) {
	'use strict';

	function UInav() {

	}
	UInav.prototype = {
		//		树状导航
		tree_nav: function(obj, cb) {
			var self = this;
			var data = {
				
			}
			if(obj.bodyArr) {
				var arr = obj.bodyArr;
//				根据bodyArr动态创建菜单
				var btnBox = $('<div>').addClass('btn_box iconfont').css('background', obj.childColor ? obj.childColor : '').appendTo("#" + obj.ele).css('color', obj.fontColor ? obj.fontColor : '');
//				添加logo和标题
				if(obj.logo){
					$('<img>').attr('src',obj.logo).addClass('logoImg').appendTo(btnBox);
				}
				if(obj.title){
					$('<span>').html(obj.title).addClass('logoTitle').appendTo(btnBox);
				}
//				创建展示位置
				if(obj.showBox){
					$('#'+obj.showBox).append($('<iframe>').addClass('showIframe').attr('src',obj.bodyArr[0].childArr[0].url));
				}

				var btn_iconfont = $('<div>').addClass('hide_btn iconfont').appendTo(btnBox);
				var box = $('<div>').addClass('nav_tree_box').appendTo("#" + obj.ele);
				var parentDom = $('<ul>').addClass("nav_tree").css('color', obj.fontColor ? obj.fontColor : '').appendTo(box);
                for(var i = 0; i < arr.length; i++) {

                    var parentLi = $('<li>').addClass('nav_item iconfont').attr('item_num', i);
                    $('<p>').addClass('item_title').attr('title', arr[i].text).html(arr[i].text).css('background', obj.itemColor ? obj.itemColor : '').appendTo(parentLi);

                    var parentDl = $('<dl>').css('background', obj.childColor ? obj.childColor : '').addClass('child_box').appendTo(parentLi);

                    if(arr[i].shiro!='' && arr[i].shiro!=undefined) {
                        var parentShiro = $('<shiro:hasPermission>').attr('name',arr[i].shiro);
                        parentLi.appendTo(parentShiro);
                        parentShiro.appendTo(parentDom);
                    }else
                        parentLi.appendTo(parentDom);

                    for(var j = 0; j < arr[i].childArr.length; j++) {

                        var child = arr[i].childArr[j];
                        var childDom = $('<dd>').addClass('nav_child site-demo-active').html(child.text).attr({'title':child.text,'child_num':j,'data-change':'tabChange','data-type':'tabAdd','id':child.id});
                        $('<img>').addClass('nav_logo').attr('src', child.imgUrl).appendTo(childDom);

                        if(arr[i].childArr[j].shiro!='' && arr[i].childArr[j].shiro!=undefined){
                            var childShiro = $('<shiro:hasPermission>').attr('name',arr[i].childArr[j].shiro);
                            childDom.appendTo(childShiro);
                            childShiro.appendTo(parentDl);
                        }else
                            childDom.appendTo(parentDl);

                    }
                }
				//设置宽度隐藏滚动条
				$('.nav_tree').width($('.nav_tree').width() + 17);
				//默认展示第一个一级菜单
				$($('.nav_item')[0]).addClass('nav_item_active').find('.nav_child').attr('style', 'display: block;');
				//点击一级菜单,显示对映的二级菜单,其他隐藏
				$('.nav_item .item_title').click(function() {
					if($(this).parent().hasClass('nav_item_active')) {
						$(this).parent().removeClass('nav_item_active');
						$(this).parent().find('.nav_child').attr('style', 'display: none;');
					} else {
						$('.nav_item').removeClass('nav_item_active').find('dd').attr('style', 'display: none;');
						$(this).parent().find('.nav_child').attr('style', 'display: block;');
						$(this).parent().addClass('nav_item_active');
					}

				})
					data.nav_show = true;
					self.layuiElement(obj,data.nav_show);
				//点击二级菜单执行cb函数,返回点击的一级菜单和二级菜单的序号
				
				$('.nav_child').click(function() {
					$('.nav_child').removeClass('childActive');
					$(this).addClass('childActive');
//					data.nav_show = true;
//					data.itemNum = $($(this).parents()[1]).attr('item_num');
//					data.childNum = $(this).attr('child_num');
//					$('.showIframe').attr('src',obj.bodyArr[data.itemNum].childArr[data.childNum].url);
//					if(cb) cb(data);
				})
				
				//点击收回按钮
				$('.hide_btn').click(function() {
					data.nav_show = false;
					if(cb) cb(data);
					$("#" + obj.ele).empty();
					//添加一个小容器,用来隐藏滚动条
					var ui_box = $('<div>').addClass('ui_box').appendTo($("#" + obj.ele));
					//动态创建菜单,只显示二级菜单的图,如果没有则不创建
					var btnBox = $('<div>').addClass('btn_box iconfont').css('background', obj.childColor ? obj.childColor : '').appendTo(ui_box).css('color', obj.fontColor ? obj.fontColor : '');

					if(obj.logo){
						$('<img />').attr('src',obj.logo).addClass('btn_logo show_btn').appendTo(btnBox);
					}else{
						$('<div>').addClass('show_btn iconfont').appendTo(btnBox);
					}
					var hide_ul = $('<ul>').addClass('hide_ul').css('background', obj.childColor ? obj.childColor : '').appendTo(ui_box);
					for(var i = 0; i < obj.bodyArr.length; i++) {
						for(var j = 0; j < obj.bodyArr[i].childArr.length; j++) {
							var hide_li = $('<li>').addClass('hide_li').appendTo(hide_ul).attr('item_num',i);
							if(obj.bodyArr[i].childArr[j].imgUrl){
								$('<img>').addClass('hide_logo site-demo-active').attr({'title':obj.bodyArr[i].childArr[j].text,'src':obj.bodyArr[i].childArr[j].imgUrl,'child_num':j,'data-type':"tabAdd",'data-change':'tabChange','id':obj.bodyArr[i].childArr[j].id}).appendTo(hide_li);
							}

						}
					}
					self.layuiElement(obj,data.nav_show);
					//点击菜单执行回调函数
					
					$('.hide_li').click(function(){
						$('.hide_li').removeClass('childActive');
						$(this).addClass('childActive');
//						data.itemNum = $(this).attr('item_num');
//						data.childNum = $(this).find('.hide_logo').attr('child_num');
//						$('.showIframe').attr('src',obj.bodyArr[data.itemNum].childArr[data.childNum].url);
//						if(cb) cb(data);
					})
					//点击展开按钮
					$('.show_btn').click(function() {
						data.nav_show = true;
						if(cb) cb(data);
						$('#' + obj.ele).empty();
						$('#' + obj.showBox).empty();
						self.tree_nav(obj, cb)
					})
				})
				

			}

		},
		layuiElement:function(obj,hideOrshow){
//			初始化layui插件
			layui.use('element', function() {
			
				var $ = layui.jquery,
					element = layui.element; 
					
				//触发事件
				var active = {

                    tabAdd: function() {
                        // alert(obj);

                        // //新增一个Tab项
                        var itemNum,childNum;
                        if(hideOrshow){
                            itemNum = $($(this).parents()[1]).attr('item_num');
                            childNum = $(this).attr('child_num');
                        }else{
                            itemNum = $($(this).parent()).attr('item_num');
                            childNum =$(this).attr('child_num');
                        }
                        var id=$(this).attr('id');
                        var exist=$("li[lay-id='"+id+"']").length;
                        if(exist<1){
                            element.tabAdd('demo', {
                                title: $(this).attr('title'),
                                content: '<iframe src="'+ obj.bodyArr[itemNum].childArr[childNum].url +'" id="iframe_'+id+'" width="100%" height="100%" frameborder="0"></iframe>',
                                id: obj.bodyArr[itemNum].childArr[childNum].id //实际使用一般是规定好的id，这里以时间戳模拟下
                            })
						}
                    },
                    tabChange: function(){
				      //切换到指定Tab项
				      element.tabChange('demo', $(this).attr('id')); //切换到：用户管理
                        element.on('tab(demo)', function(data){
                            var src=$(".layui-tab-item.layui-show").find("iframe").attr("src");
                            $(".layui-tab-item.layui-show").find("iframe").attr("src",src);
                        });
				    }
				};
				$('.site-demo-active').on('click', function() {
					var othis = $(this),
						type = othis.data('type'),
						change = othis.data('change');
					active[type] ? active[type].call(this, othis) : '';
					active[change] ? active[change].call(this, othis) : '';
				});
			});
		}

	}

	window.UInav = UInav;
})(window)