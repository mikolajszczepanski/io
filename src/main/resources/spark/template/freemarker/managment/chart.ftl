<#import "layout/app.ftl" as layout2> <@layout2.myInnerLayout>


	
	<#if REVENUE??>
		<div class="row">
			<div class="col-sm-9">
				<div class="col-sm-2">
					<form class="form-horizontal" method="post" action="/app/chart">
					<button name= "SPENDING" value= "SPENDING" button type="submit" button style="right:40px;bottom:10px" class="btn btn-primary"><h2>${other}</h2></button>
				</div>
			</div>
				<h1>${REVENUE}</h1>
		</div>
		<#if chartdata??>
		<canvas id="myChart" width="700" height="300"></canvas>
		<script>
			var data = [
		    	${chartdata}
		    ];
		    		
			var ctx = $("#myChart").get(0).getContext("2d");
			var myLineChart = new Chart(ctx).Pie(data, {});
		</script>
		<#else>
			<center><strong><h2>Brak dochodów!</h2></strong></center>
		</#if>
	</#if>
	
	
	<#if SPENDING??>
		<div class="row">
			<div class="col-sm-9">
				<h1>${SPENDING}</h1>
			</div>
				<div class="col-sm-2">
					<form class="form-horizontal" method="post" action="/app/chart">
					<button name= "REVENUE" value= "REVENUE" button type="submit" button style="right:55px;bottom:5px" class="btn btn-primary"><h2>${other}</h2></button>
				</div>
		</div>
		<#if chartdata??>
		<canvas id="myChart" width="700" height="300"></canvas>
		<script>
			var data = [
		    	${chartdata}
		    ];
		    		
			var ctx = $("#myChart").get(0).getContext("2d");
			var myLineChart = new Chart(ctx).Pie(data, {});
		</script>
		<#else>
			<center><strong><h2>Brak wydatków!</h2></strong></div></center>
		</#if>
		</#if>
</@layout2.myInnerLayout>