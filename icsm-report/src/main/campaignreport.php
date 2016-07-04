<?php
function exec_enabled() {
  $disabled = explode(', ', ini_get('disable_functions'));
  return !in_array('exec', $disabled);
}
?>


<?php
exec("curl http://localhost:8080/api/itunes/campaignapps > out.json")
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

                    $.getJSON('out.json', function(data)
                    {
			for (var i = 0; i < data.length; i++) {
				//alert(data[i].data[0].rank);
                		$('#week1-range').html(data[i].week1);
                		$('#week2-range').html(data[i].week2);
                		$('#week3-range').html(data[i].week3);
                		$('#week4-range').html(data[i].week4);

				var content = "";
				for (var x = 0; x < data[i].data.length; x++) {
					content += "<p>";
					content += "<div style=\"font-weight: bold;\">Data Pulled On:  " + data[i].data[x].timestamp + "</div><div style=\"font-weight: bold;\"><a href=\"http://localhost:8080/api/itunes/details?appId=" + data[i].data[x].appid + "\">" + data[i].data[x].name + "</a></div>&nbsp;&nbsp;";
					content += "<div style=\"font-weight: bold;\">Ranking: " + data[i].data[x].rank + "</div><div style=\"font-weight: bold;\">New:  " + data[i].data[x].newtop + "</div>";
					content += "<div style=\"font-weight: bold;\">Dropped: " + data[i].data[x].dropped + "</div>";
					content += "<a href=\"" + data[i].data[x].link + "\"><img src=\"" + data[i].data[x].image + "\"></a>";
					content += "<div style=\"font-weight: bold;\">Release Date:" + data[i].data[x].releasedate + "</div><div style=\"font-weight: bold;\">Category: " + data[i].data[x].category + "</div><div style=\"font-weight: bold;\">Company Name:";
					content += data[i].data[x].companyname + "</div><div style=\"font-weight: bold;\">Description</div><p>" + data[i].data[x].description + "</p>";
					content += "<div style=\"font-weight: bold;\">Campaign Active Date:  " + data[i].data[x].startdate + " to " + data[i].data[x].enddate + "</div><br>";
					content += "</p><br>";
				}

				$('#week' + (i+1) + '-report').html(content);
            		}
                    });

        });

    </script>

</head>
<body>

<div><h1 id="week1-range"></></div>
<div id="week1-report"></div>

<div><h1 id="week2-range"></></div>
<div id="week2-report"></div>

<div><h1 id="week3-range"></></div>
<div id="week3-report"></div>

<div><h1 id="week4-range"></></div>
<div id="week4-report"></div>

</body>
</html>
<!-- Copyright 2011 jQuery4u.com -->
