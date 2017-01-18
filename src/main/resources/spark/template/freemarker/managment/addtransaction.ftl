<#import "layout/app.ftl" as layout2> <@layout2.myInnerLayout>



<div class="">
	<form class="form-horizontal" method="post"
		action="/app/transaction/add/${transaction_type}">
		<fieldset>
			<#if transaction_title??>
			<legend>
				<h1>Dodaj ${transaction_title}</h1>
			</legend>
			</#if>
			<#if errors??>
			<div class="bs-component">
				<div class="alert alert-dismissible alert-danger">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Błąd!</strong>
					<ul>
					<#list errors as error>
							<p>${error}</p>
					</#list>
					</ul>
				</div>
				<div id="source-button" class="btn btn-primary btn-xs"
					style="display: none;">&lt; &gt;</div>
			</div>
			</#if>
			<input type="hidden" name="transactionType" class="form-control" value="${transaction_type_id}">
			<div class="form-group">
				<label for="transactionAmount" class="col-md-2 control-label">Kwota</label>
				<div class="col-md-10">
					<input type="number" step="0.01" name="transactionAmount" class="form-control" value="0">
				</div>
			</div>
			<div class="form-group">
				<label for="categoryName" class="col-md-2 control-label">Wybierz kategorie</label>

				<div class="col-md-10">
					<select name="transactionCategoryId" id="transactionCategoryId" class="form-control">
					<#if categories??>
						<#list categories as category>
							<#assign selected="">
							<#if activeCategory??>
								<#if category.getId() == activeCategory.getId()>
									<#assign selected=" selected='selected'">
								</#if>
							</#if>
							<option value="${category.getId()}" ${selected}>${category.getName()}</option>
						</#list>
					</#if>
					</select>
				</div>
				<a href="/app/categories/add?return_url=/app/transaction/add/${transaction_type_name}&transactionType=${transaction_type_id}" class="btn btn-primary">Dodaj nową kategorie</a>
			</div>
			<div class="form-group">
				<label for="transactionFrequencyType" class="col-md-2 control-label">Wybierz powtarzalność</label>

				<div class="col-md-10">
					<select name="transactionFrequencyType" id="transactionFrequencyType" class="form-control">
						<option value="0">Raz</option>
						<option value="1">Tygodniowo</option>
						<option value="2">Miesięcznie</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="transactionDate" class="col-md-2 control-label">Data</label>
				<div class="col-md-10">
					<input type="text" name="transactionDate" class="form-control" id="transactionDate">
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-10 col-md-offset-2">
					<button type="submit" class="btn btn-primary">Dodaj</button>
				</div>
			</div>
		</fieldset>
	</form>
</div>

<script>
$('#transactionDate').datepicker({
    format: "yyyy-mm-dd",
    todayBtn: true,
    clearBtn: true,
    language: "pl",
    autoclose: true,
    todayHighlight: true
});
</script>


</@layout2.myInnerLayout>
