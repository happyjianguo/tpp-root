(function(window, undefined) {
	'use strict';

	function Table() {
		this.sortIndex = true;
	}
	Table.prototype = {
		create: function(obj, cb) {
			var self = this;
			var html = "<div id='UI_table' border='0' cellspacing='0' cellpadding='0' bordercolor=" + (obj.bordercolor ? obj.bordercolor : '#ddd') + "><div class='headBox'><div class='table_head'><div class='tr'></div></div></div><div id='"+ (obj.isSortBox?'sortable':'') +"' class='table_body'></div></div>";
			$('#' + obj.ele).html(html).css('overflow','auto');
			if(obj.head) {
				//				创建表头
				//				添加序号
				$('<div>').addClass('table_title tr_id').html('序号').appendTo('#UI_table .table_head .tr');
				//				添加复选框
				if(obj.checked){
					var checkboxHtml = '<div class="checkbox"><input class="control" type="checkbox" id="control"  name="control" ><label class="layui-icon" for="control"><span></span></label></div>';
					$('<div>').addClass('table_title tr_id tr_check').html(checkboxHtml).addClass('tr_id').appendTo('#UI_table .table_head .tr');
				}
				for(var i = 0; i < obj.head.length; i++) {
					if(obj.head[i].type == 'input' || obj.head[i].type == 'date' || obj.head[i].type == 'dateRange' || obj.head[i].type == 'select') {
						$('<div>').html(obj.head[i].title).addClass('table_title  ' + (obj.head[i].sort ? "iconfont sortBox" : "") + '  inputBox').attr('data-type', obj.head[i].type).attr('data-title', obj.head[i].title).appendTo("#UI_table .table_head .tr");

					} else {
						$('<div>').html(obj.head[i].title).addClass('table_title').attr('data-type', obj.head[i].type).attr('data-title', obj.head[i].title).attr('data-type', obj.head[i].type).appendTo("#UI_table .table_head .tr");
					}
				}
//				添加操作列
			if(obj.editor){
				$('<div>').addClass('table_title btnbox').html('操作').appendTo('#UI_table .table_head .tr');
			}

			}
			if(obj.body) {
				self.createBody(obj)
			}else{
				return
			}
			
//			绑定head和body滚动效果
			$('.table_body ').scroll(function(){
				$('.table_head').scrollLeft($('.table_body ').scrollLeft())
			})
			$('.table_head').scroll(function(){
				$('.table_body').scrollLeft($('.table_head ').scrollLeft())
			})
			//监听选择框（前面的）
			var checkArr = [];
			var checkedIndex = '';
			$('input[type = checkbox][name = control]').click(function() {
				if($(this).is(':checked')) {
//					$('input[type = checkbox][name = control]').next().css('background', '#F5A623');
					$('input[type = checkbox][name = item]').prop('checked', true);
//					$('input[type = checkbox][name = item]').next().css('background', '#5FB878');
					checkArr = obj.body;
					cb(checkArr);
				} else {
					$('input[type = checkbox][name = item]').prop('checked', false);
//					$('input[type = checkbox][name = item]').next().css('background', '#fff');
//					$('input[type = checkbox][name = control]').next().css('background', '#fff');
					checkArr = [];
					checkedIndex = '';
					cb(checkArr);
				}
			})
			$('input[type = checkbox][name = item]').click(function() {
				checkedIndex = '';
				checkArr = [];
				for(var i = 0; i < $('input[type = checkbox][name = item]').length; i++) {
					if($($('input[type = checkbox][name = item]')[i]).is(':checked')) {
//						$($('input[type = checkbox][name = item]')[i]).next().css('background', '#5FB878');
						var clickIndex = ($($($('input[type = checkbox][name = item]')[i]).parents()[1]).attr('data_num') - 1);
						if(checkedIndex.indexOf(clickIndex) == -1) {
							checkedIndex += clickIndex;
							checkArr.push(obj.body[clickIndex]);
						}
					}else{
//						$($('input[type = checkbox][name = item]')[i]).next().css('background', '#fff');
					}
				}
				if(checkArr.length == obj.body.length){
					$('input[type = checkbox][name = control]').prop('checked', true);
//					$('input[type = checkbox][name = control]').next().css('background', '#5FB878');
				}else{
					$('input[type = checkbox][name = control]').prop('checked', false);
//					$('input[type = checkbox][name = control]').next().css('background', '#fff');
				}
				cb(checkArr);
			})

			layui.use(['form', 'layedit', 'laydate'], function() {
				var form = layui.form;
				
				//监听指定开关
				form.on('switch(switchTest)', function(data) {
					var switchIndex = $($(this).parents()[4]).attr('data_num') - 1;
					var switchArr = obj.body[switchIndex];
					for(var i = 0; i < switchArr.length; i++) {
						if(switchArr[i].type == 'switch') {
							switchArr[i].text = this.checked ? 'true' : 'false';
						}
					}
					cb(obj.body[switchIndex]);
				});
				//监听单选按钮
				form.on('radio(radio)', function(data) {
					var radioIndex = $($(data.elem).parents()[4]).attr('data_num') - 1;
					var radioArr = obj.body[radioIndex];

					for(var i = 0; i < radioArr.length; i++) {

						if(radioArr[i].type == 'radio') {
							radioArr[i].value = data.value;
							var options = radioArr[i].option
							for(var j = 0;j<options.length;j++){
								if(options[j].value ==  data.value){
									obj.body[radioIndex][i].text = options[j].text;
								}
							}
						}
					}
					cb(obj.body[radioIndex]);
				});
			})

			//拖动效果
			$("#sortable").sortable({
				placeholder: "ui-state-highlight",
				stop: function(event, ui) {
//											console.log(event);
//											console.log(ui);
					var arr123 = $("#sortable").sortable('toArray');
											console.log(arr123);
				}

			});
			$("#sortable").disableSelection();
		
			//添加一行
			$('.addLine').click(function(){
				if(!obj.body){
					return
				}
				var index = parseInt($(this).attr('data_num'));
				var box = $('div.tbody_tr[data_num = '+ $(this).attr('data_num') +']');
				var newTr = $('<div>').addClass('tbody_tr').attr('data_num',index + 1).attr('id','table'+(index + 1))
				newTr.append($('<div>').addClass('tr_id table_cell').html(index+1))
				box.after(newTr);
				var time =  Date.parse(new Date());
				var checkboxDom = '<div data_num="'+ (index + 1) +'" class="tr_check table_cell"><div class="checkbox"><input type="checkbox" value="" id="'+ time +'" name="item"><label class="layui-icon" for="'+ time +'"><span></span></label></div></div>' 
				newTr.append(checkboxDom)
				for(var m = 1;m<obj.body[0].length;m++){
					var msgTip = '';
					if(obj.body[0][m].msg){
						msgTip = obj.body[0][m].msg;
					}else {
						switch(obj.body[0][m].type){
							case 'input':
							msgTip = '输入任意字符';
							break;
							case 'date':
							case 'dateRange':
							msgTip = '输入正确的日期格式：2018-01-01 - 2018-12-31';
							break;
							case 'select':
							case 'radio':
							msgTip = '输入候选项，多个用空格分隔,默认选择第一个';
							break
							case 'checkbox':
							case 'switch':
							msgTip = '非空代表选中';
							break;
						}
						
					}
					var inputDom = '<input type="text" class = "newInput" placeholder="请输入数据" /><span class="iconfont messageTip" title="' + msgTip + '"></span>'
					$('<div>').append(inputDom).addClass('newTh table_cell').attr('data-title',obj.body[0][m].title).outerWidth($('div[data-title='+ obj.body[0][m].title+']').outerWidth()).appendTo(newTr);
				}
				var btnHtml = '<span class="iconfont save"  data_num=' + (index + 1) + 'newInput></span><span class="iconfont del" title="删除时会清空所有正在编辑新的添加"  data_num=' + (index + 1) + '></span>'
				$('<div>').addClass('btnbox table_cell').html(btnHtml).appendTo(newTr);
				var dom = $(newTr).nextAll();
//				修改编号
				for(var i = 0;i<dom.length;i++){
					$(dom[i]).attr('data_num',parseInt($(dom[i]).attr('data_num'))+1).attr('id','table'+(parseInt($(dom[i]).attr('data_num'))));
					$(dom[i]).find('.tr_id').html(parseInt($(dom[i]).find('.tr_id').html()) + 1);
					$(dom[i]).find('div.tr_check').attr('data_num',parseInt($(dom[i]).attr('data_num')));
					$(dom[i]).find('.btnbox span').attr('data_num',parseInt($(dom[i]).attr('data_num')));
				}
				
			//取消添加
			$('.del').click(function(){
				self.create(obj,cb);
			})
//			保存	
			$('div[data_num='+(index+1)+'] .save').click(function(){
				var addArr = JSON.parse(JSON.stringify(obj.body[0]));
//				添加的数据id为时间戳
					addArr[0].id = Date.parse(new Date());
				var newInput = $('div[data_num='+(index+1)+'] input[type=text]');
				for(var i=0;i<newInput.length;i++){
//					单选和复选框添加候选数组
					if(addArr[i+1].type == 'select'||addArr[i+1].type == 'radio'){
						for(var k = 0;k<addArr[i+1].option.length;k++){
							addArr[i+1].option[k].text = $(newInput[i]).val().split(' ')[k];
						}
						addArr[i+1].text =  addArr[i+1].option[0].text;
					}else{
						addArr[i+1].text = $(newInput[i]).val();
					}
				}
				obj.body.splice(index,0,addArr);
//				返回添加的数组
				if(cb) cb(addArr);
				self.create(obj,cb);
				
			})
				
			})
			
			
			//				点击编辑图标编辑功能
			$('#UI_table span.refresh').click(function() {
				var refreshIndex = $(this).attr('data_num') - 1;
				var refreshBody = obj.body[refreshIndex];
				self.refreshFun(refreshBody, refreshIndex);
			})

			//				双击开启编辑功能
			$('#UI_table  div.tbody_tr').dblclick(function() {
				var refreshIndex = $(this).attr('data_num') - 1;
				var refreshBody = obj.body[refreshIndex];
				if(obj.editor){
					self.refreshFun(refreshBody, refreshIndex);
				}

			})
				//				点击保存按钮保存
			$('#UI_table span.save').click(function() {
				var index = $(this).attr('data_num') - 1;
				self.save(obj, index, cb);
			})
			
			//			排序
			$('#UI_table div.sortBox').click(function() {
				var index = 0;
				for(var i = 0; i < obj.head.length; i++) {
					if(obj.head[i].title == $(this).attr('data-title')) {
						index = i;
					}
				}
				function sortRule1(x, y) {
					if(!isNaN(x[index+1].text) && !isNaN(y[index+1].text)) {
						if(parseFloat(x[index+1].text) > parseFloat(y[index+1].text)) {
							return 1;
						} else {
							return -1;
						}
					} else {
						if(x[index+1].text > y[index+1].text) {
							return 1;
						} else {
							return -1;
						}
					}

				}

				function sortRule2(x, y) {
					if(!isNaN(x[index+1].text) && !isNaN(y[index+1].text)) {
						if(parseFloat(x[index+1].text) > parseFloat(y[index+1].text)) {
							return -1;
						} else {
							return 1;
						}
					} else {
						if(x[index+1].text > y[index+1].text) {
							return -1;
						} else {
							return 1;
						}
					}
				}
				$('.table_head tr').empty();
				$('.table_body').empty();
				if(self.sortIndex) {
					obj.body.sort(sortRule1);
					self.sortIndex = !self.sortIndex
				} else {
					obj.body.sort(sortRule2);
					self.sortIndex = !self.sortIndex
				}
				self.create(obj, cb);
				if(cb) cb(obj.body);
			})
		},
		createBody: function(obj) {
			var bodyArr = obj.body
			for(var i = 0; i < bodyArr.length; i++) {
				//				添加选择框
				if(obj.checked){
					
				}
				
				//				添加序号
				var trbox = $('<div>').attr('data_num', i + 1).attr('id', 'table' + (i + 1)).addClass('tbody_tr').append($('<div>').addClass('tr_id table_cell').html(i + 1)).appendTo('#UI_table .table_body');
				if(obj.checked){
					var checkboxHtml = '<div class="checkbox"><input type="checkbox" value="'+bodyArr[i][0].id+'" id="item' + i + '"  name="item"><label class="layui-icon" for="item' + i + '"><span></span></label></div>';
					var checkboxBox = $('<div>').attr('data_num', i + 1).addClass('tr_check table_cell').html(checkboxHtml).appendTo(trbox);
				}
				for(var j = 1; j < bodyArr[i].length; j++) {
					switch(bodyArr[i][j].type) {
						case "checkbox":
							this.checkbox(bodyArr[i][j], i);
							break;
						case "switch":
							this.switch(bodyArr[i][j], i);
							break;
						case "radio":
							this.radio(bodyArr[i][j], i);
							break;
						case "input":
							if(bodyArr[i][j].msg) {
								$('<div>').attr('data-type', bodyArr[i][j].type).attr('data-title', bodyArr[i][j].title).addClass('table_cell inputBox textBox').html(bodyArr[i][j].text).appendTo('#UI_table .table_body div.tbody_tr[data_num = ' + (i + 1) + ']');
							} else {
								$('<div>').attr('data-type', bodyArr[i][j].type).attr('data-title', bodyArr[i][j].title).addClass('table_cell inputBox').html(bodyArr[i][j].text).appendTo('#UI_table .table_body div.tbody_tr[data_num = ' + (i + 1) + ']');
							}
							break;
						default:
							$('<div>').attr('data-type', bodyArr[i][j].type).attr('data-title', bodyArr[i][j].title).addClass('table_cell inputBox').html(bodyArr[i][j].text).appendTo('#UI_table .table_body div.tbody_tr[data_num = ' + (i + 1) + ']');
							break;
					}

				}
//				添加操作按钮
				if(obj.editor){
					var btnHtml = '<span class="iconfont refresh"  data_num=' + (i + 1) + '></span><span class="iconfont save" hidden="hidden" data_num=' + (i + 1) + '></span><span class="iconfont addLine"  data_num=' + (i + 1) + '></span>'
					$('<div>').addClass('btnbox table_cell').html(btnHtml).appendTo('#UI_table .table_body div.tbody_tr[data_num = ' + (i + 1) + ']');
				}


			}
//			动态设置每列的最大宽度
			
			for(var  k = 1; k < obj.body[0].length; k++) {
				var maxWidth='';
				var dom = $('div[data-title = ' + obj.body[0][k].title + ']');
				for(var l = 0;l<dom.length;l++){
					
					if($(dom[l]).outerWidth()<$(dom[l+1]).outerWidth()){

						maxWidth = $(dom[l+1]).outerWidth();
					}
				}
				$(dom).outerWidth(maxWidth);
			}

			
		},
		save: function(obj, index, cb) {
			var refreshTh = obj.body[index];
			var refreshDom = $('div.tbody_tr[data_num=' + (index + 1) + '] div.table_cell');
			for(var i = 0; i < refreshTh.length - 1; i++) {
				switch($(refreshDom[(i + 2)]).attr('data-type')) {
					case 'checkbox':
						obj.body[index][i + 1].text = $(refreshDom[(i + 2)]).find('input').prop('checked');
						break;
					case 'switch':
						obj.body[index][i + 1].text = $(refreshDom[(i + 2)]).find('em').html();
						break;
					case 'radio':
						if($(refreshDom[(i + 2)]).find('.layui-form-radioed').length > 0) {
							obj.body[index][i + 1].text = $(refreshDom[(i + 2)]).find('.layui-form-radioed div').html().replace(/(^\s*)|(\s*$)/g, "");
						}
						break;
					case 'select':
						var options = obj.body[index][i + 1].option;
						for(var j = 1;j<options.length;j++){
							if(options[j].text ==  $(refreshDom[(i + 2)]).find('input').val()){
								obj.body[index][i + 1].value = options[j].value;
							}
						}
						obj.body[index][i + 1].text = $(refreshDom[(i + 2)]).find('input').val();
						$(refreshDom[(i + 2)]).html($(refreshDom[(i + 2)]).find('input').val());
						break;
					default:
						obj.body[index][i + 1].text = $(refreshDom[(i + 2)]).find('input').val();
						$(refreshDom[(i + 2)]).html($(refreshDom[(i + 2)]).find('input').val());
						break;
				}

			}
			$('.save[data_num=' + (index + 1) + ']').attr('hidden', true);
			if(cb) cb(obj.body[index]);
		},
		refreshFun: function(arr, index) {
			//			显示保存按钮
			$('.save[data_num=' + (index + 1) + ']').attr('hidden', false);
			for(var i = 0; i < arr.length; i++) {
				switch(arr[i].type) {
					case 'input':
						this.input(arr[i], index);
						break;
					case 'date':
						this.date(arr[i], index);
						break;
					case 'dateRange':
						this.dateRange(arr[i], index);
						break;
					case 'select':
						this.select(arr[i], index);
						break;
				}
			}
		},
		select: function(arr, index) {
			var html = '<form class="layui-form"><div class="layui-form-item"><div class="layui-input-inline"><select name="quiz1">';
			for(var i = 0; i < arr.option.length; i++) {
				html += '<option value=' + arr.option[i].value + ' ' + (arr.text == arr.option[i].text ? "selected" : "") + '>' + (arr.option[i].text) + '</option>';
			}
			html += '</select></div></div></form>';
			$('#UI_table .table_body div.tbody_tr[data_num = ' + parseInt(index + 1) + '] div[data-title = ' + arr.title + ']').html(html).addClass('createdInput');
			this.layuiRender();
		},
		dateRange: function(arr, index) {
			var html = '<div class="layui-form"><div class="layui-form-item"><div class="layui-inline"><div class="layui-input-inline"><input type="text" class="layui-input" id="test' + index + '"></div></div></div></div></form>'
			$('#UI_table .table_body div.tbody_tr[data_num = ' + parseInt(index + 1) + '] div[data-title = ' + arr.title + ']').addClass('createdInput').html(html);
			//			重新初始化layui的日期插件
			layui.use(['laydate'], function() {
				var laydate = layui.laydate;
				//日期范围
				laydate.render({
					elem: '#test' + index,
					range: true,
					value: arr.text
				});

			});
		},
		date: function(arr, index) {
			var html = '<div class="layui-form"><div class="layui-inline"><div class="layui-input-inline"><input type="text" name="date" id="date' + index + '" class="layui-input" value=' + arr.text + '></div></div></form>'
			$('#UI_table .table_body div.tbody_tr[data_num = ' + parseInt(index + 1) + '] div[data-title = ' + arr.title + ']').addClass('createdInput').html(html);
			//			重新初始化layui的日期插件
			layui.use(['laydate'], function() {
				var laydate = layui.laydate;
				//日期
				laydate.render({
					elem: '#date' + index
				});

			});
		},
		input: function(arr, index) {
			var html = '<div class="layui-form"><div class="layui-form-item"><div class="layui-input-inline"><input type="text" maxlength=' + arr.maxlength + ' placeholder="请输入"  value=' + arr.text + ' class="layui-input" ></div></div></form>'
			if(arr.msg) {
				html += '<span class="iconfont messageTip" title=' + arr.msg + '></span>'
			}
			$('#UI_table .table_body div.tbody_tr[data_num = ' + parseInt(index + 1) + '] div[data-title = ' + arr.title + ']').addClass('createdInput').html(html);
			this.layuiRender();
		},
		checkbox: function(arr, index) {
			var html = '<form class="layui-form"><div class="layui-form-item" pane=""><div class="layui-input-block"><input type="checkbox"  lay-skin="primary" title="" ' + (arr.text ? "checked" : "") + '></div></div></form>';
			$('<div>').attr('data-type', 'checkbox').attr('data-title', arr.title).addClass('table_cell tab_checkbox').html(html).appendTo('#UI_table .table_body div.tbody_tr[data_num = ' + parseInt(index + 1) + ']');
			this.layuiRender();
		},
		switch: function(arr, index) {
			var html = '<form class="layui-form"><div class="layui-form-item"><div class="layui-input-block"><input type="checkbox" name="open" lay-skin="switch" lay-filter="switchTest" lay-text="ON|OFF" ' + (arr.text == "ON" ? "checked" : "") + '></div></div></form>';
			$('<div>').attr('data-type', 'switch').attr('data-title', arr.title).addClass('table_cell').html(html).appendTo('#UI_table .table_body div.tbody_tr[data_num = ' + parseInt(index + 1) + ']');
			this.layuiRender();
		},
		radio: function(arr, index) {
			var radiohtml = '';
			for(var j = 0; j < arr.option.length; j++) {
				radiohtml += '<input type="radio" name="sex" lay-filter = "radio"   title="  ' + (arr.option[j].text) + '  "  value="' +(arr.option[j].value)+ '" ' + (arr.text == arr.option[j].text ? "checked" : "") + '>';
			}
			var html = '<form class="layui-form" action=""><div class="layui-form-item"><div class="layui-input-block"> ' + radiohtml + ' </div></div></form>';
			$('<div>').attr('data-type', 'radio').attr('data-title', arr.title).addClass('table_cell th_radio').html(html).appendTo('#UI_table .table_body div.tbody_tr[data_num = ' + parseInt(index + 1) + ']');
			this.layuiRender();
		},
		layuiRender: function() {
			//			重新初始化layui的form插件
			layui.use(['form'], function() {
				var form = layui.form;
				form.render();
			});
		},

	}

	window.Table = Table;
})(window)