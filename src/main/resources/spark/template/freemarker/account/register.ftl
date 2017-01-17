<#import "../layout/default.ftl" as layout>
<@layout.myLayout "Strona rejestracji">

	<div class="pages col-xs-9" style="height: 743px;">
					
								<div class="row">
									<div class="col-xs-10">
										<div class="well ">
		<form class="form-horizontal" method="post" action="/register">
		  <fieldset>
		    <legend>
		    	<a href="/home">
		    		<i class="material-icons">keyboard_backspace</i>
		    	</a>Rejestracja
		    	</legend>
		    <#if error??>
		    <div class="bs-component">
	            <div class="alert alert-dismissible alert-danger">
	            <button type="button" class="close" data-dismiss="alert">×</button>
	            <strong>Błąd!</strong>
	            ${error}
	            <#if errors??>
		            <ul>
		            <#list errors as er>
					  <li>${er}</li>
					</#list>
		            </ul>
	            </#if>
	            </div>
	            <div id="source-button" class="btn btn-primary btn-xs" style="display: none;">&lt; &gt;
	            </div>
            </div>
		    </#if>
		    <div class="form-group">
		      <label for="inputLogin" class="col-md-2 control-label">Podaj login:</label>
		
		      <div class="col-md-10">
		        <input type="text" name="login" class="form-control" id="inputLogin" placeholder="Login">
		      </div>
		    </div>
		    <div class="form-group">
		      <label for="inputEmail" class="col-md-2 control-label">Podaj email:</label>
		
		      <div class="col-md-10">
		        <input type="text" name="email" class="form-control" id="inputEmail" placeholder="Email">
		      </div>
		    </div>
		    <div class="form-group">
		      <label for="inputPassword" class="col-md-2 control-label">Podaj hasło:</label>
		
		      <div class="col-md-10">
		        <input type="password" name="password" class="form-control" id="inputPassword" placeholder="Hasło">
		      </div>
		    </div>
		    <div class="form-group">
		      <label for="inputPasswordRepeat" class="col-md-2 control-label">Powtórz hasło:</label>
		
		      <div class="col-md-10">
		        <input type="password" name="passwordRepeat" class="form-control" id="inputPasswordRepeat" placeholder="Powtórzone hasło">
		      </div>
		    </div>
		    
		    
		    <div class="form-group">
		      <label for="helpQuestion" class="col-md-2 control-label">Wybierz pytanie pomocnicze:</label>
		
		      <div class="col-md-10">
		        <select name="helpQuestion" class="form-control" id="helpQuestion">
		        	<#list questions as question>
					  <option value="${question.getId()}">${question.getQuestion()}</option>
					</#list>
		        </select>
		      </div>
		      <div class="col-md-10">
		        <input type="text" name="helpQuestionAnswer" class="form-control" id="helpQuestionAnswer" placeholder="Odpowiedź na pytanie">
		      </div>
		    </div>
		    
		    <div class="form-group">
		     <div class="col-md-10 col-md-offset-2">
		        <button type="submit" name="submit" class="btn btn-primary">Rejestruj</button>
		     </div>
    		</div>
		  </fieldset>
		</form>
	</div>
	
	</div>
	</div>
	</div>
	</div>

</@layout.myLayout>