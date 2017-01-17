<#macro myInnerLayout>
	<#import "../../layout/default.ftl" as layout>
	<@layout.myLayout "Panel">
			<div class="">
				<#if user??>
					<div class="col-md-12">
						<h4 class="pull-left col-md-2">Zalogowano jako ${user.getUsername()}</h4>
						<h4 class="pull-left col-md-2">
							<a href="/logout">
								<span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
								Wyloguj
							</a>
						</h4>
					</div>
				</#if>
					
				<nav class="col-xs-3 menu" style="height: 599px;">
					
					<ul class="btn-group-vertical">
					  <li><a href="/app/index" class="btn btn-primary">Przegląd</a></li>
					  <li><a href="/app/transaction/add/spending" class="btn btn-primary">Dodaj wydatek</a></li>
					  <li><a href="/app/transaction/add/revenue" class="btn btn-primary">Dodaj przychód</a></li>
					  <li><a href="/app/checkspendings" class="btn btn-primary">Sprawdź wydatki</a></li>
					  <li><a href="/app/checkrevenues" class="btn btn-primary">Sprawdź przychody</a></li>
					  <li><a href="/app/balancesheet" class="btn btn-primary">Bilans</a></li>
					  <li><a href="/app/chart" class="btn btn-primary">Diagramy</a></li>
					  <li><a href="/app/settings" class="btn btn-primary">Ustawienia</a></li>
					</div>
				</div>
				<div class="pages col-xs-9" style="height: 743px;">
					
								<div class="row">
									<div class="col-xs-10">
										<div class="well ">
											<#if warning??>
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
								<#nested/>
							</div>
						</div>
					</div>
				</div>
			</div>
	</@layout.myLayout>
</#macro>

