<?php
function exec_enabled() {
  $disabled = explode(', ', ini_get('disable_functions'));
  return !in_array('exec', $disabled);
}
?>


<?php
exec("curl 'http://server3:8080/icsm-report-1.0/api/paid/itunesFeatured' > ifeatured.json")
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

                    $.getJSON('ifeatured.json', function(data)
                    {
				var content = "";
				for (var x = 0; x < data.length; x++) {
					content += "<p>";
					content += "<div style=\"font-weight: bold;\"><a href=\"http://localhost:8888/paiddetails.php?appId=" + data[x] + "\">" + data[x] + "</a></div>";
					content += "</p><br>";
				}

				$('#week1-report').html(content);
                    });

        });

    </script>

</head>
<body>

Itunes Featured Apps

<div id="week1-report"></div>

</body>
</html>
<!-- Copyright 2011 jQuery4u.com -->
