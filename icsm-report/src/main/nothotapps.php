<?php
function exec_enabled() {
  $disabled = explode(', ', ini_get('disable_functions'));
  return !in_array('exec', $disabled);
}
?>


<?php
exec("curl 'http://localhost:8080/api/itunes/getNotHotApps?moved=20&hours=24' > nothotapps.json")
?>

<!-- Copyright 2011 jQuery4u.com -->
<!DOCTYPE html>
<html>
<title>jQuery Function Demo - jQuery4u.com</title>
<head>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
<!--
        <script src="jquery-1.2.6.js"></script>

-->
    
<script type="text/javascript">

        $(document).ready(function(){

                    $.getJSON('nothotapps.json', function(data)
                    {
				var content = "";
				alert(data[0].name);
				for (var x = 0; x < data.length; x++) {
					content += "<p>";
					content += "<div style=\"font-weight: bold;\">Data Pulled On:  " + data[x].timestamp + "</div><div style=\"font-weight: bold;\"><a href=\"http://localhost:8080/api/itunes/details?appId=" + data[x].appid + "\">" + data[x].name + "</a></div>&nbsp;&nbsp;";
					content += "<div style=\"font-weight: bold;\">Ranking: " + data[x].rank + "</div><div style=\"font-weight: bold;\">New:  " + data[x].newtop + "</div>";
					content += "<div style=\"font-weight: bold;\">Movement: " + data[x].moved + "</div>";
					content += "<div style=\"font-weight: bold;\">Dropped: " + data[x].dropped + "</div>";
					content += "<a href=\"" + data[x].link + "\"><img src=\"" + data[x].image + "\"></a>";
					content += "<div style=\"font-weight: bold;\">Release Date:" + data[x].releasedate + "</div><div style=\"font-weight: bold;\">Category: " + data[x].category + "</div><div style=\"font-weight: bold;\">Company Name:";
					content += data[x].companyname + "</div>";
					content += "</p><br>";
				}

				$('#week1-report').html(content);
                    });

        });

    </script>

</head>
<body>

<div id="week1-report"></div>

</body>
</html>
<!-- Copyright 2011 jQuery4u.com -->
