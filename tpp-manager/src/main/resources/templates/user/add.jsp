<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<html lang=utf-8>
<head>
    <meta charset="utf-8">
    <title>新增用户</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${ResourcePath}/css/bootstrap-cerulean.min.css" rel="stylesheet">
    <link href="${ResourcePath}/css/charisma-app.css" rel="stylesheet">
    <link href='${ResourcePath}/bower_components/fullcalendar/dist/fullcalendar.css' rel='stylesheet'>
    <link href='${ResourcePath}/bower_components/fullcalendar/dist/fullcalendar.print.css' rel='stylesheet' media='print'>
    <link href='${ResourcePath}/bower_components/chosen/chosen.min.css' rel='stylesheet'>
    <link href='${ResourcePath}/bower_components/colorbox/example3/colorbox.css' rel='stylesheet'>
    <link href='${ResourcePath}/bower_components/responsive-tables/responsive-tables.css' rel='stylesheet'>
    <link href='${ResourcePath}/bower_components/bootstrap-tour/build/css/bootstrap-tour.min.css' rel='stylesheet'>
    <link href='${ResourcePath}/css/jquery.noty.css' rel='stylesheet'>
    <link href='${ResourcePath}/css/noty_theme_default.css' rel='stylesheet'>
    <link href='${ResourcePath}/css/elfinder.min.css' rel='stylesheet'>
    <link href='${ResourcePath}/css/elfinder.theme.css' rel='stylesheet'>
    <link href='${ResourcePath}/css/jquery.iphone.toggle.css' rel='stylesheet'>
    <link href='${ResourcePath}/css/uploadify.css' rel='stylesheet'>
    <link href='${ResourcePath}/css/animate.min.css' rel='stylesheet'>

    <!-- jQuery -->
    <script src="${ResourcePath}/bower_components/jquery/jquery.min.js"></script>

    <!-- The fav icon -->
    <link rel="shortcut icon" href="${ResourcePath}/img/favicon.ico">
</head>

<body>
    <!-- topbar starts -->
 	<jsp:include   page="../public/head.jsp" flush="true"/> 
    <!-- topbar ends -->
    
	<div class="ch-container">
    	<div class="row">
    	
			<jsp:include   page="../public/left.jsp" flush="true"/>
			 
        	<div id="content" class="col-lg-10 col-sm-10">
            
            <div>
	    		<ul class="breadcrumb">
	        		<li>
	            		<a href="#">主页</a>
	        		</li>
	        		<li>
	            		<a href="#">新增用户</a>
	        		</li>
	    		</ul>
			</div>
			
			<div class="row">
			    <div class="box col-md-12">
			        <div class="box-inner">
			            <div class="box-header well" data-original-title="">
			                <h2><i class="glyphicon glyphicon-edit"></i>用户信息</h2>
			
			                <div class="box-icon">
			                    <a href="#" class="btn btn-setting btn-round btn-default"><i
			                            class="glyphicon glyphicon-cog"></i></a>
			                    <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                            class="glyphicon glyphicon-chevron-up"></i></a>
			                    <a href="#" class="btn btn-close btn-round btn-default"><i
			                            class="glyphicon glyphicon-remove"></i></a>
			                </div>
			            </div>
			            <div class="box-content">
			                <form role="form"  action="${pageContext.request.contextPath }/user/add.do" method="post"  enctype="multipart/form-data">
			                	<div class="form-group">
			                        <label for="username">用户名</label>
			                        <input type="username" class="form-control" id="username" name="username" placeholder="Enter username">
			                    </div>
			                    <div class="form-group">
			                        <label for="email">邮件</label>
			                        <input type="email" class="form-control" id="email"  name="email" placeholder="Enter email">
			                    </div>
			                    <div class="form-group">
			                        <label for="password">密码</label>
			                        <input type="password" class="form-control" id="password"  name="password" placeholder="Enter Password">
			                    </div>
			                    <div class="form-group">
			                        <label for="headimage">头像</label>
			                        <input type="file" name="headimage" id="headimage">
			                    </div>
			                    <button type="submit" class="btn btn-default">提交</button>
			                </form>
			
			            </div>
			        </div>
			    </div>
			    <!--/span-->
			</div><!--/row-->


	<jsp:include   page="../public/foot.jsp" flush="true"/> 

</div>

<!-- external javascript -->
<script src="${ResourcePath}/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- library for cookie management -->
<script src="${ResourcePath}/js/jquery.cookie.js"></script>
<!-- calender plugin -->
<script src='${ResourcePath}/bower_components/moment/min/moment.min.js'></script>
<script src='${ResourcePath}/bower_components/fullcalendar/dist/fullcalendar.min.js'></script>
<!-- data table plugin -->
<script src='${ResourcePath}/js/jquery.dataTables.min.js'></script>
<!-- select or dropdown enhancer -->
<script src="${ResourcePath}/bower_components/chosen/chosen.jquery.min.js"></script>
<!-- plugin for gallery image view -->
<script src="${ResourcePath}/bower_components/colorbox/jquery.colorbox-min.js"></script>
<!-- notification plugin -->
<script src="${ResourcePath}/js/jquery.noty.js"></script>
<!-- library for making tables responsive -->
<script src="${ResourcePath}/bower_components/responsive-tables/responsive-tables.js"></script>
<!-- tour plugin -->
<script src="${ResourcePath}/bower_components/bootstrap-tour/build/js/bootstrap-tour.min.js"></script>
<!-- star rating plugin -->
<script src="${ResourcePath}/js/jquery.raty.min.js"></script>
<!-- for iOS style toggle switch -->
<script src="${ResourcePath}/js/jquery.iphone.toggle.js"></script>
<!-- autogrowing textarea plugin -->
<script src="${ResourcePath}/js/jquery.autogrow-textarea.js"></script>
<!-- multiple file upload plugin -->
<script src="${ResourcePath}/js/jquery.uploadify-3.1.min.js"></script>
<!-- history.js for cross-browser state change on ajax -->
<script src="${ResourcePath}/js/jquery.history.js"></script>
<!-- application script for Charisma demo -->
<script src="${ResourcePath}/js/charisma.js"></script>

</body>
</html>
