<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page isELIgnored="false" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

	<c:if test="${empty hours}">
		<c:set value="1" var="hours" />
	</c:if>

<!-- Copyright 2011 jQuery4u.com -->
<!DOCTYPE html>
<html>
<title>jQuery Function Demo - jQuery4u.com</title>
<head>
    <script src="http://code.jquery.com/jquery-latest.js"></script>    
<script type="text/javascript">
        $(document).ready(function(){
				$.getJSON('${ctx}/api/itunes/rewardsCampaignApps?hours=${hours}', function(data)
		                    {
								var content = "";
								for (var x = 0; x < data.length; x++) {
									content += "<tr class=\"highlight\">";
									content += "<td class=\"timestamp\"> " + data[x].timestamp + "</td><td><a href=\"" + data[x].link + "\"><img src=\"" + data[x].image + "\"  class=\"icon\"></a></td><td><a href=\"${ctx}/app/freedetails?appId=" + data[x].appid + "&hours=${hours}&lookBackHours=8\">" + data[x].name.substr(0,30) + "</a></td>";
									content += "<td class=\"ranking\"> " + data[x].rank + "</td><td class=\"new\">  " + data[x].newtop + "</td>";
									content += "<td class=\"dropped\"> " + data[x].dropped + "</td>";
									content += "<td class=\"moved\"> " + data[x].moved + "</td>";
									content += "<td class=\"moved\"> " + data[x].hastag + "</td>";
									//content += "<td><a href=\"" + data[x].link + "\"><img src=\"" + data[x].image + "\"  class=\"icon\"></a></td>";
									content += "<td class=\"companyname\"> " + data[x].companyname + "</td><td class=\"releasedate\"> " + data[x].releasedate + "</td><td class=\"category\"> " + data[x].category + "</td>";
									content += "<td class=\"featuredon\"> " + data[x].start_date + " to " + data[x].end_date + "</td>";
									content += "</tr>";
								}
								
								var copycontent = "<table id=\"newApps\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" width=\"100%\">";
								copycontent += "<tr class=\"top\"><td width=\"160\">Data Pulled On</td><td>Icon</td><td width=\"80\">App Name</td><td width=\"50\">Ranking</td><td width=\"50\">New</td><td width=\"50\">Dropped</td><td>Moved</td><td>Has Tag</td><td>Developer</td><td>Release Date</td><td>Category</td><td>Featured On</td></tr>";
								copycontent += content;
								copycontent += "</table>";
								
								$('#content').html(copycontent);
						});

		        });
    </script>

</head>
<body>


<div id="wrapper">
<div id="logo"><img src="http://admin.freeappaday.com/files/logo_faad.png" alt="FreeAppADay.com" title="FreeAppADay.com" width="158" height="59" /></div>
<h1>Featured Rewards Report</h1>
<h2><span style="display:block;"><img src="http://admin.freeappaday.com/files/icon_new.png" width="22" height="28" alt="Hot Apps" border="0" />Featured</span></h2>

<div class="tbl2container">
  <div class="contentTbl3">
  	<div id="content">
    
  	</div>

<!-- end .contentTbl3 -->
</div><!-- end .tbl2container -->
<div id="footer">© ICS Mobile 2012</div>
</div><!-- end #wrapper -->
<!--<script type="text/javascript" src="/formdate.js" /></script>-->
</body>
</html>    
