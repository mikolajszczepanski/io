<#import "layout/app.ftl" as layout2> <@layout2.myInnerLayout>



<div class="">
	
	<form class="form-horizontal" method="post"
		action="/app/categories/add?transactionType=${activeCategory}">
		<#if return_url??>
			<input type="hidden" name="return_url" value="${return_url}">
		</#if>
		<fieldset>
			<legend>
				<h1>Dodaj kategorie</h1>
			</legend>
			<#if errors??>
			<div class="bs-component">
				<div class="alert alert-dismissible alert-danger">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Błąd!</strong>
					<#list errors as error>
						${error}
					</#list>
				</div>
				<div id="source-button" class="btn btn-primary btn-xs"
					style="display: none;">&lt; &gt;</div>
			</div>
			</#if>
			<div class="form-group">
				<label for="categoryName" class="col-md-2 control-label">Nazwa
					kategori</label>

				<div class="col-md-10">
					<input type="text" name="categoryName" class="form-control"
						id="categoryName" placeholder="Nazwa">
				</div>
			</div>
			<div class="form-group">
				<label for="categoryType" class="col-md-2 control-label">Typ
					kategori</label>

				<div class="col-md-10">
					<select name="categoryType" id="categoryType" class="form-control">
						<#list types as type>
							<#assign selected="">
							<#if activeCategory??>
								<#if type.getValue() == activeCategory>
									<#assign selected=" selected='selected'">
								</#if>
							</#if>
							<option value="${type.getValue()}" ${selected}>${type.getKey()}</option>
						</#list>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="categoryColor" class="col-md-2 control-label">Kolor
					kategori</label>

				<div class="col-md-10">
					<input type="color" name="categoryColor" style="max-width: 50px"
						class="form-control" id="color" placeholder="Kolor">
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-10 col-md-offset-2">
					<button type="submit" class="btn btn-primary">Dodaj</button>
					<#if return_url??>
						<a href="${return_url}" type="submit" class="btn btn-primary">Powrót</button>
					</#if>
				</div>
			</div>
		</fieldset>
	</form>
</div>


</@layout2.myInnerLayout>
