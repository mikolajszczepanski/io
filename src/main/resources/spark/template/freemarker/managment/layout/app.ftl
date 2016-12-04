<#macro myInnerLayout>
	<#import "../../layout/default.ftl" as layout>
	<@layout.myLayout "Panel">
			<div class="col-md-12 well">
				<div class="col-md-3">
					<#if user??>
					<h4>Zalogowano jako ${user.getUsername()}</h4>
					</#if>
					<div class="btn-group-vertical">
					  <a href="/app/index" class="btn btn-raised btn-lg btn-primary">Przegląd</a>
					  <a href="/app/transaction/add/spending" class="btn btn-raised btn-lg btn-primary">Dodaj wydatek</a>
					  <a href="/app/transaction/add/revenue" class="btn btn-raised btn-lg btn-primary">Dodaj przychód</a>
					  <a href="/app/checkspendings" class="btn btn-raised btn-lg btn-primary">Sprawdź wydatki</a>
					  <a href="/app/checkrevenues" class="btn btn-raised btn-lg btn-primary">Sprawdź przychody</a>
					  <a href="/app/balancesheet" class="btn btn-raised btn-lg btn-primary">Bilans</a>
					  <a href="/app/chart" class="btn btn-raised btn-lg btn-primary">Diagramy</a>
					  <a href="/app/settings" class="btn btn-raised btn-lg btn-primary">Ustawienia</a>
					  <#if warning??>
					  	<div class="alert alert-warning" style="margin-left:10px;margin-right:-10px">
							<strong>${warning}</strong>
					  	</div>				   
					  </#if>
					  <#if alert??>
					  	<div class="alert alert-danger" style="margin-left:10px;margin-right:-10px">
					  		<strong>${alert}</strong>
					  	</div>	
					  </#if>
					  <#if info??>
					  	<div class="alert alert-info" style="margin-left:10px;margin-right:-10px">
							<ul>
		            			<#list info as info>
									<li style="margin-left:-25px"><strong>${info}</strong></li>
								</#list>
		            		</ul>
					  	</div>				   
					  </#if>
					</div>
				</div>
				<div class="col-md-9">
					<a href="/logout" class="pull-right">
						<span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
						Wyloguj
					</a>
				</div>
				<div class="col-md-8">
					<#nested/>
				</div>
			</div>
	</@layout.myLayout>
</#macro>