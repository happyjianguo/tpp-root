	window.onload = function(){
		if(localStorage.getItem('sortTab')){
			var sortArr = JSON.parse(localStorage.getItem('sortTab'));
			for(var i = 0; i<sortArr.length;i++){
				$('#indexsortable').append($('#'+sortArr[i]))
			}
		}
	}
	
	$("#indexsortable").sortable({
		placeholder: "ui-state-highlight",
		stop: function(event, ui) {
			var arr123 = $("#indexsortable").sortable('toArray');
				localStorage.setItem('sortTab',JSON.stringify(arr123))
		}

	});
	// 基于准备好的dom，初始化echarts实例
	var myChart1 =document.getElementsByClassName('main')[0];
	//自适应宽高
	var myChartContainer = function () {
	    myChart1.style.width = window.innerWidth-50+'px';
	};
	myChartContainer();
 	myChart =  echarts.init(myChart1)
	// 指定图表的配置项和数据
	option = {
		title: {
			text: '折线图堆叠'
		},
		tooltip: {
			trigger: 'axis'
		},
		grid: {
			left: '3%',
			right: '4%',
			bottom: '3%',
			containLabel: true
		},
		xAxis: {
			type: 'category',
			boundaryGap: false,
			data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
		},
		yAxis: {
			type: 'value'
		},
		series: [{
				name: '邮件营销',
				type: 'line',
				stack: '总量',
				data: [120, 132, 101, 134, 90, 230, 210],
				type: 'line',
				smooth: true	
			},
			{
				name: '联盟广告',
				type: 'line',
				stack: '总量',
				data: [220, 182, 191, 234, 290, 330, 310],
				type: 'line',
				smooth: true
			},
			{
				name: '视频广告',
				type: 'line',
				stack: '总量',
				data: [150, 232, 201, 154, 190, 330, 410],
				type: 'line',
				smooth: true
			},
			{
				name: '直接访问',
				type: 'line',
				stack: '总量',
				data: [320, 332, 301, 334, 390, 330, 320],
				type: 'line',
				smooth: true
			},
			{
				name: '搜索引擎',
				type: 'line',
				stack: '总量',
				data: [820, 932, 901, 934, 1290, 1330, 1320],
				type: 'line',
				smooth: true
			}
		]

	};
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
	//浏览器大小改变时重置大小
	 window.onresize = function () {
	    myChartContainer();
	    myChart.resize();
	};
	//	日期插件
	layui.use('laydate', function() {
		var laydate = layui.laydate;
		laydate.render({
			elem: '#test2',
			position: 'static',
			max: (new Date()).toLocaleDateString().replace(/\//g,'-'),
			change: function(value, date) { //监听日期被切换
				console.log(value);
				console.log(date);
			}
		});
	})
	
//初始化表格
var data= {
		ele:'idnexTable',
		isSortBox:false,
		editor:false,
		checked:false,
		head:[{
			type: 'input',
			title: '用户名',
			sort: '',
		},{
			type: 'input',
			title: '待办',
			sort: '',
		},{
			type: 'input',
			title: '待办123',
			sort: '',
		},{
			type: 'input',
			title: '待办11123',
			sort: '',
		}],
		body:[
		[{
				id:'000000'
			},{
			type: 'input',
			text: '人生恰似一人生恰似一场修行人生恰似一场修行场修行',
			msg:'ceshiciahsidas',
			title: '用户名'
		},{
			type: 'input',
			text: '人生恰似一人生恰似一场修行人生恰似一场修行场修行',
			msg:'ceshiciahsidas',
			title: '待办'
		},{
			type: 'input',
			text: '人生恰似一场修行人生恰似一场修行',
			msg:'ceshiciahsidas',
			title: '待办123'
		},{
			type: 'input',
			text: '人生恰似一场修行人生恰似一场修行人生恰似一场修行人生恰似一场修行',
			msg:'ceshiciahsidas',
			title: '待办11123'
		},
		],[{
				id:'000000'
			},{
			type: 'input',
			text: '人生恰似一人生恰似一场修行人生恰似一场修行场修行',
			msg:'ceshiciahsidas',
			title: '用户名'
		},{
			type: 'input',
			text: '人生恰似一人生恰似一场修行人生恰似一场修行场修行',
			msg:'ceshiciahsidas',
			title: '待办'
		},{
			type: 'input',
			text: '人生恰似一场修行人生恰似一场修行',
			msg:'ceshiciahsidas',
			title: '待办123'
		},{
			type: 'input',
			text: '人生恰似一场修行人生恰似一场修行人生恰似一场修行人生恰似一场修行',
			msg:'ceshiciahsidas',
			title: '待办11123'
		},
		],[{
				id:'000000'
			},{
			type: 'input',
			text: '人生恰似一人生恰似一场修行人生恰似一场修行场修行',
			msg:'ceshiciahsidas',
			title: '用户名'
		},{
			type: 'input',
			text: '人生恰似一人生恰似一场修行人生恰似一场修行场修行',
			msg:'ceshiciahsidas',
			title: '待办'
		},{
			type: 'input',
			text: '人生恰似一场修行人生恰似一场修行',
			msg:'ceshiciahsidas',
			title: '待办123'
		},{
			type: 'input',
			text: '人生恰似一场修行人生恰似一场修行人生恰似一场修行人生恰似一场修行',
			msg:'ceshiciahsidas',
			title: '待办11123'
		},
		],[{
				id:'000000'
			},{
			type: 'input',
			text: '人生恰似一人生恰似一场修行人生恰似一场修行场修行',
			msg:'ceshiciahsidas',
			title: '用户名'
		},{
			type: 'input',
			text: '人生恰似一人生恰似一场修行人生恰似一场修行场修行',
			msg:'ceshiciahsidas',
			title: '待办'
		},{
			type: 'input',
			text: '人生恰似一场修行人生恰似一场修行',
			msg:'ceshiciahsidas',
			title: '待办123'
		},{
			type: 'input',
			text: '人生恰似一场修行人生恰似一场修行人生恰似一场修行人生恰似一场修行',
			msg:'ceshiciahsidas',
			title: '待办11123'
		},
		],]
}
var table = new Table();

	table.create(data, function(data) {
		//		修改后的数据
		console.log('cb')
		console.log(data)
	})
