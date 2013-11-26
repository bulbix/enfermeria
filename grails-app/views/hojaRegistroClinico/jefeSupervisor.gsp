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


<g:barraNavegacion tagAbajo="abajoJefeSupervisor" recargarJefeSupervisor="true"></g:barraNavegacion>

<p style="font-size:18px;color:red;text-align:center">
	Solo se mostraran pacientes hospitalizados con hojas
</p>


<div>
	<input type="hidden" id="tipoHistorico" name="tipoHistorico" value="hoja"/>	
	<input style="display:none" type="checkbox" id="historico" checked/>
	<label for="pacienteauto" style="font-weight:bold">Paciente Historico:</label> 
	<input type="text" id="pacienteauto" style="width: 500px;" title="Busque por nombre, registro o cama" />
	<input type="hidden" name="idPaciente" id="idPaciente"/>
	<input type="button" value="M" id="btnMatutinoHistorico" />
	<input type="button" value="V"   id="btnVespertinoHistorico"/>
	<input type="button" value="N"   id="btnNocturnoHistorico"/>
	<input type="button" value="Nueva Busqueda"   id="btnNuevoHistorico"/>
</div>

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
	  	 	<g:if test="${paciente.tieneHoja}">
		  	 	<tr>
		  	 		<td>${paciente.label}</td>
		  	 		<td><input type="button" value="M" onclick="mostrarHojas(${paciente.id},'MATUTINO','${paciente.label}')"/></td>
		  	 		<td><input type="button" value="V" onclick="mostrarHojas(${paciente.id},'VESPERTINO','${paciente.label}')"/></td>
		  	 		<td><input type="button" value="N" onclick="mostrarHojas(${paciente.id},'NOCTURNO','${paciente.label}')"/></td>
		  	 	</tr>
	  	 	</g:if>
	  	 
	  	 </g:each>
	  	 </tbody>
	  	</table>
	  </div>
  </g:each>
  
</div>

<a name="abajoJefeSupervisor"></a>	

</body>
</html>