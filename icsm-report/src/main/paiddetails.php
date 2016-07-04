<?php
function exec_enabled() {
  $disabled = explode(', ', ini_get('disable_functions'));
  return !in_array('exec', $disabled);
}
?>


<?php

$appId = $_REQUEST["appId"];

exec("curl 'http://localhost:8080/icsm-report-1.0/api/paid/byId?appId=$appId&hours=0&lookBackHours=8' > paiddetails.json")
?>

<!-- Copyright 2011 jQuery4u.com -->
<!DOCTYPE html>
<html>
<title>jQuery Function Demo - jQuery4u.com</title>
<head>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    
<script type="text/javascript">

        $(document).ready(function(){

                    $.getJSON('paiddetails.json', function(data)
                    {
				var ranking = "";
				var headline = "";
				for (var x = 0; x < data.length; x++) {
					headline = "<h1>" + data[x].companyname + "</h1>";
					headline += "<h1>" + data[x].name + "</h1>";

					ranking += "<p>Pulled Date:  " + data[x].timestamp + " = " + data[x].rank + "</p>";
				}
				var content = "";
				content = headline + ranking;

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
