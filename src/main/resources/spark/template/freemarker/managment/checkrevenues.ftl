<#import "layout/app.ftl" as layout2> <@layout2.myInnerLayout>

<h1>Dochody</h1>



<table class="table table-striped table-hover ">
	<thead>
		<tr>
			<th>Typ</th>
			<th>Kwota</th>
			<th>Kategoria</th>
			<th>Data</th>
			<th>Opcje</th>
		</tr>
	</thead>
	<tbody>

		<#list transactions as transaction >
		<tr>
			<td>
			<#if transaction.getFrequencyType().getValue() == 0>
				Jednorazowa
				<#assign frequency="Jednorazowa">
			</#if>
			<#if transaction.getFrequencyType().getValue() == 1>
				Tygodniowa
				<#assign frequency="Tygodniowa">
			</#if>
			<#if transaction.getFrequencyType().getValue() == 2>
				Miesięczna
				<#assign frequency="Miesięczna">
			</#if>
			<td>${transaction.getAmount()}</td>
			<td style="color:${transaction.getCategory().getColor()}">${transaction.getCategory().getName()}</td>
			<td>${transaction.getStringDate()}</td>
			<td><a href="#" onclick="deleteButton('${frequency}','${transaction.getAmount()}','${transaction.getCategory().getName()}','${transaction.getStringDate()}','${transaction.getId()}')">usuń</a></td>
		</tr>
		</#list>
	</tbody>
</table>

<script>
function deleteButton(frequency,amount,categoryName,date,ID) {
 	if(confirm("Czy na pewno chcesz usunąć transakcję\n" + frequency + '  ' + amount + '  ' + categoryName + '  ' + date + ' ' + '?')){
	 	var xhttp = new XMLHttpRequest();
	    xhttp.open("POST", "/app/balancesheet", true);
	    xhttp.onreadystatechange = function() {
    	if (xhttp.readyState == 4 && xhttp.status == 200) {
      			location.reload(1);
    		}
  		};
	    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	    xhttp.send("ID="+ID);
	}
}
</script>

</@layout2.myInnerLayout>
