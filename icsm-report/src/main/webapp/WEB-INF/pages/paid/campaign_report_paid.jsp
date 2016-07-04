<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page isELIgnored="false" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>


	<c:if test="${empty hours}">
		<c:set value="1" var="hours" />
	</c:if>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Campaign Report Paid</title>
<link rel="stylesheet" type="text/css" href="http://admin.freeappaday.com/files/top300Chart.css" />
<script src="http://code.jquery.com/jquery-latest.js"></script>

<script type="text/javascript">
	
$(document).ready(function(){
                    $.getJSON('${ctx}/api/paid/top300?hours=${hours}', function(data)
                    {
						var content = "";
						for (var x = 0; x < data.length; x++) {
							content += "<tr>";
							content += "<td class=\"timestamp highlight\">" + data[x].timestamp + "</td><td class=\"appid highlight\"><a href=\"" + data[x].link + "\"><img src=\"" + data[x].image + "\" class=\"icon\"></a></td><td class=\"appid highlight\"><a href=\"${ctx}/app/paiddetails?appId=" + data[x].appid + "&hours=${hours}&lookBackHours=8\">" + data[x].name.substr(0,28) + "</a></td>";
							content += "<td class=\"ranking highlight\">" + data[x].rank + "</td><td class=\"new highlight\">  " + data[x].newtop + "</td>";
							content += "<td class=\"dropped highlight\">" + data[x].dropped + "</td>";
							content += "<td class=\"moved highlight\">" + data[x].moved + "</td>";
							content += "<td class=\"companyname highlight\">" + data[x].companyname.substr(0,28) + "</td><td class=\"releasedate highlight\">" + data[x].releasedate + "</td><td class=\"category highlight\">" + data[x].category + "</td>";
							content += "</tr>";
						}
						
						var copycontent = "<table id=\"newApps\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" width=\"100%\">";
						copycontent += "<tr class=\"top\"><td class=\"timestamp\">Pull Date</td><td>Icon</td><td width=\"80\">App Name</td><td width=\"40\">Rank</td><td width=\"40\">New</td><td width=\"50\">Dropped</td><td>Moved</td><td>Company</td><td>Released On</td><td>Category</td></tr>";
						copycontent += content;
						copycontent += "</table>";
						
						$('#content').html(copycontent);
				});

        });
	
</script>
<!--Hot Apps -->
<script type="text/javascript">
	
$(document).ready(function(){
						

                    $.getJSON('${ctx}/api/paid/hot?moved=20&hours=${hours}', function(data)
                    {
						var content2 = "";
						for (var x = 0; x < data.length; x++) {
							content2 += "<tr>";
							content2 += "<td class=\"timestamp highlight\">" + data[x].timestamp + "</td><td class=\"appid highlight\"><a href=\"" + data[x].link + "\"><img src=\"" + data[x].image + "\" class=\"icon\"></a></td><td class=\"appid highlight\"><a href=\"${ctx}/app/paiddetails?appId=" + data[x].appid + "&hours=${hours}&lookBackHours=8\">" + data[x].name.substr(0,28) + "</a></td>";
							content2 += "<td class=\"ranking highlight\">" + data[x].rank + "</td><td class=\"new highlight\">" + data[x].newtop + "</td>";
							content2 += "<td class=\"dropped highlight\">" + data[x].dropped + "</td>";
							content2 += "<td class=\"moved highlight\">" + data[x].moved + "</td>";
							content2 += "<td class=\"companyname highlight\">" + data[x].companyname.substr(0,28) + "</td><td class=\"releasedate highlight\">" + data[x].releasedate + "</td><td class=\"category highlight\">" + data[x].category + "</td>";
							content2 += "</tr>";
						}
						
						var copycontent2 = "<table id=\"newApps\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" width=\"100%\">";
						copycontent2 += "<tr class=\"top\"><td class=\"timestamp\">Pull Date</td><td>Icon</td><td width=\"80\">App Name</td><td width=\"40\">Rank</td><td width=\"40\">New</td><td width=\"50\">Dropped</td><td>Moved</td><td>Company</td><td>Released On</td><td>Category</td></tr>";
						copycontent2 += content2;
						copycontent2 += "</table>";
						
						$('#content2').html(copycontent2);
				});

        });
	
</script>
<!--Rewards -->
<script type="text/javascript">
	
