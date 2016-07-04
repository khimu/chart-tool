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
	<link rel="stylesheet" type="text/css" href="http://admin.freeappaday.com/files/top300Chart.css" />
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    
<script type="text/javascript">
        $(document).ready(function(){
             $.getJSON('${ctx}/api/itunes/byId?appId=${appId}&hours=${hours}&lookBackHours=${lookBackHours}', function(data)
                     {
                 var ranking = "";
                 var headline = "";
                 
                 for (var x = 0; x < data.length; x++) {
                         headline = "<h2>" + data[x].name + "</h2>&nbsp;&nbsp;";
                         headline += "<h3>" + data[x].companyname + "</h3>";

                         ranking += "<tr class=\"highlight\"><td>" + data[x].timestamp + "</td><td>" + data[x].rank + "</td><td>" + data[x].moved + "</td></tr>";
                 }
                 var content = "";
                 content += headline;
                 content += "<table cellpadding=\"3\" cellspacing=\"0\">";
                 content += "<tr class=\"top\"><td class=\"timestamp\">Pulled Date</span></td><td class=\"ranking\">Ranking</td><td>Movement (Hours)</td></tr>";
                 content += ranking;
                 content += "</table>";

                 $('#week1-report').html(content);
     		});
		});
</script>
</head>
<body>

<div id="wrapperdetails">
        <div id="logo">
                <img width="80" height="29" title="FreeAppADay.com" alt="FreeAppADay.com" src="http://admin.freeappaday.com/files/logo_faad.png">
        </div>
<h1>Free Chart Details</h1>
<p><a href="${ctx}/app/campaign_report_free?hours=${hours}"><span class="back"><</span> BACK</a></p>
<div class="tbl2container">
  <div class="contentTbl3">
    <div id="content">
      <div id="week1-report"></div>
    </div>
  </div>
<!-- end #contentTbl3 -->
</div><!-- end #tbl2container -->
<div id="footer">© ICS Mobile 2012</div>
</div><!-- end #wrapper -->

</body>
</html>
<!-- Copyright 2011 jQuery4u.com -->
