<#import "layout/app.ftl" as layout2>
<@layout2.myInnerLayout>

	<h1>Przegląd</h1>
	
	<#assign months = ['Styczeń', 'Luty', 'Marzec', 'Kwiecień', 'Maj', 'Czerwiec', 
					   'Lipiec', 'Sierpień', 'Wrzesień', 'Październik', 'Listopad', 'Grudzień'] >

	<div class="row">
		<div class="col-sm-11">
			<form class="form-horizontal" method="post" action="/app/index">
			<select name="month" id="month" class="form-control">
				<option value="Year">Cały rok</option>
				<#list months as month>
					<option value=${month_index + 1}>${month}</option>
				</#list>
			</select>
		</div>
			<div class="col-sm-1">
				<button type="submit" button style="bottom:8px;right:15px" class="btn btn-primary">Potwierdź</button>
			</div>
	</div>
	<#assign middle=6>
	<#if !timescale??>
		<#assign middle=5>
		<#assign timescale = "'Styczeń', 'Luty', 'Marzec', 'Kwiecień', 'Maj', 'Czerwiec', 
					   'Lipiec', 'Sierpień', 'Wrzesień', 'Październik', 'Listopad', 'Grudzień'">
	</#if>
	
	<div class="col-md-offset-${middle}"><B>${title}</B></div>
	
	
	<canvas id="myChart" width="800" height="400" style=" max-width: 110%;position: relative"></canvas>
	<script>
	var data = {
		    labels: [${timescale}],
		    datasets: [
		        {
		        	
		            label: "Wydatki",
		            fillColor: "rgba(255,0,0,1)",
		            strokeColor: "rgba(255,0,0,1)",
		            pointColor: "rgba(220,220,220,1)",
		            pointStrokeColor: "#fff",
		            pointHighlightFill: "#fff",
		            pointHighlightStroke: "rgba(220,220,220,1)",
		            data: <#if SPENDING??> ${SPENDING} </#if>
		        },
		        {
		            label: "Dochody",
		            fillColor: "rgba(0,255,0,1)",
		            strokeColor: "rgba(60,210,80,1)",
		            pointColor: "rgba(151,187,205,1)",
		            pointStrokeColor: "#fff",
		            pointHighlightFill: "#fff",
		            pointHighlightStroke: "rgba(151,187,205,1)",
		            data: <#if REVENUE??> ${REVENUE} </#if>
		        }
		    ]
		};
	var ctx = $("#myChart").get(0).getContext("2d");
	var myLineChart = new Chart(ctx).Bar(data, {});
	</script>
</@layout2.myInnerLayout>
