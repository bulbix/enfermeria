<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Grails"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">			
		<link rel="stylesheet" href="${resource(dir: 'css/themes/redmond', file: 'jquery-ui.css')}" type="text/css"/>
		<link rel="stylesheet" href="${resource(dir:'css',file:'ui.jqgrid.css')}" type="text/css" />
		
		<g:javascript library="application"/>
		
		<g:layoutHead/>
		<r:layoutResources />
	</head>
	<body>		
		<g:javascript src="underscore-min.js"/>
		<g:javascript src="jquery-ui-1.10.3/jquery-1.9.1.js"/>
		<g:javascript src="jquery-ui-1.10.3/external/jquery.mousewheel.js"/>		
		<g:javascript src="jquery-ui-1.10.3/ui/jquery-ui.js"/>				
		<g:javascript src="jquery-ui-1.10.3/ui/jquery.ui.position.js"/>
		<g:javascript src="jquery-ui-1.10.3/ui/i18n/jquery.ui.datepicker-es.js"/>		
		<g:javascript src="jquery.validate.min.js"/>
		<g:javascript src="i18n/grid.locale-es.js"/>
		<g:javascript src="jquery.jqGrid.min.js"/>
		<g:javascript src="jquery.currency.js"/>
		<g:javascript src="jquery.blockUI.js"/>
		<g:javascript src="jquery.floatThead.min.js"/>
	
		<a name="arriba"></a>
		
		<table>
			<tr>
				<td>
					<img src="${resource(dir: 'images', file: 'logotipo.jpg')}" alt="INR"/>
				</td>
				<td>
					<h3><g:message code="main.title.application"/></h3>
				</td>
				<td>
					<img src="${resource(dir: 'images', file: 'logoINR2013.png')}" alt="INR"/>
				</td>
			</tr>
		</table>
		
		<div style="text-align: center">
			<sec:ifLoggedIn>	
				<span style="white-space: nowrap;">
					<span style="font-size:20px;color:blue"><g:usuarioActual/></span>
					<g:remoteLink controller="logout" action="index" onComplete="window.close()" style="font-size:20px;color:red" >
					Cerrar Sesion</g:remoteLink>					
				</span>	
			</sec:ifLoggedIn>
		</div>
				
		
		<g:layoutBody/>
		<r:layoutResources />
	</body>
</html>
