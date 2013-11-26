<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<g:set var="entityName"	value="${message(code: 'default.home.label', default: 'Entrada')}" />
	<title>Hoja Registro Clinico de Enfermeria</title>
</head>
<body>	

<g:javascript src="hojaRegistroClinico/jefeSupervisor.js" />

<div id="mostrarHojas"></div>
<div id="mostrarFirma"></div>
<div id="dialog-confirm"></div>
<div id="dialog-mensaje"></div>

<div id="accordion">
  
  <g:each var="piso" in="${pisos}">
  
	  <h3>${piso.descripcion}</h3>
	  
	  <div>  
	      <table>
	      <thead>
	      	<tr>
	      		<th>Paciente</th>
	      		<th></th>
	      		<th></th>
	      		<th></th>
	      	</tr>
	      </thead>
	      <tbody>
	  	 <g:each var="paciente" in="${piso.pacientes}" >
	  	 	<tr>
	  	 		<td>${paciente.label}</td>
	  	 		<td><input type="button" value="M" onclick="mostrarHojas(${paciente.id},'MATUTINO','${paciente.label}')"/></td>
	  	 		<td><input type="button" value="V" onclick="mostrarHojas(${paciente.id},'VESPERTINO','${paciente.label}')"/></td>
	  	 		<td><input type="button" value="N" onclick="mostrarHojas(${paciente.id},'NOCTURNO','${paciente.label}')"/></td>
	  	 	</tr>
	  	 
	  	 </g:each>
	  	 </tbody>
	  	</table>
	  </div>
  </g:each>
  
</div>
</body>
</html>