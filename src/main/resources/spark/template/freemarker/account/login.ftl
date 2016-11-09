<#import "../layout/default.ftl" as layout>
<@layout.myLayout "Strona logowania">

	<div class="col-md-6 col-md-offset-3 well">
		<form class="form-horizontal" method="post" action="/login">
		  <fieldset>
		    <legend>
		    	<a href="/home">
		    		<i class="material-icons">keyboard_backspace</i>
		    	</a>Logowanie
		    	</legend>
		    <#if success??>
		    <div class="bs-component">
	            <div class="alert alert-dismissible alert-success">
	            <button type="button" class="close" data-dismiss="alert">×</button>
	            <strong>Sukces!</strong>
	            ${success}
	            </div>
	            <div id="source-button" class="btn btn-primary btn-xs" style="display: none;">&lt; &gt;
	            </div>
            </div>
		    </#if>
		    <#if error??>
		    <div class="bs-component">
	            <div class="alert alert-dismissible alert-danger">
	            <button type="button" class="close" data-dismiss="alert">×</button>
	            <strong>Błąd!</strong>
	            ${error}
	            </div>
	            <div id="source-button" class="btn btn-primary btn-xs" style="display: none;">&lt; &gt;
	            </div>
            </div>
		    </#if>
		    <div class="form-group">
		      <label for="inputLogin" class="col-md-2 control-label">Wpisz login</label>
		
		      <div class="col-md-10">
		        <input type="login" name="login" class="form-control" id="inputLogin" placeholder="Login">
		      </div>
		    </div>
		    <div class="form-group">
		      <label for="inputPassword" class="col-md-2 control-label">Wpisz hasło</label>
		
		      <div class="col-md-10">
		        <input type="password" name="password" class="form-control" id="inputPassword" placeholder="Hasło">
		      </div>
		    </div>
		    <div class="form-group">
		     <div class="col-md-10 col-md-offset-2">
		        <button type="submit" class="btn btn-primary">Zaloguj</button>
		     </div>
    		</div>
		  </fieldset>
		</form>
	</div>

</@layout.myLayout>