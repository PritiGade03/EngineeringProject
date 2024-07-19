<%@page import="com.twitter.beans.TweetStream"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.ResultSet" %>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setDateHeader("Expires", 0); // Proxies.
if(session!=null){
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Terrorist Activities Detection via Social Media Using Machine Learning</title>
    <!-- The styles -->
    <link id="bs-css" href="css/bootstrap-cerulean.min.css" rel="stylesheet">

    <link href="css/charisma-app.css" rel="stylesheet">
    <link href='bower_components/fullcalendar/dist/fullcalendar.css' rel='stylesheet'>
    <link href='bower_components/fullcalendar/dist/fullcalendar.print.css' rel='stylesheet' media='print'>
    <link href='bower_components/chosen/chosen.min.css' rel='stylesheet'>
    <link href='bower_components/colorbox/example3/colorbox.css' rel='stylesheet'>
    <link href='bower_components/responsive-tables/responsive-tables.css' rel='stylesheet'>
    <link href='bower_components/bootstrap-tour/build/css/bootstrap-tour.min.css' rel='stylesheet'>
    <link href='css/jquery.noty.css' rel='stylesheet'>
    <link href='css/noty_theme_default.css' rel='stylesheet'>
    <link href='css/elfinder.min.css' rel='stylesheet'>
    <link href='css/elfinder.theme.css' rel='stylesheet'>
    <link href='css/jquery.iphone.toggle.css' rel='stylesheet'>
    <link href='css/uploadify.css' rel='stylesheet'>
    <link href='css/animate.min.css' rel='stylesheet'>
	<link href='css/own.css' rel='stylesheet'>
    <!-- jQuery -->
    <script src="bower_components/jquery/jquery.min.js"></script>
	<script src="validation.js"></script>
    <!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- The fav icon -->
    <link rel="shortcut icon" href="img/favicon.ico">

</head>

<body>
    <!-- topbar starts -->
    <div class="navbar navbar-default" role="navigation">

        <div class="navbar-inner">
            <button type="button" class="navbar-toggle pull-left animated flip">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="home.jsp"> <img alt="Twitter Logo" src="img/twitterlogo.png" class="hidden-xs"/>
                <span>Social Media</span></a>
			<h2 style="color:white" class="collapse navbar-collapse nav navbar-nav top-menu" align="center">Terrorist Activities Detection via Social Media Using Machine Learning </h2>
           
           	<!-- user dropdown starts -->
            <div class="btn-group pull-right">
                <button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <i class="glyphicon glyphicon-user"></i><span class="hidden-sm hidden-xs"></span>
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li><a href="admin_changepassword.jsp">Change Password</a></li>
                    
                    <li class="divider"></li>
                    <li><a href="AdminLogoutController">Logout</a></li>
                </ul>
            </div>
            <!-- user dropdown ends -->

           
        </div>
    </div>
    <!-- topbar ends -->
    
   		                                
<div class="ch-container">
    <div class="row">
        
        <!-- left menu starts -->
        <div class="col-sm-2 col-lg-2">
            <div class="sidebar-nav">
                <div class="nav-canvas">
                    <div class="nav-sm nav nav-stacked">

                    </div>
                    <!-- <div><img alt="Profile Pic" src="profilepic.jsp" class="hidden-xs"/> -->
                    <div>
            <%
			String emailMsg = (String)session.getAttribute("emailMsg");
            //String name=(String)session.getAttribute("Server");
			if(emailMsg!=null && emailMsg!=""){
			%>
                                <div><strong><%=emailMsg %></strong></div>
            <%} %>
                    </div>
                    <ul class="nav nav-pills nav-stacked main-menu">
                        <li class="nav-header">Main</li>
                        <li><a class="ajax-link" href="admin_home.jsp"><i class="glyphicon glyphicon-home"></i><span> Home</span></a>
                        </li>
                        <li><a class="ajax-link" href="AdminUserController"><i class="glyphicon glyphicon-user"></i><span> List Users</span></a>
                        </li>
                        <li><a class="ajax-link" href="AdminTweetViewController"><i
                                    class="glyphicon glyphicon-eye-open"></i><span> View Tweets</span></a></li>
                       <li><a class="ajax-link" href="admin_upload.jsp"><i
                                    class="glyphicon glyphicon-eye-open"></i><span> Upload Dataset</span></a></li>
                      
                        <li><a class="ajax-link" href="graph7.jsp"><i
                                    class="glyphicon glyphicon-eye-open"></i><span> Daywise Graph</span></a></li>        
                        <li><a class="ajax-link" href="TweetClassificationController"><i
                                    class="glyphicon glyphicon-eye-open"></i><span> Tweet Classification</span></a></li>
                       
                    </ul>
                    
                </div>
            </div>
        </div>
        <!--/span-->
        <!-- left menu ends -->

        
<div class="row">        
    	
        <div class="col-md-8 center login-header">
            <h2 align="center">List of Tweets from Offline Twitter Streaming API</h2>
        </div>
        <!--/span-->
    
    <div class="row">
        <div class="well col-md-9  login-box">
        
            <div class="alert alert-info">
                List of Tweets
            </div>
            <a href="AdminSpamDetectController" class="btn btn-primary">Classify</a>
            <table class="table table-striped table-bordered  responsive">
   			<thead>
    		<tr>
        		<th>User Name</th>
        		<th>Nick Name</th>
       			<th>Tweet Contents</th>
        		<th>Date-Time</th>
       			<th>Country</th>
       			<th>Place</th>
       			<th>Followers</th>
       			<th>Followings</th>
       			<th>Tweet URL</th>
    		</tr>
    		</thead>
    		<tbody>
    		<%
    			ArrayList<TweetStream> tweetStreamList = (ArrayList<TweetStream>)session.getAttribute("TweetStream");
    			if(tweetStreamList!=null && tweetStreamList.size()>0){
    				for(int i=0;i<tweetStreamList.size();i++){
    		%>
    		<tr>
    			<th><%=tweetStreamList.get(i).getUserName() %></th>
        		<th><%=tweetStreamList.get(i).getNickname() %></th>
       			<th><%=tweetStreamList.get(i).getTweet_content() %></th>
        		<th><%=tweetStreamList.get(i).getDate()+" "+tweetStreamList.get(i).getHour() %></th>
        		<th><%=tweetStreamList.get(i).getCountry() %></th>
        		<th><%=tweetStreamList.get(i).getPlace() %></th>
        		<th><%=tweetStreamList.get(i).getFollowers() %></th>
        		<th><%=tweetStreamList.get(i).getFollowing() %></th>
        		<th><%=tweetStreamList.get(i).getTweet_Url() %></th>
       			
    		</tr>
    		<%		} 
    			}
    		%>
    		</tbody>
    		</table>
        </div>
        <!--/span-->
        
    </div><!--/row-->
    <!-- content ends -->
    </div><!--/#content.col-md-0-->

    
</div><!--/fluid-row-->
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<hr>

   

</div><!--/.fluid-container-->

<!-- external javascript -->

<script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

<!-- library for cookie management -->
<script src="js/jquery.cookie.js"></script>
<!-- calender plugin -->
<script src='bower_components/moment/min/moment.min.js'></script>
<script src='bower_components/fullcalendar/dist/fullcalendar.min.js'></script>
<!-- data table plugin -->
<script src='js/jquery.dataTables.min.js'></script>

<!-- select or dropdown enhancer -->
<script src="bower_components/chosen/chosen.jquery.min.js"></script>
<!-- plugin for gallery image view -->
<script src="bower_components/colorbox/jquery.colorbox-min.js"></script>
<!-- notification plugin -->
<script src="js/jquery.noty.js"></script>
<!-- library for making tables responsive -->
<script src="bower_components/responsive-tables/responsive-tables.js"></script>
<!-- tour plugin -->
<script src="bower_components/bootstrap-tour/build/js/bootstrap-tour.min.js"></script>
<!-- star rating plugin -->
<script src="js/jquery.raty.min.js"></script>
<!-- for iOS style toggle switch -->
<script src="js/jquery.iphone.toggle.js"></script>
<!-- autogrowing textarea plugin -->
<script src="js/jquery.autogrow-textarea.js"></script>
<!-- multiple file upload plugin -->
<script src="js/jquery.uploadify-3.1.min.js"></script>
<!-- history.js for cross-browser state change on ajax -->
<script src="js/jquery.history.js"></script>
<!-- application script for Charisma demo -->
<script src="js/charisma.js"></script>


</body>
</html>
<%
}else{
	response.sendRedirect("admin.jsp");
}
%>

