<#import "layout/app.ftl" as layout2> <@layout2.myInnerLayout>

<div class="">
	<form class="form-horizontal" method="post"
		action="/app/settings">
		
		<fieldset>
			<legend>
				<h1>Ustawienia</h1>
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
              <div class="checkbox">
                <label>
                  <#if user??>
	                  <#if user.haveMonthyLimit() >
	                  	<input type="checkbox" 
	                  	name="isMonthlyLimitActive"
	                  	id="isMonthlyLimitActive"
	                  	checked=checked
	                  	>
	                  <#else>
	                  	<input type="checkbox" 
	                  	id="isMonthlyLimitActive"
	                  	name="isMonthlyLimitActive">
	                  </#if>
                  </#if>
                  
                  <span class="checkbox-material"><span class="check"></span>
                  </span>Czy usługa miesięczny limit ma być aktywna?
                </label>
              </div>
            </div>
			<div class="form-group">
				
				<label for="monthyLimit" class="col-md-2 control-label">Ustaw Miesięczny limit
				</label>

				<div class="col-md-10">
					<input type="number"
						   step="0.01"
						   min="0"
						   name="monthlyLimit" 
						   class="form-control"
						   id="monthlyLimit" 
						   value="${monthlyLimit}"
						   placeholder=""
					>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-10 col-md-offset-2">
					<button type="submit" class="btn btn-primary">Zapisz zmiany</button>
				</div>
			</div>
		</fieldset>
	</form>
</div>
<script>
$( document ).ready(function() {
	$("#isMonthlyLimitActive").click( function(){
			$("#monthlyLimit").attr("disabled", !$(this).is(':checked'));
		});
});
</script>

</@layout2.myInnerLayout>
