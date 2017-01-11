<#macro myInnerLayout>
	<#import "../../layout/default.ftl" as layout>
	<@layout.myLayout "Panel">
			<div class="">
				<#if user??>
					<div class="col-md-12">
						<h4 class="pull-left col-md-2">Zalogowano jako ${user.getUsername()}</h4>
						<h4 class="pull-right col-md-2">
							<a href="/logout">
								<span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
								Wyloguj
							</a>
						</h4>
					</div>
				</#if>
					
				<div class="col-md-3">
					
					<div class="btn-group-vertical">
					  <a href="/app/index" class="btn btn-primary">Przegląd</a>
					  <a href="/app/transaction/add/spending" class="btn btn-primary">Dodaj wydatek</a>
					  <a href="/app/transaction/add/revenue" class="btn btn-primary">Dodaj przychód</a>
					  <a href="/app/checkspendings" class="btn btn-primary">Sprawdź wydatki</a>
					  <a href="/app/checkrevenues" class="btn btn-primary">Sprawdź przychody</a>
					  <a href="/app/balancesheet" class="btn btn-primary">Bilans</a>
					  <a href="/app/chart" class="btn btn-primary">Diagramy</a>
					  <a href="/app/settings" class="btn btn-primary">Ustawienia</a>
					</div>
				</div>
				<div class="well col-md-9 pull-left" style="width:70%;">
				<#if warning??>
					<div class="col-md-11">
					  	<div class="alert alert-warning" style="margin-left:10px;margin-right:-10px">
							<strong>${warning}</strong>
					  	</div>				   
					  </#if>
					  <#if alert??>
					  	<div class="col-md-12 alert alert-danger" style="margin-left:10px;margin-right:-10px">
					  		<strong>${alert}</strong>
					  	</div>	
					  </#if>
					  <#if info??>
					  	<div class="col-md-12 alert alert-info" style="margin-left:10px;margin-right:-10px">
							<ul>
		            			<#list info as info>
									<li style="margin-left:-25px"><strong>${info}</strong></li>
								</#list>
		            		</ul>
					  	</div>				   
				 	  </#if>
					<div>
						<#nested/>
					</div>
				</div>
			</div>
	</@layout.myLayout>
</#macro>