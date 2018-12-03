<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<html lang=utf-8>
<head>
    <meta charset="utf-8">
    <title>Free HTML5 Bootstrap Admin Template</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="../css/bootstrap-cerulean.min.css" rel="stylesheet">
    <link href="../css/charisma-app.css" rel="stylesheet">
    <link href='../bower_components/fullcalendar/dist/fullcalendar.css' rel='stylesheet'>
    <link href='../bower_components/fullcalendar/dist/fullcalendar.print.css' rel='stylesheet' media='print'>
    <link href='../bower_components/chosen/chosen.min.css' rel='stylesheet'>
    <link href='../bower_components/colorbox/example3/colorbox.css' rel='stylesheet'>
    <link href='../bower_components/responsive-tables/responsive-tables.css' rel='stylesheet'>
    <link href='../bower_components/bootstrap-tour/build/css/bootstrap-tour.min.css' rel='stylesheet'>
    <link href='../css/jquery.noty.css' rel='stylesheet'>
    <link href='../css/noty_theme_default.css' rel='stylesheet'>
    <link href='../css/elfinder.min.css' rel='stylesheet'>
    <link href='../css/elfinder.theme.css' rel='stylesheet'>
    <link href='../css/jquery.iphone.toggle.css' rel='stylesheet'>
    <link href='../css/uploadify.css' rel='stylesheet'>
    <link href='../css/animate.min.css' rel='stylesheet'>

    <!-- jQuery -->
    <script src="../bower_components/jquery/jquery.min.js"></script>

    <!-- The fav icon -->
    <link rel="shortcut icon" href="../img/favicon.ico">
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
	            		<a href="#">Home</a>
	        		</li>
	        		<li>
	            		<a href="#">Dashboard</a>
	        		</li>
	    		</ul>
			</div>

			<div class="row">
			    <div class="box col-md-12">
			    	<div class="box-inner">
			    		<div class="box-header well" data-original-title="">
				        	<h2><i class="glyphicon glyphicon-user"></i>普通用户管理</h2>
				        	<div class="box-icon">
				            	<a href="#" class="btn btn-setting btn-round btn-default"><i class="glyphicon glyphicon-cog"></i></a>
				            	<a href="#" class="btn btn-minimize btn-round btn-default"><i class="glyphicon glyphicon-chevron-up"></i></a>
				            	<a href="#" class="btn btn-close btn-round btn-default"><i class="glyphicon glyphicon-remove"></i></a>
				        	</div>
			    		</div>
				    	<div class="box-content">
				    		<table class="table table-striped table-bordered bootstrap-datatable datatable responsive">
				    			<thead>
								    <tr>
								        <th>用户名</th>
								        <th>注册日期</th>
								        <th>角色</th>
								        <th>状态</th>
								        <th>操作</th>
								    </tr>
				    			</thead>
				    			<tbody>
				    				<c:forEach var="i" begin="1" end="19">
								    <tr>
								        <td>周勇沩${i}</td>
								        <td class="center">2012/01/01</td>
								        <td class="center">录入员</td>
								        <td class="center">
								            <span class="label-success label label-default">激活</span>
								        </td>
								        <td class="center">
								            <a class="btn btn-success" href="#">
								                <i class="glyphicon glyphicon-zoom-in icon-white"></i>
								                查看
								            </a>
								            <a class="btn btn-info" href="#">
								                <i class="glyphicon glyphicon-edit icon-white"></i>
								                修改
								            </a>
								            <a class="btn btn-danger" href="#">
								                <i class="glyphicon glyphicon-trash icon-white"></i>
								                删除
								            </a>
								        </td>
								    </tr>
								    </c:forEach>
				    			</tbody>
				    		</table>
				    	</div>
			    	</div>
				</div>
			</div><!--/row-->


	<jsp:include   page="../public/foot.jsp" flush="true"/> 

</div>

<!-- external javascript -->
<script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- library for cookie management -->
<script src="../js/jquery.cookie.js"></script>
<!-- calender plugin -->
<script src='../bower_components/moment/min/moment.min.js'></script>
<script src='../bower_components/fullcalendar/dist/fullcalendar.min.js'></script>
<!-- data table plugin -->
<script src='../js/jquery.dataTables.min.js'></script>
<!-- select or dropdown enhancer -->
<script src="../bower_components/chosen/chosen.jquery.min.js"></script>
<!-- plugin for gallery image view -->
<script src="../bower_components/colorbox/jquery.colorbox-min.js"></script>
<!-- notification plugin -->
<script src="../js/jquery.noty.js"></script>
<!-- library for making tables responsive -->
<script src="../bower_components/responsive-tables/responsive-tables.js"></script>
<!-- tour plugin -->
<script src="../bower_components/bootstrap-tour/build/js/bootstrap-tour.min.js"></script>
<!-- star rating plugin -->
<script src="../js/jquery.raty.min.js"></script>
<!-- for iOS style toggle switch -->
<script src="../js/jquery.iphone.toggle.js"></script>
<!-- autogrowing textarea plugin -->
<script src="../js/jquery.autogrow-textarea.js"></script>
<!-- multiple file upload plugin -->
<script src="../js/jquery.uploadify-3.1.min.js"></script>
<!-- history.js for cross-browser state change on ajax -->
<script src="../js/jquery.history.js"></script>
<!-- application script for Charisma demo -->
<script src="../js/charisma.js"></script>

</body>
</html>
