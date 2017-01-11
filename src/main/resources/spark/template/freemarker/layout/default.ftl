<#macro myLayout title="FreeMarker example">

  <!DOCTYPE html>
  <html lang="en">
    <head>
	    <meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>
          ${title}
        </title>
        
        <!-- Material Design fonts -->
	    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Roboto:300,400,500,700">
	    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/icon?family=Material+Icons">
        
        <!-- Bootstrap -->
	    <link href="/bootstrap-3.3.6-dist/css/bootstrap.min.css" rel="stylesheet">
	
	    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	    <!--[if lt IE 9]>
	      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	    <![endif]-->
	    
	    <!-- Bootstrap Material Design -->
	    <link rel="stylesheet" type="text/css" href="/bootstrap-material-design-4.0.1-dist/css/bootstrap-material-design.css">
	    <link rel="stylesheet" type="text/css" href="/bootstrap-material-design-4.0.1-dist/css/ripples.min.css">
  
  
  		<!-- Custom Styles -->
  		<link rel="stylesheet" type="text/css" href="/css/app.css">
  		
  		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	    <!-- Include all compiled plugins (below), or include individual files as needed -->
	    <script src="/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>
	    <!-- Chart Library -->
	    <script src="/js/Chart.js"></script>
  		
    </head>
    <body>
    	<input id="reloadValue" type="hidden" name="reloadValue" value="" />
    	<br>
    	<div class="row">
	        <#nested/>
        </div>
        <script type="text/javascript">    
            $(document).ready(function () {
                     var d = new Date();
                     d = d.getTime();
                     if ($('#reloadValue').val().length == 0) 
                     {
                          $('#reloadValue').val(d);
                          $('body').show();
                     }
                     else 
                     {
                          $('#reloadValue').val('');
                          location.reload();
                     }
            });
	   </script>
        
    </body>
  </html>
  
</#macro>