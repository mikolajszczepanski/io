<#import "layout/default.ftl" as layout>
<@layout.myLayout "Strona główna">


	<div class="pages col-xs-9" style="height: 743px;">
					
								<div class="row">
									<div class="col-xs-10">
										<div class="well ">
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

          <p>Aplikacja napisana w ramach projektu na przedmiot "Inżynieria Oprogramowania - wybrane aspekty podejście praktyczne"</p>

          <p><a href="/login" class="btn btn-primary btn-lg">Zaloguj<div class="ripple-container"></div></a></p>
          <p><a href="/register" class="btn btn-primary btn-lg">Rejestruj<div class="ripple-container"></div></a></p>
    </div>
    
    </div>
    </div>
    </div>
    </div>

</@layout.myLayout>