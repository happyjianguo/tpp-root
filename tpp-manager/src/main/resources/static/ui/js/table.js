//翻页功能
//layui.use(['form', 'layedit', 'laydate','laypage'], function() {
	  
//laypage.render({
//  elem: 'demo7'
//  ,count: 100
//  ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
//  ,jump: function(obj){
//    console.log(obj)
//  }
//});

//});

$(function() {
	//	传入的对象数据格式如下:
	//  bordercolor:表格的边框颜色，可自定义,默认为浅灰色
	//	包含head和body两个数组;注意head和body需要一一对映（head不是必须的）
	//	head数组是表头数组,其中每个二级数组是每个标题的内容和操作
	//	type:确定整列的类型,包括input,date,dateRange,select,checkbox,switch和radio
	//	title:标题内容
	//	sort:当前列是否排序,参数为true或false
	//	body是列表内容,同样包含type和head一样
	//	text为内容
	//	option:当type为checkbox或radio时,option为选项数组,其他type属性传入无效
	//	msg:输入框的提示信息，如果没有则不显示，长度没有限度
	//	maxlength:输入最大长度，没有则没有限制
	//	isSortBox:是否可以拖动,true为可拖动
//option需要是对象



	var data = {
		ele:'table_box',
		isSortBox:true,
		editor:true,
		checked:true,
//		bordercolor:'#f00',
		head: [{
			type: 'input',
			title: '用户名',
			sort: 'true',
		}, {
			type: 'input',
			title: '测试'
		}, {
			type: 'input',
			title: '签名'
		}, {
			type: 'input',
			title: '测试1'
		}, {
			type: 'date',
			title: '日期',
			sort: '111'
		}, {
			type: 'dateRange',
			title: '日期范围'
		}, {
			type: 'select',
			title: '选择框'
		}, {
			type: 'checkbox',
			title: '复选框'
		}, {
			type: 'switch',
			title: '开关'
		}, {
			type: 'radio',
			title: '单选框'
		}],
		body: [
			[{
				id:'000000'
			},
			{
				type: 'input',
				text: '李白3',
				title: '用户名',
				msg: '中文字符中文字符',
				maxlength: '3',
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '测试'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '签名'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '测试1'
			}, {
				type: 'date',
				text: '2018-09-01',
				title: '日期'
			}, {
				type: 'dateRange',
				text: '2018-01-01 - 2018-03-03',
				title: '日期范围'
			}, {
				type: 'select',
				text: '浙江省',
				value:'000',
				option: [{
					value:"000",
					text:'浙江省'
				},{
					value:"100",
					text:'江西省'
				},{
					value:"211",
					text:'福建省'
				},{
					value:"311",
					text:'辽宁省'
				}],
				title: '选择框'
			}, {
				type: 'checkbox',
				text: 'checked',
				title: '复选框'
			}, {
				type: 'switch',
				text: 'ON',
				title: '开关'
			}, {
				type: 'radio',
				text: '男',
				value:'000',
				option:[
				{
					value:'000',
					text:'男'
				},
				{
					value:'0001',
					text:'女'
				}],
				title: '单选框'
			}],
			[{
				id:'000001'
			},{
				type: 'input',
				text: '李白4',
				title: '用户名',
				msg: '中文字符'
			}, {
				type: 'input',
				text: '人生恰似一场修行人生场修行人生恰似一场修行',
				title: '测试'
			}, {
				type: 'input',
				text: '人生恰似一场修行人生恰似一\修行',
				msg:'ceshiciahsidas',
				title: '签名'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '测试1'
			}, {
				type: 'date',
				text: '2018-07-01',
				title: '日期'
			}, {
				type: 'dateRange',
				text: '2018-01-01 - 2018-03-03',
				title: '日期范围'
			}, {
				type: 'select',
				text: '浙江省',
				value:'01',
				option:[{
					value:"00",
					text:'湖南省'
				},{
					value:"01",
					text:'浙江省'
				},{
					value:"02",
					text:'福建省'
				},{
					value:"03",
					text:'辽宁省'
				}],
				title: '选择框'
			}, {
				type: 'checkbox',
				text: 'checked',
				title: '复选框'
			}, {
				type: 'switch',
				text: 'ON',
				title: '开关'
			}, {
				type: 'radio',
				text: '好',
				value:'0',
				option:[
				{
					value:'0',
					text:'好'
				},
				{
					value:'1',
					text:'不好'
				}],
				title: '单选框'
			}],
			[{
				id:'000002'
			},{
				type: 'input',
				text: '李白5',
				title: '用户名'
			}, {
				type: 'input',
				text: '人生恰似一场修行人生恰似一场行人生恰似一场修行',
				title: '测试'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '签名'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '测试1'
			}, {
				type: 'date',
				text: '2018-02-01',
				title: '日期'
			}, {
				type: 'dateRange',
				text: '2018-01-01 - 2018-03-03',
				title: '日期范围'
			}, {
				type: 'select',
				text: '湖南省',
				value:'A',
				option: [{
					value:"A",
					text:'湖南省'
				},{
					value:"B",
					text:'浙江省'
				},{
					value:"C",
					text:'福建省'
				},{
					value:"D",
					text:'辽宁省'
				}],
				title: '选择框'
			}, {
				type: 'checkbox',
				text: '',
				title: '复选框'
			}, {
				type: 'switch',
				text: 'ON',
				title: '开关'
			}, {
				type: 'radio',
				text: '女',
				value:'0001',
				option:[
				{
					value:'000',
					text:'男'
				},
				{
					value:'0001',
					text:'女'
				}],
				title: '单选框'
			}],
			[{
				id:'000003'
			},{
				type: 'input',
				text: '李白1',
				title: '用户名'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				title: '测试'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '签名'
			}, {
				type: 'input',
				text: '人生恰似一场修行人生恰似一场修行人生恰似一场修行人生恰似一场修行人生恰似一场修行人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '测试1'
			}, {
				type: 'date',
				text: '2018-11-01',
				title: '日期'
			}, {
				type: 'dateRange',
				text: '2018-01-01 - 2018-03-03',
				title: '日期范围'
			}, {
				type: 'select',
				text: '辽宁省',
				value:'d',
				option: [{
					value:"a",
					text:'湖南省'
				},{
					value:"b",
					text:'浙江省'
				},{
					value:"c",
					text:'福建省'
				},{
					value:"d",
					text:'辽宁省'
				}],
				title: '选择框'
			}, {
				type: 'checkbox',
				text: 'checked',
				title: '复选框'
			}, {
				type: 'switch',
				text: '',
				title: '开关'
			}, {
				type: 'radio',
				text: '男',
				value:'000',
				option:[
				{
					value:'000',
					text:'男'
				},
				{
					value:'0001',
					text:'女'
				}],
				title: '单选框'
			}],
			[{
				id:'000004'
			},{
				type: 'input',
				text: '李白2',
				title: '用户名'
			}, {
				type: 'input',
				text: '人生恰似一场修行人生恰似一场修行人生恰似一场修行',
				title: '测试'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '签名'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '测试1'
			}, {
				type: 'date',
				text: '2018-08-01',
				title: '日期'
			}, {
				type: 'dateRange',
				text: '2018-01-01 - 2018-03-03',
				title: '日期范围'
			}, {
				type: 'select',
				text: '福建省',
				value:'2',
				option: [{
					value:"0",
					text:'湖南省'
				},{
					value:"1",
					text:'浙江省'
				},{
					value:"2",
					text:'福建省'
				},{
					value:"3",
					text:'辽宁省'
				}],
				title: '选择框'
			}, {
				type: 'checkbox',
				text: 'checked',
				title: '复选框'
			}, {
				type: 'switch',
				text: 'ON',
				title: '开关'
			}, {
				type: 'radio',
				text: '男',
				value:'000',
					option:[
					{
						value:'000',
						text:'男'
					},
					{
						value:'0001',
						text:'女'
					}],
				title: '单选框'
			}],
			[{
				id:'000004'
			},{
				type: 'input',
				text: '李白2',
				title: '用户名'
			}, {
				type: 'input',
				text: '人生恰似一场修行人生恰似一场修行人生恰似一场修行',
				title: '测试'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '签名'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '测试1'
			}, {
				type: 'date',
				text: '2018-08-01',
				title: '日期'
			}, {
				type: 'dateRange',
				text: '2018-01-01 - 2018-03-03',
				title: '日期范围'
			}, {
				type: 'select',
				text: '福建省',
				value:'2',
				option: [{
					value:"0",
					text:'湖南省'
				},{
					value:"1",
					text:'浙江省'
				},{
					value:"2",
					text:'福建省'
				},{
					value:"3",
					text:'辽宁省'
				}],
				title: '选择框'
			}, {
				type: 'checkbox',
				text: 'checked',
				title: '复选框'
			}, {
				type: 'switch',
				text: 'ON',
				title: '开关'
			}, {
				type: 'radio',
				text: '男',
				value:'000',
					option:[
					{
						value:'000',
						text:'男'
					},
					{
						value:'0001',
						text:'女'
					}],
				title: '单选框'
			}],
			[{
				id:'000004'
			},{
				type: 'input',
				text: '李白2',
				title: '用户名'
			}, {
				type: 'input',
				text: '人生恰似一场修行人生恰似一场修行人生恰似一场修行',
				title: '测试'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '签名'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '测试1'
			}, {
				type: 'date',
				text: '2018-08-01',
				title: '日期'
			}, {
				type: 'dateRange',
				text: '2018-01-01 - 2018-03-03',
				title: '日期范围'
			}, {
				type: 'select',
				text: '福建省',
				value:'2',
				option: [{
					value:"0",
					text:'湖南省'
				},{
					value:"1",
					text:'浙江省'
				},{
					value:"2",
					text:'福建省'
				},{
					value:"3",
					text:'辽宁省'
				}],
				title: '选择框'
			}, {
				type: 'checkbox',
				text: 'checked',
				title: '复选框'
			}, {
				type: 'switch',
				text: 'ON',
				title: '开关'
			}, {
				type: 'radio',
				text: '男',
				value:'000',
					option:[
					{
						value:'000',
						text:'男'
					},
					{
						value:'0001',
						text:'女'
					}],
				title: '单选框'
			}],
			[{
				id:'000004'
			},{
				type: 'input',
				text: '李白2',
				title: '用户名'
			}, {
				type: 'input',
				text: '人生恰似一场修行人生恰似一场修行人生恰似一场修行',
				title: '测试'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '签名'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '测试1'
			}, {
				type: 'date',
				text: '2018-08-01',
				title: '日期'
			}, {
				type: 'dateRange',
				text: '2018-01-01 - 2018-03-03',
				title: '日期范围'
			}, {
				type: 'select',
				text: '福建省',
				value:'2',
				option: [{
					value:"0",
					text:'湖南省'
				},{
					value:"1",
					text:'浙江省'
				},{
					value:"2",
					text:'福建省'
				},{
					value:"3",
					text:'辽宁省'
				}],
				title: '选择框'
			}, {
				type: 'checkbox',
				text: 'checked',
				title: '复选框'
			}, {
				type: 'switch',
				text: 'ON',
				title: '开关'
			}, {
				type: 'radio',
				text: '男',
				value:'000',
					option:[
					{
						value:'000',
						text:'男'
					},
					{
						value:'0001',
						text:'女'
					}],
				title: '单选框'
			}],
			[{
				id:'000004'
			},{
				type: 'input',
				text: '李白2',
				title: '用户名'
			}, {
				type: 'input',
				text: '人生恰似一场修行人生恰似一场修行人生恰似一场修行',
				title: '测试'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '签名'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '测试1'
			}, {
				type: 'date',
				text: '2018-08-01',
				title: '日期'
			}, {
				type: 'dateRange',
				text: '2018-01-01 - 2018-03-03',
				title: '日期范围'
			}, {
				type: 'select',
				text: '福建省',
				value:'2',
				option: [{
					value:"0",
					text:'湖南省'
				},{
					value:"1",
					text:'浙江省'
				},{
					value:"2",
					text:'福建省'
				},{
					value:"3",
					text:'辽宁省'
				}],
				title: '选择框'
			}, {
				type: 'checkbox',
				text: 'checked',
				title: '复选框'
			}, {
				type: 'switch',
				text: 'ON',
				title: '开关'
			}, {
				type: 'radio',
				text: '男',
				value:'000',
					option:[
					{
						value:'000',
						text:'男'
					},
					{
						value:'0001',
						text:'女'
					}],
				title: '单选框'
			}],
			[{
				id:'000004'
			},{
				type: 'input',
				text: '李白2',
				title: '用户名'
			}, {
				type: 'input',
				text: '人生恰似一场修行人生恰似一场修行人生恰似一场修行',
				title: '测试'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '签名'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '测试1'
			}, {
				type: 'date',
				text: '2018-08-01',
				title: '日期'
			}, {
				type: 'dateRange',
				text: '2018-01-01 - 2018-03-03',
				title: '日期范围'
			}, {
				type: 'select',
				text: '福建省',
				value:'2',
				option: [{
					value:"0",
					text:'湖南省'
				},{
					value:"1",
					text:'浙江省'
				},{
					value:"2",
					text:'福建省'
				},{
					value:"3",
					text:'辽宁省'
				}],
				title: '选择框'
			}, {
				type: 'checkbox',
				text: 'checked',
				title: '复选框'
			}, {
				type: 'switch',
				text: 'ON',
				title: '开关'
			}, {
				type: 'radio',
				text: '男',
				value:'000',
					option:[
					{
						value:'000',
						text:'男'
					},
					{
						value:'0001',
						text:'女'
					}],
				title: '单选框'
			}],
			[{
				id:'000004'
			},{
				type: 'input',
				text: '李白2',
				title: '用户名'
			}, {
				type: 'input',
				text: '人生恰似一场修行人生恰似一场修行人生恰似一场修行',
				title: '测试'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '签名'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '测试1'
			}, {
				type: 'date',
				text: '2018-08-01',
				title: '日期'
			}, {
				type: 'dateRange',
				text: '2018-01-01 - 2018-03-03',
				title: '日期范围'
			}, {
				type: 'select',
				text: '福建省',
				value:'2',
				option: [{
					value:"0",
					text:'湖南省'
				},{
					value:"1",
					text:'浙江省'
				},{
					value:"2",
					text:'福建省'
				},{
					value:"3",
					text:'辽宁省'
				}],
				title: '选择框'
			}, {
				type: 'checkbox',
				text: 'checked',
				title: '复选框'
			}, {
				type: 'switch',
				text: 'ON',
				title: '开关'
			}, {
				type: 'radio',
				text: '男',
				value:'000',
					option:[
					{
						value:'000',
						text:'男'
					},
					{
						value:'0001',
						text:'女'
					}],
				title: '单选框'
			}],
			[{
				id:'000004'
			},{
				type: 'input',
				text: '李白2',
				title: '用户名'
			}, {
				type: 'input',
				text: '人生恰似一场修行人生恰似一场修行人生恰似一场修行',
				title: '测试'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '签名'
			}, {
				type: 'input',
				text: '人生恰似一场修行',
				msg:'ceshiciahsidas',
				title: '测试1'
			}, {
				type: 'date',
				text: '2018-08-01',
				title: '日期'
			}, {
				type: 'dateRange',
				text: '2018-01-01 - 2018-03-03',
				title: '日期范围'
			}, {
				type: 'select',
				text: '福建省',
				value:'2',
				option: [{
					value:"0",
					text:'湖南省'
				},{
					value:"1",
					text:'浙江省'
				},{
					value:"2",
					text:'福建省'
				},{
					value:"3",
					text:'辽宁省'
				}],
				title: '选择框'
			}, {
				type: 'checkbox',
				text: 'checked',
				title: '复选框'
			}, {
				type: 'switch',
				text: 'ON',
				title: '开关'
			}, {
				type: 'radio',
				text: '男',
				value:'000',
					option:[
					{
						value:'000',
						text:'男'
					},
					{
						value:'0001',
						text:'女'
					}],
				title: '单选框'
			}]
		]
	}
	var table = new Table();

	table.create(data, function(dataa) {
		//		修改后的数据
		console.log('cb')
		console.log(dataa)
	})

	

});