$(document).ready(function(){
						

                    $.getJSON('${ctx}/api/paid/new?fallInNumber=25&hours=${hours}', function(data)
                    {
						var content3 = "";
						for (var x = 0; x < data.length; x++) {
							content3 += "<tr>";
							content3 += "<td class=\"timestamp highlight\">" + data[x].timestamp + "</td><td class=\"appid highlight\"><a href=\"" + data[x].link + "\"><img src=\"" + data[x].image + "\" class=\"icon highlight\"></a></td><td class=\"appid highlight\"><a href=\"${ctx}/app/paiddetails?appId=" + data[x].appid + "&hours=${hours}&lookBackHours=8\">" + data[x].name.substr(0,28) + "</a></td>";
							content3 += "<td class=\"ranking highlight\">" + data[x].rank + "</td><td class=\"new highlight\">" + data[x].newtop + "</td>";
							content3 += "<td class=\"dropped highlight\">" + data[x].dropped + "</td>";
							content3 += "<td class=\"moved highlight\">" + data[x].moved + "</td>";
							content3 += "<td class=\"companyname highlight\">" + data[x].companyname.substr(0,28) + "</td><td class=\"releasedate highlight\">" + data[x].releasedate + "</td><td class=\"category highlight\">" + data[x].category + "</td>";
							content3 += "<td class=\"hastag highlight\">" + data[x].hastag + "</td>";
							content3 += "</tr>";
						}
						
						var copycontent3 = "<table id=\"newApps\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" width=\"100%\">";
						copycontent3 += "<tr class=\"top\"><td class=\"timestamp\">Pull Date</td><td>Icon</td><td width=\"80\">App Name</td><td width=\"40\">Rank</td><td width=\"40\">New</td><td width=\"50\">Dropped</td><td>Moved</td><td>Company</td><td>Released On</td><td>Category</td><td>Has Tag</td></tr>";
						copycontent3 += content3;
						copycontent3 += "</table>";
						
						$('#content3').html(copycontent3);
				});

        });
	
</script>
<script type="text/javascript">
	
$(document).ready(function(){
						
                    $.getJSON('${ctx}/api/paid/nothot?moved=25&hours=${hours}', function(data)
                    {
						var content4 = "";
				
						for (var x = 0; x < data.length; x++) {
							content4 += "<tr>";
							content4 += "<td class=\"timestamp highlight\">" + data[x].timestamp + "</td><td class=\"appid highlight\"><a href=\"" + data[x].link + "\"><img src=\"" + data[x].image + "\" class=\"icon\"></a></td><td class=\"appid highlight\"><a href=\"${ctx}/app/paiddetails?appId=" + data[x].appid + "&hours=${hours}&lookBackHours=8\">" + data[x].name.substr(0,28) + "</a></td>";
							content4 += "<td class=\"ranking highlight\">" + data[x].rank + "</td><td class=\"new highlight\">" + data[x].newtop + "</td>";
							content4 += "<td class=\"dropped highlight\">" + data[x].dropped + "</td>";
							content4 += "<td class=\"moved highlight\">" + data[x].moved + "</td>";
							content4 += "<td class=\"companyname highlight\">" + data[x].companyname.substr(0,28) + "</td><td class=\"releasedate highlight\">" + data[x].releasedate + "</td><td class=\"category highlight\">" + data[x].category + "</td>";
							content4 += "<td class=\"hastag highlight\">" + data[x].hastag + "</td>";
							content4 += "<td class=\"alreadytargeted highlight\">" + data[x].alreadytargeted + "</td>";
							content4 += "<td class=\"itunesfeaturedapp highlight\">" + data[x].itunesfeaturedapp + "</td>";
							content4 += "</tr>";	
						}
						
						var copycontent4 = "<table id=\"newApps\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" width=\"100%\">";
						copycontent4 += "<tr class=\"top\"><td>Pull Date</td><td>Icon</td><td width=\"80\">App Name</td><td>Rank</td><td>New</td><td>Dropped</td><td>Moved</td><td>Company</td><td>Released On</td><td>Category</td><td>Has Tag</td><td>Already Targeted</td><td>Featured App</td></tr>";
						copycontent4 += content4;
						copycontent4 += "</table>";
						
						$('#content4').html(copycontent4);
				});

        });
	
</script>

</head>
<body>

<div id="wrapper">
	<div id="logo">
		<img width="158" height="59" title="FreeAppADay.com" alt="FreeAppADay.com" src="http://admin.freeappaday.com/files/logo_faad.png">
	</div>
<h1>Campaign Reports: &nbsp;&nbsp;&nbsp;&nbsp;<a href="${ctx}/app/campaign_report_paid?hours=1">Paid</a> <a href="${ctx}/app/campaign_report_free?hours=1">Free</a></h1>

<h2><span class="block"><img src="http://admin.freeappaday.com/files/icon_top300.png" width="22" height="28" alt="Top 300" border="0" />Top 300</span></h2>
<div class="tbl2container">
  <div class="contentTbl3">
    <div id="content">
    </div>
  </div>
  
<h2><span class="block"><img src="http://admin.freeappaday.com/files/icon_hot.png" width="22" height="28" alt="Hot Apps" border="0" />Hot Apps</span></h2>
	<div class="contentTbl3">
  	  <div id="content2">
      </div>
    </div>

<h2><span class="block"><img src="http://admin.freeappaday.com/files/icon_new.png" width="22" height="28" alt="New to Top 300" border="0" />New To Top X</span></h2>
	<div class="contentTbl3">
  	  <div id="content3">
      </div>
    </div>
    
<h2><span class="block"><img src="http://admin.freeappaday.com/files/icon_not.png" width="22" height="28" alt="Not Hot Apps" border="0" />Not Hot Apps</span></h2>
	<div class="contentTbl3">
  	  <div id="content4">
      </div>
    </div>
<!-- end #camp -->
</div><!-- end #tbl2container -->
<div id="footer">© ICS Mobile 2012</div>
</div><!-- end #wrapper -->
</body>
</html>