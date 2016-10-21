<#import "layout/default.ftl" as layout>
<@layout.myLayout "Strona główna">

	<div class="jumbotron">
         <h1>System planowania wydatków</h1>
         
         <#if error??>
		    <div class="bs-component">
	            <div class="alert alert-dismissible alert-danger">
	            <button type="button" class="close" data-dismiss="alert">×</button>
	            <strong>Błąd krytyczny!</strong>
	            ${error}
	            </div>
	            <div id="source-button" class="btn btn-primary btn-xs" style="display: none;">&lt; &gt;
	            </div>
            </div>
		    </#if>

          <p>Strona główna</p>

          <p><a href="/login" class="btn btn-primary btn-lg">Zaloguj<div class="ripple-container"></div></a></p>
          <p><a href="/register" class="btn btn-primary btn-lg">Rejestruj<div class="ripple-container"></div></a></p>
    </div>

</@layout.myLayout>